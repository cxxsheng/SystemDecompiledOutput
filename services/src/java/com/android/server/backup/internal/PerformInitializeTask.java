package com.android.server.backup.internal;

/* loaded from: classes.dex */
public class PerformInitializeTask implements java.lang.Runnable {
    private final com.android.server.backup.UserBackupManagerService mBackupManagerService;
    private final java.io.File mBaseStateDir;
    private final com.android.server.backup.internal.OnTaskFinishedListener mListener;

    @android.annotation.Nullable
    private android.app.backup.IBackupObserver mObserver;
    private final java.lang.String[] mQueue;
    private final com.android.server.backup.TransportManager mTransportManager;

    public PerformInitializeTask(com.android.server.backup.UserBackupManagerService userBackupManagerService, java.lang.String[] strArr, @android.annotation.Nullable android.app.backup.IBackupObserver iBackupObserver, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener) {
        this(userBackupManagerService, userBackupManagerService.getTransportManager(), strArr, iBackupObserver, onTaskFinishedListener, userBackupManagerService.getBaseStateDir());
    }

    @com.android.internal.annotations.VisibleForTesting
    PerformInitializeTask(com.android.server.backup.UserBackupManagerService userBackupManagerService, com.android.server.backup.TransportManager transportManager, java.lang.String[] strArr, @android.annotation.Nullable android.app.backup.IBackupObserver iBackupObserver, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener, java.io.File file) {
        this.mBackupManagerService = userBackupManagerService;
        this.mTransportManager = transportManager;
        this.mQueue = strArr;
        this.mObserver = iBackupObserver;
        this.mListener = onTaskFinishedListener;
        this.mBaseStateDir = file;
    }

    private void notifyResult(java.lang.String str, int i) {
        try {
            if (this.mObserver != null) {
                this.mObserver.onResult(str, i);
            }
        } catch (android.os.RemoteException e) {
            this.mObserver = null;
        }
    }

    private void notifyFinished(int i) {
        try {
            if (this.mObserver != null) {
                this.mObserver.backupFinished(i);
            }
        } catch (android.os.RemoteException e) {
            this.mObserver = null;
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        int i;
        java.lang.Exception e;
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mQueue.length);
        int i2 = 0;
        try {
            i = 0;
            for (java.lang.String str : this.mQueue) {
                try {
                    try {
                        com.android.server.backup.transport.TransportConnection transportClient = this.mTransportManager.getTransportClient(str, "PerformInitializeTask.run()");
                        if (transportClient == null) {
                            android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Requested init for " + str + " but not found");
                        } else {
                            arrayList.add(transportClient);
                            android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Initializing (wiping) backup transport storage: " + str);
                            java.lang.String transportDirName = this.mTransportManager.getTransportDirName(transportClient.getTransportComponent());
                            android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_START, transportDirName);
                            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
                            com.android.server.backup.transport.BackupTransportClient connectOrThrow = transportClient.connectOrThrow("PerformInitializeTask.run()");
                            int initializeDevice = connectOrThrow.initializeDevice();
                            if (initializeDevice != 0) {
                                android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Transport error in initializeDevice()");
                            } else {
                                initializeDevice = connectOrThrow.finishBackup();
                                if (initializeDevice != 0) {
                                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Transport error in finishBackup()");
                                }
                            }
                            if (initializeDevice == 0) {
                                android.util.Slog.i(com.android.server.backup.BackupManagerService.TAG, "Device init successful");
                                int elapsedRealtime2 = (int) (android.os.SystemClock.elapsedRealtime() - elapsedRealtime);
                                android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_INITIALIZE, new java.lang.Object[0]);
                                this.mBackupManagerService.resetBackupState(new java.io.File(this.mBaseStateDir, transportDirName));
                                android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_SUCCESS, 0, java.lang.Integer.valueOf(elapsedRealtime2));
                                this.mBackupManagerService.recordInitPending(false, str, transportDirName);
                                notifyResult(str, 0);
                            } else {
                                android.util.EventLog.writeEvent(com.android.server.EventLogTags.BACKUP_TRANSPORT_FAILURE, "(initialize)");
                                this.mBackupManagerService.recordInitPending(true, str, transportDirName);
                                notifyResult(str, initializeDevice);
                                try {
                                    long requestBackupTime = connectOrThrow.requestBackupTime();
                                    android.util.Slog.w(com.android.server.backup.BackupManagerService.TAG, "Init failed on " + str + " resched in " + requestBackupTime);
                                    this.mBackupManagerService.getAlarmManager().set(0, java.lang.System.currentTimeMillis() + requestBackupTime, this.mBackupManagerService.getRunInitIntent());
                                    i = initializeDevice;
                                } catch (java.lang.Exception e2) {
                                    e = e2;
                                    i = initializeDevice;
                                    android.util.Slog.e(com.android.server.backup.BackupManagerService.TAG, "Unexpected error performing init", e);
                                    java.util.Iterator it = arrayList.iterator();
                                    while (it.hasNext()) {
                                        this.mTransportManager.disposeOfTransportClient((com.android.server.backup.transport.TransportConnection) it.next(), "PerformInitializeTask.run()");
                                    }
                                    notifyFinished(-1000);
                                    this.mListener.onFinished("PerformInitializeTask.run()");
                                } catch (java.lang.Throwable th) {
                                    th = th;
                                    i2 = initializeDevice;
                                    java.util.Iterator it2 = arrayList.iterator();
                                    while (it2.hasNext()) {
                                        this.mTransportManager.disposeOfTransportClient((com.android.server.backup.transport.TransportConnection) it2.next(), "PerformInitializeTask.run()");
                                    }
                                    notifyFinished(i2);
                                    this.mListener.onFinished("PerformInitializeTask.run()");
                                    throw th;
                                }
                            }
                        }
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        i2 = i;
                    }
                } catch (java.lang.Exception e3) {
                    e = e3;
                }
            }
            java.util.Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                this.mTransportManager.disposeOfTransportClient((com.android.server.backup.transport.TransportConnection) it3.next(), "PerformInitializeTask.run()");
            }
            notifyFinished(i);
        } catch (java.lang.Exception e4) {
            i = 0;
            e = e4;
        } catch (java.lang.Throwable th3) {
            th = th3;
        }
        this.mListener.onFinished("PerformInitializeTask.run()");
    }
}
