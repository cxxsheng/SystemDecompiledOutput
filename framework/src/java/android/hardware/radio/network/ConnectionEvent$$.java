package android.hardware.radio.network;

/* loaded from: classes2.dex */
public interface ConnectionEvent$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? "CS_SIGNALLING_GSM" : i == 1 ? "PS_SIGNALLING_GPRS" : i == 2 ? "CS_SIGNALLING_3G" : i == 3 ? "PS_SIGNALLING_3G" : i == 4 ? "NAS_SIGNALLING_LTE" : i == 5 ? "AS_SIGNALLING_LTE" : i == 6 ? "VOLTE_SIP" : i == 7 ? "VOLTE_SIP_SOS" : i == 8 ? "VOLTE_RTP" : i == 9 ? "VOLTE_RTP_SOS" : i == 10 ? "NAS_SIGNALLING_5G" : i == 11 ? "AS_SIGNALLING_5G" : i == 12 ? "VONR_SIP" : i == 13 ? "VONR_SIP_SOS" : i == 14 ? "VONR_RTP" : i == 15 ? "VONR_RTP_SOS" : java.lang.Integer.toString(i);
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
