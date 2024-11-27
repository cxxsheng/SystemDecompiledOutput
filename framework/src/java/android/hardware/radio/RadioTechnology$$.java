package android.hardware.radio;

/* loaded from: classes2.dex */
public interface RadioTechnology$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? "UNKNOWN" : i == 1 ? "GPRS" : i == 2 ? "EDGE" : i == 3 ? "UMTS" : i == 4 ? "IS95A" : i == 5 ? "IS95B" : i == 6 ? "ONE_X_RTT" : i == 7 ? "EVDO_0" : i == 8 ? "EVDO_A" : i == 9 ? "HSDPA" : i == 10 ? "HSUPA" : i == 11 ? "HSPA" : i == 12 ? "EVDO_B" : i == 13 ? "EHRPD" : i == 14 ? com.android.internal.telephony.DctConstants.RAT_NAME_LTE : i == 15 ? "HSPAP" : i == 16 ? "GSM" : i == 17 ? "TD_SCDMA" : i == 18 ? "IWLAN" : i == 19 ? "LTE_CA" : i == 20 ? "NR" : java.lang.Integer.toString(i);
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
