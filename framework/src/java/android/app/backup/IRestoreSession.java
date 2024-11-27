package android.app.backup;

/* loaded from: classes.dex */
public interface IRestoreSession extends android.os.IInterface {
    void endRestoreSession() throws android.os.RemoteException;

    int getAvailableRestoreSets(android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) throws android.os.RemoteException;

    int restoreAll(long j, android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) throws android.os.RemoteException;

    int restorePackage(java.lang.String str, android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) throws android.os.RemoteException;

    int restorePackages(long j, android.app.backup.IRestoreObserver iRestoreObserver, java.lang.String[] strArr, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) throws android.os.RemoteException;

    public static class Default implements android.app.backup.IRestoreSession {
        @Override // android.app.backup.IRestoreSession
        public int getAvailableRestoreSets(android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.backup.IRestoreSession
        public int restoreAll(long j, android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.backup.IRestoreSession
        public int restorePackages(long j, android.app.backup.IRestoreObserver iRestoreObserver, java.lang.String[] strArr, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.backup.IRestoreSession
        public int restorePackage(java.lang.String str, android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.backup.IRestoreSession
        public void endRestoreSession() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.backup.IRestoreSession {
        public static final java.lang.String DESCRIPTOR = "android.app.backup.IRestoreSession";
        static final int TRANSACTION_endRestoreSession = 5;
        static final int TRANSACTION_getAvailableRestoreSets = 1;
        static final int TRANSACTION_restoreAll = 2;
        static final int TRANSACTION_restorePackage = 4;
        static final int TRANSACTION_restorePackages = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.backup.IRestoreSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.backup.IRestoreSession)) {
                return (android.app.backup.IRestoreSession) queryLocalInterface;
            }
            return new android.app.backup.IRestoreSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAvailableRestoreSets";
                case 2:
                    return "restoreAll";
                case 3:
                    return "restorePackages";
                case 4:
                    return "restorePackage";
                case 5:
                    return "endRestoreSession";
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
                    android.app.backup.IRestoreObserver asInterface = android.app.backup.IRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                    android.app.backup.IBackupManagerMonitor asInterface2 = android.app.backup.IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int availableRestoreSets = getAvailableRestoreSets(asInterface, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(availableRestoreSets);
                    return true;
                case 2:
                    long readLong = parcel.readLong();
                    android.app.backup.IRestoreObserver asInterface3 = android.app.backup.IRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                    android.app.backup.IBackupManagerMonitor asInterface4 = android.app.backup.IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int restoreAll = restoreAll(readLong, asInterface3, asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeInt(restoreAll);
                    return true;
                case 3:
                    long readLong2 = parcel.readLong();
                    android.app.backup.IRestoreObserver asInterface5 = android.app.backup.IRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    android.app.backup.IBackupManagerMonitor asInterface6 = android.app.backup.IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int restorePackages = restorePackages(readLong2, asInterface5, createStringArray, asInterface6);
                    parcel2.writeNoException();
                    parcel2.writeInt(restorePackages);
                    return true;
                case 4:
                    java.lang.String readString = parcel.readString();
                    android.app.backup.IRestoreObserver asInterface7 = android.app.backup.IRestoreObserver.Stub.asInterface(parcel.readStrongBinder());
                    android.app.backup.IBackupManagerMonitor asInterface8 = android.app.backup.IBackupManagerMonitor.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int restorePackage = restorePackage(readString, asInterface7, asInterface8);
                    parcel2.writeNoException();
                    parcel2.writeInt(restorePackage);
                    return true;
                case 5:
                    endRestoreSession();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.backup.IRestoreSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.backup.IRestoreSession.Stub.DESCRIPTOR;
            }

            @Override // android.app.backup.IRestoreSession
            public int getAvailableRestoreSets(android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IRestoreSession.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRestoreObserver);
                    obtain.writeStrongInterface(iBackupManagerMonitor);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public int restoreAll(long j, android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IRestoreSession.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iRestoreObserver);
                    obtain.writeStrongInterface(iBackupManagerMonitor);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public int restorePackages(long j, android.app.backup.IRestoreObserver iRestoreObserver, java.lang.String[] strArr, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IRestoreSession.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iRestoreObserver);
                    obtain.writeStringArray(strArr);
                    obtain.writeStrongInterface(iBackupManagerMonitor);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public int restorePackage(java.lang.String str, android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IRestoreSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iRestoreObserver);
                    obtain.writeStrongInterface(iBackupManagerMonitor);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreSession
            public void endRestoreSession() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.backup.IRestoreSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
