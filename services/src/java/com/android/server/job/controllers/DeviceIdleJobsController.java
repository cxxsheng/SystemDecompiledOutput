package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public final class DeviceIdleJobsController extends com.android.server.job.controllers.StateController {
    private static final long BACKGROUND_JOBS_DELAY = 3000;
    private static final boolean DEBUG;
    static final int PROCESS_BACKGROUND_JOBS = 1;
    private static final java.lang.String TAG = "JobScheduler.DeviceIdle";
    private final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mAllowInIdleJobs;
    private final android.content.BroadcastReceiver mBroadcastReceiver;
    private boolean mDeviceIdleMode;
    private final com.android.server.job.controllers.DeviceIdleJobsController.DeviceIdleUpdateFunctor mDeviceIdleUpdateFunctor;
    private int[] mDeviceIdleWhitelistAppIds;
    private final android.util.SparseBooleanArray mForegroundUids;
    private final com.android.server.job.controllers.DeviceIdleJobsController.DeviceIdleJobsDelayHandler mHandler;
    private final com.android.server.DeviceIdleInternal mLocalDeviceIdleController;
    private final android.os.PowerManager mPowerManager;
    private int[] mPowerSaveTempWhitelistAppIds;
    private int[] mPowerSaveWhitelistSystemAppIds;
    private final java.util.function.Predicate<com.android.server.job.controllers.JobStatus> mShouldRushEvaluation;

    static {
        DEBUG = com.android.server.job.JobSchedulerService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(com.android.server.job.controllers.JobStatus jobStatus) {
        return jobStatus.isRequestedExpeditedJob() || this.mForegroundUids.get(jobStatus.getSourceUid());
    }

    public DeviceIdleJobsController(com.android.server.job.JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mForegroundUids = new android.util.SparseBooleanArray();
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.job.controllers.DeviceIdleJobsController.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                char c;
                java.lang.String action = intent.getAction();
                boolean z = false;
                z = false;
                switch (action.hashCode()) {
                    case -712152692:
                        if (action.equals("android.os.action.POWER_SAVE_TEMP_WHITELIST_CHANGED")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case -65633567:
                        if (action.equals("android.os.action.POWER_SAVE_WHITELIST_CHANGED")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 498807504:
                        if (action.equals("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 870701415:
                        if (action.equals("android.os.action.DEVICE_IDLE_MODE_CHANGED")) {
                            c = 1;
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
                    case 1:
                        com.android.server.job.controllers.DeviceIdleJobsController deviceIdleJobsController = com.android.server.job.controllers.DeviceIdleJobsController.this;
                        if (com.android.server.job.controllers.DeviceIdleJobsController.this.mPowerManager != null && (com.android.server.job.controllers.DeviceIdleJobsController.this.mPowerManager.isDeviceIdleMode() || com.android.server.job.controllers.DeviceIdleJobsController.this.mPowerManager.isLightDeviceIdleMode())) {
                            z = true;
                        }
                        deviceIdleJobsController.updateIdleMode(z);
                        return;
                    case 2:
                        synchronized (com.android.server.job.controllers.DeviceIdleJobsController.this.mLock) {
                            try {
                                com.android.server.job.controllers.DeviceIdleJobsController.this.mDeviceIdleWhitelistAppIds = com.android.server.job.controllers.DeviceIdleJobsController.this.mLocalDeviceIdleController.getPowerSaveWhitelistUserAppIds();
                                if (com.android.server.job.controllers.DeviceIdleJobsController.DEBUG) {
                                    android.util.Slog.d(com.android.server.job.controllers.DeviceIdleJobsController.TAG, "Got whitelist " + java.util.Arrays.toString(com.android.server.job.controllers.DeviceIdleJobsController.this.mDeviceIdleWhitelistAppIds));
                                }
                            } finally {
                            }
                        }
                        return;
                    case 3:
                        synchronized (com.android.server.job.controllers.DeviceIdleJobsController.this.mLock) {
                            try {
                                com.android.server.job.controllers.DeviceIdleJobsController.this.mPowerSaveTempWhitelistAppIds = com.android.server.job.controllers.DeviceIdleJobsController.this.mLocalDeviceIdleController.getPowerSaveTempWhitelistAppIds();
                                if (com.android.server.job.controllers.DeviceIdleJobsController.DEBUG) {
                                    android.util.Slog.d(com.android.server.job.controllers.DeviceIdleJobsController.TAG, "Got temp whitelist " + java.util.Arrays.toString(com.android.server.job.controllers.DeviceIdleJobsController.this.mPowerSaveTempWhitelistAppIds));
                                }
                                android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet = new android.util.ArraySet<>();
                                long millis = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
                                for (int i = 0; i < com.android.server.job.controllers.DeviceIdleJobsController.this.mAllowInIdleJobs.size(); i++) {
                                    if (com.android.server.job.controllers.DeviceIdleJobsController.this.updateTaskStateLocked((com.android.server.job.controllers.JobStatus) com.android.server.job.controllers.DeviceIdleJobsController.this.mAllowInIdleJobs.valueAt(i), millis)) {
                                        arraySet.add((com.android.server.job.controllers.JobStatus) com.android.server.job.controllers.DeviceIdleJobsController.this.mAllowInIdleJobs.valueAt(i));
                                    }
                                }
                                if (arraySet.size() > 0) {
                                    com.android.server.job.controllers.DeviceIdleJobsController.this.mStateChangedListener.onControllerStateChanged(arraySet);
                                }
                            } finally {
                            }
                        }
                        return;
                    default:
                        return;
                }
            }
        };
        this.mShouldRushEvaluation = new java.util.function.Predicate() { // from class: com.android.server.job.controllers.DeviceIdleJobsController$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$new$0;
                lambda$new$0 = com.android.server.job.controllers.DeviceIdleJobsController.this.lambda$new$0((com.android.server.job.controllers.JobStatus) obj);
                return lambda$new$0;
            }
        };
        this.mHandler = new com.android.server.job.controllers.DeviceIdleJobsController.DeviceIdleJobsDelayHandler(com.android.server.AppSchedulingModuleThread.get().getLooper());
        this.mPowerManager = (android.os.PowerManager) this.mContext.getSystemService("power");
        this.mLocalDeviceIdleController = (com.android.server.DeviceIdleInternal) com.android.server.LocalServices.getService(com.android.server.DeviceIdleInternal.class);
        this.mDeviceIdleWhitelistAppIds = this.mLocalDeviceIdleController.getPowerSaveWhitelistUserAppIds();
        this.mPowerSaveWhitelistSystemAppIds = this.mLocalDeviceIdleController.getPowerSaveWhitelistSystemAppIds();
        this.mPowerSaveTempWhitelistAppIds = this.mLocalDeviceIdleController.getPowerSaveTempWhitelistAppIds();
        this.mDeviceIdleUpdateFunctor = new com.android.server.job.controllers.DeviceIdleJobsController.DeviceIdleUpdateFunctor();
        this.mAllowInIdleJobs = new android.util.ArraySet<>();
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
        intentFilter.addAction("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED");
        intentFilter.addAction("android.os.action.POWER_SAVE_WHITELIST_CHANGED");
        intentFilter.addAction("android.os.action.POWER_SAVE_TEMP_WHITELIST_CHANGED");
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter, null, null);
    }

    void updateIdleMode(boolean z) {
        boolean z2;
        synchronized (this.mLock) {
            try {
                if (this.mDeviceIdleMode == z) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                this.mDeviceIdleMode = z;
                logDeviceWideConstraintStateToStatsd(33554432, this.mDeviceIdleMode ? false : true);
                if (DEBUG) {
                    android.util.Slog.d(TAG, "mDeviceIdleMode=" + this.mDeviceIdleMode);
                }
                this.mDeviceIdleUpdateFunctor.prepare();
                if (z) {
                    this.mHandler.removeMessages(1);
                    this.mService.getJobStore().forEachJob(this.mDeviceIdleUpdateFunctor);
                } else {
                    this.mService.getJobStore().forEachJob(this.mShouldRushEvaluation, this.mDeviceIdleUpdateFunctor);
                    this.mHandler.sendEmptyMessageDelayed(1, 3000L);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (z2) {
            this.mStateChangedListener.onDeviceIdleStateChanged(z);
        }
    }

    public void setUidActiveLocked(int i, boolean z) {
        if (!(z != this.mForegroundUids.get(i))) {
            return;
        }
        if (DEBUG) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("uid ");
            sb.append(i);
            sb.append(" going ");
            sb.append(z ? com.android.server.pm.verify.domain.DomainVerificationPersistence.TAG_ACTIVE : "inactive");
            android.util.Slog.d(TAG, sb.toString());
        }
        this.mForegroundUids.put(i, z);
        this.mDeviceIdleUpdateFunctor.prepare();
        this.mService.getJobStore().forEachJobForSourceUid(i, this.mDeviceIdleUpdateFunctor);
        if (this.mDeviceIdleUpdateFunctor.mChangedJobs.size() > 0) {
            this.mStateChangedListener.onControllerStateChanged(this.mDeviceIdleUpdateFunctor.mChangedJobs);
        }
    }

    boolean isWhitelistedLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        int appId = android.os.UserHandle.getAppId(jobStatus.getSourceUid());
        return java.util.Arrays.binarySearch(this.mDeviceIdleWhitelistAppIds, appId) >= 0 || java.util.Arrays.binarySearch(this.mPowerSaveWhitelistSystemAppIds, appId) >= 0;
    }

    boolean isTempWhitelistedLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        return com.android.internal.util.jobs.ArrayUtils.contains(this.mPowerSaveTempWhitelistAppIds, android.os.UserHandle.getAppId(jobStatus.getSourceUid()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateTaskStateLocked(com.android.server.job.controllers.JobStatus jobStatus, long j) {
        boolean z = (jobStatus.getFlags() & 2) != 0 && (this.mForegroundUids.get(jobStatus.getSourceUid()) || isTempWhitelistedLocked(jobStatus));
        boolean isWhitelistedLocked = isWhitelistedLocked(jobStatus);
        return jobStatus.setDeviceNotDozingConstraintSatisfied(j, !this.mDeviceIdleMode || isWhitelistedLocked || z, isWhitelistedLocked);
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStartTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if ((jobStatus.getFlags() & 2) != 0) {
            this.mAllowInIdleJobs.add(jobStatus);
        }
        updateTaskStateLocked(jobStatus, com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis());
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        if ((jobStatus.getFlags() & 2) != 0) {
            this.mAllowInIdleJobs.remove(jobStatus);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(final android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        indentingPrintWriter.println("Idle mode: " + this.mDeviceIdleMode);
        indentingPrintWriter.println();
        this.mService.getJobStore().forEachJob(predicate, new java.util.function.Consumer() { // from class: com.android.server.job.controllers.DeviceIdleJobsController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.job.controllers.DeviceIdleJobsController.this.lambda$dumpControllerStateLocked$1(indentingPrintWriter, (com.android.server.job.controllers.JobStatus) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpControllerStateLocked$1(android.util.IndentingPrintWriter indentingPrintWriter, com.android.server.job.controllers.JobStatus jobStatus) {
        indentingPrintWriter.print("#");
        jobStatus.printUniqueId(indentingPrintWriter);
        indentingPrintWriter.print(" from ");
        android.os.UserHandle.formatUid(indentingPrintWriter, jobStatus.getSourceUid());
        indentingPrintWriter.print(": ");
        indentingPrintWriter.print(jobStatus.getSourcePackageName());
        indentingPrintWriter.print((jobStatus.satisfiedConstraints & 33554432) != 0 ? " RUNNABLE" : " WAITING");
        if (jobStatus.appHasDozeExemption) {
            indentingPrintWriter.print(" WHITELISTED");
        }
        if (this.mAllowInIdleJobs.contains(jobStatus)) {
            indentingPrintWriter.print(" ALLOWED_IN_DOZE");
        }
        indentingPrintWriter.println();
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(final android.util.proto.ProtoOutputStream protoOutputStream, long j, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        long start = protoOutputStream.start(j);
        long start2 = protoOutputStream.start(1146756268037L);
        protoOutputStream.write(1133871366145L, this.mDeviceIdleMode);
        this.mService.getJobStore().forEachJob(predicate, new java.util.function.Consumer() { // from class: com.android.server.job.controllers.DeviceIdleJobsController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.job.controllers.DeviceIdleJobsController.this.lambda$dumpControllerStateLocked$2(protoOutputStream, (com.android.server.job.controllers.JobStatus) obj);
            }
        });
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dumpControllerStateLocked$2(android.util.proto.ProtoOutputStream protoOutputStream, com.android.server.job.controllers.JobStatus jobStatus) {
        long start = protoOutputStream.start(2246267895810L);
        jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1120986464258L, jobStatus.getSourceUid());
        protoOutputStream.write(1138166333443L, jobStatus.getSourcePackageName());
        protoOutputStream.write(1133871366148L, (jobStatus.satisfiedConstraints & 33554432) != 0);
        protoOutputStream.write(1133871366149L, jobStatus.appHasDozeExemption);
        protoOutputStream.write(1133871366150L, this.mAllowInIdleJobs.contains(jobStatus));
        protoOutputStream.end(start);
    }

    final class DeviceIdleUpdateFunctor implements java.util.function.Consumer<com.android.server.job.controllers.JobStatus> {
        final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mChangedJobs = new android.util.ArraySet<>();
        long mUpdateTimeElapsed = 0;

        DeviceIdleUpdateFunctor() {
        }

        void prepare() {
            this.mChangedJobs.clear();
            this.mUpdateTimeElapsed = com.android.server.job.JobSchedulerService.sElapsedRealtimeClock.millis();
        }

        @Override // java.util.function.Consumer
        public void accept(com.android.server.job.controllers.JobStatus jobStatus) {
            if (com.android.server.job.controllers.DeviceIdleJobsController.this.updateTaskStateLocked(jobStatus, this.mUpdateTimeElapsed)) {
                this.mChangedJobs.add(jobStatus);
            }
        }
    }

    final class DeviceIdleJobsDelayHandler extends android.os.Handler {
        public DeviceIdleJobsDelayHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    synchronized (com.android.server.job.controllers.DeviceIdleJobsController.this.mLock) {
                        try {
                            com.android.server.job.controllers.DeviceIdleJobsController.this.mDeviceIdleUpdateFunctor.prepare();
                            com.android.server.job.controllers.DeviceIdleJobsController.this.mService.getJobStore().forEachJob(com.android.server.job.controllers.DeviceIdleJobsController.this.mDeviceIdleUpdateFunctor);
                            if (com.android.server.job.controllers.DeviceIdleJobsController.this.mDeviceIdleUpdateFunctor.mChangedJobs.size() > 0) {
                                com.android.server.job.controllers.DeviceIdleJobsController.this.mStateChangedListener.onControllerStateChanged(com.android.server.job.controllers.DeviceIdleJobsController.this.mDeviceIdleUpdateFunctor.mChangedJobs);
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
