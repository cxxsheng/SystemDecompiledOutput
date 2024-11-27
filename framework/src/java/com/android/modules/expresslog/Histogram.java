package com.android.modules.expresslog;

/* loaded from: classes5.dex */
public final class Histogram {
    private final com.android.modules.expresslog.Histogram.BinOptions mBinOptions;
    private final java.lang.String mMetricId;

    public interface BinOptions {
        int getBinForSample(float f);

        int getBinsCount();
    }

    public Histogram(java.lang.String str, com.android.modules.expresslog.Histogram.BinOptions binOptions) {
        this.mMetricId = str;
        this.mBinOptions = binOptions;
    }

    public void logSample(float f) {
        com.android.modules.expresslog.StatsExpressLog.write(593, com.android.modules.expresslog.MetricIds.getMetricIdHash(this.mMetricId, 2), 1L, this.mBinOptions.getBinForSample(f));
    }

    public void logSampleWithUid(int i, float f) {
        com.android.modules.expresslog.StatsExpressLog.write(658, com.android.modules.expresslog.MetricIds.getMetricIdHash(this.mMetricId, 4), 1L, this.mBinOptions.getBinForSample(f), i);
    }

    public static final class UniformOptions implements com.android.modules.expresslog.Histogram.BinOptions {
        private final int mBinCount;
        private final float mBinSize;
        private final float mExclusiveMaxValue;
        private final float mMinValue;

        public UniformOptions(int i, float f, float f2) {
            if (i < 1) {
                throw new java.lang.IllegalArgumentException("Bin count should be positive number");
            }
            if (f2 <= f) {
                throw new java.lang.IllegalArgumentException("Bins range invalid (maxValue < minValue)");
            }
            this.mMinValue = f;
            this.mExclusiveMaxValue = f2;
            this.mBinSize = (this.mExclusiveMaxValue - f) / i;
            this.mBinCount = i + 2;
        }

        @Override // com.android.modules.expresslog.Histogram.BinOptions
        public int getBinsCount() {
            return this.mBinCount;
        }

        @Override // com.android.modules.expresslog.Histogram.BinOptions
        public int getBinForSample(float f) {
            if (f < this.mMinValue) {
                return 0;
            }
            if (f >= this.mExclusiveMaxValue) {
                return this.mBinCount - 1;
            }
            return (int) (((f - this.mMinValue) / this.mBinSize) + 1.0f);
        }
    }

    public static final class ScaledRangeOptions implements com.android.modules.expresslog.Histogram.BinOptions {
        final long[] mBins;

        public ScaledRangeOptions(int i, int i2, float f, float f2) {
            if (i < 1) {
                throw new java.lang.IllegalArgumentException("Bin count should be positive number");
            }
            if (f < 1.0f) {
                throw new java.lang.IllegalArgumentException("First bin width invalid (should be 1.f at minimum)");
            }
            if (f2 < 1.0f) {
                throw new java.lang.IllegalArgumentException("Scaled factor invalid (should be 1.f at minimum)");
            }
            this.mBins = initBins(i + 1, i2, f, f2);
        }

        @Override // com.android.modules.expresslog.Histogram.BinOptions
        public int getBinsCount() {
            return this.mBins.length + 1;
        }

        @Override // com.android.modules.expresslog.Histogram.BinOptions
        public int getBinForSample(float f) {
            if (f < this.mBins[0]) {
                return 0;
            }
            if (f >= this.mBins[this.mBins.length - 1]) {
                return this.mBins.length;
            }
            return lower_bound(this.mBins, (long) f) + 1;
        }

        private static int lower_bound(long[] jArr, long j) {
            int binarySearch = java.util.Arrays.binarySearch(jArr, j);
            if (binarySearch < 0) {
                return java.lang.Math.abs(binarySearch) - 2;
            }
            return binarySearch;
        }

        private static long[] initBins(int i, int i2, float f, float f2) {
            long[] jArr = new long[i];
            jArr[0] = i2;
            double d = f;
            for (int i3 = 1; i3 < i; i3++) {
                double d2 = jArr[i3 - 1] + d;
                if (d2 > 2.147483647E9d) {
                    throw new java.lang.IllegalArgumentException("Attempted to create a bucket larger than maxint");
                }
                jArr[i3] = (long) d2;
                d *= f2;
            }
            return jArr;
        }
    }
}
