package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public interface FDistribution extends org.apache.commons.math.distribution.ContinuousDistribution {
    double getDenominatorDegreesOfFreedom();

    double getNumeratorDegreesOfFreedom();

    @java.lang.Deprecated
    void setDenominatorDegreesOfFreedom(double d);

    @java.lang.Deprecated
    void setNumeratorDegreesOfFreedom(double d);
}
