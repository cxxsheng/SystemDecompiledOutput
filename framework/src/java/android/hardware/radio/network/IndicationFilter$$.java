package android.hardware.radio.network;

/* loaded from: classes2.dex */
public interface IndicationFilter$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? android.security.keystore.KeyProperties.DIGEST_NONE : i == -1 ? "ALL" : i == 1 ? "SIGNAL_STRENGTH" : i == 2 ? "FULL_NETWORK_STATE" : i == 4 ? "DATA_CALL_DORMANCY_CHANGED" : i == 8 ? "LINK_CAPACITY_ESTIMATE" : i == 16 ? "PHYSICAL_CHANNEL_CONFIG" : i == 32 ? "REGISTRATION_FAILURE" : i == 64 ? "BARRING_INFO" : java.lang.Integer.toString(i);
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