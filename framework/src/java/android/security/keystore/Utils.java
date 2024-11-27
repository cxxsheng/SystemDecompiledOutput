package android.security.keystore;

/* loaded from: classes3.dex */
abstract class Utils {
    private Utils() {
    }

    static java.util.Date cloneIfNotNull(java.util.Date date) {
        if (date != null) {
            return (java.util.Date) date.clone();
        }
        return null;
    }

    static byte[] cloneIfNotNull(byte[] bArr) {
        if (bArr != null) {
            return (byte[]) bArr.clone();
        }
        return null;
    }

    static int[] cloneIfNotNull(int[] iArr) {
        if (iArr != null) {
            return (int[]) iArr.clone();
        }
        return null;
    }
}
