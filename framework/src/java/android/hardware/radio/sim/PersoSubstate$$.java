package android.hardware.radio.sim;

/* loaded from: classes2.dex */
public interface PersoSubstate$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? "UNKNOWN" : i == 1 ? "IN_PROGRESS" : i == 2 ? "READY" : i == 3 ? "SIM_NETWORK" : i == 4 ? "SIM_NETWORK_SUBSET" : i == 5 ? "SIM_CORPORATE" : i == 6 ? "SIM_SERVICE_PROVIDER" : i == 7 ? "SIM_SIM" : i == 8 ? "SIM_NETWORK_PUK" : i == 9 ? "SIM_NETWORK_SUBSET_PUK" : i == 10 ? "SIM_CORPORATE_PUK" : i == 11 ? "SIM_SERVICE_PROVIDER_PUK" : i == 12 ? "SIM_SIM_PUK" : i == 13 ? "RUIM_NETWORK1" : i == 14 ? "RUIM_NETWORK2" : i == 15 ? "RUIM_HRPD" : i == 16 ? "RUIM_CORPORATE" : i == 17 ? "RUIM_SERVICE_PROVIDER" : i == 18 ? "RUIM_RUIM" : i == 19 ? "RUIM_NETWORK1_PUK" : i == 20 ? "RUIM_NETWORK2_PUK" : i == 21 ? "RUIM_HRPD_PUK" : i == 22 ? "RUIM_CORPORATE_PUK" : i == 23 ? "RUIM_SERVICE_PROVIDER_PUK" : i == 24 ? "RUIM_RUIM_PUK" : i == 25 ? "SIM_SPN" : i == 26 ? "SIM_SPN_PUK" : i == 27 ? "SIM_SP_EHPLMN" : i == 28 ? "SIM_SP_EHPLMN_PUK" : i == 29 ? "SIM_ICCID" : i == 30 ? "SIM_ICCID_PUK" : i == 31 ? "SIM_IMPI" : i == 32 ? "SIM_IMPI_PUK" : i == 33 ? "SIM_NS_SP" : i == 34 ? "SIM_NS_SP_PUK" : java.lang.Integer.toString(i);
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
