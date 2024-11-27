package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public interface AnnouncementType$$ {
    static java.lang.String toString(byte b) {
        return b == 0 ? "INVALID" : b == 1 ? "EMERGENCY" : b == 2 ? "WARNING" : b == 3 ? "TRAFFIC" : b == 4 ? "WEATHER" : b == 5 ? "NEWS" : b == 6 ? "EVENT" : b == 7 ? "SPORT" : b == 8 ? "MISC" : java.lang.Byte.toString(b);
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
            if (cls != byte[].class) {
                throw new java.lang.IllegalArgumentException("wrong type: " + cls);
            }
            byte[] bArr = (byte[]) obj;
            int length = bArr.length;
            while (i < length) {
                stringJoiner.add(toString(bArr[i]));
                i++;
            }
        }
        return stringJoiner.toString();
    }
}
