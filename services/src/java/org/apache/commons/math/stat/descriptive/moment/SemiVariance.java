package org.apache.commons.math.stat.descriptive.moment;

/* loaded from: classes3.dex */
public class SemiVariance extends org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic implements java.io.Serializable {
    private static final long serialVersionUID = -2653430366886024994L;
    private boolean biasCorrected;
    private org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction varianceDirection;
    public static final org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction UPSIDE_VARIANCE = org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction.UPSIDE;
    public static final org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction DOWNSIDE_VARIANCE = org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction.DOWNSIDE;

    public SemiVariance() {
        this.biasCorrected = true;
        this.varianceDirection = org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction.DOWNSIDE;
    }

    public SemiVariance(boolean z) {
        this.biasCorrected = true;
        this.varianceDirection = org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction.DOWNSIDE;
        this.biasCorrected = z;
    }

    public SemiVariance(org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction direction) {
        this.biasCorrected = true;
        this.varianceDirection = org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction.DOWNSIDE;
        this.varianceDirection = direction;
    }

    public SemiVariance(boolean z, org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction direction) {
        this.biasCorrected = true;
        this.varianceDirection = org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction.DOWNSIDE;
        this.biasCorrected = z;
        this.varianceDirection = direction;
    }

    public SemiVariance(org.apache.commons.math.stat.descriptive.moment.SemiVariance semiVariance) {
        this.biasCorrected = true;
        this.varianceDirection = org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction.DOWNSIDE;
        copy(semiVariance, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.moment.SemiVariance copy() {
        org.apache.commons.math.stat.descriptive.moment.SemiVariance semiVariance = new org.apache.commons.math.stat.descriptive.moment.SemiVariance();
        copy(this, semiVariance);
        return semiVariance;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.moment.SemiVariance semiVariance, org.apache.commons.math.stat.descriptive.moment.SemiVariance semiVariance2) {
        semiVariance2.setData(semiVariance.getDataRef());
        semiVariance2.biasCorrected = semiVariance.biasCorrected;
        semiVariance2.varianceDirection = semiVariance.varianceDirection;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr) {
        if (dArr == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INPUT_ARRAY);
        }
        return evaluate(dArr, 0, dArr.length);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr, int i, int i2) {
        return evaluate(dArr, new org.apache.commons.math.stat.descriptive.moment.Mean().evaluate(dArr, i, i2), this.varianceDirection, this.biasCorrected, 0, dArr.length);
    }

    public double evaluate(double[] dArr, org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction direction) {
        return evaluate(dArr, new org.apache.commons.math.stat.descriptive.moment.Mean().evaluate(dArr), direction, this.biasCorrected, 0, dArr.length);
    }

    public double evaluate(double[] dArr, double d) {
        return evaluate(dArr, d, this.varianceDirection, this.biasCorrected, 0, dArr.length);
    }

    public double evaluate(double[] dArr, double d, org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction direction) {
        return evaluate(dArr, d, direction, this.biasCorrected, 0, dArr.length);
    }

    public double evaluate(double[] dArr, double d, org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction direction, boolean z, int i, int i2) {
        test(dArr, i, i2);
        if (dArr.length == 0) {
            return Double.NaN;
        }
        double d2 = 0.0d;
        if (dArr.length == 1) {
            return 0.0d;
        }
        boolean direction2 = direction.getDirection();
        while (i < i2) {
            if ((dArr[i] > d) == direction2) {
                double d3 = dArr[i] - d;
                d2 += d3 * d3;
            }
            i++;
        }
        if (z) {
            return d2 / (i2 - 1.0d);
        }
        return d2 / i2;
    }

    public boolean isBiasCorrected() {
        return this.biasCorrected;
    }

    public void setBiasCorrected(boolean z) {
        this.biasCorrected = z;
    }

    public org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction getVarianceDirection() {
        return this.varianceDirection;
    }

    public void setVarianceDirection(org.apache.commons.math.stat.descriptive.moment.SemiVariance.Direction direction) {
        this.varianceDirection = direction;
    }

    public enum Direction {
        UPSIDE(true),
        DOWNSIDE(false);

        private boolean direction;

        Direction(boolean z) {
            this.direction = z;
        }

        boolean getDirection() {
            return this.direction;
        }
    }
}
