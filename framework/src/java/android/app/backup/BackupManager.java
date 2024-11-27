package android.app.backup;

/* loaded from: classes.dex */
public class BackupManager {

    @android.annotation.SystemApi
    public static final int ERROR_AGENT_FAILURE = -1003;

    @android.annotation.SystemApi
    public static final int ERROR_BACKUP_CANCELLED = -2003;

    @android.annotation.SystemApi
    public static final int ERROR_BACKUP_NOT_ALLOWED = -2001;

    @android.annotation.SystemApi
    public static final int ERROR_PACKAGE_NOT_FOUND = -2002;

    @android.annotation.SystemApi
    public static final int ERROR_TRANSPORT_ABORTED = -1000;

    @android.annotation.SystemApi
    public static final int ERROR_TRANSPORT_INVALID = -2;

    @android.annotation.SystemApi
    public static final int ERROR_TRANSPORT_PACKAGE_REJECTED = -1002;

    @android.annotation.SystemApi
    public static final int ERROR_TRANSPORT_QUOTA_EXCEEDED = -1005;

    @android.annotation.SystemApi
    public static final int ERROR_TRANSPORT_UNAVAILABLE = -1;
    public static final java.lang.String EXTRA_BACKUP_SERVICES_AVAILABLE = "backup_services_available";

    @android.annotation.SystemApi
    public static final int FLAG_NON_INCREMENTAL_BACKUP = 1;
    public static final long IS_BACKUP_SERVICE_ACTIVE_ENFORCE_PERMISSION_IN_SERVICE = 158482162;

    @android.annotation.SystemApi
    public static final java.lang.String PACKAGE_MANAGER_SENTINEL = "@pm@";

    @android.annotation.SystemApi
    public static final int SUCCESS = 0;
    private static final java.lang.String TAG = "BackupManager";
    public static android.app.backup.IBackupManager sService;
    private android.content.Context mContext;

    private static void checkServiceBinder() {
        if (sService == null) {
            sService = android.app.backup.IBackupManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.BACKUP_SERVICE));
        }
    }

    public BackupManager(android.content.Context context) {
        this.mContext = context;
    }

    public void dataChanged() {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.dataChanged(this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.d(TAG, "dataChanged() couldn't connect");
            }
        }
    }

    public static void dataChanged(java.lang.String str) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.dataChanged(str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "dataChanged(pkg) couldn't connect");
            }
        }
    }

    public static void dataChangedForUser(int i, java.lang.String str) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.dataChangedForUser(i, str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "dataChanged(userId,pkg) couldn't connect");
            }
        }
    }

    @java.lang.Deprecated
    public int requestRestore(android.app.backup.RestoreObserver restoreObserver) {
        return requestRestore(restoreObserver, null);
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public int requestRestore(android.app.backup.RestoreObserver restoreObserver, android.app.backup.BackupManagerMonitor backupManagerMonitor) {
        android.util.Log.w(TAG, "requestRestore(): Since Android P app can no longer request restoring of its backup.");
        return -1;
    }

    @android.annotation.SystemApi
    public android.app.backup.RestoreSession beginRestoreSession() {
        checkServiceBinder();
        if (sService == null) {
            return null;
        }
        try {
            android.app.backup.IRestoreSession beginRestoreSessionForUser = sService.beginRestoreSessionForUser(this.mContext.getUserId(), null, null);
            if (beginRestoreSessionForUser != null) {
                return new android.app.backup.RestoreSession(this.mContext, beginRestoreSessionForUser);
            }
            return null;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "beginRestoreSession() couldn't connect");
            return null;
        }
    }

    @android.annotation.SystemApi
    public void setBackupEnabled(boolean z) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.setBackupEnabled(z);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "setBackupEnabled() couldn't connect");
            }
        }
    }

    @android.annotation.SystemApi
    public void setFrameworkSchedulingEnabled(boolean z) {
        checkServiceBinder();
        if (sService == null) {
            android.util.Log.e(TAG, "setFrameworkSchedulingEnabled() couldn't connect");
            return;
        }
        try {
            sService.setFrameworkSchedulingEnabledForUser(this.mContext.getUserId(), z);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "setFrameworkSchedulingEnabled() couldn't connect");
        }
    }

    @android.annotation.SystemApi
    public boolean isBackupEnabled() {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.isBackupEnabled();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "isBackupEnabled() couldn't connect");
                return false;
            }
        }
        return false;
    }

    @android.annotation.SystemApi
    public boolean isBackupServiceActive(android.os.UserHandle userHandle) {
        if (!android.app.compat.CompatChanges.isChangeEnabled(IS_BACKUP_SERVICE_ACTIVE_ENFORCE_PERMISSION_IN_SERVICE)) {
            this.mContext.enforceCallingOrSelfPermission(android.Manifest.permission.BACKUP, "isBackupServiceActive");
        }
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.isBackupServiceActive(userHandle.getIdentifier());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "isBackupEnabled() couldn't connect");
                return false;
            }
        }
        return false;
    }

    @android.annotation.SystemApi
    public void setAutoRestore(boolean z) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.setAutoRestore(z);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "setAutoRestore() couldn't connect");
            }
        }
    }

    @android.annotation.SystemApi
    public java.lang.String getCurrentTransport() {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getCurrentTransport();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "getCurrentTransport() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public android.content.ComponentName getCurrentTransportComponent() {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getCurrentTransportComponentForUser(this.mContext.getUserId());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "getCurrentTransportComponent() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public java.lang.String[] listAllTransports() {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.listAllTransports();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "listAllTransports() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void updateTransportAttributes(android.content.ComponentName componentName, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.content.Intent intent2, java.lang.String str3) {
        updateTransportAttributes(componentName, str, intent, str2, intent2, (java.lang.CharSequence) str3);
    }

    @android.annotation.SystemApi
    public void updateTransportAttributes(android.content.ComponentName componentName, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.content.Intent intent2, java.lang.CharSequence charSequence) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.updateTransportAttributesForUser(this.mContext.getUserId(), componentName, str, intent, str2, intent2, charSequence);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "describeTransport() couldn't connect");
            }
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public java.lang.String selectBackupTransport(java.lang.String str) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.selectBackupTransport(str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "selectBackupTransport() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public void selectBackupTransport(android.content.ComponentName componentName, android.app.backup.SelectBackupTransportCallback selectBackupTransportCallback) {
        android.app.backup.BackupManager.SelectTransportListenerWrapper selectTransportListenerWrapper;
        checkServiceBinder();
        if (sService != null) {
            if (selectBackupTransportCallback == null) {
                selectTransportListenerWrapper = null;
            } else {
                try {
                    selectTransportListenerWrapper = new android.app.backup.BackupManager.SelectTransportListenerWrapper(this.mContext, selectBackupTransportCallback);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "selectBackupTransportAsync() couldn't connect");
                    return;
                }
            }
            sService.selectBackupTransportAsyncForUser(this.mContext.getUserId(), componentName, selectTransportListenerWrapper);
        }
    }

    @android.annotation.SystemApi
    public void backupNow() {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.backupNow();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "backupNow() couldn't connect");
            }
        }
    }

    @android.annotation.SystemApi
    public long getAvailableRestoreToken(java.lang.String str) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getAvailableRestoreTokenForUser(this.mContext.getUserId(), str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "getAvailableRestoreToken() couldn't connect");
                return 0L;
            }
        }
        return 0L;
    }

    @android.annotation.SystemApi
    public boolean isAppEligibleForBackup(java.lang.String str) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.isAppEligibleForBackupForUser(this.mContext.getUserId(), str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "isAppEligibleForBackup(pkg) couldn't connect");
                return false;
            }
        }
        return false;
    }

    @android.annotation.SystemApi
    public int requestBackup(java.lang.String[] strArr, android.app.backup.BackupObserver backupObserver) {
        return requestBackup(strArr, backupObserver, null, 0);
    }

    @android.annotation.SystemApi
    public int requestBackup(java.lang.String[] strArr, android.app.backup.BackupObserver backupObserver, android.app.backup.BackupManagerMonitor backupManagerMonitor, int i) {
        android.app.backup.BackupManager.BackupObserverWrapper backupObserverWrapper;
        checkServiceBinder();
        if (sService != null) {
            android.app.backup.BackupManagerMonitorWrapper backupManagerMonitorWrapper = null;
            if (backupObserver == null) {
                backupObserverWrapper = null;
            } else {
                try {
                    backupObserverWrapper = new android.app.backup.BackupManager.BackupObserverWrapper(this.mContext, backupObserver);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "requestBackup() couldn't connect");
                    return -1;
                }
            }
            if (backupManagerMonitor != null) {
                backupManagerMonitorWrapper = new android.app.backup.BackupManagerMonitorWrapper(backupManagerMonitor);
            }
            return sService.requestBackup(strArr, backupObserverWrapper, backupManagerMonitorWrapper, i);
        }
        return -1;
    }

    @android.annotation.SystemApi
    public void cancelBackups() {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.cancelBackups();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "cancelBackups() couldn't connect.");
            }
        }
    }

    public android.os.UserHandle getUserForAncestralSerialNumber(long j) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getUserForAncestralSerialNumber(j);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "getUserForAncestralSerialNumber() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public void setAncestralSerialNumber(long j) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.setAncestralSerialNumber(j);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "setAncestralSerialNumber() couldn't connect");
            }
        }
    }

    @android.annotation.SystemApi
    public android.content.Intent getConfigurationIntent(java.lang.String str) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getConfigurationIntentForUser(this.mContext.getUserId(), str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "getConfigurationIntent() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public java.lang.String getDestinationString(java.lang.String str) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getDestinationStringForUser(this.mContext.getUserId(), str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "getDestinationString() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public android.content.Intent getDataManagementIntent(java.lang.String str) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getDataManagementIntentForUser(this.mContext.getUserId(), str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "getDataManagementIntent() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public java.lang.String getDataManagementLabel(java.lang.String str) {
        java.lang.CharSequence dataManagementIntentLabel = getDataManagementIntentLabel(str);
        if (dataManagementIntentLabel == null) {
            return null;
        }
        return dataManagementIntentLabel.toString();
    }

    @android.annotation.SystemApi
    public java.lang.CharSequence getDataManagementIntentLabel(java.lang.String str) {
        checkServiceBinder();
        if (sService != null) {
            try {
                return sService.getDataManagementLabelForUser(this.mContext.getUserId(), str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "getDataManagementIntentLabel() couldn't connect");
                return null;
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public void excludeKeysFromRestore(java.lang.String str, java.util.List<java.lang.String> list) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.excludeKeysFromRestore(str, list);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "excludeKeysFromRestore() couldn't connect");
            }
        }
    }

    @android.annotation.SystemApi
    public android.app.backup.BackupRestoreEventLogger getBackupRestoreEventLogger(android.app.backup.BackupAgent backupAgent) {
        if (backupAgent.getBackupRestoreEventLogger() == null) {
            throw new java.lang.IllegalStateException("Attempting to get logger on an uninitialised BackupAgent");
        }
        return backupAgent.getBackupRestoreEventLogger();
    }

    @android.annotation.SystemApi
    public android.app.backup.BackupRestoreEventLogger getDelayedRestoreLogger() {
        return new android.app.backup.BackupRestoreEventLogger(1);
    }

    @android.annotation.SystemApi
    public void reportDelayedRestoreResult(android.app.backup.BackupRestoreEventLogger backupRestoreEventLogger) {
        checkServiceBinder();
        if (sService != null) {
            try {
                sService.reportDelayedRestoreResult(this.mContext.getPackageName(), backupRestoreEventLogger.getLoggingResults());
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "reportDelayedRestoreResult() couldn't connect");
            }
        }
    }

    private class BackupObserverWrapper extends android.app.backup.IBackupObserver.Stub {
        static final int MSG_FINISHED = 3;
        static final int MSG_RESULT = 2;
        static final int MSG_UPDATE = 1;
        final android.os.Handler mHandler;
        final android.app.backup.BackupObserver mObserver;

        BackupObserverWrapper(android.content.Context context, android.app.backup.BackupObserver backupObserver) {
            this.mHandler = new android.os.Handler(context.getMainLooper()) { // from class: android.app.backup.BackupManager.BackupObserverWrapper.1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.os.Handler
                public void handleMessage(android.os.Message message) {
                    switch (message.what) {
                        case 1:
                            android.util.Pair pair = (android.util.Pair) message.obj;
                            android.app.backup.BackupManager.BackupObserverWrapper.this.mObserver.onUpdate((java.lang.String) pair.first, (android.app.backup.BackupProgress) pair.second);
                            break;
                        case 2:
                            android.app.backup.BackupManager.BackupObserverWrapper.this.mObserver.onResult((java.lang.String) message.obj, message.arg1);
                            break;
                        case 3:
                            android.app.backup.BackupManager.BackupObserverWrapper.this.mObserver.backupFinished(message.arg1);
                            break;
                        default:
                            android.util.Log.w(android.app.backup.BackupManager.TAG, "Unknown message: " + message);
                            break;
                    }
                }
            };
            this.mObserver = backupObserver;
        }

        @Override // android.app.backup.IBackupObserver
        public void onUpdate(java.lang.String str, android.app.backup.BackupProgress backupProgress) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, android.util.Pair.create(str, backupProgress)));
        }

        @Override // android.app.backup.IBackupObserver
        public void onResult(java.lang.String str, int i) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(2, i, 0, str));
        }

        @Override // android.app.backup.IBackupObserver
        public void backupFinished(int i) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(3, i, 0));
        }
    }

    private class SelectTransportListenerWrapper extends android.app.backup.ISelectBackupTransportCallback.Stub {
        private final android.os.Handler mHandler;
        private final android.app.backup.SelectBackupTransportCallback mListener;

        SelectTransportListenerWrapper(android.content.Context context, android.app.backup.SelectBackupTransportCallback selectBackupTransportCallback) {
            this.mHandler = new android.os.Handler(context.getMainLooper());
            this.mListener = selectBackupTransportCallback;
        }

        @Override // android.app.backup.ISelectBackupTransportCallback
        public void onSuccess(final java.lang.String str) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.app.backup.BackupManager.SelectTransportListenerWrapper.1
                @Override // java.lang.Runnable
                public void run() {
                    android.app.backup.BackupManager.SelectTransportListenerWrapper.this.mListener.onSuccess(str);
                }
            });
        }

        @Override // android.app.backup.ISelectBackupTransportCallback
        public void onFailure(final int i) {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.app.backup.BackupManager.SelectTransportListenerWrapper.2
                @Override // java.lang.Runnable
                public void run() {
                    android.app.backup.BackupManager.SelectTransportListenerWrapper.this.mListener.onFailure(i);
                }
            });
        }
    }
}
