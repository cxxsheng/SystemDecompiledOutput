package android.media;

/* loaded from: classes2.dex */
public class AudioManager {
    public static final java.lang.String ACTION_AUDIO_BECOMING_NOISY = "android.media.AUDIO_BECOMING_NOISY";
    public static final java.lang.String ACTION_HDMI_AUDIO_PLUG = "android.media.action.HDMI_AUDIO_PLUG";
    public static final java.lang.String ACTION_HEADSET_PLUG = "android.intent.action.HEADSET_PLUG";
    public static final java.lang.String ACTION_MICROPHONE_MUTE_CHANGED = "android.media.action.MICROPHONE_MUTE_CHANGED";

    @java.lang.Deprecated
    public static final java.lang.String ACTION_SCO_AUDIO_STATE_CHANGED = "android.media.SCO_AUDIO_STATE_CHANGED";
    public static final java.lang.String ACTION_SCO_AUDIO_STATE_UPDATED = "android.media.ACTION_SCO_AUDIO_STATE_UPDATED";
    public static final java.lang.String ACTION_SPEAKERPHONE_STATE_CHANGED = "android.media.action.SPEAKERPHONE_STATE_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_VOLUME_CHANGED = "android.media.VOLUME_CHANGED_ACTION";
    public static final int ADJUST_LOWER = -1;
    public static final int ADJUST_MUTE = -100;
    public static final int ADJUST_RAISE = 1;
    public static final int ADJUST_SAME = 0;
    public static final int ADJUST_TOGGLE_MUTE = 101;
    public static final int ADJUST_UNMUTE = 100;
    public static final int AUDIOFOCUS_FLAGS_APPS = 3;
    public static final int AUDIOFOCUS_FLAGS_SYSTEM = 7;

    @android.annotation.SystemApi
    public static final int AUDIOFOCUS_FLAG_DELAY_OK = 1;

    @android.annotation.SystemApi
    public static final int AUDIOFOCUS_FLAG_LOCK = 4;

    @android.annotation.SystemApi
    public static final int AUDIOFOCUS_FLAG_PAUSES_ON_DUCKABLE_LOSS = 2;
    public static final int AUDIOFOCUS_FLAG_TEST = 8;
    public static final int AUDIOFOCUS_GAIN = 1;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT = 2;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE = 4;
    public static final int AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK = 3;
    public static final int AUDIOFOCUS_LOSS = -1;
    public static final int AUDIOFOCUS_LOSS_TRANSIENT = -2;
    public static final int AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK = -3;
    public static final int AUDIOFOCUS_NONE = 0;
    public static final int AUDIOFOCUS_REQUEST_DELAYED = 2;
    public static final int AUDIOFOCUS_REQUEST_FAILED = 0;
    public static final int AUDIOFOCUS_REQUEST_GRANTED = 1;
    public static final int AUDIOFOCUS_REQUEST_WAITING_FOR_EXT_POLICY = 100;
    private static final int AUDIOPORT_GENERATION_INIT = 0;
    public static final int AUDIO_DEVICE_CATEGORY_CARKIT = 4;
    public static final int AUDIO_DEVICE_CATEGORY_HEADPHONES = 3;
    public static final int AUDIO_DEVICE_CATEGORY_HEARING_AID = 6;
    public static final int AUDIO_DEVICE_CATEGORY_OTHER = 1;
    public static final int AUDIO_DEVICE_CATEGORY_RECEIVER = 7;
    public static final int AUDIO_DEVICE_CATEGORY_SPEAKER = 2;
    public static final int AUDIO_DEVICE_CATEGORY_UNKNOWN = 0;
    public static final int AUDIO_DEVICE_CATEGORY_WATCH = 5;
    public static final int AUDIO_SESSION_ID_GENERATE = 0;
    public static final long CALL_REDIRECTION_AUDIO_MODES = 189472651;
    public static final int CALL_REDIRECT_NONE = 0;
    public static final int CALL_REDIRECT_PSTN = 1;
    public static final int CALL_REDIRECT_VOIP = 2;
    public static final int CSD_WARNING_ACCUMULATION_START = 4;
    public static final int CSD_WARNING_DOSE_REACHED_1X = 1;
    public static final int CSD_WARNING_DOSE_REPEATED_5X = 2;
    public static final int CSD_WARNING_MOMENTARY_EXPOSURE = 3;
    private static final boolean DEBUG = false;
    public static final int DEVICE_IN_ANLG_DOCK_HEADSET = -2147483136;
    public static final int DEVICE_IN_BACK_MIC = -2147483520;
    public static final int DEVICE_IN_BLE_HEADSET = -1610612736;
    public static final int DEVICE_IN_BLUETOOTH_SCO_HEADSET = -2147483640;
    public static final int DEVICE_IN_BUILTIN_MIC = -2147483644;
    public static final int DEVICE_IN_DGTL_DOCK_HEADSET = -2147482624;
    public static final int DEVICE_IN_ECHO_REFERENCE = -1879048192;
    public static final int DEVICE_IN_FM_TUNER = -2147475456;
    public static final int DEVICE_IN_HDMI = -2147483616;
    public static final int DEVICE_IN_HDMI_ARC = -2013265920;
    public static final int DEVICE_IN_HDMI_EARC = -2013265919;
    public static final int DEVICE_IN_LINE = -2147450880;
    public static final int DEVICE_IN_LOOPBACK = -2147221504;
    public static final int DEVICE_IN_SPDIF = -2147418112;
    public static final int DEVICE_IN_TELEPHONY_RX = -2147483584;
    public static final int DEVICE_IN_TV_TUNER = -2147467264;
    public static final int DEVICE_IN_USB_ACCESSORY = -2147481600;
    public static final int DEVICE_IN_USB_DEVICE = -2147479552;
    public static final int DEVICE_IN_WIRED_HEADSET = -2147483632;
    public static final int DEVICE_NONE = 0;
    public static final int DEVICE_OUT_ANLG_DOCK_HEADSET = 2048;
    public static final int DEVICE_OUT_AUX_DIGITAL = 1024;
    public static final int DEVICE_OUT_BLE_BROADCAST = 536870914;
    public static final int DEVICE_OUT_BLE_HEADSET = 536870912;
    public static final int DEVICE_OUT_BLE_SPEAKER = 536870913;
    public static final int DEVICE_OUT_BLUETOOTH_A2DP = 128;
    public static final int DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES = 256;
    public static final int DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER = 512;
    public static final int DEVICE_OUT_BLUETOOTH_SCO = 16;
    public static final int DEVICE_OUT_BLUETOOTH_SCO_CARKIT = 64;
    public static final int DEVICE_OUT_BLUETOOTH_SCO_HEADSET = 32;
    public static final int DEVICE_OUT_DEFAULT = 1073741824;
    public static final int DEVICE_OUT_DGTL_DOCK_HEADSET = 4096;
    public static final int DEVICE_OUT_EARPIECE = 1;
    public static final int DEVICE_OUT_ECHO_CANCELLER = 268435456;
    public static final int DEVICE_OUT_FM = 1048576;
    public static final int DEVICE_OUT_HDMI = 1024;
    public static final int DEVICE_OUT_HDMI_ARC = 262144;
    public static final int DEVICE_OUT_HDMI_EARC = 262145;
    public static final int DEVICE_OUT_LINE = 131072;
    public static final int DEVICE_OUT_REMOTE_SUBMIX = 32768;
    public static final int DEVICE_OUT_SPDIF = 524288;
    public static final int DEVICE_OUT_SPEAKER = 2;
    public static final int DEVICE_OUT_TELEPHONY_TX = 65536;
    public static final int DEVICE_OUT_USB_ACCESSORY = 8192;
    public static final int DEVICE_OUT_USB_DEVICE = 16384;
    public static final int DEVICE_OUT_USB_HEADSET = 67108864;
    public static final int DEVICE_OUT_WIRED_HEADPHONE = 8;
    public static final int DEVICE_OUT_WIRED_HEADSET = 4;

    @android.annotation.SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_ABSOLUTE = 3;

    @android.annotation.SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_ABSOLUTE_ADJUST_ONLY = 5;

    @android.annotation.SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_ABSOLUTE_MULTI_MODE = 4;

    @android.annotation.SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_FIXED = 2;

    @android.annotation.SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_FULL = 1;
    public static final int DEVICE_VOLUME_BEHAVIOR_UNSET = -1;

    @android.annotation.SystemApi
    public static final int DEVICE_VOLUME_BEHAVIOR_VARIABLE = 0;
    public static final int DIRECT_PLAYBACK_BITSTREAM_SUPPORTED = 4;
    public static final int DIRECT_PLAYBACK_NOT_SUPPORTED = 0;
    public static final int DIRECT_PLAYBACK_OFFLOAD_GAPLESS_SUPPORTED = 3;
    public static final int DIRECT_PLAYBACK_OFFLOAD_SUPPORTED = 1;
    public static final int ENCODED_SURROUND_OUTPUT_ALWAYS = 2;
    public static final int ENCODED_SURROUND_OUTPUT_AUTO = 0;
    public static final int ENCODED_SURROUND_OUTPUT_MANUAL = 3;
    public static final int ENCODED_SURROUND_OUTPUT_NEVER = 1;
    public static final int ENCODED_SURROUND_OUTPUT_UNKNOWN = -1;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -2;
    public static final int ERROR_DEAD_OBJECT = -6;
    public static final int ERROR_INVALID_OPERATION = -3;
    public static final int ERROR_NO_INIT = -5;
    public static final int ERROR_PERMISSION_DENIED = -4;
    public static final java.lang.String EXTRA_AUDIO_PLUG_STATE = "android.media.extra.AUDIO_PLUG_STATE";
    public static final java.lang.String EXTRA_ENCODINGS = "android.media.extra.ENCODINGS";
    public static final java.lang.String EXTRA_MASTER_VOLUME_MUTED = "android.media.EXTRA_MASTER_VOLUME_MUTED";
    public static final java.lang.String EXTRA_MAX_CHANNEL_COUNT = "android.media.extra.MAX_CHANNEL_COUNT";
    public static final java.lang.String EXTRA_PREV_VOLUME_STREAM_DEVICES = "android.media.EXTRA_PREV_VOLUME_STREAM_DEVICES";
    public static final java.lang.String EXTRA_PREV_VOLUME_STREAM_VALUE = "android.media.EXTRA_PREV_VOLUME_STREAM_VALUE";
    public static final java.lang.String EXTRA_RINGER_MODE = "android.media.EXTRA_RINGER_MODE";
    public static final java.lang.String EXTRA_SCO_AUDIO_PREVIOUS_STATE = "android.media.extra.SCO_AUDIO_PREVIOUS_STATE";
    public static final java.lang.String EXTRA_SCO_AUDIO_STATE = "android.media.extra.SCO_AUDIO_STATE";
    public static final java.lang.String EXTRA_STREAM_VOLUME_MUTED = "android.media.EXTRA_STREAM_VOLUME_MUTED";
    public static final java.lang.String EXTRA_VIBRATE_SETTING = "android.media.EXTRA_VIBRATE_SETTING";
    public static final java.lang.String EXTRA_VIBRATE_TYPE = "android.media.EXTRA_VIBRATE_TYPE";
    public static final java.lang.String EXTRA_VOLUME_STREAM_DEVICES = "android.media.EXTRA_VOLUME_STREAM_DEVICES";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
    public static final java.lang.String EXTRA_VOLUME_STREAM_TYPE_ALIAS = "android.media.EXTRA_VOLUME_STREAM_TYPE_ALIAS";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_VOLUME_STREAM_VALUE = "android.media.EXTRA_VOLUME_STREAM_VALUE";
    private static final int EXT_FOCUS_POLICY_TIMEOUT_MS = 250;
    public static final int FLAG_ABSOLUTE_VOLUME = 8192;
    public static final int FLAG_ACTIVE_MEDIA_ONLY = 512;
    public static final int FLAG_ALLOW_RINGER_MODES = 2;

    @android.annotation.SystemApi
    public static final int FLAG_BLUETOOTH_ABS_VOLUME = 64;
    public static final int FLAG_FIXED_VOLUME = 32;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int FLAG_FROM_KEY = 4096;
    public static final int FLAG_HDMI_SYSTEM_AUDIO_VOLUME = 256;
    public static final int FLAG_PLAY_SOUND = 4;
    public static final int FLAG_REMOVE_SOUND_AND_VIBRATE = 8;
    public static final int FLAG_SHOW_SILENT_HINT = 128;
    public static final int FLAG_SHOW_UI = 1;
    public static final int FLAG_SHOW_UI_WARNINGS = 1024;
    public static final int FLAG_SHOW_VIBRATE_HINT = 2048;
    public static final int FLAG_VIBRATE = 16;
    private static final java.lang.String FOCUS_CLIENT_ID_STRING = "android_audio_focus_client_id";
    public static final int FX_BACK = 10;
    public static final int FX_FOCUS_NAVIGATION_DOWN = 2;
    public static final int FX_FOCUS_NAVIGATION_LEFT = 3;
    public static final int FX_FOCUS_NAVIGATION_REPEAT_1 = 12;
    public static final int FX_FOCUS_NAVIGATION_REPEAT_2 = 13;
    public static final int FX_FOCUS_NAVIGATION_REPEAT_3 = 14;
    public static final int FX_FOCUS_NAVIGATION_REPEAT_4 = 15;
    public static final int FX_FOCUS_NAVIGATION_RIGHT = 4;
    public static final int FX_FOCUS_NAVIGATION_UP = 1;
    public static final int FX_HOME = 11;
    public static final int FX_KEYPRESS_DELETE = 7;
    public static final int FX_KEYPRESS_INVALID = 9;
    public static final int FX_KEYPRESS_RETURN = 8;
    public static final int FX_KEYPRESS_SPACEBAR = 6;
    public static final int FX_KEYPRESS_STANDARD = 5;
    public static final int FX_KEY_CLICK = 0;
    public static final int GET_DEVICES_ALL = 3;
    public static final int GET_DEVICES_INPUTS = 1;
    public static final int GET_DEVICES_OUTPUTS = 2;
    public static final java.lang.String INTERNAL_RINGER_MODE_CHANGED_ACTION = "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION";
    public static final java.lang.String MASTER_MUTE_CHANGED_ACTION = "android.media.MASTER_MUTE_CHANGED_ACTION";
    public static final int MODE_CALL_REDIRECT = 5;
    public static final int MODE_CALL_SCREENING = 4;
    public static final int MODE_COMMUNICATION_REDIRECT = 6;
    public static final int MODE_CURRENT = -1;
    public static final int MODE_INVALID = -2;
    public static final int MODE_IN_CALL = 2;
    public static final int MODE_IN_COMMUNICATION = 3;
    public static final int MODE_NORMAL = 0;
    public static final int MODE_RINGTONE = 1;
    private static final int MSG_DEVICES_CALLBACK_REGISTERED = 0;
    private static final int MSG_DEVICES_DEVICES_ADDED = 1;
    private static final int MSG_DEVICES_DEVICES_REMOVED = 2;
    private static final int MSSG_FOCUS_CHANGE = 0;
    private static final int MSSG_PLAYBACK_CONFIG_CHANGE = 2;
    private static final int MSSG_RECORDING_CONFIG_CHANGE = 1;
    public static final int NUM_NAVIGATION_REPEAT_SOUND_EFFECTS = 4;
    public static final int NUM_SOUND_EFFECTS = 16;

    @java.lang.Deprecated
    public static final int NUM_STREAMS = 5;
    public static final int PLAYBACK_OFFLOAD_GAPLESS_SUPPORTED = 2;
    public static final int PLAYBACK_OFFLOAD_NOT_SUPPORTED = 0;
    public static final int PLAYBACK_OFFLOAD_SUPPORTED = 1;
    public static final java.lang.String PROPERTY_OUTPUT_FRAMES_PER_BUFFER = "android.media.property.OUTPUT_FRAMES_PER_BUFFER";
    public static final java.lang.String PROPERTY_OUTPUT_SAMPLE_RATE = "android.media.property.OUTPUT_SAMPLE_RATE";
    public static final java.lang.String PROPERTY_SUPPORT_AUDIO_SOURCE_UNPROCESSED = "android.media.property.SUPPORT_AUDIO_SOURCE_UNPROCESSED";
    public static final java.lang.String PROPERTY_SUPPORT_MIC_NEAR_ULTRASOUND = "android.media.property.SUPPORT_MIC_NEAR_ULTRASOUND";
    public static final java.lang.String PROPERTY_SUPPORT_SPEAKER_NEAR_ULTRASOUND = "android.media.property.SUPPORT_SPEAKER_NEAR_ULTRASOUND";
    public static final int RECORDER_STATE_STARTED = 0;
    public static final int RECORDER_STATE_STOPPED = 1;
    public static final int RECORD_CONFIG_EVENT_NONE = -1;
    public static final int RECORD_CONFIG_EVENT_RELEASE = 3;
    public static final int RECORD_CONFIG_EVENT_START = 0;
    public static final int RECORD_CONFIG_EVENT_STOP = 1;
    public static final int RECORD_CONFIG_EVENT_UPDATE = 2;
    public static final int RECORD_RIID_INVALID = -1;
    public static final long RETURN_DEVICE_VOLUME_BEHAVIOR_ABSOLUTE_ADJUST_ONLY = 240663182;
    public static final java.lang.String RINGER_MODE_CHANGED_ACTION = "android.media.RINGER_MODE_CHANGED";
    public static final int RINGER_MODE_MAX = 2;
    public static final int RINGER_MODE_NORMAL = 2;
    public static final int RINGER_MODE_SILENT = 0;
    public static final int RINGER_MODE_VIBRATE = 1;

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
    public static final int SCO_AUDIO_STATE_CONNECTED = 1;
    public static final int SCO_AUDIO_STATE_CONNECTING = 2;
    public static final int SCO_AUDIO_STATE_DISCONNECTED = 0;
    public static final int SCO_AUDIO_STATE_ERROR = -1;
    public static final int STREAM_ACCESSIBILITY = 10;
    public static final int STREAM_ALARM = 4;

    @android.annotation.SystemApi
    public static final int STREAM_ASSISTANT = 11;

    @android.annotation.SystemApi
    public static final int STREAM_BLUETOOTH_SCO = 6;
    public static final java.lang.String STREAM_DEVICES_CHANGED_ACTION = "android.media.STREAM_DEVICES_CHANGED_ACTION";
    public static final int STREAM_DTMF = 8;
    public static final int STREAM_MUSIC = 3;
    public static final java.lang.String STREAM_MUTE_CHANGED_ACTION = "android.media.STREAM_MUTE_CHANGED_ACTION";
    public static final int STREAM_NOTIFICATION = 5;
    public static final int STREAM_RING = 2;
    public static final int STREAM_SYSTEM = 1;
    public static final int STREAM_SYSTEM_ENFORCED = 7;
    public static final int STREAM_TTS = 9;
    public static final int STREAM_VOICE_CALL = 0;

    @android.annotation.SystemApi
    public static final int SUCCESS = 0;
    private static final java.lang.String TAG = "AudioManager";
    public static final int USE_DEFAULT_STREAM_TYPE = Integer.MIN_VALUE;
    public static final java.lang.String VIBRATE_SETTING_CHANGED_ACTION = "android.media.VIBRATE_SETTING_CHANGED";
    public static final int VIBRATE_SETTING_OFF = 0;
    public static final int VIBRATE_SETTING_ON = 1;
    public static final int VIBRATE_SETTING_ONLY_SILENT = 2;
    public static final int VIBRATE_TYPE_NOTIFICATION = 1;
    public static final int VIBRATE_TYPE_RINGER = 0;
    public static final java.lang.String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";
    private static final float VOLUME_MIN_DB = -758.0f;
    private static java.util.ArrayList<android.media.AudioPatch> sAudioPatchesCached;
    private static int sAudioPortGeneration;
    private static java.lang.Object sAudioPortGenerationLock;
    private static java.util.ArrayList<android.media.AudioPort> sAudioPortsCached;
    private static java.lang.ref.WeakReference<android.content.Context> sContext;
    private static java.util.ArrayList<android.media.AudioPort> sPreviousAudioPortsCached;
    private static android.media.IAudioService sService;
    private android.content.Context mApplicationContext;
    private android.media.AudioManager.AudioServerStateCallback mAudioServerStateCb;
    private java.util.concurrent.Executor mAudioServerStateExec;
    private java.util.ArrayList<android.media.AudioManager.CallIRedirectionClientInfo> mCallIRedirectionClients;
    private android.media.AudioManager.CallInjectionModeChangedListener mCallRedirectionModeListener;
    private android.media.AudioManager.CapturePresetDevicesRoleDispatcherStub mDevicesRoleForCapturePresetDispatcherStub;
    private java.util.HashMap<java.lang.String, android.media.AudioManager.BlockingFocusResultReceiver> mFocusRequestsAwaitingResult;
    private android.media.AudioManager.MuteAwaitConnectionDispatcherStub mMuteAwaitConnDispatcherStub;
    private java.util.ArrayList<android.media.CallbackUtil.ListenerInfo<android.media.AudioManager.MuteAwaitConnectionCallback>> mMuteAwaitConnectionListeners;
    private android.content.Context mOriginalContext;
    private java.util.List<android.media.AudioManager.AudioPlaybackCallbackInfo> mPlaybackCallbackList;
    private java.util.List<android.media.AudioManager.AudioRecordingCallbackInfo> mRecordCallbackList;
    private android.companion.virtual.VirtualDeviceManager mVirtualDeviceManager;
    private static final android.media.AudioPortEventHandler sAudioPortEventHandler = new android.media.AudioPortEventHandler();
    private static final android.media.audiopolicy.AudioVolumeGroupChangeHandler sAudioAudioVolumeGroupChangedHandler = new android.media.audiopolicy.AudioVolumeGroupChangeHandler();
    private static final int[] PUBLIC_STREAM_TYPES = {0, 1, 2, 3, 4, 5, 8, 10};
    private static final java.util.TreeMap<java.lang.Integer, java.lang.String> FLAG_NAMES = new java.util.TreeMap<>();
    private int mOriginalContextDeviceId = 0;
    private final android.media.CallbackUtil.LazyListenerManager<android.media.AudioManager.OnPreferredDevicesForStrategyChangedListener> mPrefDevListenerMgr = new android.media.CallbackUtil.LazyListenerManager<>();
    private final android.media.CallbackUtil.LazyListenerManager<android.media.AudioManager.OnNonDefaultDevicesForStrategyChangedListener> mNonDefDevListenerMgr = new android.media.CallbackUtil.LazyListenerManager<>();
    private final java.util.Map<java.lang.Integer, java.lang.Object> mDevRoleForCapturePresetListeners = java.util.Map.of(1, new android.media.AudioManager.DevRoleListeners());
    private final java.lang.Object mDevRoleForCapturePresetListenersLock = new java.lang.Object();
    private int mDeviceRoleListenersStatus = 0;
    private final android.media.CallbackUtil.LazyListenerManager<android.media.AudioManager.OnModeChangedListener> mModeChangedListenerMgr = new android.media.CallbackUtil.LazyListenerManager<>();
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, android.media.AudioManager.FocusRequestInfo> mAudioFocusIdListenerMap = new java.util.concurrent.ConcurrentHashMap<>();
    private final android.media.AudioManager.ServiceEventHandlerDelegate mServiceEventHandlerDelegate = new android.media.AudioManager.ServiceEventHandlerDelegate(null);
    private final android.media.IAudioFocusDispatcher mAudioFocusDispatcher = new android.media.IAudioFocusDispatcher.Stub() { // from class: android.media.AudioManager.1
        @Override // android.media.IAudioFocusDispatcher
        public void dispatchAudioFocusChange(int i, java.lang.String str) {
            android.media.AudioManager.FocusRequestInfo findFocusRequestInfo = android.media.AudioManager.this.findFocusRequestInfo(str);
            if (findFocusRequestInfo != null && findFocusRequestInfo.mRequest.getOnAudioFocusChangeListener() != null) {
                android.os.Handler handler = findFocusRequestInfo.mHandler == null ? android.media.AudioManager.this.mServiceEventHandlerDelegate.getHandler() : findFocusRequestInfo.mHandler;
                handler.sendMessage(handler.obtainMessage(0, i, 0, str));
            }
        }

        @Override // android.media.IAudioFocusDispatcher
        public void dispatchFocusResultFromExtPolicy(int i, java.lang.String str) {
            synchronized (android.media.AudioManager.this.mFocusRequestsLock) {
                android.media.AudioManager.BlockingFocusResultReceiver blockingFocusResultReceiver = (android.media.AudioManager.BlockingFocusResultReceiver) android.media.AudioManager.this.mFocusRequestsAwaitingResult.remove(str);
                if (blockingFocusResultReceiver != null) {
                    blockingFocusResultReceiver.notifyResult(i);
                } else {
                    android.util.Log.e(android.media.AudioManager.TAG, "dispatchFocusResultFromExtPolicy found no result receiver");
                }
            }
        }
    };
    private final java.lang.Object mFocusRequestsLock = new java.lang.Object();
    private final java.lang.Object mPlaybackCallbackLock = new java.lang.Object();
    private final android.media.IPlaybackConfigDispatcher mPlayCb = new android.media.IPlaybackConfigDispatcher.Stub() { // from class: android.media.AudioManager.2
        @Override // android.media.IPlaybackConfigDispatcher
        public void dispatchPlaybackConfigChange(java.util.List<android.media.AudioPlaybackConfiguration> list, boolean z) {
            if (z) {
                android.os.Binder.flushPendingCommands();
            }
            synchronized (android.media.AudioManager.this.mPlaybackCallbackLock) {
                if (android.media.AudioManager.this.mPlaybackCallbackList != null) {
                    for (int i = 0; i < android.media.AudioManager.this.mPlaybackCallbackList.size(); i++) {
                        android.media.AudioManager.AudioPlaybackCallbackInfo audioPlaybackCallbackInfo = (android.media.AudioManager.AudioPlaybackCallbackInfo) android.media.AudioManager.this.mPlaybackCallbackList.get(i);
                        if (audioPlaybackCallbackInfo.mHandler != null) {
                            audioPlaybackCallbackInfo.mHandler.sendMessage(audioPlaybackCallbackInfo.mHandler.obtainMessage(2, new android.media.AudioManager.PlaybackConfigChangeCallbackData(audioPlaybackCallbackInfo.mCb, list)));
                        }
                    }
                }
            }
        }
    };
    private final java.lang.Object mRecordCallbackLock = new java.lang.Object();
    private final android.media.IRecordingConfigDispatcher mRecCb = new android.media.IRecordingConfigDispatcher.Stub() { // from class: android.media.AudioManager.3
        @Override // android.media.IRecordingConfigDispatcher
        public void dispatchRecordingConfigChange(java.util.List<android.media.AudioRecordingConfiguration> list) {
            synchronized (android.media.AudioManager.this.mRecordCallbackLock) {
                if (android.media.AudioManager.this.mRecordCallbackList != null) {
                    for (int i = 0; i < android.media.AudioManager.this.mRecordCallbackList.size(); i++) {
                        android.media.AudioManager.AudioRecordingCallbackInfo audioRecordingCallbackInfo = (android.media.AudioManager.AudioRecordingCallbackInfo) android.media.AudioManager.this.mRecordCallbackList.get(i);
                        if (audioRecordingCallbackInfo.mHandler != null) {
                            audioRecordingCallbackInfo.mHandler.sendMessage(audioRecordingCallbackInfo.mHandler.obtainMessage(1, new android.media.AudioManager.RecordConfigChangeCallbackData(audioRecordingCallbackInfo.mCb, list)));
                        }
                    }
                }
            }
        }
    };
    private final android.os.IBinder mICallBack = new android.os.Binder();
    private final java.util.concurrent.ConcurrentHashMap<android.media.AudioManager.OnDevicesForAttributesChangedListener, android.media.AudioManager.IDevicesForAttributesCallbackStub> mDevicesForAttributesListenerToStub = new java.util.concurrent.ConcurrentHashMap<>();
    private android.media.AudioManager.OnAmPortUpdateListener mPortListener = null;
    private final android.util.ArrayMap<android.media.AudioDeviceCallback, android.media.AudioManager.NativeEventHandlerDelegate> mDeviceCallbacks = new android.util.ArrayMap<>();
    private java.util.ArrayList<android.media.AudioDevicePort> mPreviousPorts = new java.util.ArrayList<>();
    private final java.lang.Object mAudioServerStateCbLock = new java.lang.Object();
    private final android.media.IAudioServerStateDispatcher mAudioServerStateDispatcher = new android.media.AudioManager.AnonymousClass4();
    private final android.media.CallbackUtil.LazyListenerManager<android.media.AudioManager.OnCommunicationDeviceChangedListener> mCommDeviceChangedListenerMgr = new android.media.CallbackUtil.LazyListenerManager<>();
    private java.lang.Object mCallRedirectionLock = new java.lang.Object();
    private final android.media.CallbackUtil.LazyListenerManager<android.media.AudioManager.OnPreferredMixerAttributesChangedListener> mPrefMixerAttributesListenerMgr = new android.media.CallbackUtil.LazyListenerManager<>();
    private final android.media.CallbackUtil.LazyListenerManager<java.lang.Runnable> mStreamAliasingListenerMgr = new android.media.CallbackUtil.LazyListenerManager<>();
    private final java.lang.Object mMuteAwaitConnectionListenerLock = new java.lang.Object();
    private boolean mIsAutomotive = false;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AbsoluteDeviceVolumeBehavior {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioDeviceCategory {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioDeviceRole {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioDirectPlaybackMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AudioOffloadMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CallRedirectionMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CsdWarning {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeviceVolumeBehavior {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeviceVolumeBehaviorState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EncodedSurroundOutputMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FocusRequestResult {
    }

    public interface OnAudioFocusChangeListener {
        void onAudioFocusChange(int i);
    }

    public interface OnAudioPortUpdateListener {
        void onAudioPatchListUpdate(android.media.AudioPatch[] audioPatchArr);

        void onAudioPortListUpdate(android.media.AudioPort[] audioPortArr);

        void onServiceDied();
    }

    public interface OnCommunicationDeviceChangedListener {
        void onCommunicationDeviceChanged(android.media.AudioDeviceInfo audioDeviceInfo);
    }

    @android.annotation.SystemApi
    public interface OnDevicesForAttributesChangedListener {
        void onDevicesForAttributesChanged(android.media.AudioAttributes audioAttributes, java.util.List<android.media.AudioDeviceAttributes> list);
    }

    public interface OnModeChangedListener {
        void onModeChanged(int i);
    }

    @android.annotation.SystemApi
    public interface OnNonDefaultDevicesForStrategyChangedListener {
        void onNonDefaultDevicesForStrategyChanged(android.media.audiopolicy.AudioProductStrategy audioProductStrategy, java.util.List<android.media.AudioDeviceAttributes> list);
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public interface OnPreferredDeviceForStrategyChangedListener {
        void onPreferredDeviceForStrategyChanged(android.media.audiopolicy.AudioProductStrategy audioProductStrategy, android.media.AudioDeviceAttributes audioDeviceAttributes);
    }

    @android.annotation.SystemApi
    public interface OnPreferredDevicesForCapturePresetChangedListener {
        void onPreferredDevicesForCapturePresetChanged(int i, java.util.List<android.media.AudioDeviceAttributes> list);
    }

    @android.annotation.SystemApi
    public interface OnPreferredDevicesForStrategyChangedListener {
        void onPreferredDevicesForStrategyChanged(android.media.audiopolicy.AudioProductStrategy audioProductStrategy, java.util.List<android.media.AudioDeviceAttributes> list);
    }

    public interface OnPreferredMixerAttributesChangedListener {
        void onPreferredMixerAttributesChanged(android.media.AudioAttributes audioAttributes, android.media.AudioDeviceInfo audioDeviceInfo, android.media.AudioMixerAttributes audioMixerAttributes);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PublicStreamTypes {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PublicVolumeFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SystemSoundEffect {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SystemVolumeFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VolumeAdjustment {
    }

    static {
        FLAG_NAMES.put(1, "FLAG_SHOW_UI");
        FLAG_NAMES.put(2, "FLAG_ALLOW_RINGER_MODES");
        FLAG_NAMES.put(4, "FLAG_PLAY_SOUND");
        FLAG_NAMES.put(8, "FLAG_REMOVE_SOUND_AND_VIBRATE");
        FLAG_NAMES.put(16, "FLAG_VIBRATE");
        FLAG_NAMES.put(32, "FLAG_FIXED_VOLUME");
        FLAG_NAMES.put(64, "FLAG_BLUETOOTH_ABS_VOLUME");
        FLAG_NAMES.put(128, "FLAG_SHOW_SILENT_HINT");
        FLAG_NAMES.put(256, "FLAG_HDMI_SYSTEM_AUDIO_VOLUME");
        FLAG_NAMES.put(512, "FLAG_ACTIVE_MEDIA_ONLY");
        FLAG_NAMES.put(1024, "FLAG_SHOW_UI_WARNINGS");
        FLAG_NAMES.put(2048, "FLAG_SHOW_VIBRATE_HINT");
        FLAG_NAMES.put(4096, "FLAG_FROM_KEY");
        FLAG_NAMES.put(8192, "FLAG_ABSOLUTE_VOLUME");
        sAudioPortGenerationLock = new java.lang.Object();
        sAudioPortGeneration = 0;
        sAudioPortsCached = new java.util.ArrayList<>();
        sPreviousAudioPortsCached = new java.util.ArrayList<>();
        sAudioPatchesCached = new java.util.ArrayList<>();
    }

    public static final int[] getPublicStreamTypes() {
        return PUBLIC_STREAM_TYPES;
    }

    public static final java.lang.String adjustToString(int i) {
        switch (i) {
            case -100:
                return "ADJUST_MUTE";
            case -1:
                return "ADJUST_LOWER";
            case 0:
                return "ADJUST_SAME";
            case 1:
                return "ADJUST_RAISE";
            case 100:
                return "ADJUST_UNMUTE";
            case 101:
                return "ADJUST_TOGGLE_MUTE";
            default:
                return "unknown adjust mode " + i;
        }
    }

    public static java.lang.String flagsToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (java.util.Map.Entry<java.lang.Integer, java.lang.String> entry : FLAG_NAMES.entrySet()) {
            int intValue = entry.getKey().intValue();
            if ((i & intValue) != 0) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append(entry.getValue());
                i &= ~intValue;
            }
        }
        if (i != 0) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(i);
        }
        return sb.toString();
    }

    public AudioManager() {
    }

    public AudioManager(android.content.Context context) {
        setContext(context);
        initPlatform();
    }

    private android.content.Context getContext() {
        if (this.mApplicationContext == null) {
            setContext(this.mOriginalContext);
        }
        if (this.mApplicationContext != null) {
            return this.mApplicationContext;
        }
        return this.mOriginalContext;
    }

    private void setContext(android.content.Context context) {
        if (context == null) {
            return;
        }
        this.mOriginalContextDeviceId = context.getDeviceId();
        this.mApplicationContext = context.getApplicationContext();
        if (this.mApplicationContext != null) {
            this.mOriginalContext = null;
        } else {
            this.mOriginalContext = context;
        }
        sContext = new java.lang.ref.WeakReference<>(context);
    }

    static android.media.IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        sService = android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio"));
        return sService;
    }

    private android.companion.virtual.VirtualDeviceManager getVirtualDeviceManager() {
        if (this.mVirtualDeviceManager != null) {
            return this.mVirtualDeviceManager;
        }
        this.mVirtualDeviceManager = (android.companion.virtual.VirtualDeviceManager) getContext().getSystemService(android.companion.virtual.VirtualDeviceManager.class);
        return this.mVirtualDeviceManager;
    }

    public void dispatchMediaKeyEvent(android.view.KeyEvent keyEvent) {
        android.media.session.MediaSessionLegacyHelper.getHelper(getContext()).sendMediaButtonEvent(keyEvent, false);
    }

    public void preDispatchKeyEvent(android.view.KeyEvent keyEvent, int i) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode != 25 && keyCode != 24 && keyCode != 164 && 300 > android.os.SystemClock.uptimeMillis()) {
            adjustSuggestedStreamVolume(0, i, 8);
        }
    }

    public boolean isVolumeFixed() {
        try {
            return getService().isVolumeFixed();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error querying isVolumeFixed", e);
            return false;
        }
    }

    public void adjustStreamVolume(int i, int i2, int i3) {
        try {
            getService().adjustStreamVolumeWithAttribution(i, i2, i3, getContext().getOpPackageName(), getContext().getAttributionTag());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void adjustVolume(int i, int i2) {
        if (applyAutoHardening()) {
            try {
                getService().adjustVolume(i, i2);
                return;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.media.session.MediaSessionLegacyHelper.getHelper(getContext()).sendAdjustVolumeBy(Integer.MIN_VALUE, i, i2);
    }

    public void adjustSuggestedStreamVolume(int i, int i2, int i3) {
        if (applyAutoHardening()) {
            try {
                getService().adjustSuggestedStreamVolume(i, i2, i3);
                return;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        android.media.session.MediaSessionLegacyHelper.getHelper(getContext()).sendAdjustVolumeBy(i2, i, i3);
    }

    public void setMasterMute(boolean z, int i) {
        try {
            getService().setMasterMute(z, i, getContext().getOpPackageName(), android.os.UserHandle.getCallingUserId(), getContext().getAttributionTag());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRingerMode() {
        try {
            return getService().getRingerModeExternal();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isRampingRingerEnabled() {
        return android.provider.Settings.System.getInt(getContext().getContentResolver(), "apply_ramping_ringer", 0) != 0;
    }

    public void setRampingRingerEnabled(boolean z) {
        android.provider.Settings.System.putInt(getContext().getContentResolver(), "apply_ramping_ringer", z ? 1 : 0);
    }

    public static boolean isValidRingerMode(int i) {
        if (i < 0 || i > 2) {
            return false;
        }
        try {
            return getService().isValidRingerMode(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getStreamMaxVolume(int i) {
        try {
            return getService().getStreamMaxVolume(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getStreamMinVolume(int i) {
        if (!isPublicStreamType(i)) {
            throw new java.lang.IllegalArgumentException("Invalid stream type " + i);
        }
        return getStreamMinVolumeInt(i);
    }

    public int getStreamMinVolumeInt(int i) {
        try {
            return getService().getStreamMinVolume(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getStreamVolume(int i) {
        try {
            return getService().getStreamVolume(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getStreamVolumeDb(int i, int i2, int i3) {
        if (!isPublicStreamType(i)) {
            throw new java.lang.IllegalArgumentException("Invalid stream type " + i);
        }
        if (i2 > getStreamMaxVolume(i) || i2 < getStreamMinVolume(i)) {
            throw new java.lang.IllegalArgumentException("Invalid stream volume index " + i2);
        }
        if (!android.media.AudioDeviceInfo.isValidAudioDeviceTypeOut(i3)) {
            throw new java.lang.IllegalArgumentException("Invalid audio output device type " + i3);
        }
        float streamVolumeDB = android.media.AudioSystem.getStreamVolumeDB(i, i2, android.media.AudioDeviceInfo.convertDeviceTypeToInternalDevice(i3));
        if (streamVolumeDB <= VOLUME_MIN_DB) {
            return Float.NEGATIVE_INFINITY;
        }
        return streamVolumeDB;
    }

    public static boolean isPublicStreamType(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
            case 10:
                return true;
            case 6:
            case 7:
            case 9:
            default:
                return false;
        }
    }

    @android.annotation.SystemApi
    public int getLastAudibleStreamVolume(int i) {
        try {
            return getService().getLastAudibleStreamVolume(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getUiSoundsStreamType() {
        try {
            return getService().getUiSoundsStreamType();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRingerMode(int i) {
        if (!isValidRingerMode(i)) {
            return;
        }
        try {
            getService().setRingerModeExternal(i, getContext().getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setStreamVolume(int i, int i2, int i3) {
        try {
            getService().setStreamVolumeWithAttribution(i, i2, i3, getContext().getOpPackageName(), getContext().getAttributionTag());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setVolumeIndexForAttributes(android.media.AudioAttributes audioAttributes, int i, int i2) {
        com.android.internal.util.Preconditions.checkNotNull(audioAttributes, "attr must not be null");
        getService();
        setVolumeGroupVolumeIndex(getVolumeGroupIdForAttributes(audioAttributes), i, i2);
    }

    @android.annotation.SystemApi
    public int getVolumeIndexForAttributes(android.media.AudioAttributes audioAttributes) {
        com.android.internal.util.Preconditions.checkNotNull(audioAttributes, "attr must not be null");
        getService();
        return getVolumeGroupVolumeIndex(getVolumeGroupIdForAttributes(audioAttributes));
    }

    @android.annotation.SystemApi
    public int getMaxVolumeIndexForAttributes(android.media.AudioAttributes audioAttributes) {
        com.android.internal.util.Preconditions.checkNotNull(audioAttributes, "attr must not be null");
        getService();
        return getVolumeGroupMaxVolumeIndex(getVolumeGroupIdForAttributes(audioAttributes));
    }

    @android.annotation.SystemApi
    public int getMinVolumeIndexForAttributes(android.media.AudioAttributes audioAttributes) {
        com.android.internal.util.Preconditions.checkNotNull(audioAttributes, "attr must not be null");
        getService();
        return getVolumeGroupMinVolumeIndex(getVolumeGroupIdForAttributes(audioAttributes));
    }

    public int getVolumeGroupIdForAttributes(android.media.AudioAttributes audioAttributes) {
        com.android.internal.util.Preconditions.checkNotNull(audioAttributes, "Audio Attributes must not be null");
        return android.media.audiopolicy.AudioProductStrategy.getVolumeGroupIdForAudioAttributes(audioAttributes, true);
    }

    @android.annotation.SystemApi
    public void setVolumeGroupVolumeIndex(int i, int i2, int i3) {
        try {
            getService().setVolumeGroupVolumeIndex(i, i2, i3, getContext().getOpPackageName(), getContext().getAttributionTag());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getVolumeGroupVolumeIndex(int i) {
        try {
            return getService().getVolumeGroupVolumeIndex(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getVolumeGroupMaxVolumeIndex(int i) {
        try {
            return getService().getVolumeGroupMaxVolumeIndex(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getVolumeGroupMinVolumeIndex(int i) {
        try {
            return getService().getVolumeGroupMinVolumeIndex(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void adjustVolumeGroupVolume(int i, int i2, int i3) {
        try {
            getService().adjustVolumeGroupVolume(i, i2, i3, getContext().getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getLastAudibleVolumeForVolumeGroup(int i) {
        try {
            return getService().getLastAudibleVolumeForVolumeGroup(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVolumeGroupMuted(int i) {
        try {
            return getService().isVolumeGroupMuted(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setSupportedSystemUsages(int[] iArr) {
        java.util.Objects.requireNonNull(iArr, "systemUsages must not be null");
        try {
            getService().setSupportedSystemUsages(iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int[] getSupportedSystemUsages() {
        try {
            return getService().getSupportedSystemUsages();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void setStreamSolo(int i, boolean z) {
        android.util.Log.w(TAG, "setStreamSolo has been deprecated. Do not use.");
    }

    @java.lang.Deprecated
    public void setStreamMute(int i, boolean z) {
        android.util.Log.w(TAG, "setStreamMute is deprecated. adjustStreamVolume should be used instead.");
        int i2 = z ? -100 : 100;
        if (i == Integer.MIN_VALUE) {
            adjustSuggestedStreamVolume(i2, i, 0);
        } else {
            adjustStreamVolume(i, i2, 0);
        }
    }

    public boolean isStreamMute(int i) {
        try {
            return getService().isStreamMute(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isMasterMute() {
        try {
            return getService().isMasterMute();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceVolumeControlStream(int i) {
        try {
            getService().forceVolumeControlStream(i, this.mICallBack);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldVibrate(int i) {
        try {
            return getService().shouldVibrate(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getVibrateSetting(int i) {
        try {
            return getService().getVibrateSetting(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVibrateSetting(int i, int i2) {
        try {
            getService().setVibrateSetting(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void setSpeakerphoneOn(boolean z) {
        try {
            getService().setSpeakerphoneOn(this.mICallBack, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public boolean isSpeakerphoneOn() {
        try {
            return getService().isSpeakerphoneOn();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setAllowedCapturePolicy(int i) {
        try {
            int allowedCapturePolicy = getService().setAllowedCapturePolicy(i);
            if (allowedCapturePolicy != 0) {
                android.util.Log.e(TAG, "Could not setAllowedCapturePolicy: " + allowedCapturePolicy);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAllowedCapturePolicy() {
        try {
            return getService().getAllowedCapturePolicy();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to query allowed capture policy: " + e);
            return 1;
        }
    }

    @android.annotation.SystemApi
    public boolean setPreferredDeviceForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy, android.media.AudioDeviceAttributes audioDeviceAttributes) {
        return setPreferredDevicesForStrategy(audioProductStrategy, java.util.Arrays.asList(audioDeviceAttributes));
    }

    @android.annotation.SystemApi
    public boolean removePreferredDeviceForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy) {
        java.util.Objects.requireNonNull(audioProductStrategy);
        try {
            return getService().removePreferredDevicesForStrategy(audioProductStrategy.getId()) == 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.media.AudioDeviceAttributes getPreferredDeviceForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy) {
        java.util.List<android.media.AudioDeviceAttributes> preferredDevicesForStrategy = getPreferredDevicesForStrategy(audioProductStrategy);
        if (preferredDevicesForStrategy.isEmpty()) {
            return null;
        }
        return preferredDevicesForStrategy.get(0);
    }

    @android.annotation.SystemApi
    public boolean setPreferredDevicesForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy, java.util.List<android.media.AudioDeviceAttributes> list) {
        java.util.Objects.requireNonNull(audioProductStrategy);
        java.util.Objects.requireNonNull(list);
        if (list.isEmpty()) {
            throw new java.lang.IllegalArgumentException("Tried to set preferred devices for strategy with a empty list");
        }
        java.util.Iterator<android.media.AudioDeviceAttributes> it = list.iterator();
        while (it.hasNext()) {
            java.util.Objects.requireNonNull(it.next());
        }
        try {
            return getService().setPreferredDevicesForStrategy(audioProductStrategy.getId(), list) == 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.media.AudioDeviceAttributes> getPreferredDevicesForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy) {
        java.util.Objects.requireNonNull(audioProductStrategy);
        try {
            return getService().getPreferredDevicesForStrategy(audioProductStrategy.getId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean setDeviceAsNonDefaultForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy, android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.Objects.requireNonNull(audioProductStrategy);
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        try {
            return getService().setDeviceAsNonDefaultForStrategy(audioProductStrategy.getId(), audioDeviceAttributes) == 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean removeDeviceAsNonDefaultForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy, android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.Objects.requireNonNull(audioProductStrategy);
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        try {
            return getService().removeDeviceAsNonDefaultForStrategy(audioProductStrategy.getId(), audioDeviceAttributes) == 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.media.AudioDeviceAttributes> getNonDefaultDevicesForStrategy(android.media.audiopolicy.AudioProductStrategy audioProductStrategy) {
        java.util.Objects.requireNonNull(audioProductStrategy);
        try {
            return getService().getNonDefaultDevicesForStrategy(audioProductStrategy.getId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void addOnPreferredDeviceForStrategyChangedListener(java.util.concurrent.Executor executor, android.media.AudioManager.OnPreferredDeviceForStrategyChangedListener onPreferredDeviceForStrategyChangedListener) throws java.lang.SecurityException {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void removeOnPreferredDeviceForStrategyChangedListener(android.media.AudioManager.OnPreferredDeviceForStrategyChangedListener onPreferredDeviceForStrategyChangedListener) {
    }

    @android.annotation.SystemApi
    public void addOnPreferredDevicesForStrategyChangedListener(java.util.concurrent.Executor executor, android.media.AudioManager.OnPreferredDevicesForStrategyChangedListener onPreferredDevicesForStrategyChangedListener) throws java.lang.SecurityException {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(onPreferredDevicesForStrategyChangedListener);
        this.mPrefDevListenerMgr.addListener(executor, onPreferredDevicesForStrategyChangedListener, "addOnPreferredDevicesForStrategyChangedListener", new java.util.function.Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.media.CallbackUtil.DispatcherStub lambda$addOnPreferredDevicesForStrategyChangedListener$0;
                lambda$addOnPreferredDevicesForStrategyChangedListener$0 = android.media.AudioManager.this.lambda$addOnPreferredDevicesForStrategyChangedListener$0();
                return lambda$addOnPreferredDevicesForStrategyChangedListener$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.media.CallbackUtil.DispatcherStub lambda$addOnPreferredDevicesForStrategyChangedListener$0() {
        return new android.media.AudioManager.StrategyPreferredDevicesDispatcherStub();
    }

    @android.annotation.SystemApi
    public void removeOnPreferredDevicesForStrategyChangedListener(android.media.AudioManager.OnPreferredDevicesForStrategyChangedListener onPreferredDevicesForStrategyChangedListener) {
        java.util.Objects.requireNonNull(onPreferredDevicesForStrategyChangedListener);
        this.mPrefDevListenerMgr.removeListener(onPreferredDevicesForStrategyChangedListener, "removeOnPreferredDevicesForStrategyChangedListener");
    }

    @android.annotation.SystemApi
    public void addOnNonDefaultDevicesForStrategyChangedListener(java.util.concurrent.Executor executor, android.media.AudioManager.OnNonDefaultDevicesForStrategyChangedListener onNonDefaultDevicesForStrategyChangedListener) throws java.lang.SecurityException {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(onNonDefaultDevicesForStrategyChangedListener);
        this.mNonDefDevListenerMgr.addListener(executor, onNonDefaultDevicesForStrategyChangedListener, "addOnNonDefaultDevicesForStrategyChangedListener", new java.util.function.Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.media.CallbackUtil.DispatcherStub lambda$addOnNonDefaultDevicesForStrategyChangedListener$1;
                lambda$addOnNonDefaultDevicesForStrategyChangedListener$1 = android.media.AudioManager.this.lambda$addOnNonDefaultDevicesForStrategyChangedListener$1();
                return lambda$addOnNonDefaultDevicesForStrategyChangedListener$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.media.CallbackUtil.DispatcherStub lambda$addOnNonDefaultDevicesForStrategyChangedListener$1() {
        return new android.media.AudioManager.StrategyNonDefaultDevicesDispatcherStub();
    }

    @android.annotation.SystemApi
    public void removeOnNonDefaultDevicesForStrategyChangedListener(android.media.AudioManager.OnNonDefaultDevicesForStrategyChangedListener onNonDefaultDevicesForStrategyChangedListener) {
        java.util.Objects.requireNonNull(onNonDefaultDevicesForStrategyChangedListener);
        this.mNonDefDevListenerMgr.removeListener(onNonDefaultDevicesForStrategyChangedListener, "removeOnNonDefaultDevicesForStrategyChangedListener");
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class StrategyPreferredDevicesDispatcherStub extends android.media.IStrategyPreferredDevicesDispatcher.Stub implements android.media.CallbackUtil.DispatcherStub {
        private StrategyPreferredDevicesDispatcherStub() {
        }

        @Override // android.media.IStrategyPreferredDevicesDispatcher
        public void dispatchPrefDevicesChanged(int i, final java.util.List<android.media.AudioDeviceAttributes> list) {
            final android.media.audiopolicy.AudioProductStrategy audioProductStrategyWithId = android.media.audiopolicy.AudioProductStrategy.getAudioProductStrategyWithId(i);
            android.media.AudioManager.this.mPrefDevListenerMgr.callListeners(new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$StrategyPreferredDevicesDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(java.lang.Object obj) {
                    ((android.media.AudioManager.OnPreferredDevicesForStrategyChangedListener) obj).onPreferredDevicesForStrategyChanged(android.media.audiopolicy.AudioProductStrategy.this, list);
                }
            });
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    android.media.AudioManager.getService().registerStrategyPreferredDevicesDispatcher(this);
                } else {
                    android.media.AudioManager.getService().unregisterStrategyPreferredDevicesDispatcher(this);
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class StrategyNonDefaultDevicesDispatcherStub extends android.media.IStrategyNonDefaultDevicesDispatcher.Stub implements android.media.CallbackUtil.DispatcherStub {
        private StrategyNonDefaultDevicesDispatcherStub() {
        }

        @Override // android.media.IStrategyNonDefaultDevicesDispatcher
        public void dispatchNonDefDevicesChanged(int i, final java.util.List<android.media.AudioDeviceAttributes> list) {
            final android.media.audiopolicy.AudioProductStrategy audioProductStrategyWithId = android.media.audiopolicy.AudioProductStrategy.getAudioProductStrategyWithId(i);
            android.media.AudioManager.this.mNonDefDevListenerMgr.callListeners(new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$StrategyNonDefaultDevicesDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(java.lang.Object obj) {
                    ((android.media.AudioManager.OnNonDefaultDevicesForStrategyChangedListener) obj).onNonDefaultDevicesForStrategyChanged(android.media.audiopolicy.AudioProductStrategy.this, list);
                }
            });
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    android.media.AudioManager.getService().registerStrategyNonDefaultDevicesDispatcher(this);
                } else {
                    android.media.AudioManager.getService().unregisterStrategyNonDefaultDevicesDispatcher(this);
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public boolean setPreferredDeviceForCapturePreset(int i, android.media.AudioDeviceAttributes audioDeviceAttributes) {
        return setPreferredDevicesForCapturePreset(i, java.util.Arrays.asList(audioDeviceAttributes));
    }

    @android.annotation.SystemApi
    public boolean clearPreferredDevicesForCapturePreset(int i) {
        if (!android.media.MediaRecorder.isValidAudioSource(i)) {
            return false;
        }
        try {
            return getService().clearPreferredDevicesForCapturePreset(i) == 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.media.AudioDeviceAttributes> getPreferredDevicesForCapturePreset(int i) {
        if (!android.media.MediaRecorder.isValidAudioSource(i)) {
            return new java.util.ArrayList();
        }
        try {
            return getService().getPreferredDevicesForCapturePreset(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean setPreferredDevicesForCapturePreset(int i, java.util.List<android.media.AudioDeviceAttributes> list) {
        java.util.Objects.requireNonNull(list);
        if (!android.media.MediaRecorder.isValidAudioSource(i)) {
            return false;
        }
        if (list.size() != 1) {
            throw new java.lang.IllegalArgumentException("Only support setting one preferred devices for capture preset");
        }
        java.util.Iterator<android.media.AudioDeviceAttributes> it = list.iterator();
        while (it.hasNext()) {
            java.util.Objects.requireNonNull(it.next());
        }
        try {
            return getService().setPreferredDevicesForCapturePreset(i, list) == 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void addOnPreferredDevicesForCapturePresetChangedListener(java.util.concurrent.Executor executor, android.media.AudioManager.OnPreferredDevicesForCapturePresetChangedListener onPreferredDevicesForCapturePresetChangedListener) throws java.lang.SecurityException {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(onPreferredDevicesForCapturePresetChangedListener);
        int addOnDevRoleForCapturePresetChangedListener = addOnDevRoleForCapturePresetChangedListener(executor, onPreferredDevicesForCapturePresetChangedListener, 1);
        if (addOnDevRoleForCapturePresetChangedListener == -1) {
            throw new java.lang.RuntimeException("Unknown error happened");
        }
        if (addOnDevRoleForCapturePresetChangedListener == -2) {
            throw new java.lang.IllegalArgumentException("attempt to call addOnPreferredDevicesForCapturePresetChangedListener() on a previously registered listener");
        }
    }

    @android.annotation.SystemApi
    public void removeOnPreferredDevicesForCapturePresetChangedListener(android.media.AudioManager.OnPreferredDevicesForCapturePresetChangedListener onPreferredDevicesForCapturePresetChangedListener) {
        java.util.Objects.requireNonNull(onPreferredDevicesForCapturePresetChangedListener);
        int removeOnDevRoleForCapturePresetChangedListener = removeOnDevRoleForCapturePresetChangedListener(onPreferredDevicesForCapturePresetChangedListener, 1);
        if (removeOnDevRoleForCapturePresetChangedListener == -1) {
            throw new java.lang.RuntimeException("Unknown error happened");
        }
        if (removeOnDevRoleForCapturePresetChangedListener == -2) {
            throw new java.lang.IllegalArgumentException("attempt to call removeOnPreferredDevicesForCapturePresetChangedListener() on an unregistered listener");
        }
    }

    private <T> int addOnDevRoleForCapturePresetChangedListener(java.util.concurrent.Executor executor, T t, int i) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(t);
        android.media.AudioManager.DevRoleListeners devRoleListeners = (android.media.AudioManager.DevRoleListeners) this.mDevRoleForCapturePresetListeners.get(java.lang.Integer.valueOf(i));
        if (devRoleListeners == null) {
            return -1;
        }
        synchronized (devRoleListeners.mDevRoleListenersLock) {
            if (devRoleListeners.hasDevRoleListener(t)) {
                return -2;
            }
            if (devRoleListeners.mListenerInfos == null) {
                devRoleListeners.mListenerInfos = new java.util.ArrayList();
            }
            int size = devRoleListeners.mListenerInfos.size();
            devRoleListeners.mListenerInfos.add(new android.media.AudioManager.DevRoleListenerInfo(executor, t));
            if (size == 0 && devRoleListeners.mListenerInfos.size() > 0) {
                synchronized (this.mDevRoleForCapturePresetListenersLock) {
                    int i2 = this.mDeviceRoleListenersStatus;
                    this.mDeviceRoleListenersStatus = (1 << i) | this.mDeviceRoleListenersStatus;
                    if (i2 != 0) {
                        return 0;
                    }
                    if (this.mDevicesRoleForCapturePresetDispatcherStub == null) {
                        this.mDevicesRoleForCapturePresetDispatcherStub = new android.media.AudioManager.CapturePresetDevicesRoleDispatcherStub();
                    }
                    try {
                        getService().registerCapturePresetDevicesRoleDispatcher(this.mDevicesRoleForCapturePresetDispatcherStub);
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
            return 0;
        }
    }

    private <T> int removeOnDevRoleForCapturePresetChangedListener(T t, int i) {
        java.util.Objects.requireNonNull(t);
        android.media.AudioManager.DevRoleListeners devRoleListeners = (android.media.AudioManager.DevRoleListeners) this.mDevRoleForCapturePresetListeners.get(java.lang.Integer.valueOf(i));
        if (devRoleListeners == null) {
            return -1;
        }
        synchronized (devRoleListeners.mDevRoleListenersLock) {
            if (!devRoleListeners.removeDevRoleListener(t)) {
                return -2;
            }
            if (devRoleListeners.mListenerInfos.size() == 0) {
                synchronized (this.mDevRoleForCapturePresetListenersLock) {
                    this.mDeviceRoleListenersStatus = (1 << i) ^ this.mDeviceRoleListenersStatus;
                    if (this.mDeviceRoleListenersStatus != 0) {
                        return 0;
                    }
                    try {
                        getService().unregisterCapturePresetDevicesRoleDispatcher(this.mDevicesRoleForCapturePresetDispatcherStub);
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DevRoleListenerInfo<T> {
        final java.util.concurrent.Executor mExecutor;
        final T mListener;

        DevRoleListenerInfo(java.util.concurrent.Executor executor, T t) {
            this.mExecutor = executor;
            this.mListener = t;
        }
    }

    private class DevRoleListeners<T> {
        private final java.lang.Object mDevRoleListenersLock;
        private java.util.ArrayList<android.media.AudioManager.DevRoleListenerInfo<T>> mListenerInfos;

        private DevRoleListeners() {
            this.mDevRoleListenersLock = new java.lang.Object();
        }

        private android.media.AudioManager.DevRoleListenerInfo<T> getDevRoleListenerInfo(T t) {
            if (this.mListenerInfos == null) {
                return null;
            }
            java.util.Iterator<android.media.AudioManager.DevRoleListenerInfo<T>> it = this.mListenerInfos.iterator();
            while (it.hasNext()) {
                android.media.AudioManager.DevRoleListenerInfo<T> next = it.next();
                if (next.mListener == t) {
                    return next;
                }
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasDevRoleListener(T t) {
            return getDevRoleListenerInfo(t) != null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean removeDevRoleListener(T t) {
            android.media.AudioManager.DevRoleListenerInfo<T> devRoleListenerInfo = getDevRoleListenerInfo(t);
            if (devRoleListenerInfo != null) {
                this.mListenerInfos.remove(devRoleListenerInfo);
                return true;
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class CapturePresetDevicesRoleDispatcherStub extends android.media.ICapturePresetDevicesRoleDispatcher.Stub {
        private CapturePresetDevicesRoleDispatcherStub() {
        }

        @Override // android.media.ICapturePresetDevicesRoleDispatcher
        public void dispatchDevicesRoleChanged(final int i, int i2, final java.util.List<android.media.AudioDeviceAttributes> list) {
            java.lang.Object obj = android.media.AudioManager.this.mDevRoleForCapturePresetListeners.get(java.lang.Integer.valueOf(i2));
            if (obj == null) {
                return;
            }
            switch (i2) {
                case 1:
                    android.media.AudioManager.DevRoleListeners devRoleListeners = (android.media.AudioManager.DevRoleListeners) obj;
                    synchronized (devRoleListeners.mDevRoleListenersLock) {
                        if (devRoleListeners.mListenerInfos.isEmpty()) {
                            return;
                        }
                        java.util.ArrayList arrayList = (java.util.ArrayList) devRoleListeners.mListenerInfos.clone();
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        try {
                            java.util.Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                final android.media.AudioManager.DevRoleListenerInfo devRoleListenerInfo = (android.media.AudioManager.DevRoleListenerInfo) it.next();
                                devRoleListenerInfo.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.AudioManager$CapturePresetDevicesRoleDispatcherStub$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ((android.media.AudioManager.OnPreferredDevicesForCapturePresetChangedListener) android.media.AudioManager.DevRoleListenerInfo.this.mListener).onPreferredDevicesForCapturePresetChanged(i, list);
                                    }
                                });
                            }
                            return;
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                default:
                    return;
            }
        }
    }

    public static int getDirectPlaybackSupport(android.media.AudioFormat audioFormat, android.media.AudioAttributes audioAttributes) {
        java.util.Objects.requireNonNull(audioFormat);
        java.util.Objects.requireNonNull(audioAttributes);
        return android.media.AudioSystem.getDirectPlaybackSupport(audioFormat, audioAttributes);
    }

    public static boolean isOffloadedPlaybackSupported(android.media.AudioFormat audioFormat, android.media.AudioAttributes audioAttributes) {
        if (audioFormat == null) {
            throw new java.lang.NullPointerException("Illegal null AudioFormat");
        }
        if (audioAttributes != null) {
            return android.media.AudioSystem.getOffloadSupport(audioFormat, audioAttributes) != 0;
        }
        throw new java.lang.NullPointerException("Illegal null AudioAttributes");
    }

    @java.lang.Deprecated
    public static int getPlaybackOffloadSupport(android.media.AudioFormat audioFormat, android.media.AudioAttributes audioAttributes) {
        if (audioFormat == null) {
            throw new java.lang.NullPointerException("Illegal null AudioFormat");
        }
        if (audioAttributes == null) {
            throw new java.lang.NullPointerException("Illegal null AudioAttributes");
        }
        return android.media.AudioSystem.getOffloadSupport(audioFormat, audioAttributes);
    }

    public android.media.Spatializer getSpatializer() {
        return new android.media.Spatializer(this);
    }

    public boolean isBluetoothScoAvailableOffCall() {
        return getContext().getResources().getBoolean(com.android.internal.R.bool.config_bluetooth_sco_off_call);
    }

    @java.lang.Deprecated
    public void startBluetoothSco() {
        try {
            getService().startBluetoothSco(this.mICallBack, getContext().getApplicationInfo().targetSdkVersion);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void startBluetoothScoVirtualCall() {
        try {
            getService().startBluetoothScoVirtualCall(this.mICallBack);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void stopBluetoothSco() {
        try {
            getService().stopBluetoothSco(this.mICallBack);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setBluetoothScoOn(boolean z) {
        try {
            getService().setBluetoothScoOn(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public boolean isBluetoothScoOn() {
        try {
            return getService().isBluetoothScoOn();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void setBluetoothA2dpOn(boolean z) {
    }

    public boolean isBluetoothA2dpOn() {
        return android.media.AudioSystem.getDeviceConnectionState(128, "") == 1 || android.media.AudioSystem.getDeviceConnectionState(256, "") == 1 || android.media.AudioSystem.getDeviceConnectionState(512, "") == 1;
    }

    @java.lang.Deprecated
    public void setWiredHeadsetOn(boolean z) {
    }

    public boolean isWiredHeadsetOn() {
        if (android.media.AudioSystem.getDeviceConnectionState(4, "") == 0 && android.media.AudioSystem.getDeviceConnectionState(8, "") == 0 && android.media.AudioSystem.getDeviceConnectionState(67108864, "") == 0) {
            return false;
        }
        return true;
    }

    public void setMicrophoneMute(boolean z) {
        try {
            getService().setMicrophoneMute(z, getContext().getOpPackageName(), android.os.UserHandle.getCallingUserId(), getContext().getAttributionTag());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setMicrophoneMuteFromSwitch(boolean z) {
        try {
            getService().setMicrophoneMuteFromSwitch(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isMicrophoneMute() {
        try {
            return getService().isMicrophoneMuted();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setMode(int i) {
        try {
            getService().setMode(i, this.mICallBack, this.mApplicationContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMode() {
        int i;
        try {
            int mode = getService().getMode();
            try {
                i = getContext().getApplicationInfo().targetSdkVersion;
            } catch (java.lang.NullPointerException e) {
                i = android.os.Build.VERSION.SDK_INT;
            }
            if (mode == 4 && i <= 29) {
                return 2;
            }
            if (mode == 5 && !android.app.compat.CompatChanges.isChangeEnabled(CALL_REDIRECTION_AUDIO_MODES)) {
                return 2;
            }
            if (mode == 6) {
                if (!android.app.compat.CompatChanges.isChangeEnabled(CALL_REDIRECTION_AUDIO_MODES)) {
                    return 3;
                }
                return mode;
            }
            return mode;
        } catch (android.os.RemoteException e2) {
            throw e2.rethrowFromSystemServer();
        }
    }

    final class ModeDispatcherStub extends android.media.IAudioModeDispatcher.Stub implements android.media.CallbackUtil.DispatcherStub {
        ModeDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    android.media.AudioManager.getService().registerModeDispatcher(this);
                } else {
                    android.media.AudioManager.getService().unregisterModeDispatcher(this);
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.IAudioModeDispatcher
        public void dispatchAudioModeChanged(final int i) {
            android.media.AudioManager.this.mModeChangedListenerMgr.callListeners(new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$ModeDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(java.lang.Object obj) {
                    ((android.media.AudioManager.OnModeChangedListener) obj).onModeChanged(i);
                }
            });
        }
    }

    public void addOnModeChangedListener(java.util.concurrent.Executor executor, android.media.AudioManager.OnModeChangedListener onModeChangedListener) {
        this.mModeChangedListenerMgr.addListener(executor, onModeChangedListener, "addOnModeChangedListener", new java.util.function.Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.media.CallbackUtil.DispatcherStub lambda$addOnModeChangedListener$2;
                lambda$addOnModeChangedListener$2 = android.media.AudioManager.this.lambda$addOnModeChangedListener$2();
                return lambda$addOnModeChangedListener$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.media.CallbackUtil.DispatcherStub lambda$addOnModeChangedListener$2() {
        return new android.media.AudioManager.ModeDispatcherStub();
    }

    public void removeOnModeChangedListener(android.media.AudioManager.OnModeChangedListener onModeChangedListener) {
        this.mModeChangedListenerMgr.removeListener(onModeChangedListener, "removeOnModeChangedListener");
    }

    public boolean isCallScreeningModeSupported() {
        try {
            return getService().isCallScreeningModeSupported();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void setRouting(int i, int i2, int i3) {
    }

    @java.lang.Deprecated
    public int getRouting(int i) {
        return -1;
    }

    public boolean isMusicActive() {
        try {
            return getService().isMusicActive(false);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isMusicActiveRemotely() {
        try {
            return getService().isMusicActive(true);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isAudioFocusExclusive() {
        try {
            return getService().getCurrentAudioFocus() == 4;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int generateAudioSessionId() {
        int newAudioSessionId = android.media.AudioSystem.newAudioSessionId();
        if (newAudioSessionId > 0) {
            return newAudioSessionId;
        }
        android.util.Log.e(TAG, "Failure to generate a new audio session ID");
        return -1;
    }

    @java.lang.Deprecated
    public void setParameter(java.lang.String str, java.lang.String str2) {
        setParameters(str + "=" + str2);
    }

    public void setParameters(java.lang.String str) {
        android.media.AudioSystem.setParameters(str);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void setHfpEnabled(boolean z) {
        android.media.AudioSystem.setParameters("hfp_enable=" + z);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void setHfpVolume(int i) {
        android.media.AudioSystem.setParameters("hfp_volume=" + i);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void setHfpSamplingRate(int i) {
        android.media.AudioSystem.setParameters("hfp_set_sampling_rate=" + i);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void setBluetoothHeadsetProperties(java.lang.String str, boolean z, boolean z2) {
        android.media.AudioSystem.setParameters("bt_headset_name=" + str + ";bt_headset_nrec=" + (z ? "on" : "off") + ";bt_wbs=" + (z2 ? "on" : "off"));
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void setA2dpSuspended(boolean z) {
        try {
            getService().setA2dpSuspended(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void setLeAudioSuspended(boolean z) {
        try {
            getService().setLeAudioSuspended(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getParameters(java.lang.String str) {
        return android.media.AudioSystem.getParameters(str);
    }

    public static int getNthNavigationRepeatSoundEffect(int i) {
        switch (i) {
            case 0:
                return 12;
            case 1:
                return 13;
            case 2:
                return 14;
            case 3:
                return 15;
            default:
                android.util.Log.w(TAG, "Invalid navigation repeat sound effect id: " + i);
                return -1;
        }
    }

    public void setNavigationRepeatSoundEffectsEnabled(boolean z) {
        try {
            getService().setNavigationRepeatSoundEffectsEnabled(z);
        } catch (android.os.RemoteException e) {
        }
    }

    public boolean areNavigationRepeatSoundEffectsEnabled() {
        try {
            return getService().areNavigationRepeatSoundEffectsEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setHomeSoundEffectEnabled(boolean z) {
        try {
            getService().setHomeSoundEffectEnabled(z);
        } catch (android.os.RemoteException e) {
        }
    }

    public boolean isHomeSoundEffectEnabled() {
        try {
            return getService().isHomeSoundEffectEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void playSoundEffect(int i) {
        playSoundEffect(i, -2);
    }

    public void playSoundEffect(int i, int i2) {
        if (i < 0 || i >= 16 || delegateSoundEffectToVdm(i)) {
            return;
        }
        try {
            getService().playSoundEffect(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void playSoundEffect(int i, float f) {
        if (i < 0 || i >= 16 || delegateSoundEffectToVdm(i)) {
            return;
        }
        try {
            getService().playSoundEffectVolume(i, f);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean delegateSoundEffectToVdm(int i) {
        if (hasCustomPolicyVirtualDeviceContext()) {
            getVirtualDeviceManager().playSoundEffect(this.mOriginalContextDeviceId, i);
            return true;
        }
        return false;
    }

    private boolean hasCustomPolicyVirtualDeviceContext() {
        android.companion.virtual.VirtualDeviceManager virtualDeviceManager;
        return (this.mOriginalContextDeviceId == 0 || (virtualDeviceManager = getVirtualDeviceManager()) == null || virtualDeviceManager.getDevicePolicy(this.mOriginalContextDeviceId, 1) == 0) ? false : true;
    }

    public void loadSoundEffects() {
        try {
            getService().loadSoundEffects();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unloadSoundEffects() {
        try {
            getService().unloadSoundEffects();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static java.lang.String audioFocusToString(int i) {
        switch (i) {
            case -3:
                return "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK";
            case -2:
                return "AUDIOFOCUS_LOSS_TRANSIENT";
            case -1:
                return "AUDIOFOCUS_LOSS";
            case 0:
                return "AUDIOFOCUS_NONE";
            case 1:
                return "AUDIOFOCUS_GAIN";
            case 2:
                return "AUDIOFOCUS_GAIN_TRANSIENT";
            case 3:
                return "AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK";
            case 4:
                return "AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE";
            default:
                return "AUDIO_FOCUS_UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    private static class FocusRequestInfo {
        final android.os.Handler mHandler;
        final android.media.AudioFocusRequest mRequest;

        FocusRequestInfo(android.media.AudioFocusRequest audioFocusRequest, android.os.Handler handler) {
            this.mRequest = audioFocusRequest;
            this.mHandler = handler;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.media.AudioManager.FocusRequestInfo findFocusRequestInfo(java.lang.String str) {
        return this.mAudioFocusIdListenerMap.get(str);
    }

    private class ServiceEventHandlerDelegate {
        private final android.os.Handler mHandler;

        ServiceEventHandlerDelegate(android.os.Handler handler) {
            android.os.Looper looper;
            if (handler == null) {
                looper = android.os.Looper.myLooper();
                if (looper == null) {
                    looper = android.os.Looper.getMainLooper();
                }
            } else {
                looper = handler.getLooper();
            }
            if (looper != null) {
                this.mHandler = new android.os.Handler(looper) { // from class: android.media.AudioManager.ServiceEventHandlerDelegate.1
                    @Override // android.os.Handler
                    public void handleMessage(android.os.Message message) {
                        android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;
                        switch (message.what) {
                            case 0:
                                android.media.AudioManager.FocusRequestInfo findFocusRequestInfo = android.media.AudioManager.this.findFocusRequestInfo((java.lang.String) message.obj);
                                if (findFocusRequestInfo != null && (onAudioFocusChangeListener = findFocusRequestInfo.mRequest.getOnAudioFocusChangeListener()) != null) {
                                    android.util.Log.d(android.media.AudioManager.TAG, "dispatching onAudioFocusChange(" + message.arg1 + ") to " + message.obj);
                                    onAudioFocusChangeListener.onAudioFocusChange(message.arg1);
                                    break;
                                }
                                break;
                            case 1:
                                android.media.AudioManager.RecordConfigChangeCallbackData recordConfigChangeCallbackData = (android.media.AudioManager.RecordConfigChangeCallbackData) message.obj;
                                if (recordConfigChangeCallbackData.mCb != null) {
                                    recordConfigChangeCallbackData.mCb.onRecordingConfigChanged(recordConfigChangeCallbackData.mConfigs);
                                    break;
                                }
                                break;
                            case 2:
                                android.media.AudioManager.PlaybackConfigChangeCallbackData playbackConfigChangeCallbackData = (android.media.AudioManager.PlaybackConfigChangeCallbackData) message.obj;
                                if (playbackConfigChangeCallbackData.mCb != null) {
                                    playbackConfigChangeCallbackData.mCb.onPlaybackConfigChanged(playbackConfigChangeCallbackData.mConfigs);
                                    break;
                                }
                                break;
                            default:
                                android.util.Log.e(android.media.AudioManager.TAG, "Unknown event " + message.what);
                                break;
                        }
                    }
                };
            } else {
                this.mHandler = null;
            }
        }

        android.os.Handler getHandler() {
            return this.mHandler;
        }
    }

    private java.lang.String getIdForAudioFocusListener(android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
        if (onAudioFocusChangeListener == null) {
            return new java.lang.String(toString());
        }
        return new java.lang.String(toString() + onAudioFocusChangeListener.toString());
    }

    public void registerAudioFocusRequest(android.media.AudioFocusRequest audioFocusRequest) {
        android.os.Handler onAudioFocusChangeListenerHandler = audioFocusRequest.getOnAudioFocusChangeListenerHandler();
        this.mAudioFocusIdListenerMap.put(getIdForAudioFocusListener(audioFocusRequest.getOnAudioFocusChangeListener()), new android.media.AudioManager.FocusRequestInfo(audioFocusRequest, onAudioFocusChangeListenerHandler == null ? null : new android.media.AudioManager.ServiceEventHandlerDelegate(onAudioFocusChangeListenerHandler).getHandler()));
    }

    public void unregisterAudioFocusRequest(android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
        this.mAudioFocusIdListenerMap.remove(getIdForAudioFocusListener(onAudioFocusChangeListener));
    }

    public int requestAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, int i, int i2) {
        android.media.PlayerBase.deprecateStreamTypeForPlayback(i, TAG, "requestAudioFocus()");
        try {
            return requestAudioFocus(onAudioFocusChangeListener, new android.media.AudioAttributes.Builder().setInternalLegacyStreamType(i).build(), i2, 0);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.e(TAG, "Audio focus request denied due to ", e);
            return 0;
        }
    }

    public int requestAudioFocus(android.media.AudioFocusRequest audioFocusRequest) {
        return requestAudioFocus(audioFocusRequest, null);
    }

    public int abandonAudioFocusRequest(android.media.AudioFocusRequest audioFocusRequest) {
        if (audioFocusRequest == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioFocusRequest");
        }
        return abandonAudioFocus(audioFocusRequest.getOnAudioFocusChangeListener(), audioFocusRequest.getAudioAttributes());
    }

    @android.annotation.SystemApi
    public int requestAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, android.media.AudioAttributes audioAttributes, int i, int i2) throws java.lang.IllegalArgumentException {
        int i3 = i2 & 3;
        if (i2 != i3) {
            throw new java.lang.IllegalArgumentException("Invalid flags 0x" + java.lang.Integer.toHexString(i2).toUpperCase());
        }
        return requestAudioFocus(onAudioFocusChangeListener, audioAttributes, i, i3, null);
    }

    @android.annotation.SystemApi
    public int requestAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, android.media.AudioAttributes audioAttributes, int i, int i2, android.media.audiopolicy.AudioPolicy audioPolicy) throws java.lang.IllegalArgumentException {
        if (audioAttributes == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioAttributes argument");
        }
        if (!android.media.AudioFocusRequest.isValidFocusGain(i)) {
            throw new java.lang.IllegalArgumentException("Invalid duration hint");
        }
        if (i2 != (i2 & 7)) {
            throw new java.lang.IllegalArgumentException("Illegal flags 0x" + java.lang.Integer.toHexString(i2).toUpperCase());
        }
        int i3 = i2 & 1;
        if (i3 == 1 && onAudioFocusChangeListener == null) {
            throw new java.lang.IllegalArgumentException("Illegal null focus listener when flagged as accepting delayed focus grant");
        }
        int i4 = i2 & 2;
        if (i4 == 2 && onAudioFocusChangeListener == null) {
            throw new java.lang.IllegalArgumentException("Illegal null focus listener when flagged as pausing instead of ducking");
        }
        int i5 = i2 & 4;
        if (i5 == 4 && audioPolicy == null) {
            throw new java.lang.IllegalArgumentException("Illegal null audio policy when locking audio focus");
        }
        return requestAudioFocus(new android.media.AudioFocusRequest.Builder(i).setOnAudioFocusChangeListenerInt(onAudioFocusChangeListener, null).setAudioAttributes(audioAttributes).setAcceptsDelayedFocusGain(i3 == 1).setWillPauseWhenDucked(i4 == 2).setLocksFocus(i5 == 4).build(), audioPolicy);
    }

    public int requestAudioFocusForTest(android.media.AudioFocusRequest audioFocusRequest, java.lang.String str, int i, int i2) {
        java.util.Objects.requireNonNull(audioFocusRequest);
        java.util.Objects.requireNonNull(str);
        synchronized (this.mFocusRequestsLock) {
            try {
                int requestAudioFocusForTest = getService().requestAudioFocusForTest(audioFocusRequest.getAudioAttributes(), audioFocusRequest.getFocusGain(), this.mICallBack, this.mAudioFocusDispatcher, str, "com.android.test.fakeclient", audioFocusRequest.getFlags() | 8, i, i2);
                if (requestAudioFocusForTest != 100) {
                    return requestAudioFocusForTest;
                }
                return handleExternalAudioPolicyWaitIfNeeded(str, addClientIdToFocusReceiverLocked(str));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public int abandonAudioFocusForTest(android.media.AudioFocusRequest audioFocusRequest, java.lang.String str) {
        java.util.Objects.requireNonNull(audioFocusRequest);
        java.util.Objects.requireNonNull(str);
        try {
            return getService().abandonAudioFocusForTest(this.mAudioFocusDispatcher, str, audioFocusRequest.getAudioAttributes(), "com.android.test.fakeclient");
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getFadeOutDurationOnFocusLossMillis(android.media.AudioAttributes audioAttributes) {
        java.util.Objects.requireNonNull(audioAttributes);
        try {
            return getService().getFadeOutDurationOnFocusLossMillis(audioAttributes);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<java.lang.Integer> getFocusDuckedUidsForTest() {
        try {
            return getService().getFocusDuckedUidsForTest();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getFocusFadeOutDurationForTest() {
        try {
            return getService().getFocusFadeOutDurationForTest();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getFocusUnmuteDelayAfterFadeOutForTest() {
        try {
            return getService().getFocusUnmuteDelayAfterFadeOutForTest();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean enterAudioFocusFreezeForTest(java.util.List<java.lang.Integer> list) {
        java.util.Objects.requireNonNull(list);
        try {
            return getService().enterAudioFocusFreezeForTest(this.mICallBack, list.stream().mapToInt(new android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean exitAudioFocusFreezeForTest() {
        try {
            return getService().exitAudioFocusFreezeForTest(this.mICallBack);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int requestAudioFocus(android.media.AudioFocusRequest audioFocusRequest, android.media.audiopolicy.AudioPolicy audioPolicy) {
        int i;
        if (audioFocusRequest == null) {
            throw new java.lang.NullPointerException("Illegal null AudioFocusRequest");
        }
        if (audioFocusRequest.locksFocus() && audioPolicy == null) {
            throw new java.lang.IllegalArgumentException("Illegal null audio policy when locking audio focus");
        }
        if (hasCustomPolicyVirtualDeviceContext()) {
            return 1;
        }
        registerAudioFocusRequest(audioFocusRequest);
        android.media.IAudioService service = getService();
        try {
            i = getContext().getApplicationInfo().targetSdkVersion;
        } catch (java.lang.NullPointerException e) {
            i = android.os.Build.VERSION.SDK_INT;
        }
        java.lang.String idForAudioFocusListener = getIdForAudioFocusListener(audioFocusRequest.getOnAudioFocusChangeListener());
        synchronized (this.mFocusRequestsLock) {
            try {
                int requestAudioFocus = service.requestAudioFocus(audioFocusRequest.getAudioAttributes(), audioFocusRequest.getFocusGain(), this.mICallBack, this.mAudioFocusDispatcher, idForAudioFocusListener, getContext().getOpPackageName(), getContext().getAttributionTag(), audioFocusRequest.getFlags(), audioPolicy != null ? audioPolicy.cb() : null, i);
                if (requestAudioFocus != 100) {
                    return requestAudioFocus;
                }
                return handleExternalAudioPolicyWaitIfNeeded(idForAudioFocusListener, addClientIdToFocusReceiverLocked(idForAudioFocusListener));
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
    }

    private android.media.AudioManager.BlockingFocusResultReceiver addClientIdToFocusReceiverLocked(java.lang.String str) {
        if (this.mFocusRequestsAwaitingResult == null) {
            this.mFocusRequestsAwaitingResult = new java.util.HashMap<>(1);
        }
        android.media.AudioManager.BlockingFocusResultReceiver blockingFocusResultReceiver = new android.media.AudioManager.BlockingFocusResultReceiver(str);
        this.mFocusRequestsAwaitingResult.put(str, blockingFocusResultReceiver);
        return blockingFocusResultReceiver;
    }

    private int handleExternalAudioPolicyWaitIfNeeded(java.lang.String str, android.media.AudioManager.BlockingFocusResultReceiver blockingFocusResultReceiver) {
        blockingFocusResultReceiver.waitForResult(250L);
        synchronized (this.mFocusRequestsLock) {
            this.mFocusRequestsAwaitingResult.remove(str);
        }
        return blockingFocusResultReceiver.requestResult();
    }

    private static final class SafeWaitObject {
        private boolean mQuit;

        private SafeWaitObject() {
            this.mQuit = false;
        }

        public void safeNotify() {
            synchronized (this) {
                this.mQuit = true;
                notify();
            }
        }

        public void safeWait(long j) throws java.lang.InterruptedException {
            long currentTimeMillis = java.lang.System.currentTimeMillis() + j;
            synchronized (this) {
                while (!this.mQuit) {
                    long currentTimeMillis2 = currentTimeMillis - java.lang.System.currentTimeMillis();
                    if (currentTimeMillis2 <= 0) {
                        break;
                    } else {
                        wait(currentTimeMillis2);
                    }
                }
            }
        }
    }

    private static final class BlockingFocusResultReceiver {
        private final java.lang.String mFocusClientId;
        private final android.media.AudioManager.SafeWaitObject mLock = new android.media.AudioManager.SafeWaitObject();
        private boolean mResultReceived = false;
        private int mFocusRequestResult = 0;

        BlockingFocusResultReceiver(java.lang.String str) {
            this.mFocusClientId = str;
        }

        boolean receivedResult() {
            return this.mResultReceived;
        }

        int requestResult() {
            return this.mFocusRequestResult;
        }

        void notifyResult(int i) {
            synchronized (this.mLock) {
                this.mResultReceived = true;
                this.mFocusRequestResult = i;
                this.mLock.safeNotify();
            }
        }

        public void waitForResult(long j) {
            synchronized (this.mLock) {
                if (this.mResultReceived) {
                    return;
                }
                try {
                    this.mLock.safeWait(j);
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
    }

    public void requestAudioFocusForCall(int i, int i2) {
        try {
            getService().requestAudioFocus(new android.media.AudioAttributes.Builder().setInternalLegacyStreamType(i).build(), i2, this.mICallBack, null, android.media.AudioSystem.IN_VOICE_COMM_FOCUS_ID, getContext().getOpPackageName(), getContext().getAttributionTag(), 4, null, 0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getFocusRampTimeMs(int i, android.media.AudioAttributes audioAttributes) {
        try {
            return getService().getFocusRampTimeMs(i, audioAttributes);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setFocusRequestResult(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.AudioPolicy audioPolicy) {
        if (audioFocusInfo == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioFocusInfo");
        }
        if (audioPolicy == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioPolicy");
        }
        try {
            getService().setFocusRequestResultFromExtPolicy(audioFocusInfo, i, audioPolicy.cb());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int dispatchAudioFocusChange(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.AudioPolicy audioPolicy) {
        if (audioFocusInfo == null) {
            throw new java.lang.NullPointerException("Illegal null AudioFocusInfo");
        }
        if (audioPolicy == null) {
            throw new java.lang.NullPointerException("Illegal null AudioPolicy");
        }
        try {
            return getService().dispatchFocusChange(audioFocusInfo, i, audioPolicy.cb());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int dispatchAudioFocusChangeWithFade(android.media.AudioFocusInfo audioFocusInfo, int i, android.media.audiopolicy.AudioPolicy audioPolicy, java.util.List<android.media.AudioFocusInfo> list, android.media.FadeManagerConfiguration fadeManagerConfiguration) {
        java.util.Objects.requireNonNull(audioFocusInfo, "AudioFocusInfo cannot be null");
        java.util.Objects.requireNonNull(audioPolicy, "AudioPolicy cannot be null");
        java.util.Objects.requireNonNull(list, "Other active AudioFocusInfo list cannot be null");
        try {
            return getService().dispatchFocusChangeWithFade(audioFocusInfo, i, audioPolicy.cb(), list, fadeManagerConfiguration);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void abandonAudioFocusForCall() {
        try {
            getService().abandonAudioFocus(null, android.media.AudioSystem.IN_VOICE_COMM_FOCUS_ID, null, getContext().getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int abandonAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
        return abandonAudioFocus(onAudioFocusChangeListener, null);
    }

    @android.annotation.SystemApi
    public int abandonAudioFocus(android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener, android.media.AudioAttributes audioAttributes) {
        if (hasCustomPolicyVirtualDeviceContext()) {
            return 1;
        }
        unregisterAudioFocusRequest(onAudioFocusChangeListener);
        try {
            return getService().abandonAudioFocus(this.mAudioFocusDispatcher, getIdForAudioFocusListener(onAudioFocusChangeListener), audioAttributes, getContext().getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void registerMediaButtonEventReceiver(android.content.ComponentName componentName) {
        if (componentName == null) {
            return;
        }
        if (!componentName.getPackageName().equals(getContext().getPackageName())) {
            android.util.Log.e(TAG, "registerMediaButtonEventReceiver() error: receiver and context package names don't match");
            return;
        }
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_MEDIA_BUTTON);
        intent.setComponent(componentName);
        registerMediaButtonIntent(android.app.PendingIntent.getBroadcast(getContext(), 0, intent, 67108864), componentName);
    }

    @java.lang.Deprecated
    public void registerMediaButtonEventReceiver(android.app.PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            return;
        }
        registerMediaButtonIntent(pendingIntent, null);
    }

    public void registerMediaButtonIntent(android.app.PendingIntent pendingIntent, android.content.ComponentName componentName) {
        if (pendingIntent == null) {
            android.util.Log.e(TAG, "Cannot call registerMediaButtonIntent() with a null parameter");
        } else {
            android.media.session.MediaSessionLegacyHelper.getHelper(getContext()).addMediaButtonListener(pendingIntent, componentName, getContext());
        }
    }

    @java.lang.Deprecated
    public void unregisterMediaButtonEventReceiver(android.content.ComponentName componentName) {
        if (componentName == null) {
            return;
        }
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_MEDIA_BUTTON);
        intent.setComponent(componentName);
        unregisterMediaButtonIntent(android.app.PendingIntent.getBroadcast(getContext(), 0, intent, 67108864));
    }

    @java.lang.Deprecated
    public void unregisterMediaButtonEventReceiver(android.app.PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            return;
        }
        unregisterMediaButtonIntent(pendingIntent);
    }

    public void unregisterMediaButtonIntent(android.app.PendingIntent pendingIntent) {
        android.media.session.MediaSessionLegacyHelper.getHelper(getContext()).removeMediaButtonListener(pendingIntent);
    }

    @java.lang.Deprecated
    public void registerRemoteControlClient(android.media.RemoteControlClient remoteControlClient) {
        if (remoteControlClient == null || remoteControlClient.getRcMediaIntent() == null) {
            return;
        }
        remoteControlClient.registerWithSession(android.media.session.MediaSessionLegacyHelper.getHelper(getContext()));
    }

    @java.lang.Deprecated
    public void unregisterRemoteControlClient(android.media.RemoteControlClient remoteControlClient) {
        if (remoteControlClient == null || remoteControlClient.getRcMediaIntent() == null) {
            return;
        }
        remoteControlClient.unregisterWithSession(android.media.session.MediaSessionLegacyHelper.getHelper(getContext()));
    }

    @java.lang.Deprecated
    public boolean registerRemoteController(android.media.RemoteController remoteController) {
        if (remoteController == null) {
            return false;
        }
        remoteController.startListeningToSessions();
        return true;
    }

    @java.lang.Deprecated
    public void unregisterRemoteController(android.media.RemoteController remoteController) {
        if (remoteController == null) {
            return;
        }
        remoteController.stopListeningToSessions();
    }

    @android.annotation.SystemApi
    public int registerAudioPolicy(android.media.audiopolicy.AudioPolicy audioPolicy) {
        return registerAudioPolicyStatic(audioPolicy);
    }

    static int registerAudioPolicyStatic(android.media.audiopolicy.AudioPolicy audioPolicy) {
        if (audioPolicy == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioPolicy argument");
        }
        android.media.IAudioService service = getService();
        try {
            android.media.projection.MediaProjection mediaProjection = audioPolicy.getMediaProjection();
            java.lang.String registerAudioPolicy = service.registerAudioPolicy(audioPolicy.getConfig(), audioPolicy.cb(), audioPolicy.hasFocusListener(), audioPolicy.isFocusPolicy(), audioPolicy.isTestFocusPolicy(), audioPolicy.isVolumeController(), mediaProjection == null ? null : mediaProjection.getProjection());
            if (registerAudioPolicy == null) {
                return -1;
            }
            audioPolicy.setRegistration(registerAudioPolicy);
            return 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void unregisterAudioPolicyAsync(android.media.audiopolicy.AudioPolicy audioPolicy) {
        unregisterAudioPolicyAsyncStatic(audioPolicy);
    }

    static void unregisterAudioPolicyAsyncStatic(android.media.audiopolicy.AudioPolicy audioPolicy) {
        if (audioPolicy == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioPolicy argument");
        }
        try {
            getService().unregisterAudioPolicyAsync(audioPolicy.cb());
            audioPolicy.reset();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void unregisterAudioPolicy(android.media.audiopolicy.AudioPolicy audioPolicy) {
        com.android.internal.util.Preconditions.checkNotNull(audioPolicy, "Illegal null AudioPolicy argument");
        android.media.IAudioService service = getService();
        try {
            audioPolicy.invalidateCaptorsAndInjectors();
            service.unregisterAudioPolicy(audioPolicy.cb());
            audioPolicy.reset();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.media.audiopolicy.AudioMix> getRegisteredPolicyMixes() {
        if (!android.media.audiopolicy.Flags.audioMixTestApi()) {
            return java.util.Collections.emptyList();
        }
        try {
            return getService().getRegisteredPolicyMixes();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasRegisteredDynamicPolicy() {
        try {
            return getService().hasRegisteredDynamicPolicy();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static abstract class AudioPlaybackCallback {
        public void onPlaybackConfigChanged(java.util.List<android.media.AudioPlaybackConfiguration> list) {
        }
    }

    private static class AudioPlaybackCallbackInfo {
        final android.media.AudioManager.AudioPlaybackCallback mCb;
        final android.os.Handler mHandler;

        AudioPlaybackCallbackInfo(android.media.AudioManager.AudioPlaybackCallback audioPlaybackCallback, android.os.Handler handler) {
            this.mCb = audioPlaybackCallback;
            this.mHandler = handler;
        }
    }

    private static final class PlaybackConfigChangeCallbackData {
        final android.media.AudioManager.AudioPlaybackCallback mCb;
        final java.util.List<android.media.AudioPlaybackConfiguration> mConfigs;

        PlaybackConfigChangeCallbackData(android.media.AudioManager.AudioPlaybackCallback audioPlaybackCallback, java.util.List<android.media.AudioPlaybackConfiguration> list) {
            this.mCb = audioPlaybackCallback;
            this.mConfigs = list;
        }
    }

    public void registerAudioPlaybackCallback(android.media.AudioManager.AudioPlaybackCallback audioPlaybackCallback, android.os.Handler handler) {
        if (audioPlaybackCallback == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioPlaybackCallback argument");
        }
        synchronized (this.mPlaybackCallbackLock) {
            if (this.mPlaybackCallbackList == null) {
                this.mPlaybackCallbackList = new java.util.ArrayList();
            }
            int size = this.mPlaybackCallbackList.size();
            if (!hasPlaybackCallback_sync(audioPlaybackCallback)) {
                this.mPlaybackCallbackList.add(new android.media.AudioManager.AudioPlaybackCallbackInfo(audioPlaybackCallback, new android.media.AudioManager.ServiceEventHandlerDelegate(handler).getHandler()));
                int size2 = this.mPlaybackCallbackList.size();
                if (size == 0 && size2 > 0) {
                    try {
                        getService().registerPlaybackCallback(this.mPlayCb);
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            } else {
                android.util.Log.w(TAG, "attempt to call registerAudioPlaybackCallback() on a previouslyregistered callback");
            }
        }
    }

    public void unregisterAudioPlaybackCallback(android.media.AudioManager.AudioPlaybackCallback audioPlaybackCallback) {
        if (audioPlaybackCallback == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioPlaybackCallback argument");
        }
        synchronized (this.mPlaybackCallbackLock) {
            if (this.mPlaybackCallbackList == null) {
                android.util.Log.w(TAG, "attempt to call unregisterAudioPlaybackCallback() on a callback that was never registered");
                return;
            }
            int size = this.mPlaybackCallbackList.size();
            if (removePlaybackCallback_sync(audioPlaybackCallback)) {
                int size2 = this.mPlaybackCallbackList.size();
                if (size > 0 && size2 == 0) {
                    try {
                        getService().unregisterPlaybackCallback(this.mPlayCb);
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            } else {
                android.util.Log.w(TAG, "attempt to call unregisterAudioPlaybackCallback() on a callback already unregistered or never registered");
            }
        }
    }

    public java.util.List<android.media.AudioPlaybackConfiguration> getActivePlaybackConfigurations() {
        try {
            return getService().getActivePlaybackConfigurations();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean hasPlaybackCallback_sync(android.media.AudioManager.AudioPlaybackCallback audioPlaybackCallback) {
        if (this.mPlaybackCallbackList != null) {
            for (int i = 0; i < this.mPlaybackCallbackList.size(); i++) {
                if (audioPlaybackCallback.equals(this.mPlaybackCallbackList.get(i).mCb)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean removePlaybackCallback_sync(android.media.AudioManager.AudioPlaybackCallback audioPlaybackCallback) {
        if (this.mPlaybackCallbackList != null) {
            for (int i = 0; i < this.mPlaybackCallbackList.size(); i++) {
                if (audioPlaybackCallback.equals(this.mPlaybackCallbackList.get(i).mCb)) {
                    this.mPlaybackCallbackList.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public static abstract class AudioRecordingCallback {
        public void onRecordingConfigChanged(java.util.List<android.media.AudioRecordingConfiguration> list) {
        }
    }

    private static class AudioRecordingCallbackInfo {
        final android.media.AudioManager.AudioRecordingCallback mCb;
        final android.os.Handler mHandler;

        AudioRecordingCallbackInfo(android.media.AudioManager.AudioRecordingCallback audioRecordingCallback, android.os.Handler handler) {
            this.mCb = audioRecordingCallback;
            this.mHandler = handler;
        }
    }

    private static final class RecordConfigChangeCallbackData {
        final android.media.AudioManager.AudioRecordingCallback mCb;
        final java.util.List<android.media.AudioRecordingConfiguration> mConfigs;

        RecordConfigChangeCallbackData(android.media.AudioManager.AudioRecordingCallback audioRecordingCallback, java.util.List<android.media.AudioRecordingConfiguration> list) {
            this.mCb = audioRecordingCallback;
            this.mConfigs = list;
        }
    }

    public void registerAudioRecordingCallback(android.media.AudioManager.AudioRecordingCallback audioRecordingCallback, android.os.Handler handler) {
        if (audioRecordingCallback == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioRecordingCallback argument");
        }
        synchronized (this.mRecordCallbackLock) {
            if (this.mRecordCallbackList == null) {
                this.mRecordCallbackList = new java.util.ArrayList();
            }
            int size = this.mRecordCallbackList.size();
            if (!hasRecordCallback_sync(audioRecordingCallback)) {
                this.mRecordCallbackList.add(new android.media.AudioManager.AudioRecordingCallbackInfo(audioRecordingCallback, new android.media.AudioManager.ServiceEventHandlerDelegate(handler).getHandler()));
                int size2 = this.mRecordCallbackList.size();
                if (size == 0 && size2 > 0) {
                    try {
                        getService().registerRecordingCallback(this.mRecCb);
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            } else {
                android.util.Log.w(TAG, "attempt to call registerAudioRecordingCallback() on a previouslyregistered callback");
            }
        }
    }

    public void unregisterAudioRecordingCallback(android.media.AudioManager.AudioRecordingCallback audioRecordingCallback) {
        if (audioRecordingCallback == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioRecordingCallback argument");
        }
        synchronized (this.mRecordCallbackLock) {
            if (this.mRecordCallbackList == null) {
                return;
            }
            int size = this.mRecordCallbackList.size();
            if (removeRecordCallback_sync(audioRecordingCallback)) {
                int size2 = this.mRecordCallbackList.size();
                if (size > 0 && size2 == 0) {
                    try {
                        getService().unregisterRecordingCallback(this.mRecCb);
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            } else {
                android.util.Log.w(TAG, "attempt to call unregisterAudioRecordingCallback() on a callback already unregistered or never registered");
            }
        }
    }

    public java.util.List<android.media.AudioRecordingConfiguration> getActiveRecordingConfigurations() {
        try {
            return getService().getActiveRecordingConfigurations();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private boolean hasRecordCallback_sync(android.media.AudioManager.AudioRecordingCallback audioRecordingCallback) {
        if (this.mRecordCallbackList != null) {
            for (int i = 0; i < this.mRecordCallbackList.size(); i++) {
                if (audioRecordingCallback.equals(this.mRecordCallbackList.get(i).mCb)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean removeRecordCallback_sync(android.media.AudioManager.AudioRecordingCallback audioRecordingCallback) {
        if (this.mRecordCallbackList != null) {
            for (int i = 0; i < this.mRecordCallbackList.size(); i++) {
                if (audioRecordingCallback.equals(this.mRecordCallbackList.get(i).mCb)) {
                    this.mRecordCallbackList.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public void reloadAudioSettings() {
        try {
            getService().reloadAudioSettings();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSilentMode() {
        int ringerMode = getRingerMode();
        return ringerMode == 0 || ringerMode == 1;
    }

    public static boolean isOutputDevice(int i) {
        return !android.media.AudioSystem.isInputDevice(i);
    }

    public static boolean isInputDevice(int i) {
        return android.media.AudioSystem.isInputDevice(i);
    }

    @java.lang.Deprecated
    public int getDevicesForStream(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
            case 10:
                try {
                    return getService().getDeviceMaskForStream(i);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            case 6:
            case 7:
            case 9:
            default:
                return 0;
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.media.AudioDeviceAttributes> getDevicesForAttributes(android.media.AudioAttributes audioAttributes) {
        java.util.Objects.requireNonNull(audioAttributes);
        try {
            return getService().getDevicesForAttributes(audioAttributes);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class IDevicesForAttributesCallbackStub extends android.media.IDevicesForAttributesCallback.Stub {
        android.media.CallbackUtil.ListenerInfo<android.media.AudioManager.OnDevicesForAttributesChangedListener> mInfo;

        IDevicesForAttributesCallbackStub(android.media.AudioManager.OnDevicesForAttributesChangedListener onDevicesForAttributesChangedListener, java.util.concurrent.Executor executor) {
            this.mInfo = new android.media.CallbackUtil.ListenerInfo<>(onDevicesForAttributesChangedListener, executor);
        }

        public void register(boolean z, android.media.AudioAttributes audioAttributes) {
            try {
                if (z) {
                    android.media.AudioManager.getService().addOnDevicesForAttributesChangedListener(audioAttributes, this);
                } else {
                    android.media.AudioManager.getService().removeOnDevicesForAttributesChangedListener(this);
                }
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.IDevicesForAttributesCallback
        public void onDevicesForAttributesChanged(final android.media.AudioAttributes audioAttributes, boolean z, final java.util.List<android.media.AudioDeviceAttributes> list) {
            this.mInfo.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.AudioManager$IDevicesForAttributesCallbackStub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.media.AudioManager.IDevicesForAttributesCallbackStub.this.lambda$onDevicesForAttributesChanged$0(audioAttributes, list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDevicesForAttributesChanged$0(android.media.AudioAttributes audioAttributes, java.util.List list) {
            this.mInfo.mListener.onDevicesForAttributesChanged(audioAttributes, list);
        }
    }

    @android.annotation.SystemApi
    public void addOnDevicesForAttributesChangedListener(android.media.AudioAttributes audioAttributes, java.util.concurrent.Executor executor, android.media.AudioManager.OnDevicesForAttributesChangedListener onDevicesForAttributesChangedListener) {
        java.util.Objects.requireNonNull(audioAttributes);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(onDevicesForAttributesChangedListener);
        synchronized (this.mDevicesForAttributesListenerToStub) {
            android.media.AudioManager.IDevicesForAttributesCallbackStub iDevicesForAttributesCallbackStub = this.mDevicesForAttributesListenerToStub.get(onDevicesForAttributesChangedListener);
            if (iDevicesForAttributesCallbackStub == null) {
                iDevicesForAttributesCallbackStub = new android.media.AudioManager.IDevicesForAttributesCallbackStub(onDevicesForAttributesChangedListener, executor);
                this.mDevicesForAttributesListenerToStub.put(onDevicesForAttributesChangedListener, iDevicesForAttributesCallbackStub);
            }
            iDevicesForAttributesCallbackStub.register(true, audioAttributes);
        }
    }

    @android.annotation.SystemApi
    public void removeOnDevicesForAttributesChangedListener(android.media.AudioManager.OnDevicesForAttributesChangedListener onDevicesForAttributesChangedListener) {
        java.util.Objects.requireNonNull(onDevicesForAttributesChangedListener);
        synchronized (this.mDevicesForAttributesListenerToStub) {
            android.media.AudioManager.IDevicesForAttributesCallbackStub iDevicesForAttributesCallbackStub = this.mDevicesForAttributesListenerToStub.get(onDevicesForAttributesChangedListener);
            if (iDevicesForAttributesCallbackStub != null) {
                iDevicesForAttributesCallbackStub.register(false, null);
            }
            this.mDevicesForAttributesListenerToStub.remove(onDevicesForAttributesChangedListener);
        }
    }

    public java.util.List<android.media.AudioDeviceInfo> getAudioDevicesForAttributes(android.media.AudioAttributes audioAttributes) {
        try {
            java.util.Objects.requireNonNull(audioAttributes);
            java.util.List<android.media.AudioDeviceAttributes> devicesForAttributesUnprotected = getService().getDevicesForAttributesUnprotected(audioAttributes);
            android.media.AudioDeviceInfo[] devicesStatic = getDevicesStatic(2);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.media.AudioDeviceAttributes audioDeviceAttributes : devicesForAttributesUnprotected) {
                for (android.media.AudioDeviceInfo audioDeviceInfo : devicesStatic) {
                    if (audioDeviceAttributes.getType() == audioDeviceInfo.getType() && android.text.TextUtils.equals(audioDeviceAttributes.getAddress(), audioDeviceInfo.getAddress())) {
                        arrayList.add(audioDeviceInfo);
                    }
                }
            }
            return java.util.Collections.unmodifiableList(arrayList);
        } catch (java.lang.Exception e) {
            android.util.Log.i(TAG, "No audio devices available for specified attributes.");
            return java.util.Collections.emptyList();
        }
    }

    public static void enforceValidVolumeBehavior(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return;
            default:
                throw new java.lang.IllegalArgumentException("Illegal volume behavior " + i);
        }
    }

    @android.annotation.SystemApi
    public void setDeviceVolumeBehavior(android.media.AudioDeviceAttributes audioDeviceAttributes, int i) {
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        enforceValidVolumeBehavior(i);
        try {
            getService().setDeviceVolumeBehavior(audioDeviceAttributes, i, this.mApplicationContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getDeviceVolumeBehavior(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        try {
            int deviceVolumeBehavior = getService().getDeviceVolumeBehavior(audioDeviceAttributes);
            if (!android.app.compat.CompatChanges.isChangeEnabled(RETURN_DEVICE_VOLUME_BEHAVIOR_ABSOLUTE_ADJUST_ONLY) && deviceVolumeBehavior == 5) {
                return 1;
            }
            return deviceVolumeBehavior;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isFullVolumeDevice() {
        java.util.Iterator<android.media.AudioDeviceAttributes> it = getDevicesForAttributes(new android.media.AudioAttributes.Builder().setUsage(1).build()).iterator();
        while (it.hasNext()) {
            if (getDeviceVolumeBehavior(it.next()) == 1) {
                return true;
            }
        }
        return false;
    }

    public void setWiredDeviceConnectionState(int i, int i2, java.lang.String str, java.lang.String str2) {
        setWiredDeviceConnectionState(new android.media.AudioDeviceAttributes(i, str, str2), i2);
    }

    public void setWiredDeviceConnectionState(android.media.AudioDeviceAttributes audioDeviceAttributes, int i) {
        try {
            getService().setWiredDeviceConnectionState(audioDeviceAttributes, i, this.mApplicationContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setTestDeviceConnectionState(android.media.AudioDeviceAttributes audioDeviceAttributes, boolean z) {
        try {
            getService().setTestDeviceConnectionState(audioDeviceAttributes, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void handleBluetoothActiveDeviceChanged(android.bluetooth.BluetoothDevice bluetoothDevice, android.bluetooth.BluetoothDevice bluetoothDevice2, android.media.BluetoothProfileConnectionInfo bluetoothProfileConnectionInfo) {
        try {
            getService().handleBluetoothActiveDeviceChanged(bluetoothDevice, bluetoothDevice2, bluetoothProfileConnectionInfo);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.media.IRingtonePlayer getRingtonePlayer() {
        try {
            return getService().getRingtonePlayer();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getProperty(java.lang.String str) {
        if (PROPERTY_OUTPUT_SAMPLE_RATE.equals(str)) {
            int primaryOutputSamplingRate = android.media.AudioSystem.getPrimaryOutputSamplingRate();
            if (primaryOutputSamplingRate > 0) {
                return java.lang.Integer.toString(primaryOutputSamplingRate);
            }
            return null;
        }
        if (PROPERTY_OUTPUT_FRAMES_PER_BUFFER.equals(str)) {
            int primaryOutputFrameCount = android.media.AudioSystem.getPrimaryOutputFrameCount();
            if (primaryOutputFrameCount > 0) {
                return java.lang.Integer.toString(primaryOutputFrameCount);
            }
            return null;
        }
        if (PROPERTY_SUPPORT_MIC_NEAR_ULTRASOUND.equals(str)) {
            return java.lang.String.valueOf(getContext().getResources().getBoolean(com.android.internal.R.bool.config_supportMicNearUltrasound));
        }
        if (PROPERTY_SUPPORT_SPEAKER_NEAR_ULTRASOUND.equals(str)) {
            return java.lang.String.valueOf(getContext().getResources().getBoolean(com.android.internal.R.bool.config_supportSpeakerNearUltrasound));
        }
        if (PROPERTY_SUPPORT_AUDIO_SOURCE_UNPROCESSED.equals(str)) {
            return java.lang.String.valueOf(getContext().getResources().getBoolean(com.android.internal.R.bool.config_supportAudioSourceUnprocessed));
        }
        return null;
    }

    @android.annotation.SystemApi
    public boolean setAdditionalOutputDeviceDelay(android.media.AudioDeviceInfo audioDeviceInfo, long j) {
        java.util.Objects.requireNonNull(audioDeviceInfo);
        try {
            return getService().setAdditionalOutputDeviceDelay(new android.media.AudioDeviceAttributes(audioDeviceInfo), j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public long getAdditionalOutputDeviceDelay(android.media.AudioDeviceInfo audioDeviceInfo) {
        java.util.Objects.requireNonNull(audioDeviceInfo);
        try {
            return getService().getAdditionalOutputDeviceDelay(new android.media.AudioDeviceAttributes(audioDeviceInfo));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public long getMaxAdditionalOutputDeviceDelay(android.media.AudioDeviceInfo audioDeviceInfo) {
        java.util.Objects.requireNonNull(audioDeviceInfo);
        try {
            return getService().getMaxAdditionalOutputDeviceDelay(new android.media.AudioDeviceAttributes(audioDeviceInfo));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getOutputLatency(int i) {
        return android.media.AudioSystem.getOutputLatency(i);
    }

    public void setVolumeController(android.media.IVolumeController iVolumeController) {
        try {
            getService().setVolumeController(iVolumeController);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.media.IVolumeController getVolumeController() {
        try {
            return getService().getVolumeController();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyVolumeControllerVisible(android.media.IVolumeController iVolumeController, boolean z) {
        try {
            getService().notifyVolumeControllerVisible(iVolumeController, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isStreamAffectedByRingerMode(int i) {
        try {
            return getService().isStreamAffectedByRingerMode(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isStreamAffectedByMute(int i) {
        try {
            return getService().isStreamAffectedByMute(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableSafeMediaVolume() {
        try {
            getService().disableSafeMediaVolume(this.mApplicationContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void lowerVolumeToRs1() {
        try {
            getService().lowerVolumeToRs1(this.mApplicationContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getRs2Value() {
        try {
            return getService().getOutputRs2UpperBound();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRs2Value(float f) {
        try {
            getService().setOutputRs2UpperBound(f);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public float getCsd() {
        try {
            return getService().getCsd();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setCsd(float f) {
        try {
            getService().setCsd(f);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceUseFrameworkMel(boolean z) {
        try {
            getService().forceUseFrameworkMel(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void forceComputeCsdOnAllDevices(boolean z) {
        try {
            getService().forceComputeCsdOnAllDevices(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isCsdEnabled() {
        try {
            return getService().isCsdEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isCsdAsAFeatureAvailable() {
        try {
            return getService().isCsdAsAFeatureAvailable();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isCsdAsAFeatureEnabled() {
        try {
            return getService().isCsdAsAFeatureEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setCsdAsAFeatureEnabled(boolean z) {
        try {
            getService().setCsdAsAFeatureEnabled(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static java.lang.String audioDeviceCategoryToString(int i) {
        switch (i) {
            case 0:
                return "AUDIO_DEVICE_CATEGORY_UNKNOWN";
            case 1:
                return "AUDIO_DEVICE_CATEGORY_OTHER";
            case 2:
                return "AUDIO_DEVICE_CATEGORY_SPEAKER";
            case 3:
                return "AUDIO_DEVICE_CATEGORY_HEADPHONES";
            case 4:
                return "AUDIO_DEVICE_CATEGORY_CARKIT";
            case 5:
                return "AUDIO_DEVICE_CATEGORY_WATCH";
            case 6:
                return "AUDIO_DEVICE_CATEGORY_HEARING_AID";
            case 7:
                return "AUDIO_DEVICE_CATEGORY_RECEIVER";
            default:
                return "unknown AudioDeviceCategory " + i;
        }
    }

    public void setBluetoothAudioDeviceCategory_legacy(java.lang.String str, boolean z, int i) {
        if (android.media.audio.Flags.automaticBtDeviceType()) {
            return;
        }
        try {
            getService().setBluetoothAudioDeviceCategory_legacy(str, z, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getBluetoothAudioDeviceCategory_legacy(java.lang.String str, boolean z) {
        if (android.media.audio.Flags.automaticBtDeviceType()) {
            return 0;
        }
        try {
            return getService().getBluetoothAudioDeviceCategory_legacy(str, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setBluetoothAudioDeviceCategory(java.lang.String str, int i) {
        if (!android.media.audio.Flags.automaticBtDeviceType()) {
            return false;
        }
        try {
            return getService().setBluetoothAudioDeviceCategory(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getBluetoothAudioDeviceCategory(java.lang.String str) {
        if (!android.media.audio.Flags.automaticBtDeviceType()) {
            return 0;
        }
        try {
            return getService().getBluetoothAudioDeviceCategory(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isBluetoothAudioDeviceCategoryFixed(java.lang.String str) {
        if (!android.media.audio.Flags.automaticBtDeviceType()) {
            return false;
        }
        try {
            return getService().isBluetoothAudioDeviceCategoryFixed(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRingerModeInternal(int i) {
        try {
            getService().setRingerModeInternal(i, getContext().getOpPackageName());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRingerModeInternal() {
        try {
            return getService().getRingerModeInternal();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setVolumePolicy(android.media.VolumePolicy volumePolicy) {
        try {
            getService().setVolumePolicy(volumePolicy);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int setHdmiSystemAudioSupported(boolean z) {
        try {
            return getService().setHdmiSystemAudioSupported(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isHdmiSystemAudioSupported() {
        try {
            return getService().isHdmiSystemAudioSupported();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static int listAudioPorts(java.util.ArrayList<android.media.AudioPort> arrayList) {
        return updateAudioPortCache(arrayList, null, null);
    }

    public static int listPreviousAudioPorts(java.util.ArrayList<android.media.AudioPort> arrayList) {
        return updateAudioPortCache(null, null, arrayList);
    }

    public static int listAudioDevicePorts(java.util.ArrayList<android.media.AudioDevicePort> arrayList) {
        if (arrayList == null) {
            return -2;
        }
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        int updateAudioPortCache = updateAudioPortCache(arrayList2, null, null);
        if (updateAudioPortCache == 0) {
            filterDevicePorts(arrayList2, arrayList);
        }
        return updateAudioPortCache;
    }

    public static int listPreviousAudioDevicePorts(java.util.ArrayList<android.media.AudioDevicePort> arrayList) {
        if (arrayList == null) {
            return -2;
        }
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        int updateAudioPortCache = updateAudioPortCache(null, null, arrayList2);
        if (updateAudioPortCache == 0) {
            filterDevicePorts(arrayList2, arrayList);
        }
        return updateAudioPortCache;
    }

    private static void filterDevicePorts(java.util.ArrayList<android.media.AudioPort> arrayList, java.util.ArrayList<android.media.AudioDevicePort> arrayList2) {
        arrayList2.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) instanceof android.media.AudioDevicePort) {
                arrayList2.add((android.media.AudioDevicePort) arrayList.get(i));
            }
        }
    }

    public static int createAudioPatch(android.media.AudioPatch[] audioPatchArr, android.media.AudioPortConfig[] audioPortConfigArr, android.media.AudioPortConfig[] audioPortConfigArr2) {
        return android.media.AudioSystem.createAudioPatch(audioPatchArr, audioPortConfigArr, audioPortConfigArr2);
    }

    public static int releaseAudioPatch(android.media.AudioPatch audioPatch) {
        return android.media.AudioSystem.releaseAudioPatch(audioPatch);
    }

    public static int listAudioPatches(java.util.ArrayList<android.media.AudioPatch> arrayList) {
        return updateAudioPortCache(null, arrayList, null);
    }

    public static int setAudioPortGain(android.media.AudioPort audioPort, android.media.AudioGainConfig audioGainConfig) {
        if (audioPort == null || audioGainConfig == null) {
            return -2;
        }
        android.media.AudioPortConfig activeConfig = audioPort.activeConfig();
        android.media.AudioPortConfig audioPortConfig = new android.media.AudioPortConfig(audioPort, activeConfig.samplingRate(), activeConfig.channelMask(), activeConfig.format(), audioGainConfig);
        audioPortConfig.mConfigMask = 8;
        return android.media.AudioSystem.setAudioPortConfig(audioPortConfig);
    }

    public void registerAudioPortUpdateListener(android.media.AudioManager.OnAudioPortUpdateListener onAudioPortUpdateListener) {
        sAudioPortEventHandler.init();
        sAudioPortEventHandler.registerListener(onAudioPortUpdateListener);
    }

    public void unregisterAudioPortUpdateListener(android.media.AudioManager.OnAudioPortUpdateListener onAudioPortUpdateListener) {
        sAudioPortEventHandler.unregisterListener(onAudioPortUpdateListener);
    }

    static int resetAudioPortGeneration() {
        int i;
        synchronized (sAudioPortGenerationLock) {
            i = sAudioPortGeneration;
            sAudioPortGeneration = 0;
        }
        return i;
    }

    static int updateAudioPortCache(java.util.ArrayList<android.media.AudioPort> arrayList, java.util.ArrayList<android.media.AudioPatch> arrayList2, java.util.ArrayList<android.media.AudioPort> arrayList3) {
        boolean z;
        sAudioPortEventHandler.init();
        synchronized (sAudioPortGenerationLock) {
            if (sAudioPortGeneration == 0) {
                int[] iArr = new int[1];
                int[] iArr2 = new int[1];
                java.util.ArrayList<android.media.AudioPort> arrayList4 = new java.util.ArrayList<>();
                java.util.ArrayList<android.media.AudioPatch> arrayList5 = new java.util.ArrayList<>();
                while (true) {
                    arrayList4.clear();
                    int listAudioPorts = android.media.AudioSystem.listAudioPorts(arrayList4, iArr2);
                    if (listAudioPorts != 0) {
                        android.util.Log.w(TAG, "updateAudioPortCache: listAudioPorts failed");
                        return listAudioPorts;
                    }
                    arrayList5.clear();
                    int listAudioPatches = android.media.AudioSystem.listAudioPatches(arrayList5, iArr);
                    if (listAudioPatches != 0) {
                        android.util.Log.w(TAG, "updateAudioPortCache: listAudioPatches failed");
                        return listAudioPatches;
                    }
                    if (iArr[0] == iArr2[0] || (arrayList != null && arrayList2 != null)) {
                        break;
                    }
                }
                if (iArr[0] != iArr2[0]) {
                    return -1;
                }
                for (int i = 0; i < arrayList5.size(); i++) {
                    for (int i2 = 0; i2 < arrayList5.get(i).sources().length; i2++) {
                        arrayList5.get(i).sources()[i2] = updatePortConfig(arrayList5.get(i).sources()[i2], arrayList4);
                    }
                    for (int i3 = 0; i3 < arrayList5.get(i).sinks().length; i3++) {
                        arrayList5.get(i).sinks()[i3] = updatePortConfig(arrayList5.get(i).sinks()[i3], arrayList4);
                    }
                }
                java.util.Iterator<android.media.AudioPatch> it = arrayList5.iterator();
                while (it.hasNext()) {
                    android.media.AudioPatch next = it.next();
                    android.media.AudioPortConfig[] sources = next.sources();
                    int length = sources.length;
                    int i4 = 0;
                    while (true) {
                        if (i4 >= length) {
                            z = false;
                            break;
                        }
                        if (sources[i4] != null) {
                            i4++;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    android.media.AudioPortConfig[] sinks = next.sinks();
                    int length2 = sinks.length;
                    int i5 = 0;
                    while (true) {
                        if (i5 >= length2) {
                            break;
                        }
                        if (sinks[i5] != null) {
                            i5++;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    if (z) {
                        it.remove();
                    }
                }
                sPreviousAudioPortsCached = sAudioPortsCached;
                sAudioPortsCached = arrayList4;
                sAudioPatchesCached = arrayList5;
                sAudioPortGeneration = iArr2[0];
            }
            if (arrayList != null) {
                arrayList.clear();
                arrayList.addAll(sAudioPortsCached);
            }
            if (arrayList2 != null) {
                arrayList2.clear();
                arrayList2.addAll(sAudioPatchesCached);
            }
            if (arrayList3 != null) {
                arrayList3.clear();
                arrayList3.addAll(sPreviousAudioPortsCached);
            }
            return 0;
        }
    }

    static android.media.AudioPortConfig updatePortConfig(android.media.AudioPortConfig audioPortConfig, java.util.ArrayList<android.media.AudioPort> arrayList) {
        android.media.AudioPort port = audioPortConfig.port();
        int i = 0;
        while (true) {
            if (i >= arrayList.size()) {
                break;
            }
            if (!arrayList.get(i).handle().equals(port.handle())) {
                i++;
            } else {
                port = arrayList.get(i);
                break;
            }
        }
        if (i == arrayList.size()) {
            return null;
        }
        android.media.AudioGainConfig gain = audioPortConfig.gain();
        if (gain != null) {
            gain = port.gain(gain.index()).buildConfig(gain.mode(), gain.channelMask(), gain.values(), gain.rampDurationMs());
        }
        return port.buildConfig(audioPortConfig.samplingRate(), audioPortConfig.channelMask(), audioPortConfig.format(), gain);
    }

    private static boolean checkFlags(android.media.AudioDevicePort audioDevicePort, int i) {
        if (audioDevicePort.role() != 2 || (i & 2) == 0) {
            return audioDevicePort.role() == 1 && (i & 1) != 0;
        }
        return true;
    }

    private static boolean checkTypes(android.media.AudioDevicePort audioDevicePort) {
        return android.media.AudioDeviceInfo.convertInternalDeviceToDeviceType(audioDevicePort.type()) != 0;
    }

    public java.util.Set<java.lang.Integer> getSupportedDeviceTypes(int i) {
        if (i != 2 && i != 1) {
            throw new java.lang.IllegalArgumentException("AudioManager.getSupportedDeviceTypes(0x" + java.lang.Integer.toHexString(i) + ") - Invalid.");
        }
        android.util.IntArray intArray = new android.util.IntArray();
        int supportedDeviceTypes = android.media.AudioSystem.getSupportedDeviceTypes(i, intArray);
        if (supportedDeviceTypes != 0) {
            android.util.Log.e(TAG, "AudioManager.getSupportedDeviceTypes(" + i + ") failed. status:" + supportedDeviceTypes);
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        for (int i2 = 0; i2 < intArray.size(); i2++) {
            hashSet.add(java.lang.Integer.valueOf(android.media.AudioDeviceInfo.convertInternalDeviceToDeviceType(intArray.get(i2))));
        }
        return hashSet;
    }

    public android.media.AudioDeviceInfo[] getDevices(int i) {
        return getDevicesStatic(i);
    }

    private static android.media.AudioDeviceInfo[] infoListFromPortList(java.util.ArrayList<android.media.AudioDevicePort> arrayList, int i) {
        java.util.Iterator<android.media.AudioDevicePort> it = arrayList.iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            android.media.AudioDevicePort next = it.next();
            if (checkTypes(next) && checkFlags(next, i)) {
                i3++;
            }
        }
        android.media.AudioDeviceInfo[] audioDeviceInfoArr = new android.media.AudioDeviceInfo[i3];
        java.util.Iterator<android.media.AudioDevicePort> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            android.media.AudioDevicePort next2 = it2.next();
            if (checkTypes(next2) && checkFlags(next2, i)) {
                audioDeviceInfoArr[i2] = new android.media.AudioDeviceInfo(next2);
                i2++;
            }
        }
        return audioDeviceInfoArr;
    }

    private static android.media.AudioDeviceInfo[] calcListDeltas(java.util.ArrayList<android.media.AudioDevicePort> arrayList, java.util.ArrayList<android.media.AudioDevicePort> arrayList2, int i) {
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            android.media.AudioDevicePort audioDevicePort = arrayList2.get(i2);
            boolean z = false;
            for (int i3 = 0; i3 < arrayList.size() && !z; i3++) {
                z = audioDevicePort.id() == arrayList.get(i3).id();
            }
            if (!z) {
                arrayList3.add(audioDevicePort);
            }
        }
        return infoListFromPortList(arrayList3, i);
    }

    public static android.media.AudioDeviceInfo[] getDevicesStatic(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (listAudioDevicePorts(arrayList) != 0) {
            return new android.media.AudioDeviceInfo[0];
        }
        return infoListFromPortList(arrayList, i);
    }

    public static android.media.AudioDeviceInfo getDeviceForPortId(int i, int i2) {
        if (i == 0) {
            return null;
        }
        for (android.media.AudioDeviceInfo audioDeviceInfo : getDevicesStatic(i2)) {
            if (audioDeviceInfo.getId() == i) {
                return audioDeviceInfo;
            }
        }
        return null;
    }

    public void registerAudioDeviceCallback(android.media.AudioDeviceCallback audioDeviceCallback, android.os.Handler handler) {
        synchronized (this.mDeviceCallbacks) {
            if (audioDeviceCallback != null) {
                if (!this.mDeviceCallbacks.containsKey(audioDeviceCallback)) {
                    if (this.mDeviceCallbacks.size() == 0) {
                        if (this.mPortListener == null) {
                            this.mPortListener = new android.media.AudioManager.OnAmPortUpdateListener();
                        }
                        registerAudioPortUpdateListener(this.mPortListener);
                    }
                    android.media.AudioManager.NativeEventHandlerDelegate nativeEventHandlerDelegate = new android.media.AudioManager.NativeEventHandlerDelegate(audioDeviceCallback, handler);
                    this.mDeviceCallbacks.put(audioDeviceCallback, nativeEventHandlerDelegate);
                    broadcastDeviceListChange_sync(nativeEventHandlerDelegate.getHandler());
                }
            }
        }
    }

    public void unregisterAudioDeviceCallback(android.media.AudioDeviceCallback audioDeviceCallback) {
        synchronized (this.mDeviceCallbacks) {
            if (this.mDeviceCallbacks.containsKey(audioDeviceCallback)) {
                this.mDeviceCallbacks.remove(audioDeviceCallback);
                if (this.mDeviceCallbacks.size() == 0) {
                    unregisterAudioPortUpdateListener(this.mPortListener);
                }
            }
        }
    }

    public static void setPortIdForMicrophones(java.util.ArrayList<android.media.MicrophoneInfo> arrayList) {
        android.media.AudioDeviceInfo[] devicesStatic = getDevicesStatic(1);
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            int length = devicesStatic.length;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                android.media.AudioDeviceInfo audioDeviceInfo = devicesStatic[i];
                if (audioDeviceInfo.getPort().type() != arrayList.get(size).getInternalDeviceType() || !android.text.TextUtils.equals(audioDeviceInfo.getAddress(), arrayList.get(size).getAddress())) {
                    i++;
                } else {
                    arrayList.get(size).setId(audioDeviceInfo.getId());
                    z = true;
                    break;
                }
            }
            if (!z) {
                android.util.Log.i(TAG, "Failed to find port id for device with type:" + arrayList.get(size).getType() + " address:" + arrayList.get(size).getAddress());
                arrayList.remove(size);
            }
        }
    }

    public static android.media.MicrophoneInfo microphoneInfoFromAudioDeviceInfo(android.media.AudioDeviceInfo audioDeviceInfo) {
        int i;
        int type = audioDeviceInfo.getType();
        if (type == 15 || type == 18) {
            i = 1;
        } else {
            i = type == 0 ? 0 : 3;
        }
        android.media.MicrophoneInfo microphoneInfo = new android.media.MicrophoneInfo(audioDeviceInfo.getPort().name() + audioDeviceInfo.getId(), audioDeviceInfo.getPort().type(), audioDeviceInfo.getAddress(), i, -1, -1, android.media.MicrophoneInfo.POSITION_UNKNOWN, android.media.MicrophoneInfo.ORIENTATION_UNKNOWN, new java.util.ArrayList(), new java.util.ArrayList(), -3.4028235E38f, -3.4028235E38f, -3.4028235E38f, 0);
        microphoneInfo.setId(audioDeviceInfo.getId());
        return microphoneInfo;
    }

    private void addMicrophonesFromAudioDeviceInfo(java.util.ArrayList<android.media.MicrophoneInfo> arrayList, java.util.HashSet<java.lang.Integer> hashSet) {
        for (android.media.AudioDeviceInfo audioDeviceInfo : getDevicesStatic(1)) {
            if (!hashSet.contains(java.lang.Integer.valueOf(audioDeviceInfo.getType()))) {
                arrayList.add(microphoneInfoFromAudioDeviceInfo(audioDeviceInfo));
            }
        }
    }

    public java.util.List<android.media.MicrophoneInfo> getMicrophones() throws java.io.IOException {
        java.util.ArrayList<android.media.MicrophoneInfo> arrayList = new java.util.ArrayList<>();
        int microphones = android.media.AudioSystem.getMicrophones(arrayList);
        java.util.HashSet<java.lang.Integer> hashSet = new java.util.HashSet<>();
        hashSet.add(18);
        if (microphones != 0) {
            if (microphones != -3) {
                android.util.Log.e(TAG, "getMicrophones failed:" + microphones);
            }
            android.util.Log.i(TAG, "fallback on device info");
            addMicrophonesFromAudioDeviceInfo(arrayList, hashSet);
            return arrayList;
        }
        setPortIdForMicrophones(arrayList);
        hashSet.add(15);
        addMicrophonesFromAudioDeviceInfo(arrayList, hashSet);
        return arrayList;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public java.util.List<android.bluetooth.BluetoothCodecConfig> getHwOffloadFormatsSupportedForA2dp() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        int hwOffloadFormatsSupportedForBluetoothMedia = android.media.AudioSystem.getHwOffloadFormatsSupportedForBluetoothMedia(128, arrayList);
        if (hwOffloadFormatsSupportedForBluetoothMedia != 0) {
            android.util.Log.e(TAG, "getHwOffloadEncodingFormatsSupportedForA2DP failed:" + hwOffloadFormatsSupportedForBluetoothMedia);
            return arrayList2;
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            int audioFormatToBluetoothSourceCodec = android.media.AudioSystem.audioFormatToBluetoothSourceCodec(((java.lang.Integer) it.next()).intValue());
            if (audioFormatToBluetoothSourceCodec != 1000000) {
                arrayList2.add(new android.bluetooth.BluetoothCodecConfig.Builder().setCodecType(audioFormatToBluetoothSourceCodec).build());
            }
        }
        return arrayList2;
    }

    private java.util.List<android.bluetooth.BluetoothLeAudioCodecConfig> getHwOffloadFormatsSupportedForLeAudio(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        int hwOffloadFormatsSupportedForBluetoothMedia = android.media.AudioSystem.getHwOffloadFormatsSupportedForBluetoothMedia(i, arrayList);
        if (hwOffloadFormatsSupportedForBluetoothMedia != 0) {
            android.util.Log.e(TAG, "getHwOffloadEncodingFormatsSupportedForLeAudio failed:" + hwOffloadFormatsSupportedForBluetoothMedia);
            return arrayList2;
        }
        java.util.Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            int audioFormatToBluetoothLeAudioSourceCodec = android.media.AudioSystem.audioFormatToBluetoothLeAudioSourceCodec(((java.lang.Integer) it.next()).intValue());
            if (audioFormatToBluetoothLeAudioSourceCodec != 1000000) {
                arrayList2.add(new android.bluetooth.BluetoothLeAudioCodecConfig.Builder().setCodecType(audioFormatToBluetoothLeAudioSourceCodec).build());
            }
        }
        return arrayList2;
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public java.util.List<android.bluetooth.BluetoothLeAudioCodecConfig> getHwOffloadFormatsSupportedForLeAudio() {
        return getHwOffloadFormatsSupportedForLeAudio(536870912);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public java.util.List<android.bluetooth.BluetoothLeAudioCodecConfig> getHwOffloadFormatsSupportedForLeBroadcast() {
        return getHwOffloadFormatsSupportedForLeAudio(536870914);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastDeviceListChange_sync(android.os.Handler handler) {
        java.util.ArrayList<android.media.AudioDevicePort> arrayList = new java.util.ArrayList<>();
        if (listAudioDevicePorts(arrayList) != 0) {
            return;
        }
        if (handler != null) {
            handler.sendMessage(android.os.Message.obtain(handler, 0, infoListFromPortList(arrayList, 3)));
        } else {
            android.media.AudioDeviceInfo[] calcListDeltas = calcListDeltas(this.mPreviousPorts, arrayList, 3);
            android.media.AudioDeviceInfo[] calcListDeltas2 = calcListDeltas(arrayList, this.mPreviousPorts, 3);
            if (calcListDeltas.length != 0 || calcListDeltas2.length != 0) {
                for (int i = 0; i < this.mDeviceCallbacks.size(); i++) {
                    android.os.Handler handler2 = this.mDeviceCallbacks.valueAt(i).getHandler();
                    if (handler2 != null) {
                        if (calcListDeltas2.length != 0) {
                            handler2.sendMessage(android.os.Message.obtain(handler2, 2, calcListDeltas2));
                        }
                        if (calcListDeltas.length != 0) {
                            handler2.sendMessage(android.os.Message.obtain(handler2, 1, calcListDeltas));
                        }
                    }
                }
            }
        }
        this.mPreviousPorts = arrayList;
    }

    private class OnAmPortUpdateListener implements android.media.AudioManager.OnAudioPortUpdateListener {
        static final java.lang.String TAG = "OnAmPortUpdateListener";

        private OnAmPortUpdateListener() {
        }

        @Override // android.media.AudioManager.OnAudioPortUpdateListener
        public void onAudioPortListUpdate(android.media.AudioPort[] audioPortArr) {
            synchronized (android.media.AudioManager.this.mDeviceCallbacks) {
                android.media.AudioManager.this.broadcastDeviceListChange_sync(null);
            }
        }

        @Override // android.media.AudioManager.OnAudioPortUpdateListener
        public void onAudioPatchListUpdate(android.media.AudioPatch[] audioPatchArr) {
        }

        @Override // android.media.AudioManager.OnAudioPortUpdateListener
        public void onServiceDied() {
            synchronized (android.media.AudioManager.this.mDeviceCallbacks) {
                android.media.AudioManager.this.broadcastDeviceListChange_sync(null);
            }
        }
    }

    @android.annotation.SystemApi
    public static abstract class AudioServerStateCallback {
        public void onAudioServerDown() {
        }

        public void onAudioServerUp() {
        }
    }

    /* renamed from: android.media.AudioManager$4, reason: invalid class name */
    class AnonymousClass4 extends android.media.IAudioServerStateDispatcher.Stub {
        AnonymousClass4() {
        }

        @Override // android.media.IAudioServerStateDispatcher
        public void dispatchAudioServerStateChange(boolean z) {
            java.util.concurrent.Executor executor;
            final android.media.AudioManager.AudioServerStateCallback audioServerStateCallback;
            synchronized (android.media.AudioManager.this.mAudioServerStateCbLock) {
                executor = android.media.AudioManager.this.mAudioServerStateExec;
                audioServerStateCallback = android.media.AudioManager.this.mAudioServerStateCb;
            }
            if (executor == null || audioServerStateCallback == null) {
                return;
            }
            if (z) {
                executor.execute(new java.lang.Runnable() { // from class: android.media.AudioManager$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.AudioManager.AudioServerStateCallback.this.onAudioServerUp();
                    }
                });
            } else {
                executor.execute(new java.lang.Runnable() { // from class: android.media.AudioManager$4$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.AudioManager.AudioServerStateCallback.this.onAudioServerDown();
                    }
                });
            }
        }
    }

    @android.annotation.SystemApi
    public void setAudioServerStateCallback(java.util.concurrent.Executor executor, android.media.AudioManager.AudioServerStateCallback audioServerStateCallback) {
        if (audioServerStateCallback == null) {
            throw new java.lang.IllegalArgumentException("Illegal null AudioServerStateCallback");
        }
        if (executor == null) {
            throw new java.lang.IllegalArgumentException("Illegal null Executor for the AudioServerStateCallback");
        }
        synchronized (this.mAudioServerStateCbLock) {
            if (this.mAudioServerStateCb != null) {
                throw new java.lang.IllegalStateException("setAudioServerStateCallback called with already registered callabck");
            }
            try {
                getService().registerAudioServerStateDispatcher(this.mAudioServerStateDispatcher);
                this.mAudioServerStateExec = executor;
                this.mAudioServerStateCb = audioServerStateCallback;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    public void clearAudioServerStateCallback() {
        synchronized (this.mAudioServerStateCbLock) {
            if (this.mAudioServerStateCb != null) {
                try {
                    getService().unregisterAudioServerStateDispatcher(this.mAudioServerStateDispatcher);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            this.mAudioServerStateExec = null;
            this.mAudioServerStateCb = null;
        }
    }

    @android.annotation.SystemApi
    public boolean isAudioServerRunning() {
        try {
            return getService().isAudioServerRunning();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setEncodedSurroundMode(int i) {
        try {
            return getService().setEncodedSurroundMode(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getEncodedSurroundMode() {
        try {
            return getService().getEncodedSurroundMode(getContext().getApplicationInfo().targetSdkVersion);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Map<java.lang.Integer, java.lang.Boolean> getSurroundFormats() {
        try {
            return getService().getSurroundFormats();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setSurroundFormatEnabled(int i, boolean z) {
        try {
            return getService().setSurroundFormatEnabled(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isSurroundFormatEnabled(int i) {
        try {
            return getService().isSurroundFormatEnabled(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<java.lang.Integer> getReportedSurroundFormats() {
        try {
            return getService().getReportedSurroundFormats();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean isHapticPlaybackSupported() {
        return android.media.AudioSystem.isHapticPlaybackSupported();
    }

    @android.annotation.SystemApi
    public boolean isUltrasoundSupported() {
        try {
            return getService().isUltrasoundSupported();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isHotwordStreamSupported(boolean z) {
        try {
            return getService().isHotwordStreamSupported(z);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    @android.annotation.SystemApi
    public static java.util.List<android.media.audiopolicy.AudioProductStrategy> getAudioProductStrategies() {
        try {
            return getService().getAudioProductStrategies();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public static java.util.List<android.media.audiopolicy.AudioVolumeGroup> getAudioVolumeGroups() {
        try {
            return getService().getAudioVolumeGroups();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public static abstract class VolumeGroupCallback {
        public void onAudioVolumeGroupChanged(int i, int i2) {
        }
    }

    @android.annotation.SystemApi
    public void registerVolumeGroupCallback(java.util.concurrent.Executor executor, android.media.AudioManager.VolumeGroupCallback volumeGroupCallback) {
        com.android.internal.util.Preconditions.checkNotNull(executor, "executor must not be null");
        com.android.internal.util.Preconditions.checkNotNull(volumeGroupCallback, "volume group change cb must not be null");
        sAudioAudioVolumeGroupChangedHandler.init();
        sAudioAudioVolumeGroupChangedHandler.registerListener(volumeGroupCallback);
    }

    @android.annotation.SystemApi
    public void unregisterVolumeGroupCallback(android.media.AudioManager.VolumeGroupCallback volumeGroupCallback) {
        com.android.internal.util.Preconditions.checkNotNull(volumeGroupCallback, "volume group change cb must not be null");
        sAudioAudioVolumeGroupChangedHandler.unregisterListener(volumeGroupCallback);
    }

    public static boolean hasHapticChannelsImpl(android.content.Context context, android.net.Uri uri) {
        android.media.MediaExtractor mediaExtractor = new android.media.MediaExtractor();
        try {
            mediaExtractor.setDataSource(context, uri, (java.util.Map<java.lang.String, java.lang.String>) null);
            for (int i = 0; i < mediaExtractor.getTrackCount(); i++) {
                android.media.MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                if (trackFormat.containsKey(android.media.MediaFormat.KEY_HAPTIC_CHANNEL_COUNT) && trackFormat.getInteger(android.media.MediaFormat.KEY_HAPTIC_CHANNEL_COUNT) > 0) {
                    return true;
                }
            }
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "hasHapticChannels failure:" + e);
        }
        return false;
    }

    public static boolean hasHapticChannels(android.content.Context context, android.net.Uri uri) {
        java.util.Objects.requireNonNull(uri);
        if (context != null) {
            return hasHapticChannelsImpl(context, uri);
        }
        android.content.Context context2 = sContext.get();
        if (context2 != null) {
            return hasHapticChannelsImpl(context2, uri);
        }
        try {
            return getService().hasHapticChannels(uri);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void setRttEnabled(boolean z) {
        try {
            getService().setRttEnabled(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void adjustSuggestedStreamVolumeForUid(int i, int i2, int i3, java.lang.String str, int i4, int i5, int i6) {
        try {
            getService().adjustSuggestedStreamVolumeForUid(i, i2, i3, str, i4, i5, android.os.UserHandle.getUserHandleForUid(i4), i6);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void adjustStreamVolumeForUid(int i, int i2, int i3, java.lang.String str, int i4, int i5, int i6) {
        try {
            getService().adjustStreamVolumeForUid(i, i2, i3, str, i4, i5, android.os.UserHandle.getUserHandleForUid(i4), i6);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public void setStreamVolumeForUid(int i, int i2, int i3, java.lang.String str, int i4, int i5, int i6) {
        try {
            getService().setStreamVolumeForUid(i, i2, i3, str, i4, i5, android.os.UserHandle.getUserHandleForUid(i4), i6);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setMultiAudioFocusEnabled(boolean z) {
        try {
            getService().setMultiAudioFocusEnabled(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAudioHwSyncForSession(int i) {
        int audioHwSyncForSession = android.media.AudioSystem.getAudioHwSyncForSession(i);
        if (audioHwSyncForSession == 0) {
            throw new java.lang.UnsupportedOperationException("HW A/V synchronization is not supported.");
        }
        return audioHwSyncForSession;
    }

    public boolean setCommunicationDevice(android.media.AudioDeviceInfo audioDeviceInfo) {
        java.util.Objects.requireNonNull(audioDeviceInfo);
        try {
            if (audioDeviceInfo.getId() == 0) {
                android.util.Log.w(TAG, "setCommunicationDevice: device not found: " + audioDeviceInfo);
                return false;
            }
            return getService().setCommunicationDevice(this.mICallBack, audioDeviceInfo.getId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearCommunicationDevice() {
        try {
            getService().setCommunicationDevice(this.mICallBack, 0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.media.AudioDeviceInfo getCommunicationDevice() {
        try {
            return getDeviceForPortId(getService().getCommunicationDevice(), 2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.media.AudioDeviceInfo> getAvailableCommunicationDevices() {
        try {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (int i : getService().getAvailableCommunicationDeviceIds()) {
                android.media.AudioDeviceInfo deviceForPortId = getDeviceForPortId(i, 2);
                if (deviceForPortId != null) {
                    arrayList.add(deviceForPortId);
                }
            }
            return arrayList;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.media.AudioProfile> getDirectProfilesForAttributes(android.media.AudioAttributes audioAttributes) {
        java.util.Objects.requireNonNull(audioAttributes);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (android.media.AudioSystem.getDirectProfilesForAttributes(audioAttributes, arrayList) != 0) {
            android.util.Log.w(TAG, "getDirectProfilesForAttributes failed.");
            return new java.util.ArrayList();
        }
        return arrayList;
    }

    public static android.media.AudioDeviceInfo getDeviceInfoFromType(int i) {
        return getDeviceInfoFromTypeAndAddress(i, null);
    }

    public static android.media.AudioDeviceInfo getDeviceInfoFromTypeAndAddress(int i, java.lang.String str) {
        android.media.AudioDeviceInfo audioDeviceInfo = null;
        for (android.media.AudioDeviceInfo audioDeviceInfo2 : getDevicesStatic(2)) {
            if (audioDeviceInfo2.getType() == i) {
                if (str == null || str.equals(audioDeviceInfo2.getAddress())) {
                    return audioDeviceInfo2;
                }
                audioDeviceInfo = audioDeviceInfo2;
            }
        }
        return audioDeviceInfo;
    }

    public void addOnCommunicationDeviceChangedListener(java.util.concurrent.Executor executor, android.media.AudioManager.OnCommunicationDeviceChangedListener onCommunicationDeviceChangedListener) {
        this.mCommDeviceChangedListenerMgr.addListener(executor, onCommunicationDeviceChangedListener, "addOnCommunicationDeviceChangedListener", new java.util.function.Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.media.CallbackUtil.DispatcherStub lambda$addOnCommunicationDeviceChangedListener$3;
                lambda$addOnCommunicationDeviceChangedListener$3 = android.media.AudioManager.this.lambda$addOnCommunicationDeviceChangedListener$3();
                return lambda$addOnCommunicationDeviceChangedListener$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.media.CallbackUtil.DispatcherStub lambda$addOnCommunicationDeviceChangedListener$3() {
        return new android.media.AudioManager.CommunicationDeviceDispatcherStub();
    }

    public void removeOnCommunicationDeviceChangedListener(android.media.AudioManager.OnCommunicationDeviceChangedListener onCommunicationDeviceChangedListener) {
        this.mCommDeviceChangedListenerMgr.removeListener(onCommunicationDeviceChangedListener, "removeOnCommunicationDeviceChangedListener");
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class CommunicationDeviceDispatcherStub extends android.media.ICommunicationDeviceDispatcher.Stub implements android.media.CallbackUtil.DispatcherStub {
        private CommunicationDeviceDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    android.media.AudioManager.getService().registerCommunicationDeviceDispatcher(this);
                } else {
                    android.media.AudioManager.getService().unregisterCommunicationDeviceDispatcher(this);
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.ICommunicationDeviceDispatcher
        public void dispatchCommunicationDeviceChanged(int i) {
            final android.media.AudioDeviceInfo deviceForPortId = android.media.AudioManager.getDeviceForPortId(i, 2);
            android.media.AudioManager.this.mCommDeviceChangedListenerMgr.callListeners(new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$CommunicationDeviceDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(java.lang.Object obj) {
                    ((android.media.AudioManager.OnCommunicationDeviceChangedListener) obj).onCommunicationDeviceChanged(android.media.AudioDeviceInfo.this);
                }
            });
        }
    }

    @android.annotation.SystemApi
    public boolean isPstnCallAudioInterceptable() {
        try {
            return getService().isPstnCallAudioInterceptable();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int getCallRedirectMode() {
        int mode = getMode();
        if (mode == 2 || mode == 4 || mode == 5) {
            return 1;
        }
        if (mode == 3 || mode == 6) {
            return 2;
        }
        return 0;
    }

    private void checkCallRedirectionFormat(android.media.AudioFormat audioFormat, boolean z) {
        if (audioFormat.getEncoding() != 2 && audioFormat.getEncoding() != 4) {
            throw new java.lang.UnsupportedOperationException(" Unsupported encoding ");
        }
        if (audioFormat.getSampleRate() < 8000 || audioFormat.getSampleRate() > 48000) {
            throw new java.lang.UnsupportedOperationException(" Unsupported sample rate ");
        }
        if (z && audioFormat.getChannelMask() != 4 && audioFormat.getChannelMask() != 12) {
            throw new java.lang.UnsupportedOperationException(" Unsupported output channel mask ");
        }
        if (!z && audioFormat.getChannelMask() != 16 && audioFormat.getChannelMask() != 12) {
            throw new java.lang.UnsupportedOperationException(" Unsupported input channel mask ");
        }
    }

    class CallIRedirectionClientInfo {
        public int redirectMode;
        public java.lang.ref.WeakReference trackOrRecord;

        CallIRedirectionClientInfo() {
        }
    }

    @android.annotation.SystemApi
    public android.media.AudioTrack getCallUplinkInjectionAudioTrack(android.media.AudioFormat audioFormat) {
        java.util.Objects.requireNonNull(audioFormat);
        checkCallRedirectionFormat(audioFormat, true);
        int callRedirectMode = getCallRedirectMode();
        if (callRedirectMode == 0) {
            throw new java.lang.IllegalStateException(" not available in mode " + android.media.AudioSystem.modeToString(getMode()));
        }
        if (callRedirectMode == 1 && !isPstnCallAudioInterceptable()) {
            throw new java.lang.UnsupportedOperationException(" PSTN Call audio not accessible ");
        }
        android.media.AudioTrack build = new android.media.AudioTrack.Builder().setAudioAttributes(new android.media.AudioAttributes.Builder().setSystemUsage(17).setContentType(1).build()).setAudioFormat(audioFormat).setCallRedirectionMode(callRedirectMode).build();
        if (build != null && build.getState() != 0) {
            synchronized (this.mCallRedirectionLock) {
                if (this.mCallRedirectionModeListener == null) {
                    this.mCallRedirectionModeListener = new android.media.AudioManager.CallInjectionModeChangedListener();
                    try {
                        addOnModeChangedListener(java.util.concurrent.Executors.newSingleThreadExecutor(), this.mCallRedirectionModeListener);
                        this.mCallIRedirectionClients = new java.util.ArrayList<>();
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(TAG, "addOnModeChangedListener failed with exception: " + e);
                        this.mCallRedirectionModeListener = null;
                        throw new java.lang.UnsupportedOperationException(" Cannot register mode listener ");
                    }
                }
                android.media.AudioManager.CallIRedirectionClientInfo callIRedirectionClientInfo = new android.media.AudioManager.CallIRedirectionClientInfo();
                callIRedirectionClientInfo.redirectMode = callRedirectMode;
                callIRedirectionClientInfo.trackOrRecord = new java.lang.ref.WeakReference(build);
                this.mCallIRedirectionClients.add(callIRedirectionClientInfo);
            }
            return build;
        }
        throw new java.lang.UnsupportedOperationException(" Cannot create the AudioTrack");
    }

    @android.annotation.SystemApi
    public android.media.AudioRecord getCallDownlinkExtractionAudioRecord(android.media.AudioFormat audioFormat) {
        java.util.Objects.requireNonNull(audioFormat);
        checkCallRedirectionFormat(audioFormat, false);
        int callRedirectMode = getCallRedirectMode();
        if (callRedirectMode == 0) {
            throw new java.lang.IllegalStateException(" not available in mode " + android.media.AudioSystem.modeToString(getMode()));
        }
        if (callRedirectMode == 1 && !isPstnCallAudioInterceptable()) {
            throw new java.lang.UnsupportedOperationException(" PSTN Call audio not accessible ");
        }
        android.media.AudioRecord build = new android.media.AudioRecord.Builder().setAudioAttributes(new android.media.AudioAttributes.Builder().setInternalCapturePreset(3).build()).setAudioFormat(audioFormat).setCallRedirectionMode(callRedirectMode).build();
        if (build != null && build.getState() != 0) {
            synchronized (this.mCallRedirectionLock) {
                if (this.mCallRedirectionModeListener == null) {
                    this.mCallRedirectionModeListener = new android.media.AudioManager.CallInjectionModeChangedListener();
                    try {
                        addOnModeChangedListener(java.util.concurrent.Executors.newSingleThreadExecutor(), this.mCallRedirectionModeListener);
                        this.mCallIRedirectionClients = new java.util.ArrayList<>();
                    } catch (java.lang.Exception e) {
                        android.util.Log.e(TAG, "addOnModeChangedListener failed with exception: " + e);
                        this.mCallRedirectionModeListener = null;
                        throw new java.lang.UnsupportedOperationException(" Cannot register mode listener ");
                    }
                }
                android.media.AudioManager.CallIRedirectionClientInfo callIRedirectionClientInfo = new android.media.AudioManager.CallIRedirectionClientInfo();
                callIRedirectionClientInfo.redirectMode = callRedirectMode;
                callIRedirectionClientInfo.trackOrRecord = new java.lang.ref.WeakReference(build);
                this.mCallIRedirectionClients.add(callIRedirectionClientInfo);
            }
            return build;
        }
        throw new java.lang.UnsupportedOperationException(" Cannot create the AudioRecord");
    }

    class CallInjectionModeChangedListener implements android.media.AudioManager.OnModeChangedListener {
        CallInjectionModeChangedListener() {
        }

        @Override // android.media.AudioManager.OnModeChangedListener
        public void onModeChanged(int i) {
            android.media.AudioManager audioManager;
            synchronized (android.media.AudioManager.this.mCallRedirectionLock) {
                java.util.Iterator it = ((java.util.ArrayList) android.media.AudioManager.this.mCallIRedirectionClients.clone()).iterator();
                while (it.hasNext()) {
                    android.media.AudioManager.CallIRedirectionClientInfo callIRedirectionClientInfo = (android.media.AudioManager.CallIRedirectionClientInfo) it.next();
                    java.lang.Object obj = callIRedirectionClientInfo.trackOrRecord.get();
                    if (obj != null && ((callIRedirectionClientInfo.redirectMode == 1 && i != 2 && i != 4 && i != 5) || (callIRedirectionClientInfo.redirectMode == 2 && i != 3 && i != 6))) {
                        if (obj instanceof android.media.AudioTrack) {
                            ((android.media.AudioTrack) obj).release();
                        } else {
                            ((android.media.AudioRecord) obj).release();
                        }
                        android.media.AudioManager.this.mCallIRedirectionClients.remove(callIRedirectionClientInfo);
                    }
                }
                if (android.media.AudioManager.this.mCallIRedirectionClients.isEmpty()) {
                    try {
                        try {
                            if (android.media.AudioManager.this.mCallRedirectionModeListener != null) {
                                android.media.AudioManager.this.removeOnModeChangedListener(android.media.AudioManager.this.mCallRedirectionModeListener);
                            }
                            android.media.AudioManager.this.mCallRedirectionModeListener = null;
                            audioManager = android.media.AudioManager.this;
                        } catch (java.lang.Exception e) {
                            android.util.Log.e(android.media.AudioManager.TAG, "removeOnModeChangedListener failed with exception: " + e);
                            android.media.AudioManager.this.mCallRedirectionModeListener = null;
                            audioManager = android.media.AudioManager.this;
                        }
                        audioManager.mCallIRedirectionClients = null;
                    } catch (java.lang.Throwable th) {
                        android.media.AudioManager.this.mCallRedirectionModeListener = null;
                        android.media.AudioManager.this.mCallIRedirectionClients = null;
                        throw th;
                    }
                }
            }
        }
    }

    @android.annotation.SystemApi
    public void muteAwaitConnection(int[] iArr, android.media.AudioDeviceAttributes audioDeviceAttributes, long j, java.util.concurrent.TimeUnit timeUnit) throws java.lang.IllegalStateException {
        if (j <= 0) {
            throw new java.lang.IllegalArgumentException("Timeout must be greater than 0");
        }
        java.util.Objects.requireNonNull(iArr);
        if (iArr.length == 0) {
            throw new java.lang.IllegalArgumentException("Array of usages to mute cannot be empty");
        }
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        java.util.Objects.requireNonNull(timeUnit);
        try {
            getService().muteAwaitConnection(iArr, audioDeviceAttributes, timeUnit.toMillis(j));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.media.AudioDeviceAttributes getMutingExpectedDevice() {
        try {
            return getService().getMutingExpectedDevice();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void cancelMuteAwaitConnection(android.media.AudioDeviceAttributes audioDeviceAttributes) throws java.lang.IllegalStateException {
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        try {
            getService().cancelMuteAwaitConnection(audioDeviceAttributes);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public static abstract class MuteAwaitConnectionCallback {
        public static final int EVENT_CANCEL = 3;
        public static final int EVENT_CONNECTION = 1;
        public static final int EVENT_TIMEOUT = 2;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface UnmuteEvent {
        }

        public void onMutedUntilConnection(android.media.AudioDeviceAttributes audioDeviceAttributes, int[] iArr) {
        }

        public void onUnmutedEvent(int i, android.media.AudioDeviceAttributes audioDeviceAttributes, int[] iArr) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @android.annotation.SystemApi
    public void registerMuteAwaitConnectionCallback(java.util.concurrent.Executor executor, android.media.AudioManager.MuteAwaitConnectionCallback muteAwaitConnectionCallback) {
        synchronized (this.mMuteAwaitConnectionListenerLock) {
            android.util.Pair addListener = android.media.CallbackUtil.addListener("registerMuteAwaitConnectionCallback", executor, muteAwaitConnectionCallback, this.mMuteAwaitConnectionListeners, this.mMuteAwaitConnDispatcherStub, new java.util.function.Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.media.AudioManager.MuteAwaitConnectionDispatcherStub lambda$registerMuteAwaitConnectionCallback$4;
                    lambda$registerMuteAwaitConnectionCallback$4 = android.media.AudioManager.this.lambda$registerMuteAwaitConnectionCallback$4();
                    return lambda$registerMuteAwaitConnectionCallback$4;
                }
            }, new java.util.function.Consumer() { // from class: android.media.AudioManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.media.AudioManager.MuteAwaitConnectionDispatcherStub) obj).register(true);
                }
            });
            this.mMuteAwaitConnectionListeners = (java.util.ArrayList) addListener.first;
            this.mMuteAwaitConnDispatcherStub = (android.media.AudioManager.MuteAwaitConnectionDispatcherStub) addListener.second;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.media.AudioManager.MuteAwaitConnectionDispatcherStub lambda$registerMuteAwaitConnectionCallback$4() {
        return new android.media.AudioManager.MuteAwaitConnectionDispatcherStub();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @android.annotation.SystemApi
    public void unregisterMuteAwaitConnectionCallback(android.media.AudioManager.MuteAwaitConnectionCallback muteAwaitConnectionCallback) {
        synchronized (this.mMuteAwaitConnectionListenerLock) {
            android.util.Pair removeListener = android.media.CallbackUtil.removeListener("unregisterMuteAwaitConnectionCallback", muteAwaitConnectionCallback, this.mMuteAwaitConnectionListeners, this.mMuteAwaitConnDispatcherStub, new java.util.function.Consumer() { // from class: android.media.AudioManager$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((android.media.AudioManager.MuteAwaitConnectionDispatcherStub) obj).register(false);
                }
            });
            this.mMuteAwaitConnectionListeners = (java.util.ArrayList) removeListener.first;
            this.mMuteAwaitConnDispatcherStub = (android.media.AudioManager.MuteAwaitConnectionDispatcherStub) removeListener.second;
        }
    }

    @android.annotation.SystemApi
    public void addAssistantServicesUids(int[] iArr) {
        try {
            getService().addAssistantServicesUids(iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void removeAssistantServicesUids(int[] iArr) {
        try {
            getService().removeAssistantServicesUids(iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int[] getAssistantServicesUids() {
        try {
            int[] assistantServicesUids = getService().getAssistantServicesUids();
            return java.util.Arrays.copyOf(assistantServicesUids, assistantServicesUids.length);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setActiveAssistantServiceUids(int[] iArr) {
        try {
            getService().setActiveAssistantServiceUids(iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int[] getActiveAssistantServicesUids() {
        try {
            int[] activeAssistantServiceUids = getService().getActiveAssistantServiceUids();
            return java.util.Arrays.copyOf(activeAssistantServiceUids, activeAssistantServiceUids.length);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static android.media.AudioHalVersionInfo getHalVersion() {
        try {
            return getService().getHalVersion();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error querying getHalVersion", e);
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.media.AudioMixerAttributes> getSupportedMixerAttributes(android.media.AudioDeviceInfo audioDeviceInfo) {
        java.util.Objects.requireNonNull(audioDeviceInfo);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        return android.media.AudioSystem.getSupportedMixerAttributes(audioDeviceInfo.getId(), arrayList) == 0 ? arrayList : new java.util.ArrayList();
    }

    public boolean setPreferredMixerAttributes(android.media.AudioAttributes audioAttributes, android.media.AudioDeviceInfo audioDeviceInfo, android.media.AudioMixerAttributes audioMixerAttributes) {
        java.util.Objects.requireNonNull(audioAttributes);
        java.util.Objects.requireNonNull(audioDeviceInfo);
        java.util.Objects.requireNonNull(audioMixerAttributes);
        try {
            return getService().setPreferredMixerAttributes(audioAttributes, audioDeviceInfo.getId(), audioMixerAttributes) == 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.media.AudioMixerAttributes getPreferredMixerAttributes(android.media.AudioAttributes audioAttributes, android.media.AudioDeviceInfo audioDeviceInfo) {
        java.util.Objects.requireNonNull(audioAttributes);
        java.util.Objects.requireNonNull(audioDeviceInfo);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int preferredMixerAttributes = android.media.AudioSystem.getPreferredMixerAttributes(audioAttributes, audioDeviceInfo.getId(), arrayList);
        if (preferredMixerAttributes == 0) {
            if (arrayList.isEmpty()) {
                return null;
            }
            return (android.media.AudioMixerAttributes) arrayList.get(0);
        }
        android.util.Log.e(TAG, "Failed calling getPreferredMixerAttributes, ret=" + preferredMixerAttributes);
        return null;
    }

    public boolean clearPreferredMixerAttributes(android.media.AudioAttributes audioAttributes, android.media.AudioDeviceInfo audioDeviceInfo) {
        java.util.Objects.requireNonNull(audioAttributes);
        java.util.Objects.requireNonNull(audioDeviceInfo);
        try {
            return getService().clearPreferredMixerAttributes(audioAttributes, audioDeviceInfo.getId()) == 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addOnPreferredMixerAttributesChangedListener(java.util.concurrent.Executor executor, android.media.AudioManager.OnPreferredMixerAttributesChangedListener onPreferredMixerAttributesChangedListener) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(onPreferredMixerAttributesChangedListener);
        this.mPrefMixerAttributesListenerMgr.addListener(executor, onPreferredMixerAttributesChangedListener, "addOnPreferredMixerAttributesChangedListener", new java.util.function.Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.media.CallbackUtil.DispatcherStub lambda$addOnPreferredMixerAttributesChangedListener$7;
                lambda$addOnPreferredMixerAttributesChangedListener$7 = android.media.AudioManager.this.lambda$addOnPreferredMixerAttributesChangedListener$7();
                return lambda$addOnPreferredMixerAttributesChangedListener$7;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.media.CallbackUtil.DispatcherStub lambda$addOnPreferredMixerAttributesChangedListener$7() {
        return new android.media.AudioManager.PreferredMixerAttributesDispatcherStub();
    }

    public void removeOnPreferredMixerAttributesChangedListener(android.media.AudioManager.OnPreferredMixerAttributesChangedListener onPreferredMixerAttributesChangedListener) {
        java.util.Objects.requireNonNull(onPreferredMixerAttributesChangedListener);
        this.mPrefMixerAttributesListenerMgr.removeListener(onPreferredMixerAttributesChangedListener, "removeOnPreferredMixerAttributesChangedListener");
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class PreferredMixerAttributesDispatcherStub extends android.media.IPreferredMixerAttributesDispatcher.Stub implements android.media.CallbackUtil.DispatcherStub {
        private PreferredMixerAttributesDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    android.media.AudioManager.getService().registerPreferredMixerAttributesDispatcher(this);
                } else {
                    android.media.AudioManager.getService().unregisterPreferredMixerAttributesDispatcher(this);
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.IPreferredMixerAttributesDispatcher
        public void dispatchPrefMixerAttributesChanged(final android.media.AudioAttributes audioAttributes, int i, final android.media.AudioMixerAttributes audioMixerAttributes) {
            final android.media.AudioDeviceInfo deviceForPortId = android.media.AudioManager.getDeviceForPortId(i, 2);
            if (deviceForPortId == null) {
                android.util.Log.d(android.media.AudioManager.TAG, "Drop preferred mixer attributes changed as the device(" + i + ") is disconnected");
            } else {
                android.media.AudioManager.this.mPrefMixerAttributesListenerMgr.callListeners(new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$PreferredMixerAttributesDispatcherStub$$ExternalSyntheticLambda0
                    @Override // android.media.CallbackUtil.CallbackMethod
                    public final void callbackMethod(java.lang.Object obj) {
                        ((android.media.AudioManager.OnPreferredMixerAttributesChangedListener) obj).onPreferredMixerAttributesChanged(android.media.AudioAttributes.this, deviceForPortId, audioMixerAttributes);
                    }
                });
            }
        }
    }

    @android.annotation.SystemApi
    public boolean supportsBluetoothVariableLatency() {
        try {
            return getService().supportsBluetoothVariableLatency();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setBluetoothVariableLatencyEnabled(boolean z) {
        try {
            getService().setBluetoothVariableLatencyEnabled(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isBluetoothVariableLatencyEnabled() {
        try {
            return getService().isBluetoothVariableLatencyEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    final class StreamAliasingDispatcherStub extends android.media.IStreamAliasingDispatcher.Stub implements android.media.CallbackUtil.DispatcherStub {
        StreamAliasingDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                android.media.AudioManager.getService().registerStreamAliasingDispatcher(this, z);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.IStreamAliasingDispatcher
        public void dispatchStreamAliasingChanged() {
            android.media.AudioManager.this.mStreamAliasingListenerMgr.callListeners(new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$StreamAliasingDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(java.lang.Object obj) {
                    ((java.lang.Runnable) obj).run();
                }
            });
        }
    }

    @android.annotation.SystemApi
    public void addOnStreamAliasingChangedListener(java.util.concurrent.Executor executor, java.lang.Runnable runnable) {
        this.mStreamAliasingListenerMgr.addListener(executor, runnable, "addOnStreamAliasingChangedListener", new java.util.function.Supplier() { // from class: android.media.AudioManager$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.media.CallbackUtil.DispatcherStub lambda$addOnStreamAliasingChangedListener$8;
                lambda$addOnStreamAliasingChangedListener$8 = android.media.AudioManager.this.lambda$addOnStreamAliasingChangedListener$8();
                return lambda$addOnStreamAliasingChangedListener$8;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.media.CallbackUtil.DispatcherStub lambda$addOnStreamAliasingChangedListener$8() {
        return new android.media.AudioManager.StreamAliasingDispatcherStub();
    }

    @android.annotation.SystemApi
    public void removeOnStreamAliasingChangedListener(java.lang.Runnable runnable) {
        this.mStreamAliasingListenerMgr.removeListener(runnable, "removeOnStreamAliasingChangedListener");
    }

    public void setNotifAliasRingForTest(boolean z) {
        try {
            getService().setNotifAliasRingForTest(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<java.lang.Integer> getIndependentStreamTypes() {
        try {
            return getService().getIndependentStreamTypes();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getStreamTypeAlias(int i) {
        try {
            return getService().getStreamTypeAlias(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVolumeControlUsingVolumeGroups() {
        try {
            return getService().isVolumeControlUsingVolumeGroups();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean shouldNotificationSoundPlay(android.media.AudioAttributes audioAttributes) {
        try {
            return getService().shouldNotificationSoundPlay((android.media.AudioAttributes) java.util.Objects.requireNonNull(audioAttributes));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class MuteAwaitConnectionDispatcherStub extends android.media.IMuteAwaitConnectionCallback.Stub {
        private MuteAwaitConnectionDispatcherStub() {
        }

        public void register(boolean z) {
            try {
                android.media.AudioManager.getService().registerMuteAwaitConnectionDispatcher(this, z);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.IMuteAwaitConnectionCallback
        public void dispatchOnMutedUntilConnection(final android.media.AudioDeviceAttributes audioDeviceAttributes, final int[] iArr) {
            android.media.CallbackUtil.callListeners(android.media.AudioManager.this.mMuteAwaitConnectionListeners, android.media.AudioManager.this.mMuteAwaitConnectionListenerLock, new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$MuteAwaitConnectionDispatcherStub$$ExternalSyntheticLambda1
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(java.lang.Object obj) {
                    ((android.media.AudioManager.MuteAwaitConnectionCallback) obj).onMutedUntilConnection(android.media.AudioDeviceAttributes.this, iArr);
                }
            });
        }

        @Override // android.media.IMuteAwaitConnectionCallback
        public void dispatchOnUnmutedEvent(final int i, final android.media.AudioDeviceAttributes audioDeviceAttributes, final int[] iArr) {
            android.media.CallbackUtil.callListeners(android.media.AudioManager.this.mMuteAwaitConnectionListeners, android.media.AudioManager.this.mMuteAwaitConnectionListenerLock, new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.AudioManager$MuteAwaitConnectionDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(java.lang.Object obj) {
                    ((android.media.AudioManager.MuteAwaitConnectionCallback) obj).onUnmutedEvent(i, audioDeviceAttributes, iArr);
                }
            });
        }
    }

    private void initPlatform() {
        try {
            android.content.Context context = getContext();
            if (context != null) {
                this.mIsAutomotive = context.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_AUTOMOTIVE);
            }
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Error querying system feature for AUTOMOTIVE", e);
        }
    }

    private boolean applyAutoHardening() {
        if (this.mIsAutomotive && android.media.audio.Flags.autoPublicVolumeApiHardening()) {
            return true;
        }
        return false;
    }

    private class NativeEventHandlerDelegate {
        private final android.os.Handler mHandler;

        NativeEventHandlerDelegate(final android.media.AudioDeviceCallback audioDeviceCallback, android.os.Handler handler) {
            android.os.Looper mainLooper;
            if (handler != null) {
                mainLooper = handler.getLooper();
            } else {
                mainLooper = android.os.Looper.getMainLooper();
            }
            if (mainLooper != null) {
                this.mHandler = new android.os.Handler(mainLooper) { // from class: android.media.AudioManager.NativeEventHandlerDelegate.1
                    @Override // android.os.Handler
                    public void handleMessage(android.os.Message message) {
                        switch (message.what) {
                            case 0:
                            case 1:
                                if (audioDeviceCallback != null) {
                                    audioDeviceCallback.onAudioDevicesAdded((android.media.AudioDeviceInfo[]) message.obj);
                                    break;
                                }
                                break;
                            case 2:
                                if (audioDeviceCallback != null) {
                                    audioDeviceCallback.onAudioDevicesRemoved((android.media.AudioDeviceInfo[]) message.obj);
                                    break;
                                }
                                break;
                            default:
                                android.util.Log.e(android.media.AudioManager.TAG, "Unknown native event type: " + message.what);
                                break;
                        }
                    }
                };
            } else {
                this.mHandler = null;
            }
        }

        android.os.Handler getHandler() {
            return this.mHandler;
        }
    }
}
