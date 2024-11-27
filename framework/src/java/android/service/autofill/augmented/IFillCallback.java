package android.service.autofill.augmented;

/* loaded from: classes3.dex */
public interface IFillCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.autofill.augmented.IFillCallback";

    void cancel() throws android.os.RemoteException;

    boolean isCompleted() throws android.os.RemoteException;

    void onCancellable(android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException;

    void onSuccess(java.util.List<android.service.autofill.Dataset> list, android.os.Bundle bundle, boolean z) throws android.os.RemoteException;

    public static class Default implements android.service.autofill.augmented.IFillCallback {
        @Override // android.service.autofill.augmented.IFillCallback
        public void onCancellable(android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.augmented.IFillCallback
        public void onSuccess(java.util.List<android.service.autofill.Dataset> list, android.os.Bundle bundle, boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.autofill.augmented.IFillCallback
        public boolean isCompleted() throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.autofill.augmented.IFillCallback
        public void cancel() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.autofill.augmented.IFillCallback {
        static final int TRANSACTION_cancel = 4;
        static final int TRANSACTION_isCompleted = 3;
        static final int TRANSACTION_onCancellable = 1;
        static final int TRANSACTION_onSuccess = 2;

        public Stub() {
            attachInterface(this, android.service.autofill.augmented.IFillCallback.DESCRIPTOR);
        }

        public static android.service.autofill.augmented.IFillCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.autofill.augmented.IFillCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.autofill.augmented.IFillCallback)) {
                return (android.service.autofill.augmented.IFillCallback) queryLocalInterface;
            }
            return new android.service.autofill.augmented.IFillCallback.Stub.Proxy(iBinder);
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
                    return "isCompleted";
                case 4:
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
                parcel.enforceInterface(android.service.autofill.augmented.IFillCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.autofill.augmented.IFillCallback.DESCRIPTOR);
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
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.service.autofill.Dataset.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onSuccess(createTypedArrayList, bundle, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean isCompleted = isCompleted();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCompleted);
                    return true;
                case 4:
                    cancel();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.autofill.augmented.IFillCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.autofill.augmented.IFillCallback.DESCRIPTOR;
            }

            @Override // android.service.autofill.augmented.IFillCallback
            public void onCancellable(android.os.ICancellationSignal iCancellationSignal) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.autofill.augmented.IFillCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iCancellationSignal);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IFillCallback
            public void onSuccess(java.util.List<android.service.autofill.Dataset> list, android.os.Bundle bundle, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.autofill.augmented.IFillCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IFillCallback
            public boolean isCompleted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.autofill.augmented.IFillCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.augmented.IFillCallback
            public void cancel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.autofill.augmented.IFillCallback.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
