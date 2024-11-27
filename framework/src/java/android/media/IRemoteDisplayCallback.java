package android.media;

/* loaded from: classes2.dex */
public interface IRemoteDisplayCallback extends android.os.IInterface {
    void onStateChanged(android.media.RemoteDisplayState remoteDisplayState) throws android.os.RemoteException;

    public static class Default implements android.media.IRemoteDisplayCallback {
        @Override // android.media.IRemoteDisplayCallback
        public void onStateChanged(android.media.RemoteDisplayState remoteDisplayState) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IRemoteDisplayCallback {
        public static final java.lang.String DESCRIPTOR = "android.media.IRemoteDisplayCallback";
        static final int TRANSACTION_onStateChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.IRemoteDisplayCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IRemoteDisplayCallback)) {
                return (android.media.IRemoteDisplayCallback) queryLocalInterface;
            }
            return new android.media.IRemoteDisplayCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStateChanged";
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
                    android.media.RemoteDisplayState remoteDisplayState = (android.media.RemoteDisplayState) parcel.readTypedObject(android.media.RemoteDisplayState.CREATOR);
                    parcel.enforceNoDataAvail();
                    onStateChanged(remoteDisplayState);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IRemoteDisplayCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IRemoteDisplayCallback.Stub.DESCRIPTOR;
            }

            @Override // android.media.IRemoteDisplayCallback
            public void onStateChanged(android.media.RemoteDisplayState remoteDisplayState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IRemoteDisplayCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(remoteDisplayState, 0);
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
