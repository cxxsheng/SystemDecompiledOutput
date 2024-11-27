package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa;

/* loaded from: classes4.dex */
public class DSASigner extends java.security.SignatureSpi implements com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers, com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers {
    private com.android.internal.org.bouncycastle.crypto.Digest digest;
    private com.android.internal.org.bouncycastle.crypto.signers.DSAEncoding encoding = com.android.internal.org.bouncycastle.crypto.signers.StandardDSAEncoding.INSTANCE;
    private java.security.SecureRandom random;
    private com.android.internal.org.bouncycastle.crypto.DSAExt signer;

    protected DSASigner(com.android.internal.org.bouncycastle.crypto.Digest digest, com.android.internal.org.bouncycastle.crypto.DSAExt dSAExt) {
        this.digest = digest;
        this.signer = dSAExt;
    }

    @Override // java.security.SignatureSpi
    protected void engineInitVerify(java.security.PublicKey publicKey) throws java.security.InvalidKeyException {
        com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter generatePublicKeyParameter = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil.generatePublicKeyParameter(publicKey);
        this.digest.reset();
        this.signer.init(false, generatePublicKeyParameter);
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(java.security.PrivateKey privateKey, java.security.SecureRandom secureRandom) throws java.security.InvalidKeyException {
        this.random = secureRandom;
        engineInitSign(privateKey);
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(java.security.PrivateKey privateKey) throws java.security.InvalidKeyException {
        com.android.internal.org.bouncycastle.crypto.CipherParameters generatePrivateKeyParameter = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil.generatePrivateKeyParameter(privateKey);
        checkKey(((com.android.internal.org.bouncycastle.crypto.params.DSAKeyParameters) generatePrivateKeyParameter).getParameters());
        if (this.random != null) {
            generatePrivateKeyParameter = new com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom(generatePrivateKeyParameter, this.random);
        }
        this.digest.reset();
        this.signer.init(true, generatePrivateKeyParameter);
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte b) throws java.security.SignatureException {
        this.digest.update(b);
    }

    @Override // java.security.SignatureSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) throws java.security.SignatureException {
        this.digest.update(bArr, i, i2);
    }

    @Override // java.security.SignatureSpi
    protected byte[] engineSign() throws java.security.SignatureException {
        byte[] bArr = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr, 0);
        try {
            java.math.BigInteger[] generateSignature = this.signer.generateSignature(bArr);
            return this.encoding.encode(this.signer.getOrder(), generateSignature[0], generateSignature[1]);
        } catch (java.lang.Exception e) {
            throw new java.security.SignatureException(e.toString());
        }
    }

    @Override // java.security.SignatureSpi
    protected boolean engineVerify(byte[] bArr) throws java.security.SignatureException {
        byte[] bArr2 = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr2, 0);
        try {
            java.math.BigInteger[] decode = this.encoding.decode(this.signer.getOrder(), bArr);
            return this.signer.verifySignature(bArr2, decode[0], decode[1]);
        } catch (java.lang.Exception e) {
            throw new java.security.SignatureException("error decoding signature bytes.");
        }
    }

    @Override // java.security.SignatureSpi
    protected java.security.AlgorithmParameters engineGetParameters() {
        return null;
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) {
        throw new java.lang.UnsupportedOperationException("engineSetParameter unsupported");
    }

    protected void checkKey(com.android.internal.org.bouncycastle.crypto.params.DSAParameters dSAParameters) throws java.security.InvalidKeyException {
        int bitLength = dSAParameters.getP().bitLength();
        int bitLength2 = dSAParameters.getQ().bitLength();
        int digestSize = this.digest.getDigestSize();
        if (bitLength < 1024 || bitLength > 3072 || bitLength % 1024 != 0) {
            throw new java.security.InvalidKeyException("valueL values must be between 1024 and 3072 and a multiple of 1024");
        }
        if (bitLength == 1024 && bitLength2 != 160) {
            throw new java.security.InvalidKeyException("valueN must be 160 for valueL = 1024");
        }
        if (bitLength == 2048 && bitLength2 != 224 && bitLength2 != 256) {
            throw new java.security.InvalidKeyException("valueN must be 224 or 256 for valueL = 2048");
        }
        if (bitLength == 3072 && bitLength2 != 256) {
            throw new java.security.InvalidKeyException("valueN must be 256 for valueL = 3072");
        }
        if (!(this.digest instanceof com.android.internal.org.bouncycastle.crypto.digests.NullDigest) && bitLength2 > digestSize * 8) {
            throw new java.security.InvalidKeyException("Key is too strong for this signature algorithm");
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(java.lang.String str, java.lang.Object obj) {
        throw new java.lang.UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override // java.security.SignatureSpi
    protected java.lang.Object engineGetParameter(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("engineGetParameter unsupported");
    }

    public static class stdDSA extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner {
        public stdDSA() {
            super(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1(), new com.android.internal.org.bouncycastle.crypto.signers.DSASigner());
        }
    }

    public static class dsa224 extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner {
        public dsa224() {
            super(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA224(), new com.android.internal.org.bouncycastle.crypto.signers.DSASigner());
        }
    }

    public static class dsa256 extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner {
        public dsa256() {
            super(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA256(), new com.android.internal.org.bouncycastle.crypto.signers.DSASigner());
        }
    }

    public static class noneDSA extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner {
        public noneDSA() {
            super(new com.android.internal.org.bouncycastle.crypto.digests.NullDigest(), new com.android.internal.org.bouncycastle.crypto.signers.DSASigner());
        }
    }
}
