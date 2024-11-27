package android.content.integrity;

/* loaded from: classes.dex */
public class IntegrityUtils {
    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

    public static byte[] getBytesFromHexDigest(java.lang.String str) {
        com.android.internal.util.Preconditions.checkArgument(str.length() % 2 == 0, "Invalid hex encoding %s: must have even length", str);
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            char charAt = str.charAt(i2);
            bArr[i] = (byte) (hexToDec(str.charAt(i2 + 1)) | (hexToDec(charAt) << 4));
        }
        return bArr;
    }

    public static java.lang.String getHexDigest(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = (bArr[i] >>> 4) & 15;
            int i3 = bArr[i] & 15;
            int i4 = i * 2;
            cArr[i4] = decToHex(i2);
            cArr[i4 + 1] = decToHex(i3);
        }
        return new java.lang.String(cArr);
    }

    private static int hexToDec(int i) {
        if (i >= 48 && i <= 57) {
            return i - 48;
        }
        if (i >= 97 && i <= 102) {
            return (i - 97) + 10;
        }
        if (i >= 65 && i <= 70) {
            return (i - 65) + 10;
        }
        throw new java.lang.IllegalArgumentException("Invalid hex char " + i);
    }

    private static char decToHex(int i) {
        if (i >= 0 && i < HEX_CHARS.length) {
            return HEX_CHARS[i];
        }
        throw new java.lang.IllegalArgumentException("Invalid dec value to be converted to hex digit " + i);
    }
}
