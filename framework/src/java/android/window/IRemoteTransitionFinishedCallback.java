package android.window;

/* loaded from: classes4.dex */
public interface IRemoteTransitionFinishedCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.IRemoteTransitionFinishedCallback";

    void onTransitionFinished(android.window.WindowContainerTransaction windowContainerTransaction, android.view.SurfaceControl.Transaction transaction) throws android.os.RemoteException;

    public static class Default implements android.window.IRemoteTransitionFinishedCallback {
        @Override // android.window.IRemoteTransitionFinishedCallback
        public void onTransitionFinished(android.window.WindowContainerTransaction windowContainerTransaction, android.view.SurfaceControl.Transaction transaction) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.IRemoteTransitionFinishedCallback {
        static final int TRANSACTION_onTransitionFinished = 1;

        public Stub() {
            attachInterface(this, android.window.IRemoteTransitionFinishedCallback.DESCRIPTOR);
        }

        public static android.window.IRemoteTransitionFinishedCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.IRemoteTransitionFinishedCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.IRemoteTransitionFinishedCallback)) {
                return (android.window.IRemoteTransitionFinishedCallback) queryLocalInterface;
            }
            return new android.window.IRemoteTransitionFinishedCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onTransitionFinished";
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
                parcel.enforceInterface(android.window.IRemoteTransitionFinishedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.IRemoteTransitionFinishedCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.window.WindowContainerTransaction windowContainerTransaction = (android.window.WindowContainerTransaction) parcel.readTypedObject(android.window.WindowContainerTransaction.CREATOR);
                    android.view.SurfaceControl.Transaction transaction = (android.view.SurfaceControl.Transaction) parcel.readTypedObject(android.view.SurfaceControl.Transaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTransitionFinished(windowContainerTransaction, transaction);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.IRemoteTransitionFinishedCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.IRemoteTransitionFinishedCallback.DESCRIPTOR;
            }

            @Override // android.window.IRemoteTransitionFinishedCallback
            public void onTransitionFinished(android.window.WindowContainerTransaction windowContainerTransaction, android.view.SurfaceControl.Transaction transaction) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.window.IRemoteTransitionFinishedCallback.DESCRIPTOR);
                    obtain.writeTypedObject(windowContainerTransaction, 0);
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
