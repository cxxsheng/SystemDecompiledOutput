package android.hardware.radio.network;

/* loaded from: classes2.dex */
public interface GeranBands$$ {
    static java.lang.String toString(int i) {
        return i == 1 ? "BAND_T380" : i == 2 ? "BAND_T410" : i == 3 ? "BAND_450" : i == 4 ? "BAND_480" : i == 5 ? "BAND_710" : i == 6 ? "BAND_750" : i == 7 ? "BAND_T810" : i == 8 ? "BAND_850" : i == 9 ? "BAND_P900" : i == 10 ? "BAND_E900" : i == 11 ? "BAND_R900" : i == 12 ? "BAND_DCS1800" : i == 13 ? "BAND_PCS1900" : i == 14 ? "BAND_ER900" : java.lang.Integer.toString(i);
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
