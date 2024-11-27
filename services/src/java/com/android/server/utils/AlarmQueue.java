package com.android.server.utils;

/* loaded from: classes2.dex */
public abstract class AlarmQueue<K> implements android.app.AlarmManager.OnAlarmListener {
    private static final boolean DEBUG = false;
    private static final long NOT_SCHEDULED = -1;
    private static final long SIGNIFICANT_TRIGGER_TIME_CHANGE_THRESHOLD_MS = 60000;
    private static final java.lang.String TAG = com.android.server.utils.AlarmQueue.class.getSimpleName();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final com.android.server.utils.AlarmQueue.AlarmPriorityQueue<K> mAlarmPriorityQueue;
    private final java.lang.String mAlarmTag;
    private final android.content.Context mContext;
    private final java.lang.String mDumpTitle;
    private final boolean mExactAlarm;
    private final android.os.Handler mHandler;
    private final com.android.server.utils.AlarmQueue.Injector mInjector;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mLastFireTimeElapsed;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mMinTimeBetweenAlarmsMs;
    private final java.lang.Runnable mScheduleAlarmRunnable;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private long mTriggerTimeElapsed;

    protected abstract boolean isForUser(@android.annotation.NonNull K k, int i);

    protected abstract void processExpiredAlarms(@android.annotation.NonNull android.util.ArraySet<K> arraySet);

    /* JADX INFO: Access modifiers changed from: private */
    static class AlarmPriorityQueue<Q> extends java.util.PriorityQueue<android.util.Pair<Q, java.lang.Long>> {
        private static final java.util.Comparator<android.util.Pair<?, java.lang.Long>> sTimeComparator = new java.util.Comparator() { // from class: com.android.server.utils.AlarmQueue$AlarmPriorityQueue$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$static$0;
                lambda$static$0 = com.android.server.utils.AlarmQueue.AlarmPriorityQueue.lambda$static$0((android.util.Pair) obj, (android.util.Pair) obj2);
                return lambda$static$0;
            }
        };

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$static$0(android.util.Pair pair, android.util.Pair pair2) {
            return java.lang.Long.compare(((java.lang.Long) pair.second).longValue(), ((java.lang.Long) pair2.second).longValue());
        }

        AlarmPriorityQueue() {
            super(1, sTimeComparator);
        }

        public boolean removeKey(@android.annotation.NonNull Q q) {
            android.util.Pair[] pairArr = (android.util.Pair[]) toArray(new android.util.Pair[size()]);
            boolean z = false;
            for (int length = pairArr.length - 1; length >= 0; length--) {
                if (q.equals(pairArr[length].first)) {
                    remove(pairArr[length]);
                    z = true;
                }
            }
            return z;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        long getElapsedRealtime() {
            return android.os.SystemClock.elapsedRealtime();
        }
    }

    public AlarmQueue(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Looper looper, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, boolean z, long j) {
        this(context, looper, str, str2, z, j, new com.android.server.utils.AlarmQueue.Injector());
    }

    @com.android.internal.annotations.VisibleForTesting
    AlarmQueue(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull android.os.Looper looper, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, boolean z, long j, @android.annotation.NonNull com.android.server.utils.AlarmQueue.Injector injector) {
        this.mScheduleAlarmRunnable = new java.lang.Runnable() { // from class: com.android.server.utils.AlarmQueue.1
            @Override // java.lang.Runnable
            public void run() {
                com.android.server.utils.AlarmQueue.this.mHandler.removeCallbacks(this);
                android.app.AlarmManager alarmManager = (android.app.AlarmManager) com.android.server.utils.AlarmQueue.this.mContext.getSystemService(android.app.AlarmManager.class);
                if (alarmManager == null) {
                    com.android.server.utils.AlarmQueue.this.mHandler.postDelayed(this, 30000L);
                    return;
                }
                synchronized (com.android.server.utils.AlarmQueue.this.mLock) {
                    try {
                        if (com.android.server.utils.AlarmQueue.this.mTriggerTimeElapsed == -1) {
                            return;
                        }
                        long j2 = com.android.server.utils.AlarmQueue.this.mTriggerTimeElapsed;
                        long j3 = com.android.server.utils.AlarmQueue.this.mMinTimeBetweenAlarmsMs;
                        if (com.android.server.utils.AlarmQueue.this.mExactAlarm) {
                            alarmManager.setExact(3, j2, com.android.server.utils.AlarmQueue.this.mAlarmTag, com.android.server.utils.AlarmQueue.this, com.android.server.utils.AlarmQueue.this.mHandler);
                        } else {
                            alarmManager.setWindow(3, j2, j3 / 2, com.android.server.utils.AlarmQueue.this.mAlarmTag, com.android.server.utils.AlarmQueue.this, com.android.server.utils.AlarmQueue.this.mHandler);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mLock = new java.lang.Object();
        this.mAlarmPriorityQueue = new com.android.server.utils.AlarmQueue.AlarmPriorityQueue<>();
        this.mTriggerTimeElapsed = -1L;
        this.mContext = context;
        this.mAlarmTag = str;
        this.mDumpTitle = str2.trim();
        this.mExactAlarm = z;
        this.mHandler = new android.os.Handler(looper);
        this.mInjector = injector;
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("min time between alarms must be non-negative");
        }
        this.mMinTimeBetweenAlarmsMs = j;
    }

    public void addAlarm(K k, long j) {
        synchronized (this.mLock) {
            try {
                boolean removeKey = this.mAlarmPriorityQueue.removeKey(k);
                this.mAlarmPriorityQueue.offer(new android.util.Pair(k, java.lang.Long.valueOf(j)));
                if (this.mTriggerTimeElapsed == -1 || removeKey || j < this.mTriggerTimeElapsed) {
                    setNextAlarmLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public long getMinTimeBetweenAlarmsMs() {
        long j;
        synchronized (this.mLock) {
            j = this.mMinTimeBetweenAlarmsMs;
        }
        return j;
    }

    public void removeAlarmForKey(K k) {
        synchronized (this.mLock) {
            try {
                if (this.mAlarmPriorityQueue.removeKey(k)) {
                    setNextAlarmLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void removeAlarmsForUserId(int i) {
        synchronized (this.mLock) {
            try {
                android.util.Pair[] pairArr = (android.util.Pair[]) this.mAlarmPriorityQueue.toArray(new android.util.Pair[this.mAlarmPriorityQueue.size()]);
                boolean z = false;
                for (int length = pairArr.length - 1; length >= 0; length--) {
                    if (isForUser(pairArr[length].first, i)) {
                        this.mAlarmPriorityQueue.remove(pairArr[length]);
                        z = true;
                    }
                }
                if (z) {
                    setNextAlarmLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void removeAllAlarms() {
        synchronized (this.mLock) {
            this.mAlarmPriorityQueue.clear();
            setNextAlarmLocked(0L);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void removeAlarmsIf(@android.annotation.NonNull java.util.function.Predicate<K> predicate) {
        synchronized (this.mLock) {
            try {
                android.util.Pair[] pairArr = (android.util.Pair[]) this.mAlarmPriorityQueue.toArray(new android.util.Pair[this.mAlarmPriorityQueue.size()]);
                boolean z = false;
                for (int length = pairArr.length - 1; length >= 0; length--) {
                    if (predicate.test(pairArr[length].first)) {
                        this.mAlarmPriorityQueue.remove(pairArr[length]);
                        z = true;
                    }
                }
                if (z) {
                    setNextAlarmLocked();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void setMinTimeBetweenAlarmsMs(long j) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("min time between alarms must be non-negative");
        }
        synchronized (this.mLock) {
            this.mMinTimeBetweenAlarmsMs = j;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setNextAlarmLocked() {
        setNextAlarmLocked(this.mLastFireTimeElapsed + this.mMinTimeBetweenAlarmsMs);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void setNextAlarmLocked(long j) {
        if (this.mAlarmPriorityQueue.size() == 0) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.utils.AlarmQueue$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.utils.AlarmQueue.this.lambda$setNextAlarmLocked$0();
                }
            });
            this.mTriggerTimeElapsed = -1L;
            return;
        }
        long max = java.lang.Math.max(j, ((java.lang.Long) this.mAlarmPriorityQueue.peek().second).longValue());
        long min = java.lang.Math.min(60000L, this.mMinTimeBetweenAlarmsMs);
        if (this.mTriggerTimeElapsed == -1 || max < this.mTriggerTimeElapsed - min || this.mTriggerTimeElapsed < max) {
            this.mTriggerTimeElapsed = max;
            this.mHandler.post(this.mScheduleAlarmRunnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setNextAlarmLocked$0() {
        android.app.AlarmManager alarmManager = (android.app.AlarmManager) this.mContext.getSystemService(android.app.AlarmManager.class);
        if (alarmManager != null) {
            alarmManager.cancel(this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.app.AlarmManager.OnAlarmListener
    public void onAlarm() {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        synchronized (this.mLock) {
            try {
                long elapsedRealtime = this.mInjector.getElapsedRealtime();
                this.mLastFireTimeElapsed = elapsedRealtime;
                while (this.mAlarmPriorityQueue.size() > 0) {
                    android.util.Pair peek = this.mAlarmPriorityQueue.peek();
                    if (((java.lang.Long) peek.second).longValue() > elapsedRealtime) {
                        break;
                    }
                    arraySet.add(peek.first);
                    this.mAlarmPriorityQueue.remove(peek);
                }
                setNextAlarmLocked(elapsedRealtime + this.mMinTimeBetweenAlarmsMs);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (arraySet.size() > 0) {
            processExpiredAlarms(arraySet);
        }
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.print(this.mDumpTitle);
                indentingPrintWriter.println(" alarms:");
                indentingPrintWriter.increaseIndent();
                if (this.mAlarmPriorityQueue.size() == 0) {
                    indentingPrintWriter.println("NOT WAITING");
                } else {
                    android.util.Pair[] pairArr = (android.util.Pair[]) this.mAlarmPriorityQueue.toArray(new android.util.Pair[this.mAlarmPriorityQueue.size()]);
                    for (int i = 0; i < pairArr.length; i++) {
                        indentingPrintWriter.print(pairArr[i].first);
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.print(pairArr[i].second);
                        indentingPrintWriter.println();
                    }
                }
                indentingPrintWriter.decreaseIndent();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
