package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class DSAPublicKeyParameters extends com.android.internal.org.bouncycastle.crypto.params.DSAKeyParameters {
    private static final java.math.BigInteger ONE = java.math.BigInteger.valueOf(1);
    private static final java.math.BigInteger TWO = java.math.BigInteger.valueOf(2);
    private java.math.BigInteger y;

    public DSAPublicKeyParameters(java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.crypto.params.DSAParameters dSAParameters) {
        super(false, dSAParameters);
        this.y = validate(bigInteger, dSAParameters);
    }

    private java.math.BigInteger validate(java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.crypto.params.DSAParameters dSAParameters) {
        if (dSAParameters != null) {
            if (TWO.compareTo(bigInteger) <= 0 && dSAParameters.getP().subtract(TWO).compareTo(bigInteger) >= 0 && ONE.equals(bigInteger.modPow(dSAParameters.getQ(), dSAParameters.getP()))) {
                return bigInteger;
            }
            throw new java.lang.IllegalArgumentException("y value does not appear to be in correct group");
        }
        return bigInteger;
    }

    public java.math.BigInteger getY() {
        return this.y;
    }
}
