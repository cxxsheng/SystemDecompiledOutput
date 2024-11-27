package com.android.server.am;

/* loaded from: classes.dex */
abstract class BaseAppStateEvents<E> {
    static final boolean DEBUG_BASE_APP_STATE_EVENTS = false;
    final java.util.LinkedList<E>[] mEvents;
    int mExemptReason = -1;

    @android.annotation.NonNull
    final com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig mMaxTrackingDurationConfig;

    @android.annotation.NonNull
    final java.lang.String mPackageName;

    @android.annotation.NonNull
    final java.lang.String mTag;
    final int mUid;

    interface Factory<T extends com.android.server.am.BaseAppStateEvents> {
        T createAppStateEvents(int i, java.lang.String str);

        T createAppStateEvents(T t);
    }

    interface MaxTrackingDurationConfig {
        long getMaxTrackingDuration();
    }

    abstract java.util.LinkedList<E> add(java.util.LinkedList<E> linkedList, java.util.LinkedList<E> linkedList2);

    abstract int getTotalEventsSince(long j, long j2, int i);

    abstract void trimEvents(long j, int i);

    BaseAppStateEvents(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig maxTrackingDurationConfig) {
        this.mUid = i;
        this.mPackageName = str;
        this.mTag = str2;
        this.mMaxTrackingDurationConfig = maxTrackingDurationConfig;
        this.mEvents = new java.util.LinkedList[i2];
    }

    BaseAppStateEvents(@android.annotation.NonNull com.android.server.am.BaseAppStateEvents baseAppStateEvents) {
        this.mUid = baseAppStateEvents.mUid;
        this.mPackageName = baseAppStateEvents.mPackageName;
        this.mTag = baseAppStateEvents.mTag;
        this.mMaxTrackingDurationConfig = baseAppStateEvents.mMaxTrackingDurationConfig;
        this.mEvents = new java.util.LinkedList[baseAppStateEvents.mEvents.length];
        for (int i = 0; i < this.mEvents.length; i++) {
            if (baseAppStateEvents.mEvents[i] != null) {
                this.mEvents[i] = new java.util.LinkedList<>(baseAppStateEvents.mEvents[i]);
            }
        }
    }

    void addEvent(E e, long j, int i) {
        if (this.mEvents[i] == null) {
            this.mEvents[i] = new java.util.LinkedList<>();
        }
        this.mEvents[i].add(e);
        trimEvents(getEarliest(j), i);
    }

    void trim(long j) {
        for (int i = 0; i < this.mEvents.length; i++) {
            trimEvents(j, i);
        }
    }

    boolean isEmpty() {
        for (int i = 0; i < this.mEvents.length; i++) {
            if (this.mEvents[i] != null && !this.mEvents[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    boolean isEmpty(int i) {
        return this.mEvents[i] == null || this.mEvents[i].isEmpty();
    }

    void add(com.android.server.am.BaseAppStateEvents baseAppStateEvents) {
        if (this.mEvents.length != baseAppStateEvents.mEvents.length) {
            return;
        }
        for (int i = 0; i < this.mEvents.length; i++) {
            this.mEvents[i] = add(this.mEvents[i], baseAppStateEvents.mEvents[i]);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.LinkedList<E> getRawEvents(int i) {
        return this.mEvents[i];
    }

    int getTotalEvents(long j, int i) {
        return getTotalEventsSince(getEarliest(0L), j, i);
    }

    long getEarliest(long j) {
        return java.lang.Math.max(0L, j - this.mMaxTrackingDurationConfig.getMaxTrackingDuration());
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str, long j) {
        for (int i = 0; i < this.mEvents.length; i++) {
            if (this.mEvents[i] != null) {
                printWriter.print(str);
                printWriter.print(formatEventTypeLabel(i));
                printWriter.println(formatEventSummary(j, i));
            }
        }
    }

    java.lang.String formatEventSummary(long j, int i) {
        return java.lang.Integer.toString(getTotalEvents(j, i));
    }

    java.lang.String formatEventTypeLabel(int i) {
        return java.lang.Integer.toString(i) + ":";
    }

    public java.lang.String toString() {
        return this.mPackageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.os.UserHandle.formatUid(this.mUid) + " totalEvents[0]=" + formatEventSummary(android.os.SystemClock.elapsedRealtime(), 0);
    }
}
