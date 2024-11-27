package android.system.keystore2;

/* loaded from: classes3.dex */
public interface IKeystoreService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$system$keystore2$IKeystoreService".replace('$', '.');
    public static final java.lang.String HASH = "4f1c704008e5687ed0d6f1590464aed39fc7f64e";
    public static final int VERSION = 3;

    void deleteKey(android.system.keystore2.KeyDescriptor keyDescriptor) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.system.keystore2.KeyEntryResponse getKeyEntry(android.system.keystore2.KeyDescriptor keyDescriptor) throws android.os.RemoteException;

    int getNumberOfEntries(int i, long j) throws android.os.RemoteException;

    android.system.keystore2.IKeystoreSecurityLevel getSecurityLevel(int i) throws android.os.RemoteException;

    android.system.keystore2.KeyDescriptor grant(android.system.keystore2.KeyDescriptor keyDescriptor, int i, int i2) throws android.os.RemoteException;

    @java.lang.Deprecated
    android.system.keystore2.KeyDescriptor[] listEntries(int i, long j) throws android.os.RemoteException;

    android.system.keystore2.KeyDescriptor[] listEntriesBatched(int i, long j, java.lang.String str) throws android.os.RemoteException;

    void ungrant(android.system.keystore2.KeyDescriptor keyDescriptor, int i) throws android.os.RemoteException;

    void updateSubcomponent(android.system.keystore2.KeyDescriptor keyDescriptor, byte[] bArr, byte[] bArr2) throws android.os.RemoteException;

    public static class Default implements android.system.keystore2.IKeystoreService {
        @Override // android.system.keystore2.IKeystoreService
        public android.system.keystore2.IKeystoreSecurityLevel getSecurityLevel(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public android.system.keystore2.KeyEntryResponse getKeyEntry(android.system.keystore2.KeyDescriptor keyDescriptor) throws android.os.RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public void updateSubcomponent(android.system.keystore2.KeyDescriptor keyDescriptor, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
        }

        @Override // android.system.keystore2.IKeystoreService
        public android.system.keystore2.KeyDescriptor[] listEntries(int i, long j) throws android.os.RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public void deleteKey(android.system.keystore2.KeyDescriptor keyDescriptor) throws android.os.RemoteException {
        }

        @Override // android.system.keystore2.IKeystoreService
        public android.system.keystore2.KeyDescriptor grant(android.system.keystore2.KeyDescriptor keyDescriptor, int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public void ungrant(android.system.keystore2.KeyDescriptor keyDescriptor, int i) throws android.os.RemoteException {
        }

        @Override // android.system.keystore2.IKeystoreService
        public int getNumberOfEntries(int i, long j) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.system.keystore2.IKeystoreService
        public android.system.keystore2.KeyDescriptor[] listEntriesBatched(int i, long j, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.system.keystore2.IKeystoreService
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.system.keystore2.IKeystoreService
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.system.keystore2.IKeystoreService {
        static final int TRANSACTION_deleteKey = 5;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getKeyEntry = 2;
        static final int TRANSACTION_getNumberOfEntries = 8;
        static final int TRANSACTION_getSecurityLevel = 1;
        static final int TRANSACTION_grant = 6;
        static final int TRANSACTION_listEntries = 4;
        static final int TRANSACTION_listEntriesBatched = 9;
        static final int TRANSACTION_ungrant = 7;
        static final int TRANSACTION_updateSubcomponent = 3;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.system.keystore2.IKeystoreService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.system.keystore2.IKeystoreService)) {
                return (android.system.keystore2.IKeystoreService) queryLocalInterface;
            }
            return new android.system.keystore2.IKeystoreService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getSecurityLevel";
                case 2:
                    return "getKeyEntry";
                case 3:
                    return "updateSubcomponent";
                case 4:
                    return "listEntries";
                case 5:
                    return "deleteKey";
                case 6:
                    return "grant";
                case 7:
                    return "ungrant";
                case 8:
                    return "getNumberOfEntries";
                case 9:
                    return "listEntriesBatched";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
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
            java.lang.String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.system.keystore2.IKeystoreSecurityLevel securityLevel = getSecurityLevel(readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(securityLevel);
                    return true;
                case 2:
                    android.system.keystore2.KeyDescriptor keyDescriptor = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.system.keystore2.KeyEntryResponse keyEntry = getKeyEntry(keyDescriptor);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyEntry, 1);
                    return true;
                case 3:
                    android.system.keystore2.KeyDescriptor keyDescriptor2 = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    updateSubcomponent(keyDescriptor2, createByteArray, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    android.system.keystore2.KeyDescriptor[] listEntries = listEntries(readInt2, readLong);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listEntries, 1);
                    return true;
                case 5:
                    android.system.keystore2.KeyDescriptor keyDescriptor3 = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteKey(keyDescriptor3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.system.keystore2.KeyDescriptor keyDescriptor4 = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.system.keystore2.KeyDescriptor grant = grant(keyDescriptor4, readInt3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(grant, 1);
                    return true;
                case 7:
                    android.system.keystore2.KeyDescriptor keyDescriptor5 = (android.system.keystore2.KeyDescriptor) parcel.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ungrant(keyDescriptor5, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt6 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    int numberOfEntries = getNumberOfEntries(readInt6, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeInt(numberOfEntries);
                    return true;
                case 9:
                    int readInt7 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.system.keystore2.KeyDescriptor[] listEntriesBatched = listEntriesBatched(readInt7, readLong3, readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listEntriesBatched, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.system.keystore2.IKeystoreService {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.system.keystore2.IKeystoreService
            public android.system.keystore2.IKeystoreSecurityLevel getSecurityLevel(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getSecurityLevel is unimplemented.");
                    }
                    obtain2.readException();
                    return android.system.keystore2.IKeystoreSecurityLevel.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public android.system.keystore2.KeyEntryResponse getKeyEntry(android.system.keystore2.KeyDescriptor keyDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getKeyEntry is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.system.keystore2.KeyEntryResponse) obtain2.readTypedObject(android.system.keystore2.KeyEntryResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public void updateSubcomponent(android.system.keystore2.KeyDescriptor keyDescriptor, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method updateSubcomponent is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public android.system.keystore2.KeyDescriptor[] listEntries(int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method listEntries is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.system.keystore2.KeyDescriptor[]) obtain2.createTypedArray(android.system.keystore2.KeyDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public void deleteKey(android.system.keystore2.KeyDescriptor keyDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method deleteKey is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public android.system.keystore2.KeyDescriptor grant(android.system.keystore2.KeyDescriptor keyDescriptor, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method grant is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.system.keystore2.KeyDescriptor) obtain2.readTypedObject(android.system.keystore2.KeyDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public void ungrant(android.system.keystore2.KeyDescriptor keyDescriptor, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(keyDescriptor, 0);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method ungrant is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public int getNumberOfEntries(int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getNumberOfEntries is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public android.system.keystore2.KeyDescriptor[] listEntriesBatched(int i, long j, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method listEntriesBatched is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.system.keystore2.KeyDescriptor[]) obtain2.createTypedArray(android.system.keystore2.KeyDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.system.keystore2.IKeystoreService
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.system.keystore2.IKeystoreService
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (java.lang.Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }
}
