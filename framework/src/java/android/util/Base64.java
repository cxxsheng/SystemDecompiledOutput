package android.util;

/* loaded from: classes3.dex */
public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CRLF = 4;
    public static final int DEFAULT = 0;
    public static final int NO_CLOSE = 16;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int URL_SAFE = 8;

    static abstract class Coder {
        public int op;
        public byte[] output;

        public abstract int maxOutputSize(int i);

        public abstract boolean process(byte[] bArr, int i, int i2, boolean z);

        Coder() {
        }
    }

    public static byte[] decode(java.lang.String str, int i) {
        return decode(str.getBytes(), i);
    }

    public static byte[] decode(byte[] bArr, int i) {
        return decode(bArr, 0, bArr.length, i);
    }

    public static byte[] decode(byte[] bArr, int i, int i2, int i3) {
        android.util.Base64.Decoder decoder = new android.util.Base64.Decoder(i3, new byte[(i2 * 3) / 4]);
        if (!decoder.process(bArr, i, i2, true)) {
            throw new java.lang.IllegalArgumentException("bad base-64");
        }
        if (decoder.op == decoder.output.length) {
            return decoder.output;
        }
        byte[] bArr2 = new byte[decoder.op];
        java.lang.System.arraycopy(decoder.output, 0, bArr2, 0, decoder.op);
        return bArr2;
    }

    static class Decoder extends android.util.Base64.Coder {
        private static final int[] DECODE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] DECODE_WEBSAFE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int EQUALS = -2;
        private static final int SKIP = -1;
        private final int[] alphabet;
        private int state;
        private int value;

        public Decoder(int i, byte[] bArr) {
            this.output = bArr;
            this.alphabet = (i & 8) == 0 ? DECODE : DECODE_WEBSAFE;
            this.state = 0;
            this.value = 0;
        }

        @Override // android.util.Base64.Coder
        public int maxOutputSize(int i) {
            return ((i * 3) / 4) + 10;
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x00e9  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x00f0  */
        @Override // android.util.Base64.Coder
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean process(byte[] bArr, int i, int i2, boolean z) {
            if (this.state == 6) {
                return false;
            }
            int i3 = i2 + i;
            int i4 = this.state;
            int i5 = this.value;
            byte[] bArr2 = this.output;
            int[] iArr = this.alphabet;
            int i6 = 0;
            while (i < i3) {
                if (i4 == 0) {
                    while (true) {
                        int i7 = i + 4;
                        if (i7 <= i3 && (i5 = (iArr[bArr[i] & 255] << 18) | (iArr[bArr[i + 1] & 255] << 12) | (iArr[bArr[i + 2] & 255] << 6) | iArr[bArr[i + 3] & 255]) >= 0) {
                            bArr2[i6 + 2] = (byte) i5;
                            bArr2[i6 + 1] = (byte) (i5 >> 8);
                            bArr2[i6] = (byte) (i5 >> 16);
                            i6 += 3;
                            i = i7;
                        }
                    }
                    if (i >= i3) {
                        if (z) {
                            this.state = i4;
                            this.value = i5;
                            this.op = i6;
                            return true;
                        }
                        switch (i4) {
                            case 1:
                                this.state = 6;
                                return false;
                            case 2:
                                bArr2[i6] = (byte) (i5 >> 4);
                                i6++;
                                break;
                            case 3:
                                int i8 = i6 + 1;
                                bArr2[i6] = (byte) (i5 >> 10);
                                i6 = i8 + 1;
                                bArr2[i8] = (byte) (i5 >> 2);
                                break;
                            case 4:
                                this.state = 6;
                                return false;
                        }
                        this.state = i4;
                        this.op = i6;
                        return true;
                    }
                }
                int i9 = i + 1;
                int i10 = iArr[bArr[i] & 255];
                switch (i4) {
                    case 0:
                        if (i10 >= 0) {
                            i4++;
                            i5 = i10;
                            break;
                        } else if (i10 == -1) {
                            break;
                        } else {
                            this.state = 6;
                            return false;
                        }
                    case 1:
                        if (i10 >= 0) {
                            i4++;
                            i5 = i10 | (i5 << 6);
                            break;
                        } else if (i10 == -1) {
                            break;
                        } else {
                            this.state = 6;
                            return false;
                        }
                    case 2:
                        if (i10 >= 0) {
                            i4++;
                            i5 = i10 | (i5 << 6);
                            break;
                        } else if (i10 == -2) {
                            bArr2[i6] = (byte) (i5 >> 4);
                            i6++;
                            i4 = 4;
                            break;
                        } else if (i10 == -1) {
                            break;
                        } else {
                            this.state = 6;
                            return false;
                        }
                    case 3:
                        if (i10 >= 0) {
                            int i11 = i10 | (i5 << 6);
                            bArr2[i6 + 2] = (byte) i11;
                            bArr2[i6 + 1] = (byte) (i11 >> 8);
                            bArr2[i6] = (byte) (i11 >> 16);
                            i6 += 3;
                            i5 = i11;
                            i4 = 0;
                            break;
                        } else if (i10 == -2) {
                            bArr2[i6 + 1] = (byte) (i5 >> 2);
                            bArr2[i6] = (byte) (i5 >> 10);
                            i6 += 2;
                            i4 = 5;
                            break;
                        } else if (i10 == -1) {
                            break;
                        } else {
                            this.state = 6;
                            return false;
                        }
                    case 4:
                        if (i10 == -2) {
                            i4++;
                            break;
                        } else if (i10 == -1) {
                            break;
                        } else {
                            this.state = 6;
                            return false;
                        }
                    case 5:
                        if (i10 == -1) {
                            break;
                        } else {
                            this.state = 6;
                            return false;
                        }
                }
                i = i9;
            }
            if (z) {
            }
        }
    }

    public static java.lang.String encodeToString(byte[] bArr, int i) {
        try {
            return new java.lang.String(encode(bArr, i), "US-ASCII");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new java.lang.AssertionError(e);
        }
    }

    public static java.lang.String encodeToString(byte[] bArr, int i, int i2, int i3) {
        try {
            return new java.lang.String(encode(bArr, i, i2, i3), "US-ASCII");
        } catch (java.io.UnsupportedEncodingException e) {
            throw new java.lang.AssertionError(e);
        }
    }

    public static byte[] encode(byte[] bArr, int i) {
        return encode(bArr, 0, bArr.length, i);
    }

    public static byte[] encode(byte[] bArr, int i, int i2, int i3) {
        android.util.Base64.Encoder encoder = new android.util.Base64.Encoder(i3, null);
        int i4 = (i2 / 3) * 4;
        if (encoder.do_padding) {
            if (i2 % 3 > 0) {
                i4 += 4;
            }
        } else {
            switch (i2 % 3) {
                case 1:
                    i4 += 2;
                    break;
                case 2:
                    i4 += 3;
                    break;
            }
        }
        if (encoder.do_newline && i2 > 0) {
            i4 += (((i2 - 1) / 57) + 1) * (encoder.do_cr ? 2 : 1);
        }
        encoder.output = new byte[i4];
        encoder.process(bArr, i, i2, true);
        return encoder.output;
    }

    static class Encoder extends android.util.Base64.Coder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final byte[] ENCODE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        private static final byte[] ENCODE_WEBSAFE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        public static final int LINE_GROUPS = 19;
        private final byte[] alphabet;
        private int count;
        public final boolean do_cr;
        public final boolean do_newline;
        public final boolean do_padding;
        private final byte[] tail;
        int tailLen;

        public Encoder(int i, byte[] bArr) {
            this.output = bArr;
            this.do_padding = (i & 1) == 0;
            this.do_newline = (i & 2) == 0;
            this.do_cr = (i & 4) != 0;
            this.alphabet = (i & 8) == 0 ? ENCODE : ENCODE_WEBSAFE;
            this.tail = new byte[2];
            this.tailLen = 0;
            this.count = this.do_newline ? 19 : -1;
        }

        @Override // android.util.Base64.Coder
        public int maxOutputSize(int i) {
            return ((i * 8) / 5) + 10;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.util.Base64.Coder
        public boolean process(byte[] bArr, int i, int i2, boolean z) {
            int i3;
            int i4;
            int i5;
            byte b;
            byte b2;
            byte b3;
            int i6;
            byte[] bArr2 = this.alphabet;
            byte[] bArr3 = this.output;
            int i7 = this.count;
            int i8 = i2 + i;
            int i9 = 0;
            switch (this.tailLen) {
                case 0:
                default:
                    i3 = i;
                    i4 = -1;
                    break;
                case 1:
                    if (i + 2 <= i8) {
                        int i10 = i + 1;
                        int i11 = i10 + 1;
                        i4 = (bArr[i10] & 255) | ((this.tail[0] & 255) << 16) | ((bArr[i] & 255) << 8);
                        this.tailLen = 0;
                        i3 = i11;
                        break;
                    }
                    i3 = i;
                    i4 = -1;
                    break;
                case 2:
                    i3 = i + 1;
                    if (i3 <= i8) {
                        i4 = ((this.tail[0] & 255) << 16) | ((this.tail[1] & 255) << 8) | (bArr[i] & 255);
                        this.tailLen = 0;
                        break;
                    }
                    i3 = i;
                    i4 = -1;
                    break;
            }
            if (i4 == -1) {
                i5 = 0;
            } else {
                bArr3[0] = bArr2[(i4 >> 18) & 63];
                bArr3[1] = bArr2[(i4 >> 12) & 63];
                bArr3[2] = bArr2[(i4 >> 6) & 63];
                bArr3[3] = bArr2[i4 & 63];
                i7--;
                if (i7 != 0) {
                    i5 = 4;
                } else {
                    if (this.do_cr) {
                        bArr3[4] = 13;
                        i6 = 5;
                    } else {
                        i6 = 4;
                    }
                    i5 = i6 + 1;
                    bArr3[i6] = 10;
                    i7 = 19;
                }
            }
            while (true) {
                int i12 = i3 + 3;
                if (i12 <= i8) {
                    int i13 = (bArr[i3 + 2] & 255) | ((bArr[i3 + 1] & 255) << 8) | ((bArr[i3] & 255) << 16);
                    bArr3[i5] = bArr2[(i13 >> 18) & 63];
                    bArr3[i5 + 1] = bArr2[(i13 >> 12) & 63];
                    bArr3[i5 + 2] = bArr2[(i13 >> 6) & 63];
                    bArr3[i5 + 3] = bArr2[i13 & 63];
                    i5 += 4;
                    i7--;
                    if (i7 != 0) {
                        i3 = i12;
                    } else {
                        if (this.do_cr) {
                            bArr3[i5] = 13;
                            i5++;
                        }
                        bArr3[i5] = 10;
                        i5++;
                        i3 = i12;
                        i7 = 19;
                    }
                } else {
                    if (z) {
                        if (i3 - this.tailLen != i8 - 1) {
                            if (i3 - this.tailLen != i8 - 2) {
                                if (this.do_newline && i5 > 0 && i7 != 19) {
                                    if (this.do_cr) {
                                        bArr3[i5] = 13;
                                        i5++;
                                    }
                                    bArr3[i5] = 10;
                                    i5++;
                                }
                            } else {
                                if (this.tailLen > 1) {
                                    b = this.tail[0];
                                    i9 = 1;
                                } else {
                                    int i14 = i3 + 1;
                                    byte b4 = bArr[i3];
                                    i3 = i14;
                                    b = b4;
                                }
                                int i15 = (b & 255) << 10;
                                if (this.tailLen > 0) {
                                    b2 = this.tail[i9];
                                    i9++;
                                } else {
                                    b2 = bArr[i3];
                                }
                                int i16 = i15 | ((b2 & 255) << 2);
                                this.tailLen -= i9;
                                int i17 = i5 + 1;
                                bArr3[i5] = bArr2[(i16 >> 12) & 63];
                                int i18 = i17 + 1;
                                bArr3[i17] = bArr2[(i16 >> 6) & 63];
                                int i19 = i18 + 1;
                                bArr3[i18] = bArr2[i16 & 63];
                                if (this.do_padding) {
                                    bArr3[i19] = 61;
                                    i19++;
                                }
                                if (this.do_newline) {
                                    if (this.do_cr) {
                                        bArr3[i19] = 13;
                                        i19++;
                                    }
                                    bArr3[i19] = 10;
                                    i19++;
                                }
                                i5 = i19;
                            }
                        } else {
                            if (this.tailLen > 0) {
                                b3 = this.tail[0];
                                i9 = 1;
                            } else {
                                b3 = bArr[i3];
                            }
                            int i20 = (b3 & 255) << 4;
                            this.tailLen -= i9;
                            int i21 = i5 + 1;
                            bArr3[i5] = bArr2[(i20 >> 6) & 63];
                            int i22 = i21 + 1;
                            bArr3[i21] = bArr2[i20 & 63];
                            if (this.do_padding) {
                                int i23 = i22 + 1;
                                bArr3[i22] = 61;
                                i22 = i23 + 1;
                                bArr3[i23] = 61;
                            }
                            if (this.do_newline) {
                                if (this.do_cr) {
                                    bArr3[i22] = 13;
                                    i22++;
                                }
                                bArr3[i22] = 10;
                                i22++;
                            }
                            i5 = i22;
                        }
                    } else if (i3 == i8 - 1) {
                        byte[] bArr4 = this.tail;
                        int i24 = this.tailLen;
                        this.tailLen = i24 + 1;
                        bArr4[i24] = bArr[i3];
                    } else if (i3 == i8 - 2) {
                        byte[] bArr5 = this.tail;
                        int i25 = this.tailLen;
                        this.tailLen = i25 + 1;
                        bArr5[i25] = bArr[i3];
                        byte[] bArr6 = this.tail;
                        int i26 = this.tailLen;
                        this.tailLen = i26 + 1;
                        bArr6[i26] = bArr[i3 + 1];
                    }
                    this.op = i5;
                    this.count = i7;
                    return true;
                }
            }
        }
    }

    private Base64() {
    }
}
