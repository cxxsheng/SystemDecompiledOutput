package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class ECPublicKeyParameters extends com.android.internal.org.bouncycastle.crypto.params.ECKeyParameters {
    private final com.android.internal.org.bouncycastle.math.ec.ECPoint q;

    public ECPublicKeyParameters(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters eCDomainParameters) {
        super(false, eCDomainParameters);
        this.q = eCDomainParameters.validatePublicPoint(eCPoint);
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint getQ() {
        return this.q;
    }
}
