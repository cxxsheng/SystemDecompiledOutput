package org.apache.commons.math.linear;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class BigMatrixImpl implements org.apache.commons.math.linear.BigMatrix, java.io.Serializable {
    private static final long serialVersionUID = -1011428905656140431L;
    protected java.math.BigDecimal[][] data;
    protected java.math.BigDecimal[][] lu;
    protected int parity;
    protected int[] permutation;
    private int roundingMode;
    private int scale;
    static final java.math.BigDecimal ZERO = new java.math.BigDecimal(0);
    static final java.math.BigDecimal ONE = new java.math.BigDecimal(1);
    private static final java.math.BigDecimal TOO_SMALL = new java.math.BigDecimal(1.0E-11d);

    public BigMatrixImpl() {
        this.data = null;
        this.lu = null;
        this.permutation = null;
        this.parity = 1;
        this.roundingMode = 4;
        this.scale = 64;
    }

    public BigMatrixImpl(int i, int i2) {
        this.data = null;
        this.lu = null;
        this.permutation = null;
        this.parity = 1;
        this.roundingMode = 4;
        this.scale = 64;
        if (i < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(i), 1);
        }
        if (i2 < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(i2), 1);
        }
        this.data = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, i, i2);
        this.lu = null;
    }

    public BigMatrixImpl(java.math.BigDecimal[][] bigDecimalArr) {
        this.data = null;
        this.lu = null;
        this.permutation = null;
        this.parity = 1;
        this.roundingMode = 4;
        this.scale = 64;
        copyIn(bigDecimalArr);
        this.lu = null;
    }

    public BigMatrixImpl(java.math.BigDecimal[][] bigDecimalArr, boolean z) {
        this.data = null;
        this.lu = null;
        this.permutation = null;
        this.parity = 1;
        this.roundingMode = 4;
        this.scale = 64;
        if (z) {
            copyIn(bigDecimalArr);
        } else {
            if (bigDecimalArr == null) {
                throw new java.lang.NullPointerException();
            }
            int length = bigDecimalArr.length;
            if (length == 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_ROW, new java.lang.Object[0]);
            }
            int length2 = bigDecimalArr[0].length;
            if (length2 == 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_COLUMN, new java.lang.Object[0]);
            }
            for (int i = 1; i < length; i++) {
                if (bigDecimalArr[i].length != length2) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, java.lang.Integer.valueOf(length2), java.lang.Integer.valueOf(bigDecimalArr[i].length));
                }
            }
            this.data = bigDecimalArr;
        }
        this.lu = null;
    }

    public BigMatrixImpl(double[][] dArr) {
        this.data = null;
        this.lu = null;
        this.permutation = null;
        this.parity = 1;
        this.roundingMode = 4;
        this.scale = 64;
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
        copyIn(dArr);
        this.lu = null;
    }

    public BigMatrixImpl(java.lang.String[][] strArr) {
        this.data = null;
        this.lu = null;
        this.permutation = null;
        this.parity = 1;
        this.roundingMode = 4;
        this.scale = 64;
        int length = strArr.length;
        if (length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_ROW, new java.lang.Object[0]);
        }
        int length2 = strArr[0].length;
        if (length2 == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_COLUMN, new java.lang.Object[0]);
        }
        for (int i = 1; i < length; i++) {
            if (strArr[i].length != length2) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, java.lang.Integer.valueOf(length2), java.lang.Integer.valueOf(strArr[i].length));
            }
        }
        copyIn(strArr);
        this.lu = null;
    }

    public BigMatrixImpl(java.math.BigDecimal[] bigDecimalArr) {
        this.data = null;
        this.lu = null;
        this.permutation = null;
        this.parity = 1;
        this.roundingMode = 4;
        this.scale = 64;
        int length = bigDecimalArr.length;
        this.data = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, length, 1);
        for (int i = 0; i < length; i++) {
            this.data[i][0] = bigDecimalArr[i];
        }
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public org.apache.commons.math.linear.BigMatrix copy() {
        return new org.apache.commons.math.linear.BigMatrixImpl(copyOut(), false);
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public org.apache.commons.math.linear.BigMatrix add(org.apache.commons.math.linear.BigMatrix bigMatrix) throws java.lang.IllegalArgumentException {
        try {
            return add((org.apache.commons.math.linear.BigMatrixImpl) bigMatrix);
        } catch (java.lang.ClassCastException e) {
            org.apache.commons.math.linear.MatrixUtils.checkAdditionCompatible(this, bigMatrix);
            int rowDimension = getRowDimension();
            int columnDimension = getColumnDimension();
            java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, rowDimension, columnDimension);
            for (int i = 0; i < rowDimension; i++) {
                java.math.BigDecimal[] bigDecimalArr2 = this.data[i];
                java.math.BigDecimal[] bigDecimalArr3 = bigDecimalArr[i];
                for (int i2 = 0; i2 < columnDimension; i2++) {
                    bigDecimalArr3[i2] = bigDecimalArr2[i2].add(bigMatrix.getEntry(i, i2));
                }
            }
            return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
        }
    }

    public org.apache.commons.math.linear.BigMatrixImpl add(org.apache.commons.math.linear.BigMatrixImpl bigMatrixImpl) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkAdditionCompatible(this, bigMatrixImpl);
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            java.math.BigDecimal[] bigDecimalArr2 = this.data[i];
            java.math.BigDecimal[] bigDecimalArr3 = bigMatrixImpl.data[i];
            java.math.BigDecimal[] bigDecimalArr4 = bigDecimalArr[i];
            for (int i2 = 0; i2 < columnDimension; i2++) {
                bigDecimalArr4[i2] = bigDecimalArr2[i2].add(bigDecimalArr3[i2]);
            }
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public org.apache.commons.math.linear.BigMatrix subtract(org.apache.commons.math.linear.BigMatrix bigMatrix) throws java.lang.IllegalArgumentException {
        try {
            return subtract((org.apache.commons.math.linear.BigMatrixImpl) bigMatrix);
        } catch (java.lang.ClassCastException e) {
            org.apache.commons.math.linear.MatrixUtils.checkSubtractionCompatible(this, bigMatrix);
            int rowDimension = getRowDimension();
            int columnDimension = getColumnDimension();
            java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, rowDimension, columnDimension);
            for (int i = 0; i < rowDimension; i++) {
                java.math.BigDecimal[] bigDecimalArr2 = this.data[i];
                java.math.BigDecimal[] bigDecimalArr3 = bigDecimalArr[i];
                for (int i2 = 0; i2 < columnDimension; i2++) {
                    bigDecimalArr3[i2] = bigDecimalArr2[i2].subtract(getEntry(i, i2));
                }
            }
            return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
        }
    }

    public org.apache.commons.math.linear.BigMatrixImpl subtract(org.apache.commons.math.linear.BigMatrixImpl bigMatrixImpl) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkSubtractionCompatible(this, bigMatrixImpl);
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            java.math.BigDecimal[] bigDecimalArr2 = this.data[i];
            java.math.BigDecimal[] bigDecimalArr3 = bigMatrixImpl.data[i];
            java.math.BigDecimal[] bigDecimalArr4 = bigDecimalArr[i];
            for (int i2 = 0; i2 < columnDimension; i2++) {
                bigDecimalArr4[i2] = bigDecimalArr2[i2].subtract(bigDecimalArr3[i2]);
            }
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public org.apache.commons.math.linear.BigMatrix scalarAdd(java.math.BigDecimal bigDecimal) {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            java.math.BigDecimal[] bigDecimalArr2 = this.data[i];
            java.math.BigDecimal[] bigDecimalArr3 = bigDecimalArr[i];
            for (int i2 = 0; i2 < columnDimension; i2++) {
                bigDecimalArr3[i2] = bigDecimalArr2[i2].add(bigDecimal);
            }
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public org.apache.commons.math.linear.BigMatrix scalarMultiply(java.math.BigDecimal bigDecimal) {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            java.math.BigDecimal[] bigDecimalArr2 = this.data[i];
            java.math.BigDecimal[] bigDecimalArr3 = bigDecimalArr[i];
            for (int i2 = 0; i2 < columnDimension; i2++) {
                bigDecimalArr3[i2] = bigDecimalArr2[i2].multiply(bigDecimal);
            }
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public org.apache.commons.math.linear.BigMatrix multiply(org.apache.commons.math.linear.BigMatrix bigMatrix) throws java.lang.IllegalArgumentException {
        try {
            return multiply((org.apache.commons.math.linear.BigMatrixImpl) bigMatrix);
        } catch (java.lang.ClassCastException e) {
            org.apache.commons.math.linear.MatrixUtils.checkMultiplicationCompatible(this, bigMatrix);
            int rowDimension = getRowDimension();
            int columnDimension = bigMatrix.getColumnDimension();
            int columnDimension2 = getColumnDimension();
            java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, rowDimension, columnDimension);
            for (int i = 0; i < rowDimension; i++) {
                java.math.BigDecimal[] bigDecimalArr2 = this.data[i];
                java.math.BigDecimal[] bigDecimalArr3 = bigDecimalArr[i];
                for (int i2 = 0; i2 < columnDimension; i2++) {
                    java.math.BigDecimal bigDecimal = ZERO;
                    for (int i3 = 0; i3 < columnDimension2; i3++) {
                        bigDecimal = bigDecimal.add(bigDecimalArr2[i3].multiply(bigMatrix.getEntry(i3, i2)));
                    }
                    bigDecimalArr3[i2] = bigDecimal;
                }
            }
            return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
        }
    }

    public org.apache.commons.math.linear.BigMatrixImpl multiply(org.apache.commons.math.linear.BigMatrixImpl bigMatrixImpl) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkMultiplicationCompatible(this, bigMatrixImpl);
        int rowDimension = getRowDimension();
        int columnDimension = bigMatrixImpl.getColumnDimension();
        int columnDimension2 = getColumnDimension();
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            java.math.BigDecimal[] bigDecimalArr2 = this.data[i];
            java.math.BigDecimal[] bigDecimalArr3 = bigDecimalArr[i];
            for (int i2 = 0; i2 < columnDimension; i2++) {
                java.math.BigDecimal bigDecimal = ZERO;
                for (int i3 = 0; i3 < columnDimension2; i3++) {
                    bigDecimal = bigDecimal.add(bigDecimalArr2[i3].multiply(bigMatrixImpl.data[i3][i2]));
                }
                bigDecimalArr3[i2] = bigDecimal;
            }
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public org.apache.commons.math.linear.BigMatrix preMultiply(org.apache.commons.math.linear.BigMatrix bigMatrix) throws java.lang.IllegalArgumentException {
        return bigMatrix.multiply(this);
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public java.math.BigDecimal[][] getData() {
        return copyOut();
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public double[][] getDataAsDoubleArray() {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                dArr[i][i2] = this.data[i][i2].doubleValue();
            }
        }
        return dArr;
    }

    public java.math.BigDecimal[][] getDataRef() {
        return this.data;
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public int getRoundingMode() {
        return this.roundingMode;
    }

    public void setRoundingMode(int i) {
        this.roundingMode = i;
    }

    public int getScale() {
        return this.scale;
    }

    public void setScale(int i) {
        this.scale = i;
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public java.math.BigDecimal getNorm() {
        java.math.BigDecimal bigDecimal = ZERO;
        for (int i = 0; i < getColumnDimension(); i++) {
            java.math.BigDecimal bigDecimal2 = ZERO;
            for (int i2 = 0; i2 < getRowDimension(); i2++) {
                bigDecimal2 = bigDecimal2.add(this.data[i2][i].abs());
            }
            bigDecimal = bigDecimal.max(bigDecimal2);
        }
        return bigDecimal;
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public org.apache.commons.math.linear.BigMatrix getSubMatrix(int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i2);
        if (i > i2) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.INITIAL_ROW_AFTER_FINAL_ROW, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i3);
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i4);
        if (i3 > i4) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.INITIAL_COLUMN_AFTER_FINAL_COLUMN, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4));
        }
        int i5 = (i4 - i3) + 1;
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, (i2 - i) + 1, i5);
        for (int i6 = i; i6 <= i2; i6++) {
            java.lang.System.arraycopy(this.data[i6], i3, bigDecimalArr[i6 - i], 0, i5);
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public org.apache.commons.math.linear.BigMatrix getSubMatrix(int[] iArr, int[] iArr2) throws org.apache.commons.math.linear.MatrixIndexException {
        if (iArr.length * iArr2.length == 0) {
            if (iArr.length == 0) {
                throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_SELECTED_ROW_INDEX_ARRAY, new java.lang.Object[0]);
            }
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_SELECTED_COLUMN_INDEX_ARRAY, new java.lang.Object[0]);
        }
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, iArr.length, iArr2.length);
        for (int i = 0; i < iArr.length; i++) {
            try {
                java.math.BigDecimal[] bigDecimalArr2 = bigDecimalArr[i];
                java.math.BigDecimal[] bigDecimalArr3 = this.data[iArr[i]];
                for (int i2 = 0; i2 < iArr2.length; i2++) {
                    bigDecimalArr2[i2] = bigDecimalArr3[iArr2[i2]];
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                for (int i3 : iArr) {
                    org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i3);
                }
                for (int i4 : iArr2) {
                    org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i4);
                }
            }
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    public void setSubMatrix(java.math.BigDecimal[][] bigDecimalArr, int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        int length = bigDecimalArr.length;
        if (length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_ROW, new java.lang.Object[0]);
        }
        int length2 = bigDecimalArr[0].length;
        if (length2 == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_COLUMN, new java.lang.Object[0]);
        }
        for (int i3 = 1; i3 < length; i3++) {
            if (bigDecimalArr[i3].length != length2) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, java.lang.Integer.valueOf(length2), java.lang.Integer.valueOf(bigDecimalArr[i3].length));
            }
        }
        if (this.data == null) {
            if (i > 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.FIRST_ROWS_NOT_INITIALIZED_YET, java.lang.Integer.valueOf(i));
            }
            if (i2 <= 0) {
                this.data = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, length, length2);
                java.lang.System.arraycopy(bigDecimalArr, 0, this.data, 0, bigDecimalArr.length);
            } else {
                throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.FIRST_COLUMNS_NOT_INITIALIZED_YET, java.lang.Integer.valueOf(i2));
            }
        } else {
            org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
            org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i2);
            org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, (length + i) - 1);
            org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, (length2 + i2) - 1);
        }
        for (int i4 = 0; i4 < length; i4++) {
            java.lang.System.arraycopy(bigDecimalArr[i4], 0, this.data[i + i4], i2, length2);
        }
        this.lu = null;
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public org.apache.commons.math.linear.BigMatrix getRowMatrix(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        int columnDimension = getColumnDimension();
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, 1, columnDimension);
        java.lang.System.arraycopy(this.data[i], 0, bigDecimalArr[0], 0, columnDimension);
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public org.apache.commons.math.linear.BigMatrix getColumnMatrix(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i);
        int rowDimension = getRowDimension();
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, rowDimension, 1);
        for (int i2 = 0; i2 < rowDimension; i2++) {
            bigDecimalArr[i2][0] = this.data[i2][i];
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public java.math.BigDecimal[] getRow(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        int columnDimension = getColumnDimension();
        java.math.BigDecimal[] bigDecimalArr = new java.math.BigDecimal[columnDimension];
        java.lang.System.arraycopy(this.data[i], 0, bigDecimalArr, 0, columnDimension);
        return bigDecimalArr;
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public double[] getRowAsDoubleArray(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        int columnDimension = getColumnDimension();
        double[] dArr = new double[columnDimension];
        for (int i2 = 0; i2 < columnDimension; i2++) {
            dArr[i2] = this.data[i][i2].doubleValue();
        }
        return dArr;
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public java.math.BigDecimal[] getColumn(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i);
        int rowDimension = getRowDimension();
        java.math.BigDecimal[] bigDecimalArr = new java.math.BigDecimal[rowDimension];
        for (int i2 = 0; i2 < rowDimension; i2++) {
            bigDecimalArr[i2] = this.data[i2][i];
        }
        return bigDecimalArr;
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public double[] getColumnAsDoubleArray(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i);
        int rowDimension = getRowDimension();
        double[] dArr = new double[rowDimension];
        for (int i2 = 0; i2 < rowDimension; i2++) {
            dArr[i2] = this.data[i2][i].doubleValue();
        }
        return dArr;
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public java.math.BigDecimal getEntry(int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        try {
            return this.data[i][i2];
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()));
        }
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public double getEntryAsDouble(int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        return getEntry(i, i2).doubleValue();
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public org.apache.commons.math.linear.BigMatrix transpose() {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, columnDimension, rowDimension);
        for (int i = 0; i < rowDimension; i++) {
            java.math.BigDecimal[] bigDecimalArr2 = this.data[i];
            for (int i2 = 0; i2 < columnDimension; i2++) {
                bigDecimalArr[i2][i] = bigDecimalArr2[i2];
            }
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public org.apache.commons.math.linear.BigMatrix inverse() throws org.apache.commons.math.linear.InvalidMatrixException {
        return solve(org.apache.commons.math.linear.MatrixUtils.createBigIdentityMatrix(getRowDimension()));
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public java.math.BigDecimal getDeterminant() throws org.apache.commons.math.linear.InvalidMatrixException {
        if (!isSquare()) {
            throw new org.apache.commons.math.linear.NonSquareMatrixException(getRowDimension(), getColumnDimension());
        }
        if (isSingular()) {
            return ZERO;
        }
        java.math.BigDecimal negate = this.parity == 1 ? ONE : ONE.negate();
        for (int i = 0; i < getRowDimension(); i++) {
            negate = negate.multiply(this.lu[i][i]);
        }
        return negate;
    }

    @Override // org.apache.commons.math.linear.AnyMatrix
    public boolean isSquare() {
        return getColumnDimension() == getRowDimension();
    }

    public boolean isSingular() {
        if (this.lu != null) {
            return false;
        }
        try {
            luDecompose();
            return false;
        } catch (org.apache.commons.math.linear.InvalidMatrixException e) {
            return true;
        }
    }

    @Override // org.apache.commons.math.linear.AnyMatrix
    public int getRowDimension() {
        return this.data.length;
    }

    @Override // org.apache.commons.math.linear.AnyMatrix
    public int getColumnDimension() {
        return this.data[0].length;
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public java.math.BigDecimal getTrace() throws java.lang.IllegalArgumentException {
        if (!isSquare()) {
            throw new org.apache.commons.math.linear.NonSquareMatrixException(getRowDimension(), getColumnDimension());
        }
        java.math.BigDecimal bigDecimal = this.data[0][0];
        for (int i = 1; i < getRowDimension(); i++) {
            bigDecimal = bigDecimal.add(this.data[i][i]);
        }
        return bigDecimal;
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public java.math.BigDecimal[] operate(java.math.BigDecimal[] bigDecimalArr) throws java.lang.IllegalArgumentException {
        if (bigDecimalArr.length != getColumnDimension()) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(bigDecimalArr.length), java.lang.Integer.valueOf(getColumnDimension()));
        }
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        java.math.BigDecimal[] bigDecimalArr2 = new java.math.BigDecimal[rowDimension];
        for (int i = 0; i < rowDimension; i++) {
            java.math.BigDecimal bigDecimal = ZERO;
            for (int i2 = 0; i2 < columnDimension; i2++) {
                bigDecimal = bigDecimal.add(this.data[i][i2].multiply(bigDecimalArr[i2]));
            }
            bigDecimalArr2[i] = bigDecimal;
        }
        return bigDecimalArr2;
    }

    public java.math.BigDecimal[] operate(double[] dArr) throws java.lang.IllegalArgumentException {
        int length = dArr.length;
        java.math.BigDecimal[] bigDecimalArr = new java.math.BigDecimal[length];
        for (int i = 0; i < length; i++) {
            bigDecimalArr[i] = new java.math.BigDecimal(dArr[i]);
        }
        return operate(bigDecimalArr);
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public java.math.BigDecimal[] preMultiply(java.math.BigDecimal[] bigDecimalArr) throws java.lang.IllegalArgumentException {
        int rowDimension = getRowDimension();
        if (bigDecimalArr.length != rowDimension) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(bigDecimalArr.length), java.lang.Integer.valueOf(rowDimension));
        }
        int columnDimension = getColumnDimension();
        java.math.BigDecimal[] bigDecimalArr2 = new java.math.BigDecimal[columnDimension];
        for (int i = 0; i < columnDimension; i++) {
            java.math.BigDecimal bigDecimal = ZERO;
            for (int i2 = 0; i2 < rowDimension; i2++) {
                bigDecimal = bigDecimal.add(this.data[i2][i].multiply(bigDecimalArr[i2]));
            }
            bigDecimalArr2[i] = bigDecimal;
        }
        return bigDecimalArr2;
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public java.math.BigDecimal[] solve(java.math.BigDecimal[] bigDecimalArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
        int rowDimension = getRowDimension();
        if (bigDecimalArr.length != rowDimension) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(bigDecimalArr.length), java.lang.Integer.valueOf(rowDimension));
        }
        java.math.BigDecimal[][] dataRef = ((org.apache.commons.math.linear.BigMatrixImpl) solve(new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr))).getDataRef();
        java.math.BigDecimal[] bigDecimalArr2 = new java.math.BigDecimal[rowDimension];
        for (int i = 0; i < rowDimension; i++) {
            bigDecimalArr2[i] = dataRef[i][0];
        }
        return bigDecimalArr2;
    }

    public java.math.BigDecimal[] solve(double[] dArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
        int length = dArr.length;
        java.math.BigDecimal[] bigDecimalArr = new java.math.BigDecimal[length];
        for (int i = 0; i < length; i++) {
            bigDecimalArr[i] = new java.math.BigDecimal(dArr[i]);
        }
        return solve(bigDecimalArr);
    }

    @Override // org.apache.commons.math.linear.BigMatrix
    public org.apache.commons.math.linear.BigMatrix solve(org.apache.commons.math.linear.BigMatrix bigMatrix) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
        if (bigMatrix.getRowDimension() != getRowDimension()) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(bigMatrix.getRowDimension()), java.lang.Integer.valueOf(bigMatrix.getColumnDimension()), java.lang.Integer.valueOf(getRowDimension()), "n");
        }
        if (!isSquare()) {
            throw new org.apache.commons.math.linear.NonSquareMatrixException(getRowDimension(), getColumnDimension());
        }
        if (isSingular()) {
            throw new org.apache.commons.math.linear.SingularMatrixException();
        }
        int columnDimension = getColumnDimension();
        int columnDimension2 = bigMatrix.getColumnDimension();
        int rowDimension = bigMatrix.getRowDimension();
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, rowDimension, columnDimension2);
        for (int i = 0; i < rowDimension; i++) {
            java.math.BigDecimal[] bigDecimalArr2 = bigDecimalArr[i];
            for (int i2 = 0; i2 < columnDimension2; i2++) {
                bigDecimalArr2[i2] = bigMatrix.getEntry(this.permutation[i], i2);
            }
        }
        int i3 = 0;
        while (i3 < columnDimension) {
            int i4 = i3 + 1;
            for (int i5 = i4; i5 < columnDimension; i5++) {
                java.math.BigDecimal[] bigDecimalArr3 = bigDecimalArr[i5];
                java.math.BigDecimal[] bigDecimalArr4 = this.lu[i5];
                for (int i6 = 0; i6 < columnDimension2; i6++) {
                    bigDecimalArr3[i6] = bigDecimalArr3[i6].subtract(bigDecimalArr[i3][i6].multiply(bigDecimalArr4[i3]));
                }
            }
            i3 = i4;
        }
        for (int i7 = columnDimension - 1; i7 >= 0; i7--) {
            java.math.BigDecimal[] bigDecimalArr5 = bigDecimalArr[i7];
            java.math.BigDecimal bigDecimal = this.lu[i7][i7];
            for (int i8 = 0; i8 < columnDimension2; i8++) {
                bigDecimalArr5[i8] = bigDecimalArr5[i8].divide(bigDecimal, this.scale, this.roundingMode);
            }
            for (int i9 = 0; i9 < i7; i9++) {
                java.math.BigDecimal[] bigDecimalArr6 = bigDecimalArr[i9];
                java.math.BigDecimal[] bigDecimalArr7 = this.lu[i9];
                for (int i10 = 0; i10 < columnDimension2; i10++) {
                    bigDecimalArr6[i10] = bigDecimalArr6[i10].subtract(bigDecimalArr[i7][i10].multiply(bigDecimalArr7[i7]));
                }
            }
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    public void luDecompose() throws org.apache.commons.math.linear.InvalidMatrixException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        if (rowDimension != columnDimension) {
            throw new org.apache.commons.math.linear.NonSquareMatrixException(getRowDimension(), getColumnDimension());
        }
        this.lu = getData();
        this.permutation = new int[rowDimension];
        for (int i = 0; i < rowDimension; i++) {
            this.permutation[i] = i;
        }
        this.parity = 1;
        int i2 = 0;
        while (i2 < columnDimension) {
            for (int i3 = 0; i3 < i2; i3++) {
                java.math.BigDecimal[] bigDecimalArr = this.lu[i3];
                java.math.BigDecimal bigDecimal = bigDecimalArr[i2];
                for (int i4 = 0; i4 < i3; i4++) {
                    bigDecimal = bigDecimal.subtract(bigDecimalArr[i4].multiply(this.lu[i4][i2]));
                }
                bigDecimalArr[i2] = bigDecimal;
            }
            java.math.BigDecimal bigDecimal2 = ZERO;
            int i5 = i2;
            int i6 = i5;
            while (i5 < rowDimension) {
                java.math.BigDecimal[] bigDecimalArr2 = this.lu[i5];
                java.math.BigDecimal bigDecimal3 = bigDecimalArr2[i2];
                for (int i7 = 0; i7 < i2; i7++) {
                    bigDecimal3 = bigDecimal3.subtract(bigDecimalArr2[i7].multiply(this.lu[i7][i2]));
                }
                bigDecimalArr2[i2] = bigDecimal3;
                if (bigDecimal3.abs().compareTo(bigDecimal2) == 1) {
                    bigDecimal2 = bigDecimal3.abs();
                    i6 = i5;
                }
                i5++;
            }
            if (this.lu[i6][i2].abs().compareTo(TOO_SMALL) <= 0) {
                this.lu = null;
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }
            if (i6 != i2) {
                for (int i8 = 0; i8 < columnDimension; i8++) {
                    java.math.BigDecimal bigDecimal4 = this.lu[i6][i8];
                    this.lu[i6][i8] = this.lu[i2][i8];
                    this.lu[i2][i8] = bigDecimal4;
                }
                int i9 = this.permutation[i6];
                this.permutation[i6] = this.permutation[i2];
                this.permutation[i2] = i9;
                this.parity = -this.parity;
            }
            java.math.BigDecimal bigDecimal5 = this.lu[i2][i2];
            int i10 = i2 + 1;
            for (int i11 = i10; i11 < rowDimension; i11++) {
                java.math.BigDecimal[] bigDecimalArr3 = this.lu[i11];
                bigDecimalArr3[i2] = bigDecimalArr3[i2].divide(bigDecimal5, this.scale, this.roundingMode);
            }
            i2 = i10;
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("BigMatrixImpl{");
        if (this.data != null) {
            for (int i = 0; i < this.data.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append("{");
                for (int i2 = 0; i2 < this.data[0].length; i2++) {
                    if (i2 > 0) {
                        sb.append(",");
                    }
                    sb.append(this.data[i][i2]);
                }
                sb.append("}");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.linear.BigMatrixImpl)) {
            return false;
        }
        org.apache.commons.math.linear.BigMatrix bigMatrix = (org.apache.commons.math.linear.BigMatrix) obj;
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        if (bigMatrix.getColumnDimension() != columnDimension || bigMatrix.getRowDimension() != rowDimension) {
            return false;
        }
        for (int i = 0; i < rowDimension; i++) {
            java.math.BigDecimal[] bigDecimalArr = this.data[i];
            for (int i2 = 0; i2 < columnDimension; i2++) {
                if (!bigDecimalArr[i2].equals(bigMatrix.getEntry(i, i2))) {
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
            java.math.BigDecimal[] bigDecimalArr = this.data[i2];
            int i3 = 0;
            while (i3 < columnDimension) {
                int i4 = i3 + 1;
                i = (i * 31) + ((((i2 + 1) * 11) + (i4 * 17)) * bigDecimalArr[i3].hashCode());
                i3 = i4;
            }
        }
        return i;
    }

    protected org.apache.commons.math.linear.BigMatrix getLUMatrix() throws org.apache.commons.math.linear.InvalidMatrixException {
        if (this.lu == null) {
            luDecompose();
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(this.lu);
    }

    protected int[] getPermutation() {
        int[] iArr = new int[this.permutation.length];
        java.lang.System.arraycopy(this.permutation, 0, iArr, 0, this.permutation.length);
        return iArr;
    }

    private java.math.BigDecimal[][] copyOut() {
        int rowDimension = getRowDimension();
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, rowDimension, getColumnDimension());
        for (int i = 0; i < rowDimension; i++) {
            java.lang.System.arraycopy(this.data[i], 0, bigDecimalArr[i], 0, this.data[i].length);
        }
        return bigDecimalArr;
    }

    private void copyIn(java.math.BigDecimal[][] bigDecimalArr) {
        setSubMatrix(bigDecimalArr, 0, 0);
    }

    private void copyIn(double[][] dArr) {
        int length = dArr.length;
        int length2 = dArr[0].length;
        this.data = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, length, length2);
        for (int i = 0; i < length; i++) {
            java.math.BigDecimal[] bigDecimalArr = this.data[i];
            double[] dArr2 = dArr[i];
            for (int i2 = 0; i2 < length2; i2++) {
                bigDecimalArr[i2] = new java.math.BigDecimal(dArr2[i2]);
            }
        }
        this.lu = null;
    }

    private void copyIn(java.lang.String[][] strArr) {
        int length = strArr.length;
        int length2 = strArr[0].length;
        this.data = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, length, length2);
        for (int i = 0; i < length; i++) {
            java.math.BigDecimal[] bigDecimalArr = this.data[i];
            java.lang.String[] strArr2 = strArr[i];
            for (int i2 = 0; i2 < length2; i2++) {
                bigDecimalArr[i2] = new java.math.BigDecimal(strArr2[i2]);
            }
        }
        this.lu = null;
    }
}
