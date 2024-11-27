package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class ArrayFieldVector<T extends org.apache.commons.math.FieldElement<T>> implements org.apache.commons.math.linear.FieldVector<T>, java.io.Serializable {
    private static final long serialVersionUID = 7648186910365927050L;
    protected T[] data;
    private final org.apache.commons.math.Field<T> field;

    public ArrayFieldVector(org.apache.commons.math.Field<T> field) {
        this(field, 0);
    }

    public ArrayFieldVector(org.apache.commons.math.Field<T> field, int i) {
        this.field = field;
        this.data = buildArray(i);
        java.util.Arrays.fill(this.data, field.getZero());
    }

    public ArrayFieldVector(int i, T t) {
        this(t.getField(), i);
        java.util.Arrays.fill(this.data, t);
    }

    public ArrayFieldVector(T[] tArr) throws java.lang.IllegalArgumentException {
        try {
            this.field = tArr[0].getField();
            this.data = (T[]) ((org.apache.commons.math.FieldElement[]) tArr.clone());
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_MUST_HAVE_AT_LEAST_ONE_ELEMENT, new java.lang.Object[0]);
        }
    }

    public ArrayFieldVector(org.apache.commons.math.Field<T> field, T[] tArr) {
        this.field = field;
        this.data = (T[]) ((org.apache.commons.math.FieldElement[]) tArr.clone());
    }

    public ArrayFieldVector(T[] tArr, boolean z) throws java.lang.NullPointerException, java.lang.IllegalArgumentException {
        if (tArr.length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_MUST_HAVE_AT_LEAST_ONE_ELEMENT, new java.lang.Object[0]);
        }
        this.field = tArr[0].getField();
        this.data = z ? (T[]) ((org.apache.commons.math.FieldElement[]) tArr.clone()) : tArr;
    }

    public ArrayFieldVector(org.apache.commons.math.Field<T> field, T[] tArr, boolean z) {
        this.field = field;
        this.data = z ? (T[]) ((org.apache.commons.math.FieldElement[]) tArr.clone()) : tArr;
    }

    public ArrayFieldVector(T[] tArr, int i, int i2) {
        if (tArr.length < i + i2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.POSITION_SIZE_MISMATCH_INPUT_ARRAY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(tArr.length));
        }
        this.field = tArr[0].getField();
        this.data = buildArray(i2);
        java.lang.System.arraycopy(tArr, i, this.data, 0, i2);
    }

    public ArrayFieldVector(org.apache.commons.math.linear.FieldVector<T> fieldVector) {
        this.field = fieldVector.getField();
        this.data = buildArray(fieldVector.getDimension());
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = fieldVector.getEntry(i);
        }
    }

    public ArrayFieldVector(org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector) {
        this.field = arrayFieldVector.getField();
        this.data = (T[]) ((org.apache.commons.math.FieldElement[]) arrayFieldVector.data.clone());
    }

    public ArrayFieldVector(org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector, boolean z) {
        this.field = arrayFieldVector.getField();
        T[] tArr = arrayFieldVector.data;
        this.data = z ? (T[]) ((org.apache.commons.math.FieldElement[]) tArr.clone()) : tArr;
    }

    public ArrayFieldVector(org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector, org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector2) {
        this.field = arrayFieldVector.getField();
        this.data = buildArray(arrayFieldVector.data.length + arrayFieldVector2.data.length);
        java.lang.System.arraycopy(arrayFieldVector.data, 0, this.data, 0, arrayFieldVector.data.length);
        java.lang.System.arraycopy(arrayFieldVector2.data, 0, this.data, arrayFieldVector.data.length, arrayFieldVector2.data.length);
    }

    public ArrayFieldVector(org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector, T[] tArr) {
        this.field = arrayFieldVector.getField();
        this.data = buildArray(arrayFieldVector.data.length + tArr.length);
        java.lang.System.arraycopy(arrayFieldVector.data, 0, this.data, 0, arrayFieldVector.data.length);
        java.lang.System.arraycopy(tArr, 0, this.data, arrayFieldVector.data.length, tArr.length);
    }

    public ArrayFieldVector(T[] tArr, org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector) {
        this.field = arrayFieldVector.getField();
        this.data = buildArray(tArr.length + arrayFieldVector.data.length);
        java.lang.System.arraycopy(tArr, 0, this.data, 0, tArr.length);
        java.lang.System.arraycopy(arrayFieldVector.data, 0, this.data, tArr.length, arrayFieldVector.data.length);
    }

    public ArrayFieldVector(T[] tArr, T[] tArr2) {
        try {
            this.data = buildArray(tArr.length + tArr2.length);
            java.lang.System.arraycopy(tArr, 0, this.data, 0, tArr.length);
            java.lang.System.arraycopy(tArr2, 0, this.data, tArr.length, tArr2.length);
            this.field = this.data[0].getField();
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_MUST_HAVE_AT_LEAST_ONE_ELEMENT, new java.lang.Object[0]);
        }
    }

    public ArrayFieldVector(org.apache.commons.math.Field<T> field, T[] tArr, T[] tArr2) {
        if (tArr.length + tArr2.length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_MUST_HAVE_AT_LEAST_ONE_ELEMENT, new java.lang.Object[0]);
        }
        this.data = buildArray(tArr.length + tArr2.length);
        java.lang.System.arraycopy(tArr, 0, this.data, 0, tArr.length);
        java.lang.System.arraycopy(tArr2, 0, this.data, tArr.length, tArr2.length);
        this.field = this.data[0].getField();
    }

    private T[] buildArray(int i) {
        return (T[]) ((org.apache.commons.math.FieldElement[]) java.lang.reflect.Array.newInstance(this.field.getZero().getClass(), i));
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.Field<T> getField() {
        return this.field;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> copy() {
        return new org.apache.commons.math.linear.ArrayFieldVector((org.apache.commons.math.linear.ArrayFieldVector) this, true);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> add(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        try {
            return add((org.apache.commons.math.linear.ArrayFieldVector) fieldVector);
        } catch (java.lang.ClassCastException e) {
            checkVectorDimensions(fieldVector);
            T[] buildArray = buildArray(this.data.length);
            for (int i = 0; i < this.data.length; i++) {
                buildArray[i] = (org.apache.commons.math.FieldElement) this.data[i].add(fieldVector.getEntry(i));
            }
            return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
        }
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> add(T[] tArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(tArr.length);
        T[] buildArray = buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            buildArray[i] = (org.apache.commons.math.FieldElement) this.data[i].add(tArr[i]);
        }
        return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
    }

    public org.apache.commons.math.linear.ArrayFieldVector<T> add(org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector) throws java.lang.IllegalArgumentException {
        return (org.apache.commons.math.linear.ArrayFieldVector) add(arrayFieldVector.data);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> subtract(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        try {
            return subtract((org.apache.commons.math.linear.ArrayFieldVector) fieldVector);
        } catch (java.lang.ClassCastException e) {
            checkVectorDimensions(fieldVector);
            T[] buildArray = buildArray(this.data.length);
            for (int i = 0; i < this.data.length; i++) {
                buildArray[i] = (org.apache.commons.math.FieldElement) this.data[i].subtract(fieldVector.getEntry(i));
            }
            return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
        }
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> subtract(T[] tArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(tArr.length);
        T[] buildArray = buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            buildArray[i] = (org.apache.commons.math.FieldElement) this.data[i].subtract(tArr[i]);
        }
        return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
    }

    public org.apache.commons.math.linear.ArrayFieldVector<T> subtract(org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector) throws java.lang.IllegalArgumentException {
        return (org.apache.commons.math.linear.ArrayFieldVector) subtract(arrayFieldVector.data);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapAdd(T t) {
        T[] buildArray = buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            buildArray[i] = (org.apache.commons.math.FieldElement) this.data[i].add(t);
        }
        return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapAddToSelf(T t) {
        for (int i = 0; i < this.data.length; i++) {
            ((T[]) this.data)[i] = (org.apache.commons.math.FieldElement) this.data[i].add(t);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapSubtract(T t) {
        T[] buildArray = buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            buildArray[i] = (org.apache.commons.math.FieldElement) this.data[i].subtract(t);
        }
        return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapSubtractToSelf(T t) {
        for (int i = 0; i < this.data.length; i++) {
            ((T[]) this.data)[i] = (org.apache.commons.math.FieldElement) this.data[i].subtract(t);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapMultiply(T t) {
        T[] buildArray = buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            buildArray[i] = (org.apache.commons.math.FieldElement) this.data[i].multiply(t);
        }
        return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapMultiplyToSelf(T t) {
        for (int i = 0; i < this.data.length; i++) {
            ((T[]) this.data)[i] = (org.apache.commons.math.FieldElement) this.data[i].multiply(t);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapDivide(T t) {
        T[] buildArray = buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            buildArray[i] = (org.apache.commons.math.FieldElement) this.data[i].divide(t);
        }
        return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapDivideToSelf(T t) {
        for (int i = 0; i < this.data.length; i++) {
            ((T[]) this.data)[i] = (org.apache.commons.math.FieldElement) this.data[i].divide(t);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapInv() {
        T[] buildArray = buildArray(this.data.length);
        T one = this.field.getOne();
        for (int i = 0; i < this.data.length; i++) {
            buildArray[i] = (org.apache.commons.math.FieldElement) one.divide(this.data[i]);
        }
        return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> mapInvToSelf() {
        T one = this.field.getOne();
        for (int i = 0; i < this.data.length; i++) {
            ((T[]) this.data)[i] = (org.apache.commons.math.FieldElement) one.divide(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> ebeMultiply(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        try {
            return ebeMultiply((org.apache.commons.math.linear.ArrayFieldVector) fieldVector);
        } catch (java.lang.ClassCastException e) {
            checkVectorDimensions(fieldVector);
            T[] buildArray = buildArray(this.data.length);
            for (int i = 0; i < this.data.length; i++) {
                buildArray[i] = (org.apache.commons.math.FieldElement) this.data[i].multiply(fieldVector.getEntry(i));
            }
            return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
        }
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> ebeMultiply(T[] tArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(tArr.length);
        T[] buildArray = buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            buildArray[i] = (org.apache.commons.math.FieldElement) this.data[i].multiply(tArr[i]);
        }
        return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
    }

    public org.apache.commons.math.linear.ArrayFieldVector<T> ebeMultiply(org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector) throws java.lang.IllegalArgumentException {
        return (org.apache.commons.math.linear.ArrayFieldVector) ebeMultiply(arrayFieldVector.data);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> ebeDivide(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        try {
            return ebeDivide((org.apache.commons.math.linear.ArrayFieldVector) fieldVector);
        } catch (java.lang.ClassCastException e) {
            checkVectorDimensions(fieldVector);
            T[] buildArray = buildArray(this.data.length);
            for (int i = 0; i < this.data.length; i++) {
                buildArray[i] = (org.apache.commons.math.FieldElement) this.data[i].divide(fieldVector.getEntry(i));
            }
            return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
        }
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> ebeDivide(T[] tArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(tArr.length);
        T[] buildArray = buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            buildArray[i] = (org.apache.commons.math.FieldElement) this.data[i].divide(tArr[i]);
        }
        return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
    }

    public org.apache.commons.math.linear.ArrayFieldVector<T> ebeDivide(org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector) throws java.lang.IllegalArgumentException {
        return (org.apache.commons.math.linear.ArrayFieldVector) ebeDivide(arrayFieldVector.data);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public T[] getData() {
        return (T[]) ((org.apache.commons.math.FieldElement[]) this.data.clone());
    }

    public T[] getDataRef() {
        return this.data;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public T dotProduct(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        try {
            return dotProduct((org.apache.commons.math.linear.ArrayFieldVector) fieldVector);
        } catch (java.lang.ClassCastException e) {
            checkVectorDimensions(fieldVector);
            T zero = this.field.getZero();
            for (int i = 0; i < this.data.length; i++) {
                zero = (T) zero.add((org.apache.commons.math.FieldElement) this.data[i].multiply(fieldVector.getEntry(i)));
            }
            return zero;
        }
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public T dotProduct(T[] tArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(tArr.length);
        T zero = this.field.getZero();
        for (int i = 0; i < this.data.length; i++) {
            zero = (T) zero.add((org.apache.commons.math.FieldElement) this.data[i].multiply(tArr[i]));
        }
        return zero;
    }

    public T dotProduct(org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector) throws java.lang.IllegalArgumentException {
        return dotProduct(arrayFieldVector.data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> projection(org.apache.commons.math.linear.FieldVector<T> fieldVector) {
        return fieldVector.mapMultiply((org.apache.commons.math.FieldElement) dotProduct(fieldVector).divide(fieldVector.dotProduct(fieldVector)));
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> projection(T[] tArr) {
        return projection((org.apache.commons.math.linear.ArrayFieldVector) new org.apache.commons.math.linear.ArrayFieldVector<>((org.apache.commons.math.FieldElement[]) tArr, false));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public org.apache.commons.math.linear.ArrayFieldVector<T> projection(org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector) {
        return (org.apache.commons.math.linear.ArrayFieldVector) arrayFieldVector.mapMultiply((org.apache.commons.math.FieldElement) dotProduct((org.apache.commons.math.linear.ArrayFieldVector) arrayFieldVector).divide(arrayFieldVector.dotProduct((org.apache.commons.math.linear.ArrayFieldVector) arrayFieldVector)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldMatrix<T> outerProduct(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        try {
            return outerProduct((org.apache.commons.math.linear.ArrayFieldVector) fieldVector);
        } catch (java.lang.ClassCastException e) {
            checkVectorDimensions(fieldVector);
            int length = this.data.length;
            org.apache.commons.math.linear.Array2DRowFieldMatrix array2DRowFieldMatrix = new org.apache.commons.math.linear.Array2DRowFieldMatrix(this.field, length, length);
            for (int i = 0; i < this.data.length; i++) {
                for (int i2 = 0; i2 < this.data.length; i2++) {
                    array2DRowFieldMatrix.setEntry(i, i2, (org.apache.commons.math.FieldElement) this.data[i].multiply(fieldVector.getEntry(i2)));
                }
            }
            return array2DRowFieldMatrix;
        }
    }

    public org.apache.commons.math.linear.FieldMatrix<T> outerProduct(org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector) throws java.lang.IllegalArgumentException {
        return outerProduct(arrayFieldVector.data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldMatrix<T> outerProduct(T[] tArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(tArr.length);
        int length = this.data.length;
        org.apache.commons.math.linear.Array2DRowFieldMatrix array2DRowFieldMatrix = new org.apache.commons.math.linear.Array2DRowFieldMatrix(this.field, length, length);
        for (int i = 0; i < this.data.length; i++) {
            for (int i2 = 0; i2 < this.data.length; i2++) {
                array2DRowFieldMatrix.setEntry(i, i2, (org.apache.commons.math.FieldElement) this.data[i].multiply(tArr[i2]));
            }
        }
        return array2DRowFieldMatrix;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public T getEntry(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        return this.data[i];
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public int getDimension() {
        return this.data.length;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> append(org.apache.commons.math.linear.FieldVector<T> fieldVector) {
        try {
            return append((org.apache.commons.math.linear.ArrayFieldVector) fieldVector);
        } catch (java.lang.ClassCastException e) {
            return new org.apache.commons.math.linear.ArrayFieldVector(this, new org.apache.commons.math.linear.ArrayFieldVector(fieldVector));
        }
    }

    public org.apache.commons.math.linear.ArrayFieldVector<T> append(org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector) {
        return new org.apache.commons.math.linear.ArrayFieldVector<>(this, arrayFieldVector);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> append(T t) {
        T[] buildArray = buildArray(this.data.length + 1);
        java.lang.System.arraycopy(this.data, 0, buildArray, 0, this.data.length);
        buildArray[this.data.length] = t;
        return new org.apache.commons.math.linear.ArrayFieldVector(buildArray);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> append(T[] tArr) {
        return new org.apache.commons.math.linear.ArrayFieldVector(this, tArr);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public org.apache.commons.math.linear.FieldVector<T> getSubVector(int i, int i2) {
        org.apache.commons.math.linear.ArrayFieldVector arrayFieldVector = new org.apache.commons.math.linear.ArrayFieldVector(this.field, i2);
        try {
            java.lang.System.arraycopy(this.data, i, arrayFieldVector.data, 0, i2);
        } catch (java.lang.IndexOutOfBoundsException e) {
            checkIndex(i);
            checkIndex((i + i2) - 1);
        }
        return arrayFieldVector;
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public void setEntry(int i, T t) {
        try {
            this.data[i] = t;
        } catch (java.lang.IndexOutOfBoundsException e) {
            checkIndex(i);
        }
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public void setSubVector(int i, org.apache.commons.math.linear.FieldVector<T> fieldVector) {
        try {
            try {
                set(i, (org.apache.commons.math.linear.ArrayFieldVector) fieldVector);
            } catch (java.lang.ClassCastException e) {
                for (int i2 = i; i2 < fieldVector.getDimension() + i; i2++) {
                    this.data[i2] = fieldVector.getEntry(i2 - i);
                }
            }
        } catch (java.lang.IndexOutOfBoundsException e2) {
            checkIndex(i);
            checkIndex((i + fieldVector.getDimension()) - 1);
        }
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public void setSubVector(int i, T[] tArr) {
        try {
            java.lang.System.arraycopy(tArr, 0, this.data, i, tArr.length);
        } catch (java.lang.IndexOutOfBoundsException e) {
            checkIndex(i);
            checkIndex((i + tArr.length) - 1);
        }
    }

    public void set(int i, org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector) throws org.apache.commons.math.linear.MatrixIndexException {
        setSubVector(i, arrayFieldVector.data);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public void set(T t) {
        java.util.Arrays.fill(this.data, t);
    }

    @Override // org.apache.commons.math.linear.FieldVector
    public T[] toArray() {
        return (T[]) ((org.apache.commons.math.FieldElement[]) this.data.clone());
    }

    protected void checkVectorDimensions(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(fieldVector.getDimension());
    }

    protected void checkVectorDimensions(int i) throws java.lang.IllegalArgumentException {
        if (this.data.length != i) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(this.data.length), java.lang.Integer.valueOf(i));
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        try {
            org.apache.commons.math.linear.FieldVector fieldVector = (org.apache.commons.math.linear.FieldVector) obj;
            if (this.data.length != fieldVector.getDimension()) {
                return false;
            }
            for (int i = 0; i < this.data.length; i++) {
                if (!this.data[i].equals(fieldVector.getEntry(i))) {
                    return false;
                }
            }
            return true;
        } catch (java.lang.ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        int i = 3542;
        for (T t : this.data) {
            i ^= t.hashCode();
        }
        return i;
    }

    private void checkIndex(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        if (i < 0 || i >= getDimension()) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.INDEX_OUT_OF_RANGE, java.lang.Integer.valueOf(i), 0, java.lang.Integer.valueOf(getDimension() - 1));
        }
    }
}
