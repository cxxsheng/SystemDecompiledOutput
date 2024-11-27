package com.android.net.module.util;

/* loaded from: classes5.dex */
public class ByteUtils {
    public static int indexOf(byte[] bArr, byte b) {
        return indexOf(bArr, b, 0, bArr.length);
    }

    private static int indexOf(byte[] bArr, byte b, int i, int i2) {
        while (i < i2) {
            if (bArr[i] != b) {
                i++;
            } else {
                return i;
            }
        }
        return -1;
    }

    public static byte[] concat(byte[]... bArr) {
        int i = 0;
        for (byte[] bArr2 : bArr) {
            i += bArr2.length;
        }
        byte[] bArr3 = new byte[i];
        int i2 = 0;
        for (byte[] bArr4 : bArr) {
            java.lang.System.arraycopy(bArr4, 0, bArr3, i2, bArr4.length);
            i2 += bArr4.length;
        }
        return bArr3;
    }
}
