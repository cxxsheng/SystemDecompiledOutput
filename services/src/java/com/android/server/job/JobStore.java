package com.android.server.job;

/* loaded from: classes2.dex */
public final class JobStore {
    private static final int ALL_UIDS = -1;

    @com.android.internal.annotations.VisibleForTesting
    static final int INVALID_UID = -2;
    private static final int JOBS_FILE_VERSION = 1;

    @com.android.internal.annotations.VisibleForTesting
    static final java.lang.String JOB_FILE_SPLIT_PREFIX = "jobs_";
    private static final long JOB_PERSIST_DELAY = 2000;
    private static final long SCHEDULED_JOB_HIGH_WATER_MARK_PERIOD_MS = 1800000;
    private static final java.lang.String TAG = "JobStore";
    private static final java.lang.String XML_TAG_DEBUG_INFO = "debug-info";
    private static final java.lang.String XML_TAG_DEBUG_TAG = "debug-tag";
    private static final java.lang.String XML_TAG_EXTRAS = "extras";
    private static final java.lang.String XML_TAG_JOB = "job";
    private static final java.lang.String XML_TAG_JOB_INFO = "job-info";
    private static final java.lang.String XML_TAG_JOB_WORK_ITEM = "job-work-item";
    private static final java.lang.String XML_TAG_ONEOFF = "one-off";
    private static final java.lang.String XML_TAG_PARAMS_CONSTRAINTS = "constraints";
    private static final java.lang.String XML_TAG_PERIODIC = "periodic";
    private static com.android.server.job.JobStore sSingleton;
    final android.content.Context mContext;
    private final android.util.SystemConfigFileCommitEventLogger mEventLogger;
    private final java.io.File mJobFileDirectory;
    final com.android.server.job.JobStore.JobSet mJobSet;
    private final android.util.AtomicFile mJobsFile;
    final java.lang.Object mLock;
    private boolean mRtcGood;

    @com.android.internal.annotations.GuardedBy({"mWriteScheduleLock"})
    private boolean mSplitFileMigrationNeeded;

    @com.android.internal.annotations.GuardedBy({"mWriteScheduleLock"})
    private boolean mWriteInProgress;

    @com.android.internal.annotations.GuardedBy({"mWriteScheduleLock"})
    private boolean mWriteScheduled;
    private final long mXmlTimestamp;
    private static final boolean DEBUG = com.android.server.job.JobSchedulerService.DEBUG;
    private static final java.util.regex.Pattern SPLIT_FILE_PATTERN = java.util.regex.Pattern.compile("^jobs_\\d+.xml$");
    private static final java.lang.Object sSingletonLock = new java.lang.Object();
    private static final com.android.modules.expresslog.Histogram sScheduledJob30MinHighWaterMarkLogger = new com.android.modules.expresslog.Histogram("job_scheduler.value_hist_scheduled_job_30_min_high_water_mark", new com.android.modules.expresslog.Histogram.ScaledRangeOptions(15, 1, 99.0f, 1.5f));
    private final android.util.SparseBooleanArray mPendingJobWriteUids = new android.util.SparseBooleanArray();
    private final android.os.Handler mIoHandler = com.android.server.IoThread.getHandler();
    private boolean mUseSplitFiles = true;
    private com.android.server.job.JobSchedulerInternal.JobStorePersistStats mPersistInfo = new com.android.server.job.JobSchedulerInternal.JobStorePersistStats();
    private int mCurrentJobSetSize = 0;
    private int mScheduledJob30MinHighWaterMark = 0;
    private final java.lang.Runnable mScheduledJobHighWaterMarkLoggingRunnable = new java.lang.Runnable() { // from class: com.android.server.job.JobStore.1
        @Override // java.lang.Runnable
        public void run() {
            com.android.server.AppSchedulingModuleThread.getHandler().removeCallbacks(this);
            synchronized (com.android.server.job.JobStore.this.mLock) {
                com.android.server.job.JobStore.sScheduledJob30MinHighWaterMarkLogger.logSample(com.android.server.job.JobStore.this.mScheduledJob30MinHighWaterMark);
                com.android.server.job.JobStore.this.mScheduledJob30MinHighWaterMark = com.android.server.job.JobStore.this.mJobSet.size();
            }
            com.android.server.AppSchedulingModuleThread.getHandler().postDelayed(this, 1800000L);
        }
    };
    private final java.lang.Runnable mWriteRunnable = new java.lang.Runnable() { // from class: com.android.server.job.JobStore.2
        private final android.util.SparseArray<android.util.AtomicFile> mJobFiles = new android.util.SparseArray<>();
        private final com.android.server.job.JobStore.AnonymousClass2.CopyConsumer mPersistedJobCopier = new com.android.server.job.JobStore.AnonymousClass2.CopyConsumer();

        /* renamed from: com.android.server.job.JobStore$2$CopyConsumer */
        class CopyConsumer implements java.util.function.Consumer<com.android.server.job.controllers.JobStatus> {
            private boolean mCopyAllJobs;
            private final android.util.SparseArray<java.util.List<com.android.server.job.controllers.JobStatus>> mJobStoreCopy = new android.util.SparseArray<>();

            CopyConsumer() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void prepare() {
                int i = 0;
                this.mCopyAllJobs = !com.android.server.job.JobStore.this.mUseSplitFiles || com.android.server.job.JobStore.this.mPendingJobWriteUids.get(-1);
                if (com.android.server.job.JobStore.this.mUseSplitFiles) {
                    if (com.android.server.job.JobStore.this.mPendingJobWriteUids.get(-1)) {
                        try {
                            java.io.File[] listFiles = com.android.server.job.JobStore.this.mJobFileDirectory.listFiles();
                            if (listFiles == null) {
                                android.util.Slog.wtfStack(com.android.server.job.JobStore.TAG, "Couldn't get job file list");
                                return;
                            }
                            int length = listFiles.length;
                            while (i < length) {
                                int extractUidFromJobFileName = com.android.server.job.JobStore.extractUidFromJobFileName(listFiles[i]);
                                if (extractUidFromJobFileName != -2) {
                                    this.mJobStoreCopy.put(extractUidFromJobFileName, new java.util.ArrayList());
                                }
                                i++;
                            }
                            return;
                        } catch (java.lang.SecurityException e) {
                            android.util.Slog.wtf(com.android.server.job.JobStore.TAG, "Not allowed to read job file directory", e);
                            return;
                        }
                    }
                    while (i < com.android.server.job.JobStore.this.mPendingJobWriteUids.size()) {
                        this.mJobStoreCopy.put(com.android.server.job.JobStore.this.mPendingJobWriteUids.keyAt(i), new java.util.ArrayList());
                        i++;
                    }
                    return;
                }
                this.mJobStoreCopy.put(-1, new java.util.ArrayList());
            }

            @Override // java.util.function.Consumer
            public void accept(com.android.server.job.controllers.JobStatus jobStatus) {
                int uid = com.android.server.job.JobStore.this.mUseSplitFiles ? jobStatus.getUid() : -1;
                if (jobStatus.isPersisted()) {
                    if (this.mCopyAllJobs || com.android.server.job.JobStore.this.mPendingJobWriteUids.get(uid)) {
                        java.util.List<com.android.server.job.controllers.JobStatus> list = this.mJobStoreCopy.get(uid);
                        if (list == null) {
                            list = new java.util.ArrayList<>();
                            this.mJobStoreCopy.put(uid, list);
                        }
                        list.add(new com.android.server.job.controllers.JobStatus(jobStatus));
                    }
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void reset() {
                this.mJobStoreCopy.clear();
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z;
            android.util.AtomicFile atomicFile;
            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            synchronized (com.android.server.job.JobStore.this.mWriteScheduleLock) {
                try {
                    com.android.server.job.JobStore.this.mWriteScheduled = false;
                    if (com.android.server.job.JobStore.this.mWriteInProgress) {
                        com.android.server.job.JobStore.this.maybeWriteStatusToDiskAsync();
                        return;
                    }
                    com.android.server.job.JobStore.this.mWriteInProgress = true;
                    synchronized (com.android.server.job.JobStore.this.mLock) {
                        z = com.android.server.job.JobStore.this.mUseSplitFiles;
                        this.mPersistedJobCopier.prepare();
                        com.android.server.job.JobStore.this.mJobSet.forEachJob((java.util.function.Predicate<com.android.server.job.controllers.JobStatus>) null, this.mPersistedJobCopier);
                        com.android.server.job.JobStore.this.mPendingJobWriteUids.clear();
                    }
                    com.android.server.job.JobStore.this.mPersistInfo.countAllJobsSaved = 0;
                    com.android.server.job.JobStore.this.mPersistInfo.countSystemServerJobsSaved = 0;
                    com.android.server.job.JobStore.this.mPersistInfo.countSystemSyncManagerJobsSaved = 0;
                    for (int size = this.mPersistedJobCopier.mJobStoreCopy.size() - 1; size >= 0; size--) {
                        if (z) {
                            int keyAt = this.mPersistedJobCopier.mJobStoreCopy.keyAt(size);
                            atomicFile = this.mJobFiles.get(keyAt);
                            if (atomicFile == null) {
                                atomicFile = com.android.server.job.JobStore.this.createJobFile(com.android.server.job.JobStore.JOB_FILE_SPLIT_PREFIX + keyAt);
                                this.mJobFiles.put(keyAt, atomicFile);
                            }
                        } else {
                            atomicFile = com.android.server.job.JobStore.this.mJobsFile;
                        }
                        if (com.android.server.job.JobStore.DEBUG) {
                            android.util.Slog.d(com.android.server.job.JobStore.TAG, "Writing for " + this.mPersistedJobCopier.mJobStoreCopy.keyAt(size) + " to " + atomicFile.getBaseFile().getName() + ": " + ((java.util.List) this.mPersistedJobCopier.mJobStoreCopy.valueAt(size)).size() + " jobs");
                        }
                        writeJobsMapImpl(atomicFile, (java.util.List) this.mPersistedJobCopier.mJobStoreCopy.valueAt(size));
                    }
                    if (com.android.server.job.JobStore.DEBUG) {
                        android.util.Slog.v(com.android.server.job.JobStore.TAG, "Finished writing, took " + (com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis() - millis) + "ms");
                    }
                    this.mPersistedJobCopier.reset();
                    if (!z) {
                        this.mJobFiles.clear();
                    }
                    com.android.server.job.JobStore.this.mJobFileDirectory.setLastModified(com.android.server.job.JobSchedulerService.sSystemClock.millis());
                    synchronized (com.android.server.job.JobStore.this.mWriteScheduleLock) {
                        try {
                            if (com.android.server.job.JobStore.this.mSplitFileMigrationNeeded) {
                                for (java.io.File file : com.android.server.job.JobStore.this.mJobFileDirectory.listFiles()) {
                                    if (z) {
                                        if (!file.getName().startsWith(com.android.server.job.JobStore.JOB_FILE_SPLIT_PREFIX)) {
                                            file.delete();
                                        }
                                    } else if (file.getName().startsWith(com.android.server.job.JobStore.JOB_FILE_SPLIT_PREFIX)) {
                                        file.delete();
                                    }
                                }
                            }
                            com.android.server.job.JobStore.this.mWriteInProgress = false;
                            com.android.server.job.JobStore.this.mWriteScheduleLock.notifyAll();
                        } finally {
                        }
                    }
                } finally {
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:37:0x0119 A[Catch: all -> 0x00eb, TRY_LEAVE, TryCatch #8 {all -> 0x00eb, blocks: (B:40:0x00fa, B:42:0x0100, B:34:0x0113, B:37:0x0119, B:51:0x00f1, B:62:0x00e7), top: B:2:0x0016 }] */
        /* JADX WARN: Removed duplicated region for block: B:42:0x0100 A[Catch: all -> 0x00eb, TRY_LEAVE, TryCatch #8 {all -> 0x00eb, blocks: (B:40:0x00fa, B:42:0x0100, B:34:0x0113, B:37:0x0119, B:51:0x00f1, B:62:0x00e7), top: B:2:0x0016 }] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void writeJobsMapImpl(@android.annotation.NonNull android.util.AtomicFile atomicFile, @android.annotation.NonNull java.util.List<com.android.server.job.controllers.JobStatus> list) {
            int i;
            int i2;
            com.android.server.job.JobSchedulerInternal.JobStorePersistStats jobStorePersistStats;
            int i3;
            java.io.FileOutputStream startWrite;
            com.android.server.job.JobStore.this.mEventLogger.setStartTime(android.os.SystemClock.uptimeMillis());
            int i4 = 0;
            try {
                try {
                    startWrite = atomicFile.startWrite();
                } catch (java.io.IOException e) {
                    e = e;
                    i = 0;
                    i2 = 0;
                } catch (org.xmlpull.v1.XmlPullParserException e2) {
                    e = e2;
                    i = 0;
                    i2 = 0;
                } catch (java.lang.Throwable th) {
                    th = th;
                    i = 0;
                    i2 = 0;
                }
                try {
                    com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((java.lang.String) null, true);
                    resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    resolveSerializer.startTag((java.lang.String) null, com.android.server.job.JobStore.XML_TAG_JOB_INFO);
                    resolveSerializer.attribute((java.lang.String) null, "version", java.lang.Integer.toString(1));
                    int i5 = 0;
                    i = 0;
                    i2 = 0;
                    while (i4 < list.size()) {
                        try {
                            com.android.server.job.controllers.JobStatus jobStatus = list.get(i4);
                            if (com.android.server.job.JobStore.DEBUG) {
                                android.util.Slog.d(com.android.server.job.JobStore.TAG, "Saving job " + jobStatus.getJobId());
                            }
                            resolveSerializer.startTag((java.lang.String) null, "job");
                            addAttributesToJobTag(resolveSerializer, jobStatus);
                            writeConstraintsToXml(resolveSerializer, jobStatus);
                            writeExecutionCriteriaToXml(resolveSerializer, jobStatus);
                            writeBundleToXml(jobStatus.getJob().getExtras(), resolveSerializer);
                            writeJobWorkItemsToXml(resolveSerializer, jobStatus);
                            writeDebugInfoToXml(resolveSerializer, jobStatus);
                            resolveSerializer.endTag((java.lang.String) null, "job");
                            i5++;
                            if (jobStatus.getUid() == 1000) {
                                i++;
                                if (com.android.server.job.JobStore.isSyncJob(jobStatus)) {
                                    i2++;
                                }
                            }
                            i4++;
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            i4 = i5;
                            if (startWrite != null) {
                                try {
                                    startWrite.close();
                                } catch (java.lang.Throwable th3) {
                                    try {
                                        th.addSuppressed(th3);
                                    } catch (java.io.IOException e3) {
                                        e = e3;
                                        if (com.android.server.job.JobStore.DEBUG) {
                                            android.util.Slog.v(com.android.server.job.JobStore.TAG, "Error writing out job data.", e);
                                        }
                                        jobStorePersistStats = com.android.server.job.JobStore.this.mPersistInfo;
                                        i3 = jobStorePersistStats.countAllJobsSaved + i4;
                                        jobStorePersistStats.countAllJobsSaved = i3;
                                        com.android.server.job.JobStore.this.mPersistInfo.countSystemServerJobsSaved += i;
                                        com.android.server.job.JobStore.this.mPersistInfo.countSystemSyncManagerJobsSaved += i2;
                                    } catch (org.xmlpull.v1.XmlPullParserException e4) {
                                        e = e4;
                                        if (com.android.server.job.JobStore.DEBUG) {
                                            android.util.Slog.d(com.android.server.job.JobStore.TAG, "Error persisting bundle.", e);
                                        }
                                        jobStorePersistStats = com.android.server.job.JobStore.this.mPersistInfo;
                                        i3 = jobStorePersistStats.countAllJobsSaved + i4;
                                        jobStorePersistStats.countAllJobsSaved = i3;
                                        com.android.server.job.JobStore.this.mPersistInfo.countSystemServerJobsSaved += i;
                                        com.android.server.job.JobStore.this.mPersistInfo.countSystemSyncManagerJobsSaved += i2;
                                    }
                                }
                            }
                            throw th;
                        }
                    }
                    resolveSerializer.endTag((java.lang.String) null, com.android.server.job.JobStore.XML_TAG_JOB_INFO);
                    resolveSerializer.endDocument();
                    atomicFile.finishWrite(startWrite);
                    if (startWrite != null) {
                        try {
                            startWrite.close();
                        } catch (java.io.IOException e5) {
                            e = e5;
                            i4 = i5;
                            if (com.android.server.job.JobStore.DEBUG) {
                            }
                            jobStorePersistStats = com.android.server.job.JobStore.this.mPersistInfo;
                            i3 = jobStorePersistStats.countAllJobsSaved + i4;
                            jobStorePersistStats.countAllJobsSaved = i3;
                            com.android.server.job.JobStore.this.mPersistInfo.countSystemServerJobsSaved += i;
                            com.android.server.job.JobStore.this.mPersistInfo.countSystemSyncManagerJobsSaved += i2;
                        } catch (org.xmlpull.v1.XmlPullParserException e6) {
                            e = e6;
                            i4 = i5;
                            if (com.android.server.job.JobStore.DEBUG) {
                            }
                            jobStorePersistStats = com.android.server.job.JobStore.this.mPersistInfo;
                            i3 = jobStorePersistStats.countAllJobsSaved + i4;
                            jobStorePersistStats.countAllJobsSaved = i3;
                            com.android.server.job.JobStore.this.mPersistInfo.countSystemServerJobsSaved += i;
                            com.android.server.job.JobStore.this.mPersistInfo.countSystemSyncManagerJobsSaved += i2;
                        } catch (java.lang.Throwable th4) {
                            th = th4;
                            i4 = i5;
                            com.android.server.job.JobStore.this.mPersistInfo.countAllJobsSaved += i4;
                            com.android.server.job.JobStore.this.mPersistInfo.countSystemServerJobsSaved += i;
                            com.android.server.job.JobStore.this.mPersistInfo.countSystemSyncManagerJobsSaved += i2;
                            throw th;
                        }
                    }
                    jobStorePersistStats = com.android.server.job.JobStore.this.mPersistInfo;
                    i3 = jobStorePersistStats.countAllJobsSaved + i5;
                    jobStorePersistStats.countAllJobsSaved = i3;
                    com.android.server.job.JobStore.this.mPersistInfo.countSystemServerJobsSaved += i;
                    com.android.server.job.JobStore.this.mPersistInfo.countSystemSyncManagerJobsSaved += i2;
                } catch (java.lang.Throwable th5) {
                    th = th5;
                    i = 0;
                    i2 = 0;
                }
            } catch (java.lang.Throwable th6) {
                th = th6;
            }
        }

        private void addAttributesToJobTag(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.server.job.controllers.JobStatus jobStatus) throws java.io.IOException {
            typedXmlSerializer.attribute((java.lang.String) null, "jobid", java.lang.Integer.toString(jobStatus.getJobId()));
            typedXmlSerializer.attribute((java.lang.String) null, com.android.server.pm.Settings.ATTR_PACKAGE, jobStatus.getServiceComponent().getPackageName());
            typedXmlSerializer.attribute((java.lang.String) null, "class", jobStatus.getServiceComponent().getClassName());
            if (jobStatus.getSourcePackageName() != null) {
                typedXmlSerializer.attribute((java.lang.String) null, "sourcePackageName", jobStatus.getSourcePackageName());
            }
            if (jobStatus.getNamespace() != null) {
                typedXmlSerializer.attribute((java.lang.String) null, "namespace", jobStatus.getNamespace());
            }
            if (jobStatus.getSourceTag() != null) {
                typedXmlSerializer.attribute((java.lang.String) null, "sourceTag", jobStatus.getSourceTag());
            }
            typedXmlSerializer.attribute((java.lang.String) null, "sourceUserId", java.lang.String.valueOf(jobStatus.getSourceUserId()));
            typedXmlSerializer.attribute((java.lang.String) null, com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID, java.lang.Integer.toString(jobStatus.getUid()));
            typedXmlSerializer.attribute((java.lang.String) null, "bias", java.lang.String.valueOf(jobStatus.getBias()));
            typedXmlSerializer.attribute((java.lang.String) null, "priority", java.lang.String.valueOf(jobStatus.getJob().getPriority()));
            typedXmlSerializer.attribute((java.lang.String) null, "flags", java.lang.String.valueOf(jobStatus.getFlags()));
            if (jobStatus.getInternalFlags() != 0) {
                typedXmlSerializer.attribute((java.lang.String) null, "internalFlags", java.lang.String.valueOf(jobStatus.getInternalFlags()));
            }
            typedXmlSerializer.attribute((java.lang.String) null, "lastSuccessfulRunTime", java.lang.String.valueOf(jobStatus.getLastSuccessfulRunTime()));
            typedXmlSerializer.attribute((java.lang.String) null, "lastFailedRunTime", java.lang.String.valueOf(jobStatus.getLastFailedRunTime()));
            typedXmlSerializer.attributeLong((java.lang.String) null, "cumulativeExecutionTime", jobStatus.getCumulativeExecutionTimeMs());
        }

        private void writeBundleToXml(android.os.PersistableBundle persistableBundle, org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            xmlSerializer.startTag(null, com.android.server.job.JobStore.XML_TAG_EXTRAS);
            deepCopyBundle(persistableBundle, 10).saveToXml(xmlSerializer);
            xmlSerializer.endTag(null, com.android.server.job.JobStore.XML_TAG_EXTRAS);
        }

        private android.os.PersistableBundle deepCopyBundle(android.os.PersistableBundle persistableBundle, int i) {
            if (i <= 0) {
                return null;
            }
            android.os.PersistableBundle persistableBundle2 = (android.os.PersistableBundle) persistableBundle.clone();
            for (java.lang.String str : persistableBundle.keySet()) {
                java.lang.Object obj = persistableBundle2.get(str);
                if (obj instanceof android.os.PersistableBundle) {
                    persistableBundle2.putPersistableBundle(str, deepCopyBundle((android.os.PersistableBundle) obj, i - 1));
                }
            }
            return persistableBundle2;
        }

        private void writeConstraintsToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, com.android.server.job.controllers.JobStatus jobStatus) throws java.io.IOException {
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.job.JobStore.XML_TAG_PARAMS_CONSTRAINTS);
            android.app.job.JobInfo job = jobStatus.getJob();
            if (jobStatus.hasConnectivityConstraint()) {
                android.net.NetworkRequest requiredNetwork = jobStatus.getJob().getRequiredNetwork();
                typedXmlSerializer.attribute((java.lang.String) null, "net-capabilities-csv", com.android.server.job.JobStore.intArrayToString(requiredNetwork.getCapabilities()));
                typedXmlSerializer.attribute((java.lang.String) null, "net-forbidden-capabilities-csv", com.android.server.job.JobStore.intArrayToString(requiredNetwork.getForbiddenCapabilities()));
                typedXmlSerializer.attribute((java.lang.String) null, "net-transport-types-csv", com.android.server.job.JobStore.intArrayToString(requiredNetwork.getTransportTypes()));
                if (job.getEstimatedNetworkDownloadBytes() != -1) {
                    typedXmlSerializer.attributeLong((java.lang.String) null, "estimated-download-bytes", job.getEstimatedNetworkDownloadBytes());
                }
                if (job.getEstimatedNetworkUploadBytes() != -1) {
                    typedXmlSerializer.attributeLong((java.lang.String) null, "estimated-upload-bytes", job.getEstimatedNetworkUploadBytes());
                }
                if (job.getMinimumNetworkChunkBytes() != -1) {
                    typedXmlSerializer.attributeLong((java.lang.String) null, "minimum-network-chunk-bytes", job.getMinimumNetworkChunkBytes());
                }
            }
            if (job.isRequireDeviceIdle()) {
                typedXmlSerializer.attribute((java.lang.String) null, "idle", java.lang.Boolean.toString(true));
            }
            if (job.isRequireCharging()) {
                typedXmlSerializer.attribute((java.lang.String) null, "charging", java.lang.Boolean.toString(true));
            }
            if (job.isRequireBatteryNotLow()) {
                typedXmlSerializer.attribute((java.lang.String) null, "battery-not-low", java.lang.Boolean.toString(true));
            }
            if (job.isRequireStorageNotLow()) {
                typedXmlSerializer.attribute((java.lang.String) null, "storage-not-low", java.lang.Boolean.toString(true));
            }
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.job.JobStore.XML_TAG_PARAMS_CONSTRAINTS);
        }

        private void writeExecutionCriteriaToXml(org.xmlpull.v1.XmlSerializer xmlSerializer, com.android.server.job.controllers.JobStatus jobStatus) throws java.io.IOException {
            long longValue;
            long longValue2;
            android.app.job.JobInfo job = jobStatus.getJob();
            if (jobStatus.getJob().isPeriodic()) {
                xmlSerializer.startTag(null, com.android.server.job.JobStore.XML_TAG_PERIODIC);
                xmlSerializer.attribute(null, "period", java.lang.Long.toString(job.getIntervalMillis()));
                xmlSerializer.attribute(null, "flex", java.lang.Long.toString(job.getFlexMillis()));
            } else {
                xmlSerializer.startTag(null, com.android.server.job.JobStore.XML_TAG_ONEOFF);
            }
            android.util.Pair<java.lang.Long, java.lang.Long> persistedUtcTimes = jobStatus.getPersistedUtcTimes();
            if (com.android.server.job.JobStore.DEBUG && persistedUtcTimes != null) {
                android.util.Slog.i(com.android.server.job.JobStore.TAG, "storing original UTC timestamps for " + jobStatus);
            }
            long millis = com.android.server.job.JobSchedulerService.sSystemClock.millis();
            long millis2 = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            if (jobStatus.hasDeadlineConstraint()) {
                if (persistedUtcTimes == null) {
                    longValue2 = (jobStatus.getLatestRunTimeElapsed() - millis2) + millis;
                } else {
                    longValue2 = ((java.lang.Long) persistedUtcTimes.second).longValue();
                }
                xmlSerializer.attribute(null, "deadline", java.lang.Long.toString(longValue2));
            }
            if (jobStatus.hasTimingDelayConstraint()) {
                if (persistedUtcTimes == null) {
                    longValue = millis + (jobStatus.getEarliestRunTime() - millis2);
                } else {
                    longValue = ((java.lang.Long) persistedUtcTimes.first).longValue();
                }
                xmlSerializer.attribute(null, "delay", java.lang.Long.toString(longValue));
            }
            if (jobStatus.getJob().getInitialBackoffMillis() != 30000 || jobStatus.getJob().getBackoffPolicy() != 1) {
                xmlSerializer.attribute(null, "backoff-policy", java.lang.Integer.toString(job.getBackoffPolicy()));
                xmlSerializer.attribute(null, "initial-backoff", java.lang.Long.toString(job.getInitialBackoffMillis()));
            }
            if (job.isPeriodic()) {
                xmlSerializer.endTag(null, com.android.server.job.JobStore.XML_TAG_PERIODIC);
            } else {
                xmlSerializer.endTag(null, com.android.server.job.JobStore.XML_TAG_ONEOFF);
            }
        }

        private void writeDebugInfoToXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            android.util.ArraySet debugTagsArraySet = jobStatus.getJob().getDebugTagsArraySet();
            int size = debugTagsArraySet.size();
            java.lang.String traceTag = jobStatus.getJob().getTraceTag();
            if (traceTag == null && size == 0) {
                return;
            }
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.job.JobStore.XML_TAG_DEBUG_INFO);
            if (traceTag != null) {
                typedXmlSerializer.attribute((java.lang.String) null, "trace-tag", traceTag);
            }
            for (int i = 0; i < size; i++) {
                typedXmlSerializer.startTag((java.lang.String) null, com.android.server.job.JobStore.XML_TAG_DEBUG_TAG);
                typedXmlSerializer.attribute((java.lang.String) null, "tag", (java.lang.String) debugTagsArraySet.valueAt(i));
                typedXmlSerializer.endTag((java.lang.String) null, com.android.server.job.JobStore.XML_TAG_DEBUG_TAG);
            }
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.job.JobStore.XML_TAG_DEBUG_INFO);
        }

        private void writeJobWorkItemsToXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            writeJobWorkItemListToXml(typedXmlSerializer, jobStatus.executingWork);
            writeJobWorkItemListToXml(typedXmlSerializer, jobStatus.pendingWork);
        }

        private void writeJobWorkItemListToXml(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.Nullable java.util.List<android.app.job.JobWorkItem> list) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            if (list == null) {
                return;
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                android.app.job.JobWorkItem jobWorkItem = list.get(i);
                if (jobWorkItem.getGrants() == null) {
                    if (jobWorkItem.getIntent() != null) {
                        android.util.Slog.wtf(com.android.server.job.JobStore.TAG, "Encountered JobWorkItem with Intent in persisting list");
                    } else {
                        typedXmlSerializer.startTag((java.lang.String) null, com.android.server.job.JobStore.XML_TAG_JOB_WORK_ITEM);
                        typedXmlSerializer.attributeInt((java.lang.String) null, "delivery-count", jobWorkItem.getDeliveryCount());
                        if (jobWorkItem.getEstimatedNetworkDownloadBytes() != -1) {
                            typedXmlSerializer.attributeLong((java.lang.String) null, "estimated-download-bytes", jobWorkItem.getEstimatedNetworkDownloadBytes());
                        }
                        if (jobWorkItem.getEstimatedNetworkUploadBytes() != -1) {
                            typedXmlSerializer.attributeLong((java.lang.String) null, "estimated-upload-bytes", jobWorkItem.getEstimatedNetworkUploadBytes());
                        }
                        if (jobWorkItem.getMinimumNetworkChunkBytes() != -1) {
                            typedXmlSerializer.attributeLong((java.lang.String) null, "minimum-network-chunk-bytes", jobWorkItem.getMinimumNetworkChunkBytes());
                        }
                        writeBundleToXml(jobWorkItem.getExtras(), typedXmlSerializer);
                        typedXmlSerializer.endTag((java.lang.String) null, com.android.server.job.JobStore.XML_TAG_JOB_WORK_ITEM);
                    }
                }
            }
        }
    };
    final java.lang.Object mWriteScheduleLock = new java.lang.Object();

    static com.android.server.job.JobStore get(com.android.server.job.JobSchedulerService jobSchedulerService) {
        com.android.server.job.JobStore jobStore;
        synchronized (sSingletonLock) {
            try {
                if (sSingleton == null) {
                    sSingleton = new com.android.server.job.JobStore(jobSchedulerService.getContext(), jobSchedulerService.getLock(), android.os.Environment.getDataDirectory());
                }
                jobStore = sSingleton;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return jobStore;
    }

    @com.android.internal.annotations.VisibleForTesting
    public static com.android.server.job.JobStore initAndGetForTesting(android.content.Context context, java.io.File file) {
        com.android.server.job.JobStore jobStore = new com.android.server.job.JobStore(context, new java.lang.Object(), file);
        jobStore.init();
        jobStore.clearForTesting();
        return jobStore;
    }

    private JobStore(android.content.Context context, java.lang.Object obj, java.io.File file) {
        this.mLock = obj;
        this.mContext = context;
        this.mJobFileDirectory = new java.io.File(new java.io.File(file, "system"), "job");
        this.mJobFileDirectory.mkdirs();
        this.mEventLogger = new android.util.SystemConfigFileCommitEventLogger("jobs");
        this.mJobsFile = createJobFile(new java.io.File(this.mJobFileDirectory, "jobs.xml"));
        this.mJobSet = new com.android.server.job.JobStore.JobSet();
        this.mXmlTimestamp = this.mJobsFile.exists() ? this.mJobsFile.getLastModifiedTime() : this.mJobFileDirectory.lastModified();
        this.mRtcGood = com.android.server.job.JobSchedulerService.sSystemClock.millis() > this.mXmlTimestamp;
        com.android.server.AppSchedulingModuleThread.getHandler().postDelayed(this.mScheduledJobHighWaterMarkLoggingRunnable, 1800000L);
    }

    private void init() {
        readJobMapFromDisk(this.mJobSet, this.mRtcGood);
    }

    void initAsync(java.util.concurrent.CountDownLatch countDownLatch) {
        this.mIoHandler.post(new com.android.server.job.JobStore.ReadJobMapFromDiskRunnable(this.mJobSet, this.mRtcGood, countDownLatch));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.util.AtomicFile createJobFile(java.lang.String str) {
        return createJobFile(new java.io.File(this.mJobFileDirectory, str + ".xml"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.util.AtomicFile createJobFile(java.io.File file) {
        return new android.util.AtomicFile(file, this.mEventLogger);
    }

    public boolean jobTimesInflatedValid() {
        return this.mRtcGood;
    }

    public boolean clockNowValidToInflate(long j) {
        return j >= this.mXmlTimestamp;
    }

    void runWorkAsync(@android.annotation.NonNull java.lang.Runnable runnable) {
        this.mIoHandler.post(runnable);
    }

    public void getRtcCorrectedJobsLocked(final java.util.ArrayList<com.android.server.job.controllers.JobStatus> arrayList, final java.util.ArrayList<com.android.server.job.controllers.JobStatus> arrayList2) {
        final long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        forEachJob(new java.util.function.Consumer() { // from class: com.android.server.job.JobStore$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.job.JobStore.lambda$getRtcCorrectedJobsLocked$0(millis, arrayList, arrayList2, (com.android.server.job.controllers.JobStatus) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getRtcCorrectedJobsLocked$0(long j, java.util.ArrayList arrayList, java.util.ArrayList arrayList2, com.android.server.job.controllers.JobStatus jobStatus) {
        android.util.Pair<java.lang.Long, java.lang.Long> persistedUtcTimes = jobStatus.getPersistedUtcTimes();
        if (persistedUtcTimes != null) {
            android.util.Pair<java.lang.Long, java.lang.Long> convertRtcBoundsToElapsed = convertRtcBoundsToElapsed(persistedUtcTimes, j);
            com.android.server.job.controllers.JobStatus jobStatus2 = new com.android.server.job.controllers.JobStatus(jobStatus, ((java.lang.Long) convertRtcBoundsToElapsed.first).longValue(), ((java.lang.Long) convertRtcBoundsToElapsed.second).longValue(), 0, 0, jobStatus.getLastSuccessfulRunTime(), jobStatus.getLastFailedRunTime(), jobStatus.getCumulativeExecutionTimeMs());
            jobStatus2.prepareLocked();
            arrayList.add(jobStatus2);
            arrayList2.add(jobStatus);
        }
    }

    public void add(com.android.server.job.controllers.JobStatus jobStatus) {
        if (this.mJobSet.add(jobStatus)) {
            this.mCurrentJobSetSize++;
            maybeUpdateHighWaterMark();
        }
        if (jobStatus.isPersisted()) {
            this.mPendingJobWriteUids.put(jobStatus.getUid(), true);
            maybeWriteStatusToDiskAsync();
        }
        if (DEBUG) {
            android.util.Slog.d(TAG, "Added job status to store: " + jobStatus);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void addForTesting(com.android.server.job.controllers.JobStatus jobStatus) {
        if (this.mJobSet.add(jobStatus)) {
            this.mCurrentJobSetSize++;
            maybeUpdateHighWaterMark();
        }
        if (jobStatus.isPersisted()) {
            this.mPendingJobWriteUids.put(jobStatus.getUid(), true);
        }
    }

    boolean containsJob(com.android.server.job.controllers.JobStatus jobStatus) {
        return this.mJobSet.contains(jobStatus);
    }

    public int size() {
        return this.mJobSet.size();
    }

    public com.android.server.job.JobSchedulerInternal.JobStorePersistStats getPersistStats() {
        return this.mPersistInfo;
    }

    public int countJobsForUid(int i) {
        return this.mJobSet.countJobsForUid(i);
    }

    public boolean remove(com.android.server.job.controllers.JobStatus jobStatus, boolean z) {
        boolean remove = this.mJobSet.remove(jobStatus);
        if (!remove) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "Couldn't remove job: didn't exist: " + jobStatus);
                return false;
            }
            return false;
        }
        this.mCurrentJobSetSize--;
        if (z && jobStatus.isPersisted()) {
            this.mPendingJobWriteUids.put(jobStatus.getUid(), true);
            maybeWriteStatusToDiskAsync();
        }
        return remove;
    }

    @com.android.internal.annotations.VisibleForTesting
    public void removeForTesting(com.android.server.job.controllers.JobStatus jobStatus) {
        if (this.mJobSet.remove(jobStatus)) {
            this.mCurrentJobSetSize--;
        }
        if (jobStatus.isPersisted()) {
            this.mPendingJobWriteUids.put(jobStatus.getUid(), true);
        }
    }

    public void removeJobsOfUnlistedUsers(int[] iArr) {
        this.mJobSet.removeJobsOfUnlistedUsers(iArr);
        this.mCurrentJobSetSize = this.mJobSet.size();
    }

    void touchJob(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        if (!jobStatus.isPersisted()) {
            return;
        }
        this.mPendingJobWriteUids.put(jobStatus.getUid(), true);
        maybeWriteStatusToDiskAsync();
    }

    @com.android.internal.annotations.VisibleForTesting
    public void clear() {
        this.mJobSet.clear();
        this.mPendingJobWriteUids.put(-1, true);
        this.mCurrentJobSetSize = 0;
        maybeWriteStatusToDiskAsync();
    }

    @com.android.internal.annotations.VisibleForTesting
    public void clearForTesting() {
        this.mJobSet.clear();
        this.mPendingJobWriteUids.put(-1, true);
        this.mCurrentJobSetSize = 0;
    }

    void setUseSplitFiles(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mUseSplitFiles != z) {
                    this.mUseSplitFiles = z;
                    migrateJobFilesAsync();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void setUseSplitFilesForTesting(boolean z) {
        boolean z2;
        synchronized (this.mLock) {
            try {
                z2 = this.mUseSplitFiles != z;
                if (z2) {
                    this.mUseSplitFiles = z;
                    this.mPendingJobWriteUids.put(-1, true);
                }
            } finally {
            }
        }
        if (z2) {
            synchronized (this.mWriteScheduleLock) {
                this.mSplitFileMigrationNeeded = true;
            }
        }
    }

    @android.annotation.NonNull
    public android.util.ArraySet<com.android.server.job.controllers.JobStatus> getJobsBySourceUid(int i) {
        return this.mJobSet.getJobsBySourceUid(i);
    }

    public void getJobsBySourceUid(int i, @android.annotation.NonNull java.util.Set<com.android.server.job.controllers.JobStatus> set) {
        this.mJobSet.getJobsBySourceUid(i, set);
    }

    @android.annotation.NonNull
    public android.util.ArraySet<com.android.server.job.controllers.JobStatus> getJobsByUid(int i) {
        return this.mJobSet.getJobsByUid(i);
    }

    public void getJobsByUid(int i, @android.annotation.NonNull java.util.Set<com.android.server.job.controllers.JobStatus> set) {
        this.mJobSet.getJobsByUid(i, set);
    }

    @android.annotation.Nullable
    public com.android.server.job.controllers.JobStatus getJobByUidAndJobId(int i, @android.annotation.Nullable java.lang.String str, int i2) {
        return this.mJobSet.get(i, str, i2);
    }

    public void forEachJob(java.util.function.Consumer<com.android.server.job.controllers.JobStatus> consumer) {
        this.mJobSet.forEachJob((java.util.function.Predicate<com.android.server.job.controllers.JobStatus>) null, consumer);
    }

    public void forEachJob(@android.annotation.Nullable java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate, java.util.function.Consumer<com.android.server.job.controllers.JobStatus> consumer) {
        this.mJobSet.forEachJob(predicate, consumer);
    }

    public void forEachJob(int i, java.util.function.Consumer<com.android.server.job.controllers.JobStatus> consumer) {
        this.mJobSet.forEachJob(i, consumer);
    }

    public void forEachJobForSourceUid(int i, java.util.function.Consumer<com.android.server.job.controllers.JobStatus> consumer) {
        this.mJobSet.forEachJobForSourceUid(i, consumer);
    }

    private void maybeUpdateHighWaterMark() {
        if (this.mScheduledJob30MinHighWaterMark < this.mCurrentJobSetSize) {
            this.mScheduledJob30MinHighWaterMark = this.mCurrentJobSetSize;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void migrateJobFilesAsync() {
        synchronized (this.mLock) {
            this.mPendingJobWriteUids.put(-1, true);
        }
        synchronized (this.mWriteScheduleLock) {
            this.mSplitFileMigrationNeeded = true;
            maybeWriteStatusToDiskAsync();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeWriteStatusToDiskAsync() {
        synchronized (this.mWriteScheduleLock) {
            try {
                if (!this.mWriteScheduled) {
                    if (DEBUG) {
                        android.util.Slog.v(TAG, "Scheduling persist of jobs to disk.");
                    }
                    this.mIoHandler.postDelayed(this.mWriteRunnable, JOB_PERSIST_DELAY);
                    this.mWriteScheduled = true;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void readJobMapFromDisk(com.android.server.job.JobStore.JobSet jobSet, boolean z) {
        new com.android.server.job.JobStore.ReadJobMapFromDiskRunnable(this, jobSet, z).run();
    }

    @com.android.internal.annotations.VisibleForTesting
    public void writeStatusToDiskForTesting() {
        synchronized (this.mWriteScheduleLock) {
            try {
                if (this.mWriteScheduled) {
                    throw new java.lang.IllegalStateException("An asynchronous write is already scheduled.");
                }
                this.mWriteScheduled = true;
                this.mWriteRunnable.run();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean waitForWriteToCompleteForTesting(long j) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        long j2 = uptimeMillis + j;
        synchronized (this.mWriteScheduleLock) {
            while (true) {
                try {
                    if (!this.mWriteScheduled && !this.mWriteInProgress) {
                        break;
                    }
                    long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
                    if (uptimeMillis2 >= j2) {
                        return false;
                    }
                    try {
                        this.mWriteScheduleLock.wait((uptimeMillis2 - uptimeMillis) + j);
                    } catch (java.lang.InterruptedException e) {
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return true;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static java.lang.String intArrayToString(int[] iArr) {
        java.util.StringJoiner stringJoiner = new java.util.StringJoiner(",");
        for (int i : iArr) {
            stringJoiner.add(java.lang.String.valueOf(i));
        }
        return stringJoiner.toString();
    }

    @com.android.internal.annotations.VisibleForTesting
    static int[] stringToIntArray(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return new int[0];
        }
        java.lang.String[] split = str.split(",");
        int[] iArr = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            iArr[i] = java.lang.Integer.parseInt(split[i]);
        }
        return iArr;
    }

    @com.android.internal.annotations.VisibleForTesting
    static int extractUidFromJobFileName(@android.annotation.NonNull java.io.File file) {
        java.lang.String name = file.getName();
        if (name.startsWith(JOB_FILE_SPLIT_PREFIX)) {
            try {
                int parseInt = java.lang.Integer.parseInt(name.substring(JOB_FILE_SPLIT_PREFIX.length(), name.length() - 4));
                if (parseInt < 0) {
                    return -2;
                }
                return parseInt;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Unexpected file name format", e);
            }
        }
        return -2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.util.Pair<java.lang.Long, java.lang.Long> convertRtcBoundsToElapsed(android.util.Pair<java.lang.Long, java.lang.Long> pair, long j) {
        long j2;
        long millis = com.android.server.job.JobSchedulerService.sSystemClock.millis();
        if (((java.lang.Long) pair.first).longValue() > 0) {
            j2 = java.lang.Math.max(((java.lang.Long) pair.first).longValue() - millis, 0L) + j;
        } else {
            j2 = 0;
        }
        long longValue = ((java.lang.Long) pair.second).longValue();
        long j3 = com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME;
        if (longValue < com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
            j3 = j + java.lang.Math.max(((java.lang.Long) pair.second).longValue() - millis, 0L);
        }
        return android.util.Pair.create(java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSyncJob(com.android.server.job.controllers.JobStatus jobStatus) {
        return com.android.server.content.SyncJobService.class.getName().equals(jobStatus.getServiceComponent().getClassName());
    }

    private final class ReadJobMapFromDiskRunnable implements java.lang.Runnable {
        private final com.android.server.job.JobStore.JobSet jobSet;
        private final java.util.concurrent.CountDownLatch mCompletionLatch;
        private final boolean rtcGood;

        ReadJobMapFromDiskRunnable(com.android.server.job.JobStore jobStore, com.android.server.job.JobStore.JobSet jobSet, boolean z) {
            this(jobSet, z, null);
        }

        ReadJobMapFromDiskRunnable(com.android.server.job.JobStore.JobSet jobSet, boolean z, @android.annotation.Nullable java.util.concurrent.CountDownLatch countDownLatch) {
            this.jobSet = jobSet;
            this.rtcGood = z;
            this.mCompletionLatch = countDownLatch;
        }

        /* JADX WARN: Removed duplicated region for block: B:66:0x0187 A[Catch: all -> 0x0075, TryCatch #8 {all -> 0x0075, blocks: (B:14:0x0044, B:16:0x004d, B:18:0x005b, B:22:0x01a5, B:23:0x0078, B:25:0x007e, B:60:0x0121, B:87:0x011e, B:98:0x0106, B:64:0x017e, B:66:0x0187, B:72:0x0195, B:79:0x012c, B:63:0x013d, B:77:0x0162, B:114:0x01ad, B:116:0x01b9, B:117:0x01d1), top: B:13:0x0044 }] */
        /* JADX WARN: Removed duplicated region for block: B:72:0x0195 A[Catch: all -> 0x0075, TryCatch #8 {all -> 0x0075, blocks: (B:14:0x0044, B:16:0x004d, B:18:0x005b, B:22:0x01a5, B:23:0x0078, B:25:0x007e, B:60:0x0121, B:87:0x011e, B:98:0x0106, B:64:0x017e, B:66:0x0187, B:72:0x0195, B:79:0x012c, B:63:0x013d, B:77:0x0162, B:114:0x01ad, B:116:0x01b9, B:117:0x01d1), top: B:13:0x0044 }] */
        /* JADX WARN: Removed duplicated region for block: B:81:0x0118 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            int i;
            int i2;
            boolean z;
            java.io.File[] fileArr;
            int i3;
            int i4;
            boolean z2;
            java.io.FileInputStream openRead;
            if (!com.android.server.job.JobStore.this.mJobFileDirectory.isDirectory()) {
                android.util.Slog.wtf(com.android.server.job.JobStore.TAG, "jobs directory isn't a directory O.O");
                com.android.server.job.JobStore.this.mJobFileDirectory.mkdirs();
                return;
            }
            try {
                java.io.File[] listFiles = com.android.server.job.JobStore.this.mJobFileDirectory.listFiles();
                if (listFiles == null) {
                    android.util.Slog.wtfStack(com.android.server.job.JobStore.TAG, "Couldn't get job file list");
                    return;
                }
                long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                synchronized (com.android.server.job.JobStore.this.mLock) {
                    try {
                        int length = listFiles.length;
                        int i5 = 0;
                        i = 0;
                        int i6 = 0;
                        int i7 = 0;
                        boolean z3 = false;
                        i2 = 0;
                        while (i5 < length) {
                            java.io.File file = listFiles[i5];
                            if (file.getName().equals("jobs.xml") || com.android.server.job.JobStore.SPLIT_FILE_PATTERN.matcher(file.getName()).matches()) {
                                try {
                                    openRead = com.android.server.job.JobStore.this.createJobFile(file).openRead();
                                } catch (java.io.FileNotFoundException e) {
                                    fileArr = listFiles;
                                    i3 = length;
                                    i4 = i5;
                                    z2 = z3;
                                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
                                    e = e2;
                                    fileArr = listFiles;
                                    i3 = length;
                                    i4 = i5;
                                    z2 = z3;
                                } catch (java.lang.Exception e3) {
                                    e = e3;
                                    fileArr = listFiles;
                                    i3 = length;
                                    i4 = i5;
                                    z2 = z3;
                                }
                                try {
                                    java.util.List<com.android.server.job.controllers.JobStatus> readJobMapImpl = readJobMapImpl(openRead, this.rtcGood, millis);
                                    if (readJobMapImpl != null) {
                                        int i8 = 0;
                                        while (true) {
                                            fileArr = listFiles;
                                            try {
                                                if (i8 >= readJobMapImpl.size()) {
                                                    break;
                                                }
                                                com.android.server.job.controllers.JobStatus jobStatus = readJobMapImpl.get(i8);
                                                java.util.List<com.android.server.job.controllers.JobStatus> list = readJobMapImpl;
                                                i3 = length;
                                                try {
                                                    z2 = z3;
                                                    try {
                                                        i4 = i5;
                                                        try {
                                                            if (this.jobSet.get(jobStatus.getUid(), jobStatus.getNamespace(), jobStatus.getJobId()) != null) {
                                                                i2++;
                                                            } else {
                                                                jobStatus.prepareLocked();
                                                                jobStatus.enqueueTime = millis;
                                                                this.jobSet.add(jobStatus);
                                                                i++;
                                                                if (jobStatus.getUid() == 1000) {
                                                                    i6++;
                                                                    if (com.android.server.job.JobStore.isSyncJob(jobStatus)) {
                                                                        i7++;
                                                                    }
                                                                }
                                                            }
                                                            i8++;
                                                            listFiles = fileArr;
                                                            readJobMapImpl = list;
                                                            length = i3;
                                                            z3 = z2;
                                                            i5 = i4;
                                                        } catch (java.lang.Throwable th) {
                                                            th = th;
                                                            java.lang.Throwable th2 = th;
                                                            if (openRead != null) {
                                                            }
                                                            throw th2;
                                                        }
                                                    } catch (java.lang.Throwable th3) {
                                                        th = th3;
                                                        i4 = i5;
                                                    }
                                                } catch (java.lang.Throwable th4) {
                                                    th = th4;
                                                    i4 = i5;
                                                    z2 = z3;
                                                    java.lang.Throwable th22 = th;
                                                    if (openRead != null) {
                                                        try {
                                                            openRead.close();
                                                        } catch (java.lang.Throwable th5) {
                                                            try {
                                                                th22.addSuppressed(th5);
                                                            } catch (java.io.FileNotFoundException e4) {
                                                                android.util.Slog.e(com.android.server.job.JobStore.TAG, "Could not find jobs file: " + file.getName());
                                                                if (com.android.server.job.JobStore.this.mUseSplitFiles) {
                                                                }
                                                                i5 = i4 + 1;
                                                                listFiles = fileArr;
                                                                length = i3;
                                                            } catch (java.lang.Exception e5) {
                                                                e = e5;
                                                                android.util.Slog.wtf(com.android.server.job.JobStore.TAG, "Unexpected exception", e);
                                                                if (com.android.server.job.JobStore.this.mUseSplitFiles) {
                                                                }
                                                                i5 = i4 + 1;
                                                                listFiles = fileArr;
                                                                length = i3;
                                                            }
                                                        }
                                                    }
                                                    throw th22;
                                                }
                                            } catch (java.lang.Throwable th6) {
                                                th = th6;
                                                i3 = length;
                                                i4 = i5;
                                                z2 = z3;
                                                java.lang.Throwable th222 = th;
                                                if (openRead != null) {
                                                }
                                                throw th222;
                                            }
                                        }
                                        i3 = length;
                                        i4 = i5;
                                        z2 = z3;
                                    } else {
                                        fileArr = listFiles;
                                        i3 = length;
                                        i4 = i5;
                                        z2 = z3;
                                    }
                                    if (openRead != null) {
                                        try {
                                            openRead.close();
                                        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e6) {
                                            e = e6;
                                            android.util.Slog.wtf(com.android.server.job.JobStore.TAG, "Error in " + file.getName(), e);
                                            if (com.android.server.job.JobStore.this.mUseSplitFiles) {
                                            }
                                            i5 = i4 + 1;
                                            listFiles = fileArr;
                                            length = i3;
                                        }
                                    }
                                    if (com.android.server.job.JobStore.this.mUseSplitFiles) {
                                        if (file.getName().startsWith(com.android.server.job.JobStore.JOB_FILE_SPLIT_PREFIX)) {
                                            z3 = true;
                                        }
                                        z3 = z2;
                                    } else {
                                        if (!file.getName().startsWith(com.android.server.job.JobStore.JOB_FILE_SPLIT_PREFIX)) {
                                            z3 = true;
                                        }
                                        z3 = z2;
                                    }
                                } catch (java.lang.Throwable th7) {
                                    th = th7;
                                    fileArr = listFiles;
                                }
                            } else {
                                fileArr = listFiles;
                                i3 = length;
                                i4 = i5;
                            }
                            i5 = i4 + 1;
                            listFiles = fileArr;
                            length = i3;
                        }
                        z = z3;
                        if (com.android.server.job.JobStore.this.mPersistInfo.countAllJobsLoaded < 0) {
                            com.android.server.job.JobStore.this.mPersistInfo.countAllJobsLoaded = i;
                            com.android.server.job.JobStore.this.mPersistInfo.countSystemServerJobsLoaded = i6;
                            com.android.server.job.JobStore.this.mPersistInfo.countSystemSyncManagerJobsLoaded = i7;
                        }
                    } finally {
                    }
                }
                android.util.Slog.i(com.android.server.job.JobStore.TAG, "Read " + i + " jobs");
                if (z) {
                    com.android.server.job.JobStore.this.migrateJobFilesAsync();
                }
                if (i2 > 0) {
                    android.util.Slog.wtf(com.android.server.job.JobStore.TAG, "Encountered " + i2 + " duplicate persisted jobs");
                }
                com.android.server.job.JobStore.this.mCurrentJobSetSize = i;
                com.android.server.job.JobStore.this.mScheduledJob30MinHighWaterMark = com.android.server.job.JobStore.this.mCurrentJobSetSize;
                com.android.server.job.JobStore.this.mScheduledJobHighWaterMarkLoggingRunnable.run();
                if (this.mCompletionLatch != null) {
                    this.mCompletionLatch.countDown();
                }
            } catch (java.lang.SecurityException e7) {
                android.util.Slog.wtf(com.android.server.job.JobStore.TAG, "Not allowed to read job file directory", e7);
            }
        }

        @android.annotation.Nullable
        private static java.lang.String intern(@android.annotation.Nullable java.lang.String str) {
            if (str == null) {
                return null;
            }
            return str.intern();
        }

        private java.util.List<com.android.server.job.controllers.JobStatus> readJobMapImpl(java.io.InputStream inputStream, boolean z, long j) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
            int eventType = resolvePullParser.getEventType();
            while (eventType != 2 && eventType != 1) {
                eventType = resolvePullParser.next();
                android.util.Slog.d(com.android.server.job.JobStore.TAG, "Start tag: " + resolvePullParser.getName());
            }
            if (eventType == 1) {
                if (com.android.server.job.JobStore.DEBUG) {
                    android.util.Slog.d(com.android.server.job.JobStore.TAG, "No persisted jobs.");
                }
                return null;
            }
            if (!com.android.server.job.JobStore.XML_TAG_JOB_INFO.equals(resolvePullParser.getName())) {
                return null;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int attributeInt = resolvePullParser.getAttributeInt((java.lang.String) null, "version");
            if (attributeInt > 1 || attributeInt < 0) {
                android.util.Slog.d(com.android.server.job.JobStore.TAG, "Invalid version number, aborting jobs file read.");
                return null;
            }
            int next = resolvePullParser.next();
            do {
                if (next == 2 && "job".equals(resolvePullParser.getName())) {
                    com.android.server.job.controllers.JobStatus restoreJobFromXml = restoreJobFromXml(z, resolvePullParser, attributeInt, j);
                    if (restoreJobFromXml != null) {
                        if (com.android.server.job.JobStore.DEBUG) {
                            android.util.Slog.d(com.android.server.job.JobStore.TAG, "Read out " + restoreJobFromXml);
                        }
                        arrayList.add(restoreJobFromXml);
                    } else {
                        android.util.Slog.d(com.android.server.job.JobStore.TAG, "Error reading job from file.");
                    }
                }
                next = resolvePullParser.next();
            } while (next != 1);
            return arrayList;
        }

        /* JADX WARN: Type inference failed for: r6v0 */
        /* JADX WARN: Type inference failed for: r6v1, types: [com.android.server.job.controllers.JobStatus, java.lang.String] */
        /* JADX WARN: Type inference failed for: r6v24 */
        private com.android.server.job.controllers.JobStatus restoreJobFromXml(boolean z, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, long j) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            int i2;
            int next;
            int i3;
            int next2;
            int i4;
            long j2;
            android.util.Pair<java.lang.Long, java.lang.Long> pair;
            int i5;
            android.util.Pair pair2;
            int next3;
            java.util.List<android.app.job.JobWorkItem> list;
            int i6;
            ?? r6 = 0;
            try {
                android.app.job.JobInfo.Builder buildBuilderFromXml = buildBuilderFromXml(typedXmlPullParser);
                buildBuilderFromXml.setPersisted(true);
                int parseInt = java.lang.Integer.parseInt(typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID));
                if (i == 0) {
                    java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "priority");
                    if (attributeValue != null) {
                        buildBuilderFromXml.setBias(java.lang.Integer.parseInt(attributeValue));
                    }
                } else if (i >= 1) {
                    java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "bias");
                    if (attributeValue2 != null) {
                        buildBuilderFromXml.setBias(java.lang.Integer.parseInt(attributeValue2));
                    }
                    java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "priority");
                    if (attributeValue3 != null) {
                        buildBuilderFromXml.setPriority(java.lang.Integer.parseInt(attributeValue3));
                    }
                }
                java.lang.String attributeValue4 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "flags");
                if (attributeValue4 != null) {
                    buildBuilderFromXml.setFlags(java.lang.Integer.parseInt(attributeValue4));
                }
                java.lang.String attributeValue5 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "internalFlags");
                if (attributeValue5 == null) {
                    i2 = 0;
                } else {
                    i2 = java.lang.Integer.parseInt(attributeValue5);
                }
                java.lang.String attributeValue6 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "sourceUserId");
                int parseInt2 = attributeValue6 == null ? -1 : java.lang.Integer.parseInt(attributeValue6);
                java.lang.String attributeValue7 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "lastSuccessfulRunTime");
                long parseLong = attributeValue7 == null ? 0L : java.lang.Long.parseLong(attributeValue7);
                java.lang.String attributeValue8 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "lastFailedRunTime");
                long parseLong2 = attributeValue8 == null ? 0L : java.lang.Long.parseLong(attributeValue8);
                long attributeLong = typedXmlPullParser.getAttributeLong((java.lang.String) null, "cumulativeExecutionTime", 0L);
                java.lang.String attributeValue9 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "sourcePackageName");
                java.lang.String intern = intern(typedXmlPullParser.getAttributeValue((java.lang.String) null, "namespace"));
                java.lang.String intern2 = intern(typedXmlPullParser.getAttributeValue((java.lang.String) null, "sourceTag"));
                while (true) {
                    next = typedXmlPullParser.next();
                    i3 = 4;
                    if (next != 4) {
                        break;
                    }
                    r6 = 0;
                }
                int i7 = 2;
                if (next != 2 || !com.android.server.job.JobStore.XML_TAG_PARAMS_CONSTRAINTS.equals(typedXmlPullParser.getName())) {
                    return r6;
                }
                try {
                    buildConstraintsFromXml(buildBuilderFromXml, typedXmlPullParser);
                    typedXmlPullParser.next();
                    while (true) {
                        next2 = typedXmlPullParser.next();
                        if (next2 != i3) {
                            break;
                        }
                        i7 = i7;
                        i3 = i3;
                    }
                    if (next2 != i7) {
                        return r6;
                    }
                    android.util.Pair<java.lang.Long, java.lang.Long> buildRtcExecutionTimesFromXml = buildRtcExecutionTimesFromXml(typedXmlPullParser);
                    android.util.Pair convertRtcBoundsToElapsed = com.android.server.job.JobStore.convertRtcBoundsToElapsed(buildRtcExecutionTimesFromXml, j);
                    if (com.android.server.job.JobStore.XML_TAG_PERIODIC.equals(typedXmlPullParser.getName())) {
                        try {
                            long parseLong3 = java.lang.Long.parseLong(typedXmlPullParser.getAttributeValue((java.lang.String) r6, "period"));
                            java.lang.String attributeValue10 = typedXmlPullParser.getAttributeValue((java.lang.String) r6, "flex");
                            if (attributeValue10 != null) {
                                i4 = parseInt2;
                                j2 = java.lang.Long.valueOf(attributeValue10).longValue();
                            } else {
                                i4 = parseInt2;
                                j2 = parseLong3;
                            }
                            buildBuilderFromXml.setPeriodic(parseLong3, j2);
                            if (((java.lang.Long) convertRtcBoundsToElapsed.second).longValue() <= j + parseLong3 + j2) {
                                pair = buildRtcExecutionTimesFromXml;
                                i5 = i4;
                            } else {
                                long j3 = j + j2 + parseLong3;
                                long j4 = j3 - j2;
                                pair = buildRtcExecutionTimesFromXml;
                                i5 = i4;
                                android.util.Slog.w(com.android.server.job.JobStore.TAG, java.lang.String.format("Periodic job for uid='%d' persisted run-time is too big [%s, %s]. Clamping to [%s,%s]", java.lang.Integer.valueOf(parseInt), android.text.format.DateUtils.formatElapsedTime(((java.lang.Long) convertRtcBoundsToElapsed.first).longValue() / 1000), android.text.format.DateUtils.formatElapsedTime(((java.lang.Long) convertRtcBoundsToElapsed.second).longValue() / 1000), android.text.format.DateUtils.formatElapsedTime(j4 / 1000), android.text.format.DateUtils.formatElapsedTime(j3 / 1000)));
                                convertRtcBoundsToElapsed = android.util.Pair.create(java.lang.Long.valueOf(j4), java.lang.Long.valueOf(j3));
                            }
                            pair2 = convertRtcBoundsToElapsed;
                        } catch (java.lang.NumberFormatException e) {
                            android.util.Slog.d(com.android.server.job.JobStore.TAG, "Error reading periodic execution criteria, skipping.");
                            return null;
                        }
                    } else {
                        pair = buildRtcExecutionTimesFromXml;
                        i5 = parseInt2;
                        if (com.android.server.job.JobStore.XML_TAG_ONEOFF.equals(typedXmlPullParser.getName())) {
                            try {
                                if (((java.lang.Long) convertRtcBoundsToElapsed.first).longValue() != 0) {
                                    buildBuilderFromXml.setMinimumLatency(((java.lang.Long) convertRtcBoundsToElapsed.first).longValue() - j);
                                }
                                if (((java.lang.Long) convertRtcBoundsToElapsed.second).longValue() != com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME) {
                                    buildBuilderFromXml.setOverrideDeadline(((java.lang.Long) convertRtcBoundsToElapsed.second).longValue() - j);
                                }
                                pair2 = convertRtcBoundsToElapsed;
                            } catch (java.lang.NumberFormatException e2) {
                                android.util.Slog.d(com.android.server.job.JobStore.TAG, "Error reading job execution criteria, skipping.");
                                return null;
                            }
                        } else {
                            if (com.android.server.job.JobStore.DEBUG) {
                                android.util.Slog.d(com.android.server.job.JobStore.TAG, "Invalid parameter tag, skipping - " + typedXmlPullParser.getName());
                                return null;
                            }
                            return null;
                        }
                    }
                    maybeBuildBackoffPolicyFromXml(buildBuilderFromXml, typedXmlPullParser);
                    typedXmlPullParser.nextTag();
                    do {
                        next3 = typedXmlPullParser.next();
                    } while (next3 == 4);
                    if (next3 != 2 || !com.android.server.job.JobStore.XML_TAG_EXTRAS.equals(typedXmlPullParser.getName())) {
                        if (com.android.server.job.JobStore.DEBUG) {
                            android.util.Slog.d(com.android.server.job.JobStore.TAG, "Error reading extras, skipping.");
                            return null;
                        }
                        return null;
                    }
                    try {
                        android.os.PersistableBundle restoreFromXml = android.os.PersistableBundle.restoreFromXml(typedXmlPullParser);
                        buildBuilderFromXml.setExtras(restoreFromXml);
                        int nextTag = typedXmlPullParser.nextTag();
                        if (nextTag == 2 && com.android.server.job.JobStore.XML_TAG_JOB_WORK_ITEM.equals(typedXmlPullParser.getName())) {
                            list = readJobWorkItemsFromXml(typedXmlPullParser);
                        } else {
                            list = null;
                        }
                        if (nextTag == 2 && com.android.server.job.JobStore.XML_TAG_DEBUG_INFO.equals(typedXmlPullParser.getName())) {
                            try {
                                buildBuilderFromXml.setTraceTag(typedXmlPullParser.getAttributeValue((java.lang.String) null, "trace-tag"));
                            } catch (java.lang.Exception e3) {
                                android.util.Slog.wtf(com.android.server.job.JobStore.TAG, "Invalid trace tag persisted to disk", e3);
                            }
                            typedXmlPullParser.next();
                            buildBuilderFromXml.addDebugTags(readDebugTagsFromXml(typedXmlPullParser));
                            typedXmlPullParser.nextTag();
                        }
                        try {
                            android.app.job.JobInfo build = buildBuilderFromXml.build(false, false, false, false);
                            if (!com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(attributeValue9) || restoreFromXml == null) {
                                i6 = 0;
                            } else {
                                i6 = 0;
                                if (restoreFromXml.getBoolean("SyncManagerJob", false)) {
                                    attributeValue9 = restoreFromXml.getString("owningPackage", attributeValue9);
                                    if (com.android.server.job.JobStore.DEBUG) {
                                        android.util.Slog.i(com.android.server.job.JobStore.TAG, "Fixing up sync job source package name from 'android' to '" + attributeValue9 + "'");
                                    }
                                }
                            }
                            int i8 = i5;
                            com.android.server.job.controllers.JobStatus jobStatus = new com.android.server.job.controllers.JobStatus(build, parseInt, intern(attributeValue9), i8, com.android.server.job.JobSchedulerService.standbyBucketForPackage(attributeValue9, i8, j), intern, intern2, ((java.lang.Long) pair2.first).longValue(), ((java.lang.Long) pair2.second).longValue(), parseLong, parseLong2, attributeLong, z ? null : pair, i2, 0);
                            if (list != null) {
                                for (int i9 = i6; i9 < list.size(); i9++) {
                                    jobStatus.enqueueWorkLocked(list.get(i9));
                                }
                            }
                            return jobStatus;
                        } catch (java.lang.Exception e4) {
                            android.util.Slog.w(com.android.server.job.JobStore.TAG, "Unable to build job from XML, ignoring: " + buildBuilderFromXml.summarize(), e4);
                            return null;
                        }
                    } catch (java.lang.IllegalArgumentException e5) {
                        android.util.Slog.e(com.android.server.job.JobStore.TAG, "Persisted extras contained invalid data", e5);
                        return null;
                    }
                } catch (java.io.IOException e6) {
                    android.util.Slog.d(com.android.server.job.JobStore.TAG, "Error I/O Exception.", e6);
                    return r6;
                } catch (java.lang.NumberFormatException e7) {
                    android.util.Slog.d(com.android.server.job.JobStore.TAG, "Error reading constraints, skipping.");
                    return r6;
                } catch (java.lang.IllegalArgumentException e8) {
                    android.util.Slog.e(com.android.server.job.JobStore.TAG, "Constraints contained invalid data", e8);
                    return r6;
                } catch (org.xmlpull.v1.XmlPullParserException e9) {
                    android.util.Slog.d(com.android.server.job.JobStore.TAG, "Error Parser Exception.", e9);
                    return r6;
                }
            } catch (java.lang.NumberFormatException e10) {
                android.util.Slog.e(com.android.server.job.JobStore.TAG, "Error parsing job's required fields, skipping");
                return null;
            }
        }

        private android.app.job.JobInfo.Builder buildBuilderFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException {
            return new android.app.job.JobInfo.Builder(typedXmlPullParser.getAttributeInt((java.lang.String) null, "jobid"), new android.content.ComponentName(intern(typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.pm.Settings.ATTR_PACKAGE)), intern(typedXmlPullParser.getAttributeValue((java.lang.String) null, "class"))));
        }

        private void buildConstraintsFromXml(android.app.job.JobInfo.Builder builder, com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            java.lang.String attributeValue;
            java.lang.String attributeValue2;
            java.lang.String attributeValue3;
            java.lang.String attributeValue4 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "net-capabilities-csv");
            java.lang.String attributeValue5 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "net-forbidden-capabilities-csv");
            java.lang.String attributeValue6 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "net-transport-types-csv");
            if (attributeValue4 == null || attributeValue6 == null) {
                attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "net-capabilities");
                attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "net-unwanted-capabilities");
                attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, "net-transport-types");
            } else {
                attributeValue = null;
                attributeValue2 = null;
                attributeValue3 = null;
            }
            int i = 0;
            if (attributeValue4 != null && attributeValue6 != null) {
                android.net.NetworkRequest.Builder clearCapabilities = new android.net.NetworkRequest.Builder().clearCapabilities();
                for (int i2 : com.android.server.job.JobStore.stringToIntArray(attributeValue4)) {
                    clearCapabilities.addCapability(i2);
                }
                for (int i3 : com.android.server.job.JobStore.stringToIntArray(attributeValue5)) {
                    clearCapabilities.addForbiddenCapability(i3);
                }
                int[] stringToIntArray = com.android.server.job.JobStore.stringToIntArray(attributeValue6);
                int length = stringToIntArray.length;
                while (i < length) {
                    clearCapabilities.addTransportType(stringToIntArray[i]);
                    i++;
                }
                builder.setRequiredNetwork(clearCapabilities.build()).setEstimatedNetworkBytes(typedXmlPullParser.getAttributeLong((java.lang.String) null, "estimated-download-bytes", -1L), typedXmlPullParser.getAttributeLong((java.lang.String) null, "estimated-upload-bytes", -1L)).setMinimumNetworkChunkBytes(typedXmlPullParser.getAttributeLong((java.lang.String) null, "minimum-network-chunk-bytes", -1L));
            } else if (attributeValue != null && attributeValue3 != null) {
                android.net.NetworkRequest.Builder clearCapabilities2 = new android.net.NetworkRequest.Builder().clearCapabilities();
                int[] unpackBits = com.android.internal.util.jobs.BitUtils.unpackBits(java.lang.Long.parseLong(attributeValue));
                for (int i4 : unpackBits) {
                    if (i4 <= 25) {
                        clearCapabilities2.addCapability(i4);
                    }
                }
                for (int i5 : com.android.internal.util.jobs.BitUtils.unpackBits(java.lang.Long.parseLong(attributeValue2))) {
                    if (i5 <= 25) {
                        clearCapabilities2.addForbiddenCapability(i5);
                    }
                }
                int[] unpackBits2 = com.android.internal.util.jobs.BitUtils.unpackBits(java.lang.Long.parseLong(attributeValue3));
                int length2 = unpackBits2.length;
                while (i < length2) {
                    int i6 = unpackBits2[i];
                    if (i6 <= 7) {
                        clearCapabilities2.addTransportType(i6);
                    }
                    i++;
                }
                builder.setRequiredNetwork(clearCapabilities2.build());
            } else {
                if (typedXmlPullParser.getAttributeValue((java.lang.String) null, "connectivity") != null) {
                    builder.setRequiredNetworkType(1);
                }
                if (typedXmlPullParser.getAttributeValue((java.lang.String) null, "metered") != null) {
                    builder.setRequiredNetworkType(4);
                }
                if (typedXmlPullParser.getAttributeValue((java.lang.String) null, "unmetered") != null) {
                    builder.setRequiredNetworkType(2);
                }
                if (typedXmlPullParser.getAttributeValue((java.lang.String) null, "not-roaming") != null) {
                    builder.setRequiredNetworkType(3);
                }
            }
            if (typedXmlPullParser.getAttributeValue((java.lang.String) null, "idle") != null) {
                builder.setRequiresDeviceIdle(true);
            }
            if (typedXmlPullParser.getAttributeValue((java.lang.String) null, "charging") != null) {
                builder.setRequiresCharging(true);
            }
            if (typedXmlPullParser.getAttributeValue((java.lang.String) null, "battery-not-low") != null) {
                builder.setRequiresBatteryNotLow(true);
            }
            if (typedXmlPullParser.getAttributeValue((java.lang.String) null, "storage-not-low") != null) {
                builder.setRequiresStorageNotLow(true);
            }
        }

        private void maybeBuildBackoffPolicyFromXml(android.app.job.JobInfo.Builder builder, org.xmlpull.v1.XmlPullParser xmlPullParser) {
            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "initial-backoff");
            if (attributeValue != null) {
                builder.setBackoffCriteria(java.lang.Long.parseLong(attributeValue), java.lang.Integer.parseInt(xmlPullParser.getAttributeValue(null, "backoff-policy")));
            }
        }

        private android.util.Pair<java.lang.Long, java.lang.Long> buildRtcExecutionTimesFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
            return android.util.Pair.create(java.lang.Long.valueOf(typedXmlPullParser.getAttributeLong((java.lang.String) null, "delay", 0L)), java.lang.Long.valueOf(typedXmlPullParser.getAttributeLong((java.lang.String) null, "deadline", com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME)));
        }

        @android.annotation.NonNull
        private java.util.List<android.app.job.JobWorkItem> readJobWorkItemsFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int eventType = typedXmlPullParser.getEventType();
            while (eventType != 1 && com.android.server.job.JobStore.XML_TAG_JOB_WORK_ITEM.equals(typedXmlPullParser.getName())) {
                try {
                    android.app.job.JobWorkItem readJobWorkItemFromXml = readJobWorkItemFromXml(typedXmlPullParser);
                    if (readJobWorkItemFromXml != null) {
                        arrayList.add(readJobWorkItemFromXml);
                    }
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(com.android.server.job.JobStore.TAG, "Problem with persisted JobWorkItem", e);
                }
                eventType = typedXmlPullParser.next();
            }
            return arrayList;
        }

        @android.annotation.Nullable
        private android.app.job.JobWorkItem readJobWorkItemFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            android.app.job.JobWorkItem.Builder builder = new android.app.job.JobWorkItem.Builder();
            builder.setDeliveryCount(typedXmlPullParser.getAttributeInt((java.lang.String) null, "delivery-count")).setEstimatedNetworkBytes(typedXmlPullParser.getAttributeLong((java.lang.String) null, "estimated-download-bytes", -1L), typedXmlPullParser.getAttributeLong((java.lang.String) null, "estimated-upload-bytes", -1L)).setMinimumNetworkChunkBytes(typedXmlPullParser.getAttributeLong((java.lang.String) null, "minimum-network-chunk-bytes", -1L));
            typedXmlPullParser.next();
            try {
                builder.setExtras(android.os.PersistableBundle.restoreFromXml(typedXmlPullParser));
                try {
                    return builder.build();
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(com.android.server.job.JobStore.TAG, "Invalid JobWorkItem", e);
                    return null;
                }
            } catch (java.lang.IllegalArgumentException e2) {
                android.util.Slog.e(com.android.server.job.JobStore.TAG, "Persisted extras contained invalid data", e2);
                return null;
            }
        }

        @android.annotation.NonNull
        private java.util.Set<java.lang.String> readDebugTagsFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            android.util.ArraySet arraySet = new android.util.ArraySet();
            int eventType = typedXmlPullParser.getEventType();
            while (eventType != 1 && com.android.server.job.JobStore.XML_TAG_DEBUG_TAG.equals(typedXmlPullParser.getName())) {
                if (arraySet.size() < 32) {
                    try {
                        arraySet.add(android.app.job.JobInfo.validateDebugTag(typedXmlPullParser.getAttributeValue((java.lang.String) null, "tag")));
                    } catch (java.lang.Exception e) {
                        android.util.Slog.wtf(com.android.server.job.JobStore.TAG, "Invalid debug tag persisted to disk", e);
                    }
                }
                eventType = typedXmlPullParser.next();
            }
            return arraySet;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static final class JobSet {

        @com.android.internal.annotations.VisibleForTesting
        final android.util.SparseArray<android.util.ArraySet<com.android.server.job.controllers.JobStatus>> mJobs = new android.util.SparseArray<>();

        @com.android.internal.annotations.VisibleForTesting
        final android.util.SparseArray<android.util.ArraySet<com.android.server.job.controllers.JobStatus>> mJobsPerSourceUid = new android.util.SparseArray<>();

        public android.util.ArraySet<com.android.server.job.controllers.JobStatus> getJobsByUid(int i) {
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = new android.util.ArraySet<>();
            getJobsByUid(i, arraySet);
            return arraySet;
        }

        public void getJobsByUid(int i, java.util.Set<com.android.server.job.controllers.JobStatus> set) {
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mJobs.get(i);
            if (arraySet != null) {
                set.addAll(arraySet);
            }
        }

        @android.annotation.NonNull
        public android.util.ArraySet<com.android.server.job.controllers.JobStatus> getJobsBySourceUid(int i) {
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = new android.util.ArraySet<>();
            getJobsBySourceUid(i, arraySet);
            return arraySet;
        }

        public void getJobsBySourceUid(int i, java.util.Set<com.android.server.job.controllers.JobStatus> set) {
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mJobsPerSourceUid.get(i);
            if (arraySet != null) {
                set.addAll(arraySet);
            }
        }

        public boolean add(com.android.server.job.controllers.JobStatus jobStatus) {
            int uid = jobStatus.getUid();
            int sourceUid = jobStatus.getSourceUid();
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mJobs.get(uid);
            if (arraySet == null) {
                arraySet = new android.util.ArraySet<>();
                this.mJobs.put(uid, arraySet);
            }
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet2 = this.mJobsPerSourceUid.get(sourceUid);
            if (arraySet2 == null) {
                arraySet2 = new android.util.ArraySet<>();
                this.mJobsPerSourceUid.put(sourceUid, arraySet2);
            }
            boolean add = arraySet.add(jobStatus);
            boolean add2 = arraySet2.add(jobStatus);
            if (add != add2) {
                android.util.Slog.wtf(com.android.server.job.JobStore.TAG, "mJobs and mJobsPerSourceUid mismatch; caller= " + add + " source= " + add2);
            }
            return add || add2;
        }

        public boolean remove(com.android.server.job.controllers.JobStatus jobStatus) {
            int uid = jobStatus.getUid();
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mJobs.get(uid);
            int sourceUid = jobStatus.getSourceUid();
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet2 = this.mJobsPerSourceUid.get(sourceUid);
            boolean z = arraySet != null && arraySet.remove(jobStatus);
            boolean z2 = arraySet2 != null && arraySet2.remove(jobStatus);
            if (z != z2) {
                android.util.Slog.wtf(com.android.server.job.JobStore.TAG, "Job presence mismatch; caller=" + z + " source=" + z2);
            }
            if (!z && !z2) {
                return false;
            }
            if (arraySet != null && arraySet.size() == 0) {
                this.mJobs.remove(uid);
            }
            if (arraySet2 != null && arraySet2.size() == 0) {
                this.mJobsPerSourceUid.remove(sourceUid);
            }
            return true;
        }

        public void removeJobsOfUnlistedUsers(final int[] iArr) {
            removeAll(new java.util.function.Predicate() { // from class: com.android.server.job.JobStore$JobSet$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeJobsOfUnlistedUsers$0;
                    lambda$removeJobsOfUnlistedUsers$0 = com.android.server.job.JobStore.JobSet.lambda$removeJobsOfUnlistedUsers$0(iArr, (com.android.server.job.controllers.JobStatus) obj);
                    return lambda$removeJobsOfUnlistedUsers$0;
                }
            }.or(new java.util.function.Predicate() { // from class: com.android.server.job.JobStore$JobSet$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$removeJobsOfUnlistedUsers$1;
                    lambda$removeJobsOfUnlistedUsers$1 = com.android.server.job.JobStore.JobSet.lambda$removeJobsOfUnlistedUsers$1(iArr, (com.android.server.job.controllers.JobStatus) obj);
                    return lambda$removeJobsOfUnlistedUsers$1;
                }
            }));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$removeJobsOfUnlistedUsers$0(int[] iArr, com.android.server.job.controllers.JobStatus jobStatus) {
            return !com.android.internal.util.jobs.ArrayUtils.contains(iArr, jobStatus.getSourceUserId());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$removeJobsOfUnlistedUsers$1(int[] iArr, com.android.server.job.controllers.JobStatus jobStatus) {
            return !com.android.internal.util.jobs.ArrayUtils.contains(iArr, jobStatus.getUserId());
        }

        private void removeAll(java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
            for (int size = this.mJobs.size() - 1; size >= 0; size--) {
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> valueAt = this.mJobs.valueAt(size);
                valueAt.removeIf(predicate);
                if (valueAt.size() == 0) {
                    this.mJobs.removeAt(size);
                }
            }
            for (int size2 = this.mJobsPerSourceUid.size() - 1; size2 >= 0; size2--) {
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> valueAt2 = this.mJobsPerSourceUid.valueAt(size2);
                valueAt2.removeIf(predicate);
                if (valueAt2.size() == 0) {
                    this.mJobsPerSourceUid.removeAt(size2);
                }
            }
        }

        public boolean contains(com.android.server.job.controllers.JobStatus jobStatus) {
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mJobs.get(jobStatus.getUid());
            return arraySet != null && arraySet.contains(jobStatus);
        }

        public com.android.server.job.controllers.JobStatus get(int i, @android.annotation.Nullable java.lang.String str, int i2) {
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mJobs.get(i);
            if (arraySet != null) {
                for (int size = arraySet.size() - 1; size >= 0; size--) {
                    com.android.server.job.controllers.JobStatus valueAt = arraySet.valueAt(size);
                    if (valueAt.getJobId() == i2 && java.util.Objects.equals(str, valueAt.getNamespace())) {
                        return valueAt;
                    }
                }
                return null;
            }
            return null;
        }

        public java.util.List<com.android.server.job.controllers.JobStatus> getAllJobs() {
            java.util.ArrayList arrayList = new java.util.ArrayList(size());
            for (int size = this.mJobs.size() - 1; size >= 0; size--) {
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> valueAt = this.mJobs.valueAt(size);
                if (valueAt != null) {
                    for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                        arrayList.add(valueAt.valueAt(size2));
                    }
                }
            }
            return arrayList;
        }

        public void clear() {
            this.mJobs.clear();
            this.mJobsPerSourceUid.clear();
        }

        public int size() {
            int i = 0;
            for (int size = this.mJobs.size() - 1; size >= 0; size--) {
                i += this.mJobs.valueAt(size).size();
            }
            return i;
        }

        public int countJobsForUid(int i) {
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mJobs.get(i);
            int i2 = 0;
            if (arraySet != null) {
                for (int size = arraySet.size() - 1; size >= 0; size--) {
                    com.android.server.job.controllers.JobStatus valueAt = arraySet.valueAt(size);
                    if (valueAt.getUid() == valueAt.getSourceUid()) {
                        i2++;
                    }
                }
            }
            return i2;
        }

        public void forEachJob(@android.annotation.Nullable java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate, @android.annotation.NonNull java.util.function.Consumer<com.android.server.job.controllers.JobStatus> consumer) {
            for (int size = this.mJobs.size() - 1; size >= 0; size--) {
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> valueAt = this.mJobs.valueAt(size);
                if (valueAt != null) {
                    for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                        com.android.server.job.controllers.JobStatus valueAt2 = valueAt.valueAt(size2);
                        if (predicate == null || predicate.test(valueAt2)) {
                            consumer.accept(valueAt2);
                        }
                    }
                }
            }
        }

        public void forEachJob(int i, java.util.function.Consumer<com.android.server.job.controllers.JobStatus> consumer) {
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mJobs.get(i);
            if (arraySet != null) {
                for (int size = arraySet.size() - 1; size >= 0; size--) {
                    consumer.accept(arraySet.valueAt(size));
                }
            }
        }

        public void forEachJobForSourceUid(int i, java.util.function.Consumer<com.android.server.job.controllers.JobStatus> consumer) {
            android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = this.mJobsPerSourceUid.get(i);
            if (arraySet != null) {
                for (int size = arraySet.size() - 1; size >= 0; size--) {
                    consumer.accept(arraySet.valueAt(size));
                }
            }
        }
    }
}
