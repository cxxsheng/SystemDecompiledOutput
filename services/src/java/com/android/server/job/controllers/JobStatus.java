package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public final class JobStatus {
    static final int CONSTRAINTS_OF_INTEREST = -1801453553;
    static final int CONSTRAINT_BACKGROUND_NOT_RESTRICTED = 4194304;
    public static final int CONSTRAINT_BATTERY_NOT_LOW = 2;
    public static final int CONSTRAINT_CHARGING = 1;
    public static final int CONSTRAINT_CONNECTIVITY = 268435456;
    public static final int CONSTRAINT_CONTENT_TRIGGER = 67108864;
    public static final int CONSTRAINT_DEADLINE = 1073741824;
    static final int CONSTRAINT_DEVICE_NOT_DOZING = 33554432;
    public static final int CONSTRAINT_FLEXIBLE = 2097152;
    public static final int CONSTRAINT_IDLE = 4;
    static final int CONSTRAINT_PREFETCH = 8388608;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public static final int CONSTRAINT_STORAGE_NOT_LOW = 8;
    static final int CONSTRAINT_TARE_WEALTH = 134217728;
    public static final int CONSTRAINT_TIMING_DELAY = Integer.MIN_VALUE;
    static final int CONSTRAINT_WITHIN_QUOTA = 16777216;
    static final boolean DEBUG_PREPARE = true;
    public static final long DEFAULT_TRIGGER_MAX_DELAY = 120000;
    public static final long DEFAULT_TRIGGER_UPDATE_DELAY = 10000;
    private static final int DYNAMIC_EXPEDITED_DEFERRAL_CONSTRAINTS = 37748736;
    private static final int DYNAMIC_RESTRICTED_CONSTRAINTS = 268435463;
    private static final int IMPLICIT_CONSTRAINTS = 190840832;
    public static final int INTERNAL_FLAG_DEMOTED_BY_SYSTEM_UIJ = 4;
    public static final int INTERNAL_FLAG_DEMOTED_BY_USER = 2;
    public static final int INTERNAL_FLAG_HAS_FOREGROUND_EXEMPTION = 1;
    private static final int MAX_NAMESPACE_CACHE_SIZE = 128;
    public static final long MIN_TRIGGER_MAX_DELAY = 1000;
    public static final long MIN_TRIGGER_UPDATE_DELAY = 500;
    public static final long NO_EARLIEST_RUNTIME = 0;
    public static final long NO_LATEST_RUNTIME = Long.MAX_VALUE;
    private static final int NUM_CONSTRAINT_CHANGE_HISTORY = 10;
    public static final int OVERRIDE_FULL = 3;
    public static final int OVERRIDE_NONE = 0;
    public static final int OVERRIDE_SOFT = 2;
    public static final int OVERRIDE_SORTING = 1;
    static final int SOFT_OVERRIDE_CONSTRAINTS = -2136997873;
    private static final int STATSD_CONSTRAINTS_TO_LOG = -847249408;
    private static final boolean STATS_LOG_ENABLED = false;
    private static final java.lang.String TAG = "JobScheduler.JobStatus";
    public static final int TRACKING_BATTERY = 1;
    public static final int TRACKING_CONNECTIVITY = 2;
    public static final int TRACKING_CONTENT = 4;
    public static final int TRACKING_FLEXIBILITY = 128;
    public static final int TRACKING_IDLE = 8;
    public static final int TRACKING_QUOTA = 64;
    public static final int TRACKING_STORAGE = 16;
    public static final int TRACKING_TIME = 32;
    private static java.security.MessageDigest sMessageDigest;
    boolean appHasDozeExemption;
    final java.lang.String batteryName;
    final int callingUid;
    public android.util.ArraySet<java.lang.String> changedAuthorities;
    public android.util.ArraySet<android.net.Uri> changedUris;
    com.android.server.job.controllers.ContentObserverController.JobInstance contentObserverJobInstance;
    private final long earliestRunTimeElapsedMillis;
    public long enqueueTime;
    public java.util.ArrayList<android.app.job.JobWorkItem> executingWork;
    final android.app.job.JobInfo job;
    public int lastEvaluatedBias;
    private final long latestRunTimeElapsedMillis;
    private final boolean mCanApplyTransportAffinities;
    private int mConstraintChangeHistoryIndex;
    private final int[] mConstraintStatusHistory;
    private final long[] mConstraintUpdatedTimesElapsed;
    private long mCumulativeExecutionTimeMs;
    private int mDynamicConstraints;
    private boolean mExpeditedQuotaApproved;
    private boolean mExpeditedTareApproved;

    @android.annotation.Nullable
    private java.lang.String[] mFilteredDebugTags;

    @android.annotation.Nullable
    private java.lang.String mFilteredTraceTag;
    private long mFirstForceBatchedTimeElapsed;
    private final boolean mHasExemptedMediaUrisOnly;
    private boolean mHasMediaBackupExemption;
    private int mInternalFlags;
    private boolean mIsDowngradedDueToBuggyApp;
    final boolean mIsProxyJob;
    private boolean mIsUserBgRestricted;
    private com.android.server.job.JobSchedulerInternal mJobSchedulerInternal;
    private long mLastFailedRunTime;
    private long mLastSuccessfulRunTime;
    private boolean mLoggedBucketMismatch;
    private final long mLoggingJobId;
    private long mMinimumNetworkChunkBytes;

    @android.annotation.Nullable
    private final java.lang.String mNamespace;

    @android.annotation.Nullable
    private final java.lang.String mNamespaceHash;
    private int mNumAppliedFlexibleConstraints;
    private int mNumDroppedFlexibleConstraints;
    private final int mNumSystemStops;
    private long mOriginalLatestRunTimeElapsedMillis;
    private android.util.Pair<java.lang.Long, java.lang.Long> mPersistedUtcTimes;
    private boolean mReadyDeadlineSatisfied;
    private boolean mReadyDynamicSatisfied;
    private boolean mReadyNotDozing;
    private boolean mReadyNotRestrictedInBg;
    private boolean mReadyTareWealth;
    private boolean mReadyWithinQuota;
    private int mReasonReadyToUnready;
    private final int mRequiredConstraintsOfInterest;
    private int mSatisfiedConstraintsOfInterest;
    private long mTotalNetworkDownloadBytes;
    private long mTotalNetworkUploadBytes;
    private boolean mTransportAffinitiesSatisfied;
    private android.app.job.UserVisibleJobSummary mUserVisibleJobSummary;

    @android.annotation.Nullable
    private java.lang.String mWakelockTag;
    public long madeActive;
    public long madePending;
    public android.net.Network network;
    public int nextPendingWorkId;
    private final int numFailures;
    public int overrideState;
    public java.util.ArrayList<android.app.job.JobWorkItem> pendingWork;
    private boolean prepared;
    final int requiredConstraints;
    int satisfiedConstraints;
    public java.lang.String serviceProcessName;
    final java.lang.String sourcePackageName;
    final java.lang.String sourceTag;
    final int sourceUid;
    final int sourceUserId;
    private int standbyBucket;
    public boolean startedAsExpeditedJob;
    public boolean startedAsUserInitiatedJob;
    public boolean startedWithForegroundFlag;
    public boolean startedWithImmediacyPrivilege;
    private int trackingControllers;
    public boolean uidActive;
    private java.lang.Throwable unpreparedPoint;
    private com.android.server.job.GrantedUriPermissions uriPerms;
    private long whenStandbyDeferred;
    static final boolean DEBUG = com.android.server.job.JobSchedulerService.DEBUG;

    @com.android.internal.annotations.GuardedBy({"sNamespaceHashCache"})
    private static final android.util.ArrayMap<java.lang.String, java.lang.String> sNamespaceHashCache = new android.util.ArrayMap<>();
    private static final android.net.Uri[] MEDIA_URIS_FOR_STANDBY_EXEMPTION = {android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI};
    private static final android.util.ArrayMap<java.util.regex.Pattern, java.lang.String> BASIC_PII_FILTERS = new android.util.ArrayMap<>();

    static {
        BASIC_PII_FILTERS.put(android.util.Patterns.EMAIL_ADDRESS, "[EMAIL]");
        BASIC_PII_FILTERS.put(android.util.Patterns.PHONE, "[PHONE]");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0070  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private JobStatus(android.app.job.JobInfo jobInfo, int i, java.lang.String str, int i2, int i3, @android.annotation.Nullable java.lang.String str2, java.lang.String str3, int i4, int i5, long j, long j2, long j3, long j4, long j5, int i6, int i7) {
        int i8;
        android.app.job.JobInfo jobInfo2;
        java.lang.String str4;
        java.lang.String str5;
        int constraintFlags;
        boolean z;
        this.unpreparedPoint = null;
        this.satisfiedConstraints = 0;
        this.mSatisfiedConstraintsOfInterest = 0;
        this.mDynamicConstraints = 0;
        this.startedAsExpeditedJob = false;
        this.startedAsUserInitiatedJob = false;
        this.startedWithForegroundFlag = false;
        this.startedWithImmediacyPrivilege = false;
        this.nextPendingWorkId = 1;
        this.overrideState = 0;
        this.mConstraintChangeHistoryIndex = 0;
        this.mConstraintUpdatedTimesElapsed = new long[10];
        this.mConstraintStatusHistory = new int[10];
        this.mTotalNetworkDownloadBytes = -1L;
        this.mTotalNetworkUploadBytes = -1L;
        this.mMinimumNetworkChunkBytes = -1L;
        this.mReasonReadyToUnready = 0;
        this.callingUid = i;
        this.standbyBucket = i3;
        this.mNamespace = str2;
        this.mNamespaceHash = generateNamespaceHash(str2);
        this.mLoggingJobId = generateLoggingId(str2, jobInfo.getId());
        if (i2 != -1 && str != null) {
            try {
                i8 = android.app.AppGlobals.getPackageManager().getPackageUid(str, 0L, i2);
            } catch (android.os.RemoteException e) {
            }
            if (i8 != -1) {
                this.sourceUid = i;
                this.sourceUserId = android.os.UserHandle.getUserId(i);
                this.sourcePackageName = jobInfo.getService().getPackageName();
                this.sourceTag = null;
            } else {
                this.sourceUid = i8;
                this.sourceUserId = i2;
                this.sourcePackageName = str;
                this.sourceTag = str3;
            }
            if (jobInfo.getRequiredNetwork() != null) {
                jobInfo2 = jobInfo;
            } else {
                android.app.job.JobInfo.Builder builder = new android.app.job.JobInfo.Builder(jobInfo);
                builder.setRequiredNetwork(new android.net.NetworkRequest.Builder(jobInfo.getRequiredNetwork()).setUids(java.util.Collections.singleton(new android.util.Range(java.lang.Integer.valueOf(this.sourceUid), java.lang.Integer.valueOf(this.sourceUid)))).build());
                jobInfo2 = builder.build(false, false, false, false);
            }
            this.job = jobInfo2;
            if (str2 != null) {
                str4 = "";
            } else {
                str4 = "@" + str2 + "@";
            }
            if (this.sourceTag == null) {
                str5 = str4 + this.sourceTag + ":" + jobInfo2.getService().getPackageName();
            } else {
                str5 = str4 + jobInfo2.getService().flattenToShortString();
            }
            this.batteryName = str5;
            this.mIsProxyJob = !this.sourcePackageName.equals(jobInfo2.getService().getPackageName());
            this.earliestRunTimeElapsedMillis = j;
            this.latestRunTimeElapsedMillis = j2;
            this.mOriginalLatestRunTimeElapsedMillis = j2;
            this.numFailures = i4;
            this.mNumSystemStops = i5;
            constraintFlags = jobInfo2.getConstraintFlags();
            constraintFlags = jobInfo2.getRequiredNetwork() != null ? constraintFlags | 268435456 : constraintFlags;
            constraintFlags = j != 0 ? constraintFlags | Integer.MIN_VALUE : constraintFlags;
            constraintFlags = j2 != NO_LATEST_RUNTIME ? constraintFlags | 1073741824 : constraintFlags;
            constraintFlags = jobInfo2.isPrefetch() ? constraintFlags | 8388608 : constraintFlags;
            if (jobInfo2.getTriggerContentUris() != null) {
                z = false;
            } else {
                constraintFlags |= 67108864;
                android.app.job.JobInfo.TriggerContentUri[] triggerContentUris = jobInfo2.getTriggerContentUris();
                int length = triggerContentUris.length;
                int i9 = 0;
                while (true) {
                    if (i9 >= length) {
                        z = true;
                        break;
                    }
                    if (com.android.internal.util.jobs.ArrayUtils.contains(MEDIA_URIS_FOR_STANDBY_EXEMPTION, triggerContentUris[i9].getUri())) {
                        i9++;
                    } else {
                        z = false;
                        break;
                    }
                }
            }
            this.mHasExemptedMediaUrisOnly = z;
            this.mCanApplyTransportAffinities = jobInfo2.getRequiredNetwork() == null && jobInfo2.getRequiredNetwork().getTransportTypes().length == 0;
            boolean z2 = ((~constraintFlags) & 7) == 0 || this.mCanApplyTransportAffinities;
            if (!isRequestedExpeditedJob() && !jobInfo2.isUserInitiated() && i4 + i5 != 1 && z2) {
                constraintFlags |= 2097152;
            }
            this.requiredConstraints = constraintFlags;
            this.mRequiredConstraintsOfInterest = CONSTRAINTS_OF_INTEREST & constraintFlags;
            addDynamicConstraints(i7);
            this.mReadyNotDozing = canRunInDoze();
            if (i3 != 5) {
                addDynamicConstraints(DYNAMIC_RESTRICTED_CONSTRAINTS);
            } else {
                this.mReadyDynamicSatisfied = false;
            }
            this.mCumulativeExecutionTimeMs = j5;
            this.mLastSuccessfulRunTime = j3;
            this.mLastFailedRunTime = j4;
            this.mInternalFlags = i6;
            updateNetworkBytesLocked();
            updateMediaBackupExemptionStatus();
        }
        i8 = -1;
        if (i8 != -1) {
        }
        if (jobInfo.getRequiredNetwork() != null) {
        }
        this.job = jobInfo2;
        if (str2 != null) {
        }
        if (this.sourceTag == null) {
        }
        this.batteryName = str5;
        this.mIsProxyJob = !this.sourcePackageName.equals(jobInfo2.getService().getPackageName());
        this.earliestRunTimeElapsedMillis = j;
        this.latestRunTimeElapsedMillis = j2;
        this.mOriginalLatestRunTimeElapsedMillis = j2;
        this.numFailures = i4;
        this.mNumSystemStops = i5;
        constraintFlags = jobInfo2.getConstraintFlags();
        if (jobInfo2.getRequiredNetwork() != null) {
        }
        if (j != 0) {
        }
        if (j2 != NO_LATEST_RUNTIME) {
        }
        if (jobInfo2.isPrefetch()) {
        }
        if (jobInfo2.getTriggerContentUris() != null) {
        }
        this.mHasExemptedMediaUrisOnly = z;
        this.mCanApplyTransportAffinities = jobInfo2.getRequiredNetwork() == null && jobInfo2.getRequiredNetwork().getTransportTypes().length == 0;
        if (((~constraintFlags) & 7) == 0) {
        }
        if (!isRequestedExpeditedJob()) {
            constraintFlags |= 2097152;
        }
        this.requiredConstraints = constraintFlags;
        this.mRequiredConstraintsOfInterest = CONSTRAINTS_OF_INTEREST & constraintFlags;
        addDynamicConstraints(i7);
        this.mReadyNotDozing = canRunInDoze();
        if (i3 != 5) {
        }
        this.mCumulativeExecutionTimeMs = j5;
        this.mLastSuccessfulRunTime = j3;
        this.mLastFailedRunTime = j4;
        this.mInternalFlags = i6;
        updateNetworkBytesLocked();
        updateMediaBackupExemptionStatus();
    }

    public JobStatus(com.android.server.job.controllers.JobStatus jobStatus) {
        this(jobStatus.getJob(), jobStatus.getUid(), jobStatus.getSourcePackageName(), jobStatus.getSourceUserId(), jobStatus.getStandbyBucket(), jobStatus.getNamespace(), jobStatus.getSourceTag(), jobStatus.getNumFailures(), jobStatus.getNumSystemStops(), jobStatus.getEarliestRunTime(), jobStatus.getLatestRunTimeElapsed(), jobStatus.getLastSuccessfulRunTime(), jobStatus.getLastFailedRunTime(), jobStatus.getCumulativeExecutionTimeMs(), jobStatus.getInternalFlags(), jobStatus.mDynamicConstraints);
        this.mPersistedUtcTimes = jobStatus.mPersistedUtcTimes;
        if (jobStatus.mPersistedUtcTimes != null && DEBUG) {
            android.util.Slog.i(TAG, "Cloning job with persisted run times", new java.lang.RuntimeException("here"));
        }
        if (jobStatus.executingWork != null && jobStatus.executingWork.size() > 0) {
            this.executingWork = new java.util.ArrayList<>(jobStatus.executingWork);
        }
        if (jobStatus.pendingWork != null && jobStatus.pendingWork.size() > 0) {
            this.pendingWork = new java.util.ArrayList<>(jobStatus.pendingWork);
        }
    }

    public JobStatus(android.app.job.JobInfo jobInfo, int i, java.lang.String str, int i2, int i3, @android.annotation.Nullable java.lang.String str2, java.lang.String str3, long j, long j2, long j3, long j4, long j5, android.util.Pair<java.lang.Long, java.lang.Long> pair, int i4, int i5) {
        this(jobInfo, i, str, i2, i3, str2, str3, 0, 0, j, j2, j3, j4, j5, i4, i5);
        this.mPersistedUtcTimes = pair;
        if (pair != null && DEBUG) {
            android.util.Slog.i(TAG, "+ restored job with RTC times because of bad boot clock");
        }
    }

    public JobStatus(com.android.server.job.controllers.JobStatus jobStatus, long j, long j2, int i, int i2, long j3, long j4, long j5) {
        this(jobStatus.job, jobStatus.getUid(), jobStatus.getSourcePackageName(), jobStatus.getSourceUserId(), jobStatus.getStandbyBucket(), jobStatus.getNamespace(), jobStatus.getSourceTag(), i, i2, j, j2, j3, j4, j5, jobStatus.getInternalFlags(), jobStatus.mDynamicConstraints);
    }

    public static com.android.server.job.controllers.JobStatus createFromJobInfo(android.app.job.JobInfo jobInfo, int i, java.lang.String str, int i2, @android.annotation.Nullable java.lang.String str2, java.lang.String str3) {
        long minLatencyMillis;
        long maxExecutionDelayMillis;
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        if (jobInfo.isPeriodic()) {
            long max = java.lang.Math.max(android.app.job.JobInfo.getMinPeriodMillis(), java.lang.Math.min(31536000000L, jobInfo.getIntervalMillis()));
            long j = millis + max;
            minLatencyMillis = j - java.lang.Math.max(android.app.job.JobInfo.getMinFlexMillis(), java.lang.Math.min(max, jobInfo.getFlexMillis()));
            maxExecutionDelayMillis = j;
        } else {
            minLatencyMillis = jobInfo.hasEarlyConstraint() ? jobInfo.getMinLatencyMillis() + millis : 0L;
            maxExecutionDelayMillis = jobInfo.hasLateConstraint() ? jobInfo.getMaxExecutionDelayMillis() + millis : NO_LATEST_RUNTIME;
        }
        return new com.android.server.job.controllers.JobStatus(jobInfo, i, str, i2, com.android.server.job.JobSchedulerService.standbyBucketForPackage(str != null ? str : jobInfo.getService().getPackageName(), i2, millis), str2, str3, 0, 0, minLatencyMillis, maxExecutionDelayMillis, 0L, 0L, 0L, 0, 0);
    }

    private long generateLoggingId(@android.annotation.Nullable java.lang.String str, int i) {
        if (str == null) {
            return i;
        }
        return i | (str.hashCode() << 31);
    }

    @android.annotation.Nullable
    private static java.lang.String generateNamespaceHash(@android.annotation.Nullable java.lang.String str) {
        java.lang.String str2 = null;
        if (str == null) {
            return null;
        }
        if (str.trim().isEmpty()) {
            return str;
        }
        synchronized (sNamespaceHashCache) {
            try {
                int indexOfKey = sNamespaceHashCache.indexOfKey(str);
                if (indexOfKey >= 0) {
                    return sNamespaceHashCache.valueAt(indexOfKey);
                }
                try {
                    if (sMessageDigest == null) {
                        sMessageDigest = java.security.MessageDigest.getInstance("SHA-256");
                    }
                    byte[] digest = sMessageDigest.digest(str.getBytes());
                    java.lang.StringBuilder sb = new java.lang.StringBuilder(digest.length);
                    for (byte b : digest) {
                        sb.append(java.lang.String.format("%02X", java.lang.Byte.valueOf(b)));
                    }
                    str2 = sb.toString();
                } catch (java.lang.Exception e) {
                    android.util.Slog.wtf(TAG, "Couldn't hash input", e);
                }
                if (str2 == null) {
                    return "failed_namespace_hash";
                }
                java.lang.String intern = str2.intern();
                synchronized (sNamespaceHashCache) {
                    try {
                        if (sNamespaceHashCache.size() >= 128) {
                            sNamespaceHashCache.removeAt(new java.util.Random().nextInt(128));
                        }
                        sNamespaceHashCache.put(str, intern);
                    } finally {
                    }
                }
                return intern;
            } finally {
            }
        }
    }

    public void enqueueWorkLocked(android.app.job.JobWorkItem jobWorkItem) {
        if (this.pendingWork == null) {
            this.pendingWork = new java.util.ArrayList<>();
        }
        jobWorkItem.setWorkId(this.nextPendingWorkId);
        this.nextPendingWorkId++;
        if (jobWorkItem.getIntent() != null && com.android.server.job.GrantedUriPermissions.checkGrantFlags(jobWorkItem.getIntent().getFlags())) {
            jobWorkItem.setGrants(com.android.server.job.GrantedUriPermissions.createFromIntent(jobWorkItem.getIntent(), this.sourceUid, this.sourcePackageName, this.sourceUserId, toShortString()));
        }
        this.pendingWork.add(jobWorkItem);
        updateNetworkBytesLocked();
    }

    public android.app.job.JobWorkItem dequeueWorkLocked() {
        if (this.pendingWork != null && this.pendingWork.size() > 0) {
            android.app.job.JobWorkItem remove = this.pendingWork.remove(0);
            if (remove != null) {
                if (this.executingWork == null) {
                    this.executingWork = new java.util.ArrayList<>();
                }
                this.executingWork.add(remove);
                remove.bumpDeliveryCount();
            }
            return remove;
        }
        return null;
    }

    public int getWorkCount() {
        return (this.pendingWork == null ? 0 : this.pendingWork.size()) + (this.executingWork != null ? this.executingWork.size() : 0);
    }

    public boolean hasWorkLocked() {
        return (this.pendingWork != null && this.pendingWork.size() > 0) || hasExecutingWorkLocked();
    }

    public boolean hasExecutingWorkLocked() {
        return this.executingWork != null && this.executingWork.size() > 0;
    }

    private static void ungrantWorkItem(android.app.job.JobWorkItem jobWorkItem) {
        if (jobWorkItem.getGrants() != null) {
            ((com.android.server.job.GrantedUriPermissions) jobWorkItem.getGrants()).revoke();
        }
    }

    public boolean completeWorkLocked(int i) {
        if (this.executingWork != null) {
            int size = this.executingWork.size();
            for (int i2 = 0; i2 < size; i2++) {
                android.app.job.JobWorkItem jobWorkItem = this.executingWork.get(i2);
                if (jobWorkItem.getWorkId() == i) {
                    this.executingWork.remove(i2);
                    ungrantWorkItem(jobWorkItem);
                    updateNetworkBytesLocked();
                    return true;
                }
            }
        }
        return false;
    }

    private static void ungrantWorkList(java.util.ArrayList<android.app.job.JobWorkItem> arrayList) {
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ungrantWorkItem(arrayList.get(i));
            }
        }
    }

    public void stopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus != null) {
            if (this.executingWork != null && this.executingWork.size() > 0) {
                jobStatus.pendingWork = this.executingWork;
            }
            if (jobStatus.pendingWork == null) {
                jobStatus.pendingWork = this.pendingWork;
            } else if (this.pendingWork != null && this.pendingWork.size() > 0) {
                jobStatus.pendingWork.addAll(this.pendingWork);
            }
            this.pendingWork = null;
            this.executingWork = null;
            jobStatus.nextPendingWorkId = this.nextPendingWorkId;
            jobStatus.updateNetworkBytesLocked();
        } else {
            ungrantWorkList(this.pendingWork);
            this.pendingWork = null;
            ungrantWorkList(this.executingWork);
            this.executingWork = null;
        }
        updateNetworkBytesLocked();
    }

    public void prepareLocked() {
        if (this.prepared) {
            android.util.Slog.wtf(TAG, "Already prepared: " + this);
            return;
        }
        this.prepared = true;
        this.unpreparedPoint = null;
        android.content.ClipData clipData = this.job.getClipData();
        if (clipData != null) {
            this.uriPerms = com.android.server.job.GrantedUriPermissions.createFromClip(clipData, this.sourceUid, this.sourcePackageName, this.sourceUserId, this.job.getClipGrantFlags(), toShortString());
        }
    }

    public void unprepareLocked() {
        if (!this.prepared) {
            android.util.Slog.wtf(TAG, "Hasn't been prepared: " + this);
            if (this.unpreparedPoint != null) {
                android.util.Slog.e(TAG, "Was already unprepared at ", this.unpreparedPoint);
                return;
            }
            return;
        }
        this.prepared = false;
        this.unpreparedPoint = new java.lang.Throwable().fillInStackTrace();
        if (this.uriPerms != null) {
            this.uriPerms.revoke();
            this.uriPerms = null;
        }
    }

    public boolean isPreparedLocked() {
        return this.prepared;
    }

    public android.app.job.JobInfo getJob() {
        return this.job;
    }

    public int getJobId() {
        return this.job.getId();
    }

    public long getLoggingJobId() {
        return this.mLoggingJobId;
    }

    @android.annotation.Nullable
    public java.lang.String getAppTraceTag() {
        return this.job.getTraceTag();
    }

    public boolean isProxyJob() {
        return this.mIsProxyJob;
    }

    public void printUniqueId(java.io.PrintWriter printWriter) {
        if (this.mNamespace != null) {
            printWriter.print(this.mNamespace);
            printWriter.print(":");
        } else {
            printWriter.print("#");
        }
        android.os.UserHandle.formatUid(printWriter, this.callingUid);
        printWriter.print(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
        printWriter.print(this.job.getId());
    }

    public int getNumFailures() {
        return this.numFailures;
    }

    public int getNumSystemStops() {
        return this.mNumSystemStops;
    }

    public int getNumPreviousAttempts() {
        return this.numFailures + this.mNumSystemStops;
    }

    public android.content.ComponentName getServiceComponent() {
        return this.job.getService();
    }

    public java.lang.String getCallingPackageName() {
        return this.job.getService().getPackageName();
    }

    public java.lang.String getSourcePackageName() {
        return this.sourcePackageName;
    }

    public int getSourceUid() {
        return this.sourceUid;
    }

    public int getSourceUserId() {
        return this.sourceUserId;
    }

    public int getUserId() {
        return android.os.UserHandle.getUserId(this.callingUid);
    }

    private boolean shouldBlameSourceForTimeout() {
        return android.os.UserHandle.isCore(this.callingUid);
    }

    public java.lang.String getTimeoutBlamePackageName() {
        if (shouldBlameSourceForTimeout()) {
            return this.sourcePackageName;
        }
        return getServiceComponent().getPackageName();
    }

    public int getTimeoutBlameUid() {
        if (shouldBlameSourceForTimeout()) {
            return this.sourceUid;
        }
        return this.callingUid;
    }

    public int getTimeoutBlameUserId() {
        if (shouldBlameSourceForTimeout()) {
            return this.sourceUserId;
        }
        return android.os.UserHandle.getUserId(this.callingUid);
    }

    public int getEffectiveStandbyBucket() {
        java.lang.String str;
        if (this.mJobSchedulerInternal == null) {
            this.mJobSchedulerInternal = (com.android.server.job.JobSchedulerInternal) com.android.server.LocalServices.getService(com.android.server.job.JobSchedulerInternal.class);
        }
        boolean isAppConsideredBuggy = this.mJobSchedulerInternal.isAppConsideredBuggy(getUserId(), getServiceComponent().getPackageName(), getTimeoutBlameUserId(), getTimeoutBlamePackageName());
        int standbyBucket = getStandbyBucket();
        if (standbyBucket == 6) {
            if (isAppConsideredBuggy) {
                if (getServiceComponent().getPackageName().equals(this.sourcePackageName)) {
                    str = this.sourcePackageName;
                } else {
                    str = getServiceComponent().getPackageName() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + this.sourcePackageName;
                }
                android.util.Slog.w(TAG, "Exempted app " + str + " considered buggy");
            }
            return standbyBucket;
        }
        if (this.uidActive || getJob().isExemptedFromAppStandby()) {
            return 0;
        }
        boolean z = this.mHasMediaBackupExemption || (this.job.getTriggerContentUris() != null && this.job.getRequiredNetwork() != null && !this.job.hasLateConstraint() && this.mJobSchedulerInternal.hasRunBackupJobsPermission(this.sourcePackageName, this.sourceUid));
        if (standbyBucket != 5 && standbyBucket != 4 && z) {
            standbyBucket = java.lang.Math.min(1, standbyBucket);
        }
        if (isAppConsideredBuggy && standbyBucket < 1) {
            if (!this.mIsDowngradedDueToBuggyApp) {
                com.android.modules.expresslog.Counter.logIncrementWithUid("job_scheduler.value_job_quota_reduced_due_to_buggy_uid", getTimeoutBlameUid());
                this.mIsDowngradedDueToBuggyApp = true;
            }
            return 1;
        }
        return standbyBucket;
    }

    public int getStandbyBucket() {
        return this.standbyBucket;
    }

    public void setStandbyBucket(int i) {
        if (i == 5) {
            addDynamicConstraints(DYNAMIC_RESTRICTED_CONSTRAINTS);
        } else if (this.standbyBucket == 5) {
            removeDynamicConstraints(DYNAMIC_RESTRICTED_CONSTRAINTS);
        }
        this.standbyBucket = i;
        this.mLoggedBucketMismatch = false;
    }

    public void maybeLogBucketMismatch() {
        if (!this.mLoggedBucketMismatch) {
            android.util.Slog.wtf(TAG, "App " + getSourcePackageName() + " became active but still in NEVER bucket");
            this.mLoggedBucketMismatch = true;
        }
    }

    public long getWhenStandbyDeferred() {
        return this.whenStandbyDeferred;
    }

    public void setWhenStandbyDeferred(long j) {
        this.whenStandbyDeferred = j;
    }

    public long getFirstForceBatchedTimeElapsed() {
        return this.mFirstForceBatchedTimeElapsed;
    }

    public void setFirstForceBatchedTimeElapsed(long j) {
        this.mFirstForceBatchedTimeElapsed = j;
    }

    public boolean updateMediaBackupExemptionStatus() {
        if (this.mJobSchedulerInternal == null) {
            this.mJobSchedulerInternal = (com.android.server.job.JobSchedulerInternal) com.android.server.LocalServices.getService(com.android.server.job.JobSchedulerInternal.class);
        }
        boolean z = this.mHasExemptedMediaUrisOnly && !this.job.hasLateConstraint() && this.job.getRequiredNetwork() != null && getEffectivePriority() >= 300 && this.sourcePackageName.equals(this.mJobSchedulerInternal.getCloudMediaProviderPackage(this.sourceUserId));
        if (this.mHasMediaBackupExemption == z) {
            return false;
        }
        this.mHasMediaBackupExemption = z;
        return true;
    }

    @android.annotation.Nullable
    public java.lang.String getNamespace() {
        return this.mNamespace;
    }

    @android.annotation.Nullable
    public java.lang.String getNamespaceHash() {
        return this.mNamespaceHash;
    }

    @android.annotation.Nullable
    public java.lang.String getSourceTag() {
        return this.sourceTag;
    }

    public int getUid() {
        return this.callingUid;
    }

    public java.lang.String getBatteryName() {
        return this.batteryName;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String applyBasicPiiFilters(@android.annotation.NonNull java.lang.String str) {
        for (int size = BASIC_PII_FILTERS.size() - 1; size >= 0; size--) {
            str = BASIC_PII_FILTERS.keyAt(size).matcher(str).replaceAll(BASIC_PII_FILTERS.valueAt(size));
        }
        return str;
    }

    @android.annotation.NonNull
    public java.lang.String[] getFilteredDebugTags() {
        if (this.mFilteredDebugTags != null) {
            return this.mFilteredDebugTags;
        }
        android.util.ArraySet debugTagsArraySet = this.job.getDebugTagsArraySet();
        this.mFilteredDebugTags = new java.lang.String[debugTagsArraySet.size()];
        for (int i = 0; i < this.mFilteredDebugTags.length; i++) {
            this.mFilteredDebugTags[i] = applyBasicPiiFilters((java.lang.String) debugTagsArraySet.valueAt(i));
        }
        return this.mFilteredDebugTags;
    }

    @android.annotation.Nullable
    public java.lang.String getFilteredTraceTag() {
        if (this.mFilteredTraceTag != null) {
            return this.mFilteredTraceTag;
        }
        java.lang.String traceTag = this.job.getTraceTag();
        if (traceTag == null) {
            return null;
        }
        this.mFilteredTraceTag = applyBasicPiiFilters(traceTag);
        return this.mFilteredTraceTag;
    }

    @android.annotation.NonNull
    public java.lang.String getWakelockTag() {
        if (this.mWakelockTag == null) {
            this.mWakelockTag = "*job*/" + this.batteryName;
        }
        return this.mWakelockTag;
    }

    public int getBias() {
        return this.job.getBias();
    }

    public int getEffectivePriority() {
        int i;
        if ((getInternalFlags() & 2) != 0 || (this.job.isUserInitiated() && (getInternalFlags() & 4) != 0)) {
            i = 400;
        } else {
            i = 500;
        }
        int min = java.lang.Math.min(i, this.job.getPriority());
        if (this.numFailures < 2) {
            return min;
        }
        if (shouldTreatAsUserInitiatedJob()) {
            return min;
        }
        if (isRequestedExpeditedJob()) {
            return 400;
        }
        switch (this.numFailures / 2) {
        }
        return min;
    }

    public int getFlags() {
        return this.job.getFlags();
    }

    public int getInternalFlags() {
        return this.mInternalFlags;
    }

    public void addInternalFlags(int i) {
        this.mInternalFlags = i | this.mInternalFlags;
    }

    public void removeInternalFlags(int i) {
        this.mInternalFlags = (~i) & this.mInternalFlags;
    }

    public int getSatisfiedConstraintFlags() {
        return this.satisfiedConstraints;
    }

    public void maybeAddForegroundExemption(java.util.function.Predicate<java.lang.Integer> predicate) {
        if (!this.job.hasEarlyConstraint() && !this.job.hasLateConstraint() && (this.mInternalFlags & 1) == 0 && predicate.test(java.lang.Integer.valueOf(getSourceUid()))) {
            addInternalFlags(1);
        }
    }

    private void updateNetworkBytesLocked() {
        this.mTotalNetworkDownloadBytes = this.job.getEstimatedNetworkDownloadBytes();
        if (this.mTotalNetworkDownloadBytes < 0) {
            this.mTotalNetworkDownloadBytes = -1L;
        }
        this.mTotalNetworkUploadBytes = this.job.getEstimatedNetworkUploadBytes();
        if (this.mTotalNetworkUploadBytes < 0) {
            this.mTotalNetworkUploadBytes = -1L;
        }
        this.mMinimumNetworkChunkBytes = this.job.getMinimumNetworkChunkBytes();
        if (this.pendingWork != null) {
            for (int i = 0; i < this.pendingWork.size(); i++) {
                long estimatedNetworkDownloadBytes = this.pendingWork.get(i).getEstimatedNetworkDownloadBytes();
                if (estimatedNetworkDownloadBytes != -1 && estimatedNetworkDownloadBytes > 0) {
                    if (this.mTotalNetworkDownloadBytes != -1) {
                        this.mTotalNetworkDownloadBytes += estimatedNetworkDownloadBytes;
                    } else {
                        this.mTotalNetworkDownloadBytes = estimatedNetworkDownloadBytes;
                    }
                }
                long estimatedNetworkUploadBytes = this.pendingWork.get(i).getEstimatedNetworkUploadBytes();
                if (estimatedNetworkUploadBytes != -1 && estimatedNetworkUploadBytes > 0) {
                    if (this.mTotalNetworkUploadBytes != -1) {
                        this.mTotalNetworkUploadBytes += estimatedNetworkUploadBytes;
                    } else {
                        this.mTotalNetworkUploadBytes = estimatedNetworkUploadBytes;
                    }
                }
                long minimumNetworkChunkBytes = this.pendingWork.get(i).getMinimumNetworkChunkBytes();
                if (this.mMinimumNetworkChunkBytes == -1) {
                    this.mMinimumNetworkChunkBytes = minimumNetworkChunkBytes;
                } else if (minimumNetworkChunkBytes != -1) {
                    this.mMinimumNetworkChunkBytes = java.lang.Math.min(this.mMinimumNetworkChunkBytes, minimumNetworkChunkBytes);
                }
            }
        }
    }

    public long getEstimatedNetworkDownloadBytes() {
        return this.mTotalNetworkDownloadBytes;
    }

    public long getEstimatedNetworkUploadBytes() {
        return this.mTotalNetworkUploadBytes;
    }

    public long getMinimumNetworkChunkBytes() {
        return this.mMinimumNetworkChunkBytes;
    }

    public boolean hasConnectivityConstraint() {
        return (this.requiredConstraints & 268435456) != 0;
    }

    public boolean hasChargingConstraint() {
        return hasConstraint(1);
    }

    public boolean hasBatteryNotLowConstraint() {
        return hasConstraint(2);
    }

    boolean hasPowerConstraint() {
        return hasConstraint(3);
    }

    public boolean hasStorageNotLowConstraint() {
        return hasConstraint(8);
    }

    public boolean hasTimingDelayConstraint() {
        return hasConstraint(Integer.MIN_VALUE);
    }

    public boolean hasDeadlineConstraint() {
        return hasConstraint(1073741824);
    }

    public boolean hasIdleConstraint() {
        return hasConstraint(4);
    }

    public boolean hasContentTriggerConstraint() {
        return (this.requiredConstraints & 67108864) != 0;
    }

    public boolean hasFlexibilityConstraint() {
        return (this.requiredConstraints & 2097152) != 0;
    }

    public int getNumAppliedFlexibleConstraints() {
        return this.mNumAppliedFlexibleConstraints;
    }

    public int getNumRequiredFlexibleConstraints() {
        return this.mNumAppliedFlexibleConstraints - this.mNumDroppedFlexibleConstraints;
    }

    public int getNumDroppedFlexibleConstraints() {
        return this.mNumDroppedFlexibleConstraints;
    }

    private boolean hasConstraint(int i) {
        return ((this.requiredConstraints & i) == 0 && (i & this.mDynamicConstraints) == 0) ? false : true;
    }

    public long getTriggerContentUpdateDelay() {
        long triggerContentUpdateDelay = this.job.getTriggerContentUpdateDelay();
        if (triggerContentUpdateDelay < 0) {
            return DEFAULT_TRIGGER_UPDATE_DELAY;
        }
        return java.lang.Math.max(triggerContentUpdateDelay, 500L);
    }

    public long getTriggerContentMaxDelay() {
        long triggerContentMaxDelay = this.job.getTriggerContentMaxDelay();
        if (triggerContentMaxDelay < 0) {
            return 120000L;
        }
        return java.lang.Math.max(triggerContentMaxDelay, 1000L);
    }

    public boolean isPersisted() {
        return this.job.isPersisted();
    }

    public long getCumulativeExecutionTimeMs() {
        return this.mCumulativeExecutionTimeMs;
    }

    public void incrementCumulativeExecutionTime(long j) {
        this.mCumulativeExecutionTimeMs += j;
    }

    public long getEarliestRunTime() {
        return this.earliestRunTimeElapsedMillis;
    }

    public long getLatestRunTimeElapsed() {
        return this.latestRunTimeElapsedMillis;
    }

    public long getOriginalLatestRunTimeElapsed() {
        return this.mOriginalLatestRunTimeElapsedMillis;
    }

    public void setOriginalLatestRunTimeElapsed(long j) {
        this.mOriginalLatestRunTimeElapsedMillis = j;
    }

    boolean areTransportAffinitiesSatisfied() {
        return this.mTransportAffinitiesSatisfied;
    }

    void setTransportAffinitiesSatisfied(boolean z) {
        this.mTransportAffinitiesSatisfied = z;
    }

    public boolean canApplyTransportAffinities() {
        return this.mCanApplyTransportAffinities;
    }

    public int getStopReason() {
        return this.mReasonReadyToUnready;
    }

    public float getFractionRunTime() {
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        if (this.earliestRunTimeElapsedMillis == 0 && this.latestRunTimeElapsedMillis == NO_LATEST_RUNTIME) {
            return 1.0f;
        }
        if (this.earliestRunTimeElapsedMillis == 0) {
            if (millis >= this.latestRunTimeElapsedMillis) {
                return 1.0f;
            }
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        if (this.latestRunTimeElapsedMillis == NO_LATEST_RUNTIME) {
            if (millis >= this.earliestRunTimeElapsedMillis) {
                return 1.0f;
            }
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        if (millis <= this.earliestRunTimeElapsedMillis) {
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        if (millis >= this.latestRunTimeElapsedMillis) {
            return 1.0f;
        }
        return (millis - this.earliestRunTimeElapsedMillis) / (this.latestRunTimeElapsedMillis - this.earliestRunTimeElapsedMillis);
    }

    public android.util.Pair<java.lang.Long, java.lang.Long> getPersistedUtcTimes() {
        return this.mPersistedUtcTimes;
    }

    public void clearPersistedUtcTimes() {
        this.mPersistedUtcTimes = null;
    }

    public boolean isRequestedExpeditedJob() {
        return (getFlags() & 16) != 0;
    }

    public boolean shouldTreatAsExpeditedJob() {
        return this.mExpeditedQuotaApproved && this.mExpeditedTareApproved && isRequestedExpeditedJob();
    }

    public boolean shouldTreatAsUserInitiatedJob() {
        return getJob().isUserInitiated() && (getInternalFlags() & 2) == 0 && (getInternalFlags() & 4) == 0;
    }

    @android.annotation.NonNull
    public android.app.job.UserVisibleJobSummary getUserVisibleJobSummary() {
        if (this.mUserVisibleJobSummary == null) {
            this.mUserVisibleJobSummary = new android.app.job.UserVisibleJobSummary(this.callingUid, getServiceComponent().getPackageName(), getSourceUserId(), getSourcePackageName(), getNamespace(), getJobId());
        }
        return this.mUserVisibleJobSummary;
    }

    public boolean isUserVisibleJob() {
        return shouldTreatAsUserInitiatedJob() || this.startedAsUserInitiatedJob;
    }

    public boolean canRunInDoze() {
        if (this.appHasDozeExemption || (getFlags() & 1) != 0 || shouldTreatAsUserInitiatedJob()) {
            return true;
        }
        return (shouldTreatAsExpeditedJob() || this.startedAsExpeditedJob) && (this.mDynamicConstraints & 33554432) == 0;
    }

    boolean canRunInBatterySaver() {
        if ((getInternalFlags() & 1) != 0 || shouldTreatAsUserInitiatedJob()) {
            return true;
        }
        return (shouldTreatAsExpeditedJob() || this.startedAsExpeditedJob) && (this.mDynamicConstraints & 4194304) == 0;
    }

    public boolean isUserBgRestricted() {
        return this.mIsUserBgRestricted;
    }

    boolean setChargingConstraintSatisfied(long j, boolean z) {
        return setConstraintSatisfied(1, j, z);
    }

    boolean setBatteryNotLowConstraintSatisfied(long j, boolean z) {
        return setConstraintSatisfied(2, j, z);
    }

    boolean setStorageNotLowConstraintSatisfied(long j, boolean z) {
        return setConstraintSatisfied(8, j, z);
    }

    boolean setPrefetchConstraintSatisfied(long j, boolean z) {
        return setConstraintSatisfied(8388608, j, z);
    }

    boolean setTimingDelayConstraintSatisfied(long j, boolean z) {
        return setConstraintSatisfied(Integer.MIN_VALUE, j, z);
    }

    boolean setDeadlineConstraintSatisfied(long j, boolean z) {
        boolean z2 = false;
        if (!setConstraintSatisfied(1073741824, j, z)) {
            return false;
        }
        if (!this.job.isPeriodic() && hasDeadlineConstraint() && z) {
            z2 = true;
        }
        this.mReadyDeadlineSatisfied = z2;
        return true;
    }

    boolean setIdleConstraintSatisfied(long j, boolean z) {
        return setConstraintSatisfied(4, j, z);
    }

    boolean setConnectivityConstraintSatisfied(long j, boolean z) {
        return setConstraintSatisfied(268435456, j, z);
    }

    boolean setContentTriggerConstraintSatisfied(long j, boolean z) {
        return setConstraintSatisfied(67108864, j, z);
    }

    boolean setDeviceNotDozingConstraintSatisfied(long j, boolean z, boolean z2) {
        this.appHasDozeExemption = z2;
        if (!setConstraintSatisfied(33554432, j, z)) {
            return false;
        }
        this.mReadyNotDozing = z || canRunInDoze();
        return true;
    }

    boolean setBackgroundNotRestrictedConstraintSatisfied(long j, boolean z, boolean z2) {
        this.mIsUserBgRestricted = z2;
        if (setConstraintSatisfied(4194304, j, z)) {
            this.mReadyNotRestrictedInBg = z;
            return true;
        }
        return false;
    }

    boolean setQuotaConstraintSatisfied(long j, boolean z) {
        if (setConstraintSatisfied(16777216, j, z)) {
            this.mReadyWithinQuota = z;
            return true;
        }
        return false;
    }

    boolean setTareWealthConstraintSatisfied(long j, boolean z) {
        if (setConstraintSatisfied(134217728, j, z)) {
            this.mReadyTareWealth = z;
            return true;
        }
        return false;
    }

    boolean setFlexibilityConstraintSatisfied(long j, boolean z) {
        return setConstraintSatisfied(2097152, j, z);
    }

    boolean setExpeditedJobQuotaApproved(long j, boolean z) {
        if (this.mExpeditedQuotaApproved == z) {
            return false;
        }
        boolean z2 = !z && isReady();
        this.mExpeditedQuotaApproved = z;
        updateExpeditedDependencies();
        boolean isReady = isReady();
        if (z2 && !isReady) {
            this.mReasonReadyToUnready = 10;
        } else if (!z2 && isReady) {
            this.mReasonReadyToUnready = 0;
        }
        return true;
    }

    boolean setExpeditedJobTareApproved(long j, boolean z) {
        if (this.mExpeditedTareApproved == z) {
            return false;
        }
        boolean z2 = !z && isReady();
        this.mExpeditedTareApproved = z;
        updateExpeditedDependencies();
        boolean isReady = isReady();
        if (z2 && !isReady) {
            this.mReasonReadyToUnready = 10;
        } else if (!z2 && isReady) {
            this.mReasonReadyToUnready = 0;
        }
        return true;
    }

    private void updateExpeditedDependencies() {
        this.mReadyNotDozing = isConstraintSatisfied(33554432) || canRunInDoze();
    }

    boolean setUidActive(boolean z) {
        if (z != this.uidActive) {
            this.uidActive = z;
            return true;
        }
        return false;
    }

    boolean setConstraintSatisfied(int i, long j, boolean z) {
        if (((this.satisfiedConstraints & i) != 0) == z) {
            return false;
        }
        if (DEBUG) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Constraint ");
            sb.append(i);
            sb.append(" is ");
            sb.append(!z ? "NOT " : "");
            sb.append("satisfied for ");
            sb.append(toShortString());
            android.util.Slog.v(TAG, sb.toString());
        }
        boolean z2 = !z && isReady();
        this.satisfiedConstraints = (this.satisfiedConstraints & (~i)) | (z ? i : 0);
        this.mSatisfiedConstraintsOfInterest = this.satisfiedConstraints & CONSTRAINTS_OF_INTEREST;
        this.mReadyDynamicSatisfied = this.mDynamicConstraints != 0 && this.mDynamicConstraints == (this.satisfiedConstraints & this.mDynamicConstraints);
        this.mConstraintUpdatedTimesElapsed[this.mConstraintChangeHistoryIndex] = j;
        this.mConstraintStatusHistory[this.mConstraintChangeHistoryIndex] = this.satisfiedConstraints;
        this.mConstraintChangeHistoryIndex = (this.mConstraintChangeHistoryIndex + 1) % 10;
        boolean readinessStatusWithConstraint = readinessStatusWithConstraint(i, z);
        if (z2 && !readinessStatusWithConstraint) {
            this.mReasonReadyToUnready = constraintToStopReason(i);
        } else if (!z2 && readinessStatusWithConstraint) {
            this.mReasonReadyToUnready = 0;
        }
        return true;
    }

    private int constraintToStopReason(int i) {
        switch (i) {
            case 1:
                return (i & this.requiredConstraints) != 0 ? 6 : 12;
            case 2:
                return (i & this.requiredConstraints) != 0 ? 5 : 12;
            case 4:
                return (i & this.requiredConstraints) != 0 ? 8 : 12;
            case 8:
                return 9;
            case 2097152:
                return 0;
            case 4194304:
                if (!this.mIsUserBgRestricted) {
                    return 4;
                }
                return 11;
            case 8388608:
                return 15;
            case 16777216:
            case 134217728:
                return 10;
            case 33554432:
                return 4;
            case 268435456:
                return 7;
            default:
                android.util.Slog.wtf(TAG, "Unsupported constraint (" + i + ") --stop reason mapping");
                return 0;
        }
    }

    public int getPendingJobReason() {
        int i = (~this.satisfiedConstraints) & (this.requiredConstraints | this.mDynamicConstraints | IMPLICIT_CONSTRAINTS);
        if ((4194304 & i) != 0) {
            return this.mIsUserBgRestricted ? 3 : 12;
        }
        if ((i & 2) != 0) {
            return (this.requiredConstraints & 2) != 0 ? 4 : 2;
        }
        if ((i & 1) != 0) {
            return (this.requiredConstraints & 1) != 0 ? 5 : 2;
        }
        if ((268435456 & i) != 0) {
            return 6;
        }
        if ((67108864 & i) != 0) {
            return 7;
        }
        if ((33554432 & i) != 0) {
            return 12;
        }
        if ((2097152 & i) != 0) {
            return 13;
        }
        if ((i & 4) != 0) {
            return (this.requiredConstraints & 4) != 0 ? 8 : 2;
        }
        if ((8388608 & i) != 0) {
            return 10;
        }
        if ((i & 8) != 0) {
            return 11;
        }
        if ((134217728 & i) != 0) {
            return 14;
        }
        if ((Integer.MIN_VALUE & i) != 0) {
            return 9;
        }
        if ((i & 16777216) != 0) {
            return 14;
        }
        if (getEffectiveStandbyBucket() == 4) {
            android.util.Slog.wtf(TAG, "App in NEVER bucket querying pending job reason");
            return 15;
        }
        if (this.serviceProcessName != null) {
            return 1;
        }
        if (!isReady()) {
            android.util.Slog.wtf(TAG, "Unknown reason job isn't ready");
            return 0;
        }
        return 0;
    }

    public boolean isConstraintSatisfied(int i) {
        return (i & this.satisfiedConstraints) != 0;
    }

    boolean isExpeditedQuotaApproved() {
        return this.mExpeditedQuotaApproved;
    }

    boolean clearTrackingController(int i) {
        if ((this.trackingControllers & i) != 0) {
            this.trackingControllers = (~i) & this.trackingControllers;
            return true;
        }
        return false;
    }

    void setTrackingController(int i) {
        this.trackingControllers = i | this.trackingControllers;
    }

    public void setNumAppliedFlexibleConstraints(int i) {
        this.mNumAppliedFlexibleConstraints = i;
    }

    public void setNumDroppedFlexibleConstraints(int i) {
        this.mNumDroppedFlexibleConstraints = java.lang.Math.max(0, java.lang.Math.min(this.mNumAppliedFlexibleConstraints, i));
    }

    public void disallowRunInBatterySaverAndDoze() {
        addDynamicConstraints(DYNAMIC_EXPEDITED_DEFERRAL_CONSTRAINTS);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void addDynamicConstraints(int i) {
        if ((16777216 & i) != 0) {
            android.util.Slog.wtf(TAG, "Tried to set quota as a dynamic constraint");
            i &= -16777217;
        }
        if ((134217728 & i) != 0) {
            android.util.Slog.wtf(TAG, "Tried to set TARE as a dynamic constraint");
            i &= -134217729;
        }
        if (!hasConnectivityConstraint()) {
            i &= -268435457;
        }
        if (!hasContentTriggerConstraint()) {
            i &= -67108865;
        }
        this.mDynamicConstraints = i | this.mDynamicConstraints;
        this.mReadyDynamicSatisfied = this.mDynamicConstraints != 0 && this.mDynamicConstraints == (this.satisfiedConstraints & this.mDynamicConstraints);
    }

    private void removeDynamicConstraints(int i) {
        this.mDynamicConstraints = (~i) & this.mDynamicConstraints;
        this.mReadyDynamicSatisfied = this.mDynamicConstraints != 0 && this.mDynamicConstraints == (this.satisfiedConstraints & this.mDynamicConstraints);
    }

    public long getLastSuccessfulRunTime() {
        return this.mLastSuccessfulRunTime;
    }

    public long getLastFailedRunTime() {
        return this.mLastFailedRunTime;
    }

    public boolean isReady() {
        return isReady(this.mSatisfiedConstraintsOfInterest);
    }

    public boolean wouldBeReadyWithConstraint(int i) {
        return readinessStatusWithConstraint(i, true);
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean readinessStatusWithConstraint(int i, boolean z) {
        boolean z2;
        int i2 = this.mSatisfiedConstraintsOfInterest;
        switch (i) {
            case 4194304:
                z2 = this.mReadyNotRestrictedInBg;
                this.mReadyNotRestrictedInBg = z;
                break;
            case 16777216:
                z2 = this.mReadyWithinQuota;
                this.mReadyWithinQuota = z;
                break;
            case 33554432:
                z2 = this.mReadyNotDozing;
                this.mReadyNotDozing = z;
                break;
            case 134217728:
                z2 = this.mReadyTareWealth;
                this.mReadyTareWealth = z;
                break;
            case 1073741824:
                z2 = this.mReadyDeadlineSatisfied;
                this.mReadyDeadlineSatisfied = z;
                break;
            default:
                if (z) {
                    i2 |= i;
                } else {
                    i2 = (~i) & i2;
                }
                this.mReadyDynamicSatisfied = this.mDynamicConstraints != 0 && this.mDynamicConstraints == (this.mDynamicConstraints & i2);
                z2 = false;
                break;
        }
        if (i != 2097152) {
            i2 |= 2097152;
        }
        boolean isReady = isReady(i2);
        switch (i) {
            case 4194304:
                this.mReadyNotRestrictedInBg = z2;
                return isReady;
            case 16777216:
                this.mReadyWithinQuota = z2;
                return isReady;
            case 33554432:
                this.mReadyNotDozing = z2;
                return isReady;
            case 134217728:
                this.mReadyTareWealth = z2;
                return isReady;
            case 1073741824:
                this.mReadyDeadlineSatisfied = z2;
                return isReady;
            default:
                this.mReadyDynamicSatisfied = this.mDynamicConstraints != 0 && this.mDynamicConstraints == (this.satisfiedConstraints & this.mDynamicConstraints);
                return isReady;
        }
    }

    private boolean isReady(int i) {
        if (((this.mReadyWithinQuota && this.mReadyTareWealth) || this.mReadyDynamicSatisfied || shouldTreatAsExpeditedJob()) && getEffectiveStandbyBucket() != 4 && this.mReadyNotDozing && this.mReadyNotRestrictedInBg && this.serviceProcessName != null) {
            return this.mReadyDeadlineSatisfied || isConstraintsSatisfied(i);
        }
        return false;
    }

    public boolean areDynamicConstraintsSatisfied() {
        return this.mReadyDynamicSatisfied;
    }

    public boolean isConstraintsSatisfied() {
        return isConstraintsSatisfied(this.mSatisfiedConstraintsOfInterest);
    }

    private boolean isConstraintsSatisfied(int i) {
        if (this.overrideState == 3) {
            return true;
        }
        if (this.overrideState == 2) {
            i |= this.requiredConstraints & SOFT_OVERRIDE_CONSTRAINTS;
        }
        return (i & this.mRequiredConstraintsOfInterest) == this.mRequiredConstraintsOfInterest;
    }

    public boolean matches(int i, @android.annotation.Nullable java.lang.String str, int i2) {
        return this.job.getId() == i2 && this.callingUid == i && java.util.Objects.equals(this.mNamespace, str);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("JobStatus{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        if (this.mNamespace != null) {
            sb.append(" ");
            sb.append(this.mNamespace);
            sb.append(":");
        } else {
            sb.append(" #");
        }
        android.os.UserHandle.formatUid(sb, this.callingUid);
        sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
        sb.append(this.job.getId());
        sb.append(' ');
        sb.append(this.batteryName);
        sb.append(" u=");
        sb.append(getUserId());
        sb.append(" s=");
        sb.append(getSourceUid());
        if (this.earliestRunTimeElapsedMillis != 0 || this.latestRunTimeElapsedMillis != NO_LATEST_RUNTIME) {
            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            sb.append(" TIME=");
            formatRunTime(sb, this.earliestRunTimeElapsedMillis, 0L, millis);
            sb.append(":");
            formatRunTime(sb, this.latestRunTimeElapsedMillis, NO_LATEST_RUNTIME, millis);
        }
        if (this.job.getRequiredNetwork() != null) {
            sb.append(" NET");
        }
        if (this.job.isRequireCharging()) {
            sb.append(" CHARGING");
        }
        if (this.job.isRequireBatteryNotLow()) {
            sb.append(" BATNOTLOW");
        }
        if (this.job.isRequireStorageNotLow()) {
            sb.append(" STORENOTLOW");
        }
        if (this.job.isRequireDeviceIdle()) {
            sb.append(" IDLE");
        }
        if (this.job.isPeriodic()) {
            sb.append(" PERIODIC");
        }
        if (this.job.isPersisted()) {
            sb.append(" PERSISTED");
        }
        if ((this.satisfiedConstraints & 33554432) == 0) {
            sb.append(" WAIT:DEV_NOT_DOZING");
        }
        if (this.job.getTriggerContentUris() != null) {
            sb.append(" URIS=");
            sb.append(java.util.Arrays.toString(this.job.getTriggerContentUris()));
        }
        if (this.numFailures != 0) {
            sb.append(" failures=");
            sb.append(this.numFailures);
        }
        if (this.mNumSystemStops != 0) {
            sb.append(" system stops=");
            sb.append(this.mNumSystemStops);
        }
        if (isReady()) {
            sb.append(" READY");
        } else {
            sb.append(" satisfied:0x");
            sb.append(java.lang.Integer.toHexString(this.satisfiedConstraints));
            int i = this.mRequiredConstraintsOfInterest | IMPLICIT_CONSTRAINTS;
            sb.append(" unsatisfied:0x");
            sb.append(java.lang.Integer.toHexString(i ^ (this.satisfiedConstraints & i)));
        }
        sb.append("}");
        return sb.toString();
    }

    private void formatRunTime(java.io.PrintWriter printWriter, long j, long j2, long j3) {
        if (j == j2) {
            printWriter.print("none");
        } else {
            android.util.TimeUtils.formatDuration(j - j3, printWriter);
        }
    }

    private void formatRunTime(java.lang.StringBuilder sb, long j, long j2, long j3) {
        if (j == j2) {
            sb.append("none");
        } else {
            android.util.TimeUtils.formatDuration(j - j3, sb);
        }
    }

    public java.lang.String toShortString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        if (this.mNamespace != null) {
            sb.append(" {");
            sb.append(this.mNamespace);
            sb.append("}");
        }
        sb.append(" #");
        android.os.UserHandle.formatUid(sb, this.callingUid);
        sb.append(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
        sb.append(this.job.getId());
        sb.append(' ');
        sb.append(this.batteryName);
        return sb.toString();
    }

    public java.lang.String toShortStringExceptUniqueId() {
        return java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + ' ' + this.batteryName;
    }

    public void writeToShortProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.callingUid);
        protoOutputStream.write(1120986464258L, this.job.getId());
        protoOutputStream.write(1138166333443L, this.batteryName);
        protoOutputStream.end(start);
    }

    static void dumpConstraints(java.io.PrintWriter printWriter, int i) {
        if ((i & 1) != 0) {
            printWriter.print(" CHARGING");
        }
        if ((i & 2) != 0) {
            printWriter.print(" BATTERY_NOT_LOW");
        }
        if ((i & 8) != 0) {
            printWriter.print(" STORAGE_NOT_LOW");
        }
        if ((Integer.MIN_VALUE & i) != 0) {
            printWriter.print(" TIMING_DELAY");
        }
        if ((1073741824 & i) != 0) {
            printWriter.print(" DEADLINE");
        }
        if ((i & 4) != 0) {
            printWriter.print(" IDLE");
        }
        if ((268435456 & i) != 0) {
            printWriter.print(" CONNECTIVITY");
        }
        if ((2097152 & i) != 0) {
            printWriter.print(" FLEXIBILITY");
        }
        if ((67108864 & i) != 0) {
            printWriter.print(" CONTENT_TRIGGER");
        }
        if ((33554432 & i) != 0) {
            printWriter.print(" DEVICE_NOT_DOZING");
        }
        if ((4194304 & i) != 0) {
            printWriter.print(" BACKGROUND_NOT_RESTRICTED");
        }
        if ((8388608 & i) != 0) {
            printWriter.print(" PREFETCH");
        }
        if ((134217728 & i) != 0) {
            printWriter.print(" TARE_WEALTH");
        }
        if ((16777216 & i) != 0) {
            printWriter.print(" WITHIN_QUOTA");
        }
        if (i != 0) {
            printWriter.print(" [0x");
            printWriter.print(java.lang.Integer.toHexString(i));
            printWriter.print("]");
        }
    }

    static int getProtoConstraint(int i) {
        switch (i) {
            case Integer.MIN_VALUE:
                return 4;
            case 1:
                return 1;
            case 2:
                return 2;
            case 4:
                return 6;
            case 8:
                return 3;
            case 2097152:
                return 15;
            case 4194304:
                return 11;
            case 8388608:
                return 14;
            case 16777216:
                return 10;
            case 33554432:
                return 9;
            case 67108864:
                return 8;
            case 134217728:
                return 13;
            case 268435456:
                return 7;
            case 1073741824:
                return 5;
            default:
                return 0;
        }
    }

    void dumpConstraints(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        if ((i & 1) != 0) {
            protoOutputStream.write(j, 1);
        }
        if ((i & 2) != 0) {
            protoOutputStream.write(j, 2);
        }
        if ((i & 8) != 0) {
            protoOutputStream.write(j, 3);
        }
        if ((Integer.MIN_VALUE & i) != 0) {
            protoOutputStream.write(j, 4);
        }
        if ((1073741824 & i) != 0) {
            protoOutputStream.write(j, 5);
        }
        if ((i & 4) != 0) {
            protoOutputStream.write(j, 6);
        }
        if ((268435456 & i) != 0) {
            protoOutputStream.write(j, 7);
        }
        if ((67108864 & i) != 0) {
            protoOutputStream.write(j, 8);
        }
        if ((33554432 & i) != 0) {
            protoOutputStream.write(j, 9);
        }
        if ((16777216 & i) != 0) {
            protoOutputStream.write(j, 10);
        }
        if ((i & 4194304) != 0) {
            protoOutputStream.write(j, 11);
        }
    }

    private void dumpJobWorkItem(android.util.IndentingPrintWriter indentingPrintWriter, android.app.job.JobWorkItem jobWorkItem, int i) {
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("#");
        indentingPrintWriter.print(i);
        indentingPrintWriter.print(": #");
        indentingPrintWriter.print(jobWorkItem.getWorkId());
        indentingPrintWriter.print(" ");
        indentingPrintWriter.print(jobWorkItem.getDeliveryCount());
        indentingPrintWriter.print("x ");
        indentingPrintWriter.println(jobWorkItem.getIntent());
        if (jobWorkItem.getGrants() != null) {
            indentingPrintWriter.println("URI grants:");
            indentingPrintWriter.increaseIndent();
            ((com.android.server.job.GrantedUriPermissions) jobWorkItem.getGrants()).dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
    }

    private void dumpJobWorkItem(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.app.job.JobWorkItem jobWorkItem) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, jobWorkItem.getWorkId());
        protoOutputStream.write(1120986464258L, jobWorkItem.getDeliveryCount());
        if (jobWorkItem.getIntent() != null) {
            jobWorkItem.getIntent().dumpDebug(protoOutputStream, 1146756268035L);
        }
        java.lang.Object grants = jobWorkItem.getGrants();
        if (grants != null) {
            ((com.android.server.job.GrantedUriPermissions) grants).dump(protoOutputStream, 1146756268036L);
        }
        protoOutputStream.end(start);
    }

    java.lang.String getBucketName() {
        return bucketName(this.standbyBucket);
    }

    static java.lang.String bucketName(int i) {
        switch (i) {
            case 0:
                return "ACTIVE";
            case 1:
                return "WORKING_SET";
            case 2:
                return "FREQUENT";
            case 3:
                return "RARE";
            case 4:
                return "NEVER";
            case 5:
                return "RESTRICTED";
            case 6:
                return "EXEMPTED";
            default:
                return "Unknown: " + i;
        }
    }

    @dalvik.annotation.optimization.NeverCompile
    public void dump(android.util.IndentingPrintWriter indentingPrintWriter, boolean z, long j) {
        android.os.UserHandle.formatUid(indentingPrintWriter, this.callingUid);
        indentingPrintWriter.print(" tag=");
        indentingPrintWriter.println(getWakelockTag());
        indentingPrintWriter.print("Source: uid=");
        android.os.UserHandle.formatUid(indentingPrintWriter, getSourceUid());
        indentingPrintWriter.print(" user=");
        indentingPrintWriter.print(getSourceUserId());
        indentingPrintWriter.print(" pkg=");
        indentingPrintWriter.println(getSourcePackageName());
        if (z) {
            indentingPrintWriter.println("JobInfo:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print("Service: ");
            indentingPrintWriter.println(this.job.getService().flattenToShortString());
            if (this.job.isPeriodic()) {
                indentingPrintWriter.print("PERIODIC: interval=");
                android.util.TimeUtils.formatDuration(this.job.getIntervalMillis(), indentingPrintWriter);
                indentingPrintWriter.print(" flex=");
                android.util.TimeUtils.formatDuration(this.job.getFlexMillis(), indentingPrintWriter);
                indentingPrintWriter.println();
            }
            if (this.job.isPersisted()) {
                indentingPrintWriter.println("PERSISTED");
            }
            if (this.job.getBias() != 0) {
                indentingPrintWriter.print("Bias: ");
                indentingPrintWriter.println(android.app.job.JobInfo.getBiasString(this.job.getBias()));
            }
            indentingPrintWriter.print("Priority: ");
            indentingPrintWriter.print(android.app.job.JobInfo.getPriorityString(this.job.getPriority()));
            int effectivePriority = getEffectivePriority();
            if (effectivePriority != this.job.getPriority()) {
                indentingPrintWriter.print(" effective=");
                indentingPrintWriter.print(android.app.job.JobInfo.getPriorityString(effectivePriority));
            }
            indentingPrintWriter.println();
            if (this.job.getFlags() != 0) {
                indentingPrintWriter.print("Flags: ");
                indentingPrintWriter.println(java.lang.Integer.toHexString(this.job.getFlags()));
            }
            if (getInternalFlags() != 0) {
                indentingPrintWriter.print("Internal flags: ");
                indentingPrintWriter.print(java.lang.Integer.toHexString(getInternalFlags()));
                if ((getInternalFlags() & 1) != 0) {
                    indentingPrintWriter.print(" HAS_FOREGROUND_EXEMPTION");
                }
                indentingPrintWriter.println();
            }
            indentingPrintWriter.print("Requires: charging=");
            indentingPrintWriter.print(this.job.isRequireCharging());
            indentingPrintWriter.print(" batteryNotLow=");
            indentingPrintWriter.print(this.job.isRequireBatteryNotLow());
            indentingPrintWriter.print(" deviceIdle=");
            indentingPrintWriter.println(this.job.isRequireDeviceIdle());
            if (this.job.getTriggerContentUris() != null) {
                indentingPrintWriter.println("Trigger content URIs:");
                indentingPrintWriter.increaseIndent();
                for (int i = 0; i < this.job.getTriggerContentUris().length; i++) {
                    android.app.job.JobInfo.TriggerContentUri triggerContentUri = this.job.getTriggerContentUris()[i];
                    indentingPrintWriter.print(java.lang.Integer.toHexString(triggerContentUri.getFlags()));
                    indentingPrintWriter.print(' ');
                    indentingPrintWriter.println(triggerContentUri.getUri());
                }
                indentingPrintWriter.decreaseIndent();
                if (this.job.getTriggerContentUpdateDelay() >= 0) {
                    indentingPrintWriter.print("Trigger update delay: ");
                    android.util.TimeUtils.formatDuration(this.job.getTriggerContentUpdateDelay(), indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                if (this.job.getTriggerContentMaxDelay() >= 0) {
                    indentingPrintWriter.print("Trigger max delay: ");
                    android.util.TimeUtils.formatDuration(this.job.getTriggerContentMaxDelay(), indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.print("Has media backup exemption", java.lang.Boolean.valueOf(this.mHasMediaBackupExemption)).println();
            }
            if (this.job.getExtras() != null && !this.job.getExtras().isDefinitelyEmpty()) {
                indentingPrintWriter.print("Extras: ");
                indentingPrintWriter.println(this.job.getExtras().toShortString());
            }
            if (this.job.getTransientExtras() != null && !this.job.getTransientExtras().isDefinitelyEmpty()) {
                indentingPrintWriter.print("Transient extras: ");
                indentingPrintWriter.println(this.job.getTransientExtras().toShortString());
            }
            if (this.job.getClipData() != null) {
                indentingPrintWriter.print("Clip data: ");
                java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
                sb.append(this.job.getClipData());
                indentingPrintWriter.println(sb);
            }
            if (this.uriPerms != null) {
                indentingPrintWriter.println("Granted URI permissions:");
                this.uriPerms.dump(indentingPrintWriter);
            }
            if (this.job.getRequiredNetwork() != null) {
                indentingPrintWriter.print("Network type: ");
                indentingPrintWriter.println(this.job.getRequiredNetwork());
            }
            if (this.mTotalNetworkDownloadBytes != -1) {
                indentingPrintWriter.print("Network download bytes: ");
                indentingPrintWriter.println(this.mTotalNetworkDownloadBytes);
            }
            if (this.mTotalNetworkUploadBytes != -1) {
                indentingPrintWriter.print("Network upload bytes: ");
                indentingPrintWriter.println(this.mTotalNetworkUploadBytes);
            }
            if (this.mMinimumNetworkChunkBytes != -1) {
                indentingPrintWriter.print("Minimum network chunk bytes: ");
                indentingPrintWriter.println(this.mMinimumNetworkChunkBytes);
            }
            if (this.job.getMinLatencyMillis() != 0) {
                indentingPrintWriter.print("Minimum latency: ");
                android.util.TimeUtils.formatDuration(this.job.getMinLatencyMillis(), indentingPrintWriter);
                indentingPrintWriter.println();
            }
            if (this.job.getMaxExecutionDelayMillis() != 0) {
                indentingPrintWriter.print("Max execution delay: ");
                android.util.TimeUtils.formatDuration(this.job.getMaxExecutionDelayMillis(), indentingPrintWriter);
                indentingPrintWriter.println();
            }
            indentingPrintWriter.print("Backoff: policy=");
            indentingPrintWriter.print(this.job.getBackoffPolicy());
            indentingPrintWriter.print(" initial=");
            android.util.TimeUtils.formatDuration(this.job.getInitialBackoffMillis(), indentingPrintWriter);
            indentingPrintWriter.println();
            if (this.job.hasEarlyConstraint()) {
                indentingPrintWriter.println("Has early constraint");
            }
            if (this.job.hasLateConstraint()) {
                indentingPrintWriter.println("Has late constraint");
            }
            if (this.job.getTraceTag() != null) {
                indentingPrintWriter.print("Trace tag: ");
                indentingPrintWriter.println(this.job.getTraceTag());
            }
            if (this.job.getDebugTags().size() > 0) {
                indentingPrintWriter.print("Debug tags: ");
                indentingPrintWriter.println(this.job.getDebugTags());
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.print("Required constraints:");
        dumpConstraints(indentingPrintWriter, this.requiredConstraints);
        indentingPrintWriter.println();
        indentingPrintWriter.print("Dynamic constraints:");
        dumpConstraints(indentingPrintWriter, this.mDynamicConstraints);
        indentingPrintWriter.println();
        if (z) {
            indentingPrintWriter.print("Satisfied constraints:");
            dumpConstraints(indentingPrintWriter, this.satisfiedConstraints);
            indentingPrintWriter.println();
            indentingPrintWriter.print("Unsatisfied constraints:");
            dumpConstraints(indentingPrintWriter, (this.requiredConstraints | 16777216 | 134217728) & (~this.satisfiedConstraints));
            indentingPrintWriter.println();
            if (hasFlexibilityConstraint()) {
                indentingPrintWriter.print("Num Required Flexible constraints: ");
                indentingPrintWriter.print(getNumRequiredFlexibleConstraints());
                indentingPrintWriter.println();
                indentingPrintWriter.print("Num Dropped Flexible constraints: ");
                indentingPrintWriter.print(getNumDroppedFlexibleConstraints());
                indentingPrintWriter.println();
            }
            indentingPrintWriter.println("Constraint history:");
            indentingPrintWriter.increaseIndent();
            for (int i2 = 0; i2 < 10; i2++) {
                int i3 = (this.mConstraintChangeHistoryIndex + i2) % 10;
                if (this.mConstraintUpdatedTimesElapsed[i3] != 0) {
                    android.util.TimeUtils.formatDuration(this.mConstraintUpdatedTimesElapsed[i3], j, indentingPrintWriter);
                    indentingPrintWriter.print(" =");
                    dumpConstraints(indentingPrintWriter, this.mConstraintStatusHistory[i3]);
                    indentingPrintWriter.println();
                }
            }
            indentingPrintWriter.decreaseIndent();
            if (this.appHasDozeExemption) {
                indentingPrintWriter.println("Doze whitelisted: true");
            }
            if (this.uidActive) {
                indentingPrintWriter.println("Uid: active");
            }
            if (this.job.isExemptedFromAppStandby()) {
                indentingPrintWriter.println("Is exempted from app standby");
            }
        }
        if (this.trackingControllers != 0) {
            indentingPrintWriter.print("Tracking:");
            if ((this.trackingControllers & 1) != 0) {
                indentingPrintWriter.print(" BATTERY");
            }
            if ((this.trackingControllers & 2) != 0) {
                indentingPrintWriter.print(" CONNECTIVITY");
            }
            if ((this.trackingControllers & 4) != 0) {
                indentingPrintWriter.print(" CONTENT");
            }
            if ((this.trackingControllers & 8) != 0) {
                indentingPrintWriter.print(" IDLE");
            }
            if ((this.trackingControllers & 16) != 0) {
                indentingPrintWriter.print(" STORAGE");
            }
            if ((this.trackingControllers & 32) != 0) {
                indentingPrintWriter.print(" TIME");
            }
            if ((this.trackingControllers & 64) != 0) {
                indentingPrintWriter.print(" QUOTA");
            }
            indentingPrintWriter.println();
        }
        indentingPrintWriter.println("Implicit constraints:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("readyNotDozing: ");
        indentingPrintWriter.println(this.mReadyNotDozing);
        indentingPrintWriter.print("readyNotRestrictedInBg: ");
        indentingPrintWriter.println(this.mReadyNotRestrictedInBg);
        if (!this.job.isPeriodic() && hasDeadlineConstraint()) {
            indentingPrintWriter.print("readyDeadlineSatisfied: ");
            indentingPrintWriter.println(this.mReadyDeadlineSatisfied);
        }
        if (this.mDynamicConstraints != 0) {
            indentingPrintWriter.print("readyDynamicSatisfied: ");
            indentingPrintWriter.println(this.mReadyDynamicSatisfied);
        }
        indentingPrintWriter.print("readyComponentEnabled: ");
        indentingPrintWriter.println(this.serviceProcessName != null);
        if ((getFlags() & 16) != 0) {
            indentingPrintWriter.print("expeditedQuotaApproved: ");
            indentingPrintWriter.print(this.mExpeditedQuotaApproved);
            indentingPrintWriter.print(" expeditedTareApproved: ");
            indentingPrintWriter.print(this.mExpeditedTareApproved);
            indentingPrintWriter.print(" (started as EJ: ");
            indentingPrintWriter.print(this.startedAsExpeditedJob);
            indentingPrintWriter.println(")");
        }
        if ((32 & getFlags()) != 0) {
            indentingPrintWriter.print("userInitiatedApproved: ");
            indentingPrintWriter.print(shouldTreatAsUserInitiatedJob());
            indentingPrintWriter.print(" (started as UIJ: ");
            indentingPrintWriter.print(this.startedAsUserInitiatedJob);
            indentingPrintWriter.println(")");
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.print("Started with foreground flag: ");
        indentingPrintWriter.println(this.startedWithForegroundFlag);
        if (this.mIsUserBgRestricted) {
            indentingPrintWriter.println("User BG restricted");
        }
        if (this.changedAuthorities != null) {
            indentingPrintWriter.println("Changed authorities:");
            indentingPrintWriter.increaseIndent();
            for (int i4 = 0; i4 < this.changedAuthorities.size(); i4++) {
                indentingPrintWriter.println(this.changedAuthorities.valueAt(i4));
            }
            indentingPrintWriter.decreaseIndent();
        }
        if (this.changedUris != null) {
            indentingPrintWriter.println("Changed URIs:");
            indentingPrintWriter.increaseIndent();
            for (int i5 = 0; i5 < this.changedUris.size(); i5++) {
                indentingPrintWriter.println(this.changedUris.valueAt(i5));
            }
            indentingPrintWriter.decreaseIndent();
        }
        if (this.network != null) {
            indentingPrintWriter.print("Network: ");
            indentingPrintWriter.println(this.network);
        }
        if (this.pendingWork != null && this.pendingWork.size() > 0) {
            indentingPrintWriter.println("Pending work:");
            for (int i6 = 0; i6 < this.pendingWork.size(); i6++) {
                dumpJobWorkItem(indentingPrintWriter, this.pendingWork.get(i6), i6);
            }
        }
        if (this.executingWork != null && this.executingWork.size() > 0) {
            indentingPrintWriter.println("Executing work:");
            for (int i7 = 0; i7 < this.executingWork.size(); i7++) {
                dumpJobWorkItem(indentingPrintWriter, this.executingWork.get(i7), i7);
            }
        }
        indentingPrintWriter.print("Standby bucket: ");
        indentingPrintWriter.println(getBucketName());
        indentingPrintWriter.increaseIndent();
        if (this.whenStandbyDeferred != 0) {
            indentingPrintWriter.print("Deferred since: ");
            android.util.TimeUtils.formatDuration(this.whenStandbyDeferred, j, indentingPrintWriter);
            indentingPrintWriter.println();
        }
        if (this.mFirstForceBatchedTimeElapsed != 0) {
            indentingPrintWriter.print("Time since first force batch attempt: ");
            android.util.TimeUtils.formatDuration(this.mFirstForceBatchedTimeElapsed, j, indentingPrintWriter);
            indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.print("Enqueue time: ");
        android.util.TimeUtils.formatDuration(this.enqueueTime, j, indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.print("Run time: earliest=");
        formatRunTime((java.io.PrintWriter) indentingPrintWriter, this.earliestRunTimeElapsedMillis, 0L, j);
        indentingPrintWriter.print(", latest=");
        formatRunTime((java.io.PrintWriter) indentingPrintWriter, this.latestRunTimeElapsedMillis, NO_LATEST_RUNTIME, j);
        indentingPrintWriter.print(", original latest=");
        formatRunTime((java.io.PrintWriter) indentingPrintWriter, this.mOriginalLatestRunTimeElapsedMillis, NO_LATEST_RUNTIME, j);
        indentingPrintWriter.println();
        if (this.mCumulativeExecutionTimeMs != 0) {
            indentingPrintWriter.print("Cumulative execution time=");
            android.util.TimeUtils.formatDuration(this.mCumulativeExecutionTimeMs, indentingPrintWriter);
            indentingPrintWriter.println();
        }
        if (this.numFailures != 0) {
            indentingPrintWriter.print("Num failures: ");
            indentingPrintWriter.println(this.numFailures);
        }
        if (this.mNumSystemStops != 0) {
            indentingPrintWriter.print("Num system stops: ");
            indentingPrintWriter.println(this.mNumSystemStops);
        }
        if (this.mLastSuccessfulRunTime != 0) {
            indentingPrintWriter.print("Last successful run: ");
            indentingPrintWriter.println(formatTime(this.mLastSuccessfulRunTime));
        }
        if (this.mLastFailedRunTime != 0) {
            indentingPrintWriter.print("Last failed run: ");
            indentingPrintWriter.println(formatTime(this.mLastFailedRunTime));
        }
    }

    private static java.lang.CharSequence formatTime(long j) {
        return android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", j);
    }

    public void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j, boolean z, long j2) {
        long j3;
        long start = protoOutputStream.start(j);
        long j4 = 1120986464257L;
        protoOutputStream.write(1120986464257L, this.callingUid);
        protoOutputStream.write(1138166333442L, getWakelockTag());
        protoOutputStream.write(1120986464259L, getSourceUid());
        protoOutputStream.write(1120986464260L, getSourceUserId());
        protoOutputStream.write(1138166333445L, getSourcePackageName());
        if (z) {
            long start2 = protoOutputStream.start(1146756268038L);
            this.job.getService().dumpDebug(protoOutputStream, 1146756268033L);
            protoOutputStream.write(1133871366146L, this.job.isPeriodic());
            protoOutputStream.write(1112396529667L, this.job.getIntervalMillis());
            protoOutputStream.write(1112396529668L, this.job.getFlexMillis());
            protoOutputStream.write(1133871366149L, this.job.isPersisted());
            protoOutputStream.write(1172526071814L, this.job.getBias());
            protoOutputStream.write(1120986464263L, this.job.getFlags());
            protoOutputStream.write(1112396529688L, getInternalFlags());
            protoOutputStream.write(1133871366152L, this.job.isRequireCharging());
            protoOutputStream.write(1133871366153L, this.job.isRequireBatteryNotLow());
            protoOutputStream.write(1133871366154L, this.job.isRequireDeviceIdle());
            if (this.job.getTriggerContentUris() != null) {
                int i = 0;
                while (i < this.job.getTriggerContentUris().length) {
                    long start3 = protoOutputStream.start(2246267895819L);
                    android.app.job.JobInfo.TriggerContentUri triggerContentUri = this.job.getTriggerContentUris()[i];
                    protoOutputStream.write(j4, triggerContentUri.getFlags());
                    android.net.Uri uri = triggerContentUri.getUri();
                    if (uri != null) {
                        protoOutputStream.write(1138166333442L, uri.toString());
                    }
                    protoOutputStream.end(start3);
                    i++;
                    j4 = 1120986464257L;
                }
                if (this.job.getTriggerContentUpdateDelay() >= 0) {
                    protoOutputStream.write(1112396529676L, this.job.getTriggerContentUpdateDelay());
                }
                if (this.job.getTriggerContentMaxDelay() >= 0) {
                    protoOutputStream.write(1112396529677L, this.job.getTriggerContentMaxDelay());
                }
            }
            if (this.job.getExtras() != null && !this.job.getExtras().isDefinitelyEmpty()) {
                this.job.getExtras().dumpDebug(protoOutputStream, 1146756268046L);
            }
            if (this.job.getTransientExtras() != null && !this.job.getTransientExtras().isDefinitelyEmpty()) {
                this.job.getTransientExtras().dumpDebug(protoOutputStream, 1146756268047L);
            }
            if (this.job.getClipData() != null) {
                this.job.getClipData().dumpDebug(protoOutputStream, 1146756268048L);
            }
            if (this.uriPerms != null) {
                this.uriPerms.dump(protoOutputStream, 1146756268049L);
            }
            if (this.mTotalNetworkDownloadBytes != -1) {
                protoOutputStream.write(1112396529689L, this.mTotalNetworkDownloadBytes);
            }
            if (this.mTotalNetworkUploadBytes != -1) {
                protoOutputStream.write(1112396529690L, this.mTotalNetworkUploadBytes);
            }
            protoOutputStream.write(1112396529684L, this.job.getMinLatencyMillis());
            protoOutputStream.write(1112396529685L, this.job.getMaxExecutionDelayMillis());
            long start4 = protoOutputStream.start(1146756268054L);
            protoOutputStream.write(1159641169921L, this.job.getBackoffPolicy());
            protoOutputStream.write(1112396529666L, this.job.getInitialBackoffMillis());
            protoOutputStream.end(start4);
            protoOutputStream.write(1133871366167L, this.job.hasEarlyConstraint());
            protoOutputStream.write(1133871366168L, this.job.hasLateConstraint());
            protoOutputStream.end(start2);
        }
        dumpConstraints(protoOutputStream, 2259152797703L, this.requiredConstraints);
        dumpConstraints(protoOutputStream, 2259152797727L, this.mDynamicConstraints);
        if (z) {
            dumpConstraints(protoOutputStream, 2259152797704L, this.satisfiedConstraints);
            dumpConstraints(protoOutputStream, 2259152797705L, (this.requiredConstraints | 16777216) & (~this.satisfiedConstraints));
            protoOutputStream.write(1133871366154L, this.appHasDozeExemption);
            protoOutputStream.write(1133871366170L, this.uidActive);
            protoOutputStream.write(1133871366171L, this.job.isExemptedFromAppStandby());
        }
        if ((this.trackingControllers & 1) != 0) {
            protoOutputStream.write(2259152797707L, 0);
        }
        if ((this.trackingControllers & 2) != 0) {
            protoOutputStream.write(2259152797707L, 1);
        }
        if ((this.trackingControllers & 4) != 0) {
            protoOutputStream.write(2259152797707L, 2);
        }
        if ((this.trackingControllers & 8) != 0) {
            protoOutputStream.write(2259152797707L, 3);
        }
        if ((this.trackingControllers & 16) != 0) {
            protoOutputStream.write(2259152797707L, 4);
        }
        if ((this.trackingControllers & 32) != 0) {
            protoOutputStream.write(2259152797707L, 5);
        }
        if ((this.trackingControllers & 64) != 0) {
            protoOutputStream.write(2259152797707L, 6);
        }
        long start5 = protoOutputStream.start(1146756268057L);
        protoOutputStream.write(1133871366145L, this.mReadyNotDozing);
        protoOutputStream.write(1133871366146L, this.mReadyNotRestrictedInBg);
        protoOutputStream.write(1133871366147L, this.mReadyDynamicSatisfied);
        protoOutputStream.end(start5);
        if (this.changedAuthorities != null) {
            for (int i2 = 0; i2 < this.changedAuthorities.size(); i2++) {
                protoOutputStream.write(2237677961228L, this.changedAuthorities.valueAt(i2));
            }
        }
        if (this.changedUris != null) {
            for (int i3 = 0; i3 < this.changedUris.size(); i3++) {
                protoOutputStream.write(2237677961229L, this.changedUris.valueAt(i3).toString());
            }
        }
        if (this.pendingWork != null) {
            for (int i4 = 0; i4 < this.pendingWork.size(); i4++) {
                dumpJobWorkItem(protoOutputStream, 2246267895823L, this.pendingWork.get(i4));
            }
        }
        if (this.executingWork != null) {
            for (int i5 = 0; i5 < this.executingWork.size(); i5++) {
                dumpJobWorkItem(protoOutputStream, 2246267895824L, this.executingWork.get(i5));
            }
        }
        protoOutputStream.write(1159641169937L, this.standbyBucket);
        protoOutputStream.write(1112396529682L, j2 - this.enqueueTime);
        protoOutputStream.write(1112396529692L, this.whenStandbyDeferred == 0 ? 0L : j2 - this.whenStandbyDeferred);
        if (this.mFirstForceBatchedTimeElapsed != 0) {
            j3 = j2 - this.mFirstForceBatchedTimeElapsed;
        } else {
            j3 = 0;
        }
        protoOutputStream.write(1112396529693L, j3);
        if (this.earliestRunTimeElapsedMillis == 0) {
            protoOutputStream.write(1176821039123L, 0);
        } else {
            protoOutputStream.write(1176821039123L, this.earliestRunTimeElapsedMillis - j2);
        }
        if (this.latestRunTimeElapsedMillis == NO_LATEST_RUNTIME) {
            protoOutputStream.write(1176821039124L, 0);
        } else {
            protoOutputStream.write(1176821039124L, this.latestRunTimeElapsedMillis - j2);
        }
        protoOutputStream.write(1116691496990L, this.mOriginalLatestRunTimeElapsedMillis);
        protoOutputStream.write(1120986464277L, this.numFailures + this.mNumSystemStops);
        protoOutputStream.write(1112396529686L, this.mLastSuccessfulRunTime);
        protoOutputStream.write(1112396529687L, this.mLastFailedRunTime);
        protoOutputStream.end(start);
    }
}
