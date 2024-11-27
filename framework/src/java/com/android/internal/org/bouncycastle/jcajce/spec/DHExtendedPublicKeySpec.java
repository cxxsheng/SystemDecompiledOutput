package com.android.internal.org.bouncycastle.jcajce.spec;

/* loaded from: classes4.dex */
public class DHExtendedPublicKeySpec extends javax.crypto.spec.DHPublicKeySpec {
    private final javax.crypto.spec.DHParameterSpec params;

    public DHExtendedPublicKeySpec(java.math.BigInteger bigInteger, javax.crypto.spec.DHParameterSpec dHParameterSpec) {
        super(bigInteger, dHParameterSpec.getP(), dHParameterSpec.getG());
        this.params = dHParameterSpec;
    }

    public javax.crypto.spec.DHParameterSpec getParams() {
        return this.params;
    }
}
