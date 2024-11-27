package android.hardware.radio;

/* loaded from: classes2.dex */
public interface RadioError$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? android.security.keystore.KeyProperties.DIGEST_NONE : i == 1 ? "RADIO_NOT_AVAILABLE" : i == 2 ? "GENERIC_FAILURE" : i == 3 ? "PASSWORD_INCORRECT" : i == 4 ? "SIM_PIN2" : i == 5 ? "SIM_PUK2" : i == 6 ? "REQUEST_NOT_SUPPORTED" : i == 7 ? "CANCELLED" : i == 8 ? "OP_NOT_ALLOWED_DURING_VOICE_CALL" : i == 9 ? "OP_NOT_ALLOWED_BEFORE_REG_TO_NW" : i == 10 ? "SMS_SEND_FAIL_RETRY" : i == 11 ? "SIM_ABSENT" : i == 12 ? "SUBSCRIPTION_NOT_AVAILABLE" : i == 13 ? "MODE_NOT_SUPPORTED" : i == 14 ? "FDN_CHECK_FAILURE" : i == 15 ? "ILLEGAL_SIM_OR_ME" : i == 16 ? "MISSING_RESOURCE" : i == 17 ? "NO_SUCH_ELEMENT" : i == 18 ? "DIAL_MODIFIED_TO_USSD" : i == 19 ? "DIAL_MODIFIED_TO_SS" : i == 20 ? "DIAL_MODIFIED_TO_DIAL" : i == 21 ? "USSD_MODIFIED_TO_DIAL" : i == 22 ? "USSD_MODIFIED_TO_SS" : i == 23 ? "USSD_MODIFIED_TO_USSD" : i == 24 ? "SS_MODIFIED_TO_DIAL" : i == 25 ? "SS_MODIFIED_TO_USSD" : i == 26 ? "SUBSCRIPTION_NOT_SUPPORTED" : i == 27 ? "SS_MODIFIED_TO_SS" : i == 36 ? "LCE_NOT_SUPPORTED" : i == 37 ? "NO_MEMORY" : i == 38 ? "INTERNAL_ERR" : i == 39 ? "SYSTEM_ERR" : i == 40 ? "MODEM_ERR" : i == 41 ? "INVALID_STATE" : i == 42 ? "NO_RESOURCES" : i == 43 ? "SIM_ERR" : i == 44 ? "INVALID_ARGUMENTS" : i == 45 ? "INVALID_SIM_STATE" : i == 46 ? "INVALID_MODEM_STATE" : i == 47 ? "INVALID_CALL_ID" : i == 48 ? "NO_SMS_TO_ACK" : i == 49 ? "NETWORK_ERR" : i == 50 ? "REQUEST_RATE_LIMITED" : i == 51 ? "SIM_BUSY" : i == 52 ? "SIM_FULL" : i == 53 ? "NETWORK_REJECT" : i == 54 ? "OPERATION_NOT_ALLOWED" : i == 55 ? "EMPTY_RECORD" : i == 56 ? "INVALID_SMS_FORMAT" : i == 57 ? "ENCODING_ERR" : i == 58 ? "INVALID_SMSC_ADDRESS" : i == 59 ? "NO_SUCH_ENTRY" : i == 60 ? "NETWORK_NOT_READY" : i == 61 ? "NOT_PROVISIONED" : i == 62 ? "NO_SUBSCRIPTION" : i == 63 ? "NO_NETWORK_FOUND" : i == 64 ? "DEVICE_IN_USE" : i == 65 ? "ABORTED" : i == 66 ? "INVALID_RESPONSE" : i == 501 ? "OEM_ERROR_1" : i == 502 ? "OEM_ERROR_2" : i == 503 ? "OEM_ERROR_3" : i == 504 ? "OEM_ERROR_4" : i == 505 ? "OEM_ERROR_5" : i == 506 ? "OEM_ERROR_6" : i == 507 ? "OEM_ERROR_7" : i == 508 ? "OEM_ERROR_8" : i == 509 ? "OEM_ERROR_9" : i == 510 ? "OEM_ERROR_10" : i == 511 ? "OEM_ERROR_11" : i == 512 ? "OEM_ERROR_12" : i == 513 ? "OEM_ERROR_13" : i == 514 ? "OEM_ERROR_14" : i == 515 ? "OEM_ERROR_15" : i == 516 ? "OEM_ERROR_16" : i == 517 ? "OEM_ERROR_17" : i == 518 ? "OEM_ERROR_18" : i == 519 ? "OEM_ERROR_19" : i == 520 ? "OEM_ERROR_20" : i == 521 ? "OEM_ERROR_21" : i == 522 ? "OEM_ERROR_22" : i == 523 ? "OEM_ERROR_23" : i == 524 ? "OEM_ERROR_24" : i == 525 ? "OEM_ERROR_25" : i == 67 ? "SIMULTANEOUS_SMS_AND_CALL_NOT_ALLOWED" : i == 68 ? "ACCESS_BARRED" : i == 69 ? "BLOCKED_DUE_TO_CALL" : i == 70 ? "RF_HARDWARE_ISSUE" : i == 71 ? "NO_RF_CALIBRATION_INFO" : java.lang.Integer.toString(i);
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
