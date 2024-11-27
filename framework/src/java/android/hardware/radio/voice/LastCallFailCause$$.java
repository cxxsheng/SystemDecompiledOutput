package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public interface LastCallFailCause$$ {
    static java.lang.String toString(int i) {
        return i == 1 ? "UNOBTAINABLE_NUMBER" : i == 3 ? "NO_ROUTE_TO_DESTINATION" : i == 6 ? "CHANNEL_UNACCEPTABLE" : i == 8 ? "OPERATOR_DETERMINED_BARRING" : i == 16 ? android.database.sqlite.SQLiteDatabase.SYNC_MODE_NORMAL : i == 17 ? "BUSY" : i == 18 ? "NO_USER_RESPONDING" : i == 19 ? "NO_ANSWER_FROM_USER" : i == 21 ? "CALL_REJECTED" : i == 22 ? "NUMBER_CHANGED" : i == 25 ? "PREEMPTION" : i == 27 ? "DESTINATION_OUT_OF_ORDER" : i == 28 ? "INVALID_NUMBER_FORMAT" : i == 29 ? "FACILITY_REJECTED" : i == 30 ? "RESP_TO_STATUS_ENQUIRY" : i == 31 ? "NORMAL_UNSPECIFIED" : i == 34 ? "CONGESTION" : i == 38 ? "NETWORK_OUT_OF_ORDER" : i == 41 ? "TEMPORARY_FAILURE" : i == 42 ? "SWITCHING_EQUIPMENT_CONGESTION" : i == 43 ? "ACCESS_INFORMATION_DISCARDED" : i == 44 ? "REQUESTED_CIRCUIT_OR_CHANNEL_NOT_AVAILABLE" : i == 47 ? "RESOURCES_UNAVAILABLE_OR_UNSPECIFIED" : i == 49 ? "QOS_UNAVAILABLE" : i == 50 ? "REQUESTED_FACILITY_NOT_SUBSCRIBED" : i == 55 ? "INCOMING_CALLS_BARRED_WITHIN_CUG" : i == 57 ? "BEARER_CAPABILITY_NOT_AUTHORIZED" : i == 58 ? "BEARER_CAPABILITY_UNAVAILABLE" : i == 63 ? "SERVICE_OPTION_NOT_AVAILABLE" : i == 65 ? "BEARER_SERVICE_NOT_IMPLEMENTED" : i == 68 ? "ACM_LIMIT_EXCEEDED" : i == 69 ? "REQUESTED_FACILITY_NOT_IMPLEMENTED" : i == 70 ? "ONLY_DIGITAL_INFORMATION_BEARER_AVAILABLE" : i == 79 ? "SERVICE_OR_OPTION_NOT_IMPLEMENTED" : i == 81 ? "INVALID_TRANSACTION_IDENTIFIER" : i == 87 ? "USER_NOT_MEMBER_OF_CUG" : i == 88 ? "INCOMPATIBLE_DESTINATION" : i == 91 ? "INVALID_TRANSIT_NW_SELECTION" : i == 95 ? "SEMANTICALLY_INCORRECT_MESSAGE" : i == 96 ? "INVALID_MANDATORY_INFORMATION" : i == 97 ? "MESSAGE_TYPE_NON_IMPLEMENTED" : i == 98 ? "MESSAGE_TYPE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE" : i == 99 ? "INFORMATION_ELEMENT_NON_EXISTENT" : i == 100 ? "CONDITIONAL_IE_ERROR" : i == 101 ? "MESSAGE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE" : i == 102 ? "RECOVERY_ON_TIMER_EXPIRED" : i == 111 ? "PROTOCOL_ERROR_UNSPECIFIED" : i == 127 ? "INTERWORKING_UNSPECIFIED" : i == 240 ? "CALL_BARRED" : i == 241 ? "FDN_BLOCKED" : i == 242 ? "IMSI_UNKNOWN_IN_VLR" : i == 243 ? "IMEI_NOT_ACCEPTED" : i == 244 ? "DIAL_MODIFIED_TO_USSD" : i == 245 ? "DIAL_MODIFIED_TO_SS" : i == 246 ? "DIAL_MODIFIED_TO_DIAL" : i == 247 ? "RADIO_OFF" : i == 248 ? "OUT_OF_SERVICE" : i == 249 ? "NO_VALID_SIM" : i == 250 ? "RADIO_INTERNAL_ERROR" : i == 251 ? "NETWORK_RESP_TIMEOUT" : i == 252 ? "NETWORK_REJECT" : i == 253 ? "RADIO_ACCESS_FAILURE" : i == 254 ? "RADIO_LINK_FAILURE" : i == 255 ? "RADIO_LINK_LOST" : i == 256 ? "RADIO_UPLINK_FAILURE" : i == 257 ? "RADIO_SETUP_FAILURE" : i == 258 ? "RADIO_RELEASE_NORMAL" : i == 259 ? "RADIO_RELEASE_ABNORMAL" : i == 260 ? "ACCESS_CLASS_BLOCKED" : i == 261 ? "NETWORK_DETACH" : i == 1000 ? "CDMA_LOCKED_UNTIL_POWER_CYCLE" : i == 1001 ? "CDMA_DROP" : i == 1002 ? "CDMA_INTERCEPT" : i == 1003 ? "CDMA_REORDER" : i == 1004 ? "CDMA_SO_REJECT" : i == 1005 ? "CDMA_RETRY_ORDER" : i == 1006 ? "CDMA_ACCESS_FAILURE" : i == 1007 ? "CDMA_PREEMPTED" : i == 1008 ? "CDMA_NOT_EMERGENCY" : i == 1009 ? "CDMA_ACCESS_BLOCKED" : i == 61441 ? "OEM_CAUSE_1" : i == 61442 ? "OEM_CAUSE_2" : i == 61443 ? "OEM_CAUSE_3" : i == 61444 ? "OEM_CAUSE_4" : i == 61445 ? "OEM_CAUSE_5" : i == 61446 ? "OEM_CAUSE_6" : i == 61447 ? "OEM_CAUSE_7" : i == 61448 ? "OEM_CAUSE_8" : i == 61449 ? "OEM_CAUSE_9" : i == 61450 ? "OEM_CAUSE_10" : i == 61451 ? "OEM_CAUSE_11" : i == 61452 ? "OEM_CAUSE_12" : i == 61453 ? "OEM_CAUSE_13" : i == 61454 ? "OEM_CAUSE_14" : i == 61455 ? "OEM_CAUSE_15" : i == 65535 ? "ERROR_UNSPECIFIED" : java.lang.Integer.toString(i);
    }

    static java.lang.String arrayToString(java.lang.Object obj) {
        if (obj == null) {
            return "null";
        }
        java.lang.Class<?> cls = obj.getClass();
        if (!cls.isArray()) {
            throw new java.lang.IllegalArgumentException("not an array: " + obj);
        }
        java.lang.Class<?> componentType = cls.getComponentType();
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START, android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        int i = 0;
        if (componentType.isArray()) {
            while (i < java.lang.reflect.Array.getLength(obj)) {
                stringJoiner.add(arrayToString(java.lang.reflect.Array.get(obj, i)));
                i++;
            }
        } else {
            if (cls != int[].class) {
                throw new java.lang.IllegalArgumentException("wrong type: " + cls);
            }
            int[] iArr = (int[]) obj;
            int length = iArr.length;
            while (i < length) {
                stringJoiner.add(toString(iArr[i]));
                i++;
            }
        }
        return stringJoiner.toString();
    }
}
