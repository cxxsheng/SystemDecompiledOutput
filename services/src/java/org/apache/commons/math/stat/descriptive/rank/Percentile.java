package org.apache.commons.math.stat.descriptive.rank;

/* loaded from: classes3.dex */
public class Percentile extends org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic implements java.io.Serializable {
    private static final int MAX_CACHED_LEVELS = 10;
    private static final int MIN_SELECT_SIZE = 15;
    private static final long serialVersionUID = -8091216485095130416L;
    private int[] cachedPivots;
    private double quantile;

    public Percentile() {
        this(50.0d);
    }

    public Percentile(double d) {
        this.quantile = 0.0d;
        setQuantile(d);
        this.cachedPivots = null;
    }

    public Percentile(org.apache.commons.math.stat.descriptive.rank.Percentile percentile) {
        this.quantile = 0.0d;
        copy(percentile, this);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic
    public void setData(double[] dArr) {
        if (dArr == null) {
            this.cachedPivots = null;
        } else {
            this.cachedPivots = new int[1023];
            java.util.Arrays.fill(this.cachedPivots, -1);
        }
        super.setData(dArr);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic
    public void setData(double[] dArr, int i, int i2) {
        if (dArr == null) {
            this.cachedPivots = null;
        } else {
            this.cachedPivots = new int[1023];
            java.util.Arrays.fill(this.cachedPivots, -1);
        }
        super.setData(dArr, i, i2);
    }

    public double evaluate(double d) {
        return evaluate(getDataRef(), d);
    }

    public double evaluate(double[] dArr, double d) {
        test(dArr, 0, 0);
        return evaluate(dArr, 0, dArr.length, d);
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic
    public double evaluate(double[] dArr, int i, int i2) {
        return evaluate(dArr, i, i2, this.quantile);
    }

    public double evaluate(double[] dArr, int i, int i2, double d) {
        int[] iArr;
        double[] dArr2;
        test(dArr, i, i2);
        if (d > 100.0d || d <= 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_BOUNDS_QUANTILE_VALUE, java.lang.Double.valueOf(d));
        }
        if (i2 == 0) {
            return Double.NaN;
        }
        if (i2 == 1) {
            return dArr[i];
        }
        double d2 = i2;
        double d3 = (d * (d2 + 1.0d)) / 100.0d;
        double floor = org.apache.commons.math.util.FastMath.floor(d3);
        int i3 = (int) floor;
        double d4 = d3 - floor;
        if (dArr == getDataRef()) {
            dArr2 = getDataRef();
            iArr = this.cachedPivots;
        } else {
            double[] dArr3 = new double[i2];
            java.lang.System.arraycopy(dArr, i, dArr3, 0, i2);
            iArr = new int[1023];
            java.util.Arrays.fill(iArr, -1);
            dArr2 = dArr3;
        }
        if (d3 < 1.0d) {
            return select(dArr2, iArr, 0);
        }
        if (d3 >= d2) {
            return select(dArr2, iArr, i2 - 1);
        }
        double select = select(dArr2, iArr, i3 - 1);
        return select + (d4 * (select(dArr2, iArr, i3) - select));
    }

    private double select(double[] dArr, int[] iArr, int i) {
        int partition;
        int length = dArr.length;
        int i2 = 0;
        int i3 = 0;
        while (length - i2 > 15) {
            if (i3 < iArr.length && iArr[i3] >= 0) {
                partition = iArr[i3];
            } else {
                partition = partition(dArr, i2, length, medianOf3(dArr, i2, length));
                if (i3 < iArr.length) {
                    iArr[i3] = partition;
                }
            }
            if (i == partition) {
                return dArr[i];
            }
            if (i < partition) {
                i3 = java.lang.Math.min((i3 * 2) + 1, iArr.length);
                length = partition;
            } else {
                i3 = java.lang.Math.min((i3 * 2) + 2, iArr.length);
                i2 = partition + 1;
            }
        }
        insertionSort(dArr, i2, length);
        return dArr[i];
    }

    int medianOf3(double[] dArr, int i, int i2) {
        int i3 = i2 - 1;
        int i4 = ((i3 - i) / 2) + i;
        double d = dArr[i];
        double d2 = dArr[i4];
        double d3 = dArr[i3];
        if (d < d2) {
            if (d2 < d3) {
                return i4;
            }
            return d < d3 ? i3 : i;
        }
        if (d < d3) {
            return i;
        }
        return d2 < d3 ? i3 : i4;
    }

    private int partition(double[] dArr, int i, int i2, int i3) {
        double d = dArr[i3];
        dArr[i3] = dArr[i];
        int i4 = i + 1;
        int i5 = i2 - 1;
        while (i4 < i5) {
            while (i4 < i5 && dArr[i5] >= d) {
                i5--;
            }
            while (i4 < i5 && dArr[i4] <= d) {
                i4++;
            }
            if (i4 < i5) {
                double d2 = dArr[i4];
                dArr[i4] = dArr[i5];
                dArr[i5] = d2;
                i5--;
                i4++;
            }
        }
        if (i4 >= i2 || dArr[i4] > d) {
            i4--;
        }
        dArr[i] = dArr[i4];
        dArr[i4] = d;
        return i4;
    }

    private void insertionSort(double[] dArr, int i, int i2) {
        for (int i3 = i + 1; i3 < i2; i3++) {
            double d = dArr[i3];
            int i4 = i3 - 1;
            while (i4 >= i && d < dArr[i4]) {
                dArr[i4 + 1] = dArr[i4];
                i4--;
            }
            dArr[i4 + 1] = d;
        }
    }

    public double getQuantile() {
        return this.quantile;
    }

    public void setQuantile(double d) {
        if (d <= 0.0d || d > 100.0d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_BOUNDS_QUANTILE_VALUE, java.lang.Double.valueOf(d));
        }
        this.quantile = d;
    }

    @Override // org.apache.commons.math.stat.descriptive.AbstractUnivariateStatistic, org.apache.commons.math.stat.descriptive.UnivariateStatistic, org.apache.commons.math.stat.descriptive.StorelessUnivariateStatistic
    public org.apache.commons.math.stat.descriptive.rank.Percentile copy() {
        org.apache.commons.math.stat.descriptive.rank.Percentile percentile = new org.apache.commons.math.stat.descriptive.rank.Percentile();
        copy(this, percentile);
        return percentile;
    }

    public static void copy(org.apache.commons.math.stat.descriptive.rank.Percentile percentile, org.apache.commons.math.stat.descriptive.rank.Percentile percentile2) {
        percentile2.setData(percentile.getDataRef());
        if (percentile.cachedPivots != null) {
            java.lang.System.arraycopy(percentile.cachedPivots, 0, percentile2.cachedPivots, 0, percentile.cachedPivots.length);
        }
        percentile2.quantile = percentile.quantile;
    }
}
