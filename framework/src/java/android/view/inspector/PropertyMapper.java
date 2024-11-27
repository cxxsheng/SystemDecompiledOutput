package android.view.inspector;

/* loaded from: classes4.dex */
public interface PropertyMapper {
    int mapBoolean(java.lang.String str, int i);

    int mapByte(java.lang.String str, int i);

    int mapChar(java.lang.String str, int i);

    int mapColor(java.lang.String str, int i);

    int mapDouble(java.lang.String str, int i);

    int mapFloat(java.lang.String str, int i);

    int mapGravity(java.lang.String str, int i);

    int mapInt(java.lang.String str, int i);

    int mapIntEnum(java.lang.String str, int i, java.util.function.IntFunction<java.lang.String> intFunction);

    int mapIntFlag(java.lang.String str, int i, java.util.function.IntFunction<java.util.Set<java.lang.String>> intFunction);

    int mapLong(java.lang.String str, int i);

    int mapObject(java.lang.String str, int i);

    int mapResourceId(java.lang.String str, int i);

    int mapShort(java.lang.String str, int i);

    public static class PropertyConflictException extends java.lang.RuntimeException {
        public PropertyConflictException(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            super(java.lang.String.format("Attempted to map property \"%s\" as type %s, but it is already mapped as %s.", str, str2, str3));
        }
    }
}
