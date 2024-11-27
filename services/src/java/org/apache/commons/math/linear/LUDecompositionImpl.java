package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class LUDecompositionImpl implements org.apache.commons.math.linear.LUDecomposition {
    private static final double DEFAULT_TOO_SMALL = 1.0E-11d;
    private org.apache.commons.math.linear.RealMatrix cachedL;
    private org.apache.commons.math.linear.RealMatrix cachedP;
    private org.apache.commons.math.linear.RealMatrix cachedU;
    private boolean even;
    private double[][] lu;
    private int[] pivot;
    private boolean singular;

    public LUDecompositionImpl(org.apache.commons.math.linear.RealMatrix realMatrix) throws org.apache.commons.math.linear.InvalidMatrixException {
        this(realMatrix, 1.0E-11d);
    }

    public LUDecompositionImpl(org.apache.commons.math.linear.RealMatrix realMatrix, double d) throws org.apache.commons.math.linear.NonSquareMatrixException {
        if (!realMatrix.isSquare()) {
            throw new org.apache.commons.math.linear.NonSquareMatrixException(realMatrix.getRowDimension(), realMatrix.getColumnDimension());
        }
        int columnDimension = realMatrix.getColumnDimension();
        this.lu = realMatrix.getData();
        this.pivot = new int[columnDimension];
        this.cachedL = null;
        this.cachedU = null;
        this.cachedP = null;
        for (int i = 0; i < columnDimension; i++) {
            this.pivot[i] = i;
        }
        this.even = true;
        this.singular = false;
        int i2 = 0;
        while (i2 < columnDimension) {
            for (int i3 = 0; i3 < i2; i3++) {
                double[] dArr = this.lu[i3];
                double d2 = dArr[i2];
                for (int i4 = 0; i4 < i3; i4++) {
                    d2 -= dArr[i4] * this.lu[i4][i2];
                }
                dArr[i2] = d2;
            }
            double d3 = Double.NEGATIVE_INFINITY;
            int i5 = i2;
            int i6 = i5;
            while (i5 < columnDimension) {
                double[] dArr2 = this.lu[i5];
                double d4 = dArr2[i2];
                for (int i7 = 0; i7 < i2; i7++) {
                    d4 -= dArr2[i7] * this.lu[i7][i2];
                }
                dArr2[i2] = d4;
                if (org.apache.commons.math.util.FastMath.abs(d4) > d3) {
                    d3 = org.apache.commons.math.util.FastMath.abs(d4);
                    i6 = i5;
                }
                i5++;
            }
            if (org.apache.commons.math.util.FastMath.abs(this.lu[i6][i2]) < d) {
                this.singular = true;
                return;
            }
            if (i6 != i2) {
                double[] dArr3 = this.lu[i6];
                double[] dArr4 = this.lu[i2];
                for (int i8 = 0; i8 < columnDimension; i8++) {
                    double d5 = dArr3[i8];
                    dArr3[i8] = dArr4[i8];
                    dArr4[i8] = d5;
                }
                int i9 = this.pivot[i6];
                this.pivot[i6] = this.pivot[i2];
                this.pivot[i2] = i9;
                this.even = !this.even;
            }
            double d6 = this.lu[i2][i2];
            int i10 = i2 + 1;
            for (int i11 = i10; i11 < columnDimension; i11++) {
                double[] dArr5 = this.lu[i11];
                dArr5[i2] = dArr5[i2] / d6;
            }
            i2 = i10;
        }
    }

    @Override // org.apache.commons.math.linear.LUDecomposition
    public org.apache.commons.math.linear.RealMatrix getL() {
        if (this.cachedL == null && !this.singular) {
            int length = this.pivot.length;
            this.cachedL = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length, length);
            for (int i = 0; i < length; i++) {
                double[] dArr = this.lu[i];
                for (int i2 = 0; i2 < i; i2++) {
                    this.cachedL.setEntry(i, i2, dArr[i2]);
                }
                this.cachedL.setEntry(i, i, 1.0d);
            }
        }
        return this.cachedL;
    }

    @Override // org.apache.commons.math.linear.LUDecomposition
    public org.apache.commons.math.linear.RealMatrix getU() {
        if (this.cachedU == null && !this.singular) {
            int length = this.pivot.length;
            this.cachedU = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length, length);
            for (int i = 0; i < length; i++) {
                double[] dArr = this.lu[i];
                for (int i2 = i; i2 < length; i2++) {
                    this.cachedU.setEntry(i, i2, dArr[i2]);
                }
            }
        }
        return this.cachedU;
    }

    @Override // org.apache.commons.math.linear.LUDecomposition
    public org.apache.commons.math.linear.RealMatrix getP() {
        if (this.cachedP == null && !this.singular) {
            int length = this.pivot.length;
            this.cachedP = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length, length);
            for (int i = 0; i < length; i++) {
                this.cachedP.setEntry(i, this.pivot[i], 1.0d);
            }
        }
        return this.cachedP;
    }

    @Override // org.apache.commons.math.linear.LUDecomposition
    public int[] getPivot() {
        return (int[]) this.pivot.clone();
    }

    @Override // org.apache.commons.math.linear.LUDecomposition
    public double getDeterminant() {
        if (this.singular) {
            return 0.0d;
        }
        int length = this.pivot.length;
        double d = this.even ? 1.0d : -1.0d;
        for (int i = 0; i < length; i++) {
            d *= this.lu[i][i];
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.LUDecomposition
    public org.apache.commons.math.linear.DecompositionSolver getSolver() {
        return new org.apache.commons.math.linear.LUDecompositionImpl.Solver(this.lu, this.pivot, this.singular);
    }

    private static class Solver implements org.apache.commons.math.linear.DecompositionSolver {
        private final double[][] lu;
        private final int[] pivot;
        private final boolean singular;

        private Solver(double[][] dArr, int[] iArr, boolean z) {
            this.lu = dArr;
            this.pivot = iArr;
            this.singular = z;
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public boolean isNonSingular() {
            return !this.singular;
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public double[] solve(double[] dArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            int length = this.pivot.length;
            if (dArr.length != length) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(length));
            }
            if (this.singular) {
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }
            double[] dArr2 = new double[length];
            for (int i = 0; i < length; i++) {
                dArr2[i] = dArr[this.pivot[i]];
            }
            int i2 = 0;
            while (i2 < length) {
                double d = dArr2[i2];
                int i3 = i2 + 1;
                for (int i4 = i3; i4 < length; i4++) {
                    dArr2[i4] = dArr2[i4] - (this.lu[i4][i2] * d);
                }
                i2 = i3;
            }
            for (int i5 = length - 1; i5 >= 0; i5--) {
                dArr2[i5] = dArr2[i5] / this.lu[i5][i5];
                double d2 = dArr2[i5];
                for (int i6 = 0; i6 < i5; i6++) {
                    dArr2[i6] = dArr2[i6] - (this.lu[i6][i5] * d2);
                }
            }
            return dArr2;
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealVector solve(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            try {
                return solve((org.apache.commons.math.linear.ArrayRealVector) realVector);
            } catch (java.lang.ClassCastException e) {
                int length = this.pivot.length;
                if (realVector.getDimension() != length) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(realVector.getDimension()), java.lang.Integer.valueOf(length));
                }
                if (this.singular) {
                    throw new org.apache.commons.math.linear.SingularMatrixException();
                }
                double[] dArr = new double[length];
                for (int i = 0; i < length; i++) {
                    dArr[i] = realVector.getEntry(this.pivot[i]);
                }
                int i2 = 0;
                while (i2 < length) {
                    double d = dArr[i2];
                    int i3 = i2 + 1;
                    for (int i4 = i3; i4 < length; i4++) {
                        dArr[i4] = dArr[i4] - (this.lu[i4][i2] * d);
                    }
                    i2 = i3;
                }
                for (int i5 = length - 1; i5 >= 0; i5--) {
                    dArr[i5] = dArr[i5] / this.lu[i5][i5];
                    double d2 = dArr[i5];
                    for (int i6 = 0; i6 < i5; i6++) {
                        dArr[i6] = dArr[i6] - (this.lu[i6][i5] * d2);
                    }
                }
                return new org.apache.commons.math.linear.ArrayRealVector(dArr, false);
            }
        }

        public org.apache.commons.math.linear.ArrayRealVector solve(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            return new org.apache.commons.math.linear.ArrayRealVector(solve(arrayRealVector.getDataRef()), false);
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealMatrix solve(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            int length = this.pivot.length;
            if (realMatrix.getRowDimension() != length) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(realMatrix.getRowDimension()), java.lang.Integer.valueOf(realMatrix.getColumnDimension()), java.lang.Integer.valueOf(length), "n");
            }
            if (this.singular) {
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }
            int columnDimension = realMatrix.getColumnDimension();
            double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, columnDimension);
            for (int i = 0; i < length; i++) {
                double[] dArr2 = dArr[i];
                int i2 = this.pivot[i];
                for (int i3 = 0; i3 < columnDimension; i3++) {
                    dArr2[i3] = realMatrix.getEntry(i2, i3);
                }
            }
            int i4 = 0;
            while (i4 < length) {
                double[] dArr3 = dArr[i4];
                int i5 = i4 + 1;
                for (int i6 = i5; i6 < length; i6++) {
                    double[] dArr4 = dArr[i6];
                    double d = this.lu[i6][i4];
                    for (int i7 = 0; i7 < columnDimension; i7++) {
                        dArr4[i7] = dArr4[i7] - (dArr3[i7] * d);
                    }
                }
                i4 = i5;
            }
            for (int i8 = length - 1; i8 >= 0; i8--) {
                double[] dArr5 = dArr[i8];
                double d2 = this.lu[i8][i8];
                for (int i9 = 0; i9 < columnDimension; i9++) {
                    dArr5[i9] = dArr5[i9] / d2;
                }
                for (int i10 = 0; i10 < i8; i10++) {
                    double[] dArr6 = dArr[i10];
                    double d3 = this.lu[i10][i8];
                    for (int i11 = 0; i11 < columnDimension; i11++) {
                        dArr6[i11] = dArr6[i11] - (dArr5[i11] * d3);
                    }
                }
            }
            return new org.apache.commons.math.linear.Array2DRowRealMatrix(dArr, false);
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealMatrix getInverse() throws org.apache.commons.math.linear.InvalidMatrixException {
            return solve(org.apache.commons.math.linear.MatrixUtils.createRealIdentityMatrix(this.pivot.length));
        }
    }
}
