package com.android.server.display;

/* loaded from: classes.dex */
class HbmEvent {
    private long mEndTimeMillis;
    private long mStartTimeMillis;

    HbmEvent(long j, long j2) {
        this.mStartTimeMillis = j;
        this.mEndTimeMillis = j2;
    }

    public long getStartTimeMillis() {
        return this.mStartTimeMillis;
    }

    public long getEndTimeMillis() {
        return this.mEndTimeMillis;
    }

    public java.lang.String toString() {
        return "HbmEvent: {startTimeMillis:" + this.mStartTimeMillis + ", endTimeMillis: " + this.mEndTimeMillis + "}, total: " + ((this.mEndTimeMillis - this.mStartTimeMillis) / 1000) + "]";
    }
}
