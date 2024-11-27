package org.apache.commons.math.stat.descriptive.moment;

/* loaded from: classes3.dex */
public class Variance extends org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic implements java.io.Serializable, org.apache.commons.math.stat.descriptive.WeightedEvaluation {
    private static final long serialVersionUID = -9111962718267217978L;
    protected boolean incMoment;
    private boolean isBiasCorrected;
    protected org.apache.commons.math.stat.descriptive.moment.SecondMoment moment;

    public Variance() {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        this.moment = new org.apache.commons.math.stat.descriptive.moment.SecondMoment();
    }

    public Variance(org.apache.commons.math.stat.descriptive.moment.SecondMoment secondMoment) {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        this.incMoment = false;
        this.moment = secondMoment;
    }

    public Variance(boolean z) {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        this.moment = new org.apache.commons.math.stat.descriptive.moment.SecondMoment();
        this.isBiasCorrected = z;
    }

    public Variance(boolean z, org.apache.commons.math.stat.descriptive.moment.SecondMoment secondMoment) {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        this.incMoment = false;
        this.moment = secondMoment;
        this.isBiasCorrected = z;
    }

    public Variance(org.apache.commons.math.stat.descriptive.moment.Variance variance) {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        copy(variance, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void increment(double d) {
        if (this.incMoment) {
            this.moment.increment(d);
        }
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public double getResult() {
        if (this.moment.n == 0) {
            return Double.NaN;
        }
        if (this.moment.n == 1) {
            return 0.0d;
        }
        if (this.isBiasCorrected) {
            return this.moment.m2 / (this.moment.n - 1.0d);
        }
        return this.moment.m2 / this.moment.n;
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
    public double evaluate(double[] dArr) {
        if (dArr == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INPUT_ARRAY);
        }
        return evaluate(dArr, 0, dArr.length);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr, int i, int i2) {
        if (test(dArr, i, i2)) {
            clear();
            if (i2 == 1) {
                return 0.0d;
            }
            if (i2 > 1) {
                return evaluate(dArr, new org.apache.commons.math.stat.descriptive.moment.Mean().evaluate(dArr, i, i2), i, i2);
            }
        }
        return Double.NaN;
    }

    @Override // org.apache.commons.math.stat.descriptive.WeightedEvaluation
    public double evaluate(double[] dArr, double[] dArr2, int i, int i2) {
        if (test(dArr, dArr2, i, i2)) {
            clear();
            if (i2 == 1) {
                return 0.0d;
            }
            if (i2 > 1) {
                return evaluate(dArr, dArr2, new org.apache.commons.math.stat.descriptive.moment.Mean().evaluate(dArr, dArr2, i, i2), i, i2);
            }
        }
        return Double.NaN;
    }

    @Override // org.apache.commons.math.stat.descriptive.WeightedEvaluation
    public double evaluate(double[] dArr, double[] dArr2) {
        return evaluate(dArr, dArr2, 0, dArr.length);
    }

    public double evaluate(double[] dArr, double d, int i, int i2) {
        if (test(dArr, i, i2)) {
            double d2 = 0.0d;
            if (i2 == 1) {
                return 0.0d;
            }
            if (i2 > 1) {
                double d3 = 0.0d;
                for (int i3 = i; i3 < i + i2; i3++) {
                    double d4 = dArr[i3] - d;
                    d2 += d4 * d4;
                    d3 += d4;
                }
                double d5 = i2;
                if (this.isBiasCorrected) {
                    return (d2 - ((d3 * d3) / d5)) / (d5 - 1.0d);
                }
                return (d2 - ((d3 * d3) / d5)) / d5;
            }
        }
        return Double.NaN;
    }

    public double evaluate(double[] dArr, double d) {
        return evaluate(dArr, d, 0, dArr.length);
    }

    public double evaluate(double[] dArr, double[] dArr2, double d, int i, int i2) {
        if (test(dArr, dArr2, i, i2)) {
            double d2 = 0.0d;
            if (i2 == 1) {
                return 0.0d;
            }
            if (i2 > 1) {
                double d3 = 0.0d;
                double d4 = 0.0d;
                for (int i3 = i; i3 < i + i2; i3++) {
                    double d5 = dArr[i3] - d;
                    d3 += dArr2[i3] * d5 * d5;
                    d4 += dArr2[i3] * d5;
                }
                for (double d6 : dArr2) {
                    d2 += d6;
                }
                if (this.isBiasCorrected) {
                    return (d3 - ((d4 * d4) / d2)) / (d2 - 1.0d);
                }
                return (d3 - ((d4 * d4) / d2)) / d2;
            }
        }
        return Double.NaN;
    }

    public double evaluate(double[] dArr, double[] dArr2, double d) {
        return evaluate(dArr, dArr2, d, 0, dArr.length);
    }

    public boolean isBiasCorrected() {
        return this.isBiasCorrected;
    }

    public void setBiasCorrected(boolean z) {
        this.isBiasCorrected = z;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.moment.Variance copy() {
        org.apache.commons.math.stat.descriptive.moment.Variance variance = new org.apache.commons.math.stat.descriptive.moment.Variance();
        copy(this, variance);
        return variance;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.moment.Variance variance, org.apache.commons.math.stat.descriptive.moment.Variance variance2) {
        if (variance == null || variance2 == null) {
            throw new org.apache.commons.math.exception.NullArgumentException();
        }
        variance2.setData(variance.getDataRef());
        variance2.moment = variance.moment.copy();
        variance2.isBiasCorrected = variance.isBiasCorrected;
        variance2.incMoment = variance.incMoment;
    }
}
