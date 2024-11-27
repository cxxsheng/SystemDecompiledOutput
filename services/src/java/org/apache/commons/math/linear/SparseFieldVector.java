package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class SparseFieldVector<T extends org.apache.commons.math.FieldElement<T>> implements org.apache.commons.math.linear.FieldVector<T>, java.io.Serializable {
    private static final long serialVersionUID = 7841233292190413362L;
    private final org.apache.commons.math.util.OpenIntToFieldHashMap<T> entries;
    private final org.apache.commons.math.Field<T> field;
    private final int virtualSize;

    public SparseFieldVector(org.apache.commons.math.Field<T> field) {
        this(field, 0);
    }

    public SparseFieldVector(org.apache.commons.math.Field<T> field, int i) {
        this.field = field;
        this.virtualSize = i;
        this.entries = new org.apache.commons.math.util.OpenIntToFieldHashMap<>(field);
    }

    protected SparseFieldVector(org.apache.commons.math.linear.SparseFieldVector<T> sparseFieldVector, int i) {
        this.field = sparseFieldVector.field;
        this.virtualSize = sparseFieldVector.getDimension() + i;
        this.entries = new org.apache.commons.math.util.OpenIntToFieldHashMap<>(sparseFieldVector.entries);
    }

    public SparseFieldVector(org.apache.commons.math.Field<T> field, int i, int i2) {
        this.field = field;
        this.virtualSize = i;
        this.entries = new org.apache.commons.math.util.OpenIntToFieldHashMap<>(field, i2);
    }

    public SparseFieldVector(org.apache.commons.math.Field<T> field, T[] tArr) {
        this.field = field;
        this.virtualSize = tArr.length;
        this.entries = new org.apache.commons.math.util.OpenIntToFieldHashMap<>(field);
        for (int i = 0; i < tArr.length; i++) {
            this.entries.put(i, tArr[i]);
        }
    }

    public SparseFieldVector(org.apache.commons.math.linear.SparseFieldVector<T> sparseFieldVector) {
        this.field = sparseFieldVector.field;
        this.virtualSize = sparseFieldVector.getDimension();
        this.entries = new org.apache.commons.math.util.OpenIntToFieldHashMap<>(sparseFieldVector.getEntries());
    }

    private org.apache.commons.math.util.OpenIntToFieldHashMap<T> getEntries() {
        return this.entries;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public org.apache.commons.math.linear.FieldVector<T> add(org.apache.commons.math.linear.SparseFieldVector<T> sparseFieldVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(sparseFieldVector.getDimension());
        org.apache.commons.math.linear.SparseFieldVector sparseFieldVector2 = (org.apache.commons.math.linear.SparseFieldVector) copy();
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = sparseFieldVector.getEntries().iterator();
        while (it.hasNext()) {
            it.advance();
            int key = it.key();
            T value = it.value();
            if (this.entries.containsKey(key)) {
                sparseFieldVector2.setEntry(key, (org.apache.commons.math.FieldElement) this.entries.get(key).add(value));
            } else {
                sparseFieldVector2.setEntry(key, value);
            }
        }
        return sparseFieldVector2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> add(T[] tArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(tArr.length);
        org.apache.commons.math.linear.SparseFieldVector sparseFieldVector = new org.apache.commons.math.linear.SparseFieldVector(this.field, getDimension());
        for (int i = 0; i < tArr.length; i++) {
            sparseFieldVector.setEntry(i, (org.apache.commons.math.FieldElement) tArr[i].add(getEntry(i)));
        }
        return sparseFieldVector;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public org.apache.commons.math.linear.FieldVector<T> append(org.apache.commons.math.linear.SparseFieldVector<T> sparseFieldVector) {
        org.apache.commons.math.linear.SparseFieldVector sparseFieldVector2 = new org.apache.commons.math.linear.SparseFieldVector(this, sparseFieldVector.getDimension());
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = sparseFieldVector.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            sparseFieldVector2.setEntry(it.key() + this.virtualSize, it.value());
        }
        return sparseFieldVector2;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> append(org.apache.commons.math.linear.FieldVector<T> fieldVector) {
        if (fieldVector instanceof org.apache.commons.math.linear.SparseFieldVector) {
            return append((org.apache.commons.math.linear.SparseFieldVector) fieldVector);
        }
        return append(fieldVector.toArray());
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> append(T t) {
        org.apache.commons.math.linear.SparseFieldVector sparseFieldVector = new org.apache.commons.math.linear.SparseFieldVector(this, 1);
        sparseFieldVector.setEntry(this.virtualSize, t);
        return sparseFieldVector;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> append(T[] tArr) {
        org.apache.commons.math.linear.SparseFieldVector sparseFieldVector = new org.apache.commons.math.linear.SparseFieldVector(this, tArr.length);
        for (int i = 0; i < tArr.length; i++) {
            sparseFieldVector.setEntry(this.virtualSize + i, tArr[i]);
        }
        return sparseFieldVector;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> copy() {
        return new org.apache.commons.math.linear.SparseFieldVector(this);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public T dotProduct(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(fieldVector.getDimension());
        T zero = this.field.getZero();
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            zero = (T) zero.add((org.apache.commons.math.FieldElement) fieldVector.getEntry(it.key()).multiply(it.value()));
        }
        return zero;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public T dotProduct(T[] tArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(tArr.length);
        T zero = this.field.getZero();
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            int key = it.key();
            T zero2 = this.field.getZero();
            if (key < tArr.length) {
                zero2 = tArr[key];
            }
            zero = (T) zero.add((org.apache.commons.math.FieldElement) zero2.multiply(it.value()));
        }
        return zero;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [org.apache.commons.math.FieldElement] */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> ebeDivide(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(fieldVector.getDimension());
        org.apache.commons.math.linear.SparseFieldVector sparseFieldVector = new org.apache.commons.math.linear.SparseFieldVector(this);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = sparseFieldVector.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            sparseFieldVector.setEntry(it.key(), (org.apache.commons.math.FieldElement) it.value().divide(fieldVector.getEntry(it.key())));
        }
        return sparseFieldVector;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [org.apache.commons.math.FieldElement] */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> ebeDivide(T[] tArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(tArr.length);
        org.apache.commons.math.linear.SparseFieldVector sparseFieldVector = new org.apache.commons.math.linear.SparseFieldVector(this);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = sparseFieldVector.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            sparseFieldVector.setEntry(it.key(), (org.apache.commons.math.FieldElement) it.value().divide(tArr[it.key()]));
        }
        return sparseFieldVector;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [org.apache.commons.math.FieldElement] */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> ebeMultiply(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(fieldVector.getDimension());
        org.apache.commons.math.linear.SparseFieldVector sparseFieldVector = new org.apache.commons.math.linear.SparseFieldVector(this);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = sparseFieldVector.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            sparseFieldVector.setEntry(it.key(), (org.apache.commons.math.FieldElement) it.value().multiply(fieldVector.getEntry(it.key())));
        }
        return sparseFieldVector;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [org.apache.commons.math.FieldElement] */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> ebeMultiply(T[] tArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(tArr.length);
        org.apache.commons.math.linear.SparseFieldVector sparseFieldVector = new org.apache.commons.math.linear.SparseFieldVector(this);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = sparseFieldVector.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            sparseFieldVector.setEntry(it.key(), (org.apache.commons.math.FieldElement) it.value().multiply(tArr[it.key()]));
        }
        return sparseFieldVector;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldVector
    public T[] getData() {
        T[] buildArray = buildArray(this.virtualSize);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            buildArray[it.key()] = it.value();
        }
        return buildArray;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public int getDimension() {
        return this.virtualSize;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public T getEntry(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(i);
        return this.entries.get(i);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.Field<T> getField() {
        return this.field;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> getSubVector(int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(i);
        int i3 = i + i2;
        checkIndex(i3 - 1);
        org.apache.commons.math.linear.SparseFieldVector sparseFieldVector = new org.apache.commons.math.linear.SparseFieldVector(this.field, i2);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            int key = it.key();
            if (key >= i && key < i3) {
                sparseFieldVector.setEntry(key - i, it.value());
            }
        }
        return sparseFieldVector;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapAdd(T t) {
        return copy().mapAddToSelf(t);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapAddToSelf(T t) {
        for (int i = 0; i < this.virtualSize; i++) {
            setEntry(i, (org.apache.commons.math.FieldElement) getEntry(i).add(t));
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapDivide(T t) {
        return copy().mapDivideToSelf(t);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [org.apache.commons.math.FieldElement] */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapDivideToSelf(T t) {
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            this.entries.put(it.key(), (org.apache.commons.math.FieldElement) it.value().divide(t));
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapInv() {
        return copy().mapInvToSelf();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapInvToSelf() {
        for (int i = 0; i < this.virtualSize; i++) {
            setEntry(i, (org.apache.commons.math.FieldElement) this.field.getOne().divide(getEntry(i)));
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapMultiply(T t) {
        return copy().mapMultiplyToSelf(t);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [org.apache.commons.math.FieldElement] */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapMultiplyToSelf(T t) {
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            this.entries.put(it.key(), (org.apache.commons.math.FieldElement) it.value().multiply(t));
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapSubtract(T t) {
        return copy().mapSubtractToSelf(t);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapSubtractToSelf(T t) {
        return mapAddToSelf((org.apache.commons.math.FieldElement) this.field.getZero().subtract(t));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [org.apache.commons.math.FieldElement] */
    public org.apache.commons.math.linear.FieldMatrix<T> outerProduct(org.apache.commons.math.linear.SparseFieldVector<T> sparseFieldVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(sparseFieldVector.getDimension());
        org.apache.commons.math.linear.SparseFieldMatrix sparseFieldMatrix = new org.apache.commons.math.linear.SparseFieldMatrix(this.field, this.virtualSize, this.virtualSize);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it2 = sparseFieldVector.entries.iterator();
            while (it2.hasNext()) {
                it2.advance();
                sparseFieldMatrix.setEntry(it.key(), it2.key(), (org.apache.commons.math.FieldElement) it.value().multiply(it2.value()));
            }
        }
        return sparseFieldMatrix;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [org.apache.commons.math.FieldElement] */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldMatrix<T> outerProduct(T[] tArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(tArr.length);
        org.apache.commons.math.linear.SparseFieldMatrix sparseFieldMatrix = new org.apache.commons.math.linear.SparseFieldMatrix(this.field, this.virtualSize, this.virtualSize);
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            int key = it.key();
            ?? value = it.value();
            for (int i = 0; i < this.virtualSize; i++) {
                sparseFieldMatrix.setEntry(key, i, (org.apache.commons.math.FieldElement) value.multiply(tArr[i]));
            }
        }
        return sparseFieldMatrix;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldMatrix<T> outerProduct(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        if (fieldVector instanceof org.apache.commons.math.linear.SparseFieldVector) {
            return outerProduct((org.apache.commons.math.linear.SparseFieldVector) fieldVector);
        }
        return outerProduct(fieldVector.toArray());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> projection(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(fieldVector.getDimension());
        return fieldVector.mapMultiply((org.apache.commons.math.FieldElement) dotProduct(fieldVector).divide(fieldVector.dotProduct(fieldVector)));
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> projection(T[] tArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(tArr.length);
        return projection(new org.apache.commons.math.linear.SparseFieldVector(this.field, tArr));
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public void set(T t) {
        for (int i = 0; i < this.virtualSize; i++) {
            setEntry(i, t);
        }
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public void setEntry(int i, T t) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(i);
        this.entries.put(i, t);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public void setSubVector(int i, org.apache.commons.math.linear.FieldVector<T> fieldVector) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(i);
        checkIndex((fieldVector.getDimension() + i) - 1);
        setSubVector(i, fieldVector.getData());
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public void setSubVector(int i, T[] tArr) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(i);
        checkIndex((tArr.length + i) - 1);
        for (int i2 = 0; i2 < tArr.length; i2++) {
            setEntry(i2 + i, tArr[i2]);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public org.apache.commons.math.linear.SparseFieldVector<T> subtract(org.apache.commons.math.linear.SparseFieldVector<T> sparseFieldVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(sparseFieldVector.getDimension());
        org.apache.commons.math.linear.SparseFieldVector<T> sparseFieldVector2 = (org.apache.commons.math.linear.SparseFieldVector<T>) ((org.apache.commons.math.linear.SparseFieldVector) copy());
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = sparseFieldVector.getEntries().iterator();
        while (it.hasNext()) {
            it.advance();
            int key = it.key();
            if (this.entries.containsKey(key)) {
                sparseFieldVector2.setEntry(key, (org.apache.commons.math.FieldElement) this.entries.get(key).subtract(it.value()));
            } else {
                sparseFieldVector2.setEntry(key, (org.apache.commons.math.FieldElement) this.field.getZero().subtract(it.value()));
            }
        }
        return sparseFieldVector2;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> subtract(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        if (fieldVector instanceof org.apache.commons.math.linear.SparseFieldVector) {
            return subtract((org.apache.commons.math.linear.SparseFieldVector) fieldVector);
        }
        return subtract(fieldVector.toArray());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> subtract(T[] tArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(tArr.length);
        org.apache.commons.math.linear.SparseFieldVector sparseFieldVector = new org.apache.commons.math.linear.SparseFieldVector(this);
        for (int i = 0; i < tArr.length; i++) {
            if (this.entries.containsKey(i)) {
                sparseFieldVector.setEntry(i, (org.apache.commons.math.FieldElement) this.entries.get(i).subtract(tArr[i]));
            } else {
                sparseFieldVector.setEntry(i, (org.apache.commons.math.FieldElement) this.field.getZero().subtract(tArr[i]));
            }
        }
        return sparseFieldVector;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public T[] toArray() {
        return getData();
    }

    private void checkIndex(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        if (i < 0 || i >= getDimension()) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.INDEX_OUT_OF_RANGE, java.lang.Integer.valueOf(i), 0, java.lang.Integer.valueOf(getDimension() - 1));
        }
    }

    protected void checkVectorDimensions(int i) throws java.lang.IllegalArgumentException {
        if (getDimension() != i) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(getDimension()), java.lang.Integer.valueOf(i));
        }
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> add(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        if (fieldVector instanceof org.apache.commons.math.linear.SparseFieldVector) {
            return add((org.apache.commons.math.linear.SparseFieldVector) fieldVector);
        }
        return add(fieldVector.toArray());
    }

    private T[] buildArray(int i) {
        return (T[]) ((org.apache.commons.math.FieldElement[]) java.lang.reflect.Array.newInstance(this.field.getZero().getClass(), i));
    }

    public int hashCode() {
        int hashCode = (((this.field == null ? 0 : this.field.hashCode()) + 31) * 31) + this.virtualSize;
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            hashCode = (hashCode * 31) + it.value().hashCode();
        }
        return hashCode;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.linear.SparseFieldVector)) {
            return false;
        }
        org.apache.commons.math.linear.SparseFieldVector sparseFieldVector = (org.apache.commons.math.linear.SparseFieldVector) obj;
        if (this.field == null) {
            if (sparseFieldVector.field != null) {
                return false;
            }
        } else if (!this.field.equals(sparseFieldVector.field)) {
            return false;
        }
        if (this.virtualSize != sparseFieldVector.virtualSize) {
            return false;
        }
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            if (!sparseFieldVector.getEntry(it.key()).equals(it.value())) {
                return false;
            }
        }
        org.apache.commons.math.util.OpenIntToFieldHashMap<T>.Iterator it2 = sparseFieldVector.getEntries().iterator();
        while (it2.hasNext()) {
            it2.advance();
            if (!it2.value().equals(getEntry(it2.key()))) {
                return false;
            }
        }
        return true;
    }
}
