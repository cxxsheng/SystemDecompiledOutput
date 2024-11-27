package android.hardware.radio.network;

/* loaded from: classes2.dex */
public interface NasProtocolMessage$$ {
    static java.lang.String toString(int i) {
        return i == 0 ? "UNKNOWN" : i == 1 ? "ATTACH_REQUEST" : i == 2 ? "IDENTITY_RESPONSE" : i == 3 ? "DETACH_REQUEST" : i == 4 ? "TRACKING_AREA_UPDATE_REQUEST" : i == 5 ? "LOCATION_UPDATE_REQUEST" : i == 6 ? "AUTHENTICATION_AND_CIPHERING_RESPONSE" : i == 7 ? "REGISTRATION_REQUEST" : i == 8 ? "DEREGISTRATION_REQUEST" : i == 9 ? "CM_REESTABLISHMENT_REQUEST" : i == 10 ? "CM_SERVICE_REQUEST" : i == 11 ? "IMSI_DETACH_INDICATION" : java.lang.Integer.toString(i);
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
