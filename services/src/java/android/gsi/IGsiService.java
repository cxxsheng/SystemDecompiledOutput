package android.gsi;

/* loaded from: classes.dex */
public interface IGsiService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.gsi.IGsiService";
    public static final int INSTALL_ERROR_FILE_SYSTEM_CLUTTERED = 3;
    public static final int INSTALL_ERROR_GENERIC = 1;
    public static final int INSTALL_ERROR_NO_SPACE = 2;
    public static final int INSTALL_OK = 0;
    public static final int STATUS_COMPLETE = 2;
    public static final int STATUS_NO_OPERATION = 0;
    public static final int STATUS_WORKING = 1;

    boolean cancelGsiInstall() throws android.os.RemoteException;

    int closeInstall() throws android.os.RemoteException;

    int closePartition() throws android.os.RemoteException;

    boolean commitGsiChunkFromAshmem(long j) throws android.os.RemoteException;

    boolean commitGsiChunkFromStream(android.os.ParcelFileDescriptor parcelFileDescriptor, long j) throws android.os.RemoteException;

    int createPartition(java.lang.String str, long j, boolean z) throws android.os.RemoteException;

    boolean disableGsi() throws android.os.RemoteException;

    java.lang.String dumpDeviceMapperDevices() throws android.os.RemoteException;

    int enableGsi(boolean z, java.lang.String str) throws android.os.RemoteException;

    void enableGsiAsync(boolean z, java.lang.String str, android.gsi.IGsiServiceCallback iGsiServiceCallback) throws android.os.RemoteException;

    java.lang.String getActiveDsuSlot() throws android.os.RemoteException;

    int getAvbPublicKey(android.gsi.AvbPublicKey avbPublicKey) throws android.os.RemoteException;

    android.gsi.GsiProgress getInstallProgress() throws android.os.RemoteException;

    java.util.List<java.lang.String> getInstalledDsuSlots() throws android.os.RemoteException;

    java.lang.String getInstalledGsiImageDir() throws android.os.RemoteException;

    boolean isGsiEnabled() throws android.os.RemoteException;

    boolean isGsiInstallInProgress() throws android.os.RemoteException;

    boolean isGsiInstalled() throws android.os.RemoteException;

    boolean isGsiRunning() throws android.os.RemoteException;

    android.gsi.IImageService openImageService(java.lang.String str) throws android.os.RemoteException;

    int openInstall(java.lang.String str) throws android.os.RemoteException;

    boolean removeGsi() throws android.os.RemoteException;

    void removeGsiAsync(android.gsi.IGsiServiceCallback iGsiServiceCallback) throws android.os.RemoteException;

    boolean setGsiAshmem(android.os.ParcelFileDescriptor parcelFileDescriptor, long j) throws android.os.RemoteException;

    long suggestScratchSize() throws android.os.RemoteException;

    int zeroPartition(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.gsi.IGsiService {
        @Override // android.gsi.IGsiService
        public boolean commitGsiChunkFromStream(android.os.ParcelFileDescriptor parcelFileDescriptor, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public android.gsi.GsiProgress getInstallProgress() throws android.os.RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public boolean setGsiAshmem(android.os.ParcelFileDescriptor parcelFileDescriptor, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean commitGsiChunkFromAshmem(long j) throws android.os.RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public int enableGsi(boolean z, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public void enableGsiAsync(boolean z, java.lang.String str, android.gsi.IGsiServiceCallback iGsiServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.gsi.IGsiService
        public boolean isGsiEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean cancelGsiInstall() throws android.os.RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean isGsiInstallInProgress() throws android.os.RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean removeGsi() throws android.os.RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public void removeGsiAsync(android.gsi.IGsiServiceCallback iGsiServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.gsi.IGsiService
        public boolean disableGsi() throws android.os.RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean isGsiInstalled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public boolean isGsiRunning() throws android.os.RemoteException {
            return false;
        }

        @Override // android.gsi.IGsiService
        public java.lang.String getActiveDsuSlot() throws android.os.RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public java.lang.String getInstalledGsiImageDir() throws android.os.RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public java.util.List<java.lang.String> getInstalledDsuSlots() throws android.os.RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public int openInstall(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public int closeInstall() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public int createPartition(java.lang.String str, long j, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public int closePartition() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public int zeroPartition(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public android.gsi.IImageService openImageService(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public java.lang.String dumpDeviceMapperDevices() throws android.os.RemoteException {
            return null;
        }

        @Override // android.gsi.IGsiService
        public int getAvbPublicKey(android.gsi.AvbPublicKey avbPublicKey) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.gsi.IGsiService
        public long suggestScratchSize() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.gsi.IGsiService {
        static final int TRANSACTION_cancelGsiInstall = 8;
        static final int TRANSACTION_closeInstall = 19;
        static final int TRANSACTION_closePartition = 21;
        static final int TRANSACTION_commitGsiChunkFromAshmem = 4;
        static final int TRANSACTION_commitGsiChunkFromStream = 1;
        static final int TRANSACTION_createPartition = 20;
        static final int TRANSACTION_disableGsi = 12;
        static final int TRANSACTION_dumpDeviceMapperDevices = 24;
        static final int TRANSACTION_enableGsi = 5;
        static final int TRANSACTION_enableGsiAsync = 6;
        static final int TRANSACTION_getActiveDsuSlot = 15;
        static final int TRANSACTION_getAvbPublicKey = 25;
        static final int TRANSACTION_getInstallProgress = 2;
        static final int TRANSACTION_getInstalledDsuSlots = 17;
        static final int TRANSACTION_getInstalledGsiImageDir = 16;
        static final int TRANSACTION_isGsiEnabled = 7;
        static final int TRANSACTION_isGsiInstallInProgress = 9;
        static final int TRANSACTION_isGsiInstalled = 13;
        static final int TRANSACTION_isGsiRunning = 14;
        static final int TRANSACTION_openImageService = 23;
        static final int TRANSACTION_openInstall = 18;
        static final int TRANSACTION_removeGsi = 10;
        static final int TRANSACTION_removeGsiAsync = 11;
        static final int TRANSACTION_setGsiAshmem = 3;
        static final int TRANSACTION_suggestScratchSize = 26;
        static final int TRANSACTION_zeroPartition = 22;

        public Stub() {
            attachInterface(this, android.gsi.IGsiService.DESCRIPTOR);
        }

        public static android.gsi.IGsiService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.gsi.IGsiService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.gsi.IGsiService)) {
                return (android.gsi.IGsiService) queryLocalInterface;
            }
            return new android.gsi.IGsiService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.gsi.IGsiService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.gsi.IGsiService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean commitGsiChunkFromStream = commitGsiChunkFromStream(parcelFileDescriptor, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(commitGsiChunkFromStream);
                    return true;
                case 2:
                    android.gsi.GsiProgress installProgress = getInstallProgress();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installProgress, 1);
                    return true;
                case 3:
                    android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean gsiAshmem = setGsiAshmem(parcelFileDescriptor2, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(gsiAshmem);
                    return true;
                case 4:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean commitGsiChunkFromAshmem = commitGsiChunkFromAshmem(readLong3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(commitGsiChunkFromAshmem);
                    return true;
                case 5:
                    boolean readBoolean = parcel.readBoolean();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int enableGsi = enableGsi(readBoolean, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(enableGsi);
                    return true;
                case 6:
                    boolean readBoolean2 = parcel.readBoolean();
                    java.lang.String readString2 = parcel.readString();
                    android.gsi.IGsiServiceCallback asInterface = android.gsi.IGsiServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    enableGsiAsync(readBoolean2, readString2, asInterface);
                    return true;
                case 7:
                    boolean isGsiEnabled = isGsiEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGsiEnabled);
                    return true;
                case 8:
                    boolean cancelGsiInstall = cancelGsiInstall();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cancelGsiInstall);
                    return true;
                case 9:
                    boolean isGsiInstallInProgress = isGsiInstallInProgress();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGsiInstallInProgress);
                    return true;
                case 10:
                    boolean removeGsi = removeGsi();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeGsi);
                    return true;
                case 11:
                    android.gsi.IGsiServiceCallback asInterface2 = android.gsi.IGsiServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeGsiAsync(asInterface2);
                    return true;
                case 12:
                    boolean disableGsi = disableGsi();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableGsi);
                    return true;
                case 13:
                    boolean isGsiInstalled = isGsiInstalled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGsiInstalled);
                    return true;
                case 14:
                    boolean isGsiRunning = isGsiRunning();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGsiRunning);
                    return true;
                case 15:
                    java.lang.String activeDsuSlot = getActiveDsuSlot();
                    parcel2.writeNoException();
                    parcel2.writeString(activeDsuSlot);
                    return true;
                case 16:
                    java.lang.String installedGsiImageDir = getInstalledGsiImageDir();
                    parcel2.writeNoException();
                    parcel2.writeString(installedGsiImageDir);
                    return true;
                case 17:
                    java.util.List<java.lang.String> installedDsuSlots = getInstalledDsuSlots();
                    parcel2.writeNoException();
                    parcel2.writeStringList(installedDsuSlots);
                    return true;
                case 18:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int openInstall = openInstall(readString3);
                    parcel2.writeNoException();
                    parcel2.writeInt(openInstall);
                    return true;
                case 19:
                    int closeInstall = closeInstall();
                    parcel2.writeNoException();
                    parcel2.writeInt(closeInstall);
                    return true;
                case 20:
                    java.lang.String readString4 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int createPartition = createPartition(readString4, readLong4, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeInt(createPartition);
                    return true;
                case 21:
                    int closePartition = closePartition();
                    parcel2.writeNoException();
                    parcel2.writeInt(closePartition);
                    return true;
                case 22:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int zeroPartition = zeroPartition(readString5);
                    parcel2.writeNoException();
                    parcel2.writeInt(zeroPartition);
                    return true;
                case 23:
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.gsi.IImageService openImageService = openImageService(readString6);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openImageService);
                    return true;
                case 24:
                    java.lang.String dumpDeviceMapperDevices = dumpDeviceMapperDevices();
                    parcel2.writeNoException();
                    parcel2.writeString(dumpDeviceMapperDevices);
                    return true;
                case 25:
                    android.gsi.AvbPublicKey avbPublicKey = new android.gsi.AvbPublicKey();
                    parcel.enforceNoDataAvail();
                    int avbPublicKey2 = getAvbPublicKey(avbPublicKey);
                    parcel2.writeNoException();
                    parcel2.writeInt(avbPublicKey2);
                    parcel2.writeTypedObject(avbPublicKey, 1);
                    return true;
                case 26:
                    long suggestScratchSize = suggestScratchSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(suggestScratchSize);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.gsi.IGsiService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.gsi.IGsiService.DESCRIPTOR;
            }

            @Override // android.gsi.IGsiService
            public boolean commitGsiChunkFromStream(android.os.ParcelFileDescriptor parcelFileDescriptor, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public android.gsi.GsiProgress getInstallProgress() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.gsi.GsiProgress) obtain2.readTypedObject(android.gsi.GsiProgress.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean setGsiAshmem(android.os.ParcelFileDescriptor parcelFileDescriptor, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean commitGsiChunkFromAshmem(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int enableGsi(boolean z, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public void enableGsiAsync(boolean z, java.lang.String str, android.gsi.IGsiServiceCallback iGsiServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iGsiServiceCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean isGsiEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean cancelGsiInstall() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean isGsiInstallInProgress() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean removeGsi() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public void removeGsiAsync(android.gsi.IGsiServiceCallback iGsiServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    obtain.writeStrongInterface(iGsiServiceCallback);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean disableGsi() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean isGsiInstalled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public boolean isGsiRunning() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public java.lang.String getActiveDsuSlot() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public java.lang.String getInstalledGsiImageDir() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public java.util.List<java.lang.String> getInstalledDsuSlots() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int openInstall(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int closeInstall() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int createPartition(java.lang.String str, long j, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int closePartition() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int zeroPartition(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public android.gsi.IImageService openImageService(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.gsi.IImageService.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public java.lang.String dumpDeviceMapperDevices() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public int getAvbPublicKey(android.gsi.AvbPublicKey avbPublicKey) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    if (obtain2.readInt() != 0) {
                        avbPublicKey.readFromParcel(obtain2);
                    }
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.gsi.IGsiService
            public long suggestScratchSize() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.gsi.IGsiService.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
