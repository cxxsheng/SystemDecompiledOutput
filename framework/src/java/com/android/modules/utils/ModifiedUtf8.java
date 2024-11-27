package com.android.modules.utils;

/* loaded from: classes5.dex */
public class ModifiedUtf8 {
    public static java.lang.String decode(byte[] bArr, char[] cArr, int i, int i2) throws java.io.UTFDataFormatException {
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            int i5 = i3 + 1;
            char c = (char) bArr[i3 + i];
            cArr[i4] = c;
            if (c < 128) {
                i4++;
                i3 = i5;
            } else {
                char c2 = cArr[i4];
                if ((c2 & 224) == 192) {
                    if (i5 >= i2) {
                        throw new java.io.UTFDataFormatException("bad second byte at " + i5);
                    }
                    int i6 = i5 + 1;
                    byte b = bArr[i5 + i];
                    if ((b & com.android.internal.midi.MidiConstants.STATUS_PROGRAM_CHANGE) != 128) {
                        throw new java.io.UTFDataFormatException("bad second byte at " + (i6 - 1));
                    }
                    cArr[i4] = (char) (((c2 & 31) << 6) | (b & 63));
                    i4++;
                    i3 = i6;
                } else if ((c2 & 240) == 224) {
                    int i7 = i5 + 1;
                    if (i7 >= i2) {
                        throw new java.io.UTFDataFormatException("bad third byte at " + i7);
                    }
                    byte b2 = bArr[i5 + i];
                    int i8 = i7 + 1;
                    byte b3 = bArr[i7 + i];
                    if ((b2 & com.android.internal.midi.MidiConstants.STATUS_PROGRAM_CHANGE) != 128 || (b3 & com.android.internal.midi.MidiConstants.STATUS_PROGRAM_CHANGE) != 128) {
                        throw new java.io.UTFDataFormatException("bad second or third byte at " + (i8 - 2));
                    }
                    cArr[i4] = (char) (((c2 & 15) << 12) | ((b2 & 63) << 6) | (b3 & 63));
                    i4++;
                    i3 = i8;
                } else {
                    throw new java.io.UTFDataFormatException("bad byte at " + (i5 - 1));
                }
            }
        }
        return new java.lang.String(cArr, 0, i4);
    }

    public static long countBytes(java.lang.String str, boolean z) throws java.io.UTFDataFormatException {
        int length = str.length();
        long j = 0;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt != 0 && charAt <= 127) {
                j++;
            } else if (charAt <= 2047) {
                j += 2;
            } else {
                j += 3;
            }
            if (z && j > 65535) {
                throw new java.io.UTFDataFormatException("String more than 65535 UTF bytes long");
            }
        }
        return j;
    }

    public static void encode(byte[] bArr, int i, java.lang.String str) {
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt != 0 && charAt <= 127) {
                bArr[i] = (byte) charAt;
                i++;
            } else if (charAt <= 2047) {
                int i3 = i + 1;
                bArr[i] = (byte) (((charAt >> 6) & 31) | 192);
                i = i3 + 1;
                bArr[i3] = (byte) ((charAt & '?') | 128);
            } else {
                int i4 = i + 1;
                bArr[i] = (byte) (((charAt >> '\f') & 15) | 224);
                int i5 = i4 + 1;
                bArr[i4] = (byte) (((charAt >> 6) & 63) | 128);
                bArr[i5] = (byte) ((charAt & '?') | 128);
                i = i5 + 1;
            }
        }
    }

    private ModifiedUtf8() {
    }
}
