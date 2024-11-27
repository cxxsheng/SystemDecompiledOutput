package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public interface Result$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? "OK" : i == 1 ? "INTERNAL_ERROR" : i == 2 ? "INVALID_ARGUMENTS" : i == 3 ? "INVALID_STATE" : i == 4 ? "NOT_SUPPORTED" : i == 5 ? "TIMEOUT" : i == 6 ? "CANCELED" : i == 7 ? "UNKNOWN_ERROR" : java.lang.Integer.toString(i);
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
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "[", "]");
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
