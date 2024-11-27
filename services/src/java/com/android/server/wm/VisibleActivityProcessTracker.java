package com.android.server.wm;

/* loaded from: classes3.dex */
class VisibleActivityProcessTracker {
    final com.android.server.wm.ActivityTaskManagerService mAtms;

    @com.android.internal.annotations.GuardedBy({"mProcMap"})
    private final android.util.ArrayMap<com.android.server.wm.WindowProcessController, com.android.server.wm.VisibleActivityProcessTracker.CpuTimeRecord> mProcMap = new android.util.ArrayMap<>();
    final java.util.concurrent.Executor mBgExecutor = com.android.internal.os.BackgroundThread.getExecutor();

    VisibleActivityProcessTracker(com.android.server.wm.ActivityTaskManagerService activityTaskManagerService) {
        this.mAtms = activityTaskManagerService;
    }

    void onAnyActivityVisible(com.android.server.wm.WindowProcessController windowProcessController) {
        com.android.server.wm.VisibleActivityProcessTracker.CpuTimeRecord cpuTimeRecord = new com.android.server.wm.VisibleActivityProcessTracker.CpuTimeRecord(windowProcessController);
        synchronized (this.mProcMap) {
            this.mProcMap.put(windowProcessController, cpuTimeRecord);
        }
        if (windowProcessController.hasResumedActivity()) {
            cpuTimeRecord.mShouldGetCpuTime = true;
            this.mBgExecutor.execute(cpuTimeRecord);
        }
    }

    void onAllActivitiesInvisible(com.android.server.wm.WindowProcessController windowProcessController) {
        com.android.server.wm.VisibleActivityProcessTracker.CpuTimeRecord removeProcess = removeProcess(windowProcessController);
        if (removeProcess != null && removeProcess.mShouldGetCpuTime) {
            this.mBgExecutor.execute(removeProcess);
        }
    }

    void onActivityResumedWhileVisible(com.android.server.wm.WindowProcessController windowProcessController) {
        com.android.server.wm.VisibleActivityProcessTracker.CpuTimeRecord cpuTimeRecord;
        synchronized (this.mProcMap) {
            cpuTimeRecord = this.mProcMap.get(windowProcessController);
        }
        if (cpuTimeRecord != null && !cpuTimeRecord.mShouldGetCpuTime) {
            cpuTimeRecord.mShouldGetCpuTime = true;
            this.mBgExecutor.execute(cpuTimeRecord);
        }
    }

    boolean hasResumedActivity(int i) {
        return match(i, new java.util.function.Predicate() { // from class: com.android.server.wm.VisibleActivityProcessTracker$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return ((com.android.server.wm.WindowProcessController) obj).hasResumedActivity();
            }
        });
    }

    boolean hasVisibleActivity(int i) {
        return match(i, null);
    }

    private boolean match(int i, java.util.function.Predicate<com.android.server.wm.WindowProcessController> predicate) {
        synchronized (this.mProcMap) {
            try {
                for (int size = this.mProcMap.size() - 1; size >= 0; size--) {
                    com.android.server.wm.WindowProcessController keyAt = this.mProcMap.keyAt(size);
                    if (keyAt.mUid == i && (predicate == null || predicate.test(keyAt))) {
                        return true;
                    }
                }
                return false;
            } finally {
            }
        }
    }

    com.android.server.wm.VisibleActivityProcessTracker.CpuTimeRecord removeProcess(com.android.server.wm.WindowProcessController windowProcessController) {
        com.android.server.wm.VisibleActivityProcessTracker.CpuTimeRecord remove;
        synchronized (this.mProcMap) {
            remove = this.mProcMap.remove(windowProcessController);
        }
        return remove;
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str + "VisibleActivityProcess:[");
        synchronized (this.mProcMap) {
            try {
                for (int size = this.mProcMap.size() - 1; size >= 0; size += -1) {
                    printWriter.print(" " + this.mProcMap.keyAt(size));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        printWriter.println("]");
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CpuTimeRecord implements java.lang.Runnable {
        private long mCpuTime;
        private boolean mHasStartCpuTime;
        private final com.android.server.wm.WindowProcessController mProc;
        boolean mShouldGetCpuTime;

        CpuTimeRecord(com.android.server.wm.WindowProcessController windowProcessController) {
            this.mProc = windowProcessController;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mProc.getPid() == 0) {
                return;
            }
            if (!this.mHasStartCpuTime) {
                this.mHasStartCpuTime = true;
                this.mCpuTime = this.mProc.getCpuTime();
            } else {
                long cpuTime = this.mProc.getCpuTime() - this.mCpuTime;
                if (cpuTime > 0) {
                    com.android.server.wm.VisibleActivityProcessTracker.this.mAtms.mAmInternal.updateForegroundTimeIfOnBattery(this.mProc.mInfo.packageName, this.mProc.mInfo.uid, cpuTime);
                }
            }
        }
    }
}
