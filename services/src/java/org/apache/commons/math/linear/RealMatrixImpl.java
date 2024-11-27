package org.apache.commons.math.linear;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class RealMatrixImpl extends org.apache.commons.math.linear.AbstractRealMatrix implements java.io.Serializable {
    private static final long serialVersionUID = -1067294169172445528L;
    protected double[][] data;

    public RealMatrixImpl() {
    }

    public RealMatrixImpl(int i, int i2) throws java.lang.IllegalArgumentException {
        super(i, i2);
        this.data = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i, i2);
    }

    public RealMatrixImpl(double[][] dArr) throws java.lang.IllegalArgumentException, java.lang.NullPointerException {
        copyIn(dArr);
    }

    public RealMatrixImpl(double[][] dArr, boolean z) throws java.lang.IllegalArgumentException, java.lang.NullPointerException {
        if (z) {
            copyIn(dArr);
            return;
        }
        if (dArr == null) {
            throw new java.lang.NullPointerException();
        }
        int length = dArr.length;
        if (length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_ROW, new java.lang.Object[0]);
        }
        int length2 = dArr[0].length;
        if (length2 == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_COLUMN, new java.lang.Object[0]);
        }
        for (int i = 1; i < length; i++) {
            if (dArr[i].length != length2) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, java.lang.Integer.valueOf(length2), java.lang.Integer.valueOf(dArr[i].length));
            }
        }
        this.data = dArr;
    }

    public RealMatrixImpl(double[] dArr) {
        int length = dArr.length;
        this.data = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, length, 1);
        for (int i = 0; i < length; i++) {
            this.data[i][0] = dArr[i];
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix createMatrix(int i, int i2) throws java.lang.IllegalArgumentException {
        return new org.apache.commons.math.linear.RealMatrixImpl(i, i2);
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix copy() {
        return new org.apache.commons.math.linear.RealMatrixImpl(copyOut(), false);
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix add(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
        try {
            return add((org.apache.commons.math.linear.RealMatrixImpl) realMatrix);
        } catch (java.lang.ClassCastException e) {
            return super.add(realMatrix);
        }
    }

    public org.apache.commons.math.linear.RealMatrixImpl add(org.apache.commons.math.linear.RealMatrixImpl realMatrixImpl) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkAdditionCompatible(this, realMatrixImpl);
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            double[] dArr2 = this.data[i];
            double[] dArr3 = realMatrixImpl.data[i];
            double[] dArr4 = dArr[i];
            for (int i2 = 0; i2 < columnDimension; i2++) {
                dArr4[i2] = dArr2[i2] + dArr3[i2];
            }
        }
        return new org.apache.commons.math.linear.RealMatrixImpl(dArr, false);
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix subtract(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
        try {
            return subtract((org.apache.commons.math.linear.RealMatrixImpl) realMatrix);
        } catch (java.lang.ClassCastException e) {
            return super.subtract(realMatrix);
        }
    }

    public org.apache.commons.math.linear.RealMatrixImpl subtract(org.apache.commons.math.linear.RealMatrixImpl realMatrixImpl) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkSubtractionCompatible(this, realMatrixImpl);
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            double[] dArr2 = this.data[i];
            double[] dArr3 = realMatrixImpl.data[i];
            double[] dArr4 = dArr[i];
            for (int i2 = 0; i2 < columnDimension; i2++) {
                dArr4[i2] = dArr2[i2] - dArr3[i2];
            }
        }
        return new org.apache.commons.math.linear.RealMatrixImpl(dArr, false);
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix multiply(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
        try {
            return multiply((org.apache.commons.math.linear.RealMatrixImpl) realMatrix);
        } catch (java.lang.ClassCastException e) {
            return super.multiply(realMatrix);
        }
    }

    public org.apache.commons.math.linear.RealMatrixImpl multiply(org.apache.commons.math.linear.RealMatrixImpl realMatrixImpl) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkMultiplicationCompatible(this, realMatrixImpl);
        int rowDimension = getRowDimension();
        int columnDimension = realMatrixImpl.getColumnDimension();
        int columnDimension2 = getColumnDimension();
        int i = 0;
        double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, rowDimension, columnDimension);
        int i2 = 0;
        while (i2 < rowDimension) {
            double[] dArr2 = this.data[i2];
            double[] dArr3 = dArr[i2];
            int i3 = i;
            while (i3 < columnDimension) {
                double d = 0.0d;
                for (int i4 = i; i4 < columnDimension2; i4++) {
                    d += dArr2[i4] * realMatrixImpl.data[i4][i3];
                }
                dArr3[i3] = d;
                i3++;
                i = 0;
            }
            i2++;
            i = 0;
        }
        return new org.apache.commons.math.linear.RealMatrixImpl(dArr, false);
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double[][] getData() {
        return copyOut();
    }

    public double[][] getDataRef() {
        return this.data;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void setSubMatrix(double[][] dArr, int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        if (this.data == null) {
            if (i > 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.FIRST_ROWS_NOT_INITIALIZED_YET, java.lang.Integer.valueOf(i));
            }
            if (i2 > 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.FIRST_COLUMNS_NOT_INITIALIZED_YET, java.lang.Integer.valueOf(i2));
            }
            if (dArr.length == 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_ROW, new java.lang.Object[0]);
            }
            int length = dArr[0].length;
            if (length == 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_COLUMN, new java.lang.Object[0]);
            }
            this.data = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, dArr.length, length);
            for (int i3 = 0; i3 < this.data.length; i3++) {
                if (dArr[i3].length == length) {
                    java.lang.System.arraycopy(dArr[i3], 0, this.data[i3 + i], i2, length);
                } else {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, java.lang.Integer.valueOf(length), java.lang.Integer.valueOf(dArr[i3].length));
                }
            }
            return;
        }
        super.setSubMatrix(dArr, i, i2);
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double getEntry(int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        try {
            return this.data[i][i2];
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()));
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void setEntry(int i, int i2, double d) throws org.apache.commons.math.linear.MatrixIndexException {
        try {
            this.data[i][i2] = d;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()));
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void addToEntry(int i, int i2, double d) throws org.apache.commons.math.linear.MatrixIndexException {
        try {
            double[] dArr = this.data[i];
            dArr[i2] = dArr[i2] + d;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()));
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void multiplyEntry(int i, int i2, double d) throws org.apache.commons.math.linear.MatrixIndexException {
        try {
            double[] dArr = this.data[i];
            dArr[i2] = dArr[i2] * d;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()));
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.AnyMatrix
    public int getRowDimension() {
        if (this.data == null) {
            return 0;
        }
        return this.data.length;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.AnyMatrix
    public int getColumnDimension() {
        if (this.data == null || this.data[0] == null) {
            return 0;
        }
        return this.data[0].length;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double[] operate(double[] dArr) throws java.lang.IllegalArgumentException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        if (dArr.length != columnDimension) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(columnDimension));
        }
        double[] dArr2 = new double[rowDimension];
        for (int i = 0; i < rowDimension; i++) {
            double[] dArr3 = this.data[i];
            double d = 0.0d;
            for (int i2 = 0; i2 < columnDimension; i2++) {
                d += dArr3[i2] * dArr[i2];
            }
            dArr2[i] = d;
        }
        return dArr2;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
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
                d += this.data[i2][i] * dArr[i2];
            }
            dArr2[i] = d;
        }
        return dArr2;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInRowOrder(org.apache.commons.math.linear.RealMatrixChangingVisitor realMatrixChangingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        realMatrixChangingVisitor.start(rowDimension, columnDimension, 0, rowDimension - 1, 0, columnDimension - 1);
        for (int i = 0; i < rowDimension; i++) {
            double[] dArr = this.data[i];
            for (int i2 = 0; i2 < columnDimension; i2++) {
                dArr[i2] = realMatrixChangingVisitor.visit(i, i2, dArr[i2]);
            }
        }
        return realMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInRowOrder(org.apache.commons.math.linear.RealMatrixPreservingVisitor realMatrixPreservingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        realMatrixPreservingVisitor.start(rowDimension, columnDimension, 0, rowDimension - 1, 0, columnDimension - 1);
        for (int i = 0; i < rowDimension; i++) {
            double[] dArr = this.data[i];
            for (int i2 = 0; i2 < columnDimension; i2++) {
                realMatrixPreservingVisitor.visit(i, i2, dArr[i2]);
            }
        }
        return realMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInRowOrder(org.apache.commons.math.linear.RealMatrixChangingVisitor realMatrixChangingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, i, i2, i3, i4);
        realMatrixChangingVisitor.start(getRowDimension(), getColumnDimension(), i, i2, i3, i4);
        while (i <= i2) {
            double[] dArr = this.data[i];
            for (int i5 = i3; i5 <= i4; i5++) {
                dArr[i5] = realMatrixChangingVisitor.visit(i, i5, dArr[i5]);
            }
            i++;
        }
        return realMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInRowOrder(org.apache.commons.math.linear.RealMatrixPreservingVisitor realMatrixPreservingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, i, i2, i3, i4);
        realMatrixPreservingVisitor.start(getRowDimension(), getColumnDimension(), i, i2, i3, i4);
        while (i <= i2) {
            double[] dArr = this.data[i];
            for (int i5 = i3; i5 <= i4; i5++) {
                realMatrixPreservingVisitor.visit(i, i5, dArr[i5]);
            }
            i++;
        }
        return realMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInColumnOrder(org.apache.commons.math.linear.RealMatrixChangingVisitor realMatrixChangingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        realMatrixChangingVisitor.start(rowDimension, columnDimension, 0, rowDimension - 1, 0, columnDimension - 1);
        for (int i = 0; i < columnDimension; i++) {
            for (int i2 = 0; i2 < rowDimension; i2++) {
                double[] dArr = this.data[i2];
                dArr[i] = realMatrixChangingVisitor.visit(i2, i, dArr[i]);
            }
        }
        return realMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInColumnOrder(org.apache.commons.math.linear.RealMatrixPreservingVisitor realMatrixPreservingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        realMatrixPreservingVisitor.start(rowDimension, columnDimension, 0, rowDimension - 1, 0, columnDimension - 1);
        for (int i = 0; i < columnDimension; i++) {
            for (int i2 = 0; i2 < rowDimension; i2++) {
                realMatrixPreservingVisitor.visit(i2, i, this.data[i2][i]);
            }
        }
        return realMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInColumnOrder(org.apache.commons.math.linear.RealMatrixChangingVisitor realMatrixChangingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, i, i2, i3, i4);
        realMatrixChangingVisitor.start(getRowDimension(), getColumnDimension(), i, i2, i3, i4);
        while (i3 <= i4) {
            for (int i5 = i; i5 <= i2; i5++) {
                double[] dArr = this.data[i5];
                dArr[i3] = realMatrixChangingVisitor.visit(i5, i3, dArr[i3]);
            }
            i3++;
        }
        return realMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInColumnOrder(org.apache.commons.math.linear.RealMatrixPreservingVisitor realMatrixPreservingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, i, i2, i3, i4);
        realMatrixPreservingVisitor.start(getRowDimension(), getColumnDimension(), i, i2, i3, i4);
        while (i3 <= i4) {
            for (int i5 = i; i5 <= i2; i5++) {
                realMatrixPreservingVisitor.visit(i5, i3, this.data[i5][i3]);
            }
            i3++;
        }
        return realMatrixPreservingVisitor.end();
    }

    private double[][] copyOut() {
        int rowDimension = getRowDimension();
        double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, rowDimension, getColumnDimension());
        for (int i = 0; i < rowDimension; i++) {
            java.lang.System.arraycopy(this.data[i], 0, dArr[i], 0, this.data[i].length);
        }
        return dArr;
    }

    private void copyIn(double[][] dArr) {
        setSubMatrix(dArr, 0, 0);
    }
}
