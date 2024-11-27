package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public final class ContentObserverController extends com.android.server.job.controllers.StateController {
    private static final boolean DEBUG;
    private static final int MAX_URIS_REPORTED = 50;
    private static final java.lang.String TAG = "JobScheduler.ContentObserver";
    private static final int URIS_URGENT_THRESHOLD = 40;
    final android.os.Handler mHandler;
    final android.util.SparseArray<android.util.ArrayMap<android.app.job.JobInfo.TriggerContentUri, com.android.server.job.controllers.ContentObserverController.ObserverInstance>> mObservers;
    private final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mTrackedTasks;

    static {
        DEBUG = com.android.server.job.JobSchedulerService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    public ContentObserverController(com.android.server.job.JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mTrackedTasks = new android.util.ArraySet<>();
        this.mObservers = new android.util.SparseArray<>();
        this.mHandler = new android.os.Handler(com.android.server.AppSchedulingModuleThread.get().getLooper());
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStartTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        boolean z;
        if (jobStatus.hasContentTriggerConstraint()) {
            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            if (jobStatus.contentObserverJobInstance == null) {
                jobStatus.contentObserverJobInstance = new com.android.server.job.controllers.ContentObserverController.JobInstance(jobStatus);
            }
            if (DEBUG) {
                android.util.Slog.i(TAG, "Tracking content-trigger job " + jobStatus);
            }
            this.mTrackedTasks.add(jobStatus);
            jobStatus.setTrackingController(4);
            boolean z2 = true;
            if (jobStatus.contentObserverJobInstance.mChangedAuthorities == null) {
                z = false;
            } else {
                z = true;
            }
            if (jobStatus.changedAuthorities == null) {
                z2 = z;
            } else {
                if (jobStatus.contentObserverJobInstance.mChangedAuthorities == null) {
                    jobStatus.contentObserverJobInstance.mChangedAuthorities = new android.util.ArraySet<>();
                }
                java.util.Iterator<java.lang.String> it = jobStatus.changedAuthorities.iterator();
                while (it.hasNext()) {
                    jobStatus.contentObserverJobInstance.mChangedAuthorities.add(it.next());
                }
                if (jobStatus.changedUris != null) {
                    if (jobStatus.contentObserverJobInstance.mChangedUris == null) {
                        jobStatus.contentObserverJobInstance.mChangedUris = new android.util.ArraySet<>();
                    }
                    java.util.Iterator<android.net.Uri> it2 = jobStatus.changedUris.iterator();
                    while (it2.hasNext()) {
                        jobStatus.contentObserverJobInstance.mChangedUris.add(it2.next());
                    }
                }
            }
            jobStatus.changedAuthorities = null;
            jobStatus.changedUris = null;
            jobStatus.setContentTriggerConstraintSatisfied(millis, z2);
        }
        if (jobStatus2 != null && jobStatus2.contentObserverJobInstance != null) {
            jobStatus2.contentObserverJobInstance.detachLocked();
            jobStatus2.contentObserverJobInstance = null;
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void prepareForExecutionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.hasContentTriggerConstraint() && jobStatus.contentObserverJobInstance != null) {
            jobStatus.changedUris = jobStatus.contentObserverJobInstance.mChangedUris;
            jobStatus.changedAuthorities = jobStatus.contentObserverJobInstance.mChangedAuthorities;
            jobStatus.contentObserverJobInstance.mChangedUris = null;
            jobStatus.contentObserverJobInstance.mChangedAuthorities = null;
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void unprepareFromExecutionLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        if (jobStatus.hasContentTriggerConstraint() && jobStatus.contentObserverJobInstance != null) {
            if (jobStatus.contentObserverJobInstance.mChangedUris == null) {
                jobStatus.contentObserverJobInstance.mChangedUris = jobStatus.changedUris;
            } else {
                jobStatus.contentObserverJobInstance.mChangedUris.addAll((android.util.ArraySet<? extends android.net.Uri>) jobStatus.changedUris);
            }
            if (jobStatus.contentObserverJobInstance.mChangedAuthorities == null) {
                jobStatus.contentObserverJobInstance.mChangedAuthorities = jobStatus.changedAuthorities;
            } else {
                jobStatus.contentObserverJobInstance.mChangedAuthorities.addAll((android.util.ArraySet<? extends java.lang.String>) jobStatus.changedAuthorities);
            }
            jobStatus.changedUris = null;
            jobStatus.changedAuthorities = null;
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(4)) {
            this.mTrackedTasks.remove(jobStatus);
            if (jobStatus.contentObserverJobInstance != null) {
                jobStatus.contentObserverJobInstance.unscheduleLocked();
                if (jobStatus2 != null) {
                    if (jobStatus.contentObserverJobInstance != null && jobStatus.contentObserverJobInstance.mChangedAuthorities != null) {
                        if (jobStatus2.contentObserverJobInstance == null) {
                            jobStatus2.contentObserverJobInstance = new com.android.server.job.controllers.ContentObserverController.JobInstance(jobStatus2);
                        }
                        jobStatus2.contentObserverJobInstance.mChangedAuthorities = jobStatus.contentObserverJobInstance.mChangedAuthorities;
                        jobStatus2.contentObserverJobInstance.mChangedUris = jobStatus.contentObserverJobInstance.mChangedUris;
                        jobStatus.contentObserverJobInstance.mChangedAuthorities = null;
                        jobStatus.contentObserverJobInstance.mChangedUris = null;
                    }
                } else {
                    jobStatus.contentObserverJobInstance.detachLocked();
                    jobStatus.contentObserverJobInstance = null;
                }
            }
            if (DEBUG) {
                android.util.Slog.i(TAG, "No longer tracking job " + jobStatus);
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void rescheduleForFailureLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus2.hasContentTriggerConstraint() && jobStatus.hasContentTriggerConstraint()) {
            jobStatus.changedAuthorities = jobStatus2.changedAuthorities;
            jobStatus.changedUris = jobStatus2.changedUris;
        }
    }

    final class ObserverInstance extends android.database.ContentObserver {
        final android.util.ArraySet<com.android.server.job.controllers.ContentObserverController.JobInstance> mJobs;
        final android.app.job.JobInfo.TriggerContentUri mUri;
        final int mUserId;

        public ObserverInstance(android.os.Handler handler, android.app.job.JobInfo.TriggerContentUri triggerContentUri, int i) {
            super(handler);
            this.mJobs = new android.util.ArraySet<>();
            this.mUri = triggerContentUri;
            this.mUserId = i;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (com.android.server.job.controllers.ContentObserverController.DEBUG) {
                android.util.Slog.i(com.android.server.job.controllers.ContentObserverController.TAG, "onChange(self=" + z + ") for " + uri + " when mUri=" + this.mUri + " mUserId=" + this.mUserId);
            }
            synchronized (com.android.server.job.controllers.ContentObserverController.this.mLock) {
                try {
                    int size = this.mJobs.size();
                    for (int i = 0; i < size; i++) {
                        com.android.server.job.controllers.ContentObserverController.JobInstance valueAt = this.mJobs.valueAt(i);
                        if (valueAt.mChangedUris == null) {
                            valueAt.mChangedUris = new android.util.ArraySet<>();
                        }
                        if (valueAt.mChangedUris.size() < 50) {
                            valueAt.mChangedUris.add(uri);
                        }
                        if (valueAt.mChangedAuthorities == null) {
                            valueAt.mChangedAuthorities = new android.util.ArraySet<>();
                        }
                        valueAt.mChangedAuthorities.add(uri.getAuthority());
                        valueAt.scheduleLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    static final class TriggerRunnable implements java.lang.Runnable {
        final com.android.server.job.controllers.ContentObserverController.JobInstance mInstance;

        TriggerRunnable(com.android.server.job.controllers.ContentObserverController.JobInstance jobInstance) {
            this.mInstance = jobInstance;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mInstance.trigger();
        }
    }

    final class JobInstance {
        android.util.ArraySet<java.lang.String> mChangedAuthorities;
        android.util.ArraySet<android.net.Uri> mChangedUris;
        final com.android.server.job.controllers.JobStatus mJobStatus;
        boolean mTriggerPending;
        final java.util.ArrayList<com.android.server.job.controllers.ContentObserverController.ObserverInstance> mMyObservers = new java.util.ArrayList<>();
        final java.lang.Runnable mExecuteRunner = new com.android.server.job.controllers.ContentObserverController.TriggerRunnable(this);
        final java.lang.Runnable mTimeoutRunner = new com.android.server.job.controllers.ContentObserverController.TriggerRunnable(this);

        JobInstance(com.android.server.job.controllers.JobStatus jobStatus) {
            this.mJobStatus = jobStatus;
            android.app.job.JobInfo.TriggerContentUri[] triggerContentUris = jobStatus.getJob().getTriggerContentUris();
            int sourceUserId = jobStatus.getSourceUserId();
            android.util.ArrayMap<android.app.job.JobInfo.TriggerContentUri, com.android.server.job.controllers.ContentObserverController.ObserverInstance> arrayMap = com.android.server.job.controllers.ContentObserverController.this.mObservers.get(sourceUserId);
            if (arrayMap == null) {
                arrayMap = new android.util.ArrayMap<>();
                com.android.server.job.controllers.ContentObserverController.this.mObservers.put(sourceUserId, arrayMap);
            }
            if (triggerContentUris != null) {
                for (android.app.job.JobInfo.TriggerContentUri triggerContentUri : triggerContentUris) {
                    com.android.server.job.controllers.ContentObserverController.ObserverInstance observerInstance = arrayMap.get(triggerContentUri);
                    if (observerInstance == null) {
                        observerInstance = com.android.server.job.controllers.ContentObserverController.this.new ObserverInstance(com.android.server.job.controllers.ContentObserverController.this.mHandler, triggerContentUri, jobStatus.getSourceUserId());
                        arrayMap.put(triggerContentUri, observerInstance);
                        boolean z = (triggerContentUri.getFlags() & 1) != 0;
                        if (com.android.server.job.controllers.ContentObserverController.DEBUG) {
                            android.util.Slog.v(com.android.server.job.controllers.ContentObserverController.TAG, "New observer " + observerInstance + " for " + triggerContentUri.getUri() + " andDescendants=" + z + " sourceUserId=" + sourceUserId);
                        }
                        com.android.server.job.controllers.ContentObserverController.this.mContext.getContentResolver().registerContentObserver(triggerContentUri.getUri(), z, observerInstance, sourceUserId);
                    } else if (com.android.server.job.controllers.ContentObserverController.DEBUG) {
                        android.util.Slog.v(com.android.server.job.controllers.ContentObserverController.TAG, "Reusing existing observer " + observerInstance + " for " + triggerContentUri.getUri() + " andDescendants=" + ((triggerContentUri.getFlags() & 1) != 0));
                    }
                    observerInstance.mJobs.add(this);
                    this.mMyObservers.add(observerInstance);
                }
            }
        }

        void trigger() {
            boolean z;
            synchronized (com.android.server.job.controllers.ContentObserverController.this.mLock) {
                try {
                    if (this.mTriggerPending) {
                        z = this.mJobStatus.setContentTriggerConstraintSatisfied(com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis(), true);
                        unscheduleLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (z) {
                android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = new android.util.ArraySet<>();
                arraySet.add(this.mJobStatus);
                com.android.server.job.controllers.ContentObserverController.this.mStateChangedListener.onControllerStateChanged(arraySet);
            }
        }

        void scheduleLocked() {
            if (!this.mTriggerPending) {
                this.mTriggerPending = true;
                com.android.server.job.controllers.ContentObserverController.this.mHandler.postDelayed(this.mTimeoutRunner, this.mJobStatus.getTriggerContentMaxDelay());
            }
            com.android.server.job.controllers.ContentObserverController.this.mHandler.removeCallbacks(this.mExecuteRunner);
            if (this.mChangedUris.size() >= 40) {
                com.android.server.job.controllers.ContentObserverController.this.mHandler.post(this.mExecuteRunner);
            } else {
                com.android.server.job.controllers.ContentObserverController.this.mHandler.postDelayed(this.mExecuteRunner, this.mJobStatus.getTriggerContentUpdateDelay());
            }
        }

        void unscheduleLocked() {
            if (this.mTriggerPending) {
                com.android.server.job.controllers.ContentObserverController.this.mHandler.removeCallbacks(this.mExecuteRunner);
                com.android.server.job.controllers.ContentObserverController.this.mHandler.removeCallbacks(this.mTimeoutRunner);
                this.mTriggerPending = false;
            }
        }

        void detachLocked() {
            int size = this.mMyObservers.size();
            for (int i = 0; i < size; i++) {
                com.android.server.job.controllers.ContentObserverController.ObserverInstance observerInstance = this.mMyObservers.get(i);
                observerInstance.mJobs.remove(this);
                if (observerInstance.mJobs.size() == 0) {
                    if (com.android.server.job.controllers.ContentObserverController.DEBUG) {
                        android.util.Slog.i(com.android.server.job.controllers.ContentObserverController.TAG, "Unregistering observer " + observerInstance + " for " + observerInstance.mUri.getUri());
                    }
                    com.android.server.job.controllers.ContentObserverController.this.mContext.getContentResolver().unregisterContentObserver(observerInstance);
                    android.util.ArrayMap<android.app.job.JobInfo.TriggerContentUri, com.android.server.job.controllers.ContentObserverController.ObserverInstance> arrayMap = com.android.server.job.controllers.ContentObserverController.this.mObservers.get(observerInstance.mUserId);
                    if (arrayMap != null) {
                        arrayMap.remove(observerInstance.mUri);
                    }
                }
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        boolean z;
        int i;
        int i2;
        for (int i3 = 0; i3 < this.mTrackedTasks.size(); i3++) {
            com.android.server.job.controllers.JobStatus valueAt = this.mTrackedTasks.valueAt(i3);
            if (predicate.test(valueAt)) {
                indentingPrintWriter.print("#");
                valueAt.printUniqueId(indentingPrintWriter);
                indentingPrintWriter.print(" from ");
                android.os.UserHandle.formatUid(indentingPrintWriter, valueAt.getSourceUid());
                indentingPrintWriter.println();
            }
        }
        indentingPrintWriter.println();
        int size = this.mObservers.size();
        if (size > 0) {
            indentingPrintWriter.println("Observers:");
            indentingPrintWriter.increaseIndent();
            for (int i4 = 0; i4 < size; i4++) {
                android.util.ArrayMap<android.app.job.JobInfo.TriggerContentUri, com.android.server.job.controllers.ContentObserverController.ObserverInstance> arrayMap = this.mObservers.get(this.mObservers.keyAt(i4));
                int size2 = arrayMap.size();
                int i5 = 0;
                while (i5 < size2) {
                    com.android.server.job.controllers.ContentObserverController.ObserverInstance valueAt2 = arrayMap.valueAt(i5);
                    int size3 = valueAt2.mJobs.size();
                    int i6 = 0;
                    while (true) {
                        if (i6 < size3) {
                            if (!predicate.test(valueAt2.mJobs.valueAt(i6).mJobStatus)) {
                                i6++;
                            } else {
                                z = true;
                                break;
                            }
                        } else {
                            z = false;
                            break;
                        }
                    }
                    if (!z) {
                        i = size;
                    } else {
                        android.app.job.JobInfo.TriggerContentUri keyAt = arrayMap.keyAt(i5);
                        indentingPrintWriter.print(keyAt.getUri());
                        indentingPrintWriter.print(" 0x");
                        indentingPrintWriter.print(java.lang.Integer.toHexString(keyAt.getFlags()));
                        indentingPrintWriter.print(" (");
                        indentingPrintWriter.print(java.lang.System.identityHashCode(valueAt2));
                        indentingPrintWriter.println("):");
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.println("Jobs:");
                        indentingPrintWriter.increaseIndent();
                        int i7 = 0;
                        while (i7 < size3) {
                            com.android.server.job.controllers.ContentObserverController.JobInstance valueAt3 = valueAt2.mJobs.valueAt(i7);
                            indentingPrintWriter.print("#");
                            valueAt3.mJobStatus.printUniqueId(indentingPrintWriter);
                            indentingPrintWriter.print(" from ");
                            android.os.UserHandle.formatUid(indentingPrintWriter, valueAt3.mJobStatus.getSourceUid());
                            if (valueAt3.mChangedAuthorities != null) {
                                indentingPrintWriter.println(":");
                                indentingPrintWriter.increaseIndent();
                                if (!valueAt3.mTriggerPending) {
                                    i2 = size;
                                } else {
                                    indentingPrintWriter.print("Trigger pending: update=");
                                    i2 = size;
                                    android.util.TimeUtils.formatDuration(valueAt3.mJobStatus.getTriggerContentUpdateDelay(), indentingPrintWriter);
                                    indentingPrintWriter.print(", max=");
                                    android.util.TimeUtils.formatDuration(valueAt3.mJobStatus.getTriggerContentMaxDelay(), indentingPrintWriter);
                                    indentingPrintWriter.println();
                                }
                                indentingPrintWriter.println("Changed Authorities:");
                                for (int i8 = 0; i8 < valueAt3.mChangedAuthorities.size(); i8++) {
                                    indentingPrintWriter.println(valueAt3.mChangedAuthorities.valueAt(i8));
                                }
                                if (valueAt3.mChangedUris != null) {
                                    indentingPrintWriter.println("          Changed URIs:");
                                    for (int i9 = 0; i9 < valueAt3.mChangedUris.size(); i9++) {
                                        indentingPrintWriter.println(valueAt3.mChangedUris.valueAt(i9));
                                    }
                                }
                                indentingPrintWriter.decreaseIndent();
                            } else {
                                i2 = size;
                                indentingPrintWriter.println();
                            }
                            i7++;
                            size = i2;
                        }
                        i = size;
                        indentingPrintWriter.decreaseIndent();
                        indentingPrintWriter.decreaseIndent();
                    }
                    i5++;
                    size = i;
                }
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        boolean z;
        long j2;
        long j3;
        android.util.ArrayMap<android.app.job.JobInfo.TriggerContentUri, com.android.server.job.controllers.ContentObserverController.ObserverInstance> arrayMap;
        int i;
        com.android.server.job.controllers.ContentObserverController.ObserverInstance observerInstance;
        int i2;
        com.android.server.job.controllers.ContentObserverController contentObserverController = this;
        java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate2 = predicate;
        long start = protoOutputStream.start(j);
        long start2 = protoOutputStream.start(1146756268036L);
        for (int i3 = 0; i3 < contentObserverController.mTrackedTasks.size(); i3++) {
            com.android.server.job.controllers.JobStatus valueAt = contentObserverController.mTrackedTasks.valueAt(i3);
            if (predicate2.test(valueAt)) {
                long start3 = protoOutputStream.start(2246267895809L);
                valueAt.writeToShortProto(protoOutputStream, 1146756268033L);
                protoOutputStream.write(1120986464258L, valueAt.getSourceUid());
                protoOutputStream.end(start3);
            }
        }
        int size = contentObserverController.mObservers.size();
        int i4 = 0;
        while (i4 < size) {
            int i5 = size;
            long start4 = protoOutputStream.start(2246267895810L);
            int keyAt = contentObserverController.mObservers.keyAt(i4);
            protoOutputStream.write(1120986464257L, keyAt);
            android.util.ArrayMap<android.app.job.JobInfo.TriggerContentUri, com.android.server.job.controllers.ContentObserverController.ObserverInstance> arrayMap2 = contentObserverController.mObservers.get(keyAt);
            int size2 = arrayMap2.size();
            int i6 = 0;
            while (i6 < size2) {
                com.android.server.job.controllers.ContentObserverController.ObserverInstance valueAt2 = arrayMap2.valueAt(i6);
                int size3 = valueAt2.mJobs.size();
                int i7 = 0;
                while (true) {
                    if (i7 < size3) {
                        if (!predicate2.test(valueAt2.mJobs.valueAt(i7).mJobStatus)) {
                            i7++;
                        } else {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
                if (!z) {
                    j2 = start;
                    j3 = start2;
                    arrayMap = arrayMap2;
                    i = size2;
                } else {
                    j2 = start;
                    j3 = start2;
                    long start5 = protoOutputStream.start(2246267895810L);
                    android.app.job.JobInfo.TriggerContentUri keyAt2 = arrayMap2.keyAt(i6);
                    android.net.Uri uri = keyAt2.getUri();
                    if (uri != null) {
                        protoOutputStream.write(1138166333441L, uri.toString());
                    }
                    protoOutputStream.write(1120986464258L, keyAt2.getFlags());
                    int i8 = 0;
                    while (i8 < size3) {
                        long start6 = protoOutputStream.start(2246267895811L);
                        com.android.server.job.controllers.ContentObserverController.JobInstance valueAt3 = valueAt2.mJobs.valueAt(i8);
                        android.util.ArrayMap<android.app.job.JobInfo.TriggerContentUri, com.android.server.job.controllers.ContentObserverController.ObserverInstance> arrayMap3 = arrayMap2;
                        int i9 = size2;
                        valueAt3.mJobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
                        protoOutputStream.write(1120986464258L, valueAt3.mJobStatus.getSourceUid());
                        if (valueAt3.mChangedAuthorities == null) {
                            protoOutputStream.end(start6);
                            observerInstance = valueAt2;
                            i2 = size3;
                        } else {
                            if (!valueAt3.mTriggerPending) {
                                observerInstance = valueAt2;
                                i2 = size3;
                            } else {
                                observerInstance = valueAt2;
                                i2 = size3;
                                protoOutputStream.write(1112396529667L, valueAt3.mJobStatus.getTriggerContentUpdateDelay());
                                protoOutputStream.write(1112396529668L, valueAt3.mJobStatus.getTriggerContentMaxDelay());
                            }
                            for (int i10 = 0; i10 < valueAt3.mChangedAuthorities.size(); i10++) {
                                protoOutputStream.write(2237677961221L, valueAt3.mChangedAuthorities.valueAt(i10));
                            }
                            if (valueAt3.mChangedUris != null) {
                                for (int i11 = 0; i11 < valueAt3.mChangedUris.size(); i11++) {
                                    android.net.Uri valueAt4 = valueAt3.mChangedUris.valueAt(i11);
                                    if (valueAt4 != null) {
                                        protoOutputStream.write(2237677961222L, valueAt4.toString());
                                    }
                                }
                            }
                            protoOutputStream.end(start6);
                        }
                        i8++;
                        valueAt2 = observerInstance;
                        arrayMap2 = arrayMap3;
                        size2 = i9;
                        size3 = i2;
                    }
                    arrayMap = arrayMap2;
                    i = size2;
                    protoOutputStream.end(start5);
                }
                i6++;
                predicate2 = predicate;
                start2 = j3;
                start = j2;
                arrayMap2 = arrayMap;
                size2 = i;
            }
            protoOutputStream.end(start4);
            i4++;
            contentObserverController = this;
            size = i5;
            predicate2 = predicate;
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }
}
