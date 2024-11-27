package android.hardware.radio.data;

/* loaded from: classes2.dex */
public interface PdpProtocolType$$ {
    static java.lang.String toString(int i) {
        return i == -1 ? "UNKNOWN" : i == 0 ? android.telephony.CarrierConfigManager.Apn.PROTOCOL_IPV4 : i == 1 ? "IPV6" : i == 2 ? android.telephony.CarrierConfigManager.Apn.PROTOCOL_IPV4V6 : i == 3 ? "PPP" : i == 4 ? "NON_IP" : i == 5 ? "UNSTRUCTURED" : java.lang.Integer.toString(i);
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
