package org.apache.commons.math.stat.descriptive.summary;

/* loaded from: classes3.dex */
public class Product extends org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic implements java.io.Serializable, org.apache.commons.math.stat.descriptive.WeightedEvaluation {
    private static final long serialVersionUID = 2824226005990582538L;
    private long n;
    private double value;

    public Product() {
        this.n = 0L;
        this.value = Double.NaN;
    }

    public Product(org.apache.commons.math.stat.descriptive.summary.Product product) {
        copy(product, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void increment(double d) {
        if (this.n == 0) {
            this.value = d;
        } else {
            this.value *= d;
        }
        this.n++;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public double getResult() {
        return this.value;
    }

    @Override // org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public long getN() {
        return this.n;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public void clear() {
        this.value = Double.NaN;
        this.n = 0L;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr, int i, int i2) {
        if (!test(dArr, i, i2)) {
            return Double.NaN;
        }
        double d = 1.0d;
        for (int i3 = i; i3 < i + i2; i3++) {
            d *= dArr[i3];
        }
        return d;
    }

    @Override // org.apache.commons.math.stat.descriptive.WeightedEvaluation
    public double evaluate(double[] dArr, double[] dArr2, int i, int i2) {
        if (!test(dArr, dArr2, i, i2)) {
            return Double.NaN;
        }
        double d = 1.0d;
        for (int i3 = i; i3 < i + i2; i3++) {
            d *= org.apache.commons.math.util.FastMath.pow(dArr[i3], dArr2[i3]);
        }
        return d;
    }

    @Override // org.apache.commons.math.stat.descriptive.WeightedEvaluation
    public double evaluate(double[] dArr, double[] dArr2) {
        return evaluate(dArr, dArr2, 0, dArr.length);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractStorelessUnivariateStatistic, org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.summary.Product copy() {
        org.apache.commons.math.stat.descriptive.summary.Product product = new org.apache.commons.math.stat.descriptive.summary.Product();
        copy(this, product);
        return product;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.summary.Product product, org.apache.commons.math.stat.descriptive.summary.Product product2) {
        product2.setData(product.getDataRef());
        product2.n = product.n;
        product2.value = product.value;
    }
}
