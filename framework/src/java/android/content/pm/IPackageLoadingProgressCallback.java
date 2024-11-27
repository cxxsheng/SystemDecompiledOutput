package android.content.pm;

/* loaded from: classes.dex */
public interface IPackageLoadingProgressCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.content.pm.IPackageLoadingProgressCallback";

    void onPackageLoadingProgressChanged(float f) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IPackageLoadingProgressCallback {
        @Override // android.content.pm.IPackageLoadingProgressCallback
        public void onPackageLoadingProgressChanged(float f) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IPackageLoadingProgressCallback {
        static final int TRANSACTION_onPackageLoadingProgressChanged = 1;

        public Stub() {
            attachInterface(this, android.content.pm.IPackageLoadingProgressCallback.DESCRIPTOR);
        }

        public static android.content.pm.IPackageLoadingProgressCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.content.pm.IPackageLoadingProgressCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IPackageLoadingProgressCallback)) {
                return (android.content.pm.IPackageLoadingProgressCallback) queryLocalInterface;
            }
            return new android.content.pm.IPackageLoadingProgressCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onPackageLoadingProgressChanged";
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
                parcel.enforceInterface(android.content.pm.IPackageLoadingProgressCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.content.pm.IPackageLoadingProgressCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onPackageLoadingProgressChanged(readFloat);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IPackageLoadingProgressCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IPackageLoadingProgressCallback.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageLoadingProgressCallback
            public void onPackageLoadingProgressChanged(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageLoadingProgressCallback.DESCRIPTOR);
                    obtain.writeFloat(f);
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
