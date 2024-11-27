package com.android.internal.os;

/* loaded from: classes4.dex */
public class CachedDeviceState {
    private volatile boolean mCharging;
    private final java.util.ArrayList<com.android.internal.os.CachedDeviceState.TimeInStateStopwatch> mOnBatteryStopwatches;
    private volatile boolean mScreenInteractive;
    private final java.lang.Object mStopwatchesLock;

    public CachedDeviceState() {
        this.mStopwatchesLock = new java.lang.Object();
        this.mOnBatteryStopwatches = new java.util.ArrayList<>();
        this.mCharging = true;
        this.mScreenInteractive = false;
    }

    public CachedDeviceState(boolean z, boolean z2) {
        this.mStopwatchesLock = new java.lang.Object();
        this.mOnBatteryStopwatches = new java.util.ArrayList<>();
        this.mCharging = z;
        this.mScreenInteractive = z2;
    }

    public void setScreenInteractive(boolean z) {
        this.mScreenInteractive = z;
    }

    public void setCharging(boolean z) {
        if (this.mCharging != z) {
            this.mCharging = z;
            updateStopwatches(!z);
        }
    }

    private void updateStopwatches(boolean z) {
        synchronized (this.mStopwatchesLock) {
            int size = this.mOnBatteryStopwatches.size();
            for (int i = 0; i < size; i++) {
                if (z) {
                    this.mOnBatteryStopwatches.get(i).start();
                } else {
                    this.mOnBatteryStopwatches.get(i).stop();
                }
            }
        }
    }

    public com.android.internal.os.CachedDeviceState.Readonly getReadonlyClient() {
        return new com.android.internal.os.CachedDeviceState.Readonly();
    }

    public class Readonly {
        public Readonly() {
        }

        public boolean isCharging() {
            return com.android.internal.os.CachedDeviceState.this.mCharging;
        }

        public boolean isScreenInteractive() {
            return com.android.internal.os.CachedDeviceState.this.mScreenInteractive;
        }

        public com.android.internal.os.CachedDeviceState.TimeInStateStopwatch createTimeOnBatteryStopwatch() {
            com.android.internal.os.CachedDeviceState.TimeInStateStopwatch timeInStateStopwatch;
            synchronized (com.android.internal.os.CachedDeviceState.this.mStopwatchesLock) {
                timeInStateStopwatch = com.android.internal.os.CachedDeviceState.this.new TimeInStateStopwatch();
                com.android.internal.os.CachedDeviceState.this.mOnBatteryStopwatches.add(timeInStateStopwatch);
                if (!com.android.internal.os.CachedDeviceState.this.mCharging) {
                    timeInStateStopwatch.start();
                }
            }
            return timeInStateStopwatch;
        }
    }

    public class TimeInStateStopwatch implements java.lang.AutoCloseable {
        private final java.lang.Object mLock = new java.lang.Object();
        private long mStartTimeMillis;
        private long mTotalTimeMillis;

        public TimeInStateStopwatch() {
        }

        public long getMillis() {
            long elapsedTime;
            synchronized (this.mLock) {
                elapsedTime = this.mTotalTimeMillis + elapsedTime();
            }
            return elapsedTime;
        }

        public void reset() {
            synchronized (this.mLock) {
                this.mTotalTimeMillis = 0L;
                this.mStartTimeMillis = isRunning() ? android.os.SystemClock.elapsedRealtime() : 0L;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void start() {
            synchronized (this.mLock) {
                if (!isRunning()) {
                    this.mStartTimeMillis = android.os.SystemClock.elapsedRealtime();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void stop() {
            synchronized (this.mLock) {
                if (isRunning()) {
                    this.mTotalTimeMillis += elapsedTime();
                    this.mStartTimeMillis = 0L;
                }
            }
        }

        private long elapsedTime() {
            if (isRunning()) {
                return android.os.SystemClock.elapsedRealtime() - this.mStartTimeMillis;
            }
            return 0L;
        }

        public boolean isRunning() {
            return this.mStartTimeMillis > 0;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            synchronized (com.android.internal.os.CachedDeviceState.this.mStopwatchesLock) {
                com.android.internal.os.CachedDeviceState.this.mOnBatteryStopwatches.remove(this);
            }
        }
    }
}
