package android.os;

/* loaded from: classes3.dex */
public interface IClientCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IClientCallback";

    void onClients(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException;

    public static class Default implements android.os.IClientCallback {
        @Override // android.os.IClientCallback
        public void onClients(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IClientCallback {
        static final int TRANSACTION_onClients = 1;

        public Stub() {
            attachInterface(this, android.os.IClientCallback.DESCRIPTOR);
        }

        public static android.os.IClientCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IClientCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IClientCallback)) {
                return (android.os.IClientCallback) queryLocalInterface;
            }
            return new android.os.IClientCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onClients";
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
                parcel.enforceInterface(android.os.IClientCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IClientCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onClients(readStrongBinder, readBoolean);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IClientCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IClientCallback.DESCRIPTOR;
            }

            @Override // android.os.IClientCallback
            public void onClients(android.os.IBinder iBinder, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IClientCallback.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
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
