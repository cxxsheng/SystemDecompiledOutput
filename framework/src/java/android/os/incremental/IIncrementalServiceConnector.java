package android.os.incremental;

/* loaded from: classes3.dex */
public interface IIncrementalServiceConnector extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.incremental.IIncrementalServiceConnector";

    int setStorageParams(boolean z) throws android.os.RemoteException;

    public static class Default implements android.os.incremental.IIncrementalServiceConnector {
        @Override // android.os.incremental.IIncrementalServiceConnector
        public int setStorageParams(boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.incremental.IIncrementalServiceConnector {
        static final int TRANSACTION_setStorageParams = 1;

        public Stub() {
            attachInterface(this, android.os.incremental.IIncrementalServiceConnector.DESCRIPTOR);
        }

        public static android.os.incremental.IIncrementalServiceConnector asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.incremental.IIncrementalServiceConnector.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.incremental.IIncrementalServiceConnector)) {
                return (android.os.incremental.IIncrementalServiceConnector) queryLocalInterface;
            }
            return new android.os.incremental.IIncrementalServiceConnector.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setStorageParams";
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
                parcel.enforceInterface(android.os.incremental.IIncrementalServiceConnector.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.incremental.IIncrementalServiceConnector.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int storageParams = setStorageParams(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(storageParams);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.incremental.IIncrementalServiceConnector {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.incremental.IIncrementalServiceConnector.DESCRIPTOR;
            }

            @Override // android.os.incremental.IIncrementalServiceConnector
            public int setStorageParams(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.incremental.IIncrementalServiceConnector.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
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
