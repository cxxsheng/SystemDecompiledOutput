package com.google.android.mms.pdu;

/* loaded from: classes5.dex */
public class QuotedPrintable {
    private static byte ESCAPE_CHAR = 61;

    public static final byte[] decodeQuotedPrintable(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        int i = 0;
        while (i < bArr.length) {
            byte b = bArr[i];
            if (b == ESCAPE_CHAR) {
                int i2 = i + 1;
                try {
                    if ('\r' == ((char) bArr[i2])) {
                        i += 2;
                        if ('\n' == ((char) bArr[i])) {
                        }
                    }
                    int digit = java.lang.Character.digit((char) bArr[i2], 16);
                    int i3 = i2 + 1;
                    int digit2 = java.lang.Character.digit((char) bArr[i3], 16);
                    if (digit != -1 && digit2 != -1) {
                        byteArrayOutputStream.write((char) ((digit << 4) + digit2));
                        i = i3;
                    }
                    return null;
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                    return null;
                }
            }
            byteArrayOutputStream.write(b);
            i++;
        }
        return byteArrayOutputStream.toByteArray();
    }
}
