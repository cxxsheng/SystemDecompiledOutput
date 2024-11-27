package android.hardware.hdmi;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class HdmiControlManager {
    public static final java.lang.String ACTION_OSD_MESSAGE = "android.hardware.hdmi.action.OSD_MESSAGE";
    public static final int AVR_VOLUME_MUTED = 101;
    public static final java.lang.String CEC_SETTING_NAME_HDMI_CEC_ENABLED = "hdmi_cec_enabled";
    public static final java.lang.String CEC_SETTING_NAME_HDMI_CEC_VERSION = "hdmi_cec_version";
    public static final java.lang.String CEC_SETTING_NAME_POWER_CONTROL_MODE = "power_control_mode";
    public static final java.lang.String CEC_SETTING_NAME_POWER_STATE_CHANGE_ON_ACTIVE_SOURCE_LOST = "power_state_change_on_active_source_lost";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_AAC = "query_sad_aac";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_ATRAC = "query_sad_atrac";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_DD = "query_sad_dd";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_DDP = "query_sad_ddp";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_DST = "query_sad_dst";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_DTS = "query_sad_dts";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_DTSHD = "query_sad_dtshd";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_LPCM = "query_sad_lpcm";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_MAX = "query_sad_max";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_MP3 = "query_sad_mp3";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_MPEG1 = "query_sad_mpeg1";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_MPEG2 = "query_sad_mpeg2";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_ONEBITAUDIO = "query_sad_onebitaudio";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_TRUEHD = "query_sad_truehd";
    public static final java.lang.String CEC_SETTING_NAME_QUERY_SAD_WMAPRO = "query_sad_wmapro";
    public static final java.lang.String CEC_SETTING_NAME_RC_PROFILE_SOURCE_HANDLES_CONTENTS_MENU = "rc_profile_source_handles_contents_menu";
    public static final java.lang.String CEC_SETTING_NAME_RC_PROFILE_SOURCE_HANDLES_MEDIA_CONTEXT_SENSITIVE_MENU = "rc_profile_source_handles_media_context_sensitive_menu";
    public static final java.lang.String CEC_SETTING_NAME_RC_PROFILE_SOURCE_HANDLES_ROOT_MENU = "rc_profile_source_handles_root_menu";
    public static final java.lang.String CEC_SETTING_NAME_RC_PROFILE_SOURCE_HANDLES_SETUP_MENU = "rc_profile_source_handles_setup_menu";
    public static final java.lang.String CEC_SETTING_NAME_RC_PROFILE_SOURCE_HANDLES_TOP_MENU = "rc_profile_source_handles_top_menu";
    public static final java.lang.String CEC_SETTING_NAME_RC_PROFILE_TV = "rc_profile_tv";
    public static final java.lang.String CEC_SETTING_NAME_ROUTING_CONTROL = "routing_control";
    public static final java.lang.String CEC_SETTING_NAME_SET_MENU_LANGUAGE = "set_menu_language";
    public static final java.lang.String CEC_SETTING_NAME_SOUNDBAR_MODE = "soundbar_mode";
    public static final java.lang.String CEC_SETTING_NAME_SYSTEM_AUDIO_CONTROL = "system_audio_control";
    public static final java.lang.String CEC_SETTING_NAME_SYSTEM_AUDIO_MODE_MUTING = "system_audio_mode_muting";
    public static final java.lang.String CEC_SETTING_NAME_TV_SEND_STANDBY_ON_SLEEP = "tv_send_standby_on_sleep";
    public static final java.lang.String CEC_SETTING_NAME_TV_WAKE_ON_ONE_TOUCH_PLAY = "tv_wake_on_one_touch_play";
    public static final java.lang.String CEC_SETTING_NAME_VOLUME_CONTROL_MODE = "volume_control_enabled";
    public static final int CLEAR_TIMER_STATUS_CEC_DISABLE = 162;
    public static final int CLEAR_TIMER_STATUS_CHECK_RECORDER_CONNECTION = 160;
    public static final int CLEAR_TIMER_STATUS_FAIL_TO_CLEAR_SELECTED_SOURCE = 161;
    public static final int CLEAR_TIMER_STATUS_TIMER_CLEARED = 128;
    public static final int CLEAR_TIMER_STATUS_TIMER_NOT_CLEARED_NO_INFO_AVAILABLE = 2;
    public static final int CLEAR_TIMER_STATUS_TIMER_NOT_CLEARED_NO_MATCHING = 1;
    public static final int CLEAR_TIMER_STATUS_TIMER_NOT_CLEARED_RECORDING = 0;
    public static final int CONTROL_STATE_CHANGED_REASON_SETTING = 1;
    public static final int CONTROL_STATE_CHANGED_REASON_STANDBY = 3;
    public static final int CONTROL_STATE_CHANGED_REASON_START = 0;
    public static final int CONTROL_STATE_CHANGED_REASON_WAKEUP = 2;
    public static final int DEVICE_EVENT_ADD_DEVICE = 1;
    public static final int DEVICE_EVENT_REMOVE_DEVICE = 2;
    public static final int DEVICE_EVENT_UPDATE_DEVICE = 3;
    public static final int EARC_FEATURE_DISABLED = 0;
    public static final int EARC_FEATURE_ENABLED = 1;
    public static final java.lang.String EXTRA_LOCALE = "android.hardware.hdmi.extra.LOCALE";
    public static final java.lang.String EXTRA_MESSAGE_EXTRA_PARAM1 = "android.hardware.hdmi.extra.MESSAGE_EXTRA_PARAM1";
    public static final java.lang.String EXTRA_MESSAGE_ID = "android.hardware.hdmi.extra.MESSAGE_ID";
    public static final int HDMI_CEC_CONTROL_DISABLED = 0;
    public static final int HDMI_CEC_CONTROL_ENABLED = 1;
    public static final int HDMI_CEC_VERSION_1_4_B = 5;
    public static final int HDMI_CEC_VERSION_2_0 = 6;
    private static final int INVALID_PHYSICAL_ADDRESS = 65535;
    public static final int ONE_TOUCH_RECORD_ALREADY_RECORDING = 18;
    public static final int ONE_TOUCH_RECORD_CEC_DISABLED = 51;
    public static final int ONE_TOUCH_RECORD_CHECK_RECORDER_CONNECTION = 49;
    public static final int ONE_TOUCH_RECORD_DISALLOW_TO_COPY = 13;
    public static final int ONE_TOUCH_RECORD_DISALLOW_TO_FUTHER_COPIES = 14;
    public static final int ONE_TOUCH_RECORD_FAIL_TO_RECORD_DISPLAYED_SCREEN = 50;
    public static final int ONE_TOUCH_RECORD_INVALID_EXTERNAL_PHYSICAL_ADDRESS = 10;
    public static final int ONE_TOUCH_RECORD_INVALID_EXTERNAL_PLUG_NUMBER = 9;
    public static final int ONE_TOUCH_RECORD_MEDIA_PROBLEM = 21;
    public static final int ONE_TOUCH_RECORD_MEDIA_PROTECTED = 19;
    public static final int ONE_TOUCH_RECORD_NOT_ENOUGH_SPACE = 22;
    public static final int ONE_TOUCH_RECORD_NO_MEDIA = 16;
    public static final int ONE_TOUCH_RECORD_NO_OR_INSUFFICIENT_CA_ENTITLEMENTS = 12;
    public static final int ONE_TOUCH_RECORD_NO_SOURCE_SIGNAL = 20;
    public static final int ONE_TOUCH_RECORD_OTHER_REASON = 31;
    public static final int ONE_TOUCH_RECORD_PARENT_LOCK_ON = 23;
    public static final int ONE_TOUCH_RECORD_PLAYING = 17;
    public static final int ONE_TOUCH_RECORD_PREVIOUS_RECORDING_IN_PROGRESS = 48;
    public static final int ONE_TOUCH_RECORD_RECORDING_ALREADY_TERMINATED = 27;
    public static final int ONE_TOUCH_RECORD_RECORDING_ANALOGUE_SERVICE = 3;
    public static final int ONE_TOUCH_RECORD_RECORDING_CURRENTLY_SELECTED_SOURCE = 1;
    public static final int ONE_TOUCH_RECORD_RECORDING_DIGITAL_SERVICE = 2;
    public static final int ONE_TOUCH_RECORD_RECORDING_EXTERNAL_INPUT = 4;
    public static final int ONE_TOUCH_RECORD_RECORDING_TERMINATED_NORMALLY = 26;
    public static final int ONE_TOUCH_RECORD_UNABLE_ANALOGUE_SERVICE = 6;
    public static final int ONE_TOUCH_RECORD_UNABLE_DIGITAL_SERVICE = 5;
    public static final int ONE_TOUCH_RECORD_UNABLE_SELECTED_SERVICE = 7;
    public static final int ONE_TOUCH_RECORD_UNSUPPORTED_CA = 11;
    public static final int OSD_MESSAGE_ARC_CONNECTED_INVALID_PORT = 1;
    public static final int OSD_MESSAGE_AVR_VOLUME_CHANGED = 2;
    public static final java.lang.String POWER_CONTROL_MODE_BROADCAST = "broadcast";
    public static final java.lang.String POWER_CONTROL_MODE_NONE = "none";
    public static final java.lang.String POWER_CONTROL_MODE_TV = "to_tv";
    public static final java.lang.String POWER_CONTROL_MODE_TV_AND_AUDIO_SYSTEM = "to_tv_and_audio_system";
    public static final java.lang.String POWER_STATE_CHANGE_ON_ACTIVE_SOURCE_LOST_NONE = "none";
    public static final java.lang.String POWER_STATE_CHANGE_ON_ACTIVE_SOURCE_LOST_STANDBY_NOW = "standby_now";
    public static final int POWER_STATUS_ON = 0;
    public static final int POWER_STATUS_STANDBY = 1;
    public static final int POWER_STATUS_TRANSIENT_TO_ON = 2;
    public static final int POWER_STATUS_TRANSIENT_TO_STANDBY = 3;
    public static final int POWER_STATUS_UNKNOWN = -1;
    public static final int QUERY_SAD_DISABLED = 0;
    public static final int QUERY_SAD_ENABLED = 1;
    public static final int RC_PROFILE_SOURCE_MENU_HANDLED = 1;
    public static final int RC_PROFILE_SOURCE_MENU_NOT_HANDLED = 0;
    public static final int RC_PROFILE_TV_FOUR = 14;
    public static final int RC_PROFILE_TV_NONE = 0;
    public static final int RC_PROFILE_TV_ONE = 2;
    public static final int RC_PROFILE_TV_THREE = 10;
    public static final int RC_PROFILE_TV_TWO = 6;

    @java.lang.Deprecated
    public static final int RESULT_ALREADY_IN_PROGRESS = 4;
    public static final int RESULT_COMMUNICATION_FAILED = 7;
    public static final int RESULT_EXCEPTION = 5;
    public static final int RESULT_INCORRECT_MODE = 6;
    public static final int RESULT_SOURCE_NOT_AVAILABLE = 2;
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_TARGET_NOT_AVAILABLE = 3;
    public static final int RESULT_TIMEOUT = 1;
    public static final int ROUTING_CONTROL_DISABLED = 0;
    public static final int ROUTING_CONTROL_ENABLED = 1;
    public static final java.lang.String SETTING_NAME_EARC_ENABLED = "earc_enabled";
    public static final int SET_MENU_LANGUAGE_DISABLED = 0;
    public static final int SET_MENU_LANGUAGE_ENABLED = 1;
    public static final int SOUNDBAR_MODE_DISABLED = 0;
    public static final int SOUNDBAR_MODE_ENABLED = 1;
    public static final int SYSTEM_AUDIO_CONTROL_DISABLED = 0;
    public static final int SYSTEM_AUDIO_CONTROL_ENABLED = 1;
    public static final int SYSTEM_AUDIO_MODE_MUTING_DISABLED = 0;
    public static final int SYSTEM_AUDIO_MODE_MUTING_ENABLED = 1;
    private static final java.lang.String TAG = "HdmiControlManager";
    public static final int TIMER_RECORDING_RESULT_EXTRA_CEC_DISABLED = 3;
    public static final int TIMER_RECORDING_RESULT_EXTRA_CHECK_RECORDER_CONNECTION = 1;
    public static final int TIMER_RECORDING_RESULT_EXTRA_FAIL_TO_RECORD_SELECTED_SOURCE = 2;
    public static final int TIMER_RECORDING_RESULT_EXTRA_NO_ERROR = 0;
    public static final int TIMER_RECORDING_TYPE_ANALOGUE = 2;
    public static final int TIMER_RECORDING_TYPE_DIGITAL = 1;
    public static final int TIMER_RECORDING_TYPE_EXTERNAL = 3;
    public static final int TIMER_STATUS_MEDIA_INFO_NOT_PRESENT = 2;
    public static final int TIMER_STATUS_MEDIA_INFO_PRESENT_NOT_PROTECTED = 0;
    public static final int TIMER_STATUS_MEDIA_INFO_PRESENT_PROTECTED = 1;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_CA_NOT_SUPPORTED = 6;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_CLOCK_FAILURE = 10;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_DATE_OUT_OF_RANGE = 2;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_DUPLICATED = 14;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_INVALID_EXTERNAL_PHYSICAL_NUMBER = 5;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_INVALID_EXTERNAL_PLUG_NUMBER = 4;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_INVALID_SEQUENCE = 3;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_NO_CA_ENTITLEMENTS = 7;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_NO_FREE_TIME = 1;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_PARENTAL_LOCK_ON = 9;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_UNSUPPORTED_RESOLUTION = 8;
    public static final int TIMER_STATUS_PROGRAMMED_INFO_ENOUGH_SPACE = 8;
    public static final int TIMER_STATUS_PROGRAMMED_INFO_MIGHT_NOT_ENOUGH_SPACE = 11;
    public static final int TIMER_STATUS_PROGRAMMED_INFO_NOT_ENOUGH_SPACE = 9;
    public static final int TIMER_STATUS_PROGRAMMED_INFO_NO_MEDIA_INFO = 10;
    public static final int TV_SEND_STANDBY_ON_SLEEP_DISABLED = 0;
    public static final int TV_SEND_STANDBY_ON_SLEEP_ENABLED = 1;
    public static final int TV_WAKE_ON_ONE_TOUCH_PLAY_DISABLED = 0;
    public static final int TV_WAKE_ON_ONE_TOUCH_PLAY_ENABLED = 1;
    public static final int VOLUME_CONTROL_DISABLED = 0;
    public static final int VOLUME_CONTROL_ENABLED = 1;
    private final boolean mHasAudioSystemDevice;
    private final boolean mHasPlaybackDevice;
    private final boolean mHasSwitchDevice;
    private final boolean mHasTvDevice;
    private final boolean mIsSwitchDevice;
    private final android.hardware.hdmi.IHdmiControlService mService;
    private int mLocalPhysicalAddress = 65535;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.ArrayMap<android.hardware.hdmi.HdmiControlManager.HotplugEventListener, android.hardware.hdmi.IHdmiHotplugEventListener> mHotplugEventListeners = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.hardware.hdmi.HdmiControlManager.HdmiControlStatusChangeListener, android.hardware.hdmi.IHdmiControlStatusChangeListener> mHdmiControlStatusChangeListeners = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.hardware.hdmi.HdmiControlManager.HdmiCecVolumeControlFeatureListener, android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener> mHdmiCecVolumeControlFeatureListeners = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<android.hardware.hdmi.HdmiControlManager.CecSettingChangeListener, android.hardware.hdmi.IHdmiCecSettingChangeListener>> mCecSettingChangeListeners = new android.util.ArrayMap<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ActiveSourceLostBehavior {
    }

    public interface CecSettingChangeListener {
        void onChange(java.lang.String str);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CecSettingSad {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ControlCallbackResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EarcFeature {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HdmiCecControl {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HdmiCecVersion {
    }

    public interface HdmiCecVolumeControlFeatureListener {
        void onHdmiCecVolumeControlFeature(int i);
    }

    public interface HdmiControlStatusChangeListener {
        void onStatusChange(int i, boolean z);
    }

    public interface HotplugEventListener {
        void onReceived(android.hardware.hdmi.HdmiHotplugEvent hdmiHotplugEvent);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PowerControlMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RcProfileSourceHandlesMenu {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RcProfileTv {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RoutingControl {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SadPresenceInQuery {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SetMenuLanguage {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SettingName {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SoundbarMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SystemAudioControl {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SystemAudioModeMuting {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TvSendStandbyOnSleep {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TvWakeOnOneTouchPlay {
    }

    public interface VendorCommandListener {
        void onControlStateChanged(boolean z, int i);

        void onReceived(int i, int i2, byte[] bArr, boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VolumeControl {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setLocalPhysicalAddress(int i) {
        synchronized (this.mLock) {
            this.mLocalPhysicalAddress = i;
        }
    }

    private int getLocalPhysicalAddress() {
        int i;
        synchronized (this.mLock) {
            i = this.mLocalPhysicalAddress;
        }
        return i;
    }

    public HdmiControlManager(android.hardware.hdmi.IHdmiControlService iHdmiControlService) {
        int[] supportedTypes;
        this.mService = iHdmiControlService;
        if (this.mService == null) {
            supportedTypes = null;
        } else {
            try {
                supportedTypes = this.mService.getSupportedTypes();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        this.mHasTvDevice = hasDeviceType(supportedTypes, 0);
        this.mHasPlaybackDevice = hasDeviceType(supportedTypes, 4);
        this.mHasAudioSystemDevice = hasDeviceType(supportedTypes, 5);
        this.mHasSwitchDevice = hasDeviceType(supportedTypes, 6);
        this.mIsSwitchDevice = android.sysprop.HdmiProperties.is_switch().orElse(false).booleanValue();
        addHotplugEventListener(new android.hardware.hdmi.HdmiControlManager.ClientHotplugEventListener());
    }

    private final class ClientHotplugEventListener implements android.hardware.hdmi.HdmiControlManager.HotplugEventListener {
        private ClientHotplugEventListener() {
        }

        @Override // android.hardware.hdmi.HdmiControlManager.HotplugEventListener
        public void onReceived(android.hardware.hdmi.HdmiHotplugEvent hdmiHotplugEvent) {
            int i;
            new java.util.ArrayList();
            try {
                java.util.List<android.hardware.hdmi.HdmiPortInfo> portInfo = android.hardware.hdmi.HdmiControlManager.this.mService.getPortInfo();
                if (portInfo.isEmpty()) {
                    android.util.Log.e(android.hardware.hdmi.HdmiControlManager.TAG, "Can't find port info, not updating connected status. Hotplug event:" + hdmiHotplugEvent);
                    return;
                }
                for (android.hardware.hdmi.HdmiPortInfo hdmiPortInfo : portInfo) {
                    if (hdmiPortInfo.getId() == hdmiHotplugEvent.getPort()) {
                        if (hdmiPortInfo.getType() == 1) {
                            android.hardware.hdmi.HdmiControlManager hdmiControlManager = android.hardware.hdmi.HdmiControlManager.this;
                            if (hdmiHotplugEvent.isConnected()) {
                                i = hdmiPortInfo.getAddress();
                            } else {
                                i = 65535;
                            }
                            hdmiControlManager.setLocalPhysicalAddress(i);
                            return;
                        }
                        return;
                    }
                }
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private static boolean hasDeviceType(int[] iArr, int i) {
        if (iArr == null) {
            return false;
        }
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public android.hardware.hdmi.HdmiClient getClient(int i) {
        if (this.mService == null) {
            return null;
        }
        switch (i) {
            case 0:
                if (this.mHasTvDevice) {
                    return new android.hardware.hdmi.HdmiTvClient(this.mService);
                }
                return null;
            case 1:
            case 2:
            case 3:
            default:
                return null;
            case 4:
                if (this.mHasPlaybackDevice) {
                    return new android.hardware.hdmi.HdmiPlaybackClient(this.mService);
                }
                return null;
            case 5:
                try {
                    if ((this.mService.getCecSettingIntValue(CEC_SETTING_NAME_SOUNDBAR_MODE) == 1 && this.mHasPlaybackDevice) || this.mHasAudioSystemDevice) {
                        return new android.hardware.hdmi.HdmiAudioSystemClient(this.mService);
                    }
                    return null;
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            case 6:
                if (this.mHasSwitchDevice || this.mIsSwitchDevice) {
                    return new android.hardware.hdmi.HdmiSwitchClient(this.mService);
                }
                return null;
        }
    }

    public android.hardware.hdmi.HdmiPlaybackClient getPlaybackClient() {
        return (android.hardware.hdmi.HdmiPlaybackClient) getClient(4);
    }

    public android.hardware.hdmi.HdmiTvClient getTvClient() {
        return (android.hardware.hdmi.HdmiTvClient) getClient(0);
    }

    public android.hardware.hdmi.HdmiAudioSystemClient getAudioSystemClient() {
        return (android.hardware.hdmi.HdmiAudioSystemClient) getClient(5);
    }

    public android.hardware.hdmi.HdmiSwitchClient getSwitchClient() {
        return (android.hardware.hdmi.HdmiSwitchClient) getClient(6);
    }

    public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getConnectedDevices() {
        try {
            return this.mService.getDeviceList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public java.util.List<android.hardware.hdmi.HdmiDeviceInfo> getConnectedDevicesList() {
        try {
            return this.mService.getDeviceList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.hardware.hdmi.HdmiPortInfo> getPortInfo() {
        try {
            return this.mService.getPortInfo();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void powerOffDevice(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        java.util.Objects.requireNonNull(hdmiDeviceInfo);
        try {
            this.mService.powerOffRemoteDevice(hdmiDeviceInfo.getLogicalAddress(), hdmiDeviceInfo.getDevicePowerStatus());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void powerOffRemoteDevice(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        java.util.Objects.requireNonNull(hdmiDeviceInfo);
        try {
            this.mService.powerOffRemoteDevice(hdmiDeviceInfo.getLogicalAddress(), hdmiDeviceInfo.getDevicePowerStatus());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void powerOnDevice(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        java.util.Objects.requireNonNull(hdmiDeviceInfo);
        try {
            this.mService.powerOnRemoteDevice(hdmiDeviceInfo.getLogicalAddress(), hdmiDeviceInfo.getDevicePowerStatus());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void powerOnRemoteDevice(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        java.util.Objects.requireNonNull(hdmiDeviceInfo);
        try {
            this.mService.powerOnRemoteDevice(hdmiDeviceInfo.getLogicalAddress(), hdmiDeviceInfo.getDevicePowerStatus());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setActiveSource(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        java.util.Objects.requireNonNull(hdmiDeviceInfo);
        try {
            this.mService.askRemoteDeviceToBecomeActiveSource(hdmiDeviceInfo.getPhysicalAddress());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void requestRemoteDeviceToBecomeActiveSource(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        java.util.Objects.requireNonNull(hdmiDeviceInfo);
        try {
            this.mService.askRemoteDeviceToBecomeActiveSource(hdmiDeviceInfo.getPhysicalAddress());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setStandbyMode(boolean z) {
        try {
            this.mService.setStandbyMode(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void toggleAndFollowTvPower() {
        try {
            this.mService.toggleAndFollowTvPower();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldHandleTvPowerKey() {
        try {
            return this.mService.shouldHandleTvPowerKey();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setHdmiCecVolumeControlEnabled(int i) {
        try {
            this.mService.setCecSettingIntValue(CEC_SETTING_NAME_VOLUME_CONTROL_MODE, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getHdmiCecVolumeControlEnabled() {
        try {
            return this.mService.getCecSettingIntValue(CEC_SETTING_NAME_VOLUME_CONTROL_MODE);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean getSystemAudioMode() {
        try {
            return this.mService.getSystemAudioMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getPhysicalAddress() {
        return getLocalPhysicalAddress();
    }

    public boolean isDeviceConnected(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        int physicalAddress;
        java.util.Objects.requireNonNull(hdmiDeviceInfo);
        int localPhysicalAddress = getLocalPhysicalAddress();
        return (localPhysicalAddress == 65535 || (physicalAddress = hdmiDeviceInfo.getPhysicalAddress()) == 65535 || android.hardware.hdmi.HdmiUtils.getLocalPortFromPhysicalAddress(physicalAddress, localPhysicalAddress) == -1) ? false : true;
    }

    @java.lang.Deprecated
    public boolean isRemoteDeviceConnected(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
        int physicalAddress;
        java.util.Objects.requireNonNull(hdmiDeviceInfo);
        int localPhysicalAddress = getLocalPhysicalAddress();
        return (localPhysicalAddress == 65535 || (physicalAddress = hdmiDeviceInfo.getPhysicalAddress()) == 65535 || android.hardware.hdmi.HdmiUtils.getLocalPortFromPhysicalAddress(physicalAddress, localPhysicalAddress) == -1) ? false : true;
    }

    public void addHotplugEventListener(android.hardware.hdmi.HdmiControlManager.HotplugEventListener hotplugEventListener) {
        addHotplugEventListener(com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, hotplugEventListener);
    }

    public void addHotplugEventListener(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiControlManager.HotplugEventListener hotplugEventListener) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "addHotplugEventListener: HdmiControlService is not available");
            return;
        }
        if (this.mHotplugEventListeners.containsKey(hotplugEventListener)) {
            android.util.Log.e(TAG, "listener is already registered");
            return;
        }
        android.hardware.hdmi.IHdmiHotplugEventListener hotplugEventListenerWrapper = getHotplugEventListenerWrapper(executor, hotplugEventListener);
        this.mHotplugEventListeners.put(hotplugEventListener, hotplugEventListenerWrapper);
        try {
            this.mService.addHotplugEventListener(hotplugEventListenerWrapper);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeHotplugEventListener(android.hardware.hdmi.HdmiControlManager.HotplugEventListener hotplugEventListener) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "removeHotplugEventListener: HdmiControlService is not available");
            return;
        }
        android.hardware.hdmi.IHdmiHotplugEventListener remove = this.mHotplugEventListeners.remove(hotplugEventListener);
        if (remove == null) {
            android.util.Log.e(TAG, "tried to remove not-registered listener");
            return;
        }
        try {
            this.mService.removeHotplugEventListener(remove);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.hardware.hdmi.HdmiControlManager$1, reason: invalid class name */
    class AnonymousClass1 extends android.hardware.hdmi.IHdmiHotplugEventListener.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.hardware.hdmi.HdmiControlManager.HotplugEventListener val$listener;

        AnonymousClass1(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiControlManager.HotplugEventListener hotplugEventListener) {
            this.val$executor = executor;
            this.val$listener = hotplugEventListener;
        }

        @Override // android.hardware.hdmi.IHdmiHotplugEventListener
        public void onReceived(final android.hardware.hdmi.HdmiHotplugEvent hdmiHotplugEvent) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.hardware.hdmi.HdmiControlManager.HotplugEventListener hotplugEventListener = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.hardware.hdmi.HdmiControlManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.hdmi.HdmiControlManager.HotplugEventListener.this.onReceived(hdmiHotplugEvent);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private android.hardware.hdmi.IHdmiHotplugEventListener getHotplugEventListenerWrapper(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiControlManager.HotplugEventListener hotplugEventListener) {
        return new android.hardware.hdmi.HdmiControlManager.AnonymousClass1(executor, hotplugEventListener);
    }

    public void addHdmiControlStatusChangeListener(android.hardware.hdmi.HdmiControlManager.HdmiControlStatusChangeListener hdmiControlStatusChangeListener) {
        addHdmiControlStatusChangeListener(com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, hdmiControlStatusChangeListener);
    }

    public void addHdmiControlStatusChangeListener(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiControlManager.HdmiControlStatusChangeListener hdmiControlStatusChangeListener) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "addHdmiControlStatusChangeListener: HdmiControlService is not available");
            return;
        }
        if (this.mHdmiControlStatusChangeListeners.containsKey(hdmiControlStatusChangeListener)) {
            android.util.Log.e(TAG, "listener is already registered");
            return;
        }
        android.hardware.hdmi.IHdmiControlStatusChangeListener hdmiControlStatusChangeListenerWrapper = getHdmiControlStatusChangeListenerWrapper(executor, hdmiControlStatusChangeListener);
        this.mHdmiControlStatusChangeListeners.put(hdmiControlStatusChangeListener, hdmiControlStatusChangeListenerWrapper);
        try {
            this.mService.addHdmiControlStatusChangeListener(hdmiControlStatusChangeListenerWrapper);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeHdmiControlStatusChangeListener(android.hardware.hdmi.HdmiControlManager.HdmiControlStatusChangeListener hdmiControlStatusChangeListener) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "removeHdmiControlStatusChangeListener: HdmiControlService is not available");
            return;
        }
        android.hardware.hdmi.IHdmiControlStatusChangeListener remove = this.mHdmiControlStatusChangeListeners.remove(hdmiControlStatusChangeListener);
        if (remove == null) {
            android.util.Log.e(TAG, "tried to remove not-registered listener");
            return;
        }
        try {
            this.mService.removeHdmiControlStatusChangeListener(remove);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.hardware.hdmi.HdmiControlManager$2, reason: invalid class name */
    class AnonymousClass2 extends android.hardware.hdmi.IHdmiControlStatusChangeListener.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.hardware.hdmi.HdmiControlManager.HdmiControlStatusChangeListener val$listener;

        AnonymousClass2(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiControlManager.HdmiControlStatusChangeListener hdmiControlStatusChangeListener) {
            this.val$executor = executor;
            this.val$listener = hdmiControlStatusChangeListener;
        }

        @Override // android.hardware.hdmi.IHdmiControlStatusChangeListener
        public void onStatusChange(final int i, final boolean z) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.hardware.hdmi.HdmiControlManager.HdmiControlStatusChangeListener hdmiControlStatusChangeListener = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.hardware.hdmi.HdmiControlManager$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.hdmi.HdmiControlManager.HdmiControlStatusChangeListener.this.onStatusChange(i, z);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private android.hardware.hdmi.IHdmiControlStatusChangeListener getHdmiControlStatusChangeListenerWrapper(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiControlManager.HdmiControlStatusChangeListener hdmiControlStatusChangeListener) {
        return new android.hardware.hdmi.HdmiControlManager.AnonymousClass2(executor, hdmiControlStatusChangeListener);
    }

    public void addHdmiCecVolumeControlFeatureListener(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiControlManager.HdmiCecVolumeControlFeatureListener hdmiCecVolumeControlFeatureListener) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "addHdmiCecVolumeControlFeatureListener: HdmiControlService is not available");
            return;
        }
        if (this.mHdmiCecVolumeControlFeatureListeners.containsKey(hdmiCecVolumeControlFeatureListener)) {
            android.util.Log.e(TAG, "listener is already registered");
            return;
        }
        android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener createHdmiCecVolumeControlFeatureListenerWrapper = createHdmiCecVolumeControlFeatureListenerWrapper(executor, hdmiCecVolumeControlFeatureListener);
        this.mHdmiCecVolumeControlFeatureListeners.put(hdmiCecVolumeControlFeatureListener, createHdmiCecVolumeControlFeatureListenerWrapper);
        try {
            this.mService.addHdmiCecVolumeControlFeatureListener(createHdmiCecVolumeControlFeatureListenerWrapper);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeHdmiCecVolumeControlFeatureListener(android.hardware.hdmi.HdmiControlManager.HdmiCecVolumeControlFeatureListener hdmiCecVolumeControlFeatureListener) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "removeHdmiCecVolumeControlFeatureListener: HdmiControlService is not available");
            return;
        }
        android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener remove = this.mHdmiCecVolumeControlFeatureListeners.remove(hdmiCecVolumeControlFeatureListener);
        if (remove == null) {
            android.util.Log.e(TAG, "tried to remove not-registered listener");
            return;
        }
        try {
            this.mService.removeHdmiCecVolumeControlFeatureListener(remove);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.hardware.hdmi.HdmiControlManager$3, reason: invalid class name */
    class AnonymousClass3 extends android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.hardware.hdmi.HdmiControlManager.HdmiCecVolumeControlFeatureListener val$listener;

        AnonymousClass3(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiControlManager.HdmiCecVolumeControlFeatureListener hdmiCecVolumeControlFeatureListener) {
            this.val$executor = executor;
            this.val$listener = hdmiCecVolumeControlFeatureListener;
        }

        @Override // android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener
        public void onHdmiCecVolumeControlFeature(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.hardware.hdmi.HdmiControlManager.HdmiCecVolumeControlFeatureListener hdmiCecVolumeControlFeatureListener = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.hardware.hdmi.HdmiControlManager$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.hdmi.HdmiControlManager.HdmiCecVolumeControlFeatureListener.this.onHdmiCecVolumeControlFeature(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private android.hardware.hdmi.IHdmiCecVolumeControlFeatureListener createHdmiCecVolumeControlFeatureListenerWrapper(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiControlManager.HdmiCecVolumeControlFeatureListener hdmiCecVolumeControlFeatureListener) {
        return new android.hardware.hdmi.HdmiControlManager.AnonymousClass3(executor, hdmiCecVolumeControlFeatureListener);
    }

    private void addCecSettingChangeListener(java.lang.String str, java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiControlManager.CecSettingChangeListener cecSettingChangeListener) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "addCecSettingChangeListener: HdmiControlService is not available");
            return;
        }
        if (this.mCecSettingChangeListeners.containsKey(str) && this.mCecSettingChangeListeners.get(str).containsKey(cecSettingChangeListener)) {
            android.util.Log.e(TAG, "listener is already registered");
            return;
        }
        android.hardware.hdmi.IHdmiCecSettingChangeListener cecSettingChangeListenerWrapper = getCecSettingChangeListenerWrapper(executor, cecSettingChangeListener);
        if (!this.mCecSettingChangeListeners.containsKey(str)) {
            this.mCecSettingChangeListeners.put(str, new android.util.ArrayMap<>());
        }
        this.mCecSettingChangeListeners.get(str).put(cecSettingChangeListener, cecSettingChangeListenerWrapper);
        try {
            this.mService.addCecSettingChangeListener(str, cecSettingChangeListenerWrapper);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void removeCecSettingChangeListener(java.lang.String str, android.hardware.hdmi.HdmiControlManager.CecSettingChangeListener cecSettingChangeListener) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "removeCecSettingChangeListener: HdmiControlService is not available");
            return;
        }
        android.hardware.hdmi.IHdmiCecSettingChangeListener remove = !this.mCecSettingChangeListeners.containsKey(str) ? null : this.mCecSettingChangeListeners.get(str).remove(cecSettingChangeListener);
        if (remove == null) {
            android.util.Log.e(TAG, "tried to remove not-registered listener");
            return;
        }
        try {
            this.mService.removeCecSettingChangeListener(str, remove);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.hardware.hdmi.HdmiControlManager$4, reason: invalid class name */
    class AnonymousClass4 extends android.hardware.hdmi.IHdmiCecSettingChangeListener.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.hardware.hdmi.HdmiControlManager.CecSettingChangeListener val$listener;

        AnonymousClass4(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiControlManager.CecSettingChangeListener cecSettingChangeListener) {
            this.val$executor = executor;
            this.val$listener = cecSettingChangeListener;
        }

        @Override // android.hardware.hdmi.IHdmiCecSettingChangeListener
        public void onChange(final java.lang.String str) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.hardware.hdmi.HdmiControlManager.CecSettingChangeListener cecSettingChangeListener = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.hardware.hdmi.HdmiControlManager$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.hardware.hdmi.HdmiControlManager.CecSettingChangeListener.this.onChange(str);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private android.hardware.hdmi.IHdmiCecSettingChangeListener getCecSettingChangeListenerWrapper(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiControlManager.CecSettingChangeListener cecSettingChangeListener) {
        return new android.hardware.hdmi.HdmiControlManager.AnonymousClass4(executor, cecSettingChangeListener);
    }

    public java.util.List<java.lang.String> getUserCecSettings() {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getUserCecSettings: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return this.mService.getUserCecSettings();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<java.lang.String> getAllowedCecSettingStringValues(java.lang.String str) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getAllowedCecSettingStringValues: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return this.mService.getAllowedCecSettingStringValues(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<java.lang.Integer> getAllowedCecSettingIntValues(java.lang.String str) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getAllowedCecSettingIntValues: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return (java.util.List) java.util.Arrays.stream(this.mService.getAllowedCecSettingIntValues(str)).boxed().collect(java.util.stream.Collectors.toList());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setHdmiCecEnabled(int i) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setHdmiCecEnabled: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            this.mService.setCecSettingIntValue(CEC_SETTING_NAME_HDMI_CEC_ENABLED, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getHdmiCecEnabled() {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getHdmiCecEnabled: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return this.mService.getCecSettingIntValue(CEC_SETTING_NAME_HDMI_CEC_ENABLED);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addHdmiCecEnabledChangeListener(android.hardware.hdmi.HdmiControlManager.CecSettingChangeListener cecSettingChangeListener) {
        addHdmiCecEnabledChangeListener(com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, cecSettingChangeListener);
    }

    public void addHdmiCecEnabledChangeListener(java.util.concurrent.Executor executor, android.hardware.hdmi.HdmiControlManager.CecSettingChangeListener cecSettingChangeListener) {
        addCecSettingChangeListener(CEC_SETTING_NAME_HDMI_CEC_ENABLED, executor, cecSettingChangeListener);
    }

    public void removeHdmiCecEnabledChangeListener(android.hardware.hdmi.HdmiControlManager.CecSettingChangeListener cecSettingChangeListener) {
        removeCecSettingChangeListener(CEC_SETTING_NAME_HDMI_CEC_ENABLED, cecSettingChangeListener);
    }

    public void setHdmiCecVersion(int i) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setHdmiCecVersion: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            this.mService.setCecSettingIntValue(CEC_SETTING_NAME_HDMI_CEC_VERSION, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getHdmiCecVersion() {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getHdmiCecVersion: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return this.mService.getCecSettingIntValue(CEC_SETTING_NAME_HDMI_CEC_VERSION);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRoutingControl(int i) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setRoutingControl: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            this.mService.setCecSettingIntValue(CEC_SETTING_NAME_ROUTING_CONTROL, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRoutingControl() {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getRoutingControl: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return this.mService.getCecSettingIntValue(CEC_SETTING_NAME_ROUTING_CONTROL);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSoundbarMode(int i) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setSoundbarMode: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            this.mService.setCecSettingIntValue(CEC_SETTING_NAME_SOUNDBAR_MODE, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getSoundbarMode() {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getSoundbarMode: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return this.mService.getCecSettingIntValue(CEC_SETTING_NAME_SOUNDBAR_MODE);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPowerControlMode(java.lang.String str) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setPowerControlMode: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            this.mService.setCecSettingStringValue(CEC_SETTING_NAME_POWER_CONTROL_MODE, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getPowerControlMode() {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getPowerControlMode: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return this.mService.getCecSettingStringValue(CEC_SETTING_NAME_POWER_CONTROL_MODE);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPowerStateChangeOnActiveSourceLost(java.lang.String str) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setPowerStateChangeOnActiveSourceLost: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            this.mService.setCecSettingStringValue(CEC_SETTING_NAME_POWER_STATE_CHANGE_ON_ACTIVE_SOURCE_LOST, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getPowerStateChangeOnActiveSourceLost() {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getPowerStateChangeOnActiveSourceLost: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return this.mService.getCecSettingStringValue(CEC_SETTING_NAME_POWER_STATE_CHANGE_ON_ACTIVE_SOURCE_LOST);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSystemAudioControl(int i) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setSystemAudioControl: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            this.mService.setCecSettingIntValue(CEC_SETTING_NAME_SYSTEM_AUDIO_CONTROL, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getSystemAudioControl() {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getSystemAudioControl: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return this.mService.getCecSettingIntValue(CEC_SETTING_NAME_SYSTEM_AUDIO_CONTROL);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSystemAudioModeMuting(int i) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setSystemAudioModeMuting: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            this.mService.setCecSettingIntValue(CEC_SETTING_NAME_SYSTEM_AUDIO_MODE_MUTING, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getSystemAudioModeMuting() {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getSystemAudioModeMuting: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return this.mService.getCecSettingIntValue(CEC_SETTING_NAME_SYSTEM_AUDIO_MODE_MUTING);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTvWakeOnOneTouchPlay(int i) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setTvWakeOnOneTouchPlay: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            this.mService.setCecSettingIntValue(CEC_SETTING_NAME_TV_WAKE_ON_ONE_TOUCH_PLAY, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getTvWakeOnOneTouchPlay() {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getTvWakeOnOneTouchPlay: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return this.mService.getCecSettingIntValue(CEC_SETTING_NAME_TV_WAKE_ON_ONE_TOUCH_PLAY);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTvSendStandbyOnSleep(int i) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setTvSendStandbyOnSleep: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            this.mService.setCecSettingIntValue(CEC_SETTING_NAME_TV_SEND_STANDBY_ON_SLEEP, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getTvSendStandbyOnSleep() {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getTvSendStandbyOnSleep: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return this.mService.getCecSettingIntValue(CEC_SETTING_NAME_TV_SEND_STANDBY_ON_SLEEP);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSadPresenceInQuery(java.lang.String str, int i) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setSadPresenceInQuery: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            this.mService.setCecSettingIntValue(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSadsPresenceInQuery(java.util.List<java.lang.String> list, int i) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setSadsPresenceInQuery: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            java.util.Iterator<java.lang.String> it = list.iterator();
            while (it.hasNext()) {
                this.mService.setCecSettingIntValue(it.next(), i);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getSadPresenceInQuery(java.lang.String str) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getSadPresenceInQuery: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return this.mService.getCecSettingIntValue(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setEarcEnabled(int i) {
        if (this.mService == null) {
            android.util.Log.e(TAG, "setEarcEnabled: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            this.mService.setCecSettingIntValue(SETTING_NAME_EARC_ENABLED, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getEarcEnabled() {
        if (this.mService == null) {
            android.util.Log.e(TAG, "getEarcEnabled: HdmiControlService is not available");
            throw new java.lang.RuntimeException("HdmiControlService is not available");
        }
        try {
            return this.mService.getCecSettingIntValue(SETTING_NAME_EARC_ENABLED);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
