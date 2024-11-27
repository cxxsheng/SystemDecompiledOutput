package android.content.pm;

/* loaded from: classes.dex */
public interface IPackageInstallerSessionFileSystemConnector extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.content.pm.IPackageInstallerSessionFileSystemConnector";

    void writeData(java.lang.String str, long j, long j2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    public static class Default implements android.content.pm.IPackageInstallerSessionFileSystemConnector {
        @Override // android.content.pm.IPackageInstallerSessionFileSystemConnector
        public void writeData(java.lang.String str, long j, long j2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.IPackageInstallerSessionFileSystemConnector {
        static final int TRANSACTION_writeData = 1;

        public Stub() {
            attachInterface(this, android.content.pm.IPackageInstallerSessionFileSystemConnector.DESCRIPTOR);
        }

        public static android.content.pm.IPackageInstallerSessionFileSystemConnector asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.content.pm.IPackageInstallerSessionFileSystemConnector.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.IPackageInstallerSessionFileSystemConnector)) {
                return (android.content.pm.IPackageInstallerSessionFileSystemConnector) queryLocalInterface;
            }
            return new android.content.pm.IPackageInstallerSessionFileSystemConnector.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "writeData";
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
                parcel.enforceInterface(android.content.pm.IPackageInstallerSessionFileSystemConnector.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.content.pm.IPackageInstallerSessionFileSystemConnector.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    writeData(readString, readLong, readLong2, parcelFileDescriptor);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.content.pm.IPackageInstallerSessionFileSystemConnector {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.IPackageInstallerSessionFileSystemConnector.DESCRIPTOR;
            }

            @Override // android.content.pm.IPackageInstallerSessionFileSystemConnector
            public void writeData(java.lang.String str, long j, long j2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.IPackageInstallerSessionFileSystemConnector.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
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
