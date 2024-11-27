package android.media.session;

/* loaded from: classes2.dex */
public interface IOnMediaKeyListener extends android.os.IInterface {
    void onMediaKey(android.view.KeyEvent keyEvent, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    public static class Default implements android.media.session.IOnMediaKeyListener {
        @Override // android.media.session.IOnMediaKeyListener
        public void onMediaKey(android.view.KeyEvent keyEvent, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.session.IOnMediaKeyListener {
        public static final java.lang.String DESCRIPTOR = "android.media.session.IOnMediaKeyListener";
        static final int TRANSACTION_onMediaKey = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.session.IOnMediaKeyListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.session.IOnMediaKeyListener)) {
                return (android.media.session.IOnMediaKeyListener) queryLocalInterface;
            }
            return new android.media.session.IOnMediaKeyListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onMediaKey";
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
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMediaKey(keyEvent, resultReceiver);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.session.IOnMediaKeyListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.session.IOnMediaKeyListener.Stub.DESCRIPTOR;
            }

            @Override // android.media.session.IOnMediaKeyListener
            public void onMediaKey(android.view.KeyEvent keyEvent, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.IOnMediaKeyListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
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
