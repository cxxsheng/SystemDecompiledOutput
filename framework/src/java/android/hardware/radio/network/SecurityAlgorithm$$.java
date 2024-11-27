package android.hardware.radio.network;

/* loaded from: classes2.dex */
public interface SecurityAlgorithm$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? "A50" : i == 1 ? "A51" : i == 2 ? "A52" : i == 3 ? "A53" : i == 4 ? "A54" : i == 14 ? "GEA0" : i == 15 ? "GEA1" : i == 16 ? "GEA2" : i == 17 ? "GEA3" : i == 18 ? "GEA4" : i == 19 ? "GEA5" : i == 29 ? "UEA0" : i == 30 ? "UEA1" : i == 31 ? "UEA2" : i == 41 ? "EEA0" : i == 42 ? "EEA1" : i == 43 ? "EEA2" : i == 44 ? "EEA3" : i == 55 ? "NEA0" : i == 56 ? "NEA1" : i == 57 ? "NEA2" : i == 58 ? "NEA3" : i == 66 ? "SIP_NO_IPSEC_CONFIG" : i == 67 ? "IMS_NULL" : i == 68 ? "SIP_NULL" : i == 69 ? "AES_GCM" : i == 70 ? "AES_GMAC" : i == 71 ? "AES_CBC" : i == 72 ? "DES_EDE3_CBC" : i == 73 ? "AES_EDE3_CBC" : i == 74 ? "HMAC_SHA1_96" : i == 75 ? "HMAC_MD5_96" : i == 85 ? "RTP" : i == 86 ? "SRTP_NULL" : i == 87 ? "SRTP_AES_COUNTER" : i == 88 ? "SRTP_AES_F8" : i == 89 ? "SRTP_HMAC_SHA1" : i == 99 ? "ENCR_AES_GCM_16" : i == 100 ? "ENCR_AES_CBC" : i == 101 ? "AUTH_HMAC_SHA2_256_128" : i == 113 ? "UNKNOWN" : i == 114 ? "OTHER" : i == 124 ? "ORYX" : java.lang.Integer.toString(i);
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
