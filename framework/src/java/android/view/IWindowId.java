package android.view;

/* loaded from: classes4.dex */
public interface IWindowId extends android.os.IInterface {
    boolean isFocused() throws android.os.RemoteException;

    void registerFocusObserver(android.view.IWindowFocusObserver iWindowFocusObserver) throws android.os.RemoteException;

    void unregisterFocusObserver(android.view.IWindowFocusObserver iWindowFocusObserver) throws android.os.RemoteException;

    public static class Default implements android.view.IWindowId {
        @Override // android.view.IWindowId
        public void registerFocusObserver(android.view.IWindowFocusObserver iWindowFocusObserver) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowId
        public void unregisterFocusObserver(android.view.IWindowFocusObserver iWindowFocusObserver) throws android.os.RemoteException {
        }

        @Override // android.view.IWindowId
        public boolean isFocused() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IWindowId {
        public static final java.lang.String DESCRIPTOR = "android.view.IWindowId";
        static final int TRANSACTION_isFocused = 3;
        static final int TRANSACTION_registerFocusObserver = 1;
        static final int TRANSACTION_unregisterFocusObserver = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.IWindowId asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IWindowId)) {
                return (android.view.IWindowId) queryLocalInterface;
            }
            return new android.view.IWindowId.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerFocusObserver";
                case 2:
                    return "unregisterFocusObserver";
                case 3:
                    return "isFocused";
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
                    android.view.IWindowFocusObserver asInterface = android.view.IWindowFocusObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerFocusObserver(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.view.IWindowFocusObserver asInterface2 = android.view.IWindowFocusObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterFocusObserver(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean isFocused = isFocused();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFocused);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IWindowId {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IWindowId.Stub.DESCRIPTOR;
            }

            @Override // android.view.IWindowId
            public void registerFocusObserver(android.view.IWindowFocusObserver iWindowFocusObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowId.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindowFocusObserver);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowId
            public void unregisterFocusObserver(android.view.IWindowFocusObserver iWindowFocusObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowId.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iWindowFocusObserver);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.view.IWindowId
            public boolean isFocused() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.view.IWindowId.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
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
