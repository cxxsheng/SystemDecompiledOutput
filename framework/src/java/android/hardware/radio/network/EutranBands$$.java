package android.hardware.radio.network;

/* loaded from: classes2.dex */
public interface EutranBands$$ {
    static java.lang.String toString(int i) {
        return i == 1 ? "BAND_1" : i == 2 ? "BAND_2" : i == 3 ? "BAND_3" : i == 4 ? "BAND_4" : i == 5 ? "BAND_5" : i == 6 ? "BAND_6" : i == 7 ? "BAND_7" : i == 8 ? "BAND_8" : i == 9 ? "BAND_9" : i == 10 ? "BAND_10" : i == 11 ? "BAND_11" : i == 12 ? "BAND_12" : i == 13 ? "BAND_13" : i == 14 ? "BAND_14" : i == 17 ? "BAND_17" : i == 18 ? "BAND_18" : i == 19 ? "BAND_19" : i == 20 ? "BAND_20" : i == 21 ? "BAND_21" : i == 22 ? "BAND_22" : i == 23 ? "BAND_23" : i == 24 ? "BAND_24" : i == 25 ? "BAND_25" : i == 26 ? "BAND_26" : i == 27 ? "BAND_27" : i == 28 ? "BAND_28" : i == 30 ? "BAND_30" : i == 31 ? "BAND_31" : i == 33 ? "BAND_33" : i == 34 ? "BAND_34" : i == 35 ? "BAND_35" : i == 36 ? "BAND_36" : i == 37 ? "BAND_37" : i == 38 ? "BAND_38" : i == 39 ? "BAND_39" : i == 40 ? "BAND_40" : i == 41 ? "BAND_41" : i == 42 ? "BAND_42" : i == 43 ? "BAND_43" : i == 44 ? "BAND_44" : i == 45 ? "BAND_45" : i == 46 ? "BAND_46" : i == 47 ? "BAND_47" : i == 48 ? "BAND_48" : i == 65 ? "BAND_65" : i == 66 ? "BAND_66" : i == 68 ? "BAND_68" : i == 70 ? "BAND_70" : i == 49 ? "BAND_49" : i == 50 ? "BAND_50" : i == 51 ? "BAND_51" : i == 52 ? "BAND_52" : i == 53 ? "BAND_53" : i == 71 ? "BAND_71" : i == 72 ? "BAND_72" : i == 73 ? "BAND_73" : i == 74 ? "BAND_74" : i == 85 ? "BAND_85" : i == 87 ? "BAND_87" : i == 88 ? "BAND_88" : java.lang.Integer.toString(i);
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
