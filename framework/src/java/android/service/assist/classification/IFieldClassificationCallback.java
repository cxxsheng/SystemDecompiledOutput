package android.service.assist.classification;

/* loaded from: classes3.dex */
public interface IFieldClassificationCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.assist.classification.IFieldClassificationCallback";

    void cancel() throws android.os.RemoteException;

    boolean isCompleted() throws android.os.RemoteException;

    void onCancellable(android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException;

    void onFailure() throws android.os.RemoteException;

    void onSuccess(android.service.assist.classification.FieldClassificationResponse fieldClassificationResponse) throws android.os.RemoteException;

    public static class Default implements android.service.assist.classification.IFieldClassificationCallback {
        @Override // android.service.assist.classification.IFieldClassificationCallback
        public void onCancellable(android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException {
        }

        @Override // android.service.assist.classification.IFieldClassificationCallback
        public void onSuccess(android.service.assist.classification.FieldClassificationResponse fieldClassificationResponse) throws android.os.RemoteException {
        }

        @Override // android.service.assist.classification.IFieldClassificationCallback
        public void onFailure() throws android.os.RemoteException {
        }

        @Override // android.service.assist.classification.IFieldClassificationCallback
        public boolean isCompleted() throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.assist.classification.IFieldClassificationCallback
        public void cancel() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.assist.classification.IFieldClassificationCallback {
        static final int TRANSACTION_cancel = 5;
        static final int TRANSACTION_isCompleted = 4;
        static final int TRANSACTION_onCancellable = 1;
        static final int TRANSACTION_onFailure = 3;
        static final int TRANSACTION_onSuccess = 2;

        public Stub() {
            attachInterface(this, android.service.assist.classification.IFieldClassificationCallback.DESCRIPTOR);
        }

        public static android.service.assist.classification.IFieldClassificationCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.assist.classification.IFieldClassificationCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.assist.classification.IFieldClassificationCallback)) {
                return (android.service.assist.classification.IFieldClassificationCallback) queryLocalInterface;
            }
            return new android.service.assist.classification.IFieldClassificationCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCancellable";
                case 2:
                    return "onSuccess";
                case 3:
                    return "onFailure";
                case 4:
                    return "isCompleted";
                case 5:
                    return "cancel";
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
                parcel.enforceInterface(android.service.assist.classification.IFieldClassificationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.assist.classification.IFieldClassificationCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.ICancellationSignal asInterface = android.os.ICancellationSignal.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onCancellable(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.service.assist.classification.FieldClassificationResponse fieldClassificationResponse = (android.service.assist.classification.FieldClassificationResponse) parcel.readTypedObject(android.service.assist.classification.FieldClassificationResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSuccess(fieldClassificationResponse);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    onFailure();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    boolean isCompleted = isCompleted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCompleted);
                    return true;
                case 5:
                    cancel();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.assist.classification.IFieldClassificationCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.assist.classification.IFieldClassificationCallback.DESCRIPTOR;
            }

            @Override // android.service.assist.classification.IFieldClassificationCallback
            public void onCancellable(android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.assist.classification.IFieldClassificationCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iCancellationSignal);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.assist.classification.IFieldClassificationCallback
            public void onSuccess(android.service.assist.classification.FieldClassificationResponse fieldClassificationResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.assist.classification.IFieldClassificationCallback.DESCRIPTOR);
                    obtain.writeTypedObject(fieldClassificationResponse, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.assist.classification.IFieldClassificationCallback
            public void onFailure() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.assist.classification.IFieldClassificationCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.assist.classification.IFieldClassificationCallback
            public boolean isCompleted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.assist.classification.IFieldClassificationCallback.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.assist.classification.IFieldClassificationCallback
            public void cancel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.assist.classification.IFieldClassificationCallback.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
