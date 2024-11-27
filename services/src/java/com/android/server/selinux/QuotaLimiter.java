package com.android.server.selinux;

/* loaded from: classes2.dex */
public class QuotaLimiter {
    private final com.android.internal.os.Clock mClock;
    private long mCurrentWindow;
    private final int mMaxPermits;
    private int mPermitsGranted;
    private final java.time.Duration mWindowSize;

    @com.android.internal.annotations.VisibleForTesting
    QuotaLimiter(com.android.internal.os.Clock clock, java.time.Duration duration, int i) {
        this.mCurrentWindow = 0L;
        this.mPermitsGranted = 0;
        this.mClock = clock;
        this.mWindowSize = duration;
        this.mMaxPermits = i;
    }

    public QuotaLimiter(java.time.Duration duration, int i) {
        this(com.android.internal.os.Clock.SYSTEM_CLOCK, duration, i);
    }

    public QuotaLimiter(int i) {
        this(com.android.internal.os.Clock.SYSTEM_CLOCK, java.time.Duration.ofDays(1L), i);
    }

    boolean acquire() {
        long dividedBy = java.time.Duration.between(java.time.Instant.EPOCH, java.time.Instant.ofEpochMilli(this.mClock.currentTimeMillis())).dividedBy(this.mWindowSize);
        if (dividedBy > this.mCurrentWindow) {
            this.mCurrentWindow = dividedBy;
            this.mPermitsGranted = 0;
        }
        if (this.mPermitsGranted >= this.mMaxPermits) {
            return false;
        }
        this.mPermitsGranted++;
        return true;
    }
}
