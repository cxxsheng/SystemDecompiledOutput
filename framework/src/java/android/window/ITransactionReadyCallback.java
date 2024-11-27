package android.window;

/* loaded from: classes4.dex */
public interface ITransactionReadyCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.ITransactionReadyCallback";

    void onTransactionReady(android.view.SurfaceControl.Transaction transaction) throws android.os.RemoteException;

    public static class Default implements android.window.ITransactionReadyCallback {
        @Override // android.window.ITransactionReadyCallback
        public void onTransactionReady(android.view.SurfaceControl.Transaction transaction) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.ITransactionReadyCallback {
        static final int TRANSACTION_onTransactionReady = 1;

        public Stub() {
            attachInterface(this, android.window.ITransactionReadyCallback.DESCRIPTOR);
        }

        public static android.window.ITransactionReadyCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.ITransactionReadyCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.ITransactionReadyCallback)) {
                return (android.window.ITransactionReadyCallback) queryLocalInterface;
            }
            return new android.window.ITransactionReadyCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTransactionReady";
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
                parcel.enforceInterface(android.window.ITransactionReadyCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.ITransactionReadyCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.SurfaceControl.Transaction transaction = (android.view.SurfaceControl.Transaction) parcel.readTypedObject(android.view.SurfaceControl.Transaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTransactionReady(transaction);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.ITransactionReadyCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.ITransactionReadyCallback.DESCRIPTOR;
            }

            @Override // android.window.ITransactionReadyCallback
            public void onTransactionReady(android.view.SurfaceControl.Transaction transaction) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.ITransactionReadyCallback.DESCRIPTOR);
                    obtain.writeTypedObject(transaction, 0);
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
