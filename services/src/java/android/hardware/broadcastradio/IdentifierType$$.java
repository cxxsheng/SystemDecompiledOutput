package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public interface IdentifierType$$ {
    static java.lang.String toString(int i) {
        return i == 1000 ? "VENDOR_START" : i == 1999 ? "VENDOR_END" : i == 0 ? "INVALID" : i == 1 ? "AMFM_FREQUENCY_KHZ" : i == 2 ? "RDS_PI" : i == 3 ? "HD_STATION_ID_EXT" : i == 4 ? "HD_STATION_NAME" : i == 5 ? "DAB_SID_EXT" : i == 6 ? "DAB_ENSEMBLE" : i == 7 ? "DAB_SCID" : i == 8 ? "DAB_FREQUENCY_KHZ" : i == 9 ? "DRMO_SERVICE_ID" : i == 10 ? "DRMO_FREQUENCY_KHZ" : i == 12 ? "SXM_SERVICE_ID" : i == 13 ? "SXM_CHANNEL" : i == 14 ? "HD_STATION_LOCATION" : java.lang.Integer.toString(i);
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
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(", ", "[", "]");
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
