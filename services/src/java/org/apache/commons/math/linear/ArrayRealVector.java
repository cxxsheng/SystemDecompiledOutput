package org.apache.commons.math.linear;

/* loaded from: classes3.dex */
public class ArrayRealVector extends org.apache.commons.math.linear.AbstractRealVector implements java.io.Serializable {
    private static final org.apache.commons.math.linear.RealVectorFormat DEFAULT_FORMAT = org.apache.commons.math.linear.RealVectorFormat.getInstance();
    private static final long serialVersionUID = -1097961340710804027L;
    protected double[] data;

    public ArrayRealVector() {
        this.data = new double[0];
    }

    public ArrayRealVector(int i) {
        this.data = new double[i];
    }

    public ArrayRealVector(int i, double d) {
        this.data = new double[i];
        java.util.Arrays.fill(this.data, d);
    }

    public ArrayRealVector(double[] dArr) {
        this.data = (double[]) dArr.clone();
    }

    public ArrayRealVector(double[] dArr, boolean z) {
        this.data = z ? (double[]) dArr.clone() : dArr;
    }

    public ArrayRealVector(double[] dArr, int i, int i2) {
        if (dArr.length < i + i2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.POSITION_SIZE_MISMATCH_INPUT_ARRAY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(dArr.length));
        }
        this.data = new double[i2];
        java.lang.System.arraycopy(dArr, i, this.data, 0, i2);
    }

    public ArrayRealVector(java.lang.Double[] dArr) {
        this.data = new double[dArr.length];
        for (int i = 0; i < dArr.length; i++) {
            this.data[i] = dArr[i].doubleValue();
        }
    }

    public ArrayRealVector(java.lang.Double[] dArr, int i, int i2) {
        int i3 = i + i2;
        if (dArr.length < i3) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.POSITION_SIZE_MISMATCH_INPUT_ARRAY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(dArr.length));
        }
        this.data = new double[i2];
        for (int i4 = i; i4 < i3; i4++) {
            this.data[i4 - i] = dArr[i4].doubleValue();
        }
    }

    public ArrayRealVector(org.apache.commons.math.linear.RealVector realVector) {
        this.data = new double[realVector.getDimension()];
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = realVector.getEntry(i);
        }
    }

    public ArrayRealVector(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) {
        this(arrayRealVector, true);
    }

    public ArrayRealVector(org.apache.commons.math.linear.ArrayRealVector arrayRealVector, boolean z) {
        double[] dArr = arrayRealVector.data;
        this.data = z ? (double[]) dArr.clone() : dArr;
    }

    public ArrayRealVector(org.apache.commons.math.linear.ArrayRealVector arrayRealVector, org.apache.commons.math.linear.ArrayRealVector arrayRealVector2) {
        this.data = new double[arrayRealVector.data.length + arrayRealVector2.data.length];
        java.lang.System.arraycopy(arrayRealVector.data, 0, this.data, 0, arrayRealVector.data.length);
        java.lang.System.arraycopy(arrayRealVector2.data, 0, this.data, arrayRealVector.data.length, arrayRealVector2.data.length);
    }

    public ArrayRealVector(org.apache.commons.math.linear.ArrayRealVector arrayRealVector, org.apache.commons.math.linear.RealVector realVector) {
        int length = arrayRealVector.data.length;
        int dimension = realVector.getDimension();
        this.data = new double[length + dimension];
        java.lang.System.arraycopy(arrayRealVector.data, 0, this.data, 0, length);
        for (int i = 0; i < dimension; i++) {
            this.data[length + i] = realVector.getEntry(i);
        }
    }

    public ArrayRealVector(org.apache.commons.math.linear.RealVector realVector, org.apache.commons.math.linear.ArrayRealVector arrayRealVector) {
        int dimension = realVector.getDimension();
        int length = arrayRealVector.data.length;
        this.data = new double[dimension + length];
        for (int i = 0; i < dimension; i++) {
            this.data[i] = realVector.getEntry(i);
        }
        java.lang.System.arraycopy(arrayRealVector.data, 0, this.data, dimension, length);
    }

    public ArrayRealVector(org.apache.commons.math.linear.ArrayRealVector arrayRealVector, double[] dArr) {
        int dimension = arrayRealVector.getDimension();
        int length = dArr.length;
        this.data = new double[dimension + length];
        java.lang.System.arraycopy(arrayRealVector.data, 0, this.data, 0, dimension);
        java.lang.System.arraycopy(dArr, 0, this.data, dimension, length);
    }

    public ArrayRealVector(double[] dArr, org.apache.commons.math.linear.ArrayRealVector arrayRealVector) {
        int length = dArr.length;
        int dimension = arrayRealVector.getDimension();
        this.data = new double[length + dimension];
        java.lang.System.arraycopy(dArr, 0, this.data, 0, length);
        java.lang.System.arraycopy(arrayRealVector.data, 0, this.data, length, dimension);
    }

    public ArrayRealVector(double[] dArr, double[] dArr2) {
        int length = dArr.length;
        int length2 = dArr2.length;
        this.data = new double[length + length2];
        java.lang.System.arraycopy(dArr, 0, this.data, 0, length);
        java.lang.System.arraycopy(dArr2, 0, this.data, length, length2);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.AbstractRealVector copy() {
        return new org.apache.commons.math.linear.ArrayRealVector(this, true);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector add(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.RealVector.Entry next;
        if (realVector instanceof org.apache.commons.math.linear.ArrayRealVector) {
            return add((org.apache.commons.math.linear.ArrayRealVector) realVector);
        }
        checkVectorDimensions(realVector);
        double[] dArr = (double[]) this.data.clone();
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator = realVector.sparseIterator();
        while (sparseIterator.hasNext() && (next = sparseIterator.next()) != null) {
            int index = next.getIndex();
            dArr[index] = dArr[index] + next.getValue();
        }
        return new org.apache.commons.math.linear.ArrayRealVector(dArr, false);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector add(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        double[] dArr2 = (double[]) this.data.clone();
        for (int i = 0; i < this.data.length; i++) {
            dArr2[i] = dArr2[i] + dArr[i];
        }
        return new org.apache.commons.math.linear.ArrayRealVector(dArr2, false);
    }

    public org.apache.commons.math.linear.ArrayRealVector add(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) throws java.lang.IllegalArgumentException {
        return (org.apache.commons.math.linear.ArrayRealVector) add(arrayRealVector.data);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector subtract(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.RealVector.Entry next;
        if (realVector instanceof org.apache.commons.math.linear.ArrayRealVector) {
            return subtract((org.apache.commons.math.linear.ArrayRealVector) realVector);
        }
        checkVectorDimensions(realVector);
        double[] dArr = (double[]) this.data.clone();
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator = realVector.sparseIterator();
        while (sparseIterator.hasNext() && (next = sparseIterator.next()) != null) {
            int index = next.getIndex();
            dArr[index] = dArr[index] - next.getValue();
        }
        return new org.apache.commons.math.linear.ArrayRealVector(dArr, false);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector subtract(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        double[] dArr2 = (double[]) this.data.clone();
        for (int i = 0; i < this.data.length; i++) {
            dArr2[i] = dArr2[i] - dArr[i];
        }
        return new org.apache.commons.math.linear.ArrayRealVector(dArr2, false);
    }

    public org.apache.commons.math.linear.ArrayRealVector subtract(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) throws java.lang.IllegalArgumentException {
        return (org.apache.commons.math.linear.ArrayRealVector) subtract(arrayRealVector.data);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAddToSelf(double d) {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = this.data[i] + d;
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSubtractToSelf(double d) {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = this.data[i] - d;
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapMultiplyToSelf(double d) {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = this.data[i] * d;
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapDivideToSelf(double d) {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = this.data[i] / d;
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapPowToSelf(double d) {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.pow(this.data[i], d);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapExpToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.exp(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapExpm1ToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.expm1(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapLogToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.log(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapLog10ToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.log10(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapLog1pToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.log1p(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapCoshToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.cosh(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSinhToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.sinh(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapTanhToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.tanh(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapCosToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.cos(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSinToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.sin(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapTanToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.tan(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAcosToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.acos(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAsinToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.asin(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAtanToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.atan(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapInvToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = 1.0d / this.data[i];
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapAbsToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.abs(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSqrtToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.sqrt(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapCbrtToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.cbrt(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapCeilToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.ceil(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapFloorToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.floor(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapRintToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.rint(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapSignumToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.signum(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector mapUlpToSelf() {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = org.apache.commons.math.util.FastMath.ulp(this.data[i]);
        }
        return this;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector ebeMultiply(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        if (realVector instanceof org.apache.commons.math.linear.ArrayRealVector) {
            return ebeMultiply((org.apache.commons.math.linear.ArrayRealVector) realVector);
        }
        checkVectorDimensions(realVector);
        double[] dArr = (double[]) this.data.clone();
        for (int i = 0; i < this.data.length; i++) {
            dArr[i] = dArr[i] * realVector.getEntry(i);
        }
        return new org.apache.commons.math.linear.ArrayRealVector(dArr, false);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector ebeMultiply(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        double[] dArr2 = (double[]) this.data.clone();
        for (int i = 0; i < this.data.length; i++) {
            dArr2[i] = dArr2[i] * dArr[i];
        }
        return new org.apache.commons.math.linear.ArrayRealVector(dArr2, false);
    }

    public org.apache.commons.math.linear.ArrayRealVector ebeMultiply(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) throws java.lang.IllegalArgumentException {
        return (org.apache.commons.math.linear.ArrayRealVector) ebeMultiply(arrayRealVector.data);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector ebeDivide(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        if (realVector instanceof org.apache.commons.math.linear.ArrayRealVector) {
            return ebeDivide((org.apache.commons.math.linear.ArrayRealVector) realVector);
        }
        checkVectorDimensions(realVector);
        double[] dArr = (double[]) this.data.clone();
        for (int i = 0; i < this.data.length; i++) {
            dArr[i] = dArr[i] / realVector.getEntry(i);
        }
        return new org.apache.commons.math.linear.ArrayRealVector(dArr, false);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector ebeDivide(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        double[] dArr2 = (double[]) this.data.clone();
        for (int i = 0; i < this.data.length; i++) {
            dArr2[i] = dArr2[i] / dArr[i];
        }
        return new org.apache.commons.math.linear.ArrayRealVector(dArr2, false);
    }

    public org.apache.commons.math.linear.ArrayRealVector ebeDivide(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) throws java.lang.IllegalArgumentException {
        return (org.apache.commons.math.linear.ArrayRealVector) ebeDivide(arrayRealVector.data);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double[] getData() {
        return (double[]) this.data.clone();
    }

    public double[] getDataRef() {
        return this.data;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double dotProduct(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        org.apache.commons.math.linear.RealVector.Entry next;
        if (realVector instanceof org.apache.commons.math.linear.ArrayRealVector) {
            return dotProduct((org.apache.commons.math.linear.ArrayRealVector) realVector);
        }
        checkVectorDimensions(realVector);
        java.util.Iterator<org.apache.commons.math.linear.RealVector.Entry> sparseIterator = realVector.sparseIterator();
        double d = 0.0d;
        while (sparseIterator.hasNext() && (next = sparseIterator.next()) != null) {
            d += this.data[next.getIndex()] * next.getValue();
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double dotProduct(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        double d = 0.0d;
        for (int i = 0; i < this.data.length; i++) {
            d += this.data[i] * dArr[i];
        }
        return d;
    }

    public double dotProduct(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) throws java.lang.IllegalArgumentException {
        return dotProduct(arrayRealVector.data);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getNorm() {
        double d = 0.0d;
        for (double d2 : this.data) {
            d += d2 * d2;
        }
        return org.apache.commons.math.util.FastMath.sqrt(d);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getL1Norm() {
        double d = 0.0d;
        for (double d2 : this.data) {
            d += org.apache.commons.math.util.FastMath.abs(d2);
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getLInfNorm() {
        double d = 0.0d;
        for (double d2 : this.data) {
            d = org.apache.commons.math.util.FastMath.max(d, org.apache.commons.math.util.FastMath.abs(d2));
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getDistance(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        if (realVector instanceof org.apache.commons.math.linear.ArrayRealVector) {
            return getDistance((org.apache.commons.math.linear.ArrayRealVector) realVector);
        }
        checkVectorDimensions(realVector);
        double d = 0.0d;
        for (int i = 0; i < this.data.length; i++) {
            double entry = this.data[i] - realVector.getEntry(i);
            d += entry * entry;
        }
        return org.apache.commons.math.util.FastMath.sqrt(d);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getDistance(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        double d = 0.0d;
        for (int i = 0; i < this.data.length; i++) {
            double d2 = this.data[i] - dArr[i];
            d += d2 * d2;
        }
        return org.apache.commons.math.util.FastMath.sqrt(d);
    }

    public double getDistance(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) throws java.lang.IllegalArgumentException {
        return getDistance(arrayRealVector.data);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getL1Distance(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        if (realVector instanceof org.apache.commons.math.linear.ArrayRealVector) {
            return getL1Distance((org.apache.commons.math.linear.ArrayRealVector) realVector);
        }
        checkVectorDimensions(realVector);
        double d = 0.0d;
        for (int i = 0; i < this.data.length; i++) {
            d += org.apache.commons.math.util.FastMath.abs(this.data[i] - realVector.getEntry(i));
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getL1Distance(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        double d = 0.0d;
        for (int i = 0; i < this.data.length; i++) {
            d += org.apache.commons.math.util.FastMath.abs(this.data[i] - dArr[i]);
        }
        return d;
    }

    public double getL1Distance(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) throws java.lang.IllegalArgumentException {
        return getL1Distance(arrayRealVector.data);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getLInfDistance(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        if (realVector instanceof org.apache.commons.math.linear.ArrayRealVector) {
            return getLInfDistance((org.apache.commons.math.linear.ArrayRealVector) realVector);
        }
        checkVectorDimensions(realVector);
        double d = 0.0d;
        for (int i = 0; i < this.data.length; i++) {
            d = org.apache.commons.math.util.FastMath.max(d, org.apache.commons.math.util.FastMath.abs(this.data[i] - realVector.getEntry(i)));
        }
        return d;
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double getLInfDistance(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        double d = 0.0d;
        for (int i = 0; i < this.data.length; i++) {
            d = org.apache.commons.math.util.FastMath.max(d, org.apache.commons.math.util.FastMath.abs(this.data[i] - dArr[i]));
        }
        return d;
    }

    public double getLInfDistance(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) throws java.lang.IllegalArgumentException {
        return getLInfDistance(arrayRealVector.data);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector unitVector() throws java.lang.ArithmeticException {
        double norm = getNorm();
        if (norm == 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.ZERO_NORM, new java.lang.Object[0]);
        }
        return mapDivide(norm);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public void unitize() throws java.lang.ArithmeticException {
        double norm = getNorm();
        if (norm == 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_NORMALIZE_A_ZERO_NORM_VECTOR, new java.lang.Object[0]);
        }
        mapDivideToSelf(norm);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector projection(org.apache.commons.math.linear.RealVector realVector) {
        return realVector.mapMultiply(dotProduct(realVector) / realVector.dotProduct(realVector));
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector projection(double[] dArr) {
        return projection(new org.apache.commons.math.linear.ArrayRealVector(dArr, false));
    }

    public org.apache.commons.math.linear.ArrayRealVector projection(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) {
        return (org.apache.commons.math.linear.ArrayRealVector) arrayRealVector.mapMultiply(dotProduct(arrayRealVector) / arrayRealVector.dotProduct(arrayRealVector));
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealMatrix outerProduct(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        if (realVector instanceof org.apache.commons.math.linear.ArrayRealVector) {
            return outerProduct((org.apache.commons.math.linear.ArrayRealVector) realVector);
        }
        checkVectorDimensions(realVector);
        int length = this.data.length;
        org.apache.commons.math.linear.RealMatrix createRealMatrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length, length);
        for (int i = 0; i < this.data.length; i++) {
            for (int i2 = 0; i2 < this.data.length; i2++) {
                createRealMatrix.setEntry(i, i2, this.data[i] * realVector.getEntry(i2));
            }
        }
        return createRealMatrix;
    }

    public org.apache.commons.math.linear.RealMatrix outerProduct(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) throws java.lang.IllegalArgumentException {
        return outerProduct(arrayRealVector.data);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealMatrix outerProduct(double[] dArr) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(dArr.length);
        int length = this.data.length;
        org.apache.commons.math.linear.RealMatrix createRealMatrix = org.apache.commons.math.linear.MatrixUtils.createRealMatrix(length, length);
        for (int i = 0; i < this.data.length; i++) {
            for (int i2 = 0; i2 < this.data.length; i2++) {
                createRealMatrix.setEntry(i, i2, this.data[i] * dArr[i2]);
            }
        }
        return createRealMatrix;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public double getEntry(int i) throws org.apache.commons.math.linear.MatrixIndexException {
        return this.data[i];
    }

    @Override // org.apache.commons.math.linear.RealVector
    public int getDimension() {
        return this.data.length;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector append(org.apache.commons.math.linear.RealVector realVector) {
        try {
            return new org.apache.commons.math.linear.ArrayRealVector(this, (org.apache.commons.math.linear.ArrayRealVector) realVector);
        } catch (java.lang.ClassCastException e) {
            return new org.apache.commons.math.linear.ArrayRealVector(this, realVector);
        }
    }

    public org.apache.commons.math.linear.ArrayRealVector append(org.apache.commons.math.linear.ArrayRealVector arrayRealVector) {
        return new org.apache.commons.math.linear.ArrayRealVector(this, arrayRealVector);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector append(double d) {
        double[] dArr = new double[this.data.length + 1];
        java.lang.System.arraycopy(this.data, 0, dArr, 0, this.data.length);
        dArr[this.data.length] = d;
        return new org.apache.commons.math.linear.ArrayRealVector(dArr, false);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector append(double[] dArr) {
        return new org.apache.commons.math.linear.ArrayRealVector(this, dArr);
    }

    @Override // org.apache.commons.math.linear.RealVector
    public org.apache.commons.math.linear.RealVector getSubVector(int i, int i2) {
        org.apache.commons.math.linear.ArrayRealVector arrayRealVector = new org.apache.commons.math.linear.ArrayRealVector(i2);
        try {
            java.lang.System.arraycopy(this.data, i, arrayRealVector.data, 0, i2);
        } catch (java.lang.IndexOutOfBoundsException e) {
            checkIndex(i);
            checkIndex((i + i2) - 1);
        }
        return arrayRealVector;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public void setEntry(int i, double d) {
        try {
            this.data[i] = d;
        } catch (java.lang.IndexOutOfBoundsException e) {
            checkIndex(i);
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public void setSubVector(int i, org.apache.commons.math.linear.RealVector realVector) {
        try {
            try {
                set(i, (org.apache.commons.math.linear.ArrayRealVector) realVector);
            } catch (java.lang.ClassCastException e) {
                for (int i2 = i; i2 < realVector.getDimension() + i; i2++) {
                    this.data[i2] = realVector.getEntry(i2 - i);
                }
            }
        } catch (java.lang.IndexOutOfBoundsException e2) {
            checkIndex(i);
            checkIndex((i + realVector.getDimension()) - 1);
        }
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public void setSubVector(int i, double[] dArr) {
        try {
            java.lang.System.arraycopy(dArr, 0, this.data, i, dArr.length);
        } catch (java.lang.IndexOutOfBoundsException e) {
            checkIndex(i);
            checkIndex((i + dArr.length) - 1);
        }
    }

    public void set(int i, org.apache.commons.math.linear.ArrayRealVector arrayRealVector) throws org.apache.commons.math.linear.MatrixIndexException {
        setSubVector(i, arrayRealVector.data);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public void set(double d) {
        java.util.Arrays.fill(this.data, d);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector, org.apache.commons.math.linear.RealVector
    public double[] toArray() {
        return (double[]) this.data.clone();
    }

    public java.lang.String toString() {
        return DEFAULT_FORMAT.format(this);
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector
    protected void checkVectorDimensions(org.apache.commons.math.linear.RealVector realVector) throws java.lang.IllegalArgumentException {
        checkVectorDimensions(realVector.getDimension());
    }

    @Override // org.apache.commons.math.linear.AbstractRealVector
    protected void checkVectorDimensions(int i) throws java.lang.IllegalArgumentException {
        if (this.data.length != i) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.VECTOR_LENGTH_MISMATCH, java.lang.Integer.valueOf(this.data.length), java.lang.Integer.valueOf(i));
        }
    }

    @Override // org.apache.commons.math.linear.RealVector
    public boolean isNaN() {
        for (double d : this.data) {
            if (java.lang.Double.isNaN(d)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.apache.commons.math.linear.RealVector
    public boolean isInfinite() {
        if (isNaN()) {
            return false;
        }
        for (double d : this.data) {
            if (java.lang.Double.isInfinite(d)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof org.apache.commons.math.linear.RealVector)) {
            return false;
        }
        org.apache.commons.math.linear.RealVector realVector = (org.apache.commons.math.linear.RealVector) obj;
        if (this.data.length != realVector.getDimension()) {
            return false;
        }
        if (realVector.isNaN()) {
            return isNaN();
        }
        for (int i = 0; i < this.data.length; i++) {
            if (this.data[i] != realVector.getEntry(i)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        if (isNaN()) {
            return 9;
        }
        return org.apache.commons.math.util.MathUtils.hash(this.data);
    }
}
