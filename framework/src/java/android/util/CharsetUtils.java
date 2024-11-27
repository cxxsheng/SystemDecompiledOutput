package android.util;

/* loaded from: classes3.dex */
public class CharsetUtils {
    @dalvik.annotation.optimization.FastNative
    public static native java.lang.String fromModifiedUtf8Bytes(long j, int i, int i2);

    @dalvik.annotation.optimization.FastNative
    private static native int toModifiedUtf8Bytes(java.lang.String str, int i, long j, int i2, int i3);

    public static int toModifiedUtf8Bytes(java.lang.String str, long j, int i, int i2) {
        return toModifiedUtf8Bytes(str, str.length(), j, i, i2);
    }
}
