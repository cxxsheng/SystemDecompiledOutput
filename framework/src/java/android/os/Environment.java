package android.os;

/* loaded from: classes3.dex */
public class Environment {
    private static final long DEFAULT_SCOPED_STORAGE = 149924527;
    public static java.lang.String DIRECTORY_ALARMS = null;

    @java.lang.Deprecated
    public static final java.lang.String DIRECTORY_ANDROID = "Android";
    public static java.lang.String DIRECTORY_AUDIOBOOKS = null;
    public static java.lang.String DIRECTORY_DCIM = null;
    public static java.lang.String DIRECTORY_DOCUMENTS = null;
    public static java.lang.String DIRECTORY_DOWNLOADS = null;
    public static java.lang.String DIRECTORY_MOVIES = null;
    public static java.lang.String DIRECTORY_MUSIC = null;
    public static java.lang.String DIRECTORY_NOTIFICATIONS = null;
    public static java.lang.String DIRECTORY_PICTURES = null;
    public static java.lang.String DIRECTORY_PODCASTS = null;
    public static java.lang.String DIRECTORY_RECORDINGS = null;
    public static java.lang.String DIRECTORY_RINGTONES = null;
    public static java.lang.String DIRECTORY_SCREENSHOTS = null;
    public static final java.lang.String DIR_ANDROID = "Android";
    private static final java.lang.String DIR_CACHE = "cache";
    private static final java.lang.String DIR_DATA = "data";
    private static final java.lang.String DIR_FILES = "files";
    private static final java.lang.String DIR_MEDIA = "media";
    private static final java.lang.String DIR_OBB = "obb";
    public static final java.lang.String DIR_USER_CE = "user";
    public static final java.lang.String DIR_USER_DE = "user_de";
    private static final java.lang.String ENV_EXTERNAL_STORAGE = "EXTERNAL_STORAGE";
    private static final long FORCE_ENABLE_SCOPED_STORAGE = 132649864;
    public static final int HAS_ALARMS = 8;
    public static final int HAS_ANDROID = 65536;
    public static final int HAS_AUDIOBOOKS = 1024;
    public static final int HAS_DCIM = 256;
    public static final int HAS_DOCUMENTS = 512;
    public static final int HAS_DOWNLOADS = 128;
    public static final int HAS_MOVIES = 64;
    public static final int HAS_MUSIC = 1;
    public static final int HAS_NOTIFICATIONS = 16;
    public static final int HAS_OTHER = 131072;
    public static final int HAS_PICTURES = 32;
    public static final int HAS_PODCASTS = 2;
    public static final int HAS_RECORDINGS = 2048;
    public static final int HAS_RINGTONES = 4;
    public static final java.lang.String MEDIA_BAD_REMOVAL = "bad_removal";
    public static final java.lang.String MEDIA_CHECKING = "checking";
    public static final java.lang.String MEDIA_EJECTING = "ejecting";
    public static final java.lang.String MEDIA_MOUNTED = "mounted";
    public static final java.lang.String MEDIA_MOUNTED_READ_ONLY = "mounted_ro";
    public static final java.lang.String MEDIA_NOFS = "nofs";
    public static final java.lang.String MEDIA_REMOVED = "removed";
    public static final java.lang.String MEDIA_SHARED = "shared";
    public static final java.lang.String MEDIA_UNKNOWN = "unknown";
    public static final java.lang.String MEDIA_UNMOUNTABLE = "unmountable";
    public static final java.lang.String MEDIA_UNMOUNTED = "unmounted";
    public static final java.lang.String[] STANDARD_DIRECTORIES;
    private static final java.lang.String TAG = "Environment";
    private static android.os.Environment.UserEnvironment sCurrentUser;
    private static boolean sUserRequired;
    private static final java.lang.String ENV_ANDROID_ROOT = "ANDROID_ROOT";
    private static final java.io.File DIR_ANDROID_ROOT = getDirectory(ENV_ANDROID_ROOT, "/system");
    private static final java.lang.String ENV_ANDROID_DATA = "ANDROID_DATA";
    private static final java.lang.String DIR_ANDROID_DATA_PATH = getDirectoryPath(ENV_ANDROID_DATA, "/data");
    private static final java.io.File DIR_ANDROID_DATA = new java.io.File(DIR_ANDROID_DATA_PATH);
    private static final java.lang.String ENV_ANDROID_EXPAND = "ANDROID_EXPAND";
    private static final java.io.File DIR_ANDROID_EXPAND = getDirectory(ENV_ANDROID_EXPAND, "/mnt/expand");
    private static final java.lang.String ENV_ANDROID_STORAGE = "ANDROID_STORAGE";
    private static final java.io.File DIR_ANDROID_STORAGE = getDirectory(ENV_ANDROID_STORAGE, "/storage");
    private static final java.lang.String ENV_DOWNLOAD_CACHE = "DOWNLOAD_CACHE";
    private static final java.io.File DIR_DOWNLOAD_CACHE = getDirectory(ENV_DOWNLOAD_CACHE, "/cache");
    private static final java.io.File DIR_METADATA = new java.io.File("/metadata");
    private static final java.lang.String ENV_OEM_ROOT = "OEM_ROOT";
    private static final java.io.File DIR_OEM_ROOT = getDirectory(ENV_OEM_ROOT, "/oem");
    private static final java.lang.String ENV_ODM_ROOT = "ODM_ROOT";
    private static final java.io.File DIR_ODM_ROOT = getDirectory(ENV_ODM_ROOT, "/odm");
    private static final java.lang.String ENV_VENDOR_ROOT = "VENDOR_ROOT";
    private static final java.io.File DIR_VENDOR_ROOT = getDirectory(ENV_VENDOR_ROOT, "/vendor");
    private static final java.lang.String ENV_PRODUCT_ROOT = "PRODUCT_ROOT";
    private static final java.io.File DIR_PRODUCT_ROOT = getDirectory(ENV_PRODUCT_ROOT, "/product");
    private static final java.lang.String ENV_SYSTEM_EXT_ROOT = "SYSTEM_EXT_ROOT";
    private static final java.io.File DIR_SYSTEM_EXT_ROOT = getDirectory(ENV_SYSTEM_EXT_ROOT, "/system_ext");
    private static final java.lang.String ENV_APEX_ROOT = "APEX_ROOT";
    private static final java.io.File DIR_APEX_ROOT = getDirectory(ENV_APEX_ROOT, "/apex");

    static {
        initForCurrentUser();
        DIRECTORY_MUSIC = "Music";
        DIRECTORY_PODCASTS = "Podcasts";
        DIRECTORY_RINGTONES = "Ringtones";
        DIRECTORY_ALARMS = "Alarms";
        DIRECTORY_NOTIFICATIONS = "Notifications";
        DIRECTORY_PICTURES = "Pictures";
        DIRECTORY_MOVIES = "Movies";
        DIRECTORY_DOWNLOADS = "Download";
        DIRECTORY_DCIM = "DCIM";
        DIRECTORY_DOCUMENTS = "Documents";
        DIRECTORY_SCREENSHOTS = "Screenshots";
        DIRECTORY_AUDIOBOOKS = "Audiobooks";
        DIRECTORY_RECORDINGS = "Recordings";
        STANDARD_DIRECTORIES = new java.lang.String[]{DIRECTORY_MUSIC, DIRECTORY_PODCASTS, DIRECTORY_RINGTONES, DIRECTORY_ALARMS, DIRECTORY_NOTIFICATIONS, DIRECTORY_PICTURES, DIRECTORY_MOVIES, DIRECTORY_DOWNLOADS, DIRECTORY_DCIM, DIRECTORY_DOCUMENTS, DIRECTORY_AUDIOBOOKS, DIRECTORY_RECORDINGS};
    }

    public static void initForCurrentUser() {
        sCurrentUser = new android.os.Environment.UserEnvironment(android.os.UserHandle.myUserId());
    }

    public static class UserEnvironment {
        private final int mUserId;

        public UserEnvironment(int i) {
            this.mUserId = i;
        }

        public java.io.File[] getExternalDirs() {
            android.os.storage.StorageVolume[] volumeList = android.os.storage.StorageManager.getVolumeList(this.mUserId, 256);
            java.io.File[] fileArr = new java.io.File[volumeList.length];
            for (int i = 0; i < volumeList.length; i++) {
                fileArr[i] = volumeList[i].getPathFile();
            }
            return fileArr;
        }

        public java.io.File getExternalStorageDirectory() {
            return getExternalDirs()[0];
        }

        public java.io.File getExternalStoragePublicDirectory(java.lang.String str) {
            return buildExternalStoragePublicDirs(str)[0];
        }

        public java.io.File[] buildExternalStoragePublicDirs(java.lang.String str) {
            return android.os.Environment.buildPaths(getExternalDirs(), str);
        }

        public java.io.File[] buildExternalStorageAndroidDataDirs() {
            return android.os.Environment.buildPaths(getExternalDirs(), "Android", "data");
        }

        public java.io.File[] buildExternalStorageAndroidObbDirs() {
            return android.os.Environment.buildPaths(getExternalDirs(), "Android", "obb");
        }

        public java.io.File[] buildExternalStorageAppDataDirs(java.lang.String str) {
            return android.os.Environment.buildPaths(getExternalDirs(), "Android", "data", str);
        }

        public java.io.File[] buildExternalStorageAppMediaDirs(java.lang.String str) {
            return android.os.Environment.buildPaths(getExternalDirs(), "Android", android.os.Environment.DIR_MEDIA, str);
        }

        public java.io.File[] buildExternalStorageAppObbDirs(java.lang.String str) {
            return android.os.Environment.buildPaths(getExternalDirs(), "Android", "obb", str);
        }

        public java.io.File[] buildExternalStorageAppFilesDirs(java.lang.String str) {
            return android.os.Environment.buildPaths(getExternalDirs(), "Android", "data", str, android.os.Environment.DIR_FILES);
        }

        public java.io.File[] buildExternalStorageAppCacheDirs(java.lang.String str) {
            return android.os.Environment.buildPaths(getExternalDirs(), "Android", "data", str, android.os.Environment.DIR_CACHE);
        }
    }

    public static java.io.File getRootDirectory() {
        return DIR_ANDROID_ROOT;
    }

    public static java.io.File getStorageDirectory() {
        return DIR_ANDROID_STORAGE;
    }

    @android.annotation.SystemApi
    public static java.io.File getOemDirectory() {
        return DIR_OEM_ROOT;
    }

    @android.annotation.SystemApi
    public static java.io.File getOdmDirectory() {
        return DIR_ODM_ROOT;
    }

    @android.annotation.SystemApi
    public static java.io.File getVendorDirectory() {
        return DIR_VENDOR_ROOT;
    }

    @android.annotation.SystemApi
    public static java.io.File getProductDirectory() {
        return DIR_PRODUCT_ROOT;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static java.io.File getProductServicesDirectory() {
        return getDirectory("PRODUCT_SERVICES_ROOT", "/product_services");
    }

    @android.annotation.SystemApi
    public static java.io.File getSystemExtDirectory() {
        return DIR_SYSTEM_EXT_ROOT;
    }

    public static java.io.File getApexDirectory() {
        return DIR_APEX_ROOT;
    }

    @java.lang.Deprecated
    public static java.io.File getUserSystemDirectory(int i) {
        return new java.io.File(new java.io.File(getDataSystemDirectory(), "users"), java.lang.Integer.toString(i));
    }

    @java.lang.Deprecated
    public static java.io.File getUserConfigDirectory(int i) {
        return new java.io.File(new java.io.File(new java.io.File(getDataDirectory(), "misc"), "user"), java.lang.Integer.toString(i));
    }

    public static java.io.File getDataDirectory() {
        return DIR_ANDROID_DATA;
    }

    public static java.lang.String getDataDirectoryPath() {
        return DIR_ANDROID_DATA_PATH;
    }

    public static java.io.File getDataDirectory(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return DIR_ANDROID_DATA;
        }
        return new java.io.File("/mnt/expand/" + str);
    }

    public static java.lang.String getDataDirectoryPath(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return DIR_ANDROID_DATA_PATH;
        }
        return getExpandDirectory().getAbsolutePath() + java.io.File.separator + str;
    }

    public static java.io.File getExpandDirectory() {
        return DIR_ANDROID_EXPAND;
    }

    public static java.io.File getDataSystemDirectory() {
        return new java.io.File(getDataDirectory(), "system");
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static java.io.File getDataSystemDeDirectory() {
        return buildPath(getDataDirectory(), "system_de");
    }

    public static java.io.File getDataSystemCeDirectory() {
        return buildPath(getDataDirectory(), "system_ce");
    }

    public static java.io.File getDataSystemCeDirectory(int i) {
        return buildPath(getDataDirectory(), "system_ce", java.lang.String.valueOf(i));
    }

    public static java.io.File getDataSystemDeDirectory(int i) {
        return buildPath(getDataDirectory(), "system_de", java.lang.String.valueOf(i));
    }

    public static java.io.File getDataMiscDirectory() {
        return new java.io.File(getDataDirectory(), "misc");
    }

    public static java.io.File getDataMiscCeDirectory() {
        return buildPath(getDataDirectory(), "misc_ce");
    }

    public static java.io.File getDataMiscCeDirectory(int i) {
        return buildPath(getDataDirectory(), "misc_ce", java.lang.String.valueOf(i));
    }

    private static java.io.File getDataMiscCeDirectory(java.lang.String str, int i) {
        return buildPath(getDataDirectory(str), "misc_ce", java.lang.String.valueOf(i));
    }

    public static java.io.File getDataMiscCeSharedSdkSandboxDirectory(java.lang.String str, int i, java.lang.String str2) {
        return buildPath(getDataMiscCeDirectory(str, i), "sdksandbox", str2, "shared");
    }

    public static java.io.File getDataMiscDeDirectory(int i) {
        return buildPath(getDataDirectory(), "misc_de", java.lang.String.valueOf(i));
    }

    private static java.io.File getDataMiscDeDirectory(java.lang.String str, int i) {
        return buildPath(getDataDirectory(str), "misc_de", java.lang.String.valueOf(i));
    }

    public static java.io.File getDataMiscDeSharedSdkSandboxDirectory(java.lang.String str, int i, java.lang.String str2) {
        return buildPath(getDataMiscDeDirectory(str, i), "sdksandbox", str2, "shared");
    }

    private static java.io.File getDataProfilesDeDirectory(int i) {
        return buildPath(getDataDirectory(), "misc", "profiles", "cur", java.lang.String.valueOf(i));
    }

    public static java.io.File getDataVendorCeDirectory(int i) {
        return buildPath(getDataDirectory(), "vendor_ce", java.lang.String.valueOf(i));
    }

    public static java.io.File getDataVendorDeDirectory(int i) {
        return buildPath(getDataDirectory(), "vendor_de", java.lang.String.valueOf(i));
    }

    public static java.io.File getDataRefProfilesDePackageDirectory(java.lang.String str) {
        return buildPath(getDataDirectory(), "misc", "profiles", "ref", str);
    }

    public static java.io.File getDataProfilesDePackageDirectory(int i, java.lang.String str) {
        return buildPath(getDataProfilesDeDirectory(i), str);
    }

    public static java.io.File getDataAppDirectory(java.lang.String str) {
        return new java.io.File(getDataDirectory(str), "app");
    }

    public static java.io.File getDataStagingDirectory(java.lang.String str) {
        return new java.io.File(getDataDirectory(str), "app-staging");
    }

    public static java.io.File getDataUserCeDirectory(java.lang.String str) {
        return new java.io.File(getDataDirectory(str), "user");
    }

    public static java.io.File getDataUserCeDirectory(java.lang.String str, int i) {
        return new java.io.File(getDataUserCeDirectory(str), java.lang.String.valueOf(i));
    }

    public static java.io.File getDataUserCePackageDirectory(java.lang.String str, int i, java.lang.String str2) {
        return new java.io.File(getDataUserCeDirectory(str, i), str2);
    }

    @android.annotation.SystemApi
    public static java.io.File getDataCePackageDirectoryForUser(java.util.UUID uuid, android.os.UserHandle userHandle, java.lang.String str) {
        return getDataUserCePackageDirectory(android.os.storage.StorageManager.convert(uuid), userHandle.getIdentifier(), str);
    }

    public static java.io.File getDataUserDeDirectory(java.lang.String str) {
        return new java.io.File(getDataDirectory(str), DIR_USER_DE);
    }

    public static java.io.File getDataUserDeDirectory(java.lang.String str, int i) {
        return new java.io.File(getDataUserDeDirectory(str), java.lang.String.valueOf(i));
    }

    public static java.io.File getDataUserDePackageDirectory(java.lang.String str, int i, java.lang.String str2) {
        return new java.io.File(getDataUserDeDirectory(str, i), str2);
    }

    @android.annotation.SystemApi
    public static java.io.File getDataDePackageDirectoryForUser(java.util.UUID uuid, android.os.UserHandle userHandle, java.lang.String str) {
        return getDataUserDePackageDirectory(android.os.storage.StorageManager.convert(uuid), userHandle.getIdentifier(), str);
    }

    public static java.io.File getDataPreloadsDirectory() {
        return new java.io.File(getDataDirectory(), "preloads");
    }

    public static java.io.File getDataPreloadsDemoDirectory() {
        return new java.io.File(getDataPreloadsDirectory(), "demo");
    }

    public static java.io.File getDataPreloadsAppsDirectory() {
        return new java.io.File(getDataPreloadsDirectory(), "apps");
    }

    public static java.io.File getDataPreloadsMediaDirectory() {
        return new java.io.File(getDataPreloadsDirectory(), DIR_MEDIA);
    }

    public static java.io.File getDataPreloadsFileCacheDirectory(java.lang.String str) {
        return new java.io.File(getDataPreloadsFileCacheDirectory(), str);
    }

    public static java.io.File getDataPreloadsFileCacheDirectory() {
        return new java.io.File(getDataPreloadsDirectory(), "file_cache");
    }

    public static java.io.File getPackageCacheDirectory() {
        return new java.io.File(getDataSystemDirectory(), "package_cache");
    }

    @android.annotation.SystemApi
    public static java.util.Collection<java.io.File> getInternalMediaDirectories() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        addCanonicalFile(arrayList, new java.io.File(getRootDirectory(), DIR_MEDIA));
        addCanonicalFile(arrayList, new java.io.File(getOemDirectory(), DIR_MEDIA));
        addCanonicalFile(arrayList, new java.io.File(getProductDirectory(), DIR_MEDIA));
        return arrayList;
    }

    private static void addCanonicalFile(java.util.List<java.io.File> list, java.io.File file) {
        try {
            list.add(file.getCanonicalFile());
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, "Failed to resolve " + file + ": " + e);
            list.add(file);
        }
    }

    public static java.io.File getExternalStorageDirectory() {
        throwIfUserRequired();
        return sCurrentUser.getExternalDirs()[0];
    }

    public static java.io.File getLegacyExternalStorageDirectory() {
        return new java.io.File(java.lang.System.getenv(ENV_EXTERNAL_STORAGE));
    }

    public static java.io.File getLegacyExternalStorageObbDirectory() {
        return buildPath(getLegacyExternalStorageDirectory(), "Android", "obb");
    }

    public static boolean isStandardDirectory(java.lang.String str) {
        for (java.lang.String str2 : STANDARD_DIRECTORIES) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static int classifyExternalStorageDirectory(java.io.File file) {
        int i = 0;
        for (java.io.File file2 : android.os.FileUtils.listFilesOrEmpty(file)) {
            if (file2.isFile() && isInterestingFile(file2)) {
                i |= 131072;
            } else if (file2.isDirectory() && hasInterestingFiles(file2)) {
                java.lang.String name = file2.getName();
                if (DIRECTORY_MUSIC.equals(name)) {
                    i |= 1;
                } else if (DIRECTORY_PODCASTS.equals(name)) {
                    i |= 2;
                } else if (DIRECTORY_RINGTONES.equals(name)) {
                    i |= 4;
                } else if (DIRECTORY_ALARMS.equals(name)) {
                    i |= 8;
                } else if (DIRECTORY_NOTIFICATIONS.equals(name)) {
                    i |= 16;
                } else if (DIRECTORY_PICTURES.equals(name)) {
                    i |= 32;
                } else if (DIRECTORY_MOVIES.equals(name)) {
                    i |= 64;
                } else if (DIRECTORY_DOWNLOADS.equals(name)) {
                    i |= 128;
                } else if (DIRECTORY_DCIM.equals(name)) {
                    i |= 256;
                } else if (DIRECTORY_DOCUMENTS.equals(name)) {
                    i |= 512;
                } else if (DIRECTORY_AUDIOBOOKS.equals(name)) {
                    i |= 1024;
                } else if (DIRECTORY_RECORDINGS.equals(name)) {
                    i |= 2048;
                } else {
                    i = "Android".equals(name) ? i | 65536 : i | 131072;
                }
            }
        }
        return i;
    }

    private static boolean hasInterestingFiles(java.io.File file) {
        java.util.ArrayDeque arrayDeque = new java.util.ArrayDeque();
        arrayDeque.add(file);
        while (true) {
            if (arrayDeque.isEmpty()) {
                return false;
            }
            for (java.io.File file2 : android.os.FileUtils.listFilesOrEmpty((java.io.File) arrayDeque.pop())) {
                if (isInterestingFile(file2)) {
                    return true;
                }
                if (file2.isDirectory()) {
                    arrayDeque.add(file2);
                }
            }
        }
    }

    private static boolean isInterestingFile(java.io.File file) {
        if (!file.isFile()) {
            return false;
        }
        java.lang.String lowerCase = file.getName().toLowerCase();
        return (lowerCase.endsWith(".exe") || lowerCase.equals("autorun.inf") || lowerCase.equals("launchpad.zip") || lowerCase.equals(".nomedia")) ? false : true;
    }

    public static java.io.File getExternalStoragePublicDirectory(java.lang.String str) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStoragePublicDirs(str)[0];
    }

    public static java.io.File[] buildExternalStorageAndroidDataDirs() {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAndroidDataDirs();
    }

    public static java.io.File[] buildExternalStorageAndroidObbDirs() {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAndroidObbDirs();
    }

    public static java.io.File[] buildExternalStorageAppDataDirs(java.lang.String str) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppDataDirs(str);
    }

    public static java.io.File[] buildExternalStorageAppMediaDirs(java.lang.String str) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppMediaDirs(str);
    }

    public static java.io.File[] buildExternalStorageAppObbDirs(java.lang.String str) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppObbDirs(str);
    }

    public static java.io.File[] buildExternalStorageAppFilesDirs(java.lang.String str) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppFilesDirs(str);
    }

    public static java.io.File[] buildExternalStorageAppCacheDirs(java.lang.String str) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStorageAppCacheDirs(str);
    }

    public static java.io.File[] buildExternalStoragePublicDirs(java.lang.String str) {
        throwIfUserRequired();
        return sCurrentUser.buildExternalStoragePublicDirs(str);
    }

    public static java.io.File getDownloadCacheDirectory() {
        return DIR_DOWNLOAD_CACHE;
    }

    public static java.io.File getMetadataDirectory() {
        return DIR_METADATA;
    }

    public static java.lang.String getExternalStorageState() {
        return getExternalStorageState(sCurrentUser.getExternalDirs()[0]);
    }

    @java.lang.Deprecated
    public static java.lang.String getStorageState(java.io.File file) {
        return getExternalStorageState(file);
    }

    public static java.lang.String getExternalStorageState(java.io.File file) {
        android.os.storage.StorageVolume storageVolume = android.os.storage.StorageManager.getStorageVolume(file, android.os.UserHandle.myUserId());
        if (storageVolume != null) {
            return storageVolume.getState();
        }
        return "unknown";
    }

    public static boolean isExternalStorageRemovable() {
        return isExternalStorageRemovable(sCurrentUser.getExternalDirs()[0]);
    }

    public static boolean isExternalStorageRemovable(java.io.File file) {
        android.os.storage.StorageVolume storageVolume = android.os.storage.StorageManager.getStorageVolume(file, android.os.UserHandle.myUserId());
        if (storageVolume != null) {
            return storageVolume.isRemovable();
        }
        throw new java.lang.IllegalArgumentException("Failed to find storage device at " + file);
    }

    public static boolean isExternalStorageEmulated() {
        return isExternalStorageEmulated(sCurrentUser.getExternalDirs()[0]);
    }

    public static boolean isExternalStorageEmulated(java.io.File file) {
        android.os.storage.StorageVolume storageVolume = android.os.storage.StorageManager.getStorageVolume(file, android.os.UserHandle.myUserId());
        if (storageVolume != null) {
            return storageVolume.isEmulated();
        }
        throw new java.lang.IllegalArgumentException("Failed to find storage device at " + file);
    }

    public static boolean isExternalStorageLegacy() {
        return isExternalStorageLegacy(sCurrentUser.getExternalDirs()[0]);
    }

    public static boolean isExternalStorageLegacy(java.io.File file) {
        android.app.Application initialApplication = android.app.AppGlobals.getInitialApplication();
        int i = initialApplication.getApplicationInfo().uid;
        if (android.os.Process.isIsolated(i) || android.os.Process.isSdkSandboxUid(i)) {
            return false;
        }
        android.content.pm.PackageManager packageManager = initialApplication.getPackageManager();
        if (packageManager.isInstantApp()) {
            return false;
        }
        try {
            android.content.pm.PackageManager.Property property = packageManager.getProperty(android.content.pm.PackageManager.PROPERTY_NO_APP_DATA_STORAGE, android.app.AppGlobals.getInitialPackage());
            if (property != null) {
                if (property.getBoolean()) {
                    return false;
                }
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        }
        boolean isChangeEnabled = android.compat.Compatibility.isChangeEnabled(DEFAULT_SCOPED_STORAGE);
        boolean isChangeEnabled2 = android.compat.Compatibility.isChangeEnabled(FORCE_ENABLE_SCOPED_STORAGE);
        if (isScopedStorageEnforced(isChangeEnabled, isChangeEnabled2)) {
            return false;
        }
        if (isScopedStorageDisabled(isChangeEnabled, isChangeEnabled2)) {
            return true;
        }
        android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) initialApplication.getSystemService(android.app.AppOpsManager.class);
        java.lang.String opPackageName = initialApplication.getOpPackageName();
        return appOpsManager.noteOpNoThrow(87, i, opPackageName) == 0 || appOpsManager.noteOpNoThrow(99, i, opPackageName) == 0;
    }

    private static boolean isScopedStorageEnforced(boolean z, boolean z2) {
        return z && z2;
    }

    private static boolean isScopedStorageDisabled(boolean z, boolean z2) {
        return (z || z2) ? false : true;
    }

    public static boolean isExternalStorageManager() {
        return isExternalStorageManager(sCurrentUser.getExternalDirs()[0]);
    }

    public static boolean isExternalStorageManager(java.io.File file) {
        android.content.Context context = (android.content.Context) java.util.Objects.requireNonNull(android.app.AppGlobals.getInitialApplication());
        java.lang.String str = (java.lang.String) java.util.Objects.requireNonNull(context.getPackageName());
        int i = context.getApplicationInfo().uid;
        int checkOpNoThrow = ((android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class)).checkOpNoThrow(92, i, str);
        switch (checkOpNoThrow) {
            case 0:
                return true;
            case 1:
            case 2:
                return false;
            case 3:
                return context.checkPermission(android.Manifest.permission.MANAGE_EXTERNAL_STORAGE, android.os.Process.myPid(), i) == 0;
            default:
                throw new java.lang.IllegalStateException("Unknown AppOpsManager mode " + checkOpNoThrow);
        }
    }

    static java.io.File getDirectory(java.lang.String str, java.lang.String str2) {
        java.lang.String str3 = java.lang.System.getenv(str);
        return str3 == null ? new java.io.File(str2) : new java.io.File(str3);
    }

    static java.lang.String getDirectoryPath(java.lang.String str, java.lang.String str2) {
        java.lang.String str3 = java.lang.System.getenv(str);
        return str3 == null ? str2 : str3;
    }

    public static void setUserRequired(boolean z) {
        sUserRequired = z;
    }

    private static void throwIfUserRequired() {
        if (sUserRequired) {
            android.util.Log.wtf(TAG, "Path requests must specify a user by using UserEnvironment", new java.lang.Throwable());
        }
    }

    public static java.io.File[] buildPaths(java.io.File[] fileArr, java.lang.String... strArr) {
        java.io.File[] fileArr2 = new java.io.File[fileArr.length];
        for (int i = 0; i < fileArr.length; i++) {
            fileArr2[i] = buildPath(fileArr[i], strArr);
        }
        return fileArr2;
    }

    public static java.io.File buildPath(java.io.File file, java.lang.String... strArr) {
        for (java.lang.String str : strArr) {
            if (file == null) {
                file = new java.io.File(str);
            } else {
                file = new java.io.File(file, str);
            }
        }
        return file;
    }

    @java.lang.Deprecated
    public static java.io.File maybeTranslateEmulatedPathToInternal(java.io.File file) {
        return android.os.storage.StorageManager.maybeTranslateEmulatedPathToInternal(file);
    }
}
