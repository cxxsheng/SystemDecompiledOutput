package com.android.server.companion.virtual.audio;

/* loaded from: classes.dex */
public final class VirtualAudioController implements com.android.server.companion.virtual.audio.AudioPlaybackDetector.AudioPlaybackCallback, com.android.server.companion.virtual.audio.AudioRecordingDetector.AudioRecordingCallback, com.android.server.companion.virtual.GenericWindowPolicyController.RunningAppsChangedListener {
    private static final java.lang.String TAG = "VirtualAudioController";
    private static final int UPDATE_REROUTING_APPS_DELAY_MS = 2000;
    private final com.android.server.companion.virtual.audio.AudioPlaybackDetector mAudioPlaybackDetector;
    private final com.android.server.companion.virtual.audio.AudioRecordingDetector mAudioRecordingDetector;

    @com.android.internal.annotations.GuardedBy({"mCallbackLock"})
    private android.companion.virtual.audio.IAudioConfigChangedCallback mConfigChangedCallback;
    private final android.content.Context mContext;
    private com.android.server.companion.virtual.GenericWindowPolicyController mGenericWindowPolicyController;

    @com.android.internal.annotations.GuardedBy({"mCallbackLock"})
    private android.companion.virtual.audio.IAudioRoutingCallback mRoutingCallback;
    private final android.os.Handler mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
    private final java.lang.Runnable mUpdateAudioRoutingRunnable = new java.lang.Runnable() { // from class: com.android.server.companion.virtual.audio.VirtualAudioController$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.companion.virtual.audio.VirtualAudioController.this.notifyAppsNeedingAudioRoutingChanged();
        }
    };
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<java.lang.Integer> mRunningAppUids = new android.util.ArraySet<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.ArraySet<java.lang.Integer> mPlayingAppUids = new android.util.ArraySet<>();
    private final java.lang.Object mCallbackLock = new java.lang.Object();

    public VirtualAudioController(android.content.Context context, android.content.AttributionSource attributionSource) {
        this.mContext = context;
        this.mAudioPlaybackDetector = new com.android.server.companion.virtual.audio.AudioPlaybackDetector(context);
        this.mAudioRecordingDetector = new com.android.server.companion.virtual.audio.AudioRecordingDetector(context);
        if (android.companion.virtualdevice.flags.Flags.metricsCollection()) {
            com.android.modules.expresslog.Counter.logIncrementWithUid("virtual_devices.value_virtual_audio_created_count", attributionSource.getUid());
        }
    }

    public void startListening(@android.annotation.NonNull com.android.server.companion.virtual.GenericWindowPolicyController genericWindowPolicyController, @android.annotation.NonNull android.companion.virtual.audio.IAudioRoutingCallback iAudioRoutingCallback, @android.annotation.Nullable android.companion.virtual.audio.IAudioConfigChangedCallback iAudioConfigChangedCallback) {
        this.mGenericWindowPolicyController = genericWindowPolicyController;
        this.mGenericWindowPolicyController.registerRunningAppsChangedListener(this);
        synchronized (this.mCallbackLock) {
            this.mRoutingCallback = iAudioRoutingCallback;
            this.mConfigChangedCallback = iAudioConfigChangedCallback;
        }
        synchronized (this.mLock) {
            this.mRunningAppUids.clear();
            this.mPlayingAppUids.clear();
        }
        if (iAudioConfigChangedCallback != null) {
            this.mAudioPlaybackDetector.register(this);
            this.mAudioRecordingDetector.register(this);
        }
    }

    public void stopListening() {
        if (this.mHandler.hasCallbacks(this.mUpdateAudioRoutingRunnable)) {
            this.mHandler.removeCallbacks(this.mUpdateAudioRoutingRunnable);
        }
        this.mAudioPlaybackDetector.unregister();
        this.mAudioRecordingDetector.unregister();
        if (this.mGenericWindowPolicyController != null) {
            this.mGenericWindowPolicyController.unregisterRunningAppsChangedListener(this);
            this.mGenericWindowPolicyController = null;
        }
        synchronized (this.mCallbackLock) {
            this.mRoutingCallback = null;
            this.mConfigChangedCallback = null;
        }
    }

    @Override // com.android.server.companion.virtual.GenericWindowPolicyController.RunningAppsChangedListener
    public void onRunningAppsChanged(android.util.ArraySet<java.lang.Integer> arraySet) {
        synchronized (this.mLock) {
            try {
                if (this.mRunningAppUids.equals(arraySet)) {
                    return;
                }
                this.mRunningAppUids.clear();
                this.mRunningAppUids.addAll((android.util.ArraySet<? extends java.lang.Integer>) arraySet);
                android.util.ArraySet<java.lang.Integer> arraySet2 = this.mPlayingAppUids;
                this.mPlayingAppUids = findPlayingAppUids(((android.media.AudioManager) this.mContext.getSystemService(android.media.AudioManager.class)).getActivePlaybackConfigurations(), this.mRunningAppUids);
                if (!this.mPlayingAppUids.isEmpty()) {
                    android.util.Slog.i(TAG, "Audio is playing, do not change rerouted apps");
                    return;
                }
                if (!arraySet2.isEmpty()) {
                    android.util.Slog.i(TAG, "The last playing app removed, delay change rerouted apps");
                    if (this.mHandler.hasCallbacks(this.mUpdateAudioRoutingRunnable)) {
                        this.mHandler.removeCallbacks(this.mUpdateAudioRoutingRunnable);
                    }
                    this.mHandler.postDelayed(this.mUpdateAudioRoutingRunnable, 2000L);
                    return;
                }
                notifyAppsNeedingAudioRoutingChanged();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.companion.virtual.audio.AudioPlaybackDetector.AudioPlaybackCallback
    public void onPlaybackConfigChanged(java.util.List<android.media.AudioPlaybackConfiguration> list) {
        java.util.List<android.media.AudioPlaybackConfiguration> findPlaybackConfigurations;
        updatePlayingApplications(list);
        synchronized (this.mLock) {
            findPlaybackConfigurations = findPlaybackConfigurations(list, this.mRunningAppUids);
        }
        synchronized (this.mCallbackLock) {
            if (this.mConfigChangedCallback != null) {
                try {
                    this.mConfigChangedCallback.onPlaybackConfigChanged(findPlaybackConfigurations);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "RemoteException when calling onPlaybackConfigChanged", e);
                }
            }
        }
    }

    @Override // com.android.server.companion.virtual.audio.AudioRecordingDetector.AudioRecordingCallback
    @android.annotation.RequiresPermission("android.permission.MODIFY_AUDIO_ROUTING")
    public void onRecordingConfigChanged(java.util.List<android.media.AudioRecordingConfiguration> list) {
        java.util.List<android.media.AudioRecordingConfiguration> findRecordingConfigurations;
        synchronized (this.mLock) {
            findRecordingConfigurations = findRecordingConfigurations(list, this.mRunningAppUids);
        }
        synchronized (this.mCallbackLock) {
            if (this.mConfigChangedCallback != null) {
                try {
                    this.mConfigChangedCallback.onRecordingConfigChanged(findRecordingConfigurations);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "RemoteException when calling onRecordingConfigChanged", e);
                }
            }
        }
    }

    private void updatePlayingApplications(java.util.List<android.media.AudioPlaybackConfiguration> list) {
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<java.lang.Integer> findPlayingAppUids = findPlayingAppUids(list, this.mRunningAppUids);
                if (this.mPlayingAppUids.equals(findPlayingAppUids)) {
                    return;
                }
                this.mPlayingAppUids = findPlayingAppUids;
                notifyAppsNeedingAudioRoutingChanged();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyAppsNeedingAudioRoutingChanged() {
        int[] iArr;
        if (this.mHandler.hasCallbacks(this.mUpdateAudioRoutingRunnable)) {
            this.mHandler.removeCallbacks(this.mUpdateAudioRoutingRunnable);
        }
        synchronized (this.mLock) {
            try {
                iArr = new int[this.mRunningAppUids.size()];
                for (int i = 0; i < this.mRunningAppUids.size(); i++) {
                    iArr[i] = this.mRunningAppUids.valueAt(i).intValue();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        synchronized (this.mCallbackLock) {
            if (this.mRoutingCallback != null) {
                try {
                    this.mRoutingCallback.onAppsNeedingAudioRoutingChanged(iArr);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "RemoteException when calling updateReroutingApps", e);
                }
            }
        }
    }

    private static android.util.ArraySet<java.lang.Integer> findPlayingAppUids(java.util.List<android.media.AudioPlaybackConfiguration> list, android.util.ArraySet<java.lang.Integer> arraySet) {
        android.util.ArraySet<java.lang.Integer> arraySet2 = new android.util.ArraySet<>();
        for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration : list) {
            if (arraySet.contains(java.lang.Integer.valueOf(audioPlaybackConfiguration.getClientUid())) && audioPlaybackConfiguration.getPlayerState() == 2) {
                arraySet2.add(java.lang.Integer.valueOf(audioPlaybackConfiguration.getClientUid()));
            }
        }
        return arraySet2;
    }

    private static java.util.List<android.media.AudioPlaybackConfiguration> findPlaybackConfigurations(java.util.List<android.media.AudioPlaybackConfiguration> list, android.util.ArraySet<java.lang.Integer> arraySet) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration : list) {
            if (arraySet.contains(java.lang.Integer.valueOf(audioPlaybackConfiguration.getClientUid()))) {
                arrayList.add(audioPlaybackConfiguration);
            }
        }
        return arrayList;
    }

    @android.annotation.RequiresPermission("android.permission.MODIFY_AUDIO_ROUTING")
    private static java.util.List<android.media.AudioRecordingConfiguration> findRecordingConfigurations(java.util.List<android.media.AudioRecordingConfiguration> list, android.util.ArraySet<java.lang.Integer> arraySet) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.media.AudioRecordingConfiguration audioRecordingConfiguration : list) {
            if (arraySet.contains(java.lang.Integer.valueOf(audioRecordingConfiguration.getClientUid()))) {
                arrayList.add(audioRecordingConfiguration);
            }
        }
        return arrayList;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean hasPendingRunnable() {
        return this.mHandler.hasCallbacks(this.mUpdateAudioRoutingRunnable);
    }

    @com.android.internal.annotations.VisibleForTesting
    void addPlayingAppsForTesting(int i) {
        synchronized (this.mLock) {
            this.mPlayingAppUids.add(java.lang.Integer.valueOf(i));
        }
    }
}
