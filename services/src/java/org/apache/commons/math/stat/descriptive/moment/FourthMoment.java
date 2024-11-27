package org.apache.commons.math.stat.descriptive.moment;

/* loaded from: classes3.dex */
public class FourthMoment extends org.apache.commons.math.stat.descriptive.moment.ThirdMoment implements java.io.Serializable {
    private static final long serialVersionUID = 4763990447117157611L;
    protected double m4;

    public FourthMoment() {
        this.m4 = Double.NaN;
    }

    public FourthMoment(org.apache.commons.math.stat.descriptive.moment.FourthMoment fourthMoment) {
        copy(fourthMoment, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.moment.ThirdMoment, org.apache.commons.math.stat.descriptive.moment.SecondMoment, org.apache.commons.math.stat.descriptive.moment.FirstMoment, org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void increment(double d) {
        if (this.n < 1) {
            this.m4 = 0.0d;
            this.m3 = 0.0d;
            this.m2 = 0.0d;
            this.m1 = 0.0d;
        }
        double d2 = this.m3;
        double d3 = this.m2;
        super.increment(d);
        double d4 = this.n;
        double d5 = (this.m4 - ((this.nDev * 4.0d) * d2)) + (this.nDevSq * 6.0d * d3);
        double d6 = d4 - 1.0d;
        this.m4 = d5 + (((d4 * d4) - (3.0d * d6)) * this.nDevSq * this.nDevSq * d6 * d4);
    }

    @Override // org.apache.commons.math.stat.descriptive.moment.ThirdMoment, org.apache.commons.math.stat.descriptive.moment.SecondMoment, org.apache.commons.math.stat.descriptive.moment.FirstMoment, org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public double getResult() {
        return this.m4;
    }

    @Override // org.apache.commons.math.stat.descriptive.moment.ThirdMoment, org.apache.commons.math.stat.descriptive.moment.SecondMoment, org.apache.commons.math.stat.descriptive.moment.FirstMoment, org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void clear() {
        super.clear();
        this.m4 = Double.NaN;
    }

    @Override // org.apache.commons.math.stat.descriptive.moment.ThirdMoment, org.apache.commons.math.stat.descriptive.moment.SecondMoment, org.apache.commons.math.stat.descriptive.moment.FirstMoment, org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.moment.FourthMoment copy() {
        org.apache.commons.math.stat.descriptive.moment.FourthMoment fourthMoment = new org.apache.commons.math.stat.descriptive.moment.FourthMoment();
        copy(this, fourthMoment);
        return fourthMoment;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.moment.FourthMoment fourthMoment, org.apache.commons.math.stat.descriptive.moment.FourthMoment fourthMoment2) {
        org.apache.commons.math.stat.descriptive.moment.ThirdMoment.copy((org.apache.commons.math.stat.descriptive.moment.ThirdMoment) fourthMoment, (org.apache.commons.math.stat.descriptive.moment.ThirdMoment) fourthMoment2);
        fourthMoment2.m4 = fourthMoment.m4;
    }
}
