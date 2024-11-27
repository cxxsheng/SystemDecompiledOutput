package android.credentials;

/* loaded from: classes.dex */
public interface ICreateCredentialCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.credentials.ICreateCredentialCallback";

    void onError(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void onPendingIntent(android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void onResponse(android.credentials.CreateCredentialResponse createCredentialResponse) throws android.os.RemoteException;

    public static class Default implements android.credentials.ICreateCredentialCallback {
        @Override // android.credentials.ICreateCredentialCallback
        public void onPendingIntent(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // android.credentials.ICreateCredentialCallback
        public void onResponse(android.credentials.CreateCredentialResponse createCredentialResponse) throws android.os.RemoteException {
        }

        @Override // android.credentials.ICreateCredentialCallback
        public void onError(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.credentials.ICreateCredentialCallback {
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onPendingIntent = 1;
        static final int TRANSACTION_onResponse = 2;

        public Stub() {
            attachInterface(this, android.credentials.ICreateCredentialCallback.DESCRIPTOR);
        }

        public static android.credentials.ICreateCredentialCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.credentials.ICreateCredentialCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.credentials.ICreateCredentialCallback)) {
                return (android.credentials.ICreateCredentialCallback) queryLocalInterface;
            }
            return new android.credentials.ICreateCredentialCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onPendingIntent";
                case 2:
                    return "onResponse";
                case 3:
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
                parcel.enforceInterface(android.credentials.ICreateCredentialCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.credentials.ICreateCredentialCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPendingIntent(pendingIntent);
                    return true;
                case 2:
                    android.credentials.CreateCredentialResponse createCredentialResponse = (android.credentials.CreateCredentialResponse) parcel.readTypedObject(android.credentials.CreateCredentialResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResponse(createCredentialResponse);
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onError(readString, readString2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.credentials.ICreateCredentialCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.credentials.ICreateCredentialCallback.DESCRIPTOR;
            }

            @Override // android.credentials.ICreateCredentialCallback
            public void onPendingIntent(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.credentials.ICreateCredentialCallback.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICreateCredentialCallback
            public void onResponse(android.credentials.CreateCredentialResponse createCredentialResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.credentials.ICreateCredentialCallback.DESCRIPTOR);
                    obtain.writeTypedObject(createCredentialResponse, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.credentials.ICreateCredentialCallback
            public void onError(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.credentials.ICreateCredentialCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
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
