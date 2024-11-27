package org.apache.commons.math.stat.descriptive;

/* loaded from: classes3.dex */
public abstract class AbstractStorelessUnivariateStatistic extends org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic implements org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic {
    @Override // org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public abstract void clear();

    @Override // org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public abstract org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic copy();

    @Override // org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public abstract double getResult();

    @Override // org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public abstract void increment(double d);

    @Override // org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr) {
        if (dArr == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INPUT_ARRAY);
        }
        return evaluate(dArr, 0, dArr.length);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr, int i, int i2) {
        if (test(dArr, i, i2)) {
            clear();
            incrementAll(dArr, i, i2);
        }
        return getResult();
    }

    @Override // org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void incrementAll(double[] dArr) {
        if (dArr == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INPUT_ARRAY);
        }
        incrementAll(dArr, 0, dArr.length);
    }

    @Override // org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void incrementAll(double[] dArr, int i, int i2) {
        if (test(dArr, i, i2)) {
            int i3 = i2 + i;
            while (i < i3) {
                increment(dArr[i]);
                i++;
            }
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic)) {
            return false;
        }
        org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic abstractStorelessUnivariateStatistic = (org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic) obj;
        return org.apache.commons.math.util.MathUtils.equalsIncludingNaN(abstractStorelessUnivariateStatistic.getResult(), getResult()) && org.apache.commons.math.util.MathUtils.equalsIncludingNaN((float) abstractStorelessUnivariateStatistic.getN(), (float) getN());
    }

    public int hashCode() {
        return ((org.apache.commons.math.util.MathUtils.hash(getResult()) + 31) * 31) + org.apache.commons.math.util.MathUtils.hash(getN());
    }
}
