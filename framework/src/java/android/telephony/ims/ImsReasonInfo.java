package android.telephony.ims;

/* loaded from: classes3.dex */
public final class ImsReasonInfo implements android.os.Parcelable {
    public static final int CODE_ACCESS_CLASS_BLOCKED = 1512;
    public static final int CODE_ANSWERED_ELSEWHERE = 1014;
    public static final int CODE_BLACKLISTED_CALL_ID = 506;
    public static final int CODE_CALL_BARRED = 240;
    public static final int CODE_CALL_DROP_IWLAN_TO_LTE_UNAVAILABLE = 1100;
    public static final int CODE_CALL_END_CAUSE_CALL_PULL = 1016;
    public static final int CODE_CALL_PULL_OUT_OF_SYNC = 1015;
    public static final int CODE_DATA_DISABLED = 1406;
    public static final int CODE_DATA_LIMIT_REACHED = 1405;
    public static final int CODE_DIAL_MODIFIED_TO_DIAL = 246;
    public static final int CODE_DIAL_MODIFIED_TO_DIAL_VIDEO = 247;
    public static final int CODE_DIAL_MODIFIED_TO_SS = 245;
    public static final int CODE_DIAL_MODIFIED_TO_USSD = 244;
    public static final int CODE_DIAL_VIDEO_MODIFIED_TO_DIAL = 248;
    public static final int CODE_DIAL_VIDEO_MODIFIED_TO_DIAL_VIDEO = 249;
    public static final int CODE_DIAL_VIDEO_MODIFIED_TO_SS = 250;
    public static final int CODE_DIAL_VIDEO_MODIFIED_TO_USSD = 251;
    public static final int CODE_ECBM_NOT_SUPPORTED = 901;
    public static final int CODE_EMERGENCY_CALL_OVER_WFC_NOT_AVAILABLE = 1622;
    public static final int CODE_EMERGENCY_PERM_FAILURE = 364;
    public static final int CODE_EMERGENCY_TEMP_FAILURE = 363;
    public static final int CODE_EPDG_TUNNEL_ESTABLISH_FAILURE = 1400;
    public static final int CODE_EPDG_TUNNEL_LOST_CONNECTION = 1402;
    public static final int CODE_EPDG_TUNNEL_REKEY_FAILURE = 1401;
    public static final int CODE_FDN_BLOCKED = 241;
    public static final int CODE_IKEV2_AUTH_FAILURE = 1408;
    public static final int CODE_IMEI_NOT_ACCEPTED = 243;
    public static final int CODE_IWLAN_DPD_FAILURE = 1300;
    public static final int CODE_LOCAL_CALL_BUSY = 142;
    public static final int CODE_LOCAL_CALL_CS_RETRY_REQUIRED = 146;
    public static final int CODE_LOCAL_CALL_DECLINE = 143;
    public static final int CODE_LOCAL_CALL_EXCEEDED = 141;
    public static final int CODE_LOCAL_CALL_RESOURCE_RESERVATION_FAILED = 145;
    public static final int CODE_LOCAL_CALL_TERMINATED = 148;
    public static final int CODE_LOCAL_CALL_VCC_ON_PROGRESSING = 144;
    public static final int CODE_LOCAL_CALL_VOLTE_RETRY_REQUIRED = 147;
    public static final int CODE_LOCAL_ENDED_BY_CONFERENCE_MERGE = 108;
    public static final int CODE_LOCAL_HO_NOT_FEASIBLE = 149;
    public static final int CODE_LOCAL_ILLEGAL_ARGUMENT = 101;
    public static final int CODE_LOCAL_ILLEGAL_STATE = 102;
    public static final int CODE_LOCAL_IMS_NOT_SUPPORTED_ON_DEVICE = 150;
    public static final int CODE_LOCAL_IMS_SERVICE_DOWN = 106;
    public static final int CODE_LOCAL_INTERNAL_ERROR = 103;
    public static final int CODE_LOCAL_LOW_BATTERY = 112;
    public static final int CODE_LOCAL_NETWORK_IP_CHANGED = 124;
    public static final int CODE_LOCAL_NETWORK_NO_LTE_COVERAGE = 122;
    public static final int CODE_LOCAL_NETWORK_NO_SERVICE = 121;
    public static final int CODE_LOCAL_NETWORK_ROAMING = 123;
    public static final int CODE_LOCAL_NOT_REGISTERED = 132;
    public static final int CODE_LOCAL_NO_PENDING_CALL = 107;
    public static final int CODE_LOCAL_POWER_OFF = 111;
    public static final int CODE_LOCAL_SERVICE_UNAVAILABLE = 131;
    public static final int CODE_LOW_BATTERY = 505;
    public static final int CODE_MAXIMUM_NUMBER_OF_CALLS_REACHED = 1403;
    public static final int CODE_MEDIA_INIT_FAILED = 401;
    public static final int CODE_MEDIA_NOT_ACCEPTABLE = 403;
    public static final int CODE_MEDIA_NO_DATA = 402;
    public static final int CODE_MEDIA_UNSPECIFIED = 404;
    public static final int CODE_MULTIENDPOINT_NOT_SUPPORTED = 902;
    public static final int CODE_NETWORK_CONGESTION = 1624;
    public static final int CODE_NETWORK_DETACH = 1513;
    public static final int CODE_NETWORK_REJECT = 1504;
    public static final int CODE_NETWORK_RESP_TIMEOUT = 1503;
    public static final int CODE_NO_CSFB_IN_CS_ROAM = 1516;
    public static final int CODE_NO_VALID_SIM = 1501;
    public static final int CODE_OEM_CAUSE_1 = 61441;
    public static final int CODE_OEM_CAUSE_10 = 61450;
    public static final int CODE_OEM_CAUSE_11 = 61451;
    public static final int CODE_OEM_CAUSE_12 = 61452;
    public static final int CODE_OEM_CAUSE_13 = 61453;
    public static final int CODE_OEM_CAUSE_14 = 61454;
    public static final int CODE_OEM_CAUSE_15 = 61455;
    public static final int CODE_OEM_CAUSE_2 = 61442;
    public static final int CODE_OEM_CAUSE_3 = 61443;
    public static final int CODE_OEM_CAUSE_4 = 61444;
    public static final int CODE_OEM_CAUSE_5 = 61445;
    public static final int CODE_OEM_CAUSE_6 = 61446;
    public static final int CODE_OEM_CAUSE_7 = 61447;
    public static final int CODE_OEM_CAUSE_8 = 61448;
    public static final int CODE_OEM_CAUSE_9 = 61449;
    public static final int CODE_RADIO_ACCESS_FAILURE = 1505;
    public static final int CODE_RADIO_INTERNAL_ERROR = 1502;
    public static final int CODE_RADIO_LINK_FAILURE = 1506;
    public static final int CODE_RADIO_LINK_LOST = 1507;
    public static final int CODE_RADIO_OFF = 1500;
    public static final int CODE_RADIO_RELEASE_ABNORMAL = 1511;
    public static final int CODE_RADIO_RELEASE_NORMAL = 1510;
    public static final int CODE_RADIO_SETUP_FAILURE = 1509;
    public static final int CODE_RADIO_UPLINK_FAILURE = 1508;
    public static final int CODE_REGISTRATION_ERROR = 1000;
    public static final int CODE_REJECTED_ELSEWHERE = 1017;
    public static final int CODE_REJECT_1X_COLLISION = 1603;
    public static final int CODE_REJECT_CALL_ON_OTHER_SUB = 1602;
    public static final int CODE_REJECT_CALL_TYPE_NOT_ALLOWED = 1605;
    public static final int CODE_REJECT_CONFERENCE_TTY_NOT_ALLOWED = 1617;
    public static final int CODE_REJECT_INTERNAL_ERROR = 1612;
    public static final int CODE_REJECT_MAX_CALL_LIMIT_REACHED = 1608;
    public static final int CODE_REJECT_ONGOING_CALL_SETUP = 1607;
    public static final int CODE_REJECT_ONGOING_CALL_TRANSFER = 1611;
    public static final int CODE_REJECT_ONGOING_CALL_UPGRADE = 1616;
    public static final int CODE_REJECT_ONGOING_CALL_WAITING_DISABLED = 1601;
    public static final int CODE_REJECT_ONGOING_CONFERENCE_CALL = 1618;
    public static final int CODE_REJECT_ONGOING_CS_CALL = 1621;
    public static final int CODE_REJECT_ONGOING_E911_CALL = 1606;
    public static final int CODE_REJECT_ONGOING_ENCRYPTED_CALL = 1620;
    public static final int CODE_REJECT_ONGOING_HANDOVER = 1614;
    public static final int CODE_REJECT_QOS_FAILURE = 1613;
    public static final int CODE_REJECT_SERVICE_NOT_REGISTERED = 1604;
    public static final int CODE_REJECT_UNKNOWN = 1600;
    public static final int CODE_REJECT_UNSUPPORTED_SDP_HEADERS = 1610;
    public static final int CODE_REJECT_UNSUPPORTED_SIP_HEADERS = 1609;
    public static final int CODE_REJECT_VT_AVPF_NOT_ALLOWED = 1619;
    public static final int CODE_REJECT_VT_TTY_NOT_ALLOWED = 1615;
    public static final int CODE_REMOTE_CALL_DECLINE = 1404;
    public static final int CODE_RETRY_ON_IMS_WITHOUT_RTT = 3001;
    public static final int CODE_SESSION_MODIFICATION_FAILED = 1517;
    public static final int CODE_SIP_ALTERNATE_EMERGENCY_CALL = 1514;
    public static final int CODE_SIP_AMBIGUOUS = 376;
    public static final int CODE_SIP_BAD_ADDRESS = 337;
    public static final int CODE_SIP_BAD_REQUEST = 331;
    public static final int CODE_SIP_BUSY = 338;
    public static final int CODE_SIP_CALL_OR_TRANS_DOES_NOT_EXIST = 372;
    public static final int CODE_SIP_CLIENT_ERROR = 342;
    public static final int CODE_SIP_EXTENSION_REQUIRED = 370;
    public static final int CODE_SIP_FORBIDDEN = 332;
    public static final int CODE_SIP_GLOBAL_ERROR = 362;
    public static final int CODE_SIP_INTERVAL_TOO_BRIEF = 371;
    public static final int CODE_SIP_LOOP_DETECTED = 373;
    public static final int CODE_SIP_METHOD_NOT_ALLOWED = 366;
    public static final int CODE_SIP_NOT_ACCEPTABLE = 340;
    public static final int CODE_SIP_NOT_FOUND = 333;
    public static final int CODE_SIP_NOT_REACHABLE = 341;
    public static final int CODE_SIP_NOT_SUPPORTED = 334;
    public static final int CODE_SIP_PROXY_AUTHENTICATION_REQUIRED = 367;
    public static final int CODE_SIP_REDIRECTED = 321;
    public static final int CODE_SIP_REQUEST_CANCELLED = 339;
    public static final int CODE_SIP_REQUEST_ENTITY_TOO_LARGE = 368;
    public static final int CODE_SIP_REQUEST_PENDING = 377;
    public static final int CODE_SIP_REQUEST_TIMEOUT = 335;
    public static final int CODE_SIP_REQUEST_URI_TOO_LARGE = 369;
    public static final int CODE_SIP_SERVER_ERROR = 354;
    public static final int CODE_SIP_SERVER_INTERNAL_ERROR = 351;
    public static final int CODE_SIP_SERVER_TIMEOUT = 353;
    public static final int CODE_SIP_SERVICE_UNAVAILABLE = 352;
    public static final int CODE_SIP_TEMPRARILY_UNAVAILABLE = 336;
    public static final int CODE_SIP_TOO_MANY_HOPS = 374;
    public static final int CODE_SIP_TRANSACTION_DOES_NOT_EXIST = 343;
    public static final int CODE_SIP_UNDECIPHERABLE = 378;
    public static final int CODE_SIP_USER_MARKED_UNWANTED = 365;
    public static final int CODE_SIP_USER_REJECTED = 361;
    public static final int CODE_SUPP_SVC_CANCELLED = 1202;
    public static final int CODE_SUPP_SVC_FAILED = 1201;
    public static final int CODE_SUPP_SVC_REINVITE_COLLISION = 1203;
    public static final int CODE_TIMEOUT_1XX_WAITING = 201;
    public static final int CODE_TIMEOUT_NO_ANSWER = 202;
    public static final int CODE_TIMEOUT_NO_ANSWER_CALL_UPDATE = 203;
    public static final int CODE_UNOBTAINABLE_NUMBER = 1515;
    public static final int CODE_UNSPECIFIED = 0;
    public static final int CODE_USER_CANCELLED_SESSION_MODIFICATION = 512;
    public static final int CODE_USER_DECLINE = 504;
    public static final int CODE_USER_IGNORE = 503;
    public static final int CODE_USER_NOANSWER = 502;
    public static final int CODE_USER_REJECTED_SESSION_MODIFICATION = 511;
    public static final int CODE_USER_TERMINATED = 501;
    public static final int CODE_USER_TERMINATED_BY_REMOTE = 510;
    public static final int CODE_UT_CB_PASSWORD_MISMATCH = 821;
    public static final int CODE_UT_NETWORK_ERROR = 804;
    public static final int CODE_UT_NOT_SUPPORTED = 801;
    public static final int CODE_UT_OPERATION_NOT_ALLOWED = 803;
    public static final int CODE_UT_SERVICE_UNAVAILABLE = 802;
    public static final int CODE_UT_SS_MODIFIED_TO_DIAL = 822;
    public static final int CODE_UT_SS_MODIFIED_TO_DIAL_VIDEO = 825;
    public static final int CODE_UT_SS_MODIFIED_TO_SS = 824;
    public static final int CODE_UT_SS_MODIFIED_TO_USSD = 823;
    public static final int CODE_WFC_SERVICE_NOT_AVAILABLE_IN_THIS_LOCATION = 1623;
    public static final int CODE_WIFI_LOST = 1407;
    public static final android.os.Parcelable.Creator<android.telephony.ims.ImsReasonInfo> CREATOR;
    public static final int EXTRA_CODE_CALL_RETRY_BY_SETTINGS = 3;
    public static final int EXTRA_CODE_CALL_RETRY_EMERGENCY = 4;
    public static final int EXTRA_CODE_CALL_RETRY_NORMAL = 1;
    public static final int EXTRA_CODE_CALL_RETRY_SILENT_REDIAL = 2;

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_MSG_SERVICE_NOT_AUTHORIZED = "Forbidden. Not Authorized for Service";
    private static final java.util.Map<java.lang.Integer, java.lang.String> sImsCodeMap = new java.util.HashMap();
    public int mCode;
    public int mExtraCode;
    public java.lang.String mExtraMessage;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImsCode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UtReason {
    }

    static {
        sImsCodeMap.put(0, "CODE_UNSPECIFIED");
        sImsCodeMap.put(101, "CODE_LOCAL_ILLEGAL_ARGUMENT");
        sImsCodeMap.put(102, "CODE_LOCAL_ILLEGAL_STATE");
        sImsCodeMap.put(103, "CODE_LOCAL_INTERNAL_ERROR");
        sImsCodeMap.put(106, "CODE_LOCAL_IMS_SERVICE_DOWN");
        sImsCodeMap.put(107, "CODE_LOCAL_NO_PENDING_CALL");
        sImsCodeMap.put(108, "CODE_LOCAL_ENDED_BY_CONFERENCE_MERGE");
        sImsCodeMap.put(111, "CODE_LOCAL_POWER_OFF");
        sImsCodeMap.put(112, "CODE_LOCAL_LOW_BATTERY");
        sImsCodeMap.put(121, "CODE_LOCAL_NETWORK_NO_SERVICE");
        sImsCodeMap.put(122, "CODE_LOCAL_NETWORK_NO_LTE_COVERAGE");
        sImsCodeMap.put(123, "CODE_LOCAL_NETWORK_ROAMING");
        sImsCodeMap.put(124, "CODE_LOCAL_NETWORK_IP_CHANGED");
        sImsCodeMap.put(131, "CODE_LOCAL_SERVICE_UNAVAILABLE");
        sImsCodeMap.put(132, "CODE_LOCAL_NOT_REGISTERED");
        sImsCodeMap.put(141, "CODE_LOCAL_CALL_EXCEEDED");
        sImsCodeMap.put(142, "CODE_LOCAL_CALL_BUSY");
        sImsCodeMap.put(143, "CODE_LOCAL_CALL_DECLINE");
        sImsCodeMap.put(144, "CODE_LOCAL_CALL_VCC_ON_PROGRESSING");
        sImsCodeMap.put(145, "CODE_LOCAL_CALL_RESOURCE_RESERVATION_FAILED");
        sImsCodeMap.put(146, "CODE_LOCAL_CALL_CS_RETRY_REQUIRED");
        sImsCodeMap.put(147, "CODE_LOCAL_CALL_VOLTE_RETRY_REQUIRED");
        sImsCodeMap.put(148, "CODE_LOCAL_CALL_TERMINATED");
        sImsCodeMap.put(149, "CODE_LOCAL_HO_NOT_FEASIBLE");
        sImsCodeMap.put(201, "CODE_TIMEOUT_1XX_WAITING");
        sImsCodeMap.put(202, "CODE_TIMEOUT_NO_ANSWER");
        sImsCodeMap.put(203, "CODE_TIMEOUT_NO_ANSWER_CALL_UPDATE");
        sImsCodeMap.put(240, "CODE_CALL_BARRED");
        sImsCodeMap.put(241, "CODE_FDN_BLOCKED");
        sImsCodeMap.put(243, "CODE_IMEI_NOT_ACCEPTED");
        sImsCodeMap.put(244, "CODE_DIAL_MODIFIED_TO_USSD");
        sImsCodeMap.put(245, "CODE_DIAL_MODIFIED_TO_SS");
        sImsCodeMap.put(246, "CODE_DIAL_MODIFIED_TO_DIAL");
        sImsCodeMap.put(247, "CODE_DIAL_MODIFIED_TO_DIAL_VIDEO");
        sImsCodeMap.put(248, "CODE_DIAL_VIDEO_MODIFIED_TO_DIAL");
        sImsCodeMap.put(249, "CODE_DIAL_VIDEO_MODIFIED_TO_DIAL_VIDEO");
        sImsCodeMap.put(250, "CODE_DIAL_VIDEO_MODIFIED_TO_SS");
        sImsCodeMap.put(251, "CODE_DIAL_VIDEO_MODIFIED_TO_USSD");
        sImsCodeMap.put(321, "CODE_SIP_REDIRECTED");
        sImsCodeMap.put(331, "CODE_SIP_BAD_REQUEST");
        sImsCodeMap.put(332, "CODE_SIP_FORBIDDEN");
        sImsCodeMap.put(333, "CODE_SIP_NOT_FOUND");
        sImsCodeMap.put(334, "CODE_SIP_NOT_SUPPORTED");
        sImsCodeMap.put(335, "CODE_SIP_REQUEST_TIMEOUT");
        sImsCodeMap.put(336, "CODE_SIP_TEMPRARILY_UNAVAILABLE");
        sImsCodeMap.put(337, "CODE_SIP_BAD_ADDRESS");
        sImsCodeMap.put(338, "CODE_SIP_BUSY");
        sImsCodeMap.put(339, "CODE_SIP_REQUEST_CANCELLED");
        sImsCodeMap.put(340, "CODE_SIP_NOT_ACCEPTABLE");
        sImsCodeMap.put(341, "CODE_SIP_NOT_REACHABLE");
        sImsCodeMap.put(342, "CODE_SIP_CLIENT_ERROR");
        sImsCodeMap.put(343, "CODE_SIP_TRANSACTION_DOES_NOT_EXIST");
        sImsCodeMap.put(351, "CODE_SIP_SERVER_INTERNAL_ERROR");
        sImsCodeMap.put(352, "CODE_SIP_SERVICE_UNAVAILABLE");
        sImsCodeMap.put(353, "CODE_SIP_SERVER_TIMEOUT");
        sImsCodeMap.put(354, "CODE_SIP_SERVER_ERROR");
        sImsCodeMap.put(361, "CODE_SIP_USER_REJECTED");
        sImsCodeMap.put(362, "CODE_SIP_GLOBAL_ERROR");
        sImsCodeMap.put(363, "CODE_EMERGENCY_TEMP_FAILURE");
        sImsCodeMap.put(364, "CODE_EMERGENCY_PERM_FAILURE");
        sImsCodeMap.put(365, "CODE_SIP_USER_MARKED_UNWANTED");
        sImsCodeMap.put(366, "CODE_SIP_METHOD_NOT_ALLOWED");
        sImsCodeMap.put(367, "CODE_SIP_PROXY_AUTHENTICATION_REQUIRED");
        sImsCodeMap.put(368, "CODE_SIP_REQUEST_ENTITY_TOO_LARGE");
        sImsCodeMap.put(369, "CODE_SIP_REQUEST_URI_TOO_LARGE");
        sImsCodeMap.put(370, "CODE_SIP_EXTENSION_REQUIRED");
        sImsCodeMap.put(371, "CODE_SIP_INTERVAL_TOO_BRIEF");
        sImsCodeMap.put(372, "CODE_SIP_CALL_OR_TRANS_DOES_NOT_EXIST");
        sImsCodeMap.put(373, "CODE_SIP_LOOP_DETECTED");
        sImsCodeMap.put(374, "CODE_SIP_TOO_MANY_HOPS");
        sImsCodeMap.put(376, "CODE_SIP_AMBIGUOUS");
        sImsCodeMap.put(377, "CODE_SIP_REQUEST_PENDING");
        sImsCodeMap.put(378, "CODE_SIP_UNDECIPHERABLE");
        sImsCodeMap.put(401, "CODE_MEDIA_INIT_FAILED");
        sImsCodeMap.put(402, "CODE_MEDIA_NO_DATA");
        sImsCodeMap.put(403, "CODE_MEDIA_NOT_ACCEPTABLE");
        sImsCodeMap.put(404, "CODE_MEDIA_UNSPECIFIED");
        sImsCodeMap.put(501, "CODE_USER_TERMINATED");
        sImsCodeMap.put(502, "CODE_USER_NOANSWER");
        sImsCodeMap.put(503, "CODE_USER_IGNORE");
        sImsCodeMap.put(504, "CODE_USER_DECLINE");
        sImsCodeMap.put(505, "CODE_LOW_BATTERY");
        sImsCodeMap.put(506, "CODE_BLACKLISTED_CALL_ID");
        sImsCodeMap.put(510, "CODE_USER_TERMINATED_BY_REMOTE");
        sImsCodeMap.put(511, "CODE_USER_REJECTED_SESSION_MODIFICATION");
        sImsCodeMap.put(512, "CODE_USER_CANCELLED_SESSION_MODIFICATION");
        sImsCodeMap.put(1517, "CODE_SESSION_MODIFICATION_FAILED");
        sImsCodeMap.put(801, "CODE_UT_NOT_SUPPORTED");
        sImsCodeMap.put(802, "CODE_UT_SERVICE_UNAVAILABLE");
        sImsCodeMap.put(803, "CODE_UT_OPERATION_NOT_ALLOWED");
        sImsCodeMap.put(804, "CODE_UT_NETWORK_ERROR");
        sImsCodeMap.put(821, "CODE_UT_CB_PASSWORD_MISMATCH");
        sImsCodeMap.put(822, "CODE_UT_SS_MODIFIED_TO_DIAL");
        sImsCodeMap.put(823, "CODE_UT_SS_MODIFIED_TO_USSD");
        sImsCodeMap.put(824, "CODE_UT_SS_MODIFIED_TO_SS");
        sImsCodeMap.put(825, "CODE_UT_SS_MODIFIED_TO_DIAL_VIDEO");
        sImsCodeMap.put(901, "CODE_ECBM_NOT_SUPPORTED");
        sImsCodeMap.put(902, "CODE_MULTIENDPOINT_NOT_SUPPORTED");
        sImsCodeMap.put(1000, "CODE_REGISTRATION_ERROR");
        sImsCodeMap.put(1014, "CODE_ANSWERED_ELSEWHERE");
        sImsCodeMap.put(1015, "CODE_CALL_PULL_OUT_OF_SYNC");
        sImsCodeMap.put(1016, "CODE_CALL_END_CAUSE_CALL_PULL");
        sImsCodeMap.put(1100, "CODE_CALL_DROP_IWLAN_TO_LTE_UNAVAILABLE");
        sImsCodeMap.put(1017, "CODE_REJECTED_ELSEWHERE");
        sImsCodeMap.put(1201, "CODE_SUPP_SVC_FAILED");
        sImsCodeMap.put(1202, "CODE_SUPP_SVC_CANCELLED");
        sImsCodeMap.put(1203, "CODE_SUPP_SVC_REINVITE_COLLISION");
        sImsCodeMap.put(1300, "CODE_IWLAN_DPD_FAILURE");
        sImsCodeMap.put(1400, "CODE_EPDG_TUNNEL_ESTABLISH_FAILURE");
        sImsCodeMap.put(1401, "CODE_EPDG_TUNNEL_REKEY_FAILURE");
        sImsCodeMap.put(1402, "CODE_EPDG_TUNNEL_LOST_CONNECTION");
        sImsCodeMap.put(1403, "CODE_MAXIMUM_NUMBER_OF_CALLS_REACHED");
        sImsCodeMap.put(1404, "CODE_REMOTE_CALL_DECLINE");
        sImsCodeMap.put(1405, "CODE_DATA_LIMIT_REACHED");
        sImsCodeMap.put(1406, "CODE_DATA_DISABLED");
        sImsCodeMap.put(1407, "CODE_WIFI_LOST");
        sImsCodeMap.put(1408, "CODE_IKEV2_AUTH_FAILURE");
        sImsCodeMap.put(1500, "CODE_RADIO_OFF");
        sImsCodeMap.put(1501, "CODE_NO_VALID_SIM");
        sImsCodeMap.put(1502, "CODE_RADIO_INTERNAL_ERROR");
        sImsCodeMap.put(1503, "CODE_NETWORK_RESP_TIMEOUT");
        sImsCodeMap.put(1504, "CODE_NETWORK_REJECT");
        sImsCodeMap.put(1505, "CODE_RADIO_ACCESS_FAILURE");
        sImsCodeMap.put(1506, "CODE_RADIO_LINK_FAILURE");
        sImsCodeMap.put(1507, "CODE_RADIO_LINK_LOST");
        sImsCodeMap.put(1508, "CODE_RADIO_UPLINK_FAILURE");
        sImsCodeMap.put(1509, "CODE_RADIO_SETUP_FAILURE");
        sImsCodeMap.put(1510, "CODE_RADIO_RELEASE_NORMAL");
        sImsCodeMap.put(1511, "CODE_RADIO_RELEASE_ABNORMAL");
        sImsCodeMap.put(1512, "CODE_ACCESS_CLASS_BLOCKED");
        sImsCodeMap.put(1513, "CODE_NETWORK_DETACH");
        sImsCodeMap.put(1514, "CODE_SIP_ALTERNATE_EMERGENCY_CALL");
        sImsCodeMap.put(1515, "CODE_UNOBTAINABLE_NUMBER");
        sImsCodeMap.put(1516, "CODE_NO_CSFB_IN_CS_ROAM");
        sImsCodeMap.put(1600, "CODE_REJECT_UNKNOWN");
        sImsCodeMap.put(1601, "CODE_REJECT_ONGOING_CALL_WAITING_DISABLED");
        sImsCodeMap.put(1602, "CODE_REJECT_CALL_ON_OTHER_SUB");
        sImsCodeMap.put(1603, "CODE_REJECT_1X_COLLISION");
        sImsCodeMap.put(1604, "CODE_REJECT_SERVICE_NOT_REGISTERED");
        sImsCodeMap.put(1605, "CODE_REJECT_CALL_TYPE_NOT_ALLOWED");
        sImsCodeMap.put(1606, "CODE_REJECT_ONGOING_E911_CALL");
        sImsCodeMap.put(1607, "CODE_REJECT_ONGOING_CALL_SETUP");
        sImsCodeMap.put(1608, "CODE_REJECT_MAX_CALL_LIMIT_REACHED");
        sImsCodeMap.put(1609, "CODE_REJECT_UNSUPPORTED_SIP_HEADERS");
        sImsCodeMap.put(1610, "CODE_REJECT_UNSUPPORTED_SDP_HEADERS");
        sImsCodeMap.put(1611, "CODE_REJECT_ONGOING_CALL_TRANSFER");
        sImsCodeMap.put(1612, "CODE_REJECT_INTERNAL_ERROR");
        sImsCodeMap.put(1613, "CODE_REJECT_QOS_FAILURE");
        sImsCodeMap.put(1614, "CODE_REJECT_ONGOING_HANDOVER");
        sImsCodeMap.put(1615, "CODE_REJECT_VT_TTY_NOT_ALLOWED");
        sImsCodeMap.put(1616, "CODE_REJECT_ONGOING_CALL_UPGRADE");
        sImsCodeMap.put(1617, "CODE_REJECT_CONFERENCE_TTY_NOT_ALLOWED");
        sImsCodeMap.put(1618, "CODE_REJECT_ONGOING_CONFERENCE_CALL");
        sImsCodeMap.put(1619, "CODE_REJECT_VT_AVPF_NOT_ALLOWED");
        sImsCodeMap.put(1620, "CODE_REJECT_ONGOING_ENCRYPTED_CALL");
        sImsCodeMap.put(1621, "CODE_REJECT_ONGOING_CS_CALL");
        sImsCodeMap.put(1624, "CODE_NETWORK_CONGESTION");
        sImsCodeMap.put(java.lang.Integer.valueOf(CODE_RETRY_ON_IMS_WITHOUT_RTT), "CODE_RETRY_ON_IMS_WITHOUT_RTT");
        sImsCodeMap.put(61441, "CODE_OEM_CAUSE_1");
        sImsCodeMap.put(61442, "CODE_OEM_CAUSE_2");
        sImsCodeMap.put(61443, "CODE_OEM_CAUSE_3");
        sImsCodeMap.put(61444, "CODE_OEM_CAUSE_4");
        sImsCodeMap.put(61445, "CODE_OEM_CAUSE_5");
        sImsCodeMap.put(61446, "CODE_OEM_CAUSE_6");
        sImsCodeMap.put(61447, "CODE_OEM_CAUSE_7");
        sImsCodeMap.put(61448, "CODE_OEM_CAUSE_8");
        sImsCodeMap.put(61449, "CODE_OEM_CAUSE_9");
        sImsCodeMap.put(61450, "CODE_OEM_CAUSE_10");
        sImsCodeMap.put(61451, "CODE_OEM_CAUSE_11");
        sImsCodeMap.put(61452, "CODE_OEM_CAUSE_12");
        sImsCodeMap.put(61453, "CODE_OEM_CAUSE_13");
        sImsCodeMap.put(61454, "CODE_OEM_CAUSE_14");
        sImsCodeMap.put(61455, "CODE_OEM_CAUSE_15");
        CREATOR = new android.os.Parcelable.Creator<android.telephony.ims.ImsReasonInfo>() { // from class: android.telephony.ims.ImsReasonInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.ims.ImsReasonInfo createFromParcel(android.os.Parcel parcel) {
                return new android.telephony.ims.ImsReasonInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.telephony.ims.ImsReasonInfo[] newArray(int i) {
                return new android.telephony.ims.ImsReasonInfo[i];
            }
        };
    }

    public ImsReasonInfo() {
        this.mCode = 0;
        this.mExtraCode = 0;
        this.mExtraMessage = null;
    }

    private ImsReasonInfo(android.os.Parcel parcel) {
        this.mCode = parcel.readInt();
        this.mExtraCode = parcel.readInt();
        this.mExtraMessage = parcel.readString();
    }

    public ImsReasonInfo(int i, int i2) {
        this.mCode = i;
        this.mExtraCode = i2;
        this.mExtraMessage = null;
    }

    public ImsReasonInfo(int i, int i2, java.lang.String str) {
        this.mCode = i;
        this.mExtraCode = i2;
        this.mExtraMessage = str;
    }

    public int getCode() {
        return this.mCode;
    }

    public int getExtraCode() {
        return this.mExtraCode;
    }

    public java.lang.String getExtraMessage() {
        return this.mExtraMessage;
    }

    public java.lang.String toString() {
        return "ImsReasonInfo :: {" + this.mCode + " : " + (sImsCodeMap.containsKey(java.lang.Integer.valueOf(this.mCode)) ? sImsCodeMap.get(java.lang.Integer.valueOf(this.mCode)) : "UNKNOWN_CODE") + ", " + this.mExtraCode + ", " + this.mExtraMessage + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mCode);
        parcel.writeInt(this.mExtraCode);
        parcel.writeString(this.mExtraMessage);
    }
}
