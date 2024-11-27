package org.apache.commons.math.stat.descriptive.moment;

/* loaded from: classes3.dex */
public class ThirdMoment extends org.apache.commons.math.stat.descriptive.moment.SecondMoment implements java.io.Serializable {
    private static final long serialVersionUID = -7818711964045118679L;
    protected double m3;
    protected double nDevSq;

    public ThirdMoment() {
        this.m3 = Double.NaN;
        this.nDevSq = Double.NaN;
    }

    public ThirdMoment(org.apache.commons.math.stat.descriptive.moment.ThirdMoment thirdMoment) {
        copy(thirdMoment, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.moment.SecondMoment, org.apache.commons.math.stat.descriptive.moment.FirstMoment, org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void increment(double d) {
        if (this.n < 1) {
            this.m1 = 0.0d;
            this.m2 = 0.0d;
            this.m3 = 0.0d;
        }
        double d2 = this.m2;
        super.increment(d);
        this.nDevSq = this.nDev * this.nDev;
        double d3 = this.n;
        this.m3 = (this.m3 - ((this.nDev * 3.0d) * d2)) + ((d3 - 1.0d) * (d3 - 2.0d) * this.nDevSq * this.dev);
    }

    @Override // org.apache.commons.math.stat.descriptive.moment.SecondMoment, org.apache.commons.math.stat.descriptive.moment.FirstMoment, org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public double getResult() {
        return this.m3;
    }

    @Override // org.apache.commons.math.stat.descriptive.moment.SecondMoment, org.apache.commons.math.stat.descriptive.moment.FirstMoment, org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void clear() {
        super.clear();
        this.m3 = Double.NaN;
        this.nDevSq = Double.NaN;
    }

    @Override // org.apache.commons.math.stat.descriptive.moment.SecondMoment, org.apache.commons.math.stat.descriptive.moment.FirstMoment, org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.moment.ThirdMoment copy() {
        org.apache.commons.math.stat.descriptive.moment.ThirdMoment thirdMoment = new org.apache.commons.math.stat.descriptive.moment.ThirdMoment();
        copy(this, thirdMoment);
        return thirdMoment;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.moment.ThirdMoment thirdMoment, org.apache.commons.math.stat.descriptive.moment.ThirdMoment thirdMoment2) {
        org.apache.commons.math.stat.descriptive.moment.SecondMoment.copy((org.apache.commons.math.stat.descriptive.moment.SecondMoment) thirdMoment, (org.apache.commons.math.stat.descriptive.moment.SecondMoment) thirdMoment2);
        thirdMoment2.m3 = thirdMoment.m3;
        thirdMoment2.nDevSq = thirdMoment.nDevSq;
    }
}
