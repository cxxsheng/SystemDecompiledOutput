package android.view.inspector;

/* loaded from: classes4.dex */
public interface PropertyReader {
    void readBoolean(int i, boolean z);

    void readByte(int i, byte b);

    void readChar(int i, char c);

    void readColor(int i, int i2);

    void readColor(int i, long j);

    void readColor(int i, android.graphics.Color color);

    void readDouble(int i, double d);

    void readFloat(int i, float f);

    void readGravity(int i, int i2);

    void readInt(int i, int i2);

    void readIntEnum(int i, int i2);

    void readIntFlag(int i, int i2);

    void readLong(int i, long j);

    void readObject(int i, java.lang.Object obj);

    void readResourceId(int i, int i2);

    void readShort(int i, short s);

    public static class PropertyTypeMismatchException extends java.lang.RuntimeException {
        public PropertyTypeMismatchException(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
            super(formatMessage(i, str, str2, str3));
        }

        public PropertyTypeMismatchException(int i, java.lang.String str, java.lang.String str2) {
            super(formatMessage(i, str, str2, null));
        }

        private static java.lang.String formatMessage(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
            if (str3 == null) {
                return java.lang.String.format("Attempted to read property with ID 0x%08X as type %s, but the ID is of type %s.", java.lang.Integer.valueOf(i), str, str2);
            }
            return java.lang.String.format("Attempted to read property \"%s\" with ID 0x%08X as type %s, but the ID is of type %s.", str3, java.lang.Integer.valueOf(i), str, str2);
        }
    }
}
