package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public interface ConfigFlag$$ {
    static java.lang.String toString(int i) {
        return i == 1 ? "FORCE_MONO" : i == 2 ? "FORCE_ANALOG" : i == 3 ? "FORCE_DIGITAL" : i == 4 ? "RDS_AF" : i == 5 ? "RDS_REG" : i == 6 ? "DAB_DAB_LINKING" : i == 7 ? "DAB_FM_LINKING" : i == 8 ? "DAB_DAB_SOFT_LINKING" : i == 9 ? "DAB_FM_SOFT_LINKING" : i == 10 ? "FORCE_ANALOG_FM" : i == 11 ? "FORCE_ANALOG_AM" : java.lang.Integer.toString(i);
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
