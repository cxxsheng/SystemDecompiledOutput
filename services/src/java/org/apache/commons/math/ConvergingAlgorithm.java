package org.apache.commons.math;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public interface ConvergingAlgorithm {
    double getAbsoluteAccuracy();

    int getIterationCount();

    int getMaximalIterationCount();

    double getRelativeAccuracy();

    void resetAbsoluteAccuracy();

    void resetMaximalIterationCount();

    void resetRelativeAccuracy();

    void setAbsoluteAccuracy(double d);

    void setMaximalIterationCount(int i);

    void setRelativeAccuracy(double d);
}
