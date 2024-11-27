package com.android.server.usage;

/* loaded from: classes2.dex */
class BroadcastEvent {
    private long mIdForResponseEvent;
    private int mSourceUid;
    private java.lang.String mTargetPackage;
    private int mTargetUserId;
    private final android.util.LongArrayQueue mTimestampsMs = new android.util.LongArrayQueue();

    BroadcastEvent(int i, @android.annotation.NonNull java.lang.String str, int i2, long j) {
        this.mSourceUid = i;
        this.mTargetPackage = str;
        this.mTargetUserId = i2;
        this.mIdForResponseEvent = j;
    }

    public int getSourceUid() {
        return this.mSourceUid;
    }

    @android.annotation.NonNull
    public java.lang.String getTargetPackage() {
        return this.mTargetPackage;
    }

    public int getTargetUserId() {
        return this.mTargetUserId;
    }

    public long getIdForResponseEvent() {
        return this.mIdForResponseEvent;
    }

    public android.util.LongArrayQueue getTimestampsMs() {
        return this.mTimestampsMs;
    }

    public void addTimestampMs(long j) {
        this.mTimestampsMs.addLast(j);
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof com.android.server.usage.BroadcastEvent)) {
            return false;
        }
        com.android.server.usage.BroadcastEvent broadcastEvent = (com.android.server.usage.BroadcastEvent) obj;
        if (this.mSourceUid == broadcastEvent.mSourceUid && this.mIdForResponseEvent == broadcastEvent.mIdForResponseEvent && this.mTargetUserId == broadcastEvent.mTargetUserId && this.mTargetPackage.equals(broadcastEvent.mTargetPackage)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mSourceUid), this.mTargetPackage, java.lang.Integer.valueOf(this.mTargetUserId), java.lang.Long.valueOf(this.mIdForResponseEvent));
    }

    @android.annotation.NonNull
    public java.lang.String toString() {
        return "BroadcastEvent {srcUid=" + this.mSourceUid + ",tgtPkg=" + this.mTargetPackage + ",tgtUser=" + this.mTargetUserId + ",id=" + this.mIdForResponseEvent + "}";
    }
}
