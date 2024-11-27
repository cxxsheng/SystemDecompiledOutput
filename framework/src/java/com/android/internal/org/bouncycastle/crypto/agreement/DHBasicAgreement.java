package com.android.internal.org.bouncycastle.crypto.agreement;

/* loaded from: classes4.dex */
public class DHBasicAgreement implements com.android.internal.org.bouncycastle.crypto.BasicAgreement {
    private static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);
    private com.android.internal.org.bouncycastle.crypto.params.DHParameters dhParams;
    private com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters key;

    @Override // com.android.internal.org.bouncycastle.crypto.BasicAgreement
    public void init(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter asymmetricKeyParameter;
        if (cipherParameters instanceof com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) {
            asymmetricKeyParameter = (com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) ((com.android.internal.org.bouncycastle.crypto.params.ParametersWithRandom) cipherParameters).getParameters();
        } else {
            asymmetricKeyParameter = (com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) cipherParameters;
        }
        if (!(asymmetricKeyParameter instanceof com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters)) {
            throw new java.lang.IllegalArgumentException("DHEngine expects DHPrivateKeyParameters");
        }
        this.key = (com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters) asymmetricKeyParameter;
        this.dhParams = this.key.getParameters();
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BasicAgreement
    public int getFieldSize() {
        return (this.key.getParameters().getP().bitLength() + 7) / 8;
    }

    @Override // com.android.internal.org.bouncycastle.crypto.BasicAgreement
    public java.math.BigInteger calculateAgreement(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters dHPublicKeyParameters = (com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters) cipherParameters;
        if (!dHPublicKeyParameters.getParameters().equals(this.dhParams)) {
            throw new java.lang.IllegalArgumentException("Diffie-Hellman public key has wrong parameters.");
        }
        java.math.BigInteger p = this.dhParams.getP();
        java.math.BigInteger y = dHPublicKeyParameters.getY();
        if (y == null || y.compareTo(ONE) <= 0 || y.compareTo(p.subtract(ONE)) >= 0) {
            throw new java.lang.IllegalArgumentException("Diffie-Hellman public key is weak");
        }
        java.math.BigInteger modPow = y.modPow(this.key.getX(), p);
        if (modPow.equals(ONE)) {
            throw new java.lang.IllegalStateException("Shared key can't be 1");
        }
        return modPow;
    }
}
