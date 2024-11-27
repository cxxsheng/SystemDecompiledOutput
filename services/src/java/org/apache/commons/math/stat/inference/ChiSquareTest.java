package org.apache.commons.math.stat.inference;

/* loaded from: classes3.dex */
public interface ChiSquareTest {
    double chiSquare(double[] dArr, long[] jArr) throws java.lang.IllegalArgumentException;

    double chiSquare(long[][] jArr) throws java.lang.IllegalArgumentException;

    double chiSquareTest(double[] dArr, long[] jArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    double chiSquareTest(long[][] jArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    boolean chiSquareTest(double[] dArr, long[] jArr, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    boolean chiSquareTest(long[][] jArr, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;
}
