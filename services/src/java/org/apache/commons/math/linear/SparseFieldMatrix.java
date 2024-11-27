package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class SparseFieldMatrix<T extends org.apache.commons.math.FieldElement<T>> extends org.apache.commons.math.linear.AbstractFieldMatrix<T> {
    private static final long serialVersionUID = 9078068119297757342L;
    private final int columns;
    private final org.apache.commons.math.util.OpenIntToFieldHashMap<T> entries;
    private final int rows;

    public SparseFieldMatrix(org.apache.commons.math.Field<T> field) {
        super(field);
        this.rows = 0;
        this.columns = 0;
        this.entries = new org.apache.commons.math.util.OpenIntToFieldHashMap<>(field);
    }

    public SparseFieldMatrix(org.apache.commons.math.Field<T> field, int i, int i2) throws java.lang.IllegalArgumentException {
        super(field, i, i2);
        this.rows = i;
        this.columns = i2;
        this.entries = new org.apache.commons.math.util.OpenIntToFieldHashMap<>(field);
    }

    public SparseFieldMatrix(org.apache.commons.math.linear.SparseFieldMatrix<T> sparseFieldMatrix) {
        super(sparseFieldMatrix.getField(), sparseFieldMatrix.getRowDimension(), sparseFieldMatrix.getColumnDimension());
        this.rows = sparseFieldMatrix.getRowDimension();
        this.columns = sparseFieldMatrix.getColumnDimension();
        this.entries = new org.apache.commons.math.util.OpenIntToFieldHashMap<>(sparseFieldMatrix.entries);
    }

    public SparseFieldMatrix(org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) {
        super(fieldMatrix.getField(), fieldMatrix.getRowDimension(), fieldMatrix.getColumnDimension());
        this.rows = fieldMatrix.getRowDimension();
        this.columns = fieldMatrix.getColumnDimension();
        this.entries = new org.apache.commons.math.util.OpenIntToFieldHashMap<>(getField());
        for (int i = 0; i < this.rows; i++) {
            for (int i2 = 0; i2 < this.columns; i2++) {
                setEntry(i, i2, fieldMatrix.getEntry(i, i2));
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public void addToEntry(int i, int i2, T t) throws org.apache.commons.math.linear.MatrixIndexException {
        checkRowIndex(i);
        checkColumnIndex(i2);
        int computeKey = computeKey(i, i2);
        org.apache.commons.math.FieldElement fieldElement = (org.apache.commons.math.FieldElement) this.entries.get(computeKey).add(t);
        if (getField().getZero().equals(fieldElement)) {
            this.entries.remove(computeKey);
        } else {
            this.entries.put(computeKey, fieldElement);
        }
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> copy() {
        return new org.apache.commons.math.linear.SparseFieldMatrix((org.apache.commons.math.linear.SparseFieldMatrix) this);
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> createMatrix(int i, int i2) throws java.lang.IllegalArgumentException {
        return new org.apache.commons.math.linear.SparseFieldMatrix(getField(), i, i2);
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.AnyMatrix
    public int getColumnDimension() {
        return this.columns;
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T getEntry(int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        checkRowIndex(i);
        checkColumnIndex(i2);
        return this.entries.get(computeKey(i, i2));
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.AnyMatrix
    public int getRowDimension() {
        return this.rows;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public void multiplyEntry(int i, int i2, T t) throws org.apache.commons.math.linear.MatrixIndexException {
        checkRowIndex(i);
        checkColumnIndex(i2);
        int computeKey = computeKey(i, i2);
        org.apache.commons.math.FieldElement fieldElement = (org.apache.commons.math.FieldElement) this.entries.get(computeKey).multiply(t);
        if (getField().getZero().equals(fieldElement)) {
            this.entries.remove(computeKey);
        } else {
            this.entries.put(computeKey, fieldElement);
        }
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public void setEntry(int i, int i2, T t) throws org.apache.commons.math.linear.MatrixIndexException {
        checkRowIndex(i);
        checkColumnIndex(i2);
        if (getField().getZero().equals(t)) {
            this.entries.remove(computeKey(i, i2));
        } else {
            this.entries.put(computeKey(i, i2), t);
        }
    }

    private int computeKey(int i, int i2) {
        return (i * this.columns) + i2;
    }
}
