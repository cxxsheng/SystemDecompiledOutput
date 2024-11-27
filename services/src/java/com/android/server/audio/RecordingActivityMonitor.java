package com.android.server.audio;

/* loaded from: classes.dex */
public final class RecordingActivityMonitor implements android.media.AudioSystem.AudioRecordingCallback {
    public static final java.lang.String TAG = "AudioService.RecordingActivityMonitor";
    private static final com.android.server.utils.EventLogger sEventLogger = new com.android.server.utils.EventLogger(50, "recording activity received by AudioService");
    private final android.content.pm.PackageManager mPackMan;
    private java.util.ArrayList<com.android.server.audio.RecordingActivityMonitor.RecMonitorClient> mClients = new java.util.ArrayList<>();
    private boolean mHasPublicClients = false;
    private java.util.concurrent.atomic.AtomicInteger mLegacyRemoteSubmixRiid = new java.util.concurrent.atomic.AtomicInteger(-1);
    private java.util.concurrent.atomic.AtomicBoolean mLegacyRemoteSubmixActive = new java.util.concurrent.atomic.AtomicBoolean(false);
    private java.util.List<com.android.server.audio.RecordingActivityMonitor.RecordingState> mRecordStates = new java.util.ArrayList();

    static final class RecordingState {
        private android.media.AudioRecordingConfiguration mConfig;
        private final com.android.server.audio.RecordingActivityMonitor.RecorderDeathHandler mDeathHandler;
        private boolean mIsActive;
        private final int mRiid;

        RecordingState(int i, com.android.server.audio.RecordingActivityMonitor.RecorderDeathHandler recorderDeathHandler) {
            this.mRiid = i;
            this.mDeathHandler = recorderDeathHandler;
        }

        RecordingState(android.media.AudioRecordingConfiguration audioRecordingConfiguration) {
            this.mRiid = -1;
            this.mDeathHandler = null;
            this.mConfig = audioRecordingConfiguration;
        }

        int getRiid() {
            return this.mRiid;
        }

        int getPortId() {
            if (this.mConfig != null) {
                return this.mConfig.getClientPortId();
            }
            return -1;
        }

        android.media.AudioRecordingConfiguration getConfig() {
            return this.mConfig;
        }

        boolean hasDeathHandler() {
            return this.mDeathHandler != null;
        }

        boolean isActiveConfiguration() {
            return this.mIsActive && this.mConfig != null;
        }

        void release() {
            if (this.mDeathHandler != null) {
                this.mDeathHandler.release();
            }
        }

        boolean setActive(boolean z) {
            if (this.mIsActive == z) {
                return false;
            }
            this.mIsActive = z;
            return this.mConfig != null;
        }

        boolean setConfig(android.media.AudioRecordingConfiguration audioRecordingConfiguration) {
            if (audioRecordingConfiguration.equals(this.mConfig)) {
                return false;
            }
            this.mConfig = audioRecordingConfiguration;
            return this.mIsActive;
        }

        void dump(java.io.PrintWriter printWriter) {
            printWriter.println("riid " + this.mRiid + "; active? " + this.mIsActive);
            if (this.mConfig != null) {
                this.mConfig.dump(printWriter);
            } else {
                printWriter.println("  no config");
            }
        }
    }

    RecordingActivityMonitor(android.content.Context context) {
        com.android.server.audio.RecordingActivityMonitor.RecMonitorClient.sMonitor = this;
        com.android.server.audio.RecordingActivityMonitor.RecorderDeathHandler.sMonitor = this;
        this.mPackMan = context.getPackageManager();
    }

    public void onRecordingConfigurationChanged(int i, int i2, int i3, int i4, int i5, int i6, boolean z, int[] iArr, android.media.audiofx.AudioEffect.Descriptor[] descriptorArr, android.media.audiofx.AudioEffect.Descriptor[] descriptorArr2, int i7, java.lang.String str) {
        android.media.AudioDeviceInfo audioDevice;
        android.media.AudioRecordingConfiguration createRecordingConfiguration = createRecordingConfiguration(i3, i4, i5, iArr, i6, z, i7, descriptorArr, descriptorArr2);
        if (i5 == 8 && ((i == 0 || i == 2) && (audioDevice = createRecordingConfiguration.getAudioDevice()) != null && "0".equals(audioDevice.getAddress()))) {
            this.mLegacyRemoteSubmixRiid.set(i2);
            this.mLegacyRemoteSubmixActive.set(true);
        }
        if (android.media.MediaRecorder.isSystemOnlyAudioSource(i5)) {
            sEventLogger.enqueue(new com.android.server.audio.RecordingActivityMonitor.RecordingEvent(i, i2, createRecordingConfiguration).printLog(TAG));
        } else {
            dispatchCallbacks(updateSnapshot(i, i2, createRecordingConfiguration));
        }
    }

    public int trackRecorder(android.os.IBinder iBinder) {
        if (iBinder == null) {
            android.util.Log.e(TAG, "trackRecorder called with null token");
            return -1;
        }
        int newAudioRecorderId = android.media.AudioSystem.newAudioRecorderId();
        com.android.server.audio.RecordingActivityMonitor.RecorderDeathHandler recorderDeathHandler = new com.android.server.audio.RecordingActivityMonitor.RecorderDeathHandler(newAudioRecorderId, iBinder);
        if (!recorderDeathHandler.init()) {
            return -1;
        }
        synchronized (this.mRecordStates) {
            this.mRecordStates.add(new com.android.server.audio.RecordingActivityMonitor.RecordingState(newAudioRecorderId, recorderDeathHandler));
        }
        return newAudioRecorderId;
    }

    public void recorderEvent(int i, int i2) {
        int i3 = 0;
        if (this.mLegacyRemoteSubmixRiid.get() == i) {
            this.mLegacyRemoteSubmixActive.set(i2 == 0);
        }
        if (i2 != 0) {
            if (i2 != 1) {
                i3 = -1;
            } else {
                i3 = 1;
            }
        }
        if (i == -1 || i3 == -1) {
            sEventLogger.enqueue(new com.android.server.audio.RecordingActivityMonitor.RecordingEvent(i2, i, null).printLog(TAG));
        } else {
            dispatchCallbacks(updateSnapshot(i3, i, null));
        }
    }

    public void releaseRecorder(int i) {
        dispatchCallbacks(updateSnapshot(3, i, null));
    }

    public boolean isRecordingActiveForUid(int i) {
        synchronized (this.mRecordStates) {
            try {
                for (com.android.server.audio.RecordingActivityMonitor.RecordingState recordingState : this.mRecordStates) {
                    if (recordingState.isActiveConfiguration() && recordingState.getConfig().getClientUid() == i && !recordingState.getConfig().isClientSilenced()) {
                        return true;
                    }
                }
                return false;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void dispatchCallbacks(java.util.List<android.media.AudioRecordingConfiguration> list) {
        java.util.ArrayList<android.media.AudioRecordingConfiguration> arrayList;
        if (list == null) {
            return;
        }
        synchronized (this.mClients) {
            try {
                if (this.mHasPublicClients) {
                    arrayList = anonymizeForPublicConsumption(list);
                } else {
                    arrayList = new java.util.ArrayList<>();
                }
                java.util.Iterator<com.android.server.audio.RecordingActivityMonitor.RecMonitorClient> it = this.mClients.iterator();
                while (it.hasNext()) {
                    com.android.server.audio.RecordingActivityMonitor.RecMonitorClient next = it.next();
                    try {
                        if (next.mIsPrivileged) {
                            next.mDispatcherCb.dispatchRecordingConfigChange(list);
                        } else {
                            next.mDispatcherCb.dispatchRecordingConfigChange(arrayList);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.w(TAG, "Could not call dispatchRecordingConfigChange() on client", e);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    protected void dump(java.io.PrintWriter printWriter) {
        printWriter.println("\nRecordActivityMonitor dump time: " + java.text.DateFormat.getTimeInstance().format(new java.util.Date()));
        synchronized (this.mRecordStates) {
            try {
                java.util.Iterator<com.android.server.audio.RecordingActivityMonitor.RecordingState> it = this.mRecordStates.iterator();
                while (it.hasNext()) {
                    it.next().dump(printWriter);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        printWriter.println("\n");
        sEventLogger.dump(printWriter);
    }

    private static java.util.ArrayList<android.media.AudioRecordingConfiguration> anonymizeForPublicConsumption(java.util.List<android.media.AudioRecordingConfiguration> list) {
        java.util.ArrayList<android.media.AudioRecordingConfiguration> arrayList = new java.util.ArrayList<>();
        java.util.Iterator<android.media.AudioRecordingConfiguration> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(android.media.AudioRecordingConfiguration.anonymizedCopy(it.next()));
        }
        return arrayList;
    }

    void initMonitor() {
        android.media.AudioSystem.setRecordingCallback(this);
    }

    void onAudioServerDied() {
        java.util.List<android.media.AudioRecordingConfiguration> list;
        synchronized (this.mRecordStates) {
            try {
                java.util.Iterator<com.android.server.audio.RecordingActivityMonitor.RecordingState> it = this.mRecordStates.iterator();
                boolean z = false;
                while (it.hasNext()) {
                    com.android.server.audio.RecordingActivityMonitor.RecordingState next = it.next();
                    if (!next.hasDeathHandler()) {
                        if (next.isActiveConfiguration()) {
                            sEventLogger.enqueue(new com.android.server.audio.RecordingActivityMonitor.RecordingEvent(3, next.getRiid(), next.getConfig()));
                            z = true;
                        }
                        it.remove();
                    }
                }
                if (!z) {
                    list = null;
                } else {
                    list = getActiveRecordingConfigurations(true);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        dispatchCallbacks(list);
    }

    void registerRecordingCallback(android.media.IRecordingConfigDispatcher iRecordingConfigDispatcher, boolean z) {
        if (iRecordingConfigDispatcher == null) {
            return;
        }
        synchronized (this.mClients) {
            try {
                com.android.server.audio.RecordingActivityMonitor.RecMonitorClient recMonitorClient = new com.android.server.audio.RecordingActivityMonitor.RecMonitorClient(iRecordingConfigDispatcher, z);
                if (recMonitorClient.init()) {
                    if (!z) {
                        this.mHasPublicClients = true;
                    }
                    this.mClients.add(recMonitorClient);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void unregisterRecordingCallback(android.media.IRecordingConfigDispatcher iRecordingConfigDispatcher) {
        if (iRecordingConfigDispatcher == null) {
            return;
        }
        synchronized (this.mClients) {
            try {
                java.util.Iterator<com.android.server.audio.RecordingActivityMonitor.RecMonitorClient> it = this.mClients.iterator();
                boolean z = false;
                while (it.hasNext()) {
                    com.android.server.audio.RecordingActivityMonitor.RecMonitorClient next = it.next();
                    if (iRecordingConfigDispatcher.asBinder().equals(next.mDispatcherCb.asBinder())) {
                        next.release();
                        it.remove();
                    } else if (!next.mIsPrivileged) {
                        z = true;
                    }
                }
                this.mHasPublicClients = z;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    java.util.List<android.media.AudioRecordingConfiguration> getActiveRecordingConfigurations(boolean z) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mRecordStates) {
            try {
                for (com.android.server.audio.RecordingActivityMonitor.RecordingState recordingState : this.mRecordStates) {
                    if (recordingState.isActiveConfiguration()) {
                        arrayList.add(recordingState.getConfig());
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (!z) {
            return anonymizeForPublicConsumption(arrayList);
        }
        return arrayList;
    }

    boolean isLegacyRemoteSubmixActive() {
        return this.mLegacyRemoteSubmixActive.get();
    }

    private android.media.AudioRecordingConfiguration createRecordingConfiguration(int i, int i2, int i3, int[] iArr, int i4, boolean z, int i5, android.media.audiofx.AudioEffect.Descriptor[] descriptorArr, android.media.audiofx.AudioEffect.Descriptor[] descriptorArr2) {
        java.lang.String str;
        android.media.AudioFormat build = new android.media.AudioFormat.Builder().setEncoding(iArr[0]).setChannelMask(iArr[1]).setSampleRate(iArr[2]).build();
        android.media.AudioFormat build2 = new android.media.AudioFormat.Builder().setEncoding(iArr[3]).setChannelMask(iArr[4]).setSampleRate(iArr[5]).build();
        int i6 = iArr[6];
        java.lang.String[] packagesForUid = this.mPackMan.getPackagesForUid(i);
        if (packagesForUid != null && packagesForUid.length > 0) {
            str = packagesForUid[0];
        } else {
            str = "";
        }
        return new android.media.AudioRecordingConfiguration(i, i2, i3, build, build2, i6, str, i4, z, i5, descriptorArr, descriptorArr2);
    }

    private java.util.List<android.media.AudioRecordingConfiguration> updateSnapshot(int i, int i2, android.media.AudioRecordingConfiguration audioRecordingConfiguration) {
        int i3;
        synchronized (this.mRecordStates) {
            try {
                if (i2 != -1) {
                    i3 = findStateByRiid(i2);
                } else if (audioRecordingConfiguration == null) {
                    i3 = -1;
                } else {
                    i3 = findStateByPortId(audioRecordingConfiguration.getClientPortId());
                }
                java.util.List<android.media.AudioRecordingConfiguration> list = null;
                if (i3 == -1) {
                    if (i == 0 && audioRecordingConfiguration != null) {
                        this.mRecordStates.add(new com.android.server.audio.RecordingActivityMonitor.RecordingState(audioRecordingConfiguration));
                        i3 = this.mRecordStates.size() - 1;
                    } else {
                        if (audioRecordingConfiguration == null) {
                            android.util.Log.e(TAG, java.lang.String.format("Unexpected event %d for riid %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
                        }
                        return null;
                    }
                }
                com.android.server.audio.RecordingActivityMonitor.RecordingState recordingState = this.mRecordStates.get(i3);
                boolean z = false;
                switch (i) {
                    case 0:
                        boolean active = recordingState.setActive(true);
                        if (audioRecordingConfiguration == null) {
                            z = active;
                            break;
                        } else if (recordingState.setConfig(audioRecordingConfiguration) || active) {
                            z = true;
                            break;
                        }
                    case 1:
                        z = recordingState.setActive(false);
                        if (!recordingState.hasDeathHandler()) {
                            this.mRecordStates.remove(i3);
                            break;
                        }
                        break;
                    case 2:
                        z = recordingState.setConfig(audioRecordingConfiguration);
                        break;
                    case 3:
                        z = recordingState.isActiveConfiguration();
                        recordingState.release();
                        this.mRecordStates.remove(i3);
                        break;
                    default:
                        android.util.Log.e(TAG, java.lang.String.format("Unknown event %d for riid %d / portid %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(recordingState.getPortId())));
                        break;
                }
                if (z) {
                    sEventLogger.enqueue(new com.android.server.audio.RecordingActivityMonitor.RecordingEvent(i, i2, recordingState.getConfig()));
                    list = getActiveRecordingConfigurations(true);
                }
                return list;
            } finally {
            }
        }
    }

    private int findStateByRiid(int i) {
        synchronized (this.mRecordStates) {
            for (int i2 = 0; i2 < this.mRecordStates.size(); i2++) {
                try {
                    if (this.mRecordStates.get(i2).getRiid() == i) {
                        return i2;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return -1;
        }
    }

    private int findStateByPortId(int i) {
        synchronized (this.mRecordStates) {
            for (int i2 = 0; i2 < this.mRecordStates.size(); i2++) {
                try {
                    if (!this.mRecordStates.get(i2).hasDeathHandler() && this.mRecordStates.get(i2).getPortId() == i) {
                        return i2;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return -1;
        }
    }

    private static final class RecMonitorClient implements android.os.IBinder.DeathRecipient {
        static com.android.server.audio.RecordingActivityMonitor sMonitor;
        final android.media.IRecordingConfigDispatcher mDispatcherCb;
        final boolean mIsPrivileged;

        RecMonitorClient(android.media.IRecordingConfigDispatcher iRecordingConfigDispatcher, boolean z) {
            this.mDispatcherCb = iRecordingConfigDispatcher;
            this.mIsPrivileged = z;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.util.Log.w(com.android.server.audio.RecordingActivityMonitor.TAG, "client died");
            sMonitor.unregisterRecordingCallback(this.mDispatcherCb);
        }

        boolean init() {
            try {
                this.mDispatcherCb.asBinder().linkToDeath(this, 0);
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Log.w(com.android.server.audio.RecordingActivityMonitor.TAG, "Could not link to client death", e);
                return false;
            }
        }

        void release() {
            this.mDispatcherCb.asBinder().unlinkToDeath(this, 0);
        }
    }

    private static final class RecorderDeathHandler implements android.os.IBinder.DeathRecipient {
        static com.android.server.audio.RecordingActivityMonitor sMonitor;
        private final android.os.IBinder mRecorderToken;
        final int mRiid;

        RecorderDeathHandler(int i, android.os.IBinder iBinder) {
            this.mRiid = i;
            this.mRecorderToken = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            sMonitor.releaseRecorder(this.mRiid);
        }

        boolean init() {
            try {
                this.mRecorderToken.linkToDeath(this, 0);
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Log.w(com.android.server.audio.RecordingActivityMonitor.TAG, "Could not link to recorder death", e);
                return false;
            }
        }

        void release() {
            this.mRecorderToken.unlinkToDeath(this, 0);
        }
    }

    private static final class RecordingEvent extends com.android.server.utils.EventLogger.Event {
        private final int mClientUid;
        private final java.lang.String mPackName;
        private final int mRIId;
        private final int mRecEvent;
        private final int mSession;
        private final boolean mSilenced;
        private final int mSource;

        RecordingEvent(int i, int i2, android.media.AudioRecordingConfiguration audioRecordingConfiguration) {
            this.mRecEvent = i;
            this.mRIId = i2;
            if (audioRecordingConfiguration != null) {
                this.mClientUid = audioRecordingConfiguration.getClientUid();
                this.mSession = audioRecordingConfiguration.getClientAudioSessionId();
                this.mSource = audioRecordingConfiguration.getClientAudioSource();
                this.mPackName = audioRecordingConfiguration.getClientPackageName();
                this.mSilenced = audioRecordingConfiguration.isClientSilenced();
                return;
            }
            this.mClientUid = -1;
            this.mSession = -1;
            this.mSource = -1;
            this.mPackName = null;
            this.mSilenced = false;
        }

        private static java.lang.String recordEventToString(int i) {
            switch (i) {
                case 0:
                    return "start";
                case 1:
                    return "stop";
                case 2:
                    return "update";
                case 3:
                    return "release";
                default:
                    return "unknown (" + i + ")";
            }
        }

        @Override // com.android.server.utils.EventLogger.Event
        public java.lang.String eventToString() {
            java.lang.String str;
            java.lang.StringBuilder sb = new java.lang.StringBuilder("rec ");
            sb.append(recordEventToString(this.mRecEvent));
            sb.append(" riid:");
            sb.append(this.mRIId);
            sb.append(" uid:");
            sb.append(this.mClientUid);
            sb.append(" session:");
            sb.append(this.mSession);
            sb.append(" src:");
            sb.append(android.media.MediaRecorder.toLogFriendlyAudioSource(this.mSource));
            sb.append(this.mSilenced ? " silenced" : " not silenced");
            if (this.mPackName == null) {
                str = "";
            } else {
                str = " pack:" + this.mPackName;
            }
            sb.append(str);
            return sb.toString();
        }
    }
}
