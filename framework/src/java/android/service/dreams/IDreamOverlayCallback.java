package android.service.dreams;

/* loaded from: classes3.dex */
public interface IDreamOverlayCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.dreams.IDreamOverlayCallback";

    void onExitRequested() throws android.os.RemoteException;

    public static class Default implements android.service.dreams.IDreamOverlayCallback {
        @Override // android.service.dreams.IDreamOverlayCallback
        public void onExitRequested() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.dreams.IDreamOverlayCallback {
        static final int TRANSACTION_onExitRequested = 1;

        public Stub() {
            attachInterface(this, android.service.dreams.IDreamOverlayCallback.DESCRIPTOR);
        }

        public static android.service.dreams.IDreamOverlayCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.dreams.IDreamOverlayCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.dreams.IDreamOverlayCallback)) {
                return (android.service.dreams.IDreamOverlayCallback) queryLocalInterface;
            }
            return new android.service.dreams.IDreamOverlayCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onExitRequested";
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
                parcel.enforceInterface(android.service.dreams.IDreamOverlayCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.dreams.IDreamOverlayCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onExitRequested();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.dreams.IDreamOverlayCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.dreams.IDreamOverlayCallback.DESCRIPTOR;
            }

            @Override // android.service.dreams.IDreamOverlayCallback
            public void onExitRequested() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamOverlayCallback.DESCRIPTOR);
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
