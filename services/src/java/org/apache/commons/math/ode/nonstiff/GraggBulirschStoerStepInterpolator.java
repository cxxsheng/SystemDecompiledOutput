package org.apache.commons.math.ode.nonstiff;

/* loaded from: classes3.dex */
class GraggBulirschStoerStepInterpolator extends org.apache.commons.math.ode.sampling.AbstractStepInterpolator {
    private static final long serialVersionUID = 7320613236731409847L;
    private int currentDegree;
    private double[] errfac;
    private double[][] polynoms;
    private double[] y0Dot;
    private double[] y1;
    private double[] y1Dot;
    private double[][] yMidDots;

    public GraggBulirschStoerStepInterpolator() {
        this.y0Dot = null;
        this.y1 = null;
        this.y1Dot = null;
        this.yMidDots = null;
        resetTables(-1);
    }

    public GraggBulirschStoerStepInterpolator(double[] dArr, double[] dArr2, double[] dArr3, double[] dArr4, double[][] dArr5, boolean z) {
        super(dArr, z);
        this.y0Dot = dArr2;
        this.y1 = dArr3;
        this.y1Dot = dArr4;
        this.yMidDots = dArr5;
        resetTables(dArr5.length + 4);
    }

    public GraggBulirschStoerStepInterpolator(org.apache.commons.math.ode.nonstiff.GraggBulirschStoerStepInterpolator graggBulirschStoerStepInterpolator) {
        super(graggBulirschStoerStepInterpolator);
        int length = this.currentState.length;
        this.y0Dot = null;
        this.y1 = null;
        this.y1Dot = null;
        this.yMidDots = null;
        if (graggBulirschStoerStepInterpolator.polynoms == null) {
            this.polynoms = null;
            this.currentDegree = -1;
            return;
        }
        resetTables(graggBulirschStoerStepInterpolator.currentDegree);
        for (int i = 0; i < this.polynoms.length; i++) {
            this.polynoms[i] = new double[length];
            java.lang.System.arraycopy(graggBulirschStoerStepInterpolator.polynoms[i], 0, this.polynoms[i], 0, length);
        }
        this.currentDegree = graggBulirschStoerStepInterpolator.currentDegree;
    }

    private void resetTables(int i) {
        if (i < 0) {
            this.polynoms = null;
            this.errfac = null;
            this.currentDegree = -1;
            return;
        }
        int i2 = i + 1;
        double[][] dArr = new double[i2][];
        if (this.polynoms != null) {
            java.lang.System.arraycopy(this.polynoms, 0, dArr, 0, this.polynoms.length);
            for (int length = this.polynoms.length; length < i2; length++) {
                dArr[length] = new double[this.currentState.length];
            }
        } else {
            for (int i3 = 0; i3 < i2; i3++) {
                dArr[i3] = new double[this.currentState.length];
            }
        }
        this.polynoms = dArr;
        if (i <= 4) {
            this.errfac = null;
        } else {
            this.errfac = new double[i - 4];
            int i4 = 0;
            while (i4 < this.errfac.length) {
                this.errfac[i4] = 1.0d / (r0 * r0);
                int i5 = i4 + 1;
                double sqrt = org.apache.commons.math.util.FastMath.sqrt(i5 / (i4 + 5)) * 0.5d;
                int i6 = 0;
                while (i6 <= i4) {
                    double[] dArr2 = this.errfac;
                    i6++;
                    dArr2[i4] = dArr2[i4] * (sqrt / i6);
                }
                i4 = i5;
            }
        }
        this.currentDegree = 0;
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected org.apache.commons.math.ode.sampling.StepInterpolator doCopy() {
        return new org.apache.commons.math.ode.nonstiff.GraggBulirschStoerStepInterpolator(this);
    }

    public void computeCoefficients(int i, double d) {
        if (this.polynoms == null || this.polynoms.length <= i + 4) {
            resetTables(i + 4);
        }
        this.currentDegree = i + 4;
        char c = 0;
        int i2 = 0;
        while (i2 < this.currentState.length) {
            double d2 = this.y0Dot[i2] * d;
            double d3 = this.y1Dot[i2] * d;
            double d4 = this.y1[i2] - this.currentState[i2];
            double d5 = d4 - d3;
            double d6 = d2 - d4;
            this.polynoms[c][i2] = this.currentState[i2];
            this.polynoms[1][i2] = d4;
            this.polynoms[2][i2] = d5;
            this.polynoms[3][i2] = d6;
            if (i >= 0) {
                this.polynoms[4][i2] = (this.yMidDots[c][i2] - (((this.currentState[i2] + this.y1[i2]) * 0.5d) + ((d5 + d6) * 0.125d))) * 16.0d;
                if (i > 0) {
                    this.polynoms[5][i2] = (this.yMidDots[1][i2] - (d4 + ((d5 - d6) * 0.25d))) * 16.0d;
                    if (i > 1) {
                        this.polynoms[6][i2] = ((this.yMidDots[2][i2] - (d3 - d2)) + this.polynoms[4][i2]) * 16.0d;
                        if (i > 2) {
                            this.polynoms[7][i2] = ((this.yMidDots[3][i2] - ((d6 - d5) * 6.0d)) + (this.polynoms[5][i2] * 3.0d)) * 16.0d;
                            for (int i3 = 4; i3 <= i; i3++) {
                                double d7 = i3 * 0.5d * (i3 - 1);
                                this.polynoms[i3 + 4][i2] = ((this.yMidDots[i3][i2] + (d7 * this.polynoms[i3 + 2][i2])) - ((((2.0d * d7) * (i3 - 2)) * (i3 - 3)) * this.polynoms[i3][i2])) * 16.0d;
                            }
                        }
                    }
                }
                i2++;
                c = 0;
            } else {
                return;
            }
        }
    }

    public double estimateError(double[] dArr) {
        double d = 0.0d;
        if (this.currentDegree < 5) {
            return 0.0d;
        }
        for (int i = 0; i < dArr.length; i++) {
            double d2 = this.polynoms[this.currentDegree][i] / dArr[i];
            d += d2 * d2;
        }
        return org.apache.commons.math.util.FastMath.sqrt(d / dArr.length) * this.errfac[this.currentDegree - 5];
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator
    protected void computeInterpolatedStateAndDerivatives(double d, double d2) {
        double d3;
        double d4;
        int length = this.currentState.length;
        double d5 = 1.0d - d;
        double d6 = d - 0.5d;
        double d7 = d * d5;
        double d8 = d7 * d7;
        double d9 = d7 * 2.0d * (1.0d - (d * 2.0d));
        double d10 = 1.0d / this.h;
        double d11 = 3.0d * d;
        double d12 = ((2.0d - d11) * d) / this.h;
        double d13 = (((d11 - 4.0d) * d) + 1.0d) / this.h;
        char c = 0;
        int i = 0;
        while (true) {
            double d14 = 0.0d;
            if (i >= length) {
                break;
            }
            int i2 = length;
            double d15 = this.polynoms[c][i];
            double d16 = this.polynoms[1][i];
            double d17 = this.polynoms[2][i];
            double d18 = this.polynoms[3][i];
            this.interpolatedState[i] = d15 + ((d16 + (((d17 * d) + (d18 * d5)) * d5)) * d);
            this.interpolatedDerivatives[i] = (d16 * d10) + (d17 * d12) + (d18 * d13);
            if (this.currentDegree > 3) {
                double d19 = this.polynoms[this.currentDegree][i];
                int i3 = this.currentDegree - 1;
                while (i3 > 3) {
                    double d20 = d5;
                    double d21 = 1.0d / (i3 - 3);
                    d14 = ((d14 * d6) + d19) * d21;
                    d19 = this.polynoms[i3][i] + (d19 * d21 * d6);
                    i3--;
                    d5 = d20;
                }
                d3 = d5;
                double[] dArr = this.interpolatedState;
                dArr[i] = dArr[i] + (d8 * d19);
                double[] dArr2 = this.interpolatedDerivatives;
                d4 = d6;
                dArr2[i] = dArr2[i] + (((d14 * d8) + (d19 * d9)) / this.h);
            } else {
                d3 = d5;
                d4 = d6;
            }
            i++;
            d6 = d4;
            length = i2;
            d5 = d3;
            c = 0;
        }
        int i4 = length;
        if (this.h == 0.0d) {
            java.lang.System.arraycopy(this.yMidDots[1], 0, this.interpolatedDerivatives, 0, i4);
        }
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator, java.io.Externalizable
    public void writeExternal(java.io.ObjectOutput objectOutput) throws java.io.IOException {
        int length = this.currentState == null ? -1 : this.currentState.length;
        writeBaseExternal(objectOutput);
        objectOutput.writeInt(this.currentDegree);
        for (int i = 0; i <= this.currentDegree; i++) {
            for (int i2 = 0; i2 < length; i2++) {
                objectOutput.writeDouble(this.polynoms[i][i2]);
            }
        }
    }

    @Override // org.apache.commons.math.ode.sampling.AbstractStepInterpolator, java.io.Externalizable
    public void readExternal(java.io.ObjectInput objectInput) throws java.io.IOException {
        double readBaseExternal = readBaseExternal(objectInput);
        int length = this.currentState == null ? -1 : this.currentState.length;
        int readInt = objectInput.readInt();
        resetTables(readInt);
        this.currentDegree = readInt;
        for (int i = 0; i <= this.currentDegree; i++) {
            for (int i2 = 0; i2 < length; i2++) {
                this.polynoms[i][i2] = objectInput.readDouble();
            }
        }
        setInterpolatedTime(readBaseExternal);
    }
}
