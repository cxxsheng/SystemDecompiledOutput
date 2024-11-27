package com.android.server.notification;

/* loaded from: classes2.dex */
class RateEstimator {
    private static final double MINIMUM_DT = 5.0E-4d;
    private static final double RATE_ALPHA = 0.7d;
    private double mInterarrivalTime = 1000.0d;
    private java.lang.Long mLastEventTime;

    public void update(long j) {
        if (this.mLastEventTime != null) {
            this.mInterarrivalTime = getInterarrivalEstimate(j);
        }
        this.mLastEventTime = java.lang.Long.valueOf(j);
    }

    public float getRate(long j) {
        if (this.mLastEventTime == null) {
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        return (float) (1.0d / getInterarrivalEstimate(j));
    }

    private double getInterarrivalEstimate(long j) {
        return (this.mInterarrivalTime * RATE_ALPHA) + (java.lang.Math.max((j - this.mLastEventTime.longValue()) / 1000.0d, MINIMUM_DT) * 0.30000000000000004d);
    }
}
