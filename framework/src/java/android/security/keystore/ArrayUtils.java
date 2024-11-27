package android.security.keystore;

/* loaded from: classes3.dex */
public abstract class ArrayUtils {
    private ArrayUtils() {
    }

    public static java.lang.String[] nullToEmpty(java.lang.String[] strArr) {
        return strArr != null ? strArr : libcore.util.EmptyArray.STRING;
    }

    public static java.lang.String[] cloneIfNotEmpty(java.lang.String[] strArr) {
        return (strArr == null || strArr.length <= 0) ? strArr : (java.lang.String[]) strArr.clone();
    }

    public static int[] cloneIfNotEmpty(int[] iArr) {
        return (iArr == null || iArr.length <= 0) ? iArr : (int[]) iArr.clone();
    }

    public static byte[] cloneIfNotEmpty(byte[] bArr) {
        return (bArr == null || bArr.length <= 0) ? bArr : (byte[]) bArr.clone();
    }

    public static byte[] concat(byte[] bArr, byte[] bArr2) {
        return concat(bArr, 0, bArr != null ? bArr.length : 0, bArr2, 0, bArr2 != null ? bArr2.length : 0);
    }

    public static byte[] concat(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        if (i2 == 0) {
            return subarray(bArr2, i3, i4);
        }
        if (i4 == 0) {
            return subarray(bArr, i, i2);
        }
        byte[] bArr3 = new byte[i2 + i4];
        java.lang.System.arraycopy(bArr, i, bArr3, 0, i2);
        java.lang.System.arraycopy(bArr2, i3, bArr3, i2, i4);
        return bArr3;
    }

    public static int copy(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        if (bArr2 == null || bArr == null) {
            return 0;
        }
        if (i3 > bArr2.length - i2) {
            i3 = bArr2.length - i2;
        }
        if (i3 > bArr.length - i) {
            i3 = bArr.length - i;
        }
        if (i3 <= 0) {
            return 0;
        }
        java.lang.System.arraycopy(bArr, i, bArr2, i2, i3);
        return i3;
    }

    public static byte[] subarray(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return libcore.util.EmptyArray.BYTE;
        }
        if (i == 0 && i2 == bArr.length) {
            return bArr;
        }
        byte[] bArr2 = new byte[i2];
        java.lang.System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    public static int[] concat(int[] iArr, int[] iArr2) {
        if (iArr == null || iArr.length == 0) {
            return iArr2;
        }
        if (iArr2 == null || iArr2.length == 0) {
            return iArr;
        }
        int[] iArr3 = new int[iArr.length + iArr2.length];
        java.lang.System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
        java.lang.System.arraycopy(iArr2, 0, iArr3, iArr.length, iArr2.length);
        return iArr3;
    }

    public static void forEach(int[] iArr, java.util.function.Consumer<java.lang.Integer> consumer) {
        for (int i : iArr) {
            consumer.accept(java.lang.Integer.valueOf(i));
        }
    }
}
