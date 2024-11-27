package android.window;

/* loaded from: classes4.dex */
public interface IUnhandledDragCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.IUnhandledDragCallback";

    void notifyUnhandledDropComplete(boolean z) throws android.os.RemoteException;

    public static class Default implements android.window.IUnhandledDragCallback {
        @Override // android.window.IUnhandledDragCallback
        public void notifyUnhandledDropComplete(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.IUnhandledDragCallback {
        static final int TRANSACTION_notifyUnhandledDropComplete = 1;

        public Stub() {
            attachInterface(this, android.window.IUnhandledDragCallback.DESCRIPTOR);
        }

        public static android.window.IUnhandledDragCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.IUnhandledDragCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.IUnhandledDragCallback)) {
                return (android.window.IUnhandledDragCallback) queryLocalInterface;
            }
            return new android.window.IUnhandledDragCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "notifyUnhandledDropComplete";
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
                parcel.enforceInterface(android.window.IUnhandledDragCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.IUnhandledDragCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyUnhandledDropComplete(readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.IUnhandledDragCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.IUnhandledDragCallback.DESCRIPTOR;
            }

            @Override // android.window.IUnhandledDragCallback
            public void notifyUnhandledDropComplete(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IUnhandledDragCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
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
