package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class DSAPrivateKeyParameters extends com.android.internal.org.bouncycastle.crypto.params.DSAKeyParameters {
    private java.math.BigInteger x;

    public DSAPrivateKeyParameters(java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.crypto.params.DSAParameters dSAParameters) {
        super(true, dSAParameters);
        this.x = bigInteger;
    }

    public java.math.BigInteger getX() {
        return this.x;
    }
}
