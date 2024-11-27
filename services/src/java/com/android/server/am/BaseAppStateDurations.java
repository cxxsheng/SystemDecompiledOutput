package com.android.server.am;

/* loaded from: classes.dex */
abstract class BaseAppStateDurations<T extends com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent> extends com.android.server.am.BaseAppStateTimeEvents<T> {
    static final boolean DEBUG_BASE_APP_STATE_DURATIONS = false;

    BaseAppStateDurations(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig maxTrackingDurationConfig) {
        super(i, str, i2, str2, maxTrackingDurationConfig);
    }

    BaseAppStateDurations(@android.annotation.NonNull com.android.server.am.BaseAppStateDurations baseAppStateDurations) {
        super(baseAppStateDurations);
    }

    /* JADX WARN: Multi-variable type inference failed */
    void addEvent(boolean z, @android.annotation.NonNull T t, int i) {
        if (this.mEvents[i] == null) {
            this.mEvents[i] = new java.util.LinkedList();
        }
        java.util.LinkedList linkedList = this.mEvents[i];
        linkedList.size();
        if (z != isActive(i)) {
            linkedList.add(t);
        }
        trimEvents(getEarliest(t.getTimestamp()), i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.am.BaseAppStateTimeEvents, com.android.server.am.BaseAppStateEvents
    void trimEvents(long j, int i) {
        trimEvents(j, (java.util.LinkedList) this.mEvents[i]);
    }

    void trimEvents(long j, java.util.LinkedList<T> linkedList) {
        if (linkedList == null) {
            return;
        }
        while (linkedList.size() > 1) {
            if (linkedList.peek().getTimestamp() >= j) {
                return;
            }
            if (linkedList.get(1).getTimestamp() > j) {
                linkedList.get(0).trimTo(j);
                return;
            } else {
                linkedList.pop();
                linkedList.pop();
            }
        }
        if (linkedList.size() == 1) {
            linkedList.get(0).trimTo(java.lang.Math.max(j, linkedList.peek().getTimestamp()));
        }
    }

    @Override // com.android.server.am.BaseAppStateTimeEvents, com.android.server.am.BaseAppStateEvents
    java.util.LinkedList<T> add(java.util.LinkedList<T> linkedList, java.util.LinkedList<T> linkedList2) {
        T t;
        T t2;
        long j;
        if (linkedList2 == null || linkedList2.size() == 0) {
            return linkedList;
        }
        if (linkedList == null || linkedList.size() == 0) {
            return (java.util.LinkedList) linkedList2.clone();
        }
        java.util.Iterator<T> it = linkedList.iterator();
        java.util.Iterator<T> it2 = linkedList2.iterator();
        T next = it.next();
        T next2 = it2.next();
        com.android.server.CircularQueue circularQueue = (java.util.LinkedList<T>) new java.util.LinkedList();
        long timestamp = next.getTimestamp();
        long timestamp2 = next2.getTimestamp();
        boolean z = false;
        boolean z2 = false;
        while (true) {
            long j2 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            if (timestamp != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME || timestamp2 != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                boolean z3 = true;
                boolean z4 = z || z2;
                if (timestamp == timestamp2) {
                    z = !z;
                    z2 = !z2;
                    if (it.hasNext()) {
                        t2 = it.next();
                        j = t2.getTimestamp();
                    } else {
                        t2 = next;
                        j = Long.MAX_VALUE;
                    }
                    if (it2.hasNext()) {
                        next2 = it2.next();
                        j2 = next2.getTimestamp();
                    }
                } else if (timestamp < timestamp2) {
                    z = !z;
                    if (it.hasNext()) {
                        t2 = it.next();
                        j2 = t2.getTimestamp();
                    } else {
                        t2 = next;
                    }
                    j = j2;
                    j2 = timestamp2;
                } else {
                    z2 = !z2;
                    if (it2.hasNext()) {
                        t = it2.next();
                        j2 = t.getTimestamp();
                    } else {
                        t = next2;
                    }
                    long j3 = timestamp;
                    t2 = next;
                    next = next2;
                    next2 = t;
                    j = j3;
                }
                if (!z && !z2) {
                    z3 = false;
                }
                if (z4 != z3) {
                    circularQueue.add((com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent) next.clone());
                }
                next = t2;
                timestamp = j;
                timestamp2 = j2;
            } else {
                return circularQueue;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    void subtract(com.android.server.am.BaseAppStateDurations baseAppStateDurations, int i, int i2) {
        if (this.mEvents.length <= i || this.mEvents[i] == null || baseAppStateDurations.mEvents.length <= i2 || baseAppStateDurations.mEvents[i2] == null) {
            return;
        }
        this.mEvents[i] = subtract((java.util.LinkedList) this.mEvents[i], (java.util.LinkedList) baseAppStateDurations.mEvents[i2]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    void subtract(com.android.server.am.BaseAppStateDurations baseAppStateDurations, int i) {
        if (baseAppStateDurations.mEvents.length <= i || baseAppStateDurations.mEvents[i] == null) {
            return;
        }
        for (int i2 = 0; i2 < this.mEvents.length; i2++) {
            if (this.mEvents[i2] != null) {
                this.mEvents[i2] = subtract((java.util.LinkedList) this.mEvents[i2], (java.util.LinkedList) baseAppStateDurations.mEvents[i]);
            }
        }
    }

    java.util.LinkedList<T> subtract(java.util.LinkedList<T> linkedList, java.util.LinkedList<T> linkedList2) {
        T t;
        T t2;
        long j;
        if (linkedList2 == null || linkedList2.size() == 0 || linkedList == null || linkedList.size() == 0) {
            return linkedList;
        }
        java.util.Iterator<T> it = linkedList.iterator();
        java.util.Iterator<T> it2 = linkedList2.iterator();
        T next = it.next();
        T next2 = it2.next();
        com.android.server.CircularQueue circularQueue = (java.util.LinkedList<T>) new java.util.LinkedList();
        long timestamp = next.getTimestamp();
        long timestamp2 = next2.getTimestamp();
        boolean z = false;
        boolean z2 = false;
        while (true) {
            long j2 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
            if (timestamp != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME || timestamp2 != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                boolean z3 = z && !z2;
                if (timestamp == timestamp2) {
                    z = !z;
                    z2 = !z2;
                    if (it.hasNext()) {
                        t2 = it.next();
                        j = t2.getTimestamp();
                    } else {
                        t2 = next;
                        j = Long.MAX_VALUE;
                    }
                    if (it2.hasNext()) {
                        next2 = it2.next();
                        j2 = next2.getTimestamp();
                    }
                } else if (timestamp < timestamp2) {
                    z = !z;
                    if (it.hasNext()) {
                        t2 = it.next();
                        j2 = t2.getTimestamp();
                    } else {
                        t2 = next;
                    }
                    j = j2;
                    j2 = timestamp2;
                } else {
                    z2 = !z2;
                    if (it2.hasNext()) {
                        t = it2.next();
                        j2 = t.getTimestamp();
                    } else {
                        t = next2;
                    }
                    long j3 = timestamp;
                    t2 = next;
                    next = next2;
                    next2 = t;
                    j = j3;
                }
                if (z3 != (z && !z2)) {
                    circularQueue.add((com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent) next.clone());
                }
                next = t2;
                timestamp = j;
                timestamp2 = j2;
            } else {
                return circularQueue;
            }
        }
    }

    long getTotalDurations(long j, int i) {
        return getTotalDurationsSince(getEarliest(0L), j, i);
    }

    long getTotalDurationsSince(long j, long j2, int i) {
        java.util.LinkedList linkedList = this.mEvents[i];
        if (linkedList == null || linkedList.size() == 0) {
            return 0L;
        }
        java.util.Iterator it = linkedList.iterator();
        long j3 = 0;
        long j4 = 0;
        boolean z = true;
        while (it.hasNext()) {
            com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent baseTimeEvent = (com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent) it.next();
            if (baseTimeEvent.getTimestamp() >= j && !z) {
                j3 += java.lang.Math.max(0L, baseTimeEvent.getTimestamp() - java.lang.Math.max(j4, j));
            } else {
                j4 = baseTimeEvent.getTimestamp();
            }
            z = !z;
        }
        if ((linkedList.size() & 1) == 1) {
            return j3 + java.lang.Math.max(0L, j2 - java.lang.Math.max(j4, j));
        }
        return j3;
    }

    boolean isActive(int i) {
        return this.mEvents[i] != null && (this.mEvents[i].size() & 1) == 1;
    }

    @Override // com.android.server.am.BaseAppStateEvents
    java.lang.String formatEventSummary(long j, int i) {
        return android.util.TimeUtils.formatDuration(getTotalDurations(j, i));
    }

    @Override // com.android.server.am.BaseAppStateEvents
    public java.lang.String toString() {
        return this.mPackageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.os.UserHandle.formatUid(this.mUid) + " isActive[0]=" + isActive(0) + " totalDurations[0]=" + getTotalDurations(android.os.SystemClock.elapsedRealtime(), 0);
    }
}
