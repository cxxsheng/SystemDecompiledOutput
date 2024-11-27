package com.android.server.backup.internal;

import com.android.server.backup.restore.ActiveRestoreSession.EndRestoreRunnable;

/* loaded from: classes.dex */
public class BackupHandler extends android.os.Handler {
    public static final int MSG_BACKUP_OPERATION_TIMEOUT = 17;
    public static final int MSG_BACKUP_RESTORE_STEP = 20;
    public static final int MSG_FULL_CONFIRMATION_TIMEOUT = 9;
    public static final int MSG_OP_COMPLETE = 21;
    public static final int MSG_REQUEST_BACKUP = 15;
    public static final int MSG_RESTORE_OPERATION_TIMEOUT = 18;
    public static final int MSG_RESTORE_SESSION_TIMEOUT = 8;
    public static final int MSG_RETRY_CLEAR = 12;
    public static final int MSG_RUN_ADB_BACKUP = 2;
    public static final int MSG_RUN_ADB_RESTORE = 10;
    public static final int MSG_RUN_BACKUP = 1;
    public static final int MSG_RUN_CLEAR = 4;
    public static final int MSG_RUN_GET_RESTORE_SETS = 6;
    public static final int MSG_RUN_RESTORE = 3;
    public static final int MSG_SCHEDULE_BACKUP_PACKAGE = 16;
    public static final int MSG_STOP = 22;
    private final com.android.server.backup.UserBackupManagerService backupManagerService;
    private final com.android.server.backup.BackupAgentTimeoutParameters mAgentTimeoutParameters;
    private final android.os.HandlerThread mBackupThread;

    @com.android.internal.annotations.VisibleForTesting
    volatile boolean mIsStopping;
    private final com.android.server.backup.OperationStorage mOperationStorage;

    public BackupHandler(com.android.server.backup.UserBackupManagerService userBackupManagerService, com.android.server.backup.OperationStorage operationStorage, android.os.HandlerThread handlerThread) {
        super(handlerThread.getLooper());
        this.mIsStopping = false;
        this.mBackupThread = handlerThread;
        this.backupManagerService = userBackupManagerService;
        this.mOperationStorage = operationStorage;
        com.android.server.backup.BackupAgentTimeoutParameters agentTimeoutParameters = userBackupManagerService.getAgentTimeoutParameters();
        java.util.Objects.requireNonNull(agentTimeoutParameters, "Timeout parameters cannot be null");
        this.mAgentTimeoutParameters = agentTimeoutParameters;
    }

    public void stop() {
        this.mIsStopping = true;
        sendMessage(obtainMessage(22));
    }

    @Override // android.os.Handler
    public void dispatchMessage(android.os.Message message) {
        try {
            dispatchMessageInternal(message);
        } catch (java.lang.Exception e) {
            if (!this.mIsStopping) {
                throw e;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void dispatchMessageInternal(android.os.Message message) {
        super.dispatchMessage(message);
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x028c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:175:0x04af  */
    /* JADX WARN: Removed duplicated region for block: B:186:? A[RETURN, SYNTHETIC] */
    @Override // android.os.Handler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleMessage(android.os.Message message) {
        android.app.backup.IBackupManagerMonitor iBackupManagerMonitor;
        com.android.server.backup.transport.TransportConnection transportConnection;
        java.lang.Throwable th;
        java.util.List<android.app.backup.RestoreSet> list;
        java.lang.String str;
        java.lang.StringBuilder sb;
        if (message.what == 22) {
            android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, "Stopping backup handler");
            this.backupManagerService.getWakelock().quit();
            this.mBackupThread.quitSafely();
        }
        if (this.mIsStopping) {
            return;
        }
        final com.android.server.backup.TransportManager transportManager = this.backupManagerService.getTransportManager();
        boolean z = true;
        java.util.List list2 = null;
        switch (message.what) {
            case 1:
                this.backupManagerService.setLastBackupPass(java.lang.System.currentTimeMillis());
                final com.android.server.backup.transport.TransportConnection currentTransportClient = transportManager.getCurrentTransportClient("BH/MSG_RUN_BACKUP");
                com.android.server.backup.transport.BackupTransportClient connect = currentTransportClient != null ? currentTransportClient.connect("BH/MSG_RUN_BACKUP") : null;
                if (connect == null) {
                    if (currentTransportClient != null) {
                        transportManager.disposeOfTransportClient(currentTransportClient, "BH/MSG_RUN_BACKUP");
                    }
                    android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, "Backup requested but no transport available");
                    return;
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                com.android.server.backup.DataChangedJournal journal = this.backupManagerService.getJournal();
                synchronized (this.backupManagerService.getQueueLock()) {
                    try {
                        if (this.backupManagerService.isBackupRunning()) {
                            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Backup time but one already running");
                            return;
                        }
                        android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, "Running a backup pass");
                        this.backupManagerService.setBackupRunning(true);
                        this.backupManagerService.getWakelock().acquire();
                        if (this.backupManagerService.getPendingBackups().size() > 0) {
                            java.util.Iterator<com.android.server.backup.keyvalue.BackupRequest> it = this.backupManagerService.getPendingBackups().values().iterator();
                            while (it.hasNext()) {
                                arrayList.add(it.next().packageName);
                            }
                            android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, "clearing pending backups");
                            this.backupManagerService.getPendingBackups().clear();
                            this.backupManagerService.setJournal(null);
                        }
                        try {
                            iBackupManagerMonitor = connect.getBackupManagerMonitor();
                        } catch (android.os.RemoteException e) {
                            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Failed to retrieve monitor from transport");
                            iBackupManagerMonitor = null;
                        }
                        if (arrayList.size() > 0) {
                            try {
                                transportConnection = currentTransportClient;
                                try {
                                    com.android.server.backup.keyvalue.KeyValueBackupTask.start(this.backupManagerService, this.mOperationStorage, currentTransportClient, connect.transportDirName(), arrayList, journal, null, iBackupManagerMonitor, new com.android.server.backup.internal.OnTaskFinishedListener() { // from class: com.android.server.backup.internal.BackupHandler$$ExternalSyntheticLambda0
                                        @Override // com.android.server.backup.internal.OnTaskFinishedListener
                                        public final void onFinished(java.lang.String str2) {
                                            com.android.server.backup.TransportManager.this.disposeOfTransportClient(currentTransportClient, str2);
                                        }
                                    }, java.util.Collections.emptyList(), false, false, this.backupManagerService.getEligibilityRulesForOperation(0));
                                } catch (java.lang.Exception e2) {
                                    e = e2;
                                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Transport became unavailable attempting backup or error initializing backup task", e);
                                    z = false;
                                    if (z) {
                                    }
                                }
                            } catch (java.lang.Exception e3) {
                                e = e3;
                                transportConnection = currentTransportClient;
                            }
                        } else {
                            transportConnection = currentTransportClient;
                            android.util.Slog.v(com.android.server.backup.BackupManagerService.TAG, "Backup requested but nothing pending");
                            z = false;
                        }
                        if (z) {
                            transportManager.disposeOfTransportClient(transportConnection, "BH/MSG_RUN_BACKUP");
                            synchronized (this.backupManagerService.getQueueLock()) {
                                this.backupManagerService.setBackupRunning(false);
                            }
                            this.backupManagerService.getWakelock().release();
                            return;
                        }
                        return;
                    } finally {
                    }
                }
            case 2:
                com.android.server.backup.params.AdbBackupParams adbBackupParams = (com.android.server.backup.params.AdbBackupParams) message.obj;
                new java.lang.Thread(new com.android.server.backup.fullbackup.PerformAdbBackupTask(this.backupManagerService, this.mOperationStorage, adbBackupParams.fd, adbBackupParams.observer, adbBackupParams.includeApks, adbBackupParams.includeObbs, adbBackupParams.includeShared, adbBackupParams.doWidgets, adbBackupParams.curPassword, adbBackupParams.encryptPassword, adbBackupParams.allApps, adbBackupParams.includeSystem, adbBackupParams.doCompress, adbBackupParams.includeKeyValue, adbBackupParams.packages, adbBackupParams.latch, adbBackupParams.backupEligibilityRules), "adb-backup").start();
                return;
            case 3:
                com.android.server.backup.params.RestoreParams restoreParams = (com.android.server.backup.params.RestoreParams) message.obj;
                android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "MSG_RUN_RESTORE observer=" + restoreParams.observer);
                com.android.server.backup.restore.PerformUnifiedRestoreTask performUnifiedRestoreTask = new com.android.server.backup.restore.PerformUnifiedRestoreTask(this.backupManagerService, this.mOperationStorage, restoreParams.mTransportConnection, restoreParams.observer, restoreParams.monitor, restoreParams.token, restoreParams.packageInfo, restoreParams.pmToken, restoreParams.isSystemRestore, restoreParams.filterSet, restoreParams.listener, restoreParams.backupEligibilityRules);
                synchronized (this.backupManagerService.getPendingRestores()) {
                    try {
                        if (this.backupManagerService.isRestoreInProgress()) {
                            android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Restore in progress, queueing.");
                            this.backupManagerService.getPendingRestores().add(performUnifiedRestoreTask);
                        } else {
                            android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Starting restore.");
                            this.backupManagerService.setRestoreInProgress(true);
                            sendMessage(obtainMessage(20, performUnifiedRestoreTask));
                        }
                    } finally {
                    }
                }
                return;
            case 4:
                com.android.server.backup.params.ClearParams clearParams = (com.android.server.backup.params.ClearParams) message.obj;
                new com.android.server.backup.internal.PerformClearTask(this.backupManagerService, clearParams.mTransportConnection, clearParams.packageInfo, clearParams.listener).run();
                return;
            case 5:
            case 7:
            case 11:
            case 13:
            case 14:
            case 19:
            default:
                return;
            case 6:
                com.android.server.backup.params.RestoreGetSetsParams restoreGetSetsParams = (com.android.server.backup.params.RestoreGetSetsParams) message.obj;
                try {
                    try {
                        list = restoreGetSetsParams.mTransportConnection.connectOrThrow("BH/MSG_RUN_GET_RESTORE_SETS").getAvailableRestoreSets();
                    } catch (java.lang.Exception e4) {
                        e = e4;
                        list = null;
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        if (restoreGetSetsParams.observer != null) {
                        }
                        removeMessages(8);
                        sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                        restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
                        throw th;
                    }
                    try {
                        synchronized (restoreGetSetsParams.session) {
                            restoreGetSetsParams.session.setRestoreSets(list);
                        }
                        if (list == null) {
                            android.util.EventLog.writeEvent(com.android.server.EventLogTags.RESTORE_TRANSPORT_FAILURE, new java.lang.Object[0]);
                        }
                        if (restoreGetSetsParams.observer != null) {
                            try {
                                if (list == null) {
                                    restoreGetSetsParams.observer.restoreSetsAvailable((android.app.backup.RestoreSet[]) null);
                                } else {
                                    restoreGetSetsParams.observer.restoreSetsAvailable((android.app.backup.RestoreSet[]) list.toArray(new android.app.backup.RestoreSet[0]));
                                }
                            } catch (android.os.RemoteException e5) {
                                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unable to report listing to observer");
                            } catch (java.lang.Exception e6) {
                                e = e6;
                                str = com.android.server.backup.BackupManagerService.TAG;
                                sb = new java.lang.StringBuilder();
                                sb.append("Restore observer threw: ");
                                sb.append(e.getMessage());
                                android.util.Slog.e(str, sb.toString());
                            }
                        }
                    } catch (java.lang.Exception e7) {
                        e = e7;
                        android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Error from transport getting set list: " + e.getMessage());
                        if (restoreGetSetsParams.observer != null) {
                            try {
                                if (list == null) {
                                    restoreGetSetsParams.observer.restoreSetsAvailable((android.app.backup.RestoreSet[]) null);
                                } else {
                                    restoreGetSetsParams.observer.restoreSetsAvailable((android.app.backup.RestoreSet[]) list.toArray(new android.app.backup.RestoreSet[0]));
                                }
                            } catch (android.os.RemoteException e8) {
                                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unable to report listing to observer");
                            } catch (java.lang.Exception e9) {
                                e = e9;
                                str = com.android.server.backup.BackupManagerService.TAG;
                                sb = new java.lang.StringBuilder();
                                sb.append("Restore observer threw: ");
                                sb.append(e.getMessage());
                                android.util.Slog.e(str, sb.toString());
                            }
                        }
                        removeMessages(8);
                        sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                        restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
                        return;
                    }
                    removeMessages(8);
                    sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                    restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
                    return;
                } catch (java.lang.Throwable th3) {
                    th = th3;
                    if (restoreGetSetsParams.observer != null) {
                        try {
                            if (0 == 0) {
                                restoreGetSetsParams.observer.restoreSetsAvailable((android.app.backup.RestoreSet[]) null);
                            } else {
                                restoreGetSetsParams.observer.restoreSetsAvailable((android.app.backup.RestoreSet[]) list2.toArray(new android.app.backup.RestoreSet[0]));
                            }
                        } catch (android.os.RemoteException e10) {
                            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unable to report listing to observer");
                        } catch (java.lang.Exception e11) {
                            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Restore observer threw: " + e11.getMessage());
                        }
                    }
                    removeMessages(8);
                    sendEmptyMessageDelayed(8, this.mAgentTimeoutParameters.getRestoreSessionTimeoutMillis());
                    restoreGetSetsParams.listener.onFinished("BH/MSG_RUN_GET_RESTORE_SETS");
                    throw th;
                }
            case 8:
                synchronized (this.backupManagerService) {
                    try {
                        if (this.backupManagerService.getActiveRestoreSession() != null) {
                            android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Restore session timed out; aborting");
                            this.backupManagerService.getActiveRestoreSession().markTimedOut();
                            com.android.server.backup.restore.ActiveRestoreSession activeRestoreSession = this.backupManagerService.getActiveRestoreSession();
                            java.util.Objects.requireNonNull(activeRestoreSession);
                            post(activeRestoreSession.new EndRestoreRunnable(this.backupManagerService, this.backupManagerService.getActiveRestoreSession()));
                        }
                    } finally {
                    }
                }
                return;
            case 9:
                synchronized (this.backupManagerService.getAdbBackupRestoreConfirmations()) {
                    com.android.server.backup.params.AdbParams adbParams = this.backupManagerService.getAdbBackupRestoreConfirmations().get(message.arg1);
                    if (adbParams != null) {
                        android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Full backup/restore timed out waiting for user confirmation");
                        this.backupManagerService.signalAdbBackupRestoreCompletion(adbParams);
                        this.backupManagerService.getAdbBackupRestoreConfirmations().delete(message.arg1);
                        if (adbParams.observer != null) {
                            try {
                                adbParams.observer.onTimeout();
                            } catch (android.os.RemoteException e12) {
                            }
                        }
                    } else {
                        android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "couldn't find params for token " + message.arg1);
                    }
                }
                return;
            case 10:
                com.android.server.backup.params.AdbRestoreParams adbRestoreParams = (com.android.server.backup.params.AdbRestoreParams) message.obj;
                new java.lang.Thread(new com.android.server.backup.restore.PerformAdbRestoreTask(this.backupManagerService, this.mOperationStorage, adbRestoreParams.fd, adbRestoreParams.curPassword, adbRestoreParams.encryptPassword, adbRestoreParams.observer, adbRestoreParams.latch), "adb-restore").start();
                return;
            case 12:
                com.android.server.backup.params.ClearRetryParams clearRetryParams = (com.android.server.backup.params.ClearRetryParams) message.obj;
                this.backupManagerService.clearBackupData(clearRetryParams.transportName, clearRetryParams.packageName);
                return;
            case 15:
                com.android.server.backup.params.BackupParams backupParams = (com.android.server.backup.params.BackupParams) message.obj;
                this.backupManagerService.setBackupRunning(true);
                this.backupManagerService.getWakelock().acquire();
                com.android.server.backup.keyvalue.KeyValueBackupTask.start(this.backupManagerService, this.mOperationStorage, backupParams.mTransportConnection, backupParams.dirName, backupParams.kvPackages, null, backupParams.observer, backupParams.monitor, backupParams.listener, backupParams.fullPackages, true, backupParams.nonIncrementalBackup, backupParams.mBackupEligibilityRules);
                return;
            case 16:
                this.backupManagerService.dataChangedImpl((java.lang.String) message.obj);
                return;
            case 17:
            case 18:
                android.util.Slog.d(com.android.server.backup.BackupManagerService.TAG, "Timeout message received for token=" + java.lang.Integer.toHexString(message.arg1));
                this.backupManagerService.handleCancel(message.arg1, false);
                return;
            case 20:
                try {
                    ((com.android.server.backup.BackupRestoreTask) message.obj).execute();
                    return;
                } catch (java.lang.ClassCastException e13) {
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Invalid backup/restore task in flight, obj=" + message.obj);
                    return;
                }
            case 21:
                try {
                    android.util.Pair pair = (android.util.Pair) message.obj;
                    ((com.android.server.backup.BackupRestoreTask) pair.first).operationComplete(((java.lang.Long) pair.second).longValue());
                    return;
                } catch (java.lang.ClassCastException e14) {
                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Invalid completion in flight, obj=" + message.obj);
                    return;
                }
        }
    }
}
