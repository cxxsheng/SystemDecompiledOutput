package org.apache.commons.math.stat.descriptive;

/* loaded from: classes3.dex */
public abstract class AbstractUnivariateStatistic implements org.apache.commons.math.stat.descriptive.UnivariateStatistic {
    private double[] storedData;

    @Override // org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public abstract org.apache.commons.math.stat.descriptive.UnivariateStatistic copy();

    @Override // org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public abstract double evaluate(double[] dArr, int i, int i2);

    public void setData(double[] dArr) {
        this.storedData = dArr == null ? null : (double[]) dArr.clone();
    }

    public double[] getData() {
        if (this.storedData == null) {
            return null;
        }
        return (double[]) this.storedData.clone();
    }

    protected double[] getDataRef() {
        return this.storedData;
    }

    public void setData(double[] dArr, int i, int i2) {
        this.storedData = new double[i2];
        java.lang.System.arraycopy(dArr, i, this.storedData, 0, i2);
    }

    public double evaluate() {
        return evaluate(this.storedData);
    }

    @Override // org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr) {
        test(dArr, 0, 0);
        return evaluate(dArr, 0, dArr.length);
    }

    protected boolean test(double[] dArr, int i, int i2) {
        if (dArr == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INPUT_ARRAY);
        }
        if (i < 0) {
            throw new org.apache.commons.math.exception.NotPositiveException(org.apache.commons.math.exception.util.LocalizedFormats.START_POSITION, java.lang.Integer.valueOf(i));
        }
        if (i2 < 0) {
            throw new org.apache.commons.math.exception.NotPositiveException(org.apache.commons.math.exception.util.LocalizedFormats.LENGTH, java.lang.Integer.valueOf(i2));
        }
        if (i + i2 <= dArr.length) {
            return i2 != 0;
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.SUBARRAY_ENDS_AFTER_ARRAY_END, new java.lang.Object[0]);
    }

    protected boolean test(double[] dArr, double[] dArr2, int i, int i2) {
        if (dArr2 == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INPUT_ARRAY);
        }
        if (dArr2.length != dArr.length) {
            throw new org.apache.commons.math.exception.DimensionMismatchException(dArr2.length, dArr.length);
        }
        boolean z = false;
        for (int i3 = i; i3 < i + i2; i3++) {
            if (java.lang.Double.isNaN(dArr2[i3])) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NAN_ELEMENT_AT_INDEX, java.lang.Integer.valueOf(i3));
            }
            if (java.lang.Double.isInfinite(dArr2[i3])) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INFINITE_ARRAY_ELEMENT, java.lang.Double.valueOf(dArr2[i3]), java.lang.Integer.valueOf(i3));
            }
            if (dArr2[i3] < 0.0d) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NEGATIVE_ELEMENT_AT_INDEX, java.lang.Integer.valueOf(i3), java.lang.Double.valueOf(dArr2[i3]));
            }
            if (!z && dArr2[i3] > 0.0d) {
                z = true;
            }
        }
        if (!z) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.WEIGHT_AT_LEAST_ONE_NON_ZERO, new java.lang.Object[0]);
        }
        return test(dArr, i, i2);
    }
}
