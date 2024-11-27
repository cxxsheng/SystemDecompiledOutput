package com.android.internal.telephony.uicc;

/* loaded from: classes5.dex */
public class IccUtils {
    static final int FPLMN_BYTE_SIZE = 3;
    private static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', android.text.format.DateFormat.CAPITAL_AM_PM, 'B', 'C', 'D', android.text.format.DateFormat.DAY, 'F'};
    static final java.lang.String LOG_TAG = "IccUtils";
    public static final java.lang.String TEST_ICCID = "FFFFFFFFFFFFFFFFFFFF";

    public static java.lang.String bcdToString(byte[] bArr, int i, int i2) {
        int i3;
        java.lang.StringBuilder sb = new java.lang.StringBuilder(i2 * 2);
        for (int i4 = i; i4 < i + i2 && (i3 = bArr[i4] & 15) <= 9; i4++) {
            sb.append((char) (i3 + 48));
            int i5 = (bArr[i4] >> 4) & 15;
            if (i5 != 15) {
                if (i5 > 9) {
                    break;
                }
                sb.append((char) (i5 + 48));
            }
        }
        return sb.toString();
    }

    public static java.lang.String bcdToString(byte[] bArr) {
        return bcdToString(bArr, 0, bArr.length);
    }

    public static byte[] bcdToBytes(java.lang.String str) {
        byte[] bArr = new byte[(str.length() + 1) / 2];
        bcdToBytes(str, bArr);
        return bArr;
    }

    public static void bcdToBytes(java.lang.String str, byte[] bArr) {
        bcdToBytes(str, bArr, 0);
    }

    public static void bcdToBytes(java.lang.String str, byte[] bArr, int i) {
        if (str.length() % 2 != 0) {
            str = str + android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS;
        }
        int min = java.lang.Math.min((bArr.length - i) * 2, str.length());
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            if (i3 < min) {
                bArr[i] = (byte) ((charToByte(str.charAt(i3)) << 4) | charToByte(str.charAt(i2)));
                i2 += 2;
                i++;
            } else {
                return;
            }
        }
    }

    public static java.lang.String bcdPlmnToString(byte[] bArr, int i) {
        if (i + 3 > bArr.length) {
            return null;
        }
        int i2 = i + 0;
        int i3 = i + 1;
        int i4 = i + 2;
        java.lang.String bytesToHexString = bytesToHexString(new byte[]{(byte) (((bArr[i2] >> 4) & 15) | (bArr[i2] << 4)), (byte) ((bArr[i3] << 4) | (bArr[i4] & 15)), (byte) (((bArr[i3] >> 4) & 15) | (bArr[i4] & 240))});
        if (bytesToHexString.contains("F")) {
            return bytesToHexString.replaceAll("F", "");
        }
        return bytesToHexString;
    }

    public static void stringToBcdPlmn(java.lang.String str, byte[] bArr, int i) {
        char charAt = str.length() > 5 ? str.charAt(5) : 'F';
        bArr[i] = (byte) ((charToByte(str.charAt(1)) << 4) | charToByte(str.charAt(0)));
        bArr[i + 1] = (byte) ((charToByte(charAt) << 4) | charToByte(str.charAt(2)));
        bArr[i + 2] = (byte) (charToByte(str.charAt(3)) | (charToByte(str.charAt(4)) << 4));
    }

    public static java.lang.String bchToString(byte[] bArr, int i, int i2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(i2 * 2);
        for (int i3 = i; i3 < i + i2; i3++) {
            sb.append(HEX_CHARS[bArr[i3] & 15]);
            sb.append(HEX_CHARS[(bArr[i3] >> 4) & 15]);
        }
        return sb.toString();
    }

    public static java.lang.String cdmaBcdToString(byte[] bArr, int i, int i2) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(i2);
        int i3 = 0;
        while (i3 < i2) {
            int i4 = bArr[i] & 15;
            if (i4 > 9) {
                i4 = 0;
            }
            sb.append((char) (i4 + 48));
            int i5 = i3 + 1;
            if (i5 == i2) {
                break;
            }
            int i6 = (bArr[i] >> 4) & 15;
            if (i6 > 9) {
                i6 = 0;
            }
            sb.append((char) (i6 + 48));
            i3 = i5 + 1;
            i++;
        }
        return sb.toString();
    }

    public static int gsmBcdByteToInt(byte b) {
        int i;
        if ((b & 240) > 144) {
            i = 0;
        } else {
            i = (b >> 4) & 15;
        }
        int i2 = b & 15;
        if (i2 <= 9) {
            return i + (i2 * 10);
        }
        return i;
    }

    public static int cdmaBcdByteToInt(byte b) {
        int i;
        if ((b & 240) > 144) {
            i = 0;
        } else {
            i = ((b >> 4) & 15) * 10;
        }
        int i2 = b & 15;
        if (i2 <= 9) {
            return i + i2;
        }
        return i;
    }

    public static byte[] stringToAdnStringField(java.lang.String str) {
        int countGsmSeptetsUsingTables = com.android.internal.telephony.GsmAlphabet.countGsmSeptetsUsingTables(str, false, 0, 0);
        if (countGsmSeptetsUsingTables != -1) {
            byte[] bArr = new byte[countGsmSeptetsUsingTables];
            com.android.internal.telephony.GsmAlphabet.stringToGsm8BitUnpackedField(str, bArr, 0, countGsmSeptetsUsingTables);
            return bArr;
        }
        byte[] bytes = str.getBytes(java.nio.charset.StandardCharsets.UTF_16BE);
        byte[] bArr2 = new byte[bytes.length + 1];
        bArr2[0] = Byte.MIN_VALUE;
        java.lang.System.arraycopy(bytes, 0, bArr2, 1, bytes.length);
        return bArr2;
    }

    public static java.lang.String adnStringFieldToString(byte[] bArr, int i, int i2) {
        int i3;
        char c;
        java.lang.String str;
        java.lang.String str2 = "";
        if (i2 == 0) {
            return "";
        }
        boolean z = true;
        if (i2 >= 1 && bArr[i] == Byte.MIN_VALUE) {
            try {
                str = new java.lang.String(bArr, i + 1, ((i2 - 1) / 2) * 2, "utf-16be");
            } catch (java.io.UnsupportedEncodingException e) {
                com.android.telephony.Rlog.e(LOG_TAG, "implausible UnsupportedEncodingException", e);
                str = null;
            }
            if (str != null) {
                int length = str.length();
                while (length > 0 && str.charAt(length - 1) == 65535) {
                    length--;
                }
                return str.substring(0, length);
            }
        }
        if (i2 >= 3 && bArr[i] == -127) {
            i3 = bArr[i + 1] & 255;
            int i4 = i2 - 3;
            if (i3 > i4) {
                i3 = i4;
            }
            c = (char) ((bArr[i + 2] & 255) << 7);
            i += 3;
        } else if (i2 >= 4 && bArr[i] == -126) {
            i3 = bArr[i + 1] & 255;
            int i5 = i2 - 4;
            if (i3 > i5) {
                i3 = i5;
            }
            c = (char) (((bArr[i + 2] & 255) << 8) | (bArr[i + 3] & 255));
            i += 4;
        } else {
            z = false;
            i3 = 0;
            c = 0;
        }
        if (z) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            while (i3 > 0) {
                if (bArr[i] < 0) {
                    sb.append((char) ((bArr[i] & Byte.MAX_VALUE) + c));
                    i++;
                    i3--;
                }
                int i6 = 0;
                while (i6 < i3 && bArr[i + i6] >= 0) {
                    i6++;
                }
                sb.append(com.android.internal.telephony.GsmAlphabet.gsm8BitUnpackedToString(bArr, i, i6));
                i += i6;
                i3 -= i6;
            }
            return sb.toString();
        }
        try {
            str2 = android.content.res.Resources.getSystem().getString(com.android.internal.R.string.gsm_alphabet_default_charset);
        } catch (android.content.res.Resources.NotFoundException e2) {
        }
        return com.android.internal.telephony.GsmAlphabet.gsm8BitUnpackedToString(bArr, i, i2, str2.trim());
    }

    public static int hexCharToInt(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return (c - android.text.format.DateFormat.CAPITAL_AM_PM) + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return (c - android.text.format.DateFormat.AM_PM) + 10;
        }
        throw new java.lang.RuntimeException("invalid hex char '" + c + "'");
    }

    public static byte[] hexStringToBytes(java.lang.String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((hexCharToInt(str.charAt(i)) << 4) | hexCharToInt(str.charAt(i + 1)));
        }
        return bArr;
    }

    public static java.lang.String bytesToHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            sb.append(HEX_CHARS[(bArr[i] >> 4) & 15]);
            sb.append(HEX_CHARS[bArr[i] & 15]);
        }
        return sb.toString();
    }

    public static java.lang.String networkNameToString(byte[] bArr, int i, int i2) {
        java.lang.String str = "";
        if ((bArr[i] & 128) != 128 || i2 < 1) {
            return "";
        }
        switch ((bArr[i] >>> 4) & 7) {
            case 0:
                int i3 = i + 1;
                str = com.android.internal.telephony.GsmAlphabet.gsm7BitPackedToString(bArr, i3, (((i2 - 1) * 8) - (bArr[i] & 7)) / 7);
                break;
            case 1:
                try {
                    str = new java.lang.String(bArr, i + 1, i2 - 1, com.google.android.mms.pdu.CharacterSets.MIMENAME_UTF_16);
                    break;
                } catch (java.io.UnsupportedEncodingException e) {
                    com.android.telephony.Rlog.e(LOG_TAG, "implausible UnsupportedEncodingException", e);
                    break;
                }
        }
        byte b = bArr[i];
        return str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [int] */
    public static android.graphics.Bitmap parseToBnW(byte[] bArr, int i) {
        int i2 = 0;
        int i3 = bArr[0] & 255;
        int i4 = bArr[1] & 255;
        int i5 = i3 * i4;
        int[] iArr = new int[i5];
        int i6 = 2;
        byte b = 7;
        byte b2 = 0;
        while (i2 < i5) {
            if (i2 % 8 == 0) {
                int i7 = i6 + 1;
                byte b3 = bArr[i6];
                b = 7;
                i6 = i7;
                b2 = b3;
            }
            iArr[i2] = bitToRGB((b2 >> b) & 1);
            i2++;
            b--;
        }
        if (i2 != i5) {
            com.android.telephony.Rlog.e(LOG_TAG, "parse end and size error");
        }
        return android.graphics.Bitmap.createBitmap(iArr, i3, i4, android.graphics.Bitmap.Config.ARGB_8888);
    }

    private static int bitToRGB(int i) {
        if (i == 1) {
            return -1;
        }
        return -16777216;
    }

    public static android.graphics.Bitmap parseToRGB(byte[] bArr, int i, boolean z) {
        int[] mapToNon2OrderBitColor;
        int i2 = bArr[0] & 255;
        int i3 = bArr[1] & 255;
        int i4 = bArr[2] & 255;
        int i5 = bArr[3] & 255;
        int[] clut = getCLUT(bArr, ((bArr[4] & 255) << 8) | (bArr[5] & 255), i5);
        if (true == z) {
            clut[i5 - 1] = 0;
        }
        if (8 % i4 == 0) {
            mapToNon2OrderBitColor = mapTo2OrderBitColor(bArr, 6, i2 * i3, clut, i4);
        } else {
            mapToNon2OrderBitColor = mapToNon2OrderBitColor(bArr, 6, i2 * i3, clut, i4);
        }
        return android.graphics.Bitmap.createBitmap(mapToNon2OrderBitColor, i2, i3, android.graphics.Bitmap.Config.RGB_565);
    }

    private static int[] mapTo2OrderBitColor(byte[] bArr, int i, int i2, int[] iArr, int i3) {
        int i4;
        if (8 % i3 != 0) {
            com.android.telephony.Rlog.e(LOG_TAG, "not event number of color");
            return mapToNon2OrderBitColor(bArr, i, i2, iArr, i3);
        }
        switch (i3) {
            case 1:
                i4 = 1;
                break;
            case 2:
                i4 = 3;
                break;
            case 4:
                i4 = 15;
                break;
            case 8:
                i4 = 255;
                break;
            default:
                i4 = 1;
                break;
        }
        int[] iArr2 = new int[i2];
        int i5 = 8 / i3;
        int i6 = 0;
        while (i6 < i2) {
            int i7 = i + 1;
            int i8 = bArr[i];
            int i9 = 0;
            while (i9 < i5) {
                iArr2[i6] = iArr[(i8 >> (((i5 - i9) - 1) * i3)) & i4];
                i9++;
                i6++;
            }
            i = i7;
        }
        return iArr2;
    }

    private static int[] mapToNon2OrderBitColor(byte[] bArr, int i, int i2, int[] iArr, int i3) {
        if (8 % i3 == 0) {
            com.android.telephony.Rlog.e(LOG_TAG, "not odd number of color");
            return mapTo2OrderBitColor(bArr, i, i2, iArr, i3);
        }
        return new int[i2];
    }

    private static int[] getCLUT(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            return null;
        }
        int[] iArr = new int[i2];
        int i3 = (i2 * 3) + i;
        int i4 = 0;
        while (true) {
            int i5 = i4 + 1;
            int i6 = i + 1;
            int i7 = i6 + 1;
            int i8 = ((bArr[i] & 255) << 16) | (-16777216) | ((bArr[i6] & 255) << 8);
            int i9 = i7 + 1;
            iArr[i4] = i8 | (bArr[i7] & 255);
            if (i9 < i3) {
                i4 = i5;
                i = i9;
            } else {
                return iArr;
            }
        }
    }

    public static java.lang.String getDecimalSubstring(java.lang.String str) {
        int i = 0;
        while (i < str.length() && java.lang.Character.isDigit(str.charAt(i))) {
            i++;
        }
        return str.substring(0, i);
    }

    public static int bytesToInt(byte[] bArr, int i, int i2) {
        if (i2 > 4) {
            throw new java.lang.IllegalArgumentException("length must be <= 4 (only 32-bit integer supported): " + i2);
        }
        if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
            throw new java.lang.IndexOutOfBoundsException("Out of the bounds: src=[" + bArr.length + "], offset=" + i + ", length=" + i2);
        }
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 = (i3 << 8) | (bArr[i + i4] & 255);
        }
        if (i3 < 0) {
            throw new java.lang.IllegalArgumentException("src cannot be parsed as a positive integer: " + i3);
        }
        return i3;
    }

    public static long bytesToRawLong(byte[] bArr, int i, int i2) {
        if (i2 > 8) {
            throw new java.lang.IllegalArgumentException("length must be <= 8 (only 64-bit long supported): " + i2);
        }
        if (i < 0 || i2 < 0 || i + i2 > bArr.length) {
            throw new java.lang.IndexOutOfBoundsException("Out of the bounds: src=[" + bArr.length + "], offset=" + i + ", length=" + i2);
        }
        long j = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            j = (j << 8) | (bArr[i + i3] & 255);
        }
        return j;
    }

    public static byte[] unsignedIntToBytes(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("value must be 0 or positive: " + i);
        }
        byte[] bArr = new byte[byteNumForUnsignedInt(i)];
        unsignedIntToBytes(i, bArr, 0);
        return bArr;
    }

    public static byte[] signedIntToBytes(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("value must be 0 or positive: " + i);
        }
        byte[] bArr = new byte[byteNumForSignedInt(i)];
        signedIntToBytes(i, bArr, 0);
        return bArr;
    }

    public static int unsignedIntToBytes(int i, byte[] bArr, int i2) {
        return intToBytes(i, bArr, i2, false);
    }

    public static int signedIntToBytes(int i, byte[] bArr, int i2) {
        return intToBytes(i, bArr, i2, true);
    }

    public static int byteNumForUnsignedInt(int i) {
        return byteNumForInt(i, false);
    }

    public static int byteNumForSignedInt(int i) {
        return byteNumForInt(i, true);
    }

    private static int intToBytes(int i, byte[] bArr, int i2, boolean z) {
        int byteNumForInt = byteNumForInt(i, z);
        if (i2 < 0 || i2 + byteNumForInt > bArr.length) {
            throw new java.lang.IndexOutOfBoundsException("Not enough space to write. Required bytes: " + byteNumForInt);
        }
        int i3 = byteNumForInt - 1;
        while (i3 >= 0) {
            bArr[i2 + i3] = (byte) (i & 255);
            i3--;
            i >>>= 8;
        }
        return byteNumForInt;
    }

    private static int byteNumForInt(int i, boolean z) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("value must be 0 or positive: " + i);
        }
        if (z) {
            if (i <= 127) {
                return 1;
            }
            if (i <= 32767) {
                return 2;
            }
            if (i <= 8388607) {
                return 3;
            }
            return 4;
        }
        if (i <= 255) {
            return 1;
        }
        if (i <= 65535) {
            return 2;
        }
        if (i <= 16777215) {
            return 3;
        }
        return 4;
    }

    public static byte countTrailingZeros(byte b) {
        byte b2;
        if (b == 0) {
            return (byte) 8;
        }
        int i = b & 255;
        if ((i & 15) == 0) {
            b2 = 7;
        } else {
            b2 = (byte) 3;
        }
        if ((i & 51) != 0) {
            b2 = (byte) (b2 - 2);
        }
        if ((i & 85) != 0) {
            return (byte) (b2 - 1);
        }
        return b2;
    }

    public static java.lang.String byteToHex(byte b) {
        return new java.lang.String(new char[]{HEX_CHARS[(b & 255) >>> 4], HEX_CHARS[b & 15]});
    }

    public static java.lang.String stripTrailingFs(java.lang.String str) {
        if ("FFFFFFFFFFFFFFFFFFFF".equals(str)) {
            return str;
        }
        if (str == null) {
            return null;
        }
        return str.replaceAll("(?i)f*$", "");
    }

    public static boolean compareIgnoreTrailingFs(java.lang.String str, java.lang.String str2) {
        return android.text.TextUtils.equals(str, str2) || android.text.TextUtils.equals(stripTrailingFs(str), stripTrailingFs(str2));
    }

    private static byte charToByte(char c) {
        if (c >= '0' && c <= '9') {
            return (byte) (c - '0');
        }
        if (c >= 'A' && c <= 'F') {
            return (byte) (c - '7');
        }
        if (c >= 'a' && c <= 'f') {
            return (byte) (c - 'W');
        }
        return (byte) 0;
    }

    public static byte[] encodeFplmns(java.util.List<java.lang.String> list, int i) {
        byte[] bArr = new byte[i];
        int i2 = 0;
        for (java.lang.String str : list) {
            if (i2 >= i) {
                break;
            }
            stringToBcdPlmn(str, bArr, i2);
            i2 += 3;
        }
        while (i2 < i) {
            bArr[i2] = -1;
            i2++;
        }
        return bArr;
    }
}
