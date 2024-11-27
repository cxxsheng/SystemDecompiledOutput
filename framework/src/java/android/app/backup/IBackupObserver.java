package android.app.backup;

/* loaded from: classes.dex */
public interface IBackupObserver extends android.os.IInterface {
    void backupFinished(int i) throws android.os.RemoteException;

    void onResult(java.lang.String str, int i) throws android.os.RemoteException;

    void onUpdate(java.lang.String str, android.app.backup.BackupProgress backupProgress) throws android.os.RemoteException;

    public static class Default implements android.app.backup.IBackupObserver {
        @Override // android.app.backup.IBackupObserver
        public void onUpdate(java.lang.String str, android.app.backup.BackupProgress backupProgress) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupObserver
        public void onResult(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IBackupObserver
        public void backupFinished(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.backup.IBackupObserver {
        public static final java.lang.String DESCRIPTOR = "android.app.backup.IBackupObserver";
        static final int TRANSACTION_backupFinished = 3;
        static final int TRANSACTION_onResult = 2;
        static final int TRANSACTION_onUpdate = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.backup.IBackupObserver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.backup.IBackupObserver)) {
                return (android.app.backup.IBackupObserver) queryLocalInterface;
            }
            return new android.app.backup.IBackupObserver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUpdate";
                case 2:
                    return "onResult";
                case 3:
                    return "backupFinished";
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
                    java.lang.String readString = parcel.readString();
                    android.app.backup.BackupProgress backupProgress = (android.app.backup.BackupProgress) parcel.readTypedObject(android.app.backup.BackupProgress.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUpdate(readString, backupProgress);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onResult(readString2, readInt);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    backupFinished(readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.backup.IBackupObserver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.backup.IBackupObserver.Stub.DESCRIPTOR;
            }

            @Override // android.app.backup.IBackupObserver
            public void onUpdate(java.lang.String str, android.app.backup.BackupProgress backupProgress) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupObserver.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(backupProgress, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupObserver
            public void onResult(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupObserver.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IBackupObserver
            public void backupFinished(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.backup.IBackupObserver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
