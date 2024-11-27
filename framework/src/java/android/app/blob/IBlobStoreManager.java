package android.app.blob;

/* loaded from: classes.dex */
public interface IBlobStoreManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.blob.IBlobStoreManager";

    void abandonSession(long j, java.lang.String str) throws android.os.RemoteException;

    void acquireLease(android.app.blob.BlobHandle blobHandle, int i, java.lang.CharSequence charSequence, long j, java.lang.String str) throws android.os.RemoteException;

    long createSession(android.app.blob.BlobHandle blobHandle, java.lang.String str) throws android.os.RemoteException;

    void deleteBlob(long j) throws android.os.RemoteException;

    android.app.blob.LeaseInfo getLeaseInfo(android.app.blob.BlobHandle blobHandle, java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.app.blob.BlobHandle> getLeasedBlobs(java.lang.String str) throws android.os.RemoteException;

    long getRemainingLeaseQuotaBytes(java.lang.String str) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor openBlob(android.app.blob.BlobHandle blobHandle, java.lang.String str) throws android.os.RemoteException;

    android.app.blob.IBlobStoreSession openSession(long j, java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.app.blob.BlobInfo> queryBlobsForUser(int i) throws android.os.RemoteException;

    void releaseAllLeases(java.lang.String str) throws android.os.RemoteException;

    void releaseLease(android.app.blob.BlobHandle blobHandle, java.lang.String str) throws android.os.RemoteException;

    void waitForIdle(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    public static class Default implements android.app.blob.IBlobStoreManager {
        @Override // android.app.blob.IBlobStoreManager
        public long createSession(android.app.blob.BlobHandle blobHandle, java.lang.String str) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.blob.IBlobStoreManager
        public android.app.blob.IBlobStoreSession openSession(long j, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.blob.IBlobStoreManager
        public android.os.ParcelFileDescriptor openBlob(android.app.blob.BlobHandle blobHandle, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.blob.IBlobStoreManager
        public void abandonSession(long j, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.blob.IBlobStoreManager
        public void acquireLease(android.app.blob.BlobHandle blobHandle, int i, java.lang.CharSequence charSequence, long j, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.blob.IBlobStoreManager
        public void releaseLease(android.app.blob.BlobHandle blobHandle, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.blob.IBlobStoreManager
        public void releaseAllLeases(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.blob.IBlobStoreManager
        public long getRemainingLeaseQuotaBytes(java.lang.String str) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.blob.IBlobStoreManager
        public void waitForIdle(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.app.blob.IBlobStoreManager
        public java.util.List<android.app.blob.BlobInfo> queryBlobsForUser(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.blob.IBlobStoreManager
        public void deleteBlob(long j) throws android.os.RemoteException {
        }

        @Override // android.app.blob.IBlobStoreManager
        public java.util.List<android.app.blob.BlobHandle> getLeasedBlobs(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.blob.IBlobStoreManager
        public android.app.blob.LeaseInfo getLeaseInfo(android.app.blob.BlobHandle blobHandle, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.blob.IBlobStoreManager {
        static final int TRANSACTION_abandonSession = 4;
        static final int TRANSACTION_acquireLease = 5;
        static final int TRANSACTION_createSession = 1;
        static final int TRANSACTION_deleteBlob = 11;
        static final int TRANSACTION_getLeaseInfo = 13;
        static final int TRANSACTION_getLeasedBlobs = 12;
        static final int TRANSACTION_getRemainingLeaseQuotaBytes = 8;
        static final int TRANSACTION_openBlob = 3;
        static final int TRANSACTION_openSession = 2;
        static final int TRANSACTION_queryBlobsForUser = 10;
        static final int TRANSACTION_releaseAllLeases = 7;
        static final int TRANSACTION_releaseLease = 6;
        static final int TRANSACTION_waitForIdle = 9;

        public Stub() {
            attachInterface(this, android.app.blob.IBlobStoreManager.DESCRIPTOR);
        }

        public static android.app.blob.IBlobStoreManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.blob.IBlobStoreManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.blob.IBlobStoreManager)) {
                return (android.app.blob.IBlobStoreManager) queryLocalInterface;
            }
            return new android.app.blob.IBlobStoreManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createSession";
                case 2:
                    return "openSession";
                case 3:
                    return "openBlob";
                case 4:
                    return "abandonSession";
                case 5:
                    return "acquireLease";
                case 6:
                    return "releaseLease";
                case 7:
                    return "releaseAllLeases";
                case 8:
                    return "getRemainingLeaseQuotaBytes";
                case 9:
                    return "waitForIdle";
                case 10:
                    return "queryBlobsForUser";
                case 11:
                    return "deleteBlob";
                case 12:
                    return "getLeasedBlobs";
                case 13:
                    return "getLeaseInfo";
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
                parcel.enforceInterface(android.app.blob.IBlobStoreManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.blob.IBlobStoreManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.blob.BlobHandle blobHandle = (android.app.blob.BlobHandle) parcel.readTypedObject(android.app.blob.BlobHandle.CREATOR);
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long createSession = createSession(blobHandle, readString);
                    parcel2.writeNoException();
                    parcel2.writeLong(createSession);
                    return true;
                case 2:
                    long readLong = parcel.readLong();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.blob.IBlobStoreSession openSession = openSession(readLong, readString2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openSession);
                    return true;
                case 3:
                    android.app.blob.BlobHandle blobHandle2 = (android.app.blob.BlobHandle) parcel.readTypedObject(android.app.blob.BlobHandle.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor openBlob = openBlob(blobHandle2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openBlob, 1);
                    return true;
                case 4:
                    long readLong2 = parcel.readLong();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    abandonSession(readLong2, readString4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.app.blob.BlobHandle blobHandle3 = (android.app.blob.BlobHandle) parcel.readTypedObject(android.app.blob.BlobHandle.CREATOR);
                    int readInt = parcel.readInt();
                    java.lang.CharSequence charSequence = (java.lang.CharSequence) parcel.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                    long readLong3 = parcel.readLong();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    acquireLease(blobHandle3, readInt, charSequence, readLong3, readString5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.app.blob.BlobHandle blobHandle4 = (android.app.blob.BlobHandle) parcel.readTypedObject(android.app.blob.BlobHandle.CREATOR);
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseLease(blobHandle4, readString6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseAllLeases(readString7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long remainingLeaseQuotaBytes = getRemainingLeaseQuotaBytes(readString8);
                    parcel2.writeNoException();
                    parcel2.writeLong(remainingLeaseQuotaBytes);
                    return true;
                case 9:
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    waitForIdle(remoteCallback);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.app.blob.BlobInfo> queryBlobsForUser = queryBlobsForUser(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(queryBlobsForUser, 1);
                    return true;
                case 11:
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    deleteBlob(readLong4);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.app.blob.BlobHandle> leasedBlobs = getLeasedBlobs(readString9);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(leasedBlobs, 1);
                    return true;
                case 13:
                    android.app.blob.BlobHandle blobHandle5 = (android.app.blob.BlobHandle) parcel.readTypedObject(android.app.blob.BlobHandle.CREATOR);
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.blob.LeaseInfo leaseInfo = getLeaseInfo(blobHandle5, readString10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(leaseInfo, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.blob.IBlobStoreManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.blob.IBlobStoreManager.DESCRIPTOR;
            }

            @Override // android.app.blob.IBlobStoreManager
            public long createSession(android.app.blob.BlobHandle blobHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreManager.DESCRIPTOR);
                    obtain.writeTypedObject(blobHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public android.app.blob.IBlobStoreSession openSession(long j, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.app.blob.IBlobStoreSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public android.os.ParcelFileDescriptor openBlob(android.app.blob.BlobHandle blobHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreManager.DESCRIPTOR);
                    obtain.writeTypedObject(blobHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void abandonSession(long j, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void acquireLease(android.app.blob.BlobHandle blobHandle, int i, java.lang.CharSequence charSequence, long j, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreManager.DESCRIPTOR);
                    obtain.writeTypedObject(blobHandle, 0);
                    obtain.writeInt(i);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        android.text.TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void releaseLease(android.app.blob.BlobHandle blobHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreManager.DESCRIPTOR);
                    obtain.writeTypedObject(blobHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void releaseAllLeases(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public long getRemainingLeaseQuotaBytes(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void waitForIdle(android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreManager.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public java.util.List<android.app.blob.BlobInfo> queryBlobsForUser(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.blob.BlobInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public void deleteBlob(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public java.util.List<android.app.blob.BlobHandle> getLeasedBlobs(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.app.blob.BlobHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.blob.IBlobStoreManager
            public android.app.blob.LeaseInfo getLeaseInfo(android.app.blob.BlobHandle blobHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.blob.IBlobStoreManager.DESCRIPTOR);
                    obtain.writeTypedObject(blobHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.blob.LeaseInfo) obtain2.readTypedObject(android.app.blob.LeaseInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
