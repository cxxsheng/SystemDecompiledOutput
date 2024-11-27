package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec;

/* loaded from: classes4.dex */
public class SignatureSpi extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.DSABase {
    SignatureSpi(com.android.internal.org.bouncycastle.crypto.Digest digest, com.android.internal.org.bouncycastle.crypto.DSAExt dSAExt, com.android.internal.org.bouncycastle.crypto.signers.DSAEncoding dSAEncoding) {
        super(digest, dSAExt, dSAEncoding);
    }

    @Override // java.security.SignatureSpi
    protected void engineInitVerify(java.security.PublicKey publicKey) throws java.security.InvalidKeyException {
        com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter generatePublicKeyParameter = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.ECUtils.generatePublicKeyParameter(publicKey);
        this.digest.reset();
        this.signer.init(false, generatePublicKeyParameter);
    }

    @Override // java.security.SignatureSpi
    protected void engineInitSign(java.security.PrivateKey privateKey) throws java.security.InvalidKeyException {
        com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter generatePrivateKeyParameter = com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil.generatePrivateKeyParameter(privateKey);
        this.digest.reset();
        if (this.appRandom != null) {
            this.signer.init(true, new com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom(generatePrivateKeyParameter, this.appRandom));
        } else {
            this.signer.init(true, generatePrivateKeyParameter);
        }
    }

    @Override // java.security.SignatureSpi
    protected java.security.AlgorithmParameters engineGetParameters() {
        return null;
    }

    public static class ecDSA extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi {
        public ecDSA() {
            super(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA1(), new com.android.internal.org.bouncycastle.crypto.signers.ECDSASigner(), com.android.internal.org.bouncycastle.crypto.signers.StandardDSAEncoding.INSTANCE);
        }
    }

    public static class ecDSAnone extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi {
        public ecDSAnone() {
            super(new com.android.internal.org.bouncycastle.crypto.digests.NullDigest(), new com.android.internal.org.bouncycastle.crypto.signers.ECDSASigner(), com.android.internal.org.bouncycastle.crypto.signers.StandardDSAEncoding.INSTANCE);
        }
    }

    public static class ecDSA224 extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi {
        public ecDSA224() {
            super(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA224(), new com.android.internal.org.bouncycastle.crypto.signers.ECDSASigner(), com.android.internal.org.bouncycastle.crypto.signers.StandardDSAEncoding.INSTANCE);
        }
    }

    public static class ecDSA256 extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi {
        public ecDSA256() {
            super(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA256(), new com.android.internal.org.bouncycastle.crypto.signers.ECDSASigner(), com.android.internal.org.bouncycastle.crypto.signers.StandardDSAEncoding.INSTANCE);
        }
    }

    public static class ecDSA384 extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi {
        public ecDSA384() {
            super(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA384(), new com.android.internal.org.bouncycastle.crypto.signers.ECDSASigner(), com.android.internal.org.bouncycastle.crypto.signers.StandardDSAEncoding.INSTANCE);
        }
    }

    public static class ecDSA512 extends com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.ec.SignatureSpi {
        public ecDSA512() {
            super(com.android.internal.org.bouncycastle.crypto.digests.AndroidDigestFactory.getSHA512(), new com.android.internal.org.bouncycastle.crypto.signers.ECDSASigner(), com.android.internal.org.bouncycastle.crypto.signers.StandardDSAEncoding.INSTANCE);
        }
    }
}
