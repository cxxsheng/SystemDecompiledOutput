package android.accounts;

/* loaded from: classes.dex */
public interface IAccountAuthenticatorResponse extends android.os.IInterface {
    void onError(int i, java.lang.String str) throws android.os.RemoteException;

    void onRequestContinued() throws android.os.RemoteException;

    void onResult(android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.accounts.IAccountAuthenticatorResponse {
        @Override // android.accounts.IAccountAuthenticatorResponse
        public void onResult(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticatorResponse
        public void onRequestContinued() throws android.os.RemoteException {
        }

        @Override // android.accounts.IAccountAuthenticatorResponse
        public void onError(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.accounts.IAccountAuthenticatorResponse {
        public static final java.lang.String DESCRIPTOR = "android.accounts.IAccountAuthenticatorResponse";
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onRequestContinued = 2;
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.accounts.IAccountAuthenticatorResponse asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.accounts.IAccountAuthenticatorResponse)) {
                return (android.accounts.IAccountAuthenticatorResponse) queryLocalInterface;
            }
            return new android.accounts.IAccountAuthenticatorResponse.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onResult";
                case 2:
                    return "onRequestContinued";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResult(bundle);
                    return true;
                case 2:
                    onRequestContinued();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onError(readInt, readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.accounts.IAccountAuthenticatorResponse {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.accounts.IAccountAuthenticatorResponse.Stub.DESCRIPTOR;
            }

            @Override // android.accounts.IAccountAuthenticatorResponse
            public void onResult(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticatorResponse.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticatorResponse
            public void onRequestContinued() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticatorResponse.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.accounts.IAccountAuthenticatorResponse
            public void onError(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.accounts.IAccountAuthenticatorResponse.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
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