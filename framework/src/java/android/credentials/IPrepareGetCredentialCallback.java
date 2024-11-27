package android.credentials;

/* loaded from: classes.dex */
public interface IPrepareGetCredentialCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.credentials.IPrepareGetCredentialCallback";

    void onError(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void onResponse(android.credentials.PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal) throws android.os.RemoteException;

    public static class Default implements android.credentials.IPrepareGetCredentialCallback {
        @Override // android.credentials.IPrepareGetCredentialCallback
        public void onResponse(android.credentials.PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal) throws android.os.RemoteException {
        }

        @Override // android.credentials.IPrepareGetCredentialCallback
        public void onError(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.credentials.IPrepareGetCredentialCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResponse = 1;

        public Stub() {
            attachInterface(this, android.credentials.IPrepareGetCredentialCallback.DESCRIPTOR);
        }

        public static android.credentials.IPrepareGetCredentialCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.credentials.IPrepareGetCredentialCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.credentials.IPrepareGetCredentialCallback)) {
                return (android.credentials.IPrepareGetCredentialCallback) queryLocalInterface;
            }
            return new android.credentials.IPrepareGetCredentialCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onResponse";
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
                parcel.enforceInterface(android.credentials.IPrepareGetCredentialCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.credentials.IPrepareGetCredentialCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.credentials.PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal = (android.credentials.PrepareGetCredentialResponseInternal) parcel.readTypedObject(android.credentials.PrepareGetCredentialResponseInternal.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResponse(prepareGetCredentialResponseInternal);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onError(readString, readString2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.credentials.IPrepareGetCredentialCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.credentials.IPrepareGetCredentialCallback.DESCRIPTOR;
            }

            @Override // android.credentials.IPrepareGetCredentialCallback
            public void onResponse(android.credentials.PrepareGetCredentialResponseInternal prepareGetCredentialResponseInternal) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.credentials.IPrepareGetCredentialCallback.DESCRIPTOR);
                    obtain.writeTypedObject(prepareGetCredentialResponseInternal, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.credentials.IPrepareGetCredentialCallback
            public void onError(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.credentials.IPrepareGetCredentialCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
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