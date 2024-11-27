package com.android.internal.content;

/* loaded from: classes4.dex */
public class InstallLocationUtils {
    public static final int APP_INSTALL_AUTO = 0;
    public static final int APP_INSTALL_EXTERNAL = 2;
    public static final int APP_INSTALL_INTERNAL = 1;
    public static final int RECOMMEND_FAILED_ALREADY_EXISTS = -4;
    public static final int RECOMMEND_FAILED_INSUFFICIENT_STORAGE = -1;
    public static final int RECOMMEND_FAILED_INVALID_APK = -2;
    public static final int RECOMMEND_FAILED_INVALID_LOCATION = -3;
    public static final int RECOMMEND_FAILED_INVALID_URI = -6;
    public static final int RECOMMEND_INSTALL_EPHEMERAL = 3;
    public static final int RECOMMEND_INSTALL_EXTERNAL = 2;
    public static final int RECOMMEND_INSTALL_INTERNAL = 1;
    public static final int RECOMMEND_MEDIA_UNAVAILABLE = -5;
    private static final java.lang.String TAG = "PackageHelper";
    private static com.android.internal.content.InstallLocationUtils.TestableInterface sDefaultTestableInterface = null;

    public static abstract class TestableInterface {
        public abstract boolean getAllow3rdPartyOnInternalConfig(android.content.Context context);

        public abstract java.io.File getDataDirectory();

        public abstract android.content.pm.ApplicationInfo getExistingAppInfo(android.content.Context context, java.lang.String str);

        public abstract boolean getForceAllowOnExternalSetting(android.content.Context context);

        public abstract android.os.storage.StorageManager getStorageManager(android.content.Context context);
    }

    public static android.os.storage.IStorageManager getStorageManager() throws android.os.RemoteException {
        android.os.IBinder service = android.os.ServiceManager.getService("mount");
        if (service != null) {
            return android.os.storage.IStorageManager.Stub.asInterface(service);
        }
        android.util.Log.e(TAG, "Can't get storagemanager service");
        throw new android.os.RemoteException("Could not contact storagemanager service");
    }

    private static synchronized com.android.internal.content.InstallLocationUtils.TestableInterface getDefaultTestableInterface() {
        com.android.internal.content.InstallLocationUtils.TestableInterface testableInterface;
        synchronized (com.android.internal.content.InstallLocationUtils.class) {
            if (sDefaultTestableInterface == null) {
                sDefaultTestableInterface = new com.android.internal.content.InstallLocationUtils.TestableInterface() { // from class: com.android.internal.content.InstallLocationUtils.1
                    @Override // com.android.internal.content.InstallLocationUtils.TestableInterface
                    public android.os.storage.StorageManager getStorageManager(android.content.Context context) {
                        return (android.os.storage.StorageManager) context.getSystemService(android.os.storage.StorageManager.class);
                    }

                    @Override // com.android.internal.content.InstallLocationUtils.TestableInterface
                    public boolean getForceAllowOnExternalSetting(android.content.Context context) {
                        return android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.FORCE_ALLOW_ON_EXTERNAL, 0) != 0;
                    }

                    @Override // com.android.internal.content.InstallLocationUtils.TestableInterface
                    public boolean getAllow3rdPartyOnInternalConfig(android.content.Context context) {
                        return context.getResources().getBoolean(com.android.internal.R.bool.config_allow3rdPartyAppOnInternal);
                    }

                    @Override // com.android.internal.content.InstallLocationUtils.TestableInterface
                    public android.content.pm.ApplicationInfo getExistingAppInfo(android.content.Context context, java.lang.String str) {
                        try {
                            return context.getPackageManager().getApplicationInfo(str, 4194304);
                        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                            return null;
                        }
                    }

                    @Override // com.android.internal.content.InstallLocationUtils.TestableInterface
                    public java.io.File getDataDirectory() {
                        return android.os.Environment.getDataDirectory();
                    }
                };
            }
            testableInterface = sDefaultTestableInterface;
        }
        return testableInterface;
    }

    @java.lang.Deprecated
    public static java.lang.String resolveInstallVolume(android.content.Context context, java.lang.String str, int i, long j, com.android.internal.content.InstallLocationUtils.TestableInterface testableInterface) throws java.io.IOException {
        android.content.pm.PackageInstaller.SessionParams sessionParams = new android.content.pm.PackageInstaller.SessionParams(-1);
        sessionParams.appPackageName = str;
        sessionParams.installLocation = i;
        sessionParams.sizeBytes = j;
        return resolveInstallVolume(context, sessionParams, testableInterface);
    }

    public static java.lang.String resolveInstallVolume(android.content.Context context, android.content.pm.PackageInstaller.SessionParams sessionParams) throws java.io.IOException {
        return resolveInstallVolume(context, sessionParams.appPackageName, sessionParams.installLocation, sessionParams.sizeBytes, getDefaultTestableInterface());
    }

    private static boolean checkFitOnVolume(android.os.storage.StorageManager storageManager, java.lang.String str, android.content.pm.PackageInstaller.SessionParams sessionParams) throws java.io.IOException {
        if (str == null) {
            return false;
        }
        int translateAllocateFlags = translateAllocateFlags(sessionParams.installFlags);
        java.util.UUID uuidForPath = storageManager.getUuidForPath(new java.io.File(str));
        long allocatableBytes = storageManager.getAllocatableBytes(uuidForPath, translateAllocateFlags | 8);
        if (sessionParams.sizeBytes <= allocatableBytes) {
            return true;
        }
        return sessionParams.sizeBytes <= allocatableBytes + storageManager.getAllocatableBytes(uuidForPath, translateAllocateFlags | 16);
    }

    public static java.lang.String resolveInstallVolume(android.content.Context context, android.content.pm.PackageInstaller.SessionParams sessionParams, com.android.internal.content.InstallLocationUtils.TestableInterface testableInterface) throws java.io.IOException {
        android.os.storage.StorageManager storageManager = testableInterface.getStorageManager(context);
        boolean forceAllowOnExternalSetting = testableInterface.getForceAllowOnExternalSetting(context);
        boolean allow3rdPartyOnInternalConfig = testableInterface.getAllow3rdPartyOnInternalConfig(context);
        android.content.pm.ApplicationInfo existingAppInfo = testableInterface.getExistingAppInfo(context, sessionParams.appPackageName);
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        java.lang.String str = null;
        for (android.os.storage.VolumeInfo volumeInfo : storageManager.getVolumes()) {
            if (volumeInfo.type == 1 && volumeInfo.isMountedWritable()) {
                boolean equals = android.os.storage.VolumeInfo.ID_PRIVATE_INTERNAL.equals(volumeInfo.id);
                if (equals) {
                    str = volumeInfo.path;
                }
                if (!equals || allow3rdPartyOnInternalConfig) {
                    arrayMap.put(volumeInfo.fsUuid, volumeInfo.path);
                }
            }
        }
        if (existingAppInfo != null && existingAppInfo.isSystemApp()) {
            if (checkFitOnVolume(storageManager, str, sessionParams)) {
                return android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL;
            }
            throw new java.io.IOException("Not enough space on existing volume " + existingAppInfo.volumeUuid + " for system app " + sessionParams.appPackageName + " upgrade");
        }
        if (!forceAllowOnExternalSetting && sessionParams.installLocation == 1) {
            if (existingAppInfo != null && !java.util.Objects.equals(existingAppInfo.volumeUuid, android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL)) {
                throw new java.io.IOException("Cannot automatically move " + sessionParams.appPackageName + " from " + existingAppInfo.volumeUuid + " to internal storage");
            }
            if (!allow3rdPartyOnInternalConfig) {
                throw new java.io.IOException("Not allowed to install non-system apps on internal storage");
            }
            if (checkFitOnVolume(storageManager, str, sessionParams)) {
                return android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL;
            }
            throw new java.io.IOException("Requested internal only, but not enough space");
        }
        if (existingAppInfo != null) {
            if (java.util.Objects.equals(existingAppInfo.volumeUuid, android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL)) {
                r4 = str;
            } else if (arrayMap.containsKey(existingAppInfo.volumeUuid)) {
                r4 = (java.lang.String) arrayMap.get(existingAppInfo.volumeUuid);
            }
            if (checkFitOnVolume(storageManager, r4, sessionParams)) {
                return existingAppInfo.volumeUuid;
            }
            throw new java.io.IOException("Not enough space on existing volume " + existingAppInfo.volumeUuid + " for " + sessionParams.appPackageName + " upgrade");
        }
        r4 = arrayMap.isEmpty() ? null : (java.lang.String) arrayMap.keyAt(0);
        if (arrayMap.size() == 1) {
            if (checkFitOnVolume(storageManager, (java.lang.String) arrayMap.valueAt(0), sessionParams)) {
                return r4;
            }
        } else {
            long j = Long.MIN_VALUE;
            for (java.lang.String str2 : arrayMap.keySet()) {
                long allocatableBytes = storageManager.getAllocatableBytes(storageManager.getUuidForPath(new java.io.File((java.lang.String) arrayMap.get(str2))), translateAllocateFlags(sessionParams.installFlags));
                if (allocatableBytes >= j) {
                    r4 = str2;
                    j = allocatableBytes;
                }
            }
            if (j >= sessionParams.sizeBytes) {
                return r4;
            }
        }
        if (!arrayMap.isEmpty() && 2147483647L == sessionParams.sizeBytes && android.os.SystemProperties.getBoolean("debug.pm.install_skip_size_check_for_maxint", false)) {
            return r4;
        }
        throw new java.io.IOException("No special requests, but no room on allowed volumes.  allow3rdPartyOnInternal? " + allow3rdPartyOnInternalConfig);
    }

    public static boolean fitsOnInternal(android.content.Context context, android.content.pm.PackageInstaller.SessionParams sessionParams) throws java.io.IOException {
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) context.getSystemService(android.os.storage.StorageManager.class);
        java.util.UUID uuidForPath = storageManager.getUuidForPath(android.os.Environment.getDataDirectory());
        int translateAllocateFlags = translateAllocateFlags(sessionParams.installFlags);
        long allocatableBytes = storageManager.getAllocatableBytes(uuidForPath, translateAllocateFlags | 8);
        if (sessionParams.sizeBytes <= allocatableBytes) {
            return true;
        }
        return sessionParams.sizeBytes <= allocatableBytes + storageManager.getAllocatableBytes(uuidForPath, translateAllocateFlags | 16);
    }

    public static boolean fitsOnExternal(android.content.Context context, android.content.pm.PackageInstaller.SessionParams sessionParams) {
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) context.getSystemService(android.os.storage.StorageManager.class);
        android.os.storage.StorageVolume primaryVolume = storageManager.getPrimaryVolume();
        return sessionParams.sizeBytes > 0 && !primaryVolume.isEmulated() && android.os.Environment.MEDIA_MOUNTED.equals(primaryVolume.getState()) && sessionParams.sizeBytes <= storageManager.getStorageBytesUntilLow(primaryVolume.getPathFile());
    }

    public static int resolveInstallLocation(android.content.Context context, android.content.pm.PackageInstaller.SessionParams sessionParams) throws java.io.IOException {
        android.content.pm.ApplicationInfo applicationInfo;
        boolean z;
        boolean z2;
        char c;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(sessionParams.appPackageName, 4194304);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        if ((sessionParams.installFlags & 2048) != 0) {
            z = false;
            c = 1;
            z2 = true;
        } else if ((sessionParams.installFlags & 16) != 0) {
            z = false;
            z2 = false;
            c = 1;
        } else if (sessionParams.installLocation == 1) {
            z = false;
            z2 = false;
            c = 1;
        } else if (sessionParams.installLocation == 2) {
            c = 2;
            z2 = false;
            z = true;
        } else if (sessionParams.installLocation == 0) {
            if (applicationInfo != null) {
                if ((applicationInfo.flags & 262144) != 0) {
                    c = 2;
                } else {
                    c = 1;
                }
            } else {
                c = 1;
            }
            z2 = false;
            z = true;
        } else {
            z = false;
            z2 = false;
            c = 1;
        }
        boolean fitsOnInternal = (z || c == 1) ? fitsOnInternal(context, sessionParams) : false;
        boolean fitsOnExternal = (z || c == 2) ? fitsOnExternal(context, sessionParams) : false;
        if (c == 1) {
            if (fitsOnInternal) {
                return z2 ? 3 : 1;
            }
        } else if (c == 2 && fitsOnExternal) {
            return 2;
        }
        if (z) {
            if (fitsOnInternal) {
                return 1;
            }
            return fitsOnExternal ? 2 : -1;
        }
        return -1;
    }

    @java.lang.Deprecated
    public static long calculateInstalledSize(android.content.pm.parsing.PackageLite packageLite, boolean z, java.lang.String str) throws java.io.IOException {
        return calculateInstalledSize(packageLite, str);
    }

    public static long calculateInstalledSize(android.content.pm.parsing.PackageLite packageLite, java.lang.String str) throws java.io.IOException {
        return calculateInstalledSize(packageLite, str, (java.io.FileDescriptor) null);
    }

    public static long calculateInstalledSize(android.content.pm.parsing.PackageLite packageLite, java.lang.String str, java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
        com.android.internal.content.NativeLibraryHelper.Handle handle = null;
        try {
            handle = fileDescriptor != null ? com.android.internal.content.NativeLibraryHelper.Handle.createFd(packageLite, fileDescriptor) : com.android.internal.content.NativeLibraryHelper.Handle.create(packageLite);
            return calculateInstalledSize(packageLite, handle, str);
        } finally {
            libcore.io.IoUtils.closeQuietly(handle);
        }
    }

    @java.lang.Deprecated
    public static long calculateInstalledSize(android.content.pm.parsing.PackageLite packageLite, boolean z, com.android.internal.content.NativeLibraryHelper.Handle handle, java.lang.String str) throws java.io.IOException {
        return calculateInstalledSize(packageLite, handle, str);
    }

    public static long calculateInstalledSize(android.content.pm.parsing.PackageLite packageLite, com.android.internal.content.NativeLibraryHelper.Handle handle, java.lang.String str) throws java.io.IOException {
        java.util.Iterator<java.lang.String> it = packageLite.getAllApkPaths().iterator();
        long j = 0;
        while (it.hasNext()) {
            j += new java.io.File(it.next()).length();
        }
        long packageDexMetadataSize = j + android.content.pm.dex.DexMetadataHelper.getPackageDexMetadataSize(packageLite);
        if (packageLite.isExtractNativeLibs()) {
            return packageDexMetadataSize + com.android.internal.content.NativeLibraryHelper.sumNativeBinariesWithOverride(handle, str);
        }
        return packageDexMetadataSize;
    }

    public static java.lang.String replaceEnd(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (!str.endsWith(str2)) {
            throw new java.lang.IllegalArgumentException("Expected " + str + " to end with " + str2);
        }
        return str.substring(0, str.length() - str2.length()) + str3;
    }

    public static int translateAllocateFlags(int i) {
        if ((i & 32768) != 0) {
            return 1;
        }
        return 0;
    }

    public static int installLocationPolicy(int i, int i2, int i3, boolean z, boolean z2) {
        if ((i3 & 2) == 0) {
            return -4;
        }
        if (z || i == 1) {
            return 1;
        }
        if (i == 2) {
            return i2;
        }
        if (z2) {
            return 2;
        }
        return 1;
    }

    public static int getInstallationErrorCode(int i) {
        if (i == -3) {
            return -19;
        }
        if (i == -4) {
            return -1;
        }
        if (i == -1) {
            return -4;
        }
        if (i == -2) {
            return -2;
        }
        if (i == -6) {
            return -3;
        }
        if (i == -5) {
            return -20;
        }
        return 1;
    }
}
