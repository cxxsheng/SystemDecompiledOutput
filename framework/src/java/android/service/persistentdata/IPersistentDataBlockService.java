package android.service.persistentdata;

/* loaded from: classes3.dex */
public interface IPersistentDataBlockService extends android.os.IInterface {
    boolean deactivateFactoryResetProtection(byte[] bArr) throws android.os.RemoteException;

    int getDataBlockSize() throws android.os.RemoteException;

    int getFlashLockState() throws android.os.RemoteException;

    long getMaximumDataBlockSize() throws android.os.RemoteException;

    boolean getOemUnlockEnabled() throws android.os.RemoteException;

    java.lang.String getPersistentDataPackageName() throws android.os.RemoteException;

    boolean hasFrpCredentialHandle() throws android.os.RemoteException;

    boolean isFactoryResetProtectionActive() throws android.os.RemoteException;

    byte[] read() throws android.os.RemoteException;

    boolean setFactoryResetProtectionSecret(byte[] bArr) throws android.os.RemoteException;

    void setOemUnlockEnabled(boolean z) throws android.os.RemoteException;

    void wipe() throws android.os.RemoteException;

    int write(byte[] bArr) throws android.os.RemoteException;

    public static class Default implements android.service.persistentdata.IPersistentDataBlockService {
        @Override // android.service.persistentdata.IPersistentDataBlockService
        public int write(byte[] bArr) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public byte[] read() throws android.os.RemoteException {
            return null;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public void wipe() throws android.os.RemoteException {
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public int getDataBlockSize() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public long getMaximumDataBlockSize() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public void setOemUnlockEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public boolean getOemUnlockEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public int getFlashLockState() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public boolean hasFrpCredentialHandle() throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public java.lang.String getPersistentDataPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public boolean isFactoryResetProtectionActive() throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public boolean deactivateFactoryResetProtection(byte[] bArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.persistentdata.IPersistentDataBlockService
        public boolean setFactoryResetProtectionSecret(byte[] bArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.persistentdata.IPersistentDataBlockService {
        public static final java.lang.String DESCRIPTOR = "android.service.persistentdata.IPersistentDataBlockService";
        static final int TRANSACTION_deactivateFactoryResetProtection = 12;
        static final int TRANSACTION_getDataBlockSize = 4;
        static final int TRANSACTION_getFlashLockState = 8;
        static final int TRANSACTION_getMaximumDataBlockSize = 5;
        static final int TRANSACTION_getOemUnlockEnabled = 7;
        static final int TRANSACTION_getPersistentDataPackageName = 10;
        static final int TRANSACTION_hasFrpCredentialHandle = 9;
        static final int TRANSACTION_isFactoryResetProtectionActive = 11;
        static final int TRANSACTION_read = 2;
        static final int TRANSACTION_setFactoryResetProtectionSecret = 13;
        static final int TRANSACTION_setOemUnlockEnabled = 6;
        static final int TRANSACTION_wipe = 3;
        static final int TRANSACTION_write = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.persistentdata.IPersistentDataBlockService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.persistentdata.IPersistentDataBlockService)) {
                return (android.service.persistentdata.IPersistentDataBlockService) queryLocalInterface;
            }
            return new android.service.persistentdata.IPersistentDataBlockService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "write";
                case 2:
                    return "read";
                case 3:
                    return "wipe";
                case 4:
                    return "getDataBlockSize";
                case 5:
                    return "getMaximumDataBlockSize";
                case 6:
                    return "setOemUnlockEnabled";
                case 7:
                    return "getOemUnlockEnabled";
                case 8:
                    return "getFlashLockState";
                case 9:
                    return "hasFrpCredentialHandle";
                case 10:
                    return "getPersistentDataPackageName";
                case 11:
                    return "isFactoryResetProtectionActive";
                case 12:
                    return "deactivateFactoryResetProtection";
                case 13:
                    return "setFactoryResetProtectionSecret";
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
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    int write = write(createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(write);
                    return true;
                case 2:
                    byte[] read = read();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(read);
                    return true;
                case 3:
                    wipe();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int dataBlockSize = getDataBlockSize();
                    parcel2.writeNoException();
                    parcel2.writeInt(dataBlockSize);
                    return true;
                case 5:
                    long maximumDataBlockSize = getMaximumDataBlockSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(maximumDataBlockSize);
                    return true;
                case 6:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOemUnlockEnabled(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean oemUnlockEnabled = getOemUnlockEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(oemUnlockEnabled);
                    return true;
                case 8:
                    int flashLockState = getFlashLockState();
                    parcel2.writeNoException();
                    parcel2.writeInt(flashLockState);
                    return true;
                case 9:
                    boolean hasFrpCredentialHandle = hasFrpCredentialHandle();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasFrpCredentialHandle);
                    return true;
                case 10:
                    java.lang.String persistentDataPackageName = getPersistentDataPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(persistentDataPackageName);
                    return true;
                case 11:
                    boolean isFactoryResetProtectionActive = isFactoryResetProtectionActive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFactoryResetProtectionActive);
                    return true;
                case 12:
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean deactivateFactoryResetProtection = deactivateFactoryResetProtection(createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deactivateFactoryResetProtection);
                    return true;
                case 13:
                    byte[] createByteArray3 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean factoryResetProtectionSecret = setFactoryResetProtectionSecret(createByteArray3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(factoryResetProtectionSecret);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.persistentdata.IPersistentDataBlockService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.persistentdata.IPersistentDataBlockService.Stub.DESCRIPTOR;
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public int write(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.persistentdata.IPersistentDataBlockService.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public byte[] read() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.persistentdata.IPersistentDataBlockService.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public void wipe() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.persistentdata.IPersistentDataBlockService.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public int getDataBlockSize() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.persistentdata.IPersistentDataBlockService.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public long getMaximumDataBlockSize() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.persistentdata.IPersistentDataBlockService.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public void setOemUnlockEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.persistentdata.IPersistentDataBlockService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public boolean getOemUnlockEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.persistentdata.IPersistentDataBlockService.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public int getFlashLockState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.persistentdata.IPersistentDataBlockService.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public boolean hasFrpCredentialHandle() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.persistentdata.IPersistentDataBlockService.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public java.lang.String getPersistentDataPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.persistentdata.IPersistentDataBlockService.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public boolean isFactoryResetProtectionActive() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.persistentdata.IPersistentDataBlockService.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public boolean deactivateFactoryResetProtection(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.persistentdata.IPersistentDataBlockService.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.persistentdata.IPersistentDataBlockService
            public boolean setFactoryResetProtectionSecret(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.persistentdata.IPersistentDataBlockService.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
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
