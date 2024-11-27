package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
class TriDiagonalTransformer {
    private org.apache.commons.math.linear.RealMatrix cachedQ;
    private org.apache.commons.math.linear.RealMatrix cachedQt;
    private org.apache.commons.math.linear.RealMatrix cachedT;
    private final double[][] householderVectors;
    private final double[] main;
    private final double[] secondary;

    public TriDiagonalTransformer(org.apache.commons.math.linear.RealMatrix realMatrix) throws org.apache.commons.math.linear.InvalidMatrixException {
        if (!realMatrix.isSquare()) {
            throw new org.apache.commons.math.linear.NonSquareMatrixException(realMatrix.getRowDimension(), realMatrix.getColumnDimension());
        }
        int rowDimension = realMatrix.getRowDimension();
        this.householderVectors = realMatrix.getData();
        this.main = new double[rowDimension];
        this.secondary = new double[rowDimension - 1];
        this.cachedQ = null;
        this.cachedQt = null;
        this.cachedT = null;
        transform();
    }

    public org.apache.commons.math.linear.RealMatrix getQ() {
        if (this.cachedQ == null) {
            this.cachedQ = getQT().transpose();
        }
        return this.cachedQ;
    }

    public org.apache.commons.math.linear.RealMatrix getQT() {
        if (this.cachedQt == null) {
            int length = this.householderVectors.length;
            this.cachedQt = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length, length);
            for (int i = length - 1; i >= 1; i--) {
                int i2 = i - 1;
                double[] dArr = this.householderVectors[i2];
                double d = 1.0d / (this.secondary[i2] * dArr[i]);
                this.cachedQt.setEntry(i, i, 1.0d);
                if (dArr[i] != 0.0d) {
                    double d2 = 1.0d / this.secondary[i2];
                    this.cachedQt.setEntry(i, i, (dArr[i] * d2) + 1.0d);
                    int i3 = i + 1;
                    for (int i4 = i3; i4 < length; i4++) {
                        this.cachedQt.setEntry(i, i4, dArr[i4] * d2);
                    }
                    for (int i5 = i3; i5 < length; i5++) {
                        double d3 = 0.0d;
                        for (int i6 = i3; i6 < length; i6++) {
                            d3 += this.cachedQt.getEntry(i5, i6) * dArr[i6];
                        }
                        double d4 = d3 * d;
                        this.cachedQt.setEntry(i5, i, dArr[i] * d4);
                        for (int i7 = i3; i7 < length; i7++) {
                            this.cachedQt.addToEntry(i5, i7, dArr[i7] * d4);
                        }
                    }
                }
            }
            this.cachedQt.setEntry(0, 0, 1.0d);
        }
        return this.cachedQt;
    }

    public org.apache.commons.math.linear.RealMatrix getT() {
        if (this.cachedT == null) {
            int length = this.main.length;
            this.cachedT = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length, length);
            for (int i = 0; i < length; i++) {
                this.cachedT.setEntry(i, i, this.main[i]);
                if (i > 0) {
                    int i2 = i - 1;
                    this.cachedT.setEntry(i, i2, this.secondary[i2]);
                }
                if (i < this.main.length - 1) {
                    this.cachedT.setEntry(i, i + 1, this.secondary[i]);
                }
            }
        }
        return this.cachedT;
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

    private void transform() {
        int length = this.householderVectors.length;
        double[] dArr = new double[length];
        int i = 0;
        while (true) {
            int i2 = length - 1;
            if (i >= i2) {
                this.main[i2] = this.householderVectors[i2][i2];
                return;
            }
            double[] dArr2 = this.householderVectors[i];
            this.main[i] = dArr2[i];
            int i3 = i + 1;
            double d = 0.0d;
            for (int i4 = i3; i4 < length; i4++) {
                double d2 = dArr2[i4];
                d += d2 * d2;
            }
            double sqrt = dArr2[i3] > 0.0d ? -org.apache.commons.math.util.FastMath.sqrt(d) : org.apache.commons.math.util.FastMath.sqrt(d);
            this.secondary[i] = sqrt;
            if (sqrt != 0.0d) {
                dArr2[i3] = dArr2[i3] - sqrt;
                double d3 = (-1.0d) / (sqrt * dArr2[i3]);
                java.util.Arrays.fill(dArr, i3, length, 0.0d);
                int i5 = i3;
                while (i5 < length) {
                    double[] dArr3 = this.householderVectors[i5];
                    double d4 = dArr2[i5];
                    double d5 = dArr3[i5] * d4;
                    int i6 = i5 + 1;
                    for (int i7 = i6; i7 < length; i7++) {
                        double d6 = dArr3[i7];
                        d5 += dArr2[i7] * d6;
                        dArr[i7] = dArr[i7] + (d6 * d4);
                    }
                    dArr[i5] = (dArr[i5] + d5) * d3;
                    i5 = i6;
                }
                double d7 = 0.0d;
                for (int i8 = i3; i8 < length; i8++) {
                    d7 += dArr[i8] * dArr2[i8];
                }
                double d8 = d7 * (d3 / 2.0d);
                for (int i9 = i3; i9 < length; i9++) {
                    dArr[i9] = dArr[i9] - (dArr2[i9] * d8);
                }
                for (int i10 = i3; i10 < length; i10++) {
                    double[] dArr4 = this.householderVectors[i10];
                    for (int i11 = i10; i11 < length; i11++) {
                        dArr4[i11] = dArr4[i11] - ((dArr2[i10] * dArr[i11]) + (dArr[i10] * dArr2[i11]));
                    }
                }
            }
            i = i3;
        }
    }
}
