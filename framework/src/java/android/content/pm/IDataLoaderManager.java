package android.content.pm;

/* loaded from: classes.dex */
public interface IDataLoaderManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.content.pm.IDataLoaderManager";

    boolean bindToDataLoader(int i, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, long j, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener) throws android.os.RemoteException;

    android.content.pm.IDataLoader getDataLoader(int i) throws android.os.RemoteException;

    void unbindFromDataLoader(int i) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IDataLoaderManager {
        @Override // android.content.pm.IDataLoaderManager
        public boolean bindToDataLoader(int i, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, long j, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.IDataLoaderManager
        public android.content.pm.IDataLoader getDataLoader(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.IDataLoaderManager
        public void unbindFromDataLoader(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IDataLoaderManager {
        static final int TRANSACTION_bindToDataLoader = 1;
        static final int TRANSACTION_getDataLoader = 2;
        static final int TRANSACTION_unbindFromDataLoader = 3;

        public Stub() {
            attachInterface(this, android.content.pm.IDataLoaderManager.DESCRIPTOR);
        }

        public static android.content.pm.IDataLoaderManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.content.pm.IDataLoaderManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IDataLoaderManager)) {
                return (android.content.pm.IDataLoaderManager) queryLocalInterface;
            }
            return new android.content.pm.IDataLoaderManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "bindToDataLoader";
                case 2:
                    return "getDataLoader";
                case 3:
                    return "unbindFromDataLoader";
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
                parcel.enforceInterface(android.content.pm.IDataLoaderManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.content.pm.IDataLoaderManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel = (android.content.pm.DataLoaderParamsParcel) parcel.readTypedObject(android.content.pm.DataLoaderParamsParcel.CREATOR);
                    long readLong = parcel.readLong();
                    android.content.pm.IDataLoaderStatusListener asInterface = android.content.pm.IDataLoaderStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean bindToDataLoader = bindToDataLoader(readInt, dataLoaderParamsParcel, readLong, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bindToDataLoader);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.IDataLoader dataLoader = getDataLoader(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(dataLoader);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unbindFromDataLoader(readInt3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IDataLoaderManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IDataLoaderManager.DESCRIPTOR;
            }

            @Override // android.content.pm.IDataLoaderManager
            public boolean bindToDataLoader(int i, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, long j, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IDataLoaderManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dataLoaderParamsParcel, 0);
                    obtain.writeLong(j);
                    obtain.writeStrongInterface(iDataLoaderStatusListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IDataLoaderManager
            public android.content.pm.IDataLoader getDataLoader(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IDataLoaderManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.content.pm.IDataLoader.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IDataLoaderManager
            public void unbindFromDataLoader(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IDataLoaderManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
