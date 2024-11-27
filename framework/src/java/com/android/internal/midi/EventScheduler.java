package com.android.internal.midi;

/* loaded from: classes4.dex */
public class EventScheduler {
    public static final long NANOS_PER_MILLI = 1000000;
    private boolean mClosed;
    private final java.lang.Object mLock = new java.lang.Object();
    protected com.android.internal.midi.EventScheduler.FastEventQueue mEventPool = null;
    private int mMaxPoolSize = 200;
    protected volatile java.util.SortedMap<java.lang.Long, com.android.internal.midi.EventScheduler.FastEventQueue> mEventBuffer = new java.util.TreeMap();

    public static class FastEventQueue {
        volatile long mEventsAdded = 1;
        volatile long mEventsRemoved = 0;
        volatile com.android.internal.midi.EventScheduler.SchedulableEvent mFirst;
        volatile com.android.internal.midi.EventScheduler.SchedulableEvent mLast;

        public FastEventQueue(com.android.internal.midi.EventScheduler.SchedulableEvent schedulableEvent) {
            this.mFirst = schedulableEvent;
            this.mLast = this.mFirst;
        }

        int size() {
            return (int) (this.mEventsAdded - this.mEventsRemoved);
        }

        public com.android.internal.midi.EventScheduler.SchedulableEvent remove() {
            this.mEventsRemoved++;
            com.android.internal.midi.EventScheduler.SchedulableEvent schedulableEvent = this.mFirst;
            this.mFirst = schedulableEvent.mNext;
            schedulableEvent.mNext = null;
            return schedulableEvent;
        }

        public void add(com.android.internal.midi.EventScheduler.SchedulableEvent schedulableEvent) {
            schedulableEvent.mNext = null;
            this.mLast.mNext = schedulableEvent;
            this.mLast = schedulableEvent;
            this.mEventsAdded++;
        }
    }

    public static class SchedulableEvent {
        private volatile com.android.internal.midi.EventScheduler.SchedulableEvent mNext = null;
        private long mTimestamp;

        public SchedulableEvent(long j) {
            this.mTimestamp = j;
        }

        public long getTimestamp() {
            return this.mTimestamp;
        }

        public void setTimestamp(long j) {
            this.mTimestamp = j;
        }
    }

    public com.android.internal.midi.EventScheduler.SchedulableEvent removeEventfromPool() {
        if (this.mEventPool != null && this.mEventPool.size() > 1) {
            return this.mEventPool.remove();
        }
        return null;
    }

    public void addEventToPool(com.android.internal.midi.EventScheduler.SchedulableEvent schedulableEvent) {
        if (this.mEventPool == null) {
            this.mEventPool = new com.android.internal.midi.EventScheduler.FastEventQueue(schedulableEvent);
        } else if (this.mEventPool.size() < this.mMaxPoolSize) {
            this.mEventPool.add(schedulableEvent);
        }
    }

    public void add(com.android.internal.midi.EventScheduler.SchedulableEvent schedulableEvent) {
        java.lang.Object lock = getLock();
        synchronized (lock) {
            com.android.internal.midi.EventScheduler.FastEventQueue fastEventQueue = this.mEventBuffer.get(java.lang.Long.valueOf(schedulableEvent.getTimestamp()));
            if (fastEventQueue == null) {
                long longValue = this.mEventBuffer.isEmpty() ? Long.MAX_VALUE : this.mEventBuffer.firstKey().longValue();
                this.mEventBuffer.put(java.lang.Long.valueOf(schedulableEvent.getTimestamp()), new com.android.internal.midi.EventScheduler.FastEventQueue(schedulableEvent));
                if (schedulableEvent.getTimestamp() < longValue) {
                    lock.notify();
                }
            } else {
                fastEventQueue.add(schedulableEvent);
            }
        }
    }

    protected com.android.internal.midi.EventScheduler.SchedulableEvent removeNextEventLocked(long j) {
        com.android.internal.midi.EventScheduler.FastEventQueue fastEventQueue = this.mEventBuffer.get(java.lang.Long.valueOf(j));
        if (fastEventQueue.size() == 1) {
            this.mEventBuffer.remove(java.lang.Long.valueOf(j));
        }
        return fastEventQueue.remove();
    }

    public com.android.internal.midi.EventScheduler.SchedulableEvent getNextEvent(long j) {
        com.android.internal.midi.EventScheduler.SchedulableEvent schedulableEvent;
        synchronized (getLock()) {
            if (!this.mEventBuffer.isEmpty()) {
                long longValue = this.mEventBuffer.firstKey().longValue();
                if (longValue <= j) {
                    schedulableEvent = removeNextEventLocked(longValue);
                }
            }
            schedulableEvent = null;
        }
        return schedulableEvent;
    }

    public com.android.internal.midi.EventScheduler.SchedulableEvent waitNextEvent() throws java.lang.InterruptedException {
        com.android.internal.midi.EventScheduler.SchedulableEvent schedulableEvent;
        java.lang.Object lock = getLock();
        synchronized (lock) {
            while (true) {
                if (this.mClosed) {
                    schedulableEvent = null;
                    break;
                }
                long j = 2147483647L;
                if (!this.mEventBuffer.isEmpty()) {
                    long nanoTime = java.lang.System.nanoTime();
                    long longValue = this.mEventBuffer.firstKey().longValue();
                    if (longValue <= nanoTime) {
                        schedulableEvent = removeNextEventLocked(longValue);
                        break;
                    }
                    long j2 = ((longValue - nanoTime) / 1000000) + 1;
                    if (j2 <= 2147483647L) {
                        j = j2;
                    }
                }
                lock.wait((int) j);
            }
        }
        return schedulableEvent;
    }

    protected void flush() {
        this.mEventBuffer = new java.util.TreeMap();
    }

    public void close() {
        java.lang.Object lock = getLock();
        synchronized (lock) {
            this.mClosed = true;
            lock.notify();
        }
    }

    protected java.lang.Object getLock() {
        return this.mLock;
    }
}
