package android.service.oemlock;

/* loaded from: classes3.dex */
public interface IOemLockService extends android.os.IInterface {
    java.lang.String getLockName() throws android.os.RemoteException;

    boolean isDeviceOemUnlocked() throws android.os.RemoteException;

    boolean isOemUnlockAllowed() throws android.os.RemoteException;

    boolean isOemUnlockAllowedByCarrier() throws android.os.RemoteException;

    boolean isOemUnlockAllowedByUser() throws android.os.RemoteException;

    void setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) throws android.os.RemoteException;

    void setOemUnlockAllowedByUser(boolean z) throws android.os.RemoteException;

    public static class Default implements android.service.oemlock.IOemLockService {
        @Override // android.service.oemlock.IOemLockService
        public java.lang.String getLockName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.service.oemlock.IOemLockService
        public void setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.service.oemlock.IOemLockService
        public boolean isOemUnlockAllowedByCarrier() throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.oemlock.IOemLockService
        public void setOemUnlockAllowedByUser(boolean z) throws android.os.RemoteException {
        }

        @Override // android.service.oemlock.IOemLockService
        public boolean isOemUnlockAllowedByUser() throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.oemlock.IOemLockService
        public boolean isOemUnlockAllowed() throws android.os.RemoteException {
            return false;
        }

        @Override // android.service.oemlock.IOemLockService
        public boolean isDeviceOemUnlocked() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.oemlock.IOemLockService {
        public static final java.lang.String DESCRIPTOR = "android.service.oemlock.IOemLockService";
        static final int TRANSACTION_getLockName = 1;
        static final int TRANSACTION_isDeviceOemUnlocked = 7;
        static final int TRANSACTION_isOemUnlockAllowed = 6;
        static final int TRANSACTION_isOemUnlockAllowedByCarrier = 3;
        static final int TRANSACTION_isOemUnlockAllowedByUser = 5;
        static final int TRANSACTION_setOemUnlockAllowedByCarrier = 2;
        static final int TRANSACTION_setOemUnlockAllowedByUser = 4;
        private final android.os.PermissionEnforcer mEnforcer;
        static final java.lang.String[] PERMISSIONS_isOemUnlockAllowed = {android.Manifest.permission.READ_OEM_UNLOCK_STATE, android.Manifest.permission.OEM_UNLOCK_STATE};
        static final java.lang.String[] PERMISSIONS_isDeviceOemUnlocked = {android.Manifest.permission.READ_OEM_UNLOCK_STATE, android.Manifest.permission.OEM_UNLOCK_STATE};

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.service.oemlock.IOemLockService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.oemlock.IOemLockService)) {
                return (android.service.oemlock.IOemLockService) queryLocalInterface;
            }
            return new android.service.oemlock.IOemLockService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getLockName";
                case 2:
                    return "setOemUnlockAllowedByCarrier";
                case 3:
                    return "isOemUnlockAllowedByCarrier";
                case 4:
                    return "setOemUnlockAllowedByUser";
                case 5:
                    return "isOemUnlockAllowedByUser";
                case 6:
                    return "isOemUnlockAllowed";
                case 7:
                    return "isDeviceOemUnlocked";
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
                    java.lang.String lockName = getLockName();
                    parcel2.writeNoException();
                    parcel2.writeString(lockName);
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setOemUnlockAllowedByCarrier(readBoolean, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    boolean isOemUnlockAllowedByCarrier = isOemUnlockAllowedByCarrier();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOemUnlockAllowedByCarrier);
                    return true;
                case 4:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setOemUnlockAllowedByUser(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean isOemUnlockAllowedByUser = isOemUnlockAllowedByUser();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOemUnlockAllowedByUser);
                    return true;
                case 6:
                    boolean isOemUnlockAllowed = isOemUnlockAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOemUnlockAllowed);
                    return true;
                case 7:
                    boolean isDeviceOemUnlocked = isDeviceOemUnlocked();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceOemUnlocked);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.oemlock.IOemLockService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.oemlock.IOemLockService.Stub.DESCRIPTOR;
            }

            @Override // android.service.oemlock.IOemLockService
            public java.lang.String getLockName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.oemlock.IOemLockService.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.oemlock.IOemLockService
            public void setOemUnlockAllowedByCarrier(boolean z, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.oemlock.IOemLockService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.oemlock.IOemLockService
            public boolean isOemUnlockAllowedByCarrier() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.oemlock.IOemLockService.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.oemlock.IOemLockService
            public void setOemUnlockAllowedByUser(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.oemlock.IOemLockService.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.oemlock.IOemLockService
            public boolean isOemUnlockAllowedByUser() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.oemlock.IOemLockService.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.oemlock.IOemLockService
            public boolean isOemUnlockAllowed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.oemlock.IOemLockService.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.oemlock.IOemLockService
            public boolean isDeviceOemUnlocked() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.service.oemlock.IOemLockService.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getLockName_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_CARRIER_OEM_UNLOCK_STATE, getCallingPid(), getCallingUid());
        }

        protected void setOemUnlockAllowedByCarrier_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_CARRIER_OEM_UNLOCK_STATE, getCallingPid(), getCallingUid());
        }

        protected void isOemUnlockAllowedByCarrier_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_CARRIER_OEM_UNLOCK_STATE, getCallingPid(), getCallingUid());
        }

        protected void setOemUnlockAllowedByUser_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USER_OEM_UNLOCK_STATE, getCallingPid(), getCallingUid());
        }

        protected void isOemUnlockAllowedByUser_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MANAGE_USER_OEM_UNLOCK_STATE, getCallingPid(), getCallingUid());
        }

        protected void isOemUnlockAllowed_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_isOemUnlockAllowed, getCallingPid(), getCallingUid());
        }

        protected void isDeviceOemUnlocked_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAnyOf(PERMISSIONS_isDeviceOemUnlocked, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
