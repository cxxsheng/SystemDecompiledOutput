package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
public class AdamsMoultonIntegrator extends org.apache.commons.math.ode.nonstiff.AdamsIntegrator {
    private static final java.lang.String METHOD_NAME = "Adams-Moulton";

    public AdamsMoultonIntegrator(int i, double d, double d2, double d3, double d4) throws java.lang.IllegalArgumentException {
        super(METHOD_NAME, i, i + 1, d, d2, d3, d4);
    }

    public AdamsMoultonIntegrator(int i, double d, double d2, double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        super(METHOD_NAME, i, i + 1, d, d2, dArr, dArr2);
    }

    @Override // org.apache.commons.math.ode.nonstiff.AdamsIntegrator, org.apache.commons.math.ode.nonstiff.AdaptiveStepsizeIntegrator, org.apache.commons.math.ode.FirstOrderIntegrator
    public double integrate(org.apache.commons.math.ode.FirstOrderDifferentialEquations firstOrderDifferentialEquations, double d, double[] dArr, double d2, double[] dArr2) throws org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        double d3;
        double[] dArr3 = dArr;
        double[] dArr4 = dArr2;
        int length = dArr3.length;
        sanityChecks(firstOrderDifferentialEquations, d, dArr, d2, dArr2);
        setEquations(firstOrderDifferentialEquations);
        resetEvaluations();
        boolean z = d2 > d;
        if (dArr4 != dArr3) {
            java.lang.System.arraycopy(dArr3, 0, dArr4, 0, length);
        }
        double[] dArr5 = new double[dArr3.length];
        double[] dArr6 = new double[dArr3.length];
        double[] dArr7 = new double[dArr3.length];
        org.apache.commons.math.ode.sampling.NordsieckStepInterpolator nordsieckStepInterpolator = new org.apache.commons.math.ode.sampling.NordsieckStepInterpolator();
        nordsieckStepInterpolator.reinitialize(dArr4, z);
        java.util.Iterator<org.apache.commons.math.ode.sampling.StepHandler> it = this.stepHandlers.iterator();
        while (it.hasNext()) {
            it.next().reset();
        }
        setStateInitialized(false);
        start(d, dArr2, d2);
        nordsieckStepInterpolator.reinitialize(this.stepStart, this.stepSize, this.scaled, this.nordsieck);
        org.apache.commons.math.ode.sampling.NordsieckStepInterpolator nordsieckStepInterpolator2 = nordsieckStepInterpolator;
        nordsieckStepInterpolator2.storeTime(this.stepStart);
        double d4 = this.stepSize;
        nordsieckStepInterpolator2.rescale(d4);
        this.isLastStep = false;
        org.apache.commons.math.linear.Array2DRowRealMatrix array2DRowRealMatrix = null;
        while (true) {
            org.apache.commons.math.linear.Array2DRowRealMatrix array2DRowRealMatrix2 = array2DRowRealMatrix;
            double d5 = d4;
            double d6 = 10.0d;
            while (d6 >= 1.0d) {
                this.stepSize = d5;
                double d7 = this.stepStart + this.stepSize;
                nordsieckStepInterpolator2.setInterpolatedTime(d7);
                java.lang.System.arraycopy(nordsieckStepInterpolator2.getInterpolatedState(), 0, dArr6, 0, dArr3.length);
                computeDerivatives(d7, dArr6, dArr5);
                for (int i = 0; i < dArr3.length; i++) {
                    dArr7[i] = this.stepSize * dArr5[i];
                }
                array2DRowRealMatrix2 = updateHighOrderDerivativesPhase1(this.nordsieck);
                updateHighOrderDerivativesPhase2(this.scaled, dArr7, array2DRowRealMatrix2);
                d6 = array2DRowRealMatrix2.walkInOptimizedOrder(new org.apache.commons.math.ode.nonstiff.AdamsMoultonIntegrator.Corrector(dArr4, dArr7, dArr6));
                if (d6 >= 1.0d) {
                    double filterStep = filterStep(this.stepSize * computeStepGrowShrinkFactor(d6), z, false);
                    nordsieckStepInterpolator2.rescale(filterStep);
                    d5 = filterStep;
                }
            }
            double d8 = d6;
            double d9 = this.stepStart + this.stepSize;
            computeDerivatives(d9, dArr6, dArr5);
            double[] dArr8 = new double[dArr3.length];
            int i2 = 0;
            while (true) {
                d3 = d5;
                if (i2 >= dArr3.length) {
                    break;
                }
                dArr8[i2] = this.stepSize * dArr5[i2];
                i2++;
                d5 = d3;
            }
            updateHighOrderDerivativesPhase2(dArr7, dArr8, array2DRowRealMatrix2);
            java.lang.System.arraycopy(dArr6, 0, dArr4, 0, length);
            nordsieckStepInterpolator2.reinitialize(d9, this.stepSize, dArr8, array2DRowRealMatrix2);
            nordsieckStepInterpolator2.storeTime(this.stepStart);
            nordsieckStepInterpolator2.shift();
            nordsieckStepInterpolator2.storeTime(d9);
            org.apache.commons.math.ode.sampling.NordsieckStepInterpolator nordsieckStepInterpolator3 = nordsieckStepInterpolator2;
            int i3 = length;
            org.apache.commons.math.linear.Array2DRowRealMatrix array2DRowRealMatrix3 = array2DRowRealMatrix2;
            this.stepStart = acceptStep(nordsieckStepInterpolator2, dArr2, dArr5, d2);
            this.scaled = dArr8;
            this.nordsieck = array2DRowRealMatrix3;
            if (this.isLastStep) {
                d4 = d3;
            } else {
                nordsieckStepInterpolator3.storeTime(this.stepStart);
                if (this.resetOccurred) {
                    start(this.stepStart, dArr2, d2);
                    nordsieckStepInterpolator3.reinitialize(this.stepStart, this.stepSize, this.scaled, this.nordsieck);
                }
                double computeStepGrowShrinkFactor = this.stepSize * computeStepGrowShrinkFactor(d8);
                double d10 = this.stepStart + computeStepGrowShrinkFactor;
                d4 = filterStep(computeStepGrowShrinkFactor, z, !z ? d10 > d2 : d10 < d2);
                double d11 = this.stepStart + d4;
                if (!z ? d11 > d2 : d11 < d2) {
                    d4 = d2 - this.stepStart;
                }
                nordsieckStepInterpolator3.rescale(d4);
            }
            if (!this.isLastStep) {
                dArr3 = dArr;
                nordsieckStepInterpolator2 = nordsieckStepInterpolator3;
                array2DRowRealMatrix = array2DRowRealMatrix3;
                length = i3;
                dArr4 = dArr2;
            } else {
                double d12 = this.stepStart;
                this.stepStart = Double.NaN;
                this.stepSize = Double.NaN;
                return d12;
            }
        }
    }

    private class Corrector implements org.apache.commons.math.linear.RealMatrixPreservingVisitor {
        private final double[] after;
        private final double[] before;
        private final double[] previous;
        private final double[] scaled;

        public Corrector(double[] dArr, double[] dArr2, double[] dArr3) {
            this.previous = dArr;
            this.scaled = dArr2;
            this.after = dArr3;
            this.before = (double[]) dArr3.clone();
        }

        @Override // org.apache.commons.math.linear.RealMatrixPreservingVisitor
        public void start(int i, int i2, int i3, int i4, int i5, int i6) {
            java.util.Arrays.fill(this.after, 0.0d);
        }

        @Override // org.apache.commons.math.linear.RealMatrixPreservingVisitor
        public void visit(int i, int i2, double d) {
            if ((i & 1) == 0) {
                double[] dArr = this.after;
                dArr[i2] = dArr[i2] - d;
            } else {
                double[] dArr2 = this.after;
                dArr2[i2] = dArr2[i2] + d;
            }
        }

        @Override // org.apache.commons.math.linear.RealMatrixPreservingVisitor
        public double end() {
            double d;
            double d2 = 0.0d;
            for (int i = 0; i < this.after.length; i++) {
                double[] dArr = this.after;
                dArr[i] = dArr[i] + this.previous[i] + this.scaled[i];
                if (i < org.apache.commons.math.ode.nonstiff.AdamsMoultonIntegrator.this.mainSetDimension) {
                    double max = org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.abs(this.previous[i]), org.apache.commons.math.util.FastMath.abs(this.after[i]));
                    if (org.apache.commons.math.ode.nonstiff.AdamsMoultonIntegrator.this.vecAbsoluteTolerance == null) {
                        d = org.apache.commons.math.ode.nonstiff.AdamsMoultonIntegrator.this.scalAbsoluteTolerance + (org.apache.commons.math.ode.nonstiff.AdamsMoultonIntegrator.this.scalRelativeTolerance * max);
                    } else {
                        d = org.apache.commons.math.ode.nonstiff.AdamsMoultonIntegrator.this.vecAbsoluteTolerance[i] + (org.apache.commons.math.ode.nonstiff.AdamsMoultonIntegrator.this.vecRelativeTolerance[i] * max);
                    }
                    double d3 = (this.after[i] - this.before[i]) / d;
                    d2 += d3 * d3;
                }
            }
            return org.apache.commons.math.util.FastMath.sqrt(d2 / org.apache.commons.math.ode.nonstiff.AdamsMoultonIntegrator.this.mainSetDimension);
        }
    }
}
