package com.android.server.rollback;

/* loaded from: classes2.dex */
final class RollbackPackageHealthObserver implements com.android.server.PackageWatchdog.PackageHealthObserver {
    private static final java.lang.String NAME = "rollback-observer";
    private static final int PERSISTENT_MASK = 9;
    private static final java.lang.String PROP_DISABLE_HIGH_IMPACT_ROLLBACK_FLAG = "persist.device_config.configuration.disable_high_impact_rollback";
    private static final java.lang.String TAG = "RollbackPackageHealthObserver";
    private final com.android.server.pm.ApexManager mApexManager;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final java.io.File mLastStagedRollbackIdsFile;
    private final java.util.Set<java.lang.Integer> mPendingStagedRollbackIds;
    private boolean mTwoPhaseRollbackEnabled;
    private final java.io.File mTwoPhaseRollbackEnabledFile;

    @com.android.internal.annotations.VisibleForTesting
    RollbackPackageHealthObserver(android.content.Context context, com.android.server.pm.ApexManager apexManager) {
        this.mPendingStagedRollbackIds = new android.util.ArraySet();
        this.mContext = context;
        android.os.HandlerThread handlerThread = new android.os.HandlerThread(TAG);
        handlerThread.start();
        this.mHandler = new android.os.Handler(handlerThread.getLooper());
        java.io.File file = new java.io.File(android.os.Environment.getDataDirectory(), NAME);
        file.mkdirs();
        this.mLastStagedRollbackIdsFile = new java.io.File(file, "last-staged-rollback-ids");
        this.mTwoPhaseRollbackEnabledFile = new java.io.File(file, "two-phase-rollback-enabled");
        com.android.server.PackageWatchdog.getInstance(this.mContext).registerHealthObserver(this);
        this.mApexManager = apexManager;
        if (android.os.SystemProperties.getBoolean("sys.boot_completed", false)) {
            this.mTwoPhaseRollbackEnabled = readBoolean(this.mTwoPhaseRollbackEnabledFile);
        } else {
            this.mTwoPhaseRollbackEnabled = false;
            writeBoolean(this.mTwoPhaseRollbackEnabledFile, false);
        }
    }

    RollbackPackageHealthObserver(android.content.Context context) {
        this(context, com.android.server.pm.ApexManager.getInstance());
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public int onHealthCheckFailed(@android.annotation.Nullable android.content.pm.VersionedPackage versionedPackage, int i, int i2) {
        if (android.crashrecovery.flags.Flags.recoverabilityDetection()) {
            java.util.List<android.content.rollback.RollbackInfo> rollbacksAvailableForImpactLevel = getRollbacksAvailableForImpactLevel(getAvailableRollbacks(), 0);
            if (rollbacksAvailableForImpactLevel.isEmpty()) {
                return 0;
            }
            return (i != 1 && getRollbackForPackage(versionedPackage, rollbacksAvailableForImpactLevel) == null) ? 70 : 30;
        }
        boolean z = !((android.content.rollback.RollbackManager) this.mContext.getSystemService(android.content.rollback.RollbackManager.class)).getAvailableRollbacks().isEmpty();
        if ((i != 1 || !z) && getAvailableRollback(versionedPackage) == null) {
            return z ? 70 : 0;
        }
        return 30;
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public boolean execute(@android.annotation.Nullable final android.content.pm.VersionedPackage versionedPackage, final int i, int i2) {
        if (android.crashrecovery.flags.Flags.recoverabilityDetection()) {
            final java.util.List<android.content.rollback.RollbackInfo> availableRollbacks = getAvailableRollbacks();
            if (i == 1) {
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.rollback.RollbackPackageHealthObserver.this.lambda$execute$0(availableRollbacks, i);
                    }
                });
                return true;
            }
            java.util.List<android.content.rollback.RollbackInfo> rollbacksAvailableForImpactLevel = getRollbacksAvailableForImpactLevel(availableRollbacks, 0);
            final android.content.rollback.RollbackInfo rollbackForPackage = getRollbackForPackage(versionedPackage, rollbacksAvailableForImpactLevel);
            if (rollbackForPackage != null) {
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.rollback.RollbackPackageHealthObserver.this.lambda$execute$1(rollbackForPackage, versionedPackage, i);
                    }
                });
            } else if (!rollbacksAvailableForImpactLevel.isEmpty()) {
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.rollback.RollbackPackageHealthObserver.this.lambda$execute$2(availableRollbacks, i);
                    }
                });
            }
        } else {
            if (i == 1) {
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.rollback.RollbackPackageHealthObserver.this.lambda$execute$3(i);
                    }
                });
                return true;
            }
            final android.content.rollback.RollbackInfo availableRollback = getAvailableRollback(versionedPackage);
            if (availableRollback != null) {
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.rollback.RollbackPackageHealthObserver.this.lambda$execute$4(availableRollback, versionedPackage, i);
                    }
                });
            } else {
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.rollback.RollbackPackageHealthObserver.this.lambda$execute$5(i);
                    }
                });
            }
        }
        return true;
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public int onBootLoop(int i) {
        if (android.crashrecovery.flags.Flags.recoverabilityDetection()) {
            java.util.List<android.content.rollback.RollbackInfo> availableRollbacks = getAvailableRollbacks();
            if (!availableRollbacks.isEmpty()) {
                return getUserImpactBasedOnRollbackImpactLevel(availableRollbacks);
            }
        }
        return 0;
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public boolean executeBootLoopMitigation(int i) {
        if (android.crashrecovery.flags.Flags.recoverabilityDetection()) {
            triggerLeastImpactLevelRollback(getAvailableRollbacks(), 5);
            return true;
        }
        return false;
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public java.lang.String getName() {
        return NAME;
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public boolean isPersistent() {
        return true;
    }

    @Override // com.android.server.PackageWatchdog.PackageHealthObserver
    public boolean mayObservePackage(java.lang.String str) {
        if (getAvailableRollbacks().isEmpty()) {
            return false;
        }
        return isPersistentSystemApp(str);
    }

    private java.util.List<android.content.rollback.RollbackInfo> getAvailableRollbacks() {
        return ((android.content.rollback.RollbackManager) this.mContext.getSystemService(android.content.rollback.RollbackManager.class)).getAvailableRollbacks();
    }

    private boolean isPersistentSystemApp(@android.annotation.NonNull java.lang.String str) {
        try {
            return (this.mContext.getPackageManager().getApplicationInfo(str, 0).flags & 9) == 9;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void assertInWorkerThread() {
        com.android.internal.util.Preconditions.checkState(this.mHandler.getLooper().isCurrentThread());
    }

    void startObservingHealth(java.util.List<java.lang.String> list, long j) {
        com.android.server.PackageWatchdog.getInstance(this.mContext).startObservingHealth(this, list, j);
    }

    void notifyRollbackAvailable(final android.content.rollback.RollbackInfo rollbackInfo) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.rollback.RollbackPackageHealthObserver.this.lambda$notifyRollbackAvailable$6(rollbackInfo);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyRollbackAvailable$6(android.content.rollback.RollbackInfo rollbackInfo) {
        if (isRebootlessApex(rollbackInfo)) {
            this.mTwoPhaseRollbackEnabled = true;
            writeBoolean(this.mTwoPhaseRollbackEnabledFile, true);
        }
    }

    private static boolean isRebootlessApex(android.content.rollback.RollbackInfo rollbackInfo) {
        if (!rollbackInfo.isStaged()) {
            java.util.Iterator it = rollbackInfo.getPackages().iterator();
            while (it.hasNext()) {
                if (((android.content.rollback.PackageRollbackInfo) it.next()).isApex()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    void onBootCompletedAsync() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.rollback.RollbackPackageHealthObserver.this.lambda$onBootCompletedAsync$7();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onBootCompleted, reason: merged with bridge method [inline-methods] */
    public void lambda$onBootCompletedAsync$7() {
        assertInWorkerThread();
        android.content.rollback.RollbackManager rollbackManager = (android.content.rollback.RollbackManager) this.mContext.getSystemService(android.content.rollback.RollbackManager.class);
        if (!rollbackManager.getAvailableRollbacks().isEmpty()) {
            com.android.server.PackageWatchdog.getInstance(this.mContext).scheduleCheckAndMitigateNativeCrashes();
        }
        android.util.SparseArray<java.lang.String> popLastStagedRollbackIds = popLastStagedRollbackIds();
        for (int i = 0; i < popLastStagedRollbackIds.size(); i++) {
            com.android.server.rollback.WatchdogRollbackLogger.logRollbackStatusOnBoot(this.mContext, popLastStagedRollbackIds.keyAt(i), popLastStagedRollbackIds.valueAt(i), rollbackManager.getRecentlyCommittedRollbacks());
        }
    }

    private android.content.rollback.RollbackInfo getAvailableRollback(android.content.pm.VersionedPackage versionedPackage) {
        for (android.content.rollback.RollbackInfo rollbackInfo : ((android.content.rollback.RollbackManager) this.mContext.getSystemService(android.content.rollback.RollbackManager.class)).getAvailableRollbacks()) {
            for (android.content.rollback.PackageRollbackInfo packageRollbackInfo : rollbackInfo.getPackages()) {
                if (packageRollbackInfo.getVersionRolledBackFrom().equals(versionedPackage)) {
                    return rollbackInfo;
                }
                if (packageRollbackInfo.isApkInApex() && packageRollbackInfo.getVersionRolledBackFrom().getPackageName().equals(versionedPackage.getPackageName())) {
                    return rollbackInfo;
                }
            }
        }
        return null;
    }

    private android.content.rollback.RollbackInfo getRollbackForPackage(@android.annotation.Nullable android.content.pm.VersionedPackage versionedPackage, java.util.List<android.content.rollback.RollbackInfo> list) {
        if (versionedPackage == null) {
            return null;
        }
        for (android.content.rollback.RollbackInfo rollbackInfo : list) {
            for (android.content.rollback.PackageRollbackInfo packageRollbackInfo : rollbackInfo.getPackages()) {
                if (packageRollbackInfo.getVersionRolledBackFrom().equals(versionedPackage)) {
                    return rollbackInfo;
                }
                if (packageRollbackInfo.isApkInApex() && packageRollbackInfo.getVersionRolledBackFrom().getPackageName().equals(versionedPackage.getPackageName())) {
                    return rollbackInfo;
                }
            }
        }
        return null;
    }

    private boolean markStagedSessionHandled(int i) {
        assertInWorkerThread();
        return this.mPendingStagedRollbackIds.remove(java.lang.Integer.valueOf(i));
    }

    private boolean isPendingStagedSessionsEmpty() {
        assertInWorkerThread();
        return this.mPendingStagedRollbackIds.isEmpty();
    }

    private static boolean readBoolean(java.io.File file) {
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
            try {
                boolean z = fileInputStream.read() == 1;
                fileInputStream.close();
                return z;
            } finally {
            }
        } catch (java.io.IOException e) {
            return false;
        }
    }

    private static void writeBoolean(java.io.File file, boolean z) {
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file);
            try {
                fileOutputStream.write(z ? 1 : 0);
                fileOutputStream.flush();
                android.os.FileUtils.sync(fileOutputStream);
                fileOutputStream.close();
            } finally {
            }
        } catch (java.io.IOException e) {
        }
    }

    private void saveStagedRollbackId(int i, @android.annotation.Nullable android.content.pm.VersionedPackage versionedPackage) {
        assertInWorkerThread();
        writeStagedRollbackId(this.mLastStagedRollbackIdsFile, i, versionedPackage);
    }

    static void writeStagedRollbackId(java.io.File file, int i, @android.annotation.Nullable android.content.pm.VersionedPackage versionedPackage) {
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(file, true);
            java.io.PrintWriter printWriter = new java.io.PrintWriter(fileOutputStream);
            printWriter.append((java.lang.CharSequence) java.lang.String.valueOf(i)).append((java.lang.CharSequence) ",").append((java.lang.CharSequence) (versionedPackage != null ? versionedPackage.getPackageName() : ""));
            printWriter.println();
            printWriter.flush();
            android.os.FileUtils.sync(fileOutputStream);
            printWriter.close();
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Failed to save last staged rollback id", e);
            file.delete();
        }
    }

    private android.util.SparseArray<java.lang.String> popLastStagedRollbackIds() {
        assertInWorkerThread();
        try {
            return readStagedRollbackIds(this.mLastStagedRollbackIdsFile);
        } finally {
            this.mLastStagedRollbackIdsFile.delete();
        }
    }

    static android.util.SparseArray<java.lang.String> readStagedRollbackIds(java.io.File file) {
        android.util.SparseArray<java.lang.String> sparseArray = new android.util.SparseArray<>();
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(file));
            while (true) {
                java.lang.String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    java.lang.String[] split = readLine.trim().split(",");
                    java.lang.String str = split[0];
                    java.lang.String str2 = "";
                    if (split.length > 1) {
                        str2 = split[1];
                    }
                    sparseArray.put(java.lang.Integer.parseInt(str), str2);
                } else {
                    return sparseArray;
                }
            }
        } catch (java.lang.Exception e) {
            return new android.util.SparseArray<>();
        }
    }

    private boolean isModule(java.lang.String str) {
        java.lang.String activeApexPackageNameContainingPackage = this.mApexManager.getActiveApexPackageNameContainingPackage(str);
        if (activeApexPackageNameContainingPackage != null) {
            str = activeApexPackageNameContainingPackage;
        }
        try {
            return this.mContext.getPackageManager().getModuleInfo(str, 0) != null;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: rollbackPackage, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$execute$4(final android.content.rollback.RollbackInfo rollbackInfo, android.content.pm.VersionedPackage versionedPackage, int i) {
        final java.lang.String packageName;
        final android.content.pm.VersionedPackage versionedPackage2;
        assertInWorkerThread();
        android.content.rollback.RollbackManager rollbackManager = (android.content.rollback.RollbackManager) this.mContext.getSystemService(android.content.rollback.RollbackManager.class);
        final int mapFailureReasonToMetric = com.android.server.rollback.WatchdogRollbackLogger.mapFailureReasonToMetric(i);
        if (i == 1) {
            packageName = android.os.SystemProperties.get("sys.init.updatable_crashing_process_name", "");
        } else {
            packageName = versionedPackage.getPackageName();
        }
        if (!isModule(versionedPackage.getPackageName())) {
            versionedPackage2 = null;
        } else {
            versionedPackage2 = com.android.server.rollback.WatchdogRollbackLogger.getLogPackage(this.mContext, versionedPackage);
        }
        com.android.server.rollback.WatchdogRollbackLogger.logEvent(versionedPackage2, 1, mapFailureReasonToMetric, packageName);
        final java.util.function.Consumer consumer = new java.util.function.Consumer() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.rollback.RollbackPackageHealthObserver.this.lambda$rollbackPackage$8(rollbackInfo, versionedPackage2, mapFailureReasonToMetric, packageName, (android.content.Intent) obj);
            }
        };
        rollbackManager.commitRollback(rollbackInfo.getRollbackId(), java.util.Collections.singletonList(versionedPackage), new com.android.server.rollback.LocalIntentReceiver(new java.util.function.Consumer() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.rollback.RollbackPackageHealthObserver.this.lambda$rollbackPackage$10(consumer, (android.content.Intent) obj);
            }
        }).getIntentSender());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rollbackPackage$8(android.content.rollback.RollbackInfo rollbackInfo, android.content.pm.VersionedPackage versionedPackage, int i, java.lang.String str, android.content.Intent intent) {
        assertInWorkerThread();
        if (intent.getIntExtra("android.content.rollback.extra.STATUS", 1) == 0) {
            if (rollbackInfo.isStaged()) {
                saveStagedRollbackId(rollbackInfo.getRollbackId(), versionedPackage);
                com.android.server.rollback.WatchdogRollbackLogger.logEvent(versionedPackage, 4, i, str);
            } else {
                com.android.server.rollback.WatchdogRollbackLogger.logEvent(versionedPackage, 2, i, str);
            }
        } else {
            com.android.server.rollback.WatchdogRollbackLogger.logEvent(versionedPackage, 3, i, str);
        }
        if (rollbackInfo.isStaged()) {
            markStagedSessionHandled(rollbackInfo.getRollbackId());
            if (isPendingStagedSessionsEmpty()) {
                android.sysprop.CrashRecoveryProperties.attemptingReboot(true);
                ((android.os.PowerManager) this.mContext.getSystemService(android.os.PowerManager.class)).reboot("Rollback staged install");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rollbackPackage$10(final java.util.function.Consumer consumer, final android.content.Intent intent) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                consumer.accept(intent);
            }
        });
    }

    private boolean useTwoPhaseRollback(java.util.List<android.content.rollback.RollbackInfo> list) {
        assertInWorkerThread();
        if (!this.mTwoPhaseRollbackEnabled) {
            return false;
        }
        android.util.Slog.i(TAG, "Rolling back all rebootless APEX rollbacks");
        boolean z = false;
        for (android.content.rollback.RollbackInfo rollbackInfo : list) {
            if (isRebootlessApex(rollbackInfo)) {
                lambda$execute$4(rollbackInfo, ((android.content.rollback.PackageRollbackInfo) rollbackInfo.getPackages().get(0)).getVersionRolledBackFrom(), 1);
                z = true;
            }
        }
        return z;
    }

    private void triggerLeastImpactLevelRollback(final java.util.List<android.content.rollback.RollbackInfo> list, final int i) {
        int minRollbackImpactLevel = getMinRollbackImpactLevel(list);
        if (minRollbackImpactLevel == 0) {
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.rollback.RollbackPackageHealthObserver.this.lambda$triggerLeastImpactLevelRollback$11(list, i);
                }
            });
        } else {
            if (minRollbackImpactLevel != 1 || android.os.SystemProperties.getBoolean(PROP_DISABLE_HIGH_IMPACT_ROLLBACK_FLAG, false)) {
                return;
            }
            this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.rollback.RollbackPackageHealthObserver.this.lambda$triggerLeastImpactLevelRollback$12(list, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: rollbackHighImpact, reason: merged with bridge method [inline-methods] */
    public void lambda$triggerLeastImpactLevelRollback$12(java.util.List<android.content.rollback.RollbackInfo> list, int i) {
        assertInWorkerThread();
        java.util.List<android.content.rollback.RollbackInfo> list2 = getRollbacksAvailableForImpactLevel(list, 1).stream().sorted(java.util.Comparator.comparing(new java.util.function.Function() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String lambda$rollbackHighImpact$13;
                lambda$rollbackHighImpact$13 = com.android.server.rollback.RollbackPackageHealthObserver.lambda$rollbackHighImpact$13((android.content.rollback.RollbackInfo) obj);
                return lambda$rollbackHighImpact$13;
            }
        })).toList();
        lambda$execute$4(list2.get(0), ((android.content.rollback.PackageRollbackInfo) list2.get(0).getPackages().get(0)).getVersionRolledBackFrom(), i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.String lambda$rollbackHighImpact$13(android.content.rollback.RollbackInfo rollbackInfo) {
        return ((android.content.rollback.PackageRollbackInfo) rollbackInfo.getPackages().get(0)).getPackageName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: rollbackAll, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$execute$5(int i) {
        assertInWorkerThread();
        java.util.List<android.content.rollback.RollbackInfo> availableRollbacks = ((android.content.rollback.RollbackManager) this.mContext.getSystemService(android.content.rollback.RollbackManager.class)).getAvailableRollbacks();
        if (useTwoPhaseRollback(availableRollbacks)) {
            return;
        }
        android.util.Slog.i(TAG, "Rolling back all available rollbacks");
        for (android.content.rollback.RollbackInfo rollbackInfo : availableRollbacks) {
            if (rollbackInfo.isStaged()) {
                this.mPendingStagedRollbackIds.add(java.lang.Integer.valueOf(rollbackInfo.getRollbackId()));
            }
        }
        for (android.content.rollback.RollbackInfo rollbackInfo2 : availableRollbacks) {
            lambda$execute$4(rollbackInfo2, ((android.content.rollback.PackageRollbackInfo) rollbackInfo2.getPackages().get(0)).getVersionRolledBackFrom(), i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: rollbackAllLowImpact, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$triggerLeastImpactLevelRollback$11(java.util.List<android.content.rollback.RollbackInfo> list, int i) {
        assertInWorkerThread();
        java.util.List<android.content.rollback.RollbackInfo> rollbacksAvailableForImpactLevel = getRollbacksAvailableForImpactLevel(list, 0);
        if (useTwoPhaseRollback(rollbacksAvailableForImpactLevel)) {
            return;
        }
        android.util.Slog.i(TAG, "Rolling back all available low impact rollbacks");
        for (android.content.rollback.RollbackInfo rollbackInfo : rollbacksAvailableForImpactLevel) {
            if (rollbackInfo.isStaged()) {
                this.mPendingStagedRollbackIds.add(java.lang.Integer.valueOf(rollbackInfo.getRollbackId()));
            }
        }
        for (android.content.rollback.RollbackInfo rollbackInfo2 : rollbacksAvailableForImpactLevel) {
            lambda$execute$4(rollbackInfo2, ((android.content.rollback.PackageRollbackInfo) rollbackInfo2.getPackages().get(0)).getVersionRolledBackFrom(), i);
        }
    }

    private java.util.List<android.content.rollback.RollbackInfo> getRollbacksAvailableForImpactLevel(java.util.List<android.content.rollback.RollbackInfo> list, final int i) {
        return list.stream().filter(new java.util.function.Predicate() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getRollbacksAvailableForImpactLevel$14;
                lambda$getRollbacksAvailableForImpactLevel$14 = com.android.server.rollback.RollbackPackageHealthObserver.lambda$getRollbacksAvailableForImpactLevel$14(i, (android.content.rollback.RollbackInfo) obj);
                return lambda$getRollbacksAvailableForImpactLevel$14;
            }
        }).toList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getRollbacksAvailableForImpactLevel$14(int i, android.content.rollback.RollbackInfo rollbackInfo) {
        return rollbackInfo.getRollbackImpactLevel() == i;
    }

    private int getMinRollbackImpactLevel(java.util.List<android.content.rollback.RollbackInfo> list) {
        return list.stream().mapToInt(new java.util.function.ToIntFunction() { // from class: com.android.server.rollback.RollbackPackageHealthObserver$$ExternalSyntheticLambda7
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                return ((android.content.rollback.RollbackInfo) obj).getRollbackImpactLevel();
            }
        }).min().orElse(-1);
    }

    private int getUserImpactBasedOnRollbackImpactLevel(java.util.List<android.content.rollback.RollbackInfo> list) {
        switch (getMinRollbackImpactLevel(list)) {
            case 1:
                if (!android.os.SystemProperties.getBoolean(PROP_DISABLE_HIGH_IMPACT_ROLLBACK_FLAG, false)) {
                }
                break;
        }
        return 0;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.os.Handler getHandler() {
        return this.mHandler;
    }
}
