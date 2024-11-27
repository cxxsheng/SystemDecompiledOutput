package com.android.server.powerstats;

/* loaded from: classes2.dex */
public class IntervalRandomNoiseGenerator {
    private static final int DISTRIBUTION_SAMPLE_SIZE = 17;
    private static final double UNINITIALIZED = -1.0d;
    private final org.apache.commons.math.distribution.AbstractContinuousDistribution mDistribution;
    private final double[] mSamples = new double[17];

    IntervalRandomNoiseGenerator(double d) {
        if (d <= 1.0d) {
            throw new java.lang.IllegalArgumentException("alpha should be > 1");
        }
        this.mDistribution = new org.apache.commons.math.distribution.BetaDistributionImpl(d, 1.0d);
        refresh();
    }

    @com.android.internal.annotations.VisibleForTesting
    void reseed(long j) {
        this.mDistribution.reseedRandomGenerator(j);
    }

    long addNoise(long j, long j2, int i) {
        int i2 = i % 17;
        double d = this.mSamples[i2];
        if (d < 0.0d) {
            try {
                d = this.mDistribution.sample();
                this.mSamples[i2] = d;
            } catch (org.apache.commons.math.MathException e) {
                throw new java.lang.IllegalStateException(e);
            }
        }
        return j + ((long) ((j2 - j) * d));
    }

    void refresh() {
        java.util.Arrays.fill(this.mSamples, UNINITIALIZED);
    }
}
