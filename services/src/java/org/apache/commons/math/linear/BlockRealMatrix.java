package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class BlockRealMatrix extends org.apache.commons.math.linear.AbstractRealMatrix implements java.io.Serializable {
    public static final int BLOCK_SIZE = 52;
    private static final long serialVersionUID = 4991895511313664478L;
    private final int blockColumns;
    private final int blockRows;
    private final double[][] blocks;
    private final int columns;
    private final int rows;

    public BlockRealMatrix(int i, int i2) throws java.lang.IllegalArgumentException {
        super(i, i2);
        this.rows = i;
        this.columns = i2;
        this.blockRows = ((i + 52) - 1) / 52;
        this.blockColumns = ((i2 + 52) - 1) / 52;
        this.blocks = createBlocksLayout(i, i2);
    }

    public BlockRealMatrix(double[][] dArr) throws java.lang.IllegalArgumentException {
        this(dArr.length, dArr[0].length, toBlocksLayout(dArr), false);
    }

    public BlockRealMatrix(int i, int i2, double[][] dArr, boolean z) throws java.lang.IllegalArgumentException {
        super(i, i2);
        this.rows = i;
        this.columns = i2;
        this.blockRows = ((i + 52) - 1) / 52;
        this.blockColumns = ((i2 + 52) - 1) / 52;
        if (z) {
            this.blocks = new double[this.blockRows * this.blockColumns][];
        } else {
            this.blocks = dArr;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.blockRows; i4++) {
            int blockHeight = blockHeight(i4);
            int i5 = 0;
            while (i5 < this.blockColumns) {
                if (dArr[i3].length != blockWidth(i5) * blockHeight) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.WRONG_BLOCK_LENGTH, java.lang.Integer.valueOf(dArr[i3].length), java.lang.Integer.valueOf(blockHeight * blockWidth(i5)));
                }
                if (z) {
                    this.blocks[i3] = (double[]) dArr[i3].clone();
                }
                i5++;
                i3++;
            }
        }
    }

    public static double[][] toBlocksLayout(double[][] dArr) throws java.lang.IllegalArgumentException {
        int length = dArr.length;
        int i = 0;
        int length2 = dArr[0].length;
        int i2 = ((length + 52) - 1) / 52;
        int i3 = ((length2 + 52) - 1) / 52;
        for (double[] dArr2 : dArr) {
            int length3 = dArr2.length;
            if (length3 != length2) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, java.lang.Integer.valueOf(length2), java.lang.Integer.valueOf(length3));
            }
        }
        double[][] dArr3 = new double[i2 * i3][];
        int i4 = 0;
        int i5 = 0;
        while (i4 < i2) {
            int i6 = i4 * 52;
            int min = org.apache.commons.math.util.FastMath.min(i6 + 52, length);
            int i7 = min - i6;
            int i8 = i;
            while (i8 < i3) {
                int i9 = i8 * 52;
                int min2 = org.apache.commons.math.util.FastMath.min(i9 + 52, length2) - i9;
                double[] dArr4 = new double[i7 * min2];
                dArr3[i5] = dArr4;
                int i10 = length;
                int i11 = i;
                int i12 = i6;
                while (i12 < min) {
                    java.lang.System.arraycopy(dArr[i12], i9, dArr4, i11, min2);
                    i11 += min2;
                    i12++;
                    length2 = length2;
                }
                i5++;
                i8++;
                length = i10;
                i = 0;
            }
            i4++;
            i = 0;
        }
        return dArr3;
    }

    public static double[][] createBlocksLayout(int i, int i2) {
        int i3 = ((i + 52) - 1) / 52;
        int i4 = ((i2 + 52) - 1) / 52;
        double[][] dArr = new double[i3 * i4][];
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            int i7 = i6 * 52;
            int min = org.apache.commons.math.util.FastMath.min(i7 + 52, i) - i7;
            for (int i8 = 0; i8 < i4; i8++) {
                int i9 = i8 * 52;
                dArr[i5] = new double[(org.apache.commons.math.util.FastMath.min(i9 + 52, i2) - i9) * min];
                i5++;
            }
        }
        return dArr;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.BlockRealMatrix createMatrix(int i, int i2) throws java.lang.IllegalArgumentException {
        return new org.apache.commons.math.linear.BlockRealMatrix(i, i2);
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.BlockRealMatrix copy() {
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = new org.apache.commons.math.linear.BlockRealMatrix(this.rows, this.columns);
        for (int i = 0; i < this.blocks.length; i++) {
            java.lang.System.arraycopy(this.blocks[i], 0, blockRealMatrix.blocks[i], 0, this.blocks[i].length);
        }
        return blockRealMatrix;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.BlockRealMatrix add(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
        try {
            return add((org.apache.commons.math.linear.BlockRealMatrix) realMatrix);
        } catch (java.lang.ClassCastException e) {
            org.apache.commons.math.linear.MatrixUtils.checkAdditionCompatible(this, realMatrix);
            org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = new org.apache.commons.math.linear.BlockRealMatrix(this.rows, this.columns);
            int i = 0;
            for (int i2 = 0; i2 < blockRealMatrix.blockRows; i2++) {
                for (int i3 = 0; i3 < blockRealMatrix.blockColumns; i3++) {
                    double[] dArr = blockRealMatrix.blocks[i];
                    double[] dArr2 = this.blocks[i];
                    int i4 = i2 * 52;
                    int min = org.apache.commons.math.util.FastMath.min(i4 + 52, this.rows);
                    int i5 = i3 * 52;
                    int min2 = org.apache.commons.math.util.FastMath.min(i5 + 52, this.columns);
                    int i6 = 0;
                    while (i4 < min) {
                        for (int i7 = i5; i7 < min2; i7++) {
                            dArr[i6] = dArr2[i6] + realMatrix.getEntry(i4, i7);
                            i6++;
                        }
                        i4++;
                    }
                    i++;
                }
            }
            return blockRealMatrix;
        }
    }

    public org.apache.commons.math.linear.BlockRealMatrix add(org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkAdditionCompatible(this, blockRealMatrix);
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix2 = new org.apache.commons.math.linear.BlockRealMatrix(this.rows, this.columns);
        for (int i = 0; i < blockRealMatrix2.blocks.length; i++) {
            double[] dArr = blockRealMatrix2.blocks[i];
            double[] dArr2 = this.blocks[i];
            double[] dArr3 = blockRealMatrix.blocks[i];
            for (int i2 = 0; i2 < dArr.length; i2++) {
                dArr[i2] = dArr2[i2] + dArr3[i2];
            }
        }
        return blockRealMatrix2;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.BlockRealMatrix subtract(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
        try {
            return subtract((org.apache.commons.math.linear.BlockRealMatrix) realMatrix);
        } catch (java.lang.ClassCastException e) {
            org.apache.commons.math.linear.MatrixUtils.checkSubtractionCompatible(this, realMatrix);
            org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = new org.apache.commons.math.linear.BlockRealMatrix(this.rows, this.columns);
            int i = 0;
            for (int i2 = 0; i2 < blockRealMatrix.blockRows; i2++) {
                for (int i3 = 0; i3 < blockRealMatrix.blockColumns; i3++) {
                    double[] dArr = blockRealMatrix.blocks[i];
                    double[] dArr2 = this.blocks[i];
                    int i4 = i2 * 52;
                    int min = org.apache.commons.math.util.FastMath.min(i4 + 52, this.rows);
                    int i5 = i3 * 52;
                    int min2 = org.apache.commons.math.util.FastMath.min(i5 + 52, this.columns);
                    int i6 = 0;
                    while (i4 < min) {
                        for (int i7 = i5; i7 < min2; i7++) {
                            dArr[i6] = dArr2[i6] - realMatrix.getEntry(i4, i7);
                            i6++;
                        }
                        i4++;
                    }
                    i++;
                }
            }
            return blockRealMatrix;
        }
    }

    public org.apache.commons.math.linear.BlockRealMatrix subtract(org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkSubtractionCompatible(this, blockRealMatrix);
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix2 = new org.apache.commons.math.linear.BlockRealMatrix(this.rows, this.columns);
        for (int i = 0; i < blockRealMatrix2.blocks.length; i++) {
            double[] dArr = blockRealMatrix2.blocks[i];
            double[] dArr2 = this.blocks[i];
            double[] dArr3 = blockRealMatrix.blocks[i];
            for (int i2 = 0; i2 < dArr.length; i2++) {
                dArr[i2] = dArr2[i2] - dArr3[i2];
            }
        }
        return blockRealMatrix2;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.BlockRealMatrix scalarAdd(double d) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = new org.apache.commons.math.linear.BlockRealMatrix(this.rows, this.columns);
        for (int i = 0; i < blockRealMatrix.blocks.length; i++) {
            double[] dArr = blockRealMatrix.blocks[i];
            double[] dArr2 = this.blocks[i];
            for (int i2 = 0; i2 < dArr.length; i2++) {
                dArr[i2] = dArr2[i2] + d;
            }
        }
        return blockRealMatrix;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix scalarMultiply(double d) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = new org.apache.commons.math.linear.BlockRealMatrix(this.rows, this.columns);
        for (int i = 0; i < blockRealMatrix.blocks.length; i++) {
            double[] dArr = blockRealMatrix.blocks[i];
            double[] dArr2 = this.blocks[i];
            for (int i2 = 0; i2 < dArr.length; i2++) {
                dArr[i2] = dArr2[i2] * d;
            }
        }
        return blockRealMatrix;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.BlockRealMatrix multiply(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = this;
        try {
            return blockRealMatrix.multiply((org.apache.commons.math.linear.BlockRealMatrix) realMatrix);
        } catch (java.lang.ClassCastException e) {
            org.apache.commons.math.linear.MatrixUtils.checkMultiplicationCompatible(this, realMatrix);
            org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix2 = new org.apache.commons.math.linear.BlockRealMatrix(blockRealMatrix.rows, realMatrix.getColumnDimension());
            int i = 0;
            int i2 = 0;
            while (i < blockRealMatrix2.blockRows) {
                int i3 = i * 52;
                int min = org.apache.commons.math.util.FastMath.min(i3 + 52, blockRealMatrix.rows);
                int i4 = 0;
                while (i4 < blockRealMatrix2.blockColumns) {
                    int i5 = i4 * 52;
                    int min2 = org.apache.commons.math.util.FastMath.min(i5 + 52, realMatrix.getColumnDimension());
                    double[] dArr = blockRealMatrix2.blocks[i2];
                    int i6 = 0;
                    while (i6 < blockRealMatrix.blockColumns) {
                        int blockWidth = blockRealMatrix.blockWidth(i6);
                        double[] dArr2 = blockRealMatrix.blocks[(blockRealMatrix.blockColumns * i) + i6];
                        int i7 = i6 * 52;
                        int i8 = i3;
                        int i9 = 0;
                        while (i8 < min) {
                            int i10 = (i8 - i3) * blockWidth;
                            int i11 = i10 + blockWidth;
                            int i12 = i3;
                            int i13 = i5;
                            while (i13 < min2) {
                                double d = 0.0d;
                                int i14 = min;
                                int i15 = i5;
                                int i16 = i7;
                                for (int i17 = i10; i17 < i11; i17++) {
                                    d += dArr2[i17] * realMatrix.getEntry(i16, i13);
                                    i16++;
                                }
                                dArr[i9] = dArr[i9] + d;
                                i9++;
                                i13++;
                                min = i14;
                                i5 = i15;
                            }
                            i8++;
                            i3 = i12;
                        }
                        i6++;
                        blockRealMatrix = this;
                    }
                    i2++;
                    i4++;
                    blockRealMatrix = this;
                }
                i++;
                blockRealMatrix = this;
            }
            return blockRealMatrix2;
        }
    }

    public org.apache.commons.math.linear.BlockRealMatrix multiply(org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix) throws java.lang.IllegalArgumentException {
        int i;
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix2 = this;
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix3 = blockRealMatrix;
        org.apache.commons.math.linear.MatrixUtils.checkMultiplicationCompatible(this, blockRealMatrix);
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix4 = new org.apache.commons.math.linear.BlockRealMatrix(blockRealMatrix2.rows, blockRealMatrix3.columns);
        int i2 = 0;
        int i3 = 0;
        while (i2 < blockRealMatrix4.blockRows) {
            int i4 = i2 * 52;
            int min = org.apache.commons.math.util.FastMath.min(i4 + 52, blockRealMatrix2.rows);
            int i5 = 0;
            while (i5 < blockRealMatrix4.blockColumns) {
                int blockWidth = blockRealMatrix4.blockWidth(i5);
                int i6 = blockWidth + blockWidth;
                int i7 = i6 + blockWidth;
                int i8 = i7 + blockWidth;
                double[] dArr = blockRealMatrix4.blocks[i3];
                int i9 = 0;
                while (i9 < blockRealMatrix2.blockColumns) {
                    int blockWidth2 = blockRealMatrix2.blockWidth(i9);
                    org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix5 = blockRealMatrix4;
                    double[] dArr2 = blockRealMatrix2.blocks[(blockRealMatrix2.blockColumns * i2) + i9];
                    double[] dArr3 = blockRealMatrix3.blocks[(blockRealMatrix3.blockColumns * i9) + i5];
                    int i10 = i4;
                    int i11 = 0;
                    while (i10 < min) {
                        int i12 = (i10 - i4) * blockWidth2;
                        int i13 = i12 + blockWidth2;
                        int i14 = i4;
                        int i15 = 0;
                        while (i15 < blockWidth) {
                            double d = 0.0d;
                            int i16 = i15;
                            int i17 = min;
                            int i18 = i12;
                            while (true) {
                                i = blockWidth2;
                                if (i18 >= i13 - 3) {
                                    break;
                                }
                                d += (dArr2[i18] * dArr3[i16]) + (dArr2[i18 + 1] * dArr3[i16 + blockWidth]) + (dArr2[i18 + 2] * dArr3[i16 + i6]) + (dArr2[i18 + 3] * dArr3[i16 + i7]);
                                i18 += 4;
                                i16 += i8;
                                blockWidth2 = i;
                            }
                            while (i18 < i13) {
                                d += dArr2[i18] * dArr3[i16];
                                i16 += blockWidth;
                                i18++;
                            }
                            dArr[i11] = dArr[i11] + d;
                            i11++;
                            i15++;
                            min = i17;
                            blockWidth2 = i;
                        }
                        i10++;
                        i4 = i14;
                    }
                    i9++;
                    blockRealMatrix2 = this;
                    blockRealMatrix3 = blockRealMatrix;
                    blockRealMatrix4 = blockRealMatrix5;
                }
                i3++;
                i5++;
                blockRealMatrix2 = this;
                blockRealMatrix3 = blockRealMatrix;
            }
            i2++;
            blockRealMatrix2 = this;
            blockRealMatrix3 = blockRealMatrix;
        }
        return blockRealMatrix4;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double[][] getData() {
        double[][] dArr = (double[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Double.TYPE, getRowDimension(), getColumnDimension());
        int i = this.columns - ((this.blockColumns - 1) * 52);
        for (int i2 = 0; i2 < this.blockRows; i2++) {
            int i3 = i2 * 52;
            int min = org.apache.commons.math.util.FastMath.min(i3 + 52, this.rows);
            int i4 = 0;
            int i5 = 0;
            while (i3 < min) {
                double[] dArr2 = dArr[i3];
                int i6 = this.blockColumns * i2;
                int i7 = 0;
                int i8 = 0;
                while (i7 < this.blockColumns - 1) {
                    java.lang.System.arraycopy(this.blocks[i6], i4, dArr2, i8, 52);
                    i8 += 52;
                    i7++;
                    i6++;
                }
                java.lang.System.arraycopy(this.blocks[i6], i5, dArr2, i8, i);
                i4 += 52;
                i5 += i;
                i3++;
            }
        }
        return dArr;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double getNorm() {
        double[] dArr = new double[52];
        double d = 0.0d;
        for (int i = 0; i < this.blockColumns; i++) {
            int blockWidth = blockWidth(i);
            java.util.Arrays.fill(dArr, 0, blockWidth, 0.0d);
            for (int i2 = 0; i2 < this.blockRows; i2++) {
                int blockHeight = blockHeight(i2);
                double[] dArr2 = this.blocks[(this.blockColumns * i2) + i];
                for (int i3 = 0; i3 < blockWidth; i3++) {
                    double d2 = 0.0d;
                    for (int i4 = 0; i4 < blockHeight; i4++) {
                        d2 += org.apache.commons.math.util.FastMath.abs(dArr2[(i4 * blockWidth) + i3]);
                    }
                    dArr[i3] = dArr[i3] + d2;
                }
            }
            for (int i5 = 0; i5 < blockWidth; i5++) {
                d = org.apache.commons.math.util.FastMath.max(d, dArr[i5]);
            }
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double getFrobeniusNorm() {
        double d = 0.0d;
        for (int i = 0; i < this.blocks.length; i++) {
            for (double d2 : this.blocks[i]) {
                d += d2 * d2;
            }
        }
        return org.apache.commons.math.util.FastMath.sqrt(d);
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.BlockRealMatrix getSubMatrix(int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException {
        int i5;
        int i6;
        int i7;
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(this, i, i2, i3, i4);
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = new org.apache.commons.math.linear.BlockRealMatrix((i2 - i) + 1, (i4 - i3) + 1);
        int i8 = i % 52;
        int i9 = i3 / 52;
        int i10 = i3 % 52;
        int i11 = i / 52;
        int i12 = 0;
        while (i12 < blockRealMatrix.blockRows) {
            int blockHeight = blockRealMatrix.blockHeight(i12);
            int i13 = i9;
            int i14 = 0;
            while (i14 < blockRealMatrix.blockColumns) {
                int blockWidth = blockRealMatrix.blockWidth(i14);
                double[] dArr = blockRealMatrix.blocks[(blockRealMatrix.blockColumns * i12) + i14];
                int i15 = (this.blockColumns * i11) + i13;
                int blockWidth2 = blockWidth(i13);
                int i16 = blockHeight + i8;
                int i17 = i16 - 52;
                int i18 = blockWidth + i10;
                int i19 = i18 - 52;
                if (i17 > 0) {
                    if (i19 > 0) {
                        int blockWidth3 = blockWidth(i13 + 1);
                        i5 = i13;
                        i6 = i14;
                        i7 = i12;
                        copyBlockPart(this.blocks[i15], blockWidth2, i8, 52, i10, 52, dArr, blockWidth, 0, 0);
                        int i20 = blockWidth - i19;
                        copyBlockPart(this.blocks[i15 + 1], blockWidth3, i8, 52, 0, i19, dArr, blockWidth, 0, i20);
                        int i21 = blockHeight - i17;
                        copyBlockPart(this.blocks[i15 + this.blockColumns], blockWidth2, 0, i17, i10, 52, dArr, blockWidth, i21, 0);
                        copyBlockPart(this.blocks[i15 + this.blockColumns + 1], blockWidth3, 0, i17, 0, i19, dArr, blockWidth, i21, i20);
                    } else {
                        i5 = i13;
                        i6 = i14;
                        i7 = i12;
                        copyBlockPart(this.blocks[i15], blockWidth2, i8, 52, i10, i18, dArr, blockWidth, 0, 0);
                        copyBlockPart(this.blocks[i15 + this.blockColumns], blockWidth2, 0, i17, i10, i18, dArr, blockWidth, blockHeight - i17, 0);
                    }
                } else {
                    i5 = i13;
                    i6 = i14;
                    i7 = i12;
                    if (i19 > 0) {
                        int blockWidth4 = blockWidth(i5 + 1);
                        copyBlockPart(this.blocks[i15], blockWidth2, i8, i16, i10, 52, dArr, blockWidth, 0, 0);
                        copyBlockPart(this.blocks[i15 + 1], blockWidth4, i8, i16, 0, i19, dArr, blockWidth, 0, blockWidth - i19);
                    } else {
                        copyBlockPart(this.blocks[i15], blockWidth2, i8, i16, i10, i18, dArr, blockWidth, 0, 0);
                    }
                }
                i13 = i5 + 1;
                i14 = i6 + 1;
                i12 = i7;
            }
            i11++;
            i12++;
        }
        return blockRealMatrix;
    }

    private void copyBlockPart(double[] dArr, int i, int i2, int i3, int i4, int i5, double[] dArr2, int i6, int i7, int i8) {
        int i9 = i5 - i4;
        int i10 = (i2 * i) + i4;
        int i11 = (i7 * i6) + i8;
        while (i2 < i3) {
            java.lang.System.arraycopy(dArr, i10, dArr2, i11, i9);
            i10 += i;
            i11 += i6;
            i2++;
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void setSubMatrix(double[][] dArr, int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = this;
        double[][] dArr2 = dArr;
        int i3 = i;
        int length = dArr2[0].length;
        boolean z = true;
        if (length >= 1) {
            int length2 = (dArr2.length + i3) - 1;
            int i4 = (i2 + length) - 1;
            org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(blockRealMatrix, i3, length2, i2, i4);
            for (double[] dArr3 : dArr2) {
                if (dArr3.length != length) {
                    throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.DIFFERENT_ROWS_LENGTHS, java.lang.Integer.valueOf(length), java.lang.Integer.valueOf(dArr3.length));
                }
            }
            int i5 = i3 / 52;
            int i6 = (length2 + 52) / 52;
            int i7 = i2 / 52;
            int i8 = (i4 + 52) / 52;
            while (i5 < i6) {
                int blockHeight = blockRealMatrix.blockHeight(i5);
                int i9 = i5 * 52;
                int max = org.apache.commons.math.util.FastMath.max(i3, i9);
                int min = org.apache.commons.math.util.FastMath.min(length2 + 1, blockHeight + i9);
                int i10 = i7;
                while (i10 < i8) {
                    int blockWidth = blockRealMatrix.blockWidth(i10);
                    int i11 = i10 * 52;
                    int max2 = org.apache.commons.math.util.FastMath.max(i2, i11);
                    int i12 = i6;
                    int i13 = length2;
                    int min2 = org.apache.commons.math.util.FastMath.min(i4 + 1, i11 + blockWidth) - max2;
                    int i14 = i4;
                    double[] dArr4 = blockRealMatrix.blocks[(blockRealMatrix.blockColumns * i5) + i10];
                    int i15 = max;
                    while (i15 < min) {
                        java.lang.System.arraycopy(dArr2[i15 - i3], max2 - i2, dArr4, ((i15 - i9) * blockWidth) + (max2 - i11), min2);
                        i15++;
                        dArr2 = dArr;
                        i3 = i;
                    }
                    i10++;
                    blockRealMatrix = this;
                    dArr2 = dArr;
                    i3 = i;
                    z = true;
                    i6 = i12;
                    length2 = i13;
                    i4 = i14;
                }
                i5++;
                blockRealMatrix = this;
                dArr2 = dArr;
                i3 = i;
            }
            return;
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.AT_LEAST_ONE_COLUMN, new java.lang.Object[0]);
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.BlockRealMatrix getRowMatrix(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = new org.apache.commons.math.linear.BlockRealMatrix(1, this.columns);
        int i2 = i / 52;
        int i3 = i - (i2 * 52);
        double[] dArr = blockRealMatrix.blocks[0];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < this.blockColumns; i6++) {
            int blockWidth = blockWidth(i6);
            double[] dArr2 = this.blocks[(this.blockColumns * i2) + i6];
            int length = dArr.length - i4;
            if (blockWidth > length) {
                int i7 = i3 * blockWidth;
                java.lang.System.arraycopy(dArr2, i7, dArr, i4, length);
                i5++;
                dArr = blockRealMatrix.blocks[i5];
                int i8 = blockWidth - length;
                java.lang.System.arraycopy(dArr2, i7, dArr, 0, i8);
                i4 = i8;
            } else {
                java.lang.System.arraycopy(dArr2, i3 * blockWidth, dArr, i4, blockWidth);
                i4 += blockWidth;
            }
        }
        return blockRealMatrix;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void setRowMatrix(int i, org.apache.commons.math.linear.RealMatrix realMatrix) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        try {
            setRowMatrix(i, (org.apache.commons.math.linear.BlockRealMatrix) realMatrix);
        } catch (java.lang.ClassCastException e) {
            super.setRowMatrix(i, realMatrix);
        }
    }

    public void setRowMatrix(int i, org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        int columnDimension = getColumnDimension();
        if (blockRealMatrix.getRowDimension() != 1 || blockRealMatrix.getColumnDimension() != columnDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(blockRealMatrix.getRowDimension()), java.lang.Integer.valueOf(blockRealMatrix.getColumnDimension()), 1, java.lang.Integer.valueOf(columnDimension));
        }
        int i2 = i / 52;
        int i3 = i - (i2 * 52);
        double[] dArr = blockRealMatrix.blocks[0];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < this.blockColumns; i6++) {
            int blockWidth = blockWidth(i6);
            double[] dArr2 = this.blocks[(this.blockColumns * i2) + i6];
            int length = dArr.length - i4;
            if (blockWidth > length) {
                int i7 = i3 * blockWidth;
                java.lang.System.arraycopy(dArr, i4, dArr2, i7, length);
                i5++;
                dArr = blockRealMatrix.blocks[i5];
                int i8 = blockWidth - length;
                java.lang.System.arraycopy(dArr, 0, dArr2, i7, i8);
                i4 = i8;
            } else {
                java.lang.System.arraycopy(dArr, i4, dArr2, i3 * blockWidth, blockWidth);
                i4 += blockWidth;
            }
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.BlockRealMatrix getColumnMatrix(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i);
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = new org.apache.commons.math.linear.BlockRealMatrix(this.rows, 1);
        int i2 = i / 52;
        int i3 = i - (i2 * 52);
        int blockWidth = blockWidth(i2);
        double[] dArr = blockRealMatrix.blocks[0];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < this.blockRows; i6++) {
            int blockHeight = blockHeight(i6);
            double[] dArr2 = this.blocks[(this.blockColumns * i6) + i2];
            int i7 = 0;
            while (i7 < blockHeight) {
                if (i4 >= dArr.length) {
                    i5++;
                    dArr = blockRealMatrix.blocks[i5];
                    i4 = 0;
                }
                dArr[i4] = dArr2[(i7 * blockWidth) + i3];
                i7++;
                i4++;
            }
        }
        return blockRealMatrix;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void setColumnMatrix(int i, org.apache.commons.math.linear.RealMatrix realMatrix) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        try {
            setColumnMatrix(i, (org.apache.commons.math.linear.BlockRealMatrix) realMatrix);
        } catch (java.lang.ClassCastException e) {
            super.setColumnMatrix(i, realMatrix);
        }
    }

    void setColumnMatrix(int i, org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i);
        int rowDimension = getRowDimension();
        if (blockRealMatrix.getRowDimension() != rowDimension || blockRealMatrix.getColumnDimension() != 1) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(blockRealMatrix.getRowDimension()), java.lang.Integer.valueOf(blockRealMatrix.getColumnDimension()), java.lang.Integer.valueOf(rowDimension), 1);
        }
        int i2 = i / 52;
        int i3 = i - (i2 * 52);
        int blockWidth = blockWidth(i2);
        double[] dArr = blockRealMatrix.blocks[0];
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < this.blockRows; i6++) {
            int blockHeight = blockHeight(i6);
            double[] dArr2 = this.blocks[(this.blockColumns * i6) + i2];
            int i7 = 0;
            while (i7 < blockHeight) {
                if (i4 >= dArr.length) {
                    i5++;
                    dArr = blockRealMatrix.blocks[i5];
                    i4 = 0;
                }
                dArr2[(i7 * blockWidth) + i3] = dArr[i4];
                i7++;
                i4++;
            }
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealVector getRowVector(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        double[] dArr = new double[this.columns];
        int i2 = i / 52;
        int i3 = i - (i2 * 52);
        int i4 = 0;
        for (int i5 = 0; i5 < this.blockColumns; i5++) {
            int blockWidth = blockWidth(i5);
            java.lang.System.arraycopy(this.blocks[(this.blockColumns * i2) + i5], i3 * blockWidth, dArr, i4, blockWidth);
            i4 += blockWidth;
        }
        return new org.apache.commons.math.linear.ArrayRealVector(dArr, false);
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void setRowVector(int i, org.apache.commons.math.linear.RealVector realVector) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        try {
            setRow(i, ((org.apache.commons.math.linear.ArrayRealVector) realVector).getDataRef());
        } catch (java.lang.ClassCastException e) {
            super.setRowVector(i, realVector);
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealVector getColumnVector(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i);
        double[] dArr = new double[this.rows];
        int i2 = i / 52;
        int i3 = i - (i2 * 52);
        int blockWidth = blockWidth(i2);
        int i4 = 0;
        for (int i5 = 0; i5 < this.blockRows; i5++) {
            int blockHeight = blockHeight(i5);
            double[] dArr2 = this.blocks[(this.blockColumns * i5) + i2];
            int i6 = 0;
            while (i6 < blockHeight) {
                dArr[i4] = dArr2[(i6 * blockWidth) + i3];
                i6++;
                i4++;
            }
        }
        return new org.apache.commons.math.linear.ArrayRealVector(dArr, false);
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void setColumnVector(int i, org.apache.commons.math.linear.RealVector realVector) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        try {
            setColumn(i, ((org.apache.commons.math.linear.ArrayRealVector) realVector).getDataRef());
        } catch (java.lang.ClassCastException e) {
            super.setColumnVector(i, realVector);
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double[] getRow(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        double[] dArr = new double[this.columns];
        int i2 = i / 52;
        int i3 = i - (i2 * 52);
        int i4 = 0;
        for (int i5 = 0; i5 < this.blockColumns; i5++) {
            int blockWidth = blockWidth(i5);
            java.lang.System.arraycopy(this.blocks[(this.blockColumns * i2) + i5], i3 * blockWidth, dArr, i4, blockWidth);
            i4 += blockWidth;
        }
        return dArr;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void setRow(int i, double[] dArr) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        int columnDimension = getColumnDimension();
        if (dArr.length != columnDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, 1, java.lang.Integer.valueOf(dArr.length), 1, java.lang.Integer.valueOf(columnDimension));
        }
        int i2 = i / 52;
        int i3 = i - (i2 * 52);
        int i4 = 0;
        for (int i5 = 0; i5 < this.blockColumns; i5++) {
            int blockWidth = blockWidth(i5);
            java.lang.System.arraycopy(dArr, i4, this.blocks[(this.blockColumns * i2) + i5], i3 * blockWidth, blockWidth);
            i4 += blockWidth;
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double[] getColumn(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i);
        double[] dArr = new double[this.rows];
        int i2 = i / 52;
        int i3 = i - (i2 * 52);
        int blockWidth = blockWidth(i2);
        int i4 = 0;
        for (int i5 = 0; i5 < this.blockRows; i5++) {
            int blockHeight = blockHeight(i5);
            double[] dArr2 = this.blocks[(this.blockColumns * i5) + i2];
            int i6 = 0;
            while (i6 < blockHeight) {
                dArr[i4] = dArr2[(i6 * blockWidth) + i3];
                i6++;
                i4++;
            }
        }
        return dArr;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void setColumn(int i, double[] dArr) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.InvalidMatrixException {
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i);
        int rowDimension = getRowDimension();
        if (dArr.length != rowDimension) {
            throw new org.apache.commons.math.linear.InvalidMatrixException(org.apache.commons.math.exception.util.LocalizedFormats.DIMENSIONS_MISMATCH_2x2, java.lang.Integer.valueOf(dArr.length), 1, java.lang.Integer.valueOf(rowDimension), 1);
        }
        int i2 = i / 52;
        int i3 = i - (i2 * 52);
        int blockWidth = blockWidth(i2);
        int i4 = 0;
        for (int i5 = 0; i5 < this.blockRows; i5++) {
            int blockHeight = blockHeight(i5);
            double[] dArr2 = this.blocks[(this.blockColumns * i5) + i2];
            int i6 = 0;
            while (i6 < blockHeight) {
                dArr2[(i6 * blockWidth) + i3] = dArr[i4];
                i6++;
                i4++;
            }
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double getEntry(int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        try {
            int i3 = i / 52;
            int i4 = i2 / 52;
            return this.blocks[(i3 * this.blockColumns) + i4][((i - (i3 * 52)) * blockWidth(i4)) + (i2 - (i4 * 52))];
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()));
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void setEntry(int i, int i2, double d) throws org.apache.commons.math.linear.MatrixIndexException {
        try {
            int i3 = i / 52;
            int i4 = i2 / 52;
            this.blocks[(i3 * this.blockColumns) + i4][((i - (i3 * 52)) * blockWidth(i4)) + (i2 - (i4 * 52))] = d;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()));
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void addToEntry(int i, int i2, double d) throws org.apache.commons.math.linear.MatrixIndexException {
        try {
            int i3 = i / 52;
            int i4 = i2 / 52;
            int blockWidth = ((i - (i3 * 52)) * blockWidth(i4)) + (i2 - (i4 * 52));
            double[] dArr = this.blocks[(i3 * this.blockColumns) + i4];
            dArr[blockWidth] = dArr[blockWidth] + d;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()));
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void multiplyEntry(int i, int i2, double d) throws org.apache.commons.math.linear.MatrixIndexException {
        try {
            int i3 = i / 52;
            int i4 = i2 / 52;
            int blockWidth = ((i - (i3 * 52)) * blockWidth(i4)) + (i2 - (i4 * 52));
            double[] dArr = this.blocks[(i3 * this.blockColumns) + i4];
            dArr[blockWidth] = dArr[blockWidth] * d;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            throw new org.apache.commons.math.linear.MatrixIndexException(org.apache.commons.math.exception.util.LocalizedFormats.NO_SUCH_MATRIX_ENTRY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(getRowDimension()), java.lang.Integer.valueOf(getColumnDimension()));
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.BlockRealMatrix transpose() {
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = new org.apache.commons.math.linear.BlockRealMatrix(getColumnDimension(), getRowDimension());
        int i = 0;
        for (int i2 = 0; i2 < this.blockColumns; i2++) {
            for (int i3 = 0; i3 < this.blockRows; i3++) {
                double[] dArr = blockRealMatrix.blocks[i];
                double[] dArr2 = this.blocks[(this.blockColumns * i3) + i2];
                int i4 = i2 * 52;
                int min = org.apache.commons.math.util.FastMath.min(i4 + 52, this.columns);
                int i5 = i3 * 52;
                int min2 = org.apache.commons.math.util.FastMath.min(i5 + 52, this.rows);
                int i6 = 0;
                for (int i7 = i4; i7 < min; i7++) {
                    int i8 = min - i4;
                    int i9 = i7 - i4;
                    for (int i10 = i5; i10 < min2; i10++) {
                        dArr[i6] = dArr2[i9];
                        i6++;
                        i9 += i8;
                    }
                }
                i++;
            }
        }
        return blockRealMatrix;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.AnyMatrix
    public int getRowDimension() {
        return this.rows;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.AnyMatrix
    public int getColumnDimension() {
        return this.columns;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double[] operate(double[] dArr) throws java.lang.IllegalArgumentException {
        if (dArr.length != this.columns) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(this.columns));
        }
        double[] dArr2 = new double[this.rows];
        for (int i = 0; i < this.blockRows; i++) {
            int i2 = i * 52;
            int min = org.apache.commons.math.util.FastMath.min(i2 + 52, this.rows);
            for (int i3 = 0; i3 < this.blockColumns; i3++) {
                double[] dArr3 = this.blocks[(this.blockColumns * i) + i3];
                int i4 = i3 * 52;
                int min2 = org.apache.commons.math.util.FastMath.min(i4 + 52, this.columns);
                int i5 = 0;
                for (int i6 = i2; i6 < min; i6++) {
                    double d = 0.0d;
                    int i7 = i4;
                    while (i7 < min2 - 3) {
                        d += (dArr3[i5] * dArr[i7]) + (dArr3[i5 + 1] * dArr[i7 + 1]) + (dArr3[i5 + 2] * dArr[i7 + 2]) + (dArr3[i5 + 3] * dArr[i7 + 3]);
                        i5 += 4;
                        i7 += 4;
                    }
                    while (i7 < min2) {
                        d += dArr3[i5] * dArr[i7];
                        i7++;
                        i5++;
                    }
                    dArr2[i6] = dArr2[i6] + d;
                }
            }
        }
        return dArr2;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double[] preMultiply(double[] dArr) throws java.lang.IllegalArgumentException {
        int i;
        if (dArr.length != this.rows) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(dArr.length), java.lang.Integer.valueOf(this.rows));
        }
        double[] dArr2 = new double[this.columns];
        for (int i2 = 0; i2 < this.blockColumns; i2++) {
            int blockWidth = blockWidth(i2);
            int i3 = blockWidth + blockWidth;
            int i4 = i3 + blockWidth;
            int i5 = i4 + blockWidth;
            int i6 = i2 * 52;
            int min = org.apache.commons.math.util.FastMath.min(i6 + 52, this.columns);
            for (int i7 = 0; i7 < this.blockRows; i7++) {
                double[] dArr3 = this.blocks[(this.blockColumns * i7) + i2];
                int i8 = i7 * 52;
                int min2 = org.apache.commons.math.util.FastMath.min(i8 + 52, this.rows);
                int i9 = i6;
                while (i9 < min) {
                    int i10 = i9 - i6;
                    double d = 0.0d;
                    int i11 = i8;
                    while (true) {
                        i = i6;
                        if (i11 >= min2 - 3) {
                            break;
                        }
                        d += (dArr3[i10] * dArr[i11]) + (dArr3[i10 + blockWidth] * dArr[i11 + 1]) + (dArr3[i10 + i3] * dArr[i11 + 2]) + (dArr3[i10 + i4] * dArr[i11 + 3]);
                        i10 += i5;
                        i11 += 4;
                        i6 = i;
                    }
                    while (i11 < min2) {
                        d += dArr3[i10] * dArr[i11];
                        i10 += blockWidth;
                        i11++;
                    }
                    dArr2[i9] = dArr2[i9] + d;
                    i9++;
                    i6 = i;
                }
            }
        }
        return dArr2;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInRowOrder(org.apache.commons.math.linear.RealMatrixChangingVisitor realMatrixChangingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        realMatrixChangingVisitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        for (int i = 0; i < this.blockRows; i++) {
            int i2 = i * 52;
            int min = org.apache.commons.math.util.FastMath.min(i2 + 52, this.rows);
            for (int i3 = i2; i3 < min; i3++) {
                for (int i4 = 0; i4 < this.blockColumns; i4++) {
                    int blockWidth = blockWidth(i4);
                    int i5 = i4 * 52;
                    int min2 = org.apache.commons.math.util.FastMath.min(i5 + 52, this.columns);
                    double[] dArr = this.blocks[(this.blockColumns * i) + i4];
                    int i6 = (i3 - i2) * blockWidth;
                    while (i5 < min2) {
                        dArr[i6] = realMatrixChangingVisitor.visit(i3, i5, dArr[i6]);
                        i6++;
                        i5++;
                    }
                }
            }
        }
        return realMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInRowOrder(org.apache.commons.math.linear.RealMatrixPreservingVisitor realMatrixPreservingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        realMatrixPreservingVisitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        for (int i = 0; i < this.blockRows; i++) {
            int i2 = i * 52;
            int min = org.apache.commons.math.util.FastMath.min(i2 + 52, this.rows);
            for (int i3 = i2; i3 < min; i3++) {
                for (int i4 = 0; i4 < this.blockColumns; i4++) {
                    int blockWidth = blockWidth(i4);
                    int i5 = i4 * 52;
                    int min2 = org.apache.commons.math.util.FastMath.min(i5 + 52, this.columns);
                    double[] dArr = this.blocks[(this.blockColumns * i) + i4];
                    int i6 = (i3 - i2) * blockWidth;
                    while (i5 < min2) {
                        realMatrixPreservingVisitor.visit(i3, i5, dArr[i6]);
                        i6++;
                        i5++;
                    }
                }
            }
        }
        return realMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInRowOrder(org.apache.commons.math.linear.RealMatrixChangingVisitor realMatrixChangingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = this;
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(blockRealMatrix, i, i2, i3, i4);
        realMatrixChangingVisitor.start(blockRealMatrix.rows, blockRealMatrix.columns, i, i2, i3, i4);
        int i5 = i / 52;
        while (i5 < (i2 / 52) + 1) {
            int i6 = i5 * 52;
            int max = org.apache.commons.math.util.FastMath.max(i, i6);
            int i7 = i5 + 1;
            int min = org.apache.commons.math.util.FastMath.min(i7 * 52, i2 + 1);
            while (max < min) {
                int i8 = i3 / 52;
                while (i8 < (i4 / 52) + 1) {
                    int blockWidth = blockRealMatrix.blockWidth(i8);
                    int i9 = i8 * 52;
                    int max2 = org.apache.commons.math.util.FastMath.max(i3, i9);
                    int i10 = i8 + 1;
                    int i11 = i7;
                    int min2 = org.apache.commons.math.util.FastMath.min(i10 * 52, i4 + 1);
                    int i12 = min;
                    double[] dArr = blockRealMatrix.blocks[(blockRealMatrix.blockColumns * i5) + i8];
                    int i13 = (((max - i6) * blockWidth) + max2) - i9;
                    while (max2 < min2) {
                        dArr[i13] = realMatrixChangingVisitor.visit(max, max2, dArr[i13]);
                        i13++;
                        max2++;
                        i5 = i5;
                    }
                    blockRealMatrix = this;
                    i8 = i10;
                    i7 = i11;
                    min = i12;
                }
                max++;
                blockRealMatrix = this;
            }
            blockRealMatrix = this;
            i5 = i7;
        }
        return realMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInRowOrder(org.apache.commons.math.linear.RealMatrixPreservingVisitor realMatrixPreservingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = this;
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(blockRealMatrix, i, i2, i3, i4);
        realMatrixPreservingVisitor.start(blockRealMatrix.rows, blockRealMatrix.columns, i, i2, i3, i4);
        int i5 = i / 52;
        while (i5 < (i2 / 52) + 1) {
            int i6 = i5 * 52;
            int max = org.apache.commons.math.util.FastMath.max(i, i6);
            int i7 = i5 + 1;
            int min = org.apache.commons.math.util.FastMath.min(i7 * 52, i2 + 1);
            while (max < min) {
                int i8 = i3 / 52;
                while (i8 < (i4 / 52) + 1) {
                    int blockWidth = blockRealMatrix.blockWidth(i8);
                    int i9 = i8 * 52;
                    int max2 = org.apache.commons.math.util.FastMath.max(i3, i9);
                    int i10 = i8 + 1;
                    int i11 = i7;
                    int min2 = org.apache.commons.math.util.FastMath.min(i10 * 52, i4 + 1);
                    int i12 = min;
                    double[] dArr = blockRealMatrix.blocks[(blockRealMatrix.blockColumns * i5) + i8];
                    int i13 = (((max - i6) * blockWidth) + max2) - i9;
                    while (max2 < min2) {
                        realMatrixPreservingVisitor.visit(max, max2, dArr[i13]);
                        i13++;
                        max2++;
                        i5 = i5;
                    }
                    blockRealMatrix = this;
                    i8 = i10;
                    i7 = i11;
                    min = i12;
                }
                max++;
                blockRealMatrix = this;
            }
            blockRealMatrix = this;
            i5 = i7;
        }
        return realMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInOptimizedOrder(org.apache.commons.math.linear.RealMatrixChangingVisitor realMatrixChangingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        realMatrixChangingVisitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        int i = 0;
        for (int i2 = 0; i2 < this.blockRows; i2++) {
            int i3 = i2 * 52;
            int min = org.apache.commons.math.util.FastMath.min(i3 + 52, this.rows);
            for (int i4 = 0; i4 < this.blockColumns; i4++) {
                int i5 = i4 * 52;
                int min2 = org.apache.commons.math.util.FastMath.min(i5 + 52, this.columns);
                double[] dArr = this.blocks[i];
                int i6 = 0;
                for (int i7 = i3; i7 < min; i7++) {
                    for (int i8 = i5; i8 < min2; i8++) {
                        dArr[i6] = realMatrixChangingVisitor.visit(i7, i8, dArr[i6]);
                        i6++;
                    }
                }
                i++;
            }
        }
        return realMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInOptimizedOrder(org.apache.commons.math.linear.RealMatrixPreservingVisitor realMatrixPreservingVisitor) throws org.apache.commons.math.linear.MatrixVisitorException {
        realMatrixPreservingVisitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        int i = 0;
        for (int i2 = 0; i2 < this.blockRows; i2++) {
            int i3 = i2 * 52;
            int min = org.apache.commons.math.util.FastMath.min(i3 + 52, this.rows);
            for (int i4 = 0; i4 < this.blockColumns; i4++) {
                int i5 = i4 * 52;
                int min2 = org.apache.commons.math.util.FastMath.min(i5 + 52, this.columns);
                double[] dArr = this.blocks[i];
                int i6 = 0;
                for (int i7 = i3; i7 < min; i7++) {
                    for (int i8 = i5; i8 < min2; i8++) {
                        realMatrixPreservingVisitor.visit(i7, i8, dArr[i6]);
                        i6++;
                    }
                }
                i++;
            }
        }
        return realMatrixPreservingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInOptimizedOrder(org.apache.commons.math.linear.RealMatrixChangingVisitor realMatrixChangingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = this;
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(blockRealMatrix, i, i2, i3, i4);
        realMatrixChangingVisitor.start(blockRealMatrix.rows, blockRealMatrix.columns, i, i2, i3, i4);
        int i5 = i / 52;
        while (i5 < (i2 / 52) + 1) {
            int i6 = i5 * 52;
            int max = org.apache.commons.math.util.FastMath.max(i, i6);
            int i7 = i5 + 1;
            int min = org.apache.commons.math.util.FastMath.min(i7 * 52, i2 + 1);
            int i8 = i3 / 52;
            while (i8 < (i4 / 52) + 1) {
                int blockWidth = blockRealMatrix.blockWidth(i8);
                int i9 = i8 * 52;
                int max2 = org.apache.commons.math.util.FastMath.max(i3, i9);
                int i10 = i8 + 1;
                int i11 = max;
                int min2 = org.apache.commons.math.util.FastMath.min(i10 * 52, i4 + 1);
                int i12 = i7;
                double[] dArr = blockRealMatrix.blocks[(blockRealMatrix.blockColumns * i5) + i8];
                int i13 = i11;
                while (i13 < min) {
                    int i14 = (((i13 - i6) * blockWidth) + max2) - i9;
                    int i15 = max2;
                    while (i15 < min2) {
                        dArr[i14] = realMatrixChangingVisitor.visit(i13, i15, dArr[i14]);
                        i14++;
                        i15++;
                        i5 = i5;
                        i6 = i6;
                        min2 = min2;
                    }
                    i13++;
                    min2 = min2;
                }
                blockRealMatrix = this;
                i8 = i10;
                max = i11;
                i7 = i12;
            }
            blockRealMatrix = this;
            i5 = i7;
        }
        return realMatrixChangingVisitor.end();
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double walkInOptimizedOrder(org.apache.commons.math.linear.RealMatrixPreservingVisitor realMatrixPreservingVisitor, int i, int i2, int i3, int i4) throws org.apache.commons.math.linear.MatrixIndexException, org.apache.commons.math.linear.MatrixVisitorException {
        org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = this;
        org.apache.commons.math.linear.MatrixUtils.checkSubMatrixIndex(blockRealMatrix, i, i2, i3, i4);
        realMatrixPreservingVisitor.start(blockRealMatrix.rows, blockRealMatrix.columns, i, i2, i3, i4);
        int i5 = i / 52;
        while (i5 < (i2 / 52) + 1) {
            int i6 = i5 * 52;
            int max = org.apache.commons.math.util.FastMath.max(i, i6);
            int i7 = i5 + 1;
            int min = org.apache.commons.math.util.FastMath.min(i7 * 52, i2 + 1);
            int i8 = i3 / 52;
            while (i8 < (i4 / 52) + 1) {
                int blockWidth = blockRealMatrix.blockWidth(i8);
                int i9 = i8 * 52;
                int max2 = org.apache.commons.math.util.FastMath.max(i3, i9);
                int i10 = i8 + 1;
                int i11 = max;
                int min2 = org.apache.commons.math.util.FastMath.min(i10 * 52, i4 + 1);
                int i12 = i7;
                double[] dArr = blockRealMatrix.blocks[(blockRealMatrix.blockColumns * i5) + i8];
                int i13 = i11;
                while (i13 < min) {
                    int i14 = (((i13 - i6) * blockWidth) + max2) - i9;
                    int i15 = max2;
                    while (i15 < min2) {
                        realMatrixPreservingVisitor.visit(i13, i15, dArr[i14]);
                        i14++;
                        i15++;
                        i5 = i5;
                        i6 = i6;
                        min2 = min2;
                    }
                    i13++;
                    min2 = min2;
                }
                blockRealMatrix = this;
                i8 = i10;
                max = i11;
                i7 = i12;
            }
            blockRealMatrix = this;
            i5 = i7;
        }
        return realMatrixPreservingVisitor.end();
    }

    private int blockHeight(int i) {
        if (i == this.blockRows - 1) {
            return this.rows - (i * 52);
        }
        return 52;
    }

    private int blockWidth(int i) {
        if (i == this.blockColumns - 1) {
            return this.columns - (i * 52);
        }
        return 52;
    }
}
