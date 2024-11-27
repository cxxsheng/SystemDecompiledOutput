package android.telephony;

/* loaded from: classes3.dex */
public final class TelephonyProtoEnums {
    public static final int ALREADY_IN_PROGRESS = 2;
    public static final int APN_PROTOCOL_IPV4 = 0;
    public static final int APN_PROTOCOL_IPV4V6 = 2;
    public static final int APN_PROTOCOL_IPV6 = 1;
    public static final int APN_PROTOCOL_PPP = 3;
    public static final int CALL_BEARER_CS = 1;
    public static final int CALL_BEARER_IMS = 2;
    public static final int CALL_BEARER_UNKNOWN = 0;
    public static final int CALL_DIRECTION_MO = 1;
    public static final int CALL_DIRECTION_MT = 2;
    public static final int CALL_DIRECTION_UNKNOWN = 0;
    public static final int CALL_DURATION_LESS_THAN_FIVE_MINUTES = 2;
    public static final int CALL_DURATION_LESS_THAN_ONE_HOUR = 5;
    public static final int CALL_DURATION_LESS_THAN_ONE_MINUTE = 1;
    public static final int CALL_DURATION_LESS_THAN_TEN_MINUTES = 3;
    public static final int CALL_DURATION_LESS_THAN_THIRTY_MINUTES = 4;
    public static final int CALL_DURATION_MORE_THAN_ONE_HOUR = 6;
    public static final int CALL_DURATION_UNKNOWN = 0;
    public static final int CALL_SETUP_DURATION_EXTREMELY_FAST = 1;
    public static final int CALL_SETUP_DURATION_EXTREMELY_SLOW = 9;
    public static final int CALL_SETUP_DURATION_FAST = 4;
    public static final int CALL_SETUP_DURATION_NORMAL = 5;
    public static final int CALL_SETUP_DURATION_SLOW = 6;
    public static final int CALL_SETUP_DURATION_ULTRA_FAST = 2;
    public static final int CALL_SETUP_DURATION_ULTRA_SLOW = 8;
    public static final int CALL_SETUP_DURATION_UNKNOWN = 0;
    public static final int CALL_SETUP_DURATION_VERY_FAST = 3;
    public static final int CALL_SETUP_DURATION_VERY_SLOW = 7;
    public static final int CODEC_QUALITY_FULLBAND = 4;
    public static final int CODEC_QUALITY_NARROWBAND = 1;
    public static final int CODEC_QUALITY_SUPER_WIDEBAND = 3;
    public static final int CODEC_QUALITY_UNKNOWN = 0;
    public static final int CODEC_QUALITY_WIDEBAND = 2;
    public static final int DATA_CONNECTION_POWER_STATE_HIGH = 3;
    public static final int DATA_CONNECTION_POWER_STATE_LOW = 1;
    public static final int DATA_CONNECTION_POWER_STATE_MEDIUM = 2;
    public static final int DATA_CONNECTION_POWER_STATE_UNKNOWN = Integer.MAX_VALUE;
    public static final int DATA_PROFILE_CBS = 4;
    public static final int DATA_PROFILE_DEFAULT = 0;
    public static final int DATA_PROFILE_FOTA = 3;
    public static final int DATA_PROFILE_IMS = 2;
    public static final int DATA_PROFILE_INVALID = -1;
    public static final int DATA_PROFILE_OEM_BASE = 1000;
    public static final int DATA_PROFILE_TETHERED = 1;
    public static final int DEACTIVATE_REASON_AIRPLANE_MODE_ON = 7;
    public static final int DEACTIVATE_REASON_CDMA_EMERGENCY_CALLBACK_MODE = 26;
    public static final int DEACTIVATE_REASON_CONCURRENT_VOICE_DATA_NOT_ALLOWED = 12;
    public static final int DEACTIVATE_REASON_CONNECTIVITY_SERVICE_UNWANTED = 5;
    public static final int DEACTIVATE_REASON_DATA_CONFIG_NOT_READY = 23;
    public static final int DEACTIVATE_REASON_DATA_DISABLED = 8;
    public static final int DEACTIVATE_REASON_DATA_PROFILE_INVALID = 29;
    public static final int DEACTIVATE_REASON_DATA_PROFILE_NOT_PREFERRED = 30;
    public static final int DEACTIVATE_REASON_DATA_SERVICE_NOT_READY = 14;
    public static final int DEACTIVATE_REASON_DATA_SERVICE_OPTION_NOT_SUPPORTED = 13;
    public static final int DEACTIVATE_REASON_DATA_STALL = 16;
    public static final int DEACTIVATE_REASON_DATA_THROTTLED = 28;
    public static final int DEACTIVATE_REASON_DEFAULT_DATA_UNSELECTED = 21;
    public static final int DEACTIVATE_REASON_HANDOVER = 3;
    public static final int DEACTIVATE_REASON_HANDOVER_FAILED = 17;
    public static final int DEACTIVATE_REASON_HANDOVER_NOT_ALLOWED = 18;
    public static final int DEACTIVATE_REASON_ILLEGAL_STATE = 32;
    public static final int DEACTIVATE_REASON_NONE = 4;
    public static final int DEACTIVATE_REASON_NORMAL = 1;
    public static final int DEACTIVATE_REASON_NOT_ALLOWED_BY_POLICY = 31;
    public static final int DEACTIVATE_REASON_NOT_IN_SERVICE = 22;
    public static final int DEACTIVATE_REASON_NO_LIVE_REQUEST = 9;
    public static final int DEACTIVATE_REASON_NO_SUITABLE_DATA_PROFILE = 25;
    public static final int DEACTIVATE_REASON_ONLY_ALLOWED_SINGLE_NETWORK = 33;
    public static final int DEACTIVATE_REASON_PENDING_TEAR_DOWN_ALL = 24;
    public static final int DEACTIVATE_REASON_POWER_OFF_BY_CARRIER = 15;
    public static final int DEACTIVATE_REASON_PREFERRED_DATA_SWITCHED = 34;
    public static final int DEACTIVATE_REASON_RADIO_OFF = 2;
    public static final int DEACTIVATE_REASON_RAT_NOT_ALLOWED = 10;
    public static final int DEACTIVATE_REASON_RETRY_SCHEDULED = 27;
    public static final int DEACTIVATE_REASON_ROAMING_DISABLED = 11;
    public static final int DEACTIVATE_REASON_SIM_REMOVAL = 6;
    public static final int DEACTIVATE_REASON_UNKNOWN = 0;
    public static final int DEACTIVATE_REASON_VCN_REQUESTED = 19;
    public static final int DEACTIVATE_REASON_VOPS_NOT_SUPPORTED = 20;
    public static final int EMERGENCY_CALL_ROUTE_EMERGENCY = 1;
    public static final int EMERGENCY_CALL_ROUTE_NORMAL = 2;
    public static final int EMERGENCY_CALL_ROUTE_UNKNOWN = 0;
    public static final int EMERGENCY_NUMBER_SOURCE_DATABASE = 2;
    public static final int EMERGENCY_NUMBER_SOURCE_DEFAULT = 4;
    public static final int EMERGENCY_NUMBER_SOURCE_MODEM_CONFIG = 3;
    public static final int EMERGENCY_NUMBER_SOURCE_NETWORK_SIGNALING = 0;
    public static final int EMERGENCY_NUMBER_SOURCE_SIM = 1;
    public static final int EMERGENCY_SERVICE_CATEGORY_AIEC = 7;
    public static final int EMERGENCY_SERVICE_CATEGORY_AMBULANCE = 2;
    public static final int EMERGENCY_SERVICE_CATEGORY_FIRE_BRIGADE = 3;
    public static final int EMERGENCY_SERVICE_CATEGORY_MARINE_GUARD = 4;
    public static final int EMERGENCY_SERVICE_CATEGORY_MIEC = 6;
    public static final int EMERGENCY_SERVICE_CATEGORY_MOUNTAIN_RESCUE = 5;
    public static final int EMERGENCY_SERVICE_CATEGORY_POLICE = 1;
    public static final int EMERGENCY_SERVICE_CATEGORY_UNSPECIFIED = 0;
    public static final int FAILURE = 4;
    public static final int IMS_FEATURE_TAG_CALL_COMPOSER_ENRICHED_CALLING = 7;
    public static final int IMS_FEATURE_TAG_CALL_COMPOSER_VIA_TELEPHONY = 8;
    public static final int IMS_FEATURE_TAG_CHATBOT_COMMUNICATION_USING_SESSION = 14;
    public static final int IMS_FEATURE_TAG_CHATBOT_COMMUNICATION_USING_STANDALONE_MSG = 15;
    public static final int IMS_FEATURE_TAG_CHATBOT_ROLE = 17;
    public static final int IMS_FEATURE_TAG_CHATBOT_VERSION_SUPPORTED = 16;
    public static final int IMS_FEATURE_TAG_CHAT_IM = 3;
    public static final int IMS_FEATURE_TAG_CHAT_SESSION = 4;
    public static final int IMS_FEATURE_TAG_CUSTOM = 1;
    public static final int IMS_FEATURE_TAG_FILE_TRANSFER = 5;
    public static final int IMS_FEATURE_TAG_FILE_TRANSFER_VIA_SMS = 6;
    public static final int IMS_FEATURE_TAG_GEO_PUSH = 12;
    public static final int IMS_FEATURE_TAG_GEO_PUSH_VIA_SMS = 13;
    public static final int IMS_FEATURE_TAG_MMTEL = 18;
    public static final int IMS_FEATURE_TAG_POST_CALL = 9;
    public static final int IMS_FEATURE_TAG_PRESENCE = 20;
    public static final int IMS_FEATURE_TAG_SHARED_MAP = 10;
    public static final int IMS_FEATURE_TAG_SHARED_SKETCH = 11;
    public static final int IMS_FEATURE_TAG_STANDALONE_MSG = 2;
    public static final int IMS_FEATURE_TAG_UNSPECIFIED = 0;
    public static final int IMS_FEATURE_TAG_VIDEO = 19;
    public static final int NETWORK_TYPE_1XRTT = 7;
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
    public static final int NETWORK_TYPE_IDEN = 11;
    public static final int NETWORK_TYPE_IWLAN = 18;
    public static final int NETWORK_TYPE_LTE = 13;
    public static final int NETWORK_TYPE_LTE_CA = 19;
    public static final int NETWORK_TYPE_NR = 20;
    public static final int NETWORK_TYPE_TD_SCDMA = 17;
    public static final int NETWORK_TYPE_UMTS = 3;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int NOT_SUPPORTED = 1;
    public static final int RADIO_POWER_STATE_OFF = 1;
    public static final int RADIO_POWER_STATE_ON = 2;
    public static final int RADIO_POWER_STATE_UNAVAILABLE = 3;
    public static final int RADIO_POWER_STATE_UNKNOWN = 0;
    public static final int RECOVERED_REASON_DSRM = 1;
    public static final int RECOVERED_REASON_MODEM = 2;
    public static final int RECOVERED_REASON_NONE = 0;
    public static final int RECOVERED_REASON_USER = 3;
    public static final int RECOVERY_ACTION_CLEANUP = 1;
    public static final int RECOVERY_ACTION_GET_DATA_CALL_LIST = 0;
    public static final int RECOVERY_ACTION_RADIO_RESTART = 3;
    public static final int RECOVERY_ACTION_REREGISTER = 2;
    public static final int RECOVERY_ACTION_RESET_MODEM = 4;
    public static final int REGISTRATION_STATE_DENIED = 3;
    public static final int REGISTRATION_STATE_HOME = 1;
    public static final int REGISTRATION_STATE_NOT_REGISTERED_OR_SEARCHING = 0;
    public static final int REGISTRATION_STATE_NOT_REGISTERED_SEARCHING = 2;
    public static final int REGISTRATION_STATE_ROAMING = 5;
    public static final int REGISTRATION_STATE_UNKNOWN = 4;
    public static final int ROAMING_TYPE_NOT_ROAMING = 0;
    public static final int ROAMING_TYPE_ROAMING = 1;
    public static final int ROAMING_TYPE_ROAMING_DOMESTIC = 2;
    public static final int ROAMING_TYPE_ROAMING_INTERNATIONAL = 3;
    public static final int SERVICE_STATE_EMERGENCY_ONLY = 2;
    public static final int SERVICE_STATE_IN_SERVICE = 0;
    public static final int SERVICE_STATE_OUT_OF_SERVICE = 1;
    public static final int SERVICE_STATE_POWER_OFF = 3;
    public static final int SIGNAL_STRENGTH_GOOD = 3;
    public static final int SIGNAL_STRENGTH_GREAT = 4;
    public static final int SIGNAL_STRENGTH_MODERATE = 2;
    public static final int SIGNAL_STRENGTH_NONE_OR_UNKNOWN = 0;
    public static final int SIGNAL_STRENGTH_POOR = 1;
    public static final int SIM_RESTORE_CASE_SIM_INSERTED = 2;
    public static final int SIM_RESTORE_CASE_SUW = 1;
    public static final int SIM_RESTORE_CASE_UNDEFINED_USE_CASE = 0;
    public static final int SIM_RESTORE_MATCHING_CRITERIA_CARRIER_ID_AND_PHONE_NUMBER = 3;
    public static final int SIM_RESTORE_MATCHING_CRITERIA_CARRIER_ID_ONLY = 4;
    public static final int SIM_RESTORE_MATCHING_CRITERIA_ICCID = 2;
    public static final int SIM_RESTORE_MATCHING_CRITERIA_NONE = 1;
    public static final int SIM_RESTORE_MATCHING_CRITERIA_UNSET = 0;
    public static final int SIM_RESTORE_RESULT_NONE_MATCH = 2;
    public static final int SIM_RESTORE_RESULT_SUCCESS = 1;
    public static final int SIM_RESTORE_RESULT_UNKNOWN = 0;
    public static final int SIM_RESTORE_RESULT_ZERO_SIM_IN_BACKUP = 3;
    public static final int SIM_STATE_ABSENT = 1;
    public static final int SIM_STATE_CARD_IO_ERROR = 8;
    public static final int SIM_STATE_CARD_RESTRICTED = 9;
    public static final int SIM_STATE_LOADED = 10;
    public static final int SIM_STATE_NETWORK_LOCKED = 4;
    public static final int SIM_STATE_NOT_READY = 6;
    public static final int SIM_STATE_PERM_DISABLED = 7;
    public static final int SIM_STATE_PIN_REQUIRED = 2;
    public static final int SIM_STATE_PRESENT = 11;
    public static final int SIM_STATE_PUK_REQUIRED = 3;
    public static final int SIM_STATE_READY = 5;
    public static final int SIM_STATE_UNKNOWN = 0;
    public static final int SIP_REQUEST_ACK = 3;
    public static final int SIP_REQUEST_BYE = 5;
    public static final int SIP_REQUEST_CANCEL = 6;
    public static final int SIP_REQUEST_CUSTOM = 1;
    public static final int SIP_REQUEST_INFO = 12;
    public static final int SIP_REQUEST_INVITE = 2;
    public static final int SIP_REQUEST_MESSAGE = 14;
    public static final int SIP_REQUEST_NOTIFY = 10;
    public static final int SIP_REQUEST_OPTIONS = 4;
    public static final int SIP_REQUEST_PRACK = 8;
    public static final int SIP_REQUEST_PUBLISH = 11;
    public static final int SIP_REQUEST_REFER = 13;
    public static final int SIP_REQUEST_REGISTER = 7;
    public static final int SIP_REQUEST_SUBSCRIBE = 9;
    public static final int SIP_REQUEST_UNSPECIFIED = 0;
    public static final int SIP_REQUEST_UPDATE = 15;
    public static final int SMS_CATEGORY_FREE_SHORT_CODE = 1;
    public static final int SMS_CATEGORY_NOT_SHORT_CODE = 0;
    public static final int SMS_CATEGORY_POSSIBLE_PREMIUM_SHORT_CODE = 3;
    public static final int SMS_CATEGORY_PREMIUM_SHORT_CODE = 4;
    public static final int SMS_CATEGORY_STANDARD_SHORT_CODE = 2;
    public static final int SMS_ERROR_GENERIC = 1;
    public static final int SMS_ERROR_NOT_SUPPORTED = 3;
    public static final int SMS_ERROR_NO_MEMORY = 2;
    public static final int SMS_FORMAT_3GPP = 1;
    public static final int SMS_FORMAT_3GPP2 = 2;
    public static final int SMS_FORMAT_UNKNOWN = 0;
    public static final int SMS_SEND_ERROR_BLUETOOTH_DISCONNECTED = 27;
    public static final int SMS_SEND_ERROR_CANCELLED = 23;
    public static final int SMS_SEND_ERROR_FDN_CHECK_FAILURE = 6;
    public static final int SMS_SEND_ERROR_GENERIC_FAILURE = 1;
    public static final int SMS_SEND_ERROR_INTERNAL_ERROR = 21;
    public static final int SMS_SEND_ERROR_INVALID_ARGUMENTS = 11;
    public static final int SMS_SEND_ERROR_INVALID_BLUETOOTH_ADDRESS = 26;
    public static final int SMS_SEND_ERROR_INVALID_SMSC_ADDRESS = 19;
    public static final int SMS_SEND_ERROR_INVALID_SMS_FORMAT = 14;
    public static final int SMS_SEND_ERROR_INVALID_STATE = 12;
    public static final int SMS_SEND_ERROR_LIMIT_EXCEEDED = 5;
    public static final int SMS_SEND_ERROR_MODEM_ERROR = 16;
    public static final int SMS_SEND_ERROR_NETWORK_ERROR = 17;
    public static final int SMS_SEND_ERROR_NETWORK_REJECT = 10;
    public static final int SMS_SEND_ERROR_NONE = 0;
    public static final int SMS_SEND_ERROR_NO_BLUETOOTH_SERVICE = 25;
    public static final int SMS_SEND_ERROR_NO_DEFAULT_SMS_APP = 32;
    public static final int SMS_SEND_ERROR_NO_MEMORY = 13;
    public static final int SMS_SEND_ERROR_NO_RESOURCES = 22;
    public static final int SMS_SEND_ERROR_NO_SERVICE = 4;
    public static final int SMS_SEND_ERROR_NULL_PDU = 3;
    public static final int SMS_SEND_ERROR_OPERATION_NOT_ALLOWED = 20;
    public static final int SMS_SEND_ERROR_RADIO_NOT_AVAILABLE = 9;
    public static final int SMS_SEND_ERROR_RADIO_OFF = 2;
    public static final int SMS_SEND_ERROR_REMOTE_EXCEPTION = 31;
    public static final int SMS_SEND_ERROR_REQUEST_NOT_SUPPORTED = 24;
    public static final int SMS_SEND_ERROR_RIL_ABORTED = 137;
    public static final int SMS_SEND_ERROR_RIL_ACCESS_BARRED = 122;
    public static final int SMS_SEND_ERROR_RIL_BLOCKED_DUE_TO_CALL = 123;
    public static final int SMS_SEND_ERROR_RIL_CANCELLED = 119;
    public static final int SMS_SEND_ERROR_RIL_DEVICE_IN_USE = 136;
    public static final int SMS_SEND_ERROR_RIL_ENCODING_ERR = 109;
    public static final int SMS_SEND_ERROR_RIL_GENERIC_ERROR = 124;
    public static final int SMS_SEND_ERROR_RIL_INTERNAL_ERR = 113;
    public static final int SMS_SEND_ERROR_RIL_INVALID_ARGUMENTS = 104;
    public static final int SMS_SEND_ERROR_RIL_INVALID_MODEM_STATE = 115;
    public static final int SMS_SEND_ERROR_RIL_INVALID_RESPONSE = 125;
    public static final int SMS_SEND_ERROR_RIL_INVALID_SIM_STATE = 130;
    public static final int SMS_SEND_ERROR_RIL_INVALID_SMSC_ADDRESS = 110;
    public static final int SMS_SEND_ERROR_RIL_INVALID_SMS_FORMAT = 107;
    public static final int SMS_SEND_ERROR_RIL_INVALID_STATE = 103;
    public static final int SMS_SEND_ERROR_RIL_MODEM_ERR = 111;
    public static final int SMS_SEND_ERROR_RIL_NETWORK_ERR = 112;
    public static final int SMS_SEND_ERROR_RIL_NETWORK_NOT_READY = 116;
    public static final int SMS_SEND_ERROR_RIL_NETWORK_REJECT = 102;
    public static final int SMS_SEND_ERROR_RIL_NO_MEMORY = 105;
    public static final int SMS_SEND_ERROR_RIL_NO_NETWORK_FOUND = 135;
    public static final int SMS_SEND_ERROR_RIL_NO_RESOURCES = 118;
    public static final int SMS_SEND_ERROR_RIL_NO_SMS_TO_ACK = 131;
    public static final int SMS_SEND_ERROR_RIL_NO_SUBSCRIPTION = 134;
    public static final int SMS_SEND_ERROR_RIL_OPERATION_NOT_ALLOWED = 117;
    public static final int SMS_SEND_ERROR_RIL_RADIO_NOT_AVAILABLE = 100;
    public static final int SMS_SEND_ERROR_RIL_REQUEST_NOT_SUPPORTED = 114;
    public static final int SMS_SEND_ERROR_RIL_REQUEST_RATE_LIMITED = 106;
    public static final int SMS_SEND_ERROR_RIL_SIMULTANEOUS_SMS_AND_CALL_NOT_ALLOWED = 121;
    public static final int SMS_SEND_ERROR_RIL_SIM_ABSENT = 120;
    public static final int SMS_SEND_ERROR_RIL_SIM_BUSY = 132;
    public static final int SMS_SEND_ERROR_RIL_SIM_ERR = 129;
    public static final int SMS_SEND_ERROR_RIL_SIM_FULL = 133;
    public static final int SMS_SEND_ERROR_RIL_SIM_PIN2 = 126;
    public static final int SMS_SEND_ERROR_RIL_SIM_PUK2 = 127;
    public static final int SMS_SEND_ERROR_RIL_SMS_SEND_FAIL_RETRY = 101;
    public static final int SMS_SEND_ERROR_RIL_SUBSCRIPTION_NOT_AVAILABLE = 128;
    public static final int SMS_SEND_ERROR_RIL_SYSTEM_ERR = 108;
    public static final int SMS_SEND_ERROR_SHORT_CODE_NEVER_ALLOWED = 8;
    public static final int SMS_SEND_ERROR_SHORT_CODE_NOT_ALLOWED = 7;
    public static final int SMS_SEND_ERROR_SMS_BLOCKED_DURING_EMERGENCY = 29;
    public static final int SMS_SEND_ERROR_SMS_SEND_RETRY_FAILED = 30;
    public static final int SMS_SEND_ERROR_SYSTEM_ERROR = 15;
    public static final int SMS_SEND_ERROR_UNEXPECTED_EVENT_STOP_SENDING = 28;
    public static final int SMS_SEND_ERROR_USER_NOT_ALLOWED = 33;
    public static final int SMS_SEND_RESULT_ERROR = 2;
    public static final int SMS_SEND_RESULT_ERROR_FALLBACK = 4;
    public static final int SMS_SEND_RESULT_ERROR_RETRY = 3;
    public static final int SMS_SEND_RESULT_SUCCESS = 1;
    public static final int SMS_SEND_RESULT_UNKNOWN = 0;
    public static final int SMS_SUCCESS = 0;
    public static final int SMS_TECH_CS_3GPP = 1;
    public static final int SMS_TECH_CS_3GPP2 = 2;
    public static final int SMS_TECH_IMS = 3;
    public static final int SMS_TECH_UNKNOWN = 0;
    public static final int SMS_TYPE_NORMAL = 0;
    public static final int SMS_TYPE_SMS_PP = 1;
    public static final int SMS_TYPE_VOICEMAIL_INDICATION = 2;
    public static final int SMS_TYPE_WAP_PUSH = 4;
    public static final int SMS_TYPE_ZERO = 3;
    public static final int STATE_CLOSED = 1;
    public static final int STATE_FLIPPED = 4;
    public static final int STATE_HALF_OPENED = 2;
    public static final int STATE_OPENED = 3;
    public static final int STATE_UNKNOWN = 0;
    public static final int SUCCESS = 3;
    public static final int UNSPECIFIED = 0;
}