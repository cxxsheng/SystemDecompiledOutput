package com.android.server.display.utils;

/* loaded from: classes.dex */
public class RollingBuffer {
    private static final int INITIAL_SIZE = 50;
    private int mCount;
    private int mEnd;
    private int mStart;
    private int mSize = 50;
    private long[] mTimes = new long[50];
    private float[] mValues = new float[50];

    public RollingBuffer() {
        clear();
    }

    public void add(long j, float f) {
        if (this.mCount >= this.mSize) {
            expandBuffer();
        }
        this.mTimes[this.mEnd] = j;
        this.mValues[this.mEnd] = f;
        this.mEnd = (this.mEnd + 1) % this.mSize;
        this.mCount++;
    }

    public int size() {
        return this.mCount;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public long getTime(int i) {
        return this.mTimes[offsetOf(i)];
    }

    public float getValue(int i) {
        return this.mValues[offsetOf(i)];
    }

    public void truncate(long j) {
        if (isEmpty() || getTime(0) >= j) {
            return;
        }
        int latestIndexBefore = getLatestIndexBefore(j);
        this.mStart = offsetOf(latestIndexBefore);
        this.mCount -= latestIndexBefore;
        this.mTimes[this.mStart] = j;
    }

    public void clear() {
        this.mCount = 0;
        this.mStart = 0;
        this.mEnd = 0;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("[");
        int i = 0;
        while (i < this.mCount) {
            int offsetOf = offsetOf(i);
            sb.append(this.mValues[offsetOf] + " @ " + this.mTimes[offsetOf]);
            i++;
            if (i != this.mCount) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private int offsetOf(int i) {
        if (i < 0 || i >= this.mCount) {
            throw new java.lang.ArrayIndexOutOfBoundsException("invalid index: " + i + ", mCount= " + this.mCount);
        }
        return (this.mStart + i) % this.mSize;
    }

    private void expandBuffer() {
        int i = this.mSize * 2;
        long[] jArr = new long[i];
        float[] fArr = new float[i];
        java.lang.System.arraycopy(this.mTimes, this.mStart, jArr, 0, this.mCount - this.mStart);
        java.lang.System.arraycopy(this.mTimes, 0, jArr, this.mCount - this.mStart, this.mStart);
        java.lang.System.arraycopy(this.mValues, this.mStart, fArr, 0, this.mCount - this.mStart);
        java.lang.System.arraycopy(this.mValues, 0, fArr, this.mCount - this.mStart, this.mStart);
        this.mSize = i;
        this.mStart = 0;
        this.mEnd = this.mCount;
        this.mTimes = jArr;
        this.mValues = fArr;
    }

    private int getLatestIndexBefore(long j) {
        for (int i = 1; i < this.mCount; i++) {
            if (this.mTimes[offsetOf(i)] > j) {
                return i - 1;
            }
        }
        return this.mCount - 1;
    }
}
