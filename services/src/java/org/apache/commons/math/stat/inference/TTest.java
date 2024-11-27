package org.apache.commons.math.stat.inference;

/* loaded from: classes3.dex */
public interface TTest {
    double homoscedasticT(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2) throws java.lang.IllegalArgumentException;

    double homoscedasticT(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException;

    double homoscedasticTTest(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    double homoscedasticTTest(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    boolean homoscedasticTTest(double[] dArr, double[] dArr2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    double pairedT(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    double pairedTTest(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    boolean pairedTTest(double[] dArr, double[] dArr2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    double t(double d, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary) throws java.lang.IllegalArgumentException;

    double t(double d, double[] dArr) throws java.lang.IllegalArgumentException;

    double t(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2) throws java.lang.IllegalArgumentException;

    double t(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException;

    double tTest(double d, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    double tTest(double d, double[] dArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    double tTest(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    double tTest(double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    boolean tTest(double d, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, double d2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    boolean tTest(double d, double[] dArr, double d2) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    boolean tTest(org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary, org.apache.commons.math.stat.descriptive.StatisticalSummary statisticalSummary2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;

    boolean tTest(double[] dArr, double[] dArr2, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException;
}
