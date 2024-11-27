package android.hardware.radio.network;

/* loaded from: classes2.dex */
public interface RadioBandMode$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? "BAND_MODE_UNSPECIFIED" : i == 1 ? "BAND_MODE_EURO" : i == 2 ? "BAND_MODE_USA" : i == 3 ? "BAND_MODE_JPN" : i == 4 ? "BAND_MODE_AUS" : i == 5 ? "BAND_MODE_AUS_2" : i == 6 ? "BAND_MODE_CELL_800" : i == 7 ? "BAND_MODE_PCS" : i == 8 ? "BAND_MODE_JTACS" : i == 9 ? "BAND_MODE_KOREA_PCS" : i == 10 ? "BAND_MODE_5_450M" : i == 11 ? "BAND_MODE_IMT2000" : i == 12 ? "BAND_MODE_7_700M_2" : i == 13 ? "BAND_MODE_8_1800M" : i == 14 ? "BAND_MODE_9_900M" : i == 15 ? "BAND_MODE_10_800M_2" : i == 16 ? "BAND_MODE_EURO_PAMR_400M" : i == 17 ? "BAND_MODE_AWS" : i == 18 ? "BAND_MODE_USA_2500M" : java.lang.Integer.toString(i);
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
