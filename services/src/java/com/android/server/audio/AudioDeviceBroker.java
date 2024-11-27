package com.android.server.audio;

/* loaded from: classes.dex */
public class AudioDeviceBroker {
    private static final long BROKER_WAKELOCK_TIMEOUT_MS = 5000;
    static final int BTA2DP_DOCK_TIMEOUT_MS = 8000;
    private static final int BTA2DP_MUTE_CHECK_DELAY_MS = 100;
    static final int BT_HEADSET_CNCT_TIMEOUT_MS = 3000;
    private static final int CHECK_CLIENT_STATE_DELAY_MS = 6000;
    private static final int MSG_BROADCAST_AUDIO_BECOMING_NOISY = 12;
    private static final int MSG_BT_HEADSET_CNCT_FAILED = 9;
    private static final int MSG_CHECK_COMMUNICATION_ROUTE_CLIENT_STATE = 56;
    private static final int MSG_CHECK_MUTE_MUSIC = 35;
    private static final int MSG_IIL_BTLEAUDIO_TIMEOUT = 49;
    private static final int MSG_IIL_SET_FORCE_BT_A2DP_USE = 5;
    private static final int MSG_IIL_SET_FORCE_USE = 4;
    private static final int MSG_II_SET_HEARING_AID_VOLUME = 14;
    private static final int MSG_II_SET_LE_AUDIO_OUT_VOLUME = 46;
    private static final int MSG_IL_BTA2DP_TIMEOUT = 10;
    private static final int MSG_IL_BT_SERVICE_CONNECTED_PROFILE = 23;
    private static final int MSG_IL_SAVE_NDEF_DEVICE_FOR_STRATEGY = 47;
    private static final int MSG_IL_SAVE_PREF_DEVICES_FOR_CAPTURE_PRESET = 37;
    private static final int MSG_IL_SAVE_PREF_DEVICES_FOR_STRATEGY = 32;
    private static final int MSG_IL_SAVE_REMOVE_NDEF_DEVICE_FOR_STRATEGY = 48;
    private static final int MSG_IL_UPDATE_COMMUNICATION_ROUTE_CLIENT = 43;
    private static final int MSG_I_BROADCAST_BT_CONNECTION_STATE = 3;
    private static final int MSG_I_BT_SERVICE_DISCONNECTED_PROFILE = 22;
    private static final int MSG_I_SAVE_CLEAR_PREF_DEVICES_FOR_CAPTURE_PRESET = 38;
    private static final int MSG_I_SAVE_REMOVE_PREF_DEVICES_FOR_STRATEGY = 33;
    private static final int MSG_I_SET_AVRCP_ABSOLUTE_VOLUME = 15;
    private static final int MSG_I_SET_MODE_OWNER = 16;
    private static final int MSG_I_UPDATE_LE_AUDIO_GROUP_ADDRESSES = 57;
    private static final int MSG_L_A2DP_DEVICE_CONNECTION_CHANGE_EXT = 29;
    private static final int MSG_L_BLUETOOTH_DEVICE_CONFIG_CHANGE = 11;
    private static final int MSG_L_BT_ACTIVE_DEVICE_CHANGE_EXT = 45;
    private static final int MSG_L_CHECK_COMMUNICATION_DEVICE_REMOVAL = 53;
    private static final int MSG_L_COMMUNICATION_ROUTE_CLIENT_DIED = 34;
    private static final int MSG_L_HEARING_AID_DEVICE_CONNECTION_CHANGE_EXT = 31;
    private static final int MSG_L_NOTIFY_PREFERRED_AUDIOPROFILE_APPLIED = 52;
    private static final int MSG_L_RECEIVED_BT_EVENT = 55;
    private static final int MSG_L_SET_BT_ACTIVE_DEVICE = 7;
    private static final int MSG_L_SET_COMMUNICATION_DEVICE_FOR_CLIENT = 42;
    private static final int MSG_L_SET_WIRED_DEVICE_CONNECTION_STATE = 2;
    private static final int MSG_L_SYNCHRONIZE_ADI_DEVICES_IN_INVENTORY = 58;
    private static final int MSG_L_UPDATED_ADI_DEVICE_STATE = 59;
    private static final int MSG_PERSIST_AUDIO_DEVICE_SETTINGS = 54;
    private static final int MSG_REPORT_NEW_ROUTES = 13;
    private static final int MSG_REPORT_NEW_ROUTES_A2DP = 36;
    private static final int MSG_RESTORE_DEVICES = 1;
    private static final int MSG_TOGGLE_HDMI = 6;
    private static final int SENDMSG_NOOP = 1;
    private static final int SENDMSG_QUEUE = 2;
    private static final int SENDMSG_REPLACE = 0;
    private static final long SET_COMMUNICATION_DEVICE_TIMEOUT_MS = 3000;
    private static final java.lang.String TAG = "AS.AudioDeviceBroker";
    public static final long USE_SET_COMMUNICATION_DEVICE = 243827847;
    private int mAccessibilityStrategyId;
    android.media.AudioDeviceInfo mActiveCommunicationDevice;
    private com.android.server.audio.AudioDeviceBroker.AudioModeInfo mAudioModeOwner;

    @android.annotation.NonNull
    private final com.android.server.audio.AudioService mAudioService;

    @android.annotation.NonNull
    private final com.android.server.audio.AudioSystemAdapter mAudioSystem;

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    private boolean mBluetoothA2dpEnabled;

    @com.android.internal.annotations.GuardedBy({"mBluetoothAudioStateLock"})
    private boolean mBluetoothA2dpSuspendedApplied;

    @com.android.internal.annotations.GuardedBy({"mBluetoothAudioStateLock"})
    private boolean mBluetoothA2dpSuspendedExt;

    @com.android.internal.annotations.GuardedBy({"mBluetoothAudioStateLock"})
    private boolean mBluetoothA2dpSuspendedInt;
    private final java.lang.Object mBluetoothAudioStateLock;

    @com.android.internal.annotations.GuardedBy({"mBluetoothAudioStateLock"})
    private boolean mBluetoothLeSuspendedApplied;

    @com.android.internal.annotations.GuardedBy({"mBluetoothAudioStateLock"})
    private boolean mBluetoothLeSuspendedExt;

    @com.android.internal.annotations.GuardedBy({"mBluetoothAudioStateLock"})
    private boolean mBluetoothLeSuspendedInt;

    @com.android.internal.annotations.GuardedBy({"mBluetoothAudioStateLock"})
    private boolean mBluetoothScoOn;

    @com.android.internal.annotations.GuardedBy({"mBluetoothAudioStateLock"})
    private boolean mBluetoothScoOnApplied;
    private android.os.PowerManager.WakeLock mBrokerEventWakeLock;
    private com.android.server.audio.AudioDeviceBroker.BrokerHandler mBrokerHandler;
    private com.android.server.audio.AudioDeviceBroker.BrokerThread mBrokerThread;
    private final com.android.server.audio.BtHelper mBtHelper;
    final android.os.RemoteCallbackList<android.media.ICommunicationDeviceDispatcher> mCommDevDispatchers;
    private java.lang.Object mCommunicationDeviceLock;

    @com.android.internal.annotations.GuardedBy({"mCommunicationDeviceLock"})
    private int mCommunicationDeviceUpdateCount;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    private final java.util.LinkedList<com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient> mCommunicationRouteClients;
    int mCommunicationStrategyId;

    @android.annotation.NonNull
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    int mCurCommunicationPortId;
    private final com.android.server.audio.AudioDeviceInventory mDeviceInventory;
    private final java.lang.Object mDeviceStateLock;
    private java.util.concurrent.atomic.AtomicBoolean mMusicMuted;
    private android.media.AudioDeviceAttributes mPreferredCommunicationDevice;
    final java.lang.Object mSetModeLock;
    private final com.android.server.audio.SystemServerAdapter mSystemServer;
    private static final java.lang.Object sLastDeviceConnectionMsgTimeLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"sLastDeviceConnectionMsgTimeLock"})
    private static long sLastDeviceConnectMsgTime = 0;
    private static final int[] VALID_COMMUNICATION_DEVICE_TYPES = {2, 7, 3, 22, 1, 4, 23, 26, 11, 27, 5, 9, 19};
    private static final java.util.Set<java.lang.Integer> MESSAGES_MUTE_MUSIC = new java.util.HashSet();

    static {
        MESSAGES_MUTE_MUSIC.add(7);
        MESSAGES_MUTE_MUSIC.add(11);
        MESSAGES_MUTE_MUSIC.add(29);
        MESSAGES_MUTE_MUSIC.add(5);
    }

    static final class AudioModeInfo {
        final int mMode;
        final int mPid;
        final int mUid;

        AudioModeInfo(int i, int i2, int i3) {
            this.mMode = i;
            this.mPid = i2;
            this.mUid = i3;
        }

        public java.lang.String toString() {
            return "AudioModeInfo: mMode=" + android.media.AudioSystem.modeToString(this.mMode) + ", mPid=" + this.mPid + ", mUid=" + this.mUid;
        }
    }

    AudioDeviceBroker(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.audio.AudioService audioService, @android.annotation.NonNull com.android.server.audio.AudioSystemAdapter audioSystemAdapter) {
        this.mCommunicationStrategyId = -1;
        this.mAccessibilityStrategyId = -1;
        this.mDeviceStateLock = new java.lang.Object();
        this.mSetModeLock = new java.lang.Object();
        this.mAudioModeOwner = new com.android.server.audio.AudioDeviceBroker.AudioModeInfo(0, 0, 0);
        this.mCommunicationDeviceLock = new java.lang.Object();
        this.mCommunicationDeviceUpdateCount = 0;
        this.mBluetoothAudioStateLock = new java.lang.Object();
        this.mCommDevDispatchers = new android.os.RemoteCallbackList<>();
        this.mCurCommunicationPortId = -1;
        this.mMusicMuted = new java.util.concurrent.atomic.AtomicBoolean(false);
        this.mCommunicationRouteClients = new java.util.LinkedList<>();
        this.mContext = context;
        this.mAudioService = audioService;
        this.mBtHelper = new com.android.server.audio.BtHelper(this, context);
        this.mDeviceInventory = new com.android.server.audio.AudioDeviceInventory(this);
        this.mSystemServer = com.android.server.audio.SystemServerAdapter.getDefaultAdapter(this.mContext);
        this.mAudioSystem = audioSystemAdapter;
        init();
    }

    AudioDeviceBroker(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.audio.AudioService audioService, @android.annotation.NonNull com.android.server.audio.AudioDeviceInventory audioDeviceInventory, @android.annotation.NonNull com.android.server.audio.SystemServerAdapter systemServerAdapter, @android.annotation.NonNull com.android.server.audio.AudioSystemAdapter audioSystemAdapter) {
        this.mCommunicationStrategyId = -1;
        this.mAccessibilityStrategyId = -1;
        this.mDeviceStateLock = new java.lang.Object();
        this.mSetModeLock = new java.lang.Object();
        this.mAudioModeOwner = new com.android.server.audio.AudioDeviceBroker.AudioModeInfo(0, 0, 0);
        this.mCommunicationDeviceLock = new java.lang.Object();
        this.mCommunicationDeviceUpdateCount = 0;
        this.mBluetoothAudioStateLock = new java.lang.Object();
        this.mCommDevDispatchers = new android.os.RemoteCallbackList<>();
        this.mCurCommunicationPortId = -1;
        this.mMusicMuted = new java.util.concurrent.atomic.AtomicBoolean(false);
        this.mCommunicationRouteClients = new java.util.LinkedList<>();
        this.mContext = context;
        this.mAudioService = audioService;
        this.mBtHelper = new com.android.server.audio.BtHelper(this, context);
        this.mDeviceInventory = audioDeviceInventory;
        this.mSystemServer = systemServerAdapter;
        this.mAudioSystem = audioSystemAdapter;
        init();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRoutingStrategyIds() {
        java.util.List<android.media.audiopolicy.AudioProductStrategy> audioProductStrategies = android.media.audiopolicy.AudioProductStrategy.getAudioProductStrategies();
        this.mCommunicationStrategyId = -1;
        this.mAccessibilityStrategyId = -1;
        for (android.media.audiopolicy.AudioProductStrategy audioProductStrategy : audioProductStrategies) {
            if (this.mCommunicationStrategyId == -1 && audioProductStrategy.getAudioAttributesForLegacyStreamType(0) != null) {
                this.mCommunicationStrategyId = audioProductStrategy.getId();
            }
            if (this.mAccessibilityStrategyId == -1 && audioProductStrategy.getAudioAttributesForLegacyStreamType(10) != null) {
                this.mAccessibilityStrategyId = audioProductStrategy.getId();
            }
        }
    }

    private void init() {
        setupMessaging(this.mContext);
        initAudioHalBluetoothState();
        initRoutingStrategyIds();
        this.mPreferredCommunicationDevice = null;
        updateActiveCommunicationDevice();
        this.mSystemServer.registerUserStartedReceiver(this.mContext);
    }

    android.content.Context getContext() {
        return this.mContext;
    }

    void onSystemReady() {
        synchronized (this.mSetModeLock) {
            synchronized (this.mDeviceStateLock) {
                this.mAudioModeOwner = this.mAudioService.getAudioModeOwner();
                this.mBtHelper.onSystemReady();
            }
        }
    }

    void onAudioServerDied() {
        sendMsgNoDelay(1, 0);
    }

    void setForceUse_Async(int i, int i2, java.lang.String str) {
        sendIILMsgNoDelay(4, 2, i, i2, str);
    }

    void toggleHdmiIfConnected_Async() {
        sendMsgNoDelay(6, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onReceiveBtEvent(@android.annotation.NonNull android.content.Intent intent) {
        this.mBtHelper.onReceiveBtEvent(intent);
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    void onSetBtScoActiveDevice(android.bluetooth.BluetoothDevice bluetoothDevice) {
        this.mBtHelper.onSetBtScoActiveDevice(bluetoothDevice);
    }

    void setBluetoothA2dpOn_Async(boolean z, java.lang.String str) {
        synchronized (this.mDeviceStateLock) {
            try {
                if (this.mBluetoothA2dpEnabled == z) {
                    return;
                }
                this.mBluetoothA2dpEnabled = z;
                this.mBrokerHandler.removeMessages(5);
                sendIILMsgNoDelay(5, 2, 1, this.mBluetoothA2dpEnabled ? 0 : 10, str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setSpeakerphoneOn(android.os.IBinder iBinder, int i, boolean z, boolean z2, java.lang.String str) {
        postSetCommunicationDeviceForClient(new com.android.server.audio.AudioDeviceBroker.CommunicationDeviceInfo(iBinder, i, new android.media.AudioDeviceAttributes(2, ""), z, -1, str, z2));
    }

    boolean setCommunicationDevice(android.os.IBinder iBinder, int i, android.media.AudioDeviceInfo audioDeviceInfo, boolean z, java.lang.String str) {
        synchronized (this.mDeviceStateLock) {
            if (audioDeviceInfo == null) {
                try {
                    if (getCommunicationRouteClientForUid(i) == null) {
                        return false;
                    }
                } finally {
                }
            }
            synchronized (this.mCommunicationDeviceLock) {
                try {
                    this.mCommunicationDeviceUpdateCount++;
                    postSetCommunicationDeviceForClient(new com.android.server.audio.AudioDeviceBroker.CommunicationDeviceInfo(iBinder, i, audioDeviceInfo != null ? new android.media.AudioDeviceAttributes(audioDeviceInfo) : null, audioDeviceInfo != null, -1, str, z));
                } finally {
                }
            }
            return true;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    void onSetCommunicationDeviceForClient(com.android.server.audio.AudioDeviceBroker.CommunicationDeviceInfo communicationDeviceInfo) {
        if (!communicationDeviceInfo.mOn) {
            com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient communicationRouteClientForUid = getCommunicationRouteClientForUid(communicationDeviceInfo.mUid);
            if (communicationRouteClientForUid != null) {
                if (communicationDeviceInfo.mDevice != null && !communicationDeviceInfo.mDevice.equals(communicationRouteClientForUid.getDevice())) {
                    return;
                }
            } else {
                return;
            }
        }
        setCommunicationRouteForClient(communicationDeviceInfo.mCb, communicationDeviceInfo.mUid, communicationDeviceInfo.mOn ? communicationDeviceInfo.mDevice : null, communicationDeviceInfo.mScoAudioMode, communicationDeviceInfo.mIsPrivileged, communicationDeviceInfo.mEventSource);
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    void setCommunicationRouteForClient(android.os.IBinder iBinder, int i, android.media.AudioDeviceAttributes audioDeviceAttributes, int i2, boolean z, java.lang.String str) {
        android.media.AudioDeviceAttributes audioDeviceAttributes2;
        boolean z2;
        com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient removeCommunicationRouteClient;
        com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("setCommunicationRouteForClient for uid: " + i + " device: " + audioDeviceAttributes + " isPrivileged: " + z + " from API: " + str).printLog(TAG));
        boolean isBluetoothScoRequested = isBluetoothScoRequested();
        com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient communicationRouteClientForUid = getCommunicationRouteClientForUid(i);
        if (communicationRouteClientForUid == null) {
            audioDeviceAttributes2 = null;
            z2 = false;
        } else {
            audioDeviceAttributes2 = communicationRouteClientForUid.getDevice();
            z2 = communicationRouteClientForUid.isPrivileged();
        }
        if (audioDeviceAttributes != null) {
            removeCommunicationRouteClient = addCommunicationRouteClient(iBinder, i, audioDeviceAttributes, z);
            if (removeCommunicationRouteClient == null) {
                android.util.Log.w(TAG, "setCommunicationRouteForClient: could not add client for uid: " + i + " and device: " + audioDeviceAttributes);
            }
        } else {
            removeCommunicationRouteClient = removeCommunicationRouteClient(iBinder, true);
        }
        if (removeCommunicationRouteClient == null) {
            return;
        }
        boolean isBluetoothScoRequested2 = isBluetoothScoRequested();
        if (isBluetoothScoRequested2 && (!isBluetoothScoRequested || !isBluetoothScoActive())) {
            if (!this.mBtHelper.startBluetoothSco(i2, str)) {
                android.util.Log.w(TAG, "setCommunicationRouteForClient: failure to start BT SCO for uid: " + i);
                if (audioDeviceAttributes2 != null) {
                    addCommunicationRouteClient(iBinder, i, audioDeviceAttributes2, z2);
                } else {
                    removeCommunicationRouteClient(iBinder, true);
                }
                postBroadcastScoConnectionState(0);
            }
        } else if (!isBluetoothScoRequested2 && isBluetoothScoRequested) {
            this.mBtHelper.stopBluetoothSco(str);
        }
        if (isBluetoothLeAudioRequested() && audioDeviceAttributes != null) {
            int bluetoothContextualVolumeStream = this.mAudioService.getBluetoothContextualVolumeStream();
            postSetLeAudioVolumeIndex(getVssVolumeForDevice(bluetoothContextualVolumeStream, audioDeviceAttributes.getInternalType()), getMaxVssVolumeForStream(bluetoothContextualVolumeStream), bluetoothContextualVolumeStream);
        }
        updateCommunicationRoute(str);
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    private com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient topCommunicationRouteClient() {
        java.util.Iterator<com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient> it = this.mCommunicationRouteClients.iterator();
        while (it.hasNext()) {
            com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient next = it.next();
            if (next.getUid() == this.mAudioModeOwner.mUid) {
                return next;
            }
        }
        if (!this.mCommunicationRouteClients.isEmpty() && this.mAudioModeOwner.mPid == 0 && this.mCommunicationRouteClients.get(0).isActive()) {
            return this.mCommunicationRouteClients.get(0);
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    private android.media.AudioDeviceAttributes requestedCommunicationDevice() {
        com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient communicationRouteClient = topCommunicationRouteClient();
        if (communicationRouteClient != null) {
            return communicationRouteClient.getDevice();
        }
        return null;
    }

    static boolean isValidCommunicationDevice(@android.annotation.NonNull android.media.AudioDeviceInfo audioDeviceInfo) {
        java.util.Objects.requireNonNull(audioDeviceInfo, "device must not be null");
        return audioDeviceInfo.isSink() && isValidCommunicationDeviceType(audioDeviceInfo.getType());
    }

    private static boolean isValidCommunicationDeviceType(int i) {
        for (int i2 : VALID_COMMUNICATION_DEVICE_TYPES) {
            if (i == i2) {
                return true;
            }
        }
        return false;
    }

    void postCheckCommunicationDeviceRemoval(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        if (!isValidCommunicationDeviceType(android.media.AudioDeviceInfo.convertInternalDeviceToDeviceType(audioDeviceAttributes.getInternalType()))) {
            return;
        }
        sendLMsgNoDelay(53, 2, audioDeviceAttributes);
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    void onCheckCommunicationDeviceRemoval(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.Iterator<com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient> it = this.mCommunicationRouteClients.iterator();
        while (it.hasNext()) {
            com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient next = it.next();
            if (audioDeviceAttributes.equals(next.getDevice())) {
                postSetCommunicationDeviceForClient(new com.android.server.audio.AudioDeviceBroker.CommunicationDeviceInfo(next.getBinder(), next.getUid(), audioDeviceAttributes, false, -1, "onCheckCommunicationDeviceRemoval", next.isPrivileged()));
            }
        }
    }

    void postCheckCommunicationRouteClientState(int i, boolean z, int i2) {
        com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient communicationRouteClientForUid = getCommunicationRouteClientForUid(i);
        if (communicationRouteClientForUid != null) {
            sendMsgForCheckClientState(56, 0, i, z ? 1 : 0, communicationRouteClientForUid, i2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    void onCheckCommunicationRouteClientState(int i, boolean z) {
        com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient communicationRouteClientForUid = getCommunicationRouteClientForUid(i);
        if (communicationRouteClientForUid == null) {
            return;
        }
        updateCommunicationRouteClientState(communicationRouteClientForUid, z);
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    void updateCommunicationRouteClientState(com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient communicationRouteClient, boolean z) {
        boolean isBluetoothScoRequested = isBluetoothScoRequested();
        communicationRouteClient.setPlaybackActive(this.mAudioService.isPlaybackActiveForUid(communicationRouteClient.getUid()));
        communicationRouteClient.setRecordingActive(this.mAudioService.isRecordingActiveForUid(communicationRouteClient.getUid()));
        if (z != communicationRouteClient.isActive()) {
            postUpdateCommunicationRouteClient(isBluetoothScoRequested, "updateCommunicationRouteClientState");
        }
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    void setForceCommunicationClientStateAndDelayedCheck(com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient communicationRouteClient, boolean z, boolean z2) {
        if (communicationRouteClient == null) {
            return;
        }
        if (z) {
            communicationRouteClient.setPlaybackActive(true);
        }
        if (z2) {
            communicationRouteClient.setRecordingActive(true);
        }
        postCheckCommunicationRouteClientState(communicationRouteClient.getUid(), communicationRouteClient.isActive(), 6000);
    }

    static java.util.List<android.media.AudioDeviceInfo> getAvailableCommunicationDevices() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.media.AudioDeviceInfo audioDeviceInfo : android.media.AudioManager.getDevicesStatic(2)) {
            if (isValidCommunicationDevice(audioDeviceInfo)) {
                arrayList.add(audioDeviceInfo);
            }
        }
        return arrayList;
    }

    @android.annotation.Nullable
    private android.media.AudioDeviceInfo getCommunicationDeviceOfType(final int i) {
        return getAvailableCommunicationDevices().stream().filter(new java.util.function.Predicate() { // from class: com.android.server.audio.AudioDeviceBroker$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getCommunicationDeviceOfType$0;
                lambda$getCommunicationDeviceOfType$0 = com.android.server.audio.AudioDeviceBroker.lambda$getCommunicationDeviceOfType$0(i, (android.media.AudioDeviceInfo) obj);
                return lambda$getCommunicationDeviceOfType$0;
            }
        }).findFirst().orElse(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getCommunicationDeviceOfType$0(int i, android.media.AudioDeviceInfo audioDeviceInfo) {
        return audioDeviceInfo.getType() == i;
    }

    android.media.AudioDeviceInfo getCommunicationDevice() {
        android.media.AudioDeviceInfo communicationDeviceInt;
        synchronized (this.mCommunicationDeviceLock) {
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            long j = 0;
            while (true) {
                if (this.mCommunicationDeviceUpdateCount <= 0) {
                    break;
                }
                try {
                    this.mCommunicationDeviceLock.wait(3000 - j);
                } catch (java.lang.InterruptedException e) {
                    android.util.Log.w(TAG, "Interrupted while waiting for communication device update.");
                }
                j = java.lang.System.currentTimeMillis() - currentTimeMillis;
                if (j >= 3000) {
                    android.util.Log.e(TAG, "Timeout waiting for communication device update.");
                    break;
                }
            }
        }
        synchronized (this.mDeviceStateLock) {
            communicationDeviceInt = getCommunicationDeviceInt();
        }
        return communicationDeviceInt;
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    private android.media.AudioDeviceInfo getCommunicationDeviceInt() {
        updateActiveCommunicationDevice();
        android.media.AudioDeviceInfo audioDeviceInfo = this.mActiveCommunicationDevice;
        if (audioDeviceInfo != null && audioDeviceInfo.getType() == 13) {
            audioDeviceInfo = getCommunicationDeviceOfType(2);
        }
        if (audioDeviceInfo == null || !isValidCommunicationDevice(audioDeviceInfo)) {
            android.media.AudioDeviceInfo communicationDeviceOfType = getCommunicationDeviceOfType(1);
            if (communicationDeviceOfType == null) {
                java.util.List<android.media.AudioDeviceInfo> availableCommunicationDevices = getAvailableCommunicationDevices();
                if (!availableCommunicationDevices.isEmpty()) {
                    return availableCommunicationDevices.get(0);
                }
                return communicationDeviceOfType;
            }
            return communicationDeviceOfType;
        }
        return audioDeviceInfo;
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    void updateActiveCommunicationDevice() {
        android.media.AudioDeviceAttributes preferredCommunicationDevice = preferredCommunicationDevice();
        if (preferredCommunicationDevice == null) {
            java.util.ArrayList<android.media.AudioDeviceAttributes> devicesForAttributes = this.mAudioSystem.getDevicesForAttributes(android.media.audiopolicy.AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(0), false);
            if (devicesForAttributes.isEmpty()) {
                if (this.mAudioService.isPlatformVoice()) {
                    android.util.Log.w(TAG, "updateActiveCommunicationDevice(): no device for phone strategy");
                }
                this.mActiveCommunicationDevice = null;
                return;
            }
            preferredCommunicationDevice = devicesForAttributes.get(0);
        }
        this.mActiveCommunicationDevice = android.media.AudioManager.getDeviceInfoFromTypeAndAddress(preferredCommunicationDevice.getType(), preferredCommunicationDevice.getAddress());
    }

    private boolean isDeviceRequestedForCommunication(int i) {
        boolean z;
        synchronized (this.mDeviceStateLock) {
            try {
                android.media.AudioDeviceAttributes requestedCommunicationDevice = requestedCommunicationDevice();
                z = requestedCommunicationDevice != null && requestedCommunicationDevice.getType() == i;
            } finally {
            }
        }
        return z;
    }

    private boolean isDeviceOnForCommunication(int i) {
        boolean z;
        synchronized (this.mDeviceStateLock) {
            try {
                android.media.AudioDeviceAttributes preferredCommunicationDevice = preferredCommunicationDevice();
                z = preferredCommunicationDevice != null && preferredCommunicationDevice.getType() == i;
            } finally {
            }
        }
        return z;
    }

    private boolean isDeviceActiveForCommunication(int i) {
        return this.mActiveCommunicationDevice != null && this.mActiveCommunicationDevice.getType() == i && this.mPreferredCommunicationDevice != null && this.mPreferredCommunicationDevice.getType() == i;
    }

    private boolean isSpeakerphoneRequested() {
        return isDeviceRequestedForCommunication(2);
    }

    boolean isSpeakerphoneOn() {
        return isDeviceOnForCommunication(2);
    }

    private boolean isSpeakerphoneActive() {
        return isDeviceActiveForCommunication(2);
    }

    boolean isBluetoothScoRequested() {
        return isDeviceRequestedForCommunication(7);
    }

    boolean isBluetoothLeAudioRequested() {
        return isDeviceRequestedForCommunication(26) || isDeviceRequestedForCommunication(27);
    }

    boolean isBluetoothScoOn() {
        return isDeviceOnForCommunication(7);
    }

    boolean isBluetoothScoActive() {
        return isDeviceActiveForCommunication(7);
    }

    boolean isDeviceConnected(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        boolean isDeviceConnected;
        synchronized (this.mDeviceStateLock) {
            isDeviceConnected = this.mDeviceInventory.isDeviceConnected(audioDeviceAttributes);
        }
        return isDeviceConnected;
    }

    void setWiredDeviceConnectionState(android.media.AudioDeviceAttributes audioDeviceAttributes, int i, java.lang.String str) {
        synchronized (this.mDeviceStateLock) {
            this.mDeviceInventory.setWiredDeviceConnectionState(audioDeviceAttributes, i, str);
        }
    }

    void setTestDeviceConnectionState(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, int i) {
        synchronized (this.mDeviceStateLock) {
            this.mDeviceInventory.setTestDeviceConnectionState(audioDeviceAttributes, i);
        }
    }

    static final class BleVolumeInfo {
        final int mIndex;
        final int mMaxIndex;
        final int mStreamType;

        BleVolumeInfo(int i, int i2, int i3) {
            this.mIndex = i;
            this.mMaxIndex = i2;
            this.mStreamType = i3;
        }
    }

    static final class BtDeviceChangedData {

        @android.annotation.NonNull
        final java.lang.String mEventSource;

        @android.annotation.NonNull
        final android.media.BluetoothProfileConnectionInfo mInfo;

        @android.annotation.Nullable
        final android.bluetooth.BluetoothDevice mNewDevice;

        @android.annotation.Nullable
        final android.bluetooth.BluetoothDevice mPreviousDevice;

        BtDeviceChangedData(@android.annotation.Nullable android.bluetooth.BluetoothDevice bluetoothDevice, @android.annotation.Nullable android.bluetooth.BluetoothDevice bluetoothDevice2, @android.annotation.NonNull android.media.BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo, @android.annotation.NonNull java.lang.String str) {
            this.mNewDevice = bluetoothDevice;
            this.mPreviousDevice = bluetoothDevice2;
            this.mInfo = bluetoothProfileConnectionInfo;
            this.mEventSource = str;
        }

        public java.lang.String toString() {
            return "BtDeviceChangedData profile=" + android.bluetooth.BluetoothProfile.getProfileName(this.mInfo.getProfile()) + ", switch device: [" + this.mPreviousDevice + "] -> [" + this.mNewDevice + "]";
        }
    }

    static final class BtDeviceInfo {
        final int mAudioSystemDevice;

        @android.annotation.NonNull
        final android.bluetooth.BluetoothDevice mDevice;

        @android.annotation.NonNull
        final java.lang.String mEventSource;
        final boolean mIsLeOutput;
        final int mMusicDevice;
        final int mProfile;
        final int mState;
        final boolean mSupprNoisy;
        final int mVolume;

        BtDeviceInfo(@android.annotation.NonNull com.android.server.audio.AudioDeviceBroker.BtDeviceChangedData btDeviceChangedData, @android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice, int i, int i2, int i3) {
            this.mDevice = bluetoothDevice;
            this.mState = i;
            this.mProfile = btDeviceChangedData.mInfo.getProfile();
            this.mSupprNoisy = btDeviceChangedData.mInfo.isSuppressNoisyIntent();
            this.mVolume = btDeviceChangedData.mInfo.getVolume();
            this.mIsLeOutput = btDeviceChangedData.mInfo.isLeOutput();
            this.mEventSource = btDeviceChangedData.mEventSource;
            this.mAudioSystemDevice = i2;
            this.mMusicDevice = 0;
        }

        BtDeviceInfo(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice, int i) {
            this.mDevice = bluetoothDevice;
            this.mProfile = i;
            this.mEventSource = "";
            this.mMusicDevice = 0;
            this.mAudioSystemDevice = 0;
            this.mState = 0;
            this.mSupprNoisy = false;
            this.mVolume = -1;
            this.mIsLeOutput = false;
        }

        BtDeviceInfo(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice, int i, int i2, int i3, int i4) {
            this.mDevice = bluetoothDevice;
            this.mProfile = i;
            this.mEventSource = "";
            this.mMusicDevice = i3;
            this.mAudioSystemDevice = i4;
            this.mState = i2;
            this.mSupprNoisy = false;
            this.mVolume = -1;
            this.mIsLeOutput = false;
        }

        BtDeviceInfo(@android.annotation.NonNull com.android.server.audio.AudioDeviceBroker.BtDeviceInfo btDeviceInfo, int i) {
            this.mDevice = btDeviceInfo.mDevice;
            this.mState = i;
            this.mProfile = btDeviceInfo.mProfile;
            this.mSupprNoisy = btDeviceInfo.mSupprNoisy;
            this.mVolume = btDeviceInfo.mVolume;
            this.mIsLeOutput = btDeviceInfo.mIsLeOutput;
            this.mEventSource = btDeviceInfo.mEventSource;
            this.mAudioSystemDevice = btDeviceInfo.mAudioSystemDevice;
            this.mMusicDevice = btDeviceInfo.mMusicDevice;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.audio.AudioDeviceBroker.BtDeviceInfo)) {
                return false;
            }
            com.android.server.audio.AudioDeviceBroker.BtDeviceInfo btDeviceInfo = (com.android.server.audio.AudioDeviceBroker.BtDeviceInfo) obj;
            if (this.mProfile != btDeviceInfo.mProfile || !this.mDevice.equals(btDeviceInfo.mDevice)) {
                return false;
            }
            return true;
        }

        public java.lang.String toString() {
            return "BtDeviceInfo: device=" + this.mDevice.toString() + " state=" + this.mState + " prof=" + this.mProfile + " supprNoisy=" + this.mSupprNoisy + " volume=" + this.mVolume + " isLeOutput=" + this.mIsLeOutput + " eventSource=" + this.mEventSource + " audioSystemDevice=" + this.mAudioSystemDevice + " musicDevice=" + this.mMusicDevice;
        }
    }

    com.android.server.audio.AudioDeviceBroker.BtDeviceInfo createBtDeviceInfo(@android.annotation.NonNull com.android.server.audio.AudioDeviceBroker.BtDeviceChangedData btDeviceChangedData, @android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice, int i) {
        int i2;
        switch (btDeviceChangedData.mInfo.getProfile()) {
            case 2:
                i2 = 128;
                break;
            case 11:
                i2 = -2147352576;
                break;
            case 21:
                i2 = 134217728;
                break;
            case 22:
                if (btDeviceChangedData.mInfo.isLeOutput()) {
                    i2 = 536870912;
                    break;
                } else {
                    i2 = -1610612736;
                    break;
                }
            case 26:
                i2 = 536870914;
                break;
            default:
                throw new java.lang.IllegalArgumentException("Invalid profile " + btDeviceChangedData.mInfo.getProfile());
        }
        return new com.android.server.audio.AudioDeviceBroker.BtDeviceInfo(btDeviceChangedData, bluetoothDevice, i, i2, 0);
    }

    private void btMediaMetricRecord(@android.annotation.NonNull android.bluetooth.BluetoothDevice bluetoothDevice, java.lang.String str, @android.annotation.NonNull com.android.server.audio.AudioDeviceBroker.BtDeviceChangedData btDeviceChangedData) {
        new android.media.MediaMetrics.Item("audio.device.queueOnBluetoothActiveDeviceChanged").set(android.media.MediaMetrics.Property.STATE, str).set(android.media.MediaMetrics.Property.STATUS, java.lang.Integer.valueOf(btDeviceChangedData.mInfo.getProfile())).set(android.media.MediaMetrics.Property.NAME, android.text.TextUtils.emptyIfNull(bluetoothDevice.getName())).record();
    }

    void queueOnBluetoothActiveDeviceChanged(@android.annotation.NonNull com.android.server.audio.AudioDeviceBroker.BtDeviceChangedData btDeviceChangedData) {
        if (btDeviceChangedData.mPreviousDevice != null && btDeviceChangedData.mPreviousDevice.equals(btDeviceChangedData.mNewDevice)) {
            new android.media.MediaMetrics.Item("audio.device.queueOnBluetoothActiveDeviceChanged_update").set(android.media.MediaMetrics.Property.NAME, android.text.TextUtils.emptyIfNull(btDeviceChangedData.mNewDevice.getName())).set(android.media.MediaMetrics.Property.STATUS, java.lang.Integer.valueOf(btDeviceChangedData.mInfo.getProfile())).record();
            synchronized (this.mDeviceStateLock) {
                postBluetoothDeviceConfigChange(createBtDeviceInfo(btDeviceChangedData, btDeviceChangedData.mNewDevice, 2));
            }
            return;
        }
        synchronized (this.mDeviceStateLock) {
            try {
                if (btDeviceChangedData.mPreviousDevice != null) {
                    btMediaMetricRecord(btDeviceChangedData.mPreviousDevice, "disconnected", btDeviceChangedData);
                    sendLMsgNoDelay(45, 2, createBtDeviceInfo(btDeviceChangedData, btDeviceChangedData.mPreviousDevice, 0));
                }
                if (btDeviceChangedData.mNewDevice != null) {
                    btMediaMetricRecord(btDeviceChangedData.mNewDevice, "connected", btDeviceChangedData);
                    sendLMsgNoDelay(45, 2, createBtDeviceInfo(btDeviceChangedData, btDeviceChangedData.mNewDevice, 2));
                }
            } finally {
            }
        }
    }

    private void initAudioHalBluetoothState() {
        synchronized (this.mBluetoothAudioStateLock) {
            this.mBluetoothScoOnApplied = false;
            this.mBluetoothA2dpSuspendedApplied = false;
            this.mBluetoothLeSuspendedApplied = false;
            reapplyAudioHalBluetoothState();
        }
    }

    @com.android.internal.annotations.GuardedBy({"mBluetoothAudioStateLock"})
    private void updateAudioHalBluetoothState() {
        boolean z = true;
        if (this.mBluetoothScoOn != this.mBluetoothScoOnApplied) {
            if (this.mBluetoothScoOn) {
                if (!this.mBluetoothA2dpSuspendedApplied) {
                    android.media.AudioSystem.setParameters("A2dpSuspended=true");
                    this.mBluetoothA2dpSuspendedApplied = true;
                }
                if (!this.mBluetoothLeSuspendedApplied) {
                    android.media.AudioSystem.setParameters("LeAudioSuspended=true");
                    this.mBluetoothLeSuspendedApplied = true;
                }
                android.media.AudioSystem.setParameters("BT_SCO=on");
            } else {
                android.media.AudioSystem.setParameters("BT_SCO=off");
            }
            this.mBluetoothScoOnApplied = this.mBluetoothScoOn;
        }
        if (!this.mBluetoothScoOnApplied) {
            if ((this.mBluetoothA2dpSuspendedExt || this.mBluetoothA2dpSuspendedInt) != this.mBluetoothA2dpSuspendedApplied) {
                this.mBluetoothA2dpSuspendedApplied = this.mBluetoothA2dpSuspendedExt || this.mBluetoothA2dpSuspendedInt;
                if (this.mBluetoothA2dpSuspendedApplied) {
                    android.media.AudioSystem.setParameters("A2dpSuspended=true");
                } else {
                    android.media.AudioSystem.setParameters("A2dpSuspended=false");
                }
            }
            if ((this.mBluetoothLeSuspendedExt || this.mBluetoothLeSuspendedInt) != this.mBluetoothLeSuspendedApplied) {
                if (!this.mBluetoothLeSuspendedExt && !this.mBluetoothLeSuspendedInt) {
                    z = false;
                }
                this.mBluetoothLeSuspendedApplied = z;
                if (this.mBluetoothLeSuspendedApplied) {
                    android.media.AudioSystem.setParameters("LeAudioSuspended=true");
                } else {
                    android.media.AudioSystem.setParameters("LeAudioSuspended=false");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mBluetoothAudioStateLock"})
    public void reapplyAudioHalBluetoothState() {
        if (this.mBluetoothScoOnApplied) {
            android.media.AudioSystem.setParameters("A2dpSuspended=true");
            android.media.AudioSystem.setParameters("LeAudioSuspended=true");
            android.media.AudioSystem.setParameters("BT_SCO=on");
            return;
        }
        android.media.AudioSystem.setParameters("BT_SCO=off");
        if (this.mBluetoothA2dpSuspendedApplied) {
            android.media.AudioSystem.setParameters("A2dpSuspended=true");
        } else {
            android.media.AudioSystem.setParameters("A2dpSuspended=false");
        }
        if (this.mBluetoothLeSuspendedApplied) {
            android.media.AudioSystem.setParameters("LeAudioSuspended=true");
        } else {
            android.media.AudioSystem.setParameters("LeAudioSuspended=false");
        }
    }

    void setBluetoothScoOn(boolean z, java.lang.String str) {
        synchronized (this.mBluetoothAudioStateLock) {
            this.mBluetoothScoOn = z;
            updateAudioHalBluetoothState();
            postUpdateCommunicationRouteClient(isBluetoothScoRequested(), str);
        }
    }

    void setA2dpSuspended(boolean z, boolean z2, java.lang.String str) {
        synchronized (this.mBluetoothAudioStateLock) {
            try {
                if (z2) {
                    this.mBluetoothA2dpSuspendedInt = z;
                } else {
                    this.mBluetoothA2dpSuspendedExt = z;
                }
                updateAudioHalBluetoothState();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void clearA2dpSuspended(boolean z) {
        synchronized (this.mBluetoothAudioStateLock) {
            try {
                this.mBluetoothA2dpSuspendedInt = false;
                if (!z) {
                    this.mBluetoothA2dpSuspendedExt = false;
                }
                updateAudioHalBluetoothState();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void setLeAudioSuspended(boolean z, boolean z2, java.lang.String str) {
        synchronized (this.mBluetoothAudioStateLock) {
            try {
                if (z2) {
                    this.mBluetoothLeSuspendedInt = z;
                } else {
                    this.mBluetoothLeSuspendedExt = z;
                }
                updateAudioHalBluetoothState();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void clearLeAudioSuspended(boolean z) {
        synchronized (this.mBluetoothAudioStateLock) {
            try {
                this.mBluetoothLeSuspendedInt = false;
                if (!z) {
                    this.mBluetoothLeSuspendedExt = false;
                }
                updateAudioHalBluetoothState();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    android.media.AudioRoutesInfo startWatchingRoutes(android.media.IAudioRoutesObserver iAudioRoutesObserver) {
        android.media.AudioRoutesInfo startWatchingRoutes;
        synchronized (this.mDeviceStateLock) {
            startWatchingRoutes = this.mDeviceInventory.startWatchingRoutes(iAudioRoutesObserver);
        }
        return startWatchingRoutes;
    }

    android.media.AudioRoutesInfo getCurAudioRoutes() {
        android.media.AudioRoutesInfo curAudioRoutes;
        synchronized (this.mDeviceStateLock) {
            curAudioRoutes = this.mDeviceInventory.getCurAudioRoutes();
        }
        return curAudioRoutes;
    }

    boolean isAvrcpAbsoluteVolumeSupported() {
        boolean isAvrcpAbsoluteVolumeSupported;
        synchronized (this.mDeviceStateLock) {
            isAvrcpAbsoluteVolumeSupported = this.mBtHelper.isAvrcpAbsoluteVolumeSupported();
        }
        return isAvrcpAbsoluteVolumeSupported;
    }

    boolean isBluetoothA2dpOn() {
        boolean z;
        synchronized (this.mDeviceStateLock) {
            z = this.mBluetoothA2dpEnabled;
        }
        return z;
    }

    void postSetAvrcpAbsoluteVolumeIndex(int i) {
        sendIMsgNoDelay(15, 0, i);
    }

    void postSetHearingAidVolumeIndex(int i, int i2) {
        sendIIMsgNoDelay(14, 0, i, i2);
    }

    void postSetLeAudioVolumeIndex(int i, int i2, int i3) {
        sendLMsgNoDelay(46, 0, new com.android.server.audio.AudioDeviceBroker.BleVolumeInfo(i, i2, i3));
    }

    void postSetModeOwner(int i, int i2, int i3) {
        sendLMsgNoDelay(16, 0, new com.android.server.audio.AudioDeviceBroker.AudioModeInfo(i, i2, i3));
    }

    void postBluetoothDeviceConfigChange(@android.annotation.NonNull com.android.server.audio.AudioDeviceBroker.BtDeviceInfo btDeviceInfo) {
        sendLMsgNoDelay(11, 2, btDeviceInfo);
    }

    void startBluetoothScoForClient(android.os.IBinder iBinder, int i, int i2, boolean z, @android.annotation.NonNull java.lang.String str) {
        postSetCommunicationDeviceForClient(new com.android.server.audio.AudioDeviceBroker.CommunicationDeviceInfo(iBinder, i, new android.media.AudioDeviceAttributes(16, ""), true, i2, str, z));
    }

    void stopBluetoothScoForClient(android.os.IBinder iBinder, int i, boolean z, @android.annotation.NonNull java.lang.String str) {
        postSetCommunicationDeviceForClient(new com.android.server.audio.AudioDeviceBroker.CommunicationDeviceInfo(iBinder, i, new android.media.AudioDeviceAttributes(16, ""), false, -1, str, z));
    }

    int setPreferredDevicesForStrategySync(int i, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        return this.mDeviceInventory.setPreferredDevicesForStrategyAndSave(i, list);
    }

    int removePreferredDevicesForStrategySync(int i) {
        return this.mDeviceInventory.removePreferredDevicesForStrategyAndSave(i);
    }

    int setDeviceAsNonDefaultForStrategySync(int i, @android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        return this.mDeviceInventory.setDeviceAsNonDefaultForStrategyAndSave(i, audioDeviceAttributes);
    }

    int removeDeviceAsNonDefaultForStrategySync(int i, @android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes) {
        return this.mDeviceInventory.removeDeviceAsNonDefaultForStrategyAndSave(i, audioDeviceAttributes);
    }

    void registerStrategyPreferredDevicesDispatcher(@android.annotation.NonNull android.media.IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher, boolean z) {
        this.mDeviceInventory.registerStrategyPreferredDevicesDispatcher(iStrategyPreferredDevicesDispatcher, z);
    }

    void unregisterStrategyPreferredDevicesDispatcher(@android.annotation.NonNull android.media.IStrategyPreferredDevicesDispatcher iStrategyPreferredDevicesDispatcher) {
        this.mDeviceInventory.unregisterStrategyPreferredDevicesDispatcher(iStrategyPreferredDevicesDispatcher);
    }

    void registerStrategyNonDefaultDevicesDispatcher(@android.annotation.NonNull android.media.IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher, boolean z) {
        this.mDeviceInventory.registerStrategyNonDefaultDevicesDispatcher(iStrategyNonDefaultDevicesDispatcher, z);
    }

    void unregisterStrategyNonDefaultDevicesDispatcher(@android.annotation.NonNull android.media.IStrategyNonDefaultDevicesDispatcher iStrategyNonDefaultDevicesDispatcher) {
        this.mDeviceInventory.unregisterStrategyNonDefaultDevicesDispatcher(iStrategyNonDefaultDevicesDispatcher);
    }

    int setPreferredDevicesForCapturePresetSync(int i, @android.annotation.NonNull java.util.List<android.media.AudioDeviceAttributes> list) {
        return this.mDeviceInventory.setPreferredDevicesForCapturePresetAndSave(i, list);
    }

    int clearPreferredDevicesForCapturePresetSync(int i) {
        return this.mDeviceInventory.clearPreferredDevicesForCapturePresetAndSave(i);
    }

    void registerCapturePresetDevicesRoleDispatcher(@android.annotation.NonNull android.media.ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher, boolean z) {
        this.mDeviceInventory.registerCapturePresetDevicesRoleDispatcher(iCapturePresetDevicesRoleDispatcher, z);
    }

    void unregisterCapturePresetDevicesRoleDispatcher(@android.annotation.NonNull android.media.ICapturePresetDevicesRoleDispatcher iCapturePresetDevicesRoleDispatcher) {
        this.mDeviceInventory.unregisterCapturePresetDevicesRoleDispatcher(iCapturePresetDevicesRoleDispatcher);
    }

    java.util.List<android.media.AudioDeviceAttributes> anonymizeAudioDeviceAttributesListUnchecked(java.util.List<android.media.AudioDeviceAttributes> list) {
        return this.mAudioService.anonymizeAudioDeviceAttributesListUnchecked(list);
    }

    void registerCommunicationDeviceDispatcher(@android.annotation.NonNull android.media.ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) {
        this.mCommDevDispatchers.register(iCommunicationDeviceDispatcher);
    }

    void unregisterCommunicationDeviceDispatcher(@android.annotation.NonNull android.media.ICommunicationDeviceDispatcher iCommunicationDeviceDispatcher) {
        this.mCommDevDispatchers.unregister(iCommunicationDeviceDispatcher);
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    private void dispatchCommunicationDevice() {
        android.media.AudioDeviceInfo communicationDeviceInt = getCommunicationDeviceInt();
        int id = communicationDeviceInt != null ? communicationDeviceInt.getId() : 0;
        if (id == this.mCurCommunicationPortId) {
            return;
        }
        this.mCurCommunicationPortId = id;
        int beginBroadcast = this.mCommDevDispatchers.beginBroadcast();
        for (int i = 0; i < beginBroadcast; i++) {
            try {
                this.mCommDevDispatchers.getBroadcastItem(i).dispatchCommunicationDeviceChanged(id);
            } catch (android.os.RemoteException e) {
            }
        }
        this.mCommDevDispatchers.finishBroadcast();
    }

    void postAccessoryPlugMediaUnmute(int i) {
        this.mAudioService.postAccessoryPlugMediaUnmute(i);
    }

    int getVssVolumeForDevice(int i, int i2) {
        return this.mAudioService.getVssVolumeForDevice(i, i2);
    }

    int getMaxVssVolumeForStream(int i) {
        return this.mAudioService.getMaxVssVolumeForStream(i);
    }

    int getDeviceForStream(int i) {
        return this.mAudioService.getDeviceForStream(i);
    }

    void postApplyVolumeOnDevice(int i, int i2, java.lang.String str) {
        this.mAudioService.postApplyVolumeOnDevice(i, i2, str);
    }

    void postSetVolumeIndexOnDevice(int i, int i2, int i3, java.lang.String str) {
        this.mAudioService.postSetVolumeIndexOnDevice(i, i2, i3, str);
    }

    void postObserveDevicesForAllStreams() {
        this.mAudioService.postObserveDevicesForAllStreams();
    }

    boolean isInCommunication() {
        return this.mAudioService.isInCommunication();
    }

    boolean hasMediaDynamicPolicy() {
        return this.mAudioService.hasMediaDynamicPolicy();
    }

    android.content.ContentResolver getContentResolver() {
        return this.mAudioService.getContentResolver();
    }

    void checkMusicActive(int i, java.lang.String str) {
        this.mAudioService.checkMusicActive(i, str);
    }

    void checkVolumeCecOnHdmiConnection(int i, java.lang.String str) {
        this.mAudioService.postCheckVolumeCecOnHdmiConnection(i, str);
    }

    boolean hasAudioFocusUsers() {
        return this.mAudioService.hasAudioFocusUsers();
    }

    void postInitSpatializerHeadTrackingSensors() {
        this.mAudioService.postInitSpatializerHeadTrackingSensors();
    }

    void postBroadcastScoConnectionState(int i) {
        sendIMsgNoDelay(3, 2, i);
    }

    void postBroadcastBecomingNoisy() {
        sendMsgNoDelay(12, 0);
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    void postBluetoothActiveDevice(com.android.server.audio.AudioDeviceBroker.BtDeviceInfo btDeviceInfo, int i) {
        sendLMsg(7, 2, btDeviceInfo, i);
    }

    void postSetWiredDeviceConnectionState(com.android.server.audio.AudioDeviceInventory.WiredDeviceConnectionState wiredDeviceConnectionState, int i) {
        sendLMsg(2, 2, wiredDeviceConnectionState, i);
    }

    void postBtProfileDisconnected(int i) {
        sendIMsgNoDelay(22, 2, i);
    }

    void postBtProfileConnected(int i, android.bluetooth.BluetoothProfile bluetoothProfile) {
        sendILMsgNoDelay(23, 2, i, bluetoothProfile);
    }

    void postCommunicationRouteClientDied(com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient communicationRouteClient) {
        sendLMsgNoDelay(34, 2, communicationRouteClient);
    }

    void postSaveSetPreferredDevicesForStrategy(int i, java.util.List<android.media.AudioDeviceAttributes> list) {
        sendILMsgNoDelay(32, 2, i, list);
    }

    void postSaveRemovePreferredDevicesForStrategy(int i) {
        sendIMsgNoDelay(33, 2, i);
    }

    void postSaveSetDeviceAsNonDefaultForStrategy(int i, android.media.AudioDeviceAttributes audioDeviceAttributes) {
        sendILMsgNoDelay(47, 2, i, audioDeviceAttributes);
    }

    void postSaveRemoveDeviceAsNonDefaultForStrategy(int i, android.media.AudioDeviceAttributes audioDeviceAttributes) {
        sendILMsgNoDelay(48, 2, i, audioDeviceAttributes);
    }

    void postSaveSetPreferredDevicesForCapturePreset(int i, java.util.List<android.media.AudioDeviceAttributes> list) {
        sendILMsgNoDelay(37, 2, i, list);
    }

    void postSaveClearPreferredDevicesForCapturePreset(int i) {
        sendIMsgNoDelay(38, 2, i);
    }

    void postUpdateCommunicationRouteClient(boolean z, java.lang.String str) {
        sendILMsgNoDelay(43, 2, z ? 1 : 0, str);
    }

    void postSetCommunicationDeviceForClient(com.android.server.audio.AudioDeviceBroker.CommunicationDeviceInfo communicationDeviceInfo) {
        sendLMsgNoDelay(42, 2, communicationDeviceInfo);
    }

    void postNotifyPreferredAudioProfileApplied(android.bluetooth.BluetoothDevice bluetoothDevice) {
        sendLMsgNoDelay(52, 2, bluetoothDevice);
    }

    void postReceiveBtEvent(android.content.Intent intent) {
        sendLMsgNoDelay(55, 2, intent);
    }

    void postUpdateLeAudioGroupAddresses(int i) {
        sendIMsgNoDelay(57, 2, i);
    }

    void postSynchronizeAdiDevicesInInventory(com.android.server.audio.AdiDeviceState adiDeviceState) {
        sendLMsgNoDelay(58, 2, adiDeviceState);
    }

    void postUpdatedAdiDeviceState(com.android.server.audio.AdiDeviceState adiDeviceState) {
        sendLMsgNoDelay(59, 2, adiDeviceState);
    }

    static final class CommunicationDeviceInfo {

        @android.annotation.NonNull
        final android.os.IBinder mCb;

        @android.annotation.Nullable
        final android.media.AudioDeviceAttributes mDevice;

        @android.annotation.NonNull
        final java.lang.String mEventSource;
        final boolean mIsPrivileged;
        final boolean mOn;
        final int mScoAudioMode;
        final int mUid;

        CommunicationDeviceInfo(@android.annotation.NonNull android.os.IBinder iBinder, int i, @android.annotation.Nullable android.media.AudioDeviceAttributes audioDeviceAttributes, boolean z, int i2, @android.annotation.NonNull java.lang.String str, boolean z2) {
            this.mCb = iBinder;
            this.mUid = i;
            this.mDevice = audioDeviceAttributes;
            this.mOn = z;
            this.mScoAudioMode = i2;
            this.mIsPrivileged = z2;
            this.mEventSource = str;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof com.android.server.audio.AudioDeviceBroker.CommunicationDeviceInfo)) {
                return false;
            }
            com.android.server.audio.AudioDeviceBroker.CommunicationDeviceInfo communicationDeviceInfo = (com.android.server.audio.AudioDeviceBroker.CommunicationDeviceInfo) obj;
            if (!this.mCb.equals(communicationDeviceInfo.mCb) || this.mUid != communicationDeviceInfo.mUid) {
                return false;
            }
            return true;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("CommunicationDeviceInfo mCb=");
            sb.append(this.mCb.toString());
            sb.append(" mUid=");
            sb.append(this.mUid);
            sb.append(" mDevice=[");
            sb.append(this.mDevice != null ? this.mDevice.toString() : "null");
            sb.append("] mOn=");
            sb.append(this.mOn);
            sb.append(" mScoAudioMode=");
            sb.append(this.mScoAudioMode);
            sb.append(" mIsPrivileged=");
            sb.append(this.mIsPrivileged);
            sb.append(" mEventSource=");
            sb.append(this.mEventSource);
            return sb.toString();
        }
    }

    void setBluetoothA2dpOnInt(boolean z, boolean z2, java.lang.String str) {
        java.lang.String str2 = "setBluetoothA2dpOn(" + z + ") from u/pid:" + android.os.Binder.getCallingUid() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.os.Binder.getCallingPid() + " src:" + str;
        synchronized (this.mDeviceStateLock) {
            this.mBluetoothA2dpEnabled = z;
            this.mBrokerHandler.removeMessages(5);
            onSetForceUse(1, this.mBluetoothA2dpEnabled ? 0 : 10, z2, str2);
        }
    }

    boolean handleDeviceConnection(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, boolean z, @android.annotation.Nullable android.bluetooth.BluetoothDevice bluetoothDevice) {
        boolean handleDeviceConnection;
        synchronized (this.mDeviceStateLock) {
            handleDeviceConnection = this.mDeviceInventory.handleDeviceConnection(audioDeviceAttributes, z, false, bluetoothDevice);
        }
        return handleDeviceConnection;
    }

    void handleFailureToConnectToBtHeadsetService(int i) {
        sendMsg(9, 0, i);
    }

    void handleCancelFailureToConnectToBtHeadsetService() {
        this.mBrokerHandler.removeMessages(9);
    }

    void postReportNewRoutes(boolean z) {
        sendMsgNoDelay(z ? 36 : 13, 1);
    }

    boolean hasScheduledA2dpConnection(android.bluetooth.BluetoothDevice bluetoothDevice) {
        return this.mBrokerHandler.hasEqualMessages(7, new com.android.server.audio.AudioDeviceBroker.BtDeviceInfo(bluetoothDevice, 2));
    }

    void setA2dpTimeout(java.lang.String str, int i, int i2) {
        sendILMsg(10, 2, i, str, i2);
    }

    void setLeAudioTimeout(java.lang.String str, int i, int i2, int i3) {
        sendIILMsg(49, 2, i, i2, str, i3);
    }

    void setAvrcpAbsoluteVolumeSupported(boolean z) {
        synchronized (this.mDeviceStateLock) {
            this.mBtHelper.setAvrcpAbsoluteVolumeSupported(z);
        }
    }

    void clearAvrcpAbsoluteVolumeSupported() {
        setAvrcpAbsoluteVolumeSupported(false);
        this.mAudioService.setAvrcpAbsoluteVolumeSupported(false);
    }

    boolean getBluetoothA2dpEnabled() {
        boolean z;
        synchronized (this.mDeviceStateLock) {
            z = this.mBluetoothA2dpEnabled;
        }
        return z;
    }

    int getLeAudioDeviceGroupId(android.bluetooth.BluetoothDevice bluetoothDevice) {
        return this.mBtHelper.getLeAudioDeviceGroupId(bluetoothDevice);
    }

    java.util.List<android.util.Pair<java.lang.String, java.lang.String>> getLeAudioGroupAddresses(int i) {
        return this.mBtHelper.getLeAudioGroupAddresses(i);
    }

    void broadcastStickyIntentToCurrentProfileGroup(android.content.Intent intent) {
        this.mSystemServer.broadcastStickyIntentToCurrentProfileGroup(intent);
    }

    void dump(final java.io.PrintWriter printWriter, final java.lang.String str) {
        if (this.mBrokerHandler != null) {
            printWriter.println(str + "Message handler (watch for unhandled messages):");
            this.mBrokerHandler.dump(new android.util.PrintWriterPrinter(printWriter), str + "  ");
        } else {
            printWriter.println("Message handler is null");
        }
        this.mDeviceInventory.dump(printWriter, str);
        printWriter.println("\n" + str + "Communication route clients:");
        this.mCommunicationRouteClients.forEach(new java.util.function.Consumer() { // from class: com.android.server.audio.AudioDeviceBroker$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.audio.AudioDeviceBroker.lambda$dump$1(printWriter, str, (com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient) obj);
            }
        });
        printWriter.println("\n" + str + "Computed Preferred communication device: " + preferredCommunicationDevice());
        printWriter.println("\n" + str + "Applied Preferred communication device: " + this.mPreferredCommunicationDevice);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append("Active communication device: ");
        sb.append((java.lang.Object) (this.mActiveCommunicationDevice == null ? com.android.server.input.KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : new android.media.AudioDeviceAttributes(this.mActiveCommunicationDevice)));
        printWriter.println(sb.toString());
        printWriter.println(str + "mCommunicationStrategyId: " + this.mCommunicationStrategyId);
        printWriter.println(str + "mAccessibilityStrategyId: " + this.mAccessibilityStrategyId);
        printWriter.println("\n" + str + "mAudioModeOwner: " + this.mAudioModeOwner);
        this.mBtHelper.dump(printWriter, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$1(java.io.PrintWriter printWriter, java.lang.String str, com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient communicationRouteClient) {
        printWriter.println("  " + str + communicationRouteClient.toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSetForceUse(int i, int i2, boolean z, java.lang.String str) {
        if (i == 1) {
            postReportNewRoutes(z);
        }
        com.android.server.audio.AudioService.sForceUseLogger.enqueue(new com.android.server.audio.AudioServiceEvents.ForceUseEvent(i, i2, str));
        new android.media.MediaMetrics.Item("audio.forceUse." + android.media.AudioSystem.forceUseUsageToString(i)).set(android.media.MediaMetrics.Property.EVENT, "onSetForceUse").set(android.media.MediaMetrics.Property.FORCE_USE_DUE_TO, str).set(android.media.MediaMetrics.Property.FORCE_USE_MODE, android.media.AudioSystem.forceUseConfigToString(i2)).record();
        this.mAudioSystem.setForceUse(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSendBecomingNoisyIntent() {
        com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("broadcast ACTION_AUDIO_BECOMING_NOISY").printLog(TAG));
        this.mSystemServer.sendDeviceBecomingNoisyIntent();
    }

    private void setupMessaging(android.content.Context context) {
        this.mBrokerEventWakeLock = ((android.os.PowerManager) context.getSystemService("power")).newWakeLock(1, "handleAudioDeviceEvent");
        this.mBrokerThread = new com.android.server.audio.AudioDeviceBroker.BrokerThread();
        this.mBrokerThread.start();
        waitForBrokerHandlerCreation();
    }

    private void waitForBrokerHandlerCreation() {
        synchronized (this) {
            while (this.mBrokerHandler == null) {
                try {
                    wait();
                } catch (java.lang.InterruptedException e) {
                    android.util.Log.e(TAG, "Interruption while waiting on BrokerHandler");
                }
            }
        }
    }

    private class BrokerThread extends java.lang.Thread {
        BrokerThread() {
            super("AudioDeviceBroker");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            android.os.Looper.prepare();
            synchronized (com.android.server.audio.AudioDeviceBroker.this) {
                com.android.server.audio.AudioDeviceBroker.this.mBrokerHandler = new com.android.server.audio.AudioDeviceBroker.BrokerHandler();
                com.android.server.audio.AudioDeviceBroker.this.notify();
            }
            android.os.Looper.loop();
        }
    }

    private class BrokerHandler extends android.os.Handler {
        private BrokerHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            int bluetoothContextualVolumeStream;
            switch (message.what) {
                case 1:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mSetModeLock) {
                        synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                            com.android.server.audio.AudioDeviceBroker.this.initRoutingStrategyIds();
                            com.android.server.audio.AudioDeviceBroker.this.updateActiveCommunicationDevice();
                            com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onRestoreDevices();
                            synchronized (com.android.server.audio.AudioDeviceBroker.this.mBluetoothAudioStateLock) {
                                com.android.server.audio.AudioDeviceBroker.this.reapplyAudioHalBluetoothState();
                            }
                            com.android.server.audio.AudioDeviceBroker.this.mBtHelper.onAudioServerDiedRestoreA2dp();
                            com.android.server.audio.AudioDeviceBroker.this.updateCommunicationRoute("MSG_RESTORE_DEVICES");
                        }
                        break;
                    }
                case 2:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                        com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onSetWiredDeviceConnectionState((com.android.server.audio.AudioDeviceInventory.WiredDeviceConnectionState) message.obj);
                    }
                    break;
                case 3:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                        com.android.server.audio.AudioDeviceBroker.this.mBtHelper.onBroadcastScoConnectionState(message.arg1);
                    }
                    break;
                case 4:
                case 5:
                    com.android.server.audio.AudioDeviceBroker.this.onSetForceUse(message.arg1, message.arg2, message.what == 5, (java.lang.String) message.obj);
                    break;
                case 6:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                        com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onToggleHdmi();
                    }
                    break;
                case 7:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mSetModeLock) {
                        synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                            try {
                                com.android.server.audio.AudioDeviceBroker.BtDeviceInfo btDeviceInfo = (com.android.server.audio.AudioDeviceBroker.BtDeviceInfo) message.obj;
                                if (btDeviceInfo.mState == 2 && !com.android.server.audio.AudioDeviceBroker.this.mBtHelper.isProfilePoxyConnected(btDeviceInfo.mProfile)) {
                                    com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("msg: MSG_L_SET_BT_ACTIVE_DEVICE received with null profile proxy: " + btDeviceInfo).printLog(com.android.server.audio.AudioDeviceBroker.TAG));
                                } else {
                                    int codecWithFallback = com.android.server.audio.AudioDeviceBroker.this.mBtHelper.getCodecWithFallback(btDeviceInfo.mDevice, btDeviceInfo.mProfile, btDeviceInfo.mIsLeOutput, "MSG_L_SET_BT_ACTIVE_DEVICE");
                                    com.android.server.audio.AudioDeviceInventory audioDeviceInventory = com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory;
                                    if (btDeviceInfo.mProfile != 22 || btDeviceInfo.mIsLeOutput) {
                                        bluetoothContextualVolumeStream = com.android.server.audio.AudioDeviceBroker.this.mAudioService.getBluetoothContextualVolumeStream();
                                    } else {
                                        bluetoothContextualVolumeStream = -1;
                                    }
                                    audioDeviceInventory.onSetBtActiveDevice(btDeviceInfo, codecWithFallback, bluetoothContextualVolumeStream);
                                    if (btDeviceInfo.mProfile == 22 || btDeviceInfo.mProfile == 21) {
                                        com.android.server.audio.AudioDeviceBroker.this.onUpdateCommunicationRouteClient(com.android.server.audio.AudioDeviceBroker.this.isBluetoothScoRequested(), "setBluetoothActiveDevice");
                                    }
                                }
                            } finally {
                            }
                        }
                        break;
                    }
                    break;
                case 8:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 39:
                case 40:
                case 41:
                case 44:
                case 50:
                case 51:
                default:
                    android.util.Log.wtf(com.android.server.audio.AudioDeviceBroker.TAG, "Invalid message " + message.what);
                    break;
                case 9:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mSetModeLock) {
                        synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                            com.android.server.audio.AudioDeviceBroker.this.mBtHelper.resetBluetoothSco();
                        }
                        break;
                    }
                case 10:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                        com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onMakeA2dpDeviceUnavailableNow((java.lang.String) message.obj, message.arg1);
                    }
                    break;
                case 11:
                    com.android.server.audio.AudioDeviceBroker.BtDeviceInfo btDeviceInfo2 = (com.android.server.audio.AudioDeviceBroker.BtDeviceInfo) message.obj;
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                        com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onBluetoothDeviceConfigChange(btDeviceInfo2, com.android.server.audio.AudioDeviceBroker.this.mBtHelper.getCodecWithFallback(btDeviceInfo2.mDevice, btDeviceInfo2.mProfile, btDeviceInfo2.mIsLeOutput, "MSG_L_BLUETOOTH_DEVICE_CONFIG_CHANGE"), 0);
                    }
                    break;
                case 12:
                    com.android.server.audio.AudioDeviceBroker.this.onSendBecomingNoisyIntent();
                    break;
                case 13:
                case 36:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                        com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onReportNewRoutes();
                    }
                    break;
                case 14:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                        com.android.server.audio.AudioDeviceBroker.this.mBtHelper.setHearingAidVolume(message.arg1, message.arg2, com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.isHearingAidConnected());
                    }
                    break;
                case 15:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                        com.android.server.audio.AudioDeviceBroker.this.mBtHelper.setAvrcpAbsoluteVolumeIndex(message.arg1);
                    }
                    break;
                case 16:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mSetModeLock) {
                        synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                            try {
                                boolean isBluetoothScoRequested = com.android.server.audio.AudioDeviceBroker.this.isBluetoothScoRequested();
                                com.android.server.audio.AudioDeviceBroker.this.mAudioModeOwner = (com.android.server.audio.AudioDeviceBroker.AudioModeInfo) message.obj;
                                if (com.android.server.audio.AudioDeviceBroker.this.mAudioModeOwner.mMode != 1) {
                                    com.android.server.audio.AudioDeviceBroker.this.onUpdateCommunicationRouteClient(isBluetoothScoRequested, "setNewModeOwner");
                                }
                            } finally {
                            }
                        }
                        break;
                    }
                case 22:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mSetModeLock) {
                        synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                            com.android.server.audio.AudioDeviceBroker.this.mBtHelper.onBtProfileDisconnected(message.arg1);
                            com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onBtProfileDisconnected(message.arg1);
                        }
                        break;
                    }
                case 23:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mSetModeLock) {
                        synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                            com.android.server.audio.AudioDeviceBroker.this.mBtHelper.onBtProfileConnected(message.arg1, (android.bluetooth.BluetoothProfile) message.obj);
                        }
                        break;
                    }
                case 32:
                    com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onSaveSetPreferredDevices(message.arg1, (java.util.List) message.obj);
                    break;
                case 33:
                    com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onSaveRemovePreferredDevices(message.arg1);
                    break;
                case 34:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mSetModeLock) {
                        synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                            com.android.server.audio.AudioDeviceBroker.this.onCommunicationRouteClientDied((com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient) message.obj);
                        }
                        break;
                    }
                case 35:
                    com.android.server.audio.AudioDeviceBroker.this.checkMessagesMuteMusic(0);
                    break;
                case 37:
                    com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onSaveSetPreferredDevicesForCapturePreset(message.arg1, (java.util.List) message.obj);
                    break;
                case 38:
                    com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onSaveClearPreferredDevicesForCapturePreset(message.arg1);
                    break;
                case 42:
                    com.android.server.audio.AudioDeviceBroker.CommunicationDeviceInfo communicationDeviceInfo = (com.android.server.audio.AudioDeviceBroker.CommunicationDeviceInfo) message.obj;
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mSetModeLock) {
                        synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                            com.android.server.audio.AudioDeviceBroker.this.onSetCommunicationDeviceForClient(communicationDeviceInfo);
                        }
                    }
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mCommunicationDeviceLock) {
                        try {
                            if (com.android.server.audio.AudioDeviceBroker.this.mCommunicationDeviceUpdateCount > 0) {
                                com.android.server.audio.AudioDeviceBroker.this.mCommunicationDeviceUpdateCount--;
                            } else {
                                android.util.Log.e(com.android.server.audio.AudioDeviceBroker.TAG, "mCommunicationDeviceUpdateCount already 0 in MSG_L_SET_COMMUNICATION_DEVICE_FOR_CLIENT");
                            }
                            com.android.server.audio.AudioDeviceBroker.this.mCommunicationDeviceLock.notify();
                        } finally {
                        }
                    }
                    break;
                case 43:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mSetModeLock) {
                        synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                            com.android.server.audio.AudioDeviceBroker audioDeviceBroker = com.android.server.audio.AudioDeviceBroker.this;
                            if (message.arg1 != 1) {
                                r2 = false;
                            }
                            audioDeviceBroker.onUpdateCommunicationRouteClient(r2, (java.lang.String) message.obj);
                        }
                        break;
                    }
                case 45:
                    com.android.server.audio.AudioDeviceBroker.BtDeviceInfo btDeviceInfo3 = (com.android.server.audio.AudioDeviceBroker.BtDeviceInfo) message.obj;
                    if (btDeviceInfo3.mDevice != null) {
                        com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("msg: MSG_L_BT_ACTIVE_DEVICE_CHANGE_EXT " + btDeviceInfo3).printLog(com.android.server.audio.AudioDeviceBroker.TAG));
                        synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                            com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.setBluetoothActiveDevice(btDeviceInfo3);
                        }
                        break;
                    }
                    break;
                case 46:
                    com.android.server.audio.AudioDeviceBroker.BleVolumeInfo bleVolumeInfo = (com.android.server.audio.AudioDeviceBroker.BleVolumeInfo) message.obj;
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                        com.android.server.audio.AudioDeviceBroker.this.mBtHelper.setLeAudioVolume(bleVolumeInfo.mIndex, bleVolumeInfo.mMaxIndex, bleVolumeInfo.mStreamType);
                    }
                    break;
                case 47:
                    com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onSaveSetDeviceAsNonDefault(message.arg1, (android.media.AudioDeviceAttributes) message.obj);
                    break;
                case 48:
                    com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onSaveRemoveDeviceAsNonDefault(message.arg1, (android.media.AudioDeviceAttributes) message.obj);
                    break;
                case 49:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                        com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onMakeLeAudioDeviceUnavailableNow((java.lang.String) message.obj, message.arg1, message.arg2);
                    }
                    break;
                case 52:
                    com.android.server.audio.BtHelper.onNotifyPreferredAudioProfileApplied((android.bluetooth.BluetoothDevice) message.obj);
                    break;
                case 53:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mSetModeLock) {
                        synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                            com.android.server.audio.AudioDeviceBroker.this.onCheckCommunicationDeviceRemoval((android.media.AudioDeviceAttributes) message.obj);
                        }
                        break;
                    }
                case 54:
                    com.android.server.audio.AudioDeviceBroker.this.onPersistAudioDeviceSettings();
                    break;
                case 55:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mSetModeLock) {
                        synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                            com.android.server.audio.AudioDeviceBroker.this.onReceiveBtEvent((android.content.Intent) message.obj);
                        }
                        break;
                    }
                case 56:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                        com.android.server.audio.AudioDeviceBroker audioDeviceBroker2 = com.android.server.audio.AudioDeviceBroker.this;
                        int i = message.arg1;
                        if (message.arg2 != 1) {
                            r2 = false;
                        }
                        audioDeviceBroker2.onCheckCommunicationRouteClientState(i, r2);
                    }
                    break;
                case 57:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mSetModeLock) {
                        synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                            com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onUpdateLeAudioGroupAddresses(message.arg1);
                        }
                        break;
                    }
                case 58:
                    synchronized (com.android.server.audio.AudioDeviceBroker.this.mSetModeLock) {
                        synchronized (com.android.server.audio.AudioDeviceBroker.this.mDeviceStateLock) {
                            com.android.server.audio.AudioDeviceBroker.this.mDeviceInventory.onSynchronizeAdiDevicesInInventory((com.android.server.audio.AdiDeviceState) message.obj);
                        }
                        break;
                    }
                case 59:
                    com.android.server.audio.AudioDeviceBroker.this.mAudioService.onUpdatedAdiDeviceState((com.android.server.audio.AdiDeviceState) message.obj);
                    break;
            }
            if (com.android.server.audio.AudioDeviceBroker.MESSAGES_MUTE_MUSIC.contains(java.lang.Integer.valueOf(message.what))) {
                com.android.server.audio.AudioDeviceBroker.this.sendMsg(35, 0, 100);
            }
            if (com.android.server.audio.AudioDeviceBroker.isMessageHandledUnderWakelock(message.what)) {
                try {
                    com.android.server.audio.AudioDeviceBroker.this.mBrokerEventWakeLock.release();
                } catch (java.lang.Exception e) {
                    android.util.Log.e(com.android.server.audio.AudioDeviceBroker.TAG, "Exception releasing wakelock", e);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isMessageHandledUnderWakelock(int i) {
        switch (i) {
            case 2:
            case 6:
            case 7:
            case 10:
            case 11:
            case 29:
            case 31:
            case 35:
            case 49:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendMsg(int i, int i2, int i3) {
        sendIILMsg(i, i2, 0, 0, null, i3);
    }

    private void sendILMsg(int i, int i2, int i3, java.lang.Object obj, int i4) {
        sendIILMsg(i, i2, i3, 0, obj, i4);
    }

    private void sendLMsg(int i, int i2, java.lang.Object obj, int i3) {
        sendIILMsg(i, i2, 0, 0, obj, i3);
    }

    private void sendIMsg(int i, int i2, int i3, int i4) {
        sendIILMsg(i, i2, i3, 0, null, i4);
    }

    private void sendMsgNoDelay(int i, int i2) {
        sendIILMsg(i, i2, 0, 0, null, 0);
    }

    private void sendIMsgNoDelay(int i, int i2, int i3) {
        sendIILMsg(i, i2, i3, 0, null, 0);
    }

    private void sendIIMsgNoDelay(int i, int i2, int i3, int i4) {
        sendIILMsg(i, i2, i3, i4, null, 0);
    }

    private void sendILMsgNoDelay(int i, int i2, int i3, java.lang.Object obj) {
        sendIILMsg(i, i2, i3, 0, obj, 0);
    }

    private void sendLMsgNoDelay(int i, int i2, java.lang.Object obj) {
        sendIILMsg(i, i2, 0, 0, obj, 0);
    }

    private void sendIILMsgNoDelay(int i, int i2, int i3, int i4, java.lang.Object obj) {
        sendIILMsg(i, i2, i3, i4, obj, 0);
    }

    private void sendIILMsg(int i, int i2, int i3, int i4, java.lang.Object obj, int i5) {
        if (i2 == 0) {
            this.mBrokerHandler.removeMessages(i);
        } else if (i2 == 1 && this.mBrokerHandler.hasMessages(i)) {
            return;
        }
        if (isMessageHandledUnderWakelock(i)) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    this.mBrokerEventWakeLock.acquire(BROKER_WAKELOCK_TIMEOUT_MS);
                } catch (java.lang.Exception e) {
                    android.util.Log.e(TAG, "Exception acquiring wakelock", e);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
        if (MESSAGES_MUTE_MUSIC.contains(java.lang.Integer.valueOf(i))) {
            checkMessagesMuteMusic(i);
        }
        synchronized (sLastDeviceConnectionMsgTimeLock) {
            try {
                long uptimeMillis = android.os.SystemClock.uptimeMillis() + i5;
                switch (i) {
                    case 2:
                    case 7:
                    case 10:
                    case 11:
                    case 49:
                        if (sLastDeviceConnectMsgTime >= uptimeMillis) {
                            uptimeMillis = sLastDeviceConnectMsgTime + 30;
                        }
                        sLastDeviceConnectMsgTime = uptimeMillis;
                        break;
                }
                this.mBrokerHandler.sendMessageAtTime(this.mBrokerHandler.obtainMessage(i, i3, i4, obj), uptimeMillis);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void removeMsgForCheckClientState(int i) {
        com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient communicationRouteClientForUid = getCommunicationRouteClientForUid(i);
        if (communicationRouteClientForUid != null) {
            this.mBrokerHandler.removeEqualMessages(56, communicationRouteClientForUid);
        }
    }

    private void sendMsgForCheckClientState(int i, int i2, int i3, int i4, java.lang.Object obj, int i5) {
        if (i2 == 0 && obj != null) {
            this.mBrokerHandler.removeEqualMessages(i, obj);
        }
        this.mBrokerHandler.sendMessageAtTime(this.mBrokerHandler.obtainMessage(i, i3, i4, obj), android.os.SystemClock.uptimeMillis() + i5);
    }

    private static <T> boolean hasIntersection(java.util.Set<T> set, java.util.Set<T> set2) {
        java.util.Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            if (set2.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    boolean messageMutesMusic(int i) {
        if (i == 0) {
            return false;
        }
        if ((i == 7 || i == 29 || i == 11) && android.media.AudioSystem.isStreamActive(3, 0) && hasIntersection(com.android.server.audio.AudioDeviceInventory.DEVICE_OVERRIDE_A2DP_ROUTE_ON_PLUG_SET, this.mAudioService.getDeviceSetForStream(3))) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkMessagesMuteMusic(int i) {
        boolean messageMutesMusic = messageMutesMusic(i);
        if (!messageMutesMusic) {
            java.util.Iterator<java.lang.Integer> it = MESSAGES_MUTE_MUSIC.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                int intValue = it.next().intValue();
                if (this.mBrokerHandler.hasMessages(intValue) && messageMutesMusic(intValue)) {
                    messageMutesMusic = true;
                    break;
                }
            }
        }
        if (messageMutesMusic != this.mMusicMuted.getAndSet(messageMutesMusic)) {
            this.mAudioService.setMusicMute(messageMutesMusic);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CommunicationRouteClient implements android.os.IBinder.DeathRecipient {
        private final android.os.IBinder mCb;
        private android.media.AudioDeviceAttributes mDevice;
        private final boolean mIsPrivileged;
        private boolean mPlaybackActive;
        private boolean mRecordingActive;
        private final int mUid;

        CommunicationRouteClient(android.os.IBinder iBinder, int i, android.media.AudioDeviceAttributes audioDeviceAttributes, boolean z) {
            this.mCb = iBinder;
            this.mUid = i;
            this.mDevice = audioDeviceAttributes;
            this.mIsPrivileged = z;
            this.mPlaybackActive = com.android.server.audio.AudioDeviceBroker.this.mAudioService.isPlaybackActiveForUid(i);
            this.mRecordingActive = com.android.server.audio.AudioDeviceBroker.this.mAudioService.isRecordingActiveForUid(i);
        }

        public boolean registerDeathRecipient() {
            try {
                this.mCb.linkToDeath(this, 0);
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Log.w(com.android.server.audio.AudioDeviceBroker.TAG, "CommunicationRouteClient could not link to " + this.mCb + " binder death");
                return false;
            }
        }

        public void unregisterDeathRecipient() {
            try {
                this.mCb.unlinkToDeath(this, 0);
            } catch (java.util.NoSuchElementException e) {
                android.util.Log.w(com.android.server.audio.AudioDeviceBroker.TAG, "CommunicationRouteClient could not unlink to " + this.mCb + " binder death");
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.audio.AudioDeviceBroker.this.postCommunicationRouteClientDied(this);
        }

        android.os.IBinder getBinder() {
            return this.mCb;
        }

        int getUid() {
            return this.mUid;
        }

        boolean isPrivileged() {
            return this.mIsPrivileged;
        }

        android.media.AudioDeviceAttributes getDevice() {
            return this.mDevice;
        }

        public void setPlaybackActive(boolean z) {
            this.mPlaybackActive = z;
        }

        public void setRecordingActive(boolean z) {
            this.mRecordingActive = z;
        }

        public boolean isActive() {
            return this.mIsPrivileged || this.mRecordingActive || this.mPlaybackActive;
        }

        public java.lang.String toString() {
            return "[CommunicationRouteClient: mUid: " + this.mUid + " mDevice: " + this.mDevice.toString() + " mIsPrivileged: " + this.mIsPrivileged + " mPlaybackActive: " + this.mPlaybackActive + " mRecordingActive: " + this.mRecordingActive + "]";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    public void onCommunicationRouteClientDied(com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient communicationRouteClient) {
        if (communicationRouteClient == null) {
            return;
        }
        android.util.Log.w(TAG, "Communication client died");
        setCommunicationRouteForClient(communicationRouteClient.getBinder(), communicationRouteClient.getUid(), null, -1, communicationRouteClient.isPrivileged(), "onCommunicationRouteClientDied");
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    @android.annotation.Nullable
    private android.media.AudioDeviceAttributes preferredCommunicationDevice() {
        boolean z;
        android.media.AudioDeviceAttributes headsetAudioDevice;
        boolean isBluetoothScoOn = this.mBtHelper.isBluetoothScoOn();
        synchronized (this.mBluetoothAudioStateLock) {
            if (isBluetoothScoOn) {
                try {
                    z = this.mBluetoothScoOn;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        if (z && (headsetAudioDevice = this.mBtHelper.getHeadsetAudioDevice()) != null) {
            return headsetAudioDevice;
        }
        android.media.AudioDeviceAttributes requestedCommunicationDevice = requestedCommunicationDevice();
        if (requestedCommunicationDevice == null || requestedCommunicationDevice.getType() == 7) {
            return null;
        }
        return requestedCommunicationDevice;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    public void updateCommunicationRoute(java.lang.String str) {
        android.media.AudioDeviceAttributes preferredCommunicationDevice = preferredCommunicationDevice();
        com.android.server.audio.AudioService.sDeviceLogger.enqueue(new com.android.server.utils.EventLogger.StringEvent("updateCommunicationRoute, preferredCommunicationDevice: " + preferredCommunicationDevice + " eventSource: " + str));
        if (preferredCommunicationDevice == null) {
            android.media.AudioDeviceAttributes defaultCommunicationDevice = getDefaultCommunicationDevice();
            if (defaultCommunicationDevice != null) {
                this.mDeviceInventory.setPreferredDevicesForStrategyInt(this.mCommunicationStrategyId, java.util.Arrays.asList(defaultCommunicationDevice));
                this.mDeviceInventory.setPreferredDevicesForStrategyInt(this.mAccessibilityStrategyId, java.util.Arrays.asList(defaultCommunicationDevice));
            } else {
                this.mDeviceInventory.removePreferredDevicesForStrategyInt(this.mCommunicationStrategyId);
                this.mDeviceInventory.removePreferredDevicesForStrategyInt(this.mAccessibilityStrategyId);
            }
            this.mDeviceInventory.applyConnectedDevicesRoles();
            this.mDeviceInventory.reapplyExternalDevicesRoles();
        } else {
            this.mDeviceInventory.setPreferredDevicesForStrategyInt(this.mCommunicationStrategyId, java.util.Arrays.asList(preferredCommunicationDevice));
            this.mDeviceInventory.setPreferredDevicesForStrategyInt(this.mAccessibilityStrategyId, java.util.Arrays.asList(preferredCommunicationDevice));
        }
        onUpdatePhoneStrategyDevice(preferredCommunicationDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    public void onUpdateCommunicationRouteClient(boolean z, java.lang.String str) {
        com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient communicationRouteClient = topCommunicationRouteClient();
        if (communicationRouteClient != null) {
            setCommunicationRouteForClient(communicationRouteClient.getBinder(), communicationRouteClient.getUid(), communicationRouteClient.getDevice(), -1, communicationRouteClient.isPrivileged(), str);
            return;
        }
        if (!isBluetoothScoRequested() && z) {
            this.mBtHelper.stopBluetoothSco(str);
        }
        updateCommunicationRoute(str);
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    private void onUpdatePhoneStrategyDevice(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        boolean isSpeakerphoneActive = isSpeakerphoneActive();
        this.mPreferredCommunicationDevice = audioDeviceAttributes;
        updateActiveCommunicationDevice();
        if (isSpeakerphoneActive != isSpeakerphoneActive()) {
            try {
                this.mContext.sendBroadcastAsUser(new android.content.Intent("android.media.action.SPEAKERPHONE_STATE_CHANGED").setFlags(1073741824), android.os.UserHandle.ALL);
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "failed to broadcast ACTION_SPEAKERPHONE_STATE_CHANGED: " + e);
            }
        }
        this.mAudioService.postUpdateRingerModeServiceInt();
        dispatchCommunicationDevice();
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    private com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient removeCommunicationRouteClient(android.os.IBinder iBinder, boolean z) {
        java.util.Iterator<com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient> it = this.mCommunicationRouteClients.iterator();
        while (it.hasNext()) {
            com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient next = it.next();
            if (next.getBinder() == iBinder) {
                if (z) {
                    next.unregisterDeathRecipient();
                }
                removeMsgForCheckClientState(next.getUid());
                this.mCommunicationRouteClients.remove(next);
                return next;
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    private com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient addCommunicationRouteClient(android.os.IBinder iBinder, int i, android.media.AudioDeviceAttributes audioDeviceAttributes, boolean z) {
        removeCommunicationRouteClient(iBinder, true);
        com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient communicationRouteClient = new com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient(iBinder, i, audioDeviceAttributes, z);
        if (communicationRouteClient.registerDeathRecipient()) {
            this.mCommunicationRouteClients.add(0, communicationRouteClient);
            if (!communicationRouteClient.isActive()) {
                setForceCommunicationClientStateAndDelayedCheck(communicationRouteClient, !this.mAudioService.isPlaybackActiveForUid(communicationRouteClient.getUid()), !this.mAudioService.isRecordingActiveForUid(communicationRouteClient.getUid()));
            }
            return communicationRouteClient;
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    private com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient getCommunicationRouteClientForUid(int i) {
        java.util.Iterator<com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient> it = this.mCommunicationRouteClients.iterator();
        while (it.hasNext()) {
            com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient next = it.next();
            if (next.getUid() == i) {
                return next;
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    private boolean communnicationDeviceLeAudioCompatOn() {
        return (this.mAudioModeOwner.mMode != 3 || android.app.compat.CompatChanges.isChangeEnabled(USE_SET_COMMUNICATION_DEVICE, this.mAudioModeOwner.mUid) || this.mAudioModeOwner.mUid == 1000) ? false : true;
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    private boolean communnicationDeviceHaCompatOn() {
        return this.mAudioModeOwner.mMode == 3 && this.mAudioModeOwner.mUid != 1000;
    }

    @com.android.internal.annotations.GuardedBy({"mDeviceStateLock"})
    android.media.AudioDeviceAttributes getDefaultCommunicationDevice() {
        android.media.AudioDeviceAttributes audioDeviceAttributes;
        if (!communnicationDeviceHaCompatOn()) {
            audioDeviceAttributes = null;
        } else {
            audioDeviceAttributes = this.mDeviceInventory.getDeviceOfType(134217728);
        }
        if (audioDeviceAttributes == null && communnicationDeviceLeAudioCompatOn()) {
            return this.mDeviceInventory.getDeviceOfType(536870912);
        }
        return audioDeviceAttributes;
    }

    void updateCommunicationRouteClientsActivity(java.util.List<android.media.AudioPlaybackConfiguration> list, java.util.List<android.media.AudioRecordingConfiguration> list2) {
        boolean z;
        synchronized (this.mSetModeLock) {
            synchronized (this.mDeviceStateLock) {
                try {
                    java.util.Iterator<com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient> it = this.mCommunicationRouteClients.iterator();
                    while (it.hasNext()) {
                        com.android.server.audio.AudioDeviceBroker.CommunicationRouteClient next = it.next();
                        boolean isActive = next.isActive();
                        if (list != null) {
                            next.setPlaybackActive(false);
                            for (android.media.AudioPlaybackConfiguration audioPlaybackConfiguration : list) {
                                if (audioPlaybackConfiguration.getClientUid() == next.getUid() && audioPlaybackConfiguration.isActive()) {
                                    next.setPlaybackActive(true);
                                    z = true;
                                    break;
                                }
                            }
                        }
                        z = false;
                        if (list2 != null) {
                            next.setRecordingActive(false);
                            java.util.Iterator<android.media.AudioRecordingConfiguration> it2 = list2.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    break;
                                }
                                android.media.AudioRecordingConfiguration next2 = it2.next();
                                if (next2.getClientUid() == next.getUid() && !next2.isClientSilenced()) {
                                    next.setRecordingActive(true);
                                    z = true;
                                    break;
                                }
                            }
                        }
                        if (z) {
                            removeMsgForCheckClientState(next.getUid());
                            updateCommunicationRouteClientState(next, isActive);
                        } else if (isActive) {
                            setForceCommunicationClientStateAndDelayedCheck(next, list != null, list2 != null);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    java.util.List<java.lang.String> getDeviceIdentityAddresses(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.List<java.lang.String> deviceIdentityAddresses;
        synchronized (this.mDeviceStateLock) {
            deviceIdentityAddresses = this.mDeviceInventory.getDeviceIdentityAddresses(audioDeviceAttributes);
        }
        return deviceIdentityAddresses;
    }

    void dispatchPreferredMixerAttributesChangedCausedByDeviceRemoved(android.media.AudioDeviceInfo audioDeviceInfo) {
        this.mAudioService.dispatchPreferredMixerAttributesChanged(new android.media.AudioAttributes.Builder().setUsage(1).build(), audioDeviceInfo.getId(), null);
    }

    public void postPersistAudioDeviceSettings() {
        sendMsg(54, 0, 1000);
    }

    void onPersistAudioDeviceSettings() {
        java.lang.String deviceSettings = this.mDeviceInventory.getDeviceSettings();
        android.util.Log.v(TAG, "onPersistAudioDeviceSettings AdiDeviceState: " + deviceSettings);
        if (deviceSettings.equals(readDeviceSettings())) {
            return;
        }
        try {
            if (!this.mAudioService.getSettings().putSecureStringForUser(this.mAudioService.getContentResolver(), "audio_device_inventory", deviceSettings, -2)) {
                android.util.Log.e(TAG, "error saving AdiDeviceState: " + deviceSettings);
            }
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.e(TAG, "error saving AdiDeviceState: " + deviceSettings, e);
        }
    }

    private java.lang.String readDeviceSettings() {
        return this.mAudioService.getSettings().getSecureStringForUser(this.mAudioService.getContentResolver(), "audio_device_inventory", -2);
    }

    void onReadAudioDeviceSettings() {
        com.android.server.audio.SettingsAdapter settings = this.mAudioService.getSettings();
        android.content.ContentResolver contentResolver = this.mAudioService.getContentResolver();
        java.lang.String readDeviceSettings = readDeviceSettings();
        if (readDeviceSettings == null) {
            android.util.Log.i(TAG, "reading AdiDeviceState from legacy keyspatial_audio_enabled");
            java.lang.String secureStringForUser = settings.getSecureStringForUser(contentResolver, "spatial_audio_enabled", -2);
            if (secureStringForUser == null) {
                android.util.Log.i(TAG, "no AdiDeviceState stored with legacy key");
            } else if (!secureStringForUser.equals("")) {
                if (!settings.putSecureStringForUser(contentResolver, "spatial_audio_enabled", "", -2)) {
                    android.util.Log.w(TAG, "cannot erase the legacy AdiDeviceState with key spatial_audio_enabled");
                }
                if (!settings.putSecureStringForUser(contentResolver, "audio_device_inventory", secureStringForUser, -2)) {
                    android.util.Log.e(TAG, "error updating the new AdiDeviceState with key audio_device_inventory");
                }
            }
            readDeviceSettings = secureStringForUser;
        }
        if (readDeviceSettings != null && !readDeviceSettings.equals("")) {
            setDeviceSettings(readDeviceSettings);
        }
    }

    void setDeviceSettings(java.lang.String str) {
        this.mDeviceInventory.setDeviceSettings(str);
    }

    java.lang.String getDeviceSettings() {
        return this.mDeviceInventory.getDeviceSettings();
    }

    java.util.Collection<com.android.server.audio.AdiDeviceState> getImmutableDeviceInventory() {
        return this.mDeviceInventory.getImmutableDeviceInventory();
    }

    void addOrUpdateDeviceSAStateInInventory(com.android.server.audio.AdiDeviceState adiDeviceState) {
        this.mDeviceInventory.addOrUpdateDeviceSAStateInInventory(adiDeviceState);
    }

    void addOrUpdateBtAudioDeviceCategoryInInventory(com.android.server.audio.AdiDeviceState adiDeviceState) {
        this.mDeviceInventory.addOrUpdateAudioDeviceCategoryInInventory(adiDeviceState);
    }

    @android.annotation.Nullable
    com.android.server.audio.AdiDeviceState findDeviceStateForAudioDeviceAttributes(android.media.AudioDeviceAttributes audioDeviceAttributes, int i) {
        return this.mDeviceInventory.findDeviceStateForAudioDeviceAttributes(audioDeviceAttributes, i);
    }

    @android.annotation.Nullable
    com.android.server.audio.AdiDeviceState findBtDeviceStateForAddress(java.lang.String str, int i) {
        return this.mDeviceInventory.findBtDeviceStateForAddress(str, i);
    }

    void addAudioDeviceWithCategoryInInventoryIfNeeded(@android.annotation.NonNull java.lang.String str, int i) {
        this.mDeviceInventory.addAudioDeviceWithCategoryInInventoryIfNeeded(str, i);
    }

    int getAndUpdateBtAdiDeviceStateCategoryForAddress(@android.annotation.NonNull java.lang.String str) {
        return this.mDeviceInventory.getAndUpdateBtAdiDeviceStateCategoryForAddress(str);
    }

    boolean isBluetoothAudioDeviceCategoryFixed(@android.annotation.NonNull java.lang.String str) {
        return this.mDeviceInventory.isBluetoothAudioDeviceCategoryFixed(str);
    }

    boolean isSADevice(com.android.server.audio.AdiDeviceState adiDeviceState) {
        return this.mAudioService.isSADevice(adiDeviceState);
    }

    void clearDeviceInventory() {
        this.mDeviceInventory.clearDeviceInventory();
    }
}
