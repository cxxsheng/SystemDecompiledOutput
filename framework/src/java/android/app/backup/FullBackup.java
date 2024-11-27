package android.app.backup;

/* loaded from: classes.dex */
public class FullBackup {
    public static final java.lang.String APK_TREE_TOKEN = "a";
    public static final java.lang.String APPS_PREFIX = "apps/";
    public static final java.lang.String CACHE_TREE_TOKEN = "c";
    public static final java.lang.String CONF_TOKEN_INTENT_EXTRA = "conftoken";
    public static final java.lang.String DATABASE_TREE_TOKEN = "db";
    public static final java.lang.String DEVICE_CACHE_TREE_TOKEN = "d_c";
    public static final java.lang.String DEVICE_DATABASE_TREE_TOKEN = "d_db";
    public static final java.lang.String DEVICE_FILES_TREE_TOKEN = "d_f";
    public static final java.lang.String DEVICE_NO_BACKUP_TREE_TOKEN = "d_nb";
    public static final java.lang.String DEVICE_ROOT_TREE_TOKEN = "d_r";
    public static final java.lang.String DEVICE_SHAREDPREFS_TREE_TOKEN = "d_sp";
    public static final java.lang.String FILES_TREE_TOKEN = "f";
    private static final java.lang.String FLAG_DISABLE_IF_NO_ENCRYPTION_CAPABILITIES = "disableIfNoEncryptionCapabilities";
    public static final java.lang.String FLAG_REQUIRED_CLIENT_SIDE_ENCRYPTION = "clientSideEncryption";
    public static final java.lang.String FLAG_REQUIRED_DEVICE_TO_DEVICE_TRANSFER = "deviceToDeviceTransfer";
    public static final java.lang.String FLAG_REQUIRED_FAKE_CLIENT_SIDE_ENCRYPTION = "fakeClientSideEncryption";
    public static final java.lang.String FULL_BACKUP_INTENT_ACTION = "fullback";
    public static final java.lang.String FULL_RESTORE_INTENT_ACTION = "fullrest";
    private static final long IGNORE_FULL_BACKUP_CONTENT_IN_D2D = 180523564;
    public static final java.lang.String KEY_VALUE_DATA_TOKEN = "k";
    public static final java.lang.String MANAGED_EXTERNAL_TREE_TOKEN = "ef";
    public static final java.lang.String NO_BACKUP_TREE_TOKEN = "nb";
    public static final java.lang.String OBB_TREE_TOKEN = "obb";
    public static final java.lang.String ROOT_TREE_TOKEN = "r";
    public static final java.lang.String SHAREDPREFS_TREE_TOKEN = "sp";
    public static final java.lang.String SHARED_PREFIX = "shared/";
    public static final java.lang.String SHARED_STORAGE_TOKEN = "shared";
    static final java.lang.String TAG = "FullBackup";
    static final java.lang.String TAG_XML_PARSER = "BackupXmlParserLogging";
    private static final java.util.Map<android.app.backup.FullBackup.BackupSchemeId, android.app.backup.FullBackup.BackupScheme> kPackageBackupSchemeMap = new android.util.ArrayMap();

    @interface ConfigSection {
        public static final java.lang.String CLOUD_BACKUP = "cloud-backup";
        public static final java.lang.String DEVICE_TRANSFER = "device-transfer";
    }

    public static native int backupToTar(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, android.app.backup.FullBackupDataOutput fullBackupDataOutput);

    private static class BackupSchemeId {
        final int mBackupDestination;
        final java.lang.String mPackageName;

        BackupSchemeId(java.lang.String str, int i) {
            this.mPackageName = str;
            this.mBackupDestination = i;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mPackageName, java.lang.Integer.valueOf(this.mBackupDestination));
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.app.backup.FullBackup.BackupSchemeId backupSchemeId = (android.app.backup.FullBackup.BackupSchemeId) obj;
            if (java.util.Objects.equals(this.mPackageName, backupSchemeId.mPackageName) && java.util.Objects.equals(java.lang.Integer.valueOf(this.mBackupDestination), java.lang.Integer.valueOf(backupSchemeId.mBackupDestination))) {
                return true;
            }
            return false;
        }
    }

    static synchronized android.app.backup.FullBackup.BackupScheme getBackupScheme(android.content.Context context, int i) {
        android.app.backup.FullBackup.BackupScheme backupScheme;
        synchronized (android.app.backup.FullBackup.class) {
            android.app.backup.FullBackup.BackupSchemeId backupSchemeId = new android.app.backup.FullBackup.BackupSchemeId(context.getPackageName(), i);
            backupScheme = kPackageBackupSchemeMap.get(backupSchemeId);
            if (backupScheme == null) {
                backupScheme = new android.app.backup.FullBackup.BackupScheme(context, i);
                kPackageBackupSchemeMap.put(backupSchemeId, backupScheme);
            }
        }
        return backupScheme;
    }

    public static android.app.backup.FullBackup.BackupScheme getBackupSchemeForTest(android.content.Context context) {
        android.app.backup.FullBackup.BackupScheme backupScheme = new android.app.backup.FullBackup.BackupScheme(context, 0);
        backupScheme.mExcludes = new android.util.ArraySet<>();
        backupScheme.mIncludes = new android.util.ArrayMap();
        return backupScheme;
    }

    public static void restoreFile(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, long j2, long j3, java.io.File file) throws java.io.IOException {
        java.io.FileOutputStream fileOutputStream;
        if (i != 2) {
            if (file == null) {
                fileOutputStream = null;
            } else {
                try {
                    java.io.File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    fileOutputStream = new java.io.FileOutputStream(file);
                } catch (java.io.IOException e) {
                    android.util.Log.e(TAG, "Unable to create/open file " + file.getPath(), e);
                    fileOutputStream = null;
                }
            }
            byte[] bArr = new byte[65536];
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(parcelFileDescriptor.getFileDescriptor());
            long j4 = j;
            java.io.FileOutputStream fileOutputStream2 = fileOutputStream;
            while (true) {
                if (j4 <= 0) {
                    break;
                }
                int read = fileInputStream.read(bArr, 0, j4 > ((long) 65536) ? 65536 : (int) j4);
                if (read <= 0) {
                    android.util.Log.w(TAG, "Incomplete read: expected " + j4 + " but got " + (j - j4));
                    break;
                }
                if (fileOutputStream2 != null) {
                    try {
                        fileOutputStream2.write(bArr, 0, read);
                    } catch (java.io.IOException e2) {
                        android.util.Log.e(TAG, "Unable to write to file " + file.getPath(), e2);
                        fileOutputStream2.close();
                        file.delete();
                        fileOutputStream2 = null;
                    }
                }
                j4 -= read;
            }
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
        } else if (file != null) {
            file.mkdirs();
        }
        if (j2 >= 0 && file != null) {
            try {
                android.system.Os.chmod(file.getPath(), (int) (j2 & 448));
            } catch (android.system.ErrnoException e3) {
                e3.rethrowAsIOException();
            }
            file.setLastModified(j3);
        }
    }

    public static class BackupScheme {
        private static final java.lang.String TAG_EXCLUDE = "exclude";
        private static final java.lang.String TAG_INCLUDE = "include";
        private final java.io.File CACHE_DIR;
        private final java.io.File DATABASE_DIR;
        private final java.io.File DEVICE_CACHE_DIR;
        private final java.io.File DEVICE_DATABASE_DIR;
        private final java.io.File DEVICE_FILES_DIR;
        private final java.io.File DEVICE_NOBACKUP_DIR;
        private final java.io.File DEVICE_ROOT_DIR;
        private final java.io.File DEVICE_SHAREDPREF_DIR;
        private final java.io.File EXTERNAL_DIR;
        private final java.io.File FILES_DIR;
        private final java.io.File NOBACKUP_DIR;
        private final java.io.File ROOT_DIR;
        private final java.io.File SHAREDPREF_DIR;
        final int mBackupDestination;
        final int mDataExtractionRules;
        android.util.ArraySet<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> mExcludes;
        final int mFullBackupContent;
        java.util.Map<java.lang.String, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> mIncludes;
        private java.lang.Boolean mIsUsingNewScheme;
        final android.content.pm.PackageManager mPackageManager;
        final java.lang.String mPackageName;
        private java.lang.Integer mRequiredTransportFlags;
        final android.os.storage.StorageManager mStorageManager;
        private android.os.storage.StorageVolume[] mVolumes = null;

        java.lang.String tokenToDirectoryPath(java.lang.String str) {
            try {
                if (str.equals(android.app.backup.FullBackup.FILES_TREE_TOKEN)) {
                    return this.FILES_DIR.getCanonicalPath();
                }
                if (str.equals(android.app.backup.FullBackup.DATABASE_TREE_TOKEN)) {
                    return this.DATABASE_DIR.getCanonicalPath();
                }
                if (str.equals("r")) {
                    return this.ROOT_DIR.getCanonicalPath();
                }
                if (str.equals(android.app.backup.FullBackup.SHAREDPREFS_TREE_TOKEN)) {
                    return this.SHAREDPREF_DIR.getCanonicalPath();
                }
                if (str.equals("c")) {
                    return this.CACHE_DIR.getCanonicalPath();
                }
                if (str.equals(android.app.backup.FullBackup.NO_BACKUP_TREE_TOKEN)) {
                    return this.NOBACKUP_DIR.getCanonicalPath();
                }
                if (str.equals(android.app.backup.FullBackup.DEVICE_FILES_TREE_TOKEN)) {
                    return this.DEVICE_FILES_DIR.getCanonicalPath();
                }
                if (str.equals(android.app.backup.FullBackup.DEVICE_DATABASE_TREE_TOKEN)) {
                    return this.DEVICE_DATABASE_DIR.getCanonicalPath();
                }
                if (str.equals(android.app.backup.FullBackup.DEVICE_ROOT_TREE_TOKEN)) {
                    return this.DEVICE_ROOT_DIR.getCanonicalPath();
                }
                if (str.equals(android.app.backup.FullBackup.DEVICE_SHAREDPREFS_TREE_TOKEN)) {
                    return this.DEVICE_SHAREDPREF_DIR.getCanonicalPath();
                }
                if (str.equals(android.app.backup.FullBackup.DEVICE_CACHE_TREE_TOKEN)) {
                    return this.DEVICE_CACHE_DIR.getCanonicalPath();
                }
                if (str.equals(android.app.backup.FullBackup.DEVICE_NO_BACKUP_TREE_TOKEN)) {
                    return this.DEVICE_NOBACKUP_DIR.getCanonicalPath();
                }
                if (str.equals(android.app.backup.FullBackup.MANAGED_EXTERNAL_TREE_TOKEN)) {
                    if (this.EXTERNAL_DIR != null) {
                        return this.EXTERNAL_DIR.getCanonicalPath();
                    }
                    return null;
                }
                if (!str.startsWith(android.app.backup.FullBackup.SHARED_PREFIX)) {
                    android.util.Log.i(android.app.backup.FullBackup.TAG, "Unrecognized domain " + str);
                    return null;
                }
                return sharedDomainToPath(str);
            } catch (java.lang.Exception e) {
                android.util.Log.i(android.app.backup.FullBackup.TAG, "Error reading directory for domain: " + str);
                return null;
            }
        }

        private java.lang.String sharedDomainToPath(java.lang.String str) throws java.io.IOException {
            java.lang.String substring = str.substring(android.app.backup.FullBackup.SHARED_PREFIX.length());
            android.os.storage.StorageVolume[] volumeList = getVolumeList();
            int parseInt = java.lang.Integer.parseInt(substring);
            if (parseInt < this.mVolumes.length) {
                return volumeList[parseInt].getPathFile().getCanonicalPath();
            }
            return null;
        }

        private android.os.storage.StorageVolume[] getVolumeList() {
            if (this.mStorageManager != null) {
                if (this.mVolumes == null) {
                    this.mVolumes = this.mStorageManager.getVolumeList();
                }
            } else {
                android.util.Log.e(android.app.backup.FullBackup.TAG, "Unable to access Storage Manager");
            }
            return this.mVolumes;
        }

        public static class PathWithRequiredFlags {
            private final java.lang.String mPath;
            private final int mRequiredFlags;

            public PathWithRequiredFlags(java.lang.String str, int i) {
                this.mPath = str;
                this.mRequiredFlags = i;
            }

            public java.lang.String getPath() {
                return this.mPath;
            }

            public int getRequiredFlags() {
                return this.mRequiredFlags;
            }
        }

        BackupScheme(android.content.Context context, int i) {
            android.content.pm.ApplicationInfo applicationInfo = context.getApplicationInfo();
            this.mDataExtractionRules = applicationInfo.dataExtractionRulesRes;
            this.mFullBackupContent = applicationInfo.fullBackupContent;
            this.mBackupDestination = i;
            this.mStorageManager = (android.os.storage.StorageManager) context.getSystemService(android.content.Context.STORAGE_SERVICE);
            this.mPackageManager = context.getPackageManager();
            this.mPackageName = context.getPackageName();
            android.content.Context createCredentialProtectedStorageContext = context.createCredentialProtectedStorageContext();
            this.FILES_DIR = createCredentialProtectedStorageContext.getFilesDir();
            this.DATABASE_DIR = createCredentialProtectedStorageContext.getDatabasePath("foo").getParentFile();
            this.ROOT_DIR = createCredentialProtectedStorageContext.getDataDir();
            this.SHAREDPREF_DIR = createCredentialProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile();
            this.CACHE_DIR = createCredentialProtectedStorageContext.getCacheDir();
            this.NOBACKUP_DIR = createCredentialProtectedStorageContext.getNoBackupFilesDir();
            android.content.Context createDeviceProtectedStorageContext = context.createDeviceProtectedStorageContext();
            this.DEVICE_FILES_DIR = createDeviceProtectedStorageContext.getFilesDir();
            this.DEVICE_DATABASE_DIR = createDeviceProtectedStorageContext.getDatabasePath("foo").getParentFile();
            this.DEVICE_ROOT_DIR = createDeviceProtectedStorageContext.getDataDir();
            this.DEVICE_SHAREDPREF_DIR = createDeviceProtectedStorageContext.getSharedPreferencesPath("foo").getParentFile();
            this.DEVICE_CACHE_DIR = createDeviceProtectedStorageContext.getCacheDir();
            this.DEVICE_NOBACKUP_DIR = createDeviceProtectedStorageContext.getNoBackupFilesDir();
            if (android.os.Process.myUid() != 1000) {
                this.EXTERNAL_DIR = context.getExternalFilesDir(null);
            } else {
                this.EXTERNAL_DIR = null;
            }
        }

        boolean isFullBackupEnabled(int i) {
            try {
                if (isUsingNewScheme()) {
                    int requiredTransportFlags = getRequiredTransportFlags();
                    return (i & requiredTransportFlags) == requiredTransportFlags;
                }
                return isFullBackupContentEnabled();
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.w(android.app.backup.FullBackup.TAG, "Failed to interpret the backup scheme: " + e);
                return false;
            }
        }

        boolean isFullRestoreEnabled() {
            try {
                if (isUsingNewScheme()) {
                    return true;
                }
                return isFullBackupContentEnabled();
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Slog.w(android.app.backup.FullBackup.TAG, "Failed to interpret the backup scheme: " + e);
                return false;
            }
        }

        boolean isFullBackupContentEnabled() {
            if (this.mFullBackupContent < 0) {
                if (android.util.Log.isLoggable(android.app.backup.FullBackup.TAG_XML_PARSER, 2)) {
                    android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "android:fullBackupContent - \"false\"");
                    return false;
                }
                return false;
            }
            return true;
        }

        public synchronized java.util.Map<java.lang.String, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> maybeParseAndGetCanonicalIncludePaths() throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            if (this.mIncludes == null) {
                maybeParseBackupSchemeLocked();
            }
            return this.mIncludes;
        }

        public synchronized android.util.ArraySet<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> maybeParseAndGetCanonicalExcludePaths() throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            if (this.mExcludes == null) {
                maybeParseBackupSchemeLocked();
            }
            return this.mExcludes;
        }

        public synchronized int getRequiredTransportFlags() throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            if (this.mRequiredTransportFlags == null) {
                maybeParseBackupSchemeLocked();
            }
            return this.mRequiredTransportFlags.intValue();
        }

        private synchronized boolean isUsingNewScheme() throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            if (this.mIsUsingNewScheme == null) {
                maybeParseBackupSchemeLocked();
            }
            return this.mIsUsingNewScheme.booleanValue();
        }

        private void maybeParseBackupSchemeLocked() throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            this.mIncludes = new android.util.ArrayMap();
            this.mExcludes = new android.util.ArraySet<>();
            this.mRequiredTransportFlags = 0;
            this.mIsUsingNewScheme = false;
            if (this.mFullBackupContent == 0 && this.mDataExtractionRules == 0) {
                if (android.util.Log.isLoggable(android.app.backup.FullBackup.TAG_XML_PARSER, 2)) {
                    android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "android:fullBackupContent - \"true\"");
                }
            } else {
                if (android.util.Log.isLoggable(android.app.backup.FullBackup.TAG_XML_PARSER, 2)) {
                    android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "Found xml scheme: android:fullBackupContent=" + this.mFullBackupContent + "; android:dataExtractionRules=" + this.mDataExtractionRules);
                }
                try {
                    parseSchemeForBackupDestination(this.mBackupDestination);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    throw new java.io.IOException(e);
                }
            }
        }

        private void parseSchemeForBackupDestination(int i) throws android.content.pm.PackageManager.NameNotFoundException, java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            android.content.res.XmlResourceParser parserForResource;
            java.lang.String configSectionForBackupDestination = getConfigSectionForBackupDestination(i);
            if (configSectionForBackupDestination == null) {
                android.util.Slog.w(android.app.backup.FullBackup.TAG, "Given backup destination isn't supported by backup scheme: " + i);
                return;
            }
            if (this.mDataExtractionRules != 0) {
                parserForResource = getParserForResource(this.mDataExtractionRules);
                try {
                    boolean parseNewBackupSchemeFromXmlLocked = parseNewBackupSchemeFromXmlLocked(parserForResource, configSectionForBackupDestination, this.mExcludes, this.mIncludes);
                    if (parserForResource != null) {
                        parserForResource.close();
                    }
                    if (parseNewBackupSchemeFromXmlLocked) {
                        this.mIsUsingNewScheme = true;
                        return;
                    }
                } finally {
                }
            }
            if (i == 1 && android.app.compat.CompatChanges.isChangeEnabled(android.app.backup.FullBackup.IGNORE_FULL_BACKUP_CONTENT_IN_D2D)) {
                this.mIsUsingNewScheme = true;
                return;
            }
            if (this.mFullBackupContent != 0) {
                parserForResource = getParserForResource(this.mFullBackupContent);
                try {
                    parseBackupSchemeFromXmlLocked(parserForResource, this.mExcludes, this.mIncludes);
                    if (parserForResource != null) {
                        parserForResource.close();
                    }
                } finally {
                }
            }
        }

        private java.lang.String getConfigSectionForBackupDestination(int i) {
            switch (i) {
                case 0:
                    return android.app.backup.FullBackup.ConfigSection.CLOUD_BACKUP;
                case 1:
                    return android.app.backup.FullBackup.ConfigSection.DEVICE_TRANSFER;
                default:
                    return null;
            }
        }

        private android.content.res.XmlResourceParser getParserForResource(int i) throws android.content.pm.PackageManager.NameNotFoundException {
            return this.mPackageManager.getResourcesForApplication(this.mPackageName).getXml(i);
        }

        public boolean parseNewBackupSchemeFromXmlLocked(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> set, java.util.Map<java.lang.String, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> map) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            verifyTopLevelTag(xmlPullParser, "data-extraction-rules");
            boolean z = false;
            while (true) {
                int next = xmlPullParser.next();
                if (next != 1) {
                    if (next == 2 && str.equals(xmlPullParser.getName())) {
                        parseRequiredTransportFlags(xmlPullParser, str);
                        parseRules(xmlPullParser, set, map, java.util.Optional.of(0), str);
                        z = true;
                    }
                } else {
                    logParsingResults(set, map);
                    return z;
                }
            }
        }

        private void parseRequiredTransportFlags(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str) {
            if (android.app.backup.FullBackup.ConfigSection.CLOUD_BACKUP.equals(str) && "true".equals(xmlPullParser.getAttributeValue(null, android.app.backup.FullBackup.FLAG_DISABLE_IF_NO_ENCRYPTION_CAPABILITIES))) {
                this.mRequiredTransportFlags = 1;
            }
        }

        public void parseBackupSchemeFromXmlLocked(org.xmlpull.v1.XmlPullParser xmlPullParser, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> set, java.util.Map<java.lang.String, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> map) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            verifyTopLevelTag(xmlPullParser, "full-backup-content");
            parseRules(xmlPullParser, set, map, java.util.Optional.empty(), "full-backup-content");
            logParsingResults(set, map);
        }

        private void verifyTopLevelTag(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            int eventType = xmlPullParser.getEventType();
            while (eventType != 2) {
                eventType = xmlPullParser.next();
            }
            if (str.equals(xmlPullParser.getName())) {
                if (android.util.Log.isLoggable(android.app.backup.FullBackup.TAG_XML_PARSER, 2)) {
                    android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "\n");
                    android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "====================================================");
                    android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "Found valid " + str + "; parsing xml resource.");
                    android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "====================================================");
                    android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "");
                    return;
                }
                return;
            }
            throw new org.xmlpull.v1.XmlPullParserException("Xml file didn't start with correct tag (" + str + " ). Found \"" + xmlPullParser.getName() + "\"");
        }

        private void parseRules(org.xmlpull.v1.XmlPullParser xmlPullParser, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> set, java.util.Map<java.lang.String, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> map, java.util.Optional<java.lang.Integer> optional, java.lang.String str) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            while (true) {
                int next = xmlPullParser.next();
                if (next != 1 && !xmlPullParser.getName().equals(str)) {
                    switch (next) {
                        case 2:
                            validateInnerTagContents(xmlPullParser);
                            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "domain");
                            java.io.File directoryForCriteriaDomain = getDirectoryForCriteriaDomain(attributeValue);
                            if (directoryForCriteriaDomain == null) {
                                if (!android.util.Log.isLoggable(android.app.backup.FullBackup.TAG_XML_PARSER, 2)) {
                                    break;
                                } else {
                                    android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "...parsing \"" + xmlPullParser.getName() + "\": domain=\"" + attributeValue + "\" invalid; skipping");
                                    break;
                                }
                            } else {
                                java.io.File extractCanonicalFile = extractCanonicalFile(directoryForCriteriaDomain, xmlPullParser.getAttributeValue(null, "path"));
                                if (extractCanonicalFile != null) {
                                    int requiredFlagsForRule = getRequiredFlagsForRule(xmlPullParser, optional);
                                    java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> parseCurrentTagForDomain = parseCurrentTagForDomain(xmlPullParser, set, map, attributeValue);
                                    parseCurrentTagForDomain.add(new android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags(extractCanonicalFile.getCanonicalPath(), requiredFlagsForRule));
                                    if (android.util.Log.isLoggable(android.app.backup.FullBackup.TAG_XML_PARSER, 2)) {
                                        android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "...parsed " + extractCanonicalFile.getCanonicalPath() + " for domain \"" + attributeValue + "\", requiredFlags + \"" + requiredFlagsForRule + "\"");
                                    }
                                    if ("database".equals(attributeValue) && !extractCanonicalFile.isDirectory()) {
                                        java.lang.String str2 = extractCanonicalFile.getCanonicalPath() + "-journal";
                                        parseCurrentTagForDomain.add(new android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags(str2, requiredFlagsForRule));
                                        if (android.util.Log.isLoggable(android.app.backup.FullBackup.TAG_XML_PARSER, 2)) {
                                            android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "...automatically generated " + str2 + ". Ignore if nonexistent.");
                                        }
                                        java.lang.String str3 = extractCanonicalFile.getCanonicalPath() + "-wal";
                                        parseCurrentTagForDomain.add(new android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags(str3, requiredFlagsForRule));
                                        if (android.util.Log.isLoggable(android.app.backup.FullBackup.TAG_XML_PARSER, 2)) {
                                            android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "...automatically generated " + str3 + ". Ignore if nonexistent.");
                                        }
                                    }
                                    if ("sharedpref".equals(attributeValue) && !extractCanonicalFile.isDirectory() && !extractCanonicalFile.getCanonicalPath().endsWith(".xml")) {
                                        java.lang.String str4 = extractCanonicalFile.getCanonicalPath() + ".xml";
                                        parseCurrentTagForDomain.add(new android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags(str4, requiredFlagsForRule));
                                        if (!android.util.Log.isLoggable(android.app.backup.FullBackup.TAG_XML_PARSER, 2)) {
                                            break;
                                        } else {
                                            android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "...automatically generated " + str4 + ". Ignore if nonexistent.");
                                            break;
                                        }
                                    }
                                } else {
                                    break;
                                }
                            }
                            break;
                    }
                } else {
                    return;
                }
            }
        }

        private void logParsingResults(java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> set, java.util.Map<java.lang.String, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> map) {
            if (android.util.Log.isLoggable(android.app.backup.FullBackup.TAG_XML_PARSER, 2)) {
                android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "\n");
                android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "Xml resource parsing complete.");
                android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "Final tally.");
                android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "Includes:");
                if (map.isEmpty()) {
                    android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "  ...nothing specified (This means the entirety of app data minus excludes)");
                } else {
                    for (java.util.Map.Entry<java.lang.String, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> entry : map.entrySet()) {
                        android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "  domain=" + entry.getKey());
                        for (android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags pathWithRequiredFlags : entry.getValue()) {
                            android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, " path: " + pathWithRequiredFlags.getPath() + " requiredFlags: " + pathWithRequiredFlags.getRequiredFlags());
                        }
                    }
                }
                android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "Excludes:");
                if (set.isEmpty()) {
                    android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "  ...nothing to exclude.");
                } else {
                    for (android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags pathWithRequiredFlags2 : set) {
                        android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, " path: " + pathWithRequiredFlags2.getPath() + " requiredFlags: " + pathWithRequiredFlags2.getRequiredFlags());
                    }
                }
                android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "  ");
                android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "====================================================");
                android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "\n");
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private int getRequiredFlagsFromString(java.lang.String str) {
            char c;
            if (str == null || str.length() == 0) {
                return 0;
            }
            int i = 0;
            for (java.lang.String str2 : str.split("\\|")) {
                switch (str2.hashCode()) {
                    case 482744282:
                        if (str2.equals(android.app.backup.FullBackup.FLAG_REQUIRED_FAKE_CLIENT_SIDE_ENCRYPTION)) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1499007205:
                        if (str2.equals(android.app.backup.FullBackup.FLAG_REQUIRED_CLIENT_SIDE_ENCRYPTION)) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1935925810:
                        if (str2.equals(android.app.backup.FullBackup.FLAG_REQUIRED_DEVICE_TO_DEVICE_TRANSFER)) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        i |= 1;
                        continue;
                    case 1:
                        i |= 2;
                        continue;
                    case 2:
                        i |= Integer.MIN_VALUE;
                        break;
                }
                android.util.Log.w(android.app.backup.FullBackup.TAG, "Unrecognized requiredFlag provided, value: \"" + str2 + "\"");
            }
            return i;
        }

        private int getRequiredFlagsForRule(org.xmlpull.v1.XmlPullParser xmlPullParser, java.util.Optional<java.lang.Integer> optional) {
            if (optional.isPresent()) {
                return optional.get().intValue();
            }
            if (TAG_INCLUDE.equals(xmlPullParser.getName())) {
                return getRequiredFlagsFromString(xmlPullParser.getAttributeValue(null, "requireFlags"));
            }
            return 0;
        }

        private java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> parseCurrentTagForDomain(org.xmlpull.v1.XmlPullParser xmlPullParser, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> set, java.util.Map<java.lang.String, java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags>> map, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException {
            if (TAG_INCLUDE.equals(xmlPullParser.getName())) {
                java.lang.String tokenForXmlDomain = getTokenForXmlDomain(str);
                java.util.Set<android.app.backup.FullBackup.BackupScheme.PathWithRequiredFlags> set2 = map.get(tokenForXmlDomain);
                if (set2 == null) {
                    android.util.ArraySet arraySet = new android.util.ArraySet();
                    map.put(tokenForXmlDomain, arraySet);
                    return arraySet;
                }
                return set2;
            }
            if (TAG_EXCLUDE.equals(xmlPullParser.getName())) {
                return set;
            }
            if (android.util.Log.isLoggable(android.app.backup.FullBackup.TAG_XML_PARSER, 2)) {
                android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "Invalid tag found in xml \"" + xmlPullParser.getName() + "\"; aborting operation.");
            }
            throw new org.xmlpull.v1.XmlPullParserException("Unrecognised tag in backup criteria xml (" + xmlPullParser.getName() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }

        private java.lang.String getTokenForXmlDomain(java.lang.String str) {
            if ("root".equals(str)) {
                return "r";
            }
            if ("file".equals(str)) {
                return android.app.backup.FullBackup.FILES_TREE_TOKEN;
            }
            if ("database".equals(str)) {
                return android.app.backup.FullBackup.DATABASE_TREE_TOKEN;
            }
            if ("sharedpref".equals(str)) {
                return android.app.backup.FullBackup.SHAREDPREFS_TREE_TOKEN;
            }
            if ("device_root".equals(str)) {
                return android.app.backup.FullBackup.DEVICE_ROOT_TREE_TOKEN;
            }
            if ("device_file".equals(str)) {
                return android.app.backup.FullBackup.DEVICE_FILES_TREE_TOKEN;
            }
            if ("device_database".equals(str)) {
                return android.app.backup.FullBackup.DEVICE_DATABASE_TREE_TOKEN;
            }
            if ("device_sharedpref".equals(str)) {
                return android.app.backup.FullBackup.DEVICE_SHAREDPREFS_TREE_TOKEN;
            }
            if ("external".equals(str)) {
                return android.app.backup.FullBackup.MANAGED_EXTERNAL_TREE_TOKEN;
            }
            return null;
        }

        private java.io.File extractCanonicalFile(java.io.File file, java.lang.String str) {
            if (str == null) {
                str = "";
            }
            if (str.contains("..")) {
                if (android.util.Log.isLoggable(android.app.backup.FullBackup.TAG_XML_PARSER, 2)) {
                    android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "...resolved \"" + file.getPath() + " " + str + "\", but the \"..\" path is not permitted; skipping.");
                }
                return null;
            }
            if (str.contains("//")) {
                if (android.util.Log.isLoggable(android.app.backup.FullBackup.TAG_XML_PARSER, 2)) {
                    android.util.Log.v(android.app.backup.FullBackup.TAG_XML_PARSER, "...resolved \"" + file.getPath() + " " + str + "\", which contains the invalid \"//\" sequence; skipping.");
                }
                return null;
            }
            return new java.io.File(file, str);
        }

        private java.io.File getDirectoryForCriteriaDomain(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                return null;
            }
            if ("file".equals(str)) {
                return this.FILES_DIR;
            }
            if ("database".equals(str)) {
                return this.DATABASE_DIR;
            }
            if ("root".equals(str)) {
                return this.ROOT_DIR;
            }
            if ("sharedpref".equals(str)) {
                return this.SHAREDPREF_DIR;
            }
            if ("device_file".equals(str)) {
                return this.DEVICE_FILES_DIR;
            }
            if ("device_database".equals(str)) {
                return this.DEVICE_DATABASE_DIR;
            }
            if ("device_root".equals(str)) {
                return this.DEVICE_ROOT_DIR;
            }
            if ("device_sharedpref".equals(str)) {
                return this.DEVICE_SHAREDPREF_DIR;
            }
            if ("external".equals(str)) {
                return this.EXTERNAL_DIR;
            }
            return null;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private void validateInnerTagContents(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException {
            char c;
            if (xmlPullParser == null) {
                return;
            }
            java.lang.String name = xmlPullParser.getName();
            switch (name.hashCode()) {
                case -1321148966:
                    if (name.equals(TAG_EXCLUDE)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1942574248:
                    if (name.equals(TAG_INCLUDE)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    if (xmlPullParser.getAttributeCount() > 3) {
                        throw new org.xmlpull.v1.XmlPullParserException("At most 3 tag attributes allowed for \"include\" tag (\"domain\" & \"path\" & optional \"requiredFlags\").");
                    }
                    return;
                case 1:
                    if (xmlPullParser.getAttributeCount() > 2) {
                        throw new org.xmlpull.v1.XmlPullParserException("At most 2 tag attributes allowed for \"exclude\" tag (\"domain\" & \"path\".");
                    }
                    return;
                default:
                    throw new org.xmlpull.v1.XmlPullParserException("A valid tag is one of \"<include/>\" or \"<exclude/>. You provided \"" + xmlPullParser.getName() + "\"");
            }
        }
    }
}
