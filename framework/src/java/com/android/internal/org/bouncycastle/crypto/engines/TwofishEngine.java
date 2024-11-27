package com.android.internal.org.bouncycastle.crypto.engines;

/* loaded from: classes4.dex */
public final class TwofishEngine implements com.android.internal.org.bouncycastle.crypto.BlockCipher {
    private static final int BLOCK_SIZE = 16;
    private static final int GF256_FDBK = 361;
    private static final int GF256_FDBK_2 = 180;
    private static final int GF256_FDBK_4 = 90;
    private static final int INPUT_WHITEN = 0;
    private static final int MAX_KEY_BITS = 256;
    private static final int MAX_ROUNDS = 16;
    private static final int OUTPUT_WHITEN = 4;
    private static final byte[][] P = {new byte[]{-87, 103, -77, -24, 4, -3, -93, 118, -102, -110, Byte.MIN_VALUE, 120, -28, -35, -47, 56, 13, -58, 53, -104, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, -9, -20, 108, 67, 117, 55, 38, -6, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -108, 72, com.android.internal.midi.MidiConstants.STATUS_SONG_POSITION, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, -117, 48, -124, 84, -33, 35, android.hardware.biometrics.face.AcquiredInfo.DARK_GLASSES_DETECTED, 91, 61, 89, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -82, -94, -126, 99, 1, -125, 46, -39, 81, -101, 124, -90, -21, -91, -66, android.hardware.biometrics.face.AcquiredInfo.SENSOR_DIRTY, 12, -29, 97, com.android.internal.midi.MidiConstants.STATUS_PROGRAM_CHANGE, -116, 58, -11, 115, 44, 37, 11, -69, 78, -119, 107, 83, 106, -76, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, -31, -26, -67, 69, -30, -12, -74, 102, -52, -107, 3, 86, -44, 28, 30, -41, -5, -61, -114, -75, -23, -49, -65, -70, -22, 119, 57, -81, 51, -55, 98, 113, -127, 121, 9, -83, 36, -51, -7, -40, -27, -59, -71, 77, 68, 8, -122, -25, -95, 29, -86, -19, 6, 112, -78, -46, 65, 123, com.android.internal.midi.MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 17, 49, -62, 39, com.android.internal.midi.MidiConstants.STATUS_NOTE_ON, com.android.net.module.util.NetworkStackConstants.TCPHDR_URG, -10, 96, -1, -106, 92, -79, -85, -98, -100, 82, com.android.internal.telephony.GsmAlphabet.GSM_EXTENDED_ESCAPE, 95, -109, 10, -17, -111, -123, 73, -18, 45, 79, -113, 59, 71, -121, 109, 70, -42, 62, 105, 100, 42, -50, -53, 47, -4, -105, 5, 122, -84, Byte.MAX_VALUE, -43, android.hardware.biometrics.face.AcquiredInfo.MOUTH_COVERING_DETECTED, 75, 14, -89, 90, 40, 20, 63, 41, -120, 60, 76, 2, -72, -38, com.android.internal.midi.MidiConstants.STATUS_CONTROL_CHANGE, android.hardware.biometrics.face.AcquiredInfo.VENDOR, 85, 31, -118, 125, 87, -57, -115, 116, -73, -60, -97, 114, 126, android.hardware.biometrics.face.AcquiredInfo.START, 34, android.hardware.biometrics.face.AcquiredInfo.TILT_TOO_EXTREME, 88, 7, -103, 52, 110, 80, -34, 104, 101, -68, -37, -8, -56, -88, 43, 64, -36, -2, 50, -92, -54, 16, 33, -16, -45, 93, 15, 0, 111, -99, 54, 66, 74, 94, -63, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND}, new byte[]{117, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -58, -12, -37, 123, -5, -56, 74, -45, -26, 107, 69, 125, -24, 75, -42, 50, -40, -3, 55, 113, com.android.internal.midi.MidiConstants.STATUS_MIDI_TIME_CODE, -31, 48, 15, -8, com.android.internal.telephony.GsmAlphabet.GSM_EXTENDED_ESCAPE, -121, -6, 6, 63, 94, -70, -82, 91, -118, 0, -68, -99, 109, -63, -79, 14, Byte.MIN_VALUE, 93, -46, -43, com.android.internal.midi.MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, -124, 7, 20, -75, com.android.internal.midi.MidiConstants.STATUS_NOTE_ON, 44, -93, -78, 115, 76, 84, -110, 116, 54, 81, 56, com.android.internal.midi.MidiConstants.STATUS_CONTROL_CHANGE, -67, 90, -4, 96, 98, -106, 108, 66, -9, 16, 124, 40, 39, -116, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -107, -100, -57, 36, 70, 59, 112, -54, -29, -123, -53, 17, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, -109, -72, -90, -125, com.android.net.module.util.NetworkStackConstants.TCPHDR_URG, -1, -97, 119, -61, -52, 3, 111, 8, -65, 64, -25, 43, -30, 121, 12, -86, -126, 65, 58, -22, -71, -28, -102, -92, -105, 126, -38, 122, android.hardware.biometrics.face.AcquiredInfo.VENDOR, 102, -108, -95, 29, 61, -16, -34, -77, 11, 114, -89, 28, -17, -47, 83, 62, -113, 51, 38, 95, -20, 118, 42, 73, -127, -120, -18, 33, -60, android.hardware.biometrics.face.AcquiredInfo.MOUTH_COVERING_DETECTED, -21, -39, -59, 57, -103, -51, -83, 49, -117, 1, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, 35, -35, 31, 78, 45, -7, 72, 79, com.android.internal.midi.MidiConstants.STATUS_SONG_POSITION, 101, -114, 120, 92, 88, android.hardware.biometrics.face.AcquiredInfo.DARK_GLASSES_DETECTED, -115, -27, -104, 87, 103, Byte.MAX_VALUE, 5, 100, -81, 99, -74, -2, -11, -73, 60, -91, -50, -23, 104, 68, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, 77, 67, 105, 41, 46, -84, android.hardware.biometrics.face.AcquiredInfo.START, 89, -88, 10, -98, 110, 71, -33, 52, 53, 106, -49, -36, 34, -55, com.android.internal.midi.MidiConstants.STATUS_PROGRAM_CHANGE, -101, -119, -44, -19, -85, android.hardware.biometrics.face.AcquiredInfo.TILT_TOO_EXTREME, -94, 13, 82, -69, 2, 47, -87, -41, 97, 30, -76, 80, 4, -10, -62, android.hardware.biometrics.face.AcquiredInfo.SENSOR_DIRTY, 37, -122, 86, 85, 9, -66, -111}};
    private static final int P_00 = 1;
    private static final int P_01 = 0;
    private static final int P_02 = 0;
    private static final int P_03 = 1;
    private static final int P_04 = 1;
    private static final int P_10 = 0;
    private static final int P_11 = 0;
    private static final int P_12 = 1;
    private static final int P_13 = 1;
    private static final int P_14 = 0;
    private static final int P_20 = 1;
    private static final int P_21 = 1;
    private static final int P_22 = 0;
    private static final int P_23 = 0;
    private static final int P_24 = 0;
    private static final int P_30 = 0;
    private static final int P_31 = 1;
    private static final int P_32 = 1;
    private static final int P_33 = 0;
    private static final int P_34 = 1;
    private static final int ROUNDS = 16;
    private static final int ROUND_SUBKEYS = 8;
    private static final int RS_GF_FDBK = 333;
    private static final int SK_BUMP = 16843009;
    private static final int SK_ROTL = 9;
    private static final int SK_STEP = 33686018;
    private static final int TOTAL_SUBKEYS = 40;
    private int[] gSBox;
    private int[] gSubKeys;
    private boolean encrypting = false;
    private int[] gMDS0 = new int[256];
    private int[] gMDS1 = new int[256];
    private int[] gMDS2 = new int[256];
    private int[] gMDS3 = new int[256];
    private int k64Cnt = 0;
    private byte[] workingKey = null;

    public TwofishEngine() {
        for (int i = 0; i < 256; i++) {
            int i2 = P[0][i] & 255;
            int Mx_X = Mx_X(i2) & 255;
            int Mx_Y = Mx_Y(i2) & 255;
            int i3 = P[1][i] & 255;
            int[] iArr = {i2, i3};
            int[] iArr2 = {Mx_X, Mx_X(i3) & 255};
            int[] iArr3 = {Mx_Y, Mx_Y(i3) & 255};
            this.gMDS0[i] = iArr[1] | (iArr2[1] << 8) | (iArr3[1] << 16) | (iArr3[1] << 24);
            this.gMDS1[i] = iArr3[0] | (iArr3[0] << 8) | (iArr2[0] << 16) | (iArr[0] << 24);
            this.gMDS2[i] = (iArr3[1] << 24) | iArr2[1] | (iArr3[1] << 8) | (iArr[1] << 16);
            this.gMDS3[i] = (iArr[0] << 8) | iArr2[0] | (iArr3[0] << 16) | (iArr2[0] << 24);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.KeyParameter) {
            this.encrypting = z;
            this.workingKey = ((com.android.internal.org.bouncycastle.crypto.params.KeyParameter) cipherParameters).getKey();
            this.k64Cnt = this.workingKey.length / 8;
            setKey(this.workingKey);
            return;
        }
        throw new java.lang.IllegalArgumentException("invalid parameter passed to Twofish init - " + cipherParameters.getClass().getName());
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public java.lang.String getAlgorithmName() {
        return "Twofish";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.workingKey == null) {
            throw new java.lang.IllegalStateException("Twofish not initialised");
        }
        if (i + 16 > bArr.length) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("input buffer too short");
        }
        if (i2 + 16 > bArr2.length) {
            throw new com.android.internal.org.bouncycastle.crypto.OutputLengthException("output buffer too short");
        }
        if (this.encrypting) {
            encryptBlock(bArr, i, bArr2, i2);
            return 16;
        }
        decryptBlock(bArr, i, bArr2, i2);
        return 16;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void reset() {
        if (this.workingKey != null) {
            setKey(this.workingKey);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    private void setKey(byte[] bArr) {
        int b0;
        int b1;
        int b2;
        int b3;
        int i;
        int i2;
        int i3;
        int i4;
        int[] iArr = new int[4];
        int[] iArr2 = new int[4];
        int[] iArr3 = new int[4];
        this.gSubKeys = new int[40];
        if (this.k64Cnt < 1) {
            throw new java.lang.IllegalArgumentException("Key size less than 64 bits");
        }
        if (this.k64Cnt > 4) {
            throw new java.lang.IllegalArgumentException("Key size larger than 256 bits");
        }
        for (int i5 = 0; i5 < this.k64Cnt; i5++) {
            int i6 = i5 * 8;
            iArr[i5] = BytesTo32Bits(bArr, i6);
            iArr2[i5] = BytesTo32Bits(bArr, i6 + 4);
            iArr3[(this.k64Cnt - 1) - i5] = RS_MDS_Encode(iArr[i5], iArr2[i5]);
        }
        for (int i7 = 0; i7 < 20; i7++) {
            int i8 = SK_STEP * i7;
            int F32 = F32(i8, iArr);
            int F322 = F32(i8 + 16843009, iArr2);
            int i9 = (F322 >>> 24) | (F322 << 8);
            int i10 = F32 + i9;
            int i11 = i7 * 2;
            this.gSubKeys[i11] = i10;
            int i12 = i10 + i9;
            this.gSubKeys[i11 + 1] = (i12 >>> 23) | (i12 << 9);
        }
        int i13 = iArr3[0];
        int i14 = iArr3[1];
        int i15 = iArr3[2];
        int i16 = iArr3[3];
        this.gSBox = new int[1024];
        for (int i17 = 0; i17 < 256; i17++) {
            switch (this.k64Cnt & 3) {
                case 0:
                    b0 = (P[1][i17] & 255) ^ b0(i16);
                    b1 = (P[0][i17] & 255) ^ b1(i16);
                    b2 = (P[0][i17] & 255) ^ b2(i16);
                    b3 = (P[1][i17] & 255) ^ b3(i16);
                    i = (P[1][b0] & 255) ^ b0(i15);
                    i2 = (P[1][b1] & 255) ^ b1(i15);
                    i3 = (P[0][b2] & 255) ^ b2(i15);
                    i4 = (P[0][b3] & 255) ^ b3(i15);
                    int i18 = i17 * 2;
                    this.gSBox[i18] = this.gMDS0[(P[0][(P[0][i] & 255) ^ b0(i14)] & 255) ^ b0(i13)];
                    this.gSBox[i18 + 1] = this.gMDS1[(P[0][(P[1][i2] & 255) ^ b1(i14)] & 255) ^ b1(i13)];
                    this.gSBox[i18 + 512] = this.gMDS2[(P[1][(P[0][i3] & 255) ^ b2(i14)] & 255) ^ b2(i13)];
                    this.gSBox[i18 + 513] = this.gMDS3[(P[1][(P[1][i4] & 255) ^ b3(i14)] & 255) ^ b3(i13)];
                    break;
                case 1:
                    int i19 = i17 * 2;
                    this.gSBox[i19] = this.gMDS0[(P[0][i17] & 255) ^ b0(i13)];
                    this.gSBox[i19 + 1] = this.gMDS1[(P[0][i17] & 255) ^ b1(i13)];
                    this.gSBox[i19 + 512] = this.gMDS2[(P[1][i17] & 255) ^ b2(i13)];
                    this.gSBox[i19 + 513] = this.gMDS3[(P[1][i17] & 255) ^ b3(i13)];
                    break;
                case 2:
                    i = i17;
                    i2 = i;
                    i3 = i2;
                    i4 = i3;
                    int i182 = i17 * 2;
                    this.gSBox[i182] = this.gMDS0[(P[0][(P[0][i] & 255) ^ b0(i14)] & 255) ^ b0(i13)];
                    this.gSBox[i182 + 1] = this.gMDS1[(P[0][(P[1][i2] & 255) ^ b1(i14)] & 255) ^ b1(i13)];
                    this.gSBox[i182 + 512] = this.gMDS2[(P[1][(P[0][i3] & 255) ^ b2(i14)] & 255) ^ b2(i13)];
                    this.gSBox[i182 + 513] = this.gMDS3[(P[1][(P[1][i4] & 255) ^ b3(i14)] & 255) ^ b3(i13)];
                    break;
                case 3:
                    b0 = i17;
                    b1 = b0;
                    b2 = b1;
                    b3 = b2;
                    i = (P[1][b0] & 255) ^ b0(i15);
                    i2 = (P[1][b1] & 255) ^ b1(i15);
                    i3 = (P[0][b2] & 255) ^ b2(i15);
                    i4 = (P[0][b3] & 255) ^ b3(i15);
                    int i1822 = i17 * 2;
                    this.gSBox[i1822] = this.gMDS0[(P[0][(P[0][i] & 255) ^ b0(i14)] & 255) ^ b0(i13)];
                    this.gSBox[i1822 + 1] = this.gMDS1[(P[0][(P[1][i2] & 255) ^ b1(i14)] & 255) ^ b1(i13)];
                    this.gSBox[i1822 + 512] = this.gMDS2[(P[1][(P[0][i3] & 255) ^ b2(i14)] & 255) ^ b2(i13)];
                    this.gSBox[i1822 + 513] = this.gMDS3[(P[1][(P[1][i4] & 255) ^ b3(i14)] & 255) ^ b3(i13)];
                    break;
            }
        }
    }

    private void encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3 = 0;
        int BytesTo32Bits = BytesTo32Bits(bArr, i) ^ this.gSubKeys[0];
        int BytesTo32Bits2 = BytesTo32Bits(bArr, i + 4) ^ this.gSubKeys[1];
        int BytesTo32Bits3 = BytesTo32Bits(bArr, i + 8) ^ this.gSubKeys[2];
        int BytesTo32Bits4 = BytesTo32Bits(bArr, i + 12) ^ this.gSubKeys[3];
        int i4 = 8;
        while (i3 < 16) {
            int Fe32_0 = Fe32_0(BytesTo32Bits);
            int Fe32_3 = Fe32_3(BytesTo32Bits2);
            int i5 = i4 + 1;
            int i6 = BytesTo32Bits3 ^ ((Fe32_0 + Fe32_3) + this.gSubKeys[i4]);
            BytesTo32Bits3 = (i6 >>> 1) | (i6 << 31);
            int i7 = Fe32_0 + (Fe32_3 * 2);
            int i8 = i5 + 1;
            BytesTo32Bits4 = ((BytesTo32Bits4 >>> 31) | (BytesTo32Bits4 << 1)) ^ (i7 + this.gSubKeys[i5]);
            int Fe32_02 = Fe32_0(BytesTo32Bits3);
            int Fe32_32 = Fe32_3(BytesTo32Bits4);
            int i9 = i8 + 1;
            int i10 = BytesTo32Bits ^ ((Fe32_02 + Fe32_32) + this.gSubKeys[i8]);
            BytesTo32Bits = (i10 << 31) | (i10 >>> 1);
            BytesTo32Bits2 = ((BytesTo32Bits2 >>> 31) | (BytesTo32Bits2 << 1)) ^ ((Fe32_02 + (Fe32_32 * 2)) + this.gSubKeys[i9]);
            i3 += 2;
            i4 = i9 + 1;
        }
        Bits32ToBytes(this.gSubKeys[4] ^ BytesTo32Bits3, bArr2, i2);
        Bits32ToBytes(BytesTo32Bits4 ^ this.gSubKeys[5], bArr2, i2 + 4);
        Bits32ToBytes(this.gSubKeys[6] ^ BytesTo32Bits, bArr2, i2 + 8);
        Bits32ToBytes(this.gSubKeys[7] ^ BytesTo32Bits2, bArr2, i2 + 12);
    }

    private void decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int BytesTo32Bits = BytesTo32Bits(bArr, i) ^ this.gSubKeys[4];
        int BytesTo32Bits2 = BytesTo32Bits(bArr, i + 4) ^ this.gSubKeys[5];
        int BytesTo32Bits3 = BytesTo32Bits(bArr, i + 8) ^ this.gSubKeys[6];
        int BytesTo32Bits4 = BytesTo32Bits(bArr, i + 12) ^ this.gSubKeys[7];
        int i3 = 39;
        int i4 = 0;
        while (i4 < 16) {
            int Fe32_0 = Fe32_0(BytesTo32Bits);
            int Fe32_3 = Fe32_3(BytesTo32Bits2);
            int i5 = i3 - 1;
            int i6 = BytesTo32Bits4 ^ (((Fe32_3 * 2) + Fe32_0) + this.gSubKeys[i3]);
            int i7 = Fe32_0 + Fe32_3;
            int i8 = i5 - 1;
            BytesTo32Bits3 = ((BytesTo32Bits3 << 1) | (BytesTo32Bits3 >>> 31)) ^ (i7 + this.gSubKeys[i5]);
            BytesTo32Bits4 = (i6 << 31) | (i6 >>> 1);
            int Fe32_02 = Fe32_0(BytesTo32Bits3);
            int Fe32_32 = Fe32_3(BytesTo32Bits4);
            int i9 = i8 - 1;
            int i10 = BytesTo32Bits2 ^ (((Fe32_32 * 2) + Fe32_02) + this.gSubKeys[i8]);
            BytesTo32Bits = ((BytesTo32Bits >>> 31) | (BytesTo32Bits << 1)) ^ ((Fe32_02 + Fe32_32) + this.gSubKeys[i9]);
            BytesTo32Bits2 = (i10 << 31) | (i10 >>> 1);
            i4 += 2;
            i3 = i9 - 1;
        }
        Bits32ToBytes(this.gSubKeys[0] ^ BytesTo32Bits3, bArr2, i2);
        Bits32ToBytes(BytesTo32Bits4 ^ this.gSubKeys[1], bArr2, i2 + 4);
        Bits32ToBytes(this.gSubKeys[2] ^ BytesTo32Bits, bArr2, i2 + 8);
        Bits32ToBytes(this.gSubKeys[3] ^ BytesTo32Bits2, bArr2, i2 + 12);
    }

    private int F32(int i, int[] iArr) {
        int b0 = b0(i);
        int b1 = b1(i);
        int b2 = b2(i);
        int b3 = b3(i);
        int i2 = iArr[0];
        int i3 = iArr[1];
        int i4 = iArr[2];
        int i5 = iArr[3];
        switch (3 & this.k64Cnt) {
            case 0:
                b0 = (P[1][b0] & 255) ^ b0(i5);
                b1 = (P[0][b1] & 255) ^ b1(i5);
                b2 = (P[0][b2] & 255) ^ b2(i5);
                b3 = (P[1][b3] & 255) ^ b3(i5);
                break;
            case 1:
                return ((this.gMDS0[(P[0][b0] & 255) ^ b0(i2)] ^ this.gMDS1[(P[0][b1] & 255) ^ b1(i2)]) ^ this.gMDS2[(P[1][b2] & 255) ^ b2(i2)]) ^ this.gMDS3[(P[1][b3] & 255) ^ b3(i2)];
            case 3:
                break;
            case 2:
                return ((this.gMDS0[(P[0][(P[0][b0] & 255) ^ b0(i3)] & 255) ^ b0(i2)] ^ this.gMDS1[(P[0][(P[1][b1] & 255) ^ b1(i3)] & 255) ^ b1(i2)]) ^ this.gMDS2[(P[1][(P[0][b2] & 255) ^ b2(i3)] & 255) ^ b2(i2)]) ^ this.gMDS3[(P[1][(P[1][b3] & 255) ^ b3(i3)] & 255) ^ b3(i2)];
            default:
                return 0;
        }
        b0 = b0(i4) ^ (P[1][b0] & 255);
        b1 = b1(i4) ^ (P[1][b1] & 255);
        b2 = b2(i4) ^ (P[0][b2] & 255);
        b3 = (P[0][b3] & 255) ^ b3(i4);
        return ((this.gMDS0[(P[0][(P[0][b0] & 255) ^ b0(i3)] & 255) ^ b0(i2)] ^ this.gMDS1[(P[0][(P[1][b1] & 255) ^ b1(i3)] & 255) ^ b1(i2)]) ^ this.gMDS2[(P[1][(P[0][b2] & 255) ^ b2(i3)] & 255) ^ b2(i2)]) ^ this.gMDS3[(P[1][(P[1][b3] & 255) ^ b3(i3)] & 255) ^ b3(i2)];
    }

    private int RS_MDS_Encode(int i, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            i2 = RS_rem(i2);
        }
        int i4 = i ^ i2;
        for (int i5 = 0; i5 < 4; i5++) {
            i4 = RS_rem(i4);
        }
        return i4;
    }

    private int RS_rem(int i) {
        int i2 = (i >>> 24) & 255;
        int i3 = ((i2 << 1) ^ ((i2 & 128) != 0 ? 333 : 0)) & 255;
        int i4 = ((i2 >>> 1) ^ ((i2 & 1) != 0 ? 166 : 0)) ^ i3;
        return ((((i << 8) ^ (i4 << 24)) ^ (i3 << 16)) ^ (i4 << 8)) ^ i2;
    }

    private int LFSR1(int i) {
        return ((i & 1) != 0 ? 180 : 0) ^ (i >> 1);
    }

    private int LFSR2(int i) {
        return ((i >> 2) ^ ((i & 2) != 0 ? 180 : 0)) ^ ((i & 1) != 0 ? 90 : 0);
    }

    private int Mx_X(int i) {
        return i ^ LFSR2(i);
    }

    private int Mx_Y(int i) {
        return LFSR2(i) ^ (LFSR1(i) ^ i);
    }

    private int b0(int i) {
        return i & 255;
    }

    private int b1(int i) {
        return (i >>> 8) & 255;
    }

    private int b2(int i) {
        return (i >>> 16) & 255;
    }

    private int b3(int i) {
        return (i >>> 24) & 255;
    }

    private int Fe32_0(int i) {
        return this.gSBox[(((i >>> 24) & 255) * 2) + 513] ^ ((this.gSBox[((i & 255) * 2) + 0] ^ this.gSBox[(((i >>> 8) & 255) * 2) + 1]) ^ this.gSBox[(((i >>> 16) & 255) * 2) + 512]);
    }

    private int Fe32_3(int i) {
        return this.gSBox[(((i >>> 16) & 255) * 2) + 513] ^ ((this.gSBox[(((i >>> 24) & 255) * 2) + 0] ^ this.gSBox[((i & 255) * 2) + 1]) ^ this.gSBox[(((i >>> 8) & 255) * 2) + 512]);
    }

    private int BytesTo32Bits(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    private void Bits32ToBytes(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        bArr[i2 + 1] = (byte) (i >> 8);
        bArr[i2 + 2] = (byte) (i >> 16);
        bArr[i2 + 3] = (byte) (i >> 24);
    }
}
