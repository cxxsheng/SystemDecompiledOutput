package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public interface HypergeometricDistribution extends org.apache.commons.math.distribution.IntegerDistribution {
    int getNumberOfSuccesses();

    int getPopulationSize();

    int getSampleSize();

    @java.lang.Deprecated
    void setNumberOfSuccesses(int i);

    @java.lang.Deprecated
    void setPopulationSize(int i);

    @java.lang.Deprecated
    void setSampleSize(int i);
}
