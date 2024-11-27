package android.security.maintenance;

/* loaded from: classes3.dex */
public interface IKeystoreMaintenance extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.maintenance.IKeystoreMaintenance";

    void clearNamespace(int i, long j) throws android.os.RemoteException;

    void deleteAllKeys() throws android.os.RemoteException;

    void earlyBootEnded() throws android.os.RemoteException;

    long[] getAppUidsAffectedBySid(int i, long j) throws android.os.RemoteException;

    void initUserSuperKeys(int i, byte[] bArr, boolean z) throws android.os.RemoteException;

    void migrateKeyNamespace(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2) throws android.os.RemoteException;

    void onDeviceOffBody() throws android.os.RemoteException;

    void onUserAdded(int i) throws android.os.RemoteException;

    void onUserLskfRemoved(int i) throws android.os.RemoteException;

    void onUserPasswordChanged(int i, byte[] bArr) throws android.os.RemoteException;

    void onUserRemoved(int i) throws android.os.RemoteException;

    public static class Default implements android.security.maintenance.IKeystoreMaintenance {
        @Override // android.security.maintenance.IKeystoreMaintenance
        public void onUserAdded(int i) throws android.os.RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void initUserSuperKeys(int i, byte[] bArr, boolean z) throws android.os.RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void onUserRemoved(int i) throws android.os.RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void onUserLskfRemoved(int i) throws android.os.RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void onUserPasswordChanged(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void clearNamespace(int i, long j) throws android.os.RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void earlyBootEnded() throws android.os.RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void onDeviceOffBody() throws android.os.RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void migrateKeyNamespace(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2) throws android.os.RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public void deleteAllKeys() throws android.os.RemoteException {
        }

        @Override // android.security.maintenance.IKeystoreMaintenance
        public long[] getAppUidsAffectedBySid(int i, long j) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.maintenance.IKeystoreMaintenance {
        static final int TRANSACTION_clearNamespace = 6;
        static final int TRANSACTION_deleteAllKeys = 10;
        static final int TRANSACTION_earlyBootEnded = 7;
        static final int TRANSACTION_getAppUidsAffectedBySid = 11;
        static final int TRANSACTION_initUserSuperKeys = 2;
        static final int TRANSACTION_migrateKeyNamespace = 9;
        static final int TRANSACTION_onDeviceOffBody = 8;
        static final int TRANSACTION_onUserAdded = 1;
        static final int TRANSACTION_onUserLskfRemoved = 4;
        static final int TRANSACTION_onUserPasswordChanged = 5;
        static final int TRANSACTION_onUserRemoved = 3;

        public Stub() {
            attachInterface(this, android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
        }

        public static android.security.maintenance.IKeystoreMaintenance asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.maintenance.IKeystoreMaintenance)) {
                return (android.security.maintenance.IKeystoreMaintenance) queryLocalInterface;
            }
            return new android.security.maintenance.IKeystoreMaintenance.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUserAdded";
                case 2:
                    return "initUserSuperKeys";
                case 3:
                    return "onUserRemoved";
                case 4:
                    return "onUserLskfRemoved";
                case 5:
                    return "onUserPasswordChanged";
                case 6:
                    return "clearNamespace";
                case 7:
                    return "earlyBootEnded";
                case 8:
                    return "onDeviceOffBody";
                case 9:
                    return "migrateKeyNamespace";
                case 10:
                    return "deleteAllKeys";
                case 11:
                    return "getAppUidsAffectedBySid";
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
                parcel.enforceInterface(android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserAdded(readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    initUserSuperKeys(readInt2, createByteArray, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserRemoved(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserLskfRemoved(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onUserPasswordChanged(readInt5, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    clearNamespace(readInt6, readLong);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    earlyBootEnded();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    onDeviceOffBody();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.system.keystore2.KeyDescriptor keyDescriptor = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    android.system.keystore2.KeyDescriptor keyDescriptor2 = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    migrateKeyNamespace(keyDescriptor, keyDescriptor2);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    deleteAllKeys();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt7 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    long[] appUidsAffectedBySid = getAppUidsAffectedBySid(readInt7, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(appUidsAffectedBySid);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.maintenance.IKeystoreMaintenance {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR;
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void onUserAdded(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void initUserSuperKeys(int i, byte[] bArr, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void onUserRemoved(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void onUserLskfRemoved(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void onUserPasswordChanged(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(5, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void clearNamespace(int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(6, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void earlyBootEnded() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void onDeviceOffBody() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void migrateKeyNamespace(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    obtain.writeTypedObject(keyDescriptor2, 0);
                    this.mRemote.transact(9, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public void deleteAllKeys() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 32);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.security.maintenance.IKeystoreMaintenance
            public long[] getAppUidsAffectedBySid(int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                obtain.markSensitive();
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.maintenance.IKeystoreMaintenance.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(11, obtain, obtain2, 32);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
