package com.android.server.selinux;

/* loaded from: classes2.dex */
public final class RateLimiter {
    private final com.android.internal.os.Clock mClock;
    private java.time.Instant mNextPermit;
    private final java.time.Duration mWindow;

    @com.android.internal.annotations.VisibleForTesting
    RateLimiter(com.android.internal.os.Clock clock, java.time.Duration duration) {
        this.mNextPermit = java.time.Instant.EPOCH;
        this.mClock = clock;
        this.mWindow = duration;
    }

    public RateLimiter(java.time.Duration duration) {
        this(com.android.internal.os.Clock.SYSTEM_CLOCK, duration);
    }

    public void acquire() {
        java.time.Instant ofEpochMilli = java.time.Instant.ofEpochMilli(this.mClock.currentTimeMillis());
        if (this.mNextPermit.isAfter(ofEpochMilli)) {
            android.os.SystemClock.sleep(java.time.temporal.ChronoUnit.MILLIS.between(ofEpochMilli, this.mNextPermit));
            this.mNextPermit = this.mNextPermit.plus((java.time.temporal.TemporalAmount) this.mWindow);
        } else {
            this.mNextPermit = ofEpochMilli.plus((java.time.temporal.TemporalAmount) this.mWindow);
        }
    }

    public boolean tryAcquire() {
        java.time.Instant ofEpochMilli = java.time.Instant.ofEpochMilli(this.mClock.currentTimeMillis());
        if (this.mNextPermit.isAfter(ofEpochMilli)) {
            return false;
        }
        this.mNextPermit = ofEpochMilli.plus((java.time.temporal.TemporalAmount) this.mWindow);
        return true;
    }
}
