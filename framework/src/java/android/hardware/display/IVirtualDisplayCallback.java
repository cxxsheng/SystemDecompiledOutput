package android.hardware.display;

/* loaded from: classes2.dex */
public interface IVirtualDisplayCallback extends android.os.IInterface {
    void onPaused() throws android.os.RemoteException;

    void onResumed() throws android.os.RemoteException;

    void onStopped() throws android.os.RemoteException;

    public static class Default implements android.hardware.display.IVirtualDisplayCallback {
        @Override // android.hardware.display.IVirtualDisplayCallback
        public void onPaused() throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IVirtualDisplayCallback
        public void onResumed() throws android.os.RemoteException {
        }

        @Override // android.hardware.display.IVirtualDisplayCallback
        public void onStopped() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.display.IVirtualDisplayCallback {
        public static final java.lang.String DESCRIPTOR = "android.hardware.display.IVirtualDisplayCallback";
        static final int TRANSACTION_onPaused = 1;
        static final int TRANSACTION_onResumed = 2;
        static final int TRANSACTION_onStopped = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.display.IVirtualDisplayCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.display.IVirtualDisplayCallback)) {
                return (android.hardware.display.IVirtualDisplayCallback) queryLocalInterface;
            }
            return new android.hardware.display.IVirtualDisplayCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onPaused";
                case 2:
                    return "onResumed";
                case 3:
                    return "onStopped";
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
                    onPaused();
                    return true;
                case 2:
                    onResumed();
                    return true;
                case 3:
                    onStopped();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.display.IVirtualDisplayCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.display.IVirtualDisplayCallback.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.display.IVirtualDisplayCallback
            public void onPaused() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IVirtualDisplayCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IVirtualDisplayCallback
            public void onResumed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IVirtualDisplayCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.display.IVirtualDisplayCallback
            public void onStopped() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.display.IVirtualDisplayCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
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
