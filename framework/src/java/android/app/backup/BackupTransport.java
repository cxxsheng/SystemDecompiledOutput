package android.app.backup;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public class BackupTransport {
    public static final int AGENT_ERROR = -1003;
    public static final int AGENT_UNKNOWN = -1004;
    public static final java.lang.String EXTRA_TRANSPORT_REGISTRATION = "android.app.backup.extra.TRANSPORT_REGISTRATION";
    public static final int FLAG_DATA_NOT_CHANGED = 8;
    public static final int FLAG_INCREMENTAL = 2;
    public static final int FLAG_NON_INCREMENTAL = 4;
    public static final int FLAG_USER_INITIATED = 1;
    public static final int NO_MORE_DATA = -1;
    public static final int TRANSPORT_ERROR = -1000;
    public static final int TRANSPORT_NON_INCREMENTAL_BACKUP_REQUIRED = -1006;
    public static final int TRANSPORT_NOT_INITIALIZED = -1001;
    public static final int TRANSPORT_OK = 0;
    public static final int TRANSPORT_PACKAGE_REJECTED = -1002;
    public static final int TRANSPORT_QUOTA_EXCEEDED = -1005;
    com.android.internal.backup.IBackupTransport mBinderImpl = new android.app.backup.BackupTransport.TransportImpl();

    public android.os.IBinder getBinder() {
        return this.mBinderImpl.asBinder();
    }

    public java.lang.String name() {
        throw new java.lang.UnsupportedOperationException("Transport name() not implemented");
    }

    public android.content.Intent configurationIntent() {
        return null;
    }

    public java.lang.String currentDestinationString() {
        throw new java.lang.UnsupportedOperationException("Transport currentDestinationString() not implemented");
    }

    public android.content.Intent dataManagementIntent() {
        return null;
    }

    @java.lang.Deprecated
    public java.lang.String dataManagementLabel() {
        throw new java.lang.UnsupportedOperationException("Transport dataManagementLabel() not implemented");
    }

    public java.lang.CharSequence dataManagementIntentLabel() {
        return dataManagementLabel();
    }

    public java.lang.String transportDirName() {
        throw new java.lang.UnsupportedOperationException("Transport transportDirName() not implemented");
    }

    public int initializeDevice() {
        return -1000;
    }

    public int clearBackupData(android.content.pm.PackageInfo packageInfo) {
        return -1000;
    }

    public int finishBackup() {
        return -1000;
    }

    public long requestBackupTime() {
        return 0L;
    }

    public int performBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor, int i) {
        return performBackup(packageInfo, parcelFileDescriptor);
    }

    public int performBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        return -1000;
    }

    public android.app.backup.RestoreSet[] getAvailableRestoreSets() {
        return null;
    }

    public long getCurrentRestoreSet() {
        return 0L;
    }

    public int startRestore(long j, android.content.pm.PackageInfo[] packageInfoArr) {
        return -1000;
    }

    public android.app.backup.RestoreDescription nextRestorePackage() {
        return null;
    }

    public int getRestoreData(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        return -1000;
    }

    public void finishRestore() {
        throw new java.lang.UnsupportedOperationException("Transport finishRestore() not implemented");
    }

    public long requestFullBackupTime() {
        return 0L;
    }

    public int performFullBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor, int i) {
        return performFullBackup(packageInfo, parcelFileDescriptor);
    }

    public int performFullBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor) {
        return -1002;
    }

    public int checkFullBackupSize(long j) {
        return 0;
    }

    public int sendBackupData(int i) {
        return -1000;
    }

    public void cancelFullBackup() {
        throw new java.lang.UnsupportedOperationException("Transport cancelFullBackup() not implemented");
    }

    public boolean isAppEligibleForBackup(android.content.pm.PackageInfo packageInfo, boolean z) {
        return true;
    }

    public long getBackupQuota(java.lang.String str, boolean z) {
        return Long.MAX_VALUE;
    }

    public int getNextFullRestoreDataChunk(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        return 0;
    }

    public int abortFullRestore() {
        return 0;
    }

    public int getTransportFlags() {
        return 0;
    }

    public android.app.backup.BackupManagerMonitor getBackupManagerMonitor() {
        return null;
    }

    class TransportImpl extends com.android.internal.backup.IBackupTransport.Stub {
        TransportImpl() {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void name(com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
            try {
                androidFuture.complete(android.app.backup.BackupTransport.this.name());
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void configurationIntent(com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture) throws android.os.RemoteException {
            try {
                androidFuture.complete(android.app.backup.BackupTransport.this.configurationIntent());
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void currentDestinationString(com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
            try {
                androidFuture.complete(android.app.backup.BackupTransport.this.currentDestinationString());
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void dataManagementIntent(com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture) throws android.os.RemoteException {
            try {
                androidFuture.complete(android.app.backup.BackupTransport.this.dataManagementIntent());
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void dataManagementIntentLabel(com.android.internal.infra.AndroidFuture<java.lang.CharSequence> androidFuture) throws android.os.RemoteException {
            try {
                androidFuture.complete(android.app.backup.BackupTransport.this.dataManagementIntentLabel());
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void transportDirName(com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
            try {
                androidFuture.complete(android.app.backup.BackupTransport.this.transportDirName());
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void requestBackupTime(com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException {
            try {
                androidFuture.complete(java.lang.Long.valueOf(android.app.backup.BackupTransport.this.requestBackupTime()));
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void initializeDevice(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(android.app.backup.BackupTransport.this.initializeDevice());
            } catch (java.lang.RuntimeException e) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void performBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor, int i, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(android.app.backup.BackupTransport.this.performBackup(packageInfo, parcelFileDescriptor, i));
            } catch (java.lang.RuntimeException e) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void clearBackupData(android.content.pm.PackageInfo packageInfo, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(android.app.backup.BackupTransport.this.clearBackupData(packageInfo));
            } catch (java.lang.RuntimeException e) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void finishBackup(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(android.app.backup.BackupTransport.this.finishBackup());
            } catch (java.lang.RuntimeException e) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getAvailableRestoreSets(com.android.internal.infra.AndroidFuture<java.util.List<android.app.backup.RestoreSet>> androidFuture) throws android.os.RemoteException {
            try {
                androidFuture.complete(java.util.Arrays.asList(android.app.backup.BackupTransport.this.getAvailableRestoreSets()));
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getCurrentRestoreSet(com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException {
            try {
                androidFuture.complete(java.lang.Long.valueOf(android.app.backup.BackupTransport.this.getCurrentRestoreSet()));
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void startRestore(long j, android.content.pm.PackageInfo[] packageInfoArr, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(android.app.backup.BackupTransport.this.startRestore(j, packageInfoArr));
            } catch (java.lang.RuntimeException e) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void nextRestorePackage(com.android.internal.infra.AndroidFuture<android.app.backup.RestoreDescription> androidFuture) throws android.os.RemoteException {
            try {
                androidFuture.complete(android.app.backup.BackupTransport.this.nextRestorePackage());
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getRestoreData(android.os.ParcelFileDescriptor parcelFileDescriptor, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(android.app.backup.BackupTransport.this.getRestoreData(parcelFileDescriptor));
            } catch (java.lang.RuntimeException e) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void finishRestore(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
            try {
                android.app.backup.BackupTransport.this.finishRestore();
                iTransportStatusCallback.onOperationComplete();
            } catch (java.lang.RuntimeException e) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void requestFullBackupTime(com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException {
            try {
                androidFuture.complete(java.lang.Long.valueOf(android.app.backup.BackupTransport.this.requestFullBackupTime()));
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void performFullBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor, int i, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(android.app.backup.BackupTransport.this.performFullBackup(packageInfo, parcelFileDescriptor, i));
            } catch (java.lang.RuntimeException e) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void checkFullBackupSize(long j, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(android.app.backup.BackupTransport.this.checkFullBackupSize(j));
            } catch (java.lang.RuntimeException e) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void sendBackupData(int i, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(android.app.backup.BackupTransport.this.sendBackupData(i));
            } catch (java.lang.RuntimeException e) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void cancelFullBackup(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
            try {
                android.app.backup.BackupTransport.this.cancelFullBackup();
                iTransportStatusCallback.onOperationComplete();
            } catch (java.lang.RuntimeException e) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void isAppEligibleForBackup(android.content.pm.PackageInfo packageInfo, boolean z, com.android.internal.infra.AndroidFuture<java.lang.Boolean> androidFuture) throws android.os.RemoteException {
            try {
                androidFuture.complete(java.lang.Boolean.valueOf(android.app.backup.BackupTransport.this.isAppEligibleForBackup(packageInfo, z)));
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getBackupQuota(java.lang.String str, boolean z, com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException {
            try {
                androidFuture.complete(java.lang.Long.valueOf(android.app.backup.BackupTransport.this.getBackupQuota(str, z)));
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getTransportFlags(com.android.internal.infra.AndroidFuture<java.lang.Integer> androidFuture) throws android.os.RemoteException {
            try {
                androidFuture.complete(java.lang.Integer.valueOf(android.app.backup.BackupTransport.this.getTransportFlags()));
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getNextFullRestoreDataChunk(android.os.ParcelFileDescriptor parcelFileDescriptor, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(android.app.backup.BackupTransport.this.getNextFullRestoreDataChunk(parcelFileDescriptor));
            } catch (java.lang.RuntimeException e) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void abortFullRestore(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
            try {
                iTransportStatusCallback.onOperationCompleteWithStatus(android.app.backup.BackupTransport.this.abortFullRestore());
            } catch (java.lang.RuntimeException e) {
                iTransportStatusCallback.onOperationCompleteWithStatus(-1000);
            }
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getBackupManagerMonitor(com.android.internal.infra.AndroidFuture<android.app.backup.IBackupManagerMonitor> androidFuture) {
            try {
                androidFuture.complete(new android.app.backup.BackupManagerMonitorWrapper(android.app.backup.BackupTransport.this.getBackupManagerMonitor()));
            } catch (java.lang.RuntimeException e) {
                androidFuture.cancel(true);
            }
        }
    }
}
