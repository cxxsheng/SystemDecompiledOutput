package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public abstract class AbstractRealMatrix implements org.apache.commons.math.linear.RealMatrix {

    @java.lang.Deprecated
    private org.apache.commons.math.linear.DecompositionSolver lu;

    @Override // org.apache.commons.math.linear.RealMatrix
    public abstract void addToEntry(int i, int i2, double d) throws org.apache.commons.math.linear.MatrixIndexException;

    @Override // org.apache.commons.math.linear.RealMatrix
    public abstract org.apache.commons.math.linear.RealMatrix copy();

    @Override // org.apache.commons.math.linear.RealMatrix
    public abstract org.apache.commons.math.linear.RealMatrix createMatrix(int i, int i2) throws java.lang.IllegalArgumentException;

    @Override // org.apache.commons.math.linear.AnyMatrix
    public abstract int getColumnDimension();

    @Override // org.apache.commons.math.linear.RealMatrix
    public abstract double getEntry(int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException;

    @Override // org.apache.commons.math.linear.AnyMatrix
    public abstract int getRowDimension();

    @Override // org.apache.commons.math.linear.RealMatrix
    public abstract void multiplyEntry(int i, int i2, double d) throws org.apache.commons.math.linear.MatrixIndexException;

    @Override // org.apache.commons.math.linear.RealMatrix
    public abstract void setEntry(int i, int i2, double d) throws org.apache.commons.math.linear.MatrixIndexException;

    protected AbstractRealMatrix() {
        this.lu = null;
    }

    protected AbstractRealMatrix(int i, int i2) throws java.lang.IllegalArgumentException {
        if (i < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(i), 1);
        }
        if (i2 <= 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(i2), 1);
        }
        this.lu = null;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix add(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkAdditionCompatible(this, realMatrix);
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        org.apache.commons.math.linear.RealMatrix createMatrix = createMatrix(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                createMatrix.setEntry(i, i2, getEntry(i, i2) + realMatrix.getEntry(i, i2));
            }
        }
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix subtract(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkSubtractionCompatible(this, realMatrix);
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        org.apache.commons.math.linear.RealMatrix createMatrix = createMatrix(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                createMatrix.setEntry(i, i2, getEntry(i, i2) - realMatrix.getEntry(i, i2));
            }
        }
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix scalarAdd(double d) {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        org.apache.commons.math.linear.RealMatrix createMatrix = createMatrix(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                createMatrix.setEntry(i, i2, getEntry(i, i2) + d);
            }
        }
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix scalarMultiply(double d) {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        org.apache.commons.math.linear.RealMatrix createMatrix = createMatrix(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                createMatrix.setEntry(i, i2, getEntry(i, i2) * d);
            }
        }
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix multiply(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkMultiplicationCompatible(this, realMatrix);
        int rowDimension = getRowDimension();
        int columnDimension = realMatrix.getColumnDimension();
        int columnDimension2 = getColumnDimension();
        org.apache.commons.math.linear.RealMatrix createMatrix = createMatrix(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                double d = 0.0d;
                for (int i3 = 0; i3 < columnDimension2; i3++) {
                    d += getEntry(i, i3) * realMatrix.getEntry(i3, i2);
                }
                createMatrix.setEntry(i, i2, d);
            }
        }
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix preMultiply(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
        return realMatrix.multiply(this);
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double[][] getData() {
        double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, getRowDimension(), getColumnDimension());
        for (int i = 0; i < dArr.length; i++) {
            double[] dArr2 = dArr[i];
            for (int i2 = 0; i2 < dArr2.length; i2++) {
                dArr2[i2] = getEntry(i, i2);
            }
        }
        return dArr;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double getNorm() {
        return walkInColumnOrder(new org.apache.commons.math.linear.RealMatrixPreservingVisitor() { // from class: org.apache.commons.math.linear.AbstractRealMatrix.1
            private double columnSum;
            private double endRow;
            private double maxColSum;

            @Override // org.apache.commons.math.linear.RealMatrixPreservingVisitor
            public void start(int i, int i2, int i3, int i4, int i5, int i6) {
                this.endRow = i4;
                this.columnSum = 0.0d;
                this.maxColSum = 0.0d;
            }

            @Override // org.apache.commons.math.linear.RealMatrixPreservingVisitor
            public void visit(int i, int i2, double d) {
                this.columnSum += org.apache.commons.math.util.FastMath.abs(d);
                if (i == this.endRow) {
                    this.maxColSum = org.apache.commons.math.util.FastMath.max(this.maxColSum, this.columnSum);
                    this.columnSum = 0.0d;
                }
            }

            @Override // org.apache.commons.math.linear.RealMatrixPreservingVisitor
            public double end() {
                return this.maxColSum;
            }
        });
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double getFrobeniusNorm() {
        return walkInOptimizedOrder(new org.apache.commons.math.linear.RealMatrixPreservingVisitor() { // from class: org.apache.commons.math.linear.AbstractRealMatrix.2
            private double sum;

            @Override // org.apache.commons.math.linear.RealMatrixPreservingVisitor
            public void start(int i, int i2, int i3, int i4, int i5, int i6) {
                this.sum = 0.0d;
            }

            @Override // org.apache.commons.math.linear.RealMatrixPreservingVisitor
            public void visit(int i, int i2, double d) {
                this.sum += d * d;
            }

            @Override // org.apache.commons.math.linear.RealMatrixPreservingVisitor
            public double end() {
                return org.apache.commons.math.util.FastMath.sqrt(this.sum);
            }
        });
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix getSubMatrix(int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, i, i2, i3, i4);
        org.apache.commons.math.linear.RealMatrix createMatrix = createMatrix((i2 - i) + 1, (i4 - i3) + 1);
        for (int i5 = i; i5 <= i2; i5++) {
            for (int i6 = i3; i6 <= i4; i6++) {
                createMatrix.setEntry(i5 - i, i6 - i3, getEntry(i5, i6));
            }
        }
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix getSubMatrix(final int[] iArr, final int[] iArr2) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, iArr, iArr2);
        org.apache.commons.math.linear.RealMatrix createMatrix = createMatrix(iArr.length, iArr2.length);
        createMatrix.walkInOptimizedOrder(new org.apache.commons.math.linear.DefaultRealMatrixChangingVisitor() { // from class: org.apache.commons.math.linear.AbstractRealMatrix.3
            @Override // org.apache.commons.math.linear.DefaultRealMatrixChangingVisitor, org.apache.commons.math.linear.RealMatrixChangingVisitor
            public double visit(int i, int i2, double d) {
                return org.apache.commons.math.linear.AbstractRealMatrix.this.getEntry(iArr[i], iArr2[i2]);
            }
        });
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public void copySubMatrix(int i, int i2, int i3, int i4, final double[][] dArr) throws org.apache.commons.math.linear.MatrixIndexException, java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, i, i2, i3, i4);
        int i5 = (i2 + 1) - i;
        int i6 = (i4 + 1) - i3;
        if (dArr.length < i5 || dArr[0].length < i6) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(dArr[0].length), java.lang.Integer.valueOf(i5), java.lang.Integer.valueOf(i6));
        }
        walkInOptimizedOrder(new org.apache.commons.math.linear.DefaultRealMatrixPreservingVisitor() { // from class: org.apache.commons.math.linear.AbstractRealMatrix.4
            private int startColumn;
            private int startRow;

            @Override // org.apache.commons.math.linear.DefaultRealMatrixPreservingVisitor, org.apache.commons.math.linear.RealMatrixPreservingVisitor
            public void start(int i7, int i8, int i9, int i10, int i11, int i12) {
                this.startRow = i9;
                this.startColumn = i11;
            }

            @Override // org.apache.commons.math.linear.DefaultRealMatrixPreservingVisitor, org.apache.commons.math.linear.RealMatrixPreservingVisitor
            public void visit(int i7, int i8, double d) {
                dArr[i7 - this.startRow][i8 - this.startColumn] = d;
            }
        }, i, i2, i3, i4);
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public void copySubMatrix(int[] iArr, int[] iArr2, double[][] dArr) throws org.apache.commons.math.linear.MatrixIndexException, java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, iArr, iArr2);
        if (dArr.length < iArr.length || dArr[0].length < iArr2.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(dArr[0].length), java.lang.Integer.valueOf(iArr.length), java.lang.Integer.valueOf(iArr2.length));
        }
        for (int i = 0; i < iArr.length; i++) {
            double[] dArr2 = dArr[i];
            for (int i2 = 0; i2 < iArr2.length; i2++) {
                dArr2[i2] = getEntry(iArr[i], iArr2[i2]);
            }
        }
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public void setSubMatrix(double[][] dArr, int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        int length = dArr.length;
        if (length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_ROW, new java.lang.Object[0]);
        }
        int length2 = dArr[0].length;
        if (length2 == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_COLUMN, new java.lang.Object[0]);
        }
        for (int i3 = 1; i3 < length; i3++) {
            if (dArr[i3].length != length2) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, java.lang.Integer.valueOf(length2), java.lang.Integer.valueOf(dArr[i3].length));
            }
        }
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i2);
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, (length + i) - 1);
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, (length2 + i2) - 1);
        for (int i4 = 0; i4 < length; i4++) {
            for (int i5 = 0; i5 < length2; i5++) {
                setEntry(i + i4, i2 + i5, dArr[i4][i5]);
            }
        }
        this.lu = null;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix getRowMatrix(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        int columnDimension = getColumnDimension();
        org.apache.commons.math.linear.RealMatrix createMatrix = createMatrix(1, columnDimension);
        for (int i2 = 0; i2 < columnDimension; i2++) {
            createMatrix.setEntry(0, i2, getEntry(i, i2));
        }
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public void setRowMatrix(int i, org.apache.commons.math.linear.RealMatrix realMatrix) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        int columnDimension = getColumnDimension();
        if (realMatrix.getRowDimension() != 1 || realMatrix.getColumnDimension() != columnDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(realMatrix.getRowDimension()), java.lang.Integer.valueOf(realMatrix.getColumnDimension()), 1, java.lang.Integer.valueOf(columnDimension));
        }
        for (int i2 = 0; i2 < columnDimension; i2++) {
            setEntry(i, i2, realMatrix.getEntry(0, i2));
        }
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix getColumnMatrix(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i);
        int rowDimension = getRowDimension();
        org.apache.commons.math.linear.RealMatrix createMatrix = createMatrix(rowDimension, 1);
        for (int i2 = 0; i2 < rowDimension; i2++) {
            createMatrix.setEntry(i2, 0, getEntry(i2, i));
        }
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public void setColumnMatrix(int i, org.apache.commons.math.linear.RealMatrix realMatrix) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i);
        int rowDimension = getRowDimension();
        if (realMatrix.getRowDimension() != rowDimension || realMatrix.getColumnDimension() != 1) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(realMatrix.getRowDimension()), java.lang.Integer.valueOf(realMatrix.getColumnDimension()), java.lang.Integer.valueOf(rowDimension), 1);
        }
        for (int i2 = 0; i2 < rowDimension; i2++) {
            setEntry(i2, i, realMatrix.getEntry(i2, 0));
        }
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealVector getRowVector(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        return new org.apache.commons.math.linear.ArrayRealVector(getRow(i), false);
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public void setRowVector(int i, org.apache.commons.math.linear.RealVector realVector) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        int columnDimension = getColumnDimension();
        if (realVector.getDimension() != columnDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 1, java.lang.Integer.valueOf(realVector.getDimension()), 1, java.lang.Integer.valueOf(columnDimension));
        }
        for (int i2 = 0; i2 < columnDimension; i2++) {
            setEntry(i, i2, realVector.getEntry(i2));
        }
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealVector getColumnVector(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        return new org.apache.commons.math.linear.ArrayRealVector(getColumn(i), false);
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public void setColumnVector(int i, org.apache.commons.math.linear.RealVector realVector) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i);
        int rowDimension = getRowDimension();
        if (realVector.getDimension() != rowDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(realVector.getDimension()), 1, java.lang.Integer.valueOf(rowDimension), 1);
        }
        for (int i2 = 0; i2 < rowDimension; i2++) {
            setEntry(i2, i, realVector.getEntry(i2));
        }
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double[] getRow(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        int columnDimension = getColumnDimension();
        double[] dArr = new double[columnDimension];
        for (int i2 = 0; i2 < columnDimension; i2++) {
            dArr[i2] = getEntry(i, i2);
        }
        return dArr;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public void setRow(int i, double[] dArr) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        int columnDimension = getColumnDimension();
        if (dArr.length != columnDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 1, java.lang.Integer.valueOf(dArr.length), 1, java.lang.Integer.valueOf(columnDimension));
        }
        for (int i2 = 0; i2 < columnDimension; i2++) {
            setEntry(i, i2, dArr[i2]);
        }
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double[] getColumn(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i);
        int rowDimension = getRowDimension();
        double[] dArr = new double[rowDimension];
        for (int i2 = 0; i2 < rowDimension; i2++) {
            dArr[i2] = getEntry(i2, i);
        }
        return dArr;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public void setColumn(int i, double[] dArr) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i);
        int rowDimension = getRowDimension();
        if (dArr.length != rowDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(dArr.length), 1, java.lang.Integer.valueOf(rowDimension), 1);
        }
        for (int i2 = 0; i2 < rowDimension; i2++) {
            setEntry(i2, i, dArr[i2]);
        }
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix transpose() {
        final org.apache.commons.math.linear.RealMatrix createMatrix = createMatrix(getColumnDimension(), getRowDimension());
        walkInOptimizedOrder(new org.apache.commons.math.linear.DefaultRealMatrixPreservingVisitor() { // from class: org.apache.commons.math.linear.AbstractRealMatrix.5
            @Override // org.apache.commons.math.linear.DefaultRealMatrixPreservingVisitor, org.apache.commons.math.linear.RealMatrixPreservingVisitor
            public void visit(int i, int i2, double d) {
                createMatrix.setEntry(i2, i, d);
            }
        });
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    @java.lang.Deprecated
    public org.apache.commons.math.linear.RealMatrix inverse() throws org.apache.commons.math.linear.InvalidMatrixException {
        if (this.lu == null) {
            this.lu = new org.apache.commons.math.linear.LUDecompositionImpl(this, Double.MIN_NORMAL).getSolver();
        }
        return this.lu.getInverse();
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    @java.lang.Deprecated
    public double getDeterminant() throws org.apache.commons.math.linear.InvalidMatrixException {
        return new org.apache.commons.math.linear.LUDecompositionImpl(this, Double.MIN_NORMAL).getDeterminant();
    }

    @Override // org.apache.commons.math.linear.AnyMatrix
    public boolean isSquare() {
        return getColumnDimension() == getRowDimension();
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    @java.lang.Deprecated
    public boolean isSingular() {
        if (this.lu == null) {
            this.lu = new org.apache.commons.math.linear.LUDecompositionImpl(this, Double.MIN_NORMAL).getSolver();
        }
        return !this.lu.isNonSingular();
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double getTrace() throws org.apache.commons.math.linear.NonSquareMatrixException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        if (rowDimension != columnDimension) {
            throw new org.apache.commons.math.linear.NonSquareMatrixException(rowDimension, columnDimension);
        }
        double d = 0.0d;
        for (int i = 0; i < rowDimension; i++) {
            d += getEntry(i, i);
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double[] operate(double[] dArr) throws java.lang.IllegalArgumentException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        if (dArr.length != columnDimension) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(columnDimension));
        }
        double[] dArr2 = new double[rowDimension];
        for (int i = 0; i < rowDimension; i++) {
            double d = 0.0d;
            for (int i2 = 0; i2 < columnDimension; i2++) {
                d += getEntry(i, i2) * dArr[i2];
            }
            dArr2[i] = d;
        }
        return dArr2;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealVector operate(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        try {
            return new org.apache.commons.math.linear.ArrayRealVector(operate(((org.apache.commons.math.linear.ArrayRealVector) realVector).getDataRef()), false);
        } catch (java.lang.ClassCastException e) {
            int rowDimension = getRowDimension();
            int columnDimension = getColumnDimension();
            if (realVector.getDimension() != columnDimension) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(realVector.getDimension()), java.lang.Integer.valueOf(columnDimension));
            }
            double[] dArr = new double[rowDimension];
            for (int i = 0; i < rowDimension; i++) {
                double d = 0.0d;
                for (int i2 = 0; i2 < columnDimension; i2++) {
                    d += getEntry(i, i2) * realVector.getEntry(i2);
                }
                dArr[i] = d;
            }
            return new org.apache.commons.math.linear.ArrayRealVector(dArr, false);
        }
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double[] preMultiply(double[] dArr) throws java.lang.IllegalArgumentException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        if (dArr.length != rowDimension) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(rowDimension));
        }
        double[] dArr2 = new double[columnDimension];
        for (int i = 0; i < columnDimension; i++) {
            double d = 0.0d;
            for (int i2 = 0; i2 < rowDimension; i2++) {
                d += getEntry(i2, i) * dArr[i2];
            }
            dArr2[i] = d;
        }
        return dArr2;
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealVector preMultiply(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        try {
            return new org.apache.commons.math.linear.ArrayRealVector(preMultiply(((org.apache.commons.math.linear.ArrayRealVector) realVector).getDataRef()), false);
        } catch (java.lang.ClassCastException e) {
            int rowDimension = getRowDimension();
            int columnDimension = getColumnDimension();
            if (realVector.getDimension() != rowDimension) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(realVector.getDimension()), java.lang.Integer.valueOf(rowDimension));
            }
            double[] dArr = new double[columnDimension];
            for (int i = 0; i < columnDimension; i++) {
                double d = 0.0d;
                for (int i2 = 0; i2 < rowDimension; i2++) {
                    d += getEntry(i2, i) * realVector.getEntry(i2);
                }
                dArr[i] = d;
            }
            return new org.apache.commons.math.linear.ArrayRealVector(dArr);
        }
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double walkInRowOrder(org.apache.commons.math.linear.RealMatrixChangingVisitor realMatrixChangingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        realMatrixChangingVisitor.start(rowDimension, columnDimension, 0, rowDimension - 1, 0, columnDimension - 1);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                setEntry(i, i2, realMatrixChangingVisitor.visit(i, i2, getEntry(i, i2)));
            }
        }
        this.lu = null;
        return realMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double walkInRowOrder(org.apache.commons.math.linear.RealMatrixPreservingVisitor realMatrixPreservingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        realMatrixPreservingVisitor.start(rowDimension, columnDimension, 0, rowDimension - 1, 0, columnDimension - 1);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                realMatrixPreservingVisitor.visit(i, i2, getEntry(i, i2));
            }
        }
        return realMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double walkInRowOrder(org.apache.commons.math.linear.RealMatrixChangingVisitor realMatrixChangingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, i, i2, i3, i4);
        realMatrixChangingVisitor.start(getRowDimension(), getColumnDimension(), i, i2, i3, i4);
        while (i <= i2) {
            for (int i5 = i3; i5 <= i4; i5++) {
                setEntry(i, i5, realMatrixChangingVisitor.visit(i, i5, getEntry(i, i5)));
            }
            i++;
        }
        this.lu = null;
        return realMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double walkInRowOrder(org.apache.commons.math.linear.RealMatrixPreservingVisitor realMatrixPreservingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, i, i2, i3, i4);
        realMatrixPreservingVisitor.start(getRowDimension(), getColumnDimension(), i, i2, i3, i4);
        while (i <= i2) {
            for (int i5 = i3; i5 <= i4; i5++) {
                realMatrixPreservingVisitor.visit(i, i5, getEntry(i, i5));
            }
            i++;
        }
        return realMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double walkInColumnOrder(org.apache.commons.math.linear.RealMatrixChangingVisitor realMatrixChangingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        realMatrixChangingVisitor.start(rowDimension, columnDimension, 0, rowDimension - 1, 0, columnDimension - 1);
        for (int i = 0; i < columnDimension; i++) {
            for (int i2 = 0; i2 < rowDimension; i2++) {
                setEntry(i2, i, realMatrixChangingVisitor.visit(i2, i, getEntry(i2, i)));
            }
        }
        this.lu = null;
        return realMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double walkInColumnOrder(org.apache.commons.math.linear.RealMatrixPreservingVisitor realMatrixPreservingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        realMatrixPreservingVisitor.start(rowDimension, columnDimension, 0, rowDimension - 1, 0, columnDimension - 1);
        for (int i = 0; i < columnDimension; i++) {
            for (int i2 = 0; i2 < rowDimension; i2++) {
                realMatrixPreservingVisitor.visit(i2, i, getEntry(i2, i));
            }
        }
        return realMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double walkInColumnOrder(org.apache.commons.math.linear.RealMatrixChangingVisitor realMatrixChangingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, i, i2, i3, i4);
        realMatrixChangingVisitor.start(getRowDimension(), getColumnDimension(), i, i2, i3, i4);
        while (i3 <= i4) {
            for (int i5 = i; i5 <= i2; i5++) {
                setEntry(i5, i3, realMatrixChangingVisitor.visit(i5, i3, getEntry(i5, i3)));
            }
            i3++;
        }
        this.lu = null;
        return realMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double walkInColumnOrder(org.apache.commons.math.linear.RealMatrixPreservingVisitor realMatrixPreservingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, i, i2, i3, i4);
        realMatrixPreservingVisitor.start(getRowDimension(), getColumnDimension(), i, i2, i3, i4);
        while (i3 <= i4) {
            for (int i5 = i; i5 <= i2; i5++) {
                realMatrixPreservingVisitor.visit(i5, i3, getEntry(i5, i3));
            }
            i3++;
        }
        return realMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double walkInOptimizedOrder(org.apache.commons.math.linear.RealMatrixChangingVisitor realMatrixChangingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        return walkInRowOrder(realMatrixChangingVisitor);
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double walkInOptimizedOrder(org.apache.commons.math.linear.RealMatrixPreservingVisitor realMatrixPreservingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        return walkInRowOrder(realMatrixPreservingVisitor);
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double walkInOptimizedOrder(org.apache.commons.math.linear.RealMatrixChangingVisitor realMatrixChangingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        return walkInRowOrder(realMatrixChangingVisitor, i, i2, i3, i4);
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    public double walkInOptimizedOrder(org.apache.commons.math.linear.RealMatrixPreservingVisitor realMatrixPreservingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        return walkInRowOrder(realMatrixPreservingVisitor, i, i2, i3, i4);
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    @java.lang.Deprecated
    public double[] solve(double[] dArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
        if (this.lu == null) {
            this.lu = new org.apache.commons.math.linear.LUDecompositionImpl(this, Double.MIN_NORMAL).getSolver();
        }
        return this.lu.solve(dArr);
    }

    @Override // org.apache.commons.math.linear.RealMatrix
    @java.lang.Deprecated
    public org.apache.commons.math.linear.RealMatrix solve(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
        if (this.lu == null) {
            this.lu = new org.apache.commons.math.linear.LUDecompositionImpl(this, Double.MIN_NORMAL).getSolver();
        }
        return this.lu.solve(realMatrix);
    }

    @java.lang.Deprecated
    public void luDecompose() throws org.apache.commons.math.linear.InvalidMatrixException {
        if (this.lu == null) {
            this.lu = new org.apache.commons.math.linear.LUDecompositionImpl(this, Double.MIN_NORMAL).getSolver();
        }
    }

    public java.lang.String toString() {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.lang.String name = getClass().getName();
        sb.append(name.substring(name.lastIndexOf(46) + 1));
        sb.append("{");
        for (int i = 0; i < rowDimension; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append("{");
            for (int i2 = 0; i2 < columnDimension; i2++) {
                if (i2 > 0) {
                    sb.append(",");
                }
                sb.append(getEntry(i, i2));
            }
            sb.append("}");
        }
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.linear.RealMatrix)) {
            return false;
        }
        org.apache.commons.math.linear.RealMatrix realMatrix = (org.apache.commons.math.linear.RealMatrix) obj;
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        if (realMatrix.getColumnDimension() != columnDimension || realMatrix.getRowDimension() != rowDimension) {
            return false;
        }
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                if (getEntry(i, i2) != realMatrix.getEntry(i, i2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        int i = ((com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_APPLICATION_EXEMPTIONS + rowDimension) * 31) + columnDimension;
        for (int i2 = 0; i2 < rowDimension; i2++) {
            int i3 = 0;
            while (i3 < columnDimension) {
                int i4 = i3 + 1;
                i = (i * 31) + ((((i2 + 1) * 11) + (i4 * 17)) * org.apache.commons.math.util.MathUtils.hash(getEntry(i2, i3)));
                i3 = i4;
            }
        }
        return i;
    }
}
