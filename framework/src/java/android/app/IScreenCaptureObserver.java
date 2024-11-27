package android.app;

/* loaded from: classes.dex */
public interface IScreenCaptureObserver extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.IScreenCaptureObserver";

    void onScreenCaptured() throws android.os.RemoteException;

    public static class Default implements android.app.IScreenCaptureObserver {
        @Override // android.app.IScreenCaptureObserver
        public void onScreenCaptured() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.IScreenCaptureObserver {
        static final int TRANSACTION_onScreenCaptured = 1;

        public Stub() {
            attachInterface(this, android.app.IScreenCaptureObserver.DESCRIPTOR);
        }

        public static android.app.IScreenCaptureObserver asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.IScreenCaptureObserver.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.IScreenCaptureObserver)) {
                return (android.app.IScreenCaptureObserver) queryLocalInterface;
            }
            return new android.app.IScreenCaptureObserver.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onScreenCaptured";
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
                parcel.enforceInterface(android.app.IScreenCaptureObserver.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.IScreenCaptureObserver.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onScreenCaptured();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.IScreenCaptureObserver {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.IScreenCaptureObserver.DESCRIPTOR;
            }

            @Override // android.app.IScreenCaptureObserver
            public void onScreenCaptured() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.IScreenCaptureObserver.DESCRIPTOR);
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
