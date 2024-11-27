package android.credentials;

/* loaded from: classes.dex */
public interface IGetCredentialCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.credentials.IGetCredentialCallback";

    void onError(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void onPendingIntent(android.app.PendingIntent pendingIntent) throws android.os.RemoteException;

    void onResponse(android.credentials.GetCredentialResponse getCredentialResponse) throws android.os.RemoteException;

    public static class Default implements android.credentials.IGetCredentialCallback {
        @Override // android.credentials.IGetCredentialCallback
        public void onPendingIntent(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onResponse(android.credentials.GetCredentialResponse getCredentialResponse) throws android.os.RemoteException {
        }

        @Override // android.credentials.IGetCredentialCallback
        public void onError(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.credentials.IGetCredentialCallback {
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onPendingIntent = 1;
        static final int TRANSACTION_onResponse = 2;

        public Stub() {
            attachInterface(this, android.credentials.IGetCredentialCallback.DESCRIPTOR);
        }

        public static android.credentials.IGetCredentialCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.credentials.IGetCredentialCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.credentials.IGetCredentialCallback)) {
                return (android.credentials.IGetCredentialCallback) queryLocalInterface;
            }
            return new android.credentials.IGetCredentialCallback.Stub.Proxy(iBinder);
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
                parcel.enforceInterface(android.credentials.IGetCredentialCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.credentials.IGetCredentialCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPendingIntent(pendingIntent);
                    return true;
                case 2:
                    android.credentials.GetCredentialResponse getCredentialResponse = (android.credentials.GetCredentialResponse) parcel.readTypedObject(android.credentials.GetCredentialResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResponse(getCredentialResponse);
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

        private static class Proxy implements android.credentials.IGetCredentialCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.credentials.IGetCredentialCallback.DESCRIPTOR;
            }

            @Override // android.credentials.IGetCredentialCallback
            public void onPendingIntent(android.app.PendingIntent pendingIntent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.credentials.IGetCredentialCallback.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.credentials.IGetCredentialCallback
            public void onResponse(android.credentials.GetCredentialResponse getCredentialResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.credentials.IGetCredentialCallback.DESCRIPTOR);
                    obtain.writeTypedObject(getCredentialResponse, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.credentials.IGetCredentialCallback
            public void onError(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.credentials.IGetCredentialCallback.DESCRIPTOR);
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
