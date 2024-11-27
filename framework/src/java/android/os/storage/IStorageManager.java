package android.os.storage;

/* loaded from: classes3.dex */
public interface IStorageManager extends android.os.IInterface {
    void abortChanges(java.lang.String str, boolean z) throws android.os.RemoteException;

    void abortIdleMaintenance() throws android.os.RemoteException;

    void allocateBytes(java.lang.String str, long j, int i, java.lang.String str2) throws android.os.RemoteException;

    void benchmark(java.lang.String str, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException;

    void commitChanges() throws android.os.RemoteException;

    void createUserStorageKeys(int i, boolean z) throws android.os.RemoteException;

    void destroyUserStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void destroyUserStorageKeys(int i) throws android.os.RemoteException;

    void disableAppDataIsolation(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void fixupAppDir(java.lang.String str) throws android.os.RemoteException;

    void forgetAllVolumes() throws android.os.RemoteException;

    void forgetVolume(java.lang.String str) throws android.os.RemoteException;

    void format(java.lang.String str) throws android.os.RemoteException;

    void fstrim(int i, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException;

    long getAllocatableBytes(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    long getCacheQuotaBytes(java.lang.String str, int i) throws android.os.RemoteException;

    long getCacheSizeBytes(java.lang.String str, int i) throws android.os.RemoteException;

    java.lang.String getCloudMediaProvider() throws android.os.RemoteException;

    android.os.storage.DiskInfo[] getDisks() throws android.os.RemoteException;

    int getExternalStorageMountMode(int i, java.lang.String str) throws android.os.RemoteException;

    long getInternalStorageBlockDeviceSize() throws android.os.RemoteException;

    int getInternalStorageRemainingLifetime() throws android.os.RemoteException;

    android.app.PendingIntent getManageSpaceActivityIntent(java.lang.String str, int i) throws android.os.RemoteException;

    java.lang.String getMountedObbPath(java.lang.String str) throws android.os.RemoteException;

    java.lang.String getPrimaryStorageUuid() throws android.os.RemoteException;

    android.os.storage.StorageVolume[] getVolumeList(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    android.os.storage.VolumeRecord[] getVolumeRecords(int i) throws android.os.RemoteException;

    android.os.storage.VolumeInfo[] getVolumes(int i) throws android.os.RemoteException;

    boolean isAppIoBlocked(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    boolean isCeStorageUnlocked(int i) throws android.os.RemoteException;

    boolean isObbMounted(java.lang.String str) throws android.os.RemoteException;

    long lastMaintenance() throws android.os.RemoteException;

    void lockCeStorage(int i) throws android.os.RemoteException;

    void mkdirs(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void mount(java.lang.String str) throws android.os.RemoteException;

    void mountObb(java.lang.String str, java.lang.String str2, android.os.storage.IObbActionListener iObbActionListener, int i, android.content.res.ObbInfo obbInfo) throws android.os.RemoteException;

    com.android.internal.os.AppFuseMount mountProxyFileDescriptorBridge() throws android.os.RemoteException;

    boolean needsCheckpoint() throws android.os.RemoteException;

    void notifyAppIoBlocked(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    void notifyAppIoResumed(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor openProxyFileDescriptor(int i, int i2, int i3) throws android.os.RemoteException;

    void partitionMixed(java.lang.String str, int i) throws android.os.RemoteException;

    void partitionPrivate(java.lang.String str) throws android.os.RemoteException;

    void partitionPublic(java.lang.String str) throws android.os.RemoteException;

    void prepareUserStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void registerListener(android.os.storage.IStorageEventListener iStorageEventListener) throws android.os.RemoteException;

    void runIdleMaintenance() throws android.os.RemoteException;

    void runMaintenance() throws android.os.RemoteException;

    void setCeStorageProtection(int i, byte[] bArr) throws android.os.RemoteException;

    void setCloudMediaProvider(java.lang.String str) throws android.os.RemoteException;

    void setDebugFlags(int i, int i2) throws android.os.RemoteException;

    void setPrimaryStorageUuid(java.lang.String str, android.content.pm.IPackageMoveObserver iPackageMoveObserver) throws android.os.RemoteException;

    void setVolumeNickname(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setVolumeUserFlags(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void shutdown(android.os.storage.IStorageShutdownObserver iStorageShutdownObserver) throws android.os.RemoteException;

    void startCheckpoint(int i) throws android.os.RemoteException;

    boolean supportsCheckpoint() throws android.os.RemoteException;

    void unlockCeStorage(int i, byte[] bArr) throws android.os.RemoteException;

    void unmount(java.lang.String str) throws android.os.RemoteException;

    void unmountObb(java.lang.String str, boolean z, android.os.storage.IObbActionListener iObbActionListener, int i) throws android.os.RemoteException;

    void unregisterListener(android.os.storage.IStorageEventListener iStorageEventListener) throws android.os.RemoteException;

    public static class Default implements android.os.storage.IStorageManager {
        @Override // android.os.storage.IStorageManager
        public void registerListener(android.os.storage.IStorageEventListener iStorageEventListener) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void unregisterListener(android.os.storage.IStorageEventListener iStorageEventListener) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void shutdown(android.os.storage.IStorageShutdownObserver iStorageShutdownObserver) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void mountObb(java.lang.String str, java.lang.String str2, android.os.storage.IObbActionListener iObbActionListener, int i, android.content.res.ObbInfo obbInfo) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void unmountObb(java.lang.String str, boolean z, android.os.storage.IObbActionListener iObbActionListener, int i) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public boolean isObbMounted(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public java.lang.String getMountedObbPath(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public android.os.storage.StorageVolume[] getVolumeList(int i, java.lang.String str, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public void mkdirs(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public long lastMaintenance() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public void runMaintenance() throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public android.os.storage.DiskInfo[] getDisks() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public android.os.storage.VolumeInfo[] getVolumes(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public android.os.storage.VolumeRecord[] getVolumeRecords(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public void mount(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void unmount(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void format(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void partitionPublic(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void partitionPrivate(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void partitionMixed(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void setVolumeNickname(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void setVolumeUserFlags(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void forgetVolume(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void forgetAllVolumes() throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public java.lang.String getPrimaryStorageUuid() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public void setPrimaryStorageUuid(java.lang.String str, android.content.pm.IPackageMoveObserver iPackageMoveObserver) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void benchmark(java.lang.String str, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void setDebugFlags(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void createUserStorageKeys(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void destroyUserStorageKeys(int i) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void unlockCeStorage(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void lockCeStorage(int i) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public boolean isCeStorageUnlocked(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public void prepareUserStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void destroyUserStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void setCeStorageProtection(int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void fstrim(int i, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public com.android.internal.os.AppFuseMount mountProxyFileDescriptorBridge() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public android.os.ParcelFileDescriptor openProxyFileDescriptor(int i, int i2, int i3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public long getCacheQuotaBytes(java.lang.String str, int i) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public long getCacheSizeBytes(java.lang.String str, int i) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public long getAllocatableBytes(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public void allocateBytes(java.lang.String str, long j, int i, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void runIdleMaintenance() throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void abortIdleMaintenance() throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void commitChanges() throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public boolean supportsCheckpoint() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public void startCheckpoint(int i) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public boolean needsCheckpoint() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public void abortChanges(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void fixupAppDir(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void disableAppDataIsolation(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public android.app.PendingIntent getManageSpaceActivityIntent(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public void notifyAppIoBlocked(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public void notifyAppIoResumed(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public int getExternalStorageMountMode(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.storage.IStorageManager
        public boolean isAppIoBlocked(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.storage.IStorageManager
        public void setCloudMediaProvider(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.storage.IStorageManager
        public java.lang.String getCloudMediaProvider() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.storage.IStorageManager
        public long getInternalStorageBlockDeviceSize() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.storage.IStorageManager
        public int getInternalStorageRemainingLifetime() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.storage.IStorageManager {
        public static final java.lang.String DESCRIPTOR = "android.os.storage.IStorageManager";
        static final int TRANSACTION_abortChanges = 88;
        static final int TRANSACTION_abortIdleMaintenance = 81;
        static final int TRANSACTION_allocateBytes = 79;
        static final int TRANSACTION_benchmark = 60;
        static final int TRANSACTION_commitChanges = 84;
        static final int TRANSACTION_createUserStorageKeys = 62;
        static final int TRANSACTION_destroyUserStorage = 68;
        static final int TRANSACTION_destroyUserStorageKeys = 63;
        static final int TRANSACTION_disableAppDataIsolation = 91;
        static final int TRANSACTION_fixupAppDir = 90;
        static final int TRANSACTION_forgetAllVolumes = 57;
        static final int TRANSACTION_forgetVolume = 56;
        static final int TRANSACTION_format = 50;
        static final int TRANSACTION_fstrim = 73;
        static final int TRANSACTION_getAllocatableBytes = 78;
        static final int TRANSACTION_getCacheQuotaBytes = 76;
        static final int TRANSACTION_getCacheSizeBytes = 77;
        static final int TRANSACTION_getCloudMediaProvider = 98;
        static final int TRANSACTION_getDisks = 45;
        static final int TRANSACTION_getExternalStorageMountMode = 95;
        static final int TRANSACTION_getInternalStorageBlockDeviceSize = 99;
        static final int TRANSACTION_getInternalStorageRemainingLifetime = 100;
        static final int TRANSACTION_getManageSpaceActivityIntent = 92;
        static final int TRANSACTION_getMountedObbPath = 25;
        static final int TRANSACTION_getPrimaryStorageUuid = 58;
        static final int TRANSACTION_getVolumeList = 30;
        static final int TRANSACTION_getVolumeRecords = 47;
        static final int TRANSACTION_getVolumes = 46;
        static final int TRANSACTION_isAppIoBlocked = 96;
        static final int TRANSACTION_isCeStorageUnlocked = 66;
        static final int TRANSACTION_isObbMounted = 24;
        static final int TRANSACTION_lastMaintenance = 42;
        static final int TRANSACTION_lockCeStorage = 65;
        static final int TRANSACTION_mkdirs = 35;
        static final int TRANSACTION_mount = 48;
        static final int TRANSACTION_mountObb = 22;
        static final int TRANSACTION_mountProxyFileDescriptorBridge = 74;
        static final int TRANSACTION_needsCheckpoint = 87;
        static final int TRANSACTION_notifyAppIoBlocked = 93;
        static final int TRANSACTION_notifyAppIoResumed = 94;
        static final int TRANSACTION_openProxyFileDescriptor = 75;
        static final int TRANSACTION_partitionMixed = 53;
        static final int TRANSACTION_partitionPrivate = 52;
        static final int TRANSACTION_partitionPublic = 51;
        static final int TRANSACTION_prepareUserStorage = 67;
        static final int TRANSACTION_registerListener = 1;
        static final int TRANSACTION_runIdleMaintenance = 80;
        static final int TRANSACTION_runMaintenance = 43;
        static final int TRANSACTION_setCeStorageProtection = 71;
        static final int TRANSACTION_setCloudMediaProvider = 97;
        static final int TRANSACTION_setDebugFlags = 61;
        static final int TRANSACTION_setPrimaryStorageUuid = 59;
        static final int TRANSACTION_setVolumeNickname = 54;
        static final int TRANSACTION_setVolumeUserFlags = 55;
        static final int TRANSACTION_shutdown = 20;
        static final int TRANSACTION_startCheckpoint = 86;
        static final int TRANSACTION_supportsCheckpoint = 85;
        static final int TRANSACTION_unlockCeStorage = 64;
        static final int TRANSACTION_unmount = 49;
        static final int TRANSACTION_unmountObb = 23;
        static final int TRANSACTION_unregisterListener = 2;
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

        public static android.os.storage.IStorageManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.storage.IStorageManager)) {
                return (android.os.storage.IStorageManager) queryLocalInterface;
            }
            return new android.os.storage.IStorageManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerListener";
                case 2:
                    return "unregisterListener";
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 21:
                case 26:
                case 27:
                case 28:
                case 29:
                case 31:
                case 32:
                case 33:
                case 34:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 44:
                case 69:
                case 70:
                case 72:
                case 82:
                case 83:
                case 89:
                default:
                    return null;
                case 20:
                    return "shutdown";
                case 22:
                    return "mountObb";
                case 23:
                    return "unmountObb";
                case 24:
                    return "isObbMounted";
                case 25:
                    return "getMountedObbPath";
                case 30:
                    return "getVolumeList";
                case 35:
                    return "mkdirs";
                case 42:
                    return "lastMaintenance";
                case 43:
                    return "runMaintenance";
                case 45:
                    return "getDisks";
                case 46:
                    return "getVolumes";
                case 47:
                    return "getVolumeRecords";
                case 48:
                    return "mount";
                case 49:
                    return "unmount";
                case 50:
                    return android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT;
                case 51:
                    return "partitionPublic";
                case 52:
                    return "partitionPrivate";
                case 53:
                    return "partitionMixed";
                case 54:
                    return "setVolumeNickname";
                case 55:
                    return "setVolumeUserFlags";
                case 56:
                    return "forgetVolume";
                case 57:
                    return "forgetAllVolumes";
                case 58:
                    return "getPrimaryStorageUuid";
                case 59:
                    return "setPrimaryStorageUuid";
                case 60:
                    return "benchmark";
                case 61:
                    return "setDebugFlags";
                case 62:
                    return "createUserStorageKeys";
                case 63:
                    return "destroyUserStorageKeys";
                case 64:
                    return "unlockCeStorage";
                case 65:
                    return "lockCeStorage";
                case 66:
                    return "isCeStorageUnlocked";
                case 67:
                    return "prepareUserStorage";
                case 68:
                    return "destroyUserStorage";
                case 71:
                    return "setCeStorageProtection";
                case 73:
                    return "fstrim";
                case 74:
                    return "mountProxyFileDescriptorBridge";
                case 75:
                    return "openProxyFileDescriptor";
                case 76:
                    return "getCacheQuotaBytes";
                case 77:
                    return "getCacheSizeBytes";
                case 78:
                    return "getAllocatableBytes";
                case 79:
                    return "allocateBytes";
                case 80:
                    return "runIdleMaintenance";
                case 81:
                    return "abortIdleMaintenance";
                case 84:
                    return "commitChanges";
                case 85:
                    return "supportsCheckpoint";
                case 86:
                    return "startCheckpoint";
                case 87:
                    return "needsCheckpoint";
                case 88:
                    return "abortChanges";
                case 90:
                    return "fixupAppDir";
                case 91:
                    return "disableAppDataIsolation";
                case 92:
                    return "getManageSpaceActivityIntent";
                case 93:
                    return "notifyAppIoBlocked";
                case 94:
                    return "notifyAppIoResumed";
                case 95:
                    return "getExternalStorageMountMode";
                case 96:
                    return "isAppIoBlocked";
                case 97:
                    return "setCloudMediaProvider";
                case 98:
                    return "getCloudMediaProvider";
                case 99:
                    return "getInternalStorageBlockDeviceSize";
                case 100:
                    return "getInternalStorageRemainingLifetime";
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
                    android.os.storage.IStorageEventListener asInterface = android.os.storage.IStorageEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.os.storage.IStorageEventListener asInterface2 = android.os.storage.IStorageEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 21:
                case 26:
                case 27:
                case 28:
                case 29:
                case 31:
                case 32:
                case 33:
                case 34:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 44:
                case 69:
                case 70:
                case 72:
                case 82:
                case 83:
                case 89:
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
                case 20:
                    android.os.storage.IStorageShutdownObserver asInterface3 = android.os.storage.IStorageShutdownObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    shutdown(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    android.os.storage.IObbActionListener asInterface4 = android.os.storage.IObbActionListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    android.content.res.ObbInfo obbInfo = (android.content.res.ObbInfo) parcel.readTypedObject(android.content.res.ObbInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    mountObb(readString, readString2, asInterface4, readInt, obbInfo);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    java.lang.String readString3 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    android.os.storage.IObbActionListener asInterface5 = android.os.storage.IObbActionListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unmountObb(readString3, readBoolean, asInterface5, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isObbMounted = isObbMounted(readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isObbMounted);
                    return true;
                case 25:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String mountedObbPath = getMountedObbPath(readString5);
                    parcel2.writeNoException();
                    parcel2.writeString(mountedObbPath);
                    return true;
                case 30:
                    int readInt3 = parcel.readInt();
                    java.lang.String readString6 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.storage.StorageVolume[] volumeList = getVolumeList(readInt3, readString6, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(volumeList, 1);
                    return true;
                case 35:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    mkdirs(readString7, readString8);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    long lastMaintenance = lastMaintenance();
                    parcel2.writeNoException();
                    parcel2.writeLong(lastMaintenance);
                    return true;
                case 43:
                    runMaintenance();
                    parcel2.writeNoException();
                    return true;
                case 45:
                    android.os.storage.DiskInfo[] disks = getDisks();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(disks, 1);
                    return true;
                case 46:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.storage.VolumeInfo[] volumes = getVolumes(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(volumes, 1);
                    return true;
                case 47:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.storage.VolumeRecord[] volumeRecords = getVolumeRecords(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(volumeRecords, 1);
                    return true;
                case 48:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    mount(readString9);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unmount(readString10);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    format(readString11);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    partitionPublic(readString12);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    partitionPrivate(readString13);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    java.lang.String readString14 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    partitionMixed(readString14, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    java.lang.String readString15 = parcel.readString();
                    java.lang.String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setVolumeNickname(readString15, readString16);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    java.lang.String readString17 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVolumeUserFlags(readString17, readInt8, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    java.lang.String readString18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    forgetVolume(readString18);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    forgetAllVolumes();
                    parcel2.writeNoException();
                    return true;
                case 58:
                    java.lang.String primaryStorageUuid = getPrimaryStorageUuid();
                    parcel2.writeNoException();
                    parcel2.writeString(primaryStorageUuid);
                    return true;
                case 59:
                    java.lang.String readString19 = parcel.readString();
                    android.content.pm.IPackageMoveObserver asInterface6 = android.content.pm.IPackageMoveObserver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setPrimaryStorageUuid(readString19, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    java.lang.String readString20 = parcel.readString();
                    android.os.IVoldTaskListener asInterface7 = android.os.IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    benchmark(readString20, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDebugFlags(readInt10, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 62:
                    int readInt12 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    createUserStorageKeys(readInt12, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyUserStorageKeys(readInt13);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    int readInt14 = parcel.readInt();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    unlockCeStorage(readInt14, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    lockCeStorage(readInt15);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCeStorageUnlocked = isCeStorageUnlocked(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCeStorageUnlocked);
                    return true;
                case 67:
                    java.lang.String readString21 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepareUserStorage(readString21, readInt17, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 68:
                    java.lang.String readString22 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyUserStorage(readString22, readInt19, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 71:
                    int readInt21 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setCeStorageProtection(readInt21, createByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    int readInt22 = parcel.readInt();
                    android.os.IVoldTaskListener asInterface8 = android.os.IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    fstrim(readInt22, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    com.android.internal.os.AppFuseMount mountProxyFileDescriptorBridge = mountProxyFileDescriptorBridge();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mountProxyFileDescriptorBridge, 1);
                    return true;
                case 75:
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor openProxyFileDescriptor = openProxyFileDescriptor(readInt23, readInt24, readInt25);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openProxyFileDescriptor, 1);
                    return true;
                case 76:
                    java.lang.String readString23 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long cacheQuotaBytes = getCacheQuotaBytes(readString23, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeLong(cacheQuotaBytes);
                    return true;
                case 77:
                    java.lang.String readString24 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long cacheSizeBytes = getCacheSizeBytes(readString24, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeLong(cacheSizeBytes);
                    return true;
                case 78:
                    java.lang.String readString25 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    java.lang.String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long allocatableBytes = getAllocatableBytes(readString25, readInt28, readString26);
                    parcel2.writeNoException();
                    parcel2.writeLong(allocatableBytes);
                    return true;
                case 79:
                    java.lang.String readString27 = parcel.readString();
                    long readLong = parcel.readLong();
                    int readInt29 = parcel.readInt();
                    java.lang.String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    allocateBytes(readString27, readLong, readInt29, readString28);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    runIdleMaintenance();
                    parcel2.writeNoException();
                    return true;
                case 81:
                    abortIdleMaintenance();
                    parcel2.writeNoException();
                    return true;
                case 84:
                    commitChanges();
                    parcel2.writeNoException();
                    return true;
                case 85:
                    boolean supportsCheckpoint = supportsCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsCheckpoint);
                    return true;
                case 86:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startCheckpoint(readInt30);
                    parcel2.writeNoException();
                    return true;
                case 87:
                    boolean needsCheckpoint = needsCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(needsCheckpoint);
                    return true;
                case 88:
                    java.lang.String readString29 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    abortChanges(readString29, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 90:
                    java.lang.String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    fixupAppDir(readString30);
                    parcel2.writeNoException();
                    return true;
                case 91:
                    java.lang.String readString31 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableAppDataIsolation(readString31, readInt31, readInt32);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    java.lang.String readString32 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.PendingIntent manageSpaceActivityIntent = getManageSpaceActivityIntent(readString32, readInt33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(manageSpaceActivityIntent, 1);
                    return true;
                case 93:
                    java.lang.String readString33 = parcel.readString();
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAppIoBlocked(readString33, readInt34, readInt35, readInt36);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    java.lang.String readString34 = parcel.readString();
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAppIoResumed(readString34, readInt37, readInt38, readInt39);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    int readInt40 = parcel.readInt();
                    java.lang.String readString35 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int externalStorageMountMode = getExternalStorageMountMode(readInt40, readString35);
                    parcel2.writeNoException();
                    parcel2.writeInt(externalStorageMountMode);
                    return true;
                case 96:
                    java.lang.String readString36 = parcel.readString();
                    int readInt41 = parcel.readInt();
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAppIoBlocked = isAppIoBlocked(readString36, readInt41, readInt42, readInt43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppIoBlocked);
                    return true;
                case 97:
                    java.lang.String readString37 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCloudMediaProvider(readString37);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    java.lang.String cloudMediaProvider = getCloudMediaProvider();
                    parcel2.writeNoException();
                    parcel2.writeString(cloudMediaProvider);
                    return true;
                case 99:
                    long internalStorageBlockDeviceSize = getInternalStorageBlockDeviceSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(internalStorageBlockDeviceSize);
                    return true;
                case 100:
                    int internalStorageRemainingLifetime = getInternalStorageRemainingLifetime();
                    parcel2.writeNoException();
                    parcel2.writeInt(internalStorageRemainingLifetime);
                    return true;
            }
        }

        private static class Proxy implements android.os.storage.IStorageManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.storage.IStorageManager.Stub.DESCRIPTOR;
            }

            @Override // android.os.storage.IStorageManager
            public void registerListener(android.os.storage.IStorageEventListener iStorageEventListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStorageEventListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unregisterListener(android.os.storage.IStorageEventListener iStorageEventListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStorageEventListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void shutdown(android.os.storage.IStorageShutdownObserver iStorageShutdownObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iStorageShutdownObserver);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mountObb(java.lang.String str, java.lang.String str2, android.os.storage.IObbActionListener iObbActionListener, int i, android.content.res.ObbInfo obbInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iObbActionListener);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(obbInfo, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unmountObb(java.lang.String str, boolean z, android.os.storage.IObbActionListener iObbActionListener, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iObbActionListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isObbMounted(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public java.lang.String getMountedObbPath(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public android.os.storage.StorageVolume[] getVolumeList(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.storage.StorageVolume[]) obtain2.createTypedArray(android.os.storage.StorageVolume.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mkdirs(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long lastMaintenance() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void runMaintenance() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public android.os.storage.DiskInfo[] getDisks() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.storage.DiskInfo[]) obtain2.createTypedArray(android.os.storage.DiskInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public android.os.storage.VolumeInfo[] getVolumes(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.storage.VolumeInfo[]) obtain2.createTypedArray(android.os.storage.VolumeInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public android.os.storage.VolumeRecord[] getVolumeRecords(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.storage.VolumeRecord[]) obtain2.createTypedArray(android.os.storage.VolumeRecord.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void mount(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unmount(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void format(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void partitionPublic(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void partitionPrivate(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void partitionMixed(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setVolumeNickname(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setVolumeUserFlags(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void forgetVolume(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void forgetAllVolumes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public java.lang.String getPrimaryStorageUuid() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setPrimaryStorageUuid(java.lang.String str, android.content.pm.IPackageMoveObserver iPackageMoveObserver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPackageMoveObserver);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void benchmark(java.lang.String str, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setDebugFlags(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void createUserStorageKeys(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void destroyUserStorageKeys(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void unlockCeStorage(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void lockCeStorage(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isCeStorageUnlocked(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void prepareUserStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void destroyUserStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setCeStorageProtection(int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void fstrim(int i, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public com.android.internal.os.AppFuseMount mountProxyFileDescriptorBridge() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                    return (com.android.internal.os.AppFuseMount) obtain2.readTypedObject(com.android.internal.os.AppFuseMount.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public android.os.ParcelFileDescriptor openProxyFileDescriptor(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getCacheQuotaBytes(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getCacheSizeBytes(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getAllocatableBytes(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void allocateBytes(java.lang.String str, long j, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void runIdleMaintenance() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void abortIdleMaintenance() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void commitChanges() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean supportsCheckpoint() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void startCheckpoint(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean needsCheckpoint() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void abortChanges(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void fixupAppDir(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void disableAppDataIsolation(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public android.app.PendingIntent getManageSpaceActivityIntent(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.PendingIntent) obtain2.readTypedObject(android.app.PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void notifyAppIoBlocked(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void notifyAppIoResumed(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int getExternalStorageMountMode(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public boolean isAppIoBlocked(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public void setCloudMediaProvider(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public java.lang.String getCloudMediaProvider() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public long getInternalStorageBlockDeviceSize() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.storage.IStorageManager
            public int getInternalStorageRemainingLifetime() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.storage.IStorageManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void shutdown_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.SHUTDOWN, getCallingPid(), getCallingUid());
        }

        protected void runMaintenance_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void mount_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void unmount_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void format_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void partitionPublic_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void partitionPrivate_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void partitionMixed_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void setVolumeNickname_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void setVolumeUserFlags_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void forgetVolume_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void forgetAllVolumes_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void setPrimaryStorageUuid_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void benchmark_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void setDebugFlags_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void createUserStorageKeys_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void destroyUserStorageKeys_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void unlockCeStorage_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void lockCeStorage_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void prepareUserStorage_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void destroyUserStorage_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void setCeStorageProtection_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.STORAGE_INTERNAL, getCallingPid(), getCallingUid());
        }

        protected void fstrim_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void needsCheckpoint_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.MOUNT_FORMAT_FILESYSTEMS, getCallingPid(), getCallingUid());
        }

        protected void getExternalStorageMountMode_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.WRITE_MEDIA_STORAGE, getCallingPid(), getCallingUid());
        }

        protected void getInternalStorageRemainingLifetime_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.READ_PRIVILEGED_PHONE_STATE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 99;
        }
    }
}
