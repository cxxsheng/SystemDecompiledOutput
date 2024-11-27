package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public abstract class AbstractFieldMatrix<T extends org.apache.commons.math.FieldElement<T>> implements org.apache.commons.math.linear.FieldMatrix<T> {
    private final org.apache.commons.math.Field<T> field;

    @Override // org.apache.commons.math.linear.FieldMatrix
    public abstract void addToEntry(int i, int i2, T t) throws org.apache.commons.math.linear.MatrixIndexException;

    @Override // org.apache.commons.math.linear.FieldMatrix
    public abstract org.apache.commons.math.linear.FieldMatrix<T> copy();

    @Override // org.apache.commons.math.linear.FieldMatrix
    public abstract org.apache.commons.math.linear.FieldMatrix<T> createMatrix(int i, int i2) throws java.lang.IllegalArgumentException;

    @Override // org.apache.commons.math.linear.AnyMatrix
    public abstract int getColumnDimension();

    @Override // org.apache.commons.math.linear.FieldMatrix
    public abstract T getEntry(int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException;

    @Override // org.apache.commons.math.linear.AnyMatrix
    public abstract int getRowDimension();

    @Override // org.apache.commons.math.linear.FieldMatrix
    public abstract void multiplyEntry(int i, int i2, T t) throws org.apache.commons.math.linear.MatrixIndexException;

    @Override // org.apache.commons.math.linear.FieldMatrix
    public abstract void setEntry(int i, int i2, T t) throws org.apache.commons.math.linear.MatrixIndexException;

    protected AbstractFieldMatrix() {
        this.field = null;
    }

    protected AbstractFieldMatrix(org.apache.commons.math.Field<T> field) {
        this.field = field;
    }

    protected AbstractFieldMatrix(org.apache.commons.math.Field<T> field, int i, int i2) throws java.lang.IllegalArgumentException {
        if (i < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(i), 1);
        }
        if (i2 < 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INSUFFICIENT_DIMENSION, java.lang.Integer.valueOf(i2), 1);
        }
        this.field = field;
    }

    protected static <T extends org.apache.commons.math.FieldElement<T>> org.apache.commons.math.Field<T> extractField(T[][] tArr) throws java.lang.IllegalArgumentException {
        if (tArr.length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_ROW, new java.lang.Object[0]);
        }
        if (tArr[0].length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_COLUMN, new java.lang.Object[0]);
        }
        return tArr[0][0].getField();
    }

    protected static <T extends org.apache.commons.math.FieldElement<T>> org.apache.commons.math.Field<T> extractField(T[] tArr) throws java.lang.IllegalArgumentException {
        if (tArr.length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_ROW, new java.lang.Object[0]);
        }
        return tArr[0].getField();
    }

    protected static <T extends org.apache.commons.math.FieldElement<T>> T[][] buildArray(org.apache.commons.math.Field<T> field, int i, int i2) {
        if (i2 < 0) {
            return (T[][]) ((org.apache.commons.math.FieldElement[][]) java.lang.reflect.Array.newInstance(((org.apache.commons.math.FieldElement[]) java.lang.reflect.Array.newInstance(field.getZero().getClass(), 0)).getClass(), i));
        }
        T[][] tArr = (T[][]) ((org.apache.commons.math.FieldElement[][]) java.lang.reflect.Array.newInstance(field.getZero().getClass(), i, i2));
        for (T[] tArr2 : tArr) {
            java.util.Arrays.fill(tArr2, field.getZero());
        }
        return tArr;
    }

    protected static <T extends org.apache.commons.math.FieldElement<T>> T[] buildArray(org.apache.commons.math.Field<T> field, int i) {
        T[] tArr = (T[]) ((org.apache.commons.math.FieldElement[]) java.lang.reflect.Array.newInstance(field.getZero().getClass(), i));
        java.util.Arrays.fill(tArr, field.getZero());
        return tArr;
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.Field<T> getField() {
        return this.field;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> add(org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) throws java.lang.IllegalArgumentException {
        checkAdditionCompatible(fieldMatrix);
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        org.apache.commons.math.linear.FieldMatrix<T> createMatrix = createMatrix(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                createMatrix.setEntry(i, i2, (org.apache.commons.math.FieldElement) getEntry(i, i2).add(fieldMatrix.getEntry(i, i2)));
            }
        }
        return createMatrix;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> subtract(org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) throws java.lang.IllegalArgumentException {
        checkSubtractionCompatible(fieldMatrix);
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        org.apache.commons.math.linear.FieldMatrix<T> createMatrix = createMatrix(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                createMatrix.setEntry(i, i2, (org.apache.commons.math.FieldElement) getEntry(i, i2).subtract(fieldMatrix.getEntry(i, i2)));
            }
        }
        return createMatrix;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> scalarAdd(T t) {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        org.apache.commons.math.linear.FieldMatrix<T> createMatrix = createMatrix(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                createMatrix.setEntry(i, i2, (org.apache.commons.math.FieldElement) getEntry(i, i2).add(t));
            }
        }
        return createMatrix;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> scalarMultiply(T t) {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        org.apache.commons.math.linear.FieldMatrix<T> createMatrix = createMatrix(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                createMatrix.setEntry(i, i2, (org.apache.commons.math.FieldElement) getEntry(i, i2).multiply(t));
            }
        }
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> multiply(org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) throws java.lang.IllegalArgumentException {
        checkMultiplicationCompatible(fieldMatrix);
        int rowDimension = getRowDimension();
        int columnDimension = fieldMatrix.getColumnDimension();
        int columnDimension2 = getColumnDimension();
        org.apache.commons.math.linear.FieldMatrix<T> createMatrix = createMatrix(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                T zero = this.field.getZero();
                for (int i3 = 0; i3 < columnDimension2; i3++) {
                    zero = (T) zero.add((org.apache.commons.math.FieldElement) getEntry(i, i3).multiply(fieldMatrix.getEntry(i3, i2)));
                }
                createMatrix.setEntry(i, i2, zero);
            }
        }
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> preMultiply(org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) throws java.lang.IllegalArgumentException {
        return fieldMatrix.multiply(this);
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T[][] getData() {
        T[][] tArr = (T[][]) buildArray(this.field, getRowDimension(), getColumnDimension());
        for (int i = 0; i < tArr.length; i++) {
            T[] tArr2 = tArr[i];
            for (int i2 = 0; i2 < tArr2.length; i2++) {
                tArr2[i2] = getEntry(i, i2);
            }
        }
        return tArr;
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> getSubMatrix(int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException {
        checkSubMatrixIndex(i, i2, i3, i4);
        org.apache.commons.math.linear.FieldMatrix<T> createMatrix = createMatrix((i2 - i) + 1, (i4 - i3) + 1);
        for (int i5 = i; i5 <= i2; i5++) {
            for (int i6 = i3; i6 <= i4; i6++) {
                createMatrix.setEntry(i5 - i, i6 - i3, getEntry(i5, i6));
            }
        }
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> getSubMatrix(final int[] iArr, final int[] iArr2) throws org.apache.commons.math.linear.MatrixIndexException {
        checkSubMatrixIndex(iArr, iArr2);
        org.apache.commons.math.linear.FieldMatrix<T> createMatrix = createMatrix(iArr.length, iArr2.length);
        createMatrix.walkInOptimizedOrder(new org.apache.commons.math.linear.DefaultFieldMatrixChangingVisitor<T>(this.field.getZero()) { // from class: org.apache.commons.math.linear.AbstractFieldMatrix.1
            @Override // org.apache.commons.math.linear.DefaultFieldMatrixChangingVisitor, org.apache.commons.math.linear.FieldMatrixChangingVisitor
            public T visit(int i, int i2, T t) {
                return (T) org.apache.commons.math.linear.AbstractFieldMatrix.this.getEntry(iArr[i], iArr2[i2]);
            }
        });
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public void copySubMatrix(int i, int i2, int i3, int i4, final T[][] tArr) throws org.apache.commons.math.linear.MatrixIndexException, java.lang.IllegalArgumentException {
        checkSubMatrixIndex(i, i2, i3, i4);
        int i5 = (i2 + 1) - i;
        int i6 = (i4 + 1) - i3;
        if (tArr.length < i5 || tArr[0].length < i6) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(tArr.length), java.lang.Integer.valueOf(tArr[0].length), java.lang.Integer.valueOf(i5), java.lang.Integer.valueOf(i6));
        }
        walkInOptimizedOrder(new org.apache.commons.math.linear.DefaultFieldMatrixPreservingVisitor<T>(this.field.getZero()) { // from class: org.apache.commons.math.linear.AbstractFieldMatrix.2
            private int startColumn;
            private int startRow;

            @Override // org.apache.commons.math.linear.DefaultFieldMatrixPreservingVisitor, org.apache.commons.math.linear.FieldMatrixPreservingVisitor
            public void start(int i7, int i8, int i9, int i10, int i11, int i12) {
                this.startRow = i9;
                this.startColumn = i11;
            }

            @Override // org.apache.commons.math.linear.DefaultFieldMatrixPreservingVisitor, org.apache.commons.math.linear.FieldMatrixPreservingVisitor
            public void visit(int i7, int i8, T t) {
                tArr[i7 - this.startRow][i8 - this.startColumn] = t;
            }
        }, i, i2, i3, i4);
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public void copySubMatrix(int[] iArr, int[] iArr2, T[][] tArr) throws org.apache.commons.math.linear.MatrixIndexException, java.lang.IllegalArgumentException {
        checkSubMatrixIndex(iArr, iArr2);
        if (tArr.length < iArr.length || tArr[0].length < iArr2.length) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(tArr.length), java.lang.Integer.valueOf(tArr[0].length), java.lang.Integer.valueOf(iArr.length), java.lang.Integer.valueOf(iArr2.length));
        }
        for (int i = 0; i < iArr.length; i++) {
            T[] tArr2 = tArr[i];
            for (int i2 = 0; i2 < iArr2.length; i2++) {
                tArr2[i2] = getEntry(iArr[i], iArr2[i2]);
            }
        }
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public void setSubMatrix(T[][] tArr, int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        int length = tArr.length;
        if (length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_ROW, new java.lang.Object[0]);
        }
        int length2 = tArr[0].length;
        if (length2 == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_COLUMN, new java.lang.Object[0]);
        }
        for (int i3 = 1; i3 < length; i3++) {
            if (tArr[i3].length != length2) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, java.lang.Integer.valueOf(length2), java.lang.Integer.valueOf(tArr[i3].length));
            }
        }
        checkRowIndex(i);
        checkColumnIndex(i2);
        checkRowIndex((length + i) - 1);
        checkColumnIndex((length2 + i2) - 1);
        for (int i4 = 0; i4 < length; i4++) {
            for (int i5 = 0; i5 < length2; i5++) {
                setEntry(i + i4, i2 + i5, tArr[i4][i5]);
            }
        }
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> getRowMatrix(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        checkRowIndex(i);
        int columnDimension = getColumnDimension();
        org.apache.commons.math.linear.FieldMatrix<T> createMatrix = createMatrix(1, columnDimension);
        for (int i2 = 0; i2 < columnDimension; i2++) {
            createMatrix.setEntry(0, i2, getEntry(i, i2));
        }
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public void setRowMatrix(int i, org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        checkRowIndex(i);
        int columnDimension = getColumnDimension();
        if (fieldMatrix.getRowDimension() != 1 || fieldMatrix.getColumnDimension() != columnDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(fieldMatrix.getRowDimension()), java.lang.Integer.valueOf(fieldMatrix.getColumnDimension()), 1, java.lang.Integer.valueOf(columnDimension));
        }
        for (int i2 = 0; i2 < columnDimension; i2++) {
            setEntry(i, i2, fieldMatrix.getEntry(0, i2));
        }
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> getColumnMatrix(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        checkColumnIndex(i);
        int rowDimension = getRowDimension();
        org.apache.commons.math.linear.FieldMatrix<T> createMatrix = createMatrix(rowDimension, 1);
        for (int i2 = 0; i2 < rowDimension; i2++) {
            createMatrix.setEntry(i2, 0, getEntry(i2, i));
        }
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public void setColumnMatrix(int i, org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        checkColumnIndex(i);
        int rowDimension = getRowDimension();
        if (fieldMatrix.getRowDimension() != rowDimension || fieldMatrix.getColumnDimension() != 1) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(fieldMatrix.getRowDimension()), java.lang.Integer.valueOf(fieldMatrix.getColumnDimension()), java.lang.Integer.valueOf(rowDimension), 1);
        }
        for (int i2 = 0; i2 < rowDimension; i2++) {
            setEntry(i2, i, fieldMatrix.getEntry(i2, 0));
        }
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldVector<T> getRowVector(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        return new org.apache.commons.math.linear.ArrayFieldVector((org.apache.commons.math.FieldElement[]) getRow(i), false);
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public void setRowVector(int i, org.apache.commons.math.linear.FieldVector<T> fieldVector) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        checkRowIndex(i);
        int columnDimension = getColumnDimension();
        if (fieldVector.getDimension() != columnDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 1, java.lang.Integer.valueOf(fieldVector.getDimension()), 1, java.lang.Integer.valueOf(columnDimension));
        }
        for (int i2 = 0; i2 < columnDimension; i2++) {
            setEntry(i, i2, fieldVector.getEntry(i2));
        }
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldVector<T> getColumnVector(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        return new org.apache.commons.math.linear.ArrayFieldVector((org.apache.commons.math.FieldElement[]) getColumn(i), false);
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public void setColumnVector(int i, org.apache.commons.math.linear.FieldVector<T> fieldVector) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        checkColumnIndex(i);
        int rowDimension = getRowDimension();
        if (fieldVector.getDimension() != rowDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(fieldVector.getDimension()), 1, java.lang.Integer.valueOf(rowDimension), 1);
        }
        for (int i2 = 0; i2 < rowDimension; i2++) {
            setEntry(i2, i, fieldVector.getEntry(i2));
        }
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T[] getRow(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        checkRowIndex(i);
        int columnDimension = getColumnDimension();
        T[] tArr = (T[]) buildArray(this.field, columnDimension);
        for (int i2 = 0; i2 < columnDimension; i2++) {
            tArr[i2] = getEntry(i, i2);
        }
        return tArr;
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public void setRow(int i, T[] tArr) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        checkRowIndex(i);
        int columnDimension = getColumnDimension();
        if (tArr.length != columnDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 1, java.lang.Integer.valueOf(tArr.length), 1, java.lang.Integer.valueOf(columnDimension));
        }
        for (int i2 = 0; i2 < columnDimension; i2++) {
            setEntry(i, i2, tArr[i2]);
        }
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T[] getColumn(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        checkColumnIndex(i);
        int rowDimension = getRowDimension();
        T[] tArr = (T[]) buildArray(this.field, rowDimension);
        for (int i2 = 0; i2 < rowDimension; i2++) {
            tArr[i2] = getEntry(i2, i);
        }
        return tArr;
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public void setColumn(int i, T[] tArr) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        checkColumnIndex(i);
        int rowDimension = getRowDimension();
        if (tArr.length != rowDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(tArr.length), 1, java.lang.Integer.valueOf(rowDimension), 1);
        }
        for (int i2 = 0; i2 < rowDimension; i2++) {
            setEntry(i2, i, tArr[i2]);
        }
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> transpose() {
        final org.apache.commons.math.linear.FieldMatrix<T> createMatrix = createMatrix(getColumnDimension(), getRowDimension());
        walkInOptimizedOrder(new org.apache.commons.math.linear.DefaultFieldMatrixPreservingVisitor<T>(this.field.getZero()) { // from class: org.apache.commons.math.linear.AbstractFieldMatrix.3
            @Override // org.apache.commons.math.linear.DefaultFieldMatrixPreservingVisitor, org.apache.commons.math.linear.FieldMatrixPreservingVisitor
            public void visit(int i, int i2, T t) {
                createMatrix.setEntry(i2, i, t);
            }
        });
        return createMatrix;
    }

    @Override // org.apache.commons.math.linear.AnyMatrix
    public boolean isSquare() {
        return getColumnDimension() == getRowDimension();
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T getTrace() throws org.apache.commons.math.linear.NonSquareMatrixException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        if (rowDimension != columnDimension) {
            throw new org.apache.commons.math.linear.NonSquareMatrixException(rowDimension, columnDimension);
        }
        T zero = this.field.getZero();
        for (int i = 0; i < rowDimension; i++) {
            zero = (T) zero.add(getEntry(i, i));
        }
        return zero;
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T[] operate(T[] tArr) throws java.lang.IllegalArgumentException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        if (tArr.length != columnDimension) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(tArr.length), java.lang.Integer.valueOf(columnDimension));
        }
        T[] tArr2 = (T[]) buildArray(this.field, rowDimension);
        for (int i = 0; i < rowDimension; i++) {
            T zero = this.field.getZero();
            for (int i2 = 0; i2 < columnDimension; i2++) {
                zero = (T) zero.add((org.apache.commons.math.FieldElement) getEntry(i, i2).multiply(tArr[i2]));
            }
            tArr2[i] = zero;
        }
        return tArr2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldVector<T> operate(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        try {
            return new org.apache.commons.math.linear.ArrayFieldVector(operate(((org.apache.commons.math.linear.ArrayFieldVector) fieldVector).getDataRef()), false);
        } catch (java.lang.ClassCastException e) {
            int rowDimension = getRowDimension();
            int columnDimension = getColumnDimension();
            if (fieldVector.getDimension() != columnDimension) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(fieldVector.getDimension()), java.lang.Integer.valueOf(columnDimension));
            }
            org.apache.commons.math.FieldElement[] buildArray = buildArray(this.field, rowDimension);
            for (int i = 0; i < rowDimension; i++) {
                T zero = this.field.getZero();
                for (int i2 = 0; i2 < columnDimension; i2++) {
                    zero = (T) zero.add((org.apache.commons.math.FieldElement) getEntry(i, i2).multiply(fieldVector.getEntry(i2)));
                }
                buildArray[i] = zero;
            }
            return new org.apache.commons.math.linear.ArrayFieldVector(buildArray, false);
        }
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T[] preMultiply(T[] tArr) throws java.lang.IllegalArgumentException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        if (tArr.length != rowDimension) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(tArr.length), java.lang.Integer.valueOf(rowDimension));
        }
        T[] tArr2 = (T[]) buildArray(this.field, columnDimension);
        for (int i = 0; i < columnDimension; i++) {
            T zero = this.field.getZero();
            for (int i2 = 0; i2 < rowDimension; i2++) {
                zero = (T) zero.add((org.apache.commons.math.FieldElement) getEntry(i2, i).multiply(tArr[i2]));
            }
            tArr2[i] = zero;
        }
        return tArr2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldVector<T> preMultiply(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        try {
            return new org.apache.commons.math.linear.ArrayFieldVector(preMultiply(((org.apache.commons.math.linear.ArrayFieldVector) fieldVector).getDataRef()), false);
        } catch (java.lang.ClassCastException e) {
            int rowDimension = getRowDimension();
            int columnDimension = getColumnDimension();
            if (fieldVector.getDimension() != rowDimension) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(fieldVector.getDimension()), java.lang.Integer.valueOf(rowDimension));
            }
            org.apache.commons.math.FieldElement[] buildArray = buildArray(this.field, columnDimension);
            for (int i = 0; i < columnDimension; i++) {
                T zero = this.field.getZero();
                for (int i2 = 0; i2 < rowDimension; i2++) {
                    zero = (T) zero.add((org.apache.commons.math.FieldElement) getEntry(i2, i).multiply(fieldVector.getEntry(i2)));
                }
                buildArray[i] = zero;
            }
            return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
        }
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T walkInRowOrder(org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> fieldMatrixChangingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        fieldMatrixChangingVisitor.start(rowDimension, columnDimension, 0, rowDimension - 1, 0, columnDimension - 1);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                setEntry(i, i2, fieldMatrixChangingVisitor.visit(i, i2, getEntry(i, i2)));
            }
        }
        return fieldMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T walkInRowOrder(org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> fieldMatrixPreservingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        fieldMatrixPreservingVisitor.start(rowDimension, columnDimension, 0, rowDimension - 1, 0, columnDimension - 1);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                fieldMatrixPreservingVisitor.visit(i, i2, getEntry(i, i2));
            }
        }
        return fieldMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T walkInRowOrder(org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> fieldMatrixChangingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        checkSubMatrixIndex(i, i2, i3, i4);
        fieldMatrixChangingVisitor.start(getRowDimension(), getColumnDimension(), i, i2, i3, i4);
        while (i <= i2) {
            for (int i5 = i3; i5 <= i4; i5++) {
                setEntry(i, i5, fieldMatrixChangingVisitor.visit(i, i5, getEntry(i, i5)));
            }
            i++;
        }
        return fieldMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T walkInRowOrder(org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> fieldMatrixPreservingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        checkSubMatrixIndex(i, i2, i3, i4);
        fieldMatrixPreservingVisitor.start(getRowDimension(), getColumnDimension(), i, i2, i3, i4);
        while (i <= i2) {
            for (int i5 = i3; i5 <= i4; i5++) {
                fieldMatrixPreservingVisitor.visit(i, i5, getEntry(i, i5));
            }
            i++;
        }
        return fieldMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T walkInColumnOrder(org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> fieldMatrixChangingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        fieldMatrixChangingVisitor.start(rowDimension, columnDimension, 0, rowDimension - 1, 0, columnDimension - 1);
        for (int i = 0; i < columnDimension; i++) {
            for (int i2 = 0; i2 < rowDimension; i2++) {
                setEntry(i2, i, fieldMatrixChangingVisitor.visit(i2, i, getEntry(i2, i)));
            }
        }
        return fieldMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T walkInColumnOrder(org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> fieldMatrixPreservingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        fieldMatrixPreservingVisitor.start(rowDimension, columnDimension, 0, rowDimension - 1, 0, columnDimension - 1);
        for (int i = 0; i < columnDimension; i++) {
            for (int i2 = 0; i2 < rowDimension; i2++) {
                fieldMatrixPreservingVisitor.visit(i2, i, getEntry(i2, i));
            }
        }
        return fieldMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T walkInColumnOrder(org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> fieldMatrixChangingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        checkSubMatrixIndex(i, i2, i3, i4);
        fieldMatrixChangingVisitor.start(getRowDimension(), getColumnDimension(), i, i2, i3, i4);
        while (i3 <= i4) {
            for (int i5 = i; i5 <= i2; i5++) {
                setEntry(i5, i3, fieldMatrixChangingVisitor.visit(i5, i3, getEntry(i5, i3)));
            }
            i3++;
        }
        return fieldMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T walkInColumnOrder(org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> fieldMatrixPreservingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        checkSubMatrixIndex(i, i2, i3, i4);
        fieldMatrixPreservingVisitor.start(getRowDimension(), getColumnDimension(), i, i2, i3, i4);
        while (i3 <= i4) {
            for (int i5 = i; i5 <= i2; i5++) {
                fieldMatrixPreservingVisitor.visit(i5, i3, getEntry(i5, i3));
            }
            i3++;
        }
        return fieldMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T walkInOptimizedOrder(org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> fieldMatrixChangingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        return walkInRowOrder(fieldMatrixChangingVisitor);
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T walkInOptimizedOrder(org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> fieldMatrixPreservingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        return walkInRowOrder(fieldMatrixPreservingVisitor);
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T walkInOptimizedOrder(org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> fieldMatrixChangingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        return walkInRowOrder(fieldMatrixChangingVisitor, i, i2, i3, i4);
    }

    @Override // org.apache.commons.math.linear.FieldMatrix
    public T walkInOptimizedOrder(org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> fieldMatrixPreservingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        return walkInRowOrder(fieldMatrixPreservingVisitor, i, i2, i3, i4);
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
        if (!(obj instanceof org.apache.commons.math.linear.FieldMatrix)) {
            return false;
        }
        org.apache.commons.math.linear.FieldMatrix fieldMatrix = (org.apache.commons.math.linear.FieldMatrix) obj;
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        if (fieldMatrix.getColumnDimension() != columnDimension || fieldMatrix.getRowDimension() != rowDimension) {
            return false;
        }
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                if (!getEntry(i, i2).equals(fieldMatrix.getEntry(i, i2))) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();
        int i = ((9999422 + rowDimension) * 31) + columnDimension;
        for (int i2 = 0; i2 < rowDimension; i2++) {
            int i3 = 0;
            while (i3 < columnDimension) {
                int i4 = i3 + 1;
                i = (i * 31) + ((((i2 + 1) * 11) + (i4 * 17)) * getEntry(i2, i3).hashCode());
                i3 = i4;
            }
        }
        return i;
    }

    protected void checkRowIndex(int i) {
        if (i < 0 || i >= getRowDimension()) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.ROW_INDEX_OUT_OF_RANGE, java.lang.Integer.valueOf(i), 0, java.lang.Integer.valueOf(getRowDimension() - 1));
        }
    }

    protected void checkColumnIndex(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        if (i < 0 || i >= getColumnDimension()) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.COLUMN_INDEX_OUT_OF_RANGE, java.lang.Integer.valueOf(i), 0, java.lang.Integer.valueOf(getColumnDimension() - 1));
        }
    }

    protected void checkSubMatrixIndex(int i, int i2, int i3, int i4) {
        checkRowIndex(i);
        checkRowIndex(i2);
        if (i > i2) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.INITIAL_ROW_AFTER_FINAL_ROW, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        checkColumnIndex(i3);
        checkColumnIndex(i4);
        if (i3 > i4) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.INITIAL_COLUMN_AFTER_FINAL_COLUMN, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4));
        }
    }

    protected void checkSubMatrixIndex(int[] iArr, int[] iArr2) {
        if (iArr.length * iArr2.length == 0) {
            if (iArr.length == 0) {
                throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_SELECTED_ROW_INDEX_ARRAY, new java.lang.Object[0]);
            }
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_SELECTED_COLUMN_INDEX_ARRAY, new java.lang.Object[0]);
        }
        for (int i : iArr) {
            checkRowIndex(i);
        }
        for (int i2 : iArr2) {
            checkColumnIndex(i2);
        }
    }

    protected void checkAdditionCompatible(org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) {
        if (getRowDimension() != fieldMatrix.getRowDimension() || getColumnDimension() != fieldMatrix.getColumnDimension()) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_ADDITION_COMPATIBLE_MATRICES, java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()), java.lang.Integer.valueOf(fieldMatrix.getRowDimension()), java.lang.Integer.valueOf(fieldMatrix.getColumnDimension()));
        }
    }

    protected void checkSubtractionCompatible(org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) {
        if (getRowDimension() != fieldMatrix.getRowDimension() || getColumnDimension() != fieldMatrix.getColumnDimension()) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_SUBTRACTION_COMPATIBLE_MATRICES, java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()), java.lang.Integer.valueOf(fieldMatrix.getRowDimension()), java.lang.Integer.valueOf(fieldMatrix.getColumnDimension()));
        }
    }

    protected void checkMultiplicationCompatible(org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) {
        if (getColumnDimension() != fieldMatrix.getRowDimension()) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_MULTIPLICATION_COMPATIBLE_MATRICES, java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()), java.lang.Integer.valueOf(fieldMatrix.getRowDimension()), java.lang.Integer.valueOf(fieldMatrix.getColumnDimension()));
        }
    }
}
