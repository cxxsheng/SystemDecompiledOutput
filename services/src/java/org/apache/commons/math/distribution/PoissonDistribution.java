package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public interface PoissonDistribution extends org.apache.commons.math.distribution.IntegerDistribution {
    double getMean();

    double normalApproximateProbability(int i) throws org.apache.commons.math.MathException;

    @java.lang.Deprecated
    void setMean(double d);
}
