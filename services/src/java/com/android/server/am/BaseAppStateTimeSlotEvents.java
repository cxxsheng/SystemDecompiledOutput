package com.android.server.am;

/* loaded from: classes.dex */
class BaseAppStateTimeSlotEvents extends com.android.server.am.BaseAppStateEvents<java.lang.Integer> {
    static final boolean DEBUG_BASE_APP_TIME_SLOT_EVENTS = false;
    long[] mCurSlotStartTime;
    final long mTimeSlotSize;

    BaseAppStateTimeSlotEvents(int i, @android.annotation.NonNull java.lang.String str, int i2, long j, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull com.android.server.am.BaseAppStateEvents.MaxTrackingDurationConfig maxTrackingDurationConfig) {
        super(i, str, i2, str2, maxTrackingDurationConfig);
        this.mTimeSlotSize = j;
        this.mCurSlotStartTime = new long[i2];
    }

    BaseAppStateTimeSlotEvents(@android.annotation.NonNull com.android.server.am.BaseAppStateTimeSlotEvents baseAppStateTimeSlotEvents) {
        super(baseAppStateTimeSlotEvents);
        this.mTimeSlotSize = baseAppStateTimeSlotEvents.mTimeSlotSize;
        this.mCurSlotStartTime = new long[baseAppStateTimeSlotEvents.mCurSlotStartTime.length];
        for (int i = 0; i < this.mCurSlotStartTime.length; i++) {
            this.mCurSlotStartTime[i] = baseAppStateTimeSlotEvents.mCurSlotStartTime[i];
        }
    }

    @Override // com.android.server.am.BaseAppStateEvents
    java.util.LinkedList<java.lang.Integer> add(java.util.LinkedList<java.lang.Integer> linkedList, java.util.LinkedList<java.lang.Integer> linkedList2) {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.server.am.BaseAppStateEvents
    void add(com.android.server.am.BaseAppStateEvents baseAppStateEvents) {
        int i;
        if (baseAppStateEvents == null || !(baseAppStateEvents instanceof com.android.server.am.BaseAppStateTimeSlotEvents)) {
            return;
        }
        com.android.server.am.BaseAppStateTimeSlotEvents baseAppStateTimeSlotEvents = (com.android.server.am.BaseAppStateTimeSlotEvents) baseAppStateEvents;
        if (this.mEvents.length != baseAppStateTimeSlotEvents.mEvents.length) {
            return;
        }
        int i2 = 0;
        while (i2 < this.mEvents.length) {
            java.util.LinkedList linkedList = baseAppStateTimeSlotEvents.mEvents[i2];
            if (linkedList == null) {
                i = i2;
            } else if (linkedList.size() == 0) {
                i = i2;
            } else {
                java.util.LinkedList linkedList2 = this.mEvents[i2];
                if (linkedList2 == null) {
                    i = i2;
                } else if (linkedList2.size() == 0) {
                    i = i2;
                } else {
                    java.util.LinkedList linkedList3 = new java.util.LinkedList();
                    java.util.Iterator it = linkedList2.iterator();
                    java.util.Iterator it2 = linkedList.iterator();
                    long j = this.mCurSlotStartTime[i2];
                    long j2 = baseAppStateTimeSlotEvents.mCurSlotStartTime[i2];
                    int i3 = i2;
                    long size = j - (this.mTimeSlotSize * (linkedList2.size() - 1));
                    long size2 = j2 - (this.mTimeSlotSize * (linkedList.size() - 1));
                    long max = java.lang.Math.max(j, j2);
                    long min = java.lang.Math.min(size, size2);
                    while (min <= max) {
                        linkedList3.add(java.lang.Integer.valueOf(((min < size || min > j) ? 0 : ((java.lang.Integer) it.next()).intValue()) + ((min < size2 || min > j2) ? 0 : ((java.lang.Integer) it2.next()).intValue())));
                        min += this.mTimeSlotSize;
                        size = size;
                    }
                    this.mEvents[i3] = linkedList3;
                    if (j < j2) {
                        this.mCurSlotStartTime[i3] = baseAppStateTimeSlotEvents.mCurSlotStartTime[i3];
                    }
                    i = i3;
                    trimEvents(getEarliest(this.mCurSlotStartTime[i3]), i);
                }
                this.mEvents[i] = new java.util.LinkedList(linkedList);
                this.mCurSlotStartTime[i] = baseAppStateTimeSlotEvents.mCurSlotStartTime[i];
            }
            i2 = i + 1;
        }
    }

    @Override // com.android.server.am.BaseAppStateEvents
    int getTotalEventsSince(long j, long j2, int i) {
        java.util.LinkedList linkedList = this.mEvents[i];
        int i2 = 0;
        if (linkedList == null || linkedList.size() == 0) {
            return 0;
        }
        long slotStartTime = getSlotStartTime(j);
        if (slotStartTime > this.mCurSlotStartTime[i]) {
            return 0;
        }
        long min = java.lang.Math.min(getSlotStartTime(j2), this.mCurSlotStartTime[i]);
        java.util.Iterator descendingIterator = linkedList.descendingIterator();
        long j3 = this.mCurSlotStartTime[i];
        while (j3 >= slotStartTime && descendingIterator.hasNext()) {
            int intValue = ((java.lang.Integer) descendingIterator.next()).intValue();
            if (j3 <= min) {
                i2 += intValue;
            }
            j3 -= this.mTimeSlotSize;
        }
        return i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    void addEvent(long j, int i) {
        long slotStartTime = getSlotStartTime(j);
        java.util.LinkedList linkedList = this.mEvents[i];
        if (linkedList == null) {
            linkedList = new java.util.LinkedList();
            this.mEvents[i] = linkedList;
        }
        if (linkedList.size() == 0) {
            linkedList.add(1);
        } else {
            long j2 = this.mCurSlotStartTime[i];
            while (j2 < slotStartTime) {
                linkedList.add(0);
                j2 += this.mTimeSlotSize;
            }
            linkedList.offerLast(java.lang.Integer.valueOf(((java.lang.Integer) linkedList.pollLast()).intValue() + 1));
        }
        this.mCurSlotStartTime[i] = slotStartTime;
        trimEvents(getEarliest(j), i);
    }

    @Override // com.android.server.am.BaseAppStateEvents
    void trimEvents(long j, int i) {
        java.util.LinkedList linkedList = this.mEvents[i];
        if (linkedList == null || linkedList.size() == 0) {
            return;
        }
        long slotStartTime = getSlotStartTime(j);
        long size = this.mCurSlotStartTime[i] - (this.mTimeSlotSize * (linkedList.size() - 1));
        while (size < slotStartTime && linkedList.size() > 0) {
            linkedList.pop();
            size += this.mTimeSlotSize;
        }
    }

    long getSlotStartTime(long j) {
        return j - (j % this.mTimeSlotSize);
    }

    @com.android.internal.annotations.VisibleForTesting
    long getCurrentSlotStartTime(int i) {
        return this.mCurSlotStartTime[i];
    }
}
