package com.android.server.display.utils;

/* loaded from: classes.dex */
public abstract class AmbientFilter {
    protected static final boolean DEBUG = false;
    private final com.android.server.display.utils.RollingBuffer mBuffer;
    private final int mHorizon;
    protected boolean mLoggingEnabled;
    protected final java.lang.String mTag;

    protected abstract float filter(long j, com.android.server.display.utils.RollingBuffer rollingBuffer);

    AmbientFilter(java.lang.String str, int i) {
        validateArguments(i);
        this.mTag = str;
        this.mLoggingEnabled = false;
        this.mHorizon = i;
        this.mBuffer = new com.android.server.display.utils.RollingBuffer();
    }

    public boolean addValue(long j, float f) {
        if (f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return false;
        }
        truncateOldValues(j);
        if (this.mLoggingEnabled) {
            android.util.Slog.d(this.mTag, "add value: " + f + " @ " + j);
        }
        this.mBuffer.add(j, f);
        return true;
    }

    public float getEstimate(long j) {
        truncateOldValues(j);
        float filter = filter(j, this.mBuffer);
        if (this.mLoggingEnabled) {
            android.util.Slog.d(this.mTag, "get estimate: " + filter + " @ " + j);
        }
        return filter;
    }

    public void clear() {
        this.mBuffer.clear();
    }

    public boolean setLoggingEnabled(boolean z) {
        if (this.mLoggingEnabled == z) {
            return false;
        }
        this.mLoggingEnabled = z;
        return true;
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("  " + this.mTag);
        printWriter.println("    mLoggingEnabled=" + this.mLoggingEnabled);
        printWriter.println("    mHorizon=" + this.mHorizon);
        printWriter.println("    mBuffer=" + this.mBuffer);
    }

    private void validateArguments(int i) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("horizon must be positive");
        }
    }

    private void truncateOldValues(long j) {
        this.mBuffer.truncate(j - this.mHorizon);
    }

    static class WeightedMovingAverageAmbientFilter extends com.android.server.display.utils.AmbientFilter {
        private static final int PREDICTION_TIME = 100;
        private final float mIntercept;

        WeightedMovingAverageAmbientFilter(java.lang.String str, int i, float f) {
            super(str, i);
            validateArguments(f);
            this.mIntercept = f;
        }

        @Override // com.android.server.display.utils.AmbientFilter
        public void dump(java.io.PrintWriter printWriter) {
            super.dump(printWriter);
            printWriter.println("    mIntercept=" + this.mIntercept);
        }

        @Override // com.android.server.display.utils.AmbientFilter
        protected float filter(long j, com.android.server.display.utils.RollingBuffer rollingBuffer) {
            if (rollingBuffer.isEmpty()) {
                return -1.0f;
            }
            float[] weights = getWeights(j, rollingBuffer);
            float f = 0.0f;
            float f2 = 0.0f;
            for (int i = 0; i < weights.length; i++) {
                float value = rollingBuffer.getValue(i);
                float f3 = weights[i];
                f2 += value * f3;
                f += f3;
            }
            if (f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                return rollingBuffer.getValue(rollingBuffer.size() - 1);
            }
            return f2 / f;
        }

        private void validateArguments(float f) {
            if (java.lang.Float.isNaN(f) || f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                throw new java.lang.IllegalArgumentException("intercept must be a non-negative number");
            }
        }

        private float[] getWeights(long j, com.android.server.display.utils.RollingBuffer rollingBuffer) {
            int size = rollingBuffer.size();
            float[] fArr = new float[size];
            long time = rollingBuffer.getTime(0);
            float f = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            int i = 1;
            while (i < size) {
                float time2 = (rollingBuffer.getTime(i) - time) / 1000.0f;
                fArr[i - 1] = calculateIntegral(f, time2);
                i++;
                f = time2;
            }
            fArr[size - 1] = calculateIntegral(f, ((j + 100) - time) / 1000.0f);
            return fArr;
        }

        private float calculateIntegral(float f, float f2) {
            return antiderivative(f2) - antiderivative(f);
        }

        private float antiderivative(float f) {
            return (0.5f * f * f) + (this.mIntercept * f);
        }
    }
}
