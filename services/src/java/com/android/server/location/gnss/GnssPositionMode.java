package com.android.server.location.gnss;

/* loaded from: classes2.dex */
public class GnssPositionMode {
    private final boolean mLowPowerMode;
    private final int mMinInterval;
    private final int mMode;
    private final int mPreferredAccuracy;
    private final int mPreferredTime;
    private final int mRecurrence;

    public GnssPositionMode(int i, int i2, int i3, int i4, int i5, boolean z) {
        this.mMode = i;
        this.mRecurrence = i2;
        this.mMinInterval = i3;
        this.mPreferredAccuracy = i4;
        this.mPreferredTime = i5;
        this.mLowPowerMode = z;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.server.location.gnss.GnssPositionMode)) {
            return false;
        }
        com.android.server.location.gnss.GnssPositionMode gnssPositionMode = (com.android.server.location.gnss.GnssPositionMode) obj;
        return this.mMode == gnssPositionMode.mMode && this.mRecurrence == gnssPositionMode.mRecurrence && this.mMinInterval == gnssPositionMode.mMinInterval && this.mPreferredAccuracy == gnssPositionMode.mPreferredAccuracy && this.mPreferredTime == gnssPositionMode.mPreferredTime && this.mLowPowerMode == gnssPositionMode.mLowPowerMode && getClass() == gnssPositionMode.getClass();
    }

    public int hashCode() {
        return java.util.Arrays.hashCode(new java.lang.Object[]{java.lang.Integer.valueOf(this.mMode), java.lang.Integer.valueOf(this.mRecurrence), java.lang.Integer.valueOf(this.mMinInterval), java.lang.Integer.valueOf(this.mPreferredAccuracy), java.lang.Integer.valueOf(this.mPreferredTime), java.lang.Boolean.valueOf(this.mLowPowerMode), getClass()});
    }
}
