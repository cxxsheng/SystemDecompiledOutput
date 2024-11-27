package android.window;

/* loaded from: classes4.dex */
public interface ISurfaceSyncGroupCompletedListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.window.ISurfaceSyncGroupCompletedListener";

    void onSurfaceSyncGroupComplete() throws android.os.RemoteException;

    public static class Default implements android.window.ISurfaceSyncGroupCompletedListener {
        @Override // android.window.ISurfaceSyncGroupCompletedListener
        public void onSurfaceSyncGroupComplete() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.window.ISurfaceSyncGroupCompletedListener {
        static final int TRANSACTION_onSurfaceSyncGroupComplete = 1;

        public Stub() {
            attachInterface(this, android.window.ISurfaceSyncGroupCompletedListener.DESCRIPTOR);
        }

        public static android.window.ISurfaceSyncGroupCompletedListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.window.ISurfaceSyncGroupCompletedListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.window.ISurfaceSyncGroupCompletedListener)) {
                return (android.window.ISurfaceSyncGroupCompletedListener) queryLocalInterface;
            }
            return new android.window.ISurfaceSyncGroupCompletedListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSurfaceSyncGroupComplete";
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
                parcel.enforceInterface(android.window.ISurfaceSyncGroupCompletedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.window.ISurfaceSyncGroupCompletedListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onSurfaceSyncGroupComplete();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.window.ISurfaceSyncGroupCompletedListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.window.ISurfaceSyncGroupCompletedListener.DESCRIPTOR;
            }

            @Override // android.window.ISurfaceSyncGroupCompletedListener
            public void onSurfaceSyncGroupComplete() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.window.ISurfaceSyncGroupCompletedListener.DESCRIPTOR);
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
