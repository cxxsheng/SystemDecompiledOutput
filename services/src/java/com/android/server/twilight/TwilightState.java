package com.android.server.twilight;

/* loaded from: classes2.dex */
public final class TwilightState {
    private final long mSunriseTimeMillis;
    private final long mSunsetTimeMillis;

    public TwilightState(long j, long j2) {
        this.mSunriseTimeMillis = j;
        this.mSunsetTimeMillis = j2;
    }

    public long sunriseTimeMillis() {
        return this.mSunriseTimeMillis;
    }

    public java.time.LocalDateTime sunrise() {
        return java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(this.mSunriseTimeMillis), java.util.TimeZone.getDefault().toZoneId());
    }

    public long sunsetTimeMillis() {
        return this.mSunsetTimeMillis;
    }

    public java.time.LocalDateTime sunset() {
        return java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(this.mSunsetTimeMillis), java.util.TimeZone.getDefault().toZoneId());
    }

    public boolean isNight() {
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        return currentTimeMillis >= this.mSunsetTimeMillis && currentTimeMillis < this.mSunriseTimeMillis;
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof com.android.server.twilight.TwilightState) && equals((com.android.server.twilight.TwilightState) obj);
    }

    public boolean equals(com.android.server.twilight.TwilightState twilightState) {
        return twilightState != null && this.mSunriseTimeMillis == twilightState.mSunriseTimeMillis && this.mSunsetTimeMillis == twilightState.mSunsetTimeMillis;
    }

    public int hashCode() {
        return java.lang.Long.hashCode(this.mSunriseTimeMillis) ^ java.lang.Long.hashCode(this.mSunsetTimeMillis);
    }

    public java.lang.String toString() {
        return "TwilightState { sunrise=" + ((java.lang.Object) android.text.format.DateFormat.format("MM-dd HH:mm", this.mSunriseTimeMillis)) + " sunset=" + ((java.lang.Object) android.text.format.DateFormat.format("MM-dd HH:mm", this.mSunsetTimeMillis)) + " }";
    }
}
