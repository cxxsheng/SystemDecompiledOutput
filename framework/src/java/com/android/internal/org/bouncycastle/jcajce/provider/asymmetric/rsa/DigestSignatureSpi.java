package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa;

/* loaded from: classes4.dex */
public class DigestSignatureSpi extends java.security.SignatureSpi {
    private com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algId;
    private com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher cipher;
    private com.android.internal.org.bouncycastle.crypto.Digest digest;

    protected DigestSignatureSpi(com.android.internal.org.bouncycastle.crypto.Digest digest, com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher asymmetricBlockCipher) {
        this.digest = digest;
        this.cipher = asymmetricBlockCipher;
        this.algId = null;
    }

    protected DigestSignatureSpi(com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.crypto.Digest digest, com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher asymmetricBlockCipher) {
        this.digest = digest;
        this.cipher = asymmetricBlockCipher;
        this.algId = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
    }

    @Override // java.security.SignatureSpi
    protected void engineInitVerify(java.security.PublicKey publicKey) throws java.security.InvalidKeyException {
        if (!(publicKey instanceof java.security.interfaces.RSAPublicKey)) {
            throw new java.security.InvalidKeyException("Supplied key (" + getType(publicKey) + ") is not a RSAPublicKey instance");
        }
        com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters generatePublicKeyParameter = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil.generatePublicKeyParameter((java.security.interfaces.RSAPublicKey) publicKey);
        this.digest.reset();
        this.cipher.init(false, generatePublicKeyParameter);
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(java.security.PrivateKey privateKey) throws java.security.InvalidKeyException {
        if (!(privateKey instanceof java.security.interfaces.RSAPrivateKey)) {
            throw new java.security.InvalidKeyException("Supplied key (" + getType(privateKey) + ") is not a RSAPrivateKey instance");
        }
        com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters generatePrivateKeyParameter = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil.generatePrivateKeyParameter((java.security.interfaces.RSAPrivateKey) privateKey);
        this.digest.reset();
        this.cipher.init(true, generatePrivateKeyParameter);
    }

    private java.lang.String getType(java.lang.Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.getClass().getName();
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
            byte[] derEncode = derEncode(bArr);
            return this.cipher.processBlock(derEncode, 0, derEncode.length);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new java.security.SignatureException("key too small for signature type");
        } catch (java.lang.Exception e2) {
            throw new java.security.SignatureException(e2.toString());
        }
    }

    @Override // java.security.SignatureSpi
    protected boolean engineVerify(byte[] bArr) throws java.security.SignatureException {
        byte[] bArr2 = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr2, 0);
        try {
            byte[] processBlock = this.cipher.processBlock(bArr, 0, bArr.length);
            byte[] derEncode = derEncode(bArr2);
            if (processBlock.length == derEncode.length) {
                return com.android.internal.org.bouncycastle.util.Arrays.constantTimeAreEqual(processBlock, derEncode);
            }
            if (processBlock.length == derEncode.length - 2) {
                derEncode[1] = (byte) (derEncode[1] - 2);
                derEncode[3] = (byte) (derEncode[3] - 2);
                int i = derEncode[3] + 4;
                int i2 = i + 2;
                int i3 = 0;
                for (int i4 = 0; i4 < derEncode.length - i2; i4++) {
                    i3 |= processBlock[i + i4] ^ derEncode[i2 + i4];
                }
                for (int i5 = 0; i5 < i; i5++) {
                    i3 |= processBlock[i5] ^ derEncode[i5];
                }
                return i3 == 0;
            }
            com.android.internal.org.bouncycastle.util.Arrays.constantTimeAreEqual(derEncode, derEncode);
            return false;
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) {
        throw new java.lang.UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(java.lang.String str, java.lang.Object obj) {
        throw new java.lang.UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override // java.security.SignatureSpi
    protected java.lang.Object engineGetParameter(java.lang.String str) {
        return null;
    }

    @Override // java.security.SignatureSpi
    protected java.security.AlgorithmParameters engineGetParameters() {
        return null;
    }

    private byte[] derEncode(byte[] bArr) throws java.io.IOException {
        if (this.algId == null) {
            return bArr;
        }
        return new com.android.internal.org.bouncycastle.asn1.x509.DigestInfo(this.algId, bArr).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
    }

    public static class SHA1 extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi {
        public SHA1() {
            super(com.android.internal.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers.idSHA1, com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1(), new com.android.internal.org.bouncycastle.crypto.encodings.PKCS1Encoding(new com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine()));
        }
    }

    public static class SHA224 extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi {
        public SHA224() {
            super(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224, com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA224(), new com.android.internal.org.bouncycastle.crypto.encodings.PKCS1Encoding(new com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine()));
        }
    }

    public static class SHA256 extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi {
        public SHA256() {
            super(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256, com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA256(), new com.android.internal.org.bouncycastle.crypto.encodings.PKCS1Encoding(new com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine()));
        }
    }

    public static class SHA384 extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi {
        public SHA384() {
            super(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384, com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA384(), new com.android.internal.org.bouncycastle.crypto.encodings.PKCS1Encoding(new com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine()));
        }
    }

    public static class SHA512 extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi {
        public SHA512() {
            super(com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512, com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA512(), new com.android.internal.org.bouncycastle.crypto.encodings.PKCS1Encoding(new com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine()));
        }
    }

    public static class MD5 extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi {
        public MD5() {
            super(com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5, com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getMD5(), new com.android.internal.org.bouncycastle.crypto.encodings.PKCS1Encoding(new com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine()));
        }
    }
}
