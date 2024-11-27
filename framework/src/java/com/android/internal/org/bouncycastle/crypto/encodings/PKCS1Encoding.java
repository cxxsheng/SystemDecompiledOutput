package com.android.internal.org.bouncycastle.crypto.encodings;

/* loaded from: classes4.dex */
public class PKCS1Encoding implements com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher {
    private static final int HEADER_LENGTH = 10;
    public static final java.lang.String NOT_STRICT_LENGTH_ENABLED_PROPERTY = "com.android.internal.org.bouncycastle.pkcs1.not_strict";
    public static final java.lang.String STRICT_LENGTH_ENABLED_PROPERTY = "com.android.internal.org.bouncycastle.pkcs1.strict";
    private byte[] blockBuffer;
    private com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher engine;
    private byte[] fallback;
    private boolean forEncryption;
    private boolean forPrivateKey;
    private int pLen;
    private java.security.SecureRandom random;
    private boolean useStrictLength;

    public PKCS1Encoding(com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher asymmetricBlockCipher) {
        this.pLen = -1;
        this.fallback = null;
        this.engine = asymmetricBlockCipher;
        this.useStrictLength = useStrict();
    }

    public PKCS1Encoding(com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher asymmetricBlockCipher, int i) {
        this.pLen = -1;
        this.fallback = null;
        this.engine = asymmetricBlockCipher;
        this.useStrictLength = useStrict();
        this.pLen = i;
    }

    public PKCS1Encoding(com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher asymmetricBlockCipher, byte[] bArr) {
        this.pLen = -1;
        this.fallback = null;
        this.engine = asymmetricBlockCipher;
        this.useStrictLength = useStrict();
        this.fallback = bArr;
        this.pLen = bArr.length;
    }

    private boolean useStrict() {
        if (com.android.internal.org.bouncycastle.util.Properties.isOverrideSetTo(NOT_STRICT_LENGTH_ENABLED_PROPERTY, true)) {
            return false;
        }
        return !com.android.internal.org.bouncycastle.util.Properties.isOverrideSetTo(STRICT_LENGTH_ENABLED_PROPERTY, false);
    }

    public com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher getUnderlyingCipher() {
        return this.engine;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter asymmetricKeyParameter;
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) {
            com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom parametersWithRandom = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) cipherParameters;
            this.random = parametersWithRandom.getRandom();
            asymmetricKeyParameter = (com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) parametersWithRandom.getParameters();
        } else {
            asymmetricKeyParameter = (com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) cipherParameters;
            if (!asymmetricKeyParameter.isPrivate() && z) {
                this.random = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom();
            }
        }
        this.engine.init(z, cipherParameters);
        this.forPrivateKey = asymmetricKeyParameter.isPrivate();
        this.forEncryption = z;
        this.blockBuffer = new byte[this.engine.getOutputBlockSize()];
        if (this.pLen > 0 && this.fallback == null && this.random == null) {
            throw new java.lang.IllegalArgumentException("encoder requires random");
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        int inputBlockSize = this.engine.getInputBlockSize();
        if (this.forEncryption) {
            return inputBlockSize - 10;
        }
        return inputBlockSize;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        int outputBlockSize = this.engine.getOutputBlockSize();
        if (this.forEncryption) {
            return outputBlockSize;
        }
        return outputBlockSize - 10;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i, int i2) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        if (this.forEncryption) {
            return encodeBlock(bArr, i, i2);
        }
        return decodeBlock(bArr, i, i2);
    }

    private byte[] encodeBlock(byte[] bArr, int i, int i2) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        if (i2 > getInputBlockSize()) {
            throw new java.lang.IllegalArgumentException("input data too large");
        }
        int inputBlockSize = this.engine.getInputBlockSize();
        byte[] bArr2 = new byte[inputBlockSize];
        if (this.forPrivateKey) {
            bArr2[0] = 1;
            for (int i3 = 1; i3 != (inputBlockSize - i2) - 1; i3++) {
                bArr2[i3] = -1;
            }
        } else {
            this.random.nextBytes(bArr2);
            bArr2[0] = 2;
            for (int i4 = 1; i4 != (inputBlockSize - i2) - 1; i4++) {
                while (bArr2[i4] == 0) {
                    bArr2[i4] = (byte) this.random.nextInt();
                }
            }
        }
        int i5 = inputBlockSize - i2;
        bArr2[i5 - 1] = 0;
        java.lang.System.arraycopy(bArr, i, bArr2, i5, i2);
        return this.engine.processBlock(bArr2, 0, inputBlockSize);
    }

    private static int checkPkcs1Encoding(byte[] bArr, int i) {
        int i2 = 0 | (bArr[0] ^ 2);
        int i3 = i + 1;
        int length = bArr.length - i3;
        for (int i4 = 1; i4 < length; i4++) {
            byte b = bArr[i4];
            int i5 = b | (b >> 1);
            int i6 = i5 | (i5 >> 2);
            i2 |= ((i6 | (i6 >> 4)) & 1) - 1;
        }
        int i7 = bArr[bArr.length - i3] | i2;
        int i8 = i7 | (i7 >> 1);
        int i9 = i8 | (i8 >> 2);
        return ~(((i9 | (i9 >> 4)) & 1) - 1);
    }

    private byte[] decodeBlockOrRandom(byte[] bArr, int i, int i2) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        byte[] bArr2;
        if (!this.forPrivateKey) {
            throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("sorry, this method is only for decryption, not for signing");
        }
        byte[] processBlock = this.engine.processBlock(bArr, i, i2);
        if (this.fallback == null) {
            bArr2 = new byte[this.pLen];
            this.random.nextBytes(bArr2);
        } else {
            bArr2 = this.fallback;
        }
        if (this.useStrictLength & (processBlock.length != this.engine.getOutputBlockSize())) {
            processBlock = this.blockBuffer;
        }
        int checkPkcs1Encoding = checkPkcs1Encoding(processBlock, this.pLen);
        byte[] bArr3 = new byte[this.pLen];
        for (int i3 = 0; i3 < this.pLen; i3++) {
            bArr3[i3] = (byte) ((processBlock[(processBlock.length - this.pLen) + i3] & (~checkPkcs1Encoding)) | (bArr2[i3] & checkPkcs1Encoding));
        }
        com.android.internal.org.bouncycastle.util.Arrays.fill(processBlock, (byte) 0);
        return bArr3;
    }

    private byte[] decodeBlock(byte[] bArr, int i, int i2) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        boolean z;
        if (this.pLen != -1) {
            return decodeBlockOrRandom(bArr, i, i2);
        }
        byte[] processBlock = this.engine.processBlock(bArr, i, i2);
        boolean z2 = this.useStrictLength & (processBlock.length != this.engine.getOutputBlockSize());
        if (processBlock.length < getOutputBlockSize()) {
            processBlock = this.blockBuffer;
        }
        byte b = processBlock[0];
        if (this.forPrivateKey) {
            z = b != 2;
        } else {
            z = b != 1;
        }
        int findStart = findStart(b, processBlock) + 1;
        if (z | (findStart < 10)) {
            com.android.internal.org.bouncycastle.util.Arrays.fill(processBlock, (byte) 0);
            throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("block incorrect");
        }
        if (z2) {
            com.android.internal.org.bouncycastle.util.Arrays.fill(processBlock, (byte) 0);
            throw new com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException("block incorrect size");
        }
        int length = processBlock.length - findStart;
        byte[] bArr2 = new byte[length];
        java.lang.System.arraycopy(processBlock, findStart, bArr2, 0, length);
        return bArr2;
    }

    private int findStart(byte b, byte[] bArr) throws com.android.internal.org.bouncycastle.crypto.InvalidCipherTextException {
        int i = -1;
        boolean z = false;
        for (int i2 = 1; i2 != bArr.length; i2++) {
            byte b2 = bArr[i2];
            if ((b2 == 0) & (i < 0)) {
                i = i2;
            }
            z |= (b2 != -1) & (b == 1) & (i < 0);
        }
        if (z) {
            return -1;
        }
        return i;
    }
}
