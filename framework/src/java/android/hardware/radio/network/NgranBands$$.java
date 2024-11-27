package android.hardware.radio.network;

/* loaded from: classes2.dex */
public interface NgranBands$$ {
    static java.lang.String toString(int i) {
        return i == 1 ? "BAND_1" : i == 2 ? "BAND_2" : i == 3 ? "BAND_3" : i == 5 ? "BAND_5" : i == 7 ? "BAND_7" : i == 8 ? "BAND_8" : i == 12 ? "BAND_12" : i == 14 ? "BAND_14" : i == 18 ? "BAND_18" : i == 20 ? "BAND_20" : i == 25 ? "BAND_25" : i == 26 ? "BAND_26" : i == 28 ? "BAND_28" : i == 29 ? "BAND_29" : i == 30 ? "BAND_30" : i == 34 ? "BAND_34" : i == 38 ? "BAND_38" : i == 39 ? "BAND_39" : i == 40 ? "BAND_40" : i == 41 ? "BAND_41" : i == 46 ? "BAND_46" : i == 48 ? "BAND_48" : i == 50 ? "BAND_50" : i == 51 ? "BAND_51" : i == 53 ? "BAND_53" : i == 65 ? "BAND_65" : i == 66 ? "BAND_66" : i == 70 ? "BAND_70" : i == 71 ? "BAND_71" : i == 74 ? "BAND_74" : i == 75 ? "BAND_75" : i == 76 ? "BAND_76" : i == 77 ? "BAND_77" : i == 78 ? "BAND_78" : i == 79 ? "BAND_79" : i == 80 ? "BAND_80" : i == 81 ? "BAND_81" : i == 82 ? "BAND_82" : i == 83 ? "BAND_83" : i == 84 ? "BAND_84" : i == 86 ? "BAND_86" : i == 89 ? "BAND_89" : i == 90 ? "BAND_90" : i == 91 ? "BAND_91" : i == 92 ? "BAND_92" : i == 93 ? "BAND_93" : i == 94 ? "BAND_94" : i == 95 ? "BAND_95" : i == 96 ? "BAND_96" : i == 257 ? "BAND_257" : i == 258 ? "BAND_258" : i == 260 ? "BAND_260" : i == 261 ? "BAND_261" : java.lang.Integer.toString(i);
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
