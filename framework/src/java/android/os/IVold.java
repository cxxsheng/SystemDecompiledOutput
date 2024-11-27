package android.os;

/* loaded from: classes3.dex */
public interface IVold extends android.os.IInterface {
    public static final int FSTRIM_FLAG_DEEP_TRIM = 1;
    public static final int MOUNT_FLAG_PRIMARY = 1;
    public static final int MOUNT_FLAG_VISIBLE_FOR_READ = 2;
    public static final int MOUNT_FLAG_VISIBLE_FOR_WRITE = 4;
    public static final int PARTITION_TYPE_MIXED = 2;
    public static final int PARTITION_TYPE_PRIVATE = 1;
    public static final int PARTITION_TYPE_PUBLIC = 0;
    public static final int REMOUNT_MODE_ANDROID_WRITABLE = 4;
    public static final int REMOUNT_MODE_DEFAULT = 1;
    public static final int REMOUNT_MODE_INSTALLER = 2;
    public static final int REMOUNT_MODE_NONE = 0;
    public static final int REMOUNT_MODE_PASS_THROUGH = 3;
    public static final int STORAGE_FLAG_CE = 2;
    public static final int STORAGE_FLAG_DE = 1;
    public static final int VOLUME_STATE_BAD_REMOVAL = 8;
    public static final int VOLUME_STATE_CHECKING = 1;
    public static final int VOLUME_STATE_EJECTING = 5;
    public static final int VOLUME_STATE_FORMATTING = 4;
    public static final int VOLUME_STATE_MOUNTED = 2;
    public static final int VOLUME_STATE_MOUNTED_READ_ONLY = 3;
    public static final int VOLUME_STATE_REMOVED = 7;
    public static final int VOLUME_STATE_UNMOUNTABLE = 6;
    public static final int VOLUME_STATE_UNMOUNTED = 0;
    public static final int VOLUME_TYPE_ASEC = 3;
    public static final int VOLUME_TYPE_EMULATED = 2;
    public static final int VOLUME_TYPE_OBB = 4;
    public static final int VOLUME_TYPE_PRIVATE = 1;
    public static final int VOLUME_TYPE_PUBLIC = 0;
    public static final int VOLUME_TYPE_STUB = 5;

    void abortChanges(java.lang.String str, boolean z) throws android.os.RemoteException;

    void abortFuse() throws android.os.RemoteException;

    void abortIdleMaint(android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException;

    void addAppIds(java.lang.String[] strArr, int[] iArr) throws android.os.RemoteException;

    void addSandboxIds(int[] iArr, java.lang.String[] strArr) throws android.os.RemoteException;

    void benchmark(java.lang.String str, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException;

    void bindMount(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void commitChanges() throws android.os.RemoteException;

    java.lang.String createObb(java.lang.String str, int i) throws android.os.RemoteException;

    java.lang.String createStubVolume(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, int i) throws android.os.RemoteException;

    void createUserStorageKeys(int i, boolean z) throws android.os.RemoteException;

    void destroyDsuMetadataKey(java.lang.String str) throws android.os.RemoteException;

    void destroyObb(java.lang.String str) throws android.os.RemoteException;

    void destroySandboxForApp(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void destroyStubVolume(java.lang.String str) throws android.os.RemoteException;

    void destroyUserStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void destroyUserStorageKeys(int i) throws android.os.RemoteException;

    void earlyBootEnded() throws android.os.RemoteException;

    void encryptFstab(java.lang.String str, java.lang.String str2, boolean z, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException;

    void ensureAppDirsCreated(java.lang.String[] strArr, int i) throws android.os.RemoteException;

    void fbeEnable() throws android.os.RemoteException;

    void fixupAppDir(java.lang.String str, int i) throws android.os.RemoteException;

    void forgetPartition(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void format(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void fstrim(int i, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException;

    int getStorageLifeTime() throws android.os.RemoteException;

    int getStorageRemainingLifetime() throws android.os.RemoteException;

    long getStorageSize() throws android.os.RemoteException;

    int[] getUnlockedUsers() throws android.os.RemoteException;

    int getWriteAmount() throws android.os.RemoteException;

    boolean incFsEnabled() throws android.os.RemoteException;

    void initUser0() throws android.os.RemoteException;

    boolean isCheckpointing() throws android.os.RemoteException;

    void lockCeStorage(int i) throws android.os.RemoteException;

    void markBootAttempt() throws android.os.RemoteException;

    void monitor() throws android.os.RemoteException;

    void mount(java.lang.String str, int i, int i2, android.os.IVoldMountCallback iVoldMountCallback) throws android.os.RemoteException;

    java.io.FileDescriptor mountAppFuse(int i, int i2) throws android.os.RemoteException;

    void mountFstab(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    android.os.incremental.IncrementalFileSystemControlParcel mountIncFs(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) throws android.os.RemoteException;

    void moveStorage(java.lang.String str, java.lang.String str2, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException;

    boolean needsCheckpoint() throws android.os.RemoteException;

    boolean needsRollback() throws android.os.RemoteException;

    void onSecureKeyguardStateChanged(boolean z) throws android.os.RemoteException;

    void onUserAdded(int i, int i2, int i3) throws android.os.RemoteException;

    void onUserRemoved(int i) throws android.os.RemoteException;

    void onUserStarted(int i) throws android.os.RemoteException;

    void onUserStopped(int i) throws android.os.RemoteException;

    java.io.FileDescriptor openAppFuseFile(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void partition(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void prepareCheckpoint() throws android.os.RemoteException;

    void prepareSandboxForApp(java.lang.String str, int i, java.lang.String str2, int i2) throws android.os.RemoteException;

    void prepareUserStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void refreshLatestWrite() throws android.os.RemoteException;

    void remountAppStorageDirs(int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException;

    void remountUid(int i, int i2) throws android.os.RemoteException;

    void reset() throws android.os.RemoteException;

    void resetCheckpoint() throws android.os.RemoteException;

    void restoreCheckpoint(java.lang.String str) throws android.os.RemoteException;

    void restoreCheckpointPart(java.lang.String str, int i) throws android.os.RemoteException;

    void runIdleMaint(boolean z, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException;

    void setCeStorageProtection(int i, java.lang.String str) throws android.os.RemoteException;

    void setGCUrgentPace(int i, int i2, float f, float f2, int i3, int i4, int i5) throws android.os.RemoteException;

    void setIncFsMountOptions(android.os.incremental.IncrementalFileSystemControlParcel incrementalFileSystemControlParcel, boolean z, boolean z2, java.lang.String str) throws android.os.RemoteException;

    void setListener(android.os.IVoldListener iVoldListener) throws android.os.RemoteException;

    void setStorageBindingSeed(byte[] bArr) throws android.os.RemoteException;

    void setupAppDir(java.lang.String str, int i) throws android.os.RemoteException;

    void shutdown() throws android.os.RemoteException;

    void startCheckpoint(int i) throws android.os.RemoteException;

    boolean supportsBlockCheckpoint() throws android.os.RemoteException;

    boolean supportsCheckpoint() throws android.os.RemoteException;

    boolean supportsFileCheckpoint() throws android.os.RemoteException;

    void unlockCeStorage(int i, java.lang.String str) throws android.os.RemoteException;

    void unmount(java.lang.String str) throws android.os.RemoteException;

    void unmountAppFuse(int i, int i2) throws android.os.RemoteException;

    void unmountAppStorageDirs(int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException;

    void unmountIncFs(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.os.IVold {
        @Override // android.os.IVold
        public void setListener(android.os.IVoldListener iVoldListener) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void abortFuse() throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void monitor() throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void reset() throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void shutdown() throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void onUserAdded(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void onUserRemoved(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void onUserStarted(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void onUserStopped(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void addAppIds(java.lang.String[] strArr, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void addSandboxIds(int[] iArr, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void onSecureKeyguardStateChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void partition(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void forgetPartition(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void mount(java.lang.String str, int i, int i2, android.os.IVoldMountCallback iVoldMountCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void unmount(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void format(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void benchmark(java.lang.String str, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void moveStorage(java.lang.String str, java.lang.String str2, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void remountUid(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void remountAppStorageDirs(int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void unmountAppStorageDirs(int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void setupAppDir(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void fixupAppDir(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void ensureAppDirsCreated(java.lang.String[] strArr, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public java.lang.String createObb(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public void destroyObb(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void fstrim(int i, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void runIdleMaint(boolean z, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void abortIdleMaint(android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public int getStorageLifeTime() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public void setGCUrgentPace(int i, int i2, float f, float f2, int i3, int i4, int i5) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void refreshLatestWrite() throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public int getWriteAmount() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IVold
        public java.io.FileDescriptor mountAppFuse(int i, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public void unmountAppFuse(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void fbeEnable() throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void initUser0() throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void mountFstab(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void encryptFstab(java.lang.String str, java.lang.String str2, boolean z, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void setStorageBindingSeed(byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void createUserStorageKeys(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void destroyUserStorageKeys(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void setCeStorageProtection(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public int[] getUnlockedUsers() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public void unlockCeStorage(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void lockCeStorage(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void prepareUserStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void destroyUserStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void prepareSandboxForApp(java.lang.String str, int i, java.lang.String str2, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void destroySandboxForApp(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void startCheckpoint(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public boolean needsCheckpoint() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean needsRollback() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean isCheckpointing() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public void abortChanges(java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void commitChanges() throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void prepareCheckpoint() throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void restoreCheckpoint(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void restoreCheckpointPart(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void markBootAttempt() throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public boolean supportsCheckpoint() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean supportsBlockCheckpoint() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public boolean supportsFileCheckpoint() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public void resetCheckpoint() throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void earlyBootEnded() throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public java.lang.String createStubVolume(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public void destroyStubVolume(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public java.io.FileDescriptor openAppFuseFile(int i, int i2, int i3, int i4) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public boolean incFsEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IVold
        public android.os.incremental.IncrementalFileSystemControlParcel mountIncFs(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IVold
        public void unmountIncFs(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void setIncFsMountOptions(android.os.incremental.IncrementalFileSystemControlParcel incrementalFileSystemControlParcel, boolean z, boolean z2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void bindMount(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public void destroyDsuMetadataKey(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IVold
        public long getStorageSize() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.IVold
        public int getStorageRemainingLifetime() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IVold {
        public static final java.lang.String DESCRIPTOR = "android.os.IVold";
        static final int TRANSACTION_abortChanges = 56;
        static final int TRANSACTION_abortFuse = 2;
        static final int TRANSACTION_abortIdleMaint = 30;
        static final int TRANSACTION_addAppIds = 10;
        static final int TRANSACTION_addSandboxIds = 11;
        static final int TRANSACTION_benchmark = 18;
        static final int TRANSACTION_bindMount = 74;
        static final int TRANSACTION_commitChanges = 57;
        static final int TRANSACTION_createObb = 26;
        static final int TRANSACTION_createStubVolume = 67;
        static final int TRANSACTION_createUserStorageKeys = 42;
        static final int TRANSACTION_destroyDsuMetadataKey = 75;
        static final int TRANSACTION_destroyObb = 27;
        static final int TRANSACTION_destroySandboxForApp = 51;
        static final int TRANSACTION_destroyStubVolume = 68;
        static final int TRANSACTION_destroyUserStorage = 49;
        static final int TRANSACTION_destroyUserStorageKeys = 43;
        static final int TRANSACTION_earlyBootEnded = 66;
        static final int TRANSACTION_encryptFstab = 40;
        static final int TRANSACTION_ensureAppDirsCreated = 25;
        static final int TRANSACTION_fbeEnable = 37;
        static final int TRANSACTION_fixupAppDir = 24;
        static final int TRANSACTION_forgetPartition = 14;
        static final int TRANSACTION_format = 17;
        static final int TRANSACTION_fstrim = 28;
        static final int TRANSACTION_getStorageLifeTime = 31;
        static final int TRANSACTION_getStorageRemainingLifetime = 77;
        static final int TRANSACTION_getStorageSize = 76;
        static final int TRANSACTION_getUnlockedUsers = 45;
        static final int TRANSACTION_getWriteAmount = 34;
        static final int TRANSACTION_incFsEnabled = 70;
        static final int TRANSACTION_initUser0 = 38;
        static final int TRANSACTION_isCheckpointing = 55;
        static final int TRANSACTION_lockCeStorage = 47;
        static final int TRANSACTION_markBootAttempt = 61;
        static final int TRANSACTION_monitor = 3;
        static final int TRANSACTION_mount = 15;
        static final int TRANSACTION_mountAppFuse = 35;
        static final int TRANSACTION_mountFstab = 39;
        static final int TRANSACTION_mountIncFs = 71;
        static final int TRANSACTION_moveStorage = 19;
        static final int TRANSACTION_needsCheckpoint = 53;
        static final int TRANSACTION_needsRollback = 54;
        static final int TRANSACTION_onSecureKeyguardStateChanged = 12;
        static final int TRANSACTION_onUserAdded = 6;
        static final int TRANSACTION_onUserRemoved = 7;
        static final int TRANSACTION_onUserStarted = 8;
        static final int TRANSACTION_onUserStopped = 9;
        static final int TRANSACTION_openAppFuseFile = 69;
        static final int TRANSACTION_partition = 13;
        static final int TRANSACTION_prepareCheckpoint = 58;
        static final int TRANSACTION_prepareSandboxForApp = 50;
        static final int TRANSACTION_prepareUserStorage = 48;
        static final int TRANSACTION_refreshLatestWrite = 33;
        static final int TRANSACTION_remountAppStorageDirs = 21;
        static final int TRANSACTION_remountUid = 20;
        static final int TRANSACTION_reset = 4;
        static final int TRANSACTION_resetCheckpoint = 65;
        static final int TRANSACTION_restoreCheckpoint = 59;
        static final int TRANSACTION_restoreCheckpointPart = 60;
        static final int TRANSACTION_runIdleMaint = 29;
        static final int TRANSACTION_setCeStorageProtection = 44;
        static final int TRANSACTION_setGCUrgentPace = 32;
        static final int TRANSACTION_setIncFsMountOptions = 73;
        static final int TRANSACTION_setListener = 1;
        static final int TRANSACTION_setStorageBindingSeed = 41;
        static final int TRANSACTION_setupAppDir = 23;
        static final int TRANSACTION_shutdown = 5;
        static final int TRANSACTION_startCheckpoint = 52;
        static final int TRANSACTION_supportsBlockCheckpoint = 63;
        static final int TRANSACTION_supportsCheckpoint = 62;
        static final int TRANSACTION_supportsFileCheckpoint = 64;
        static final int TRANSACTION_unlockCeStorage = 46;
        static final int TRANSACTION_unmount = 16;
        static final int TRANSACTION_unmountAppFuse = 36;
        static final int TRANSACTION_unmountAppStorageDirs = 22;
        static final int TRANSACTION_unmountIncFs = 72;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.os.IVold asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IVold)) {
                return (android.os.IVold) queryLocalInterface;
            }
            return new android.os.IVold.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setListener";
                case 2:
                    return "abortFuse";
                case 3:
                    return "monitor";
                case 4:
                    return "reset";
                case 5:
                    return "shutdown";
                case 6:
                    return "onUserAdded";
                case 7:
                    return "onUserRemoved";
                case 8:
                    return "onUserStarted";
                case 9:
                    return "onUserStopped";
                case 10:
                    return "addAppIds";
                case 11:
                    return "addSandboxIds";
                case 12:
                    return "onSecureKeyguardStateChanged";
                case 13:
                    return "partition";
                case 14:
                    return "forgetPartition";
                case 15:
                    return "mount";
                case 16:
                    return "unmount";
                case 17:
                    return android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT;
                case 18:
                    return "benchmark";
                case 19:
                    return "moveStorage";
                case 20:
                    return "remountUid";
                case 21:
                    return "remountAppStorageDirs";
                case 22:
                    return "unmountAppStorageDirs";
                case 23:
                    return "setupAppDir";
                case 24:
                    return "fixupAppDir";
                case 25:
                    return "ensureAppDirsCreated";
                case 26:
                    return "createObb";
                case 27:
                    return "destroyObb";
                case 28:
                    return "fstrim";
                case 29:
                    return "runIdleMaint";
                case 30:
                    return "abortIdleMaint";
                case 31:
                    return "getStorageLifeTime";
                case 32:
                    return "setGCUrgentPace";
                case 33:
                    return "refreshLatestWrite";
                case 34:
                    return "getWriteAmount";
                case 35:
                    return "mountAppFuse";
                case 36:
                    return "unmountAppFuse";
                case 37:
                    return "fbeEnable";
                case 38:
                    return "initUser0";
                case 39:
                    return "mountFstab";
                case 40:
                    return "encryptFstab";
                case 41:
                    return "setStorageBindingSeed";
                case 42:
                    return "createUserStorageKeys";
                case 43:
                    return "destroyUserStorageKeys";
                case 44:
                    return "setCeStorageProtection";
                case 45:
                    return "getUnlockedUsers";
                case 46:
                    return "unlockCeStorage";
                case 47:
                    return "lockCeStorage";
                case 48:
                    return "prepareUserStorage";
                case 49:
                    return "destroyUserStorage";
                case 50:
                    return "prepareSandboxForApp";
                case 51:
                    return "destroySandboxForApp";
                case 52:
                    return "startCheckpoint";
                case 53:
                    return "needsCheckpoint";
                case 54:
                    return "needsRollback";
                case 55:
                    return "isCheckpointing";
                case 56:
                    return "abortChanges";
                case 57:
                    return "commitChanges";
                case 58:
                    return "prepareCheckpoint";
                case 59:
                    return "restoreCheckpoint";
                case 60:
                    return "restoreCheckpointPart";
                case 61:
                    return "markBootAttempt";
                case 62:
                    return "supportsCheckpoint";
                case 63:
                    return "supportsBlockCheckpoint";
                case 64:
                    return "supportsFileCheckpoint";
                case 65:
                    return "resetCheckpoint";
                case 66:
                    return "earlyBootEnded";
                case 67:
                    return "createStubVolume";
                case 68:
                    return "destroyStubVolume";
                case 69:
                    return "openAppFuseFile";
                case 70:
                    return "incFsEnabled";
                case 71:
                    return "mountIncFs";
                case 72:
                    return "unmountIncFs";
                case 73:
                    return "setIncFsMountOptions";
                case 74:
                    return "bindMount";
                case 75:
                    return "destroyDsuMetadataKey";
                case 76:
                    return "getStorageSize";
                case 77:
                    return "getStorageRemainingLifetime";
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
                    android.os.IVoldListener asInterface = android.os.IVoldListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    abortFuse();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    monitor();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    reset();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    shutdown();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserAdded(readInt, readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserRemoved(readInt4);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserStarted(readInt5);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUserStopped(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    addAppIds(createStringArray, createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int[] createIntArray2 = parcel.createIntArray();
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    addSandboxIds(createIntArray2, createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onSecureKeyguardStateChanged(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    java.lang.String readString = parcel.readString();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    partition(readString, readInt7, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    forgetPartition(readString2, readString3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    java.lang.String readString4 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    android.os.IVoldMountCallback asInterface2 = android.os.IVoldMountCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    mount(readString4, readInt9, readInt10, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unmount(readString5);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    format(readString6, readString7);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    java.lang.String readString8 = parcel.readString();
                    android.os.IVoldTaskListener asInterface3 = android.os.IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    benchmark(readString8, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    java.lang.String readString9 = parcel.readString();
                    java.lang.String readString10 = parcel.readString();
                    android.os.IVoldTaskListener asInterface4 = android.os.IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    moveStorage(readString9, readString10, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    remountUid(readInt11, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    remountAppStorageDirs(readInt13, readInt14, createStringArray3);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    java.lang.String[] createStringArray4 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    unmountAppStorageDirs(readInt15, readInt16, createStringArray4);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    java.lang.String readString11 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setupAppDir(readString11, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    java.lang.String readString12 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    fixupAppDir(readString12, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    java.lang.String[] createStringArray5 = parcel.createStringArray();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ensureAppDirsCreated(createStringArray5, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    java.lang.String readString13 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String createObb = createObb(readString13, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeString(createObb);
                    return true;
                case 27:
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyObb(readString14);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt21 = parcel.readInt();
                    android.os.IVoldTaskListener asInterface5 = android.os.IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    fstrim(readInt21, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    boolean readBoolean2 = parcel.readBoolean();
                    android.os.IVoldTaskListener asInterface6 = android.os.IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    runIdleMaint(readBoolean2, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    android.os.IVoldTaskListener asInterface7 = android.os.IVoldTaskListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    abortIdleMaint(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int storageLifeTime = getStorageLifeTime();
                    parcel2.writeNoException();
                    parcel2.writeInt(storageLifeTime);
                    return true;
                case 32:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    float readFloat2 = parcel.readFloat();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setGCUrgentPace(readInt22, readInt23, readFloat, readFloat2, readInt24, readInt25, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    refreshLatestWrite();
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int writeAmount = getWriteAmount();
                    parcel2.writeNoException();
                    parcel2.writeInt(writeAmount);
                    return true;
                case 35:
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.io.FileDescriptor mountAppFuse = mountAppFuse(readInt27, readInt28);
                    parcel2.writeNoException();
                    parcel2.writeRawFileDescriptor(mountAppFuse);
                    return true;
                case 36:
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unmountAppFuse(readInt29, readInt30);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    fbeEnable();
                    parcel2.writeNoException();
                    return true;
                case 38:
                    initUser0();
                    parcel2.writeNoException();
                    return true;
                case 39:
                    java.lang.String readString15 = parcel.readString();
                    java.lang.String readString16 = parcel.readString();
                    java.lang.String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    mountFstab(readString15, readString16, readString17);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    java.lang.String readString18 = parcel.readString();
                    java.lang.String readString19 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    java.lang.String readString20 = parcel.readString();
                    java.lang.String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    encryptFstab(readString18, readString19, readBoolean3, readString20, readString21);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    setStorageBindingSeed(createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int readInt31 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    createUserStorageKeys(readInt31, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyUserStorageKeys(readInt32);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int readInt33 = parcel.readInt();
                    java.lang.String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setCeStorageProtection(readInt33, readString22);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int[] unlockedUsers = getUnlockedUsers();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(unlockedUsers);
                    return true;
                case 46:
                    int readInt34 = parcel.readInt();
                    java.lang.String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unlockCeStorage(readInt34, readString23);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    lockCeStorage(readInt35);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    java.lang.String readString24 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepareUserStorage(readString24, readInt36, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    java.lang.String readString25 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyUserStorage(readString25, readInt38, readInt39);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    java.lang.String readString26 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    java.lang.String readString27 = parcel.readString();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    prepareSandboxForApp(readString26, readInt40, readString27, readInt41);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    java.lang.String readString28 = parcel.readString();
                    java.lang.String readString29 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroySandboxForApp(readString28, readString29, readInt42);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startCheckpoint(readInt43);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    boolean needsCheckpoint = needsCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(needsCheckpoint);
                    return true;
                case 54:
                    boolean needsRollback = needsRollback();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(needsRollback);
                    return true;
                case 55:
                    boolean isCheckpointing = isCheckpointing();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCheckpointing);
                    return true;
                case 56:
                    java.lang.String readString30 = parcel.readString();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    abortChanges(readString30, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    commitChanges();
                    parcel2.writeNoException();
                    return true;
                case 58:
                    prepareCheckpoint();
                    parcel2.writeNoException();
                    return true;
                case 59:
                    java.lang.String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    restoreCheckpoint(readString31);
                    parcel2.writeNoException();
                    return true;
                case 60:
                    java.lang.String readString32 = parcel.readString();
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreCheckpointPart(readString32, readInt44);
                    parcel2.writeNoException();
                    return true;
                case 61:
                    markBootAttempt();
                    parcel2.writeNoException();
                    return true;
                case 62:
                    boolean supportsCheckpoint = supportsCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsCheckpoint);
                    return true;
                case 63:
                    boolean supportsBlockCheckpoint = supportsBlockCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsBlockCheckpoint);
                    return true;
                case 64:
                    boolean supportsFileCheckpoint = supportsFileCheckpoint();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsFileCheckpoint);
                    return true;
                case 65:
                    resetCheckpoint();
                    parcel2.writeNoException();
                    return true;
                case 66:
                    earlyBootEnded();
                    parcel2.writeNoException();
                    return true;
                case 67:
                    java.lang.String readString33 = parcel.readString();
                    java.lang.String readString34 = parcel.readString();
                    java.lang.String readString35 = parcel.readString();
                    java.lang.String readString36 = parcel.readString();
                    java.lang.String readString37 = parcel.readString();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String createStubVolume = createStubVolume(readString33, readString34, readString35, readString36, readString37, readInt45);
                    parcel2.writeNoException();
                    parcel2.writeString(createStubVolume);
                    return true;
                case 68:
                    java.lang.String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyStubVolume(readString38);
                    parcel2.writeNoException();
                    return true;
                case 69:
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.io.FileDescriptor openAppFuseFile = openAppFuseFile(readInt46, readInt47, readInt48, readInt49);
                    parcel2.writeNoException();
                    parcel2.writeRawFileDescriptor(openAppFuseFile);
                    return true;
                case 70:
                    boolean incFsEnabled = incFsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(incFsEnabled);
                    return true;
                case 71:
                    java.lang.String readString39 = parcel.readString();
                    java.lang.String readString40 = parcel.readString();
                    int readInt50 = parcel.readInt();
                    java.lang.String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.incremental.IncrementalFileSystemControlParcel mountIncFs = mountIncFs(readString39, readString40, readInt50, readString41);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mountIncFs, 1);
                    return true;
                case 72:
                    java.lang.String readString42 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unmountIncFs(readString42);
                    parcel2.writeNoException();
                    return true;
                case 73:
                    android.os.incremental.IncrementalFileSystemControlParcel incrementalFileSystemControlParcel = (android.os.incremental.IncrementalFileSystemControlParcel) parcel.readTypedObject(android.os.incremental.IncrementalFileSystemControlParcel.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    boolean readBoolean7 = parcel.readBoolean();
                    java.lang.String readString43 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setIncFsMountOptions(incrementalFileSystemControlParcel, readBoolean6, readBoolean7, readString43);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    java.lang.String readString44 = parcel.readString();
                    java.lang.String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    bindMount(readString44, readString45);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    java.lang.String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyDsuMetadataKey(readString46);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    long storageSize = getStorageSize();
                    parcel2.writeNoException();
                    parcel2.writeLong(storageSize);
                    return true;
                case 77:
                    int storageRemainingLifetime = getStorageRemainingLifetime();
                    parcel2.writeNoException();
                    parcel2.writeInt(storageRemainingLifetime);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IVold {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IVold.Stub.DESCRIPTOR;
            }

            @Override // android.os.IVold
            public void setListener(android.os.IVoldListener iVoldListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoldListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void abortFuse() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void monitor() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void reset() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void shutdown() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onUserAdded(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onUserRemoved(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onUserStarted(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onUserStopped(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void addAppIds(java.lang.String[] strArr, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void addSandboxIds(int[] iArr, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void onSecureKeyguardStateChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void partition(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void forgetPartition(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void mount(java.lang.String str, int i, int i2, android.os.IVoldMountCallback iVoldMountCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iVoldMountCallback);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unmount(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void format(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void benchmark(java.lang.String str, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void moveStorage(java.lang.String str, java.lang.String str2, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void remountUid(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void remountAppStorageDirs(int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unmountAppStorageDirs(int i, int i2, java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setupAppDir(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void fixupAppDir(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void ensureAppDirsCreated(java.lang.String[] strArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public java.lang.String createObb(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyObb(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void fstrim(int i, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void runIdleMaint(boolean z, android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void abortIdleMaint(android.os.IVoldTaskListener iVoldTaskListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iVoldTaskListener);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getStorageLifeTime() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setGCUrgentPace(int i, int i2, float f, float f2, int i3, int i4, int i5) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeFloat(f);
                    obtain.writeFloat(f2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void refreshLatestWrite() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getWriteAmount() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public java.io.FileDescriptor mountAppFuse(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readRawFileDescriptor();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unmountAppFuse(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void fbeEnable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void initUser0() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void mountFstab(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void encryptFstab(java.lang.String str, java.lang.String str2, boolean z, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setStorageBindingSeed(byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void createUserStorageKeys(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyUserStorageKeys(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setCeStorageProtection(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int[] getUnlockedUsers() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unlockCeStorage(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void lockCeStorage(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void prepareUserStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyUserStorage(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void prepareSandboxForApp(java.lang.String str, int i, java.lang.String str2, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroySandboxForApp(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void startCheckpoint(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean needsCheckpoint() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean needsRollback() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean isCheckpointing() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void abortChanges(java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void commitChanges() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void prepareCheckpoint() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void restoreCheckpoint(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void restoreCheckpointPart(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void markBootAttempt() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean supportsCheckpoint() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean supportsBlockCheckpoint() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean supportsFileCheckpoint() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void resetCheckpoint() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void earlyBootEnded() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public java.lang.String createStubVolume(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeInt(i);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyStubVolume(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public java.io.FileDescriptor openAppFuseFile(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readRawFileDescriptor();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public boolean incFsEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public android.os.incremental.IncrementalFileSystemControlParcel mountIncFs(java.lang.String str, java.lang.String str2, int i, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.incremental.IncrementalFileSystemControlParcel) obtain2.readTypedObject(android.os.incremental.IncrementalFileSystemControlParcel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void unmountIncFs(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void setIncFsMountOptions(android.os.incremental.IncrementalFileSystemControlParcel incrementalFileSystemControlParcel, boolean z, boolean z2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(incrementalFileSystemControlParcel, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeString(str);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void bindMount(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public void destroyDsuMetadataKey(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public long getStorageSize() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IVold
            public int getStorageRemainingLifetime() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IVold.Stub.DESCRIPTOR);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 76;
        }
    }
}
