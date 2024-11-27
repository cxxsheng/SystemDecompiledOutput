package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class BlockFieldMatrix<T extends org.apache.commons.math.FieldElement<T>> extends org.apache.commons.math.linear.AbstractFieldMatrix<T> implements java.io.Serializable {
    public static final int BLOCK_SIZE = 36;
    private static final long serialVersionUID = -4602336630143123183L;
    private final int blockColumns;
    private final int blockRows;
    private final T[][] blocks;
    private final int columns;
    private final int rows;

    public BlockFieldMatrix(org.apache.commons.math.Field<T> field, int i, int i2) throws java.lang.IllegalArgumentException {
        super(field, i, i2);
        this.rows = i;
        this.columns = i2;
        this.blockRows = ((i + 36) - 1) / 36;
        this.blockColumns = ((i2 + 36) - 1) / 36;
        this.blocks = (T[][]) createBlocksLayout(field, i, i2);
    }

    public BlockFieldMatrix(T[][] tArr) throws java.lang.IllegalArgumentException {
        this(tArr.length, tArr[0].length, toBlocksLayout(tArr), false);
    }

    public BlockFieldMatrix(int i, int i2, T[][] tArr, boolean z) throws java.lang.IllegalArgumentException {
        super(org.apache.commons.math.linear.AbstractFieldMatrix.extractField(tArr), i, i2);
        this.rows = i;
        this.columns = i2;
        this.blockRows = ((i + 36) - 1) / 36;
        this.blockColumns = ((i2 + 36) - 1) / 36;
        if (z) {
            this.blocks = (T[][]) org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(getField(), this.blockRows * this.blockColumns, -1);
        } else {
            this.blocks = tArr;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.blockRows; i4++) {
            int blockHeight = blockHeight(i4);
            int i5 = 0;
            while (i5 < this.blockColumns) {
                if (tArr[i3].length != blockWidth(i5) * blockHeight) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.WRONG_BLOCK_LENGTH, java.lang.Integer.valueOf(tArr[i3].length), java.lang.Integer.valueOf(blockHeight * blockWidth(i5)));
                }
                if (z) {
                    ((T[][]) this.blocks)[i3] = (org.apache.commons.math.FieldElement[]) tArr[i3].clone();
                }
                i5++;
                i3++;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T extends org.apache.commons.math.FieldElement<T>> T[][] toBlocksLayout(T[][] tArr) throws java.lang.IllegalArgumentException {
        int length = tArr.length;
        int i = 0;
        int length2 = tArr[0].length;
        int i2 = ((length + 36) - 1) / 36;
        int i3 = ((length2 + 36) - 1) / 36;
        for (T[] tArr2 : tArr) {
            int length3 = tArr2.length;
            if (length3 != length2) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, java.lang.Integer.valueOf(length2), java.lang.Integer.valueOf(length3));
            }
        }
        org.apache.commons.math.Field extractField = org.apache.commons.math.linear.AbstractFieldMatrix.extractField(tArr);
        T[][] tArr3 = (T[][]) org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(extractField, i2 * i3, -1);
        int i4 = 0;
        int i5 = 0;
        while (i4 < i2) {
            int i6 = i4 * 36;
            int min = org.apache.commons.math.util.FastMath.min(i6 + 36, length);
            int i7 = min - i6;
            int i8 = i;
            while (i8 < i3) {
                int i9 = i8 * 36;
                int min2 = org.apache.commons.math.util.FastMath.min(i9 + 36, length2) - i9;
                org.apache.commons.math.FieldElement[] buildArray = org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(extractField, i7 * min2);
                tArr3[i5] = buildArray;
                int i10 = length;
                int i11 = length2;
                int i12 = i6;
                int i13 = 0;
                while (i12 < min) {
                    java.lang.System.arraycopy(tArr[i12], i9, buildArray, i13, min2);
                    i13 += min2;
                    i12++;
                    i2 = i2;
                }
                i5++;
                i8++;
                length = i10;
                length2 = i11;
            }
            i4++;
            i = 0;
        }
        return tArr3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T extends org.apache.commons.math.FieldElement<T>> T[][] createBlocksLayout(org.apache.commons.math.Field<T> field, int i, int i2) {
        int i3 = ((i + 36) - 1) / 36;
        int i4 = ((i2 + 36) - 1) / 36;
        T[][] tArr = (T[][]) org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(field, i3 * i4, -1);
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            int i7 = i6 * 36;
            int min = org.apache.commons.math.util.FastMath.min(i7 + 36, i) - i7;
            for (int i8 = 0; i8 < i4; i8++) {
                int i9 = i8 * 36;
                tArr[i5] = org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(field, (org.apache.commons.math.util.FastMath.min(i9 + 36, i2) - i9) * min);
                i5++;
            }
        }
        return tArr;
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> createMatrix(int i, int i2) throws java.lang.IllegalArgumentException {
        return new org.apache.commons.math.linear.BlockFieldMatrix(getField(), i, i2);
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> copy() {
        org.apache.commons.math.linear.BlockFieldMatrix blockFieldMatrix = new org.apache.commons.math.linear.BlockFieldMatrix(getField(), this.rows, this.columns);
        for (int i = 0; i < this.blocks.length; i++) {
            java.lang.System.arraycopy(this.blocks[i], 0, blockFieldMatrix.blocks[i], 0, this.blocks[i].length);
        }
        return blockFieldMatrix;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> add(org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) throws java.lang.IllegalArgumentException {
        try {
            return add((org.apache.commons.math.linear.BlockFieldMatrix) fieldMatrix);
        } catch (java.lang.ClassCastException e) {
            checkAdditionCompatible(fieldMatrix);
            org.apache.commons.math.linear.BlockFieldMatrix blockFieldMatrix = new org.apache.commons.math.linear.BlockFieldMatrix(getField(), this.rows, this.columns);
            int i = 0;
            for (int i2 = 0; i2 < blockFieldMatrix.blockRows; i2++) {
                for (int i3 = 0; i3 < blockFieldMatrix.blockColumns; i3++) {
                    org.apache.commons.math.FieldElement[] fieldElementArr = ((T[][]) blockFieldMatrix.blocks)[i];
                    T[] tArr = this.blocks[i];
                    int i4 = i2 * 36;
                    int min = org.apache.commons.math.util.FastMath.min(i4 + 36, this.rows);
                    int i5 = i3 * 36;
                    int min2 = org.apache.commons.math.util.FastMath.min(i5 + 36, this.columns);
                    int i6 = 0;
                    while (i4 < min) {
                        for (int i7 = i5; i7 < min2; i7++) {
                            fieldElementArr[i6] = (org.apache.commons.math.FieldElement) tArr[i6].add(fieldMatrix.getEntry(i4, i7));
                            i6++;
                        }
                        i4++;
                    }
                    i++;
                }
            }
            return blockFieldMatrix;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public org.apache.commons.math.linear.BlockFieldMatrix<T> add(org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix) throws java.lang.IllegalArgumentException {
        checkAdditionCompatible(blockFieldMatrix);
        org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix2 = new org.apache.commons.math.linear.BlockFieldMatrix<>(getField(), this.rows, this.columns);
        for (int i = 0; i < blockFieldMatrix2.blocks.length; i++) {
            org.apache.commons.math.FieldElement[] fieldElementArr = ((T[][]) blockFieldMatrix2.blocks)[i];
            T[] tArr = this.blocks[i];
            T[] tArr2 = blockFieldMatrix.blocks[i];
            for (int i2 = 0; i2 < fieldElementArr.length; i2++) {
                fieldElementArr[i2] = (org.apache.commons.math.FieldElement) tArr[i2].add(tArr2[i2]);
            }
        }
        return blockFieldMatrix2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> subtract(org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) throws java.lang.IllegalArgumentException {
        try {
            return subtract((org.apache.commons.math.linear.BlockFieldMatrix) fieldMatrix);
        } catch (java.lang.ClassCastException e) {
            checkSubtractionCompatible(fieldMatrix);
            org.apache.commons.math.linear.BlockFieldMatrix blockFieldMatrix = new org.apache.commons.math.linear.BlockFieldMatrix(getField(), this.rows, this.columns);
            int i = 0;
            for (int i2 = 0; i2 < blockFieldMatrix.blockRows; i2++) {
                for (int i3 = 0; i3 < blockFieldMatrix.blockColumns; i3++) {
                    org.apache.commons.math.FieldElement[] fieldElementArr = ((T[][]) blockFieldMatrix.blocks)[i];
                    T[] tArr = this.blocks[i];
                    int i4 = i2 * 36;
                    int min = org.apache.commons.math.util.FastMath.min(i4 + 36, this.rows);
                    int i5 = i3 * 36;
                    int min2 = org.apache.commons.math.util.FastMath.min(i5 + 36, this.columns);
                    int i6 = 0;
                    while (i4 < min) {
                        for (int i7 = i5; i7 < min2; i7++) {
                            fieldElementArr[i6] = (org.apache.commons.math.FieldElement) tArr[i6].subtract(fieldMatrix.getEntry(i4, i7));
                            i6++;
                        }
                        i4++;
                    }
                    i++;
                }
            }
            return blockFieldMatrix;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public org.apache.commons.math.linear.BlockFieldMatrix<T> subtract(org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix) throws java.lang.IllegalArgumentException {
        checkSubtractionCompatible(blockFieldMatrix);
        org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix2 = new org.apache.commons.math.linear.BlockFieldMatrix<>(getField(), this.rows, this.columns);
        for (int i = 0; i < blockFieldMatrix2.blocks.length; i++) {
            org.apache.commons.math.FieldElement[] fieldElementArr = ((T[][]) blockFieldMatrix2.blocks)[i];
            T[] tArr = this.blocks[i];
            T[] tArr2 = blockFieldMatrix.blocks[i];
            for (int i2 = 0; i2 < fieldElementArr.length; i2++) {
                fieldElementArr[i2] = (org.apache.commons.math.FieldElement) tArr[i2].subtract(tArr2[i2]);
            }
        }
        return blockFieldMatrix2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> scalarAdd(T t) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.BlockFieldMatrix blockFieldMatrix = new org.apache.commons.math.linear.BlockFieldMatrix(getField(), this.rows, this.columns);
        for (int i = 0; i < blockFieldMatrix.blocks.length; i++) {
            org.apache.commons.math.FieldElement[] fieldElementArr = ((T[][]) blockFieldMatrix.blocks)[i];
            T[] tArr = this.blocks[i];
            for (int i2 = 0; i2 < fieldElementArr.length; i2++) {
                fieldElementArr[i2] = (org.apache.commons.math.FieldElement) tArr[i2].add(t);
            }
        }
        return blockFieldMatrix;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> scalarMultiply(T t) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.BlockFieldMatrix blockFieldMatrix = new org.apache.commons.math.linear.BlockFieldMatrix(getField(), this.rows, this.columns);
        for (int i = 0; i < blockFieldMatrix.blocks.length; i++) {
            org.apache.commons.math.FieldElement[] fieldElementArr = ((T[][]) blockFieldMatrix.blocks)[i];
            T[] tArr = this.blocks[i];
            for (int i2 = 0; i2 < fieldElementArr.length; i2++) {
                fieldElementArr[i2] = (org.apache.commons.math.FieldElement) tArr[i2].multiply(t);
            }
        }
        return blockFieldMatrix;
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> multiply(org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix = this;
        try {
            return blockFieldMatrix.multiply((org.apache.commons.math.linear.BlockFieldMatrix) fieldMatrix);
        } catch (java.lang.ClassCastException e) {
            checkMultiplicationCompatible(fieldMatrix);
            org.apache.commons.math.linear.BlockFieldMatrix blockFieldMatrix2 = new org.apache.commons.math.linear.BlockFieldMatrix(getField(), blockFieldMatrix.rows, fieldMatrix.getColumnDimension());
            T zero = getField().getZero();
            int i = 0;
            int i2 = 0;
            while (i < blockFieldMatrix2.blockRows) {
                int i3 = i * 36;
                int min = org.apache.commons.math.util.FastMath.min(i3 + 36, blockFieldMatrix.rows);
                int i4 = 0;
                while (i4 < blockFieldMatrix2.blockColumns) {
                    int i5 = i4 * 36;
                    int min2 = org.apache.commons.math.util.FastMath.min(i5 + 36, fieldMatrix.getColumnDimension());
                    org.apache.commons.math.FieldElement[] fieldElementArr = blockFieldMatrix2.blocks[i2];
                    int i6 = 0;
                    while (i6 < blockFieldMatrix.blockColumns) {
                        int blockWidth = blockFieldMatrix.blockWidth(i6);
                        T[] tArr = blockFieldMatrix.blocks[(blockFieldMatrix.blockColumns * i) + i6];
                        int i7 = i6 * 36;
                        int i8 = i3;
                        int i9 = 0;
                        while (i8 < min) {
                            int i10 = (i8 - i3) * blockWidth;
                            T t = zero;
                            int i11 = i10 + blockWidth;
                            int i12 = i3;
                            int i13 = i5;
                            while (i13 < min2) {
                                int i14 = min;
                                int i15 = i5;
                                int i16 = min2;
                                int i17 = i7;
                                int i18 = i10;
                                org.apache.commons.math.FieldElement fieldElement = t;
                                while (i18 < i11) {
                                    fieldElement = (org.apache.commons.math.FieldElement) fieldElement.add((org.apache.commons.math.FieldElement) tArr[i18].multiply(fieldMatrix.getEntry(i17, i13)));
                                    i17++;
                                    i18++;
                                    i11 = i11;
                                    tArr = tArr;
                                }
                                fieldElementArr[i9] = (org.apache.commons.math.FieldElement) fieldElementArr[i9].add(fieldElement);
                                i9++;
                                i13++;
                                min = i14;
                                i5 = i15;
                                min2 = i16;
                                i11 = i11;
                            }
                            i8++;
                            zero = t;
                            i3 = i12;
                        }
                        i6++;
                        blockFieldMatrix = this;
                    }
                    i2++;
                    i4++;
                    blockFieldMatrix = this;
                }
                i++;
                blockFieldMatrix = this;
            }
            return blockFieldMatrix2;
        }
    }

    public org.apache.commons.math.linear.BlockFieldMatrix<T> multiply(org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix) throws java.lang.IllegalArgumentException {
        int i;
        org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix2 = this;
        org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix3 = blockFieldMatrix;
        checkMultiplicationCompatible(blockFieldMatrix);
        org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix4 = new org.apache.commons.math.linear.BlockFieldMatrix<>(getField(), blockFieldMatrix2.rows, blockFieldMatrix3.columns);
        T zero = getField().getZero();
        int i2 = 0;
        int i3 = 0;
        while (i2 < blockFieldMatrix4.blockRows) {
            int i4 = i2 * 36;
            int min = org.apache.commons.math.util.FastMath.min(i4 + 36, blockFieldMatrix2.rows);
            int i5 = 0;
            while (i5 < blockFieldMatrix4.blockColumns) {
                int blockWidth = blockFieldMatrix4.blockWidth(i5);
                int i6 = blockWidth + blockWidth;
                int i7 = i6 + blockWidth;
                int i8 = i7 + blockWidth;
                org.apache.commons.math.FieldElement[] fieldElementArr = blockFieldMatrix4.blocks[i3];
                int i9 = 0;
                while (i9 < blockFieldMatrix2.blockColumns) {
                    int blockWidth2 = blockFieldMatrix2.blockWidth(i9);
                    T t = zero;
                    org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix5 = blockFieldMatrix4;
                    T[] tArr = blockFieldMatrix2.blocks[(blockFieldMatrix2.blockColumns * i2) + i9];
                    T[] tArr2 = blockFieldMatrix3.blocks[(blockFieldMatrix3.blockColumns * i9) + i5];
                    int i10 = i4;
                    int i11 = 0;
                    while (i10 < min) {
                        int i12 = (i10 - i4) * blockWidth2;
                        int i13 = i12 + blockWidth2;
                        int i14 = blockWidth2;
                        int i15 = 0;
                        while (i15 < blockWidth) {
                            int i16 = i15;
                            int i17 = i4;
                            int i18 = min;
                            org.apache.commons.math.FieldElement fieldElement = t;
                            int i19 = i12;
                            while (true) {
                                i = i2;
                                if (i19 >= i13 - 3) {
                                    break;
                                }
                                fieldElement = (org.apache.commons.math.FieldElement) ((org.apache.commons.math.FieldElement) ((org.apache.commons.math.FieldElement) ((org.apache.commons.math.FieldElement) fieldElement.add((org.apache.commons.math.FieldElement) tArr[i19].multiply(tArr2[i16]))).add((org.apache.commons.math.FieldElement) tArr[i19 + 1].multiply(tArr2[i16 + blockWidth]))).add((org.apache.commons.math.FieldElement) tArr[i19 + 2].multiply(tArr2[i16 + i6]))).add((org.apache.commons.math.FieldElement) tArr[i19 + 3].multiply(tArr2[i16 + i7]));
                                i19 += 4;
                                i16 += i8;
                                i2 = i;
                                i5 = i5;
                            }
                            int i20 = i5;
                            while (i19 < i13) {
                                fieldElement = (org.apache.commons.math.FieldElement) fieldElement.add((org.apache.commons.math.FieldElement) tArr[i19].multiply(tArr2[i16]));
                                i16 += blockWidth;
                                i19++;
                            }
                            fieldElementArr[i11] = (org.apache.commons.math.FieldElement) fieldElementArr[i11].add(fieldElement);
                            i11++;
                            i15++;
                            i4 = i17;
                            min = i18;
                            i2 = i;
                            i5 = i20;
                        }
                        i10++;
                        blockWidth2 = i14;
                    }
                    i9++;
                    blockFieldMatrix2 = this;
                    blockFieldMatrix3 = blockFieldMatrix;
                    zero = t;
                    blockFieldMatrix4 = blockFieldMatrix5;
                }
                i3++;
                i5++;
                blockFieldMatrix2 = this;
                blockFieldMatrix3 = blockFieldMatrix;
            }
            i2++;
            blockFieldMatrix2 = this;
            blockFieldMatrix3 = blockFieldMatrix;
        }
        return blockFieldMatrix4;
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T[][] getData() {
        T[][] tArr = (T[][]) org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(getField(), getRowDimension(), getColumnDimension());
        int i = this.columns - ((this.blockColumns - 1) * 36);
        for (int i2 = 0; i2 < this.blockRows; i2++) {
            int i3 = i2 * 36;
            int min = org.apache.commons.math.util.FastMath.min(i3 + 36, this.rows);
            int i4 = 0;
            int i5 = 0;
            while (i3 < min) {
                T[] tArr2 = tArr[i3];
                int i6 = this.blockColumns * i2;
                int i7 = 0;
                int i8 = 0;
                while (i7 < this.blockColumns - 1) {
                    java.lang.System.arraycopy(this.blocks[i6], i4, tArr2, i8, 36);
                    i8 += 36;
                    i7++;
                    i6++;
                }
                java.lang.System.arraycopy(this.blocks[i6], i5, tArr2, i8, i);
                i4 += 36;
                i5 += i;
                i3++;
            }
        }
        return tArr;
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> getSubMatrix(int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException {
        int i5;
        int i6;
        int i7;
        checkSubMatrixIndex(i, i2, i3, i4);
        org.apache.commons.math.linear.BlockFieldMatrix blockFieldMatrix = new org.apache.commons.math.linear.BlockFieldMatrix(getField(), (i2 - i) + 1, (i4 - i3) + 1);
        int i8 = i % 36;
        int i9 = i3 / 36;
        int i10 = i3 % 36;
        int i11 = i / 36;
        int i12 = 0;
        while (i12 < blockFieldMatrix.blockRows) {
            int blockHeight = blockFieldMatrix.blockHeight(i12);
            int i13 = i9;
            int i14 = 0;
            while (i14 < blockFieldMatrix.blockColumns) {
                int blockWidth = blockFieldMatrix.blockWidth(i14);
                T[] tArr = blockFieldMatrix.blocks[(blockFieldMatrix.blockColumns * i12) + i14];
                int i15 = (this.blockColumns * i11) + i13;
                int blockWidth2 = blockWidth(i13);
                int i16 = blockHeight + i8;
                int i17 = i16 - 36;
                int i18 = blockWidth + i10;
                int i19 = i18 - 36;
                if (i17 > 0) {
                    if (i19 > 0) {
                        int blockWidth3 = blockWidth(i13 + 1);
                        i5 = i13;
                        i6 = i14;
                        i7 = i12;
                        copyBlockPart(this.blocks[i15], blockWidth2, i8, 36, i10, 36, tArr, blockWidth, 0, 0);
                        int i20 = blockWidth - i19;
                        copyBlockPart(this.blocks[i15 + 1], blockWidth3, i8, 36, 0, i19, tArr, blockWidth, 0, i20);
                        int i21 = blockHeight - i17;
                        copyBlockPart(this.blocks[i15 + this.blockColumns], blockWidth2, 0, i17, i10, 36, tArr, blockWidth, i21, 0);
                        copyBlockPart(this.blocks[i15 + this.blockColumns + 1], blockWidth3, 0, i17, 0, i19, tArr, blockWidth, i21, i20);
                    } else {
                        i5 = i13;
                        i6 = i14;
                        i7 = i12;
                        copyBlockPart(this.blocks[i15], blockWidth2, i8, 36, i10, i18, tArr, blockWidth, 0, 0);
                        copyBlockPart(this.blocks[i15 + this.blockColumns], blockWidth2, 0, i17, i10, i18, tArr, blockWidth, blockHeight - i17, 0);
                    }
                } else {
                    i5 = i13;
                    i6 = i14;
                    i7 = i12;
                    if (i19 > 0) {
                        int blockWidth4 = blockWidth(i5 + 1);
                        copyBlockPart(this.blocks[i15], blockWidth2, i8, i16, i10, 36, tArr, blockWidth, 0, 0);
                        copyBlockPart(this.blocks[i15 + 1], blockWidth4, i8, i16, 0, i19, tArr, blockWidth, 0, blockWidth - i19);
                    } else {
                        copyBlockPart(this.blocks[i15], blockWidth2, i8, i16, i10, i18, tArr, blockWidth, 0, 0);
                    }
                }
                i13 = i5 + 1;
                i14 = i6 + 1;
                i12 = i7;
            }
            i11++;
            i12++;
        }
        return blockFieldMatrix;
    }

    private void copyBlockPart(T[] tArr, int i, int i2, int i3, int i4, int i5, T[] tArr2, int i6, int i7, int i8) {
        int i9 = i5 - i4;
        int i10 = (i2 * i) + i4;
        int i11 = (i7 * i6) + i8;
        while (i2 < i3) {
            java.lang.System.arraycopy(tArr, i10, tArr2, i11, i9);
            i10 += i;
            i11 += i6;
            i2++;
        }
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public void setSubMatrix(T[][] tArr, int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix = this;
        T[][] tArr2 = tArr;
        int i3 = i;
        int length = tArr2[0].length;
        boolean z = true;
        if (length >= 1) {
            int length2 = (tArr2.length + i3) - 1;
            int i4 = (i2 + length) - 1;
            blockFieldMatrix.checkSubMatrixIndex(i3, length2, i2, i4);
            for (T[] tArr3 : tArr2) {
                if (tArr3.length != length) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, java.lang.Integer.valueOf(length), java.lang.Integer.valueOf(tArr3.length));
                }
            }
            int i5 = i3 / 36;
            int i6 = (length2 + 36) / 36;
            int i7 = i2 / 36;
            int i8 = (i4 + 36) / 36;
            while (i5 < i6) {
                int blockHeight = blockFieldMatrix.blockHeight(i5);
                int i9 = i5 * 36;
                int max = org.apache.commons.math.util.FastMath.max(i3, i9);
                int min = org.apache.commons.math.util.FastMath.min(length2 + 1, blockHeight + i9);
                int i10 = i7;
                while (i10 < i8) {
                    int blockWidth = blockFieldMatrix.blockWidth(i10);
                    int i11 = i10 * 36;
                    int max2 = org.apache.commons.math.util.FastMath.max(i2, i11);
                    int i12 = i6;
                    int i13 = length2;
                    int min2 = org.apache.commons.math.util.FastMath.min(i4 + 1, i11 + blockWidth) - max2;
                    int i14 = i4;
                    T[] tArr4 = blockFieldMatrix.blocks[(blockFieldMatrix.blockColumns * i5) + i10];
                    int i15 = max;
                    while (i15 < min) {
                        java.lang.System.arraycopy(tArr2[i15 - i3], max2 - i2, tArr4, ((i15 - i9) * blockWidth) + (max2 - i11), min2);
                        i15++;
                        tArr2 = tArr;
                        i3 = i;
                    }
                    i10++;
                    blockFieldMatrix = this;
                    tArr2 = tArr;
                    i3 = i;
                    z = true;
                    i6 = i12;
                    length2 = i13;
                    i4 = i14;
                }
                i5++;
                blockFieldMatrix = this;
                tArr2 = tArr;
                i3 = i;
            }
            return;
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_COLUMN, new java.lang.Object[0]);
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> getRowMatrix(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        checkRowIndex(i);
        org.apache.commons.math.linear.BlockFieldMatrix blockFieldMatrix = new org.apache.commons.math.linear.BlockFieldMatrix(getField(), 1, this.columns);
        int i2 = i / 36;
        int i3 = i - (i2 * 36);
        T[] tArr = blockFieldMatrix.blocks[0];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < this.blockColumns; i6++) {
            int blockWidth = blockWidth(i6);
            T[] tArr2 = this.blocks[(this.blockColumns * i2) + i6];
            int length = tArr.length - i4;
            if (blockWidth > length) {
                int i7 = i3 * blockWidth;
                java.lang.System.arraycopy(tArr2, i7, tArr, i4, length);
                i5++;
                tArr = blockFieldMatrix.blocks[i5];
                int i8 = blockWidth - length;
                java.lang.System.arraycopy(tArr2, i7, tArr, 0, i8);
                i4 = i8;
            } else {
                java.lang.System.arraycopy(tArr2, i3 * blockWidth, tArr, i4, blockWidth);
                i4 += blockWidth;
            }
        }
        return blockFieldMatrix;
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public void setRowMatrix(int i, org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        try {
            setRowMatrix(i, (org.apache.commons.math.linear.BlockFieldMatrix) fieldMatrix);
        } catch (java.lang.ClassCastException e) {
            super.setRowMatrix(i, fieldMatrix);
        }
    }

    public void setRowMatrix(int i, org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        checkRowIndex(i);
        int columnDimension = getColumnDimension();
        if (blockFieldMatrix.getRowDimension() != 1 || blockFieldMatrix.getColumnDimension() != columnDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(blockFieldMatrix.getRowDimension()), java.lang.Integer.valueOf(blockFieldMatrix.getColumnDimension()), 1, java.lang.Integer.valueOf(columnDimension));
        }
        int i2 = i / 36;
        int i3 = i - (i2 * 36);
        T[] tArr = blockFieldMatrix.blocks[0];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < this.blockColumns; i6++) {
            int blockWidth = blockWidth(i6);
            T[] tArr2 = this.blocks[(this.blockColumns * i2) + i6];
            int length = tArr.length - i4;
            if (blockWidth > length) {
                int i7 = i3 * blockWidth;
                java.lang.System.arraycopy(tArr, i4, tArr2, i7, length);
                i5++;
                tArr = blockFieldMatrix.blocks[i5];
                int i8 = blockWidth - length;
                java.lang.System.arraycopy(tArr, 0, tArr2, i7, i8);
                i4 = i8;
            } else {
                java.lang.System.arraycopy(tArr, i4, tArr2, i3 * blockWidth, blockWidth);
                i4 += blockWidth;
            }
        }
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> getColumnMatrix(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        checkColumnIndex(i);
        org.apache.commons.math.linear.BlockFieldMatrix blockFieldMatrix = new org.apache.commons.math.linear.BlockFieldMatrix(getField(), this.rows, 1);
        int i2 = i / 36;
        int i3 = i - (i2 * 36);
        int blockWidth = blockWidth(i2);
        T[] tArr = blockFieldMatrix.blocks[0];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < this.blockRows; i6++) {
            int blockHeight = blockHeight(i6);
            T[] tArr2 = this.blocks[(this.blockColumns * i6) + i2];
            int i7 = 0;
            while (i7 < blockHeight) {
                if (i4 >= tArr.length) {
                    i5++;
                    tArr = blockFieldMatrix.blocks[i5];
                    i4 = 0;
                }
                tArr[i4] = tArr2[(i7 * blockWidth) + i3];
                i7++;
                i4++;
            }
        }
        return blockFieldMatrix;
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public void setColumnMatrix(int i, org.apache.commons.math.linear.FieldMatrix<T> fieldMatrix) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        try {
            setColumnMatrix(i, (org.apache.commons.math.linear.BlockFieldMatrix) fieldMatrix);
        } catch (java.lang.ClassCastException e) {
            super.setColumnMatrix(i, fieldMatrix);
        }
    }

    void setColumnMatrix(int i, org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        checkColumnIndex(i);
        int rowDimension = getRowDimension();
        if (blockFieldMatrix.getRowDimension() != rowDimension || blockFieldMatrix.getColumnDimension() != 1) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(blockFieldMatrix.getRowDimension()), java.lang.Integer.valueOf(blockFieldMatrix.getColumnDimension()), java.lang.Integer.valueOf(rowDimension), 1);
        }
        int i2 = i / 36;
        int i3 = i - (i2 * 36);
        int blockWidth = blockWidth(i2);
        T[] tArr = blockFieldMatrix.blocks[0];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < this.blockRows; i6++) {
            int blockHeight = blockHeight(i6);
            T[] tArr2 = this.blocks[(this.blockColumns * i6) + i2];
            int i7 = 0;
            while (i7 < blockHeight) {
                if (i4 >= tArr.length) {
                    i5++;
                    tArr = blockFieldMatrix.blocks[i5];
                    i4 = 0;
                }
                tArr2[(i7 * blockWidth) + i3] = tArr[i4];
                i7++;
                i4++;
            }
        }
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldVector<T> getRowVector(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        checkRowIndex(i);
        org.apache.commons.math.FieldElement[] buildArray = org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(getField(), this.columns);
        int i2 = i / 36;
        int i3 = i - (i2 * 36);
        int i4 = 0;
        for (int i5 = 0; i5 < this.blockColumns; i5++) {
            int blockWidth = blockWidth(i5);
            java.lang.System.arraycopy(this.blocks[(this.blockColumns * i2) + i5], i3 * blockWidth, buildArray, i4, blockWidth);
            i4 += blockWidth;
        }
        return new org.apache.commons.math.linear.ArrayFieldVector(buildArray, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public void setRowVector(int i, org.apache.commons.math.linear.FieldVector<T> fieldVector) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        try {
            setRow(i, ((org.apache.commons.math.linear.ArrayFieldVector) fieldVector).getDataRef());
        } catch (java.lang.ClassCastException e) {
            super.setRowVector(i, fieldVector);
        }
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldVector<T> getColumnVector(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        checkColumnIndex(i);
        org.apache.commons.math.FieldElement[] buildArray = org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(getField(), this.rows);
        int i2 = i / 36;
        int i3 = i - (i2 * 36);
        int blockWidth = blockWidth(i2);
        int i4 = 0;
        for (int i5 = 0; i5 < this.blockRows; i5++) {
            int blockHeight = blockHeight(i5);
            T[] tArr = this.blocks[(this.blockColumns * i5) + i2];
            int i6 = 0;
            while (i6 < blockHeight) {
                buildArray[i4] = tArr[(i6 * blockWidth) + i3];
                i6++;
                i4++;
            }
        }
        return new org.apache.commons.math.linear.ArrayFieldVector(buildArray, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public void setColumnVector(int i, org.apache.commons.math.linear.FieldVector<T> fieldVector) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        try {
            setColumn(i, ((org.apache.commons.math.linear.ArrayFieldVector) fieldVector).getDataRef());
        } catch (java.lang.ClassCastException e) {
            super.setColumnVector(i, fieldVector);
        }
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T[] getRow(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        checkRowIndex(i);
        T[] tArr = (T[]) org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(getField(), this.columns);
        int i2 = i / 36;
        int i3 = i - (i2 * 36);
        int i4 = 0;
        for (int i5 = 0; i5 < this.blockColumns; i5++) {
            int blockWidth = blockWidth(i5);
            java.lang.System.arraycopy(this.blocks[(this.blockColumns * i2) + i5], i3 * blockWidth, tArr, i4, blockWidth);
            i4 += blockWidth;
        }
        return tArr;
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public void setRow(int i, T[] tArr) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        checkRowIndex(i);
        int columnDimension = getColumnDimension();
        if (tArr.length != columnDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 1, java.lang.Integer.valueOf(tArr.length), 1, java.lang.Integer.valueOf(columnDimension));
        }
        int i2 = i / 36;
        int i3 = i - (i2 * 36);
        int i4 = 0;
        for (int i5 = 0; i5 < this.blockColumns; i5++) {
            int blockWidth = blockWidth(i5);
            java.lang.System.arraycopy(tArr, i4, this.blocks[(this.blockColumns * i2) + i5], i3 * blockWidth, blockWidth);
            i4 += blockWidth;
        }
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T[] getColumn(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        checkColumnIndex(i);
        T[] tArr = (T[]) org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(getField(), this.rows);
        int i2 = i / 36;
        int i3 = i - (i2 * 36);
        int blockWidth = blockWidth(i2);
        int i4 = 0;
        for (int i5 = 0; i5 < this.blockRows; i5++) {
            int blockHeight = blockHeight(i5);
            T[] tArr2 = this.blocks[(this.blockColumns * i5) + i2];
            int i6 = 0;
            while (i6 < blockHeight) {
                tArr[i4] = tArr2[(i6 * blockWidth) + i3];
                i6++;
                i4++;
            }
        }
        return tArr;
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public void setColumn(int i, T[] tArr) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        checkColumnIndex(i);
        int rowDimension = getRowDimension();
        if (tArr.length != rowDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(tArr.length), 1, java.lang.Integer.valueOf(rowDimension), 1);
        }
        int i2 = i / 36;
        int i3 = i - (i2 * 36);
        int blockWidth = blockWidth(i2);
        int i4 = 0;
        for (int i5 = 0; i5 < this.blockRows; i5++) {
            int blockHeight = blockHeight(i5);
            T[] tArr2 = this.blocks[(this.blockColumns * i5) + i2];
            int i6 = 0;
            while (i6 < blockHeight) {
                tArr2[(i6 * blockWidth) + i3] = tArr[i4];
                i6++;
                i4++;
            }
        }
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T getEntry(int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        try {
            int i3 = i / 36;
            int i4 = i2 / 36;
            return this.blocks[(i3 * this.blockColumns) + i4][((i - (i3 * 36)) * blockWidth(i4)) + (i2 - (i4 * 36))];
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()));
        }
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public void setEntry(int i, int i2, T t) throws org.apache.commons.math.linear.MatrixIndexException {
        try {
            int i3 = i / 36;
            int i4 = i2 / 36;
            this.blocks[(i3 * this.blockColumns) + i4][((i - (i3 * 36)) * blockWidth(i4)) + (i2 - (i4 * 36))] = t;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()));
        }
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public void addToEntry(int i, int i2, T t) throws org.apache.commons.math.linear.MatrixIndexException {
        try {
            int i3 = i / 36;
            int i4 = i2 / 36;
            int blockWidth = ((i - (i3 * 36)) * blockWidth(i4)) + (i2 - (i4 * 36));
            org.apache.commons.math.FieldElement[] fieldElementArr = this.blocks[(i3 * this.blockColumns) + i4];
            fieldElementArr[blockWidth] = (org.apache.commons.math.FieldElement) fieldElementArr[blockWidth].add(t);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()));
        }
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public void multiplyEntry(int i, int i2, T t) throws org.apache.commons.math.linear.MatrixIndexException {
        try {
            int i3 = i / 36;
            int i4 = i2 / 36;
            int blockWidth = ((i - (i3 * 36)) * blockWidth(i4)) + (i2 - (i4 * 36));
            org.apache.commons.math.FieldElement[] fieldElementArr = this.blocks[(i3 * this.blockColumns) + i4];
            fieldElementArr[blockWidth] = (org.apache.commons.math.FieldElement) fieldElementArr[blockWidth].multiply(t);
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()));
        }
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public org.apache.commons.math.linear.FieldMatrix<T> transpose() {
        int rowDimension = getRowDimension();
        org.apache.commons.math.linear.BlockFieldMatrix blockFieldMatrix = new org.apache.commons.math.linear.BlockFieldMatrix(getField(), getColumnDimension(), rowDimension);
        int i = 0;
        for (int i2 = 0; i2 < this.blockColumns; i2++) {
            for (int i3 = 0; i3 < this.blockRows; i3++) {
                T[] tArr = blockFieldMatrix.blocks[i];
                T[] tArr2 = this.blocks[(this.blockColumns * i3) + i2];
                int i4 = i2 * 36;
                int min = org.apache.commons.math.util.FastMath.min(i4 + 36, this.columns);
                int i5 = i3 * 36;
                int min2 = org.apache.commons.math.util.FastMath.min(i5 + 36, this.rows);
                int i6 = 0;
                for (int i7 = i4; i7 < min; i7++) {
                    int i8 = min - i4;
                    int i9 = i7 - i4;
                    for (int i10 = i5; i10 < min2; i10++) {
                        tArr[i6] = tArr2[i9];
                        i6++;
                        i9 += i8;
                    }
                }
                i++;
            }
        }
        return blockFieldMatrix;
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.AnyMatrix
    public int getRowDimension() {
        return this.rows;
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.AnyMatrix
    public int getColumnDimension() {
        return this.columns;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T[] operate(T[] tArr) throws java.lang.IllegalArgumentException {
        if (tArr.length == this.columns) {
            T[] tArr2 = (T[]) org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(getField(), this.rows);
            T zero = getField().getZero();
            for (int i = 0; i < this.blockRows; i++) {
                int i2 = i * 36;
                int min = org.apache.commons.math.util.FastMath.min(i2 + 36, this.rows);
                for (int i3 = 0; i3 < this.blockColumns; i3++) {
                    T[] tArr3 = this.blocks[(this.blockColumns * i) + i3];
                    int i4 = i3 * 36;
                    int min2 = org.apache.commons.math.util.FastMath.min(i4 + 36, this.columns);
                    int i5 = i2;
                    int i6 = 0;
                    while (i5 < min) {
                        org.apache.commons.math.FieldElement fieldElement = zero;
                        int i7 = i4;
                        while (i7 < min2 - 3) {
                            fieldElement = (org.apache.commons.math.FieldElement) ((org.apache.commons.math.FieldElement) ((org.apache.commons.math.FieldElement) ((org.apache.commons.math.FieldElement) fieldElement.add((org.apache.commons.math.FieldElement) tArr3[i6].multiply(tArr[i7]))).add((org.apache.commons.math.FieldElement) tArr3[i6 + 1].multiply(tArr[i7 + 1]))).add((org.apache.commons.math.FieldElement) tArr3[i6 + 2].multiply(tArr[i7 + 2]))).add((org.apache.commons.math.FieldElement) tArr3[i6 + 3].multiply(tArr[i7 + 3]));
                            i6 += 4;
                            i7 += 4;
                            zero = zero;
                        }
                        T t = zero;
                        while (i7 < min2) {
                            fieldElement = (org.apache.commons.math.FieldElement) fieldElement.add((org.apache.commons.math.FieldElement) tArr3[i6].multiply(tArr[i7]));
                            i7++;
                            i6++;
                        }
                        tArr2[i5] = (org.apache.commons.math.FieldElement) tArr2[i5].add((org.apache.commons.math.complex.Complex) fieldElement);
                        i5++;
                        zero = t;
                    }
                }
            }
            return tArr2;
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(tArr.length), java.lang.Integer.valueOf(this.columns));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T[] preMultiply(T[] tArr) throws java.lang.IllegalArgumentException {
        int i;
        if (tArr.length == this.rows) {
            T[] tArr2 = (T[]) org.apache.commons.math.linear.AbstractFieldMatrix.buildArray(getField(), this.columns);
            T zero = getField().getZero();
            for (int i2 = 0; i2 < this.blockColumns; i2++) {
                int blockWidth = blockWidth(i2);
                int i3 = blockWidth + blockWidth;
                int i4 = i3 + blockWidth;
                int i5 = i4 + blockWidth;
                int i6 = i2 * 36;
                int min = org.apache.commons.math.util.FastMath.min(i6 + 36, this.columns);
                for (int i7 = 0; i7 < this.blockRows; i7++) {
                    T[] tArr3 = this.blocks[(this.blockColumns * i7) + i2];
                    int i8 = i7 * 36;
                    int min2 = org.apache.commons.math.util.FastMath.min(i8 + 36, this.rows);
                    int i9 = i6;
                    while (i9 < min) {
                        int i10 = i9 - i6;
                        T t = zero;
                        int i11 = i6;
                        org.apache.commons.math.FieldElement fieldElement = t;
                        int i12 = i8;
                        while (true) {
                            i = min;
                            if (i12 >= min2 - 3) {
                                break;
                            }
                            fieldElement = (org.apache.commons.math.FieldElement) ((org.apache.commons.math.FieldElement) ((org.apache.commons.math.FieldElement) ((org.apache.commons.math.FieldElement) fieldElement.add((org.apache.commons.math.FieldElement) tArr3[i10].multiply(tArr[i12]))).add((org.apache.commons.math.FieldElement) tArr3[i10 + blockWidth].multiply(tArr[i12 + 1]))).add((org.apache.commons.math.FieldElement) tArr3[i10 + i3].multiply(tArr[i12 + 2]))).add((org.apache.commons.math.FieldElement) tArr3[i10 + i4].multiply(tArr[i12 + 3]));
                            i10 += i5;
                            i12 += 4;
                            min = i;
                            i8 = i8;
                        }
                        int i13 = i8;
                        while (i12 < min2) {
                            fieldElement = (org.apache.commons.math.FieldElement) fieldElement.add((org.apache.commons.math.FieldElement) tArr3[i10].multiply(tArr[i12]));
                            i10 += blockWidth;
                            i12++;
                        }
                        tArr2[i9] = (org.apache.commons.math.FieldElement) tArr2[i9].add((org.apache.commons.math.complex.Complex) fieldElement);
                        i9++;
                        zero = t;
                        i6 = i11;
                        min = i;
                        i8 = i13;
                    }
                }
            }
            return tArr2;
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(tArr.length), java.lang.Integer.valueOf(this.rows));
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T walkInRowOrder(org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> fieldMatrixChangingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        fieldMatrixChangingVisitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        for (int i = 0; i < this.blockRows; i++) {
            int i2 = i * 36;
            int min = org.apache.commons.math.util.FastMath.min(i2 + 36, this.rows);
            for (int i3 = i2; i3 < min; i3++) {
                for (int i4 = 0; i4 < this.blockColumns; i4++) {
                    int blockWidth = blockWidth(i4);
                    int i5 = i4 * 36;
                    int min2 = org.apache.commons.math.util.FastMath.min(i5 + 36, this.columns);
                    T[] tArr = this.blocks[(this.blockColumns * i) + i4];
                    int i6 = (i3 - i2) * blockWidth;
                    while (i5 < min2) {
                        tArr[i6] = fieldMatrixChangingVisitor.visit(i3, i5, tArr[i6]);
                        i6++;
                        i5++;
                    }
                }
            }
        }
        return fieldMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T walkInRowOrder(org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> fieldMatrixPreservingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        fieldMatrixPreservingVisitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        for (int i = 0; i < this.blockRows; i++) {
            int i2 = i * 36;
            int min = org.apache.commons.math.util.FastMath.min(i2 + 36, this.rows);
            for (int i3 = i2; i3 < min; i3++) {
                for (int i4 = 0; i4 < this.blockColumns; i4++) {
                    int blockWidth = blockWidth(i4);
                    int i5 = i4 * 36;
                    int min2 = org.apache.commons.math.util.FastMath.min(i5 + 36, this.columns);
                    T[] tArr = this.blocks[(this.blockColumns * i) + i4];
                    int i6 = (i3 - i2) * blockWidth;
                    while (i5 < min2) {
                        fieldMatrixPreservingVisitor.visit(i3, i5, tArr[i6]);
                        i6++;
                        i5++;
                    }
                }
            }
        }
        return fieldMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T walkInRowOrder(org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> fieldMatrixChangingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        int i5;
        checkSubMatrixIndex(i, i2, i3, i4);
        fieldMatrixChangingVisitor.start(this.rows, this.columns, i, i2, i3, i4);
        for (int i6 = i / 36; i6 < (i2 / 36) + 1; i6 = i5) {
            int i7 = i6 * 36;
            i5 = i6 + 1;
            int min = org.apache.commons.math.util.FastMath.min(i5 * 36, i2 + 1);
            for (int max = org.apache.commons.math.util.FastMath.max(i, i7); max < min; max++) {
                int i8 = i3 / 36;
                while (i8 < (i4 / 36) + 1) {
                    int blockWidth = blockWidth(i8);
                    int i9 = i8 * 36;
                    int max2 = org.apache.commons.math.util.FastMath.max(i3, i9);
                    int i10 = i8 + 1;
                    int i11 = i5;
                    int min2 = org.apache.commons.math.util.FastMath.min(i10 * 36, i4 + 1);
                    int i12 = min;
                    T[] tArr = this.blocks[(this.blockColumns * i6) + i8];
                    int i13 = (((max - i7) * blockWidth) + max2) - i9;
                    while (max2 < min2) {
                        tArr[i13] = fieldMatrixChangingVisitor.visit(max, max2, tArr[i13]);
                        i13++;
                        max2++;
                    }
                    i8 = i10;
                    i5 = i11;
                    min = i12;
                }
            }
        }
        return fieldMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T walkInRowOrder(org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> fieldMatrixPreservingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        int i5;
        checkSubMatrixIndex(i, i2, i3, i4);
        fieldMatrixPreservingVisitor.start(this.rows, this.columns, i, i2, i3, i4);
        for (int i6 = i / 36; i6 < (i2 / 36) + 1; i6 = i5) {
            int i7 = i6 * 36;
            i5 = i6 + 1;
            int min = org.apache.commons.math.util.FastMath.min(i5 * 36, i2 + 1);
            for (int max = org.apache.commons.math.util.FastMath.max(i, i7); max < min; max++) {
                int i8 = i3 / 36;
                while (i8 < (i4 / 36) + 1) {
                    int blockWidth = blockWidth(i8);
                    int i9 = i8 * 36;
                    int max2 = org.apache.commons.math.util.FastMath.max(i3, i9);
                    int i10 = i8 + 1;
                    int i11 = i5;
                    int min2 = org.apache.commons.math.util.FastMath.min(i10 * 36, i4 + 1);
                    int i12 = min;
                    T[] tArr = this.blocks[(this.blockColumns * i6) + i8];
                    int i13 = (((max - i7) * blockWidth) + max2) - i9;
                    while (max2 < min2) {
                        fieldMatrixPreservingVisitor.visit(max, max2, tArr[i13]);
                        i13++;
                        max2++;
                    }
                    i8 = i10;
                    i5 = i11;
                    min = i12;
                }
            }
        }
        return fieldMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T walkInOptimizedOrder(org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> fieldMatrixChangingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        fieldMatrixChangingVisitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        int i = 0;
        for (int i2 = 0; i2 < this.blockRows; i2++) {
            int i3 = i2 * 36;
            int min = org.apache.commons.math.util.FastMath.min(i3 + 36, this.rows);
            for (int i4 = 0; i4 < this.blockColumns; i4++) {
                int i5 = i4 * 36;
                int min2 = org.apache.commons.math.util.FastMath.min(i5 + 36, this.columns);
                T[] tArr = this.blocks[i];
                int i6 = 0;
                for (int i7 = i3; i7 < min; i7++) {
                    for (int i8 = i5; i8 < min2; i8++) {
                        tArr[i6] = fieldMatrixChangingVisitor.visit(i7, i8, tArr[i6]);
                        i6++;
                    }
                }
                i++;
            }
        }
        return fieldMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T walkInOptimizedOrder(org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> fieldMatrixPreservingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        fieldMatrixPreservingVisitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        int i = 0;
        for (int i2 = 0; i2 < this.blockRows; i2++) {
            int i3 = i2 * 36;
            int min = org.apache.commons.math.util.FastMath.min(i3 + 36, this.rows);
            for (int i4 = 0; i4 < this.blockColumns; i4++) {
                int i5 = i4 * 36;
                int min2 = org.apache.commons.math.util.FastMath.min(i5 + 36, this.columns);
                T[] tArr = this.blocks[i];
                int i6 = 0;
                for (int i7 = i3; i7 < min; i7++) {
                    for (int i8 = i5; i8 < min2; i8++) {
                        fieldMatrixPreservingVisitor.visit(i7, i8, tArr[i6]);
                        i6++;
                    }
                }
                i++;
            }
        }
        return fieldMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T walkInOptimizedOrder(org.apache.commons.math.linear.FieldMatrixChangingVisitor<T> fieldMatrixChangingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix = this;
        blockFieldMatrix.checkSubMatrixIndex(i, i2, i3, i4);
        fieldMatrixChangingVisitor.start(blockFieldMatrix.rows, blockFieldMatrix.columns, i, i2, i3, i4);
        int i5 = i / 36;
        while (i5 < (i2 / 36) + 1) {
            int i6 = i5 * 36;
            int max = org.apache.commons.math.util.FastMath.max(i, i6);
            int i7 = i5 + 1;
            int min = org.apache.commons.math.util.FastMath.min(i7 * 36, i2 + 1);
            int i8 = i3 / 36;
            while (i8 < (i4 / 36) + 1) {
                int blockWidth = blockFieldMatrix.blockWidth(i8);
                int i9 = i8 * 36;
                int max2 = org.apache.commons.math.util.FastMath.max(i3, i9);
                int i10 = i8 + 1;
                int i11 = max;
                int min2 = org.apache.commons.math.util.FastMath.min(i10 * 36, i4 + 1);
                int i12 = i7;
                T[] tArr = blockFieldMatrix.blocks[(blockFieldMatrix.blockColumns * i5) + i8];
                int i13 = i11;
                while (i13 < min) {
                    int i14 = (((i13 - i6) * blockWidth) + max2) - i9;
                    int i15 = max2;
                    while (i15 < min2) {
                        tArr[i14] = fieldMatrixChangingVisitor.visit(i13, i15, tArr[i14]);
                        i14++;
                        i15++;
                        i5 = i5;
                        i6 = i6;
                    }
                    i13++;
                    i6 = i6;
                }
                blockFieldMatrix = this;
                i8 = i10;
                max = i11;
                i7 = i12;
                i6 = i6;
            }
            blockFieldMatrix = this;
            i5 = i7;
        }
        return fieldMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractFieldMatrix, org.apache.commons.math.linear.FieldMatrix
    public T walkInOptimizedOrder(org.apache.commons.math.linear.FieldMatrixPreservingVisitor<T> fieldMatrixPreservingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.BlockFieldMatrix<T> blockFieldMatrix = this;
        blockFieldMatrix.checkSubMatrixIndex(i, i2, i3, i4);
        fieldMatrixPreservingVisitor.start(blockFieldMatrix.rows, blockFieldMatrix.columns, i, i2, i3, i4);
        int i5 = i / 36;
        while (i5 < (i2 / 36) + 1) {
            int i6 = i5 * 36;
            int max = org.apache.commons.math.util.FastMath.max(i, i6);
            int i7 = i5 + 1;
            int min = org.apache.commons.math.util.FastMath.min(i7 * 36, i2 + 1);
            int i8 = i3 / 36;
            while (i8 < (i4 / 36) + 1) {
                int blockWidth = blockFieldMatrix.blockWidth(i8);
                int i9 = i8 * 36;
                int max2 = org.apache.commons.math.util.FastMath.max(i3, i9);
                int i10 = i8 + 1;
                int i11 = max;
                int min2 = org.apache.commons.math.util.FastMath.min(i10 * 36, i4 + 1);
                int i12 = i7;
                T[] tArr = blockFieldMatrix.blocks[(blockFieldMatrix.blockColumns * i5) + i8];
                int i13 = i11;
                while (i13 < min) {
                    int i14 = (((i13 - i6) * blockWidth) + max2) - i9;
                    int i15 = max2;
                    while (i15 < min2) {
                        fieldMatrixPreservingVisitor.visit(i13, i15, tArr[i14]);
                        i14++;
                        i15++;
                        i5 = i5;
                        i6 = i6;
                    }
                    i13++;
                    i6 = i6;
                }
                blockFieldMatrix = this;
                i8 = i10;
                max = i11;
                i7 = i12;
                i6 = i6;
            }
            blockFieldMatrix = this;
            i5 = i7;
        }
        return fieldMatrixPreservingVisitor.end();
    }

    private int blockHeight(int i) {
        if (i == this.blockRows - 1) {
            return this.rows - (i * 36);
        }
        return 36;
    }

    private int blockWidth(int i) {
        if (i == this.blockColumns - 1) {
            return this.columns - (i * 36);
        }
        return 36;
    }
}
