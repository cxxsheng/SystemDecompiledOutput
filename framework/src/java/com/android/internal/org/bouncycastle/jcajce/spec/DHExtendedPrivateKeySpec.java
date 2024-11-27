package com.android.internal.org.bouncycastle.jcajce.spec;

/* loaded from: classes4.dex */
public class DHExtendedPrivateKeySpec extends javax.crypto.spec.DHPrivateKeySpec {
    private final javax.crypto.spec.DHParameterSpec params;

    public DHExtendedPrivateKeySpec(java.math.BigInteger bigInteger, javax.crypto.spec.DHParameterSpec dHParameterSpec) {
        super(bigInteger, dHParameterSpec.getP(), dHParameterSpec.getG());
        this.params = dHParameterSpec;
    }

    public javax.crypto.spec.DHParameterSpec getParams() {
        return this.params;
    }
}
