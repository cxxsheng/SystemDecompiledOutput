package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh;

/* loaded from: classes4.dex */
public class KeyAgreementSpi extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi {
    private static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);
    private static final java.math.BigInteger TWO = java.math.BigInteger.valueOf(2);
    private java.math.BigInteger g;
    private final com.android.internal.org.bouncycastle.crypto.BasicAgreement mqvAgreement;
    private java.math.BigInteger p;
    private byte[] result;
    private java.math.BigInteger x;

    public KeyAgreementSpi() {
        this("Diffie-Hellman", null);
    }

    public KeyAgreementSpi(java.lang.String str, com.android.internal.org.bouncycastle.crypto.DerivationFunction derivationFunction) {
        super(str, derivationFunction);
        this.mqvAgreement = null;
    }

    public KeyAgreementSpi(java.lang.String str, com.android.internal.org.bouncycastle.crypto.BasicAgreement basicAgreement, com.android.internal.org.bouncycastle.crypto.DerivationFunction derivationFunction) {
        super(str, derivationFunction);
        this.mqvAgreement = basicAgreement;
    }

    protected byte[] bigIntToBytes(java.math.BigInteger bigInteger) {
        int bitLength = (this.p.bitLength() + 7) / 8;
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray.length == bitLength) {
            return byteArray;
        }
        if (byteArray[0] == 0 && byteArray.length == bitLength + 1) {
            int length = byteArray.length - 1;
            byte[] bArr = new byte[length];
            java.lang.System.arraycopy(byteArray, 1, bArr, 0, length);
            return bArr;
        }
        byte[] bArr2 = new byte[bitLength];
        java.lang.System.arraycopy(byteArray, 0, bArr2, bitLength - byteArray.length, byteArray.length);
        return bArr2;
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected java.security.Key engineDoPhase(java.security.Key key, boolean z) throws java.security.InvalidKeyException, java.lang.IllegalStateException {
        if (this.x == null) {
            throw new java.lang.IllegalStateException("Diffie-Hellman not initialised.");
        }
        if (!(key instanceof javax.crypto.interfaces.DHPublicKey)) {
            throw new java.security.InvalidKeyException("DHKeyAgreement doPhase requires DHPublicKey");
        }
        javax.crypto.interfaces.DHPublicKey dHPublicKey = (javax.crypto.interfaces.DHPublicKey) key;
        if (!dHPublicKey.getParams().getG().equals(this.g) || !dHPublicKey.getParams().getP().equals(this.p)) {
            throw new java.security.InvalidKeyException("DHPublicKey not for this KeyAgreement!");
        }
        java.math.BigInteger y = dHPublicKey.getY();
        if (y == null || y.compareTo(TWO) < 0 || y.compareTo(this.p.subtract(ONE)) >= 0) {
            throw new java.security.InvalidKeyException("Invalid DH PublicKey");
        }
        java.math.BigInteger modPow = y.modPow(this.x, this.p);
        if (modPow.compareTo(ONE) == 0) {
            throw new java.security.InvalidKeyException("Shared key can't be 1");
        }
        this.result = bigIntToBytes(modPow);
        if (z) {
            return null;
        }
        return new com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey(modPow, dHPublicKey.getParams());
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi, javax.crypto.KeyAgreementSpi
    protected byte[] engineGenerateSecret() throws java.lang.IllegalStateException {
        if (this.x == null) {
            throw new java.lang.IllegalStateException("Diffie-Hellman not initialised.");
        }
        return super.engineGenerateSecret();
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi, javax.crypto.KeyAgreementSpi
    protected int engineGenerateSecret(byte[] bArr, int i) throws java.lang.IllegalStateException, javax.crypto.ShortBufferException {
        if (this.x == null) {
            throw new java.lang.IllegalStateException("Diffie-Hellman not initialised.");
        }
        return super.engineGenerateSecret(bArr, i);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi, javax.crypto.KeyAgreementSpi
    protected javax.crypto.SecretKey engineGenerateSecret(java.lang.String str) throws java.security.NoSuchAlgorithmException {
        if (this.x == null) {
            throw new java.lang.IllegalStateException("Diffie-Hellman not initialised.");
        }
        if (str.equals("TlsPremasterSecret")) {
            return new javax.crypto.spec.SecretKeySpec(trimZeroes(this.result), str);
        }
        return super.engineGenerateSecret(str);
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected void engineInit(java.security.Key key, java.security.spec.AlgorithmParameterSpec algorithmParameterSpec, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException, java.security.InvalidAlgorithmParameterException {
        if (!(key instanceof javax.crypto.interfaces.DHPrivateKey)) {
            throw new java.security.InvalidKeyException("DHKeyAgreement requires DHPrivateKey for initialisation");
        }
        javax.crypto.interfaces.DHPrivateKey dHPrivateKey = (javax.crypto.interfaces.DHPrivateKey) key;
        if (algorithmParameterSpec != null) {
            if (algorithmParameterSpec instanceof javax.crypto.spec.DHParameterSpec) {
                javax.crypto.spec.DHParameterSpec dHParameterSpec = (javax.crypto.spec.DHParameterSpec) algorithmParameterSpec;
                this.p = dHParameterSpec.getP();
                this.g = dHParameterSpec.getG();
                this.ukmParameters = null;
            } else if (algorithmParameterSpec instanceof com.android.internal.org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec) {
                if (this.kdf == null) {
                    throw new java.security.InvalidAlgorithmParameterException("no KDF specified for UserKeyingMaterialSpec");
                }
                this.p = dHPrivateKey.getParams().getP();
                this.g = dHPrivateKey.getParams().getG();
                this.ukmParameters = ((com.android.internal.org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec) algorithmParameterSpec).getUserKeyingMaterial();
            } else {
                throw new java.security.InvalidAlgorithmParameterException("DHKeyAgreement only accepts DHParameterSpec");
            }
        } else {
            this.p = dHPrivateKey.getParams().getP();
            this.g = dHPrivateKey.getParams().getG();
        }
        this.x = dHPrivateKey.getX();
        this.result = bigIntToBytes(this.x);
    }

    @Override // javax.crypto.KeyAgreementSpi
    protected void engineInit(java.security.Key key, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException {
        if (!(key instanceof javax.crypto.interfaces.DHPrivateKey)) {
            throw new java.security.InvalidKeyException("DHKeyAgreement requires DHPrivateKey");
        }
        javax.crypto.interfaces.DHPrivateKey dHPrivateKey = (javax.crypto.interfaces.DHPrivateKey) key;
        this.p = dHPrivateKey.getParams().getP();
        this.g = dHPrivateKey.getParams().getG();
        this.x = dHPrivateKey.getX();
        this.result = bigIntToBytes(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi
    protected byte[] calcSecret() {
        return this.result;
    }

    private com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters generatePrivateKeyParameter(java.security.PrivateKey privateKey) throws java.security.InvalidKeyException {
        if (privateKey instanceof javax.crypto.interfaces.DHPrivateKey) {
            if (privateKey instanceof com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPrivateKey) {
                return ((com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPrivateKey) privateKey).engineGetKeyParameters();
            }
            javax.crypto.interfaces.DHPrivateKey dHPrivateKey = (javax.crypto.interfaces.DHPrivateKey) privateKey;
            javax.crypto.spec.DHParameterSpec params = dHPrivateKey.getParams();
            return new com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters(dHPrivateKey.getX(), new com.android.internal.org.bouncycastle.crypto.params.DHParameters(params.getP(), params.getG(), null, params.getL()));
        }
        throw new java.security.InvalidKeyException("private key not a DHPrivateKey");
    }

    private com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters generatePublicKeyParameter(java.security.PublicKey publicKey) throws java.security.InvalidKeyException {
        if (publicKey instanceof javax.crypto.interfaces.DHPublicKey) {
            if (publicKey instanceof com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey) {
                return ((com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey) publicKey).engineGetKeyParameters();
            }
            javax.crypto.interfaces.DHPublicKey dHPublicKey = (javax.crypto.interfaces.DHPublicKey) publicKey;
            javax.crypto.spec.DHParameterSpec params = dHPublicKey.getParams();
            if (params instanceof com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) {
                return new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(dHPublicKey.getY(), ((com.android.internal.org.bouncycastle.jcajce.spec.DHDomainParameterSpec) params).getDomainParameters());
            }
            return new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(dHPublicKey.getY(), new com.android.internal.org.bouncycastle.crypto.params.DHParameters(params.getP(), params.getG(), null, params.getL()));
        }
        throw new java.security.InvalidKeyException("public key not a DHPublicKey");
    }
}
