package android.service.autofill;

/* loaded from: classes3.dex */
public interface IConvertCredentialCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.autofill.IConvertCredentialCallback";

    void onFailure(java.lang.CharSequence charSequence) throws android.os.RemoteException;

    void onSuccess(android.service.autofill.ConvertCredentialResponse convertCredentialResponse) throws android.os.RemoteException;

    public static class Default implements android.service.autofill.IConvertCredentialCallback {
        @Override // android.service.autofill.IConvertCredentialCallback
        public void onSuccess(android.service.autofill.ConvertCredentialResponse convertCredentialResponse) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.IConvertCredentialCallback
        public void onFailure(java.lang.CharSequence charSequence) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.autofill.IConvertCredentialCallback {
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, android.service.autofill.IConvertCredentialCallback.DESCRIPTOR);
        }

        public static android.service.autofill.IConvertCredentialCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.autofill.IConvertCredentialCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.autofill.IConvertCredentialCallback)) {
                return (android.service.autofill.IConvertCredentialCallback) queryLocalInterface;
            }
            return new android.service.autofill.IConvertCredentialCallback.Stub.Proxy(iBinder);
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
                parcel.enforceInterface(android.service.autofill.IConvertCredentialCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.autofill.IConvertCredentialCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.autofill.ConvertCredentialResponse convertCredentialResponse = (android.service.autofill.ConvertCredentialResponse) parcel.readTypedObject(android.service.autofill.ConvertCredentialResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSuccess(convertCredentialResponse);
                    return true;
                case 2:
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    parcel.enforceNoDataAvail();
                    onFailure(charSequence);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.autofill.IConvertCredentialCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.autofill.IConvertCredentialCallback.DESCRIPTOR;
            }

            @Override // android.service.autofill.IConvertCredentialCallback
            public void onSuccess(android.service.autofill.ConvertCredentialResponse convertCredentialResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IConvertCredentialCallback.DESCRIPTOR);
                    obtain.writeTypedObject(convertCredentialResponse, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IConvertCredentialCallback
            public void onFailure(java.lang.CharSequence charSequence) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IConvertCredentialCallback.DESCRIPTOR);
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
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
