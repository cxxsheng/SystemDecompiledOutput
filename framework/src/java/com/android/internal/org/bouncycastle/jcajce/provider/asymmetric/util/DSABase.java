package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util;

/* loaded from: classes4.dex */
public abstract class DSABase extends java.security.SignatureSpi implements com.android.internal.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers, com.android.internal.org.bouncycastle.asn1.x509.X509ObjectIdentifiers {
    protected com.android.internal.org.bouncycastle.crypto.Digest digest;
    protected com.android.internal.org.bouncycastle.crypto.signers.DSAEncoding encoding;
    protected com.android.internal.org.bouncycastle.crypto.DSAExt signer;

    protected DSABase(com.android.internal.org.bouncycastle.crypto.Digest digest, com.android.internal.org.bouncycastle.crypto.DSAExt dSAExt, com.android.internal.org.bouncycastle.crypto.signers.DSAEncoding dSAEncoding) {
        this.digest = digest;
        this.signer = dSAExt;
        this.encoding = dSAEncoding;
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
    protected void engineSetParameter(java.security.spec.AlgorithmParameterSpec algorithmParameterSpec) {
        throw new java.lang.UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override // java.security.SignatureSpi
    protected void engineSetParameter(java.lang.String str, java.lang.Object obj) {
        throw new java.lang.UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override // java.security.SignatureSpi
    protected java.lang.Object engineGetParameter(java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("engineSetParameter unsupported");
    }
}
