package android.service.dreams;

/* loaded from: classes3.dex */
public interface IDreamOverlayClient extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.dreams.IDreamOverlayClient";

    void endDream() throws android.os.RemoteException;

    void startDream(android.view.WindowManager.LayoutParams layoutParams, android.service.dreams.IDreamOverlayCallback iDreamOverlayCallback, java.lang.String str, boolean z) throws android.os.RemoteException;

    void wakeUp() throws android.os.RemoteException;

    public static class Default implements android.service.dreams.IDreamOverlayClient {
        @Override // android.service.dreams.IDreamOverlayClient
        public void startDream(android.view.WindowManager.LayoutParams layoutParams, android.service.dreams.IDreamOverlayCallback iDreamOverlayCallback, java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void wakeUp() throws android.os.RemoteException {
        }

        @Override // android.service.dreams.IDreamOverlayClient
        public void endDream() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.dreams.IDreamOverlayClient {
        static final int TRANSACTION_endDream = 3;
        static final int TRANSACTION_startDream = 1;
        static final int TRANSACTION_wakeUp = 2;

        public Stub() {
            attachInterface(this, android.service.dreams.IDreamOverlayClient.DESCRIPTOR);
        }

        public static android.service.dreams.IDreamOverlayClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.dreams.IDreamOverlayClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.dreams.IDreamOverlayClient)) {
                return (android.service.dreams.IDreamOverlayClient) queryLocalInterface;
            }
            return new android.service.dreams.IDreamOverlayClient.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startDream";
                case 2:
                    return "wakeUp";
                case 3:
                    return "endDream";
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
                parcel.enforceInterface(android.service.dreams.IDreamOverlayClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.dreams.IDreamOverlayClient.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.WindowManager.LayoutParams layoutParams = (android.view.WindowManager.LayoutParams) parcel.readTypedObject(android.view.WindowManager.LayoutParams.CREATOR);
                    android.service.dreams.IDreamOverlayCallback asInterface = android.service.dreams.IDreamOverlayCallback.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startDream(layoutParams, asInterface, readString, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    wakeUp();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    endDream();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.dreams.IDreamOverlayClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.dreams.IDreamOverlayClient.DESCRIPTOR;
            }

            @Override // android.service.dreams.IDreamOverlayClient
            public void startDream(android.view.WindowManager.LayoutParams layoutParams, android.service.dreams.IDreamOverlayCallback iDreamOverlayCallback, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamOverlayClient.DESCRIPTOR);
                    obtain.writeTypedObject(layoutParams, 0);
                    obtain.writeStrongInterface(iDreamOverlayCallback);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamOverlayClient
            public void wakeUp() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamOverlayClient.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamOverlayClient
            public void endDream() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.dreams.IDreamOverlayClient.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
