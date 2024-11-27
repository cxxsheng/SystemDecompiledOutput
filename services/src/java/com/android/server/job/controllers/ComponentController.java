package com.android.server.job.controllers;

/* loaded from: classes2.dex */
public class ComponentController extends com.android.server.job.controllers.StateController {
    private static final boolean DEBUG;
    private static final java.lang.String TAG = "JobScheduler.Component";
    private final android.content.BroadcastReceiver mBroadcastReceiver;
    private final com.android.server.job.controllers.ComponentController.ComponentStateUpdateFunctor mComponentStateUpdateFunctor;
    private final android.util.SparseArrayMap<android.content.ComponentName, java.lang.String> mServiceProcessCache;

    static {
        DEBUG = com.android.server.job.JobSchedulerService.DEBUG || android.util.Log.isLoggable(TAG, 3);
    }

    public ComponentController(com.android.server.job.JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mBroadcastReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.job.controllers.ComponentController.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                char c;
                java.lang.String schemeSpecificPart;
                java.lang.String action = intent.getAction();
                if (action == null) {
                    android.util.Slog.wtf(com.android.server.job.controllers.ComponentController.TAG, "Intent action was null");
                }
                switch (action.hashCode()) {
                    case -742246786:
                        if (action.equals("android.intent.action.USER_STOPPED")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 172491798:
                        if (action.equals("android.intent.action.PACKAGE_CHANGED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 833559602:
                        if (action.equals("android.intent.action.USER_UNLOCKED")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1544582882:
                        if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                            c = 0;
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
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            android.net.Uri data = intent.getData();
                            schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                            if (schemeSpecificPart != null) {
                                com.android.server.job.controllers.ComponentController.this.updateComponentStateForPackage(android.os.UserHandle.getUserId(intent.getIntExtra("android.intent.extra.UID", -1)), schemeSpecificPart);
                                break;
                            }
                        }
                        break;
                    case 1:
                        android.net.Uri data2 = intent.getData();
                        schemeSpecificPart = data2 != null ? data2.getSchemeSpecificPart() : null;
                        java.lang.String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_component_name_list");
                        if (schemeSpecificPart != null && stringArrayExtra != null && stringArrayExtra.length > 0) {
                            com.android.server.job.controllers.ComponentController.this.updateComponentStateForPackage(android.os.UserHandle.getUserId(intent.getIntExtra("android.intent.extra.UID", -1)), schemeSpecificPart);
                            break;
                        }
                        break;
                    case 2:
                    case 3:
                        com.android.server.job.controllers.ComponentController.this.updateComponentStateForUser(intent.getIntExtra("android.intent.extra.user_handle", 0));
                        break;
                }
            }
        };
        this.mServiceProcessCache = new android.util.SparseArrayMap<>();
        this.mComponentStateUpdateFunctor = new com.android.server.job.controllers.ComponentController.ComponentStateUpdateFunctor();
    }

    @Override // com.android.server.job.controllers.StateController
    public void startTrackingLocked() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter, null, null);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter2.addAction("android.intent.action.USER_STOPPED");
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, android.os.UserHandle.ALL, intentFilter2, null, null);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void maybeStartTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
        updateComponentEnabledStateLocked(jobStatus);
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStopTrackingJobLocked(com.android.server.job.controllers.JobStatus jobStatus, com.android.server.job.controllers.JobStatus jobStatus2) {
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onAppRemovedLocked(java.lang.String str, int i) {
        clearComponentsForPackageLocked(android.os.UserHandle.getUserId(i), str);
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onUserRemovedLocked(int i) {
        this.mServiceProcessCache.delete(i);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    @android.annotation.Nullable
    private java.lang.String getServiceProcessLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        android.content.pm.ServiceInfo serviceInfo;
        android.content.ComponentName serviceComponent = jobStatus.getServiceComponent();
        int userId = jobStatus.getUserId();
        if (this.mServiceProcessCache.contains(userId, serviceComponent)) {
            return (java.lang.String) this.mServiceProcessCache.get(userId, serviceComponent);
        }
        try {
            serviceInfo = this.mContext.createContextAsUser(android.os.UserHandle.of(userId), 0).getPackageManager().getServiceInfo(serviceComponent, 268435456);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            if (this.mService.areUsersStartedLocked(jobStatus)) {
                android.util.Slog.e(TAG, "Job exists for non-existent package: " + serviceComponent.getPackageName());
            }
            serviceInfo = null;
        }
        java.lang.String str = serviceInfo != null ? serviceInfo.processName : null;
        this.mServiceProcessCache.add(userId, serviceComponent, str);
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public boolean updateComponentEnabledStateLocked(com.android.server.job.controllers.JobStatus jobStatus) {
        java.lang.String serviceProcessLocked = getServiceProcessLocked(jobStatus);
        if (DEBUG && serviceProcessLocked == null) {
            android.util.Slog.v(TAG, jobStatus.toShortString() + " component not present");
        }
        java.lang.String str = jobStatus.serviceProcessName;
        jobStatus.serviceProcessName = serviceProcessLocked;
        return !java.util.Objects.equals(str, serviceProcessLocked);
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void clearComponentsForPackageLocked(int i, java.lang.String str) {
        int indexOfKey = this.mServiceProcessCache.indexOfKey(i);
        for (int numElementsForKey = this.mServiceProcessCache.numElementsForKey(i) - 1; numElementsForKey >= 0; numElementsForKey--) {
            android.content.ComponentName componentName = (android.content.ComponentName) this.mServiceProcessCache.keyAt(indexOfKey, numElementsForKey);
            if (componentName.getPackageName().equals(str)) {
                this.mServiceProcessCache.delete(i, componentName);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateComponentStateForPackage(final int i, final java.lang.String str) {
        synchronized (this.mLock) {
            clearComponentsForPackageLocked(i, str);
            updateComponentStatesLocked(new java.util.function.Predicate() { // from class: com.android.server.job.controllers.ComponentController$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$updateComponentStateForPackage$0;
                    lambda$updateComponentStateForPackage$0 = com.android.server.job.controllers.ComponentController.lambda$updateComponentStateForPackage$0(i, str, (com.android.server.job.controllers.JobStatus) obj);
                    return lambda$updateComponentStateForPackage$0;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateComponentStateForPackage$0(int i, java.lang.String str, com.android.server.job.controllers.JobStatus jobStatus) {
        return jobStatus.getUserId() == i && jobStatus.getServiceComponent().getPackageName().equals(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateComponentStateForUser(final int i) {
        synchronized (this.mLock) {
            this.mServiceProcessCache.delete(i);
            updateComponentStatesLocked(new java.util.function.Predicate() { // from class: com.android.server.job.controllers.ComponentController$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$updateComponentStateForUser$1;
                    lambda$updateComponentStateForUser$1 = com.android.server.job.controllers.ComponentController.lambda$updateComponentStateForUser$1(i, (com.android.server.job.controllers.JobStatus) obj);
                    return lambda$updateComponentStateForUser$1;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateComponentStateForUser$1(int i, com.android.server.job.controllers.JobStatus jobStatus) {
        return jobStatus.getUserId() == i;
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void updateComponentStatesLocked(@android.annotation.NonNull java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        this.mComponentStateUpdateFunctor.reset();
        this.mService.getJobStore().forEachJob(predicate, this.mComponentStateUpdateFunctor);
        if (this.mComponentStateUpdateFunctor.mChangedJobs.size() > 0) {
            this.mStateChangedListener.onControllerStateChanged(this.mComponentStateUpdateFunctor.mChangedJobs);
        }
    }

    final class ComponentStateUpdateFunctor implements java.util.function.Consumer<com.android.server.job.controllers.JobStatus> {

        @com.android.internal.annotations.GuardedBy({"mLock"})
        final android.util.ArraySet<com.android.server.job.controllers.JobStatus> mChangedJobs = new android.util.ArraySet<>();

        ComponentStateUpdateFunctor() {
        }

        @Override // java.util.function.Consumer
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void accept(com.android.server.job.controllers.JobStatus jobStatus) {
            if (com.android.server.job.controllers.ComponentController.this.updateComponentEnabledStateLocked(jobStatus)) {
                this.mChangedJobs.add(jobStatus);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mLock"})
        public void reset() {
            this.mChangedJobs.clear();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void dumpControllerStateLocked(android.util.IndentingPrintWriter indentingPrintWriter, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
        for (int i = 0; i < this.mServiceProcessCache.numMaps(); i++) {
            int keyAt = this.mServiceProcessCache.keyAt(i);
            for (int i2 = 0; i2 < this.mServiceProcessCache.numElementsForKey(keyAt); i2++) {
                android.content.ComponentName componentName = (android.content.ComponentName) this.mServiceProcessCache.keyAt(i, i2);
                indentingPrintWriter.print(keyAt);
                indentingPrintWriter.print("-");
                indentingPrintWriter.print(componentName);
                indentingPrintWriter.print(": ");
                indentingPrintWriter.print((java.lang.String) this.mServiceProcessCache.valueAt(i, i2));
                indentingPrintWriter.println();
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void dumpControllerStateLocked(android.util.proto.ProtoOutputStream protoOutputStream, long j, java.util.function.Predicate<com.android.server.job.controllers.JobStatus> predicate) {
    }
}
