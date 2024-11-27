package com.android.server.am;

/* loaded from: classes.dex */
public final class LowMemDetector {
    public static final int ADJ_MEM_FACTOR_NOTHING = -1;
    private static final java.lang.String TAG = "LowMemDetector";
    private final com.android.server.am.ActivityManagerService mAm;
    private boolean mAvailable;
    private final java.lang.Object mPressureStateLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mPressureStateLock"})
    private int mPressureState = 0;
    private final com.android.server.am.LowMemDetector.LowMemThread mLowMemThread = new com.android.server.am.LowMemDetector.LowMemThread();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MemFactor {
    }

    private native int init();

    /* JADX INFO: Access modifiers changed from: private */
    public native int waitForPressure();

    LowMemDetector(com.android.server.am.ActivityManagerService activityManagerService) {
        this.mAm = activityManagerService;
        if (init() != 0) {
            this.mAvailable = false;
        } else {
            this.mAvailable = true;
            this.mLowMemThread.start();
        }
    }

    public boolean isAvailable() {
        return this.mAvailable;
    }

    public int getMemFactor() {
        int i;
        synchronized (this.mPressureStateLock) {
            i = this.mPressureState;
        }
        return i;
    }

    private final class LowMemThread extends java.lang.Thread {
        private boolean mIsTracingMemCriticalLow;

        LowMemThread() {
            super("LowMemThread");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                int waitForPressure = com.android.server.am.LowMemDetector.this.waitForPressure();
                boolean z = waitForPressure == 3;
                if (z && !this.mIsTracingMemCriticalLow) {
                    android.os.Trace.traceBegin(64L, "criticalLowMemory");
                } else if (!z && this.mIsTracingMemCriticalLow) {
                    android.os.Trace.traceEnd(64L);
                }
                this.mIsTracingMemCriticalLow = z;
                if (waitForPressure == -1) {
                    com.android.server.am.LowMemDetector.this.mAvailable = false;
                    return;
                } else {
                    synchronized (com.android.server.am.LowMemDetector.this.mPressureStateLock) {
                        com.android.server.am.LowMemDetector.this.mPressureState = waitForPressure;
                    }
                }
            }
        }
    }
}
