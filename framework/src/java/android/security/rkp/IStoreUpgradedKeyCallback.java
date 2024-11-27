package android.security.rkp;

/* loaded from: classes3.dex */
public interface IStoreUpgradedKeyCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.security.rkp.IStoreUpgradedKeyCallback";

    void onError(java.lang.String str) throws android.os.RemoteException;

    void onSuccess() throws android.os.RemoteException;

    public static class Default implements android.security.rkp.IStoreUpgradedKeyCallback {
        @Override // android.security.rkp.IStoreUpgradedKeyCallback
        public void onSuccess() throws android.os.RemoteException {
        }

        @Override // android.security.rkp.IStoreUpgradedKeyCallback
        public void onError(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.security.rkp.IStoreUpgradedKeyCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, android.security.rkp.IStoreUpgradedKeyCallback.DESCRIPTOR);
        }

        public static android.security.rkp.IStoreUpgradedKeyCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.security.rkp.IStoreUpgradedKeyCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.security.rkp.IStoreUpgradedKeyCallback)) {
                return (android.security.rkp.IStoreUpgradedKeyCallback) queryLocalInterface;
            }
            return new android.security.rkp.IStoreUpgradedKeyCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSuccess";
                case 2:
                    return "onError";
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
                parcel.enforceInterface(android.security.rkp.IStoreUpgradedKeyCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.security.rkp.IStoreUpgradedKeyCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onSuccess();
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onError(readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.security.rkp.IStoreUpgradedKeyCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.security.rkp.IStoreUpgradedKeyCallback.DESCRIPTOR;
            }

            @Override // android.security.rkp.IStoreUpgradedKeyCallback
            public void onSuccess() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.security.rkp.IStoreUpgradedKeyCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.security.rkp.IStoreUpgradedKeyCallback
            public void onError(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.security.rkp.IStoreUpgradedKeyCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
