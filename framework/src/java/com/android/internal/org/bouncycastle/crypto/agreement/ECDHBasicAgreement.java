package com.android.internal.org.bouncycastle.crypto.agreement;

/* loaded from: classes4.dex */
public class ECDHBasicAgreement implements com.android.internal.org.bouncycastle.crypto.BasicAgreement {
    private com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters key;

    @Override // com.android.internal.org.bouncycastle.crypto.BasicAgreement
    public void init(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        this.key = (com.android.internal.org.bouncycastle.crypto.params.ECPrivateKeyParameters) cipherParameters;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BasicAgreement
    public int getFieldSize() {
        return (this.key.getParameters().getCurve().getFieldSize() + 7) / 8;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BasicAgreement
    public java.math.BigInteger calculateAgreement(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters eCPublicKeyParameters = (com.android.internal.org.bouncycastle.crypto.params.ECPublicKeyParameters) cipherParameters;
        com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters parameters = this.key.getParameters();
        if (!parameters.equals(eCPublicKeyParameters.getParameters())) {
            throw new java.lang.IllegalStateException("ECDH public key has wrong domain parameters");
        }
        java.math.BigInteger d = this.key.getD();
        com.android.internal.org.bouncycastle.math.ec.ECPoint cleanPoint = com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.cleanPoint(parameters.getCurve(), eCPublicKeyParameters.getQ());
        if (cleanPoint.isInfinity()) {
            throw new java.lang.IllegalStateException("Infinity is not a valid public key for ECDH");
        }
        java.math.BigInteger h = parameters.getH();
        if (!h.equals(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE)) {
            d = parameters.getHInv().multiply(d).mod(parameters.getN());
            cleanPoint = com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.referenceMultiply(cleanPoint, h);
        }
        com.android.internal.org.bouncycastle.math.ec.ECPoint normalize = cleanPoint.multiply(d).normalize();
        if (normalize.isInfinity()) {
            throw new java.lang.IllegalStateException("Infinity is not a valid agreement value for ECDH");
        }
        return normalize.getAffineXCoord().toBigInteger();
    }
}
