package android.app.backup;

/* loaded from: classes.dex */
public interface IBackupManager extends android.os.IInterface {
    void acknowledgeFullBackupOrRestore(int i, boolean z, java.lang.String str, java.lang.String str2, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver) throws android.os.RemoteException;

    void acknowledgeFullBackupOrRestoreForUser(int i, int i2, boolean z, java.lang.String str, java.lang.String str2, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver) throws android.os.RemoteException;

    void adbBackup(int i, android.os.ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, java.lang.String[] strArr) throws android.os.RemoteException;

    void adbRestore(int i, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    void agentConnected(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException;

    void agentConnectedForUser(int i, java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException;

    void agentDisconnected(java.lang.String str) throws android.os.RemoteException;

    void agentDisconnectedForUser(int i, java.lang.String str) throws android.os.RemoteException;

    void backupNow() throws android.os.RemoteException;

    void backupNowForUser(int i) throws android.os.RemoteException;

    android.app.backup.IRestoreSession beginRestoreSessionForUser(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void cancelBackups() throws android.os.RemoteException;

    void cancelBackupsForUser(int i) throws android.os.RemoteException;

    void clearBackupData(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void clearBackupDataForUser(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void dataChanged(java.lang.String str) throws android.os.RemoteException;

    void dataChangedForUser(int i, java.lang.String str) throws android.os.RemoteException;

    void excludeKeysFromRestore(java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    java.lang.String[] filterAppsEligibleForBackupForUser(int i, java.lang.String[] strArr) throws android.os.RemoteException;

    void fullTransportBackupForUser(int i, java.lang.String[] strArr) throws android.os.RemoteException;

    long getAvailableRestoreTokenForUser(int i, java.lang.String str) throws android.os.RemoteException;

    android.content.Intent getConfigurationIntent(java.lang.String str) throws android.os.RemoteException;

    android.content.Intent getConfigurationIntentForUser(int i, java.lang.String str) throws android.os.RemoteException;

    java.lang.String getCurrentTransport() throws android.os.RemoteException;

    android.content.ComponentName getCurrentTransportComponentForUser(int i) throws android.os.RemoteException;

    java.lang.String getCurrentTransportForUser(int i) throws android.os.RemoteException;

    android.content.Intent getDataManagementIntent(java.lang.String str) throws android.os.RemoteException;

    android.content.Intent getDataManagementIntentForUser(int i, java.lang.String str) throws android.os.RemoteException;

    java.lang.CharSequence getDataManagementLabelForUser(int i, java.lang.String str) throws android.os.RemoteException;

    java.lang.String getDestinationString(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getDestinationStringForUser(int i, java.lang.String str) throws android.os.RemoteException;

    java.lang.String[] getTransportWhitelist() throws android.os.RemoteException;

    android.os.UserHandle getUserForAncestralSerialNumber(long j) throws android.os.RemoteException;

    boolean hasBackupPassword() throws android.os.RemoteException;

    void initializeTransportsForUser(int i, java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver) throws android.os.RemoteException;

    boolean isAppEligibleForBackupForUser(int i, java.lang.String str) throws android.os.RemoteException;

    boolean isBackupEnabled() throws android.os.RemoteException;

    boolean isBackupEnabledForUser(int i) throws android.os.RemoteException;

    boolean isBackupServiceActive(int i) throws android.os.RemoteException;

    boolean isUserReadyForBackup(int i) throws android.os.RemoteException;

    android.content.ComponentName[] listAllTransportComponentsForUser(int i) throws android.os.RemoteException;

    java.lang.String[] listAllTransports() throws android.os.RemoteException;

    java.lang.String[] listAllTransportsForUser(int i) throws android.os.RemoteException;

    void opComplete(int i, long j) throws android.os.RemoteException;

    void opCompleteForUser(int i, int i2, long j) throws android.os.RemoteException;

    void reportDelayedRestoreResult(java.lang.String str, java.util.List<android.app.backup.BackupRestoreEventLogger.DataTypeResult> list) throws android.os.RemoteException;

    int requestBackup(java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, int i) throws android.os.RemoteException;

    int requestBackupForUser(int i, java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, int i2) throws android.os.RemoteException;

    void restoreAtInstall(java.lang.String str, int i) throws android.os.RemoteException;

    void restoreAtInstallForUser(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    java.lang.String selectBackupTransport(java.lang.String str) throws android.os.RemoteException;

    void selectBackupTransportAsyncForUser(int i, android.content.ComponentName componentName, android.app.backup.ISelectBackupTransportCallback iSelectBackupTransportCallback) throws android.os.RemoteException;

    java.lang.String selectBackupTransportForUser(int i, java.lang.String str) throws android.os.RemoteException;

    void setAncestralSerialNumber(long j) throws android.os.RemoteException;

    void setAutoRestore(boolean z) throws android.os.RemoteException;

    void setAutoRestoreForUser(int i, boolean z) throws android.os.RemoteException;

    void setBackupEnabled(boolean z) throws android.os.RemoteException;

    void setBackupEnabledForUser(int i, boolean z) throws android.os.RemoteException;

    boolean setBackupPassword(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setBackupServiceActive(int i, boolean z) throws android.os.RemoteException;

    void setFrameworkSchedulingEnabledForUser(int i, boolean z) throws android.os.RemoteException;

    void updateTransportAttributesForUser(int i, android.content.ComponentName componentName, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.content.Intent intent2, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    public static class Default implements android.app.backup.IBackupManager {
        @Override // android.app.backup.IBackupManager
        public void dataChangedForUser(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void dataChanged(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void clearBackupDataForUser(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void clearBackupData(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void initializeTransportsForUser(int i, java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void agentConnectedForUser(int i, java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void agentConnected(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void agentDisconnectedForUser(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void agentDisconnected(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void restoreAtInstallForUser(int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void restoreAtInstall(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void setBackupEnabledForUser(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void setFrameworkSchedulingEnabledForUser(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void setBackupEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void setAutoRestoreForUser(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void setAutoRestore(boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public boolean isBackupEnabledForUser(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public boolean isBackupEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public boolean setBackupPassword(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public boolean hasBackupPassword() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public void backupNowForUser(int i) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void backupNow() throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void adbBackup(int i, android.os.ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void fullTransportBackupForUser(int i, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void adbRestore(int i, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void acknowledgeFullBackupOrRestoreForUser(int i, int i2, boolean z, java.lang.String str, java.lang.String str2, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void acknowledgeFullBackupOrRestore(int i, boolean z, java.lang.String str, java.lang.String str2, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void updateTransportAttributesForUser(int i, android.content.ComponentName componentName, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.content.Intent intent2, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public java.lang.String getCurrentTransportForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public java.lang.String getCurrentTransport() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public android.content.ComponentName getCurrentTransportComponentForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public java.lang.String[] listAllTransportsForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public java.lang.String[] listAllTransports() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public android.content.ComponentName[] listAllTransportComponentsForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public java.lang.String[] getTransportWhitelist() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public java.lang.String selectBackupTransportForUser(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public java.lang.String selectBackupTransport(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public void selectBackupTransportAsyncForUser(int i, android.content.ComponentName componentName, android.app.backup.ISelectBackupTransportCallback iSelectBackupTransportCallback) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public android.content.Intent getConfigurationIntentForUser(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public android.content.Intent getConfigurationIntent(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public java.lang.String getDestinationStringForUser(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public java.lang.String getDestinationString(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public android.content.Intent getDataManagementIntentForUser(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public android.content.Intent getDataManagementIntent(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public java.lang.CharSequence getDataManagementLabelForUser(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public android.app.backup.IRestoreSession beginRestoreSessionForUser(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public void opCompleteForUser(int i, int i2, long j) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void opComplete(int i, long j) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void setBackupServiceActive(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public boolean isBackupServiceActive(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public boolean isUserReadyForBackup(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public long getAvailableRestoreTokenForUser(int i, java.lang.String str) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.backup.IBackupManager
        public boolean isAppEligibleForBackupForUser(int i, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.backup.IBackupManager
        public java.lang.String[] filterAppsEligibleForBackupForUser(int i, java.lang.String[] strArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public int requestBackupForUser(int i, java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.backup.IBackupManager
        public int requestBackup(java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.backup.IBackupManager
        public void cancelBackupsForUser(int i) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void cancelBackups() throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public android.os.UserHandle getUserForAncestralSerialNumber(long j) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.backup.IBackupManager
        public void setAncestralSerialNumber(long j) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void excludeKeysFromRestore(java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupManager
        public void reportDelayedRestoreResult(java.lang.String str, java.util.List<android.app.backup.BackupRestoreEventLogger.DataTypeResult> list) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.backup.IBackupManager {
        public static final java.lang.String DESCRIPTOR = "android.app.backup.IBackupManager";
        static final int TRANSACTION_acknowledgeFullBackupOrRestore = 27;
        static final int TRANSACTION_acknowledgeFullBackupOrRestoreForUser = 26;
        static final int TRANSACTION_adbBackup = 23;
        static final int TRANSACTION_adbRestore = 25;
        static final int TRANSACTION_agentConnected = 7;
        static final int TRANSACTION_agentConnectedForUser = 6;
        static final int TRANSACTION_agentDisconnected = 9;
        static final int TRANSACTION_agentDisconnectedForUser = 8;
        static final int TRANSACTION_backupNow = 22;
        static final int TRANSACTION_backupNowForUser = 21;
        static final int TRANSACTION_beginRestoreSessionForUser = 46;
        static final int TRANSACTION_cancelBackups = 58;
        static final int TRANSACTION_cancelBackupsForUser = 57;
        static final int TRANSACTION_clearBackupData = 4;
        static final int TRANSACTION_clearBackupDataForUser = 3;
        static final int TRANSACTION_dataChanged = 2;
        static final int TRANSACTION_dataChangedForUser = 1;
        static final int TRANSACTION_excludeKeysFromRestore = 61;
        static final int TRANSACTION_filterAppsEligibleForBackupForUser = 54;
        static final int TRANSACTION_fullTransportBackupForUser = 24;
        static final int TRANSACTION_getAvailableRestoreTokenForUser = 52;
        static final int TRANSACTION_getConfigurationIntent = 40;
        static final int TRANSACTION_getConfigurationIntentForUser = 39;
        static final int TRANSACTION_getCurrentTransport = 30;
        static final int TRANSACTION_getCurrentTransportComponentForUser = 31;
        static final int TRANSACTION_getCurrentTransportForUser = 29;
        static final int TRANSACTION_getDataManagementIntent = 44;
        static final int TRANSACTION_getDataManagementIntentForUser = 43;
        static final int TRANSACTION_getDataManagementLabelForUser = 45;
        static final int TRANSACTION_getDestinationString = 42;
        static final int TRANSACTION_getDestinationStringForUser = 41;
        static final int TRANSACTION_getTransportWhitelist = 35;
        static final int TRANSACTION_getUserForAncestralSerialNumber = 59;
        static final int TRANSACTION_hasBackupPassword = 20;
        static final int TRANSACTION_initializeTransportsForUser = 5;
        static final int TRANSACTION_isAppEligibleForBackupForUser = 53;
        static final int TRANSACTION_isBackupEnabled = 18;
        static final int TRANSACTION_isBackupEnabledForUser = 17;
        static final int TRANSACTION_isBackupServiceActive = 50;
        static final int TRANSACTION_isUserReadyForBackup = 51;
        static final int TRANSACTION_listAllTransportComponentsForUser = 34;
        static final int TRANSACTION_listAllTransports = 33;
        static final int TRANSACTION_listAllTransportsForUser = 32;
        static final int TRANSACTION_opComplete = 48;
        static final int TRANSACTION_opCompleteForUser = 47;
        static final int TRANSACTION_reportDelayedRestoreResult = 62;
        static final int TRANSACTION_requestBackup = 56;
        static final int TRANSACTION_requestBackupForUser = 55;
        static final int TRANSACTION_restoreAtInstall = 11;
        static final int TRANSACTION_restoreAtInstallForUser = 10;
        static final int TRANSACTION_selectBackupTransport = 37;
        static final int TRANSACTION_selectBackupTransportAsyncForUser = 38;
        static final int TRANSACTION_selectBackupTransportForUser = 36;
        static final int TRANSACTION_setAncestralSerialNumber = 60;
        static final int TRANSACTION_setAutoRestore = 16;
        static final int TRANSACTION_setAutoRestoreForUser = 15;
        static final int TRANSACTION_setBackupEnabled = 14;
        static final int TRANSACTION_setBackupEnabledForUser = 12;
        static final int TRANSACTION_setBackupPassword = 19;
        static final int TRANSACTION_setBackupServiceActive = 49;
        static final int TRANSACTION_setFrameworkSchedulingEnabledForUser = 13;
        static final int TRANSACTION_updateTransportAttributesForUser = 28;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.backup.IBackupManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.backup.IBackupManager)) {
                return (android.app.backup.IBackupManager) queryLocalInterface;
            }
            return new android.app.backup.IBackupManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dataChangedForUser";
                case 2:
                    return "dataChanged";
                case 3:
                    return "clearBackupDataForUser";
                case 4:
                    return "clearBackupData";
                case 5:
                    return "initializeTransportsForUser";
                case 6:
                    return "agentConnectedForUser";
                case 7:
                    return "agentConnected";
                case 8:
                    return "agentDisconnectedForUser";
                case 9:
                    return "agentDisconnected";
                case 10:
                    return "restoreAtInstallForUser";
                case 11:
                    return "restoreAtInstall";
                case 12:
                    return "setBackupEnabledForUser";
                case 13:
                    return "setFrameworkSchedulingEnabledForUser";
                case 14:
                    return "setBackupEnabled";
                case 15:
                    return "setAutoRestoreForUser";
                case 16:
                    return "setAutoRestore";
                case 17:
                    return "isBackupEnabledForUser";
                case 18:
                    return "isBackupEnabled";
                case 19:
                    return "setBackupPassword";
                case 20:
                    return "hasBackupPassword";
                case 21:
                    return "backupNowForUser";
                case 22:
                    return "backupNow";
                case 23:
                    return "adbBackup";
                case 24:
                    return "fullTransportBackupForUser";
                case 25:
                    return "adbRestore";
                case 26:
                    return "acknowledgeFullBackupOrRestoreForUser";
                case 27:
                    return "acknowledgeFullBackupOrRestore";
                case 28:
                    return "updateTransportAttributesForUser";
                case 29:
                    return "getCurrentTransportForUser";
                case 30:
                    return "getCurrentTransport";
                case 31:
                    return "getCurrentTransportComponentForUser";
                case 32:
                    return "listAllTransportsForUser";
                case 33:
                    return "listAllTransports";
                case 34:
                    return "listAllTransportComponentsForUser";
                case 35:
                    return "getTransportWhitelist";
                case 36:
                    return "selectBackupTransportForUser";
                case 37:
                    return "selectBackupTransport";
                case 38:
                    return "selectBackupTransportAsyncForUser";
                case 39:
                    return "getConfigurationIntentForUser";
                case 40:
                    return "getConfigurationIntent";
                case 41:
                    return "getDestinationStringForUser";
                case 42:
                    return "getDestinationString";
                case 43:
                    return "getDataManagementIntentForUser";
                case 44:
                    return "getDataManagementIntent";
                case 45:
                    return "getDataManagementLabelForUser";
                case 46:
                    return "beginRestoreSessionForUser";
                case 47:
                    return "opCompleteForUser";
                case 48:
                    return "opComplete";
                case 49:
                    return "setBackupServiceActive";
                case 50:
                    return "isBackupServiceActive";
                case 51:
                    return "isUserReadyForBackup";
                case 52:
                    return "getAvailableRestoreTokenForUser";
                case 53:
                    return "isAppEligibleForBackupForUser";
                case 54:
                    return "filterAppsEligibleForBackupForUser";
                case 55:
                    return "requestBackupForUser";
                case 56:
                    return "requestBackup";
                case 57:
                    return "cancelBackupsForUser";
                case 58:
                    return "cancelBackups";
                case 59:
                    return "getUserForAncestralSerialNumber";
                case 60:
                    return "setAncestralSerialNumber";
                case 61:
                    return "excludeKeysFromRestore";
                case 62:
                    return "reportDelayedRestoreResult";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dataChangedForUser(readInt, readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    dataChanged(readString2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearBackupDataForUser(readInt2, readString3, readString4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearBackupData(readString5, readString6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt3 = parcel.readInt();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    android.app.backup.IBackupObserver asInterface = android.app.backup.IBackupObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    initializeTransportsForUser(readInt3, createStringArray, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt4 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    agentConnectedForUser(readInt4, readString7, readStrongBinder);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString8 = parcel.readString();
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    agentConnected(readString8, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt5 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    agentDisconnectedForUser(readInt5, readString9);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    agentDisconnected(readString10);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt6 = parcel.readInt();
                    java.lang.String readString11 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreAtInstallForUser(readInt6, readString11, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    java.lang.String readString12 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreAtInstall(readString12, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt9 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBackupEnabledForUser(readInt9, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt10 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setFrameworkSchedulingEnabledForUser(readInt10, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBackupEnabled(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt11 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoRestoreForUser(readInt11, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutoRestore(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isBackupEnabledForUser = isBackupEnabledForUser(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBackupEnabledForUser);
                    return true;
                case 18:
                    boolean isBackupEnabled = isBackupEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBackupEnabled);
                    return true;
                case 19:
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean backupPassword = setBackupPassword(readString13, readString14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(backupPassword);
                    return true;
                case 20:
                    boolean hasBackupPassword = hasBackupPassword();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasBackupPassword);
                    return true;
                case 21:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    backupNowForUser(readInt13);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    backupNow();
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt14 = parcel.readInt();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    boolean readBoolean7 = parcel.readBoolean();
                    boolean readBoolean8 = parcel.readBoolean();
                    boolean readBoolean9 = parcel.readBoolean();
                    boolean readBoolean10 = parcel.readBoolean();
                    boolean readBoolean11 = parcel.readBoolean();
                    boolean readBoolean12 = parcel.readBoolean();
                    boolean readBoolean13 = parcel.readBoolean();
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    adbBackup(readInt14, parcelFileDescriptor, readBoolean6, readBoolean7, readBoolean8, readBoolean9, readBoolean10, readBoolean11, readBoolean12, readBoolean13, createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt15 = parcel.readInt();
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    fullTransportBackupForUser(readInt15, createStringArray3);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt16 = parcel.readInt();
                    android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    adbRestore(readInt16, parcelFileDescriptor2);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    boolean readBoolean14 = parcel.readBoolean();
                    java.lang.String readString15 = parcel.readString();
                    java.lang.String readString16 = parcel.readString();
                    android.app.backup.IFullBackupRestoreObserver asInterface2 = android.app.backup.IFullBackupRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acknowledgeFullBackupOrRestoreForUser(readInt17, readInt18, readBoolean14, readString15, readString16, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int readInt19 = parcel.readInt();
                    boolean readBoolean15 = parcel.readBoolean();
                    java.lang.String readString17 = parcel.readString();
                    java.lang.String readString18 = parcel.readString();
                    android.app.backup.IFullBackupRestoreObserver asInterface3 = android.app.backup.IFullBackupRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    acknowledgeFullBackupOrRestore(readInt19, readBoolean15, readString17, readString18, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt20 = parcel.readInt();
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    java.lang.String readString19 = parcel.readString();
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.String readString20 = parcel.readString();
                    android.content.Intent intent2 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    updateTransportAttributesForUser(readInt20, componentName, readString19, intent, readString20, intent2, charSequence);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String currentTransportForUser = getCurrentTransportForUser(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeString(currentTransportForUser);
                    return true;
                case 30:
                    java.lang.String currentTransport = getCurrentTransport();
                    parcel2.writeNoException();
                    parcel2.writeString(currentTransport);
                    return true;
                case 31:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.ComponentName currentTransportComponentForUser = getCurrentTransportComponentForUser(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(currentTransportComponentForUser, 1);
                    return true;
                case 32:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] listAllTransportsForUser = listAllTransportsForUser(readInt23);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(listAllTransportsForUser);
                    return true;
                case 33:
                    java.lang.String[] listAllTransports = listAllTransports();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(listAllTransports);
                    return true;
                case 34:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.ComponentName[] listAllTransportComponentsForUser = listAllTransportComponentsForUser(readInt24);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listAllTransportComponentsForUser, 1);
                    return true;
                case 35:
                    java.lang.String[] transportWhitelist = getTransportWhitelist();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(transportWhitelist);
                    return true;
                case 36:
                    int readInt25 = parcel.readInt();
                    java.lang.String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String selectBackupTransportForUser = selectBackupTransportForUser(readInt25, readString21);
                    parcel2.writeNoException();
                    parcel2.writeString(selectBackupTransportForUser);
                    return true;
                case 37:
                    java.lang.String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String selectBackupTransport = selectBackupTransport(readString22);
                    parcel2.writeNoException();
                    parcel2.writeString(selectBackupTransport);
                    return true;
                case 38:
                    int readInt26 = parcel.readInt();
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.app.backup.ISelectBackupTransportCallback asInterface4 = android.app.backup.ISelectBackupTransportCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    selectBackupTransportAsyncForUser(readInt26, componentName2, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt27 = parcel.readInt();
                    java.lang.String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.Intent configurationIntentForUser = getConfigurationIntentForUser(readInt27, readString23);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configurationIntentForUser, 1);
                    return true;
                case 40:
                    java.lang.String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.Intent configurationIntent = getConfigurationIntent(readString24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(configurationIntent, 1);
                    return true;
                case 41:
                    int readInt28 = parcel.readInt();
                    java.lang.String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String destinationStringForUser = getDestinationStringForUser(readInt28, readString25);
                    parcel2.writeNoException();
                    parcel2.writeString(destinationStringForUser);
                    return true;
                case 42:
                    java.lang.String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String destinationString = getDestinationString(readString26);
                    parcel2.writeNoException();
                    parcel2.writeString(destinationString);
                    return true;
                case 43:
                    int readInt29 = parcel.readInt();
                    java.lang.String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.Intent dataManagementIntentForUser = getDataManagementIntentForUser(readInt29, readString27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dataManagementIntentForUser, 1);
                    return true;
                case 44:
                    java.lang.String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.Intent dataManagementIntent = getDataManagementIntent(readString28);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(dataManagementIntent, 1);
                    return true;
                case 45:
                    int readInt30 = parcel.readInt();
                    java.lang.String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.CharSequence dataManagementLabelForUser = getDataManagementLabelForUser(readInt30, readString29);
                    parcel2.writeNoException();
                    if (dataManagementLabelForUser != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(dataManagementLabelForUser, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 46:
                    int readInt31 = parcel.readInt();
                    java.lang.String readString30 = parcel.readString();
                    java.lang.String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.backup.IRestoreSession beginRestoreSessionForUser = beginRestoreSessionForUser(readInt31, readString30, readString31);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(beginRestoreSessionForUser);
                    return true;
                case 47:
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    opCompleteForUser(readInt32, readInt33, readLong);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    int readInt34 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    opComplete(readInt34, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    int readInt35 = parcel.readInt();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBackupServiceActive(readInt35, readBoolean16);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isBackupServiceActive = isBackupServiceActive(readInt36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBackupServiceActive);
                    return true;
                case 51:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserReadyForBackup = isUserReadyForBackup(readInt37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserReadyForBackup);
                    return true;
                case 52:
                    int readInt38 = parcel.readInt();
                    java.lang.String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long availableRestoreTokenForUser = getAvailableRestoreTokenForUser(readInt38, readString32);
                    parcel2.writeNoException();
                    parcel2.writeLong(availableRestoreTokenForUser);
                    return true;
                case 53:
                    int readInt39 = parcel.readInt();
                    java.lang.String readString33 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAppEligibleForBackupForUser = isAppEligibleForBackupForUser(readInt39, readString33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppEligibleForBackupForUser);
                    return true;
                case 54:
                    int readInt40 = parcel.readInt();
                    java.lang.String[] createStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] filterAppsEligibleForBackupForUser = filterAppsEligibleForBackupForUser(readInt40, createStringArray4);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(filterAppsEligibleForBackupForUser);
                    return true;
                case 55:
                    int readInt41 = parcel.readInt();
                    java.lang.String[] createStringArray5 = parcel.createStringArray();
                    android.app.backup.IBackupObserver asInterface5 = android.app.backup.IBackupObserver.Stub.asInterface(parcel.readStrongBinder());
                    android.app.backup.IBackupManagerMonitor asInterface6 = android.app.backup.IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int requestBackupForUser = requestBackupForUser(readInt41, createStringArray5, asInterface5, asInterface6, readInt42);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestBackupForUser);
                    return true;
                case 56:
                    java.lang.String[] createStringArray6 = parcel.createStringArray();
                    android.app.backup.IBackupObserver asInterface7 = android.app.backup.IBackupObserver.Stub.asInterface(parcel.readStrongBinder());
                    android.app.backup.IBackupManagerMonitor asInterface8 = android.app.backup.IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int requestBackup = requestBackup(createStringArray6, asInterface7, asInterface8, readInt43);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestBackup);
                    return true;
                case 57:
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelBackupsForUser(readInt44);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    cancelBackups();
                    parcel2.writeNoException();
                    return true;
                case 59:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    android.os.UserHandle userForAncestralSerialNumber = getUserForAncestralSerialNumber(readLong3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userForAncestralSerialNumber, 1);
                    return true;
                case 60:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setAncestralSerialNumber(readLong4);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    java.lang.String readString34 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    excludeKeysFromRestore(readString34, createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    java.lang.String readString35 = parcel.readString();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.app.backup.BackupRestoreEventLogger.DataTypeResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportDelayedRestoreResult(readString35, createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.backup.IBackupManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.backup.IBackupManager.Stub.DESCRIPTOR;
            }

            @Override // android.app.backup.IBackupManager
            public void dataChangedForUser(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void dataChanged(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void clearBackupDataForUser(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void clearBackupData(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void initializeTransportsForUser(int i, java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongInterface(iBackupObserver);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void agentConnectedForUser(int i, java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void agentConnected(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void agentDisconnectedForUser(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void agentDisconnected(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void restoreAtInstallForUser(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void restoreAtInstall(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setBackupEnabledForUser(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setFrameworkSchedulingEnabledForUser(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setBackupEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setAutoRestoreForUser(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setAutoRestore(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isBackupEnabledForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isBackupEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean setBackupPassword(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean hasBackupPassword() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void backupNowForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void backupNow() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void adbBackup(int i, android.os.ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeBoolean(z3);
                    obtain.writeBoolean(z4);
                    obtain.writeBoolean(z5);
                    obtain.writeBoolean(z6);
                    obtain.writeBoolean(z7);
                    obtain.writeBoolean(z8);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void fullTransportBackupForUser(int i, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void adbRestore(int i, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void acknowledgeFullBackupOrRestoreForUser(int i, int i2, boolean z, java.lang.String str, java.lang.String str2, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iFullBackupRestoreObserver);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void acknowledgeFullBackupOrRestore(int i, boolean z, java.lang.String str, java.lang.String str2, android.app.backup.IFullBackupRestoreObserver iFullBackupRestoreObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iFullBackupRestoreObserver);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void updateTransportAttributesForUser(int i, android.content.ComponentName componentName, java.lang.String str, android.content.Intent intent, java.lang.String str2, android.content.Intent intent2, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intent2, 0);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public java.lang.String getCurrentTransportForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public java.lang.String getCurrentTransport() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public android.content.ComponentName getCurrentTransportComponentForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName) obtain2.readTypedObject(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public java.lang.String[] listAllTransportsForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public java.lang.String[] listAllTransports() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public android.content.ComponentName[] listAllTransportComponentsForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.ComponentName[]) obtain2.createTypedArray(android.content.ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public java.lang.String[] getTransportWhitelist() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public java.lang.String selectBackupTransportForUser(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public java.lang.String selectBackupTransport(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void selectBackupTransportAsyncForUser(int i, android.content.ComponentName componentName, android.app.backup.ISelectBackupTransportCallback iSelectBackupTransportCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeStrongInterface(iSelectBackupTransportCallback);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public android.content.Intent getConfigurationIntentForUser(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.Intent) obtain2.readTypedObject(android.content.Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public android.content.Intent getConfigurationIntent(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.Intent) obtain2.readTypedObject(android.content.Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public java.lang.String getDestinationStringForUser(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public java.lang.String getDestinationString(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public android.content.Intent getDataManagementIntentForUser(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.Intent) obtain2.readTypedObject(android.content.Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public android.content.Intent getDataManagementIntent(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.Intent) obtain2.readTypedObject(android.content.Intent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public java.lang.CharSequence getDataManagementLabelForUser(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public android.app.backup.IRestoreSession beginRestoreSessionForUser(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.app.backup.IRestoreSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void opCompleteForUser(int i, int i2, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void opComplete(int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setBackupServiceActive(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isBackupServiceActive(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isUserReadyForBackup(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public long getAvailableRestoreTokenForUser(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public boolean isAppEligibleForBackupForUser(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public java.lang.String[] filterAppsEligibleForBackupForUser(int i, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public int requestBackupForUser(int i, java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongInterface(iBackupObserver);
                    obtain.writeStrongInterface(iBackupManagerMonitor);
                    obtain.writeInt(i2);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public int requestBackup(java.lang.String[] strArr, android.app.backup.IBackupObserver iBackupObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongInterface(iBackupObserver);
                    obtain.writeStrongInterface(iBackupManagerMonitor);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void cancelBackupsForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void cancelBackups() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public android.os.UserHandle getUserForAncestralSerialNumber(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.UserHandle) obtain2.readTypedObject(android.os.UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void setAncestralSerialNumber(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void excludeKeysFromRestore(java.lang.String str, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupManager
            public void reportDelayedRestoreResult(java.lang.String str, java.util.List<android.app.backup.BackupRestoreEventLogger.DataTypeResult> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 61;
        }
    }
}
