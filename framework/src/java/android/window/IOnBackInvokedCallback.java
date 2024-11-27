package android.window;

/* loaded from: classes4.dex */
public interface IOnBackInvokedCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.IOnBackInvokedCallback";

    void onBackCancelled() throws android.os.RemoteException;

    void onBackInvoked() throws android.os.RemoteException;

    void onBackProgressed(android.window.BackMotionEvent backMotionEvent) throws android.os.RemoteException;

    void onBackStarted(android.window.BackMotionEvent backMotionEvent) throws android.os.RemoteException;

    public static class Default implements android.window.IOnBackInvokedCallback {
        @Override // android.window.IOnBackInvokedCallback
        public void onBackStarted(android.window.BackMotionEvent backMotionEvent) throws android.os.RemoteException {
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackProgressed(android.window.BackMotionEvent backMotionEvent) throws android.os.RemoteException {
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackCancelled() throws android.os.RemoteException {
        }

        @Override // android.window.IOnBackInvokedCallback
        public void onBackInvoked() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.IOnBackInvokedCallback {
        static final int TRANSACTION_onBackCancelled = 3;
        static final int TRANSACTION_onBackInvoked = 4;
        static final int TRANSACTION_onBackProgressed = 2;
        static final int TRANSACTION_onBackStarted = 1;

        public Stub() {
            attachInterface(this, android.window.IOnBackInvokedCallback.DESCRIPTOR);
        }

        public static android.window.IOnBackInvokedCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.IOnBackInvokedCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.IOnBackInvokedCallback)) {
                return (android.window.IOnBackInvokedCallback) queryLocalInterface;
            }
            return new android.window.IOnBackInvokedCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onBackStarted";
                case 2:
                    return "onBackProgressed";
                case 3:
                    return "onBackCancelled";
                case 4:
                    return "onBackInvoked";
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
                parcel.enforceInterface(android.window.IOnBackInvokedCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.IOnBackInvokedCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.window.BackMotionEvent backMotionEvent = (android.window.BackMotionEvent) parcel.readTypedObject(android.window.BackMotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBackStarted(backMotionEvent);
                    return true;
                case 2:
                    android.window.BackMotionEvent backMotionEvent2 = (android.window.BackMotionEvent) parcel.readTypedObject(android.window.BackMotionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBackProgressed(backMotionEvent2);
                    return true;
                case 3:
                    onBackCancelled();
                    return true;
                case 4:
                    onBackInvoked();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.IOnBackInvokedCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.IOnBackInvokedCallback.DESCRIPTOR;
            }

            @Override // android.window.IOnBackInvokedCallback
            public void onBackStarted(android.window.BackMotionEvent backMotionEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IOnBackInvokedCallback.DESCRIPTOR);
                    obtain.writeTypedObject(backMotionEvent, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.IOnBackInvokedCallback
            public void onBackProgressed(android.window.BackMotionEvent backMotionEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IOnBackInvokedCallback.DESCRIPTOR);
                    obtain.writeTypedObject(backMotionEvent, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.IOnBackInvokedCallback
            public void onBackCancelled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IOnBackInvokedCallback.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.window.IOnBackInvokedCallback
            public void onBackInvoked() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.IOnBackInvokedCallback.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
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
