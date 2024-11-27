package android.content.pm;

/* loaded from: classes.dex */
public interface IDataLoaderStatusListener extends android.os.IInterface {
    public static final int DATA_LOADER_BINDING = 1;
    public static final int DATA_LOADER_BOUND = 2;
    public static final int DATA_LOADER_CREATED = 3;
    public static final int DATA_LOADER_DESTROYED = 0;
    public static final int DATA_LOADER_IMAGE_NOT_READY = 7;
    public static final int DATA_LOADER_IMAGE_READY = 6;
    public static final int DATA_LOADER_STARTED = 4;
    public static final int DATA_LOADER_STOPPED = 5;
    public static final int DATA_LOADER_UNAVAILABLE = 8;
    public static final int DATA_LOADER_UNRECOVERABLE = 9;
    public static final java.lang.String DESCRIPTOR = "android.content.pm.IDataLoaderStatusListener";

    void onStatusChanged(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IDataLoaderStatusListener {
        @Override // android.content.pm.IDataLoaderStatusListener
        public void onStatusChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IDataLoaderStatusListener {
        static final int TRANSACTION_onStatusChanged = 1;

        public Stub() {
            attachInterface(this, android.content.pm.IDataLoaderStatusListener.DESCRIPTOR);
        }

        public static android.content.pm.IDataLoaderStatusListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.content.pm.IDataLoaderStatusListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IDataLoaderStatusListener)) {
                return (android.content.pm.IDataLoaderStatusListener) queryLocalInterface;
            }
            return new android.content.pm.IDataLoaderStatusListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onStatusChanged";
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
                parcel.enforceInterface(android.content.pm.IDataLoaderStatusListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.content.pm.IDataLoaderStatusListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStatusChanged(readInt, readInt2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IDataLoaderStatusListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IDataLoaderStatusListener.DESCRIPTOR;
            }

            @Override // android.content.pm.IDataLoaderStatusListener
            public void onStatusChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IDataLoaderStatusListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
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
