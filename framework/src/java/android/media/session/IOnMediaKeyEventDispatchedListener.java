package android.media.session;

/* loaded from: classes2.dex */
public interface IOnMediaKeyEventDispatchedListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.session.IOnMediaKeyEventDispatchedListener";

    void onMediaKeyEventDispatched(android.view.KeyEvent keyEvent, java.lang.String str, android.media.session.MediaSession.Token token) throws android.os.RemoteException;

    public static class Default implements android.media.session.IOnMediaKeyEventDispatchedListener {
        @Override // android.media.session.IOnMediaKeyEventDispatchedListener
        public void onMediaKeyEventDispatched(android.view.KeyEvent keyEvent, java.lang.String str, android.media.session.MediaSession.Token token) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.session.IOnMediaKeyEventDispatchedListener {
        static final int TRANSACTION_onMediaKeyEventDispatched = 1;

        public Stub() {
            attachInterface(this, android.media.session.IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
        }

        public static android.media.session.IOnMediaKeyEventDispatchedListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.session.IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.session.IOnMediaKeyEventDispatchedListener)) {
                return (android.media.session.IOnMediaKeyEventDispatchedListener) queryLocalInterface;
            }
            return new android.media.session.IOnMediaKeyEventDispatchedListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onMediaKeyEventDispatched";
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
                parcel.enforceInterface(android.media.session.IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.session.IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    java.lang.String readString = parcel.readString();
                    android.media.session.MediaSession.Token token = (android.media.session.MediaSession.Token) parcel.readTypedObject(android.media.session.MediaSession.Token.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMediaKeyEventDispatched(keyEvent, readString, token);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.session.IOnMediaKeyEventDispatchedListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.session.IOnMediaKeyEventDispatchedListener.DESCRIPTOR;
            }

            @Override // android.media.session.IOnMediaKeyEventDispatchedListener
            public void onMediaKeyEventDispatched(android.view.KeyEvent keyEvent, java.lang.String str, android.media.session.MediaSession.Token token) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.IOnMediaKeyEventDispatchedListener.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(token, 0);
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
