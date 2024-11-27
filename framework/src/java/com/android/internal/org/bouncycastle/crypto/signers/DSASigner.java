package com.android.internal.org.bouncycastle.crypto.signers;

/* loaded from: classes4.dex */
public class DSASigner implements com.android.internal.org.bouncycastle.crypto.DSAExt {
    private final com.android.internal.org.bouncycastle.crypto.signers.DSAKCalculator kCalculator;
    private com.android.internal.org.bouncycastle.crypto.params.DSAKeyParameters key;
    private java.security.SecureRandom random;

    public DSASigner() {
        this.kCalculator = new com.android.internal.org.bouncycastle.crypto.signers.RandomDSAKCalculator();
    }

    public DSASigner(com.android.internal.org.bouncycastle.crypto.signers.DSAKCalculator dSAKCalculator) {
        this.kCalculator = dSAKCalculator;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.DSA
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        java.security.SecureRandom secureRandom;
        if (z) {
            if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) {
                com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom parametersWithRandom = (com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) cipherParameters;
                this.key = (com.android.internal.org.bouncycastle.crypto.params.DSAPrivateKeyParameters) parametersWithRandom.getParameters();
                secureRandom = parametersWithRandom.getRandom();
                this.random = initSecureRandom((z || this.kCalculator.isDeterministic()) ? false : true, secureRandom);
            }
            this.key = (com.android.internal.org.bouncycastle.crypto.params.DSAPrivateKeyParameters) cipherParameters;
        } else {
            this.key = (com.android.internal.org.bouncycastle.crypto.params.DSAPublicKeyParameters) cipherParameters;
        }
        secureRandom = null;
        this.random = initSecureRandom((z || this.kCalculator.isDeterministic()) ? false : true, secureRandom);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.DSAExt
    public java.math.BigInteger getOrder() {
        return this.key.getParameters().getQ();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.DSA
    public java.math.BigInteger[] generateSignature(byte[] bArr) {
        com.android.internal.org.bouncycastle.crypto.params.DSAParameters parameters = this.key.getParameters();
        java.math.BigInteger q = parameters.getQ();
        java.math.BigInteger calculateE = calculateE(q, bArr);
        java.math.BigInteger x = ((com.android.internal.org.bouncycastle.crypto.params.DSAPrivateKeyParameters) this.key).getX();
        if (this.kCalculator.isDeterministic()) {
            this.kCalculator.init(q, x, bArr);
        } else {
            this.kCalculator.init(q, this.random);
        }
        java.math.BigInteger nextK = this.kCalculator.nextK();
        java.math.BigInteger mod = parameters.getG().modPow(nextK.add(getRandomizer(q, this.random)), parameters.getP()).mod(q);
        return new java.math.BigInteger[]{mod, com.android.internal.org.bouncycastle.util.BigIntegers.modOddInverse(q, nextK).multiply(calculateE.add(x.multiply(mod))).mod(q)};
    }

    @Override // com.android.internal.org.bouncycastle.crypto.DSA
    public boolean verifySignature(byte[] bArr, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        com.android.internal.org.bouncycastle.crypto.params.DSAParameters parameters = this.key.getParameters();
        java.math.BigInteger q = parameters.getQ();
        java.math.BigInteger calculateE = calculateE(q, bArr);
        java.math.BigInteger valueOf = java.math.BigInteger.valueOf(0L);
        if (valueOf.compareTo(bigInteger) >= 0 || q.compareTo(bigInteger) <= 0 || valueOf.compareTo(bigInteger2) >= 0 || q.compareTo(bigInteger2) <= 0) {
            return false;
        }
        java.math.BigInteger modOddInverseVar = com.android.internal.org.bouncycastle.util.BigIntegers.modOddInverseVar(q, bigInteger2);
        java.math.BigInteger mod = calculateE.multiply(modOddInverseVar).mod(q);
        java.math.BigInteger mod2 = bigInteger.multiply(modOddInverseVar).mod(q);
        java.math.BigInteger p = parameters.getP();
        return parameters.getG().modPow(mod, p).multiply(((com.android.internal.org.bouncycastle.crypto.params.DSAPublicKeyParameters) this.key).getY().modPow(mod2, p)).mod(p).mod(q).equals(bigInteger);
    }

    private java.math.BigInteger calculateE(java.math.BigInteger bigInteger, byte[] bArr) {
        if (bigInteger.bitLength() >= bArr.length * 8) {
            return new java.math.BigInteger(1, bArr);
        }
        int bitLength = bigInteger.bitLength() / 8;
        byte[] bArr2 = new byte[bitLength];
        java.lang.System.arraycopy(bArr, 0, bArr2, 0, bitLength);
        return new java.math.BigInteger(1, bArr2);
    }

    protected java.security.SecureRandom initSecureRandom(boolean z, java.security.SecureRandom secureRandom) {
        if (z) {
            return com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom(secureRandom);
        }
        return null;
    }

    private java.math.BigInteger getRandomizer(java.math.BigInteger bigInteger, java.security.SecureRandom secureRandom) {
        return com.android.internal.org.bouncycastle.util.BigIntegers.createRandomBigInteger(7, com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom(secureRandom)).add(java.math.BigInteger.valueOf(128L)).multiply(bigInteger);
    }
}
