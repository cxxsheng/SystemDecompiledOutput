package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class OpenMapRealVector extends org.apache.commons.math.linear.AbstractRealVector implements org.apache.commons.math.linear.SparseRealVector, java.io.Serializable {
    public static final double DEFAULT_ZERO_TOLERANCE = 1.0E-12d;
    private static final long serialVersionUID = 8772222695580707260L;
    private final org.apache.commons.math.util.OpenIntToDoubleHashMap entries;
    private final double epsilon;
    private final int virtualSize;

    public OpenMapRealVector() {
        this(0, 1.0E-12d);
    }

    public OpenMapRealVector(int i) {
        this(i, 1.0E-12d);
    }

    public OpenMapRealVector(int i, double d) {
        this.virtualSize = i;
        this.entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(0.0d);
        this.epsilon = d;
    }

    protected OpenMapRealVector(org.apache.commons.math.linear.OpenMapRealVector openMapRealVector, int i) {
        this.virtualSize = openMapRealVector.getDimension() + i;
        this.entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(openMapRealVector.entries);
        this.epsilon = openMapRealVector.epsilon;
    }

    public OpenMapRealVector(int i, int i2) {
        this(i, i2, 1.0E-12d);
    }

    public OpenMapRealVector(int i, int i2, double d) {
        this.virtualSize = i;
        this.entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(i2, 0.0d);
        this.epsilon = d;
    }

    public OpenMapRealVector(double[] dArr) {
        this(dArr, 1.0E-12d);
    }

    public OpenMapRealVector(double[] dArr, double d) {
        this.virtualSize = dArr.length;
        this.entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(0.0d);
        this.epsilon = d;
        for (int i = 0; i < dArr.length; i++) {
            double d2 = dArr[i];
            if (!isDefaultValue(d2)) {
                this.entries.put(i, d2);
            }
        }
    }

    public OpenMapRealVector(java.lang.Double[] dArr) {
        this(dArr, 1.0E-12d);
    }

    public OpenMapRealVector(java.lang.Double[] dArr, double d) {
        this.virtualSize = dArr.length;
        this.entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(0.0d);
        this.epsilon = d;
        for (int i = 0; i < dArr.length; i++) {
            double doubleValue = dArr[i].doubleValue();
            if (!isDefaultValue(doubleValue)) {
                this.entries.put(i, doubleValue);
            }
        }
    }

    public OpenMapRealVector(org.apache.commons.math.linear.OpenMapRealVector openMapRealVector) {
        this.virtualSize = openMapRealVector.getDimension();
        this.entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(openMapRealVector.getEntries());
        this.epsilon = openMapRealVector.epsilon;
    }

    public OpenMapRealVector(org.apache.commons.math.linear.RealVector realVector) {
        this.virtualSize = realVector.getDimension();
        this.entries = new org.apache.commons.math.util.OpenIntToDoubleHashMap(0.0d);
        this.epsilon = 1.0E-12d;
        for (int i = 0; i < this.virtualSize; i++) {
            double entry = realVector.getEntry(i);
            if (!isDefaultValue(entry)) {
                this.entries.put(i, entry);
            }
        }
    }

    private org.apache.commons.math.util.OpenIntToDoubleHashMap getEntries() {
        return this.entries;
    }

    protected boolean isDefaultValue(double d) {
        return org.apache.commons.math.util.FastMath.abs(d) < this.epsilon;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector add(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(realVector.getDimension());
        if (realVector instanceof org.apache.commons.math.linear.OpenMapRealVector) {
            return add((org.apache.commons.math.linear.OpenMapRealVector) realVector);
        }
        return super.add(realVector);
    }

    public org.apache.commons.math.linear.OpenMapRealVector add(org.apache.commons.math.linear.OpenMapRealVector openMapRealVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(openMapRealVector.getDimension());
        boolean z = this.entries.size() > openMapRealVector.entries.size();
        org.apache.commons.math.linear.OpenMapRealVector copy = z ? copy() : openMapRealVector.copy();
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = (z ? openMapRealVector.entries : this.entries).iterator();
        org.apache.commons.math.util.OpenIntToDoubleHashMap openIntToDoubleHashMap = z ? this.entries : openMapRealVector.entries;
        while (it.hasNext()) {
            it.advance();
            int key = it.key();
            if (openIntToDoubleHashMap.containsKey(key)) {
                copy.setEntry(key, openIntToDoubleHashMap.get(key) + it.value());
            } else {
                copy.setEntry(key, it.value());
            }
        }
        return copy;
    }

    public org.apache.commons.math.linear.OpenMapRealVector append(org.apache.commons.math.linear.OpenMapRealVector openMapRealVector) {
        org.apache.commons.math.linear.OpenMapRealVector openMapRealVector2 = new org.apache.commons.math.linear.OpenMapRealVector(this, openMapRealVector.getDimension());
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = openMapRealVector.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            openMapRealVector2.setEntry(it.key() + this.virtualSize, it.value());
        }
        return openMapRealVector2;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector append(org.apache.commons.math.linear.RealVector realVector) {
        if (realVector instanceof org.apache.commons.math.linear.OpenMapRealVector) {
            return append((org.apache.commons.math.linear.OpenMapRealVector) realVector);
        }
        return append(realVector.getData());
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector append(double d) {
        org.apache.commons.math.linear.OpenMapRealVector openMapRealVector = new org.apache.commons.math.linear.OpenMapRealVector(this, 1);
        openMapRealVector.setEntry(this.virtualSize, d);
        return openMapRealVector;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector append(double[] dArr) {
        org.apache.commons.math.linear.OpenMapRealVector openMapRealVector = new org.apache.commons.math.linear.OpenMapRealVector(this, dArr.length);
        for (int i = 0; i < dArr.length; i++) {
            openMapRealVector.setEntry(this.virtualSize + i, dArr[i]);
        }
        return openMapRealVector;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector copy() {
        return new org.apache.commons.math.linear.OpenMapRealVector(this);
    }

    public double dotProduct(org.apache.commons.math.linear.OpenMapRealVector openMapRealVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(openMapRealVector.getDimension());
        boolean z = this.entries.size() < openMapRealVector.entries.size();
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = (z ? this.entries : openMapRealVector.entries).iterator();
        org.apache.commons.math.util.OpenIntToDoubleHashMap openIntToDoubleHashMap = z ? openMapRealVector.entries : this.entries;
        double d = 0.0d;
        while (it.hasNext()) {
            it.advance();
            d += it.value() * openIntToDoubleHashMap.get(it.key());
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double dotProduct(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        if (realVector instanceof org.apache.commons.math.linear.OpenMapRealVector) {
            return dotProduct((org.apache.commons.math.linear.OpenMapRealVector) realVector);
        }
        return super.dotProduct(realVector);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector ebeDivide(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(realVector.getDimension());
        org.apache.commons.math.linear.OpenMapRealVector openMapRealVector = new org.apache.commons.math.linear.OpenMapRealVector(this);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = openMapRealVector.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            openMapRealVector.setEntry(it.key(), it.value() / realVector.getEntry(it.key()));
        }
        return openMapRealVector;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector ebeDivide(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        org.apache.commons.math.linear.OpenMapRealVector openMapRealVector = new org.apache.commons.math.linear.OpenMapRealVector(this);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = openMapRealVector.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            openMapRealVector.setEntry(it.key(), it.value() / dArr[it.key()]);
        }
        return openMapRealVector;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector ebeMultiply(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(realVector.getDimension());
        org.apache.commons.math.linear.OpenMapRealVector openMapRealVector = new org.apache.commons.math.linear.OpenMapRealVector(this);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = openMapRealVector.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            openMapRealVector.setEntry(it.key(), it.value() * realVector.getEntry(it.key()));
        }
        return openMapRealVector;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector ebeMultiply(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        org.apache.commons.math.linear.OpenMapRealVector openMapRealVector = new org.apache.commons.math.linear.OpenMapRealVector(this);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = openMapRealVector.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            openMapRealVector.setEntry(it.key(), it.value() * dArr[it.key()]);
        }
        return openMapRealVector;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector getSubVector(int i, int i2) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(i);
        int i3 = i + i2;
        checkIndex(i3 - 1);
        org.apache.commons.math.linear.OpenMapRealVector openMapRealVector = new org.apache.commons.math.linear.OpenMapRealVector(i2);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            int key = it.key();
            if (key >= i && key < i3) {
                openMapRealVector.setEntry(key - i, it.value());
            }
        }
        return openMapRealVector;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double[] getData() {
        double[] dArr = new double[this.virtualSize];
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            dArr[it.key()] = it.value();
        }
        return dArr;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public int getDimension() {
        return this.virtualSize;
    }

    public double getDistance(org.apache.commons.math.linear.OpenMapRealVector openMapRealVector) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = this.entries.iterator();
        double d = 0.0d;
        while (it.hasNext()) {
            it.advance();
            double value = it.value() - openMapRealVector.getEntry(it.key());
            d += value * value;
        }
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it2 = openMapRealVector.getEntries().iterator();
        while (it2.hasNext()) {
            it2.advance();
            if (!this.entries.containsKey(it2.key())) {
                double value2 = it2.value();
                d += value2 * value2;
            }
        }
        return org.apache.commons.math.util.FastMath.sqrt(d);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getDistance(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(realVector.getDimension());
        if (realVector instanceof org.apache.commons.math.linear.OpenMapRealVector) {
            return getDistance((org.apache.commons.math.linear.OpenMapRealVector) realVector);
        }
        return getDistance(realVector.getData());
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getDistance(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        double d = 0.0d;
        for (int i = 0; i < dArr.length; i++) {
            double d2 = this.entries.get(i) - dArr[i];
            d += d2 * d2;
        }
        return org.apache.commons.math.util.FastMath.sqrt(d);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double getEntry(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(i);
        return this.entries.get(i);
    }

    public double getL1Distance(org.apache.commons.math.linear.OpenMapRealVector openMapRealVector) {
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = this.entries.iterator();
        double d = 0.0d;
        while (it.hasNext()) {
            it.advance();
            d += org.apache.commons.math.util.FastMath.abs(it.value() - openMapRealVector.getEntry(it.key()));
        }
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it2 = openMapRealVector.getEntries().iterator();
        while (it2.hasNext()) {
            it2.advance();
            if (!this.entries.containsKey(it2.key())) {
                d += org.apache.commons.math.util.FastMath.abs(org.apache.commons.math.util.FastMath.abs(it2.value()));
            }
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getL1Distance(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(realVector.getDimension());
        if (realVector instanceof org.apache.commons.math.linear.OpenMapRealVector) {
            return getL1Distance((org.apache.commons.math.linear.OpenMapRealVector) realVector);
        }
        return getL1Distance(realVector.getData());
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getL1Distance(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        double d = 0.0d;
        for (int i = 0; i < dArr.length; i++) {
            d += org.apache.commons.math.util.FastMath.abs(getEntry(i) - dArr[i]);
        }
        return d;
    }

    private double getLInfDistance(org.apache.commons.math.linear.OpenMapRealVector openMapRealVector) {
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = this.entries.iterator();
        double d = 0.0d;
        while (it.hasNext()) {
            it.advance();
            double abs = org.apache.commons.math.util.FastMath.abs(it.value() - openMapRealVector.getEntry(it.key()));
            if (abs > d) {
                d = abs;
            }
        }
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it2 = openMapRealVector.getEntries().iterator();
        while (it2.hasNext()) {
            it2.advance();
            if (!this.entries.containsKey(it2.key()) && it2.value() > d) {
                d = it2.value();
            }
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getLInfDistance(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(realVector.getDimension());
        if (realVector instanceof org.apache.commons.math.linear.OpenMapRealVector) {
            return getLInfDistance((org.apache.commons.math.linear.OpenMapRealVector) realVector);
        }
        return getLInfDistance(realVector.getData());
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getLInfDistance(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        double d = 0.0d;
        for (int i = 0; i < dArr.length; i++) {
            double abs = org.apache.commons.math.util.FastMath.abs(getEntry(i) - dArr[i]);
            if (abs > d) {
                d = abs;
            }
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public boolean isInfinite() {
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = this.entries.iterator();
        boolean z = false;
        while (it.hasNext()) {
            it.advance();
            double value = it.value();
            if (java.lang.Double.isNaN(value)) {
                return false;
            }
            if (java.lang.Double.isInfinite(value)) {
                z = true;
            }
        }
        return z;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public boolean isNaN() {
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            if (java.lang.Double.isNaN(it.value())) {
                return true;
            }
        }
        return false;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector mapAdd(double d) {
        return copy().mapAddToSelf(d);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector mapAddToSelf(double d) {
        for (int i = 0; i < this.virtualSize; i++) {
            setEntry(i, getEntry(i) + d);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealMatrix outerProduct(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        org.apache.commons.math.linear.OpenMapRealMatrix openMapRealMatrix = new org.apache.commons.math.linear.OpenMapRealMatrix(this.virtualSize, this.virtualSize);
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            int key = it.key();
            double value = it.value();
            for (int i = 0; i < this.virtualSize; i++) {
                openMapRealMatrix.setEntry(key, i, dArr[i] * value);
            }
        }
        return openMapRealMatrix;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector projection(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(realVector.getDimension());
        return realVector.mapMultiply(dotProduct(realVector) / realVector.dotProduct(realVector));
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector projection(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        return (org.apache.commons.math.linear.OpenMapRealVector) projection(new org.apache.commons.math.linear.OpenMapRealVector(dArr));
    }

    @Override // org.apache.commons.math.linear.RealVector
    public void setEntry(int i, double d) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(i);
        if (!isDefaultValue(d)) {
            this.entries.put(i, d);
        } else if (this.entries.containsKey(i)) {
            this.entries.remove(i);
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public void setSubVector(int i, org.apache.commons.math.linear.RealVector realVector) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(i);
        checkIndex((realVector.getDimension() + i) - 1);
        setSubVector(i, realVector.getData());
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public void setSubVector(int i, double[] dArr) throws org.apache.commons.math.linear.MatrixIndexException {
        checkIndex(i);
        checkIndex((dArr.length + i) - 1);
        for (int i2 = 0; i2 < dArr.length; i2++) {
            setEntry(i2 + i, dArr[i2]);
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public void set(double d) {
        for (int i = 0; i < this.virtualSize; i++) {
            setEntry(i, d);
        }
    }

    public org.apache.commons.math.linear.OpenMapRealVector subtract(org.apache.commons.math.linear.OpenMapRealVector openMapRealVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(openMapRealVector.getDimension());
        org.apache.commons.math.linear.OpenMapRealVector copy = copy();
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = openMapRealVector.getEntries().iterator();
        while (it.hasNext()) {
            it.advance();
            int key = it.key();
            if (this.entries.containsKey(key)) {
                copy.setEntry(key, this.entries.get(key) - it.value());
            } else {
                copy.setEntry(key, -it.value());
            }
        }
        return copy;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector subtract(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(realVector.getDimension());
        if (realVector instanceof org.apache.commons.math.linear.OpenMapRealVector) {
            return subtract((org.apache.commons.math.linear.OpenMapRealVector) realVector);
        }
        return subtract(realVector.getData());
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector subtract(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        org.apache.commons.math.linear.OpenMapRealVector openMapRealVector = new org.apache.commons.math.linear.OpenMapRealVector(this);
        for (int i = 0; i < dArr.length; i++) {
            if (this.entries.containsKey(i)) {
                openMapRealVector.setEntry(i, this.entries.get(i) - dArr[i]);
            } else {
                openMapRealVector.setEntry(i, -dArr[i]);
            }
        }
        return openMapRealVector;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.OpenMapRealVector unitVector() {
        org.apache.commons.math.linear.OpenMapRealVector copy = copy();
        copy.unitize();
        return copy;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public void unitize() {
        double norm = getNorm();
        if (isDefaultValue(norm)) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_NORMALIZE_A_ZERO_NORM_VECTOR, new java.lang.Object[0]);
        }
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            this.entries.put(it.key(), it.value() / norm);
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double[] toArray() {
        return getData();
    }

    public int hashCode() {
        long doubleToLongBits = java.lang.Double.doubleToLongBits(this.epsilon);
        int i = ((((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) + 31) * 31) + this.virtualSize;
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            long doubleToLongBits2 = java.lang.Double.doubleToLongBits(it.value());
            i = (i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >> 32)));
        }
        return i;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.linear.OpenMapRealVector)) {
            return false;
        }
        org.apache.commons.math.linear.OpenMapRealVector openMapRealVector = (org.apache.commons.math.linear.OpenMapRealVector) obj;
        if (this.virtualSize != openMapRealVector.virtualSize || java.lang.Double.doubleToLongBits(this.epsilon) != java.lang.Double.doubleToLongBits(openMapRealVector.epsilon)) {
            return false;
        }
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it = this.entries.iterator();
        while (it.hasNext()) {
            it.advance();
            if (java.lang.Double.doubleToLongBits(openMapRealVector.getEntry(it.key())) != java.lang.Double.doubleToLongBits(it.value())) {
                return false;
            }
        }
        org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator it2 = openMapRealVector.getEntries().iterator();
        while (it2.hasNext()) {
            it2.advance();
            if (java.lang.Double.doubleToLongBits(it2.value()) != java.lang.Double.doubleToLongBits(getEntry(it2.key()))) {
                return false;
            }
        }
        return true;
    }

    @java.lang.Deprecated
    public double getSparcity() {
        return getSparsity();
    }

    public double getSparsity() {
        return this.entries.size() / getDimension();
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator() {
        return new org.apache.commons.math.linear.OpenMapRealVector.OpenMapSparseIterator();
    }

    protected class OpenMapEntry extends org.apache.commons.math.linear.RealVector.Entry {
        private final org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter;

        protected OpenMapEntry(org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iterator) {
            this.iter = iterator;
        }

        @Override // org.apache.commons.math.linear.RealVector.Entry
        public double getValue() {
            return this.iter.value();
        }

        @Override // org.apache.commons.math.linear.RealVector.Entry
        public void setValue(double d) {
            org.apache.commons.math.linear.OpenMapRealVector.this.entries.put(this.iter.key(), d);
        }

        @Override // org.apache.commons.math.linear.RealVector.Entry
        public int getIndex() {
            return this.iter.key();
        }
    }

    protected class OpenMapSparseIterator implements java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> {
        private final org.apache.commons.math.linear.RealVector.Entry current;
        private final org.apache.commons.math.util.OpenIntToDoubleHashMap.Iterator iter;

        protected OpenMapSparseIterator() {
            this.iter = org.apache.commons.math.linear.OpenMapRealVector.this.entries.iterator();
            this.current = org.apache.commons.math.linear.OpenMapRealVector.this.new OpenMapEntry(this.iter);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override // java.util.Iterator
        public org.apache.commons.math.linear.RealVector.Entry next() {
            this.iter.advance();
            return this.current;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new java.lang.UnsupportedOperationException("Not supported");
        }
    }
}
