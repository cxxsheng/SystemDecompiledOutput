package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
public abstract class AdaptiveStepsizeIntegrator extends org.apache.commons.math.ode.AbstractIntegrator {
    private double initialStep;
    protected int mainSetDimension;
    private final double maxStep;
    private final double minStep;
    protected final double scalAbsoluteTolerance;
    protected final double scalRelativeTolerance;
    protected final double[] vecAbsoluteTolerance;
    protected final double[] vecRelativeTolerance;

    @Override // org.apache.commons.math.ode.FirstOrderIntegrator
    public abstract double integrate(org.apache.commons.math.ode.FirstOrderDifferentialEquations firstOrderDifferentialEquations, double d, double[] dArr, double d2, double[] dArr2) throws org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException;

    public AdaptiveStepsizeIntegrator(java.lang.String str, double d, double d2, double d3, double d4) {
        super(str);
        this.minStep = org.apache.commons.math.util.FastMath.abs(d);
        this.maxStep = org.apache.commons.math.util.FastMath.abs(d2);
        this.initialStep = -1.0d;
        this.scalAbsoluteTolerance = d3;
        this.scalRelativeTolerance = d4;
        this.vecAbsoluteTolerance = null;
        this.vecRelativeTolerance = null;
        resetInternalState();
    }

    public AdaptiveStepsizeIntegrator(java.lang.String str, double d, double d2, double[] dArr, double[] dArr2) {
        super(str);
        this.minStep = d;
        this.maxStep = d2;
        this.initialStep = -1.0d;
        this.scalAbsoluteTolerance = 0.0d;
        this.scalRelativeTolerance = 0.0d;
        this.vecAbsoluteTolerance = (double[]) dArr.clone();
        this.vecRelativeTolerance = (double[]) dArr2.clone();
        resetInternalState();
    }

    public void setInitialStepSize(double d) {
        if (d < this.minStep || d > this.maxStep) {
            this.initialStep = -1.0d;
        } else {
            this.initialStep = d;
        }
    }

    @Override // org.apache.commons.math.ode.AbstractIntegrator
    protected void sanityChecks(org.apache.commons.math.ode.FirstOrderDifferentialEquations firstOrderDifferentialEquations, double d, double[] dArr, double d2, double[] dArr2) throws org.apache.commons.math.ode.IntegratorException {
        super.sanityChecks(firstOrderDifferentialEquations, d, dArr, d2, dArr2);
        if (firstOrderDifferentialEquations instanceof org.apache.commons.math.ode.ExtendedFirstOrderDifferentialEquations) {
            this.mainSetDimension = ((org.apache.commons.math.ode.ExtendedFirstOrderDifferentialEquations) firstOrderDifferentialEquations).getMainSetDimension();
        } else {
            this.mainSetDimension = firstOrderDifferentialEquations.getDimension();
        }
        if (this.vecAbsoluteTolerance != null && this.vecAbsoluteTolerance.length != this.mainSetDimension) {
            throw new org.apache.commons.math.ode.IntegratorException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(this.mainSetDimension), java.lang.Integer.valueOf(this.vecAbsoluteTolerance.length));
        }
        if (this.vecRelativeTolerance != null && this.vecRelativeTolerance.length != this.mainSetDimension) {
            throw new org.apache.commons.math.ode.IntegratorException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, java.lang.Integer.valueOf(this.mainSetDimension), java.lang.Integer.valueOf(this.vecRelativeTolerance.length));
        }
    }

    public double initializeStep(org.apache.commons.math.ode.FirstOrderDifferentialEquations firstOrderDifferentialEquations, boolean z, int i, double[] dArr, double d, double[] dArr2, double[] dArr3, double[] dArr4, double[] dArr5) throws org.apache.commons.math.ode.DerivativeException {
        double pow;
        if (this.initialStep > 0.0d) {
            double d2 = this.initialStep;
            return z ? d2 : -d2;
        }
        double d3 = 0.0d;
        double d4 = 0.0d;
        for (int i2 = 0; i2 < dArr.length; i2++) {
            double d5 = dArr2[i2] / dArr[i2];
            d3 += d5 * d5;
            double d6 = dArr3[i2] / dArr[i2];
            d4 += d6 * d6;
        }
        double sqrt = (d3 < 1.0E-10d || d4 < 1.0E-10d) ? 1.0E-6d : org.apache.commons.math.util.FastMath.sqrt(d3 / d4) * 0.01d;
        if (!z) {
            sqrt = -sqrt;
        }
        for (int i3 = 0; i3 < dArr2.length; i3++) {
            dArr4[i3] = dArr2[i3] + (dArr3[i3] * sqrt);
        }
        computeDerivatives(d + sqrt, dArr4, dArr5);
        double d7 = 0.0d;
        for (int i4 = 0; i4 < dArr.length; i4++) {
            double d8 = (dArr5[i4] - dArr3[i4]) / dArr[i4];
            d7 += d8 * d8;
        }
        double max = org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.sqrt(d4), org.apache.commons.math.util.FastMath.sqrt(d7) / sqrt);
        if (max < 1.0E-15d) {
            pow = org.apache.commons.math.util.FastMath.max(1.0E-6d, org.apache.commons.math.util.FastMath.abs(sqrt) * 0.001d);
        } else {
            pow = org.apache.commons.math.util.FastMath.pow(0.01d / max, 1.0d / i);
        }
        double max2 = org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.min(org.apache.commons.math.util.FastMath.abs(sqrt) * 100.0d, pow), org.apache.commons.math.util.FastMath.abs(d) * 1.0E-12d);
        if (max2 < getMinStep()) {
            max2 = getMinStep();
        }
        if (max2 > getMaxStep()) {
            max2 = getMaxStep();
        }
        if (!z) {
            return -max2;
        }
        return max2;
    }

    protected double filterStep(double d, boolean z, boolean z2) throws org.apache.commons.math.ode.IntegratorException {
        if (org.apache.commons.math.util.FastMath.abs(d) < this.minStep) {
            if (z2) {
                d = this.minStep;
                if (!z) {
                    d = -d;
                }
            } else {
                throw new org.apache.commons.math.ode.IntegratorException(org.apache.commons.math.exception.util.LocalizedFormats.MINIMAL_STEPSIZE_REACHED_DURING_INTEGRATION, java.lang.Double.valueOf(this.minStep), java.lang.Double.valueOf(org.apache.commons.math.util.FastMath.abs(d)));
            }
        }
        if (d > this.maxStep) {
            return this.maxStep;
        }
        if (d < (-this.maxStep)) {
            return -this.maxStep;
        }
        return d;
    }

    @Override // org.apache.commons.math.ode.AbstractIntegrator, org.apache.commons.math.ode.ODEIntegrator
    public double getCurrentStepStart() {
        return this.stepStart;
    }

    protected void resetInternalState() {
        this.stepStart = Double.NaN;
        this.stepSize = org.apache.commons.math.util.FastMath.sqrt(this.minStep * this.maxStep);
    }

    public double getMinStep() {
        return this.minStep;
    }

    public double getMaxStep() {
        return this.maxStep;
    }
}
