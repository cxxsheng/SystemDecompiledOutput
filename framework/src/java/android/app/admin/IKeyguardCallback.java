package android.app.admin;

/* loaded from: classes.dex */
public interface IKeyguardCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.admin.IKeyguardCallback";

    void onDismiss() throws android.os.RemoteException;

    void onRemoteContentReady(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) throws android.os.RemoteException;

    public static class Default implements android.app.admin.IKeyguardCallback {
        @Override // android.app.admin.IKeyguardCallback
        public void onRemoteContentReady(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) throws android.os.RemoteException {
        }

        @Override // android.app.admin.IKeyguardCallback
        public void onDismiss() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.admin.IKeyguardCallback {
        static final int TRANSACTION_onDismiss = 2;
        static final int TRANSACTION_onRemoteContentReady = 1;

        public Stub() {
            attachInterface(this, android.app.admin.IKeyguardCallback.DESCRIPTOR);
        }

        public static android.app.admin.IKeyguardCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.admin.IKeyguardCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.admin.IKeyguardCallback)) {
                return (android.app.admin.IKeyguardCallback) queryLocalInterface;
            }
            return new android.app.admin.IKeyguardCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onRemoteContentReady";
                case 2:
                    return "onDismiss";
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
                parcel.enforceInterface(android.app.admin.IKeyguardCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.admin.IKeyguardCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.SurfaceControlViewHost.SurfacePackage surfacePackage = (android.view.SurfaceControlViewHost.SurfacePackage) parcel.readTypedObject(android.view.SurfaceControlViewHost.SurfacePackage.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRemoteContentReady(surfacePackage);
                    return true;
                case 2:
                    onDismiss();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.admin.IKeyguardCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.admin.IKeyguardCallback.DESCRIPTOR;
            }

            @Override // android.app.admin.IKeyguardCallback
            public void onRemoteContentReady(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.admin.IKeyguardCallback.DESCRIPTOR);
                    obtain.writeTypedObject(surfacePackage, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.admin.IKeyguardCallback
            public void onDismiss() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.admin.IKeyguardCallback.DESCRIPTOR);
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
