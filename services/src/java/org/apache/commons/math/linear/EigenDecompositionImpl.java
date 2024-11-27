package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class EigenDecompositionImpl implements org.apache.commons.math.linear.EigenDecomposition {
    private org.apache.commons.math.linear.RealMatrix cachedD;
    private org.apache.commons.math.linear.RealMatrix cachedV;
    private org.apache.commons.math.linear.RealMatrix cachedVt;
    private org.apache.commons.math.linear.ArrayRealVector[] eigenvectors;
    private double[] imagEigenvalues;
    private double[] main;
    private byte maxIter;
    private double[] realEigenvalues;
    private double[] secondary;
    private org.apache.commons.math.linear.TriDiagonalTransformer transformer;

    public EigenDecompositionImpl(org.apache.commons.math.linear.RealMatrix realMatrix, double d) throws org.apache.commons.math.linear.InvalidMatrixException {
        this.maxIter = (byte) 30;
        if (isSymmetric(realMatrix)) {
            transformToTridiagonal(realMatrix);
            findEigenVectors(this.transformer.getQ().getData());
            return;
        }
        throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.ASSYMETRIC_EIGEN_NOT_SUPPORTED, new java.lang.Object[0]);
    }

    public EigenDecompositionImpl(double[] dArr, double[] dArr2, double d) throws org.apache.commons.math.linear.InvalidMatrixException {
        this.maxIter = (byte) 30;
        this.main = (double[]) dArr.clone();
        this.secondary = (double[]) dArr2.clone();
        this.transformer = null;
        int length = dArr.length;
        double[][] dArr3 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length);
        for (int i = 0; i < length; i++) {
            dArr3[i][i] = 1.0d;
        }
        findEigenVectors(dArr3);
    }

    private boolean isSymmetric(org.apache.commons.math.linear.RealMatrix realMatrix) {
        int rowDimension = realMatrix.getRowDimension();
        int columnDimension = realMatrix.getColumnDimension();
        double d = rowDimension * 10 * columnDimension * 1.1102230246251565E-16d;
        int i = 0;
        while (i < rowDimension) {
            int i2 = i + 1;
            for (int i3 = i2; i3 < columnDimension; i3++) {
                double entry = realMatrix.getEntry(i, i3);
                double entry2 = realMatrix.getEntry(i3, i);
                if (org.apache.commons.math.util.FastMath.abs(entry - entry2) > org.apache.commons.math.util.FastMath.max(org.apache.commons.math.util.FastMath.abs(entry), org.apache.commons.math.util.FastMath.abs(entry2)) * d) {
                    return false;
                }
            }
            i = i2;
        }
        return true;
    }

    @Override // org.apache.commons.math.linear.EigenDecomposition
    public org.apache.commons.math.linear.RealMatrix getV() throws org.apache.commons.math.linear.InvalidMatrixException {
        if (this.cachedV == null) {
            int length = this.eigenvectors.length;
            this.cachedV = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length, length);
            for (int i = 0; i < length; i++) {
                this.cachedV.setColumnVector(i, this.eigenvectors[i]);
            }
        }
        return this.cachedV;
    }

    @Override // org.apache.commons.math.linear.EigenDecomposition
    public org.apache.commons.math.linear.RealMatrix getD() throws org.apache.commons.math.linear.InvalidMatrixException {
        if (this.cachedD == null) {
            this.cachedD = org.apache.commons.math.linear.MatrixUtils.createRealDiagonalMatrix(this.realEigenvalues);
        }
        return this.cachedD;
    }

    @Override // org.apache.commons.math.linear.EigenDecomposition
    public org.apache.commons.math.linear.RealMatrix getVT() throws org.apache.commons.math.linear.InvalidMatrixException {
        if (this.cachedVt == null) {
            int length = this.eigenvectors.length;
            this.cachedVt = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length, length);
            for (int i = 0; i < length; i++) {
                this.cachedVt.setRowVector(i, this.eigenvectors[i]);
            }
        }
        return this.cachedVt;
    }

    @Override // org.apache.commons.math.linear.EigenDecomposition
    public double[] getRealEigenvalues() throws org.apache.commons.math.linear.InvalidMatrixException {
        return (double[]) this.realEigenvalues.clone();
    }

    @Override // org.apache.commons.math.linear.EigenDecomposition
    public double getRealEigenvalue(int i) throws org.apache.commons.math.linear.InvalidMatrixException, java.lang.ArrayIndexOutOfBoundsException {
        return this.realEigenvalues[i];
    }

    @Override // org.apache.commons.math.linear.EigenDecomposition
    public double[] getImagEigenvalues() throws org.apache.commons.math.linear.InvalidMatrixException {
        return (double[]) this.imagEigenvalues.clone();
    }

    @Override // org.apache.commons.math.linear.EigenDecomposition
    public double getImagEigenvalue(int i) throws org.apache.commons.math.linear.InvalidMatrixException, java.lang.ArrayIndexOutOfBoundsException {
        return this.imagEigenvalues[i];
    }

    @Override // org.apache.commons.math.linear.EigenDecomposition
    public org.apache.commons.math.linear.RealVector getEigenvector(int i) throws org.apache.commons.math.linear.InvalidMatrixException, java.lang.ArrayIndexOutOfBoundsException {
        return this.eigenvectors[i].copy();
    }

    @Override // org.apache.commons.math.linear.EigenDecomposition
    public double getDeterminant() {
        double d = 1.0d;
        for (double d2 : this.realEigenvalues) {
            d *= d2;
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.EigenDecomposition
    public org.apache.commons.math.linear.DecompositionSolver getSolver() {
        return new org.apache.commons.math.linear.EigenDecompositionImpl.Solver(this.realEigenvalues, this.imagEigenvalues, this.eigenvectors);
    }

    private static class Solver implements org.apache.commons.math.linear.DecompositionSolver {
        private final org.apache.commons.math.linear.ArrayRealVector[] eigenvectors;
        private double[] imagEigenvalues;
        private double[] realEigenvalues;

        private Solver(double[] dArr, double[] dArr2, org.apache.commons.math.linear.ArrayRealVector[] arrayRealVectorArr) {
            this.realEigenvalues = dArr;
            this.imagEigenvalues = dArr2;
            this.eigenvectors = arrayRealVectorArr;
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public double[] solve(double[] dArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            if (!isNonSingular()) {
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }
            int length = this.realEigenvalues.length;
            if (dArr.length != length) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(length));
            }
            double[] dArr2 = new double[length];
            for (int i = 0; i < length; i++) {
                org.apache.commons.math.linear.ArrayRealVector arrayRealVector = this.eigenvectors[i];
                double[] dataRef = arrayRealVector.getDataRef();
                double dotProduct = arrayRealVector.dotProduct(dArr) / this.realEigenvalues[i];
                for (int i2 = 0; i2 < length; i2++) {
                    dArr2[i2] = dArr2[i2] + (dataRef[i2] * dotProduct);
                }
            }
            return dArr2;
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealVector solve(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            if (!isNonSingular()) {
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }
            int length = this.realEigenvalues.length;
            if (realVector.getDimension() != length) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(realVector.getDimension()), java.lang.Integer.valueOf(length));
            }
            double[] dArr = new double[length];
            for (int i = 0; i < length; i++) {
                org.apache.commons.math.linear.ArrayRealVector arrayRealVector = this.eigenvectors[i];
                double[] dataRef = arrayRealVector.getDataRef();
                double dotProduct = arrayRealVector.dotProduct(realVector) / this.realEigenvalues[i];
                for (int i2 = 0; i2 < length; i2++) {
                    dArr[i2] = dArr[i2] + (dataRef[i2] * dotProduct);
                }
            }
            return new org.apache.commons.math.linear.ArrayRealVector(dArr, false);
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealMatrix solve(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            if (!isNonSingular()) {
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }
            int length = this.realEigenvalues.length;
            if (realMatrix.getRowDimension() != length) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(realMatrix.getRowDimension()), java.lang.Integer.valueOf(realMatrix.getColumnDimension()), java.lang.Integer.valueOf(length), "n");
            }
            int columnDimension = realMatrix.getColumnDimension();
            double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, columnDimension);
            for (int i = 0; i < columnDimension; i++) {
                for (int i2 = 0; i2 < length; i2++) {
                    org.apache.commons.math.linear.ArrayRealVector arrayRealVector = this.eigenvectors[i2];
                    double[] dataRef = arrayRealVector.getDataRef();
                    double d = 0.0d;
                    for (int i3 = 0; i3 < length; i3++) {
                        d += arrayRealVector.getEntry(i3) * realMatrix.getEntry(i3, i);
                    }
                    double d2 = d / this.realEigenvalues[i2];
                    for (int i4 = 0; i4 < length; i4++) {
                        double[] dArr2 = dArr[i4];
                        dArr2[i] = dArr2[i] + (dataRef[i4] * d2);
                    }
                }
            }
            return org.apache.commons.math.linear.MatrixUtils.createRealMatrix(dArr);
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public boolean isNonSingular() {
            for (int i = 0; i < this.realEigenvalues.length; i++) {
                if (this.realEigenvalues[i] == 0.0d && this.imagEigenvalues[i] == 0.0d) {
                    return false;
                }
            }
            return true;
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealMatrix getInverse() throws org.apache.commons.math.linear.InvalidMatrixException {
            if (!isNonSingular()) {
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }
            int length = this.realEigenvalues.length;
            double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, length);
            for (int i = 0; i < length; i++) {
                double[] dArr2 = dArr[i];
                for (int i2 = 0; i2 < length; i2++) {
                    double d = 0.0d;
                    for (int i3 = 0; i3 < length; i3++) {
                        double[] dataRef = this.eigenvectors[i3].getDataRef();
                        d += (dataRef[i] * dataRef[i2]) / this.realEigenvalues[i3];
                    }
                    dArr2[i2] = d;
                }
            }
            return org.apache.commons.math.linear.MatrixUtils.createRealMatrix(dArr);
        }
    }

    private void transformToTridiagonal(org.apache.commons.math.linear.RealMatrix realMatrix) {
        this.transformer = new org.apache.commons.math.linear.TriDiagonalTransformer(realMatrix);
        this.main = this.transformer.getMainDiagonalRef();
        this.secondary = this.transformer.getSecondaryDiagonalRef();
    }

    /* JADX WARN: Code restructure failed: missing block: B:75:0x01d9, code lost:
    
        r5 = r5 + 1;
        r7 = 0.0d;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void findEigenVectors(double[][] dArr) {
        int i;
        double d;
        double[][] dArr2 = (double[][]) dArr.clone();
        int length = this.main.length;
        this.realEigenvalues = new double[length];
        this.imagEigenvalues = new double[length];
        double[] dArr3 = new double[length];
        int i2 = 0;
        while (true) {
            i = length - 1;
            if (i2 >= i) {
                break;
            }
            this.realEigenvalues[i2] = this.main[i2];
            dArr3[i2] = this.secondary[i2];
            i2++;
        }
        this.realEigenvalues[i] = this.main[i];
        double d2 = 0.0d;
        dArr3[i] = 0.0d;
        double d3 = 0.0d;
        for (int i3 = 0; i3 < length; i3++) {
            if (org.apache.commons.math.util.FastMath.abs(this.realEigenvalues[i3]) > d3) {
                d3 = org.apache.commons.math.util.FastMath.abs(this.realEigenvalues[i3]);
            }
            if (org.apache.commons.math.util.FastMath.abs(dArr3[i3]) > d3) {
                d3 = org.apache.commons.math.util.FastMath.abs(dArr3[i3]);
            }
        }
        if (d3 != 0.0d) {
            for (int i4 = 0; i4 < length; i4++) {
                double d4 = d3 * 1.1102230246251565E-16d;
                if (org.apache.commons.math.util.FastMath.abs(this.realEigenvalues[i4]) <= d4) {
                    this.realEigenvalues[i4] = 0.0d;
                }
                if (org.apache.commons.math.util.FastMath.abs(dArr3[i4]) <= d4) {
                    dArr3[i4] = 0.0d;
                }
            }
        }
        int i5 = 0;
        while (i5 < length) {
            int i6 = 0;
            while (true) {
                int i7 = i5;
                while (i7 < i) {
                    int i8 = i7 + 1;
                    double abs = org.apache.commons.math.util.FastMath.abs(this.realEigenvalues[i7]) + org.apache.commons.math.util.FastMath.abs(this.realEigenvalues[i8]);
                    if (org.apache.commons.math.util.FastMath.abs(dArr3[i7]) + abs == abs) {
                        break;
                    } else {
                        i7 = i8;
                    }
                }
                if (i7 != i5) {
                    if (i6 == this.maxIter) {
                        throw new org.apache.commons.math.linear.InvalidMatrixException(new org.apache.commons.math.MaxIterationsExceededException(this.maxIter));
                    }
                    i6++;
                    double d5 = (this.realEigenvalues[i5 + 1] - this.realEigenvalues[i5]) / (dArr3[i5] * 2.0d);
                    double sqrt = org.apache.commons.math.util.FastMath.sqrt((d5 * d5) + 1.0d);
                    double d6 = d5 < d2 ? (this.realEigenvalues[i7] - this.realEigenvalues[i5]) + (dArr3[i5] / (d5 - sqrt)) : (this.realEigenvalues[i7] - this.realEigenvalues[i5]) + (dArr3[i5] / (d5 + sqrt));
                    int i9 = i7 - 1;
                    double d7 = d2;
                    double d8 = 1.0d;
                    double d9 = 1.0d;
                    while (true) {
                        if (i9 < i5) {
                            break;
                        }
                        double d10 = d8 * dArr3[i9];
                        double d11 = d9 * dArr3[i9];
                        if (org.apache.commons.math.util.FastMath.abs(d10) >= org.apache.commons.math.util.FastMath.abs(d6)) {
                            double d12 = d6 / d10;
                            double sqrt2 = org.apache.commons.math.util.FastMath.sqrt((d12 * d12) + 1.0d);
                            dArr3[i9 + 1] = d10 * sqrt2;
                            d8 = 1.0d / sqrt2;
                            double d13 = d12 * d8;
                            sqrt = sqrt2;
                            d = d13;
                        } else {
                            double d14 = d10 / d6;
                            sqrt = org.apache.commons.math.util.FastMath.sqrt((d14 * d14) + 1.0d);
                            dArr3[i9 + 1] = d6 * sqrt;
                            d = 1.0d / sqrt;
                            d8 = d14 * d;
                        }
                        int i10 = i9 + 1;
                        if (dArr3[i10] == d2) {
                            double[] dArr4 = this.realEigenvalues;
                            dArr4[i10] = dArr4[i10] - d7;
                            dArr3[i7] = d2;
                            break;
                        }
                        double d15 = this.realEigenvalues[i10] - d7;
                        double d16 = ((this.realEigenvalues[i9] - d15) * d8) + (d * 2.0d * d11);
                        d7 = d8 * d16;
                        this.realEigenvalues[i10] = d15 + d7;
                        d6 = (d * d16) - d11;
                        for (int i11 = 0; i11 < length; i11++) {
                            double d17 = dArr2[i11][i10];
                            dArr2[i11][i10] = (dArr2[i11][i9] * d8) + (d * d17);
                            dArr2[i11][i9] = (dArr2[i11][i9] * d) - (d17 * d8);
                        }
                        i9--;
                        sqrt = d16;
                        d9 = d;
                        d2 = 0.0d;
                    }
                    if (sqrt != 0.0d || i9 < i5) {
                        double[] dArr5 = this.realEigenvalues;
                        dArr5[i5] = dArr5[i5] - d7;
                        dArr3[i5] = d6;
                        dArr3[i7] = 0.0d;
                    }
                }
                if (i7 == i5) {
                    break;
                } else {
                    d2 = 0.0d;
                }
            }
        }
        int i12 = 0;
        while (i12 < length) {
            double d18 = this.realEigenvalues[i12];
            int i13 = i12 + 1;
            int i14 = i12;
            for (int i15 = i13; i15 < length; i15++) {
                if (this.realEigenvalues[i15] > d18) {
                    d18 = this.realEigenvalues[i15];
                    i14 = i15;
                }
            }
            if (i14 != i12) {
                this.realEigenvalues[i14] = this.realEigenvalues[i12];
                this.realEigenvalues[i12] = d18;
                for (int i16 = 0; i16 < length; i16++) {
                    double d19 = dArr2[i16][i12];
                    dArr2[i16][i12] = dArr2[i16][i14];
                    dArr2[i16][i14] = d19;
                }
            }
            i12 = i13;
        }
        double d20 = 0.0d;
        for (int i17 = 0; i17 < length; i17++) {
            if (org.apache.commons.math.util.FastMath.abs(this.realEigenvalues[i17]) > d20) {
                d20 = org.apache.commons.math.util.FastMath.abs(this.realEigenvalues[i17]);
            }
        }
        if (d20 != 0.0d) {
            for (int i18 = 0; i18 < length; i18++) {
                if (org.apache.commons.math.util.FastMath.abs(this.realEigenvalues[i18]) < d20 * 1.1102230246251565E-16d) {
                    this.realEigenvalues[i18] = 0.0d;
                }
            }
        }
        this.eigenvectors = new org.apache.commons.math.linear.ArrayRealVector[length];
        double[] dArr6 = new double[length];
        for (int i19 = 0; i19 < length; i19++) {
            for (int i20 = 0; i20 < length; i20++) {
                dArr6[i20] = dArr2[i20][i19];
            }
            this.eigenvectors[i19] = new org.apache.commons.math.linear.ArrayRealVector(dArr6);
        }
    }
}
