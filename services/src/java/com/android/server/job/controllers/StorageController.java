package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public final class StorageController extends com.android.server.job.controllers.StateController {
    private static final boolean DEBUG;
    private static final java.lang.String TAG = "JobScheduler.Storage";
    private final com.android.server.job.controllers.StorageController.StorageTracker mStorageTracker;
    private final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mTrackedTasks;

    static {
        DEBUG = com.android.server.job.JobSchedulerService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    @com.android.internal.annotations.VisibleForTesting
    public com.android.server.job.controllers.StorageController.StorageTracker getTracker() {
        return this.mStorageTracker;
    }

    public StorageController(com.android.server.job.JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mTrackedTasks = new android.util.ArraySet<>();
        this.mStorageTracker = new com.android.server.job.controllers.StorageController.StorageTracker();
    }

    @Override // com.android.server.job.controllers.StateController
    public void startTrackingLocked() {
        this.mStorageTracker.startTracking();
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStartTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.hasStorageNotLowConstraint()) {
            long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
            this.mTrackedTasks.add(jobStatus);
            jobStatus.setTrackingController(16);
            jobStatus.setStorageNotLowConstraintSatisfied(millis, this.mStorageTracker.isStorageNotLow());
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(16)) {
            this.mTrackedTasks.remove(jobStatus);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeReportNewStorageState() {
        boolean z;
        long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        boolean isStorageNotLow = this.mStorageTracker.isStorageNotLow();
        synchronized (this.mLock) {
            try {
                z = false;
                for (int size = this.mTrackedTasks.size() - 1; size >= 0; size--) {
                    z |= this.mTrackedTasks.valueAt(size).setStorageNotLowConstraintSatisfied(millis, isStorageNotLow);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (isStorageNotLow) {
            this.mStateChangedListener.onRunJobNow(null);
        } else if (z) {
            this.mStateChangedListener.onControllerStateChanged(this.mTrackedTasks);
        }
    }

    public final class StorageTracker extends android.content.BroadcastReceiver {
        private int mLastStorageSeq = -1;
        private boolean mStorageLow;

        public StorageTracker() {
        }

        public void startTracking() {
            android.content.IntentFilter intentFilter = new android.content.IntentFilter();
            intentFilter.addAction("android.intent.action.DEVICE_STORAGE_LOW");
            intentFilter.addAction("android.intent.action.DEVICE_STORAGE_OK");
            com.android.server.job.controllers.StorageController.this.mContext.registerReceiver(this, intentFilter);
        }

        public boolean isStorageNotLow() {
            return !this.mStorageLow;
        }

        public int getSeq() {
            return this.mLastStorageSeq;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            onReceiveInternal(intent);
        }

        @com.android.internal.annotations.VisibleForTesting
        public void onReceiveInternal(android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            this.mLastStorageSeq = intent.getIntExtra(com.android.server.storage.DeviceStorageMonitorService.EXTRA_SEQUENCE, this.mLastStorageSeq);
            if ("android.intent.action.DEVICE_STORAGE_LOW".equals(action)) {
                if (com.android.server.job.controllers.StorageController.DEBUG) {
                    android.util.Slog.d(com.android.server.job.controllers.StorageController.TAG, "Available storage too low to do work. @ " + com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
                }
                this.mStorageLow = true;
                com.android.server.job.controllers.StorageController.this.maybeReportNewStorageState();
                return;
            }
            if ("android.intent.action.DEVICE_STORAGE_OK".equals(action)) {
                if (com.android.server.job.controllers.StorageController.DEBUG) {
                    android.util.Slog.d(com.android.server.job.controllers.StorageController.TAG, "Available storage high enough to do work. @ " + com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
                }
                this.mStorageLow = false;
                com.android.server.job.controllers.StorageController.this.maybeReportNewStorageState();
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        indentingPrintWriter.println("Not low: " + this.mStorageTracker.isStorageNotLow());
        indentingPrintWriter.println("Sequence: " + this.mStorageTracker.getSeq());
        indentingPrintWriter.println();
        for (int i = 0; i < this.mTrackedTasks.size(); i++) {
            com.android.server.job.controllers.JobStatus valueAt = this.mTrackedTasks.valueAt(i);
            if (predicate.test(valueAt)) {
                indentingPrintWriter.print("#");
                valueAt.printUniqueId(indentingPrintWriter);
                indentingPrintWriter.print(" from ");
                android.os.UserHandle.formatUid(indentingPrintWriter, valueAt.getSourceUid());
                indentingPrintWriter.println();
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        long start = protoOutputStream.start(j);
        long start2 = protoOutputStream.start(1146756268039L);
        protoOutputStream.write(1133871366145L, this.mStorageTracker.isStorageNotLow());
        protoOutputStream.write(1120986464258L, this.mStorageTracker.getSeq());
        for (int i = 0; i < this.mTrackedTasks.size(); i++) {
            com.android.server.job.controllers.JobStatus valueAt = this.mTrackedTasks.valueAt(i);
            if (predicate.test(valueAt)) {
                long start3 = protoOutputStream.start(2246267895811L);
                valueAt.writeToShortProto(protoOutputStream, 1146756268033L);
                protoOutputStream.write(1120986464258L, valueAt.getSourceUid());
                protoOutputStream.end(start3);
            }
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }
}
