package android.os.storage;

/* loaded from: classes3.dex */
public interface IStorageEventListener extends android.os.IInterface {
    void onDiskDestroyed(android.os.storage.DiskInfo diskInfo) throws android.os.RemoteException;

    void onDiskScanned(android.os.storage.DiskInfo diskInfo, int i) throws android.os.RemoteException;

    void onStorageStateChanged(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void onUsbMassStorageConnectionChanged(boolean z) throws android.os.RemoteException;

    void onVolumeForgotten(java.lang.String str) throws android.os.RemoteException;

    void onVolumeRecordChanged(android.os.storage.VolumeRecord volumeRecord) throws android.os.RemoteException;

    void onVolumeStateChanged(android.os.storage.VolumeInfo volumeInfo, int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.os.storage.IStorageEventListener {
        @Override // android.os.storage.IStorageEventListener
        public void onUsbMassStorageConnectionChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageEventListener
        public void onStorageStateChanged(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageEventListener
        public void onVolumeStateChanged(android.os.storage.VolumeInfo volumeInfo, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageEventListener
        public void onVolumeRecordChanged(android.os.storage.VolumeRecord volumeRecord) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageEventListener
        public void onVolumeForgotten(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageEventListener
        public void onDiskScanned(android.os.storage.DiskInfo diskInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageEventListener
        public void onDiskDestroyed(android.os.storage.DiskInfo diskInfo) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.storage.IStorageEventListener {
        public static final java.lang.String DESCRIPTOR = "android.os.storage.IStorageEventListener";
        static final int TRANSACTION_onDiskDestroyed = 7;
        static final int TRANSACTION_onDiskScanned = 6;
        static final int TRANSACTION_onStorageStateChanged = 2;
        static final int TRANSACTION_onUsbMassStorageConnectionChanged = 1;
        static final int TRANSACTION_onVolumeForgotten = 5;
        static final int TRANSACTION_onVolumeRecordChanged = 4;
        static final int TRANSACTION_onVolumeStateChanged = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.os.storage.IStorageEventListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.storage.IStorageEventListener)) {
                return (android.os.storage.IStorageEventListener) queryLocalInterface;
            }
            return new android.os.storage.IStorageEventListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUsbMassStorageConnectionChanged";
                case 2:
                    return "onStorageStateChanged";
                case 3:
                    return "onVolumeStateChanged";
                case 4:
                    return "onVolumeRecordChanged";
                case 5:
                    return "onVolumeForgotten";
                case 6:
                    return "onDiskScanned";
                case 7:
                    return "onDiskDestroyed";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onUsbMassStorageConnectionChanged(readBoolean);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onStorageStateChanged(readString, readString2, readString3);
                    return true;
                case 3:
                    android.os.storage.VolumeInfo volumeInfo = (android.os.storage.VolumeInfo) parcel.readTypedObject(android.os.storage.VolumeInfo.CREATOR);
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVolumeStateChanged(volumeInfo, readInt, readInt2);
                    return true;
                case 4:
                    android.os.storage.VolumeRecord volumeRecord = (android.os.storage.VolumeRecord) parcel.readTypedObject(android.os.storage.VolumeRecord.CREATOR);
                    parcel.enforceNoDataAvail();
                    onVolumeRecordChanged(volumeRecord);
                    return true;
                case 5:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onVolumeForgotten(readString4);
                    return true;
                case 6:
                    android.os.storage.DiskInfo diskInfo = (android.os.storage.DiskInfo) parcel.readTypedObject(android.os.storage.DiskInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDiskScanned(diskInfo, readInt3);
                    return true;
                case 7:
                    android.os.storage.DiskInfo diskInfo2 = (android.os.storage.DiskInfo) parcel.readTypedObject(android.os.storage.DiskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDiskDestroyed(diskInfo2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.storage.IStorageEventListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.storage.IStorageEventListener.Stub.DESCRIPTOR;
            }

            @Override // android.os.storage.IStorageEventListener
            public void onUsbMassStorageConnectionChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageEventListener.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageEventListener
            public void onStorageStateChanged(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageEventListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageEventListener
            public void onVolumeStateChanged(android.os.storage.VolumeInfo volumeInfo, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageEventListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(volumeInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageEventListener
            public void onVolumeRecordChanged(android.os.storage.VolumeRecord volumeRecord) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageEventListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(volumeRecord, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageEventListener
            public void onVolumeForgotten(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageEventListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageEventListener
            public void onDiskScanned(android.os.storage.DiskInfo diskInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageEventListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(diskInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageEventListener
            public void onDiskDestroyed(android.os.storage.DiskInfo diskInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageEventListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(diskInfo, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
