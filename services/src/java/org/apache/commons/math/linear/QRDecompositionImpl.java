package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class QRDecompositionImpl implements org.apache.commons.math.linear.QRDecomposition {
    private org.apache.commons.math.linear.RealMatrix cachedH;
    private org.apache.commons.math.linear.RealMatrix cachedQ;
    private org.apache.commons.math.linear.RealMatrix cachedQT;
    private org.apache.commons.math.linear.RealMatrix cachedR;
    private double[][] qrt;
    private double[] rDiag;

    public QRDecompositionImpl(org.apache.commons.math.linear.RealMatrix realMatrix) {
        int rowDimension = realMatrix.getRowDimension();
        int columnDimension = realMatrix.getColumnDimension();
        this.qrt = realMatrix.transpose().getData();
        this.rDiag = new double[org.apache.commons.math.util.FastMath.min(rowDimension, columnDimension)];
        this.cachedQ = null;
        this.cachedQT = null;
        this.cachedR = null;
        this.cachedH = null;
        for (int i = 0; i < org.apache.commons.math.util.FastMath.min(rowDimension, columnDimension); i++) {
            double[] dArr = this.qrt[i];
            double d = 0.0d;
            for (int i2 = i; i2 < rowDimension; i2++) {
                double d2 = dArr[i2];
                d += d2 * d2;
            }
            double sqrt = dArr[i] > 0.0d ? -org.apache.commons.math.util.FastMath.sqrt(d) : org.apache.commons.math.util.FastMath.sqrt(d);
            this.rDiag[i] = sqrt;
            if (sqrt != 0.0d) {
                dArr[i] = dArr[i] - sqrt;
                for (int i3 = i + 1; i3 < columnDimension; i3++) {
                    double[] dArr2 = this.qrt[i3];
                    double d3 = 0.0d;
                    for (int i4 = i; i4 < rowDimension; i4++) {
                        d3 -= dArr2[i4] * dArr[i4];
                    }
                    double d4 = d3 / (dArr[i] * sqrt);
                    for (int i5 = i; i5 < rowDimension; i5++) {
                        dArr2[i5] = dArr2[i5] - (dArr[i5] * d4);
                    }
                }
            }
        }
    }

    @Override // org.apache.commons.math.linear.QRDecomposition
    public org.apache.commons.math.linear.RealMatrix getR() {
        if (this.cachedR == null) {
            int length = this.qrt.length;
            int length2 = this.qrt[0].length;
            this.cachedR = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length2, length);
            for (int min = org.apache.commons.math.util.FastMath.min(length2, length) - 1; min >= 0; min--) {
                this.cachedR.setEntry(min, min, this.rDiag[min]);
                for (int i = min + 1; i < length; i++) {
                    this.cachedR.setEntry(min, i, this.qrt[i][min]);
                }
            }
        }
        return this.cachedR;
    }

    @Override // org.apache.commons.math.linear.QRDecomposition
    public org.apache.commons.math.linear.RealMatrix getQ() {
        if (this.cachedQ == null) {
            this.cachedQ = getQT().transpose();
        }
        return this.cachedQ;
    }

    @Override // org.apache.commons.math.linear.QRDecomposition
    public org.apache.commons.math.linear.RealMatrix getQT() {
        if (this.cachedQT == null) {
            int length = this.qrt.length;
            int length2 = this.qrt[0].length;
            this.cachedQT = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length2, length2);
            for (int i = length2 - 1; i >= org.apache.commons.math.util.FastMath.min(length2, length); i--) {
                this.cachedQT.setEntry(i, i, 1.0d);
            }
            for (int min = org.apache.commons.math.util.FastMath.min(length2, length) - 1; min >= 0; min--) {
                double[] dArr = this.qrt[min];
                this.cachedQT.setEntry(min, min, 1.0d);
                if (dArr[min] != 0.0d) {
                    for (int i2 = min; i2 < length2; i2++) {
                        double d = 0.0d;
                        for (int i3 = min; i3 < length2; i3++) {
                            d -= this.cachedQT.getEntry(i2, i3) * dArr[i3];
                        }
                        double d2 = d / (this.rDiag[min] * dArr[min]);
                        for (int i4 = min; i4 < length2; i4++) {
                            this.cachedQT.addToEntry(i2, i4, (-d2) * dArr[i4]);
                        }
                    }
                }
            }
        }
        return this.cachedQT;
    }

    @Override // org.apache.commons.math.linear.QRDecomposition
    public org.apache.commons.math.linear.RealMatrix getH() {
        int i;
        if (this.cachedH == null) {
            int length = this.qrt.length;
            int length2 = this.qrt[0].length;
            this.cachedH = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length2, length);
            int i2 = 0;
            while (i2 < length2) {
                int i3 = 0;
                while (true) {
                    i = i2 + 1;
                    if (i3 < org.apache.commons.math.util.FastMath.min(i, length)) {
                        this.cachedH.setEntry(i2, i3, this.qrt[i3][i2] / (-this.rDiag[i3]));
                        i3++;
                    }
                }
                i2 = i;
            }
        }
        return this.cachedH;
    }

    @Override // org.apache.commons.math.linear.QRDecomposition
    public org.apache.commons.math.linear.DecompositionSolver getSolver() {
        return new org.apache.commons.math.linear.QRDecompositionImpl.Solver(this.qrt, this.rDiag);
    }

    private static class Solver implements org.apache.commons.math.linear.DecompositionSolver {
        private final double[][] qrt;
        private final double[] rDiag;

        private Solver(double[][] dArr, double[] dArr2) {
            this.qrt = dArr;
            this.rDiag = dArr2;
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public boolean isNonSingular() {
            for (double d : this.rDiag) {
                if (d == 0.0d) {
                    return false;
                }
            }
            return true;
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public double[] solve(double[] dArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            int length = this.qrt.length;
            int length2 = this.qrt[0].length;
            if (dArr.length != length2) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(length2));
            }
            if (!isNonSingular()) {
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }
            double[] dArr2 = new double[length];
            double[] dArr3 = (double[]) dArr.clone();
            for (int i = 0; i < org.apache.commons.math.util.FastMath.min(length2, length); i++) {
                double[] dArr4 = this.qrt[i];
                double d = 0.0d;
                for (int i2 = i; i2 < length2; i2++) {
                    d += dArr3[i2] * dArr4[i2];
                }
                double d2 = d / (this.rDiag[i] * dArr4[i]);
                for (int i3 = i; i3 < length2; i3++) {
                    dArr3[i3] = dArr3[i3] + (dArr4[i3] * d2);
                }
            }
            for (int length3 = this.rDiag.length - 1; length3 >= 0; length3--) {
                dArr3[length3] = dArr3[length3] / this.rDiag[length3];
                double d3 = dArr3[length3];
                double[] dArr5 = this.qrt[length3];
                dArr2[length3] = d3;
                for (int i4 = 0; i4 < length3; i4++) {
                    dArr3[i4] = dArr3[i4] - (dArr5[i4] * d3);
                }
            }
            return dArr2;
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealVector solve(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            try {
                return solve((org.apache.commons.math.linear.ArrayRealVector) realVector);
            } catch (java.lang.ClassCastException e) {
                return new org.apache.commons.math.linear.ArrayRealVector(solve(realVector.getData()), false);
            }
        }

        public org.apache.commons.math.linear.ArrayRealVector solve(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            return new org.apache.commons.math.linear.ArrayRealVector(solve(arrayRealVector.getDataRef()), false);
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealMatrix solve(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            double d;
            int length = this.qrt.length;
            int i = 0;
            int length2 = this.qrt[0].length;
            if (realMatrix.getRowDimension() != length2) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(realMatrix.getRowDimension()), java.lang.Integer.valueOf(realMatrix.getColumnDimension()), java.lang.Integer.valueOf(length2), "n");
            }
            if (!isNonSingular()) {
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }
            int columnDimension = realMatrix.getColumnDimension();
            int i2 = ((columnDimension + 52) - 1) / 52;
            double[][] createBlocksLayout = org.apache.commons.math.linear.BlockRealMatrix.createBlocksLayout(length, columnDimension);
            double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, realMatrix.getRowDimension(), 52);
            double[] dArr2 = new double[52];
            int i3 = 0;
            while (i3 < i2) {
                int i4 = i3 * 52;
                int min = org.apache.commons.math.util.FastMath.min(i4 + 52, columnDimension);
                int i5 = min - i4;
                int i6 = i3;
                realMatrix.copySubMatrix(0, length2 - 1, i4, min - 1, dArr);
                int i7 = i;
                while (true) {
                    d = 1.0d;
                    if (i7 >= org.apache.commons.math.util.FastMath.min(length2, length)) {
                        break;
                    }
                    double[] dArr3 = this.qrt[i7];
                    double d2 = 1.0d / (this.rDiag[i7] * dArr3[i7]);
                    java.util.Arrays.fill(dArr2, i, i5, 0.0d);
                    int i8 = i7;
                    while (i8 < length2) {
                        double d3 = dArr3[i8];
                        double[] dArr4 = dArr[i8];
                        while (i < i5) {
                            dArr2[i] = dArr2[i] + (dArr4[i] * d3);
                            i++;
                        }
                        i8++;
                        i = 0;
                    }
                    for (int i9 = 0; i9 < i5; i9++) {
                        dArr2[i9] = dArr2[i9] * d2;
                    }
                    for (int i10 = i7; i10 < length2; i10++) {
                        double d4 = dArr3[i10];
                        double[] dArr5 = dArr[i10];
                        for (int i11 = 0; i11 < i5; i11++) {
                            dArr5[i11] = dArr5[i11] + (dArr2[i11] * d4);
                        }
                    }
                    i7++;
                    i = 0;
                }
                int length3 = this.rDiag.length - 1;
                while (length3 >= 0) {
                    int i12 = length3 / 52;
                    int i13 = i12 * 52;
                    double d5 = d / this.rDiag[length3];
                    double[] dArr6 = dArr[length3];
                    double[] dArr7 = createBlocksLayout[(i12 * i2) + i6];
                    int i14 = (length3 - i13) * i5;
                    int i15 = 0;
                    while (i15 < i5) {
                        dArr6[i15] = dArr6[i15] * d5;
                        dArr7[i14] = dArr6[i15];
                        i15++;
                        i14++;
                    }
                    double[] dArr8 = this.qrt[length3];
                    for (int i16 = 0; i16 < length3; i16++) {
                        double d6 = dArr8[i16];
                        double[] dArr9 = dArr[i16];
                        for (int i17 = 0; i17 < i5; i17++) {
                            dArr9[i17] = dArr9[i17] - (dArr6[i17] * d6);
                        }
                    }
                    length3--;
                    d = 1.0d;
                }
                i3 = i6 + 1;
                i = 0;
            }
            return new org.apache.commons.math.linear.BlockRealMatrix(length, columnDimension, createBlocksLayout, false);
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealMatrix getInverse() throws org.apache.commons.math.linear.InvalidMatrixException {
            return solve(org.apache.commons.math.linear.MatrixUtils.createRealIdentityMatrix(this.rDiag.length));
        }
    }
}
