package com.android.internal.org.bouncycastle.jce.provider;

/* loaded from: classes4.dex */
public class DHUtil {
    public static com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter generatePublicKeyParameter(java.security.PublicKey publicKey) throws java.security.InvalidKeyException {
        if (publicKey instanceof javax.crypto.interfaces.DHPublicKey) {
            javax.crypto.interfaces.DHPublicKey dHPublicKey = (javax.crypto.interfaces.DHPublicKey) publicKey;
            return new com.android.internal.org.bouncycastle.crypto.params.DHPublicKeyParameters(dHPublicKey.getY(), new com.android.internal.org.bouncycastle.crypto.params.DHParameters(dHPublicKey.getParams().getP(), dHPublicKey.getParams().getG(), null, dHPublicKey.getParams().getL()));
        }
        throw new java.security.InvalidKeyException("can't identify DH public key.");
    }

    public static com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter generatePrivateKeyParameter(java.security.PrivateKey privateKey) throws java.security.InvalidKeyException {
        if (privateKey instanceof javax.crypto.interfaces.DHPrivateKey) {
            javax.crypto.interfaces.DHPrivateKey dHPrivateKey = (javax.crypto.interfaces.DHPrivateKey) privateKey;
            return new com.android.internal.org.bouncycastle.crypto.params.DHPrivateKeyParameters(dHPrivateKey.getX(), new com.android.internal.org.bouncycastle.crypto.params.DHParameters(dHPrivateKey.getParams().getP(), dHPrivateKey.getParams().getG(), null, dHPrivateKey.getParams().getL()));
        }
        throw new java.security.InvalidKeyException("can't identify DH private key.");
    }
}
