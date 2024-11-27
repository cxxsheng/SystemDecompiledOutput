package android.app.backup;

/* loaded from: classes.dex */
public interface IRestoreObserver extends android.os.IInterface {
    void onUpdate(int i, java.lang.String str) throws android.os.RemoteException;

    void restoreFinished(int i) throws android.os.RemoteException;

    void restoreSetsAvailable(android.app.backup.RestoreSet[] restoreSetArr) throws android.os.RemoteException;

    void restoreStarting(int i) throws android.os.RemoteException;

    public static class Default implements android.app.backup.IRestoreObserver {
        @Override // android.app.backup.IRestoreObserver
        public void restoreSetsAvailable(android.app.backup.RestoreSet[] restoreSetArr) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IRestoreObserver
        public void restoreStarting(int i) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IRestoreObserver
        public void onUpdate(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.backup.IRestoreObserver
        public void restoreFinished(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.backup.IRestoreObserver {
        public static final java.lang.String DESCRIPTOR = "android.app.backup.IRestoreObserver";
        static final int TRANSACTION_onUpdate = 3;
        static final int TRANSACTION_restoreFinished = 4;
        static final int TRANSACTION_restoreSetsAvailable = 1;
        static final int TRANSACTION_restoreStarting = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.app.backup.IRestoreObserver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.backup.IRestoreObserver)) {
                return (android.app.backup.IRestoreObserver) queryLocalInterface;
            }
            return new android.app.backup.IRestoreObserver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "restoreSetsAvailable";
                case 2:
                    return "restoreStarting";
                case 3:
                    return "onUpdate";
                case 4:
                    return "restoreFinished";
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
                    android.app.backup.RestoreSet[] restoreSetArr = (android.app.backup.RestoreSet[]) parcel.createTypedArray(android.app.backup.RestoreSet.CREATOR);
                    parcel.enforceNoDataAvail();
                    restoreSetsAvailable(restoreSetArr);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreStarting(readInt);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onUpdate(readInt2, readString);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreFinished(readInt3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.backup.IRestoreObserver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.backup.IRestoreObserver.Stub.DESCRIPTOR;
            }

            @Override // android.app.backup.IRestoreObserver
            public void restoreSetsAvailable(android.app.backup.RestoreSet[] restoreSetArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.backup.IRestoreObserver.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(restoreSetArr, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreObserver
            public void restoreStarting(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.backup.IRestoreObserver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreObserver
            public void onUpdate(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.backup.IRestoreObserver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.backup.IRestoreObserver
            public void restoreFinished(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.backup.IRestoreObserver.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
