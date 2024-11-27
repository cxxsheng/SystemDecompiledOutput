package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
public abstract class EmbeddedRungeKuttaIntegrator extends org.apache.commons.math.ode.nonstiff.AdaptiveStepsizeIntegrator {
    private final double[][] a;
    private final double[] b;
    private final double[] c;
    private final double exp;
    private final boolean fsal;
    private double maxGrowth;
    private double minReduction;
    private final org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator prototype;
    private double safety;

    protected abstract double estimateError(double[][] dArr, double[] dArr2, double[] dArr3, double d);

    public abstract int getOrder();

    protected EmbeddedRungeKuttaIntegrator(java.lang.String str, boolean z, double[] dArr, double[][] dArr2, double[] dArr3, org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator rungeKuttaStepInterpolator, double d, double d2, double d3, double d4) {
        super(str, d, d2, d3, d4);
        this.fsal = z;
        this.c = dArr;
        this.a = dArr2;
        this.b = dArr3;
        this.prototype = rungeKuttaStepInterpolator;
        this.exp = (-1.0d) / getOrder();
        setSafety(0.9d);
        setMinReduction(0.2d);
        setMaxGrowth(10.0d);
    }

    protected EmbeddedRungeKuttaIntegrator(java.lang.String str, boolean z, double[] dArr, double[][] dArr2, double[] dArr3, org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator rungeKuttaStepInterpolator, double d, double d2, double[] dArr4, double[] dArr5) {
        super(str, d, d2, dArr4, dArr5);
        this.fsal = z;
        this.c = dArr;
        this.a = dArr2;
        this.b = dArr3;
        this.prototype = rungeKuttaStepInterpolator;
        this.exp = (-1.0d) / getOrder();
        setSafety(0.9d);
        setMinReduction(0.2d);
        setMaxGrowth(10.0d);
    }

    public double getSafety() {
        return this.safety;
    }

    public void setSafety(double d) {
        this.safety = d;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v35 */
    /* JADX WARN: Type inference failed for: r0v36, types: [int] */
    /* JADX WARN: Type inference failed for: r0v51 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11, types: [int] */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r2v36 */
    /* JADX WARN: Type inference failed for: r2v37, types: [int] */
    /* JADX WARN: Type inference failed for: r2v49, types: [org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator] */
    /* JADX WARN: Type inference failed for: r2v51 */
    @Override // org.apache.commons.math.ode.nonstiff.AdaptiveStepsizeIntegrator, org.apache.commons.math.ode.FirstOrderIntegrator
    public double integrate(org.apache.commons.math.ode.FirstOrderDifferentialEquations firstOrderDifferentialEquations, double d, double[] dArr, double d2, double[] dArr2) throws org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        org.apache.commons.math.ode.sampling.DummyStepInterpolator dummyStepInterpolator;
        org.apache.commons.math.ode.sampling.DummyStepInterpolator dummyStepInterpolator2;
        double[] dArr3;
        org.apache.commons.math.ode.sampling.DummyStepInterpolator dummyStepInterpolator3;
        double[][] dArr4;
        int i;
        boolean z;
        boolean z2;
        double[] dArr5;
        double d3;
        double[] dArr6 = dArr2;
        sanityChecks(firstOrderDifferentialEquations, d, dArr, d2, dArr2);
        setEquations(firstOrderDifferentialEquations);
        resetEvaluations();
        boolean z3 = true;
        int i2 = 0;
        boolean z4 = d2 > d;
        int length = this.c.length + 1;
        if (dArr6 != dArr) {
            java.lang.System.arraycopy(dArr, 0, dArr6, 0, dArr.length);
        }
        double[][] dArr7 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, dArr.length);
        double[] dArr8 = new double[dArr.length];
        double[] dArr9 = new double[dArr.length];
        if (requiresDenseOutput()) {
            ?? r2 = (org.apache.commons.math.ode.nonstiff.RungeKuttaStepInterpolator) this.prototype.copy();
            r2.reinitialize(this, dArr8, dArr7, z4);
            dummyStepInterpolator = r2;
        } else {
            dummyStepInterpolator = new org.apache.commons.math.ode.sampling.DummyStepInterpolator(dArr8, dArr7[length - 1], z4);
        }
        dummyStepInterpolator.storeTime(d);
        this.stepStart = d;
        java.util.Iterator<org.apache.commons.math.ode.sampling.StepHandler> it = this.stepHandlers.iterator();
        while (it.hasNext()) {
            it.next().reset();
        }
        setStateInitialized(false);
        this.isLastStep = false;
        double d4 = 0.0d;
        boolean z5 = true;
        while (true) {
            dummyStepInterpolator.shift();
            double d5 = 10.0d;
            double d6 = d4;
            boolean z6 = z5;
            while (d5 >= 1.0d) {
                if (z6 || !this.fsal) {
                    computeDerivatives(this.stepStart, dArr6, dArr7[i2]);
                }
                if (!z6) {
                    dArr3 = dArr9;
                    dummyStepInterpolator3 = dummyStepInterpolator;
                    dArr4 = dArr7;
                    i = length;
                    z = z4;
                    z2 = z3;
                    dArr5 = dArr8;
                    d3 = d6;
                } else {
                    int i3 = this.mainSetDimension;
                    double[] dArr10 = new double[i3];
                    if (this.vecAbsoluteTolerance == null) {
                        for (int i4 = i2; i4 < i3; i4++) {
                            dArr10[i4] = this.scalAbsoluteTolerance + (this.scalRelativeTolerance * org.apache.commons.math.util.FastMath.abs(dArr6[i4]));
                        }
                    } else {
                        for (int i5 = 0; i5 < i3; i5++) {
                            dArr10[i5] = this.vecAbsoluteTolerance[i5] + (this.vecRelativeTolerance[i5] * org.apache.commons.math.util.FastMath.abs(dArr6[i5]));
                        }
                    }
                    z2 = true;
                    dArr3 = dArr9;
                    dummyStepInterpolator3 = dummyStepInterpolator;
                    dArr5 = dArr8;
                    dArr4 = dArr7;
                    i = length;
                    z = z4;
                    d3 = initializeStep(firstOrderDifferentialEquations, z4, getOrder(), dArr10, this.stepStart, dArr2, dArr7[0], dArr5, dArr7[1]);
                    z6 = false;
                }
                this.stepSize = d3;
                for (?? r0 = z2; r0 < i; r0++) {
                    for (int i6 = 0; i6 < dArr.length; i6++) {
                        int i7 = r0 - 1;
                        double d7 = this.a[i7][0] * dArr4[0][i6];
                        for (?? r22 = z2; r22 < r0; r22++) {
                            d7 += this.a[i7][r22] * dArr4[r22][i6];
                        }
                        dArr5[i6] = dArr2[i6] + (this.stepSize * d7);
                    }
                    computeDerivatives(this.stepStart + (this.c[r0 - 1] * this.stepSize), dArr5, dArr4[r0]);
                }
                for (int i8 = 0; i8 < dArr.length; i8++) {
                    double d8 = this.b[0] * dArr4[0][i8];
                    for (?? r1 = z2; r1 < i; r1++) {
                        d8 += this.b[r1] * dArr4[r1][i8];
                    }
                    dArr5[i8] = dArr2[i8] + (this.stepSize * d8);
                }
                d5 = estimateError(dArr4, dArr2, dArr5, this.stepSize);
                if (d5 >= 1.0d) {
                    boolean z7 = z;
                    d6 = filterStep(this.stepSize * org.apache.commons.math.util.FastMath.min(this.maxGrowth, org.apache.commons.math.util.FastMath.max(this.minReduction, this.safety * org.apache.commons.math.util.FastMath.pow(d5, this.exp))), z7, false);
                    z4 = z7;
                    length = i;
                    dArr8 = dArr5;
                    dArr7 = dArr4;
                    z3 = z2;
                    dArr9 = dArr3;
                    dummyStepInterpolator = dummyStepInterpolator3;
                    i2 = 0;
                    dArr6 = dArr2;
                } else {
                    d6 = d3;
                    z4 = z;
                    length = i;
                    dArr8 = dArr5;
                    dArr7 = dArr4;
                    z3 = z2;
                    dArr9 = dArr3;
                    dummyStepInterpolator = dummyStepInterpolator3;
                    i2 = 0;
                    dArr6 = dArr2;
                }
            }
            double[] dArr11 = dArr9;
            org.apache.commons.math.ode.sampling.DummyStepInterpolator dummyStepInterpolator4 = dummyStepInterpolator;
            double[][] dArr12 = dArr7;
            int i9 = length;
            boolean z8 = z4;
            boolean z9 = z3;
            double[] dArr13 = dArr8;
            dummyStepInterpolator4.storeTime(this.stepStart + this.stepSize);
            java.lang.System.arraycopy(dArr13, 0, dArr2, 0, dArr.length);
            java.lang.System.arraycopy(dArr12[i9 - 1], 0, dArr11, 0, dArr.length);
            double d9 = d5;
            this.stepStart = acceptStep(dummyStepInterpolator4, dArr2, dArr11, d2);
            if (this.isLastStep) {
                dummyStepInterpolator2 = dummyStepInterpolator4;
                d4 = d6;
            } else {
                dummyStepInterpolator4.storeTime(this.stepStart);
                if (this.fsal) {
                    java.lang.System.arraycopy(dArr11, 0, dArr12[0], 0, dArr.length);
                }
                dummyStepInterpolator2 = dummyStepInterpolator4;
                double min = this.stepSize * org.apache.commons.math.util.FastMath.min(this.maxGrowth, org.apache.commons.math.util.FastMath.max(this.minReduction, this.safety * org.apache.commons.math.util.FastMath.pow(d9, this.exp)));
                double d10 = this.stepStart + min;
                d4 = filterStep(min, z8, (!z8 ? d10 <= d2 : d10 >= d2) ? false : z9);
                double d11 = this.stepStart + d4;
                if ((!z8 ? d11 <= d2 : d11 >= d2) ? false : z9) {
                    d4 = d2 - this.stepStart;
                }
            }
            if (!this.isLastStep) {
                dArr6 = dArr2;
                dArr9 = dArr11;
                length = i9;
                z5 = z6;
                z3 = z9;
                dummyStepInterpolator = dummyStepInterpolator2;
                dArr8 = dArr13;
                z4 = z8;
                dArr7 = dArr12;
                i2 = 0;
            } else {
                double d12 = this.stepStart;
                resetInternalState();
                return d12;
            }
        }
    }

    public double getMinReduction() {
        return this.minReduction;
    }

    public void setMinReduction(double d) {
        this.minReduction = d;
    }

    public double getMaxGrowth() {
        return this.maxGrowth;
    }

    public void setMaxGrowth(double d) {
        this.maxGrowth = d;
    }
}
