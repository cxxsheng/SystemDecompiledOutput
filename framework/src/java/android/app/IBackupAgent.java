package android.app;

/* loaded from: classes.dex */
public interface IBackupAgent extends android.os.IInterface {
    void clearBackupRestoreEventLogger() throws android.os.RemoteException;

    void doBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3, long j, android.app.backup.IBackupCallback iBackupCallback, int i) throws android.os.RemoteException;

    void doFullBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, android.app.backup.IBackupManager iBackupManager, int i2) throws android.os.RemoteException;

    void doMeasureFullBackup(long j, int i, android.app.backup.IBackupManager iBackupManager, int i2) throws android.os.RemoteException;

    void doQuotaExceeded(long j, long j2, android.app.backup.IBackupCallback iBackupCallback) throws android.os.RemoteException;

    void doRestore(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, android.os.ParcelFileDescriptor parcelFileDescriptor2, int i, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException;

    void doRestoreFile(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, java.lang.String str, java.lang.String str2, long j2, long j3, int i2, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException;

    void doRestoreFinished(int i, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException;

    void doRestoreWithExcludedKeys(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, android.os.ParcelFileDescriptor parcelFileDescriptor2, int i, android.app.backup.IBackupManager iBackupManager, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    void fail(java.lang.String str) throws android.os.RemoteException;

    void getLoggerResults(com.android.internal.infra.AndroidFuture<java.util.List<android.app.backup.BackupRestoreEventLogger.DataTypeResult>> androidFuture) throws android.os.RemoteException;

    void getOperationType(com.android.internal.infra.AndroidFuture<java.lang.Integer> androidFuture) throws android.os.RemoteException;

    public static class Default implements android.app.IBackupAgent {
        @Override // android.app.IBackupAgent
        public void doBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3, long j, android.app.backup.IBackupCallback iBackupCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doRestore(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, android.os.ParcelFileDescriptor parcelFileDescriptor2, int i, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doRestoreWithExcludedKeys(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, android.os.ParcelFileDescriptor parcelFileDescriptor2, int i, android.app.backup.IBackupManager iBackupManager, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doFullBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, android.app.backup.IBackupManager iBackupManager, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doMeasureFullBackup(long j, int i, android.app.backup.IBackupManager iBackupManager, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doQuotaExceeded(long j, long j2, android.app.backup.IBackupCallback iBackupCallback) throws android.os.RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doRestoreFile(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, java.lang.String str, java.lang.String str2, long j2, long j3, int i2, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void doRestoreFinished(int i, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void fail(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void getLoggerResults(com.android.internal.infra.AndroidFuture<java.util.List<android.app.backup.BackupRestoreEventLogger.DataTypeResult>> androidFuture) throws android.os.RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void getOperationType(com.android.internal.infra.AndroidFuture<java.lang.Integer> androidFuture) throws android.os.RemoteException {
        }

        @Override // android.app.IBackupAgent
        public void clearBackupRestoreEventLogger() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IBackupAgent {
        public static final java.lang.String DESCRIPTOR = "android.app.IBackupAgent";
        static final int TRANSACTION_clearBackupRestoreEventLogger = 12;
        static final int TRANSACTION_doBackup = 1;
        static final int TRANSACTION_doFullBackup = 4;
        static final int TRANSACTION_doMeasureFullBackup = 5;
        static final int TRANSACTION_doQuotaExceeded = 6;
        static final int TRANSACTION_doRestore = 2;
        static final int TRANSACTION_doRestoreFile = 7;
        static final int TRANSACTION_doRestoreFinished = 8;
        static final int TRANSACTION_doRestoreWithExcludedKeys = 3;
        static final int TRANSACTION_fail = 9;
        static final int TRANSACTION_getLoggerResults = 10;
        static final int TRANSACTION_getOperationType = 11;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.IBackupAgent asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IBackupAgent)) {
                return (android.app.IBackupAgent) queryLocalInterface;
            }
            return new android.app.IBackupAgent.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "doBackup";
                case 2:
                    return "doRestore";
                case 3:
                    return "doRestoreWithExcludedKeys";
                case 4:
                    return "doFullBackup";
                case 5:
                    return "doMeasureFullBackup";
                case 6:
                    return "doQuotaExceeded";
                case 7:
                    return "doRestoreFile";
                case 8:
                    return "doRestoreFinished";
                case 9:
                    return "fail";
                case 10:
                    return "getLoggerResults";
                case 11:
                    return "getOperationType";
                case 12:
                    return "clearBackupRestoreEventLogger";
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
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    android.os.ParcelFileDescriptor parcelFileDescriptor3 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    long readLong = parcel.readLong();
                    android.app.backup.IBackupCallback asInterface = android.app.backup.IBackupCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    doBackup(parcelFileDescriptor, parcelFileDescriptor2, parcelFileDescriptor3, readLong, asInterface, readInt);
                    return true;
                case 2:
                    android.os.ParcelFileDescriptor parcelFileDescriptor4 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    long readLong2 = parcel.readLong();
                    android.os.ParcelFileDescriptor parcelFileDescriptor5 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    int readInt2 = parcel.readInt();
                    android.app.backup.IBackupManager asInterface2 = android.app.backup.IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    doRestore(parcelFileDescriptor4, readLong2, parcelFileDescriptor5, readInt2, asInterface2);
                    return true;
                case 3:
                    android.os.ParcelFileDescriptor parcelFileDescriptor6 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    long readLong3 = parcel.readLong();
                    android.os.ParcelFileDescriptor parcelFileDescriptor7 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    int readInt3 = parcel.readInt();
                    android.app.backup.IBackupManager asInterface3 = android.app.backup.IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    doRestoreWithExcludedKeys(parcelFileDescriptor6, readLong3, parcelFileDescriptor7, readInt3, asInterface3, createStringArrayList);
                    return true;
                case 4:
                    android.os.ParcelFileDescriptor parcelFileDescriptor8 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    long readLong4 = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    android.app.backup.IBackupManager asInterface4 = android.app.backup.IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    doFullBackup(parcelFileDescriptor8, readLong4, readInt4, asInterface4, readInt5);
                    return true;
                case 5:
                    long readLong5 = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    android.app.backup.IBackupManager asInterface5 = android.app.backup.IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    doMeasureFullBackup(readLong5, readInt6, asInterface5, readInt7);
                    return true;
                case 6:
                    long readLong6 = parcel.readLong();
                    long readLong7 = parcel.readLong();
                    android.app.backup.IBackupCallback asInterface6 = android.app.backup.IBackupCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    doQuotaExceeded(readLong6, readLong7, asInterface6);
                    return true;
                case 7:
                    android.os.ParcelFileDescriptor parcelFileDescriptor9 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    long readLong8 = parcel.readLong();
                    int readInt8 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    long readLong9 = parcel.readLong();
                    long readLong10 = parcel.readLong();
                    int readInt9 = parcel.readInt();
                    android.app.backup.IBackupManager asInterface7 = android.app.backup.IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    doRestoreFile(parcelFileDescriptor9, readLong8, readInt8, readString, readString2, readLong9, readLong10, readInt9, asInterface7);
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    android.app.backup.IBackupManager asInterface8 = android.app.backup.IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    doRestoreFinished(readInt10, asInterface8);
                    return true;
                case 9:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    fail(readString3);
                    return true;
                case 10:
                    com.android.internal.infra.AndroidFuture<java.util.List<android.app.backup.BackupRestoreEventLogger.DataTypeResult>> androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getLoggerResults(androidFuture);
                    return true;
                case 11:
                    com.android.internal.infra.AndroidFuture<java.lang.Integer> androidFuture2 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getOperationType(androidFuture2);
                    return true;
                case 12:
                    clearBackupRestoreEventLogger();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IBackupAgent {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IBackupAgent.Stub.DESCRIPTOR;
            }

            @Override // android.app.IBackupAgent
            public void doBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3, long j, android.app.backup.IBackupCallback iBackupCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IBackupAgent.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(parcelFileDescriptor2, 0);
                    obtain.writeTypedObject(parcelFileDescriptor3, 0);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iBackupCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestore(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, android.os.ParcelFileDescriptor parcelFileDescriptor2, int i, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IBackupAgent.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(parcelFileDescriptor2, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBackupManager);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestoreWithExcludedKeys(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, android.os.ParcelFileDescriptor parcelFileDescriptor2, int i, android.app.backup.IBackupManager iBackupManager, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IBackupAgent.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(parcelFileDescriptor2, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBackupManager);
                    obtain.writeStringList(list);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doFullBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, android.app.backup.IBackupManager iBackupManager, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IBackupAgent.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBackupManager);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doMeasureFullBackup(long j, int i, android.app.backup.IBackupManager iBackupManager, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IBackupAgent.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBackupManager);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doQuotaExceeded(long j, long j2, android.app.backup.IBackupCallback iBackupCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IBackupAgent.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeStrongInterface(iBackupCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestoreFile(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, java.lang.String str, java.lang.String str2, long j2, long j3, int i2, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IBackupAgent.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iBackupManager);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void doRestoreFinished(int i, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IBackupAgent.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBackupManager);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void fail(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IBackupAgent.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void getLoggerResults(com.android.internal.infra.AndroidFuture<java.util.List<android.app.backup.BackupRestoreEventLogger.DataTypeResult>> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IBackupAgent.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void getOperationType(com.android.internal.infra.AndroidFuture<java.lang.Integer> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IBackupAgent.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IBackupAgent
            public void clearBackupRestoreEventLogger() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IBackupAgent.Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
