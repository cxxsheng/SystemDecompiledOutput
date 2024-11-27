package com.android.server.am;

/* loaded from: classes.dex */
class BaseAppStateTimeEvents<T extends com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent> extends com.android.server.am.BaseAppStateEvents<T> {
    BaseAppStateTimeEvents(int i, @android.annotation.NonNull java.lang.String str, int i2, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig maxTrackingDurationConfig) {
        super(i, str, i2, str2, maxTrackingDurationConfig);
    }

    BaseAppStateTimeEvents(@android.annotation.NonNull com.android.server.am.BaseAppStateTimeEvents baseAppStateTimeEvents) {
        super(baseAppStateTimeEvents);
    }

    @Override // com.android.server.am.BaseAppStateEvents
    java.util.LinkedList<T> add(java.util.LinkedList<T> linkedList, java.util.LinkedList<T> linkedList2) {
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
        while (true) {
            if (timestamp == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME && timestamp2 == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                return circularQueue;
            }
            if (timestamp == timestamp2) {
                circularQueue.add((com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent) next.clone());
                if (it.hasNext()) {
                    next = it.next();
                    timestamp = next.getTimestamp();
                } else {
                    timestamp = Long.MAX_VALUE;
                }
                if (it2.hasNext()) {
                    next2 = it2.next();
                    timestamp2 = next2.getTimestamp();
                } else {
                    timestamp2 = Long.MAX_VALUE;
                }
            } else if (timestamp < timestamp2) {
                circularQueue.add((com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent) next.clone());
                if (it.hasNext()) {
                    next = it.next();
                    timestamp = next.getTimestamp();
                } else {
                    timestamp = Long.MAX_VALUE;
                }
            } else {
                circularQueue.add((com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent) next2.clone());
                if (it2.hasNext()) {
                    next2 = it2.next();
                    timestamp2 = next2.getTimestamp();
                } else {
                    timestamp2 = Long.MAX_VALUE;
                }
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateEvents
    int getTotalEventsSince(long j, long j2, int i) {
        java.util.LinkedList linkedList = this.mEvents[i];
        int i2 = 0;
        if (linkedList == null || linkedList.size() == 0) {
            return 0;
        }
        java.util.Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            if (((com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent) it.next()).getTimestamp() >= j) {
                i2++;
            }
        }
        return i2;
    }

    @Override // com.android.server.am.BaseAppStateEvents
    void trimEvents(long j, int i) {
        java.util.LinkedList linkedList = this.mEvents[i];
        if (linkedList == null) {
            return;
        }
        while (linkedList.size() > 0 && ((com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent) linkedList.peek()).getTimestamp() < j) {
            linkedList.pop();
        }
    }

    static class BaseTimeEvent implements java.lang.Cloneable {
        long mTimestamp;

        BaseTimeEvent(long j) {
            this.mTimestamp = j;
        }

        BaseTimeEvent(com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent baseTimeEvent) {
            this.mTimestamp = baseTimeEvent.mTimestamp;
        }

        void trimTo(long j) {
            this.mTimestamp = j;
        }

        long getTimestamp() {
            return this.mTimestamp;
        }

        public java.lang.Object clone() {
            return new com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent(this);
        }

        public boolean equals(java.lang.Object obj) {
            return obj != null && obj.getClass() == com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent.class && ((com.android.server.am.BaseAppStateTimeEvents.BaseTimeEvent) obj).mTimestamp == this.mTimestamp;
        }

        public int hashCode() {
            return java.lang.Long.hashCode(this.mTimestamp);
        }
    }
}
