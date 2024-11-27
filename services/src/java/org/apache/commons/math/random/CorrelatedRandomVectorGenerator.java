package org.apache.commons.math.random;

/* loaded from: classes3.dex */
public class CorrelatedRandomVectorGenerator implements org.apache.commons.math.random.RandomVectorGenerator {
    private final org.apache.commons.math.random.NormalizedRandomGenerator generator;
    private final double[] mean;
    private final double[] normalized;
    private int rank;
    private org.apache.commons.math.linear.RealMatrix root;

    public CorrelatedRandomVectorGenerator(double[] dArr, org.apache.commons.math.linear.RealMatrix realMatrix, double d, org.apache.commons.math.random.NormalizedRandomGenerator normalizedRandomGenerator) throws org.apache.commons.math.linear.NotPositiveDefiniteMatrixException, org.apache.commons.math.DimensionMismatchException {
        int rowDimension = realMatrix.getRowDimension();
        if (dArr.length != rowDimension) {
            throw new org.apache.commons.math.DimensionMismatchException(dArr.length, rowDimension);
        }
        this.mean = (double[]) dArr.clone();
        decompose(realMatrix, d);
        this.generator = normalizedRandomGenerator;
        this.normalized = new double[this.rank];
    }

    public CorrelatedRandomVectorGenerator(org.apache.commons.math.linear.RealMatrix realMatrix, double d, org.apache.commons.math.random.NormalizedRandomGenerator normalizedRandomGenerator) throws org.apache.commons.math.linear.NotPositiveDefiniteMatrixException {
        int rowDimension = realMatrix.getRowDimension();
        this.mean = new double[rowDimension];
        for (int i = 0; i < rowDimension; i++) {
            this.mean[i] = 0.0d;
        }
        decompose(realMatrix, d);
        this.generator = normalizedRandomGenerator;
        this.normalized = new double[this.rank];
    }

    public org.apache.commons.math.random.NormalizedRandomGenerator getGenerator() {
        return this.generator;
    }

    public org.apache.commons.math.linear.RealMatrix getRootMatrix() {
        return this.root;
    }

    public int getRank() {
        return this.rank;
    }

    private void decompose(org.apache.commons.math.linear.RealMatrix realMatrix, double d) throws org.apache.commons.math.linear.NotPositiveDefiniteMatrixException {
        int i;
        int rowDimension = realMatrix.getRowDimension();
        double[][] data = realMatrix.getData();
        int i2 = 1;
        boolean z = false;
        double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, rowDimension, rowDimension);
        int[] iArr = new int[rowDimension];
        int[] iArr2 = new int[rowDimension];
        for (int i3 = 0; i3 < rowDimension; i3++) {
            iArr2[i3] = i3;
        }
        this.rank = 0;
        boolean z2 = true;
        while (z2) {
            iArr[this.rank] = this.rank;
            for (int i4 = this.rank + i2; i4 < rowDimension; i4++) {
                int i5 = iArr2[i4];
                int i6 = iArr2[iArr[i4]];
                if (data[i5][i5] > data[i6][i6]) {
                    iArr[this.rank] = i4;
                }
            }
            if (iArr[this.rank] != this.rank) {
                int i7 = iArr2[this.rank];
                iArr2[this.rank] = iArr2[iArr[this.rank]];
                iArr2[iArr[this.rank]] = i7;
            }
            int i8 = iArr2[this.rank];
            if (data[i8][i8] < d) {
                if (this.rank == 0) {
                    throw new org.apache.commons.math.linear.NotPositiveDefiniteMatrixException();
                }
                for (int i9 = this.rank; i9 < rowDimension; i9++) {
                    if (data[iArr2[i9]][iArr2[i9]] < (-d)) {
                        throw new org.apache.commons.math.linear.NotPositiveDefiniteMatrixException();
                    }
                }
                this.rank += i2;
                z2 = z;
                i = i2;
            } else {
                double sqrt = org.apache.commons.math.util.FastMath.sqrt(data[i8][i8]);
                dArr[this.rank][this.rank] = sqrt;
                double d2 = 1.0d / sqrt;
                int i10 = this.rank + i2;
                while (i10 < rowDimension) {
                    int i11 = iArr2[i10];
                    double d3 = data[i11][i8] * d2;
                    dArr[i10][this.rank] = d3;
                    double[] dArr2 = data[i11];
                    dArr2[i11] = dArr2[i11] - (d3 * d3);
                    for (int i12 = this.rank + i2; i12 < i10; i12++) {
                        int i13 = iArr2[i12];
                        double d4 = data[i11][i13] - (dArr[i12][this.rank] * d3);
                        data[i11][i13] = d4;
                        data[i13][i11] = d4;
                    }
                    i10++;
                    i2 = 1;
                }
                i = 1;
                int i14 = this.rank + 1;
                this.rank = i14;
                z2 = i14 < rowDimension;
            }
            i2 = i;
            z = false;
        }
        this.root = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(rowDimension, this.rank);
        for (int i15 = 0; i15 < rowDimension; i15++) {
            for (int i16 = 0; i16 < this.rank; i16++) {
                this.root.setEntry(iArr2[i15], i16, dArr[i15][i16]);
            }
        }
    }

    @Override // org.apache.commons.math.random.RandomVectorGenerator
    public double[] nextVector() {
        for (int i = 0; i < this.rank; i++) {
            this.normalized[i] = this.generator.nextNormalizedDouble();
        }
        int length = this.mean.length;
        double[] dArr = new double[length];
        for (int i2 = 0; i2 < length; i2++) {
            dArr[i2] = this.mean[i2];
            for (int i3 = 0; i3 < this.rank; i3++) {
                dArr[i2] = dArr[i2] + (this.root.getEntry(i2, i3) * this.normalized[i3]);
            }
        }
        return dArr;
    }
}
