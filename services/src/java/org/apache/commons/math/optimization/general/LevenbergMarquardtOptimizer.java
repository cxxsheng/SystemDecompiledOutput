package org.apache.commons.math.optimization.general;

/* loaded from: classes3.dex */
public class LevenbergMarquardtOptimizer extends org.apache.commons.math.optimization.general.AbstractLeastSquaresOptimizer {
    private double[] beta;
    private double costRelativeTolerance;
    private double[] diagR;
    private double initialStepBoundFactor;
    private double[] jacNorm;
    private double[] lmDir;
    private double lmPar;
    private double orthoTolerance;
    private double parRelativeTolerance;
    private int[] permutation;
    private double qrRankingThreshold;
    private int rank;
    private int solvedCols;

    public LevenbergMarquardtOptimizer() {
        setMaxIterations(1000);
        setConvergenceChecker(null);
        setInitialStepBoundFactor(100.0d);
        setCostRelativeTolerance(1.0E-10d);
        setParRelativeTolerance(1.0E-10d);
        setOrthoTolerance(1.0E-10d);
        setQRRankingThreshold(Double.MIN_NORMAL);
    }

    public void setInitialStepBoundFactor(double d) {
        this.initialStepBoundFactor = d;
    }

    public void setCostRelativeTolerance(double d) {
        this.costRelativeTolerance = d;
    }

    public void setParRelativeTolerance(double d) {
        this.parRelativeTolerance = d;
    }

    public void setOrthoTolerance(double d) {
        this.orthoTolerance = d;
    }

    public void setQRRankingThreshold(double d) {
        this.qrRankingThreshold = d;
    }

    /* JADX WARN: Code restructure failed: missing block: B:123:0x0349, code lost:
    
        return r16;
     */
    @Override // org.apache.commons.math.optimization.general.AbstractLeastSquaresOptimizer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected org.apache.commons.math.optimization.VectorialPointValuePair doOptimize() throws org.apache.commons.math.FunctionEvaluationException, org.apache.commons.math.optimization.OptimizationException, java.lang.IllegalArgumentException {
        double[] dArr;
        boolean z;
        double d;
        double d2;
        double d3;
        double d4;
        boolean z2;
        double d5;
        this.solvedCols = java.lang.Math.min(this.rows, this.cols);
        this.diagR = new double[this.cols];
        this.jacNorm = new double[this.cols];
        this.beta = new double[this.cols];
        this.permutation = new int[this.cols];
        this.lmDir = new double[this.cols];
        double[] dArr2 = new double[this.cols];
        double[] dArr3 = new double[this.cols];
        double[] dArr4 = new double[this.rows];
        double[] dArr5 = new double[this.rows];
        double[] dArr6 = new double[this.rows];
        double[] dArr7 = new double[this.cols];
        double[] dArr8 = new double[this.cols];
        double[] dArr9 = new double[this.cols];
        updateResidualsAndCost();
        this.lmPar = 0.0d;
        boolean z3 = true;
        org.apache.commons.math.optimization.VectorialPointValuePair vectorialPointValuePair = new org.apache.commons.math.optimization.VectorialPointValuePair(this.point, this.objective);
        double d6 = 0.0d;
        double d7 = 0.0d;
        loop0: while (true) {
            for (int i = 0; i < this.rows; i++) {
                dArr6[i] = this.wresiduals[i];
            }
            incrementIterationsCounter();
            updateJacobian();
            qrDecomposition();
            qTy(dArr6);
            int i2 = 0;
            while (i2 < this.solvedCols) {
                int i3 = this.permutation[i2];
                this.wjacobian[i2][i3] = this.diagR[i3];
                i2++;
                dArr4 = dArr4;
            }
            double[] dArr10 = dArr4;
            if (z3) {
                double d8 = 0.0d;
                for (int i4 = 0; i4 < this.cols; i4++) {
                    double d9 = this.jacNorm[i4];
                    if (d9 == 0.0d) {
                        d9 = 1.0d;
                    }
                    double d10 = this.point[i4] * d9;
                    d8 += d10 * d10;
                    dArr2[i4] = d9;
                }
                d7 = org.apache.commons.math.util.FastMath.sqrt(d8);
                d6 = this.initialStepBoundFactor;
                if (d7 != 0.0d) {
                    d6 *= d7;
                }
            }
            if (this.cost == 0.0d) {
                dArr = dArr5;
                z = z3;
                d = d6;
                d2 = 0.0d;
                d3 = 0.0d;
            } else {
                int i5 = 0;
                double d11 = 0.0d;
                while (i5 < this.solvedCols) {
                    int i6 = this.permutation[i5];
                    double[] dArr11 = dArr5;
                    double d12 = this.jacNorm[i6];
                    if (d12 == 0.0d) {
                        z2 = z3;
                        d5 = d6;
                    } else {
                        int i7 = 0;
                        double d13 = 0.0d;
                        while (i7 <= i5) {
                            d13 += this.wjacobian[i7][i6] * dArr6[i7];
                            i7++;
                            z3 = z3;
                        }
                        z2 = z3;
                        double abs = org.apache.commons.math.util.FastMath.abs(d13);
                        d5 = d6;
                        d11 = org.apache.commons.math.util.FastMath.max(d11, abs / (d12 * this.cost));
                    }
                    i5++;
                    dArr5 = dArr11;
                    d6 = d5;
                    z3 = z2;
                }
                dArr = dArr5;
                z = z3;
                d = d6;
                d2 = 0.0d;
                d3 = d11;
            }
            if (d3 <= this.orthoTolerance) {
                updateResidualsAndCost();
                return new org.apache.commons.math.optimization.VectorialPointValuePair(this.point, this.objective);
            }
            for (int i8 = 0; i8 < this.cols; i8++) {
                dArr2[i8] = org.apache.commons.math.util.FastMath.max(dArr2[i8], this.jacNorm[i8]);
            }
            double d14 = d2;
            dArr4 = dArr10;
            dArr5 = dArr;
            double d15 = d;
            double d16 = d7;
            org.apache.commons.math.optimization.VectorialPointValuePair vectorialPointValuePair2 = vectorialPointValuePair;
            while (d14 < 1.0E-4d) {
                for (int i9 = 0; i9 < this.solvedCols; i9++) {
                    int i10 = this.permutation[i9];
                    dArr3[i10] = this.point[i10];
                }
                double d17 = this.cost;
                double[] dArr12 = this.residuals;
                this.residuals = dArr4;
                double[] dArr13 = this.objective;
                this.objective = dArr5;
                double[] dArr14 = dArr6;
                double[] dArr15 = dArr7;
                double d18 = d15;
                determineLMParameter(dArr6, d15, dArr2, dArr7, dArr8, dArr9);
                double d19 = d2;
                for (int i11 = 0; i11 < this.solvedCols; i11++) {
                    int i12 = this.permutation[i11];
                    this.lmDir[i12] = -this.lmDir[i12];
                    this.point[i12] = dArr3[i12] + this.lmDir[i12];
                    double d20 = dArr2[i12] * this.lmDir[i12];
                    d19 += d20 * d20;
                }
                double sqrt = org.apache.commons.math.util.FastMath.sqrt(d19);
                if (!z) {
                    d15 = d18;
                } else {
                    d15 = org.apache.commons.math.util.FastMath.min(d18, sqrt);
                }
                updateResidualsAndCost();
                if (this.cost * 0.1d >= d17) {
                    d4 = -1.0d;
                } else {
                    double d21 = this.cost / d17;
                    d4 = 1.0d - (d21 * d21);
                }
                int i13 = 0;
                while (i13 < this.solvedCols) {
                    int i14 = this.permutation[i13];
                    double[] dArr16 = dArr8;
                    double d22 = this.lmDir[i14];
                    dArr15[i13] = d2;
                    int i15 = 0;
                    while (i15 <= i13) {
                        dArr15[i15] = dArr15[i15] + (this.wjacobian[i15][i14] * d22);
                        i15++;
                        dArr9 = dArr9;
                    }
                    i13++;
                    dArr8 = dArr16;
                }
                double[] dArr17 = dArr8;
                double[] dArr18 = dArr9;
                double d23 = d2;
                for (int i16 = 0; i16 < this.solvedCols; i16++) {
                    d23 += dArr15[i16] * dArr15[i16];
                }
                double d24 = d17 * d17;
                double d25 = d23 / d24;
                double[] dArr19 = dArr2;
                double[] dArr20 = dArr3;
                double d26 = ((this.lmPar * sqrt) * sqrt) / d24;
                double d27 = d25 + (d26 * 2.0d);
                double d28 = -(d25 + d26);
                double d29 = d27 == d2 ? d2 : d4 / d27;
                if (d29 <= 0.25d) {
                    double d30 = d4 < d2 ? (d28 * 0.5d) / (d28 + (0.5d * d4)) : 0.5d;
                    double d31 = (this.cost * 0.1d >= d17 || d30 < 0.1d) ? 0.1d : d30;
                    double min = org.apache.commons.math.util.FastMath.min(d15, sqrt * 10.0d) * d31;
                    this.lmPar /= d31;
                    d15 = min;
                } else if (this.lmPar == d2 || d29 >= 0.75d) {
                    this.lmPar *= 0.5d;
                    d15 = sqrt * 2.0d;
                }
                if (d29 >= 1.0E-4d) {
                    double d32 = d2;
                    for (int i17 = 0; i17 < this.cols; i17++) {
                        double d33 = dArr19[i17] * this.point[i17];
                        d32 += d33 * d33;
                    }
                    d16 = org.apache.commons.math.util.FastMath.sqrt(d32);
                    org.apache.commons.math.optimization.VectorialPointValuePair vectorialPointValuePair3 = new org.apache.commons.math.optimization.VectorialPointValuePair(this.point, this.objective);
                    if (this.checker != null && this.checker.converged(getIterations(), vectorialPointValuePair, vectorialPointValuePair3)) {
                        return vectorialPointValuePair3;
                    }
                    vectorialPointValuePair2 = vectorialPointValuePair3;
                    z = false;
                    dArr4 = dArr12;
                    dArr5 = dArr13;
                } else {
                    this.cost = d17;
                    for (int i18 = 0; i18 < this.solvedCols; i18++) {
                        int i19 = this.permutation[i18];
                        this.point[i19] = dArr20[i19];
                    }
                    dArr4 = this.residuals;
                    this.residuals = dArr12;
                    dArr5 = this.objective;
                    this.objective = dArr13;
                }
                if (this.checker != null || ((org.apache.commons.math.util.FastMath.abs(d4) > this.costRelativeTolerance || d27 > this.costRelativeTolerance || d29 > 2.0d) && d15 > this.parRelativeTolerance * d16)) {
                    if (org.apache.commons.math.util.FastMath.abs(d4) <= 2.2204E-16d && d27 <= 2.2204E-16d && d29 <= 2.0d) {
                        throw new org.apache.commons.math.optimization.OptimizationException(org.apache.commons.math.exception.util.LocalizedFormats.TOO_SMALL_COST_RELATIVE_TOLERANCE, java.lang.Double.valueOf(this.costRelativeTolerance));
                    }
                    if (d15 <= d16 * 2.2204E-16d) {
                        throw new org.apache.commons.math.optimization.OptimizationException(org.apache.commons.math.exception.util.LocalizedFormats.TOO_SMALL_PARAMETERS_RELATIVE_TOLERANCE, java.lang.Double.valueOf(this.parRelativeTolerance));
                    }
                    if (d3 <= 2.2204E-16d) {
                        throw new org.apache.commons.math.optimization.OptimizationException(org.apache.commons.math.exception.util.LocalizedFormats.TOO_SMALL_ORTHOGONALITY_TOLERANCE, java.lang.Double.valueOf(this.orthoTolerance));
                    }
                    d14 = d29;
                    dArr6 = dArr14;
                    dArr7 = dArr15;
                    dArr8 = dArr17;
                    dArr2 = dArr19;
                    dArr3 = dArr20;
                    dArr9 = dArr18;
                }
            }
            d6 = d15;
            vectorialPointValuePair = vectorialPointValuePair2;
            dArr6 = dArr6;
            dArr7 = dArr7;
            d7 = d16;
            z3 = z;
        }
    }

    private void determineLMParameter(double[] dArr, double d, double[] dArr2, double[] dArr3, double[] dArr4, double[] dArr5) {
        double d2;
        double d3;
        double d4;
        int i;
        int i2;
        double d5;
        double[] dArr6 = dArr;
        double d6 = d;
        for (int i3 = 0; i3 < this.rank; i3++) {
            this.lmDir[this.permutation[i3]] = dArr6[i3];
        }
        for (int i4 = this.rank; i4 < this.cols; i4++) {
            this.lmDir[this.permutation[i4]] = 0.0d;
        }
        for (int i5 = this.rank - 1; i5 >= 0; i5--) {
            int i6 = this.permutation[i5];
            double d7 = this.lmDir[i6] / this.diagR[i6];
            for (int i7 = 0; i7 < i5; i7++) {
                double[] dArr7 = this.lmDir;
                int i8 = this.permutation[i7];
                dArr7[i8] = dArr7[i8] - (this.wjacobian[i7][i6] * d7);
            }
            this.lmDir[i6] = d7;
        }
        double d8 = 0.0d;
        for (int i9 = 0; i9 < this.solvedCols; i9++) {
            int i10 = this.permutation[i9];
            double d9 = dArr2[i10] * this.lmDir[i10];
            dArr3[i10] = d9;
            d8 += d9 * d9;
        }
        double sqrt = org.apache.commons.math.util.FastMath.sqrt(d8);
        double d10 = sqrt - d6;
        double d11 = d6 * 0.1d;
        if (d10 <= d11) {
            this.lmPar = 0.0d;
            return;
        }
        if (this.rank != this.solvedCols) {
            d2 = d11;
            d3 = 0.0d;
        } else {
            for (int i11 = 0; i11 < this.solvedCols; i11++) {
                int i12 = this.permutation[i11];
                dArr3[i12] = dArr3[i12] * (dArr2[i12] / sqrt);
            }
            double d12 = 0.0d;
            int i13 = 0;
            while (true) {
                d2 = d11;
                if (i13 >= this.solvedCols) {
                    break;
                }
                int i14 = this.permutation[i13];
                double d13 = 0.0d;
                for (int i15 = 0; i15 < i13; i15++) {
                    d13 += this.wjacobian[i15][i14] * dArr3[this.permutation[i15]];
                }
                double d14 = (dArr3[i14] - d13) / this.diagR[i14];
                dArr3[i14] = d14;
                d12 += d14 * d14;
                i13++;
                d11 = d2;
            }
            d3 = d10 / (d12 * d6);
        }
        int i16 = 0;
        double d15 = 0.0d;
        while (i16 < this.solvedCols) {
            int i17 = this.permutation[i16];
            double d16 = d10;
            double d17 = 0.0d;
            for (int i18 = 0; i18 <= i16; i18++) {
                d17 += this.wjacobian[i18][i17] * dArr6[i18];
            }
            double d18 = d17 / dArr2[i17];
            d15 += d18 * d18;
            i16++;
            d10 = d16;
        }
        double d19 = d10;
        double sqrt2 = org.apache.commons.math.util.FastMath.sqrt(d15);
        double d20 = sqrt2 / d6;
        if (d20 != 0.0d) {
            d4 = d20;
        } else {
            d4 = 2.2251E-308d / org.apache.commons.math.util.FastMath.min(d6, 0.1d);
        }
        this.lmPar = org.apache.commons.math.util.FastMath.min(d4, org.apache.commons.math.util.FastMath.max(this.lmPar, d3));
        if (this.lmPar == 0.0d) {
            this.lmPar = sqrt2 / sqrt;
        }
        int i19 = 10;
        double d21 = d19;
        while (i19 >= 0) {
            if (this.lmPar == 0.0d) {
                i = i19;
                this.lmPar = org.apache.commons.math.util.FastMath.max(2.2251E-308d, 0.001d * d4);
            } else {
                i = i19;
            }
            double sqrt3 = org.apache.commons.math.util.FastMath.sqrt(this.lmPar);
            for (int i20 = 0; i20 < this.solvedCols; i20++) {
                int i21 = this.permutation[i20];
                dArr3[i21] = dArr2[i21] * sqrt3;
            }
            determineLMDirection(dArr6, dArr3, dArr4, dArr5);
            int i22 = 0;
            double d22 = 0.0d;
            while (true) {
                i2 = i;
                if (i22 >= this.solvedCols) {
                    break;
                }
                int i23 = this.permutation[i22];
                double d23 = dArr2[i23] * this.lmDir[i23];
                dArr5[i23] = d23;
                d22 += d23 * d23;
                i22++;
                i = i2;
                d4 = d4;
            }
            double d24 = d4;
            double sqrt4 = org.apache.commons.math.util.FastMath.sqrt(d22);
            double d25 = sqrt4 - d6;
            if (org.apache.commons.math.util.FastMath.abs(d25) <= d2) {
                return;
            }
            if (d3 == 0.0d && d25 <= d21 && d21 < 0.0d) {
                return;
            }
            for (int i24 = 0; i24 < this.solvedCols; i24++) {
                int i25 = this.permutation[i24];
                dArr3[i25] = (dArr5[i25] * dArr2[i25]) / sqrt4;
            }
            int i26 = 0;
            while (i26 < this.solvedCols) {
                int i27 = this.permutation[i26];
                dArr3[i27] = dArr3[i27] / dArr4[i26];
                double d26 = dArr3[i27];
                i26++;
                int i28 = i26;
                while (i28 < this.solvedCols) {
                    int i29 = this.permutation[i28];
                    dArr3[i29] = dArr3[i29] - (this.wjacobian[i28][i27] * d26);
                    i28++;
                    i26 = i26;
                }
            }
            double d27 = 0.0d;
            for (int i30 = 0; i30 < this.solvedCols; i30++) {
                double d28 = dArr3[this.permutation[i30]];
                d27 += d28 * d28;
            }
            double d29 = d25 / (d27 * d6);
            if (d25 > 0.0d) {
                d3 = org.apache.commons.math.util.FastMath.max(d3, this.lmPar);
                d5 = d24;
            } else if (d25 >= 0.0d) {
                d5 = d24;
            } else {
                d5 = org.apache.commons.math.util.FastMath.min(d24, this.lmPar);
            }
            this.lmPar = org.apache.commons.math.util.FastMath.max(d3, this.lmPar + d29);
            d4 = d5;
            i19 = i2 - 1;
            d21 = d25;
            dArr6 = dArr;
            d6 = d;
        }
    }

    private void determineLMDirection(double[] dArr, double[] dArr2, double[] dArr3, double[] dArr4) {
        double d;
        double d2;
        int i = 0;
        while (i < this.solvedCols) {
            int i2 = this.permutation[i];
            int i3 = i + 1;
            for (int i4 = i3; i4 < this.solvedCols; i4++) {
                this.wjacobian[i4][i2] = this.wjacobian[i][this.permutation[i4]];
            }
            this.lmDir[i] = this.diagR[i2];
            dArr4[i] = dArr[i];
            i = i3;
        }
        int i5 = 0;
        while (true) {
            double d3 = 0.0d;
            if (i5 >= this.solvedCols) {
                break;
            }
            double d4 = dArr2[this.permutation[i5]];
            if (d4 != 0.0d) {
                java.util.Arrays.fill(dArr3, i5 + 1, dArr3.length, 0.0d);
            }
            dArr3[i5] = d4;
            int i6 = i5;
            double d5 = 0.0d;
            while (i6 < this.solvedCols) {
                int i7 = this.permutation[i6];
                if (dArr3[i6] != d3) {
                    double d6 = this.wjacobian[i6][i7];
                    if (org.apache.commons.math.util.FastMath.abs(d6) < org.apache.commons.math.util.FastMath.abs(dArr3[i6])) {
                        double d7 = d6 / dArr3[i6];
                        d = 1.0d / org.apache.commons.math.util.FastMath.sqrt((d7 * d7) + 1.0d);
                        d2 = d7 * d;
                    } else {
                        double d8 = dArr3[i6] / d6;
                        double sqrt = 1.0d / org.apache.commons.math.util.FastMath.sqrt((d8 * d8) + 1.0d);
                        d = sqrt * d8;
                        d2 = sqrt;
                    }
                    this.wjacobian[i6][i7] = (d6 * d2) + (dArr3[i6] * d);
                    double d9 = (dArr4[i6] * d2) + (d * d5);
                    double d10 = -d;
                    d5 = (dArr4[i6] * d10) + (d5 * d2);
                    dArr4[i6] = d9;
                    for (int i8 = i6 + 1; i8 < this.solvedCols; i8++) {
                        double d11 = this.wjacobian[i8][i7];
                        double d12 = (d2 * d11) + (dArr3[i8] * d);
                        dArr3[i8] = (d11 * d10) + (dArr3[i8] * d2);
                        this.wjacobian[i8][i7] = d12;
                    }
                }
                i6++;
                d3 = 0.0d;
            }
            dArr3[i5] = this.wjacobian[i5][this.permutation[i5]];
            this.wjacobian[i5][this.permutation[i5]] = this.lmDir[i5];
            i5++;
        }
        int i9 = this.solvedCols;
        for (int i10 = 0; i10 < this.solvedCols; i10++) {
            if (dArr3[i10] == 0.0d && i9 == this.solvedCols) {
                i9 = i10;
            }
            if (i9 < this.solvedCols) {
                dArr4[i10] = 0.0d;
            }
        }
        if (i9 > 0) {
            for (int i11 = i9 - 1; i11 >= 0; i11--) {
                int i12 = this.permutation[i11];
                double d13 = 0.0d;
                for (int i13 = i11 + 1; i13 < i9; i13++) {
                    d13 += this.wjacobian[i13][i12] * dArr4[i13];
                }
                dArr4[i11] = (dArr4[i11] - d13) / dArr3[i11];
            }
        }
        for (int i14 = 0; i14 < this.lmDir.length; i14++) {
            this.lmDir[this.permutation[i14]] = dArr4[i14];
        }
    }

    private void qrDecomposition() throws org.apache.commons.math.optimization.OptimizationException {
        int i = 0;
        while (true) {
            double d = 0.0d;
            if (i >= this.cols) {
                break;
            }
            this.permutation[i] = i;
            for (int i2 = 0; i2 < this.wjacobian.length; i2++) {
                double d2 = this.wjacobian[i2][i];
                d += d2 * d2;
            }
            this.jacNorm[i] = org.apache.commons.math.util.FastMath.sqrt(d);
            i++;
        }
        for (int i3 = 0; i3 < this.cols; i3++) {
            int i4 = -1;
            double d3 = Double.NEGATIVE_INFINITY;
            for (int i5 = i3; i5 < this.cols; i5++) {
                double d4 = 0.0d;
                for (int i6 = i3; i6 < this.wjacobian.length; i6++) {
                    double d5 = this.wjacobian[i6][this.permutation[i5]];
                    d4 += d5 * d5;
                }
                if (!java.lang.Double.isInfinite(d4) && !java.lang.Double.isNaN(d4)) {
                    if (d4 > d3) {
                        i4 = i5;
                        d3 = d4;
                    }
                } else {
                    throw new org.apache.commons.math.optimization.OptimizationException(org.apache.commons.math.exception.util.LocalizedFormats.UNABLE_TO_PERFORM_QR_DECOMPOSITION_ON_JACOBIAN, java.lang.Integer.valueOf(this.rows), java.lang.Integer.valueOf(this.cols));
                }
            }
            if (d3 <= this.qrRankingThreshold) {
                this.rank = i3;
                return;
            }
            int i7 = this.permutation[i4];
            this.permutation[i4] = this.permutation[i3];
            this.permutation[i3] = i7;
            double d6 = this.wjacobian[i3][i7];
            double sqrt = org.apache.commons.math.util.FastMath.sqrt(d3);
            if (d6 > 0.0d) {
                sqrt = -sqrt;
            }
            double d7 = 1.0d / (d3 - (d6 * sqrt));
            this.beta[i7] = d7;
            this.diagR[i7] = sqrt;
            double[] dArr = this.wjacobian[i3];
            dArr[i7] = dArr[i7] - sqrt;
            for (int i8 = (this.cols - 1) - i3; i8 > 0; i8--) {
                double d8 = 0.0d;
                for (int i9 = i3; i9 < this.wjacobian.length; i9++) {
                    d8 += this.wjacobian[i9][i7] * this.wjacobian[i9][this.permutation[i3 + i8]];
                }
                double d9 = d8 * d7;
                for (int i10 = i3; i10 < this.wjacobian.length; i10++) {
                    double[] dArr2 = this.wjacobian[i10];
                    int i11 = this.permutation[i3 + i8];
                    dArr2[i11] = dArr2[i11] - (this.wjacobian[i10][i7] * d9);
                }
            }
        }
        this.rank = this.solvedCols;
    }

    private void qTy(double[] dArr) {
        for (int i = 0; i < this.cols; i++) {
            int i2 = this.permutation[i];
            double d = 0.0d;
            for (int i3 = i; i3 < this.rows; i3++) {
                d += this.wjacobian[i3][i2] * dArr[i3];
            }
            double d2 = d * this.beta[i2];
            for (int i4 = i; i4 < this.rows; i4++) {
                dArr[i4] = dArr[i4] - (this.wjacobian[i4][i2] * d2);
            }
        }
    }
}
