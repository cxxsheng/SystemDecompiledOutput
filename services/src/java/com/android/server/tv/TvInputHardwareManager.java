package com.android.server.tv;

/* loaded from: classes2.dex */
class TvInputHardwareManager implements com.android.server.tv.TvInputHal.Callback {
    private static final java.lang.String TAG = com.android.server.tv.TvInputHardwareManager.class.getSimpleName();
    private final android.media.AudioManager mAudioManager;
    private final android.content.Context mContext;
    private final com.android.server.tv.TvInputHardwareManager.Listener mListener;
    private final com.android.server.tv.TvInputHal mHal = new com.android.server.tv.TvInputHal(this);
    private final java.lang.Object mLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<com.android.server.tv.TvInputHardwareManager.Connection> mConnections = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<android.media.tv.TvInputHardwareInfo> mHardwareList = new java.util.ArrayList();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<android.hardware.hdmi.HdmiDeviceInfo> mHdmiDeviceList = new java.util.ArrayList();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.lang.String> mHardwareInputIdMap = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseArray<java.lang.String> mHdmiInputIdMap = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<java.lang.String, android.media.tv.TvInputInfo> mInputMap = new android.util.ArrayMap();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Map<java.lang.String, java.util.List<java.lang.String>> mHdmiParentInputMap = new android.util.ArrayMap();
    private final android.hardware.hdmi.IHdmiHotplugEventListener mHdmiHotplugEventListener = new com.android.server.tv.TvInputHardwareManager.HdmiHotplugEventListener();
    private final android.hardware.hdmi.IHdmiDeviceEventListener mHdmiDeviceEventListener = new com.android.server.tv.TvInputHardwareManager.HdmiDeviceEventListener();
    private final android.hardware.hdmi.IHdmiSystemAudioModeChangeListener mHdmiSystemAudioModeChangeListener = new com.android.server.tv.TvInputHardwareManager.HdmiSystemAudioModeChangeListener();
    private final android.content.BroadcastReceiver mVolumeReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.tv.TvInputHardwareManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            com.android.server.tv.TvInputHardwareManager.this.handleVolumeChange(context, intent);
        }
    };
    private int mCurrentIndex = 0;
    private int mCurrentMaxIndex = 0;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseBooleanArray mHdmiStateMap = new android.util.SparseBooleanArray();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<android.os.Message> mPendingHdmiDeviceEvents = new java.util.ArrayList();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.List<android.os.Message> mPendingTvinputInfoEvents = new java.util.ArrayList();
    private final android.os.Handler mHandler = new com.android.server.tv.TvInputHardwareManager.ListenerHandler();

    interface Listener {
        void onHardwareDeviceAdded(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo);

        void onHardwareDeviceRemoved(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo);

        void onHdmiDeviceAdded(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo);

        void onHdmiDeviceRemoved(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo);

        void onHdmiDeviceUpdated(java.lang.String str, android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo);

        void onStateChanged(java.lang.String str, int i);

        void onTvMessage(java.lang.String str, int i, android.os.Bundle bundle);
    }

    public TvInputHardwareManager(android.content.Context context, com.android.server.tv.TvInputHardwareManager.Listener listener) {
        this.mContext = context;
        this.mListener = listener;
        this.mAudioManager = (android.media.AudioManager) context.getSystemService("audio");
        this.mHal.init();
    }

    public void onBootPhase(int i) {
        if (i == 500) {
            android.hardware.hdmi.IHdmiControlService asInterface = android.hardware.hdmi.IHdmiControlService.Stub.asInterface(android.os.ServiceManager.getService("hdmi_control"));
            if (asInterface != null) {
                try {
                    asInterface.addHotplugEventListener(this.mHdmiHotplugEventListener);
                    asInterface.addDeviceEventListener(this.mHdmiDeviceEventListener);
                    asInterface.addSystemAudioModeChangeListener(this.mHdmiSystemAudioModeChangeListener);
                    synchronized (this.mLock) {
                        this.mHdmiDeviceList.addAll(asInterface.getInputDevices());
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Error registering listeners to HdmiControlService:", e);
                }
            } else {
                android.util.Slog.w(TAG, "HdmiControlService is not available");
            }
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            intentFilter.addAction("android.media.STREAM_MUTE_CHANGED_ACTION");
            this.mContext.registerReceiver(this.mVolumeReceiver, intentFilter);
            updateVolume();
        }
    }

    @Override // com.android.server.tv.TvInputHal.Callback
    public void onDeviceAvailable(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo, android.media.tv.TvStreamConfig[] tvStreamConfigArr) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.TvInputHardwareManager.Connection connection = new com.android.server.tv.TvInputHardwareManager.Connection(tvInputHardwareInfo);
                connection.updateConfigsLocked(tvStreamConfigArr);
                connection.updateCableConnectionStatusLocked(tvInputHardwareInfo.getCableConnectionStatus());
                this.mConnections.put(tvInputHardwareInfo.getDeviceId(), connection);
                buildHardwareListLocked();
                this.mHandler.obtainMessage(2, 0, 0, tvInputHardwareInfo).sendToTarget();
                if (tvInputHardwareInfo.getType() == 9) {
                    processPendingHdmiDeviceEventsLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void buildHardwareListLocked() {
        this.mHardwareList.clear();
        for (int i = 0; i < this.mConnections.size(); i++) {
            this.mHardwareList.add(this.mConnections.valueAt(i).getHardwareInfoLocked());
        }
    }

    @Override // com.android.server.tv.TvInputHal.Callback
    public void onDeviceUnavailable(int i) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.TvInputHardwareManager.Connection connection = this.mConnections.get(i);
                if (connection == null) {
                    android.util.Slog.e(TAG, "onDeviceUnavailable: Cannot find a connection with " + i);
                    return;
                }
                connection.resetLocked(null, null, null, null, null, null);
                this.mConnections.remove(i);
                buildHardwareListLocked();
                android.media.tv.TvInputHardwareInfo hardwareInfoLocked = connection.getHardwareInfoLocked();
                if (hardwareInfoLocked.getType() == 9) {
                    java.util.Iterator<android.hardware.hdmi.HdmiDeviceInfo> it = this.mHdmiDeviceList.iterator();
                    while (it.hasNext()) {
                        android.hardware.hdmi.HdmiDeviceInfo next = it.next();
                        if (next.getPortId() == hardwareInfoLocked.getHdmiPortId()) {
                            this.mHandler.obtainMessage(5, 0, 0, next).sendToTarget();
                            it.remove();
                        }
                    }
                }
                this.mHandler.obtainMessage(3, 0, 0, hardwareInfoLocked).sendToTarget();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.tv.TvInputHal.Callback
    public void onStreamConfigurationChanged(final int i, android.media.tv.TvStreamConfig[] tvStreamConfigArr, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.TvInputHardwareManager.Connection connection = this.mConnections.get(i);
                if (connection == null) {
                    android.util.Slog.e(TAG, "StreamConfigurationChanged: Cannot find a connection with " + i);
                    return;
                }
                int configsLengthLocked = connection.getConfigsLengthLocked();
                int inputStateLocked = connection.getInputStateLocked();
                connection.updateConfigsLocked(tvStreamConfigArr);
                java.lang.String str = this.mHardwareInputIdMap.get(i);
                if (str != null) {
                    if (connection.updateCableConnectionStatusLocked(i2)) {
                        if (inputStateLocked != connection.getInputStateLocked()) {
                            this.mHandler.obtainMessage(1, connection.getInputStateLocked(), 0, str).sendToTarget();
                        }
                    } else {
                        if ((configsLengthLocked == 0) != (connection.getConfigsLengthLocked() == 0)) {
                            this.mHandler.obtainMessage(1, connection.getInputStateLocked(), 0, str).sendToTarget();
                        }
                    }
                } else {
                    android.os.Message obtainMessage = this.mHandler.obtainMessage(7, i, i2, connection);
                    this.mPendingTvinputInfoEvents.removeIf(new java.util.function.Predicate() { // from class: com.android.server.tv.TvInputHardwareManager$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(java.lang.Object obj) {
                            boolean lambda$onStreamConfigurationChanged$0;
                            lambda$onStreamConfigurationChanged$0 = com.android.server.tv.TvInputHardwareManager.lambda$onStreamConfigurationChanged$0(i, (android.os.Message) obj);
                            return lambda$onStreamConfigurationChanged$0;
                        }
                    });
                    this.mPendingTvinputInfoEvents.add(obtainMessage);
                }
                android.media.tv.ITvInputHardwareCallback callbackLocked = connection.getCallbackLocked();
                if (callbackLocked != null) {
                    try {
                        callbackLocked.onStreamConfigChanged(tvStreamConfigArr);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "error in onStreamConfigurationChanged", e);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onStreamConfigurationChanged$0(int i, android.os.Message message) {
        return message.arg1 == i;
    }

    @Override // com.android.server.tv.TvInputHal.Callback
    public void onFirstFrameCaptured(int i, int i2) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.TvInputHardwareManager.Connection connection = this.mConnections.get(i);
                if (connection == null) {
                    android.util.Slog.e(TAG, "FirstFrameCaptured: Cannot find a connection with " + i);
                    return;
                }
                java.lang.Runnable onFirstFrameCapturedLocked = connection.getOnFirstFrameCapturedLocked();
                if (onFirstFrameCapturedLocked != null) {
                    onFirstFrameCapturedLocked.run();
                    connection.setOnFirstFrameCapturedLocked(null);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.tv.TvInputHal.Callback
    public void onTvMessage(int i, int i2, android.os.Bundle bundle) {
        synchronized (this.mLock) {
            try {
                if (this.mHardwareInputIdMap.get(i) == null) {
                    return;
                }
                com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                obtain.arg1 = this.mHardwareInputIdMap.get(i);
                obtain.arg2 = bundle;
                this.mHandler.obtainMessage(8, i2, 0, obtain).sendToTarget();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.util.List<android.media.tv.TvInputHardwareInfo> getHardwareList() {
        java.util.List<android.media.tv.TvInputHardwareInfo> unmodifiableList;
        synchronized (this.mLock) {
            unmodifiableList = java.util.Collections.unmodifiableList(this.mHardwareList);
        }
        return unmodifiableList;
    }

    public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getHdmiDeviceList() {
        java.util.List<android.hardware.hdmi.HdmiDeviceInfo> unmodifiableList;
        synchronized (this.mLock) {
            unmodifiableList = java.util.Collections.unmodifiableList(this.mHdmiDeviceList);
        }
        return unmodifiableList;
    }

    public android.util.SparseArray<java.lang.String> getHardwareInputIdMap() {
        android.util.SparseArray<java.lang.String> clone;
        synchronized (this.mLock) {
            clone = this.mHardwareInputIdMap.clone();
        }
        return clone;
    }

    public android.util.SparseArray<java.lang.String> getHdmiInputIdMap() {
        android.util.SparseArray<java.lang.String> clone;
        synchronized (this.mLock) {
            clone = this.mHdmiInputIdMap.clone();
        }
        return clone;
    }

    public java.util.Map<java.lang.String, android.media.tv.TvInputInfo> getInputMap() {
        java.util.Map<java.lang.String, android.media.tv.TvInputInfo> unmodifiableMap;
        synchronized (this.mLock) {
            unmodifiableMap = java.util.Collections.unmodifiableMap(this.mInputMap);
        }
        return unmodifiableMap;
    }

    public java.util.Map<java.lang.String, java.util.List<java.lang.String>> getHdmiParentInputMap() {
        java.util.Map<java.lang.String, java.util.List<java.lang.String>> unmodifiableMap;
        synchronized (this.mLock) {
            unmodifiableMap = java.util.Collections.unmodifiableMap(this.mHdmiParentInputMap);
        }
        return unmodifiableMap;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean checkUidChangedLocked(com.android.server.tv.TvInputHardwareManager.Connection connection, int i, int i2) {
        java.lang.Integer callingUidLocked = connection.getCallingUidLocked();
        java.lang.Integer resolvedUserIdLocked = connection.getResolvedUserIdLocked();
        return callingUidLocked == null || resolvedUserIdLocked == null || callingUidLocked.intValue() != i || resolvedUserIdLocked.intValue() != i2;
    }

    public void addHardwareInput(int i, android.media.tv.TvInputInfo tvInputInfo) {
        java.lang.String str;
        int i2;
        synchronized (this.mLock) {
            try {
                java.lang.String str2 = this.mHardwareInputIdMap.get(i);
                if (str2 != null) {
                    android.util.Slog.w(TAG, "Trying to override previous registration: old = " + this.mInputMap.get(str2) + ":" + i + ", new = " + tvInputInfo + ":" + i);
                }
                this.mHardwareInputIdMap.put(i, tvInputInfo.getId());
                this.mInputMap.put(tvInputInfo.getId(), tvInputInfo);
                processPendingTvInputInfoEventsLocked();
                android.util.Slog.d(TAG, "deviceId =" + i + ", tvinputinfo = " + tvInputInfo);
                for (int i3 = 0; i3 < this.mHdmiStateMap.size(); i3++) {
                    android.media.tv.TvInputHardwareInfo findHardwareInfoForHdmiPortLocked = findHardwareInfoForHdmiPortLocked(this.mHdmiStateMap.keyAt(i3));
                    if (findHardwareInfoForHdmiPortLocked != null && (str = this.mHardwareInputIdMap.get(findHardwareInfoForHdmiPortLocked.getDeviceId())) != null && str.equals(tvInputInfo.getId())) {
                        if (this.mHdmiStateMap.valueAt(i3)) {
                            i2 = 0;
                        } else {
                            i2 = 1;
                        }
                        this.mHandler.obtainMessage(1, i2, 0, str).sendToTarget();
                        return;
                    }
                }
                com.android.server.tv.TvInputHardwareManager.Connection connection = this.mConnections.get(i);
                if (connection != null) {
                    this.mHandler.obtainMessage(1, connection.getInputStateLocked(), 0, tvInputInfo.getId()).sendToTarget();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static <T> int indexOfEqualValue(android.util.SparseArray<T> sparseArray, T t) {
        for (int i = 0; i < sparseArray.size(); i++) {
            if (sparseArray.valueAt(i).equals(t)) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean intArrayContains(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public void addHdmiInput(int i, android.media.tv.TvInputInfo tvInputInfo) {
        if (tvInputInfo.getType() != 1007) {
            throw new java.lang.IllegalArgumentException("info (" + tvInputInfo + ") has non-HDMI type.");
        }
        synchronized (this.mLock) {
            try {
                java.lang.String parentId = tvInputInfo.getParentId();
                if (indexOfEqualValue(this.mHardwareInputIdMap, parentId) < 0) {
                    throw new java.lang.IllegalArgumentException("info (" + tvInputInfo + ") has invalid parentId.");
                }
                java.lang.String str = this.mHdmiInputIdMap.get(i);
                if (str != null) {
                    android.util.Slog.w(TAG, "Trying to override previous registration: old = " + this.mInputMap.get(str) + ":" + i + ", new = " + tvInputInfo + ":" + i);
                }
                this.mHdmiInputIdMap.put(i, tvInputInfo.getId());
                this.mInputMap.put(tvInputInfo.getId(), tvInputInfo);
                if (!this.mHdmiParentInputMap.containsKey(parentId)) {
                    this.mHdmiParentInputMap.put(parentId, new java.util.ArrayList());
                }
                this.mHdmiParentInputMap.get(parentId).add(tvInputInfo.getId());
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeHardwareInput(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                int indexOfEqualValue = indexOfEqualValue(this.mHardwareInputIdMap, str);
                if (indexOfEqualValue >= 0) {
                    this.mHardwareInputIdMap.removeAt(indexOfEqualValue);
                }
                int indexOfEqualValue2 = indexOfEqualValue(this.mHdmiInputIdMap, str);
                if (indexOfEqualValue2 >= 0) {
                    this.mHdmiInputIdMap.removeAt(indexOfEqualValue2);
                }
                if (this.mInputMap.containsKey(str)) {
                    java.lang.String parentId = this.mInputMap.get(str).getParentId();
                    if (parentId != null && this.mHdmiParentInputMap.containsKey(parentId)) {
                        java.util.List<java.lang.String> list = this.mHdmiParentInputMap.get(parentId);
                        list.remove(str);
                        if (list.isEmpty()) {
                            this.mHdmiParentInputMap.remove(parentId);
                        }
                    }
                    this.mInputMap.remove(str);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void updateInputInfo(android.media.tv.TvInputInfo tvInputInfo) {
        synchronized (this.mLock) {
            try {
                if (this.mInputMap.containsKey(tvInputInfo.getId())) {
                    android.util.Slog.w(TAG, "update inputInfo for input id " + tvInputInfo.getId());
                    this.mInputMap.put(tvInputInfo.getId(), tvInputInfo);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public android.media.tv.ITvInputHardware acquireHardware(int i, android.media.tv.ITvInputHardwareCallback iTvInputHardwareCallback, android.media.tv.TvInputInfo tvInputInfo, int i2, int i3, java.lang.String str, int i4) {
        if (iTvInputHardwareCallback == null) {
            throw new java.lang.NullPointerException();
        }
        android.media.tv.tunerresourcemanager.TunerResourceManager tunerResourceManager = (android.media.tv.tunerresourcemanager.TunerResourceManager) this.mContext.getSystemService("tv_tuner_resource_mgr");
        synchronized (this.mLock) {
            try {
                com.android.server.tv.TvInputHardwareManager.Connection connection = this.mConnections.get(i);
                if (connection == null) {
                    android.util.Slog.e(TAG, "Invalid deviceId : " + i);
                    return null;
                }
                android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile = new android.media.tv.tunerresourcemanager.ResourceClientProfile();
                resourceClientProfile.tvInputSessionId = str;
                resourceClientProfile.useCase = i4;
                android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfileLocked = connection.getResourceClientProfileLocked();
                if (resourceClientProfileLocked != null && tunerResourceManager != null && !tunerResourceManager.isHigherPriority(resourceClientProfile, resourceClientProfileLocked)) {
                    android.util.Slog.d(TAG, "Acquiring does not show higher priority than the current holder. Device id:" + i);
                    return null;
                }
                com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl tvInputHardwareImpl = new com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl(connection.getHardwareInfoLocked());
                try {
                    iTvInputHardwareCallback.asBinder().linkToDeath(connection, 0);
                    connection.resetLocked(tvInputHardwareImpl, iTvInputHardwareCallback, tvInputInfo, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), resourceClientProfile);
                    return connection.getHardwareLocked();
                } catch (android.os.RemoteException e) {
                    tvInputHardwareImpl.release();
                    return null;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void releaseHardware(int i, android.media.tv.ITvInputHardware iTvInputHardware, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                com.android.server.tv.TvInputHardwareManager.Connection connection = this.mConnections.get(i);
                if (connection == null) {
                    android.util.Slog.e(TAG, "Invalid deviceId : " + i);
                    return;
                }
                if (connection.getHardwareLocked() == iTvInputHardware && !checkUidChangedLocked(connection, i2, i3)) {
                    android.media.tv.ITvInputHardwareCallback callbackLocked = connection.getCallbackLocked();
                    if (callbackLocked != null) {
                        callbackLocked.asBinder().unlinkToDeath(connection, 0);
                    }
                    connection.resetLocked(null, null, null, null, null, null);
                }
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public android.media.tv.TvInputHardwareInfo findHardwareInfoForHdmiPortLocked(int i) {
        for (android.media.tv.TvInputHardwareInfo tvInputHardwareInfo : this.mHardwareList) {
            if (tvInputHardwareInfo.getType() == 9 && tvInputHardwareInfo.getHdmiPortId() == i) {
                return tvInputHardwareInfo;
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int findDeviceIdForInputIdLocked(java.lang.String str) {
        for (int i = 0; i < this.mConnections.size(); i++) {
            int keyAt = this.mConnections.keyAt(i);
            com.android.server.tv.TvInputHardwareManager.Connection connection = this.mConnections.get(keyAt);
            if (connection != null && connection.getInfoLocked() != null && connection.getInfoLocked().getId().equals(str)) {
                return keyAt;
            }
        }
        return -1;
    }

    public java.util.List<android.media.tv.TvStreamConfig> getAvailableTvStreamConfigList(java.lang.String str, int i, int i2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                int findDeviceIdForInputIdLocked = findDeviceIdForInputIdLocked(str);
                if (findDeviceIdForInputIdLocked < 0) {
                    android.util.Slog.e(TAG, "Invalid inputId : " + str);
                    return arrayList;
                }
                for (android.media.tv.TvStreamConfig tvStreamConfig : this.mConnections.get(findDeviceIdForInputIdLocked).getConfigsLocked()) {
                    if (tvStreamConfig.getType() == 2) {
                        arrayList.add(tvStreamConfig);
                    }
                }
                return arrayList;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean setTvMessageEnabled(java.lang.String str, int i, boolean z) {
        synchronized (this.mLock) {
            try {
                int findDeviceIdForInputIdLocked = findDeviceIdForInputIdLocked(str);
                if (findDeviceIdForInputIdLocked < 0) {
                    android.util.Slog.e(TAG, "Invalid inputId : " + str);
                    return false;
                }
                boolean z2 = true;
                for (android.media.tv.TvStreamConfig tvStreamConfig : this.mConnections.get(findDeviceIdForInputIdLocked).getConfigsLocked()) {
                    z2 = z2 && this.mHal.setTvMessageEnabled(findDeviceIdForInputIdLocked, tvStreamConfig, i, z) == 0;
                }
                return z2;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean captureFrame(java.lang.String str, android.view.Surface surface, final android.media.tv.TvStreamConfig tvStreamConfig, int i, int i2) {
        synchronized (this.mLock) {
            try {
                int findDeviceIdForInputIdLocked = findDeviceIdForInputIdLocked(str);
                if (findDeviceIdForInputIdLocked < 0) {
                    android.util.Slog.e(TAG, "Invalid inputId : " + str);
                    return false;
                }
                com.android.server.tv.TvInputHardwareManager.Connection connection = this.mConnections.get(findDeviceIdForInputIdLocked);
                final com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl hardwareImplLocked = connection.getHardwareImplLocked();
                if (hardwareImplLocked == null) {
                    return false;
                }
                java.lang.Runnable onFirstFrameCapturedLocked = connection.getOnFirstFrameCapturedLocked();
                if (onFirstFrameCapturedLocked != null) {
                    onFirstFrameCapturedLocked.run();
                    connection.setOnFirstFrameCapturedLocked(null);
                }
                boolean startCapture = hardwareImplLocked.startCapture(surface, tvStreamConfig);
                if (startCapture) {
                    connection.setOnFirstFrameCapturedLocked(new java.lang.Runnable() { // from class: com.android.server.tv.TvInputHardwareManager.2
                        @Override // java.lang.Runnable
                        public void run() {
                            hardwareImplLocked.stopCapture(tvStreamConfig);
                        }
                    });
                }
                return startCapture;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void processPendingHdmiDeviceEventsLocked() {
        java.util.Iterator<android.os.Message> it = this.mPendingHdmiDeviceEvents.iterator();
        while (it.hasNext()) {
            android.os.Message next = it.next();
            if (findHardwareInfoForHdmiPortLocked(((android.hardware.hdmi.HdmiDeviceInfo) next.obj).getPortId()) != null) {
                next.sendToTarget();
                it.remove();
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void processPendingTvInputInfoEventsLocked() {
        java.util.Iterator<android.os.Message> it = this.mPendingTvinputInfoEvents.iterator();
        while (it.hasNext()) {
            android.os.Message next = it.next();
            if (this.mHardwareInputIdMap.get(next.arg1) != null) {
                next.sendToTarget();
                it.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVolume() {
        this.mCurrentMaxIndex = this.mAudioManager.getStreamMaxVolume(3);
        this.mCurrentIndex = this.mAudioManager.getStreamVolume(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void handleVolumeChange(android.content.Context context, android.content.Intent intent) {
        boolean z;
        int intExtra;
        java.lang.String action = intent.getAction();
        switch (action.hashCode()) {
            case -1940635523:
                if (action.equals("android.media.VOLUME_CHANGED_ACTION")) {
                    z = false;
                    break;
                }
                z = -1;
                break;
            case 1920758225:
                if (action.equals("android.media.STREAM_MUTE_CHANGED_ACTION")) {
                    z = true;
                    break;
                }
                z = -1;
                break;
            default:
                z = -1;
                break;
        }
        switch (z) {
            case false:
                if (intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) != 3 || (intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0)) == this.mCurrentIndex) {
                    return;
                }
                this.mCurrentIndex = intExtra;
                break;
            case true:
                if (intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) != 3) {
                    return;
                }
                break;
            default:
                android.util.Slog.w(TAG, "Unrecognized intent: " + intent);
                return;
        }
        synchronized (this.mLock) {
            for (int i = 0; i < this.mConnections.size(); i++) {
                try {
                    com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl hardwareImplLocked = this.mConnections.valueAt(i).getHardwareImplLocked();
                    if (hardwareImplLocked != null) {
                        hardwareImplLocked.onMediaStreamVolumeChanged();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getMediaStreamVolume() {
        return this.mCurrentIndex / this.mCurrentMaxIndex;
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, indentingPrintWriter)) {
            synchronized (this.mLock) {
                try {
                    indentingPrintWriter.println("TvInputHardwareManager Info:");
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.println("mConnections: deviceId -> Connection");
                    indentingPrintWriter.increaseIndent();
                    for (int i = 0; i < this.mConnections.size(); i++) {
                        indentingPrintWriter.println(this.mConnections.keyAt(i) + ": " + this.mConnections.valueAt(i));
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("mHardwareList:");
                    indentingPrintWriter.increaseIndent();
                    java.util.Iterator<android.media.tv.TvInputHardwareInfo> it = this.mHardwareList.iterator();
                    while (it.hasNext()) {
                        indentingPrintWriter.println(it.next());
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("mHdmiDeviceList:");
                    indentingPrintWriter.increaseIndent();
                    java.util.Iterator<android.hardware.hdmi.HdmiDeviceInfo> it2 = this.mHdmiDeviceList.iterator();
                    while (it2.hasNext()) {
                        indentingPrintWriter.println(it2.next());
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("mHardwareInputIdMap: deviceId -> inputId");
                    indentingPrintWriter.increaseIndent();
                    for (int i2 = 0; i2 < this.mHardwareInputIdMap.size(); i2++) {
                        indentingPrintWriter.println(this.mHardwareInputIdMap.keyAt(i2) + ": " + this.mHardwareInputIdMap.valueAt(i2));
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("mHdmiInputIdMap: id -> inputId");
                    indentingPrintWriter.increaseIndent();
                    for (int i3 = 0; i3 < this.mHdmiInputIdMap.size(); i3++) {
                        indentingPrintWriter.println(this.mHdmiInputIdMap.keyAt(i3) + ": " + this.mHdmiInputIdMap.valueAt(i3));
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println("mInputMap: inputId -> inputInfo");
                    indentingPrintWriter.increaseIndent();
                    for (java.util.Map.Entry<java.lang.String, android.media.tv.TvInputInfo> entry : this.mInputMap.entrySet()) {
                        indentingPrintWriter.println(entry.getKey() + ": " + entry.getValue());
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.decreaseIndent();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private class Connection implements android.os.IBinder.DeathRecipient {
        private android.media.tv.ITvInputHardwareCallback mCallback;
        private android.media.tv.TvInputHardwareInfo mHardwareInfo;
        private android.media.tv.TvInputInfo mInfo;
        private java.lang.Runnable mOnFirstFrameCaptured;
        private com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl mHardware = null;
        private android.media.tv.TvStreamConfig[] mConfigs = null;
        private java.lang.Integer mCallingUid = null;
        private java.lang.Integer mResolvedUserId = null;
        private android.media.tv.tunerresourcemanager.ResourceClientProfile mResourceClientProfile = null;
        private boolean mIsCableConnectionStatusUpdated = false;

        public Connection(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) {
            this.mHardwareInfo = tvInputHardwareInfo;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void resetLocked(com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl tvInputHardwareImpl, android.media.tv.ITvInputHardwareCallback iTvInputHardwareCallback, android.media.tv.TvInputInfo tvInputInfo, java.lang.Integer num, java.lang.Integer num2, android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile) {
            if (this.mHardware != null) {
                try {
                    this.mCallback.onReleased();
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(com.android.server.tv.TvInputHardwareManager.TAG, "error in Connection::resetLocked", e);
                }
                this.mHardware.release();
            }
            this.mHardware = tvInputHardwareImpl;
            this.mCallback = iTvInputHardwareCallback;
            this.mInfo = tvInputInfo;
            this.mCallingUid = num;
            this.mResolvedUserId = num2;
            this.mOnFirstFrameCaptured = null;
            this.mResourceClientProfile = resourceClientProfile;
            if (this.mHardware != null && this.mCallback != null) {
                try {
                    this.mCallback.onStreamConfigChanged(getConfigsLocked());
                } catch (android.os.RemoteException e2) {
                    android.util.Slog.e(com.android.server.tv.TvInputHardwareManager.TAG, "error in Connection::resetLocked", e2);
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void updateConfigsLocked(android.media.tv.TvStreamConfig[] tvStreamConfigArr) {
            this.mConfigs = tvStreamConfigArr;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public android.media.tv.TvInputHardwareInfo getHardwareInfoLocked() {
            return this.mHardwareInfo;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public android.media.tv.TvInputInfo getInfoLocked() {
            return this.mInfo;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public android.media.tv.ITvInputHardware getHardwareLocked() {
            return this.mHardware;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl getHardwareImplLocked() {
            return this.mHardware;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public android.media.tv.ITvInputHardwareCallback getCallbackLocked() {
            return this.mCallback;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public android.media.tv.TvStreamConfig[] getConfigsLocked() {
            return this.mConfigs;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public java.lang.Integer getCallingUidLocked() {
            return this.mCallingUid;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public java.lang.Integer getResolvedUserIdLocked() {
            return this.mResolvedUserId;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void setOnFirstFrameCapturedLocked(java.lang.Runnable runnable) {
            this.mOnFirstFrameCaptured = runnable;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public java.lang.Runnable getOnFirstFrameCapturedLocked() {
            return this.mOnFirstFrameCaptured;
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public android.media.tv.tunerresourcemanager.ResourceClientProfile getResourceClientProfileLocked() {
            return this.mResourceClientProfile;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (com.android.server.tv.TvInputHardwareManager.this.mLock) {
                resetLocked(null, null, null, null, null, null);
            }
        }

        public java.lang.String toString() {
            return "Connection{ mHardwareInfo: " + this.mHardwareInfo + ", mInfo: " + this.mInfo + ", mCallback: " + this.mCallback + ", mHardware: " + this.mHardware + ", mConfigs: " + java.util.Arrays.toString(this.mConfigs) + ", mCallingUid: " + this.mCallingUid + ", mResolvedUserId: " + this.mResolvedUserId + ", mResourceClientProfile: " + this.mResourceClientProfile + " }";
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        public boolean updateCableConnectionStatusLocked(int i) {
            if (i != 0 || this.mIsCableConnectionStatusUpdated) {
                this.mIsCableConnectionStatusUpdated = true;
                this.mHardwareInfo = this.mHardwareInfo.toBuilder().cableConnectionStatus(i).build();
            }
            return this.mIsCableConnectionStatusUpdated;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public int getConfigsLengthLocked() {
            if (this.mConfigs == null) {
                return 0;
            }
            return this.mConfigs.length;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public int getInputStateLocked() {
            if (getConfigsLengthLocked() <= 0 || this.mIsCableConnectionStatusUpdated) {
                switch (this.mHardwareInfo.getCableConnectionStatus()) {
                }
                return 0;
            }
            return 0;
        }
    }

    private class TvInputHardwareImpl extends android.media.tv.ITvInputHardware.Stub {

        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        private android.media.AudioDevicePort mAudioSource;
        private final android.media.tv.TvInputHardwareInfo mInfo;
        private final java.lang.Object mImplLock = new java.lang.Object();
        private final android.media.AudioManager.OnAudioPortUpdateListener mAudioListener = new android.media.AudioManager.OnAudioPortUpdateListener() { // from class: com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl.1
            public void onAudioPortListUpdate(android.media.AudioPort[] audioPortArr) {
                synchronized (com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl.this.mImplLock) {
                    com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl.this.updateAudioConfigLocked();
                }
            }

            public void onAudioPatchListUpdate(android.media.AudioPatch[] audioPatchArr) {
            }

            public void onServiceDied() {
                synchronized (com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl.this.mImplLock) {
                    try {
                        com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl.this.mAudioSource = null;
                        com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl.this.mAudioSink.clear();
                        if (com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl.this.mAudioPatch != null) {
                            android.media.AudioManager unused = com.android.server.tv.TvInputHardwareManager.this.mAudioManager;
                            android.media.AudioManager.releaseAudioPatch(com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl.this.mAudioPatch);
                            com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl.this.mAudioPatch = null;
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };

        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        private boolean mReleased = false;

        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        private int mOverrideAudioType = 0;

        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        private java.lang.String mOverrideAudioAddress = "";

        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        private java.util.List<android.media.AudioDevicePort> mAudioSink = new java.util.ArrayList();

        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        private android.media.AudioPatch mAudioPatch = null;

        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        private float mCommittedVolume = -1.0f;

        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        private float mSourceVolume = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;

        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        private android.media.tv.TvStreamConfig mActiveConfig = null;

        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        private int mDesiredSamplingRate = 0;

        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        private int mDesiredChannelMask = 1;

        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        private int mDesiredFormat = 1;

        public TvInputHardwareImpl(android.media.tv.TvInputHardwareInfo tvInputHardwareInfo) {
            this.mInfo = tvInputHardwareInfo;
            com.android.server.tv.TvInputHardwareManager.this.mAudioManager.registerAudioPortUpdateListener(this.mAudioListener);
            if (this.mInfo.getAudioType() != 0) {
                synchronized (this.mImplLock) {
                    this.mAudioSource = findAudioDevicePort(this.mInfo.getAudioType(), this.mInfo.getAudioAddress());
                    findAudioSinkFromAudioPolicy(this.mAudioSink);
                }
            }
        }

        private void findAudioSinkFromAudioPolicy(java.util.List<android.media.AudioDevicePort> list) {
            list.clear();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            android.media.AudioManager unused = com.android.server.tv.TvInputHardwareManager.this.mAudioManager;
            if (android.media.AudioManager.listAudioDevicePorts(arrayList) != 0) {
                return;
            }
            int devicesForStream = com.android.server.tv.TvInputHardwareManager.this.mAudioManager.getDevicesForStream(3);
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                android.media.AudioDevicePort audioDevicePort = (android.media.AudioDevicePort) it.next();
                if ((audioDevicePort.type() & devicesForStream) != 0 && !android.media.AudioSystem.isInputDevice(audioDevicePort.type())) {
                    list.add(audioDevicePort);
                }
            }
        }

        private android.media.AudioDevicePort findAudioDevicePort(int i, java.lang.String str) {
            if (i == 0) {
                return null;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            android.media.AudioManager unused = com.android.server.tv.TvInputHardwareManager.this.mAudioManager;
            if (android.media.AudioManager.listAudioDevicePorts(arrayList) != 0) {
                return null;
            }
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                android.media.AudioDevicePort audioDevicePort = (android.media.AudioDevicePort) it.next();
                if (audioDevicePort.type() == i && audioDevicePort.address().equals(str)) {
                    return audioDevicePort;
                }
            }
            return null;
        }

        public void release() {
            synchronized (this.mImplLock) {
                try {
                    com.android.server.tv.TvInputHardwareManager.this.mAudioManager.unregisterAudioPortUpdateListener(this.mAudioListener);
                    if (this.mAudioPatch != null) {
                        android.media.AudioManager unused = com.android.server.tv.TvInputHardwareManager.this.mAudioManager;
                        android.media.AudioManager.releaseAudioPatch(this.mAudioPatch);
                        this.mAudioPatch = null;
                    }
                    this.mReleased = true;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public boolean setSurface(android.view.Surface surface, android.media.tv.TvStreamConfig tvStreamConfig) throws android.os.RemoteException {
            int i;
            int i2;
            synchronized (this.mImplLock) {
                try {
                    if (this.mReleased) {
                        throw new java.lang.IllegalStateException("Device already released.");
                    }
                    boolean z = true;
                    if (surface == null) {
                        if (this.mActiveConfig == null) {
                            return true;
                        }
                        i2 = com.android.server.tv.TvInputHardwareManager.this.mHal.removeStream(this.mInfo.getDeviceId(), this.mActiveConfig);
                        this.mActiveConfig = null;
                    } else {
                        if (tvStreamConfig == null) {
                            return false;
                        }
                        if (this.mActiveConfig != null && !tvStreamConfig.equals(this.mActiveConfig)) {
                            i = com.android.server.tv.TvInputHardwareManager.this.mHal.removeStream(this.mInfo.getDeviceId(), this.mActiveConfig);
                            if (i != 0) {
                                this.mActiveConfig = null;
                            }
                        } else {
                            i = 0;
                        }
                        if (i != 0) {
                            i2 = i;
                        } else {
                            i2 = com.android.server.tv.TvInputHardwareManager.this.mHal.addOrUpdateStream(this.mInfo.getDeviceId(), surface, tvStreamConfig);
                            if (i2 == 0) {
                                this.mActiveConfig = tvStreamConfig;
                            }
                        }
                    }
                    updateAudioConfigLocked();
                    if (i2 != 0) {
                        z = false;
                    }
                    return z;
                } finally {
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:31:0x00c4  */
        /* JADX WARN: Removed duplicated region for block: B:66:0x01ae  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x01eb  */
        /* JADX WARN: Removed duplicated region for block: B:79:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:82:0x0152  */
        /* JADX WARN: Removed duplicated region for block: B:85:0x0178  */
        /* JADX WARN: Removed duplicated region for block: B:91:0x019f  */
        /* JADX WARN: Removed duplicated region for block: B:93:0x01a4  */
        /* JADX WARN: Removed duplicated region for block: B:94:0x018d A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:96:0x0158  */
        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void updateAudioConfigLocked() {
            android.media.AudioGainConfig audioGainConfig;
            android.media.AudioPortConfig activeConfig;
            boolean z;
            android.media.AudioPortConfig audioPortConfig;
            int i;
            int length;
            int i2;
            int i3;
            int i4;
            char c;
            android.media.AudioGain audioGain;
            int maxValue;
            boolean updateAudioSinkLocked = updateAudioSinkLocked();
            boolean updateAudioSourceLocked = updateAudioSourceLocked();
            if (this.mAudioSource == null || this.mAudioSink.isEmpty() || this.mActiveConfig == null) {
                if (this.mAudioPatch != null) {
                    android.media.AudioManager unused = com.android.server.tv.TvInputHardwareManager.this.mAudioManager;
                    android.media.AudioManager.releaseAudioPatch(this.mAudioPatch);
                    this.mAudioPatch = null;
                    return;
                }
                return;
            }
            com.android.server.tv.TvInputHardwareManager.this.updateVolume();
            float mediaStreamVolume = this.mSourceVolume * com.android.server.tv.TvInputHardwareManager.this.getMediaStreamVolume();
            int i5 = 1;
            if (this.mAudioSource.gains().length > 0 && mediaStreamVolume != this.mCommittedVolume) {
                android.media.AudioGain[] gains = this.mAudioSource.gains();
                int length2 = gains.length;
                int i6 = 0;
                while (true) {
                    if (i6 >= length2) {
                        audioGain = null;
                        break;
                    }
                    audioGain = gains[i6];
                    if ((audioGain.mode() & 1) != 0) {
                        break;
                    } else {
                        i6++;
                    }
                }
                if (audioGain != null) {
                    int maxValue2 = (audioGain.maxValue() - audioGain.minValue()) / audioGain.stepValue();
                    int minValue = audioGain.minValue();
                    if (mediaStreamVolume < 1.0f) {
                        maxValue = minValue + (audioGain.stepValue() * ((int) ((maxValue2 * mediaStreamVolume) + 0.5d)));
                    } else {
                        maxValue = audioGain.maxValue();
                    }
                    audioGainConfig = audioGain.buildConfig(1, audioGain.channelMask(), new int[]{maxValue}, 0);
                    activeConfig = this.mAudioSource.activeConfig();
                    java.util.ArrayList arrayList = new java.util.ArrayList();
                    android.media.AudioPatch[] audioPatchArr = {this.mAudioPatch};
                    z = !updateAudioSourceLocked || updateAudioSinkLocked || this.mAudioPatch == null;
                    for (android.media.AudioDevicePort audioDevicePort : this.mAudioSink) {
                        android.media.AudioDevicePortConfig activeConfig2 = audioDevicePort.activeConfig();
                        int i7 = this.mDesiredSamplingRate;
                        int i8 = this.mDesiredChannelMask;
                        int i9 = this.mDesiredFormat;
                        if (activeConfig2 != null) {
                            if (i7 == 0) {
                                i7 = activeConfig2.samplingRate();
                            }
                            if (i8 == i5) {
                                i8 = activeConfig2.channelMask();
                            }
                            if (i9 == i5) {
                                i9 = activeConfig2.format();
                            }
                        }
                        if (activeConfig2 == null || activeConfig2.samplingRate() != i7 || activeConfig2.channelMask() != i8 || activeConfig2.format() != i9) {
                            if (!com.android.server.tv.TvInputHardwareManager.intArrayContains(audioDevicePort.samplingRates(), i7) && audioDevicePort.samplingRates().length > 0) {
                                i7 = audioDevicePort.samplingRates()[0];
                            }
                            if (!com.android.server.tv.TvInputHardwareManager.intArrayContains(audioDevicePort.channelMasks(), i8)) {
                                i8 = 1;
                            }
                            if (!com.android.server.tv.TvInputHardwareManager.intArrayContains(audioDevicePort.formats(), i9)) {
                                i9 = 1;
                            }
                            activeConfig2 = audioDevicePort.buildConfig(i7, i8, i9, (android.media.AudioGainConfig) null);
                            z = true;
                        }
                        arrayList.add(activeConfig2);
                        i5 = 1;
                    }
                    audioPortConfig = (android.media.AudioPortConfig) arrayList.get(0);
                    if (activeConfig != null || audioGainConfig != null) {
                        if (!com.android.server.tv.TvInputHardwareManager.intArrayContains(this.mAudioSource.samplingRates(), audioPortConfig.samplingRate())) {
                            i = audioPortConfig.samplingRate();
                        } else if (this.mAudioSource.samplingRates().length <= 0) {
                            i = 0;
                        } else {
                            i = this.mAudioSource.samplingRates()[0];
                        }
                        int[] channelMasks = this.mAudioSource.channelMasks();
                        length = channelMasks.length;
                        i2 = 0;
                        while (true) {
                            if (i2 < length) {
                                i3 = 1;
                                break;
                            }
                            i3 = channelMasks[i2];
                            if (android.media.AudioFormat.channelCountFromOutChannelMask(audioPortConfig.channelMask()) == android.media.AudioFormat.channelCountFromInChannelMask(i3)) {
                                break;
                            } else {
                                i2++;
                            }
                        }
                        if (com.android.server.tv.TvInputHardwareManager.intArrayContains(this.mAudioSource.formats(), audioPortConfig.format())) {
                            i4 = 1;
                        } else {
                            i4 = audioPortConfig.format();
                        }
                        activeConfig = this.mAudioSource.buildConfig(i, i3, i4, audioGainConfig);
                        z = true;
                    }
                    if (z) {
                        this.mCommittedVolume = mediaStreamVolume;
                        if (this.mAudioPatch == null || updateAudioSinkLocked || updateAudioSourceLocked) {
                            if (this.mAudioPatch == null) {
                                c = 0;
                            } else {
                                android.media.AudioManager unused2 = com.android.server.tv.TvInputHardwareManager.this.mAudioManager;
                                android.media.AudioManager.releaseAudioPatch(this.mAudioPatch);
                                c = 0;
                                audioPatchArr[0] = null;
                            }
                            android.media.AudioManager unused3 = com.android.server.tv.TvInputHardwareManager.this.mAudioManager;
                            android.media.AudioPortConfig[] audioPortConfigArr = new android.media.AudioPortConfig[1];
                            audioPortConfigArr[c] = activeConfig;
                            android.media.AudioManager.createAudioPatch(audioPatchArr, audioPortConfigArr, (android.media.AudioPortConfig[]) arrayList.toArray(new android.media.AudioPortConfig[arrayList.size()]));
                            this.mAudioPatch = audioPatchArr[c];
                        }
                    }
                    if (audioGainConfig == null) {
                        android.media.AudioManager unused4 = com.android.server.tv.TvInputHardwareManager.this.mAudioManager;
                        android.media.AudioManager.setAudioPortGain(this.mAudioSource, audioGainConfig);
                        return;
                    }
                    return;
                }
                android.util.Slog.w(com.android.server.tv.TvInputHardwareManager.TAG, "No audio source gain with MODE_JOINT support exists.");
            }
            audioGainConfig = null;
            activeConfig = this.mAudioSource.activeConfig();
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            android.media.AudioPatch[] audioPatchArr2 = {this.mAudioPatch};
            if (updateAudioSourceLocked) {
            }
            while (r12.hasNext()) {
            }
            audioPortConfig = (android.media.AudioPortConfig) arrayList2.get(0);
            if (activeConfig != null) {
            }
            if (!com.android.server.tv.TvInputHardwareManager.intArrayContains(this.mAudioSource.samplingRates(), audioPortConfig.samplingRate())) {
            }
            int[] channelMasks2 = this.mAudioSource.channelMasks();
            length = channelMasks2.length;
            i2 = 0;
            while (true) {
                if (i2 < length) {
                }
                i2++;
            }
            if (com.android.server.tv.TvInputHardwareManager.intArrayContains(this.mAudioSource.formats(), audioPortConfig.format())) {
            }
            activeConfig = this.mAudioSource.buildConfig(i, i3, i4, audioGainConfig);
            z = true;
            if (z) {
            }
            if (audioGainConfig == null) {
            }
        }

        public void setStreamVolume(float f) throws android.os.RemoteException {
            synchronized (this.mImplLock) {
                try {
                    if (this.mReleased) {
                        throw new java.lang.IllegalStateException("Device already released.");
                    }
                    this.mSourceVolume = f;
                    updateAudioConfigLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean startCapture(android.view.Surface surface, android.media.tv.TvStreamConfig tvStreamConfig) {
            synchronized (this.mImplLock) {
                try {
                    if (this.mReleased) {
                        return false;
                    }
                    if (surface == null || tvStreamConfig == null) {
                        return false;
                    }
                    if (tvStreamConfig.getType() != 2) {
                        return false;
                    }
                    return com.android.server.tv.TvInputHardwareManager.this.mHal.addOrUpdateStream(this.mInfo.getDeviceId(), surface, tvStreamConfig) == 0;
                } finally {
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean stopCapture(android.media.tv.TvStreamConfig tvStreamConfig) {
            synchronized (this.mImplLock) {
                try {
                    if (this.mReleased) {
                        return false;
                    }
                    if (tvStreamConfig == null) {
                        return false;
                    }
                    return com.android.server.tv.TvInputHardwareManager.this.mHal.removeStream(this.mInfo.getDeviceId(), tvStreamConfig) == 0;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        private boolean updateAudioSourceLocked() {
            if (this.mInfo.getAudioType() == 0) {
                return false;
            }
            android.media.AudioDevicePort audioDevicePort = this.mAudioSource;
            this.mAudioSource = findAudioDevicePort(this.mInfo.getAudioType(), this.mInfo.getAudioAddress());
            return this.mAudioSource == null ? audioDevicePort != null : !this.mAudioSource.equals(audioDevicePort);
        }

        @com.android.internal.annotations.GuardedBy({"mImplLock"})
        private boolean updateAudioSinkLocked() {
            if (this.mInfo.getAudioType() == 0) {
                return false;
            }
            java.util.List<android.media.AudioDevicePort> list = this.mAudioSink;
            this.mAudioSink = new java.util.ArrayList();
            if (this.mOverrideAudioType == 0) {
                findAudioSinkFromAudioPolicy(this.mAudioSink);
            } else {
                android.media.AudioDevicePort findAudioDevicePort = findAudioDevicePort(this.mOverrideAudioType, this.mOverrideAudioAddress);
                if (findAudioDevicePort != null) {
                    this.mAudioSink.add(findAudioDevicePort);
                }
            }
            if (this.mAudioSink.size() != list.size()) {
                return true;
            }
            list.removeAll(this.mAudioSink);
            return !list.isEmpty();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleAudioSinkUpdated() {
            synchronized (this.mImplLock) {
                updateAudioConfigLocked();
            }
        }

        public void overrideAudioSink(int i, java.lang.String str, int i2, int i3, int i4) {
            synchronized (this.mImplLock) {
                this.mOverrideAudioType = i;
                this.mOverrideAudioAddress = str;
                this.mDesiredSamplingRate = i2;
                this.mDesiredChannelMask = i3;
                this.mDesiredFormat = i4;
                updateAudioConfigLocked();
            }
        }

        public void onMediaStreamVolumeChanged() {
            synchronized (this.mImplLock) {
                updateAudioConfigLocked();
            }
        }
    }

    private class ListenerHandler extends android.os.Handler {
        private static final int HARDWARE_DEVICE_ADDED = 2;
        private static final int HARDWARE_DEVICE_REMOVED = 3;
        private static final int HDMI_DEVICE_ADDED = 4;
        private static final int HDMI_DEVICE_REMOVED = 5;
        private static final int HDMI_DEVICE_UPDATED = 6;
        private static final int STATE_CHANGED = 1;
        private static final int TVINPUT_INFO_ADDED = 7;
        private static final int TV_MESSAGE_RECEIVED = 8;

        private ListenerHandler() {
        }

        @Override // android.os.Handler
        public final void handleMessage(android.os.Message message) {
            java.lang.String str;
            switch (message.what) {
                case 1:
                    com.android.server.tv.TvInputHardwareManager.this.mListener.onStateChanged((java.lang.String) message.obj, message.arg1);
                    return;
                case 2:
                    com.android.server.tv.TvInputHardwareManager.this.mListener.onHardwareDeviceAdded((android.media.tv.TvInputHardwareInfo) message.obj);
                    return;
                case 3:
                    com.android.server.tv.TvInputHardwareManager.this.mListener.onHardwareDeviceRemoved((android.media.tv.TvInputHardwareInfo) message.obj);
                    return;
                case 4:
                    com.android.server.tv.TvInputHardwareManager.this.mListener.onHdmiDeviceAdded((android.hardware.hdmi.HdmiDeviceInfo) message.obj);
                    return;
                case 5:
                    com.android.server.tv.TvInputHardwareManager.this.mListener.onHdmiDeviceRemoved((android.hardware.hdmi.HdmiDeviceInfo) message.obj);
                    return;
                case 6:
                    android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo = (android.hardware.hdmi.HdmiDeviceInfo) message.obj;
                    synchronized (com.android.server.tv.TvInputHardwareManager.this.mLock) {
                        str = (java.lang.String) com.android.server.tv.TvInputHardwareManager.this.mHdmiInputIdMap.get(hdmiDeviceInfo.getId());
                    }
                    if (str != null) {
                        com.android.server.tv.TvInputHardwareManager.this.mListener.onHdmiDeviceUpdated(str, hdmiDeviceInfo);
                        return;
                    } else {
                        android.util.Slog.w(com.android.server.tv.TvInputHardwareManager.TAG, "Could not resolve input ID matching the device info; ignoring.");
                        return;
                    }
                case 7:
                    int i = message.arg1;
                    int i2 = message.arg2;
                    com.android.server.tv.TvInputHardwareManager.Connection connection = (com.android.server.tv.TvInputHardwareManager.Connection) message.obj;
                    int configsLengthLocked = connection.getConfigsLengthLocked();
                    int inputStateLocked = connection.getInputStateLocked();
                    java.lang.String str2 = (java.lang.String) com.android.server.tv.TvInputHardwareManager.this.mHardwareInputIdMap.get(i);
                    if (str2 != null) {
                        synchronized (com.android.server.tv.TvInputHardwareManager.this.mLock) {
                            try {
                                if (connection.updateCableConnectionStatusLocked(i2)) {
                                    if (inputStateLocked != connection.getInputStateLocked()) {
                                        com.android.server.tv.TvInputHardwareManager.this.mHandler.obtainMessage(1, connection.getInputStateLocked(), 0, str2).sendToTarget();
                                    }
                                } else {
                                    if ((configsLengthLocked == 0) != (connection.getConfigsLengthLocked() == 0)) {
                                        com.android.server.tv.TvInputHardwareManager.this.mHandler.obtainMessage(1, connection.getInputStateLocked(), 0, str2).sendToTarget();
                                    }
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    return;
                case 8:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    com.android.server.tv.TvInputHardwareManager.this.mListener.onTvMessage((java.lang.String) someArgs.arg1, message.arg1, (android.os.Bundle) someArgs.arg2);
                    someArgs.recycle();
                    return;
                default:
                    android.util.Slog.w(com.android.server.tv.TvInputHardwareManager.TAG, "Unhandled message: " + message);
                    return;
            }
        }
    }

    private final class HdmiHotplugEventListener extends android.hardware.hdmi.IHdmiHotplugEventListener.Stub {
        private HdmiHotplugEventListener() {
        }

        public void onReceived(android.hardware.hdmi.HdmiHotplugEvent hdmiHotplugEvent) {
            int i;
            synchronized (com.android.server.tv.TvInputHardwareManager.this.mLock) {
                try {
                    com.android.server.tv.TvInputHardwareManager.this.mHdmiStateMap.put(hdmiHotplugEvent.getPort(), hdmiHotplugEvent.isConnected());
                    android.media.tv.TvInputHardwareInfo findHardwareInfoForHdmiPortLocked = com.android.server.tv.TvInputHardwareManager.this.findHardwareInfoForHdmiPortLocked(hdmiHotplugEvent.getPort());
                    if (findHardwareInfoForHdmiPortLocked == null) {
                        return;
                    }
                    java.lang.String str = (java.lang.String) com.android.server.tv.TvInputHardwareManager.this.mHardwareInputIdMap.get(findHardwareInfoForHdmiPortLocked.getDeviceId());
                    if (str == null) {
                        return;
                    }
                    if (hdmiHotplugEvent.isConnected()) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    com.android.server.tv.TvInputHardwareManager.this.mHandler.obtainMessage(1, i, 0, str).sendToTarget();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    private final class HdmiDeviceEventListener extends android.hardware.hdmi.IHdmiDeviceEventListener.Stub {
        private HdmiDeviceEventListener() {
        }

        public void onStatusChanged(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo, int i) {
            int i2;
            android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo2;
            if (hdmiDeviceInfo.isSourceType()) {
                synchronized (com.android.server.tv.TvInputHardwareManager.this.mLock) {
                    try {
                        switch (i) {
                            case 1:
                                if (findHdmiDeviceInfo(hdmiDeviceInfo.getId()) == null) {
                                    com.android.server.tv.TvInputHardwareManager.this.mHdmiDeviceList.add(hdmiDeviceInfo);
                                    i2 = 4;
                                    hdmiDeviceInfo2 = hdmiDeviceInfo;
                                    break;
                                } else {
                                    android.util.Slog.w(com.android.server.tv.TvInputHardwareManager.TAG, "The list already contains " + hdmiDeviceInfo + "; ignoring.");
                                    return;
                                }
                            case 2:
                                if (!com.android.server.tv.TvInputHardwareManager.this.mHdmiDeviceList.remove(findHdmiDeviceInfo(hdmiDeviceInfo.getId()))) {
                                    android.util.Slog.w(com.android.server.tv.TvInputHardwareManager.TAG, "The list doesn't contain " + hdmiDeviceInfo + "; ignoring.");
                                    return;
                                }
                                i2 = 5;
                                hdmiDeviceInfo2 = hdmiDeviceInfo;
                                break;
                            case 3:
                                if (!com.android.server.tv.TvInputHardwareManager.this.mHdmiDeviceList.remove(findHdmiDeviceInfo(hdmiDeviceInfo.getId()))) {
                                    android.util.Slog.w(com.android.server.tv.TvInputHardwareManager.TAG, "The list doesn't contain " + hdmiDeviceInfo + "; ignoring.");
                                    return;
                                }
                                com.android.server.tv.TvInputHardwareManager.this.mHdmiDeviceList.add(hdmiDeviceInfo);
                                i2 = 6;
                                hdmiDeviceInfo2 = hdmiDeviceInfo;
                                break;
                            default:
                                hdmiDeviceInfo2 = null;
                                i2 = 0;
                                break;
                        }
                        android.os.Message obtainMessage = com.android.server.tv.TvInputHardwareManager.this.mHandler.obtainMessage(i2, 0, 0, hdmiDeviceInfo2);
                        if (com.android.server.tv.TvInputHardwareManager.this.findHardwareInfoForHdmiPortLocked(hdmiDeviceInfo.getPortId()) != null) {
                            obtainMessage.sendToTarget();
                        } else {
                            com.android.server.tv.TvInputHardwareManager.this.mPendingHdmiDeviceEvents.add(obtainMessage);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        private android.hardware.hdmi.HdmiDeviceInfo findHdmiDeviceInfo(int i) {
            for (android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo : com.android.server.tv.TvInputHardwareManager.this.mHdmiDeviceList) {
                if (hdmiDeviceInfo.getId() == i) {
                    return hdmiDeviceInfo;
                }
            }
            return null;
        }
    }

    private final class HdmiSystemAudioModeChangeListener extends android.hardware.hdmi.IHdmiSystemAudioModeChangeListener.Stub {
        private HdmiSystemAudioModeChangeListener() {
        }

        public void onStatusChanged(boolean z) throws android.os.RemoteException {
            synchronized (com.android.server.tv.TvInputHardwareManager.this.mLock) {
                for (int i = 0; i < com.android.server.tv.TvInputHardwareManager.this.mConnections.size(); i++) {
                    try {
                        com.android.server.tv.TvInputHardwareManager.TvInputHardwareImpl hardwareImplLocked = ((com.android.server.tv.TvInputHardwareManager.Connection) com.android.server.tv.TvInputHardwareManager.this.mConnections.valueAt(i)).getHardwareImplLocked();
                        if (hardwareImplLocked != null) {
                            hardwareImplLocked.handleAudioSinkUpdated();
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }
}
