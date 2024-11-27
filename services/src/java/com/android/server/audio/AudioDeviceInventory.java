package com.android.server.audio;

/* loaded from: classes.dex */
public class AudioDeviceInventory {
    private static final java.util.Set<java.lang.Integer> BECOMING_NOISY_INTENT_DEVICES_SET;
    static final int[] CAPTURE_PRESETS;
    private static final java.lang.String CONNECT_INTENT_KEY_ADDRESS = "address";
    private static final java.lang.String CONNECT_INTENT_KEY_DEVICE_CLASS = "class";
    private static final java.lang.String CONNECT_INTENT_KEY_HAS_CAPTURE = "hasCapture";
    private static final java.lang.String CONNECT_INTENT_KEY_HAS_MIDI = "hasMIDI";
    private static final java.lang.String CONNECT_INTENT_KEY_HAS_PLAYBACK = "hasPlayback";
    private static final java.lang.String CONNECT_INTENT_KEY_PORT_NAME = "portName";
    private static final java.lang.String CONNECT_INTENT_KEY_STATE = "state";
    private static final int MAX_SETTINGS_LENGTH_PER_STRING = 32768;
    private static final java.lang.String SETTING_DEVICE_SEPARATOR = "\\|";
    private static final java.lang.String SETTING_DEVICE_SEPARATOR_CHAR = "|";
    private static final java.lang.String TAG = "AS.AudioDeviceInventory";
    private static final java.lang.String mMetricsId = "audio.device.";

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private final android.util.ArrayMap<java.lang.Integer, java.lang.String> mApmConnectedDevices;
    private final android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.Integer>, java.util.List<android.media.AudioDeviceAttributes>> mAppliedPresetRoles;
    private final android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.Integer>, java.util.List<android.media.AudioDeviceAttributes>> mAppliedPresetRolesInt;
    private final android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.Integer>, java.util.List<android.media.AudioDeviceAttributes>> mAppliedStrategyRoles;
    private final android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.Integer>, java.util.List<android.media.AudioDeviceAttributes>> mAppliedStrategyRolesInt;

    @android.annotation.NonNull
    private final com.android.server.audio.AudioSystemAdapter mAudioSystem;
    final boolean mBluetoothDualModeEnabled;

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private final java.util.LinkedHashMap<java.lang.String, com.android.server.audio.AudioDeviceInventory.DeviceInfo> mConnectedDevices;
    final android.media.AudioRoutesInfo mCurAudioRoutes;
    final android.os.RemoteCallbackList<android.media.ICapturePresetDevicesRoleDispatcher> mDevRoleCapturePresetDispatchers;

    @android.annotation.NonNull
    private com.android.server.audio.AudioDeviceBroker mDeviceBroker;

    @com.android.internal.annotations.GuardedBy({"mDeviceInventoryLock"})
    private final java.util.LinkedHashMap<android.util.Pair<java.lang.Integer, java.lang.String>, com.android.server.audio.AdiDeviceState> mDeviceInventory;
    private final java.lang.Object mDeviceInventoryLock;
    private final java.lang.Object mDevicesLock;
    final android.os.RemoteCallbackList<android.media.IStrategyNonDefaultDevicesDispatcher> mNonDefDevDispatchers;
    private final android.util.ArrayMap<java.lang.Integer, java.util.List<android.media.AudioDeviceAttributes>> mNonDefaultDevices;
    final android.os.RemoteCallbackList<android.media.IStrategyPreferredDevicesDispatcher> mPrefDevDispatchers;
    private final android.util.ArrayMap<java.lang.Integer, java.util.List<android.media.AudioDeviceAttributes>> mPreferredDevices;
    private final android.util.ArrayMap<java.lang.Integer, java.util.List<android.media.AudioDeviceAttributes>> mPreferredDevicesForCapturePreset;
    final android.os.RemoteCallbackList<android.media.IAudioRoutesObserver> mRoutesObservers;
    final java.util.List<android.media.audiopolicy.AudioProductStrategy> mStrategies;
    private static final int MAX_DEVICE_INVENTORY_ENTRIES = 32768 / com.android.server.audio.AdiDeviceState.getPeristedMaxSize();
    static final java.util.Set<java.lang.Integer> DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET = new java.util.HashSet();

    interface AudioSystemInterface {
        int deviceRoleAction(int i, int i2, @android.annotation.Nullable java.util.List<android.media.AudioDeviceAttributes> list);
    }

    static {
        DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET.add(4);
        DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET.add(8);
        DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET.add(131072);
        DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET.addAll(android.media.AudioSystem.DEVICE_OUT_ALL_USB_SET);
        CAPTURE_PRESETS = new int[]{1, 5, 6, 7, 9, 10, 1999};
        BECOMING_NOISY_INTENT_DEVICES_SET = new java.util.HashSet();
        BECOMING_NOISY_INTENT_DEVICES_SET.add(4);
        BECOMING_NOISY_INTENT_DEVICES_SET.add(8);
        BECOMING_NOISY_INTENT_DEVICES_SET.add(1024);
        BECOMING_NOISY_INTENT_DEVICES_SET.add(2048);
        BECOMING_NOISY_INTENT_DEVICES_SET.add(131072);
        BECOMING_NOISY_INTENT_DEVICES_SET.add(134217728);
        BECOMING_NOISY_INTENT_DEVICES_SET.add(536870912);
        BECOMING_NOISY_INTENT_DEVICES_SET.add(536870914);
        BECOMING_NOISY_INTENT_DEVICES_SET.addAll(android.media.AudioSystem.DEVICE_OUT_ALL_A2DP_SET);
        BECOMING_NOISY_INTENT_DEVICES_SET.addAll(android.media.AudioSystem.DEVICE_OUT_ALL_USB_SET);
        BECOMING_NOISY_INTENT_DEVICES_SET.addAll(android.media.AudioSystem.DEVICE_OUT_ALL_BLE_SET);
    }

    java.util.Collection<com.android.server.audio.AdiDeviceState> getImmutableDeviceInventory() {
        java.util.ArrayList arrayList;
        synchronized (this.mDeviceInventoryLock) {
            arrayList = new java.util.ArrayList(this.mDeviceInventory.values());
        }
        return arrayList;
    }

    void addOrUpdateDeviceSAStateInInventory(com.android.server.audio.AdiDeviceState adiDeviceState) {
        synchronized (this.mDeviceInventoryLock) {
            this.mDeviceInventory.merge(adiDeviceState.getDeviceId(), adiDeviceState, new java.util.function.BiFunction() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda22
                @Override // java.util.function.BiFunction
                public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.audio.AdiDeviceState lambda$addOrUpdateDeviceSAStateInInventory$0;
                    lambda$addOrUpdateDeviceSAStateInInventory$0 = com.android.server.audio.AudioDeviceInventory.lambda$addOrUpdateDeviceSAStateInInventory$0((com.android.server.audio.AdiDeviceState) obj, (com.android.server.audio.AdiDeviceState) obj2);
                    return lambda$addOrUpdateDeviceSAStateInInventory$0;
                }
            });
            checkDeviceInventorySize_l();
        }
        this.mDeviceBroker.postSynchronizeAdiDevicesInInventory(adiDeviceState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.audio.AdiDeviceState lambda$addOrUpdateDeviceSAStateInInventory$0(com.android.server.audio.AdiDeviceState adiDeviceState, com.android.server.audio.AdiDeviceState adiDeviceState2) {
        adiDeviceState.setHasHeadTracker(adiDeviceState2.hasHeadTracker());
        adiDeviceState.setHeadTrackerEnabled(adiDeviceState2.isHeadTrackerEnabled());
        adiDeviceState.setSAEnabled(adiDeviceState2.isSAEnabled());
        return adiDeviceState;
    }

    void addAudioDeviceInInventoryIfNeeded(int i, java.lang.String str, java.lang.String str2, int i2) {
        if (!android.media.AudioSystem.isBluetoothOutDevice(i)) {
            return;
        }
        synchronized (this.mDeviceInventoryLock) {
            try {
                com.android.server.audio.AdiDeviceState findBtDeviceStateForAddress = findBtDeviceStateForAddress(str, i);
                if (findBtDeviceStateForAddress == null && str2 != null) {
                    findBtDeviceStateForAddress = findBtDeviceStateForAddress(str2, i);
                }
                if (findBtDeviceStateForAddress != null) {
                    if (findBtDeviceStateForAddress.getAudioDeviceCategory() != i2) {
                        findBtDeviceStateForAddress.setAudioDeviceCategory(i2);
                        this.mDeviceBroker.postUpdatedAdiDeviceState(findBtDeviceStateForAddress);
                        this.mDeviceBroker.postPersistAudioDeviceSettings();
                    }
                    this.mDeviceBroker.postSynchronizeAdiDevicesInInventory(findBtDeviceStateForAddress);
                    return;
                }
                com.android.server.audio.AdiDeviceState adiDeviceState = new com.android.server.audio.AdiDeviceState(android.media.AudioDeviceInfo.convertInternalDeviceToDeviceType(i), i, str);
                adiDeviceState.setAudioDeviceCategory(i2);
                this.mDeviceInventory.put(adiDeviceState.getDeviceId(), adiDeviceState);
                checkDeviceInventorySize_l();
                this.mDeviceBroker.postUpdatedAdiDeviceState(adiDeviceState);
                this.mDeviceBroker.postPersistAudioDeviceSettings();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void addOrUpdateAudioDeviceCategoryInInventory(com.android.server.audio.AdiDeviceState adiDeviceState) {
        com.android.server.audio.AdiDeviceState merge;
        final java.util.concurrent.atomic.AtomicBoolean atomicBoolean = new java.util.concurrent.atomic.AtomicBoolean(false);
        synchronized (this.mDeviceInventoryLock) {
            try {
                if (android.media.audio.Flags.automaticBtDeviceType() && adiDeviceState.updateAudioDeviceCategory()) {
                    atomicBoolean.set(true);
                }
                merge = this.mDeviceInventory.merge(adiDeviceState.getDeviceId(), adiDeviceState, new java.util.function.BiFunction() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda32
                    @Override // java.util.function.BiFunction
                    public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                        com.android.server.audio.AdiDeviceState lambda$addOrUpdateAudioDeviceCategoryInInventory$1;
                        lambda$addOrUpdateAudioDeviceCategoryInInventory$1 = com.android.server.audio.AudioDeviceInventory.lambda$addOrUpdateAudioDeviceCategoryInInventory$1(atomicBoolean, (com.android.server.audio.AdiDeviceState) obj, (com.android.server.audio.AdiDeviceState) obj2);
                        return lambda$addOrUpdateAudioDeviceCategoryInInventory$1;
                    }
                });
                checkDeviceInventorySize_l();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (atomicBoolean.get()) {
            this.mDeviceBroker.postUpdatedAdiDeviceState(merge);
        }
        this.mDeviceBroker.postSynchronizeAdiDevicesInInventory(merge);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.audio.AdiDeviceState lambda$addOrUpdateAudioDeviceCategoryInInventory$1(java.util.concurrent.atomic.AtomicBoolean atomicBoolean, com.android.server.audio.AdiDeviceState adiDeviceState, com.android.server.audio.AdiDeviceState adiDeviceState2) {
        if (adiDeviceState.getAudioDeviceCategory() != adiDeviceState2.getAudioDeviceCategory()) {
            adiDeviceState.setAudioDeviceCategory(adiDeviceState2.getAudioDeviceCategory());
            atomicBoolean.set(true);
        }
        return adiDeviceState;
    }

    void addAudioDeviceWithCategoryInInventoryIfNeeded(@android.annotation.NonNull java.lang.String str, int i) {
        addAudioDeviceInInventoryIfNeeded(536870912, str, "", i);
        addAudioDeviceInInventoryIfNeeded(128, str, "", i);
    }

    int getAndUpdateBtAdiDeviceStateCategoryForAddress(@android.annotation.NonNull java.lang.String str) {
        int i;
        boolean z;
        com.android.server.audio.AdiDeviceState findBtDeviceStateForAddress = findBtDeviceStateForAddress(str, 536870912);
        if (findBtDeviceStateForAddress == null) {
            i = 0;
            z = false;
        } else {
            addOrUpdateAudioDeviceCategoryInInventory(findBtDeviceStateForAddress);
            i = findBtDeviceStateForAddress.getAudioDeviceCategory();
            z = true;
        }
        com.android.server.audio.AdiDeviceState findBtDeviceStateForAddress2 = findBtDeviceStateForAddress(str, 128);
        if (findBtDeviceStateForAddress2 != null) {
            addOrUpdateAudioDeviceCategoryInInventory(findBtDeviceStateForAddress2);
            int audioDeviceCategory = findBtDeviceStateForAddress2.getAudioDeviceCategory();
            if (z && audioDeviceCategory != i) {
                android.util.Log.w(TAG, "Found different audio device category for A2DP and BLE profiles with address " + str);
            }
            return audioDeviceCategory;
        }
        return i;
    }

    boolean isBluetoothAudioDeviceCategoryFixed(@android.annotation.NonNull java.lang.String str) {
        com.android.server.audio.AdiDeviceState findBtDeviceStateForAddress = findBtDeviceStateForAddress(str, 536870912);
        if (findBtDeviceStateForAddress != null) {
            return findBtDeviceStateForAddress.isBtDeviceCategoryFixed();
        }
        com.android.server.audio.AdiDeviceState findBtDeviceStateForAddress2 = findBtDeviceStateForAddress(str, 128);
        if (findBtDeviceStateForAddress2 != null) {
            return findBtDeviceStateForAddress2.isBtDeviceCategoryFixed();
        }
        return false;
    }

    void onSynchronizeAdiDevicesInInventory(com.android.server.audio.AdiDeviceState adiDeviceState) {
        synchronized (this.mDevicesLock) {
            synchronized (this.mDeviceInventoryLock) {
                try {
                    boolean synchronizeBleDeviceInInventory = synchronizeBleDeviceInInventory(adiDeviceState) | false;
                    if (android.media.audio.Flags.automaticBtDeviceType()) {
                        synchronizeBleDeviceInInventory |= synchronizeDeviceProfilesInInventory(adiDeviceState);
                    }
                    if (synchronizeBleDeviceInInventory) {
                        this.mDeviceBroker.postPersistAudioDeviceSettings();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceInventoryLock"})
    private void checkDeviceInventorySize_l() {
        if (this.mDeviceInventory.size() > MAX_DEVICE_INVENTORY_ENTRIES) {
            java.util.Iterator<java.util.Map.Entry<android.util.Pair<java.lang.Integer, java.lang.String>, com.android.server.audio.AdiDeviceState>> it = this.mDeviceInventory.entrySet().iterator();
            if (it.hasNext()) {
                it.remove();
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock", "mDeviceInventoryLock"})
    private boolean synchronizeBleDeviceInInventory(com.android.server.audio.AdiDeviceState adiDeviceState) {
        for (com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
            if (deviceInfo.mDeviceType == adiDeviceState.getInternalDeviceType()) {
                if (deviceInfo.mDeviceAddress.equals(adiDeviceState.getDeviceAddress())) {
                    for (com.android.server.audio.AdiDeviceState adiDeviceState2 : this.mDeviceInventory.values()) {
                        if (deviceInfo.mDeviceType == adiDeviceState2.getInternalDeviceType() && deviceInfo.mPeerDeviceAddress.equals(adiDeviceState2.getDeviceAddress())) {
                            if (this.mDeviceBroker.isSADevice(adiDeviceState) == this.mDeviceBroker.isSADevice(adiDeviceState2)) {
                                adiDeviceState2.setHasHeadTracker(adiDeviceState.hasHeadTracker());
                                adiDeviceState2.setHeadTrackerEnabled(adiDeviceState.isHeadTrackerEnabled());
                                adiDeviceState2.setSAEnabled(adiDeviceState.isSAEnabled());
                            }
                            adiDeviceState2.setAudioDeviceCategory(adiDeviceState.getAudioDeviceCategory());
                            this.mDeviceBroker.postUpdatedAdiDeviceState(adiDeviceState2);
                            com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("synchronizeBleDeviceInInventory synced device pair ads1=" + adiDeviceState + " ads2=" + adiDeviceState2).printLog(TAG));
                            return true;
                        }
                    }
                }
                if (deviceInfo.mPeerDeviceAddress.equals(adiDeviceState.getDeviceAddress())) {
                    for (com.android.server.audio.AdiDeviceState adiDeviceState3 : this.mDeviceInventory.values()) {
                        if (deviceInfo.mDeviceType == adiDeviceState3.getInternalDeviceType() && deviceInfo.mDeviceAddress.equals(adiDeviceState3.getDeviceAddress())) {
                            if (this.mDeviceBroker.isSADevice(adiDeviceState) == this.mDeviceBroker.isSADevice(adiDeviceState3)) {
                                adiDeviceState3.setHasHeadTracker(adiDeviceState.hasHeadTracker());
                                adiDeviceState3.setHeadTrackerEnabled(adiDeviceState.isHeadTrackerEnabled());
                                adiDeviceState3.setSAEnabled(adiDeviceState.isSAEnabled());
                            }
                            adiDeviceState3.setAudioDeviceCategory(adiDeviceState.getAudioDeviceCategory());
                            this.mDeviceBroker.postUpdatedAdiDeviceState(adiDeviceState3);
                            com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("synchronizeBleDeviceInInventory synced device pair ads1=" + adiDeviceState + " peer ads2=" + adiDeviceState3).printLog(TAG));
                            return true;
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        return false;
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceInventoryLock"})
    private boolean synchronizeDeviceProfilesInInventory(com.android.server.audio.AdiDeviceState adiDeviceState) {
        for (com.android.server.audio.AdiDeviceState adiDeviceState2 : this.mDeviceInventory.values()) {
            if (adiDeviceState.getInternalDeviceType() != adiDeviceState2.getInternalDeviceType() && adiDeviceState.getDeviceAddress().equals(adiDeviceState2.getDeviceAddress())) {
                if (this.mDeviceBroker.isSADevice(adiDeviceState) == this.mDeviceBroker.isSADevice(adiDeviceState2)) {
                    adiDeviceState2.setHasHeadTracker(adiDeviceState.hasHeadTracker());
                    adiDeviceState2.setHeadTrackerEnabled(adiDeviceState.isHeadTrackerEnabled());
                    adiDeviceState2.setSAEnabled(adiDeviceState.isSAEnabled());
                }
                adiDeviceState2.setAudioDeviceCategory(adiDeviceState.getAudioDeviceCategory());
                this.mDeviceBroker.postUpdatedAdiDeviceState(adiDeviceState2);
                com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("synchronizeDeviceProfilesInInventory synced device pair ads1=" + adiDeviceState + " ads2=" + adiDeviceState2).printLog(TAG));
                return true;
            }
        }
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    @android.annotation.Nullable
    public com.android.server.audio.AdiDeviceState findBtDeviceStateForAddress(java.lang.String str, int i) {
        java.util.Set hashSet;
        if (android.media.AudioSystem.isBluetoothA2dpOutDevice(i)) {
            hashSet = android.media.AudioSystem.DEVICE_OUT_ALL_A2DP_SET;
        } else if (android.media.AudioSystem.isBluetoothLeOutDevice(i)) {
            hashSet = android.media.AudioSystem.DEVICE_OUT_ALL_BLE_SET;
        } else if (android.media.AudioSystem.isBluetoothScoOutDevice(i)) {
            hashSet = android.media.AudioSystem.DEVICE_OUT_ALL_SCO_SET;
        } else {
            if (i != 134217728) {
                return null;
            }
            hashSet = new java.util.HashSet();
            hashSet.add(134217728);
        }
        synchronized (this.mDeviceInventoryLock) {
            try {
                java.util.Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    com.android.server.audio.AdiDeviceState adiDeviceState = this.mDeviceInventory.get(new android.util.Pair((java.lang.Integer) it.next(), str));
                    if (adiDeviceState != null) {
                        return adiDeviceState;
                    }
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    com.android.server.audio.AdiDeviceState findDeviceStateForAudioDeviceAttributes(android.media.AudioDeviceAttributes audioDeviceAttributes, int i) {
        boolean isBluetoothDevice = android.media.AudioSystem.isBluetoothDevice(audioDeviceAttributes.getInternalType());
        synchronized (this.mDeviceInventoryLock) {
            try {
                for (com.android.server.audio.AdiDeviceState adiDeviceState : this.mDeviceInventory.values()) {
                    if (adiDeviceState.getDeviceType() != i || (isBluetoothDevice && !audioDeviceAttributes.getAddress().equals(adiDeviceState.getDeviceAddress()))) {
                    }
                    return adiDeviceState;
                }
                return null;
            } finally {
            }
        }
    }

    void clearDeviceInventory() {
        synchronized (this.mDeviceInventoryLock) {
            this.mDeviceInventory.clear();
        }
    }

    AudioDeviceInventory(@android.annotation.NonNull com.android.server.audio.AudioDeviceBroker audioDeviceBroker) {
        this(audioDeviceBroker, com.android.server.audio.AudioSystemAdapter.getDefaultAdapter());
    }

    AudioDeviceInventory(@android.annotation.NonNull com.android.server.audio.AudioSystemAdapter audioSystemAdapter) {
        this(null, audioSystemAdapter);
    }

    private AudioDeviceInventory(@android.annotation.Nullable com.android.server.audio.AudioDeviceBroker audioDeviceBroker, @android.annotation.Nullable com.android.server.audio.AudioSystemAdapter audioSystemAdapter) {
        this.mDevicesLock = new java.lang.Object();
        this.mDeviceInventoryLock = new java.lang.Object();
        this.mDeviceInventory = new java.util.LinkedHashMap<>();
        this.mConnectedDevices = new java.util.LinkedHashMap<java.lang.String, com.android.server.audio.AudioDeviceInventory.DeviceInfo>() { // from class: com.android.server.audio.AudioDeviceInventory.1
            @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
            public com.android.server.audio.AudioDeviceInventory.DeviceInfo put(java.lang.String str, com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo) {
                com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo2 = (com.android.server.audio.AudioDeviceInventory.DeviceInfo) super.put((com.android.server.audio.AudioDeviceInventory.AnonymousClass1) str, (java.lang.String) deviceInfo);
                record("put", true, str, deviceInfo);
                return deviceInfo2;
            }

            @Override // java.util.HashMap, java.util.Map
            public com.android.server.audio.AudioDeviceInventory.DeviceInfo putIfAbsent(java.lang.String str, com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo) {
                com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo2 = (com.android.server.audio.AudioDeviceInventory.DeviceInfo) super.putIfAbsent((com.android.server.audio.AudioDeviceInventory.AnonymousClass1) str, (java.lang.String) deviceInfo);
                if (deviceInfo2 == null) {
                    record("putIfAbsent", true, str, deviceInfo);
                }
                return deviceInfo2;
            }

            @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
            public com.android.server.audio.AudioDeviceInventory.DeviceInfo remove(java.lang.Object obj) {
                com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo = (com.android.server.audio.AudioDeviceInventory.DeviceInfo) super.remove(obj);
                if (deviceInfo != null) {
                    record("remove", false, (java.lang.String) obj, deviceInfo);
                }
                return deviceInfo;
            }

            @Override // java.util.HashMap, java.util.Map
            public boolean remove(java.lang.Object obj, java.lang.Object obj2) {
                boolean remove = super.remove(obj, obj2);
                if (remove) {
                    record("remove", false, (java.lang.String) obj, (com.android.server.audio.AudioDeviceInventory.DeviceInfo) obj2);
                }
                return remove;
            }

            private void record(java.lang.String str, boolean z, java.lang.String str2, com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo) {
                new android.media.MediaMetrics.Item(com.android.server.audio.AudioDeviceInventory.mMetricsId + android.media.AudioSystem.getDeviceName(deviceInfo.mDeviceType)).set(android.media.MediaMetrics.Property.ADDRESS, deviceInfo.mDeviceAddress).set(android.media.MediaMetrics.Property.EVENT, str).set(android.media.MediaMetrics.Property.NAME, deviceInfo.mDeviceName).set(android.media.MediaMetrics.Property.STATE, z ? "connected" : "disconnected").record();
            }
        };
        this.mApmConnectedDevices = new android.util.ArrayMap<>();
        this.mPreferredDevices = new android.util.ArrayMap<>();
        this.mNonDefaultDevices = new android.util.ArrayMap<>();
        this.mPreferredDevicesForCapturePreset = new android.util.ArrayMap<>();
        this.mCurAudioRoutes = new android.media.AudioRoutesInfo();
        this.mRoutesObservers = new android.os.RemoteCallbackList<>();
        this.mPrefDevDispatchers = new android.os.RemoteCallbackList<>();
        this.mNonDefDevDispatchers = new android.os.RemoteCallbackList<>();
        this.mDevRoleCapturePresetDispatchers = new android.os.RemoteCallbackList<>();
        this.mAppliedStrategyRoles = new android.util.ArrayMap<>();
        this.mAppliedStrategyRolesInt = new android.util.ArrayMap<>();
        this.mAppliedPresetRoles = new android.util.ArrayMap<>();
        this.mAppliedPresetRolesInt = new android.util.ArrayMap<>();
        this.mDeviceBroker = audioDeviceBroker;
        this.mAudioSystem = audioSystemAdapter;
        this.mStrategies = android.media.audiopolicy.AudioProductStrategy.getAudioProductStrategies();
        this.mBluetoothDualModeEnabled = android.os.SystemProperties.getBoolean("persist.bluetooth.enable_dual_mode_audio", false);
    }

    void setDeviceBroker(@android.annotation.NonNull com.android.server.audio.AudioDeviceBroker audioDeviceBroker) {
        this.mDeviceBroker = audioDeviceBroker;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DeviceInfo {

        @android.annotation.NonNull
        final java.lang.String mDeviceAddress;
        int mDeviceCodecFormat;

        @android.annotation.NonNull
        java.lang.String mDeviceIdentityAddress;

        @android.annotation.NonNull
        final java.lang.String mDeviceName;
        final int mDeviceType;

        @android.annotation.NonNull
        android.util.ArraySet<java.lang.String> mDisabledModes;
        final int mGroupId;

        @android.annotation.NonNull
        java.lang.String mPeerDeviceAddress;

        @android.annotation.NonNull
        java.lang.String mPeerIdentityDeviceAddress;

        DeviceInfo(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2, int i3, java.lang.String str4, java.lang.String str5) {
            this.mDisabledModes = new android.util.ArraySet<>(0);
            this.mDeviceType = i;
            this.mDeviceName = android.text.TextUtils.emptyIfNull(str);
            this.mDeviceAddress = android.text.TextUtils.emptyIfNull(str2);
            this.mDeviceIdentityAddress = android.text.TextUtils.emptyIfNull(str3);
            this.mDeviceCodecFormat = i2;
            this.mGroupId = i3;
            this.mPeerDeviceAddress = android.text.TextUtils.emptyIfNull(str4);
            this.mPeerIdentityDeviceAddress = android.text.TextUtils.emptyIfNull(str5);
        }

        DeviceInfo(int i, java.lang.String str, java.lang.String str2) {
            this(i, str, str2, null, 0);
        }

        DeviceInfo(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, int i2) {
            this(i, str, str2, str3, i2, -1, null, null);
        }

        void setModeDisabled(java.lang.String str) {
            this.mDisabledModes.add(str);
        }

        void setModeEnabled(java.lang.String str) {
            this.mDisabledModes.remove(str);
        }

        boolean isModeEnabled(java.lang.String str) {
            return !this.mDisabledModes.contains(str);
        }

        boolean isOutputOnlyModeEnabled() {
            return isModeEnabled("audio_mode_output_only");
        }

        boolean isDuplexModeEnabled() {
            return isModeEnabled("audio_mode_duplex");
        }

        public java.lang.String toString() {
            return "[DeviceInfo: type:0x" + java.lang.Integer.toHexString(this.mDeviceType) + " (" + android.media.AudioSystem.getDeviceName(this.mDeviceType) + ") name:" + this.mDeviceName + " addr:" + android.media.Utils.anonymizeBluetoothAddress(this.mDeviceType, this.mDeviceAddress) + " identity addr:" + android.media.Utils.anonymizeBluetoothAddress(this.mDeviceType, this.mDeviceIdentityAddress) + " codec: " + java.lang.Integer.toHexString(this.mDeviceCodecFormat) + " group:" + this.mGroupId + " peer addr:" + android.media.Utils.anonymizeBluetoothAddress(this.mDeviceType, this.mPeerDeviceAddress) + " peer identity addr:" + android.media.Utils.anonymizeBluetoothAddress(this.mDeviceType, this.mPeerIdentityDeviceAddress) + " disabled modes: " + this.mDisabledModes + "]";
        }

        @android.annotation.NonNull
        java.lang.String getKey() {
            return makeDeviceListKey(this.mDeviceType, this.mDeviceAddress);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.NonNull
        public static java.lang.String makeDeviceListKey(int i, java.lang.String str) {
            return "0x" + java.lang.Integer.toHexString(i) + ":" + str;
        }
    }

    class WiredDeviceConnectionState {
        public final android.media.AudioDeviceAttributes mAttributes;
        public final java.lang.String mCaller;
        public boolean mForTest = false;
        public final int mState;

        WiredDeviceConnectionState(android.media.AudioDeviceAttributes audioDeviceAttributes, int i, java.lang.String str) {
            this.mAttributes = audioDeviceAttributes;
            this.mState = i;
            this.mCaller = str;
        }
    }

    void dump(final java.io.PrintWriter printWriter, final java.lang.String str) {
        printWriter.println("\n" + str + "BECOMING_NOISY_INTENT_DEVICES_SET=");
        BECOMING_NOISY_INTENT_DEVICES_SET.forEach(new java.util.function.Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.audio.AudioDeviceInventory.lambda$dump$2(printWriter, (java.lang.Integer) obj);
            }
        });
        printWriter.println("\n" + str + "Preferred devices for strategy:");
        this.mPreferredDevices.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.audio.AudioDeviceInventory.lambda$dump$3(printWriter, str, (java.lang.Integer) obj, (java.util.List) obj2);
            }
        });
        printWriter.println("\n" + str + "Non-default devices for strategy:");
        this.mNonDefaultDevices.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda12
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.audio.AudioDeviceInventory.lambda$dump$4(printWriter, str, (java.lang.Integer) obj, (java.util.List) obj2);
            }
        });
        printWriter.println("\n" + str + "Connected devices:");
        this.mConnectedDevices.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda13
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.audio.AudioDeviceInventory.lambda$dump$5(printWriter, str, (java.lang.String) obj, (com.android.server.audio.AudioDeviceInventory.DeviceInfo) obj2);
            }
        });
        printWriter.println("\n" + str + "APM Connected device (A2DP sink only):");
        this.mApmConnectedDevices.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda14
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.audio.AudioDeviceInventory.lambda$dump$6(printWriter, str, (java.lang.Integer) obj, (java.lang.String) obj2);
            }
        });
        printWriter.println("\n" + str + "Preferred devices for capture preset:");
        this.mPreferredDevicesForCapturePreset.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda15
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.audio.AudioDeviceInventory.lambda$dump$7(printWriter, str, (java.lang.Integer) obj, (java.util.List) obj2);
            }
        });
        printWriter.println("\n" + str + "Applied devices roles for strategies (from API):");
        this.mAppliedStrategyRoles.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda16
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.audio.AudioDeviceInventory.lambda$dump$8(printWriter, str, (android.util.Pair) obj, (java.util.List) obj2);
            }
        });
        printWriter.println("\n" + str + "Applied devices roles for strategies (internal):");
        this.mAppliedStrategyRolesInt.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda17
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.audio.AudioDeviceInventory.lambda$dump$9(printWriter, str, (android.util.Pair) obj, (java.util.List) obj2);
            }
        });
        printWriter.println("\n" + str + "Applied devices roles for presets (from API):");
        this.mAppliedPresetRoles.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda18
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.audio.AudioDeviceInventory.lambda$dump$10(printWriter, str, (android.util.Pair) obj, (java.util.List) obj2);
            }
        });
        printWriter.println("\n" + str + "Applied devices roles for presets (internal:");
        this.mAppliedPresetRolesInt.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda19
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.audio.AudioDeviceInventory.lambda$dump$11(printWriter, str, (android.util.Pair) obj, (java.util.List) obj2);
            }
        });
        printWriter.println("\ndevices:\n");
        synchronized (this.mDeviceInventoryLock) {
            try {
                java.util.Iterator<com.android.server.audio.AdiDeviceState> it = this.mDeviceInventory.values().iterator();
                while (it.hasNext()) {
                    printWriter.println("\t" + it.next() + "\n");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$2(java.io.PrintWriter printWriter, java.lang.Integer num) {
        printWriter.print(" 0x" + java.lang.Integer.toHexString(num.intValue()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$3(java.io.PrintWriter printWriter, java.lang.String str, java.lang.Integer num, java.util.List list) {
        printWriter.println("  " + str + "strategy:" + num + " device:" + list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$4(java.io.PrintWriter printWriter, java.lang.String str, java.lang.Integer num, java.util.List list) {
        printWriter.println("  " + str + "strategy:" + num + " device:" + list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$5(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo) {
        printWriter.println("  " + str + deviceInfo.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$6(java.io.PrintWriter printWriter, java.lang.String str, java.lang.Integer num, java.lang.String str2) {
        printWriter.println("  " + str + " type:0x" + java.lang.Integer.toHexString(num.intValue()) + " (" + android.media.AudioSystem.getDeviceName(num.intValue()) + ") addr:" + android.media.Utils.anonymizeBluetoothAddress(num.intValue(), str2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$7(java.io.PrintWriter printWriter, java.lang.String str, java.lang.Integer num, java.util.List list) {
        printWriter.println("  " + str + "capturePreset:" + num + " devices:" + list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$8(java.io.PrintWriter printWriter, java.lang.String str, android.util.Pair pair, java.util.List list) {
        printWriter.println("  " + str + "strategy: " + pair.first + " role:" + pair.second + " devices:" + list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$9(java.io.PrintWriter printWriter, java.lang.String str, android.util.Pair pair, java.util.List list) {
        printWriter.println("  " + str + "strategy: " + pair.first + " role:" + pair.second + " devices:" + list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$10(java.io.PrintWriter printWriter, java.lang.String str, android.util.Pair pair, java.util.List list) {
        printWriter.println("  " + str + "preset: " + pair.first + " role:" + pair.second + " devices:" + list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$11(java.io.PrintWriter printWriter, java.lang.String str, android.util.Pair pair, java.util.List list) {
        printWriter.println("  " + str + "preset: " + pair.first + " role:" + pair.second + " devices:" + list);
    }

    void onRestoreDevices() {
        synchronized (this.mDevicesLock) {
            try {
                for (com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
                    this.mAudioSystem.setDeviceConnectionState(new android.media.AudioDeviceAttributes(deviceInfo.mDeviceType, deviceInfo.mDeviceAddress, deviceInfo.mDeviceName), 1, deviceInfo.mDeviceCodecFormat);
                }
                this.mAppliedStrategyRolesInt.clear();
                this.mAppliedPresetRolesInt.clear();
                applyConnectedDevicesRoles_l();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        reapplyExternalDevicesRoles();
    }

    void reapplyExternalDevicesRoles() {
        synchronized (this.mDevicesLock) {
            this.mAppliedStrategyRoles.clear();
            this.mAppliedPresetRoles.clear();
        }
        synchronized (this.mPreferredDevices) {
            this.mPreferredDevices.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda36
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.audio.AudioDeviceInventory.this.lambda$reapplyExternalDevicesRoles$12((java.lang.Integer) obj, (java.util.List) obj2);
                }
            });
        }
        synchronized (this.mNonDefaultDevices) {
            this.mNonDefaultDevices.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda37
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.audio.AudioDeviceInventory.this.lambda$reapplyExternalDevicesRoles$13((java.lang.Integer) obj, (java.util.List) obj2);
                }
            });
        }
        synchronized (this.mPreferredDevicesForCapturePreset) {
            this.mPreferredDevicesForCapturePreset.forEach(new java.util.function.BiConsumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda38
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.audio.AudioDeviceInventory.this.lambda$reapplyExternalDevicesRoles$14((java.lang.Integer) obj, (java.util.List) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reapplyExternalDevicesRoles$12(java.lang.Integer num, java.util.List list) {
        setPreferredDevicesForStrategy(num.intValue(), list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reapplyExternalDevicesRoles$13(java.lang.Integer num, java.util.List list) {
        addDevicesRoleForStrategy(num.intValue(), 2, list, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reapplyExternalDevicesRoles$14(java.lang.Integer num, java.util.List list) {
        setDevicesRoleForCapturePreset(num.intValue(), 1, list);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void onSetBtActiveDevice(@android.annotation.NonNull com.android.server.audio.AudioDeviceBroker.BtDeviceInfo btDeviceInfo, int i, int i2) {
        java.lang.String address = btDeviceInfo.mDevice.getAddress();
        if (!android.bluetooth.BluetoothAdapter.checkBluetoothAddress(address)) {
            address = "";
        }
        com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("BT connected:" + btDeviceInfo + " codec=" + android.media.AudioSystem.audioFormatToString(i)));
        new android.media.MediaMetrics.Item("audio.device.onSetBtActiveDevice").set(android.media.MediaMetrics.Property.STATUS, java.lang.Integer.valueOf(btDeviceInfo.mProfile)).set(android.media.MediaMetrics.Property.DEVICE, android.media.AudioSystem.getDeviceName(btDeviceInfo.mAudioSystemDevice)).set(android.media.MediaMetrics.Property.ADDRESS, address).set(android.media.MediaMetrics.Property.ENCODING, android.media.AudioSystem.audioFormatToString(i)).set(android.media.MediaMetrics.Property.EVENT, "onSetBtActiveDevice").set(android.media.MediaMetrics.Property.STREAM_TYPE, android.media.AudioSystem.streamToString(i2)).set(android.media.MediaMetrics.Property.STATE, btDeviceInfo.mState == 2 ? "connected" : "disconnected").record();
        synchronized (this.mDevicesLock) {
            try {
                com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo = this.mConnectedDevices.get(com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(btDeviceInfo.mAudioSystemDevice, address));
                boolean z = false;
                boolean z2 = deviceInfo != null;
                boolean z3 = z2 && btDeviceInfo.mState != 2;
                if (!z2 && btDeviceInfo.mState == 2) {
                    z = true;
                }
                switch (btDeviceInfo.mProfile) {
                    case 2:
                        if (z3) {
                            makeA2dpDeviceUnavailableNow(address, deviceInfo.mDeviceCodecFormat);
                            break;
                        } else if (z) {
                            if (btDeviceInfo.mVolume != -1) {
                                this.mDeviceBroker.postSetVolumeIndexOnDevice(3, btDeviceInfo.mVolume * 10, btDeviceInfo.mAudioSystemDevice, "onSetBtActiveDevice");
                            }
                            makeA2dpDeviceAvailable(btDeviceInfo, i, "onSetBtActiveDevice");
                            break;
                        }
                        break;
                    case 11:
                        if (z3) {
                            lambda$disconnectA2dpSink$32(address);
                            break;
                        } else if (z) {
                            makeA2dpSrcAvailable(address);
                            break;
                        }
                        break;
                    case 21:
                        if (z3) {
                            lambda$disconnectHearingAid$34(address);
                            break;
                        } else if (z) {
                            makeHearingAidDeviceAvailable(address, com.android.server.audio.BtHelper.getName(btDeviceInfo.mDevice), i2, "onSetBtActiveDevice");
                            break;
                        }
                        break;
                    case 22:
                    case 26:
                        if (z3) {
                            makeLeAudioDeviceUnavailableNow(address, btDeviceInfo.mAudioSystemDevice, deviceInfo.mDeviceCodecFormat);
                            break;
                        } else if (z) {
                            makeLeAudioDeviceAvailable(btDeviceInfo, i2, i, "onSetBtActiveDevice");
                            break;
                        }
                        break;
                    default:
                        throw new java.lang.IllegalArgumentException("Invalid profile " + android.bluetooth.BluetoothProfile.getProfileName(btDeviceInfo.mProfile));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceBroker.mDeviceStateLock"})
    void onBluetoothDeviceConfigChange(@android.annotation.NonNull com.android.server.audio.AudioDeviceBroker.BtDeviceInfo btDeviceInfo, int i, int i2) {
        boolean z;
        android.media.MediaMetrics.Item item = new android.media.MediaMetrics.Item("audio.device.onBluetoothDeviceConfigChange").set(android.media.MediaMetrics.Property.EVENT, com.android.server.audio.BtHelper.deviceEventToString(i2));
        android.bluetooth.BluetoothDevice bluetoothDevice = btDeviceInfo.mDevice;
        if (bluetoothDevice == null) {
            item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "btDevice null").record();
            return;
        }
        int i3 = btDeviceInfo.mVolume;
        java.lang.String address = bluetoothDevice.getAddress();
        if (!android.bluetooth.BluetoothAdapter.checkBluetoothAddress(address)) {
            address = "";
        }
        com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("onBluetoothDeviceConfigChange addr=" + address + " event=" + com.android.server.audio.BtHelper.deviceEventToString(i2)));
        synchronized (this.mDevicesLock) {
            try {
                boolean z2 = false;
                if (this.mDeviceBroker.hasScheduledA2dpConnection(bluetoothDevice)) {
                    com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("A2dp config change ignored (scheduled connection change)").printSlog(0, TAG));
                    item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "A2dp config change ignored").record();
                    return;
                }
                java.lang.String makeDeviceListKey = com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(128, address);
                com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo = this.mConnectedDevices.get(makeDeviceListKey);
                if (deviceInfo == null) {
                    android.util.Log.e(TAG, "invalid null DeviceInfo in onBluetoothDeviceConfigChange");
                    item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "null DeviceInfo").record();
                    return;
                }
                item.set(android.media.MediaMetrics.Property.ADDRESS, address).set(android.media.MediaMetrics.Property.ENCODING, android.media.AudioSystem.audioFormatToString(i)).set(android.media.MediaMetrics.Property.INDEX, java.lang.Integer.valueOf(i3)).set(android.media.MediaMetrics.Property.NAME, deviceInfo.mDeviceName);
                if (i2 == 0) {
                    if (btDeviceInfo.mProfile == 2 || btDeviceInfo.mProfile == 22 || btDeviceInfo.mProfile == 26) {
                        if (deviceInfo.mDeviceCodecFormat == i) {
                            z = false;
                        } else {
                            deviceInfo.mDeviceCodecFormat = i;
                            this.mConnectedDevices.replace(makeDeviceListKey, deviceInfo);
                            z = true;
                        }
                        if (this.mAudioSystem.handleDeviceConfigChange(btDeviceInfo.mAudioSystemDevice, address, com.android.server.audio.BtHelper.getName(bluetoothDevice), i) != 0) {
                            com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("APM handleDeviceConfigChange failed for A2DP device addr=" + address + " codec=" + android.media.AudioSystem.audioFormatToString(i)).printSlog(1, TAG));
                            setBluetoothActiveDevice(new com.android.server.audio.AudioDeviceBroker.BtDeviceInfo(btDeviceInfo, 0));
                        } else {
                            com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("APM handleDeviceConfigChange success for A2DP device addr=" + address + " codec=" + android.media.AudioSystem.audioFormatToString(i)).printSlog(0, TAG));
                        }
                        z2 = z;
                    }
                    if (!z2) {
                        updateBluetoothPreferredModes_l(bluetoothDevice);
                    }
                }
                item.record();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onMakeA2dpDeviceUnavailableNow(java.lang.String str, int i) {
        synchronized (this.mDevicesLock) {
            makeA2dpDeviceUnavailableNow(str, i);
        }
    }

    void onMakeLeAudioDeviceUnavailableNow(java.lang.String str, int i, int i2) {
        synchronized (this.mDevicesLock) {
            makeLeAudioDeviceUnavailableNow(str, i, i2);
        }
    }

    void onUpdateLeAudioGroupAddresses(int i) {
        synchronized (this.mDevicesLock) {
            try {
                java.util.List<android.util.Pair<java.lang.String, java.lang.String>> arrayList = new java.util.ArrayList<>();
                for (com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
                    if (deviceInfo.mGroupId == i) {
                        if (arrayList.isEmpty()) {
                            arrayList = this.mDeviceBroker.getLeAudioGroupAddresses(i);
                        }
                        if (deviceInfo.mPeerDeviceAddress.equals("")) {
                            java.util.Iterator<android.util.Pair<java.lang.String, java.lang.String>> it = arrayList.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                android.util.Pair<java.lang.String, java.lang.String> next = it.next();
                                if (!((java.lang.String) next.first).equals(deviceInfo.mDeviceAddress)) {
                                    deviceInfo.mPeerDeviceAddress = (java.lang.String) next.first;
                                    deviceInfo.mPeerIdentityDeviceAddress = (java.lang.String) next.second;
                                    break;
                                }
                            }
                        } else if (!arrayList.contains(new android.util.Pair(deviceInfo.mPeerDeviceAddress, deviceInfo.mPeerIdentityDeviceAddress))) {
                            deviceInfo.mPeerDeviceAddress = "";
                            deviceInfo.mPeerIdentityDeviceAddress = "";
                        }
                        if (deviceInfo.mDeviceIdentityAddress.equals("")) {
                            java.util.Iterator<android.util.Pair<java.lang.String, java.lang.String>> it2 = arrayList.iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    android.util.Pair<java.lang.String, java.lang.String> next2 = it2.next();
                                    if (((java.lang.String) next2.first).equals(deviceInfo.mDeviceAddress)) {
                                        deviceInfo.mDeviceIdentityAddress = (java.lang.String) next2.second;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onReportNewRoutes() {
        android.media.AudioRoutesInfo audioRoutesInfo;
        int beginBroadcast = this.mRoutesObservers.beginBroadcast();
        if (beginBroadcast > 0) {
            new android.media.MediaMetrics.Item("audio.device.onReportNewRoutes").set(android.media.MediaMetrics.Property.OBSERVERS, java.lang.Integer.valueOf(beginBroadcast)).record();
            synchronized (this.mCurAudioRoutes) {
                audioRoutesInfo = new android.media.AudioRoutesInfo(this.mCurAudioRoutes);
            }
            while (beginBroadcast > 0) {
                beginBroadcast--;
                try {
                    this.mRoutesObservers.getBroadcastItem(beginBroadcast).dispatchAudioRoutesChanged(audioRoutesInfo);
                } catch (android.os.RemoteException e) {
                }
            }
        }
        this.mRoutesObservers.finishBroadcast();
        this.mDeviceBroker.postObserveDevicesForAllStreams();
    }

    void onSetWiredDeviceConnectionState(com.android.server.audio.AudioDeviceInventory.WiredDeviceConnectionState wiredDeviceConnectionState) {
        android.media.AudioDeviceInfo audioDeviceInfo;
        int internalType = wiredDeviceConnectionState.mAttributes.getInternalType();
        com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.audio.AudioServiceEvents.WiredDevConnectEvent(wiredDeviceConnectionState));
        android.media.MediaMetrics.Item item = new android.media.MediaMetrics.Item("audio.device.onSetWiredDeviceConnectionState").set(android.media.MediaMetrics.Property.ADDRESS, wiredDeviceConnectionState.mAttributes.getAddress()).set(android.media.MediaMetrics.Property.DEVICE, android.media.AudioSystem.getDeviceName(internalType)).set(android.media.MediaMetrics.Property.STATE, wiredDeviceConnectionState.mState == 0 ? "disconnected" : "connected");
        if (wiredDeviceConnectionState.mState == 0 && android.media.AudioSystem.DEVICE_OUT_ALL_USB_SET.contains(java.lang.Integer.valueOf(wiredDeviceConnectionState.mAttributes.getInternalType()))) {
            android.media.AudioDeviceInfo[] devicesStatic = android.media.AudioManager.getDevicesStatic(2);
            int length = devicesStatic.length;
            for (int i = 0; i < length; i++) {
                audioDeviceInfo = devicesStatic[i];
                if (audioDeviceInfo.getInternalType() == wiredDeviceConnectionState.mAttributes.getInternalType()) {
                    break;
                }
            }
        }
        audioDeviceInfo = null;
        synchronized (this.mDevicesLock) {
            try {
                boolean z = true;
                if (wiredDeviceConnectionState.mState == 0 && DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET.contains(java.lang.Integer.valueOf(internalType))) {
                    this.mDeviceBroker.setBluetoothA2dpOnInt(true, false, "onSetWiredDeviceConnectionState state DISCONNECTED");
                }
                android.media.AudioDeviceAttributes audioDeviceAttributes = wiredDeviceConnectionState.mAttributes;
                if (wiredDeviceConnectionState.mState != 1) {
                    z = false;
                }
                if (!handleDeviceConnection(audioDeviceAttributes, z, wiredDeviceConnectionState.mForTest, null)) {
                    item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "change of connection state failed").record();
                    return;
                }
                if (wiredDeviceConnectionState.mState != 0) {
                    if (DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET.contains(java.lang.Integer.valueOf(internalType))) {
                        this.mDeviceBroker.setBluetoothA2dpOnInt(false, false, "onSetWiredDeviceConnectionState state not DISCONNECTED");
                    }
                    this.mDeviceBroker.checkMusicActive(internalType, wiredDeviceConnectionState.mCaller);
                }
                if (internalType == 1024) {
                    this.mDeviceBroker.checkVolumeCecOnHdmiConnection(wiredDeviceConnectionState.mState, wiredDeviceConnectionState.mCaller);
                }
                if (wiredDeviceConnectionState.mState == 0 && android.media.AudioSystem.DEVICE_OUT_ALL_USB_SET.contains(java.lang.Integer.valueOf(wiredDeviceConnectionState.mAttributes.getInternalType()))) {
                    if (audioDeviceInfo != null) {
                        this.mDeviceBroker.dispatchPreferredMixerAttributesChangedCausedByDeviceRemoved(audioDeviceInfo);
                    } else {
                        android.util.Log.e(TAG, "Didn't find AudioDeviceInfo to notify preferred mixer attributes change for type=" + wiredDeviceConnectionState.mAttributes.getType());
                    }
                }
                sendDeviceConnectionIntent(internalType, wiredDeviceConnectionState.mState, wiredDeviceConnectionState.mAttributes.getAddress(), wiredDeviceConnectionState.mAttributes.getName());
                updateAudioRoutes(internalType, wiredDeviceConnectionState.mState);
                item.record();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onToggleHdmi() {
        android.media.MediaMetrics.Item item = new android.media.MediaMetrics.Item("audio.device.onToggleHdmi").set(android.media.MediaMetrics.Property.DEVICE, android.media.AudioSystem.getDeviceName(1024));
        synchronized (this.mDevicesLock) {
            try {
                if (this.mConnectedDevices.get(com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(1024, "")) == null) {
                    android.util.Log.e(TAG, "invalid null DeviceInfo in onToggleHdmi");
                    item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "invalid null DeviceInfo").record();
                } else {
                    setWiredDeviceConnectionState(new android.media.AudioDeviceAttributes(1024, ""), 0, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
                    setWiredDeviceConnectionState(new android.media.AudioDeviceAttributes(1024, ""), 1, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME);
                    item.record();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void onSaveSetPreferredDevices(int i, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        this.mPreferredDevices.put(java.lang.Integer.valueOf(i), list);
        java.util.List<android.media.AudioDeviceAttributes> list2 = this.mNonDefaultDevices.get(java.lang.Integer.valueOf(i));
        if (list2 != null) {
            list2.removeAll(list);
            if (list2.isEmpty()) {
                this.mNonDefaultDevices.remove(java.lang.Integer.valueOf(i));
            } else {
                this.mNonDefaultDevices.put(java.lang.Integer.valueOf(i), list2);
            }
            dispatchNonDefaultDevice(i, list2);
        }
        dispatchPreferredDevice(i, list);
    }

    void onSaveRemovePreferredDevices(int i) {
        this.mPreferredDevices.remove(java.lang.Integer.valueOf(i));
        dispatchPreferredDevice(i, new java.util.ArrayList());
    }

    void onSaveSetDeviceAsNonDefault(int i, @android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.List<android.media.AudioDeviceAttributes> list = this.mNonDefaultDevices.get(java.lang.Integer.valueOf(i));
        if (list == null) {
            list = new java.util.ArrayList<>();
        }
        if (!list.contains(audioDeviceAttributes)) {
            list.add(audioDeviceAttributes);
        }
        this.mNonDefaultDevices.put(java.lang.Integer.valueOf(i), list);
        dispatchNonDefaultDevice(i, list);
        java.util.List<android.media.AudioDeviceAttributes> list2 = this.mPreferredDevices.get(java.lang.Integer.valueOf(i));
        if (list2 != null) {
            list2.remove(audioDeviceAttributes);
            this.mPreferredDevices.put(java.lang.Integer.valueOf(i), list2);
            dispatchPreferredDevice(i, list2);
        }
    }

    void onSaveRemoveDeviceAsNonDefault(int i, @android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.List<android.media.AudioDeviceAttributes> list = this.mNonDefaultDevices.get(java.lang.Integer.valueOf(i));
        if (list != null) {
            list.remove(audioDeviceAttributes);
            this.mNonDefaultDevices.put(java.lang.Integer.valueOf(i), list);
            dispatchNonDefaultDevice(i, list);
        }
    }

    void onSaveSetPreferredDevicesForCapturePreset(int i, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        this.mPreferredDevicesForCapturePreset.put(java.lang.Integer.valueOf(i), list);
        dispatchDevicesRoleForCapturePreset(i, 1, list);
    }

    void onSaveClearPreferredDevicesForCapturePreset(int i) {
        this.mPreferredDevicesForCapturePreset.remove(java.lang.Integer.valueOf(i));
        dispatchDevicesRoleForCapturePreset(i, 1, new java.util.ArrayList());
    }

    int setPreferredDevicesForStrategyAndSave(int i, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        int preferredDevicesForStrategy = setPreferredDevicesForStrategy(i, list);
        if (preferredDevicesForStrategy == 0) {
            this.mDeviceBroker.postSaveSetPreferredDevicesForStrategy(i, list);
        }
        return preferredDevicesForStrategy;
    }

    int setPreferredDevicesForStrategy(int i, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
        try {
            int devicesRoleForStrategy = setDevicesRoleForStrategy(i, 1, list, false);
            if (create != null) {
                create.close();
            }
            return devicesRoleForStrategy;
        } catch (java.lang.Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    int setPreferredDevicesForStrategyInt(int i, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        return setDevicesRoleForStrategy(i, 1, list, true);
    }

    int removePreferredDevicesForStrategyAndSave(int i) {
        int removePreferredDevicesForStrategy = removePreferredDevicesForStrategy(i);
        if (removePreferredDevicesForStrategy == 0) {
            this.mDeviceBroker.postSaveRemovePreferredDevicesForStrategy(i);
        }
        return removePreferredDevicesForStrategy;
    }

    int removePreferredDevicesForStrategy(int i) {
        android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
        try {
            int clearDevicesRoleForStrategy = clearDevicesRoleForStrategy(i, 1, false);
            if (create != null) {
                create.close();
            }
            return clearDevicesRoleForStrategy;
        } catch (java.lang.Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    int removePreferredDevicesForStrategyInt(int i) {
        return clearDevicesRoleForStrategy(i, 1, true);
    }

    int setDeviceAsNonDefaultForStrategyAndSave(int i, @android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(audioDeviceAttributes);
            int addDevicesRoleForStrategy = addDevicesRoleForStrategy(i, 2, arrayList, false);
            if (create != null) {
                create.close();
            }
            if (addDevicesRoleForStrategy == 0) {
                this.mDeviceBroker.postSaveSetDeviceAsNonDefaultForStrategy(i, audioDeviceAttributes);
            }
            return addDevicesRoleForStrategy;
        } catch (java.lang.Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    int removeDeviceAsNonDefaultForStrategyAndSave(int i, @android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(audioDeviceAttributes);
            int removeDevicesRoleForStrategy = removeDevicesRoleForStrategy(i, 2, arrayList, false);
            if (create != null) {
                create.close();
            }
            if (removeDevicesRoleForStrategy == 0) {
                this.mDeviceBroker.postSaveRemoveDeviceAsNonDefaultForStrategy(i, audioDeviceAttributes);
            }
            return removeDevicesRoleForStrategy;
        } catch (java.lang.Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    void registerStrategyPreferredDevicesDispatcher(@android.annotation.NonNull android.media.IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher, boolean z) {
        this.mPrefDevDispatchers.register(iStrategyPreferredDevicesDispatcher, java.lang.Boolean.valueOf(z));
    }

    void unregisterStrategyPreferredDevicesDispatcher(@android.annotation.NonNull android.media.IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) {
        this.mPrefDevDispatchers.unregister(iStrategyPreferredDevicesDispatcher);
    }

    void registerStrategyNonDefaultDevicesDispatcher(@android.annotation.NonNull android.media.IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher, boolean z) {
        this.mNonDefDevDispatchers.register(iStrategyNonDefaultDevicesDispatcher, java.lang.Boolean.valueOf(z));
    }

    void unregisterStrategyNonDefaultDevicesDispatcher(@android.annotation.NonNull android.media.IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) {
        this.mNonDefDevDispatchers.unregister(iStrategyNonDefaultDevicesDispatcher);
    }

    int setPreferredDevicesForCapturePresetAndSave(int i, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        int preferredDevicesForCapturePreset = setPreferredDevicesForCapturePreset(i, list);
        if (preferredDevicesForCapturePreset == 0) {
            this.mDeviceBroker.postSaveSetPreferredDevicesForCapturePreset(i, list);
        }
        return preferredDevicesForCapturePreset;
    }

    private int setPreferredDevicesForCapturePreset(int i, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
        try {
            int devicesRoleForCapturePreset = setDevicesRoleForCapturePreset(i, 1, list);
            if (create != null) {
                create.close();
            }
            return devicesRoleForCapturePreset;
        } catch (java.lang.Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    int clearPreferredDevicesForCapturePresetAndSave(int i) {
        int clearPreferredDevicesForCapturePreset = clearPreferredDevicesForCapturePreset(i);
        if (clearPreferredDevicesForCapturePreset == 0) {
            this.mDeviceBroker.postSaveClearPreferredDevicesForCapturePreset(i);
        }
        return clearPreferredDevicesForCapturePreset;
    }

    private int clearPreferredDevicesForCapturePreset(int i) {
        android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
        try {
            int clearDevicesRoleForCapturePreset = clearDevicesRoleForCapturePreset(i, 1);
            if (create != null) {
                create.close();
            }
            return clearDevicesRoleForCapturePreset;
        } catch (java.lang.Throwable th) {
            if (create != null) {
                try {
                    create.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private int addDevicesRoleForCapturePresetInt(int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        return addDevicesRole(this.mAppliedPresetRolesInt, new com.android.server.audio.AudioDeviceInventory.AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda20
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, java.util.List list2) {
                int lambda$addDevicesRoleForCapturePresetInt$15;
                lambda$addDevicesRoleForCapturePresetInt$15 = com.android.server.audio.AudioDeviceInventory.this.lambda$addDevicesRoleForCapturePresetInt$15(i3, i4, list2);
                return lambda$addDevicesRoleForCapturePresetInt$15;
            }
        }, i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$addDevicesRoleForCapturePresetInt$15(int i, int i2, java.util.List list) {
        return this.mAudioSystem.addDevicesRoleForCapturePreset(i, i2, list);
    }

    private int removeDevicesRoleForCapturePresetInt(int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        return removeDevicesRole(this.mAppliedPresetRolesInt, new com.android.server.audio.AudioDeviceInventory.AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda26
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, java.util.List list2) {
                int lambda$removeDevicesRoleForCapturePresetInt$16;
                lambda$removeDevicesRoleForCapturePresetInt$16 = com.android.server.audio.AudioDeviceInventory.this.lambda$removeDevicesRoleForCapturePresetInt$16(i3, i4, list2);
                return lambda$removeDevicesRoleForCapturePresetInt$16;
            }
        }, i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$removeDevicesRoleForCapturePresetInt$16(int i, int i2, java.util.List list) {
        return this.mAudioSystem.removeDevicesRoleForCapturePreset(i, i2, list);
    }

    private int setDevicesRoleForCapturePreset(int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        return setDevicesRole(this.mAppliedPresetRoles, new com.android.server.audio.AudioDeviceInventory.AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda5
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, java.util.List list2) {
                int lambda$setDevicesRoleForCapturePreset$17;
                lambda$setDevicesRoleForCapturePreset$17 = com.android.server.audio.AudioDeviceInventory.this.lambda$setDevicesRoleForCapturePreset$17(i3, i4, list2);
                return lambda$setDevicesRoleForCapturePreset$17;
            }
        }, new com.android.server.audio.AudioDeviceInventory.AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda6
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, java.util.List list2) {
                int lambda$setDevicesRoleForCapturePreset$18;
                lambda$setDevicesRoleForCapturePreset$18 = com.android.server.audio.AudioDeviceInventory.this.lambda$setDevicesRoleForCapturePreset$18(i3, i4, list2);
                return lambda$setDevicesRoleForCapturePreset$18;
            }
        }, i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setDevicesRoleForCapturePreset$17(int i, int i2, java.util.List list) {
        return this.mAudioSystem.addDevicesRoleForCapturePreset(i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setDevicesRoleForCapturePreset$18(int i, int i2, java.util.List list) {
        return this.mAudioSystem.clearDevicesRoleForCapturePreset(i, i2);
    }

    private int clearDevicesRoleForCapturePreset(int i, int i2) {
        return clearDevicesRole(this.mAppliedPresetRoles, new com.android.server.audio.AudioDeviceInventory.AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda9
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, java.util.List list) {
                int lambda$clearDevicesRoleForCapturePreset$19;
                lambda$clearDevicesRoleForCapturePreset$19 = com.android.server.audio.AudioDeviceInventory.this.lambda$clearDevicesRoleForCapturePreset$19(i3, i4, list);
                return lambda$clearDevicesRoleForCapturePreset$19;
            }
        }, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$clearDevicesRoleForCapturePreset$19(int i, int i2, java.util.List list) {
        return this.mAudioSystem.clearDevicesRoleForCapturePreset(i, i2);
    }

    void registerCapturePresetDevicesRoleDispatcher(@android.annotation.NonNull android.media.ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher, boolean z) {
        this.mDevRoleCapturePresetDispatchers.register(iCapturePresetDevicesRoleDispatcher, java.lang.Boolean.valueOf(z));
    }

    void unregisterCapturePresetDevicesRoleDispatcher(@android.annotation.NonNull android.media.ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) {
        this.mDevRoleCapturePresetDispatchers.unregister(iCapturePresetDevicesRoleDispatcher);
    }

    private int addDevicesRoleForStrategy(int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list, boolean z) {
        return addDevicesRole(z ? this.mAppliedStrategyRolesInt : this.mAppliedStrategyRoles, new com.android.server.audio.AudioDeviceInventory.AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda35
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, java.util.List list2) {
                int lambda$addDevicesRoleForStrategy$20;
                lambda$addDevicesRoleForStrategy$20 = com.android.server.audio.AudioDeviceInventory.this.lambda$addDevicesRoleForStrategy$20(i3, i4, list2);
                return lambda$addDevicesRoleForStrategy$20;
            }
        }, i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$addDevicesRoleForStrategy$20(int i, int i2, java.util.List list) {
        return this.mAudioSystem.setDevicesRoleForStrategy(i, i2, list);
    }

    private int removeDevicesRoleForStrategy(int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list, boolean z) {
        return removeDevicesRole(z ? this.mAppliedStrategyRolesInt : this.mAppliedStrategyRoles, new com.android.server.audio.AudioDeviceInventory.AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda4
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, java.util.List list2) {
                int lambda$removeDevicesRoleForStrategy$21;
                lambda$removeDevicesRoleForStrategy$21 = com.android.server.audio.AudioDeviceInventory.this.lambda$removeDevicesRoleForStrategy$21(i3, i4, list2);
                return lambda$removeDevicesRoleForStrategy$21;
            }
        }, i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$removeDevicesRoleForStrategy$21(int i, int i2, java.util.List list) {
        return this.mAudioSystem.removeDevicesRoleForStrategy(i, i2, list);
    }

    private int setDevicesRoleForStrategy(int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list, boolean z) {
        return setDevicesRole(z ? this.mAppliedStrategyRolesInt : this.mAppliedStrategyRoles, new com.android.server.audio.AudioDeviceInventory.AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda2
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, java.util.List list2) {
                int lambda$setDevicesRoleForStrategy$22;
                lambda$setDevicesRoleForStrategy$22 = com.android.server.audio.AudioDeviceInventory.this.lambda$setDevicesRoleForStrategy$22(i3, i4, list2);
                return lambda$setDevicesRoleForStrategy$22;
            }
        }, new com.android.server.audio.AudioDeviceInventory.AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda3
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, java.util.List list2) {
                int lambda$setDevicesRoleForStrategy$23;
                lambda$setDevicesRoleForStrategy$23 = com.android.server.audio.AudioDeviceInventory.this.lambda$setDevicesRoleForStrategy$23(i3, i4, list2);
                return lambda$setDevicesRoleForStrategy$23;
            }
        }, i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setDevicesRoleForStrategy$22(int i, int i2, java.util.List list) {
        return this.mAudioSystem.setDevicesRoleForStrategy(i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$setDevicesRoleForStrategy$23(int i, int i2, java.util.List list) {
        return this.mAudioSystem.clearDevicesRoleForStrategy(i, i2);
    }

    private int clearDevicesRoleForStrategy(int i, int i2, boolean z) {
        return clearDevicesRole(z ? this.mAppliedStrategyRolesInt : this.mAppliedStrategyRoles, new com.android.server.audio.AudioDeviceInventory.AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda23
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i3, int i4, java.util.List list) {
                int lambda$clearDevicesRoleForStrategy$24;
                lambda$clearDevicesRoleForStrategy$24 = com.android.server.audio.AudioDeviceInventory.this.lambda$clearDevicesRoleForStrategy$24(i3, i4, list);
                return lambda$clearDevicesRoleForStrategy$24;
            }
        }, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$clearDevicesRoleForStrategy$24(int i, int i2, java.util.List list) {
        return this.mAudioSystem.clearDevicesRoleForStrategy(i, i2);
    }

    private int addDevicesRole(android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.Integer>, java.util.List<android.media.AudioDeviceAttributes>> arrayMap, com.android.server.audio.AudioDeviceInventory.AudioSystemInterface audioSystemInterface, int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        synchronized (arrayMap) {
            try {
                android.util.Pair<java.lang.Integer, java.lang.Integer> pair = new android.util.Pair<>(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
                java.util.List<android.media.AudioDeviceAttributes> arrayList = new java.util.ArrayList<>();
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                if (arrayMap.containsKey(pair)) {
                    arrayList = arrayMap.get(pair);
                    for (android.media.AudioDeviceAttributes audioDeviceAttributes : list) {
                        if (!arrayList.contains(audioDeviceAttributes)) {
                            arrayList2.add(audioDeviceAttributes);
                        }
                    }
                } else {
                    arrayList2.addAll(list);
                }
                if (arrayList2.isEmpty()) {
                    return 0;
                }
                int deviceRoleAction = audioSystemInterface.deviceRoleAction(i, i2, arrayList2);
                if (deviceRoleAction == 0) {
                    arrayList.addAll(arrayList2);
                    arrayMap.put(pair, arrayList);
                }
                return deviceRoleAction;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int removeDevicesRole(android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.Integer>, java.util.List<android.media.AudioDeviceAttributes>> arrayMap, com.android.server.audio.AudioDeviceInventory.AudioSystemInterface audioSystemInterface, int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        synchronized (arrayMap) {
            try {
                android.util.Pair<java.lang.Integer, java.lang.Integer> pair = new android.util.Pair<>(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
                if (!arrayMap.containsKey(pair)) {
                    return -2;
                }
                java.util.List<android.media.AudioDeviceAttributes> list2 = arrayMap.get(pair);
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (android.media.AudioDeviceAttributes audioDeviceAttributes : list) {
                    if (list2.contains(audioDeviceAttributes)) {
                        arrayList.add(audioDeviceAttributes);
                    }
                }
                if (arrayList.isEmpty()) {
                    return 0;
                }
                int deviceRoleAction = audioSystemInterface.deviceRoleAction(i, i2, arrayList);
                if (deviceRoleAction == 0) {
                    list2.removeAll(arrayList);
                    if (list2.isEmpty()) {
                        arrayMap.remove(pair);
                    } else {
                        arrayMap.put(pair, list2);
                    }
                }
                return deviceRoleAction;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static boolean devicesListEqual(@android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list2) {
        boolean z;
        if (list.size() != list2.size()) {
            return false;
        }
        java.util.Iterator<android.media.AudioDeviceAttributes> it = list.iterator();
        do {
            z = true;
            if (!it.hasNext()) {
                return true;
            }
            android.media.AudioDeviceAttributes next = it.next();
            java.util.Iterator<android.media.AudioDeviceAttributes> it2 = list2.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    z = false;
                    break;
                }
                if (next.equalTypeAddress(it2.next())) {
                    break;
                }
            }
        } while (z);
        return false;
    }

    private int setDevicesRole(android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.Integer>, java.util.List<android.media.AudioDeviceAttributes>> arrayMap, com.android.server.audio.AudioDeviceInventory.AudioSystemInterface audioSystemInterface, com.android.server.audio.AudioDeviceInventory.AudioSystemInterface audioSystemInterface2, int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        int deviceRoleAction;
        synchronized (arrayMap) {
            try {
                android.util.Pair<java.lang.Integer, java.lang.Integer> pair = new android.util.Pair<>(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
                if (arrayMap.containsKey(pair)) {
                    if (devicesListEqual(list, arrayMap.get(pair))) {
                        return 0;
                    }
                } else if (list.isEmpty()) {
                    return 0;
                }
                if (list.isEmpty()) {
                    deviceRoleAction = audioSystemInterface2.deviceRoleAction(i, i2, null);
                    if (deviceRoleAction == 0) {
                        arrayMap.remove(pair);
                    }
                } else {
                    deviceRoleAction = audioSystemInterface.deviceRoleAction(i, i2, list);
                    if (deviceRoleAction == 0) {
                        arrayMap.put(pair, new java.util.ArrayList(list));
                    }
                }
                return deviceRoleAction;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private int clearDevicesRole(android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.Integer>, java.util.List<android.media.AudioDeviceAttributes>> arrayMap, com.android.server.audio.AudioDeviceInventory.AudioSystemInterface audioSystemInterface, int i, int i2) {
        synchronized (arrayMap) {
            try {
                android.util.Pair pair = new android.util.Pair(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
                if (!arrayMap.containsKey(pair)) {
                    return -2;
                }
                int deviceRoleAction = audioSystemInterface.deviceRoleAction(i, i2, null);
                if (deviceRoleAction == 0) {
                    arrayMap.remove(pair);
                }
                return deviceRoleAction;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private void purgeDevicesRoles_l() {
        purgeRoles(this.mAppliedStrategyRolesInt, new com.android.server.audio.AudioDeviceInventory.AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda24
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i, int i2, java.util.List list) {
                int lambda$purgeDevicesRoles_l$25;
                lambda$purgeDevicesRoles_l$25 = com.android.server.audio.AudioDeviceInventory.this.lambda$purgeDevicesRoles_l$25(i, i2, list);
                return lambda$purgeDevicesRoles_l$25;
            }
        });
        purgeRoles(this.mAppliedPresetRolesInt, new com.android.server.audio.AudioDeviceInventory.AudioSystemInterface() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda25
            @Override // com.android.server.audio.AudioDeviceInventory.AudioSystemInterface
            public final int deviceRoleAction(int i, int i2, java.util.List list) {
                int lambda$purgeDevicesRoles_l$26;
                lambda$purgeDevicesRoles_l$26 = com.android.server.audio.AudioDeviceInventory.this.lambda$purgeDevicesRoles_l$26(i, i2, list);
                return lambda$purgeDevicesRoles_l$26;
            }
        });
        reapplyExternalDevicesRoles();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$purgeDevicesRoles_l$25(int i, int i2, java.util.List list) {
        return this.mAudioSystem.removeDevicesRoleForStrategy(i, i2, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$purgeDevicesRoles_l$26(int i, int i2, java.util.List list) {
        return this.mAudioSystem.removeDevicesRoleForCapturePreset(i, i2, list);
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private void purgeRoles(android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.Integer>, java.util.List<android.media.AudioDeviceAttributes>> arrayMap, com.android.server.audio.AudioDeviceInventory.AudioSystemInterface audioSystemInterface) {
        synchronized (arrayMap) {
            try {
                android.media.AudioDeviceInfo[] devicesStatic = android.media.AudioManager.getDevicesStatic(3);
                java.util.Iterator<java.util.Map.Entry<android.util.Pair<java.lang.Integer, java.lang.Integer>, java.util.List<android.media.AudioDeviceAttributes>>> it = arrayMap.entrySet().iterator();
                while (it.hasNext()) {
                    android.util.Pair<java.lang.Integer, java.lang.Integer> key = it.next().getKey();
                    java.util.Iterator<android.media.AudioDeviceAttributes> it2 = arrayMap.get(key).iterator();
                    while (it2.hasNext()) {
                        final android.media.AudioDeviceAttributes next = it2.next();
                        if (((android.media.AudioDeviceInfo) java.util.stream.Stream.of((java.lang.Object[]) devicesStatic).filter(new java.util.function.Predicate() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda30
                            @Override // java.util.function.Predicate
                            public final boolean test(java.lang.Object obj) {
                                boolean lambda$purgeRoles$27;
                                lambda$purgeRoles$27 = com.android.server.audio.AudioDeviceInventory.lambda$purgeRoles$27(next, (android.media.AudioDeviceInfo) obj);
                                return lambda$purgeRoles$27;
                            }
                        }).filter(new java.util.function.Predicate() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda31
                            @Override // java.util.function.Predicate
                            public final boolean test(java.lang.Object obj) {
                                boolean lambda$purgeRoles$28;
                                lambda$purgeRoles$28 = com.android.server.audio.AudioDeviceInventory.lambda$purgeRoles$28(next, (android.media.AudioDeviceInfo) obj);
                                return lambda$purgeRoles$28;
                            }
                        }).findFirst().orElse(null)) == null) {
                            audioSystemInterface.deviceRoleAction(((java.lang.Integer) key.first).intValue(), ((java.lang.Integer) key.second).intValue(), java.util.Arrays.asList(next));
                            it2.remove();
                        }
                    }
                    if (arrayMap.get(key).isEmpty()) {
                        it.remove();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$purgeRoles$27(android.media.AudioDeviceAttributes audioDeviceAttributes, android.media.AudioDeviceInfo audioDeviceInfo) {
        return audioDeviceInfo.getInternalType() == audioDeviceAttributes.getInternalType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$purgeRoles$28(android.media.AudioDeviceAttributes audioDeviceAttributes, android.media.AudioDeviceInfo audioDeviceInfo) {
        return !android.media.AudioSystem.isBluetoothDevice(audioDeviceInfo.getInternalType()) || audioDeviceInfo.getAddress().equals(audioDeviceAttributes.getAddress());
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceBroker.mDeviceStateLock"})
    public boolean isDeviceConnected(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        boolean z;
        java.lang.String makeDeviceListKey = com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(audioDeviceAttributes.getInternalType(), audioDeviceAttributes.getAddress());
        synchronized (this.mDevicesLock) {
            z = this.mConnectedDevices.get(makeDeviceListKey) != null;
        }
        return z;
    }

    boolean handleDeviceConnection(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, boolean z, boolean z2, @android.annotation.Nullable android.bluetooth.BluetoothDevice bluetoothDevice) {
        int deviceConnectionState;
        int internalType = audioDeviceAttributes.getInternalType();
        java.lang.String address = audioDeviceAttributes.getAddress();
        java.lang.String name = audioDeviceAttributes.getName();
        android.media.MediaMetrics.Item item = new android.media.MediaMetrics.Item("audio.device.handleDeviceConnection").set(android.media.MediaMetrics.Property.ADDRESS, address).set(android.media.MediaMetrics.Property.DEVICE, android.media.AudioSystem.getDeviceName(internalType)).set(android.media.MediaMetrics.Property.MODE, z ? "connect" : "disconnect").set(android.media.MediaMetrics.Property.NAME, name);
        synchronized (this.mDevicesLock) {
            try {
                java.lang.String makeDeviceListKey = com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(internalType, address);
                com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo = this.mConnectedDevices.get(makeDeviceListKey);
                boolean z3 = true;
                boolean z4 = deviceInfo != null;
                if (z && !z4) {
                    if (!z2) {
                        deviceConnectionState = this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 1, 0);
                    } else {
                        deviceConnectionState = 0;
                    }
                    if (deviceConnectionState != 0) {
                        item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "not connecting device 0x" + java.lang.Integer.toHexString(internalType) + " due to command error " + deviceConnectionState).set(android.media.MediaMetrics.Property.STATE, "disconnected").record();
                        com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("APM failed to make available device 0x" + java.lang.Integer.toHexString(internalType) + "addr=" + address + " error=" + deviceConnectionState).printSlog(1, TAG));
                        return false;
                    }
                    this.mConnectedDevices.put(makeDeviceListKey, new com.android.server.audio.AudioDeviceInventory.DeviceInfo(internalType, name, address));
                    this.mDeviceBroker.postAccessoryPlugMediaUnmute(internalType);
                } else if (!z && z4) {
                    this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, 0);
                    this.mConnectedDevices.remove(makeDeviceListKey);
                    this.mDeviceBroker.postCheckCommunicationDeviceRemoval(audioDeviceAttributes);
                } else {
                    z3 = false;
                }
                if (z3) {
                    if (android.media.AudioSystem.isBluetoothScoDevice(internalType)) {
                        if (!z) {
                            bluetoothDevice = null;
                        }
                        updateBluetoothPreferredModes_l(bluetoothDevice);
                        if (!z) {
                            purgeDevicesRoles_l();
                        } else {
                            addAudioDeviceInInventoryIfNeeded(internalType, address, "", com.android.server.audio.BtHelper.getBtDeviceCategory(address));
                        }
                        com.android.server.utils.EventLogger eventLogger = com.android.server.audio.AudioService.sDeviceLogger;
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        sb.append("SCO ");
                        sb.append(android.media.AudioSystem.isInputDevice(internalType) ? "source" : "sink");
                        sb.append(" device addr=");
                        sb.append(address);
                        sb.append(z ? " now available" : " made unavailable");
                        eventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(sb.toString()).printSlog(0, TAG));
                    }
                    item.set(android.media.MediaMetrics.Property.STATE, "connected").record();
                } else {
                    android.util.Log.w(TAG, "handleDeviceConnection() failed, deviceKey=" + makeDeviceListKey + ", deviceSpec=" + deviceInfo + ", connect=" + z);
                    item.set(android.media.MediaMetrics.Property.STATE, "disconnected").record();
                }
                return z3;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void disconnectA2dp() {
        synchronized (this.mDevicesLock) {
            try {
                final android.util.ArraySet arraySet = new android.util.ArraySet();
                this.mConnectedDevices.values().forEach(new java.util.function.Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.audio.AudioDeviceInventory.lambda$disconnectA2dp$29(arraySet, (com.android.server.audio.AudioDeviceInventory.DeviceInfo) obj);
                    }
                });
                new android.media.MediaMetrics.Item("audio.device.disconnectA2dp").set(android.media.MediaMetrics.Property.EVENT, "disconnectA2dp").record();
                if (arraySet.size() > 0) {
                    final int checkSendBecomingNoisyIntentInt = checkSendBecomingNoisyIntentInt(128, 0, 0);
                    arraySet.stream().forEach(new java.util.function.Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda8
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.audio.AudioDeviceInventory.this.lambda$disconnectA2dp$30(checkSendBecomingNoisyIntentInt, (java.lang.String) obj);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$disconnectA2dp$29(android.util.ArraySet arraySet, com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo) {
        if (deviceInfo.mDeviceType == 128) {
            arraySet.add(deviceInfo.mDeviceAddress);
        }
    }

    private void disconnectA2dpSink() {
        synchronized (this.mDevicesLock) {
            final android.util.ArraySet arraySet = new android.util.ArraySet();
            this.mConnectedDevices.values().forEach(new java.util.function.Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda28
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.audio.AudioDeviceInventory.lambda$disconnectA2dpSink$31(arraySet, (com.android.server.audio.AudioDeviceInventory.DeviceInfo) obj);
                }
            });
            new android.media.MediaMetrics.Item("audio.device.disconnectA2dpSink").set(android.media.MediaMetrics.Property.EVENT, "disconnectA2dpSink").record();
            arraySet.stream().forEach(new java.util.function.Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda29
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.audio.AudioDeviceInventory.this.lambda$disconnectA2dpSink$32((java.lang.String) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$disconnectA2dpSink$31(android.util.ArraySet arraySet, com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo) {
        if (deviceInfo.mDeviceType == -2147352576) {
            arraySet.add(deviceInfo.mDeviceAddress);
        }
    }

    private void disconnectHearingAid() {
        synchronized (this.mDevicesLock) {
            try {
                final android.util.ArraySet arraySet = new android.util.ArraySet();
                this.mConnectedDevices.values().forEach(new java.util.function.Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda33
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.audio.AudioDeviceInventory.lambda$disconnectHearingAid$33(arraySet, (com.android.server.audio.AudioDeviceInventory.DeviceInfo) obj);
                    }
                });
                new android.media.MediaMetrics.Item("audio.device.disconnectHearingAid").set(android.media.MediaMetrics.Property.EVENT, "disconnectHearingAid").record();
                if (arraySet.size() > 0) {
                    checkSendBecomingNoisyIntentInt(134217728, 0, 0);
                    arraySet.stream().forEach(new java.util.function.Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda34
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.audio.AudioDeviceInventory.this.lambda$disconnectHearingAid$34((java.lang.String) obj);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$disconnectHearingAid$33(android.util.ArraySet arraySet, com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo) {
        if (deviceInfo.mDeviceType == 134217728) {
            arraySet.add(deviceInfo.mDeviceAddress);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceBroker.mDeviceStateLock"})
    void onBtProfileDisconnected(int i) {
        switch (i) {
            case 1:
                disconnectHeadset();
                break;
            case 2:
                disconnectA2dp();
                break;
            case 11:
                disconnectA2dpSink();
                break;
            case 21:
                disconnectHearingAid();
                break;
            case 22:
                disconnectLeAudioUnicast();
                break;
            case 26:
                disconnectLeAudioBroadcast();
                break;
            default:
                android.util.Log.e(TAG, "onBtProfileDisconnected: Not a valid profile to disconnect " + android.bluetooth.BluetoothProfile.getProfileName(i));
                break;
        }
    }

    void disconnectLeAudio(final int i) {
        if (i != 536870912 && i != 536870914) {
            android.util.Log.e(TAG, "disconnectLeAudio: Can't disconnect not LE Audio device " + i);
            return;
        }
        synchronized (this.mDevicesLock) {
            try {
                final android.util.ArraySet arraySet = new android.util.ArraySet();
                this.mConnectedDevices.values().forEach(new java.util.function.Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.audio.AudioDeviceInventory.lambda$disconnectLeAudio$35(i, arraySet, (com.android.server.audio.AudioDeviceInventory.DeviceInfo) obj);
                    }
                });
                new android.media.MediaMetrics.Item("audio.device.disconnectLeAudio").set(android.media.MediaMetrics.Property.EVENT, "disconnectLeAudio").record();
                if (arraySet.size() > 0) {
                    final int checkSendBecomingNoisyIntentInt = checkSendBecomingNoisyIntentInt(i, 0, 0);
                    arraySet.stream().forEach(new java.util.function.Consumer() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.audio.AudioDeviceInventory.this.lambda$disconnectLeAudio$36(i, checkSendBecomingNoisyIntentInt, (android.util.Pair) obj);
                        }
                    });
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$disconnectLeAudio$35(int i, android.util.ArraySet arraySet, com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo) {
        if (deviceInfo.mDeviceType == i) {
            arraySet.add(new android.util.Pair(deviceInfo.mDeviceAddress, java.lang.Integer.valueOf(deviceInfo.mDeviceCodecFormat)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disconnectLeAudio$36(int i, int i2, android.util.Pair pair) {
        makeLeAudioDeviceUnavailableLater((java.lang.String) pair.first, i, ((java.lang.Integer) pair.second).intValue(), i2);
    }

    void disconnectLeAudioUnicast() {
        disconnectLeAudio(536870912);
    }

    void disconnectLeAudioBroadcast() {
        disconnectLeAudio(536870914);
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceBroker.mDeviceStateLock"})
    private void disconnectHeadset() {
        boolean z;
        synchronized (this.mDevicesLock) {
            try {
                java.util.Iterator<com.android.server.audio.AudioDeviceInventory.DeviceInfo> it = this.mConnectedDevices.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    } else if (android.media.AudioSystem.isBluetoothScoDevice(it.next().mDeviceType)) {
                        z = true;
                        break;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z) {
            this.mDeviceBroker.onSetBtScoActiveDevice(null);
        }
    }

    int checkSendBecomingNoisyIntent(int i, int i2, int i3) {
        int checkSendBecomingNoisyIntentInt;
        synchronized (this.mDevicesLock) {
            checkSendBecomingNoisyIntentInt = checkSendBecomingNoisyIntentInt(i, i2, i3);
        }
        return checkSendBecomingNoisyIntentInt;
    }

    android.media.AudioRoutesInfo startWatchingRoutes(android.media.IAudioRoutesObserver iAudioRoutesObserver) {
        android.media.AudioRoutesInfo audioRoutesInfo;
        synchronized (this.mCurAudioRoutes) {
            audioRoutesInfo = new android.media.AudioRoutesInfo(this.mCurAudioRoutes);
            this.mRoutesObservers.register(iAudioRoutesObserver);
        }
        return audioRoutesInfo;
    }

    android.media.AudioRoutesInfo getCurAudioRoutes() {
        return this.mCurAudioRoutes;
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceBroker.mDeviceStateLock"})
    public int setBluetoothActiveDevice(@android.annotation.NonNull com.android.server.audio.AudioDeviceBroker.BtDeviceInfo btDeviceInfo) {
        int i;
        synchronized (this.mDevicesLock) {
            try {
                if (!btDeviceInfo.mSupprNoisy && (((btDeviceInfo.mProfile == 22 || btDeviceInfo.mProfile == 26) && btDeviceInfo.mIsLeOutput) || btDeviceInfo.mProfile == 21 || btDeviceInfo.mProfile == 2)) {
                    i = checkSendBecomingNoisyIntentInt(btDeviceInfo.mAudioSystemDevice, btDeviceInfo.mState == 2 ? 1 : 0, btDeviceInfo.mMusicDevice);
                }
                this.mDeviceBroker.postBluetoothActiveDevice(btDeviceInfo, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    int setWiredDeviceConnectionState(android.media.AudioDeviceAttributes audioDeviceAttributes, int i, java.lang.String str) {
        int checkSendBecomingNoisyIntentInt;
        synchronized (this.mDevicesLock) {
            checkSendBecomingNoisyIntentInt = checkSendBecomingNoisyIntentInt(audioDeviceAttributes.getInternalType(), i, 0);
            this.mDeviceBroker.postSetWiredDeviceConnectionState(new com.android.server.audio.AudioDeviceInventory.WiredDeviceConnectionState(audioDeviceAttributes, i, str), checkSendBecomingNoisyIntentInt);
        }
        return checkSendBecomingNoisyIntentInt;
    }

    void setTestDeviceConnectionState(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, int i) {
        com.android.server.audio.AudioDeviceInventory.WiredDeviceConnectionState wiredDeviceConnectionState = new com.android.server.audio.AudioDeviceInventory.WiredDeviceConnectionState(audioDeviceAttributes, i, "com.android.server.audio");
        wiredDeviceConnectionState.mForTest = true;
        onSetWiredDeviceConnectionState(wiredDeviceConnectionState);
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private void makeA2dpDeviceAvailable(com.android.server.audio.AudioDeviceBroker.BtDeviceInfo btDeviceInfo, int i, java.lang.String str) {
        java.lang.String address = btDeviceInfo.mDevice.getAddress();
        java.lang.String name = com.android.server.audio.BtHelper.getName(btDeviceInfo.mDevice);
        this.mDeviceBroker.setBluetoothA2dpOnInt(true, true, str);
        int deviceConnectionState = this.mAudioSystem.setDeviceConnectionState(new android.media.AudioDeviceAttributes(128, address, name), 1, i);
        if (deviceConnectionState != 0) {
            com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("APM failed to make available A2DP device addr=" + android.media.Utils.anonymizeBluetoothAddress(address) + " error=" + deviceConnectionState).printSlog(1, TAG));
        } else {
            com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("A2DP sink device addr=" + android.media.Utils.anonymizeBluetoothAddress(address) + " now available").printSlog(0, TAG));
        }
        this.mDeviceBroker.clearA2dpSuspended(true);
        com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo = new com.android.server.audio.AudioDeviceInventory.DeviceInfo(128, name, address, btDeviceInfo.mDevice.getIdentityAddress(), i);
        java.lang.String key = deviceInfo.getKey();
        this.mConnectedDevices.put(key, deviceInfo);
        this.mApmConnectedDevices.put(128, key);
        this.mDeviceBroker.postAccessoryPlugMediaUnmute(128);
        setCurrentAudioRouteNameIfPossible(name, true);
        updateBluetoothPreferredModes_l(btDeviceInfo.mDevice);
        addAudioDeviceInInventoryIfNeeded(128, address, "", com.android.server.audio.BtHelper.getBtDeviceCategory(address));
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private void applyConnectedDevicesRoles_l() {
        boolean z;
        boolean z2;
        if (!this.mBluetoothDualModeEnabled) {
            return;
        }
        com.android.server.audio.AudioDeviceInventory.DeviceInfo firstConnectedDeviceOfTypes = getFirstConnectedDeviceOfTypes(android.media.AudioSystem.DEVICE_OUT_ALL_BLE_SET);
        com.android.server.audio.AudioDeviceInventory.DeviceInfo firstConnectedDeviceOfTypes2 = getFirstConnectedDeviceOfTypes(android.media.AudioSystem.DEVICE_IN_ALL_BLE_SET);
        getFirstConnectedDeviceOfTypes(android.media.AudioSystem.DEVICE_OUT_ALL_A2DP_SET);
        getFirstConnectedDeviceOfTypes(android.media.AudioSystem.DEVICE_OUT_ALL_SCO_SET);
        getFirstConnectedDeviceOfTypes(android.media.AudioSystem.DEVICE_IN_ALL_SCO_SET);
        boolean z3 = firstConnectedDeviceOfTypes != null && firstConnectedDeviceOfTypes.isOutputOnlyModeEnabled();
        boolean z4 = (firstConnectedDeviceOfTypes != null && firstConnectedDeviceOfTypes.isDuplexModeEnabled()) || (firstConnectedDeviceOfTypes2 != null && firstConnectedDeviceOfTypes2.isDuplexModeEnabled());
        android.media.AudioDeviceAttributes audioDeviceAttributes = null;
        if (this.mDeviceBroker.mActiveCommunicationDevice != null && this.mDeviceBroker.isInCommunication() && this.mDeviceBroker.mActiveCommunicationDevice != null) {
            audioDeviceAttributes = new android.media.AudioDeviceAttributes(this.mDeviceBroker.mActiveCommunicationDevice);
        }
        for (com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
            if (android.media.AudioSystem.isBluetoothDevice(deviceInfo.mDeviceType)) {
                android.media.AudioDeviceAttributes audioDeviceAttributes2 = new android.media.AudioDeviceAttributes(deviceInfo.mDeviceType, deviceInfo.mDeviceAddress, deviceInfo.mDeviceName);
                if (!audioDeviceAttributes2.equalTypeAddress(audioDeviceAttributes)) {
                    if (android.media.AudioSystem.isBluetoothOutDevice(deviceInfo.mDeviceType)) {
                        for (android.media.audiopolicy.AudioProductStrategy audioProductStrategy : this.mStrategies) {
                            if (audioProductStrategy.getId() == this.mDeviceBroker.mCommunicationStrategyId) {
                                if (android.media.AudioSystem.isBluetoothScoDevice(deviceInfo.mDeviceType)) {
                                    z2 = z4 || !deviceInfo.isDuplexModeEnabled();
                                } else {
                                    if (android.media.AudioSystem.isBluetoothLeDevice(deviceInfo.mDeviceType)) {
                                        z2 = !deviceInfo.isDuplexModeEnabled();
                                    }
                                    z2 = false;
                                }
                            } else if (android.media.AudioSystem.isBluetoothA2dpOutDevice(deviceInfo.mDeviceType)) {
                                z2 = z3 || !deviceInfo.isOutputOnlyModeEnabled();
                            } else if (android.media.AudioSystem.isBluetoothScoDevice(deviceInfo.mDeviceType)) {
                                z2 = z4 || !deviceInfo.isOutputOnlyModeEnabled();
                            } else {
                                if (android.media.AudioSystem.isBluetoothLeDevice(deviceInfo.mDeviceType)) {
                                    z2 = !deviceInfo.isOutputOnlyModeEnabled();
                                }
                                z2 = false;
                            }
                            if (z2) {
                                addDevicesRoleForStrategy(audioProductStrategy.getId(), 2, java.util.Arrays.asList(audioDeviceAttributes2), true);
                            } else {
                                removeDevicesRoleForStrategy(audioProductStrategy.getId(), 2, java.util.Arrays.asList(audioDeviceAttributes2), true);
                            }
                        }
                    }
                    if (android.media.AudioSystem.isBluetoothInDevice(deviceInfo.mDeviceType)) {
                        for (int i : CAPTURE_PRESETS) {
                            if (android.media.AudioSystem.isBluetoothScoDevice(deviceInfo.mDeviceType)) {
                                z = z4 || !deviceInfo.isDuplexModeEnabled();
                            } else if (android.media.AudioSystem.isBluetoothLeDevice(deviceInfo.mDeviceType)) {
                                z = !deviceInfo.isDuplexModeEnabled();
                            } else {
                                z = false;
                            }
                            if (z) {
                                addDevicesRoleForCapturePresetInt(i, 2, java.util.Arrays.asList(audioDeviceAttributes2));
                            } else {
                                removeDevicesRoleForCapturePresetInt(i, 2, java.util.Arrays.asList(audioDeviceAttributes2));
                            }
                        }
                    }
                }
            }
        }
    }

    void applyConnectedDevicesRoles() {
        synchronized (this.mDevicesLock) {
            applyConnectedDevicesRoles_l();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    int checkProfileIsConnected(int i) {
        switch (i) {
            case 1:
                if (getFirstConnectedDeviceOfTypes(android.media.AudioSystem.DEVICE_OUT_ALL_SCO_SET) != null || getFirstConnectedDeviceOfTypes(android.media.AudioSystem.DEVICE_IN_ALL_SCO_SET) != null) {
                }
                break;
            case 2:
                if (getFirstConnectedDeviceOfTypes(android.media.AudioSystem.DEVICE_OUT_ALL_A2DP_SET) != null) {
                }
                break;
            case 22:
            case 26:
                if (getFirstConnectedDeviceOfTypes(android.media.AudioSystem.DEVICE_OUT_ALL_BLE_SET) != null || getFirstConnectedDeviceOfTypes(android.media.AudioSystem.DEVICE_IN_ALL_BLE_SET) != null) {
                }
                break;
        }
        return i;
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private void updateBluetoothPreferredModes_l(android.bluetooth.BluetoothDevice bluetoothDevice) {
        int profileFromType;
        if (!this.mBluetoothDualModeEnabled) {
            return;
        }
        java.util.HashSet hashSet = new java.util.HashSet(0);
        for (com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
            if (android.media.AudioSystem.isBluetoothDevice(deviceInfo.mDeviceType) && !hashSet.contains(deviceInfo.mDeviceAddress)) {
                android.os.Bundle preferredAudioProfiles = com.android.server.audio.BtHelper.getPreferredAudioProfiles(deviceInfo.mDeviceAddress);
                for (com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo2 : this.mConnectedDevices.values()) {
                    if (android.media.AudioSystem.isBluetoothDevice(deviceInfo2.mDeviceType) && deviceInfo.mDeviceAddress.equals(deviceInfo2.mDeviceAddress) && (profileFromType = com.android.server.audio.BtHelper.getProfileFromType(deviceInfo2.mDeviceType)) != 0) {
                        int checkProfileIsConnected = checkProfileIsConnected(preferredAudioProfiles.getInt("audio_mode_duplex"));
                        if (checkProfileIsConnected == profileFromType || checkProfileIsConnected == 0) {
                            deviceInfo2.setModeEnabled("audio_mode_duplex");
                        } else {
                            deviceInfo2.setModeDisabled("audio_mode_duplex");
                        }
                        int checkProfileIsConnected2 = checkProfileIsConnected(preferredAudioProfiles.getInt("audio_mode_output_only"));
                        if (checkProfileIsConnected2 == profileFromType || checkProfileIsConnected2 == 0) {
                            deviceInfo2.setModeEnabled("audio_mode_output_only");
                        } else {
                            deviceInfo2.setModeDisabled("audio_mode_output_only");
                        }
                    }
                }
                hashSet.add(deviceInfo.mDeviceAddress);
            }
        }
        applyConnectedDevicesRoles_l();
        if (bluetoothDevice != null) {
            this.mDeviceBroker.postNotifyPreferredAudioProfileApplied(bluetoothDevice);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private void makeA2dpDeviceUnavailableNow(java.lang.String str, int i) {
        android.media.MediaMetrics.Item item = new android.media.MediaMetrics.Item("audio.device.a2dp." + str).set(android.media.MediaMetrics.Property.ENCODING, android.media.AudioSystem.audioFormatToString(i)).set(android.media.MediaMetrics.Property.EVENT, "makeA2dpDeviceUnavailableNow");
        if (str == null) {
            item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "address null").record();
            return;
        }
        java.lang.String makeDeviceListKey = com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(128, str);
        this.mConnectedDevices.remove(makeDeviceListKey);
        if (!makeDeviceListKey.equals(this.mApmConnectedDevices.get(128))) {
            com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("A2DP device " + android.media.Utils.anonymizeBluetoothAddress(str) + " made unavailable, was not used").printSlog(0, TAG));
            item.set(android.media.MediaMetrics.Property.EARLY_RETURN, "A2DP device made unavailable, was not used").record();
            return;
        }
        this.mDeviceBroker.clearAvrcpAbsoluteVolumeSupported();
        android.media.AudioDeviceAttributes audioDeviceAttributes = new android.media.AudioDeviceAttributes(128, str);
        int deviceConnectionState = this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, i);
        if (deviceConnectionState != 0) {
            com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("APM failed to make unavailable A2DP device addr=" + android.media.Utils.anonymizeBluetoothAddress(str) + " error=" + deviceConnectionState).printSlog(1, TAG));
        } else {
            com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("A2DP device addr=" + android.media.Utils.anonymizeBluetoothAddress(str) + " made unavailable").printSlog(0, TAG));
        }
        this.mApmConnectedDevices.remove(128);
        setCurrentAudioRouteNameIfPossible(null, true);
        item.record();
        updateBluetoothPreferredModes_l(null);
        purgeDevicesRoles_l();
        this.mDeviceBroker.postCheckCommunicationDeviceRemoval(audioDeviceAttributes);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    /* renamed from: makeA2dpDeviceUnavailableLater, reason: merged with bridge method [inline-methods] */
    public void lambda$disconnectA2dp$30(java.lang.String str, int i) {
        this.mDeviceBroker.setA2dpSuspended(true, true, "makeA2dpDeviceUnavailableLater");
        java.lang.String makeDeviceListKey = com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(128, str);
        com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo = this.mConnectedDevices.get(makeDeviceListKey);
        int i2 = deviceInfo != null ? deviceInfo.mDeviceCodecFormat : 0;
        this.mConnectedDevices.remove(makeDeviceListKey);
        this.mDeviceBroker.setA2dpTimeout(str, i2, i);
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private void makeA2dpSrcAvailable(java.lang.String str) {
        int deviceConnectionState = this.mAudioSystem.setDeviceConnectionState(new android.media.AudioDeviceAttributes(android.hardware.audio.common.V2_0.AudioDevice.IN_BLUETOOTH_A2DP, str), 1, 0);
        if (deviceConnectionState != 0) {
            com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("APM failed to make available A2DP source device addr=" + android.media.Utils.anonymizeBluetoothAddress(str) + " error=" + deviceConnectionState).printSlog(1, TAG));
        } else {
            com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("A2DP source device addr=" + android.media.Utils.anonymizeBluetoothAddress(str) + " now available").printSlog(0, TAG));
        }
        this.mConnectedDevices.put(com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(android.hardware.audio.common.V2_0.AudioDevice.IN_BLUETOOTH_A2DP, str), new com.android.server.audio.AudioDeviceInventory.DeviceInfo(android.hardware.audio.common.V2_0.AudioDevice.IN_BLUETOOTH_A2DP, "", str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    /* renamed from: makeA2dpSrcUnavailable, reason: merged with bridge method [inline-methods] */
    public void lambda$disconnectA2dpSink$32(java.lang.String str) {
        android.media.AudioDeviceAttributes audioDeviceAttributes = new android.media.AudioDeviceAttributes(android.hardware.audio.common.V2_0.AudioDevice.IN_BLUETOOTH_A2DP, str);
        this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, 0);
        this.mConnectedDevices.remove(com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(android.hardware.audio.common.V2_0.AudioDevice.IN_BLUETOOTH_A2DP, str));
        this.mDeviceBroker.postCheckCommunicationDeviceRemoval(audioDeviceAttributes);
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private void makeHearingAidDeviceAvailable(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) {
        this.mDeviceBroker.postSetHearingAidVolumeIndex(this.mDeviceBroker.getVssVolumeForDevice(i, 134217728), i);
        this.mDeviceBroker.setBluetoothA2dpOnInt(true, false, str3);
        this.mAudioSystem.setDeviceConnectionState(new android.media.AudioDeviceAttributes(134217728, str, str2), 1, 0);
        this.mConnectedDevices.put(com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(134217728, str), new com.android.server.audio.AudioDeviceInventory.DeviceInfo(134217728, str2, str));
        this.mDeviceBroker.postAccessoryPlugMediaUnmute(134217728);
        this.mDeviceBroker.postApplyVolumeOnDevice(i, 134217728, "makeHearingAidDeviceAvailable");
        setCurrentAudioRouteNameIfPossible(str2, false);
        addAudioDeviceInInventoryIfNeeded(134217728, str, "", com.android.server.audio.BtHelper.getBtDeviceCategory(str));
        android.media.MediaMetrics.Item item = new android.media.MediaMetrics.Item("audio.device.makeHearingAidDeviceAvailable");
        android.media.MediaMetrics.Key key = android.media.MediaMetrics.Property.ADDRESS;
        if (str == null) {
            str = "";
        }
        item.set(key, str).set(android.media.MediaMetrics.Property.DEVICE, android.media.AudioSystem.getDeviceName(134217728)).set(android.media.MediaMetrics.Property.NAME, str2).set(android.media.MediaMetrics.Property.STREAM_TYPE, android.media.AudioSystem.streamToString(i)).record();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    /* renamed from: makeHearingAidDeviceUnavailable, reason: merged with bridge method [inline-methods] */
    public void lambda$disconnectHearingAid$34(java.lang.String str) {
        android.media.AudioDeviceAttributes audioDeviceAttributes = new android.media.AudioDeviceAttributes(134217728, str);
        this.mAudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, 0);
        this.mConnectedDevices.remove(com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(134217728, str));
        setCurrentAudioRouteNameIfPossible(null, false);
        android.media.MediaMetrics.Item item = new android.media.MediaMetrics.Item("audio.device.makeHearingAidDeviceUnavailable");
        android.media.MediaMetrics.Key key = android.media.MediaMetrics.Property.ADDRESS;
        if (str == null) {
            str = "";
        }
        item.set(key, str).set(android.media.MediaMetrics.Property.DEVICE, android.media.AudioSystem.getDeviceName(134217728)).record();
        this.mDeviceBroker.postCheckCommunicationDeviceRemoval(audioDeviceAttributes);
    }

    boolean isHearingAidConnected() {
        return getFirstConnectedDeviceOfTypes(com.google.android.collect.Sets.newHashSet(new java.lang.Integer[]{134217728})) != null;
    }

    private com.android.server.audio.AudioDeviceInventory.DeviceInfo getFirstConnectedDeviceOfTypes(java.util.Set<java.lang.Integer> set) {
        java.util.List<com.android.server.audio.AudioDeviceInventory.DeviceInfo> connectedDevicesOfTypes = getConnectedDevicesOfTypes(set);
        if (connectedDevicesOfTypes.isEmpty()) {
            return null;
        }
        return connectedDevicesOfTypes.get(0);
    }

    private java.util.List<com.android.server.audio.AudioDeviceInventory.DeviceInfo> getConnectedDevicesOfTypes(java.util.Set<java.lang.Integer> set) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mDevicesLock) {
            try {
                for (com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
                    if (set.contains(java.lang.Integer.valueOf(deviceInfo.mDeviceType))) {
                        arrayList.add(deviceInfo);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    android.media.AudioDeviceAttributes getDeviceOfType(int i) {
        com.android.server.audio.AudioDeviceInventory.DeviceInfo firstConnectedDeviceOfTypes = getFirstConnectedDeviceOfTypes(com.google.android.collect.Sets.newHashSet(new java.lang.Integer[]{java.lang.Integer.valueOf(i)}));
        if (firstConnectedDeviceOfTypes == null) {
            return null;
        }
        return new android.media.AudioDeviceAttributes(firstConnectedDeviceOfTypes.mDeviceType, firstConnectedDeviceOfTypes.mDeviceAddress, firstConnectedDeviceOfTypes.mDeviceName);
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private void makeLeAudioDeviceAvailable(com.android.server.audio.AudioDeviceBroker.BtDeviceInfo btDeviceInfo, int i, int i2, java.lang.String str) {
        int i3;
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        boolean z;
        int i4 = btDeviceInfo.mVolume == -1 ? -1 : btDeviceInfo.mVolume * 10;
        int i5 = btDeviceInfo.mAudioSystemDevice;
        if (i5 == 0) {
            i3 = i4;
        } else {
            java.lang.String address = btDeviceInfo.mDevice.getAddress();
            java.lang.String name = com.android.server.audio.BtHelper.getName(btDeviceInfo.mDevice);
            int leAudioDeviceGroupId = this.mDeviceBroker.getLeAudioDeviceGroupId(btDeviceInfo.mDevice);
            if (leAudioDeviceGroupId != -1) {
                java.util.List<android.util.Pair<java.lang.String, java.lang.String>> leAudioGroupAddresses = this.mDeviceBroker.getLeAudioGroupAddresses(leAudioDeviceGroupId);
                if (leAudioGroupAddresses.size() > 1) {
                    for (android.util.Pair<java.lang.String, java.lang.String> pair : leAudioGroupAddresses) {
                        if (!((java.lang.String) pair.first).equals(address)) {
                            str2 = (java.lang.String) pair.first;
                            str3 = (java.lang.String) pair.second;
                            break;
                        }
                    }
                }
            }
            str2 = "";
            str3 = str2;
            if (i5 == 536870914 && name.equals("")) {
                str4 = "Broadcast";
            } else {
                str4 = name;
            }
            this.mDeviceBroker.setBluetoothA2dpOnInt(true, false, str);
            int deviceConnectionState = android.media.AudioSystem.setDeviceConnectionState(new android.media.AudioDeviceAttributes(i5, address, str4), 1, i2);
            if (deviceConnectionState != 0) {
                com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("APM failed to make available LE Audio device addr=" + address + " error=" + deviceConnectionState).printSlog(1, TAG));
                z = false;
            } else {
                com.android.server.utils.EventLogger eventLogger = com.android.server.audio.AudioService.sDeviceLogger;
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("LE Audio ");
                sb.append(android.media.AudioSystem.isInputDevice(i5) ? "source" : "sink");
                sb.append(" device addr=");
                sb.append(android.media.Utils.anonymizeBluetoothAddress(address));
                sb.append(" now available");
                z = false;
                eventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(sb.toString()).printSlog(0, TAG));
            }
            this.mDeviceBroker.clearLeAudioSuspended(true);
            i3 = i4;
            this.mConnectedDevices.put(com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(i5, address), new com.android.server.audio.AudioDeviceInventory.DeviceInfo(i5, str4, address, btDeviceInfo.mDevice.getIdentityAddress(), i2, leAudioDeviceGroupId, str2, str3));
            this.mDeviceBroker.postAccessoryPlugMediaUnmute(i5);
            setCurrentAudioRouteNameIfPossible(str4, z);
            addAudioDeviceInInventoryIfNeeded(i5, address, str2, com.android.server.audio.BtHelper.getBtDeviceCategory(address));
        }
        if (i == -1) {
            return;
        }
        int i6 = i3;
        if (i6 == -1) {
            i6 = this.mDeviceBroker.getVssVolumeForDevice(i, i5);
        }
        this.mDeviceBroker.postSetLeAudioVolumeIndex(i6, this.mDeviceBroker.getMaxVssVolumeForStream(i), i);
        this.mDeviceBroker.postApplyVolumeOnDevice(i, i5, "makeLeAudioDeviceAvailable");
        updateBluetoothPreferredModes_l(btDeviceInfo.mDevice);
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private void makeLeAudioDeviceUnavailableNow(java.lang.String str, int i, int i2) {
        android.media.AudioDeviceAttributes audioDeviceAttributes;
        if (i == 0) {
            audioDeviceAttributes = null;
        } else {
            audioDeviceAttributes = new android.media.AudioDeviceAttributes(i, str);
            int deviceConnectionState = android.media.AudioSystem.setDeviceConnectionState(audioDeviceAttributes, 0, i2);
            if (deviceConnectionState != 0) {
                com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("APM failed to make unavailable LE Audio device addr=" + str + " error=" + deviceConnectionState).printSlog(1, TAG));
            } else {
                com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("LE Audio device addr=" + android.media.Utils.anonymizeBluetoothAddress(str) + " made unavailable").printSlog(0, TAG));
            }
            this.mConnectedDevices.remove(com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(i, str));
        }
        setCurrentAudioRouteNameIfPossible(null, false);
        updateBluetoothPreferredModes_l(null);
        purgeDevicesRoles_l();
        if (audioDeviceAttributes != null) {
            this.mDeviceBroker.postCheckCommunicationDeviceRemoval(audioDeviceAttributes);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private void makeLeAudioDeviceUnavailableLater(java.lang.String str, int i, int i2, int i3) {
        this.mDeviceBroker.setLeAudioSuspended(true, true, "makeLeAudioDeviceUnavailableLater");
        this.mConnectedDevices.remove(com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(i, str));
        this.mDeviceBroker.setLeAudioTimeout(str, i, i2, i3);
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private void setCurrentAudioRouteNameIfPossible(java.lang.String str, boolean z) {
        synchronized (this.mCurAudioRoutes) {
            try {
                if (android.text.TextUtils.equals(this.mCurAudioRoutes.bluetoothName, str)) {
                    return;
                }
                if (str != null || !isCurrentDeviceConnected()) {
                    this.mCurAudioRoutes.bluetoothName = str;
                    this.mDeviceBroker.postReportNewRoutes(z);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private boolean isCurrentDeviceConnected() {
        return this.mConnectedDevices.values().stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$isCurrentDeviceConnected$37;
                lambda$isCurrentDeviceConnected$37 = com.android.server.audio.AudioDeviceInventory.this.lambda$isCurrentDeviceConnected$37((com.android.server.audio.AudioDeviceInventory.DeviceInfo) obj);
                return lambda$isCurrentDeviceConnected$37;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$isCurrentDeviceConnected$37(com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo) {
        return android.text.TextUtils.equals(deviceInfo.mDeviceName, this.mCurAudioRoutes.bluetoothName);
    }

    @com.android.internal.annotations.GuardedBy({"mDevicesLock"})
    private int checkSendBecomingNoisyIntentInt(int i, int i2, int i3) {
        android.media.MediaMetrics.Item item = new android.media.MediaMetrics.Item("audio.device.checkSendBecomingNoisyIntentInt").set(android.media.MediaMetrics.Property.DEVICE, android.media.AudioSystem.getDeviceName(i)).set(android.media.MediaMetrics.Property.STATE, i2 == 1 ? "connected" : "disconnected");
        int i4 = 0;
        if (i2 != 0) {
            android.util.Log.i(TAG, "not sending NOISY: state=" + i2);
            item.set(android.media.MediaMetrics.Property.DELAY_MS, 0).record();
            return 0;
        }
        if (!BECOMING_NOISY_INTENT_DEVICES_SET.contains(java.lang.Integer.valueOf(i))) {
            android.util.Log.i(TAG, "not sending NOISY: device=0x" + java.lang.Integer.toHexString(i) + " not in set " + BECOMING_NOISY_INTENT_DEVICES_SET);
            item.set(android.media.MediaMetrics.Property.DELAY_MS, 0).record();
            return 0;
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        for (com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo : this.mConnectedDevices.values()) {
            if (!android.media.AudioSystem.isInputDevice(deviceInfo.mDeviceType) && BECOMING_NOISY_INTENT_DEVICES_SET.contains(java.lang.Integer.valueOf(deviceInfo.mDeviceType))) {
                hashSet.add(java.lang.Integer.valueOf(deviceInfo.mDeviceType));
                android.util.Log.i(TAG, "NOISY: adding 0x" + java.lang.Integer.toHexString(deviceInfo.mDeviceType));
            }
        }
        if (i3 == 0) {
            i3 = this.mDeviceBroker.getDeviceForStream(3);
            android.util.Log.i(TAG, "NOISY: musicDevice changing from NONE to 0x" + java.lang.Integer.toHexString(i3));
        }
        boolean isInCommunication = this.mDeviceBroker.isInCommunication();
        boolean isSingleAudioDeviceType = android.media.AudioSystem.isSingleAudioDeviceType(hashSet, i);
        boolean hasMediaDynamicPolicy = this.mDeviceBroker.hasMediaDynamicPolicy();
        if ((i == i3 || isInCommunication) && isSingleAudioDeviceType && !hasMediaDynamicPolicy && i3 != 32768) {
            if (!this.mAudioSystem.isStreamActive(3, 0) && !this.mDeviceBroker.hasAudioFocusUsers()) {
                com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("dropping ACTION_AUDIO_BECOMING_NOISY").printLog(TAG));
                item.set(android.media.MediaMetrics.Property.DELAY_MS, 0).record();
                return 0;
            }
            this.mDeviceBroker.postBroadcastBecomingNoisy();
            i4 = 1000;
        } else {
            android.util.Log.i(TAG, "not sending NOISY: device:0x" + java.lang.Integer.toHexString(i) + " musicDevice:0x" + java.lang.Integer.toHexString(i3) + " inComm:" + isInCommunication + " mediaPolicy:" + hasMediaDynamicPolicy + " singleDevice:" + isSingleAudioDeviceType);
        }
        item.set(android.media.MediaMetrics.Property.DELAY_MS, java.lang.Integer.valueOf(i4)).record();
        return i4;
    }

    private void sendDeviceConnectionIntent(int i, int i2, java.lang.String str, java.lang.String str2) {
        android.content.Intent intent = new android.content.Intent();
        int i3 = 0;
        switch (i) {
            case android.hardware.audio.common.V2_0.AudioDevice.IN_USB_HEADSET /* -2113929216 */:
                if (android.media.AudioSystem.getDeviceConnectionState(67108864, "") == 1) {
                    intent.setAction("android.intent.action.HEADSET_PLUG");
                    intent.putExtra("microphone", 1);
                    break;
                } else {
                    return;
                }
            case 4:
                intent.setAction("android.intent.action.HEADSET_PLUG");
                intent.putExtra("microphone", 1);
                break;
            case 8:
            case 131072:
                intent.setAction("android.intent.action.HEADSET_PLUG");
                intent.putExtra("microphone", 0);
                break;
            case 1024:
            case 262144:
            case 262145:
                configureHdmiPlugIntent(intent, i2);
                break;
            case 67108864:
                intent.setAction("android.intent.action.HEADSET_PLUG");
                if (android.media.AudioSystem.getDeviceConnectionState(android.hardware.audio.common.V2_0.AudioDevice.IN_USB_HEADSET, "") == 1) {
                    i3 = 1;
                }
                intent.putExtra("microphone", i3);
                break;
        }
        if (intent.getAction() == null) {
            return;
        }
        intent.putExtra("state", i2);
        intent.putExtra(CONNECT_INTENT_KEY_ADDRESS, str);
        intent.putExtra(CONNECT_INTENT_KEY_PORT_NAME, str2);
        intent.addFlags(1073741824);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mDeviceBroker.broadcastStickyIntentToCurrentProfileGroup(intent);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void updateAudioRoutes(int i, int i2) {
        int i3;
        int i4;
        switch (i) {
            case 4:
                i3 = 1;
                break;
            case 8:
            case 131072:
                i3 = 2;
                break;
            case 1024:
            case 262144:
            case 262145:
                i3 = 8;
                break;
            case 4096:
                i3 = 4;
                break;
            case 16384:
            case 67108864:
                i3 = 16;
                break;
            default:
                i3 = 0;
                break;
        }
        synchronized (this.mCurAudioRoutes) {
            try {
                if (i3 == 0) {
                    return;
                }
                int i5 = this.mCurAudioRoutes.mainType;
                if (i2 != 0) {
                    i4 = i3 | i5;
                } else {
                    i4 = (~i3) & i5;
                }
                if (i4 != this.mCurAudioRoutes.mainType) {
                    this.mCurAudioRoutes.mainType = i4;
                    this.mDeviceBroker.postReportNewRoutes(false);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void configureHdmiPlugIntent(android.content.Intent intent, int i) {
        intent.setAction("android.media.action.HDMI_AUDIO_PLUG");
        intent.putExtra("android.media.extra.AUDIO_PLUG_STATE", i);
        if (i != 1) {
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int listAudioPorts = android.media.AudioSystem.listAudioPorts(arrayList, new int[1]);
        if (listAudioPorts != 0) {
            android.util.Log.e(TAG, "listAudioPorts error " + listAudioPorts + " in configureHdmiPlugIntent");
            return;
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            android.media.AudioDevicePort audioDevicePort = (android.media.AudioPort) it.next();
            if (audioDevicePort instanceof android.media.AudioDevicePort) {
                android.media.AudioDevicePort audioDevicePort2 = audioDevicePort;
                if (audioDevicePort2.type() == 1024 || audioDevicePort2.type() == 262144 || audioDevicePort2.type() == 262145) {
                    int[] filterPublicFormats = android.media.AudioFormat.filterPublicFormats(audioDevicePort2.formats());
                    if (filterPublicFormats.length > 0) {
                        java.util.ArrayList arrayList2 = new java.util.ArrayList(1);
                        for (int i2 : filterPublicFormats) {
                            if (i2 != 0) {
                                arrayList2.add(java.lang.Integer.valueOf(i2));
                            }
                        }
                        intent.putExtra("android.media.extra.ENCODINGS", arrayList2.stream().mapToInt(new java.util.function.ToIntFunction() { // from class: com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticLambda27
                            @Override // java.util.function.ToIntFunction
                            public final int applyAsInt(java.lang.Object obj) {
                                int intValue;
                                intValue = ((java.lang.Integer) obj).intValue();
                                return intValue;
                            }
                        }).toArray());
                    }
                    int i3 = 0;
                    for (int i4 : audioDevicePort2.channelMasks()) {
                        int channelCountFromOutChannelMask = android.media.AudioFormat.channelCountFromOutChannelMask(i4);
                        if (channelCountFromOutChannelMask > i3) {
                            i3 = channelCountFromOutChannelMask;
                        }
                    }
                    intent.putExtra("android.media.extra.MAX_CHANNEL_COUNT", i3);
                }
            }
        }
    }

    private void dispatchPreferredDevice(int i, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        int beginBroadcast = this.mPrefDevDispatchers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                if (!((java.lang.Boolean) this.mPrefDevDispatchers.getBroadcastCookie(i2)).booleanValue()) {
                    list = this.mDeviceBroker.anonymizeAudioDeviceAttributesListUnchecked(list);
                }
                this.mPrefDevDispatchers.getBroadcastItem(i2).dispatchPrefDevicesChanged(i, list);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mPrefDevDispatchers.finishBroadcast();
    }

    private void dispatchNonDefaultDevice(int i, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        int beginBroadcast = this.mNonDefDevDispatchers.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                if (!((java.lang.Boolean) this.mNonDefDevDispatchers.getBroadcastCookie(i2)).booleanValue()) {
                    list = this.mDeviceBroker.anonymizeAudioDeviceAttributesListUnchecked(list);
                }
                this.mNonDefDevDispatchers.getBroadcastItem(i2).dispatchNonDefDevicesChanged(i, list);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mNonDefDevDispatchers.finishBroadcast();
    }

    private void dispatchDevicesRoleForCapturePreset(int i, int i2, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        int beginBroadcast = this.mDevRoleCapturePresetDispatchers.beginBroadcast();
        for (int i3 = 0; i3 < beginBroadcast; i3++) {
            try {
                if (!((java.lang.Boolean) this.mDevRoleCapturePresetDispatchers.getBroadcastCookie(i3)).booleanValue()) {
                    list = this.mDeviceBroker.anonymizeAudioDeviceAttributesListUnchecked(list);
                }
                this.mDevRoleCapturePresetDispatchers.getBroadcastItem(i3).dispatchDevicesRoleChanged(i, i2, list);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mDevRoleCapturePresetDispatchers.finishBroadcast();
    }

    java.util.List<java.lang.String> getDeviceIdentityAddresses(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.lang.String makeDeviceListKey = com.android.server.audio.AudioDeviceInventory.DeviceInfo.makeDeviceListKey(audioDeviceAttributes.getInternalType(), audioDeviceAttributes.getAddress());
        synchronized (this.mDevicesLock) {
            try {
                com.android.server.audio.AudioDeviceInventory.DeviceInfo deviceInfo = this.mConnectedDevices.get(makeDeviceListKey);
                if (deviceInfo != null) {
                    if (!deviceInfo.mDeviceIdentityAddress.isEmpty()) {
                        arrayList.add(deviceInfo.mDeviceIdentityAddress);
                    }
                    if (!deviceInfo.mPeerIdentityDeviceAddress.isEmpty() && !deviceInfo.mPeerIdentityDeviceAddress.equals(deviceInfo.mDeviceIdentityAddress)) {
                        arrayList.add(deviceInfo.mPeerIdentityDeviceAddress);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    java.lang.String getDeviceSettings() {
        java.lang.String sb;
        synchronized (this.mDeviceInventoryLock) {
            try {
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder(this.mDeviceInventory.size() * com.android.server.audio.AdiDeviceState.getPeristedMaxSize());
                java.util.Iterator<com.android.server.audio.AdiDeviceState> it = this.mDeviceInventory.values().iterator();
                if (it.hasNext()) {
                    sb2.append(it.next().toPersistableString());
                }
                while (it.hasNext()) {
                    sb2.append(SETTING_DEVICE_SEPARATOR_CHAR);
                    sb2.append(it.next().toPersistableString());
                }
                sb = sb2.toString();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return sb;
    }

    void setDeviceSettings(java.lang.String str) {
        clearDeviceInventory();
        java.util.Objects.requireNonNull(str);
        for (java.lang.String str2 : android.text.TextUtils.split(str, SETTING_DEVICE_SEPARATOR)) {
            com.android.server.audio.AdiDeviceState fromPersistedString = com.android.server.audio.AdiDeviceState.fromPersistedString(str2);
            if (fromPersistedString != null) {
                addOrUpdateDeviceSAStateInInventory(fromPersistedString);
                addOrUpdateAudioDeviceCategoryInInventory(fromPersistedString);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean isA2dpDeviceConnected(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice) {
        java.util.Iterator<com.android.server.audio.AudioDeviceInventory.DeviceInfo> it = getConnectedDevicesOfTypes(com.google.android.collect.Sets.newHashSet(new java.lang.Integer[]{128})).iterator();
        while (it.hasNext()) {
            if (it.next().mDeviceAddress.equals(bluetoothDevice.getAddress())) {
                return true;
            }
        }
        return false;
    }
}
