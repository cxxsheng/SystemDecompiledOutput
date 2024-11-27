package android.service.textclassifier;

/* loaded from: classes3.dex */
public interface ITextClassifierCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.textclassifier.ITextClassifierCallback";

    void onFailure() throws android.os.RemoteException;

    void onSuccess(android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.service.textclassifier.ITextClassifierCallback {
        @Override // android.service.textclassifier.ITextClassifierCallback
        public void onSuccess(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierCallback
        public void onFailure() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.textclassifier.ITextClassifierCallback {
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, android.service.textclassifier.ITextClassifierCallback.DESCRIPTOR);
        }

        public static android.service.textclassifier.ITextClassifierCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.textclassifier.ITextClassifierCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.textclassifier.ITextClassifierCallback)) {
                return (android.service.textclassifier.ITextClassifierCallback) queryLocalInterface;
            }
            return new android.service.textclassifier.ITextClassifierCallback.Stub.Proxy(iBinder);
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
                parcel.enforceInterface(android.service.textclassifier.ITextClassifierCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.textclassifier.ITextClassifierCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSuccess(bundle);
                    return true;
                case 2:
                    onFailure();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.textclassifier.ITextClassifierCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.textclassifier.ITextClassifierCallback.DESCRIPTOR;
            }

            @Override // android.service.textclassifier.ITextClassifierCallback
            public void onSuccess(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.textclassifier.ITextClassifierCallback.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierCallback
            public void onFailure() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.textclassifier.ITextClassifierCallback.DESCRIPTOR);
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
