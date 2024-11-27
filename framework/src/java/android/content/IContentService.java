package android.content;

/* loaded from: classes.dex */
public interface IContentService extends android.os.IInterface {
    void addPeriodicSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, long j) throws android.os.RemoteException;

    void addStatusChangeListener(int i, android.content.ISyncStatusObserver iSyncStatusObserver) throws android.os.RemoteException;

    void cancelRequest(android.content.SyncRequest syncRequest) throws android.os.RemoteException;

    void cancelSync(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException;

    void cancelSyncAsUser(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    android.os.Bundle getCache(java.lang.String str, android.net.Uri uri, int i) throws android.os.RemoteException;

    java.util.List<android.content.SyncInfo> getCurrentSyncs() throws android.os.RemoteException;

    java.util.List<android.content.SyncInfo> getCurrentSyncsAsUser(int i) throws android.os.RemoteException;

    int getIsSyncable(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException;

    int getIsSyncableAsUser(android.accounts.Account account, java.lang.String str, int i) throws android.os.RemoteException;

    boolean getMasterSyncAutomatically() throws android.os.RemoteException;

    boolean getMasterSyncAutomaticallyAsUser(int i) throws android.os.RemoteException;

    java.util.List<android.content.PeriodicSync> getPeriodicSyncs(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException;

    java.lang.String getSyncAdapterPackageAsUser(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    java.lang.String[] getSyncAdapterPackagesForAuthorityAsUser(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.SyncAdapterType[] getSyncAdapterTypes() throws android.os.RemoteException;

    android.content.SyncAdapterType[] getSyncAdapterTypesAsUser(int i) throws android.os.RemoteException;

    boolean getSyncAutomatically(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException;

    boolean getSyncAutomaticallyAsUser(android.accounts.Account account, java.lang.String str, int i) throws android.os.RemoteException;

    android.content.SyncStatusInfo getSyncStatus(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException;

    android.content.SyncStatusInfo getSyncStatusAsUser(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    boolean isSyncActive(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean isSyncPending(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException;

    boolean isSyncPendingAsUser(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName, int i) throws android.os.RemoteException;

    void notifyChange(android.net.Uri[] uriArr, android.database.IContentObserver iContentObserver, boolean z, int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException;

    void onDbCorruption(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void putCache(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void registerContentObserver(android.net.Uri uri, boolean z, android.database.IContentObserver iContentObserver, int i, int i2) throws android.os.RemoteException;

    void removePeriodicSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void removeStatusChangeListener(android.content.ISyncStatusObserver iSyncStatusObserver) throws android.os.RemoteException;

    void requestSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, java.lang.String str2) throws android.os.RemoteException;

    void resetTodayStats() throws android.os.RemoteException;

    void setIsSyncable(android.accounts.Account account, java.lang.String str, int i) throws android.os.RemoteException;

    void setIsSyncableAsUser(android.accounts.Account account, java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void setMasterSyncAutomatically(boolean z) throws android.os.RemoteException;

    void setMasterSyncAutomaticallyAsUser(boolean z, int i) throws android.os.RemoteException;

    void setSyncAutomatically(android.accounts.Account account, java.lang.String str, boolean z) throws android.os.RemoteException;

    void setSyncAutomaticallyAsUser(android.accounts.Account account, java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    void sync(android.content.SyncRequest syncRequest, java.lang.String str) throws android.os.RemoteException;

    void syncAsUser(android.content.SyncRequest syncRequest, int i, java.lang.String str) throws android.os.RemoteException;

    void unregisterContentObserver(android.database.IContentObserver iContentObserver) throws android.os.RemoteException;

    public static class Default implements android.content.IContentService {
        @Override // android.content.IContentService
        public void unregisterContentObserver(android.database.IContentObserver iContentObserver) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void registerContentObserver(android.net.Uri uri, boolean z, android.database.IContentObserver iContentObserver, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void notifyChange(android.net.Uri[] uriArr, android.database.IContentObserver iContentObserver, boolean z, int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void requestSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void sync(android.content.SyncRequest syncRequest, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void syncAsUser(android.content.SyncRequest syncRequest, int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void cancelSync(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void cancelSyncAsUser(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void cancelRequest(android.content.SyncRequest syncRequest) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public boolean getSyncAutomatically(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.IContentService
        public boolean getSyncAutomaticallyAsUser(android.accounts.Account account, java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.IContentService
        public void setSyncAutomatically(android.accounts.Account account, java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void setSyncAutomaticallyAsUser(android.accounts.Account account, java.lang.String str, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public java.util.List<android.content.PeriodicSync> getPeriodicSyncs(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public void addPeriodicSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, long j) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void removePeriodicSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public int getIsSyncable(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.IContentService
        public int getIsSyncableAsUser(android.accounts.Account account, java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.IContentService
        public void setIsSyncable(android.accounts.Account account, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void setIsSyncableAsUser(android.accounts.Account account, java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void setMasterSyncAutomatically(boolean z) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void setMasterSyncAutomaticallyAsUser(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public boolean getMasterSyncAutomatically() throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.IContentService
        public boolean getMasterSyncAutomaticallyAsUser(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.IContentService
        public java.util.List<android.content.SyncInfo> getCurrentSyncs() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public java.util.List<android.content.SyncInfo> getCurrentSyncsAsUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public android.content.SyncAdapterType[] getSyncAdapterTypes() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public android.content.SyncAdapterType[] getSyncAdapterTypesAsUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public java.lang.String[] getSyncAdapterPackagesForAuthorityAsUser(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public java.lang.String getSyncAdapterPackageAsUser(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public boolean isSyncActive(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.IContentService
        public android.content.SyncStatusInfo getSyncStatus(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public android.content.SyncStatusInfo getSyncStatusAsUser(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public boolean isSyncPending(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.IContentService
        public boolean isSyncPendingAsUser(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.IContentService
        public void addStatusChangeListener(int i, android.content.ISyncStatusObserver iSyncStatusObserver) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void removeStatusChangeListener(android.content.ISyncStatusObserver iSyncStatusObserver) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void putCache(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public android.os.Bundle getCache(java.lang.String str, android.net.Uri uri, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.IContentService
        public void resetTodayStats() throws android.os.RemoteException {
        }

        @Override // android.content.IContentService
        public void onDbCorruption(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.IContentService {
        public static final java.lang.String DESCRIPTOR = "android.content.IContentService";
        static final int TRANSACTION_addPeriodicSync = 15;
        static final int TRANSACTION_addStatusChangeListener = 36;
        static final int TRANSACTION_cancelRequest = 9;
        static final int TRANSACTION_cancelSync = 7;
        static final int TRANSACTION_cancelSyncAsUser = 8;
        static final int TRANSACTION_getCache = 39;
        static final int TRANSACTION_getCurrentSyncs = 25;
        static final int TRANSACTION_getCurrentSyncsAsUser = 26;
        static final int TRANSACTION_getIsSyncable = 17;
        static final int TRANSACTION_getIsSyncableAsUser = 18;
        static final int TRANSACTION_getMasterSyncAutomatically = 23;
        static final int TRANSACTION_getMasterSyncAutomaticallyAsUser = 24;
        static final int TRANSACTION_getPeriodicSyncs = 14;
        static final int TRANSACTION_getSyncAdapterPackageAsUser = 30;
        static final int TRANSACTION_getSyncAdapterPackagesForAuthorityAsUser = 29;
        static final int TRANSACTION_getSyncAdapterTypes = 27;
        static final int TRANSACTION_getSyncAdapterTypesAsUser = 28;
        static final int TRANSACTION_getSyncAutomatically = 10;
        static final int TRANSACTION_getSyncAutomaticallyAsUser = 11;
        static final int TRANSACTION_getSyncStatus = 32;
        static final int TRANSACTION_getSyncStatusAsUser = 33;
        static final int TRANSACTION_isSyncActive = 31;
        static final int TRANSACTION_isSyncPending = 34;
        static final int TRANSACTION_isSyncPendingAsUser = 35;
        static final int TRANSACTION_notifyChange = 3;
        static final int TRANSACTION_onDbCorruption = 41;
        static final int TRANSACTION_putCache = 38;
        static final int TRANSACTION_registerContentObserver = 2;
        static final int TRANSACTION_removePeriodicSync = 16;
        static final int TRANSACTION_removeStatusChangeListener = 37;
        static final int TRANSACTION_requestSync = 4;
        static final int TRANSACTION_resetTodayStats = 40;
        static final int TRANSACTION_setIsSyncable = 19;
        static final int TRANSACTION_setIsSyncableAsUser = 20;
        static final int TRANSACTION_setMasterSyncAutomatically = 21;
        static final int TRANSACTION_setMasterSyncAutomaticallyAsUser = 22;
        static final int TRANSACTION_setSyncAutomatically = 12;
        static final int TRANSACTION_setSyncAutomaticallyAsUser = 13;
        static final int TRANSACTION_sync = 5;
        static final int TRANSACTION_syncAsUser = 6;
        static final int TRANSACTION_unregisterContentObserver = 1;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.content.IContentService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.IContentService)) {
                return (android.content.IContentService) queryLocalInterface;
            }
            return new android.content.IContentService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "unregisterContentObserver";
                case 2:
                    return "registerContentObserver";
                case 3:
                    return "notifyChange";
                case 4:
                    return "requestSync";
                case 5:
                    return "sync";
                case 6:
                    return "syncAsUser";
                case 7:
                    return "cancelSync";
                case 8:
                    return "cancelSyncAsUser";
                case 9:
                    return "cancelRequest";
                case 10:
                    return "getSyncAutomatically";
                case 11:
                    return "getSyncAutomaticallyAsUser";
                case 12:
                    return "setSyncAutomatically";
                case 13:
                    return "setSyncAutomaticallyAsUser";
                case 14:
                    return "getPeriodicSyncs";
                case 15:
                    return "addPeriodicSync";
                case 16:
                    return "removePeriodicSync";
                case 17:
                    return "getIsSyncable";
                case 18:
                    return "getIsSyncableAsUser";
                case 19:
                    return "setIsSyncable";
                case 20:
                    return "setIsSyncableAsUser";
                case 21:
                    return "setMasterSyncAutomatically";
                case 22:
                    return "setMasterSyncAutomaticallyAsUser";
                case 23:
                    return "getMasterSyncAutomatically";
                case 24:
                    return "getMasterSyncAutomaticallyAsUser";
                case 25:
                    return "getCurrentSyncs";
                case 26:
                    return "getCurrentSyncsAsUser";
                case 27:
                    return "getSyncAdapterTypes";
                case 28:
                    return "getSyncAdapterTypesAsUser";
                case 29:
                    return "getSyncAdapterPackagesForAuthorityAsUser";
                case 30:
                    return "getSyncAdapterPackageAsUser";
                case 31:
                    return "isSyncActive";
                case 32:
                    return "getSyncStatus";
                case 33:
                    return "getSyncStatusAsUser";
                case 34:
                    return "isSyncPending";
                case 35:
                    return "isSyncPendingAsUser";
                case 36:
                    return "addStatusChangeListener";
                case 37:
                    return "removeStatusChangeListener";
                case 38:
                    return "putCache";
                case 39:
                    return "getCache";
                case 40:
                    return "resetTodayStats";
                case 41:
                    return "onDbCorruption";
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
                    android.database.IContentObserver asInterface = android.database.IContentObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterContentObserver(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    android.database.IContentObserver asInterface2 = android.database.IContentObserver.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerContentObserver(uri, readBoolean, asInterface2, readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.net.Uri[] uriArr = (android.net.Uri[]) parcel.createTypedArray(android.net.Uri.CREATOR);
                    android.database.IContentObserver asInterface3 = android.database.IContentObserver.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    notifyChange(uriArr, asInterface3, readBoolean2, readInt3, readInt4, readInt5, readString);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.accounts.Account account = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestSync(account, readString2, bundle, readString3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.content.SyncRequest syncRequest = (android.content.SyncRequest) parcel.readTypedObject(android.content.SyncRequest.CREATOR);
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sync(syncRequest, readString4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.content.SyncRequest syncRequest2 = (android.content.SyncRequest) parcel.readTypedObject(android.content.SyncRequest.CREATOR);
                    int readInt6 = parcel.readInt();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    syncAsUser(syncRequest2, readInt6, readString5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.accounts.Account account2 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString6 = parcel.readString();
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    cancelSync(account2, readString6, componentName);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.accounts.Account account3 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString7 = parcel.readString();
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelSyncAsUser(account3, readString7, componentName2, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.content.SyncRequest syncRequest3 = (android.content.SyncRequest) parcel.readTypedObject(android.content.SyncRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    cancelRequest(syncRequest3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.accounts.Account account4 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean syncAutomatically = getSyncAutomatically(account4, readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(syncAutomatically);
                    return true;
                case 11:
                    android.accounts.Account account5 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString9 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean syncAutomaticallyAsUser = getSyncAutomaticallyAsUser(account5, readString9, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(syncAutomaticallyAsUser);
                    return true;
                case 12:
                    android.accounts.Account account6 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString10 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSyncAutomatically(account6, readString10, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.accounts.Account account7 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString11 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSyncAutomaticallyAsUser(account7, readString11, readBoolean4, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.accounts.Account account8 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString12 = parcel.readString();
                    android.content.ComponentName componentName3 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<android.content.PeriodicSync> periodicSyncs = getPeriodicSyncs(account8, readString12, componentName3);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(periodicSyncs, 1);
                    return true;
                case 15:
                    android.accounts.Account account9 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString13 = parcel.readString();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    addPeriodicSync(account9, readString13, bundle2, readLong);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    android.accounts.Account account10 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString14 = parcel.readString();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    removePeriodicSync(account10, readString14, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.accounts.Account account11 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int isSyncable = getIsSyncable(account11, readString15);
                    parcel2.writeNoException();
                    parcel2.writeInt(isSyncable);
                    return true;
                case 18:
                    android.accounts.Account account12 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString16 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int isSyncableAsUser = getIsSyncableAsUser(account12, readString16, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(isSyncableAsUser);
                    return true;
                case 19:
                    android.accounts.Account account13 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString17 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIsSyncable(account13, readString17, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    android.accounts.Account account14 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString18 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setIsSyncableAsUser(account14, readString18, readInt12, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMasterSyncAutomatically(readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    boolean readBoolean6 = parcel.readBoolean();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMasterSyncAutomaticallyAsUser(readBoolean6, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    boolean masterSyncAutomatically = getMasterSyncAutomatically();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(masterSyncAutomatically);
                    return true;
                case 24:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean masterSyncAutomaticallyAsUser = getMasterSyncAutomaticallyAsUser(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(masterSyncAutomaticallyAsUser);
                    return true;
                case 25:
                    java.util.List<android.content.SyncInfo> currentSyncs = getCurrentSyncs();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(currentSyncs, 1);
                    return true;
                case 26:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.content.SyncInfo> currentSyncsAsUser = getCurrentSyncsAsUser(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(currentSyncsAsUser, 1);
                    return true;
                case 27:
                    android.content.SyncAdapterType[] syncAdapterTypes = getSyncAdapterTypes();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(syncAdapterTypes, 1);
                    return true;
                case 28:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.SyncAdapterType[] syncAdapterTypesAsUser = getSyncAdapterTypesAsUser(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(syncAdapterTypesAsUser, 1);
                    return true;
                case 29:
                    java.lang.String readString19 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String[] syncAdapterPackagesForAuthorityAsUser = getSyncAdapterPackagesForAuthorityAsUser(readString19, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(syncAdapterPackagesForAuthorityAsUser);
                    return true;
                case 30:
                    java.lang.String readString20 = parcel.readString();
                    java.lang.String readString21 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String syncAdapterPackageAsUser = getSyncAdapterPackageAsUser(readString20, readString21, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeString(syncAdapterPackageAsUser);
                    return true;
                case 31:
                    android.accounts.Account account15 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString22 = parcel.readString();
                    android.content.ComponentName componentName4 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSyncActive = isSyncActive(account15, readString22, componentName4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSyncActive);
                    return true;
                case 32:
                    android.accounts.Account account16 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString23 = parcel.readString();
                    android.content.ComponentName componentName5 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.SyncStatusInfo syncStatus = getSyncStatus(account16, readString23, componentName5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(syncStatus, 1);
                    return true;
                case 33:
                    android.accounts.Account account17 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString24 = parcel.readString();
                    android.content.ComponentName componentName6 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.SyncStatusInfo syncStatusAsUser = getSyncStatusAsUser(account17, readString24, componentName6, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(syncStatusAsUser, 1);
                    return true;
                case 34:
                    android.accounts.Account account18 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString25 = parcel.readString();
                    android.content.ComponentName componentName7 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSyncPending = isSyncPending(account18, readString25, componentName7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSyncPending);
                    return true;
                case 35:
                    android.accounts.Account account19 = (android.accounts.Account) parcel.readTypedObject(android.accounts.Account.CREATOR);
                    java.lang.String readString26 = parcel.readString();
                    android.content.ComponentName componentName8 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSyncPendingAsUser = isSyncPendingAsUser(account19, readString26, componentName8, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSyncPendingAsUser);
                    return true;
                case 36:
                    int readInt22 = parcel.readInt();
                    android.content.ISyncStatusObserver asInterface4 = android.content.ISyncStatusObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addStatusChangeListener(readInt22, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    android.content.ISyncStatusObserver asInterface5 = android.content.ISyncStatusObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeStatusChangeListener(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    java.lang.String readString27 = parcel.readString();
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    putCache(readString27, uri2, bundle4, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    java.lang.String readString28 = parcel.readString();
                    android.net.Uri uri3 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.Bundle cache = getCache(readString28, uri3, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cache, 1);
                    return true;
                case 40:
                    resetTodayStats();
                    parcel2.writeNoException();
                    return true;
                case 41:
                    java.lang.String readString29 = parcel.readString();
                    java.lang.String readString30 = parcel.readString();
                    java.lang.String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onDbCorruption(readString29, readString30, readString31);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.IContentService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.IContentService.Stub.DESCRIPTOR;
            }

            @Override // android.content.IContentService
            public void unregisterContentObserver(android.database.IContentObserver iContentObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iContentObserver);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void registerContentObserver(android.net.Uri uri, boolean z, android.database.IContentObserver iContentObserver, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iContentObserver);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void notifyChange(android.net.Uri[] uriArr, android.database.IContentObserver iContentObserver, boolean z, int i, int i2, int i3, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(uriArr, 0);
                    obtain.writeStrongInterface(iContentObserver);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void requestSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void sync(android.content.SyncRequest syncRequest, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(syncRequest, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void syncAsUser(android.content.SyncRequest syncRequest, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(syncRequest, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void cancelSync(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void cancelSyncAsUser(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void cancelRequest(android.content.SyncRequest syncRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(syncRequest, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean getSyncAutomatically(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean getSyncAutomaticallyAsUser(android.accounts.Account account, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setSyncAutomatically(android.accounts.Account account, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setSyncAutomaticallyAsUser(android.accounts.Account account, java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public java.util.List<android.content.PeriodicSync> getPeriodicSyncs(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.PeriodicSync.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void addPeriodicSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void removePeriodicSync(android.accounts.Account account, java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public int getIsSyncable(android.accounts.Account account, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public int getIsSyncableAsUser(android.accounts.Account account, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setIsSyncable(android.accounts.Account account, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setIsSyncableAsUser(android.accounts.Account account, java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setMasterSyncAutomatically(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void setMasterSyncAutomaticallyAsUser(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean getMasterSyncAutomatically() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean getMasterSyncAutomaticallyAsUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public java.util.List<android.content.SyncInfo> getCurrentSyncs() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.SyncInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public java.util.List<android.content.SyncInfo> getCurrentSyncsAsUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.content.SyncInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public android.content.SyncAdapterType[] getSyncAdapterTypes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.SyncAdapterType[]) obtain2.createTypedArray(android.content.SyncAdapterType.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public android.content.SyncAdapterType[] getSyncAdapterTypesAsUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.SyncAdapterType[]) obtain2.createTypedArray(android.content.SyncAdapterType.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public java.lang.String[] getSyncAdapterPackagesForAuthorityAsUser(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public java.lang.String getSyncAdapterPackageAsUser(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean isSyncActive(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public android.content.SyncStatusInfo getSyncStatus(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.SyncStatusInfo) obtain2.readTypedObject(android.content.SyncStatusInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public android.content.SyncStatusInfo getSyncStatusAsUser(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.SyncStatusInfo) obtain2.readTypedObject(android.content.SyncStatusInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean isSyncPending(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public boolean isSyncPendingAsUser(android.accounts.Account account, java.lang.String str, android.content.ComponentName componentName, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(account, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void addStatusChangeListener(int i, android.content.ISyncStatusObserver iSyncStatusObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSyncStatusObserver);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void removeStatusChangeListener(android.content.ISyncStatusObserver iSyncStatusObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSyncStatusObserver);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void putCache(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public android.os.Bundle getCache(java.lang.String str, android.net.Uri uri, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void resetTodayStats() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.IContentService
            public void onDbCorruption(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.IContentService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void isSyncActive_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.READ_SYNC_STATS, getCallingPid(), getCallingUid());
        }

        protected void isSyncPendingAsUser_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.READ_SYNC_STATS, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 40;
        }
    }
}
