package org.apache.commons.math;

/* loaded from: classes3.dex */
public abstract class ConvergingAlgorithmImpl implements org.apache.commons.math.ConvergingAlgorithm {
    protected double absoluteAccuracy;
    protected double defaultAbsoluteAccuracy;
    protected int defaultMaximalIterationCount;
    protected double defaultRelativeAccuracy;
    protected int iterationCount;
    protected int maximalIterationCount;
    protected double relativeAccuracy;

    @java.lang.Deprecated
    protected ConvergingAlgorithmImpl() {
    }

    @java.lang.Deprecated
    protected ConvergingAlgorithmImpl(int i, double d) {
        this.defaultAbsoluteAccuracy = d;
        this.defaultRelativeAccuracy = 1.0E-14d;
        this.absoluteAccuracy = d;
        this.relativeAccuracy = this.defaultRelativeAccuracy;
        this.defaultMaximalIterationCount = i;
        this.maximalIterationCount = i;
        this.iterationCount = 0;
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public int getIterationCount() {
        return this.iterationCount;
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public void setAbsoluteAccuracy(double d) {
        this.absoluteAccuracy = d;
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public double getAbsoluteAccuracy() {
        return this.absoluteAccuracy;
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public void resetAbsoluteAccuracy() {
        this.absoluteAccuracy = this.defaultAbsoluteAccuracy;
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public void setMaximalIterationCount(int i) {
        this.maximalIterationCount = i;
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public int getMaximalIterationCount() {
        return this.maximalIterationCount;
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public void resetMaximalIterationCount() {
        this.maximalIterationCount = this.defaultMaximalIterationCount;
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public void setRelativeAccuracy(double d) {
        this.relativeAccuracy = d;
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public double getRelativeAccuracy() {
        return this.relativeAccuracy;
    }

    @Override // org.apache.commons.math.ConvergingAlgorithm
    public void resetRelativeAccuracy() {
        this.relativeAccuracy = this.defaultRelativeAccuracy;
    }

    protected void resetIterationsCounter() {
        this.iterationCount = 0;
    }

    protected void incrementIterationsCounter() throws org.apache.commons.math.MaxIterationsExceededException {
        int i = this.iterationCount + 1;
        this.iterationCount = i;
        if (i > this.maximalIterationCount) {
            throw new org.apache.commons.math.MaxIterationsExceededException(this.maximalIterationCount);
        }
    }
}
