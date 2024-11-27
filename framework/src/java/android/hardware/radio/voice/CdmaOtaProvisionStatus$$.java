package android.hardware.radio.voice;

/* loaded from: classes2.dex */
public interface CdmaOtaProvisionStatus$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? "SPL_UNLOCKED" : i == 1 ? "SPC_RETRIES_EXCEEDED" : i == 2 ? "A_KEY_EXCHANGED" : i == 3 ? "SSD_UPDATED" : i == 4 ? "NAM_DOWNLOADED" : i == 5 ? "MDN_DOWNLOADED" : i == 6 ? "IMSI_DOWNLOADED" : i == 7 ? "PRL_DOWNLOADED" : i == 8 ? "COMMITTED" : i == 9 ? "OTAPA_STARTED" : i == 10 ? "OTAPA_STOPPED" : i == 11 ? "OTAPA_ABORTED" : java.lang.Integer.toString(i);
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
