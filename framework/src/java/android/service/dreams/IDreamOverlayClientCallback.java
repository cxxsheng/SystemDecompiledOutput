package android.service.dreams;

/* loaded from: classes3.dex */
public interface IDreamOverlayClientCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.dreams.IDreamOverlayClientCallback";

    void onDreamOverlayClient(android.service.dreams.IDreamOverlayClient iDreamOverlayClient) throws android.os.RemoteException;

    public static class Default implements android.service.dreams.IDreamOverlayClientCallback {
        @Override // android.service.dreams.IDreamOverlayClientCallback
        public void onDreamOverlayClient(android.service.dreams.IDreamOverlayClient iDreamOverlayClient) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.dreams.IDreamOverlayClientCallback {
        static final int TRANSACTION_onDreamOverlayClient = 1;

        public Stub() {
            attachInterface(this, android.service.dreams.IDreamOverlayClientCallback.DESCRIPTOR);
        }

        public static android.service.dreams.IDreamOverlayClientCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.dreams.IDreamOverlayClientCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.dreams.IDreamOverlayClientCallback)) {
                return (android.service.dreams.IDreamOverlayClientCallback) queryLocalInterface;
            }
            return new android.service.dreams.IDreamOverlayClientCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDreamOverlayClient";
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
                parcel.enforceInterface(android.service.dreams.IDreamOverlayClientCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.dreams.IDreamOverlayClientCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.service.dreams.IDreamOverlayClient asInterface = android.service.dreams.IDreamOverlayClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onDreamOverlayClient(asInterface);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.dreams.IDreamOverlayClientCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.dreams.IDreamOverlayClientCallback.DESCRIPTOR;
            }

            @Override // android.service.dreams.IDreamOverlayClientCallback
            public void onDreamOverlayClient(android.service.dreams.IDreamOverlayClient iDreamOverlayClient) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamOverlayClientCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iDreamOverlayClient);
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
