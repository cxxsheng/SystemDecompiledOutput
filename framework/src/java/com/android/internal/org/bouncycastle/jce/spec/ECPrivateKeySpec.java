package com.android.internal.org.bouncycastle.jce.spec;

/* loaded from: classes4.dex */
public class ECPrivateKeySpec extends com.android.internal.org.bouncycastle.jce.spec.ECKeySpec {
    private java.math.BigInteger d;

    public ECPrivateKeySpec(java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        super(eCParameterSpec);
        this.d = bigInteger;
    }

    public java.math.BigInteger getD() {
        return this.d;
    }
}
