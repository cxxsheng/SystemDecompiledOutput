package android.app.backup;

/* loaded from: classes.dex */
public abstract class BackupAgent extends android.content.ContextWrapper {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_BACKUP_DESTINATION = 0;
    public static final int FLAG_CLIENT_SIDE_ENCRYPTION_ENABLED = 1;
    public static final int FLAG_DEVICE_TO_DEVICE_TRANSFER = 2;
    public static final int FLAG_FAKE_CLIENT_SIDE_ENCRYPTION_ENABLED = Integer.MIN_VALUE;
    public static final int FLAG_SKIP_RESTORE_FOR_LAUNCHED_APPS = 4;
    public static final int RESULT_ERROR = -1;
    public static final int RESULT_SUCCESS = 0;
    private static final java.lang.String TAG = "BackupAgent";
    public static final int TYPE_DIRECTORY = 2;
    public static final int TYPE_EOF = 0;
    public static final int TYPE_FILE = 1;
    public static final int TYPE_SYMLINK = 3;
    private volatile int mBackupDestination;
    private final android.os.IBinder mBinder;
    android.os.Handler mHandler;
    private volatile android.app.backup.BackupRestoreEventLogger mLogger;
    private android.os.UserHandle mUser;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BackupTransportFlags {
    }

    public abstract void onBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.app.backup.BackupDataOutput backupDataOutput, android.os.ParcelFileDescriptor parcelFileDescriptor2) throws java.io.IOException;

    public abstract void onRestore(android.app.backup.BackupDataInput backupDataInput, int i, android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException;

    android.os.Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        }
        return this.mHandler;
    }

    class SharedPrefsSynchronizer implements java.lang.Runnable {
        public final java.util.concurrent.CountDownLatch mLatch = new java.util.concurrent.CountDownLatch(1);

        SharedPrefsSynchronizer() {
        }

        @Override // java.lang.Runnable
        public void run() {
            android.app.QueuedWork.waitToFinish();
            this.mLatch.countDown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void waitForSharedPrefs() {
        android.os.Handler handler = getHandler();
        android.app.backup.BackupAgent.SharedPrefsSynchronizer sharedPrefsSynchronizer = new android.app.backup.BackupAgent.SharedPrefsSynchronizer();
        handler.postAtFrontOfQueue(sharedPrefsSynchronizer);
        try {
            sharedPrefsSynchronizer.mLatch.await();
        } catch (java.lang.InterruptedException e) {
        }
    }

    public android.app.backup.BackupRestoreEventLogger getBackupRestoreEventLogger() {
        return this.mLogger;
    }

    public BackupAgent() {
        super(null);
        this.mHandler = null;
        this.mLogger = null;
        this.mBackupDestination = 0;
        this.mBinder = new android.app.backup.BackupAgent.BackupServiceBinder().asBinder();
    }

    public void onCreate() {
    }

    public void onCreate(android.os.UserHandle userHandle) {
        this.mUser = userHandle;
        onCreate();
    }

    @java.lang.Deprecated
    public void onCreate(android.os.UserHandle userHandle, int i) {
        this.mBackupDestination = i;
        onCreate(userHandle);
    }

    public void onCreate(android.os.UserHandle userHandle, int i, int i2) {
        this.mBackupDestination = i;
        this.mLogger = new android.app.backup.BackupRestoreEventLogger(i2);
        onCreate(userHandle, i);
    }

    public void onDestroy() {
    }

    public void onRestore(android.app.backup.BackupDataInput backupDataInput, long j, android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
        onRestore(backupDataInput, (int) j, parcelFileDescriptor);
    }

    public void onRestore(android.app.backup.BackupDataInput backupDataInput, long j, android.os.ParcelFileDescriptor parcelFileDescriptor, java.util.Set<java.lang.String> set) throws java.io.IOException {
        onRestore(backupDataInput, j, parcelFileDescriptor);
    }

    public void onFullBackup(android.app.backup.FullBackupDataOutput fullBackupDataOutput) throws java.io.IOException {
        java.lang.String str;
        android.app.backup.FullBackup.BackupScheme backupScheme = android.app.backup.FullBackup.getBackupScheme(this, this.mBackupDestination);
        if (!backupScheme.isFullBackupEnabled(fullBackupDataOutput.getTransportFlags())) {
            return;
        }
        try {
            android.app.backup.BackupAgent.IncludeExcludeRules includeExcludeRules = getIncludeExcludeRules(backupScheme);
            java.util.Map<java.lang.String, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> includeMap = includeExcludeRules.getIncludeMap();
            java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> excludeSet = includeExcludeRules.getExcludeSet();
            java.lang.String packageName = getPackageName();
            android.content.pm.ApplicationInfo applicationInfo = getApplicationInfo();
            android.content.Context createCredentialProtectedStorageContext = createCredentialProtectedStorageContext();
            java.lang.String canonicalPath = createCredentialProtectedStorageContext.getDataDir().getCanonicalPath();
            java.lang.String canonicalPath2 = createCredentialProtectedStorageContext.getFilesDir().getCanonicalPath();
            java.lang.String canonicalPath3 = createCredentialProtectedStorageContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
            java.lang.String canonicalPath4 = createCredentialProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
            android.content.Context createDeviceProtectedStorageContext = createDeviceProtectedStorageContext();
            java.lang.String canonicalPath5 = createDeviceProtectedStorageContext.getDataDir().getCanonicalPath();
            java.lang.String canonicalPath6 = createDeviceProtectedStorageContext.getFilesDir().getCanonicalPath();
            java.lang.String canonicalPath7 = createDeviceProtectedStorageContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
            java.lang.String canonicalPath8 = createDeviceProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
            if (applicationInfo.nativeLibraryDir != null) {
                str = new java.io.File(applicationInfo.nativeLibraryDir).getCanonicalPath();
            } else {
                str = null;
            }
            android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
            arraySet.add(canonicalPath2);
            arraySet.add(canonicalPath3);
            arraySet.add(canonicalPath4);
            arraySet.add(canonicalPath6);
            arraySet.add(canonicalPath7);
            arraySet.add(canonicalPath8);
            if (str != null) {
                arraySet.add(str);
            }
            java.util.Set<java.lang.String> extraExcludeDirsIfAny = getExtraExcludeDirsIfAny(createCredentialProtectedStorageContext);
            java.util.Set<java.lang.String> extraExcludeDirsIfAny2 = getExtraExcludeDirsIfAny(createDeviceProtectedStorageContext);
            arraySet.addAll(extraExcludeDirsIfAny);
            arraySet.addAll(extraExcludeDirsIfAny2);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, "r", includeMap, excludeSet, arraySet, fullBackupDataOutput);
            arraySet.add(canonicalPath);
            arraySet.addAll(extraExcludeDirsIfAny);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, android.app.backup.FullBackup.DEVICE_ROOT_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
            arraySet.add(canonicalPath5);
            arraySet.addAll(extraExcludeDirsIfAny2);
            arraySet.remove(canonicalPath2);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, android.app.backup.FullBackup.FILES_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
            arraySet.add(canonicalPath2);
            arraySet.remove(canonicalPath6);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, android.app.backup.FullBackup.DEVICE_FILES_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
            arraySet.add(canonicalPath6);
            arraySet.remove(canonicalPath3);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, android.app.backup.FullBackup.DATABASE_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
            arraySet.add(canonicalPath3);
            arraySet.remove(canonicalPath7);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, android.app.backup.FullBackup.DEVICE_DATABASE_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
            arraySet.add(canonicalPath7);
            arraySet.remove(canonicalPath4);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, android.app.backup.FullBackup.SHAREDPREFS_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
            arraySet.add(canonicalPath4);
            arraySet.remove(canonicalPath8);
            applyXmlFiltersAndDoFullBackupForDomain(packageName, android.app.backup.FullBackup.DEVICE_SHAREDPREFS_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
            arraySet.add(canonicalPath8);
            if (android.os.Process.myUid() != 1000 && getExternalFilesDir(null) != null) {
                applyXmlFiltersAndDoFullBackupForDomain(packageName, android.app.backup.FullBackup.MANAGED_EXTERNAL_TREE_TOKEN, includeMap, excludeSet, arraySet, fullBackupDataOutput);
            }
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            if (android.util.Log.isLoggable("BackupXmlParserLogging", 2)) {
                android.util.Log.v("BackupXmlParserLogging", "Exception trying to parse fullBackupContent xml file! Aborting full backup.", e);
            }
        }
    }

    private java.util.Set<java.lang.String> getExtraExcludeDirsIfAny(android.content.Context context) throws java.io.IOException {
        java.util.HashSet hashSet = new java.util.HashSet();
        hashSet.add(context.getCacheDir().getCanonicalPath());
        hashSet.add(context.getCodeCacheDir().getCanonicalPath());
        hashSet.add(context.getNoBackupFilesDir().getCanonicalPath());
        return java.util.Collections.unmodifiableSet(hashSet);
    }

    public android.app.backup.BackupAgent.IncludeExcludeRules getIncludeExcludeRules(android.app.backup.FullBackup.BackupScheme backupScheme) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        return new android.app.backup.BackupAgent.IncludeExcludeRules(backupScheme.maybeParseAndGetCanonicalIncludePaths(), backupScheme.maybeParseAndGetCanonicalExcludePaths());
    }

    public void onQuotaExceeded(long j, long j2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getBackupUserId() {
        return this.mUser == null ? super.getUserId() : this.mUser.getIdentifier();
    }

    private void applyXmlFiltersAndDoFullBackupForDomain(java.lang.String str, java.lang.String str2, java.util.Map<java.lang.String, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> map, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> set, android.util.ArraySet<java.lang.String> arraySet, android.app.backup.FullBackupDataOutput fullBackupDataOutput) throws java.io.IOException {
        if (map == null || map.size() == 0) {
            fullBackupFileTree(str, str2, android.app.backup.FullBackup.getBackupScheme(this, this.mBackupDestination).tokenToDirectoryPath(str2), set, arraySet, fullBackupDataOutput);
            return;
        }
        if (map.get(str2) != null) {
            for (android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags pathWithRequiredFlags : map.get(str2)) {
                if (areIncludeRequiredTransportFlagsSatisfied(pathWithRequiredFlags.getRequiredFlags(), fullBackupDataOutput.getTransportFlags())) {
                    fullBackupFileTree(str, str2, pathWithRequiredFlags.getPath(), set, arraySet, fullBackupDataOutput);
                }
            }
        }
    }

    private boolean areIncludeRequiredTransportFlagsSatisfied(int i, int i2) {
        return (i2 & i) == i;
    }

    public final void fullBackupFile(java.io.File file, android.app.backup.FullBackupDataOutput fullBackupDataOutput) {
        java.lang.String canonicalPath;
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        java.lang.String str5 = TAG;
        android.content.pm.ApplicationInfo applicationInfo = getApplicationInfo();
        try {
            android.content.Context createCredentialProtectedStorageContext = createCredentialProtectedStorageContext();
            java.lang.String canonicalPath2 = createCredentialProtectedStorageContext.getDataDir().getCanonicalPath();
            java.lang.String canonicalPath3 = createCredentialProtectedStorageContext.getFilesDir().getCanonicalPath();
            java.lang.String canonicalPath4 = createCredentialProtectedStorageContext.getNoBackupFilesDir().getCanonicalPath();
            java.lang.String canonicalPath5 = createCredentialProtectedStorageContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
            java.lang.String canonicalPath6 = createCredentialProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
            java.lang.String canonicalPath7 = createCredentialProtectedStorageContext.getCacheDir().getCanonicalPath();
            java.lang.String canonicalPath8 = createCredentialProtectedStorageContext.getCodeCacheDir().getCanonicalPath();
            android.content.Context createDeviceProtectedStorageContext = createDeviceProtectedStorageContext();
            java.lang.String canonicalPath9 = createDeviceProtectedStorageContext.getDataDir().getCanonicalPath();
            java.lang.String canonicalPath10 = createDeviceProtectedStorageContext.getFilesDir().getCanonicalPath();
            java.lang.String canonicalPath11 = createDeviceProtectedStorageContext.getNoBackupFilesDir().getCanonicalPath();
            java.lang.String canonicalPath12 = createDeviceProtectedStorageContext.getDatabasePath("foo").getParentFile().getCanonicalPath();
            java.lang.String canonicalPath13 = createDeviceProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
            java.lang.String canonicalPath14 = createDeviceProtectedStorageContext.getCacheDir().getCanonicalPath();
            java.lang.String canonicalPath15 = createDeviceProtectedStorageContext.getCodeCacheDir().getCanonicalPath();
            try {
                if (applicationInfo.nativeLibraryDir == null) {
                    canonicalPath = null;
                } else {
                    canonicalPath = new java.io.File(applicationInfo.nativeLibraryDir).getCanonicalPath();
                }
                if (android.os.Process.myUid() == 1000) {
                    str = null;
                } else {
                    str = null;
                    java.io.File externalFilesDir = getExternalFilesDir(null);
                    if (externalFilesDir != null) {
                        str = externalFilesDir.getCanonicalPath();
                    }
                }
                java.lang.String canonicalPath16 = file.getCanonicalPath();
                if (canonicalPath16.startsWith(canonicalPath7)) {
                    str2 = TAG;
                } else if (canonicalPath16.startsWith(canonicalPath8)) {
                    str2 = TAG;
                } else if (canonicalPath16.startsWith(canonicalPath4)) {
                    str2 = TAG;
                } else if (canonicalPath16.startsWith(canonicalPath14)) {
                    str2 = TAG;
                } else if (canonicalPath16.startsWith(canonicalPath15)) {
                    str2 = TAG;
                } else if (canonicalPath16.startsWith(canonicalPath11)) {
                    str2 = TAG;
                } else if (canonicalPath16.startsWith(canonicalPath)) {
                    str2 = TAG;
                } else {
                    if (canonicalPath16.startsWith(canonicalPath5)) {
                        str3 = android.app.backup.FullBackup.DATABASE_TREE_TOKEN;
                        str4 = canonicalPath5;
                    } else if (canonicalPath16.startsWith(canonicalPath6)) {
                        str3 = android.app.backup.FullBackup.SHAREDPREFS_TREE_TOKEN;
                        str4 = canonicalPath6;
                    } else if (canonicalPath16.startsWith(canonicalPath3)) {
                        str3 = android.app.backup.FullBackup.FILES_TREE_TOKEN;
                        str4 = canonicalPath3;
                    } else if (canonicalPath16.startsWith(canonicalPath2)) {
                        str3 = "r";
                        str4 = canonicalPath2;
                    } else if (canonicalPath16.startsWith(canonicalPath12)) {
                        str3 = android.app.backup.FullBackup.DEVICE_DATABASE_TREE_TOKEN;
                        str4 = canonicalPath12;
                    } else if (canonicalPath16.startsWith(canonicalPath13)) {
                        str4 = canonicalPath13;
                        str3 = android.app.backup.FullBackup.DEVICE_SHAREDPREFS_TREE_TOKEN;
                    } else if (canonicalPath16.startsWith(canonicalPath10)) {
                        str3 = android.app.backup.FullBackup.DEVICE_FILES_TREE_TOKEN;
                        str4 = canonicalPath10;
                    } else if (canonicalPath16.startsWith(canonicalPath9)) {
                        str4 = canonicalPath9;
                        str3 = android.app.backup.FullBackup.DEVICE_ROOT_TREE_TOKEN;
                    } else if (str == null || !canonicalPath16.startsWith(str)) {
                        android.util.Log.w(TAG, "File " + canonicalPath16 + " is in an unsupported location; skipping");
                        return;
                    } else {
                        str3 = android.app.backup.FullBackup.MANAGED_EXTERNAL_TREE_TOKEN;
                        str4 = str;
                    }
                    android.app.backup.FullBackup.backupToTar(getPackageName(), str3, null, str4, canonicalPath16, fullBackupDataOutput);
                    return;
                }
                android.util.Log.w(str2, "lib, cache, code_cache, and no_backup files are not backed up");
            } catch (java.io.IOException e) {
                str5 = TAG;
                android.util.Log.w(str5, "Unable to obtain canonical paths");
            }
        } catch (java.io.IOException e2) {
        }
    }

    protected final void fullBackupFileTree(java.lang.String str, java.lang.String str2, java.lang.String str3, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> set, android.util.ArraySet<java.lang.String> arraySet, android.app.backup.FullBackupDataOutput fullBackupDataOutput) {
        java.io.File[] listFiles;
        java.lang.String str4 = android.app.backup.FullBackup.getBackupScheme(this, this.mBackupDestination).tokenToDirectoryPath(str2);
        if (str4 == null) {
            return;
        }
        java.io.File file = new java.io.File(str3);
        if (file.exists()) {
            java.util.LinkedList linkedList = new java.util.LinkedList();
            linkedList.add(file);
            while (linkedList.size() > 0) {
                java.io.File file2 = (java.io.File) linkedList.remove(0);
                try {
                    android.system.StructStat lstat = android.system.Os.lstat(file2.getPath());
                    if (android.system.OsConstants.S_ISREG(lstat.st_mode) || android.system.OsConstants.S_ISDIR(lstat.st_mode)) {
                        java.lang.String canonicalPath = file2.getCanonicalPath();
                        if (set == null || !manifestExcludesContainFilePath(set, canonicalPath)) {
                            if (arraySet == null || !arraySet.contains(canonicalPath)) {
                                if (android.system.OsConstants.S_ISDIR(lstat.st_mode) && (listFiles = file2.listFiles()) != null) {
                                    for (java.io.File file3 : listFiles) {
                                        linkedList.add(0, file3);
                                    }
                                }
                                android.app.backup.FullBackup.backupToTar(str, str2, null, str4, canonicalPath, fullBackupDataOutput);
                            }
                        }
                    }
                } catch (android.system.ErrnoException e) {
                    if (android.util.Log.isLoggable("BackupXmlParserLogging", 2)) {
                        android.util.Log.v("BackupXmlParserLogging", "Error scanning file " + file2 + " : " + e);
                    }
                } catch (java.io.IOException e2) {
                    if (android.util.Log.isLoggable("BackupXmlParserLogging", 2)) {
                        android.util.Log.v("BackupXmlParserLogging", "Error canonicalizing path of " + file2);
                    }
                }
            }
        }
    }

    private boolean manifestExcludesContainFilePath(java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> set, java.lang.String str) {
        java.util.Iterator<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> it = set.iterator();
        while (it.hasNext()) {
            java.lang.String path = it.next().getPath();
            if (path != null && path.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void onRestoreFile(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, java.io.File file, int i, long j2, long j3) throws java.io.IOException {
        android.app.backup.FullBackup.restoreFile(parcelFileDescriptor, j, i, j2, j3, isFileEligibleForRestore(file) ? file : null);
    }

    private boolean isFileEligibleForRestore(java.io.File file) throws java.io.IOException {
        android.app.backup.FullBackup.BackupScheme backupScheme = android.app.backup.FullBackup.getBackupScheme(this, this.mBackupDestination);
        if (!backupScheme.isFullRestoreEnabled()) {
            if (android.util.Log.isLoggable("BackupXmlParserLogging", 2)) {
                android.util.Log.v("BackupXmlParserLogging", "onRestoreFile \"" + file.getCanonicalPath() + "\" : fullBackupContent not enabled for " + getPackageName());
            }
            return false;
        }
        java.lang.String canonicalPath = file.getCanonicalPath();
        try {
            java.util.Map<java.lang.String, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> maybeParseAndGetCanonicalIncludePaths = backupScheme.maybeParseAndGetCanonicalIncludePaths();
            android.util.ArraySet<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> maybeParseAndGetCanonicalExcludePaths = backupScheme.maybeParseAndGetCanonicalExcludePaths();
            if (maybeParseAndGetCanonicalExcludePaths != null && android.app.backup.BackupUtils.isFileSpecifiedInPathList(file, maybeParseAndGetCanonicalExcludePaths)) {
                if (android.util.Log.isLoggable("BackupXmlParserLogging", 2)) {
                    android.util.Log.v("BackupXmlParserLogging", "onRestoreFile: \"" + canonicalPath + "\": listed in excludes; skipping.");
                }
                return false;
            }
            if (maybeParseAndGetCanonicalIncludePaths != null && !maybeParseAndGetCanonicalIncludePaths.isEmpty()) {
                java.util.Iterator<java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> it = maybeParseAndGetCanonicalIncludePaths.values().iterator();
                boolean z = false;
                while (it.hasNext() && !((z = z | android.app.backup.BackupUtils.isFileSpecifiedInPathList(file, it.next())))) {
                }
                if (!z) {
                    if (android.util.Log.isLoggable("BackupXmlParserLogging", 2)) {
                        android.util.Log.v("BackupXmlParserLogging", "onRestoreFile: Trying to restore \"" + canonicalPath + "\" but it isn't specified in the included files; skipping.");
                    }
                    return false;
                }
                return true;
            }
            return true;
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            if (android.util.Log.isLoggable("BackupXmlParserLogging", 2)) {
                android.util.Log.v("BackupXmlParserLogging", "onRestoreFile \"" + canonicalPath + "\" : Exception trying to parse fullBackupContent xml file! Aborting onRestoreFile.", e);
            }
            return false;
        }
    }

    protected void onRestoreFile(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, java.lang.String str, java.lang.String str2, long j2, long j3) throws java.io.IOException {
        long j4;
        java.lang.String str3 = android.app.backup.FullBackup.getBackupScheme(this, this.mBackupDestination).tokenToDirectoryPath(str);
        if (!str.equals(android.app.backup.FullBackup.MANAGED_EXTERNAL_TREE_TOKEN)) {
            j4 = j2;
        } else {
            j4 = -1;
        }
        if (str3 != null) {
            java.io.File file = new java.io.File(str3, str2);
            if (file.getCanonicalPath().startsWith(str3 + java.io.File.separatorChar)) {
                onRestoreFile(parcelFileDescriptor, j, file, i, j4, j3);
                return;
            }
        }
        android.app.backup.FullBackup.restoreFile(parcelFileDescriptor, j, i, j4, j3, null);
    }

    public void onRestoreFinished() {
    }

    public final void clearBackupRestoreEventLogger() {
        if (this.mLogger != null) {
            this.mLogger.clearData();
        }
    }

    public final android.os.IBinder onBind() {
        return this.mBinder;
    }

    public void attach(android.content.Context context) {
        attachBaseContext(context);
    }

    private class BackupServiceBinder extends android.app.IBackupAgent.Stub {
        private static final java.lang.String TAG = "BackupServiceBinder";

        private BackupServiceBinder() {
        }

        @Override // android.app.IBackupAgent
        public void doBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.ParcelFileDescriptor parcelFileDescriptor2, android.os.ParcelFileDescriptor parcelFileDescriptor3, long j, android.app.backup.IBackupCallback iBackupCallback, int i) throws android.os.RemoteException {
            android.app.backup.BackupDataOutput backupDataOutput = new android.app.backup.BackupDataOutput(parcelFileDescriptor2.getFileDescriptor(), j, i);
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    android.app.backup.BackupAgent.this.onBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor3);
                    android.app.backup.BackupAgent.this.waitForSharedPrefs();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    try {
                        iBackupCallback.operationComplete(0L);
                    } catch (android.os.RemoteException e) {
                    }
                    if (android.os.Binder.getCallingPid() != android.os.Process.myPid()) {
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptor2);
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptor3);
                    }
                } catch (java.io.IOException e2) {
                    android.util.Log.d(TAG, "onBackup (" + android.app.backup.BackupAgent.this.getClass().getName() + ") threw", e2);
                    throw new java.lang.RuntimeException(e2);
                } catch (java.lang.RuntimeException e3) {
                    android.util.Log.d(TAG, "onBackup (" + android.app.backup.BackupAgent.this.getClass().getName() + ") threw", e3);
                    throw e3;
                }
            } finally {
            }
        }

        @Override // android.app.IBackupAgent
        public void doRestore(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, android.os.ParcelFileDescriptor parcelFileDescriptor2, int i, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException {
            doRestoreInternal(parcelFileDescriptor, j, parcelFileDescriptor2, i, iBackupManager, null);
        }

        @Override // android.app.IBackupAgent
        public void doRestoreWithExcludedKeys(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, android.os.ParcelFileDescriptor parcelFileDescriptor2, int i, android.app.backup.IBackupManager iBackupManager, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            doRestoreInternal(parcelFileDescriptor, j, parcelFileDescriptor2, i, iBackupManager, list);
        }

        /* JADX WARN: Removed duplicated region for block: B:44:0x00e8  */
        /* JADX WARN: Removed duplicated region for block: B:46:? A[SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r6v1 */
        /* JADX WARN: Type inference failed for: r6v4 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void doRestoreInternal(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, android.os.ParcelFileDescriptor parcelFileDescriptor2, int i, android.app.backup.IBackupManager iBackupManager, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            java.lang.Throwable th;
            long j2;
            java.lang.String str;
            java.lang.String str2;
            java.util.Set<java.lang.String> hashSet;
            ?? r6 = TAG;
            android.app.backup.BackupAgent.this.waitForSharedPrefs();
            android.app.backup.BackupDataInput backupDataInput = new android.app.backup.BackupDataInput(parcelFileDescriptor.getFileDescriptor());
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    try {
                        try {
                            android.app.backup.BackupAgent backupAgent = android.app.backup.BackupAgent.this;
                            if (list != null) {
                                try {
                                    hashSet = new java.util.HashSet(list);
                                } catch (java.lang.Throwable th2) {
                                    th = th2;
                                    j2 = 0;
                                    android.app.backup.BackupAgent.this.reloadSharedPreferences();
                                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                    try {
                                        iBackupManager.opCompleteForUser(android.app.backup.BackupAgent.this.getBackupUserId(), i, j2);
                                    } catch (android.os.RemoteException e) {
                                    }
                                    if (android.os.Binder.getCallingPid() != android.os.Process.myPid()) {
                                    }
                                }
                            } else {
                                try {
                                    hashSet = java.util.Collections.emptySet();
                                } catch (java.io.IOException e2) {
                                    e = e2;
                                    str2 = TAG;
                                    str = str2;
                                    android.util.Log.d(str, "onRestore (" + android.app.backup.BackupAgent.this.getClass().getName() + ") threw", e);
                                    throw new java.lang.RuntimeException(e);
                                }
                            }
                            str2 = TAG;
                            try {
                                backupAgent.onRestore(backupDataInput, j, parcelFileDescriptor2, hashSet);
                                android.app.backup.BackupAgent.this.reloadSharedPreferences();
                                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                                try {
                                    iBackupManager.opCompleteForUser(android.app.backup.BackupAgent.this.getBackupUserId(), i, 0L);
                                } catch (android.os.RemoteException e3) {
                                }
                                if (android.os.Binder.getCallingPid() != android.os.Process.myPid()) {
                                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                                    libcore.io.IoUtils.closeQuietly(parcelFileDescriptor2);
                                }
                            } catch (java.io.IOException e4) {
                                e = e4;
                                str = str2;
                                android.util.Log.d(str, "onRestore (" + android.app.backup.BackupAgent.this.getClass().getName() + ") threw", e);
                                throw new java.lang.RuntimeException(e);
                            } catch (java.lang.RuntimeException e5) {
                                e = e5;
                                android.util.Log.d(str2, "onRestore (" + android.app.backup.BackupAgent.this.getClass().getName() + ") threw", e);
                                throw e;
                            }
                        } catch (java.io.IOException e6) {
                            e = e6;
                            str = TAG;
                        }
                    } catch (java.lang.Throwable th3) {
                        th = th3;
                        r6 = 0;
                        th = th;
                        j2 = r6;
                        android.app.backup.BackupAgent.this.reloadSharedPreferences();
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        iBackupManager.opCompleteForUser(android.app.backup.BackupAgent.this.getBackupUserId(), i, j2);
                        if (android.os.Binder.getCallingPid() != android.os.Process.myPid()) {
                            throw th;
                        }
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptor2);
                        throw th;
                    }
                } catch (java.lang.RuntimeException e7) {
                    e = e7;
                    str2 = TAG;
                }
            } catch (java.lang.Throwable th4) {
                th = th4;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:42:0x00ed  */
        /* JADX WARN: Removed duplicated region for block: B:44:? A[SYNTHETIC] */
        @Override // android.app.IBackupAgent
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void doFullBackup(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, android.app.backup.IBackupManager iBackupManager, int i2) {
            android.app.backup.BackupAgent.this.waitForSharedPrefs();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                } catch (java.io.IOException e) {
                    e = e;
                } catch (java.lang.RuntimeException e2) {
                    e = e2;
                } catch (java.lang.Throwable th) {
                    th = th;
                    java.lang.Throwable th2 = th;
                    android.app.backup.BackupAgent.this.waitForSharedPrefs();
                    try {
                        new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor()).write(new byte[4]);
                    } catch (java.io.IOException e3) {
                        android.util.Log.e(TAG, "Unable to finalize backup stream!");
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    try {
                        iBackupManager.opCompleteForUser(android.app.backup.BackupAgent.this.getBackupUserId(), i, 0L);
                    } catch (android.os.RemoteException e4) {
                    }
                    if (android.os.Binder.getCallingPid() != android.os.Process.myPid()) {
                    }
                }
                try {
                    android.app.backup.BackupAgent.this.onFullBackup(new android.app.backup.FullBackupDataOutput(parcelFileDescriptor, j, i2));
                    android.app.backup.BackupAgent.this.waitForSharedPrefs();
                    try {
                        new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor()).write(new byte[4]);
                    } catch (java.io.IOException e5) {
                        android.util.Log.e(TAG, "Unable to finalize backup stream!");
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    try {
                        iBackupManager.opCompleteForUser(android.app.backup.BackupAgent.this.getBackupUserId(), i, 0L);
                    } catch (android.os.RemoteException e6) {
                    }
                    if (android.os.Binder.getCallingPid() != android.os.Process.myPid()) {
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                    }
                } catch (java.io.IOException e7) {
                    e = e7;
                    android.util.Log.d(TAG, "onFullBackup (" + android.app.backup.BackupAgent.this.getClass().getName() + ") threw", e);
                    throw new java.lang.RuntimeException(e);
                } catch (java.lang.RuntimeException e8) {
                    e = e8;
                    android.util.Log.d(TAG, "onFullBackup (" + android.app.backup.BackupAgent.this.getClass().getName() + ") threw", e);
                    throw e;
                }
            } catch (java.lang.Throwable th3) {
                th = th3;
                java.lang.Throwable th22 = th;
                android.app.backup.BackupAgent.this.waitForSharedPrefs();
                new java.io.FileOutputStream(parcelFileDescriptor.getFileDescriptor()).write(new byte[4]);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                iBackupManager.opCompleteForUser(android.app.backup.BackupAgent.this.getBackupUserId(), i, 0L);
                if (android.os.Binder.getCallingPid() != android.os.Process.myPid()) {
                    throw th22;
                }
                libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                throw th22;
            }
        }

        @Override // android.app.IBackupAgent
        public void doMeasureFullBackup(long j, int i, android.app.backup.IBackupManager iBackupManager, int i2) {
            android.app.backup.FullBackupDataOutput fullBackupDataOutput = new android.app.backup.FullBackupDataOutput(j, i2);
            android.app.backup.BackupAgent.this.waitForSharedPrefs();
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    android.app.backup.BackupAgent.this.onFullBackup(fullBackupDataOutput);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    try {
                        iBackupManager.opCompleteForUser(android.app.backup.BackupAgent.this.getBackupUserId(), i, fullBackupDataOutput.getSize());
                    } catch (android.os.RemoteException e) {
                    }
                } catch (java.io.IOException e2) {
                    android.util.Log.d(TAG, "onFullBackup[M] (" + android.app.backup.BackupAgent.this.getClass().getName() + ") threw", e2);
                    throw new java.lang.RuntimeException(e2);
                } catch (java.lang.RuntimeException e3) {
                    android.util.Log.d(TAG, "onFullBackup[M] (" + android.app.backup.BackupAgent.this.getClass().getName() + ") threw", e3);
                    throw e3;
                }
            } catch (java.lang.Throwable th) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                try {
                    iBackupManager.opCompleteForUser(android.app.backup.BackupAgent.this.getBackupUserId(), i, fullBackupDataOutput.getSize());
                } catch (android.os.RemoteException e4) {
                }
                throw th;
            }
        }

        @Override // android.app.IBackupAgent
        public void doRestoreFile(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, java.lang.String str, java.lang.String str2, long j2, long j3, int i2, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    android.app.backup.BackupAgent.this.onRestoreFile(parcelFileDescriptor, j, i, str, str2, j2, j3);
                    android.app.backup.BackupAgent.this.waitForSharedPrefs();
                    android.app.backup.BackupAgent.this.reloadSharedPreferences();
                    if (com.android.server.backup.Flags.enableClearPipeAfterRestoreFile()) {
                        clearUnconsumedDataFromPipe(parcelFileDescriptor, j);
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    try {
                        iBackupManager.opCompleteForUser(android.app.backup.BackupAgent.this.getBackupUserId(), i2, 0L);
                    } catch (android.os.RemoteException e) {
                    }
                    if (android.os.Binder.getCallingPid() != android.os.Process.myPid()) {
                        libcore.io.IoUtils.closeQuietly(parcelFileDescriptor);
                    }
                } catch (java.io.IOException e2) {
                    android.util.Log.d(TAG, "onRestoreFile (" + android.app.backup.BackupAgent.this.getClass().getName() + ") threw", e2);
                    throw new java.lang.RuntimeException(e2);
                }
            } finally {
            }
        }

        private static void clearUnconsumedDataFromPipe(android.os.ParcelFileDescriptor parcelFileDescriptor, long j) {
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(parcelFileDescriptor.getFileDescriptor());
                try {
                    if (fileInputStream.available() > 0) {
                        fileInputStream.skip(j);
                    }
                    fileInputStream.close();
                } finally {
                }
            } catch (java.io.IOException e) {
                android.util.Log.w(TAG, "Failed to clear unconsumed data from pipe.", e);
            }
        }

        @Override // android.app.IBackupAgent
        public void doRestoreFinished(int i, android.app.backup.IBackupManager iBackupManager) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    android.app.backup.BackupAgent.this.onRestoreFinished();
                    android.app.backup.BackupAgent.this.waitForSharedPrefs();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    try {
                        iBackupManager.opCompleteForUser(android.app.backup.BackupAgent.this.getBackupUserId(), i, 0L);
                    } catch (android.os.RemoteException e) {
                    }
                } catch (java.lang.Exception e2) {
                    android.util.Log.d(TAG, "onRestoreFinished (" + android.app.backup.BackupAgent.this.getClass().getName() + ") threw", e2);
                    throw e2;
                }
            } catch (java.lang.Throwable th) {
                android.app.backup.BackupAgent.this.waitForSharedPrefs();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                try {
                    iBackupManager.opCompleteForUser(android.app.backup.BackupAgent.this.getBackupUserId(), i, 0L);
                } catch (android.os.RemoteException e3) {
                }
                throw th;
            }
        }

        @Override // android.app.IBackupAgent
        public void fail(java.lang.String str) {
            android.app.backup.BackupAgent.this.getHandler().post(new android.app.backup.BackupAgent.FailRunnable(str));
        }

        @Override // android.app.IBackupAgent
        public void doQuotaExceeded(long j, long j2, android.app.backup.IBackupCallback iBackupCallback) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    android.app.backup.BackupAgent.this.onQuotaExceeded(j, j2);
                    android.app.backup.BackupAgent.this.waitForSharedPrefs();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    try {
                        iBackupCallback.operationComplete(0L);
                    } catch (android.os.RemoteException e) {
                    }
                } catch (java.lang.Throwable th) {
                    android.app.backup.BackupAgent.this.waitForSharedPrefs();
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    try {
                        iBackupCallback.operationComplete(-1L);
                    } catch (android.os.RemoteException e2) {
                    }
                    throw th;
                }
            } catch (java.lang.Exception e3) {
                android.util.Log.d(TAG, "onQuotaExceeded(" + android.app.backup.BackupAgent.this.getClass().getName() + ") threw", e3);
                throw e3;
            }
        }

        @Override // android.app.IBackupAgent
        public void getLoggerResults(com.android.internal.infra.AndroidFuture<java.util.List<android.app.backup.BackupRestoreEventLogger.DataTypeResult>> androidFuture) {
            if (android.app.backup.BackupAgent.this.mLogger != null) {
                androidFuture.complete(android.app.backup.BackupAgent.this.mLogger.getLoggingResults());
            } else {
                androidFuture.complete(java.util.Collections.emptyList());
            }
        }

        @Override // android.app.IBackupAgent
        public void getOperationType(com.android.internal.infra.AndroidFuture<java.lang.Integer> androidFuture) {
            androidFuture.complete(java.lang.Integer.valueOf(android.app.backup.BackupAgent.this.mLogger == null ? -1 : android.app.backup.BackupAgent.this.mLogger.getOperationType()));
        }

        @Override // android.app.IBackupAgent
        public void clearBackupRestoreEventLogger() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    android.app.backup.BackupAgent.this.clearBackupRestoreEventLogger();
                } catch (java.lang.Exception e) {
                    android.util.Log.d(TAG, "clearBackupRestoreEventLogger (" + android.app.backup.BackupAgent.this.getClass().getName() + ") threw", e);
                    throw e;
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    static class FailRunnable implements java.lang.Runnable {
        private java.lang.String mMessage;

        FailRunnable(java.lang.String str) {
            this.mMessage = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            throw new java.lang.IllegalStateException(this.mMessage);
        }
    }

    public static class IncludeExcludeRules {
        private final java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> mManifestExcludeSet;
        private final java.util.Map<java.lang.String, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> mManifestIncludeMap;

        public IncludeExcludeRules(java.util.Map<java.lang.String, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> map, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> set) {
            this.mManifestIncludeMap = map;
            this.mManifestExcludeSet = set;
        }

        public static android.app.backup.BackupAgent.IncludeExcludeRules emptyRules() {
            return new android.app.backup.BackupAgent.IncludeExcludeRules(java.util.Collections.emptyMap(), new android.util.ArraySet());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.util.Map<java.lang.String, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> getIncludeMap() {
            return this.mManifestIncludeMap;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> getExcludeSet() {
            return this.mManifestExcludeSet;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mManifestIncludeMap, this.mManifestExcludeSet);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.app.backup.BackupAgent.IncludeExcludeRules includeExcludeRules = (android.app.backup.BackupAgent.IncludeExcludeRules) obj;
            if (java.util.Objects.equals(this.mManifestIncludeMap, includeExcludeRules.mManifestIncludeMap) && java.util.Objects.equals(this.mManifestExcludeSet, includeExcludeRules.mManifestExcludeSet)) {
                return true;
            }
            return false;
        }
    }
}
