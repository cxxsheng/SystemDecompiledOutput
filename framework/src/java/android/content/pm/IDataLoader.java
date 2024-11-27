package android.content.pm;

/* loaded from: classes.dex */
public interface IDataLoader extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.content.pm.IDataLoader";

    void create(int i, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, android.content.pm.FileSystemControlParcel fileSystemControlParcel, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener) throws android.os.RemoteException;

    void destroy(int i) throws android.os.RemoteException;

    void prepareImage(int i, android.content.pm.InstallationFileParcel[] installationFileParcelArr, java.lang.String[] strArr) throws android.os.RemoteException;

    void start(int i) throws android.os.RemoteException;

    void stop(int i) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IDataLoader {
        @Override // android.content.pm.IDataLoader
        public void create(int i, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, android.content.pm.FileSystemControlParcel fileSystemControlParcel, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IDataLoader
        public void start(int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IDataLoader
        public void stop(int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IDataLoader
        public void destroy(int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.IDataLoader
        public void prepareImage(int i, android.content.pm.InstallationFileParcel[] installationFileParcelArr, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IDataLoader {
        static final int TRANSACTION_create = 1;
        static final int TRANSACTION_destroy = 4;
        static final int TRANSACTION_prepareImage = 5;
        static final int TRANSACTION_start = 2;
        static final int TRANSACTION_stop = 3;

        public Stub() {
            attachInterface(this, android.content.pm.IDataLoader.DESCRIPTOR);
        }

        public static android.content.pm.IDataLoader asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.content.pm.IDataLoader.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IDataLoader)) {
                return (android.content.pm.IDataLoader) queryLocalInterface;
            }
            return new android.content.pm.IDataLoader.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "create";
                case 2:
                    return "start";
                case 3:
                    return "stop";
                case 4:
                    return "destroy";
                case 5:
                    return "prepareImage";
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
                parcel.enforceInterface(android.content.pm.IDataLoader.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.content.pm.IDataLoader.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel = (android.content.pm.DataLoaderParamsParcel) parcel.readTypedObject(android.content.pm.DataLoaderParamsParcel.CREATOR);
                    android.content.pm.FileSystemControlParcel fileSystemControlParcel = (android.content.pm.FileSystemControlParcel) parcel.readTypedObject(android.content.pm.FileSystemControlParcel.CREATOR);
                    android.content.pm.IDataLoaderStatusListener asInterface = android.content.pm.IDataLoaderStatusListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    create(readInt, dataLoaderParamsParcel, fileSystemControlParcel, asInterface);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    start(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stop(readInt3);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroy(readInt4);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    android.content.pm.InstallationFileParcel[] installationFileParcelArr = (android.content.pm.InstallationFileParcel[]) parcel.createTypedArray(android.content.pm.InstallationFileParcel.CREATOR);
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    prepareImage(readInt5, installationFileParcelArr, createStringArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IDataLoader {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IDataLoader.DESCRIPTOR;
            }

            @Override // android.content.pm.IDataLoader
            public void create(int i, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, android.content.pm.FileSystemControlParcel fileSystemControlParcel, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IDataLoader.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dataLoaderParamsParcel, 0);
                    obtain.writeTypedObject(fileSystemControlParcel, 0);
                    obtain.writeStrongInterface(iDataLoaderStatusListener);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IDataLoader
            public void start(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IDataLoader.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IDataLoader
            public void stop(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IDataLoader.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IDataLoader
            public void destroy(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IDataLoader.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IDataLoader
            public void prepareImage(int i, android.content.pm.InstallationFileParcel[] installationFileParcelArr, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.content.pm.IDataLoader.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(installationFileParcelArr, 0);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
