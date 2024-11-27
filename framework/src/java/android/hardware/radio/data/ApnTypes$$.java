package android.hardware.radio.data;

/* loaded from: classes2.dex */
public interface ApnTypes$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? android.security.keystore.KeyProperties.DIGEST_NONE : i == 1 ? "DEFAULT" : i == 2 ? "MMS" : i == 4 ? "SUPL" : i == 8 ? "DUN" : i == 16 ? "HIPRI" : i == 32 ? "FOTA" : i == 64 ? "IMS" : i == 128 ? "CBS" : i == 256 ? "IA" : i == 512 ? "EMERGENCY" : i == 1024 ? "MCX" : i == 2048 ? "XCAP" : i == 4096 ? "VSIM" : i == 8192 ? "BIP" : i == 16384 ? "ENTERPRISE" : i == 32768 ? "RCS" : java.lang.Integer.toString(i);
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
