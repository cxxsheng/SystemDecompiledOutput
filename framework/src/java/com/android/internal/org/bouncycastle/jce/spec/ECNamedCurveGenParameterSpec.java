package com.android.internal.org.bouncycastle.jce.spec;

/* loaded from: classes4.dex */
public class ECNamedCurveGenParameterSpec implements java.security.spec.AlgorithmParameterSpec {
    private java.lang.String name;

    public ECNamedCurveGenParameterSpec(java.lang.String str) {
        this.name = str;
    }

    public java.lang.String getName() {
        return this.name;
    }
}
