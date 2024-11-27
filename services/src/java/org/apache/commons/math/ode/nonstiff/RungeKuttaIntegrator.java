package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
public abstract class RungeKuttaIntegrator extends org.apache.commons.math.ode.AbstractIntegrator {
    private final double[][] a;
    private final double[] b;
    private final double[] c;
    private final org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator prototype;
    private final double step;

    protected RungeKuttaIntegrator(java.lang.String str, double[] dArr, double[][] dArr2, double[] dArr3, org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator rungeKuttaStepInterpolator, double d) {
        super(str);
        this.c = dArr;
        this.a = dArr2;
        this.b = dArr3;
        this.prototype = rungeKuttaStepInterpolator;
        this.step = org.apache.commons.math.util.FastMath.abs(d);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.ode.FirstOrderIntegrator
    public double integrate(org.apache.commons.math.ode.FirstOrderDifferentialEquations firstOrderDifferentialEquations, double d, double[] dArr, double d2, double[] dArr2) throws org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        org.apache.commons.math.ode.sampling.DummyStepInterpolator dummyStepInterpolator;
        sanityChecks(firstOrderDifferentialEquations, d, dArr, d2, dArr2);
        setEquations(firstOrderDifferentialEquations);
        resetEvaluations();
        int i = 1;
        int i2 = 0;
        boolean z = d2 > d;
        int length = this.c.length + 1;
        if (dArr2 != dArr) {
            java.lang.System.arraycopy(dArr, 0, dArr2, 0, dArr.length);
        }
        double[][] dArr3 = new double[length][];
        for (int i3 = 0; i3 < length; i3++) {
            dArr3[i3] = new double[dArr.length];
        }
        double[] dArr4 = new double[dArr.length];
        double[] dArr5 = new double[dArr.length];
        if (requiresDenseOutput()) {
            org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator rungeKuttaStepInterpolator = (org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator) this.prototype.copy();
            rungeKuttaStepInterpolator.reinitialize(this, dArr4, dArr3, z);
            dummyStepInterpolator = rungeKuttaStepInterpolator;
        } else {
            dummyStepInterpolator = new org.apache.commons.math.ode.sampling.DummyStepInterpolator(dArr4, dArr3[length - 1], z);
        }
        dummyStepInterpolator.storeTime(d);
        this.stepStart = d;
        double d3 = this.step;
        if (!z) {
            d3 = -d3;
        }
        this.stepSize = d3;
        java.util.Iterator<org.apache.commons.math.ode.sampling.StepHandler> it = this.stepHandlers.iterator();
        while (it.hasNext()) {
            it.next().reset();
        }
        setStateInitialized(false);
        this.isLastStep = false;
        while (true) {
            dummyStepInterpolator.shift();
            computeDerivatives(this.stepStart, dArr2, dArr3[i2]);
            int i4 = i;
            while (i4 < length) {
                int i5 = i2;
                while (i5 < dArr.length) {
                    int i6 = i4 - 1;
                    double d4 = this.a[i6][i2] * dArr3[i2][i5];
                    for (int i7 = i; i7 < i4; i7++) {
                        d4 += this.a[i6][i7] * dArr3[i7][i5];
                    }
                    dArr4[i5] = dArr2[i5] + (this.stepSize * d4);
                    i5++;
                    i = 1;
                    i2 = 0;
                }
                computeDerivatives(this.stepStart + (this.c[i4 - 1] * this.stepSize), dArr4, dArr3[i4]);
                i4++;
                dummyStepInterpolator = dummyStepInterpolator;
                i = 1;
                i2 = 0;
            }
            org.apache.commons.math.ode.sampling.DummyStepInterpolator dummyStepInterpolator2 = dummyStepInterpolator;
            for (int i8 = 0; i8 < dArr.length; i8++) {
                double d5 = this.b[0] * dArr3[0][i8];
                for (int i9 = 1; i9 < length; i9++) {
                    d5 += this.b[i9] * dArr3[i9][i8];
                }
                dArr4[i8] = dArr2[i8] + (this.stepSize * d5);
            }
            dummyStepInterpolator2.storeTime(this.stepStart + this.stepSize);
            java.lang.System.arraycopy(dArr4, 0, dArr2, 0, dArr.length);
            java.lang.System.arraycopy(dArr3[length - 1], 0, dArr5, 0, dArr.length);
            this.stepStart = acceptStep(dummyStepInterpolator2, dArr2, dArr5, d2);
            if (!this.isLastStep) {
                dummyStepInterpolator2.storeTime(this.stepStart);
                double d6 = this.stepStart + this.stepSize;
                if (!z ? d6 > d2 : d6 < d2) {
                    this.stepSize = d2 - this.stepStart;
                }
            }
            if (!this.isLastStep) {
                dummyStepInterpolator = dummyStepInterpolator2;
                i2 = 0;
                i = 1;
            } else {
                double d7 = this.stepStart;
                this.stepStart = Double.NaN;
                this.stepSize = Double.NaN;
                return d7;
            }
        }
    }
}
