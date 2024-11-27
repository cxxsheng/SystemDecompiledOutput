package android.security.identity;

/* loaded from: classes3.dex */
public interface ICredentialStoreFactory extends android.os.IInterface {
    public static final int CREDENTIAL_STORE_TYPE_DEFAULT = 0;
    public static final int CREDENTIAL_STORE_TYPE_DIRECT_ACCESS = 1;
    public static final java.lang.String DESCRIPTOR = "android.security.identity.ICredentialStoreFactory";

    android.security.identity.ICredentialStore getCredentialStore(int i) throws android.os.RemoteException;

    public static class Default implements android.security.identity.ICredentialStoreFactory {
        @Override // android.security.identity.ICredentialStoreFactory
        public android.security.identity.ICredentialStore getCredentialStore(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.identity.ICredentialStoreFactory {
        static final int TRANSACTION_getCredentialStore = 1;

        public Stub() {
            attachInterface(this, android.security.identity.ICredentialStoreFactory.DESCRIPTOR);
        }

        public static android.security.identity.ICredentialStoreFactory asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.identity.ICredentialStoreFactory.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.identity.ICredentialStoreFactory)) {
                return (android.security.identity.ICredentialStoreFactory) queryLocalInterface;
            }
            return new android.security.identity.ICredentialStoreFactory.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getCredentialStore";
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
                parcel.enforceInterface(android.security.identity.ICredentialStoreFactory.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.identity.ICredentialStoreFactory.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.security.identity.ICredentialStore credentialStore = getCredentialStore(readInt);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(credentialStore);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.identity.ICredentialStoreFactory {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.identity.ICredentialStoreFactory.DESCRIPTOR;
            }

            @Override // android.security.identity.ICredentialStoreFactory
            public android.security.identity.ICredentialStore getCredentialStore(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.identity.ICredentialStoreFactory.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.security.identity.ICredentialStore.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
