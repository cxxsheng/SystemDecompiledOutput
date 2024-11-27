package com.android.internal.org.bouncycastle.crypto.engines;

/* loaded from: classes4.dex */
public class DESEngine implements com.android.internal.org.bouncycastle.crypto.BlockCipher {
    protected static final int BLOCK_SIZE = 8;
    private int[] workingKey = null;
    private static final short[] bytebit = {128, 64, 32, 16, 8, 4, 2, 1};
    private static final int[] bigbyte = {8388608, 4194304, 2097152, 1048576, 524288, 262144, 131072, 65536, 32768, 16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1};
    private static final byte[] pc1 = {56, 48, 40, com.android.net.module.util.NetworkStackConstants.TCPHDR_URG, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, 16, 8, 0, 57, 49, 41, 33, android.hardware.biometrics.face.AcquiredInfo.DARK_GLASSES_DETECTED, 17, 9, 1, 58, 50, 42, 34, android.hardware.biometrics.face.AcquiredInfo.MOUTH_COVERING_DETECTED, android.hardware.biometrics.face.AcquiredInfo.TILT_TOO_EXTREME, 10, 2, 59, 51, 43, 35, 62, 54, 46, 38, 30, android.hardware.biometrics.face.AcquiredInfo.SENSOR_DIRTY, 14, 6, 61, 53, 45, 37, 29, android.hardware.biometrics.face.AcquiredInfo.START, 13, 5, 60, 52, 44, 36, 28, 20, 12, 4, com.android.internal.telephony.GsmAlphabet.GSM_EXTENDED_ESCAPE, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, 11, 3};
    private static final byte[] totrot = {1, 2, 4, 6, 8, 10, 12, 14, 15, 17, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, android.hardware.biometrics.face.AcquiredInfo.START, android.hardware.biometrics.face.AcquiredInfo.VENDOR, android.hardware.biometrics.face.AcquiredInfo.DARK_GLASSES_DETECTED, com.android.internal.telephony.GsmAlphabet.GSM_EXTENDED_ESCAPE, 28};
    private static final byte[] pc2 = {13, 16, 10, android.hardware.biometrics.face.AcquiredInfo.VENDOR, 0, 4, 2, com.android.internal.telephony.GsmAlphabet.GSM_EXTENDED_ESCAPE, 14, 5, 20, 9, android.hardware.biometrics.face.AcquiredInfo.SENSOR_DIRTY, android.hardware.biometrics.face.AcquiredInfo.TILT_TOO_EXTREME, 11, 3, android.hardware.biometrics.face.AcquiredInfo.DARK_GLASSES_DETECTED, 7, 15, 6, android.hardware.biometrics.face.AcquiredInfo.MOUTH_COVERING_DETECTED, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, 12, 1, 40, 51, 30, 36, 46, 54, 29, 39, 50, 44, com.android.net.module.util.NetworkStackConstants.TCPHDR_URG, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31};
    private static final int[] SP1 = {16843776, 0, 65536, 16843780, 16842756, 66564, 4, 65536, 1024, 16843776, 16843780, 1024, 16778244, 16842756, 16777216, 4, 1028, 16778240, 16778240, 66560, 66560, 16842752, 16842752, 16778244, 65540, 16777220, 16777220, 65540, 0, 1028, 66564, 16777216, 65536, 16843780, 4, 16842752, 16843776, 16777216, 16777216, 1024, 16842756, 65536, 66560, 16777220, 1024, 4, 16778244, 66564, 16843780, 65540, 16842752, 16778244, 16777220, 1028, 66564, 16843776, 1028, 16778240, 16778240, 0, 65540, 66560, 0, 16842756};
    private static final int[] SP2 = {-2146402272, -2147450880, 32768, 1081376, 1048576, 32, -2146435040, -2147450848, -2147483616, -2146402272, -2146402304, Integer.MIN_VALUE, -2147450880, 1048576, 32, -2146435040, 1081344, 1048608, -2147450848, 0, Integer.MIN_VALUE, 32768, 1081376, android.media.AudioSystem.DEVICE_IN_BUS, 1048608, -2147483616, 0, 1081344, 32800, -2146402304, android.media.AudioSystem.DEVICE_IN_BUS, 32800, 0, 1081376, -2146435040, 1048576, -2147450848, android.media.AudioSystem.DEVICE_IN_BUS, -2146402304, 32768, android.media.AudioSystem.DEVICE_IN_BUS, -2147450880, 32, -2146402272, 1081376, 32, 32768, Integer.MIN_VALUE, 32800, -2146402304, 1048576, -2147483616, 1048608, -2147450848, -2147483616, 1048608, 1081344, 0, -2147450880, 32800, Integer.MIN_VALUE, -2146435040, -2146402272, 1081344};
    private static final int[] SP3 = {520, 134349312, 0, 134348808, 134218240, 0, 131592, 134218240, 131080, 134217736, 134217736, 131072, 134349320, 131080, 134348800, 520, 134217728, 8, 134349312, 512, 131584, 134348800, 134348808, 131592, 134218248, 131584, 131072, 134218248, 8, 134349320, 512, 134217728, 134349312, 134217728, 131080, 520, 131072, 134349312, 134218240, 0, 512, 131080, 134349320, 134218240, 134217736, 512, 0, 134348808, 134218248, 131072, 134217728, 134349320, 8, 131592, 131584, 134217736, 134348800, 134218248, 520, 134348800, 131592, 8, 134348808, 131584};
    private static final int[] SP4 = {8396801, 8321, 8321, 128, 8396928, 8388737, 8388609, 8193, 0, 8396800, 8396800, 8396929, 129, 0, 8388736, 8388609, 1, 8192, 8388608, 8396801, 128, 8388608, 8193, 8320, 8388737, 1, 8320, 8388736, 8192, 8396928, 8396929, 129, 8388736, 8388609, 8396800, 8396929, 129, 0, 0, 8396800, 8320, 8388736, 8388737, 1, 8396801, 8321, 8321, 128, 8396929, 129, 1, 8192, 8388609, 8193, 8396928, 8388737, 8193, 8320, 8388608, 8396801, 128, 8388608, 8192, 8396928};
    private static final int[] SP5 = {256, 34078976, 34078720, 1107296512, 524288, 256, 1073741824, 34078720, 1074266368, 524288, 33554688, 1074266368, 1107296512, 1107820544, 524544, 1073741824, 33554432, 1074266112, 1074266112, 0, 1073742080, 1107820800, 1107820800, 33554688, 1107820544, 1073742080, 0, 1107296256, 34078976, 33554432, 1107296256, 524544, 524288, 1107296512, 256, 33554432, 1073741824, 34078720, 1107296512, 1074266368, 33554688, 1073741824, 1107820544, 34078976, 1074266368, 256, 33554432, 1107820544, 1107820800, 524544, 1107296256, 1107820800, 34078720, 0, 1074266112, 1107296256, 524544, 33554688, 1073742080, 524288, 0, 1074266112, 34078976, 1073742080};
    private static final int[] SP6 = {536870928, 541065216, 16384, 541081616, 541065216, 16, 541081616, 4194304, 536887296, 4210704, 4194304, 536870928, 4194320, 536887296, 536870912, 16400, 0, 4194320, 536887312, 16384, 4210688, 536887312, 16, 541065232, 541065232, 0, 4210704, 541081600, 16400, 4210688, 541081600, 536870912, 536887296, 16, 541065232, 4210688, 541081616, 4194304, 16400, 536870928, 4194304, 536887296, 536870912, 16400, 536870928, 541081616, 4210688, 541065216, 4210704, 541081600, 0, 541065232, 16, 16384, 541065216, 4210704, 16384, 4194320, 536887312, 0, 541081600, 536870912, 4194320, 536887312};
    private static final int[] SP7 = {2097152, 69206018, 67110914, 0, 2048, 67110914, 2099202, 69208064, 69208066, 2097152, 0, android.media.audio.Enums.AUDIO_FORMAT_AAC_LC, 2, 67108864, 69206018, 2050, 67110912, 2099202, 2097154, 67110912, android.media.audio.Enums.AUDIO_FORMAT_AAC_LC, 69206016, 69208064, 2097154, 69206016, 2048, 2050, 69208066, 2099200, 2, 67108864, 2099200, 67108864, 2099200, 2097152, 67110914, 67110914, 69206018, 69206018, 2, 2097154, 67108864, 67110912, 2097152, 69208064, 2050, 2099202, 69208064, 2050, android.media.audio.Enums.AUDIO_FORMAT_AAC_LC, 69208066, 69206016, 2099200, 0, 2, 69208066, 0, 2099202, 69206016, 2048, android.media.audio.Enums.AUDIO_FORMAT_AAC_LC, 67110912, 2048, 2097154};
    private static final int[] SP8 = {268439616, 4096, 262144, 268701760, 268435456, 268439616, 64, 268435456, 262208, 268697600, 268701760, com.android.internal.util.Protocol.BASE_DATA_CONNECTION_AC, 268701696, 266304, 4096, 64, 268697600, 268435520, 268439552, 4160, com.android.internal.util.Protocol.BASE_DATA_CONNECTION_AC, 262208, 268697664, 268701696, 4160, 0, 0, 268697664, 268435520, 268439552, 266304, 262144, 266304, 262144, 268701696, 4096, 64, 268697664, 4096, 266304, 268439552, 64, 268435520, 268697600, 268697664, 268435456, 262144, 268439616, 0, 268701760, 262208, 268435520, 268697600, 268439552, 268439616, 0, 268701760, com.android.internal.util.Protocol.BASE_DATA_CONNECTION_AC, com.android.internal.util.Protocol.BASE_DATA_CONNECTION_AC, 4160, 4160, 262208, 268435456, 268701696};

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.KeyParameter) {
            com.android.internal.org.bouncycastle.crypto.params.KeyParameter keyParameter = (com.android.internal.org.bouncycastle.crypto.params.KeyParameter) cipherParameters;
            if (keyParameter.getKey().length > 8) {
                throw new java.lang.IllegalArgumentException("DES key too long - should be 8 bytes");
            }
            this.workingKey = generateWorkingKey(z, keyParameter.getKey());
            return;
        }
        throw new java.lang.IllegalArgumentException("invalid parameter passed to DES init - " + cipherParameters.getClass().getName());
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public java.lang.String getAlgorithmName() {
        return "DES";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 8;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.workingKey == null) {
            throw new java.lang.IllegalStateException("DES engine not initialised");
        }
        if (i + 8 > bArr.length) {
            throw new com.android.internal.org.bouncycastle.crypto.DataLengthException("input buffer too short");
        }
        if (i2 + 8 > bArr2.length) {
            throw new com.android.internal.org.bouncycastle.crypto.OutputLengthException("output buffer too short");
        }
        desFunc(this.workingKey, bArr, i, bArr2, i2);
        return 8;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }

    protected int[] generateWorkingKey(boolean z, byte[] bArr) {
        int i;
        int i2;
        int[] iArr = new int[32];
        boolean[] zArr = new boolean[56];
        boolean[] zArr2 = new boolean[56];
        int i3 = 0;
        while (true) {
            boolean z2 = true;
            if (i3 >= 56) {
                break;
            }
            byte b = pc1[i3];
            if ((bytebit[b & 7] & bArr[b >>> 3]) == 0) {
                z2 = false;
            }
            zArr[i3] = z2;
            i3++;
        }
        for (int i4 = 0; i4 < 16; i4++) {
            if (z) {
                i = i4 << 1;
            } else {
                i = (15 - i4) << 1;
            }
            int i5 = i + 1;
            iArr[i5] = 0;
            iArr[i] = 0;
            int i6 = 0;
            while (true) {
                if (i6 >= 28) {
                    break;
                }
                int i7 = totrot[i4] + i6;
                if (i7 < 28) {
                    zArr2[i6] = zArr[i7];
                } else {
                    zArr2[i6] = zArr[i7 - 28];
                }
                i6++;
            }
            for (i2 = 28; i2 < 56; i2++) {
                int i8 = totrot[i4] + i2;
                if (i8 < 56) {
                    zArr2[i2] = zArr[i8];
                } else {
                    zArr2[i2] = zArr[i8 - 28];
                }
            }
            for (int i9 = 0; i9 < 24; i9++) {
                if (zArr2[pc2[i9]]) {
                    iArr[i] = iArr[i] | bigbyte[i9];
                }
                if (zArr2[pc2[i9 + 24]]) {
                    iArr[i5] = iArr[i5] | bigbyte[i9];
                }
            }
        }
        for (int i10 = 0; i10 != 32; i10 += 2) {
            int i11 = iArr[i10];
            int i12 = i10 + 1;
            int i13 = iArr[i12];
            iArr[i10] = ((16515072 & i13) >>> 10) | ((i11 & 16515072) << 6) | ((i11 & 4032) << 10) | ((i13 & 4032) >>> 6);
            iArr[i12] = ((i11 & 63) << 16) | ((i11 & 258048) << 12) | ((258048 & i13) >>> 4) | (i13 & 63);
        }
        return iArr;
    }

    protected void desFunc(int[] iArr, byte[] bArr, int i, byte[] bArr2, int i2) {
        int bigEndianToInt = com.android.internal.org.bouncycastle.util.Pack.bigEndianToInt(bArr, i);
        int bigEndianToInt2 = com.android.internal.org.bouncycastle.util.Pack.bigEndianToInt(bArr, i + 4);
        int i3 = ((bigEndianToInt >>> 4) ^ bigEndianToInt2) & 252645135;
        int i4 = bigEndianToInt2 ^ i3;
        int i5 = bigEndianToInt ^ (i3 << 4);
        int i6 = ((i5 >>> 16) ^ i4) & 65535;
        int i7 = i4 ^ i6;
        int i8 = i5 ^ (i6 << 16);
        int i9 = ((i7 >>> 2) ^ i8) & 858993459;
        int i10 = i8 ^ i9;
        int i11 = i7 ^ (i9 << 2);
        int i12 = ((i11 >>> 8) ^ i10) & 16711935;
        int i13 = i10 ^ i12;
        int i14 = i11 ^ (i12 << 8);
        int i15 = (i14 >>> 31) | (i14 << 1);
        int i16 = (i13 ^ i15) & (-1431655766);
        int i17 = i13 ^ i16;
        int i18 = i15 ^ i16;
        int i19 = (i17 >>> 31) | (i17 << 1);
        for (int i20 = 0; i20 < 8; i20++) {
            int i21 = i20 * 4;
            int i22 = ((i18 << 28) | (i18 >>> 4)) ^ iArr[i21 + 0];
            int i23 = SP1[(i22 >>> 24) & 63] | SP7[i22 & 63] | SP5[(i22 >>> 8) & 63] | SP3[(i22 >>> 16) & 63];
            int i24 = iArr[i21 + 1] ^ i18;
            i19 ^= (((i23 | SP8[i24 & 63]) | SP6[(i24 >>> 8) & 63]) | SP4[(i24 >>> 16) & 63]) | SP2[(i24 >>> 24) & 63];
            int i25 = ((i19 << 28) | (i19 >>> 4)) ^ iArr[i21 + 2];
            int i26 = SP1[(i25 >>> 24) & 63] | SP7[i25 & 63] | SP5[(i25 >>> 8) & 63] | SP3[(i25 >>> 16) & 63];
            int i27 = iArr[i21 + 3] ^ i19;
            i18 ^= (((i26 | SP8[i27 & 63]) | SP6[(i27 >>> 8) & 63]) | SP4[(i27 >>> 16) & 63]) | SP2[(i27 >>> 24) & 63];
        }
        int i28 = (i18 >>> 1) | (i18 << 31);
        int i29 = (i19 ^ i28) & (-1431655766);
        int i30 = i19 ^ i29;
        int i31 = i28 ^ i29;
        int i32 = (i30 >>> 1) | (i30 << 31);
        int i33 = ((i32 >>> 8) ^ i31) & 16711935;
        int i34 = i31 ^ i33;
        int i35 = i32 ^ (i33 << 8);
        int i36 = ((i35 >>> 2) ^ i34) & 858993459;
        int i37 = i34 ^ i36;
        int i38 = i35 ^ (i36 << 2);
        int i39 = ((i37 >>> 16) ^ i38) & 65535;
        int i40 = i38 ^ i39;
        int i41 = i37 ^ (i39 << 16);
        int i42 = ((i41 >>> 4) ^ i40) & 252645135;
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(i41 ^ (i42 << 4), bArr2, i2);
        com.android.internal.org.bouncycastle.util.Pack.intToBigEndian(i40 ^ i42, bArr2, i2 + 4);
    }
}
