package android.hardware.fingerprint;

/* loaded from: classes2.dex */
public interface IUdfpsOverlayControllerCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.fingerprint.IUdfpsOverlayControllerCallback";

    void onUserCanceled() throws android.os.RemoteException;

    public static class Default implements android.hardware.fingerprint.IUdfpsOverlayControllerCallback {
        @Override // android.hardware.fingerprint.IUdfpsOverlayControllerCallback
        public void onUserCanceled() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.fingerprint.IUdfpsOverlayControllerCallback {
        static final int TRANSACTION_onUserCanceled = 1;

        public Stub() {
            attachInterface(this, android.hardware.fingerprint.IUdfpsOverlayControllerCallback.DESCRIPTOR);
        }

        public static android.hardware.fingerprint.IUdfpsOverlayControllerCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.fingerprint.IUdfpsOverlayControllerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.fingerprint.IUdfpsOverlayControllerCallback)) {
                return (android.hardware.fingerprint.IUdfpsOverlayControllerCallback) queryLocalInterface;
            }
            return new android.hardware.fingerprint.IUdfpsOverlayControllerCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUserCanceled";
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
                parcel.enforceInterface(android.hardware.fingerprint.IUdfpsOverlayControllerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.fingerprint.IUdfpsOverlayControllerCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onUserCanceled();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.fingerprint.IUdfpsOverlayControllerCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.fingerprint.IUdfpsOverlayControllerCallback.DESCRIPTOR;
            }

            @Override // android.hardware.fingerprint.IUdfpsOverlayControllerCallback
            public void onUserCanceled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.fingerprint.IUdfpsOverlayControllerCallback.DESCRIPTOR);
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
