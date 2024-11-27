package android.hardware.biometrics;

/* loaded from: classes.dex */
public interface IInvalidationCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.biometrics.IInvalidationCallback";

    void onCompleted() throws android.os.RemoteException;

    public static class Default implements android.hardware.biometrics.IInvalidationCallback {
        @Override // android.hardware.biometrics.IInvalidationCallback
        public void onCompleted() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.biometrics.IInvalidationCallback {
        static final int TRANSACTION_onCompleted = 1;

        public Stub() {
            attachInterface(this, android.hardware.biometrics.IInvalidationCallback.DESCRIPTOR);
        }

        public static android.hardware.biometrics.IInvalidationCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.biometrics.IInvalidationCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.biometrics.IInvalidationCallback)) {
                return (android.hardware.biometrics.IInvalidationCallback) queryLocalInterface;
            }
            return new android.hardware.biometrics.IInvalidationCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCompleted";
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
                parcel.enforceInterface(android.hardware.biometrics.IInvalidationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.biometrics.IInvalidationCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onCompleted();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.biometrics.IInvalidationCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.biometrics.IInvalidationCallback.DESCRIPTOR;
            }

            @Override // android.hardware.biometrics.IInvalidationCallback
            public void onCompleted() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.biometrics.IInvalidationCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
