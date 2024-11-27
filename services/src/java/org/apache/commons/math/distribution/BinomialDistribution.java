package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public interface BinomialDistribution extends org.apache.commons.math.distribution.IntegerDistribution {
    int getNumberOfTrials();

    double getProbabilityOfSuccess();

    @java.lang.Deprecated
    void setNumberOfTrials(int i);

    @java.lang.Deprecated
    void setProbabilityOfSuccess(double d);
}
