package android.util;

/* loaded from: classes3.dex */
public class SparseDoubleArray implements java.lang.Cloneable {
    private android.util.SparseLongArray mValues;

    public SparseDoubleArray() {
        this(0);
    }

    public SparseDoubleArray(int i) {
        this.mValues = new android.util.SparseLongArray(i);
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.util.SparseDoubleArray m4837clone() {
        android.util.SparseDoubleArray sparseDoubleArray;
        android.util.SparseDoubleArray sparseDoubleArray2 = null;
        try {
            sparseDoubleArray = (android.util.SparseDoubleArray) super.clone();
        } catch (java.lang.CloneNotSupportedException e) {
        }
        try {
            sparseDoubleArray.mValues = this.mValues.m4839clone();
            return sparseDoubleArray;
        } catch (java.lang.CloneNotSupportedException e2) {
            sparseDoubleArray2 = sparseDoubleArray;
            return sparseDoubleArray2;
        }
    }

    public double get(int i) {
        return get(i, 0.0d);
    }

    public double get(int i, double d) {
        int indexOfKey = this.mValues.indexOfKey(i);
        if (indexOfKey < 0) {
            return d;
        }
        return valueAt(indexOfKey);
    }

    public void put(int i, double d) {
        this.mValues.put(i, java.lang.Double.doubleToRawLongBits(d));
    }

    public void incrementValue(int i, double d) {
        put(i, get(i) + d);
    }

    public int size() {
        return this.mValues.size();
    }

    public int indexOfKey(int i) {
        return this.mValues.indexOfKey(i);
    }

    public int keyAt(int i) {
        return this.mValues.keyAt(i);
    }

    public double valueAt(int i) {
        return java.lang.Double.longBitsToDouble(this.mValues.valueAt(i));
    }

    public void setValueAt(int i, double d) {
        this.mValues.setValueAt(i, java.lang.Double.doubleToRawLongBits(d));
    }

    public void removeAt(int i) {
        this.mValues.removeAt(i);
    }

    public void delete(int i) {
        this.mValues.delete(i);
    }

    public void clear() {
        this.mValues.clear();
    }

    public java.lang.String toString() {
        if (size() <= 0) {
            return "{}";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder(size() * 34);
        sb.append('{');
        for (int i = 0; i < size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            sb.append(valueAt(i));
        }
        sb.append('}');
        return sb.toString();
    }
}
