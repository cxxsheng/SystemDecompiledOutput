package com.android.server.am;

/* loaded from: classes.dex */
public class OomAdjProfiler {
    private static final int MSG_UPDATE_CPU_TIME = 42;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mLastScheduledOnBattery;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mLastScheduledScreenOff;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mLastSystemServerCpuTimeMs;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mOnBattery;

    @com.android.internal.annotations.GuardedBy({"this"})
    private com.android.server.am.OomAdjProfiler.CpuTimes mOomAdjRunTime;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mOomAdjStartTimeUs;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mOomAdjStarted;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mScreenOff;

    @com.android.internal.annotations.GuardedBy({"this"})
    private com.android.server.am.OomAdjProfiler.CpuTimes mSystemServerCpuTime;

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean mSystemServerCpuTimeUpdateScheduled;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mTotalOomAdjCalls;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mTotalOomAdjRunTimeUs;
    private final com.android.internal.os.ProcessCpuTracker mProcessCpuTracker = new com.android.internal.os.ProcessCpuTracker(false);

    @com.android.internal.annotations.GuardedBy({"this"})
    final com.android.internal.util.RingBuffer<com.android.server.am.OomAdjProfiler.CpuTimes> mOomAdjRunTimesHist = new com.android.internal.util.RingBuffer<>(com.android.server.am.OomAdjProfiler.CpuTimes.class, 10);

    @com.android.internal.annotations.GuardedBy({"this"})
    final com.android.internal.util.RingBuffer<com.android.server.am.OomAdjProfiler.CpuTimes> mSystemServerCpuTimesHist = new com.android.internal.util.RingBuffer<>(com.android.server.am.OomAdjProfiler.CpuTimes.class, 10);

    public OomAdjProfiler() {
        this.mOomAdjRunTime = new com.android.server.am.OomAdjProfiler.CpuTimes();
        this.mSystemServerCpuTime = new com.android.server.am.OomAdjProfiler.CpuTimes();
    }

    void batteryPowerChanged(boolean z) {
        synchronized (this) {
            scheduleSystemServerCpuTimeUpdate();
            this.mOnBattery = z;
        }
    }

    void onWakefulnessChanged(int i) {
        synchronized (this) {
            scheduleSystemServerCpuTimeUpdate();
            boolean z = true;
            if (i == 1) {
                z = false;
            }
            this.mScreenOff = z;
        }
    }

    void oomAdjStarted() {
        synchronized (this) {
            this.mOomAdjStartTimeUs = android.os.SystemClock.currentThreadTimeMicro();
            this.mOomAdjStarted = true;
        }
    }

    void oomAdjEnded() {
        synchronized (this) {
            try {
                if (this.mOomAdjStarted) {
                    long currentThreadTimeMicro = android.os.SystemClock.currentThreadTimeMicro() - this.mOomAdjStartTimeUs;
                    this.mOomAdjRunTime.addCpuTimeUs(currentThreadTimeMicro);
                    this.mTotalOomAdjRunTimeUs += currentThreadTimeMicro;
                    this.mTotalOomAdjCalls++;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void scheduleSystemServerCpuTimeUpdate() {
        synchronized (this) {
            try {
                if (this.mSystemServerCpuTimeUpdateScheduled) {
                    return;
                }
                this.mLastScheduledOnBattery = this.mOnBattery;
                this.mLastScheduledScreenOff = this.mScreenOff;
                this.mSystemServerCpuTimeUpdateScheduled = true;
                android.os.Message obtainMessage = com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.QuadConsumer() { // from class: com.android.server.am.OomAdjProfiler$$ExternalSyntheticLambda0
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                        ((com.android.server.am.OomAdjProfiler) obj).updateSystemServerCpuTime(((java.lang.Boolean) obj2).booleanValue(), ((java.lang.Boolean) obj3).booleanValue(), ((java.lang.Boolean) obj4).booleanValue());
                    }
                }, this, java.lang.Boolean.valueOf(this.mLastScheduledOnBattery), java.lang.Boolean.valueOf(this.mLastScheduledScreenOff), true);
                obtainMessage.setWhat(42);
                com.android.internal.os.BackgroundThread.getHandler().sendMessage(obtainMessage);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSystemServerCpuTime(boolean z, boolean z2, boolean z3) {
        long cpuTimeForPid = this.mProcessCpuTracker.getCpuTimeForPid(android.os.Process.myPid());
        synchronized (this) {
            if (z3) {
                try {
                    if (!this.mSystemServerCpuTimeUpdateScheduled) {
                        return;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mSystemServerCpuTime.addCpuTimeMs(cpuTimeForPid - this.mLastSystemServerCpuTimeMs, z, z2);
            this.mLastSystemServerCpuTimeMs = cpuTimeForPid;
            this.mSystemServerCpuTimeUpdateScheduled = false;
        }
    }

    void reset() {
        synchronized (this) {
            try {
                if (this.mSystemServerCpuTime.isEmpty()) {
                    return;
                }
                this.mOomAdjRunTimesHist.append(this.mOomAdjRunTime);
                this.mSystemServerCpuTimesHist.append(this.mSystemServerCpuTime);
                this.mOomAdjRunTime = new com.android.server.am.OomAdjProfiler.CpuTimes();
                this.mSystemServerCpuTime = new com.android.server.am.OomAdjProfiler.CpuTimes();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    void dump(java.io.PrintWriter printWriter) {
        synchronized (this) {
            try {
                if (this.mSystemServerCpuTimeUpdateScheduled) {
                    com.android.internal.os.BackgroundThread.getHandler().removeMessages(42);
                    updateSystemServerCpuTime(this.mLastScheduledOnBattery, this.mLastScheduledScreenOff, false);
                } else {
                    updateSystemServerCpuTime(this.mOnBattery, this.mScreenOff, false);
                }
                printWriter.println("System server and oomAdj runtimes (ms) in recent battery sessions (most recent first):");
                if (!this.mSystemServerCpuTime.isEmpty()) {
                    printWriter.print("  ");
                    printWriter.print("system_server=");
                    printWriter.print(this.mSystemServerCpuTime);
                    printWriter.print("  ");
                    printWriter.print("oom_adj=");
                    printWriter.println(this.mOomAdjRunTime);
                }
                com.android.server.am.OomAdjProfiler.CpuTimes[] cpuTimesArr = (com.android.server.am.OomAdjProfiler.CpuTimes[]) this.mSystemServerCpuTimesHist.toArray();
                com.android.server.am.OomAdjProfiler.CpuTimes[] cpuTimesArr2 = (com.android.server.am.OomAdjProfiler.CpuTimes[]) this.mOomAdjRunTimesHist.toArray();
                for (int length = cpuTimesArr2.length - 1; length >= 0; length--) {
                    printWriter.print("  ");
                    printWriter.print("system_server=");
                    printWriter.print(cpuTimesArr[length]);
                    printWriter.print("  ");
                    printWriter.print("oom_adj=");
                    printWriter.println(cpuTimesArr2[length]);
                }
                if (this.mTotalOomAdjCalls != 0) {
                    printWriter.println("System server total oomAdj runtimes (us) since boot:");
                    printWriter.print("  cpu time spent=");
                    printWriter.print(this.mTotalOomAdjRunTimeUs);
                    printWriter.print("  number of calls=");
                    printWriter.print(this.mTotalOomAdjCalls);
                    printWriter.print("  average=");
                    printWriter.println(this.mTotalOomAdjRunTimeUs / this.mTotalOomAdjCalls);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private class CpuTimes {
        private long mOnBatteryScreenOffTimeUs;
        private long mOnBatteryTimeUs;

        private CpuTimes() {
        }

        public void addCpuTimeMs(long j) {
            addCpuTimeUs(j * 1000, com.android.server.am.OomAdjProfiler.this.mOnBattery, com.android.server.am.OomAdjProfiler.this.mScreenOff);
        }

        public void addCpuTimeMs(long j, boolean z, boolean z2) {
            addCpuTimeUs(j * 1000, z, z2);
        }

        public void addCpuTimeUs(long j) {
            addCpuTimeUs(j, com.android.server.am.OomAdjProfiler.this.mOnBattery, com.android.server.am.OomAdjProfiler.this.mScreenOff);
        }

        public void addCpuTimeUs(long j, boolean z, boolean z2) {
            if (z) {
                this.mOnBatteryTimeUs += j;
                if (z2) {
                    this.mOnBatteryScreenOffTimeUs += j;
                }
            }
        }

        public boolean isEmpty() {
            return this.mOnBatteryTimeUs == 0 && this.mOnBatteryScreenOffTimeUs == 0;
        }

        public java.lang.String toString() {
            return "[" + (this.mOnBatteryTimeUs / 1000) + "," + (this.mOnBatteryScreenOffTimeUs / 1000) + "]";
        }
    }
}
