package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public interface GammaDistribution extends org.apache.commons.math.distribution.ContinuousDistribution, org.apache.commons.math.distribution.HasDensity<java.lang.Double> {
    @Override // org.apache.commons.math.distribution.HasDensity
    double density(java.lang.Double d);

    double getAlpha();

    double getBeta();

    @java.lang.Deprecated
    void setAlpha(double d);

    @java.lang.Deprecated
    void setBeta(double d);
}
