package org.apache.commons.math.stat.inference;

/* loaded from: classes3.dex */
public class TestUtils {
    private static org.apache.commons.math.stat.inference.TTest tTest = new org.apache.commons.math.stat.inference.TTestImpl();
    private static org.apache.commons.math.stat.inference.ChiSquareTest chiSquareTest = new org.apache.commons.math.stat.inference.ChiSquareTestImpl();
    private static org.apache.commons.math.stat.inference.UnknownDistributionChiSquareTest unknownDistributionChiSquareTest = new org.apache.commons.math.stat.inference.ChiSquareTestImpl();
    private static org.apache.commons.math.stat.inference.OneWayAnova oneWayAnova = new org.apache.commons.math.stat.inference.OneWayAnovaImpl();

    protected TestUtils() {
    }

    @java.lang.Deprecated
    public static void setChiSquareTest(org.apache.commons.math.stat.inference.TTest tTest2) {
        tTest = tTest2;
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.stat.inference.TTest getTTest() {
        return tTest;
    }

    @java.lang.Deprecated
    public static void setChiSquareTest(org.apache.commons.math.stat.inference.ChiSquareTest chiSquareTest2) {
        chiSquareTest = chiSquareTest2;
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.stat.inference.ChiSquareTest getChiSquareTest() {
        return chiSquareTest;
    }

    @java.lang.Deprecated
    public static void setUnknownDistributionChiSquareTest(org.apache.commons.math.stat.inference.UnknownDistributionChiSquareTest unknownDistributionChiSquareTest2) {
        unknownDistributionChiSquareTest = unknownDistributionChiSquareTest2;
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.stat.inference.UnknownDistributionChiSquareTest getUnknownDistributionChiSquareTest() {
        return unknownDistributionChiSquareTest;
    }

    @java.lang.Deprecated
    public static void setOneWayAnova(org.apache.commons.math.stat.inference.OneWayAnova oneWayAnova2) {
        oneWayAnova = oneWayAnova2;
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.stat.inference.OneWayAnova getOneWayAnova() {
        return oneWayAnova;
    }

    public static double homoscedasticT(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        return tTest.homoscedasticT(dArr, dArr2);
    }

    public static double homoscedasticT(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2) throws java.lang.IllegalArgumentException {
        return tTest.homoscedasticT(statisticalSummary, statisticalSummary2);
    }

    public static boolean homoscedasticTTest(double[] dArr, double[] dArr2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return tTest.homoscedasticTTest(dArr, dArr2, d);
    }

    public static double homoscedasticTTest(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return tTest.homoscedasticTTest(dArr, dArr2);
    }

    public static double homoscedasticTTest(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return tTest.homoscedasticTTest(statisticalSummary, statisticalSummary2);
    }

    public static double pairedT(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return tTest.pairedT(dArr, dArr2);
    }

    public static boolean pairedTTest(double[] dArr, double[] dArr2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return tTest.pairedTTest(dArr, dArr2, d);
    }

    public static double pairedTTest(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return tTest.pairedTTest(dArr, dArr2);
    }

    public static double t(double d, double[] dArr) throws java.lang.IllegalArgumentException {
        return tTest.t(d, dArr);
    }

    public static double t(double d, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary) throws java.lang.IllegalArgumentException {
        return tTest.t(d, statisticalSummary);
    }

    public static double t(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        return tTest.t(dArr, dArr2);
    }

    public static double t(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2) throws java.lang.IllegalArgumentException {
        return tTest.t(statisticalSummary, statisticalSummary2);
    }

    public static boolean tTest(double d, double[] dArr, double d2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return tTest.tTest(d, dArr, d2);
    }

    public static double tTest(double d, double[] dArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return tTest.tTest(d, dArr);
    }

    public static boolean tTest(double d, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, double d2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return tTest.tTest(d, statisticalSummary, d2);
    }

    public static double tTest(double d, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return tTest.tTest(d, statisticalSummary);
    }

    public static boolean tTest(double[] dArr, double[] dArr2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return tTest.tTest(dArr, dArr2, d);
    }

    public static double tTest(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return tTest.tTest(dArr, dArr2);
    }

    public static boolean tTest(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return tTest.tTest(statisticalSummary, statisticalSummary2, d);
    }

    public static double tTest(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return tTest.tTest(statisticalSummary, statisticalSummary2);
    }

    public static double chiSquare(double[] dArr, long[] jArr) throws java.lang.IllegalArgumentException {
        return chiSquareTest.chiSquare(dArr, jArr);
    }

    public static double chiSquare(long[][] jArr) throws java.lang.IllegalArgumentException {
        return chiSquareTest.chiSquare(jArr);
    }

    public static boolean chiSquareTest(double[] dArr, long[] jArr, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return chiSquareTest.chiSquareTest(dArr, jArr, d);
    }

    public static double chiSquareTest(double[] dArr, long[] jArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return chiSquareTest.chiSquareTest(dArr, jArr);
    }

    public static boolean chiSquareTest(long[][] jArr, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return chiSquareTest.chiSquareTest(jArr, d);
    }

    public static double chiSquareTest(long[][] jArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return chiSquareTest.chiSquareTest(jArr);
    }

    public static double chiSquareDataSetsComparison(long[] jArr, long[] jArr2) throws java.lang.IllegalArgumentException {
        return unknownDistributionChiSquareTest.chiSquareDataSetsComparison(jArr, jArr2);
    }

    public static double chiSquareTestDataSetsComparison(long[] jArr, long[] jArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return unknownDistributionChiSquareTest.chiSquareTestDataSetsComparison(jArr, jArr2);
    }

    public static boolean chiSquareTestDataSetsComparison(long[] jArr, long[] jArr2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return unknownDistributionChiSquareTest.chiSquareTestDataSetsComparison(jArr, jArr2, d);
    }

    public static double oneWayAnovaFValue(java.util.Collection<double[]> collection) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return oneWayAnova.anovaFValue(collection);
    }

    public static double oneWayAnovaPValue(java.util.Collection<double[]> collection) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return oneWayAnova.anovaPValue(collection);
    }

    public static boolean oneWayAnovaTest(java.util.Collection<double[]> collection, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return oneWayAnova.anovaTest(collection, d);
    }
}
