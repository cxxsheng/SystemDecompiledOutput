package org.apache.commons.math.stat.inference;

/* loaded from: classes3.dex */
public interface UnknownDistributionChiSquareTest extends org.apache.commons.math.stat.inference.ChiSquareTest {
    double chiSquareDataSetsComparison(long[] jArr, long[] jArr2) throws java.lang.IllegalArgumentException;

    double chiSquareTestDataSetsComparison(long[] jArr, long[] jArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    boolean chiSquareTestDataSetsComparison(long[] jArr, long[] jArr2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;
}
