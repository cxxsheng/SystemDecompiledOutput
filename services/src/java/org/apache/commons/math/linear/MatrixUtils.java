package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class MatrixUtils {
    private MatrixUtils() {
    }

    public static org.apache.commons.math.linear.RealMatrix createRealMatrix(int i, int i2) {
        return i * i2 <= 4096 ? new org.apache.commons.math.linear.Array2DRowRealMatrix(i, i2) : new org.apache.commons.math.linear.BlockRealMatrix(i, i2);
    }

    public static <T extends org.apache.commons.math.FieldElement<T>> org.apache.commons.math.linear.FieldMatrix<T> createFieldMatrix(org.apache.commons.math.Field<T> field, int i, int i2) {
        return i * i2 <= 4096 ? new org.apache.commons.math.linear.Array2DRowFieldMatrix(field, i, i2) : new org.apache.commons.math.linear.BlockFieldMatrix(field, i, i2);
    }

    public static org.apache.commons.math.linear.RealMatrix createRealMatrix(double[][] dArr) {
        return dArr.length * dArr[0].length <= 4096 ? new org.apache.commons.math.linear.Array2DRowRealMatrix(dArr) : new org.apache.commons.math.linear.BlockRealMatrix(dArr);
    }

    public static <T extends org.apache.commons.math.FieldElement<T>> org.apache.commons.math.linear.FieldMatrix<T> createFieldMatrix(T[][] tArr) {
        return tArr.length * tArr[0].length <= 4096 ? new org.apache.commons.math.linear.Array2DRowFieldMatrix(tArr) : new org.apache.commons.math.linear.BlockFieldMatrix(tArr);
    }

    public static org.apache.commons.math.linear.RealMatrix createRealIdentityMatrix(int i) {
        org.apache.commons.math.linear.RealMatrix createRealMatrix = createRealMatrix(i, i);
        for (int i2 = 0; i2 < i; i2++) {
            createRealMatrix.setEntry(i2, i2, 1.0d);
        }
        return createRealMatrix;
    }

    public static <T extends org.apache.commons.math.FieldElement<T>> org.apache.commons.math.linear.FieldMatrix<T> createFieldIdentityMatrix(org.apache.commons.math.Field<T> field, int i) {
        T zero = field.getZero();
        T one = field.getOne();
        org.apache.commons.math.FieldElement[][] fieldElementArr = (org.apache.commons.math.FieldElement[][]) java.lang.reflect.Array.newInstance(zero.getClass(), i, i);
        for (int i2 = 0; i2 < i; i2++) {
            org.apache.commons.math.FieldElement[] fieldElementArr2 = fieldElementArr[i2];
            java.util.Arrays.fill(fieldElementArr2, zero);
            fieldElementArr2[i2] = one;
        }
        return new org.apache.commons.math.linear.Array2DRowFieldMatrix(fieldElementArr, false);
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.linear.BigMatrix createBigIdentityMatrix(int i) {
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, i, i);
        for (int i2 = 0; i2 < i; i2++) {
            java.math.BigDecimal[] bigDecimalArr2 = bigDecimalArr[i2];
            java.util.Arrays.fill(bigDecimalArr2, org.apache.commons.math.linear.BigMatrixImpl.ZERO);
            bigDecimalArr2[i2] = org.apache.commons.math.linear.BigMatrixImpl.ONE;
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    public static org.apache.commons.math.linear.RealMatrix createRealDiagonalMatrix(double[] dArr) {
        org.apache.commons.math.linear.RealMatrix createRealMatrix = createRealMatrix(dArr.length, dArr.length);
        for (int i = 0; i < dArr.length; i++) {
            createRealMatrix.setEntry(i, i, dArr[i]);
        }
        return createRealMatrix;
    }

    public static <T extends org.apache.commons.math.FieldElement<T>> org.apache.commons.math.linear.FieldMatrix<T> createFieldDiagonalMatrix(T[] tArr) {
        org.apache.commons.math.linear.FieldMatrix<T> createFieldMatrix = createFieldMatrix(tArr[0].getField(), tArr.length, tArr.length);
        for (int i = 0; i < tArr.length; i++) {
            createFieldMatrix.setEntry(i, i, tArr[i]);
        }
        return createFieldMatrix;
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.linear.BigMatrix createBigMatrix(double[][] dArr) {
        return new org.apache.commons.math.linear.BigMatrixImpl(dArr);
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.linear.BigMatrix createBigMatrix(java.math.BigDecimal[][] bigDecimalArr) {
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr);
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.linear.BigMatrix createBigMatrix(java.math.BigDecimal[][] bigDecimalArr, boolean z) {
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, z);
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.linear.BigMatrix createBigMatrix(java.lang.String[][] strArr) {
        return new org.apache.commons.math.linear.BigMatrixImpl(strArr);
    }

    public static org.apache.commons.math.linear.RealVector createRealVector(double[] dArr) {
        return new org.apache.commons.math.linear.ArrayRealVector(dArr, true);
    }

    public static <T extends org.apache.commons.math.FieldElement<T>> org.apache.commons.math.linear.FieldVector<T> createFieldVector(T[] tArr) {
        return new org.apache.commons.math.linear.ArrayFieldVector((org.apache.commons.math.FieldElement[]) tArr, true);
    }

    public static org.apache.commons.math.linear.RealMatrix createRowRealMatrix(double[] dArr) {
        int length = dArr.length;
        org.apache.commons.math.linear.RealMatrix createRealMatrix = createRealMatrix(1, length);
        for (int i = 0; i < length; i++) {
            createRealMatrix.setEntry(0, i, dArr[i]);
        }
        return createRealMatrix;
    }

    public static <T extends org.apache.commons.math.FieldElement<T>> org.apache.commons.math.linear.FieldMatrix<T> createRowFieldMatrix(T[] tArr) {
        int length = tArr.length;
        if (length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_COLUMN, new java.lang.Object[0]);
        }
        org.apache.commons.math.linear.FieldMatrix<T> createFieldMatrix = createFieldMatrix(tArr[0].getField(), 1, length);
        for (int i = 0; i < length; i++) {
            createFieldMatrix.setEntry(0, i, tArr[i]);
        }
        return createFieldMatrix;
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.linear.BigMatrix createRowBigMatrix(double[] dArr) {
        int length = dArr.length;
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, 1, length);
        for (int i = 0; i < length; i++) {
            bigDecimalArr[0][i] = new java.math.BigDecimal(dArr[i]);
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.linear.BigMatrix createRowBigMatrix(java.math.BigDecimal[] bigDecimalArr) {
        int length = bigDecimalArr.length;
        java.math.BigDecimal[][] bigDecimalArr2 = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, 1, length);
        java.lang.System.arraycopy(bigDecimalArr, 0, bigDecimalArr2[0], 0, length);
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr2, false);
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.linear.BigMatrix createRowBigMatrix(java.lang.String[] strArr) {
        int length = strArr.length;
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, 1, length);
        for (int i = 0; i < length; i++) {
            bigDecimalArr[0][i] = new java.math.BigDecimal(strArr[i]);
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    public static org.apache.commons.math.linear.RealMatrix createColumnRealMatrix(double[] dArr) {
        int length = dArr.length;
        org.apache.commons.math.linear.RealMatrix createRealMatrix = createRealMatrix(length, 1);
        for (int i = 0; i < length; i++) {
            createRealMatrix.setEntry(i, 0, dArr[i]);
        }
        return createRealMatrix;
    }

    public static <T extends org.apache.commons.math.FieldElement<T>> org.apache.commons.math.linear.FieldMatrix<T> createColumnFieldMatrix(T[] tArr) {
        int length = tArr.length;
        if (length == 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_ROW, new java.lang.Object[0]);
        }
        org.apache.commons.math.linear.FieldMatrix<T> createFieldMatrix = createFieldMatrix(tArr[0].getField(), length, 1);
        for (int i = 0; i < length; i++) {
            createFieldMatrix.setEntry(i, 0, tArr[i]);
        }
        return createFieldMatrix;
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.linear.BigMatrix createColumnBigMatrix(double[] dArr) {
        int length = dArr.length;
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, length, 1);
        for (int i = 0; i < length; i++) {
            bigDecimalArr[i][0] = new java.math.BigDecimal(dArr[i]);
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.linear.BigMatrix createColumnBigMatrix(java.math.BigDecimal[] bigDecimalArr) {
        int length = bigDecimalArr.length;
        java.math.BigDecimal[][] bigDecimalArr2 = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, length, 1);
        for (int i = 0; i < length; i++) {
            bigDecimalArr2[i][0] = bigDecimalArr[i];
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr2, false);
    }

    @java.lang.Deprecated
    public static org.apache.commons.math.linear.BigMatrix createColumnBigMatrix(java.lang.String[] strArr) {
        int length = strArr.length;
        java.math.BigDecimal[][] bigDecimalArr = (java.math.BigDecimal[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.math.BigDecimal.class, length, 1);
        for (int i = 0; i < length; i++) {
            bigDecimalArr[i][0] = new java.math.BigDecimal(strArr[i]);
        }
        return new org.apache.commons.math.linear.BigMatrixImpl(bigDecimalArr, false);
    }

    public static void checkRowIndex(org.apache.commons.math.linear.AnyMatrix anyMatrix, int i) {
        if (i < 0 || i >= anyMatrix.getRowDimension()) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.ROW_INDEX_OUT_OF_RANGE, java.lang.Integer.valueOf(i), 0, java.lang.Integer.valueOf(anyMatrix.getRowDimension() - 1));
        }
    }

    public static void checkColumnIndex(org.apache.commons.math.linear.AnyMatrix anyMatrix, int i) throws org.apache.commons.math.linear.MatrixIndexException {
        if (i < 0 || i >= anyMatrix.getColumnDimension()) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.COLUMN_INDEX_OUT_OF_RANGE, java.lang.Integer.valueOf(i), 0, java.lang.Integer.valueOf(anyMatrix.getColumnDimension() - 1));
        }
    }

    public static void checkSubMatrixIndex(org.apache.commons.math.linear.AnyMatrix anyMatrix, int i, int i2, int i3, int i4) {
        checkRowIndex(anyMatrix, i);
        checkRowIndex(anyMatrix, i2);
        if (i > i2) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.INITIAL_ROW_AFTER_FINAL_ROW, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        checkColumnIndex(anyMatrix, i3);
        checkColumnIndex(anyMatrix, i4);
        if (i3 > i4) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.INITIAL_COLUMN_AFTER_FINAL_COLUMN, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4));
        }
    }

    public static void checkSubMatrixIndex(org.apache.commons.math.linear.AnyMatrix anyMatrix, int[] iArr, int[] iArr2) throws org.apache.commons.math.linear.MatrixIndexException {
        if (iArr.length * iArr2.length == 0) {
            if (iArr.length == 0) {
                throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_SELECTED_ROW_INDEX_ARRAY, new java.lang.Object[0]);
            }
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.EMPTY_SELECTED_COLUMN_INDEX_ARRAY, new java.lang.Object[0]);
        }
        for (int i : iArr) {
            checkRowIndex(anyMatrix, i);
        }
        for (int i2 : iArr2) {
            checkColumnIndex(anyMatrix, i2);
        }
    }

    public static void checkAdditionCompatible(org.apache.commons.math.linear.AnyMatrix anyMatrix, org.apache.commons.math.linear.AnyMatrix anyMatrix2) throws java.lang.IllegalArgumentException {
        if (anyMatrix.getRowDimension() != anyMatrix2.getRowDimension() || anyMatrix.getColumnDimension() != anyMatrix2.getColumnDimension()) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_ADDITION_COMPATIBLE_MATRICES, java.lang.Integer.valueOf(anyMatrix.getRowDimension()), java.lang.Integer.valueOf(anyMatrix.getColumnDimension()), java.lang.Integer.valueOf(anyMatrix2.getRowDimension()), java.lang.Integer.valueOf(anyMatrix2.getColumnDimension()));
        }
    }

    public static void checkSubtractionCompatible(org.apache.commons.math.linear.AnyMatrix anyMatrix, org.apache.commons.math.linear.AnyMatrix anyMatrix2) throws java.lang.IllegalArgumentException {
        if (anyMatrix.getRowDimension() != anyMatrix2.getRowDimension() || anyMatrix.getColumnDimension() != anyMatrix2.getColumnDimension()) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_SUBTRACTION_COMPATIBLE_MATRICES, java.lang.Integer.valueOf(anyMatrix.getRowDimension()), java.lang.Integer.valueOf(anyMatrix.getColumnDimension()), java.lang.Integer.valueOf(anyMatrix2.getRowDimension()), java.lang.Integer.valueOf(anyMatrix2.getColumnDimension()));
        }
    }

    public static void checkMultiplicationCompatible(org.apache.commons.math.linear.AnyMatrix anyMatrix, org.apache.commons.math.linear.AnyMatrix anyMatrix2) throws java.lang.IllegalArgumentException {
        if (anyMatrix.getColumnDimension() != anyMatrix2.getRowDimension()) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NOT_MULTIPLICATION_COMPATIBLE_MATRICES, java.lang.Integer.valueOf(anyMatrix.getRowDimension()), java.lang.Integer.valueOf(anyMatrix.getColumnDimension()), java.lang.Integer.valueOf(anyMatrix2.getRowDimension()), java.lang.Integer.valueOf(anyMatrix2.getColumnDimension()));
        }
    }

    public static org.apache.commons.math.linear.Array2DRowRealMatrix fractionMatrixToRealMatrix(org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.Fraction> fieldMatrix) {
        org.apache.commons.math.linear.MatrixUtils.FractionMatrixConverter fractionMatrixConverter = new org.apache.commons.math.linear.MatrixUtils.FractionMatrixConverter();
        fieldMatrix.walkInOptimizedOrder(fractionMatrixConverter);
        return fractionMatrixConverter.getConvertedMatrix();
    }

    private static class FractionMatrixConverter extends org.apache.commons.math.linear.DefaultFieldMatrixPreservingVisitor<org.apache.commons.math.fraction.Fraction> {
        private double[][] data;

        public FractionMatrixConverter() {
            super(org.apache.commons.math.fraction.Fraction.ZERO);
        }

        @Override // org.apache.commons.math.linear.DefaultFieldMatrixPreservingVisitor, org.apache.commons.math.linear.FieldMatrixPreservingVisitor
        public void start(int i, int i2, int i3, int i4, int i5, int i6) {
            this.data = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i, i2);
        }

        @Override // org.apache.commons.math.linear.DefaultFieldMatrixPreservingVisitor, org.apache.commons.math.linear.FieldMatrixPreservingVisitor
        public void visit(int i, int i2, org.apache.commons.math.fraction.Fraction fraction) {
            this.data[i][i2] = fraction.doubleValue();
        }

        org.apache.commons.math.linear.Array2DRowRealMatrix getConvertedMatrix() {
            return new org.apache.commons.math.linear.Array2DRowRealMatrix(this.data, false);
        }
    }

    public static org.apache.commons.math.linear.Array2DRowRealMatrix bigFractionMatrixToRealMatrix(org.apache.commons.math.linear.FieldMatrix<org.apache.commons.math.fraction.BigFraction> fieldMatrix) {
        org.apache.commons.math.linear.MatrixUtils.BigFractionMatrixConverter bigFractionMatrixConverter = new org.apache.commons.math.linear.MatrixUtils.BigFractionMatrixConverter();
        fieldMatrix.walkInOptimizedOrder(bigFractionMatrixConverter);
        return bigFractionMatrixConverter.getConvertedMatrix();
    }

    private static class BigFractionMatrixConverter extends org.apache.commons.math.linear.DefaultFieldMatrixPreservingVisitor<org.apache.commons.math.fraction.BigFraction> {
        private double[][] data;

        public BigFractionMatrixConverter() {
            super(org.apache.commons.math.fraction.BigFraction.ZERO);
        }

        @Override // org.apache.commons.math.linear.DefaultFieldMatrixPreservingVisitor, org.apache.commons.math.linear.FieldMatrixPreservingVisitor
        public void start(int i, int i2, int i3, int i4, int i5, int i6) {
            this.data = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, i, i2);
        }

        @Override // org.apache.commons.math.linear.DefaultFieldMatrixPreservingVisitor, org.apache.commons.math.linear.FieldMatrixPreservingVisitor
        public void visit(int i, int i2, org.apache.commons.math.fraction.BigFraction bigFraction) {
            this.data[i][i2] = bigFraction.doubleValue();
        }

        org.apache.commons.math.linear.Array2DRowRealMatrix getConvertedMatrix() {
            return new org.apache.commons.math.linear.Array2DRowRealMatrix(this.data, false);
        }
    }

    public static void serializeRealVector(org.apache.commons.math.linear.RealVector realVector, java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        int dimension = realVector.getDimension();
        objectOutputStream.writeInt(dimension);
        for (int i = 0; i < dimension; i++) {
            objectOutputStream.writeDouble(realVector.getEntry(i));
        }
    }

    public static void deserializeRealVector(java.lang.Object obj, java.lang.String str, java.io.ObjectInputStream objectInputStream) throws java.lang.ClassNotFoundException, java.io.IOException {
        try {
            int readInt = objectInputStream.readInt();
            double[] dArr = new double[readInt];
            for (int i = 0; i < readInt; i++) {
                dArr[i] = objectInputStream.readDouble();
            }
            org.apache.commons.math.linear.ArrayRealVector arrayRealVector = new org.apache.commons.math.linear.ArrayRealVector(dArr, false);
            java.lang.reflect.Field declaredField = obj.getClass().getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(obj, arrayRealVector);
        } catch (java.lang.IllegalAccessException e) {
            java.io.IOException iOException = new java.io.IOException();
            iOException.initCause(e);
            throw iOException;
        } catch (java.lang.NoSuchFieldException e2) {
            java.io.IOException iOException2 = new java.io.IOException();
            iOException2.initCause(e2);
            throw iOException2;
        }
    }

    public static void serializeRealMatrix(org.apache.commons.math.linear.RealMatrix realMatrix, java.io.ObjectOutputStream objectOutputStream) throws java.io.IOException {
        int rowDimension = realMatrix.getRowDimension();
        int columnDimension = realMatrix.getColumnDimension();
        objectOutputStream.writeInt(rowDimension);
        objectOutputStream.writeInt(columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int i2 = 0; i2 < columnDimension; i2++) {
                objectOutputStream.writeDouble(realMatrix.getEntry(i, i2));
            }
        }
    }

    public static void deserializeRealMatrix(java.lang.Object obj, java.lang.String str, java.io.ObjectInputStream objectInputStream) throws java.lang.ClassNotFoundException, java.io.IOException {
        try {
            int readInt = objectInputStream.readInt();
            int readInt2 = objectInputStream.readInt();
            double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, readInt, readInt2);
            for (int i = 0; i < readInt; i++) {
                double[] dArr2 = dArr[i];
                for (int i2 = 0; i2 < readInt2; i2++) {
                    dArr2[i2] = objectInputStream.readDouble();
                }
            }
            org.apache.commons.math.linear.Array2DRowRealMatrix array2DRowRealMatrix = new org.apache.commons.math.linear.Array2DRowRealMatrix(dArr, false);
            java.lang.reflect.Field declaredField = obj.getClass().getDeclaredField(str);
            declaredField.setAccessible(true);
            declaredField.set(obj, array2DRowRealMatrix);
        } catch (java.lang.IllegalAccessException e) {
            java.io.IOException iOException = new java.io.IOException();
            iOException.initCause(e);
            throw iOException;
        } catch (java.lang.NoSuchFieldException e2) {
            java.io.IOException iOException2 = new java.io.IOException();
            iOException2.initCause(e2);
            throw iOException2;
        }
    }
}
