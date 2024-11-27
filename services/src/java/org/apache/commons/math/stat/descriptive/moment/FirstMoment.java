package org.apache.commons.math.stat.descriptive.moment;

/* loaded from: classes3.dex */
public class FirstMoment extends org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic implements java.io.Serializable {
    private static final long serialVersionUID = 6112755307178490473L;
    protected double dev;
    protected double m1;
    protected long n;
    protected double nDev;

    public FirstMoment() {
        this.n = 0L;
        this.m1 = Double.NaN;
        this.dev = Double.NaN;
        this.nDev = Double.NaN;
    }

    public FirstMoment(org.apache.commons.math.stat.descriptive.moment.FirstMoment firstMoment) {
        copy(firstMoment, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void increment(double d) {
        if (this.n == 0) {
            this.m1 = 0.0d;
        }
        this.n++;
        double d2 = this.n;
        this.dev = d - this.m1;
        this.nDev = this.dev / d2;
        this.m1 += this.nDev;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void clear() {
        this.m1 = Double.NaN;
        this.n = 0L;
        this.dev = Double.NaN;
        this.nDev = Double.NaN;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public double getResult() {
        return this.m1;
    }

    @Override // org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public long getN() {
        return this.n;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.moment.FirstMoment copy() {
        org.apache.commons.math.stat.descriptive.moment.FirstMoment firstMoment = new org.apache.commons.math.stat.descriptive.moment.FirstMoment();
        copy(this, firstMoment);
        return firstMoment;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.moment.FirstMoment firstMoment, org.apache.commons.math.stat.descriptive.moment.FirstMoment firstMoment2) {
        firstMoment2.setData(firstMoment.getDataRef());
        firstMoment2.n = firstMoment.n;
        firstMoment2.m1 = firstMoment.m1;
        firstMoment2.dev = firstMoment.dev;
        firstMoment2.nDev = firstMoment.nDev;
    }
}
