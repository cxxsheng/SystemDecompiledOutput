package android.hardware.radio.modem;

/* loaded from: classes2.dex */
public interface NvItem$$ {
    static java.lang.String toString(int i) {
        return i == 1 ? "CDMA_MEID" : i == 2 ? "CDMA_MIN" : i == 3 ? "CDMA_MDN" : i == 4 ? "CDMA_ACCOLC" : i == 11 ? "DEVICE_MSL" : i == 12 ? "RTN_RECONDITIONED_STATUS" : i == 13 ? "RTN_ACTIVATION_DATE" : i == 14 ? "RTN_LIFE_TIMER" : i == 15 ? "RTN_LIFE_CALLS" : i == 16 ? "RTN_LIFE_DATA_TX" : i == 17 ? "RTN_LIFE_DATA_RX" : i == 18 ? "OMADM_HFA_LEVEL" : i == 31 ? "MIP_PROFILE_NAI" : i == 32 ? "MIP_PROFILE_HOME_ADDRESS" : i == 33 ? "MIP_PROFILE_AAA_AUTH" : i == 34 ? "MIP_PROFILE_HA_AUTH" : i == 35 ? "MIP_PROFILE_PRI_HA_ADDR" : i == 36 ? "MIP_PROFILE_SEC_HA_ADDR" : i == 37 ? "MIP_PROFILE_REV_TUN_PREF" : i == 38 ? "MIP_PROFILE_HA_SPI" : i == 39 ? "MIP_PROFILE_AAA_SPI" : i == 40 ? "MIP_PROFILE_MN_HA_SS" : i == 41 ? "MIP_PROFILE_MN_AAA_SS" : i == 51 ? "CDMA_PRL_VERSION" : i == 52 ? "CDMA_BC10" : i == 53 ? "CDMA_BC14" : i == 54 ? "CDMA_SO68" : i == 55 ? "CDMA_SO73_COP0" : i == 56 ? "CDMA_SO73_COP1TO7" : i == 57 ? "CDMA_1X_ADVANCED_ENABLED" : i == 58 ? "CDMA_EHRPD_ENABLED" : i == 59 ? "CDMA_EHRPD_FORCED" : i == 71 ? "LTE_BAND_ENABLE_25" : i == 72 ? "LTE_BAND_ENABLE_26" : i == 73 ? "LTE_BAND_ENABLE_41" : i == 74 ? "LTE_SCAN_PRIORITY_25" : i == 75 ? "LTE_SCAN_PRIORITY_26" : i == 76 ? "LTE_SCAN_PRIORITY_41" : i == 77 ? "LTE_HIDDEN_BAND_PRIORITY_25" : i == 78 ? "LTE_HIDDEN_BAND_PRIORITY_26" : i == 79 ? "LTE_HIDDEN_BAND_PRIORITY_41" : java.lang.Integer.toString(i);
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
