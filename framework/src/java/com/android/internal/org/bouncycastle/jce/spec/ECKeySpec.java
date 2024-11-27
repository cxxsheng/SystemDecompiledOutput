package com.android.internal.org.bouncycastle.jce.spec;

/* loaded from: classes4.dex */
public class ECKeySpec implements java.security.spec.KeySpec {
    private com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec spec;

    protected ECKeySpec(com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        this.spec = eCParameterSpec;
    }

    public com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec getParams() {
        return this.spec;
    }
}
