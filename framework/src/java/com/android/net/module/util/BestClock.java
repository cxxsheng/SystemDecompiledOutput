package com.android.net.module.util;

/* loaded from: classes5.dex */
public final class BestClock extends java.time.Clock {
    private static final java.lang.String TAG = "BestClock";
    private final java.time.Clock[] mClocks;
    private final java.time.ZoneId mZone;

    public BestClock(java.time.ZoneId zoneId, java.time.Clock... clockArr) {
        this.mZone = zoneId;
        this.mClocks = clockArr;
    }

    @Override // java.time.Clock, java.time.InstantSource
    public long millis() {
        java.time.Clock[] clockArr = this.mClocks;
        int length = clockArr.length;
        for (int i = 0; i < length; i++) {
            try {
                return clockArr[i].millis();
            } catch (java.time.DateTimeException e) {
                android.util.Log.w(TAG, e.toString());
            }
        }
        throw new java.time.DateTimeException("No clocks in " + java.util.Arrays.toString(this.mClocks) + " were able to provide time");
    }

    @Override // java.time.Clock
    public java.time.ZoneId getZone() {
        return this.mZone;
    }

    @Override // java.time.Clock, java.time.InstantSource
    public java.time.Clock withZone(java.time.ZoneId zoneId) {
        return new com.android.net.module.util.BestClock(zoneId, this.mClocks);
    }

    @Override // java.time.Clock, java.time.InstantSource
    public java.time.Instant instant() {
        return java.time.Instant.ofEpochMilli(millis());
    }
}
