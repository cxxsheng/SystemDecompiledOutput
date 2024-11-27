package com.android.internal.util;

/* loaded from: classes5.dex */
public class TokenBucket {
    private int mAvailable;
    private final int mCapacity;
    private final int mFillDelta;
    private long mLastFill;

    public TokenBucket(int i, int i2, int i3) {
        this.mFillDelta = com.android.internal.util.Preconditions.checkArgumentPositive(i, "deltaMs must be strictly positive");
        this.mCapacity = com.android.internal.util.Preconditions.checkArgumentPositive(i2, "capacity must be strictly positive");
        this.mAvailable = java.lang.Math.min(com.android.internal.util.Preconditions.checkArgumentNonnegative(i3), this.mCapacity);
        this.mLastFill = scaledTime();
    }

    public TokenBucket(int i, int i2) {
        this(i, i2, i2);
    }

    public void reset(int i) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        this.mAvailable = java.lang.Math.min(i, this.mCapacity);
        this.mLastFill = scaledTime();
    }

    public int capacity() {
        return this.mCapacity;
    }

    public int available() {
        fill();
        return this.mAvailable;
    }

    public boolean has() {
        fill();
        return this.mAvailable > 0;
    }

    public boolean get() {
        return get(1) == 1;
    }

    public int get(int i) {
        fill();
        if (i <= 0) {
            return 0;
        }
        if (i > this.mAvailable) {
            int i2 = this.mAvailable;
            this.mAvailable = 0;
            return i2;
        }
        this.mAvailable -= i;
        return i;
    }

    private void fill() {
        long scaledTime = scaledTime();
        this.mAvailable = java.lang.Math.min(this.mCapacity, this.mAvailable + ((int) (scaledTime - this.mLastFill)));
        this.mLastFill = scaledTime;
    }

    private long scaledTime() {
        return android.os.SystemClock.elapsedRealtime() / this.mFillDelta;
    }
}
