package org.apache.commons.math.stat.descriptive;

/* loaded from: classes3.dex */
public interface StatisticalMultivariateSummary {
    org.apache.commons.math.linear.RealMatrix getCovariance();

    int getDimension();

    double[] getGeometricMean();

    double[] getMax();

    double[] getMean();

    double[] getMin();

    long getN();

    double[] getStandardDeviation();

    double[] getSum();

    double[] getSumLog();

    double[] getSumSq();
}
