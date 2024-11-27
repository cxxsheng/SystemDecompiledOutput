package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public final class TimeController extends com.android.server.job.controllers.StateController {
    private static final boolean DEBUG;

    @com.android.internal.annotations.VisibleForTesting
    static final long DELAY_COALESCE_TIME_MS = 30000;
    private static final java.lang.String TAG = "JobScheduler.Time";
    private final java.lang.String DEADLINE_TAG;
    private final java.lang.String DELAY_TAG;
    private android.app.AlarmManager mAlarmService;
    private final android.app.AlarmManager.OnAlarmListener mDeadlineExpiredListener;
    private volatile long mLastFiredDelayExpiredElapsedMillis;
    private long mNextDelayExpiredElapsedMillis;
    private final android.app.AlarmManager.OnAlarmListener mNextDelayExpiredListener;
    private long mNextJobExpiredElapsedMillis;
    private final java.util.List<com.android.server.job.controllers.JobStatus> mTrackedJobs;

    static {
        DEBUG = com.android.server.job.JobSchedulerService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    public TimeController(com.android.server.job.JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.DEADLINE_TAG = "*job.deadline*";
        this.DELAY_TAG = "*job.delay*";
        this.mAlarmService = null;
        this.mTrackedJobs = new java.util.LinkedList();
        this.mDeadlineExpiredListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.job.controllers.TimeController.1
            @Override // android.app.AlarmManager.OnAlarmListener
            public void onAlarm() {
                if (com.android.server.job.controllers.TimeController.DEBUG) {
                    android.util.Slog.d(com.android.server.job.controllers.TimeController.TAG, "Deadline-expired alarm fired");
                }
                com.android.server.job.controllers.TimeController.this.checkExpiredDeadlinesAndResetAlarm();
            }
        };
        this.mNextDelayExpiredListener = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.job.controllers.TimeController.2
            @Override // android.app.AlarmManager.OnAlarmListener
            public void onAlarm() {
                if (com.android.server.job.controllers.TimeController.DEBUG) {
                    android.util.Slog.d(com.android.server.job.controllers.TimeController.TAG, "Delay-expired alarm fired");
                }
                com.android.server.job.controllers.TimeController.this.mLastFiredDelayExpiredElapsedMillis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                com.android.server.job.controllers.TimeController.this.checkExpiredDelaysAndResetAlarm();
            }
        };
        this.mNextJobExpiredElapsedMillis = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        this.mNextDelayExpiredElapsedMillis = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStartTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        boolean z;
        if (jobStatus.hasTimingDelayConstraint() || jobStatus.hasDeadlineConstraint()) {
            maybeStopTrackingJobLocked(jobStatus, null);
            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            if (jobStatus.hasDeadlineConstraint() && evaluateDeadlineConstraint(jobStatus, millis)) {
                return;
            }
            if (jobStatus.hasTimingDelayConstraint() && evaluateTimingDelayConstraint(jobStatus, millis) && !jobStatus.hasDeadlineConstraint()) {
                return;
            }
            java.util.ListIterator<com.android.server.job.controllers.JobStatus> listIterator = this.mTrackedJobs.listIterator(this.mTrackedJobs.size());
            while (true) {
                if (!listIterator.hasPrevious()) {
                    z = false;
                    break;
                } else if (listIterator.previous().getLatestRunTimeElapsed() < jobStatus.getLatestRunTimeElapsed()) {
                    z = true;
                    break;
                }
            }
            if (z) {
                listIterator.next();
            }
            listIterator.add(jobStatus);
            jobStatus.setTrackingController(32);
            android.os.WorkSource deriveWorkSource = this.mService.deriveWorkSource(jobStatus.getSourceUid(), jobStatus.getSourcePackageName());
            if (jobStatus.hasTimingDelayConstraint() && wouldBeReadyWithConstraintLocked(jobStatus, Integer.MIN_VALUE)) {
                maybeUpdateDelayAlarmLocked(jobStatus.getEarliestRunTime(), deriveWorkSource);
            }
            if (jobStatus.hasDeadlineConstraint() && wouldBeReadyWithConstraintLocked(jobStatus, 1073741824)) {
                maybeUpdateDeadlineAlarmLocked(jobStatus.getLatestRunTimeElapsed(), deriveWorkSource);
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(32) && this.mTrackedJobs.remove(jobStatus)) {
            checkExpiredDelaysAndResetAlarm();
            checkExpiredDeadlinesAndResetAlarm();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void evaluateStateLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        if (jobStatus.hasDeadlineConstraint() && !jobStatus.isConstraintSatisfied(1073741824) && jobStatus.getLatestRunTimeElapsed() <= this.mNextJobExpiredElapsedMillis) {
            if (evaluateDeadlineConstraint(jobStatus, millis)) {
                if (jobStatus.isReady()) {
                    this.mStateChangedListener.onRunJobNow(jobStatus);
                }
                this.mTrackedJobs.remove(jobStatus);
                com.android.modules.expresslog.Counter.logIncrement("job_scheduler.value_job_scheduler_job_deadline_expired_counter");
            } else if (wouldBeReadyWithConstraintLocked(jobStatus, 1073741824)) {
                setDeadlineExpiredAlarmLocked(jobStatus.getLatestRunTimeElapsed(), this.mService.deriveWorkSource(jobStatus.getSourceUid(), jobStatus.getSourcePackageName()));
            }
        }
        if (jobStatus.hasTimingDelayConstraint() && !jobStatus.isConstraintSatisfied(Integer.MIN_VALUE) && jobStatus.getEarliestRunTime() <= this.mNextDelayExpiredElapsedMillis) {
            if (evaluateTimingDelayConstraint(jobStatus, millis)) {
                if (canStopTrackingJobLocked(jobStatus)) {
                    this.mTrackedJobs.remove(jobStatus);
                }
            } else if (wouldBeReadyWithConstraintLocked(jobStatus, Integer.MIN_VALUE)) {
                setDelayExpiredAlarmLocked(jobStatus.getEarliestRunTime(), this.mService.deriveWorkSource(jobStatus.getSourceUid(), jobStatus.getSourcePackageName()));
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void reevaluateStateLocked(int i) {
        checkExpiredDeadlinesAndResetAlarm();
        checkExpiredDelaysAndResetAlarm();
    }

    private boolean canStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        return (!jobStatus.hasTimingDelayConstraint() || jobStatus.isConstraintSatisfied(Integer.MIN_VALUE)) && (!jobStatus.hasDeadlineConstraint() || jobStatus.isConstraintSatisfied(1073741824));
    }

    private void ensureAlarmServiceLocked() {
        if (this.mAlarmService == null) {
            this.mAlarmService = (android.app.AlarmManager) this.mContext.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void checkExpiredDeadlinesAndResetAlarm() {
        long j;
        int i;
        java.lang.String str;
        synchronized (this.mLock) {
            try {
                long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                java.util.ListIterator<com.android.server.job.controllers.JobStatus> listIterator = this.mTrackedJobs.listIterator();
                while (true) {
                    if (!listIterator.hasNext()) {
                        j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                        i = 0;
                        str = null;
                        break;
                    }
                    com.android.server.job.controllers.JobStatus next = listIterator.next();
                    if (next.hasDeadlineConstraint()) {
                        if (evaluateDeadlineConstraint(next, millis)) {
                            if (next.isReady()) {
                                this.mStateChangedListener.onRunJobNow(next);
                            }
                            com.android.modules.expresslog.Counter.logIncrement("job_scheduler.value_job_scheduler_job_deadline_expired_counter");
                            listIterator.remove();
                        } else if (!wouldBeReadyWithConstraintLocked(next, 1073741824)) {
                            if (DEBUG) {
                                android.util.Slog.i(TAG, "Skipping " + next + " because deadline won't make it ready.");
                            }
                        } else {
                            j = next.getLatestRunTimeElapsed();
                            i = next.getSourceUid();
                            str = next.getSourcePackageName();
                            break;
                        }
                    }
                }
                setDeadlineExpiredAlarmLocked(j, this.mService.deriveWorkSource(i, str));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean evaluateDeadlineConstraint(com.android.server.job.controllers.JobStatus jobStatus, long j) {
        if (jobStatus.getLatestRunTimeElapsed() <= j) {
            if (jobStatus.hasTimingDelayConstraint()) {
                jobStatus.setTimingDelayConstraintSatisfied(j, true);
            }
            jobStatus.setDeadlineConstraintSatisfied(j, true);
            return true;
        }
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting
    void checkExpiredDelaysAndResetAlarm() {
        synchronized (this.mLock) {
            try {
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = new android.util.ArraySet<>();
                java.util.Iterator<com.android.server.job.controllers.JobStatus> it = this.mTrackedJobs.iterator();
                long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                long j = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
                int i = 0;
                java.lang.String str = null;
                while (it.hasNext()) {
                    com.android.server.job.controllers.JobStatus next = it.next();
                    if (next.hasTimingDelayConstraint()) {
                        if (evaluateTimingDelayConstraint(next, millis)) {
                            if (canStopTrackingJobLocked(next)) {
                                it.remove();
                            }
                            arraySet.add(next);
                        } else if (!wouldBeReadyWithConstraintLocked(next, Integer.MIN_VALUE)) {
                            if (DEBUG) {
                                android.util.Slog.i(TAG, "Skipping " + next + " because delay won't make it ready.");
                            }
                        } else {
                            long earliestRunTime = next.getEarliestRunTime();
                            if (j > earliestRunTime) {
                                i = next.getSourceUid();
                                str = next.getSourcePackageName();
                                j = earliestRunTime;
                            }
                        }
                    }
                }
                if (arraySet.size() > 0) {
                    this.mStateChangedListener.onControllerStateChanged(arraySet);
                }
                setDelayExpiredAlarmLocked(j, this.mService.deriveWorkSource(i, str));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean evaluateTimingDelayConstraint(com.android.server.job.controllers.JobStatus jobStatus, long j) {
        if (jobStatus.getEarliestRunTime() <= j) {
            jobStatus.setTimingDelayConstraintSatisfied(j, true);
            return true;
        }
        return false;
    }

    private void maybeUpdateDelayAlarmLocked(long j, android.os.WorkSource workSource) {
        if (j < this.mNextDelayExpiredElapsedMillis) {
            setDelayExpiredAlarmLocked(j, workSource);
        }
    }

    private void maybeUpdateDeadlineAlarmLocked(long j, android.os.WorkSource workSource) {
        if (j < this.mNextJobExpiredElapsedMillis) {
            setDeadlineExpiredAlarmLocked(j, workSource);
        }
    }

    private void setDelayExpiredAlarmLocked(long j, android.os.WorkSource workSource) {
        long maybeAdjustAlarmTime = maybeAdjustAlarmTime(java.lang.Math.max(j, this.mLastFiredDelayExpiredElapsedMillis + 30000));
        if (this.mNextDelayExpiredElapsedMillis == maybeAdjustAlarmTime) {
            return;
        }
        this.mNextDelayExpiredElapsedMillis = maybeAdjustAlarmTime;
        updateAlarmWithListenerLocked("*job.delay*", 3, this.mNextDelayExpiredListener, this.mNextDelayExpiredElapsedMillis, workSource);
    }

    private void setDeadlineExpiredAlarmLocked(long j, android.os.WorkSource workSource) {
        long maybeAdjustAlarmTime = maybeAdjustAlarmTime(j);
        if (this.mNextJobExpiredElapsedMillis == maybeAdjustAlarmTime) {
            return;
        }
        this.mNextJobExpiredElapsedMillis = maybeAdjustAlarmTime;
        updateAlarmWithListenerLocked("*job.deadline*", 2, this.mDeadlineExpiredListener, this.mNextJobExpiredElapsedMillis, workSource);
    }

    private long maybeAdjustAlarmTime(long j) {
        return java.lang.Math.max(j, com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
    }

    private void updateAlarmWithListenerLocked(java.lang.String str, int i, android.app.AlarmManager.OnAlarmListener onAlarmListener, long j, android.os.WorkSource workSource) {
        ensureAlarmServiceLocked();
        if (j == com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            this.mAlarmService.cancel(onAlarmListener);
            return;
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Setting " + str + " for: " + j);
        }
        this.mAlarmService.set(i, j, -1L, 0L, str, onAlarmListener, com.android.server.AppSchedulingModuleThread.getHandler(), workSource);
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        indentingPrintWriter.println("Elapsed clock: " + millis);
        indentingPrintWriter.print("Next delay alarm in ");
        android.util.TimeUtils.formatDuration(this.mNextDelayExpiredElapsedMillis, millis, indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.print("Last delay alarm fired @ ");
        android.util.TimeUtils.formatDuration(millis, this.mLastFiredDelayExpiredElapsedMillis, indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.print("Next deadline alarm in ");
        android.util.TimeUtils.formatDuration(this.mNextJobExpiredElapsedMillis, millis, indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.println();
        for (com.android.server.job.controllers.JobStatus jobStatus : this.mTrackedJobs) {
            if (predicate.test(jobStatus)) {
                indentingPrintWriter.print("#");
                jobStatus.printUniqueId(indentingPrintWriter);
                indentingPrintWriter.print(" from ");
                android.os.UserHandle.formatUid(indentingPrintWriter, jobStatus.getSourceUid());
                indentingPrintWriter.print(": Delay=");
                if (jobStatus.hasTimingDelayConstraint()) {
                    android.util.TimeUtils.formatDuration(jobStatus.getEarliestRunTime(), millis, indentingPrintWriter);
                } else {
                    indentingPrintWriter.print("N/A");
                }
                indentingPrintWriter.print(", Deadline=");
                if (jobStatus.hasDeadlineConstraint()) {
                    android.util.TimeUtils.formatDuration(jobStatus.getLatestRunTimeElapsed(), millis, indentingPrintWriter);
                } else {
                    indentingPrintWriter.print("N/A");
                }
                indentingPrintWriter.println();
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        long start = protoOutputStream.start(j);
        long start2 = protoOutputStream.start(1146756268040L);
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        protoOutputStream.write(1112396529665L, millis);
        protoOutputStream.write(1112396529666L, this.mNextDelayExpiredElapsedMillis - millis);
        protoOutputStream.write(1112396529667L, this.mNextJobExpiredElapsedMillis - millis);
        for (com.android.server.job.controllers.JobStatus jobStatus : this.mTrackedJobs) {
            if (predicate.test(jobStatus)) {
                long start3 = protoOutputStream.start(2246267895812L);
                jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
                protoOutputStream.write(1133871366147L, jobStatus.hasTimingDelayConstraint());
                protoOutputStream.write(1112396529668L, jobStatus.getEarliestRunTime() - millis);
                protoOutputStream.write(1133871366149L, jobStatus.hasDeadlineConstraint());
                protoOutputStream.write(1112396529670L, jobStatus.getLatestRunTimeElapsed() - millis);
                protoOutputStream.end(start3);
            }
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }
}
