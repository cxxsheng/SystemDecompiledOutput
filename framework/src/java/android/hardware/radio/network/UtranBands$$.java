package android.hardware.radio.network;

/* loaded from: classes2.dex */
public interface UtranBands$$ {
    static java.lang.String toString(int i) {
        return i == 1 ? "BAND_1" : i == 2 ? "BAND_2" : i == 3 ? "BAND_3" : i == 4 ? "BAND_4" : i == 5 ? "BAND_5" : i == 6 ? "BAND_6" : i == 7 ? "BAND_7" : i == 8 ? "BAND_8" : i == 9 ? "BAND_9" : i == 10 ? "BAND_10" : i == 11 ? "BAND_11" : i == 12 ? "BAND_12" : i == 13 ? "BAND_13" : i == 14 ? "BAND_14" : i == 19 ? "BAND_19" : i == 20 ? "BAND_20" : i == 21 ? "BAND_21" : i == 22 ? "BAND_22" : i == 25 ? "BAND_25" : i == 26 ? "BAND_26" : i == 101 ? "BAND_A" : i == 102 ? "BAND_B" : i == 103 ? "BAND_C" : i == 104 ? "BAND_D" : i == 105 ? "BAND_E" : i == 106 ? "BAND_F" : java.lang.Integer.toString(i);
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
