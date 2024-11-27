package org.apache.commons.math.util;

/* loaded from: classes3.dex */
public class ResizableDoubleArray implements org.apache.commons.math.util.DoubleArray, java.io.Serializable {
    public static final int ADDITIVE_MODE = 1;
    public static final int MULTIPLICATIVE_MODE = 0;
    private static final long serialVersionUID = -3485529955529426875L;
    protected float contractionCriteria;
    protected float expansionFactor;
    protected int expansionMode;
    protected int initialCapacity;
    protected double[] internalArray;
    protected int numElements;
    protected int startIndex;

    public ResizableDoubleArray() {
        this.contractionCriteria = 2.5f;
        this.expansionFactor = 2.0f;
        this.expansionMode = 0;
        this.initialCapacity = 16;
        this.numElements = 0;
        this.startIndex = 0;
        this.internalArray = new double[this.initialCapacity];
    }

    public ResizableDoubleArray(int i) {
        this.contractionCriteria = 2.5f;
        this.expansionFactor = 2.0f;
        this.expansionMode = 0;
        this.initialCapacity = 16;
        this.numElements = 0;
        this.startIndex = 0;
        setInitialCapacity(i);
        this.internalArray = new double[this.initialCapacity];
    }

    public ResizableDoubleArray(double[] dArr) {
        this.contractionCriteria = 2.5f;
        this.expansionFactor = 2.0f;
        this.expansionMode = 0;
        this.initialCapacity = 16;
        this.numElements = 0;
        this.startIndex = 0;
        if (dArr == null) {
            this.internalArray = new double[this.initialCapacity];
            return;
        }
        this.internalArray = new double[dArr.length];
        java.lang.System.arraycopy(dArr, 0, this.internalArray, 0, dArr.length);
        this.initialCapacity = dArr.length;
        this.numElements = dArr.length;
    }

    public ResizableDoubleArray(int i, float f) {
        this.contractionCriteria = 2.5f;
        this.expansionFactor = 2.0f;
        this.expansionMode = 0;
        this.initialCapacity = 16;
        this.numElements = 0;
        this.startIndex = 0;
        this.expansionFactor = f;
        setInitialCapacity(i);
        this.internalArray = new double[i];
        setContractionCriteria(f + 0.5f);
    }

    public ResizableDoubleArray(int i, float f, float f2) {
        this.contractionCriteria = 2.5f;
        this.expansionFactor = 2.0f;
        this.expansionMode = 0;
        this.initialCapacity = 16;
        this.numElements = 0;
        this.startIndex = 0;
        this.expansionFactor = f;
        setContractionCriteria(f2);
        setInitialCapacity(i);
        this.internalArray = new double[i];
    }

    public ResizableDoubleArray(int i, float f, float f2, int i2) {
        this.contractionCriteria = 2.5f;
        this.expansionFactor = 2.0f;
        this.expansionMode = 0;
        this.initialCapacity = 16;
        this.numElements = 0;
        this.startIndex = 0;
        this.expansionFactor = f;
        setContractionCriteria(f2);
        setInitialCapacity(i);
        setExpansionMode(i2);
        this.internalArray = new double[i];
    }

    public ResizableDoubleArray(org.apache.commons.math.util.ResizableDoubleArray resizableDoubleArray) {
        this.contractionCriteria = 2.5f;
        this.expansionFactor = 2.0f;
        this.expansionMode = 0;
        this.initialCapacity = 16;
        this.numElements = 0;
        this.startIndex = 0;
        copy(resizableDoubleArray, this);
    }

    @Override // org.apache.commons.math.util.DoubleArray
    public synchronized void addElement(double d) {
        try {
            this.numElements++;
            if (this.startIndex + this.numElements > this.internalArray.length) {
                expand();
            }
            this.internalArray[this.startIndex + (this.numElements - 1)] = d;
            if (shouldContract()) {
                contract();
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public synchronized void addElements(double[] dArr) {
        double[] dArr2 = new double[this.numElements + dArr.length + 1];
        java.lang.System.arraycopy(this.internalArray, this.startIndex, dArr2, 0, this.numElements);
        java.lang.System.arraycopy(dArr, 0, dArr2, this.numElements, dArr.length);
        this.internalArray = dArr2;
        this.startIndex = 0;
        this.numElements += dArr.length;
    }

    @Override // org.apache.commons.math.util.DoubleArray
    public synchronized double addElementRolling(double d) {
        double d2;
        try {
            d2 = this.internalArray[this.startIndex];
            if (this.startIndex + this.numElements + 1 > this.internalArray.length) {
                expand();
            }
            this.startIndex++;
            this.internalArray[this.startIndex + (this.numElements - 1)] = d;
            if (shouldContract()) {
                contract();
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
        return d2;
    }

    public synchronized double substituteMostRecentElement(double d) {
        double d2;
        if (this.numElements < 1) {
            throw org.apache.commons.math.MathRuntimeException.createArrayIndexOutOfBoundsException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_SUBSTITUTE_ELEMENT_FROM_EMPTY_ARRAY, new java.lang.Object[0]);
        }
        d2 = this.internalArray[this.startIndex + (this.numElements - 1)];
        this.internalArray[this.startIndex + (this.numElements - 1)] = d;
        return d2;
    }

    protected void checkContractExpand(float f, float f2) {
        if (f < f2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.CONTRACTION_CRITERIA_SMALLER_THAN_EXPANSION_FACTOR, java.lang.Float.valueOf(f), java.lang.Float.valueOf(f2));
        }
        if (f <= 1.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.CONTRACTION_CRITERIA_SMALLER_THAN_ONE, java.lang.Float.valueOf(f));
        }
        if (f2 <= 1.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.EXPANSION_FACTOR_SMALLER_THAN_ONE, java.lang.Float.valueOf(f2));
        }
    }

    @Override // org.apache.commons.math.util.DoubleArray
    public synchronized void clear() {
        this.numElements = 0;
        this.startIndex = 0;
        this.internalArray = new double[this.initialCapacity];
    }

    public synchronized void contract() {
        double[] dArr = new double[this.numElements + 1];
        java.lang.System.arraycopy(this.internalArray, this.startIndex, dArr, 0, this.numElements);
        this.internalArray = dArr;
        this.startIndex = 0;
    }

    public synchronized void discardFrontElements(int i) {
        discardExtremeElements(i, true);
    }

    public synchronized void discardMostRecentElements(int i) {
        discardExtremeElements(i, false);
    }

    private synchronized void discardExtremeElements(int i, boolean z) {
        try {
            if (i > this.numElements) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.TOO_MANY_ELEMENTS_TO_DISCARD_FROM_ARRAY, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(this.numElements));
            }
            if (i < 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_DISCARD_NEGATIVE_NUMBER_OF_ELEMENTS, java.lang.Integer.valueOf(i));
            }
            this.numElements -= i;
            if (z) {
                this.startIndex += i;
            }
            if (shouldContract()) {
                contract();
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    protected synchronized void expand() {
        int length;
        try {
            if (this.expansionMode == 0) {
                length = (int) org.apache.commons.math.util.FastMath.ceil(this.internalArray.length * this.expansionFactor);
            } else {
                length = this.internalArray.length + org.apache.commons.math.util.FastMath.round(this.expansionFactor);
            }
            double[] dArr = new double[length];
            java.lang.System.arraycopy(this.internalArray, 0, dArr, 0, this.internalArray.length);
            this.internalArray = dArr;
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private synchronized void expandTo(int i) {
        double[] dArr = new double[i];
        java.lang.System.arraycopy(this.internalArray, 0, dArr, 0, this.internalArray.length);
        this.internalArray = dArr;
    }

    public float getContractionCriteria() {
        return this.contractionCriteria;
    }

    @Override // org.apache.commons.math.util.DoubleArray
    public synchronized double getElement(int i) {
        if (i >= this.numElements) {
            throw org.apache.commons.math.MathRuntimeException.createArrayIndexOutOfBoundsException(org.apache.commons.math.exception.util.LocalizedFormats.INDEX_LARGER_THAN_MAX, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(this.numElements - 1));
        }
        if (i >= 0) {
        } else {
            throw org.apache.commons.math.MathRuntimeException.createArrayIndexOutOfBoundsException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_RETRIEVE_AT_NEGATIVE_INDEX, java.lang.Integer.valueOf(i));
        }
        return this.internalArray[this.startIndex + i];
    }

    @Override // org.apache.commons.math.util.DoubleArray
    public synchronized double[] getElements() {
        double[] dArr;
        dArr = new double[this.numElements];
        java.lang.System.arraycopy(this.internalArray, this.startIndex, dArr, 0, this.numElements);
        return dArr;
    }

    public float getExpansionFactor() {
        return this.expansionFactor;
    }

    public int getExpansionMode() {
        return this.expansionMode;
    }

    synchronized int getInternalLength() {
        return this.internalArray.length;
    }

    @Override // org.apache.commons.math.util.DoubleArray
    public synchronized int getNumElements() {
        return this.numElements;
    }

    @java.lang.Deprecated
    public synchronized double[] getValues() {
        return this.internalArray;
    }

    public synchronized double[] getInternalValues() {
        return this.internalArray;
    }

    public void setContractionCriteria(float f) {
        checkContractExpand(f, getExpansionFactor());
        synchronized (this) {
            this.contractionCriteria = f;
        }
    }

    @Override // org.apache.commons.math.util.DoubleArray
    public synchronized void setElement(int i, double d) {
        try {
            if (i < 0) {
                throw org.apache.commons.math.MathRuntimeException.createArrayIndexOutOfBoundsException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_SET_AT_NEGATIVE_INDEX, java.lang.Integer.valueOf(i));
            }
            int i2 = i + 1;
            if (i2 > this.numElements) {
                this.numElements = i2;
            }
            if (this.startIndex + i >= this.internalArray.length) {
                expandTo(this.startIndex + i2);
            }
            this.internalArray[this.startIndex + i] = d;
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    public void setExpansionFactor(float f) {
        checkContractExpand(getContractionCriteria(), f);
        synchronized (this) {
            this.expansionFactor = f;
        }
    }

    public void setExpansionMode(int i) {
        if (i != 0 && i != 1) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.UNSUPPORTED_EXPANSION_MODE, java.lang.Integer.valueOf(i), 0, "MULTIPLICATIVE_MODE", 1, "ADDITIVE_MODE");
        }
        synchronized (this) {
            this.expansionMode = i;
        }
    }

    protected void setInitialCapacity(int i) {
        if (i > 0) {
            synchronized (this) {
                this.initialCapacity = i;
            }
            return;
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INITIAL_CAPACITY_NOT_POSITIVE, java.lang.Integer.valueOf(i));
    }

    public synchronized void setNumElements(int i) {
        try {
            if (i < 0) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INDEX_NOT_POSITIVE, java.lang.Integer.valueOf(i));
            }
            if (this.startIndex + i > this.internalArray.length) {
                expandTo(this.startIndex + i);
            }
            this.numElements = i;
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    private synchronized boolean shouldContract() {
        if (this.expansionMode == 0) {
            return ((float) this.internalArray.length) / ((float) this.numElements) > this.contractionCriteria;
        }
        return ((float) (this.internalArray.length - this.numElements)) > this.contractionCriteria;
    }

    public synchronized int start() {
        return this.startIndex;
    }

    public static void copy(org.apache.commons.math.util.ResizableDoubleArray resizableDoubleArray, org.apache.commons.math.util.ResizableDoubleArray resizableDoubleArray2) {
        synchronized (resizableDoubleArray) {
            synchronized (resizableDoubleArray2) {
                resizableDoubleArray2.initialCapacity = resizableDoubleArray.initialCapacity;
                resizableDoubleArray2.contractionCriteria = resizableDoubleArray.contractionCriteria;
                resizableDoubleArray2.expansionFactor = resizableDoubleArray.expansionFactor;
                resizableDoubleArray2.expansionMode = resizableDoubleArray.expansionMode;
                resizableDoubleArray2.internalArray = new double[resizableDoubleArray.internalArray.length];
                java.lang.System.arraycopy(resizableDoubleArray.internalArray, 0, resizableDoubleArray2.internalArray, 0, resizableDoubleArray2.internalArray.length);
                resizableDoubleArray2.numElements = resizableDoubleArray.numElements;
                resizableDoubleArray2.startIndex = resizableDoubleArray.startIndex;
            }
        }
    }

    public synchronized org.apache.commons.math.util.ResizableDoubleArray copy() {
        org.apache.commons.math.util.ResizableDoubleArray resizableDoubleArray;
        resizableDoubleArray = new org.apache.commons.math.util.ResizableDoubleArray();
        copy(this, resizableDoubleArray);
        return resizableDoubleArray;
    }

    public boolean equals(java.lang.Object obj) {
        boolean z;
        boolean z2 = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.util.ResizableDoubleArray)) {
            return false;
        }
        synchronized (this) {
            synchronized (obj) {
                try {
                    org.apache.commons.math.util.ResizableDoubleArray resizableDoubleArray = (org.apache.commons.math.util.ResizableDoubleArray) obj;
                    if ((resizableDoubleArray.initialCapacity == this.initialCapacity) && resizableDoubleArray.contractionCriteria == this.contractionCriteria) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!(((z && (resizableDoubleArray.expansionFactor > this.expansionFactor ? 1 : (resizableDoubleArray.expansionFactor == this.expansionFactor ? 0 : -1)) == 0) && resizableDoubleArray.expansionMode == this.expansionMode) && resizableDoubleArray.numElements == this.numElements) || resizableDoubleArray.startIndex != this.startIndex) {
                        z2 = false;
                    }
                    if (z2) {
                        return java.util.Arrays.equals(this.internalArray, resizableDoubleArray.internalArray);
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    public synchronized int hashCode() {
        return java.util.Arrays.hashCode(new int[]{new java.lang.Float(this.expansionFactor).hashCode(), new java.lang.Float(this.contractionCriteria).hashCode(), this.expansionMode, java.util.Arrays.hashCode(this.internalArray), this.initialCapacity, this.numElements, this.startIndex});
    }
}
