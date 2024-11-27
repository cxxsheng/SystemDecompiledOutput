package com.android.internal.org.bouncycastle.math.ec.endo;

/* loaded from: classes4.dex */
public class GLVTypeAParameters {
    protected final java.math.BigInteger i;
    protected final java.math.BigInteger lambda;
    protected final com.android.internal.org.bouncycastle.math.ec.endo.ScalarSplitParameters splitParams;

    public GLVTypeAParameters(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, com.android.internal.org.bouncycastle.math.ec.endo.ScalarSplitParameters scalarSplitParameters) {
        this.i = bigInteger;
        this.lambda = bigInteger2;
        this.splitParams = scalarSplitParameters;
    }

    public java.math.BigInteger getI() {
        return this.i;
    }

    public java.math.BigInteger getLambda() {
        return this.lambda;
    }

    public com.android.internal.org.bouncycastle.math.ec.endo.ScalarSplitParameters getSplitParams() {
        return this.splitParams;
    }
}
