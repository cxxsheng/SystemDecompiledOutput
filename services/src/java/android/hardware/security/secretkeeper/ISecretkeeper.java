package android.hardware.security.secretkeeper;

/* loaded from: classes.dex */
public interface ISecretkeeper extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$security$secretkeeper$ISecretkeeper".replace('$', '.');
    public static final int ERROR_INTERNAL_ERROR = 2;
    public static final int ERROR_REQUEST_MALFORMED = 3;
    public static final int ERROR_UNKNOWN_KEY_ID = 1;
    public static final java.lang.String HASH = "347439bd6088bd24a72e789a616a1586863e43b8";
    public static final int VERSION = 1;

    void deleteAll() throws android.os.RemoteException;

    void deleteIds(android.hardware.security.secretkeeper.SecretId[] secretIdArr) throws android.os.RemoteException;

    android.hardware.security.authgraph.IAuthGraphKeyExchange getAuthGraphKe() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    byte[] processSecretManagementRequest(byte[] bArr) throws android.os.RemoteException;

    public static class Default implements android.hardware.security.secretkeeper.ISecretkeeper {
        @Override // android.hardware.security.secretkeeper.ISecretkeeper
        public android.hardware.security.authgraph.IAuthGraphKeyExchange getAuthGraphKe() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.secretkeeper.ISecretkeeper
        public byte[] processSecretManagementRequest(byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.security.secretkeeper.ISecretkeeper
        public void deleteIds(android.hardware.security.secretkeeper.SecretId[] secretIdArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.security.secretkeeper.ISecretkeeper
        public void deleteAll() throws android.os.RemoteException {
        }

        @Override // android.hardware.security.secretkeeper.ISecretkeeper
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.security.secretkeeper.ISecretkeeper
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.security.secretkeeper.ISecretkeeper {
        static final int TRANSACTION_deleteAll = 4;
        static final int TRANSACTION_deleteIds = 3;
        static final int TRANSACTION_getAuthGraphKe = 1;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_processSecretManagementRequest = 2;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.security.secretkeeper.ISecretkeeper.DESCRIPTOR);
        }

        public static android.hardware.security.secretkeeper.ISecretkeeper asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.security.secretkeeper.ISecretkeeper.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.security.secretkeeper.ISecretkeeper)) {
                return (android.hardware.security.secretkeeper.ISecretkeeper) queryLocalInterface;
            }
            return new android.hardware.security.secretkeeper.ISecretkeeper.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getAuthGraphKe";
                case 2:
                    return "processSecretManagementRequest";
                case 3:
                    return "deleteIds";
                case 4:
                    return "deleteAll";
                case TRANSACTION_getInterfaceHash /* 16777214 */:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.security.secretkeeper.ISecretkeeper.DESCRIPTOR;
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
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.security.authgraph.IAuthGraphKeyExchange authGraphKe = getAuthGraphKe();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(authGraphKe);
                    return true;
                case 2:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] processSecretManagementRequest = processSecretManagementRequest(createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(processSecretManagementRequest);
                    return true;
                case 3:
                    android.hardware.security.secretkeeper.SecretId[] secretIdArr = (android.hardware.security.secretkeeper.SecretId[]) parcel.createTypedArray(android.hardware.security.secretkeeper.SecretId.CREATOR);
                    parcel.enforceNoDataAvail();
                    deleteIds(secretIdArr);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    deleteAll();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.security.secretkeeper.ISecretkeeper {
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
                return android.hardware.security.secretkeeper.ISecretkeeper.DESCRIPTOR;
            }

            @Override // android.hardware.security.secretkeeper.ISecretkeeper
            public android.hardware.security.authgraph.IAuthGraphKeyExchange getAuthGraphKe() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.security.secretkeeper.ISecretkeeper.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getAuthGraphKe is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.security.authgraph.IAuthGraphKeyExchange.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.secretkeeper.ISecretkeeper
            public byte[] processSecretManagementRequest(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.security.secretkeeper.ISecretkeeper.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method processSecretManagementRequest is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.secretkeeper.ISecretkeeper
            public void deleteIds(android.hardware.security.secretkeeper.SecretId[] secretIdArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.security.secretkeeper.ISecretkeeper.DESCRIPTOR);
                    obtain.writeTypedArray(secretIdArr, 0);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method deleteIds is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.secretkeeper.ISecretkeeper
            public void deleteAll() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.security.secretkeeper.ISecretkeeper.DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method deleteAll is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.security.secretkeeper.ISecretkeeper
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.security.secretkeeper.ISecretkeeper.DESCRIPTOR);
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

            @Override // android.hardware.security.secretkeeper.ISecretkeeper
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.security.secretkeeper.ISecretkeeper.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.security.secretkeeper.ISecretkeeper.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }

        public int getMaxTransactionId() {
            return TRANSACTION_getInterfaceHash;
        }
    }
}
