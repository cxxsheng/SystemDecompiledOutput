package android.service.storage;

/* loaded from: classes3.dex */
public interface IExternalStorageService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.service.storage.IExternalStorageService";

    void endSession(java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void freeCache(java.lang.String str, java.lang.String str2, long j, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void notifyAnrDelayStarted(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    void notifyVolumeStateChanged(java.lang.String str, android.os.storage.StorageVolume storageVolume, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    void startSession(java.lang.String str, int i, android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String str2, java.lang.String str3, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException;

    public static class Default implements android.service.storage.IExternalStorageService {
        @Override // android.service.storage.IExternalStorageService
        public void startSession(java.lang.String str, int i, android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String str2, java.lang.String str3, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.storage.IExternalStorageService
        public void endSession(java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.storage.IExternalStorageService
        public void notifyVolumeStateChanged(java.lang.String str, android.os.storage.StorageVolume storageVolume, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.storage.IExternalStorageService
        public void freeCache(java.lang.String str, java.lang.String str2, long j, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
        }

        @Override // android.service.storage.IExternalStorageService
        public void notifyAnrDelayStarted(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.storage.IExternalStorageService {
        static final int TRANSACTION_endSession = 2;
        static final int TRANSACTION_freeCache = 4;
        static final int TRANSACTION_notifyAnrDelayStarted = 5;
        static final int TRANSACTION_notifyVolumeStateChanged = 3;
        static final int TRANSACTION_startSession = 1;

        public Stub() {
            attachInterface(this, android.service.storage.IExternalStorageService.DESCRIPTOR);
        }

        public static android.service.storage.IExternalStorageService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.service.storage.IExternalStorageService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.storage.IExternalStorageService)) {
                return (android.service.storage.IExternalStorageService) queryLocalInterface;
            }
            return new android.service.storage.IExternalStorageService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startSession";
                case 2:
                    return "endSession";
                case 3:
                    return "notifyVolumeStateChanged";
                case 4:
                    return "freeCache";
                case 5:
                    return "notifyAnrDelayStarted";
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
                parcel.enforceInterface(android.service.storage.IExternalStorageService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.service.storage.IExternalStorageService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    startSession(readString, readInt, parcelFileDescriptor, readString2, readString3, remoteCallback);
                    return true;
                case 2:
                    java.lang.String readString4 = parcel.readString();
                    android.os.RemoteCallback remoteCallback2 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    endSession(readString4, remoteCallback2);
                    return true;
                case 3:
                    java.lang.String readString5 = parcel.readString();
                    android.os.storage.StorageVolume storageVolume = (android.os.storage.StorageVolume) parcel.readTypedObject(android.os.storage.StorageVolume.CREATOR);
                    android.os.RemoteCallback remoteCallback3 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyVolumeStateChanged(readString5, storageVolume, remoteCallback3);
                    return true;
                case 4:
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    long readLong = parcel.readLong();
                    android.os.RemoteCallback remoteCallback4 = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    parcel.enforceNoDataAvail();
                    freeCache(readString6, readString7, readLong, remoteCallback4);
                    return true;
                case 5:
                    java.lang.String readString8 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAnrDelayStarted(readString8, readInt2, readInt3, readInt4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.storage.IExternalStorageService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.storage.IExternalStorageService.DESCRIPTOR;
            }

            @Override // android.service.storage.IExternalStorageService
            public void startSession(java.lang.String str, int i, android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String str2, java.lang.String str3, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.storage.IExternalStorageService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.storage.IExternalStorageService
            public void endSession(java.lang.String str, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.storage.IExternalStorageService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.storage.IExternalStorageService
            public void notifyVolumeStateChanged(java.lang.String str, android.os.storage.StorageVolume storageVolume, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.storage.IExternalStorageService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(storageVolume, 0);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.storage.IExternalStorageService
            public void freeCache(java.lang.String str, java.lang.String str2, long j, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.storage.IExternalStorageService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.storage.IExternalStorageService
            public void notifyAnrDelayStarted(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.storage.IExternalStorageService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
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
