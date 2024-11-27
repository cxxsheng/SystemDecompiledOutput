package android.graphics;

/* loaded from: classes.dex */
public class TemporaryBuffer {
    private static char[] sTemp = null;

    public static char[] obtain(int i) {
        char[] cArr;
        synchronized (android.graphics.TemporaryBuffer.class) {
            cArr = sTemp;
            sTemp = null;
        }
        if (cArr == null || cArr.length < i) {
            return com.android.internal.util.ArrayUtils.newUnpaddedCharArray(i);
        }
        return cArr;
    }

    public static void recycle(char[] cArr) {
        if (cArr.length > 1000) {
            return;
        }
        synchronized (android.graphics.TemporaryBuffer.class) {
            sTemp = cArr;
        }
    }
}
