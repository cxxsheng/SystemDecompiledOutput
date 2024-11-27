package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
class BiDiagonalTransformer {
    private org.apache.commons.math.linear.RealMatrix cachedB;
    private org.apache.commons.math.linear.RealMatrix cachedU;
    private org.apache.commons.math.linear.RealMatrix cachedV;
    private final double[][] householderVectors;
    private final double[] main;
    private final double[] secondary;

    public BiDiagonalTransformer(org.apache.commons.math.linear.RealMatrix realMatrix) {
        int rowDimension = realMatrix.getRowDimension();
        int columnDimension = realMatrix.getColumnDimension();
        int min = org.apache.commons.math.util.FastMath.min(rowDimension, columnDimension);
        this.householderVectors = realMatrix.getData();
        this.main = new double[min];
        this.secondary = new double[min - 1];
        this.cachedU = null;
        this.cachedB = null;
        this.cachedV = null;
        if (rowDimension >= columnDimension) {
            transformToUpperBiDiagonal();
        } else {
            transformToLowerBiDiagonal();
        }
    }

    public org.apache.commons.math.linear.RealMatrix getU() {
        double d;
        if (this.cachedU == null) {
            int length = this.householderVectors.length;
            int length2 = this.householderVectors[0].length;
            int length3 = this.main.length;
            int i = length >= length2 ? 0 : 1;
            double[] dArr = length >= length2 ? this.main : this.secondary;
            this.cachedU = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length, length);
            int i2 = length - 1;
            while (true) {
                d = 1.0d;
                if (i2 < length3) {
                    break;
                }
                this.cachedU.setEntry(i2, i2, 1.0d);
                i2--;
            }
            int i3 = length3 - 1;
            while (i3 >= i) {
                double[] dArr2 = this.householderVectors[i3];
                this.cachedU.setEntry(i3, i3, d);
                int i4 = i3 - i;
                double d2 = 0.0d;
                if (dArr2[i4] != 0.0d) {
                    int i5 = i3;
                    while (i5 < length) {
                        double d3 = d2;
                        for (int i6 = i3; i6 < length; i6++) {
                            d3 -= this.cachedU.getEntry(i6, i5) * this.householderVectors[i6][i4];
                        }
                        double d4 = d3 / (dArr[i4] * dArr2[i4]);
                        for (int i7 = i3; i7 < length; i7++) {
                            this.cachedU.addToEntry(i7, i5, (-d4) * this.householderVectors[i7][i4]);
                        }
                        i5++;
                        d2 = 0.0d;
                    }
                }
                i3--;
                d = 1.0d;
            }
            if (i > 0) {
                this.cachedU.setEntry(0, 0, 1.0d);
            }
        }
        return this.cachedU;
    }

    public org.apache.commons.math.linear.RealMatrix getB() {
        if (this.cachedB == null) {
            int length = this.householderVectors.length;
            int length2 = this.householderVectors[0].length;
            this.cachedB = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length, length2);
            for (int i = 0; i < this.main.length; i++) {
                this.cachedB.setEntry(i, i, this.main[i]);
                if (length < length2) {
                    if (i > 0) {
                        int i2 = i - 1;
                        this.cachedB.setEntry(i, i2, this.secondary[i2]);
                    }
                } else if (i < this.main.length - 1) {
                    this.cachedB.setEntry(i, i + 1, this.secondary[i]);
                }
            }
        }
        return this.cachedB;
    }

    public org.apache.commons.math.linear.RealMatrix getV() {
        double d;
        if (this.cachedV == null) {
            int length = this.householderVectors.length;
            int length2 = this.householderVectors[0].length;
            int length3 = this.main.length;
            int i = length >= length2 ? 1 : 0;
            double[] dArr = length >= length2 ? this.secondary : this.main;
            this.cachedV = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length2, length2);
            int i2 = length2 - 1;
            while (true) {
                d = 1.0d;
                if (i2 < length3) {
                    break;
                }
                this.cachedV.setEntry(i2, i2, 1.0d);
                i2--;
            }
            int i3 = length3 - 1;
            while (i3 >= i) {
                int i4 = i3 - i;
                double[] dArr2 = this.householderVectors[i4];
                this.cachedV.setEntry(i3, i3, d);
                double d2 = 0.0d;
                if (dArr2[i3] != 0.0d) {
                    int i5 = i3;
                    while (i5 < length2) {
                        double d3 = d2;
                        for (int i6 = i3; i6 < length2; i6++) {
                            d3 -= this.cachedV.getEntry(i6, i5) * dArr2[i6];
                        }
                        double d4 = d3 / (dArr[i4] * dArr2[i3]);
                        for (int i7 = i3; i7 < length2; i7++) {
                            this.cachedV.addToEntry(i7, i5, (-d4) * dArr2[i7]);
                        }
                        i5++;
                        d2 = 0.0d;
                    }
                }
                i3--;
                d = 1.0d;
            }
            if (i > 0) {
                this.cachedV.setEntry(0, 0, 1.0d);
            }
        }
        return this.cachedV;
    }

    double[][] getHouseholderVectorsRef() {
        return this.householderVectors;
    }

    double[] getMainDiagonalRef() {
        return this.main;
    }

    double[] getSecondaryDiagonalRef() {
        return this.secondary;
    }

    boolean isUpperBiDiagonal() {
        return this.householderVectors.length >= this.householderVectors[0].length;
    }

    private void transformToUpperBiDiagonal() {
        int length = this.householderVectors.length;
        int length2 = this.householderVectors[0].length;
        for (int i = 0; i < length2; i++) {
            double d = 0.0d;
            for (int i2 = i; i2 < length; i2++) {
                double d2 = this.householderVectors[i2][i];
                d += d2 * d2;
            }
            double[] dArr = this.householderVectors[i];
            double d3 = dArr[i];
            double sqrt = org.apache.commons.math.util.FastMath.sqrt(d);
            if (d3 > 0.0d) {
                sqrt = -sqrt;
            }
            this.main[i] = sqrt;
            if (sqrt != 0.0d) {
                dArr[i] = dArr[i] - sqrt;
                for (int i3 = i + 1; i3 < length2; i3++) {
                    double d4 = 0.0d;
                    for (int i4 = i; i4 < length; i4++) {
                        double[] dArr2 = this.householderVectors[i4];
                        d4 -= dArr2[i3] * dArr2[i];
                    }
                    double d5 = d4 / (this.householderVectors[i][i] * sqrt);
                    for (int i5 = i; i5 < length; i5++) {
                        double[] dArr3 = this.householderVectors[i5];
                        dArr3[i3] = dArr3[i3] - (dArr3[i] * d5);
                    }
                }
            }
            if (i < length2 - 1) {
                int i6 = i + 1;
                double d6 = 0.0d;
                for (int i7 = i6; i7 < length2; i7++) {
                    double d7 = dArr[i7];
                    d6 += d7 * d7;
                }
                double sqrt2 = dArr[i6] > 0.0d ? -org.apache.commons.math.util.FastMath.sqrt(d6) : org.apache.commons.math.util.FastMath.sqrt(d6);
                this.secondary[i] = sqrt2;
                if (sqrt2 != 0.0d) {
                    dArr[i6] = dArr[i6] - sqrt2;
                    for (int i8 = i6; i8 < length; i8++) {
                        double[] dArr4 = this.householderVectors[i8];
                        double d8 = 0.0d;
                        for (int i9 = i6; i9 < length2; i9++) {
                            d8 -= dArr4[i9] * dArr[i9];
                        }
                        double d9 = d8 / (dArr[i6] * sqrt2);
                        for (int i10 = i6; i10 < length2; i10++) {
                            dArr4[i10] = dArr4[i10] - (dArr[i10] * d9);
                        }
                    }
                }
            }
        }
    }

    private void transformToLowerBiDiagonal() {
        int length = this.householderVectors.length;
        int length2 = this.householderVectors[0].length;
        for (int i = 0; i < length; i++) {
            double[] dArr = this.householderVectors[i];
            double d = 0.0d;
            for (int i2 = i; i2 < length2; i2++) {
                double d2 = dArr[i2];
                d += d2 * d2;
            }
            double sqrt = dArr[i] > 0.0d ? -org.apache.commons.math.util.FastMath.sqrt(d) : org.apache.commons.math.util.FastMath.sqrt(d);
            this.main[i] = sqrt;
            if (sqrt != 0.0d) {
                dArr[i] = dArr[i] - sqrt;
                for (int i3 = i + 1; i3 < length; i3++) {
                    double[] dArr2 = this.householderVectors[i3];
                    double d3 = 0.0d;
                    for (int i4 = i; i4 < length2; i4++) {
                        d3 -= dArr2[i4] * dArr[i4];
                    }
                    double d4 = d3 / (this.householderVectors[i][i] * sqrt);
                    for (int i5 = i; i5 < length2; i5++) {
                        dArr2[i5] = dArr2[i5] - (dArr[i5] * d4);
                    }
                }
            }
            if (i < length - 1) {
                int i6 = i + 1;
                double[] dArr3 = this.householderVectors[i6];
                double d5 = 0.0d;
                for (int i7 = i6; i7 < length; i7++) {
                    double d6 = this.householderVectors[i7][i];
                    d5 += d6 * d6;
                }
                double sqrt2 = dArr3[i] > 0.0d ? -org.apache.commons.math.util.FastMath.sqrt(d5) : org.apache.commons.math.util.FastMath.sqrt(d5);
                this.secondary[i] = sqrt2;
                if (sqrt2 != 0.0d) {
                    dArr3[i] = dArr3[i] - sqrt2;
                    for (int i8 = i6; i8 < length2; i8++) {
                        double d7 = 0.0d;
                        for (int i9 = i6; i9 < length; i9++) {
                            double[] dArr4 = this.householderVectors[i9];
                            d7 -= dArr4[i8] * dArr4[i];
                        }
                        double d8 = d7 / (dArr3[i] * sqrt2);
                        for (int i10 = i6; i10 < length; i10++) {
                            double[] dArr5 = this.householderVectors[i10];
                            dArr5[i8] = dArr5[i8] - (dArr5[i] * d8);
                        }
                    }
                }
            }
        }
    }
}
