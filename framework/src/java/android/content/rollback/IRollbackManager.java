package android.content.rollback;

/* loaded from: classes.dex */
public interface IRollbackManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.content.rollback.IRollbackManager";

    void blockRollbackManager(long j) throws android.os.RemoteException;

    void commitRollback(int i, android.content.pm.ParceledListSlice parceledListSlice, java.lang.String str, android.content.IntentSender intentSender) throws android.os.RemoteException;

    void expireRollbackForPackage(java.lang.String str) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getAvailableRollbacks() throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getRecentlyCommittedRollbacks() throws android.os.RemoteException;

    int notifyStagedSession(int i) throws android.os.RemoteException;

    void reloadPersistedData() throws android.os.RemoteException;

    void snapshotAndRestoreUserData(java.lang.String str, int[] iArr, int i, long j, java.lang.String str2, int i2) throws android.os.RemoteException;

    public static class Default implements android.content.rollback.IRollbackManager {
        @Override // android.content.rollback.IRollbackManager
        public android.content.pm.ParceledListSlice getAvailableRollbacks() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.rollback.IRollbackManager
        public android.content.pm.ParceledListSlice getRecentlyCommittedRollbacks() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.rollback.IRollbackManager
        public void commitRollback(int i, android.content.pm.ParceledListSlice parceledListSlice, java.lang.String str, android.content.IntentSender intentSender) throws android.os.RemoteException {
        }

        @Override // android.content.rollback.IRollbackManager
        public void snapshotAndRestoreUserData(java.lang.String str, int[] iArr, int i, long j, java.lang.String str2, int i2) throws android.os.RemoteException {
        }

        @Override // android.content.rollback.IRollbackManager
        public void reloadPersistedData() throws android.os.RemoteException {
        }

        @Override // android.content.rollback.IRollbackManager
        public void expireRollbackForPackage(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.content.rollback.IRollbackManager
        public int notifyStagedSession(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.rollback.IRollbackManager
        public void blockRollbackManager(long j) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.rollback.IRollbackManager {
        static final int TRANSACTION_blockRollbackManager = 8;
        static final int TRANSACTION_commitRollback = 3;
        static final int TRANSACTION_expireRollbackForPackage = 6;
        static final int TRANSACTION_getAvailableRollbacks = 1;
        static final int TRANSACTION_getRecentlyCommittedRollbacks = 2;
        static final int TRANSACTION_notifyStagedSession = 7;
        static final int TRANSACTION_reloadPersistedData = 5;
        static final int TRANSACTION_snapshotAndRestoreUserData = 4;

        public Stub() {
            attachInterface(this, android.content.rollback.IRollbackManager.DESCRIPTOR);
        }

        public static android.content.rollback.IRollbackManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.content.rollback.IRollbackManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.rollback.IRollbackManager)) {
                return (android.content.rollback.IRollbackManager) queryLocalInterface;
            }
            return new android.content.rollback.IRollbackManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAvailableRollbacks";
                case 2:
                    return "getRecentlyCommittedRollbacks";
                case 3:
                    return "commitRollback";
                case 4:
                    return "snapshotAndRestoreUserData";
                case 5:
                    return "reloadPersistedData";
                case 6:
                    return "expireRollbackForPackage";
                case 7:
                    return "notifyStagedSession";
                case 8:
                    return "blockRollbackManager";
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
                parcel.enforceInterface(android.content.rollback.IRollbackManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.content.rollback.IRollbackManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.content.pm.ParceledListSlice availableRollbacks = getAvailableRollbacks();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(availableRollbacks, 1);
                    return true;
                case 2:
                    android.content.pm.ParceledListSlice recentlyCommittedRollbacks = getRecentlyCommittedRollbacks();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(recentlyCommittedRollbacks, 1);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    java.lang.String readString = parcel.readString();
                    android.content.IntentSender intentSender = (android.content.IntentSender) parcel.readTypedObject(android.content.IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    commitRollback(readInt, parceledListSlice, readString, intentSender);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.lang.String readString2 = parcel.readString();
                    int[] createIntArray = parcel.createIntArray();
                    int readInt2 = parcel.readInt();
                    long readLong = parcel.readLong();
                    java.lang.String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    snapshotAndRestoreUserData(readString2, createIntArray, readInt2, readLong, readString3, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    reloadPersistedData();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    expireRollbackForPackage(readString4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int notifyStagedSession = notifyStagedSession(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(notifyStagedSession);
                    return true;
                case 8:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    blockRollbackManager(readLong2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.rollback.IRollbackManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.rollback.IRollbackManager.DESCRIPTOR;
            }

            @Override // android.content.rollback.IRollbackManager
            public android.content.pm.ParceledListSlice getAvailableRollbacks() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.rollback.IRollbackManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.rollback.IRollbackManager
            public android.content.pm.ParceledListSlice getRecentlyCommittedRollbacks() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.rollback.IRollbackManager.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.rollback.IRollbackManager
            public void commitRollback(int i, android.content.pm.ParceledListSlice parceledListSlice, java.lang.String str, android.content.IntentSender intentSender) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.rollback.IRollbackManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.rollback.IRollbackManager
            public void snapshotAndRestoreUserData(java.lang.String str, int[] iArr, int i, long j, java.lang.String str2, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.rollback.IRollbackManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.rollback.IRollbackManager
            public void reloadPersistedData() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.rollback.IRollbackManager.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.rollback.IRollbackManager
            public void expireRollbackForPackage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.rollback.IRollbackManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.rollback.IRollbackManager
            public int notifyStagedSession(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.rollback.IRollbackManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.rollback.IRollbackManager
            public void blockRollbackManager(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.rollback.IRollbackManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
