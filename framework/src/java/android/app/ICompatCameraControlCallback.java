package android.app;

/* loaded from: classes.dex */
public interface ICompatCameraControlCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.ICompatCameraControlCallback";

    void applyCameraCompatTreatment() throws android.os.RemoteException;

    void revertCameraCompatTreatment() throws android.os.RemoteException;

    public static class Default implements android.app.ICompatCameraControlCallback {
        @Override // android.app.ICompatCameraControlCallback
        public void applyCameraCompatTreatment() throws android.os.RemoteException {
        }

        @Override // android.app.ICompatCameraControlCallback
        public void revertCameraCompatTreatment() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.ICompatCameraControlCallback {
        static final int TRANSACTION_applyCameraCompatTreatment = 1;
        static final int TRANSACTION_revertCameraCompatTreatment = 2;

        public Stub() {
            attachInterface(this, android.app.ICompatCameraControlCallback.DESCRIPTOR);
        }

        public static android.app.ICompatCameraControlCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.ICompatCameraControlCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.ICompatCameraControlCallback)) {
                return (android.app.ICompatCameraControlCallback) queryLocalInterface;
            }
            return new android.app.ICompatCameraControlCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "applyCameraCompatTreatment";
                case 2:
                    return "revertCameraCompatTreatment";
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
                parcel.enforceInterface(android.app.ICompatCameraControlCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.ICompatCameraControlCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    applyCameraCompatTreatment();
                    return true;
                case 2:
                    revertCameraCompatTreatment();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.ICompatCameraControlCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.ICompatCameraControlCallback.DESCRIPTOR;
            }

            @Override // android.app.ICompatCameraControlCallback
            public void applyCameraCompatTreatment() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ICompatCameraControlCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.ICompatCameraControlCallback
            public void revertCameraCompatTreatment() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.app.ICompatCameraControlCallback.DESCRIPTOR);
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
