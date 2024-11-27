package com.android.internal.org.bouncycastle.math.ec.endo;

/* loaded from: classes4.dex */
public class GLVTypeBParameters {
    protected final java.math.BigInteger beta;
    protected final java.math.BigInteger lambda;
    protected final com.android.internal.org.bouncycastle.math.ec.endo.ScalarSplitParameters splitParams;

    public GLVTypeBParameters(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger[] bigIntegerArr, java.math.BigInteger[] bigIntegerArr2, java.math.BigInteger bigInteger3, java.math.BigInteger bigInteger4, int i) {
        this.beta = bigInteger;
        this.lambda = bigInteger2;
        this.splitParams = new com.android.internal.org.bouncycastle.math.ec.endo.ScalarSplitParameters(bigIntegerArr, bigIntegerArr2, bigInteger3, bigInteger4, i);
    }

    public GLVTypeBParameters(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, com.android.internal.org.bouncycastle.math.ec.endo.ScalarSplitParameters scalarSplitParameters) {
        this.beta = bigInteger;
        this.lambda = bigInteger2;
        this.splitParams = scalarSplitParameters;
    }

    public java.math.BigInteger getBeta() {
        return this.beta;
    }

    public java.math.BigInteger getLambda() {
        return this.lambda;
    }

    public com.android.internal.org.bouncycastle.math.ec.endo.ScalarSplitParameters getSplitParams() {
        return this.splitParams;
    }

    public java.math.BigInteger getV1A() {
        return getSplitParams().getV1A();
    }

    public java.math.BigInteger getV1B() {
        return getSplitParams().getV1B();
    }

    public java.math.BigInteger getV2A() {
        return getSplitParams().getV2A();
    }

    public java.math.BigInteger getV2B() {
        return getSplitParams().getV2B();
    }

    public java.math.BigInteger getG1() {
        return getSplitParams().getG1();
    }

    public java.math.BigInteger getG2() {
        return getSplitParams().getG2();
    }

    public int getBits() {
        return getSplitParams().getBits();
    }
}
