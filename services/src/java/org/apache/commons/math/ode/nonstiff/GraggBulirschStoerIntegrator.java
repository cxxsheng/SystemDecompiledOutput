package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
public class GraggBulirschStoerIntegrator extends org.apache.commons.math.ode.nonstiff.AdaptiveStepsizeIntegrator {
    private static final java.lang.String METHOD_NAME = "Gragg-Bulirsch-Stoer";
    private double[][] coeff;
    private int[] costPerStep;
    private double[] costPerTimeUnit;
    private int maxChecks;
    private int maxIter;
    private int maxOrder;
    private int mudif;
    private double[] optimalStep;
    private double orderControl1;
    private double orderControl2;
    private boolean performTest;
    private int[] sequence;
    private double stabilityReduction;
    private double stepControl1;
    private double stepControl2;
    private double stepControl3;
    private double stepControl4;
    private boolean useInterpolationError;

    public GraggBulirschStoerIntegrator(double d, double d2, double d3, double d4) {
        super(METHOD_NAME, d, d2, d3, d4);
        setStabilityCheck(true, -1, -1, -1.0d);
        setStepsizeControl(-1.0d, -1.0d, -1.0d, -1.0d);
        setOrderControl(-1, -1.0d, -1.0d);
        setInterpolationControl(true, -1);
    }

    public GraggBulirschStoerIntegrator(double d, double d2, double[] dArr, double[] dArr2) {
        super(METHOD_NAME, d, d2, dArr, dArr2);
        setStabilityCheck(true, -1, -1, -1.0d);
        setStepsizeControl(-1.0d, -1.0d, -1.0d, -1.0d);
        setOrderControl(-1, -1.0d, -1.0d);
        setInterpolationControl(true, -1);
    }

    public void setStabilityCheck(boolean z, int i, int i2, double d) {
        this.performTest = z;
        if (i <= 0) {
            i = 2;
        }
        this.maxIter = i;
        if (i2 <= 0) {
            i2 = 1;
        }
        this.maxChecks = i2;
        if (d < 1.0E-4d || d > 0.9999d) {
            this.stabilityReduction = 0.5d;
        } else {
            this.stabilityReduction = d;
        }
    }

    public void setStepsizeControl(double d, double d2, double d3, double d4) {
        if (d < 1.0E-4d || d > 0.9999d) {
            this.stepControl1 = 0.65d;
        } else {
            this.stepControl1 = d;
        }
        if (d2 < 1.0E-4d || d2 > 0.9999d) {
            this.stepControl2 = 0.94d;
        } else {
            this.stepControl2 = d2;
        }
        if (d3 < 1.0E-4d || d3 > 0.9999d) {
            this.stepControl3 = 0.02d;
        } else {
            this.stepControl3 = d3;
        }
        if (d4 < 1.0001d || d4 > 999.9d) {
            this.stepControl4 = 4.0d;
        } else {
            this.stepControl4 = d4;
        }
    }

    public void setOrderControl(int i, double d, double d2) {
        if (i <= 6 || i % 2 != 0) {
            this.maxOrder = 18;
        }
        if (d < 1.0E-4d || d > 0.9999d) {
            this.orderControl1 = 0.8d;
        } else {
            this.orderControl1 = d;
        }
        if (d2 < 1.0E-4d || d2 > 0.9999d) {
            this.orderControl2 = 0.9d;
        } else {
            this.orderControl2 = d2;
        }
        initializeArrays();
    }

    @Override // org.apache.commons.math.ode.AbstractIntegrator, org.apache.commons.math.ode.ODEIntegrator
    public void addStepHandler(org.apache.commons.math.ode.sampling.StepHandler stepHandler) {
        super.addStepHandler(stepHandler);
        initializeArrays();
    }

    @Override // org.apache.commons.math.ode.AbstractIntegrator, org.apache.commons.math.ode.ODEIntegrator
    public void addEventHandler(org.apache.commons.math.ode.events.EventHandler eventHandler, double d, double d2, int i) {
        super.addEventHandler(eventHandler, d, d2, i);
        initializeArrays();
    }

    private void initializeArrays() {
        int i = this.maxOrder / 2;
        if (this.sequence == null || this.sequence.length != i) {
            this.sequence = new int[i];
            this.costPerStep = new int[i];
            this.coeff = new double[i][];
            this.costPerTimeUnit = new double[i];
            this.optimalStep = new double[i];
        }
        if (requiresDenseOutput()) {
            for (int i2 = 0; i2 < i; i2++) {
                this.sequence[i2] = (i2 * 4) + 2;
            }
        } else {
            int i3 = 0;
            while (i3 < i) {
                int i4 = i3 + 1;
                this.sequence[i3] = i4 * 2;
                i3 = i4;
            }
        }
        this.costPerStep[0] = this.sequence[0] + 1;
        for (int i5 = 1; i5 < i; i5++) {
            this.costPerStep[i5] = this.costPerStep[i5 - 1] + this.sequence[i5];
        }
        int i6 = 0;
        while (i6 < i) {
            this.coeff[i6] = i6 > 0 ? new double[i6] : null;
            for (int i7 = 0; i7 < i6; i7++) {
                double d = this.sequence[i6] / this.sequence[(i6 - i7) - 1];
                this.coeff[i6][i7] = 1.0d / ((d * d) - 1.0d);
            }
            i6++;
        }
    }

    public void setInterpolationControl(boolean z, int i) {
        this.useInterpolationError = z;
        if (i <= 0 || i >= 7) {
            this.mudif = 4;
        } else {
            this.mudif = i;
        }
    }

    private void rescale(double[] dArr, double[] dArr2, double[] dArr3) {
        int i = 0;
        if (this.vecAbsoluteTolerance == null) {
            while (i < dArr3.length) {
                dArr3[i] = this.scalAbsoluteTolerance + (this.scalRelativeTolerance * org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.abs(dArr[i]), org.apache.commons.math.util.FastMath.abs(dArr2[i])));
                i++;
            }
            return;
        }
        while (i < dArr3.length) {
            dArr3[i] = this.vecAbsoluteTolerance[i] + (this.vecRelativeTolerance[i] * org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.abs(dArr[i]), org.apache.commons.math.util.FastMath.abs(dArr2[i])));
            i++;
        }
    }

    private boolean tryStep(double d, double[] dArr, double d2, int i, double[] dArr2, double[][] dArr3, double[] dArr4, double[] dArr5, double[] dArr6) throws org.apache.commons.math.ode.DerivativeException {
        double d3;
        org.apache.commons.math.ode.nonstiff.GraggBulirschStoerIntegrator graggBulirschStoerIntegrator = this;
        int i2 = i;
        double[] dArr7 = dArr2;
        int i3 = graggBulirschStoerIntegrator.sequence[i2];
        double d4 = d2 / i3;
        double d5 = 2.0d * d4;
        double d6 = d + d4;
        int i4 = 0;
        for (int i5 = 0; i5 < dArr.length; i5++) {
            dArr6[i5] = dArr[i5];
            dArr5[i5] = dArr[i5] + (dArr3[0][i5] * d4);
        }
        graggBulirschStoerIntegrator.computeDerivatives(d6, dArr5, dArr3[1]);
        int i6 = 1;
        while (i6 < i3) {
            if (i6 * 2 == i3) {
                java.lang.System.arraycopy(dArr5, i4, dArr4, i4, dArr.length);
            }
            d6 += d4;
            for (int i7 = i4; i7 < dArr.length; i7++) {
                double d7 = dArr5[i7];
                dArr5[i7] = dArr6[i7] + (dArr3[i6][i7] * d5);
                dArr6[i7] = d7;
            }
            int i8 = i6 + 1;
            graggBulirschStoerIntegrator.computeDerivatives(d6, dArr5, dArr3[i8]);
            if (!graggBulirschStoerIntegrator.performTest || i6 > graggBulirschStoerIntegrator.maxChecks || i2 >= graggBulirschStoerIntegrator.maxIter) {
                d3 = d5;
            } else {
                d3 = d5;
                double d8 = 0.0d;
                for (int i9 = 0; i9 < dArr7.length; i9++) {
                    double d9 = dArr3[0][i9] / dArr7[i9];
                    d8 += d9 * d9;
                }
                double d10 = 0.0d;
                for (int i10 = 0; i10 < dArr7.length; i10++) {
                    double d11 = (dArr3[i8][i10] - dArr3[0][i10]) / dArr7[i10];
                    d10 += d11 * d11;
                }
                if (d10 > org.apache.commons.math.util.FastMath.max(1.0E-15d, d8) * 4.0d) {
                    return false;
                }
            }
            i2 = i;
            dArr7 = dArr2;
            i6 = i8;
            d5 = d3;
            i4 = 0;
            graggBulirschStoerIntegrator = this;
        }
        while (i4 < dArr.length) {
            dArr5[i4] = (dArr6[i4] + dArr5[i4] + (dArr3[i3][i4] * d4)) * 0.5d;
            i4++;
        }
        return true;
    }

    private void extrapolate(int i, int i2, double[][] dArr, double[] dArr2) {
        int i3 = 1;
        while (true) {
            if (i3 >= i2) {
                break;
            }
            for (int i4 = 0; i4 < dArr2.length; i4++) {
                int i5 = i2 - i3;
                int i6 = i5 - 1;
                dArr[i6][i4] = dArr[i5][i4] + (this.coeff[i2 + i][i3 - 1] * (dArr[i5][i4] - dArr[i6][i4]));
            }
            i3++;
        }
        for (int i7 = 0; i7 < dArr2.length; i7++) {
            dArr2[i7] = dArr[0][i7] + (this.coeff[i2 + i][i2 - 1] * (dArr[0][i7] - dArr2[i7]));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:206:0x058c  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x06d2  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x06d6  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x06e9 A[LOOP:5: B:41:0x016b->B:220:0x06e9, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:221:0x06e3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:225:0x06dc  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x06be  */
    @Override // org.apache.commons.math.ode.nonstiff.AdaptiveStepsizeIntegrator, org.apache.commons.math.ode.FirstOrderIntegrator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public double integrate(org.apache.commons.math.ode.FirstOrderDifferentialEquations firstOrderDifferentialEquations, double d, double[] dArr, double d2, double[] dArr2) throws org.apache.commons.math.ode.DerivativeException, org.apache.commons.math.ode.IntegratorException {
        double[][] dArr3;
        double[] dArr4;
        double[][] dArr5;
        double[][] dArr6;
        double[] dArr7;
        double[] dArr8;
        org.apache.commons.math.ode.sampling.AbstractStepInterpolator dummyStepInterpolator;
        org.apache.commons.math.ode.sampling.AbstractStepInterpolator abstractStepInterpolator;
        double[] dArr9;
        double[] dArr10;
        boolean z;
        boolean z2;
        boolean z3;
        double[] dArr11;
        double d3;
        double[] dArr12;
        int i;
        double[] dArr13;
        boolean z4;
        org.apache.commons.math.ode.sampling.AbstractStepInterpolator abstractStepInterpolator2;
        double[] dArr14;
        double d4;
        boolean z5;
        double[] dArr15;
        double d5;
        boolean z6;
        int i2;
        int i3;
        double filterStep;
        double d6;
        int i4;
        double[][] dArr16;
        double[] dArr17;
        boolean z7;
        boolean z8;
        boolean z9;
        double[] dArr18 = dArr2;
        sanityChecks(firstOrderDifferentialEquations, d, dArr, d2, dArr2);
        setEquations(firstOrderDifferentialEquations);
        resetEvaluations();
        int i5 = 1;
        boolean z10 = d2 > d;
        double[] dArr19 = new double[dArr.length];
        double[] dArr20 = new double[dArr.length];
        double[] dArr21 = new double[dArr.length];
        double[] dArr22 = new double[dArr.length];
        double[][] dArr23 = new double[this.sequence.length - 1][];
        double[][] dArr24 = new double[this.sequence.length - 1][];
        for (int i6 = 0; i6 < this.sequence.length - 1; i6++) {
            dArr23[i6] = new double[dArr.length];
            dArr24[i6] = new double[dArr.length];
        }
        double[][][] dArr25 = new double[this.sequence.length][][];
        int i7 = 0;
        while (i7 < this.sequence.length) {
            dArr25[i7] = new double[this.sequence[i7] + i5][];
            dArr25[i7][0] = dArr19;
            int i8 = 0;
            while (i8 < this.sequence[i7]) {
                i8++;
                dArr25[i7][i8] = new double[dArr.length];
                dArr25 = dArr25;
            }
            i7++;
            i5 = 1;
        }
        double[][][] dArr26 = dArr25;
        if (dArr18 != dArr) {
            java.lang.System.arraycopy(dArr, 0, dArr18, 0, dArr.length);
        }
        double[] dArr27 = new double[dArr.length];
        boolean requiresDenseOutput = requiresDenseOutput();
        if (requiresDenseOutput) {
            int length = (this.sequence.length * 2) + 1;
            double[][] dArr28 = new double[length][];
            int i9 = 0;
            while (i9 < length) {
                dArr28[i9] = new double[dArr.length];
                i9++;
                length = length;
            }
            dArr3 = dArr28;
        } else {
            dArr3 = new double[][]{new double[dArr.length]};
        }
        double[] dArr29 = new double[this.mainSetDimension];
        rescale(dArr18, dArr18, dArr29);
        int max = org.apache.commons.math.util.FastMath.max(1, org.apache.commons.math.util.FastMath.min(this.sequence.length - 2, (int) org.apache.commons.math.util.FastMath.floor(0.5d - (org.apache.commons.math.util.FastMath.log10(org.apache.commons.math.util.FastMath.max(1.0E-10d, this.vecRelativeTolerance == null ? this.scalRelativeTolerance : this.vecRelativeTolerance[0])) * 0.6d))));
        if (requiresDenseOutput) {
            dArr6 = dArr24;
            dArr5 = dArr23;
            dArr7 = dArr22;
            dArr8 = dArr21;
            dArr4 = dArr20;
            dummyStepInterpolator = new org.apache.commons.math.ode.nonstiff.GraggBulirschStoerStepInterpolator(dArr2, dArr19, dArr20, dArr27, dArr3, z10);
        } else {
            dArr4 = dArr20;
            dArr5 = dArr23;
            dArr6 = dArr24;
            dArr7 = dArr22;
            dArr8 = dArr21;
            dummyStepInterpolator = new org.apache.commons.math.ode.sampling.DummyStepInterpolator(dArr18, dArr27, z10);
        }
        dummyStepInterpolator.storeTime(d);
        this.stepStart = d;
        java.util.Iterator<org.apache.commons.math.ode.sampling.StepHandler> it = this.stepHandlers.iterator();
        while (it.hasNext()) {
            it.next().reset();
        }
        setStateInitialized(false);
        this.costPerTimeUnit[0] = 0.0d;
        this.isLastStep = false;
        int i10 = max;
        boolean z11 = false;
        boolean z12 = true;
        boolean z13 = false;
        double d7 = Double.MAX_VALUE;
        double d8 = 0.0d;
        boolean z14 = true;
        while (true) {
            if (!z14) {
                abstractStepInterpolator = dummyStepInterpolator;
                dArr9 = dArr19;
                dArr10 = dArr27;
                z = true;
                z2 = z10;
                z3 = z14;
            } else {
                dummyStepInterpolator.shift();
                if (!z11) {
                    computeDerivatives(this.stepStart, dArr18, dArr19);
                }
                if (z12) {
                    z = true;
                    abstractStepInterpolator = dummyStepInterpolator;
                    dArr9 = dArr19;
                    dArr10 = dArr27;
                    z2 = z10;
                    d8 = initializeStep(firstOrderDifferentialEquations, z10, (i10 * 2) + 1, dArr29, this.stepStart, dArr2, dArr19, dArr8, dArr7);
                } else {
                    abstractStepInterpolator = dummyStepInterpolator;
                    dArr9 = dArr19;
                    dArr10 = dArr27;
                    z = true;
                    z2 = z10;
                }
                z3 = false;
            }
            this.stepSize = d8;
            if ((z2 && this.stepStart + this.stepSize > d2) || (!z2 && this.stepStart + this.stepSize < d2)) {
                this.stepSize = d2 - this.stepStart;
            }
            double d9 = this.stepStart + this.stepSize;
            this.isLastStep = (!z2 ? d9 <= d2 : d9 >= d2) ? false : z;
            int i11 = -1;
            double d10 = d7;
            int i12 = i10;
            boolean z15 = false;
            double d11 = d8;
            boolean z16 = z;
            while (z16) {
                int i13 = i11 + 1;
                double d12 = this.stepStart;
                double d13 = this.stepSize;
                double[][] dArr30 = dArr26[i13];
                double[] dArr31 = i13 == 0 ? dArr3[0] : dArr5[i13 - 1];
                if (i13 == 0) {
                    dArr16 = dArr6;
                    dArr17 = dArr4;
                } else {
                    dArr16 = dArr6;
                    dArr17 = dArr16[i13 - 1];
                }
                double[][] dArr32 = dArr5;
                double[][] dArr33 = dArr16;
                int i14 = i12;
                double[] dArr34 = dArr29;
                if (!tryStep(d12, dArr2, d13, i13, dArr29, dArr30, dArr31, dArr17, dArr8)) {
                    d11 = org.apache.commons.math.util.FastMath.abs(filterStep(this.stepSize * this.stabilityReduction, z2, false));
                    z16 = false;
                    dArr6 = dArr33;
                    i11 = i13;
                    dArr5 = dArr32;
                    i12 = i14;
                    dArr29 = dArr34;
                    z15 = true;
                    dArr18 = dArr2;
                } else {
                    char c = 0;
                    if (i13 <= 0) {
                        i11 = i13;
                        i12 = i14;
                        dArr18 = dArr2;
                        dArr29 = dArr34;
                        dArr6 = dArr33;
                        dArr5 = dArr32;
                    } else {
                        double[] dArr35 = dArr4;
                        extrapolate(0, i13, dArr33, dArr35);
                        i11 = i13;
                        rescale(dArr2, dArr35, dArr34);
                        int i15 = 0;
                        double d14 = 0.0d;
                        while (i15 < this.mainSetDimension) {
                            double abs = org.apache.commons.math.util.FastMath.abs(dArr35[i15] - dArr33[c][i15]) / dArr34[i15];
                            d14 += abs * abs;
                            i15++;
                            c = 0;
                        }
                        double sqrt = org.apache.commons.math.util.FastMath.sqrt(d14 / this.mainSetDimension);
                        if (sqrt > 1.0E15d || (i11 > 1 && sqrt > d10)) {
                            i12 = i14;
                            d11 = org.apache.commons.math.util.FastMath.abs(filterStep(this.stepSize * this.stabilityReduction, z2, false));
                            dArr4 = dArr35;
                            dArr18 = dArr2;
                            dArr29 = dArr34;
                            dArr6 = dArr33;
                            dArr5 = dArr32;
                            z16 = false;
                            z15 = true;
                        } else {
                            d10 = org.apache.commons.math.util.FastMath.max(4.0d * sqrt, 1.0d);
                            double d15 = 1.0d / ((i11 * 2) + 1);
                            double pow = this.stepControl2 / org.apache.commons.math.util.FastMath.pow(sqrt / this.stepControl1, d15);
                            double pow2 = org.apache.commons.math.util.FastMath.pow(this.stepControl3, d15);
                            this.optimalStep[i11] = org.apache.commons.math.util.FastMath.abs(filterStep(this.stepSize * org.apache.commons.math.util.FastMath.max(pow2 / this.stepControl4, org.apache.commons.math.util.FastMath.min(1.0d / pow2, pow)), z2, true));
                            this.costPerTimeUnit[i11] = this.costPerStep[i11] / this.optimalStep[i11];
                            i12 = i14;
                            switch (i11 - i12) {
                                case -1:
                                    if (i12 > 1 && !z13) {
                                        if (sqrt > 1.0d) {
                                            double d16 = (this.sequence[i12] * this.sequence[i12 + 1]) / (this.sequence[0] * this.sequence[0]);
                                            if (sqrt <= d16 * d16) {
                                                z7 = z15;
                                            } else {
                                                i12 = (i11 > 1 && this.costPerTimeUnit[i11 - 1] < this.orderControl1 * this.costPerTimeUnit[i11]) ? i11 - 1 : i11;
                                                d11 = this.optimalStep[i12];
                                                z7 = true;
                                                z16 = false;
                                            }
                                            z15 = z7;
                                            break;
                                        } else {
                                            z16 = false;
                                            break;
                                        }
                                    }
                                    break;
                                case 0:
                                    if (sqrt > 1.0d) {
                                        double d17 = this.sequence[i11 + 1] / this.sequence[0];
                                        if (sqrt <= d17 * d17) {
                                            z8 = z15;
                                        } else {
                                            if (i12 > 1 && this.costPerTimeUnit[i12 - 1] < this.orderControl1 * this.costPerTimeUnit[i12]) {
                                                i12--;
                                            }
                                            d11 = this.optimalStep[i12];
                                            z8 = true;
                                            z16 = false;
                                        }
                                        z15 = z8;
                                        break;
                                    } else {
                                        z16 = false;
                                        break;
                                    }
                                    break;
                                case 1:
                                    if (sqrt <= 1.0d) {
                                        z9 = z15;
                                    } else {
                                        if (i12 > 1 && this.costPerTimeUnit[i12 - 1] < this.orderControl1 * this.costPerTimeUnit[i12]) {
                                            i12--;
                                        }
                                        d11 = this.optimalStep[i12];
                                        z9 = true;
                                    }
                                    z15 = z9;
                                    z16 = false;
                                    break;
                                default:
                                    if ((z12 || this.isLastStep) && sqrt <= 1.0d) {
                                        z16 = false;
                                        break;
                                    }
                                    break;
                            }
                            dArr4 = dArr35;
                            dArr18 = dArr2;
                            dArr29 = dArr34;
                            dArr6 = dArr33;
                            dArr5 = dArr32;
                        }
                    }
                }
            }
            double[] dArr36 = dArr29;
            double[][] dArr37 = dArr5;
            double[][] dArr38 = dArr6;
            double[] dArr39 = dArr4;
            if (!z15) {
                dArr11 = dArr10;
                computeDerivatives(this.stepStart + this.stepSize, dArr39, dArr11);
            } else {
                dArr11 = dArr10;
            }
            double maxStep = getMaxStep();
            if (!requiresDenseOutput || z15) {
                d3 = maxStep;
                dArr12 = dArr36;
                i = i12;
                dArr13 = dArr11;
                dArr6 = dArr38;
                z4 = z2;
                abstractStepInterpolator2 = abstractStepInterpolator;
                dArr14 = dArr;
            } else {
                for (int i16 = 1; i16 <= i11; i16++) {
                    extrapolate(0, i16, dArr37, dArr3[0]);
                }
                double[][] dArr40 = dArr37;
                int i17 = ((i11 * 2) - this.mudif) + 3;
                int i18 = 0;
                while (i18 < i17) {
                    int i19 = i18 / 2;
                    double d18 = maxStep;
                    double[][] dArr41 = dArr38;
                    double d19 = i18;
                    double pow3 = org.apache.commons.math.util.FastMath.pow(this.sequence[i19] * 0.5d, d19);
                    double[][] dArr42 = dArr40;
                    boolean z17 = z2;
                    int length2 = dArr26[i19].length / 2;
                    int i20 = 0;
                    while (true) {
                        int i21 = i12;
                        double[] dArr43 = dArr11;
                        double[][] dArr44 = dArr42;
                        if (i20 < dArr.length) {
                            dArr3[i18 + 1][i20] = dArr26[i19][length2 + i18][i20] * pow3;
                            i20++;
                            dArr42 = dArr44;
                            dArr11 = dArr43;
                            i12 = i21;
                        } else {
                            int i22 = 1;
                            while (i22 <= i11 - i19) {
                                int i23 = i22 + i19;
                                int i24 = i11;
                                double pow4 = org.apache.commons.math.util.FastMath.pow(this.sequence[i23] * 0.5d, d19);
                                int length3 = dArr26[i23].length / 2;
                                double d20 = d19;
                                for (int i25 = 0; i25 < dArr.length; i25++) {
                                    dArr44[i22 - 1][i25] = dArr26[i23][length3 + i18][i25] * pow4;
                                }
                                extrapolate(i19, i22, dArr44, dArr3[i18 + 1]);
                                i22++;
                                i11 = i24;
                                d19 = d20;
                            }
                            int i26 = i11;
                            for (int i27 = 0; i27 < dArr.length; i27++) {
                                double[] dArr45 = dArr3[i18 + 1];
                                dArr45[i27] = dArr45[i27] * this.stepSize;
                            }
                            i18++;
                            int i28 = i18 / 2;
                            while (true) {
                                i11 = i26;
                                if (i28 <= i11) {
                                    for (int length4 = dArr26[i28].length - 1; length4 >= i18 * 2; length4--) {
                                        for (int i29 = 0; i29 < dArr.length; i29++) {
                                            double[] dArr46 = dArr26[i28][length4];
                                            dArr46[i29] = dArr46[i29] - dArr26[i28][length4 - 2][i29];
                                        }
                                    }
                                    i28++;
                                    i26 = i11;
                                }
                            }
                            maxStep = d18;
                            dArr40 = dArr44;
                            z2 = z17;
                            dArr38 = dArr41;
                            dArr11 = dArr43;
                            i12 = i21;
                        }
                    }
                }
                d3 = maxStep;
                double[][] dArr47 = dArr40;
                i = i12;
                dArr13 = dArr11;
                dArr6 = dArr38;
                z4 = z2;
                dArr14 = dArr;
                if (i17 < 0) {
                    dArr37 = dArr47;
                    dArr12 = dArr36;
                    abstractStepInterpolator2 = abstractStepInterpolator;
                } else {
                    abstractStepInterpolator2 = abstractStepInterpolator;
                    org.apache.commons.math.ode.nonstiff.GraggBulirschStoerStepInterpolator graggBulirschStoerStepInterpolator = (org.apache.commons.math.ode.nonstiff.GraggBulirschStoerStepInterpolator) abstractStepInterpolator2;
                    graggBulirschStoerStepInterpolator.computeCoefficients(i17, this.stepSize);
                    if (!this.useInterpolationError) {
                        dArr37 = dArr47;
                        dArr12 = dArr36;
                    } else {
                        double estimateError = graggBulirschStoerStepInterpolator.estimateError(dArr36);
                        dArr37 = dArr47;
                        dArr12 = dArr36;
                        double abs2 = org.apache.commons.math.util.FastMath.abs(this.stepSize / org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.pow(estimateError, 1.0d / (i17 + 4)), 0.01d));
                        if (estimateError <= 10.0d) {
                            d4 = abs2;
                        } else {
                            d4 = abs2;
                            d11 = d4;
                            z15 = true;
                        }
                        if (z15) {
                            abstractStepInterpolator2.storeTime(this.stepStart + this.stepSize);
                            this.stepStart = acceptStep(abstractStepInterpolator2, dArr39, dArr13, d2);
                            abstractStepInterpolator2.storeTime(this.stepStart);
                            double[][] dArr48 = dArr37;
                            java.lang.System.arraycopy(dArr39, 0, dArr2, 0, dArr14.length);
                            double[] dArr49 = dArr9;
                            dArr15 = dArr13;
                            java.lang.System.arraycopy(dArr15, 0, dArr49, 0, dArr14.length);
                            int i30 = 1;
                            if (i11 == 1) {
                                if (!z13) {
                                    dArr5 = dArr48;
                                    dArr9 = dArr49;
                                    i2 = i;
                                    i30 = 2;
                                } else {
                                    dArr5 = dArr48;
                                    dArr9 = dArr49;
                                    i2 = i;
                                }
                            } else {
                                i2 = i;
                                if (i11 <= i2) {
                                    int i31 = i11 - 1;
                                    if (this.costPerTimeUnit[i31] >= this.orderControl1 * this.costPerTimeUnit[i11]) {
                                        dArr5 = dArr48;
                                        dArr9 = dArr49;
                                        i30 = this.costPerTimeUnit[i11] < this.orderControl2 * this.costPerTimeUnit[i31] ? org.apache.commons.math.util.FastMath.min(i11 + 1, this.sequence.length - 2) : i11;
                                    } else {
                                        dArr5 = dArr48;
                                        dArr9 = dArr49;
                                        i30 = i31;
                                    }
                                } else {
                                    dArr5 = dArr48;
                                    dArr9 = dArr49;
                                    i30 = i11 - 1;
                                    if (i11 > 2) {
                                        int i32 = i11 - 2;
                                        if (this.costPerTimeUnit[i32] < this.orderControl1 * this.costPerTimeUnit[i30]) {
                                            i30 = i32;
                                        }
                                    }
                                    if (this.costPerTimeUnit[i11] < this.orderControl2 * this.costPerTimeUnit[i30]) {
                                        i30 = org.apache.commons.math.util.FastMath.min(i11, this.sequence.length - 2);
                                    }
                                }
                            }
                            if (z13) {
                                i4 = org.apache.commons.math.util.FastMath.min(i30, i11);
                                d6 = org.apache.commons.math.util.FastMath.min(org.apache.commons.math.util.FastMath.abs(this.stepSize), this.optimalStep[i4]);
                                z5 = z4;
                            } else {
                                if (i30 <= i11) {
                                    filterStep = this.optimalStep[i30];
                                    i3 = i30;
                                    z5 = z4;
                                } else if (i11 < i2 && this.costPerTimeUnit[i11] < this.orderControl2 * this.costPerTimeUnit[i11 - 1]) {
                                    z5 = z4;
                                    filterStep = filterStep((this.optimalStep[i11] * this.costPerStep[i30 + 1]) / this.costPerStep[i11], z5, false);
                                    i3 = i30;
                                } else {
                                    z5 = z4;
                                    i3 = i30;
                                    filterStep = filterStep((this.optimalStep[i11] * this.costPerStep[i30]) / this.costPerStep[i11], z5, false);
                                }
                                d6 = filterStep;
                                i4 = i3;
                            }
                            i10 = i4;
                            d5 = d6;
                            z14 = true;
                            z11 = true;
                        } else {
                            z5 = z4;
                            dArr15 = dArr13;
                            dArr5 = dArr37;
                            i10 = i;
                            d5 = d11;
                            z14 = z3;
                        }
                        d8 = org.apache.commons.math.util.FastMath.min(d5, d4);
                        if (!z5) {
                            d8 = -d8;
                        }
                        if (!z15) {
                            z6 = false;
                            this.isLastStep = false;
                            z13 = true;
                        } else {
                            z6 = false;
                            z13 = false;
                        }
                        if (this.isLastStep) {
                            dArr18 = dArr2;
                            z12 = z6;
                            dArr4 = dArr39;
                            dummyStepInterpolator = abstractStepInterpolator2;
                            z10 = z5;
                            dArr19 = dArr9;
                            d7 = d10;
                            dArr29 = dArr12;
                            dArr27 = dArr15;
                        } else {
                            double d21 = this.stepStart;
                            resetInternalState();
                            return d21;
                        }
                    }
                }
            }
            d4 = d3;
            if (z15) {
            }
            d8 = org.apache.commons.math.util.FastMath.min(d5, d4);
            if (!z5) {
            }
            if (!z15) {
            }
            if (this.isLastStep) {
            }
        }
    }
}
