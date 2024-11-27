package com.android.internal.backup;

/* loaded from: classes4.dex */
public interface IBackupTransport extends android.os.IInterface {
    void abortFullRestore(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException;

    void cancelFullBackup(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException;

    void checkFullBackupSize(long j, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException;

    void clearBackupData(android.content.pm.PackageInfo packageInfo, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException;

    void configurationIntent(com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture) throws android.os.RemoteException;

    void currentDestinationString(com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException;

    void dataManagementIntent(com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture) throws android.os.RemoteException;

    void dataManagementIntentLabel(com.android.internal.infra.AndroidFuture<java.lang.CharSequence> androidFuture) throws android.os.RemoteException;

    void finishBackup(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException;

    void finishRestore(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException;

    void getAvailableRestoreSets(com.android.internal.infra.AndroidFuture<java.util.List<android.app.backup.RestoreSet>> androidFuture) throws android.os.RemoteException;

    void getBackupManagerMonitor(com.android.internal.infra.AndroidFuture<android.app.backup.IBackupManagerMonitor> androidFuture) throws android.os.RemoteException;

    void getBackupQuota(java.lang.String str, boolean z, com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException;

    void getCurrentRestoreSet(com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException;

    void getNextFullRestoreDataChunk(android.os.ParcelFileDescriptor parcelFileDescriptor, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException;

    void getRestoreData(android.os.ParcelFileDescriptor parcelFileDescriptor, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException;

    void getTransportFlags(com.android.internal.infra.AndroidFuture<java.lang.Integer> androidFuture) throws android.os.RemoteException;

    void initializeDevice(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException;

    void isAppEligibleForBackup(android.content.pm.PackageInfo packageInfo, boolean z, com.android.internal.infra.AndroidFuture<java.lang.Boolean> androidFuture) throws android.os.RemoteException;

    void name(com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException;

    void nextRestorePackage(com.android.internal.infra.AndroidFuture<android.app.backup.RestoreDescription> androidFuture) throws android.os.RemoteException;

    void performBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor, int i, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException;

    void performFullBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor, int i, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException;

    void requestBackupTime(com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException;

    void requestFullBackupTime(com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException;

    void sendBackupData(int i, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException;

    void startRestore(long j, android.content.pm.PackageInfo[] packageInfoArr, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException;

    void transportDirName(com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException;

    public static class Default implements com.android.internal.backup.IBackupTransport {
        @Override // com.android.internal.backup.IBackupTransport
        public void name(com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void configurationIntent(com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void currentDestinationString(com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void dataManagementIntent(com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void dataManagementIntentLabel(com.android.internal.infra.AndroidFuture<java.lang.CharSequence> androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void transportDirName(com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void requestBackupTime(com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void initializeDevice(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void performBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor, int i, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void clearBackupData(android.content.pm.PackageInfo packageInfo, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void finishBackup(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getAvailableRestoreSets(com.android.internal.infra.AndroidFuture<java.util.List<android.app.backup.RestoreSet>> androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getCurrentRestoreSet(com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void startRestore(long j, android.content.pm.PackageInfo[] packageInfoArr, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void nextRestorePackage(com.android.internal.infra.AndroidFuture<android.app.backup.RestoreDescription> androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getRestoreData(android.os.ParcelFileDescriptor parcelFileDescriptor, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void finishRestore(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void requestFullBackupTime(com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void performFullBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor, int i, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void checkFullBackupSize(long j, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void sendBackupData(int i, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void cancelFullBackup(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void isAppEligibleForBackup(android.content.pm.PackageInfo packageInfo, boolean z, com.android.internal.infra.AndroidFuture<java.lang.Boolean> androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getBackupQuota(java.lang.String str, boolean z, com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getNextFullRestoreDataChunk(android.os.ParcelFileDescriptor parcelFileDescriptor, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void abortFullRestore(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getTransportFlags(com.android.internal.infra.AndroidFuture<java.lang.Integer> androidFuture) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IBackupTransport
        public void getBackupManagerMonitor(com.android.internal.infra.AndroidFuture<android.app.backup.IBackupManagerMonitor> androidFuture) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.backup.IBackupTransport {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.backup.IBackupTransport";
        static final int TRANSACTION_abortFullRestore = 26;
        static final int TRANSACTION_cancelFullBackup = 22;
        static final int TRANSACTION_checkFullBackupSize = 20;
        static final int TRANSACTION_clearBackupData = 10;
        static final int TRANSACTION_configurationIntent = 2;
        static final int TRANSACTION_currentDestinationString = 3;
        static final int TRANSACTION_dataManagementIntent = 4;
        static final int TRANSACTION_dataManagementIntentLabel = 5;
        static final int TRANSACTION_finishBackup = 11;
        static final int TRANSACTION_finishRestore = 17;
        static final int TRANSACTION_getAvailableRestoreSets = 12;
        static final int TRANSACTION_getBackupManagerMonitor = 28;
        static final int TRANSACTION_getBackupQuota = 24;
        static final int TRANSACTION_getCurrentRestoreSet = 13;
        static final int TRANSACTION_getNextFullRestoreDataChunk = 25;
        static final int TRANSACTION_getRestoreData = 16;
        static final int TRANSACTION_getTransportFlags = 27;
        static final int TRANSACTION_initializeDevice = 8;
        static final int TRANSACTION_isAppEligibleForBackup = 23;
        static final int TRANSACTION_name = 1;
        static final int TRANSACTION_nextRestorePackage = 15;
        static final int TRANSACTION_performBackup = 9;
        static final int TRANSACTION_performFullBackup = 19;
        static final int TRANSACTION_requestBackupTime = 7;
        static final int TRANSACTION_requestFullBackupTime = 18;
        static final int TRANSACTION_sendBackupData = 21;
        static final int TRANSACTION_startRestore = 14;
        static final int TRANSACTION_transportDirName = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.backup.IBackupTransport asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.backup.IBackupTransport)) {
                return (com.android.internal.backup.IBackupTransport) queryLocalInterface;
            }
            return new com.android.internal.backup.IBackupTransport.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "name";
                case 2:
                    return "configurationIntent";
                case 3:
                    return "currentDestinationString";
                case 4:
                    return "dataManagementIntent";
                case 5:
                    return "dataManagementIntentLabel";
                case 6:
                    return "transportDirName";
                case 7:
                    return "requestBackupTime";
                case 8:
                    return "initializeDevice";
                case 9:
                    return "performBackup";
                case 10:
                    return "clearBackupData";
                case 11:
                    return "finishBackup";
                case 12:
                    return "getAvailableRestoreSets";
                case 13:
                    return "getCurrentRestoreSet";
                case 14:
                    return "startRestore";
                case 15:
                    return "nextRestorePackage";
                case 16:
                    return "getRestoreData";
                case 17:
                    return "finishRestore";
                case 18:
                    return "requestFullBackupTime";
                case 19:
                    return "performFullBackup";
                case 20:
                    return "checkFullBackupSize";
                case 21:
                    return "sendBackupData";
                case 22:
                    return "cancelFullBackup";
                case 23:
                    return "isAppEligibleForBackup";
                case 24:
                    return "getBackupQuota";
                case 25:
                    return "getNextFullRestoreDataChunk";
                case 26:
                    return "abortFullRestore";
                case 27:
                    return "getTransportFlags";
                case 28:
                    return "getBackupManagerMonitor";
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
                    com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    name(androidFuture);
                    return true;
                case 2:
                    com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture2 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    configurationIntent(androidFuture2);
                    return true;
                case 3:
                    com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture3 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    currentDestinationString(androidFuture3);
                    return true;
                case 4:
                    com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture4 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    dataManagementIntent(androidFuture4);
                    return true;
                case 5:
                    com.android.internal.infra.AndroidFuture<java.lang.CharSequence> androidFuture5 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    dataManagementIntentLabel(androidFuture5);
                    return true;
                case 6:
                    com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture6 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    transportDirName(androidFuture6);
                    return true;
                case 7:
                    com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture7 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestBackupTime(androidFuture7);
                    return true;
                case 8:
                    com.android.internal.backup.ITransportStatusCallback asInterface = com.android.internal.backup.ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    initializeDevice(asInterface);
                    return true;
                case 9:
                    android.content.pm.PackageInfo packageInfo = (android.content.pm.PackageInfo) parcel.readTypedObject(android.content.pm.PackageInfo.CREATOR);
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    int readInt = parcel.readInt();
                    com.android.internal.backup.ITransportStatusCallback asInterface2 = com.android.internal.backup.ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    performBackup(packageInfo, parcelFileDescriptor, readInt, asInterface2);
                    return true;
                case 10:
                    android.content.pm.PackageInfo packageInfo2 = (android.content.pm.PackageInfo) parcel.readTypedObject(android.content.pm.PackageInfo.CREATOR);
                    com.android.internal.backup.ITransportStatusCallback asInterface3 = com.android.internal.backup.ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    clearBackupData(packageInfo2, asInterface3);
                    return true;
                case 11:
                    com.android.internal.backup.ITransportStatusCallback asInterface4 = com.android.internal.backup.ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    finishBackup(asInterface4);
                    return true;
                case 12:
                    com.android.internal.infra.AndroidFuture<java.util.List<android.app.backup.RestoreSet>> androidFuture8 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAvailableRestoreSets(androidFuture8);
                    return true;
                case 13:
                    com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture9 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getCurrentRestoreSet(androidFuture9);
                    return true;
                case 14:
                    long readLong = parcel.readLong();
                    android.content.pm.PackageInfo[] packageInfoArr = (android.content.pm.PackageInfo[]) parcel.createTypedArray(android.content.pm.PackageInfo.CREATOR);
                    com.android.internal.backup.ITransportStatusCallback asInterface5 = com.android.internal.backup.ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startRestore(readLong, packageInfoArr, asInterface5);
                    return true;
                case 15:
                    com.android.internal.infra.AndroidFuture<android.app.backup.RestoreDescription> androidFuture10 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    nextRestorePackage(androidFuture10);
                    return true;
                case 16:
                    android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    com.android.internal.backup.ITransportStatusCallback asInterface6 = com.android.internal.backup.ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getRestoreData(parcelFileDescriptor2, asInterface6);
                    return true;
                case 17:
                    com.android.internal.backup.ITransportStatusCallback asInterface7 = com.android.internal.backup.ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    finishRestore(asInterface7);
                    return true;
                case 18:
                    com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture11 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestFullBackupTime(androidFuture11);
                    return true;
                case 19:
                    android.content.pm.PackageInfo packageInfo3 = (android.content.pm.PackageInfo) parcel.readTypedObject(android.content.pm.PackageInfo.CREATOR);
                    android.os.ParcelFileDescriptor parcelFileDescriptor3 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    int readInt2 = parcel.readInt();
                    com.android.internal.backup.ITransportStatusCallback asInterface8 = com.android.internal.backup.ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    performFullBackup(packageInfo3, parcelFileDescriptor3, readInt2, asInterface8);
                    return true;
                case 20:
                    long readLong2 = parcel.readLong();
                    com.android.internal.backup.ITransportStatusCallback asInterface9 = com.android.internal.backup.ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    checkFullBackupSize(readLong2, asInterface9);
                    return true;
                case 21:
                    int readInt3 = parcel.readInt();
                    com.android.internal.backup.ITransportStatusCallback asInterface10 = com.android.internal.backup.ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendBackupData(readInt3, asInterface10);
                    return true;
                case 22:
                    com.android.internal.backup.ITransportStatusCallback asInterface11 = com.android.internal.backup.ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelFullBackup(asInterface11);
                    return true;
                case 23:
                    android.content.pm.PackageInfo packageInfo4 = (android.content.pm.PackageInfo) parcel.readTypedObject(android.content.pm.PackageInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    com.android.internal.infra.AndroidFuture<java.lang.Boolean> androidFuture12 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    isAppEligibleForBackup(packageInfo4, readBoolean, androidFuture12);
                    return true;
                case 24:
                    java.lang.String readString = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture13 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getBackupQuota(readString, readBoolean2, androidFuture13);
                    return true;
                case 25:
                    android.os.ParcelFileDescriptor parcelFileDescriptor4 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    com.android.internal.backup.ITransportStatusCallback asInterface12 = com.android.internal.backup.ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getNextFullRestoreDataChunk(parcelFileDescriptor4, asInterface12);
                    return true;
                case 26:
                    com.android.internal.backup.ITransportStatusCallback asInterface13 = com.android.internal.backup.ITransportStatusCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    abortFullRestore(asInterface13);
                    return true;
                case 27:
                    com.android.internal.infra.AndroidFuture<java.lang.Integer> androidFuture14 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getTransportFlags(androidFuture14);
                    return true;
                case 28:
                    com.android.internal.infra.AndroidFuture<android.app.backup.IBackupManagerMonitor> androidFuture15 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getBackupManagerMonitor(androidFuture15);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.backup.IBackupTransport {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void name(com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void configurationIntent(com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void currentDestinationString(com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void dataManagementIntent(com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void dataManagementIntentLabel(com.android.internal.infra.AndroidFuture<java.lang.CharSequence> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void transportDirName(com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void requestBackupTime(com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void initializeDevice(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void performBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor, int i, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(packageInfo, 0);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void clearBackupData(android.content.pm.PackageInfo packageInfo, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(packageInfo, 0);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void finishBackup(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getAvailableRestoreSets(com.android.internal.infra.AndroidFuture<java.util.List<android.app.backup.RestoreSet>> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getCurrentRestoreSet(com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void startRestore(long j, android.content.pm.PackageInfo[] packageInfoArr, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedArray(packageInfoArr, 0);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void nextRestorePackage(com.android.internal.infra.AndroidFuture<android.app.backup.RestoreDescription> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getRestoreData(android.os.ParcelFileDescriptor parcelFileDescriptor, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void finishRestore(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void requestFullBackupTime(com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void performFullBackup(android.content.pm.PackageInfo packageInfo, android.os.ParcelFileDescriptor parcelFileDescriptor, int i, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(packageInfo, 0);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void checkFullBackupSize(long j, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void sendBackupData(int i, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void cancelFullBackup(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void isAppEligibleForBackup(android.content.pm.PackageInfo packageInfo, boolean z, com.android.internal.infra.AndroidFuture<java.lang.Boolean> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(packageInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getBackupQuota(java.lang.String str, boolean z, com.android.internal.infra.AndroidFuture<java.lang.Long> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getNextFullRestoreDataChunk(android.os.ParcelFileDescriptor parcelFileDescriptor, com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void abortFullRestore(com.android.internal.backup.ITransportStatusCallback iTransportStatusCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTransportStatusCallback);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getTransportFlags(com.android.internal.infra.AndroidFuture<java.lang.Integer> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IBackupTransport
            public void getBackupManagerMonitor(com.android.internal.infra.AndroidFuture<android.app.backup.IBackupManagerMonitor> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IBackupTransport.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 27;
        }
    }
}
