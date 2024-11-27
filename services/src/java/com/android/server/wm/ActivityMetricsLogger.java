package com.android.server.wm;

/* loaded from: classes3.dex */
class ActivityMetricsLogger {
    private static final int IGNORE_CALLER = -1;
    private static final long LATENCY_TRACKER_RECENTS_DELAY_MS = 300;
    private static final java.lang.String TAG = "ActivityTaskManager";
    private static final java.lang.String[] TRON_WINDOW_STATE_VARZ_STRINGS = {"window_time_0", "window_time_1", "window_time_2", "window_time_3", "window_time_4"};
    private static final long UNKNOWN_VISIBILITY_CHECK_DELAY_MS = 3000;
    private static final int WINDOW_STATE_ASSISTANT = 3;
    private static final int WINDOW_STATE_FREEFORM = 2;
    private static final int WINDOW_STATE_INVALID = -1;
    private static final int WINDOW_STATE_MULTI_WINDOW = 4;
    private static final int WINDOW_STATE_SIDE_BY_SIDE = 1;
    private static final int WINDOW_STATE_STANDARD = 0;
    private com.android.server.apphibernation.AppHibernationManagerInternal mAppHibernationManagerInternal;
    private android.content.pm.dex.ArtManagerInternal mArtManagerInternal;
    private final com.android.server.wm.LaunchObserverRegistryImpl mLaunchObserver;
    private final com.android.server.wm.ActivityTaskSupervisor mSupervisor;
    private int mWindowState = 0;
    private final com.android.internal.logging.MetricsLogger mMetricsLogger = new com.android.internal.logging.MetricsLogger();
    private final android.os.Handler mLoggerHandler = com.android.server.FgThread.getHandler();
    private final java.util.ArrayList<com.android.server.wm.ActivityMetricsLogger.TransitionInfo> mTransitionInfoList = new java.util.ArrayList<>();
    private final android.util.ArrayMap<com.android.server.wm.ActivityRecord, com.android.server.wm.ActivityMetricsLogger.TransitionInfo> mLastTransitionInfo = new android.util.ArrayMap<>();
    private final android.util.SparseArray<com.android.server.wm.ActivityMetricsLogger.PackageCompatStateInfo> mPackageUidToCompatStateInfo = new android.util.SparseArray<>(0);
    private final java.lang.StringBuilder mStringBuilder = new java.lang.StringBuilder();
    private final android.util.ArrayMap<java.lang.String, java.lang.Boolean> mLastHibernationStates = new android.util.ArrayMap<>();
    private long mLastLogTimeSecs = android.os.SystemClock.elapsedRealtime() / 1000;

    static final class LaunchingState {
        private static int sTraceSeqId;
        private com.android.server.wm.ActivityMetricsLogger.TransitionInfo mAssociatedTransitionInfo;
        java.lang.String mTraceName;
        final long mStartUptimeNs = android.os.SystemClock.uptimeNanos();
        final long mStartRealtimeNs = android.os.SystemClock.elapsedRealtimeNanos();

        LaunchingState() {
            if (!android.os.Trace.isTagEnabled(64L)) {
                return;
            }
            sTraceSeqId++;
            this.mTraceName = "launchingActivity#" + sTraceSeqId;
            android.os.Trace.asyncTraceBegin(64L, this.mTraceName, 0);
        }

        void stopTrace(boolean z, com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo) {
            java.lang.String str;
            java.lang.String str2;
            if (this.mTraceName == null) {
                return;
            }
            if (!z && transitionInfo != this.mAssociatedTransitionInfo) {
                return;
            }
            android.os.Trace.asyncTraceEnd(64L, this.mTraceName, 0);
            if (this.mAssociatedTransitionInfo == null) {
                str2 = ":failed";
            } else {
                if (z) {
                    str = ":canceled:";
                } else if (!this.mAssociatedTransitionInfo.mProcessSwitch) {
                    str = ":completed-same-process:";
                } else if (transitionInfo.mTransitionType == 9) {
                    str = ":completed-hot:";
                } else if (transitionInfo.mTransitionType == 8) {
                    str = ":completed-warm:";
                } else {
                    str = ":completed-cold:";
                }
                str2 = str + this.mAssociatedTransitionInfo.mLastLaunchedActivity.packageName;
            }
            android.os.Trace.instant(64L, this.mTraceName + str2);
            this.mTraceName = null;
        }

        @com.android.internal.annotations.VisibleForTesting
        boolean allDrawn() {
            return this.mAssociatedTransitionInfo != null && this.mAssociatedTransitionInfo.mIsDrawn;
        }

        boolean hasActiveTransitionInfo() {
            return this.mAssociatedTransitionInfo != null;
        }

        boolean contains(com.android.server.wm.ActivityRecord activityRecord) {
            return this.mAssociatedTransitionInfo != null && this.mAssociatedTransitionInfo.contains(activityRecord);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TransitionInfo {
        int mCurrentTransitionDelayMs;
        boolean mIsDrawn;
        final boolean mIsInTaskActivityStart;

        @android.annotation.NonNull
        com.android.server.wm.ActivityRecord mLastLaunchedActivity;

        @android.annotation.Nullable
        java.lang.String mLaunchTraceName;
        final com.android.server.wm.ActivityMetricsLogger.LaunchingState mLaunchingState;
        boolean mLoggedStartingWindowDrawn;
        boolean mLoggedTransitionStarting;

        @android.annotation.Nullable
        java.lang.Runnable mPendingFullyDrawn;
        final int mProcessOomAdj;
        boolean mProcessRunning;
        final int mProcessState;
        final boolean mProcessSwitch;
        boolean mRelaunched;
        int mSourceEventDelayMs;
        int mSourceType;
        int mTransitionType;
        int mWindowsDrawnDelayMs;
        int mStartingWindowDelayMs = -1;
        int mBindApplicationDelayMs = -1;
        int mReason = 3;

        @android.annotation.Nullable
        static com.android.server.wm.ActivityMetricsLogger.TransitionInfo create(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull com.android.server.wm.ActivityMetricsLogger.LaunchingState launchingState, @android.annotation.Nullable android.app.ActivityOptions activityOptions, boolean z, boolean z2, int i, int i2, boolean z3, boolean z4, int i3) {
            int i4;
            int i5;
            if (i3 != 0 && i3 != 2) {
                return null;
            }
            if (z) {
                if (!z3 && activityRecord.attachedToProcess()) {
                    i5 = 9;
                } else {
                    i5 = 8;
                }
                i4 = i5;
            } else {
                i4 = 7;
            }
            return new com.android.server.wm.ActivityMetricsLogger.TransitionInfo(activityRecord, launchingState, activityOptions, i4, z, z2, i, i2, z4);
        }

        private TransitionInfo(com.android.server.wm.ActivityRecord activityRecord, com.android.server.wm.ActivityMetricsLogger.LaunchingState launchingState, android.app.ActivityOptions activityOptions, int i, boolean z, boolean z2, int i2, int i3, boolean z3) {
            android.app.ActivityOptions.SourceInfo sourceInfo;
            this.mSourceEventDelayMs = -1;
            this.mLaunchingState = launchingState;
            this.mTransitionType = i;
            this.mProcessRunning = z;
            this.mProcessSwitch = z2;
            this.mProcessState = i2;
            this.mProcessOomAdj = i3;
            this.mIsInTaskActivityStart = z3;
            setLatestLaunchedActivity(activityRecord);
            if (launchingState.mAssociatedTransitionInfo == null) {
                launchingState.mAssociatedTransitionInfo = this;
            }
            if (activityOptions != null && (sourceInfo = activityOptions.getSourceInfo()) != null) {
                this.mSourceType = sourceInfo.type;
                this.mSourceEventDelayMs = (int) (java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(launchingState.mStartUptimeNs) - sourceInfo.eventTimeMs);
            }
        }

        void setLatestLaunchedActivity(com.android.server.wm.ActivityRecord activityRecord) {
            if (this.mLastLaunchedActivity == activityRecord) {
                return;
            }
            if (this.mLastLaunchedActivity != null) {
                activityRecord.mLaunchCookie = this.mLastLaunchedActivity.mLaunchCookie;
                this.mLastLaunchedActivity.mLaunchCookie = null;
                activityRecord.mLaunchRootTask = this.mLastLaunchedActivity.mLaunchRootTask;
                this.mLastLaunchedActivity.mLaunchRootTask = null;
            }
            this.mLastLaunchedActivity = activityRecord;
            this.mIsDrawn = activityRecord.isReportedDrawn();
        }

        boolean canCoalesce(com.android.server.wm.ActivityRecord activityRecord) {
            if (this.mLastLaunchedActivity.mDisplayContent != activityRecord.mDisplayContent || this.mLastLaunchedActivity.getWindowingMode() != activityRecord.getWindowingMode()) {
                return false;
            }
            com.android.server.wm.Task task = this.mLastLaunchedActivity.getTask();
            com.android.server.wm.Task task2 = activityRecord.getTask();
            if (task != null && task2 != null) {
                if (task == task2) {
                    return true;
                }
                return task.getBounds().equals(task2.getBounds());
            }
            return this.mLastLaunchedActivity.isUid(activityRecord.launchedFromUid);
        }

        boolean contains(com.android.server.wm.ActivityRecord activityRecord) {
            return activityRecord == this.mLastLaunchedActivity;
        }

        boolean isInterestingToLoggerAndObserver() {
            return this.mProcessSwitch;
        }

        int calculateCurrentDelay() {
            return calculateDelay(android.os.SystemClock.uptimeNanos());
        }

        int calculateDelay(long j) {
            return (int) java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(j - this.mLaunchingState.mStartUptimeNs);
        }

        public java.lang.String toString() {
            return "TransitionInfo{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " a=" + this.mLastLaunchedActivity + " d=" + this.mIsDrawn + "}";
        }
    }

    static final class TransitionInfoSnapshot {
        final int activityRecordIdHashCode;
        private final android.content.pm.ApplicationInfo applicationInfo;
        private final int bindApplicationDelayMs;
        private final java.lang.String launchedActivityAppRecordRequiredAbi;
        private final java.lang.String launchedActivityLaunchToken;
        private final java.lang.String launchedActivityLaunchedFromPackage;
        final java.lang.String launchedActivityName;
        final java.lang.String launchedActivityShortComponentName;
        final java.lang.String packageName;
        private final java.lang.String processName;
        private final com.android.server.wm.WindowProcessController processRecord;
        private final int reason;
        final boolean relaunched;

        @com.android.internal.annotations.VisibleForTesting
        final int sourceEventDelayMs;

        @com.android.internal.annotations.VisibleForTesting
        final int sourceType;
        private final int startingWindowDelayMs;
        final long timestampNs;
        final int type;
        final int userId;
        final int windowsDrawnDelayMs;
        final int windowsFullyDrawnDelayMs;

        private TransitionInfoSnapshot(com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo) {
            this(transitionInfo, transitionInfo.mLastLaunchedActivity, -1);
        }

        private TransitionInfoSnapshot(com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo, com.android.server.wm.ActivityRecord activityRecord, int i) {
            java.lang.String requiredAbi;
            this.applicationInfo = activityRecord.info.applicationInfo;
            this.packageName = activityRecord.packageName;
            this.launchedActivityName = activityRecord.info.name;
            this.launchedActivityLaunchedFromPackage = activityRecord.launchedFromPackage;
            this.launchedActivityLaunchToken = activityRecord.info.launchToken;
            if (activityRecord.app == null) {
                requiredAbi = null;
            } else {
                requiredAbi = activityRecord.app.getRequiredAbi();
            }
            this.launchedActivityAppRecordRequiredAbi = requiredAbi;
            this.reason = transitionInfo.mReason;
            this.sourceEventDelayMs = transitionInfo.mSourceEventDelayMs;
            this.startingWindowDelayMs = transitionInfo.mStartingWindowDelayMs;
            this.bindApplicationDelayMs = transitionInfo.mBindApplicationDelayMs;
            this.windowsDrawnDelayMs = transitionInfo.mWindowsDrawnDelayMs;
            this.type = transitionInfo.mTransitionType;
            this.processRecord = activityRecord.app;
            this.processName = activityRecord.processName;
            this.sourceType = transitionInfo.mSourceType;
            this.userId = activityRecord.mUserId;
            this.launchedActivityShortComponentName = activityRecord.shortComponentName;
            this.activityRecordIdHashCode = java.lang.System.identityHashCode(activityRecord);
            this.windowsFullyDrawnDelayMs = i;
            this.relaunched = transitionInfo.mRelaunched;
            this.timestampNs = transitionInfo.mLaunchingState.mStartRealtimeNs;
        }

        int getLaunchState() {
            switch (this.type) {
                case 7:
                    return 1;
                case 8:
                    return 2;
                case 9:
                    return this.relaunched ? 4 : 3;
                default:
                    return -1;
            }
        }

        boolean isInterestedToEventLog() {
            return this.type == 8 || this.type == 7;
        }

        android.content.pm.dex.PackageOptimizationInfo getPackageOptimizationInfo(android.content.pm.dex.ArtManagerInternal artManagerInternal) {
            if (artManagerInternal == null || this.launchedActivityAppRecordRequiredAbi == null) {
                return android.content.pm.dex.PackageOptimizationInfo.createWithNoInfo();
            }
            return artManagerInternal.getPackageOptimizationInfo(this.applicationInfo, this.launchedActivityAppRecordRequiredAbi, this.launchedActivityName);
        }
    }

    private static final class PackageCompatStateInfo {

        @android.annotation.Nullable
        com.android.server.wm.ActivityRecord mLastLoggedActivity;
        int mLastLoggedState;
        final java.util.ArrayList<com.android.server.wm.ActivityRecord> mVisibleActivities;

        private PackageCompatStateInfo() {
            this.mVisibleActivities = new java.util.ArrayList<>();
            this.mLastLoggedState = 1;
        }
    }

    ActivityMetricsLogger(com.android.server.wm.ActivityTaskSupervisor activityTaskSupervisor, android.os.Looper looper) {
        this.mSupervisor = activityTaskSupervisor;
        this.mLaunchObserver = new com.android.server.wm.LaunchObserverRegistryImpl(looper);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logWindowState(java.lang.String str, int i) {
        this.mMetricsLogger.count(str, i);
    }

    void logWindowState() {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() / 1000;
        if (this.mWindowState != -1) {
            this.mLoggerHandler.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.wm.ActivityMetricsLogger$$ExternalSyntheticLambda5
                public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                    ((com.android.server.wm.ActivityMetricsLogger) obj).logWindowState((java.lang.String) obj2, ((java.lang.Integer) obj3).intValue());
                }
            }, this, TRON_WINDOW_STATE_VARZ_STRINGS[this.mWindowState], java.lang.Integer.valueOf((int) (elapsedRealtime - this.mLastLogTimeSecs))));
        }
        this.mLastLogTimeSecs = elapsedRealtime;
        this.mWindowState = -1;
        com.android.server.wm.Task topDisplayFocusedRootTask = this.mSupervisor.mRootWindowContainer.getTopDisplayFocusedRootTask();
        if (topDisplayFocusedRootTask == null) {
        }
        if (topDisplayFocusedRootTask.isActivityTypeAssistant()) {
            this.mWindowState = 3;
            return;
        }
        int windowingMode = topDisplayFocusedRootTask.getWindowingMode();
        switch (windowingMode) {
            case 1:
                this.mWindowState = 0;
                break;
            case 5:
                this.mWindowState = 2;
                break;
            case 6:
                this.mWindowState = 4;
                break;
            default:
                if (windowingMode != 0) {
                    android.util.Slog.wtf(TAG, "Unknown windowing mode for task=" + topDisplayFocusedRootTask + " windowingMode=" + windowingMode);
                    break;
                }
                break;
        }
    }

    @android.annotation.Nullable
    private com.android.server.wm.ActivityMetricsLogger.TransitionInfo getActiveTransitionInfo(com.android.server.wm.ActivityRecord activityRecord) {
        for (int size = this.mTransitionInfoList.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo = this.mTransitionInfoList.get(size);
            if (transitionInfo.contains(activityRecord)) {
                return transitionInfo;
            }
        }
        return null;
    }

    com.android.server.wm.ActivityMetricsLogger.LaunchingState notifyActivityLaunching(android.content.Intent intent) {
        return notifyActivityLaunching(intent, null, -1);
    }

    com.android.server.wm.ActivityMetricsLogger.LaunchingState notifyActivityLaunching(android.content.Intent intent, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, int i) {
        com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo = null;
        if (i != -1) {
            int size = this.mTransitionInfoList.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo2 = this.mTransitionInfoList.get(size);
                if (activityRecord != null && transitionInfo2.contains(activityRecord)) {
                    transitionInfo = transitionInfo2;
                    break;
                }
                if (transitionInfo == null && i == transitionInfo2.mLastLaunchedActivity.getUid()) {
                    transitionInfo = transitionInfo2;
                }
                size--;
            }
        }
        if (transitionInfo == null) {
            com.android.server.wm.ActivityMetricsLogger.LaunchingState launchingState = new com.android.server.wm.ActivityMetricsLogger.LaunchingState();
            launchObserverNotifyIntentStarted(intent, launchingState.mStartUptimeNs);
            return launchingState;
        }
        return transitionInfo.mLaunchingState;
    }

    void notifyActivityLaunched(@android.annotation.NonNull com.android.server.wm.ActivityMetricsLogger.LaunchingState launchingState, int i, boolean z, @android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, @android.annotation.Nullable android.app.ActivityOptions activityOptions) {
        com.android.server.wm.WindowProcessController processController;
        int i2;
        int i3;
        if (activityRecord == null || activityRecord.getTask() == null) {
            abort(launchingState, "nothing launched");
            return;
        }
        if (activityRecord.app != null) {
            processController = activityRecord.app;
        } else {
            processController = this.mSupervisor.mService.getProcessController(activityRecord.processName, activityRecord.info.applicationInfo.uid);
        }
        boolean z2 = processController != null;
        boolean z3 = (z2 && processController.hasStartedActivity(activityRecord)) ? false : true;
        if (z2) {
            int currentProcState = processController.getCurrentProcState();
            i3 = processController.getCurrentAdj();
            i2 = currentProcState;
        } else {
            i2 = 20;
            i3 = -10000;
        }
        com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo = launchingState.mAssociatedTransitionInfo;
        if (activityRecord.isReportedDrawn() && activityRecord.isVisible()) {
            abort(launchingState, "launched activity already visible");
            return;
        }
        if (transitionInfo != null && transitionInfo.canCoalesce(activityRecord)) {
            boolean z4 = !transitionInfo.mLastLaunchedActivity.packageName.equals(activityRecord.packageName);
            if (z4) {
                stopLaunchTrace(transitionInfo);
            }
            this.mLastTransitionInfo.remove(transitionInfo.mLastLaunchedActivity);
            transitionInfo.setLatestLaunchedActivity(activityRecord);
            this.mLastTransitionInfo.put(activityRecord, transitionInfo);
            if (z4) {
                startLaunchTrace(transitionInfo);
            }
            scheduleCheckActivityToBeDrawnIfSleeping(activityRecord);
            return;
        }
        com.android.server.wm.ActivityMetricsLogger.TransitionInfo create = com.android.server.wm.ActivityMetricsLogger.TransitionInfo.create(activityRecord, launchingState, activityOptions, z2, z3, i2, i3, z, activityRecord.getTask().isVisible(), i);
        if (create == null) {
            abort(launchingState, "unrecognized launch");
            return;
        }
        this.mTransitionInfoList.add(create);
        this.mLastTransitionInfo.put(activityRecord, create);
        startLaunchTrace(create);
        if (create.isInterestingToLoggerAndObserver()) {
            launchObserverNotifyActivityLaunched(create);
        } else {
            launchObserverNotifyIntentFailed(create.mLaunchingState.mStartUptimeNs);
        }
        scheduleCheckActivityToBeDrawnIfSleeping(activityRecord);
        for (int size = this.mTransitionInfoList.size() - 2; size >= 0; size--) {
            com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo2 = this.mTransitionInfoList.get(size);
            if (transitionInfo2.mIsDrawn || !transitionInfo2.mLastLaunchedActivity.isVisibleRequested()) {
                scheduleCheckActivityToBeDrawn(transitionInfo2.mLastLaunchedActivity, 0L);
            }
        }
    }

    private void scheduleCheckActivityToBeDrawnIfSleeping(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.mDisplayContent.isSleeping()) {
            scheduleCheckActivityToBeDrawn(activityRecord, 3000L);
        }
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot notifyWindowsDrawn(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        long uptimeNanos = android.os.SystemClock.uptimeNanos();
        com.android.server.wm.ActivityMetricsLogger.TransitionInfo activeTransitionInfo = getActiveTransitionInfo(activityRecord);
        if (activeTransitionInfo == null || activeTransitionInfo.mIsDrawn) {
            return null;
        }
        activeTransitionInfo.mWindowsDrawnDelayMs = activeTransitionInfo.calculateDelay(uptimeNanos);
        activeTransitionInfo.mIsDrawn = true;
        com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot = new com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot(activeTransitionInfo);
        if (activeTransitionInfo.mLoggedTransitionStarting || (!activityRecord.mDisplayContent.mOpeningApps.contains(activityRecord) && !activityRecord.mTransitionController.isCollecting(activityRecord))) {
            done(false, activeTransitionInfo, "notifyWindowsDrawn", uptimeNanos);
        }
        return transitionInfoSnapshot;
    }

    void notifyStartingWindowDrawn(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.ActivityMetricsLogger.TransitionInfo activeTransitionInfo = getActiveTransitionInfo(activityRecord);
        if (activeTransitionInfo == null || activeTransitionInfo.mLoggedStartingWindowDrawn) {
            return;
        }
        activeTransitionInfo.mLoggedStartingWindowDrawn = true;
        activeTransitionInfo.mStartingWindowDelayMs = activeTransitionInfo.calculateCurrentDelay();
    }

    void notifyTransitionStarting(android.util.ArrayMap<com.android.server.wm.WindowContainer, java.lang.Integer> arrayMap) {
        long uptimeNanos = android.os.SystemClock.uptimeNanos();
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowContainer keyAt = arrayMap.keyAt(size);
            com.android.server.wm.ActivityRecord asActivityRecord = keyAt.asActivityRecord();
            if (asActivityRecord == null) {
                asActivityRecord = keyAt.getTopActivity(false, true);
            }
            com.android.server.wm.ActivityMetricsLogger.TransitionInfo activeTransitionInfo = getActiveTransitionInfo(asActivityRecord);
            if (activeTransitionInfo != null && !activeTransitionInfo.mLoggedTransitionStarting) {
                activeTransitionInfo.mCurrentTransitionDelayMs = activeTransitionInfo.calculateDelay(uptimeNanos);
                activeTransitionInfo.mReason = arrayMap.valueAt(size).intValue();
                activeTransitionInfo.mLoggedTransitionStarting = true;
                if (activeTransitionInfo.mIsDrawn) {
                    done(false, activeTransitionInfo, "notifyTransitionStarting drawn", uptimeNanos);
                }
            }
        }
    }

    void notifyActivityRelaunched(com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.ActivityMetricsLogger.TransitionInfo activeTransitionInfo = getActiveTransitionInfo(activityRecord);
        if (activeTransitionInfo != null) {
            activeTransitionInfo.mRelaunched = true;
        }
    }

    void notifyActivityRemoved(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        this.mLastTransitionInfo.remove(activityRecord);
        com.android.server.wm.ActivityMetricsLogger.TransitionInfo activeTransitionInfo = getActiveTransitionInfo(activityRecord);
        if (activeTransitionInfo != null) {
            abort(activeTransitionInfo, "removed");
        }
        com.android.server.wm.ActivityMetricsLogger.PackageCompatStateInfo packageCompatStateInfo = this.mPackageUidToCompatStateInfo.get(activityRecord.info.applicationInfo.uid);
        if (packageCompatStateInfo == null) {
            return;
        }
        packageCompatStateInfo.mVisibleActivities.remove(activityRecord);
        if (packageCompatStateInfo.mLastLoggedActivity == activityRecord) {
            packageCompatStateInfo.mLastLoggedActivity = null;
        }
    }

    void notifyVisibilityChanged(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        if (getActiveTransitionInfo(activityRecord) == null) {
            return;
        }
        if (activityRecord.isState(com.android.server.wm.ActivityRecord.State.RESUMED) && activityRecord.mDisplayContent.isSleeping()) {
            return;
        }
        if (!activityRecord.isVisibleRequested() || activityRecord.finishing) {
            scheduleCheckActivityToBeDrawn(activityRecord, 0L);
        }
    }

    private void scheduleCheckActivityToBeDrawn(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, long j) {
        activityRecord.mAtmService.mH.sendMessageDelayed(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.wm.ActivityMetricsLogger$$ExternalSyntheticLambda7
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                ((com.android.server.wm.ActivityMetricsLogger) obj).checkActivityToBeDrawn((com.android.server.wm.Task) obj2, (com.android.server.wm.ActivityRecord) obj3);
            }
        }, this, activityRecord.getTask(), activityRecord), j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkActivityToBeDrawn(com.android.server.wm.Task task, com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mSupervisor.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ActivityMetricsLogger.TransitionInfo activeTransitionInfo = getActiveTransitionInfo(activityRecord);
                if (activeTransitionInfo == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                if (task != null && task.forAllActivities(new java.util.function.Predicate() { // from class: com.android.server.wm.ActivityMetricsLogger$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$checkActivityToBeDrawn$0;
                        lambda$checkActivityToBeDrawn$0 = com.android.server.wm.ActivityMetricsLogger.lambda$checkActivityToBeDrawn$0((com.android.server.wm.ActivityRecord) obj);
                        return lambda$checkActivityToBeDrawn$0;
                    }
                })) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                logAppTransitionCancel(activeTransitionInfo);
                abort(activeTransitionInfo, "checkActivityToBeDrawn (invisible or drawn already)");
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$checkActivityToBeDrawn$0(com.android.server.wm.ActivityRecord activityRecord) {
        return (!activityRecord.isVisibleRequested() || activityRecord.isReportedDrawn() || activityRecord.finishing) ? false : true;
    }

    @android.annotation.Nullable
    private com.android.server.apphibernation.AppHibernationManagerInternal getAppHibernationManagerInternal() {
        if (!com.android.server.apphibernation.AppHibernationService.isAppHibernationEnabled()) {
            return null;
        }
        if (this.mAppHibernationManagerInternal == null) {
            this.mAppHibernationManagerInternal = (com.android.server.apphibernation.AppHibernationManagerInternal) com.android.server.LocalServices.getService(com.android.server.apphibernation.AppHibernationManagerInternal.class);
        }
        return this.mAppHibernationManagerInternal;
    }

    void notifyBeforePackageUnstopped(@android.annotation.NonNull java.lang.String str) {
        com.android.server.apphibernation.AppHibernationManagerInternal appHibernationManagerInternal = getAppHibernationManagerInternal();
        if (appHibernationManagerInternal != null) {
            this.mLastHibernationStates.put(str, java.lang.Boolean.valueOf(appHibernationManagerInternal.isHibernatingGlobally(str)));
        }
    }

    void notifyBindApplication(android.content.pm.ApplicationInfo applicationInfo) {
        for (int size = this.mTransitionInfoList.size() - 1; size >= 0; size--) {
            com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo = this.mTransitionInfoList.get(size);
            if (transitionInfo.mLastLaunchedActivity.info.applicationInfo == applicationInfo) {
                transitionInfo.mBindApplicationDelayMs = transitionInfo.calculateCurrentDelay();
                if (transitionInfo.mProcessRunning) {
                    transitionInfo.mProcessRunning = false;
                    transitionInfo.mTransitionType = 7;
                    java.lang.String str = "Process " + transitionInfo.mLastLaunchedActivity.info.processName + " restarted";
                    android.util.Slog.i(TAG, str);
                    if (transitionInfo.mLaunchingState.mTraceName != null) {
                        android.os.Trace.instant(64L, str + "#" + com.android.server.wm.ActivityMetricsLogger.LaunchingState.sTraceSeqId);
                    }
                }
            }
        }
    }

    private void abort(@android.annotation.NonNull com.android.server.wm.ActivityMetricsLogger.LaunchingState launchingState, java.lang.String str) {
        if (launchingState.mAssociatedTransitionInfo != null) {
            abort(launchingState.mAssociatedTransitionInfo, str);
        } else {
            launchingState.stopTrace(true, null);
            launchObserverNotifyIntentFailed(launchingState.mStartUptimeNs);
        }
    }

    private void abort(@android.annotation.NonNull com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo, java.lang.String str) {
        done(true, transitionInfo, str, 0L);
    }

    private void done(boolean z, @android.annotation.NonNull com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo, java.lang.String str, long j) {
        transitionInfo.mLaunchingState.stopTrace(z, transitionInfo);
        stopLaunchTrace(transitionInfo);
        java.lang.Boolean remove = this.mLastHibernationStates.remove(transitionInfo.mLastLaunchedActivity.packageName);
        if (z) {
            this.mLastTransitionInfo.remove(transitionInfo.mLastLaunchedActivity);
            this.mSupervisor.stopWaitingForActivityVisible(transitionInfo.mLastLaunchedActivity);
            launchObserverNotifyActivityLaunchCancelled(transitionInfo);
        } else {
            if (transitionInfo.isInterestingToLoggerAndObserver()) {
                launchObserverNotifyActivityLaunchFinished(transitionInfo, j);
            }
            logAppTransitionFinished(transitionInfo, remove != null ? remove.booleanValue() : false);
            if (transitionInfo.mReason == 5) {
                logRecentsAnimationLatency(transitionInfo);
            }
        }
        this.mTransitionInfoList.remove(transitionInfo);
    }

    private void logAppTransitionCancel(com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo) {
        int i = transitionInfo.mTransitionType;
        com.android.server.wm.ActivityRecord activityRecord = transitionInfo.mLastLaunchedActivity;
        android.metrics.LogMaker logMaker = new android.metrics.LogMaker(1144);
        logMaker.setPackageName(activityRecord.packageName);
        logMaker.setType(i);
        logMaker.addTaggedData(871, activityRecord.info.name);
        this.mMetricsLogger.write(logMaker);
        com.android.internal.util.FrameworkStatsLog.write(49, activityRecord.info.applicationInfo.uid, activityRecord.packageName, getAppStartTransitionType(i, transitionInfo.mRelaunched), activityRecord.info.name);
    }

    private void logAppTransitionFinished(@android.annotation.NonNull final com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo, final boolean z) {
        final com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot = new com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot(transitionInfo);
        final boolean z2 = transitionInfo.mLastLaunchedActivity.mStyleFillsParent;
        final long j = transitionInfo.mLaunchingState.mStartUptimeNs;
        final int i = transitionInfo.mCurrentTransitionDelayMs;
        final int i2 = transitionInfo.mProcessState;
        final int i3 = transitionInfo.mProcessOomAdj;
        this.mLoggerHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityMetricsLogger$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ActivityMetricsLogger.this.lambda$logAppTransitionFinished$1(transitionInfo, j, i, transitionInfoSnapshot, z, i2, i3, z2);
            }
        });
        if (transitionInfo.mPendingFullyDrawn != null) {
            transitionInfo.mPendingFullyDrawn.run();
        }
        transitionInfo.mLastLaunchedActivity.info.launchToken = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$logAppTransitionFinished$1(com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo, long j, int i, com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot, boolean z, int i2, int i3, boolean z2) {
        if (transitionInfo.isInterestingToLoggerAndObserver()) {
            logAppTransition(j, i, transitionInfoSnapshot, z, i2, i3);
        }
        if (transitionInfo.mIsInTaskActivityStart) {
            logInTaskActivityStart(transitionInfoSnapshot, z2, i);
        }
        if (transitionInfoSnapshot.isInterestedToEventLog()) {
            logAppDisplayed(transitionInfoSnapshot);
        }
    }

    private void logAppTransition(long j, int i, com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot, boolean z, int i2, int i3) {
        boolean z2;
        boolean z3;
        int i4;
        android.metrics.LogMaker logMaker = new android.metrics.LogMaker(com.android.internal.util.FrameworkStatsLog.HOTWORD_EGRESS_SIZE_ATOM_REPORTED);
        logMaker.setPackageName(transitionInfoSnapshot.packageName);
        logMaker.setType(transitionInfoSnapshot.type);
        logMaker.addTaggedData(871, transitionInfoSnapshot.launchedActivityName);
        boolean isInstantApp = transitionInfoSnapshot.applicationInfo.isInstantApp();
        if (transitionInfoSnapshot.launchedActivityLaunchedFromPackage != null) {
            logMaker.addTaggedData(904, transitionInfoSnapshot.launchedActivityLaunchedFromPackage);
        }
        java.lang.String str = transitionInfoSnapshot.launchedActivityLaunchToken;
        if (str != null) {
            logMaker.addTaggedData(903, str);
        }
        logMaker.addTaggedData(905, java.lang.Integer.valueOf(isInstantApp ? 1 : 0));
        logMaker.addTaggedData(com.android.internal.util.FrameworkStatsLog.APP_PROCESS_DIED__IMPORTANCE__IMPORTANCE_TOP_SLEEPING, java.lang.Long.valueOf(java.util.concurrent.TimeUnit.NANOSECONDS.toSeconds(j)));
        logMaker.addTaggedData(319, java.lang.Integer.valueOf(i));
        logMaker.setSubtype(transitionInfoSnapshot.reason);
        if (transitionInfoSnapshot.startingWindowDelayMs != -1) {
            logMaker.addTaggedData(321, java.lang.Integer.valueOf(transitionInfoSnapshot.startingWindowDelayMs));
        }
        if (transitionInfoSnapshot.bindApplicationDelayMs != -1) {
            logMaker.addTaggedData(945, java.lang.Integer.valueOf(transitionInfoSnapshot.bindApplicationDelayMs));
        }
        logMaker.addTaggedData(322, java.lang.Integer.valueOf(transitionInfoSnapshot.windowsDrawnDelayMs));
        android.content.pm.dex.PackageOptimizationInfo packageOptimizationInfo = transitionInfoSnapshot.getPackageOptimizationInfo(getArtManagerInternal());
        logMaker.addTaggedData(1321, java.lang.Integer.valueOf(packageOptimizationInfo.getCompilationReason()));
        logMaker.addTaggedData(1320, java.lang.Integer.valueOf(packageOptimizationInfo.getCompilationFilter()));
        this.mMetricsLogger.write(logMaker);
        java.lang.String codePath = transitionInfoSnapshot.applicationInfo.getCodePath();
        if (codePath != null && android.os.incremental.IncrementalManager.isIncrementalPath(codePath)) {
            z3 = isIncrementalLoading(transitionInfoSnapshot.packageName, transitionInfoSnapshot.userId);
            z2 = true;
        } else {
            z2 = false;
            z3 = false;
        }
        if ((transitionInfoSnapshot.applicationInfo.flags & 2097152) != 0) {
            i4 = 2;
        } else {
            i4 = 1;
        }
        com.android.internal.util.FrameworkStatsLog.write(48, transitionInfoSnapshot.applicationInfo.uid, transitionInfoSnapshot.packageName, getAppStartTransitionType(transitionInfoSnapshot.type, transitionInfoSnapshot.relaunched), transitionInfoSnapshot.launchedActivityName, transitionInfoSnapshot.launchedActivityLaunchedFromPackage, isInstantApp, 0L, transitionInfoSnapshot.reason, i, transitionInfoSnapshot.startingWindowDelayMs, transitionInfoSnapshot.bindApplicationDelayMs, transitionInfoSnapshot.windowsDrawnDelayMs, str, packageOptimizationInfo.getCompilationReason(), packageOptimizationInfo.getCompilationFilter(), transitionInfoSnapshot.sourceType, transitionInfoSnapshot.sourceEventDelayMs, z, z2, z3, transitionInfoSnapshot.launchedActivityName.hashCode(), java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(transitionInfoSnapshot.timestampNs), i2, i3, i4);
        logAppStartMemoryStateCapture(transitionInfoSnapshot);
    }

    private boolean isIncrementalLoading(java.lang.String str, int i) {
        android.content.pm.IncrementalStatesInfo incrementalStatesInfo = this.mSupervisor.mService.getPackageManagerInternalLocked().getIncrementalStatesInfo(str, 0, i);
        return incrementalStatesInfo != null && incrementalStatesInfo.isLoading();
    }

    @com.android.internal.annotations.VisibleForTesting
    void logInTaskActivityStart(com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot, boolean z, int i) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.IN_TASK_ACTIVITY_STARTED, transitionInfoSnapshot.applicationInfo.uid, getAppStartTransitionType(transitionInfoSnapshot.type, transitionInfoSnapshot.relaunched), z, i, transitionInfoSnapshot.windowsDrawnDelayMs, java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(transitionInfoSnapshot.timestampNs));
    }

    private void logAppDisplayed(com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot) {
        android.util.EventLog.writeEvent(com.android.server.wm.EventLogTags.WM_ACTIVITY_LAUNCH_TIME, java.lang.Integer.valueOf(transitionInfoSnapshot.userId), java.lang.Integer.valueOf(transitionInfoSnapshot.activityRecordIdHashCode), transitionInfoSnapshot.launchedActivityShortComponentName, java.lang.Integer.valueOf(transitionInfoSnapshot.windowsDrawnDelayMs));
        java.lang.StringBuilder sb = this.mStringBuilder;
        sb.setLength(0);
        sb.append("Displayed ");
        sb.append(transitionInfoSnapshot.launchedActivityShortComponentName);
        sb.append(" for user ");
        sb.append(transitionInfoSnapshot.userId);
        sb.append(": ");
        android.util.TimeUtils.formatDuration(transitionInfoSnapshot.windowsDrawnDelayMs, sb);
        android.util.Log.i(TAG, sb.toString());
    }

    private void logRecentsAnimationLatency(com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo) {
        final int i = transitionInfo.mSourceEventDelayMs + transitionInfo.mWindowsDrawnDelayMs;
        final com.android.server.wm.ActivityRecord activityRecord = transitionInfo.mLastLaunchedActivity;
        final long j = activityRecord.topResumedStateLossTime;
        final com.android.server.wm.WindowManagerService windowManagerService = this.mSupervisor.mService.mWindowManager;
        final com.android.server.wm.RecentsAnimationController recentsAnimationController = windowManagerService.getRecentsAnimationController();
        this.mLoggerHandler.postDelayed(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityMetricsLogger$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ActivityMetricsLogger.lambda$logRecentsAnimationLatency$2(j, activityRecord, recentsAnimationController, windowManagerService, i);
            }
        }, LATENCY_TRACKER_RECENTS_DELAY_MS);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$logRecentsAnimationLatency$2(long j, com.android.server.wm.ActivityRecord activityRecord, java.lang.Object obj, com.android.server.wm.WindowManagerService windowManagerService, int i) {
        if (j != activityRecord.topResumedStateLossTime || obj != windowManagerService.getRecentsAnimationController()) {
            return;
        }
        windowManagerService.mLatencyTracker.logAction(8, i);
    }

    private static int getAppStartTransitionType(int i, boolean z) {
        if (i == 7) {
            return 3;
        }
        if (i == 8) {
            return 1;
        }
        if (i == 9) {
            if (z) {
                return 4;
            }
            return 2;
        }
        return 0;
    }

    com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot notifyFullyDrawn(final com.android.server.wm.ActivityRecord activityRecord, final boolean z) {
        long millis;
        final com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo = this.mLastTransitionInfo.get(activityRecord);
        if (transitionInfo == null) {
            return null;
        }
        if (!transitionInfo.mIsDrawn && transitionInfo.mPendingFullyDrawn == null) {
            transitionInfo.mPendingFullyDrawn = new java.lang.Runnable() { // from class: com.android.server.wm.ActivityMetricsLogger$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.ActivityMetricsLogger.this.lambda$notifyFullyDrawn$3(activityRecord, z, transitionInfo);
                }
            };
            return null;
        }
        long uptimeNanos = android.os.SystemClock.uptimeNanos();
        if (transitionInfo.mPendingFullyDrawn != null) {
            millis = transitionInfo.mWindowsDrawnDelayMs;
        } else {
            millis = java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(uptimeNanos - transitionInfo.mLaunchingState.mStartUptimeNs);
        }
        final com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot = new com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot(transitionInfo, activityRecord, (int) millis);
        if (transitionInfoSnapshot.isInterestedToEventLog()) {
            this.mLoggerHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityMetricsLogger$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.ActivityMetricsLogger.this.lambda$notifyFullyDrawn$4(transitionInfoSnapshot);
                }
            });
        }
        this.mLastTransitionInfo.remove(activityRecord);
        if (!transitionInfo.isInterestingToLoggerAndObserver()) {
            return transitionInfoSnapshot;
        }
        android.os.Trace.traceBegin(64L, "ActivityManager:ReportingFullyDrawn " + transitionInfo.mLastLaunchedActivity.packageName);
        this.mLoggerHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.ActivityMetricsLogger$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.ActivityMetricsLogger.this.lambda$notifyFullyDrawn$5(transitionInfoSnapshot, z, transitionInfo);
            }
        });
        android.os.Trace.traceEnd(64L);
        launchObserverNotifyReportFullyDrawn(transitionInfo, uptimeNanos);
        return transitionInfoSnapshot;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyFullyDrawn$3(com.android.server.wm.ActivityRecord activityRecord, boolean z, com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo) {
        notifyFullyDrawn(activityRecord, z);
        transitionInfo.mPendingFullyDrawn = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyFullyDrawn$5(com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot, boolean z, com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo) {
        logAppFullyDrawnMetrics(transitionInfoSnapshot, z, transitionInfo.mProcessRunning);
    }

    private void logAppFullyDrawnMetrics(com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot, boolean z, boolean z2) {
        int i;
        boolean z3;
        boolean z4;
        int i2;
        android.metrics.LogMaker logMaker = new android.metrics.LogMaker(1090);
        logMaker.setPackageName(transitionInfoSnapshot.packageName);
        logMaker.addTaggedData(871, transitionInfoSnapshot.launchedActivityName);
        logMaker.addTaggedData(1091, java.lang.Long.valueOf(transitionInfoSnapshot.windowsFullyDrawnDelayMs));
        if (z) {
            i = 13;
        } else {
            i = 12;
        }
        logMaker.setType(i);
        logMaker.addTaggedData(com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ACTIVE_DEVICE_ADMIN, java.lang.Integer.valueOf(z2 ? 1 : 0));
        this.mMetricsLogger.write(logMaker);
        android.content.pm.dex.PackageOptimizationInfo packageOptimizationInfo = transitionInfoSnapshot.getPackageOptimizationInfo(getArtManagerInternal());
        java.lang.String codePath = transitionInfoSnapshot.applicationInfo.getCodePath();
        if (codePath != null && android.os.incremental.IncrementalManager.isIncrementalPath(codePath)) {
            z4 = isIncrementalLoading(transitionInfoSnapshot.packageName, transitionInfoSnapshot.userId);
            z3 = true;
        } else {
            z3 = false;
            z4 = false;
        }
        int i3 = transitionInfoSnapshot.applicationInfo.uid;
        java.lang.String str = transitionInfoSnapshot.packageName;
        if (z) {
            i2 = 1;
        } else {
            i2 = 2;
        }
        com.android.internal.util.FrameworkStatsLog.write(50, i3, str, i2, transitionInfoSnapshot.launchedActivityName, z2, transitionInfoSnapshot.windowsFullyDrawnDelayMs, packageOptimizationInfo.getCompilationReason(), packageOptimizationInfo.getCompilationFilter(), transitionInfoSnapshot.sourceType, transitionInfoSnapshot.sourceEventDelayMs, z3, z4, transitionInfoSnapshot.launchedActivityName.hashCode(), java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(transitionInfoSnapshot.timestampNs));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: logAppFullyDrawn, reason: merged with bridge method [inline-methods] */
    public void lambda$notifyFullyDrawn$4(com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot) {
        java.lang.StringBuilder sb = this.mStringBuilder;
        sb.setLength(0);
        sb.append("Fully drawn ");
        sb.append(transitionInfoSnapshot.launchedActivityShortComponentName);
        sb.append(": ");
        android.util.TimeUtils.formatDuration(transitionInfoSnapshot.windowsFullyDrawnDelayMs, sb);
        android.util.Log.i(TAG, sb.toString());
    }

    void logAbortedBgActivityStart(android.content.Intent intent, com.android.server.wm.WindowProcessController windowProcessController, int i, java.lang.String str, int i2, boolean z, int i3, int i4, boolean z2, boolean z3) {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        android.metrics.LogMaker logMaker = new android.metrics.LogMaker(1513);
        logMaker.setTimestamp(java.lang.System.currentTimeMillis());
        logMaker.addTaggedData(1514, java.lang.Integer.valueOf(i));
        logMaker.addTaggedData(1515, str);
        logMaker.addTaggedData(1516, java.lang.Integer.valueOf(android.app.ActivityManager.processStateAmToProto(i2)));
        logMaker.addTaggedData(1517, java.lang.Integer.valueOf(z ? 1 : 0));
        logMaker.addTaggedData(1518, java.lang.Integer.valueOf(i3));
        logMaker.addTaggedData(1519, java.lang.Integer.valueOf(android.app.ActivityManager.processStateAmToProto(i4)));
        logMaker.addTaggedData(1520, java.lang.Integer.valueOf(z2 ? 1 : 0));
        logMaker.addTaggedData(1527, java.lang.Integer.valueOf(z3 ? 1 : 0));
        if (intent != null) {
            logMaker.addTaggedData(1528, intent.getAction());
            android.content.ComponentName component = intent.getComponent();
            if (component != null) {
                logMaker.addTaggedData(1526, component.flattenToShortString());
            }
        }
        if (windowProcessController != null) {
            logMaker.addTaggedData(1529, windowProcessController.mName);
            logMaker.addTaggedData(1530, java.lang.Integer.valueOf(android.app.ActivityManager.processStateAmToProto(windowProcessController.getCurrentProcState())));
            logMaker.addTaggedData(1531, java.lang.Integer.valueOf(windowProcessController.hasClientActivities() ? 1 : 0));
            logMaker.addTaggedData(1532, java.lang.Integer.valueOf(windowProcessController.hasForegroundServices() ? 1 : 0));
            logMaker.addTaggedData(1533, java.lang.Integer.valueOf(windowProcessController.hasForegroundActivities() ? 1 : 0));
            logMaker.addTaggedData(1534, java.lang.Integer.valueOf(windowProcessController.hasTopUi() ? 1 : 0));
            logMaker.addTaggedData(1535, java.lang.Integer.valueOf(windowProcessController.hasOverlayUi() ? 1 : 0));
            logMaker.addTaggedData(1536, java.lang.Integer.valueOf(windowProcessController.hasPendingUiClean() ? 1 : 0));
            if (windowProcessController.getInteractionEventTime() != 0) {
                logMaker.addTaggedData(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EXTERN_ANALOG, java.lang.Long.valueOf(elapsedRealtime - windowProcessController.getInteractionEventTime()));
            }
            if (windowProcessController.getFgInteractionTime() != 0) {
                logMaker.addTaggedData(com.android.server.usb.descriptors.UsbTerminalTypes.TERMINAL_EXTERN_DIGITAL, java.lang.Long.valueOf(elapsedRealtime - windowProcessController.getFgInteractionTime()));
            }
            if (windowProcessController.getWhenUnimportant() != 0) {
                logMaker.addTaggedData(1539, java.lang.Long.valueOf(uptimeMillis - windowProcessController.getWhenUnimportant()));
            }
        }
        this.mMetricsLogger.write(logMaker);
    }

    private void logAppStartMemoryStateCapture(com.android.server.wm.ActivityMetricsLogger.TransitionInfoSnapshot transitionInfoSnapshot) {
        if (transitionInfoSnapshot.processRecord == null) {
            return;
        }
        int pid = transitionInfoSnapshot.processRecord.getPid();
        int i = transitionInfoSnapshot.applicationInfo.uid;
        com.android.server.am.MemoryStatUtil.MemoryStat readMemoryStatFromFilesystem = com.android.server.am.MemoryStatUtil.readMemoryStatFromFilesystem(i, pid);
        if (readMemoryStatFromFilesystem == null) {
            return;
        }
        com.android.internal.util.FrameworkStatsLog.write(55, i, transitionInfoSnapshot.processName, transitionInfoSnapshot.launchedActivityName, readMemoryStatFromFilesystem.pgfault, readMemoryStatFromFilesystem.pgmajfault, readMemoryStatFromFilesystem.rssInBytes, readMemoryStatFromFilesystem.cacheInBytes, readMemoryStatFromFilesystem.swapInBytes);
    }

    void logAppCompatState(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        int i = activityRecord.info.applicationInfo.uid;
        int appCompatState = activityRecord.getAppCompatState();
        if (!this.mPackageUidToCompatStateInfo.contains(i)) {
            this.mPackageUidToCompatStateInfo.put(i, new com.android.server.wm.ActivityMetricsLogger.PackageCompatStateInfo());
        }
        com.android.server.wm.ActivityMetricsLogger.PackageCompatStateInfo packageCompatStateInfo = this.mPackageUidToCompatStateInfo.get(i);
        int i2 = packageCompatStateInfo.mLastLoggedState;
        com.android.server.wm.ActivityRecord activityRecord2 = packageCompatStateInfo.mLastLoggedActivity;
        boolean z = appCompatState != 1;
        java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList = packageCompatStateInfo.mVisibleActivities;
        if (z && !arrayList.contains(activityRecord)) {
            arrayList.add(activityRecord);
        } else if (!z) {
            arrayList.remove(activityRecord);
            if (arrayList.isEmpty()) {
                this.mPackageUidToCompatStateInfo.remove(i);
            }
        }
        if (appCompatState == i2) {
            return;
        }
        if (!z && !arrayList.isEmpty()) {
            if (activityRecord2 == null || activityRecord == activityRecord2) {
                findAppCompatStateToLog(packageCompatStateInfo, i);
                return;
            }
            return;
        }
        if (activityRecord2 != null && activityRecord != activityRecord2 && i2 != 1 && i2 != 2) {
            return;
        }
        logAppCompatStateInternal(activityRecord, appCompatState, packageCompatStateInfo);
    }

    private void findAppCompatStateToLog(com.android.server.wm.ActivityMetricsLogger.PackageCompatStateInfo packageCompatStateInfo, int i) {
        java.util.ArrayList<com.android.server.wm.ActivityRecord> arrayList = packageCompatStateInfo.mVisibleActivities;
        int i2 = packageCompatStateInfo.mLastLoggedState;
        com.android.server.wm.ActivityRecord activityRecord = null;
        int i3 = 1;
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            com.android.server.wm.ActivityRecord activityRecord2 = arrayList.get(i4);
            int appCompatState = activityRecord2.getAppCompatState();
            if (appCompatState == i2) {
                packageCompatStateInfo.mLastLoggedActivity = activityRecord2;
                return;
            }
            if (appCompatState == 1) {
                android.util.Slog.w(TAG, "Visible activity with NOT_VISIBLE App Compat state for package UID: " + i);
            } else if (i3 == 1 || (i3 == 2 && appCompatState != 2)) {
                activityRecord = activityRecord2;
                i3 = appCompatState;
            }
        }
        if (activityRecord != null && i3 != 1) {
            logAppCompatStateInternal(activityRecord, i3, packageCompatStateInfo);
        }
    }

    private static boolean isAppCompateStateChangedToLetterboxed(int i) {
        return i == 5 || i == 4 || i == 3;
    }

    private void logAppCompatStateInternal(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, int i, com.android.server.wm.ActivityMetricsLogger.PackageCompatStateInfo packageCompatStateInfo) {
        int i2;
        packageCompatStateInfo.mLastLoggedState = i;
        packageCompatStateInfo.mLastLoggedActivity = activityRecord;
        int i3 = activityRecord.info.applicationInfo.uid;
        if (!isAppCompateStateChangedToLetterboxed(i)) {
            i2 = 1;
        } else {
            i2 = activityRecord.mLetterboxUiController.getLetterboxPositionForLogging();
        }
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.APP_COMPAT_STATE_CHANGED, i3, i, i2);
    }

    void logLetterboxPositionChange(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, int i) {
        int i2 = activityRecord.info.applicationInfo.uid;
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.LETTERBOX_POSITION_CHANGED, i2, i);
        if (!this.mPackageUidToCompatStateInfo.contains(i2)) {
            return;
        }
        com.android.server.wm.ActivityMetricsLogger.PackageCompatStateInfo packageCompatStateInfo = this.mPackageUidToCompatStateInfo.get(i2);
        if (activityRecord != packageCompatStateInfo.mLastLoggedActivity) {
            return;
        }
        logAppCompatStateInternal(activityRecord, activityRecord.getAppCompatState(), packageCompatStateInfo);
    }

    void logCameraCompatControlAppearedEventReported(int i, int i2) {
        switch (i) {
            case 0:
                break;
            case 1:
                logCameraCompatControlEventReported(1, i2);
                break;
            case 2:
                logCameraCompatControlEventReported(2, i2);
                break;
            default:
                android.util.Slog.w(TAG, "Unexpected state in logCameraCompatControlAppearedEventReported: " + i);
                break;
        }
    }

    void logCameraCompatControlClickedEventReported(int i, int i2) {
        switch (i) {
            case 1:
                logCameraCompatControlEventReported(4, i2);
                break;
            case 2:
                logCameraCompatControlEventReported(3, i2);
                break;
            case 3:
                logCameraCompatControlEventReported(5, i2);
                break;
            default:
                android.util.Slog.w(TAG, "Unexpected state in logCameraCompatControlAppearedEventReported: " + i);
                break;
        }
    }

    private void logCameraCompatControlEventReported(int i, int i2) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.CAMERA_COMPAT_CONTROL_EVENT_REPORTED, i2, i);
    }

    private android.content.pm.dex.ArtManagerInternal getArtManagerInternal() {
        if (this.mArtManagerInternal == null) {
            this.mArtManagerInternal = (android.content.pm.dex.ArtManagerInternal) com.android.server.LocalServices.getService(android.content.pm.dex.ArtManagerInternal.class);
        }
        return this.mArtManagerInternal;
    }

    private void startLaunchTrace(@android.annotation.NonNull com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo) {
        if (transitionInfo.mLaunchingState.mTraceName == null) {
            return;
        }
        transitionInfo.mLaunchTraceName = "launching: " + transitionInfo.mLastLaunchedActivity.packageName;
        android.os.Trace.asyncTraceBegin(64L, transitionInfo.mLaunchTraceName, (int) transitionInfo.mLaunchingState.mStartRealtimeNs);
    }

    private void stopLaunchTrace(@android.annotation.NonNull com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo) {
        if (transitionInfo.mLaunchTraceName == null) {
            return;
        }
        android.os.Trace.asyncTraceEnd(64L, transitionInfo.mLaunchTraceName, (int) transitionInfo.mLaunchingState.mStartRealtimeNs);
        transitionInfo.mLaunchTraceName = null;
    }

    public com.android.server.wm.ActivityMetricsLaunchObserverRegistry getLaunchObserverRegistry() {
        return this.mLaunchObserver;
    }

    private void launchObserverNotifyIntentStarted(android.content.Intent intent, long j) {
        android.os.Trace.traceBegin(64L, "MetricsLogger:launchObserverNotifyIntentStarted");
        this.mLaunchObserver.onIntentStarted(intent, j);
        android.os.Trace.traceEnd(64L);
    }

    private void launchObserverNotifyIntentFailed(long j) {
        android.os.Trace.traceBegin(64L, "MetricsLogger:launchObserverNotifyIntentFailed");
        this.mLaunchObserver.onIntentFailed(j);
        android.os.Trace.traceEnd(64L);
    }

    private void launchObserverNotifyActivityLaunched(com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo) {
        android.os.Trace.traceBegin(64L, "MetricsLogger:launchObserverNotifyActivityLaunched");
        this.mLaunchObserver.onActivityLaunched(transitionInfo.mLaunchingState.mStartUptimeNs, transitionInfo.mLastLaunchedActivity.mActivityComponent, convertTransitionTypeToLaunchObserverTemperature(transitionInfo.mTransitionType), transitionInfo.mLastLaunchedActivity.mUserId);
        android.os.Trace.traceEnd(64L);
    }

    private void launchObserverNotifyReportFullyDrawn(com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo, long j) {
        android.os.Trace.traceBegin(64L, "MetricsLogger:launchObserverNotifyReportFullyDrawn");
        this.mLaunchObserver.onReportFullyDrawn(transitionInfo.mLaunchingState.mStartUptimeNs, j);
        android.os.Trace.traceEnd(64L);
    }

    private void launchObserverNotifyActivityLaunchCancelled(com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo) {
        android.os.Trace.traceBegin(64L, "MetricsLogger:launchObserverNotifyActivityLaunchCancelled");
        this.mLaunchObserver.onActivityLaunchCancelled(transitionInfo.mLaunchingState.mStartUptimeNs);
        android.os.Trace.traceEnd(64L);
    }

    private void launchObserverNotifyActivityLaunchFinished(com.android.server.wm.ActivityMetricsLogger.TransitionInfo transitionInfo, long j) {
        android.os.Trace.traceBegin(64L, "MetricsLogger:launchObserverNotifyActivityLaunchFinished");
        this.mLaunchObserver.onActivityLaunchFinished(transitionInfo.mLaunchingState.mStartUptimeNs, transitionInfo.mLastLaunchedActivity.mActivityComponent, j, transitionInfo.mLastLaunchedActivity.launchMode);
        android.os.Trace.traceEnd(64L);
    }

    private static int convertTransitionTypeToLaunchObserverTemperature(int i) {
        switch (i) {
            case 7:
                return 1;
            case 8:
                return 2;
            case 9:
                return 3;
            default:
                return -1;
        }
    }
}
