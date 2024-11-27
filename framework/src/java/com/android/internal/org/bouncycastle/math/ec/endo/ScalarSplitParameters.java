package com.android.internal.org.bouncycastle.math.ec.endo;

/* loaded from: classes4.dex */
public class ScalarSplitParameters {
    protected final int bits;
    protected final java.math.BigInteger g1;
    protected final java.math.BigInteger g2;
    protected final java.math.BigInteger v1A;
    protected final java.math.BigInteger v1B;
    protected final java.math.BigInteger v2A;
    protected final java.math.BigInteger v2B;

    private static void checkVector(java.math.BigInteger[] bigIntegerArr, java.lang.String str) {
        if (bigIntegerArr == null || bigIntegerArr.length != 2 || bigIntegerArr[0] == null || bigIntegerArr[1] == null) {
            throw new java.lang.IllegalArgumentException("'" + str + "' must consist of exactly 2 (non-null) values");
        }
    }

    public ScalarSplitParameters(java.math.BigInteger[] bigIntegerArr, java.math.BigInteger[] bigIntegerArr2, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, int i) {
        checkVector(bigIntegerArr, "v1");
        checkVector(bigIntegerArr2, "v2");
        this.v1A = bigIntegerArr[0];
        this.v1B = bigIntegerArr[1];
        this.v2A = bigIntegerArr2[0];
        this.v2B = bigIntegerArr2[1];
        this.g1 = bigInteger;
        this.g2 = bigInteger2;
        this.bits = i;
    }

    public java.math.BigInteger getV1A() {
        return this.v1A;
    }

    public java.math.BigInteger getV1B() {
        return this.v1B;
    }

    public java.math.BigInteger getV2A() {
        return this.v2A;
    }

    public java.math.BigInteger getV2B() {
        return this.v2B;
    }

    public java.math.BigInteger getG1() {
        return this.g1;
    }

    public java.math.BigInteger getG2() {
        return this.g2;
    }

    public int getBits() {
        return this.bits;
    }
}
