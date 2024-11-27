package org.apache.commons.math.stat.descriptive.moment;

/* loaded from: classes3.dex */
public class Kurtosis extends org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic implements java.io.Serializable {
    private static final long serialVersionUID = 2784465764798260919L;
    protected boolean incMoment;
    protected org.apache.commons.math.stat.descriptive.moment.FourthMoment moment;

    public Kurtosis() {
        this.incMoment = true;
        this.moment = new org.apache.commons.math.stat.descriptive.moment.FourthMoment();
    }

    public Kurtosis(org.apache.commons.math.stat.descriptive.moment.FourthMoment fourthMoment) {
        this.incMoment = false;
        this.moment = fourthMoment;
    }

    public Kurtosis(org.apache.commons.math.stat.descriptive.moment.Kurtosis kurtosis) {
        copy(kurtosis, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void increment(double d) {
        if (this.incMoment) {
            this.moment.increment(d);
            return;
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_INCREMENT_STATISTIC_CONSTRUCTED_FROM_EXTERNAL_MOMENTS, new java.lang.Object[0]);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public double getResult() {
        if (this.moment.getN() <= 3) {
            return Double.NaN;
        }
        double d = this.moment.m2 / (this.moment.n - 1);
        if (this.moment.n <= 3 || d < 1.0E-19d) {
            return 0.0d;
        }
        double d2 = this.moment.n;
        double d3 = d2 - 1.0d;
        return ((((d2 + 1.0d) * d2) * this.moment.m4) - (((this.moment.m2 * 3.0d) * this.moment.m2) * d3)) / ((((d3 * (d2 - 2.0d)) * (d2 - 3.0d)) * d) * d);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void clear() {
        if (this.incMoment) {
            this.moment.clear();
            return;
        }
        throw org.apache.commons.math.MathRuntimeException.createIllegalStateException(org.apache.commons.math.exception.util.LocalizedFormats.CANNOT_CLEAR_STATISTIC_CONSTRUCTED_FROM_EXTERNAL_MOMENTS, new java.lang.Object[0]);
    }

    @Override // org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public long getN() {
        return this.moment.getN();
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr, int i, int i2) {
        if (test(dArr, i, i2) && i2 > 3) {
            org.apache.commons.math.stat.descriptive.moment.Variance variance = new org.apache.commons.math.stat.descriptive.moment.Variance();
            variance.incrementAll(dArr, i, i2);
            double d = variance.moment.m1;
            double sqrt = org.apache.commons.math.util.FastMath.sqrt(variance.getResult());
            double d2 = 0.0d;
            for (int i3 = i; i3 < i + i2; i3++) {
                d2 += org.apache.commons.math.util.FastMath.pow(dArr[i3] - d, 4.0d);
            }
            double d3 = i2;
            double d4 = (d3 + 1.0d) * d3;
            double d5 = d3 - 1.0d;
            double d6 = d3 - 2.0d;
            double d7 = d3 - 3.0d;
            return ((d4 / ((d5 * d6) * d7)) * (d2 / org.apache.commons.math.util.FastMath.pow(sqrt, 4.0d))) - ((org.apache.commons.math.util.FastMath.pow(d5, 2.0d) * 3.0d) / (d6 * d7));
        }
        return Double.NaN;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.moment.Kurtosis copy() {
        org.apache.commons.math.stat.descriptive.moment.Kurtosis kurtosis = new org.apache.commons.math.stat.descriptive.moment.Kurtosis();
        copy(this, kurtosis);
        return kurtosis;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.moment.Kurtosis kurtosis, org.apache.commons.math.stat.descriptive.moment.Kurtosis kurtosis2) {
        kurtosis2.setData(kurtosis.getDataRef());
        kurtosis2.moment = kurtosis.moment.copy();
        kurtosis2.incMoment = kurtosis.incMoment;
    }
}
