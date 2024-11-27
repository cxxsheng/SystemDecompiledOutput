package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class ECPrivateKeyParameters extends com.android.internal.org.bouncycastle.crypto.params.ECKeyParameters {
    private final java.math.BigInteger d;

    public ECPrivateKeyParameters(java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters eCDomainParameters) {
        super(true, eCDomainParameters);
        this.d = eCDomainParameters.validatePrivateScalar(bigInteger);
    }

    public java.math.BigInteger getD() {
        return this.d;
    }
}
