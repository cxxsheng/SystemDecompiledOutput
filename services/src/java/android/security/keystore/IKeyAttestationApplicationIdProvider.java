package android.security.keystore;

/* loaded from: classes.dex */
public interface IKeyAttestationApplicationIdProvider extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.keystore.IKeyAttestationApplicationIdProvider";
    public static final int ERROR_GET_ATTESTATION_APPLICATION_ID_FAILED = 1;

    android.security.keystore.KeyAttestationApplicationId getKeyAttestationApplicationId(int i) throws android.os.RemoteException;

    public static class Default implements android.security.keystore.IKeyAttestationApplicationIdProvider {
        @Override // android.security.keystore.IKeyAttestationApplicationIdProvider
        public android.security.keystore.KeyAttestationApplicationId getKeyAttestationApplicationId(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.keystore.IKeyAttestationApplicationIdProvider {
        static final int TRANSACTION_getKeyAttestationApplicationId = 1;

        public Stub() {
            attachInterface(this, android.security.keystore.IKeyAttestationApplicationIdProvider.DESCRIPTOR);
        }

        public static android.security.keystore.IKeyAttestationApplicationIdProvider asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.keystore.IKeyAttestationApplicationIdProvider.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.keystore.IKeyAttestationApplicationIdProvider)) {
                return (android.security.keystore.IKeyAttestationApplicationIdProvider) queryLocalInterface;
            }
            return new android.security.keystore.IKeyAttestationApplicationIdProvider.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.security.keystore.IKeyAttestationApplicationIdProvider.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.keystore.IKeyAttestationApplicationIdProvider.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.security.keystore.KeyAttestationApplicationId keyAttestationApplicationId = getKeyAttestationApplicationId(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(keyAttestationApplicationId, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.keystore.IKeyAttestationApplicationIdProvider {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.keystore.IKeyAttestationApplicationIdProvider.DESCRIPTOR;
            }

            @Override // android.security.keystore.IKeyAttestationApplicationIdProvider
            public android.security.keystore.KeyAttestationApplicationId getKeyAttestationApplicationId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.security.keystore.IKeyAttestationApplicationIdProvider.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.security.keystore.KeyAttestationApplicationId) obtain2.readTypedObject(android.security.keystore.KeyAttestationApplicationId.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
