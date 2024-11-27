package org.apache.commons.math.distribution;

/* loaded from: classes3.dex */
public interface ZipfDistribution extends org.apache.commons.math.distribution.IntegerDistribution {
    double getExponent();

    int getNumberOfElements();

    @java.lang.Deprecated
    void setExponent(double d);

    @java.lang.Deprecated
    void setNumberOfElements(int i);
}
