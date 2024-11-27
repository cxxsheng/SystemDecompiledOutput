package android.hardware.radio;

/* loaded from: classes2.dex */
public interface RadioAccessFamily$$ {
    static java.lang.String toString(int i) {
        return i == 1 ? "UNKNOWN" : i == 2 ? "GPRS" : i == 4 ? "EDGE" : i == 8 ? "UMTS" : i == 16 ? "IS95A" : i == 32 ? "IS95B" : i == 64 ? "ONE_X_RTT" : i == 128 ? "EVDO_0" : i == 256 ? "EVDO_A" : i == 512 ? "HSDPA" : i == 1024 ? "HSUPA" : i == 2048 ? "HSPA" : i == 4096 ? "EVDO_B" : i == 8192 ? "EHRPD" : i == 16384 ? com.android.internal.telephony.DctConstants.RAT_NAME_LTE : i == 32768 ? "HSPAP" : i == 65536 ? "GSM" : i == 131072 ? "TD_SCDMA" : i == 262144 ? "IWLAN" : i == 524288 ? "LTE_CA" : i == 1048576 ? "NR" : java.lang.Integer.toString(i);
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
