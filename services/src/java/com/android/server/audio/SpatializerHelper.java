package com.android.server.audio;

/* loaded from: classes.dex */
public class SpatializerHelper {
    private static final boolean DEBUG = true;
    private static final boolean DEBUG_MORE = false;
    private static final java.lang.String METRICS_DEVICE_PREFIX = "audio.spatializer.device.";
    static final int STATE_DISABLED_AVAILABLE = 6;
    static final int STATE_DISABLED_UNAVAILABLE = 3;
    static final int STATE_ENABLED_AVAILABLE = 5;
    static final int STATE_ENABLED_UNAVAILABLE = 4;
    static final int STATE_NOT_SUPPORTED = 1;
    static final int STATE_UNINITIALIZED = 0;
    private static final java.lang.String TAG = "AS.SpatializerHelper";

    @android.annotation.NonNull
    private final com.android.server.audio.AudioSystemAdapter mASA;

    @android.annotation.NonNull
    private final com.android.server.audio.AudioService mAudioService;

    @com.android.internal.annotations.VisibleForTesting
    boolean mBinauralEnabledDefault;

    @android.annotation.NonNull
    private final com.android.server.audio.AudioDeviceBroker mDeviceBroker;

    @android.annotation.Nullable
    private com.android.server.audio.SpatializerHelper.HelperDynamicSensorCallback mDynSensorCallback;

    @com.android.internal.annotations.VisibleForTesting
    boolean mHeadTrackingEnabledDefault;

    @android.annotation.Nullable
    private android.hardware.SensorManager mSensorManager;

    @android.annotation.Nullable
    private android.media.ISpatializer mSpat;

    @android.annotation.Nullable
    private com.android.server.audio.SpatializerHelper.SpatializerCallback mSpatCallback;

    @com.android.internal.annotations.VisibleForTesting
    boolean mTransauralEnabledDefault;
    static final android.util.SparseIntArray SPAT_MODE_FOR_DEVICE_TYPE = new android.util.SparseIntArray(14) { // from class: com.android.server.audio.SpatializerHelper.1
        {
            append(2, 1);
            append(3, 0);
            append(4, 0);
            append(8, 0);
            append(13, 1);
            append(12, 1);
            append(11, 1);
            append(22, 0);
            append(5, 1);
            append(6, 1);
            append(19, 1);
            append(26, 0);
            append(27, 1);
            append(30, 0);
        }
    };
    private static final android.media.AudioAttributes DEFAULT_ATTRIBUTES = new android.media.AudioAttributes.Builder().setUsage(1).build();
    private static final android.media.AudioFormat DEFAULT_FORMAT = new android.media.AudioFormat.Builder().setEncoding(2).setSampleRate(48000).setChannelMask(android.hardware.audio.common.V2_0.AudioChannelMask.IN_6).build();
    private static java.util.ArrayList<android.media.AudioDeviceAttributes> sRoutingDevices = new java.util.ArrayList<>(0);
    private int mState = 0;
    private boolean mFeatureEnabled = false;
    private int mSpatLevel = 0;
    private int mCapableSpatLevel = 0;
    private boolean mTransauralSupported = false;
    private boolean mBinauralSupported = false;
    private boolean mIsHeadTrackingSupported = false;
    private int[] mSupportedHeadTrackingModes = new int[0];
    private int mActualHeadTrackingMode = -2;
    private int mDesiredHeadTrackingMode = 1;
    private boolean mHeadTrackerAvailable = false;
    private int mDesiredHeadTrackingModeWhenEnabled = 1;
    private int mSpatOutput = 0;

    @android.annotation.Nullable
    private com.android.server.audio.SpatializerHelper.SpatializerHeadTrackingCallback mSpatHeadTrackingCallback = new com.android.server.audio.SpatializerHelper.SpatializerHeadTrackingCallback();
    private final java.util.ArrayList<java.lang.Integer> mSACapableDeviceTypes = new java.util.ArrayList<>(0);
    final android.os.RemoteCallbackList<android.media.ISpatializerCallback> mStateCallbacks = new android.os.RemoteCallbackList<>();
    final android.os.RemoteCallbackList<android.media.ISpatializerHeadTrackingModeCallback> mHeadTrackingModeCallbacks = new android.os.RemoteCallbackList<>();
    final android.os.RemoteCallbackList<android.media.ISpatializerHeadTrackerAvailableCallback> mHeadTrackerCallbacks = new android.os.RemoteCallbackList<>();
    final android.os.RemoteCallbackList<android.media.ISpatializerHeadToSoundStagePoseCallback> mHeadPoseCallbacks = new android.os.RemoteCallbackList<>();
    final android.os.RemoteCallbackList<android.media.ISpatializerOutputCallback> mOutputCallbacks = new android.os.RemoteCallbackList<>();

    private static void logd(java.lang.String str) {
        android.util.Log.i(TAG, str);
    }

    SpatializerHelper(@android.annotation.NonNull com.android.server.audio.AudioService audioService, @android.annotation.NonNull com.android.server.audio.AudioSystemAdapter audioSystemAdapter, @android.annotation.NonNull com.android.server.audio.AudioDeviceBroker audioDeviceBroker, boolean z, boolean z2, boolean z3) {
        this.mAudioService = audioService;
        this.mASA = audioSystemAdapter;
        this.mDeviceBroker = audioDeviceBroker;
        this.mBinauralEnabledDefault = z;
        this.mTransauralEnabledDefault = z2;
        this.mHeadTrackingEnabledDefault = z3;
    }

    synchronized void init(boolean z) {
        byte[] supportedLevels;
        loglogi("init effectExpected=" + z);
        if (!z) {
            loglogi("init(): setting state to STATE_NOT_SUPPORTED due to effect not expected");
            this.mState = 1;
            return;
        }
        if (this.mState != 0) {
            throw new java.lang.IllegalStateException(logloge("init() called in state " + this.mState));
        }
        this.mSpatCallback = new com.android.server.audio.SpatializerHelper.SpatializerCallback();
        android.media.ISpatializer spatializer = android.media.AudioSystem.getSpatializer(this.mSpatCallback);
        if (spatializer == null) {
            loglogi("init(): No Spatializer found");
            this.mState = 1;
            return;
        }
        try {
            try {
                resetCapabilities();
                try {
                    supportedLevels = spatializer.getSupportedLevels();
                } catch (android.os.RemoteException e) {
                    resetCapabilities();
                    spatializer.release();
                }
            } catch (java.lang.Throwable th) {
                try {
                    spatializer.release();
                } catch (android.os.RemoteException e2) {
                }
                throw th;
            }
        } catch (android.os.RemoteException e3) {
        }
        if (supportedLevels == null || supportedLevels.length == 0 || (supportedLevels.length == 1 && supportedLevels[0] == 0)) {
            logloge("init(): found Spatializer is useless");
            this.mState = 1;
            try {
                spatializer.release();
            } catch (android.os.RemoteException e4) {
            }
            return;
        }
        int length = supportedLevels.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            byte b = supportedLevels[i];
            loglogi("init(): found support for level: " + ((int) b));
            if (b == 1) {
                loglogi("init(): setting capable level to LEVEL_MULTICHANNEL");
                this.mCapableSpatLevel = b;
                break;
            }
            i++;
        }
        this.mIsHeadTrackingSupported = spatializer.isHeadTrackingSupported();
        if (this.mIsHeadTrackingSupported) {
            byte[] supportedHeadTrackingModes = spatializer.getSupportedHeadTrackingModes();
            java.util.ArrayList arrayList = new java.util.ArrayList(0);
            for (byte b2 : supportedHeadTrackingModes) {
                switch (b2) {
                    case 0:
                    case 1:
                        break;
                    case 2:
                    case 3:
                        arrayList.add(java.lang.Integer.valueOf(headTrackingModeTypeToSpatializerInt(b2)));
                        break;
                    default:
                        android.util.Log.e(TAG, "Unexpected head tracking mode:" + ((int) b2), new java.lang.IllegalArgumentException("invalid mode"));
                        break;
                }
            }
            this.mSupportedHeadTrackingModes = new int[arrayList.size()];
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                this.mSupportedHeadTrackingModes[i2] = ((java.lang.Integer) arrayList.get(i2)).intValue();
            }
            this.mActualHeadTrackingMode = headTrackingModeTypeToSpatializerInt(spatializer.getActualHeadTrackingMode());
        } else {
            this.mDesiredHeadTrackingModeWhenEnabled = -2;
            this.mDesiredHeadTrackingMode = -2;
        }
        for (byte b3 : spatializer.getSupportedModes()) {
            switch (b3) {
                case 0:
                    this.mBinauralSupported = true;
                    break;
                case 1:
                    this.mTransauralSupported = true;
                    break;
                default:
                    logloge("init(): Spatializer reports unknown supported mode:" + ((int) b3));
                    break;
            }
        }
        if (!this.mBinauralSupported && !this.mTransauralSupported) {
            this.mState = 1;
            try {
                spatializer.release();
            } catch (android.os.RemoteException e5) {
            }
            return;
        }
        for (int i3 = 0; i3 < SPAT_MODE_FOR_DEVICE_TYPE.size(); i3++) {
            int valueAt = SPAT_MODE_FOR_DEVICE_TYPE.valueAt(i3);
            if ((valueAt == 0 && this.mBinauralSupported) || (valueAt == 1 && this.mTransauralSupported)) {
                this.mSACapableDeviceTypes.add(java.lang.Integer.valueOf(SPAT_MODE_FOR_DEVICE_TYPE.keyAt(i3)));
            }
        }
        for (com.android.server.audio.AdiDeviceState adiDeviceState : this.mDeviceBroker.getImmutableDeviceInventory()) {
            if (isSADevice(adiDeviceState)) {
                logDeviceState(adiDeviceState, "setSADeviceSettings");
            }
        }
        addCompatibleAudioDevice(new android.media.AudioDeviceAttributes(2, ""), false);
        addCompatibleAudioDevice(new android.media.AudioDeviceAttributes(8, ""), false);
        spatializer.release();
        if (this.mCapableSpatLevel == 0) {
            this.mState = 1;
        } else {
            this.mState = 3;
            sRoutingDevices = getRoutingDevices(DEFAULT_ATTRIBUTES);
        }
    }

    synchronized void reset(boolean z) {
        loglogi("Resetting featureEnabled=" + z);
        releaseSpat();
        this.mState = 0;
        this.mSpatLevel = 0;
        this.mActualHeadTrackingMode = -2;
        init(true);
        setSpatializerEnabledInt(z);
    }

    private void resetCapabilities() {
        this.mCapableSpatLevel = 0;
        this.mBinauralSupported = false;
        this.mTransauralSupported = false;
        this.mIsHeadTrackingSupported = false;
        this.mSupportedHeadTrackingModes = new int[0];
    }

    synchronized void onRoutingUpdated() {
        boolean z;
        if (this.mFeatureEnabled) {
            switch (this.mState) {
                case 0:
                case 1:
                    return;
                default:
                    sRoutingDevices = getRoutingDevices(DEFAULT_ATTRIBUTES);
                    if (sRoutingDevices.isEmpty()) {
                        logloge("onRoutingUpdated: no device, no Spatial Audio");
                        setDispatchAvailableState(false);
                        return;
                    }
                    android.media.AudioDeviceAttributes audioDeviceAttributes = sRoutingDevices.get(0);
                    if (android.media.AudioSystem.isBluetoothDevice(audioDeviceAttributes.getInternalType())) {
                        addWirelessDeviceIfNew(audioDeviceAttributes);
                    }
                    android.util.Pair<java.lang.Boolean, java.lang.Boolean> evaluateState = evaluateState(audioDeviceAttributes);
                    if (((java.lang.Boolean) evaluateState.second).booleanValue()) {
                        z = canBeSpatializedOnDevice(DEFAULT_ATTRIBUTES, DEFAULT_FORMAT, sRoutingDevices);
                        loglogi("onRoutingUpdated: can spatialize media 5.1:" + z + " on device:" + audioDeviceAttributes);
                        setDispatchAvailableState(z);
                    } else {
                        loglogi("onRoutingUpdated: device:" + audioDeviceAttributes + " not available for Spatial Audio");
                        setDispatchAvailableState(false);
                        z = false;
                    }
                    boolean z2 = z && ((java.lang.Boolean) evaluateState.first).booleanValue();
                    if (z2) {
                        loglogi("Enabling Spatial Audio since enabled for media device:" + audioDeviceAttributes);
                    } else {
                        loglogi("Disabling Spatial Audio since disabled for media device:" + audioDeviceAttributes);
                    }
                    if (this.mSpat != null) {
                        byte b = z2 ? (byte) 1 : (byte) 0;
                        loglogi("Setting spatialization level to: " + ((int) b));
                        try {
                            this.mSpat.setLevel(b);
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(TAG, "onRoutingUpdated() Can't set spatializer level", e);
                            postReset();
                            return;
                        }
                    }
                    setDispatchFeatureEnabledState(z2, "onRoutingUpdated");
                    if (this.mDesiredHeadTrackingMode != -2 && this.mDesiredHeadTrackingMode != -1) {
                        postInitSensors();
                    }
                    return;
            }
        }
    }

    private void postReset() {
        this.mAudioService.postResetSpatializer();
    }

    private final class SpatializerCallback extends android.media.INativeSpatializerCallback.Stub {
        private SpatializerCallback() {
        }

        public void onLevelChanged(byte b) {
            com.android.server.audio.SpatializerHelper.loglogi("SpatializerCallback.onLevelChanged level:" + ((int) b));
            synchronized (com.android.server.audio.SpatializerHelper.this) {
                com.android.server.audio.SpatializerHelper.this.mSpatLevel = com.android.server.audio.SpatializerHelper.spatializationLevelToSpatializerInt(b);
            }
            com.android.server.audio.SpatializerHelper.this.postInitSensors();
        }

        public void onOutputChanged(int i) {
            int i2;
            com.android.server.audio.SpatializerHelper.loglogi("SpatializerCallback.onOutputChanged output:" + i);
            synchronized (com.android.server.audio.SpatializerHelper.this) {
                i2 = com.android.server.audio.SpatializerHelper.this.mSpatOutput;
                com.android.server.audio.SpatializerHelper.this.mSpatOutput = i;
            }
            if (i2 != i) {
                com.android.server.audio.SpatializerHelper.this.dispatchOutputUpdate(i);
            }
        }
    }

    private final class SpatializerHeadTrackingCallback extends android.media.ISpatializerHeadTrackingCallback.Stub {
        private SpatializerHeadTrackingCallback() {
        }

        public void onHeadTrackingModeChanged(byte b) {
            int i;
            int i2;
            synchronized (this) {
                i = com.android.server.audio.SpatializerHelper.this.mActualHeadTrackingMode;
                com.android.server.audio.SpatializerHelper.this.mActualHeadTrackingMode = com.android.server.audio.SpatializerHelper.headTrackingModeTypeToSpatializerInt(b);
                i2 = com.android.server.audio.SpatializerHelper.this.mActualHeadTrackingMode;
            }
            com.android.server.audio.SpatializerHelper.loglogi("SpatializerHeadTrackingCallback.onHeadTrackingModeChanged mode:" + android.media.Spatializer.headtrackingModeToString(i2));
            if (i != i2) {
                com.android.server.audio.SpatializerHelper.this.dispatchActualHeadTrackingMode(i2);
            }
        }

        public void onHeadToSoundStagePoseUpdated(float[] fArr) {
            if (fArr == null) {
                android.util.Log.e(com.android.server.audio.SpatializerHelper.TAG, "SpatializerHeadTrackingCallback.onHeadToStagePoseUpdatednull transform");
                return;
            }
            if (fArr.length != 6) {
                android.util.Log.e(com.android.server.audio.SpatializerHelper.TAG, "SpatializerHeadTrackingCallback.onHeadToStagePoseUpdated invalid transform length" + fArr.length);
                return;
            }
            com.android.server.audio.SpatializerHelper.this.dispatchPoseUpdate(fArr);
        }
    }

    private final class HelperDynamicSensorCallback extends android.hardware.SensorManager.DynamicSensorCallback {
        private HelperDynamicSensorCallback() {
        }

        @Override // android.hardware.SensorManager.DynamicSensorCallback
        public void onDynamicSensorConnected(android.hardware.Sensor sensor) {
            com.android.server.audio.SpatializerHelper.this.postInitSensors();
        }

        @Override // android.hardware.SensorManager.DynamicSensorCallback
        public void onDynamicSensorDisconnected(android.hardware.Sensor sensor) {
            com.android.server.audio.SpatializerHelper.this.postInitSensors();
        }
    }

    @android.annotation.NonNull
    synchronized java.util.List<android.media.AudioDeviceAttributes> getCompatibleAudioDevices() {
        java.util.ArrayList arrayList;
        arrayList = new java.util.ArrayList();
        for (com.android.server.audio.AdiDeviceState adiDeviceState : this.mDeviceBroker.getImmutableDeviceInventory()) {
            if (adiDeviceState.isSAEnabled() && isSADevice(adiDeviceState)) {
                arrayList.add(adiDeviceState.getAudioDeviceAttributes());
            }
        }
        return arrayList;
    }

    synchronized void addCompatibleAudioDevice(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        addCompatibleAudioDevice(audioDeviceAttributes, true);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void addCompatibleAudioDevice(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, boolean z) {
        if (!isDeviceCompatibleWithSpatializationModes(audioDeviceAttributes)) {
            return;
        }
        loglogi("addCompatibleAudioDevice: dev=" + audioDeviceAttributes);
        com.android.server.audio.AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes = findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes);
        if (findSACompatibleDeviceStateForAudioDeviceAttributes != null) {
            if (z && !findSACompatibleDeviceStateForAudioDeviceAttributes.isSAEnabled()) {
                findSACompatibleDeviceStateForAudioDeviceAttributes.setSAEnabled(true);
            } else {
                findSACompatibleDeviceStateForAudioDeviceAttributes = null;
            }
        } else {
            int canonicalDeviceType = getCanonicalDeviceType(audioDeviceAttributes.getType(), audioDeviceAttributes.getInternalType());
            if (canonicalDeviceType == 0) {
                android.util.Log.e(TAG, "addCompatibleAudioDevice with incompatible AudioDeviceAttributes " + audioDeviceAttributes);
                return;
            }
            findSACompatibleDeviceStateForAudioDeviceAttributes = new com.android.server.audio.AdiDeviceState(canonicalDeviceType, audioDeviceAttributes.getInternalType(), audioDeviceAttributes.getAddress());
            initSAState(findSACompatibleDeviceStateForAudioDeviceAttributes);
            this.mDeviceBroker.addOrUpdateDeviceSAStateInInventory(findSACompatibleDeviceStateForAudioDeviceAttributes);
        }
        if (findSACompatibleDeviceStateForAudioDeviceAttributes != null) {
            onRoutingUpdated();
            this.mDeviceBroker.postPersistAudioDeviceSettings();
            logDeviceState(findSACompatibleDeviceStateForAudioDeviceAttributes, "addCompatibleAudioDevice");
        }
    }

    private void initSAState(com.android.server.audio.AdiDeviceState adiDeviceState) {
        boolean z;
        if (adiDeviceState == null) {
            return;
        }
        int i = SPAT_MODE_FOR_DEVICE_TYPE.get(adiDeviceState.getDeviceType(), Integer.MIN_VALUE);
        if (i == 0) {
            z = this.mBinauralEnabledDefault;
        } else if (i == 1) {
            z = this.mTransauralEnabledDefault;
        } else {
            z = false;
        }
        adiDeviceState.setSAEnabled(z);
        adiDeviceState.setHeadTrackerEnabled(this.mHeadTrackingEnabledDefault);
    }

    static void logDeviceState(com.android.server.audio.AdiDeviceState adiDeviceState, java.lang.String str) {
        new android.media.MediaMetrics.Item(METRICS_DEVICE_PREFIX + android.media.AudioSystem.getDeviceName(android.media.AudioDeviceInfo.convertDeviceTypeToInternalDevice(adiDeviceState.getDeviceType()))).set(android.media.MediaMetrics.Property.ADDRESS, adiDeviceState.getDeviceAddress()).set(android.media.MediaMetrics.Property.ENABLED, adiDeviceState.isSAEnabled() ? "true" : "false").set(android.media.MediaMetrics.Property.EVENT, android.text.TextUtils.emptyIfNull(str)).set(android.media.MediaMetrics.Property.HAS_HEAD_TRACKER, adiDeviceState.hasHeadTracker() ? "true" : "false").set(android.media.MediaMetrics.Property.HEAD_TRACKER_ENABLED, adiDeviceState.isHeadTrackerEnabled() ? "true" : "false").record();
    }

    synchronized void removeCompatibleAudioDevice(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        loglogi("removeCompatibleAudioDevice: dev=" + audioDeviceAttributes);
        com.android.server.audio.AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes = findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes);
        if (findSACompatibleDeviceStateForAudioDeviceAttributes != null && findSACompatibleDeviceStateForAudioDeviceAttributes.isSAEnabled()) {
            findSACompatibleDeviceStateForAudioDeviceAttributes.setSAEnabled(false);
            onRoutingUpdated();
            this.mDeviceBroker.postPersistAudioDeviceSettings();
            logDeviceState(findSACompatibleDeviceStateForAudioDeviceAttributes, "removeCompatibleAudioDevice");
        }
    }

    private static int getCanonicalDeviceType(int i, int i2) {
        if (android.media.AudioSystem.isBluetoothDevice(i2)) {
            return i;
        }
        int i3 = SPAT_MODE_FOR_DEVICE_TYPE.get(i, Integer.MIN_VALUE);
        if (i3 == 1) {
            return 2;
        }
        if (i3 == 0) {
            return 4;
        }
        return 0;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    private com.android.server.audio.AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        com.android.server.audio.AdiDeviceState findDeviceStateForAudioDeviceAttributes = this.mDeviceBroker.findDeviceStateForAudioDeviceAttributes(audioDeviceAttributes, getCanonicalDeviceType(audioDeviceAttributes.getType(), audioDeviceAttributes.getInternalType()));
        if (findDeviceStateForAudioDeviceAttributes == null || !isSADevice(findDeviceStateForAudioDeviceAttributes)) {
            return null;
        }
        return findDeviceStateForAudioDeviceAttributes;
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b1, code lost:
    
        if (r5.mBinauralSupported == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b3, code lost:
    
        r2 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private synchronized android.util.Pair<java.lang.Boolean, java.lang.Boolean> evaluateState(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        int type = audioDeviceAttributes.getType();
        boolean z = false;
        if (!this.mSACapableDeviceTypes.contains(java.lang.Integer.valueOf(type))) {
            android.util.Log.i(TAG, "Device incompatible with Spatial Audio dev:" + audioDeviceAttributes);
            return new android.util.Pair<>(false, false);
        }
        int i = SPAT_MODE_FOR_DEVICE_TYPE.get(type, Integer.MIN_VALUE);
        if (i == Integer.MIN_VALUE) {
            android.util.Log.e(TAG, "no spatialization mode found for device type:" + type);
            return new android.util.Pair<>(false, false);
        }
        com.android.server.audio.AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes = findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes);
        if (findSACompatibleDeviceStateForAudioDeviceAttributes == null) {
            android.util.Log.i(TAG, "no spatialization device state found for Spatial Audio device:" + audioDeviceAttributes);
            return new android.util.Pair<>(false, false);
        }
        if (!android.media.AudioSystem.isBluetoothDevice(type)) {
            z = true;
        } else if (findSACompatibleDeviceStateForAudioDeviceAttributes.getAudioDeviceCategory() != 0 && findSACompatibleDeviceStateForAudioDeviceAttributes.getAudioDeviceCategory() != 3) {
        }
        return new android.util.Pair<>(java.lang.Boolean.valueOf(findSACompatibleDeviceStateForAudioDeviceAttributes.isSAEnabled()), java.lang.Boolean.valueOf(z));
    }

    private synchronized void addWirelessDeviceIfNew(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        if (isDeviceCompatibleWithSpatializationModes(audioDeviceAttributes)) {
            if (findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes) == null) {
                int canonicalDeviceType = getCanonicalDeviceType(audioDeviceAttributes.getType(), audioDeviceAttributes.getInternalType());
                if (canonicalDeviceType == 0) {
                    android.util.Log.e(TAG, "addWirelessDeviceIfNew with incompatible AudioDeviceAttributes " + audioDeviceAttributes);
                    return;
                }
                com.android.server.audio.AdiDeviceState adiDeviceState = new com.android.server.audio.AdiDeviceState(canonicalDeviceType, audioDeviceAttributes.getInternalType(), audioDeviceAttributes.getAddress());
                initSAState(adiDeviceState);
                this.mDeviceBroker.addOrUpdateDeviceSAStateInInventory(adiDeviceState);
                this.mDeviceBroker.postPersistAudioDeviceSettings();
                logDeviceState(adiDeviceState, "addWirelessDeviceIfNew");
            }
        }
    }

    synchronized boolean isEnabled() {
        switch (this.mState) {
            case 0:
            case 1:
            case 3:
            case 6:
                return false;
            case 2:
            case 4:
            case 5:
            default:
                return true;
        }
    }

    synchronized boolean isAvailable() {
        switch (this.mState) {
            case 0:
            case 1:
            case 3:
            case 4:
                return false;
            case 2:
            default:
                return true;
        }
    }

    synchronized void refreshDevice(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        try {
            com.android.server.audio.AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes = findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes);
            if (isAvailableForAdiDeviceState(findSACompatibleDeviceStateForAudioDeviceAttributes)) {
                addCompatibleAudioDevice(audioDeviceAttributes, findSACompatibleDeviceStateForAudioDeviceAttributes.isSAEnabled());
                setHeadTrackerEnabled(findSACompatibleDeviceStateForAudioDeviceAttributes.isHeadTrackerEnabled(), audioDeviceAttributes);
            } else {
                removeCompatibleAudioDevice(audioDeviceAttributes);
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    synchronized boolean isAvailableForDevice(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        if (audioDeviceAttributes.getRole() != 2) {
            return false;
        }
        return isAvailableForAdiDeviceState(findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes));
    }

    private boolean isAvailableForAdiDeviceState(com.android.server.audio.AdiDeviceState adiDeviceState) {
        if (adiDeviceState == null) {
            return false;
        }
        if (android.media.AudioSystem.isBluetoothDevice(adiDeviceState.getInternalDeviceType()) && adiDeviceState.getAudioDeviceCategory() != 0 && adiDeviceState.getAudioDeviceCategory() != 3) {
            return false;
        }
        return true;
    }

    private synchronized boolean canBeSpatializedOnDevice(@android.annotation.NonNull android.media.AudioAttributes audioAttributes, @android.annotation.NonNull android.media.AudioFormat audioFormat, @android.annotation.NonNull java.util.ArrayList<android.media.AudioDeviceAttributes> arrayList) {
        if (arrayList.isEmpty()) {
            return false;
        }
        if (!isDeviceCompatibleWithSpatializationModes(arrayList.get(0))) {
            return false;
        }
        return android.media.AudioSystem.canBeSpatialized(audioAttributes, audioFormat, (android.media.AudioDeviceAttributes[]) arrayList.toArray(new android.media.AudioDeviceAttributes[arrayList.size()]));
    }

    private boolean isDeviceCompatibleWithSpatializationModes(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        byte b = (byte) SPAT_MODE_FOR_DEVICE_TYPE.get(audioDeviceAttributes.getType(), -1);
        if ((b == 0 && this.mBinauralSupported) || (b == 1 && this.mTransauralSupported)) {
            return true;
        }
        return false;
    }

    boolean isSADevice(com.android.server.audio.AdiDeviceState adiDeviceState) {
        return adiDeviceState.getDeviceType() == getCanonicalDeviceType(adiDeviceState.getDeviceType(), adiDeviceState.getInternalDeviceType()) && isDeviceCompatibleWithSpatializationModes(adiDeviceState.getAudioDeviceAttributes());
    }

    synchronized void setFeatureEnabled(boolean z) {
        loglogi("setFeatureEnabled(" + z + ") was featureEnabled:" + this.mFeatureEnabled);
        if (this.mFeatureEnabled == z) {
            return;
        }
        this.mFeatureEnabled = z;
        if (this.mFeatureEnabled) {
            if (this.mState == 1) {
                android.util.Log.e(TAG, "Can't enabled Spatial Audio, unsupported");
            } else {
                if (this.mState == 0) {
                    init(true);
                }
                setSpatializerEnabledInt(true);
            }
        } else {
            setSpatializerEnabledInt(false);
        }
    }

    synchronized void setSpatializerEnabledInt(boolean z) {
        try {
            switch (this.mState) {
                case 0:
                    if (z) {
                        throw new java.lang.IllegalStateException("Can't enable when uninitialized");
                    }
                    break;
                case 1:
                    if (z) {
                        android.util.Log.e(TAG, "Can't enable when unsupported");
                        break;
                    }
                    break;
                case 3:
                case 6:
                    if (z) {
                        createSpat();
                        onRoutingUpdated();
                        break;
                    }
                    break;
                case 4:
                case 5:
                    if (!z) {
                        releaseSpat();
                        setDispatchFeatureEnabledState(false, "setSpatializerEnabledInt");
                        break;
                    }
                    break;
            }
        } finally {
        }
    }

    synchronized int getCapableImmersiveAudioLevel() {
        return this.mCapableSpatLevel;
    }

    synchronized void registerStateCallback(@android.annotation.NonNull android.media.ISpatializerCallback iSpatializerCallback) {
        this.mStateCallbacks.register(iSpatializerCallback);
    }

    synchronized void unregisterStateCallback(@android.annotation.NonNull android.media.ISpatializerCallback iSpatializerCallback) {
        this.mStateCallbacks.unregister(iSpatializerCallback);
    }

    private synchronized void setDispatchFeatureEnabledState(boolean z, java.lang.String str) {
        try {
            if (z) {
                switch (this.mState) {
                    case 3:
                        this.mState = 4;
                        break;
                    case 4:
                    case 5:
                        loglogi("setDispatchFeatureEnabledState(" + z + ") no dispatch: mState:" + spatStateString(this.mState) + " src:" + str);
                        return;
                    case 6:
                        this.mState = 5;
                        break;
                    default:
                        throw new java.lang.IllegalStateException("Invalid mState:" + this.mState + " for enabled true");
                }
            } else {
                switch (this.mState) {
                    case 3:
                    case 6:
                        loglogi("setDispatchFeatureEnabledState(" + z + ") no dispatch: mState:" + spatStateString(this.mState) + " src:" + str);
                        return;
                    case 4:
                        this.mState = 3;
                        break;
                    case 5:
                        this.mState = 6;
                        break;
                    default:
                        throw new java.lang.IllegalStateException("Invalid mState:" + this.mState + " for enabled false");
                }
            }
            loglogi("setDispatchFeatureEnabledState(" + z + ") mState:" + spatStateString(this.mState));
            int beginBroadcast = this.mStateCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    this.mStateCallbacks.getBroadcastItem(i).dispatchSpatializerEnabledChanged(z);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "Error in dispatchSpatializerEnabledChanged", e);
                }
            }
            this.mStateCallbacks.finishBroadcast();
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private synchronized void setDispatchAvailableState(boolean z) {
        switch (this.mState) {
            case 0:
            case 1:
                throw new java.lang.IllegalStateException("Should not update available state in state:" + this.mState);
            case 3:
                if (z) {
                    this.mState = 6;
                    break;
                } else {
                    loglogi("setDispatchAvailableState(" + z + ") no dispatch: mState:" + spatStateString(this.mState));
                    return;
                }
            case 4:
                if (z) {
                    this.mState = 5;
                    break;
                } else {
                    loglogi("setDispatchAvailableState(" + z + ") no dispatch: mState:" + spatStateString(this.mState));
                    return;
                }
            case 5:
                if (z) {
                    loglogi("setDispatchAvailableState(" + z + ") no dispatch: mState:" + spatStateString(this.mState));
                    return;
                }
                this.mState = 4;
                break;
            case 6:
                if (z) {
                    loglogi("setDispatchAvailableState(" + z + ") no dispatch: mState:" + spatStateString(this.mState));
                    return;
                }
                this.mState = 3;
                break;
        }
        loglogi("setDispatchAvailableState(" + z + ") mState:" + spatStateString(this.mState));
        int beginBroadcast = this.mStateCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mStateCallbacks.getBroadcastItem(i).dispatchSpatializerAvailableChanged(z);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error in dispatchSpatializerEnabledChanged", e);
            }
        }
        this.mStateCallbacks.finishBroadcast();
    }

    private void createSpat() {
        if (this.mSpat == null) {
            this.mSpatCallback = new com.android.server.audio.SpatializerHelper.SpatializerCallback();
            this.mSpat = android.media.AudioSystem.getSpatializer(this.mSpatCallback);
            if (this.mSpat == null) {
                android.util.Log.e(TAG, "createSpat(): No Spatializer found");
                postReset();
                return;
            }
            try {
                if (this.mIsHeadTrackingSupported) {
                    this.mActualHeadTrackingMode = headTrackingModeTypeToSpatializerInt(this.mSpat.getActualHeadTrackingMode());
                    this.mSpat.registerHeadTrackingCallback(this.mSpatHeadTrackingCallback);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Can't configure head tracking", e);
                this.mState = 1;
                this.mCapableSpatLevel = 0;
                this.mActualHeadTrackingMode = -2;
            }
        }
    }

    private void releaseSpat() {
        if (this.mSpat != null) {
            this.mSpatCallback = null;
            try {
                if (this.mIsHeadTrackingSupported) {
                    this.mSpat.registerHeadTrackingCallback((android.media.ISpatializerHeadTrackingCallback) null);
                }
                this.mHeadTrackerAvailable = false;
                this.mSpat.release();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Can't set release spatializer cleanly", e);
            }
            this.mSpat = null;
        }
    }

    synchronized boolean canBeSpatialized(@android.annotation.NonNull android.media.AudioAttributes audioAttributes, @android.annotation.NonNull android.media.AudioFormat audioFormat) {
        switch (this.mState) {
            case 0:
            case 1:
            case 3:
            case 4:
                logd("canBeSpatialized false due to state:" + this.mState);
                return false;
            case 2:
            default:
                switch (audioAttributes.getUsage()) {
                    case 1:
                    case 14:
                        java.util.ArrayList<android.media.AudioDeviceAttributes> routingDevices = getRoutingDevices(audioAttributes);
                        if (routingDevices.isEmpty()) {
                            logloge("canBeSpatialized got no device for " + audioAttributes);
                            return false;
                        }
                        boolean canBeSpatializedOnDevice = canBeSpatializedOnDevice(audioAttributes, audioFormat, routingDevices);
                        logd("canBeSpatialized usage:" + audioAttributes.getUsage() + " format:" + audioFormat.toLogFriendlyString() + " returning " + canBeSpatializedOnDevice);
                        return canBeSpatializedOnDevice;
                    default:
                        logd("canBeSpatialized false due to usage:" + audioAttributes.getUsage());
                        return false;
                }
        }
    }

    synchronized void registerHeadTrackingModeCallback(@android.annotation.NonNull android.media.ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) {
        this.mHeadTrackingModeCallbacks.register(iSpatializerHeadTrackingModeCallback);
    }

    synchronized void unregisterHeadTrackingModeCallback(@android.annotation.NonNull android.media.ISpatializerHeadTrackingModeCallback iSpatializerHeadTrackingModeCallback) {
        this.mHeadTrackingModeCallbacks.unregister(iSpatializerHeadTrackingModeCallback);
    }

    synchronized void registerHeadTrackerAvailableCallback(@android.annotation.NonNull android.media.ISpatializerHeadTrackerAvailableCallback iSpatializerHeadTrackerAvailableCallback, boolean z) {
        try {
            if (z) {
                this.mHeadTrackerCallbacks.register(iSpatializerHeadTrackerAvailableCallback);
            } else {
                this.mHeadTrackerCallbacks.unregister(iSpatializerHeadTrackerAvailableCallback);
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    synchronized int[] getSupportedHeadTrackingModes() {
        return this.mSupportedHeadTrackingModes;
    }

    synchronized int getActualHeadTrackingMode() {
        return this.mActualHeadTrackingMode;
    }

    synchronized int getDesiredHeadTrackingMode() {
        return this.mDesiredHeadTrackingMode;
    }

    synchronized void setGlobalTransform(@android.annotation.NonNull float[] fArr) {
        if (fArr.length != 6) {
            throw new java.lang.IllegalArgumentException("invalid array size" + fArr.length);
        }
        if (checkSpatializerForHeadTracking("setGlobalTransform")) {
            try {
                this.mSpat.setGlobalTransform(fArr);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling setGlobalTransform", e);
            }
        }
    }

    synchronized void recenterHeadTracker() {
        if (checkSpatializerForHeadTracking("recenterHeadTracker")) {
            try {
                this.mSpat.recenterHeadTracker();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling recenterHeadTracker", e);
            }
        }
    }

    synchronized void setDisplayOrientation(float f) {
        if (checkSpatializer("setDisplayOrientation")) {
            try {
                this.mSpat.setDisplayOrientation(f);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling setDisplayOrientation", e);
            }
        }
    }

    synchronized void setFoldState(boolean z) {
        if (checkSpatializer("setFoldState")) {
            try {
                this.mSpat.setFoldState(z);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling setFoldState", e);
            }
        }
    }

    synchronized void setDesiredHeadTrackingMode(int i) {
        if (checkSpatializerForHeadTracking("setDesiredHeadTrackingMode")) {
            if (i != -1) {
                this.mDesiredHeadTrackingModeWhenEnabled = i;
            }
            try {
                if (this.mDesiredHeadTrackingMode != i) {
                    this.mDesiredHeadTrackingMode = i;
                    dispatchDesiredHeadTrackingMode(i);
                }
                android.util.Log.i(TAG, "setDesiredHeadTrackingMode(" + android.media.Spatializer.headtrackingModeToString(i) + ")");
                this.mSpat.setDesiredHeadTrackingMode(spatializerIntToHeadTrackingModeType(i));
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling setDesiredHeadTrackingMode", e);
            }
        }
    }

    synchronized void setHeadTrackerEnabled(boolean z, @android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        try {
            if (!this.mIsHeadTrackingSupported) {
                android.util.Log.v(TAG, "no headtracking support, ignoring setHeadTrackerEnabled to " + z + " for " + audioDeviceAttributes);
            }
            com.android.server.audio.AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes = findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes);
            if (findSACompatibleDeviceStateForAudioDeviceAttributes == null) {
                return;
            }
            if (!findSACompatibleDeviceStateForAudioDeviceAttributes.hasHeadTracker()) {
                android.util.Log.e(TAG, "Called setHeadTrackerEnabled enabled:" + z + " device:" + audioDeviceAttributes + " on a device without headtracker");
                return;
            }
            android.util.Log.i(TAG, "setHeadTrackerEnabled enabled:" + z + " device:" + audioDeviceAttributes);
            findSACompatibleDeviceStateForAudioDeviceAttributes.setHeadTrackerEnabled(z);
            this.mDeviceBroker.postPersistAudioDeviceSettings();
            logDeviceState(findSACompatibleDeviceStateForAudioDeviceAttributes, "setHeadTrackerEnabled");
            if (sRoutingDevices.isEmpty()) {
                logloge("setHeadTrackerEnabled: no device, bailing");
                return;
            }
            android.media.AudioDeviceAttributes audioDeviceAttributes2 = sRoutingDevices.get(0);
            if (audioDeviceAttributes2.getType() == audioDeviceAttributes.getType() && audioDeviceAttributes2.getAddress().equals(audioDeviceAttributes.getAddress())) {
                setDesiredHeadTrackingMode(z ? this.mDesiredHeadTrackingModeWhenEnabled : -1);
                if (z && !this.mHeadTrackerAvailable) {
                    postInitSensors();
                }
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    synchronized boolean hasHeadTracker(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        boolean z = false;
        if (!this.mIsHeadTrackingSupported) {
            android.util.Log.v(TAG, "no headtracking support, hasHeadTracker always false for " + audioDeviceAttributes);
            return false;
        }
        com.android.server.audio.AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes = findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes);
        if (findSACompatibleDeviceStateForAudioDeviceAttributes != null && findSACompatibleDeviceStateForAudioDeviceAttributes.hasHeadTracker()) {
            z = true;
        }
        return z;
    }

    synchronized boolean setHasHeadTracker(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        if (!this.mIsHeadTrackingSupported) {
            android.util.Log.v(TAG, "no headtracking support, setHasHeadTracker always false for " + audioDeviceAttributes);
            return false;
        }
        com.android.server.audio.AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes = findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes);
        if (findSACompatibleDeviceStateForAudioDeviceAttributes != null) {
            if (!findSACompatibleDeviceStateForAudioDeviceAttributes.hasHeadTracker()) {
                findSACompatibleDeviceStateForAudioDeviceAttributes.setHasHeadTracker(true);
                this.mDeviceBroker.postPersistAudioDeviceSettings();
                logDeviceState(findSACompatibleDeviceStateForAudioDeviceAttributes, "setHasHeadTracker");
            }
            return findSACompatibleDeviceStateForAudioDeviceAttributes.isHeadTrackerEnabled();
        }
        android.util.Log.e(TAG, "setHasHeadTracker: device not found for:" + audioDeviceAttributes);
        return false;
    }

    synchronized boolean isHeadTrackerEnabled(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        boolean z = false;
        if (!this.mIsHeadTrackingSupported) {
            android.util.Log.v(TAG, "no headtracking support, isHeadTrackerEnabled always false for " + audioDeviceAttributes);
            return false;
        }
        com.android.server.audio.AdiDeviceState findSACompatibleDeviceStateForAudioDeviceAttributes = findSACompatibleDeviceStateForAudioDeviceAttributes(audioDeviceAttributes);
        if (findSACompatibleDeviceStateForAudioDeviceAttributes != null && findSACompatibleDeviceStateForAudioDeviceAttributes.hasHeadTracker() && findSACompatibleDeviceStateForAudioDeviceAttributes.isHeadTrackerEnabled()) {
            z = true;
        }
        return z;
    }

    synchronized boolean isHeadTrackerAvailable() {
        return this.mHeadTrackerAvailable;
    }

    private boolean checkSpatializer(java.lang.String str) {
        switch (this.mState) {
            case 3:
            case 4:
            case 5:
            case 6:
                if (this.mSpat == null) {
                    android.util.Log.e(TAG, "checkSpatializer(): called from " + str + "(), native spatializer should not be null in state: " + this.mState);
                    postReset();
                    break;
                }
                break;
        }
        return false;
    }

    private boolean checkSpatializerForHeadTracking(java.lang.String str) {
        return checkSpatializer(str) && this.mIsHeadTrackingSupported;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchActualHeadTrackingMode(int i) {
        int beginBroadcast = this.mHeadTrackingModeCallbacks.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mHeadTrackingModeCallbacks.getBroadcastItem(i2).dispatchSpatializerActualHeadTrackingModeChanged(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error in dispatchSpatializerActualHeadTrackingModeChanged(" + i + ")", e);
            }
        }
        this.mHeadTrackingModeCallbacks.finishBroadcast();
    }

    private void dispatchDesiredHeadTrackingMode(int i) {
        int beginBroadcast = this.mHeadTrackingModeCallbacks.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mHeadTrackingModeCallbacks.getBroadcastItem(i2).dispatchSpatializerDesiredHeadTrackingModeChanged(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error in dispatchSpatializerDesiredHeadTrackingModeChanged(" + i + ")", e);
            }
        }
        this.mHeadTrackingModeCallbacks.finishBroadcast();
    }

    private void dispatchHeadTrackerAvailable(boolean z) {
        int beginBroadcast = this.mHeadTrackerCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mHeadTrackerCallbacks.getBroadcastItem(i).dispatchSpatializerHeadTrackerAvailable(z);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error in dispatchSpatializerHeadTrackerAvailable(" + z + ")", e);
            }
        }
        this.mHeadTrackerCallbacks.finishBroadcast();
    }

    synchronized void registerHeadToSoundstagePoseCallback(@android.annotation.NonNull android.media.ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) {
        this.mHeadPoseCallbacks.register(iSpatializerHeadToSoundStagePoseCallback);
    }

    synchronized void unregisterHeadToSoundstagePoseCallback(@android.annotation.NonNull android.media.ISpatializerHeadToSoundStagePoseCallback iSpatializerHeadToSoundStagePoseCallback) {
        this.mHeadPoseCallbacks.unregister(iSpatializerHeadToSoundStagePoseCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchPoseUpdate(float[] fArr) {
        int beginBroadcast = this.mHeadPoseCallbacks.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mHeadPoseCallbacks.getBroadcastItem(i).dispatchPoseChanged(fArr);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error in dispatchPoseChanged", e);
            }
        }
        this.mHeadPoseCallbacks.finishBroadcast();
    }

    synchronized void setEffectParameter(int i, @android.annotation.NonNull byte[] bArr) {
        switch (this.mState) {
            case 0:
            case 1:
                throw new java.lang.IllegalStateException("Can't set parameter key:" + i + " without a spatializer");
            case 3:
            case 4:
            case 5:
            case 6:
                if (this.mSpat == null) {
                    android.util.Log.e(TAG, "setParameter(" + i + "): null spatializer in state: " + this.mState);
                    return;
                }
                break;
        }
        try {
            this.mSpat.setParameter(i, bArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error in setParameter for key:" + i, e);
        }
    }

    synchronized void getEffectParameter(int i, @android.annotation.NonNull byte[] bArr) {
        switch (this.mState) {
            case 0:
            case 1:
                throw new java.lang.IllegalStateException("Can't get parameter key:" + i + " without a spatializer");
            case 3:
            case 4:
            case 5:
            case 6:
                if (this.mSpat == null) {
                    android.util.Log.e(TAG, "getParameter(" + i + "): null spatializer in state: " + this.mState);
                    return;
                }
                break;
        }
        try {
            this.mSpat.getParameter(i, bArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error in getParameter for key:" + i, e);
        }
    }

    synchronized int getOutput() {
        switch (this.mState) {
            case 0:
            case 1:
                throw new java.lang.IllegalStateException("Can't get output without a spatializer");
            case 3:
            case 4:
            case 5:
            case 6:
                if (this.mSpat == null) {
                    throw new java.lang.IllegalStateException("null Spatializer for getOutput");
                }
                break;
        }
        try {
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error in getOutput", e);
            return 0;
        }
        return this.mSpat.getOutput();
    }

    synchronized void registerSpatializerOutputCallback(@android.annotation.NonNull android.media.ISpatializerOutputCallback iSpatializerOutputCallback) {
        this.mOutputCallbacks.register(iSpatializerOutputCallback);
    }

    synchronized void unregisterSpatializerOutputCallback(@android.annotation.NonNull android.media.ISpatializerOutputCallback iSpatializerOutputCallback) {
        this.mOutputCallbacks.unregister(iSpatializerOutputCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOutputUpdate(int i) {
        int beginBroadcast = this.mOutputCallbacks.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mOutputCallbacks.getBroadcastItem(i2).dispatchSpatializerOutputChanged(i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error in dispatchOutputUpdate", e);
            }
        }
        this.mOutputCallbacks.finishBroadcast();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postInitSensors() {
        this.mAudioService.postInitSpatializerHeadTrackingSensors();
    }

    synchronized void onInitSensors() {
        int i;
        int i2;
        try {
            boolean z = this.mFeatureEnabled && this.mSpatLevel != 0;
            java.lang.String str = z ? "initializing" : "releasing";
            if (this.mSpat == null) {
                logloge("not " + str + " sensors, null spatializer");
                return;
            }
            if (!this.mIsHeadTrackingSupported) {
                logloge("not " + str + " sensors, spatializer doesn't support headtracking");
                return;
            }
            if (z) {
                if (this.mSensorManager == null) {
                    try {
                        this.mSensorManager = (android.hardware.SensorManager) this.mAudioService.mContext.getSystemService("sensor");
                        this.mDynSensorCallback = new com.android.server.audio.SpatializerHelper.HelperDynamicSensorCallback();
                        this.mSensorManager.registerDynamicSensorCallback(this.mDynSensorCallback);
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(TAG, "Error with SensorManager, can't initialize sensors", e);
                        this.mSensorManager = null;
                        this.mDynSensorCallback = null;
                        return;
                    }
                }
                i = getHeadSensorHandleUpdateTracker();
                loglogi("head tracker sensor handle initialized to " + i);
                i2 = getScreenSensorHandle();
                android.util.Log.i(TAG, "found screen sensor handle initialized to " + i2);
            } else {
                if (this.mSensorManager != null && this.mDynSensorCallback != null) {
                    this.mSensorManager.unregisterDynamicSensorCallback(this.mDynSensorCallback);
                    this.mSensorManager = null;
                    this.mDynSensorCallback = null;
                }
                i = -1;
                i2 = -1;
            }
            try {
                android.util.Log.i(TAG, "setScreenSensor:" + i2);
                this.mSpat.setScreenSensor(i2);
            } catch (java.lang.Exception e2) {
                android.util.Log.e(TAG, "Error calling setScreenSensor:" + i2, e2);
            }
            try {
                android.util.Log.i(TAG, "setHeadSensor:" + i);
                this.mSpat.setHeadSensor(i);
                if (this.mHeadTrackerAvailable != (i != -1)) {
                    this.mHeadTrackerAvailable = i != -1;
                    dispatchHeadTrackerAvailable(this.mHeadTrackerAvailable);
                }
            } catch (java.lang.Exception e3) {
                android.util.Log.e(TAG, "Error calling setHeadSensor:" + i, e3);
            }
            setDesiredHeadTrackingMode(this.mDesiredHeadTrackingMode);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int headTrackingModeTypeToSpatializerInt(byte b) {
        switch (b) {
            case 0:
                return 0;
            case 1:
                return -1;
            case 2:
                return 1;
            case 3:
                return 2;
            default:
                throw new java.lang.IllegalArgumentException("Unexpected head tracking mode:" + ((int) b));
        }
    }

    private static byte spatializerIntToHeadTrackingModeType(int i) {
        switch (i) {
            case -1:
                return (byte) 1;
            case 0:
                return (byte) 0;
            case 1:
                return (byte) 2;
            case 2:
                return (byte) 3;
            default:
                throw new java.lang.IllegalArgumentException("Unexpected head tracking mode:" + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int spatializationLevelToSpatializerInt(byte b) {
        switch (b) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            default:
                throw new java.lang.IllegalArgumentException("Unexpected spatializer level:" + ((int) b));
        }
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.println("SpatializerHelper:");
        printWriter.println("\tmState:" + this.mState);
        printWriter.println("\tmSpatLevel:" + this.mSpatLevel);
        printWriter.println("\tmCapableSpatLevel:" + this.mCapableSpatLevel);
        printWriter.println("\tmIsHeadTrackingSupported:" + this.mIsHeadTrackingSupported);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i : this.mSupportedHeadTrackingModes) {
            sb.append(android.media.Spatializer.headtrackingModeToString(i));
            sb.append(" ");
        }
        printWriter.println("\tsupported head tracking modes:" + ((java.lang.Object) sb));
        printWriter.println("\tmDesiredHeadTrackingMode:" + android.media.Spatializer.headtrackingModeToString(this.mDesiredHeadTrackingMode));
        printWriter.println("\tmActualHeadTrackingMode:" + android.media.Spatializer.headtrackingModeToString(this.mActualHeadTrackingMode));
        printWriter.println("\theadtracker available:" + this.mHeadTrackerAvailable);
        printWriter.println("\tsupports binaural:" + this.mBinauralSupported + " / transaural:" + this.mTransauralSupported);
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append("\tmSpatOutput:");
        sb2.append(this.mSpatOutput);
        printWriter.println(sb2.toString());
        printWriter.println("\thas FEATURE_AUDIO_SPATIAL_HEADTRACKING_LOW_LATENCY:" + this.mAudioService.mContext.getPackageManager().hasSystemFeature("android.hardware.audio.spatial.headtracking.low_latency"));
    }

    private static java.lang.String spatStateString(int i) {
        switch (i) {
            case 0:
                return "STATE_UNINITIALIZED";
            case 1:
                return "STATE_NOT_SUPPORTED";
            case 2:
            default:
                return "invalid state";
            case 3:
                return "STATE_DISABLED_UNAVAILABLE";
            case 4:
                return "STATE_ENABLED_UNAVAILABLE";
            case 5:
                return "STATE_ENABLED_AVAILABLE";
            case 6:
                return "STATE_DISABLED_AVAILABLE";
        }
    }

    private int getHeadSensorHandleUpdateTracker() {
        if (sRoutingDevices.isEmpty()) {
            logloge("getHeadSensorHandleUpdateTracker: no device, no head tracker");
            return -1;
        }
        android.media.AudioDeviceAttributes audioDeviceAttributes = sRoutingDevices.get(0);
        java.util.List<java.lang.String> deviceIdentityAddresses = this.mAudioService.getDeviceIdentityAddresses(audioDeviceAttributes);
        java.util.List<android.hardware.Sensor> dynamicSensorList = this.mSensorManager.getDynamicSensorList(37);
        java.util.Iterator<java.lang.String> it = deviceIdentityAddresses.iterator();
        android.hardware.Sensor sensor = null;
        while (it.hasNext()) {
            java.util.UUID uuidFromAudioDeviceAttributes = com.android.server.audio.UuidUtils.uuidFromAudioDeviceAttributes(new android.media.AudioDeviceAttributes(audioDeviceAttributes.getInternalType(), it.next()));
            com.android.media.audio.Flags.dsaOverBtLeAudio();
            java.util.Iterator<android.hardware.Sensor> it2 = dynamicSensorList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                android.hardware.Sensor next = it2.next();
                java.util.UUID uuid = next.getUuid();
                if (uuid.equals(uuidFromAudioDeviceAttributes)) {
                    if (setHasHeadTracker(audioDeviceAttributes)) {
                        sensor = next;
                    } else {
                        sensor = null;
                    }
                } else if (uuid.equals(com.android.server.audio.UuidUtils.STANDALONE_UUID)) {
                    sensor = next;
                }
            }
            if (sensor != null) {
                break;
            }
        }
        if (sensor != null) {
            return sensor.getHandle();
        }
        return -1;
    }

    private static class HeadtrackerInfo {
        private final int mVersion;

        HeadtrackerInfo(android.hardware.Sensor sensor) {
            this.mVersion = sensor.getVersion();
        }

        int getMajorVersion() {
            return (this.mVersion & android.hardware.audio.common.V2_0.AudioFormat.MAIN_MASK) >> 24;
        }

        int getMinorVersion() {
            return (this.mVersion & 16711680) >> 16;
        }

        boolean hasAclTransport() {
            return getMajorVersion() == 2 && (this.mVersion & 1) != 0;
        }

        boolean hasIsoTransport() {
            return getMajorVersion() == 2 && (this.mVersion & 2) != 0;
        }
    }

    private int getScreenSensorHandle() {
        android.hardware.Sensor defaultSensor = this.mSensorManager.getDefaultSensor(11);
        if (defaultSensor == null) {
            return -1;
        }
        return defaultSensor.getHandle();
    }

    @android.annotation.NonNull
    private java.util.ArrayList<android.media.AudioDeviceAttributes> getRoutingDevices(android.media.AudioAttributes audioAttributes) {
        java.util.ArrayList<android.media.AudioDeviceAttributes> devicesForAttributes = this.mASA.getDevicesForAttributes(audioAttributes, false);
        java.util.Iterator<android.media.AudioDeviceAttributes> it = devicesForAttributes.iterator();
        while (it.hasNext()) {
            if (it.next() == null) {
                return new java.util.ArrayList<>(0);
            }
        }
        return devicesForAttributes;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void loglogi(java.lang.String str) {
        com.android.server.audio.AudioService.sSpatialLogger.enqueueAndLog(str, 0, TAG);
    }

    private static java.lang.String logloge(java.lang.String str) {
        com.android.server.audio.AudioService.sSpatialLogger.enqueueAndLog(str, 1, TAG);
        return str;
    }

    synchronized void forceStateForTest(int i) {
        this.mState = i;
    }

    synchronized void initForTest(boolean z, boolean z2) {
        this.mBinauralSupported = z;
        this.mTransauralSupported = z2;
    }
}
