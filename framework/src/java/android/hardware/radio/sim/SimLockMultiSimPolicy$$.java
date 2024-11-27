package android.hardware.radio.sim;

/* loaded from: classes2.dex */
public interface SimLockMultiSimPolicy$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? "NO_MULTISIM_POLICY" : i == 1 ? "ONE_VALID_SIM_MUST_BE_PRESENT" : i == 2 ? "APPLY_TO_ALL_SLOTS" : i == 3 ? "APPLY_TO_ONLY_SLOT_1" : i == 4 ? "VALID_SIM_MUST_PRESENT_ON_SLOT_1" : i == 5 ? "ACTIVE_SERVICE_ON_SLOT_1_TO_UNBLOCK_OTHER_SLOTS" : i == 6 ? "ACTIVE_SERVICE_ON_ANY_SLOT_TO_UNBLOCK_OTHER_SLOTS" : i == 7 ? "ALL_SIMS_MUST_BE_VALID" : i == 8 ? "SLOT_POLICY_OTHER" : java.lang.Integer.toString(i);
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
