package android.os.image;

/* loaded from: classes3.dex */
public interface IDynamicSystemService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.os.image.IDynamicSystemService";

    boolean abort() throws android.os.RemoteException;

    boolean closePartition() throws android.os.RemoteException;

    int createPartition(java.lang.String str, long j, boolean z) throws android.os.RemoteException;

    boolean finishInstallation() throws android.os.RemoteException;

    java.lang.String getActiveDsuSlot() throws android.os.RemoteException;

    boolean getAvbPublicKey(android.gsi.AvbPublicKey avbPublicKey) throws android.os.RemoteException;

    android.gsi.GsiProgress getInstallationProgress() throws android.os.RemoteException;

    boolean isEnabled() throws android.os.RemoteException;

    boolean isInUse() throws android.os.RemoteException;

    boolean isInstalled() throws android.os.RemoteException;

    boolean remove() throws android.os.RemoteException;

    boolean setAshmem(android.os.ParcelFileDescriptor parcelFileDescriptor, long j) throws android.os.RemoteException;

    boolean setEnable(boolean z, boolean z2) throws android.os.RemoteException;

    boolean startInstallation(java.lang.String str) throws android.os.RemoteException;

    boolean submitFromAshmem(long j) throws android.os.RemoteException;

    long suggestScratchSize() throws android.os.RemoteException;

    public static class Default implements android.os.image.IDynamicSystemService {
        @Override // android.os.image.IDynamicSystemService
        public boolean startInstallation(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public int createPartition(java.lang.String str, long j, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean closePartition() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean finishInstallation() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public android.gsi.GsiProgress getInstallationProgress() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean abort() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean isInUse() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean isInstalled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean isEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean remove() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean setEnable(boolean z, boolean z2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean setAshmem(android.os.ParcelFileDescriptor parcelFileDescriptor, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean submitFromAshmem(long j) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public boolean getAvbPublicKey(android.gsi.AvbPublicKey avbPublicKey) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.image.IDynamicSystemService
        public long suggestScratchSize() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.image.IDynamicSystemService
        public java.lang.String getActiveDsuSlot() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.image.IDynamicSystemService {
        static final int TRANSACTION_abort = 6;
        static final int TRANSACTION_closePartition = 3;
        static final int TRANSACTION_createPartition = 2;
        static final int TRANSACTION_finishInstallation = 4;
        static final int TRANSACTION_getActiveDsuSlot = 16;
        static final int TRANSACTION_getAvbPublicKey = 14;
        static final int TRANSACTION_getInstallationProgress = 5;
        static final int TRANSACTION_isEnabled = 9;
        static final int TRANSACTION_isInUse = 7;
        static final int TRANSACTION_isInstalled = 8;
        static final int TRANSACTION_remove = 10;
        static final int TRANSACTION_setAshmem = 12;
        static final int TRANSACTION_setEnable = 11;
        static final int TRANSACTION_startInstallation = 1;
        static final int TRANSACTION_submitFromAshmem = 13;
        static final int TRANSACTION_suggestScratchSize = 15;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, android.os.image.IDynamicSystemService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.os.image.IDynamicSystemService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.image.IDynamicSystemService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.image.IDynamicSystemService)) {
                return (android.os.image.IDynamicSystemService) queryLocalInterface;
            }
            return new android.os.image.IDynamicSystemService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startInstallation";
                case 2:
                    return "createPartition";
                case 3:
                    return "closePartition";
                case 4:
                    return "finishInstallation";
                case 5:
                    return "getInstallationProgress";
                case 6:
                    return "abort";
                case 7:
                    return "isInUse";
                case 8:
                    return "isInstalled";
                case 9:
                    return "isEnabled";
                case 10:
                    return "remove";
                case 11:
                    return "setEnable";
                case 12:
                    return "setAshmem";
                case 13:
                    return "submitFromAshmem";
                case 14:
                    return "getAvbPublicKey";
                case 15:
                    return "suggestScratchSize";
                case 16:
                    return "getActiveDsuSlot";
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
                parcel.enforceInterface(android.os.image.IDynamicSystemService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.os.image.IDynamicSystemService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean startInstallation = startInstallation(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startInstallation);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    long readLong = parcel.readLong();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int createPartition = createPartition(readString2, readLong, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(createPartition);
                    return true;
                case 3:
                    boolean closePartition = closePartition();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(closePartition);
                    return true;
                case 4:
                    boolean finishInstallation = finishInstallation();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(finishInstallation);
                    return true;
                case 5:
                    android.gsi.GsiProgress installationProgress = getInstallationProgress();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installationProgress, 1);
                    return true;
                case 6:
                    boolean abort = abort();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(abort);
                    return true;
                case 7:
                    boolean isInUse = isInUse();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInUse);
                    return true;
                case 8:
                    boolean isInstalled = isInstalled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInstalled);
                    return true;
                case 9:
                    boolean isEnabled = isEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEnabled);
                    return true;
                case 10:
                    boolean remove = remove();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(remove);
                    return true;
                case 11:
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enable = setEnable(readBoolean2, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enable);
                    return true;
                case 12:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean ashmem = setAshmem(parcelFileDescriptor, readLong2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(ashmem);
                    return true;
                case 13:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean submitFromAshmem = submitFromAshmem(readLong3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(submitFromAshmem);
                    return true;
                case 14:
                    android.gsi.AvbPublicKey avbPublicKey = new android.gsi.AvbPublicKey();
                    parcel.enforceNoDataAvail();
                    boolean avbPublicKey2 = getAvbPublicKey(avbPublicKey);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(avbPublicKey2);
                    parcel2.writeTypedObject(avbPublicKey, 1);
                    return true;
                case 15:
                    long suggestScratchSize = suggestScratchSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(suggestScratchSize);
                    return true;
                case 16:
                    java.lang.String activeDsuSlot = getActiveDsuSlot();
                    parcel2.writeNoException();
                    parcel2.writeString(activeDsuSlot);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.image.IDynamicSystemService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.image.IDynamicSystemService.DESCRIPTOR;
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean startInstallation(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public int createPartition(java.lang.String str, long j, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean closePartition() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean finishInstallation() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public android.gsi.GsiProgress getInstallationProgress() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.gsi.GsiProgress) obtain2.readTypedObject(android.gsi.GsiProgress.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean abort() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean isInUse() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean isInstalled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean isEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean remove() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean setEnable(boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean setAshmem(android.os.ParcelFileDescriptor parcelFileDescriptor, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean submitFromAshmem(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public boolean getAvbPublicKey(android.gsi.AvbPublicKey avbPublicKey) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    if (obtain2.readInt() != 0) {
                        avbPublicKey.readFromParcel(obtain2);
                    }
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public long suggestScratchSize() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.image.IDynamicSystemService
            public java.lang.String getActiveDsuSlot() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.image.IDynamicSystemService.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void startInstallation_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void createPartition_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void closePartition_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void finishInstallation_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void getInstallationProgress_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void abort_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void isEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void remove_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void setEnable_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void setAshmem_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void submitFromAshmem_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void getAvbPublicKey_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void suggestScratchSize_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        protected void getActiveDsuSlot_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_DYNAMIC_SYSTEM, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }
    }
}
