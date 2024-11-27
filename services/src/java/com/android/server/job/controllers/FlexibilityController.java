package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public final class FlexibilityController extends com.android.server.job.controllers.StateController {
    private static final boolean DEBUG;

    @com.android.internal.annotations.VisibleForTesting
    static final int FLEXIBLE_CONSTRAINTS = 268435463;
    private static final int JOB_SPECIFIC_FLEXIBLE_CONSTRAINTS = 268435456;
    private static final int MSG_CHECK_ALL_JOBS = 0;
    private static final int MSG_CHECK_JOBS = 1;
    private static final int MSG_CHECK_PACKAGES = 2;
    private static final long NO_LIFECYCLE_END = Long.MAX_VALUE;
    static final int SYSTEM_WIDE_FLEXIBLE_CONSTRAINTS = 7;
    private static final java.lang.String TAG = "JobScheduler.Flex";

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mAppliedConstraints;
    private long mDeadlineProximityLimitMs;
    private android.util.SparseLongArray mFallbackFlexibilityAdditionalScoreTimeFactors;
    private long mFallbackFlexibilityDeadlineMs;
    private android.util.SparseIntArray mFallbackFlexibilityDeadlineScores;
    private android.util.SparseLongArray mFallbackFlexibilityDeadlines;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.job.controllers.FlexibilityController.FcConfig mFcConfig;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.job.controllers.FlexibilityController.FlexibilityAlarmQueue mFlexibilityAlarmQueue;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private boolean mFlexibilityEnabled;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.job.controllers.FlexibilityController.FlexibilityTracker mFlexibilityTracker;
    private final com.android.server.job.controllers.FlexibilityController.FcHandler mHandler;
    private final android.util.SparseArrayMap<java.lang.String, com.android.server.job.controllers.FlexibilityController.JobScoreTracker> mJobScoreTrackers;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mJobsToCheck;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.SparseLongArray mLastSeenConstraintTimesElapsed;
    private long mMaxRescheduledDeadline;
    private long mMinTimeBetweenFlexibilityAlarmsMs;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArraySet<java.lang.String> mPackagesToCheck;
    private android.util.SparseArray<int[]> mPercentsToDropConstraints;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.job.controllers.PrefetchController.PrefetchChangedListener mPrefetchChangedListener;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.job.controllers.PrefetchController mPrefetchController;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    final android.util.SparseArrayMap<java.lang.String, java.lang.Long> mPrefetchLifeCycleStart;
    private long mRescheduledJobDeadline;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    int mSatisfiedFlexibleConstraints;
    private final com.android.server.job.controllers.FlexibilityController.SpecialAppTracker mSpecialAppTracker;
    private final int mSupportedFlexConstraints;
    private long mUnseenConstraintGracePeriodMs;

    static {
        DEBUG = com.android.server.job.JobSchedulerService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class JobScoreTracker {
        private static final long MAX_TIME_WINDOW_MS = 86400000;
        private static final int NUM_SCORE_BUCKETS = 24;
        private int mCachedScore;
        private long mCachedScoreExpirationTimeElapsed;
        private int mScoreBucketIndex;
        private final com.android.server.job.controllers.FlexibilityController.JobScoreTracker.JobScoreBucket[] mScoreBuckets;

        private JobScoreTracker() {
            this.mScoreBuckets = new com.android.server.job.controllers.FlexibilityController.JobScoreTracker.JobScoreBucket[24];
            this.mScoreBucketIndex = 0;
        }

        private static class JobScoreBucket {
            public int score;
            public long startTimeElapsed;

            private JobScoreBucket() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void reset() {
                this.startTimeElapsed = 0L;
                this.score = 0;
            }
        }

        public void addScore(int i, long j) {
            com.android.server.job.controllers.FlexibilityController.JobScoreTracker.JobScoreBucket jobScoreBucket = this.mScoreBuckets[this.mScoreBucketIndex];
            if (jobScoreBucket == null) {
                jobScoreBucket = new com.android.server.job.controllers.FlexibilityController.JobScoreTracker.JobScoreBucket();
                jobScoreBucket.startTimeElapsed = j;
                this.mScoreBuckets[this.mScoreBucketIndex] = jobScoreBucket;
                this.mCachedScoreExpirationTimeElapsed = java.lang.Math.min(this.mCachedScoreExpirationTimeElapsed, j + 86400000);
            } else if (jobScoreBucket.startTimeElapsed < j - 86400000) {
                jobScoreBucket.reset();
                jobScoreBucket.startTimeElapsed = j;
                this.mCachedScoreExpirationTimeElapsed = j;
            } else if (jobScoreBucket.startTimeElapsed < j - 3600000) {
                this.mScoreBucketIndex = (this.mScoreBucketIndex + 1) % 24;
                addScore(i, j);
                return;
            }
            jobScoreBucket.score += i;
            this.mCachedScore += i;
        }

        public int getScore(long j) {
            if (j < this.mCachedScoreExpirationTimeElapsed) {
                return this.mCachedScore;
            }
            long j2 = j - 86400000;
            long j3 = Long.MAX_VALUE;
            int i = 0;
            for (com.android.server.job.controllers.FlexibilityController.JobScoreTracker.JobScoreBucket jobScoreBucket : this.mScoreBuckets) {
                if (jobScoreBucket != null && jobScoreBucket.startTimeElapsed >= j2) {
                    i += jobScoreBucket.score;
                    if (j3 > jobScoreBucket.startTimeElapsed) {
                        j3 = jobScoreBucket.startTimeElapsed;
                    }
                }
            }
            this.mCachedScore = i;
            this.mCachedScoreExpirationTimeElapsed = j3 + 86400000;
            return i;
        }

        public void dump(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter, long j) {
            indentingPrintWriter.print("{");
            boolean z = false;
            for (int i = 0; i < this.mScoreBuckets.length; i++) {
                com.android.server.job.controllers.FlexibilityController.JobScoreTracker.JobScoreBucket jobScoreBucket = this.mScoreBuckets[((this.mScoreBucketIndex + 1) + i) % this.mScoreBuckets.length];
                if (jobScoreBucket != null && jobScoreBucket.startTimeElapsed != 0) {
                    if (z) {
                        indentingPrintWriter.print(", ");
                    }
                    android.util.TimeUtils.formatDuration(jobScoreBucket.startTimeElapsed, j, indentingPrintWriter);
                    indentingPrintWriter.print("=");
                    indentingPrintWriter.print(jobScoreBucket.score);
                    z = true;
                }
            }
            indentingPrintWriter.print("}");
        }
    }

    public FlexibilityController(com.android.server.job.JobSchedulerService jobSchedulerService, com.android.server.job.controllers.PrefetchController prefetchController) {
        super(jobSchedulerService);
        this.mFallbackFlexibilityDeadlineMs = 86400000L;
        this.mFallbackFlexibilityDeadlines = com.android.server.job.controllers.FlexibilityController.FcConfig.DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES;
        this.mFallbackFlexibilityDeadlineScores = com.android.server.job.controllers.FlexibilityController.FcConfig.DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES;
        this.mFallbackFlexibilityAdditionalScoreTimeFactors = com.android.server.job.controllers.FlexibilityController.FcConfig.DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS;
        this.mRescheduledJobDeadline = 3600000L;
        this.mMaxRescheduledDeadline = 86400000L;
        this.mUnseenConstraintGracePeriodMs = 259200000L;
        this.mAppliedConstraints = 0;
        this.mMinTimeBetweenFlexibilityAlarmsMs = 60000L;
        this.mDeadlineProximityLimitMs = 900000L;
        this.mLastSeenConstraintTimesElapsed = new android.util.SparseLongArray();
        this.mPrefetchLifeCycleStart = new android.util.SparseArrayMap<>();
        this.mPrefetchChangedListener = new com.android.server.job.controllers.PrefetchController.PrefetchChangedListener() { // from class: com.android.server.job.controllers.FlexibilityController.1
            @Override // com.android.server.job.controllers.PrefetchController.PrefetchChangedListener
            public void onPrefetchCacheUpdated(android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet, int i, java.lang.String str, long j, long j2, long j3) {
                synchronized (com.android.server.job.controllers.FlexibilityController.this.mLock) {
                    try {
                        long launchTimeThresholdMs = com.android.server.job.controllers.FlexibilityController.this.mPrefetchController.getLaunchTimeThresholdMs();
                        boolean z = true;
                        boolean z2 = j - launchTimeThresholdMs < j3;
                        if (j2 - launchTimeThresholdMs >= j3) {
                            z = false;
                        }
                        if (z != z2) {
                            com.android.server.job.controllers.FlexibilityController.this.mPrefetchLifeCycleStart.add(i, str, java.lang.Long.valueOf(java.lang.Math.max(j3, ((java.lang.Long) com.android.server.job.controllers.FlexibilityController.this.mPrefetchLifeCycleStart.getOrDefault(i, str, 0L)).longValue())));
                        }
                        for (int i2 = 0; i2 < arraySet.size(); i2++) {
                            com.android.server.job.controllers.JobStatus valueAt = arraySet.valueAt(i2);
                            if (valueAt.hasFlexibilityConstraint()) {
                                com.android.server.job.controllers.FlexibilityController.this.mFlexibilityTracker.calculateNumDroppedConstraints(valueAt, j3);
                                com.android.server.job.controllers.FlexibilityController.this.mFlexibilityAlarmQueue.scheduleDropNumConstraintsAlarm(valueAt, j3);
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mJobScoreTrackers = new android.util.SparseArrayMap<>();
        this.mJobsToCheck = new android.util.ArraySet<>();
        this.mPackagesToCheck = new android.util.ArraySet<>();
        this.mHandler = new com.android.server.job.controllers.FlexibilityController.FcHandler(com.android.server.AppSchedulingModuleThread.get().getLooper());
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive") || this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
            this.mSupportedFlexConstraints = 0;
        } else {
            this.mSupportedFlexConstraints = FLEXIBLE_CONSTRAINTS;
        }
        this.mFlexibilityEnabled = (this.mAppliedConstraints & this.mSupportedFlexConstraints) != 0;
        this.mFlexibilityTracker = new com.android.server.job.controllers.FlexibilityController.FlexibilityTracker(java.lang.Integer.bitCount(this.mSupportedFlexConstraints));
        this.mFcConfig = new com.android.server.job.controllers.FlexibilityController.FcConfig();
        this.mFlexibilityAlarmQueue = new com.android.server.job.controllers.FlexibilityController.FlexibilityAlarmQueue(this.mContext, com.android.server.AppSchedulingModuleThread.get().getLooper());
        this.mPercentsToDropConstraints = com.android.server.job.controllers.FlexibilityController.FcConfig.DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS;
        this.mPrefetchController = prefetchController;
        this.mSpecialAppTracker = new com.android.server.job.controllers.FlexibilityController.SpecialAppTracker();
        if (this.mFlexibilityEnabled) {
            this.mSpecialAppTracker.startTracking();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void onSystemServicesReady() {
        this.mSpecialAppTracker.onSystemServicesReady();
    }

    @Override // com.android.server.job.controllers.StateController
    public void startTrackingLocked() {
        if (this.mFlexibilityEnabled) {
            this.mPrefetchController.registerPrefetchChangedListener(this.mPrefetchChangedListener);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeStartTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.hasFlexibilityConstraint()) {
            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            if (this.mSupportedFlexConstraints == 0) {
                jobStatus.setFlexibilityConstraintSatisfied(millis, true);
                return;
            }
            jobStatus.setNumAppliedFlexibleConstraints(java.lang.Integer.bitCount(getRelevantAppliedConstraintsLocked(jobStatus)));
            jobStatus.setFlexibilityConstraintSatisfied(millis, isFlexibilitySatisfiedLocked(jobStatus));
            this.mFlexibilityTracker.add(jobStatus);
            jobStatus.setTrackingController(128);
            this.mFlexibilityAlarmQueue.scheduleDropNumConstraintsAlarm(jobStatus, millis);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void prepareForExecutionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.lastEvaluatedBias == 40) {
            return;
        }
        int priority = jobStatus.getJob().getPriority();
        int i = this.mFallbackFlexibilityDeadlineScores.get(priority, com.android.server.job.controllers.FlexibilityController.FcConfig.DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES.get(priority, priority / 100));
        com.android.server.job.controllers.FlexibilityController.JobScoreTracker jobScoreTracker = (com.android.server.job.controllers.FlexibilityController.JobScoreTracker) this.mJobScoreTrackers.get(jobStatus.getSourceUid(), jobStatus.getSourcePackageName());
        if (jobScoreTracker == null) {
            jobScoreTracker = new com.android.server.job.controllers.FlexibilityController.JobScoreTracker();
            this.mJobScoreTrackers.add(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), jobScoreTracker);
        }
        jobScoreTracker.addScore(i, com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
    }

    @Override // com.android.server.job.controllers.StateController
    public void unprepareFromExecutionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.lastEvaluatedBias == 40) {
            return;
        }
        com.android.server.job.controllers.FlexibilityController.JobScoreTracker jobScoreTracker = (com.android.server.job.controllers.FlexibilityController.JobScoreTracker) this.mJobScoreTrackers.get(jobStatus.getSourceUid(), jobStatus.getSourcePackageName());
        if (jobScoreTracker == null) {
            android.util.Slog.e(TAG, "Unprepared a job that didn't result in a score change");
        } else {
            int priority = jobStatus.getJob().getPriority();
            jobScoreTracker.addScore(-this.mFallbackFlexibilityDeadlineScores.get(priority, com.android.server.job.controllers.FlexibilityController.FcConfig.DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES.get(priority, priority / 100)), com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(128)) {
            this.mFlexibilityAlarmQueue.removeAlarmForKey(jobStatus);
            this.mFlexibilityTracker.remove(jobStatus);
        }
        this.mJobsToCheck.remove(jobStatus);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onAppRemovedLocked(java.lang.String str, int i) {
        int userId = android.os.UserHandle.getUserId(i);
        this.mPrefetchLifeCycleStart.delete(userId, str);
        this.mJobScoreTrackers.delete(i, str);
        this.mSpecialAppTracker.onAppRemoved(userId, str);
        for (int size = this.mJobsToCheck.size() - 1; size >= 0; size--) {
            com.android.server.job.controllers.JobStatus valueAt = this.mJobsToCheck.valueAt(size);
            if ((valueAt.getSourceUid() == i && valueAt.getSourcePackageName().equals(str)) || (valueAt.getUid() == i && valueAt.getCallingPackageName().equals(str))) {
                this.mJobsToCheck.removeAt(size);
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onUserRemovedLocked(int i) {
        this.mPrefetchLifeCycleStart.delete(i);
        this.mSpecialAppTracker.onUserRemoved(i);
        for (int numMaps = this.mJobScoreTrackers.numMaps() - 1; numMaps >= 0; numMaps--) {
            if (android.os.UserHandle.getUserId(this.mJobScoreTrackers.keyAt(numMaps)) == i) {
                this.mJobScoreTrackers.deleteAt(numMaps);
            }
        }
        for (int size = this.mJobsToCheck.size() - 1; size >= 0; size--) {
            com.android.server.job.controllers.JobStatus valueAt = this.mJobsToCheck.valueAt(size);
            if (android.os.UserHandle.getUserId(valueAt.getSourceUid()) == i || android.os.UserHandle.getUserId(valueAt.getUid()) == i) {
                this.mJobsToCheck.removeAt(size);
            }
        }
    }

    boolean isEnabled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mFlexibilityEnabled;
        }
        return z;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    boolean isFlexibilitySatisfiedLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        return !this.mFlexibilityEnabled || this.mService.getUidBias(jobStatus.getSourceUid()) == 40 || (this.mService.getUidBias(jobStatus.getSourceUid()) >= 30 && jobStatus.getEffectivePriority() >= 300) || ((jobStatus.getEffectivePriority() >= 300 && this.mSpecialAppTracker.isSpecialApp(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName())) || hasEnoughSatisfiedConstraintsLocked(jobStatus) || this.mService.isCurrentlyRunningLocked(jobStatus));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    int getRelevantAppliedConstraintsLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        return ((jobStatus.canApplyTransportAffinities() ? 268435456 : 0) | 7) & this.mAppliedConstraints;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    boolean hasEnoughSatisfiedConstraintsLocked(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        int bitCount = java.lang.Integer.bitCount(this.mSatisfiedFlexibleConstraints & this.mAppliedConstraints & ((jobStatus.areTransportAffinitiesSatisfied() ? 268435456 : 0) | 7));
        if (bitCount >= jobStatus.getNumRequiredFlexibleConstraints()) {
            return true;
        }
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        if (millis < this.mUnseenConstraintGracePeriodMs) {
            return false;
        }
        int i = ~getRelevantAppliedConstraintsLocked(jobStatus);
        for (int size = this.mLastSeenConstraintTimesElapsed.size() - 1; size >= 0; size--) {
            int keyAt = this.mLastSeenConstraintTimesElapsed.keyAt(size);
            if ((keyAt & i) == 0) {
                boolean z = millis - this.mLastSeenConstraintTimesElapsed.valueAt(size) <= this.mUnseenConstraintGracePeriodMs;
                if (java.lang.Integer.bitCount(keyAt) > bitCount && z) {
                    return false;
                }
            }
        }
        return true;
    }

    void setConstraintSatisfied(int i, boolean z, long j) {
        synchronized (this.mLock) {
            try {
                if (((this.mSatisfiedFlexibleConstraints & i) != 0) == z) {
                    return;
                }
                if (DEBUG) {
                    android.util.Slog.d(TAG, "setConstraintSatisfied:  constraint: " + i + " state: " + z);
                }
                this.mLastSeenConstraintTimesElapsed.put(this.mSatisfiedFlexibleConstraints, j);
                if (!z) {
                    this.mLastSeenConstraintTimesElapsed.put(i, j);
                }
                this.mSatisfiedFlexibleConstraints = (z ? i : 0) | (this.mSatisfiedFlexibleConstraints & (~i));
                if ((i & 268435456) != 0) {
                    return;
                }
                if (this.mFlexibilityEnabled) {
                    this.mHandler.obtainMessage(0).sendToTarget();
                }
            } finally {
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isConstraintSatisfied(int i) {
        return (i & this.mSatisfiedFlexibleConstraints) != 0;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    long getLifeCycleBeginningElapsedLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        long earliestRunTime = jobStatus.getEarliestRunTime() == 0 ? jobStatus.enqueueTime : jobStatus.getEarliestRunTime();
        if (jobStatus.getJob().isPeriodic() && jobStatus.getNumPreviousAttempts() == 0) {
            earliestRunTime = (earliestRunTime + (jobStatus.getLatestRunTimeElapsed() - jobStatus.getJob().getFlexMillis())) / 2;
        }
        if (jobStatus.getJob().isPrefetch()) {
            long nextEstimatedLaunchTimeLocked = this.mPrefetchController.getNextEstimatedLaunchTimeLocked(jobStatus);
            long longValue = ((java.lang.Long) this.mPrefetchLifeCycleStart.getOrDefault(jobStatus.getSourceUserId(), jobStatus.getSourcePackageName(), 0L)).longValue();
            if (nextEstimatedLaunchTimeLocked != Long.MAX_VALUE) {
                longValue = java.lang.Math.max(longValue, nextEstimatedLaunchTimeLocked - this.mPrefetchController.getLaunchTimeThresholdMs());
            }
            return java.lang.Math.max(longValue, earliestRunTime);
        }
        return earliestRunTime;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    int getScoreLocked(int i, @android.annotation.NonNull java.lang.String str, long j) {
        com.android.server.job.controllers.FlexibilityController.JobScoreTracker jobScoreTracker = (com.android.server.job.controllers.FlexibilityController.JobScoreTracker) this.mJobScoreTrackers.get(i, str);
        if (jobScoreTracker == null) {
            return 0;
        }
        return jobScoreTracker.getScore(j);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    long getLifeCycleEndElapsedLocked(com.android.server.job.controllers.JobStatus jobStatus, long j, long j2) {
        if (jobStatus.getJob().isPrefetch()) {
            long nextEstimatedLaunchTimeLocked = this.mPrefetchController.getNextEstimatedLaunchTimeLocked(jobStatus);
            if (jobStatus.getLatestRunTimeElapsed() != Long.MAX_VALUE) {
                return java.lang.Math.min(nextEstimatedLaunchTimeLocked - this.mConstants.PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS, jobStatus.getLatestRunTimeElapsed());
            }
            if (nextEstimatedLaunchTimeLocked != Long.MAX_VALUE) {
                return nextEstimatedLaunchTimeLocked - this.mConstants.PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS;
            }
            return Long.MAX_VALUE;
        }
        if (jobStatus.getNumPreviousAttempts() > 1) {
            return j2 + java.lang.Math.min((long) java.lang.Math.scalb(this.mRescheduledJobDeadline, jobStatus.getNumPreviousAttempts() - 2), this.mMaxRescheduledDeadline);
        }
        int effectivePriority = jobStatus.getEffectivePriority();
        long min = j2 + java.lang.Math.min(this.mFallbackFlexibilityDeadlineMs * 3, this.mFallbackFlexibilityDeadlines.get(effectivePriority, this.mFallbackFlexibilityDeadlineMs) + (this.mFallbackFlexibilityAdditionalScoreTimeFactors.get(effectivePriority, 60000L) * getScoreLocked(jobStatus.getSourceUid(), jobStatus.getSourcePackageName(), j)));
        if (jobStatus.getLatestRunTimeElapsed() == Long.MAX_VALUE) {
            return min;
        }
        return java.lang.Math.max(min, jobStatus.getLatestRunTimeElapsed());
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    int getCurPercentOfLifecycleLocked(com.android.server.job.controllers.JobStatus jobStatus, long j) {
        long lifeCycleBeginningElapsedLocked = getLifeCycleBeginningElapsedLocked(jobStatus);
        long lifeCycleEndElapsedLocked = getLifeCycleEndElapsedLocked(jobStatus, j, lifeCycleBeginningElapsedLocked);
        if (lifeCycleEndElapsedLocked == Long.MAX_VALUE || lifeCycleBeginningElapsedLocked >= j) {
            return 0;
        }
        if (j > lifeCycleEndElapsedLocked || lifeCycleEndElapsedLocked == lifeCycleBeginningElapsedLocked) {
            return 100;
        }
        return (int) (((j - lifeCycleBeginningElapsedLocked) * 100) / (lifeCycleEndElapsedLocked - lifeCycleBeginningElapsedLocked));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @com.android.internal.annotations.VisibleForTesting
    long getNextConstraintDropTimeElapsedLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        long lifeCycleBeginningElapsedLocked = getLifeCycleBeginningElapsedLocked(jobStatus);
        return getNextConstraintDropTimeElapsedLocked(jobStatus, lifeCycleBeginningElapsedLocked, getLifeCycleEndElapsedLocked(jobStatus, com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis(), lifeCycleBeginningElapsedLocked));
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    long getNextConstraintDropTimeElapsedLocked(com.android.server.job.controllers.JobStatus jobStatus, long j, long j2) {
        int[] percentsToDropConstraints = getPercentsToDropConstraints(jobStatus.getEffectivePriority());
        if (j2 == Long.MAX_VALUE || jobStatus.getNumDroppedFlexibleConstraints() == percentsToDropConstraints.length) {
            return Long.MAX_VALUE;
        }
        return j + (((j2 - j) * percentsToDropConstraints[jobStatus.getNumDroppedFlexibleConstraints()]) / 100);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public int[] getPercentsToDropConstraints(int i) {
        int[] iArr = this.mPercentsToDropConstraints.get(i);
        if (iArr == null) {
            android.util.Slog.wtf(TAG, "No %-to-drop for priority " + android.app.job.JobInfo.getPriorityString(i));
            return new int[]{50, 60, 70, 80};
        }
        return iArr;
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onUidBiasChangedLocked(int i, int i2, int i3) {
        if (i2 < 30 && i3 < 30) {
            return;
        }
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        android.util.ArraySet<com.android.server.job.controllers.JobStatus> jobsBySourceUid = this.mService.getJobStore().getJobsBySourceUid(i);
        boolean z = false;
        for (int i4 = 0; i4 < jobsBySourceUid.size(); i4++) {
            com.android.server.job.controllers.JobStatus valueAt = jobsBySourceUid.valueAt(i4);
            if (valueAt.hasFlexibilityConstraint()) {
                valueAt.setFlexibilityConstraintSatisfied(millis, isFlexibilitySatisfiedLocked(valueAt));
                z |= valueAt.getJob().isPrefetch();
            }
        }
        if (z && i2 == 40) {
            int userId = android.os.UserHandle.getUserId(i);
            android.util.ArraySet<java.lang.String> packagesForUidLocked = this.mService.getPackagesForUidLocked(i);
            if (packagesForUidLocked == null) {
                return;
            }
            for (int i5 = 0; i5 < packagesForUidLocked.size(); i5++) {
                java.lang.String valueAt2 = packagesForUidLocked.valueAt(i5);
                this.mPrefetchLifeCycleStart.add(userId, valueAt2, java.lang.Long.valueOf(java.lang.Math.max(((java.lang.Long) this.mPrefetchLifeCycleStart.getOrDefault(userId, valueAt2, 0L)).longValue(), millis)));
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onConstantsUpdatedLocked() {
        if (this.mFcConfig.mShouldReevaluateConstraints) {
            com.android.server.AppSchedulingModuleThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.job.controllers.FlexibilityController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.job.controllers.FlexibilityController.this.lambda$onConstantsUpdatedLocked$0();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onConstantsUpdatedLocked$0() {
        android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = new android.util.ArraySet<>();
        synchronized (this.mLock) {
            try {
                long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                for (int i = 0; i < this.mFlexibilityTracker.size(); i++) {
                    android.util.ArraySet<com.android.server.job.controllers.JobStatus> jobsByNumRequiredConstraints = this.mFlexibilityTracker.getJobsByNumRequiredConstraints(i);
                    for (int size = jobsByNumRequiredConstraints.size() - 1; size >= 0; size--) {
                        com.android.server.job.controllers.JobStatus valueAt = jobsByNumRequiredConstraints.valueAt(size);
                        this.mFlexibilityTracker.updateFlexibleConstraints(valueAt, millis);
                        this.mFlexibilityAlarmQueue.scheduleDropNumConstraintsAlarm(valueAt, millis);
                        if (valueAt.setFlexibilityConstraintSatisfied(millis, isFlexibilitySatisfiedLocked(valueAt))) {
                            arraySet.add(valueAt);
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (arraySet.size() > 0) {
            this.mStateChangedListener.onControllerStateChanged(arraySet);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void prepareForUpdatedConstantsLocked() {
        this.mFcConfig.mShouldReevaluateConstraints = false;
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void processConstantLocked(android.provider.DeviceConfig.Properties properties, java.lang.String str) {
        this.mFcConfig.processConstantLocked(properties, str);
    }

    @com.android.internal.annotations.VisibleForTesting
    class FlexibilityTracker {
        final java.util.ArrayList<android.util.ArraySet<com.android.server.job.controllers.JobStatus>> mTrackedJobs = new java.util.ArrayList<>();

        FlexibilityTracker(int i) {
            for (int i2 = 0; i2 <= i; i2++) {
                this.mTrackedJobs.add(new android.util.ArraySet<>());
            }
        }

        @android.annotation.Nullable
        public android.util.ArraySet<com.android.server.job.controllers.JobStatus> getJobsByNumRequiredConstraints(int i) {
            if (i > this.mTrackedJobs.size()) {
                android.util.Slog.wtfStack(com.android.server.job.controllers.FlexibilityController.TAG, "Asked for a larger number of constraints than exists.");
                return null;
            }
            return this.mTrackedJobs.get(i);
        }

        public void add(com.android.server.job.controllers.JobStatus jobStatus) {
            if (jobStatus.getNumRequiredFlexibleConstraints() < 0) {
                return;
            }
            this.mTrackedJobs.get(jobStatus.getNumRequiredFlexibleConstraints()).add(jobStatus);
        }

        public void remove(com.android.server.job.controllers.JobStatus jobStatus) {
            this.mTrackedJobs.get(jobStatus.getNumRequiredFlexibleConstraints()).remove(jobStatus);
        }

        public void updateFlexibleConstraints(com.android.server.job.controllers.JobStatus jobStatus, long j) {
            int numRequiredFlexibleConstraints = jobStatus.getNumRequiredFlexibleConstraints();
            int bitCount = java.lang.Integer.bitCount(com.android.server.job.controllers.FlexibilityController.this.getRelevantAppliedConstraintsLocked(jobStatus));
            jobStatus.setNumAppliedFlexibleConstraints(bitCount);
            int[] percentsToDropConstraints = com.android.server.job.controllers.FlexibilityController.this.getPercentsToDropConstraints(jobStatus.getEffectivePriority());
            int curPercentOfLifecycleLocked = com.android.server.job.controllers.FlexibilityController.this.getCurPercentOfLifecycleLocked(jobStatus, j);
            int i = 0;
            for (int i2 = 0; i2 < bitCount; i2++) {
                if (curPercentOfLifecycleLocked >= percentsToDropConstraints[i2]) {
                    i++;
                }
            }
            jobStatus.setNumDroppedFlexibleConstraints(i);
            if (numRequiredFlexibleConstraints == jobStatus.getNumRequiredFlexibleConstraints()) {
                return;
            }
            this.mTrackedJobs.get(numRequiredFlexibleConstraints).remove(jobStatus);
            add(jobStatus);
        }

        public void calculateNumDroppedConstraints(com.android.server.job.controllers.JobStatus jobStatus, long j) {
            int curPercentOfLifecycleLocked = com.android.server.job.controllers.FlexibilityController.this.getCurPercentOfLifecycleLocked(jobStatus, j);
            int numAppliedFlexibleConstraints = jobStatus.getNumAppliedFlexibleConstraints();
            int[] percentsToDropConstraints = com.android.server.job.controllers.FlexibilityController.this.getPercentsToDropConstraints(jobStatus.getEffectivePriority());
            int i = 0;
            for (int i2 = 0; i2 < numAppliedFlexibleConstraints; i2++) {
                if (curPercentOfLifecycleLocked >= percentsToDropConstraints[i2]) {
                    i++;
                }
            }
            setNumDroppedFlexibleConstraints(jobStatus, i);
        }

        public java.util.ArrayList<android.util.ArraySet<com.android.server.job.controllers.JobStatus>> getArrayList() {
            return this.mTrackedJobs;
        }

        public void setNumDroppedFlexibleConstraints(com.android.server.job.controllers.JobStatus jobStatus, int i) {
            if (i != jobStatus.getNumDroppedFlexibleConstraints()) {
                remove(jobStatus);
                jobStatus.setNumDroppedFlexibleConstraints(i);
                add(jobStatus);
            }
        }

        public int size() {
            return this.mTrackedJobs.size();
        }

        public void dump(android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate, long j) {
            for (int i = 0; i < this.mTrackedJobs.size(); i++) {
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mTrackedJobs.get(i);
                for (int i2 = 0; i2 < arraySet.size(); i2++) {
                    com.android.server.job.controllers.JobStatus valueAt = arraySet.valueAt(i2);
                    if (predicate.test(valueAt)) {
                        valueAt.printUniqueId(indentingPrintWriter);
                        indentingPrintWriter.print(" from ");
                        android.os.UserHandle.formatUid(indentingPrintWriter, valueAt.getSourceUid());
                        indentingPrintWriter.print("-> Num Required Constraints: ");
                        indentingPrintWriter.print(valueAt.getNumRequiredFlexibleConstraints());
                        indentingPrintWriter.print(", lifecycle=[");
                        long lifeCycleBeginningElapsedLocked = com.android.server.job.controllers.FlexibilityController.this.getLifeCycleBeginningElapsedLocked(valueAt);
                        indentingPrintWriter.print(lifeCycleBeginningElapsedLocked);
                        indentingPrintWriter.print(", (");
                        indentingPrintWriter.print(com.android.server.job.controllers.FlexibilityController.this.getCurPercentOfLifecycleLocked(valueAt, j));
                        indentingPrintWriter.print("%), ");
                        indentingPrintWriter.print(com.android.server.job.controllers.FlexibilityController.this.getLifeCycleEndElapsedLocked(valueAt, j, lifeCycleBeginningElapsedLocked));
                        indentingPrintWriter.print("]");
                        indentingPrintWriter.println();
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    class FlexibilityAlarmQueue extends com.android.server.utils.AlarmQueue<com.android.server.job.controllers.JobStatus> {
        private FlexibilityAlarmQueue(android.content.Context context, android.os.Looper looper) {
            super(context, looper, "*job.flexibility_check*", "Flexible Constraint Check", true, com.android.server.job.controllers.FlexibilityController.this.mMinTimeBetweenFlexibilityAlarmsMs);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.android.server.utils.AlarmQueue
        public boolean isForUser(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus, int i) {
            return jobStatus.getSourceUserId() == i;
        }

        public void scheduleDropNumConstraintsAlarm(com.android.server.job.controllers.JobStatus jobStatus, long j) {
            synchronized (com.android.server.job.controllers.FlexibilityController.this.mLock) {
                try {
                    long lifeCycleBeginningElapsedLocked = com.android.server.job.controllers.FlexibilityController.this.getLifeCycleBeginningElapsedLocked(jobStatus);
                    long lifeCycleEndElapsedLocked = com.android.server.job.controllers.FlexibilityController.this.getLifeCycleEndElapsedLocked(jobStatus, j, lifeCycleBeginningElapsedLocked);
                    if (lifeCycleEndElapsedLocked <= lifeCycleBeginningElapsedLocked) {
                        android.util.Slog.wtf(com.android.server.job.controllers.FlexibilityController.TAG, "Got invalid latest when scheduling alarm. prefetch=" + jobStatus.getJob().isPrefetch() + " periodic=" + jobStatus.getJob().isPeriodic());
                        com.android.server.job.controllers.FlexibilityController.this.mFlexibilityTracker.setNumDroppedFlexibleConstraints(jobStatus, jobStatus.getNumAppliedFlexibleConstraints());
                        com.android.server.job.controllers.FlexibilityController.this.mJobsToCheck.add(jobStatus);
                        com.android.server.job.controllers.FlexibilityController.this.mHandler.sendEmptyMessage(1);
                        return;
                    }
                    long nextConstraintDropTimeElapsedLocked = com.android.server.job.controllers.FlexibilityController.this.getNextConstraintDropTimeElapsedLocked(jobStatus, lifeCycleBeginningElapsedLocked, lifeCycleEndElapsedLocked);
                    if (com.android.server.job.controllers.FlexibilityController.DEBUG) {
                        android.util.Slog.d(com.android.server.job.controllers.FlexibilityController.TAG, "scheduleDropNumConstraintsAlarm: " + jobStatus.toShortString() + " numApplied: " + jobStatus.getNumAppliedFlexibleConstraints() + " numRequired: " + jobStatus.getNumRequiredFlexibleConstraints() + " numSatisfied: " + java.lang.Integer.bitCount(com.android.server.job.controllers.FlexibilityController.this.mSatisfiedFlexibleConstraints & com.android.server.job.controllers.FlexibilityController.this.getRelevantAppliedConstraintsLocked(jobStatus)) + " curTime: " + j + " earliest: " + lifeCycleBeginningElapsedLocked + " latest: " + lifeCycleEndElapsedLocked + " nextTime: " + nextConstraintDropTimeElapsedLocked);
                    }
                    if (lifeCycleEndElapsedLocked - j < com.android.server.job.controllers.FlexibilityController.this.mDeadlineProximityLimitMs) {
                        if (com.android.server.job.controllers.FlexibilityController.DEBUG) {
                            android.util.Slog.d(com.android.server.job.controllers.FlexibilityController.TAG, "deadline proximity met: " + jobStatus);
                        }
                        com.android.server.job.controllers.FlexibilityController.this.mFlexibilityTracker.setNumDroppedFlexibleConstraints(jobStatus, jobStatus.getNumAppliedFlexibleConstraints());
                        com.android.server.job.controllers.FlexibilityController.this.mJobsToCheck.add(jobStatus);
                        com.android.server.job.controllers.FlexibilityController.this.mHandler.sendEmptyMessage(1);
                        return;
                    }
                    if (nextConstraintDropTimeElapsedLocked == Long.MAX_VALUE) {
                        removeAlarmForKey(jobStatus);
                        return;
                    }
                    if (lifeCycleEndElapsedLocked - nextConstraintDropTimeElapsedLocked <= com.android.server.job.controllers.FlexibilityController.this.mDeadlineProximityLimitMs) {
                        if (com.android.server.job.controllers.FlexibilityController.DEBUG) {
                            android.util.Slog.d(com.android.server.job.controllers.FlexibilityController.TAG, "last alarm set: " + jobStatus);
                        }
                        addAlarm(jobStatus, lifeCycleEndElapsedLocked - com.android.server.job.controllers.FlexibilityController.this.mDeadlineProximityLimitMs);
                        return;
                    }
                    addAlarm(jobStatus, nextConstraintDropTimeElapsedLocked);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.utils.AlarmQueue
        protected void processExpiredAlarms(@android.annotation.NonNull android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet) {
            synchronized (com.android.server.job.controllers.FlexibilityController.this.mLock) {
                try {
                    android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet2 = new android.util.ArraySet<>();
                    long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                    for (int i = 0; i < arraySet.size(); i++) {
                        com.android.server.job.controllers.JobStatus valueAt = arraySet.valueAt(i);
                        if (com.android.server.job.controllers.FlexibilityController.DEBUG) {
                            android.util.Slog.d(com.android.server.job.controllers.FlexibilityController.TAG, "Alarm fired for " + valueAt.toShortString());
                        }
                        com.android.server.job.controllers.FlexibilityController.this.mFlexibilityTracker.calculateNumDroppedConstraints(valueAt, millis);
                        if (valueAt.getNumRequiredFlexibleConstraints() > 0) {
                            scheduleDropNumConstraintsAlarm(valueAt, millis);
                        }
                        if (valueAt.setFlexibilityConstraintSatisfied(millis, com.android.server.job.controllers.FlexibilityController.this.isFlexibilitySatisfiedLocked(valueAt))) {
                            arraySet2.add(valueAt);
                        }
                    }
                    com.android.server.job.controllers.FlexibilityController.this.mStateChangedListener.onControllerStateChanged(arraySet2);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class FcHandler extends android.os.Handler {
        FcHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 0:
                    removeMessages(0);
                    synchronized (com.android.server.job.controllers.FlexibilityController.this.mLock) {
                        try {
                            com.android.server.job.controllers.FlexibilityController.this.mJobsToCheck.clear();
                            com.android.server.job.controllers.FlexibilityController.this.mPackagesToCheck.clear();
                            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = new android.util.ArraySet<>();
                            int bitCount = java.lang.Integer.bitCount(com.android.server.job.controllers.FlexibilityController.this.mAppliedConstraints & 7);
                            for (int i = 0; i <= bitCount; i++) {
                                android.util.ArraySet<com.android.server.job.controllers.JobStatus> jobsByNumRequiredConstraints = com.android.server.job.controllers.FlexibilityController.this.mFlexibilityTracker.getJobsByNumRequiredConstraints(i);
                                if (jobsByNumRequiredConstraints != null) {
                                    for (int i2 = 0; i2 < jobsByNumRequiredConstraints.size(); i2++) {
                                        com.android.server.job.controllers.JobStatus valueAt = jobsByNumRequiredConstraints.valueAt(i2);
                                        if (valueAt.setFlexibilityConstraintSatisfied(millis, com.android.server.job.controllers.FlexibilityController.this.isFlexibilitySatisfiedLocked(valueAt))) {
                                            arraySet.add(valueAt);
                                        }
                                    }
                                }
                            }
                            if (arraySet.size() > 0) {
                                com.android.server.job.controllers.FlexibilityController.this.mStateChangedListener.onControllerStateChanged(arraySet);
                            }
                        } finally {
                        }
                    }
                    return;
                case 1:
                    synchronized (com.android.server.job.controllers.FlexibilityController.this.mLock) {
                        try {
                            long millis2 = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet2 = new android.util.ArraySet<>();
                            for (int size = com.android.server.job.controllers.FlexibilityController.this.mJobsToCheck.size() - 1; size >= 0; size--) {
                                com.android.server.job.controllers.JobStatus jobStatus = (com.android.server.job.controllers.JobStatus) com.android.server.job.controllers.FlexibilityController.this.mJobsToCheck.valueAt(size);
                                if (com.android.server.job.controllers.FlexibilityController.DEBUG) {
                                    android.util.Slog.d(com.android.server.job.controllers.FlexibilityController.TAG, "Checking on " + jobStatus.toShortString());
                                }
                                if (jobStatus.setFlexibilityConstraintSatisfied(millis2, com.android.server.job.controllers.FlexibilityController.this.isFlexibilitySatisfiedLocked(jobStatus))) {
                                    arraySet2.add(jobStatus);
                                }
                            }
                            com.android.server.job.controllers.FlexibilityController.this.mJobsToCheck.clear();
                            if (arraySet2.size() > 0) {
                                com.android.server.job.controllers.FlexibilityController.this.mStateChangedListener.onControllerStateChanged(arraySet2);
                            }
                        } finally {
                        }
                    }
                    return;
                case 2:
                    synchronized (com.android.server.job.controllers.FlexibilityController.this.mLock) {
                        try {
                            final long millis3 = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                            final android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet3 = new android.util.ArraySet<>();
                            com.android.server.job.controllers.FlexibilityController.this.mService.getJobStore().forEachJob(new java.util.function.Predicate() { // from class: com.android.server.job.controllers.FlexibilityController$FcHandler$$ExternalSyntheticLambda0
                                @Override // java.util.function.Predicate
                                public final boolean test(java.lang.Object obj) {
                                    boolean lambda$handleMessage$0;
                                    lambda$handleMessage$0 = com.android.server.job.controllers.FlexibilityController.FcHandler.this.lambda$handleMessage$0((com.android.server.job.controllers.JobStatus) obj);
                                    return lambda$handleMessage$0;
                                }
                            }, new java.util.function.Consumer() { // from class: com.android.server.job.controllers.FlexibilityController$FcHandler$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj) {
                                    com.android.server.job.controllers.FlexibilityController.FcHandler.this.lambda$handleMessage$1(millis3, arraySet3, (com.android.server.job.controllers.JobStatus) obj);
                                }
                            });
                            com.android.server.job.controllers.FlexibilityController.this.mPackagesToCheck.clear();
                            if (arraySet3.size() > 0) {
                                com.android.server.job.controllers.FlexibilityController.this.mStateChangedListener.onControllerStateChanged(arraySet3);
                            }
                        } finally {
                        }
                    }
                    return;
                default:
                    return;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$handleMessage$0(com.android.server.job.controllers.JobStatus jobStatus) {
            return com.android.server.job.controllers.FlexibilityController.this.mPackagesToCheck.contains(jobStatus.getSourcePackageName()) || com.android.server.job.controllers.FlexibilityController.this.mPackagesToCheck.contains(jobStatus.getCallingPackageName());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$1(long j, android.util.ArraySet arraySet, com.android.server.job.controllers.JobStatus jobStatus) {
            if (com.android.server.job.controllers.FlexibilityController.DEBUG) {
                android.util.Slog.d(com.android.server.job.controllers.FlexibilityController.TAG, "Checking on " + jobStatus.toShortString());
            }
            if (jobStatus.setFlexibilityConstraintSatisfied(j, com.android.server.job.controllers.FlexibilityController.this.isFlexibilitySatisfiedLocked(jobStatus))) {
                arraySet.add(jobStatus);
            }
        }
    }

    class FcConfig {
        static final int DEFAULT_APPLIED_CONSTRAINTS = 0;

        @com.android.internal.annotations.VisibleForTesting
        static final long DEFAULT_DEADLINE_PROXIMITY_LIMIT_MS = 900000;

        @com.android.internal.annotations.VisibleForTesting
        static final long DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_MS = 86400000;
        private static final long DEFAULT_MAX_RESCHEDULED_DEADLINE_MS = 86400000;
        private static final long DEFAULT_MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS = 60000;
        private static final long DEFAULT_RESCHEDULED_JOB_DEADLINE_MS = 3600000;

        @com.android.internal.annotations.VisibleForTesting
        static final long DEFAULT_UNSEEN_CONSTRAINT_GRACE_PERIOD_MS = 259200000;
        private static final java.lang.String FC_CONFIG_PREFIX = "fc_";

        @com.android.internal.annotations.VisibleForTesting
        static final java.lang.String KEY_APPLIED_CONSTRAINTS = "fc_applied_constraints";
        static final java.lang.String KEY_DEADLINE_PROXIMITY_LIMIT = "fc_flexibility_deadline_proximity_limit_ms";
        static final java.lang.String KEY_FALLBACK_FLEXIBILITY_DEADLINE = "fc_fallback_flexibility_deadline_ms";
        static final java.lang.String KEY_FALLBACK_FLEXIBILITY_DEADLINES = "fc_fallback_flexibility_deadlines";
        static final java.lang.String KEY_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS = "fc_fallback_flexibility_deadline_additional_score_time_factors";
        static final java.lang.String KEY_FALLBACK_FLEXIBILITY_DEADLINE_SCORES = "fc_fallback_flexibility_deadline_scores";
        static final java.lang.String KEY_MAX_RESCHEDULED_DEADLINE_MS = "fc_max_rescheduled_deadline_ms";
        static final java.lang.String KEY_MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS = "fc_min_time_between_flexibility_alarms_ms";
        static final java.lang.String KEY_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS = "fc_percents_to_drop_flexible_constraints";
        static final java.lang.String KEY_RESCHEDULED_JOB_DEADLINE_MS = "fc_rescheduled_job_deadline_ms";
        static final java.lang.String KEY_UNSEEN_CONSTRAINT_GRACE_PERIOD_MS = "fc_unseen_constraint_grace_period_ms";
        static final android.util.SparseLongArray DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES = new android.util.SparseLongArray();
        static final android.util.SparseIntArray DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES = new android.util.SparseIntArray();
        static final android.util.SparseLongArray DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS = new android.util.SparseLongArray();

        @com.android.internal.annotations.VisibleForTesting
        static final android.util.SparseArray<int[]> DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS = new android.util.SparseArray<>();
        private boolean mShouldReevaluateConstraints = false;
        public int APPLIED_CONSTRAINTS = 0;
        public long DEADLINE_PROXIMITY_LIMIT_MS = DEFAULT_DEADLINE_PROXIMITY_LIMIT_MS;
        public long FALLBACK_FLEXIBILITY_DEADLINE_MS = 86400000;
        public long MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS = 60000;
        public android.util.SparseArray<int[]> PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS = new android.util.SparseArray<>();
        public long RESCHEDULED_JOB_DEADLINE_MS = 3600000;
        public long MAX_RESCHEDULED_DEADLINE_MS = 86400000;
        public long UNSEEN_CONSTRAINT_GRACE_PERIOD_MS = DEFAULT_UNSEEN_CONSTRAINT_GRACE_PERIOD_MS;
        public final android.util.SparseLongArray FALLBACK_FLEXIBILITY_DEADLINES = new android.util.SparseLongArray();
        public final android.util.SparseIntArray FALLBACK_FLEXIBILITY_DEADLINE_SCORES = new android.util.SparseIntArray();
        public final android.util.SparseLongArray FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS = new android.util.SparseLongArray();

        static {
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES.put(500, 3600000L);
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES.put(400, 21600000L);
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES.put(300, 43200000L);
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES.put(200, 86400000L);
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES.put(100, 172800000L);
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES.put(500, 5);
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES.put(400, 4);
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES.put(300, 3);
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES.put(200, 2);
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES.put(100, 1);
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS.put(500, 0L);
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS.put(400, 180000L);
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS.put(300, 120000L);
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS.put(200, 60000L);
            DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS.put(100, 60000L);
            DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS.put(500, new int[]{1, 2, 3, 4});
            DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS.put(400, new int[]{33, 50, 60, 75});
            DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS.put(300, new int[]{50, 60, 70, 80});
            DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS.put(200, new int[]{50, 60, 70, 80});
            DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS.put(100, new int[]{55, 65, 75, 85});
        }

        FcConfig() {
            for (int i = 0; i < DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES.size(); i++) {
                this.FALLBACK_FLEXIBILITY_DEADLINES.put(DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES.keyAt(i), DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES.valueAt(i));
            }
            for (int i2 = 0; i2 < DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES.size(); i2++) {
                this.FALLBACK_FLEXIBILITY_DEADLINE_SCORES.put(DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES.keyAt(i2), DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES.valueAt(i2));
            }
            for (int i3 = 0; i3 < DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS.size(); i3++) {
                this.FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS.put(DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS.keyAt(i3), DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS.valueAt(i3));
            }
            for (int i4 = 0; i4 < DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS.size(); i4++) {
                this.PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS.put(DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS.keyAt(i4), DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS.valueAt(i4));
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void processConstantLocked(@android.annotation.NonNull android.provider.DeviceConfig.Properties properties, @android.annotation.NonNull java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case -2004789501:
                    if (str.equals(KEY_MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -1573718613:
                    if (str.equals(KEY_MAX_RESCHEDULED_DEADLINE_MS)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -790107353:
                    if (str.equals(KEY_FALLBACK_FLEXIBILITY_DEADLINE_SCORES)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -540379004:
                    if (str.equals(KEY_RESCHEDULED_JOB_DEADLINE_MS)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 100307866:
                    if (str.equals(KEY_FALLBACK_FLEXIBILITY_DEADLINES)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 581236233:
                    if (str.equals(KEY_DEADLINE_PROXIMITY_LIMIT)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 659395101:
                    if (str.equals(KEY_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 746985028:
                    if (str.equals(KEY_UNSEEN_CONSTRAINT_GRACE_PERIOD_MS)) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case 1815875441:
                    if (str.equals(KEY_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1821391602:
                    if (str.equals(KEY_APPLIED_CONSTRAINTS)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1906562988:
                    if (str.equals(KEY_FALLBACK_FLEXIBILITY_DEADLINE)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    this.APPLIED_CONSTRAINTS = properties.getInt(str, 0) & com.android.server.job.controllers.FlexibilityController.this.mSupportedFlexConstraints;
                    if (com.android.server.job.controllers.FlexibilityController.this.mAppliedConstraints != this.APPLIED_CONSTRAINTS) {
                        com.android.server.job.controllers.FlexibilityController.this.mAppliedConstraints = this.APPLIED_CONSTRAINTS;
                        this.mShouldReevaluateConstraints = true;
                        if (com.android.server.job.controllers.FlexibilityController.this.mAppliedConstraints != 0) {
                            com.android.server.job.controllers.FlexibilityController.this.mFlexibilityEnabled = true;
                            com.android.server.job.controllers.FlexibilityController.this.mPrefetchController.registerPrefetchChangedListener(com.android.server.job.controllers.FlexibilityController.this.mPrefetchChangedListener);
                            com.android.server.job.controllers.FlexibilityController.this.mSpecialAppTracker.startTracking();
                            break;
                        } else {
                            com.android.server.job.controllers.FlexibilityController.this.mFlexibilityEnabled = false;
                            com.android.server.job.controllers.FlexibilityController.this.mPrefetchController.unRegisterPrefetchChangedListener(com.android.server.job.controllers.FlexibilityController.this.mPrefetchChangedListener);
                            com.android.server.job.controllers.FlexibilityController.this.mSpecialAppTracker.stopTracking();
                            break;
                        }
                    }
                    break;
                case 1:
                    this.RESCHEDULED_JOB_DEADLINE_MS = properties.getLong(str, 3600000L);
                    if (com.android.server.job.controllers.FlexibilityController.this.mRescheduledJobDeadline != this.RESCHEDULED_JOB_DEADLINE_MS) {
                        com.android.server.job.controllers.FlexibilityController.this.mRescheduledJobDeadline = this.RESCHEDULED_JOB_DEADLINE_MS;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case 2:
                    this.MAX_RESCHEDULED_DEADLINE_MS = properties.getLong(str, 86400000L);
                    if (com.android.server.job.controllers.FlexibilityController.this.mMaxRescheduledDeadline != this.MAX_RESCHEDULED_DEADLINE_MS) {
                        com.android.server.job.controllers.FlexibilityController.this.mMaxRescheduledDeadline = this.MAX_RESCHEDULED_DEADLINE_MS;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case 3:
                    this.DEADLINE_PROXIMITY_LIMIT_MS = properties.getLong(str, DEFAULT_DEADLINE_PROXIMITY_LIMIT_MS);
                    if (com.android.server.job.controllers.FlexibilityController.this.mDeadlineProximityLimitMs != this.DEADLINE_PROXIMITY_LIMIT_MS) {
                        com.android.server.job.controllers.FlexibilityController.this.mDeadlineProximityLimitMs = this.DEADLINE_PROXIMITY_LIMIT_MS;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case 4:
                    this.FALLBACK_FLEXIBILITY_DEADLINE_MS = properties.getLong(str, 86400000L);
                    if (com.android.server.job.controllers.FlexibilityController.this.mFallbackFlexibilityDeadlineMs != this.FALLBACK_FLEXIBILITY_DEADLINE_MS) {
                        com.android.server.job.controllers.FlexibilityController.this.mFallbackFlexibilityDeadlineMs = this.FALLBACK_FLEXIBILITY_DEADLINE_MS;
                        break;
                    }
                    break;
                case 5:
                    if (parsePriorityToLongKeyValueString(properties.getString(str, (java.lang.String) null), this.FALLBACK_FLEXIBILITY_DEADLINES, DEFAULT_FALLBACK_FLEXIBILITY_DEADLINES)) {
                        com.android.server.job.controllers.FlexibilityController.this.mFallbackFlexibilityDeadlines = this.FALLBACK_FLEXIBILITY_DEADLINES;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case 6:
                    if (parsePriorityToIntKeyValueString(properties.getString(str, (java.lang.String) null), this.FALLBACK_FLEXIBILITY_DEADLINE_SCORES, DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_SCORES)) {
                        com.android.server.job.controllers.FlexibilityController.this.mFallbackFlexibilityDeadlineScores = this.FALLBACK_FLEXIBILITY_DEADLINE_SCORES;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case 7:
                    if (parsePriorityToLongKeyValueString(properties.getString(str, (java.lang.String) null), this.FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS, DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS)) {
                        com.android.server.job.controllers.FlexibilityController.this.mFallbackFlexibilityAdditionalScoreTimeFactors = this.FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case '\b':
                    this.MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS = properties.getLong(str, 60000L);
                    if (com.android.server.job.controllers.FlexibilityController.this.mMinTimeBetweenFlexibilityAlarmsMs != this.MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS) {
                        com.android.server.job.controllers.FlexibilityController.this.mMinTimeBetweenFlexibilityAlarmsMs = this.MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS;
                        com.android.server.job.controllers.FlexibilityController.this.mFlexibilityAlarmQueue.setMinTimeBetweenAlarmsMs(this.MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS);
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case '\t':
                    this.UNSEEN_CONSTRAINT_GRACE_PERIOD_MS = properties.getLong(str, DEFAULT_UNSEEN_CONSTRAINT_GRACE_PERIOD_MS);
                    if (com.android.server.job.controllers.FlexibilityController.this.mUnseenConstraintGracePeriodMs != this.UNSEEN_CONSTRAINT_GRACE_PERIOD_MS) {
                        com.android.server.job.controllers.FlexibilityController.this.mUnseenConstraintGracePeriodMs = this.UNSEEN_CONSTRAINT_GRACE_PERIOD_MS;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
                case '\n':
                    if (parsePercentToDropKeyValueString(properties.getString(str, (java.lang.String) null), this.PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS, DEFAULT_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS)) {
                        com.android.server.job.controllers.FlexibilityController.this.mPercentsToDropConstraints = this.PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS;
                        this.mShouldReevaluateConstraints = true;
                        break;
                    }
                    break;
            }
        }

        private boolean parsePercentToDropKeyValueString(@android.annotation.Nullable java.lang.String str, android.util.SparseArray<int[]> sparseArray, android.util.SparseArray<int[]> sparseArray2) {
            int i;
            int i2;
            int i3;
            int i4;
            android.util.KeyValueListParser keyValueListParser = new android.util.KeyValueListParser(',');
            try {
                keyValueListParser.setString(str);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.wtf(com.android.server.job.controllers.FlexibilityController.TAG, "Bad percent to drop key value string given", e);
                keyValueListParser.setString((java.lang.String) null);
            }
            int[] iArr = sparseArray.get(500);
            int[] iArr2 = sparseArray.get(400);
            int[] iArr3 = sparseArray.get(300);
            int[] iArr4 = sparseArray.get(200);
            int[] iArr5 = sparseArray.get(100);
            int[] parsePercentToDropString = parsePercentToDropString(keyValueListParser.getString(java.lang.String.valueOf(500), (java.lang.String) null));
            int[] parsePercentToDropString2 = parsePercentToDropString(keyValueListParser.getString(java.lang.String.valueOf(400), (java.lang.String) null));
            int[] parsePercentToDropString3 = parsePercentToDropString(keyValueListParser.getString(java.lang.String.valueOf(300), (java.lang.String) null));
            int[] parsePercentToDropString4 = parsePercentToDropString(keyValueListParser.getString(java.lang.String.valueOf(200), (java.lang.String) null));
            int[] parsePercentToDropString5 = parsePercentToDropString(keyValueListParser.getString(java.lang.String.valueOf(100), (java.lang.String) null));
            if (parsePercentToDropString == null) {
                i = 500;
                parsePercentToDropString = sparseArray2.get(500);
            } else {
                i = 500;
            }
            sparseArray.put(i, parsePercentToDropString);
            if (parsePercentToDropString2 == null) {
                i2 = 400;
                parsePercentToDropString2 = sparseArray2.get(400);
            } else {
                i2 = 400;
            }
            sparseArray.put(i2, parsePercentToDropString2);
            if (parsePercentToDropString3 == null) {
                i3 = 300;
                parsePercentToDropString3 = sparseArray2.get(300);
            } else {
                i3 = 300;
            }
            sparseArray.put(i3, parsePercentToDropString3);
            if (parsePercentToDropString4 == null) {
                i4 = 200;
                parsePercentToDropString4 = sparseArray2.get(200);
            } else {
                i4 = 200;
            }
            sparseArray.put(i4, parsePercentToDropString4);
            if (parsePercentToDropString5 == null) {
                parsePercentToDropString5 = sparseArray2.get(100);
            }
            sparseArray.put(100, parsePercentToDropString5);
            return (java.util.Arrays.equals(iArr, sparseArray.get(500)) && java.util.Arrays.equals(iArr2, sparseArray.get(400)) && java.util.Arrays.equals(iArr3, sparseArray.get(300)) && java.util.Arrays.equals(iArr4, sparseArray.get(200)) && java.util.Arrays.equals(iArr5, sparseArray.get(100))) ? false : true;
        }

        @android.annotation.Nullable
        private int[] parsePercentToDropString(@android.annotation.Nullable java.lang.String str) {
            if (str == null || str.isEmpty()) {
                return null;
            }
            java.lang.String[] split = str.split("\\|");
            int bitCount = java.lang.Integer.bitCount(com.android.server.job.controllers.FlexibilityController.FLEXIBLE_CONSTRAINTS);
            int[] iArr = new int[bitCount];
            if (bitCount != split.length) {
                return null;
            }
            int i = 0;
            for (int i2 = 0; i2 < split.length; i2++) {
                try {
                    iArr[i2] = java.lang.Integer.parseInt(split[i2]);
                    if (iArr[i2] < i) {
                        android.util.Slog.wtf(com.android.server.job.controllers.FlexibilityController.TAG, "Percents to drop constraints were not in increasing order.");
                        return null;
                    }
                    if (iArr[i2] > 100) {
                        android.util.Slog.e(com.android.server.job.controllers.FlexibilityController.TAG, "Found % over 100");
                        return null;
                    }
                    i = iArr[i2];
                } catch (java.lang.NumberFormatException e) {
                    android.util.Slog.e(com.android.server.job.controllers.FlexibilityController.TAG, "Provided string was improperly formatted.", e);
                    return null;
                }
            }
            return iArr;
        }

        private boolean parsePriorityToIntKeyValueString(@android.annotation.Nullable java.lang.String str, android.util.SparseIntArray sparseIntArray, android.util.SparseIntArray sparseIntArray2) {
            android.util.KeyValueListParser keyValueListParser = new android.util.KeyValueListParser(',');
            try {
                keyValueListParser.setString(str);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.wtf(com.android.server.job.controllers.FlexibilityController.TAG, "Bad string given", e);
                keyValueListParser.setString((java.lang.String) null);
            }
            int i = sparseIntArray.get(500);
            int i2 = sparseIntArray.get(400);
            int i3 = sparseIntArray.get(300);
            int i4 = sparseIntArray.get(200);
            int i5 = sparseIntArray.get(100);
            int i6 = keyValueListParser.getInt(java.lang.String.valueOf(500), sparseIntArray2.get(500));
            int i7 = keyValueListParser.getInt(java.lang.String.valueOf(400), sparseIntArray2.get(400));
            int i8 = keyValueListParser.getInt(java.lang.String.valueOf(300), sparseIntArray2.get(300));
            int i9 = keyValueListParser.getInt(java.lang.String.valueOf(200), sparseIntArray2.get(200));
            int i10 = keyValueListParser.getInt(java.lang.String.valueOf(100), sparseIntArray2.get(100));
            sparseIntArray.put(500, i6);
            sparseIntArray.put(400, i7);
            sparseIntArray.put(300, i8);
            sparseIntArray.put(200, i9);
            sparseIntArray.put(100, i10);
            return (i == i6 && i2 == i7 && i3 == i8 && i4 == i9 && i5 == i10) ? false : true;
        }

        private boolean parsePriorityToLongKeyValueString(@android.annotation.Nullable java.lang.String str, android.util.SparseLongArray sparseLongArray, android.util.SparseLongArray sparseLongArray2) {
            android.util.KeyValueListParser keyValueListParser = new android.util.KeyValueListParser(',');
            try {
                keyValueListParser.setString(str);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Slog.wtf(com.android.server.job.controllers.FlexibilityController.TAG, "Bad string given", e);
                keyValueListParser.setString((java.lang.String) null);
            }
            long j = sparseLongArray.get(500);
            long j2 = sparseLongArray.get(400);
            long j3 = sparseLongArray.get(300);
            long j4 = sparseLongArray.get(200);
            long j5 = sparseLongArray.get(100);
            long j6 = keyValueListParser.getLong(java.lang.String.valueOf(500), sparseLongArray2.get(500));
            long j7 = keyValueListParser.getLong(java.lang.String.valueOf(400), sparseLongArray2.get(400));
            long j8 = keyValueListParser.getLong(java.lang.String.valueOf(300), sparseLongArray2.get(300));
            long j9 = keyValueListParser.getLong(java.lang.String.valueOf(200), sparseLongArray2.get(200));
            long j10 = keyValueListParser.getLong(java.lang.String.valueOf(100), sparseLongArray2.get(100));
            sparseLongArray.put(500, j6);
            sparseLongArray.put(400, j7);
            sparseLongArray.put(300, j8);
            sparseLongArray.put(200, j9);
            sparseLongArray.put(100, j10);
            return (j == j6 && j2 == j7 && j3 == j8 && j4 == j9 && j5 == j10) ? false : true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println();
            indentingPrintWriter.print(com.android.server.job.controllers.FlexibilityController.class.getSimpleName());
            indentingPrintWriter.println(":");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print(KEY_APPLIED_CONSTRAINTS, java.lang.Integer.valueOf(this.APPLIED_CONSTRAINTS));
            indentingPrintWriter.print("(");
            if (this.APPLIED_CONSTRAINTS != 0) {
                com.android.server.job.controllers.JobStatus.dumpConstraints(indentingPrintWriter, this.APPLIED_CONSTRAINTS);
            } else {
                indentingPrintWriter.print("nothing");
            }
            indentingPrintWriter.println(")");
            indentingPrintWriter.print(KEY_DEADLINE_PROXIMITY_LIMIT, java.lang.Long.valueOf(this.DEADLINE_PROXIMITY_LIMIT_MS)).println();
            indentingPrintWriter.print(KEY_FALLBACK_FLEXIBILITY_DEADLINE, java.lang.Long.valueOf(this.FALLBACK_FLEXIBILITY_DEADLINE_MS)).println();
            indentingPrintWriter.print(KEY_FALLBACK_FLEXIBILITY_DEADLINES, this.FALLBACK_FLEXIBILITY_DEADLINES).println();
            indentingPrintWriter.print(KEY_FALLBACK_FLEXIBILITY_DEADLINE_SCORES, this.FALLBACK_FLEXIBILITY_DEADLINE_SCORES).println();
            indentingPrintWriter.print(KEY_FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS, this.FALLBACK_FLEXIBILITY_DEADLINE_ADDITIONAL_SCORE_TIME_FACTORS).println();
            indentingPrintWriter.print(KEY_MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS, java.lang.Long.valueOf(this.MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS)).println();
            indentingPrintWriter.print(KEY_PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS, this.PERCENTS_TO_DROP_FLEXIBLE_CONSTRAINTS).println();
            indentingPrintWriter.print(KEY_RESCHEDULED_JOB_DEADLINE_MS, java.lang.Long.valueOf(this.RESCHEDULED_JOB_DEADLINE_MS)).println();
            indentingPrintWriter.print(KEY_MAX_RESCHEDULED_DEADLINE_MS, java.lang.Long.valueOf(this.MAX_RESCHEDULED_DEADLINE_MS)).println();
            indentingPrintWriter.print(KEY_UNSEEN_CONSTRAINT_GRACE_PERIOD_MS, java.lang.Long.valueOf(this.UNSEEN_CONSTRAINT_GRACE_PERIOD_MS)).println();
            indentingPrintWriter.decreaseIndent();
        }
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    com.android.server.job.controllers.FlexibilityController.FcConfig getFcConfig() {
        return this.mFcConfig;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SpecialAppTracker {
        private final android.content.BroadcastReceiver mBroadcastReceiver;
        private com.android.server.DeviceIdleInternal mDeviceIdleInternal;

        @com.android.internal.annotations.GuardedBy({"mSatLock"})
        private final android.util.ArraySet<java.lang.String> mPowerAllowlistedApps;
        private final java.lang.Object mSatLock;
        private final android.util.SparseSetArray<java.lang.String> mSpecialApps;

        private SpecialAppTracker() {
            this.mSatLock = new java.lang.Object();
            this.mSpecialApps = new android.util.SparseSetArray<>();
            this.mPowerAllowlistedApps = new android.util.ArraySet<>();
            this.mBroadcastReceiver = new com.android.server.job.controllers.FlexibilityController.SpecialAppTracker.AnonymousClass1();
        }

        /* renamed from: com.android.server.job.controllers.FlexibilityController$SpecialAppTracker$1, reason: invalid class name */
        class AnonymousClass1 extends android.content.BroadcastReceiver {
            AnonymousClass1() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                char c;
                java.lang.String action = intent.getAction();
                switch (action.hashCode()) {
                    case -65633567:
                        if (action.equals("android.os.action.POWER_SAVE_WHITELIST_CHANGED")) {
                            c = 0;
                            break;
                        }
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        com.android.server.job.controllers.FlexibilityController.FcHandler fcHandler = com.android.server.job.controllers.FlexibilityController.this.mHandler;
                        final com.android.server.job.controllers.FlexibilityController.SpecialAppTracker specialAppTracker = com.android.server.job.controllers.FlexibilityController.SpecialAppTracker.this;
                        fcHandler.post(new java.lang.Runnable() { // from class: com.android.server.job.controllers.FlexibilityController$SpecialAppTracker$1$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.job.controllers.FlexibilityController.SpecialAppTracker.this.updatePowerAllowlistCache();
                            }
                        });
                        break;
                }
            }
        }

        public boolean isSpecialApp(int i, @android.annotation.NonNull java.lang.String str) {
            synchronized (this.mSatLock) {
                try {
                    if (this.mSpecialApps.contains(-1, str)) {
                        return true;
                    }
                    return this.mSpecialApps.contains(i, str);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private boolean isSpecialAppInternal(int i, @android.annotation.NonNull java.lang.String str) {
            synchronized (this.mSatLock) {
                try {
                    return this.mPowerAllowlistedApps.contains(str);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onAppRemoved(int i, java.lang.String str) {
            synchronized (this.mSatLock) {
                this.mSpecialApps.remove(i, str);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onSystemServicesReady() {
            this.mDeviceIdleInternal = (com.android.server.DeviceIdleInternal) com.android.server.LocalServices.getService(com.android.server.DeviceIdleInternal.class);
            synchronized (com.android.server.job.controllers.FlexibilityController.this.mLock) {
                try {
                    if (com.android.server.job.controllers.FlexibilityController.this.mFlexibilityEnabled) {
                        com.android.server.job.controllers.FlexibilityController.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.job.controllers.FlexibilityController$SpecialAppTracker$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.job.controllers.FlexibilityController.SpecialAppTracker.this.updatePowerAllowlistCache();
                            }
                        });
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void onUserRemoved(int i) {
            synchronized (this.mSatLock) {
                this.mSpecialApps.remove(i);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void startTracking() {
            com.android.server.job.controllers.FlexibilityController.this.mContext.registerReceiver(this.mBroadcastReceiver, new android.content.IntentFilter("android.os.action.POWER_SAVE_WHITELIST_CHANGED"));
            updatePowerAllowlistCache();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void stopTracking() {
            com.android.server.job.controllers.FlexibilityController.this.mContext.unregisterReceiver(this.mBroadcastReceiver);
            synchronized (this.mSatLock) {
                this.mPowerAllowlistedApps.clear();
                this.mSpecialApps.clear();
            }
        }

        private void updateSpecialAppSetUnlocked(int i, @android.annotation.NonNull android.util.ArraySet<java.lang.String> arraySet) {
            if (java.lang.Thread.holdsLock(this.mSatLock)) {
                throw new java.lang.IllegalStateException("Must never hold local mSatLock");
            }
            if (arraySet.size() == 0) {
                return;
            }
            android.util.ArraySet arraySet2 = new android.util.ArraySet();
            synchronized (this.mSatLock) {
                try {
                    for (int size = arraySet.size() - 1; size >= 0; size--) {
                        java.lang.String valueAt = arraySet.valueAt(size);
                        if (isSpecialAppInternal(i, valueAt)) {
                            if (this.mSpecialApps.add(i, valueAt)) {
                                arraySet2.add(valueAt);
                            }
                        } else if (this.mSpecialApps.remove(i, valueAt)) {
                            arraySet2.add(valueAt);
                        }
                    }
                } finally {
                }
            }
            if (arraySet2.size() > 0) {
                synchronized (com.android.server.job.controllers.FlexibilityController.this.mLock) {
                    com.android.server.job.controllers.FlexibilityController.this.mPackagesToCheck.addAll(arraySet2);
                    com.android.server.job.controllers.FlexibilityController.this.mHandler.sendEmptyMessage(2);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updatePowerAllowlistCache() {
            if (this.mDeviceIdleInternal == null) {
                return;
            }
            java.lang.String[] fullPowerWhitelistExceptIdle = this.mDeviceIdleInternal.getFullPowerWhitelistExceptIdle();
            android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
            synchronized (this.mSatLock) {
                try {
                    arraySet.addAll((android.util.ArraySet<? extends java.lang.String>) this.mPowerAllowlistedApps);
                    this.mPowerAllowlistedApps.clear();
                    for (java.lang.String str : fullPowerWhitelistExceptIdle) {
                        this.mPowerAllowlistedApps.add(str);
                        if (!arraySet.remove(str)) {
                            arraySet.add(str);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            updateSpecialAppSetUnlocked(-1, arraySet);
        }

        public void dump(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("Special apps:");
            indentingPrintWriter.increaseIndent();
            synchronized (this.mSatLock) {
                for (int i = 0; i < this.mSpecialApps.size(); i++) {
                    try {
                        indentingPrintWriter.print(this.mSpecialApps.keyAt(i));
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(this.mSpecialApps.valuesAt(i));
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
                indentingPrintWriter.println();
                indentingPrintWriter.print("Power allowlisted packages: ");
                indentingPrintWriter.println(this.mPowerAllowlistedApps);
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void dumpConstants(android.util.IndentingPrintWriter indentingPrintWriter) {
        this.mFcConfig.dump(indentingPrintWriter);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void dumpControllerStateLocked(final android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        indentingPrintWriter.print("Satisfied Flexible Constraints:");
        com.android.server.job.controllers.JobStatus.dumpConstraints(indentingPrintWriter, this.mSatisfiedFlexibleConstraints);
        indentingPrintWriter.println();
        indentingPrintWriter.println();
        final long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        indentingPrintWriter.println("Time since constraint combos last seen:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mLastSeenConstraintTimesElapsed.size(); i++) {
            int keyAt = this.mLastSeenConstraintTimesElapsed.keyAt(i);
            if (keyAt == this.mSatisfiedFlexibleConstraints) {
                indentingPrintWriter.print("0ms");
            } else {
                android.util.TimeUtils.formatDuration(this.mLastSeenConstraintTimesElapsed.valueAt(i), millis, indentingPrintWriter);
            }
            indentingPrintWriter.print(":");
            if (keyAt != 0) {
                com.android.server.job.controllers.JobStatus.dumpConstraints(indentingPrintWriter, keyAt);
            } else {
                indentingPrintWriter.print(" none");
            }
            indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        this.mSpecialAppTracker.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        this.mFlexibilityTracker.dump(indentingPrintWriter, predicate, millis);
        indentingPrintWriter.println();
        indentingPrintWriter.println("Job scores:");
        indentingPrintWriter.increaseIndent();
        this.mJobScoreTrackers.forEach(new android.util.SparseArrayMap.TriConsumer() { // from class: com.android.server.job.controllers.FlexibilityController$$ExternalSyntheticLambda0
            public final void accept(int i2, java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.job.controllers.FlexibilityController.lambda$dumpControllerStateLocked$1(indentingPrintWriter, millis, i2, (java.lang.String) obj, (com.android.server.job.controllers.FlexibilityController.JobScoreTracker) obj2);
            }
        });
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        this.mFlexibilityAlarmQueue.dump(indentingPrintWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpControllerStateLocked$1(android.util.IndentingPrintWriter indentingPrintWriter, long j, int i, java.lang.String str, com.android.server.job.controllers.FlexibilityController.JobScoreTracker jobScoreTracker) {
        indentingPrintWriter.print(i);
        indentingPrintWriter.print(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
        indentingPrintWriter.print(str);
        indentingPrintWriter.print(": ");
        jobScoreTracker.dump(indentingPrintWriter, j);
        indentingPrintWriter.println();
    }
}
