package android.app.blob;

/* loaded from: classes.dex */
public interface IBlobStoreSession extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.blob.IBlobStoreSession";

    void abandon() throws android.os.RemoteException;

    void allowPackageAccess(java.lang.String str, byte[] bArr) throws android.os.RemoteException;

    void allowPublicAccess() throws android.os.RemoteException;

    void allowSameSignatureAccess() throws android.os.RemoteException;

    void close() throws android.os.RemoteException;

    void commit(android.app.blob.IBlobCommitCallback iBlobCommitCallback) throws android.os.RemoteException;

    long getSize() throws android.os.RemoteException;

    boolean isPackageAccessAllowed(java.lang.String str, byte[] bArr) throws android.os.RemoteException;

    boolean isPublicAccessAllowed() throws android.os.RemoteException;

    boolean isSameSignatureAccessAllowed() throws android.os.RemoteException;

    android.os.ParcelFileDescriptor openRead() throws android.os.RemoteException;

    android.os.ParcelFileDescriptor openWrite(long j, long j2) throws android.os.RemoteException;

    public static class Default implements android.app.blob.IBlobStoreSession {
        @Override // android.app.blob.IBlobStoreSession
        public android.os.ParcelFileDescriptor openWrite(long j, long j2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.blob.IBlobStoreSession
        public android.os.ParcelFileDescriptor openRead() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.blob.IBlobStoreSession
        public void allowPackageAccess(java.lang.String str, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.app.blob.IBlobStoreSession
        public void allowSameSignatureAccess() throws android.os.RemoteException {
        }

        @Override // android.app.blob.IBlobStoreSession
        public void allowPublicAccess() throws android.os.RemoteException {
        }

        @Override // android.app.blob.IBlobStoreSession
        public boolean isPackageAccessAllowed(java.lang.String str, byte[] bArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.blob.IBlobStoreSession
        public boolean isSameSignatureAccessAllowed() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.blob.IBlobStoreSession
        public boolean isPublicAccessAllowed() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.blob.IBlobStoreSession
        public long getSize() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.blob.IBlobStoreSession
        public void close() throws android.os.RemoteException {
        }

        @Override // android.app.blob.IBlobStoreSession
        public void abandon() throws android.os.RemoteException {
        }

        @Override // android.app.blob.IBlobStoreSession
        public void commit(android.app.blob.IBlobCommitCallback iBlobCommitCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.blob.IBlobStoreSession {
        static final int TRANSACTION_abandon = 11;
        static final int TRANSACTION_allowPackageAccess = 3;
        static final int TRANSACTION_allowPublicAccess = 5;
        static final int TRANSACTION_allowSameSignatureAccess = 4;
        static final int TRANSACTION_close = 10;
        static final int TRANSACTION_commit = 12;
        static final int TRANSACTION_getSize = 9;
        static final int TRANSACTION_isPackageAccessAllowed = 6;
        static final int TRANSACTION_isPublicAccessAllowed = 8;
        static final int TRANSACTION_isSameSignatureAccessAllowed = 7;
        static final int TRANSACTION_openRead = 2;
        static final int TRANSACTION_openWrite = 1;

        public Stub() {
            attachInterface(this, android.app.blob.IBlobStoreSession.DESCRIPTOR);
        }

        public static android.app.blob.IBlobStoreSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.blob.IBlobStoreSession.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.blob.IBlobStoreSession)) {
                return (android.app.blob.IBlobStoreSession) queryLocalInterface;
            }
            return new android.app.blob.IBlobStoreSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "openWrite";
                case 2:
                    return "openRead";
                case 3:
                    return "allowPackageAccess";
                case 4:
                    return "allowSameSignatureAccess";
                case 5:
                    return "allowPublicAccess";
                case 6:
                    return "isPackageAccessAllowed";
                case 7:
                    return "isSameSignatureAccessAllowed";
                case 8:
                    return "isPublicAccessAllowed";
                case 9:
                    return "getSize";
                case 10:
                    return "close";
                case 11:
                    return "abandon";
                case 12:
                    return "commit";
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
                parcel.enforceInterface(android.app.blob.IBlobStoreSession.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.blob.IBlobStoreSession.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor openWrite = openWrite(readLong, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openWrite, 1);
                    return true;
                case 2:
                    android.os.ParcelFileDescriptor openRead = openRead();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openRead, 1);
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    allowPackageAccess(readString, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    allowSameSignatureAccess();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    allowPublicAccess();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    java.lang.String readString2 = parcel.readString();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean isPackageAccessAllowed = isPackageAccessAllowed(readString2, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageAccessAllowed);
                    return true;
                case 7:
                    boolean isSameSignatureAccessAllowed = isSameSignatureAccessAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSameSignatureAccessAllowed);
                    return true;
                case 8:
                    boolean isPublicAccessAllowed = isPublicAccessAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPublicAccessAllowed);
                    return true;
                case 9:
                    long size = getSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(size);
                    return true;
                case 10:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    abandon();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.app.blob.IBlobCommitCallback asInterface = android.app.blob.IBlobCommitCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    commit(asInterface);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.blob.IBlobStoreSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.blob.IBlobStoreSession.DESCRIPTOR;
            }

            @Override // android.app.blob.IBlobStoreSession
            public android.os.ParcelFileDescriptor openWrite(long j, long j2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreSession.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public android.os.ParcelFileDescriptor openRead() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public void allowPackageAccess(java.lang.String str, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public void allowSameSignatureAccess() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public void allowPublicAccess() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public boolean isPackageAccessAllowed(java.lang.String str, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreSession.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public boolean isSameSignatureAccessAllowed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public boolean isPublicAccessAllowed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public long getSize() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public void abandon() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreSession.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreSession
            public void commit(android.app.blob.IBlobCommitCallback iBlobCommitCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreSession.DESCRIPTOR);
                    obtain.writeStrongInterface(iBlobCommitCallback);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
