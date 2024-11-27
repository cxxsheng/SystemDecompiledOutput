package android.app;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ContextImpl extends android.content.Context {
    private static final int CONTEXT_TYPE_ACTIVITY = 2;
    private static final int CONTEXT_TYPE_DISPLAY_CONTEXT = 1;
    private static final int CONTEXT_TYPE_NON_UI = 0;
    private static final int CONTEXT_TYPE_SYSTEM_OR_SYSTEM_UI = 4;
    private static final int CONTEXT_TYPE_WINDOW_CONTEXT = 3;
    private static final boolean DEBUG = false;
    static final int STATE_INITIALIZING = 1;
    static final int STATE_NOT_FOUND = 3;
    static final int STATE_READY = 2;
    static final int STATE_UNINITIALIZED = 0;
    private static final java.lang.String TAG = "ContextImpl";
    private static final java.lang.String XATTR_INODE_CACHE = "user.inode_cache";
    private static final java.lang.String XATTR_INODE_CODE_CACHE = "user.inode_code_cache";
    private static android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.io.File, android.app.SharedPreferencesImpl>> sSharedPrefsCache;
    private android.content.AttributionSource mAttributionSource;
    private android.content.AutofillOptions mAutofillOptions;
    private final java.lang.String mBasePackageName;
    private java.io.File mCacheDir;
    private java.lang.ClassLoader mClassLoader;
    private java.io.File mCodeCacheDir;
    private android.content.ContentCaptureOptions mContentCaptureOptions;
    private final android.app.ContextImpl.ApplicationContentResolver mContentResolver;
    private int mContextType;
    private java.io.File mCratesDir;
    private java.io.File mDatabasesDir;
    private int mDeviceId;
    private java.util.ArrayList<android.app.ContextImpl.DeviceIdChangeListenerDelegate> mDeviceIdChangeListeners;
    private android.view.Display mDisplay;
    private java.io.File mFilesDir;
    private final int mFlags;
    private boolean mForceDisplayOverrideInResources;
    private boolean mIsConfigurationBasedContext;
    private boolean mIsExplicitDeviceId;
    final android.app.ActivityThread mMainThread;
    private java.io.File mNoBackupFilesDir;
    private final java.lang.String mOpPackageName;
    final android.app.LoadedApk mPackageInfo;
    private android.content.pm.PackageManager mPackageManager;
    private final android.content.ContextParams mParams;
    private java.io.File mPreferencesDir;
    private android.content.res.Resources mResources;
    private final android.app.ResourcesManager mResourcesManager;
    private android.util.ArrayMap<java.lang.String, java.io.File> mSharedPrefsPaths;
    private java.lang.String mSplitName;
    private final android.os.IBinder mToken;
    private final android.os.UserHandle mUser;
    private int mThemeResource = 0;
    private android.content.res.Resources.Theme mTheme = null;
    private android.content.Context mReceiverRestrictedContext = null;
    private android.view.autofill.AutofillManager.AutofillClient mAutofillClient = null;
    private final java.lang.Object mSync = new java.lang.Object();
    private boolean mOwnsToken = false;
    final java.lang.Object[] mServiceCache = android.app.SystemServiceRegistry.createServiceCache();
    final int[] mServiceInitializationStateArray = new int[this.mServiceCache.length];
    private final java.lang.Object mDeviceIdListenerLock = new java.lang.Object();
    private android.content.Context mOuterContext = this;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface ContextType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ServiceInitializationState {
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class DeviceIdChangeListenerDelegate {
        final java.util.concurrent.Executor mExecutor;
        final java.util.function.IntConsumer mListener;

        DeviceIdChangeListenerDelegate(java.util.function.IntConsumer intConsumer, java.util.concurrent.Executor executor) {
            this.mListener = intConsumer;
            this.mExecutor = executor;
        }
    }

    static android.app.ContextImpl getImpl(android.content.Context context) {
        android.content.Context baseContext;
        while ((context instanceof android.content.ContextWrapper) && (baseContext = ((android.content.ContextWrapper) context).getBaseContext()) != null) {
            context = baseContext;
        }
        return (android.app.ContextImpl) context;
    }

    @Override // android.content.Context
    public android.content.res.AssetManager getAssets() {
        return getResources().getAssets();
    }

    @Override // android.content.Context
    public android.content.res.Resources getResources() {
        return this.mResources;
    }

    @Override // android.content.Context
    public android.content.pm.PackageManager getPackageManager() {
        if (this.mPackageManager != null) {
            return this.mPackageManager;
        }
        android.content.pm.IPackageManager packageManager = android.app.ActivityThread.getPackageManager();
        if (packageManager != null) {
            android.app.ApplicationPackageManager applicationPackageManager = new android.app.ApplicationPackageManager(this, packageManager);
            this.mPackageManager = applicationPackageManager;
            return applicationPackageManager;
        }
        return null;
    }

    @Override // android.content.Context
    public android.content.ContentResolver getContentResolver() {
        return this.mContentResolver;
    }

    @Override // android.content.Context
    public android.os.Looper getMainLooper() {
        return this.mMainThread.getLooper();
    }

    @Override // android.content.Context
    public java.util.concurrent.Executor getMainExecutor() {
        return this.mMainThread.getExecutor();
    }

    @Override // android.content.Context
    public android.content.Context getApplicationContext() {
        return this.mPackageInfo != null ? this.mPackageInfo.getApplication() : this.mMainThread.getApplication();
    }

    @Override // android.content.Context
    public void setTheme(int i) {
        synchronized (this.mSync) {
            if (this.mThemeResource != i) {
                this.mThemeResource = i;
                initializeTheme();
            }
        }
    }

    @Override // android.content.Context
    public int getThemeResId() {
        int i;
        synchronized (this.mSync) {
            i = this.mThemeResource;
        }
        return i;
    }

    @Override // android.content.Context
    public android.content.res.Resources.Theme getTheme() {
        synchronized (this.mSync) {
            if (this.mTheme != null) {
                return this.mTheme;
            }
            this.mThemeResource = android.content.res.Resources.selectDefaultTheme(this.mThemeResource, getOuterContext().getApplicationInfo().targetSdkVersion);
            initializeTheme();
            return this.mTheme;
        }
    }

    private void initializeTheme() {
        if (this.mTheme == null) {
            this.mTheme = this.mResources.newTheme();
        }
        this.mTheme.applyStyle(this.mThemeResource, true);
    }

    @Override // android.content.Context
    public java.lang.ClassLoader getClassLoader() {
        return this.mClassLoader != null ? this.mClassLoader : this.mPackageInfo != null ? this.mPackageInfo.getClassLoader() : java.lang.ClassLoader.getSystemClassLoader();
    }

    @Override // android.content.Context
    public java.lang.String getPackageName() {
        if (this.mPackageInfo != null) {
            return this.mPackageInfo.getPackageName();
        }
        return "android";
    }

    @Override // android.content.Context
    public java.lang.String getBasePackageName() {
        return this.mBasePackageName != null ? this.mBasePackageName : getPackageName();
    }

    @Override // android.content.Context
    public java.lang.String getOpPackageName() {
        return this.mAttributionSource.getPackageName();
    }

    @Override // android.content.Context
    public java.lang.String getAttributionTag() {
        return this.mAttributionSource.getAttributionTag();
    }

    @Override // android.content.Context
    public android.content.ContextParams getParams() {
        return this.mParams;
    }

    @Override // android.content.Context
    public android.content.AttributionSource getAttributionSource() {
        return this.mAttributionSource;
    }

    @Override // android.content.Context
    public android.content.pm.ApplicationInfo getApplicationInfo() {
        if (this.mPackageInfo != null) {
            return this.mPackageInfo.getApplicationInfo();
        }
        throw new java.lang.RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public java.lang.String getPackageResourcePath() {
        if (this.mPackageInfo != null) {
            return this.mPackageInfo.getResDir();
        }
        throw new java.lang.RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public java.lang.String getPackageCodePath() {
        if (this.mPackageInfo != null) {
            return this.mPackageInfo.getAppDir();
        }
        throw new java.lang.RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public android.content.SharedPreferences getSharedPreferences(java.lang.String str, int i) {
        java.io.File file;
        if (this.mPackageInfo.getApplicationInfo().targetSdkVersion < 19 && str == null) {
            str = "null";
        }
        synchronized (android.app.ContextImpl.class) {
            if (this.mSharedPrefsPaths == null) {
                this.mSharedPrefsPaths = new android.util.ArrayMap<>();
            }
            file = this.mSharedPrefsPaths.get(str);
            if (file == null) {
                file = getSharedPreferencesPath(str);
                this.mSharedPrefsPaths.put(str, file);
            }
        }
        return getSharedPreferences(file, i);
    }

    @Override // android.content.Context
    public android.content.SharedPreferences getSharedPreferences(java.io.File file, int i) {
        synchronized (android.app.ContextImpl.class) {
            android.util.ArrayMap<java.io.File, android.app.SharedPreferencesImpl> sharedPreferencesCacheLocked = getSharedPreferencesCacheLocked();
            android.app.SharedPreferencesImpl sharedPreferencesImpl = sharedPreferencesCacheLocked.get(file);
            if (sharedPreferencesImpl == null) {
                checkMode(i);
                if (getApplicationInfo().targetSdkVersion >= 26 && isCredentialProtectedStorage() && !((android.os.UserManager) getSystemService(android.os.UserManager.class)).isUserUnlockingOrUnlocked(android.os.UserHandle.myUserId())) {
                    throw new java.lang.IllegalStateException("SharedPreferences in credential encrypted storage are not available until after user (id " + android.os.UserHandle.myUserId() + ") is unlocked");
                }
                android.app.SharedPreferencesImpl sharedPreferencesImpl2 = new android.app.SharedPreferencesImpl(file, i);
                sharedPreferencesCacheLocked.put(file, sharedPreferencesImpl2);
                return sharedPreferencesImpl2;
            }
            if ((i & 4) != 0 || getApplicationInfo().targetSdkVersion < 11) {
                sharedPreferencesImpl.startReloadIfChangedUnexpectedly();
            }
            return sharedPreferencesImpl;
        }
    }

    private android.util.ArrayMap<java.io.File, android.app.SharedPreferencesImpl> getSharedPreferencesCacheLocked() {
        if (sSharedPrefsCache == null) {
            sSharedPrefsCache = new android.util.ArrayMap<>();
        }
        java.lang.String packageName = getPackageName();
        android.util.ArrayMap<java.io.File, android.app.SharedPreferencesImpl> arrayMap = sSharedPrefsCache.get(packageName);
        if (arrayMap == null) {
            android.util.ArrayMap<java.io.File, android.app.SharedPreferencesImpl> arrayMap2 = new android.util.ArrayMap<>();
            sSharedPrefsCache.put(packageName, arrayMap2);
            return arrayMap2;
        }
        return arrayMap;
    }

    @Override // android.content.Context
    public void reloadSharedPreferences() {
        int i;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (android.app.ContextImpl.class) {
            android.util.ArrayMap<java.io.File, android.app.SharedPreferencesImpl> sharedPreferencesCacheLocked = getSharedPreferencesCacheLocked();
            for (int i2 = 0; i2 < sharedPreferencesCacheLocked.size(); i2++) {
                android.app.SharedPreferencesImpl valueAt = sharedPreferencesCacheLocked.valueAt(i2);
                if (valueAt != null) {
                    arrayList.add(valueAt);
                }
            }
        }
        for (i = 0; i < arrayList.size(); i++) {
            ((android.app.SharedPreferencesImpl) arrayList.get(i)).startReloadIfChangedUnexpectedly();
        }
    }

    private static int moveFiles(java.io.File file, java.io.File file2, final java.lang.String str) {
        int i = 0;
        for (java.io.File file3 : android.os.FileUtils.listFilesOrEmpty(file, new java.io.FilenameFilter() { // from class: android.app.ContextImpl.1
            @Override // java.io.FilenameFilter
            public boolean accept(java.io.File file4, java.lang.String str2) {
                return str2.startsWith(str);
            }
        })) {
            java.io.File file4 = new java.io.File(file2, file3.getName());
            android.util.Log.d(TAG, "Migrating " + file3 + " to " + file4);
            try {
                android.os.FileUtils.copyFileOrThrow(file3, file4);
                android.os.FileUtils.copyPermissions(file3, file4);
            } catch (java.io.IOException e) {
                android.util.Log.w(TAG, "Failed to migrate " + file3 + ": " + e);
                i = -1;
            }
            if (!file3.delete()) {
                throw new java.io.IOException("Failed to clean up " + file3);
            }
            if (i != -1) {
                i++;
            }
        }
        return i;
    }

    @Override // android.content.Context
    public boolean moveSharedPreferencesFrom(android.content.Context context, java.lang.String str) {
        boolean z;
        synchronized (android.app.ContextImpl.class) {
            java.io.File sharedPreferencesPath = context.getSharedPreferencesPath(str);
            java.io.File sharedPreferencesPath2 = getSharedPreferencesPath(str);
            int moveFiles = moveFiles(sharedPreferencesPath.getParentFile(), sharedPreferencesPath2.getParentFile(), sharedPreferencesPath.getName());
            if (moveFiles > 0) {
                android.util.ArrayMap<java.io.File, android.app.SharedPreferencesImpl> sharedPreferencesCacheLocked = getSharedPreferencesCacheLocked();
                sharedPreferencesCacheLocked.remove(sharedPreferencesPath);
                sharedPreferencesCacheLocked.remove(sharedPreferencesPath2);
            }
            z = moveFiles != -1;
        }
        return z;
    }

    @Override // android.content.Context
    public boolean deleteSharedPreferences(java.lang.String str) {
        boolean z;
        synchronized (android.app.ContextImpl.class) {
            java.io.File sharedPreferencesPath = getSharedPreferencesPath(str);
            java.io.File makeBackupFile = android.app.SharedPreferencesImpl.makeBackupFile(sharedPreferencesPath);
            getSharedPreferencesCacheLocked().remove(sharedPreferencesPath);
            sharedPreferencesPath.delete();
            makeBackupFile.delete();
            z = (sharedPreferencesPath.exists() || makeBackupFile.exists()) ? false : true;
        }
        return z;
    }

    private java.io.File getPreferencesDir() {
        java.io.File ensurePrivateDirExists;
        synchronized (this.mSync) {
            if (this.mPreferencesDir == null) {
                this.mPreferencesDir = new java.io.File(getDataDir(), "shared_prefs");
            }
            ensurePrivateDirExists = ensurePrivateDirExists(this.mPreferencesDir);
        }
        return ensurePrivateDirExists;
    }

    @Override // android.content.Context
    public java.io.FileInputStream openFileInput(java.lang.String str) throws java.io.FileNotFoundException {
        return new java.io.FileInputStream(makeFilename(getFilesDir(), str));
    }

    @Override // android.content.Context
    public java.io.FileOutputStream openFileOutput(java.lang.String str, int i) throws java.io.FileNotFoundException {
        checkMode(i);
        boolean z = (32768 & i) != 0;
        java.io.File makeFilename = makeFilename(getFilesDir(), str);
        try {
            java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(makeFilename, z);
            setFilePermissionsFromMode(makeFilename.getPath(), i, 0);
            return fileOutputStream;
        } catch (java.io.FileNotFoundException e) {
            java.io.File parentFile = makeFilename.getParentFile();
            parentFile.mkdir();
            android.os.FileUtils.setPermissions(parentFile.getPath(), 505, -1, -1);
            java.io.FileOutputStream fileOutputStream2 = new java.io.FileOutputStream(makeFilename, z);
            setFilePermissionsFromMode(makeFilename.getPath(), i, 0);
            return fileOutputStream2;
        }
    }

    @Override // android.content.Context
    public boolean deleteFile(java.lang.String str) {
        return makeFilename(getFilesDir(), str).delete();
    }

    private static java.io.File ensurePrivateDirExists(java.io.File file) {
        return ensurePrivateDirExists(file, 505, -1, null);
    }

    private static java.io.File ensurePrivateCacheDirExists(java.io.File file, java.lang.String str) {
        return ensurePrivateDirExists(file, com.android.internal.logging.nano.MetricsProto.MetricsEvent.FIELD_PROCESS_RECORD_PROCESS_NAME, android.os.UserHandle.getCacheAppGid(android.os.Process.myUid()), str);
    }

    private static java.io.File ensurePrivateDirExists(java.io.File file, int i, int i2, java.lang.String str) {
        if (!file.exists()) {
            java.lang.String absolutePath = file.getAbsolutePath();
            try {
                android.system.Os.mkdir(absolutePath, i);
                android.system.Os.chmod(absolutePath, i);
                if (i2 != -1) {
                    android.system.Os.chown(absolutePath, -1, i2);
                }
            } catch (android.system.ErrnoException e) {
                if (e.errno != android.system.OsConstants.EEXIST) {
                    android.util.Log.w(TAG, "Failed to ensure " + file + ": " + e.getMessage());
                }
            }
            if (str != null) {
                try {
                    byte[] bArr = new byte[8];
                    libcore.io.Memory.pokeLong(bArr, 0, android.system.Os.stat(file.getAbsolutePath()).st_ino, java.nio.ByteOrder.nativeOrder());
                    android.system.Os.setxattr(file.getParentFile().getAbsolutePath(), str, bArr, 0);
                } catch (android.system.ErrnoException e2) {
                    android.util.Log.w(TAG, "Failed to update " + str + ": " + e2.getMessage());
                }
            }
        }
        return file;
    }

    @Override // android.content.Context
    public java.io.File getFilesDir() {
        java.io.File ensurePrivateDirExists;
        synchronized (this.mSync) {
            if (this.mFilesDir == null) {
                this.mFilesDir = new java.io.File(getDataDir(), "files");
            }
            ensurePrivateDirExists = ensurePrivateDirExists(this.mFilesDir);
        }
        return ensurePrivateDirExists;
    }

    @Override // android.content.Context
    public java.io.File getCrateDir(java.lang.String str) {
        com.android.internal.util.Preconditions.checkArgument(android.os.FileUtils.isValidExtFilename(str), "invalidated crateId");
        java.nio.file.Path resolve = getDataDir().toPath().resolve("crates");
        java.nio.file.Path normalize = resolve.resolve(str).toAbsolutePath().normalize();
        synchronized (this.mSync) {
            if (this.mCratesDir == null) {
                this.mCratesDir = resolve.toFile();
            }
            ensurePrivateDirExists(this.mCratesDir);
        }
        return ensurePrivateDirExists(normalize.toFile());
    }

    @Override // android.content.Context
    public java.io.File getNoBackupFilesDir() {
        java.io.File ensurePrivateDirExists;
        synchronized (this.mSync) {
            if (this.mNoBackupFilesDir == null) {
                this.mNoBackupFilesDir = new java.io.File(getDataDir(), "no_backup");
            }
            ensurePrivateDirExists = ensurePrivateDirExists(this.mNoBackupFilesDir);
        }
        return ensurePrivateDirExists;
    }

    @Override // android.content.Context
    public java.io.File getExternalFilesDir(java.lang.String str) {
        java.io.File[] externalFilesDirs = getExternalFilesDirs(str);
        if (externalFilesDirs == null || externalFilesDirs.length <= 0) {
            return null;
        }
        return externalFilesDirs[0];
    }

    @Override // android.content.Context
    public java.io.File[] getExternalFilesDirs(java.lang.String str) {
        java.io.File[] ensureExternalDirsExistOrFilter;
        synchronized (this.mSync) {
            java.io.File[] buildExternalStorageAppFilesDirs = android.os.Environment.buildExternalStorageAppFilesDirs(getPackageName());
            if (str != null) {
                buildExternalStorageAppFilesDirs = android.os.Environment.buildPaths(buildExternalStorageAppFilesDirs, str);
            }
            ensureExternalDirsExistOrFilter = ensureExternalDirsExistOrFilter(buildExternalStorageAppFilesDirs, true);
        }
        return ensureExternalDirsExistOrFilter;
    }

    @Override // android.content.Context
    public java.io.File getObbDir() {
        java.io.File[] obbDirs = getObbDirs();
        if (obbDirs == null || obbDirs.length <= 0) {
            return null;
        }
        return obbDirs[0];
    }

    @Override // android.content.Context
    public java.io.File[] getObbDirs() {
        java.io.File[] ensureExternalDirsExistOrFilter;
        synchronized (this.mSync) {
            ensureExternalDirsExistOrFilter = ensureExternalDirsExistOrFilter(android.os.Environment.buildExternalStorageAppObbDirs(getPackageName()), true);
        }
        return ensureExternalDirsExistOrFilter;
    }

    @Override // android.content.Context
    public java.io.File getCacheDir() {
        java.io.File ensurePrivateCacheDirExists;
        synchronized (this.mSync) {
            if (this.mCacheDir == null) {
                this.mCacheDir = new java.io.File(getDataDir(), "cache");
            }
            ensurePrivateCacheDirExists = ensurePrivateCacheDirExists(this.mCacheDir, XATTR_INODE_CACHE);
        }
        return ensurePrivateCacheDirExists;
    }

    @Override // android.content.Context
    public java.io.File getCodeCacheDir() {
        java.io.File ensurePrivateCacheDirExists;
        synchronized (this.mSync) {
            if (this.mCodeCacheDir == null) {
                this.mCodeCacheDir = getCodeCacheDirBeforeBind(getDataDir());
            }
            ensurePrivateCacheDirExists = ensurePrivateCacheDirExists(this.mCodeCacheDir, XATTR_INODE_CODE_CACHE);
        }
        return ensurePrivateCacheDirExists;
    }

    static java.io.File getCodeCacheDirBeforeBind(java.io.File file) {
        return new java.io.File(file, "code_cache");
    }

    @Override // android.content.Context
    public java.io.File getExternalCacheDir() {
        java.io.File[] externalCacheDirs = getExternalCacheDirs();
        if (externalCacheDirs == null || externalCacheDirs.length <= 0) {
            return null;
        }
        return externalCacheDirs[0];
    }

    @Override // android.content.Context
    public java.io.File[] getExternalCacheDirs() {
        java.io.File[] ensureExternalDirsExistOrFilter;
        synchronized (this.mSync) {
            ensureExternalDirsExistOrFilter = ensureExternalDirsExistOrFilter(android.os.Environment.buildExternalStorageAppCacheDirs(getPackageName()), false);
        }
        return ensureExternalDirsExistOrFilter;
    }

    @Override // android.content.Context
    public java.io.File[] getExternalMediaDirs() {
        java.io.File[] ensureExternalDirsExistOrFilter;
        synchronized (this.mSync) {
            ensureExternalDirsExistOrFilter = ensureExternalDirsExistOrFilter(android.os.Environment.buildExternalStorageAppMediaDirs(getPackageName()), true);
        }
        return ensureExternalDirsExistOrFilter;
    }

    @Override // android.content.Context
    public java.io.File getPreloadsFileCache() {
        return android.os.Environment.getDataPreloadsFileCacheDirectory(getPackageName());
    }

    @Override // android.content.Context
    public java.io.File getFileStreamPath(java.lang.String str) {
        return makeFilename(getFilesDir(), str);
    }

    @Override // android.content.Context
    public java.io.File getSharedPreferencesPath(java.lang.String str) {
        return makeFilename(getPreferencesDir(), str + ".xml");
    }

    @Override // android.content.Context
    public java.lang.String[] fileList() {
        return android.os.FileUtils.listOrEmpty(getFilesDir());
    }

    @Override // android.content.Context
    public android.database.sqlite.SQLiteDatabase openOrCreateDatabase(java.lang.String str, int i, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory) {
        return openOrCreateDatabase(str, i, cursorFactory, null);
    }

    @Override // android.content.Context
    public android.database.sqlite.SQLiteDatabase openOrCreateDatabase(java.lang.String str, int i, android.database.sqlite.SQLiteDatabase.CursorFactory cursorFactory, android.database.DatabaseErrorHandler databaseErrorHandler) {
        int i2;
        checkMode(i);
        java.io.File databasePath = getDatabasePath(str);
        if ((i & 8) == 0) {
            i2 = 268435456;
        } else {
            i2 = 805306368;
        }
        if ((i & 16) != 0) {
            i2 |= 16;
        }
        android.database.sqlite.SQLiteDatabase openDatabase = android.database.sqlite.SQLiteDatabase.openDatabase(databasePath.getPath(), cursorFactory, i2, databaseErrorHandler);
        setFilePermissionsFromMode(databasePath.getPath(), i, 0);
        return openDatabase;
    }

    @Override // android.content.Context
    public boolean moveDatabaseFrom(android.content.Context context, java.lang.String str) {
        boolean z;
        synchronized (android.app.ContextImpl.class) {
            java.io.File databasePath = context.getDatabasePath(str);
            z = moveFiles(databasePath.getParentFile(), getDatabasePath(str).getParentFile(), databasePath.getName()) != -1;
        }
        return z;
    }

    @Override // android.content.Context
    public boolean deleteDatabase(java.lang.String str) {
        try {
            return android.database.sqlite.SQLiteDatabase.deleteDatabase(getDatabasePath(str));
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    @Override // android.content.Context
    public java.io.File getDatabasePath(java.lang.String str) {
        if (str.charAt(0) == java.io.File.separatorChar) {
            java.io.File file = new java.io.File(str.substring(0, str.lastIndexOf(java.io.File.separatorChar)));
            java.io.File file2 = new java.io.File(file, str.substring(str.lastIndexOf(java.io.File.separatorChar)));
            if (!file.isDirectory() && file.mkdir()) {
                android.os.FileUtils.setPermissions(file.getPath(), 505, -1, -1);
                return file2;
            }
            return file2;
        }
        return makeFilename(getDatabasesDir(), str);
    }

    @Override // android.content.Context
    public java.lang.String[] databaseList() {
        return android.os.FileUtils.listOrEmpty(getDatabasesDir());
    }

    private java.io.File getDatabasesDir() {
        java.io.File ensurePrivateDirExists;
        synchronized (this.mSync) {
            if (this.mDatabasesDir == null) {
                if ("android".equals(getPackageName())) {
                    this.mDatabasesDir = new java.io.File("/data/system");
                } else {
                    this.mDatabasesDir = new java.io.File(getDataDir(), "databases");
                }
            }
            ensurePrivateDirExists = ensurePrivateDirExists(this.mDatabasesDir);
        }
        return ensurePrivateDirExists;
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public android.graphics.drawable.Drawable getWallpaper() {
        return getWallpaperManager().getDrawable();
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public android.graphics.drawable.Drawable peekWallpaper() {
        return getWallpaperManager().peekDrawable();
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public int getWallpaperDesiredMinimumWidth() {
        return getWallpaperManager().getDesiredMinimumWidth();
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public int getWallpaperDesiredMinimumHeight() {
        return getWallpaperManager().getDesiredMinimumHeight();
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void setWallpaper(android.graphics.Bitmap bitmap) throws java.io.IOException {
        getWallpaperManager().setBitmap(bitmap);
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void setWallpaper(java.io.InputStream inputStream) throws java.io.IOException {
        getWallpaperManager().setStream(inputStream);
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void clearWallpaper() throws java.io.IOException {
        getWallpaperManager().clear();
    }

    private android.app.WallpaperManager getWallpaperManager() {
        return (android.app.WallpaperManager) getSystemService(android.app.WallpaperManager.class);
    }

    @Override // android.content.Context
    public void startActivity(android.content.Intent intent) {
        warnIfCallingFromSystemProcess();
        startActivity(intent, null);
    }

    @Override // android.content.Context
    public void startActivityAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        startActivityAsUser(intent, null, userHandle);
    }

    @Override // android.content.Context
    public void startActivity(android.content.Intent intent, android.os.Bundle bundle) {
        warnIfCallingFromSystemProcess();
        int i = getApplicationInfo().targetSdkVersion;
        if ((intent.getFlags() & 268435456) == 0 && ((i < 24 || i >= 28) && (bundle == null || android.app.ActivityOptions.fromBundle(bundle).getLaunchTaskId() == -1))) {
            throw new android.util.AndroidRuntimeException("Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?");
        }
        this.mMainThread.getInstrumentation().execStartActivity(getOuterContext(), this.mMainThread.getApplicationThread(), (android.os.IBinder) null, (android.app.Activity) null, intent, -1, bundle);
    }

    @Override // android.content.Context
    public void startActivityAsUser(android.content.Intent intent, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        try {
            android.app.ActivityTaskManager.getService().startActivityAsUser(this.mMainThread.getApplicationThread(), getOpPackageName(), getAttributionTag(), intent, intent.resolveTypeIfNeeded(getContentResolver()), null, null, 0, 268435456, null, bundle, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void startActivities(android.content.Intent[] intentArr) {
        warnIfCallingFromSystemProcess();
        startActivities(intentArr, null);
    }

    @Override // android.content.Context
    public int startActivitiesAsUser(android.content.Intent[] intentArr, android.os.Bundle bundle, android.os.UserHandle userHandle) {
        if ((intentArr[0].getFlags() & 268435456) == 0) {
            throw new android.util.AndroidRuntimeException("Calling startActivities() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag on first Intent. Is this really what you want?");
        }
        return this.mMainThread.getInstrumentation().execStartActivitiesAsUser(getOuterContext(), this.mMainThread.getApplicationThread(), null, null, intentArr, bundle, userHandle.getIdentifier());
    }

    @Override // android.content.Context
    public void startActivities(android.content.Intent[] intentArr, android.os.Bundle bundle) {
        warnIfCallingFromSystemProcess();
        if ((intentArr[0].getFlags() & 268435456) == 0) {
            throw new android.util.AndroidRuntimeException("Calling startActivities() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag on first Intent. Is this really what you want?");
        }
        this.mMainThread.getInstrumentation().execStartActivities(getOuterContext(), this.mMainThread.getApplicationThread(), null, null, intentArr, bundle);
    }

    @Override // android.content.Context
    public void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent, int i, int i2, int i3) throws android.content.IntentSender.SendIntentException {
        startIntentSender(intentSender, intent, i, i2, i3, null);
    }

    @Override // android.content.Context
    public void startIntentSender(android.content.IntentSender intentSender, android.content.Intent intent, int i, int i2, int i3, android.os.Bundle bundle) throws android.content.IntentSender.SendIntentException {
        java.lang.String resolveTypeIfNeeded;
        if (intent == null) {
            resolveTypeIfNeeded = null;
        } else {
            try {
                intent.migrateExtraStreamToClipData(this);
                intent.prepareToLeaveProcess(this);
                resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        int startActivityIntentSender = android.app.ActivityTaskManager.getService().startActivityIntentSender(this.mMainThread.getApplicationThread(), intentSender != null ? intentSender.getTarget() : null, intentSender != null ? intentSender.getWhitelistToken() : null, intent, resolveTypeIfNeeded, null, null, 0, i, i2, bundle);
        if (startActivityIntentSender == -96) {
            throw new android.content.IntentSender.SendIntentException();
        }
        android.app.Instrumentation.checkStartActivityResult(startActivityIntentSender, null);
    }

    @Override // android.content.Context
    public void sendBroadcast(android.content.Intent intent) {
        warnIfCallingFromSystemProcess();
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, null, null, null, -1, null, false, false, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcast(android.content.Intent intent, java.lang.String str) {
        warnIfCallingFromSystemProcess();
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        java.lang.String[] strArr = str == null ? null : new java.lang.String[]{str};
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, strArr, null, null, -1, null, false, false, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(android.content.Intent intent, java.lang.String[] strArr) {
        warnIfCallingFromSystemProcess();
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, strArr, null, null, -1, null, false, false, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(android.content.Intent intent, java.lang.String[] strArr, android.os.Bundle bundle) {
        warnIfCallingFromSystemProcess();
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, strArr, null, null, -1, bundle, false, false, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastAsUserMultiplePermissions(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String[] strArr) {
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, strArr, null, null, -1, null, false, false, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastMultiplePermissions(android.content.Intent intent, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String[] strArr3, android.app.BroadcastOptions broadcastOptions) {
        warnIfCallingFromSystemProcess();
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, strArr, strArr2, strArr3, -1, broadcastOptions == null ? null : broadcastOptions.toBundle(), false, false, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcast(android.content.Intent intent, java.lang.String str, android.os.Bundle bundle) {
        java.lang.String[] strArr;
        java.lang.String[] strArr2;
        java.lang.String[] strArr3;
        warnIfCallingFromSystemProcess();
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        if (str == null) {
            strArr = null;
        } else {
            strArr = new java.lang.String[]{str};
        }
        if (bundle == null) {
            strArr2 = null;
            strArr3 = strArr;
        } else {
            java.lang.String[] stringArray = bundle.getStringArray(android.app.BroadcastOptions.KEY_REQUIRE_ALL_OF_PERMISSIONS);
            if (stringArray != null) {
                strArr = stringArray;
            }
            strArr2 = bundle.getStringArray(android.app.BroadcastOptions.KEY_REQUIRE_NONE_OF_PERMISSIONS);
            strArr3 = strArr;
        }
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, strArr3, strArr2, null, -1, bundle, false, false, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcast(android.content.Intent intent, java.lang.String str, int i) {
        warnIfCallingFromSystemProcess();
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        java.lang.String[] strArr = str == null ? null : new java.lang.String[]{str};
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, strArr, null, null, i, null, false, false, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str) {
        sendOrderedBroadcast(intent, str, null);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, android.os.Bundle bundle) {
        warnIfCallingFromSystemProcess();
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        java.lang.String[] strArr = str == null ? null : new java.lang.String[]{str};
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, strArr, null, null, -1, bundle, true, false, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str2, android.os.Bundle bundle) {
        sendOrderedBroadcast(intent, str, -1, broadcastReceiver, handler, i, str2, bundle, (android.os.Bundle) null);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, android.os.Bundle bundle, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str2, android.os.Bundle bundle2) {
        sendOrderedBroadcast(intent, str, -1, broadcastReceiver, handler, i, str2, bundle2, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, int i, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i2, java.lang.String str2, android.os.Bundle bundle) {
        sendOrderedBroadcast(intent, str, i, broadcastReceiver, handler, i2, str2, bundle, (android.os.Bundle) null);
    }

    void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, int i, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i2, java.lang.String str2, android.os.Bundle bundle, android.os.Bundle bundle2) {
        android.content.IIntentReceiver iIntentReceiver;
        java.lang.String[] strArr;
        android.os.Handler handler2;
        android.os.Handler handler3;
        warnIfCallingFromSystemProcess();
        if (broadcastReceiver == null) {
            iIntentReceiver = null;
        } else if (this.mPackageInfo != null) {
            if (handler != null) {
                handler3 = handler;
            } else {
                handler3 = this.mMainThread.getHandler();
            }
            iIntentReceiver = this.mPackageInfo.getReceiverDispatcher(broadcastReceiver, getOuterContext(), handler3, this.mMainThread.getInstrumentation(), false);
        } else {
            if (handler != null) {
                handler2 = handler;
            } else {
                handler2 = this.mMainThread.getHandler();
            }
            iIntentReceiver = new android.app.LoadedApk.ReceiverDispatcher(this.mMainThread.getApplicationThread(), broadcastReceiver, getOuterContext(), handler2, null, false).getIIntentReceiver();
        }
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        if (str == null) {
            strArr = null;
        } else {
            strArr = new java.lang.String[]{str};
        }
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, iIntentReceiver, i2, str2, bundle, strArr, null, null, i, bundle2, true, false, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, null, null, null, -1, null, false, false, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str) {
        sendBroadcastAsUser(intent, userHandle, str, -1);
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, android.os.Bundle bundle) {
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        java.lang.String[] strArr = str == null ? null : new java.lang.String[]{str};
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, strArr, null, null, -1, bundle, false, false, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, int i) {
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        java.lang.String[] strArr = str == null ? null : new java.lang.String[]{str};
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, strArr, null, null, i, null, false, false, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str2, android.os.Bundle bundle) {
        sendOrderedBroadcastAsUser(intent, userHandle, str, -1, null, broadcastReceiver, handler, i, str2, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, int i, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i2, java.lang.String str2, android.os.Bundle bundle) {
        sendOrderedBroadcastAsUser(intent, userHandle, str, i, null, broadcastReceiver, handler, i2, str2, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, java.lang.String str, int i, android.os.Bundle bundle, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i2, java.lang.String str2, android.os.Bundle bundle2) {
        android.content.IIntentReceiver iIntentReceiver;
        java.lang.String[] strArr;
        android.os.Handler handler2;
        android.os.Handler handler3;
        if (broadcastReceiver == null) {
            iIntentReceiver = null;
        } else if (this.mPackageInfo != null) {
            if (handler != null) {
                handler3 = handler;
            } else {
                handler3 = this.mMainThread.getHandler();
            }
            iIntentReceiver = this.mPackageInfo.getReceiverDispatcher(broadcastReceiver, getOuterContext(), handler3, this.mMainThread.getInstrumentation(), false);
        } else {
            if (handler != null) {
                handler2 = handler;
            } else {
                handler2 = this.mMainThread.getHandler();
            }
            iIntentReceiver = new android.app.LoadedApk.ReceiverDispatcher(this.mMainThread.getApplicationThread(), broadcastReceiver, getOuterContext(), handler2, null, false).getIIntentReceiver();
        }
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        if (str == null) {
            strArr = null;
        } else {
            strArr = new java.lang.String[]{str};
        }
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, iIntentReceiver, i2, str2, bundle2, strArr, null, null, i, bundle, true, false, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(android.content.Intent intent, java.lang.String str, java.lang.String str2, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str3, android.os.Bundle bundle) {
        int i2;
        if (android.text.TextUtils.isEmpty(str2)) {
            i2 = -1;
        } else {
            i2 = android.app.AppOpsManager.strOpToOp(str2);
        }
        sendOrderedBroadcastAsUser(intent, getUser(), str, i2, broadcastReceiver, handler, i, str3, bundle);
    }

    @Override // android.content.Context
    public void sendOrderedBroadcast(android.content.Intent intent, int i, java.lang.String str, java.lang.String str2, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, java.lang.String str3, android.os.Bundle bundle, android.os.Bundle bundle2) {
        int i2;
        if (android.text.TextUtils.isEmpty(str2)) {
            i2 = -1;
        } else {
            i2 = android.app.AppOpsManager.strOpToOp(str2);
        }
        sendOrderedBroadcastAsUser(intent, getUser(), str, i2, bundle2, broadcastReceiver, handler, i, str3, bundle);
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void sendStickyBroadcast(android.content.Intent intent) {
        warnIfCallingFromSystemProcess();
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, null, null, null, -1, null, false, true, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void sendStickyBroadcast(android.content.Intent intent, android.os.Bundle bundle) {
        warnIfCallingFromSystemProcess();
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, null, null, null, -1, bundle, false, true, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void sendStickyOrderedBroadcast(android.content.Intent intent, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str, android.os.Bundle bundle) {
        android.content.IIntentReceiver iIntentReceiver;
        android.os.Handler handler2;
        android.os.Handler handler3;
        warnIfCallingFromSystemProcess();
        if (broadcastReceiver == null) {
            iIntentReceiver = null;
        } else if (this.mPackageInfo != null) {
            if (handler != null) {
                handler3 = handler;
            } else {
                handler3 = this.mMainThread.getHandler();
            }
            iIntentReceiver = this.mPackageInfo.getReceiverDispatcher(broadcastReceiver, getOuterContext(), handler3, this.mMainThread.getInstrumentation(), false);
        } else {
            if (handler != null) {
                handler2 = handler;
            } else {
                handler2 = this.mMainThread.getHandler();
            }
            iIntentReceiver = new android.app.LoadedApk.ReceiverDispatcher(this.mMainThread.getApplicationThread(), broadcastReceiver, getOuterContext(), handler2, null, false).getIIntentReceiver();
        }
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, iIntentReceiver, i, str, bundle, null, null, null, -1, null, true, true, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void removeStickyBroadcast(android.content.Intent intent) {
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        if (resolveTypeIfNeeded != null) {
            android.content.Intent intent2 = new android.content.Intent(intent);
            intent2.setDataAndType(intent2.getData(), resolveTypeIfNeeded);
            intent = intent2;
        }
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().unbroadcastIntent(this.mMainThread.getApplicationThread(), intent, getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void sendStickyBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, null, null, null, -1, null, false, true, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void sendStickyBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, android.os.Bundle bundle) {
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, null, -1, null, null, null, null, null, -1, bundle, false, true, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void sendStickyOrderedBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, java.lang.String str, android.os.Bundle bundle) {
        android.content.IIntentReceiver iIntentReceiver;
        android.os.Handler handler2;
        android.os.Handler handler3;
        if (broadcastReceiver == null) {
            iIntentReceiver = null;
        } else if (this.mPackageInfo != null) {
            if (handler != null) {
                handler3 = handler;
            } else {
                handler3 = this.mMainThread.getHandler();
            }
            iIntentReceiver = this.mPackageInfo.getReceiverDispatcher(broadcastReceiver, getOuterContext(), handler3, this.mMainThread.getInstrumentation(), false);
        } else {
            if (handler != null) {
                handler2 = handler;
            } else {
                handler2 = this.mMainThread.getHandler();
            }
            iIntentReceiver = new android.app.LoadedApk.ReceiverDispatcher(this.mMainThread.getApplicationThread(), broadcastReceiver, getOuterContext(), handler2, null, false).getIIntentReceiver();
        }
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().broadcastIntentWithFeature(this.mMainThread.getApplicationThread(), getAttributionTag(), intent, resolveTypeIfNeeded, iIntentReceiver, i, str, bundle, null, null, null, -1, null, true, true, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    @java.lang.Deprecated
    public void removeStickyBroadcastAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        java.lang.String resolveTypeIfNeeded = intent.resolveTypeIfNeeded(getContentResolver());
        if (resolveTypeIfNeeded != null) {
            android.content.Intent intent2 = new android.content.Intent(intent);
            intent2.setDataAndType(intent2.getData(), resolveTypeIfNeeded);
            intent = intent2;
        }
        try {
            intent.prepareToLeaveProcess(this);
            android.app.ActivityManager.getService().unbroadcastIntent(this.mMainThread.getApplicationThread(), intent, userHandle.getIdentifier());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter) {
        return registerReceiver(broadcastReceiver, intentFilter, null, null);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, int i) {
        return registerReceiver(broadcastReceiver, intentFilter, null, null, i);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler) {
        return registerReceiverInternal(broadcastReceiver, getUserId(), intentFilter, str, handler, getOuterContext(), 0);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiver(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler, int i) {
        return registerReceiverInternal(broadcastReceiver, getUserId(), intentFilter, str, handler, getOuterContext(), i);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiverForAllUsers(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler) {
        return registerReceiverAsUser(broadcastReceiver, android.os.UserHandle.ALL, intentFilter, str, handler);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiverForAllUsers(android.content.BroadcastReceiver broadcastReceiver, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler, int i) {
        return registerReceiverAsUser(broadcastReceiver, android.os.UserHandle.ALL, intentFilter, str, handler, i);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiverAsUser(android.content.BroadcastReceiver broadcastReceiver, android.os.UserHandle userHandle, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler) {
        return registerReceiverInternal(broadcastReceiver, userHandle.getIdentifier(), intentFilter, str, handler, getOuterContext(), 0);
    }

    @Override // android.content.Context
    public android.content.Intent registerReceiverAsUser(android.content.BroadcastReceiver broadcastReceiver, android.os.UserHandle userHandle, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler, int i) {
        return registerReceiverInternal(broadcastReceiver, userHandle.getIdentifier(), intentFilter, str, handler, getOuterContext(), i);
    }

    private android.content.Intent registerReceiverInternal(android.content.BroadcastReceiver broadcastReceiver, int i, android.content.IntentFilter intentFilter, java.lang.String str, android.os.Handler handler, android.content.Context context, int i2) {
        android.content.IIntentReceiver iIntentReceiver;
        android.os.Handler handler2;
        android.os.Handler handler3;
        if (broadcastReceiver == null) {
            iIntentReceiver = null;
        } else if (this.mPackageInfo != null && context != null) {
            if (handler != null) {
                handler3 = handler;
            } else {
                handler3 = this.mMainThread.getHandler();
            }
            iIntentReceiver = this.mPackageInfo.getReceiverDispatcher(broadcastReceiver, context, handler3, this.mMainThread.getInstrumentation(), true);
        } else {
            if (handler != null) {
                handler2 = handler;
            } else {
                handler2 = this.mMainThread.getHandler();
            }
            iIntentReceiver = new android.app.LoadedApk.ReceiverDispatcher(this.mMainThread.getApplicationThread(), broadcastReceiver, context, handler2, null, true).getIIntentReceiver();
        }
        try {
            android.content.Intent registerReceiverWithFeature = android.app.ActivityManager.getService().registerReceiverWithFeature(this.mMainThread.getApplicationThread(), this.mBasePackageName, getAttributionTag(), android.app.AppOpsManager.toReceiverId(broadcastReceiver), iIntentReceiver, intentFilter, str, i, i2);
            if (registerReceiverWithFeature != null) {
                registerReceiverWithFeature.setExtrasClassLoader(getClassLoader());
                registerReceiverWithFeature.prepareToEnterProcess(android.app.ActivityThread.isProtectedBroadcast(registerReceiverWithFeature), getAttributionSource());
            }
            return registerReceiverWithFeature;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void unregisterReceiver(android.content.BroadcastReceiver broadcastReceiver) {
        if (this.mPackageInfo != null) {
            try {
                android.app.ActivityManager.getService().unregisterReceiver(this.mPackageInfo.forgetReceiverDispatcher(getOuterContext(), broadcastReceiver));
                return;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new java.lang.RuntimeException("Not supported in system context");
    }

    private void validateServiceIntent(android.content.Intent intent) {
        if (intent.getComponent() == null && intent.getPackage() == null) {
            if (getApplicationInfo().targetSdkVersion >= 21) {
                throw new java.lang.IllegalArgumentException("Service Intent must be explicit: " + intent);
            }
            android.util.Log.w(TAG, "Implicit intents with startService are not safe: " + intent + " " + android.os.Debug.getCallers(2, 3));
        }
    }

    @Override // android.content.Context
    public android.content.ComponentName startService(android.content.Intent intent) {
        warnIfCallingFromSystemProcess();
        return startServiceCommon(intent, false, this.mUser);
    }

    @Override // android.content.Context
    public android.content.ComponentName startForegroundService(android.content.Intent intent) {
        warnIfCallingFromSystemProcess();
        return startServiceCommon(intent, true, this.mUser);
    }

    @Override // android.content.Context
    public boolean stopService(android.content.Intent intent) {
        warnIfCallingFromSystemProcess();
        return stopServiceCommon(intent, this.mUser);
    }

    @Override // android.content.Context
    public android.content.ComponentName startServiceAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        return startServiceCommon(intent, false, userHandle);
    }

    @Override // android.content.Context
    public android.content.ComponentName startForegroundServiceAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        return startServiceCommon(intent, true, userHandle);
    }

    private android.content.ComponentName startServiceCommon(android.content.Intent intent, boolean z, android.os.UserHandle userHandle) {
        try {
            validateServiceIntent(intent);
            intent.prepareToLeaveProcess(this);
            android.content.ComponentName startService = android.app.ActivityManager.getService().startService(this.mMainThread.getApplicationThread(), intent, intent.resolveTypeIfNeeded(getContentResolver()), z, getOpPackageName(), getAttributionTag(), userHandle.getIdentifier());
            if (startService != null) {
                if (startService.getPackageName().equals("!")) {
                    throw new java.lang.SecurityException("Not allowed to start service " + intent + " without permission " + startService.getClassName());
                }
                if (startService.getPackageName().equals("!!")) {
                    throw new java.lang.SecurityException("Unable to start service " + intent + ": " + startService.getClassName());
                }
                if (startService.getPackageName().equals("?")) {
                    throw android.app.ServiceStartNotAllowedException.newInstance(z, "Not allowed to start service " + intent + ": " + startService.getClassName());
                }
            }
            if (startService != null && z && startService.getPackageName().equals(getOpPackageName())) {
                android.app.Service.setStartForegroundServiceStackTrace(startService.getClassName(), new android.app.StackTrace("Last startServiceCommon() call for this service was made here"));
            }
            return startService;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public boolean stopServiceAsUser(android.content.Intent intent, android.os.UserHandle userHandle) {
        return stopServiceCommon(intent, userHandle);
    }

    private boolean stopServiceCommon(android.content.Intent intent, android.os.UserHandle userHandle) {
        try {
            validateServiceIntent(intent);
            intent.prepareToLeaveProcess(this);
            int stopService = android.app.ActivityManager.getService().stopService(this.mMainThread.getApplicationThread(), intent, intent.resolveTypeIfNeeded(getContentResolver()), userHandle.getIdentifier());
            if (stopService >= 0) {
                return stopService != 0;
            }
            throw new java.lang.SecurityException("Not allowed to stop service " + intent);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public boolean bindService(android.content.Intent intent, android.content.ServiceConnection serviceConnection, int i) {
        warnIfCallingFromSystemProcess();
        return bindServiceCommon(intent, serviceConnection, java.lang.Integer.toUnsignedLong(i), null, this.mMainThread.getHandler(), null, getUser());
    }

    @Override // android.content.Context
    public boolean bindService(android.content.Intent intent, android.content.ServiceConnection serviceConnection, android.content.Context.BindServiceFlags bindServiceFlags) {
        warnIfCallingFromSystemProcess();
        return bindServiceCommon(intent, serviceConnection, bindServiceFlags.getValue(), null, this.mMainThread.getHandler(), null, getUser());
    }

    @Override // android.content.Context
    public boolean bindService(android.content.Intent intent, int i, java.util.concurrent.Executor executor, android.content.ServiceConnection serviceConnection) {
        return bindServiceCommon(intent, serviceConnection, java.lang.Integer.toUnsignedLong(i), null, null, executor, getUser());
    }

    @Override // android.content.Context
    public boolean bindService(android.content.Intent intent, android.content.Context.BindServiceFlags bindServiceFlags, java.util.concurrent.Executor executor, android.content.ServiceConnection serviceConnection) {
        return bindServiceCommon(intent, serviceConnection, bindServiceFlags.getValue(), null, null, executor, getUser());
    }

    @Override // android.content.Context
    public boolean bindIsolatedService(android.content.Intent intent, int i, java.lang.String str, java.util.concurrent.Executor executor, android.content.ServiceConnection serviceConnection) {
        warnIfCallingFromSystemProcess();
        if (str == null) {
            throw new java.lang.NullPointerException("null instanceName");
        }
        return bindServiceCommon(intent, serviceConnection, java.lang.Integer.toUnsignedLong(i), str, null, executor, getUser());
    }

    @Override // android.content.Context
    public boolean bindIsolatedService(android.content.Intent intent, android.content.Context.BindServiceFlags bindServiceFlags, java.lang.String str, java.util.concurrent.Executor executor, android.content.ServiceConnection serviceConnection) {
        warnIfCallingFromSystemProcess();
        if (str == null) {
            throw new java.lang.NullPointerException("null instanceName");
        }
        return bindServiceCommon(intent, serviceConnection, bindServiceFlags.getValue(), str, null, executor, getUser());
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(android.content.Intent intent, android.content.ServiceConnection serviceConnection, int i, android.os.UserHandle userHandle) {
        return bindServiceCommon(intent, serviceConnection, java.lang.Integer.toUnsignedLong(i), null, this.mMainThread.getHandler(), null, userHandle);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(android.content.Intent intent, android.content.ServiceConnection serviceConnection, android.content.Context.BindServiceFlags bindServiceFlags, android.os.UserHandle userHandle) {
        return bindServiceCommon(intent, serviceConnection, bindServiceFlags.getValue(), null, this.mMainThread.getHandler(), null, userHandle);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(android.content.Intent intent, android.content.ServiceConnection serviceConnection, int i, android.os.Handler handler, android.os.UserHandle userHandle) {
        if (handler == null) {
            throw new java.lang.IllegalArgumentException("handler must not be null.");
        }
        return bindServiceCommon(intent, serviceConnection, java.lang.Integer.toUnsignedLong(i), null, handler, null, userHandle);
    }

    @Override // android.content.Context
    public boolean bindServiceAsUser(android.content.Intent intent, android.content.ServiceConnection serviceConnection, android.content.Context.BindServiceFlags bindServiceFlags, android.os.Handler handler, android.os.UserHandle userHandle) {
        if (handler == null) {
            throw new java.lang.IllegalArgumentException("handler must not be null.");
        }
        return bindServiceCommon(intent, serviceConnection, bindServiceFlags.getValue(), null, handler, null, userHandle);
    }

    @Override // android.content.Context
    public android.app.IServiceConnection getServiceDispatcher(android.content.ServiceConnection serviceConnection, android.os.Handler handler, long j) {
        return this.mPackageInfo.getServiceDispatcher(serviceConnection, getOuterContext(), handler, j);
    }

    @Override // android.content.Context
    public android.app.IApplicationThread getIApplicationThread() {
        return this.mMainThread.getApplicationThread();
    }

    @Override // android.content.Context
    public android.os.IBinder getProcessToken() {
        return getIApplicationThread().asBinder();
    }

    @Override // android.content.Context
    public android.os.Handler getMainThreadHandler() {
        return this.mMainThread.getHandler();
    }

    private boolean bindServiceCommon(android.content.Intent intent, android.content.ServiceConnection serviceConnection, long j, java.lang.String str, android.os.Handler handler, java.util.concurrent.Executor executor, android.os.UserHandle userHandle) {
        android.app.IServiceConnection serviceDispatcher;
        long j2;
        if (serviceConnection == null) {
            throw new java.lang.IllegalArgumentException("connection is null");
        }
        if (handler != null && executor != null) {
            throw new java.lang.IllegalArgumentException("Handler and Executor both supplied");
        }
        if (this.mPackageInfo != null) {
            if (executor != null) {
                serviceDispatcher = this.mPackageInfo.getServiceDispatcher(serviceConnection, getOuterContext(), executor, j);
            } else {
                serviceDispatcher = this.mPackageInfo.getServiceDispatcher(serviceConnection, getOuterContext(), handler, j);
            }
            validateServiceIntent(intent);
            try {
                if (getActivityToken() == null && (1 & j) == 0 && this.mPackageInfo != null && this.mPackageInfo.getApplicationInfo().targetSdkVersion < 14) {
                    j2 = 32 | j;
                } else {
                    j2 = j;
                }
                intent.prepareToLeaveProcess(this);
                int bindServiceInstance = android.app.ActivityManager.getService().bindServiceInstance(this.mMainThread.getApplicationThread(), getActivityToken(), intent, intent.resolveTypeIfNeeded(getContentResolver()), serviceDispatcher, j2, str, getOpPackageName(), userHandle.getIdentifier());
                if (bindServiceInstance >= 0) {
                    return bindServiceInstance != 0;
                }
                throw new java.lang.SecurityException("Not allowed to bind to service " + intent);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new java.lang.RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public void updateServiceGroup(android.content.ServiceConnection serviceConnection, int i, int i2) {
        if (serviceConnection == null) {
            throw new java.lang.IllegalArgumentException("connection is null");
        }
        if (this.mPackageInfo != null) {
            android.app.IServiceConnection lookupServiceDispatcher = this.mPackageInfo.lookupServiceDispatcher(serviceConnection, getOuterContext());
            if (lookupServiceDispatcher == null) {
                throw new java.lang.IllegalArgumentException("ServiceConnection not currently bound: " + serviceConnection);
            }
            try {
                android.app.ActivityManager.getService().updateServiceGroup(lookupServiceDispatcher, i, i2);
                return;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new java.lang.RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public void unbindService(android.content.ServiceConnection serviceConnection) {
        if (serviceConnection == null) {
            throw new java.lang.IllegalArgumentException("connection is null");
        }
        if (this.mPackageInfo != null) {
            try {
                android.app.ActivityManager.getService().unbindService(this.mPackageInfo.forgetServiceDispatcher(getOuterContext(), serviceConnection));
                return;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new java.lang.RuntimeException("Not supported in system context");
    }

    @Override // android.content.Context
    public boolean startInstrumentation(android.content.ComponentName componentName, java.lang.String str, android.os.Bundle bundle) {
        if (bundle != null) {
            try {
                bundle.setAllowFds(false);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return android.app.ActivityManager.getService().startInstrumentation(componentName, str, 0, bundle, null, null, getUserId(), null);
    }

    @Override // android.content.Context
    public java.lang.Object getSystemService(java.lang.String str) {
        if (android.os.StrictMode.vmIncorrectContextUseEnabled() && android.content.Context.WINDOW_SERVICE.equals(str) && !isUiContext()) {
            java.lang.String str2 = "Tried to access visual service " + android.app.SystemServiceRegistry.getSystemServiceClassName(str) + " from a non-visual Context:" + getOuterContext();
            java.lang.IllegalAccessException illegalAccessException = new java.lang.IllegalAccessException(str2);
            android.os.StrictMode.onIncorrectContextUsed("WindowManager should be accessed from Activity or other visual Context. Use an Activity or a Context created with Context#createWindowContext(int, Bundle), which are adjusted to the configuration and visual bounds of an area on screen.", illegalAccessException);
            android.util.Log.e(TAG, str2 + " WindowManager should be accessed from Activity or other visual Context. Use an Activity or a Context created with Context#createWindowContext(int, Bundle), which are adjusted to the configuration and visual bounds of an area on screen.", illegalAccessException);
        }
        return android.app.SystemServiceRegistry.getSystemService(this, str);
    }

    @Override // android.content.Context
    public java.lang.String getSystemServiceName(java.lang.Class<?> cls) {
        return android.app.SystemServiceRegistry.getSystemServiceName(cls);
    }

    @Override // android.content.Context
    public boolean isUiContext() {
        switch (this.mContextType) {
        }
        return false;
    }

    @Override // android.content.Context
    public boolean isConfigurationContext() {
        return isUiContext() || this.mIsConfigurationBasedContext;
    }

    private static boolean isSystemOrSystemUI(android.content.Context context) {
        return android.app.ActivityThread.isSystem() || context.checkPermission(android.Manifest.permission.STATUS_BAR_SERVICE, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid()) == 0;
    }

    @Override // android.content.Context
    public int checkPermission(java.lang.String str, int i, int i2) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("permission is null");
        }
        if (this.mParams.isRenouncedPermission(str) && i == android.os.Process.myPid() && i2 == android.os.Process.myUid()) {
            android.util.Log.v(TAG, "Treating renounced permission " + str + " as denied");
            return -1;
        }
        return android.permission.PermissionManager.checkPermission(str, i, i2, getDeviceId());
    }

    @Override // android.content.Context
    public int checkPermission(java.lang.String str, int i, int i2, android.os.IBinder iBinder) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("permission is null");
        }
        if (this.mParams.isRenouncedPermission(str) && i == android.os.Process.myPid() && i2 == android.os.Process.myUid()) {
            android.util.Log.v(TAG, "Treating renounced permission " + str + " as denied");
            return -1;
        }
        return checkPermission(str, i, i2);
    }

    @Override // android.content.Context
    public void revokeSelfPermissionsOnKill(java.util.Collection<java.lang.String> collection) {
        ((android.permission.PermissionControllerManager) getSystemService(android.permission.PermissionControllerManager.class)).revokeSelfPermissionsOnKill(getPackageName(), new java.util.ArrayList(collection));
    }

    @Override // android.content.Context
    public int checkCallingPermission(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("permission is null");
        }
        int callingPid = android.os.Binder.getCallingPid();
        if (callingPid != android.os.Process.myPid()) {
            return checkPermission(str, callingPid, android.os.Binder.getCallingUid());
        }
        return -1;
    }

    @Override // android.content.Context
    public int checkCallingOrSelfPermission(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("permission is null");
        }
        return checkPermission(str, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid());
    }

    @Override // android.content.Context
    public int checkSelfPermission(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("permission is null");
        }
        if (this.mParams.isRenouncedPermission(str)) {
            android.util.Log.v(TAG, "Treating renounced permission " + str + " as denied");
            return -1;
        }
        return checkPermission(str, android.os.Process.myPid(), android.os.Process.myUid());
    }

    private void enforce(java.lang.String str, int i, boolean z, int i2, java.lang.String str2) {
        java.lang.String str3;
        if (i != 0) {
            java.lang.StringBuilder append = new java.lang.StringBuilder().append(str2 != null ? str2 + ": " : "");
            if (z) {
                str3 = "Neither user " + i2 + " nor current process has ";
            } else {
                str3 = "uid " + i2 + " does not have ";
            }
            throw new java.lang.SecurityException(append.append(str3).append(str).append(android.media.MediaMetrics.SEPARATOR).toString());
        }
    }

    @Override // android.content.Context
    public void enforcePermission(java.lang.String str, int i, int i2, java.lang.String str2) {
        enforce(str, checkPermission(str, i, i2), false, i2, str2);
    }

    @Override // android.content.Context
    public void enforceCallingPermission(java.lang.String str, java.lang.String str2) {
        enforce(str, checkCallingPermission(str), false, android.os.Binder.getCallingUid(), str2);
    }

    @Override // android.content.Context
    public void enforceCallingOrSelfPermission(java.lang.String str, java.lang.String str2) {
        enforce(str, checkCallingOrSelfPermission(str), true, android.os.Binder.getCallingUid(), str2);
    }

    @Override // android.content.Context
    public void grantUriPermission(java.lang.String str, android.net.Uri uri, int i) {
        try {
            android.app.ActivityManager.getService().grantUriPermission(this.mMainThread.getApplicationThread(), str, android.content.ContentProvider.getUriWithoutUserId(uri), i, resolveUserId(uri));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void revokeUriPermission(android.net.Uri uri, int i) {
        try {
            android.app.ActivityManager.getService().revokeUriPermission(this.mMainThread.getApplicationThread(), null, android.content.ContentProvider.getUriWithoutUserId(uri), i, resolveUserId(uri));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public void revokeUriPermission(java.lang.String str, android.net.Uri uri, int i) {
        try {
            android.app.ActivityManager.getService().revokeUriPermission(this.mMainThread.getApplicationThread(), str, android.content.ContentProvider.getUriWithoutUserId(uri), i, resolveUserId(uri));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public int checkUriPermission(android.net.Uri uri, int i, int i2, int i3) {
        try {
            return android.app.ActivityManager.getService().checkUriPermission(android.content.ContentProvider.getUriWithoutUserId(uri), i, i2, i3, resolveUserId(uri), null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public int checkContentUriPermissionFull(android.net.Uri uri, int i, int i2, int i3) {
        try {
            return android.app.ActivityManager.getService().checkContentUriPermissionFull(android.content.ContentProvider.getUriWithoutUserId(uri), i, i2, i3, resolveUserId(uri));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public int[] checkUriPermissions(java.util.List<android.net.Uri> list, int i, int i2, int i3) {
        try {
            return android.app.ActivityManager.getService().checkUriPermissions(list, i, i2, i3, getUserId(), null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @Override // android.content.Context
    public int checkUriPermission(android.net.Uri uri, int i, int i2, int i3, android.os.IBinder iBinder) {
        try {
            return android.app.ActivityManager.getService().checkUriPermission(android.content.ContentProvider.getUriWithoutUserId(uri), i, i2, i3, resolveUserId(uri), iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int resolveUserId(android.net.Uri uri) {
        return android.content.ContentProvider.getUserIdFromUri(uri, getUserId());
    }

    @Override // android.content.Context
    public int checkCallingUriPermission(android.net.Uri uri, int i) {
        int callingPid = android.os.Binder.getCallingPid();
        if (callingPid != android.os.Process.myPid()) {
            return checkUriPermission(uri, callingPid, android.os.Binder.getCallingUid(), i);
        }
        return -1;
    }

    @Override // android.content.Context
    public int[] checkCallingUriPermissions(java.util.List<android.net.Uri> list, int i) {
        int callingPid = android.os.Binder.getCallingPid();
        if (callingPid != android.os.Process.myPid()) {
            return checkUriPermissions(list, callingPid, android.os.Binder.getCallingUid(), i);
        }
        int[] iArr = new int[list.size()];
        java.util.Arrays.fill(iArr, -1);
        return iArr;
    }

    @Override // android.content.Context
    public int checkCallingOrSelfUriPermission(android.net.Uri uri, int i) {
        return checkUriPermission(uri, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i);
    }

    @Override // android.content.Context
    public int[] checkCallingOrSelfUriPermissions(java.util.List<android.net.Uri> list, int i) {
        return checkUriPermissions(list, android.os.Binder.getCallingPid(), android.os.Binder.getCallingUid(), i);
    }

    @Override // android.content.Context
    public int checkUriPermission(android.net.Uri uri, java.lang.String str, java.lang.String str2, int i, int i2, int i3) {
        if ((i3 & 1) != 0 && (str == null || checkPermission(str, i, i2) == 0)) {
            return 0;
        }
        if ((i3 & 2) != 0 && (str2 == null || checkPermission(str2, i, i2) == 0)) {
            return 0;
        }
        if (uri != null) {
            return checkUriPermission(uri, i, i2, i3);
        }
        return -1;
    }

    private java.lang.String uriModeFlagToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if ((i & 1) != 0) {
            sb.append("read and ");
        }
        if ((i & 2) != 0) {
            sb.append("write and ");
        }
        if ((i & 64) != 0) {
            sb.append("persistable and ");
        }
        if ((i & 128) != 0) {
            sb.append("prefix and ");
        }
        if (sb.length() > 5) {
            sb.setLength(sb.length() - 5);
            return sb.toString();
        }
        throw new java.lang.IllegalArgumentException("Unknown permission mode flags: " + i);
    }

    private void enforceForUri(int i, int i2, boolean z, int i3, android.net.Uri uri, java.lang.String str) {
        java.lang.String str2;
        if (i2 != 0) {
            java.lang.StringBuilder append = new java.lang.StringBuilder().append(str != null ? str + ": " : "");
            if (z) {
                str2 = "Neither user " + i3 + " nor current process has ";
            } else {
                str2 = "User " + i3 + " does not have ";
            }
            throw new java.lang.SecurityException(append.append(str2).append(uriModeFlagToString(i)).append(" permission on ").append(uri).append(android.media.MediaMetrics.SEPARATOR).toString());
        }
    }

    @Override // android.content.Context
    public void enforceUriPermission(android.net.Uri uri, int i, int i2, int i3, java.lang.String str) {
        enforceForUri(i3, checkUriPermission(uri, i, i2, i3), false, i2, uri, str);
    }

    @Override // android.content.Context
    public void enforceCallingUriPermission(android.net.Uri uri, int i, java.lang.String str) {
        enforceForUri(i, checkCallingUriPermission(uri, i), false, android.os.Binder.getCallingUid(), uri, str);
    }

    @Override // android.content.Context
    public void enforceCallingOrSelfUriPermission(android.net.Uri uri, int i, java.lang.String str) {
        enforceForUri(i, checkCallingOrSelfUriPermission(uri, i), true, android.os.Binder.getCallingUid(), uri, str);
    }

    @Override // android.content.Context
    public void enforceUriPermission(android.net.Uri uri, java.lang.String str, java.lang.String str2, int i, int i2, int i3, java.lang.String str3) {
        enforceForUri(i3, checkUriPermission(uri, str, str2, i, i2, i3), false, i2, uri, str3);
    }

    private void warnIfCallingFromSystemProcess() {
        if (android.os.Process.myUid() == 1000) {
            android.util.Slog.w(TAG, "Calling a method in the system process without a qualified user: " + android.os.Debug.getCallers(5));
        }
    }

    private static android.content.res.Resources createResources(android.os.IBinder iBinder, android.app.LoadedApk loadedApk, java.lang.String str, java.lang.Integer num, android.content.res.Configuration configuration, android.content.res.CompatibilityInfo compatibilityInfo, java.util.List<android.content.res.loader.ResourcesLoader> list) {
        try {
            return android.app.ResourcesManager.getInstance().getResources(iBinder, loadedApk.getResDir(), loadedApk.getSplitPaths(str), loadedApk.getOverlayDirs(), loadedApk.getOverlayPaths(), loadedApk.getApplicationInfo().sharedLibraryFiles, num, configuration, compatibilityInfo, loadedApk.getSplitClassLoader(str), list);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @Override // android.content.Context
    public android.content.Context createApplicationContext(android.content.pm.ApplicationInfo applicationInfo, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return createApplicationContextAsUser(applicationInfo, i, new android.os.UserHandle(android.os.UserHandle.getUserId(applicationInfo.uid)));
    }

    private android.content.Context createApplicationContextAsUser(android.content.pm.ApplicationInfo applicationInfo, int i, android.os.UserHandle userHandle) throws android.content.pm.PackageManager.NameNotFoundException {
        android.app.LoadedApk packageInfo = this.mMainThread.getPackageInfo(applicationInfo, this.mResources.getCompatibilityInfo(), i | 1073741824);
        if (packageInfo != null) {
            android.app.ContextImpl contextImpl = new android.app.ContextImpl(this, this.mMainThread, packageInfo, android.content.ContextParams.EMPTY, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), null, this.mToken, userHandle, i, null, null, this.mDeviceId, this.mIsExplicitDeviceId);
            int displayId = getDisplayId();
            contextImpl.setResources(createResources(this.mToken, packageInfo, null, this.mForceDisplayOverrideInResources ? java.lang.Integer.valueOf(displayId) : null, null, getDisplayAdjustments(displayId).getCompatibilityInfo(), null));
            if (contextImpl.mResources != null) {
                return contextImpl;
            }
        }
        throw new android.content.pm.PackageManager.NameNotFoundException("Application package " + applicationInfo.packageName + " not found");
    }

    @Override // android.content.Context
    public android.content.Context createContextForSdkInSandbox(android.content.pm.ApplicationInfo applicationInfo, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        if (!android.os.Process.isSdkSandbox()) {
            throw new java.lang.SecurityException("API can only be called from SdkSandbox process");
        }
        android.app.ContextImpl contextImpl = (android.app.ContextImpl) createApplicationContextAsUser(applicationInfo, i, applicationInfo.uid >= 0 ? new android.os.UserHandle(android.os.UserHandle.getUserId(applicationInfo.uid)) : android.os.Process.myUserHandle());
        contextImpl.mPackageInfo.makeApplicationInner(false, null);
        return contextImpl;
    }

    @Override // android.content.Context
    public android.content.Context createPackageContext(java.lang.String str, int i) throws android.content.pm.PackageManager.NameNotFoundException {
        return createPackageContextAsUser(str, i, this.mUser);
    }

    @Override // android.content.Context
    public android.content.Context createPackageContextAsUser(java.lang.String str, int i, android.os.UserHandle userHandle) throws android.content.pm.PackageManager.NameNotFoundException {
        if (str.equals("system") || str.equals("android")) {
            return new android.app.ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), null, this.mToken, userHandle, i, null, null, this.mDeviceId, this.mIsExplicitDeviceId);
        }
        android.app.LoadedApk packageInfo = this.mMainThread.getPackageInfo(str, this.mResources.getCompatibilityInfo(), i | 1073741824, userHandle.getIdentifier());
        if (packageInfo != null) {
            android.app.ContextImpl contextImpl = new android.app.ContextImpl(this, this.mMainThread, packageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), null, this.mToken, userHandle, i, null, null, this.mDeviceId, this.mIsExplicitDeviceId);
            int displayId = getDisplayId();
            contextImpl.setResources(createResources(this.mToken, packageInfo, null, this.mForceDisplayOverrideInResources ? java.lang.Integer.valueOf(displayId) : null, null, getDisplayAdjustments(displayId).getCompatibilityInfo(), null));
            if (contextImpl.mResources != null) {
                return contextImpl;
            }
        }
        throw new android.content.pm.PackageManager.NameNotFoundException("Application package " + str + " not found");
    }

    @Override // android.content.Context
    public android.content.Context createContextAsUser(android.os.UserHandle userHandle, int i) {
        try {
            return createPackageContextAsUser(getPackageName(), i, userHandle);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.IllegalStateException("Own package not found for user " + userHandle.getIdentifier() + ": package=" + getPackageName());
        }
    }

    @Override // android.content.Context
    public android.content.Context createContextForSplit(java.lang.String str) throws android.content.pm.PackageManager.NameNotFoundException {
        if (!this.mPackageInfo.getApplicationInfo().requestsIsolatedSplitLoading()) {
            return this;
        }
        java.lang.ClassLoader splitClassLoader = this.mPackageInfo.getSplitClassLoader(str);
        java.lang.String[] splitPaths = this.mPackageInfo.getSplitPaths(str);
        android.app.ContextImpl contextImpl = new android.app.ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), str, this.mToken, this.mUser, this.mFlags, splitClassLoader, null, this.mDeviceId, this.mIsExplicitDeviceId);
        contextImpl.setResources(android.app.ResourcesManager.getInstance().getResources(this.mToken, this.mPackageInfo.getResDir(), splitPaths, this.mPackageInfo.getOverlayDirs(), this.mPackageInfo.getOverlayPaths(), this.mPackageInfo.getApplicationInfo().sharedLibraryFiles, this.mForceDisplayOverrideInResources ? java.lang.Integer.valueOf(getDisplayId()) : null, null, this.mPackageInfo.getCompatibilityInfo(), splitClassLoader, this.mResources.getLoaders()));
        return contextImpl;
    }

    @Override // android.content.Context
    public android.content.Context createConfigurationContext(android.content.res.Configuration configuration) {
        android.content.res.Configuration configuration2;
        if (configuration == null) {
            throw new java.lang.IllegalArgumentException("overrideConfiguration must not be null");
        }
        if (!this.mForceDisplayOverrideInResources) {
            configuration2 = configuration;
        } else {
            android.content.res.Configuration configuration3 = new android.content.res.Configuration();
            configuration3.setTo(this.mDisplay.getDisplayAdjustments().getConfiguration(), 536870912, 1);
            configuration3.updateFrom(configuration);
            configuration2 = configuration3;
        }
        android.app.ContextImpl contextImpl = new android.app.ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), this.mSplitName, this.mToken, this.mUser, this.mFlags, this.mClassLoader, null, this.mDeviceId, this.mIsExplicitDeviceId);
        contextImpl.mIsConfigurationBasedContext = true;
        int displayId = getDisplayId();
        contextImpl.setResources(createResources(this.mToken, this.mPackageInfo, this.mSplitName, this.mForceDisplayOverrideInResources ? java.lang.Integer.valueOf(displayId) : null, configuration2, getDisplayAdjustments(displayId).getCompatibilityInfo(), this.mResources.getLoaders()));
        return contextImpl;
    }

    @Override // android.content.Context
    public android.content.Context createDisplayContext(android.view.Display display) {
        if (display == null) {
            throw new java.lang.IllegalArgumentException("display must not be null");
        }
        android.app.ContextImpl contextImpl = new android.app.ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), this.mSplitName, this.mToken, this.mUser, this.mFlags, this.mClassLoader, null, this.mDeviceId, this.mIsExplicitDeviceId);
        int displayId = display.getDisplayId();
        android.content.res.Configuration configuration = new android.content.res.Configuration();
        configuration.setTo(display.getDisplayAdjustments().getConfiguration(), 536870912, 1);
        contextImpl.setResources(createResources(this.mToken, this.mPackageInfo, this.mSplitName, java.lang.Integer.valueOf(displayId), configuration, display.getDisplayAdjustments().getCompatibilityInfo(), this.mResources.getLoaders()));
        contextImpl.setDisplay(display);
        contextImpl.mContextType = this.mContextType != 4 ? 1 : 4;
        contextImpl.mForceDisplayOverrideInResources = true;
        contextImpl.mIsConfigurationBasedContext = false;
        return contextImpl;
    }

    private void setDisplay(android.view.Display display) {
        this.mDisplay = display;
        if (display != null) {
            updateDeviceIdIfChanged(display.getDisplayId());
        }
    }

    @Override // android.content.Context
    public android.content.Context createDeviceContext(int i) {
        android.companion.virtual.VirtualDeviceManager virtualDeviceManager;
        if (i != 0 && ((virtualDeviceManager = (android.companion.virtual.VirtualDeviceManager) getSystemService(android.companion.virtual.VirtualDeviceManager.class)) == null || !virtualDeviceManager.isValidVirtualDeviceId(i))) {
            throw new java.lang.IllegalArgumentException("Not a valid ID of the default device or any virtual device: " + i);
        }
        return new android.app.ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), this.mSplitName, this.mToken, this.mUser, this.mFlags, this.mClassLoader, null, i, true);
    }

    @Override // android.content.Context
    public android.window.WindowContext createWindowContext(int i, android.os.Bundle bundle) {
        if (getDisplay() == null) {
            throw new java.lang.UnsupportedOperationException("Please call this API with context associated with a display instance, such as Activity or context created via Context#createDisplayContext(Display), or try to invoke Context#createWindowContext(Display, int, Bundle)");
        }
        return createWindowContextInternal(getDisplay(), i, bundle);
    }

    @Override // android.content.Context
    public android.window.WindowContext createWindowContext(android.view.Display display, int i, android.os.Bundle bundle) {
        if (display == null) {
            throw new java.lang.IllegalArgumentException("Display must not be null");
        }
        return createWindowContextInternal(display, i, bundle);
    }

    private android.window.WindowContext createWindowContextInternal(android.view.Display display, int i, android.os.Bundle bundle) {
        android.window.WindowTokenClient windowTokenClient = new android.window.WindowTokenClient();
        android.app.ContextImpl createWindowContextBase = createWindowContextBase(windowTokenClient, display.getDisplayId());
        android.window.WindowContext windowContext = new android.window.WindowContext(createWindowContextBase, i, bundle);
        createWindowContextBase.setOuterContext(windowContext);
        windowTokenClient.attachContext(windowContext);
        windowContext.attachToDisplayArea();
        return windowContext;
    }

    @Override // android.content.Context
    public android.content.Context createTokenContext(android.os.IBinder iBinder, android.view.Display display) {
        if (display == null) {
            throw new java.lang.IllegalArgumentException("Display must not be null");
        }
        return createWindowContextBase(iBinder, display.getDisplayId());
    }

    android.app.ContextImpl createWindowContextBase(android.os.IBinder iBinder, int i) {
        android.app.ContextImpl contextImpl = new android.app.ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), this.mSplitName, iBinder, this.mUser, this.mFlags, this.mClassLoader, null, this.mDeviceId, this.mIsExplicitDeviceId);
        contextImpl.mForceDisplayOverrideInResources = false;
        contextImpl.mContextType = 3;
        android.content.res.Resources createWindowContextResources = createWindowContextResources(contextImpl);
        contextImpl.setResources(createWindowContextResources);
        contextImpl.setDisplay(android.app.ResourcesManager.getInstance().getAdjustedDisplay(i, createWindowContextResources));
        return contextImpl;
    }

    private static android.content.res.Resources createWindowContextResources(android.app.ContextImpl contextImpl) {
        android.content.res.CompatibilityInfo compatibilityInfo;
        android.app.LoadedApk loadedApk = contextImpl.mPackageInfo;
        java.lang.ClassLoader classLoader = contextImpl.getClassLoader();
        android.os.IBinder windowContextToken = contextImpl.getWindowContextToken();
        java.lang.String resDir = loadedApk.getResDir();
        java.lang.String[] splitResDirs = loadedApk.getSplitResDirs();
        java.lang.String[] overlayDirs = loadedApk.getOverlayDirs();
        java.lang.String[] overlayPaths = loadedApk.getOverlayPaths();
        java.lang.String[] strArr = loadedApk.getApplicationInfo().sharedLibraryFiles;
        int displayId = contextImpl.getDisplayId();
        if (displayId == 0) {
            compatibilityInfo = loadedApk.getCompatibilityInfo();
        } else {
            compatibilityInfo = android.content.res.CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
        }
        return contextImpl.mResourcesManager.createBaseTokenResources(windowContextToken, resDir, splitResDirs, overlayDirs, overlayPaths, strArr, displayId, null, compatibilityInfo, classLoader, contextImpl.mResources.getLoaders());
    }

    @Override // android.content.Context
    public android.content.Context createContext(android.content.ContextParams contextParams) {
        return new android.app.ContextImpl(this, this.mMainThread, this.mPackageInfo, contextParams, contextParams.getAttributionTag(), contextParams.getNextAttributionSource(), this.mSplitName, this.mToken, this.mUser, this.mFlags, this.mClassLoader, null, this.mDeviceId, this.mIsExplicitDeviceId);
    }

    @Override // android.content.Context
    public android.content.Context createAttributionContext(java.lang.String str) {
        return createContext(new android.content.ContextParams.Builder(this.mParams).setAttributionTag(str).build());
    }

    @Override // android.content.Context
    public android.content.Context createDeviceProtectedStorageContext() {
        return new android.app.ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), this.mSplitName, this.mToken, this.mUser, (this.mFlags & (-17)) | 8, this.mClassLoader, null, this.mDeviceId, this.mIsExplicitDeviceId);
    }

    @Override // android.content.Context
    public android.content.Context createCredentialProtectedStorageContext() {
        return new android.app.ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mParams, this.mAttributionSource.getAttributionTag(), this.mAttributionSource.getNext(), this.mSplitName, this.mToken, this.mUser, (this.mFlags & (-9)) | 16, this.mClassLoader, null, this.mDeviceId, this.mIsExplicitDeviceId);
    }

    @Override // android.content.Context
    public boolean isRestricted() {
        return (this.mFlags & 4) != 0;
    }

    @Override // android.content.Context
    public boolean isDeviceProtectedStorage() {
        return (this.mFlags & 8) != 0;
    }

    @Override // android.content.Context
    public boolean isCredentialProtectedStorage() {
        return (this.mFlags & 16) != 0;
    }

    @Override // android.content.Context
    public boolean canLoadUnsafeResources() {
        return getPackageName().equals(getOpPackageName()) || (this.mFlags & 2) != 0;
    }

    @Override // android.content.Context
    public android.view.Display getDisplay() {
        if (!isAssociatedWithDisplay()) {
            throw new java.lang.UnsupportedOperationException("Tried to obtain display from a Context not associated with one. Only visual Contexts (such as Activity or one created with Context#createWindowContext) or ones created with Context#createDisplayContext are associated with displays. Other types of Contexts are typically related to background entities and may return an arbitrary display.");
        }
        return getDisplayNoVerify();
    }

    private boolean isAssociatedWithDisplay() {
        switch (this.mContextType) {
            case 1:
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }

    @Override // android.content.Context
    public int getAssociatedDisplayId() {
        if (isAssociatedWithDisplay()) {
            return getDisplayId();
        }
        return -1;
    }

    @Override // android.content.Context
    public android.view.Display getDisplayNoVerify() {
        if (this.mDisplay == null) {
            return this.mResourcesManager.getAdjustedDisplay(0, this.mResources);
        }
        return this.mDisplay;
    }

    @Override // android.content.Context
    public int getDisplayId() {
        android.view.Display displayNoVerify = getDisplayNoVerify();
        if (displayNoVerify != null) {
            return displayNoVerify.getDisplayId();
        }
        return 0;
    }

    @Override // android.content.Context
    public void updateDisplay(int i) {
        setDisplay(this.mResourcesManager.getAdjustedDisplay(i, this.mResources));
        if (this.mContextType == 0) {
            this.mContextType = 1;
        }
    }

    private void updateDeviceIdIfChanged(int i) {
        android.companion.virtual.VirtualDeviceManager virtualDeviceManager;
        int deviceIdForDisplayId;
        if (!this.mIsExplicitDeviceId && (virtualDeviceManager = (android.companion.virtual.VirtualDeviceManager) getSystemService(android.companion.virtual.VirtualDeviceManager.class)) != null && (deviceIdForDisplayId = virtualDeviceManager.getDeviceIdForDisplayId(i)) != this.mDeviceId) {
            this.mDeviceId = deviceIdForDisplayId;
            this.mAttributionSource = this.mAttributionSource.withDeviceId(this.mDeviceId);
            notifyOnDeviceChangedListeners(this.mDeviceId);
        }
    }

    @Override // android.content.Context
    public void updateDeviceId(int i) {
        if (i != 0 && !((android.companion.virtual.VirtualDeviceManager) getSystemService(android.companion.virtual.VirtualDeviceManager.class)).isValidVirtualDeviceId(i)) {
            throw new java.lang.IllegalArgumentException("Not a valid ID of the default device or any virtual device: " + i);
        }
        if (this.mIsExplicitDeviceId) {
            throw new java.lang.UnsupportedOperationException("Cannot update device ID on a Context created with createDeviceContext()");
        }
        if (this.mDeviceId != i) {
            this.mDeviceId = i;
            notifyOnDeviceChangedListeners(i);
        }
    }

    @Override // android.content.Context
    public int getDeviceId() {
        return this.mDeviceId;
    }

    @Override // android.content.Context
    public void registerDeviceIdChangeListener(java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
        java.util.Objects.requireNonNull(executor, "executor cannot be null");
        java.util.Objects.requireNonNull(intConsumer, "listener cannot be null");
        synchronized (this.mDeviceIdListenerLock) {
            if (getDeviceIdListener(intConsumer) != null) {
                throw new java.lang.IllegalArgumentException("attempt to call registerDeviceIdChangeListener() on a previously registered listener");
            }
            if (this.mDeviceIdChangeListeners == null) {
                this.mDeviceIdChangeListeners = new java.util.ArrayList<>();
            }
            this.mDeviceIdChangeListeners.add(new android.app.ContextImpl.DeviceIdChangeListenerDelegate(intConsumer, executor));
        }
    }

    @Override // android.content.Context
    public void unregisterDeviceIdChangeListener(java.util.function.IntConsumer intConsumer) {
        java.util.Objects.requireNonNull(intConsumer, "listener cannot be null");
        synchronized (this.mDeviceIdListenerLock) {
            android.app.ContextImpl.DeviceIdChangeListenerDelegate deviceIdListener = getDeviceIdListener(intConsumer);
            if (deviceIdListener != null) {
                this.mDeviceIdChangeListeners.remove(deviceIdListener);
            }
        }
    }

    private android.app.ContextImpl.DeviceIdChangeListenerDelegate getDeviceIdListener(java.util.function.IntConsumer intConsumer) {
        if (this.mDeviceIdChangeListeners == null) {
            return null;
        }
        for (int i = 0; i < this.mDeviceIdChangeListeners.size(); i++) {
            android.app.ContextImpl.DeviceIdChangeListenerDelegate deviceIdChangeListenerDelegate = this.mDeviceIdChangeListeners.get(i);
            if (deviceIdChangeListenerDelegate.mListener == intConsumer) {
                return deviceIdChangeListenerDelegate;
            }
        }
        return null;
    }

    private void notifyOnDeviceChangedListeners(final int i) {
        synchronized (this.mDeviceIdListenerLock) {
            if (this.mDeviceIdChangeListeners != null) {
                java.util.Iterator<android.app.ContextImpl.DeviceIdChangeListenerDelegate> it = this.mDeviceIdChangeListeners.iterator();
                while (it.hasNext()) {
                    final android.app.ContextImpl.DeviceIdChangeListenerDelegate next = it.next();
                    next.mExecutor.execute(new java.lang.Runnable() { // from class: android.app.ContextImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.app.ContextImpl.DeviceIdChangeListenerDelegate.this.mListener.accept(i);
                        }
                    });
                }
            }
        }
    }

    @Override // android.content.Context
    public android.view.DisplayAdjustments getDisplayAdjustments(int i) {
        return this.mResources.getDisplayAdjustments();
    }

    @Override // android.content.Context
    public java.io.File getDataDir() {
        java.io.File dataDirFile;
        if (this.mPackageInfo != null) {
            if (isCredentialProtectedStorage()) {
                dataDirFile = this.mPackageInfo.getCredentialProtectedDataDirFile();
            } else if (isDeviceProtectedStorage()) {
                dataDirFile = this.mPackageInfo.getDeviceProtectedDataDirFile();
            } else {
                dataDirFile = this.mPackageInfo.getDataDirFile();
            }
            if (dataDirFile != null) {
                if (!dataDirFile.exists() && android.os.Process.myUid() == 1000) {
                    android.util.Log.wtf(TAG, "Data directory doesn't exist for package " + getPackageName(), new java.lang.Throwable());
                }
                return dataDirFile;
            }
            throw new java.lang.RuntimeException("No data directory found for package " + getPackageName());
        }
        throw new java.lang.RuntimeException("No package details found for package " + getPackageName());
    }

    @Override // android.content.Context
    public java.io.File getDir(java.lang.String str, int i) {
        checkMode(i);
        java.io.File makeFilename = makeFilename(getDataDir(), "app_" + str);
        if (!makeFilename.exists()) {
            makeFilename.mkdir();
            setFilePermissionsFromMode(makeFilename.getPath(), i, 505);
        }
        return makeFilename;
    }

    @Override // android.content.Context
    public android.os.UserHandle getUser() {
        return this.mUser;
    }

    @Override // android.content.Context
    public int getUserId() {
        return this.mUser.getIdentifier();
    }

    @Override // android.content.Context
    public android.view.autofill.AutofillManager.AutofillClient getAutofillClient() {
        return this.mAutofillClient;
    }

    @Override // android.content.Context
    public void setAutofillClient(android.view.autofill.AutofillManager.AutofillClient autofillClient) {
        this.mAutofillClient = autofillClient;
    }

    @Override // android.content.Context
    public android.content.AutofillOptions getAutofillOptions() {
        return this.mAutofillOptions;
    }

    @Override // android.content.Context
    public void setAutofillOptions(android.content.AutofillOptions autofillOptions) {
        this.mAutofillOptions = autofillOptions;
    }

    @Override // android.content.Context
    public android.content.ContentCaptureOptions getContentCaptureOptions() {
        return this.mContentCaptureOptions;
    }

    @Override // android.content.Context
    public void setContentCaptureOptions(android.content.ContentCaptureOptions contentCaptureOptions) {
        this.mContentCaptureOptions = contentCaptureOptions;
    }

    protected void finalize() throws java.lang.Throwable {
        if ((this.mToken instanceof android.window.WindowTokenClient) && this.mOwnsToken) {
            android.window.WindowTokenClientController.getInstance().detachIfNeeded((android.window.WindowTokenClient) this.mToken);
        }
        super.finalize();
    }

    static android.app.ContextImpl createSystemContext(android.app.ActivityThread activityThread) {
        android.app.LoadedApk loadedApk = new android.app.LoadedApk(activityThread);
        android.app.ContextImpl contextImpl = new android.app.ContextImpl(null, activityThread, loadedApk, android.content.ContextParams.EMPTY, null, null, null, null, null, 0, null, null, 0, false);
        contextImpl.setResources(loadedApk.getResources());
        contextImpl.mResources.updateConfiguration(contextImpl.mResourcesManager.getConfiguration(), contextImpl.mResourcesManager.getDisplayMetrics());
        contextImpl.mContextType = 4;
        return contextImpl;
    }

    static android.app.ContextImpl createSystemUiContext(android.app.ContextImpl contextImpl, int i) {
        android.window.WindowTokenClient windowTokenClient = new android.window.WindowTokenClient();
        android.app.ContextImpl createWindowContextBase = contextImpl.createWindowContextBase(windowTokenClient, i);
        windowTokenClient.attachContext(createWindowContextBase);
        android.window.WindowTokenClientController.getInstance().attachToDisplayContent(windowTokenClient, i);
        createWindowContextBase.mContextType = 4;
        createWindowContextBase.mOwnsToken = true;
        return createWindowContextBase;
    }

    static android.app.ContextImpl createAppContext(android.app.ActivityThread activityThread, android.app.LoadedApk loadedApk) {
        return createAppContext(activityThread, loadedApk, null);
    }

    static android.app.ContextImpl createAppContext(android.app.ActivityThread activityThread, android.app.LoadedApk loadedApk, java.lang.String str) {
        if (loadedApk == null) {
            throw new java.lang.IllegalArgumentException("packageInfo");
        }
        android.app.ContextImpl contextImpl = new android.app.ContextImpl(null, activityThread, loadedApk, android.content.ContextParams.EMPTY, null, null, null, null, null, 0, null, str, 0, false);
        contextImpl.setResources(loadedApk.getResources());
        contextImpl.mContextType = isSystemOrSystemUI(contextImpl) ? 4 : 0;
        return contextImpl;
    }

    static android.app.ContextImpl createActivityContext(android.app.ActivityThread activityThread, android.app.LoadedApk loadedApk, android.content.pm.ActivityInfo activityInfo, android.os.IBinder iBinder, int i, android.content.res.Configuration configuration) {
        java.lang.String[] strArr;
        java.lang.ClassLoader classLoader;
        java.lang.String str;
        android.content.res.CompatibilityInfo compatibilityInfo;
        if (loadedApk == null) {
            throw new java.lang.IllegalArgumentException("packageInfo");
        }
        java.lang.String[] splitResDirs = loadedApk.getSplitResDirs();
        java.lang.ClassLoader classLoader2 = loadedApk.getClassLoader();
        if (!loadedApk.getApplicationInfo().requestsIsolatedSplitLoading()) {
            strArr = splitResDirs;
            classLoader = classLoader2;
        } else {
            android.os.Trace.traceBegin(8192L, "SplitDependencies");
            try {
                try {
                    java.lang.ClassLoader splitClassLoader = loadedApk.getSplitClassLoader(activityInfo.splitName);
                    java.lang.String[] splitPaths = loadedApk.getSplitPaths(activityInfo.splitName);
                    android.os.Trace.traceEnd(8192L);
                    classLoader = splitClassLoader;
                    strArr = splitPaths;
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    throw new java.lang.RuntimeException(e);
                }
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(8192L);
                throw th;
            }
        }
        if (activityInfo.attributionTags != null && activityInfo.attributionTags.length > 0) {
            str = activityInfo.attributionTags[0];
        } else {
            str = null;
        }
        android.app.ContextImpl contextImpl = new android.app.ContextImpl(null, activityThread, loadedApk, android.content.ContextParams.EMPTY, str, null, activityInfo.splitName, iBinder, null, 0, classLoader, null, 0, false);
        contextImpl.mContextType = 2;
        contextImpl.mIsConfigurationBasedContext = true;
        int i2 = i;
        if (i2 == -1) {
            i2 = 0;
        }
        if (i2 == 0) {
            compatibilityInfo = loadedApk.getCompatibilityInfo();
        } else {
            compatibilityInfo = android.content.res.CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
        }
        android.app.ResourcesManager resourcesManager = android.app.ResourcesManager.getInstance();
        contextImpl.setResources(resourcesManager.createBaseTokenResources(iBinder, loadedApk.getResDir(), strArr, loadedApk.getOverlayDirs(), loadedApk.getOverlayPaths(), loadedApk.getApplicationInfo().sharedLibraryFiles, i2, configuration, compatibilityInfo, classLoader, loadedApk.getApplication() == null ? null : loadedApk.getApplication().getResources().getLoaders()));
        contextImpl.setDisplay(resourcesManager.getAdjustedDisplay(i2, contextImpl.getResources()));
        return contextImpl;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private ContextImpl(android.app.ContextImpl contextImpl, android.app.ActivityThread activityThread, android.app.LoadedApk loadedApk, android.content.ContextParams contextParams, java.lang.String str, android.content.AttributionSource attributionSource, java.lang.String str2, android.os.IBinder iBinder, android.os.UserHandle userHandle, int i, java.lang.ClassLoader classLoader, java.lang.String str3, int i2, boolean z) {
        int i3;
        android.os.UserHandle userHandle2;
        java.lang.String str4;
        this.mDeviceId = 0;
        this.mIsExplicitDeviceId = false;
        this.mSplitName = null;
        this.mContentCaptureOptions = null;
        if ((i & 24) == 0) {
            java.io.File dataDirFile = loadedApk.getDataDirFile();
            if (java.util.Objects.equals(dataDirFile, loadedApk.getCredentialProtectedDataDirFile())) {
                i3 = i | 16;
            } else if (java.util.Objects.equals(dataDirFile, loadedApk.getDeviceProtectedDataDirFile())) {
                i3 = i | 8;
            }
            this.mMainThread = activityThread;
            this.mToken = iBinder;
            this.mFlags = i3;
            if (userHandle == null) {
                userHandle2 = userHandle;
            } else {
                userHandle2 = android.os.Process.myUserHandle();
            }
            this.mUser = userHandle2;
            this.mPackageInfo = loadedApk;
            this.mSplitName = str2;
            this.mClassLoader = classLoader;
            this.mResourcesManager = android.app.ResourcesManager.getInstance();
            this.mDeviceId = i2;
            this.mIsExplicitDeviceId = z;
            if (contextImpl == null) {
                this.mBasePackageName = contextImpl.mBasePackageName;
                str4 = contextImpl.mOpPackageName;
                setResources(contextImpl.mResources);
                this.mDisplay = contextImpl.mDisplay;
                if (!z) {
                    this.mIsExplicitDeviceId = contextImpl.mIsExplicitDeviceId;
                    this.mDeviceId = contextImpl.mDeviceId;
                }
                this.mForceDisplayOverrideInResources = contextImpl.mForceDisplayOverrideInResources;
                this.mIsConfigurationBasedContext = contextImpl.mIsConfigurationBasedContext;
                this.mContextType = contextImpl.mContextType;
                this.mContentCaptureOptions = contextImpl.mContentCaptureOptions;
                this.mAutofillOptions = contextImpl.mAutofillOptions;
            } else {
                this.mBasePackageName = loadedApk.mPackageName;
                android.content.pm.ApplicationInfo applicationInfo = loadedApk.getApplicationInfo();
                if (applicationInfo.uid == 1000 && applicationInfo.uid != android.os.Process.myUid()) {
                    str4 = android.app.ActivityThread.currentPackageName();
                } else {
                    str4 = this.mBasePackageName;
                }
            }
            this.mOpPackageName = str3 != null ? str3 : str4;
            this.mParams = (android.content.ContextParams) java.util.Objects.requireNonNull(contextParams);
            this.mAttributionSource = createAttributionSource(str, attributionSource, contextParams.getRenouncedPermissions(), contextParams.shouldRegisterAttributionSource(), this.mDeviceId);
            this.mContentResolver = new android.app.ContextImpl.ApplicationContentResolver(this, activityThread);
        }
        i3 = i;
        this.mMainThread = activityThread;
        this.mToken = iBinder;
        this.mFlags = i3;
        if (userHandle == null) {
        }
        this.mUser = userHandle2;
        this.mPackageInfo = loadedApk;
        this.mSplitName = str2;
        this.mClassLoader = classLoader;
        this.mResourcesManager = android.app.ResourcesManager.getInstance();
        this.mDeviceId = i2;
        this.mIsExplicitDeviceId = z;
        if (contextImpl == null) {
        }
        this.mOpPackageName = str3 != null ? str3 : str4;
        this.mParams = (android.content.ContextParams) java.util.Objects.requireNonNull(contextParams);
        this.mAttributionSource = createAttributionSource(str, attributionSource, contextParams.getRenouncedPermissions(), contextParams.shouldRegisterAttributionSource(), this.mDeviceId);
        this.mContentResolver = new android.app.ContextImpl.ApplicationContentResolver(this, activityThread);
    }

    private android.content.AttributionSource createAttributionSource(java.lang.String str, android.content.AttributionSource attributionSource, java.util.Set<java.lang.String> set, boolean z, int i) {
        android.content.AttributionSource attributionSource2 = new android.content.AttributionSource(android.os.Process.myUid(), android.os.Process.myPid(), this.mOpPackageName, str, set != null ? (java.lang.String[]) set.toArray(new java.lang.String[0]) : null, i, attributionSource);
        if (attributionSource != null || z) {
            return ((android.permission.PermissionManager) getSystemService(android.permission.PermissionManager.class)).registerAttributionSource(attributionSource2);
        }
        return attributionSource2;
    }

    void setResources(android.content.res.Resources resources) {
        if (resources instanceof android.content.res.CompatResources) {
            ((android.content.res.CompatResources) resources).setContext(this);
        }
        this.mResources = resources;
        if (resources != null && android.content.res.Flags.defaultLocale() && resources.getConfiguration().getLocales().size() > 1) {
            this.mResourcesManager.setLocaleConfig(android.app.LocaleConfig.fromContextIgnoringOverride(this));
        }
    }

    void installSystemApplicationInfo(android.content.pm.ApplicationInfo applicationInfo, java.lang.ClassLoader classLoader) {
        this.mPackageInfo.installSystemApplicationInfo(applicationInfo, classLoader);
    }

    final void scheduleFinalCleanup(java.lang.String str, java.lang.String str2) {
        this.mMainThread.scheduleContextCleanup(this, str, str2);
    }

    final void performFinalCleanup(java.lang.String str, java.lang.String str2) {
        this.mPackageInfo.removeContextRegistrations(getOuterContext(), str, str2);
        if (this.mContextType == 4 && (this.mToken instanceof android.window.WindowTokenClient)) {
            this.mMainThread.onSystemUiContextCleanup(this);
        }
    }

    final android.content.Context getReceiverRestrictedContext() {
        if (this.mReceiverRestrictedContext != null) {
            return this.mReceiverRestrictedContext;
        }
        android.app.ReceiverRestrictedContext receiverRestrictedContext = new android.app.ReceiverRestrictedContext(getOuterContext());
        this.mReceiverRestrictedContext = receiverRestrictedContext;
        return receiverRestrictedContext;
    }

    final void setOuterContext(android.content.Context context) {
        this.mOuterContext = context;
    }

    final android.content.Context getOuterContext() {
        return this.mOuterContext;
    }

    @Override // android.content.Context
    public android.os.IBinder getActivityToken() {
        if (this.mContextType == 2) {
            return this.mToken;
        }
        return null;
    }

    @Override // android.content.Context
    public android.os.IBinder getWindowContextToken() {
        switch (this.mContextType) {
            case 3:
            case 4:
                return this.mToken;
            default:
                return null;
        }
    }

    private void checkMode(int i) {
        if (getApplicationInfo().targetSdkVersion >= 24) {
            if ((i & 1) != 0) {
                throw new java.lang.SecurityException("MODE_WORLD_READABLE no longer supported");
            }
            if ((i & 2) != 0) {
                throw new java.lang.SecurityException("MODE_WORLD_WRITEABLE no longer supported");
            }
        }
    }

    static void setFilePermissionsFromMode(java.lang.String str, int i, int i2) {
        int i3 = i2 | 432;
        if ((i & 1) != 0) {
            i3 |= 4;
        }
        if ((i & 2) != 0) {
            i3 |= 2;
        }
        android.os.FileUtils.setPermissions(str, i3, -1, -1);
    }

    private java.io.File makeFilename(java.io.File file, java.lang.String str) {
        if (str.indexOf(java.io.File.separatorChar) < 0) {
            java.io.File file2 = new java.io.File(file, str);
            dalvik.system.BlockGuard.getVmPolicy().onPathAccess(file2.getPath());
            return file2;
        }
        throw new java.lang.IllegalArgumentException("File " + str + " contains a path separator");
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001d, code lost:
    
        if (r3.mkdirs() == false) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private java.io.File[] ensureExternalDirsExistOrFilter(java.io.File[] fileArr, boolean z) {
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) getSystemService(android.os.storage.StorageManager.class);
        java.io.File[] fileArr2 = new java.io.File[fileArr.length];
        for (int i = 0; i < fileArr.length; i++) {
            java.io.File file = fileArr[i];
            if (!file.exists()) {
                if (z) {
                    try {
                    } catch (java.lang.Exception e) {
                        android.util.Log.w(TAG, "Failed to ensure " + file + ": " + e);
                        file = null;
                    }
                }
                if (!file.exists()) {
                    storageManager.mkdirs(file);
                }
            }
            if (file != null && !file.canWrite()) {
                storageManager.fixupAppDir(file);
            }
            fileArr2[i] = file;
        }
        return fileArr2;
    }

    @Override // android.content.Context
    public void destroy() {
        scheduleFinalCleanup(getClass().getName(), getOuterContext().getClass().getSimpleName());
    }

    @Override // android.content.Context
    public void closeSystemDialogs() {
        sendBroadcast(new android.content.Intent(android.content.Intent.ACTION_CLOSE_SYSTEM_DIALOGS).addFlags(268435456), (java.lang.String) null, android.app.BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeferralPolicy(2).toBundle());
    }

    private static final class ApplicationContentResolver extends android.content.ContentResolver {
        private final android.app.ActivityThread mMainThread;

        public ApplicationContentResolver(android.content.Context context, android.app.ActivityThread activityThread) {
            super(context);
            this.mMainThread = (android.app.ActivityThread) java.util.Objects.requireNonNull(activityThread);
        }

        @Override // android.content.ContentResolver
        protected android.content.IContentProvider acquireProvider(android.content.Context context, java.lang.String str) {
            return this.mMainThread.acquireProvider(context, android.content.ContentProvider.getAuthorityWithoutUserId(str), resolveUserIdFromAuthority(str), true);
        }

        @Override // android.content.ContentResolver
        protected android.content.IContentProvider acquireExistingProvider(android.content.Context context, java.lang.String str) {
            return this.mMainThread.acquireExistingProvider(context, android.content.ContentProvider.getAuthorityWithoutUserId(str), resolveUserIdFromAuthority(str), true);
        }

        @Override // android.content.ContentResolver
        public boolean releaseProvider(android.content.IContentProvider iContentProvider) {
            return this.mMainThread.releaseProvider(iContentProvider, true);
        }

        @Override // android.content.ContentResolver
        protected android.content.IContentProvider acquireUnstableProvider(android.content.Context context, java.lang.String str) {
            return this.mMainThread.acquireProvider(context, android.content.ContentProvider.getAuthorityWithoutUserId(str), resolveUserIdFromAuthority(str), false);
        }

        @Override // android.content.ContentResolver
        public boolean releaseUnstableProvider(android.content.IContentProvider iContentProvider) {
            return this.mMainThread.releaseProvider(iContentProvider, false);
        }

        @Override // android.content.ContentResolver
        public void unstableProviderDied(android.content.IContentProvider iContentProvider) {
            this.mMainThread.handleUnstableProviderDied(iContentProvider.asBinder(), true);
        }

        @Override // android.content.ContentResolver
        public void appNotRespondingViaProvider(android.content.IContentProvider iContentProvider) {
            this.mMainThread.appNotRespondingViaProvider(iContentProvider.asBinder());
        }

        protected int resolveUserIdFromAuthority(java.lang.String str) {
            return android.content.ContentProvider.getUserIdFromAuthority(str, getUserId());
        }
    }
}
