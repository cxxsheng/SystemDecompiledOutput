package org.apache.commons.math.stat.descriptive.moment;

/* loaded from: classes3.dex */
public class Skewness extends org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic implements java.io.Serializable {
    private static final long serialVersionUID = 7101857578996691352L;
    protected boolean incMoment;
    protected org.apache.commons.math.stat.descriptive.moment.ThirdMoment moment;

    public Skewness() {
        this.moment = null;
        this.incMoment = true;
        this.moment = new org.apache.commons.math.stat.descriptive.moment.ThirdMoment();
    }

    public Skewness(org.apache.commons.math.stat.descriptive.moment.ThirdMoment thirdMoment) {
        this.moment = null;
        this.incMoment = false;
        this.moment = thirdMoment;
    }

    public Skewness(org.apache.commons.math.stat.descriptive.moment.Skewness skewness) {
        this.moment = null;
        copy(skewness, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void increment(double d) {
        if (this.incMoment) {
            this.moment.increment(d);
        }
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public double getResult() {
        if (this.moment.n < 3) {
            return Double.NaN;
        }
        double d = this.moment.m2 / (this.moment.n - 1);
        if (d < 1.0E-19d) {
            return 0.0d;
        }
        double n = this.moment.getN();
        return (this.moment.m3 * n) / ((((n - 1.0d) * (n - 2.0d)) * org.apache.commons.math.util.FastMath.sqrt(d)) * d);
    }

    @Override // org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public long getN() {
        return this.moment.getN();
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void clear() {
        if (this.incMoment) {
            this.moment.clear();
        }
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr, int i, int i2) {
        int i3;
        int i4 = i;
        if (test(dArr, i, i2) && i2 > 2) {
            double evaluate = new org.apache.commons.math.stat.descriptive.moment.Mean().evaluate(dArr, i4, i2);
            double d = 0.0d;
            int i5 = i4;
            double d2 = 0.0d;
            double d3 = 0.0d;
            while (true) {
                i3 = i4 + i2;
                if (i5 >= i3) {
                    break;
                }
                double d4 = dArr[i5] - evaluate;
                d2 += d4 * d4;
                d3 += d4;
                i5++;
            }
            double d5 = i2;
            double d6 = (d2 - ((d3 * d3) / d5)) / (i2 - 1);
            while (i4 < i3) {
                double d7 = dArr[i4] - evaluate;
                d += d7 * d7 * d7;
                i4++;
            }
            return (d5 / ((d5 - 1.0d) * (d5 - 2.0d))) * (d / (d6 * org.apache.commons.math.util.FastMath.sqrt(d6)));
        }
        return Double.NaN;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.moment.Skewness copy() {
        org.apache.commons.math.stat.descriptive.moment.Skewness skewness = new org.apache.commons.math.stat.descriptive.moment.Skewness();
        copy(this, skewness);
        return skewness;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.moment.Skewness skewness, org.apache.commons.math.stat.descriptive.moment.Skewness skewness2) {
        skewness2.setData(skewness.getDataRef());
        skewness2.moment = new org.apache.commons.math.stat.descriptive.moment.ThirdMoment(skewness.moment.copy());
        skewness2.incMoment = skewness.incMoment;
    }
}
