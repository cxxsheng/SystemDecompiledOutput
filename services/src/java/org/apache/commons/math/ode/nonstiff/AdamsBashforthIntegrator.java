package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
public class AdamsBashforthIntegrator extends org.apache.commons.math.ode.nonstiff.AdamsIntegrator {
    private static final java.lang.String METHOD_NAME = "Adams-Bashforth";

    public AdamsBashforthIntegrator(int i, double d, double d2, double d3, double d4) throws java.lang.IllegalArgumentException {
        super(METHOD_NAME, i, i, d, d2, d3, d4);
    }

    public AdamsBashforthIntegrator(int i, double d, double d2, double[] dArr, double[] dArr2) throws java.lang.IllegalArgumentException {
        super(METHOD_NAME, i, i, d, d2, dArr, dArr2);
    }

    @Override // org.apache.commons.math.ode.nonstiff.AdamsIntegrator, org.apache.commons.math.ode.nonstiff.AdaptiveStepsizeIntegrator, org.apache.commons.math.ode.FirstOrderIntegrator
    public double integrate(org.apache.commons.math.ode.FirstOrderDifferentialEquations firstOrderDifferentialEquations, double d, double[] dArr, double d2, double[] dArr2) throws org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        org.apache.commons.math.ode.sampling.NordsieckStepInterpolator nordsieckStepInterpolator;
        double d3;
        org.apache.commons.math.ode.sampling.NordsieckStepInterpolator nordsieckStepInterpolator2;
        double d4;
        double d5;
        double[] dArr3 = dArr;
        int length = dArr3.length;
        sanityChecks(firstOrderDifferentialEquations, d, dArr, d2, dArr2);
        setEquations(firstOrderDifferentialEquations);
        resetEvaluations();
        boolean z = d2 > d;
        if (dArr2 != dArr3) {
            java.lang.System.arraycopy(dArr3, 0, dArr2, 0, length);
        }
        double[] dArr4 = new double[length];
        org.apache.commons.math.ode.sampling.NordsieckStepInterpolator nordsieckStepInterpolator3 = new org.apache.commons.math.ode.sampling.NordsieckStepInterpolator();
        nordsieckStepInterpolator3.reinitialize(dArr2, z);
        java.util.Iterator<org.apache.commons.math.ode.sampling.StepHandler> it = this.stepHandlers.iterator();
        while (it.hasNext()) {
            it.next().reset();
        }
        setStateInitialized(false);
        start(d, dArr2, d2);
        org.apache.commons.math.ode.sampling.NordsieckStepInterpolator nordsieckStepInterpolator4 = nordsieckStepInterpolator3;
        nordsieckStepInterpolator3.reinitialize(this.stepStart, this.stepSize, this.scaled, this.nordsieck);
        nordsieckStepInterpolator4.storeTime(this.stepStart);
        int rowDimension = this.nordsieck.getRowDimension() - 1;
        double d6 = this.stepSize;
        nordsieckStepInterpolator4.rescale(d6);
        this.isLastStep = false;
        while (true) {
            double d7 = d6;
            double d8 = 10.0d;
            double d9 = d7;
            while (d8 >= 1.0d) {
                this.stepSize = d9;
                double d10 = 0.0d;
                int i = 0;
                while (i < this.mainSetDimension) {
                    double abs = org.apache.commons.math.util.FastMath.abs(dArr2[i]);
                    if (this.vecAbsoluteTolerance == null) {
                        nordsieckStepInterpolator2 = nordsieckStepInterpolator4;
                        d4 = this.scalAbsoluteTolerance;
                        d5 = this.scalRelativeTolerance;
                    } else {
                        nordsieckStepInterpolator2 = nordsieckStepInterpolator4;
                        d4 = this.vecAbsoluteTolerance[i];
                        d5 = this.vecRelativeTolerance[i];
                    }
                    double entry = this.nordsieck.getEntry(rowDimension, i) / (d4 + (d5 * abs));
                    d10 += entry * entry;
                    i++;
                    nordsieckStepInterpolator4 = nordsieckStepInterpolator2;
                }
                org.apache.commons.math.ode.sampling.NordsieckStepInterpolator nordsieckStepInterpolator5 = nordsieckStepInterpolator4;
                d8 = org.apache.commons.math.util.FastMath.sqrt(d10 / this.mainSetDimension);
                if (d8 >= 1.0d) {
                    double filterStep = filterStep(this.stepSize * computeStepGrowShrinkFactor(d8), z, false);
                    nordsieckStepInterpolator5.rescale(filterStep);
                    d9 = filterStep;
                    nordsieckStepInterpolator4 = nordsieckStepInterpolator5;
                } else {
                    nordsieckStepInterpolator4 = nordsieckStepInterpolator5;
                }
            }
            org.apache.commons.math.ode.sampling.NordsieckStepInterpolator nordsieckStepInterpolator6 = nordsieckStepInterpolator4;
            double d11 = this.stepStart + this.stepSize;
            nordsieckStepInterpolator6.shift();
            nordsieckStepInterpolator6.setInterpolatedTime(d11);
            java.lang.System.arraycopy(nordsieckStepInterpolator6.getInterpolatedState(), 0, dArr2, 0, dArr3.length);
            computeDerivatives(d11, dArr2, dArr4);
            double[] dArr5 = new double[dArr3.length];
            int i2 = 0;
            while (i2 < dArr3.length) {
                dArr5[i2] = this.stepSize * dArr4[i2];
                i2++;
                d8 = d8;
            }
            double d12 = d8;
            org.apache.commons.math.linear.Array2DRowRealMatrix updateHighOrderDerivativesPhase1 = updateHighOrderDerivativesPhase1(this.nordsieck);
            updateHighOrderDerivativesPhase2(this.scaled, dArr5, updateHighOrderDerivativesPhase1);
            double d13 = d9;
            nordsieckStepInterpolator6.reinitialize(d11, this.stepSize, dArr5, updateHighOrderDerivativesPhase1);
            nordsieckStepInterpolator6.storeTime(d11);
            int i3 = rowDimension;
            this.stepStart = acceptStep(nordsieckStepInterpolator6, dArr2, dArr4, d2);
            this.scaled = dArr5;
            this.nordsieck = updateHighOrderDerivativesPhase1;
            nordsieckStepInterpolator6.reinitialize(d11, this.stepSize, this.scaled, this.nordsieck);
            if (this.isLastStep) {
                nordsieckStepInterpolator = nordsieckStepInterpolator6;
                d6 = d13;
            } else {
                nordsieckStepInterpolator = nordsieckStepInterpolator6;
                nordsieckStepInterpolator.storeTime(this.stepStart);
                if (this.resetOccurred) {
                    d3 = d12;
                    start(this.stepStart, dArr2, d2);
                    nordsieckStepInterpolator.reinitialize(this.stepStart, this.stepSize, this.scaled, this.nordsieck);
                } else {
                    d3 = d12;
                }
                double computeStepGrowShrinkFactor = this.stepSize * computeStepGrowShrinkFactor(d3);
                double d14 = this.stepStart + computeStepGrowShrinkFactor;
                d6 = filterStep(computeStepGrowShrinkFactor, z, !z ? d14 > d2 : d14 < d2);
                double d15 = this.stepStart + d6;
                if (!z ? d15 > d2 : d15 < d2) {
                    d6 = d2 - this.stepStart;
                }
                nordsieckStepInterpolator.rescale(d6);
            }
            if (!this.isLastStep) {
                nordsieckStepInterpolator4 = nordsieckStepInterpolator;
                rowDimension = i3;
                dArr3 = dArr;
            } else {
                double d16 = this.stepStart;
                resetInternalState();
                return d16;
            }
        }
    }
}
