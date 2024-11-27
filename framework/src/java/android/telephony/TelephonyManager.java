package android.telephony;

/* loaded from: classes3.dex */
public class TelephonyManager {

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_ANOMALY_REPORTED = "android.telephony.action.ANOMALY_REPORTED";
    public static final java.lang.String ACTION_CALL_DISCONNECT_CAUSE_CHANGED = "android.intent.action.CALL_DISCONNECT_CAUSE";
    public static final java.lang.String ACTION_CARRIER_MESSAGING_CLIENT_SERVICE = "android.telephony.action.CARRIER_MESSAGING_CLIENT_SERVICE";
    public static final java.lang.String ACTION_CARRIER_SIGNAL_DEFAULT_NETWORK_AVAILABLE = "android.telephony.action.CARRIER_SIGNAL_DEFAULT_NETWORK_AVAILABLE";
    public static final java.lang.String ACTION_CARRIER_SIGNAL_PCO_VALUE = "android.telephony.action.CARRIER_SIGNAL_PCO_VALUE";
    public static final java.lang.String ACTION_CARRIER_SIGNAL_REDIRECTED = "android.telephony.action.CARRIER_SIGNAL_REDIRECTED";
    public static final java.lang.String ACTION_CARRIER_SIGNAL_REQUEST_NETWORK_FAILED = "android.telephony.action.CARRIER_SIGNAL_REQUEST_NETWORK_FAILED";
    public static final java.lang.String ACTION_CARRIER_SIGNAL_RESET = "android.telephony.action.CARRIER_SIGNAL_RESET";
    public static final java.lang.String ACTION_CONFIGURE_VOICEMAIL = "android.telephony.action.CONFIGURE_VOICEMAIL";
    public static final java.lang.String ACTION_DATA_STALL_DETECTED = "android.intent.action.DATA_STALL_DETECTED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED = "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED = "android.intent.action.ACTION_DEFAULT_VOICE_SUBSCRIPTION_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_EMERGENCY_ASSISTANCE = "android.telephony.action.EMERGENCY_ASSISTANCE";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_EMERGENCY_CALLBACK_MODE_CHANGED = "android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_EMERGENCY_CALL_STATE_CHANGED = "android.intent.action.EMERGENCY_CALL_STATE_CHANGED";
    public static final java.lang.String ACTION_MULTI_SIM_CONFIG_CHANGED = "android.telephony.action.MULTI_SIM_CONFIG_CHANGED";
    public static final java.lang.String ACTION_NETWORK_COUNTRY_CHANGED = "android.telephony.action.NETWORK_COUNTRY_CHANGED";
    public static final java.lang.String ACTION_PHONE_STATE_CHANGED = "android.intent.action.PHONE_STATE";
    public static final java.lang.String ACTION_PRIMARY_SUBSCRIPTION_LIST_CHANGED = "android.telephony.action.PRIMARY_SUBSCRIPTION_LIST_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_REQUEST_OMADM_CONFIGURATION_UPDATE = "com.android.omadm.service.CONFIGURATION_UPDATE";
    public static final java.lang.String ACTION_RESET_MOBILE_NETWORK_SETTINGS = "android.telephony.action.RESET_MOBILE_NETWORK_SETTINGS";
    public static final java.lang.String ACTION_RESPOND_VIA_MESSAGE = "android.intent.action.RESPOND_VIA_MESSAGE";
    public static final java.lang.String ACTION_SECRET_CODE = "android.telephony.action.SECRET_CODE";
    public static final java.lang.String ACTION_SERVICE_PROVIDERS_UPDATED = "android.telephony.action.SERVICE_PROVIDERS_UPDATED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_SHOW_NOTICE_ECM_BLOCK_OTHERS = "android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS";
    public static final java.lang.String ACTION_SHOW_VOICEMAIL_NOTIFICATION = "android.telephony.action.SHOW_VOICEMAIL_NOTIFICATION";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_SIM_APPLICATION_STATE_CHANGED = "android.telephony.action.SIM_APPLICATION_STATE_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_SIM_CARD_STATE_CHANGED = "android.telephony.action.SIM_CARD_STATE_CHANGED";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_SIM_SLOT_STATUS_CHANGED = "android.telephony.action.SIM_SLOT_STATUS_CHANGED";
    public static final java.lang.String ACTION_SUBSCRIPTION_CARRIER_IDENTITY_CHANGED = "android.telephony.action.SUBSCRIPTION_CARRIER_IDENTITY_CHANGED";
    public static final java.lang.String ACTION_SUBSCRIPTION_SPECIFIC_CARRIER_IDENTITY_CHANGED = "android.telephony.action.SUBSCRIPTION_SPECIFIC_CARRIER_IDENTITY_CHANGED";
    public static final int ALLOWED_NETWORK_TYPES_REASON_CARRIER = 2;

    @android.annotation.SystemApi
    public static final int ALLOWED_NETWORK_TYPES_REASON_ENABLE_2G = 3;

    @android.annotation.SystemApi
    public static final int ALLOWED_NETWORK_TYPES_REASON_POWER = 1;
    public static final int ALLOWED_NETWORK_TYPES_REASON_USER = 0;
    public static final int APPTYPE_CSIM = 4;
    public static final int APPTYPE_ISIM = 5;
    public static final int APPTYPE_RUIM = 3;
    public static final int APPTYPE_SIM = 1;
    public static final int APPTYPE_UNKNOWN = 0;
    public static final int APPTYPE_USIM = 2;
    public static final int AUTHTYPE_EAP_AKA = 129;
    public static final int AUTHTYPE_EAP_SIM = 128;
    public static final int AUTHTYPE_GBA_BOOTSTRAP = 132;
    public static final int AUTHTYPE_GBA_NAF_KEY_EXTERNAL = 133;
    public static final java.lang.String CACHE_KEY_PHONE_ACCOUNT_TO_SUBID = "cache_key.telephony.phone_account_to_subid";
    private static final int CACHE_MAX_SIZE = 4;
    private static final long CALLBACK_ON_MORE_ERROR_CODE_CHANGE = 130595455;
    public static final int CALL_COMPOSER_STATUS_BUSINESS_ONLY = 2;
    public static final int CALL_COMPOSER_STATUS_OFF = 0;
    public static final int CALL_COMPOSER_STATUS_ON = 1;
    public static final int CALL_STATE_IDLE = 0;
    public static final int CALL_STATE_OFFHOOK = 2;
    public static final int CALL_STATE_RINGING = 1;

    @android.annotation.SystemApi
    public static final int CALL_WAITING_STATUS_DISABLED = 2;

    @android.annotation.SystemApi
    public static final int CALL_WAITING_STATUS_ENABLED = 1;

    @android.annotation.SystemApi
    public static final int CALL_WAITING_STATUS_FDN_CHECK_FAILURE = 5;

    @android.annotation.SystemApi
    public static final int CALL_WAITING_STATUS_NOT_SUPPORTED = 4;

    @android.annotation.SystemApi
    public static final int CALL_WAITING_STATUS_UNKNOWN_ERROR = 3;

    @android.annotation.SystemApi
    public static final java.lang.String CAPABILITY_NR_DUAL_CONNECTIVITY_CONFIGURATION_AVAILABLE = "CAPABILITY_NR_DUAL_CONNECTIVITY_CONFIGURATION_AVAILABLE";
    public static final java.lang.String CAPABILITY_PHYSICAL_CHANNEL_CONFIG_1_6_SUPPORTED = "CAPABILITY_PHYSICAL_CHANNEL_CONFIG_1_6_SUPPORTED";

    @android.annotation.SystemApi
    public static final java.lang.String CAPABILITY_SECONDARY_LINK_BANDWIDTH_VISIBLE = "CAPABILITY_SECONDARY_LINK_BANDWIDTH_VISIBLE";
    public static final java.lang.String CAPABILITY_SIM_PHONEBOOK_IN_MODEM = "CAPABILITY_SIM_PHONEBOOK_IN_MODEM";
    public static final java.lang.String CAPABILITY_SLICING_CONFIG_SUPPORTED = "CAPABILITY_SLICING_CONFIG_SUPPORTED";

    @android.annotation.SystemApi
    public static final java.lang.String CAPABILITY_THERMAL_MITIGATION_DATA_THROTTLING = "CAPABILITY_THERMAL_MITIGATION_DATA_THROTTLING";

    @android.annotation.SystemApi
    public static final java.lang.String CAPABILITY_USES_ALLOWED_NETWORK_TYPES_BITMASK = "CAPABILITY_USES_ALLOWED_NETWORK_TYPES_BITMASK";
    public static final int CARD_POWER_DOWN = 0;
    public static final int CARD_POWER_UP = 1;
    public static final int CARD_POWER_UP_PASS_THROUGH = 2;

    @android.annotation.SystemApi
    public static final int CARRIER_PRIVILEGE_STATUS_ERROR_LOADING_RULES = -2;

    @android.annotation.SystemApi
    public static final int CARRIER_PRIVILEGE_STATUS_HAS_ACCESS = 1;

    @android.annotation.SystemApi
    public static final int CARRIER_PRIVILEGE_STATUS_NO_ACCESS = 0;

    @android.annotation.SystemApi
    public static final int CARRIER_PRIVILEGE_STATUS_RULES_NOT_LOADED = -1;
    public static final int CARRIER_RESTRICTION_STATUS_NOT_RESTRICTED = 1;
    public static final int CARRIER_RESTRICTION_STATUS_RESTRICTED = 2;
    public static final int CARRIER_RESTRICTION_STATUS_RESTRICTED_TO_CALLER = 3;
    public static final int CARRIER_RESTRICTION_STATUS_UNKNOWN = 0;
    public static final int CDMA_ROAMING_MODE_AFFILIATED = 1;
    public static final int CDMA_ROAMING_MODE_ANY = 2;
    public static final int CDMA_ROAMING_MODE_HOME = 0;
    public static final int CDMA_ROAMING_MODE_RADIO_DEFAULT = -1;

    @android.annotation.SystemApi
    public static final int CDMA_SUBSCRIPTION_NV = 1;

    @android.annotation.SystemApi
    public static final int CDMA_SUBSCRIPTION_RUIM_SIM = 0;

    @android.annotation.SystemApi
    public static final int CDMA_SUBSCRIPTION_UNKNOWN = -1;

    @android.annotation.SystemApi
    public static final int CELL_BROADCAST_RESULT_FAIL_ACTIVATION = 3;

    @android.annotation.SystemApi
    public static final int CELL_BROADCAST_RESULT_FAIL_CONFIG = 2;

    @android.annotation.SystemApi
    public static final int CELL_BROADCAST_RESULT_SUCCESS = 0;

    @android.annotation.SystemApi
    public static final int CELL_BROADCAST_RESULT_UNKNOWN = -1;

    @android.annotation.SystemApi
    public static final int CELL_BROADCAST_RESULT_UNSUPPORTED = 1;
    public static final int CHANGE_ICC_LOCK_SUCCESS = Integer.MAX_VALUE;
    public static final int DATA_ACTIVITY_DORMANT = 4;
    public static final int DATA_ACTIVITY_IN = 1;
    public static final int DATA_ACTIVITY_INOUT = 3;
    public static final int DATA_ACTIVITY_NONE = 0;
    public static final int DATA_ACTIVITY_OUT = 2;
    public static final int DATA_CONNECTED = 2;
    public static final int DATA_CONNECTING = 1;
    public static final int DATA_DISCONNECTED = 0;
    public static final int DATA_DISCONNECTING = 4;
    public static final int DATA_ENABLED_REASON_CARRIER = 2;
    public static final int DATA_ENABLED_REASON_OVERRIDE = 4;
    public static final int DATA_ENABLED_REASON_POLICY = 1;
    public static final int DATA_ENABLED_REASON_THERMAL = 3;
    public static final int DATA_ENABLED_REASON_UNKNOWN = -1;
    public static final int DATA_ENABLED_REASON_USER = 0;
    public static final int DATA_HANDOVER_IN_PROGRESS = 5;
    public static final int DATA_SUSPENDED = 3;
    public static final int DATA_UNKNOWN = -1;
    public static final int DEFAULT_PORT_INDEX = 0;
    public static final boolean EMERGENCY_ASSISTANCE_ENABLED = true;
    public static final int EMERGENCY_CALLBACK_MODE_CALL = 1;
    public static final int EMERGENCY_CALLBACK_MODE_SMS = 2;
    public static final long ENABLE_FEATURE_MAPPING = 297989574;

    @android.annotation.SystemApi
    public static final int ENABLE_NR_DUAL_CONNECTIVITY_INVALID_STATE = 4;

    @android.annotation.SystemApi
    public static final int ENABLE_NR_DUAL_CONNECTIVITY_NOT_SUPPORTED = 1;

    @android.annotation.SystemApi
    public static final int ENABLE_NR_DUAL_CONNECTIVITY_RADIO_ERROR = 3;

    @android.annotation.SystemApi
    public static final int ENABLE_NR_DUAL_CONNECTIVITY_RADIO_NOT_AVAILABLE = 2;

    @android.annotation.SystemApi
    public static final int ENABLE_NR_DUAL_CONNECTIVITY_SUCCESS = 0;
    public static final int ENABLE_VONR_RADIO_ERROR = 3;
    public static final int ENABLE_VONR_RADIO_INVALID_STATE = 4;
    public static final int ENABLE_VONR_RADIO_NOT_AVAILABLE = 2;
    public static final int ENABLE_VONR_REQUEST_NOT_SUPPORTED = 5;
    public static final int ENABLE_VONR_SUCCESS = 0;
    public static final int ERI_FLASH = 2;
    public static final int ERI_ICON_MODE_FLASH = 1;
    public static final int ERI_ICON_MODE_NORMAL = 0;
    public static final int ERI_OFF = 1;
    public static final int ERI_ON = 0;
    public static final java.lang.String EVENT_CALL_FORWARDED = "android.telephony.event.EVENT_CALL_FORWARDED";
    public static final java.lang.String EVENT_DISPLAY_EMERGENCY_MESSAGE = "android.telephony.event.DISPLAY_EMERGENCY_MESSAGE";
    public static final java.lang.String EVENT_DOWNGRADE_DATA_DISABLED = "android.telephony.event.EVENT_DOWNGRADE_DATA_DISABLED";
    public static final java.lang.String EVENT_DOWNGRADE_DATA_LIMIT_REACHED = "android.telephony.event.EVENT_DOWNGRADE_DATA_LIMIT_REACHED";
    public static final java.lang.String EVENT_HANDOVER_TO_WIFI_FAILED = "android.telephony.event.EVENT_HANDOVER_TO_WIFI_FAILED";
    public static final java.lang.String EVENT_HANDOVER_VIDEO_FROM_LTE_TO_WIFI = "android.telephony.event.EVENT_HANDOVER_VIDEO_FROM_LTE_TO_WIFI";
    public static final java.lang.String EVENT_HANDOVER_VIDEO_FROM_WIFI_TO_LTE = "android.telephony.event.EVENT_HANDOVER_VIDEO_FROM_WIFI_TO_LTE";
    public static final java.lang.String EVENT_NOTIFY_INTERNATIONAL_CALL_ON_WFC = "android.telephony.event.EVENT_NOTIFY_INTERNATIONAL_CALL_ON_WFC";
    public static final java.lang.String EVENT_SUPPLEMENTARY_SERVICE_NOTIFICATION = "android.telephony.event.EVENT_SUPPLEMENTARY_SERVICE_NOTIFICATION";
    public static final java.lang.String EXCEPTION_RESULT_KEY = "exception";
    public static final java.lang.String EXTRA_ACTIVE_SIM_SUPPORTED_COUNT = "android.telephony.extra.ACTIVE_SIM_SUPPORTED_COUNT";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_ANOMALY_DESCRIPTION = "android.telephony.extra.ANOMALY_DESCRIPTION";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_ANOMALY_ID = "android.telephony.extra.ANOMALY_ID";
    public static final java.lang.String EXTRA_APN_PROTOCOL = "android.telephony.extra.APN_PROTOCOL";
    public static final java.lang.String EXTRA_APN_TYPE = "android.telephony.extra.APN_TYPE";
    public static final java.lang.String EXTRA_CALL_VOICEMAIL_INTENT = "android.telephony.extra.CALL_VOICEMAIL_INTENT";
    public static final java.lang.String EXTRA_CARRIER_ID = "android.telephony.extra.CARRIER_ID";
    public static final java.lang.String EXTRA_CARRIER_NAME = "android.telephony.extra.CARRIER_NAME";
    public static final java.lang.String EXTRA_DATA_FAIL_CAUSE = "android.telephony.extra.DATA_FAIL_CAUSE";
    public static final java.lang.String EXTRA_DATA_SPN = "android.telephony.extra.DATA_SPN";
    public static final java.lang.String EXTRA_DEFAULT_NETWORK_AVAILABLE = "android.telephony.extra.DEFAULT_NETWORK_AVAILABLE";
    public static final java.lang.String EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE = "android.telephony.extra.DEFAULT_SUBSCRIPTION_SELECT_TYPE";
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_ALL = 4;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_DATA = 1;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_DISMISS = 5;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_NONE = 0;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_SMS = 3;
    public static final int EXTRA_DEFAULT_SUBSCRIPTION_SELECT_TYPE_VOICE = 2;

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_DISCONNECT_CAUSE = "disconnect_cause";
    public static final java.lang.String EXTRA_EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE = "android.telephony.extra.EMERGENCY_CALL_TO_SATELLITE_HANDOVER_TYPE";
    public static final java.lang.String EXTRA_EMERGENCY_CALL_TO_SATELLITE_LAUNCH_INTENT = "android.telephony.extra.EMERGENCY_CALL_TO_SATELLITE_LAUNCH_INTENT";
    public static final java.lang.String EXTRA_HIDE_PUBLIC_SETTINGS = "android.telephony.extra.HIDE_PUBLIC_SETTINGS";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_INCOMING_NUMBER = "incoming_number";
    public static final java.lang.String EXTRA_IS_REFRESH = "android.telephony.extra.IS_REFRESH";
    public static final java.lang.String EXTRA_LAST_KNOWN_NETWORK_COUNTRY = "android.telephony.extra.LAST_KNOWN_NETWORK_COUNTRY";
    public static final java.lang.String EXTRA_LAUNCH_VOICEMAIL_SETTINGS_INTENT = "android.telephony.extra.LAUNCH_VOICEMAIL_SETTINGS_INTENT";
    public static final java.lang.String EXTRA_NETWORK_COUNTRY = "android.telephony.extra.NETWORK_COUNTRY";
    public static final java.lang.String EXTRA_NOTIFICATION_CODE = "android.telephony.extra.NOTIFICATION_CODE";
    public static final java.lang.String EXTRA_NOTIFICATION_COUNT = "android.telephony.extra.NOTIFICATION_COUNT";
    public static final java.lang.String EXTRA_NOTIFICATION_MESSAGE = "android.telephony.extra.NOTIFICATION_MESSAGE";
    public static final java.lang.String EXTRA_NOTIFICATION_TYPE = "android.telephony.extra.NOTIFICATION_TYPE";
    public static final java.lang.String EXTRA_PCO_ID = "android.telephony.extra.PCO_ID";
    public static final java.lang.String EXTRA_PCO_VALUE = "android.telephony.extra.PCO_VALUE";
    public static final java.lang.String EXTRA_PHONE_ACCOUNT_HANDLE = "android.telephony.extra.PHONE_ACCOUNT_HANDLE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PHONE_IN_ECM_STATE = "android.telephony.extra.PHONE_IN_ECM_STATE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PHONE_IN_EMERGENCY_CALL = "android.telephony.extra.PHONE_IN_EMERGENCY_CALL";
    public static final java.lang.String EXTRA_PLMN = "android.telephony.extra.PLMN";
    public static final java.lang.String EXTRA_PRECISE_DISCONNECT_CAUSE = "precise_disconnect_cause";
    public static final java.lang.String EXTRA_RECOVERY_ACTION = "recoveryAction";
    public static final java.lang.String EXTRA_REDIRECTION_URL = "android.telephony.extra.REDIRECTION_URL";
    public static final java.lang.String EXTRA_SHOW_PLMN = "android.telephony.extra.SHOW_PLMN";
    public static final java.lang.String EXTRA_SHOW_SPN = "android.telephony.extra.SHOW_SPN";
    public static final java.lang.String EXTRA_SIM_COMBINATION_NAMES = "android.telephony.extra.SIM_COMBINATION_NAMES";
    public static final java.lang.String EXTRA_SIM_COMBINATION_WARNING_TYPE = "android.telephony.extra.SIM_COMBINATION_WARNING_TYPE";
    public static final int EXTRA_SIM_COMBINATION_WARNING_TYPE_DUAL_CDMA = 1;
    public static final int EXTRA_SIM_COMBINATION_WARNING_TYPE_NONE = 0;

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_SIM_STATE = "android.telephony.extra.SIM_STATE";
    public static final java.lang.String EXTRA_SPECIFIC_CARRIER_ID = "android.telephony.extra.SPECIFIC_CARRIER_ID";
    public static final java.lang.String EXTRA_SPECIFIC_CARRIER_NAME = "android.telephony.extra.SPECIFIC_CARRIER_NAME";
    public static final java.lang.String EXTRA_SPN = "android.telephony.extra.SPN";
    public static final java.lang.String EXTRA_STATE = "state";
    public static final java.lang.String EXTRA_SUBSCRIPTION_ID = "android.telephony.extra.SUBSCRIPTION_ID";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_VISUAL_VOICEMAIL_ENABLED_BY_USER_BOOL = "android.telephony.extra.VISUAL_VOICEMAIL_ENABLED_BY_USER_BOOL";
    public static final java.lang.String EXTRA_VOICEMAIL_NUMBER = "android.telephony.extra.VOICEMAIL_NUMBER";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_VOICEMAIL_SCRAMBLED_PIN_STRING = "android.telephony.extra.VOICEMAIL_SCRAMBLED_PIN_STRING";

    @android.annotation.SystemApi
    public static final int GBA_FAILURE_REASON_FEATURE_NOT_READY = 2;

    @android.annotation.SystemApi
    public static final int GBA_FAILURE_REASON_FEATURE_NOT_SUPPORTED = 1;

    @android.annotation.SystemApi
    public static final int GBA_FAILURE_REASON_INCORRECT_NAF_ID = 4;

    @android.annotation.SystemApi
    public static final int GBA_FAILURE_REASON_NETWORK_FAILURE = 3;

    @android.annotation.SystemApi
    public static final int GBA_FAILURE_REASON_SECURITY_PROTOCOL_NOT_SUPPORTED = 5;

    @android.annotation.SystemApi
    public static final int GBA_FAILURE_REASON_UNKNOWN = 0;
    private static final long GET_DATA_STATE_R_VERSION = 148534348;
    public static final int HAL_SERVICE_DATA = 1;
    public static final int HAL_SERVICE_IMS = 7;
    public static final int HAL_SERVICE_MESSAGING = 2;
    public static final int HAL_SERVICE_MODEM = 3;
    public static final int HAL_SERVICE_NETWORK = 4;
    public static final int HAL_SERVICE_RADIO = 0;
    public static final int HAL_SERVICE_SIM = 5;
    public static final int HAL_SERVICE_VOICE = 6;
    public static final int INCLUDE_LOCATION_DATA_COARSE = 1;
    public static final int INCLUDE_LOCATION_DATA_FINE = 2;
    public static final int INCLUDE_LOCATION_DATA_NONE = 0;
    public static final int INDICATION_FILTER_DATA_CALL_DORMANCY_CHANGED = 4;
    public static final int INDICATION_FILTER_FULL_NETWORK_STATE = 2;
    public static final int INDICATION_FILTER_LINK_CAPACITY_ESTIMATE = 8;
    public static final int INDICATION_FILTER_PHYSICAL_CHANNEL_CONFIG = 16;
    public static final int INDICATION_FILTER_SIGNAL_STRENGTH = 1;

    @android.annotation.SystemApi
    public static final int INVALID_EMERGENCY_NUMBER_DB_VERSION = -1;
    public static final int INVALID_PORT_INDEX = -1;
    public static final java.lang.String KEY_CALL_COMPOSER_PICTURE_HANDLE = "call_composer_picture_handle";
    public static final java.lang.String KEY_SLICING_CONFIG_HANDLE = "slicing_config_handle";

    @android.annotation.SystemApi
    public static final int KEY_TYPE_EPDG = 1;

    @android.annotation.SystemApi
    public static final int KEY_TYPE_WLAN = 2;
    private static final int MAXIMUM_CALL_COMPOSER_PICTURE_SIZE = 80000;
    private static final long MAX_NUMBER_VERIFICATION_TIMEOUT_MILLIS = 60000;
    public static final java.lang.String METADATA_HIDE_VOICEMAIL_SETTINGS_MENU = "android.telephony.HIDE_VOICEMAIL_SETTINGS_MENU";

    @android.annotation.SystemApi
    public static final int MOBILE_DATA_POLICY_AUTO_DATA_SWITCH = 3;

    @android.annotation.SystemApi
    public static final int MOBILE_DATA_POLICY_DATA_ON_NON_DEFAULT_DURING_VOICE_CALL = 1;

    @android.annotation.SystemApi
    public static final int MOBILE_DATA_POLICY_MMS_ALWAYS_ALLOWED = 2;
    public static final java.lang.String MODEM_ACTIVITY_RESULT_KEY = "controller_activity";
    public static final int MULTISIM_ALLOWED = 0;
    public static final int MULTISIM_NOT_SUPPORTED_BY_CARRIER = 2;
    public static final int MULTISIM_NOT_SUPPORTED_BY_HARDWARE = 1;
    public static final long NETWORK_CLASS_BITMASK_2G = 32843;
    public static final long NETWORK_CLASS_BITMASK_3G = 93108;
    public static final long NETWORK_CLASS_BITMASK_4G = 397312;
    public static final long NETWORK_CLASS_BITMASK_5G = 524288;
    public static final int NETWORK_MODE_CDMA_EVDO = 4;
    public static final int NETWORK_MODE_CDMA_NO_EVDO = 5;
    public static final int NETWORK_MODE_EVDO_NO_CDMA = 6;
    public static final int NETWORK_MODE_GLOBAL = 7;
    public static final int NETWORK_MODE_GSM_ONLY = 1;
    public static final int NETWORK_MODE_GSM_UMTS = 3;
    public static final int NETWORK_MODE_LTE_CDMA_EVDO = 8;
    public static final int NETWORK_MODE_LTE_CDMA_EVDO_GSM_WCDMA = 10;
    public static final int NETWORK_MODE_LTE_GSM_WCDMA = 9;
    public static final int NETWORK_MODE_LTE_ONLY = 11;
    public static final int NETWORK_MODE_LTE_TDSCDMA = 15;
    public static final int NETWORK_MODE_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 22;
    public static final int NETWORK_MODE_LTE_TDSCDMA_GSM = 17;
    public static final int NETWORK_MODE_LTE_TDSCDMA_GSM_WCDMA = 20;
    public static final int NETWORK_MODE_LTE_TDSCDMA_WCDMA = 19;
    public static final int NETWORK_MODE_LTE_WCDMA = 12;
    public static final int NETWORK_MODE_NR_LTE = 24;
    public static final int NETWORK_MODE_NR_LTE_CDMA_EVDO = 25;
    public static final int NETWORK_MODE_NR_LTE_CDMA_EVDO_GSM_WCDMA = 27;
    public static final int NETWORK_MODE_NR_LTE_GSM_WCDMA = 26;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA = 29;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 33;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_GSM = 30;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_GSM_WCDMA = 32;
    public static final int NETWORK_MODE_NR_LTE_TDSCDMA_WCDMA = 31;
    public static final int NETWORK_MODE_NR_LTE_WCDMA = 28;
    public static final int NETWORK_MODE_NR_ONLY = 23;
    public static final int NETWORK_MODE_TDSCDMA_CDMA_EVDO_GSM_WCDMA = 21;
    public static final int NETWORK_MODE_TDSCDMA_GSM = 16;
    public static final int NETWORK_MODE_TDSCDMA_GSM_WCDMA = 18;
    public static final int NETWORK_MODE_TDSCDMA_ONLY = 13;
    public static final int NETWORK_MODE_TDSCDMA_WCDMA = 14;
    public static final int NETWORK_MODE_WCDMA_ONLY = 2;
    public static final int NETWORK_MODE_WCDMA_PREF = 0;
    public static final int NETWORK_SELECTION_MODE_AUTO = 1;
    public static final int NETWORK_SELECTION_MODE_MANUAL = 2;
    public static final int NETWORK_SELECTION_MODE_UNKNOWN = 0;
    public static final long NETWORK_STANDARDS_FAMILY_BITMASK_3GPP = 906119;
    public static final long NETWORK_STANDARDS_FAMILY_BITMASK_3GPP2 = 10360;
    public static final int NETWORK_TYPE_1xRTT = 7;
    public static final long NETWORK_TYPE_BITMASK_1xRTT = 64;
    public static final long NETWORK_TYPE_BITMASK_CDMA = 8;
    public static final long NETWORK_TYPE_BITMASK_EDGE = 2;
    public static final long NETWORK_TYPE_BITMASK_EHRPD = 8192;
    public static final long NETWORK_TYPE_BITMASK_EVDO_0 = 16;
    public static final long NETWORK_TYPE_BITMASK_EVDO_A = 32;
    public static final long NETWORK_TYPE_BITMASK_EVDO_B = 2048;
    public static final long NETWORK_TYPE_BITMASK_GPRS = 1;
    public static final long NETWORK_TYPE_BITMASK_GSM = 32768;
    public static final long NETWORK_TYPE_BITMASK_HSDPA = 128;
    public static final long NETWORK_TYPE_BITMASK_HSPA = 512;
    public static final long NETWORK_TYPE_BITMASK_HSPAP = 16384;
    public static final long NETWORK_TYPE_BITMASK_HSUPA = 256;
    public static final long NETWORK_TYPE_BITMASK_IDEN = 1024;
    public static final long NETWORK_TYPE_BITMASK_IWLAN = 131072;
    public static final long NETWORK_TYPE_BITMASK_LTE = 4096;

    @java.lang.Deprecated
    public static final long NETWORK_TYPE_BITMASK_LTE_CA = 262144;
    public static final long NETWORK_TYPE_BITMASK_NR = 524288;
    public static final long NETWORK_TYPE_BITMASK_TD_SCDMA = 65536;
    public static final long NETWORK_TYPE_BITMASK_UMTS = 4;
    public static final long NETWORK_TYPE_BITMASK_UNKNOWN = 0;
    public static final int NETWORK_TYPE_CDMA = 4;
    public static final int NETWORK_TYPE_EDGE = 2;
    public static final int NETWORK_TYPE_EHRPD = 14;
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    public static final int NETWORK_TYPE_EVDO_A = 6;
    public static final int NETWORK_TYPE_EVDO_B = 12;
    public static final int NETWORK_TYPE_GPRS = 1;
    public static final int NETWORK_TYPE_GSM = 16;
    public static final int NETWORK_TYPE_HSDPA = 8;
    public static final int NETWORK_TYPE_HSPA = 10;
    public static final int NETWORK_TYPE_HSPAP = 15;
    public static final int NETWORK_TYPE_HSUPA = 9;

    @java.lang.Deprecated
    public static final int NETWORK_TYPE_IDEN = 11;
    public static final int NETWORK_TYPE_IWLAN = 18;
    public static final int NETWORK_TYPE_LTE = 13;
    public static final int NETWORK_TYPE_LTE_CA = 19;
    public static final int NETWORK_TYPE_NR = 20;
    public static final int NETWORK_TYPE_TD_SCDMA = 17;
    public static final int NETWORK_TYPE_UMTS = 3;
    public static final int NETWORK_TYPE_UNKNOWN = 0;

    @android.annotation.SystemApi
    public static final int NR_DUAL_CONNECTIVITY_DISABLE = 2;

    @android.annotation.SystemApi
    public static final int NR_DUAL_CONNECTIVITY_DISABLE_IMMEDIATE = 3;

    @android.annotation.SystemApi
    public static final int NR_DUAL_CONNECTIVITY_ENABLE = 1;
    private static final long NULL_TELEPHONY_THROW_NO_CB = 182185642;
    public static final int OTASP_NEEDED = 2;
    public static final int OTASP_NOT_NEEDED = 3;
    public static final int OTASP_SIM_UNPROVISIONED = 5;
    public static final int OTASP_UNINITIALIZED = 0;
    public static final int OTASP_UNKNOWN = 1;
    public static final java.lang.String PHONE_PROCESS_NAME = "com.android.phone";
    public static final int PHONE_TYPE_CDMA = 2;
    public static final int PHONE_TYPE_GSM = 1;
    public static final int PHONE_TYPE_IMS = 5;
    public static final int PHONE_TYPE_NONE = 0;
    public static final int PHONE_TYPE_SIP = 3;
    public static final int PHONE_TYPE_THIRD_PARTY = 4;
    public static final int PREMIUM_CAPABILITY_PRIORITIZE_LATENCY = 34;

    @android.annotation.SystemApi
    public static final int PREPARE_UNATTENDED_REBOOT_ERROR = 2;

    @android.annotation.SystemApi
    public static final int PREPARE_UNATTENDED_REBOOT_PIN_REQUIRED = 1;

    @android.annotation.SystemApi
    public static final int PREPARE_UNATTENDED_REBOOT_SUCCESS = 0;
    public static final java.lang.String PROPERTY_ENABLE_NULL_CIPHER_TOGGLE = "enable_null_cipher_toggle";
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_ALREADY_IN_PROGRESS = 4;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_ALREADY_PURCHASED = 3;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_CARRIER_DISABLED = 7;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_CARRIER_ERROR = 8;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_ENTITLEMENT_CHECK_FAILED = 13;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_FEATURE_NOT_SUPPORTED = 10;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_NETWORK_NOT_AVAILABLE = 12;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_NOT_DEFAULT_DATA_SUBSCRIPTION = 14;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_NOT_FOREGROUND = 5;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_PENDING_NETWORK_SETUP = 15;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_REQUEST_FAILED = 11;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_SUCCESS = 1;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_THROTTLED = 2;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_TIMEOUT = 9;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_USER_CANCELED = 6;
    public static final int PURCHASE_PREMIUM_CAPABILITY_RESULT_USER_DISABLED = 16;

    @android.annotation.SystemApi
    public static final int RADIO_POWER_OFF = 0;

    @android.annotation.SystemApi
    public static final int RADIO_POWER_ON = 1;

    @android.annotation.SystemApi
    public static final int RADIO_POWER_REASON_CARRIER = 2;

    @android.annotation.SystemApi
    public static final int RADIO_POWER_REASON_NEARBY_DEVICE = 3;

    @android.annotation.SystemApi
    public static final int RADIO_POWER_REASON_THERMAL = 1;

    @android.annotation.SystemApi
    public static final int RADIO_POWER_REASON_USER = 0;

    @android.annotation.SystemApi
    public static final int RADIO_POWER_UNAVAILABLE = 2;

    @android.annotation.SystemApi
    public static final int SET_CARRIER_RESTRICTION_ERROR = 2;

    @android.annotation.SystemApi
    public static final int SET_CARRIER_RESTRICTION_NOT_SUPPORTED = 1;

    @android.annotation.SystemApi
    public static final int SET_CARRIER_RESTRICTION_SUCCESS = 0;
    public static final int SET_OPPORTUNISTIC_SUB_INACTIVE_SUBSCRIPTION = 2;
    public static final int SET_OPPORTUNISTIC_SUB_NO_OPPORTUNISTIC_SUB_AVAILABLE = 3;
    public static final int SET_OPPORTUNISTIC_SUB_REMOTE_SERVICE_EXCEPTION = 4;
    public static final int SET_OPPORTUNISTIC_SUB_SUCCESS = 0;
    public static final int SET_OPPORTUNISTIC_SUB_VALIDATION_FAILED = 1;

    @android.annotation.SystemApi
    public static final int SET_SIM_POWER_STATE_ALREADY_IN_STATE = 1;

    @android.annotation.SystemApi
    public static final int SET_SIM_POWER_STATE_MODEM_ERROR = 2;

    @android.annotation.SystemApi
    public static final int SET_SIM_POWER_STATE_NOT_SUPPORTED = 4;

    @android.annotation.SystemApi
    public static final int SET_SIM_POWER_STATE_SIM_ERROR = 3;

    @android.annotation.SystemApi
    public static final int SET_SIM_POWER_STATE_SUCCESS = 0;

    @android.annotation.SystemApi
    public static final int SIM_ACTIVATION_STATE_ACTIVATED = 2;

    @android.annotation.SystemApi
    public static final int SIM_ACTIVATION_STATE_ACTIVATING = 1;

    @android.annotation.SystemApi
    public static final int SIM_ACTIVATION_STATE_DEACTIVATED = 3;

    @android.annotation.SystemApi
    public static final int SIM_ACTIVATION_STATE_RESTRICTED = 4;

    @android.annotation.SystemApi
    public static final int SIM_ACTIVATION_STATE_UNKNOWN = 0;
    public static final int SIM_STATE_ABSENT = 1;
    public static final int SIM_STATE_CARD_IO_ERROR = 8;
    public static final int SIM_STATE_CARD_RESTRICTED = 9;

    @android.annotation.SystemApi
    public static final int SIM_STATE_LOADED = 10;
    public static final int SIM_STATE_NETWORK_LOCKED = 4;
    public static final int SIM_STATE_NOT_READY = 6;
    public static final int SIM_STATE_PERM_DISABLED = 7;
    public static final int SIM_STATE_PIN_REQUIRED = 2;

    @android.annotation.SystemApi
    public static final int SIM_STATE_PRESENT = 11;
    public static final int SIM_STATE_PUK_REQUIRED = 3;
    public static final int SIM_STATE_READY = 5;
    public static final int SIM_STATE_UNKNOWN = 0;

    @android.annotation.SystemApi
    public static final int SRVCC_STATE_HANDOVER_CANCELED = 3;

    @android.annotation.SystemApi
    public static final int SRVCC_STATE_HANDOVER_COMPLETED = 1;

    @android.annotation.SystemApi
    public static final int SRVCC_STATE_HANDOVER_FAILED = 2;

    @android.annotation.SystemApi
    public static final int SRVCC_STATE_HANDOVER_NONE = -1;

    @android.annotation.SystemApi
    public static final int SRVCC_STATE_HANDOVER_STARTED = 0;
    public static final int STOP_REASON_EMERGENCY_SMS_SENT = 4;
    public static final int STOP_REASON_NORMAL_SMS_SENT = 2;
    public static final int STOP_REASON_OUTGOING_EMERGENCY_CALL_INITIATED = 3;
    public static final int STOP_REASON_OUTGOING_NORMAL_CALL_INITIATED = 1;
    public static final int STOP_REASON_TIMER_EXPIRED = 5;
    public static final int STOP_REASON_UNKNOWN = 0;
    public static final int STOP_REASON_USER_ACTION = 6;
    private static final java.lang.String TAG = "TelephonyManager";

    @android.annotation.SystemApi
    public static final int THERMAL_MITIGATION_RESULT_INVALID_STATE = 3;

    @android.annotation.SystemApi
    public static final int THERMAL_MITIGATION_RESULT_MODEM_ERROR = 1;

    @android.annotation.SystemApi
    public static final int THERMAL_MITIGATION_RESULT_MODEM_NOT_AVAILABLE = 2;

    @android.annotation.SystemApi
    public static final int THERMAL_MITIGATION_RESULT_SUCCESS = 0;

    @android.annotation.SystemApi
    public static final int THERMAL_MITIGATION_RESULT_UNKNOWN_ERROR = 4;
    public static final int UNINITIALIZED_CARD_ID = -2;
    public static final int UNKNOWN_CARRIER_ID = -1;
    public static final int UNKNOWN_CARRIER_ID_LIST_VERSION = -1;
    public static final int UNSUPPORTED_CARD_ID = -1;
    public static final int UPDATE_AVAILABLE_NETWORKS_ABORTED = 2;
    public static final int UPDATE_AVAILABLE_NETWORKS_DISABLE_MODEM_FAIL = 5;
    public static final int UPDATE_AVAILABLE_NETWORKS_ENABLE_MODEM_FAIL = 6;
    public static final int UPDATE_AVAILABLE_NETWORKS_INVALID_ARGUMENTS = 3;
    public static final int UPDATE_AVAILABLE_NETWORKS_MULTIPLE_NETWORKS_NOT_SUPPORTED = 7;
    public static final int UPDATE_AVAILABLE_NETWORKS_NO_CARRIER_PRIVILEGE = 4;
    public static final int UPDATE_AVAILABLE_NETWORKS_NO_OPPORTUNISTIC_SUB_AVAILABLE = 8;
    public static final int UPDATE_AVAILABLE_NETWORKS_REMOTE_SERVICE_EXCEPTION = 9;
    public static final int UPDATE_AVAILABLE_NETWORKS_SERVICE_IS_DISABLED = 10;
    public static final int UPDATE_AVAILABLE_NETWORKS_SIM_PORT_NOT_AVAILABLE = 11;
    public static final int UPDATE_AVAILABLE_NETWORKS_SUCCESS = 0;
    public static final int UPDATE_AVAILABLE_NETWORKS_UNKNOWN_FAILURE = 1;
    public static final int USSD_ERROR_SERVICE_UNAVAIL = -2;
    public static final java.lang.String USSD_RESPONSE = "USSD_RESPONSE";
    public static final int USSD_RETURN_FAILURE = -1;
    public static final int USSD_RETURN_SUCCESS = 100;
    public static final java.lang.String VVM_TYPE_CVVM = "vvm_type_cvvm";
    public static final java.lang.String VVM_TYPE_OMTP = "vvm_type_omtp";
    private static com.android.internal.telephony.IPhoneSubInfo sIPhoneSubInfo;
    private static com.android.internal.telephony.ISms sISms;
    private static com.android.internal.telephony.ISub sISub;
    private static com.android.internal.telephony.ITelephony sITelephony;
    private final android.content.Context mContext;
    private android.app.PropertyInvalidatedCache<android.telecom.PhoneAccountHandle, java.lang.Integer> mPhoneAccountHandleToSubIdCache;
    private final int mSubId;
    private android.telephony.SubscriptionManager mSubscriptionManager;
    private android.telephony.TelephonyRegistryManager mTelephonyRegistryMgr;
    private android.telephony.TelephonyScanManager mTelephonyScanManager;
    private static final java.lang.Object sCacheLock = new java.lang.Object();
    private static boolean sServiceHandleCacheEnabled = true;
    private static final android.telephony.TelephonyManager.DeathRecipient sServiceDeath = new android.telephony.TelephonyManager.DeathRecipient();
    private static android.telephony.TelephonyManager sInstance = new android.telephony.TelephonyManager();
    public static final java.lang.String EXTRA_STATE_IDLE = com.android.internal.telephony.PhoneConstants.State.IDLE.toString();
    public static final java.lang.String EXTRA_STATE_RINGING = com.android.internal.telephony.PhoneConstants.State.RINGING.toString();
    public static final java.lang.String EXTRA_STATE_OFFHOOK = com.android.internal.telephony.PhoneConstants.State.OFFHOOK.toString();
    private static final int[] NETWORK_TYPES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    public static final int DEFAULT_PREFERRED_NETWORK_MODE = com.android.internal.telephony.RILConstants.PREFERRED_NETWORK_MODE;
    public static final android.util.Pair HAL_VERSION_UNKNOWN = new android.util.Pair(-1, -1);
    public static final android.util.Pair HAL_VERSION_UNSUPPORTED = new android.util.Pair(-2, -2);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AllowedNetworkTypesReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AuthType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AuthenticationFailureReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CallComposerStatus {
    }

    @android.annotation.SystemApi
    public interface CallForwardingInfoCallback {
        public static final int RESULT_ERROR_FDN_CHECK_FAILURE = 2;
        public static final int RESULT_ERROR_NOT_SUPPORTED = 3;
        public static final int RESULT_ERROR_UNKNOWN = 1;
        public static final int RESULT_SUCCESS = 0;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface CallForwardingError {
        }

        void onCallForwardingInfoAvailable(android.telephony.CallForwardingInfo callForwardingInfo);

        void onError(int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CallWaitingStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CarrierRestrictionStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CdmaRoamingMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CdmaSubscription {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CellBroadcastResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DataEnabledChangedReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DataEnabledReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DataState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DefaultSubscriptionSelectType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EmergencyCallbackModeStopReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EmergencyCallbackModeType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EnableNrDualConnectivityResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EnableVoNrResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EriIconIndex {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EriIconMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HalService {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface IncludeLocationData {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface IsMultiSimSupportedResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface KeyType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MobileDataPolicy {
    }

    public enum MultiSimVariants {
        DSDS,
        DSDA,
        TSTS,
        UNKNOWN
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NetworkSelectionMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NetworkTypeBitMask {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NrDualConnectivityState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PrefNetworkMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PremiumCapability {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PrepareUnattendedRebootResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PurchasePremiumCapabilityResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RadioInterfaceCapability {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RadioPowerReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SetCarrierRestrictionResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SetOpportunisticSubscriptionResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SetSimPowerStateResult {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SimCombinationWarningType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SimPowerState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SimState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UpdateAvailableNetworksResult {
    }

    public interface WifiCallingChoices {
        public static final int ALWAYS_USE = 0;
        public static final int ASK_EVERY_TIME = 1;
        public static final int NEVER_USE = 2;
    }

    public static java.lang.String srvccStateToString(int i) {
        switch (i) {
            case -1:
                return android.security.keystore.KeyProperties.DIGEST_NONE;
            case 0:
                return "STARTED";
            case 1:
                return "COMPLETED";
            case 2:
                return "FAILED";
            case 3:
                return "CANCELED";
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public TelephonyManager(android.content.Context context) {
        this(context, Integer.MAX_VALUE);
    }

    public TelephonyManager(android.content.Context context, int i) {
        this.mPhoneAccountHandleToSubIdCache = new android.app.PropertyInvalidatedCache<android.telecom.PhoneAccountHandle, java.lang.Integer>(4, CACHE_KEY_PHONE_ACCOUNT_TO_SUBID) { // from class: android.telephony.TelephonyManager.1
            @Override // android.app.PropertyInvalidatedCache
            public java.lang.Integer recompute(android.telecom.PhoneAccountHandle phoneAccountHandle) {
                try {
                    com.android.internal.telephony.ITelephony iTelephony = android.telephony.TelephonyManager.getITelephony();
                    if (iTelephony != null) {
                        return java.lang.Integer.valueOf(iTelephony.getSubIdForPhoneAccountHandle(phoneAccountHandle, android.telephony.TelephonyManager.this.mContext.getOpPackageName(), android.telephony.TelephonyManager.this.mContext.getAttributionTag()));
                    }
                    return -1;
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            }
        };
        this.mSubId = i;
        this.mContext = mergeAttributionAndRenouncedPermissions(context.getApplicationContext(), context);
        this.mSubscriptionManager = android.telephony.SubscriptionManager.from(this.mContext);
    }

    private TelephonyManager() {
        this.mPhoneAccountHandleToSubIdCache = new android.app.PropertyInvalidatedCache<android.telecom.PhoneAccountHandle, java.lang.Integer>(4, CACHE_KEY_PHONE_ACCOUNT_TO_SUBID) { // from class: android.telephony.TelephonyManager.1
            @Override // android.app.PropertyInvalidatedCache
            public java.lang.Integer recompute(android.telecom.PhoneAccountHandle phoneAccountHandle) {
                try {
                    com.android.internal.telephony.ITelephony iTelephony = android.telephony.TelephonyManager.getITelephony();
                    if (iTelephony != null) {
                        return java.lang.Integer.valueOf(iTelephony.getSubIdForPhoneAccountHandle(phoneAccountHandle, android.telephony.TelephonyManager.this.mContext.getOpPackageName(), android.telephony.TelephonyManager.this.mContext.getAttributionTag()));
                    }
                    return -1;
                } catch (android.os.RemoteException e) {
                    throw e.rethrowAsRuntimeException();
                }
            }
        };
        this.mContext = null;
        this.mSubId = -1;
    }

    @java.lang.Deprecated
    public static android.telephony.TelephonyManager getDefault() {
        return sInstance;
    }

    private android.content.Context mergeAttributionAndRenouncedPermissions(android.content.Context context, android.content.Context context2) {
        android.content.Context context3;
        if (context != null) {
            if (!java.util.Objects.equals(context2.getAttributionTag(), context.getAttributionTag())) {
                context3 = context.createAttributionContext(context2.getAttributionTag());
            } else {
                context3 = context;
            }
            java.util.Set<java.lang.String> renouncedPermissions = context2.getAttributionSource().getRenouncedPermissions();
            if (renouncedPermissions.isEmpty()) {
                return context3;
            }
            if (context.getParams() != null) {
                return context3.createContext(new android.content.ContextParams.Builder(context.getParams()).setRenouncedPermissions(renouncedPermissions).build());
            }
            return context3.createContext(new android.content.ContextParams.Builder().setRenouncedPermissions(renouncedPermissions).build());
        }
        return context2;
    }

    private java.lang.String getOpPackageName() {
        if (this.mContext != null) {
            return this.mContext.getOpPackageName();
        }
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return null;
        }
        try {
            return iTelephony.getCurrentPackageName();
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    private java.lang.String getAttributionTag() {
        if (this.mContext != null) {
            return this.mContext.getAttributionTag();
        }
        return null;
    }

    private java.util.Set<java.lang.String> getRenouncedPermissions() {
        if (this.mContext != null) {
            return this.mContext.getAttributionSource().getRenouncedPermissions();
        }
        return java.util.Collections.emptySet();
    }

    private static void runOnBackgroundThread(java.lang.Runnable runnable) {
        try {
            com.android.internal.os.BackgroundThread.getExecutor().execute(runnable);
        } catch (java.util.concurrent.RejectedExecutionException e) {
            throw new java.lang.IllegalStateException("Failed to post a callback from the caller's thread context.", e);
        }
    }

    public android.telephony.TelephonyManager.MultiSimVariants getMultiSimConfiguration() {
        java.lang.String orElse = android.sysprop.TelephonyProperties.multi_sim_config().orElse("");
        if (orElse.equals("dsds")) {
            return android.telephony.TelephonyManager.MultiSimVariants.DSDS;
        }
        if (orElse.equals("dsda")) {
            return android.telephony.TelephonyManager.MultiSimVariants.DSDA;
        }
        if (orElse.equals("tsts")) {
            return android.telephony.TelephonyManager.MultiSimVariants.TSTS;
        }
        return android.telephony.TelephonyManager.MultiSimVariants.UNKNOWN;
    }

    @java.lang.Deprecated
    public int getPhoneCount() {
        return getActiveModemCount();
    }

    public int getActiveModemCount() {
        switch (getMultiSimConfiguration()) {
            case UNKNOWN:
                if (isVoiceCapable() || isSmsCapable() || isDataCapable()) {
                }
                break;
        }
        return 1;
    }

    public int getSupportedModemCount() {
        return android.sysprop.TelephonyProperties.max_active_modems().orElse(java.lang.Integer.valueOf(getActiveModemCount())).intValue();
    }

    @android.annotation.SystemApi
    public int getMaxNumberOfSimultaneouslyActiveSims() {
        switch (getMultiSimConfiguration()) {
        }
        return 1;
    }

    public static android.telephony.TelephonyManager from(android.content.Context context) {
        return (android.telephony.TelephonyManager) context.getSystemService("phone");
    }

    public android.telephony.TelephonyManager createForSubscriptionId(int i) {
        return new android.telephony.TelephonyManager(this.mContext, i);
    }

    public android.telephony.TelephonyManager createForPhoneAccountHandle(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        int subscriptionId = getSubscriptionId(phoneAccountHandle);
        if (!android.telephony.SubscriptionManager.isValidSubscriptionId(subscriptionId)) {
            return null;
        }
        return new android.telephony.TelephonyManager(this.mContext, subscriptionId);
    }

    public boolean isMultiSimEnabled() {
        return getPhoneCount() > 1;
    }

    public static long getMaximumCallComposerPictureSize() {
        return 80000L;
    }

    public java.lang.String getDeviceSoftwareVersion() {
        return getDeviceSoftwareVersion(getSlotIndex());
    }

    @android.annotation.SystemApi
    public java.lang.String getDeviceSoftwareVersion(int i) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return null;
        }
        try {
            return iTelephony.getDeviceSoftwareVersionForSlot(i, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @java.lang.Deprecated
    public java.lang.String getDeviceId() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getDeviceIdWithFeature(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @java.lang.Deprecated
    public java.lang.String getDeviceId(int i) {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getDeviceIdForPhone(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String getImei() {
        return getImei(getSlotIndex());
    }

    public java.lang.String getImei(int i) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return null;
        }
        try {
            return iTelephony.getImeiForSlot(i, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String getTypeAllocationCode() {
        return getTypeAllocationCode(getSlotIndex());
    }

    public java.lang.String getTypeAllocationCode(int i) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return null;
        }
        try {
            return iTelephony.getTypeAllocationCodeForSlot(i);
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String getMeid() {
        return getMeid(getSlotIndex());
    }

    public java.lang.String getMeid(int i) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return null;
        }
        try {
            java.lang.String meidForSlot = iTelephony.getMeidForSlot(i, getOpPackageName(), getAttributionTag());
            if (android.text.TextUtils.isEmpty(meidForSlot)) {
                android.util.Log.d(TAG, "getMeid: return null because MEID is not available");
                return null;
            }
            return meidForSlot;
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String getManufacturerCode() {
        return getManufacturerCode(getSlotIndex());
    }

    public java.lang.String getManufacturerCode(int i) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return null;
        }
        try {
            return iTelephony.getManufacturerCodeForSlot(i);
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String getNai() {
        return getNaiBySubscriberId(getSubId());
    }

    private java.lang.String getNaiBySubscriberId(int i) {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            java.lang.String naiForSubscriber = subscriberInfoService.getNaiForSubscriber(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            if (android.util.Log.isLoggable(TAG, 2)) {
                com.android.telephony.Rlog.v(TAG, "Nai = " + naiForSubscriber);
            }
            return naiForSubscriber;
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @java.lang.Deprecated
    public android.telephony.CellLocation getCellLocation() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                com.android.telephony.Rlog.d(TAG, "getCellLocation returning null because telephony is null");
                return null;
            }
            android.telephony.CellLocation asCellLocation = iTelephony.getCellLocation(this.mContext.getOpPackageName(), this.mContext.getAttributionTag()).asCellLocation();
            if (asCellLocation != null && !asCellLocation.isEmpty()) {
                return asCellLocation;
            }
            com.android.telephony.Rlog.d(TAG, "getCellLocation returning null because CellLocation is empty or phone type doesn't match CellLocation type");
            return null;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.d(TAG, "getCellLocation returning null due to RemoteException " + e);
            return null;
        }
    }

    @java.lang.Deprecated
    public java.util.List<android.telephony.NeighboringCellInfo> getNeighboringCellInfo() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getNeighboringCellInfo(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @android.annotation.SystemApi
    public int getCurrentPhoneType() {
        return getCurrentPhoneType(getSubId());
    }

    @android.annotation.SystemApi
    public int getCurrentPhoneType(int i) {
        int phoneId;
        if (i == -1) {
            phoneId = 0;
        } else {
            phoneId = android.telephony.SubscriptionManager.getPhoneId(i);
        }
        return getCurrentPhoneTypeForSlot(phoneId);
    }

    public int getCurrentPhoneTypeForSlot(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getActivePhoneTypeForSlot(i);
            }
            return getPhoneTypeFromProperty(i);
        } catch (android.os.RemoteException e) {
            return getPhoneTypeFromProperty(i);
        } catch (java.lang.NullPointerException e2) {
            return getPhoneTypeFromProperty(i);
        }
    }

    public int getPhoneType() {
        if (!isVoiceCapable()) {
            return 0;
        }
        return getCurrentPhoneType();
    }

    private int getPhoneTypeFromProperty(int i) {
        java.lang.Integer num = (java.lang.Integer) getTelephonyProperty(i, android.sysprop.TelephonyProperties.current_active_phone(), (java.lang.Object) null);
        return num != null ? num.intValue() : getPhoneTypeFromNetworkType(i);
    }

    private int getPhoneTypeFromNetworkType(int i) {
        java.lang.Integer num = (java.lang.Integer) getTelephonyProperty(i, android.sysprop.TelephonyProperties.default_network(), (java.lang.Object) null);
        if (num != null) {
            return getPhoneType(num.intValue());
        }
        return 0;
    }

    public static int getPhoneType(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 22:
                return 1;
            case 4:
            case 5:
            case 6:
                return 2;
            case 7:
            case 8:
            case 21:
                return 2;
            case 11:
                return android.sysprop.TelephonyProperties.lte_on_cdma_device().orElse(0).intValue() == 1 ? 2 : 1;
            default:
                return 1;
        }
    }

    @android.annotation.SystemApi
    public static long getMaxNumberVerificationTimeoutMillis() {
        return 60000L;
    }

    public java.lang.String getNetworkOperatorName() {
        return getNetworkOperatorName(getSubId());
    }

    public java.lang.String getNetworkOperatorName(int i) {
        return (java.lang.String) getTelephonyProperty(android.telephony.SubscriptionManager.getPhoneId(i), android.sysprop.TelephonyProperties.operator_alpha(), "");
    }

    public java.lang.String getNetworkOperator() {
        return getNetworkOperatorForPhone(getPhoneId());
    }

    public java.lang.String getNetworkOperator(int i) {
        return getNetworkOperatorForPhone(android.telephony.SubscriptionManager.getPhoneId(i));
    }

    public java.lang.String getNetworkOperatorForPhone(int i) {
        return (java.lang.String) getTelephonyProperty(i, android.sysprop.TelephonyProperties.operator_numeric(), "");
    }

    public java.lang.String getNetworkSpecifier() {
        return java.lang.String.valueOf(getSubId());
    }

    public android.os.PersistableBundle getCarrierConfig() {
        return ((android.telephony.CarrierConfigManager) this.mContext.getSystemService(android.telephony.CarrierConfigManager.class)).getConfigForSubId(getSubId());
    }

    public boolean isNetworkRoaming() {
        return isNetworkRoaming(getSubId());
    }

    public boolean isNetworkRoaming(int i) {
        return ((java.lang.Boolean) getTelephonyProperty(android.telephony.SubscriptionManager.getPhoneId(i), (java.util.List<boolean>) android.sysprop.TelephonyProperties.operator_is_roaming(), false)).booleanValue();
    }

    public java.lang.String getNetworkCountryIso() {
        return getNetworkCountryIso(getSlotIndex());
    }

    public java.lang.String getNetworkCountryIso(int i) {
        if (i != Integer.MAX_VALUE) {
            try {
                if (!android.telephony.SubscriptionManager.isValidSlotIndex(i)) {
                    throw new java.lang.IllegalArgumentException("invalid slot index " + i);
                }
            } catch (android.os.RemoteException e) {
                return "";
            }
        }
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        return iTelephony == null ? "" : iTelephony.getNetworkCountryIsoForPhone(i);
    }

    @java.lang.Deprecated
    public java.lang.String getNetworkCountryIsoForPhone(int i) {
        return getNetworkCountryIso(i);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static int[] getAllNetworkTypes() {
        return (int[]) NETWORK_TYPES.clone();
    }

    @java.lang.Deprecated
    public int getNetworkType() {
        return getNetworkType(getSubId(android.telephony.SubscriptionManager.getActiveDataSubscriptionId()));
    }

    public int getNetworkType(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return 0;
            }
            return iTelephony.getNetworkTypeForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return 0;
        } catch (java.lang.NullPointerException e2) {
            return 0;
        }
    }

    public int getDataNetworkType() {
        return getDataNetworkType(getSubId(android.telephony.SubscriptionManager.getActiveDataSubscriptionId()));
    }

    public int getDataNetworkType(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return 0;
            }
            return iTelephony.getDataNetworkTypeForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return 0;
        } catch (java.lang.NullPointerException e2) {
            return 0;
        }
    }

    public int getVoiceNetworkType() {
        return getVoiceNetworkType(getSubId());
    }

    public int getVoiceNetworkType(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return 0;
            }
            return iTelephony.getVoiceNetworkTypeForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return 0;
        } catch (java.lang.NullPointerException e2) {
            return 0;
        }
    }

    public java.lang.String getNetworkTypeName() {
        return getNetworkTypeName(getNetworkType());
    }

    public static java.lang.String getNetworkTypeName(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "GPRS";
            case 2:
                return "EDGE";
            case 3:
                return "UMTS";
            case 4:
                return "CDMA";
            case 5:
                return "CDMA - EvDo rev. 0";
            case 6:
                return "CDMA - EvDo rev. A";
            case 7:
                return "CDMA - 1xRTT";
            case 8:
                return "HSDPA";
            case 9:
                return "HSUPA";
            case 10:
                return "HSPA";
            case 11:
                return "iDEN";
            case 12:
                return "CDMA - EvDo rev. B";
            case 13:
                return com.android.internal.telephony.DctConstants.RAT_NAME_LTE;
            case 14:
                return "CDMA - eHRPD";
            case 15:
                return "HSPA+";
            case 16:
                return "GSM";
            case 17:
                return "TD_SCDMA";
            case 18:
                return "IWLAN";
            case 19:
                return "LTE_CA";
            case 20:
                return "NR";
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static long getBitMaskForNetworkType(int i) {
        switch (i) {
            case 1:
                return 1L;
            case 2:
                return 2L;
            case 3:
                return 4L;
            case 4:
                return 8L;
            case 5:
                return 16L;
            case 6:
                return 32L;
            case 7:
                return 64L;
            case 8:
                return 128L;
            case 9:
                return 256L;
            case 10:
                return 512L;
            case 11:
                return 1024L;
            case 12:
                return 2048L;
            case 13:
            case 19:
                return 4096L;
            case 14:
                return 8192L;
            case 15:
                return 16384L;
            case 16:
                return 32768L;
            case 17:
                return 65536L;
            case 18:
                return 131072L;
            case 20:
                return 524288L;
            default:
                return 0L;
        }
    }

    public boolean hasIccCard() {
        return hasIccCard(getSlotIndex());
    }

    public boolean hasIccCard(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return false;
            }
            return iTelephony.hasIccCardUsingSlotIndex(i);
        } catch (android.os.RemoteException e) {
            return false;
        } catch (java.lang.NullPointerException e2) {
            return false;
        }
    }

    public int getSimState() {
        int simStateIncludingLoaded = getSimStateIncludingLoaded();
        if (simStateIncludingLoaded == 10) {
            return 5;
        }
        return simStateIncludingLoaded;
    }

    private int getSimStateIncludingLoaded() {
        int slotIndex = getSlotIndex();
        if (slotIndex < 0) {
            for (int i = 0; i < getPhoneCount(); i++) {
                int simState = getSimState(i);
                if (simState != 1) {
                    com.android.telephony.Rlog.d(TAG, "getSimState: default sim:" + slotIndex + ", sim state for slotIndex=" + i + " is " + simState + ", return state as unknown");
                    return 0;
                }
            }
            com.android.telephony.Rlog.d(TAG, "getSimState: default sim:" + slotIndex + ", all SIMs absent, return state as absent");
            return 1;
        }
        return getSimStateForSlotIndex(slotIndex);
    }

    @android.annotation.SystemApi
    public int getSimCardState() {
        return getSimCardStateFromSimState(getSimState());
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public int getSimCardState(int i) {
        return getSimCardStateFromSimState(getSimState(getLogicalSlotIndex(i, getFirstActivePortIndex(i))));
    }

    @android.annotation.SystemApi
    public int getSimCardState(int i, int i2) {
        return getSimCardStateFromSimState(getSimState(getLogicalSlotIndex(i, i2)));
    }

    private int getSimCardStateFromSimState(int i) {
        switch (i) {
            case 0:
            case 1:
            case 8:
            case 9:
                return i;
            default:
                return 11;
        }
    }

    private int getLogicalSlotIndex(int i, int i2) {
        android.telephony.UiccSlotInfo[] uiccSlotsInfo = getUiccSlotsInfo();
        if (uiccSlotsInfo != null && i >= 0 && i < uiccSlotsInfo.length && uiccSlotsInfo[i] != null) {
            for (android.telephony.UiccPortInfo uiccPortInfo : uiccSlotsInfo[i].getPorts()) {
                if (uiccPortInfo.getPortIndex() == i2) {
                    return uiccPortInfo.getLogicalSlotIndex();
                }
            }
            return -1;
        }
        return -1;
    }

    @android.annotation.SystemApi
    public int getSimApplicationState() {
        return getSimApplicationStateFromSimState(getSimStateIncludingLoaded());
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public int getSimApplicationState(int i) {
        return getSimApplicationStateFromSimState(getSimStateForSlotIndex(getLogicalSlotIndex(i, getFirstActivePortIndex(i))));
    }

    @android.annotation.SystemApi
    public int getSimApplicationState(int i, int i2) {
        return getSimApplicationStateFromSimState(getSimStateForSlotIndex(getLogicalSlotIndex(i, i2)));
    }

    private int getSimApplicationStateFromSimState(int i) {
        switch (i) {
            case 0:
            case 1:
            case 8:
            case 9:
                return 0;
            case 5:
                return 6;
            default:
                return i;
        }
    }

    @android.annotation.SystemApi
    public boolean isApplicationOnUicc(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isApplicationOnUicc(getSubId(), i);
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#isApplicationOnUicc", e);
            return false;
        }
    }

    public int getSimState(int i) {
        int simStateForSlotIndex = getSimStateForSlotIndex(i);
        if (simStateForSlotIndex == 10) {
            return 5;
        }
        return simStateForSlotIndex;
    }

    public java.lang.String getSimOperator() {
        return getSimOperatorNumeric();
    }

    public java.lang.String getSimOperator(int i) {
        return getSimOperatorNumeric(i);
    }

    public java.lang.String getSimOperatorNumeric() {
        int i = this.mSubId;
        if (!android.telephony.SubscriptionManager.isUsableSubIdValue(i)) {
            i = android.telephony.SubscriptionManager.getDefaultDataSubscriptionId();
            if (!android.telephony.SubscriptionManager.isUsableSubIdValue(i)) {
                i = android.telephony.SubscriptionManager.getDefaultSmsSubscriptionId();
                if (!android.telephony.SubscriptionManager.isUsableSubIdValue(i)) {
                    i = android.telephony.SubscriptionManager.getDefaultVoiceSubscriptionId();
                    if (!android.telephony.SubscriptionManager.isUsableSubIdValue(i)) {
                        i = android.telephony.SubscriptionManager.getDefaultSubscriptionId();
                    }
                }
            }
        }
        return getSimOperatorNumeric(i);
    }

    public java.lang.String getSimOperatorNumeric(int i) {
        return getSimOperatorNumericForPhone(android.telephony.SubscriptionManager.getPhoneId(i));
    }

    public java.lang.String getSimOperatorNumericForPhone(int i) {
        return (java.lang.String) getTelephonyProperty(i, android.sysprop.TelephonyProperties.icc_operator_numeric(), "");
    }

    public java.lang.String getSimOperatorName() {
        return getSimOperatorNameForPhone(getPhoneId());
    }

    public java.lang.String getSimOperatorName(int i) {
        return getSimOperatorNameForPhone(android.telephony.SubscriptionManager.getPhoneId(i));
    }

    public java.lang.String getSimOperatorNameForPhone(int i) {
        return (java.lang.String) getTelephonyProperty(i, android.sysprop.TelephonyProperties.icc_operator_alpha(), "");
    }

    public java.lang.String getSimCountryIso() {
        return getSimCountryIsoForPhone(getPhoneId());
    }

    public static java.lang.String getSimCountryIso(int i) {
        return getSimCountryIsoForPhone(android.telephony.SubscriptionManager.getPhoneId(i));
    }

    public static java.lang.String getSimCountryIsoForPhone(int i) {
        return (java.lang.String) getTelephonyProperty(i, android.sysprop.TelephonyProperties.icc_operator_iso_country(), "");
    }

    public java.lang.String getSimSerialNumber() {
        return getSimSerialNumber(getSubId());
    }

    public java.lang.String getSimSerialNumber(int i) {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getIccSerialNumberForSubscriber(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @android.annotation.SystemApi
    public boolean isLteCdmaEvdoGsmWcdmaEnabled() {
        return getLteOnCdmaMode(getSubId()) == 1;
    }

    public int getLteOnCdmaMode(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return -1;
            }
            return iTelephony.getLteOnCdmaModeForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return -1;
        } catch (java.lang.NullPointerException e2) {
            return -1;
        }
    }

    public int getCardIdForDefaultEuicc() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return -2;
            }
            return iTelephony.getCardIdForDefaultEuicc(this.mSubId, this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            return -2;
        }
    }

    public java.util.List<android.telephony.UiccCardInfo> getUiccCardsInfo() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                android.util.Log.e(TAG, "Error in getUiccCardsInfo: unable to connect to Telephony service.");
                return new java.util.ArrayList();
            }
            return iTelephony.getUiccCardsInfo(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error in getUiccCardsInfo: " + e);
            return new java.util.ArrayList();
        }
    }

    @android.annotation.SystemApi
    public android.telephony.UiccSlotInfo[] getUiccSlotsInfo() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getUiccSlotsInfo(this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public void refreshUiccProfile() {
        try {
            getITelephony().refreshUiccProfile(this.mSubId);
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.w(TAG, "RemoteException", e);
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean switchSlots(int[] iArr) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return false;
            }
            return iTelephony.switchSlots(iArr);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    private static boolean isSlotMappingValid(java.util.Collection<android.telephony.UiccSlotMapping> collection) {
        java.util.Iterator it = ((java.util.Map) collection.stream().collect(java.util.stream.Collectors.groupingBy(new java.util.function.Function() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return java.lang.Integer.valueOf(((android.telephony.UiccSlotMapping) obj).getLogicalSlotIndex());
            }
        }))).entrySet().iterator();
        while (it.hasNext()) {
            if (((java.util.List) ((java.util.Map.Entry) it.next()).getValue()).size() > 1) {
                return false;
            }
        }
        java.util.Iterator it2 = ((java.util.Map) collection.stream().collect(java.util.stream.Collectors.groupingBy(new java.util.function.Function() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.util.List asList;
                asList = java.util.Arrays.asList(java.lang.Integer.valueOf(r1.getPhysicalSlotIndex()), java.lang.Integer.valueOf(((android.telephony.UiccSlotMapping) obj).getPortIndex()));
                return asList;
            }
        }))).entrySet().iterator();
        while (it2.hasNext()) {
            if (((java.util.List) ((java.util.Map.Entry) it2.next()).getValue()).size() > 1) {
                return false;
            }
        }
        return true;
    }

    @android.annotation.SystemApi
    public void setSimSlotMapping(java.util.Collection<android.telephony.UiccSlotMapping> collection) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (isSlotMappingValid(collection)) {
                    if (!iTelephony.setSimSlotMapping(new java.util.ArrayList(collection))) {
                        throw new java.lang.IllegalStateException("setSimSlotMapping has failed");
                    }
                    return;
                }
                throw new java.lang.IllegalArgumentException("Duplicate UiccSlotMapping data found");
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public java.util.Map<java.lang.Integer, java.lang.Integer> getLogicalToPhysicalSlotMapping() {
        java.util.HashMap hashMap = new java.util.HashMap();
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                for (android.telephony.UiccSlotMapping uiccSlotMapping : iTelephony.getSlotsMapping(this.mContext.getOpPackageName())) {
                    hashMap.put(java.lang.Integer.valueOf(uiccSlotMapping.getLogicalSlotIndex()), java.lang.Integer.valueOf(uiccSlotMapping.getPhysicalSlotIndex()));
                }
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "getSlotsMapping RemoteException", e);
        }
        return hashMap;
    }

    @android.annotation.SystemApi
    public java.util.Collection<android.telephony.UiccSlotMapping> getSimSlotMapping() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSlotsMapping(this.mContext.getOpPackageName());
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public java.lang.String getSubscriberId() {
        return getSubscriberId(getSubId());
    }

    public java.lang.String getSubscriberId(int i) {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getSubscriberIdForSubscriber(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @android.annotation.SystemApi
    public android.telephony.ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int i) {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                com.android.telephony.Rlog.e(TAG, "IMSI error: Subscriber Info is null");
                return null;
            }
            int subId = getSubId(android.telephony.SubscriptionManager.getDefaultDataSubscriptionId());
            if (i != 1 && i != 2) {
                throw new java.lang.IllegalArgumentException("IMSI error: Invalid key type");
            }
            android.telephony.ImsiEncryptionInfo carrierInfoForImsiEncryption = subscriberInfoService.getCarrierInfoForImsiEncryption(subId, i, this.mContext.getOpPackageName());
            if (carrierInfoForImsiEncryption == null && isImsiEncryptionRequired(subId, i)) {
                com.android.telephony.Rlog.e(TAG, "IMSI error: key is required but not found");
                throw new java.lang.IllegalArgumentException("IMSI error: key is required but not found");
            }
            return carrierInfoForImsiEncryption;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCarrierInfoForImsiEncryption RemoteException" + e);
            return null;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getCarrierInfoForImsiEncryption NullPointerException" + e2);
            return null;
        }
    }

    @android.annotation.SystemApi
    public void resetCarrierKeysForImsiEncryption() {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                throw new java.lang.RuntimeException("IMSI error: Subscriber Info is null");
            }
            subscriberInfoService.resetCarrierKeysForImsiEncryption(getSubId(android.telephony.SubscriptionManager.getDefaultDataSubscriptionId()), this.mContext.getOpPackageName());
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#getCarrierInfoForImsiEncryption RemoteException" + e);
        }
    }

    private static boolean isKeyEnabled(int i, int i2) {
        return ((i >> (i2 - 1)) & 1) == 1;
    }

    private boolean isImsiEncryptionRequired(int i, int i2) {
        android.os.PersistableBundle configForSubId;
        android.telephony.CarrierConfigManager carrierConfigManager = (android.telephony.CarrierConfigManager) this.mContext.getSystemService("carrier_config");
        if (carrierConfigManager == null || (configForSubId = carrierConfigManager.getConfigForSubId(i)) == null) {
            return false;
        }
        return isKeyEnabled(configForSubId.getInt(android.telephony.CarrierConfigManager.IMSI_KEY_AVAILABILITY_INT), i2);
    }

    public void setCarrierInfoForImsiEncryption(android.telephony.ImsiEncryptionInfo imsiEncryptionInfo) {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return;
            }
            subscriberInfoService.setCarrierInfoForImsiEncryption(this.mSubId, this.mContext.getOpPackageName(), imsiEncryptionInfo);
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setCarrierInfoForImsiEncryption RemoteException", e);
        } catch (java.lang.NullPointerException e2) {
        }
    }

    public static class CallComposerException extends java.lang.Exception {
        public static final int ERROR_AUTHENTICATION_FAILED = 3;
        public static final int ERROR_FILE_TOO_LARGE = 2;
        public static final int ERROR_INPUT_CLOSED = 4;
        public static final int ERROR_IO_EXCEPTION = 5;
        public static final int ERROR_NETWORK_UNAVAILABLE = 6;
        public static final int ERROR_REMOTE_END_CLOSED = 1;
        public static final int ERROR_UNKNOWN = 0;
        public static final int SUCCESS = -1;
        private final int mErrorCode;
        private final java.io.IOException mIOException;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface CallComposerError {
        }

        public CallComposerException(int i, java.io.IOException iOException) {
            this.mErrorCode = i;
            this.mIOException = iOException;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        public java.io.IOException getIOException() {
            return this.mIOException;
        }
    }

    public void uploadCallComposerPicture(final java.nio.file.Path path, final java.lang.String str, final java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<android.os.ParcelUuid, android.telephony.TelephonyManager.CallComposerException> outcomeReceiver) {
        java.util.Objects.requireNonNull(path);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        if (!((android.app.role.RoleManager) this.mContext.getSystemService(android.app.role.RoleManager.class)).isRoleHeld("android.app.role.DIALER")) {
            throw new java.lang.SecurityException("You must hold RoleManager.ROLE_DIALER to do this");
        }
        executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                android.telephony.TelephonyManager.this.lambda$uploadCallComposerPicture$1(path, outcomeReceiver, str, executor);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$uploadCallComposerPicture$1(java.nio.file.Path path, final android.os.OutcomeReceiver outcomeReceiver, java.lang.String str, java.util.concurrent.Executor executor) {
        try {
            if (android.os.Looper.getMainLooper().isCurrentThread()) {
                android.util.Log.w(TAG, "Uploading call composer picture on main thread! hic sunt dracones!");
            }
            if (java.nio.file.Files.size(path) > getMaximumCallComposerPictureSize()) {
                outcomeReceiver.onError(new android.telephony.TelephonyManager.CallComposerException(2, null));
                return;
            }
            final java.io.InputStream newInputStream = java.nio.file.Files.newInputStream(path, new java.nio.file.OpenOption[0]);
            try {
                uploadCallComposerPicture(newInputStream, str, executor, new android.os.OutcomeReceiver<android.os.ParcelUuid, android.telephony.TelephonyManager.CallComposerException>() { // from class: android.telephony.TelephonyManager.2
                    @Override // android.os.OutcomeReceiver
                    public void onResult(android.os.ParcelUuid parcelUuid) {
                        try {
                            newInputStream.close();
                        } catch (java.io.IOException e) {
                            android.util.Log.e(android.telephony.TelephonyManager.TAG, "Error closing file input stream when uploading call composer pic");
                        }
                        outcomeReceiver.onResult(parcelUuid);
                    }

                    @Override // android.os.OutcomeReceiver
                    public void onError(android.telephony.TelephonyManager.CallComposerException callComposerException) {
                        try {
                            newInputStream.close();
                        } catch (java.io.IOException e) {
                            android.util.Log.e(android.telephony.TelephonyManager.TAG, "Error closing file input stream when uploading call composer pic");
                        }
                        outcomeReceiver.onError(callComposerException);
                    }
                });
            } catch (java.lang.Exception e) {
                android.util.Log.e(TAG, "Got exception calling into stream-version of uploadCallComposerPicture: " + e);
                try {
                    newInputStream.close();
                } catch (java.io.IOException e2) {
                    android.util.Log.e(TAG, "Error closing file input stream when uploading call composer pic");
                }
            }
        } catch (java.io.IOException e3) {
            android.util.Log.e(TAG, "IOException when uploading call composer pic:" + e3);
            outcomeReceiver.onError(new android.telephony.TelephonyManager.CallComposerException(5, e3));
        }
    }

    public void uploadCallComposerPicture(final java.io.InputStream inputStream, java.lang.String str, java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<android.os.ParcelUuid, android.telephony.TelephonyManager.CallComposerException> outcomeReceiver) {
        java.util.Objects.requireNonNull(inputStream);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            throw new java.lang.IllegalStateException("Telephony service not available.");
        }
        try {
            android.os.ParcelFileDescriptor[] createReliablePipe = android.os.ParcelFileDescriptor.createReliablePipe();
            final android.os.ParcelFileDescriptor parcelFileDescriptor = createReliablePipe[1];
            android.os.ParcelFileDescriptor parcelFileDescriptor2 = createReliablePipe[0];
            final android.os.ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new android.os.ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
            try {
                iTelephony.uploadCallComposerPicture(getSubId(), this.mContext.getOpPackageName(), str, parcelFileDescriptor2, new android.telephony.TelephonyManager.AnonymousClass3(null, executor, outcomeReceiver));
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Remote exception uploading call composer pic:" + e);
                e.rethrowAsRuntimeException();
            }
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyManager.lambda$uploadCallComposerPicture$3(inputStream, outcomeReceiver, parcelFileDescriptor, autoCloseOutputStream);
                }
            });
        } catch (java.io.IOException e2) {
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.OutcomeReceiver.this.onError(new android.telephony.TelephonyManager.CallComposerException(5, e2));
                }
            });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$3, reason: invalid class name */
    class AnonymousClass3 extends android.os.ResultReceiver {
        final /* synthetic */ android.os.OutcomeReceiver val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(android.os.Handler handler, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, android.os.Bundle bundle) {
            if (i != -1) {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.OutcomeReceiver.this.onError(new android.telephony.TelephonyManager.CallComposerException(i, null));
                    }
                });
                return;
            }
            final android.os.ParcelUuid parcelUuid = (android.os.ParcelUuid) bundle.getParcelable(android.telephony.TelephonyManager.KEY_CALL_COMPOSER_PICTURE_HANDLE, android.os.ParcelUuid.class);
            if (parcelUuid == null) {
                android.util.Log.e(android.telephony.TelephonyManager.TAG, "Got null uuid without an error while uploading call composer pic");
                java.util.concurrent.Executor executor2 = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$3$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.OutcomeReceiver.this.onError(new android.telephony.TelephonyManager.CallComposerException(0, null));
                    }
                });
                return;
            }
            java.util.concurrent.Executor executor3 = this.val$executor;
            final android.os.OutcomeReceiver outcomeReceiver3 = this.val$callback;
            executor3.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$3$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.OutcomeReceiver.this.onResult(parcelUuid);
                }
            });
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x002d, code lost:
    
        android.util.Log.e(android.telephony.TelephonyManager.TAG, "Read too many bytes from call composer pic stream: " + r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0043, code lost:
    
        r11.onError(new android.telephony.TelephonyManager.CallComposerException(2, null));
        r12.closeWithError("too large");
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0055, code lost:
    
        r10 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0056, code lost:
    
        android.util.Log.e(android.telephony.TelephonyManager.TAG, "Error closing fd pipe: " + r10);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static /* synthetic */ void lambda$uploadCallComposerPicture$3(java.io.InputStream inputStream, android.os.OutcomeReceiver outcomeReceiver, android.os.ParcelFileDescriptor parcelFileDescriptor, java.io.OutputStream outputStream) {
        if (android.os.Looper.getMainLooper().isCurrentThread()) {
            android.util.Log.w(TAG, "Uploading call composer picture on main thread! hic sunt dracones!");
        }
        byte[] bArr = new byte[16384];
        int i = 0;
        while (true) {
            try {
                try {
                    int read = inputStream.read(bArr);
                    if (read >= 0) {
                        i += read;
                        if (i > getMaximumCallComposerPictureSize()) {
                            break;
                        }
                        try {
                            outputStream.write(bArr, 0, read);
                        } catch (java.io.IOException e) {
                            outcomeReceiver.onError(new android.telephony.TelephonyManager.CallComposerException(1, e));
                            try {
                                parcelFileDescriptor.closeWithError("remote end closed");
                            } catch (java.io.IOException e2) {
                                android.util.Log.e(TAG, "Error closing fd pipe: " + e2);
                            }
                            try {
                                outputStream.close();
                                return;
                            } catch (java.io.IOException e3) {
                                return;
                            }
                        }
                    }
                } catch (java.io.IOException e4) {
                    android.util.Log.e(TAG, "IOException reading from input while uploading pic: " + e4);
                    outcomeReceiver.onError(new android.telephony.TelephonyManager.CallComposerException(4, e4));
                    try {
                        parcelFileDescriptor.closeWithError("input closed");
                    } catch (java.io.IOException e5) {
                        android.util.Log.e(TAG, "Error closing fd pipe: " + e5);
                    }
                    outputStream.close();
                    return;
                }
            } catch (java.lang.Throwable th) {
                try {
                    outputStream.close();
                } catch (java.io.IOException e6) {
                }
                throw th;
            }
        }
    }

    public java.lang.String getGroupIdLevel1() {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getGroupIdLevel1ForSubscriber(getSubId(), this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String getGroupIdLevel1(int i) {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getGroupIdLevel1ForSubscriber(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @java.lang.Deprecated
    public java.lang.String getLine1Number() {
        return getLine1Number(getSubId());
    }

    public java.lang.String getLine1Number(int i) {
        java.lang.String str;
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                str = null;
            } else {
                str = iTelephony.getLine1NumberForDisplay(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            str = null;
        }
        if (str != null) {
            return str;
        }
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getLine1NumberForSubscriber(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e2) {
            return null;
        } catch (java.lang.NullPointerException e3) {
            return null;
        }
    }

    @java.lang.Deprecated
    public boolean setLine1NumberForDisplay(java.lang.String str, java.lang.String str2) {
        return setLine1NumberForDisplay(getSubId(), str, str2);
    }

    public boolean setLine1NumberForDisplay(int i, java.lang.String str, java.lang.String str2) {
        try {
            this.mSubscriptionManager.setCarrierPhoneNumber(i, str2 == null ? "" : str2);
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setLine1NumberForDisplayForSubscriber(i, str, str2);
            }
            return false;
        } catch (android.os.RemoteException e) {
            return false;
        } catch (java.lang.NullPointerException e2) {
            return false;
        }
    }

    public java.lang.String getLine1AlphaTag() {
        return getLine1AlphaTag(getSubId());
    }

    public java.lang.String getLine1AlphaTag(int i) {
        java.lang.String str;
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                str = null;
            } else {
                str = iTelephony.getLine1AlphaTagForDisplay(i, getOpPackageName(), getAttributionTag());
            }
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            str = null;
        }
        if (str != null) {
            return str;
        }
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getLine1AlphaTagForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e2) {
            return null;
        } catch (java.lang.NullPointerException e3) {
            return null;
        }
    }

    @java.lang.Deprecated
    public java.lang.String[] getMergedSubscriberIds() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getMergedSubscriberIds(getSubId(), getOpPackageName(), getAttributionTag());
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @android.annotation.SystemApi
    public java.lang.String[] getMergedImsisFromGroup() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                java.lang.String[] mergedImsisFromGroup = iTelephony.getMergedImsisFromGroup(getSubId(), getOpPackageName());
                if (mergedImsisFromGroup != null) {
                    return mergedImsisFromGroup;
                }
            }
        } catch (android.os.RemoteException e) {
        }
        return new java.lang.String[0];
    }

    public java.lang.String getMsisdn() {
        return getMsisdn(getSubId());
    }

    public java.lang.String getMsisdn(int i) {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getMsisdnForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String getVoiceMailNumber() {
        return getVoiceMailNumber(getSubId());
    }

    public java.lang.String getVoiceMailNumber(int i) {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getVoiceMailNumberForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public boolean setVoiceMailNumber(java.lang.String str, java.lang.String str2) {
        return setVoiceMailNumber(getSubId(), str, str2);
    }

    public boolean setVoiceMailNumber(int i, java.lang.String str, java.lang.String str2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setVoiceMailNumber(i, str, str2);
            }
            return false;
        } catch (android.os.RemoteException e) {
            return false;
        } catch (java.lang.NullPointerException e2) {
            return false;
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setVisualVoicemailEnabled(android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean isVisualVoicemailEnabled(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        return false;
    }

    @android.annotation.SystemApi
    public android.os.Bundle getVisualVoicemailSettings() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getVisualVoicemailSettings(this.mContext.getOpPackageName(), this.mSubId);
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String getVisualVoicemailPackageName() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getVisualVoicemailPackageName(this.mContext.getOpPackageName(), getAttributionTag(), getSubId());
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public void setVisualVoicemailSmsFilterSettings(android.telephony.VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) {
        if (visualVoicemailSmsFilterSettings == null) {
            disableVisualVoicemailSmsFilter(this.mSubId);
        } else {
            enableVisualVoicemailSmsFilter(this.mSubId, visualVoicemailSmsFilterSettings);
        }
    }

    public void sendVisualVoicemailSms(java.lang.String str, int i, java.lang.String str2, android.app.PendingIntent pendingIntent) {
        sendVisualVoicemailSmsForSubscriber(this.mSubId, str, i, str2, pendingIntent);
    }

    public void enableVisualVoicemailSmsFilter(int i, android.telephony.VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) {
        if (visualVoicemailSmsFilterSettings == null) {
            throw new java.lang.IllegalArgumentException("Settings cannot be null");
        }
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.enableVisualVoicemailSmsFilter(this.mContext.getOpPackageName(), i, visualVoicemailSmsFilterSettings);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.NullPointerException e2) {
        }
    }

    public void disableVisualVoicemailSmsFilter(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.disableVisualVoicemailSmsFilter(this.mContext.getOpPackageName(), i);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.NullPointerException e2) {
        }
    }

    public android.telephony.VisualVoicemailSmsFilterSettings getVisualVoicemailSmsFilterSettings(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getVisualVoicemailSmsFilterSettings(this.mContext.getOpPackageName(), i);
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public android.telephony.VisualVoicemailSmsFilterSettings getActiveVisualVoicemailSmsFilterSettings(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getActiveVisualVoicemailSmsFilterSettings(i);
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public void sendVisualVoicemailSmsForSubscriber(int i, java.lang.String str, int i2, java.lang.String str2, android.app.PendingIntent pendingIntent) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.sendVisualVoicemailSmsForSubscriber(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), i, str, i2, str2, pendingIntent);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    @android.annotation.SystemApi
    public void setVoiceActivationState(int i) {
        setVoiceActivationState(getSubId(), i);
    }

    public void setVoiceActivationState(int i, int i2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setVoiceActivationState(i, i2);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.NullPointerException e2) {
        }
    }

    @android.annotation.SystemApi
    public void setDataActivationState(int i) {
        setDataActivationState(getSubId(), i);
    }

    public void setDataActivationState(int i, int i2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setDataActivationState(i, i2);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.NullPointerException e2) {
        }
    }

    @android.annotation.SystemApi
    public int getVoiceActivationState() {
        return getVoiceActivationState(getSubId());
    }

    public int getVoiceActivationState(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getVoiceActivationState(i, getOpPackageName());
            }
            return 0;
        } catch (android.os.RemoteException e) {
            return 0;
        } catch (java.lang.NullPointerException e2) {
            return 0;
        }
    }

    @android.annotation.SystemApi
    public int getDataActivationState() {
        return getDataActivationState(getSubId());
    }

    public int getDataActivationState(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getDataActivationState(i, getOpPackageName());
            }
            return 0;
        } catch (android.os.RemoteException e) {
            return 0;
        } catch (java.lang.NullPointerException e2) {
            return 0;
        }
    }

    public int getVoiceMessageCount() {
        return getVoiceMessageCount(getSubId());
    }

    public int getVoiceMessageCount(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return 0;
            }
            return iTelephony.getVoiceMessageCountForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return 0;
        } catch (java.lang.NullPointerException e2) {
            return 0;
        }
    }

    public java.lang.String getVoiceMailAlphaTag() {
        return getVoiceMailAlphaTag(getSubId());
    }

    public java.lang.String getVoiceMailAlphaTag(int i) {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getVoiceMailAlphaTagForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public void sendDialerSpecialCode(java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return;
            }
            iTelephony.sendDialerSpecialCode(this.mContext.getOpPackageName(), str);
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#sendDialerSpecialCode RemoteException" + e);
        }
    }

    public java.lang.String getIsimImpi() {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getIsimImpi(getSubId());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String getImsPrivateUserIdentity() {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                com.android.telephony.Rlog.e(TAG, "getImsPrivateUserIdentity(): IPhoneSubInfo instance is NULL");
                throw new java.lang.RuntimeException("IMPI error: Subscriber Info is null");
            }
            return subscriberInfoService.getImsPrivateUserIdentity(getSubId(), getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException | java.lang.IllegalArgumentException | java.lang.NullPointerException e) {
            com.android.telephony.Rlog.e(TAG, "getImsPrivateUserIdentity() Exception = " + e);
            throw new java.lang.RuntimeException(e.getMessage());
        }
    }

    @android.annotation.SystemApi
    public java.lang.String getIsimDomain() {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getIsimDomain(getSubId());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String[] getIsimImpu() {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getIsimImpu(getSubId());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.util.List<android.net.Uri> getImsPublicUserIdentities() {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                throw new java.lang.RuntimeException("IMPU error: Subscriber Info is null");
            }
            return subscriberInfoService.getImsPublicUserIdentities(getSubId(), getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getImsPublicUserIdentities Exception = " + e);
            e.rethrowAsRuntimeException();
            return java.util.Collections.EMPTY_LIST;
        } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getImsPublicUserIdentities Exception = " + e2);
            return java.util.Collections.EMPTY_LIST;
        }
    }

    @java.lang.Deprecated
    public int getCallState() {
        android.telecom.TelecomManager telecomManager;
        if (this.mContext != null && (telecomManager = (android.telecom.TelecomManager) this.mContext.getSystemService(android.telecom.TelecomManager.class)) != null) {
            return telecomManager.getCallState();
        }
        return 0;
    }

    public int getCallStateForSubscription() {
        return getCallState(getSubId());
    }

    public int getCallState(int i) {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return 0;
        }
        try {
            return iTelephony.getCallStateForSubscription(i, this.mContext.getPackageName(), this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    public int getDataActivity() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return 0;
            }
            return iTelephony.getDataActivityForSubId(getSubId(android.telephony.SubscriptionManager.getActiveDataSubscriptionId()));
        } catch (android.os.RemoteException e) {
            return 0;
        } catch (java.lang.NullPointerException e2) {
            return 0;
        }
    }

    public int getDataState() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return 0;
            }
            int dataStateForSubId = iTelephony.getDataStateForSubId(getSubId(android.telephony.SubscriptionManager.getActiveDataSubscriptionId()));
            if (dataStateForSubId == 4) {
                if (!android.compat.Compatibility.isChangeEnabled(GET_DATA_STATE_R_VERSION)) {
                    return 2;
                }
            }
            return dataStateForSubId;
        } catch (android.os.RemoteException e) {
            return 0;
        } catch (java.lang.NullPointerException e2) {
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.internal.telephony.ITelephony getITelephony() {
        if (!sServiceHandleCacheEnabled) {
            return com.android.internal.telephony.ITelephony.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
        }
        if (sITelephony == null) {
            com.android.internal.telephony.ITelephony asInterface = com.android.internal.telephony.ITelephony.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
            synchronized (sCacheLock) {
                if (sITelephony == null && asInterface != null) {
                    try {
                        sITelephony = asInterface;
                        sITelephony.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (java.lang.Exception e) {
                        sITelephony = null;
                    }
                }
            }
        }
        return sITelephony;
    }

    private com.android.internal.telephony.IOns getIOns() {
        return com.android.internal.telephony.IOns.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getOpportunisticNetworkServiceRegisterer().get());
    }

    @java.lang.Deprecated
    public void listen(android.telephony.PhoneStateListener phoneStateListener, int i) {
        if (this.mContext == null) {
            return;
        }
        boolean z = getITelephony() != null;
        android.telephony.TelephonyRegistryManager telephonyRegistryManager = (android.telephony.TelephonyRegistryManager) this.mContext.getSystemService(android.content.Context.TELEPHONY_REGISTRY_SERVICE);
        if (telephonyRegistryManager != null) {
            java.util.Set<java.lang.String> renouncedPermissions = getRenouncedPermissions();
            telephonyRegistryManager.listenFromListener(this.mSubId, renouncedPermissions.contains(android.Manifest.permission.ACCESS_FINE_LOCATION), renouncedPermissions.contains(android.Manifest.permission.ACCESS_COARSE_LOCATION), getOpPackageName(), getAttributionTag(), phoneStateListener, i, z);
        } else {
            com.android.telephony.Rlog.w(TAG, "telephony registry not ready.");
        }
    }

    @android.annotation.SystemApi
    public int getCdmaEnhancedRoamingIndicatorDisplayNumber() {
        return getCdmaEriIconIndex(getSubId());
    }

    public int getCdmaEriIconIndex(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return -1;
            }
            return iTelephony.getCdmaEriIconIndexForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return -1;
        } catch (java.lang.NullPointerException e2) {
            return -1;
        }
    }

    public int getCdmaEriIconMode(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return -1;
            }
            return iTelephony.getCdmaEriIconModeForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return -1;
        } catch (java.lang.NullPointerException e2) {
            return -1;
        }
    }

    public java.lang.String getCdmaEriText() {
        return getCdmaEriText(getSubId());
    }

    public java.lang.String getCdmaEriText(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getCdmaEriTextForSubscriber(i, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @java.lang.Deprecated
    public boolean isVoiceCapable() {
        if (this.mContext == null) {
            return true;
        }
        return this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_voice_capable);
    }

    public boolean isDeviceVoiceCapable() {
        return isVoiceCapable();
    }

    public boolean isSmsCapable() {
        if (this.mContext == null) {
            return true;
        }
        return this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_sms_capable);
    }

    public boolean isDeviceSmsCapable() {
        return isSmsCapable();
    }

    public java.util.List<android.telephony.CellInfo> getAllCellInfo() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getAllCellInfo(getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            return null;
        }
    }

    public static abstract class CellInfoCallback {
        public static final int ERROR_MODEM_ERROR = 2;
        public static final int ERROR_TIMEOUT = 1;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface CellInfoCallbackError {
        }

        public abstract void onCellInfo(java.util.List<android.telephony.CellInfo> list);

        public void onError(int i, java.lang.Throwable th) {
            onCellInfo(new java.util.ArrayList());
        }
    }

    public void requestCellInfoUpdate(final java.util.concurrent.Executor executor, final android.telephony.TelephonyManager.CellInfoCallback cellInfoCallback) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                if (android.compat.Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new java.lang.IllegalStateException("Telephony is null");
                }
            } else {
                iTelephony.requestCellInfoUpdate(getSubId(), new android.telephony.TelephonyManager.AnonymousClass4(executor, cellInfoCallback), getOpPackageName(), getAttributionTag());
            }
        } catch (android.os.RemoteException e) {
            runOnBackgroundThread(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda22
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.TelephonyManager.CellInfoCallback.this.onError(2, r2);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$4, reason: invalid class name */
    class AnonymousClass4 extends android.telephony.ICellInfoCallback.Stub {
        final /* synthetic */ android.telephony.TelephonyManager.CellInfoCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass4(java.util.concurrent.Executor executor, android.telephony.TelephonyManager.CellInfoCallback cellInfoCallback) {
            this.val$executor = executor;
            this.val$callback = cellInfoCallback;
        }

        @Override // android.telephony.ICellInfoCallback
        public void onCellInfo(final java.util.List<android.telephony.CellInfo> list) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.TelephonyManager.CellInfoCallback cellInfoCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$4$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.TelephonyManager.CellInfoCallback.this.onCellInfo(list);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.ICellInfoCallback
        public void onError(final int i, final java.lang.String str, final java.lang.String str2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.TelephonyManager.CellInfoCallback cellInfoCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.TelephonyManager.CellInfoCallback.this.onError(i, android.telephony.TelephonyManager.createThrowableByClassName(str, str2));
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @android.annotation.SystemApi
    public void requestCellInfoUpdate(android.os.WorkSource workSource, final java.util.concurrent.Executor executor, final android.telephony.TelephonyManager.CellInfoCallback cellInfoCallback) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                if (android.compat.Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new java.lang.IllegalStateException("Telephony is null");
                }
            } else {
                iTelephony.requestCellInfoUpdateWithWorkSource(getSubId(), new android.telephony.TelephonyManager.AnonymousClass5(executor, cellInfoCallback), getOpPackageName(), getAttributionTag(), workSource);
            }
        } catch (android.os.RemoteException e) {
            runOnBackgroundThread(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda21
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.TelephonyManager.CellInfoCallback.this.onError(2, r2);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$5, reason: invalid class name */
    class AnonymousClass5 extends android.telephony.ICellInfoCallback.Stub {
        final /* synthetic */ android.telephony.TelephonyManager.CellInfoCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass5(java.util.concurrent.Executor executor, android.telephony.TelephonyManager.CellInfoCallback cellInfoCallback) {
            this.val$executor = executor;
            this.val$callback = cellInfoCallback;
        }

        @Override // android.telephony.ICellInfoCallback
        public void onCellInfo(final java.util.List<android.telephony.CellInfo> list) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.TelephonyManager.CellInfoCallback cellInfoCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$5$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.TelephonyManager.CellInfoCallback.this.onCellInfo(list);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.ICellInfoCallback
        public void onError(final int i, final java.lang.String str, final java.lang.String str2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.TelephonyManager.CellInfoCallback cellInfoCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$5$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.TelephonyManager.CellInfoCallback.this.onError(i, android.telephony.TelephonyManager.createThrowableByClassName(str, str2));
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.Throwable createThrowableByClassName(java.lang.String str, java.lang.String str2) {
        if (str == null) {
            return null;
        }
        try {
            return (java.lang.Throwable) java.lang.Class.forName(str).getConstructor(java.lang.String.class).newInstance(str2);
        } catch (java.lang.ClassCastException | java.lang.ReflectiveOperationException e) {
            return new java.lang.RuntimeException(str + ": " + str2);
        }
    }

    public void setCellInfoListRate(int i, int i2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setCellInfoListRate(i, i2);
            }
        } catch (android.os.RemoteException e) {
        } catch (java.lang.NullPointerException e2) {
        }
    }

    public java.lang.String getMmsUserAgent() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getMmsUserAgent(getSubId());
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String getMmsUAProfUrl() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getMmsUAProfUrl(getSubId());
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    private int getFirstActivePortIndex(int i) {
        android.telephony.UiccSlotInfo[] uiccSlotsInfo = getUiccSlotsInfo();
        if (uiccSlotsInfo != null && i >= 0 && i < uiccSlotsInfo.length && uiccSlotsInfo[i] != null) {
            java.util.Optional<android.telephony.UiccPortInfo> findFirst = uiccSlotsInfo[i].getPorts().stream().filter(new java.util.function.Predicate() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda18
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean isActive;
                    isActive = ((android.telephony.UiccPortInfo) obj).isActive();
                    return isActive;
                }
            }).findFirst();
            if (findFirst.isPresent()) {
                return findFirst.get().getPortIndex();
            }
            return -1;
        }
        return -1;
    }

    @java.lang.Deprecated
    public android.telephony.IccOpenLogicalChannelResponse iccOpenLogicalChannel(java.lang.String str) {
        return iccOpenLogicalChannel(getSubId(), str, -1);
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public android.telephony.IccOpenLogicalChannelResponse iccOpenLogicalChannelBySlot(int i, java.lang.String str, int i2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest = new com.android.internal.telephony.IccLogicalChannelRequest();
                iccLogicalChannelRequest.slotIndex = i;
                iccLogicalChannelRequest.portIndex = getFirstActivePortIndex(i);
                iccLogicalChannelRequest.aid = str;
                iccLogicalChannelRequest.p2 = i2;
                iccLogicalChannelRequest.callingPackage = getOpPackageName();
                iccLogicalChannelRequest.binder = new android.os.Binder();
                return iTelephony.iccOpenLogicalChannel(iccLogicalChannelRequest);
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @android.annotation.SystemApi
    public android.telephony.IccOpenLogicalChannelResponse iccOpenLogicalChannelByPort(int i, int i2, java.lang.String str, int i3) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest = new com.android.internal.telephony.IccLogicalChannelRequest();
                iccLogicalChannelRequest.slotIndex = i;
                iccLogicalChannelRequest.portIndex = i2;
                iccLogicalChannelRequest.aid = str;
                iccLogicalChannelRequest.p2 = i3;
                iccLogicalChannelRequest.callingPackage = getOpPackageName();
                iccLogicalChannelRequest.binder = new android.os.Binder();
                return iTelephony.iccOpenLogicalChannel(iccLogicalChannelRequest);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public android.telephony.IccOpenLogicalChannelResponse iccOpenLogicalChannel(java.lang.String str, int i) {
        return iccOpenLogicalChannel(getSubId(), str, i);
    }

    public android.telephony.IccOpenLogicalChannelResponse iccOpenLogicalChannel(int i, java.lang.String str, int i2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest = new com.android.internal.telephony.IccLogicalChannelRequest();
                iccLogicalChannelRequest.subId = i;
                iccLogicalChannelRequest.callingPackage = getOpPackageName();
                iccLogicalChannelRequest.aid = str;
                iccLogicalChannelRequest.p2 = i2;
                iccLogicalChannelRequest.binder = new android.os.Binder();
                return iTelephony.iccOpenLogicalChannel(iccLogicalChannelRequest);
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean iccCloseLogicalChannelBySlot(int i, int i2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest = new com.android.internal.telephony.IccLogicalChannelRequest();
                iccLogicalChannelRequest.slotIndex = i;
                iccLogicalChannelRequest.portIndex = getFirstActivePortIndex(i);
                iccLogicalChannelRequest.channel = i2;
                return iTelephony.iccCloseLogicalChannel(iccLogicalChannelRequest);
            }
            return false;
        } catch (android.os.RemoteException e) {
            return false;
        } catch (java.lang.IllegalStateException e2) {
            com.android.telephony.Rlog.e(TAG, "iccCloseLogicalChannel IllegalStateException", e2);
            return false;
        } catch (java.lang.NullPointerException e3) {
            return false;
        }
    }

    @android.annotation.SystemApi
    public void iccCloseLogicalChannelByPort(int i, int i2, int i3) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest = new com.android.internal.telephony.IccLogicalChannelRequest();
                iccLogicalChannelRequest.slotIndex = i;
                iccLogicalChannelRequest.portIndex = i2;
                iccLogicalChannelRequest.channel = i3;
                iTelephony.iccCloseLogicalChannel(iccLogicalChannelRequest);
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean iccCloseLogicalChannel(int i) {
        try {
            return iccCloseLogicalChannel(getSubId(), i);
        } catch (java.lang.IllegalStateException e) {
            com.android.telephony.Rlog.e(TAG, "iccCloseLogicalChannel IllegalStateException", e);
            return false;
        }
    }

    public boolean iccCloseLogicalChannel(int i, int i2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                com.android.internal.telephony.IccLogicalChannelRequest iccLogicalChannelRequest = new com.android.internal.telephony.IccLogicalChannelRequest();
                iccLogicalChannelRequest.subId = i;
                iccLogicalChannelRequest.channel = i2;
                return iTelephony.iccCloseLogicalChannel(iccLogicalChannelRequest);
            }
            return false;
        } catch (android.os.RemoteException e) {
            return false;
        } catch (java.lang.IllegalStateException e2) {
            com.android.telephony.Rlog.e(TAG, "iccCloseLogicalChannel IllegalStateException", e2);
            return false;
        } catch (java.lang.NullPointerException e3) {
            return false;
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public java.lang.String iccTransmitApduLogicalChannelBySlot(int i, int i2, int i3, int i4, int i5, int i6, int i7, java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.iccTransmitApduLogicalChannelByPort(i, getFirstActivePortIndex(i), i2, i3, i4, i5, i6, i7, str);
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @android.annotation.SystemApi
    public java.lang.String iccTransmitApduLogicalChannelByPort(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.iccTransmitApduLogicalChannelByPort(i, i2, i3, i4, i5, i6, i7, i8, str);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public java.lang.String iccTransmitApduLogicalChannel(int i, int i2, int i3, int i4, int i5, int i6, java.lang.String str) {
        return iccTransmitApduLogicalChannel(getSubId(), i, i2, i3, i4, i5, i6, str);
    }

    public java.lang.String iccTransmitApduLogicalChannel(int i, int i2, int i3, int i4, int i5, int i6, int i7, java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.iccTransmitApduLogicalChannel(i, i2, i3, i4, i5, i6, i7, str);
            }
            return "";
        } catch (android.os.RemoteException e) {
            return "";
        } catch (java.lang.NullPointerException e2) {
            return "";
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public java.lang.String iccTransmitApduBasicChannelBySlot(int i, int i2, int i3, int i4, int i5, int i6, java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.iccTransmitApduBasicChannelByPort(i, getFirstActivePortIndex(i), getOpPackageName(), i2, i3, i4, i5, i6, str);
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @android.annotation.SystemApi
    public java.lang.String iccTransmitApduBasicChannelByPort(int i, int i2, int i3, int i4, int i5, int i6, int i7, java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.iccTransmitApduBasicChannelByPort(i, i2, getOpPackageName(), i3, i4, i5, i6, i7, str);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public java.lang.String iccTransmitApduBasicChannel(int i, int i2, int i3, int i4, int i5, java.lang.String str) {
        return iccTransmitApduBasicChannel(getSubId(), i, i2, i3, i4, i5, str);
    }

    public java.lang.String iccTransmitApduBasicChannel(int i, int i2, int i3, int i4, int i5, int i6, java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.iccTransmitApduBasicChannel(i, getOpPackageName(), i2, i3, i4, i5, i6, str);
            }
            return "";
        } catch (android.os.RemoteException e) {
            return "";
        } catch (java.lang.NullPointerException e2) {
            return "";
        }
    }

    public byte[] iccExchangeSimIO(int i, int i2, int i3, int i4, int i5, java.lang.String str) {
        return iccExchangeSimIO(getSubId(), i, i2, i3, i4, i5, str);
    }

    public byte[] iccExchangeSimIO(int i, int i2, int i3, int i4, int i5, int i6, java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.iccExchangeSimIO(i, i2, i3, i4, i5, i6, str);
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String sendEnvelopeWithStatus(java.lang.String str) {
        return sendEnvelopeWithStatus(getSubId(), str);
    }

    public java.lang.String sendEnvelopeWithStatus(int i, java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.sendEnvelopeWithStatus(i, str);
            }
            return "";
        } catch (android.os.RemoteException e) {
            return "";
        } catch (java.lang.NullPointerException e2) {
            return "";
        }
    }

    public java.lang.String nvReadItem(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.nvReadItem(i);
            }
            return "";
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "nvReadItem RemoteException", e);
            return "";
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "nvReadItem NPE", e2);
            return "";
        }
    }

    public boolean nvWriteItem(int i, java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.nvWriteItem(i, str);
            }
            return false;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "nvWriteItem RemoteException", e);
            return false;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "nvWriteItem NPE", e2);
            return false;
        }
    }

    public boolean nvWriteCdmaPrl(byte[] bArr) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.nvWriteCdmaPrl(bArr);
            }
            return false;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "nvWriteCdmaPrl RemoteException", e);
            return false;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "nvWriteCdmaPrl NPE", e2);
            return false;
        }
    }

    public boolean nvResetConfig(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (i == 1) {
                    return iTelephony.rebootModem(getSlotIndex());
                }
                if (i != 3) {
                    com.android.telephony.Rlog.e(TAG, "nvResetConfig unsupported reset type");
                    return false;
                }
                return iTelephony.resetModemConfig(getSlotIndex());
            }
            return false;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "nvResetConfig RemoteException", e);
            return false;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "nvResetConfig NPE", e2);
            return false;
        }
    }

    @android.annotation.SystemApi
    public boolean resetRadioConfig() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.resetModemConfig(getSlotIndex());
            }
            return false;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "resetRadioConfig RemoteException", e);
            return false;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "resetRadioConfig NPE", e2);
            return false;
        }
    }

    @android.annotation.SystemApi
    public boolean rebootRadio() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.rebootModem(getSlotIndex());
            }
            return false;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "rebootRadio RemoteException", e);
            return false;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "rebootRadio NPE", e2);
            return false;
        }
    }

    public void rebootModem() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new java.lang.IllegalStateException("telephony service is null.");
            }
            iTelephony.rebootModem(getSlotIndex());
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "rebootRadio RemoteException", e);
            throw e.rethrowAsRuntimeException();
        }
    }

    public int getSubscriptionId() {
        return getSubId();
    }

    private int getSubId() {
        if (android.telephony.SubscriptionManager.isUsableSubIdValue(this.mSubId)) {
            return this.mSubId;
        }
        return android.telephony.SubscriptionManager.getDefaultSubscriptionId();
    }

    private int getSubId(int i) {
        if (android.telephony.SubscriptionManager.isUsableSubIdValue(this.mSubId)) {
            return this.mSubId;
        }
        return i;
    }

    private int getPhoneId() {
        return android.telephony.SubscriptionManager.getPhoneId(getSubId());
    }

    private int getPhoneId(int i) {
        return android.telephony.SubscriptionManager.getPhoneId(getSubId(i));
    }

    public int getSlotIndex() {
        int slotIndex = android.telephony.SubscriptionManager.getSlotIndex(getSubId());
        if (slotIndex == -1) {
            return Integer.MAX_VALUE;
        }
        return slotIndex;
    }

    @android.annotation.SystemApi
    public void requestNumberVerification(android.telephony.PhoneNumberRange phoneNumberRange, long j, final java.util.concurrent.Executor executor, final android.telephony.NumberVerificationCallback numberVerificationCallback) {
        if (executor == null) {
            throw new java.lang.NullPointerException("Executor must be non-null");
        }
        if (numberVerificationCallback == null) {
            throw new java.lang.NullPointerException("Callback must be non-null");
        }
        android.telephony.TelephonyManager.AnonymousClass6 anonymousClass6 = new android.telephony.TelephonyManager.AnonymousClass6(executor, numberVerificationCallback);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                if (android.compat.Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new java.lang.IllegalStateException("Telephony is null");
                }
            } else {
                iTelephony.requestNumberVerification(phoneNumberRange, j, anonymousClass6, getOpPackageName());
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "requestNumberVerification RemoteException", e);
            runOnBackgroundThread(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.NumberVerificationCallback.this.onVerificationFailed(0);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$6, reason: invalid class name */
    class AnonymousClass6 extends com.android.internal.telephony.INumberVerificationCallback.Stub {
        final /* synthetic */ android.telephony.NumberVerificationCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass6(java.util.concurrent.Executor executor, android.telephony.NumberVerificationCallback numberVerificationCallback) {
            this.val$executor = executor;
            this.val$callback = numberVerificationCallback;
        }

        @Override // com.android.internal.telephony.INumberVerificationCallback
        public void onCallReceived(final java.lang.String str) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.NumberVerificationCallback numberVerificationCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$6$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.NumberVerificationCallback.this.onCallReceived(str);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // com.android.internal.telephony.INumberVerificationCallback
        public void onVerificationFailed(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.telephony.NumberVerificationCallback numberVerificationCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$6$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.NumberVerificationCallback.this.onVerificationFailed(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private static <T> java.util.List<T> updateTelephonyProperty(java.util.List<T> list, int i, T t) {
        java.util.ArrayList arrayList = new java.util.ArrayList(list);
        while (arrayList.size() <= i) {
            arrayList.add(null);
        }
        arrayList.set(i, t);
        return arrayList;
    }

    public static int getIntAtIndex(android.content.ContentResolver contentResolver, java.lang.String str, int i) throws android.provider.Settings.SettingNotFoundException {
        java.lang.String string = android.provider.Settings.Global.getString(contentResolver, str);
        if (string != null) {
            java.lang.String[] split = string.split(",");
            if (i >= 0 && i < split.length && split[i] != null) {
                try {
                    return java.lang.Integer.parseInt(split[i]);
                } catch (java.lang.NumberFormatException e) {
                }
            }
        }
        throw new android.provider.Settings.SettingNotFoundException(str);
    }

    public static boolean putIntAtIndex(android.content.ContentResolver contentResolver, java.lang.String str, int i, int i2) {
        java.lang.String[] strArr;
        java.lang.String str2;
        java.lang.String string = android.provider.Settings.Global.getString(contentResolver, str);
        if (i == Integer.MAX_VALUE) {
            throw new java.lang.IllegalArgumentException("putIntAtIndex index == MAX_VALUE index=" + i);
        }
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("putIntAtIndex index < 0 index=" + i);
        }
        if (string == null) {
            strArr = null;
        } else {
            strArr = string.split(",");
        }
        java.lang.String str3 = "";
        for (int i3 = 0; i3 < i; i3++) {
            if (strArr == null || i3 >= strArr.length) {
                str2 = "";
            } else {
                str2 = strArr[i3];
            }
            str3 = str3 + str2 + ",";
        }
        java.lang.String str4 = str3 + i2;
        if (strArr != null) {
            while (true) {
                i++;
                if (i >= strArr.length) {
                    break;
                }
                str4 = str4 + "," + strArr[i];
            }
        }
        return android.provider.Settings.Global.putString(contentResolver, str, str4);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0023  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static java.lang.String getTelephonyProperty(int i, java.lang.String str, java.lang.String str2) {
        java.lang.String str3;
        java.lang.String str4 = android.os.SystemProperties.get(str);
        if (str4 != null && str4.length() > 0) {
            java.lang.String[] split = str4.split(",");
            if (i >= 0 && i < split.length && split[i] != null) {
                str3 = split[i];
                return str3 != null ? str2 : str3;
            }
        }
        str3 = null;
        if (str3 != null) {
        }
    }

    private static <T> T getTelephonyProperty(int i, java.util.List<T> list, T t) {
        T t2 = (i < 0 || i >= list.size()) ? null : list.get(i);
        return t2 != null ? t2 : t;
    }

    public static java.lang.String getTelephonyProperty(java.lang.String str, java.lang.String str2) {
        java.lang.String str3 = android.os.SystemProperties.get(str);
        return android.text.TextUtils.isEmpty(str3) ? str2 : str3;
    }

    public int getSimCount() {
        return getPhoneCount();
    }

    @android.annotation.SystemApi
    public java.lang.String getIsimIst() {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getIsimIst(getSubId());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String[] getIsimPcscf() {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getIsimPcscf(getSubId());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String getIccAuthentication(int i, int i2, java.lang.String str) {
        return getIccAuthentication(getSubId(), i, i2, str);
    }

    public java.lang.String getIccAuthentication(int i, int i2, int i3, java.lang.String str) {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                return null;
            }
            return subscriberInfoService.getIccSimChallengeResponse(i, i2, i3, str, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public java.lang.String[] getForbiddenPlmns() {
        return getForbiddenPlmns(getSubId(), 2);
    }

    public java.lang.String[] getForbiddenPlmns(int i, int i2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getForbiddenPlmns(i, i2, this.mContext.getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    public int setForbiddenPlmns(java.util.List<java.lang.String> list) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return -1;
            }
            return iTelephony.setForbiddenPlmns(getSubId(), 2, list, getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setForbiddenPlmns RemoteException: " + e.getMessage());
            return -1;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setForbiddenPlmns NullPointerException: " + e2.getMessage());
            return -1;
        }
    }

    public java.lang.String getSimServiceTable(int i) {
        try {
            com.android.internal.telephony.IPhoneSubInfo subscriberInfoService = getSubscriberInfoService();
            if (subscriberInfoService == null) {
                com.android.telephony.Rlog.e(TAG, "getSimServiceTable(): IPhoneSubInfo is null");
                return null;
            }
            if (i == 5) {
                return subscriberInfoService.getIsimIst(getSubId());
            }
            if (i == 2) {
                return subscriberInfoService.getSimServiceTable(getSubId(), 2);
            }
            return null;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getSimServiceTable(): RemoteException=" + e.getMessage());
            return null;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getSimServiceTable(): NullPointerException=" + e2.getMessage());
            return null;
        }
    }

    @android.annotation.SystemApi
    public void resetIms(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.resetIms(i);
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "toggleImsOnOff, RemoteException: " + e.getMessage());
        }
    }

    public void enableIms(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.enableIms(i);
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "enableIms, RemoteException: " + e.getMessage());
        }
    }

    public void disableIms(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.disableIms(i);
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "disableIms, RemoteException: " + e.getMessage());
        }
    }

    public android.telephony.ims.aidl.IImsRegistration getImsRegistration(int i, int i2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getImsRegistration(i, i2);
            }
            return null;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getImsRegistration, RemoteException: " + e.getMessage());
            return null;
        }
    }

    public android.telephony.ims.aidl.IImsConfig getImsConfig(int i, int i2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getImsConfig(i, i2);
            }
            return null;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getImsRegistration, RemoteException: " + e.getMessage());
            return null;
        }
    }

    public void setImsRegistrationState(boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setImsRegistrationState(z);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    @java.lang.Deprecated
    public int getPreferredNetworkType(int i) {
        return android.telephony.RadioAccessFamily.getNetworkTypeFromRaf((int) getAllowedNetworkTypesBitmask());
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public long getPreferredNetworkTypeBitmask() {
        return getAllowedNetworkTypesBitmask();
    }

    @android.annotation.SystemApi
    public long getAllowedNetworkTypesBitmask() {
        try {
            if (getITelephony() != null) {
                return r0.getAllowedNetworkTypesBitmask(getSubId());
            }
            return 0L;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getAllowedNetworkTypesBitmask RemoteException", e);
            return 0L;
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public long getAllowedNetworkTypes() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getAllowedNetworkTypesForReason(getSubId(), 2);
            }
            return -1L;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getAllowedNetworkTypes RemoteException", e);
            return -1L;
        }
    }

    public void setNetworkSelectionModeAutomatic() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setNetworkSelectionModeAutomatic(getSubId());
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setNetworkSelectionModeAutomatic RemoteException", e);
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setNetworkSelectionModeAutomatic NPE", e2);
        }
    }

    public com.android.internal.telephony.CellNetworkScanResult getAvailableNetworks() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCellNetworkScanResults(getSubId(), getOpPackageName(), getAttributionTag());
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getAvailableNetworks RemoteException", e);
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getAvailableNetworks NPE", e2);
        }
        return new com.android.internal.telephony.CellNetworkScanResult(4, (java.util.List<com.android.internal.telephony.OperatorInfo>) null);
    }

    public android.telephony.NetworkScan requestNetworkScan(android.telephony.NetworkScanRequest networkScanRequest, java.util.concurrent.Executor executor, android.telephony.TelephonyScanManager.NetworkScanCallback networkScanCallback) {
        return requestNetworkScan(2, networkScanRequest, executor, networkScanCallback);
    }

    public android.telephony.NetworkScan requestNetworkScan(int i, android.telephony.NetworkScanRequest networkScanRequest, java.util.concurrent.Executor executor, android.telephony.TelephonyScanManager.NetworkScanCallback networkScanCallback) {
        synchronized (sCacheLock) {
            if (this.mTelephonyScanManager == null) {
                this.mTelephonyScanManager = new android.telephony.TelephonyScanManager();
            }
        }
        return this.mTelephonyScanManager.requestNetworkScan(getSubId(), i != 2, networkScanRequest, executor, networkScanCallback, getOpPackageName(), getAttributionTag());
    }

    @java.lang.Deprecated
    public android.telephony.NetworkScan requestNetworkScan(android.telephony.NetworkScanRequest networkScanRequest, android.telephony.TelephonyScanManager.NetworkScanCallback networkScanCallback) {
        return requestNetworkScan(networkScanRequest, android.os.AsyncTask.SERIAL_EXECUTOR, networkScanCallback);
    }

    public boolean setNetworkSelectionModeManual(java.lang.String str, boolean z) {
        return setNetworkSelectionModeManual(new com.android.internal.telephony.OperatorInfo("", "", str), z);
    }

    public boolean setNetworkSelectionModeManual(java.lang.String str, boolean z, int i) {
        return setNetworkSelectionModeManual(new com.android.internal.telephony.OperatorInfo("", "", str, i), z);
    }

    public boolean setNetworkSelectionModeManual(com.android.internal.telephony.OperatorInfo operatorInfo, boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setNetworkSelectionModeManual(getSubId(), operatorInfo, z);
            }
            return false;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setNetworkSelectionModeManual RemoteException", e);
            return false;
        }
    }

    public int getNetworkSelectionMode() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return 0;
            }
            return iTelephony.getNetworkSelectionMode(getSubId());
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getNetworkSelectionMode RemoteException", e);
            return 0;
        }
    }

    public java.lang.String getManualNetworkSelectionPlmn() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null && isManualNetworkSelectionAllowed()) {
                return iTelephony.getManualNetworkSelectionPlmn(getSubId());
            }
            return "";
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getManualNetworkSelectionPlmn RemoteException", e);
            return "";
        }
    }

    @android.annotation.SystemApi
    public boolean isInEmergencySmsMode() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isInEmergencySmsMode();
            }
            return false;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "isInEmergencySmsMode RemoteException", e);
            return false;
        }
    }

    @java.lang.Deprecated
    public boolean setPreferredNetworkType(int i, int i2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setAllowedNetworkTypesForReason(i, 0, android.telephony.RadioAccessFamily.getRafFromNetworkType(i2));
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setPreferredNetworkType RemoteException", e);
        }
        return false;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean setPreferredNetworkTypeBitmask(long j) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setAllowedNetworkTypesForReason(getSubId(), 0, checkNetworkTypeBitmask(j));
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setPreferredNetworkTypeBitmask RemoteException", e);
        }
        return false;
    }

    private long checkNetworkTypeBitmask(long j) {
        if ((j & 262144) != 0) {
            return (j ^ 262144) | 4096;
        }
        return j;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean setAllowedNetworkTypes(long j) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setAllowedNetworkTypesForReason(getSubId(), 2, checkNetworkTypeBitmask(j));
            }
            return false;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setAllowedNetworkTypes RemoteException", e);
            return false;
        }
    }

    public void setAllowedNetworkTypesForReason(int i, long j) {
        if (!isValidAllowedNetworkTypesReason(i)) {
            throw new java.lang.IllegalArgumentException("invalid AllowedNetworkTypesReason.");
        }
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setAllowedNetworkTypesForReason(getSubId(), i, checkNetworkTypeBitmask(j));
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setAllowedNetworkTypesForReason RemoteException", e);
            e.rethrowFromSystemServer();
        }
    }

    public long getAllowedNetworkTypesForReason(int i) {
        if (!isValidAllowedNetworkTypesReason(i)) {
            throw new java.lang.IllegalArgumentException("invalid AllowedNetworkTypesReason.");
        }
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getAllowedNetworkTypesForReason(getSubId(), i);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getAllowedNetworkTypesForReason RemoteException", e);
            e.rethrowFromSystemServer();
            return -1L;
        }
    }

    public static boolean isValidAllowedNetworkTypesReason(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }

    public static long getAllNetworkTypesBitmask() {
        return 916479L;
    }

    public static java.lang.String convertNetworkTypeBitmaskToString(final long j) {
        java.lang.String str = (java.lang.String) java.util.stream.IntStream.rangeClosed(1, 20).filter(new java.util.function.IntPredicate() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda2
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return android.telephony.TelephonyManager.lambda$convertNetworkTypeBitmaskToString$11(j, i);
            }
        }).mapToObj(new java.util.function.IntFunction() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda3
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i) {
                java.lang.String networkTypeName;
                networkTypeName = android.telephony.TelephonyManager.getNetworkTypeName(i);
                return networkTypeName;
            }
        }).collect(java.util.stream.Collectors.joining(android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER));
        return android.text.TextUtils.isEmpty(str) ? "UNKNOWN" : str;
    }

    static /* synthetic */ boolean lambda$convertNetworkTypeBitmaskToString$11(long j, int i) {
        return (j & getBitMaskForNetworkType(i)) == getBitMaskForNetworkType(i);
    }

    public boolean setPreferredNetworkTypeToGlobal() {
        return setPreferredNetworkTypeToGlobal(getSubId());
    }

    public boolean setPreferredNetworkTypeToGlobal(int i) {
        return setPreferredNetworkType(i, 27);
    }

    @android.annotation.SystemApi
    public boolean isTetheringApnRequired() {
        return isTetheringApnRequired(getSubId(android.telephony.SubscriptionManager.getActiveDataSubscriptionId()));
    }

    public boolean isTetheringApnRequired(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isTetheringApnRequiredForSubscriber(i);
            }
            return false;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "hasMatchedTetherApnSetting RemoteException", e);
            return false;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "hasMatchedTetherApnSetting NPE", e2);
            return false;
        }
    }

    public boolean hasCarrierPrivileges() {
        return hasCarrierPrivileges(getSubId());
    }

    public boolean hasCarrierPrivileges(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCarrierPrivilegeStatus(i) == 1;
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "hasCarrierPrivileges RemoteException", e);
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "hasCarrierPrivileges NPE", e2);
        }
        return false;
    }

    public boolean setOperatorBrandOverride(java.lang.String str) {
        return setOperatorBrandOverride(getSubId(), str);
    }

    public boolean setOperatorBrandOverride(int i, java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setOperatorBrandOverride(i, str);
            }
            return false;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setOperatorBrandOverride RemoteException", e);
            return false;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setOperatorBrandOverride NPE", e2);
            return false;
        }
    }

    public boolean setRoamingOverride(java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, java.util.List<java.lang.String> list3, java.util.List<java.lang.String> list4) {
        return setRoamingOverride(getSubId(), list, list2, list3, list4);
    }

    public boolean setRoamingOverride(int i, java.util.List<java.lang.String> list, java.util.List<java.lang.String> list2, java.util.List<java.lang.String> list3, java.util.List<java.lang.String> list4) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setRoamingOverride(i, list, list2, list3, list4);
            }
            return false;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setRoamingOverride RemoteException", e);
            return false;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setRoamingOverride NPE", e2);
            return false;
        }
    }

    @android.annotation.SystemApi
    public java.lang.String getCdmaMdn() {
        return getCdmaMdn(getSubId());
    }

    @android.annotation.SystemApi
    public java.lang.String getCdmaMdn(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getCdmaMdn(i);
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @android.annotation.SystemApi
    public java.lang.String getCdmaMin() {
        return getCdmaMin(getSubId());
    }

    @android.annotation.SystemApi
    public java.lang.String getCdmaMin(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getCdmaMin(i);
        } catch (android.os.RemoteException e) {
            return null;
        } catch (java.lang.NullPointerException e2) {
            return null;
        }
    }

    @android.annotation.SystemApi
    public int checkCarrierPrivilegesForPackage(java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.checkCarrierPrivilegesForPackage(getSubId(), str);
            }
            return 0;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "checkCarrierPrivilegesForPackage RemoteException", e);
            return 0;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "checkCarrierPrivilegesForPackage NPE", e2);
            return 0;
        }
    }

    @android.annotation.SystemApi
    public int checkCarrierPrivilegesForPackageAnyPhone(java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.checkCarrierPrivilegesForPackageAnyPhone(str);
            }
            return 0;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "checkCarrierPrivilegesForPackageAnyPhone RemoteException", e);
            return 0;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "checkCarrierPrivilegesForPackageAnyPhone NPE", e2);
            return 0;
        }
    }

    @android.annotation.SystemApi
    public java.util.List<java.lang.String> getCarrierPackageNamesForIntent(android.content.Intent intent) {
        return getCarrierPackageNamesForIntentAndPhone(intent, getPhoneId());
    }

    @android.annotation.SystemApi
    public java.util.List<java.lang.String> getCarrierPackageNamesForIntentAndPhone(android.content.Intent intent, int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCarrierPackageNamesForIntentAndPhone(intent, i);
            }
            return null;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCarrierPackageNamesForIntentAndPhone RemoteException", e);
            return null;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getCarrierPackageNamesForIntentAndPhone NPE", e2);
            return null;
        }
    }

    @android.annotation.SystemApi
    public java.lang.String getCarrierServicePackageName() {
        return getCarrierServicePackageNameForLogicalSlot(getPhoneId());
    }

    @android.annotation.SystemApi
    public java.lang.String getCarrierServicePackageNameForLogicalSlot(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCarrierServicePackageNameForLogicalSlot(i);
            }
            return null;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCarrierServicePackageNameForLogicalSlot RemoteException", e);
            return null;
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getCarrierServicePackageNameForLogicalSlot NPE", e2);
            return null;
        }
    }

    public java.util.List<java.lang.String> getPackagesWithCarrierPrivileges() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getPackagesWithCarrierPrivileges(getPhoneId());
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getPackagesWithCarrierPrivileges RemoteException", e);
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getPackagesWithCarrierPrivileges NPE", e2);
        }
        return java.util.Collections.EMPTY_LIST;
    }

    @android.annotation.SystemApi
    public java.util.List<java.lang.String> getCarrierPrivilegedPackagesForAllActiveSubscriptions() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getPackagesWithCarrierPrivilegesForAllPhones();
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCarrierPrivilegedPackagesForAllActiveSubscriptions RemoteException", e);
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getCarrierPrivilegedPackagesForAllActiveSubscriptions NPE", e2);
        }
        return java.util.Collections.EMPTY_LIST;
    }

    public void setCallComposerStatus(int i) {
        if (com.android.server.telecom.flags.Flags.businessCallComposer()) {
            if (i > 2 || i < 0) {
                throw new java.lang.IllegalArgumentException("requested status is invalid");
            }
        } else if (i > 1 || i < 0) {
            throw new java.lang.IllegalArgumentException("requested status is invalid");
        }
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setCallComposerStatus(getSubId(), i);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#setCallComposerStatus", e);
            e.rethrowFromSystemServer();
        }
    }

    public int getCallComposerStatus() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCallComposerStatus(getSubId());
            }
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getCallComposerStatus", e);
            e.rethrowFromSystemServer();
            return 0;
        }
    }

    @android.annotation.SystemApi
    public void dial(java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.dial(str);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#dial", e);
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void call(java.lang.String str, java.lang.String str2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.call(str, str2);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#call", e);
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean endCall() {
        return false;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void answerRingingCall() {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void silenceRinger() {
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean isOffhook() {
        return ((android.telecom.TelecomManager) this.mContext.getSystemService(android.content.Context.TELECOM_SERVICE)).isInCall();
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean isRinging() {
        return ((android.telecom.TelecomManager) this.mContext.getSystemService(android.content.Context.TELECOM_SERVICE)).isRinging();
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean isIdle() {
        return !((android.telecom.TelecomManager) this.mContext.getSystemService(android.content.Context.TELECOM_SERVICE)).isInCall();
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean isRadioOn() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isRadioOnWithFeature(getOpPackageName(), getAttributionTag());
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#isRadioOn", e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public boolean supplyPin(java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.supplyPinForSubscriber(getSubId(), str);
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#supplyPinForSubscriber", e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public boolean supplyPuk(java.lang.String str, java.lang.String str2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.supplyPukForSubscriber(getSubId(), str, str2);
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#supplyPukForSubscriber", e);
            return false;
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public int[] supplyPinReportResult(java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.supplyPinReportResultForSubscriber(getSubId(), str);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#supplyPinReportResultForSubscriber", e);
        }
        return new int[0];
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public int[] supplyPukReportResult(java.lang.String str, java.lang.String str2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.supplyPukReportResultForSubscriber(getSubId(), str, str2);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#supplyPukReportResultForSubscriber", e);
        }
        return new int[0];
    }

    @android.annotation.SystemApi
    public android.telephony.PinResult supplyIccLockPin(java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                int[] supplyPinReportResultForSubscriber = iTelephony.supplyPinReportResultForSubscriber(getSubId(), str);
                return new android.telephony.PinResult(supplyPinReportResultForSubscriber[0], supplyPinReportResultForSubscriber[1]);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#supplyIccLockPin", e);
            e.rethrowFromSystemServer();
            return android.telephony.PinResult.getDefaultFailedResult();
        }
    }

    @android.annotation.SystemApi
    public android.telephony.PinResult supplyIccLockPuk(java.lang.String str, java.lang.String str2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                int[] supplyPukReportResultForSubscriber = iTelephony.supplyPukReportResultForSubscriber(getSubId(), str, str2);
                return new android.telephony.PinResult(supplyPukReportResultForSubscriber[0], supplyPukReportResultForSubscriber[1]);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#supplyIccLockPuk", e);
            e.rethrowFromSystemServer();
            return android.telephony.PinResult.getDefaultFailedResult();
        }
    }

    public static abstract class UssdResponseCallback {
        public void onReceiveUssdResponse(android.telephony.TelephonyManager telephonyManager, java.lang.String str, java.lang.CharSequence charSequence) {
        }

        public void onReceiveUssdResponseFailed(android.telephony.TelephonyManager telephonyManager, java.lang.String str, int i) {
        }
    }

    public void sendUssdRequest(java.lang.String str, final android.telephony.TelephonyManager.UssdResponseCallback ussdResponseCallback, android.os.Handler handler) {
        com.android.internal.util.Preconditions.checkNotNull(ussdResponseCallback, "UssdResponseCallback cannot be null.");
        android.os.ResultReceiver resultReceiver = new android.os.ResultReceiver(handler) { // from class: android.telephony.TelephonyManager.7
            @Override // android.os.ResultReceiver
            protected void onReceiveResult(int i, android.os.Bundle bundle) {
                com.android.telephony.Rlog.d(android.telephony.TelephonyManager.TAG, "USSD:" + i);
                com.android.internal.util.Preconditions.checkNotNull(bundle, "ussdResponse cannot be null.");
                android.telephony.UssdResponse ussdResponse = (android.telephony.UssdResponse) bundle.getParcelable(android.telephony.TelephonyManager.USSD_RESPONSE, android.telephony.UssdResponse.class);
                if (i == 100) {
                    ussdResponseCallback.onReceiveUssdResponse(this, ussdResponse.getUssdRequest(), ussdResponse.getReturnMessage());
                } else {
                    ussdResponseCallback.onReceiveUssdResponseFailed(this, ussdResponse.getUssdRequest(), i);
                }
            }
        };
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.handleUssdRequest(getSubId(), str, resultReceiver);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#sendUSSDCode", e);
            android.telephony.UssdResponse ussdResponse = new android.telephony.UssdResponse(str, "");
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putParcelable(USSD_RESPONSE, ussdResponse);
            resultReceiver.send(-2, bundle);
        }
    }

    public boolean isConcurrentVoiceAndDataSupported() {
        boolean z = false;
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                z = iTelephony.isConcurrentVoiceAndDataAllowed(getSubId());
            }
            return z;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#isConcurrentVoiceAndDataAllowed", e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public boolean handlePinMmi(java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.handlePinMmi(str);
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#handlePinMmi", e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public boolean handlePinMmiForSubscriber(int i, java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.handlePinMmiForSubscriber(i, str);
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#handlePinMmi", e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public void toggleRadioOnOff() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.toggleRadioOnOff();
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#toggleRadioOnOff", e);
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean setRadio(boolean z) {
        boolean z2 = false;
        try {
            if (z) {
                clearRadioPowerOffForReason(0);
            } else {
                requestRadioPowerOffForReason(0);
            }
            z2 = true;
            return true;
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Error calling " + (z ? "clearRadioPowerOffForReason" : "requestRadioPowerOffForReason"), e);
            return z2;
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean setRadioPower(boolean z) {
        boolean z2 = false;
        try {
            if (z) {
                clearRadioPowerOffForReason(0);
            } else {
                requestRadioPowerOffForReason(0);
            }
            z2 = true;
            return true;
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Error calling " + (z ? "clearRadioPowerOffForReason" : "requestRadioPowerOffForReason"), e);
            return z2;
        }
    }

    @android.annotation.SystemApi
    public void requestRadioPowerOffForReason(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (!iTelephony.requestRadioPowerOffForReason(getSubId(), i)) {
                    throw new java.lang.IllegalStateException("Telephony service is not available.");
                }
                return;
            }
            throw new java.lang.IllegalStateException("Telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#requestRadioPowerOffForReason", e);
            e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public void clearRadioPowerOffForReason(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (!iTelephony.clearRadioPowerOffForReason(getSubId(), i)) {
                    throw new java.lang.IllegalStateException("Telephony service is not available.");
                }
                return;
            }
            throw new java.lang.IllegalStateException("Telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#clearRadioPowerOffForReason", e);
            e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public java.util.Set<java.lang.Integer> getRadioPowerOffReasons() {
        com.android.internal.telephony.ITelephony iTelephony;
        java.util.HashSet hashSet = new java.util.HashSet();
        try {
            iTelephony = getITelephony();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getRadioPowerOffReasons", e);
            e.rethrowAsRuntimeException();
        }
        if (iTelephony != null) {
            hashSet.addAll(iTelephony.getRadioPowerOffReasons(getSubId(), this.mContext.getOpPackageName(), this.mContext.getAttributionTag()));
            return hashSet;
        }
        throw new java.lang.IllegalStateException("Telephony service is null.");
    }

    @android.annotation.SystemApi
    public void shutdownAllRadios() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.shutdownMobileRadios();
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#shutdownAllRadios", e);
            e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public boolean isAnyRadioPoweredOn() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.needMobileRadioShutdown();
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#isAnyRadioPoweredOn", e);
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    @android.annotation.SystemApi
    public int getRadioPowerState() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getRadioPowerState(getSlotIndex(), this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            return 2;
        } catch (android.os.RemoteException e) {
            return 2;
        }
    }

    @android.annotation.SystemApi
    public void updateServiceLocation() {
        android.util.Log.e(TAG, "Do not call TelephonyManager#updateServiceLocation()");
    }

    @android.annotation.SystemApi
    public boolean enableDataConnectivity() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.enableDataConnectivity(getOpPackageName());
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#enableDataConnectivity", e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public boolean disableDataConnectivity() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.disableDataConnectivity(getOpPackageName());
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#disableDataConnectivity", e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public boolean isDataConnectivityPossible() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isDataConnectivityPossible(getSubId(android.telephony.SubscriptionManager.getActiveDataSubscriptionId()));
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#isDataAllowed", e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public boolean needsOtaServiceProvisioning() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.needsOtaServiceProvisioning();
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#needsOtaServiceProvisioning", e);
            return false;
        }
    }

    public java.lang.String getMobileProvisioningUrl() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getMobileProvisioningUrl();
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#getMobileProvisioningUrl RemoteException" + e);
            return null;
        }
    }

    @java.lang.Deprecated
    public void setDataEnabled(boolean z) {
        setDataEnabled(getSubId(android.telephony.SubscriptionManager.getDefaultDataSubscriptionId()), z);
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setDataEnabled(int i, boolean z) {
        try {
            setDataEnabledForReason(i, 0, z);
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(TAG, "Error calling setDataEnabledForReason e:" + e);
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean getDataEnabled() {
        return isDataEnabled();
    }

    public boolean isDataEnabled() {
        try {
            return isDataEnabledForReason(0);
        } catch (java.lang.IllegalStateException e) {
            android.util.Log.e(TAG, "Error calling #isDataEnabled, returning default (false).", e);
            return false;
        }
    }

    public boolean isDataRoamingEnabled() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return false;
            }
            return iTelephony.isDataRoamingEnabled(getSubId(android.telephony.SubscriptionManager.getDefaultDataSubscriptionId()));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#isDataRoamingEnabled", e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public int getCdmaRoamingMode() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCdmaRoamingMode(getSubId());
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getCdmaRoamingMode", e);
            e.rethrowFromSystemServer();
            return -1;
        }
    }

    @android.annotation.SystemApi
    public void setCdmaRoamingMode(int i) {
        if (getPhoneType() != 2) {
            throw new java.lang.IllegalStateException("Phone does not support CDMA.");
        }
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (!iTelephony.setCdmaRoamingMode(getSubId(), i)) {
                    throw new java.lang.IllegalStateException("radio is unavailable.");
                }
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#setCdmaRoamingMode", e);
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getCdmaSubscriptionMode() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCdmaSubscriptionMode(getSubId());
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getCdmaSubscriptionMode", e);
            e.rethrowFromSystemServer();
            return 0;
        }
    }

    @android.annotation.SystemApi
    public void setCdmaSubscriptionMode(int i) {
        if (getPhoneType() != 2) {
            throw new java.lang.IllegalStateException("Phone does not support CDMA.");
        }
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                if (!iTelephony.setCdmaSubscriptionMode(getSubId(), i)) {
                    throw new java.lang.IllegalStateException("radio is unavailable.");
                }
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#setCdmaSubscriptionMode", e);
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setDataRoamingEnabled(boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setDataRoamingEnabled(getSubId(android.telephony.SubscriptionManager.getDefaultDataSubscriptionId()), z);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#setDataRoamingEnabled", e);
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean getDataEnabled(int i) {
        try {
            return isDataEnabledForReason(i, 0);
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(TAG, "Error calling isDataEnabledForReason e:" + e);
            return false;
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void enableVideoCalling(boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.enableVideoCalling(z);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#enableVideoCalling", e);
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean isVideoCallingEnabled() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isVideoCallingEnabled(getOpPackageName(), getAttributionTag());
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#isVideoCallingEnabled", e);
            return false;
        }
    }

    public boolean canChangeDtmfToneLength() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.canChangeDtmfToneLength(this.mSubId, getOpPackageName(), getAttributionTag());
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#canChangeDtmfToneLength", e);
            return false;
        } catch (java.lang.SecurityException e2) {
            android.util.Log.e(TAG, "Permission error calling ITelephony#canChangeDtmfToneLength", e2);
            return false;
        }
    }

    public boolean isWorldPhone() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isWorldPhone(this.mSubId, getOpPackageName(), getAttributionTag());
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#isWorldPhone", e);
            return false;
        } catch (java.lang.SecurityException e2) {
            android.util.Log.e(TAG, "Permission error calling ITelephony#isWorldPhone", e2);
            return false;
        }
    }

    @java.lang.Deprecated
    public boolean isTtyModeSupported() {
        android.telecom.TelecomManager telecomManager;
        try {
            if (this.mContext == null) {
                telecomManager = null;
            } else {
                telecomManager = (android.telecom.TelecomManager) this.mContext.getSystemService(android.telecom.TelecomManager.class);
            }
            if (telecomManager != null) {
                return telecomManager.isTtySupported();
            }
            return false;
        } catch (java.lang.SecurityException e) {
            android.util.Log.e(TAG, "Permission error calling TelecomManager#isTtySupported", e);
            return false;
        }
    }

    public boolean isRttSupported() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isRttSupported(this.mSubId);
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#isRttSupported", e);
            return false;
        } catch (java.lang.SecurityException e2) {
            android.util.Log.e(TAG, "Permission error calling ITelephony#isWorldPhone", e2);
            return false;
        }
    }

    public boolean isHearingAidCompatibilitySupported() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isHearingAidCompatibilitySupported();
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#isHearingAidCompatibilitySupported", e);
            return false;
        } catch (java.lang.SecurityException e2) {
            android.util.Log.e(TAG, "Permission error calling ITelephony#isHearingAidCompatibilitySupported", e2);
            return false;
        }
    }

    public boolean isImsRegistered(int i) {
        try {
            return getITelephony().isImsRegistered(i);
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            return false;
        }
    }

    public boolean isImsRegistered() {
        try {
            return getITelephony().isImsRegistered(getSubId());
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            return false;
        }
    }

    public boolean isVolteAvailable() {
        try {
            return getITelephony().isAvailable(getSubId(), 1, 0);
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            return false;
        }
    }

    public boolean isVideoTelephonyAvailable() {
        try {
            return getITelephony().isVideoTelephonyAvailable(getSubId());
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            return false;
        }
    }

    public boolean isWifiCallingAvailable() {
        try {
            return getITelephony().isWifiCallingAvailable(getSubId());
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            return false;
        }
    }

    public int getImsRegTechnologyForMmTel() {
        try {
            return getITelephony().getImsRegTechnologyForMmTel(getSubId());
        } catch (android.os.RemoteException e) {
            return -1;
        }
    }

    public void setSimOperatorNumericForPhone(int i, java.lang.String str) {
        if (android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            android.sysprop.TelephonyProperties.icc_operator_numeric(updateTelephonyProperty(android.sysprop.TelephonyProperties.icc_operator_numeric(), i, str));
        }
    }

    public void setSimOperatorNameForPhone(int i, java.lang.String str) {
        if (android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            android.sysprop.TelephonyProperties.icc_operator_alpha(updateTelephonyProperty(android.sysprop.TelephonyProperties.icc_operator_alpha(), i, str));
        }
    }

    public void setSimCountryIso(java.lang.String str) {
        setSimCountryIsoForPhone(getPhoneId(), str);
    }

    public void setSimCountryIsoForPhone(int i, java.lang.String str) {
        if (android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            android.sysprop.TelephonyProperties.icc_operator_iso_country(updateTelephonyProperty(android.sysprop.TelephonyProperties.icc_operator_iso_country(), i, str));
        }
    }

    public void setSimState(java.lang.String str) {
        setSimStateForPhone(getPhoneId(), str);
    }

    public void setSimStateForPhone(int i, java.lang.String str) {
        if (android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            android.sysprop.TelephonyProperties.sim_state(updateTelephonyProperty(android.sysprop.TelephonyProperties.sim_state(), i, str));
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setSimPowerState(int i) {
        setSimPowerStateForSlot(getSlotIndex(), i);
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setSimPowerStateForSlot(int i, int i2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setSimPowerStateForSlot(i, i2);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#setSimPowerStateForSlot", e);
        } catch (java.lang.SecurityException e2) {
            android.util.Log.e(TAG, "Permission error calling ITelephony#setSimPowerStateForSlot", e2);
        }
    }

    @android.annotation.SystemApi
    public void setSimPowerState(int i, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        setSimPowerStateForSlot(getSlotIndex(), i, executor, consumer);
    }

    @android.annotation.SystemApi
    public void setSimPowerStateForSlot(int i, int i2, final java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        if (i2 != 0 && i2 != 1 && i2 != 2) {
            throw new java.lang.IllegalArgumentException("requested SIM state is invalid");
        }
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new java.lang.IllegalStateException("Telephony is null.");
            }
            android.telephony.TelephonyManager.AnonymousClass8 anonymousClass8 = new android.telephony.TelephonyManager.AnonymousClass8(executor, consumer);
            if (iTelephony == null) {
                if (android.compat.Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new java.lang.IllegalStateException("Telephony is null");
                }
            } else {
                iTelephony.setSimPowerStateForSlotWithCallback(i, i2, anonymousClass8);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#setSimPowerStateForSlot", e);
            runOnBackgroundThread(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            r1.accept(2);
                        }
                    });
                }
            });
        } catch (java.lang.SecurityException e2) {
            android.util.Log.e(TAG, "Permission error calling ITelephony#setSimPowerStateForSlot", e2);
        }
    }

    /* renamed from: android.telephony.TelephonyManager$8, reason: invalid class name */
    class AnonymousClass8 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.function.Consumer val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass8(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$8$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$8$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    public void setBasebandVersionForPhone(int i, java.lang.String str) {
        if (android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            android.sysprop.TelephonyProperties.baseband_version(updateTelephonyProperty(android.sysprop.TelephonyProperties.baseband_version(), i, str));
        }
    }

    public java.lang.String getBasebandVersion() {
        return getBasebandVersionForPhone(getPhoneId());
    }

    public java.lang.String getBasebandVersionForPhone(int i) {
        return (java.lang.String) getTelephonyProperty(i, android.sysprop.TelephonyProperties.baseband_version(), "");
    }

    public void setPhoneType(int i, int i2) {
        if (android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            android.sysprop.TelephonyProperties.current_active_phone(updateTelephonyProperty(android.sysprop.TelephonyProperties.current_active_phone(), i, java.lang.Integer.valueOf(i2)));
        }
    }

    public java.lang.String getOtaSpNumberSchemaForPhone(int i, java.lang.String str) {
        if (android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            return (java.lang.String) getTelephonyProperty(i, android.sysprop.TelephonyProperties.otasp_num_schema(), str);
        }
        return str;
    }

    public boolean getSmsReceiveCapableForPhone(int i, boolean z) {
        if (android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            return ((java.lang.Boolean) getTelephonyProperty(i, android.sysprop.TelephonyProperties.sms_receive(), java.lang.Boolean.valueOf(z))).booleanValue();
        }
        return z;
    }

    public boolean getSmsSendCapableForPhone(int i, boolean z) {
        if (android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            return ((java.lang.Boolean) getTelephonyProperty(i, android.sysprop.TelephonyProperties.sms_send(), java.lang.Boolean.valueOf(z))).booleanValue();
        }
        return z;
    }

    @android.annotation.SystemApi
    public android.content.ComponentName getAndUpdateDefaultRespondViaMessageApplication() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getDefaultRespondViaMessageApplication(getSubId(), true);
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error in getAndUpdateDefaultRespondViaMessageApplication: " + e);
            return null;
        }
    }

    @android.annotation.SystemApi
    public android.content.ComponentName getDefaultRespondViaMessageApplication() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getDefaultRespondViaMessageApplication(getSubId(), false);
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error in getDefaultRespondViaMessageApplication: " + e);
            return null;
        }
    }

    public void setNetworkOperatorNameForPhone(int i, java.lang.String str) {
        if (android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            java.util.List updateTelephonyProperty = updateTelephonyProperty(android.sysprop.TelephonyProperties.operator_alpha(), i, str);
            try {
                android.sysprop.TelephonyProperties.operator_alpha(updateTelephonyProperty);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.e(TAG, "setNetworkOperatorNameForPhone: ", e);
                int size = updateTelephonyProperty.size();
                int i2 = (91 - size) / size;
                for (int i3 = 0; i3 < updateTelephonyProperty.size(); i3++) {
                    if (updateTelephonyProperty.get(i3) != null) {
                        updateTelephonyProperty.set(i3, android.text.TextUtils.truncateStringForUtf8Storage((java.lang.String) updateTelephonyProperty.get(i3), i2));
                    }
                }
                android.sysprop.TelephonyProperties.operator_alpha(updateTelephonyProperty);
                android.util.Log.e(TAG, "successfully truncated operator_alpha: " + updateTelephonyProperty);
            }
        }
    }

    public void setNetworkOperatorNumericForPhone(int i, java.lang.String str) {
        if (android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            android.sysprop.TelephonyProperties.operator_numeric(updateTelephonyProperty(android.sysprop.TelephonyProperties.operator_numeric(), i, str));
        }
    }

    public void setNetworkRoamingForPhone(int i, boolean z) {
        if (android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            android.sysprop.TelephonyProperties.operator_is_roaming(updateTelephonyProperty(android.sysprop.TelephonyProperties.operator_is_roaming(), i, java.lang.Boolean.valueOf(z)));
        }
    }

    public void setDataNetworkType(int i) {
        setDataNetworkTypeForPhone(getPhoneId(android.telephony.SubscriptionManager.getDefaultDataSubscriptionId()), i);
    }

    public void setDataNetworkTypeForPhone(int i, int i2) {
        if (android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            android.sysprop.TelephonyProperties.data_network_type(updateTelephonyProperty(android.sysprop.TelephonyProperties.data_network_type(), i, android.telephony.ServiceState.rilRadioTechnologyToString(i2)));
        }
    }

    public int getSubIdForPhoneAccount(android.telecom.PhoneAccount phoneAccount) {
        if (phoneAccount != null && phoneAccount.hasCapabilities(4)) {
            return getSubscriptionId(phoneAccount.getAccountHandle());
        }
        return -1;
    }

    public android.telecom.PhoneAccountHandle getPhoneAccountHandle() {
        return getPhoneAccountHandleForSubscriptionId(getSubId());
    }

    public android.telecom.PhoneAccountHandle getPhoneAccountHandleForSubscriptionId(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return null;
            }
            return iTelephony.getPhoneAccountHandleForSubscriptionId(i);
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public int getSubscriptionId(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        return this.mPhoneAccountHandleToSubIdCache.query(phoneAccountHandle).intValue();
    }

    public void factoryReset(int i) {
        try {
            android.util.Log.d(TAG, "factoryReset: subId=" + i);
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.factoryReset(i, getOpPackageName());
            }
        } catch (android.os.RemoteException e) {
        }
    }

    @android.annotation.SystemApi
    public void resetSettings() {
        try {
            android.util.Log.d(TAG, "resetSettings: subId=" + getSubId());
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.factoryReset(getSubId(), getOpPackageName());
            }
        } catch (android.os.RemoteException e) {
        }
    }

    @android.annotation.SystemApi
    public java.util.Locale getSimLocale() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                java.lang.String simLocaleForSubscriber = iTelephony.getSimLocaleForSubscriber(getSubId());
                if (!android.text.TextUtils.isEmpty(simLocaleForSubscriber)) {
                    return java.util.Locale.forLanguageTag(simLocaleForSubscriber);
                }
                return null;
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    @android.annotation.SystemApi
    public static class ModemActivityInfoException extends java.lang.Exception {
        public static final int ERROR_INVALID_INFO_RECEIVED = 2;
        public static final int ERROR_MODEM_RESPONSE_ERROR = 3;
        public static final int ERROR_PHONE_NOT_AVAILABLE = 1;
        public static final int ERROR_UNKNOWN = 0;
        private final int mErrorCode;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ModemActivityInfoError {
        }

        public ModemActivityInfoException(int i) {
            this.mErrorCode = i;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        @Override // java.lang.Throwable
        public java.lang.String toString() {
            switch (this.mErrorCode) {
                case 0:
                    return "ERROR_UNKNOWN";
                case 1:
                    return "ERROR_PHONE_NOT_AVAILABLE";
                case 2:
                    return "ERROR_INVALID_INFO_RECEIVED";
                case 3:
                    return "ERROR_MODEM_RESPONSE_ERROR";
                default:
                    return android.app.admin.DevicePolicyResources.UNDEFINED;
            }
        }
    }

    @android.annotation.SystemApi
    public void requestModemActivityInfo(java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<android.telephony.ModemActivityInfo, android.telephony.TelephonyManager.ModemActivityInfoException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        android.telephony.TelephonyManager.AnonymousClass9 anonymousClass9 = new android.telephony.TelephonyManager.AnonymousClass9(null, executor, outcomeReceiver);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.requestModemActivityInfo(anonymousClass9);
                return;
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getModemActivityInfo", e);
        }
        executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                android.os.OutcomeReceiver.this.onError(new android.telephony.TelephonyManager.ModemActivityInfoException(1));
            }
        });
    }

    /* renamed from: android.telephony.TelephonyManager$9, reason: invalid class name */
    class AnonymousClass9 extends android.os.ResultReceiver {
        final /* synthetic */ android.os.OutcomeReceiver val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass9(android.os.Handler handler, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(int i, android.os.Bundle bundle) {
            if (bundle == null) {
                android.util.Log.w(android.telephony.TelephonyManager.TAG, "requestModemActivityInfo: received null bundle");
                sendErrorToListener(0);
                return;
            }
            bundle.setDefusable(true);
            if (bundle.containsKey(android.telephony.TelephonyManager.EXCEPTION_RESULT_KEY)) {
                sendErrorToListener(bundle.getInt(android.telephony.TelephonyManager.EXCEPTION_RESULT_KEY));
                return;
            }
            if (!bundle.containsKey("controller_activity")) {
                android.util.Log.w(android.telephony.TelephonyManager.TAG, "requestModemActivityInfo: Bundle did not contain expected key");
                sendErrorToListener(0);
                return;
            }
            android.os.Parcelable parcelable = bundle.getParcelable("controller_activity");
            if (!(parcelable instanceof android.telephony.ModemActivityInfo)) {
                android.util.Log.w(android.telephony.TelephonyManager.TAG, "requestModemActivityInfo: Bundle contained something that wasn't a ModemActivityInfo.");
                sendErrorToListener(0);
                return;
            }
            android.telephony.ModemActivityInfo modemActivityInfo = (android.telephony.ModemActivityInfo) parcelable;
            if (!modemActivityInfo.isValid()) {
                android.util.Log.w(android.telephony.TelephonyManager.TAG, "requestModemActivityInfo: Received an invalid ModemActivityInfo");
                sendErrorToListener(2);
            } else {
                android.util.Log.d(android.telephony.TelephonyManager.TAG, "requestModemActivityInfo: Sending result to app: " + modemActivityInfo);
                sendResultToListener(modemActivityInfo);
            }
        }

        private void sendResultToListener(final android.telephony.ModemActivityInfo modemActivityInfo) {
            final java.util.concurrent.Executor executor = this.val$executor;
            final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$9$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$9$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onResult(r2);
                        }
                    });
                }
            });
        }

        private void sendErrorToListener(int i) {
            final android.telephony.TelephonyManager.ModemActivityInfoException modemActivityInfoException = new android.telephony.TelephonyManager.ModemActivityInfoException(i);
            final java.util.concurrent.Executor executor = this.val$executor;
            final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$9$$ExternalSyntheticLambda1
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$9$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.OutcomeReceiver.this.onError(r2);
                        }
                    });
                }
            });
        }
    }

    public android.telephony.ServiceState getServiceState() {
        return getServiceState(getLocationData());
    }

    public android.telephony.ServiceState getServiceState(int i) {
        return getServiceStateForSubscriber(getSubId(), i != 2, i == 0);
    }

    private android.telephony.ServiceState getServiceStateForSubscriber(int i, boolean z, boolean z2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getServiceStateForSubscriber(i, z, z2, getOpPackageName(), getAttributionTag());
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getServiceStateForSubscriber", e);
            return null;
        } catch (java.lang.NullPointerException e2) {
            android.telephony.AnomalyReporter.reportAnomaly(java.util.UUID.fromString("e2bed88e-def9-476e-bd71-3e572a8de6d1"), "getServiceStateForSubscriber " + i + " NPE");
            return null;
        }
    }

    public android.telephony.ServiceState getServiceStateForSubscriber(int i) {
        return getServiceStateForSubscriber(i, false, false);
    }

    public android.net.Uri getVoicemailRingtoneUri(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getVoicemailRingtoneUri(phoneAccountHandle);
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getVoicemailRingtoneUri", e);
            return null;
        }
    }

    @java.lang.Deprecated
    public void setVoicemailRingtoneUri(android.telecom.PhoneAccountHandle phoneAccountHandle, android.net.Uri uri) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setVoicemailRingtoneUri(getOpPackageName(), phoneAccountHandle, uri);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#setVoicemailRingtoneUri", e);
        }
    }

    public boolean isVoicemailVibrationEnabled(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isVoicemailVibrationEnabled(phoneAccountHandle);
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#isVoicemailVibrationEnabled", e);
            return false;
        }
    }

    @java.lang.Deprecated
    public void setVoicemailVibrationEnabled(android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setVoicemailVibrationEnabled(getOpPackageName(), phoneAccountHandle, z);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#isVoicemailVibrationEnabled", e);
        }
    }

    public int getSimCarrierId() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSubscriptionCarrierId(getSubId());
            }
            return -1;
        } catch (android.os.RemoteException e) {
            return -1;
        }
    }

    public java.lang.CharSequence getSimCarrierIdName() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSubscriptionCarrierName(getSubId());
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public int getSimSpecificCarrierId() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSubscriptionSpecificCarrierId(getSubId());
            }
            return -1;
        } catch (android.os.RemoteException e) {
            return -1;
        }
    }

    public java.lang.CharSequence getSimSpecificCarrierIdName() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSubscriptionSpecificCarrierName(getSubId());
            }
            return null;
        } catch (android.os.RemoteException e) {
            return null;
        }
    }

    public int getCarrierIdFromSimMccMnc() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCarrierIdFromMccMnc(getSlotIndex(), getSimOperator(), true);
            }
            return -1;
        } catch (android.os.RemoteException e) {
            return -1;
        }
    }

    public int getCarrierIdFromMccMnc(java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCarrierIdFromMccMnc(getSlotIndex(), str, false);
            }
            return -1;
        } catch (android.os.RemoteException e) {
            return -1;
        }
    }

    public java.util.List<java.lang.String> getCertsFromCarrierPrivilegeAccessRules() {
        java.util.List<java.lang.String> list = null;
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                list = iTelephony.getCertsFromCarrierPrivilegeAccessRules(getSubId());
            }
        } catch (android.os.RemoteException e) {
        }
        return list == null ? java.util.Collections.emptyList() : list;
    }

    @android.annotation.SystemApi
    public java.lang.String getAidForAppType(int i) {
        return getAidForAppType(getSubId(), i);
    }

    public java.lang.String getAidForAppType(int i, int i2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getAidForAppType(i, i2);
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getAidForAppType", e);
            return null;
        }
    }

    public java.lang.String getEsn() {
        return getEsn(getSubId());
    }

    public java.lang.String getEsn(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getEsn(i);
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getEsn", e);
            return null;
        }
    }

    @android.annotation.SystemApi
    public java.lang.String getCdmaPrlVersion() {
        return getCdmaPrlVersion(getSubId());
    }

    public java.lang.String getCdmaPrlVersion(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCdmaPrlVersion(i);
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getCdmaPrlVersion", e);
            return null;
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.telephony.TelephonyHistogram> getTelephonyHistograms() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getTelephonyHistograms();
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getTelephonyHistograms", e);
            return null;
        }
    }

    @android.annotation.SystemApi
    public int setAllowedCarriers(int i, java.util.List<android.service.carrier.CarrierIdentifier> list) {
        int i2;
        if (list == null || !android.telephony.SubscriptionManager.isValidPhoneId(i)) {
            return -1;
        }
        android.telephony.CarrierRestrictionRules.Builder allowedCarriers = android.telephony.CarrierRestrictionRules.newBuilder().setAllowedCarriers(list);
        if (list.isEmpty()) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        if (setCarrierRestrictionRules(allowedCarriers.setDefaultCarrierRestriction(i2).build()) != 0) {
            return -1;
        }
        return list.size();
    }

    @android.annotation.SystemApi
    public int setCarrierRestrictionRules(android.telephony.CarrierRestrictionRules carrierRestrictionRules) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setAllowedCarriers(carrierRestrictionRules);
            }
            return 2;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#setAllowedCarriers", e);
            return 2;
        } catch (java.lang.NullPointerException e2) {
            android.util.Log.e(TAG, "Error calling ITelephony#setAllowedCarriers", e2);
            return 2;
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public java.util.List<android.service.carrier.CarrierIdentifier> getAllowedCarriers(int i) {
        android.telephony.CarrierRestrictionRules carrierRestrictionRules;
        if (android.telephony.SubscriptionManager.isValidPhoneId(i) && (carrierRestrictionRules = getCarrierRestrictionRules()) != null) {
            return carrierRestrictionRules.getAllowedCarriers();
        }
        return new java.util.ArrayList(0);
    }

    @android.annotation.SystemApi
    public android.telephony.CarrierRestrictionRules getCarrierRestrictionRules() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getAllowedCarriers();
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getAllowedCarriers", e);
            return null;
        } catch (java.lang.NullPointerException e2) {
            android.util.Log.e(TAG, "Error calling ITelephony#getAllowedCarriers", e2);
            return null;
        }
    }

    public void getCarrierRestrictionStatus(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        android.telephony.TelephonyManager.AnonymousClass10 anonymousClass10 = new android.telephony.TelephonyManager.AnonymousClass10(executor, consumer);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.getCarrierRestrictionStatus(anonymousClass10, getOpPackageName());
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCarrierRestrictionStatus: RemoteException = " + e);
            throw e.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.TelephonyManager$10, reason: invalid class name */
    class AnonymousClass10 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$resultListener;

        AnonymousClass10(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$resultListener;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$10$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$10$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    public java.util.List<java.lang.String> getShaIdFromAllowList(java.lang.String str, int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getShaIdFromAllowList(str, i);
            }
            return java.util.Collections.EMPTY_LIST;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getShaIdFromAllowList: RemoteException = " + e);
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setCarrierDataEnabled(boolean z) {
        try {
            setDataEnabledForReason(2, z);
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(TAG, "Error calling setDataEnabledForReason e:" + e);
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void setRadioEnabled(boolean z) {
        if (z) {
            clearRadioPowerOffForReason(2);
        } else {
            requestRadioPowerOffForReason(2);
        }
    }

    public int setVoNrEnabled(boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setVoNrEnabled(getSubId(android.telephony.SubscriptionManager.getDefaultDataSubscriptionId()), z);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#setVoNrEnabled", e);
            return 4;
        }
    }

    public boolean isVoNrEnabled() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isVoNrEnabled(getSubId());
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "isVoNrEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    @android.annotation.SystemApi
    public void reportDefaultNetworkStatus(boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.carrierActionReportDefaultNetworkStatus(getSubId(android.telephony.SubscriptionManager.getDefaultDataSubscriptionId()), z);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#carrierActionReportDefaultNetworkStatus", e);
        }
    }

    @android.annotation.SystemApi
    public void resetAllCarrierActions() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.carrierActionResetAll(getSubId(android.telephony.SubscriptionManager.getDefaultDataSubscriptionId()));
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#carrierActionResetAll", e);
        }
    }

    @java.lang.Deprecated
    public void setPolicyDataEnabled(boolean z) {
        try {
            setDataEnabledForReason(1, z);
        } catch (java.lang.RuntimeException e) {
            android.util.Log.e(TAG, "Error calling setDataEnabledForReason e:" + e);
        }
    }

    public void setDataEnabledForReason(int i, boolean z) {
        setDataEnabledForReason(getSubId(), i, z);
    }

    private void setDataEnabledForReason(int i, int i2, boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setDataEnabledForReason(i, i2, z, getOpPackageName());
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Telephony#setDataEnabledForReason RemoteException", e);
            e.rethrowFromSystemServer();
        }
    }

    public boolean isDataEnabledForReason(int i) {
        return isDataEnabledForReason(getSubId(), i);
    }

    private boolean isDataEnabledForReason(int i, int i2) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isDataEnabledForReason(i, i2);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Telephony#isDataEnabledForReason RemoteException", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public java.util.List<android.telephony.ClientRequestStats> getClientRequestStats(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getClientRequestStats(getOpPackageName(), getAttributionTag(), i);
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getClientRequestStats", e);
            return null;
        }
    }

    @android.annotation.SystemApi
    public boolean getEmergencyCallbackMode() {
        return getEmergencyCallbackMode(getSubId());
    }

    public boolean getEmergencyCallbackMode(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return false;
            }
            return iTelephony.getEmergencyCallbackMode(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getEmergencyCallbackMode", e);
            return false;
        }
    }

    public boolean isManualNetworkSelectionAllowed() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isManualNetworkSelectionAllowed(getSubId());
            }
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#isManualNetworkSelectionAllowed", e);
            return true;
        }
    }

    public android.telephony.SignalStrength getSignalStrength() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSignalStrength(getSubId());
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#getSignalStrength", e);
            return null;
        }
    }

    public boolean isDataConnectionAllowed() {
        try {
            int subId = getSubId(android.telephony.SubscriptionManager.getDefaultDataSubscriptionId());
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return false;
            }
            return iTelephony.isDataEnabled(subId);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error isDataConnectionAllowed", e);
            return false;
        }
    }

    public boolean isDataCapable() {
        if (this.mContext == null) {
            return true;
        }
        return this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_mobile_data_capable);
    }

    @java.lang.Deprecated
    public void setCarrierTestOverride(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, java.lang.String str7) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setCarrierTestOverride(getSubId(), str, str2, str3, str4, str5, str6, str7, null, null);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public void setCarrierTestOverride(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, java.lang.String str6, java.lang.String str7, java.lang.String str8, java.lang.String str9) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setCarrierTestOverride(getSubId(), str, str2, str3, str4, str5, str6, str7, str8, str9);
            }
        } catch (android.os.RemoteException e) {
        }
    }

    public int getCarrierIdListVersion() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCarrierIdListVersion(getSubId());
            }
            return -1;
        } catch (android.os.RemoteException e) {
            return -1;
        }
    }

    public int getNumberOfModemsWithSimultaneousDataConnections() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getNumberOfModemsWithSimultaneousDataConnections(getSubId(), getOpPackageName(), getAttributionTag());
            }
            return 0;
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    @android.annotation.SystemApi
    public boolean setOpportunisticNetworkState(boolean z) {
        java.lang.String opPackageName = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        try {
            com.android.internal.telephony.IOns iOns = getIOns();
            if (iOns == null) {
                return false;
            }
            return iOns.setEnable(z, opPackageName);
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "enableOpportunisticNetwork RemoteException", e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public boolean isOpportunisticNetworkEnabled() {
        java.lang.String opPackageName = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        try {
            com.android.internal.telephony.IOns iOns = getIOns();
            if (iOns == null) {
                return false;
            }
            return iOns.isEnabled(opPackageName);
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "enableOpportunisticNetwork RemoteException", e);
            return false;
        }
    }

    public long getSupportedRadioAccessFamily() {
        try {
            if (getITelephony() == null) {
                return 0L;
            }
            return r2.getRadioAccessFamily(getSlotIndex(), getOpPackageName());
        } catch (android.os.RemoteException e) {
            return 0L;
        } catch (java.lang.NullPointerException e2) {
            return 0L;
        }
    }

    @android.annotation.SystemApi
    public void notifyOtaEmergencyNumberDbInstalled() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.notifyOtaEmergencyNumberDbInstalled();
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "notifyOtaEmergencyNumberDatabaseInstalled RemoteException", e);
            e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public void updateOtaEmergencyNumberDbFilePath(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.updateOtaEmergencyNumberDbFilePath(parcelFileDescriptor);
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "updateOtaEmergencyNumberDbFilePath RemoteException", e);
            e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public void resetOtaEmergencyNumberDbFilePath() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.resetOtaEmergencyNumberDbFilePath();
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "resetOtaEmergencyNumberDbFilePath RemoteException", e);
            e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public boolean isEmergencyAssistanceEnabled() {
        this.mContext.enforceCallingOrSelfPermission(android.Manifest.permission.READ_PRIVILEGED_PHONE_STATE, "isEmergencyAssistanceEnabled");
        return true;
    }

    @android.annotation.SystemApi
    public java.lang.String getEmergencyAssistancePackageName() {
        if (!isEmergencyAssistanceEnabled() || !isVoiceCapable()) {
            throw new java.lang.IllegalStateException("isEmergencyAssistanceEnabled() is false or device not voice capable.");
        }
        return (java.lang.String) java.util.Objects.requireNonNull(((android.app.role.RoleManager) this.mContext.getSystemService(android.app.role.RoleManager.class)).getEmergencyRoleHolder(this.mContext.getUserId()), "Emergency role holder must not be null");
    }

    public java.util.Map<java.lang.Integer, java.util.List<android.telephony.emergency.EmergencyNumber>> getEmergencyNumberList() {
        java.util.HashMap hashMap = new java.util.HashMap();
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getEmergencyNumberList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "getEmergencyNumberList RemoteException", e);
            e.rethrowAsRuntimeException();
            return hashMap;
        }
    }

    public java.util.Map<java.lang.Integer, java.util.List<android.telephony.emergency.EmergencyNumber>> getEmergencyNumberList(int i) {
        java.util.HashMap hashMap = new java.util.HashMap();
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return filterEmergencyNumbersByCategories(iTelephony.getEmergencyNumberList(this.mContext.getOpPackageName(), this.mContext.getAttributionTag()), i);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "getEmergencyNumberList with Categories RemoteException", e);
            e.rethrowAsRuntimeException();
            return hashMap;
        }
    }

    public java.util.Map<java.lang.Integer, java.util.List<android.telephony.emergency.EmergencyNumber>> filterEmergencyNumbersByCategories(java.util.Map<java.lang.Integer, java.util.List<android.telephony.emergency.EmergencyNumber>> map, int i) {
        java.util.HashMap hashMap = new java.util.HashMap();
        if (map != null) {
            for (java.lang.Integer num : map.keySet()) {
                java.util.List<android.telephony.emergency.EmergencyNumber> list = map.get(num);
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (android.telephony.emergency.EmergencyNumber emergencyNumber : list) {
                    if (emergencyNumber.isInEmergencyServiceCategories(i)) {
                        arrayList.add(emergencyNumber);
                    }
                }
                hashMap.put(num, arrayList);
            }
        }
        return hashMap;
    }

    public boolean isEmergencyNumber(java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isEmergencyNumber(str, true);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "isEmergencyNumber RemoteException", e);
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean isPotentialEmergencyNumber(java.lang.String str) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isEmergencyNumber(str, false);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "isEmergencyNumber RemoteException", e);
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    @android.annotation.SystemApi
    public int getEmergencyNumberDbVersion() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getEmergencyNumberDbVersion(getSubId());
            }
            return -1;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "getEmergencyNumberDbVersion RemoteException", e);
            e.rethrowAsRuntimeException();
            return -1;
        }
    }

    public void setPreferredOpportunisticDataSubscription(int i, boolean z, final java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        java.lang.String opPackageName = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        try {
            com.android.internal.telephony.IOns iOns = getIOns();
            if (iOns == null) {
                if (android.compat.Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new java.lang.IllegalStateException("Opportunistic Network Service is null");
                }
                throw new android.os.RemoteException("Null Opportunistic Network Service!");
            }
            iOns.setPreferredDataSubscriptionId(i, z, new android.telephony.TelephonyManager.AnonymousClass11(executor, consumer), opPackageName);
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setPreferredOpportunisticDataSubscription RemoteException", e);
            if (executor == null || consumer == null) {
                return;
            }
            runOnBackgroundThread(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.TelephonyManager.lambda$setPreferredOpportunisticDataSubscription$16(r1);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$11, reason: invalid class name */
    class AnonymousClass11 extends com.android.internal.telephony.ISetOpportunisticDataCallback.Stub {
        final /* synthetic */ java.util.function.Consumer val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass11(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.ISetOpportunisticDataCallback
        public void onComplete(final int i) {
            if (this.val$executor == null || this.val$callback == null) {
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final java.util.function.Consumer consumer = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$11$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(java.lang.Integer.valueOf(i));
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    static /* synthetic */ void lambda$setPreferredOpportunisticDataSubscription$16(java.util.function.Consumer consumer) {
        if (android.compat.Compatibility.isChangeEnabled(CALLBACK_ON_MORE_ERROR_CODE_CHANGE)) {
            consumer.accept(4);
        } else {
            consumer.accept(2);
        }
    }

    public int getPreferredOpportunisticDataSubscription() {
        java.lang.String opPackageName = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        java.lang.String attributionTag = this.mContext != null ? this.mContext.getAttributionTag() : null;
        try {
            com.android.internal.telephony.IOns iOns = getIOns();
            if (iOns == null) {
                return -1;
            }
            return iOns.getPreferredDataSubscriptionId(opPackageName, attributionTag);
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getPreferredDataSubscriptionId RemoteException", e);
            return -1;
        }
    }

    public void updateAvailableNetworks(java.util.List<android.telephony.AvailableNetworkInfo> list, final java.util.concurrent.Executor executor, final java.util.function.Consumer<java.lang.Integer> consumer) {
        java.lang.String opPackageName = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        java.util.Objects.requireNonNull(list, "availableNetworks must not be null.");
        try {
            com.android.internal.telephony.IOns iOns = getIOns();
            if (iOns == null) {
                if (android.compat.Compatibility.isChangeEnabled(NULL_TELEPHONY_THROW_NO_CB)) {
                    throw new java.lang.IllegalStateException("Opportunistic Network Service is null");
                }
                throw new android.os.RemoteException("Null Opportunistic Network Service!");
            }
            iOns.updateAvailableNetworks(list, new android.telephony.TelephonyManager.AnonymousClass12(executor, consumer), opPackageName);
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "updateAvailableNetworks RemoteException", e);
            if (executor == null || consumer == null) {
                return;
            }
            runOnBackgroundThread(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.telephony.TelephonyManager.lambda$updateAvailableNetworks$18(r1);
                        }
                    });
                }
            });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$12, reason: invalid class name */
    class AnonymousClass12 extends com.android.internal.telephony.IUpdateAvailableNetworksCallback.Stub {
        final /* synthetic */ java.util.function.Consumer val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass12(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IUpdateAvailableNetworksCallback
        public void onComplete(final int i) {
            if (this.val$executor == null || this.val$callback == null) {
                return;
            }
            final java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$callback;
            android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$12$$ExternalSyntheticLambda0
                @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                public final void runOrThrow() {
                    executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$12$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    static /* synthetic */ void lambda$updateAvailableNetworks$18(java.util.function.Consumer consumer) {
        if (android.compat.Compatibility.isChangeEnabled(CALLBACK_ON_MORE_ERROR_CODE_CHANGE)) {
            consumer.accept(9);
        } else {
            consumer.accept(1);
        }
    }

    @android.annotation.SystemApi
    public boolean enableModemForSlot(int i, boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                return false;
            }
            return iTelephony.enableModemForSlot(i, z);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "enableModem RemoteException", e);
            return false;
        }
    }

    public boolean isModemEnabledForSlot(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isModemEnabledForSlot(i, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "enableModem RemoteException", e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public void setMultiSimCarrierRestriction(boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setMultiSimCarrierRestriction(z);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "setMultiSimCarrierRestriction RemoteException", e);
        }
    }

    public int isMultiSimSupported() {
        if (getSupportedModemCount() < 2) {
            return 1;
        }
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isMultiSimSupported(getOpPackageName(), getAttributionTag());
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "isMultiSimSupported RemoteException", e);
        }
        return 1;
    }

    public void switchMultiSimConfig(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.switchMultiSimConfig(i);
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "switchMultiSimConfig RemoteException", e);
        }
    }

    public boolean doesSwitchMultiSimConfigTriggerReboot() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.doesSwitchMultiSimConfigTriggerReboot(getSubId(), getOpPackageName(), getAttributionTag());
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "doesSwitchMultiSimConfigTriggerReboot RemoteException", e);
            return false;
        }
    }

    @java.lang.Deprecated
    public android.util.Pair<java.lang.Integer, java.lang.Integer> getRadioHalVersion() {
        return getHalVersion(0);
    }

    public android.util.Pair<java.lang.Integer, java.lang.Integer> getHalVersion(int i) {
        com.android.internal.telephony.ITelephony iTelephony;
        try {
            iTelephony = getITelephony();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "getHalVersion() RemoteException", e);
            e.rethrowAsRuntimeException();
        }
        if (iTelephony != null) {
            int halVersion = iTelephony.getHalVersion(i);
            if (halVersion != -1) {
                return new android.util.Pair<>(java.lang.Integer.valueOf(halVersion / 100), java.lang.Integer.valueOf(halVersion % 100));
            }
            return HAL_VERSION_UNKNOWN;
        }
        throw new java.lang.IllegalStateException("telephony service is null.");
    }

    @android.annotation.SystemApi
    public int getCarrierPrivilegeStatus(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCarrierPrivilegeStatusForUid(getSubId(), i);
            }
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "getCarrierPrivilegeStatus RemoteException", e);
            return 0;
        }
    }

    public java.util.List<android.telephony.data.ApnSetting> getDevicePolicyOverrideApns(android.content.Context context) {
        android.database.Cursor query = context.getContentResolver().query(android.provider.Telephony.Carriers.DPC_URI, null, null, null, null);
        try {
            if (query == null) {
                java.util.List<android.telephony.data.ApnSetting> emptyList = java.util.Collections.emptyList();
                if (query != null) {
                    query.close();
                }
                return emptyList;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            query.moveToPosition(-1);
            while (query.moveToNext()) {
                arrayList.add(android.telephony.data.ApnSetting.makeApnSetting(query));
            }
            if (query != null) {
                query.close();
            }
            return arrayList;
        } catch (java.lang.Throwable th) {
            if (query != null) {
                try {
                    query.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public int addDevicePolicyOverrideApn(android.content.Context context, android.telephony.data.ApnSetting apnSetting) {
        android.net.Uri insert = context.getContentResolver().insert(android.provider.Telephony.Carriers.DPC_URI, apnSetting.toContentValues());
        if (insert != null) {
            try {
                return java.lang.Integer.parseInt(insert.getLastPathSegment());
            } catch (java.lang.NumberFormatException e) {
                com.android.telephony.Rlog.e(TAG, "Failed to parse inserted override APN id: " + insert.getLastPathSegment());
            }
        }
        return -1;
    }

    public boolean modifyDevicePolicyOverrideApn(android.content.Context context, int i, android.telephony.data.ApnSetting apnSetting) {
        return context.getContentResolver().update(android.net.Uri.withAppendedPath(android.provider.Telephony.Carriers.DPC_URI, java.lang.Integer.toString(i)), apnSetting.toContentValues(), null, null) > 0;
    }

    @android.annotation.SystemApi
    public boolean isDataEnabledForApn(int i) {
        java.lang.String opPackageName = this.mContext != null ? this.mContext.getOpPackageName() : "<unknown>";
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isDataEnabledForApn(i, getSubId(), opPackageName);
            }
            return false;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#isDataEnabledForApn RemoteException" + e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public boolean isApnMetered(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isApnMetered(i, getSubId());
            }
            return true;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#isApnMetered RemoteException" + e);
            return true;
        }
    }

    @android.annotation.SystemApi
    public void setSystemSelectionChannels(java.util.List<android.telephony.RadioAccessSpecifier> list, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) {
        java.util.Objects.requireNonNull(list, "Specifiers must not be null.");
        java.util.Objects.requireNonNull(executor, "Executor must not be null.");
        java.util.Objects.requireNonNull(consumer, "Callback must not be null.");
        setSystemSelectionChannelsInternal(list, executor, consumer);
    }

    @android.annotation.SystemApi
    public void setSystemSelectionChannels(java.util.List<android.telephony.RadioAccessSpecifier> list) {
        java.util.Objects.requireNonNull(list, "Specifiers must not be null.");
        setSystemSelectionChannelsInternal(list, null, null);
    }

    /* renamed from: android.telephony.TelephonyManager$13, reason: invalid class name */
    class AnonymousClass13 extends com.android.internal.telephony.IBooleanConsumer.Stub {
        final /* synthetic */ java.util.function.Consumer val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass13(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IBooleanConsumer
        public void accept(final boolean z) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final java.util.function.Consumer consumer = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$13$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(java.lang.Boolean.valueOf(z));
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private void setSystemSelectionChannelsInternal(java.util.List<android.telephony.RadioAccessSpecifier> list, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) {
        android.telephony.TelephonyManager.AnonymousClass13 anonymousClass13 = consumer == null ? null : new android.telephony.TelephonyManager.AnonymousClass13(executor, consumer);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setSystemSelectionChannels(list, getSubId(), anonymousClass13);
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#setSystemSelectionChannels RemoteException" + e);
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.telephony.RadioAccessSpecifier> getSystemSelectionChannels() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSystemSelectionChannels(getSubId());
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#getSystemSelectionChannels RemoteException" + e);
            return new java.util.ArrayList();
        }
    }

    @android.annotation.SystemApi
    public boolean matchesCurrentSimOperator(java.lang.String str, int i, java.lang.String str2) {
        com.android.internal.telephony.ITelephony iTelephony;
        try {
            if (str.equals(getSimOperator()) && (iTelephony = getITelephony()) != null) {
                return iTelephony.isMvnoMatched(getSlotIndex(), i, str2);
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#matchesCurrentSimOperator RemoteException" + e);
        }
        return false;
    }

    @android.annotation.SystemApi
    public void getCallForwarding(int i, java.util.concurrent.Executor executor, android.telephony.TelephonyManager.CallForwardingInfoCallback callForwardingInfoCallback) {
        if (i < 0 || i > 5) {
            throw new java.lang.IllegalArgumentException("callForwardingReason is out of range");
        }
        android.telephony.TelephonyManager.AnonymousClass14 anonymousClass14 = new android.telephony.TelephonyManager.AnonymousClass14(executor, callForwardingInfoCallback);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.getCallForwarding(getSubId(), i, anonymousClass14);
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCallForwarding RemoteException", e);
            e.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.TelephonyManager$14, reason: invalid class name */
    class AnonymousClass14 extends com.android.internal.telephony.ICallForwardingInfoCallback.Stub {
        final /* synthetic */ android.telephony.TelephonyManager.CallForwardingInfoCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass14(java.util.concurrent.Executor executor, android.telephony.TelephonyManager.CallForwardingInfoCallback callForwardingInfoCallback) {
            this.val$executor = executor;
            this.val$callback = callForwardingInfoCallback;
        }

        @Override // com.android.internal.telephony.ICallForwardingInfoCallback
        public void onCallForwardingInfoAvailable(final android.telephony.CallForwardingInfo callForwardingInfo) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.telephony.TelephonyManager.CallForwardingInfoCallback callForwardingInfoCallback = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$14$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$14$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.telephony.TelephonyManager.CallForwardingInfoCallback.this.onCallForwardingInfoAvailable(r2);
                        }
                    });
                }
            });
        }

        @Override // com.android.internal.telephony.ICallForwardingInfoCallback
        public void onError(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final android.telephony.TelephonyManager.CallForwardingInfoCallback callForwardingInfoCallback = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$14$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$14$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            android.telephony.TelephonyManager.CallForwardingInfoCallback.this.onError(r2);
                        }
                    });
                }
            });
        }
    }

    @android.annotation.SystemApi
    public void setCallForwarding(android.telephony.CallForwardingInfo callForwardingInfo, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        if (callForwardingInfo == null) {
            throw new java.lang.IllegalArgumentException("callForwardingInfo is null");
        }
        int reason = callForwardingInfo.getReason();
        if (reason < 0 || reason > 5) {
            throw new java.lang.IllegalArgumentException("callForwardingReason is out of range");
        }
        if (callForwardingInfo.isEnabled()) {
            if (callForwardingInfo.getNumber() == null) {
                throw new java.lang.IllegalArgumentException("callForwarding number is null");
            }
            if (reason == 2 && callForwardingInfo.getTimeoutSeconds() <= 0) {
                throw new java.lang.IllegalArgumentException("callForwarding timeout isn't positive");
            }
        }
        if (consumer != null) {
            java.util.Objects.requireNonNull(executor);
        }
        android.telephony.TelephonyManager.AnonymousClass15 anonymousClass15 = new android.telephony.TelephonyManager.AnonymousClass15(executor, consumer);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setCallForwarding(getSubId(), callForwardingInfo, anonymousClass15);
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setCallForwarding RemoteException", e);
            e.rethrowAsRuntimeException();
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setCallForwarding NPE", e2);
            throw e2;
        }
    }

    /* renamed from: android.telephony.TelephonyManager$15, reason: invalid class name */
    class AnonymousClass15 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$resultListener;

        AnonymousClass15(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$resultListener;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$15$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$15$$ExternalSyntheticLambda1
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    @android.annotation.SystemApi
    public void getCallWaitingStatus(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        android.telephony.TelephonyManager.AnonymousClass16 anonymousClass16 = new android.telephony.TelephonyManager.AnonymousClass16(executor, consumer);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.getCallWaitingStatus(getSubId(), anonymousClass16);
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getCallWaitingStatus RemoteException", e);
            e.rethrowAsRuntimeException();
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "getCallWaitingStatus NPE", e2);
            throw e2;
        }
    }

    /* renamed from: android.telephony.TelephonyManager$16, reason: invalid class name */
    class AnonymousClass16 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$resultListener;

        AnonymousClass16(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$resultListener;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$16$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$16$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    @android.annotation.SystemApi
    public void setCallWaitingEnabled(boolean z, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        if (consumer != null) {
            java.util.Objects.requireNonNull(executor);
        }
        android.telephony.TelephonyManager.AnonymousClass17 anonymousClass17 = new android.telephony.TelephonyManager.AnonymousClass17(executor, consumer);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setCallWaitingStatus(getSubId(), z, anonymousClass17);
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setCallWaitingStatus RemoteException", e);
            e.rethrowAsRuntimeException();
        } catch (java.lang.NullPointerException e2) {
            com.android.telephony.Rlog.e(TAG, "setCallWaitingStatus NPE", e2);
            throw e2;
        }
    }

    /* renamed from: android.telephony.TelephonyManager$17, reason: invalid class name */
    class AnonymousClass17 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.Consumer val$resultListener;

        AnonymousClass17(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$resultListener = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$resultListener;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$17$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.os.Binder.withCleanCallingIdentity(new com.android.internal.util.FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.TelephonyManager$17$$ExternalSyntheticLambda0
                        @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                        public final void runOrThrow() {
                            r1.accept(java.lang.Integer.valueOf(r2));
                        }
                    });
                }
            });
        }
    }

    @android.annotation.SystemApi
    public void setMobileDataPolicyEnabled(int i, boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setMobileDataPolicyEnabled(getSubId(), i, z);
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#setMobileDataPolicyEnabled RemoteException" + e);
        }
    }

    @android.annotation.SystemApi
    public boolean isMobileDataPolicyEnabled(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isMobileDataPolicyEnabled(getSubId(), i);
            }
            return false;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#isMobileDataPolicyEnabled RemoteException" + e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public boolean isIccLockEnabled() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isIccLockEnabled(getSubId());
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "isIccLockEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    @android.annotation.SystemApi
    public android.telephony.PinResult setIccLockEnabled(boolean z, java.lang.String str) {
        com.android.internal.util.Preconditions.checkNotNull(str, "setIccLockEnabled pin can't be null.");
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                int iccLockEnabled = iTelephony.setIccLockEnabled(getSubId(), z, str);
                if (iccLockEnabled == Integer.MAX_VALUE) {
                    return new android.telephony.PinResult(0, 0);
                }
                if (iccLockEnabled < 0) {
                    return android.telephony.PinResult.getDefaultFailedResult();
                }
                return new android.telephony.PinResult(1, iccLockEnabled);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "setIccLockEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return android.telephony.PinResult.getDefaultFailedResult();
        }
    }

    @android.annotation.SystemApi
    public android.telephony.PinResult changeIccLockPin(java.lang.String str, java.lang.String str2) {
        com.android.internal.util.Preconditions.checkNotNull(str, "changeIccLockPin oldPin can't be null.");
        com.android.internal.util.Preconditions.checkNotNull(str2, "changeIccLockPin newPin can't be null.");
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                int changeIccLockPassword = iTelephony.changeIccLockPassword(getSubId(), str, str2);
                if (changeIccLockPassword == Integer.MAX_VALUE) {
                    return new android.telephony.PinResult(0, 0);
                }
                if (changeIccLockPassword < 0) {
                    return android.telephony.PinResult.getDefaultFailedResult();
                }
                return new android.telephony.PinResult(1, changeIccLockPassword);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "changeIccLockPin RemoteException", e);
            e.rethrowFromSystemServer();
            return android.telephony.PinResult.getDefaultFailedResult();
        }
    }

    public void notifyUserActivity() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.userActivity();
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "notifyUserActivity exception: " + e.getMessage());
        }
    }

    @android.annotation.SystemApi
    public int setNrDualConnectivityState(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.setNrDualConnectivityState(getSubId(), i);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setNrDualConnectivityState RemoteException", e);
            e.rethrowFromSystemServer();
            return 4;
        }
    }

    @android.annotation.SystemApi
    public boolean isNrDualConnectivityEnabled() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isNrDualConnectivityEnabled(getSubId());
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "isNRDualConnectivityEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    private static class DeathRecipient implements android.os.IBinder.DeathRecipient {
        private DeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.telephony.TelephonyManager.resetServiceCache();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void resetServiceCache() {
        synchronized (sCacheLock) {
            if (sITelephony != null) {
                sITelephony.asBinder().unlinkToDeath(sServiceDeath, 0);
                sITelephony = null;
            }
            if (sISub != null) {
                sISub.asBinder().unlinkToDeath(sServiceDeath, 0);
                sISub = null;
                android.telephony.SubscriptionManager.clearCaches();
            }
            if (sISms != null) {
                sISms.asBinder().unlinkToDeath(sServiceDeath, 0);
                sISms = null;
            }
            if (sIPhoneSubInfo != null) {
                sIPhoneSubInfo.asBinder().unlinkToDeath(sServiceDeath, 0);
                sIPhoneSubInfo = null;
            }
        }
    }

    static com.android.internal.telephony.IPhoneSubInfo getSubscriberInfoService() {
        if (!sServiceHandleCacheEnabled) {
            return com.android.internal.telephony.IPhoneSubInfo.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getPhoneSubServiceRegisterer().get());
        }
        if (sIPhoneSubInfo == null) {
            com.android.internal.telephony.IPhoneSubInfo asInterface = com.android.internal.telephony.IPhoneSubInfo.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getPhoneSubServiceRegisterer().get());
            synchronized (sCacheLock) {
                if (sIPhoneSubInfo == null && asInterface != null) {
                    try {
                        sIPhoneSubInfo = asInterface;
                        sIPhoneSubInfo.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (java.lang.Exception e) {
                        sIPhoneSubInfo = null;
                    }
                }
            }
        }
        return sIPhoneSubInfo;
    }

    static com.android.internal.telephony.ISub getSubscriptionService() {
        if (!sServiceHandleCacheEnabled) {
            return com.android.internal.telephony.ISub.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getSubscriptionServiceRegisterer().get());
        }
        if (sISub == null) {
            com.android.internal.telephony.ISub asInterface = com.android.internal.telephony.ISub.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getSubscriptionServiceRegisterer().get());
            synchronized (sCacheLock) {
                if (sISub == null && asInterface != null) {
                    try {
                        sISub = asInterface;
                        sISub.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (java.lang.Exception e) {
                        sISub = null;
                    }
                }
            }
        }
        return sISub;
    }

    static com.android.internal.telephony.ISms getSmsService() {
        if (!sServiceHandleCacheEnabled) {
            return com.android.internal.telephony.ISms.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getSmsServiceRegisterer().get());
        }
        if (sISms == null) {
            com.android.internal.telephony.ISms asInterface = com.android.internal.telephony.ISms.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getSmsServiceRegisterer().get());
            synchronized (sCacheLock) {
                if (sISms == null && asInterface != null) {
                    try {
                        sISms = asInterface;
                        sISms.asBinder().linkToDeath(sServiceDeath, 0);
                    } catch (java.lang.Exception e) {
                        sISms = null;
                    }
                }
            }
        }
        return sISms;
    }

    public static void disableServiceHandleCaching() {
        sServiceHandleCacheEnabled = false;
    }

    public static void enableServiceHandleCaching() {
        sServiceHandleCacheEnabled = true;
    }

    public static void setupITelephonyForTest(com.android.internal.telephony.ITelephony iTelephony) {
        sITelephony = iTelephony;
    }

    public static void setupIPhoneSubInfoForTest(com.android.internal.telephony.IPhoneSubInfo iPhoneSubInfo) {
        synchronized (sCacheLock) {
            sIPhoneSubInfo = iPhoneSubInfo;
        }
    }

    public static void setupISubForTest(com.android.internal.telephony.ISub iSub) {
        synchronized (sCacheLock) {
            sISub = iSub;
        }
    }

    public boolean canConnectTo5GInDsdsMode() {
        com.android.internal.telephony.ITelephony iTelephony = getITelephony();
        if (iTelephony == null) {
            return true;
        }
        try {
            return iTelephony.canConnectTo5GInDsdsMode();
        } catch (android.os.RemoteException e) {
            return true;
        } catch (java.lang.NullPointerException e2) {
            return true;
        }
    }

    public java.util.List<java.lang.String> getEquivalentHomePlmns() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getEquivalentHomePlmns(getSubId(), this.mContext.getOpPackageName(), getAttributionTag());
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#getEquivalentHomePlmns RemoteException" + e);
            return java.util.Collections.emptyList();
        }
    }

    public boolean isRadioInterfaceCapabilitySupported(java.lang.String str) {
        if (str == null) {
            return false;
        }
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isRadioInterfaceCapabilitySupported(str);
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "Telephony#isRadioInterfaceCapabilitySupported RemoteException" + e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public int sendThermalMitigationRequest(android.telephony.ThermalMitigationRequest thermalMitigationRequest) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.sendThermalMitigationRequest(getSubId(), thermalMitigationRequest, getOpPackageName());
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Telephony#thermalMitigationRequest RemoteException", e);
            e.rethrowFromSystemServer();
            return 4;
        }
    }

    public void registerTelephonyCallback(java.util.concurrent.Executor executor, android.telephony.TelephonyCallback telephonyCallback) {
        registerTelephonyCallback(getLocationData(), executor, telephonyCallback);
    }

    private int getLocationData() {
        boolean contains = getRenouncedPermissions().contains(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        boolean contains2 = getRenouncedPermissions().contains(android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (contains) {
            return 0;
        }
        if (contains2) {
            return 1;
        }
        return 2;
    }

    public void registerTelephonyCallback(int i, java.util.concurrent.Executor executor, android.telephony.TelephonyCallback telephonyCallback) {
        if (this.mContext == null) {
            throw new java.lang.IllegalStateException("telephony service is null.");
        }
        if (executor == null || telephonyCallback == null) {
            throw new java.lang.IllegalArgumentException("TelephonyCallback and executor must be non-null");
        }
        this.mTelephonyRegistryMgr = (android.telephony.TelephonyRegistryManager) this.mContext.getSystemService(android.content.Context.TELEPHONY_REGISTRY_SERVICE);
        if (this.mTelephonyRegistryMgr != null) {
            this.mTelephonyRegistryMgr.registerTelephonyCallback(i != 2, i == 0, executor, this.mSubId, getOpPackageName(), getAttributionTag(), telephonyCallback, getITelephony() != null);
            return;
        }
        throw new java.lang.IllegalStateException("telephony service is null.");
    }

    public void unregisterTelephonyCallback(android.telephony.TelephonyCallback telephonyCallback) {
        if (this.mContext == null) {
            throw new java.lang.IllegalStateException("telephony service is null.");
        }
        if (telephonyCallback.callback == null) {
            return;
        }
        this.mTelephonyRegistryMgr = (android.telephony.TelephonyRegistryManager) this.mContext.getSystemService(android.telephony.TelephonyRegistryManager.class);
        if (this.mTelephonyRegistryMgr != null) {
            this.mTelephonyRegistryMgr.unregisterTelephonyCallback(this.mSubId, getOpPackageName(), getAttributionTag(), telephonyCallback, getITelephony() != null);
            return;
        }
        throw new java.lang.IllegalStateException("telephony service is null.");
    }

    @android.annotation.SystemApi
    public static class BootstrapAuthenticationCallback {
        public void onKeysAvailable(byte[] bArr, java.lang.String str) {
        }

        public void onAuthenticationFailure(int i) {
        }
    }

    @android.annotation.SystemApi
    public void bootstrapAuthenticationRequest(int i, android.net.Uri uri, android.telephony.gba.UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier, boolean z, java.util.concurrent.Executor executor, final android.telephony.TelephonyManager.BootstrapAuthenticationCallback bootstrapAuthenticationCallback) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.TelephonyManager.BootstrapAuthenticationCallback.this.onAuthenticationFailure(2);
                    }
                });
            } else {
                iTelephony.bootstrapAuthenticationRequest(getSubId(), i, uri, uaSecurityProtocolIdentifier, z, new android.telephony.TelephonyManager.AnonymousClass18(executor, bootstrapAuthenticationCallback));
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#bootstrapAuthenticationRequest", e);
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.TelephonyManager.BootstrapAuthenticationCallback.this.onAuthenticationFailure(2);
                }
            });
        }
    }

    /* renamed from: android.telephony.TelephonyManager$18, reason: invalid class name */
    class AnonymousClass18 extends android.telephony.IBootstrapAuthenticationCallback.Stub {
        final /* synthetic */ android.telephony.TelephonyManager.BootstrapAuthenticationCallback val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$e;

        AnonymousClass18(java.util.concurrent.Executor executor, android.telephony.TelephonyManager.BootstrapAuthenticationCallback bootstrapAuthenticationCallback) {
            this.val$e = executor;
            this.val$callback = bootstrapAuthenticationCallback;
        }

        @Override // android.telephony.IBootstrapAuthenticationCallback
        public void onKeysAvailable(int i, final byte[] bArr, final java.lang.String str) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$e;
                final android.telephony.TelephonyManager.BootstrapAuthenticationCallback bootstrapAuthenticationCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$18$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.TelephonyManager.BootstrapAuthenticationCallback.this.onKeysAvailable(bArr, str);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.IBootstrapAuthenticationCallback
        public void onAuthenticationFailure(int i, final int i2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$e;
                final android.telephony.TelephonyManager.BootstrapAuthenticationCallback bootstrapAuthenticationCallback = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$18$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.TelephonyManager.BootstrapAuthenticationCallback.this.onAuthenticationFailure(i2);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public static boolean isNetworkTypeValid(int i) {
        return i >= 0 && i <= 20;
    }

    public void setSignalStrengthUpdateRequest(android.telephony.SignalStrengthUpdateRequest signalStrengthUpdateRequest) {
        java.util.Objects.requireNonNull(signalStrengthUpdateRequest, "request must not be null");
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setSignalStrengthUpdateRequest(getSubId(), signalStrengthUpdateRequest, getOpPackageName());
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#setSignalStrengthUpdateRequest", e);
        }
    }

    public void clearSignalStrengthUpdateRequest(android.telephony.SignalStrengthUpdateRequest signalStrengthUpdateRequest) {
        java.util.Objects.requireNonNull(signalStrengthUpdateRequest, "request must not be null");
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.clearSignalStrengthUpdateRequest(getSubId(), signalStrengthUpdateRequest, getOpPackageName());
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling ITelephony#clearSignalStrengthUpdateRequest", e);
        }
    }

    @android.annotation.SystemApi
    public android.telephony.PhoneCapability getPhoneCapability() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getPhoneCapability();
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
            if (getActiveModemCount() > 1) {
                return android.telephony.PhoneCapability.DEFAULT_DSDS_CAPABILITY;
            }
            return android.telephony.PhoneCapability.DEFAULT_SSSS_CAPABILITY;
        }
    }

    @android.annotation.SystemApi
    public int prepareForUnattendedReboot() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.prepareForUnattendedReboot();
            }
            return 2;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Telephony#prepareForUnattendedReboot RemoteException", e);
            e.rethrowFromSystemServer();
            return 2;
        }
    }

    public static class NetworkSlicingException extends java.lang.Exception {
        public static final int ERROR_MODEM_ERROR = 2;
        public static final int ERROR_TIMEOUT = 1;
        public static final int SUCCESS = 0;
        private final int mErrorCode;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface NetworkSlicingError {
        }

        public NetworkSlicingException(int i) {
            this.mErrorCode = i;
        }

        @Override // java.lang.Throwable
        public java.lang.String toString() {
            switch (this.mErrorCode) {
                case 1:
                    return "ERROR_TIMEOUT";
                case 2:
                    return "ERROR_MODEM_ERROR";
                default:
                    return android.app.admin.DevicePolicyResources.UNDEFINED;
            }
        }
    }

    public class TimeoutException extends android.telephony.TelephonyManager.NetworkSlicingException {
        public TimeoutException(int i) {
            super(i);
        }
    }

    public class ModemErrorException extends android.telephony.TelephonyManager.NetworkSlicingException {
        public ModemErrorException(int i) {
            super(i);
        }
    }

    public void getNetworkSlicingConfiguration(java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.telephony.data.NetworkSlicingConfig, android.telephony.TelephonyManager.NetworkSlicingException> outcomeReceiver) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new java.lang.IllegalStateException("telephony service is null.");
            }
            iTelephony.getSlicingConfig(new android.telephony.TelephonyManager.AnonymousClass19(null, executor, outcomeReceiver));
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    /* renamed from: android.telephony.TelephonyManager$19, reason: invalid class name */
    class AnonymousClass19 extends android.os.ResultReceiver {
        final /* synthetic */ android.os.OutcomeReceiver val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass19(android.os.Handler handler, java.util.concurrent.Executor executor, android.os.OutcomeReceiver outcomeReceiver) {
            super(handler);
            this.val$executor = executor;
            this.val$callback = outcomeReceiver;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, android.os.Bundle bundle) {
            if (i == 1) {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$19$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.TelephonyManager.AnonymousClass19.this.lambda$onReceiveResult$0(outcomeReceiver, i);
                    }
                });
            } else if (i == 2) {
                java.util.concurrent.Executor executor2 = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver2 = this.val$callback;
                executor2.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$19$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.telephony.TelephonyManager.AnonymousClass19.this.lambda$onReceiveResult$1(outcomeReceiver2, i);
                    }
                });
            } else {
                final android.telephony.data.NetworkSlicingConfig networkSlicingConfig = (android.telephony.data.NetworkSlicingConfig) bundle.getParcelable(android.telephony.TelephonyManager.KEY_SLICING_CONFIG_HANDLE, android.telephony.data.NetworkSlicingConfig.class);
                java.util.concurrent.Executor executor3 = this.val$executor;
                final android.os.OutcomeReceiver outcomeReceiver3 = this.val$callback;
                executor3.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$19$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.OutcomeReceiver.this.onResult(networkSlicingConfig);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceiveResult$0(android.os.OutcomeReceiver outcomeReceiver, int i) {
            outcomeReceiver.onError(android.telephony.TelephonyManager.this.new TimeoutException(i));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceiveResult$1(android.os.OutcomeReceiver outcomeReceiver, int i) {
            outcomeReceiver.onError(android.telephony.TelephonyManager.this.new ModemErrorException(i));
        }
    }

    public static java.lang.String convertPremiumCapabilityToString(int i) {
        switch (i) {
            case 34:
                return "PRIORITIZE_LATENCY";
            default:
                return "UNKNOWN (" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public boolean isPremiumCapabilityAvailableForPurchase(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new java.lang.IllegalStateException("telephony service is null.");
            }
            return iTelephony.isPremiumCapabilityAvailableForPurchase(i, getSubId());
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    public static java.lang.String convertPurchaseResultToString(int i) {
        switch (i) {
            case 1:
                return android.service.timezone.TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY;
            case 2:
                return "THROTTLED";
            case 3:
                return "ALREADY_PURCHASED";
            case 4:
                return "ALREADY_IN_PROGRESS";
            case 5:
                return "NOT_FOREGROUND";
            case 6:
                return "USER_CANCELED";
            case 7:
                return "CARRIER_DISABLED";
            case 8:
                return "CARRIER_ERROR";
            case 9:
                return "TIMEOUT";
            case 10:
                return "FEATURE_NOT_SUPPORTED";
            case 11:
                return "REQUEST_FAILED";
            case 12:
                return "NETWORK_NOT_AVAILABLE";
            case 13:
                return "ENTITLEMENT_CHECK_FAILED";
            case 14:
                return "NOT_DEFAULT_DATA_SUBSCRIPTION";
            case 15:
                return "PENDING_NETWORK_SETUP";
            default:
                return "UNKNOWN (" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public void purchasePremiumCapability(int i, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(consumer);
        android.telephony.TelephonyManager.AnonymousClass20 anonymousClass20 = new android.telephony.TelephonyManager.AnonymousClass20(executor, consumer);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                consumer.accept(11);
            } else {
                iTelephony.purchasePremiumCapability(i, anonymousClass20, getSubId());
            }
        } catch (android.os.RemoteException e) {
            consumer.accept(11);
        }
    }

    /* renamed from: android.telephony.TelephonyManager$20, reason: invalid class name */
    class AnonymousClass20 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.function.Consumer val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass20(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.Consumer consumer = this.val$callback;
            executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$20$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(java.lang.Integer.valueOf(i));
                }
            });
        }
    }

    @android.annotation.SystemApi
    public android.telephony.CellIdentity getLastKnownCellIdentity() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new java.lang.IllegalStateException("telephony service is null.");
            }
            return iTelephony.getLastKnownCellIdentity(getSubId(), getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
            return null;
        }
    }

    @android.annotation.SystemApi
    public interface CarrierPrivilegesCallback {
        void onCarrierPrivilegesChanged(java.util.Set<java.lang.String> set, java.util.Set<java.lang.Integer> set2);

        default void onCarrierServiceChanged(java.lang.String str, int i) {
        }
    }

    @android.annotation.SystemApi
    public void setVoiceServiceStateOverride(boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                throw new java.lang.IllegalStateException("Telephony service is null");
            }
            iTelephony.setVoiceServiceStateOverride(getSubId(), z, getOpPackageName());
        } catch (android.os.RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.SystemApi
    public void registerCarrierPrivilegesCallback(int i, java.util.concurrent.Executor executor, android.telephony.TelephonyManager.CarrierPrivilegesCallback carrierPrivilegesCallback) {
        if (this.mContext == null) {
            throw new java.lang.IllegalStateException("Telephony service is null");
        }
        if (executor == null || carrierPrivilegesCallback == null) {
            throw new java.lang.IllegalArgumentException("CarrierPrivilegesCallback and executor must be non-null");
        }
        this.mTelephonyRegistryMgr = (android.telephony.TelephonyRegistryManager) this.mContext.getSystemService(android.telephony.TelephonyRegistryManager.class);
        if (this.mTelephonyRegistryMgr == null) {
            throw new java.lang.IllegalStateException("Telephony registry service is null");
        }
        this.mTelephonyRegistryMgr.addCarrierPrivilegesCallback(i, executor, carrierPrivilegesCallback);
    }

    @android.annotation.SystemApi
    public void unregisterCarrierPrivilegesCallback(android.telephony.TelephonyManager.CarrierPrivilegesCallback carrierPrivilegesCallback) {
        if (this.mContext == null) {
            throw new java.lang.IllegalStateException("Telephony service is null");
        }
        if (carrierPrivilegesCallback == null) {
            throw new java.lang.IllegalArgumentException("CarrierPrivilegesCallback must be non-null");
        }
        this.mTelephonyRegistryMgr = (android.telephony.TelephonyRegistryManager) this.mContext.getSystemService(android.telephony.TelephonyRegistryManager.class);
        if (this.mTelephonyRegistryMgr == null) {
            throw new java.lang.IllegalStateException("Telephony registry service is null");
        }
        this.mTelephonyRegistryMgr.removeCarrierPrivilegesCallback(carrierPrivilegesCallback);
    }

    public void setRemovableEsimAsDefaultEuicc(boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setRemovableEsimAsDefaultEuicc(z, getOpPackageName());
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error in setRemovableEsimAsDefault: " + e);
        }
    }

    public boolean isRemovableEsimDefaultEuicc() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isRemovableEsimDefaultEuicc(getOpPackageName());
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error in isRemovableEsimDefaultEuicc: " + e);
            return false;
        }
    }

    public static int getSimStateForSlotIndex(int i) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getSimStateForSlotIndex(i);
            }
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error in getSimStateForSlotIndex: " + e);
            return 0;
        }
    }

    @android.annotation.SystemApi
    public static final class EmergencyCallDiagnosticData {
        private static long sUnsetLogcatStartTime = -1;
        private boolean mCollectLogcat;
        private boolean mCollectTelecomDumpsys;
        private boolean mCollectTelephonyDumpsys;
        private long mLogcatStartTimeMillis;

        public static final class Builder {
            private boolean mCollectTelecomDumpsys;
            private boolean mCollectTelephonyDumpsys;
            private long mLogcatStartTimeMillis = android.telephony.TelephonyManager.EmergencyCallDiagnosticData.sUnsetLogcatStartTime;

            public android.telephony.TelephonyManager.EmergencyCallDiagnosticData.Builder setTelecomDumpsysCollectionEnabled(boolean z) {
                this.mCollectTelecomDumpsys = z;
                return this;
            }

            public android.telephony.TelephonyManager.EmergencyCallDiagnosticData.Builder setTelephonyDumpsysCollectionEnabled(boolean z) {
                this.mCollectTelephonyDumpsys = z;
                return this;
            }

            public android.telephony.TelephonyManager.EmergencyCallDiagnosticData.Builder setLogcatCollectionStartTimeMillis(long j) {
                this.mLogcatStartTimeMillis = j;
                return this;
            }

            public android.telephony.TelephonyManager.EmergencyCallDiagnosticData build() {
                return new android.telephony.TelephonyManager.EmergencyCallDiagnosticData(this.mCollectTelecomDumpsys, this.mCollectTelephonyDumpsys, this.mLogcatStartTimeMillis);
            }
        }

        private EmergencyCallDiagnosticData(boolean z, boolean z2, long j) {
            this.mCollectTelecomDumpsys = z;
            this.mCollectTelephonyDumpsys = z2;
            this.mLogcatStartTimeMillis = j;
            this.mCollectLogcat = j != sUnsetLogcatStartTime;
        }

        public boolean isTelecomDumpsysCollectionEnabled() {
            return this.mCollectTelecomDumpsys;
        }

        public boolean isTelephonyDumpsysCollectionEnabled() {
            return this.mCollectTelephonyDumpsys;
        }

        public boolean isLogcatCollectionEnabled() {
            return this.mCollectLogcat;
        }

        public long getLogcatCollectionStartTimeMillis() {
            return this.mLogcatStartTimeMillis;
        }

        public java.lang.String toString() {
            return "EmergencyCallDiagnosticData{mCollectTelecomDumpsys=" + this.mCollectTelecomDumpsys + ", mCollectTelephonyDumpsys=" + this.mCollectTelephonyDumpsys + ", mCollectLogcat=" + this.mCollectLogcat + ", mLogcatStartTimeMillis=" + this.mLogcatStartTimeMillis + '}';
        }
    }

    @android.annotation.SystemApi
    public void persistEmergencyCallDiagnosticData(java.lang.String str, android.telephony.TelephonyManager.EmergencyCallDiagnosticData emergencyCallDiagnosticData) {
        try {
            com.android.internal.telephony.ITelephony asInterface = com.android.internal.telephony.ITelephony.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
            if (asInterface != null) {
                asInterface.persistEmergencyCallDiagnosticData(str, emergencyCallDiagnosticData.isLogcatCollectionEnabled(), emergencyCallDiagnosticData.getLogcatCollectionStartTimeMillis(), emergencyCallDiagnosticData.isTelecomDumpsysCollectionEnabled(), emergencyCallDiagnosticData.isTelephonyDumpsysCollectionEnabled());
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error while persistEmergencyCallDiagnosticData: " + e);
        }
    }

    public void setNullCipherAndIntegrityEnabled(boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setNullCipherAndIntegrityEnabled(z);
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setNullCipherAndIntegrityEnabled RemoteException", e);
            e.rethrowFromSystemServer();
        }
    }

    public boolean isNullCipherAndIntegrityPreferenceEnabled() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isNullCipherAndIntegrityPreferenceEnabled();
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "isNullCipherAndIntegrityPreferenceEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return true;
        }
    }

    @android.annotation.SystemApi
    public void setEnableCellularIdentifierDisclosureNotifications(boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setEnableCellularIdentifierDisclosureNotifications(z);
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setEnableCellularIdentifierDisclosureNotifications RemoteException", e);
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isCellularIdentifierDisclosureNotificationsEnabled() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isCellularIdentifierDisclosureNotificationsEnabled();
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "isCellularIdentifierDisclosureNotificationsEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    @android.annotation.SystemApi
    public void setNullCipherNotificationsEnabled(boolean z) {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setNullCipherNotificationsEnabled(z);
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "setEnableNullCipherNotifications RemoteException", e);
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isNullCipherNotificationsEnabled() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isNullCipherNotificationsEnabled();
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "isNullCipherNotificationsEnabled RemoteException", e);
            e.rethrowFromSystemServer();
            return false;
        }
    }

    @android.annotation.SystemApi
    public java.util.List<android.telephony.CellBroadcastIdRange> getCellBroadcastIdRanges() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.getCellBroadcastIdRanges(getSubId());
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return new java.util.ArrayList();
        }
    }

    /* renamed from: android.telephony.TelephonyManager$21, reason: invalid class name */
    class AnonymousClass21 extends com.android.internal.telephony.IIntegerConsumer.Stub {
        final /* synthetic */ java.util.function.Consumer val$callback;
        final /* synthetic */ java.util.concurrent.Executor val$executor;

        AnonymousClass21(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
            this.val$executor = executor;
            this.val$callback = consumer;
        }

        @Override // com.android.internal.telephony.IIntegerConsumer
        public void accept(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final java.util.function.Consumer consumer = this.val$callback;
                executor.execute(new java.lang.Runnable() { // from class: android.telephony.TelephonyManager$21$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(java.lang.Integer.valueOf(i));
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    @android.annotation.SystemApi
    public void setCellBroadcastIdRanges(java.util.List<android.telephony.CellBroadcastIdRange> list, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        android.telephony.TelephonyManager.AnonymousClass21 anonymousClass21 = consumer == null ? null : new android.telephony.TelephonyManager.AnonymousClass21(executor, consumer);
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                iTelephony.setCellBroadcastIdRanges(getSubId(), list, anonymousClass21);
                return;
            }
            throw new java.lang.IllegalStateException("telephony service is null.");
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isDomainSelectionSupported() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony != null) {
                return iTelephony.isDomainSelectionSupported();
            }
            return false;
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.w(TAG, "RemoteException", e);
            return false;
        }
    }

    public java.lang.String getPrimaryImei() {
        try {
            com.android.internal.telephony.ITelephony iTelephony = getITelephony();
            if (iTelephony == null) {
                com.android.telephony.Rlog.e(TAG, "getPrimaryImei(): IPhoneSubInfo instance is NULL");
                throw new java.lang.IllegalStateException("Telephony service not available.");
            }
            return iTelephony.getPrimaryImei(getOpPackageName(), getAttributionTag());
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getPrimaryImei() RemoteException : " + e);
            throw e.rethrowAsRuntimeException();
        }
    }

    public static java.lang.String simStateToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "ABSENT";
            case 2:
                return "PIN_REQUIRED";
            case 3:
                return "PUK_REQUIRED";
            case 4:
                return "NETWORK_LOCKED";
            case 5:
                return "READY";
            case 6:
                return "NOT_READY";
            case 7:
                return "PERM_DISABLED";
            case 8:
                return "CARD_IO_ERROR";
            case 9:
                return "CARD_RESTRICTED";
            case 10:
                return "LOADED";
            case 11:
                return "PRESENT";
            default:
                return "UNKNOWN(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }
}
