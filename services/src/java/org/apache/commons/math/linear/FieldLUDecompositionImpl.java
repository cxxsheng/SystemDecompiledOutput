package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class FieldLUDecompositionImpl<T extends org.apache.commons.math.FieldElement<T>> implements org.apache.commons.math.linear.FieldLUDecomposition<T> {
    private org.apache.commons.math.linear.FieldMatrix<T> cachedL;
    private org.apache.commons.math.linear.FieldMatrix<T> cachedP;
    private org.apache.commons.math.linear.FieldMatrix<T> cachedU;
    private boolean even;
    private final org.apache.commons.math.Field<T> field;
    private T[][] lu;
    private int[] pivot;
    private boolean singular;

    public FieldLUDecompositionImpl(org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) throws org.apache.commons.math.linear.NonSquareMatrixException {
        if (!fieldMatrix.isSquare()) {
            throw new org.apache.commons.math.linear.NonSquareMatrixException(fieldMatrix.getRowDimension(), fieldMatrix.getColumnDimension());
        }
        int columnDimension = fieldMatrix.getColumnDimension();
        this.field = fieldMatrix.getField();
        this.lu = fieldMatrix.getData();
        this.pivot = new int[columnDimension];
        this.cachedL = null;
        this.cachedU = null;
        this.cachedP = null;
        for (int i = 0; i < columnDimension; i++) {
            this.pivot[i] = i;
        }
        this.even = true;
        this.singular = false;
        int i2 = 0;
        while (i2 < columnDimension) {
            this.field.getZero();
            for (int i3 = 0; i3 < i2; i3++) {
                org.apache.commons.math.FieldElement[] fieldElementArr = this.lu[i3];
                org.apache.commons.math.FieldElement fieldElement = fieldElementArr[i2];
                for (int i4 = 0; i4 < i3; i4++) {
                    fieldElement = (org.apache.commons.math.FieldElement) fieldElement.subtract((org.apache.commons.math.FieldElement) fieldElementArr[i4].multiply(this.lu[i4][i2]));
                }
                fieldElementArr[i2] = fieldElement;
            }
            int i5 = i2;
            int i6 = i5;
            while (i5 < columnDimension) {
                org.apache.commons.math.FieldElement[] fieldElementArr2 = this.lu[i5];
                org.apache.commons.math.FieldElement fieldElement2 = fieldElementArr2[i2];
                for (int i7 = 0; i7 < i2; i7++) {
                    fieldElement2 = (org.apache.commons.math.FieldElement) fieldElement2.subtract((org.apache.commons.math.FieldElement) fieldElementArr2[i7].multiply(this.lu[i7][i2]));
                }
                fieldElementArr2[i2] = fieldElement2;
                if (this.lu[i6][i2].equals(this.field.getZero())) {
                    i6++;
                }
                i5++;
            }
            if (i6 >= columnDimension) {
                this.singular = true;
                return;
            }
            if (i6 != i2) {
                this.field.getZero();
                for (int i8 = 0; i8 < columnDimension; i8++) {
                    T t = this.lu[i6][i8];
                    this.lu[i6][i8] = this.lu[i2][i8];
                    this.lu[i2][i8] = t;
                }
                int i9 = this.pivot[i6];
                this.pivot[i6] = this.pivot[i2];
                this.pivot[i2] = i9;
                this.even = !this.even;
            }
            T t2 = this.lu[i2][i2];
            int i10 = i2 + 1;
            for (int i11 = i10; i11 < columnDimension; i11++) {
                org.apache.commons.math.FieldElement[] fieldElementArr3 = this.lu[i11];
                fieldElementArr3[i2] = (org.apache.commons.math.FieldElement) fieldElementArr3[i2].divide(t2);
            }
            i2 = i10;
        }
    }

    @Override // org.apache.commons.math.linear.FieldLUDecomposition
    public org.apache.commons.math.linear.FieldMatrix<T> getL() {
        if (this.cachedL == null && !this.singular) {
            int length = this.pivot.length;
            this.cachedL = new org.apache.commons.math.linear.Array2DRowFieldMatrix(this.field, length, length);
            for (int i = 0; i < length; i++) {
                T[] tArr = this.lu[i];
                for (int i2 = 0; i2 < i; i2++) {
                    this.cachedL.setEntry(i, i2, tArr[i2]);
                }
                this.cachedL.setEntry(i, i, this.field.getOne());
            }
        }
        return this.cachedL;
    }

    @Override // org.apache.commons.math.linear.FieldLUDecomposition
    public org.apache.commons.math.linear.FieldMatrix<T> getU() {
        if (this.cachedU == null && !this.singular) {
            int length = this.pivot.length;
            this.cachedU = new org.apache.commons.math.linear.Array2DRowFieldMatrix(this.field, length, length);
            for (int i = 0; i < length; i++) {
                T[] tArr = this.lu[i];
                for (int i2 = i; i2 < length; i2++) {
                    this.cachedU.setEntry(i, i2, tArr[i2]);
                }
            }
        }
        return this.cachedU;
    }

    @Override // org.apache.commons.math.linear.FieldLUDecomposition
    public org.apache.commons.math.linear.FieldMatrix<T> getP() {
        if (this.cachedP == null && !this.singular) {
            int length = this.pivot.length;
            this.cachedP = new org.apache.commons.math.linear.Array2DRowFieldMatrix(this.field, length, length);
            for (int i = 0; i < length; i++) {
                this.cachedP.setEntry(i, this.pivot[i], this.field.getOne());
            }
        }
        return this.cachedP;
    }

    @Override // org.apache.commons.math.linear.FieldLUDecomposition
    public int[] getPivot() {
        return (int[]) this.pivot.clone();
    }

    @Override // org.apache.commons.math.linear.FieldLUDecomposition
    public T getDeterminant() {
        if (this.singular) {
            return this.field.getZero();
        }
        int length = this.pivot.length;
        T t = (T) (this.even ? this.field.getOne() : this.field.getZero().subtract(this.field.getOne()));
        for (int i = 0; i < length; i++) {
            t = (T) t.multiply(this.lu[i][i]);
        }
        return t;
    }

    @Override // org.apache.commons.math.linear.FieldLUDecomposition
    public org.apache.commons.math.linear.FieldDecompositionSolver<T> getSolver() {
        return new org.apache.commons.math.linear.FieldLUDecompositionImpl.Solver(this.field, this.lu, this.pivot, this.singular);
    }

    private static class Solver<T extends org.apache.commons.math.FieldElement<T>> implements org.apache.commons.math.linear.FieldDecompositionSolver<T> {
        private static final long serialVersionUID = -6353105415121373022L;
        private final org.apache.commons.math.Field<T> field;
        private final T[][] lu;
        private final int[] pivot;
        private final boolean singular;

        private Solver(org.apache.commons.math.Field<T> field, T[][] tArr, int[] iArr, boolean z) {
            this.field = field;
            this.lu = tArr;
            this.pivot = iArr;
            this.singular = z;
        }

        @Override // org.apache.commons.math.linear.FieldDecompositionSolver
        public boolean isNonSingular() {
            return !this.singular;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.apache.commons.math.linear.FieldDecompositionSolver
        public T[] solve(T[] tArr) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            int length = this.pivot.length;
            if (tArr.length != length) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(tArr.length), java.lang.Integer.valueOf(length));
            }
            if (this.singular) {
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }
            T[] tArr2 = (T[]) ((org.apache.commons.math.FieldElement[]) java.lang.reflect.Array.newInstance(this.field.getZero().getClass(), length));
            for (int i = 0; i < length; i++) {
                tArr2[i] = tArr[this.pivot[i]];
            }
            int i2 = 0;
            while (i2 < length) {
                org.apache.commons.math.complex.Complex complex = tArr2[i2];
                int i3 = i2 + 1;
                for (int i4 = i3; i4 < length; i4++) {
                    tArr2[i4] = (org.apache.commons.math.FieldElement) tArr2[i4].subtract((org.apache.commons.math.complex.Complex) complex.multiply((org.apache.commons.math.complex.Complex) this.lu[i4][i2]));
                }
                i2 = i3;
            }
            for (int i5 = length - 1; i5 >= 0; i5--) {
                tArr2[i5] = (org.apache.commons.math.FieldElement) tArr2[i5].divide((org.apache.commons.math.complex.Complex) this.lu[i5][i5]);
                org.apache.commons.math.complex.Complex complex2 = tArr2[i5];
                for (int i6 = 0; i6 < i5; i6++) {
                    tArr2[i6] = (org.apache.commons.math.FieldElement) tArr2[i6].subtract((org.apache.commons.math.complex.Complex) complex2.multiply((org.apache.commons.math.complex.Complex) this.lu[i6][i5]));
                }
            }
            return tArr2;
        }

        @Override // org.apache.commons.math.linear.FieldDecompositionSolver
        public org.apache.commons.math.linear.FieldVector<T> solve(org.apache.commons.math.linear.FieldVector<T> fieldVector) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            try {
                return solve((org.apache.commons.math.linear.ArrayFieldVector) fieldVector);
            } catch (java.lang.ClassCastException e) {
                int length = this.pivot.length;
                if (fieldVector.getDimension() != length) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(fieldVector.getDimension()), java.lang.Integer.valueOf(length));
                }
                if (this.singular) {
                    throw new org.apache.commons.math.linear.SingularMatrixException();
                }
                org.apache.commons.math.FieldElement[] fieldElementArr = (org.apache.commons.math.FieldElement[]) java.lang.reflect.Array.newInstance(this.field.getZero().getClass(), length);
                for (int i = 0; i < length; i++) {
                    fieldElementArr[i] = fieldVector.getEntry(this.pivot[i]);
                }
                int i2 = 0;
                while (i2 < length) {
                    org.apache.commons.math.FieldElement fieldElement = fieldElementArr[i2];
                    int i3 = i2 + 1;
                    for (int i4 = i3; i4 < length; i4++) {
                        fieldElementArr[i4] = (org.apache.commons.math.FieldElement) fieldElementArr[i4].subtract((org.apache.commons.math.FieldElement) fieldElement.multiply(this.lu[i4][i2]));
                    }
                    i2 = i3;
                }
                for (int i5 = length - 1; i5 >= 0; i5--) {
                    fieldElementArr[i5] = (org.apache.commons.math.FieldElement) fieldElementArr[i5].divide(this.lu[i5][i5]);
                    org.apache.commons.math.FieldElement fieldElement2 = fieldElementArr[i5];
                    for (int i6 = 0; i6 < i5; i6++) {
                        fieldElementArr[i6] = (org.apache.commons.math.FieldElement) fieldElementArr[i6].subtract((org.apache.commons.math.FieldElement) fieldElement2.multiply(this.lu[i6][i5]));
                    }
                }
                return new org.apache.commons.math.linear.ArrayFieldVector(fieldElementArr, false);
            }
        }

        public org.apache.commons.math.linear.ArrayFieldVector<T> solve(org.apache.commons.math.linear.ArrayFieldVector<T> arrayFieldVector) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            return new org.apache.commons.math.linear.ArrayFieldVector<>((org.apache.commons.math.FieldElement[]) solve(arrayFieldVector.getDataRef()), false);
        }

        @Override // org.apache.commons.math.linear.FieldDecompositionSolver
        public org.apache.commons.math.linear.FieldMatrix<T> solve(org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) throws java.lang.IllegalArgumentException, org.apache.commons.math.linear.InvalidMatrixException {
            int length = this.pivot.length;
            if (fieldMatrix.getRowDimension() != length) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(fieldMatrix.getRowDimension()), java.lang.Integer.valueOf(fieldMatrix.getColumnDimension()), java.lang.Integer.valueOf(length), "n");
            }
            if (this.singular) {
                throw new org.apache.commons.math.linear.SingularMatrixException();
            }
            int columnDimension = fieldMatrix.getColumnDimension();
            org.apache.commons.math.FieldElement[][] fieldElementArr = (org.apache.commons.math.FieldElement[][]) java.lang.reflect.Array.newInstance(this.field.getZero().getClass(), length, columnDimension);
            for (int i = 0; i < length; i++) {
                org.apache.commons.math.FieldElement[] fieldElementArr2 = fieldElementArr[i];
                int i2 = this.pivot[i];
                for (int i3 = 0; i3 < columnDimension; i3++) {
                    fieldElementArr2[i3] = fieldMatrix.getEntry(i2, i3);
                }
            }
            int i4 = 0;
            while (i4 < length) {
                org.apache.commons.math.FieldElement[] fieldElementArr3 = fieldElementArr[i4];
                int i5 = i4 + 1;
                for (int i6 = i5; i6 < length; i6++) {
                    org.apache.commons.math.FieldElement[] fieldElementArr4 = fieldElementArr[i6];
                    T t = this.lu[i6][i4];
                    for (int i7 = 0; i7 < columnDimension; i7++) {
                        fieldElementArr4[i7] = (org.apache.commons.math.FieldElement) fieldElementArr4[i7].subtract((org.apache.commons.math.FieldElement) fieldElementArr3[i7].multiply(t));
                    }
                }
                i4 = i5;
            }
            for (int i8 = length - 1; i8 >= 0; i8--) {
                org.apache.commons.math.FieldElement[] fieldElementArr5 = fieldElementArr[i8];
                T t2 = this.lu[i8][i8];
                for (int i9 = 0; i9 < columnDimension; i9++) {
                    fieldElementArr5[i9] = (org.apache.commons.math.FieldElement) fieldElementArr5[i9].divide(t2);
                }
                for (int i10 = 0; i10 < i8; i10++) {
                    org.apache.commons.math.FieldElement[] fieldElementArr6 = fieldElementArr[i10];
                    T t3 = this.lu[i10][i8];
                    for (int i11 = 0; i11 < columnDimension; i11++) {
                        fieldElementArr6[i11] = (org.apache.commons.math.FieldElement) fieldElementArr6[i11].subtract((org.apache.commons.math.FieldElement) fieldElementArr5[i11].multiply(t3));
                    }
                }
            }
            return new org.apache.commons.math.linear.Array2DRowFieldMatrix(fieldElementArr, false);
        }

        @Override // org.apache.commons.math.linear.FieldDecompositionSolver
        public org.apache.commons.math.linear.FieldMatrix<T> getInverse() throws org.apache.commons.math.linear.InvalidMatrixException {
            int length = this.pivot.length;
            T one = this.field.getOne();
            org.apache.commons.math.linear.FieldMatrix<T> array2DRowFieldMatrix = new org.apache.commons.math.linear.Array2DRowFieldMatrix<>(this.field, length, length);
            for (int i = 0; i < length; i++) {
                array2DRowFieldMatrix.setEntry(i, i, one);
            }
            return solve(array2DRowFieldMatrix);
        }
    }
}
