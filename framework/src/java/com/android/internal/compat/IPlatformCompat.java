package com.android.internal.compat;

/* loaded from: classes4.dex */
public interface IPlatformCompat extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.compat.IPlatformCompat";

    boolean clearOverride(long j, java.lang.String str) throws android.os.RemoteException;

    boolean clearOverrideForTest(long j, java.lang.String str) throws android.os.RemoteException;

    void clearOverrides(java.lang.String str) throws android.os.RemoteException;

    void clearOverridesForTest(java.lang.String str) throws android.os.RemoteException;

    int disableTargetSdkChanges(java.lang.String str, int i) throws android.os.RemoteException;

    int enableTargetSdkChanges(java.lang.String str, int i) throws android.os.RemoteException;

    com.android.internal.compat.CompatibilityChangeConfig getAppConfig(android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException;

    com.android.internal.compat.IOverrideValidator getOverrideValidator() throws android.os.RemoteException;

    boolean isChangeEnabled(long j, android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException;

    boolean isChangeEnabledByPackageName(long j, java.lang.String str, int i) throws android.os.RemoteException;

    boolean isChangeEnabledByUid(long j, int i) throws android.os.RemoteException;

    com.android.internal.compat.CompatibilityChangeInfo[] listAllChanges() throws android.os.RemoteException;

    com.android.internal.compat.CompatibilityChangeInfo[] listUIChanges() throws android.os.RemoteException;

    void putAllOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverridesByPackageConfig compatibilityOverridesByPackageConfig) throws android.os.RemoteException;

    void putOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverrideConfig compatibilityOverrideConfig, java.lang.String str) throws android.os.RemoteException;

    void removeAllOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig compatibilityOverridesToRemoveByPackageConfig) throws android.os.RemoteException;

    void removeOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig, java.lang.String str) throws android.os.RemoteException;

    void reportChange(long j, android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException;

    void reportChangeByPackageName(long j, java.lang.String str, int i) throws android.os.RemoteException;

    void reportChangeByUid(long j, int i) throws android.os.RemoteException;

    void setOverrides(com.android.internal.compat.CompatibilityChangeConfig compatibilityChangeConfig, java.lang.String str) throws android.os.RemoteException;

    void setOverridesForTest(com.android.internal.compat.CompatibilityChangeConfig compatibilityChangeConfig, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.internal.compat.IPlatformCompat {
        @Override // com.android.internal.compat.IPlatformCompat
        public void reportChange(long j, android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void reportChangeByPackageName(long j, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void reportChangeByUid(long j, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean isChangeEnabled(long j, android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean isChangeEnabledByPackageName(long j, java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean isChangeEnabledByUid(long j, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void setOverrides(com.android.internal.compat.CompatibilityChangeConfig compatibilityChangeConfig, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void putAllOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverridesByPackageConfig compatibilityOverridesByPackageConfig) throws android.os.RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void putOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverrideConfig compatibilityOverrideConfig, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void setOverridesForTest(com.android.internal.compat.CompatibilityChangeConfig compatibilityChangeConfig, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean clearOverride(long j, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public boolean clearOverrideForTest(long j, java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void removeAllOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig compatibilityOverridesToRemoveByPackageConfig) throws android.os.RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void removeOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public int enableTargetSdkChanges(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public int disableTargetSdkChanges(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void clearOverrides(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public void clearOverridesForTest(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public com.android.internal.compat.CompatibilityChangeConfig getAppConfig(android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public com.android.internal.compat.CompatibilityChangeInfo[] listAllChanges() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public com.android.internal.compat.CompatibilityChangeInfo[] listUIChanges() throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.compat.IPlatformCompat
        public com.android.internal.compat.IOverrideValidator getOverrideValidator() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.compat.IPlatformCompat {
        static final int TRANSACTION_clearOverride = 11;
        static final int TRANSACTION_clearOverrideForTest = 12;
        static final int TRANSACTION_clearOverrides = 17;
        static final int TRANSACTION_clearOverridesForTest = 18;
        static final int TRANSACTION_disableTargetSdkChanges = 16;
        static final int TRANSACTION_enableTargetSdkChanges = 15;
        static final int TRANSACTION_getAppConfig = 19;
        static final int TRANSACTION_getOverrideValidator = 22;
        static final int TRANSACTION_isChangeEnabled = 4;
        static final int TRANSACTION_isChangeEnabledByPackageName = 5;
        static final int TRANSACTION_isChangeEnabledByUid = 6;
        static final int TRANSACTION_listAllChanges = 20;
        static final int TRANSACTION_listUIChanges = 21;
        static final int TRANSACTION_putAllOverridesOnReleaseBuilds = 8;
        static final int TRANSACTION_putOverridesOnReleaseBuilds = 9;
        static final int TRANSACTION_removeAllOverridesOnReleaseBuilds = 13;
        static final int TRANSACTION_removeOverridesOnReleaseBuilds = 14;
        static final int TRANSACTION_reportChange = 1;
        static final int TRANSACTION_reportChangeByPackageName = 2;
        static final int TRANSACTION_reportChangeByUid = 3;
        static final int TRANSACTION_setOverrides = 7;
        static final int TRANSACTION_setOverridesForTest = 10;
        private final android.os.PermissionEnforcer mEnforcer;
        static final java.lang.String[] PERMISSIONS_isChangeEnabled = {android.Manifest.permission.LOG_COMPAT_CHANGE, android.Manifest.permission.READ_COMPAT_CHANGE_CONFIG};
        static final java.lang.String[] PERMISSIONS_isChangeEnabledByPackageName = {android.Manifest.permission.LOG_COMPAT_CHANGE, android.Manifest.permission.READ_COMPAT_CHANGE_CONFIG};
        static final java.lang.String[] PERMISSIONS_isChangeEnabledByUid = {android.Manifest.permission.LOG_COMPAT_CHANGE, android.Manifest.permission.READ_COMPAT_CHANGE_CONFIG};
        static final java.lang.String[] PERMISSIONS_getAppConfig = {android.Manifest.permission.LOG_COMPAT_CHANGE, android.Manifest.permission.READ_COMPAT_CHANGE_CONFIG};

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static com.android.internal.compat.IPlatformCompat asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.compat.IPlatformCompat)) {
                return (com.android.internal.compat.IPlatformCompat) queryLocalInterface;
            }
            return new com.android.internal.compat.IPlatformCompat.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "reportChange";
                case 2:
                    return "reportChangeByPackageName";
                case 3:
                    return "reportChangeByUid";
                case 4:
                    return "isChangeEnabled";
                case 5:
                    return "isChangeEnabledByPackageName";
                case 6:
                    return "isChangeEnabledByUid";
                case 7:
                    return "setOverrides";
                case 8:
                    return "putAllOverridesOnReleaseBuilds";
                case 9:
                    return "putOverridesOnReleaseBuilds";
                case 10:
                    return "setOverridesForTest";
                case 11:
                    return "clearOverride";
                case 12:
                    return "clearOverrideForTest";
                case 13:
                    return "removeAllOverridesOnReleaseBuilds";
                case 14:
                    return "removeOverridesOnReleaseBuilds";
                case 15:
                    return "enableTargetSdkChanges";
                case 16:
                    return "disableTargetSdkChanges";
                case 17:
                    return "clearOverrides";
                case 18:
                    return "clearOverridesForTest";
                case 19:
                    return "getAppConfig";
                case 20:
                    return "listAllChanges";
                case 21:
                    return "listUIChanges";
                case 22:
                    return "getOverrideValidator";
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
                parcel.enforceInterface(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    long readLong = parcel.readLong();
                    android.content.pm.ApplicationInfo applicationInfo = (android.content.pm.ApplicationInfo) parcel.readTypedObject(android.content.pm.ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportChange(readLong, applicationInfo);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    long readLong2 = parcel.readLong();
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportChangeByPackageName(readLong2, readString, readInt);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    long readLong3 = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportChangeByUid(readLong3, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    long readLong4 = parcel.readLong();
                    android.content.pm.ApplicationInfo applicationInfo2 = (android.content.pm.ApplicationInfo) parcel.readTypedObject(android.content.pm.ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isChangeEnabled = isChangeEnabled(readLong4, applicationInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isChangeEnabled);
                    return true;
                case 5:
                    long readLong5 = parcel.readLong();
                    java.lang.String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isChangeEnabledByPackageName = isChangeEnabledByPackageName(readLong5, readString2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isChangeEnabledByPackageName);
                    return true;
                case 6:
                    long readLong6 = parcel.readLong();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isChangeEnabledByUid = isChangeEnabledByUid(readLong6, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isChangeEnabledByUid);
                    return true;
                case 7:
                    com.android.internal.compat.CompatibilityChangeConfig compatibilityChangeConfig = (com.android.internal.compat.CompatibilityChangeConfig) parcel.readTypedObject(com.android.internal.compat.CompatibilityChangeConfig.CREATOR);
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setOverrides(compatibilityChangeConfig, readString3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    com.android.internal.compat.CompatibilityOverridesByPackageConfig compatibilityOverridesByPackageConfig = (com.android.internal.compat.CompatibilityOverridesByPackageConfig) parcel.readTypedObject(com.android.internal.compat.CompatibilityOverridesByPackageConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    putAllOverridesOnReleaseBuilds(compatibilityOverridesByPackageConfig);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    com.android.internal.compat.CompatibilityOverrideConfig compatibilityOverrideConfig = (com.android.internal.compat.CompatibilityOverrideConfig) parcel.readTypedObject(com.android.internal.compat.CompatibilityOverrideConfig.CREATOR);
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    putOverridesOnReleaseBuilds(compatibilityOverrideConfig, readString4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    com.android.internal.compat.CompatibilityChangeConfig compatibilityChangeConfig2 = (com.android.internal.compat.CompatibilityChangeConfig) parcel.readTypedObject(com.android.internal.compat.CompatibilityChangeConfig.CREATOR);
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setOverridesForTest(compatibilityChangeConfig2, readString5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    long readLong7 = parcel.readLong();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean clearOverride = clearOverride(readLong7, readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearOverride);
                    return true;
                case 12:
                    long readLong8 = parcel.readLong();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean clearOverrideForTest = clearOverrideForTest(readLong8, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearOverrideForTest);
                    return true;
                case 13:
                    com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig compatibilityOverridesToRemoveByPackageConfig = (com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig) parcel.readTypedObject(com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeAllOverridesOnReleaseBuilds(compatibilityOverridesToRemoveByPackageConfig);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    com.android.internal.compat.CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig = (com.android.internal.compat.CompatibilityOverridesToRemoveConfig) parcel.readTypedObject(com.android.internal.compat.CompatibilityOverridesToRemoveConfig.CREATOR);
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeOverridesOnReleaseBuilds(compatibilityOverridesToRemoveConfig, readString8);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    java.lang.String readString9 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int enableTargetSdkChanges = enableTargetSdkChanges(readString9, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(enableTargetSdkChanges);
                    return true;
                case 16:
                    java.lang.String readString10 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int disableTargetSdkChanges = disableTargetSdkChanges(readString10, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(disableTargetSdkChanges);
                    return true;
                case 17:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearOverrides(readString11);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearOverridesForTest(readString12);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    android.content.pm.ApplicationInfo applicationInfo3 = (android.content.pm.ApplicationInfo) parcel.readTypedObject(android.content.pm.ApplicationInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    com.android.internal.compat.CompatibilityChangeConfig appConfig = getAppConfig(applicationInfo3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appConfig, 1);
                    return true;
                case 20:
                    com.android.internal.compat.CompatibilityChangeInfo[] listAllChanges = listAllChanges();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listAllChanges, 1);
                    return true;
                case 21:
                    com.android.internal.compat.CompatibilityChangeInfo[] listUIChanges = listUIChanges();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(listUIChanges, 1);
                    return true;
                case 22:
                    com.android.internal.compat.IOverrideValidator overrideValidator = getOverrideValidator();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(overrideValidator);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.compat.IPlatformCompat {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.compat.IPlatformCompat.DESCRIPTOR;
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void reportChange(long j, android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void reportChangeByPackageName(long j, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void reportChangeByUid(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean isChangeEnabled(long j, android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean isChangeEnabledByPackageName(long j, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean isChangeEnabledByUid(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void setOverrides(com.android.internal.compat.CompatibilityChangeConfig compatibilityChangeConfig, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeTypedObject(compatibilityChangeConfig, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void putAllOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverridesByPackageConfig compatibilityOverridesByPackageConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeTypedObject(compatibilityOverridesByPackageConfig, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void putOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverrideConfig compatibilityOverrideConfig, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeTypedObject(compatibilityOverrideConfig, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void setOverridesForTest(com.android.internal.compat.CompatibilityChangeConfig compatibilityChangeConfig, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeTypedObject(compatibilityChangeConfig, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean clearOverride(long j, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public boolean clearOverrideForTest(long j, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void removeAllOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverridesToRemoveByPackageConfig compatibilityOverridesToRemoveByPackageConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeTypedObject(compatibilityOverridesToRemoveByPackageConfig, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void removeOverridesOnReleaseBuilds(com.android.internal.compat.CompatibilityOverridesToRemoveConfig compatibilityOverridesToRemoveConfig, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeTypedObject(compatibilityOverridesToRemoveConfig, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public int enableTargetSdkChanges(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public int disableTargetSdkChanges(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void clearOverrides(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public void clearOverridesForTest(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public com.android.internal.compat.CompatibilityChangeConfig getAppConfig(android.content.pm.ApplicationInfo applicationInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    obtain.writeTypedObject(applicationInfo, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.compat.CompatibilityChangeConfig) obtain2.readTypedObject(com.android.internal.compat.CompatibilityChangeConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public com.android.internal.compat.CompatibilityChangeInfo[] listAllChanges() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.compat.CompatibilityChangeInfo[]) obtain2.createTypedArray(com.android.internal.compat.CompatibilityChangeInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public com.android.internal.compat.CompatibilityChangeInfo[] listUIChanges() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.compat.CompatibilityChangeInfo[]) obtain2.createTypedArray(com.android.internal.compat.CompatibilityChangeInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.compat.IPlatformCompat
            public com.android.internal.compat.IOverrideValidator getOverrideValidator() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.compat.IPlatformCompat.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.android.internal.compat.IOverrideValidator.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void reportChange_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.LOG_COMPAT_CHANGE, getCallingPid(), getCallingUid());
        }

        protected void reportChangeByPackageName_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.LOG_COMPAT_CHANGE, getCallingPid(), getCallingUid());
        }

        protected void reportChangeByUid_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.LOG_COMPAT_CHANGE, getCallingPid(), getCallingUid());
        }

        protected void isChangeEnabled_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_isChangeEnabled, getCallingPid(), getCallingUid());
        }

        protected void isChangeEnabledByPackageName_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_isChangeEnabledByPackageName, getCallingPid(), getCallingUid());
        }

        protected void isChangeEnabledByUid_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_isChangeEnabledByUid, getCallingPid(), getCallingUid());
        }

        protected void setOverrides_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void putAllOverridesOnReleaseBuilds_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD, getCallingPid(), getCallingUid());
        }

        protected void putOverridesOnReleaseBuilds_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD, getCallingPid(), getCallingUid());
        }

        protected void setOverridesForTest_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void clearOverride_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void clearOverrideForTest_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void removeAllOverridesOnReleaseBuilds_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD, getCallingPid(), getCallingUid());
        }

        protected void removeOverridesOnReleaseBuilds_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG_ON_RELEASE_BUILD, getCallingPid(), getCallingUid());
        }

        protected void enableTargetSdkChanges_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void disableTargetSdkChanges_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void clearOverrides_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void clearOverridesForTest_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.OVERRIDE_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        protected void getAppConfig_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_getAppConfig, getCallingPid(), getCallingUid());
        }

        protected void listAllChanges_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.READ_COMPAT_CHANGE_CONFIG, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 21;
        }
    }
}
