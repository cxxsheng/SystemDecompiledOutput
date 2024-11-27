package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public interface ExponentialDistribution extends org.apache.commons.math.distribution.ContinuousDistribution, org.apache.commons.math.distribution.HasDensity<java.lang.Double> {
    @Override // org.apache.commons.math.distribution.HasDensity
    double density(java.lang.Double d);

    double getMean();

    @java.lang.Deprecated
    void setMean(double d);
}
