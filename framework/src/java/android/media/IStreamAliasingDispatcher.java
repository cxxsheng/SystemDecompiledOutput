package android.media;

/* loaded from: classes2.dex */
public interface IStreamAliasingDispatcher extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.IStreamAliasingDispatcher";

    void dispatchStreamAliasingChanged() throws android.os.RemoteException;

    public static class Default implements android.media.IStreamAliasingDispatcher {
        @Override // android.media.IStreamAliasingDispatcher
        public void dispatchStreamAliasingChanged() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IStreamAliasingDispatcher {
        static final int TRANSACTION_dispatchStreamAliasingChanged = 1;

        public Stub() {
            attachInterface(this, android.media.IStreamAliasingDispatcher.DESCRIPTOR);
        }

        public static android.media.IStreamAliasingDispatcher asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.IStreamAliasingDispatcher.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IStreamAliasingDispatcher)) {
                return (android.media.IStreamAliasingDispatcher) queryLocalInterface;
            }
            return new android.media.IStreamAliasingDispatcher.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dispatchStreamAliasingChanged";
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
                parcel.enforceInterface(android.media.IStreamAliasingDispatcher.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.IStreamAliasingDispatcher.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    dispatchStreamAliasingChanged();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IStreamAliasingDispatcher {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IStreamAliasingDispatcher.DESCRIPTOR;
            }

            @Override // android.media.IStreamAliasingDispatcher
            public void dispatchStreamAliasingChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.IStreamAliasingDispatcher.DESCRIPTOR);
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
