package android.app.backup;

/* loaded from: classes.dex */
public interface IFullBackupRestoreObserver extends android.os.IInterface {
    void onBackupPackage(java.lang.String str) throws android.os.RemoteException;

    void onEndBackup() throws android.os.RemoteException;

    void onEndRestore() throws android.os.RemoteException;

    void onRestorePackage(java.lang.String str) throws android.os.RemoteException;

    void onStartBackup() throws android.os.RemoteException;

    void onStartRestore() throws android.os.RemoteException;

    void onTimeout() throws android.os.RemoteException;

    public static class Default implements android.app.backup.IFullBackupRestoreObserver {
        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onStartBackup() throws android.os.RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onBackupPackage(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onEndBackup() throws android.os.RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onStartRestore() throws android.os.RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onRestorePackage(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onEndRestore() throws android.os.RemoteException {
        }

        @Override // android.app.backup.IFullBackupRestoreObserver
        public void onTimeout() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.backup.IFullBackupRestoreObserver {
        public static final java.lang.String DESCRIPTOR = "android.app.backup.IFullBackupRestoreObserver";
        static final int TRANSACTION_onBackupPackage = 2;
        static final int TRANSACTION_onEndBackup = 3;
        static final int TRANSACTION_onEndRestore = 6;
        static final int TRANSACTION_onRestorePackage = 5;
        static final int TRANSACTION_onStartBackup = 1;
        static final int TRANSACTION_onStartRestore = 4;
        static final int TRANSACTION_onTimeout = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.backup.IFullBackupRestoreObserver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.backup.IFullBackupRestoreObserver)) {
                return (android.app.backup.IFullBackupRestoreObserver) queryLocalInterface;
            }
            return new android.app.backup.IFullBackupRestoreObserver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStartBackup";
                case 2:
                    return "onBackupPackage";
                case 3:
                    return "onEndBackup";
                case 4:
                    return "onStartRestore";
                case 5:
                    return "onRestorePackage";
                case 6:
                    return "onEndRestore";
                case 7:
                    return "onTimeout";
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
                    onStartBackup();
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBackupPackage(readString);
                    return true;
                case 3:
                    onEndBackup();
                    return true;
                case 4:
                    onStartRestore();
                    return true;
                case 5:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onRestorePackage(readString2);
                    return true;
                case 6:
                    onEndRestore();
                    return true;
                case 7:
                    onTimeout();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.backup.IFullBackupRestoreObserver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.backup.IFullBackupRestoreObserver.Stub.DESCRIPTOR;
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onStartBackup() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.backup.IFullBackupRestoreObserver.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onBackupPackage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.backup.IFullBackupRestoreObserver.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onEndBackup() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.backup.IFullBackupRestoreObserver.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onStartRestore() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.backup.IFullBackupRestoreObserver.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onRestorePackage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.backup.IFullBackupRestoreObserver.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onEndRestore() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.backup.IFullBackupRestoreObserver.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IFullBackupRestoreObserver
            public void onTimeout() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.backup.IFullBackupRestoreObserver.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
