package android.app.trust;

/* loaded from: classes.dex */
public interface ITrustManager extends android.os.IInterface {
    void clearAllBiometricRecognized(android.hardware.biometrics.BiometricSourceType biometricSourceType, int i) throws android.os.RemoteException;

    boolean isActiveUnlockRunning(int i) throws android.os.RemoteException;

    boolean isDeviceLocked(int i, int i2) throws android.os.RemoteException;

    boolean isDeviceSecure(int i, int i2) throws android.os.RemoteException;

    boolean isTrustUsuallyManaged(int i) throws android.os.RemoteException;

    void registerTrustListener(android.app.trust.ITrustListener iTrustListener) throws android.os.RemoteException;

    void reportEnabledTrustAgentsChanged(int i) throws android.os.RemoteException;

    void reportKeyguardShowingChanged() throws android.os.RemoteException;

    void reportUnlockAttempt(boolean z, int i) throws android.os.RemoteException;

    void reportUnlockLockout(int i, int i2) throws android.os.RemoteException;

    void reportUserMayRequestUnlock(int i) throws android.os.RemoteException;

    void reportUserRequestedUnlock(int i, boolean z) throws android.os.RemoteException;

    void setDeviceLockedForUser(int i, boolean z) throws android.os.RemoteException;

    void unlockedByBiometricForUser(int i, android.hardware.biometrics.BiometricSourceType biometricSourceType) throws android.os.RemoteException;

    void unregisterTrustListener(android.app.trust.ITrustListener iTrustListener) throws android.os.RemoteException;

    public static class Default implements android.app.trust.ITrustManager {
        @Override // android.app.trust.ITrustManager
        public void reportUnlockAttempt(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void reportUserRequestedUnlock(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void reportUserMayRequestUnlock(int i) throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void reportUnlockLockout(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void reportEnabledTrustAgentsChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void registerTrustListener(android.app.trust.ITrustListener iTrustListener) throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void unregisterTrustListener(android.app.trust.ITrustListener iTrustListener) throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void reportKeyguardShowingChanged() throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void setDeviceLockedForUser(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public boolean isDeviceLocked(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.trust.ITrustManager
        public boolean isDeviceSecure(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.trust.ITrustManager
        public boolean isTrustUsuallyManaged(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.trust.ITrustManager
        public void unlockedByBiometricForUser(int i, android.hardware.biometrics.BiometricSourceType biometricSourceType) throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public void clearAllBiometricRecognized(android.hardware.biometrics.BiometricSourceType biometricSourceType, int i) throws android.os.RemoteException {
        }

        @Override // android.app.trust.ITrustManager
        public boolean isActiveUnlockRunning(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.trust.ITrustManager {
        public static final java.lang.String DESCRIPTOR = "android.app.trust.ITrustManager";
        static final int TRANSACTION_clearAllBiometricRecognized = 14;
        static final int TRANSACTION_isActiveUnlockRunning = 15;
        static final int TRANSACTION_isDeviceLocked = 10;
        static final int TRANSACTION_isDeviceSecure = 11;
        static final int TRANSACTION_isTrustUsuallyManaged = 12;
        static final int TRANSACTION_registerTrustListener = 6;
        static final int TRANSACTION_reportEnabledTrustAgentsChanged = 5;
        static final int TRANSACTION_reportKeyguardShowingChanged = 8;
        static final int TRANSACTION_reportUnlockAttempt = 1;
        static final int TRANSACTION_reportUnlockLockout = 4;
        static final int TRANSACTION_reportUserMayRequestUnlock = 3;
        static final int TRANSACTION_reportUserRequestedUnlock = 2;
        static final int TRANSACTION_setDeviceLockedForUser = 9;
        static final int TRANSACTION_unlockedByBiometricForUser = 13;
        static final int TRANSACTION_unregisterTrustListener = 7;
        private final android.os.PermissionEnforcer mEnforcer;

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

        public static android.app.trust.ITrustManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.trust.ITrustManager)) {
                return (android.app.trust.ITrustManager) queryLocalInterface;
            }
            return new android.app.trust.ITrustManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "reportUnlockAttempt";
                case 2:
                    return "reportUserRequestedUnlock";
                case 3:
                    return "reportUserMayRequestUnlock";
                case 4:
                    return "reportUnlockLockout";
                case 5:
                    return "reportEnabledTrustAgentsChanged";
                case 6:
                    return "registerTrustListener";
                case 7:
                    return "unregisterTrustListener";
                case 8:
                    return "reportKeyguardShowingChanged";
                case 9:
                    return "setDeviceLockedForUser";
                case 10:
                    return "isDeviceLocked";
                case 11:
                    return "isDeviceSecure";
                case 12:
                    return "isTrustUsuallyManaged";
                case 13:
                    return "unlockedByBiometricForUser";
                case 14:
                    return "clearAllBiometricRecognized";
                case 15:
                    return "isActiveUnlockRunning";
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportUnlockAttempt(readBoolean, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    reportUserRequestedUnlock(readInt2, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportUserMayRequestUnlock(readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportUnlockLockout(readInt4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportEnabledTrustAgentsChanged(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.app.trust.ITrustListener asInterface = android.app.trust.ITrustListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerTrustListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.app.trust.ITrustListener asInterface2 = android.app.trust.ITrustListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterTrustListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    reportKeyguardShowingChanged();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt7 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDeviceLockedForUser(readInt7, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDeviceLocked = isDeviceLocked(readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceLocked);
                    return true;
                case 11:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDeviceSecure = isDeviceSecure(readInt10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceSecure);
                    return true;
                case 12:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isTrustUsuallyManaged = isTrustUsuallyManaged(readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTrustUsuallyManaged);
                    return true;
                case 13:
                    int readInt13 = parcel.readInt();
                    android.hardware.biometrics.BiometricSourceType biometricSourceType = (android.hardware.biometrics.BiometricSourceType) parcel.readTypedObject(android.hardware.biometrics.BiometricSourceType.CREATOR);
                    parcel.enforceNoDataAvail();
                    unlockedByBiometricForUser(readInt13, biometricSourceType);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.hardware.biometrics.BiometricSourceType biometricSourceType2 = (android.hardware.biometrics.BiometricSourceType) parcel.readTypedObject(android.hardware.biometrics.BiometricSourceType.CREATOR);
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearAllBiometricRecognized(biometricSourceType2, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isActiveUnlockRunning = isActiveUnlockRunning(readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActiveUnlockRunning);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.trust.ITrustManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.trust.ITrustManager.Stub.DESCRIPTOR;
            }

            @Override // android.app.trust.ITrustManager
            public void reportUnlockAttempt(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportUserRequestedUnlock(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportUserMayRequestUnlock(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportUnlockLockout(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportEnabledTrustAgentsChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void registerTrustListener(android.app.trust.ITrustListener iTrustListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTrustListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void unregisterTrustListener(android.app.trust.ITrustListener iTrustListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTrustListener);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void reportKeyguardShowingChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void setDeviceLockedForUser(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public boolean isDeviceLocked(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public boolean isDeviceSecure(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public boolean isTrustUsuallyManaged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void unlockedByBiometricForUser(int i, android.hardware.biometrics.BiometricSourceType biometricSourceType) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(biometricSourceType, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public void clearAllBiometricRecognized(android.hardware.biometrics.BiometricSourceType biometricSourceType, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(biometricSourceType, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.trust.ITrustManager
            public boolean isActiveUnlockRunning(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.trust.ITrustManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void isTrustUsuallyManaged_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.TRUST_LISTENER, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 14;
        }
    }
}
