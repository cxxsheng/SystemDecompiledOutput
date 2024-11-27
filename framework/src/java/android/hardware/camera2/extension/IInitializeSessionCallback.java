package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public interface IInitializeSessionCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.camera2.extension.IInitializeSessionCallback";

    void onFailure() throws android.os.RemoteException;

    void onSuccess() throws android.os.RemoteException;

    public static class Default implements android.hardware.camera2.extension.IInitializeSessionCallback {
        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onSuccess() throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.IInitializeSessionCallback
        public void onFailure() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.camera2.extension.IInitializeSessionCallback {
        static final int TRANSACTION_onFailure = 2;
        static final int TRANSACTION_onSuccess = 1;

        public Stub() {
            attachInterface(this, android.hardware.camera2.extension.IInitializeSessionCallback.DESCRIPTOR);
        }

        public static android.hardware.camera2.extension.IInitializeSessionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.camera2.extension.IInitializeSessionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.camera2.extension.IInitializeSessionCallback)) {
                return (android.hardware.camera2.extension.IInitializeSessionCallback) queryLocalInterface;
            }
            return new android.hardware.camera2.extension.IInitializeSessionCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSuccess";
                case 2:
                    return "onFailure";
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
                parcel.enforceInterface(android.hardware.camera2.extension.IInitializeSessionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.camera2.extension.IInitializeSessionCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onSuccess();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    onFailure();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.camera2.extension.IInitializeSessionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.camera2.extension.IInitializeSessionCallback.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.IInitializeSessionCallback
            public void onSuccess() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IInitializeSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.IInitializeSessionCallback
            public void onFailure() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.IInitializeSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
