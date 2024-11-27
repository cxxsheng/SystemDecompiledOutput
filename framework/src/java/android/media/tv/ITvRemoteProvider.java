package android.media.tv;

/* loaded from: classes2.dex */
public interface ITvRemoteProvider extends android.os.IInterface {
    void onInputBridgeConnected(android.os.IBinder iBinder) throws android.os.RemoteException;

    void setRemoteServiceInputSink(android.media.tv.ITvRemoteServiceInput iTvRemoteServiceInput) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ITvRemoteProvider {
        @Override // android.media.tv.ITvRemoteProvider
        public void setRemoteServiceInputSink(android.media.tv.ITvRemoteServiceInput iTvRemoteServiceInput) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvRemoteProvider
        public void onInputBridgeConnected(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ITvRemoteProvider {
        public static final java.lang.String DESCRIPTOR = "android.media.tv.ITvRemoteProvider";
        static final int TRANSACTION_onInputBridgeConnected = 2;
        static final int TRANSACTION_setRemoteServiceInputSink = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.tv.ITvRemoteProvider asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ITvRemoteProvider)) {
                return (android.media.tv.ITvRemoteProvider) queryLocalInterface;
            }
            return new android.media.tv.ITvRemoteProvider.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setRemoteServiceInputSink";
                case 2:
                    return "onInputBridgeConnected";
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
                    android.media.tv.ITvRemoteServiceInput asInterface = android.media.tv.ITvRemoteServiceInput.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setRemoteServiceInputSink(asInterface);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onInputBridgeConnected(readStrongBinder);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ITvRemoteProvider {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ITvRemoteProvider.Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvRemoteProvider
            public void setRemoteServiceInputSink(android.media.tv.ITvRemoteServiceInput iTvRemoteServiceInput) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteProvider.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvRemoteServiceInput);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteProvider
            public void onInputBridgeConnected(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvRemoteProvider.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
