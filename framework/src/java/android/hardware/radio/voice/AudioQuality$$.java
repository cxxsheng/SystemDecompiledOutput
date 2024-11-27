package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public interface AudioQuality$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? "UNSPECIFIED" : i == 1 ? "AMR" : i == 2 ? "AMR_WB" : i == 3 ? "GSM_EFR" : i == 4 ? "GSM_FR" : i == 5 ? "GSM_HR" : i == 6 ? "EVRC" : i == 7 ? "EVRC_B" : i == 8 ? "EVRC_WB" : i == 9 ? "EVRC_NW" : java.lang.Integer.toString(i);
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
