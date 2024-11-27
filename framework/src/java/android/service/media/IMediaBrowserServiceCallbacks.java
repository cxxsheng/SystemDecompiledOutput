package android.service.media;

/* loaded from: classes3.dex */
public interface IMediaBrowserServiceCallbacks extends android.os.IInterface {
    void onConnect(java.lang.String str, android.media.session.MediaSession.Token token, android.os.Bundle bundle) throws android.os.RemoteException;

    void onConnectFailed() throws android.os.RemoteException;

    void onDisconnect() throws android.os.RemoteException;

    void onLoadChildren(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.service.media.IMediaBrowserServiceCallbacks {
        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onConnect(java.lang.String str, android.media.session.MediaSession.Token token, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onConnectFailed() throws android.os.RemoteException {
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onLoadChildren(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.service.media.IMediaBrowserServiceCallbacks
        public void onDisconnect() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.media.IMediaBrowserServiceCallbacks {
        public static final java.lang.String DESCRIPTOR = "android.service.media.IMediaBrowserServiceCallbacks";
        static final int TRANSACTION_onConnect = 1;
        static final int TRANSACTION_onConnectFailed = 2;
        static final int TRANSACTION_onDisconnect = 4;
        static final int TRANSACTION_onLoadChildren = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.media.IMediaBrowserServiceCallbacks asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.media.IMediaBrowserServiceCallbacks)) {
                return (android.service.media.IMediaBrowserServiceCallbacks) queryLocalInterface;
            }
            return new android.service.media.IMediaBrowserServiceCallbacks.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onConnect";
                case 2:
                    return "onConnectFailed";
                case 3:
                    return "onLoadChildren";
                case 4:
                    return "onDisconnect";
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
                    java.lang.String readString = parcel.readString();
                    android.media.session.MediaSession.Token token = (android.media.session.MediaSession.Token) parcel.readTypedObject(android.media.session.MediaSession.Token.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConnect(readString, token, bundle);
                    return true;
                case 2:
                    onConnectFailed();
                    return true;
                case 3:
                    java.lang.String readString2 = parcel.readString();
                    android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onLoadChildren(readString2, parceledListSlice, bundle2);
                    return true;
                case 4:
                    onDisconnect();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.media.IMediaBrowserServiceCallbacks {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.media.IMediaBrowserServiceCallbacks.Stub.DESCRIPTOR;
            }

            @Override // android.service.media.IMediaBrowserServiceCallbacks
            public void onConnect(java.lang.String str, android.media.session.MediaSession.Token token, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.media.IMediaBrowserServiceCallbacks.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(token, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserServiceCallbacks
            public void onConnectFailed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.media.IMediaBrowserServiceCallbacks.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserServiceCallbacks
            public void onLoadChildren(java.lang.String str, android.content.pm.ParceledListSlice parceledListSlice, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.media.IMediaBrowserServiceCallbacks.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.media.IMediaBrowserServiceCallbacks
            public void onDisconnect() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.media.IMediaBrowserServiceCallbacks.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
