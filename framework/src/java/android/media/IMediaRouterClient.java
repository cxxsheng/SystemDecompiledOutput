package android.media;

/* loaded from: classes2.dex */
public interface IMediaRouterClient extends android.os.IInterface {
    void onGroupRouteSelected(java.lang.String str) throws android.os.RemoteException;

    void onRestoreRoute() throws android.os.RemoteException;

    void onStateChanged() throws android.os.RemoteException;

    public static class Default implements android.media.IMediaRouterClient {
        @Override // android.media.IMediaRouterClient
        public void onStateChanged() throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterClient
        public void onRestoreRoute() throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterClient
        public void onGroupRouteSelected(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IMediaRouterClient {
        public static final java.lang.String DESCRIPTOR = "android.media.IMediaRouterClient";
        static final int TRANSACTION_onGroupRouteSelected = 3;
        static final int TRANSACTION_onRestoreRoute = 2;
        static final int TRANSACTION_onStateChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.IMediaRouterClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IMediaRouterClient)) {
                return (android.media.IMediaRouterClient) queryLocalInterface;
            }
            return new android.media.IMediaRouterClient.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStateChanged";
                case 2:
                    return "onRestoreRoute";
                case 3:
                    return "onGroupRouteSelected";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    onStateChanged();
                    return true;
                case 2:
                    onRestoreRoute();
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onGroupRouteSelected(readString);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IMediaRouterClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IMediaRouterClient.Stub.DESCRIPTOR;
            }

            @Override // android.media.IMediaRouterClient
            public void onStateChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterClient.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterClient
            public void onRestoreRoute() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterClient.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterClient
            public void onGroupRouteSelected(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterClient.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
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
