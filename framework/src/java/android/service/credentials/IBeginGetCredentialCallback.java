package android.service.credentials;

/* loaded from: classes3.dex */
public interface IBeginGetCredentialCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.credentials.IBeginGetCredentialCallback";

    void onCancellable(android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException;

    void onFailure(java.lang.String str, java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void onSuccess(android.service.credentials.BeginGetCredentialResponse beginGetCredentialResponse) throws android.os.RemoteException;

    public static class Default implements android.service.credentials.IBeginGetCredentialCallback {
        @Override // android.service.credentials.IBeginGetCredentialCallback
        public void onSuccess(android.service.credentials.BeginGetCredentialResponse beginGetCredentialResponse) throws android.os.RemoteException {
        }

        @Override // android.service.credentials.IBeginGetCredentialCallback
        public void onFailure(java.lang.String str, java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.service.credentials.IBeginGetCredentialCallback
        public void onCancellable(android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.credentials.IBeginGetCredentialCallback {
        static final int TRANSACTION_onCancellable = 3;
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, android.service.credentials.IBeginGetCredentialCallback.DESCRIPTOR);
        }

        public static android.service.credentials.IBeginGetCredentialCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.credentials.IBeginGetCredentialCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.credentials.IBeginGetCredentialCallback)) {
                return (android.service.credentials.IBeginGetCredentialCallback) queryLocalInterface;
            }
            return new android.service.credentials.IBeginGetCredentialCallback.Stub.Proxy(iBinder);
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
                    return "onFailure";
                case 3:
                    return "onCancellable";
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
                parcel.enforceInterface(android.service.credentials.IBeginGetCredentialCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.credentials.IBeginGetCredentialCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.credentials.BeginGetCredentialResponse beginGetCredentialResponse = (android.service.credentials.BeginGetCredentialResponse) parcel.readTypedObject(android.service.credentials.BeginGetCredentialResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSuccess(beginGetCredentialResponse);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    onFailure(readString, charSequence);
                    return true;
                case 3:
                    android.os.ICancellationSignal asInterface = android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onCancellable(asInterface);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.credentials.IBeginGetCredentialCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.credentials.IBeginGetCredentialCallback.DESCRIPTOR;
            }

            @Override // android.service.credentials.IBeginGetCredentialCallback
            public void onSuccess(android.service.credentials.BeginGetCredentialResponse beginGetCredentialResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.credentials.IBeginGetCredentialCallback.DESCRIPTOR);
                    obtain.writeTypedObject(beginGetCredentialResponse, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.credentials.IBeginGetCredentialCallback
            public void onFailure(java.lang.String str, java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.credentials.IBeginGetCredentialCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.credentials.IBeginGetCredentialCallback
            public void onCancellable(android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.credentials.IBeginGetCredentialCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iCancellationSignal);
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
