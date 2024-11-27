package com.android.server;

/* loaded from: classes.dex */
public class SystemConfig {
    private static final int ALLOW_ALL = -1;
    private static final int ALLOW_APP_CONFIGS = 8;
    private static final int ALLOW_ASSOCIATIONS = 128;
    private static final int ALLOW_FEATURES = 1;
    private static final int ALLOW_HIDDENAPI_WHITELISTING = 64;
    private static final int ALLOW_IMPLICIT_BROADCASTS = 512;
    private static final int ALLOW_LIBS = 2;
    private static final int ALLOW_OEM_PERMISSIONS = 32;
    private static final int ALLOW_OVERRIDE_APP_RESTRICTIONS = 256;
    private static final int ALLOW_PERMISSIONS = 4;
    private static final int ALLOW_PRIVAPP_PERMISSIONS = 16;
    private static final int ALLOW_SIGNATURE_PERMISSIONS = 2048;
    private static final int ALLOW_VENDOR_APEX = 1024;
    private static final android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> EMPTY_PERMISSIONS = new android.util.ArrayMap<>();
    private static final java.lang.String SKU_PROPERTY = "ro.boot.product.hardware.sku";
    static final java.lang.String TAG = "SystemConfig";
    private static final java.lang.String VENDOR_SKU_PROPERTY = "ro.boot.product.vendor.sku";
    static com.android.server.SystemConfig sInstance;
    private java.lang.String mModulesInstallerPackageName;
    private java.lang.String mOverlayConfigSignaturePackage;
    int[] mGlobalGids = libcore.util.EmptyArray.INT;
    final android.util.SparseArray<android.util.ArraySet<java.lang.String>> mSystemPermissions = new android.util.SparseArray<>();
    final java.util.ArrayList<android.permission.PermissionManager.SplitPermissionInfo> mSplitPermissions = new java.util.ArrayList<>();
    final android.util.ArrayMap<java.lang.String, com.android.server.SystemConfig.SharedLibraryEntry> mSharedLibraries = new android.util.ArrayMap<>();
    final android.util.ArrayMap<java.lang.String, android.content.pm.FeatureInfo> mAvailableFeatures = new android.util.ArrayMap<>();
    final android.util.ArraySet<java.lang.String> mUnavailableFeatures = new android.util.ArraySet<>();
    final android.util.ArrayMap<java.lang.String, com.android.server.SystemConfig.PermissionEntry> mPermissions = new android.util.ArrayMap<>();
    final android.util.ArraySet<java.lang.String> mAllowInPowerSaveExceptIdle = new android.util.ArraySet<>();
    final android.util.ArraySet<java.lang.String> mAllowInPowerSave = new android.util.ArraySet<>();
    final android.util.ArraySet<java.lang.String> mAllowInDataUsageSave = new android.util.ArraySet<>();
    final android.util.ArraySet<java.lang.String> mAllowUnthrottledLocation = new android.util.ArraySet<>();
    final android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> mAllowAdasSettings = new android.util.ArrayMap<>();
    final android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> mAllowIgnoreLocationSettings = new android.util.ArrayMap<>();
    final android.util.ArrayMap<java.lang.String, java.lang.Boolean> mAllowlistCameraPrivacy = new android.util.ArrayMap<>();
    final android.util.ArraySet<java.lang.String> mAllowImplicitBroadcasts = new android.util.ArraySet<>();
    final android.util.ArraySet<java.lang.String> mBgRestrictionExemption = new android.util.ArraySet<>();
    final android.util.ArraySet<java.lang.String> mLinkedApps = new android.util.ArraySet<>();
    final android.util.ArraySet<android.content.ComponentName> mDefaultVrComponents = new android.util.ArraySet<>();
    final android.util.ArraySet<android.content.ComponentName> mBackupTransportWhitelist = new android.util.ArraySet<>();
    final android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> mPackageComponentEnabledState = new android.util.ArrayMap<>();
    final android.util.ArraySet<java.lang.String> mHiddenApiPackageWhitelist = new android.util.ArraySet<>();
    final android.util.ArraySet<java.lang.String> mDisabledUntilUsedPreinstalledCarrierApps = new android.util.ArraySet<>();
    final android.util.ArrayMap<java.lang.String, java.util.List<android.os.CarrierAssociatedAppEntry>> mDisabledUntilUsedPreinstalledCarrierAssociatedApps = new android.util.ArrayMap<>();
    private final com.android.server.pm.permission.PermissionAllowlist mPermissionAllowlist = new com.android.server.pm.permission.PermissionAllowlist();
    final android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> mAllowedAssociations = new android.util.ArrayMap<>();
    private final android.util.ArraySet<java.lang.String> mBugreportWhitelistedPackages = new android.util.ArraySet<>();
    private final android.util.ArraySet<java.lang.String> mAppDataIsolationWhitelistedApps = new android.util.ArraySet<>();
    private final java.util.ArrayList<java.lang.String> mPreventUserDisablePackages = new java.util.ArrayList<>();
    private android.util.ArrayMap<java.lang.String, java.util.Set<java.lang.String>> mPackageToUserTypeWhitelist = new android.util.ArrayMap<>();
    private android.util.ArrayMap<java.lang.String, java.util.Set<java.lang.String>> mPackageToUserTypeBlacklist = new android.util.ArrayMap<>();
    private final android.util.ArraySet<java.lang.String> mRollbackWhitelistedPackages = new android.util.ArraySet<>();
    private final android.util.ArraySet<java.lang.String> mWhitelistedStagedInstallers = new android.util.ArraySet<>();
    private final android.util.ArrayMap<java.lang.String, java.lang.String> mAllowedVendorApexes = new android.util.ArrayMap<>();
    private final java.util.Set<java.lang.String> mInstallConstraintsAllowlist = new android.util.ArraySet();
    private final android.util.ArrayMap<java.lang.String, java.lang.String> mUpdateOwnersForSystemApps = new android.util.ArrayMap<>();

    @android.annotation.NonNull
    private final java.util.Set<java.lang.String> mInitialNonStoppedSystemPackages = new android.util.ArraySet();

    @android.annotation.NonNull
    private final java.util.Map<java.lang.String, java.lang.String> mPackageToSharedUidAllowList = new android.util.ArrayMap();
    private final android.util.ArrayMap<java.lang.String, java.lang.String> mAppMetadataFilePaths = new android.util.ArrayMap<>();
    private final java.util.Set<java.lang.String> mPreinstallPackagesWithStrictSignatureCheck = new android.util.ArraySet();
    private final android.util.ArraySet<android.content.pm.SignedPackage> mEnhancedConfirmationTrustedPackages = new android.util.ArraySet<>();
    private final android.util.ArraySet<android.content.pm.SignedPackage> mEnhancedConfirmationTrustedInstallers = new android.util.ArraySet<>();
    private java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> mNamedActors = null;

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isAtLeastSdkLevel(java.lang.String str) {
        try {
            return com.android.modules.utils.build.UnboundedSdkLevel.isAtLeast(str);
        } catch (java.lang.IllegalArgumentException e) {
            return false;
        }
    }

    private static boolean isAtMostSdkLevel(java.lang.String str) {
        try {
            return com.android.modules.utils.build.UnboundedSdkLevel.isAtMost(str);
        } catch (java.lang.IllegalArgumentException e) {
            return true;
        }
    }

    public static final class SharedLibraryEntry {
        public final boolean canBeSafelyIgnored;
        public final java.lang.String[] dependencies;
        public final java.lang.String filename;
        public final boolean isNative;
        public final java.lang.String name;
        public final java.lang.String onBootclasspathBefore;
        public final java.lang.String onBootclasspathSince;

        @com.android.internal.annotations.VisibleForTesting
        public SharedLibraryEntry(java.lang.String str, java.lang.String str2, java.lang.String[] strArr, boolean z) {
            this(str, str2, strArr, null, null, z);
        }

        @com.android.internal.annotations.VisibleForTesting
        public SharedLibraryEntry(java.lang.String str, java.lang.String str2, java.lang.String[] strArr, java.lang.String str3, java.lang.String str4) {
            this(str, str2, strArr, str3, str4, false);
        }

        SharedLibraryEntry(java.lang.String str, java.lang.String str2, java.lang.String[] strArr, java.lang.String str3, java.lang.String str4, boolean z) {
            this.name = str;
            this.filename = str2;
            this.dependencies = strArr;
            this.onBootclasspathSince = str3;
            this.onBootclasspathBefore = str4;
            this.isNative = z;
            this.canBeSafelyIgnored = (this.onBootclasspathSince != null && com.android.server.SystemConfig.isAtLeastSdkLevel(this.onBootclasspathSince)) || !(this.onBootclasspathBefore == null || com.android.server.SystemConfig.isAtLeastSdkLevel(this.onBootclasspathBefore));
        }
    }

    public static final class PermissionEntry {
        public int[] gids;
        public final java.lang.String name;
        public boolean perUser;

        PermissionEntry(java.lang.String str, boolean z) {
            this.name = str;
            this.perUser = z;
        }
    }

    public static com.android.server.SystemConfig getInstance() {
        com.android.server.SystemConfig systemConfig;
        if (!isSystemProcess()) {
            android.util.Slog.wtf(TAG, "SystemConfig is being accessed by a process other than system_server.");
        }
        synchronized (com.android.server.SystemConfig.class) {
            try {
                if (sInstance == null) {
                    sInstance = new com.android.server.SystemConfig();
                }
                systemConfig = sInstance;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return systemConfig;
    }

    public int[] getGlobalGids() {
        return this.mGlobalGids;
    }

    public android.util.SparseArray<android.util.ArraySet<java.lang.String>> getSystemPermissions() {
        return this.mSystemPermissions;
    }

    public java.util.ArrayList<android.permission.PermissionManager.SplitPermissionInfo> getSplitPermissions() {
        return this.mSplitPermissions;
    }

    public android.util.ArrayMap<java.lang.String, com.android.server.SystemConfig.SharedLibraryEntry> getSharedLibraries() {
        return this.mSharedLibraries;
    }

    public android.util.ArrayMap<java.lang.String, android.content.pm.FeatureInfo> getAvailableFeatures() {
        return this.mAvailableFeatures;
    }

    public android.util.ArrayMap<java.lang.String, com.android.server.SystemConfig.PermissionEntry> getPermissions() {
        return this.mPermissions;
    }

    public android.util.ArraySet<java.lang.String> getAllowImplicitBroadcasts() {
        return this.mAllowImplicitBroadcasts;
    }

    public android.util.ArraySet<java.lang.String> getAllowInPowerSaveExceptIdle() {
        return this.mAllowInPowerSaveExceptIdle;
    }

    public android.util.ArraySet<java.lang.String> getAllowInPowerSave() {
        return this.mAllowInPowerSave;
    }

    public android.util.ArraySet<java.lang.String> getAllowInDataUsageSave() {
        return this.mAllowInDataUsageSave;
    }

    public android.util.ArraySet<java.lang.String> getAllowUnthrottledLocation() {
        return this.mAllowUnthrottledLocation;
    }

    public android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> getAllowAdasLocationSettings() {
        return this.mAllowAdasSettings;
    }

    public android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> getAllowIgnoreLocationSettings() {
        return this.mAllowIgnoreLocationSettings;
    }

    public android.util.ArraySet<java.lang.String> getBgRestrictionExemption() {
        return this.mBgRestrictionExemption;
    }

    public android.util.ArraySet<java.lang.String> getLinkedApps() {
        return this.mLinkedApps;
    }

    public android.util.ArraySet<java.lang.String> getHiddenApiWhitelistedApps() {
        return this.mHiddenApiPackageWhitelist;
    }

    public android.util.ArraySet<android.content.ComponentName> getDefaultVrComponents() {
        return this.mDefaultVrComponents;
    }

    public android.util.ArraySet<android.content.ComponentName> getBackupTransportWhitelist() {
        return this.mBackupTransportWhitelist;
    }

    public android.util.ArrayMap<java.lang.String, java.lang.Boolean> getComponentsEnabledStates(java.lang.String str) {
        return this.mPackageComponentEnabledState.get(str);
    }

    public android.util.ArraySet<java.lang.String> getDisabledUntilUsedPreinstalledCarrierApps() {
        return this.mDisabledUntilUsedPreinstalledCarrierApps;
    }

    public android.util.ArrayMap<java.lang.String, java.util.List<android.os.CarrierAssociatedAppEntry>> getDisabledUntilUsedPreinstalledCarrierAssociatedApps() {
        return this.mDisabledUntilUsedPreinstalledCarrierAssociatedApps;
    }

    public com.android.server.pm.permission.PermissionAllowlist getPermissionAllowlist() {
        return this.mPermissionAllowlist;
    }

    public android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.lang.String>> getAllowedAssociations() {
        return this.mAllowedAssociations;
    }

    public android.util.ArrayMap<java.lang.String, java.lang.Boolean> getCameraPrivacyAllowlist() {
        return this.mAllowlistCameraPrivacy;
    }

    public android.util.ArraySet<java.lang.String> getBugreportWhitelistedPackages() {
        return this.mBugreportWhitelistedPackages;
    }

    public java.util.Set<java.lang.String> getRollbackWhitelistedPackages() {
        return this.mRollbackWhitelistedPackages;
    }

    public java.util.Set<java.lang.String> getWhitelistedStagedInstallers() {
        return this.mWhitelistedStagedInstallers;
    }

    public java.util.Map<java.lang.String, java.lang.String> getAllowedVendorApexes() {
        return this.mAllowedVendorApexes;
    }

    public java.util.Set<java.lang.String> getInstallConstraintsAllowlist() {
        return this.mInstallConstraintsAllowlist;
    }

    public java.lang.String getModulesInstallerPackageName() {
        return this.mModulesInstallerPackageName;
    }

    @android.annotation.Nullable
    public java.lang.String getSystemAppUpdateOwnerPackageName(@android.annotation.NonNull java.lang.String str) {
        return this.mUpdateOwnersForSystemApps.get(str);
    }

    public android.util.ArraySet<java.lang.String> getAppDataIsolationWhitelistedApps() {
        return this.mAppDataIsolationWhitelistedApps;
    }

    @android.annotation.NonNull
    public java.util.ArrayList<java.lang.String> getPreventUserDisablePackages() {
        return this.mPreventUserDisablePackages;
    }

    public android.util.ArrayMap<java.lang.String, java.util.Set<java.lang.String>> getAndClearPackageToUserTypeWhitelist() {
        android.util.ArrayMap<java.lang.String, java.util.Set<java.lang.String>> arrayMap = this.mPackageToUserTypeWhitelist;
        this.mPackageToUserTypeWhitelist = new android.util.ArrayMap<>(0);
        return arrayMap;
    }

    public android.util.ArrayMap<java.lang.String, java.util.Set<java.lang.String>> getAndClearPackageToUserTypeBlacklist() {
        android.util.ArrayMap<java.lang.String, java.util.Set<java.lang.String>> arrayMap = this.mPackageToUserTypeBlacklist;
        this.mPackageToUserTypeBlacklist = new android.util.ArrayMap<>(0);
        return arrayMap;
    }

    @android.annotation.NonNull
    public java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> getNamedActors() {
        return this.mNamedActors != null ? this.mNamedActors : java.util.Collections.emptyMap();
    }

    @android.annotation.Nullable
    public java.lang.String getOverlayConfigSignaturePackage() {
        if (android.text.TextUtils.isEmpty(this.mOverlayConfigSignaturePackage)) {
            return null;
        }
        return this.mOverlayConfigSignaturePackage;
    }

    public java.util.Set<java.lang.String> getInitialNonStoppedSystemPackages() {
        return this.mInitialNonStoppedSystemPackages;
    }

    @android.annotation.NonNull
    public java.util.Map<java.lang.String, java.lang.String> getPackageToSharedUidAllowList() {
        return this.mPackageToSharedUidAllowList;
    }

    public android.util.ArrayMap<java.lang.String, java.lang.String> getAppMetadataFilePaths() {
        return this.mAppMetadataFilePaths;
    }

    public java.util.Set<java.lang.String> getPreinstallPackagesWithStrictSignatureCheck() {
        return this.mPreinstallPackagesWithStrictSignatureCheck;
    }

    public android.util.ArraySet<android.content.pm.SignedPackage> getEnhancedConfirmationTrustedPackages() {
        return this.mEnhancedConfirmationTrustedPackages;
    }

    public android.util.ArraySet<android.content.pm.SignedPackage> getEnhancedConfirmationTrustedInstallers() {
        return this.mEnhancedConfirmationTrustedInstallers;
    }

    @com.android.internal.annotations.VisibleForTesting
    public SystemConfig(boolean z) {
        if (z) {
            android.util.Slog.w(TAG, "Constructing a test SystemConfig");
            readAllPermissions();
        } else {
            android.util.Slog.w(TAG, "Constructing an empty test SystemConfig");
        }
    }

    SystemConfig() {
        android.util.TimingsTraceLog timingsTraceLog = new android.util.TimingsTraceLog(TAG, 524288L);
        timingsTraceLog.traceBegin("readAllPermissions");
        try {
            readAllPermissions();
            readPublicNativeLibrariesList();
        } finally {
            timingsTraceLog.traceEnd();
        }
    }

    private void readAllPermissions() {
        int i;
        int i2;
        org.xmlpull.v1.XmlPullParser newPullParser = android.util.Xml.newPullParser();
        readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getRootDirectory(), new java.lang.String[]{"etc", "sysconfig"}), -1);
        readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getRootDirectory(), new java.lang.String[]{"etc", "permissions"}), -1);
        if (android.os.Build.VERSION.DEVICE_INITIAL_SDK_INT > 27) {
            i = 3219;
        } else {
            i = 3231;
        }
        readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getVendorDirectory(), new java.lang.String[]{"etc", "sysconfig"}), i);
        readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getVendorDirectory(), new java.lang.String[]{"etc", "permissions"}), i);
        java.lang.String str = android.os.SystemProperties.get(VENDOR_SKU_PROPERTY, "");
        if (!str.isEmpty()) {
            java.lang.String str2 = "sku_" + str;
            readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getVendorDirectory(), new java.lang.String[]{"etc", "sysconfig", str2}), i);
            readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getVendorDirectory(), new java.lang.String[]{"etc", "permissions", str2}), i);
        }
        readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getOdmDirectory(), new java.lang.String[]{"etc", "sysconfig"}), i);
        readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getOdmDirectory(), new java.lang.String[]{"etc", "permissions"}), i);
        java.lang.String str3 = android.os.SystemProperties.get(SKU_PROPERTY, "");
        if (!str3.isEmpty()) {
            java.lang.String str4 = "sku_" + str3;
            readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getOdmDirectory(), new java.lang.String[]{"etc", "sysconfig", str4}), i);
            readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getOdmDirectory(), new java.lang.String[]{"etc", "permissions", str4}), i);
        }
        readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getOemDirectory(), new java.lang.String[]{"etc", "sysconfig"}), 1185);
        readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getOemDirectory(), new java.lang.String[]{"etc", "permissions"}), 1185);
        if (android.os.Build.VERSION.DEVICE_INITIAL_SDK_INT > 30) {
            i2 = 4063;
        } else {
            i2 = -1;
        }
        readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getProductDirectory(), new java.lang.String[]{"etc", "sysconfig"}), i2);
        readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getProductDirectory(), new java.lang.String[]{"etc", "permissions"}), i2);
        readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getSystemExtDirectory(), new java.lang.String[]{"etc", "sysconfig"}), -1);
        readPermissions(newPullParser, android.os.Environment.buildPath(android.os.Environment.getSystemExtDirectory(), new java.lang.String[]{"etc", "permissions"}), -1);
        if (!isSystemProcess()) {
            return;
        }
        for (java.io.File file : android.os.FileUtils.listFilesOrEmpty(android.os.Environment.getApexDirectory())) {
            if (!file.isFile() && !file.getPath().contains("@")) {
                readPermissions(newPullParser, android.os.Environment.buildPath(file, new java.lang.String[]{"etc", "permissions"}), 19);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void readPermissions(org.xmlpull.v1.XmlPullParser xmlPullParser, java.io.File file, int i) {
        if (!file.exists() || !file.isDirectory()) {
            if (i == -1) {
                android.util.Slog.w(TAG, "No directory " + file + ", skipping");
                return;
            }
            return;
        }
        if (!file.canRead()) {
            android.util.Slog.w(TAG, "Directory " + file + " cannot be read");
            return;
        }
        java.io.File file2 = null;
        for (java.io.File file3 : file.listFiles()) {
            if (file3.isFile()) {
                if (file3.getPath().endsWith("etc/permissions/platform.xml")) {
                    file2 = file3;
                } else if (!file3.getPath().endsWith(".xml")) {
                    android.util.Slog.i(TAG, "Non-xml file " + file3 + " in " + file + " directory, ignoring");
                } else if (file3.canRead()) {
                    readPermissionsFromXml(xmlPullParser, file3, i);
                } else {
                    android.util.Slog.w(TAG, "Permissions library file " + file3 + " cannot be read");
                }
            }
        }
        if (file2 != null) {
            readPermissionsFromXml(xmlPullParser, file2, i);
        }
    }

    private void logNotAllowedInPartition(java.lang.String str, java.io.File file, org.xmlpull.v1.XmlPullParser xmlPullParser) {
        android.util.Slog.w(TAG, "<" + str + "> not allowed in partition of " + file + " at " + xmlPullParser.getPositionDescription());
    }

    /* JADX WARN: Removed duplicated region for block: B:255:0x0a9a A[Catch: all -> 0x036f, IOException -> 0x0373, XmlPullParserException -> 0x0377, TryCatch #10 {all -> 0x036f, blocks: (B:68:0x035a, B:70:0x0360, B:72:0x0366, B:77:0x037f, B:79:0x0385, B:81:0x038b, B:83:0x03c5, B:85:0x03cb, B:87:0x03d5, B:89:0x03f8, B:91:0x0405, B:93:0x0416, B:94:0x0439, B:96:0x043f, B:97:0x0464, B:98:0x0469, B:100:0x047a, B:102:0x049d, B:104:0x04a3, B:105:0x04c8, B:106:0x04d1, B:108:0x04e2, B:110:0x0505, B:112:0x050b, B:113:0x0530, B:115:0x0536, B:116:0x053f, B:118:0x0550, B:119:0x05a3, B:120:0x0573, B:122:0x0579, B:123:0x059e, B:125:0x05ac, B:127:0x05b2, B:129:0x05de, B:130:0x05d5, B:131:0x05db, B:133:0x05e7, B:135:0x05f4, B:137:0x0618, B:140:0x0640, B:142:0x0649, B:143:0x0646, B:145:0x0652, B:147:0x0660, B:149:0x068a, B:151:0x068e, B:153:0x0691, B:154:0x0698, B:157:0x069d, B:158:0x0683, B:159:0x069a, B:160:0x06a4, B:162:0x06aa, B:163:0x06d2, B:164:0x06cd, B:166:0x06db, B:168:0x06e1, B:170:0x0737, B:171:0x0704, B:173:0x070c, B:175:0x0713, B:176:0x0733, B:177:0x0734, B:178:0x073e, B:180:0x075c, B:181:0x0809, B:182:0x0782, B:184:0x0788, B:185:0x07ad, B:187:0x07b3, B:188:0x07d8, B:190:0x07e0, B:192:0x07e4, B:193:0x07eb, B:195:0x07f5, B:196:0x0806, B:197:0x0800, B:200:0x0810, B:201:0x0844, B:203:0x0845, B:204:0x0868, B:205:0x0869, B:206:0x0874, B:208:0x087a, B:209:0x08a2, B:210:0x089d, B:211:0x08a9, B:213:0x08af, B:214:0x08d7, B:215:0x08d2, B:216:0x08de, B:218:0x08e4, B:219:0x090c, B:220:0x0907, B:222:0x0915, B:224:0x091e, B:225:0x0949, B:227:0x0951, B:228:0x097c, B:230:0x098e, B:231:0x0998, B:232:0x09bb, B:233:0x09b8, B:235:0x09c4, B:237:0x09ca, B:239:0x09f6, B:240:0x09ed, B:241:0x09f3, B:243:0x09ff, B:244:0x0a06, B:247:0x0a14, B:249:0x0a35, B:253:0x0a5a, B:255:0x0a9a, B:258:0x0aa6, B:260:0x0ab2, B:261:0x0abc, B:263:0x0ac9, B:266:0x0ad7, B:268:0x0af8, B:272:0x0b1d, B:274:0x0b7c, B:277:0x0b88, B:279:0x0b94, B:281:0x0ba1, B:282:0x0bad, B:284:0x0bba, B:286:0x0bc6, B:288:0x0bcc, B:290:0x0bf8, B:291:0x0bef, B:292:0x0bf5, B:294:0x0c01, B:297:0x0c10, B:307:0x0c1d, B:300:0x0c4f, B:302:0x0c59, B:303:0x0c63, B:305:0x0c94, B:310:0x0c23, B:312:0x0c6c, B:313:0x0c91, B:315:0x0c9d, B:317:0x0ca6, B:319:0x0d07, B:320:0x0ccb, B:322:0x0cd1, B:323:0x0cfe, B:324:0x0d04, B:325:0x0d0e, B:327:0x0d17, B:329:0x0d23, B:331:0x0d7b, B:333:0x0d48, B:334:0x0d6d, B:335:0x0d78, B:337:0x0d84, B:339:0x0d8a, B:341:0x0db6, B:342:0x0dad, B:343:0x0db3, B:345:0x0dbf, B:347:0x0dc5, B:349:0x0df1, B:350:0x0de8, B:351:0x0dee, B:353:0x0dfa, B:355:0x0e02, B:357:0x0e30, B:358:0x0e27, B:359:0x0e2d, B:361:0x0e39, B:363:0x0e45, B:365:0x0e9f, B:366:0x0e68, B:368:0x0e72, B:371:0x0e7a, B:372:0x0e85, B:374:0x0e8d, B:377:0x0e98, B:379:0x0e9c, B:381:0x0ea8, B:383:0x0eb6, B:385:0x0ee6, B:386:0x0ed9, B:387:0x0ee3, B:389:0x0eef, B:391:0x0efb, B:393:0x0f55, B:394:0x0f1e, B:396:0x0f28, B:399:0x0f30, B:400:0x0f3b, B:402:0x0f43, B:405:0x0f4e, B:407:0x0f52, B:409:0x0f5e, B:411:0x0f64, B:413:0x0f90, B:414:0x0f87, B:415:0x0f8d, B:417:0x0f99, B:419:0x0f9f, B:421:0x0fcb, B:422:0x0fc2, B:423:0x0fc8, B:425:0x0fd4, B:427:0x0fda, B:429:0x1006, B:430:0x0ffd, B:431:0x1003, B:433:0x100f, B:435:0x1015, B:437:0x1041, B:438:0x1038, B:439:0x103e, B:441:0x104a, B:443:0x1050, B:445:0x107e, B:446:0x1075, B:447:0x107b, B:450:0x1089, B:454:0x10af, B:456:0x10dc, B:458:0x10d4, B:459:0x109b, B:460:0x10d8, B:463:0x10e8, B:465:0x110b, B:467:0x11ed, B:469:0x1131, B:471:0x1159, B:476:0x1165, B:480:0x116f, B:484:0x117e, B:486:0x1191, B:487:0x119f, B:488:0x1197, B:489:0x11ae, B:491:0x11bf, B:493:0x11c9, B:495:0x11d3, B:496:0x11e0, B:499:0x11e8, B:502:0x11f6, B:503:0x11fb, B:506:0x1209, B:508:0x1210, B:509:0x1237, B:511:0x1241, B:512:0x126a, B:514:0x1270, B:515:0x12a1, B:517:0x12af, B:518:0x12b9, B:519:0x12c0, B:520:0x12bd, B:523:0x12cb, B:525:0x12d2, B:526:0x12f8, B:527:0x1300, B:530:0x130b, B:532:0x1314, B:534:0x1349, B:535:0x1321, B:536:0x1346, B:537:0x0398, B:728:0x1378, B:732:0x1383, B:737:0x136d, B:738:0x1376), top: B:5:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0aa4  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x0b7c A[Catch: all -> 0x036f, IOException -> 0x0373, XmlPullParserException -> 0x0377, TryCatch #10 {all -> 0x036f, blocks: (B:68:0x035a, B:70:0x0360, B:72:0x0366, B:77:0x037f, B:79:0x0385, B:81:0x038b, B:83:0x03c5, B:85:0x03cb, B:87:0x03d5, B:89:0x03f8, B:91:0x0405, B:93:0x0416, B:94:0x0439, B:96:0x043f, B:97:0x0464, B:98:0x0469, B:100:0x047a, B:102:0x049d, B:104:0x04a3, B:105:0x04c8, B:106:0x04d1, B:108:0x04e2, B:110:0x0505, B:112:0x050b, B:113:0x0530, B:115:0x0536, B:116:0x053f, B:118:0x0550, B:119:0x05a3, B:120:0x0573, B:122:0x0579, B:123:0x059e, B:125:0x05ac, B:127:0x05b2, B:129:0x05de, B:130:0x05d5, B:131:0x05db, B:133:0x05e7, B:135:0x05f4, B:137:0x0618, B:140:0x0640, B:142:0x0649, B:143:0x0646, B:145:0x0652, B:147:0x0660, B:149:0x068a, B:151:0x068e, B:153:0x0691, B:154:0x0698, B:157:0x069d, B:158:0x0683, B:159:0x069a, B:160:0x06a4, B:162:0x06aa, B:163:0x06d2, B:164:0x06cd, B:166:0x06db, B:168:0x06e1, B:170:0x0737, B:171:0x0704, B:173:0x070c, B:175:0x0713, B:176:0x0733, B:177:0x0734, B:178:0x073e, B:180:0x075c, B:181:0x0809, B:182:0x0782, B:184:0x0788, B:185:0x07ad, B:187:0x07b3, B:188:0x07d8, B:190:0x07e0, B:192:0x07e4, B:193:0x07eb, B:195:0x07f5, B:196:0x0806, B:197:0x0800, B:200:0x0810, B:201:0x0844, B:203:0x0845, B:204:0x0868, B:205:0x0869, B:206:0x0874, B:208:0x087a, B:209:0x08a2, B:210:0x089d, B:211:0x08a9, B:213:0x08af, B:214:0x08d7, B:215:0x08d2, B:216:0x08de, B:218:0x08e4, B:219:0x090c, B:220:0x0907, B:222:0x0915, B:224:0x091e, B:225:0x0949, B:227:0x0951, B:228:0x097c, B:230:0x098e, B:231:0x0998, B:232:0x09bb, B:233:0x09b8, B:235:0x09c4, B:237:0x09ca, B:239:0x09f6, B:240:0x09ed, B:241:0x09f3, B:243:0x09ff, B:244:0x0a06, B:247:0x0a14, B:249:0x0a35, B:253:0x0a5a, B:255:0x0a9a, B:258:0x0aa6, B:260:0x0ab2, B:261:0x0abc, B:263:0x0ac9, B:266:0x0ad7, B:268:0x0af8, B:272:0x0b1d, B:274:0x0b7c, B:277:0x0b88, B:279:0x0b94, B:281:0x0ba1, B:282:0x0bad, B:284:0x0bba, B:286:0x0bc6, B:288:0x0bcc, B:290:0x0bf8, B:291:0x0bef, B:292:0x0bf5, B:294:0x0c01, B:297:0x0c10, B:307:0x0c1d, B:300:0x0c4f, B:302:0x0c59, B:303:0x0c63, B:305:0x0c94, B:310:0x0c23, B:312:0x0c6c, B:313:0x0c91, B:315:0x0c9d, B:317:0x0ca6, B:319:0x0d07, B:320:0x0ccb, B:322:0x0cd1, B:323:0x0cfe, B:324:0x0d04, B:325:0x0d0e, B:327:0x0d17, B:329:0x0d23, B:331:0x0d7b, B:333:0x0d48, B:334:0x0d6d, B:335:0x0d78, B:337:0x0d84, B:339:0x0d8a, B:341:0x0db6, B:342:0x0dad, B:343:0x0db3, B:345:0x0dbf, B:347:0x0dc5, B:349:0x0df1, B:350:0x0de8, B:351:0x0dee, B:353:0x0dfa, B:355:0x0e02, B:357:0x0e30, B:358:0x0e27, B:359:0x0e2d, B:361:0x0e39, B:363:0x0e45, B:365:0x0e9f, B:366:0x0e68, B:368:0x0e72, B:371:0x0e7a, B:372:0x0e85, B:374:0x0e8d, B:377:0x0e98, B:379:0x0e9c, B:381:0x0ea8, B:383:0x0eb6, B:385:0x0ee6, B:386:0x0ed9, B:387:0x0ee3, B:389:0x0eef, B:391:0x0efb, B:393:0x0f55, B:394:0x0f1e, B:396:0x0f28, B:399:0x0f30, B:400:0x0f3b, B:402:0x0f43, B:405:0x0f4e, B:407:0x0f52, B:409:0x0f5e, B:411:0x0f64, B:413:0x0f90, B:414:0x0f87, B:415:0x0f8d, B:417:0x0f99, B:419:0x0f9f, B:421:0x0fcb, B:422:0x0fc2, B:423:0x0fc8, B:425:0x0fd4, B:427:0x0fda, B:429:0x1006, B:430:0x0ffd, B:431:0x1003, B:433:0x100f, B:435:0x1015, B:437:0x1041, B:438:0x1038, B:439:0x103e, B:441:0x104a, B:443:0x1050, B:445:0x107e, B:446:0x1075, B:447:0x107b, B:450:0x1089, B:454:0x10af, B:456:0x10dc, B:458:0x10d4, B:459:0x109b, B:460:0x10d8, B:463:0x10e8, B:465:0x110b, B:467:0x11ed, B:469:0x1131, B:471:0x1159, B:476:0x1165, B:480:0x116f, B:484:0x117e, B:486:0x1191, B:487:0x119f, B:488:0x1197, B:489:0x11ae, B:491:0x11bf, B:493:0x11c9, B:495:0x11d3, B:496:0x11e0, B:499:0x11e8, B:502:0x11f6, B:503:0x11fb, B:506:0x1209, B:508:0x1210, B:509:0x1237, B:511:0x1241, B:512:0x126a, B:514:0x1270, B:515:0x12a1, B:517:0x12af, B:518:0x12b9, B:519:0x12c0, B:520:0x12bd, B:523:0x12cb, B:525:0x12d2, B:526:0x12f8, B:527:0x1300, B:530:0x130b, B:532:0x1314, B:534:0x1349, B:535:0x1321, B:536:0x1346, B:537:0x0398, B:728:0x1378, B:732:0x1383, B:737:0x136d, B:738:0x1376), top: B:5:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:276:0x0b86  */
    /* JADX WARN: Removed duplicated region for block: B:482:0x117a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:491:0x11bf A[Catch: all -> 0x036f, IOException -> 0x0373, XmlPullParserException -> 0x0377, TryCatch #10 {all -> 0x036f, blocks: (B:68:0x035a, B:70:0x0360, B:72:0x0366, B:77:0x037f, B:79:0x0385, B:81:0x038b, B:83:0x03c5, B:85:0x03cb, B:87:0x03d5, B:89:0x03f8, B:91:0x0405, B:93:0x0416, B:94:0x0439, B:96:0x043f, B:97:0x0464, B:98:0x0469, B:100:0x047a, B:102:0x049d, B:104:0x04a3, B:105:0x04c8, B:106:0x04d1, B:108:0x04e2, B:110:0x0505, B:112:0x050b, B:113:0x0530, B:115:0x0536, B:116:0x053f, B:118:0x0550, B:119:0x05a3, B:120:0x0573, B:122:0x0579, B:123:0x059e, B:125:0x05ac, B:127:0x05b2, B:129:0x05de, B:130:0x05d5, B:131:0x05db, B:133:0x05e7, B:135:0x05f4, B:137:0x0618, B:140:0x0640, B:142:0x0649, B:143:0x0646, B:145:0x0652, B:147:0x0660, B:149:0x068a, B:151:0x068e, B:153:0x0691, B:154:0x0698, B:157:0x069d, B:158:0x0683, B:159:0x069a, B:160:0x06a4, B:162:0x06aa, B:163:0x06d2, B:164:0x06cd, B:166:0x06db, B:168:0x06e1, B:170:0x0737, B:171:0x0704, B:173:0x070c, B:175:0x0713, B:176:0x0733, B:177:0x0734, B:178:0x073e, B:180:0x075c, B:181:0x0809, B:182:0x0782, B:184:0x0788, B:185:0x07ad, B:187:0x07b3, B:188:0x07d8, B:190:0x07e0, B:192:0x07e4, B:193:0x07eb, B:195:0x07f5, B:196:0x0806, B:197:0x0800, B:200:0x0810, B:201:0x0844, B:203:0x0845, B:204:0x0868, B:205:0x0869, B:206:0x0874, B:208:0x087a, B:209:0x08a2, B:210:0x089d, B:211:0x08a9, B:213:0x08af, B:214:0x08d7, B:215:0x08d2, B:216:0x08de, B:218:0x08e4, B:219:0x090c, B:220:0x0907, B:222:0x0915, B:224:0x091e, B:225:0x0949, B:227:0x0951, B:228:0x097c, B:230:0x098e, B:231:0x0998, B:232:0x09bb, B:233:0x09b8, B:235:0x09c4, B:237:0x09ca, B:239:0x09f6, B:240:0x09ed, B:241:0x09f3, B:243:0x09ff, B:244:0x0a06, B:247:0x0a14, B:249:0x0a35, B:253:0x0a5a, B:255:0x0a9a, B:258:0x0aa6, B:260:0x0ab2, B:261:0x0abc, B:263:0x0ac9, B:266:0x0ad7, B:268:0x0af8, B:272:0x0b1d, B:274:0x0b7c, B:277:0x0b88, B:279:0x0b94, B:281:0x0ba1, B:282:0x0bad, B:284:0x0bba, B:286:0x0bc6, B:288:0x0bcc, B:290:0x0bf8, B:291:0x0bef, B:292:0x0bf5, B:294:0x0c01, B:297:0x0c10, B:307:0x0c1d, B:300:0x0c4f, B:302:0x0c59, B:303:0x0c63, B:305:0x0c94, B:310:0x0c23, B:312:0x0c6c, B:313:0x0c91, B:315:0x0c9d, B:317:0x0ca6, B:319:0x0d07, B:320:0x0ccb, B:322:0x0cd1, B:323:0x0cfe, B:324:0x0d04, B:325:0x0d0e, B:327:0x0d17, B:329:0x0d23, B:331:0x0d7b, B:333:0x0d48, B:334:0x0d6d, B:335:0x0d78, B:337:0x0d84, B:339:0x0d8a, B:341:0x0db6, B:342:0x0dad, B:343:0x0db3, B:345:0x0dbf, B:347:0x0dc5, B:349:0x0df1, B:350:0x0de8, B:351:0x0dee, B:353:0x0dfa, B:355:0x0e02, B:357:0x0e30, B:358:0x0e27, B:359:0x0e2d, B:361:0x0e39, B:363:0x0e45, B:365:0x0e9f, B:366:0x0e68, B:368:0x0e72, B:371:0x0e7a, B:372:0x0e85, B:374:0x0e8d, B:377:0x0e98, B:379:0x0e9c, B:381:0x0ea8, B:383:0x0eb6, B:385:0x0ee6, B:386:0x0ed9, B:387:0x0ee3, B:389:0x0eef, B:391:0x0efb, B:393:0x0f55, B:394:0x0f1e, B:396:0x0f28, B:399:0x0f30, B:400:0x0f3b, B:402:0x0f43, B:405:0x0f4e, B:407:0x0f52, B:409:0x0f5e, B:411:0x0f64, B:413:0x0f90, B:414:0x0f87, B:415:0x0f8d, B:417:0x0f99, B:419:0x0f9f, B:421:0x0fcb, B:422:0x0fc2, B:423:0x0fc8, B:425:0x0fd4, B:427:0x0fda, B:429:0x1006, B:430:0x0ffd, B:431:0x1003, B:433:0x100f, B:435:0x1015, B:437:0x1041, B:438:0x1038, B:439:0x103e, B:441:0x104a, B:443:0x1050, B:445:0x107e, B:446:0x1075, B:447:0x107b, B:450:0x1089, B:454:0x10af, B:456:0x10dc, B:458:0x10d4, B:459:0x109b, B:460:0x10d8, B:463:0x10e8, B:465:0x110b, B:467:0x11ed, B:469:0x1131, B:471:0x1159, B:476:0x1165, B:480:0x116f, B:484:0x117e, B:486:0x1191, B:487:0x119f, B:488:0x1197, B:489:0x11ae, B:491:0x11bf, B:493:0x11c9, B:495:0x11d3, B:496:0x11e0, B:499:0x11e8, B:502:0x11f6, B:503:0x11fb, B:506:0x1209, B:508:0x1210, B:509:0x1237, B:511:0x1241, B:512:0x126a, B:514:0x1270, B:515:0x12a1, B:517:0x12af, B:518:0x12b9, B:519:0x12c0, B:520:0x12bd, B:523:0x12cb, B:525:0x12d2, B:526:0x12f8, B:527:0x1300, B:530:0x130b, B:532:0x1314, B:534:0x1349, B:535:0x1321, B:536:0x1346, B:537:0x0398, B:728:0x1378, B:732:0x1383, B:737:0x136d, B:738:0x1376), top: B:5:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:493:0x11c9 A[Catch: all -> 0x036f, IOException -> 0x0373, XmlPullParserException -> 0x0377, TryCatch #10 {all -> 0x036f, blocks: (B:68:0x035a, B:70:0x0360, B:72:0x0366, B:77:0x037f, B:79:0x0385, B:81:0x038b, B:83:0x03c5, B:85:0x03cb, B:87:0x03d5, B:89:0x03f8, B:91:0x0405, B:93:0x0416, B:94:0x0439, B:96:0x043f, B:97:0x0464, B:98:0x0469, B:100:0x047a, B:102:0x049d, B:104:0x04a3, B:105:0x04c8, B:106:0x04d1, B:108:0x04e2, B:110:0x0505, B:112:0x050b, B:113:0x0530, B:115:0x0536, B:116:0x053f, B:118:0x0550, B:119:0x05a3, B:120:0x0573, B:122:0x0579, B:123:0x059e, B:125:0x05ac, B:127:0x05b2, B:129:0x05de, B:130:0x05d5, B:131:0x05db, B:133:0x05e7, B:135:0x05f4, B:137:0x0618, B:140:0x0640, B:142:0x0649, B:143:0x0646, B:145:0x0652, B:147:0x0660, B:149:0x068a, B:151:0x068e, B:153:0x0691, B:154:0x0698, B:157:0x069d, B:158:0x0683, B:159:0x069a, B:160:0x06a4, B:162:0x06aa, B:163:0x06d2, B:164:0x06cd, B:166:0x06db, B:168:0x06e1, B:170:0x0737, B:171:0x0704, B:173:0x070c, B:175:0x0713, B:176:0x0733, B:177:0x0734, B:178:0x073e, B:180:0x075c, B:181:0x0809, B:182:0x0782, B:184:0x0788, B:185:0x07ad, B:187:0x07b3, B:188:0x07d8, B:190:0x07e0, B:192:0x07e4, B:193:0x07eb, B:195:0x07f5, B:196:0x0806, B:197:0x0800, B:200:0x0810, B:201:0x0844, B:203:0x0845, B:204:0x0868, B:205:0x0869, B:206:0x0874, B:208:0x087a, B:209:0x08a2, B:210:0x089d, B:211:0x08a9, B:213:0x08af, B:214:0x08d7, B:215:0x08d2, B:216:0x08de, B:218:0x08e4, B:219:0x090c, B:220:0x0907, B:222:0x0915, B:224:0x091e, B:225:0x0949, B:227:0x0951, B:228:0x097c, B:230:0x098e, B:231:0x0998, B:232:0x09bb, B:233:0x09b8, B:235:0x09c4, B:237:0x09ca, B:239:0x09f6, B:240:0x09ed, B:241:0x09f3, B:243:0x09ff, B:244:0x0a06, B:247:0x0a14, B:249:0x0a35, B:253:0x0a5a, B:255:0x0a9a, B:258:0x0aa6, B:260:0x0ab2, B:261:0x0abc, B:263:0x0ac9, B:266:0x0ad7, B:268:0x0af8, B:272:0x0b1d, B:274:0x0b7c, B:277:0x0b88, B:279:0x0b94, B:281:0x0ba1, B:282:0x0bad, B:284:0x0bba, B:286:0x0bc6, B:288:0x0bcc, B:290:0x0bf8, B:291:0x0bef, B:292:0x0bf5, B:294:0x0c01, B:297:0x0c10, B:307:0x0c1d, B:300:0x0c4f, B:302:0x0c59, B:303:0x0c63, B:305:0x0c94, B:310:0x0c23, B:312:0x0c6c, B:313:0x0c91, B:315:0x0c9d, B:317:0x0ca6, B:319:0x0d07, B:320:0x0ccb, B:322:0x0cd1, B:323:0x0cfe, B:324:0x0d04, B:325:0x0d0e, B:327:0x0d17, B:329:0x0d23, B:331:0x0d7b, B:333:0x0d48, B:334:0x0d6d, B:335:0x0d78, B:337:0x0d84, B:339:0x0d8a, B:341:0x0db6, B:342:0x0dad, B:343:0x0db3, B:345:0x0dbf, B:347:0x0dc5, B:349:0x0df1, B:350:0x0de8, B:351:0x0dee, B:353:0x0dfa, B:355:0x0e02, B:357:0x0e30, B:358:0x0e27, B:359:0x0e2d, B:361:0x0e39, B:363:0x0e45, B:365:0x0e9f, B:366:0x0e68, B:368:0x0e72, B:371:0x0e7a, B:372:0x0e85, B:374:0x0e8d, B:377:0x0e98, B:379:0x0e9c, B:381:0x0ea8, B:383:0x0eb6, B:385:0x0ee6, B:386:0x0ed9, B:387:0x0ee3, B:389:0x0eef, B:391:0x0efb, B:393:0x0f55, B:394:0x0f1e, B:396:0x0f28, B:399:0x0f30, B:400:0x0f3b, B:402:0x0f43, B:405:0x0f4e, B:407:0x0f52, B:409:0x0f5e, B:411:0x0f64, B:413:0x0f90, B:414:0x0f87, B:415:0x0f8d, B:417:0x0f99, B:419:0x0f9f, B:421:0x0fcb, B:422:0x0fc2, B:423:0x0fc8, B:425:0x0fd4, B:427:0x0fda, B:429:0x1006, B:430:0x0ffd, B:431:0x1003, B:433:0x100f, B:435:0x1015, B:437:0x1041, B:438:0x1038, B:439:0x103e, B:441:0x104a, B:443:0x1050, B:445:0x107e, B:446:0x1075, B:447:0x107b, B:450:0x1089, B:454:0x10af, B:456:0x10dc, B:458:0x10d4, B:459:0x109b, B:460:0x10d8, B:463:0x10e8, B:465:0x110b, B:467:0x11ed, B:469:0x1131, B:471:0x1159, B:476:0x1165, B:480:0x116f, B:484:0x117e, B:486:0x1191, B:487:0x119f, B:488:0x1197, B:489:0x11ae, B:491:0x11bf, B:493:0x11c9, B:495:0x11d3, B:496:0x11e0, B:499:0x11e8, B:502:0x11f6, B:503:0x11fb, B:506:0x1209, B:508:0x1210, B:509:0x1237, B:511:0x1241, B:512:0x126a, B:514:0x1270, B:515:0x12a1, B:517:0x12af, B:518:0x12b9, B:519:0x12c0, B:520:0x12bd, B:523:0x12cb, B:525:0x12d2, B:526:0x12f8, B:527:0x1300, B:530:0x130b, B:532:0x1314, B:534:0x1349, B:535:0x1321, B:536:0x1346, B:537:0x0398, B:728:0x1378, B:732:0x1383, B:737:0x136d, B:738:0x1376), top: B:5:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:495:0x11d3 A[Catch: all -> 0x036f, IOException -> 0x0373, XmlPullParserException -> 0x0377, TryCatch #10 {all -> 0x036f, blocks: (B:68:0x035a, B:70:0x0360, B:72:0x0366, B:77:0x037f, B:79:0x0385, B:81:0x038b, B:83:0x03c5, B:85:0x03cb, B:87:0x03d5, B:89:0x03f8, B:91:0x0405, B:93:0x0416, B:94:0x0439, B:96:0x043f, B:97:0x0464, B:98:0x0469, B:100:0x047a, B:102:0x049d, B:104:0x04a3, B:105:0x04c8, B:106:0x04d1, B:108:0x04e2, B:110:0x0505, B:112:0x050b, B:113:0x0530, B:115:0x0536, B:116:0x053f, B:118:0x0550, B:119:0x05a3, B:120:0x0573, B:122:0x0579, B:123:0x059e, B:125:0x05ac, B:127:0x05b2, B:129:0x05de, B:130:0x05d5, B:131:0x05db, B:133:0x05e7, B:135:0x05f4, B:137:0x0618, B:140:0x0640, B:142:0x0649, B:143:0x0646, B:145:0x0652, B:147:0x0660, B:149:0x068a, B:151:0x068e, B:153:0x0691, B:154:0x0698, B:157:0x069d, B:158:0x0683, B:159:0x069a, B:160:0x06a4, B:162:0x06aa, B:163:0x06d2, B:164:0x06cd, B:166:0x06db, B:168:0x06e1, B:170:0x0737, B:171:0x0704, B:173:0x070c, B:175:0x0713, B:176:0x0733, B:177:0x0734, B:178:0x073e, B:180:0x075c, B:181:0x0809, B:182:0x0782, B:184:0x0788, B:185:0x07ad, B:187:0x07b3, B:188:0x07d8, B:190:0x07e0, B:192:0x07e4, B:193:0x07eb, B:195:0x07f5, B:196:0x0806, B:197:0x0800, B:200:0x0810, B:201:0x0844, B:203:0x0845, B:204:0x0868, B:205:0x0869, B:206:0x0874, B:208:0x087a, B:209:0x08a2, B:210:0x089d, B:211:0x08a9, B:213:0x08af, B:214:0x08d7, B:215:0x08d2, B:216:0x08de, B:218:0x08e4, B:219:0x090c, B:220:0x0907, B:222:0x0915, B:224:0x091e, B:225:0x0949, B:227:0x0951, B:228:0x097c, B:230:0x098e, B:231:0x0998, B:232:0x09bb, B:233:0x09b8, B:235:0x09c4, B:237:0x09ca, B:239:0x09f6, B:240:0x09ed, B:241:0x09f3, B:243:0x09ff, B:244:0x0a06, B:247:0x0a14, B:249:0x0a35, B:253:0x0a5a, B:255:0x0a9a, B:258:0x0aa6, B:260:0x0ab2, B:261:0x0abc, B:263:0x0ac9, B:266:0x0ad7, B:268:0x0af8, B:272:0x0b1d, B:274:0x0b7c, B:277:0x0b88, B:279:0x0b94, B:281:0x0ba1, B:282:0x0bad, B:284:0x0bba, B:286:0x0bc6, B:288:0x0bcc, B:290:0x0bf8, B:291:0x0bef, B:292:0x0bf5, B:294:0x0c01, B:297:0x0c10, B:307:0x0c1d, B:300:0x0c4f, B:302:0x0c59, B:303:0x0c63, B:305:0x0c94, B:310:0x0c23, B:312:0x0c6c, B:313:0x0c91, B:315:0x0c9d, B:317:0x0ca6, B:319:0x0d07, B:320:0x0ccb, B:322:0x0cd1, B:323:0x0cfe, B:324:0x0d04, B:325:0x0d0e, B:327:0x0d17, B:329:0x0d23, B:331:0x0d7b, B:333:0x0d48, B:334:0x0d6d, B:335:0x0d78, B:337:0x0d84, B:339:0x0d8a, B:341:0x0db6, B:342:0x0dad, B:343:0x0db3, B:345:0x0dbf, B:347:0x0dc5, B:349:0x0df1, B:350:0x0de8, B:351:0x0dee, B:353:0x0dfa, B:355:0x0e02, B:357:0x0e30, B:358:0x0e27, B:359:0x0e2d, B:361:0x0e39, B:363:0x0e45, B:365:0x0e9f, B:366:0x0e68, B:368:0x0e72, B:371:0x0e7a, B:372:0x0e85, B:374:0x0e8d, B:377:0x0e98, B:379:0x0e9c, B:381:0x0ea8, B:383:0x0eb6, B:385:0x0ee6, B:386:0x0ed9, B:387:0x0ee3, B:389:0x0eef, B:391:0x0efb, B:393:0x0f55, B:394:0x0f1e, B:396:0x0f28, B:399:0x0f30, B:400:0x0f3b, B:402:0x0f43, B:405:0x0f4e, B:407:0x0f52, B:409:0x0f5e, B:411:0x0f64, B:413:0x0f90, B:414:0x0f87, B:415:0x0f8d, B:417:0x0f99, B:419:0x0f9f, B:421:0x0fcb, B:422:0x0fc2, B:423:0x0fc8, B:425:0x0fd4, B:427:0x0fda, B:429:0x1006, B:430:0x0ffd, B:431:0x1003, B:433:0x100f, B:435:0x1015, B:437:0x1041, B:438:0x1038, B:439:0x103e, B:441:0x104a, B:443:0x1050, B:445:0x107e, B:446:0x1075, B:447:0x107b, B:450:0x1089, B:454:0x10af, B:456:0x10dc, B:458:0x10d4, B:459:0x109b, B:460:0x10d8, B:463:0x10e8, B:465:0x110b, B:467:0x11ed, B:469:0x1131, B:471:0x1159, B:476:0x1165, B:480:0x116f, B:484:0x117e, B:486:0x1191, B:487:0x119f, B:488:0x1197, B:489:0x11ae, B:491:0x11bf, B:493:0x11c9, B:495:0x11d3, B:496:0x11e0, B:499:0x11e8, B:502:0x11f6, B:503:0x11fb, B:506:0x1209, B:508:0x1210, B:509:0x1237, B:511:0x1241, B:512:0x126a, B:514:0x1270, B:515:0x12a1, B:517:0x12af, B:518:0x12b9, B:519:0x12c0, B:520:0x12bd, B:523:0x12cb, B:525:0x12d2, B:526:0x12f8, B:527:0x1300, B:530:0x130b, B:532:0x1314, B:534:0x1349, B:535:0x1321, B:536:0x1346, B:537:0x0398, B:728:0x1378, B:732:0x1383, B:737:0x136d, B:738:0x1376), top: B:5:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:678:0x138f  */
    /* JADX WARN: Removed duplicated region for block: B:681:0x13a2  */
    /* JADX WARN: Removed duplicated region for block: B:684:0x13ad  */
    /* JADX WARN: Removed duplicated region for block: B:687:0x13be  */
    /* JADX WARN: Removed duplicated region for block: B:690:0x13cf  */
    /* JADX WARN: Removed duplicated region for block: B:693:0x13dd  */
    /* JADX WARN: Removed duplicated region for block: B:702:0x1408 A[LOOP:2: B:700:0x1402->B:702:0x1408, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:705:0x13b3  */
    /* JADX WARN: Removed duplicated region for block: B:706:0x139b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void readPermissionsFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser, java.io.File file, int i) {
        java.io.FileReader fileReader;
        java.io.FileReader fileReader2;
        java.lang.Throwable th;
        org.xmlpull.v1.XmlPullParserException xmlPullParserException;
        java.io.IOException iOException;
        int i2;
        int version;
        java.util.Iterator<java.lang.String> it;
        int next;
        int i3;
        char c;
        boolean z;
        boolean z2;
        boolean z3;
        boolean exists;
        int parseInt;
        boolean z4;
        boolean z5;
        try {
            java.io.FileReader fileReader3 = new java.io.FileReader(file);
            android.util.Slog.i(TAG, "Reading permissions from " + file);
            boolean isLowRamDeviceStatic = android.app.ActivityManager.isLowRamDeviceStatic();
            try {
                try {
                    xmlPullParser.setInput(fileReader3);
                } catch (java.io.IOException e) {
                    e = e;
                    fileReader2 = fileReader3;
                } catch (org.xmlpull.v1.XmlPullParserException e2) {
                    e = e2;
                    fileReader2 = fileReader3;
                } catch (java.lang.Throwable th2) {
                    th = th2;
                    fileReader = fileReader3;
                    th = th;
                    libcore.io.IoUtils.closeQuietly(fileReader);
                    throw th;
                }
                try {
                    do {
                        next = xmlPullParser.next();
                        i3 = 1;
                        if (next != 2) {
                        }
                        break;
                    } while (next != 1);
                    break;
                    if (next != 2) {
                        throw new org.xmlpull.v1.XmlPullParserException("No start tag found");
                    }
                    if (!xmlPullParser.getName().equals("permissions")) {
                        try {
                            if (!xmlPullParser.getName().equals("config")) {
                                throw new org.xmlpull.v1.XmlPullParserException("Unexpected start tag in " + file + ": found " + xmlPullParser.getName() + ", expected 'permissions' or 'config'");
                            }
                        } catch (java.io.IOException e3) {
                            iOException = e3;
                            fileReader2 = fileReader3;
                            android.util.Slog.w(TAG, "Got exception parsing permissions.", iOException);
                            libcore.io.IoUtils.closeQuietly(fileReader2);
                            if (android.os.storage.StorageManager.isFileEncrypted()) {
                            }
                            if (android.os.storage.StorageManager.hasAdoptable()) {
                            }
                            if (android.app.ActivityManager.isLowRamDeviceStatic()) {
                            }
                            version = android.os.incremental.IncrementalManager.getVersion();
                            if (version > 0) {
                            }
                            addFeature("android.software.app_enumeration", 0);
                            if (android.os.Build.VERSION.DEVICE_INITIAL_SDK_INT >= 29) {
                            }
                            enableIpSecTunnelMigrationOnVsrUAndAbove();
                            if (isErofsSupported()) {
                            }
                            it = this.mUnavailableFeatures.iterator();
                            while (it.hasNext()) {
                            }
                        } catch (org.xmlpull.v1.XmlPullParserException e4) {
                            xmlPullParserException = e4;
                            fileReader2 = fileReader3;
                            android.util.Slog.w(TAG, "Got exception parsing permissions.", xmlPullParserException);
                            libcore.io.IoUtils.closeQuietly(fileReader2);
                            if (android.os.storage.StorageManager.isFileEncrypted()) {
                            }
                            if (android.os.storage.StorageManager.hasAdoptable()) {
                            }
                            if (android.app.ActivityManager.isLowRamDeviceStatic()) {
                            }
                            version = android.os.incremental.IncrementalManager.getVersion();
                            if (version > 0) {
                            }
                            addFeature("android.software.app_enumeration", 0);
                            if (android.os.Build.VERSION.DEVICE_INITIAL_SDK_INT >= 29) {
                            }
                            enableIpSecTunnelMigrationOnVsrUAndAbove();
                            if (isErofsSupported()) {
                            }
                            it = this.mUnavailableFeatures.iterator();
                            while (it.hasNext()) {
                            }
                        } catch (java.lang.Throwable th3) {
                            th = th3;
                            fileReader = fileReader3;
                            libcore.io.IoUtils.closeQuietly(fileReader);
                            throw th;
                        }
                    }
                    boolean z6 = i == -1;
                    boolean z7 = (i & 2) != 0;
                    boolean z8 = (i & 1) != 0;
                    boolean z9 = (i & 4) != 0;
                    boolean z10 = (i & 8) != 0;
                    boolean z11 = (i & 16) != 0;
                    boolean z12 = (i & 2048) != 0;
                    boolean z13 = (i & 32) != 0;
                    boolean z14 = (i & 64) != 0;
                    boolean z15 = (i & 128) != 0;
                    boolean z16 = (i & 256) != 0;
                    boolean z17 = (i & 512) != 0;
                    boolean z18 = (i & 1024) != 0;
                    while (true) {
                        com.android.internal.util.XmlUtils.nextElement(xmlPullParser);
                        if (xmlPullParser.getEventType() != i3) {
                            java.lang.String name = xmlPullParser.getName();
                            if (name != null) {
                                switch (name.hashCode()) {
                                    case -2110956849:
                                        if (name.equals("enhanced-confirmation-trusted-package")) {
                                            c = '+';
                                            break;
                                        }
                                        break;
                                    case -2040330235:
                                        if (name.equals("allow-unthrottled-location")) {
                                            c = 11;
                                            break;
                                        }
                                        break;
                                    case -1882490007:
                                        if (name.equals("allow-in-power-save")) {
                                            c = '\t';
                                            break;
                                        }
                                        break;
                                    case -1582324217:
                                        if (name.equals("allow-adas-location-settings")) {
                                            c = '\f';
                                            break;
                                        }
                                        break;
                                    case -1554938271:
                                        if (name.equals("named-actor")) {
                                            c = ' ';
                                            break;
                                        }
                                        break;
                                    case -1461465444:
                                        if (name.equals("component-override")) {
                                            c = 19;
                                            break;
                                        }
                                        break;
                                    case -1390350881:
                                        if (name.equals("install-constraints-allowed")) {
                                            c = '%';
                                            break;
                                        }
                                        break;
                                    case -1005864890:
                                        if (name.equals("disabled-until-used-preinstalled-carrier-app")) {
                                            c = 22;
                                            break;
                                        }
                                        break;
                                    case -980620291:
                                        if (name.equals("allow-association")) {
                                            c = 27;
                                            break;
                                        }
                                        break;
                                    case -979207434:
                                        if (name.equals("feature")) {
                                            c = 6;
                                            break;
                                        }
                                        break;
                                    case -828905863:
                                        if (name.equals("unavailable-feature")) {
                                            c = 7;
                                            break;
                                        }
                                        break;
                                    case -642819164:
                                        if (name.equals("allow-in-power-save-except-idle")) {
                                            c = '\b';
                                            break;
                                        }
                                        break;
                                    case -634266752:
                                        if (name.equals("bg-restriction-exemption")) {
                                            c = 17;
                                            break;
                                        }
                                        break;
                                    case -625731345:
                                        if (name.equals("asl-file")) {
                                            c = ')';
                                            break;
                                        }
                                        break;
                                    case -560717308:
                                        if (name.equals("allow-ignore-location-settings")) {
                                            c = 14;
                                            break;
                                        }
                                        break;
                                    case -517618225:
                                        if (name.equals("permission")) {
                                            c = 1;
                                            break;
                                        }
                                        break;
                                    case -150068154:
                                        if (name.equals("install-in-user-type")) {
                                            c = 31;
                                            break;
                                        }
                                        break;
                                    case -5781715:
                                        if (name.equals("camera-privacy-allowlisted-app")) {
                                            c = '\r';
                                            break;
                                        }
                                        break;
                                    case 72567535:
                                        if (name.equals("signature-permissions")) {
                                            c = 24;
                                            break;
                                        }
                                        break;
                                    case 98629247:
                                        if (name.equals("group")) {
                                            c = 0;
                                            break;
                                        }
                                        break;
                                    case 166208699:
                                        if (name.equals("library")) {
                                            c = 5;
                                            break;
                                        }
                                        break;
                                    case 176950812:
                                        if (name.equals("require-strict-signature")) {
                                            c = '*';
                                            break;
                                        }
                                        break;
                                    case 180165796:
                                        if (name.equals("hidden-api-whitelisted-app")) {
                                            c = 26;
                                            break;
                                        }
                                        break;
                                    case 347247519:
                                        if (name.equals("backup-transport-whitelisted-service")) {
                                            c = 20;
                                            break;
                                        }
                                        break;
                                    case 357860339:
                                        if (name.equals("prevent-disable")) {
                                            c = 30;
                                            break;
                                        }
                                        break;
                                    case 414198242:
                                        if (name.equals("allowed-vendor-apex")) {
                                            c = '$';
                                            break;
                                        }
                                        break;
                                    case 783200107:
                                        if (name.equals("update-ownership")) {
                                            c = '&';
                                            break;
                                        }
                                        break;
                                    case 802332808:
                                        if (name.equals("allow-in-data-usage-save")) {
                                            c = '\n';
                                            break;
                                        }
                                        break;
                                    case 953292141:
                                        if (name.equals("assign-permission")) {
                                            c = 2;
                                            break;
                                        }
                                        break;
                                    case 968751633:
                                        if (name.equals("rollback-whitelisted-app")) {
                                            c = '\"';
                                            break;
                                        }
                                        break;
                                    case 1005096720:
                                        if (name.equals("apex-library")) {
                                            c = 4;
                                            break;
                                        }
                                        break;
                                    case 1044015374:
                                        if (name.equals("oem-permissions")) {
                                            c = 25;
                                            break;
                                        }
                                        break;
                                    case 1046683496:
                                        if (name.equals("whitelisted-staged-installer")) {
                                            c = '#';
                                            break;
                                        }
                                        break;
                                    case 1116987345:
                                        if (name.equals("enhanced-confirmation-trusted-installer")) {
                                            c = ',';
                                            break;
                                        }
                                        break;
                                    case 1121420326:
                                        if (name.equals("app-link")) {
                                            c = 16;
                                            break;
                                        }
                                        break;
                                    case 1269564002:
                                        if (name.equals("split-permission")) {
                                            c = 3;
                                            break;
                                        }
                                        break;
                                    case 1347585732:
                                        if (name.equals("app-data-isolation-whitelisted-app")) {
                                            c = 28;
                                            break;
                                        }
                                        break;
                                    case 1567330472:
                                        if (name.equals("default-enabled-vr-app")) {
                                            c = 18;
                                            break;
                                        }
                                        break;
                                    case 1633270165:
                                        if (name.equals("disabled-until-used-preinstalled-carrier-associated-app")) {
                                            c = 21;
                                            break;
                                        }
                                        break;
                                    case 1723146313:
                                        if (name.equals("privapp-permissions")) {
                                            c = 23;
                                            break;
                                        }
                                        break;
                                    case 1723586945:
                                        if (name.equals("bugreport-whitelisted")) {
                                            c = 29;
                                            break;
                                        }
                                        break;
                                    case 1748566401:
                                        if (name.equals("initial-package-state")) {
                                            c = '\'';
                                            break;
                                        }
                                        break;
                                    case 1793277898:
                                        if (name.equals("overlay-config-signature")) {
                                            c = '!';
                                            break;
                                        }
                                        break;
                                    case 1954925533:
                                        if (name.equals("allow-implicit-broadcast")) {
                                            c = 15;
                                            break;
                                        }
                                        break;
                                    case 2011364224:
                                        if (name.equals("allow-package-shareduid")) {
                                            c = '(';
                                            break;
                                        }
                                        break;
                                }
                                c = 65535;
                                java.io.FileReader fileReader4 = fileReader3;
                                boolean z19 = isLowRamDeviceStatic;
                                boolean z20 = z17;
                                boolean z21 = z16;
                                boolean z22 = z12;
                                boolean z23 = z15;
                                java.lang.String str = null;
                                switch (c) {
                                    case 0:
                                        z = z18;
                                        if (z6) {
                                            java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "gid");
                                            if (attributeValue != null) {
                                                this.mGlobalGids = com.android.internal.util.ArrayUtils.appendInt(this.mGlobalGids, android.os.Process.getGidForName(attributeValue));
                                            } else {
                                                android.util.Slog.w(TAG, "<" + name + "> without gid in " + file + " at " + xmlPullParser.getPositionDescription());
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 1:
                                        z = z18;
                                        if (z9) {
                                            java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "name");
                                            if (attributeValue2 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without name in " + file + " at " + xmlPullParser.getPositionDescription());
                                                com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                            } else {
                                                readPermission(xmlPullParser, attributeValue2.intern());
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        }
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 2:
                                        z = z18;
                                        if (z9) {
                                            java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "name");
                                            if (attributeValue3 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without name in " + file + " at " + xmlPullParser.getPositionDescription());
                                                com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                            } else {
                                                java.lang.String attributeValue4 = xmlPullParser.getAttributeValue(null, com.android.server.net.watchlist.WatchlistLoggingHandler.WatchlistEventKeys.UID);
                                                if (attributeValue4 == null) {
                                                    android.util.Slog.w(TAG, "<" + name + "> without uid in " + file + " at " + xmlPullParser.getPositionDescription());
                                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                                } else {
                                                    int uidForName = android.os.Process.getUidForName(attributeValue4);
                                                    if (uidForName < 0) {
                                                        android.util.Slog.w(TAG, "<" + name + "> with unknown uid \"" + attributeValue4 + "  in " + file + " at " + xmlPullParser.getPositionDescription());
                                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                                    } else {
                                                        java.lang.String intern = attributeValue3.intern();
                                                        android.util.ArraySet<java.lang.String> arraySet = this.mSystemPermissions.get(uidForName);
                                                        if (arraySet == null) {
                                                            arraySet = new android.util.ArraySet<>();
                                                            this.mSystemPermissions.put(uidForName, arraySet);
                                                        }
                                                        arraySet.add(intern);
                                                    }
                                                }
                                            }
                                            fileReader3 = fileReader4;
                                            isLowRamDeviceStatic = z19;
                                            z17 = z20;
                                            z16 = z21;
                                            z12 = z22;
                                            z15 = z23;
                                            z18 = z;
                                            i3 = 1;
                                            break;
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                    case 3:
                                        z = z18;
                                        if (z9) {
                                            readSplitPermission(xmlPullParser, file);
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        }
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 4:
                                    case 5:
                                        if (z7) {
                                            java.lang.String attributeValue5 = xmlPullParser.getAttributeValue(null, "name");
                                            java.lang.String attributeValue6 = xmlPullParser.getAttributeValue(null, "file");
                                            java.lang.String attributeValue7 = xmlPullParser.getAttributeValue(null, "dependency");
                                            java.lang.String attributeValue8 = xmlPullParser.getAttributeValue(null, "min-device-sdk");
                                            z = z18;
                                            java.lang.String attributeValue9 = xmlPullParser.getAttributeValue(null, "max-device-sdk");
                                            if (attributeValue5 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without name in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else if (attributeValue6 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without file in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                if (attributeValue8 != null && !isAtLeastSdkLevel(attributeValue8)) {
                                                    z2 = false;
                                                    if (attributeValue9 != null && !isAtMostSdkLevel(attributeValue9)) {
                                                        z3 = false;
                                                        exists = new java.io.File(attributeValue6).exists();
                                                        if (!z2 && z3 && exists) {
                                                            this.mSharedLibraries.put(attributeValue5, new com.android.server.SystemConfig.SharedLibraryEntry(attributeValue5, attributeValue6, attributeValue7 == null ? new java.lang.String[0] : attributeValue7.split(":"), xmlPullParser.getAttributeValue(null, "on-bootclasspath-since"), xmlPullParser.getAttributeValue(null, "on-bootclasspath-before")));
                                                        } else {
                                                            java.lang.StringBuilder sb = new java.lang.StringBuilder("Ignore shared library ");
                                                            sb.append(attributeValue5);
                                                            sb.append(":");
                                                            if (!z2) {
                                                                sb.append(" min-device-sdk=");
                                                                sb.append(attributeValue8);
                                                            }
                                                            if (!z3) {
                                                                sb.append(" max-device-sdk=");
                                                                sb.append(attributeValue9);
                                                            }
                                                            if (!exists) {
                                                                sb.append(" ");
                                                                sb.append(attributeValue6);
                                                                sb.append(" does not exist");
                                                            }
                                                            android.util.Slog.i(TAG, sb.toString());
                                                        }
                                                    }
                                                    z3 = true;
                                                    exists = new java.io.File(attributeValue6).exists();
                                                    if (!z2) {
                                                    }
                                                    java.lang.StringBuilder sb2 = new java.lang.StringBuilder("Ignore shared library ");
                                                    sb2.append(attributeValue5);
                                                    sb2.append(":");
                                                    if (!z2) {
                                                    }
                                                    if (!z3) {
                                                    }
                                                    if (!exists) {
                                                    }
                                                    android.util.Slog.i(TAG, sb2.toString());
                                                }
                                                z2 = true;
                                                if (attributeValue9 != null) {
                                                    z3 = false;
                                                    exists = new java.io.File(attributeValue6).exists();
                                                    if (!z2) {
                                                    }
                                                    java.lang.StringBuilder sb22 = new java.lang.StringBuilder("Ignore shared library ");
                                                    sb22.append(attributeValue5);
                                                    sb22.append(":");
                                                    if (!z2) {
                                                    }
                                                    if (!z3) {
                                                    }
                                                    if (!exists) {
                                                    }
                                                    android.util.Slog.i(TAG, sb22.toString());
                                                }
                                                z3 = true;
                                                exists = new java.io.File(attributeValue6).exists();
                                                if (!z2) {
                                                }
                                                java.lang.StringBuilder sb222 = new java.lang.StringBuilder("Ignore shared library ");
                                                sb222.append(attributeValue5);
                                                sb222.append(":");
                                                if (!z2) {
                                                }
                                                if (!z3) {
                                                }
                                                if (!exists) {
                                                }
                                                android.util.Slog.i(TAG, sb222.toString());
                                            }
                                        } else {
                                            z = z18;
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 6:
                                        if (z8) {
                                            java.lang.String attributeValue10 = xmlPullParser.getAttributeValue(null, "name");
                                            int readIntAttribute = com.android.internal.util.XmlUtils.readIntAttribute(xmlPullParser, "version", 0);
                                            boolean z24 = !z19 ? true : !"true".equals(xmlPullParser.getAttributeValue(null, "notLowRam"));
                                            if (attributeValue10 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without name in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else if (z24) {
                                                addFeature(attributeValue10, readIntAttribute);
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 7:
                                        if (z8) {
                                            java.lang.String attributeValue11 = xmlPullParser.getAttributeValue(null, "name");
                                            if (attributeValue11 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without name in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mUnavailableFeatures.add(attributeValue11);
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '\b':
                                        if (z21) {
                                            java.lang.String attributeValue12 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            if (attributeValue12 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mAllowInPowerSaveExceptIdle.add(attributeValue12);
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '\t':
                                        if (z21) {
                                            java.lang.String attributeValue13 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            if (attributeValue13 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mAllowInPowerSave.add(attributeValue13);
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '\n':
                                        if (z21) {
                                            java.lang.String attributeValue14 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            if (attributeValue14 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mAllowInDataUsageSave.add(attributeValue14);
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 11:
                                        if (z21) {
                                            java.lang.String attributeValue15 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            if (attributeValue15 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mAllowUnthrottledLocation.add(attributeValue15);
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '\f':
                                        if (z21) {
                                            java.lang.String attributeValue16 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            java.lang.String attributeValue17 = xmlPullParser.getAttributeValue(null, "attributionTag");
                                            if (attributeValue16 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                android.util.ArraySet<java.lang.String> arraySet2 = this.mAllowAdasSettings.get(attributeValue16);
                                                if (arraySet2 == null || !arraySet2.isEmpty()) {
                                                    if (arraySet2 == null) {
                                                        arraySet2 = new android.util.ArraySet<>(1);
                                                        this.mAllowAdasSettings.put(attributeValue16, arraySet2);
                                                    }
                                                    if (!com.android.server.am.SettingsToPropertiesMapper.NAMESPACE_REBOOT_STAGING_DELIMITER.equals(attributeValue17)) {
                                                        if (!"null".equals(attributeValue17)) {
                                                            str = attributeValue17;
                                                        }
                                                        arraySet2.add(str);
                                                    }
                                                }
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '\r':
                                        if (z21) {
                                            java.lang.String attributeValue18 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            boolean readBooleanAttribute = com.android.internal.util.XmlUtils.readBooleanAttribute(xmlPullParser, "mandatory", false);
                                            if (attributeValue18 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mAllowlistCameraPrivacy.put(attributeValue18, java.lang.Boolean.valueOf(readBooleanAttribute));
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 14:
                                        if (z21) {
                                            java.lang.String attributeValue19 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            java.lang.String attributeValue20 = xmlPullParser.getAttributeValue(null, "attributionTag");
                                            if (attributeValue19 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                android.util.ArraySet<java.lang.String> arraySet3 = this.mAllowIgnoreLocationSettings.get(attributeValue19);
                                                if (arraySet3 == null || !arraySet3.isEmpty()) {
                                                    if (arraySet3 == null) {
                                                        arraySet3 = new android.util.ArraySet<>(1);
                                                        this.mAllowIgnoreLocationSettings.put(attributeValue19, arraySet3);
                                                    }
                                                    if (!com.android.server.am.SettingsToPropertiesMapper.NAMESPACE_REBOOT_STAGING_DELIMITER.equals(attributeValue20)) {
                                                        if (!"null".equals(attributeValue20)) {
                                                            str = attributeValue20;
                                                        }
                                                        arraySet3.add(str);
                                                    }
                                                }
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 15:
                                        if (z20) {
                                            java.lang.String attributeValue21 = xmlPullParser.getAttributeValue(null, com.android.server.pm.verify.domain.DomainVerificationPersistence.ATTR_ACTION);
                                            if (attributeValue21 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without action in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mAllowImplicitBroadcasts.add(attributeValue21);
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 16:
                                        if (z10) {
                                            java.lang.String attributeValue22 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            if (attributeValue22 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mLinkedApps.add(attributeValue22);
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 17:
                                        if (z21) {
                                            java.lang.String attributeValue23 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            if (attributeValue23 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mBgRestrictionExemption.add(attributeValue23);
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 18:
                                        if (z10) {
                                            java.lang.String attributeValue24 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            java.lang.String attributeValue25 = xmlPullParser.getAttributeValue(null, "class");
                                            if (attributeValue24 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else if (attributeValue25 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without class in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mDefaultVrComponents.add(new android.content.ComponentName(attributeValue24, attributeValue25));
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 19:
                                        readComponentOverrides(xmlPullParser, file);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 20:
                                        if (z8) {
                                            java.lang.String attributeValue26 = xmlPullParser.getAttributeValue(null, com.android.server.am.HostingRecord.HOSTING_TYPE_SERVICE);
                                            if (attributeValue26 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without service in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                android.content.ComponentName unflattenFromString = android.content.ComponentName.unflattenFromString(attributeValue26);
                                                if (unflattenFromString == null) {
                                                    android.util.Slog.w(TAG, "<" + name + "> with invalid service name " + attributeValue26 + " in " + file + " at " + xmlPullParser.getPositionDescription());
                                                } else {
                                                    this.mBackupTransportWhitelist.add(unflattenFromString);
                                                }
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 21:
                                        if (z10) {
                                            java.lang.String attributeValue27 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            java.lang.String attributeValue28 = xmlPullParser.getAttributeValue(null, "carrierAppPackage");
                                            if (attributeValue27 == null || attributeValue28 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package or carrierAppPackage in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                java.lang.String attributeValue29 = xmlPullParser.getAttributeValue(null, "addedInSdk");
                                                if (android.text.TextUtils.isEmpty(attributeValue29)) {
                                                    parseInt = -1;
                                                } else {
                                                    try {
                                                        parseInt = java.lang.Integer.parseInt(attributeValue29);
                                                    } catch (java.lang.NumberFormatException e5) {
                                                        android.util.Slog.w(TAG, "<" + name + "> addedInSdk not an integer in " + file + " at " + xmlPullParser.getPositionDescription());
                                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                                        z = z18;
                                                    }
                                                }
                                                java.util.List<android.os.CarrierAssociatedAppEntry> list = this.mDisabledUntilUsedPreinstalledCarrierAssociatedApps.get(attributeValue28);
                                                if (list == null) {
                                                    list = new java.util.ArrayList<>();
                                                    this.mDisabledUntilUsedPreinstalledCarrierAssociatedApps.put(attributeValue28, list);
                                                }
                                                list.add(new android.os.CarrierAssociatedAppEntry(attributeValue27, parseInt));
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 22:
                                        if (z10) {
                                            java.lang.String attributeValue30 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            if (attributeValue30 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mDisabledUntilUsedPreinstalledCarrierApps.add(attributeValue30);
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 23:
                                        if (z11) {
                                            if (!file.toPath().startsWith(android.os.Environment.getVendorDirectory().toPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER)) {
                                                if (!file.toPath().startsWith(android.os.Environment.getOdmDirectory().toPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER)) {
                                                    z4 = false;
                                                    boolean startsWith = file.toPath().startsWith(android.os.Environment.getProductDirectory().toPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                                                    boolean startsWith2 = file.toPath().startsWith(android.os.Environment.getSystemExtDirectory().toPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                                                    boolean startsWith3 = file.toPath().startsWith(android.os.Environment.getApexDirectory().toPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                                                    if (!z4) {
                                                        readPrivAppPermissions(xmlPullParser, this.mPermissionAllowlist.getVendorPrivilegedAppAllowlist());
                                                    } else if (startsWith) {
                                                        readPrivAppPermissions(xmlPullParser, this.mPermissionAllowlist.getProductPrivilegedAppAllowlist());
                                                    } else if (startsWith2) {
                                                        readPrivAppPermissions(xmlPullParser, this.mPermissionAllowlist.getSystemExtPrivilegedAppAllowlist());
                                                    } else if (startsWith3) {
                                                        readApexPrivAppPermissions(xmlPullParser, file, android.os.Environment.getApexDirectory().toPath());
                                                    } else {
                                                        readPrivAppPermissions(xmlPullParser, this.mPermissionAllowlist.getPrivilegedAppAllowlist());
                                                    }
                                                    z = z18;
                                                }
                                            }
                                            z4 = true;
                                            boolean startsWith4 = file.toPath().startsWith(android.os.Environment.getProductDirectory().toPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                                            boolean startsWith22 = file.toPath().startsWith(android.os.Environment.getSystemExtDirectory().toPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                                            boolean startsWith32 = file.toPath().startsWith(android.os.Environment.getApexDirectory().toPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                                            if (!z4) {
                                            }
                                            z = z18;
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                            z = z18;
                                        }
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 24:
                                        if (z22) {
                                            if (!file.toPath().startsWith(android.os.Environment.getVendorDirectory().toPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER)) {
                                                if (!file.toPath().startsWith(android.os.Environment.getOdmDirectory().toPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER)) {
                                                    z5 = false;
                                                    boolean startsWith5 = file.toPath().startsWith(android.os.Environment.getProductDirectory().toPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                                                    boolean startsWith6 = file.toPath().startsWith(android.os.Environment.getSystemExtDirectory().toPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                                                    if (!z5) {
                                                        readSignatureAppPermissions(xmlPullParser, this.mPermissionAllowlist.getVendorSignatureAppAllowlist());
                                                    } else if (startsWith5) {
                                                        readSignatureAppPermissions(xmlPullParser, this.mPermissionAllowlist.getProductSignatureAppAllowlist());
                                                    } else if (startsWith6) {
                                                        readSignatureAppPermissions(xmlPullParser, this.mPermissionAllowlist.getSystemExtSignatureAppAllowlist());
                                                    } else {
                                                        readSignatureAppPermissions(xmlPullParser, this.mPermissionAllowlist.getSignatureAppAllowlist());
                                                    }
                                                    z = z18;
                                                }
                                            }
                                            z5 = true;
                                            boolean startsWith52 = file.toPath().startsWith(android.os.Environment.getProductDirectory().toPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                                            boolean startsWith62 = file.toPath().startsWith(android.os.Environment.getSystemExtDirectory().toPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER);
                                            if (!z5) {
                                            }
                                            z = z18;
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                            z = z18;
                                        }
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 25:
                                        if (z13) {
                                            readOemPermissions(xmlPullParser);
                                            z = z18;
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                            z = z18;
                                        }
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 26:
                                        if (z14) {
                                            java.lang.String attributeValue31 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            if (attributeValue31 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mHiddenApiPackageWhitelist.add(attributeValue31);
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 27:
                                        if (z23) {
                                            java.lang.String attributeValue32 = xmlPullParser.getAttributeValue(null, "target");
                                            if (attributeValue32 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without target in " + file + " at " + xmlPullParser.getPositionDescription());
                                                com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                                z = z18;
                                            } else {
                                                java.lang.String attributeValue33 = xmlPullParser.getAttributeValue(null, "allowed");
                                                if (attributeValue33 == null) {
                                                    android.util.Slog.w(TAG, "<" + name + "> without allowed in " + file + " at " + xmlPullParser.getPositionDescription());
                                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                                    z = z18;
                                                } else {
                                                    java.lang.String intern2 = attributeValue32.intern();
                                                    java.lang.String intern3 = attributeValue33.intern();
                                                    android.util.ArraySet<java.lang.String> arraySet4 = this.mAllowedAssociations.get(intern2);
                                                    if (arraySet4 == null) {
                                                        arraySet4 = new android.util.ArraySet<>();
                                                        this.mAllowedAssociations.put(intern2, arraySet4);
                                                    }
                                                    android.util.Slog.i(TAG, "Adding association: " + intern2 + " <- " + intern3);
                                                    arraySet4.add(intern3);
                                                }
                                            }
                                            fileReader3 = fileReader4;
                                            isLowRamDeviceStatic = z19;
                                            z17 = z20;
                                            z16 = z21;
                                            z12 = z22;
                                            z15 = z23;
                                            z18 = z;
                                            i3 = 1;
                                            break;
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                    case 28:
                                        java.lang.String attributeValue34 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                        if (attributeValue34 == null) {
                                            android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mAppDataIsolationWhitelistedApps.add(attributeValue34);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 29:
                                        java.lang.String attributeValue35 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                        if (attributeValue35 == null) {
                                            android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mBugreportWhitelistedPackages.add(attributeValue35);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 30:
                                        java.lang.String attributeValue36 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                        if (attributeValue36 == null) {
                                            android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mPreventUserDisablePackages.add(attributeValue36);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case 31:
                                        readInstallInUserType(xmlPullParser, this.mPackageToUserTypeWhitelist, this.mPackageToUserTypeBlacklist);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case ' ':
                                        java.lang.String safeIntern = android.text.TextUtils.safeIntern(xmlPullParser.getAttributeValue(null, "namespace"));
                                        java.lang.String attributeValue37 = xmlPullParser.getAttributeValue(null, "name");
                                        java.lang.String safeIntern2 = android.text.TextUtils.safeIntern(xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE));
                                        if (android.text.TextUtils.isEmpty(safeIntern)) {
                                            android.util.Slog.wtf(TAG, "<" + name + "> without namespace in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else if (android.text.TextUtils.isEmpty(attributeValue37)) {
                                            android.util.Slog.wtf(TAG, "<" + name + "> without actor name in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else if (android.text.TextUtils.isEmpty(safeIntern2)) {
                                            android.util.Slog.wtf(TAG, "<" + name + "> without package name in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equalsIgnoreCase(safeIntern)) {
                                                throw new java.lang.IllegalStateException("Defining " + attributeValue37 + " as " + safeIntern2 + " for the android namespace is not allowed");
                                            }
                                            if (this.mNamedActors == null) {
                                                this.mNamedActors = new android.util.ArrayMap();
                                            }
                                            java.util.Map<java.lang.String, java.lang.String> map = this.mNamedActors.get(safeIntern);
                                            if (map == null) {
                                                map = new android.util.ArrayMap<>();
                                                this.mNamedActors.put(safeIntern, map);
                                            } else if (map.containsKey(attributeValue37)) {
                                                throw new java.lang.IllegalStateException("Duplicate actor definition for " + safeIntern + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + attributeValue37 + "; defined as both " + map.get(attributeValue37) + " and " + safeIntern2);
                                            }
                                            map.put(attributeValue37, safeIntern2);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '!':
                                        if (z6) {
                                            java.lang.String attributeValue38 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            if (attributeValue38 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                if (!android.text.TextUtils.isEmpty(this.mOverlayConfigSignaturePackage)) {
                                                    throw new java.lang.IllegalStateException("Reference signature package defined as both " + this.mOverlayConfigSignaturePackage + " and " + attributeValue38);
                                                }
                                                this.mOverlayConfigSignaturePackage = attributeValue38.intern();
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '\"':
                                        java.lang.String attributeValue39 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                        if (attributeValue39 == null) {
                                            android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mRollbackWhitelistedPackages.add(attributeValue39);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '#':
                                        if (z10) {
                                            java.lang.String attributeValue40 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            boolean readBooleanAttribute2 = com.android.internal.util.XmlUtils.readBooleanAttribute(xmlPullParser, "isModulesInstaller", false);
                                            if (attributeValue40 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mWhitelistedStagedInstallers.add(attributeValue40);
                                            }
                                            if (readBooleanAttribute2) {
                                                if (this.mModulesInstallerPackageName != null) {
                                                    throw new java.lang.IllegalStateException("Multiple modules installers");
                                                }
                                                this.mModulesInstallerPackageName = attributeValue40;
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '$':
                                        if (z18) {
                                            java.lang.String attributeValue41 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            java.lang.String attributeValue42 = xmlPullParser.getAttributeValue(null, "installerPackage");
                                            if (attributeValue41 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            }
                                            if (attributeValue42 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without installerPackage in " + file + " at " + xmlPullParser.getPositionDescription());
                                            }
                                            if (attributeValue41 != null && attributeValue42 != null) {
                                                this.mAllowedVendorApexes.put(attributeValue41, attributeValue42);
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '%':
                                        if (z10) {
                                            java.lang.String attributeValue43 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            if (attributeValue43 == null) {
                                                android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mInstallConstraintsAllowlist.add(attributeValue43);
                                            }
                                        } else {
                                            logNotAllowedInPartition(name, file, xmlPullParser);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '&':
                                        java.lang.String attributeValue44 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                        java.lang.String attributeValue45 = xmlPullParser.getAttributeValue(null, "installer");
                                        if (android.text.TextUtils.isEmpty(attributeValue44)) {
                                            android.util.Slog.w(TAG, "<" + name + "> without valid package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else if (android.text.TextUtils.isEmpty(attributeValue45)) {
                                            android.util.Slog.w(TAG, "<" + name + "> without valid installer in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mUpdateOwnersForSystemApps.put(attributeValue44, attributeValue45);
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '\'':
                                        java.lang.String attributeValue46 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                        java.lang.String attributeValue47 = xmlPullParser.getAttributeValue(null, "stopped");
                                        if (android.text.TextUtils.isEmpty(attributeValue46)) {
                                            android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else if (android.text.TextUtils.isEmpty(attributeValue47)) {
                                            android.util.Slog.w(TAG, "<" + name + "> without stopped in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else if (!java.lang.Boolean.parseBoolean(attributeValue47)) {
                                            this.mInitialNonStoppedSystemPackages.add(attributeValue46);
                                        }
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '(':
                                        java.lang.String attributeValue48 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                        java.lang.String attributeValue49 = xmlPullParser.getAttributeValue(null, "shareduid");
                                        if (android.text.TextUtils.isEmpty(attributeValue48)) {
                                            android.util.Slog.w(TAG, "<" + name + "> without package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else if (android.text.TextUtils.isEmpty(attributeValue49)) {
                                            android.util.Slog.w(TAG, "<" + name + "> without shareduid in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mPackageToSharedUidAllowList.put(attributeValue48, attributeValue49);
                                        }
                                    case ')':
                                        java.lang.String attributeValue50 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                        java.lang.String attributeValue51 = xmlPullParser.getAttributeValue(null, "path");
                                        if (android.text.TextUtils.isEmpty(attributeValue50)) {
                                            android.util.Slog.w(TAG, "<" + name + "> without valid package in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else if (android.text.TextUtils.isEmpty(attributeValue51)) {
                                            android.util.Slog.w(TAG, "<" + name + "> without valid path in " + file + " at " + xmlPullParser.getPositionDescription());
                                        } else {
                                            this.mAppMetadataFilePaths.put(attributeValue50, attributeValue51);
                                        }
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '*':
                                        if (android.security.Flags.extendVbChainToUpdatedApk()) {
                                            java.lang.String attributeValue52 = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
                                            if (android.text.TextUtils.isEmpty(attributeValue52)) {
                                                android.util.Slog.w(TAG, "<" + name + "> without valid package in " + file + " at " + xmlPullParser.getPositionDescription());
                                            } else {
                                                this.mPreinstallPackagesWithStrictSignatureCheck.add(attributeValue52);
                                            }
                                            z = z18;
                                        } else {
                                            z = z18;
                                        }
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                    case '+':
                                        if (android.permission.flags.Flags.enhancedConfirmationModeApisEnabled()) {
                                            android.content.pm.SignedPackage parseEnhancedConfirmationTrustedPackage = parseEnhancedConfirmationTrustedPackage(xmlPullParser, file, name);
                                            if (parseEnhancedConfirmationTrustedPackage != null) {
                                                this.mEnhancedConfirmationTrustedPackages.add(parseEnhancedConfirmationTrustedPackage);
                                                z = z18;
                                            } else {
                                                z = z18;
                                            }
                                            fileReader3 = fileReader4;
                                            isLowRamDeviceStatic = z19;
                                            z17 = z20;
                                            z16 = z21;
                                            z12 = z22;
                                            z15 = z23;
                                            z18 = z;
                                            i3 = 1;
                                            break;
                                        }
                                    case ',':
                                        if (android.permission.flags.Flags.enhancedConfirmationModeApisEnabled()) {
                                            android.content.pm.SignedPackage parseEnhancedConfirmationTrustedPackage2 = parseEnhancedConfirmationTrustedPackage(xmlPullParser, file, name);
                                            if (parseEnhancedConfirmationTrustedPackage2 != null) {
                                                this.mEnhancedConfirmationTrustedInstallers.add(parseEnhancedConfirmationTrustedPackage2);
                                                z = z18;
                                            } else {
                                                z = z18;
                                            }
                                            fileReader3 = fileReader4;
                                            isLowRamDeviceStatic = z19;
                                            z17 = z20;
                                            z16 = z21;
                                            z12 = z22;
                                            z15 = z23;
                                            z18 = z;
                                            i3 = 1;
                                            break;
                                        }
                                    default:
                                        android.util.Slog.w(TAG, "Tag " + name + " is unknown in " + file + " at " + xmlPullParser.getPositionDescription());
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                                        z = z18;
                                        fileReader3 = fileReader4;
                                        isLowRamDeviceStatic = z19;
                                        z17 = z20;
                                        z16 = z21;
                                        z12 = z22;
                                        z15 = z23;
                                        z18 = z;
                                        i3 = 1;
                                        break;
                                }
                            } else {
                                com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                            }
                        } else {
                            libcore.io.IoUtils.closeQuietly(fileReader3);
                        }
                    }
                } catch (java.io.IOException e6) {
                    e = e6;
                    iOException = e;
                    android.util.Slog.w(TAG, "Got exception parsing permissions.", iOException);
                    libcore.io.IoUtils.closeQuietly(fileReader2);
                    if (android.os.storage.StorageManager.isFileEncrypted()) {
                        i2 = 0;
                    } else {
                        i2 = 0;
                        addFeature("android.software.file_based_encryption", 0);
                        addFeature("android.software.securely_removes_users", 0);
                    }
                    if (android.os.storage.StorageManager.hasAdoptable()) {
                        addFeature("android.software.adoptable_storage", i2);
                    }
                    if (android.app.ActivityManager.isLowRamDeviceStatic()) {
                        addFeature("android.hardware.ram.normal", i2);
                    } else {
                        addFeature("android.hardware.ram.low", i2);
                    }
                    version = android.os.incremental.IncrementalManager.getVersion();
                    if (version > 0) {
                        addFeature("android.software.incremental_delivery", version);
                    }
                    addFeature("android.software.app_enumeration", 0);
                    if (android.os.Build.VERSION.DEVICE_INITIAL_SDK_INT >= 29) {
                        addFeature("android.software.ipsec_tunnels", 0);
                    }
                    enableIpSecTunnelMigrationOnVsrUAndAbove();
                    if (isErofsSupported()) {
                        if (isKernelVersionAtLeast(5, 10)) {
                            addFeature("android.software.erofs", 0);
                        } else if (isKernelVersionAtLeast(4, 19)) {
                            addFeature("android.software.erofs_legacy", 0);
                        }
                    }
                    it = this.mUnavailableFeatures.iterator();
                    while (it.hasNext()) {
                        removeFeature(it.next());
                    }
                } catch (org.xmlpull.v1.XmlPullParserException e7) {
                    e = e7;
                    xmlPullParserException = e;
                    android.util.Slog.w(TAG, "Got exception parsing permissions.", xmlPullParserException);
                    libcore.io.IoUtils.closeQuietly(fileReader2);
                    if (android.os.storage.StorageManager.isFileEncrypted()) {
                    }
                    if (android.os.storage.StorageManager.hasAdoptable()) {
                    }
                    if (android.app.ActivityManager.isLowRamDeviceStatic()) {
                    }
                    version = android.os.incremental.IncrementalManager.getVersion();
                    if (version > 0) {
                    }
                    addFeature("android.software.app_enumeration", 0);
                    if (android.os.Build.VERSION.DEVICE_INITIAL_SDK_INT >= 29) {
                    }
                    enableIpSecTunnelMigrationOnVsrUAndAbove();
                    if (isErofsSupported()) {
                    }
                    it = this.mUnavailableFeatures.iterator();
                    while (it.hasNext()) {
                    }
                }
            } catch (java.lang.Throwable th4) {
                th = th4;
                th = th;
                libcore.io.IoUtils.closeQuietly(fileReader);
                throw th;
            }
        } catch (java.io.FileNotFoundException e8) {
            android.util.Slog.w(TAG, "Couldn't find or open permissions file " + file);
        }
    }

    @android.annotation.Nullable
    private android.content.pm.SignedPackage parseEnhancedConfirmationTrustedPackage(org.xmlpull.v1.XmlPullParser xmlPullParser, java.io.File file, java.lang.String str) {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
        if (android.text.TextUtils.isEmpty(attributeValue)) {
            android.util.Slog.w(TAG, "<" + str + "> without package " + file + " at " + xmlPullParser.getPositionDescription());
            return null;
        }
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "sha256-cert-digest");
        if (android.text.TextUtils.isEmpty(attributeValue2)) {
            android.util.Slog.w(TAG, "<" + str + "> without sha256-cert-digest in " + file + " at " + xmlPullParser.getPositionDescription());
            return null;
        }
        try {
            return new android.content.pm.SignedPackage(attributeValue, new android.content.pm.Signature(attributeValue2.replace(":", "")).toByteArray());
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.w(TAG, "<" + str + "> with invalid sha256-cert-digest in " + file + " at " + xmlPullParser.getPositionDescription());
            return null;
        }
    }

    private void enableIpSecTunnelMigrationOnVsrUAndAbove() {
        if (android.os.SystemProperties.getInt("ro.vendor.api_level", android.os.Build.VERSION.DEVICE_INITIAL_SDK_INT) > 33) {
            addFeature("android.software.ipsec_tunnel_migration", 0);
        }
    }

    private void addFeature(java.lang.String str, int i) {
        android.content.pm.FeatureInfo featureInfo = this.mAvailableFeatures.get(str);
        if (featureInfo == null) {
            android.content.pm.FeatureInfo featureInfo2 = new android.content.pm.FeatureInfo();
            featureInfo2.name = str;
            featureInfo2.version = i;
            this.mAvailableFeatures.put(str, featureInfo2);
            return;
        }
        featureInfo.version = java.lang.Math.max(featureInfo.version, i);
    }

    private void removeFeature(java.lang.String str) {
        if (this.mAvailableFeatures.remove(str) != null) {
            android.util.Slog.d(TAG, "Removed unavailable feature " + str);
        }
    }

    void readPermission(org.xmlpull.v1.XmlPullParser xmlPullParser, java.lang.String str) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        if (this.mPermissions.containsKey(str)) {
            throw new java.lang.IllegalStateException("Duplicate permission definition for " + str);
        }
        com.android.server.SystemConfig.PermissionEntry permissionEntry = new com.android.server.SystemConfig.PermissionEntry(str, com.android.internal.util.XmlUtils.readBooleanAttribute(xmlPullParser, "perUser", false));
        this.mPermissions.put(str, permissionEntry);
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                if (next == 3 && xmlPullParser.getDepth() <= depth) {
                    return;
                }
                if (next != 3 && next != 4) {
                    if ("group".equals(xmlPullParser.getName())) {
                        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "gid");
                        if (attributeValue != null) {
                            permissionEntry.gids = com.android.internal.util.ArrayUtils.appendInt(permissionEntry.gids, android.os.Process.getGidForName(attributeValue));
                        } else {
                            android.util.Slog.w(TAG, "<group> without gid at " + xmlPullParser.getPositionDescription());
                        }
                    }
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                }
            } else {
                return;
            }
        }
    }

    private void readPrivAppPermissions(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> arrayMap) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        readPermissionAllowlist(xmlPullParser, arrayMap, "privapp-permissions");
    }

    private void readSignatureAppPermissions(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> arrayMap) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        readPermissionAllowlist(xmlPullParser, arrayMap, "signature-permissions");
    }

    private void readInstallInUserType(org.xmlpull.v1.XmlPullParser xmlPullParser, java.util.Map<java.lang.String, java.util.Set<java.lang.String>> map, java.util.Map<java.lang.String, java.util.Set<java.lang.String>> map2) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
        if (android.text.TextUtils.isEmpty(attributeValue)) {
            android.util.Slog.w(TAG, "package is required for <install-in-user-type> in " + xmlPullParser.getPositionDescription());
            return;
        }
        java.util.Set<java.lang.String> set = map.get(attributeValue);
        java.util.Set<java.lang.String> set2 = map2.get(attributeValue);
        int depth = xmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            java.lang.String name = xmlPullParser.getName();
            if ("install-in".equals(name)) {
                java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "user-type");
                if (android.text.TextUtils.isEmpty(attributeValue2)) {
                    android.util.Slog.w(TAG, "user-type is required for <install-in-user-type> in " + xmlPullParser.getPositionDescription());
                } else {
                    if (set == null) {
                        set = new android.util.ArraySet<>();
                        map.put(attributeValue, set);
                    }
                    set.add(attributeValue2);
                }
            } else if ("do-not-install-in".equals(name)) {
                java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "user-type");
                if (android.text.TextUtils.isEmpty(attributeValue3)) {
                    android.util.Slog.w(TAG, "user-type is required for <install-in-user-type> in " + xmlPullParser.getPositionDescription());
                } else {
                    if (set2 == null) {
                        set2 = new android.util.ArraySet<>();
                        map2.put(attributeValue, set2);
                    }
                    set2.add(attributeValue3);
                }
            } else {
                android.util.Slog.w(TAG, "unrecognized tag in <install-in-user-type> in " + xmlPullParser.getPositionDescription());
            }
        }
    }

    void readOemPermissions(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        readPermissionAllowlist(xmlPullParser, this.mPermissionAllowlist.getOemAppAllowlist(), "oem-permissions");
    }

    private static void readPermissionAllowlist(@android.annotation.NonNull org.xmlpull.v1.XmlPullParser xmlPullParser, @android.annotation.NonNull android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> arrayMap, @android.annotation.NonNull java.lang.String str) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
        if (android.text.TextUtils.isEmpty(attributeValue)) {
            android.util.Slog.w(TAG, "package is required for <" + str + "> in " + xmlPullParser.getPositionDescription());
            return;
        }
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap2 = arrayMap.get(attributeValue);
        if (arrayMap2 == null) {
            arrayMap2 = new android.util.ArrayMap<>();
        }
        int depth = xmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            java.lang.String name = xmlPullParser.getName();
            if ("permission".equals(name)) {
                java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "name");
                if (android.text.TextUtils.isEmpty(attributeValue2)) {
                    android.util.Slog.w(TAG, "name is required for <permission> in " + xmlPullParser.getPositionDescription());
                } else {
                    arrayMap2.put(attributeValue2, java.lang.Boolean.TRUE);
                }
            } else if ("deny-permission".equals(name)) {
                java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "name");
                if (android.text.TextUtils.isEmpty(attributeValue3)) {
                    android.util.Slog.w(TAG, "name is required for <deny-permission> in " + xmlPullParser.getPositionDescription());
                } else {
                    arrayMap2.put(attributeValue3, java.lang.Boolean.FALSE);
                }
            }
        }
        arrayMap.put(attributeValue, arrayMap2);
    }

    private void readSplitPermission(org.xmlpull.v1.XmlPullParser xmlPullParser, java.io.File file) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int parseInt;
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        if (attributeValue != null) {
            java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "targetSdk");
            if (android.text.TextUtils.isEmpty(attributeValue2)) {
                parseInt = com.android.internal.util.FrameworkStatsLog.WIFI_BYTES_TRANSFER_BY_FG_BG;
            } else {
                try {
                    parseInt = java.lang.Integer.parseInt(attributeValue2);
                } catch (java.lang.NumberFormatException e) {
                    android.util.Slog.w(TAG, "<split-permission> targetSdk not an integer in " + file + " at " + xmlPullParser.getPositionDescription());
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                    return;
                }
            }
            int depth = xmlPullParser.getDepth();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (com.android.internal.util.XmlUtils.nextElementWithin(xmlPullParser, depth)) {
                if ("new-permission".equals(xmlPullParser.getName())) {
                    java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, "name");
                    if (android.text.TextUtils.isEmpty(attributeValue3)) {
                        android.util.Slog.w(TAG, "name is required for <new-permission> in " + xmlPullParser.getPositionDescription());
                    } else {
                        arrayList.add(attributeValue3);
                    }
                } else {
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                }
            }
            if (!arrayList.isEmpty()) {
                this.mSplitPermissions.add(new android.permission.PermissionManager.SplitPermissionInfo(attributeValue, arrayList, parseInt));
                return;
            }
            return;
        }
        android.util.Slog.w(TAG, "<split-permission> without name in " + file + " at " + xmlPullParser.getPositionDescription());
        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
    }

    private void readComponentOverrides(org.xmlpull.v1.XmlPullParser xmlPullParser, java.io.File file) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, com.android.server.pm.Settings.ATTR_PACKAGE);
        if (attributeValue == null) {
            android.util.Slog.w(TAG, "<component-override> without package in " + file + " at " + xmlPullParser.getPositionDescription());
            return;
        }
        java.lang.String intern = attributeValue.intern();
        int depth = xmlPullParser.getDepth();
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlPullParser, depth)) {
            if ("component".equals(xmlPullParser.getName())) {
                java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "class");
                java.lang.String attributeValue3 = xmlPullParser.getAttributeValue(null, com.android.server.timezonedetector.ServiceConfigAccessor.PROVIDER_MODE_ENABLED);
                if (attributeValue2 == null) {
                    android.util.Slog.w(TAG, "<component> without class in " + file + " at " + xmlPullParser.getPositionDescription());
                    return;
                }
                if (attributeValue3 == null) {
                    android.util.Slog.w(TAG, "<component> without enabled in " + file + " at " + xmlPullParser.getPositionDescription());
                    return;
                }
                if (attributeValue2.startsWith(".")) {
                    attributeValue2 = intern + attributeValue2;
                }
                java.lang.String intern2 = attributeValue2.intern();
                android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap = this.mPackageComponentEnabledState.get(intern);
                if (arrayMap == null) {
                    arrayMap = new android.util.ArrayMap<>();
                    this.mPackageComponentEnabledState.put(intern, arrayMap);
                }
                arrayMap.put(intern2, java.lang.Boolean.valueOf(!"false".equals(attributeValue3)));
            }
        }
    }

    private void readPublicNativeLibrariesList() {
        readPublicLibrariesListFile(new java.io.File("/vendor/etc/public.libraries.txt"));
        java.lang.String[] strArr = {"/system/etc", "/system_ext/etc", "/product/etc"};
        for (int i = 0; i < 3; i++) {
            java.lang.String str = strArr[i];
            java.io.File[] listFiles = new java.io.File(str).listFiles();
            if (listFiles == null) {
                android.util.Slog.w(TAG, "Public libraries file folder missing: " + str);
            } else {
                for (java.io.File file : listFiles) {
                    java.lang.String name = file.getName();
                    if (name.startsWith("public.libraries-") && name.endsWith(".txt")) {
                        readPublicLibrariesListFile(file);
                    }
                }
            }
        }
    }

    private void readPublicLibrariesListFile(java.io.File file) {
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(file));
            while (true) {
                try {
                    java.lang.String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        if (!readLine.isEmpty() && !readLine.startsWith("#")) {
                            java.lang.String str = readLine.trim().split(" ")[0];
                            com.android.server.SystemConfig.SharedLibraryEntry sharedLibraryEntry = new com.android.server.SystemConfig.SharedLibraryEntry(str, str, new java.lang.String[0], true);
                            this.mSharedLibraries.put(sharedLibraryEntry.name, sharedLibraryEntry);
                        }
                    } else {
                        bufferedReader.close();
                        return;
                    }
                } finally {
                }
            }
        } catch (java.io.FileNotFoundException e) {
            android.util.Slog.d(TAG, file + " does not exist");
        } catch (java.io.IOException e2) {
            android.util.Slog.w(TAG, "Failed to read public libraries file " + file, e2);
        }
    }

    private java.lang.String getApexModuleNameFromFilePath(java.nio.file.Path path, java.nio.file.Path path2) {
        if (!path.startsWith(path2)) {
            throw new java.lang.IllegalArgumentException("File " + path + " is not part of an APEX.");
        }
        if (path.getNameCount() <= path2.getNameCount() + 1) {
            throw new java.lang.IllegalArgumentException("File " + path + " is in the APEX partition, but not inside a module.");
        }
        return path.getName(path2.getNameCount()).toString();
    }

    @com.android.internal.annotations.VisibleForTesting
    public void readApexPrivAppPermissions(org.xmlpull.v1.XmlPullParser xmlPullParser, java.io.File file, java.nio.file.Path path) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String apexModuleNameFromFilePath = getApexModuleNameFromFilePath(file.toPath(), path);
        android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>>> apexPrivilegedAppAllowlists = this.mPermissionAllowlist.getApexPrivilegedAppAllowlists();
        android.util.ArrayMap<java.lang.String, android.util.ArrayMap<java.lang.String, java.lang.Boolean>> arrayMap = apexPrivilegedAppAllowlists.get(apexModuleNameFromFilePath);
        if (arrayMap == null) {
            arrayMap = new android.util.ArrayMap<>();
            apexPrivilegedAppAllowlists.put(apexModuleNameFromFilePath, arrayMap);
        }
        readPrivAppPermissions(xmlPullParser, arrayMap);
    }

    private static boolean isSystemProcess() {
        return android.os.Process.myUid() == 1000;
    }

    private static boolean isErofsSupported() {
        try {
            return java.nio.file.Files.exists(java.nio.file.Paths.get("/sys/fs/erofs", new java.lang.String[0]), new java.nio.file.LinkOption[0]);
        } catch (java.lang.Exception e) {
            return false;
        }
    }

    private static boolean isKernelVersionAtLeast(int i, int i2) {
        java.lang.String[] split = android.os.VintfRuntimeInfo.getKernelVersion().split("\\.");
        if (split.length < 2) {
            return false;
        }
        try {
            int parseInt = java.lang.Integer.parseInt(split[0]);
            return parseInt > i || (parseInt == i && java.lang.Integer.parseInt(split[1]) >= i2);
        } catch (java.lang.NumberFormatException e) {
            return false;
        }
    }
}
