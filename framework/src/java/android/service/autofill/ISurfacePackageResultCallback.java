package android.service.autofill;

/* loaded from: classes3.dex */
public interface ISurfacePackageResultCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.autofill.ISurfacePackageResultCallback";

    void onResult(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) throws android.os.RemoteException;

    public static class Default implements android.service.autofill.ISurfacePackageResultCallback {
        @Override // android.service.autofill.ISurfacePackageResultCallback
        public void onResult(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.autofill.ISurfacePackageResultCallback {
        static final int TRANSACTION_onResult = 1;

        public Stub() {
            attachInterface(this, android.service.autofill.ISurfacePackageResultCallback.DESCRIPTOR);
        }

        public static android.service.autofill.ISurfacePackageResultCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.autofill.ISurfacePackageResultCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.autofill.ISurfacePackageResultCallback)) {
                return (android.service.autofill.ISurfacePackageResultCallback) queryLocalInterface;
            }
            return new android.service.autofill.ISurfacePackageResultCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onResult";
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
                parcel.enforceInterface(android.service.autofill.ISurfacePackageResultCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.autofill.ISurfacePackageResultCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.SurfaceControlViewHost.SurfacePackage surfacePackage = (android.view.SurfaceControlViewHost.SurfacePackage) parcel.readTypedObject(android.view.SurfaceControlViewHost.SurfacePackage.CREATOR);
                    parcel.enforceNoDataAvail();
                    onResult(surfacePackage);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.autofill.ISurfacePackageResultCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.autofill.ISurfacePackageResultCallback.DESCRIPTOR;
            }

            @Override // android.service.autofill.ISurfacePackageResultCallback
            public void onResult(android.view.SurfaceControlViewHost.SurfacePackage surfacePackage) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.ISurfacePackageResultCallback.DESCRIPTOR);
                    obtain.writeTypedObject(surfacePackage, 0);
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
