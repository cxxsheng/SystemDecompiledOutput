package android.os;

/* loaded from: classes.dex */
public interface IInstalld extends android.os.IInterface {
    public static final int FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES = 131072;
    public static final int FLAG_CLEAR_CACHE_ONLY = 16;
    public static final int FLAG_CLEAR_CODE_CACHE_ONLY = 32;
    public static final int FLAG_FORCE = 8192;
    public static final int FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES = 2048;
    public static final int FLAG_FREE_CACHE_NOOP = 1024;
    public static final int FLAG_FREE_CACHE_V2 = 256;
    public static final int FLAG_FREE_CACHE_V2_DEFY_QUOTA = 512;
    public static final int FLAG_STORAGE_CE = 2;
    public static final int FLAG_STORAGE_DE = 1;
    public static final int FLAG_STORAGE_EXTERNAL = 4;
    public static final int FLAG_STORAGE_SDK = 8;
    public static final int FLAG_USE_QUOTA = 4096;

    void cleanupInvalidPackageDirs(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void clearAppData(java.lang.String str, java.lang.String str2, int i, int i2, long j) throws android.os.RemoteException;

    void clearAppProfiles(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void controlDexOptBlocking(boolean z) throws android.os.RemoteException;

    boolean copySystemProfile(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    android.os.CreateAppDataResult createAppData(android.os.CreateAppDataArgs createAppDataArgs) throws android.os.RemoteException;

    android.os.CreateAppDataResult[] createAppDataBatched(android.os.CreateAppDataArgs[] createAppDataArgsArr) throws android.os.RemoteException;

    android.os.IInstalld.IFsveritySetupAuthToken createFsveritySetupAuthToken(android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws android.os.RemoteException;

    void createOatDir(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    boolean createProfileSnapshot(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    void createUserData(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    long deleteOdex(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException;

    void deleteReferenceProfile(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void destroyAppData(java.lang.String str, java.lang.String str2, int i, int i2, long j) throws android.os.RemoteException;

    void destroyAppDataSnapshot(java.lang.String str, java.lang.String str2, int i, long j, int i2, int i3) throws android.os.RemoteException;

    void destroyAppProfiles(java.lang.String str) throws android.os.RemoteException;

    void destroyCeSnapshotsNotSpecified(java.lang.String str, int i, int[] iArr) throws android.os.RemoteException;

    void destroyProfileSnapshot(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void destroyUserData(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    boolean dexopt(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2, java.lang.String str4, int i3, java.lang.String str5, java.lang.String str6, java.lang.String str7, java.lang.String str8, boolean z, int i4, java.lang.String str9, java.lang.String str10, java.lang.String str11) throws android.os.RemoteException;

    boolean dumpProfiles(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) throws android.os.RemoteException;

    int enableFsverity(android.os.IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void fixupAppData(java.lang.String str, int i) throws android.os.RemoteException;

    void freeCache(java.lang.String str, long j, int i) throws android.os.RemoteException;

    android.os.storage.CrateMetadata[] getAppCrates(java.lang.String str, java.lang.String[] strArr, int i) throws android.os.RemoteException;

    long[] getAppSize(java.lang.String str, java.lang.String[] strArr, int i, int i2, int i3, long[] jArr, java.lang.String[] strArr2) throws android.os.RemoteException;

    long[] getExternalSize(java.lang.String str, int i, int i2, int[] iArr) throws android.os.RemoteException;

    int getOdexVisibility(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException;

    android.os.storage.CrateMetadata[] getUserCrates(java.lang.String str, int i) throws android.os.RemoteException;

    long[] getUserSize(java.lang.String str, int i, int i2, int[] iArr) throws android.os.RemoteException;

    byte[] hashSecondaryDexFile(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, int i2) throws android.os.RemoteException;

    void invalidateMounts() throws android.os.RemoteException;

    boolean isQuotaSupported(java.lang.String str) throws android.os.RemoteException;

    void linkFile(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException;

    void linkNativeLibraryDirectory(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException;

    int mergeProfiles(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void migrateAppData(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void migrateLegacyObbData() throws android.os.RemoteException;

    void moveAb(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException;

    void moveCompleteApp(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4, int i2, java.lang.String str5) throws android.os.RemoteException;

    void onPrivateVolumeRemoved(java.lang.String str) throws android.os.RemoteException;

    boolean prepareAppProfile(java.lang.String str, int i, int i2, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException;

    void reconcileSdkData(android.os.ReconcileSdkDataArgs reconcileSdkDataArgs) throws android.os.RemoteException;

    boolean reconcileSecondaryDexFile(java.lang.String str, java.lang.String str2, int i, java.lang.String[] strArr, java.lang.String str3, int i2) throws android.os.RemoteException;

    void restoreAppDataSnapshot(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, int i2, int i3, int i4) throws android.os.RemoteException;

    void restoreconAppData(java.lang.String str, java.lang.String str2, int i, int i2, int i3, java.lang.String str3) throws android.os.RemoteException;

    void rmPackageDir(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void rmdex(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void setAppQuota(java.lang.String str, int i, int i2, long j) throws android.os.RemoteException;

    void setFirstBoot() throws android.os.RemoteException;

    long snapshotAppData(java.lang.String str, java.lang.String str2, int i, int i2, int i3) throws android.os.RemoteException;

    void tryMountDataMirror(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.os.IInstalld {
        @Override // android.os.IInstalld
        public void createUserData(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyUserData(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void setFirstBoot() throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public android.os.CreateAppDataResult createAppData(android.os.CreateAppDataArgs createAppDataArgs) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public android.os.CreateAppDataResult[] createAppDataBatched(android.os.CreateAppDataArgs[] createAppDataArgsArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public void reconcileSdkData(android.os.ReconcileSdkDataArgs reconcileSdkDataArgs) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void restoreconAppData(java.lang.String str, java.lang.String str2, int i, int i2, int i3, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void migrateAppData(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void clearAppData(java.lang.String str, java.lang.String str2, int i, int i2, long j) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyAppData(java.lang.String str, java.lang.String str2, int i, int i2, long j) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void fixupAppData(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public long[] getAppSize(java.lang.String str, java.lang.String[] strArr, int i, int i2, int i3, long[] jArr, java.lang.String[] strArr2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public long[] getUserSize(java.lang.String str, int i, int i2, int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public long[] getExternalSize(java.lang.String str, int i, int i2, int[] iArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public android.os.storage.CrateMetadata[] getAppCrates(java.lang.String str, java.lang.String[] strArr, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public android.os.storage.CrateMetadata[] getUserCrates(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public void setAppQuota(java.lang.String str, int i, int i2, long j) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void moveCompleteApp(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4, int i2, java.lang.String str5) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean dexopt(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2, java.lang.String str4, int i3, java.lang.String str5, java.lang.String str6, java.lang.String str7, java.lang.String str8, boolean z, int i4, java.lang.String str9, java.lang.String str10, java.lang.String str11) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void controlDexOptBlocking(boolean z) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void rmdex(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public int mergeProfiles(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInstalld
        public boolean dumpProfiles(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean copySystemProfile(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void clearAppProfiles(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyAppProfiles(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void deleteReferenceProfile(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean createProfileSnapshot(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public void destroyProfileSnapshot(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void rmPackageDir(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void freeCache(java.lang.String str, long j, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void linkNativeLibraryDirectory(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void createOatDir(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void linkFile(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void moveAb(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public long deleteOdex(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.IInstalld
        public boolean reconcileSecondaryDexFile(java.lang.String str, java.lang.String str2, int i, java.lang.String[] strArr, java.lang.String str3, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public byte[] hashSecondaryDexFile(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public void invalidateMounts() throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public boolean isQuotaSupported(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public boolean prepareAppProfile(java.lang.String str, int i, int i2, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInstalld
        public long snapshotAppData(java.lang.String str, java.lang.String str2, int i, int i2, int i3) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.os.IInstalld
        public void restoreAppDataSnapshot(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyAppDataSnapshot(java.lang.String str, java.lang.String str2, int i, long j, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void destroyCeSnapshotsNotSpecified(java.lang.String str, int i, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void tryMountDataMirror(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void onPrivateVolumeRemoved(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void migrateLegacyObbData() throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public void cleanupInvalidPackageDirs(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.os.IInstalld
        public int getOdexVisibility(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInstalld
        public android.os.IInstalld.IFsveritySetupAuthToken createFsveritySetupAuthToken(android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInstalld
        public int enableFsverity(android.os.IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.os.IInstalld {
        public static final java.lang.String DESCRIPTOR = "android.os.IInstalld";
        static final int TRANSACTION_cleanupInvalidPackageDirs = 49;
        static final int TRANSACTION_clearAppData = 9;
        static final int TRANSACTION_clearAppProfiles = 25;
        static final int TRANSACTION_controlDexOptBlocking = 20;
        static final int TRANSACTION_copySystemProfile = 24;
        static final int TRANSACTION_createAppData = 4;
        static final int TRANSACTION_createAppDataBatched = 5;
        static final int TRANSACTION_createFsveritySetupAuthToken = 51;
        static final int TRANSACTION_createOatDir = 33;
        static final int TRANSACTION_createProfileSnapshot = 28;
        static final int TRANSACTION_createUserData = 1;
        static final int TRANSACTION_deleteOdex = 36;
        static final int TRANSACTION_deleteReferenceProfile = 27;
        static final int TRANSACTION_destroyAppData = 10;
        static final int TRANSACTION_destroyAppDataSnapshot = 44;
        static final int TRANSACTION_destroyAppProfiles = 26;
        static final int TRANSACTION_destroyCeSnapshotsNotSpecified = 45;
        static final int TRANSACTION_destroyProfileSnapshot = 29;
        static final int TRANSACTION_destroyUserData = 2;
        static final int TRANSACTION_dexopt = 19;
        static final int TRANSACTION_dumpProfiles = 23;
        static final int TRANSACTION_enableFsverity = 52;
        static final int TRANSACTION_fixupAppData = 11;
        static final int TRANSACTION_freeCache = 31;
        static final int TRANSACTION_getAppCrates = 15;
        static final int TRANSACTION_getAppSize = 12;
        static final int TRANSACTION_getExternalSize = 14;
        static final int TRANSACTION_getOdexVisibility = 50;
        static final int TRANSACTION_getUserCrates = 16;
        static final int TRANSACTION_getUserSize = 13;
        static final int TRANSACTION_hashSecondaryDexFile = 38;
        static final int TRANSACTION_invalidateMounts = 39;
        static final int TRANSACTION_isQuotaSupported = 40;
        static final int TRANSACTION_linkFile = 34;
        static final int TRANSACTION_linkNativeLibraryDirectory = 32;
        static final int TRANSACTION_mergeProfiles = 22;
        static final int TRANSACTION_migrateAppData = 8;
        static final int TRANSACTION_migrateLegacyObbData = 48;
        static final int TRANSACTION_moveAb = 35;
        static final int TRANSACTION_moveCompleteApp = 18;
        static final int TRANSACTION_onPrivateVolumeRemoved = 47;
        static final int TRANSACTION_prepareAppProfile = 41;
        static final int TRANSACTION_reconcileSdkData = 6;
        static final int TRANSACTION_reconcileSecondaryDexFile = 37;
        static final int TRANSACTION_restoreAppDataSnapshot = 43;
        static final int TRANSACTION_restoreconAppData = 7;
        static final int TRANSACTION_rmPackageDir = 30;
        static final int TRANSACTION_rmdex = 21;
        static final int TRANSACTION_setAppQuota = 17;
        static final int TRANSACTION_setFirstBoot = 3;
        static final int TRANSACTION_snapshotAppData = 42;
        static final int TRANSACTION_tryMountDataMirror = 46;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.os.IInstalld asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IInstalld)) {
                return (android.os.IInstalld) queryLocalInterface;
            }
            return new android.os.IInstalld.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
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
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createUserData(readString, readInt, readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyUserData(readString2, readInt4, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    setFirstBoot();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.os.CreateAppDataArgs createAppDataArgs = (android.os.CreateAppDataArgs) parcel.readTypedObject(android.os.CreateAppDataArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.CreateAppDataResult createAppData = createAppData(createAppDataArgs);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createAppData, 1);
                    return true;
                case 5:
                    android.os.CreateAppDataArgs[] createAppDataArgsArr = (android.os.CreateAppDataArgs[]) parcel.createTypedArray(android.os.CreateAppDataArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.CreateAppDataResult[] createAppDataBatched = createAppDataBatched(createAppDataArgsArr);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(createAppDataBatched, 1);
                    return true;
                case 6:
                    android.os.ReconcileSdkDataArgs reconcileSdkDataArgs = (android.os.ReconcileSdkDataArgs) parcel.readTypedObject(android.os.ReconcileSdkDataArgs.CREATOR);
                    parcel.enforceNoDataAvail();
                    reconcileSdkData(reconcileSdkDataArgs);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString3 = parcel.readString();
                    java.lang.String readString4 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    restoreconAppData(readString3, readString4, readInt6, readInt7, readInt8, readString5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    migrateAppData(readString6, readString7, readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    clearAppData(readString8, readString9, readInt11, readInt12, readLong);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    destroyAppData(readString10, readString11, readInt13, readInt14, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    java.lang.String readString12 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    fixupAppData(readString12, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    long[] createLongArray = parcel.createLongArray();
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    long[] appSize = getAppSize(readString13, createStringArray, readInt16, readInt17, readInt18, createLongArray, createStringArray2);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(appSize);
                    return true;
                case 13:
                    java.lang.String readString14 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long[] userSize = getUserSize(readString14, readInt19, readInt20, createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(userSize);
                    return true;
                case 14:
                    java.lang.String readString15 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    int[] createIntArray2 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    long[] externalSize = getExternalSize(readString15, readInt21, readInt22, createIntArray2);
                    parcel2.writeNoException();
                    parcel2.writeLongArray(externalSize);
                    return true;
                case 15:
                    java.lang.String readString16 = parcel.readString();
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.storage.CrateMetadata[] appCrates = getAppCrates(readString16, createStringArray3, readInt23);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(appCrates, 1);
                    return true;
                case 16:
                    java.lang.String readString17 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.storage.CrateMetadata[] userCrates = getUserCrates(readString17, readInt24);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(userCrates, 1);
                    return true;
                case 17:
                    java.lang.String readString18 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setAppQuota(readString18, readInt25, readInt26, readLong3);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    java.lang.String readString19 = parcel.readString();
                    java.lang.String readString20 = parcel.readString();
                    java.lang.String readString21 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    java.lang.String readString22 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    java.lang.String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    moveCompleteApp(readString19, readString20, readString21, readInt27, readString22, readInt28, readString23);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    java.lang.String readString24 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    java.lang.String readString25 = parcel.readString();
                    java.lang.String readString26 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    java.lang.String readString27 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    java.lang.String readString28 = parcel.readString();
                    java.lang.String readString29 = parcel.readString();
                    java.lang.String readString30 = parcel.readString();
                    java.lang.String readString31 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt32 = parcel.readInt();
                    java.lang.String readString32 = parcel.readString();
                    java.lang.String readString33 = parcel.readString();
                    java.lang.String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean dexopt = dexopt(readString24, readInt29, readString25, readString26, readInt30, readString27, readInt31, readString28, readString29, readString30, readString31, readBoolean, readInt32, readString32, readString33, readString34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dexopt);
                    return true;
                case 20:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    controlDexOptBlocking(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    java.lang.String readString35 = parcel.readString();
                    java.lang.String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rmdex(readString35, readString36);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt33 = parcel.readInt();
                    java.lang.String readString37 = parcel.readString();
                    java.lang.String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int mergeProfiles = mergeProfiles(readInt33, readString37, readString38);
                    parcel2.writeNoException();
                    parcel2.writeInt(mergeProfiles);
                    return true;
                case 23:
                    int readInt34 = parcel.readInt();
                    java.lang.String readString39 = parcel.readString();
                    java.lang.String readString40 = parcel.readString();
                    java.lang.String readString41 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dumpProfiles = dumpProfiles(readInt34, readString39, readString40, readString41, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dumpProfiles);
                    return true;
                case 24:
                    java.lang.String readString42 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    java.lang.String readString43 = parcel.readString();
                    java.lang.String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean copySystemProfile = copySystemProfile(readString42, readInt35, readString43, readString44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(copySystemProfile);
                    return true;
                case 25:
                    java.lang.String readString45 = parcel.readString();
                    java.lang.String readString46 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearAppProfiles(readString45, readString46);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    java.lang.String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyAppProfiles(readString47);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    java.lang.String readString48 = parcel.readString();
                    java.lang.String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteReferenceProfile(readString48, readString49);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt36 = parcel.readInt();
                    java.lang.String readString50 = parcel.readString();
                    java.lang.String readString51 = parcel.readString();
                    java.lang.String readString52 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean createProfileSnapshot = createProfileSnapshot(readInt36, readString50, readString51, readString52);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(createProfileSnapshot);
                    return true;
                case 29:
                    java.lang.String readString53 = parcel.readString();
                    java.lang.String readString54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    destroyProfileSnapshot(readString53, readString54);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    java.lang.String readString55 = parcel.readString();
                    java.lang.String readString56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rmPackageDir(readString55, readString56);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    java.lang.String readString57 = parcel.readString();
                    long readLong4 = parcel.readLong();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    freeCache(readString57, readLong4, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    java.lang.String readString58 = parcel.readString();
                    java.lang.String readString59 = parcel.readString();
                    java.lang.String readString60 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    linkNativeLibraryDirectory(readString58, readString59, readString60, readInt38);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    java.lang.String readString61 = parcel.readString();
                    java.lang.String readString62 = parcel.readString();
                    java.lang.String readString63 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createOatDir(readString61, readString62, readString63);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    java.lang.String readString64 = parcel.readString();
                    java.lang.String readString65 = parcel.readString();
                    java.lang.String readString66 = parcel.readString();
                    java.lang.String readString67 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    linkFile(readString64, readString65, readString66, readString67);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    java.lang.String readString68 = parcel.readString();
                    java.lang.String readString69 = parcel.readString();
                    java.lang.String readString70 = parcel.readString();
                    java.lang.String readString71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    moveAb(readString68, readString69, readString70, readString71);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    java.lang.String readString72 = parcel.readString();
                    java.lang.String readString73 = parcel.readString();
                    java.lang.String readString74 = parcel.readString();
                    java.lang.String readString75 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long deleteOdex = deleteOdex(readString72, readString73, readString74, readString75);
                    parcel2.writeNoException();
                    parcel2.writeLong(deleteOdex);
                    return true;
                case 37:
                    java.lang.String readString76 = parcel.readString();
                    java.lang.String readString77 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    java.lang.String[] createStringArray4 = parcel.createStringArray();
                    java.lang.String readString78 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean reconcileSecondaryDexFile = reconcileSecondaryDexFile(readString76, readString77, readInt39, createStringArray4, readString78, readInt40);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(reconcileSecondaryDexFile);
                    return true;
                case 38:
                    java.lang.String readString79 = parcel.readString();
                    java.lang.String readString80 = parcel.readString();
                    int readInt41 = parcel.readInt();
                    java.lang.String readString81 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] hashSecondaryDexFile = hashSecondaryDexFile(readString79, readString80, readInt41, readString81, readInt42);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(hashSecondaryDexFile);
                    return true;
                case 39:
                    invalidateMounts();
                    parcel2.writeNoException();
                    return true;
                case 40:
                    java.lang.String readString82 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isQuotaSupported = isQuotaSupported(readString82);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isQuotaSupported);
                    return true;
                case 41:
                    java.lang.String readString83 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    int readInt44 = parcel.readInt();
                    java.lang.String readString84 = parcel.readString();
                    java.lang.String readString85 = parcel.readString();
                    java.lang.String readString86 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean prepareAppProfile = prepareAppProfile(readString83, readInt43, readInt44, readString84, readString85, readString86);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(prepareAppProfile);
                    return true;
                case 42:
                    java.lang.String readString87 = parcel.readString();
                    java.lang.String readString88 = parcel.readString();
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    long snapshotAppData = snapshotAppData(readString87, readString88, readInt45, readInt46, readInt47);
                    parcel2.writeNoException();
                    parcel2.writeLong(snapshotAppData);
                    return true;
                case 43:
                    java.lang.String readString89 = parcel.readString();
                    java.lang.String readString90 = parcel.readString();
                    int readInt48 = parcel.readInt();
                    java.lang.String readString91 = parcel.readString();
                    int readInt49 = parcel.readInt();
                    int readInt50 = parcel.readInt();
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreAppDataSnapshot(readString89, readString90, readInt48, readString91, readInt49, readInt50, readInt51);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    java.lang.String readString92 = parcel.readString();
                    java.lang.String readString93 = parcel.readString();
                    int readInt52 = parcel.readInt();
                    long readLong5 = parcel.readLong();
                    int readInt53 = parcel.readInt();
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyAppDataSnapshot(readString92, readString93, readInt52, readLong5, readInt53, readInt54);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    java.lang.String readString94 = parcel.readString();
                    int readInt55 = parcel.readInt();
                    int[] createIntArray3 = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    destroyCeSnapshotsNotSpecified(readString94, readInt55, createIntArray3);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    java.lang.String readString95 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    tryMountDataMirror(readString95);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    java.lang.String readString96 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onPrivateVolumeRemoved(readString96);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    migrateLegacyObbData();
                    parcel2.writeNoException();
                    return true;
                case 49:
                    java.lang.String readString97 = parcel.readString();
                    int readInt56 = parcel.readInt();
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cleanupInvalidPackageDirs(readString97, readInt56, readInt57);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    java.lang.String readString98 = parcel.readString();
                    java.lang.String readString99 = parcel.readString();
                    java.lang.String readString100 = parcel.readString();
                    java.lang.String readString101 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int odexVisibility = getOdexVisibility(readString98, readString99, readString100, readString101);
                    parcel2.writeNoException();
                    parcel2.writeInt(odexVisibility);
                    return true;
                case 51:
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.IInstalld.IFsveritySetupAuthToken createFsveritySetupAuthToken = createFsveritySetupAuthToken(parcelFileDescriptor, readInt58);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createFsveritySetupAuthToken);
                    return true;
                case 52:
                    android.os.IInstalld.IFsveritySetupAuthToken asInterface = android.os.IInstalld.IFsveritySetupAuthToken.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString102 = parcel.readString();
                    java.lang.String readString103 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int enableFsverity = enableFsverity(asInterface, readString102, readString103);
                    parcel2.writeNoException();
                    parcel2.writeInt(enableFsverity);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.os.IInstalld {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.os.IInstalld.Stub.DESCRIPTOR;
            }

            @Override // android.os.IInstalld
            public void createUserData(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyUserData(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void setFirstBoot() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public android.os.CreateAppDataResult createAppData(android.os.CreateAppDataArgs createAppDataArgs) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(createAppDataArgs, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.CreateAppDataResult) obtain2.readTypedObject(android.os.CreateAppDataResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public android.os.CreateAppDataResult[] createAppDataBatched(android.os.CreateAppDataArgs[] createAppDataArgsArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeTypedArray(createAppDataArgsArr, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.CreateAppDataResult[]) obtain2.createTypedArray(android.os.CreateAppDataResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void reconcileSdkData(android.os.ReconcileSdkDataArgs reconcileSdkDataArgs) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(reconcileSdkDataArgs, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void restoreconAppData(java.lang.String str, java.lang.String str2, int i, int i2, int i3, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void migrateAppData(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void clearAppData(java.lang.String str, java.lang.String str2, int i, int i2, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyAppData(java.lang.String str, java.lang.String str2, int i, int i2, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void fixupAppData(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long[] getAppSize(java.lang.String str, java.lang.String[] strArr, int i, int i2, int i3, long[] jArr, java.lang.String[] strArr2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeLongArray(jArr);
                    obtain.writeStringArray(strArr2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long[] getUserSize(java.lang.String str, int i, int i2, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long[] getExternalSize(java.lang.String str, int i, int i2, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public android.os.storage.CrateMetadata[] getAppCrates(java.lang.String str, java.lang.String[] strArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.storage.CrateMetadata[]) obtain2.createTypedArray(android.os.storage.CrateMetadata.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public android.os.storage.CrateMetadata[] getUserCrates(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.storage.CrateMetadata[]) obtain2.createTypedArray(android.os.storage.CrateMetadata.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void setAppQuota(java.lang.String str, int i, int i2, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void moveCompleteApp(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, java.lang.String str4, int i2, java.lang.String str5) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeString(str4);
                    obtain.writeInt(i2);
                    obtain.writeString(str5);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean dexopt(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2, java.lang.String str4, int i3, java.lang.String str5, java.lang.String str6, java.lang.String str7, java.lang.String str8, boolean z, int i4, java.lang.String str9, java.lang.String str10, java.lang.String str11) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeString(str4);
                    obtain.writeInt(i3);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    obtain.writeString(str7);
                    obtain.writeString(str8);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i4);
                    obtain.writeString(str9);
                    obtain.writeString(str10);
                    obtain.writeString(str11);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void controlDexOptBlocking(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void rmdex(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public int mergeProfiles(int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean dumpProfiles(int i, java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean copySystemProfile(java.lang.String str, int i, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void clearAppProfiles(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyAppProfiles(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void deleteReferenceProfile(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean createProfileSnapshot(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyProfileSnapshot(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void rmPackageDir(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void freeCache(java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void linkNativeLibraryDirectory(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void createOatDir(java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void linkFile(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void moveAb(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long deleteOdex(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean reconcileSecondaryDexFile(java.lang.String str, java.lang.String str2, int i, java.lang.String[] strArr, java.lang.String str3, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public byte[] hashSecondaryDexFile(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void invalidateMounts() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean isQuotaSupported(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public boolean prepareAppProfile(java.lang.String str, int i, int i2, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public long snapshotAppData(java.lang.String str, java.lang.String str2, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void restoreAppDataSnapshot(java.lang.String str, java.lang.String str2, int i, java.lang.String str3, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyAppDataSnapshot(java.lang.String str, java.lang.String str2, int i, long j, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void destroyCeSnapshotsNotSpecified(java.lang.String str, int i, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void tryMountDataMirror(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void onPrivateVolumeRemoved(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void migrateLegacyObbData() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public void cleanupInvalidPackageDirs(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
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

            @Override // android.os.IInstalld
            public int getOdexVisibility(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public android.os.IInstalld.IFsveritySetupAuthToken createFsveritySetupAuthToken(android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.os.IInstalld.IFsveritySetupAuthToken.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInstalld
            public int enableFsverity(android.os.IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.os.IInstalld.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iFsveritySetupAuthToken);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }

    public interface IFsveritySetupAuthToken extends android.os.IInterface {
        public static final java.lang.String DESCRIPTOR = "android.os.IInstalld.IFsveritySetupAuthToken";

        public static class Default implements android.os.IInstalld.IFsveritySetupAuthToken {
            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return null;
            }
        }

        public static abstract class Stub extends android.os.Binder implements android.os.IInstalld.IFsveritySetupAuthToken {
            public Stub() {
                attachInterface(this, android.os.IInstalld.IFsveritySetupAuthToken.DESCRIPTOR);
            }

            public static android.os.IInstalld.IFsveritySetupAuthToken asInterface(android.os.IBinder iBinder) {
                if (iBinder == null) {
                    return null;
                }
                android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.os.IInstalld.IFsveritySetupAuthToken.DESCRIPTOR);
                if (queryLocalInterface != null && (queryLocalInterface instanceof android.os.IInstalld.IFsveritySetupAuthToken)) {
                    return (android.os.IInstalld.IFsveritySetupAuthToken) queryLocalInterface;
                }
                return new android.os.IInstalld.IFsveritySetupAuthToken.Stub.Proxy(iBinder);
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this;
            }

            @Override // android.os.Binder
            public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
                if (i == 1598968902) {
                    parcel2.writeString(android.os.IInstalld.IFsveritySetupAuthToken.DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }

            private static class Proxy implements android.os.IInstalld.IFsveritySetupAuthToken {
                private android.os.IBinder mRemote;

                Proxy(android.os.IBinder iBinder) {
                    this.mRemote = iBinder;
                }

                @Override // android.os.IInterface
                public android.os.IBinder asBinder() {
                    return this.mRemote;
                }

                public java.lang.String getInterfaceDescriptor() {
                    return android.os.IInstalld.IFsveritySetupAuthToken.DESCRIPTOR;
                }
            }
        }
    }
}
