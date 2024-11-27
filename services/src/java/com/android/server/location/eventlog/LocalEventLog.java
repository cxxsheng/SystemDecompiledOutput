package com.android.server.location.eventlog;

/* loaded from: classes2.dex */
public class LocalEventLog<T> {
    private static final int IS_FILLER_MASK = Integer.MIN_VALUE;
    private static final int TIME_DELTA_MASK = Integer.MAX_VALUE;

    @com.android.internal.annotations.GuardedBy({"this"})
    final int[] mEntries;

    @com.android.internal.annotations.GuardedBy({"this"})
    long mLastLogTime;

    @com.android.internal.annotations.GuardedBy({"this"})
    int mLogEndIndex;

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    final T[] mLogEvents;

    @com.android.internal.annotations.GuardedBy({"this"})
    int mLogSize;

    @com.android.internal.annotations.GuardedBy({"this"})
    long mModificationCount;

    @com.android.internal.annotations.GuardedBy({"this"})
    long mStartTime;
    private static final int IS_FILLER_OFFSET = countTrailingZeros(Integer.MIN_VALUE);
    private static final int TIME_DELTA_OFFSET = countTrailingZeros(Integer.MAX_VALUE);

    @com.android.internal.annotations.VisibleForTesting
    static final int MAX_TIME_DELTA = (1 << java.lang.Integer.bitCount(Integer.MAX_VALUE)) - 1;

    public interface LogConsumer<T> {
        void acceptLog(long j, T t);
    }

    private static int countTrailingZeros(int i) {
        int i2 = 0;
        while (i != 0 && (i & 1) == 0) {
            i2++;
            i >>>= 1;
        }
        return i2;
    }

    private static int createEntry(boolean z, int i) {
        com.android.internal.util.Preconditions.checkArgument(i >= 0 && i <= MAX_TIME_DELTA);
        return (((z ? 1 : 0) << IS_FILLER_OFFSET) & Integer.MIN_VALUE) | ((i << TIME_DELTA_OFFSET) & Integer.MAX_VALUE);
    }

    static int getTimeDelta(int i) {
        return (i & Integer.MAX_VALUE) >>> TIME_DELTA_OFFSET;
    }

    static boolean isFiller(int i) {
        return (i & Integer.MIN_VALUE) != 0;
    }

    public LocalEventLog(int i, java.lang.Class<T> cls) {
        com.android.internal.util.Preconditions.checkArgument(i > 0);
        this.mEntries = new int[i];
        this.mLogEvents = (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, i));
        this.mLogSize = 0;
        this.mLogEndIndex = 0;
        this.mStartTime = -1L;
        this.mLastLogTime = -1L;
    }

    protected synchronized void addLog(long j, T t) {
        try {
            com.android.internal.util.Preconditions.checkArgument(t != null);
            long j2 = 0;
            if (!isEmpty()) {
                long j3 = j - this.mLastLogTime;
                if (j3 < 0 || j3 / MAX_TIME_DELTA >= this.mEntries.length - 1) {
                    clear();
                } else {
                    j2 = j3;
                    while (j2 >= MAX_TIME_DELTA) {
                        addLogEventInternal(true, MAX_TIME_DELTA, null);
                        j2 -= MAX_TIME_DELTA;
                    }
                }
            }
            if (isEmpty()) {
                this.mStartTime = j;
                this.mLastLogTime = this.mStartTime;
                this.mModificationCount++;
            }
            addLogEventInternal(false, (int) j2, t);
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void addLogEventInternal(boolean z, int i, @android.annotation.Nullable T t) {
        boolean z2 = false;
        com.android.internal.util.Preconditions.checkArgument(z || t != null);
        if (this.mStartTime != -1 && this.mLastLogTime != -1) {
            z2 = true;
        }
        com.android.internal.util.Preconditions.checkState(z2);
        if (this.mLogSize == this.mEntries.length) {
            this.mStartTime += getTimeDelta(this.mEntries[startIndex()]);
            this.mModificationCount++;
        } else {
            this.mLogSize++;
        }
        this.mEntries[this.mLogEndIndex] = createEntry(z, i);
        this.mLogEvents[this.mLogEndIndex] = t;
        this.mLogEndIndex = incrementIndex(this.mLogEndIndex);
        this.mLastLogTime += i;
    }

    public synchronized void clear() {
        java.util.Arrays.fill(this.mLogEvents, (java.lang.Object) null);
        this.mLogEndIndex = 0;
        this.mLogSize = 0;
        this.mModificationCount++;
        this.mStartTime = -1L;
        this.mLastLogTime = -1L;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean isEmpty() {
        return this.mLogSize == 0;
    }

    public synchronized void iterate(com.android.server.location.eventlog.LocalEventLog.LogConsumer<? super T> logConsumer) {
        com.android.server.location.eventlog.LocalEventLog.LogIterator logIterator = new com.android.server.location.eventlog.LocalEventLog.LogIterator();
        while (logIterator.hasNext()) {
            logIterator.next();
            logConsumer.acceptLog(logIterator.getTime(), (java.lang.Object) logIterator.getLog());
        }
    }

    @java.lang.SafeVarargs
    public static <T> void iterate(com.android.server.location.eventlog.LocalEventLog.LogConsumer<? super T> logConsumer, com.android.server.location.eventlog.LocalEventLog<T>... localEventLogArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList(localEventLogArr.length);
        for (com.android.server.location.eventlog.LocalEventLog<T> localEventLog : localEventLogArr) {
            java.util.Objects.requireNonNull(localEventLog);
            com.android.server.location.eventlog.LocalEventLog.LogIterator logIterator = localEventLog.new LogIterator();
            if (logIterator.hasNext()) {
                arrayList.add(logIterator);
                logIterator.next();
            }
        }
        while (true) {
            java.util.Iterator it = arrayList.iterator();
            com.android.server.location.eventlog.LocalEventLog.LogIterator logIterator2 = null;
            while (it.hasNext()) {
                com.android.server.location.eventlog.LocalEventLog.LogIterator logIterator3 = (com.android.server.location.eventlog.LocalEventLog.LogIterator) it.next();
                if (logIterator3 != null && (logIterator2 == null || logIterator3.getTime() < logIterator2.getTime())) {
                    logIterator2 = logIterator3;
                }
            }
            if (logIterator2 == null) {
                return;
            }
            logConsumer.acceptLog(logIterator2.getTime(), (java.lang.Object) logIterator2.getLog());
            if (logIterator2.hasNext()) {
                logIterator2.next();
            } else {
                arrayList.remove(logIterator2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    int startIndex() {
        return wrapIndex(this.mLogEndIndex - this.mLogSize);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    int incrementIndex(int i) {
        if (i == -1) {
            return startIndex();
        }
        if (i >= 0) {
            return wrapIndex(i + 1);
        }
        throw new java.lang.IllegalArgumentException();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    int wrapIndex(int i) {
        return ((i % this.mEntries.length) + this.mEntries.length) % this.mEntries.length;
    }

    protected final class LogIterator {
        private int mCount;
        private T mCurrentLogEvent;
        private long mCurrentTime;
        private int mIndex;
        private long mLogTime;
        private final long mModificationCount;

        public LogIterator() {
            synchronized (com.android.server.location.eventlog.LocalEventLog.this) {
                this.mModificationCount = com.android.server.location.eventlog.LocalEventLog.this.mModificationCount;
                this.mLogTime = com.android.server.location.eventlog.LocalEventLog.this.mStartTime;
                this.mIndex = -1;
                this.mCount = -1;
                increment();
            }
        }

        public boolean hasNext() {
            boolean z;
            synchronized (com.android.server.location.eventlog.LocalEventLog.this) {
                checkModifications();
                z = this.mCount < com.android.server.location.eventlog.LocalEventLog.this.mLogSize;
            }
            return z;
        }

        public void next() {
            synchronized (com.android.server.location.eventlog.LocalEventLog.this) {
                try {
                    if (!hasNext()) {
                        throw new java.util.NoSuchElementException();
                    }
                    this.mCurrentTime = this.mLogTime + com.android.server.location.eventlog.LocalEventLog.getTimeDelta(com.android.server.location.eventlog.LocalEventLog.this.mEntries[this.mIndex]);
                    T t = com.android.server.location.eventlog.LocalEventLog.this.mLogEvents[this.mIndex];
                    java.util.Objects.requireNonNull(t);
                    this.mCurrentLogEvent = t;
                    increment();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public long getTime() {
            return this.mCurrentTime;
        }

        public T getLog() {
            return this.mCurrentLogEvent;
        }

        @com.android.internal.annotations.GuardedBy({"LocalEventLog.this"})
        private void increment() {
            long timeDelta = this.mIndex == -1 ? 0L : com.android.server.location.eventlog.LocalEventLog.getTimeDelta(com.android.server.location.eventlog.LocalEventLog.this.mEntries[this.mIndex]);
            do {
                this.mLogTime += timeDelta;
                this.mIndex = com.android.server.location.eventlog.LocalEventLog.this.incrementIndex(this.mIndex);
                int i = this.mCount + 1;
                this.mCount = i;
                if (i < com.android.server.location.eventlog.LocalEventLog.this.mLogSize) {
                    timeDelta = com.android.server.location.eventlog.LocalEventLog.getTimeDelta(com.android.server.location.eventlog.LocalEventLog.this.mEntries[this.mIndex]);
                }
                if (this.mCount >= com.android.server.location.eventlog.LocalEventLog.this.mLogSize) {
                    return;
                }
            } while (com.android.server.location.eventlog.LocalEventLog.isFiller(com.android.server.location.eventlog.LocalEventLog.this.mEntries[this.mIndex]));
        }

        @com.android.internal.annotations.GuardedBy({"LocalEventLog.this"})
        private void checkModifications() {
            if (this.mModificationCount != com.android.server.location.eventlog.LocalEventLog.this.mModificationCount) {
                throw new java.util.ConcurrentModificationException();
            }
        }
    }
}
