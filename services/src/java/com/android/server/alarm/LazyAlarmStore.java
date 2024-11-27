package com.android.server.alarm;

/* loaded from: classes.dex */
public class LazyAlarmStore implements com.android.server.alarm.AlarmStore {
    private static final long ALARM_DEADLINE_SLOP = 500;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String TAG = com.android.server.alarm.LazyAlarmStore.class.getSimpleName();
    private static final java.util.Comparator<com.android.server.alarm.Alarm> sDecreasingTimeOrder = java.util.Comparator.comparingLong(new java.util.function.ToLongFunction() { // from class: com.android.server.alarm.LazyAlarmStore$$ExternalSyntheticLambda0
        @Override // java.util.function.ToLongFunction
        public final long applyAsLong(java.lang.Object obj) {
            return ((com.android.server.alarm.Alarm) obj).getWhenElapsed();
        }
    }).reversed();
    private java.lang.Runnable mOnAlarmClockRemoved;
    private final java.util.ArrayList<com.android.server.alarm.Alarm> mAlarms = new java.util.ArrayList<>();
    final com.android.internal.util.jobs.StatLogger mStatLogger = new com.android.internal.util.jobs.StatLogger(TAG + " stats", new java.lang.String[]{"GET_NEXT_DELIVERY_TIME", "GET_NEXT_WAKEUP_DELIVERY_TIME", "GET_COUNT"});

    interface Stats {
        public static final int GET_COUNT = 2;
        public static final int GET_NEXT_DELIVERY_TIME = 0;
        public static final int GET_NEXT_WAKEUP_DELIVERY_TIME = 1;
    }

    @Override // com.android.server.alarm.AlarmStore
    public void add(com.android.server.alarm.Alarm alarm) {
        int binarySearch = java.util.Collections.binarySearch(this.mAlarms, alarm, sDecreasingTimeOrder);
        if (binarySearch < 0) {
            binarySearch = (0 - binarySearch) - 1;
        }
        this.mAlarms.add(binarySearch, alarm);
    }

    @Override // com.android.server.alarm.AlarmStore
    public void addAll(java.util.ArrayList<com.android.server.alarm.Alarm> arrayList) {
        if (arrayList == null) {
            return;
        }
        this.mAlarms.addAll(arrayList);
        java.util.Collections.sort(this.mAlarms, sDecreasingTimeOrder);
    }

    @Override // com.android.server.alarm.AlarmStore
    public java.util.ArrayList<com.android.server.alarm.Alarm> remove(java.util.function.Predicate<com.android.server.alarm.Alarm> predicate) {
        java.util.ArrayList<com.android.server.alarm.Alarm> arrayList = new java.util.ArrayList<>();
        for (int size = this.mAlarms.size() - 1; size >= 0; size--) {
            if (predicate.test(this.mAlarms.get(size))) {
                com.android.server.alarm.Alarm remove = this.mAlarms.remove(size);
                if (remove.alarmClock != null && this.mOnAlarmClockRemoved != null) {
                    this.mOnAlarmClockRemoved.run();
                }
                if (com.android.server.alarm.AlarmManagerService.isTimeTickAlarm(remove)) {
                    android.util.Slog.wtf(TAG, "Removed TIME_TICK alarm");
                }
                arrayList.add(remove);
            }
        }
        return arrayList;
    }

    @Override // com.android.server.alarm.AlarmStore
    public void setAlarmClockRemovalListener(java.lang.Runnable runnable) {
        this.mOnAlarmClockRemoved = runnable;
    }

    @Override // com.android.server.alarm.AlarmStore
    public com.android.server.alarm.Alarm getNextWakeFromIdleAlarm() {
        for (int size = this.mAlarms.size() - 1; size >= 0; size--) {
            com.android.server.alarm.Alarm alarm = this.mAlarms.get(size);
            if ((alarm.flags & 2) != 0) {
                return alarm;
            }
        }
        return null;
    }

    @Override // com.android.server.alarm.AlarmStore
    public int size() {
        return this.mAlarms.size();
    }

    @Override // com.android.server.alarm.AlarmStore
    public long getNextWakeupDeliveryTime() {
        long time = this.mStatLogger.getTime();
        long j = 0;
        for (int size = this.mAlarms.size() - 1; size >= 0; size--) {
            com.android.server.alarm.Alarm alarm = this.mAlarms.get(size);
            if (alarm.wakeup) {
                if (j == 0) {
                    j = alarm.getMaxWhenElapsed();
                } else {
                    if (alarm.getWhenElapsed() > j) {
                        break;
                    }
                    j = java.lang.Math.min(j, alarm.getMaxWhenElapsed());
                }
            }
        }
        this.mStatLogger.logDurationStat(1, time);
        return j;
    }

    @Override // com.android.server.alarm.AlarmStore
    public long getNextDeliveryTime() {
        long time = this.mStatLogger.getTime();
        int size = this.mAlarms.size();
        if (size == 0) {
            return 0L;
        }
        long maxWhenElapsed = this.mAlarms.get(size - 1).getMaxWhenElapsed();
        for (int i = size - 2; i >= 0; i--) {
            com.android.server.alarm.Alarm alarm = this.mAlarms.get(i);
            if (alarm.getWhenElapsed() > maxWhenElapsed) {
                break;
            }
            maxWhenElapsed = java.lang.Math.min(maxWhenElapsed, alarm.getMaxWhenElapsed());
        }
        this.mStatLogger.logDurationStat(0, time);
        return maxWhenElapsed;
    }

    @Override // com.android.server.alarm.AlarmStore
    public java.util.ArrayList<com.android.server.alarm.Alarm> removePendingAlarms(long j) {
        java.util.ArrayList<com.android.server.alarm.Alarm> arrayList = new java.util.ArrayList<>();
        boolean z = false;
        boolean z2 = false;
        for (int size = this.mAlarms.size() - 1; size >= 0; size--) {
            com.android.server.alarm.Alarm alarm = this.mAlarms.get(size);
            if (alarm.getWhenElapsed() > j) {
                break;
            }
            this.mAlarms.remove(size);
            arrayList.add(alarm);
            if (alarm.wakeup && alarm.getMaxWhenElapsed() <= 500 + j) {
                z = true;
            }
            if ((alarm.flags & 1) != 0) {
                z2 = true;
            }
        }
        java.util.ArrayList<com.android.server.alarm.Alarm> arrayList2 = new java.util.ArrayList<>();
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            com.android.server.alarm.Alarm alarm2 = arrayList.get(size2);
            if ((z || !alarm2.wakeup) && (!z2 || (alarm2.flags & 1) != 0)) {
                arrayList.remove(size2);
                arrayList2.add(alarm2);
            }
        }
        addAll(arrayList);
        return arrayList2;
    }

    @Override // com.android.server.alarm.AlarmStore
    public boolean updateAlarmDeliveries(com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator alarmDeliveryCalculator) {
        java.util.Iterator<com.android.server.alarm.Alarm> it = this.mAlarms.iterator();
        boolean z = false;
        while (it.hasNext()) {
            z |= alarmDeliveryCalculator.updateAlarmDelivery(it.next());
        }
        if (z) {
            java.util.Collections.sort(this.mAlarms, sDecreasingTimeOrder);
        }
        return z;
    }

    @Override // com.android.server.alarm.AlarmStore
    public java.util.ArrayList<com.android.server.alarm.Alarm> asList() {
        java.util.ArrayList<com.android.server.alarm.Alarm> arrayList = new java.util.ArrayList<>(this.mAlarms);
        java.util.Collections.reverse(arrayList);
        return arrayList;
    }

    @Override // com.android.server.alarm.AlarmStore
    public void dump(android.util.IndentingPrintWriter indentingPrintWriter, long j, java.text.SimpleDateFormat simpleDateFormat) {
        indentingPrintWriter.println(this.mAlarms.size() + " pending alarms: ");
        indentingPrintWriter.increaseIndent();
        com.android.server.alarm.AlarmManagerService.dumpAlarmList(indentingPrintWriter, this.mAlarms, j, simpleDateFormat);
        indentingPrintWriter.decreaseIndent();
        this.mStatLogger.dump(indentingPrintWriter);
    }

    @Override // com.android.server.alarm.AlarmStore
    public void dumpProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        java.util.Iterator<com.android.server.alarm.Alarm> it = this.mAlarms.iterator();
        while (it.hasNext()) {
            it.next().dumpDebug(protoOutputStream, 2246267895850L, j);
        }
    }

    @Override // com.android.server.alarm.AlarmStore
    public java.lang.String getName() {
        return TAG;
    }

    @Override // com.android.server.alarm.AlarmStore
    public int getCount(java.util.function.Predicate<com.android.server.alarm.Alarm> predicate) {
        long time = this.mStatLogger.getTime();
        java.util.Iterator<com.android.server.alarm.Alarm> it = this.mAlarms.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                i++;
            }
        }
        this.mStatLogger.logDurationStat(2, time);
        return i;
    }
}
