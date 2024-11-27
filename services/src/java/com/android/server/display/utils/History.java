package com.android.server.display.utils;

/* loaded from: classes.dex */
public class History {
    private java.time.Clock mClock;
    private int mCount;
    private int mEnd;
    private int mSize;
    private int mStart;
    private long[] mTimes;
    private float[] mValues;

    public History(int i) {
        this(i, java.time.Clock.systemUTC());
    }

    public History(int i, java.time.Clock clock) {
        this.mSize = i;
        this.mCount = 0;
        this.mStart = 0;
        this.mEnd = 0;
        this.mTimes = new long[i];
        this.mValues = new float[i];
        this.mClock = clock;
    }

    public void add(float f) {
        this.mTimes[this.mEnd] = this.mClock.millis();
        this.mValues[this.mEnd] = f;
        if (this.mCount < this.mSize) {
            this.mCount++;
        } else {
            this.mStart = (this.mStart + 1) % this.mSize;
        }
        this.mEnd = (this.mEnd + 1) % this.mSize;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("[");
        int i = 0;
        while (i < this.mCount) {
            int i2 = (this.mStart + i) % this.mSize;
            long j = this.mTimes[i2];
            sb.append(this.mValues[i2] + " @ " + j);
            i++;
            if (i != this.mCount) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
