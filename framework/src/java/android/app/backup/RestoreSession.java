package android.app.backup;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class RestoreSession {
    static final java.lang.String TAG = "RestoreSession";
    android.app.backup.IRestoreSession mBinder;
    final android.content.Context mContext;
    android.app.backup.RestoreSession.RestoreObserverWrapper mObserver = null;

    public int getAvailableRestoreSets(android.app.backup.RestoreObserver restoreObserver, android.app.backup.BackupManagerMonitor backupManagerMonitor) {
        android.app.backup.BackupManagerMonitorWrapper backupManagerMonitorWrapper;
        android.app.backup.RestoreSession.RestoreObserverWrapper restoreObserverWrapper = new android.app.backup.RestoreSession.RestoreObserverWrapper(this.mContext, restoreObserver);
        if (backupManagerMonitor == null) {
            backupManagerMonitorWrapper = null;
        } else {
            backupManagerMonitorWrapper = new android.app.backup.BackupManagerMonitorWrapper(backupManagerMonitor);
        }
        try {
            return this.mBinder.getAvailableRestoreSets(restoreObserverWrapper, backupManagerMonitorWrapper);
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "Can't contact server to get available sets");
            return -1;
        }
    }

    public int getAvailableRestoreSets(android.app.backup.RestoreObserver restoreObserver) {
        return getAvailableRestoreSets(restoreObserver, null);
    }

    public int restoreAll(long j, android.app.backup.RestoreObserver restoreObserver, android.app.backup.BackupManagerMonitor backupManagerMonitor) {
        android.app.backup.BackupManagerMonitorWrapper backupManagerMonitorWrapper;
        if (this.mObserver != null) {
            android.util.Log.d(TAG, "restoreAll() called during active restore");
            return -1;
        }
        this.mObserver = new android.app.backup.RestoreSession.RestoreObserverWrapper(this.mContext, restoreObserver);
        if (backupManagerMonitor == null) {
            backupManagerMonitorWrapper = null;
        } else {
            backupManagerMonitorWrapper = new android.app.backup.BackupManagerMonitorWrapper(backupManagerMonitor);
        }
        try {
            return this.mBinder.restoreAll(j, this.mObserver, backupManagerMonitorWrapper);
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "Can't contact server to restore");
            return -1;
        }
    }

    public int restoreAll(long j, android.app.backup.RestoreObserver restoreObserver) {
        return restoreAll(j, restoreObserver, null);
    }

    public int restorePackages(long j, android.app.backup.RestoreObserver restoreObserver, java.util.Set<java.lang.String> set, android.app.backup.BackupManagerMonitor backupManagerMonitor) {
        android.app.backup.BackupManagerMonitorWrapper backupManagerMonitorWrapper;
        if (this.mObserver != null) {
            android.util.Log.d(TAG, "restoreAll() called during active restore");
            return -1;
        }
        this.mObserver = new android.app.backup.RestoreSession.RestoreObserverWrapper(this.mContext, restoreObserver);
        if (backupManagerMonitor == null) {
            backupManagerMonitorWrapper = null;
        } else {
            backupManagerMonitorWrapper = new android.app.backup.BackupManagerMonitorWrapper(backupManagerMonitor);
        }
        try {
            return this.mBinder.restorePackages(j, this.mObserver, (java.lang.String[]) set.toArray(new java.lang.String[0]), backupManagerMonitorWrapper);
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "Can't contact server to restore packages");
            return -1;
        }
    }

    public int restorePackages(long j, android.app.backup.RestoreObserver restoreObserver, java.util.Set<java.lang.String> set) {
        return restorePackages(j, restoreObserver, set, null);
    }

    @java.lang.Deprecated
    public int restoreSome(long j, android.app.backup.RestoreObserver restoreObserver, android.app.backup.BackupManagerMonitor backupManagerMonitor, java.lang.String[] strArr) {
        return restorePackages(j, restoreObserver, new java.util.HashSet(java.util.Arrays.asList(strArr)), backupManagerMonitor);
    }

    @java.lang.Deprecated
    public int restoreSome(long j, android.app.backup.RestoreObserver restoreObserver, java.lang.String[] strArr) {
        return restoreSome(j, restoreObserver, null, strArr);
    }

    public int restorePackage(java.lang.String str, android.app.backup.RestoreObserver restoreObserver, android.app.backup.BackupManagerMonitor backupManagerMonitor) {
        android.app.backup.BackupManagerMonitorWrapper backupManagerMonitorWrapper;
        if (this.mObserver != null) {
            android.util.Log.d(TAG, "restorePackage() called during active restore");
            return -1;
        }
        this.mObserver = new android.app.backup.RestoreSession.RestoreObserverWrapper(this.mContext, restoreObserver);
        if (backupManagerMonitor == null) {
            backupManagerMonitorWrapper = null;
        } else {
            backupManagerMonitorWrapper = new android.app.backup.BackupManagerMonitorWrapper(backupManagerMonitor);
        }
        try {
            return this.mBinder.restorePackage(str, this.mObserver, backupManagerMonitorWrapper);
        } catch (android.os.RemoteException e) {
            android.util.Log.d(TAG, "Can't contact server to restore package");
            return -1;
        }
    }

    public int restorePackage(java.lang.String str, android.app.backup.RestoreObserver restoreObserver) {
        return restorePackage(str, restoreObserver, null);
    }

    public void endRestoreSession() {
        try {
            try {
                this.mBinder.endRestoreSession();
            } catch (android.os.RemoteException e) {
                android.util.Log.d(TAG, "Can't contact server to get available sets");
            }
        } finally {
            this.mBinder = null;
        }
    }

    RestoreSession(android.content.Context context, android.app.backup.IRestoreSession iRestoreSession) {
        this.mContext = context;
        this.mBinder = iRestoreSession;
    }

    private class RestoreObserverWrapper extends android.app.backup.IRestoreObserver.Stub {
        static final int MSG_RESTORE_FINISHED = 3;
        static final int MSG_RESTORE_SETS_AVAILABLE = 4;
        static final int MSG_RESTORE_STARTING = 1;
        static final int MSG_UPDATE = 2;
        final android.app.backup.RestoreObserver mAppObserver;
        final android.os.Handler mHandler;

        RestoreObserverWrapper(android.content.Context context, android.app.backup.RestoreObserver restoreObserver) {
            this.mHandler = new android.os.Handler(context.getMainLooper()) { // from class: android.app.backup.RestoreSession.RestoreObserverWrapper.1
                @Override // android.os.Handler
                public void handleMessage(android.os.Message message) {
                    switch (message.what) {
                        case 1:
                            android.app.backup.RestoreSession.RestoreObserverWrapper.this.mAppObserver.restoreStarting(message.arg1);
                            break;
                        case 2:
                            android.app.backup.RestoreSession.RestoreObserverWrapper.this.mAppObserver.onUpdate(message.arg1, (java.lang.String) message.obj);
                            break;
                        case 3:
                            android.app.backup.RestoreSession.RestoreObserverWrapper.this.mAppObserver.restoreFinished(message.arg1);
                            break;
                        case 4:
                            android.app.backup.RestoreSession.RestoreObserverWrapper.this.mAppObserver.restoreSetsAvailable((android.app.backup.RestoreSet[]) message.obj);
                            break;
                    }
                }
            };
            this.mAppObserver = restoreObserver;
        }

        @Override // android.app.backup.IRestoreObserver
        public void restoreSetsAvailable(android.app.backup.RestoreSet[] restoreSetArr) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(4, restoreSetArr));
        }

        @Override // android.app.backup.IRestoreObserver
        public void restoreStarting(int i) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, i, 0));
        }

        @Override // android.app.backup.IRestoreObserver
        public void onUpdate(int i, java.lang.String str) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(2, i, 0, str));
        }

        @Override // android.app.backup.IRestoreObserver
        public void restoreFinished(int i) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, i, 0));
        }
    }
}
