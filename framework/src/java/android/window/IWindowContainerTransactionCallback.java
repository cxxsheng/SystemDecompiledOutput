package android.window;

/* loaded from: classes4.dex */
public interface IWindowContainerTransactionCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.IWindowContainerTransactionCallback";

    void onTransactionReady(int i, android.view.SurfaceControl.Transaction transaction) throws android.os.RemoteException;

    public static class Default implements android.window.IWindowContainerTransactionCallback {
        @Override // android.window.IWindowContainerTransactionCallback
        public void onTransactionReady(int i, android.view.SurfaceControl.Transaction transaction) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.IWindowContainerTransactionCallback {
        static final int TRANSACTION_onTransactionReady = 1;

        public Stub() {
            attachInterface(this, android.window.IWindowContainerTransactionCallback.DESCRIPTOR);
        }

        public static android.window.IWindowContainerTransactionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.IWindowContainerTransactionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.IWindowContainerTransactionCallback)) {
                return (android.window.IWindowContainerTransactionCallback) queryLocalInterface;
            }
            return new android.window.IWindowContainerTransactionCallback.Stub.Proxy(iBinder);
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
                parcel.enforceInterface(android.window.IWindowContainerTransactionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.IWindowContainerTransactionCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.view.SurfaceControl.Transaction transaction = (android.view.SurfaceControl.Transaction) parcel.readTypedObject(android.view.SurfaceControl.Transaction.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTransactionReady(readInt, transaction);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.IWindowContainerTransactionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.IWindowContainerTransactionCallback.DESCRIPTOR;
            }

            @Override // android.window.IWindowContainerTransactionCallback
            public void onTransactionReady(int i, android.view.SurfaceControl.Transaction transaction) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IWindowContainerTransactionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(transaction, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
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
