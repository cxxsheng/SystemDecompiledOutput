package com.android.server.backup.restore;

/* loaded from: classes.dex */
public class ActiveRestoreSession extends android.app.backup.IRestoreSession.Stub {
    private static final java.lang.String DEVICE_NAME_FOR_D2D_SET = "D2D";
    private static final java.lang.String TAG = "RestoreSession";
    private final com.android.server.backup.utils.BackupEligibilityRules mBackupEligibilityRules;
    private final com.android.server.backup.UserBackupManagerService mBackupManagerService;

    @android.annotation.Nullable
    private final java.lang.String mPackageName;
    private final com.android.server.backup.TransportManager mTransportManager;
    private final java.lang.String mTransportName;
    private final int mUserId;
    public java.util.List<android.app.backup.RestoreSet> mRestoreSets = null;
    boolean mEnded = false;
    boolean mTimedOut = false;

    public ActiveRestoreSession(com.android.server.backup.UserBackupManagerService userBackupManagerService, @android.annotation.Nullable java.lang.String str, java.lang.String str2, com.android.server.backup.utils.BackupEligibilityRules backupEligibilityRules) {
        this.mBackupManagerService = userBackupManagerService;
        this.mPackageName = str;
        this.mTransportManager = userBackupManagerService.getTransportManager();
        this.mTransportName = str2;
        this.mUserId = userBackupManagerService.getUserId();
        this.mBackupEligibilityRules = backupEligibilityRules;
    }

    public void markTimedOut() {
        this.mTimedOut = true;
    }

    public synchronized int getAvailableRestoreSets(android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) {
        this.mBackupManagerService.getContext().enforceCallingOrSelfPermission("android.permission.BACKUP", "getAvailableRestoreSets");
        if (iRestoreObserver == null) {
            throw new java.lang.IllegalArgumentException("Observer must not be null");
        }
        if (this.mEnded) {
            throw new java.lang.IllegalStateException("Restore session already ended");
        }
        if (this.mTimedOut) {
            android.util.Slog.i(TAG, "Session already timed out");
            return -1;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            final com.android.server.backup.transport.TransportConnection transportClient = this.mTransportManager.getTransportClient(this.mTransportName, "RestoreSession.getAvailableRestoreSets()");
            if (transportClient == null) {
                android.util.Slog.w(TAG, "Null transport client getting restore sets");
                return -1;
            }
            this.mBackupManagerService.getBackupHandler().removeMessages(8);
            final com.android.server.backup.UserBackupManagerService.BackupWakeLock wakelock = this.mBackupManagerService.getWakelock();
            wakelock.acquire();
            final com.android.server.backup.TransportManager transportManager = this.mTransportManager;
            this.mBackupManagerService.getBackupHandler().sendMessage(this.mBackupManagerService.getBackupHandler().obtainMessage(6, new com.android.server.backup.params.RestoreGetSetsParams(transportClient, this, iRestoreObserver, iBackupManagerMonitor, new com.android.server.backup.internal.OnTaskFinishedListener() { // from class: com.android.server.backup.restore.ActiveRestoreSession$$ExternalSyntheticLambda1
                @Override // com.android.server.backup.internal.OnTaskFinishedListener
                public final void onFinished(java.lang.String str) {
                    com.android.server.backup.restore.ActiveRestoreSession.lambda$getAvailableRestoreSets$0(com.android.server.backup.TransportManager.this, transportClient, wakelock, str);
                }
            })));
            return 0;
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Error in getAvailableRestoreSets", e);
            return -1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getAvailableRestoreSets$0(com.android.server.backup.TransportManager transportManager, com.android.server.backup.transport.TransportConnection transportConnection, com.android.server.backup.UserBackupManagerService.BackupWakeLock backupWakeLock, java.lang.String str) {
        transportManager.disposeOfTransportClient(transportConnection, str);
        backupWakeLock.release();
    }

    public synchronized int restoreAll(final long j, final android.app.backup.IRestoreObserver iRestoreObserver, final android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) {
        this.mBackupManagerService.getContext().enforceCallingOrSelfPermission("android.permission.BACKUP", "performRestore");
        android.util.Slog.d(TAG, "restoreAll token=" + java.lang.Long.toHexString(j) + " observer=" + iRestoreObserver);
        if (this.mEnded) {
            throw new java.lang.IllegalStateException("Restore session already ended");
        }
        if (this.mTimedOut) {
            android.util.Slog.i(TAG, "Session already timed out");
            return -1;
        }
        if (this.mRestoreSets == null) {
            android.util.Slog.e(TAG, "Ignoring restoreAll() with no restore set");
            return -1;
        }
        if (this.mPackageName != null) {
            android.util.Slog.e(TAG, "Ignoring restoreAll() on single-package session");
            return -1;
        }
        if (!this.mTransportManager.isTransportRegistered(this.mTransportName)) {
            android.util.Slog.e(TAG, "Transport " + this.mTransportName + " not registered");
            return -1;
        }
        synchronized (this.mBackupManagerService.getQueueLock()) {
            for (int i = 0; i < this.mRestoreSets.size(); i++) {
                if (j == this.mRestoreSets.get(i).token) {
                    long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                    final android.app.backup.RestoreSet restoreSet = this.mRestoreSets.get(i);
                    try {
                        return sendRestoreToHandlerLocked(new java.util.function.BiFunction() { // from class: com.android.server.backup.restore.ActiveRestoreSession$$ExternalSyntheticLambda3
                            @Override // java.util.function.BiFunction
                            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                                com.android.server.backup.params.RestoreParams lambda$restoreAll$1;
                                lambda$restoreAll$1 = com.android.server.backup.restore.ActiveRestoreSession.this.lambda$restoreAll$1(iRestoreObserver, iBackupManagerMonitor, j, restoreSet, (com.android.server.backup.transport.TransportConnection) obj, (com.android.server.backup.internal.OnTaskFinishedListener) obj2);
                                return lambda$restoreAll$1;
                            }
                        }, "RestoreSession.restoreAll()");
                    } finally {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            }
            android.util.Slog.w(TAG, "Restore token " + java.lang.Long.toHexString(j) + " not found");
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.backup.params.RestoreParams lambda$restoreAll$1(android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, long j, android.app.backup.RestoreSet restoreSet, com.android.server.backup.transport.TransportConnection transportConnection, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener) {
        return com.android.server.backup.params.RestoreParams.createForRestoreAll(transportConnection, iRestoreObserver, iBackupManagerMonitor, j, onTaskFinishedListener, getBackupEligibilityRules(restoreSet));
    }

    public synchronized int restorePackages(final long j, @android.annotation.Nullable final android.app.backup.IRestoreObserver iRestoreObserver, @android.annotation.NonNull final java.lang.String[] strArr, @android.annotation.Nullable final android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) {
        try {
            this.mBackupManagerService.getContext().enforceCallingOrSelfPermission("android.permission.BACKUP", "performRestore");
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("restorePackages token=");
            sb.append(java.lang.Long.toHexString(j));
            sb.append(" observer=");
            if (iRestoreObserver == null) {
                sb.append("null");
            } else {
                sb.append(iRestoreObserver.toString());
            }
            sb.append(" monitor=");
            if (iBackupManagerMonitor == null) {
                sb.append("null");
            } else {
                sb.append(iBackupManagerMonitor.toString());
            }
            sb.append(" packages=");
            if (strArr == null) {
                sb.append("null");
            } else {
                sb.append('{');
                boolean z = true;
                for (java.lang.String str : strArr) {
                    if (!z) {
                        sb.append(", ");
                    } else {
                        z = false;
                    }
                    sb.append(str);
                }
                sb.append('}');
            }
            android.util.Slog.d(TAG, sb.toString());
            if (this.mEnded) {
                throw new java.lang.IllegalStateException("Restore session already ended");
            }
            if (this.mTimedOut) {
                android.util.Slog.i(TAG, "Session already timed out");
                return -1;
            }
            if (this.mRestoreSets == null) {
                android.util.Slog.e(TAG, "Ignoring restoreAll() with no restore set");
                return -1;
            }
            if (this.mPackageName != null) {
                android.util.Slog.e(TAG, "Ignoring restoreAll() on single-package session");
                return -1;
            }
            if (!this.mTransportManager.isTransportRegistered(this.mTransportName)) {
                android.util.Slog.e(TAG, "Transport " + this.mTransportName + " not registered");
                return -1;
            }
            synchronized (this.mBackupManagerService.getQueueLock()) {
                for (int i = 0; i < this.mRestoreSets.size(); i++) {
                    if (j == this.mRestoreSets.get(i).token) {
                        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                        final android.app.backup.RestoreSet restoreSet = this.mRestoreSets.get(i);
                        try {
                            return sendRestoreToHandlerLocked(new java.util.function.BiFunction() { // from class: com.android.server.backup.restore.ActiveRestoreSession$$ExternalSyntheticLambda2
                                @Override // java.util.function.BiFunction
                                public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                                    com.android.server.backup.params.RestoreParams lambda$restorePackages$2;
                                    lambda$restorePackages$2 = com.android.server.backup.restore.ActiveRestoreSession.this.lambda$restorePackages$2(iRestoreObserver, iBackupManagerMonitor, j, strArr, restoreSet, (com.android.server.backup.transport.TransportConnection) obj, (com.android.server.backup.internal.OnTaskFinishedListener) obj2);
                                    return lambda$restorePackages$2;
                                }
                            }, "RestoreSession.restorePackages(" + strArr.length + " packages)");
                        } finally {
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                }
                android.util.Slog.w(TAG, "Restore token " + java.lang.Long.toHexString(j) + " not found");
                return -1;
            }
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.backup.params.RestoreParams lambda$restorePackages$2(android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, long j, java.lang.String[] strArr, android.app.backup.RestoreSet restoreSet, com.android.server.backup.transport.TransportConnection transportConnection, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener) {
        return com.android.server.backup.params.RestoreParams.createForRestorePackages(transportConnection, iRestoreObserver, iBackupManagerMonitor, j, strArr, strArr.length > 1, onTaskFinishedListener, getBackupEligibilityRules(restoreSet));
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.backup.utils.BackupEligibilityRules getBackupEligibilityRules(android.app.backup.RestoreSet restoreSet) {
        int i = DEVICE_NAME_FOR_D2D_SET.equals(restoreSet.device) ? 1 : 0;
        if (!com.android.server.backup.Flags.enableSkippingRestoreLaunchedApps()) {
            return this.mBackupManagerService.getEligibilityRulesForOperation(i);
        }
        return new com.android.server.backup.utils.BackupEligibilityRules(this.mBackupManagerService.getPackageManager(), (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class), this.mUserId, this.mBackupManagerService.getContext(), i, (restoreSet.backupTransportFlags & 4) != 0);
    }

    public synchronized int restorePackage(java.lang.String str, final android.app.backup.IRestoreObserver iRestoreObserver, final android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) {
        android.util.Slog.v(TAG, "restorePackage pkg=" + str + " obs=" + iRestoreObserver + "monitor=" + iBackupManagerMonitor);
        if (this.mEnded) {
            throw new java.lang.IllegalStateException("Restore session already ended");
        }
        if (this.mTimedOut) {
            android.util.Slog.i(TAG, "Session already timed out");
            return -1;
        }
        if (this.mPackageName != null && !this.mPackageName.equals(str)) {
            android.util.Slog.e(TAG, "Ignoring attempt to restore pkg=" + str + " on session for package " + this.mPackageName);
            return -1;
        }
        try {
            final android.content.pm.PackageInfo packageInfoAsUser = this.mBackupManagerService.getPackageManager().getPackageInfoAsUser(str, 0, this.mUserId);
            if (this.mBackupManagerService.getContext().checkPermission("android.permission.BACKUP", android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid()) == -1 && packageInfoAsUser.applicationInfo.uid != android.os.Binder.getCallingUid()) {
                android.util.Slog.w(TAG, "restorePackage: bad packageName=" + str + " or calling uid=" + android.os.Binder.getCallingUid());
                throw new java.lang.SecurityException("No permission to restore other packages");
            }
            if (!this.mTransportManager.isTransportRegistered(this.mTransportName)) {
                android.util.Slog.e(TAG, "Transport " + this.mTransportName + " not registered");
                return -1;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                final long availableRestoreToken = this.mBackupManagerService.getAvailableRestoreToken(str);
                android.util.Slog.v(TAG, "restorePackage pkg=" + str + " token=" + java.lang.Long.toHexString(availableRestoreToken));
                if (availableRestoreToken == 0) {
                    android.util.Slog.w(TAG, "No data available for this package; not restoring");
                    return -1;
                }
                return sendRestoreToHandlerLocked(new java.util.function.BiFunction() { // from class: com.android.server.backup.restore.ActiveRestoreSession$$ExternalSyntheticLambda4
                    @Override // java.util.function.BiFunction
                    public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                        com.android.server.backup.params.RestoreParams lambda$restorePackage$3;
                        lambda$restorePackage$3 = com.android.server.backup.restore.ActiveRestoreSession.this.lambda$restorePackage$3(iRestoreObserver, iBackupManagerMonitor, availableRestoreToken, packageInfoAsUser, (com.android.server.backup.transport.TransportConnection) obj, (com.android.server.backup.internal.OnTaskFinishedListener) obj2);
                        return lambda$restorePackage$3;
                    }
                }, "RestoreSession.restorePackage(" + str + ")");
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.w(TAG, "Asked to restore nonexistent pkg " + str);
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.backup.params.RestoreParams lambda$restorePackage$3(android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, long j, android.content.pm.PackageInfo packageInfo, com.android.server.backup.transport.TransportConnection transportConnection, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener) {
        return com.android.server.backup.params.RestoreParams.createForSinglePackage(transportConnection, iRestoreObserver, iBackupManagerMonitor, j, packageInfo, onTaskFinishedListener, this.mBackupEligibilityRules);
    }

    public void setRestoreSets(java.util.List<android.app.backup.RestoreSet> list) {
        this.mRestoreSets = list;
    }

    private int sendRestoreToHandlerLocked(java.util.function.BiFunction<com.android.server.backup.transport.TransportConnection, com.android.server.backup.internal.OnTaskFinishedListener, com.android.server.backup.params.RestoreParams> biFunction, java.lang.String str) {
        final com.android.server.backup.transport.TransportConnection transportClient = this.mTransportManager.getTransportClient(this.mTransportName, str);
        if (transportClient == null) {
            android.util.Slog.e(TAG, "Transport " + this.mTransportName + " got unregistered");
            return -1;
        }
        android.os.Handler backupHandler = this.mBackupManagerService.getBackupHandler();
        backupHandler.removeMessages(8);
        final com.android.server.backup.UserBackupManagerService.BackupWakeLock wakelock = this.mBackupManagerService.getWakelock();
        wakelock.acquire();
        final com.android.server.backup.TransportManager transportManager = this.mTransportManager;
        com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener = new com.android.server.backup.internal.OnTaskFinishedListener() { // from class: com.android.server.backup.restore.ActiveRestoreSession$$ExternalSyntheticLambda0
            @Override // com.android.server.backup.internal.OnTaskFinishedListener
            public final void onFinished(java.lang.String str2) {
                com.android.server.backup.restore.ActiveRestoreSession.lambda$sendRestoreToHandlerLocked$4(com.android.server.backup.TransportManager.this, transportClient, wakelock, str2);
            }
        };
        android.os.Message obtainMessage = backupHandler.obtainMessage(3);
        obtainMessage.obj = biFunction.apply(transportClient, onTaskFinishedListener);
        backupHandler.sendMessage(obtainMessage);
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendRestoreToHandlerLocked$4(com.android.server.backup.TransportManager transportManager, com.android.server.backup.transport.TransportConnection transportConnection, com.android.server.backup.UserBackupManagerService.BackupWakeLock backupWakeLock, java.lang.String str) {
        transportManager.disposeOfTransportClient(transportConnection, str);
        backupWakeLock.release();
    }

    public class EndRestoreRunnable implements java.lang.Runnable {
        com.android.server.backup.UserBackupManagerService mBackupManager;
        com.android.server.backup.restore.ActiveRestoreSession mSession;

        public EndRestoreRunnable(com.android.server.backup.UserBackupManagerService userBackupManagerService, com.android.server.backup.restore.ActiveRestoreSession activeRestoreSession) {
            this.mBackupManager = userBackupManagerService;
            this.mSession = activeRestoreSession;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this.mSession) {
                this.mSession.mEnded = true;
            }
            this.mBackupManager.clearRestoreSession(this.mSession);
        }
    }

    public synchronized void endRestoreSession() {
        android.util.Slog.d(TAG, "endRestoreSession");
        if (this.mTimedOut) {
            android.util.Slog.i(TAG, "Session already timed out");
        } else {
            if (this.mEnded) {
                throw new java.lang.IllegalStateException("Restore session already ended");
            }
            this.mBackupManagerService.getBackupHandler().post(new com.android.server.backup.restore.ActiveRestoreSession.EndRestoreRunnable(this.mBackupManagerService, this));
        }
    }
}
