package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public interface WeibullDistribution extends org.apache.commons.math.distribution.ContinuousDistribution {
    double getScale();

    double getShape();

    @java.lang.Deprecated
    void setScale(double d);

    @java.lang.Deprecated
    void setShape(double d);
}
