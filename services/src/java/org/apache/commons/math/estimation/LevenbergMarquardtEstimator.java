package org.apache.commons.math.estimation;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class LevenbergMarquardtEstimator extends org.apache.commons.math.estimation.AbstractEstimator implements java.io.Serializable {
    private static final long serialVersionUID = -5705952631533171019L;
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
    private int rank;
    private int solvedCols;

    public LevenbergMarquardtEstimator() {
        setMaxCostEval(1000);
        setInitialStepBoundFactor(100.0d);
        setCostRelativeTolerance(1.0E-10d);
        setParRelativeTolerance(1.0E-10d);
        setOrthoTolerance(1.0E-10d);
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

    @Override // org.apache.commons.math.estimation.AbstractEstimator, org.apache.commons.math.estimation.Estimator
    public void estimate(org.apache.commons.math.estimation.EstimationProblem estimationProblem) throws org.apache.commons.math.estimation.EstimationException {
        boolean z;
        double d;
        double d2;
        double d3;
        double d4;
        boolean z2;
        double d5;
        initializeEstimate(estimationProblem);
        this.solvedCols = org.apache.commons.math.util.FastMath.min(this.rows, this.cols);
        this.diagR = new double[this.cols];
        this.jacNorm = new double[this.cols];
        this.beta = new double[this.cols];
        this.permutation = new int[this.cols];
        this.lmDir = new double[this.cols];
        double[] dArr = new double[this.cols];
        double[] dArr2 = new double[this.cols];
        double[] dArr3 = new double[this.rows];
        double[] dArr4 = new double[this.cols];
        double[] dArr5 = new double[this.cols];
        double[] dArr6 = new double[this.cols];
        updateResidualsAndCost();
        this.lmPar = 0.0d;
        boolean z3 = true;
        double d6 = 0.0d;
        double d7 = 0.0d;
        while (true) {
            updateJacobian();
            qrDecomposition();
            qTy(this.residuals);
            int i = 0;
            while (i < this.solvedCols) {
                int i2 = this.permutation[i];
                this.jacobian[(this.cols * i) + i2] = this.diagR[i2];
                i++;
                dArr3 = dArr3;
            }
            double[] dArr7 = dArr3;
            if (z3) {
                double d8 = 0.0d;
                for (int i3 = 0; i3 < this.cols; i3++) {
                    double d9 = this.jacNorm[i3];
                    if (d9 == 0.0d) {
                        d9 = 1.0d;
                    }
                    double estimate = this.parameters[i3].getEstimate() * d9;
                    d8 += estimate * estimate;
                    dArr[i3] = d9;
                }
                d7 = org.apache.commons.math.util.FastMath.sqrt(d8);
                d6 = this.initialStepBoundFactor;
                if (d7 != 0.0d) {
                    d6 *= d7;
                }
            }
            if (this.cost == 0.0d) {
                z = z3;
                d = d6;
                d2 = 0.0d;
            } else {
                int i4 = 0;
                double d10 = 0.0d;
                while (i4 < this.solvedCols) {
                    int i5 = this.permutation[i4];
                    double d11 = this.jacNorm[i5];
                    if (d11 == 0.0d) {
                        z2 = z3;
                        d5 = d6;
                    } else {
                        int i6 = 0;
                        double d12 = 0.0d;
                        while (i6 <= i4) {
                            d12 += this.jacobian[i5] * this.residuals[i6];
                            i5 += this.cols;
                            i6++;
                            z3 = z3;
                        }
                        z2 = z3;
                        double abs = org.apache.commons.math.util.FastMath.abs(d12);
                        d5 = d6;
                        d10 = org.apache.commons.math.util.FastMath.max(d10, abs / (d11 * this.cost));
                    }
                    i4++;
                    d6 = d5;
                    z3 = z2;
                }
                z = z3;
                d = d6;
                d2 = d10;
            }
            if (d2 <= this.orthoTolerance) {
                return;
            }
            for (int i7 = 0; i7 < this.cols; i7++) {
                dArr[i7] = org.apache.commons.math.util.FastMath.max(dArr[i7], this.jacNorm[i7]);
            }
            dArr3 = dArr7;
            double d13 = d7;
            double d14 = d;
            double d15 = 0.0d;
            while (d15 < 1.0E-4d) {
                for (int i8 = 0; i8 < this.solvedCols; i8++) {
                    int i9 = this.permutation[i8];
                    dArr2[i9] = this.parameters[i9].getEstimate();
                }
                double d16 = this.cost;
                double[] dArr8 = this.residuals;
                this.residuals = dArr3;
                double d17 = d2;
                double d18 = d14;
                determineLMParameter(dArr8, d14, dArr, dArr4, dArr5, dArr6);
                double d19 = 0.0d;
                for (int i10 = 0; i10 < this.solvedCols; i10++) {
                    int i11 = this.permutation[i10];
                    this.lmDir[i11] = -this.lmDir[i11];
                    this.parameters[i11].setEstimate(dArr2[i11] + this.lmDir[i11]);
                    double d20 = dArr[i11] * this.lmDir[i11];
                    d19 += d20 * d20;
                }
                double sqrt = org.apache.commons.math.util.FastMath.sqrt(d19);
                if (!z) {
                    d14 = d18;
                } else {
                    d14 = org.apache.commons.math.util.FastMath.min(d18, sqrt);
                }
                updateResidualsAndCost();
                if (this.cost * 0.1d < d16) {
                    double d21 = this.cost / d16;
                    d3 = 1.0d - (d21 * d21);
                } else {
                    d3 = -1.0d;
                }
                int i12 = 0;
                while (i12 < this.solvedCols) {
                    int i13 = this.permutation[i12];
                    double d22 = this.lmDir[i13];
                    dArr4[i12] = 0.0d;
                    double[] dArr9 = dArr5;
                    int i14 = 0;
                    while (i14 <= i12) {
                        dArr4[i14] = dArr4[i14] + (this.jacobian[i13] * d22);
                        i13 += this.cols;
                        i14++;
                        dArr6 = dArr6;
                    }
                    i12++;
                    dArr5 = dArr9;
                }
                double[] dArr10 = dArr5;
                double[] dArr11 = dArr6;
                double d23 = 0.0d;
                for (int i15 = 0; i15 < this.solvedCols; i15++) {
                    d23 += dArr4[i15] * dArr4[i15];
                }
                double d24 = d16 * d16;
                double d25 = d23 / d24;
                double d26 = ((this.lmPar * sqrt) * sqrt) / d24;
                double d27 = d25 + (d26 * 2.0d);
                double d28 = -(d25 + d26);
                double d29 = d27 == 0.0d ? 0.0d : d3 / d27;
                if (d29 <= 0.25d) {
                    double d30 = d3 < 0.0d ? (d28 * 0.5d) / (d28 + (0.5d * d3)) : 0.5d;
                    double d31 = (this.cost * 0.1d >= d16 || d30 < 0.1d) ? 0.1d : d30;
                    double min = org.apache.commons.math.util.FastMath.min(d14, sqrt * 10.0d) * d31;
                    this.lmPar /= d31;
                    d14 = min;
                    d4 = 0.0d;
                } else {
                    d4 = 0.0d;
                    if (this.lmPar == 0.0d || d29 >= 0.75d) {
                        this.lmPar *= 0.5d;
                        d14 = sqrt * 2.0d;
                    }
                }
                if (d29 < 1.0E-4d) {
                    this.cost = d16;
                    for (int i16 = 0; i16 < this.solvedCols; i16++) {
                        int i17 = this.permutation[i16];
                        this.parameters[i17].setEstimate(dArr2[i17]);
                    }
                    dArr3 = this.residuals;
                    this.residuals = dArr8;
                } else {
                    double d32 = d4;
                    for (int i18 = 0; i18 < this.cols; i18++) {
                        double estimate2 = dArr[i18] * this.parameters[i18].getEstimate();
                        d32 += estimate2 * estimate2;
                    }
                    d13 = org.apache.commons.math.util.FastMath.sqrt(d32);
                    z = false;
                    dArr3 = dArr8;
                }
                if ((org.apache.commons.math.util.FastMath.abs(d3) <= this.costRelativeTolerance && d27 <= this.costRelativeTolerance && d29 <= 2.0d) || d14 <= this.parRelativeTolerance * d13) {
                    return;
                }
                if (org.apache.commons.math.util.FastMath.abs(d3) <= 2.2204E-16d && d27 <= 2.2204E-16d && d29 <= 2.0d) {
                    throw new org.apache.commons.math.estimation.EstimationException("cost relative tolerance is too small ({0}), no further reduction in the sum of squares is possible", java.lang.Double.valueOf(this.costRelativeTolerance));
                }
                if (d14 <= d13 * 2.2204E-16d) {
                    throw new org.apache.commons.math.estimation.EstimationException("parameters relative tolerance is too small ({0}), no further improvement in the approximate solution is possible", java.lang.Double.valueOf(this.parRelativeTolerance));
                }
                if (d17 <= 2.2204E-16d) {
                    throw new org.apache.commons.math.estimation.EstimationException("orthogonality tolerance is too small ({0}), solution is orthogonal to the jacobian", java.lang.Double.valueOf(this.orthoTolerance));
                }
                dArr5 = dArr10;
                d15 = d29;
                d2 = d17;
                dArr6 = dArr11;
            }
            d6 = d14;
            d7 = d13;
            z3 = z;
        }
    }

    private void determineLMParameter(double[] dArr, double d, double[] dArr2, double[] dArr3, double[] dArr4, double[] dArr5) {
        double d2;
        double d3;
        double[] dArr6 = dArr;
        for (int i = 0; i < this.rank; i++) {
            this.lmDir[this.permutation[i]] = dArr6[i];
        }
        for (int i2 = this.rank; i2 < this.cols; i2++) {
            this.lmDir[this.permutation[i2]] = 0.0d;
        }
        for (int i3 = this.rank - 1; i3 >= 0; i3--) {
            int i4 = this.permutation[i3];
            double d4 = this.lmDir[i4] / this.diagR[i4];
            int i5 = i4;
            for (int i6 = 0; i6 < i3; i6++) {
                double[] dArr7 = this.lmDir;
                int i7 = this.permutation[i6];
                dArr7[i7] = dArr7[i7] - (this.jacobian[i5] * d4);
                i5 += this.cols;
            }
            this.lmDir[i4] = d4;
        }
        double d5 = 0.0d;
        for (int i8 = 0; i8 < this.solvedCols; i8++) {
            int i9 = this.permutation[i8];
            double d6 = dArr2[i9] * this.lmDir[i9];
            dArr3[i9] = d6;
            d5 += d6 * d6;
        }
        double sqrt = org.apache.commons.math.util.FastMath.sqrt(d5);
        double d7 = sqrt - d;
        double d8 = d * 0.1d;
        if (d7 <= d8) {
            this.lmPar = 0.0d;
            return;
        }
        if (this.rank != this.solvedCols) {
            d2 = d8;
            d3 = 0.0d;
        } else {
            for (int i10 = 0; i10 < this.solvedCols; i10++) {
                int i11 = this.permutation[i10];
                dArr3[i11] = dArr3[i11] * (dArr2[i11] / sqrt);
            }
            double d9 = 0.0d;
            int i12 = 0;
            while (true) {
                d2 = d8;
                if (i12 >= this.solvedCols) {
                    break;
                }
                int i13 = this.permutation[i12];
                int i14 = i13;
                double d10 = 0.0d;
                for (int i15 = 0; i15 < i12; i15++) {
                    d10 += this.jacobian[i14] * dArr3[this.permutation[i15]];
                    i14 += this.cols;
                }
                double d11 = (dArr3[i13] - d10) / this.diagR[i13];
                dArr3[i13] = d11;
                d9 += d11 * d11;
                i12++;
                d8 = d2;
            }
            d3 = d7 / (d9 * d);
        }
        int i16 = 0;
        double d12 = 0.0d;
        while (i16 < this.solvedCols) {
            int i17 = this.permutation[i16];
            double d13 = d7;
            int i18 = i17;
            double d14 = 0.0d;
            for (int i19 = 0; i19 <= i16; i19++) {
                d14 += this.jacobian[i18] * dArr6[i19];
                i18 += this.cols;
            }
            double d15 = d14 / dArr2[i17];
            d12 += d15 * d15;
            i16++;
            d7 = d13;
        }
        double d16 = d7;
        double sqrt2 = org.apache.commons.math.util.FastMath.sqrt(d12);
        double d17 = sqrt2 / d;
        if (d17 == 0.0d) {
            d17 = 2.2251E-308d / org.apache.commons.math.util.FastMath.min(d, 0.1d);
        }
        this.lmPar = org.apache.commons.math.util.FastMath.min(d17, org.apache.commons.math.util.FastMath.max(this.lmPar, d3));
        if (this.lmPar == 0.0d) {
            this.lmPar = sqrt2 / sqrt;
        }
        int i20 = 10;
        while (i20 >= 0) {
            if (this.lmPar == 0.0d) {
                this.lmPar = org.apache.commons.math.util.FastMath.max(2.2251E-308d, 0.001d * d17);
            }
            double sqrt3 = org.apache.commons.math.util.FastMath.sqrt(this.lmPar);
            for (int i21 = 0; i21 < this.solvedCols; i21++) {
                int i22 = this.permutation[i21];
                dArr3[i22] = dArr2[i22] * sqrt3;
            }
            double[] dArr8 = dArr4;
            determineLMDirection(dArr6, dArr3, dArr8, dArr5);
            double d18 = 0.0d;
            for (int i23 = 0; i23 < this.solvedCols; i23++) {
                int i24 = this.permutation[i23];
                double d19 = dArr2[i24] * this.lmDir[i24];
                dArr5[i24] = d19;
                d18 += d19 * d19;
            }
            double sqrt4 = org.apache.commons.math.util.FastMath.sqrt(d18);
            double d20 = sqrt4 - d;
            if (org.apache.commons.math.util.FastMath.abs(d20) <= d2) {
                return;
            }
            if (d3 == 0.0d && d20 <= d16 && d16 < 0.0d) {
                return;
            }
            for (int i25 = 0; i25 < this.solvedCols; i25++) {
                int i26 = this.permutation[i25];
                dArr3[i26] = (dArr5[i26] * dArr2[i26]) / sqrt4;
            }
            int i27 = 0;
            while (i27 < this.solvedCols) {
                int i28 = this.permutation[i27];
                dArr3[i28] = dArr3[i28] / dArr8[i27];
                double d21 = dArr3[i28];
                i27++;
                int i29 = i27;
                while (i29 < this.solvedCols) {
                    int i30 = this.permutation[i29];
                    dArr3[i30] = dArr3[i30] - (this.jacobian[(this.cols * i29) + i28] * d21);
                    i29++;
                    i27 = i27;
                }
                dArr8 = dArr4;
            }
            double d22 = 0.0d;
            for (int i31 = 0; i31 < this.solvedCols; i31++) {
                double d23 = dArr3[this.permutation[i31]];
                d22 += d23 * d23;
            }
            double d24 = d20 / (d22 * d);
            if (d20 > 0.0d) {
                d3 = org.apache.commons.math.util.FastMath.max(d3, this.lmPar);
            } else if (d20 < 0.0d) {
                d17 = org.apache.commons.math.util.FastMath.min(d17, this.lmPar);
            }
            this.lmPar = org.apache.commons.math.util.FastMath.max(d3, this.lmPar + d24);
            i20--;
            dArr6 = dArr;
            d16 = d20;
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
                this.jacobian[(this.cols * i4) + i2] = this.jacobian[(this.cols * i) + this.permutation[i4]];
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
                    double d6 = this.jacobian[(this.cols * i6) + i7];
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
                    this.jacobian[(this.cols * i6) + i7] = (d6 * d2) + (dArr3[i6] * d);
                    double d9 = (dArr4[i6] * d2) + (d * d5);
                    double d10 = -d;
                    d5 = (dArr4[i6] * d10) + (d5 * d2);
                    dArr4[i6] = d9;
                    for (int i8 = i6 + 1; i8 < this.solvedCols; i8++) {
                        double d11 = this.jacobian[(this.cols * i8) + i7];
                        double d12 = (d2 * d11) + (dArr3[i8] * d);
                        dArr3[i8] = (d11 * d10) + (dArr3[i8] * d2);
                        this.jacobian[(this.cols * i8) + i7] = d12;
                    }
                }
                i6++;
                d3 = 0.0d;
            }
            int i9 = (this.cols * i5) + this.permutation[i5];
            dArr3[i5] = this.jacobian[i9];
            this.jacobian[i9] = this.lmDir[i5];
            i5++;
        }
        int i10 = this.solvedCols;
        for (int i11 = 0; i11 < this.solvedCols; i11++) {
            if (dArr3[i11] == 0.0d && i10 == this.solvedCols) {
                i10 = i11;
            }
            if (i10 < this.solvedCols) {
                dArr4[i11] = 0.0d;
            }
        }
        if (i10 > 0) {
            for (int i12 = i10 - 1; i12 >= 0; i12--) {
                int i13 = this.permutation[i12];
                double d13 = 0.0d;
                for (int i14 = i12 + 1; i14 < i10; i14++) {
                    d13 += this.jacobian[(this.cols * i14) + i13] * dArr4[i14];
                }
                dArr4[i12] = (dArr4[i12] - d13) / dArr3[i12];
            }
        }
        for (int i15 = 0; i15 < this.lmDir.length; i15++) {
            this.lmDir[this.permutation[i15]] = dArr4[i15];
        }
    }

    private void qrDecomposition() throws org.apache.commons.math.estimation.EstimationException {
        double d;
        int i = 0;
        int i2 = 0;
        while (true) {
            d = 0.0d;
            if (i2 >= this.cols) {
                break;
            }
            this.permutation[i2] = i2;
            int i3 = i2;
            while (i3 < this.jacobian.length) {
                double d2 = this.jacobian[i3];
                d += d2 * d2;
                i3 += this.cols;
            }
            this.jacNorm[i2] = org.apache.commons.math.util.FastMath.sqrt(d);
            i2++;
        }
        while (i < this.cols) {
            int i4 = -1;
            double d3 = Double.NEGATIVE_INFINITY;
            for (int i5 = i; i5 < this.cols; i5++) {
                int i6 = (this.cols * i) + this.permutation[i5];
                double d4 = d;
                while (i6 < this.jacobian.length) {
                    double d5 = this.jacobian[i6];
                    d4 += d5 * d5;
                    i6 += this.cols;
                }
                if (!java.lang.Double.isInfinite(d4) && !java.lang.Double.isNaN(d4)) {
                    if (d4 > d3) {
                        i4 = i5;
                        d3 = d4;
                    }
                } else {
                    throw new org.apache.commons.math.estimation.EstimationException(org.apache.commons.math.exception.util.LocalizedFormats.UNABLE_TO_PERFORM_QR_DECOMPOSITION_ON_JACOBIAN, java.lang.Integer.valueOf(this.rows), java.lang.Integer.valueOf(this.cols));
                }
            }
            if (d3 == d) {
                this.rank = i;
                return;
            }
            int i7 = this.permutation[i4];
            this.permutation[i4] = this.permutation[i];
            this.permutation[i] = i7;
            int i8 = (this.cols * i) + i7;
            double d6 = this.jacobian[i8];
            double sqrt = d6 > d ? -org.apache.commons.math.util.FastMath.sqrt(d3) : org.apache.commons.math.util.FastMath.sqrt(d3);
            double d7 = 1.0d / (d3 - (d6 * sqrt));
            this.beta[i7] = d7;
            this.diagR[i7] = sqrt;
            double[] dArr = this.jacobian;
            dArr[i8] = dArr[i8] - sqrt;
            int i9 = (this.cols - 1) - i;
            while (i9 > 0) {
                int i10 = this.permutation[i + i9] - i7;
                int i11 = i8;
                double d8 = d;
                while (i11 < this.jacobian.length) {
                    d8 += this.jacobian[i11] * this.jacobian[i11 + i10];
                    i11 += this.cols;
                }
                double d9 = d8 * d7;
                int i12 = i8;
                while (i12 < this.jacobian.length) {
                    double[] dArr2 = this.jacobian;
                    int i13 = i12 + i10;
                    dArr2[i13] = dArr2[i13] - (this.jacobian[i12] * d9);
                    i12 += this.cols;
                }
                i9--;
                d = 0.0d;
            }
            i++;
            d = 0.0d;
        }
        this.rank = this.solvedCols;
    }

    private void qTy(double[] dArr) {
        for (int i = 0; i < this.cols; i++) {
            int i2 = this.permutation[i];
            int i3 = (this.cols * i) + i2;
            double d = 0.0d;
            int i4 = i3;
            for (int i5 = i; i5 < this.rows; i5++) {
                d += this.jacobian[i4] * dArr[i5];
                i4 += this.cols;
            }
            double d2 = d * this.beta[i2];
            for (int i6 = i; i6 < this.rows; i6++) {
                dArr[i6] = dArr[i6] - (this.jacobian[i3] * d2);
                i3 += this.cols;
            }
        }
    }
}
