package org.apache.commons.math.stat.descriptive.moment;

/* loaded from: classes3.dex */
public class Mean extends org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic implements java.io.Serializable, org.apache.commons.math.stat.descriptive.WeightedEvaluation {
    private static final long serialVersionUID = -1296043746617791564L;
    protected boolean incMoment;
    protected org.apache.commons.math.stat.descriptive.moment.FirstMoment moment;

    public Mean() {
        this.incMoment = true;
        this.moment = new org.apache.commons.math.stat.descriptive.moment.FirstMoment();
    }

    public Mean(org.apache.commons.math.stat.descriptive.moment.FirstMoment firstMoment) {
        this.moment = firstMoment;
        this.incMoment = false;
    }

    public Mean(org.apache.commons.math.stat.descriptive.moment.Mean mean) {
        copy(mean, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void increment(double d) {
        if (this.incMoment) {
            this.moment.increment(d);
        }
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void clear() {
        if (this.incMoment) {
            this.moment.clear();
        }
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public double getResult() {
        return this.moment.m1;
    }

    @Override // org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public long getN() {
        return this.moment.getN();
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr, int i, int i2) {
        if (test(dArr, i, i2)) {
            double d = i2;
            double evaluate = new org.apache.commons.math.stat.descriptive.summary.Sum().evaluate(dArr, i, i2) / d;
            double d2 = 0.0d;
            for (int i3 = i; i3 < i + i2; i3++) {
                d2 += dArr[i3] - evaluate;
            }
            return evaluate + (d2 / d);
        }
        return Double.NaN;
    }

    @Override // org.apache.commons.math.stat.descriptive.WeightedEvaluation
    public double evaluate(double[] dArr, double[] dArr2, int i, int i2) {
        if (test(dArr, dArr2, i, i2)) {
            org.apache.commons.math.stat.descriptive.summary.Sum sum = new org.apache.commons.math.stat.descriptive.summary.Sum();
            double evaluate = sum.evaluate(dArr2, i, i2);
            double evaluate2 = sum.evaluate(dArr, dArr2, i, i2) / evaluate;
            double d = 0.0d;
            for (int i3 = i; i3 < i + i2; i3++) {
                d += dArr2[i3] * (dArr[i3] - evaluate2);
            }
            return evaluate2 + (d / evaluate);
        }
        return Double.NaN;
    }

    @Override // org.apache.commons.math.stat.descriptive.WeightedEvaluation
    public double evaluate(double[] dArr, double[] dArr2) {
        return evaluate(dArr, dArr2, 0, dArr.length);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.moment.Mean copy() {
        org.apache.commons.math.stat.descriptive.moment.Mean mean = new org.apache.commons.math.stat.descriptive.moment.Mean();
        copy(this, mean);
        return mean;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.moment.Mean mean, org.apache.commons.math.stat.descriptive.moment.Mean mean2) {
        mean2.setData(mean.getDataRef());
        mean2.incMoment = mean.incMoment;
        mean2.moment = mean.moment.copy();
    }
}
