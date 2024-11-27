package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class CholeskyDecompositionImpl implements org.apache.commons.math.linear.CholeskyDecomposition {
    public static final double DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD = 1.0E-10d;
    public static final double DEFAULT_RELATIVE_SYMMETRY_THRESHOLD = 1.0E-15d;
    private org.apache.commons.math.linear.RealMatrix cachedL;
    private org.apache.commons.math.linear.RealMatrix cachedLT;
    private double[][] lTData;

    public CholeskyDecompositionImpl(org.apache.commons.math.linear.RealMatrix realMatrix) throws org.apache.commons.math.linear.NonSquareMatrixException, org.apache.commons.math.linear.NotSymmetricMatrixException, org.apache.commons.math.linear.NotPositiveDefiniteMatrixException {
        this(realMatrix, 1.0E-15d, 1.0E-10d);
    }

    public CholeskyDecompositionImpl(org.apache.commons.math.linear.RealMatrix realMatrix, double d, double d2) throws org.apache.commons.math.linear.NonSquareMatrixException, org.apache.commons.math.linear.NotSymmetricMatrixException, org.apache.commons.math.linear.NotPositiveDefiniteMatrixException {
        if (!realMatrix.isSquare()) {
            throw new org.apache.commons.math.linear.NonSquareMatrixException(realMatrix.getRowDimension(), realMatrix.getColumnDimension());
        }
        int rowDimension = realMatrix.getRowDimension();
        this.lTData = realMatrix.getData();
        this.cachedL = null;
        this.cachedLT = null;
        int i = 0;
        while (i < rowDimension) {
            double[] dArr = this.lTData[i];
            int i2 = i + 1;
            for (int i3 = i2; i3 < rowDimension; i3++) {
                double[] dArr2 = this.lTData[i3];
                double d3 = dArr[i3];
                double d4 = dArr2[i];
                if (org.apache.commons.math.util.FastMath.abs(d3 - d4) > org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.abs(d3), org.apache.commons.math.util.FastMath.abs(d4)) * d) {
                    throw new org.apache.commons.math.linear.NotSymmetricMatrixException();
                }
                dArr2[i] = 0.0d;
            }
            i = i2;
        }
        for (int i4 = 0; i4 < rowDimension; i4++) {
            double[] dArr3 = this.lTData[i4];
            if (dArr3[i4] < d2) {
                throw new org.apache.commons.math.linear.NotPositiveDefiniteMatrixException();
            }
            dArr3[i4] = org.apache.commons.math.util.FastMath.sqrt(dArr3[i4]);
            double d5 = 1.0d / dArr3[i4];
            for (int i5 = rowDimension - 1; i5 > i4; i5--) {
                dArr3[i5] = dArr3[i5] * d5;
                double[] dArr4 = this.lTData[i5];
                for (int i6 = i5; i6 < rowDimension; i6++) {
                    dArr4[i6] = dArr4[i6] - (dArr3[i5] * dArr3[i6]);
                }
            }
        }
    }

    @Override // org.apache.commons.math.linear.CholeskyDecomposition
    public org.apache.commons.math.linear.RealMatrix getL() {
        if (this.cachedL == null) {
            this.cachedL = getLT().transpose();
        }
        return this.cachedL;
    }

    @Override // org.apache.commons.math.linear.CholeskyDecomposition
    public org.apache.commons.math.linear.RealMatrix getLT() {
        if (this.cachedLT == null) {
            this.cachedLT = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(this.lTData);
        }
        return this.cachedLT;
    }

    @Override // org.apache.commons.math.linear.CholeskyDecomposition
    public double getDeterminant() {
        double d = 1.0d;
        for (int i = 0; i < this.lTData.length; i++) {
            double d2 = this.lTData[i][i];
            d *= d2 * d2;
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.CholeskyDecomposition
    public org.apache.commons.math.linear.DecompositionSolver getSolver() {
        return new org.apache.commons.math.linear.CholeskyDecompositionImpl.Solver(this.lTData);
    }

    private static class Solver implements org.apache.commons.math.linear.DecompositionSolver {
        private final double[][] lTData;

        private Solver(double[][] dArr) {
            this.lTData = dArr;
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public boolean isNonSingular() {
            return true;
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public double[] solve(double[] dArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            int length = this.lTData.length;
            if (dArr.length != length) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(length));
            }
            double[] dArr2 = (double[]) dArr.clone();
            int i = 0;
            while (i < length) {
                double[] dArr3 = this.lTData[i];
                dArr2[i] = dArr2[i] / dArr3[i];
                double d = dArr2[i];
                i++;
                for (int i2 = i; i2 < length; i2++) {
                    dArr2[i2] = dArr2[i2] - (dArr3[i2] * d);
                }
            }
            for (int i3 = length - 1; i3 >= 0; i3--) {
                dArr2[i3] = dArr2[i3] / this.lTData[i3][i3];
                double d2 = dArr2[i3];
                for (int i4 = 0; i4 < i3; i4++) {
                    dArr2[i4] = dArr2[i4] - (this.lTData[i4][i3] * d2);
                }
            }
            return dArr2;
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealVector solve(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            try {
                return solve((org.apache.commons.math.linear.ArrayRealVector) realVector);
            } catch (java.lang.ClassCastException e) {
                int length = this.lTData.length;
                if (realVector.getDimension() != length) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(realVector.getDimension()), java.lang.Integer.valueOf(length));
                }
                double[] data = realVector.getData();
                int i = 0;
                while (i < length) {
                    double[] dArr = this.lTData[i];
                    data[i] = data[i] / dArr[i];
                    double d = data[i];
                    i++;
                    for (int i2 = i; i2 < length; i2++) {
                        data[i2] = data[i2] - (dArr[i2] * d);
                    }
                }
                for (int i3 = length - 1; i3 >= 0; i3--) {
                    data[i3] = data[i3] / this.lTData[i3][i3];
                    double d2 = data[i3];
                    for (int i4 = 0; i4 < i3; i4++) {
                        data[i4] = data[i4] - (this.lTData[i4][i3] * d2);
                    }
                }
                return new org.apache.commons.math.linear.ArrayRealVector(data, false);
            }
        }

        public org.apache.commons.math.linear.ArrayRealVector solve(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            return new org.apache.commons.math.linear.ArrayRealVector(solve(arrayRealVector.getDataRef()), false);
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealMatrix solve(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            int length = this.lTData.length;
            if (realMatrix.getRowDimension() != length) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(realMatrix.getRowDimension()), java.lang.Integer.valueOf(realMatrix.getColumnDimension()), java.lang.Integer.valueOf(length), "n");
            }
            int columnDimension = realMatrix.getColumnDimension();
            double[][] data = realMatrix.getData();
            int i = 0;
            while (i < length) {
                double[] dArr = this.lTData[i];
                double d = dArr[i];
                double[] dArr2 = data[i];
                for (int i2 = 0; i2 < columnDimension; i2++) {
                    dArr2[i2] = dArr2[i2] / d;
                }
                i++;
                for (int i3 = i; i3 < length; i3++) {
                    double[] dArr3 = data[i3];
                    double d2 = dArr[i3];
                    for (int i4 = 0; i4 < columnDimension; i4++) {
                        dArr3[i4] = dArr3[i4] - (dArr2[i4] * d2);
                    }
                }
            }
            for (int i5 = length - 1; i5 >= 0; i5--) {
                double d3 = this.lTData[i5][i5];
                double[] dArr4 = data[i5];
                for (int i6 = 0; i6 < columnDimension; i6++) {
                    dArr4[i6] = dArr4[i6] / d3;
                }
                for (int i7 = 0; i7 < i5; i7++) {
                    double[] dArr5 = data[i7];
                    double d4 = this.lTData[i7][i5];
                    for (int i8 = 0; i8 < columnDimension; i8++) {
                        dArr5[i8] = dArr5[i8] - (dArr4[i8] * d4);
                    }
                }
            }
            return new org.apache.commons.math.linear.Array2DRowRealMatrix(data, false);
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealMatrix getInverse() throws org.apache.commons.math.linear.InvalidMatrixException {
            return solve(org.apache.commons.math.linear.MatrixUtils.createRealIdentityMatrix(this.lTData.length));
        }
    }
}
