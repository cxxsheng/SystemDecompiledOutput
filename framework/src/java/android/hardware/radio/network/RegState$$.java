package android.hardware.radio.network;

/* loaded from: classes2.dex */
public interface RegState$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? "NOT_REG_MT_NOT_SEARCHING_OP" : i == 1 ? "REG_HOME" : i == 2 ? "NOT_REG_MT_SEARCHING_OP" : i == 3 ? "REG_DENIED" : i == 4 ? "UNKNOWN" : i == 5 ? "REG_ROAMING" : i == 10 ? "NOT_REG_MT_NOT_SEARCHING_OP_EM" : i == 12 ? "NOT_REG_MT_SEARCHING_OP_EM" : i == 13 ? "REG_DENIED_EM" : i == 14 ? "UNKNOWN_EM" : i == 20 ? "REG_EM" : java.lang.Integer.toString(i);
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
