package com.android.internal.org.bouncycastle.crypto.engines;

/* loaded from: classes4.dex */
public class RSABlindedEngine implements com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher {
    private static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);
    private com.android.internal.org.bouncycastle.crypto.engines.RSACoreEngine core = new com.android.internal.org.bouncycastle.crypto.engines.RSACoreEngine();
    private com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters key;
    private java.security.SecureRandom random;

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        this.core.init(z, cipherParameters);
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) {
            com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom parametersWithRandom = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) cipherParameters;
            this.key = (com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters) parametersWithRandom.getParameters();
            if (this.key instanceof com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters) {
                this.random = parametersWithRandom.getRandom();
                return;
            } else {
                this.random = null;
                return;
            }
        }
        this.key = (com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters) cipherParameters;
        if (this.key instanceof com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters) {
            this.random = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom();
        } else {
            this.random = null;
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        return this.core.getInputBlockSize();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        return this.core.getOutputBlockSize();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i, int i2) {
        java.math.BigInteger processBlock;
        if (this.key == null) {
            throw new java.lang.IllegalStateException("RSA engine not initialised");
        }
        java.math.BigInteger convertInput = this.core.convertInput(bArr, i, i2);
        if (this.key instanceof com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters) {
            com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters rSAPrivateCrtKeyParameters = (com.android.internal.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters) this.key;
            java.math.BigInteger publicExponent = rSAPrivateCrtKeyParameters.getPublicExponent();
            if (publicExponent != null) {
                java.math.BigInteger modulus = rSAPrivateCrtKeyParameters.getModulus();
                java.math.BigInteger createRandomInRange = com.android.internal.org.bouncycastle.util.BigIntegers.createRandomInRange(ONE, modulus.subtract(ONE), this.random);
                processBlock = this.core.processBlock(createRandomInRange.modPow(publicExponent, modulus).multiply(convertInput).mod(modulus)).multiply(com.android.internal.org.bouncycastle.util.BigIntegers.modOddInverse(modulus, createRandomInRange)).mod(modulus);
                if (!convertInput.equals(processBlock.modPow(publicExponent, modulus))) {
                    throw new java.lang.IllegalStateException("RSA engine faulty decryption/signing detected");
                }
            } else {
                processBlock = this.core.processBlock(convertInput);
            }
        } else {
            processBlock = this.core.processBlock(convertInput);
        }
        return this.core.convertOutput(processBlock);
    }
}
