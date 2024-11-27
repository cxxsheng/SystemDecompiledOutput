package org.apache.commons.math.stat.inference;

/* loaded from: classes3.dex */
public class OneWayAnovaImpl implements org.apache.commons.math.stat.inference.OneWayAnova {
    @Override // org.apache.commons.math.stat.inference.OneWayAnova
    public double anovaFValue(java.util.Collection<double[]> collection) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return anovaStats(collection).F;
    }

    @Override // org.apache.commons.math.stat.inference.OneWayAnova
    public double anovaPValue(java.util.Collection<double[]> collection) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        return 1.0d - new org.apache.commons.math.distribution.FDistributionImpl(r6.dfbg, r6.dfwg).cumulativeProbability(anovaStats(collection).F);
    }

    @Override // org.apache.commons.math.stat.inference.OneWayAnova
    public boolean anovaTest(java.util.Collection<double[]> collection, double d) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        if (d <= 0.0d || d > 0.5d) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, java.lang.Double.valueOf(d), 0, java.lang.Double.valueOf(0.5d));
        }
        return anovaPValue(collection) < d;
    }

    private org.apache.commons.math.stat.inference.OneWayAnovaImpl.AnovaStats anovaStats(java.util.Collection<double[]> collection) throws java.lang.IllegalArgumentException, org.apache.commons.math.MathException {
        if (collection.size() < 2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.TWO_OR_MORE_CATEGORIES_REQUIRED, java.lang.Integer.valueOf(collection.size()));
        }
        for (double[] dArr : collection) {
            if (dArr.length <= 1) {
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.TWO_OR_MORE_VALUES_IN_CATEGORY_REQUIRED, java.lang.Integer.valueOf(dArr.length));
            }
        }
        org.apache.commons.math.stat.descriptive.summary.Sum sum = new org.apache.commons.math.stat.descriptive.summary.Sum();
        org.apache.commons.math.stat.descriptive.summary.SumOfSquares sumOfSquares = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
        double d = 0.0d;
        int i = 0;
        int i2 = 0;
        for (double[] dArr2 : collection) {
            org.apache.commons.math.stat.descriptive.summary.Sum sum2 = new org.apache.commons.math.stat.descriptive.summary.Sum();
            org.apache.commons.math.stat.descriptive.summary.SumOfSquares sumOfSquares2 = new org.apache.commons.math.stat.descriptive.summary.SumOfSquares();
            int i3 = 0;
            for (double d2 : dArr2) {
                i3++;
                sum2.increment(d2);
                sumOfSquares2.increment(d2);
                i++;
                sum.increment(d2);
                sumOfSquares.increment(d2);
            }
            i2 += i3 - 1;
            d += sumOfSquares2.getResult() - ((sum2.getResult() * sum2.getResult()) / i3);
        }
        double result = (sumOfSquares.getResult() - ((sum.getResult() * sum.getResult()) / i)) - d;
        int size = collection.size() - 1;
        return new org.apache.commons.math.stat.inference.OneWayAnovaImpl.AnovaStats(size, i2, (result / size) / (d / i2));
    }

    private static class AnovaStats {
        private double F;
        private int dfbg;
        private int dfwg;

        private AnovaStats(int i, int i2, double d) {
            this.dfbg = i;
            this.dfwg = i2;
            this.F = d;
        }
    }
}
