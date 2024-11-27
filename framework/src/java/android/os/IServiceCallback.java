package android.os;

/* loaded from: classes3.dex */
public interface IServiceCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.IServiceCallback";

    void onRegistration(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException;

    public static class Default implements android.os.IServiceCallback {
        @Override // android.os.IServiceCallback
        public void onRegistration(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IServiceCallback {
        static final int TRANSACTION_onRegistration = 1;

        public Stub() {
            attachInterface(this, android.os.IServiceCallback.DESCRIPTOR);
        }

        public static android.os.IServiceCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IServiceCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IServiceCallback)) {
                return (android.os.IServiceCallback) queryLocalInterface;
            }
            return new android.os.IServiceCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onRegistration";
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
                parcel.enforceInterface(android.os.IServiceCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.IServiceCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onRegistration(readString, readStrongBinder);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IServiceCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IServiceCallback.DESCRIPTOR;
            }

            @Override // android.os.IServiceCallback
            public void onRegistration(java.lang.String str, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.IServiceCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
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
