package android.view;

/* loaded from: classes4.dex */
public interface IInputMonitorHost extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.view.IInputMonitorHost";

    void dispose() throws android.os.RemoteException;

    void pilferPointers() throws android.os.RemoteException;

    public static class Default implements android.view.IInputMonitorHost {
        @Override // android.view.IInputMonitorHost
        public void pilferPointers() throws android.os.RemoteException {
        }

        @Override // android.view.IInputMonitorHost
        public void dispose() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.IInputMonitorHost {
        static final int TRANSACTION_dispose = 2;
        static final int TRANSACTION_pilferPointers = 1;

        public Stub() {
            attachInterface(this, android.view.IInputMonitorHost.DESCRIPTOR);
        }

        public static android.view.IInputMonitorHost asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.view.IInputMonitorHost.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.IInputMonitorHost)) {
                return (android.view.IInputMonitorHost) queryLocalInterface;
            }
            return new android.view.IInputMonitorHost.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "pilferPointers";
                case 2:
                    return "dispose";
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
                parcel.enforceInterface(android.view.IInputMonitorHost.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.view.IInputMonitorHost.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    pilferPointers();
                    return true;
                case 2:
                    dispose();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.IInputMonitorHost {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.IInputMonitorHost.DESCRIPTOR;
            }

            @Override // android.view.IInputMonitorHost
            public void pilferPointers() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IInputMonitorHost.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.IInputMonitorHost
            public void dispose() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.IInputMonitorHost.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
