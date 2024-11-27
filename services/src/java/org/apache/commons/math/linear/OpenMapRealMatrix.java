package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class OpenMapRealMatrix extends org.apache.commons.math.linear.AbstractRealMatrix implements org.apache.commons.math.linear.SparseRealMatrix, java.io.Serializable {
    private static final long serialVersionUID = -5962461716457143437L;
    private final int columns;
    private final org.apache.commons.math.util.OpenIntToDoubleHashMap entries;
    private final int rows;

    public OpenMapRealMatrix(int i, int i2) {
        super(i, i2);
        this.rows = i;
        this.columns = i2;
        this.entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(0.0d);
    }

    public OpenMapRealMatrix(org.apache.commons.math.linear.OpenMapRealMatrix openMapRealMatrix) {
        this.rows = openMapRealMatrix.rows;
        this.columns = openMapRealMatrix.columns;
        this.entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(openMapRealMatrix.entries);
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.OpenMapRealMatrix copy() {
        return new org.apache.commons.math.linear.OpenMapRealMatrix(this);
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.OpenMapRealMatrix createMatrix(int i, int i2) throws java.lang.IllegalArgumentException {
        return new org.apache.commons.math.linear.OpenMapRealMatrix(i, i2);
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.AnyMatrix
    public int getColumnDimension() {
        return this.columns;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.OpenMapRealMatrix add(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
        try {
            return add((org.apache.commons.math.linear.OpenMapRealMatrix) realMatrix);
        } catch (java.lang.ClassCastException e) {
            return (org.apache.commons.math.linear.OpenMapRealMatrix) super.add(realMatrix);
        }
    }

    public org.apache.commons.math.linear.OpenMapRealMatrix add(org.apache.commons.math.linear.OpenMapRealMatrix openMapRealMatrix) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkAdditionCompatible(this, openMapRealMatrix);
        org.apache.commons.math.linear.OpenMapRealMatrix openMapRealMatrix2 = new org.apache.commons.math.linear.OpenMapRealMatrix(this);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = openMapRealMatrix.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            int key = it.key() / this.columns;
            int key2 = it.key() - (this.columns * key);
            openMapRealMatrix2.setEntry(key, key2, getEntry(key, key2) + it.value());
        }
        return openMapRealMatrix2;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.OpenMapRealMatrix subtract(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
        try {
            return subtract((org.apache.commons.math.linear.OpenMapRealMatrix) realMatrix);
        } catch (java.lang.ClassCastException e) {
            return (org.apache.commons.math.linear.OpenMapRealMatrix) super.subtract(realMatrix);
        }
    }

    public org.apache.commons.math.linear.OpenMapRealMatrix subtract(org.apache.commons.math.linear.OpenMapRealMatrix openMapRealMatrix) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkAdditionCompatible(this, openMapRealMatrix);
        org.apache.commons.math.linear.OpenMapRealMatrix openMapRealMatrix2 = new org.apache.commons.math.linear.OpenMapRealMatrix(this);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = openMapRealMatrix.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            int key = it.key() / this.columns;
            int key2 = it.key() - (this.columns * key);
            openMapRealMatrix2.setEntry(key, key2, getEntry(key, key2) - it.value());
        }
        return openMapRealMatrix2;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public org.apache.commons.math.linear.RealMatrix multiply(org.apache.commons.math.linear.RealMatrix realMatrix) throws java.lang.IllegalArgumentException {
        try {
            return multiply((org.apache.commons.math.linear.OpenMapRealMatrix) realMatrix);
        } catch (java.lang.ClassCastException e) {
            org.apache.commons.math.linear.MatrixUtils.checkMultiplicationCompatible(this, realMatrix);
            int columnDimension = realMatrix.getColumnDimension();
            org.apache.commons.math.linear.BlockRealMatrix blockRealMatrix = new org.apache.commons.math.linear.BlockRealMatrix(this.rows, columnDimension);
            org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = this.entries.iterator();
            while (it.hasNext()) {
                it.advance();
                double value = it.value();
                int key = it.key();
                int i = key / this.columns;
                int i2 = key % this.columns;
                for (int i3 = 0; i3 < columnDimension; i3++) {
                    blockRealMatrix.addToEntry(i, i3, realMatrix.getEntry(i2, i3) * value);
                }
            }
            return blockRealMatrix;
        }
    }

    public org.apache.commons.math.linear.OpenMapRealMatrix multiply(org.apache.commons.math.linear.OpenMapRealMatrix openMapRealMatrix) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.MatrixUtils.checkMultiplicationCompatible(this, openMapRealMatrix);
        int columnDimension = openMapRealMatrix.getColumnDimension();
        org.apache.commons.math.linear.OpenMapRealMatrix openMapRealMatrix2 = new org.apache.commons.math.linear.OpenMapRealMatrix(this.rows, columnDimension);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            double value = it.value();
            int key = it.key();
            int i = key / this.columns;
            int i2 = key % this.columns;
            for (int i3 = 0; i3 < columnDimension; i3++) {
                int computeKey = openMapRealMatrix.computeKey(i2, i3);
                if (openMapRealMatrix.entries.containsKey(computeKey)) {
                    int computeKey2 = openMapRealMatrix2.computeKey(i, i3);
                    double d = openMapRealMatrix2.entries.get(computeKey2) + (openMapRealMatrix.entries.get(computeKey) * value);
                    if (d == 0.0d) {
                        openMapRealMatrix2.entries.remove(computeKey2);
                    } else {
                        openMapRealMatrix2.entries.put(computeKey2, d);
                    }
                }
            }
        }
        return openMapRealMatrix2;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public double getEntry(int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i2);
        return this.entries.get(computeKey(i, i2));
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.AnyMatrix
    public int getRowDimension() {
        return this.rows;
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void setEntry(int i, int i2, double d) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i2);
        if (d == 0.0d) {
            this.entries.remove(computeKey(i, i2));
        } else {
            this.entries.put(computeKey(i, i2), d);
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void addToEntry(int i, int i2, double d) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i2);
        int computeKey = computeKey(i, i2);
        double d2 = this.entries.get(computeKey) + d;
        if (d2 == 0.0d) {
            this.entries.remove(computeKey);
        } else {
            this.entries.put(computeKey, d2);
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealMatrix, org.apache.commons.math.linear.RealMatrix
    public void multiplyEntry(int i, int i2, double d) throws org.apache.commons.math.linear.MatrixIndexException {
        org.apache.commons.math.linear.MatrixUtils.checkRowIndex(this, i);
        org.apache.commons.math.linear.MatrixUtils.checkColumnIndex(this, i2);
        int computeKey = computeKey(i, i2);
        double d2 = this.entries.get(computeKey) * d;
        if (d2 == 0.0d) {
            this.entries.remove(computeKey);
        } else {
            this.entries.put(computeKey, d2);
        }
    }

    private int computeKey(int i, int i2) {
        return (i * this.columns) + i2;
    }
}
