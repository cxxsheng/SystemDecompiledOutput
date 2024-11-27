package com.android.server.appop;

/* loaded from: classes.dex */
class AppOpsUidStateTrackerImpl implements com.android.server.appop.AppOpsUidStateTracker {
    private static final java.lang.String LOG_TAG = com.android.server.appop.AppOpsUidStateTrackerImpl.class.getSimpleName();
    private android.app.ActivityManagerInternal mActivityManagerInternal;
    private android.util.SparseBooleanArray mAppWidgetVisible;
    private android.util.SparseIntArray mCapability;
    private final com.android.internal.os.Clock mClock;
    private com.android.server.appop.AppOpsService.Constants mConstants;
    private final com.android.server.appop.AppOpsUidStateTrackerImpl.EventLog mEventLog;
    private final com.android.server.appop.AppOpsUidStateTrackerImpl.DelayableExecutor mExecutor;
    private android.util.SparseBooleanArray mPendingAppWidgetVisible;
    private android.util.SparseIntArray mPendingCapability;
    private android.util.SparseLongArray mPendingCommitTime;
    private android.util.SparseBooleanArray mPendingGone;
    private android.util.SparseIntArray mPendingUidStates;
    private android.util.ArrayMap<com.android.server.appop.AppOpsUidStateTracker.UidStateChangedCallback, java.util.concurrent.Executor> mUidStateChangedCallbacks;
    private android.util.SparseIntArray mUidStates;

    @com.android.internal.annotations.VisibleForTesting
    interface DelayableExecutor extends java.util.concurrent.Executor {
        @Override // java.util.concurrent.Executor
        void execute(java.lang.Runnable runnable);

        void executeDelayed(java.lang.Runnable runnable, long j);
    }

    /* renamed from: com.android.server.appop.AppOpsUidStateTrackerImpl$1, reason: invalid class name */
    class AnonymousClass1 implements com.android.server.appop.AppOpsUidStateTrackerImpl.DelayableExecutor {
        final /* synthetic */ android.os.Handler val$handler;
        final /* synthetic */ java.util.concurrent.Executor val$lockingExecutor;

        AnonymousClass1(android.os.Handler handler, java.util.concurrent.Executor executor) {
            this.val$handler = handler;
            this.val$lockingExecutor = executor;
        }

        @Override // com.android.server.appop.AppOpsUidStateTrackerImpl.DelayableExecutor, java.util.concurrent.Executor
        public void execute(final java.lang.Runnable runnable) {
            android.os.Handler handler = this.val$handler;
            final java.util.concurrent.Executor executor = this.val$lockingExecutor;
            handler.post(new java.lang.Runnable() { // from class: com.android.server.appop.AppOpsUidStateTrackerImpl$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    executor.execute(runnable);
                }
            });
        }

        @Override // com.android.server.appop.AppOpsUidStateTrackerImpl.DelayableExecutor
        public void executeDelayed(final java.lang.Runnable runnable, long j) {
            android.os.Handler handler = this.val$handler;
            final java.util.concurrent.Executor executor = this.val$lockingExecutor;
            handler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.appop.AppOpsUidStateTrackerImpl$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    executor.execute(runnable);
                }
            }, j);
        }
    }

    AppOpsUidStateTrackerImpl(android.app.ActivityManagerInternal activityManagerInternal, android.os.Handler handler, java.util.concurrent.Executor executor, com.android.internal.os.Clock clock, com.android.server.appop.AppOpsService.Constants constants) {
        this(activityManagerInternal, new com.android.server.appop.AppOpsUidStateTrackerImpl.AnonymousClass1(handler, executor), clock, constants, handler.getLooper().getThread());
    }

    @com.android.internal.annotations.VisibleForTesting
    AppOpsUidStateTrackerImpl(android.app.ActivityManagerInternal activityManagerInternal, com.android.server.appop.AppOpsUidStateTrackerImpl.DelayableExecutor delayableExecutor, com.android.internal.os.Clock clock, com.android.server.appop.AppOpsService.Constants constants, java.lang.Thread thread) {
        this.mUidStates = new android.util.SparseIntArray();
        this.mPendingUidStates = new android.util.SparseIntArray();
        this.mCapability = new android.util.SparseIntArray();
        this.mPendingCapability = new android.util.SparseIntArray();
        this.mAppWidgetVisible = new android.util.SparseBooleanArray();
        this.mPendingAppWidgetVisible = new android.util.SparseBooleanArray();
        this.mPendingCommitTime = new android.util.SparseLongArray();
        this.mPendingGone = new android.util.SparseBooleanArray();
        this.mUidStateChangedCallbacks = new android.util.ArrayMap<>();
        this.mActivityManagerInternal = activityManagerInternal;
        this.mExecutor = delayableExecutor;
        this.mClock = clock;
        this.mConstants = constants;
        this.mEventLog = new com.android.server.appop.AppOpsUidStateTrackerImpl.EventLog(delayableExecutor, thread);
    }

    @Override // com.android.server.appop.AppOpsUidStateTracker
    public int getUidState(int i) {
        return getUidStateLocked(i);
    }

    private int getUidStateLocked(int i) {
        updateUidPendingStateIfNeeded(i);
        return this.mUidStates.get(i, com.android.server.am.ProcessList.PREVIOUS_APP_ADJ);
    }

    @Override // com.android.server.appop.AppOpsUidStateTracker
    public int evalMode(int i, int i2, int i3) {
        if (i3 != 4) {
            return i3;
        }
        int uidState = getUidState(i);
        int uidCapability = getUidCapability(i);
        int evalModeInternal = evalModeInternal(i, i2, uidState, uidCapability);
        this.mEventLog.logEvalForegroundMode(i, uidState, uidCapability, i2, evalModeInternal);
        return evalModeInternal;
    }

    private int evalModeInternal(int i, int i2, int i3, int i4) {
        if (getUidAppWidgetVisible(i) || this.mActivityManagerInternal.isPendingTopUid(i) || this.mActivityManagerInternal.isTempAllowlistedForFgsWhileInUse(i)) {
            return 0;
        }
        int opCapability = getOpCapability(i2);
        return opCapability != 0 ? (opCapability & i4) == 0 ? 1 : 0 : i3 > android.app.AppOpsManager.resolveFirstUnrestrictedUidState(i2) ? 1 : 0;
    }

    private int getOpCapability(int i) {
        switch (i) {
            case 0:
            case 1:
            case 41:
            case 42:
                return 1;
            case 26:
                return 2;
            case 27:
            case 121:
                return 4;
            case 32:
                return 64;
            default:
                return 0;
        }
    }

    @Override // com.android.server.appop.AppOpsUidStateTracker
    public boolean isUidInForeground(int i) {
        return evalMode(i, -1, 4) == 0;
    }

    @Override // com.android.server.appop.AppOpsUidStateTracker
    public void addUidStateChangedCallback(java.util.concurrent.Executor executor, com.android.server.appop.AppOpsUidStateTracker.UidStateChangedCallback uidStateChangedCallback) {
        if (this.mUidStateChangedCallbacks.containsKey(uidStateChangedCallback)) {
            throw new java.lang.IllegalStateException("Callback is already registered.");
        }
        this.mUidStateChangedCallbacks.put(uidStateChangedCallback, executor);
    }

    @Override // com.android.server.appop.AppOpsUidStateTracker
    public void removeUidStateChangedCallback(com.android.server.appop.AppOpsUidStateTracker.UidStateChangedCallback uidStateChangedCallback) {
        if (!this.mUidStateChangedCallbacks.containsKey(uidStateChangedCallback)) {
            throw new java.lang.IllegalStateException("Callback is not registered.");
        }
        this.mUidStateChangedCallbacks.remove(uidStateChangedCallback);
    }

    @Override // com.android.server.appop.AppOpsUidStateTracker
    public void updateAppWidgetVisibility(android.util.SparseArray<java.lang.String> sparseArray, boolean z) {
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int keyAt = sparseArray.keyAt(i);
            this.mPendingAppWidgetVisible.put(keyAt, z);
            commitUidPendingState(keyAt);
        }
    }

    @Override // com.android.server.appop.AppOpsUidStateTracker
    public void updateUidProcState(int i, int i2, int i3) {
        long j;
        int processStateToUidState = com.android.server.appop.AppOpsUidStateTracker.processStateToUidState(i2);
        int i4 = this.mUidStates.get(i, com.android.server.am.ProcessList.PREVIOUS_APP_ADJ);
        int i5 = this.mCapability.get(i, 0);
        int i6 = this.mPendingUidStates.get(i, com.android.server.am.ProcessList.PREVIOUS_APP_ADJ);
        int i7 = this.mPendingCapability.get(i, 0);
        long j2 = this.mPendingCommitTime.get(i, 0L);
        if (j2 != 0 || (processStateToUidState == i4 && i3 == i5)) {
            if (j2 == 0) {
                return;
            }
            if (processStateToUidState == i6 && i3 == i7) {
                return;
            }
        }
        this.mEventLog.logUpdateUidProcState(i, i2, i3);
        this.mPendingUidStates.put(i, processStateToUidState);
        this.mPendingCapability.put(i, i3);
        if (i2 == 20) {
            this.mPendingGone.put(i, true);
            commitUidPendingState(i);
            return;
        }
        if (processStateToUidState < i4 || (processStateToUidState <= 500 && i4 > 500)) {
            commitUidPendingState(i);
            return;
        }
        if (processStateToUidState == i4 && i3 != i5) {
            commitUidPendingState(i);
            return;
        }
        if (processStateToUidState <= 500) {
            commitUidPendingState(i);
            return;
        }
        if (j2 == 0) {
            if (i4 <= 200) {
                j = this.mConstants.TOP_STATE_SETTLE_TIME;
            } else if (i4 <= 400) {
                j = this.mConstants.FG_SERVICE_STATE_SETTLE_TIME;
            } else {
                j = this.mConstants.BG_STATE_SETTLE_TIME;
            }
            this.mPendingCommitTime.put(i, this.mClock.elapsedRealtime() + j);
            this.mExecutor.executeDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.BiConsumer() { // from class: com.android.server.appop.AppOpsUidStateTrackerImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((com.android.server.appop.AppOpsUidStateTrackerImpl) obj).updateUidPendingStateIfNeeded(((java.lang.Integer) obj2).intValue());
                }
            }, this, java.lang.Integer.valueOf(i)), j + 1);
        }
    }

    @Override // com.android.server.appop.AppOpsUidStateTracker
    public void dumpUidState(java.io.PrintWriter printWriter, int i, long j) {
        int i2 = this.mUidStates.get(i, com.android.server.am.ProcessList.PREVIOUS_APP_ADJ);
        int i3 = this.mPendingUidStates.get(i, i2);
        printWriter.print("    state=");
        printWriter.println(android.app.AppOpsManager.getUidStateName(i2));
        if (i2 != i3) {
            printWriter.print("    pendingState=");
            printWriter.println(android.app.AppOpsManager.getUidStateName(i3));
        }
        int i4 = this.mCapability.get(i, 0);
        int i5 = this.mPendingCapability.get(i, i4);
        printWriter.print("    capability=");
        android.app.ActivityManager.printCapabilitiesFull(printWriter, i4);
        printWriter.println();
        if (i4 != i5) {
            printWriter.print("    pendingCapability=");
            android.app.ActivityManager.printCapabilitiesFull(printWriter, i5);
            printWriter.println();
        }
        boolean z = this.mAppWidgetVisible.get(i, false);
        boolean z2 = this.mPendingAppWidgetVisible.get(i, z);
        printWriter.print("    appWidgetVisible=");
        printWriter.println(z);
        if (z != z2) {
            printWriter.print("    pendingAppWidgetVisible=");
            printWriter.println(z2);
        }
        long j2 = this.mPendingCommitTime.get(i, 0L);
        if (j2 != 0) {
            printWriter.print("    pendingStateCommitTime=");
            android.util.TimeUtils.formatDuration(j2, j, printWriter);
            printWriter.println();
        }
    }

    @Override // com.android.server.appop.AppOpsUidStateTracker
    public void dumpEvents(java.io.PrintWriter printWriter) {
        this.mEventLog.dumpEvents(printWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUidPendingStateIfNeeded(int i) {
        updateUidPendingStateIfNeededLocked(i);
    }

    private void updateUidPendingStateIfNeededLocked(int i) {
        if (this.mPendingCommitTime.get(i, 0L) == 0 || this.mClock.elapsedRealtime() < this.mPendingCommitTime.get(i)) {
            return;
        }
        commitUidPendingState(i);
    }

    private void commitUidPendingState(int i) {
        int i2 = this.mPendingUidStates.get(i, this.mUidStates.get(i, com.android.server.am.ProcessList.PREVIOUS_APP_ADJ));
        int i3 = this.mPendingCapability.get(i, this.mCapability.get(i, 0));
        boolean z = this.mPendingAppWidgetVisible.get(i, this.mAppWidgetVisible.get(i, false));
        int i4 = this.mUidStates.get(i, com.android.server.am.ProcessList.PREVIOUS_APP_ADJ);
        int i5 = this.mCapability.get(i, 0);
        boolean z2 = this.mAppWidgetVisible.get(i, false);
        if (i4 != i2 || i5 != i3 || z2 != z) {
            boolean z3 = ((i4 <= 500) == (i2 <= 500) && i5 == i3 && z2 == z) ? false : true;
            if (z3) {
                this.mEventLog.logCommitUidState(i, i2, i3, z, z2 != z);
            }
            for (int i6 = 0; i6 < this.mUidStateChangedCallbacks.size(); i6++) {
                this.mUidStateChangedCallbacks.valueAt(i6).execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.QuadConsumer() { // from class: com.android.server.appop.AppOpsUidStateTrackerImpl$$ExternalSyntheticLambda1
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4) {
                        ((com.android.server.appop.AppOpsUidStateTracker.UidStateChangedCallback) obj).onUidStateChanged(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue(), ((java.lang.Boolean) obj4).booleanValue());
                    }
                }, this.mUidStateChangedCallbacks.keyAt(i6), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Boolean.valueOf(z3)));
            }
        }
        if (this.mPendingGone.get(i, false)) {
            this.mUidStates.delete(i);
            this.mCapability.delete(i);
            this.mAppWidgetVisible.delete(i);
            this.mPendingGone.delete(i);
        } else {
            this.mUidStates.put(i, i2);
            this.mCapability.put(i, i3);
            this.mAppWidgetVisible.put(i, z);
        }
        this.mPendingUidStates.delete(i);
        this.mPendingCapability.delete(i);
        this.mPendingAppWidgetVisible.delete(i);
        this.mPendingCommitTime.delete(i);
    }

    private int getUidCapability(int i) {
        return this.mCapability.get(i, 0);
    }

    private boolean getUidAppWidgetVisible(int i) {
        return this.mAppWidgetVisible.get(i, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class EventLog {
        private static final int APP_WIDGET_VISIBLE = 1;
        private static final int APP_WIDGET_VISIBLE_CHANGED = 2;
        private static final int COMMIT_UID_STATE_LOG_MAX_SIZE = 200;
        private static final int EVAL_FOREGROUND_MODE_MAX_SIZE = 200;
        private static final int UPDATE_UID_PROC_STATE_LOG_MAX_SIZE = 200;
        private final com.android.server.appop.AppOpsUidStateTrackerImpl.DelayableExecutor mExecutor;
        private final java.lang.Thread mExecutorThread;
        private int[][] mUpdateUidProcStateLog = (int[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Integer.TYPE, 200, 3);
        private long[] mUpdateUidProcStateLogTimestamps = new long[200];
        private int mUpdateUidProcStateLogSize = 0;
        private int mUpdateUidProcStateLogHead = 0;
        private int[][] mCommitUidStateLog = (int[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Integer.TYPE, 200, 4);
        private long[] mCommitUidStateLogTimestamps = new long[200];
        private int mCommitUidStateLogSize = 0;
        private int mCommitUidStateLogHead = 0;
        private int[][] mEvalForegroundModeLog = (int[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Integer.TYPE, 200, 5);
        private long[] mEvalForegroundModeLogTimestamps = new long[200];
        private int mEvalForegroundModeLogSize = 0;
        private int mEvalForegroundModeLogHead = 0;

        EventLog(com.android.server.appop.AppOpsUidStateTrackerImpl.DelayableExecutor delayableExecutor, java.lang.Thread thread) {
            this.mExecutor = delayableExecutor;
            this.mExecutorThread = thread;
        }

        void logUpdateUidProcState(int i, int i2, int i3) {
            this.mExecutor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.QuintConsumer() { // from class: com.android.server.appop.AppOpsUidStateTrackerImpl$EventLog$$ExternalSyntheticLambda2
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5) {
                    ((com.android.server.appop.AppOpsUidStateTrackerImpl.EventLog) obj).logUpdateUidProcStateAsync(((java.lang.Long) obj2).longValue(), ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), ((java.lang.Integer) obj5).intValue());
                }
            }, this, java.lang.Long.valueOf(java.lang.System.currentTimeMillis()), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3)));
        }

        void logUpdateUidProcStateAsync(long j, int i, int i2, int i3) {
            int i4 = (this.mUpdateUidProcStateLogHead + this.mUpdateUidProcStateLogSize) % 200;
            if (this.mUpdateUidProcStateLogSize == 200) {
                this.mUpdateUidProcStateLogHead = (this.mUpdateUidProcStateLogHead + 1) % 200;
            } else {
                this.mUpdateUidProcStateLogSize++;
            }
            this.mUpdateUidProcStateLog[i4][0] = i;
            this.mUpdateUidProcStateLog[i4][1] = i2;
            this.mUpdateUidProcStateLog[i4][2] = i3;
            this.mUpdateUidProcStateLogTimestamps[i4] = j;
        }

        void logCommitUidState(int i, int i2, int i3, boolean z, boolean z2) {
            this.mExecutor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.HeptConsumer() { // from class: com.android.server.appop.AppOpsUidStateTrackerImpl$EventLog$$ExternalSyntheticLambda0
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7) {
                    ((com.android.server.appop.AppOpsUidStateTrackerImpl.EventLog) obj).logCommitUidStateAsync(((java.lang.Long) obj2).longValue(), ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), ((java.lang.Integer) obj5).intValue(), ((java.lang.Boolean) obj6).booleanValue(), ((java.lang.Boolean) obj7).booleanValue());
                }
            }, this, java.lang.Long.valueOf(java.lang.System.currentTimeMillis()), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2)));
        }

        void logCommitUidStateAsync(long j, int i, int i2, int i3, boolean z, boolean z2) {
            int i4 = (this.mCommitUidStateLogHead + this.mCommitUidStateLogSize) % 200;
            if (this.mCommitUidStateLogSize == 200) {
                this.mCommitUidStateLogHead = (this.mCommitUidStateLogHead + 1) % 200;
            } else {
                this.mCommitUidStateLogSize++;
            }
            this.mCommitUidStateLog[i4][0] = i;
            this.mCommitUidStateLog[i4][1] = i2;
            this.mCommitUidStateLog[i4][2] = i3;
            this.mCommitUidStateLog[i4][3] = 0;
            if (z) {
                int[] iArr = this.mCommitUidStateLog[i4];
                iArr[3] = iArr[3] + 1;
            }
            if (z2) {
                int[] iArr2 = this.mCommitUidStateLog[i4];
                iArr2[3] = iArr2[3] + 2;
            }
            this.mCommitUidStateLogTimestamps[i4] = j;
        }

        void logEvalForegroundMode(int i, int i2, int i3, int i4, int i5) {
            this.mExecutor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new com.android.internal.util.function.HeptConsumer() { // from class: com.android.server.appop.AppOpsUidStateTrackerImpl$EventLog$$ExternalSyntheticLambda1
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3, java.lang.Object obj4, java.lang.Object obj5, java.lang.Object obj6, java.lang.Object obj7) {
                    ((com.android.server.appop.AppOpsUidStateTrackerImpl.EventLog) obj).logEvalForegroundModeAsync(((java.lang.Long) obj2).longValue(), ((java.lang.Integer) obj3).intValue(), ((java.lang.Integer) obj4).intValue(), ((java.lang.Integer) obj5).intValue(), ((java.lang.Integer) obj6).intValue(), ((java.lang.Integer) obj7).intValue());
                }
            }, this, java.lang.Long.valueOf(java.lang.System.currentTimeMillis()), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i4), java.lang.Integer.valueOf(i5)));
        }

        void logEvalForegroundModeAsync(long j, int i, int i2, int i3, int i4, int i5) {
            int i6 = (this.mEvalForegroundModeLogHead + this.mEvalForegroundModeLogSize) % 200;
            if (this.mEvalForegroundModeLogSize == 200) {
                this.mEvalForegroundModeLogHead = (this.mEvalForegroundModeLogHead + 1) % 200;
            } else {
                this.mEvalForegroundModeLogSize++;
            }
            this.mEvalForegroundModeLog[i6][0] = i;
            this.mEvalForegroundModeLog[i6][1] = i2;
            this.mEvalForegroundModeLog[i6][2] = i3;
            this.mEvalForegroundModeLog[i6][3] = i4;
            this.mEvalForegroundModeLog[i6][4] = i5;
            this.mEvalForegroundModeLogTimestamps[i6] = j;
        }

        void dumpEvents(java.io.PrintWriter printWriter) {
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (i < this.mUpdateUidProcStateLogSize || i2 < this.mCommitUidStateLogSize || i3 < this.mEvalForegroundModeLogSize) {
                    int i4 = (this.mUpdateUidProcStateLogHead + i) % 200;
                    int i5 = (this.mCommitUidStateLogHead + i2) % 200;
                    int i6 = (this.mEvalForegroundModeLogHead + i3) % 200;
                    int i7 = this.mUpdateUidProcStateLogSize;
                    long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                    long j2 = i < i7 ? this.mUpdateUidProcStateLogTimestamps[i4] : Long.MAX_VALUE;
                    long j3 = i2 < this.mCommitUidStateLogSize ? this.mCommitUidStateLogTimestamps[i5] : Long.MAX_VALUE;
                    if (i3 < this.mEvalForegroundModeLogSize) {
                        j = this.mEvalForegroundModeLogTimestamps[i6];
                    }
                    if (j2 <= j3 && j2 <= j) {
                        dumpUpdateUidProcState(printWriter, i4);
                        i++;
                    } else if (j3 <= j) {
                        dumpCommitUidState(printWriter, i5);
                        i2++;
                    } else {
                        dumpEvalForegroundMode(printWriter, i6);
                        i3++;
                    }
                } else {
                    return;
                }
            }
        }

        void dumpUpdateUidProcState(java.io.PrintWriter printWriter, int i) {
            long j = this.mUpdateUidProcStateLogTimestamps[i];
            int i2 = this.mUpdateUidProcStateLog[i][0];
            int i3 = this.mUpdateUidProcStateLog[i][1];
            int i4 = this.mUpdateUidProcStateLog[i][2];
            android.util.TimeUtils.dumpTime(printWriter, j);
            printWriter.print(" UPDATE_UID_PROC_STATE");
            printWriter.print(" uid=");
            printWriter.print(java.lang.String.format("%-8d", java.lang.Integer.valueOf(i2)));
            printWriter.print(" procState=");
            printWriter.print(java.lang.String.format("%-30s", android.app.ActivityManager.procStateToString(i3)));
            printWriter.print(" capability=");
            printWriter.print(android.app.ActivityManager.getCapabilitiesSummary(i4) + " ");
            printWriter.println();
        }

        void dumpCommitUidState(java.io.PrintWriter printWriter, int i) {
            long j = this.mCommitUidStateLogTimestamps[i];
            int i2 = this.mCommitUidStateLog[i][0];
            int i3 = this.mCommitUidStateLog[i][1];
            int i4 = this.mCommitUidStateLog[i][2];
            boolean z = (this.mCommitUidStateLog[i][3] & 1) != 0;
            boolean z2 = (this.mCommitUidStateLog[i][3] & 2) != 0;
            android.util.TimeUtils.dumpTime(printWriter, j);
            printWriter.print(" COMMIT_UID_STATE     ");
            printWriter.print(" uid=");
            printWriter.print(java.lang.String.format("%-8d", java.lang.Integer.valueOf(i2)));
            printWriter.print(" uidState=");
            printWriter.print(java.lang.String.format("%-30s", android.app.AppOpsManager.uidStateToString(i3)));
            printWriter.print(" capability=");
            printWriter.print(android.app.ActivityManager.getCapabilitiesSummary(i4) + " ");
            printWriter.print(" appWidgetVisible=");
            printWriter.print(z);
            if (z2) {
                printWriter.print(" (changed)");
            }
            printWriter.println();
        }

        void dumpEvalForegroundMode(java.io.PrintWriter printWriter, int i) {
            long j = this.mEvalForegroundModeLogTimestamps[i];
            int i2 = this.mEvalForegroundModeLog[i][0];
            int i3 = this.mEvalForegroundModeLog[i][1];
            int i4 = this.mEvalForegroundModeLog[i][2];
            int i5 = this.mEvalForegroundModeLog[i][3];
            int i6 = this.mEvalForegroundModeLog[i][4];
            android.util.TimeUtils.dumpTime(printWriter, j);
            printWriter.print(" EVAL_FOREGROUND_MODE ");
            printWriter.print(" uid=");
            printWriter.print(java.lang.String.format("%-8d", java.lang.Integer.valueOf(i2)));
            printWriter.print(" uidState=");
            printWriter.print(java.lang.String.format("%-30s", android.app.AppOpsManager.uidStateToString(i3)));
            printWriter.print(" capability=");
            printWriter.print(android.app.ActivityManager.getCapabilitiesSummary(i4) + " ");
            printWriter.print(" code=");
            printWriter.print(java.lang.String.format("%-20s", android.app.AppOpsManager.opToName(i5)));
            printWriter.print(" result=");
            printWriter.print(android.app.AppOpsManager.modeToName(i6));
            printWriter.println();
        }
    }
}
