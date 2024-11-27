package android.view;

/* loaded from: classes4.dex */
public interface IDisplayChangeWindowCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.IDisplayChangeWindowCallback";

    void continueDisplayChange(android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException;

    public static class Default implements android.view.IDisplayChangeWindowCallback {
        @Override // android.view.IDisplayChangeWindowCallback
        public void continueDisplayChange(android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IDisplayChangeWindowCallback {
        static final int TRANSACTION_continueDisplayChange = 1;

        public Stub() {
            attachInterface(this, android.view.IDisplayChangeWindowCallback.DESCRIPTOR);
        }

        public static android.view.IDisplayChangeWindowCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.IDisplayChangeWindowCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IDisplayChangeWindowCallback)) {
                return (android.view.IDisplayChangeWindowCallback) queryLocalInterface;
            }
            return new android.view.IDisplayChangeWindowCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "continueDisplayChange";
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
                parcel.enforceInterface(android.view.IDisplayChangeWindowCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.IDisplayChangeWindowCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.window.WindowContainerTransaction windowContainerTransaction = (android.window.WindowContainerTransaction) parcel.readTypedObject(android.window.WindowContainerTransaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    continueDisplayChange(windowContainerTransaction);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IDisplayChangeWindowCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IDisplayChangeWindowCallback.DESCRIPTOR;
            }

            @Override // android.view.IDisplayChangeWindowCallback
            public void continueDisplayChange(android.window.WindowContainerTransaction windowContainerTransaction) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IDisplayChangeWindowCallback.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
