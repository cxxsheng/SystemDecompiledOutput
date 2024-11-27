package android.app;

/* loaded from: classes.dex */
public class AppOpsManager {
    public static final int ATTRIBUTION_CHAIN_ID_NONE = -1;
    public static final int ATTRIBUTION_FLAGS_NONE = 0;
    public static final int ATTRIBUTION_FLAG_ACCESSOR = 1;
    public static final int ATTRIBUTION_FLAG_INTERMEDIARY = 2;
    public static final int ATTRIBUTION_FLAG_RECEIVER = 4;
    public static final int ATTRIBUTION_FLAG_TRUSTED = 8;
    private static final int BITMASK_LEN = 3;
    public static final long CALL_BACK_ON_CHANGED_LISTENER_WITH_SWITCHED_OP_CHANGE = 148180766;
    public static final int CALL_BACK_ON_SWITCHED_OP = 2;
    private static final int COLLECT_ASYNC = 3;
    private static final int COLLECT_SELF = 1;
    private static final int COLLECT_SYNC = 2;
    private static final java.lang.String DEBUG_LOGGING_ENABLE_PROP = "appops.logging_enabled";
    private static final java.lang.String DEBUG_LOGGING_OPS_PROP = "appops.logging_ops";
    private static final java.lang.String DEBUG_LOGGING_PACKAGES_PROP = "appops.logging_packages";
    private static final java.lang.String DEBUG_LOGGING_TAG = "AppOpsManager";
    private static final int DONT_COLLECT = 0;
    public static final int FILTER_BY_ATTRIBUTION_TAG = 4;
    public static final int FILTER_BY_OP_NAMES = 8;
    public static final int FILTER_BY_PACKAGE_NAME = 2;
    public static final int FILTER_BY_UID = 1;
    private static final int FLAGS_MASK = -1;
    private static final java.lang.String FULL_LOG = "privacy_attribution_tag_full_log_enabled";
    public static final int HISTORICAL_MODE_DISABLED = 0;
    public static final int HISTORICAL_MODE_ENABLED_ACTIVE = 1;
    public static final int HISTORICAL_MODE_ENABLED_PASSIVE = 2;

    @android.annotation.SystemApi
    public static final int HISTORY_FLAGS_ALL = 3;

    @android.annotation.SystemApi
    public static final int HISTORY_FLAG_AGGREGATE = 1;

    @android.annotation.SystemApi
    public static final int HISTORY_FLAG_DISCRETE = 2;

    @android.annotation.SystemApi
    public static final int HISTORY_FLAG_GET_ATTRIBUTION_CHAINS = 4;
    public static final java.lang.String KEY_BG_STATE_SETTLE_TIME = "bg_state_settle_time";
    public static final java.lang.String KEY_FG_SERVICE_STATE_SETTLE_TIME = "fg_service_state_settle_time";
    public static final java.lang.String KEY_HISTORICAL_OPS = "historical_ops";
    public static final java.lang.String KEY_TOP_STATE_SETTLE_TIME = "top_state_settle_time";
    public static final int MAX_PRIORITY_UID_STATE = 100;
    private static final int MAX_UNFORWARDED_OPS = 10;
    public static final int MIN_PRIORITY_UID_STATE = 700;
    public static final int MODE_ALLOWED = 0;
    public static final int MODE_DEFAULT = 3;
    public static final int MODE_ERRORED = 2;
    public static final int MODE_FOREGROUND = 4;
    public static final int MODE_IGNORED = 1;
    public static final boolean NOTE_OP_COLLECTION_ENABLED = false;
    public static final int OP_ACCEPT_HANDOVER = 74;
    public static final int OP_ACCESS_ACCESSIBILITY = 88;
    public static final int OP_ACCESS_MEDIA_LOCATION = 90;
    public static final int OP_ACCESS_NOTIFICATIONS = 25;
    public static final int OP_ACCESS_RESTRICTED_SETTINGS = 119;
    public static final int OP_ACTIVATE_PLATFORM_VPN = 94;
    public static final int OP_ACTIVATE_VPN = 47;
    public static final int OP_ACTIVITY_RECOGNITION = 79;
    public static final int OP_ACTIVITY_RECOGNITION_SOURCE = 113;
    public static final int OP_ADD_VOICEMAIL = 52;
    public static final int OP_ANSWER_PHONE_CALLS = 69;
    public static final int OP_ARCHIVE_ICON_OVERLAY = 145;
    public static final int OP_ASSIST_SCREENSHOT = 50;
    public static final int OP_ASSIST_STRUCTURE = 49;
    public static final int OP_AUDIO_ACCESSIBILITY_VOLUME = 64;
    public static final int OP_AUDIO_ALARM_VOLUME = 37;
    public static final int OP_AUDIO_BLUETOOTH_VOLUME = 39;
    public static final int OP_AUDIO_MASTER_VOLUME = 33;
    public static final int OP_AUDIO_MEDIA_VOLUME = 36;
    public static final int OP_AUDIO_NOTIFICATION_VOLUME = 38;
    public static final int OP_AUDIO_RING_VOLUME = 35;
    public static final int OP_AUDIO_VOICE_VOLUME = 34;
    public static final int OP_AUTO_REVOKE_MANAGED_BY_INSTALLER = 98;
    public static final int OP_AUTO_REVOKE_PERMISSIONS_IF_UNUSED = 97;
    public static final int OP_BIND_ACCESSIBILITY_SERVICE = 73;
    public static final int OP_BLUETOOTH_ADVERTISE = 114;
    public static final int OP_BLUETOOTH_CONNECT = 111;
    public static final int OP_BLUETOOTH_SCAN = 77;
    public static final int OP_BODY_SENSORS = 56;
    public static final int OP_CALL_PHONE = 13;
    public static final int OP_CAMERA = 26;
    public static final int OP_CAMERA_SANDBOXED = 134;
    public static final int OP_CAPTURE_CONSENTLESS_BUGREPORT_ON_USERDEBUG_BUILD = 131;
    public static final int OP_CHANGE_WIFI_STATE = 71;
    public static final int OP_COARSE_LOCATION = 0;
    public static final int OP_COARSE_LOCATION_SOURCE = 109;
    public static final int OP_CREATE_ACCESSIBILITY_OVERLAY = 138;
    private static final int OP_DEPRECATED_1 = 96;
    private static final int OP_DEPRECATED_2 = 132;
    public static final int OP_EMERGENCY_LOCATION = 147;
    public static final int OP_ENABLE_MOBILE_DATA_BY_USER = 140;
    public static final int OP_ESTABLISH_VPN_MANAGER = 118;
    public static final int OP_ESTABLISH_VPN_SERVICE = 117;
    public static final int OP_FINE_LOCATION = 1;
    public static final int OP_FINE_LOCATION_SOURCE = 108;

    @android.annotation.SystemApi
    public static final int OP_FLAGS_ALL = 31;

    @android.annotation.SystemApi
    public static final int OP_FLAGS_ALL_TRUSTED = 13;

    @android.annotation.SystemApi
    public static final int OP_FLAG_SELF = 1;

    @android.annotation.SystemApi
    public static final int OP_FLAG_TRUSTED_PROXIED = 8;

    @android.annotation.SystemApi
    public static final int OP_FLAG_TRUSTED_PROXY = 2;

    @android.annotation.SystemApi
    public static final int OP_FLAG_UNTRUSTED_PROXIED = 16;

    @android.annotation.SystemApi
    public static final int OP_FLAG_UNTRUSTED_PROXY = 4;
    public static final int OP_FOREGROUND_SERVICE_SPECIAL_USE = 127;
    public static final int OP_GET_ACCOUNTS = 62;
    public static final int OP_GET_USAGE_STATS = 43;
    public static final int OP_GPS = 2;
    public static final int OP_INSTANT_APP_START_FOREGROUND = 68;
    public static final int OP_INTERACT_ACROSS_PROFILES = 93;
    public static final int OP_LEGACY_STORAGE = 87;
    public static final int OP_LOADER_USAGE_STATS = 95;
    public static final int OP_MANAGE_CREDENTIALS = 104;
    public static final int OP_MANAGE_EXTERNAL_STORAGE = 92;
    public static final int OP_MANAGE_IPSEC_TUNNELS = 75;
    public static final int OP_MANAGE_MEDIA = 110;
    public static final int OP_MANAGE_ONGOING_CALLS = 103;
    public static final int OP_MEDIA_ROUTING_CONTROL = 139;
    public static final int OP_MOCK_LOCATION = 58;
    public static final int OP_MONITOR_HIGH_POWER_LOCATION = 42;
    public static final int OP_MONITOR_LOCATION = 41;
    public static final int OP_MUTE_MICROPHONE = 44;
    public static final int OP_NEARBY_WIFI_DEVICES = 116;
    public static final int OP_NEIGHBORING_CELLS = 12;
    public static final int OP_NONE = -1;
    public static final int OP_NO_ISOLATED_STORAGE = 99;
    public static final int OP_PHONE_CALL_CAMERA = 101;
    public static final int OP_PHONE_CALL_MICROPHONE = 100;
    public static final int OP_PICTURE_IN_PICTURE = 67;
    public static final int OP_PLAY_AUDIO = 28;
    public static final int OP_POST_NOTIFICATION = 11;
    public static final int OP_PROCESS_OUTGOING_CALLS = 54;
    public static final int OP_PROJECT_MEDIA = 46;
    public static final int OP_QUERY_ALL_PACKAGES = 91;
    public static final int OP_RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER = 142;
    public static final int OP_READ_CALENDAR = 8;
    public static final int OP_READ_CALL_LOG = 6;
    public static final int OP_READ_CELL_BROADCASTS = 57;
    public static final int OP_READ_CLIPBOARD = 29;
    public static final int OP_READ_CONTACTS = 4;
    public static final int OP_READ_DEVICE_IDENTIFIERS = 89;
    public static final int OP_READ_EXTERNAL_STORAGE = 59;
    public static final int OP_READ_ICC_SMS = 21;
    public static final int OP_READ_MEDIA_AUDIO = 81;
    public static final int OP_READ_MEDIA_IMAGES = 85;
    public static final int OP_READ_MEDIA_VIDEO = 83;
    public static final int OP_READ_MEDIA_VISUAL_USER_SELECTED = 123;
    public static final int OP_READ_PHONE_NUMBERS = 65;
    public static final int OP_READ_PHONE_STATE = 51;
    public static final int OP_READ_SMS = 14;
    public static final int OP_READ_SYSTEM_GRAMMATICAL_GENDER = 143;
    public static final int OP_READ_WRITE_HEALTH_DATA = 126;
    public static final int OP_RECEIVE_AMBIENT_TRIGGER_AUDIO = 120;
    public static final int OP_RECEIVE_EMERGECY_SMS = 17;
    public static final int OP_RECEIVE_EXPLICIT_USER_INTERACTION_AUDIO = 121;
    public static final int OP_RECEIVE_MMS = 18;
    public static final int OP_RECEIVE_SANDBOXED_DETECTION_TRAINING_DATA = 137;
    public static final int OP_RECEIVE_SANDBOX_TRIGGER_AUDIO = 136;
    public static final int OP_RECEIVE_SMS = 16;
    public static final int OP_RECEIVE_WAP_PUSH = 19;
    public static final int OP_RECORD_AUDIO = 27;
    public static final int OP_RECORD_AUDIO_HOTWORD = 102;
    public static final int OP_RECORD_AUDIO_OUTPUT = 106;
    public static final int OP_RECORD_AUDIO_SANDBOXED = 135;
    public static final int OP_RECORD_INCOMING_PHONE_AUDIO = 115;
    public static final int OP_REQUEST_DELETE_PACKAGES = 72;
    public static final int OP_REQUEST_INSTALL_PACKAGES = 66;
    public static final int OP_RESERVED_FOR_TESTING = 141;
    public static final int OP_RUN_ANY_IN_BACKGROUND = 70;
    public static final int OP_RUN_BACKUP_JOBS = 144;
    public static final int OP_RUN_IN_BACKGROUND = 63;
    public static final int OP_RUN_USER_INITIATED_JOBS = 122;
    public static final int OP_SCHEDULE_EXACT_ALARM = 107;
    public static final int OP_SEND_SMS = 20;
    public static final int OP_SMS_FINANCIAL_TRANSACTIONS = 80;
    public static final int OP_START_FOREGROUND = 76;
    public static final int OP_SYSTEM_ALERT_WINDOW = 24;
    public static final int OP_SYSTEM_EXEMPT_FROM_ACTIVITY_BG_START_RESTRICTION = 130;
    public static final int OP_SYSTEM_EXEMPT_FROM_DISMISSIBLE_NOTIFICATIONS = 125;
    public static final int OP_SYSTEM_EXEMPT_FROM_HIBERNATION = 129;
    public static final int OP_SYSTEM_EXEMPT_FROM_POWER_RESTRICTIONS = 128;
    public static final int OP_SYSTEM_EXEMPT_FROM_SUSPENSION = 124;
    public static final int OP_TAKE_AUDIO_FOCUS = 32;
    public static final int OP_TAKE_MEDIA_BUTTONS = 31;
    public static final int OP_TOAST_WINDOW = 45;
    public static final int OP_TURN_SCREEN_ON = 61;
    public static final int OP_UNARCHIVAL_CONFIRMATION = 146;
    public static final int OP_USE_BIOMETRIC = 78;
    public static final int OP_USE_FINGERPRINT = 55;
    public static final int OP_USE_FULL_SCREEN_INTENT = 133;
    public static final int OP_USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER = 105;
    public static final int OP_USE_SIP = 53;
    public static final int OP_UWB_RANGING = 112;
    public static final int OP_VIBRATE = 3;
    public static final int OP_WAKE_LOCK = 40;
    public static final int OP_WIFI_SCAN = 10;
    public static final int OP_WRITE_CALENDAR = 9;
    public static final int OP_WRITE_CALL_LOG = 7;
    public static final int OP_WRITE_CLIPBOARD = 30;
    public static final int OP_WRITE_CONTACTS = 5;
    public static final int OP_WRITE_EXTERNAL_STORAGE = 60;
    public static final int OP_WRITE_ICC_SMS = 22;
    public static final int OP_WRITE_MEDIA_AUDIO = 82;
    public static final int OP_WRITE_MEDIA_IMAGES = 86;
    public static final int OP_WRITE_MEDIA_VIDEO = 84;
    public static final int OP_WRITE_SETTINGS = 23;
    public static final int OP_WRITE_SMS = 15;
    public static final int OP_WRITE_WALLPAPER = 48;
    public static final int SAMPLING_STRATEGY_BOOT_TIME_SAMPLING = 3;
    public static final int SAMPLING_STRATEGY_DEFAULT = 0;
    public static final int SAMPLING_STRATEGY_RARELY_USED = 2;
    public static final int SAMPLING_STRATEGY_UNIFORM = 1;
    public static final int SAMPLING_STRATEGY_UNIFORM_OPS = 4;
    public static final long SECURITY_EXCEPTION_ON_INVALID_ATTRIBUTION_TAG_CHANGE = 151105954;
    private static final byte SHOULD_COLLECT_NOTE_OP = 2;
    private static final byte SHOULD_COLLECT_NOTE_OP_NOT_INITIALIZED = 0;
    private static final byte SHOULD_NOT_COLLECT_NOTE_OP = 1;

    @android.annotation.SystemApi
    public static final int UID_STATE_BACKGROUND = 600;

    @android.annotation.SystemApi
    public static final int UID_STATE_CACHED = 700;

    @android.annotation.SystemApi
    public static final int UID_STATE_FOREGROUND = 500;

    @android.annotation.SystemApi
    public static final int UID_STATE_FOREGROUND_SERVICE = 400;

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final int UID_STATE_FOREGROUND_SERVICE_LOCATION = 300;
    public static final int UID_STATE_MAX_LAST_NON_RESTRICTED = 500;
    private static final int UID_STATE_OFFSET = 31;

    @android.annotation.SystemApi
    public static final int UID_STATE_PERSISTENT = 100;

    @android.annotation.SystemApi
    public static final int UID_STATE_TOP = 200;
    public static final int WATCH_FOREGROUND_CHANGES = 1;
    public static final int _NUM_OP = 148;
    static android.os.IBinder sClientId;
    private static com.android.internal.app.MessageSamplingConfig sConfig;
    private static android.app.AppOpsManager.OnOpNotedCallback sOnOpNotedCallback;
    static com.android.internal.app.IAppOpsService sService;
    final android.content.Context mContext;
    final com.android.internal.app.IAppOpsService mService;
    private android.permission.PermissionUsageHelper mUsageHelper;
    private static java.lang.Boolean sFullLog = null;
    private static final java.lang.Object sLock = new java.lang.Object();
    private static java.util.ArrayList<android.app.AsyncNotedAppOp> sUnforwardedOps = new java.util.ArrayList<>();
    private static android.app.AppOpsManager.OnOpNotedCallback sMessageCollector = new android.app.AppOpsManager.OnOpNotedCallback() { // from class: android.app.AppOpsManager.1
        @Override // android.app.AppOpsManager.OnOpNotedCallback
        public void onNoted(android.app.SyncNotedAppOp syncNotedAppOp) {
            reportStackTraceIfNeeded(syncNotedAppOp);
        }

        @Override // android.app.AppOpsManager.OnOpNotedCallback
        public void onAsyncNoted(android.app.AsyncNotedAppOp asyncNotedAppOp) {
        }

        @Override // android.app.AppOpsManager.OnOpNotedCallback
        public void onSelfNoted(android.app.SyncNotedAppOp syncNotedAppOp) {
            reportStackTraceIfNeeded(syncNotedAppOp);
        }

        private void reportStackTraceIfNeeded(android.app.SyncNotedAppOp syncNotedAppOp) {
            if (!android.app.AppOpsManager.isCollectingStackTraces()) {
                return;
            }
            com.android.internal.app.MessageSamplingConfig messageSamplingConfig = android.app.AppOpsManager.sConfig;
            if (android.app.AppOpsManager.leftCircularDistance(android.app.AppOpsManager.strOpToOp(syncNotedAppOp.getOp()), messageSamplingConfig.getSampledOpCode(), 148) <= messageSamplingConfig.getAcceptableLeftDistance() || messageSamplingConfig.getExpirationTimeSinceBootMillis() < android.os.SystemClock.elapsedRealtime()) {
                java.lang.String formattedStackTrace = android.app.AppOpsManager.getFormattedStackTrace();
                try {
                    java.lang.String currentOpPackageName = android.app.ActivityThread.currentOpPackageName();
                    com.android.internal.app.IAppOpsService service = android.app.AppOpsManager.getService();
                    if (currentOpPackageName == null) {
                        currentOpPackageName = "";
                    }
                    android.app.AppOpsManager.sConfig = service.reportRuntimeAppOpAccessMessageAndGetConfig(currentOpPackageName, syncNotedAppOp, formattedStackTrace);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }
    };
    public static final java.lang.String[] MODE_NAMES = {"allow", "ignore", "deny", "default", "foreground"};
    public static final int[] UID_STATES = {100, 200, 300, 400, 500, 600, 700};
    private static final byte[] sAppOpsToNote = new byte[148];
    private static final int[] RUNTIME_PERMISSION_OPS = {4, 5, 62, 8, 9, 20, 16, 14, 19, 18, 57, 59, 60, 90, 0, 1, 51, 65, 13, 6, 7, 52, 53, 54, 69, 74, 27, 26, 56, 79, 81, 83, 85, 123, 77, 111, 114, 112, 116, 11};
    private static final int[] APP_OP_PERMISSION_PACKAGE_OPS = {25, 24, 23, 43, 66, 76, 80, 75, 68, 95};
    private static final int[] APP_OP_PERMISSION_UID_OPS = {92, 93, 103, 105, 107, 110, 61, 122, 127, 131, 133, 136, 137, 139, 143, 144, 145, 146};
    public static final java.lang.String OPSTR_COARSE_LOCATION = "android:coarse_location";
    public static final java.lang.String OPSTR_FINE_LOCATION = "android:fine_location";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_GPS = "android:gps";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_VIBRATE = "android:vibrate";
    public static final java.lang.String OPSTR_READ_CONTACTS = "android:read_contacts";
    public static final java.lang.String OPSTR_WRITE_CONTACTS = "android:write_contacts";
    public static final java.lang.String OPSTR_READ_CALL_LOG = "android:read_call_log";
    public static final java.lang.String OPSTR_WRITE_CALL_LOG = "android:write_call_log";
    public static final java.lang.String OPSTR_READ_CALENDAR = "android:read_calendar";
    public static final java.lang.String OPSTR_WRITE_CALENDAR = "android:write_calendar";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_WIFI_SCAN = "android:wifi_scan";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_POST_NOTIFICATION = "android:post_notification";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_NEIGHBORING_CELLS = "android:neighboring_cells";
    public static final java.lang.String OPSTR_CALL_PHONE = "android:call_phone";
    public static final java.lang.String OPSTR_READ_SMS = "android:read_sms";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_WRITE_SMS = "android:write_sms";
    public static final java.lang.String OPSTR_RECEIVE_SMS = "android:receive_sms";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_RECEIVE_EMERGENCY_BROADCAST = "android:receive_emergency_broadcast";
    public static final java.lang.String OPSTR_RECEIVE_MMS = "android:receive_mms";
    public static final java.lang.String OPSTR_RECEIVE_WAP_PUSH = "android:receive_wap_push";
    public static final java.lang.String OPSTR_SEND_SMS = "android:send_sms";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_READ_ICC_SMS = "android:read_icc_sms";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_WRITE_ICC_SMS = "android:write_icc_sms";
    public static final java.lang.String OPSTR_WRITE_SETTINGS = "android:write_settings";
    public static final java.lang.String OPSTR_SYSTEM_ALERT_WINDOW = "android:system_alert_window";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_ACCESS_NOTIFICATIONS = "android:access_notifications";
    public static final java.lang.String OPSTR_CAMERA = "android:camera";
    public static final java.lang.String OPSTR_RECORD_AUDIO = "android:record_audio";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_PLAY_AUDIO = "android:play_audio";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_READ_CLIPBOARD = "android:read_clipboard";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_WRITE_CLIPBOARD = "android:write_clipboard";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_TAKE_MEDIA_BUTTONS = "android:take_media_buttons";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_TAKE_AUDIO_FOCUS = "android:take_audio_focus";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_AUDIO_MASTER_VOLUME = "android:audio_master_volume";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_AUDIO_VOICE_VOLUME = "android:audio_voice_volume";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_AUDIO_RING_VOLUME = "android:audio_ring_volume";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_AUDIO_MEDIA_VOLUME = "android:audio_media_volume";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_AUDIO_ALARM_VOLUME = "android:audio_alarm_volume";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_AUDIO_NOTIFICATION_VOLUME = "android:audio_notification_volume";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_AUDIO_BLUETOOTH_VOLUME = "android:audio_bluetooth_volume";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_WAKE_LOCK = "android:wake_lock";
    public static final java.lang.String OPSTR_MONITOR_LOCATION = "android:monitor_location";
    public static final java.lang.String OPSTR_MONITOR_HIGH_POWER_LOCATION = "android:monitor_location_high_power";
    public static final java.lang.String OPSTR_GET_USAGE_STATS = "android:get_usage_stats";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_MUTE_MICROPHONE = "android:mute_microphone";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_TOAST_WINDOW = "android:toast_window";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_PROJECT_MEDIA = "android:project_media";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_ACTIVATE_VPN = "android:activate_vpn";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_WRITE_WALLPAPER = "android:write_wallpaper";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_ASSIST_STRUCTURE = "android:assist_structure";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_ASSIST_SCREENSHOT = "android:assist_screenshot";
    public static final java.lang.String OPSTR_READ_PHONE_STATE = "android:read_phone_state";
    public static final java.lang.String OPSTR_ADD_VOICEMAIL = "android:add_voicemail";
    public static final java.lang.String OPSTR_USE_SIP = "android:use_sip";
    public static final java.lang.String OPSTR_PROCESS_OUTGOING_CALLS = "android:process_outgoing_calls";
    public static final java.lang.String OPSTR_USE_FINGERPRINT = "android:use_fingerprint";
    public static final java.lang.String OPSTR_BODY_SENSORS = "android:body_sensors";
    public static final java.lang.String OPSTR_READ_CELL_BROADCASTS = "android:read_cell_broadcasts";
    public static final java.lang.String OPSTR_MOCK_LOCATION = "android:mock_location";
    public static final java.lang.String OPSTR_READ_EXTERNAL_STORAGE = "android:read_external_storage";
    public static final java.lang.String OPSTR_WRITE_EXTERNAL_STORAGE = "android:write_external_storage";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_TURN_SCREEN_ON = "android:turn_screen_on";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_GET_ACCOUNTS = "android:get_accounts";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_RUN_IN_BACKGROUND = "android:run_in_background";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_AUDIO_ACCESSIBILITY_VOLUME = "android:audio_accessibility_volume";
    public static final java.lang.String OPSTR_READ_PHONE_NUMBERS = "android:read_phone_numbers";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_REQUEST_INSTALL_PACKAGES = "android:request_install_packages";
    public static final java.lang.String OPSTR_PICTURE_IN_PICTURE = "android:picture_in_picture";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_INSTANT_APP_START_FOREGROUND = "android:instant_app_start_foreground";
    public static final java.lang.String OPSTR_ANSWER_PHONE_CALLS = "android:answer_phone_calls";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_RUN_ANY_IN_BACKGROUND = "android:run_any_in_background";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_CHANGE_WIFI_STATE = "android:change_wifi_state";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_REQUEST_DELETE_PACKAGES = "android:request_delete_packages";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_BIND_ACCESSIBILITY_SERVICE = "android:bind_accessibility_service";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_ACCEPT_HANDOVER = "android:accept_handover";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_MANAGE_IPSEC_TUNNELS = "android:manage_ipsec_tunnels";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_START_FOREGROUND = "android:start_foreground";
    public static final java.lang.String OPSTR_BLUETOOTH_SCAN = "android:bluetooth_scan";
    public static final java.lang.String OPSTR_USE_BIOMETRIC = "android:use_biometric";
    public static final java.lang.String OPSTR_ACTIVITY_RECOGNITION = "android:activity_recognition";
    public static final java.lang.String OPSTR_SMS_FINANCIAL_TRANSACTIONS = "android:sms_financial_transactions";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_READ_MEDIA_AUDIO = "android:read_media_audio";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_WRITE_MEDIA_AUDIO = "android:write_media_audio";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_READ_MEDIA_VIDEO = "android:read_media_video";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_WRITE_MEDIA_VIDEO = "android:write_media_video";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_READ_MEDIA_IMAGES = "android:read_media_images";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_WRITE_MEDIA_IMAGES = "android:write_media_images";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_LEGACY_STORAGE = "android:legacy_storage";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_ACCESS_ACCESSIBILITY = "android:access_accessibility";
    public static final java.lang.String OPSTR_READ_DEVICE_IDENTIFIERS = "android:read_device_identifiers";
    public static final java.lang.String OPSTR_ACCESS_MEDIA_LOCATION = "android:access_media_location";
    public static final java.lang.String OPSTR_QUERY_ALL_PACKAGES = "android:query_all_packages";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_MANAGE_EXTERNAL_STORAGE = "android:manage_external_storage";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_INTERACT_ACROSS_PROFILES = "android:interact_across_profiles";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_ACTIVATE_PLATFORM_VPN = "android:activate_platform_vpn";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_LOADER_USAGE_STATS = "android:loader_usage_stats";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_AUTO_REVOKE_PERMISSIONS_IF_UNUSED = "android:auto_revoke_permissions_if_unused";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_AUTO_REVOKE_MANAGED_BY_INSTALLER = "android:auto_revoke_managed_by_installer";

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final java.lang.String OPSTR_NO_ISOLATED_STORAGE = "android:no_isolated_storage";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_PHONE_CALL_MICROPHONE = "android:phone_call_microphone";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_PHONE_CALL_CAMERA = "android:phone_call_camera";
    public static final java.lang.String OPSTR_RECORD_AUDIO_HOTWORD = "android:record_audio_hotword";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_MANAGE_ONGOING_CALLS = "android:manage_ongoing_calls";
    public static final java.lang.String OPSTR_MANAGE_CREDENTIALS = "android:manage_credentials";
    public static final java.lang.String OPSTR_USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER = "android:use_icc_auth_with_device_identifier";
    public static final java.lang.String OPSTR_RECORD_AUDIO_OUTPUT = "android:record_audio_output";
    public static final java.lang.String OPSTR_SCHEDULE_EXACT_ALARM = "android:schedule_exact_alarm";
    public static final java.lang.String OPSTR_FINE_LOCATION_SOURCE = "android:fine_location_source";
    public static final java.lang.String OPSTR_COARSE_LOCATION_SOURCE = "android:coarse_location_source";
    public static final java.lang.String OPSTR_MANAGE_MEDIA = "android:manage_media";
    public static final java.lang.String OPSTR_BLUETOOTH_CONNECT = "android:bluetooth_connect";
    public static final java.lang.String OPSTR_UWB_RANGING = "android:uwb_ranging";
    public static final java.lang.String OPSTR_ACTIVITY_RECOGNITION_SOURCE = "android:activity_recognition_source";
    public static final java.lang.String OPSTR_BLUETOOTH_ADVERTISE = "android:bluetooth_advertise";
    public static final java.lang.String OPSTR_RECORD_INCOMING_PHONE_AUDIO = "android:record_incoming_phone_audio";
    public static final java.lang.String OPSTR_NEARBY_WIFI_DEVICES = "android:nearby_wifi_devices";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_ESTABLISH_VPN_SERVICE = "android:establish_vpn_service";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_ESTABLISH_VPN_MANAGER = "android:establish_vpn_manager";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_ACCESS_RESTRICTED_SETTINGS = "android:access_restricted_settings";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_RECEIVE_AMBIENT_TRIGGER_AUDIO = "android:receive_ambient_trigger_audio";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_RECEIVE_EXPLICIT_USER_INTERACTION_AUDIO = "android:receive_explicit_user_interaction_audio";
    public static final java.lang.String OPSTR_RUN_USER_INITIATED_JOBS = "android:run_user_initiated_jobs";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_READ_MEDIA_VISUAL_USER_SELECTED = "android:read_media_visual_user_selected";
    public static final java.lang.String OPSTR_SYSTEM_EXEMPT_FROM_SUSPENSION = "android:system_exempt_from_suspension";
    public static final java.lang.String OPSTR_SYSTEM_EXEMPT_FROM_DISMISSIBLE_NOTIFICATIONS = "android:system_exempt_from_dismissible_notifications";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_READ_WRITE_HEALTH_DATA = "android:read_write_health_data";
    public static final java.lang.String OPSTR_FOREGROUND_SERVICE_SPECIAL_USE = "android:foreground_service_special_use";
    public static final java.lang.String OPSTR_SYSTEM_EXEMPT_FROM_POWER_RESTRICTIONS = "android:system_exempt_from_power_restrictions";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_SYSTEM_EXEMPT_FROM_HIBERNATION = "android:system_exempt_from_hibernation";
    public static final java.lang.String OPSTR_SYSTEM_EXEMPT_FROM_ACTIVITY_BG_START_RESTRICTION = "android:system_exempt_from_activity_bg_start_restriction";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_CAPTURE_CONSENTLESS_BUGREPORT_ON_USERDEBUG_BUILD = "android:capture_consentless_bugreport_on_userdebug_build";
    public static final java.lang.String OPSTR_DEPRECATED_2 = "android:deprecated_2";
    public static final java.lang.String OPSTR_USE_FULL_SCREEN_INTENT = "android:use_full_screen_intent";
    public static final java.lang.String OPSTR_CAMERA_SANDBOXED = "android:camera_sandboxed";
    public static final java.lang.String OPSTR_RECORD_AUDIO_SANDBOXED = "android:record_audio_sandboxed";
    public static final java.lang.String OPSTR_RECEIVE_SANDBOX_TRIGGER_AUDIO = "android:receive_sandbox_trigger_audio";
    public static final java.lang.String OPSTR_RECEIVE_SANDBOXED_DETECTION_TRAINING_DATA = "android:RECEIVE_SANDBOXED_DETECTION_TRAINING_DATA";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_CREATE_ACCESSIBILITY_OVERLAY = "android:create_accessibility_overlay";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_MEDIA_ROUTING_CONTROL = "android:media_routing_control";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_ENABLE_MOBILE_DATA_BY_USER = "android:enable_mobile_data_by_user";
    public static final java.lang.String OPSTR_RESERVED_FOR_TESTING = "android:reserved_for_testing";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER = "android:rapid_clear_notifications_by_listener";
    public static final java.lang.String OPSTR_READ_SYSTEM_GRAMMATICAL_GENDER = "android:read_system_grammatical_gender";
    public static final java.lang.String OPSTR_RUN_BACKUP_JOBS = "android:run_backup_jobs";
    public static final java.lang.String OPSTR_ARCHIVE_ICON_OVERLAY = "android:archive_icon_overlay";
    public static final java.lang.String OPSTR_UNARCHIVAL_CONFIRMATION = "android:unarchival_support";

    @android.annotation.SystemApi
    public static final java.lang.String OPSTR_EMERGENCY_LOCATION = "android:emergency_location";
    static final android.app.AppOpInfo[] sAppOpInfos = {new android.app.AppOpInfo.Builder(0, OPSTR_COARSE_LOCATION, "COARSE_LOCATION").setPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION).setRestriction(android.os.UserManager.DISALLOW_SHARE_LOCATION).setAllowSystemRestrictionBypass(new android.app.AppOpsManager.RestrictionBypass(true, false, false)).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(1, OPSTR_FINE_LOCATION, "FINE_LOCATION").setPermission(android.Manifest.permission.ACCESS_FINE_LOCATION).setRestriction(android.os.UserManager.DISALLOW_SHARE_LOCATION).setAllowSystemRestrictionBypass(new android.app.AppOpsManager.RestrictionBypass(true, false, false)).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(2, OPSTR_GPS, "GPS").setSwitchCode(0).setRestriction(android.os.UserManager.DISALLOW_SHARE_LOCATION).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(3, OPSTR_VIBRATE, "VIBRATE").setSwitchCode(3).setPermission(android.Manifest.permission.VIBRATE).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(4, OPSTR_READ_CONTACTS, "READ_CONTACTS").setPermission(android.Manifest.permission.READ_CONTACTS).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(5, OPSTR_WRITE_CONTACTS, "WRITE_CONTACTS").setPermission(android.Manifest.permission.WRITE_CONTACTS).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(6, OPSTR_READ_CALL_LOG, "READ_CALL_LOG").setPermission(android.Manifest.permission.READ_CALL_LOG).setRestriction(android.os.UserManager.DISALLOW_OUTGOING_CALLS).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(7, OPSTR_WRITE_CALL_LOG, "WRITE_CALL_LOG").setPermission(android.Manifest.permission.WRITE_CALL_LOG).setRestriction(android.os.UserManager.DISALLOW_OUTGOING_CALLS).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(8, OPSTR_READ_CALENDAR, "READ_CALENDAR").setPermission(android.Manifest.permission.READ_CALENDAR).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(9, OPSTR_WRITE_CALENDAR, "WRITE_CALENDAR").setPermission(android.Manifest.permission.WRITE_CALENDAR).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(10, OPSTR_WIFI_SCAN, "WIFI_SCAN").setSwitchCode(0).setPermission(android.Manifest.permission.ACCESS_WIFI_STATE).setRestriction(android.os.UserManager.DISALLOW_SHARE_LOCATION).setAllowSystemRestrictionBypass(new android.app.AppOpsManager.RestrictionBypass(false, true, false)).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(11, OPSTR_POST_NOTIFICATION, "POST_NOTIFICATION").setPermission(android.Manifest.permission.POST_NOTIFICATIONS).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(12, OPSTR_NEIGHBORING_CELLS, "NEIGHBORING_CELLS").setSwitchCode(0).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(13, OPSTR_CALL_PHONE, "CALL_PHONE").setSwitchCode(13).setPermission(android.Manifest.permission.CALL_PHONE).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(14, OPSTR_READ_SMS, "READ_SMS").setPermission(android.Manifest.permission.READ_SMS).setRestriction(android.os.UserManager.DISALLOW_SMS).setDefaultMode(0).setDisableReset(true).build(), new android.app.AppOpInfo.Builder(15, OPSTR_WRITE_SMS, "WRITE_SMS").setRestriction(android.os.UserManager.DISALLOW_SMS).setDefaultMode(1).setDisableReset(true).build(), new android.app.AppOpInfo.Builder(16, OPSTR_RECEIVE_SMS, "RECEIVE_SMS").setPermission(android.Manifest.permission.RECEIVE_SMS).setRestriction(android.os.UserManager.DISALLOW_SMS).setDefaultMode(0).setDisableReset(true).build(), new android.app.AppOpInfo.Builder(17, OPSTR_RECEIVE_EMERGENCY_BROADCAST, "RECEIVE_EMERGENCY_BROADCAST").setSwitchCode(16).setPermission(android.Manifest.permission.RECEIVE_EMERGENCY_BROADCAST).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(18, OPSTR_RECEIVE_MMS, "RECEIVE_MMS").setPermission(android.Manifest.permission.RECEIVE_MMS).setRestriction(android.os.UserManager.DISALLOW_SMS).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(19, OPSTR_RECEIVE_WAP_PUSH, "RECEIVE_WAP_PUSH").setPermission(android.Manifest.permission.RECEIVE_WAP_PUSH).setDefaultMode(0).setDisableReset(true).build(), new android.app.AppOpInfo.Builder(20, OPSTR_SEND_SMS, "SEND_SMS").setPermission(android.Manifest.permission.SEND_SMS).setRestriction(android.os.UserManager.DISALLOW_SMS).setDefaultMode(0).setDisableReset(true).build(), new android.app.AppOpInfo.Builder(21, OPSTR_READ_ICC_SMS, "READ_ICC_SMS").setSwitchCode(14).setPermission(android.Manifest.permission.READ_SMS).setRestriction(android.os.UserManager.DISALLOW_SMS).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(22, OPSTR_WRITE_ICC_SMS, "WRITE_ICC_SMS").setSwitchCode(15).setRestriction(android.os.UserManager.DISALLOW_SMS).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(23, OPSTR_WRITE_SETTINGS, "WRITE_SETTINGS").setPermission(android.Manifest.permission.WRITE_SETTINGS).build(), new android.app.AppOpInfo.Builder(24, OPSTR_SYSTEM_ALERT_WINDOW, "SYSTEM_ALERT_WINDOW").setPermission(android.Manifest.permission.SYSTEM_ALERT_WINDOW).setRestriction(android.os.UserManager.DISALLOW_CREATE_WINDOWS).setAllowSystemRestrictionBypass(new android.app.AppOpsManager.RestrictionBypass(false, true, false)).setDefaultMode(getSystemAlertWindowDefault()).build(), new android.app.AppOpInfo.Builder(25, OPSTR_ACCESS_NOTIFICATIONS, "ACCESS_NOTIFICATIONS").setPermission(android.Manifest.permission.ACCESS_NOTIFICATIONS).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(26, OPSTR_CAMERA, "CAMERA").setPermission(android.Manifest.permission.CAMERA).setRestriction(android.os.UserManager.DISALLOW_CAMERA).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(27, OPSTR_RECORD_AUDIO, "RECORD_AUDIO").setPermission(android.Manifest.permission.RECORD_AUDIO).setRestriction(android.os.UserManager.DISALLOW_RECORD_AUDIO).setAllowSystemRestrictionBypass(new android.app.AppOpsManager.RestrictionBypass(false, false, true)).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(28, OPSTR_PLAY_AUDIO, "PLAY_AUDIO").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(29, OPSTR_READ_CLIPBOARD, "READ_CLIPBOARD").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(30, OPSTR_WRITE_CLIPBOARD, "WRITE_CLIPBOARD").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(31, OPSTR_TAKE_MEDIA_BUTTONS, "TAKE_MEDIA_BUTTONS").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(32, OPSTR_TAKE_AUDIO_FOCUS, "TAKE_AUDIO_FOCUS").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(33, OPSTR_AUDIO_MASTER_VOLUME, "AUDIO_MASTER_VOLUME").setSwitchCode(33).setRestriction(android.os.UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(34, OPSTR_AUDIO_VOICE_VOLUME, "AUDIO_VOICE_VOLUME").setRestriction(android.os.UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(35, OPSTR_AUDIO_RING_VOLUME, "AUDIO_RING_VOLUME").setRestriction(android.os.UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(36, OPSTR_AUDIO_MEDIA_VOLUME, "AUDIO_MEDIA_VOLUME").setRestriction(android.os.UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(37, OPSTR_AUDIO_ALARM_VOLUME, "AUDIO_ALARM_VOLUME").setRestriction(android.os.UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(38, OPSTR_AUDIO_NOTIFICATION_VOLUME, "AUDIO_NOTIFICATION_VOLUME").setSwitchCode(38).setRestriction(android.os.UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(39, OPSTR_AUDIO_BLUETOOTH_VOLUME, "AUDIO_BLUETOOTH_VOLUME").setRestriction(android.os.UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(40, OPSTR_WAKE_LOCK, "WAKE_LOCK").setPermission(android.Manifest.permission.WAKE_LOCK).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(41, OPSTR_MONITOR_LOCATION, "MONITOR_LOCATION").setSwitchCode(0).setRestriction(android.os.UserManager.DISALLOW_SHARE_LOCATION).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(42, OPSTR_MONITOR_HIGH_POWER_LOCATION, "MONITOR_HIGH_POWER_LOCATION").setSwitchCode(0).setRestriction(android.os.UserManager.DISALLOW_SHARE_LOCATION).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(43, OPSTR_GET_USAGE_STATS, "GET_USAGE_STATS").setPermission(android.Manifest.permission.PACKAGE_USAGE_STATS).build(), new android.app.AppOpInfo.Builder(44, OPSTR_MUTE_MICROPHONE, "MUTE_MICROPHONE").setRestriction(android.os.UserManager.DISALLOW_UNMUTE_MICROPHONE).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(45, OPSTR_TOAST_WINDOW, "TOAST_WINDOW").setRestriction(android.os.UserManager.DISALLOW_CREATE_WINDOWS).setAllowSystemRestrictionBypass(new android.app.AppOpsManager.RestrictionBypass(false, true, false)).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(46, OPSTR_PROJECT_MEDIA, "PROJECT_MEDIA").setDefaultMode(1).build(), new android.app.AppOpInfo.Builder(47, OPSTR_ACTIVATE_VPN, "ACTIVATE_VPN").setDefaultMode(1).build(), new android.app.AppOpInfo.Builder(48, OPSTR_WRITE_WALLPAPER, "WRITE_WALLPAPER").setRestriction(android.os.UserManager.DISALLOW_WALLPAPER).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(49, OPSTR_ASSIST_STRUCTURE, "ASSIST_STRUCTURE").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(50, OPSTR_ASSIST_SCREENSHOT, "ASSIST_SCREENSHOT").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(51, OPSTR_READ_PHONE_STATE, "READ_PHONE_STATE").setPermission(android.Manifest.permission.READ_PHONE_STATE).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(52, OPSTR_ADD_VOICEMAIL, "ADD_VOICEMAIL").setPermission(android.Manifest.permission.ADD_VOICEMAIL).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(53, OPSTR_USE_SIP, "USE_SIP").setPermission(android.Manifest.permission.USE_SIP).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(54, OPSTR_PROCESS_OUTGOING_CALLS, "PROCESS_OUTGOING_CALLS").setSwitchCode(54).setPermission(android.Manifest.permission.PROCESS_OUTGOING_CALLS).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(55, OPSTR_USE_FINGERPRINT, "USE_FINGERPRINT").setPermission(android.Manifest.permission.USE_FINGERPRINT).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(56, OPSTR_BODY_SENSORS, "BODY_SENSORS").setPermission(android.Manifest.permission.BODY_SENSORS).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(57, OPSTR_READ_CELL_BROADCASTS, "READ_CELL_BROADCASTS").setPermission(android.Manifest.permission.READ_CELL_BROADCASTS).setDefaultMode(0).setDisableReset(true).build(), new android.app.AppOpInfo.Builder(58, OPSTR_MOCK_LOCATION, "MOCK_LOCATION").setDefaultMode(2).build(), new android.app.AppOpInfo.Builder(59, OPSTR_READ_EXTERNAL_STORAGE, "READ_EXTERNAL_STORAGE").setPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(60, OPSTR_WRITE_EXTERNAL_STORAGE, "WRITE_EXTERNAL_STORAGE").setPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(61, OPSTR_TURN_SCREEN_ON, "TURN_SCREEN_ON").setPermission(android.Manifest.permission.TURN_SCREEN_ON).setDefaultMode(3).build(), new android.app.AppOpInfo.Builder(62, OPSTR_GET_ACCOUNTS, "GET_ACCOUNTS").setPermission(android.Manifest.permission.GET_ACCOUNTS).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(63, OPSTR_RUN_IN_BACKGROUND, "RUN_IN_BACKGROUND").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(64, OPSTR_AUDIO_ACCESSIBILITY_VOLUME, "AUDIO_ACCESSIBILITY_VOLUME").setRestriction(android.os.UserManager.DISALLOW_ADJUST_VOLUME).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(65, OPSTR_READ_PHONE_NUMBERS, "READ_PHONE_NUMBERS").setPermission(android.Manifest.permission.READ_PHONE_NUMBERS).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(66, OPSTR_REQUEST_INSTALL_PACKAGES, "REQUEST_INSTALL_PACKAGES").setSwitchCode(66).setPermission(android.Manifest.permission.REQUEST_INSTALL_PACKAGES).build(), new android.app.AppOpInfo.Builder(67, OPSTR_PICTURE_IN_PICTURE, "PICTURE_IN_PICTURE").setSwitchCode(67).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(68, OPSTR_INSTANT_APP_START_FOREGROUND, "INSTANT_APP_START_FOREGROUND").setPermission(android.Manifest.permission.INSTANT_APP_FOREGROUND_SERVICE).build(), new android.app.AppOpInfo.Builder(69, OPSTR_ANSWER_PHONE_CALLS, "ANSWER_PHONE_CALLS").setSwitchCode(69).setPermission(android.Manifest.permission.ANSWER_PHONE_CALLS).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(70, OPSTR_RUN_ANY_IN_BACKGROUND, "RUN_ANY_IN_BACKGROUND").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(71, OPSTR_CHANGE_WIFI_STATE, "CHANGE_WIFI_STATE").setSwitchCode(71).setPermission(android.Manifest.permission.CHANGE_WIFI_STATE).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(72, OPSTR_REQUEST_DELETE_PACKAGES, "REQUEST_DELETE_PACKAGES").setPermission(android.Manifest.permission.REQUEST_DELETE_PACKAGES).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(73, OPSTR_BIND_ACCESSIBILITY_SERVICE, "BIND_ACCESSIBILITY_SERVICE").setPermission(android.Manifest.permission.BIND_ACCESSIBILITY_SERVICE).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(74, OPSTR_ACCEPT_HANDOVER, "ACCEPT_HANDOVER").setSwitchCode(74).setPermission(android.Manifest.permission.ACCEPT_HANDOVER).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(75, OPSTR_MANAGE_IPSEC_TUNNELS, "MANAGE_IPSEC_TUNNELS").setPermission(android.Manifest.permission.MANAGE_IPSEC_TUNNELS).setDefaultMode(2).build(), new android.app.AppOpInfo.Builder(76, OPSTR_START_FOREGROUND, "START_FOREGROUND").setPermission(android.Manifest.permission.FOREGROUND_SERVICE).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(77, OPSTR_BLUETOOTH_SCAN, "BLUETOOTH_SCAN").setPermission(android.Manifest.permission.BLUETOOTH_SCAN).setAllowSystemRestrictionBypass(new android.app.AppOpsManager.RestrictionBypass(false, true, false)).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(78, OPSTR_USE_BIOMETRIC, "USE_BIOMETRIC").setPermission(android.Manifest.permission.USE_BIOMETRIC).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(79, OPSTR_ACTIVITY_RECOGNITION, "ACTIVITY_RECOGNITION").setPermission(android.Manifest.permission.ACTIVITY_RECOGNITION).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(80, OPSTR_SMS_FINANCIAL_TRANSACTIONS, "SMS_FINANCIAL_TRANSACTIONS").setPermission(android.Manifest.permission.SMS_FINANCIAL_TRANSACTIONS).setRestriction(android.os.UserManager.DISALLOW_SMS).build(), new android.app.AppOpInfo.Builder(81, OPSTR_READ_MEDIA_AUDIO, "READ_MEDIA_AUDIO").setPermission(android.Manifest.permission.READ_MEDIA_AUDIO).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(82, OPSTR_WRITE_MEDIA_AUDIO, "WRITE_MEDIA_AUDIO").setDefaultMode(2).build(), new android.app.AppOpInfo.Builder(83, OPSTR_READ_MEDIA_VIDEO, "READ_MEDIA_VIDEO").setPermission(android.Manifest.permission.READ_MEDIA_VIDEO).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(84, OPSTR_WRITE_MEDIA_VIDEO, "WRITE_MEDIA_VIDEO").setDefaultMode(2).setDisableReset(true).build(), new android.app.AppOpInfo.Builder(85, OPSTR_READ_MEDIA_IMAGES, "READ_MEDIA_IMAGES").setPermission(android.Manifest.permission.READ_MEDIA_IMAGES).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(86, OPSTR_WRITE_MEDIA_IMAGES, "WRITE_MEDIA_IMAGES").setDefaultMode(2).setDisableReset(true).build(), new android.app.AppOpInfo.Builder(87, OPSTR_LEGACY_STORAGE, "LEGACY_STORAGE").setDisableReset(true).build(), new android.app.AppOpInfo.Builder(88, OPSTR_ACCESS_ACCESSIBILITY, "ACCESS_ACCESSIBILITY").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(89, OPSTR_READ_DEVICE_IDENTIFIERS, "READ_DEVICE_IDENTIFIERS").setDefaultMode(2).build(), new android.app.AppOpInfo.Builder(90, OPSTR_ACCESS_MEDIA_LOCATION, "ACCESS_MEDIA_LOCATION").setPermission(android.Manifest.permission.ACCESS_MEDIA_LOCATION).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(91, OPSTR_QUERY_ALL_PACKAGES, "QUERY_ALL_PACKAGES").build(), new android.app.AppOpInfo.Builder(92, OPSTR_MANAGE_EXTERNAL_STORAGE, "MANAGE_EXTERNAL_STORAGE").setPermission(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE).build(), new android.app.AppOpInfo.Builder(93, OPSTR_INTERACT_ACROSS_PROFILES, "INTERACT_ACROSS_PROFILES").setPermission(android.Manifest.permission.INTERACT_ACROSS_PROFILES).build(), new android.app.AppOpInfo.Builder(94, OPSTR_ACTIVATE_PLATFORM_VPN, "ACTIVATE_PLATFORM_VPN").setDefaultMode(1).build(), new android.app.AppOpInfo.Builder(95, OPSTR_LOADER_USAGE_STATS, "LOADER_USAGE_STATS").setPermission(android.Manifest.permission.LOADER_USAGE_STATS).build(), new android.app.AppOpInfo.Builder(-1, "", "").setDefaultMode(1).build(), new android.app.AppOpInfo.Builder(97, OPSTR_AUTO_REVOKE_PERMISSIONS_IF_UNUSED, "AUTO_REVOKE_PERMISSIONS_IF_UNUSED").build(), new android.app.AppOpInfo.Builder(98, OPSTR_AUTO_REVOKE_MANAGED_BY_INSTALLER, "AUTO_REVOKE_MANAGED_BY_INSTALLER").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(99, OPSTR_NO_ISOLATED_STORAGE, "NO_ISOLATED_STORAGE").setDefaultMode(2).setDisableReset(true).build(), new android.app.AppOpInfo.Builder(100, OPSTR_PHONE_CALL_MICROPHONE, "PHONE_CALL_MICROPHONE").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(101, OPSTR_PHONE_CALL_CAMERA, "PHONE_CALL_CAMERA").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(102, OPSTR_RECORD_AUDIO_HOTWORD, "RECORD_AUDIO_HOTWORD").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(103, OPSTR_MANAGE_ONGOING_CALLS, "MANAGE_ONGOING_CALLS").setPermission(android.Manifest.permission.MANAGE_ONGOING_CALLS).setDisableReset(true).build(), new android.app.AppOpInfo.Builder(104, OPSTR_MANAGE_CREDENTIALS, "MANAGE_CREDENTIALS").build(), new android.app.AppOpInfo.Builder(105, OPSTR_USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER, "USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER").setPermission(android.Manifest.permission.USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER).setDisableReset(true).build(), new android.app.AppOpInfo.Builder(106, OPSTR_RECORD_AUDIO_OUTPUT, "RECORD_AUDIO_OUTPUT").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(107, OPSTR_SCHEDULE_EXACT_ALARM, "SCHEDULE_EXACT_ALARM").setPermission(android.Manifest.permission.SCHEDULE_EXACT_ALARM).build(), new android.app.AppOpInfo.Builder(108, OPSTR_FINE_LOCATION_SOURCE, "FINE_LOCATION_SOURCE").setSwitchCode(1).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(109, OPSTR_COARSE_LOCATION_SOURCE, "COARSE_LOCATION_SOURCE").setSwitchCode(0).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(110, OPSTR_MANAGE_MEDIA, "MANAGE_MEDIA").setPermission(android.Manifest.permission.MANAGE_MEDIA).build(), new android.app.AppOpInfo.Builder(111, OPSTR_BLUETOOTH_CONNECT, "BLUETOOTH_CONNECT").setPermission(android.Manifest.permission.BLUETOOTH_CONNECT).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(112, OPSTR_UWB_RANGING, "UWB_RANGING").setPermission(android.Manifest.permission.UWB_RANGING).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(113, OPSTR_ACTIVITY_RECOGNITION_SOURCE, "ACTIVITY_RECOGNITION_SOURCE").setSwitchCode(79).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(114, OPSTR_BLUETOOTH_ADVERTISE, "BLUETOOTH_ADVERTISE").setPermission(android.Manifest.permission.BLUETOOTH_ADVERTISE).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(115, OPSTR_RECORD_INCOMING_PHONE_AUDIO, "RECORD_INCOMING_PHONE_AUDIO").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(116, OPSTR_NEARBY_WIFI_DEVICES, "NEARBY_WIFI_DEVICES").setPermission(android.Manifest.permission.NEARBY_WIFI_DEVICES).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(117, OPSTR_ESTABLISH_VPN_SERVICE, "ESTABLISH_VPN_SERVICE").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(118, OPSTR_ESTABLISH_VPN_MANAGER, "ESTABLISH_VPN_MANAGER").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(119, OPSTR_ACCESS_RESTRICTED_SETTINGS, "ACCESS_RESTRICTED_SETTINGS").setDefaultMode(0).setDisableReset(true).setRestrictRead(true).build(), new android.app.AppOpInfo.Builder(120, OPSTR_RECEIVE_AMBIENT_TRIGGER_AUDIO, "RECEIVE_SOUNDTRIGGER_AUDIO").setDefaultMode(0).setForceCollectNotes(true).build(), new android.app.AppOpInfo.Builder(121, OPSTR_RECEIVE_EXPLICIT_USER_INTERACTION_AUDIO, "RECEIVE_EXPLICIT_USER_INTERACTION_AUDIO").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(122, OPSTR_RUN_USER_INITIATED_JOBS, "RUN_USER_INITIATED_JOBS").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(123, OPSTR_READ_MEDIA_VISUAL_USER_SELECTED, "READ_MEDIA_VISUAL_USER_SELECTED").setPermission(android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED).setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(124, OPSTR_SYSTEM_EXEMPT_FROM_SUSPENSION, "SYSTEM_EXEMPT_FROM_SUSPENSION").setDisableReset(true).build(), new android.app.AppOpInfo.Builder(125, OPSTR_SYSTEM_EXEMPT_FROM_DISMISSIBLE_NOTIFICATIONS, "SYSTEM_EXEMPT_FROM_DISMISSIBLE_NOTIFICATIONS").setDisableReset(true).build(), new android.app.AppOpInfo.Builder(126, OPSTR_READ_WRITE_HEALTH_DATA, "READ_WRITE_HEALTH_DATA").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(127, OPSTR_FOREGROUND_SERVICE_SPECIAL_USE, "FOREGROUND_SERVICE_SPECIAL_USE").setPermission(android.Manifest.permission.FOREGROUND_SERVICE_SPECIAL_USE).build(), new android.app.AppOpInfo.Builder(128, OPSTR_SYSTEM_EXEMPT_FROM_POWER_RESTRICTIONS, "SYSTEM_EXEMPT_FROM_POWER_RESTRICTIONS").setDisableReset(true).build(), new android.app.AppOpInfo.Builder(129, OPSTR_SYSTEM_EXEMPT_FROM_HIBERNATION, "SYSTEM_EXEMPT_FROM_HIBERNATION").setDisableReset(true).build(), new android.app.AppOpInfo.Builder(130, OPSTR_SYSTEM_EXEMPT_FROM_ACTIVITY_BG_START_RESTRICTION, "SYSTEM_EXEMPT_FROM_ACTIVITY_BG_START_RESTRICTION").setDisableReset(true).build(), new android.app.AppOpInfo.Builder(131, OPSTR_CAPTURE_CONSENTLESS_BUGREPORT_ON_USERDEBUG_BUILD, "CAPTURE_CONSENTLESS_BUGREPORT_ON_USERDEBUG_BUILD").setPermission(android.Manifest.permission.CAPTURE_CONSENTLESS_BUGREPORT_ON_USERDEBUG_BUILD).build(), new android.app.AppOpInfo.Builder(132, OPSTR_DEPRECATED_2, "DEPRECATED_2").setDefaultMode(1).build(), new android.app.AppOpInfo.Builder(133, OPSTR_USE_FULL_SCREEN_INTENT, "USE_FULL_SCREEN_INTENT").setPermission(android.Manifest.permission.USE_FULL_SCREEN_INTENT).build(), new android.app.AppOpInfo.Builder(134, OPSTR_CAMERA_SANDBOXED, "CAMERA_SANDBOXED").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(135, OPSTR_RECORD_AUDIO_SANDBOXED, "RECORD_AUDIO_SANDBOXED").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(136, OPSTR_RECEIVE_SANDBOX_TRIGGER_AUDIO, "RECEIVE_SANDBOX_TRIGGER_AUDIO").setPermission(android.Manifest.permission.RECEIVE_SANDBOX_TRIGGER_AUDIO).setDefaultMode(3).build(), new android.app.AppOpInfo.Builder(137, OPSTR_RECEIVE_SANDBOXED_DETECTION_TRAINING_DATA, "RECEIVE_SANDBOXED_DETECTION_TRAINING_DATA").setPermission(android.Manifest.permission.RECEIVE_SANDBOXED_DETECTION_TRAINING_DATA).setDefaultMode(3).build(), new android.app.AppOpInfo.Builder(138, OPSTR_CREATE_ACCESSIBILITY_OVERLAY, "CREATE_ACCESSIBILITY_OVERLAY").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(139, OPSTR_MEDIA_ROUTING_CONTROL, "MEDIA_ROUTING_CONTROL").setPermission(android.Manifest.permission.MEDIA_ROUTING_CONTROL).build(), new android.app.AppOpInfo.Builder(140, OPSTR_ENABLE_MOBILE_DATA_BY_USER, "ENABLE_MOBILE_DATA_BY_USER").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(141, OPSTR_RESERVED_FOR_TESTING, "OP_RESERVED_FOR_TESTING").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(142, OPSTR_RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER, "RAPID_CLEAR_NOTIFICATIONS_BY_LISTENER").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(143, OPSTR_READ_SYSTEM_GRAMMATICAL_GENDER, "READ_SYSTEM_GRAMMATICAL_GENDER").build(), new android.app.AppOpInfo.Builder(144, OPSTR_RUN_BACKUP_JOBS, "RUN_BACKUP_JOBS").setPermission(android.Manifest.permission.RUN_BACKUP_JOBS).build(), new android.app.AppOpInfo.Builder(145, OPSTR_ARCHIVE_ICON_OVERLAY, "ARCHIVE_ICON_OVERLAY").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(146, OPSTR_UNARCHIVAL_CONFIRMATION, "UNARCHIVAL_CONFIRMATION").setDefaultMode(0).build(), new android.app.AppOpInfo.Builder(147, OPSTR_EMERGENCY_LOCATION, "EMERGENCY_LOCATION").setPermission(android.Manifest.permission.LOCATION_BYPASS).build()};
    private static java.util.HashMap<java.lang.String, java.lang.Integer> sOpStrToOp = new java.util.HashMap<>();
    private static java.util.HashMap<java.lang.String, java.lang.Integer> sPermToOp = new java.util.HashMap<>();
    private static final java.lang.ThreadLocal<java.lang.Integer> sBinderThreadCallingUid = new java.lang.ThreadLocal<>();
    private static final java.lang.ThreadLocal<android.util.ArrayMap<java.lang.String, java.util.BitSet>> sAppOpsNotedInThisBinderTransaction = new java.lang.ThreadLocal<>();
    private final android.util.ArrayMap<android.app.AppOpsManager.OnOpChangedListener, com.android.internal.app.IAppOpsCallback> mModeWatchers = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.app.AppOpsManager.OnOpActiveChangedListener, com.android.internal.app.IAppOpsActiveCallback> mActiveWatchers = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.app.AppOpsManager.OnOpStartedListener, com.android.internal.app.IAppOpsStartedCallback> mStartedWatchers = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.app.AppOpsManager.OnOpNotedListener, com.android.internal.app.IAppOpsNotedCallback> mNotedWatchers = new android.util.ArrayMap<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AppOpString {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AttributionFlags {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.FIELD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DataBucketKey {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HistoricalMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HistoricalOpsRequestFilter {
    }

    public interface HistoricalOpsVisitor {
        void visitHistoricalAttributionOps(android.app.AppOpsManager.AttributedHistoricalOps attributedHistoricalOps);

        void visitHistoricalOp(android.app.AppOpsManager.HistoricalOp historicalOp);

        void visitHistoricalOps(android.app.AppOpsManager.HistoricalOps historicalOps);

        void visitHistoricalPackageOps(android.app.AppOpsManager.HistoricalPackageOps historicalPackageOps);

        void visitHistoricalUidOps(android.app.AppOpsManager.HistoricalUidOps historicalUidOps);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface NotedOpCollectionMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OpFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OpHistoryFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SamplingStrategy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface ShouldCollectNoteOp {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UidState {
    }

    static {
        if (sAppOpInfos.length != 148) {
            throw new java.lang.IllegalStateException("mAppOpInfos length " + sAppOpInfos.length + " should be 148");
        }
        for (int i = 0; i < 148; i++) {
            if (sAppOpInfos[i].name != null) {
                sOpStrToOp.put(sAppOpInfos[i].name, java.lang.Integer.valueOf(i));
            }
        }
        for (int i2 : RUNTIME_PERMISSION_OPS) {
            if (sAppOpInfos[i2].permission != null) {
                sPermToOp.put(sAppOpInfos[i2].permission, java.lang.Integer.valueOf(i2));
            }
        }
        for (int i3 : APP_OP_PERMISSION_PACKAGE_OPS) {
            if (sAppOpInfos[i3].permission != null) {
                sPermToOp.put(sAppOpInfos[i3].permission, java.lang.Integer.valueOf(i3));
            }
        }
        for (int i4 : APP_OP_PERMISSION_UID_OPS) {
            if (sAppOpInfos[i4].permission != null) {
                sPermToOp.put(sAppOpInfos[i4].permission, java.lang.Integer.valueOf(i4));
            }
        }
        sConfig = new com.android.internal.app.MessageSamplingConfig(-1, 0, 0L);
    }

    public static int resolveFirstUnrestrictedUidState(int i) {
        return 500;
    }

    public static int resolveLastRestrictedUidState(int i) {
        return 600;
    }

    public static java.lang.String getUidStateName(int i) {
        switch (i) {
            case 100:
                return "pers";
            case 200:
                return "top";
            case 300:
                return "fgsvcl";
            case 400:
                return "fgsvc";
            case 500:
                return "fg";
            case 600:
                return "bg";
            case 700:
                return "cch";
            default:
                return "unknown";
        }
    }

    public static final java.lang.String getFlagName(int i) {
        switch (i) {
            case 1:
                return android.app.blob.XmlTags.TAG_SESSION;
            case 2:
                return "tp";
            case 4:
                return android.media.MediaMetrics.Value.UP;
            case 8:
                return "tpd";
            case 16:
                return "upd";
            default:
                return "unknown";
        }
    }

    public static java.lang.String keyToString(long j) {
        return android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + getUidStateName(extractUidStateFromKey(j)) + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + flagsToString(extractFlagsFromKey(j)) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public static long makeKey(int i, int i2) {
        return i2 | (i << 31);
    }

    public static int extractUidStateFromKey(long j) {
        return (int) (j >> 31);
    }

    public static int extractFlagsFromKey(long j) {
        return (int) (j & (-1));
    }

    public static java.lang.String flagsToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        while (i != 0) {
            int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i);
            i &= ~numberOfTrailingZeros;
            if (sb.length() > 0) {
                sb.append('|');
            }
            sb.append(getFlagName(numberOfTrailingZeros));
        }
        return sb.toString();
    }

    public static boolean shouldForceCollectNoteForOp(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, 147, "opCode");
        return sAppOpInfos[i].forceCollectNotes;
    }

    public static int opToSwitch(int i) {
        return sAppOpInfos[i].switchCode;
    }

    public static java.lang.String opToName(int i) {
        return i == -1 ? android.security.keystore.KeyProperties.DIGEST_NONE : i < sAppOpInfos.length ? sAppOpInfos[i].simpleName : "Unknown(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    public static java.lang.String opToPublicName(int i) {
        return sAppOpInfos[i].name;
    }

    public static int strDebugOpToOp(java.lang.String str) {
        for (int i = 0; i < sAppOpInfos.length; i++) {
            if (sAppOpInfos[i].simpleName.equals(str)) {
                return i;
            }
        }
        throw new java.lang.IllegalArgumentException("Unknown operation string: " + str);
    }

    public static java.lang.String opToPermission(int i) {
        return sAppOpInfos[i].permission;
    }

    @android.annotation.SystemApi
    public static java.lang.String opToPermission(java.lang.String str) {
        return opToPermission(strOpToOp(str));
    }

    public static java.lang.String opToRestriction(int i) {
        return sAppOpInfos[i].restriction;
    }

    public static int permissionToOpCode(java.lang.String str) {
        java.lang.Integer num = sPermToOp.get(str);
        if (num != null) {
            return num.intValue();
        }
        if (str != null && android.health.connect.HealthConnectManager.isHealthPermission(android.app.ActivityThread.currentApplication(), str)) {
            return 126;
        }
        return -1;
    }

    public static android.app.AppOpsManager.RestrictionBypass opAllowSystemBypassRestriction(int i) {
        return sAppOpInfos[i].allowSystemRestrictionBypass;
    }

    public static int opToDefaultMode(int i) {
        if (i == 32 && android.media.audio.Flags.foregroundAudioControl()) {
            return 4;
        }
        return sAppOpInfos[i].defaultMode;
    }

    @android.annotation.SystemApi
    public static int opToDefaultMode(java.lang.String str) {
        return opToDefaultMode(strOpToOp(str));
    }

    public static java.lang.String modeToName(int i) {
        if (i >= 0 && i < MODE_NAMES.length) {
            return MODE_NAMES[i];
        }
        return "mode=" + i;
    }

    public static boolean opRestrictsRead(int i) {
        return sAppOpInfos[i].restrictRead;
    }

    public static boolean opAllowsReset(int i) {
        return !sAppOpInfos[i].disableReset;
    }

    public static boolean opIsPackageAppOpPermission(int i) {
        return com.android.internal.util.ArrayUtils.contains(APP_OP_PERMISSION_PACKAGE_OPS, i);
    }

    public static boolean opIsUidAppOpPermission(int i) {
        return com.android.internal.util.ArrayUtils.contains(APP_OP_PERMISSION_UID_OPS, i);
    }

    public static java.lang.String toReceiverId(java.lang.Object obj) {
        if (obj == null) {
            return "null";
        }
        if (obj instanceof android.app.PendingIntent) {
            return toReceiverId((android.app.PendingIntent) obj);
        }
        return obj.getClass().getName() + "@" + java.lang.System.identityHashCode(obj);
    }

    public static java.lang.String toReceiverId(android.app.PendingIntent pendingIntent) {
        return pendingIntent.getTag("");
    }

    public static class RestrictionBypass {
        public static android.app.AppOpsManager.RestrictionBypass UNRESTRICTED = new android.app.AppOpsManager.RestrictionBypass(false, true, true);
        public boolean isPrivileged;
        public boolean isRecordAudioRestrictionExcept;
        public boolean isSystemUid;

        public RestrictionBypass(boolean z, boolean z2, boolean z3) {
            this.isSystemUid = z;
            this.isPrivileged = z2;
            this.isRecordAudioRestrictionExcept = z3;
        }
    }

    @android.annotation.SystemApi
    public static final class PackageOps implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.AppOpsManager.PackageOps> CREATOR = new android.os.Parcelable.Creator<android.app.AppOpsManager.PackageOps>() { // from class: android.app.AppOpsManager.PackageOps.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.PackageOps createFromParcel(android.os.Parcel parcel) {
                return new android.app.AppOpsManager.PackageOps(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.PackageOps[] newArray(int i) {
                return new android.app.AppOpsManager.PackageOps[i];
            }
        };
        private final java.util.List<android.app.AppOpsManager.OpEntry> mEntries;
        private final java.lang.String mPackageName;
        private final int mUid;

        public PackageOps(java.lang.String str, int i, java.util.List<android.app.AppOpsManager.OpEntry> list) {
            this.mPackageName = str;
            this.mUid = i;
            this.mEntries = list;
        }

        public java.lang.String getPackageName() {
            return this.mPackageName;
        }

        public int getUid() {
            return this.mUid;
        }

        public java.util.List<android.app.AppOpsManager.OpEntry> getOps() {
            return this.mEntries;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.mPackageName);
            parcel.writeInt(this.mUid);
            parcel.writeInt(this.mEntries.size());
            for (int i2 = 0; i2 < this.mEntries.size(); i2++) {
                this.mEntries.get(i2).writeToParcel(parcel, i);
            }
        }

        PackageOps(android.os.Parcel parcel) {
            this.mPackageName = parcel.readString();
            this.mUid = parcel.readInt();
            this.mEntries = new java.util.ArrayList();
            int readInt = parcel.readInt();
            for (int i = 0; i < readInt; i++) {
                this.mEntries.add(android.app.AppOpsManager.OpEntry.CREATOR.createFromParcel(parcel));
            }
        }
    }

    @android.annotation.SystemApi
    public static final class OpEventProxyInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.AppOpsManager.OpEventProxyInfo> CREATOR = new android.os.Parcelable.Creator<android.app.AppOpsManager.OpEventProxyInfo>() { // from class: android.app.AppOpsManager.OpEventProxyInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.OpEventProxyInfo[] newArray(int i) {
                return new android.app.AppOpsManager.OpEventProxyInfo[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.OpEventProxyInfo createFromParcel(android.os.Parcel parcel) {
                return new android.app.AppOpsManager.OpEventProxyInfo(parcel);
            }
        };
        private java.lang.String mAttributionTag;
        private java.lang.String mPackageName;
        private int mUid;

        public void reinit(int i, java.lang.String str, java.lang.String str2) {
            this.mUid = com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
            this.mPackageName = str;
            this.mAttributionTag = str2;
        }

        public OpEventProxyInfo(int i, java.lang.String str, java.lang.String str2) {
            this.mUid = i;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mUid, "from", 0L);
            this.mPackageName = str;
            this.mAttributionTag = str2;
        }

        public OpEventProxyInfo(android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo) {
            this.mUid = opEventProxyInfo.mUid;
            this.mPackageName = opEventProxyInfo.mPackageName;
            this.mAttributionTag = opEventProxyInfo.mAttributionTag;
        }

        public int getUid() {
            return this.mUid;
        }

        public java.lang.String getPackageName() {
            return this.mPackageName;
        }

        public java.lang.String getAttributionTag() {
            return this.mAttributionTag;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            byte b = this.mPackageName != null ? (byte) 2 : (byte) 0;
            if (this.mAttributionTag != null) {
                b = (byte) (b | 4);
            }
            parcel.writeByte(b);
            parcel.writeInt(this.mUid);
            if (this.mPackageName != null) {
                parcel.writeString(this.mPackageName);
            }
            if (this.mAttributionTag != null) {
                parcel.writeString(this.mAttributionTag);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        OpEventProxyInfo(android.os.Parcel parcel) {
            byte readByte = parcel.readByte();
            int readInt = parcel.readInt();
            java.lang.String readString = (readByte & 2) == 0 ? null : parcel.readString();
            java.lang.String readString2 = (readByte & 4) != 0 ? parcel.readString() : null;
            this.mUid = readInt;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mUid, "from", 0L);
            this.mPackageName = readString;
            this.mAttributionTag = readString2;
        }
    }

    public static final class NoteOpEvent implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.AppOpsManager.NoteOpEvent> CREATOR = new android.os.Parcelable.Creator<android.app.AppOpsManager.NoteOpEvent>() { // from class: android.app.AppOpsManager.NoteOpEvent.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.NoteOpEvent[] newArray(int i) {
                return new android.app.AppOpsManager.NoteOpEvent[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.NoteOpEvent createFromParcel(android.os.Parcel parcel) {
                return new android.app.AppOpsManager.NoteOpEvent(parcel);
            }
        };
        private long mDuration;
        private long mNoteTime;
        private android.app.AppOpsManager.OpEventProxyInfo mProxy;

        public void reinit(long j, long j2, android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo, android.util.Pools.Pool<android.app.AppOpsManager.OpEventProxyInfo> pool) {
            this.mNoteTime = com.android.internal.util.Preconditions.checkArgumentNonnegative(j);
            this.mDuration = com.android.internal.util.Preconditions.checkArgumentInRange(j2, -1L, Long.MAX_VALUE, "duration");
            if (this.mProxy != null) {
                pool.release(this.mProxy);
            }
            this.mProxy = opEventProxyInfo;
        }

        public NoteOpEvent(android.app.AppOpsManager.NoteOpEvent noteOpEvent) {
            this(noteOpEvent.mNoteTime, noteOpEvent.mDuration, noteOpEvent.mProxy != null ? new android.app.AppOpsManager.OpEventProxyInfo(noteOpEvent.mProxy) : null);
        }

        public NoteOpEvent(long j, long j2, android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo) {
            this.mNoteTime = j;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mNoteTime, "from", 0L);
            this.mDuration = j2;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mDuration, "from", -1L);
            this.mProxy = opEventProxyInfo;
        }

        public long getNoteTime() {
            return this.mNoteTime;
        }

        public long getDuration() {
            return this.mDuration;
        }

        public android.app.AppOpsManager.OpEventProxyInfo getProxy() {
            return this.mProxy;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeByte(this.mProxy != null ? (byte) 4 : (byte) 0);
            parcel.writeLong(this.mNoteTime);
            parcel.writeLong(this.mDuration);
            if (this.mProxy != null) {
                parcel.writeTypedObject(this.mProxy, i);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        NoteOpEvent(android.os.Parcel parcel) {
            byte readByte = parcel.readByte();
            long readLong = parcel.readLong();
            long readLong2 = parcel.readLong();
            android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo = (readByte & 4) == 0 ? null : (android.app.AppOpsManager.OpEventProxyInfo) parcel.readTypedObject(android.app.AppOpsManager.OpEventProxyInfo.CREATOR);
            this.mNoteTime = readLong;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mNoteTime, "from", 0L);
            this.mDuration = readLong2;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mDuration, "from", -1L);
            this.mProxy = opEventProxyInfo;
        }
    }

    @android.annotation.SystemApi
    public static final class AttributedOpEntry implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.AppOpsManager.AttributedOpEntry> CREATOR;
        static com.android.internal.util.Parcelling<android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent>> sParcellingForAccessEvents;
        static com.android.internal.util.Parcelling<android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent>> sParcellingForRejectEvents;
        private final android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> mAccessEvents;
        private final int mOp;
        private final android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> mRejectEvents;
        private final boolean mRunning;

        private AttributedOpEntry(android.app.AppOpsManager.AttributedOpEntry attributedOpEntry) {
            this.mOp = attributedOpEntry.mOp;
            this.mRunning = attributedOpEntry.mRunning;
            this.mAccessEvents = attributedOpEntry.mAccessEvents == null ? null : attributedOpEntry.mAccessEvents.m4820clone();
            this.mRejectEvents = attributedOpEntry.mRejectEvents != null ? attributedOpEntry.mRejectEvents.m4820clone() : null;
        }

        public android.util.ArraySet<java.lang.Long> collectKeys() {
            android.util.ArraySet<java.lang.Long> arraySet = new android.util.ArraySet<>();
            if (this.mAccessEvents != null) {
                int size = this.mAccessEvents.size();
                for (int i = 0; i < size; i++) {
                    arraySet.add(java.lang.Long.valueOf(this.mAccessEvents.keyAt(i)));
                }
            }
            if (this.mRejectEvents != null) {
                int size2 = this.mRejectEvents.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    arraySet.add(java.lang.Long.valueOf(this.mRejectEvents.keyAt(i2)));
                }
            }
            return arraySet;
        }

        public long getLastAccessTime(int i) {
            return getLastAccessTime(100, 700, i);
        }

        public long getLastAccessForegroundTime(int i) {
            return getLastAccessTime(100, android.app.AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getLastAccessBackgroundTime(int i) {
            return getLastAccessTime(android.app.AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.app.AppOpsManager.NoteOpEvent getLastAccessEvent(int i, int i2, int i3) {
            return android.app.AppOpsManager.getLastEvent(this.mAccessEvents, i, i2, i3);
        }

        public long getLastAccessTime(int i, int i2, int i3) {
            android.app.AppOpsManager.NoteOpEvent lastAccessEvent = getLastAccessEvent(i, i2, i3);
            if (lastAccessEvent == null) {
                return -1L;
            }
            return lastAccessEvent.getNoteTime();
        }

        public long getLastRejectTime(int i) {
            return getLastRejectTime(100, 700, i);
        }

        public long getLastRejectForegroundTime(int i) {
            return getLastRejectTime(100, android.app.AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getLastRejectBackgroundTime(int i) {
            return getLastRejectTime(android.app.AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.app.AppOpsManager.NoteOpEvent getLastRejectEvent(int i, int i2, int i3) {
            return android.app.AppOpsManager.getLastEvent(this.mRejectEvents, i, i2, i3);
        }

        public long getLastRejectTime(int i, int i2, int i3) {
            android.app.AppOpsManager.NoteOpEvent lastRejectEvent = getLastRejectEvent(i, i2, i3);
            if (lastRejectEvent == null) {
                return -1L;
            }
            return lastRejectEvent.getNoteTime();
        }

        public long getLastDuration(int i) {
            return getLastDuration(100, 700, i);
        }

        public long getLastForegroundDuration(int i) {
            return getLastDuration(100, android.app.AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getLastBackgroundDuration(int i) {
            return getLastDuration(android.app.AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public long getLastDuration(int i, int i2, int i3) {
            android.app.AppOpsManager.NoteOpEvent lastAccessEvent = getLastAccessEvent(i, i2, i3);
            if (lastAccessEvent == null) {
                return -1L;
            }
            return lastAccessEvent.getDuration();
        }

        public android.app.AppOpsManager.OpEventProxyInfo getLastProxyInfo(int i) {
            return getLastProxyInfo(100, 700, i);
        }

        public android.app.AppOpsManager.OpEventProxyInfo getLastForegroundProxyInfo(int i) {
            return getLastProxyInfo(100, android.app.AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public android.app.AppOpsManager.OpEventProxyInfo getLastBackgroundProxyInfo(int i) {
            return getLastProxyInfo(android.app.AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public android.app.AppOpsManager.OpEventProxyInfo getLastProxyInfo(int i, int i2, int i3) {
            android.app.AppOpsManager.NoteOpEvent lastAccessEvent = getLastAccessEvent(i, i2, i3);
            if (lastAccessEvent == null) {
                return null;
            }
            return lastAccessEvent.getProxy();
        }

        java.lang.String getOpName() {
            return android.app.AppOpsManager.opToPublicName(this.mOp);
        }

        int getOp() {
            return this.mOp;
        }

        private static class LongSparseArrayParceling implements com.android.internal.util.Parcelling<android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent>> {
            private LongSparseArrayParceling() {
            }

            @Override // com.android.internal.util.Parcelling
            public void parcel(android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> longSparseArray, android.os.Parcel parcel, int i) {
                if (longSparseArray == null) {
                    parcel.writeInt(-1);
                    return;
                }
                int size = longSparseArray.size();
                parcel.writeInt(size);
                for (int i2 = 0; i2 < size; i2++) {
                    parcel.writeLong(longSparseArray.keyAt(i2));
                    parcel.writeParcelable(longSparseArray.valueAt(i2), i);
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.android.internal.util.Parcelling
            public android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> unparcel(android.os.Parcel parcel) {
                int readInt = parcel.readInt();
                if (readInt == -1) {
                    return null;
                }
                android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> longSparseArray = new android.util.LongSparseArray<>(readInt);
                for (int i = 0; i < readInt; i++) {
                    longSparseArray.put(parcel.readLong(), (android.app.AppOpsManager.NoteOpEvent) parcel.readParcelable(null, android.app.AppOpsManager.NoteOpEvent.class));
                }
                return longSparseArray;
            }
        }

        public AttributedOpEntry(int i, boolean z, android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> longSparseArray, android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> longSparseArray2) {
            this.mOp = i;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mOp, "from", 0L, "to", 147L);
            this.mRunning = z;
            this.mAccessEvents = longSparseArray;
            this.mRejectEvents = longSparseArray2;
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        static {
            sParcellingForAccessEvents = com.android.internal.util.Parcelling.Cache.get(android.app.AppOpsManager.AttributedOpEntry.LongSparseArrayParceling.class);
            if (sParcellingForAccessEvents == null) {
                sParcellingForAccessEvents = com.android.internal.util.Parcelling.Cache.put(new android.app.AppOpsManager.AttributedOpEntry.LongSparseArrayParceling());
            }
            sParcellingForRejectEvents = com.android.internal.util.Parcelling.Cache.get(android.app.AppOpsManager.AttributedOpEntry.LongSparseArrayParceling.class);
            if (sParcellingForRejectEvents == null) {
                sParcellingForRejectEvents = com.android.internal.util.Parcelling.Cache.put(new android.app.AppOpsManager.AttributedOpEntry.LongSparseArrayParceling());
            }
            CREATOR = new android.os.Parcelable.Creator<android.app.AppOpsManager.AttributedOpEntry>() { // from class: android.app.AppOpsManager.AttributedOpEntry.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.app.AppOpsManager.AttributedOpEntry[] newArray(int i) {
                    return new android.app.AppOpsManager.AttributedOpEntry[i];
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public android.app.AppOpsManager.AttributedOpEntry createFromParcel(android.os.Parcel parcel) {
                    return new android.app.AppOpsManager.AttributedOpEntry(parcel);
                }
            };
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            byte b = this.mRunning ? (byte) 2 : (byte) 0;
            if (this.mAccessEvents != null) {
                b = (byte) (b | 4);
            }
            if (this.mRejectEvents != null) {
                b = (byte) (b | 8);
            }
            parcel.writeByte(b);
            parcel.writeInt(this.mOp);
            sParcellingForAccessEvents.parcel(this.mAccessEvents, parcel, i);
            sParcellingForRejectEvents.parcel(this.mRejectEvents, parcel, i);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        AttributedOpEntry(android.os.Parcel parcel) {
            boolean z = (parcel.readByte() & 2) != 0;
            int readInt = parcel.readInt();
            android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> unparcel = sParcellingForAccessEvents.unparcel(parcel);
            android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> unparcel2 = sParcellingForRejectEvents.unparcel(parcel);
            this.mOp = readInt;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mOp, "from", 0L, "to", 147L);
            this.mRunning = z;
            this.mAccessEvents = unparcel;
            this.mRejectEvents = unparcel2;
        }
    }

    @android.annotation.SystemApi
    public static final class OpEntry implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.AppOpsManager.OpEntry> CREATOR = new android.os.Parcelable.Creator<android.app.AppOpsManager.OpEntry>() { // from class: android.app.AppOpsManager.OpEntry.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.OpEntry[] newArray(int i) {
                return new android.app.AppOpsManager.OpEntry[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.OpEntry createFromParcel(android.os.Parcel parcel) {
                return new android.app.AppOpsManager.OpEntry(parcel);
            }
        };
        private final java.util.Map<java.lang.String, android.app.AppOpsManager.AttributedOpEntry> mAttributedOpEntries;
        private final int mMode;
        private final int mOp;

        public int getOp() {
            return this.mOp;
        }

        public java.lang.String getOpStr() {
            return android.app.AppOpsManager.sAppOpInfos[this.mOp].name;
        }

        @java.lang.Deprecated
        public long getTime() {
            return getLastAccessTime(31);
        }

        public long getLastAccessTime(int i) {
            return getLastAccessTime(100, 700, i);
        }

        public long getLastAccessForegroundTime(int i) {
            return getLastAccessTime(100, android.app.AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getLastAccessBackgroundTime(int i) {
            return getLastAccessTime(android.app.AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        private android.app.AppOpsManager.NoteOpEvent getLastAccessEvent(int i, int i2, int i3) {
            java.util.Iterator<android.app.AppOpsManager.AttributedOpEntry> it = this.mAttributedOpEntries.values().iterator();
            android.app.AppOpsManager.NoteOpEvent noteOpEvent = null;
            while (it.hasNext()) {
                android.app.AppOpsManager.NoteOpEvent lastAccessEvent = it.next().getLastAccessEvent(i, i2, i3);
                if (noteOpEvent == null || (lastAccessEvent != null && lastAccessEvent.getNoteTime() > noteOpEvent.getNoteTime())) {
                    noteOpEvent = lastAccessEvent;
                }
            }
            return noteOpEvent;
        }

        public long getLastAccessTime(int i, int i2, int i3) {
            android.app.AppOpsManager.NoteOpEvent lastAccessEvent = getLastAccessEvent(i, i2, i3);
            if (lastAccessEvent == null) {
                return -1L;
            }
            return lastAccessEvent.getNoteTime();
        }

        @java.lang.Deprecated
        public long getRejectTime() {
            return getLastRejectTime(31);
        }

        public long getLastRejectTime(int i) {
            return getLastRejectTime(100, 700, i);
        }

        public long getLastRejectForegroundTime(int i) {
            return getLastRejectTime(100, android.app.AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getLastRejectBackgroundTime(int i) {
            return getLastRejectTime(android.app.AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        private android.app.AppOpsManager.NoteOpEvent getLastRejectEvent(int i, int i2, int i3) {
            java.util.Iterator<android.app.AppOpsManager.AttributedOpEntry> it = this.mAttributedOpEntries.values().iterator();
            android.app.AppOpsManager.NoteOpEvent noteOpEvent = null;
            while (it.hasNext()) {
                android.app.AppOpsManager.NoteOpEvent lastRejectEvent = it.next().getLastRejectEvent(i, i2, i3);
                if (noteOpEvent == null || (lastRejectEvent != null && lastRejectEvent.getNoteTime() > noteOpEvent.getNoteTime())) {
                    noteOpEvent = lastRejectEvent;
                }
            }
            return noteOpEvent;
        }

        public long getLastRejectTime(int i, int i2, int i3) {
            android.app.AppOpsManager.NoteOpEvent lastRejectEvent = getLastRejectEvent(i, i2, i3);
            if (lastRejectEvent == null) {
                return -1L;
            }
            return lastRejectEvent.getNoteTime();
        }

        public boolean isRunning() {
            java.util.Iterator<android.app.AppOpsManager.AttributedOpEntry> it = this.mAttributedOpEntries.values().iterator();
            while (it.hasNext()) {
                if (it.next().isRunning()) {
                    return true;
                }
            }
            return false;
        }

        @java.lang.Deprecated
        public long getDuration() {
            return getLastDuration(31);
        }

        public long getLastDuration(int i) {
            return getLastDuration(100, 700, i);
        }

        public long getLastForegroundDuration(int i) {
            return getLastDuration(100, android.app.AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getLastBackgroundDuration(int i) {
            return getLastDuration(android.app.AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public long getLastDuration(int i, int i2, int i3) {
            android.app.AppOpsManager.NoteOpEvent lastAccessEvent = getLastAccessEvent(i, i2, i3);
            if (lastAccessEvent == null) {
                return -1L;
            }
            return lastAccessEvent.getDuration();
        }

        @java.lang.Deprecated
        public int getProxyUid() {
            android.app.AppOpsManager.OpEventProxyInfo lastProxyInfo = getLastProxyInfo(31);
            if (lastProxyInfo == null) {
                return -1;
            }
            return lastProxyInfo.getUid();
        }

        @java.lang.Deprecated
        public int getProxyUid(int i, int i2) {
            android.app.AppOpsManager.OpEventProxyInfo lastProxyInfo = getLastProxyInfo(i, i, i2);
            if (lastProxyInfo == null) {
                return -1;
            }
            return lastProxyInfo.getUid();
        }

        @java.lang.Deprecated
        public java.lang.String getProxyPackageName() {
            android.app.AppOpsManager.OpEventProxyInfo lastProxyInfo = getLastProxyInfo(31);
            if (lastProxyInfo == null) {
                return null;
            }
            return lastProxyInfo.getPackageName();
        }

        @java.lang.Deprecated
        public java.lang.String getProxyPackageName(int i, int i2) {
            android.app.AppOpsManager.OpEventProxyInfo lastProxyInfo = getLastProxyInfo(i, i, i2);
            if (lastProxyInfo == null) {
                return null;
            }
            return lastProxyInfo.getPackageName();
        }

        public android.app.AppOpsManager.OpEventProxyInfo getLastProxyInfo(int i) {
            return getLastProxyInfo(100, 700, i);
        }

        public android.app.AppOpsManager.OpEventProxyInfo getLastForegroundProxyInfo(int i) {
            return getLastProxyInfo(100, android.app.AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public android.app.AppOpsManager.OpEventProxyInfo getLastBackgroundProxyInfo(int i) {
            return getLastProxyInfo(android.app.AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public android.app.AppOpsManager.OpEventProxyInfo getLastProxyInfo(int i, int i2, int i3) {
            android.app.AppOpsManager.NoteOpEvent lastAccessEvent = getLastAccessEvent(i, i2, i3);
            if (lastAccessEvent == null) {
                return null;
            }
            return lastAccessEvent.getProxy();
        }

        public OpEntry(int i, int i2, java.util.Map<java.lang.String, android.app.AppOpsManager.AttributedOpEntry> map) {
            this.mOp = i;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mOp, "from", 0L, "to", 147L);
            this.mMode = i2;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.AppOpsManager.Mode.class, (java.lang.annotation.Annotation) null, this.mMode);
            this.mAttributedOpEntries = map;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAttributedOpEntries);
        }

        public int getMode() {
            return this.mMode;
        }

        public java.util.Map<java.lang.String, android.app.AppOpsManager.AttributedOpEntry> getAttributedOpEntries() {
            return this.mAttributedOpEntries;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mOp);
            parcel.writeInt(this.mMode);
            parcel.writeMap(this.mAttributedOpEntries);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        OpEntry(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap();
            parcel.readMap(linkedHashMap, android.app.AppOpsManager.AttributedOpEntry.class.getClassLoader());
            this.mOp = readInt;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mOp, "from", 0L, "to", 147L);
            this.mMode = readInt2;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<? extends java.lang.annotation.Annotation>) android.app.AppOpsManager.Mode.class, (java.lang.annotation.Annotation) null, this.mMode);
            this.mAttributedOpEntries = linkedHashMap;
            com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAttributedOpEntries);
        }
    }

    @android.annotation.SystemApi
    public static final class HistoricalOpsRequest {
        private final java.lang.String mAttributionTag;
        private final long mBeginTimeMillis;
        private final long mEndTimeMillis;
        private final int mFilter;
        private final int mFlags;
        private final int mHistoryFlags;
        private final java.util.List<java.lang.String> mOpNames;
        private final java.lang.String mPackageName;
        private final int mUid;

        private HistoricalOpsRequest(int i, java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, int i2, int i3, long j, long j2, int i4) {
            this.mUid = i;
            this.mPackageName = str;
            this.mAttributionTag = str2;
            this.mOpNames = list;
            this.mHistoryFlags = i2;
            this.mFilter = i3;
            this.mBeginTimeMillis = j;
            this.mEndTimeMillis = j2;
            this.mFlags = i4;
        }

        @android.annotation.SystemApi
        public static final class Builder {
            private java.lang.String mAttributionTag;
            private final long mBeginTimeMillis;
            private final long mEndTimeMillis;
            private int mFilter;
            private int mHistoryFlags;
            private java.util.List<java.lang.String> mOpNames;
            private java.lang.String mPackageName;
            private int mUid = -1;
            private int mFlags = 31;

            public Builder(long j, long j2) {
                com.android.internal.util.Preconditions.checkArgument(j >= 0 && j < j2, "beginTimeMillis must be non negative and lesser than endTimeMillis");
                this.mBeginTimeMillis = j;
                this.mEndTimeMillis = j2;
                this.mHistoryFlags = 1;
            }

            public android.app.AppOpsManager.HistoricalOpsRequest.Builder setUid(int i) {
                com.android.internal.util.Preconditions.checkArgument(i == -1 || i >= 0, "uid must be -1 or non negative");
                this.mUid = i;
                if (i != -1) {
                    this.mFilter |= 1;
                } else {
                    this.mFilter &= -2;
                }
                return this;
            }

            public android.app.AppOpsManager.HistoricalOpsRequest.Builder setPackageName(java.lang.String str) {
                this.mPackageName = str;
                if (str == null) {
                    this.mFilter &= -3;
                } else {
                    this.mFilter |= 2;
                }
                return this;
            }

            public android.app.AppOpsManager.HistoricalOpsRequest.Builder setAttributionTag(java.lang.String str) {
                this.mAttributionTag = str;
                this.mFilter |= 4;
                return this;
            }

            public android.app.AppOpsManager.HistoricalOpsRequest.Builder setOpNames(java.util.List<java.lang.String> list) {
                if (list != null) {
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        com.android.internal.util.Preconditions.checkArgument(android.app.AppOpsManager.strOpToOp(list.get(i)) != -1);
                    }
                }
                this.mOpNames = list;
                if (this.mOpNames == null) {
                    this.mFilter &= -9;
                } else {
                    this.mFilter |= 8;
                }
                return this;
            }

            public android.app.AppOpsManager.HistoricalOpsRequest.Builder setFlags(int i) {
                com.android.internal.util.Preconditions.checkFlagsArgument(i, 31);
                this.mFlags = i;
                return this;
            }

            public android.app.AppOpsManager.HistoricalOpsRequest.Builder setHistoryFlags(int i) {
                com.android.internal.util.Preconditions.checkFlagsArgument(i, 7);
                this.mHistoryFlags = i;
                return this;
            }

            public android.app.AppOpsManager.HistoricalOpsRequest build() {
                return new android.app.AppOpsManager.HistoricalOpsRequest(this.mUid, this.mPackageName, this.mAttributionTag, this.mOpNames, this.mHistoryFlags, this.mFilter, this.mBeginTimeMillis, this.mEndTimeMillis, this.mFlags);
            }
        }
    }

    @android.annotation.SystemApi
    public static final class HistoricalOps implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.AppOpsManager.HistoricalOps> CREATOR = new android.os.Parcelable.Creator<android.app.AppOpsManager.HistoricalOps>() { // from class: android.app.AppOpsManager.HistoricalOps.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.HistoricalOps createFromParcel(android.os.Parcel parcel) {
                return new android.app.AppOpsManager.HistoricalOps(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.HistoricalOps[] newArray(int i) {
                return new android.app.AppOpsManager.HistoricalOps[i];
            }
        };
        private long mBeginTimeMillis;
        private long mEndTimeMillis;
        private android.util.SparseArray<android.app.AppOpsManager.HistoricalUidOps> mHistoricalUidOps;

        public HistoricalOps(long j, long j2) {
            com.android.internal.util.Preconditions.checkState(j <= j2);
            this.mBeginTimeMillis = j;
            this.mEndTimeMillis = j2;
        }

        public HistoricalOps(android.app.AppOpsManager.HistoricalOps historicalOps) {
            this.mBeginTimeMillis = historicalOps.mBeginTimeMillis;
            this.mEndTimeMillis = historicalOps.mEndTimeMillis;
            com.android.internal.util.Preconditions.checkState(this.mBeginTimeMillis <= this.mEndTimeMillis);
            if (historicalOps.mHistoricalUidOps != null) {
                int uidCount = historicalOps.getUidCount();
                for (int i = 0; i < uidCount; i++) {
                    android.app.AppOpsManager.HistoricalUidOps historicalUidOps = new android.app.AppOpsManager.HistoricalUidOps(historicalOps.getUidOpsAt(i));
                    if (this.mHistoricalUidOps == null) {
                        this.mHistoricalUidOps = new android.util.SparseArray<>(uidCount);
                    }
                    this.mHistoricalUidOps.put(historicalUidOps.getUid(), historicalUidOps);
                }
            }
        }

        private HistoricalOps(android.os.Parcel parcel) {
            this.mBeginTimeMillis = parcel.readLong();
            this.mEndTimeMillis = parcel.readLong();
            int[] createIntArray = parcel.createIntArray();
            if (!com.android.internal.util.ArrayUtils.isEmpty(createIntArray)) {
                android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readParcelable(android.app.AppOpsManager.HistoricalOps.class.getClassLoader(), android.content.pm.ParceledListSlice.class);
                java.util.List list = parceledListSlice != null ? parceledListSlice.getList() : null;
                if (list == null) {
                    return;
                }
                for (int i = 0; i < createIntArray.length; i++) {
                    if (this.mHistoricalUidOps == null) {
                        this.mHistoricalUidOps = new android.util.SparseArray<>();
                    }
                    this.mHistoricalUidOps.put(createIntArray[i], (android.app.AppOpsManager.HistoricalUidOps) list.get(i));
                }
            }
        }

        public android.app.AppOpsManager.HistoricalOps spliceFromBeginning(double d) {
            return splice(d, true);
        }

        public android.app.AppOpsManager.HistoricalOps spliceFromEnd(double d) {
            return splice(d, false);
        }

        private android.app.AppOpsManager.HistoricalOps splice(double d, boolean z) {
            long durationMillis;
            long j;
            if (z) {
                durationMillis = this.mBeginTimeMillis;
                j = (long) (this.mBeginTimeMillis + (getDurationMillis() * d));
                this.mBeginTimeMillis = j;
            } else {
                durationMillis = (long) (this.mEndTimeMillis - (getDurationMillis() * d));
                j = this.mEndTimeMillis;
                this.mEndTimeMillis = durationMillis;
            }
            int uidCount = getUidCount();
            android.app.AppOpsManager.HistoricalOps historicalOps = null;
            for (int i = 0; i < uidCount; i++) {
                android.app.AppOpsManager.HistoricalUidOps splice = getUidOpsAt(i).splice(d);
                if (splice != null) {
                    if (historicalOps == null) {
                        historicalOps = new android.app.AppOpsManager.HistoricalOps(durationMillis, j);
                    }
                    if (historicalOps.mHistoricalUidOps == null) {
                        historicalOps.mHistoricalUidOps = new android.util.SparseArray<>();
                    }
                    historicalOps.mHistoricalUidOps.put(splice.getUid(), splice);
                }
            }
            return historicalOps;
        }

        public void merge(android.app.AppOpsManager.HistoricalOps historicalOps) {
            this.mBeginTimeMillis = java.lang.Math.min(this.mBeginTimeMillis, historicalOps.mBeginTimeMillis);
            this.mEndTimeMillis = java.lang.Math.max(this.mEndTimeMillis, historicalOps.mEndTimeMillis);
            int uidCount = historicalOps.getUidCount();
            for (int i = 0; i < uidCount; i++) {
                android.app.AppOpsManager.HistoricalUidOps uidOpsAt = historicalOps.getUidOpsAt(i);
                android.app.AppOpsManager.HistoricalUidOps uidOps = getUidOps(uidOpsAt.getUid());
                if (uidOps != null) {
                    uidOps.merge(uidOpsAt);
                } else {
                    if (this.mHistoricalUidOps == null) {
                        this.mHistoricalUidOps = new android.util.SparseArray<>();
                    }
                    this.mHistoricalUidOps.put(uidOpsAt.getUid(), uidOpsAt);
                }
            }
        }

        public void filter(int i, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, int i2, int i3, long j, long j2) {
            long durationMillis = getDurationMillis();
            this.mBeginTimeMillis = java.lang.Math.max(this.mBeginTimeMillis, j);
            this.mEndTimeMillis = java.lang.Math.min(this.mEndTimeMillis, j2);
            double min = java.lang.Math.min((j2 - j) / durationMillis, 1.0d);
            for (int uidCount = getUidCount() - 1; uidCount >= 0; uidCount--) {
                android.app.AppOpsManager.HistoricalUidOps valueAt = this.mHistoricalUidOps.valueAt(uidCount);
                if ((i3 & 1) != 0 && i != valueAt.getUid()) {
                    this.mHistoricalUidOps.removeAt(uidCount);
                }
                valueAt.filter(str, str2, strArr, i3, i2, min, this.mBeginTimeMillis, this.mEndTimeMillis);
                if (valueAt.getPackageCount() == 0) {
                    this.mHistoricalUidOps.removeAt(uidCount);
                }
            }
        }

        public boolean isEmpty() {
            if (getBeginTimeMillis() >= getEndTimeMillis()) {
                return true;
            }
            for (int uidCount = getUidCount() - 1; uidCount >= 0; uidCount--) {
                if (!this.mHistoricalUidOps.valueAt(uidCount).isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        public long getDurationMillis() {
            return this.mEndTimeMillis - this.mBeginTimeMillis;
        }

        public void increaseAccessCount(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, long j) {
            getOrCreateHistoricalUidOps(i2).increaseAccessCount(i, str, str2, i3, i4, j);
        }

        public void increaseRejectCount(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, long j) {
            getOrCreateHistoricalUidOps(i2).increaseRejectCount(i, str, str2, i3, i4, j);
        }

        public void increaseAccessDuration(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, long j) {
            getOrCreateHistoricalUidOps(i2).increaseAccessDuration(i, str, str2, i3, i4, j);
        }

        public void addDiscreteAccess(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, long j, long j2) {
            getOrCreateHistoricalUidOps(i2).addDiscreteAccess(i, str, str2, i3, i4, j, j2, null);
        }

        public void addDiscreteAccess(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, long j, long j2, android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo) {
            getOrCreateHistoricalUidOps(i2).addDiscreteAccess(i, str, str2, i3, i4, j, j2, opEventProxyInfo);
        }

        public void offsetBeginAndEndTime(long j) {
            this.mBeginTimeMillis += j;
            this.mEndTimeMillis += j;
        }

        public void setBeginAndEndTime(long j, long j2) {
            this.mBeginTimeMillis = j;
            this.mEndTimeMillis = j2;
        }

        public void setBeginTime(long j) {
            this.mBeginTimeMillis = j;
        }

        public void setEndTime(long j) {
            this.mEndTimeMillis = j;
        }

        public long getBeginTimeMillis() {
            return this.mBeginTimeMillis;
        }

        public long getEndTimeMillis() {
            return this.mEndTimeMillis;
        }

        public int getUidCount() {
            if (this.mHistoricalUidOps == null) {
                return 0;
            }
            return this.mHistoricalUidOps.size();
        }

        public android.app.AppOpsManager.HistoricalUidOps getUidOpsAt(int i) {
            if (this.mHistoricalUidOps == null) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            return this.mHistoricalUidOps.valueAt(i);
        }

        public android.app.AppOpsManager.HistoricalUidOps getUidOps(int i) {
            if (this.mHistoricalUidOps == null) {
                return null;
            }
            return this.mHistoricalUidOps.get(i);
        }

        public void clearHistory(int i, java.lang.String str) {
            android.app.AppOpsManager.HistoricalUidOps orCreateHistoricalUidOps = getOrCreateHistoricalUidOps(i);
            orCreateHistoricalUidOps.clearHistory(str);
            if (orCreateHistoricalUidOps.isEmpty()) {
                this.mHistoricalUidOps.remove(i);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeLong(this.mBeginTimeMillis);
            parcel.writeLong(this.mEndTimeMillis);
            if (this.mHistoricalUidOps != null) {
                int size = this.mHistoricalUidOps.size();
                parcel.writeInt(size);
                for (int i2 = 0; i2 < size; i2++) {
                    parcel.writeInt(this.mHistoricalUidOps.keyAt(i2));
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(size);
                for (int i3 = 0; i3 < size; i3++) {
                    arrayList.add(this.mHistoricalUidOps.valueAt(i3));
                }
                parcel.writeParcelable(new android.content.pm.ParceledListSlice(arrayList), i);
                return;
            }
            parcel.writeInt(-1);
        }

        public void accept(android.app.AppOpsManager.HistoricalOpsVisitor historicalOpsVisitor) {
            historicalOpsVisitor.visitHistoricalOps(this);
            int uidCount = getUidCount();
            for (int i = 0; i < uidCount; i++) {
                getUidOpsAt(i).accept(historicalOpsVisitor);
            }
        }

        private android.app.AppOpsManager.HistoricalUidOps getOrCreateHistoricalUidOps(int i) {
            if (this.mHistoricalUidOps == null) {
                this.mHistoricalUidOps = new android.util.SparseArray<>();
            }
            android.app.AppOpsManager.HistoricalUidOps historicalUidOps = this.mHistoricalUidOps.get(i);
            if (historicalUidOps == null) {
                android.app.AppOpsManager.HistoricalUidOps historicalUidOps2 = new android.app.AppOpsManager.HistoricalUidOps(i);
                this.mHistoricalUidOps.put(i, historicalUidOps2);
                return historicalUidOps2;
            }
            return historicalUidOps;
        }

        public static double round(double d) {
            return java.lang.Math.floor(d + 0.5d);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.app.AppOpsManager.HistoricalOps historicalOps = (android.app.AppOpsManager.HistoricalOps) obj;
            if (this.mBeginTimeMillis != historicalOps.mBeginTimeMillis || this.mEndTimeMillis != historicalOps.mEndTimeMillis) {
                return false;
            }
            if (this.mHistoricalUidOps == null) {
                if (historicalOps.mHistoricalUidOps != null) {
                    return false;
                }
            } else if (!this.mHistoricalUidOps.equals(historicalOps.mHistoricalUidOps)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((int) (this.mBeginTimeMillis ^ (this.mBeginTimeMillis >>> 32))) * 31) + this.mHistoricalUidOps.hashCode();
        }

        public java.lang.String toString() {
            return getClass().getSimpleName() + "[from:" + this.mBeginTimeMillis + " to:" + this.mEndTimeMillis + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    @android.annotation.SystemApi
    public static final class HistoricalUidOps implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.AppOpsManager.HistoricalUidOps> CREATOR = new android.os.Parcelable.Creator<android.app.AppOpsManager.HistoricalUidOps>() { // from class: android.app.AppOpsManager.HistoricalUidOps.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.HistoricalUidOps createFromParcel(android.os.Parcel parcel) {
                return new android.app.AppOpsManager.HistoricalUidOps(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.HistoricalUidOps[] newArray(int i) {
                return new android.app.AppOpsManager.HistoricalUidOps[i];
            }
        };
        private android.util.ArrayMap<java.lang.String, android.app.AppOpsManager.HistoricalPackageOps> mHistoricalPackageOps;
        private final int mUid;

        public HistoricalUidOps(int i) {
            this.mUid = i;
        }

        private HistoricalUidOps(android.app.AppOpsManager.HistoricalUidOps historicalUidOps) {
            this.mUid = historicalUidOps.mUid;
            int packageCount = historicalUidOps.getPackageCount();
            for (int i = 0; i < packageCount; i++) {
                android.app.AppOpsManager.HistoricalPackageOps historicalPackageOps = new android.app.AppOpsManager.HistoricalPackageOps(historicalUidOps.getPackageOpsAt(i));
                if (this.mHistoricalPackageOps == null) {
                    this.mHistoricalPackageOps = new android.util.ArrayMap<>(packageCount);
                }
                this.mHistoricalPackageOps.put(historicalPackageOps.getPackageName(), historicalPackageOps);
            }
        }

        private HistoricalUidOps(android.os.Parcel parcel) {
            this.mUid = parcel.readInt();
            this.mHistoricalPackageOps = parcel.createTypedArrayMap(android.app.AppOpsManager.HistoricalPackageOps.CREATOR);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.app.AppOpsManager.HistoricalUidOps splice(double d) {
            int packageCount = getPackageCount();
            android.app.AppOpsManager.HistoricalUidOps historicalUidOps = null;
            for (int i = 0; i < packageCount; i++) {
                android.app.AppOpsManager.HistoricalPackageOps splice = getPackageOpsAt(i).splice(d);
                if (splice != null) {
                    if (historicalUidOps == null) {
                        historicalUidOps = new android.app.AppOpsManager.HistoricalUidOps(this.mUid);
                    }
                    if (historicalUidOps.mHistoricalPackageOps == null) {
                        historicalUidOps.mHistoricalPackageOps = new android.util.ArrayMap<>();
                    }
                    historicalUidOps.mHistoricalPackageOps.put(splice.getPackageName(), splice);
                }
            }
            return historicalUidOps;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void merge(android.app.AppOpsManager.HistoricalUidOps historicalUidOps) {
            int packageCount = historicalUidOps.getPackageCount();
            for (int i = 0; i < packageCount; i++) {
                android.app.AppOpsManager.HistoricalPackageOps packageOpsAt = historicalUidOps.getPackageOpsAt(i);
                android.app.AppOpsManager.HistoricalPackageOps packageOps = getPackageOps(packageOpsAt.getPackageName());
                if (packageOps != null) {
                    packageOps.merge(packageOpsAt);
                } else {
                    if (this.mHistoricalPackageOps == null) {
                        this.mHistoricalPackageOps = new android.util.ArrayMap<>();
                    }
                    this.mHistoricalPackageOps.put(packageOpsAt.getPackageName(), packageOpsAt);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void filter(java.lang.String str, java.lang.String str2, java.lang.String[] strArr, int i, int i2, double d, long j, long j2) {
            for (int packageCount = getPackageCount() - 1; packageCount >= 0; packageCount--) {
                android.app.AppOpsManager.HistoricalPackageOps packageOpsAt = getPackageOpsAt(packageCount);
                if ((i & 2) != 0 && !str.equals(packageOpsAt.getPackageName())) {
                    this.mHistoricalPackageOps.removeAt(packageCount);
                }
                packageOpsAt.filter(str2, strArr, i, i2, d, j, j2);
                if (packageOpsAt.getAttributedOpsCount() == 0) {
                    this.mHistoricalPackageOps.removeAt(packageCount);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmpty() {
            for (int packageCount = getPackageCount() - 1; packageCount >= 0; packageCount--) {
                if (!this.mHistoricalPackageOps.valueAt(packageCount).isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessCount(int i, java.lang.String str, java.lang.String str2, int i2, int i3, long j) {
            getOrCreateHistoricalPackageOps(str).increaseAccessCount(i, str2, i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseRejectCount(int i, java.lang.String str, java.lang.String str2, int i2, int i3, long j) {
            getOrCreateHistoricalPackageOps(str).increaseRejectCount(i, str2, i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessDuration(int i, java.lang.String str, java.lang.String str2, int i2, int i3, long j) {
            getOrCreateHistoricalPackageOps(str).increaseAccessDuration(i, str2, i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDiscreteAccess(int i, java.lang.String str, java.lang.String str2, int i2, int i3, long j, long j2, android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo) {
            getOrCreateHistoricalPackageOps(str).addDiscreteAccess(i, str2, i2, i3, j, j2, opEventProxyInfo);
        }

        public int getUid() {
            return this.mUid;
        }

        public int getPackageCount() {
            if (this.mHistoricalPackageOps == null) {
                return 0;
            }
            return this.mHistoricalPackageOps.size();
        }

        public android.app.AppOpsManager.HistoricalPackageOps getPackageOpsAt(int i) {
            if (this.mHistoricalPackageOps == null) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            return this.mHistoricalPackageOps.valueAt(i);
        }

        public android.app.AppOpsManager.HistoricalPackageOps getPackageOps(java.lang.String str) {
            if (this.mHistoricalPackageOps == null) {
                return null;
            }
            return this.mHistoricalPackageOps.get(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearHistory(java.lang.String str) {
            if (this.mHistoricalPackageOps != null) {
                this.mHistoricalPackageOps.remove(str);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mUid);
            parcel.writeTypedArrayMap(this.mHistoricalPackageOps, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void accept(android.app.AppOpsManager.HistoricalOpsVisitor historicalOpsVisitor) {
            historicalOpsVisitor.visitHistoricalUidOps(this);
            int packageCount = getPackageCount();
            for (int i = 0; i < packageCount; i++) {
                getPackageOpsAt(i).accept(historicalOpsVisitor);
            }
        }

        private android.app.AppOpsManager.HistoricalPackageOps getOrCreateHistoricalPackageOps(java.lang.String str) {
            if (this.mHistoricalPackageOps == null) {
                this.mHistoricalPackageOps = new android.util.ArrayMap<>();
            }
            android.app.AppOpsManager.HistoricalPackageOps historicalPackageOps = this.mHistoricalPackageOps.get(str);
            if (historicalPackageOps == null) {
                android.app.AppOpsManager.HistoricalPackageOps historicalPackageOps2 = new android.app.AppOpsManager.HistoricalPackageOps(str);
                this.mHistoricalPackageOps.put(str, historicalPackageOps2);
                return historicalPackageOps2;
            }
            return historicalPackageOps;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.app.AppOpsManager.HistoricalUidOps historicalUidOps = (android.app.AppOpsManager.HistoricalUidOps) obj;
            if (this.mUid != historicalUidOps.mUid) {
                return false;
            }
            if (this.mHistoricalPackageOps == null) {
                if (historicalUidOps.mHistoricalPackageOps != null) {
                    return false;
                }
            } else if (!this.mHistoricalPackageOps.equals(historicalUidOps.mHistoricalPackageOps)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (this.mUid * 31) + (this.mHistoricalPackageOps != null ? this.mHistoricalPackageOps.hashCode() : 0);
        }
    }

    @android.annotation.SystemApi
    public static final class HistoricalPackageOps implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.AppOpsManager.HistoricalPackageOps> CREATOR = new android.os.Parcelable.Creator<android.app.AppOpsManager.HistoricalPackageOps>() { // from class: android.app.AppOpsManager.HistoricalPackageOps.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.HistoricalPackageOps createFromParcel(android.os.Parcel parcel) {
                return new android.app.AppOpsManager.HistoricalPackageOps(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.HistoricalPackageOps[] newArray(int i) {
                return new android.app.AppOpsManager.HistoricalPackageOps[i];
            }
        };
        private android.util.ArrayMap<java.lang.String, android.app.AppOpsManager.AttributedHistoricalOps> mAttributedHistoricalOps;
        private final java.lang.String mPackageName;

        public HistoricalPackageOps(java.lang.String str) {
            this.mPackageName = str;
        }

        private HistoricalPackageOps(android.app.AppOpsManager.HistoricalPackageOps historicalPackageOps) {
            this.mPackageName = historicalPackageOps.mPackageName;
            int attributedOpsCount = historicalPackageOps.getAttributedOpsCount();
            for (int i = 0; i < attributedOpsCount; i++) {
                android.app.AppOpsManager.AttributedHistoricalOps attributedHistoricalOps = new android.app.AppOpsManager.AttributedHistoricalOps(historicalPackageOps.getAttributedOpsAt(i));
                if (this.mAttributedHistoricalOps == null) {
                    this.mAttributedHistoricalOps = new android.util.ArrayMap<>(attributedOpsCount);
                }
                this.mAttributedHistoricalOps.put(attributedHistoricalOps.getTag(), attributedHistoricalOps);
            }
        }

        private HistoricalPackageOps(android.os.Parcel parcel) {
            this.mPackageName = parcel.readString();
            this.mAttributedHistoricalOps = parcel.createTypedArrayMap(android.app.AppOpsManager.AttributedHistoricalOps.CREATOR);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.app.AppOpsManager.HistoricalPackageOps splice(double d) {
            int attributedOpsCount = getAttributedOpsCount();
            android.app.AppOpsManager.HistoricalPackageOps historicalPackageOps = null;
            for (int i = 0; i < attributedOpsCount; i++) {
                android.app.AppOpsManager.AttributedHistoricalOps splice = getAttributedOpsAt(i).splice(d);
                if (splice != null) {
                    if (historicalPackageOps == null) {
                        historicalPackageOps = new android.app.AppOpsManager.HistoricalPackageOps(this.mPackageName);
                    }
                    if (historicalPackageOps.mAttributedHistoricalOps == null) {
                        historicalPackageOps.mAttributedHistoricalOps = new android.util.ArrayMap<>();
                    }
                    historicalPackageOps.mAttributedHistoricalOps.put(splice.getTag(), splice);
                }
            }
            return historicalPackageOps;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void merge(android.app.AppOpsManager.HistoricalPackageOps historicalPackageOps) {
            int attributedOpsCount = historicalPackageOps.getAttributedOpsCount();
            for (int i = 0; i < attributedOpsCount; i++) {
                android.app.AppOpsManager.AttributedHistoricalOps attributedOpsAt = historicalPackageOps.getAttributedOpsAt(i);
                android.app.AppOpsManager.AttributedHistoricalOps attributedOps = getAttributedOps(attributedOpsAt.getTag());
                if (attributedOps != null) {
                    attributedOps.merge(attributedOpsAt);
                } else {
                    if (this.mAttributedHistoricalOps == null) {
                        this.mAttributedHistoricalOps = new android.util.ArrayMap<>();
                    }
                    this.mAttributedHistoricalOps.put(attributedOpsAt.getTag(), attributedOpsAt);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void filter(java.lang.String str, java.lang.String[] strArr, int i, int i2, double d, long j, long j2) {
            for (int attributedOpsCount = getAttributedOpsCount() - 1; attributedOpsCount >= 0; attributedOpsCount--) {
                android.app.AppOpsManager.AttributedHistoricalOps attributedOpsAt = getAttributedOpsAt(attributedOpsCount);
                if ((i & 4) != 0 && !java.util.Objects.equals(str, attributedOpsAt.getTag())) {
                    this.mAttributedHistoricalOps.removeAt(attributedOpsCount);
                }
                attributedOpsAt.filter(strArr, i, i2, d, j, j2);
                if (attributedOpsAt.getOpCount() == 0) {
                    this.mAttributedHistoricalOps.removeAt(attributedOpsCount);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void accept(android.app.AppOpsManager.HistoricalOpsVisitor historicalOpsVisitor) {
            historicalOpsVisitor.visitHistoricalPackageOps(this);
            int attributedOpsCount = getAttributedOpsCount();
            for (int i = 0; i < attributedOpsCount; i++) {
                getAttributedOpsAt(i).accept(historicalOpsVisitor);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmpty() {
            for (int attributedOpsCount = getAttributedOpsCount() - 1; attributedOpsCount >= 0; attributedOpsCount--) {
                if (!this.mAttributedHistoricalOps.valueAt(attributedOpsCount).isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessCount(int i, java.lang.String str, int i2, int i3, long j) {
            getOrCreateAttributedHistoricalOps(str).increaseAccessCount(i, i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseRejectCount(int i, java.lang.String str, int i2, int i3, long j) {
            getOrCreateAttributedHistoricalOps(str).increaseRejectCount(i, i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessDuration(int i, java.lang.String str, int i2, int i3, long j) {
            getOrCreateAttributedHistoricalOps(str).increaseAccessDuration(i, i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDiscreteAccess(int i, java.lang.String str, int i2, int i3, long j, long j2, android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo) {
            getOrCreateAttributedHistoricalOps(str).addDiscreteAccess(i, i2, i3, j, j2, opEventProxyInfo);
        }

        public java.lang.String getPackageName() {
            return this.mPackageName;
        }

        private android.app.AppOpsManager.AttributedHistoricalOps getOrCreateAttributedHistoricalOps(java.lang.String str) {
            if (this.mAttributedHistoricalOps == null) {
                this.mAttributedHistoricalOps = new android.util.ArrayMap<>();
            }
            android.app.AppOpsManager.AttributedHistoricalOps attributedHistoricalOps = this.mAttributedHistoricalOps.get(str);
            if (attributedHistoricalOps == null) {
                android.app.AppOpsManager.AttributedHistoricalOps attributedHistoricalOps2 = new android.app.AppOpsManager.AttributedHistoricalOps(str);
                this.mAttributedHistoricalOps.put(str, attributedHistoricalOps2);
                return attributedHistoricalOps2;
            }
            return attributedHistoricalOps;
        }

        public int getOpCount() {
            int attributedOpsCount = getAttributedOpsCount();
            int i = 0;
            for (int i2 = 0; i2 < 148; i2++) {
                java.lang.String opToPublicName = android.app.AppOpsManager.opToPublicName(i2);
                int i3 = 0;
                while (true) {
                    if (i3 >= attributedOpsCount) {
                        break;
                    }
                    if (getAttributedOpsAt(i3).getOp(opToPublicName) == null) {
                        i3++;
                    } else {
                        i++;
                        break;
                    }
                }
            }
            return i;
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x002a, code lost:
        
            r2 = r2 + 1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public android.app.AppOpsManager.HistoricalOp getOpAt(int i) {
            int attributedOpsCount = getAttributedOpsCount();
            int i2 = 0;
            int i3 = 0;
            while (i2 < 148) {
                java.lang.String opToPublicName = android.app.AppOpsManager.opToPublicName(i2);
                int i4 = 0;
                while (true) {
                    if (i4 >= attributedOpsCount) {
                        break;
                    }
                    if (getAttributedOpsAt(i4).getOp(opToPublicName) == null) {
                        i4++;
                    } else {
                        if (i3 == i) {
                            return getOp(opToPublicName);
                        }
                        i3++;
                    }
                }
            }
            throw new java.lang.IndexOutOfBoundsException();
        }

        public android.app.AppOpsManager.HistoricalOp getOp(java.lang.String str) {
            if (this.mAttributedHistoricalOps == null) {
                return null;
            }
            int attributedOpsCount = getAttributedOpsCount();
            android.app.AppOpsManager.HistoricalOp historicalOp = null;
            for (int i = 0; i < attributedOpsCount; i++) {
                android.app.AppOpsManager.HistoricalOp op = getAttributedOpsAt(i).getOp(str);
                if (op != null) {
                    if (historicalOp == null) {
                        historicalOp = new android.app.AppOpsManager.HistoricalOp(op);
                    } else {
                        historicalOp.merge(op);
                    }
                }
            }
            return historicalOp;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.mPackageName);
            parcel.writeTypedArrayMap(this.mAttributedHistoricalOps, i);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.app.AppOpsManager.HistoricalPackageOps historicalPackageOps = (android.app.AppOpsManager.HistoricalPackageOps) obj;
            if (!this.mPackageName.equals(historicalPackageOps.mPackageName)) {
                return false;
            }
            if (this.mAttributedHistoricalOps == null) {
                if (historicalPackageOps.mAttributedHistoricalOps != null) {
                    return false;
                }
            } else if (!this.mAttributedHistoricalOps.equals(historicalPackageOps.mAttributedHistoricalOps)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return ((this.mPackageName != null ? this.mPackageName.hashCode() : 0) * 31) + (this.mAttributedHistoricalOps != null ? this.mAttributedHistoricalOps.hashCode() : 0);
        }

        public int getAttributedOpsCount() {
            if (this.mAttributedHistoricalOps == null) {
                return 0;
            }
            return this.mAttributedHistoricalOps.size();
        }

        public android.app.AppOpsManager.AttributedHistoricalOps getAttributedOpsAt(int i) {
            if (this.mAttributedHistoricalOps == null) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            return this.mAttributedHistoricalOps.valueAt(i);
        }

        public android.app.AppOpsManager.AttributedHistoricalOps getAttributedOps(java.lang.String str) {
            if (this.mAttributedHistoricalOps == null) {
                return null;
            }
            return this.mAttributedHistoricalOps.get(str);
        }
    }

    @android.annotation.SystemApi
    public static final class AttributedHistoricalOps implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.AppOpsManager.AttributedHistoricalOps> CREATOR = new android.os.Parcelable.Creator<android.app.AppOpsManager.AttributedHistoricalOps>() { // from class: android.app.AppOpsManager.AttributedHistoricalOps.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.AttributedHistoricalOps[] newArray(int i) {
                return new android.app.AppOpsManager.AttributedHistoricalOps[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.AttributedHistoricalOps createFromParcel(android.os.Parcel parcel) {
                return new android.app.AppOpsManager.AttributedHistoricalOps(parcel);
            }
        };
        private android.util.ArrayMap<java.lang.String, android.app.AppOpsManager.HistoricalOp> mHistoricalOps;
        private final java.lang.String mTag;

        public AttributedHistoricalOps(java.lang.String str) {
            this.mTag = str;
        }

        private AttributedHistoricalOps(android.app.AppOpsManager.AttributedHistoricalOps attributedHistoricalOps) {
            this.mTag = attributedHistoricalOps.mTag;
            int opCount = attributedHistoricalOps.getOpCount();
            for (int i = 0; i < opCount; i++) {
                android.app.AppOpsManager.HistoricalOp historicalOp = new android.app.AppOpsManager.HistoricalOp(attributedHistoricalOps.getOpAt(i));
                if (this.mHistoricalOps == null) {
                    this.mHistoricalOps = new android.util.ArrayMap<>(opCount);
                }
                this.mHistoricalOps.put(historicalOp.getOpName(), historicalOp);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.app.AppOpsManager.AttributedHistoricalOps splice(double d) {
            int opCount = getOpCount();
            android.app.AppOpsManager.AttributedHistoricalOps attributedHistoricalOps = null;
            for (int i = 0; i < opCount; i++) {
                android.app.AppOpsManager.HistoricalOp splice = getOpAt(i).splice(d);
                if (splice != null) {
                    if (attributedHistoricalOps == null) {
                        attributedHistoricalOps = new android.app.AppOpsManager.AttributedHistoricalOps(this.mTag, (android.util.ArrayMap<java.lang.String, android.app.AppOpsManager.HistoricalOp>) null);
                    }
                    if (attributedHistoricalOps.mHistoricalOps == null) {
                        attributedHistoricalOps.mHistoricalOps = new android.util.ArrayMap<>();
                    }
                    attributedHistoricalOps.mHistoricalOps.put(splice.getOpName(), splice);
                }
            }
            return attributedHistoricalOps;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void merge(android.app.AppOpsManager.AttributedHistoricalOps attributedHistoricalOps) {
            int opCount = attributedHistoricalOps.getOpCount();
            for (int i = 0; i < opCount; i++) {
                android.app.AppOpsManager.HistoricalOp opAt = attributedHistoricalOps.getOpAt(i);
                android.app.AppOpsManager.HistoricalOp op = getOp(opAt.getOpName());
                if (op != null) {
                    op.merge(opAt);
                } else {
                    if (this.mHistoricalOps == null) {
                        this.mHistoricalOps = new android.util.ArrayMap<>();
                    }
                    this.mHistoricalOps.put(opAt.getOpName(), opAt);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void filter(java.lang.String[] strArr, int i, int i2, double d, long j, long j2) {
            for (int opCount = getOpCount() - 1; opCount >= 0; opCount--) {
                android.app.AppOpsManager.HistoricalOp valueAt = this.mHistoricalOps.valueAt(opCount);
                if ((i & 8) != 0 && !com.android.internal.util.ArrayUtils.contains(strArr, valueAt.getOpName())) {
                    this.mHistoricalOps.removeAt(opCount);
                }
                valueAt.filter(i2, d, j, j2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmpty() {
            for (int opCount = getOpCount() - 1; opCount >= 0; opCount--) {
                if (!this.mHistoricalOps.valueAt(opCount).isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessCount(int i, int i2, int i3, long j) {
            getOrCreateHistoricalOp(i).increaseAccessCount(i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseRejectCount(int i, int i2, int i3, long j) {
            getOrCreateHistoricalOp(i).increaseRejectCount(i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessDuration(int i, int i2, int i3, long j) {
            getOrCreateHistoricalOp(i).increaseAccessDuration(i2, i3, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDiscreteAccess(int i, int i2, int i3, long j, long j2, android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo) {
            getOrCreateHistoricalOp(i).addDiscreteAccess(i2, i3, j, j2, opEventProxyInfo);
        }

        public int getOpCount() {
            if (this.mHistoricalOps == null) {
                return 0;
            }
            return this.mHistoricalOps.size();
        }

        public android.app.AppOpsManager.HistoricalOp getOpAt(int i) {
            if (this.mHistoricalOps == null) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            return this.mHistoricalOps.valueAt(i);
        }

        public android.app.AppOpsManager.HistoricalOp getOp(java.lang.String str) {
            if (this.mHistoricalOps == null) {
                return null;
            }
            return this.mHistoricalOps.get(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void accept(android.app.AppOpsManager.HistoricalOpsVisitor historicalOpsVisitor) {
            historicalOpsVisitor.visitHistoricalAttributionOps(this);
            int opCount = getOpCount();
            for (int i = 0; i < opCount; i++) {
                getOpAt(i).accept(historicalOpsVisitor);
            }
        }

        private android.app.AppOpsManager.HistoricalOp getOrCreateHistoricalOp(int i) {
            if (this.mHistoricalOps == null) {
                this.mHistoricalOps = new android.util.ArrayMap<>();
            }
            java.lang.String str = android.app.AppOpsManager.sAppOpInfos[i].name;
            android.app.AppOpsManager.HistoricalOp historicalOp = this.mHistoricalOps.get(str);
            if (historicalOp == null) {
                android.app.AppOpsManager.HistoricalOp historicalOp2 = new android.app.AppOpsManager.HistoricalOp(i);
                this.mHistoricalOps.put(str, historicalOp2);
                return historicalOp2;
            }
            return historicalOp;
        }

        public AttributedHistoricalOps(java.lang.String str, android.util.ArrayMap<java.lang.String, android.app.AppOpsManager.HistoricalOp> arrayMap) {
            this.mTag = str;
            this.mHistoricalOps = arrayMap;
        }

        public java.lang.String getTag() {
            return this.mTag;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.app.AppOpsManager.AttributedHistoricalOps attributedHistoricalOps = (android.app.AppOpsManager.AttributedHistoricalOps) obj;
            if (java.util.Objects.equals(this.mTag, attributedHistoricalOps.mTag) && java.util.Objects.equals(this.mHistoricalOps, attributedHistoricalOps.mHistoricalOps)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return ((java.util.Objects.hashCode(this.mTag) + 31) * 31) + java.util.Objects.hashCode(this.mHistoricalOps);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            byte b = this.mTag != null ? (byte) 1 : (byte) 0;
            if (this.mHistoricalOps != null) {
                b = (byte) (b | 2);
            }
            parcel.writeByte(b);
            if (this.mTag != null) {
                parcel.writeString(this.mTag);
            }
            if (this.mHistoricalOps != null) {
                parcel.writeMap(this.mHistoricalOps);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        AttributedHistoricalOps(android.os.Parcel parcel) {
            byte readByte = parcel.readByte();
            android.util.ArrayMap<java.lang.String, android.app.AppOpsManager.HistoricalOp> arrayMap = null;
            java.lang.String readString = (readByte & 1) == 0 ? null : parcel.readString();
            if ((readByte & 2) != 0) {
                arrayMap = new android.util.ArrayMap<>();
                parcel.readMap(arrayMap, android.app.AppOpsManager.HistoricalOp.class.getClassLoader());
            }
            this.mTag = readString;
            this.mHistoricalOps = arrayMap;
        }
    }

    @android.annotation.SystemApi
    public static final class HistoricalOp implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.AppOpsManager.HistoricalOp> CREATOR = new android.os.Parcelable.Creator<android.app.AppOpsManager.HistoricalOp>() { // from class: android.app.AppOpsManager.HistoricalOp.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.HistoricalOp createFromParcel(android.os.Parcel parcel) {
                return new android.app.AppOpsManager.HistoricalOp(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.AppOpsManager.HistoricalOp[] newArray(int i) {
                return new android.app.AppOpsManager.HistoricalOp[i];
            }
        };
        private android.util.LongSparseLongArray mAccessCount;
        private android.util.LongSparseLongArray mAccessDuration;
        private java.util.List<android.app.AppOpsManager.AttributedOpEntry> mDiscreteAccesses;
        private final int mOp;
        private android.util.LongSparseLongArray mRejectCount;

        public HistoricalOp(int i) {
            this.mOp = i;
        }

        private HistoricalOp(android.app.AppOpsManager.HistoricalOp historicalOp) {
            this.mOp = historicalOp.mOp;
            if (historicalOp.mAccessCount != null) {
                this.mAccessCount = historicalOp.mAccessCount.m4827clone();
            }
            if (historicalOp.mRejectCount != null) {
                this.mRejectCount = historicalOp.mRejectCount.m4827clone();
            }
            if (historicalOp.mAccessDuration != null) {
                this.mAccessDuration = historicalOp.mAccessDuration.m4827clone();
            }
            int discreteAccessCount = historicalOp.getDiscreteAccessCount();
            for (int i = 0; i < discreteAccessCount; i++) {
                getOrCreateDiscreteAccesses().add(new android.app.AppOpsManager.AttributedOpEntry(historicalOp.getDiscreteAccessAt(i)));
            }
        }

        private HistoricalOp(android.os.Parcel parcel) {
            this.mOp = parcel.readInt();
            this.mAccessCount = android.app.AppOpsManager.readLongSparseLongArrayFromParcel(parcel);
            this.mRejectCount = android.app.AppOpsManager.readLongSparseLongArrayFromParcel(parcel);
            this.mAccessDuration = android.app.AppOpsManager.readLongSparseLongArrayFromParcel(parcel);
            this.mDiscreteAccesses = android.app.AppOpsManager.readDiscreteAccessArrayFromParcel(parcel);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void filter(int i, double d, long j, long j2) {
            if ((i & 1) == 0) {
                this.mAccessCount = null;
                this.mRejectCount = null;
                this.mAccessDuration = null;
            } else {
                scale(this.mAccessCount, d);
                scale(this.mRejectCount, d);
                scale(this.mAccessDuration, d);
            }
            if ((i & 2) == 0) {
                this.mDiscreteAccesses = null;
                return;
            }
            for (int discreteAccessCount = getDiscreteAccessCount() - 1; discreteAccessCount >= 0; discreteAccessCount--) {
                android.app.AppOpsManager.AttributedOpEntry attributedOpEntry = this.mDiscreteAccesses.get(discreteAccessCount);
                long lastAccessTime = attributedOpEntry.getLastAccessTime(31);
                if (java.lang.Long.max(lastAccessTime, attributedOpEntry.getLastDuration(31) + lastAccessTime) < j || lastAccessTime > j2) {
                    this.mDiscreteAccesses.remove(discreteAccessCount);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEmpty() {
            return (hasData(this.mAccessCount) || hasData(this.mRejectCount) || hasData(this.mAccessDuration) || this.mDiscreteAccesses != null) ? false : true;
        }

        private boolean hasData(android.util.LongSparseLongArray longSparseLongArray) {
            return longSparseLongArray != null && longSparseLongArray.size() > 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.app.AppOpsManager.HistoricalOp splice(double d) {
            android.app.AppOpsManager.HistoricalOp historicalOp = new android.app.AppOpsManager.HistoricalOp(this.mOp);
            android.util.LongSparseLongArray longSparseLongArray = this.mAccessCount;
            java.util.Objects.requireNonNull(historicalOp);
            splice(longSparseLongArray, new android.app.AppOpsManager$HistoricalOp$$ExternalSyntheticLambda0(historicalOp), d);
            android.util.LongSparseLongArray longSparseLongArray2 = this.mRejectCount;
            java.util.Objects.requireNonNull(historicalOp);
            splice(longSparseLongArray2, new android.app.AppOpsManager$HistoricalOp$$ExternalSyntheticLambda1(historicalOp), d);
            android.util.LongSparseLongArray longSparseLongArray3 = this.mAccessDuration;
            java.util.Objects.requireNonNull(historicalOp);
            splice(longSparseLongArray3, new android.app.AppOpsManager$HistoricalOp$$ExternalSyntheticLambda2(historicalOp), d);
            return historicalOp;
        }

        private static void splice(android.util.LongSparseLongArray longSparseLongArray, java.util.function.Supplier<android.util.LongSparseLongArray> supplier, double d) {
            if (longSparseLongArray != null) {
                int size = longSparseLongArray.size();
                for (int i = 0; i < size; i++) {
                    long keyAt = longSparseLongArray.keyAt(i);
                    long valueAt = longSparseLongArray.valueAt(i);
                    long round = java.lang.Math.round(valueAt * d);
                    if (round > 0) {
                        supplier.get().put(keyAt, round);
                        longSparseLongArray.put(keyAt, valueAt - round);
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void merge(android.app.AppOpsManager.HistoricalOp historicalOp) {
            merge(new android.app.AppOpsManager$HistoricalOp$$ExternalSyntheticLambda0(this), historicalOp.mAccessCount);
            merge(new android.app.AppOpsManager$HistoricalOp$$ExternalSyntheticLambda1(this), historicalOp.mRejectCount);
            merge(new android.app.AppOpsManager$HistoricalOp$$ExternalSyntheticLambda2(this), historicalOp.mAccessDuration);
            if (historicalOp.mDiscreteAccesses == null) {
                return;
            }
            if (this.mDiscreteAccesses == null) {
                this.mDiscreteAccesses = new java.util.ArrayList(historicalOp.mDiscreteAccesses);
                return;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int discreteAccessCount = historicalOp.getDiscreteAccessCount();
            int discreteAccessCount2 = getDiscreteAccessCount();
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i < discreteAccessCount || i2 < discreteAccessCount2) {
                    if (i == discreteAccessCount) {
                        arrayList.add(this.mDiscreteAccesses.get(i2));
                        i2++;
                    } else if (i2 == discreteAccessCount2) {
                        arrayList.add(historicalOp.mDiscreteAccesses.get(i));
                        i++;
                    } else if (this.mDiscreteAccesses.get(i2).getLastAccessTime(31) < historicalOp.mDiscreteAccesses.get(i).getLastAccessTime(31)) {
                        arrayList.add(this.mDiscreteAccesses.get(i2));
                        i2++;
                    } else {
                        arrayList.add(historicalOp.mDiscreteAccesses.get(i));
                        i++;
                    }
                } else {
                    this.mDiscreteAccesses = android.app.AppOpsManager.deduplicateDiscreteEvents(arrayList);
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessCount(int i, int i2, long j) {
            increaseCount(getOrCreateAccessCount(), i, i2, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseRejectCount(int i, int i2, long j) {
            increaseCount(getOrCreateRejectCount(), i, i2, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increaseAccessDuration(int i, int i2, long j) {
            increaseCount(getOrCreateAccessDuration(), i, i2, j);
        }

        private void increaseCount(android.util.LongSparseLongArray longSparseLongArray, int i, int i2, long j) {
            while (i2 != 0) {
                int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i2);
                i2 &= ~numberOfTrailingZeros;
                long makeKey = android.app.AppOpsManager.makeKey(i, numberOfTrailingZeros);
                longSparseLongArray.put(makeKey, longSparseLongArray.get(makeKey) + j);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDiscreteAccess(int i, int i2, long j, long j2, android.app.AppOpsManager.OpEventProxyInfo opEventProxyInfo) {
            java.util.List<android.app.AppOpsManager.AttributedOpEntry> orCreateDiscreteAccesses = getOrCreateDiscreteAccesses();
            android.util.LongSparseArray longSparseArray = new android.util.LongSparseArray();
            longSparseArray.append(android.app.AppOpsManager.makeKey(i, i2), new android.app.AppOpsManager.NoteOpEvent(j, j2, opEventProxyInfo));
            android.app.AppOpsManager.AttributedOpEntry attributedOpEntry = new android.app.AppOpsManager.AttributedOpEntry(this.mOp, false, longSparseArray, null);
            int size = orCreateDiscreteAccesses.size() - 1;
            while (size >= 0 && orCreateDiscreteAccesses.get(size).getLastAccessTime(31) >= j) {
                size--;
            }
            int i3 = size + 1;
            if (i3 < orCreateDiscreteAccesses.size() && orCreateDiscreteAccesses.get(i3).getLastAccessTime(31) == j) {
                orCreateDiscreteAccesses.set(i3, android.app.AppOpsManager.mergeAttributedOpEntries(java.util.Arrays.asList(orCreateDiscreteAccesses.get(i3), attributedOpEntry)));
            } else {
                orCreateDiscreteAccesses.add(i3, attributedOpEntry);
            }
        }

        public java.lang.String getOpName() {
            return android.app.AppOpsManager.sAppOpInfos[this.mOp].name;
        }

        public int getOpCode() {
            return this.mOp;
        }

        public int getDiscreteAccessCount() {
            if (this.mDiscreteAccesses == null) {
                return 0;
            }
            return this.mDiscreteAccesses.size();
        }

        public android.app.AppOpsManager.AttributedOpEntry getDiscreteAccessAt(int i) {
            if (this.mDiscreteAccesses == null) {
                throw new java.lang.IndexOutOfBoundsException();
            }
            return this.mDiscreteAccesses.get(i);
        }

        public long getForegroundAccessCount(int i) {
            return android.app.AppOpsManager.sumForFlagsInStates(this.mAccessCount, 100, android.app.AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public java.util.List<android.app.AppOpsManager.AttributedOpEntry> getForegroundDiscreteAccesses(int i) {
            return android.app.AppOpsManager.listForFlagsInStates(this.mDiscreteAccesses, 100, android.app.AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getBackgroundAccessCount(int i) {
            return android.app.AppOpsManager.sumForFlagsInStates(this.mAccessCount, android.app.AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public java.util.List<android.app.AppOpsManager.AttributedOpEntry> getBackgroundDiscreteAccesses(int i) {
            return android.app.AppOpsManager.listForFlagsInStates(this.mDiscreteAccesses, android.app.AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public long getAccessCount(int i, int i2, int i3) {
            return android.app.AppOpsManager.sumForFlagsInStates(this.mAccessCount, i, i2, i3);
        }

        public java.util.List<android.app.AppOpsManager.AttributedOpEntry> getDiscreteAccesses(int i, int i2, int i3) {
            return android.app.AppOpsManager.listForFlagsInStates(this.mDiscreteAccesses, i, i2, i3);
        }

        public long getForegroundRejectCount(int i) {
            return android.app.AppOpsManager.sumForFlagsInStates(this.mRejectCount, 100, android.app.AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getBackgroundRejectCount(int i) {
            return android.app.AppOpsManager.sumForFlagsInStates(this.mRejectCount, android.app.AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public long getRejectCount(int i, int i2, int i3) {
            return android.app.AppOpsManager.sumForFlagsInStates(this.mRejectCount, i, i2, i3);
        }

        public long getForegroundAccessDuration(int i) {
            return android.app.AppOpsManager.sumForFlagsInStates(this.mAccessDuration, 100, android.app.AppOpsManager.resolveFirstUnrestrictedUidState(this.mOp), i);
        }

        public long getBackgroundAccessDuration(int i) {
            return android.app.AppOpsManager.sumForFlagsInStates(this.mAccessDuration, android.app.AppOpsManager.resolveLastRestrictedUidState(this.mOp), 700, i);
        }

        public long getAccessDuration(int i, int i2, int i3) {
            return android.app.AppOpsManager.sumForFlagsInStates(this.mAccessDuration, i, i2, i3);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mOp);
            android.app.AppOpsManager.writeLongSparseLongArrayToParcel(this.mAccessCount, parcel);
            android.app.AppOpsManager.writeLongSparseLongArrayToParcel(this.mRejectCount, parcel);
            android.app.AppOpsManager.writeLongSparseLongArrayToParcel(this.mAccessDuration, parcel);
            android.app.AppOpsManager.writeDiscreteAccessArrayToParcel(this.mDiscreteAccesses, parcel, i);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.app.AppOpsManager.HistoricalOp historicalOp = (android.app.AppOpsManager.HistoricalOp) obj;
            if (this.mOp != historicalOp.mOp || !android.app.AppOpsManager.equalsLongSparseLongArray(this.mAccessCount, historicalOp.mAccessCount) || !android.app.AppOpsManager.equalsLongSparseLongArray(this.mRejectCount, historicalOp.mRejectCount) || !android.app.AppOpsManager.equalsLongSparseLongArray(this.mAccessDuration, historicalOp.mAccessDuration)) {
                return false;
            }
            if (this.mDiscreteAccesses != null) {
                return this.mDiscreteAccesses.equals(historicalOp.mDiscreteAccesses);
            }
            if (historicalOp.mDiscreteAccesses == null) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (((((((this.mOp * 31) + java.util.Objects.hashCode(this.mAccessCount)) * 31) + java.util.Objects.hashCode(this.mRejectCount)) * 31) + java.util.Objects.hashCode(this.mAccessDuration)) * 31) + java.util.Objects.hashCode(this.mDiscreteAccesses);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void accept(android.app.AppOpsManager.HistoricalOpsVisitor historicalOpsVisitor) {
            historicalOpsVisitor.visitHistoricalOp(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.util.LongSparseLongArray getOrCreateAccessCount() {
            if (this.mAccessCount == null) {
                this.mAccessCount = new android.util.LongSparseLongArray();
            }
            return this.mAccessCount;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.util.LongSparseLongArray getOrCreateRejectCount() {
            if (this.mRejectCount == null) {
                this.mRejectCount = new android.util.LongSparseLongArray();
            }
            return this.mRejectCount;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.util.LongSparseLongArray getOrCreateAccessDuration() {
            if (this.mAccessDuration == null) {
                this.mAccessDuration = new android.util.LongSparseLongArray();
            }
            return this.mAccessDuration;
        }

        private java.util.List<android.app.AppOpsManager.AttributedOpEntry> getOrCreateDiscreteAccesses() {
            if (this.mDiscreteAccesses == null) {
                this.mDiscreteAccesses = new java.util.ArrayList();
            }
            return this.mDiscreteAccesses;
        }

        private static void scale(android.util.LongSparseLongArray longSparseLongArray, double d) {
            if (longSparseLongArray != null) {
                int size = longSparseLongArray.size();
                for (int i = 0; i < size; i++) {
                    longSparseLongArray.put(longSparseLongArray.keyAt(i), (long) android.app.AppOpsManager.HistoricalOps.round(longSparseLongArray.valueAt(i) * d));
                }
            }
        }

        private static void merge(java.util.function.Supplier<android.util.LongSparseLongArray> supplier, android.util.LongSparseLongArray longSparseLongArray) {
            if (longSparseLongArray != null) {
                int size = longSparseLongArray.size();
                for (int i = 0; i < size; i++) {
                    android.util.LongSparseLongArray longSparseLongArray2 = supplier.get();
                    long keyAt = longSparseLongArray.keyAt(i);
                    longSparseLongArray2.put(keyAt, longSparseLongArray2.get(keyAt) + longSparseLongArray.valueAt(i));
                }
            }
        }

        public android.util.LongSparseArray<java.lang.Object> collectKeys() {
            return android.app.AppOpsManager.collectKeys(this.mAccessDuration, android.app.AppOpsManager.collectKeys(this.mRejectCount, android.app.AppOpsManager.collectKeys(this.mAccessCount, null)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long sumForFlagsInStates(android.util.LongSparseLongArray longSparseLongArray, int i, int i2, int i3) {
        long j = 0;
        if (longSparseLongArray == null) {
            return 0L;
        }
        while (i3 != 0) {
            int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i3);
            i3 &= ~numberOfTrailingZeros;
            for (int i4 : UID_STATES) {
                if (i4 >= i && i4 <= i2) {
                    j += longSparseLongArray.get(makeKey(i4, numberOfTrailingZeros));
                }
            }
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.List<android.app.AppOpsManager.AttributedOpEntry> listForFlagsInStates(java.util.List<android.app.AppOpsManager.AttributedOpEntry> list, int i, int i2, int i3) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (list == null) {
            return arrayList;
        }
        int size = list.size();
        for (int i4 = 0; i4 < size; i4++) {
            android.app.AppOpsManager.AttributedOpEntry attributedOpEntry = list.get(i4);
            if (attributedOpEntry.getLastAccessTime(i, i2, i3) != -1) {
                arrayList.add(attributedOpEntry);
            }
        }
        return deduplicateDiscreteEvents(arrayList);
    }

    public interface OnOpChangedListener {
        void onOpChanged(java.lang.String str, java.lang.String str2);

        default void onOpChanged(java.lang.String str, java.lang.String str2, int i) {
            onOpChanged(str, str2);
        }

        default void onOpChanged(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) {
            if (java.util.Objects.equals(str3, android.companion.virtual.VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT)) {
                onOpChanged(str, str2, i);
            }
        }
    }

    public interface OnOpActiveChangedListener {
        void onOpActiveChanged(java.lang.String str, int i, java.lang.String str2, boolean z);

        default void onOpActiveChanged(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, boolean z, int i2, int i3) {
            onOpActiveChanged(str, i, str2, z);
        }

        default void onOpActiveChanged(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2, boolean z, int i3, int i4) {
            if (i2 == 0) {
                onOpActiveChanged(str, i, str2, str3, z, i3, i4);
            }
        }
    }

    @android.annotation.SystemApi
    public interface OnOpNotedListener {
        void onOpNoted(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2, int i3);

        default void onOpNoted(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2, int i3, int i4) {
            if (i2 == 0) {
                onOpNoted(str, i, str2, str3, i3, i4);
            }
        }
    }

    public interface OnOpNotedInternalListener extends android.app.AppOpsManager.OnOpNotedListener {
        void onOpNoted(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4);

        @Override // android.app.AppOpsManager.OnOpNotedListener
        default void onOpNoted(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2, int i3) {
            onOpNoted(android.app.AppOpsManager.strOpToOp(str), i, str2, str3, i2, i3);
        }
    }

    public static class OnOpChangedInternalListener implements android.app.AppOpsManager.OnOpChangedListener {
        @Override // android.app.AppOpsManager.OnOpChangedListener
        public void onOpChanged(java.lang.String str, java.lang.String str2) {
        }

        public void onOpChanged(int i, java.lang.String str) {
        }

        public void onOpChanged(int i, java.lang.String str, java.lang.String str2) {
            onOpChanged(i, str);
        }
    }

    public interface OnOpActiveChangedInternalListener extends android.app.AppOpsManager.OnOpActiveChangedListener {
        @Override // android.app.AppOpsManager.OnOpActiveChangedListener
        default void onOpActiveChanged(java.lang.String str, int i, java.lang.String str2, boolean z) {
        }

        default void onOpActiveChanged(int i, int i2, java.lang.String str, boolean z) {
        }

        default void onOpActiveChanged(int i, int i2, java.lang.String str, int i3, boolean z) {
        }
    }

    public interface OnOpStartedListener {
        public static final int START_TYPE_FAILED = 0;
        public static final int START_TYPE_RESUMED = 2;
        public static final int START_TYPE_STARTED = 1;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface StartedType {
        }

        void onOpStarted(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4);

        default void onOpStarted(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5, int i6, int i7) {
            if (i5 != 2) {
                onOpStarted(i, i2, str, str2, i3, i4);
            }
        }

        default void onOpStarted(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (i3 == 0) {
                onOpStarted(i, i2, str, str2, i4, i5, i6, i7, i8);
            }
        }
    }

    AppOpsManager(android.content.Context context, com.android.internal.app.IAppOpsService iAppOpsService) {
        this.mContext = context;
        this.mService = iAppOpsService;
        if (this.mContext != null) {
            android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
            try {
                if (android.os.Build.IS_ENG && packageManager != null && packageManager.checkPermission(android.Manifest.permission.READ_DEVICE_CONFIG, this.mContext.getPackageName()) == 0) {
                    android.provider.DeviceConfig.addOnPropertiesChangedListener("privacy", this.mContext.getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda6
                        public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                            android.app.AppOpsManager.lambda$new$0(properties);
                        }
                    });
                    return;
                }
            } catch (java.lang.Exception e) {
            }
        }
        sFullLog = false;
    }

    static /* synthetic */ void lambda$new$0(android.provider.DeviceConfig.Properties properties) {
        if (properties.getKeyset().contains(FULL_LOG)) {
            sFullLog = java.lang.Boolean.valueOf(properties.getBoolean(FULL_LOG, false));
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.app.AppOpsManager.PackageOps> getPackagesForOps(java.lang.String[] strArr) {
        int[] iArr;
        if (strArr != null) {
            int length = strArr.length;
            iArr = new int[length];
            for (int i = 0; i < length; i++) {
                iArr[i] = sOpStrToOp.get(strArr[i]).intValue();
            }
        } else {
            iArr = null;
        }
        java.util.List<android.app.AppOpsManager.PackageOps> packagesForOps = getPackagesForOps(iArr);
        return packagesForOps != null ? packagesForOps : java.util.Collections.emptyList();
    }

    @android.annotation.SystemApi
    public java.util.List<android.app.AppOpsManager.PackageOps> getPackagesForOps(java.lang.String[] strArr, java.lang.String str) {
        int[] iArr;
        if (strArr != null) {
            int length = strArr.length;
            iArr = new int[length];
            for (int i = 0; i < length; i++) {
                iArr[i] = sOpStrToOp.get(strArr[i]).intValue();
            }
        } else {
            iArr = null;
        }
        try {
            java.util.List<android.app.AppOpsManager.PackageOps> packagesForOpsForDevice = this.mService.getPackagesForOpsForDevice(iArr, str);
            return packagesForOpsForDevice != null ? packagesForOpsForDevice : java.util.Collections.emptyList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.app.AppOpsManager.PackageOps> getPackagesForOps(int[] iArr) {
        try {
            return this.mService.getPackagesForOpsForDevice(iArr, android.companion.virtual.VirtualDeviceManager.PERSISTENT_DEVICE_ID_DEFAULT);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public java.util.List<android.app.AppOpsManager.PackageOps> getOpsForPackage(int i, java.lang.String str, int[] iArr) {
        try {
            return this.mService.getOpsForPackage(i, str, iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.app.AppOpsManager.PackageOps> getOpsForPackage(int i, java.lang.String str, java.lang.String... strArr) {
        int[] iArr;
        if (strArr == null) {
            iArr = null;
        } else {
            iArr = new int[strArr.length];
            for (int i2 = 0; i2 < strArr.length; i2++) {
                iArr[i2] = strOpToOp(strArr[i2]);
            }
        }
        try {
            java.util.List<android.app.AppOpsManager.PackageOps> opsForPackage = this.mService.getOpsForPackage(i, str, iArr);
            if (opsForPackage == null) {
                return java.util.Collections.emptyList();
            }
            return opsForPackage;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void getHistoricalOps(android.app.AppOpsManager.HistoricalOpsRequest historicalOpsRequest, final java.util.concurrent.Executor executor, final java.util.function.Consumer<android.app.AppOpsManager.HistoricalOps> consumer) {
        java.util.Objects.requireNonNull(executor, "executor cannot be null");
        java.util.Objects.requireNonNull(consumer, "callback cannot be null");
        try {
            this.mService.getHistoricalOps(historicalOpsRequest.mUid, historicalOpsRequest.mPackageName, historicalOpsRequest.mAttributionTag, historicalOpsRequest.mOpNames, historicalOpsRequest.mHistoryFlags, historicalOpsRequest.mFilter, historicalOpsRequest.mBeginTimeMillis, historicalOpsRequest.mEndTimeMillis, historicalOpsRequest.mFlags, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda2
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(android.os.Bundle bundle) {
                    android.app.AppOpsManager.lambda$getHistoricalOps$2(executor, consumer, bundle);
                }
            }));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$getHistoricalOps$2(java.util.concurrent.Executor executor, final java.util.function.Consumer consumer, android.os.Bundle bundle) {
        final android.app.AppOpsManager.HistoricalOps historicalOps = (android.app.AppOpsManager.HistoricalOps) bundle.getParcelable(KEY_HISTORICAL_OPS, android.app.AppOpsManager.HistoricalOps.class);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            executor.execute(new java.lang.Runnable() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(historicalOps);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void getHistoricalOpsFromDiskRaw(android.app.AppOpsManager.HistoricalOpsRequest historicalOpsRequest, final java.util.concurrent.Executor executor, final java.util.function.Consumer<android.app.AppOpsManager.HistoricalOps> consumer) {
        java.util.Objects.requireNonNull(executor, "executor cannot be null");
        java.util.Objects.requireNonNull(consumer, "callback cannot be null");
        try {
            this.mService.getHistoricalOpsFromDiskRaw(historicalOpsRequest.mUid, historicalOpsRequest.mPackageName, historicalOpsRequest.mAttributionTag, historicalOpsRequest.mOpNames, historicalOpsRequest.mHistoryFlags, historicalOpsRequest.mFilter, historicalOpsRequest.mBeginTimeMillis, historicalOpsRequest.mEndTimeMillis, historicalOpsRequest.mFlags, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda3
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(android.os.Bundle bundle) {
                    android.app.AppOpsManager.lambda$getHistoricalOpsFromDiskRaw$4(executor, consumer, bundle);
                }
            }));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ void lambda$getHistoricalOpsFromDiskRaw$4(java.util.concurrent.Executor executor, final java.util.function.Consumer consumer, android.os.Bundle bundle) {
        final android.app.AppOpsManager.HistoricalOps historicalOps = (android.app.AppOpsManager.HistoricalOps) bundle.getParcelable(KEY_HISTORICAL_OPS, android.app.AppOpsManager.HistoricalOps.class);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            executor.execute(new java.lang.Runnable() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(historicalOps);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void reloadNonHistoricalState() {
        try {
            this.mService.reloadNonHistoricalState();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUidMode(int i, int i2, int i3) {
        if (i == 111) {
            try {
                android.util.Log.i(DEBUG_LOGGING_TAG, "setUidMode called for OP_BLUETOOTH_CONNECT with mode: " + i3 + " for uid: " + i2 + " calling uid: " + android.os.Binder.getCallingUid() + " trace: " + java.util.Arrays.toString(java.lang.Thread.currentThread().getStackTrace()));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        this.mService.setUidMode(i, i2, i3);
    }

    @android.annotation.SystemApi
    public void setUidMode(java.lang.String str, int i, int i2) {
        try {
            if (str.equals(OPSTR_BLUETOOTH_CONNECT)) {
                android.util.Log.i(DEBUG_LOGGING_TAG, "setUidMode called for OPSTR_BLUETOOTH_CONNECT with mode: " + i2 + " for uid: " + i + " calling uid: " + android.os.Binder.getCallingUid() + " trace: " + java.util.Arrays.toString(java.lang.Thread.currentThread().getStackTrace()));
            }
            this.mService.setUidMode(strOpToOp(str), i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setUserRestriction(int i, boolean z, android.os.IBinder iBinder) {
        setUserRestriction(i, z, iBinder, null);
    }

    public void setUserRestriction(int i, boolean z, android.os.IBinder iBinder, android.os.PackageTagsList packageTagsList) {
        setUserRestrictionForUser(i, z, iBinder, packageTagsList, this.mContext.getUserId());
    }

    public void setUserRestrictionForUser(int i, boolean z, android.os.IBinder iBinder, android.os.PackageTagsList packageTagsList, int i2) {
        try {
            this.mService.setUserRestriction(i, z, iBinder, i2, packageTagsList);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setMode(int i, int i2, java.lang.String str, int i3) {
        if (i == 111) {
            try {
                android.util.Log.i(DEBUG_LOGGING_TAG, "setMode called for OPSTR_BLUETOOTH_CONNECT with mode: " + i3 + " for uid: " + i2 + " calling uid: " + android.os.Binder.getCallingUid() + " trace: " + java.util.Arrays.toString(java.lang.Thread.currentThread().getStackTrace()));
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        this.mService.setMode(i, i2, str, i3);
    }

    @android.annotation.SystemApi
    public void setMode(java.lang.String str, int i, java.lang.String str2, int i2) {
        try {
            if (str.equals(OPSTR_BLUETOOTH_CONNECT)) {
                android.util.Log.i(DEBUG_LOGGING_TAG, "setMode called for OPSTR_BLUETOOTH_CONNECT with mode: " + i2 + " for uid: " + i + " calling uid: " + android.os.Binder.getCallingUid() + " trace: " + java.util.Arrays.toString(java.lang.Thread.currentThread().getStackTrace()));
            }
            this.mService.setMode(strOpToOp(str), i, str2, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setRestriction(int i, int i2, int i3, java.lang.String[] strArr) {
        try {
            this.mService.setAudioRestriction(i, i2, android.os.Binder.getCallingUid(), i3, strArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetAllModes() {
        try {
            this.mService.resetAllModes(this.mContext.getUserId(), null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static java.lang.String permissionToOp(java.lang.String str) {
        java.lang.Integer num = sPermToOp.get(str);
        if (num != null) {
            return sAppOpInfos[num.intValue()].name;
        }
        if (android.health.connect.HealthConnectManager.isHealthPermission(android.app.ActivityThread.currentApplication(), str)) {
            return sAppOpInfos[126].name;
        }
        return null;
    }

    public static java.lang.String resolvePackageName(int i, java.lang.String str) {
        if (i == 0) {
            return "root";
        }
        if (i == 2000) {
            return "com.android.shell";
        }
        if (i == 1013) {
            return "media";
        }
        if (i == 1041) {
            return "audioserver";
        }
        if (i == 1047) {
            return "cameraserver";
        }
        if (i == 1000 && str == null) {
            return "android";
        }
        return str;
    }

    public void startWatchingMode(java.lang.String str, java.lang.String str2, android.app.AppOpsManager.OnOpChangedListener onOpChangedListener) {
        startWatchingMode(strOpToOp(str), str2, onOpChangedListener);
    }

    public void startWatchingMode(java.lang.String str, java.lang.String str2, int i, android.app.AppOpsManager.OnOpChangedListener onOpChangedListener) {
        startWatchingMode(strOpToOp(str), str2, i, onOpChangedListener);
    }

    public void startWatchingMode(int i, java.lang.String str, android.app.AppOpsManager.OnOpChangedListener onOpChangedListener) {
        startWatchingMode(i, str, 0, onOpChangedListener);
    }

    public void startWatchingMode(int i, java.lang.String str, int i2, final android.app.AppOpsManager.OnOpChangedListener onOpChangedListener) {
        synchronized (this.mModeWatchers) {
            com.android.internal.app.IAppOpsCallback iAppOpsCallback = this.mModeWatchers.get(onOpChangedListener);
            if (iAppOpsCallback == null) {
                iAppOpsCallback = new com.android.internal.app.IAppOpsCallback.Stub() { // from class: android.app.AppOpsManager.2
                    @Override // com.android.internal.app.IAppOpsCallback
                    public void opChanged(int i3, int i4, java.lang.String str2, java.lang.String str3) {
                        if (android.permission.flags.Flags.deviceAwarePermissionApisEnabled()) {
                            if (onOpChangedListener instanceof android.app.AppOpsManager.OnOpChangedInternalListener) {
                                ((android.app.AppOpsManager.OnOpChangedInternalListener) onOpChangedListener).onOpChanged(i3, str2, str3);
                            }
                            if (android.app.AppOpsManager.sAppOpInfos[i3].name != null) {
                                onOpChangedListener.onOpChanged(android.app.AppOpsManager.sAppOpInfos[i3].name, str2, android.os.UserHandle.getUserId(i4), str3);
                                return;
                            }
                            return;
                        }
                        if (onOpChangedListener instanceof android.app.AppOpsManager.OnOpChangedInternalListener) {
                            ((android.app.AppOpsManager.OnOpChangedInternalListener) onOpChangedListener).onOpChanged(i3, str2);
                        }
                        if (android.app.AppOpsManager.sAppOpInfos[i3].name != null) {
                            onOpChangedListener.onOpChanged(android.app.AppOpsManager.sAppOpInfos[i3].name, str2, android.os.UserHandle.getUserId(i4));
                        }
                    }
                };
                this.mModeWatchers.put(onOpChangedListener, iAppOpsCallback);
            }
            if (!android.compat.Compatibility.isChangeEnabled(CALL_BACK_ON_CHANGED_LISTENER_WITH_SWITCHED_OP_CHANGE)) {
                i2 |= 2;
            }
            try {
                this.mService.startWatchingModeWithFlags(i, str, i2, iAppOpsCallback);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void stopWatchingMode(android.app.AppOpsManager.OnOpChangedListener onOpChangedListener) {
        synchronized (this.mModeWatchers) {
            com.android.internal.app.IAppOpsCallback remove = this.mModeWatchers.remove(onOpChangedListener);
            if (remove != null) {
                try {
                    this.mService.stopWatchingMode(remove);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @java.lang.Deprecated
    public void startWatchingActive(int[] iArr, android.app.AppOpsManager.OnOpActiveChangedListener onOpActiveChangedListener) {
        java.lang.String[] strArr = new java.lang.String[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            strArr[i] = opToPublicName(iArr[i]);
        }
        startWatchingActive(strArr, this.mContext.getMainExecutor(), onOpActiveChangedListener);
    }

    public void startWatchingActive(java.lang.String[] strArr, java.util.concurrent.Executor executor, android.app.AppOpsManager.OnOpActiveChangedListener onOpActiveChangedListener) {
        java.util.Objects.requireNonNull(strArr);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(onOpActiveChangedListener);
        synchronized (this.mActiveWatchers) {
            if (this.mActiveWatchers.get(onOpActiveChangedListener) != null) {
                return;
            }
            android.app.AppOpsManager.AnonymousClass3 anonymousClass3 = new android.app.AppOpsManager.AnonymousClass3(executor, onOpActiveChangedListener);
            this.mActiveWatchers.put(onOpActiveChangedListener, anonymousClass3);
            int[] iArr = new int[strArr.length];
            for (int i = 0; i < strArr.length; i++) {
                iArr[i] = strOpToOp(strArr[i]);
            }
            try {
                this.mService.startWatchingActive(iArr, anonymousClass3);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* renamed from: android.app.AppOpsManager$3, reason: invalid class name */
    class AnonymousClass3 extends com.android.internal.app.IAppOpsActiveCallback.Stub {
        final /* synthetic */ android.app.AppOpsManager.OnOpActiveChangedListener val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass3(java.util.concurrent.Executor executor, android.app.AppOpsManager.OnOpActiveChangedListener onOpActiveChangedListener) {
            this.val$executor = executor;
            this.val$callback = onOpActiveChangedListener;
        }

        @Override // com.android.internal.app.IAppOpsActiveCallback
        public void opActiveChanged(final int i, final int i2, final java.lang.String str, final java.lang.String str2, final int i3, final boolean z, final int i4, final int i5) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.app.AppOpsManager.OnOpActiveChangedListener onOpActiveChangedListener = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.app.AppOpsManager$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.app.AppOpsManager.AnonymousClass3.lambda$opActiveChanged$0(android.app.AppOpsManager.OnOpActiveChangedListener.this, i, i2, str, i3, z, str2, i4, i5);
                }
            });
        }

        static /* synthetic */ void lambda$opActiveChanged$0(android.app.AppOpsManager.OnOpActiveChangedListener onOpActiveChangedListener, int i, int i2, java.lang.String str, int i3, boolean z, java.lang.String str2, int i4, int i5) {
            if (android.permission.flags.Flags.deviceAwarePermissionApisEnabled()) {
                if (onOpActiveChangedListener instanceof android.app.AppOpsManager.OnOpActiveChangedInternalListener) {
                    ((android.app.AppOpsManager.OnOpActiveChangedInternalListener) onOpActiveChangedListener).onOpActiveChanged(i, i2, str, i3, z);
                }
                if (android.app.AppOpsManager.sAppOpInfos[i].name != null) {
                    onOpActiveChangedListener.onOpActiveChanged(android.app.AppOpsManager.sAppOpInfos[i].name, i2, str, str2, i3, z, i4, i5);
                    return;
                }
                return;
            }
            if (onOpActiveChangedListener instanceof android.app.AppOpsManager.OnOpActiveChangedInternalListener) {
                ((android.app.AppOpsManager.OnOpActiveChangedInternalListener) onOpActiveChangedListener).onOpActiveChanged(i, i2, str, z);
            }
            if (android.app.AppOpsManager.sAppOpInfos[i].name != null) {
                onOpActiveChangedListener.onOpActiveChanged(android.app.AppOpsManager.sAppOpInfos[i].name, i2, str, str2, z, i4, i5);
            }
        }
    }

    public void stopWatchingActive(android.app.AppOpsManager.OnOpActiveChangedListener onOpActiveChangedListener) {
        synchronized (this.mActiveWatchers) {
            com.android.internal.app.IAppOpsActiveCallback remove = this.mActiveWatchers.remove(onOpActiveChangedListener);
            if (remove != null) {
                try {
                    this.mService.stopWatchingActive(remove);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void startWatchingStarted(int[] iArr, final android.app.AppOpsManager.OnOpStartedListener onOpStartedListener) {
        synchronized (this.mStartedWatchers) {
            if (this.mStartedWatchers.containsKey(onOpStartedListener)) {
                return;
            }
            com.android.internal.app.IAppOpsStartedCallback.Stub stub = new com.android.internal.app.IAppOpsStartedCallback.Stub() { // from class: android.app.AppOpsManager.4
                @Override // com.android.internal.app.IAppOpsStartedCallback
                public void opStarted(int i, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    onOpStartedListener.onOpStarted(i, i2, str, str2, i3, i4, i5, i6, i7, i8);
                }
            };
            this.mStartedWatchers.put(onOpStartedListener, stub);
            try {
                this.mService.startWatchingStarted(iArr, stub);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void stopWatchingStarted(android.app.AppOpsManager.OnOpStartedListener onOpStartedListener) {
        synchronized (this.mStartedWatchers) {
            com.android.internal.app.IAppOpsStartedCallback remove = this.mStartedWatchers.remove(onOpStartedListener);
            if (remove != null) {
                try {
                    this.mService.stopWatchingStarted(remove);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @android.annotation.SystemApi
    public void startWatchingNoted(java.lang.String[] strArr, android.app.AppOpsManager.OnOpNotedListener onOpNotedListener) {
        int[] iArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            iArr[i] = strOpToOp(strArr[i]);
        }
        startWatchingNoted(iArr, onOpNotedListener);
    }

    @android.annotation.SystemApi
    public void startWatchingNoted(java.lang.String[] strArr, java.util.concurrent.Executor executor, android.app.AppOpsManager.OnOpNotedListener onOpNotedListener) {
        int[] iArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            iArr[i] = strOpToOp(strArr[i]);
        }
        startWatchingNoted(iArr, executor, onOpNotedListener);
    }

    public void startWatchingNoted(int[] iArr, android.app.AppOpsManager.OnOpNotedListener onOpNotedListener) {
        startWatchingNoted(iArr, this.mContext.getMainExecutor(), onOpNotedListener);
    }

    public void startWatchingNoted(int[] iArr, java.util.concurrent.Executor executor, android.app.AppOpsManager.OnOpNotedListener onOpNotedListener) {
        synchronized (this.mNotedWatchers) {
            if (this.mNotedWatchers.get(onOpNotedListener) != null) {
                return;
            }
            android.app.AppOpsManager.AnonymousClass5 anonymousClass5 = new android.app.AppOpsManager.AnonymousClass5(executor, onOpNotedListener);
            this.mNotedWatchers.put(onOpNotedListener, anonymousClass5);
            try {
                this.mService.startWatchingNoted(iArr, anonymousClass5);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* renamed from: android.app.AppOpsManager$5, reason: invalid class name */
    class AnonymousClass5 extends com.android.internal.app.IAppOpsNotedCallback.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.app.AppOpsManager.OnOpNotedListener val$listener;

        AnonymousClass5(java.util.concurrent.Executor executor, android.app.AppOpsManager.OnOpNotedListener onOpNotedListener) {
            this.val$executor = executor;
            this.val$listener = onOpNotedListener;
        }

        @Override // com.android.internal.app.IAppOpsNotedCallback
        public void opNoted(final int i, final int i2, final java.lang.String str, final java.lang.String str2, final int i3, final int i4, final int i5) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.app.AppOpsManager.OnOpNotedListener onOpNotedListener = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.app.AppOpsManager$5$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.app.AppOpsManager.AnonymousClass5.lambda$opNoted$0(i, onOpNotedListener, i2, str, str2, i3, i4, i5);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        static /* synthetic */ void lambda$opNoted$0(int i, android.app.AppOpsManager.OnOpNotedListener onOpNotedListener, int i2, java.lang.String str, java.lang.String str2, int i3, int i4, int i5) {
            if (android.app.AppOpsManager.sAppOpInfos[i].name != null) {
                if (android.permission.flags.Flags.deviceAwarePermissionApisEnabled()) {
                    onOpNotedListener.onOpNoted(android.app.AppOpsManager.sAppOpInfos[i].name, i2, str, str2, i3, i4, i5);
                } else {
                    onOpNotedListener.onOpNoted(android.app.AppOpsManager.sAppOpInfos[i].name, i2, str, str2, i4, i5);
                }
            }
        }
    }

    @android.annotation.SystemApi
    public void stopWatchingNoted(android.app.AppOpsManager.OnOpNotedListener onOpNotedListener) {
        synchronized (this.mNotedWatchers) {
            com.android.internal.app.IAppOpsNotedCallback remove = this.mNotedWatchers.remove(onOpNotedListener);
            if (remove != null) {
                try {
                    this.mService.stopWatchingNoted(remove);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    private java.lang.String buildSecurityExceptionMsg(int i, int i2, java.lang.String str) {
        return str + " from uid " + i2 + " not allowed to perform " + sAppOpInfos[i].simpleName;
    }

    public static int strOpToOp(java.lang.String str) {
        java.lang.Integer num = sOpStrToOp.get(str);
        if (num == null) {
            throw new java.lang.IllegalArgumentException("Unknown operation string: " + str);
        }
        return num.intValue();
    }

    public int unsafeCheckOp(java.lang.String str, int i, java.lang.String str2) {
        return checkOp(strOpToOp(str), i, str2);
    }

    @java.lang.Deprecated
    public int checkOp(java.lang.String str, int i, java.lang.String str2) {
        return checkOp(strOpToOp(str), i, str2);
    }

    public int unsafeCheckOpNoThrow(java.lang.String str, int i, java.lang.String str2) {
        return checkOpNoThrow(strOpToOp(str), i, str2);
    }

    @java.lang.Deprecated
    public int checkOpNoThrow(java.lang.String str, int i, java.lang.String str2) {
        return checkOpNoThrow(strOpToOp(str), i, str2);
    }

    public int unsafeCheckOpRaw(java.lang.String str, int i, java.lang.String str2) {
        return unsafeCheckOpRawNoThrow(str, i, str2);
    }

    public int unsafeCheckOpRawNoThrow(java.lang.String str, int i, java.lang.String str2) {
        return unsafeCheckOpRawNoThrow(strOpToOp(str), i, str2);
    }

    public int unsafeCheckOpRawNoThrow(int i, android.content.AttributionSource attributionSource) {
        return unsafeCheckOpRawNoThrow(i, attributionSource.getUid(), attributionSource.getPackageName(), attributionSource.getDeviceId());
    }

    public int unsafeCheckOpRawNoThrow(int i, int i2, java.lang.String str) {
        return unsafeCheckOpRawNoThrow(i, i2, str, 0);
    }

    private int unsafeCheckOpRawNoThrow(int i, int i2, java.lang.String str, int i3) {
        try {
            if (i3 == 0) {
                return this.mService.checkOperationRaw(i, i2, str, null);
            }
            return this.mService.checkOperationRawForDevice(i, i2, str, null, 0);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public int noteOp(java.lang.String str, int i, java.lang.String str2) {
        return noteOp(str, i, str2, (java.lang.String) null, (java.lang.String) null);
    }

    @java.lang.Deprecated
    public int noteOp(int i) {
        return noteOp(i, android.os.Process.myUid(), this.mContext.getOpPackageName(), (java.lang.String) null, (java.lang.String) null);
    }

    @java.lang.Deprecated
    public int noteOp(int i, int i2, java.lang.String str) {
        return noteOp(i, i2, str, (java.lang.String) null, (java.lang.String) null);
    }

    public int noteOp(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        return noteOp(strOpToOp(str), i, str2, str3, str4);
    }

    public int noteOp(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        int noteOpNoThrow = noteOpNoThrow(i, i2, str, str2, str3);
        if (noteOpNoThrow == 2) {
            throw new java.lang.SecurityException(buildSecurityExceptionMsg(i, i2, str));
        }
        return noteOpNoThrow;
    }

    @java.lang.Deprecated
    public int noteOpNoThrow(java.lang.String str, int i, java.lang.String str2) {
        return noteOpNoThrow(str, i, str2, (java.lang.String) null, (java.lang.String) null);
    }

    @java.lang.Deprecated
    public int noteOpNoThrow(int i, int i2, java.lang.String str) {
        return noteOpNoThrow(i, i2, str, (java.lang.String) null, (java.lang.String) null);
    }

    public int noteOpNoThrow(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        return noteOpNoThrow(strOpToOp(str), i, str2, str3, str4);
    }

    public int noteOpNoThrow(int i, android.content.AttributionSource attributionSource, java.lang.String str) {
        return noteOpNoThrow(i, attributionSource.getUid(), attributionSource.getPackageName(), attributionSource.getAttributionTag(), attributionSource.getDeviceId(), str);
    }

    public int noteOpNoThrow(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String str3) {
        return noteOpNoThrow(i, i2, str, str2, 0, str3);
    }

    private int noteOpNoThrow(int i, int i2, java.lang.String str, java.lang.String str2, int i3, java.lang.String str3) {
        java.lang.String str4;
        boolean z;
        android.app.SyncNotedAppOp noteOperationForDevice;
        try {
            collectNoteOpCallsForValidation(i);
            int notedOpCollectionMode = getNotedOpCollectionMode(i2, str, i);
            boolean z2 = android.os.Process.myUid() == 1000;
            if (notedOpCollectionMode == 3 && str3 == null) {
                str4 = getFormattedStackTrace();
                z = true;
            } else {
                str4 = str3;
                z = z2;
            }
            if (i3 == 0) {
                noteOperationForDevice = this.mService.noteOperation(i, i2, str, str2, notedOpCollectionMode == 3, str4, z);
            } else {
                noteOperationForDevice = this.mService.noteOperationForDevice(i, i2, str, str2, i3, notedOpCollectionMode == 3, str4, z);
            }
            if (noteOperationForDevice.getOpMode() == 0) {
                if (notedOpCollectionMode == 1) {
                    collectNotedOpForSelf(noteOperationForDevice);
                } else if (notedOpCollectionMode == 2) {
                    collectNotedOpSync(noteOperationForDevice);
                }
            }
            return noteOperationForDevice.getOpMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public int noteProxyOp(java.lang.String str, java.lang.String str2) {
        return noteProxyOp(str, str2, android.os.Binder.getCallingUid(), (java.lang.String) null, (java.lang.String) null);
    }

    @java.lang.Deprecated
    public int noteProxyOp(int i, java.lang.String str) {
        return noteProxyOp(i, str, android.os.Binder.getCallingUid(), (java.lang.String) null, (java.lang.String) null);
    }

    public int noteProxyOp(int i, java.lang.String str, int i2, java.lang.String str2, java.lang.String str3) {
        return noteProxyOp(i, new android.content.AttributionSource(this.mContext.getAttributionSource(), new android.content.AttributionSource(i2, -1, str, str2, this.mContext.getAttributionSource().getToken())), str3, false);
    }

    public int noteProxyOp(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, java.lang.String str4) {
        return noteProxyOp(strOpToOp(str), str2, i, str3, str4);
    }

    public int noteProxyOp(int i, android.content.AttributionSource attributionSource, java.lang.String str, boolean z) {
        int noteProxyOpNoThrow = noteProxyOpNoThrow(i, attributionSource, str, z);
        if (noteProxyOpNoThrow == 2) {
            throw new java.lang.SecurityException("Proxy package " + attributionSource.getPackageName() + " from uid " + attributionSource.getUid() + " or calling package " + attributionSource.getNextPackageName() + " from uid " + attributionSource.getNextUid() + " not allowed to perform " + sAppOpInfos[i].simpleName);
        }
        return noteProxyOpNoThrow;
    }

    @java.lang.Deprecated
    public int noteProxyOpNoThrow(java.lang.String str, java.lang.String str2) {
        return noteProxyOpNoThrow(str, str2, android.os.Binder.getCallingUid(), null, null);
    }

    @java.lang.Deprecated
    public int noteProxyOpNoThrow(java.lang.String str, java.lang.String str2, int i) {
        return noteProxyOpNoThrow(str, str2, i, null, null);
    }

    public int noteProxyOpNoThrow(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, java.lang.String str4) {
        return noteProxyOpNoThrow(strOpToOp(str), new android.content.AttributionSource(this.mContext.getAttributionSource(), new android.content.AttributionSource(i, -1, str2, str3, this.mContext.getAttributionSource().getToken())), str4, false);
    }

    public int noteProxyOpNoThrow(int i, android.content.AttributionSource attributionSource, java.lang.String str, boolean z) {
        java.lang.String str2;
        boolean z2;
        int myUid = android.os.Process.myUid();
        try {
            collectNoteOpCallsForValidation(i);
            int notedOpCollectionMode = getNotedOpCollectionMode(attributionSource.getNextUid(), attributionSource.getNextAttributionTag(), i);
            boolean z3 = myUid == 1000;
            if (notedOpCollectionMode == 3 && str == null) {
                str2 = getFormattedStackTrace();
                z2 = true;
            } else {
                str2 = str;
                z2 = z3;
            }
            android.app.SyncNotedAppOp noteProxyOperationWithState = this.mService.noteProxyOperationWithState(i, attributionSource.asState(), notedOpCollectionMode == 3, str2, z2, z);
            if (noteProxyOperationWithState.getOpMode() == 0) {
                if (notedOpCollectionMode == 1) {
                    collectNotedOpForSelf(noteProxyOperationWithState);
                } else if (notedOpCollectionMode == 2 && (this.mContext.checkPermission(android.Manifest.permission.UPDATE_APP_OPS_STATS, -1, myUid) == 0 || android.os.Binder.getCallingUid() == attributionSource.getNextUid())) {
                    collectNotedOpSync(noteProxyOperationWithState);
                }
            }
            return noteProxyOperationWithState.getOpMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static java.lang.String getComponentPackageNameFromString(java.lang.String str) {
        android.content.ComponentName unflattenFromString = str != null ? android.content.ComponentName.unflattenFromString(str) : null;
        return unflattenFromString != null ? unflattenFromString.getPackageName() : "";
    }

    private static boolean isPackagePreInstalled(android.content.Context context, java.lang.String str, int i) {
        try {
            return (context.getPackageManager().getApplicationInfoAsUser(str, 0, i).flags & 1) != 0;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public int checkOp(int i, int i2, java.lang.String str) {
        try {
            int checkOperationForDevice = this.mService.checkOperationForDevice(i, i2, str, 0);
            if (checkOperationForDevice == 2) {
                throw new java.lang.SecurityException(buildSecurityExceptionMsg(i, i2, str));
            }
            return checkOperationForDevice;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int checkOpNoThrow(int i, android.content.AttributionSource attributionSource) {
        return checkOpNoThrow(i, attributionSource.getUid(), attributionSource.getPackageName(), attributionSource.getDeviceId());
    }

    public int checkOpNoThrow(int i, int i2, java.lang.String str) {
        return checkOpNoThrow(i, i2, str, 0);
    }

    private int checkOpNoThrow(int i, int i2, java.lang.String str, int i3) {
        int checkOperationForDevice;
        try {
            if (i3 == 0) {
                checkOperationForDevice = this.mService.checkOperation(i, i2, str);
            } else {
                checkOperationForDevice = this.mService.checkOperationForDevice(i, i2, str, i3);
            }
            if (checkOperationForDevice == 4) {
                return 0;
            }
            return checkOperationForDevice;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void checkPackage(int i, java.lang.String str) {
        try {
            if (this.mService.checkPackage(i, str) != 0) {
                throw new java.lang.SecurityException("Package " + str + " does not belong to " + i);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int checkAudioOp(int i, int i2, int i3, java.lang.String str) {
        try {
            int checkAudioOperation = this.mService.checkAudioOperation(i, i2, i3, str);
            if (checkAudioOperation == 2) {
                throw new java.lang.SecurityException(buildSecurityExceptionMsg(i, i3, str));
            }
            return checkAudioOperation;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int checkAudioOpNoThrow(int i, int i2, int i3, java.lang.String str) {
        try {
            return this.mService.checkAudioOperation(i, i2, i3, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public static android.os.IBinder getToken(com.android.internal.app.IAppOpsService iAppOpsService) {
        return getClientId();
    }

    public static android.os.IBinder getClientId() {
        android.os.IBinder iBinder;
        synchronized (android.app.AppOpsManager.class) {
            if (sClientId == null) {
                sClientId = new android.os.Binder();
            }
            iBinder = sClientId;
        }
        return iBinder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.internal.app.IAppOpsService getService() {
        com.android.internal.app.IAppOpsService iAppOpsService;
        synchronized (sLock) {
            if (sService == null) {
                sService = com.android.internal.app.IAppOpsService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.APP_OPS_SERVICE));
            }
            iAppOpsService = sService;
        }
        return iAppOpsService;
    }

    @java.lang.Deprecated
    public int startOp(java.lang.String str, int i, java.lang.String str2) {
        return startOp(str, i, str2, null, null);
    }

    @java.lang.Deprecated
    public int startOp(int i) {
        return startOp(i, android.os.Process.myUid(), this.mContext.getOpPackageName(), false, null, null);
    }

    @java.lang.Deprecated
    public int startOp(int i, int i2, java.lang.String str) {
        return startOp(i, i2, str, false, null, null);
    }

    @java.lang.Deprecated
    public int startOp(int i, int i2, java.lang.String str, boolean z) {
        return startOp(i, i2, str, z, null, null);
    }

    public int startOp(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        return startOp(strOpToOp(str), i, str2, false, str3, str4);
    }

    public int startOp(int i, int i2, java.lang.String str, boolean z, java.lang.String str2, java.lang.String str3) {
        int startOpNoThrow = startOpNoThrow(i, i2, str, z, str2, str3);
        if (startOpNoThrow == 2) {
            throw new java.lang.SecurityException(buildSecurityExceptionMsg(i, i2, str));
        }
        return startOpNoThrow;
    }

    @java.lang.Deprecated
    public int startOpNoThrow(java.lang.String str, int i, java.lang.String str2) {
        return startOpNoThrow(str, i, str2, null, null);
    }

    @java.lang.Deprecated
    public int startOpNoThrow(int i, int i2, java.lang.String str) {
        return startOpNoThrow(i, i2, str, false, null, null);
    }

    @java.lang.Deprecated
    public int startOpNoThrow(int i, int i2, java.lang.String str, boolean z) {
        return startOpNoThrow(i, i2, str, z, null, null);
    }

    public int startOpNoThrow(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        return startOpNoThrow(strOpToOp(str), i, str2, false, str3, str4);
    }

    public int startOpNoThrow(int i, int i2, java.lang.String str, boolean z, java.lang.String str2, java.lang.String str3) {
        return startOpNoThrow(this.mContext.getAttributionSource().getToken(), i, i2, str, z, str2, str3);
    }

    public int startOpNoThrow(android.os.IBinder iBinder, int i, int i2, java.lang.String str, boolean z, java.lang.String str2, java.lang.String str3) {
        return startOpNoThrow(iBinder, i, i2, str, z, str2, str3, 0, -1);
    }

    public int startOpNoThrow(android.os.IBinder iBinder, int i, android.content.AttributionSource attributionSource, boolean z, java.lang.String str, int i2, int i3) {
        return startOpNoThrow(iBinder, i, attributionSource.getUid(), attributionSource.getPackageName(), z, attributionSource.getAttributionTag(), attributionSource.getDeviceId(), str, i2, i3);
    }

    public int startOpNoThrow(android.os.IBinder iBinder, int i, int i2, java.lang.String str, boolean z, java.lang.String str2, java.lang.String str3, int i3, int i4) {
        return startOpNoThrow(iBinder, i, i2, str, z, str2, 0, str3, i3, i4);
    }

    private int startOpNoThrow(android.os.IBinder iBinder, int i, int i2, java.lang.String str, boolean z, java.lang.String str2, int i3, java.lang.String str3, int i4, int i5) {
        java.lang.String str4;
        boolean z2;
        android.app.SyncNotedAppOp startOperationForDevice;
        try {
            collectNoteOpCallsForValidation(i);
            int notedOpCollectionMode = getNotedOpCollectionMode(i2, str, i);
            boolean z3 = android.os.Process.myUid() == 1000;
            if (notedOpCollectionMode == 3 && str3 == null) {
                str4 = getFormattedStackTrace();
                z2 = true;
            } else {
                str4 = str3;
                z2 = z3;
            }
            if (i3 == 0) {
                startOperationForDevice = this.mService.startOperation(iBinder, i, i2, str, str2, z, notedOpCollectionMode == 3, str4, z2, i4, i5);
            } else {
                startOperationForDevice = this.mService.startOperationForDevice(iBinder, i, i2, str, str2, i3, z, notedOpCollectionMode == 3, str4, z2, i4, i5);
            }
            if (startOperationForDevice.getOpMode() == 0) {
                if (notedOpCollectionMode == 1) {
                    collectNotedOpForSelf(startOperationForDevice);
                } else if (notedOpCollectionMode == 2) {
                    collectNotedOpSync(startOperationForDevice);
                }
            }
            return startOperationForDevice.getOpMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int startProxyOp(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        return startProxyOp(str, new android.content.AttributionSource(this.mContext.getAttributionSource(), new android.content.AttributionSource(i, -1, str2, str3, this.mContext.getAttributionSource().getToken())), str4, false);
    }

    public int startProxyOp(java.lang.String str, android.content.AttributionSource attributionSource, java.lang.String str2, boolean z) {
        int startProxyOpNoThrow = startProxyOpNoThrow(strOpToOp(str), attributionSource, str2, z);
        if (startProxyOpNoThrow == 2) {
            throw new java.lang.SecurityException("Proxy package " + attributionSource.getPackageName() + " from uid " + attributionSource.getUid() + " or calling package " + attributionSource.getNextPackageName() + " from uid " + attributionSource.getNextUid() + " not allowed to perform " + str);
        }
        return startProxyOpNoThrow;
    }

    public int startProxyOpNoThrow(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
        return startProxyOpNoThrow(strOpToOp(str), new android.content.AttributionSource(this.mContext.getAttributionSource(), new android.content.AttributionSource(i, -1, str2, str3, this.mContext.getAttributionSource().getToken())), str4, false);
    }

    public int startProxyOpNoThrow(int i, android.content.AttributionSource attributionSource, java.lang.String str, boolean z) {
        return startProxyOpNoThrow(attributionSource.getToken(), i, attributionSource, str, z, 0, 0, -1);
    }

    public int startProxyOpNoThrow(android.os.IBinder iBinder, int i, android.content.AttributionSource attributionSource, java.lang.String str, boolean z, int i2, int i3, int i4) {
        java.lang.String str2;
        boolean z2;
        try {
            collectNoteOpCallsForValidation(i);
            int notedOpCollectionMode = getNotedOpCollectionMode(attributionSource.getNextUid(), attributionSource.getNextPackageName(), i);
            boolean z3 = android.os.Process.myUid() == 1000;
            if (notedOpCollectionMode == 3 && str == null) {
                str2 = getFormattedStackTrace();
                z2 = true;
            } else {
                str2 = str;
                z2 = z3;
            }
            android.app.SyncNotedAppOp startProxyOperationWithState = this.mService.startProxyOperationWithState(iBinder, i, attributionSource.asState(), false, notedOpCollectionMode == 3, str2, z2, z, i2, i3, i4);
            if (startProxyOperationWithState.getOpMode() == 0) {
                if (notedOpCollectionMode == 1) {
                    collectNotedOpForSelf(startProxyOperationWithState);
                } else if (notedOpCollectionMode == 2 && (this.mContext.checkPermission(android.Manifest.permission.UPDATE_APP_OPS_STATS, -1, android.os.Process.myUid()) == 0 || android.os.Binder.getCallingUid() == attributionSource.getNextUid())) {
                    collectNotedOpSync(startProxyOperationWithState);
                }
            }
            return startProxyOperationWithState.getOpMode();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void finishOp(int i) {
        finishOp(i, android.os.Process.myUid(), this.mContext.getOpPackageName(), (java.lang.String) null);
    }

    public void finishOp(java.lang.String str, int i, java.lang.String str2) {
        finishOp(strOpToOp(str), i, str2, (java.lang.String) null);
    }

    public void finishOp(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) {
        finishOp(strOpToOp(str), i, str2, str3);
    }

    public void finishOp(int i, int i2, java.lang.String str) {
        finishOp(i, i2, str, (java.lang.String) null);
    }

    public void finishOp(int i, int i2, java.lang.String str, java.lang.String str2) {
        finishOp(this.mContext.getAttributionSource().getToken(), i, i2, str, str2);
    }

    public void finishOp(android.os.IBinder iBinder, int i, android.content.AttributionSource attributionSource) {
        finishOp(iBinder, i, attributionSource.getUid(), attributionSource.getPackageName(), attributionSource.getAttributionTag(), attributionSource.getDeviceId());
    }

    public void finishOp(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2) {
        finishOp(iBinder, i, i2, str, str2, 0);
    }

    private void finishOp(android.os.IBinder iBinder, int i, int i2, java.lang.String str, java.lang.String str2, int i3) {
        try {
            if (i3 == 0) {
                this.mService.finishOperation(iBinder, i, i2, str, str2);
            } else {
                this.mService.finishOperationForDevice(iBinder, i, i2, str, str2, i3);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void finishProxyOp(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) {
        android.os.IBinder token = this.mContext.getAttributionSource().getToken();
        finishProxyOp(token, str, new android.content.AttributionSource(this.mContext.getAttributionSource(), new android.content.AttributionSource(i, -1, str2, str3, token)), false);
    }

    public void finishProxyOp(android.os.IBinder iBinder, java.lang.String str, android.content.AttributionSource attributionSource, boolean z) {
        try {
            this.mService.finishProxyOperationWithState(iBinder, strOpToOp(str), attributionSource.asState(), z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isOpActive(java.lang.String str, int i, java.lang.String str2) {
        return isOperationActive(strOpToOp(str), i, str2);
    }

    public boolean isProxying(int i, java.lang.String str, int i2, java.lang.String str2) {
        try {
            return this.mService.isProxying(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), i2, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetPackageOpsNoHistory(java.lang.String str) {
        try {
            this.mService.resetPackageOpsNoHistory(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static void startNotedAppOpsCollection(int i) {
        sBinderThreadCallingUid.set(java.lang.Integer.valueOf(i));
    }

    public static class PausedNotedAppOpsCollection {
        final android.util.ArrayMap<java.lang.String, java.util.BitSet> mCollectedNotedAppOps;
        final int mUid;

        PausedNotedAppOpsCollection(int i, android.util.ArrayMap<java.lang.String, java.util.BitSet> arrayMap) {
            this.mUid = i;
            this.mCollectedNotedAppOps = arrayMap;
        }
    }

    public static android.app.AppOpsManager.PausedNotedAppOpsCollection pauseNotedAppOpsCollection() {
        java.lang.Integer num = sBinderThreadCallingUid.get();
        if (num != null) {
            android.util.ArrayMap<java.lang.String, java.util.BitSet> arrayMap = sAppOpsNotedInThisBinderTransaction.get();
            sBinderThreadCallingUid.remove();
            sAppOpsNotedInThisBinderTransaction.remove();
            return new android.app.AppOpsManager.PausedNotedAppOpsCollection(num.intValue(), arrayMap);
        }
        return null;
    }

    public static void resumeNotedAppOpsCollection(android.app.AppOpsManager.PausedNotedAppOpsCollection pausedNotedAppOpsCollection) {
        if (pausedNotedAppOpsCollection != null) {
            sBinderThreadCallingUid.set(java.lang.Integer.valueOf(pausedNotedAppOpsCollection.mUid));
            if (pausedNotedAppOpsCollection.mCollectedNotedAppOps != null) {
                sAppOpsNotedInThisBinderTransaction.set(pausedNotedAppOpsCollection.mCollectedNotedAppOps);
            }
        }
    }

    public static void finishNotedAppOpsCollection() {
        sBinderThreadCallingUid.remove();
        sAppOpsNotedInThisBinderTransaction.remove();
    }

    private void collectNotedOpForSelf(android.app.SyncNotedAppOp syncNotedAppOp) {
        synchronized (sLock) {
            if (sOnOpNotedCallback != null) {
                sOnOpNotedCallback.onSelfNoted(syncNotedAppOp);
            }
        }
        sMessageCollector.onSelfNoted(syncNotedAppOp);
    }

    public static void collectNotedOpSync(android.app.SyncNotedAppOp syncNotedAppOp) {
        int intValue = sOpStrToOp.get(syncNotedAppOp.getOp()).intValue();
        android.util.ArrayMap<java.lang.String, java.util.BitSet> arrayMap = sAppOpsNotedInThisBinderTransaction.get();
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap<>(1);
            sAppOpsNotedInThisBinderTransaction.set(arrayMap);
        }
        java.util.BitSet bitSet = arrayMap.get(syncNotedAppOp.getAttributionTag());
        if (bitSet == null) {
            bitSet = new java.util.BitSet(148);
            arrayMap.put(syncNotedAppOp.getAttributionTag(), bitSet);
        }
        bitSet.set(intValue);
    }

    @android.annotation.SystemApi
    public java.util.List<android.permission.PermissionGroupUsage> getPermissionGroupUsageForPrivacyIndicator(boolean z) {
        if (this.mUsageHelper == null) {
            this.mUsageHelper = new android.permission.PermissionUsageHelper(this.mContext);
        }
        return this.mUsageHelper.getOpUsageDataForAllDevices(z);
    }

    private int getNotedOpCollectionMode(int i, java.lang.String str, int i2) {
        if (str == null) {
            str = "android";
        }
        if (sAppOpsToNote[i2] == 0) {
            try {
                if (this.mService.shouldCollectNotes(i2)) {
                    sAppOpsToNote[i2] = 2;
                } else {
                    sAppOpsToNote[i2] = 1;
                }
            } catch (android.os.RemoteException e) {
                return 0;
            }
        }
        if (sAppOpsToNote[i2] != 2) {
            return 0;
        }
        synchronized (sLock) {
            if (i == android.os.Process.myUid() && str.equals(android.app.ActivityThread.currentOpPackageName())) {
                return 1;
            }
            java.lang.Integer num = sBinderThreadCallingUid.get();
            return (num == null || num.intValue() != i) ? 3 : 2;
        }
    }

    public static void prefixParcelWithAppOpsIfNeeded(android.os.Parcel parcel) {
        android.util.ArrayMap<java.lang.String, java.util.BitSet> arrayMap = sAppOpsNotedInThisBinderTransaction.get();
        if (arrayMap == null) {
            return;
        }
        parcel.writeInt(-127);
        int size = arrayMap.size();
        parcel.writeInt(size);
        for (int i = 0; i < size; i++) {
            parcel.writeString(arrayMap.keyAt(i));
            long[] longArray = arrayMap.valueAt(i).toLongArray();
            for (int i2 = 0; i2 < 3; i2++) {
                if (i2 < longArray.length) {
                    parcel.writeLong(longArray[i2]);
                } else {
                    parcel.writeLong(0L);
                }
            }
        }
    }

    public static void readAndLogNotedAppops(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            java.lang.String readString = parcel.readString();
            long[] jArr = new long[3];
            for (int i2 = 0; i2 < 3; i2++) {
                jArr[i2] = parcel.readLong();
            }
            java.util.BitSet valueOf = java.util.BitSet.valueOf(jArr);
            if (!valueOf.isEmpty()) {
                synchronized (sLock) {
                    for (int nextSetBit = valueOf.nextSetBit(0); nextSetBit != -1; nextSetBit = valueOf.nextSetBit(nextSetBit + 1)) {
                        if (sOnOpNotedCallback != null) {
                            sOnOpNotedCallback.onNoted(new android.app.SyncNotedAppOp(nextSetBit, readString));
                        } else {
                            sUnforwardedOps.add(new android.app.AsyncNotedAppOp(nextSetBit, android.os.Process.myUid(), readString, getFormattedStackTrace(), java.lang.System.currentTimeMillis()));
                            if (sUnforwardedOps.size() > 10) {
                                sUnforwardedOps.remove(0);
                            }
                        }
                    }
                }
                for (int nextSetBit2 = valueOf.nextSetBit(0); nextSetBit2 != -1; nextSetBit2 = valueOf.nextSetBit(nextSetBit2 + 1)) {
                    sMessageCollector.onNoted(new android.app.SyncNotedAppOp(nextSetBit2, readString));
                }
            }
        }
    }

    public void setOnOpNotedCallback(java.util.concurrent.Executor executor, android.app.AppOpsManager.OnOpNotedCallback onOpNotedCallback) {
        boolean z = true;
        com.android.internal.util.Preconditions.checkState((onOpNotedCallback == null) == (executor == null));
        synchronized (sLock) {
            java.util.List<android.app.AsyncNotedAppOp> list = null;
            if (onOpNotedCallback == null) {
                if (sOnOpNotedCallback == null) {
                    z = false;
                }
                com.android.internal.util.Preconditions.checkState(z, "No callback is currently registered");
                try {
                    this.mService.stopWatchingAsyncNoted(this.mContext.getPackageName(), sOnOpNotedCallback.mAsyncCb);
                } catch (android.os.RemoteException e) {
                    e.rethrowFromSystemServer();
                }
                sOnOpNotedCallback = null;
            } else {
                if (sOnOpNotedCallback != null) {
                    z = false;
                }
                com.android.internal.util.Preconditions.checkState(z, "Another callback is already registered");
                onOpNotedCallback.mAsyncExecutor = executor;
                sOnOpNotedCallback = onOpNotedCallback;
                try {
                    this.mService.startWatchingAsyncNoted(this.mContext.getPackageName(), sOnOpNotedCallback.mAsyncCb);
                    list = this.mService.extractAsyncOps(this.mContext.getPackageName());
                } catch (android.os.RemoteException e2) {
                    e2.rethrowFromSystemServer();
                }
                final android.app.AppOpsManager.OnOpNotedCallback onOpNotedCallback2 = sOnOpNotedCallback;
                if (onOpNotedCallback2 != null && list != null) {
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        final android.app.AsyncNotedAppOp asyncNotedAppOp = list.get(i);
                        onOpNotedCallback2.getAsyncNotedExecutor().execute(new java.lang.Runnable() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.app.AppOpsManager.OnOpNotedCallback.this.onAsyncNoted(asyncNotedAppOp);
                            }
                        });
                    }
                }
                synchronized (this) {
                    int size2 = sUnforwardedOps.size();
                    if (onOpNotedCallback2 != null) {
                        for (int i2 = 0; i2 < size2; i2++) {
                            final android.app.AsyncNotedAppOp asyncNotedAppOp2 = sUnforwardedOps.get(i2);
                            onOpNotedCallback2.getAsyncNotedExecutor().execute(new java.lang.Runnable() { // from class: android.app.AppOpsManager$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    android.app.AppOpsManager.OnOpNotedCallback.this.onAsyncNoted(asyncNotedAppOp2);
                                }
                            });
                        }
                    }
                    sUnforwardedOps.clear();
                }
            }
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setNotedAppOpsCollector(android.app.AppOpsManager.AppOpsCollector appOpsCollector) {
        synchronized (sLock) {
            if (appOpsCollector != null) {
                if (isListeningForOpNoted()) {
                    setOnOpNotedCallback(null, null);
                }
                setOnOpNotedCallback(new android.os.HandlerExecutor(android.os.Handler.getMain()), appOpsCollector);
            } else if (sOnOpNotedCallback != null) {
                setOnOpNotedCallback(null, null);
            }
        }
    }

    public static boolean isListeningForOpNoted() {
        return sOnOpNotedCallback != null || isCollectingStackTraces();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isCollectingStackTraces() {
        if (sConfig.getSampledOpCode() == -1 && sConfig.getAcceptableLeftDistance() == 0 && sConfig.getExpirationTimeSinceBootMillis() >= android.os.SystemClock.elapsedRealtime()) {
            return false;
        }
        return true;
    }

    public static abstract class OnOpNotedCallback {
        private final com.android.internal.app.IAppOpsAsyncNotedCallback mAsyncCb = new android.app.AppOpsManager.OnOpNotedCallback.AnonymousClass1();
        private java.util.concurrent.Executor mAsyncExecutor;

        public abstract void onAsyncNoted(android.app.AsyncNotedAppOp asyncNotedAppOp);

        public abstract void onNoted(android.app.SyncNotedAppOp syncNotedAppOp);

        public abstract void onSelfNoted(android.app.SyncNotedAppOp syncNotedAppOp);

        /* renamed from: android.app.AppOpsManager$OnOpNotedCallback$1, reason: invalid class name */
        class AnonymousClass1 extends com.android.internal.app.IAppOpsAsyncNotedCallback.Stub {
            AnonymousClass1() {
            }

            @Override // com.android.internal.app.IAppOpsAsyncNotedCallback
            public void opNoted(final android.app.AsyncNotedAppOp asyncNotedAppOp) {
                java.util.Objects.requireNonNull(asyncNotedAppOp);
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    android.app.AppOpsManager.OnOpNotedCallback.this.getAsyncNotedExecutor().execute(new java.lang.Runnable() { // from class: android.app.AppOpsManager$OnOpNotedCallback$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.AppOpsManager.OnOpNotedCallback.AnonymousClass1.this.lambda$opNoted$0(asyncNotedAppOp);
                        }
                    });
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$opNoted$0(android.app.AsyncNotedAppOp asyncNotedAppOp) {
                android.app.AppOpsManager.OnOpNotedCallback.this.onAsyncNoted(asyncNotedAppOp);
            }
        }

        protected java.util.concurrent.Executor getAsyncNotedExecutor() {
            return this.mAsyncExecutor;
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static abstract class AppOpsCollector extends android.app.AppOpsManager.OnOpNotedCallback {
        @Override // android.app.AppOpsManager.OnOpNotedCallback
        public java.util.concurrent.Executor getAsyncNotedExecutor() {
            return new android.os.HandlerExecutor(android.os.Handler.getMain());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String getFormattedStackTrace() {
        int i;
        java.lang.StackTraceElement[] stackTrace = new java.lang.Exception().getStackTrace();
        int i2 = 0;
        for (int i3 = 0; i3 < stackTrace.length && (stackTrace[i3].getClassName().startsWith(android.app.AppOpsManager.class.getName()) || stackTrace[i3].getClassName().startsWith(android.os.Parcel.class.getName()) || stackTrace[i3].getClassName().contains("$Stub$Proxy") || stackTrace[i3].getClassName().startsWith(android.database.DatabaseUtils.class.getName()) || stackTrace[i3].getClassName().startsWith("android.content.ContentProviderProxy") || stackTrace[i3].getClassName().startsWith(android.content.ContentResolver.class.getName())); i3++) {
            i2 = i3;
        }
        int length = stackTrace.length - 1;
        int length2 = stackTrace.length - 1;
        while (true) {
            int i4 = length2;
            i = length;
            length = i4;
            if (length < 0 || !(stackTrace[length].getClassName().startsWith(android.os.HandlerThread.class.getName()) || stackTrace[length].getClassName().startsWith(android.os.Handler.class.getName()) || stackTrace[length].getClassName().startsWith(android.os.Looper.class.getName()) || stackTrace[length].getClassName().startsWith(android.os.Binder.class.getName()) || stackTrace[length].getClassName().startsWith(com.android.internal.os.RuntimeInit.class.getName()) || stackTrace[length].getClassName().startsWith(com.android.internal.os.ZygoteInit.class.getName()) || stackTrace[length].getClassName().startsWith(android.app.ActivityThread.class.getName()) || stackTrace[length].getClassName().startsWith(java.lang.reflect.Method.class.getName()) || stackTrace[length].getClassName().startsWith("com.android.server.SystemServer"))) {
                break;
            }
            length2 = length - 1;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i5 = i2; i5 <= i; i5++) {
            if (sFullLog == null) {
                try {
                    sFullLog = java.lang.Boolean.valueOf(android.provider.DeviceConfig.getBoolean("privacy", FULL_LOG, false));
                } catch (java.lang.Exception e) {
                    sFullLog = false;
                }
            }
            if (i5 != i2) {
                sb.append('\n');
            }
            java.lang.String obj = stackTrace[i5].toString();
            if (!sFullLog.booleanValue() && sb.length() + obj.length() > 600) {
                break;
            }
            sb.append(obj);
        }
        return sb.toString();
    }

    public boolean isOperationActive(int i, int i2, java.lang.String str) {
        try {
            return this.mService.isOperationActive(i, i2, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setHistoryParameters(int i, long j, int i2) {
        try {
            this.mService.setHistoryParameters(i, j, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void offsetHistory(long j) {
        try {
            this.mService.offsetHistory(j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addHistoricalOps(android.app.AppOpsManager.HistoricalOps historicalOps) {
        try {
            this.mService.addHistoricalOps(historicalOps);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetHistoryParameters() {
        try {
            this.mService.resetHistoryParameters();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearHistory() {
        try {
            this.mService.clearHistory();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void rebootHistory(long j) {
        try {
            this.mService.rebootHistory(j);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.app.RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage() {
        try {
            return this.mService.collectRuntimeAppOpAccessMessage();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public static java.lang.String[] getOpStrs() {
        java.lang.String[] strArr = new java.lang.String[sAppOpInfos.length];
        for (int i = 0; i < sAppOpInfos.length; i++) {
            strArr[i] = sAppOpInfos[i].name;
        }
        return strArr;
    }

    public static int getNumOps() {
        return 148;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.AppOpsManager.NoteOpEvent getLastEvent(android.util.LongSparseArray<android.app.AppOpsManager.NoteOpEvent> longSparseArray, int i, int i2, int i3) {
        android.app.AppOpsManager.NoteOpEvent noteOpEvent = null;
        if (longSparseArray == null) {
            return null;
        }
        while (i3 != 0) {
            int numberOfTrailingZeros = 1 << java.lang.Integer.numberOfTrailingZeros(i3);
            i3 &= ~numberOfTrailingZeros;
            for (int i4 : UID_STATES) {
                if (i4 >= i && i4 <= i2) {
                    android.app.AppOpsManager.NoteOpEvent noteOpEvent2 = longSparseArray.get(makeKey(i4, numberOfTrailingZeros));
                    if (noteOpEvent == null || (noteOpEvent2 != null && noteOpEvent2.getNoteTime() > noteOpEvent.getNoteTime())) {
                        noteOpEvent = noteOpEvent2;
                    }
                }
            }
        }
        return noteOpEvent;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean equalsLongSparseLongArray(android.util.LongSparseLongArray longSparseLongArray, android.util.LongSparseLongArray longSparseLongArray2) {
        if (longSparseLongArray == longSparseLongArray2) {
            return true;
        }
        if (longSparseLongArray == null || longSparseLongArray2 == null || longSparseLongArray.size() != longSparseLongArray2.size()) {
            return false;
        }
        int size = longSparseLongArray.size();
        for (int i = 0; i < size; i++) {
            if (longSparseLongArray.keyAt(i) != longSparseLongArray2.keyAt(i) || longSparseLongArray.valueAt(i) != longSparseLongArray2.valueAt(i)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeLongSparseLongArrayToParcel(android.util.LongSparseLongArray longSparseLongArray, android.os.Parcel parcel) {
        if (longSparseLongArray != null) {
            int size = longSparseLongArray.size();
            parcel.writeInt(size);
            for (int i = 0; i < size; i++) {
                parcel.writeLong(longSparseLongArray.keyAt(i));
                parcel.writeLong(longSparseLongArray.valueAt(i));
            }
            return;
        }
        parcel.writeInt(-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.util.LongSparseLongArray readLongSparseLongArrayFromParcel(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt < 0) {
            return null;
        }
        android.util.LongSparseLongArray longSparseLongArray = new android.util.LongSparseLongArray(readInt);
        for (int i = 0; i < readInt; i++) {
            longSparseLongArray.append(parcel.readLong(), parcel.readLong());
        }
        return longSparseLongArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void writeDiscreteAccessArrayToParcel(java.util.List<android.app.AppOpsManager.AttributedOpEntry> list, android.os.Parcel parcel, int i) {
        parcel.writeParcelable(list == null ? null : new android.content.pm.ParceledListSlice(list), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.List<android.app.AppOpsManager.AttributedOpEntry> readDiscreteAccessArrayFromParcel(android.os.Parcel parcel) {
        android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readParcelable(null, android.content.pm.ParceledListSlice.class);
        if (parceledListSlice == null) {
            return null;
        }
        return parceledListSlice.getList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.util.LongSparseArray<java.lang.Object> collectKeys(android.util.LongSparseLongArray longSparseLongArray, android.util.LongSparseArray<java.lang.Object> longSparseArray) {
        if (longSparseLongArray != null) {
            if (longSparseArray == null) {
                longSparseArray = new android.util.LongSparseArray<>();
            }
            int size = longSparseLongArray.size();
            for (int i = 0; i < size; i++) {
                longSparseArray.put(longSparseLongArray.keyAt(i), null);
            }
        }
        return longSparseArray;
    }

    public static java.lang.String uidStateToString(int i) {
        switch (i) {
            case 100:
                return "UID_STATE_PERSISTENT";
            case 200:
                return "UID_STATE_TOP";
            case 300:
                return "UID_STATE_FOREGROUND_SERVICE_LOCATION";
            case 400:
                return "UID_STATE_FOREGROUND_SERVICE";
            case 500:
                return "UID_STATE_FOREGROUND";
            case 600:
                return "UID_STATE_BACKGROUND";
            case 700:
                return "UID_STATE_CACHED";
            default:
                return "UNKNOWN";
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int parseHistoricalMode(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case 155185419:
                if (str.equals("HISTORICAL_MODE_ENABLED_ACTIVE")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 885538210:
                if (str.equals("HISTORICAL_MODE_ENABLED_PASSIVE")) {
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
                return 1;
            case 1:
                return 2;
            default:
                return 0;
        }
    }

    public static java.lang.String historicalModeToString(int i) {
        switch (i) {
            case 0:
                return "HISTORICAL_MODE_DISABLED";
            case 1:
                return "HISTORICAL_MODE_ENABLED_ACTIVE";
            case 2:
                return "HISTORICAL_MODE_ENABLED_PASSIVE";
            default:
                return "UNKNOWN";
        }
    }

    private static int getSystemAlertWindowDefault() {
        android.content.pm.PackageManager packageManager;
        android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
        if (currentApplication == null || (packageManager = currentApplication.getPackageManager()) == null || !android.app.ActivityManager.isLowRamDeviceStatic() || packageManager.hasSystemFeature(android.content.pm.PackageManager.FEATURE_LEANBACK, 0)) {
            return 3;
        }
        return 1;
    }

    public static int leftCircularDistance(int i, int i2, int i3) {
        return ((i2 + i3) - i) % i3;
    }

    private void collectNoteOpCallsForValidation(int i) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.util.List<android.app.AppOpsManager.AttributedOpEntry> deduplicateDiscreteEvents(java.util.List<android.app.AppOpsManager.AttributedOpEntry> list) {
        int size = list.size();
        int i = 0;
        int i2 = 0;
        while (i < size) {
            long lastAccessTime = list.get(i).getLastAccessTime(31);
            int i3 = i + 1;
            while (i3 < size && list.get(i3).getLastAccessTime(31) == lastAccessTime) {
                i3++;
            }
            list.set(i2, mergeAttributedOpEntries(list.subList(i, i3)));
            i2++;
            i = i3;
        }
        while (i2 < size) {
            list.remove(list.size() - 1);
            i2++;
        }
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.AppOpsManager.AttributedOpEntry mergeAttributedOpEntries(java.util.List<android.app.AppOpsManager.AttributedOpEntry> list) {
        if (list.size() == 1) {
            return list.get(0);
        }
        android.util.LongSparseArray longSparseArray = new android.util.LongSparseArray();
        android.util.LongSparseArray longSparseArray2 = new android.util.LongSparseArray();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            android.app.AppOpsManager.AttributedOpEntry attributedOpEntry = list.get(i);
            android.util.ArraySet<java.lang.Long> collectKeys = attributedOpEntry.collectKeys();
            int size2 = collectKeys.size();
            for (int i2 = 0; i2 < size2; i2++) {
                long longValue = collectKeys.valueAt(i2).longValue();
                int extractUidStateFromKey = extractUidStateFromKey(longValue);
                int extractFlagsFromKey = extractFlagsFromKey(longValue);
                android.app.AppOpsManager.NoteOpEvent lastAccessEvent = attributedOpEntry.getLastAccessEvent(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                android.app.AppOpsManager.NoteOpEvent lastRejectEvent = attributedOpEntry.getLastRejectEvent(extractUidStateFromKey, extractUidStateFromKey, extractFlagsFromKey);
                if (lastAccessEvent != null) {
                    android.app.AppOpsManager.NoteOpEvent noteOpEvent = (android.app.AppOpsManager.NoteOpEvent) longSparseArray.get(longValue);
                    if (noteOpEvent == null || noteOpEvent.getDuration() == -1) {
                        longSparseArray.append(longValue, lastAccessEvent);
                    } else if (noteOpEvent.mProxy == null && lastAccessEvent.mProxy != null) {
                        noteOpEvent.mProxy = lastAccessEvent.mProxy;
                    }
                }
                if (lastRejectEvent != null) {
                    longSparseArray2.append(longValue, lastRejectEvent);
                }
            }
        }
        return new android.app.AppOpsManager.AttributedOpEntry(list.get(0).mOp, false, longSparseArray, longSparseArray2);
    }
}
