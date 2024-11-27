package com.android.internal.org.bouncycastle.jce.netscape;

/* loaded from: classes4.dex */
public class NetscapeCertRequest extends com.android.internal.org.bouncycastle.asn1.ASN1Object {
    java.lang.String challenge;
    com.android.internal.org.bouncycastle.asn1.DERBitString content;
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier keyAlg;
    java.security.PublicKey pubkey;
    com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier sigAlg;
    byte[] sigBits;

    private static com.android.internal.org.bouncycastle.asn1.ASN1Sequence getReq(byte[] bArr) throws java.io.IOException {
        return com.android.internal.org.bouncycastle.asn1.ASN1Sequence.getInstance(new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(new java.io.ByteArrayInputStream(bArr)).readObject());
    }

    public NetscapeCertRequest(byte[] bArr) throws java.io.IOException {
        this(getReq(bArr));
    }

    public NetscapeCertRequest(com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence) {
        try {
            if (aSN1Sequence.size() != 3) {
                throw new java.lang.IllegalArgumentException("invalid SPKAC (size):" + aSN1Sequence.size());
            }
            this.sigAlg = com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
            this.sigBits = ((com.android.internal.org.bouncycastle.asn1.DERBitString) aSN1Sequence.getObjectAt(2)).getOctets();
            com.android.internal.org.bouncycastle.asn1.ASN1Sequence aSN1Sequence2 = (com.android.internal.org.bouncycastle.asn1.ASN1Sequence) aSN1Sequence.getObjectAt(0);
            if (aSN1Sequence2.size() != 2) {
                throw new java.lang.IllegalArgumentException("invalid PKAC (len): " + aSN1Sequence2.size());
            }
            this.challenge = ((com.android.internal.org.bouncycastle.asn1.DERIA5String) aSN1Sequence2.getObjectAt(1)).getString();
            this.content = new com.android.internal.org.bouncycastle.asn1.DERBitString(aSN1Sequence2);
            com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo subjectPublicKeyInfo = com.android.internal.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo.getInstance(aSN1Sequence2.getObjectAt(0));
            java.security.spec.X509EncodedKeySpec x509EncodedKeySpec = new java.security.spec.X509EncodedKeySpec(new com.android.internal.org.bouncycastle.asn1.DERBitString(subjectPublicKeyInfo).getBytes());
            this.keyAlg = subjectPublicKeyInfo.getAlgorithm();
            this.pubkey = java.security.KeyFactory.getInstance(this.keyAlg.getAlgorithm().getId()).generatePublic(x509EncodedKeySpec);
        } catch (java.lang.Exception e) {
            throw new java.lang.IllegalArgumentException(e.toString());
        }
    }

    public NetscapeCertRequest(java.lang.String str, com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier, java.security.PublicKey publicKey) throws java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException, java.security.NoSuchProviderException {
        this.challenge = str;
        this.sigAlg = algorithmIdentifier;
        this.pubkey = publicKey;
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        aSN1EncodableVector.add(getKeySpec());
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERIA5String(str));
        try {
            this.content = new com.android.internal.org.bouncycastle.asn1.DERBitString(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector));
        } catch (java.io.IOException e) {
            throw new java.security.spec.InvalidKeySpecException("exception encoding key: " + e.toString());
        }
    }

    public java.lang.String getChallenge() {
        return this.challenge;
    }

    public void setChallenge(java.lang.String str) {
        this.challenge = str;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getSigningAlgorithm() {
        return this.sigAlg;
    }

    public void setSigningAlgorithm(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        this.sigAlg = algorithmIdentifier;
    }

    public com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier getKeyAlgorithm() {
        return this.keyAlg;
    }

    public void setKeyAlgorithm(com.android.internal.org.bouncycastle.asn1.x509.AlgorithmIdentifier algorithmIdentifier) {
        this.keyAlg = algorithmIdentifier;
    }

    public java.security.PublicKey getPublicKey() {
        return this.pubkey;
    }

    public void setPublicKey(java.security.PublicKey publicKey) {
        this.pubkey = publicKey;
    }

    public boolean verify(java.lang.String str) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException, java.security.NoSuchProviderException {
        if (!str.equals(this.challenge)) {
            return false;
        }
        java.security.Signature signature = java.security.Signature.getInstance(this.sigAlg.getAlgorithm().getId());
        signature.initVerify(this.pubkey);
        signature.update(this.content.getBytes());
        return signature.verify(this.sigBits);
    }

    public void sign(java.security.PrivateKey privateKey) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException, java.security.NoSuchProviderException, java.security.spec.InvalidKeySpecException {
        sign(privateKey, null);
    }

    public void sign(java.security.PrivateKey privateKey, java.security.SecureRandom secureRandom) throws java.security.NoSuchAlgorithmException, java.security.InvalidKeyException, java.security.SignatureException, java.security.NoSuchProviderException, java.security.spec.InvalidKeySpecException {
        java.security.Signature signature = java.security.Signature.getInstance(this.sigAlg.getAlgorithm().getId());
        if (secureRandom != null) {
            signature.initSign(privateKey, secureRandom);
        } else {
            signature.initSign(privateKey);
        }
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        aSN1EncodableVector.add(getKeySpec());
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERIA5String(this.challenge));
        try {
            signature.update(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector).getEncoded(com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER));
            this.sigBits = signature.sign();
        } catch (java.io.IOException e) {
            throw new java.security.SignatureException(e.getMessage());
        }
    }

    private com.android.internal.org.bouncycastle.asn1.ASN1Primitive getKeySpec() throws java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException, java.security.NoSuchProviderException {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(this.pubkey.getEncoded());
            byteArrayOutputStream.close();
            return new com.android.internal.org.bouncycastle.asn1.ASN1InputStream(new java.io.ByteArrayInputStream(byteArrayOutputStream.toByteArray())).readObject();
        } catch (java.io.IOException e) {
            throw new java.security.spec.InvalidKeySpecException(e.getMessage());
        }
    }

    @Override // com.android.internal.org.bouncycastle.asn1.ASN1Object, com.android.internal.org.bouncycastle.asn1.ASN1Encodable
    public com.android.internal.org.bouncycastle.asn1.ASN1Primitive toASN1Primitive() {
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector aSN1EncodableVector2 = new com.android.internal.org.bouncycastle.asn1.ASN1EncodableVector();
        try {
            aSN1EncodableVector2.add(getKeySpec());
        } catch (java.lang.Exception e) {
        }
        aSN1EncodableVector2.add(new com.android.internal.org.bouncycastle.asn1.DERIA5String(this.challenge));
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector2));
        aSN1EncodableVector.add(this.sigAlg);
        aSN1EncodableVector.add(new com.android.internal.org.bouncycastle.asn1.DERBitString(this.sigBits));
        return new com.android.internal.org.bouncycastle.asn1.DERSequence(aSN1EncodableVector);
    }
}
