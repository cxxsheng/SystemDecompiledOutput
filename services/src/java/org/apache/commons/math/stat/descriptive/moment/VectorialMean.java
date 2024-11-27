package org.apache.commons.math.stat.descriptive.moment;

/* loaded from: classes3.dex */
public class VectorialMean implements java.io.Serializable {
    private static final long serialVersionUID = 8223009086481006892L;
    private final org.apache.commons.math.stat.descriptive.moment.Mean[] means;

    public VectorialMean(int i) {
        this.means = new org.apache.commons.math.stat.descriptive.moment.Mean[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.means[i2] = new org.apache.commons.math.stat.descriptive.moment.Mean();
        }
    }

    public void increment(double[] dArr) throws org.apache.commons.math.DimensionMismatchException {
        if (dArr.length != this.means.length) {
            throw new org.apache.commons.math.DimensionMismatchException(dArr.length, this.means.length);
        }
        for (int i = 0; i < dArr.length; i++) {
            this.means[i].increment(dArr[i]);
        }
    }

    public double[] getResult() {
        int length = this.means.length;
        double[] dArr = new double[length];
        for (int i = 0; i < length; i++) {
            dArr[i] = this.means[i].getResult();
        }
        return dArr;
    }

    public long getN() {
        if (this.means.length == 0) {
            return 0L;
        }
        return this.means[0].getN();
    }

    public int hashCode() {
        return 31 + java.util.Arrays.hashCode(this.means);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof org.apache.commons.math.stat.descriptive.moment.VectorialMean) && java.util.Arrays.equals(this.means, ((org.apache.commons.math.stat.descriptive.moment.VectorialMean) obj).means);
    }
}
