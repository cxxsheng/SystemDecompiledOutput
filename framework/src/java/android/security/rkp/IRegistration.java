package android.security.rkp;

/* loaded from: classes3.dex */
public interface IRegistration extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.rkp.IRegistration";

    void cancelGetKey(android.security.rkp.IGetKeyCallback iGetKeyCallback) throws android.os.RemoteException;

    void getKey(int i, android.security.rkp.IGetKeyCallback iGetKeyCallback) throws android.os.RemoteException;

    void storeUpgradedKeyAsync(byte[] bArr, byte[] bArr2, android.security.rkp.IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback) throws android.os.RemoteException;

    public static class Default implements android.security.rkp.IRegistration {
        @Override // android.security.rkp.IRegistration
        public void getKey(int i, android.security.rkp.IGetKeyCallback iGetKeyCallback) throws android.os.RemoteException {
        }

        @Override // android.security.rkp.IRegistration
        public void cancelGetKey(android.security.rkp.IGetKeyCallback iGetKeyCallback) throws android.os.RemoteException {
        }

        @Override // android.security.rkp.IRegistration
        public void storeUpgradedKeyAsync(byte[] bArr, byte[] bArr2, android.security.rkp.IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.rkp.IRegistration {
        static final int TRANSACTION_cancelGetKey = 2;
        static final int TRANSACTION_getKey = 1;
        static final int TRANSACTION_storeUpgradedKeyAsync = 3;

        public Stub() {
            attachInterface(this, android.security.rkp.IRegistration.DESCRIPTOR);
        }

        public static android.security.rkp.IRegistration asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.rkp.IRegistration.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.rkp.IRegistration)) {
                return (android.security.rkp.IRegistration) queryLocalInterface;
            }
            return new android.security.rkp.IRegistration.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getKey";
                case 2:
                    return "cancelGetKey";
                case 3:
                    return "storeUpgradedKeyAsync";
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
                parcel.enforceInterface(android.security.rkp.IRegistration.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.rkp.IRegistration.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.security.rkp.IGetKeyCallback asInterface = android.security.rkp.IGetKeyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getKey(readInt, asInterface);
                    return true;
                case 2:
                    android.security.rkp.IGetKeyCallback asInterface2 = android.security.rkp.IGetKeyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelGetKey(asInterface2);
                    return true;
                case 3:
                    byte[] createByteArray = parcel.createByteArray();
                    byte[] createByteArray2 = parcel.createByteArray();
                    android.security.rkp.IStoreUpgradedKeyCallback asInterface3 = android.security.rkp.IStoreUpgradedKeyCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    storeUpgradedKeyAsync(createByteArray, createByteArray2, asInterface3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.rkp.IRegistration {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.rkp.IRegistration.DESCRIPTOR;
            }

            @Override // android.security.rkp.IRegistration
            public void getKey(int i, android.security.rkp.IGetKeyCallback iGetKeyCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.security.rkp.IRegistration.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iGetKeyCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.security.rkp.IRegistration
            public void cancelGetKey(android.security.rkp.IGetKeyCallback iGetKeyCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.security.rkp.IRegistration.DESCRIPTOR);
                    obtain.writeStrongInterface(iGetKeyCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.security.rkp.IRegistration
            public void storeUpgradedKeyAsync(byte[] bArr, byte[] bArr2, android.security.rkp.IStoreUpgradedKeyCallback iStoreUpgradedKeyCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.security.rkp.IRegistration.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeByteArray(bArr2);
                    obtain.writeStrongInterface(iStoreUpgradedKeyCallback);
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
