package com.android.internal.org.bouncycastle.crypto.generators;

/* loaded from: classes4.dex */
public class DSAParametersGenerator {
    private int L;
    private int N;
    private int certainty;
    private com.android.internal.org.bouncycastle.crypto.Digest digest;
    private int iterations;
    private java.security.SecureRandom random;
    private int usageIndex;
    private boolean use186_3;
    private static final java.math.BigInteger ZERO = java.math.BigInteger.valueOf(0);
    private static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);
    private static final java.math.BigInteger TWO = java.math.BigInteger.valueOf(2);

    public DSAParametersGenerator() {
        this(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1());
    }

    public DSAParametersGenerator(com.android.internal.org.bouncycastle.crypto.Digest digest) {
        this.digest = digest;
    }

    public void init(int i, int i2, java.security.SecureRandom secureRandom) {
        this.L = i;
        this.N = getDefaultN(i);
        this.certainty = i2;
        this.iterations = java.lang.Math.max(getMinimumIterations(this.L), (i2 + 1) / 2);
        this.random = secureRandom;
        this.use186_3 = false;
        this.usageIndex = -1;
    }

    public void init(com.android.internal.org.bouncycastle.crypto.params.DSAParameterGenerationParameters dSAParameterGenerationParameters) {
        int l = dSAParameterGenerationParameters.getL();
        int n = dSAParameterGenerationParameters.getN();
        if (l < 1024 || l > 3072 || l % 1024 != 0) {
            throw new java.lang.IllegalArgumentException("L values must be between 1024 and 3072 and a multiple of 1024");
        }
        if (l == 1024 && n != 160) {
            throw new java.lang.IllegalArgumentException("N must be 160 for L = 1024");
        }
        if (l == 2048 && n != 224 && n != 256) {
            throw new java.lang.IllegalArgumentException("N must be 224 or 256 for L = 2048");
        }
        if (l == 3072 && n != 256) {
            throw new java.lang.IllegalArgumentException("N must be 256 for L = 3072");
        }
        if (this.digest.getDigestSize() * 8 < n) {
            throw new java.lang.IllegalStateException("Digest output size too small for value of N");
        }
        this.L = l;
        this.N = n;
        this.certainty = dSAParameterGenerationParameters.getCertainty();
        this.iterations = java.lang.Math.max(getMinimumIterations(l), (this.certainty + 1) / 2);
        this.random = dSAParameterGenerationParameters.getRandom();
        this.use186_3 = true;
        this.usageIndex = dSAParameterGenerationParameters.getUsageIndex();
    }

    public com.android.internal.org.bouncycastle.crypto.params.DSAParameters generateParameters() {
        if (this.use186_3) {
            return generateParameters_FIPS186_3();
        }
        return generateParameters_FIPS186_2();
    }

    private com.android.internal.org.bouncycastle.crypto.params.DSAParameters generateParameters_FIPS186_2() {
        int i = 20;
        byte[] bArr = new byte[20];
        byte[] bArr2 = new byte[20];
        byte[] bArr3 = new byte[20];
        byte[] bArr4 = new byte[20];
        int i2 = (this.L - 1) / 160;
        int i3 = this.L / 8;
        byte[] bArr5 = new byte[i3];
        if (!this.digest.getAlgorithmName().equals("SHA-1")) {
            throw new java.lang.IllegalStateException("can only use SHA-1 for generating FIPS 186-2 parameters");
        }
        while (true) {
            this.random.nextBytes(bArr);
            hash(this.digest, bArr, bArr2, 0);
            java.lang.System.arraycopy(bArr, 0, bArr3, 0, i);
            inc(bArr3);
            hash(this.digest, bArr3, bArr3, 0);
            for (int i4 = 0; i4 != i; i4++) {
                bArr4[i4] = (byte) (bArr2[i4] ^ bArr3[i4]);
            }
            bArr4[0] = (byte) (bArr4[0] | Byte.MIN_VALUE);
            bArr4[19] = (byte) (bArr4[19] | 1);
            java.math.BigInteger bigInteger = new java.math.BigInteger(1, bArr4);
            if (isProbablePrime(bigInteger)) {
                byte[] clone = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
                inc(clone);
                for (int i5 = 0; i5 < 4096; i5++) {
                    for (int i6 = 1; i6 <= i2; i6++) {
                        inc(clone);
                        hash(this.digest, clone, bArr5, i3 - (i6 * 20));
                    }
                    int i7 = i3 - (i2 * 20);
                    inc(clone);
                    hash(this.digest, clone, bArr2, 0);
                    java.lang.System.arraycopy(bArr2, 20 - i7, bArr5, 0, i7);
                    bArr5[0] = (byte) (bArr5[0] | Byte.MIN_VALUE);
                    java.math.BigInteger bigInteger2 = new java.math.BigInteger(1, bArr5);
                    java.math.BigInteger subtract = bigInteger2.subtract(bigInteger2.mod(bigInteger.shiftLeft(1)).subtract(ONE));
                    if (subtract.bitLength() == this.L && isProbablePrime(subtract)) {
                        return new com.android.internal.org.bouncycastle.crypto.params.DSAParameters(subtract, bigInteger, calculateGenerator_FIPS186_2(subtract, bigInteger, this.random), new com.android.internal.org.bouncycastle.crypto.params.DSAValidationParameters(bArr, i5));
                    }
                }
                i = 20;
            }
        }
    }

    private static java.math.BigInteger calculateGenerator_FIPS186_2(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.security.SecureRandom secureRandom) {
        java.math.BigInteger modPow;
        java.math.BigInteger divide = bigInteger.subtract(ONE).divide(bigInteger2);
        java.math.BigInteger subtract = bigInteger.subtract(TWO);
        do {
            modPow = com.android.internal.org.bouncycastle.util.BigIntegers.createRandomInRange(TWO, subtract, secureRandom).modPow(divide, bigInteger);
        } while (modPow.bitLength() <= 1);
        return modPow;
    }

    private com.android.internal.org.bouncycastle.crypto.params.DSAParameters generateParameters_FIPS186_3() {
        java.math.BigInteger bit;
        int i;
        java.math.BigInteger subtract;
        java.math.BigInteger calculateGenerator_FIPS186_3_Verifiable;
        com.android.internal.org.bouncycastle.crypto.Digest digest = this.digest;
        int digestSize = digest.getDigestSize() * 8;
        byte[] bArr = new byte[this.N / 8];
        int i2 = 1;
        int i3 = (this.L - 1) / digestSize;
        int i4 = (this.L - 1) % digestSize;
        int i5 = this.L / 8;
        byte[] bArr2 = new byte[i5];
        int digestSize2 = digest.getDigestSize();
        byte[] bArr3 = new byte[digestSize2];
        loop0: while (true) {
            this.random.nextBytes(bArr);
            hash(digest, bArr, bArr3, 0);
            bit = new java.math.BigInteger(i2, bArr3).mod(ONE.shiftLeft(this.N - i2)).setBit(0).setBit(this.N - i2);
            if (isProbablePrime(bit)) {
                byte[] clone = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
                int i6 = this.L * 4;
                i = 0;
                while (i < i6) {
                    for (int i7 = i2; i7 <= i3; i7++) {
                        inc(clone);
                        hash(digest, clone, bArr2, i5 - (i7 * digestSize2));
                    }
                    int i8 = i5 - (i3 * digestSize2);
                    inc(clone);
                    hash(digest, clone, bArr3, 0);
                    java.lang.System.arraycopy(bArr3, digestSize2 - i8, bArr2, 0, i8);
                    bArr2[0] = (byte) (bArr2[0] | Byte.MIN_VALUE);
                    java.math.BigInteger bigInteger = new java.math.BigInteger(i2, bArr2);
                    subtract = bigInteger.subtract(bigInteger.mod(bit.shiftLeft(i2)).subtract(ONE));
                    if (subtract.bitLength() == this.L && isProbablePrime(subtract)) {
                        break loop0;
                    }
                    i++;
                    i2 = 1;
                }
                i2 = 1;
            }
        }
        if (this.usageIndex >= 0 && (calculateGenerator_FIPS186_3_Verifiable = calculateGenerator_FIPS186_3_Verifiable(digest, subtract, bit, bArr, this.usageIndex)) != null) {
            return new com.android.internal.org.bouncycastle.crypto.params.DSAParameters(subtract, bit, calculateGenerator_FIPS186_3_Verifiable, new com.android.internal.org.bouncycastle.crypto.params.DSAValidationParameters(bArr, i, this.usageIndex));
        }
        return new com.android.internal.org.bouncycastle.crypto.params.DSAParameters(subtract, bit, calculateGenerator_FIPS186_3_Unverifiable(subtract, bit, this.random), new com.android.internal.org.bouncycastle.crypto.params.DSAValidationParameters(bArr, i));
    }

    private boolean isProbablePrime(java.math.BigInteger bigInteger) {
        return bigInteger.isProbablePrime(this.certainty);
    }

    private static java.math.BigInteger calculateGenerator_FIPS186_3_Unverifiable(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.security.SecureRandom secureRandom) {
        return calculateGenerator_FIPS186_2(bigInteger, bigInteger2, secureRandom);
    }

    private static java.math.BigInteger calculateGenerator_FIPS186_3_Verifiable(com.android.internal.org.bouncycastle.crypto.Digest digest, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, byte[] bArr, int i) {
        java.math.BigInteger divide = bigInteger.subtract(ONE).divide(bigInteger2);
        byte[] decodeStrict = com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict("6767656E");
        int length = bArr.length + decodeStrict.length + 1 + 2;
        byte[] bArr2 = new byte[length];
        java.lang.System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        java.lang.System.arraycopy(decodeStrict, 0, bArr2, bArr.length, decodeStrict.length);
        bArr2[length - 3] = (byte) i;
        byte[] bArr3 = new byte[digest.getDigestSize()];
        for (int i2 = 1; i2 < 65536; i2++) {
            inc(bArr2);
            hash(digest, bArr2, bArr3, 0);
            java.math.BigInteger modPow = new java.math.BigInteger(1, bArr3).modPow(divide, bigInteger);
            if (modPow.compareTo(TWO) >= 0) {
                return modPow;
            }
        }
        return null;
    }

    private static void hash(com.android.internal.org.bouncycastle.crypto.Digest digest, byte[] bArr, byte[] bArr2, int i) {
        digest.update(bArr, 0, bArr.length);
        digest.doFinal(bArr2, i);
    }

    private static int getDefaultN(int i) {
        return i > 1024 ? 256 : 160;
    }

    private static int getMinimumIterations(int i) {
        if (i <= 1024) {
            return 40;
        }
        return (((i - 1) / 1024) * 8) + 48;
    }

    private static void inc(byte[] bArr) {
        for (int length = bArr.length - 1; length >= 0; length--) {
            byte b = (byte) ((bArr[length] + 1) & 255);
            bArr[length] = b;
            if (b != 0) {
                return;
            }
        }
    }
}
