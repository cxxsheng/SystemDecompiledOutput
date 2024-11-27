package org.apache.commons.math.stat.regression;

/* loaded from: classes3.dex */
public class SimpleRegression implements java.io.Serializable {
    private static final long serialVersionUID = -3004689053607543335L;
    private org.apache.commons.math.distribution.TDistribution distribution;
    private long n;
    private double sumX;
    private double sumXX;
    private double sumXY;
    private double sumY;
    private double sumYY;
    private double xbar;
    private double ybar;

    public SimpleRegression() {
        this(new org.apache.commons.math.distribution.TDistributionImpl(1.0d));
    }

    @java.lang.Deprecated
    public SimpleRegression(org.apache.commons.math.distribution.TDistribution tDistribution) {
        this.sumX = 0.0d;
        this.sumXX = 0.0d;
        this.sumY = 0.0d;
        this.sumYY = 0.0d;
        this.sumXY = 0.0d;
        this.n = 0L;
        this.xbar = 0.0d;
        this.ybar = 0.0d;
        setDistribution(tDistribution);
    }

    public SimpleRegression(int i) {
        this.sumX = 0.0d;
        this.sumXX = 0.0d;
        this.sumY = 0.0d;
        this.sumYY = 0.0d;
        this.sumXY = 0.0d;
        this.n = 0L;
        this.xbar = 0.0d;
        this.ybar = 0.0d;
        setDistribution(new org.apache.commons.math.distribution.TDistributionImpl(i));
    }

    public void addData(double d, double d2) {
        if (this.n == 0) {
            this.xbar = d;
            this.ybar = d2;
        } else {
            double d3 = d - this.xbar;
            double d4 = d2 - this.ybar;
            this.sumXX += ((d3 * d3) * this.n) / (this.n + 1.0d);
            this.sumYY += ((d4 * d4) * this.n) / (this.n + 1.0d);
            this.sumXY += ((d3 * d4) * this.n) / (this.n + 1.0d);
            this.xbar += d3 / (this.n + 1.0d);
            this.ybar += d4 / (this.n + 1.0d);
        }
        this.sumX += d;
        this.sumY += d2;
        this.n++;
        if (this.n > 2) {
            this.distribution.setDegreesOfFreedom(this.n - 2);
        }
    }

    public void removeData(double d, double d2) {
        if (this.n > 0) {
            double d3 = d - this.xbar;
            double d4 = d2 - this.ybar;
            this.sumXX -= ((d3 * d3) * this.n) / (this.n - 1.0d);
            this.sumYY -= ((d4 * d4) * this.n) / (this.n - 1.0d);
            this.sumXY -= ((d3 * d4) * this.n) / (this.n - 1.0d);
            this.xbar -= d3 / (this.n - 1.0d);
            this.ybar -= d4 / (this.n - 1.0d);
            this.sumX -= d;
            this.sumY -= d2;
            this.n--;
            if (this.n > 2) {
                this.distribution.setDegreesOfFreedom(this.n - 2);
            }
        }
    }

    public void addData(double[][] dArr) {
        for (int i = 0; i < dArr.length; i++) {
            addData(dArr[i][0], dArr[i][1]);
        }
    }

    public void removeData(double[][] dArr) {
        for (int i = 0; i < dArr.length && this.n > 0; i++) {
            removeData(dArr[i][0], dArr[i][1]);
        }
    }

    public void clear() {
        this.sumX = 0.0d;
        this.sumXX = 0.0d;
        this.sumY = 0.0d;
        this.sumYY = 0.0d;
        this.sumXY = 0.0d;
        this.n = 0L;
    }

    public long getN() {
        return this.n;
    }

    public double predict(double d) {
        double slope = getSlope();
        return getIntercept(slope) + (slope * d);
    }

    public double getIntercept() {
        return getIntercept(getSlope());
    }

    public double getSlope() {
        if (this.n >= 2 && org.apache.commons.math.util.FastMath.abs(this.sumXX) >= 4.9E-323d) {
            return this.sumXY / this.sumXX;
        }
        return Double.NaN;
    }

    public double getSumSquaredErrors() {
        return org.apache.commons.math.util.FastMath.max(0.0d, this.sumYY - ((this.sumXY * this.sumXY) / this.sumXX));
    }

    public double getTotalSumSquares() {
        if (this.n < 2) {
            return Double.NaN;
        }
        return this.sumYY;
    }

    public double getXSumSquares() {
        if (this.n < 2) {
            return Double.NaN;
        }
        return this.sumXX;
    }

    public double getSumOfCrossProducts() {
        return this.sumXY;
    }

    public double getRegressionSumSquares() {
        return getRegressionSumSquares(getSlope());
    }

    public double getMeanSquareError() {
        if (this.n < 3) {
            return Double.NaN;
        }
        return getSumSquaredErrors() / (this.n - 2);
    }

    public double getR() {
        double slope = getSlope();
        double sqrt = org.apache.commons.math.util.FastMath.sqrt(getRSquare());
        if (slope < 0.0d) {
            return -sqrt;
        }
        return sqrt;
    }

    public double getRSquare() {
        double totalSumSquares = getTotalSumSquares();
        return (totalSumSquares - getSumSquaredErrors()) / totalSumSquares;
    }

    public double getInterceptStdErr() {
        return org.apache.commons.math.util.FastMath.sqrt(getMeanSquareError() * ((1.0d / this.n) + ((this.xbar * this.xbar) / this.sumXX)));
    }

    public double getSlopeStdErr() {
        return org.apache.commons.math.util.FastMath.sqrt(getMeanSquareError() / this.sumXX);
    }

    public double getSlopeConfidenceInterval() throws org.apache.commons.math.MathException {
        return getSlopeConfidenceInterval(0.05d);
    }

    public double getSlopeConfidenceInterval(double d) throws org.apache.commons.math.MathException {
        if (d >= 1.0d || d <= 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, java.lang.Double.valueOf(d), java.lang.Double.valueOf(0.0d), java.lang.Double.valueOf(1.0d));
        }
        return getSlopeStdErr() * this.distribution.inverseCumulativeProbability(1.0d - (d / 2.0d));
    }

    public double getSignificance() throws org.apache.commons.math.MathException {
        return (1.0d - this.distribution.cumulativeProbability(org.apache.commons.math.util.FastMath.abs(getSlope()) / getSlopeStdErr())) * 2.0d;
    }

    private double getIntercept(double d) {
        return (this.sumY - (d * this.sumX)) / this.n;
    }

    private double getRegressionSumSquares(double d) {
        return d * d * this.sumXX;
    }

    @java.lang.Deprecated
    public void setDistribution(org.apache.commons.math.distribution.TDistribution tDistribution) {
        this.distribution = tDistribution;
        if (this.n > 2) {
            this.distribution.setDegreesOfFreedom(this.n - 2);
        }
    }
}
