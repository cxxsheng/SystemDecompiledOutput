package android.hardware.radio.network;

/* loaded from: classes2.dex */
public interface RegistrationFailCause$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? android.security.keystore.KeyProperties.DIGEST_NONE : i == 2 ? "IMSI_UNKNOWN_IN_HLR" : i == 3 ? "ILLEGAL_MS" : i == 4 ? "IMSI_UNKNOWN_IN_VLR" : i == 5 ? "IMEI_NOT_ACCEPTED" : i == 6 ? "ILLEGAL_ME" : i == 7 ? "GPRS_SERVICES_NOT_ALLOWED" : i == 8 ? "GPRS_AND_NON_GPRS_SERVICES_NOT_ALLOWED" : i == 9 ? "MS_IDENTITY_CANNOT_BE_DERIVED_BY_NETWORK" : i == 10 ? "IMPLICITLY_DETACHED" : i == 11 ? "PLMN_NOT_ALLOWED" : i == 12 ? "LOCATION_AREA_NOT_ALLOWED" : i == 13 ? "ROAMING_NOT_ALLOWED" : i == 14 ? "GPRS_SERVICES_NOT_ALLOWED_IN_PLMN" : i == 15 ? "NO_SUITABLE_CELLS" : i == 15 ? "MSC_TEMPORARILY_NOT_REACHABLE" : i == 16 ? "MSC_TEMP_NOT_REACHABLE" : i == 17 ? "NETWORK_FAILURE" : i == 20 ? "MAC_FAILURE" : i == 21 ? "SYNC_FAILURE" : i == 22 ? "CONGESTION" : i == 23 ? "GSM_AUTHENTICATION_UNACCEPTABLE" : i == 25 ? "NOT_AUTHORIZED_FOR_THIS_CSG" : i == 26 ? "SMS_PROVIDED_BY_GPRS_IN_ROUTING_AREA" : i == 32 ? "SERVICE_OPTION_NOT_SUPPORTED" : i == 33 ? "SERVICE_OPTION_NOT_SUBSCRIBED" : i == 34 ? "SERVICE_OPTION_TEMPORARILY_OUT_OF_ORDER" : i == 38 ? "CALL_CANNOT_BE_IDENTIFIED" : i == 40 ? "NO_PDP_CONTEXT_ACTIVATED" : i == 48 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_1" : i == 49 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_2" : i == 50 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_3" : i == 51 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_4" : i == 52 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_5" : i == 53 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_6" : i == 54 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_7" : i == 55 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_8" : i == 56 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_9" : i == 57 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_10" : i == 58 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_11" : i == 59 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_12" : i == 60 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_13" : i == 61 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_14" : i == 62 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_15" : i == 63 ? "RETRY_UPON_ENTRY_INTO_NEW_CELL_16" : i == 95 ? "SEMANTICALLY_INCORRECT_MESSAGE" : i == 96 ? "INVALID_MANDATORY_INFORMATION" : i == 97 ? "MESSAGE_TYPE_NON_EXISTENT_OR_NOT_IMPLEMENTED" : i == 98 ? "MESSAGE_TYPE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE" : i == 99 ? "INFORMATION_ELEMENT_NON_EXISTENT_OR_NOT_IMPLEMENTED" : i == 100 ? "CONDITIONAL_IE_ERROR" : i == 101 ? "MESSAGE_NOT_COMPATIBLE_WITH_PROTOCOL_STATE" : i == 111 ? "PROTOCOL_ERROR_UNSPECIFIED" : java.lang.Integer.toString(i);
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
