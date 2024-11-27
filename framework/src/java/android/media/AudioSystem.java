package android.media;

/* loaded from: classes2.dex */
public class AudioSystem {
    public static final int AUDIO_FORMAT_AAC = 67108864;
    public static final int AUDIO_FORMAT_APTX = 536870912;
    public static final int AUDIO_FORMAT_APTX_HD = 553648128;
    public static final int AUDIO_FORMAT_DEFAULT = 0;
    public static final int AUDIO_FORMAT_INVALID = -1;
    public static final int AUDIO_FORMAT_LC3 = 721420288;
    public static final int AUDIO_FORMAT_LDAC = 587202560;
    public static final int AUDIO_FORMAT_OPUS = 134217728;
    public static final int AUDIO_FORMAT_SBC = 520093696;
    public static final int AUDIO_HW_SYNC_INVALID = 0;
    public static final int AUDIO_SESSION_ALLOCATE = 0;
    public static final int AUDIO_STATUS_ERROR = 1;
    public static final int AUDIO_STATUS_OK = 0;
    public static final int AUDIO_STATUS_SERVER_DIED = 100;
    public static final int BAD_VALUE = -2;
    public static final int DEAD_OBJECT = -6;
    private static final boolean DEBUG_VOLUME = false;
    public static final int DEFAULT_MUTE_STREAMS_AFFECTED = 111;
    public static int[] DEFAULT_STREAM_VOLUME = null;
    public static final java.util.Set<java.lang.Integer> DEVICE_ALL_HDMI_SYSTEM_AUDIO_AND_SPEAKER_SET;
    public static final int DEVICE_BIT_DEFAULT = 1073741824;
    public static final int DEVICE_BIT_IN = Integer.MIN_VALUE;
    public static final java.util.Set<java.lang.Integer> DEVICE_IN_ALL_BLE_SET;
    public static final java.util.Set<java.lang.Integer> DEVICE_IN_ALL_SCO_SET;
    public static final java.util.Set<java.lang.Integer> DEVICE_IN_ALL_SET;
    public static final java.util.Set<java.lang.Integer> DEVICE_IN_ALL_USB_SET;
    public static final int DEVICE_IN_AMBIENT = -2147483646;
    public static final java.lang.String DEVICE_IN_AMBIENT_NAME = "ambient";
    public static final int DEVICE_IN_ANLG_DOCK_HEADSET = -2147483136;
    public static final java.lang.String DEVICE_IN_ANLG_DOCK_HEADSET_NAME = "analog_dock";
    public static final int DEVICE_IN_AUX_DIGITAL = -2147483616;
    public static final java.lang.String DEVICE_IN_AUX_DIGITAL_NAME = "aux_digital";
    public static final int DEVICE_IN_BACK_MIC = -2147483520;
    public static final java.lang.String DEVICE_IN_BACK_MIC_NAME = "back_mic";
    public static final int DEVICE_IN_BLE_HEADSET = -1610612736;
    public static final java.lang.String DEVICE_IN_BLE_HEADSET_NAME = "ble_headset";
    public static final int DEVICE_IN_BLUETOOTH_A2DP = -2147352576;
    public static final java.lang.String DEVICE_IN_BLUETOOTH_A2DP_NAME = "bt_a2dp";
    public static final int DEVICE_IN_BLUETOOTH_BLE = -2080374784;
    public static final java.lang.String DEVICE_IN_BLUETOOTH_BLE_NAME = "bt_ble";
    public static final int DEVICE_IN_BLUETOOTH_SCO_HEADSET = -2147483640;
    public static final java.lang.String DEVICE_IN_BLUETOOTH_SCO_HEADSET_NAME = "bt_sco_hs";
    public static final int DEVICE_IN_BUILTIN_MIC = -2147483644;
    public static final java.lang.String DEVICE_IN_BUILTIN_MIC_NAME = "mic";
    public static final int DEVICE_IN_BUS = -2146435072;
    public static final java.lang.String DEVICE_IN_BUS_NAME = "bus";
    public static final int DEVICE_IN_COMMUNICATION = -2147483647;
    public static final java.lang.String DEVICE_IN_COMMUNICATION_NAME = "communication";
    public static final int DEVICE_IN_DEFAULT = -1073741824;
    public static final int DEVICE_IN_DGTL_DOCK_HEADSET = -2147482624;
    public static final java.lang.String DEVICE_IN_DGTL_DOCK_HEADSET_NAME = "digital_dock";
    public static final int DEVICE_IN_ECHO_REFERENCE = -1879048192;
    public static final java.lang.String DEVICE_IN_ECHO_REFERENCE_NAME = "echo_reference";
    public static final int DEVICE_IN_FM_TUNER = -2147475456;
    public static final java.lang.String DEVICE_IN_FM_TUNER_NAME = "fm_tuner";
    public static final int DEVICE_IN_HDMI = -2147483616;
    public static final int DEVICE_IN_HDMI_ARC = -2013265920;
    public static final java.lang.String DEVICE_IN_HDMI_ARC_NAME = "hdmi_arc";
    public static final int DEVICE_IN_HDMI_EARC = -2013265919;
    public static final java.lang.String DEVICE_IN_HDMI_EARC_NAME = "hdmi_earc";
    public static final int DEVICE_IN_IP = -2146959360;
    public static final java.lang.String DEVICE_IN_IP_NAME = "ip";
    public static final int DEVICE_IN_LINE = -2147450880;
    public static final java.lang.String DEVICE_IN_LINE_NAME = "line";
    public static final int DEVICE_IN_LOOPBACK = -2147221504;
    public static final java.lang.String DEVICE_IN_LOOPBACK_NAME = "loopback";
    public static final int DEVICE_IN_PROXY = -2130706432;
    public static final java.lang.String DEVICE_IN_PROXY_NAME = "proxy";
    public static final int DEVICE_IN_REMOTE_SUBMIX = -2147483392;
    public static final java.lang.String DEVICE_IN_REMOTE_SUBMIX_NAME = "remote_submix";
    public static final int DEVICE_IN_SPDIF = -2147418112;
    public static final java.lang.String DEVICE_IN_SPDIF_NAME = "spdif";
    public static final int DEVICE_IN_TELEPHONY_RX = -2147483584;
    public static final java.lang.String DEVICE_IN_TELEPHONY_RX_NAME = "telephony_rx";
    public static final int DEVICE_IN_TV_TUNER = -2147467264;
    public static final java.lang.String DEVICE_IN_TV_TUNER_NAME = "tv_tuner";
    public static final int DEVICE_IN_USB_ACCESSORY = -2147481600;
    public static final java.lang.String DEVICE_IN_USB_ACCESSORY_NAME = "usb_accessory";
    public static final int DEVICE_IN_USB_DEVICE = -2147479552;
    public static final java.lang.String DEVICE_IN_USB_DEVICE_NAME = "usb_device";
    public static final int DEVICE_IN_USB_HEADSET = -2113929216;
    public static final java.lang.String DEVICE_IN_USB_HEADSET_NAME = "usb_headset";
    public static final int DEVICE_IN_VOICE_CALL = -2147483584;
    public static final int DEVICE_IN_WIRED_HEADSET = -2147483632;
    public static final java.lang.String DEVICE_IN_WIRED_HEADSET_NAME = "headset";
    public static final int DEVICE_NONE = 0;
    public static final java.util.Set<java.lang.Integer> DEVICE_OUT_ALL_A2DP_SET;
    public static final java.util.Set<java.lang.Integer> DEVICE_OUT_ALL_BLE_SET;
    public static final java.util.Set<java.lang.Integer> DEVICE_OUT_ALL_HDMI_SYSTEM_AUDIO_SET;
    public static final java.util.Set<java.lang.Integer> DEVICE_OUT_ALL_SCO_SET;
    public static final int DEVICE_OUT_ALL_USB = 67133440;
    public static final java.util.Set<java.lang.Integer> DEVICE_OUT_ALL_USB_SET;
    public static final int DEVICE_OUT_ANLG_DOCK_HEADSET = 2048;
    public static final java.lang.String DEVICE_OUT_ANLG_DOCK_HEADSET_NAME = "analog_dock";
    public static final int DEVICE_OUT_AUX_DIGITAL = 1024;
    public static final java.lang.String DEVICE_OUT_AUX_DIGITAL_NAME = "aux_digital";
    public static final int DEVICE_OUT_AUX_LINE = 2097152;
    public static final java.lang.String DEVICE_OUT_AUX_LINE_NAME = "aux_line";
    public static final int DEVICE_OUT_BLE_BROADCAST = 536870914;
    public static final java.lang.String DEVICE_OUT_BLE_BROADCAST_NAME = "ble_broadcast";
    public static final int DEVICE_OUT_BLE_HEADSET = 536870912;
    public static final java.lang.String DEVICE_OUT_BLE_HEADSET_NAME = "ble_headset";
    public static final int DEVICE_OUT_BLE_SPEAKER = 536870913;
    public static final java.lang.String DEVICE_OUT_BLE_SPEAKER_NAME = "ble_speaker";
    public static final int DEVICE_OUT_BLUETOOTH_A2DP = 128;
    public static final int DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES = 256;
    public static final java.lang.String DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES_NAME = "bt_a2dp_hp";
    public static final java.lang.String DEVICE_OUT_BLUETOOTH_A2DP_NAME = "bt_a2dp";
    public static final int DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER = 512;
    public static final java.lang.String DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER_NAME = "bt_a2dp_spk";
    public static final int DEVICE_OUT_BLUETOOTH_SCO = 16;
    public static final int DEVICE_OUT_BLUETOOTH_SCO_CARKIT = 64;
    public static final java.lang.String DEVICE_OUT_BLUETOOTH_SCO_CARKIT_NAME = "bt_sco_carkit";
    public static final int DEVICE_OUT_BLUETOOTH_SCO_HEADSET = 32;
    public static final java.lang.String DEVICE_OUT_BLUETOOTH_SCO_HEADSET_NAME = "bt_sco_hs";
    public static final java.lang.String DEVICE_OUT_BLUETOOTH_SCO_NAME = "bt_sco";
    public static final int DEVICE_OUT_BUS = 16777216;
    public static final java.lang.String DEVICE_OUT_BUS_NAME = "bus";
    public static final int DEVICE_OUT_DEFAULT = 1073741824;
    public static final int DEVICE_OUT_DGTL_DOCK_HEADSET = 4096;
    public static final java.lang.String DEVICE_OUT_DGTL_DOCK_HEADSET_NAME = "digital_dock";
    public static final int DEVICE_OUT_EARPIECE = 1;
    public static final java.lang.String DEVICE_OUT_EARPIECE_NAME = "earpiece";
    public static final int DEVICE_OUT_ECHO_CANCELLER = 268435456;
    public static final java.lang.String DEVICE_OUT_ECHO_CANCELLER_NAME = "echo_canceller";
    public static final int DEVICE_OUT_FM = 1048576;
    public static final java.lang.String DEVICE_OUT_FM_NAME = "fm_transmitter";
    public static final int DEVICE_OUT_HDMI = 1024;
    public static final int DEVICE_OUT_HDMI_ARC = 262144;
    public static final java.lang.String DEVICE_OUT_HDMI_ARC_NAME = "hdmi_arc";
    public static final int DEVICE_OUT_HDMI_EARC = 262145;
    public static final java.lang.String DEVICE_OUT_HDMI_EARC_NAME = "hdmi_earc";
    public static final java.lang.String DEVICE_OUT_HDMI_NAME = "hdmi";
    public static final int DEVICE_OUT_HEARING_AID = 134217728;
    public static final java.lang.String DEVICE_OUT_HEARING_AID_NAME = "hearing_aid_out";
    public static final int DEVICE_OUT_IP = 8388608;
    public static final java.lang.String DEVICE_OUT_IP_NAME = "ip";
    public static final int DEVICE_OUT_LINE = 131072;
    public static final java.lang.String DEVICE_OUT_LINE_NAME = "line";
    public static final int DEVICE_OUT_PROXY = 33554432;
    public static final java.lang.String DEVICE_OUT_PROXY_NAME = "proxy";
    public static final int DEVICE_OUT_REMOTE_SUBMIX = 32768;
    public static final java.lang.String DEVICE_OUT_REMOTE_SUBMIX_NAME = "remote_submix";
    public static final int DEVICE_OUT_SPDIF = 524288;
    public static final java.lang.String DEVICE_OUT_SPDIF_NAME = "spdif";
    public static final int DEVICE_OUT_SPEAKER = 2;
    public static final java.lang.String DEVICE_OUT_SPEAKER_NAME = "speaker";
    public static final int DEVICE_OUT_SPEAKER_SAFE = 4194304;
    public static final java.lang.String DEVICE_OUT_SPEAKER_SAFE_NAME = "speaker_safe";
    public static final int DEVICE_OUT_TELEPHONY_TX = 65536;
    public static final java.lang.String DEVICE_OUT_TELEPHONY_TX_NAME = "telephony_tx";
    public static final int DEVICE_OUT_USB_ACCESSORY = 8192;
    public static final java.lang.String DEVICE_OUT_USB_ACCESSORY_NAME = "usb_accessory";
    public static final int DEVICE_OUT_USB_DEVICE = 16384;
    public static final java.lang.String DEVICE_OUT_USB_DEVICE_NAME = "usb_device";
    public static final int DEVICE_OUT_USB_HEADSET = 67108864;
    public static final java.lang.String DEVICE_OUT_USB_HEADSET_NAME = "usb_headset";
    public static final int DEVICE_OUT_WIRED_HEADPHONE = 8;
    public static final java.lang.String DEVICE_OUT_WIRED_HEADPHONE_NAME = "headphone";
    public static final int DEVICE_OUT_WIRED_HEADSET = 4;
    public static final java.lang.String DEVICE_OUT_WIRED_HEADSET_NAME = "headset";
    public static final int DEVICE_ROLE_DISABLED = 2;
    public static final int DEVICE_ROLE_NONE = 0;
    public static final int DEVICE_ROLE_PREFERRED = 1;
    public static final int DEVICE_STATE_AVAILABLE = 1;
    public static final int DEVICE_STATE_UNAVAILABLE = 0;
    public static final int DIRECT_BITSTREAM_SUPPORTED = 4;
    public static final int DIRECT_NOT_SUPPORTED = 0;
    public static final int DIRECT_OFFLOAD_GAPLESS_SUPPORTED = 3;
    public static final int DIRECT_OFFLOAD_SUPPORTED = 1;
    private static final int DYNAMIC_POLICY_EVENT_MIX_STATE_UPDATE = 0;
    public static final int ERROR = -1;
    public static final int FCC_24 = 24;
    public static final int FORCE_ANALOG_DOCK = 8;
    public static final int FORCE_BT_A2DP = 4;
    public static final int FORCE_BT_CAR_DOCK = 6;
    public static final int FORCE_BT_DESK_DOCK = 7;
    public static final int FORCE_BT_SCO = 3;
    public static final int FORCE_DEFAULT = 0;
    public static final int FORCE_DIGITAL_DOCK = 9;
    public static final int FORCE_ENCODED_SURROUND_ALWAYS = 14;
    public static final int FORCE_ENCODED_SURROUND_MANUAL = 15;
    public static final int FORCE_ENCODED_SURROUND_NEVER = 13;
    public static final int FORCE_HDMI_SYSTEM_AUDIO_ENFORCED = 12;
    public static final int FORCE_HEADPHONES = 2;
    public static final int FORCE_NONE = 0;
    public static final int FORCE_NO_BT_A2DP = 10;
    public static final int FORCE_SPEAKER = 1;
    public static final int FORCE_SYSTEM_ENFORCED = 11;
    public static final int FORCE_WIRED_ACCESSORY = 5;
    public static final int FOR_COMMUNICATION = 0;
    public static final int FOR_DOCK = 3;
    public static final int FOR_ENCODED_SURROUND = 6;
    public static final int FOR_HDMI_SYSTEM_AUDIO = 5;
    public static final int FOR_MEDIA = 1;
    public static final int FOR_RECORD = 2;
    public static final int FOR_SYSTEM = 4;
    public static final int FOR_VIBRATE_RINGING = 7;
    public static final int INVALID_OPERATION = -3;
    public static final java.lang.String IN_VOICE_COMM_FOCUS_ID = "AudioFocus_For_Phone_Ring_And_Calls";
    public static final java.lang.String LEGACY_REMOTE_SUBMIX_ADDRESS = "0";
    private static final int MAX_DEVICE_ROUTING = 4;
    public static final int MODE_CALL_REDIRECT = 5;
    public static final int MODE_CALL_SCREENING = 4;
    public static final int MODE_COMMUNICATION_REDIRECT = 6;
    public static final int MODE_CURRENT = -1;
    public static final int MODE_INVALID = -2;
    public static final int MODE_IN_CALL = 2;
    public static final int MODE_IN_COMMUNICATION = 3;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_RINGTONE = 1;
    static final int NATIVE_EVENT_ROUTING_CHANGE = 1000;
    public static final int NO_INIT = -5;
    private static final int NUM_DEVICE_STATES = 1;
    public static final int NUM_FORCE_CONFIG = 16;
    private static final int NUM_FORCE_USE = 8;
    public static final int NUM_MODES = 7;
    public static final int NUM_STREAMS = 5;
    private static final int NUM_STREAM_TYPES = 12;
    public static final int OFFLOAD_GAPLESS_SUPPORTED = 2;
    public static final int OFFLOAD_NOT_SUPPORTED = 0;
    public static final int OFFLOAD_SUPPORTED = 1;
    public static final int PERMISSION_DENIED = -4;
    public static final int PHONE_STATE_INCALL = 2;
    public static final int PHONE_STATE_OFFCALL = 0;
    public static final int PHONE_STATE_RINGING = 1;
    public static final int PLATFORM_AUTOMOTIVE = 3;
    public static final int PLATFORM_DEFAULT = 0;
    public static final int PLATFORM_TELEVISION = 2;
    public static final int PLATFORM_VOICE = 1;
    public static final int PLAY_SOUND_DELAY = 300;

    @java.lang.Deprecated
    public static final int ROUTE_ALL = -1;

    @java.lang.Deprecated
    public static final int ROUTE_BLUETOOTH = 4;

    @java.lang.Deprecated
    public static final int ROUTE_BLUETOOTH_A2DP = 16;

    @java.lang.Deprecated
    public static final int ROUTE_BLUETOOTH_SCO = 4;

    @java.lang.Deprecated
    public static final int ROUTE_EARPIECE = 1;

    @java.lang.Deprecated
    public static final int ROUTE_HEADSET = 8;

    @java.lang.Deprecated
    public static final int ROUTE_SPEAKER = 2;
    public static final int STREAM_ACCESSIBILITY = 10;
    public static final int STREAM_ALARM = 4;
    public static final int STREAM_ASSISTANT = 11;
    public static final int STREAM_BLUETOOTH_SCO = 6;
    public static final int STREAM_DEFAULT = -1;
    public static final int STREAM_DTMF = 8;
    public static final int STREAM_MUSIC = 3;
    public static final int STREAM_NOTIFICATION = 5;
    public static final int STREAM_RING = 2;
    public static final int STREAM_SYSTEM = 1;
    public static final int STREAM_SYSTEM_ENFORCED = 7;
    public static final int STREAM_TTS = 9;
    public static final int STREAM_VOICE_CALL = 0;
    public static final int SUCCESS = 0;
    public static final int SYNC_EVENT_NONE = 0;
    public static final int SYNC_EVENT_PRESENTATION_COMPLETE = 1;
    public static final int SYNC_EVENT_SHARE_AUDIO_HISTORY = 100;
    private static final java.lang.String TAG = "AudioSystem";
    public static final int WOULD_BLOCK = -7;
    private static android.media.AudioSystem.DynamicPolicyCallback sDynPolicyCallback;
    private static android.media.AudioSystem.ErrorCallback sErrorCallback;
    private static android.media.AudioSystem.AudioRecordingCallback sRecordingCallback;
    private static android.media.AudioSystem.RoutingUpdateCallback sRoutingUpdateCallback;
    private static android.media.AudioSystem.VolumeRangeInitRequestCallback sVolRangeInitReqCallback;
    public static final int OUT_CHANNEL_COUNT_MAX = native_getMaxChannelCount();
    public static final int SAMPLE_RATE_HZ_MAX = native_getMaxSampleRate();
    public static final int SAMPLE_RATE_HZ_MIN = native_getMinSampleRate();
    public static final java.lang.String[] STREAM_NAMES = {"STREAM_VOICE_CALL", "STREAM_SYSTEM", "STREAM_RING", "STREAM_MUSIC", "STREAM_ALARM", "STREAM_NOTIFICATION", "STREAM_BLUETOOTH_SCO", "STREAM_SYSTEM_ENFORCED", "STREAM_DTMF", "STREAM_TTS", "STREAM_ACCESSIBILITY", "STREAM_ASSISTANT"};
    public static final java.util.Set<java.lang.Integer> DEVICE_OUT_ALL_SET = new java.util.HashSet();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioFormatNativeEnumForBtCodec {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioFormatNativeEnumForBtLeAudioCodec {
    }

    public interface AudioRecordingCallback {
        void onRecordingConfigurationChanged(int i, int i2, int i3, int i4, int i5, int i6, boolean z, int[] iArr, android.media.audiofx.AudioEffect.Descriptor[] descriptorArr, android.media.audiofx.AudioEffect.Descriptor[] descriptorArr2, int i7, java.lang.String str);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioSystemError {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BtOffloadDeviceType {
    }

    public interface DynamicPolicyCallback {
        void onDynamicPolicyMixStateUpdate(java.lang.String str, int i);
    }

    public interface ErrorCallback {
        void onError(int i);
    }

    public interface RoutingUpdateCallback {
        void onRoutingUpdated();
    }

    public interface VolumeRangeInitRequestCallback {
        void onVolumeRangeInitializationRequested();
    }

    private static native int addDevicesRoleForCapturePreset(int i, int i2, int[] iArr, java.lang.String[] strArr);

    public static native boolean canBeSpatialized(android.media.AudioAttributes audioAttributes, android.media.AudioFormat audioFormat, android.media.AudioDeviceAttributes[] audioDeviceAttributesArr);

    public static native int checkAudioFlinger();

    public static native int clearDevicesRoleForCapturePreset(int i, int i2);

    public static native int clearDevicesRoleForStrategy(int i, int i2);

    public static native int clearPreferredMixerAttributes(android.media.AudioAttributes audioAttributes, int i, int i2);

    public static native int createAudioPatch(android.media.AudioPatch[] audioPatchArr, android.media.AudioPortConfig[] audioPortConfigArr, android.media.AudioPortConfig[] audioPortConfigArr2);

    public static native int getAudioHwSyncForSession(int i);

    public static native int getDeviceConnectionState(int i, java.lang.String str);

    private static native int getDevicesForAttributes(android.media.AudioAttributes audioAttributes, android.media.AudioDeviceAttributes[] audioDeviceAttributesArr, boolean z);

    public static native int getDevicesForRoleAndCapturePreset(int i, int i2, java.util.List<android.media.AudioDeviceAttributes> list);

    public static native int getDevicesForRoleAndStrategy(int i, int i2, java.util.List<android.media.AudioDeviceAttributes> list);

    public static native int getDirectPlaybackSupport(android.media.AudioFormat audioFormat, android.media.AudioAttributes audioAttributes);

    public static native int getDirectProfilesForAttributes(android.media.AudioAttributes audioAttributes, java.util.ArrayList<android.media.AudioProfile> arrayList);

    public static native int getForceUse(int i);

    public static native int getHwOffloadFormatsSupportedForBluetoothMedia(int i, java.util.ArrayList<java.lang.Integer> arrayList);

    public static native float getMasterBalance();

    public static native boolean getMasterMono();

    public static native boolean getMasterMute();

    public static native float getMasterVolume();

    public static native int getMaxVolumeIndexForAttributes(android.media.AudioAttributes audioAttributes);

    public static native int getMicrophones(java.util.ArrayList<android.media.MicrophoneInfo> arrayList);

    public static native int getMinVolumeIndexForAttributes(android.media.AudioAttributes audioAttributes);

    public static native int getOutputLatency(int i);

    public static native java.lang.String getParameters(java.lang.String str);

    public static native int getPreferredMixerAttributes(android.media.AudioAttributes audioAttributes, int i, java.util.List<android.media.AudioMixerAttributes> list);

    public static native int getPrimaryOutputFrameCount();

    public static native int getPrimaryOutputSamplingRate();

    public static native int getRegisteredPolicyMixes(java.util.List<android.media.audiopolicy.AudioMix> list);

    public static native int getReportedSurroundFormats(java.util.ArrayList<java.lang.Integer> arrayList);

    public static native float getStreamVolumeDB(int i, int i2, int i3);

    public static native int getStreamVolumeIndex(int i, int i2);

    public static native int getSupportedDeviceTypes(int i, android.util.IntArray intArray);

    public static native int getSupportedMixerAttributes(int i, java.util.List<android.media.AudioMixerAttributes> list);

    public static native int getSurroundFormats(java.util.Map<java.lang.Integer, java.lang.Boolean> map);

    public static native int getVolumeIndexForAttributes(android.media.AudioAttributes audioAttributes, int i);

    public static native int handleDeviceConfigChange(int i, java.lang.String str, java.lang.String str2, int i2);

    public static native int initStreamVolume(int i, int i2, int i3);

    public static native boolean isBluetoothVariableLatencyEnabled();

    public static native boolean isCallScreeningModeSupported();

    public static native boolean isHapticPlaybackSupported();

    public static native boolean isMicrophoneMuted();

    public static native boolean isSourceActive(int i);

    public static native boolean isStreamActive(int i, int i2);

    public static native boolean isStreamActiveRemotely(int i, int i2);

    public static native boolean isUltrasoundSupported();

    public static native int listAudioPatches(java.util.ArrayList<android.media.AudioPatch> arrayList, int[] iArr);

    public static native int listAudioPorts(java.util.ArrayList<android.media.AudioPort> arrayList, int[] iArr);

    public static native int muteMicrophone(boolean z);

    private static native android.os.IBinder nativeGetSoundDose(android.media.ISoundDoseCallback iSoundDoseCallback);

    private static native android.os.IBinder nativeGetSpatializer(android.media.INativeSpatializerCallback iNativeSpatializerCallback);

    private static native int native_getMaxChannelCount();

    private static native int native_getMaxSampleRate();

    private static native int native_getMinSampleRate();

    private static native int native_get_offload_support(int i, int i2, int i3, int i4, int i5);

    private static final native void native_register_dynamic_policy_callback();

    private static final native void native_register_recording_callback();

    private static native void native_register_routing_callback();

    private static native void native_register_vol_range_init_req_callback();

    public static native int newAudioPlayerId();

    public static native int newAudioRecorderId();

    public static native int newAudioSessionId();

    public static native int registerPolicyMixes(java.util.ArrayList<android.media.audiopolicy.AudioMix> arrayList, boolean z);

    public static native int releaseAudioPatch(android.media.AudioPatch audioPatch);

    private static native int removeDevicesRoleForCapturePreset(int i, int i2, int[] iArr, java.lang.String[] strArr);

    public static native int removeDevicesRoleForStrategy(int i, int i2, int[] iArr, java.lang.String[] strArr);

    public static native int removeUidDeviceAffinities(int i);

    public static native int removeUserIdDeviceAffinities(int i);

    public static native int setA11yServicesUids(int[] iArr);

    public static native int setActiveAssistantServicesUids(int[] iArr);

    public static native int setAllowedCapturePolicy(int i, int i2);

    public static native int setAssistantServicesUids(int[] iArr);

    public static native void setAudioFlingerBinder(android.os.IBinder iBinder);

    public static native int setAudioHalPids(int[] iArr);

    public static native int setAudioPortConfig(android.media.AudioPortConfig audioPortConfig);

    public static native int setBluetoothVariableLatencyEnabled(boolean z);

    public static native int setCurrentImeUid(int i);

    public static native int setDeviceConnectionState(int i, android.os.Parcel parcel, int i2);

    private static native int setDevicesRoleForCapturePreset(int i, int i2, int[] iArr, java.lang.String[] strArr);

    private static native int setDevicesRoleForStrategy(int i, int i2, int[] iArr, java.lang.String[] strArr);

    public static native int setForceUse(int i, int i2);

    public static native int setLowRamDevice(boolean z, long j);

    public static native int setMasterBalance(float f);

    public static native int setMasterMono(boolean z);

    public static native int setMasterMute(boolean z);

    public static native int setMasterVolume(float f);

    public static native int setParameters(java.lang.String str);

    public static native int setPhoneState(int i, int i2);

    public static native int setPreferredMixerAttributes(android.media.AudioAttributes audioAttributes, int i, int i2, android.media.AudioMixerAttributes audioMixerAttributes);

    public static native int setRttEnabled(boolean z);

    private static native int setStreamVolumeIndex(int i, int i2, int i3);

    public static native int setSupportedSystemUsages(int[] iArr);

    public static native int setSurroundFormatEnabled(int i, boolean z);

    public static native int setUidDeviceAffinities(int i, int[] iArr, java.lang.String[] strArr);

    public static native int setUserIdDeviceAffinities(int i, int[] iArr, java.lang.String[] strArr);

    public static native int setVibratorInfos(java.util.List<android.os.Vibrator> list);

    public static native int setVolumeIndexForAttributes(android.media.AudioAttributes audioAttributes, int i, int i2);

    public static native int startAudioSource(android.media.AudioPortConfig audioPortConfig, android.media.AudioAttributes audioAttributes);

    public static native int stopAudioSource(int i);

    public static native boolean supportsBluetoothVariableLatency();

    public static native int systemReady();

    public static native int updatePolicyMixes(android.media.audiopolicy.AudioMix[] audioMixArr, android.media.audiopolicy.AudioMixingRule[] audioMixingRuleArr);

    private AudioSystem() {
        throw new java.lang.UnsupportedOperationException("Trying to instantiate AudioSystem");
    }

    static {
        DEVICE_OUT_ALL_SET.add(1);
        DEVICE_OUT_ALL_SET.add(2);
        DEVICE_OUT_ALL_SET.add(4);
        DEVICE_OUT_ALL_SET.add(8);
        DEVICE_OUT_ALL_SET.add(16);
        DEVICE_OUT_ALL_SET.add(32);
        DEVICE_OUT_ALL_SET.add(64);
        DEVICE_OUT_ALL_SET.add(128);
        DEVICE_OUT_ALL_SET.add(256);
        DEVICE_OUT_ALL_SET.add(512);
        DEVICE_OUT_ALL_SET.add(1024);
        DEVICE_OUT_ALL_SET.add(2048);
        DEVICE_OUT_ALL_SET.add(4096);
        DEVICE_OUT_ALL_SET.add(8192);
        DEVICE_OUT_ALL_SET.add(16384);
        DEVICE_OUT_ALL_SET.add(32768);
        DEVICE_OUT_ALL_SET.add(65536);
        DEVICE_OUT_ALL_SET.add(131072);
        DEVICE_OUT_ALL_SET.add(262144);
        DEVICE_OUT_ALL_SET.add(262145);
        DEVICE_OUT_ALL_SET.add(524288);
        DEVICE_OUT_ALL_SET.add(1048576);
        DEVICE_OUT_ALL_SET.add(2097152);
        DEVICE_OUT_ALL_SET.add(4194304);
        DEVICE_OUT_ALL_SET.add(8388608);
        DEVICE_OUT_ALL_SET.add(16777216);
        DEVICE_OUT_ALL_SET.add(33554432);
        DEVICE_OUT_ALL_SET.add(67108864);
        DEVICE_OUT_ALL_SET.add(134217728);
        DEVICE_OUT_ALL_SET.add(268435456);
        DEVICE_OUT_ALL_SET.add(536870912);
        DEVICE_OUT_ALL_SET.add(536870913);
        DEVICE_OUT_ALL_SET.add(536870914);
        DEVICE_OUT_ALL_SET.add(1073741824);
        DEVICE_OUT_ALL_A2DP_SET = new java.util.HashSet();
        DEVICE_OUT_ALL_A2DP_SET.add(128);
        DEVICE_OUT_ALL_A2DP_SET.add(256);
        DEVICE_OUT_ALL_A2DP_SET.add(512);
        DEVICE_OUT_ALL_SCO_SET = new java.util.HashSet();
        DEVICE_OUT_ALL_SCO_SET.add(16);
        DEVICE_OUT_ALL_SCO_SET.add(32);
        DEVICE_OUT_ALL_SCO_SET.add(64);
        DEVICE_OUT_ALL_USB_SET = new java.util.HashSet();
        DEVICE_OUT_ALL_USB_SET.add(8192);
        DEVICE_OUT_ALL_USB_SET.add(16384);
        DEVICE_OUT_ALL_USB_SET.add(67108864);
        DEVICE_OUT_ALL_HDMI_SYSTEM_AUDIO_SET = new java.util.HashSet();
        DEVICE_OUT_ALL_HDMI_SYSTEM_AUDIO_SET.add(2097152);
        DEVICE_OUT_ALL_HDMI_SYSTEM_AUDIO_SET.add(262144);
        DEVICE_OUT_ALL_HDMI_SYSTEM_AUDIO_SET.add(262145);
        DEVICE_OUT_ALL_HDMI_SYSTEM_AUDIO_SET.add(524288);
        DEVICE_ALL_HDMI_SYSTEM_AUDIO_AND_SPEAKER_SET = new java.util.HashSet();
        DEVICE_ALL_HDMI_SYSTEM_AUDIO_AND_SPEAKER_SET.addAll(DEVICE_OUT_ALL_HDMI_SYSTEM_AUDIO_SET);
        DEVICE_ALL_HDMI_SYSTEM_AUDIO_AND_SPEAKER_SET.add(2);
        DEVICE_OUT_ALL_BLE_SET = new java.util.HashSet();
        DEVICE_OUT_ALL_BLE_SET.add(536870912);
        DEVICE_OUT_ALL_BLE_SET.add(536870913);
        DEVICE_OUT_ALL_BLE_SET.add(536870914);
        DEVICE_IN_ALL_SET = new java.util.HashSet();
        DEVICE_IN_ALL_SET.add(java.lang.Integer.valueOf(DEVICE_IN_COMMUNICATION));
        DEVICE_IN_ALL_SET.add(java.lang.Integer.valueOf(DEVICE_IN_AMBIENT));
        DEVICE_IN_ALL_SET.add(-2147483644);
        DEVICE_IN_ALL_SET.add(-2147483640);
        DEVICE_IN_ALL_SET.add(-2147483632);
        DEVICE_IN_ALL_SET.add(-2147483616);
        DEVICE_IN_ALL_SET.add(-2147483584);
        DEVICE_IN_ALL_SET.add(-2147483520);
        DEVICE_IN_ALL_SET.add(java.lang.Integer.valueOf(DEVICE_IN_REMOTE_SUBMIX));
        DEVICE_IN_ALL_SET.add(-2147483136);
        DEVICE_IN_ALL_SET.add(-2147482624);
        DEVICE_IN_ALL_SET.add(-2147481600);
        DEVICE_IN_ALL_SET.add(-2147479552);
        DEVICE_IN_ALL_SET.add(-2147475456);
        DEVICE_IN_ALL_SET.add(-2147467264);
        DEVICE_IN_ALL_SET.add(-2147450880);
        DEVICE_IN_ALL_SET.add(-2147418112);
        DEVICE_IN_ALL_SET.add(java.lang.Integer.valueOf(DEVICE_IN_BLUETOOTH_A2DP));
        DEVICE_IN_ALL_SET.add(-2147221504);
        DEVICE_IN_ALL_SET.add(java.lang.Integer.valueOf(DEVICE_IN_IP));
        DEVICE_IN_ALL_SET.add(java.lang.Integer.valueOf(DEVICE_IN_BUS));
        DEVICE_IN_ALL_SET.add(java.lang.Integer.valueOf(DEVICE_IN_PROXY));
        DEVICE_IN_ALL_SET.add(java.lang.Integer.valueOf(DEVICE_IN_USB_HEADSET));
        DEVICE_IN_ALL_SET.add(java.lang.Integer.valueOf(DEVICE_IN_BLUETOOTH_BLE));
        DEVICE_IN_ALL_SET.add(-2013265920);
        DEVICE_IN_ALL_SET.add(-2013265919);
        DEVICE_IN_ALL_SET.add(-1879048192);
        DEVICE_IN_ALL_SET.add(-1610612736);
        DEVICE_IN_ALL_SET.add(java.lang.Integer.valueOf(DEVICE_IN_DEFAULT));
        DEVICE_IN_ALL_SCO_SET = new java.util.HashSet();
        DEVICE_IN_ALL_SCO_SET.add(-2147483640);
        DEVICE_IN_ALL_USB_SET = new java.util.HashSet();
        DEVICE_IN_ALL_USB_SET.add(-2147481600);
        DEVICE_IN_ALL_USB_SET.add(-2147479552);
        DEVICE_IN_ALL_USB_SET.add(java.lang.Integer.valueOf(DEVICE_IN_USB_HEADSET));
        DEVICE_IN_ALL_BLE_SET = new java.util.HashSet();
        DEVICE_IN_ALL_BLE_SET.add(-1610612736);
        DEFAULT_STREAM_VOLUME = new int[]{4, 7, 5, 5, 6, 5, 7, 7, 5, 5, 5, 5};
    }

    public static final int getNumStreamTypes() {
        return 12;
    }

    public static java.lang.String modeToString(int i) {
        switch (i) {
            case -2:
                return "MODE_INVALID";
            case -1:
                return "MODE_CURRENT";
            case 0:
                return "MODE_NORMAL";
            case 1:
                return "MODE_RINGTONE";
            case 2:
                return "MODE_IN_CALL";
            case 3:
                return "MODE_IN_COMMUNICATION";
            case 4:
                return "MODE_CALL_SCREENING";
            case 5:
                return "MODE_CALL_REDIRECT";
            case 6:
                return "MODE_COMMUNICATION_REDIRECT";
            default:
                return "unknown mode (" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static int audioFormatToBluetoothSourceCodec(int i) {
        switch (i) {
            case 67108864:
                return 1;
            case 134217728:
                return 6;
            case 520093696:
                return 0;
            case 536870912:
                return 2;
            case 553648128:
                return 3;
            case 587202560:
                return 4;
            case 721420288:
                return 5;
            default:
                android.util.Log.e(TAG, "Unknown audio format 0x" + java.lang.Integer.toHexString(i) + " for conversion to BT codec");
                return 1000000;
        }
    }

    public static int audioFormatToBluetoothLeAudioSourceCodec(int i) {
        switch (i) {
            case 721420288:
                return 0;
            default:
                android.util.Log.e(TAG, "Unknown audio format 0x" + java.lang.Integer.toHexString(i) + " for conversion to BT LE audio codec");
                return 1000000;
        }
    }

    public static int bluetoothA2dpCodecToAudioFormat(int i) {
        switch (i) {
            case 0:
                return 520093696;
            case 1:
                return 67108864;
            case 2:
                return 536870912;
            case 3:
                return 553648128;
            case 4:
                return 587202560;
            case 5:
                return 721420288;
            case 6:
                return 134217728;
            default:
                android.util.Log.e(TAG, "Unknown A2DP BT codec 0x" + java.lang.Integer.toHexString(i) + " for conversion to audio format");
                return 0;
        }
    }

    public static int bluetoothLeCodecToAudioFormat(int i) {
        switch (i) {
            case 0:
                return 721420288;
            default:
                android.util.Log.e(TAG, "Unknown LE Audio BT codec 0x" + java.lang.Integer.toHexString(i) + " for conversion to audio format");
                return 0;
        }
    }

    public static java.lang.String audioFormatToString(int i) {
        switch (i) {
            case -1:
                return "AUDIO_FORMAT_INVALID";
            case 0:
                return "AUDIO_FORMAT_DEFAULT";
            case 1:
                return "AUDIO_FORMAT_PCM_16_BIT";
            case 2:
                return "AUDIO_FORMAT_PCM_8_BIT";
            case 3:
                return "AUDIO_FORMAT_PCM_32_BIT";
            case 4:
                return "AUDIO_FORMAT_PCM_8_24_BIT";
            case 5:
                return "AUDIO_FORMAT_PCM_FLOAT";
            case 6:
                return "AUDIO_FORMAT_PCM_24_BIT_PACKED";
            case 16777216:
                return "AUDIO_FORMAT_MP3";
            case 33554432:
                return "AUDIO_FORMAT_AMR_NB";
            case 50331648:
                return "AUDIO_FORMAT_AMR_WB";
            case 67108864:
                return "AUDIO_FORMAT_AAC";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_MAIN /* 67108865 */:
                return "AUDIO_FORMAT_AAC_MAIN";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_LC /* 67108866 */:
                return "AUDIO_FORMAT_AAC_LC";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_SSR /* 67108868 */:
                return "AUDIO_FORMAT_AAC_SSR";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_LTP /* 67108872 */:
                return "AUDIO_FORMAT_AAC_LTP";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_HE_V1 /* 67108880 */:
                return "AUDIO_FORMAT_AAC_HE_V1";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_SCALABLE /* 67108896 */:
                return "AUDIO_FORMAT_AAC_SCALABLE";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ERLC /* 67108928 */:
                return "AUDIO_FORMAT_AAC_ERLC";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_LD /* 67108992 */:
                return "AUDIO_FORMAT_AAC_LD";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_HE_V2 /* 67109120 */:
                return "AUDIO_FORMAT_AAC_HE_V2";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ELD /* 67109376 */:
                return "AUDIO_FORMAT_AAC_ELD";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_XHE /* 67109632 */:
                return "AUDIO_FORMAT_AAC_XHE";
            case android.media.audio.Enums.AUDIO_FORMAT_HE_AAC_V1 /* 83886080 */:
                return "AUDIO_FORMAT_HE_AAC_V1";
            case 100663296:
                return "AUDIO_FORMAT_HE_AAC_V2";
            case android.media.audio.Enums.AUDIO_FORMAT_VORBIS /* 117440512 */:
                return "AUDIO_FORMAT_VORBIS";
            case 134217728:
                return "AUDIO_FORMAT_OPUS";
            case android.media.audio.Enums.AUDIO_FORMAT_AC3 /* 150994944 */:
                return "AUDIO_FORMAT_AC3";
            case android.media.audio.Enums.AUDIO_FORMAT_E_AC3 /* 167772160 */:
                return "AUDIO_FORMAT_E_AC3";
            case android.media.audio.Enums.AUDIO_FORMAT_E_AC3_JOC /* 167772161 */:
                return "AUDIO_FORMAT_E_AC3_JOC";
            case android.media.audio.Enums.AUDIO_FORMAT_DTS /* 184549376 */:
                return "AUDIO_FORMAT_DTS";
            case android.media.audio.Enums.AUDIO_FORMAT_DTS_HD /* 201326592 */:
                return "AUDIO_FORMAT_DTS_HD";
            case android.media.audio.Enums.AUDIO_FORMAT_IEC61937 /* 218103808 */:
                return "AUDIO_FORMAT_IEC61937";
            case android.media.audio.Enums.AUDIO_FORMAT_DOLBY_TRUEHD /* 234881024 */:
                return "AUDIO_FORMAT_DOLBY_TRUEHD";
            case 268435456:
                return "AUDIO_FORMAT_EVRC";
            case 285212672:
                return "AUDIO_FORMAT_EVRCB";
            case 301989888:
                return "AUDIO_FORMAT_EVRCWB";
            case 318767104:
                return "AUDIO_FORMAT_EVRCNW";
            case 335544320:
                return "AUDIO_FORMAT_AAC_ADIF";
            case 352321536:
                return "AUDIO_FORMAT_WMA";
            case 369098752:
                return "AUDIO_FORMAT_WMA_PRO";
            case 385875968:
                return "AUDIO_FORMAT_AMR_WB_PLUS";
            case 402653184:
                return "AUDIO_FORMAT_MP2";
            case 419430400:
                return "AUDIO_FORMAT_QCELP";
            case android.media.audio.Enums.AUDIO_FORMAT_DSD /* 436207616 */:
                return "AUDIO_FORMAT_DSD";
            case android.media.audio.Enums.AUDIO_FORMAT_FLAC /* 452984832 */:
                return "AUDIO_FORMAT_FLAC";
            case android.media.audio.Enums.AUDIO_FORMAT_ALAC /* 469762048 */:
                return "AUDIO_FORMAT_ALAC";
            case android.media.audio.Enums.AUDIO_FORMAT_APE /* 486539264 */:
                return "AUDIO_FORMAT_APE";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS /* 503316480 */:
                return "AUDIO_FORMAT_AAC_ADTS";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_MAIN /* 503316481 */:
                return "AUDIO_FORMAT_AAC_ADTS_MAIN";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_LC /* 503316482 */:
                return "AUDIO_FORMAT_AAC_ADTS_LC";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_SSR /* 503316484 */:
                return "AUDIO_FORMAT_AAC_ADTS_SSR";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_LTP /* 503316488 */:
                return "AUDIO_FORMAT_AAC_ADTS_LTP";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_HE_V1 /* 503316496 */:
                return "AUDIO_FORMAT_AAC_ADTS_HE_V1";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_SCALABLE /* 503316512 */:
                return "AUDIO_FORMAT_AAC_ADTS_SCALABLE";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_ERLC /* 503316544 */:
                return "AUDIO_FORMAT_AAC_ADTS_ERLC";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_LD /* 503316608 */:
                return "AUDIO_FORMAT_AAC_ADTS_LD";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_HE_V2 /* 503316736 */:
                return "AUDIO_FORMAT_AAC_ADTS_HE_V2";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_ELD /* 503316992 */:
                return "AUDIO_FORMAT_AAC_ADTS_ELD";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_ADTS_XHE /* 503317248 */:
                return "AUDIO_FORMAT_AAC_ADTS_XHE";
            case 520093696:
                return "AUDIO_FORMAT_SBC";
            case 536870912:
                return "AUDIO_FORMAT_APTX";
            case 553648128:
                return "AUDIO_FORMAT_APTX_HD";
            case android.media.audio.Enums.AUDIO_FORMAT_AC4 /* 570425344 */:
                return "AUDIO_FORMAT_AC4";
            case 587202560:
                return "AUDIO_FORMAT_LDAC";
            case android.media.audio.Enums.AUDIO_FORMAT_MAT /* 603979776 */:
                return "AUDIO_FORMAT_MAT";
            case android.media.audio.Enums.AUDIO_FORMAT_MAT_1_0 /* 603979777 */:
                return "AUDIO_FORMAT_MAT_1_0";
            case android.media.audio.Enums.AUDIO_FORMAT_MAT_2_0 /* 603979778 */:
                return "AUDIO_FORMAT_MAT_2_0";
            case android.media.audio.Enums.AUDIO_FORMAT_MAT_2_1 /* 603979779 */:
                return "AUDIO_FORMAT_MAT_2_1";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_LATM /* 620756992 */:
                return "AUDIO_FORMAT_AAC_LATM";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_LATM_LC /* 620756994 */:
                return "AUDIO_FORMAT_AAC_LATM_LC";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_LATM_HE_V1 /* 620757008 */:
                return "AUDIO_FORMAT_AAC_LATM_HE_V1";
            case android.media.audio.Enums.AUDIO_FORMAT_AAC_LATM_HE_V2 /* 620757248 */:
                return "AUDIO_FORMAT_AAC_LATM_HE_V2";
            case android.media.audio.Enums.AUDIO_FORMAT_CELT /* 637534208 */:
                return "AUDIO_FORMAT_CELT";
            case android.media.audio.Enums.AUDIO_FORMAT_APTX_ADAPTIVE /* 654311424 */:
                return "AUDIO_FORMAT_APTX_ADAPTIVE";
            case android.media.audio.Enums.AUDIO_FORMAT_LHDC /* 671088640 */:
                return "AUDIO_FORMAT_LHDC";
            case android.media.audio.Enums.AUDIO_FORMAT_LHDC_LL /* 687865856 */:
                return "AUDIO_FORMAT_LHDC_LL";
            case android.media.audio.Enums.AUDIO_FORMAT_APTX_TWSP /* 704643072 */:
                return "AUDIO_FORMAT_APTX_TWSP";
            case 721420288:
                return "AUDIO_FORMAT_LC3";
            case android.media.audio.Enums.AUDIO_FORMAT_MPEGH /* 738197504 */:
                return "AUDIO_FORMAT_MPEGH";
            case android.media.audio.Enums.AUDIO_FORMAT_MPEGH_BL_L3 /* 738197523 */:
                return "AUDIO_FORMAT_MPEGH_SUB_BL_L3";
            case android.media.audio.Enums.AUDIO_FORMAT_MPEGH_BL_L4 /* 738197524 */:
                return "AUDIO_FORMAT_MPEGH_SUB_BL_L4";
            case android.media.audio.Enums.AUDIO_FORMAT_MPEGH_LC_L3 /* 738197539 */:
                return "AUDIO_FORMAT_MPEGH_SUB_LC_L3";
            case android.media.audio.Enums.AUDIO_FORMAT_MPEGH_LC_L4 /* 738197540 */:
                return "AUDIO_FORMAT_MPEGH_SUB_LC_L4";
            case android.media.audio.Enums.AUDIO_FORMAT_IEC60958 /* 754974720 */:
                return "AUDIO_FORMAT_IEC60958";
            case android.media.audio.Enums.AUDIO_FORMAT_DTS_UHD /* 771751936 */:
                return "AUDIO_FORMAT_DTS_UHD";
            case android.media.audio.Enums.AUDIO_FORMAT_DRA /* 788529152 */:
                return "AUDIO_FORMAT_DRA";
            case 805306368:
                return "AUDIO_FORMAT_APTX_ADAPTIVE_QLEA";
            case android.media.audio.Enums.AUDIO_FORMAT_APTX_R4 /* 822083584 */:
                return "AUDIO_FORMAT_APTX_ADAPTIVE_R4";
            case android.media.audio.Enums.AUDIO_FORMAT_DTS_HD_MA /* 838860800 */:
                return "AUDIO_FORMAT_DTS_HD_MA";
            case android.media.audio.Enums.AUDIO_FORMAT_DTS_UHD_P2 /* 855638016 */:
                return "AUDIO_FORMAT_DTS_UHD_P2";
            default:
                return "AUDIO_FORMAT_(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static void setErrorCallback(android.media.AudioSystem.ErrorCallback errorCallback) {
        synchronized (android.media.AudioSystem.class) {
            sErrorCallback = errorCallback;
            if (errorCallback != null) {
                errorCallback.onError(checkAudioFlinger());
            }
        }
    }

    private static void errorCallbackFromNative(int i) {
        android.media.AudioSystem.ErrorCallback errorCallback;
        synchronized (android.media.AudioSystem.class) {
            errorCallback = sErrorCallback;
        }
        if (errorCallback != null) {
            errorCallback.onError(i);
        }
    }

    public static void setDynamicPolicyCallback(android.media.AudioSystem.DynamicPolicyCallback dynamicPolicyCallback) {
        synchronized (android.media.AudioSystem.class) {
            sDynPolicyCallback = dynamicPolicyCallback;
            native_register_dynamic_policy_callback();
        }
    }

    private static void dynamicPolicyCallbackFromNative(int i, java.lang.String str, int i2) {
        android.media.AudioSystem.DynamicPolicyCallback dynamicPolicyCallback;
        synchronized (android.media.AudioSystem.class) {
            dynamicPolicyCallback = sDynPolicyCallback;
        }
        if (dynamicPolicyCallback != null) {
            switch (i) {
                case 0:
                    dynamicPolicyCallback.onDynamicPolicyMixStateUpdate(str, i2);
                    return;
                default:
                    android.util.Log.e(TAG, "dynamicPolicyCallbackFromNative: unknown event " + i);
                    return;
            }
        }
    }

    public static void setRecordingCallback(android.media.AudioSystem.AudioRecordingCallback audioRecordingCallback) {
        synchronized (android.media.AudioSystem.class) {
            sRecordingCallback = audioRecordingCallback;
            native_register_recording_callback();
        }
    }

    private static void recordingCallbackFromNative(int i, int i2, int i3, int i4, int i5, int i6, boolean z, int[] iArr, android.media.audiofx.AudioEffect.Descriptor[] descriptorArr, android.media.audiofx.AudioEffect.Descriptor[] descriptorArr2, int i7) {
        android.media.AudioSystem.AudioRecordingCallback audioRecordingCallback;
        synchronized (android.media.AudioSystem.class) {
            audioRecordingCallback = sRecordingCallback;
        }
        boolean z2 = false;
        if (descriptorArr.length != 0) {
            java.lang.String str = descriptorArr[0].name;
        }
        if (descriptorArr2.length != 0) {
            java.lang.String str2 = descriptorArr2[0].name;
        }
        if (audioRecordingCallback != null) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (android.media.AudioManager.listAudioPatches(arrayList) == 0) {
                int i8 = iArr[6];
                java.util.Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else if (((android.media.AudioPatch) it.next()).id() == i8) {
                        z2 = true;
                        break;
                    }
                }
                if (!z2) {
                    android.media.AudioManager.resetAudioPortGeneration();
                }
            }
            audioRecordingCallback.onRecordingConfigurationChanged(i, i2, i3, i4, i5, i6, z, iArr, descriptorArr, descriptorArr2, i7, "");
        }
    }

    public static void setRoutingCallback(android.media.AudioSystem.RoutingUpdateCallback routingUpdateCallback) {
        synchronized (android.media.AudioSystem.class) {
            sRoutingUpdateCallback = routingUpdateCallback;
            native_register_routing_callback();
        }
    }

    private static void routingCallbackFromNative() {
        android.media.AudioSystem.RoutingUpdateCallback routingUpdateCallback;
        synchronized (android.media.AudioSystem.class) {
            routingUpdateCallback = sRoutingUpdateCallback;
        }
        if (routingUpdateCallback == null) {
            android.util.Log.e(TAG, "routing update from APM was not captured");
        } else {
            routingUpdateCallback.onRoutingUpdated();
        }
    }

    public static void setVolumeRangeInitRequestCallback(android.media.AudioSystem.VolumeRangeInitRequestCallback volumeRangeInitRequestCallback) {
        synchronized (android.media.AudioSystem.class) {
            sVolRangeInitReqCallback = volumeRangeInitRequestCallback;
            native_register_vol_range_init_req_callback();
        }
    }

    private static void volRangeInitReqCallbackFromNative() {
        android.media.AudioSystem.VolumeRangeInitRequestCallback volumeRangeInitRequestCallback;
        synchronized (android.media.AudioSystem.class) {
            volumeRangeInitRequestCallback = sVolRangeInitReqCallback;
        }
        if (volumeRangeInitRequestCallback == null) {
            android.util.Log.e(TAG, "APM requested volume range initialization, but no callback found");
        } else {
            volumeRangeInitRequestCallback.onVolumeRangeInitializationRequested();
        }
    }

    public static java.lang.String audioSystemErrorToString(int i) {
        switch (i) {
            case -7:
                return "WOULD_BLOCK";
            case -6:
                return "DEAD_OBJECT";
            case -5:
                return "NO_INIT";
            case -4:
                return "PERMISSION_DENIED";
            case -3:
                return "INVALID_OPERATION";
            case -2:
                return "BAD_VALUE";
            case -1:
                return android.service.timezone.TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY;
            case 0:
                return android.service.timezone.TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY;
            default:
                return "unknown error:" + i;
        }
    }

    public static boolean isInputDevice(int i) {
        return (i & Integer.MIN_VALUE) == Integer.MIN_VALUE;
    }

    public static boolean isBluetoothDevice(int i) {
        return isBluetoothA2dpOutDevice(i) || isBluetoothScoDevice(i) || isBluetoothLeDevice(i);
    }

    public static boolean isBluetoothOutDevice(int i) {
        return isBluetoothA2dpOutDevice(i) || isBluetoothScoOutDevice(i) || isBluetoothLeOutDevice(i);
    }

    public static boolean isBluetoothInDevice(int i) {
        return isBluetoothScoInDevice(i) || isBluetoothLeInDevice(i);
    }

    public static boolean isBluetoothA2dpOutDevice(int i) {
        return DEVICE_OUT_ALL_A2DP_SET.contains(java.lang.Integer.valueOf(i));
    }

    public static boolean isBluetoothScoOutDevice(int i) {
        return DEVICE_OUT_ALL_SCO_SET.contains(java.lang.Integer.valueOf(i));
    }

    public static boolean isBluetoothScoInDevice(int i) {
        return DEVICE_IN_ALL_SCO_SET.contains(java.lang.Integer.valueOf(i));
    }

    public static boolean isBluetoothScoDevice(int i) {
        return isBluetoothScoOutDevice(i) || isBluetoothScoInDevice(i);
    }

    public static boolean isBluetoothLeOutDevice(int i) {
        return DEVICE_OUT_ALL_BLE_SET.contains(java.lang.Integer.valueOf(i));
    }

    public static boolean isBluetoothLeInDevice(int i) {
        return DEVICE_IN_ALL_BLE_SET.contains(java.lang.Integer.valueOf(i));
    }

    public static boolean isBluetoothLeDevice(int i) {
        return isBluetoothLeOutDevice(i) || isBluetoothLeInDevice(i);
    }

    public static boolean isRemoteSubmixDevice(int i) {
        return i == -2147483392 || i == 32768;
    }

    public static java.lang.String deviceStateToString(int i) {
        switch (i) {
            case 0:
                return "DEVICE_STATE_UNAVAILABLE";
            case 1:
                return "DEVICE_STATE_AVAILABLE";
            default:
                return "unknown state (" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static java.lang.String getOutputDeviceName(int i) {
        switch (i) {
            case 1:
                return DEVICE_OUT_EARPIECE_NAME;
            case 2:
                return DEVICE_OUT_SPEAKER_NAME;
            case 4:
                return "headset";
            case 8:
                return DEVICE_OUT_WIRED_HEADPHONE_NAME;
            case 16:
                return DEVICE_OUT_BLUETOOTH_SCO_NAME;
            case 32:
                return "bt_sco_hs";
            case 64:
                return DEVICE_OUT_BLUETOOTH_SCO_CARKIT_NAME;
            case 128:
                return "bt_a2dp";
            case 256:
                return DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES_NAME;
            case 512:
                return DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER_NAME;
            case 1024:
                return "hdmi";
            case 2048:
                return "analog_dock";
            case 4096:
                return "digital_dock";
            case 8192:
                return "usb_accessory";
            case 16384:
                return "usb_device";
            case 32768:
                return "remote_submix";
            case 65536:
                return DEVICE_OUT_TELEPHONY_TX_NAME;
            case 131072:
                return "line";
            case 262144:
                return "hdmi_arc";
            case 262145:
                return "hdmi_earc";
            case 524288:
                return "spdif";
            case 1048576:
                return DEVICE_OUT_FM_NAME;
            case 2097152:
                return DEVICE_OUT_AUX_LINE_NAME;
            case 4194304:
                return DEVICE_OUT_SPEAKER_SAFE_NAME;
            case 8388608:
                return "ip";
            case 16777216:
                return "bus";
            case 33554432:
                return "proxy";
            case 67108864:
                return "usb_headset";
            case 134217728:
                return DEVICE_OUT_HEARING_AID_NAME;
            case 268435456:
                return DEVICE_OUT_ECHO_CANCELLER_NAME;
            case 536870912:
                return "ble_headset";
            case 536870913:
                return DEVICE_OUT_BLE_SPEAKER_NAME;
            case 536870914:
                return DEVICE_OUT_BLE_BROADCAST_NAME;
            default:
                return "0x" + java.lang.Integer.toHexString(i);
        }
    }

    public static java.lang.String getInputDeviceName(int i) {
        switch (i) {
            case DEVICE_IN_COMMUNICATION /* -2147483647 */:
                return DEVICE_IN_COMMUNICATION_NAME;
            case DEVICE_IN_AMBIENT /* -2147483646 */:
                return DEVICE_IN_AMBIENT_NAME;
            case -2147483644:
                return DEVICE_IN_BUILTIN_MIC_NAME;
            case -2147483640:
                return "bt_sco_hs";
            case -2147483632:
                return "headset";
            case -2147483616:
                return "aux_digital";
            case -2147483584:
                return DEVICE_IN_TELEPHONY_RX_NAME;
            case -2147483520:
                return DEVICE_IN_BACK_MIC_NAME;
            case DEVICE_IN_REMOTE_SUBMIX /* -2147483392 */:
                return "remote_submix";
            case -2147483136:
                return "analog_dock";
            case -2147482624:
                return "digital_dock";
            case -2147481600:
                return "usb_accessory";
            case -2147479552:
                return "usb_device";
            case -2147475456:
                return DEVICE_IN_FM_TUNER_NAME;
            case -2147467264:
                return DEVICE_IN_TV_TUNER_NAME;
            case -2147450880:
                return "line";
            case -2147418112:
                return "spdif";
            case DEVICE_IN_BLUETOOTH_A2DP /* -2147352576 */:
                return "bt_a2dp";
            case -2147221504:
                return DEVICE_IN_LOOPBACK_NAME;
            case DEVICE_IN_IP /* -2146959360 */:
                return "ip";
            case DEVICE_IN_BUS /* -2146435072 */:
                return "bus";
            case DEVICE_IN_PROXY /* -2130706432 */:
                return "proxy";
            case DEVICE_IN_USB_HEADSET /* -2113929216 */:
                return "usb_headset";
            case DEVICE_IN_BLUETOOTH_BLE /* -2080374784 */:
                return DEVICE_IN_BLUETOOTH_BLE_NAME;
            case -2013265920:
                return "hdmi_arc";
            case -2013265919:
                return "hdmi_earc";
            case -1879048192:
                return DEVICE_IN_ECHO_REFERENCE_NAME;
            case -1610612736:
                return "ble_headset";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static java.lang.String getDeviceName(int i) {
        if (isInputDevice(i)) {
            return getInputDeviceName(i);
        }
        return getOutputDeviceName(i);
    }

    public static java.lang.String forceUseConfigToString(int i) {
        switch (i) {
            case 0:
                return "FORCE_NONE";
            case 1:
                return "FORCE_SPEAKER";
            case 2:
                return "FORCE_HEADPHONES";
            case 3:
                return "FORCE_BT_SCO";
            case 4:
                return "FORCE_BT_A2DP";
            case 5:
                return "FORCE_WIRED_ACCESSORY";
            case 6:
                return "FORCE_BT_CAR_DOCK";
            case 7:
                return "FORCE_BT_DESK_DOCK";
            case 8:
                return "FORCE_ANALOG_DOCK";
            case 9:
                return "FORCE_DIGITAL_DOCK";
            case 10:
                return "FORCE_NO_BT_A2DP";
            case 11:
                return "FORCE_SYSTEM_ENFORCED";
            case 12:
                return "FORCE_HDMI_SYSTEM_AUDIO_ENFORCED";
            case 13:
                return "FORCE_ENCODED_SURROUND_NEVER";
            case 14:
                return "FORCE_ENCODED_SURROUND_ALWAYS";
            case 15:
                return "FORCE_ENCODED_SURROUND_MANUAL";
            default:
                return "unknown config (" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static java.lang.String forceUseUsageToString(int i) {
        switch (i) {
            case 0:
                return "FOR_COMMUNICATION";
            case 1:
                return "FOR_MEDIA";
            case 2:
                return "FOR_RECORD";
            case 3:
                return "FOR_DOCK";
            case 4:
                return "FOR_SYSTEM";
            case 5:
                return "FOR_HDMI_SYSTEM_AUDIO";
            case 6:
                return "FOR_ENCODED_SURROUND";
            case 7:
                return "FOR_VIBRATE_RINGING";
            default:
                return "unknown usage (" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static int setStreamVolumeIndexAS(int i, int i2, int i3) {
        return setStreamVolumeIndex(i, i2, i3);
    }

    public static int setDeviceConnectionState(android.media.AudioDeviceAttributes audioDeviceAttributes, int i, int i2) {
        android.media.audio.common.AudioPort api2aidl_AudioDeviceAttributes_AudioPort = android.media.audio.common.AidlConversion.api2aidl_AudioDeviceAttributes_AudioPort(audioDeviceAttributes);
        android.os.Parcel obtain = android.os.Parcel.obtain();
        api2aidl_AudioDeviceAttributes_AudioPort.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        try {
            return setDeviceConnectionState(i, obtain, i2);
        } finally {
            obtain.recycle();
        }
    }

    public static int setPhoneState(int i) {
        android.util.Log.w(TAG, "Do not use this method! Use AudioManager.setMode() instead.");
        return 0;
    }

    @java.lang.Deprecated
    public static int getDevicesForStream(int i) {
        return getDeviceMaskFromSet(generateAudioDeviceTypesSet(getDevicesForAttributes(android.media.audiopolicy.AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(i), true)));
    }

    public static int getDeviceMaskFromSet(java.util.Set<java.lang.Integer> set) {
        int i = 0;
        int i2 = Integer.MIN_VALUE;
        for (java.lang.Integer num : set) {
            if ((num.intValue() & (num.intValue() - 1) & Integer.MAX_VALUE) != 0) {
                android.util.Log.v(TAG, "getDeviceMaskFromSet skipping multi-bit device value " + num);
            } else {
                i |= num.intValue();
                i2 &= num.intValue();
            }
        }
        if (!set.isEmpty() && i2 != (i & Integer.MIN_VALUE)) {
            android.util.Log.e(TAG, "getDeviceMaskFromSet: Invalid set: " + deviceSetToString(set));
        }
        return i;
    }

    public static java.lang.String deviceSetToString(java.util.Set<java.lang.Integer> set) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int i = 0;
        for (java.lang.Integer num : set) {
            int i2 = i + 1;
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(getDeviceName(num.intValue()));
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + java.lang.Integer.toHexString(num.intValue()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            i = i2;
        }
        return sb.toString();
    }

    public static java.util.ArrayList<android.media.AudioDeviceAttributes> getDevicesForAttributes(android.media.AudioAttributes audioAttributes, boolean z) {
        java.util.Objects.requireNonNull(audioAttributes);
        android.media.AudioDeviceAttributes[] audioDeviceAttributesArr = new android.media.AudioDeviceAttributes[4];
        int devicesForAttributes = getDevicesForAttributes(audioAttributes, audioDeviceAttributesArr, z);
        java.util.ArrayList<android.media.AudioDeviceAttributes> arrayList = new java.util.ArrayList<>();
        if (devicesForAttributes != 0) {
            android.util.Log.e(TAG, "error " + devicesForAttributes + " in getDevicesForAttributes attributes: " + audioAttributes + " forVolume: " + z);
            return arrayList;
        }
        for (int i = 0; i < 4; i++) {
            android.media.AudioDeviceAttributes audioDeviceAttributes = audioDeviceAttributesArr[i];
            if (audioDeviceAttributes != null) {
                arrayList.add(audioDeviceAttributes);
            }
        }
        return arrayList;
    }

    static int getOffloadSupport(android.media.AudioFormat audioFormat, android.media.AudioAttributes audioAttributes) {
        return native_get_offload_support(audioFormat.getEncoding(), audioFormat.getSampleRate(), audioFormat.getChannelMask(), audioFormat.getChannelIndexMask(), audioAttributes.getVolumeControlStream());
    }

    public static int setDevicesRoleForStrategy(int i, int i2, java.util.List<android.media.AudioDeviceAttributes> list) {
        if (list.isEmpty()) {
            return -2;
        }
        int[] iArr = new int[list.size()];
        java.lang.String[] strArr = new java.lang.String[list.size()];
        for (int i3 = 0; i3 < list.size(); i3++) {
            iArr[i3] = list.get(i3).getInternalType();
            strArr[i3] = list.get(i3).getAddress();
        }
        return setDevicesRoleForStrategy(i, i2, iArr, strArr);
    }

    public static int removeDevicesRoleForStrategy(int i, int i2, java.util.List<android.media.AudioDeviceAttributes> list) {
        if (list.isEmpty()) {
            return -2;
        }
        int[] iArr = new int[list.size()];
        java.lang.String[] strArr = new java.lang.String[list.size()];
        for (int i3 = 0; i3 < list.size(); i3++) {
            iArr[i3] = list.get(i3).getInternalType();
            strArr[i3] = list.get(i3).getAddress();
        }
        return removeDevicesRoleForStrategy(i, i2, iArr, strArr);
    }

    private static android.util.Pair<int[], java.lang.String[]> populateInputDevicesTypeAndAddress(java.util.List<android.media.AudioDeviceAttributes> list) {
        int[] iArr = new int[list.size()];
        java.lang.String[] strArr = new java.lang.String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            iArr[i] = list.get(i).getInternalType();
            if (iArr[i] == 0) {
                iArr[i] = android.media.AudioDeviceInfo.convertDeviceTypeToInternalInputDevice(list.get(i).getType(), list.get(i).getAddress());
            }
            strArr[i] = list.get(i).getAddress();
        }
        return new android.util.Pair<>(iArr, strArr);
    }

    public static int setDevicesRoleForCapturePreset(int i, int i2, java.util.List<android.media.AudioDeviceAttributes> list) {
        if (list.isEmpty()) {
            return -2;
        }
        android.util.Pair<int[], java.lang.String[]> populateInputDevicesTypeAndAddress = populateInputDevicesTypeAndAddress(list);
        return setDevicesRoleForCapturePreset(i, i2, populateInputDevicesTypeAndAddress.first, populateInputDevicesTypeAndAddress.second);
    }

    public static int addDevicesRoleForCapturePreset(int i, int i2, java.util.List<android.media.AudioDeviceAttributes> list) {
        if (list.isEmpty()) {
            return -2;
        }
        android.util.Pair<int[], java.lang.String[]> populateInputDevicesTypeAndAddress = populateInputDevicesTypeAndAddress(list);
        return addDevicesRoleForCapturePreset(i, i2, populateInputDevicesTypeAndAddress.first, populateInputDevicesTypeAndAddress.second);
    }

    public static int removeDevicesRoleForCapturePreset(int i, int i2, java.util.List<android.media.AudioDeviceAttributes> list) {
        if (list.isEmpty()) {
            return -2;
        }
        android.util.Pair<int[], java.lang.String[]> populateInputDevicesTypeAndAddress = populateInputDevicesTypeAndAddress(list);
        return removeDevicesRoleForCapturePreset(i, i2, populateInputDevicesTypeAndAddress.first, populateInputDevicesTypeAndAddress.second);
    }

    public static android.media.ISpatializer getSpatializer(android.media.INativeSpatializerCallback iNativeSpatializerCallback) {
        return android.media.ISpatializer.Stub.asInterface(nativeGetSpatializer(iNativeSpatializerCallback));
    }

    public static android.media.ISoundDose getSoundDoseInterface(android.media.ISoundDoseCallback iSoundDoseCallback) {
        return android.media.ISoundDose.Stub.asInterface(nativeGetSoundDose(iSoundDoseCallback));
    }

    public static int getValueForVibrateSetting(int i, int i2, int i3) {
        int i4 = i2 * 2;
        return (i & (~(3 << i4))) | ((i3 & 3) << i4);
    }

    public static int getDefaultStreamVolume(int i) {
        return DEFAULT_STREAM_VOLUME[i];
    }

    public static java.lang.String streamToString(int i) {
        return (i < 0 || i >= STREAM_NAMES.length) ? i == Integer.MIN_VALUE ? "USE_DEFAULT_STREAM_TYPE" : "UNKNOWN_STREAM_" + i : STREAM_NAMES[i];
    }

    public static int getPlatformType(android.content.Context context) {
        if (context.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_AUTOMOTIVE)) {
            return 3;
        }
        if (((android.telephony.TelephonyManager) context.getSystemService(android.telephony.TelephonyManager.class)).isVoiceCapable()) {
            return 1;
        }
        if (context.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_LEANBACK)) {
            return 2;
        }
        return 0;
    }

    public static boolean isSingleVolume(android.content.Context context) {
        return getPlatformType(context) == 2 || context.getResources().getBoolean(com.android.internal.R.bool.config_single_volume);
    }

    public static java.util.Set<java.lang.Integer> generateAudioDeviceTypesSet(java.util.List<android.media.AudioDeviceAttributes> list) {
        java.util.TreeSet treeSet = new java.util.TreeSet();
        java.util.Iterator<android.media.AudioDeviceAttributes> it = list.iterator();
        while (it.hasNext()) {
            treeSet.add(java.lang.Integer.valueOf(it.next().getInternalType()));
        }
        return treeSet;
    }

    public static java.util.Set<java.lang.Integer> intersectionAudioDeviceTypes(java.util.Set<java.lang.Integer> set, java.util.Set<java.lang.Integer> set2) {
        java.util.TreeSet treeSet = new java.util.TreeSet(set);
        treeSet.retainAll(set2);
        return treeSet;
    }

    public static boolean isSingleAudioDeviceType(java.util.Set<java.lang.Integer> set, int i) {
        return set.size() == 1 && set.contains(java.lang.Integer.valueOf(i));
    }

    public static boolean isLeAudioDeviceType(int i) {
        return DEVICE_OUT_ALL_BLE_SET.contains(java.lang.Integer.valueOf(i));
    }
}
