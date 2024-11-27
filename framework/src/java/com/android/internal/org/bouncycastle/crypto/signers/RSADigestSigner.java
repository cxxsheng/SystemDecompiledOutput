package com.android.internal.org.bouncycastle.crypto.signers;

/* loaded from: classes4.dex */
public class RSADigestSigner implements com.android.internal.org.bouncycastle.crypto.Signer {
    private static final java.util.Hashtable oidMap = new java.util.Hashtable();
    private final com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algId;
    private final com.android.internal.org.bouncycastle.crypto.Digest digest;
    private boolean forSigning;
    private final com.android.internal.org.bouncycastle.crypto.AsymmetricBlockCipher rsaEngine;

    static {
        oidMap.put("SHA-1", com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id_SHA1);
        oidMap.put(android.security.keystore.KeyProperties.DIGEST_SHA224, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha224);
        oidMap.put("SHA-256", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha256);
        oidMap.put(android.security.keystore.KeyProperties.DIGEST_SHA384, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha384);
        oidMap.put(android.security.keystore.KeyProperties.DIGEST_SHA512, com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512);
        oidMap.put("SHA-512/224", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512_224);
        oidMap.put("SHA-512/256", com.android.internal.org.bouncycastle.asn1.nist.NISTObjectIdentifiers.id_sha512_256);
        oidMap.put(android.security.keystore.KeyProperties.DIGEST_MD5, com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5);
    }

    public RSADigestSigner(com.android.internal.org.bouncycastle.crypto.Digest digest) {
        this(digest, (com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier) oidMap.get(digest.getAlgorithmName()));
    }

    public RSADigestSigner(com.android.internal.org.bouncycastle.crypto.Digest digest, com.android.internal.org.bouncycastle.asn1.ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.rsaEngine = new com.android.internal.org.bouncycastle.crypto.encodings.PKCS1Encoding(new com.android.internal.org.bouncycastle.crypto.engines.RSABlindedEngine());
        this.digest = digest;
        if (aSN1ObjectIdentifier != null) {
            this.algId = new com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier(aSN1ObjectIdentifier, com.android.internal.org.bouncycastle.asn1.DERNull.INSTANCE);
        } else {
            this.algId = null;
        }
    }

    public java.lang.String getAlgorithmName() {
        return this.digest.getAlgorithmName() + "withRSA";
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Signer
    public void init(boolean z, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter asymmetricKeyParameter;
        this.forSigning = z;
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) {
            asymmetricKeyParameter = (com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) cipherParameters).getParameters();
        } else {
            asymmetricKeyParameter = (com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) cipherParameters;
        }
        if (z && !asymmetricKeyParameter.isPrivate()) {
            throw new java.lang.IllegalArgumentException("signing requires private key");
        }
        if (!z && asymmetricKeyParameter.isPrivate()) {
            throw new java.lang.IllegalArgumentException("verification requires public key");
        }
        reset();
        this.rsaEngine.init(z, cipherParameters);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Signer
    public void update(byte b) {
        this.digest.update(b);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Signer
    public void update(byte[] bArr, int i, int i2) {
        this.digest.update(bArr, i, i2);
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Signer
    public byte[] generateSignature() throws com.android.internal.org.bouncycastle.crypto.CryptoException, com.android.internal.org.bouncycastle.crypto.DataLengthException {
        if (!this.forSigning) {
            throw new java.lang.IllegalStateException("RSADigestSigner not initialised for signature generation.");
        }
        byte[] bArr = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr, 0);
        try {
            byte[] derEncode = derEncode(bArr);
            return this.rsaEngine.processBlock(derEncode, 0, derEncode.length);
        } catch (java.io.IOException e) {
            throw new com.android.internal.org.bouncycastle.crypto.CryptoException("unable to encode signature: " + e.getMessage(), e);
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Signer
    public boolean verifySignature(byte[] bArr) {
        if (this.forSigning) {
            throw new java.lang.IllegalStateException("RSADigestSigner not initialised for verification");
        }
        int digestSize = this.digest.getDigestSize();
        byte[] bArr2 = new byte[digestSize];
        this.digest.doFinal(bArr2, 0);
        try {
            byte[] processBlock = this.rsaEngine.processBlock(bArr, 0, bArr.length);
            byte[] derEncode = derEncode(bArr2);
            if (processBlock.length == derEncode.length) {
                return com.android.internal.org.bouncycastle.util.Arrays.constantTimeAreEqual(processBlock, derEncode);
            }
            if (processBlock.length == derEncode.length - 2) {
                int length = (processBlock.length - digestSize) - 2;
                int length2 = (derEncode.length - digestSize) - 2;
                derEncode[1] = (byte) (derEncode[1] - 2);
                derEncode[3] = (byte) (derEncode[3] - 2);
                int i = 0;
                for (int i2 = 0; i2 < digestSize; i2++) {
                    i |= processBlock[length + i2] ^ derEncode[length2 + i2];
                }
                for (int i3 = 0; i3 < length; i3++) {
                    i |= processBlock[i3] ^ derEncode[i3];
                }
                return i == 0;
            }
            com.android.internal.org.bouncycastle.util.Arrays.constantTimeAreEqual(derEncode, derEncode);
            return false;
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    @Override // com.android.internal.org.bouncycastle.crypto.Signer
    public void reset() {
        this.digest.reset();
    }

    private byte[] derEncode(byte[] bArr) throws java.io.IOException {
        if (this.algId == null) {
            try {
                com.android.internal.org.bouncycastle.asn1.x509.DigestInfo.getInstance(bArr);
                return bArr;
            } catch (java.lang.IllegalArgumentException e) {
                throw new java.io.IOException("malformed DigestInfo for NONEwithRSA hash: " + e.getMessage());
            }
        }
        return new com.android.internal.org.bouncycastle.asn1.x509.DigestInfo(this.algId, bArr).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER);
    }
}
