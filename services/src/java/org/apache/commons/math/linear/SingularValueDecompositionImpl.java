package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class SingularValueDecompositionImpl implements org.apache.commons.math.linear.SingularValueDecomposition {
    private org.apache.commons.math.linear.RealMatrix cachedU;
    private org.apache.commons.math.linear.RealMatrix cachedUt;
    private org.apache.commons.math.linear.RealMatrix cachedV;
    private org.apache.commons.math.linear.EigenDecomposition eigenDecomposition;
    private int m;
    private int n;
    private double[] singularValues;
    private org.apache.commons.math.linear.RealMatrix cachedS = null;
    private org.apache.commons.math.linear.RealMatrix cachedVt = null;

    public SingularValueDecompositionImpl(org.apache.commons.math.linear.RealMatrix realMatrix) throws org.apache.commons.math.linear.InvalidMatrixException {
        int i;
        this.m = realMatrix.getRowDimension();
        this.n = realMatrix.getColumnDimension();
        this.cachedU = null;
        this.cachedV = null;
        double[][] data = realMatrix.getData();
        double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, this.n, this.n);
        for (int i2 = 0; i2 < this.n; i2++) {
            for (int i3 = i2; i3 < this.n; i3++) {
                dArr[i2][i3] = 0.0d;
                for (int i4 = 0; i4 < this.m; i4++) {
                    double[] dArr2 = dArr[i2];
                    dArr2[i3] = dArr2[i3] + (data[i4][i2] * data[i4][i3]);
                }
                dArr[i3][i2] = dArr[i2][i3];
            }
        }
        double[][] dArr3 = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, this.m, this.m);
        for (int i5 = 0; i5 < this.m; i5++) {
            for (int i6 = i5; i6 < this.m; i6++) {
                dArr3[i5][i6] = 0.0d;
                for (int i7 = 0; i7 < this.n; i7++) {
                    double[] dArr4 = dArr3[i5];
                    dArr4[i6] = dArr4[i6] + (data[i5][i7] * data[i6][i7]);
                }
                dArr3[i6][i5] = dArr3[i5][i6];
            }
        }
        if (this.m >= this.n) {
            i = this.n;
            this.eigenDecomposition = new org.apache.commons.math.linear.EigenDecompositionImpl(new org.apache.commons.math.linear.Array2DRowRealMatrix(dArr), 1.0d);
            this.singularValues = this.eigenDecomposition.getRealEigenvalues();
            this.cachedV = this.eigenDecomposition.getV();
            this.eigenDecomposition = new org.apache.commons.math.linear.EigenDecompositionImpl(new org.apache.commons.math.linear.Array2DRowRealMatrix(dArr3), 1.0d);
            this.cachedU = this.eigenDecomposition.getV().getSubMatrix(0, this.m - 1, 0, i - 1);
        } else {
            i = this.m;
            this.eigenDecomposition = new org.apache.commons.math.linear.EigenDecompositionImpl(new org.apache.commons.math.linear.Array2DRowRealMatrix(dArr3), 1.0d);
            this.singularValues = this.eigenDecomposition.getRealEigenvalues();
            this.cachedU = this.eigenDecomposition.getV();
            this.eigenDecomposition = new org.apache.commons.math.linear.EigenDecompositionImpl(new org.apache.commons.math.linear.Array2DRowRealMatrix(dArr), 1.0d);
            this.cachedV = this.eigenDecomposition.getV().getSubMatrix(0, this.n - 1, 0, i - 1);
        }
        for (int i8 = 0; i8 < i; i8++) {
            this.singularValues[i8] = org.apache.commons.math.util.FastMath.sqrt(org.apache.commons.math.util.FastMath.abs(this.singularValues[i8]));
        }
        for (int i9 = 0; i9 < i; i9++) {
            org.apache.commons.math.linear.RealVector columnVector = this.cachedU.getColumnVector(i9);
            if (realMatrix.operate(this.cachedV.getColumnVector(i9)).dotProduct(columnVector) < 0.0d) {
                this.cachedU.setColumnVector(i9, columnVector.mapMultiply(-1.0d));
            }
        }
    }

    @Override // org.apache.commons.math.linear.SingularValueDecomposition
    public org.apache.commons.math.linear.RealMatrix getU() throws org.apache.commons.math.linear.InvalidMatrixException {
        return this.cachedU;
    }

    @Override // org.apache.commons.math.linear.SingularValueDecomposition
    public org.apache.commons.math.linear.RealMatrix getUT() throws org.apache.commons.math.linear.InvalidMatrixException {
        if (this.cachedUt == null) {
            this.cachedUt = getU().transpose();
        }
        return this.cachedUt;
    }

    @Override // org.apache.commons.math.linear.SingularValueDecomposition
    public org.apache.commons.math.linear.RealMatrix getS() throws org.apache.commons.math.linear.InvalidMatrixException {
        if (this.cachedS == null) {
            this.cachedS = org.apache.commons.math.linear.MatrixUtils.createRealDiagonalMatrix(this.singularValues);
        }
        return this.cachedS;
    }

    @Override // org.apache.commons.math.linear.SingularValueDecomposition
    public double[] getSingularValues() throws org.apache.commons.math.linear.InvalidMatrixException {
        return (double[]) this.singularValues.clone();
    }

    @Override // org.apache.commons.math.linear.SingularValueDecomposition
    public org.apache.commons.math.linear.RealMatrix getV() throws org.apache.commons.math.linear.InvalidMatrixException {
        return this.cachedV;
    }

    @Override // org.apache.commons.math.linear.SingularValueDecomposition
    public org.apache.commons.math.linear.RealMatrix getVT() throws org.apache.commons.math.linear.InvalidMatrixException {
        if (this.cachedVt == null) {
            this.cachedVt = getV().transpose();
        }
        return this.cachedVt;
    }

    @Override // org.apache.commons.math.linear.SingularValueDecomposition
    public org.apache.commons.math.linear.RealMatrix getCovariance(double d) {
        int length = this.singularValues.length;
        int i = 0;
        while (i < length && this.singularValues[i] >= d) {
            i++;
        }
        if (i == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.TOO_LARGE_CUTOFF_SINGULAR_VALUE, java.lang.Double.valueOf(d), java.lang.Double.valueOf(this.singularValues[0]));
        }
        final double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i, length);
        getVT().walkInOptimizedOrder(new org.apache.commons.math.linear.DefaultRealMatrixPreservingVisitor() { // from class: org.apache.commons.math.linear.SingularValueDecompositionImpl.1
            @Override // org.apache.commons.math.linear.DefaultRealMatrixPreservingVisitor, org.apache.commons.math.linear.RealMatrixPreservingVisitor
            public void visit(int i2, int i3, double d2) {
                dArr[i2][i3] = d2 / org.apache.commons.math.linear.SingularValueDecompositionImpl.this.singularValues[i2];
            }
        }, 0, i - 1, 0, length - 1);
        org.apache.commons.math.linear.RealMatrix array2DRowRealMatrix = new org.apache.commons.math.linear.Array2DRowRealMatrix(dArr, false);
        return array2DRowRealMatrix.transpose().multiply(array2DRowRealMatrix);
    }

    @Override // org.apache.commons.math.linear.SingularValueDecomposition
    public double getNorm() throws org.apache.commons.math.linear.InvalidMatrixException {
        return this.singularValues[0];
    }

    @Override // org.apache.commons.math.linear.SingularValueDecomposition
    public double getConditionNumber() throws org.apache.commons.math.linear.InvalidMatrixException {
        return this.singularValues[0] / this.singularValues[this.singularValues.length - 1];
    }

    @Override // org.apache.commons.math.linear.SingularValueDecomposition
    public int getRank() throws java.lang.IllegalStateException {
        double max = org.apache.commons.math.util.FastMath.max(this.m, this.n) * org.apache.commons.math.util.FastMath.ulp(this.singularValues[0]);
        for (int length = this.singularValues.length - 1; length >= 0; length--) {
            if (this.singularValues[length] > max) {
                return length + 1;
            }
        }
        return 0;
    }

    @Override // org.apache.commons.math.linear.SingularValueDecomposition
    public org.apache.commons.math.linear.DecompositionSolver getSolver() {
        return new org.apache.commons.math.linear.SingularValueDecompositionImpl.Solver(this.singularValues, getUT(), getV(), getRank() == java.lang.Math.max(this.m, this.n));
    }

    private static class Solver implements org.apache.commons.math.linear.DecompositionSolver {
        private boolean nonSingular;
        private final org.apache.commons.math.linear.RealMatrix pseudoInverse;

        private Solver(double[] dArr, org.apache.commons.math.linear.RealMatrix realMatrix, org.apache.commons.math.linear.RealMatrix realMatrix2, boolean z) {
            double[][] data = realMatrix.getData();
            for (int i = 0; i < dArr.length; i++) {
                double d = dArr[i] > 0.0d ? 1.0d / dArr[i] : 0.0d;
                double[] dArr2 = data[i];
                for (int i2 = 0; i2 < dArr2.length; i2++) {
                    dArr2[i2] = dArr2[i2] * d;
                }
            }
            this.pseudoInverse = realMatrix2.multiply(new org.apache.commons.math.linear.Array2DRowRealMatrix(data, false));
            this.nonSingular = z;
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public double[] solve(double[] dArr) throws java.lang.IllegalArgumentException {
            return this.pseudoInverse.operate(dArr);
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealVector solve(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
            return this.pseudoInverse.operate(realVector);
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealMatrix solve(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
            return this.pseudoInverse.multiply(realMatrix);
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public boolean isNonSingular() {
            return this.nonSingular;
        }

        @Override // org.apache.commons.math.linear.DecompositionSolver
        public org.apache.commons.math.linear.RealMatrix getInverse() {
            return this.pseudoInverse;
        }
    }
}
