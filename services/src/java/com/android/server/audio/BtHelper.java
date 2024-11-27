package com.android.server.audio;

/* loaded from: classes.dex */
public class BtHelper {
    private static final int BT_HEARING_AID_GAIN_MIN = -128;
    private static final int BT_LE_AUDIO_MAX_VOL = 255;
    private static final int BT_LE_AUDIO_MIN_VOL = 0;
    static final int EVENT_DEVICE_CONFIG_CHANGE = 0;
    private static final int SCO_MODE_MAX = 2;
    static final int SCO_MODE_UNDEFINED = -1;
    static final int SCO_MODE_VIRTUAL_CALL = 0;
    private static final int SCO_MODE_VR = 2;
    private static final int SCO_STATE_ACTIVATE_REQ = 1;
    private static final int SCO_STATE_ACTIVE_EXTERNAL = 2;
    private static final int SCO_STATE_ACTIVE_INTERNAL = 3;
    private static final int SCO_STATE_DEACTIVATE_REQ = 4;
    private static final int SCO_STATE_DEACTIVATING = 5;
    private static final int SCO_STATE_INACTIVE = 0;
    private static final java.lang.String TAG = "AS.BtHelper";

    @android.annotation.Nullable
    private android.bluetooth.BluetoothA2dp mA2dp;

    @android.annotation.Nullable
    private android.bluetooth.BluetoothHeadset mBluetoothHeadset;

    @android.annotation.Nullable
    private android.bluetooth.BluetoothDevice mBluetoothHeadsetDevice;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @android.annotation.NonNull
    private final com.android.server.audio.AudioDeviceBroker mDeviceBroker;

    @android.annotation.Nullable
    private android.bluetooth.BluetoothHearingAid mHearingAid;

    @android.annotation.Nullable
    private android.bluetooth.BluetoothLeAudio mLeAudio;
    private int mScoAudioMode;
    private int mScoAudioState;
    private int mScoConnectionState;
    private final java.util.Map<android.bluetooth.BluetoothDevice, android.media.AudioDeviceAttributes> mResolvedScoAudioDevices = new java.util.HashMap();
    private boolean mAvrcpAbsVolSupported = false;
    com.android.server.audio.BtHelper.MyLeAudioCallback mLeAudioCallback = null;
    private android.bluetooth.BluetoothProfile.ServiceListener mBluetoothProfileServiceListener = new android.bluetooth.BluetoothProfile.ServiceListener() { // from class: com.android.server.audio.BtHelper.1
        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, android.bluetooth.BluetoothProfile bluetoothProfile) {
            switch (i) {
                case 1:
                case 2:
                case 11:
                case 21:
                case 22:
                case 26:
                    com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("BT profile service: connecting " + android.bluetooth.BluetoothProfile.getProfileName(i) + " profile").printLog(com.android.server.audio.BtHelper.TAG));
                    com.android.server.audio.BtHelper.this.mDeviceBroker.postBtProfileConnected(i, bluetoothProfile);
                    break;
            }
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            switch (i) {
                case 1:
                case 2:
                case 11:
                case 21:
                case 22:
                case 26:
                    com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("BT profile service: disconnecting " + android.bluetooth.BluetoothProfile.getProfileName(i) + " profile").printLog(com.android.server.audio.BtHelper.TAG));
                    com.android.server.audio.BtHelper.this.mDeviceBroker.postBtProfileDisconnected(i);
                    break;
            }
        }
    };

    BtHelper(@android.annotation.NonNull com.android.server.audio.AudioDeviceBroker audioDeviceBroker, android.content.Context context) {
        this.mDeviceBroker = audioDeviceBroker;
        this.mContext = context;
    }

    public static java.lang.String scoAudioModeToString(int i) {
        switch (i) {
            case -1:
                return "SCO_MODE_UNDEFINED";
            case 0:
                return "SCO_MODE_VIRTUAL_CALL";
            case 1:
            default:
                return "SCO_MODE_(" + i + ")";
            case 2:
                return "SCO_MODE_VR";
        }
    }

    public static java.lang.String scoAudioStateToString(int i) {
        switch (i) {
            case 0:
                return "SCO_STATE_INACTIVE";
            case 1:
                return "SCO_STATE_ACTIVATE_REQ";
            case 2:
                return "SCO_STATE_ACTIVE_EXTERNAL";
            case 3:
                return "SCO_STATE_ACTIVE_INTERNAL";
            case 4:
            default:
                return "SCO_STATE_(" + i + ")";
            case 5:
                return "SCO_STATE_DEACTIVATING";
        }
    }

    static java.lang.String deviceEventToString(int i) {
        switch (i) {
            case 0:
                return "DEVICE_CONFIG_CHANGE";
            default:
                return new java.lang.String("invalid event:" + i);
        }
    }

    @android.annotation.NonNull
    static java.lang.String getName(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice) {
        java.lang.String name = bluetoothDevice.getName();
        if (name == null) {
            return "";
        }
        return name;
    }

    @com.android.internal.annotations.GuardedBy({"AudioDeviceBroker.this.mDeviceStateLock"})
    synchronized void onSystemReady() {
        this.mScoConnectionState = -1;
        resetBluetoothSco();
        getBluetoothHeadset();
        android.content.Intent intent = new android.content.Intent("android.media.SCO_AUDIO_STATE_CHANGED");
        intent.putExtra("android.media.extra.SCO_AUDIO_STATE", 0);
        sendStickyBroadcastToAll(intent);
        android.bluetooth.BluetoothAdapter defaultAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            defaultAdapter.getProfileProxy(this.mDeviceBroker.getContext(), this.mBluetoothProfileServiceListener, 2);
            defaultAdapter.getProfileProxy(this.mDeviceBroker.getContext(), this.mBluetoothProfileServiceListener, 11);
            defaultAdapter.getProfileProxy(this.mDeviceBroker.getContext(), this.mBluetoothProfileServiceListener, 21);
            defaultAdapter.getProfileProxy(this.mDeviceBroker.getContext(), this.mBluetoothProfileServiceListener, 22);
            defaultAdapter.getProfileProxy(this.mDeviceBroker.getContext(), this.mBluetoothProfileServiceListener, 26);
        }
    }

    synchronized void onAudioServerDiedRestoreA2dp() {
        this.mDeviceBroker.setForceUse_Async(1, this.mDeviceBroker.getBluetoothA2dpEnabled() ? 0 : 10, "onAudioServerDied()");
    }

    synchronized boolean isAvrcpAbsoluteVolumeSupported() {
        boolean z;
        if (this.mA2dp != null) {
            z = this.mAvrcpAbsVolSupported;
        }
        return z;
    }

    synchronized void setAvrcpAbsoluteVolumeSupported(boolean z) {
        this.mAvrcpAbsVolSupported = z;
        android.util.Log.i(TAG, "setAvrcpAbsoluteVolumeSupported supported=" + z);
    }

    synchronized void setAvrcpAbsoluteVolumeIndex(int i) {
        if (this.mA2dp == null) {
            return;
        }
        if (!this.mAvrcpAbsVolSupported) {
            com.android.server.audio.AudioService.sVolumeLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("setAvrcpAbsoluteVolumeIndex: abs vol not supported ").printLog(TAG));
            return;
        }
        com.android.server.audio.AudioService.sVolumeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.VolumeEvent(4, i));
        try {
            this.mA2dp.setAvrcpAbsoluteVolume(i);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Exception while changing abs volume", e);
        }
    }

    synchronized int getCodec(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice, int i) {
        android.bluetooth.BluetoothLeAudioCodecStatus bluetoothLeAudioCodecStatus = null;
        android.bluetooth.BluetoothCodecStatus bluetoothCodecStatus = null;
        switch (i) {
            case 2:
                if (this.mA2dp == null) {
                    return 0;
                }
                try {
                    bluetoothCodecStatus = this.mA2dp.getCodecStatus(bluetoothDevice);
                } catch (java.lang.Exception e) {
                    android.util.Log.e(TAG, "Exception while getting status of " + bluetoothDevice, e);
                }
                if (bluetoothCodecStatus == null) {
                    return 0;
                }
                android.bluetooth.BluetoothCodecConfig codecConfig = bluetoothCodecStatus.getCodecConfig();
                if (codecConfig == null) {
                    return 0;
                }
                return android.media.AudioSystem.bluetoothA2dpCodecToAudioFormat(codecConfig.getCodecType());
            case 22:
                if (this.mLeAudio == null) {
                    return 0;
                }
                try {
                    bluetoothLeAudioCodecStatus = this.mLeAudio.getCodecStatus(this.mLeAudio.getGroupId(bluetoothDevice));
                } catch (java.lang.Exception e2) {
                    android.util.Log.e(TAG, "Exception while getting status of " + bluetoothDevice, e2);
                }
                if (bluetoothLeAudioCodecStatus == null) {
                    return 0;
                }
                android.bluetooth.BluetoothLeAudioCodecConfig outputCodecConfig = bluetoothLeAudioCodecStatus.getOutputCodecConfig();
                if (outputCodecConfig == null) {
                    return 0;
                }
                return android.media.AudioSystem.bluetoothLeCodecToAudioFormat(outputCodecConfig.getCodecType());
            default:
                return 0;
        }
    }

    synchronized int getCodecWithFallback(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice, int i, boolean z, @android.annotation.NonNull java.lang.String str) {
        if (i != 2 && (!z || (i != 22 && i != 26))) {
            return 0;
        }
        try {
            int codec = getCodec(bluetoothDevice, i);
            if (codec != 0) {
                return codec;
            }
            com.android.server.utils.EventLogger eventLogger = com.android.server.audio.AudioService.sDeviceLogger;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("getCodec DEFAULT from ");
            sb.append(str);
            sb.append(" fallback to ");
            sb.append(i == 2 ? "SBC" : "LC3");
            eventLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(sb.toString()));
            return i == 2 ? android.hardware.audio.common.V2_0.AudioFormat.SBC : 721420288;
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"AudioDeviceBroker.this.mDeviceStateLock"})
    synchronized void onReceiveBtEvent(android.content.Intent intent) {
        java.lang.String action = intent.getAction();
        android.util.Log.i(TAG, "onReceiveBtEvent action: " + action + " mScoAudioState: " + this.mScoAudioState);
        if (action.equals("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED")) {
            android.bluetooth.BluetoothDevice bluetoothDevice = (android.bluetooth.BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", android.bluetooth.BluetoothDevice.class);
            if (bluetoothDevice != null && !isProfilePoxyConnected(1)) {
                com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("onReceiveBtEvent ACTION_ACTIVE_DEVICE_CHANGED received with null profile proxy for device: " + bluetoothDevice).printLog(TAG));
                return;
            }
            onSetBtScoActiveDevice(bluetoothDevice);
        } else if (action.equals("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED")) {
            onScoAudioStateChanged(intent.getIntExtra("android.bluetooth.profile.extra.STATE", -1));
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @com.android.internal.annotations.GuardedBy({"AudioDeviceBroker.this.mDeviceStateLock"})
    private void onScoAudioStateChanged(int i) {
        int i2 = 2;
        boolean z = false;
        switch (i) {
            case 10:
                this.mDeviceBroker.setBluetoothScoOn(false, "BtHelper.onScoAudioStateChanged");
                if (this.mScoAudioState == 1 && this.mBluetoothHeadset != null && this.mBluetoothHeadsetDevice != null && connectBluetoothScoAudioHelper(this.mBluetoothHeadset, this.mBluetoothHeadsetDevice, this.mScoAudioMode)) {
                    this.mScoAudioState = 3;
                    break;
                } else {
                    r4 = this.mScoAudioState != 2;
                    this.mScoAudioState = 0;
                    i2 = 0;
                    break;
                }
            case 11:
                if (this.mScoAudioState != 3 && this.mScoAudioState != 4) {
                    this.mScoAudioState = 2;
                }
                i2 = -1;
                r4 = false;
                break;
            case 12:
                if (this.mScoAudioState != 3 && this.mScoAudioState != 4) {
                    this.mScoAudioState = 2;
                } else if (this.mDeviceBroker.isBluetoothScoRequested()) {
                    z = true;
                }
                this.mDeviceBroker.setBluetoothScoOn(true, "BtHelper.onScoAudioStateChanged");
                i2 = 1;
                r4 = z;
                break;
            default:
                i2 = -1;
                r4 = false;
                break;
        }
        if (r4) {
            broadcastScoConnectionState(i2);
            android.content.Intent intent = new android.content.Intent("android.media.SCO_AUDIO_STATE_CHANGED");
            intent.putExtra("android.media.extra.SCO_AUDIO_STATE", i2);
            sendStickyBroadcastToAll(intent);
        }
    }

    synchronized boolean isBluetoothScoOn() {
        if (this.mBluetoothHeadset == null || this.mBluetoothHeadsetDevice == null) {
            return false;
        }
        return this.mBluetoothHeadset.getAudioState(this.mBluetoothHeadsetDevice) == 12;
    }

    @com.android.internal.annotations.GuardedBy({"AudioDeviceBroker.this.mDeviceStateLock"})
    synchronized boolean startBluetoothSco(int i, @android.annotation.NonNull java.lang.String str) {
        com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(str));
        return requestScoState(12, i);
    }

    @com.android.internal.annotations.GuardedBy({"AudioDeviceBroker.this.mDeviceStateLock"})
    synchronized boolean stopBluetoothSco(@android.annotation.NonNull java.lang.String str) {
        com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent(str));
        return requestScoState(10, 0);
    }

    synchronized void setLeAudioVolume(int i, int i2, int i3) {
        if (this.mLeAudio == null) {
            return;
        }
        int round = (int) java.lang.Math.round((i * 255.0d) / i2);
        com.android.server.audio.AudioService.sVolumeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.VolumeEvent(10, i3, i, i2, (java.lang.String) null));
        try {
            this.mLeAudio.setVolume(round);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Exception while setting LE volume", e);
        }
    }

    synchronized void setHearingAidVolume(int i, int i2, boolean z) {
        if (this.mHearingAid == null) {
            return;
        }
        int streamVolumeDB = (int) android.media.AudioSystem.getStreamVolumeDB(i2, i / 10, 134217728);
        if (streamVolumeDB < -128) {
            streamVolumeDB = -128;
        }
        if (z) {
            com.android.server.audio.AudioService.sVolumeLogger.enqueue(new com.android.server.audio.AudioServiceEvents.VolumeEvent(3, i, streamVolumeDB));
        }
        try {
            this.mHearingAid.setVolume(streamVolumeDB);
        } catch (java.lang.Exception e) {
            android.util.Log.i(TAG, "Exception while setting hearing aid volume", e);
        }
    }

    synchronized void onBroadcastScoConnectionState(int i) {
        if (i == this.mScoConnectionState) {
            return;
        }
        android.content.Intent intent = new android.content.Intent("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
        intent.putExtra("android.media.extra.SCO_AUDIO_STATE", i);
        intent.putExtra("android.media.extra.SCO_AUDIO_PREVIOUS_STATE", this.mScoConnectionState);
        sendStickyBroadcastToAll(intent);
        this.mScoConnectionState = i;
    }

    @com.android.internal.annotations.GuardedBy({"AudioDeviceBroker.this.mDeviceStateLock"})
    synchronized void resetBluetoothSco() {
        this.mScoAudioState = 0;
        broadcastScoConnectionState(0);
        this.mDeviceBroker.clearA2dpSuspended(false);
        this.mDeviceBroker.clearLeAudioSuspended(false);
        this.mDeviceBroker.setBluetoothScoOn(false, "resetBluetoothSco");
    }

    @com.android.internal.annotations.GuardedBy({"AudioDeviceBroker.this.mDeviceStateLock"})
    synchronized void onBtProfileDisconnected(int i) {
        try {
            com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("BT profile " + android.bluetooth.BluetoothProfile.getProfileName(i) + " disconnected").printLog(TAG));
            switch (i) {
                case 1:
                    this.mBluetoothHeadset = null;
                    break;
                case 2:
                    this.mA2dp = null;
                    break;
                case 11:
                case 26:
                    break;
                case 21:
                    this.mHearingAid = null;
                    break;
                case 22:
                    this.mLeAudio = null;
                    break;
                default:
                    android.util.Log.e(TAG, "onBtProfileDisconnected: Not a valid profile to disconnect " + android.bluetooth.BluetoothProfile.getProfileName(i));
                    break;
            }
        } finally {
        }
    }

    class MyLeAudioCallback implements android.bluetooth.BluetoothLeAudio.Callback {
        MyLeAudioCallback() {
        }

        public void onCodecConfigChanged(int i, @android.annotation.NonNull android.bluetooth.BluetoothLeAudioCodecStatus bluetoothLeAudioCodecStatus) {
        }

        public void onGroupNodeAdded(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice, int i) {
            com.android.server.audio.BtHelper.this.mDeviceBroker.postUpdateLeAudioGroupAddresses(i);
        }

        public void onGroupNodeRemoved(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice, int i) {
            com.android.server.audio.BtHelper.this.mDeviceBroker.postUpdateLeAudioGroupAddresses(i);
        }

        public void onGroupStatusChanged(int i, int i2) {
            com.android.server.audio.BtHelper.this.mDeviceBroker.postUpdateLeAudioGroupAddresses(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"AudioDeviceBroker.this.mDeviceStateLock"})
    synchronized void onBtProfileConnected(int i, android.bluetooth.BluetoothProfile bluetoothProfile) {
        com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("BT profile " + android.bluetooth.BluetoothProfile.getProfileName(i) + " connected to proxy " + bluetoothProfile).printLog(TAG));
        if (bluetoothProfile == null) {
            android.util.Log.e(TAG, "onBtProfileConnected: null proxy for profile: " + i);
            return;
        }
        switch (i) {
            case 1:
                onHeadsetProfileConnected((android.bluetooth.BluetoothHeadset) bluetoothProfile);
                return;
            case 2:
                this.mA2dp = (android.bluetooth.BluetoothA2dp) bluetoothProfile;
                break;
            case 11:
            case 26:
                return;
            case 21:
                this.mHearingAid = (android.bluetooth.BluetoothHearingAid) bluetoothProfile;
                break;
            case 22:
                if (this.mLeAudio == null) {
                    this.mLeAudioCallback = new com.android.server.audio.BtHelper.MyLeAudioCallback();
                    ((android.bluetooth.BluetoothLeAudio) bluetoothProfile).registerCallback(this.mContext.getMainExecutor(), this.mLeAudioCallback);
                }
                this.mLeAudio = (android.bluetooth.BluetoothLeAudio) bluetoothProfile;
                break;
            default:
                android.util.Log.e(TAG, "onBtProfileConnected: Not a valid profile to connect " + android.bluetooth.BluetoothProfile.getProfileName(i));
                break;
        }
        android.bluetooth.BluetoothAdapter defaultAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            android.util.Log.e(TAG, "onBtProfileConnected: Null BluetoothAdapter when connecting profile: " + android.bluetooth.BluetoothProfile.getProfileName(i));
            return;
        }
        java.util.List<android.bluetooth.BluetoothDevice> activeDevices = defaultAdapter.getActiveDevices(i);
        android.media.BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo = new android.media.BluetoothProfileConnectionInfo(i);
        for (android.bluetooth.BluetoothDevice bluetoothDevice : activeDevices) {
            if (bluetoothDevice != null) {
                this.mDeviceBroker.postBluetoothActiveDevice(this.mDeviceBroker.createBtDeviceInfo(new com.android.server.audio.AudioDeviceBroker.BtDeviceChangedData(bluetoothDevice, null, bluetoothProfileConnectionInfo, "mBluetoothProfileServiceListener"), bluetoothDevice, 2), 0);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"AudioDeviceBroker.this.mDeviceStateLock"})
    synchronized boolean isProfilePoxyConnected(int i) {
        switch (i) {
            case 1:
                return this.mBluetoothHeadset != null;
            case 2:
                return this.mA2dp != null;
            case 21:
                return this.mHearingAid != null;
            case 22:
                return this.mLeAudio != null;
            default:
                return true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    @com.android.internal.annotations.GuardedBy({"AudioDeviceBroker.this.mDeviceStateLock"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void onHeadsetProfileConnected(@android.annotation.NonNull android.bluetooth.BluetoothHeadset bluetoothHeadset) {
        boolean z;
        this.mDeviceBroker.handleCancelFailureToConnectToBtHeadsetService();
        this.mBluetoothHeadset = bluetoothHeadset;
        android.bluetooth.BluetoothAdapter defaultAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            for (android.bluetooth.BluetoothDevice bluetoothDevice : defaultAdapter.getActiveDevices(1)) {
                if (bluetoothDevice != null) {
                    onSetBtScoActiveDevice(bluetoothDevice);
                }
            }
        } else {
            android.util.Log.e(TAG, "onHeadsetProfileConnected: Null BluetoothAdapter");
        }
        checkScoAudioState();
        if (this.mScoAudioState != 1 && this.mScoAudioState != 4) {
            return;
        }
        if (this.mBluetoothHeadsetDevice != null) {
            switch (this.mScoAudioState) {
                case 1:
                    z = connectBluetoothScoAudioHelper(this.mBluetoothHeadset, this.mBluetoothHeadsetDevice, this.mScoAudioMode);
                    if (z) {
                        this.mScoAudioState = 3;
                        break;
                    }
                    break;
                case 4:
                    z = disconnectBluetoothScoAudioHelper(this.mBluetoothHeadset, this.mBluetoothHeadsetDevice, this.mScoAudioMode);
                    if (z) {
                        this.mScoAudioState = 5;
                        break;
                    }
                    break;
            }
            if (z) {
                this.mScoAudioState = 0;
                broadcastScoConnectionState(0);
                return;
            }
            return;
        }
        z = false;
        if (z) {
        }
    }

    private void broadcastScoConnectionState(int i) {
        this.mDeviceBroker.postBroadcastScoConnectionState(i);
    }

    @android.annotation.Nullable
    android.media.AudioDeviceAttributes getHeadsetAudioDevice() {
        if (this.mBluetoothHeadsetDevice == null) {
            return null;
        }
        return getHeadsetAudioDevice(this.mBluetoothHeadsetDevice);
    }

    @android.annotation.NonNull
    private android.media.AudioDeviceAttributes getHeadsetAudioDevice(android.bluetooth.BluetoothDevice bluetoothDevice) {
        android.media.AudioDeviceAttributes audioDeviceAttributes = this.mResolvedScoAudioDevices.get(bluetoothDevice);
        if (audioDeviceAttributes != null) {
            return audioDeviceAttributes;
        }
        return btHeadsetDeviceToAudioDevice(bluetoothDevice);
    }

    private static android.media.AudioDeviceAttributes btHeadsetDeviceToAudioDevice(android.bluetooth.BluetoothDevice bluetoothDevice) {
        java.lang.String str = "";
        int i = 16;
        if (bluetoothDevice == null) {
            return new android.media.AudioDeviceAttributes(16, "");
        }
        java.lang.String address = bluetoothDevice.getAddress();
        java.lang.String name = getName(bluetoothDevice);
        if (android.bluetooth.BluetoothAdapter.checkBluetoothAddress(address)) {
            str = address;
        }
        android.bluetooth.BluetoothClass bluetoothClass = bluetoothDevice.getBluetoothClass();
        if (bluetoothClass != null) {
            switch (bluetoothClass.getDeviceClass()) {
                case com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_SKRPHONE_SUPRESS /* 1028 */:
                case 1032:
                    i = 32;
                    break;
                case 1056:
                    i = 64;
                    break;
            }
        }
        return new android.media.AudioDeviceAttributes(i, str, name);
    }

    private boolean handleBtScoActiveDeviceChange(android.bluetooth.BluetoothDevice bluetoothDevice, boolean z) {
        boolean z2;
        boolean z3 = true;
        if (bluetoothDevice == null) {
            return true;
        }
        android.media.AudioDeviceAttributes btHeadsetDeviceToAudioDevice = btHeadsetDeviceToAudioDevice(bluetoothDevice);
        if (z) {
            z2 = this.mDeviceBroker.handleDeviceConnection(btHeadsetDeviceToAudioDevice, z, bluetoothDevice) | false;
        } else {
            int[] iArr = {16, 32, 64};
            boolean z4 = false;
            for (int i = 0; i < 3; i++) {
                z4 |= this.mDeviceBroker.handleDeviceConnection(new android.media.AudioDeviceAttributes(iArr[i], btHeadsetDeviceToAudioDevice.getAddress(), btHeadsetDeviceToAudioDevice.getName()), z, bluetoothDevice);
            }
            z2 = z4;
        }
        if (!this.mDeviceBroker.handleDeviceConnection(new android.media.AudioDeviceAttributes(-2147483640, btHeadsetDeviceToAudioDevice.getAddress(), btHeadsetDeviceToAudioDevice.getName()), z, bluetoothDevice) || !z2) {
            z3 = false;
        }
        if (z3) {
            if (z) {
                this.mResolvedScoAudioDevices.put(bluetoothDevice, btHeadsetDeviceToAudioDevice);
            } else {
                this.mResolvedScoAudioDevices.remove(bluetoothDevice);
            }
        }
        return z3;
    }

    private java.lang.String getAnonymizedAddress(android.bluetooth.BluetoothDevice bluetoothDevice) {
        return bluetoothDevice == null ? "(null)" : bluetoothDevice.getAnonymizedAddress();
    }

    @com.android.internal.annotations.GuardedBy({"AudioDeviceBroker.this.mDeviceStateLock"})
    synchronized void onSetBtScoActiveDevice(android.bluetooth.BluetoothDevice bluetoothDevice) {
        try {
            android.util.Log.i(TAG, "onSetBtScoActiveDevice: " + getAnonymizedAddress(this.mBluetoothHeadsetDevice) + " -> " + getAnonymizedAddress(bluetoothDevice));
            android.bluetooth.BluetoothDevice bluetoothDevice2 = this.mBluetoothHeadsetDevice;
            if (java.util.Objects.equals(bluetoothDevice, bluetoothDevice2)) {
                return;
            }
            if (!handleBtScoActiveDeviceChange(bluetoothDevice2, false)) {
                android.util.Log.w(TAG, "onSetBtScoActiveDevice() failed to remove previous device " + getAnonymizedAddress(bluetoothDevice2));
            }
            if (!handleBtScoActiveDeviceChange(bluetoothDevice, true)) {
                android.util.Log.e(TAG, "onSetBtScoActiveDevice() failed to add new device " + getAnonymizedAddress(bluetoothDevice));
                bluetoothDevice = null;
            }
            this.mBluetoothHeadsetDevice = bluetoothDevice;
            if (this.mBluetoothHeadsetDevice == null) {
                resetBluetoothSco();
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @com.android.internal.annotations.GuardedBy({"BtHelper.this"})
    private boolean requestScoState(int i, int i2) {
        checkScoAudioState();
        if (i == 12) {
            if (this.mScoAudioState != 3) {
                broadcastScoConnectionState(2);
            }
            switch (this.mScoAudioState) {
                case 0:
                    this.mScoAudioMode = i2;
                    if (i2 == -1) {
                        this.mScoAudioMode = 0;
                        if (this.mBluetoothHeadsetDevice != null) {
                            this.mScoAudioMode = android.provider.Settings.Global.getInt(this.mDeviceBroker.getContentResolver(), "bluetooth_sco_channel_" + this.mBluetoothHeadsetDevice.getAddress(), 0);
                            if (this.mScoAudioMode > 2 || this.mScoAudioMode < 0) {
                                this.mScoAudioMode = 0;
                            }
                        }
                    }
                    if (this.mBluetoothHeadset == null) {
                        if (getBluetoothHeadset()) {
                            this.mScoAudioState = 1;
                            break;
                        } else {
                            android.util.Log.w(TAG, "requestScoState: getBluetoothHeadset failed during connection, mScoAudioMode=" + this.mScoAudioMode);
                            broadcastScoConnectionState(0);
                            return false;
                        }
                    } else {
                        if (this.mBluetoothHeadsetDevice == null) {
                            android.util.Log.w(TAG, "requestScoState: no active device while connecting, mScoAudioMode=" + this.mScoAudioMode);
                            broadcastScoConnectionState(0);
                            return false;
                        }
                        if (connectBluetoothScoAudioHelper(this.mBluetoothHeadset, this.mBluetoothHeadsetDevice, this.mScoAudioMode)) {
                            this.mScoAudioState = 3;
                            break;
                        } else {
                            android.util.Log.w(TAG, "requestScoState: connect to " + getAnonymizedAddress(this.mBluetoothHeadsetDevice) + " failed, mScoAudioMode=" + this.mScoAudioMode);
                            broadcastScoConnectionState(0);
                            return false;
                        }
                    }
                case 1:
                default:
                    android.util.Log.w(TAG, "requestScoState: failed to connect in state " + this.mScoAudioState + ", scoAudioMode=" + i2);
                    broadcastScoConnectionState(0);
                    return false;
                case 2:
                    broadcastScoConnectionState(1);
                    break;
                case 3:
                    break;
                case 4:
                    this.mScoAudioState = 3;
                    broadcastScoConnectionState(1);
                    break;
                case 5:
                    this.mScoAudioState = 1;
                    break;
            }
        } else if (i == 10) {
            switch (this.mScoAudioState) {
                case 1:
                    this.mScoAudioState = 0;
                    broadcastScoConnectionState(0);
                    break;
                case 2:
                default:
                    android.util.Log.w(TAG, "requestScoState: failed to disconnect in state " + this.mScoAudioState + ", scoAudioMode=" + i2);
                    broadcastScoConnectionState(0);
                    return false;
                case 3:
                    if (this.mBluetoothHeadset == null) {
                        if (getBluetoothHeadset()) {
                            this.mScoAudioState = 4;
                            break;
                        } else {
                            android.util.Log.w(TAG, "requestScoState: getBluetoothHeadset failed during disconnection, mScoAudioMode=" + this.mScoAudioMode);
                            this.mScoAudioState = 0;
                            broadcastScoConnectionState(0);
                            return false;
                        }
                    } else if (this.mBluetoothHeadsetDevice == null) {
                        this.mScoAudioState = 0;
                        broadcastScoConnectionState(0);
                        break;
                    } else if (disconnectBluetoothScoAudioHelper(this.mBluetoothHeadset, this.mBluetoothHeadsetDevice, this.mScoAudioMode)) {
                        this.mScoAudioState = 5;
                        break;
                    } else {
                        this.mScoAudioState = 0;
                        broadcastScoConnectionState(0);
                        break;
                    }
            }
        }
        return true;
    }

    private void sendStickyBroadcastToAll(android.content.Intent intent) {
        intent.addFlags(268435456);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mDeviceBroker.getContext().sendStickyBroadcastAsUser(intent, android.os.UserHandle.ALL);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private static boolean disconnectBluetoothScoAudioHelper(android.bluetooth.BluetoothHeadset bluetoothHeadset, android.bluetooth.BluetoothDevice bluetoothDevice, int i) {
        switch (i) {
            case 0:
                return bluetoothHeadset.stopScoUsingVirtualVoiceCall();
            case 1:
            default:
                return false;
            case 2:
                return bluetoothHeadset.stopVoiceRecognition(bluetoothDevice);
        }
    }

    private static boolean connectBluetoothScoAudioHelper(android.bluetooth.BluetoothHeadset bluetoothHeadset, android.bluetooth.BluetoothDevice bluetoothDevice, int i) {
        switch (i) {
            case 0:
                return bluetoothHeadset.startScoUsingVirtualVoiceCall();
            case 1:
            default:
                return false;
            case 2:
                return bluetoothHeadset.startVoiceRecognition(bluetoothDevice);
        }
    }

    private void checkScoAudioState() {
        if (this.mBluetoothHeadset != null && this.mBluetoothHeadsetDevice != null && this.mScoAudioState == 0 && this.mBluetoothHeadset.getAudioState(this.mBluetoothHeadsetDevice) != 10) {
            this.mScoAudioState = 2;
        }
    }

    private boolean getBluetoothHeadset() {
        boolean z;
        android.bluetooth.BluetoothAdapter defaultAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            z = false;
        } else {
            z = defaultAdapter.getProfileProxy(this.mDeviceBroker.getContext(), this.mBluetoothProfileServiceListener, 1);
        }
        this.mDeviceBroker.handleFailureToConnectToBtHeadsetService(z ? 3000 : 0);
        return z;
    }

    int getLeAudioDeviceGroupId(android.bluetooth.BluetoothDevice bluetoothDevice) {
        if (this.mLeAudio == null || bluetoothDevice == null) {
            return -1;
        }
        return this.mLeAudio.getGroupId(bluetoothDevice);
    }

    java.util.List<android.util.Pair<java.lang.String, java.lang.String>> getLeAudioGroupAddresses(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.bluetooth.BluetoothAdapter defaultAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || this.mLeAudio == null) {
            return arrayList;
        }
        for (android.bluetooth.BluetoothDevice bluetoothDevice : defaultAdapter.getActiveDevices(22)) {
            if (bluetoothDevice != null && this.mLeAudio.getGroupId(bluetoothDevice) == i) {
                arrayList.add(new android.util.Pair(bluetoothDevice.getAddress(), bluetoothDevice.getIdentityAddress()));
            }
        }
        return arrayList;
    }

    public static java.lang.String bluetoothCodecToEncodingString(int i) {
        switch (i) {
            case 0:
                return "ENCODING_SBC";
            case 1:
                return "ENCODING_AAC";
            case 2:
                return "ENCODING_APTX";
            case 3:
                return "ENCODING_APTX_HD";
            case 4:
                return "ENCODING_LDAC";
            case 5:
            default:
                return "ENCODING_BT_CODEC_TYPE(" + i + ")";
            case 6:
                return "ENCODING_OPUS";
        }
    }

    static int getProfileFromType(int i) {
        if (android.media.AudioSystem.isBluetoothA2dpOutDevice(i)) {
            return 2;
        }
        if (android.media.AudioSystem.isBluetoothScoDevice(i)) {
            return 1;
        }
        if (android.media.AudioSystem.isBluetoothLeDevice(i)) {
            return 22;
        }
        return 0;
    }

    static android.os.Bundle getPreferredAudioProfiles(java.lang.String str) {
        android.bluetooth.BluetoothAdapter defaultAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
        return defaultAdapter.getPreferredAudioProfiles(defaultAdapter.getRemoteDevice(str));
    }

    @android.annotation.Nullable
    static android.bluetooth.BluetoothDevice getBluetoothDevice(java.lang.String str) {
        android.bluetooth.BluetoothAdapter defaultAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null || !android.bluetooth.BluetoothAdapter.checkBluetoothAddress(str)) {
            return null;
        }
        return defaultAdapter.getRemoteDevice(str);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static int getBtDeviceCategory(java.lang.String str) {
        android.bluetooth.BluetoothDevice bluetoothDevice;
        byte[] metadata;
        char c;
        if (!android.media.audio.Flags.automaticBtDeviceType() || (bluetoothDevice = getBluetoothDevice(str)) == null || (metadata = bluetoothDevice.getMetadata(17)) == null) {
            return 0;
        }
        java.lang.String str2 = new java.lang.String(metadata);
        switch (str2.hashCode()) {
            case -1834993054:
                if (str2.equals("Headset")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1085510111:
                if (str2.equals("Default")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -672479864:
                if (str2.equals("HearingAid")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -343869473:
                if (str2.equals("Speaker")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 83350703:
                if (str2.equals("Watch")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 104438316:
                if (str2.equals("Untethered Headset")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 2011237026:
                if (str2.equals("Carkit")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                break;
            case 1:
                break;
            case 2:
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                android.bluetooth.BluetoothClass bluetoothClass = bluetoothDevice.getBluetoothClass();
                if (bluetoothClass != null) {
                    switch (bluetoothClass.getDeviceClass()) {
                        case com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_SKRPHONE_SUPRESS /* 1028 */:
                        case 1048:
                            break;
                        case 1044:
                        case 1052:
                        case 1084:
                            break;
                        case 1064:
                            break;
                        case com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EMBED_DAT /* 1796 */:
                            break;
                    }
                }
                break;
        }
        return 0;
    }

    public static void onNotifyPreferredAudioProfileApplied(android.bluetooth.BluetoothDevice bluetoothDevice) {
        android.bluetooth.BluetoothAdapter.getDefaultAdapter().notifyActiveDeviceChangeApplied(bluetoothDevice);
    }

    public static java.lang.String btDeviceClassToString(int i) {
        switch (i) {
            case 1024:
                return "AUDIO_VIDEO_UNCATEGORIZED";
            case com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_BIDIR_SKRPHONE_SUPRESS /* 1028 */:
                return "AUDIO_VIDEO_WEARABLE_HEADSET";
            case 1032:
                return "AUDIO_VIDEO_HANDSFREE";
            case 1036:
                return "AUDIO_VIDEO_RESERVED_0x040C";
            case 1040:
                return "AUDIO_VIDEO_MICROPHONE";
            case 1044:
                return "AUDIO_VIDEO_LOUDSPEAKER";
            case 1048:
                return "AUDIO_VIDEO_HEADPHONES";
            case 1052:
                return "AUDIO_VIDEO_PORTABLE_AUDIO";
            case 1056:
                return "AUDIO_VIDEO_CAR_AUDIO";
            case 1060:
                return "AUDIO_VIDEO_SET_TOP_BOX";
            case 1064:
                return "AUDIO_VIDEO_HIFI_AUDIO";
            case 1068:
                return "AUDIO_VIDEO_VCR";
            case 1072:
                return "AUDIO_VIDEO_VIDEO_CAMERA";
            case 1076:
                return "AUDIO_VIDEO_CAMCORDER";
            case 1080:
                return "AUDIO_VIDEO_VIDEO_MONITOR";
            case 1084:
                return "AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER";
            case 1088:
                return "AUDIO_VIDEO_VIDEO_CONFERENCING";
            case 1092:
                return "AUDIO_VIDEO_RESERVED_0x0444";
            case 1096:
                return "AUDIO_VIDEO_VIDEO_GAMING_TOY";
            default:
                return android.text.TextUtils.formatSimple("0x%04x", new java.lang.Object[]{java.lang.Integer.valueOf(i)});
        }
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        android.bluetooth.BluetoothClass bluetoothClass;
        printWriter.println("\n" + str + "mBluetoothHeadset: " + this.mBluetoothHeadset);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append("mBluetoothHeadsetDevice: ");
        sb.append(this.mBluetoothHeadsetDevice);
        printWriter.println(sb.toString());
        if (this.mBluetoothHeadsetDevice != null && (bluetoothClass = this.mBluetoothHeadsetDevice.getBluetoothClass()) != null) {
            printWriter.println(str + "mBluetoothHeadsetDevice.DeviceClass: " + btDeviceClassToString(bluetoothClass.getDeviceClass()));
        }
        printWriter.println(str + "mScoAudioState: " + scoAudioStateToString(this.mScoAudioState));
        printWriter.println(str + "mScoAudioMode: " + scoAudioModeToString(this.mScoAudioMode));
        printWriter.println("\n" + str + "mHearingAid: " + this.mHearingAid);
        printWriter.println("\n" + str + "mLeAudio: " + this.mLeAudio);
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append(str);
        sb2.append("mA2dp: ");
        sb2.append(this.mA2dp);
        printWriter.println(sb2.toString());
        printWriter.println(str + "mAvrcpAbsVolSupported: " + this.mAvrcpAbsVolSupported);
    }
}
