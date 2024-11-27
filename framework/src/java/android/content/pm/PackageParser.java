package android.content.pm;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class PackageParser {
    public static final java.lang.String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";
    public static final java.lang.String ANDROID_RESOURCES = "http://schemas.android.com/apk/res/android";
    public static final java.lang.String APEX_FILE_EXTENSION = ".apex";
    public static final java.lang.String APK_FILE_EXTENSION = ".apk";
    public static final java.util.Set<java.lang.String> CHILD_PACKAGE_TAGS;
    public static final boolean DEBUG_BACKUP = false;
    public static final boolean DEBUG_JAR = false;
    public static final boolean DEBUG_PARSER = false;
    private static final int DEFAULT_MIN_SDK_VERSION = 1;
    public static final float DEFAULT_PRE_O_MAX_ASPECT_RATIO = 1.86f;
    private static final int DEFAULT_TARGET_SDK_VERSION = 0;
    public static final boolean LOG_PARSE_TIMINGS = android.os.Build.IS_DEBUGGABLE;
    public static final int LOG_PARSE_TIMINGS_THRESHOLD_MS = 100;
    public static final boolean LOG_UNSAFE_BROADCASTS = false;
    public static final java.lang.String METADATA_ACTIVITY_WINDOW_LAYOUT_AFFINITY = "android.activity_window_layout_affinity";
    public static final java.lang.String METADATA_MAX_ASPECT_RATIO = "android.max_aspect";
    public static final java.lang.String METADATA_SUPPORTS_SIZE_CHANGES = "android.supports_size_changes";
    public static final java.lang.String MNT_EXPAND = "/mnt/expand/";
    public static final boolean MULTI_PACKAGE_APK_ENABLED;
    public static final android.content.pm.PackageParser.NewPermissionInfo[] NEW_PERMISSIONS;
    public static final int PARSE_CHATTY = Integer.MIN_VALUE;
    public static final int PARSE_COLLECT_CERTIFICATES = 32;
    public static final int PARSE_DEFAULT_INSTALL_LOCATION = -1;
    public static final int PARSE_DEFAULT_TARGET_SANDBOX = 1;
    public static final int PARSE_ENFORCE_CODE = 64;
    public static final int PARSE_EXTERNAL_STORAGE = 8;
    public static final int PARSE_IGNORE_PROCESSES = 2;
    public static final int PARSE_IS_SYSTEM_DIR = 16;
    public static final int PARSE_MUST_BE_APK = 1;
    private static final java.lang.String PROPERTY_CHILD_PACKAGES_ENABLED = "persist.sys.child_packages_enabled";
    private static final int RECREATE_ON_CONFIG_CHANGES_MASK = 3;
    public static final boolean RIGID_PARSER = false;
    public static final java.util.Set<java.lang.String> SAFE_BROADCASTS;
    public static final java.lang.String[] SDK_CODENAMES;
    public static final int SDK_VERSION;
    private static final java.lang.String TAG = "PackageParser";
    public static final java.lang.String TAG_ADOPT_PERMISSIONS = "adopt-permissions";
    public static final java.lang.String TAG_APPLICATION = "application";
    public static final java.lang.String TAG_ATTRIBUTION = "attribution";
    public static final java.lang.String TAG_COMPATIBLE_SCREENS = "compatible-screens";
    public static final java.lang.String TAG_EAT_COMMENT = "eat-comment";
    public static final java.lang.String TAG_FEATURE_GROUP = "feature-group";
    public static final java.lang.String TAG_INSTRUMENTATION = "instrumentation";
    public static final java.lang.String TAG_KEY_SETS = "key-sets";
    public static final java.lang.String TAG_MANIFEST = "manifest";
    public static final java.lang.String TAG_ORIGINAL_PACKAGE = "original-package";
    public static final java.lang.String TAG_OVERLAY = "overlay";
    public static final java.lang.String TAG_PACKAGE = "package";
    public static final java.lang.String TAG_PACKAGE_VERIFIER = "package-verifier";
    public static final java.lang.String TAG_PERMISSION = "permission";
    public static final java.lang.String TAG_PERMISSION_GROUP = "permission-group";
    public static final java.lang.String TAG_PERMISSION_TREE = "permission-tree";
    public static final java.lang.String TAG_PROFILEABLE = "profileable";
    public static final java.lang.String TAG_PROTECTED_BROADCAST = "protected-broadcast";
    public static final java.lang.String TAG_QUERIES = "queries";
    public static final java.lang.String TAG_RESTRICT_UPDATE = "restrict-update";
    public static final java.lang.String TAG_SUPPORTS_INPUT = "supports-input";
    public static final java.lang.String TAG_SUPPORT_SCREENS = "supports-screens";
    public static final java.lang.String TAG_USES_CONFIGURATION = "uses-configuration";
    public static final java.lang.String TAG_USES_FEATURE = "uses-feature";
    public static final java.lang.String TAG_USES_GL_TEXTURE = "uses-gl-texture";
    public static final java.lang.String TAG_USES_PERMISSION = "uses-permission";
    public static final java.lang.String TAG_USES_PERMISSION_SDK_23 = "uses-permission-sdk-23";
    public static final java.lang.String TAG_USES_PERMISSION_SDK_M = "uses-permission-sdk-m";
    public static final java.lang.String TAG_USES_SDK = "uses-sdk";
    public static final java.lang.String TAG_USES_SPLIT = "uses-split";
    public static boolean sCompatibilityModeEnabled;
    public static final java.util.Comparator<java.lang.String> sSplitNameComparator;
    public static boolean sUseRoundIcon;

    @java.lang.Deprecated
    public java.lang.String mArchiveSourcePath;
    private java.io.File mCacheDir;
    public android.content.pm.PackageParser.Callback mCallback;
    private boolean mOnlyCoreApps;
    private android.content.pm.PackageParser.ParsePackageItemArgs mParseInstrumentationArgs;
    public java.lang.String[] mSeparateProcesses;
    public int mParseError = 1;
    private android.util.DisplayMetrics mMetrics = new android.util.DisplayMetrics();

    public interface Callback {
        boolean hasFeature(java.lang.String str);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ParseFlags {
    }

    @java.lang.Deprecated
    private interface SplitAssetLoader extends java.lang.AutoCloseable {
        android.content.res.ApkAssets getBaseApkAssets();

        android.content.res.AssetManager getBaseAssetManager() throws android.content.pm.PackageParser.PackageParserException;

        android.content.res.AssetManager getSplitAssetManager(int i) throws android.content.pm.PackageParser.PackageParserException;
    }

    static {
        MULTI_PACKAGE_APK_ENABLED = android.os.Build.IS_DEBUGGABLE && android.os.SystemProperties.getBoolean(PROPERTY_CHILD_PACKAGES_ENABLED, false);
        CHILD_PACKAGE_TAGS = new android.util.ArraySet();
        CHILD_PACKAGE_TAGS.add("application");
        CHILD_PACKAGE_TAGS.add("compatible-screens");
        CHILD_PACKAGE_TAGS.add("eat-comment");
        CHILD_PACKAGE_TAGS.add("feature-group");
        CHILD_PACKAGE_TAGS.add("instrumentation");
        CHILD_PACKAGE_TAGS.add("supports-screens");
        CHILD_PACKAGE_TAGS.add("supports-input");
        CHILD_PACKAGE_TAGS.add("uses-configuration");
        CHILD_PACKAGE_TAGS.add("uses-feature");
        CHILD_PACKAGE_TAGS.add("uses-gl-texture");
        CHILD_PACKAGE_TAGS.add("uses-permission");
        CHILD_PACKAGE_TAGS.add("uses-permission-sdk-23");
        CHILD_PACKAGE_TAGS.add("uses-permission-sdk-m");
        CHILD_PACKAGE_TAGS.add("uses-sdk");
        SAFE_BROADCASTS = new android.util.ArraySet();
        SAFE_BROADCASTS.add(android.content.Intent.ACTION_BOOT_COMPLETED);
        NEW_PERMISSIONS = new android.content.pm.PackageParser.NewPermissionInfo[]{new android.content.pm.PackageParser.NewPermissionInfo(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, 4, 0), new android.content.pm.PackageParser.NewPermissionInfo(android.Manifest.permission.READ_PHONE_STATE, 4, 0)};
        SDK_VERSION = android.os.Build.VERSION.SDK_INT;
        SDK_CODENAMES = android.os.Build.VERSION.ACTIVE_CODENAMES;
        sCompatibilityModeEnabled = true;
        sUseRoundIcon = false;
        sSplitNameComparator = new android.content.pm.PackageParser.SplitNameComparator();
    }

    public static class NewPermissionInfo {
        public final int fileVersion;
        public final java.lang.String name;
        public final int sdkVersion;

        public NewPermissionInfo(java.lang.String str, int i, int i2) {
            this.name = str;
            this.sdkVersion = i;
            this.fileVersion = i2;
        }
    }

    static class ParsePackageItemArgs {
        final int bannerRes;
        final int iconRes;
        final int labelRes;
        final int logoRes;
        final int nameRes;
        final java.lang.String[] outError;
        final android.content.pm.PackageParser.Package owner;
        final int roundIconRes;
        android.content.res.TypedArray sa;
        java.lang.String tag;

        ParsePackageItemArgs(android.content.pm.PackageParser.Package r1, java.lang.String[] strArr, int i, int i2, int i3, int i4, int i5, int i6) {
            this.owner = r1;
            this.outError = strArr;
            this.nameRes = i;
            this.labelRes = i2;
            this.iconRes = i3;
            this.logoRes = i5;
            this.bannerRes = i6;
            this.roundIconRes = i4;
        }
    }

    public static class ParseComponentArgs extends android.content.pm.PackageParser.ParsePackageItemArgs {
        final int descriptionRes;
        final int enabledRes;
        int flags;
        final int processRes;
        final java.lang.String[] sepProcesses;

        public ParseComponentArgs(android.content.pm.PackageParser.Package r1, java.lang.String[] strArr, int i, int i2, int i3, int i4, int i5, int i6, java.lang.String[] strArr2, int i7, int i8, int i9) {
            super(r1, strArr, i, i2, i3, i4, i5, i6);
            this.sepProcesses = strArr2;
            this.processRes = i7;
            this.descriptionRes = i8;
            this.enabledRes = i9;
        }
    }

    public static class PackageLite {
        public final java.lang.String baseCodePath;
        public final int baseRevisionCode;
        public final java.lang.String codePath;
        public final java.lang.String[] configForSplit;
        public final boolean coreApp;
        public final boolean debuggable;
        public final boolean extractNativeLibs;
        public final int installLocation;
        public final boolean[] isFeatureSplits;
        public final boolean isSplitRequired;
        public final boolean isolatedSplits;
        public final boolean multiArch;
        public final java.lang.String packageName;
        public final boolean profilableByShell;
        public final java.lang.String[] splitCodePaths;
        public final java.lang.String[] splitNames;
        public final int[] splitRevisionCodes;
        public final boolean use32bitAbi;
        public final boolean useEmbeddedDex;
        public final java.lang.String[] usesSplitNames;
        public final android.content.pm.VerifierInfo[] verifiers;
        public final int versionCode;
        public final int versionCodeMajor;

        public PackageLite(java.lang.String str, java.lang.String str2, android.content.pm.PackageParser.ApkLite apkLite, java.lang.String[] strArr, boolean[] zArr, java.lang.String[] strArr2, java.lang.String[] strArr3, java.lang.String[] strArr4, int[] iArr) {
            this.packageName = apkLite.packageName;
            this.versionCode = apkLite.versionCode;
            this.versionCodeMajor = apkLite.versionCodeMajor;
            this.installLocation = apkLite.installLocation;
            this.verifiers = apkLite.verifiers;
            this.splitNames = strArr;
            this.isFeatureSplits = zArr;
            this.usesSplitNames = strArr2;
            this.configForSplit = strArr3;
            this.codePath = str;
            this.baseCodePath = str2;
            this.splitCodePaths = strArr4;
            this.baseRevisionCode = apkLite.revisionCode;
            this.splitRevisionCodes = iArr;
            this.coreApp = apkLite.coreApp;
            this.debuggable = apkLite.debuggable;
            this.multiArch = apkLite.multiArch;
            this.use32bitAbi = apkLite.use32bitAbi;
            this.extractNativeLibs = apkLite.extractNativeLibs;
            this.isolatedSplits = apkLite.isolatedSplits;
            this.useEmbeddedDex = apkLite.useEmbeddedDex;
            this.isSplitRequired = apkLite.isSplitRequired;
            this.profilableByShell = apkLite.profilableByShell;
        }

        public java.util.List<java.lang.String> getAllCodePaths() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(this.baseCodePath);
            if (!com.android.internal.util.ArrayUtils.isEmpty(this.splitCodePaths)) {
                java.util.Collections.addAll(arrayList, this.splitCodePaths);
            }
            return arrayList;
        }

        public long getLongVersionCode() {
            return android.content.pm.PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
        }
    }

    public static class ApkLite {
        public final java.lang.String codePath;
        public final java.lang.String configForSplit;
        public final boolean coreApp;
        public final boolean debuggable;
        public final boolean extractNativeLibs;
        public final int installLocation;
        public boolean isFeatureSplit;
        public final boolean isSplitRequired;
        public final boolean isolatedSplits;
        public final int minSdkVersion;
        public final boolean multiArch;
        public final boolean overlayIsStatic;
        public final int overlayPriority;
        public final java.lang.String packageName;
        public final boolean profilableByShell;
        public final int revisionCode;
        public final int rollbackDataPolicy;
        public final android.content.pm.PackageParser.SigningDetails signingDetails;
        public final java.lang.String splitName;
        public final java.lang.String targetPackageName;
        public final int targetSdkVersion;
        public final boolean use32bitAbi;
        public final boolean useEmbeddedDex;
        public final java.lang.String usesSplitName;
        public final android.content.pm.VerifierInfo[] verifiers;
        public final int versionCode;
        public final int versionCodeMajor;

        public ApkLite(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, java.lang.String str4, java.lang.String str5, boolean z2, int i, int i2, int i3, int i4, java.util.List<android.content.pm.VerifierInfo> list, android.content.pm.PackageParser.SigningDetails signingDetails, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, java.lang.String str6, boolean z11, int i5, int i6, int i7, int i8) {
            this.codePath = str;
            this.packageName = str2;
            this.splitName = str3;
            this.isFeatureSplit = z;
            this.configForSplit = str4;
            this.usesSplitName = str5;
            this.versionCode = i;
            this.versionCodeMajor = i2;
            this.revisionCode = i3;
            this.installLocation = i4;
            this.signingDetails = signingDetails;
            this.verifiers = (android.content.pm.VerifierInfo[]) list.toArray(new android.content.pm.VerifierInfo[list.size()]);
            this.coreApp = z3;
            this.debuggable = z4;
            this.profilableByShell = z5;
            this.multiArch = z6;
            this.use32bitAbi = z7;
            this.useEmbeddedDex = z8;
            this.extractNativeLibs = z9;
            this.isolatedSplits = z10;
            this.isSplitRequired = z2;
            this.targetPackageName = str6;
            this.overlayIsStatic = z11;
            this.overlayPriority = i5;
            this.minSdkVersion = i6;
            this.targetSdkVersion = i7;
            this.rollbackDataPolicy = i8;
        }

        public long getLongVersionCode() {
            return android.content.pm.PackageInfo.composeLongVersionCode(this.versionCodeMajor, this.versionCode);
        }
    }

    private static class CachedComponentArgs {
        android.content.pm.PackageParser.ParseComponentArgs mActivityAliasArgs;
        android.content.pm.PackageParser.ParseComponentArgs mActivityArgs;
        android.content.pm.PackageParser.ParseComponentArgs mProviderArgs;
        android.content.pm.PackageParser.ParseComponentArgs mServiceArgs;

        private CachedComponentArgs() {
        }
    }

    public PackageParser() {
        this.mMetrics.setToDefaults();
    }

    public void setSeparateProcesses(java.lang.String[] strArr) {
        this.mSeparateProcesses = strArr;
    }

    public void setOnlyCoreApps(boolean z) {
        this.mOnlyCoreApps = z;
    }

    public void setDisplayMetrics(android.util.DisplayMetrics displayMetrics) {
        this.mMetrics = displayMetrics;
    }

    public void setCacheDir(java.io.File file) {
        this.mCacheDir = file;
    }

    public static final class CallbackImpl implements android.content.pm.PackageParser.Callback {
        private final android.content.pm.PackageManager mPm;

        public CallbackImpl(android.content.pm.PackageManager packageManager) {
            this.mPm = packageManager;
        }

        @Override // android.content.pm.PackageParser.Callback
        public boolean hasFeature(java.lang.String str) {
            return this.mPm.hasSystemFeature(str);
        }
    }

    public void setCallback(android.content.pm.PackageParser.Callback callback) {
        this.mCallback = callback;
    }

    public static final boolean isApkFile(java.io.File file) {
        return isApkPath(file.getName());
    }

    public static boolean isApkPath(java.lang.String str) {
        return str.endsWith(".apk");
    }

    private static boolean checkUseInstalledOrHidden(int i, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, android.content.pm.ApplicationInfo applicationInfo) {
        int i2 = 536870912 & i;
        if (i2 == 0 && !frameworkPackageUserState.isInstalled() && applicationInfo != null && applicationInfo.hiddenUntilInstalled) {
            return false;
        }
        if (!isAvailable(frameworkPackageUserState, i)) {
            if (applicationInfo == null || !applicationInfo.isSystemApp()) {
                return false;
            }
            if ((i & android.content.pm.PackageManager.MATCH_KNOWN_PACKAGES) == 0 && i2 == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAvailable(android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState) {
        return checkUseInstalledOrHidden(0, frameworkPackageUserState, null);
    }

    public static android.content.pm.PackageInfo generatePackageInfo(android.content.pm.PackageParser.Package r10, int[] iArr, int i, long j, long j2, java.util.Set<java.lang.String> set, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState) {
        return generatePackageInfo(r10, iArr, i, j, j2, set, frameworkPackageUserState, android.os.UserHandle.getCallingUserId());
    }

    public static android.content.pm.PackageInfo generatePackageInfo(android.content.pm.PackageParser.Package r11, int[] iArr, int i, long j, long j2, java.util.Set<java.lang.String> set, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, int i2) {
        return generatePackageInfo(r11, null, iArr, i, j, j2, set, frameworkPackageUserState, i2);
    }

    public static android.content.pm.PackageInfo generatePackageInfo(android.content.pm.PackageParser.Package r11, android.apex.ApexInfo apexInfo, int i) {
        return generatePackageInfo(r11, apexInfo, libcore.util.EmptyArray.INT, i, 0L, 0L, java.util.Collections.emptySet(), android.content.pm.pkg.FrameworkPackageUserState.DEFAULT, android.os.UserHandle.getCallingUserId());
    }

    private static android.content.pm.PackageInfo generatePackageInfo(android.content.pm.PackageParser.Package r16, android.apex.ApexInfo apexInfo, int[] iArr, int i, long j, long j2, java.util.Set<java.lang.String> set, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, int i2) {
        android.content.pm.ApplicationInfo applicationInfo;
        int size;
        int size2;
        int size3;
        int size4;
        int size5;
        if (!checkUseInstalledOrHidden(i, frameworkPackageUserState, r16.applicationInfo) || !r16.isMatch(i)) {
            return null;
        }
        if ((i & 15) != 0) {
            applicationInfo = generateApplicationInfo(r16, i, frameworkPackageUserState, i2);
        } else {
            applicationInfo = null;
        }
        android.content.pm.PackageInfo packageInfo = new android.content.pm.PackageInfo();
        packageInfo.packageName = r16.packageName;
        packageInfo.splitNames = r16.splitNames;
        packageInfo.versionCode = r16.mVersionCode;
        packageInfo.versionCodeMajor = r16.mVersionCodeMajor;
        packageInfo.baseRevisionCode = r16.baseRevisionCode;
        packageInfo.splitRevisionCodes = r16.splitRevisionCodes;
        packageInfo.versionName = r16.mVersionName;
        packageInfo.sharedUserId = r16.mSharedUserId;
        packageInfo.sharedUserLabel = r16.mSharedUserLabel;
        packageInfo.applicationInfo = generateApplicationInfo(r16, i, frameworkPackageUserState, i2);
        packageInfo.installLocation = r16.installLocation;
        packageInfo.isStub = r16.isStub;
        packageInfo.coreApp = r16.coreApp;
        if ((packageInfo.applicationInfo.flags & 1) != 0 || (packageInfo.applicationInfo.flags & 128) != 0) {
            packageInfo.requiredForAllUsers = r16.mRequiredForAllUsers;
        }
        packageInfo.restrictedAccountType = r16.mRestrictedAccountType;
        packageInfo.requiredAccountType = r16.mRequiredAccountType;
        packageInfo.overlayTarget = r16.mOverlayTarget;
        packageInfo.targetOverlayableName = r16.mOverlayTargetName;
        packageInfo.overlayCategory = r16.mOverlayCategory;
        packageInfo.overlayPriority = r16.mOverlayPriority;
        packageInfo.mOverlayIsStatic = r16.mOverlayIsStatic;
        packageInfo.compileSdkVersion = r16.mCompileSdkVersion;
        packageInfo.compileSdkVersionCodename = r16.mCompileSdkVersionCodename;
        packageInfo.firstInstallTime = j;
        packageInfo.lastUpdateTime = j2;
        if ((i & 256) != 0) {
            packageInfo.gids = iArr;
        }
        if ((i & 16384) != 0) {
            int size6 = r16.configPreferences != null ? r16.configPreferences.size() : 0;
            if (size6 > 0) {
                packageInfo.configPreferences = new android.content.pm.ConfigurationInfo[size6];
                r16.configPreferences.toArray(packageInfo.configPreferences);
            }
            int size7 = r16.reqFeatures != null ? r16.reqFeatures.size() : 0;
            if (size7 > 0) {
                packageInfo.reqFeatures = new android.content.pm.FeatureInfo[size7];
                r16.reqFeatures.toArray(packageInfo.reqFeatures);
            }
            int size8 = r16.featureGroups != null ? r16.featureGroups.size() : 0;
            if (size8 > 0) {
                packageInfo.featureGroups = new android.content.pm.FeatureGroupInfo[size8];
                r16.featureGroups.toArray(packageInfo.featureGroups);
            }
        }
        if ((i & 1) != 0 && (size5 = r16.activities.size()) > 0) {
            android.content.pm.ActivityInfo[] activityInfoArr = new android.content.pm.ActivityInfo[size5];
            int i3 = 0;
            for (int i4 = 0; i4 < size5; i4++) {
                android.content.pm.PackageParser.Activity activity = r16.activities.get(i4);
                if (isMatch(frameworkPackageUserState, activity.info, i) && !android.content.pm.PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(activity.className)) {
                    activityInfoArr[i3] = generateActivityInfo(activity, i, frameworkPackageUserState, i2, applicationInfo);
                    i3++;
                }
            }
            packageInfo.activities = (android.content.pm.ActivityInfo[]) com.android.internal.util.ArrayUtils.trimToSize(activityInfoArr, i3);
        }
        if ((i & 2) != 0 && (size4 = r16.receivers.size()) > 0) {
            android.content.pm.ActivityInfo[] activityInfoArr2 = new android.content.pm.ActivityInfo[size4];
            int i5 = 0;
            for (int i6 = 0; i6 < size4; i6++) {
                android.content.pm.PackageParser.Activity activity2 = r16.receivers.get(i6);
                if (isMatch(frameworkPackageUserState, activity2.info, i)) {
                    activityInfoArr2[i5] = generateActivityInfo(activity2, i, frameworkPackageUserState, i2, applicationInfo);
                    i5++;
                }
            }
            packageInfo.receivers = (android.content.pm.ActivityInfo[]) com.android.internal.util.ArrayUtils.trimToSize(activityInfoArr2, i5);
        }
        if ((i & 4) != 0 && (size3 = r16.services.size()) > 0) {
            android.content.pm.ServiceInfo[] serviceInfoArr = new android.content.pm.ServiceInfo[size3];
            int i7 = 0;
            for (int i8 = 0; i8 < size3; i8++) {
                android.content.pm.PackageParser.Service service = r16.services.get(i8);
                if (isMatch(frameworkPackageUserState, service.info, i)) {
                    serviceInfoArr[i7] = generateServiceInfo(service, i, frameworkPackageUserState, i2, applicationInfo);
                    i7++;
                }
            }
            packageInfo.services = (android.content.pm.ServiceInfo[]) com.android.internal.util.ArrayUtils.trimToSize(serviceInfoArr, i7);
        }
        if ((i & 8) != 0 && (size2 = r16.providers.size()) > 0) {
            android.content.pm.ProviderInfo[] providerInfoArr = new android.content.pm.ProviderInfo[size2];
            int i9 = 0;
            for (int i10 = 0; i10 < size2; i10++) {
                android.content.pm.PackageParser.Provider provider = r16.providers.get(i10);
                if (isMatch(frameworkPackageUserState, provider.info, i)) {
                    providerInfoArr[i9] = generateProviderInfo(provider, i, frameworkPackageUserState, i2, applicationInfo);
                    i9++;
                }
            }
            packageInfo.providers = (android.content.pm.ProviderInfo[]) com.android.internal.util.ArrayUtils.trimToSize(providerInfoArr, i9);
        }
        if ((i & 16) != 0 && (size = r16.instrumentation.size()) > 0) {
            packageInfo.instrumentation = new android.content.pm.InstrumentationInfo[size];
            for (int i11 = 0; i11 < size; i11++) {
                packageInfo.instrumentation[i11] = generateInstrumentationInfo(r16.instrumentation.get(i11), i);
            }
        }
        if ((i & 4096) != 0) {
            int size9 = r16.permissions.size();
            if (size9 > 0) {
                packageInfo.permissions = new android.content.pm.PermissionInfo[size9];
                for (int i12 = 0; i12 < size9; i12++) {
                    packageInfo.permissions[i12] = generatePermissionInfo(r16.permissions.get(i12), i);
                }
            }
            int size10 = r16.requestedPermissions.size();
            if (size10 > 0) {
                packageInfo.requestedPermissions = new java.lang.String[size10];
                packageInfo.requestedPermissionsFlags = new int[size10];
                for (int i13 = 0; i13 < size10; i13++) {
                    java.lang.String str = r16.requestedPermissions.get(i13);
                    packageInfo.requestedPermissions[i13] = str;
                    int[] iArr2 = packageInfo.requestedPermissionsFlags;
                    iArr2[i13] = iArr2[i13] | 1;
                    if (set != null && set.contains(str)) {
                        int[] iArr3 = packageInfo.requestedPermissionsFlags;
                        iArr3[i13] = iArr3[i13] | 2;
                    }
                }
            }
        }
        if (apexInfo != null) {
            java.io.File file = new java.io.File(apexInfo.modulePath);
            packageInfo.applicationInfo.sourceDir = file.getPath();
            packageInfo.applicationInfo.publicSourceDir = file.getPath();
            if (apexInfo.isFactory) {
                packageInfo.applicationInfo.flags |= 1;
            } else {
                packageInfo.applicationInfo.flags &= -2;
            }
            if (apexInfo.isActive) {
                packageInfo.applicationInfo.flags |= 8388608;
            } else {
                packageInfo.applicationInfo.flags &= -8388609;
            }
            packageInfo.isApex = true;
        }
        if ((i & 64) != 0) {
            if (r16.mSigningDetails.hasPastSigningCertificates()) {
                packageInfo.signatures = new android.content.pm.Signature[1];
                packageInfo.signatures[0] = r16.mSigningDetails.pastSigningCertificates[0];
            } else if (r16.mSigningDetails.hasSignatures()) {
                int length = r16.mSigningDetails.signatures.length;
                packageInfo.signatures = new android.content.pm.Signature[length];
                java.lang.System.arraycopy(r16.mSigningDetails.signatures, 0, packageInfo.signatures, 0, length);
            }
        }
        if ((134217728 & i) != 0) {
            if (r16.mSigningDetails != android.content.pm.PackageParser.SigningDetails.UNKNOWN) {
                packageInfo.signingInfo = new android.content.pm.SigningInfo(new android.content.pm.SigningDetails(r16.mSigningDetails.signatures, r16.mSigningDetails.signatureSchemeVersion, r16.mSigningDetails.publicKeys, r16.mSigningDetails.pastSigningCertificates));
            } else {
                packageInfo.signingInfo = null;
            }
        }
        return packageInfo;
    }

    private static class SplitNameComparator implements java.util.Comparator<java.lang.String> {
        private SplitNameComparator() {
        }

        @Override // java.util.Comparator
        public int compare(java.lang.String str, java.lang.String str2) {
            if (str == null) {
                return -1;
            }
            if (str2 == null) {
                return 1;
            }
            return str.compareTo(str2);
        }
    }

    public static android.content.pm.PackageParser.PackageLite parsePackageLite(java.io.File file, int i) throws android.content.pm.PackageParser.PackageParserException {
        if (file.isDirectory()) {
            return parseClusterPackageLite(file, i);
        }
        return parseMonolithicPackageLite(file, i);
    }

    private static android.content.pm.PackageParser.PackageLite parseMonolithicPackageLite(java.io.File file, int i) throws android.content.pm.PackageParser.PackageParserException {
        android.os.Trace.traceBegin(262144L, "parseApkLite");
        android.content.pm.PackageParser.ApkLite parseApkLite = parseApkLite(file, i);
        java.lang.String absolutePath = file.getAbsolutePath();
        android.os.Trace.traceEnd(262144L);
        return new android.content.pm.PackageParser.PackageLite(absolutePath, parseApkLite.codePath, parseApkLite, null, null, null, null, null, null);
    }

    static android.content.pm.PackageParser.PackageLite parseClusterPackageLite(java.io.File file, int i) throws android.content.pm.PackageParser.PackageParserException {
        java.lang.String[] strArr;
        boolean[] zArr;
        java.lang.String[] strArr2;
        java.lang.String[] strArr3;
        java.lang.String[] strArr4;
        int[] iArr;
        java.io.File[] listFiles = file.listFiles();
        if (com.android.internal.util.ArrayUtils.isEmpty(listFiles)) {
            throw new android.content.pm.PackageParser.PackageParserException(-100, "No packages found in split");
        }
        if (listFiles.length == 1 && listFiles[0].isDirectory()) {
            return parseClusterPackageLite(listFiles[0], i);
        }
        android.os.Trace.traceBegin(262144L, "parseApkLite");
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        int i2 = 0;
        java.lang.String str = null;
        for (java.io.File file2 : listFiles) {
            if (isApkFile(file2)) {
                android.content.pm.PackageParser.ApkLite parseApkLite = parseApkLite(file2, i);
                if (str == null) {
                    str = parseApkLite.packageName;
                    i2 = parseApkLite.versionCode;
                } else {
                    if (!str.equals(parseApkLite.packageName)) {
                        throw new android.content.pm.PackageParser.PackageParserException(-101, "Inconsistent package " + parseApkLite.packageName + " in " + file2 + "; expected " + str);
                    }
                    if (i2 != parseApkLite.versionCode) {
                        throw new android.content.pm.PackageParser.PackageParserException(-101, "Inconsistent version " + parseApkLite.versionCode + " in " + file2 + "; expected " + i2);
                    }
                }
                if (arrayMap.put(parseApkLite.splitName, parseApkLite) != null) {
                    throw new android.content.pm.PackageParser.PackageParserException(-101, "Split name " + parseApkLite.splitName + " defined more than once; most recent was " + file2);
                }
            }
        }
        android.os.Trace.traceEnd(262144L);
        android.content.pm.PackageParser.ApkLite apkLite = (android.content.pm.PackageParser.ApkLite) arrayMap.remove(null);
        if (apkLite == null) {
            throw new android.content.pm.PackageParser.PackageParserException(-101, "Missing base APK in " + file);
        }
        int size = arrayMap.size();
        if (size <= 0) {
            strArr = null;
            zArr = null;
            strArr2 = null;
            strArr3 = null;
            strArr4 = null;
            iArr = null;
        } else {
            boolean[] zArr2 = new boolean[size];
            java.lang.String[] strArr5 = new java.lang.String[size];
            java.lang.String[] strArr6 = new java.lang.String[size];
            java.lang.String[] strArr7 = new java.lang.String[size];
            int[] iArr2 = new int[size];
            java.lang.String[] strArr8 = (java.lang.String[]) arrayMap.keySet().toArray(new java.lang.String[size]);
            java.util.Arrays.sort(strArr8, sSplitNameComparator);
            for (int i3 = 0; i3 < size; i3++) {
                android.content.pm.PackageParser.ApkLite apkLite2 = (android.content.pm.PackageParser.ApkLite) arrayMap.get(strArr8[i3]);
                strArr5[i3] = apkLite2.usesSplitName;
                zArr2[i3] = apkLite2.isFeatureSplit;
                strArr6[i3] = apkLite2.configForSplit;
                strArr7[i3] = apkLite2.codePath;
                iArr2[i3] = apkLite2.revisionCode;
            }
            strArr = strArr8;
            strArr2 = strArr5;
            strArr3 = strArr6;
            strArr4 = strArr7;
            zArr = zArr2;
            iArr = iArr2;
        }
        return new android.content.pm.PackageParser.PackageLite(file.getAbsolutePath(), apkLite.codePath, apkLite, strArr, zArr, strArr2, strArr3, strArr4, iArr);
    }

    public android.content.pm.PackageParser.Package parsePackage(java.io.File file, int i, boolean z) throws android.content.pm.PackageParser.PackageParserException {
        if (file.isDirectory()) {
            return parseClusterPackage(file, i);
        }
        return parseMonolithicPackage(file, i);
    }

    public android.content.pm.PackageParser.Package parsePackage(java.io.File file, int i) throws android.content.pm.PackageParser.PackageParserException {
        return parsePackage(file, i, false);
    }

    private android.content.pm.PackageParser.Package parseClusterPackage(java.io.File file, int i) throws android.content.pm.PackageParser.PackageParserException {
        android.content.pm.PackageParser.SplitAssetLoader defaultSplitAssetLoader;
        android.util.SparseArray<int[]> sparseArray;
        android.content.pm.PackageParser.PackageLite parseClusterPackageLite = parseClusterPackageLite(file, 0);
        if (this.mOnlyCoreApps && !parseClusterPackageLite.coreApp) {
            throw new android.content.pm.PackageParser.PackageParserException(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "Not a coreApp: " + file);
        }
        if (parseClusterPackageLite.isolatedSplits && !com.android.internal.util.ArrayUtils.isEmpty(parseClusterPackageLite.splitNames)) {
            try {
                sparseArray = android.content.pm.PackageParser.SplitAssetDependencyLoader.createDependenciesFromPackage(parseClusterPackageLite);
                defaultSplitAssetLoader = new android.content.pm.PackageParser.SplitAssetDependencyLoader(parseClusterPackageLite, sparseArray, i);
            } catch (android.content.pm.PackageParser.SplitDependencyLoader.IllegalDependencyException e) {
                throw new android.content.pm.PackageParser.PackageParserException(-101, e.getMessage());
            }
        } else {
            defaultSplitAssetLoader = new android.content.pm.PackageParser.DefaultSplitAssetLoader(parseClusterPackageLite, i);
            sparseArray = null;
        }
        try {
            android.content.res.AssetManager baseAssetManager = defaultSplitAssetLoader.getBaseAssetManager();
            java.io.File file2 = new java.io.File(parseClusterPackageLite.baseCodePath);
            android.content.pm.PackageParser.Package parseBaseApk = parseBaseApk(file2, baseAssetManager, i);
            if (parseBaseApk == null) {
                throw new android.content.pm.PackageParser.PackageParserException(-100, "Failed to parse base APK: " + file2);
            }
            if (!com.android.internal.util.ArrayUtils.isEmpty(parseClusterPackageLite.splitNames)) {
                int length = parseClusterPackageLite.splitNames.length;
                parseBaseApk.splitNames = parseClusterPackageLite.splitNames;
                parseBaseApk.splitCodePaths = parseClusterPackageLite.splitCodePaths;
                parseBaseApk.splitRevisionCodes = parseClusterPackageLite.splitRevisionCodes;
                parseBaseApk.splitFlags = new int[length];
                parseBaseApk.splitPrivateFlags = new int[length];
                parseBaseApk.applicationInfo.splitNames = parseBaseApk.splitNames;
                parseBaseApk.applicationInfo.splitDependencies = sparseArray;
                parseBaseApk.applicationInfo.splitClassLoaderNames = new java.lang.String[length];
                for (int i2 = 0; i2 < length; i2++) {
                    parseSplitApk(parseBaseApk, i2, defaultSplitAssetLoader.getSplitAssetManager(i2), i);
                }
            }
            parseBaseApk.setCodePath(parseClusterPackageLite.codePath);
            parseBaseApk.setUse32bitAbi(parseClusterPackageLite.use32bitAbi);
            return parseBaseApk;
        } finally {
            libcore.io.IoUtils.closeQuietly(defaultSplitAssetLoader);
        }
    }

    public android.content.pm.PackageParser.Package parseMonolithicPackage(java.io.File file, int i) throws android.content.pm.PackageParser.PackageParserException {
        android.content.pm.PackageParser.PackageLite parseMonolithicPackageLite = parseMonolithicPackageLite(file, i);
        if (this.mOnlyCoreApps && !parseMonolithicPackageLite.coreApp) {
            throw new android.content.pm.PackageParser.PackageParserException(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "Not a coreApp: " + file);
        }
        android.content.pm.PackageParser.DefaultSplitAssetLoader defaultSplitAssetLoader = new android.content.pm.PackageParser.DefaultSplitAssetLoader(parseMonolithicPackageLite, i);
        try {
            try {
                android.content.pm.PackageParser.Package parseBaseApk = parseBaseApk(file, defaultSplitAssetLoader.getBaseAssetManager(), i);
                parseBaseApk.setCodePath(file.getCanonicalPath());
                parseBaseApk.setUse32bitAbi(parseMonolithicPackageLite.use32bitAbi);
                return parseBaseApk;
            } catch (java.io.IOException e) {
                throw new android.content.pm.PackageParser.PackageParserException(-102, "Failed to get path: " + file, e);
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(defaultSplitAssetLoader);
        }
    }

    private android.content.pm.PackageParser.Package parseBaseApk(java.io.File file, android.content.res.AssetManager assetManager, int i) throws android.content.pm.PackageParser.PackageParserException {
        java.lang.String str;
        java.lang.String absolutePath = file.getAbsolutePath();
        android.content.res.XmlResourceParser xmlResourceParser = null;
        if (absolutePath.startsWith("/mnt/expand/")) {
            str = absolutePath.substring("/mnt/expand/".length(), absolutePath.indexOf(47, "/mnt/expand/".length()));
        } else {
            str = null;
        }
        this.mParseError = 1;
        this.mArchiveSourcePath = file.getAbsolutePath();
        try {
            try {
                int findCookieForPath = assetManager.findCookieForPath(absolutePath);
                if (findCookieForPath == 0) {
                    throw new android.content.pm.PackageParser.PackageParserException(-101, "Failed adding asset path: " + absolutePath);
                }
                android.content.res.XmlResourceParser openXmlResourceParser = assetManager.openXmlResourceParser(findCookieForPath, "AndroidManifest.xml");
                try {
                    android.content.res.Resources resources = new android.content.res.Resources(assetManager, this.mMetrics, null);
                    java.lang.String[] strArr = new java.lang.String[1];
                    android.content.pm.PackageParser.Package parseBaseApk = parseBaseApk(absolutePath, resources, openXmlResourceParser, i, strArr);
                    if (parseBaseApk == null) {
                        throw new android.content.pm.PackageParser.PackageParserException(this.mParseError, absolutePath + " (at " + openXmlResourceParser.getPositionDescription() + "): " + strArr[0]);
                    }
                    parseBaseApk.setVolumeUuid(str);
                    parseBaseApk.setApplicationVolumeUuid(str);
                    parseBaseApk.setBaseCodePath(absolutePath);
                    parseBaseApk.setSigningDetails(android.content.pm.PackageParser.SigningDetails.UNKNOWN);
                    libcore.io.IoUtils.closeQuietly(openXmlResourceParser);
                    return parseBaseApk;
                } catch (android.content.pm.PackageParser.PackageParserException e) {
                    throw e;
                } catch (java.lang.Exception e2) {
                    e = e2;
                    throw new android.content.pm.PackageParser.PackageParserException(-102, "Failed to read manifest from " + absolutePath, e);
                } catch (java.lang.Throwable th) {
                    th = th;
                    xmlResourceParser = openXmlResourceParser;
                    libcore.io.IoUtils.closeQuietly(xmlResourceParser);
                    throw th;
                }
            } catch (android.content.pm.PackageParser.PackageParserException e3) {
                throw e3;
            } catch (java.lang.Exception e4) {
                e = e4;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    private void parseSplitApk(android.content.pm.PackageParser.Package r12, int i, android.content.res.AssetManager assetManager, int i2) throws android.content.pm.PackageParser.PackageParserException {
        java.lang.String str = r12.splitCodePaths[i];
        this.mParseError = 1;
        this.mArchiveSourcePath = str;
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                int findCookieForPath = assetManager.findCookieForPath(str);
                if (findCookieForPath == 0) {
                    throw new android.content.pm.PackageParser.PackageParserException(-101, "Failed adding asset path: " + str);
                }
                android.content.res.XmlResourceParser openXmlResourceParser = assetManager.openXmlResourceParser(findCookieForPath, "AndroidManifest.xml");
                try {
                    java.lang.String[] strArr = new java.lang.String[1];
                    if (parseSplitApk(r12, new android.content.res.Resources(assetManager, this.mMetrics, null), openXmlResourceParser, i2, i, strArr) == null) {
                        throw new android.content.pm.PackageParser.PackageParserException(this.mParseError, str + " (at " + openXmlResourceParser.getPositionDescription() + "): " + strArr[0]);
                    }
                    libcore.io.IoUtils.closeQuietly(openXmlResourceParser);
                } catch (android.content.pm.PackageParser.PackageParserException e) {
                } catch (java.lang.Exception e2) {
                    e = e2;
                    throw new android.content.pm.PackageParser.PackageParserException(-102, "Failed to read manifest from " + str, e);
                } catch (java.lang.Throwable th) {
                    th = th;
                    xmlResourceParser = openXmlResourceParser;
                    libcore.io.IoUtils.closeQuietly(xmlResourceParser);
                    throw th;
                }
            } catch (android.content.pm.PackageParser.PackageParserException e3) {
                throw e3;
            } catch (java.lang.Exception e4) {
                e = e4;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    private android.content.pm.PackageParser.Package parseSplitApk(android.content.pm.PackageParser.Package r9, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, int i2, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, android.content.pm.PackageParser.PackageParserException {
        parsePackageSplitNames(xmlResourceParser, xmlResourceParser);
        this.mParseInstrumentationArgs = null;
        int depth = xmlResourceParser.getDepth();
        boolean z = false;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                if (xmlResourceParser.getName().equals("application")) {
                    if (z) {
                        android.util.Slog.w(TAG, "<manifest> has more than one <application>");
                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                    } else {
                        if (!parseSplitApplication(r9, resources, xmlResourceParser, i, i2, strArr)) {
                            return null;
                        }
                        z = true;
                    }
                } else {
                    android.util.Slog.w(TAG, "Unknown element under <manifest>: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                }
            }
        }
        if (!z) {
            strArr[0] = "<manifest> does not contain an <application>";
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_EMPTY;
        }
        return r9;
    }

    public static android.util.ArraySet<java.security.PublicKey> toSigningKeys(android.content.pm.Signature[] signatureArr) throws java.security.cert.CertificateException {
        android.util.ArraySet<java.security.PublicKey> arraySet = new android.util.ArraySet<>(signatureArr.length);
        for (android.content.pm.Signature signature : signatureArr) {
            arraySet.add(signature.getPublicKey());
        }
        return arraySet;
    }

    public static void collectCertificates(android.content.pm.PackageParser.Package r3, boolean z) throws android.content.pm.PackageParser.PackageParserException {
        collectCertificatesInternal(r3, z);
        int size = r3.childPackages != null ? r3.childPackages.size() : 0;
        for (int i = 0; i < size; i++) {
            r3.childPackages.get(i).mSigningDetails = r3.mSigningDetails;
        }
    }

    private static void collectCertificatesInternal(android.content.pm.PackageParser.Package r5, boolean z) throws android.content.pm.PackageParser.PackageParserException {
        r5.mSigningDetails = android.content.pm.PackageParser.SigningDetails.UNKNOWN;
        android.os.Trace.traceBegin(262144L, "collectCertificates");
        try {
            collectCertificates(r5, new java.io.File(r5.baseCodePath), z);
            if (!com.android.internal.util.ArrayUtils.isEmpty(r5.splitCodePaths)) {
                for (int i = 0; i < r5.splitCodePaths.length; i++) {
                    collectCertificates(r5, new java.io.File(r5.splitCodePaths[i]), z);
                }
            }
        } finally {
            android.os.Trace.traceEnd(262144L);
        }
    }

    private static void collectCertificates(android.content.pm.PackageParser.Package r3, java.io.File file, boolean z) throws android.content.pm.PackageParser.PackageParserException {
        android.content.pm.parsing.result.ParseResult<android.content.pm.SigningDetails> verify;
        java.lang.String absolutePath = file.getAbsolutePath();
        int minimumSignatureSchemeVersionForTargetSdk = android.util.apk.ApkSignatureVerifier.getMinimumSignatureSchemeVersionForTargetSdk(r3.applicationInfo.targetSdkVersion);
        if (r3.applicationInfo.isStaticSharedLibrary()) {
            minimumSignatureSchemeVersionForTargetSdk = 2;
        }
        android.content.pm.parsing.result.ParseTypeImpl forDefaultParsing = android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing();
        if (z) {
            verify = android.util.apk.ApkSignatureVerifier.unsafeGetCertsWithoutVerification(forDefaultParsing, absolutePath, minimumSignatureSchemeVersionForTargetSdk);
        } else {
            verify = android.util.apk.ApkSignatureVerifier.verify(forDefaultParsing, absolutePath, minimumSignatureSchemeVersionForTargetSdk);
        }
        if (verify.isError()) {
            throw new android.content.pm.PackageParser.PackageParserException(verify.getErrorCode(), verify.getErrorMessage(), verify.getException());
        }
        android.content.pm.SigningDetails result = verify.getResult();
        if (r3.mSigningDetails == android.content.pm.PackageParser.SigningDetails.UNKNOWN) {
            r3.mSigningDetails = new android.content.pm.PackageParser.SigningDetails(result.getSignatures(), result.getSignatureSchemeVersion(), result.getPublicKeys(), result.getPastSigningCertificates());
        } else if (!android.content.pm.Signature.areExactArraysMatch(r3.mSigningDetails.signatures, result.getSignatures())) {
            throw new android.content.pm.PackageParser.PackageParserException(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES, absolutePath + " has mismatched certificates");
        }
    }

    private static android.content.res.AssetManager newConfiguredAssetManager() {
        android.content.res.AssetManager assetManager = new android.content.res.AssetManager();
        assetManager.setConfiguration(0, 0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, android.os.Build.VERSION.RESOURCES_SDK_INT);
        return assetManager;
    }

    public static android.content.pm.PackageParser.ApkLite parseApkLite(java.io.File file, int i) throws android.content.pm.PackageParser.PackageParserException {
        return parseApkLiteInner(file, null, null, i);
    }

    public static android.content.pm.PackageParser.ApkLite parseApkLite(java.io.FileDescriptor fileDescriptor, java.lang.String str, int i) throws android.content.pm.PackageParser.PackageParserException {
        return parseApkLiteInner(null, fileDescriptor, str, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v10, types: [android.content.res.ApkAssets] */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v2 */
    private static android.content.pm.PackageParser.ApkLite parseApkLiteInner(java.io.File file, java.io.FileDescriptor fileDescriptor, java.lang.String str, int i) throws android.content.pm.PackageParser.PackageParserException {
        android.content.pm.PackageParser.SigningDetails signingDetails;
        java.lang.String absolutePath = fileDescriptor != 0 ? str : file.getAbsolutePath();
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                try {
                    try {
                        fileDescriptor = fileDescriptor != 0 ? android.content.res.ApkAssets.loadFromFd(fileDescriptor, str, 0, null) : android.content.res.ApkAssets.loadFromPath(absolutePath);
                        try {
                            android.content.res.XmlResourceParser openXml = fileDescriptor.openXml("AndroidManifest.xml");
                            try {
                                try {
                                    if ((i & 32) != 0) {
                                        android.content.pm.PackageParser.Package r4 = new android.content.pm.PackageParser.Package((java.lang.String) null);
                                        boolean z = (i & 16) != 0;
                                        android.os.Trace.traceBegin(262144L, "collectCertificates");
                                        try {
                                            collectCertificates(r4, file, z);
                                            android.os.Trace.traceEnd(262144L);
                                            signingDetails = r4.mSigningDetails;
                                        } catch (java.lang.Throwable th) {
                                            android.os.Trace.traceEnd(262144L);
                                            throw th;
                                        }
                                    } else {
                                        signingDetails = android.content.pm.PackageParser.SigningDetails.UNKNOWN;
                                    }
                                    android.content.pm.PackageParser.ApkLite parseApkLite = parseApkLite(absolutePath, openXml, openXml, signingDetails);
                                    libcore.io.IoUtils.closeQuietly(openXml);
                                    if (fileDescriptor != 0) {
                                        try {
                                            fileDescriptor.close();
                                        } catch (java.lang.Throwable th2) {
                                        }
                                    }
                                    return parseApkLite;
                                } catch (java.lang.Throwable th3) {
                                    th = th3;
                                    xmlResourceParser = openXml;
                                    libcore.io.IoUtils.closeQuietly(xmlResourceParser);
                                    if (fileDescriptor != 0) {
                                        try {
                                            fileDescriptor.close();
                                        } catch (java.lang.Throwable th4) {
                                        }
                                    }
                                    throw th;
                                }
                            } catch (java.io.IOException | java.lang.RuntimeException | org.xmlpull.v1.XmlPullParserException e) {
                                e = e;
                                android.util.Slog.w(TAG, "Failed to parse " + absolutePath, e);
                                throw new android.content.pm.PackageParser.PackageParserException(-102, "Failed to parse " + absolutePath, e);
                            }
                        } catch (java.io.IOException | java.lang.RuntimeException | org.xmlpull.v1.XmlPullParserException e2) {
                            e = e2;
                        }
                    } catch (java.io.IOException | java.lang.RuntimeException | org.xmlpull.v1.XmlPullParserException e3) {
                        e = e3;
                    }
                } catch (java.lang.Throwable th5) {
                    th = th5;
                    fileDescriptor = 0;
                }
            } catch (java.lang.Throwable th6) {
                th = th6;
            }
        } catch (java.io.IOException e4) {
            throw new android.content.pm.PackageParser.PackageParserException(-100, "Failed to parse " + absolutePath);
        }
    }

    public static java.lang.String validateName(java.lang.String str, boolean z, boolean z2) {
        int length = str.length();
        boolean z3 = false;
        boolean z4 = true;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if ((charAt >= 'a' && charAt <= 'z') || (charAt >= 'A' && charAt <= 'Z')) {
                z4 = false;
            } else if (z4 || ((charAt < '0' || charAt > '9') && charAt != '_')) {
                if (charAt == '.') {
                    z3 = true;
                    z4 = true;
                } else {
                    return "bad character '" + charAt + "'";
                }
            }
        }
        if (z2 && !android.os.FileUtils.isValidExtFilename(str)) {
            return "Invalid filename";
        }
        if (z3 || !z) {
            return null;
        }
        return "must have at least one '.' separator";
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0086  */
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static android.util.Pair<java.lang.String, java.lang.String> parsePackageSplitNames(org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, android.content.pm.PackageParser.PackageParserException {
        int next;
        java.lang.String validateName;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new android.content.pm.PackageParser.PackageParserException(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "No start tag found");
        }
        if (!xmlPullParser.getName().equals("manifest")) {
            throw new android.content.pm.PackageParser.PackageParserException(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "No <manifest> tag");
        }
        java.lang.String str = null;
        java.lang.String attributeValue = attributeSet.getAttributeValue(null, "package");
        if (!"android".equals(attributeValue) && (validateName = validateName(attributeValue, true, true)) != null) {
            throw new android.content.pm.PackageParser.PackageParserException(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME, "Invalid manifest package: " + validateName);
        }
        java.lang.String attributeValue2 = attributeSet.getAttributeValue(null, "split");
        if (attributeValue2 != null) {
            if (attributeValue2.length() != 0) {
                java.lang.String validateName2 = validateName(attributeValue2, false, false);
                if (validateName2 != null) {
                    throw new android.content.pm.PackageParser.PackageParserException(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME, "Invalid manifest split: " + validateName2);
                }
            }
            java.lang.String intern = attributeValue.intern();
            if (str != null) {
                str = str.intern();
            }
            return android.util.Pair.create(intern, str);
        }
        str = attributeValue2;
        java.lang.String intern2 = attributeValue.intern();
        if (str != null) {
        }
        return android.util.Pair.create(intern2, str);
    }

    private static android.content.pm.PackageParser.ApkLite parseApkLite(java.lang.String str, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.pm.PackageParser.SigningDetails signingDetails) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException, android.content.pm.PackageParser.PackageParserException {
        boolean z;
        int i;
        boolean z2;
        java.lang.String str2;
        android.util.Pair<java.lang.String, java.lang.String> parsePackageSplitNames = parsePackageSplitNames(xmlPullParser, attributeSet);
        int i2 = -1;
        boolean z3 = false;
        boolean z4 = false;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        boolean z5 = false;
        boolean z6 = false;
        java.lang.String str3 = null;
        for (int i6 = 0; i6 < attributeSet.getAttributeCount(); i6++) {
            java.lang.String attributeName = attributeSet.getAttributeName(i6);
            if (attributeName.equals("installLocation")) {
                i2 = attributeSet.getAttributeIntValue(i6, -1);
            } else if (attributeName.equals("versionCode")) {
                i3 = attributeSet.getAttributeIntValue(i6, 0);
            } else if (attributeName.equals("versionCodeMajor")) {
                i4 = attributeSet.getAttributeIntValue(i6, 0);
            } else if (attributeName.equals("revisionCode")) {
                i5 = attributeSet.getAttributeIntValue(i6, 0);
            } else if (attributeName.equals("coreApp")) {
                z5 = attributeSet.getAttributeBooleanValue(i6, false);
            } else if (attributeName.equals("isolatedSplits")) {
                z6 = attributeSet.getAttributeBooleanValue(i6, false);
            } else if (attributeName.equals("configForSplit")) {
                str3 = attributeSet.getAttributeValue(i6);
            } else if (attributeName.equals("isFeatureSplit")) {
                z3 = attributeSet.getAttributeBooleanValue(i6, false);
            } else if (attributeName.equals("isSplitRequired")) {
                z4 = attributeSet.getAttributeBooleanValue(i6, false);
            }
        }
        int i7 = 1;
        int depth = xmlPullParser.getDepth() + 1;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        boolean z7 = false;
        int i8 = 0;
        boolean z8 = false;
        boolean z9 = false;
        boolean z10 = false;
        boolean z11 = false;
        int i9 = 0;
        int i10 = 0;
        boolean z12 = true;
        int i11 = 1;
        java.lang.String str4 = null;
        java.lang.String str5 = null;
        java.lang.String str6 = null;
        java.lang.String str7 = null;
        while (true) {
            int next = xmlPullParser.next();
            z = z7;
            if (next == i7 || (next == 3 && xmlPullParser.getDepth() < depth)) {
                break;
            }
            if (next != 3 && next != 4 && xmlPullParser.getDepth() == depth) {
                if ("package-verifier".equals(xmlPullParser.getName())) {
                    android.content.pm.VerifierInfo parseVerifier = parseVerifier(attributeSet);
                    if (parseVerifier != null) {
                        arrayList.add(parseVerifier);
                    }
                } else if ("application".equals(xmlPullParser.getName())) {
                    for (int i12 = 0; i12 < attributeSet.getAttributeCount(); i12++) {
                        java.lang.String attributeName2 = attributeSet.getAttributeName(i12);
                        if ("debuggable".equals(attributeName2)) {
                            z8 = attributeSet.getAttributeBooleanValue(i12, false);
                        }
                        if ("multiArch".equals(attributeName2)) {
                            z9 = attributeSet.getAttributeBooleanValue(i12, false);
                        }
                        if ("use32bitAbi".equals(attributeName2)) {
                            z10 = attributeSet.getAttributeBooleanValue(i12, false);
                        }
                        if ("extractNativeLibs".equals(attributeName2)) {
                            z12 = attributeSet.getAttributeBooleanValue(i12, true);
                        }
                        if ("useEmbeddedDex".equals(attributeName2)) {
                            z11 = attributeSet.getAttributeBooleanValue(i12, false);
                        }
                        if (attributeName2.equals("rollbackDataPolicy")) {
                            i10 = attributeSet.getAttributeIntValue(i12, 0);
                        }
                    }
                    z7 = z;
                    i7 = 1;
                } else if ("overlay".equals(xmlPullParser.getName())) {
                    for (int i13 = 0; i13 < attributeSet.getAttributeCount(); i13++) {
                        java.lang.String attributeName3 = attributeSet.getAttributeName(i13);
                        if ("requiredSystemPropertyName".equals(attributeName3)) {
                            str6 = attributeSet.getAttributeValue(i13);
                        } else if ("requiredSystemPropertyValue".equals(attributeName3)) {
                            str4 = attributeSet.getAttributeValue(i13);
                        } else if ("targetPackage".equals(attributeName3)) {
                            str5 = attributeSet.getAttributeValue(i13);
                        } else if ("isStatic".equals(attributeName3)) {
                            z = attributeSet.getAttributeBooleanValue(i13, false);
                        } else if ("priority".equals(attributeName3)) {
                            i8 = attributeSet.getAttributeIntValue(i13, 0);
                        }
                    }
                    z7 = z;
                    i7 = 1;
                } else if ("uses-split".equals(xmlPullParser.getName())) {
                    if (str7 == null) {
                        str7 = attributeSet.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                        if (str7 == null) {
                            throw new android.content.pm.PackageParser.PackageParserException(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "<uses-split> tag requires 'android:name' attribute");
                        }
                        z7 = z;
                        i7 = 1;
                    } else {
                        android.util.Slog.w(TAG, "Only one <uses-split> permitted. Ignoring others.");
                    }
                } else if ("uses-sdk".equals(xmlPullParser.getName())) {
                    for (int i14 = 0; i14 < attributeSet.getAttributeCount(); i14++) {
                        java.lang.String attributeName4 = attributeSet.getAttributeName(i14);
                        if ("targetSdkVersion".equals(attributeName4)) {
                            i9 = attributeSet.getAttributeIntValue(i14, 0);
                        }
                        if ("minSdkVersion".equals(attributeName4)) {
                            i11 = attributeSet.getAttributeIntValue(i14, 1);
                        }
                    }
                    i7 = 1;
                    z7 = z;
                }
            }
            i7 = 1;
            z7 = z;
        }
        if (android.content.pm.parsing.FrameworkParsingPackageUtils.checkRequiredSystemProperties(str6, str4)) {
            i = i8;
            z2 = z;
            str2 = str5;
        } else {
            android.util.Slog.i(TAG, "Skipping target and overlay pair " + str5 + " and " + str + ": overlay ignored due to required system property: " + str6 + " with value: " + str4);
            str2 = null;
            z2 = false;
            i = 0;
        }
        return new android.content.pm.PackageParser.ApkLite(str, parsePackageSplitNames.first, parsePackageSplitNames.second, z3, str3, str7, z4, i3, i4, i5, i2, arrayList, signingDetails, z5, z8, false, z9, z10, z11, z12, z6, str2, z2, i, i11, i9, i10);
    }

    private boolean parseBaseApkChild(android.content.pm.PackageParser.Package r10, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue = xmlResourceParser.getAttributeValue(null, "package");
        if (validateName(attributeValue, true, false) != null) {
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME;
            return false;
        }
        if (attributeValue.equals(r10.packageName)) {
            java.lang.String str = "Child package name cannot be equal to parent package name: " + r10.packageName;
            android.util.Slog.w(TAG, str);
            strArr[0] = str;
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        if (r10.hasChildPackage(attributeValue)) {
            java.lang.String str2 = "Duplicate child package:" + attributeValue;
            android.util.Slog.w(TAG, str2);
            strArr[0] = str2;
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        android.content.pm.PackageParser.Package r2 = new android.content.pm.PackageParser.Package(attributeValue);
        r2.mVersionCode = r10.mVersionCode;
        r2.baseRevisionCode = r10.baseRevisionCode;
        r2.mVersionName = r10.mVersionName;
        r2.applicationInfo.targetSdkVersion = r10.applicationInfo.targetSdkVersion;
        r2.applicationInfo.minSdkVersion = r10.applicationInfo.minSdkVersion;
        android.content.pm.PackageParser.Package parseBaseApkCommon = parseBaseApkCommon(r2, CHILD_PACKAGE_TAGS, resources, xmlResourceParser, i, strArr);
        if (parseBaseApkCommon == null) {
            return false;
        }
        if (r10.childPackages == null) {
            r10.childPackages = new java.util.ArrayList<>();
        }
        r10.childPackages.add(parseBaseApkCommon);
        parseBaseApkCommon.parentPackage = r10;
        return true;
    }

    private android.content.pm.PackageParser.Package parseBaseApk(java.lang.String str, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        try {
            android.util.Pair<java.lang.String, java.lang.String> parsePackageSplitNames = parsePackageSplitNames(xmlResourceParser, xmlResourceParser);
            java.lang.String str2 = parsePackageSplitNames.first;
            java.lang.String str3 = parsePackageSplitNames.second;
            if (!android.text.TextUtils.isEmpty(str3)) {
                strArr[0] = "Expected base APK, but found split " + str3;
                this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME;
                return null;
            }
            android.content.pm.PackageParser.Package r9 = new android.content.pm.PackageParser.Package(str2);
            android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifest);
            r9.mVersionCode = obtainAttributes.getInteger(1, 0);
            r9.mVersionCodeMajor = obtainAttributes.getInteger(11, 0);
            r9.applicationInfo.setVersionCode(r9.getLongVersionCode());
            r9.baseRevisionCode = obtainAttributes.getInteger(5, 0);
            r9.mVersionName = obtainAttributes.getNonConfigurationString(2, 0);
            if (r9.mVersionName != null) {
                r9.mVersionName = r9.mVersionName.intern();
            }
            r9.coreApp = xmlResourceParser.getAttributeBooleanValue(null, "coreApp", false);
            if (obtainAttributes.getBoolean(6, false)) {
                r9.applicationInfo.privateFlags |= 32768;
            }
            r9.mCompileSdkVersion = obtainAttributes.getInteger(9, 0);
            r9.applicationInfo.compileSdkVersion = r9.mCompileSdkVersion;
            r9.mCompileSdkVersionCodename = obtainAttributes.getNonConfigurationString(10, 0);
            if (r9.mCompileSdkVersionCodename != null) {
                r9.mCompileSdkVersionCodename = r9.mCompileSdkVersionCodename.intern();
            }
            r9.applicationInfo.compileSdkVersionCodename = r9.mCompileSdkVersionCodename;
            obtainAttributes.recycle();
            return parseBaseApkCommon(r9, null, resources, xmlResourceParser, i, strArr);
        } catch (android.content.pm.PackageParser.PackageParserException e) {
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME;
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0717, code lost:
    
        if (r16 != false) goto L261;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x071f, code lost:
    
        if (r27.instrumentation.size() != 0) goto L261;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0721, code lost:
    
        r2 = 0;
        r32[0] = "<manifest> does not contain an <application> or <instrumentation>";
        r26.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_EMPTY;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x072c, code lost:
    
        r0 = android.content.pm.PackageParser.NEW_PERMISSIONS.length;
        r12 = r1;
        r1 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0732, code lost:
    
        if (r1 >= r0) goto L381;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0734, code lost:
    
        r3 = android.content.pm.PackageParser.NEW_PERMISSIONS[r1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x073e, code lost:
    
        if (r27.applicationInfo.targetSdkVersion < r3.sdkVersion) goto L267;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0749, code lost:
    
        if (r27.requestedPermissions.contains(r3.name) != false) goto L384;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x074b, code lost:
    
        if (r12 != null) goto L271;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x074d, code lost:
    
        r12 = new java.lang.StringBuilder(128);
        r12.append(r27.packageName);
        r12.append(": compat added ");
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0764, code lost:
    
        r12.append(r3.name);
        r27.requestedPermissions.add(r3.name);
        r27.implicitPermissions.add(r3.name);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0777, code lost:
    
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x075f, code lost:
    
        r12.append(' ');
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x077a, code lost:
    
        if (r12 == null) goto L329;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x077c, code lost:
    
        android.util.Slog.i(android.content.pm.PackageParser.TAG, r12.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:368:0x0225, code lost:
    
        r32[0] = "<overlay> priority must be between 0 and 9999";
        r26.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
     */
    /* JADX WARN: Code restructure failed: missing block: B:369:0x022d, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0783, code lost:
    
        r0 = android.app.ActivityThread.getPermissionManager().getSplitPermissions();
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x078d, code lost:
    
        r0 = java.util.Collections.emptyList();
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x072b, code lost:
    
        r2 = 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.content.pm.PackageParser.Package parseBaseApkCommon(android.content.pm.PackageParser.Package r27, java.util.Set<java.lang.String> set, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String nonConfigurationString;
        int i2;
        int i3;
        int i4;
        java.lang.StringBuilder sb;
        int i5;
        java.util.List<android.content.pm.permission.SplitPermissionInfoParcelable> emptyList;
        int i6;
        int i7;
        int i8;
        java.lang.StringBuilder sb2;
        int i9;
        int i10;
        char c;
        int i11;
        java.lang.StringBuilder sb3;
        int i12;
        int i13;
        int i14;
        java.lang.String str;
        int i15;
        java.lang.String str2;
        java.util.Set<java.lang.String> set2 = set;
        java.lang.StringBuilder sb4 = null;
        this.mParseInstrumentationArgs = null;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifest);
        int integer = obtainAttributes.getInteger(13, 0);
        int i16 = 3;
        int i17 = 1;
        if ((integer == 0 || integer >= android.os.Build.VERSION.RESOURCES_SDK_INT) && (nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0)) != null && nonConfigurationString.length() > 0) {
            java.lang.String validateName = validateName(nonConfigurationString, true, true);
            if (validateName != null && !"android".equals(r27.packageName)) {
                strArr[0] = "<manifest> specifies bad sharedUserId name \"" + nonConfigurationString + "\": " + validateName;
                this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID;
                return null;
            }
            r27.mSharedUserId = nonConfigurationString.intern();
            r27.mSharedUserLabel = obtainAttributes.getResourceId(3, 0);
        }
        int i18 = 4;
        r27.installLocation = obtainAttributes.getInteger(4, -1);
        r27.applicationInfo.installLocation = r27.installLocation;
        r27.applicationInfo.targetSandboxVersion = obtainAttributes.getInteger(7, 1);
        if ((i & 8) != 0) {
            r27.applicationInfo.flags |= 262144;
        }
        int depth = xmlResourceParser.getDepth();
        boolean z = false;
        int i19 = 1;
        int i20 = 1;
        int i21 = 1;
        int i22 = 1;
        int i23 = 1;
        int i24 = 1;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == i17) {
                i2 = i19;
                i3 = i20;
                i4 = i21;
                sb = sb4;
                break;
            }
            if (next == i16 && xmlResourceParser.getDepth() <= depth) {
                i2 = i19;
                i3 = i20;
                i4 = i21;
                sb = sb4;
                break;
            }
            if (next == i16) {
                i6 = i19;
                i7 = i21;
                i8 = depth;
                sb2 = sb4;
                i9 = i16;
                i10 = 1;
                c = 7;
                i11 = i20;
            } else if (next == i18) {
                i6 = i19;
                i7 = i21;
                i8 = depth;
                sb2 = sb4;
                i9 = i16;
                i10 = 1;
                c = 7;
                i11 = i20;
            } else {
                java.lang.String name = xmlResourceParser.getName();
                if (set2 != null && !set2.contains(name)) {
                    android.util.Slog.w(TAG, "Skipping unsupported element under <manifest>: " + name + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                    i6 = i19;
                    i11 = i20;
                    i7 = i21;
                    i8 = depth;
                    i9 = 3;
                    sb2 = null;
                    i10 = 1;
                    c = 7;
                } else if (name.equals("application")) {
                    if (z) {
                        android.util.Slog.w(TAG, "<manifest> has more than one <application>");
                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        i6 = i19;
                        i11 = i20;
                        i7 = i21;
                        i8 = depth;
                        i9 = 3;
                        sb2 = null;
                        i10 = 1;
                        c = 7;
                    } else {
                        int i25 = i20;
                        int i26 = i21;
                        i8 = depth;
                        i6 = i19;
                        if (parseBaseApplication(r27, resources, xmlResourceParser, i, strArr)) {
                            i21 = i26;
                            i20 = i25;
                            c = 7;
                            i9 = 3;
                            sb3 = null;
                            i10 = 1;
                            z = true;
                            i16 = i9;
                            i17 = i10;
                            depth = i8;
                            i18 = 4;
                            set2 = set;
                            sb4 = sb3;
                            i19 = i6;
                        } else {
                            return null;
                        }
                    }
                } else {
                    i6 = i19;
                    i11 = i20;
                    i8 = depth;
                    int i27 = i21;
                    if (name.equals("overlay")) {
                        android.content.res.TypedArray obtainAttributes2 = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestResourceOverlay);
                        r27.mOverlayTarget = obtainAttributes2.getString(1);
                        r27.mOverlayTargetName = obtainAttributes2.getString(3);
                        r27.mOverlayCategory = obtainAttributes2.getString(2);
                        r27.mOverlayPriority = obtainAttributes2.getInt(0, 0);
                        r27.mOverlayIsStatic = obtainAttributes2.getBoolean(4, false);
                        java.lang.String string = obtainAttributes2.getString(5);
                        java.lang.String string2 = obtainAttributes2.getString(6);
                        obtainAttributes2.recycle();
                        if (r27.mOverlayTarget == null) {
                            strArr[0] = "<overlay> does not specify a target package";
                            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                            return null;
                        }
                        if (r27.mOverlayPriority < 0 || r27.mOverlayPriority > 9999) {
                            break;
                        }
                        if (!android.content.pm.parsing.FrameworkParsingPackageUtils.checkRequiredSystemProperties(string, string2)) {
                            android.util.Slog.i(TAG, "Skipping target and overlay pair " + r27.mOverlayTarget + " and " + r27.baseCodePath + ": overlay ignored due to required system property: " + string + " with value: " + string2);
                            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_SKIPPED;
                            return null;
                        }
                        r27.applicationInfo.privateFlags |= 268435456;
                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        i7 = i27;
                        i9 = 3;
                        sb3 = null;
                        i10 = 1;
                        c = 7;
                    } else if (name.equals("key-sets")) {
                        if (!parseKeySets(r27, resources, xmlResourceParser, strArr)) {
                            return null;
                        }
                        i7 = i27;
                        i9 = 3;
                        sb3 = null;
                        i10 = 1;
                        c = 7;
                    } else if (name.equals("permission-group")) {
                        if (parsePermissionGroup(r27, i, resources, xmlResourceParser, strArr)) {
                            sb3 = null;
                            c = 7;
                            i7 = i27;
                            i9 = 3;
                            i10 = 1;
                        } else {
                            return null;
                        }
                    } else {
                        sb3 = null;
                        if (name.equals("permission")) {
                            if (!parsePermission(r27, resources, xmlResourceParser, strArr)) {
                                return null;
                            }
                            i7 = i27;
                            i9 = 3;
                            i10 = 1;
                            c = 7;
                        } else if (name.equals("permission-tree")) {
                            if (!parsePermissionTree(r27, resources, xmlResourceParser, strArr)) {
                                return null;
                            }
                            i7 = i27;
                            i9 = 3;
                            i10 = 1;
                            c = 7;
                        } else if (name.equals("uses-permission")) {
                            if (!parseUsesPermission(r27, resources, xmlResourceParser)) {
                                return null;
                            }
                            i7 = i27;
                            i9 = 3;
                            sb3 = null;
                            i10 = 1;
                            c = 7;
                        } else {
                            if (name.equals("uses-permission-sdk-m")) {
                                i7 = i27;
                                i9 = 3;
                                i10 = 1;
                                c = 7;
                            } else if (name.equals("uses-permission-sdk-23")) {
                                i7 = i27;
                                i9 = 3;
                                i10 = 1;
                                c = 7;
                            } else if (name.equals("uses-configuration")) {
                                android.content.pm.ConfigurationInfo configurationInfo = new android.content.pm.ConfigurationInfo();
                                android.content.res.TypedArray obtainAttributes3 = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUsesConfiguration);
                                configurationInfo.reqTouchScreen = obtainAttributes3.getInt(0, 0);
                                configurationInfo.reqKeyboardType = obtainAttributes3.getInt(1, 0);
                                if (obtainAttributes3.getBoolean(2, false)) {
                                    configurationInfo.reqInputFeatures |= 1;
                                }
                                configurationInfo.reqNavigation = obtainAttributes3.getInt(3, 0);
                                if (obtainAttributes3.getBoolean(4, false)) {
                                    configurationInfo.reqInputFeatures = 2 | configurationInfo.reqInputFeatures;
                                }
                                obtainAttributes3.recycle();
                                r27.configPreferences = com.android.internal.util.ArrayUtils.add(r27.configPreferences, configurationInfo);
                                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                i7 = i27;
                                i9 = 3;
                                sb3 = null;
                                i10 = 1;
                                c = 7;
                            } else if (name.equals("uses-feature")) {
                                android.content.pm.FeatureInfo parseUsesFeature = parseUsesFeature(resources, xmlResourceParser);
                                r27.reqFeatures = com.android.internal.util.ArrayUtils.add(r27.reqFeatures, parseUsesFeature);
                                if (parseUsesFeature.name == null) {
                                    android.content.pm.ConfigurationInfo configurationInfo2 = new android.content.pm.ConfigurationInfo();
                                    configurationInfo2.reqGlEsVersion = parseUsesFeature.reqGlEsVersion;
                                    r27.configPreferences = com.android.internal.util.ArrayUtils.add(r27.configPreferences, configurationInfo2);
                                }
                                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                i7 = i27;
                                i9 = 3;
                                sb3 = null;
                                i10 = 1;
                                c = 7;
                            } else if (name.equals("feature-group")) {
                                android.content.pm.FeatureGroupInfo featureGroupInfo = new android.content.pm.FeatureGroupInfo();
                                int depth2 = xmlResourceParser.getDepth();
                                java.util.ArrayList arrayList = null;
                                while (true) {
                                    int next2 = xmlResourceParser.next();
                                    if (next2 == 1 || (next2 == 3 && xmlResourceParser.getDepth() <= depth2)) {
                                        break;
                                    }
                                    if (next2 != 3) {
                                        int i28 = depth2;
                                        if (next2 == 4) {
                                            i12 = i28;
                                        } else {
                                            java.lang.String name2 = xmlResourceParser.getName();
                                            if (name2.equals("uses-feature")) {
                                                android.content.pm.FeatureInfo parseUsesFeature2 = parseUsesFeature(resources, xmlResourceParser);
                                                parseUsesFeature2.flags |= 1;
                                                i13 = i28;
                                                arrayList = com.android.internal.util.ArrayUtils.add((java.util.ArrayList<android.content.pm.FeatureInfo>) arrayList, parseUsesFeature2);
                                            } else {
                                                i13 = i28;
                                                android.util.Slog.w(TAG, "Unknown element under <feature-group>: " + name2 + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                                            }
                                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                            depth2 = i13;
                                        }
                                    } else {
                                        i12 = depth2;
                                    }
                                    depth2 = i12;
                                }
                                if (arrayList != null) {
                                    featureGroupInfo.features = new android.content.pm.FeatureInfo[arrayList.size()];
                                    featureGroupInfo.features = (android.content.pm.FeatureInfo[]) arrayList.toArray(featureGroupInfo.features);
                                }
                                r27.featureGroups = com.android.internal.util.ArrayUtils.add(r27.featureGroups, featureGroupInfo);
                                i7 = i27;
                                i9 = 3;
                                sb3 = null;
                                i10 = 1;
                                c = 7;
                            } else if (name.equals("uses-sdk")) {
                                if (SDK_VERSION > 0) {
                                    android.content.res.TypedArray obtainAttributes4 = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUsesSdk);
                                    android.util.TypedValue peekValue = obtainAttributes4.peekValue(0);
                                    if (peekValue == null) {
                                        i14 = 1;
                                        str = null;
                                    } else if (peekValue.type == 3 && peekValue.string != null) {
                                        str = peekValue.string.toString();
                                        i14 = 1;
                                    } else {
                                        i14 = peekValue.data;
                                        str = null;
                                    }
                                    android.util.TypedValue peekValue2 = obtainAttributes4.peekValue(1);
                                    if (peekValue2 != null) {
                                        if (peekValue2.type == 3 && peekValue2.string != null) {
                                            java.lang.String charSequence = peekValue2.string.toString();
                                            if (str != null) {
                                                str2 = charSequence;
                                                i15 = 0;
                                            } else {
                                                str = charSequence;
                                                str2 = str;
                                                i15 = 0;
                                            }
                                        } else {
                                            i15 = peekValue2.data;
                                            str2 = null;
                                        }
                                    } else {
                                        i15 = i14;
                                        str2 = str;
                                    }
                                    obtainAttributes4.recycle();
                                    int computeMinSdkVersion = computeMinSdkVersion(i14, str, SDK_VERSION, SDK_CODENAMES, strArr);
                                    if (computeMinSdkVersion < 0) {
                                        this.mParseError = -12;
                                        return null;
                                    }
                                    int computeTargetSdkVersion = computeTargetSdkVersion(i15, str2, SDK_CODENAMES, strArr);
                                    if (computeTargetSdkVersion < 0) {
                                        this.mParseError = -12;
                                        return null;
                                    }
                                    r27.applicationInfo.minSdkVersion = computeMinSdkVersion;
                                    r27.applicationInfo.targetSdkVersion = computeTargetSdkVersion;
                                }
                                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                i7 = i27;
                                i9 = 3;
                                sb3 = null;
                                i10 = 1;
                                c = 7;
                            } else if (name.equals("supports-screens")) {
                                android.content.res.TypedArray obtainAttributes5 = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestSupportsScreens);
                                r27.applicationInfo.requiresSmallestWidthDp = obtainAttributes5.getInteger(6, 0);
                                r27.applicationInfo.compatibleWidthLimitDp = obtainAttributes5.getInteger(7, 0);
                                r27.applicationInfo.largestWidthLimitDp = obtainAttributes5.getInteger(8, 0);
                                i10 = 1;
                                int integer2 = obtainAttributes5.getInteger(1, i27);
                                i20 = obtainAttributes5.getInteger(2, i11);
                                int integer3 = obtainAttributes5.getInteger(3, i6);
                                int integer4 = obtainAttributes5.getInteger(5, i22);
                                int integer5 = obtainAttributes5.getInteger(4, i23);
                                int integer6 = obtainAttributes5.getInteger(0, i24);
                                obtainAttributes5.recycle();
                                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                c = 7;
                                i6 = integer3;
                                i9 = 3;
                                i22 = integer4;
                                i23 = integer5;
                                i24 = integer6;
                                i21 = integer2;
                                sb3 = null;
                                i16 = i9;
                                i17 = i10;
                                depth = i8;
                                i18 = 4;
                                set2 = set;
                                sb4 = sb3;
                                i19 = i6;
                            } else {
                                i9 = 3;
                                i10 = 1;
                                if (name.equals("protected-broadcast")) {
                                    android.content.res.TypedArray obtainAttributes6 = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestProtectedBroadcast);
                                    java.lang.String nonResourceString = obtainAttributes6.getNonResourceString(0);
                                    obtainAttributes6.recycle();
                                    if (nonResourceString != null) {
                                        if (r27.protectedBroadcasts == null) {
                                            r27.protectedBroadcasts = new java.util.ArrayList<>();
                                        }
                                        if (!r27.protectedBroadcasts.contains(nonResourceString)) {
                                            r27.protectedBroadcasts.add(nonResourceString.intern());
                                        }
                                    }
                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                    c = 7;
                                    i7 = i27;
                                    sb3 = null;
                                } else if (name.equals("instrumentation")) {
                                    if (parseInstrumentation(r27, resources, xmlResourceParser, strArr) != null) {
                                        c = 7;
                                        i7 = i27;
                                        sb3 = null;
                                    } else {
                                        return null;
                                    }
                                } else if (name.equals("original-package")) {
                                    android.content.res.TypedArray obtainAttributes7 = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestOriginalPackage);
                                    java.lang.String nonConfigurationString2 = obtainAttributes7.getNonConfigurationString(0, 0);
                                    if (!r27.packageName.equals(nonConfigurationString2)) {
                                        if (r27.mOriginalPackages == null) {
                                            r27.mOriginalPackages = new java.util.ArrayList<>();
                                            r27.mRealPackage = r27.packageName;
                                        }
                                        r27.mOriginalPackages.add(nonConfigurationString2);
                                    }
                                    obtainAttributes7.recycle();
                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                    c = 7;
                                    i7 = i27;
                                    sb3 = null;
                                } else if (name.equals("adopt-permissions")) {
                                    android.content.res.TypedArray obtainAttributes8 = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestOriginalPackage);
                                    java.lang.String nonConfigurationString3 = obtainAttributes8.getNonConfigurationString(0, 0);
                                    obtainAttributes8.recycle();
                                    if (nonConfigurationString3 != null) {
                                        if (r27.mAdoptPermissions == null) {
                                            r27.mAdoptPermissions = new java.util.ArrayList<>();
                                        }
                                        r27.mAdoptPermissions.add(nonConfigurationString3);
                                    }
                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                    c = 7;
                                    i7 = i27;
                                    sb3 = null;
                                } else if (name.equals("uses-gl-texture")) {
                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                    c = 7;
                                    i7 = i27;
                                    sb2 = null;
                                } else if (name.equals("compatible-screens")) {
                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                    c = 7;
                                    i7 = i27;
                                    sb2 = null;
                                } else if (name.equals("supports-input")) {
                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                    c = 7;
                                    i7 = i27;
                                    sb2 = null;
                                } else if (name.equals("eat-comment")) {
                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                    c = 7;
                                    i7 = i27;
                                    sb2 = null;
                                } else if (name.equals("package")) {
                                    if (!MULTI_PACKAGE_APK_ENABLED) {
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                        c = 7;
                                        i7 = i27;
                                        sb2 = null;
                                    } else {
                                        c = 7;
                                        i7 = i27;
                                        if (parseBaseApkChild(r27, resources, xmlResourceParser, i, strArr)) {
                                            sb3 = null;
                                        } else {
                                            return null;
                                        }
                                    }
                                } else {
                                    c = 7;
                                    i7 = i27;
                                    if (name.equals("restrict-update")) {
                                        if ((i & 16) != 0) {
                                            android.content.res.TypedArray obtainAttributes9 = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestRestrictUpdate);
                                            java.lang.String nonConfigurationString4 = obtainAttributes9.getNonConfigurationString(0, 0);
                                            obtainAttributes9.recycle();
                                            r27.restrictUpdateHash = null;
                                            if (nonConfigurationString4 != null) {
                                                int length = nonConfigurationString4.length();
                                                byte[] bArr = new byte[length / 2];
                                                for (int i29 = 0; i29 < length; i29 += 2) {
                                                    bArr[i29 / 2] = (byte) ((java.lang.Character.digit(nonConfigurationString4.charAt(i29), 16) << 4) + java.lang.Character.digit(nonConfigurationString4.charAt(i29 + 1), 16));
                                                }
                                                r27.restrictUpdateHash = bArr;
                                            }
                                        }
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                        sb3 = null;
                                    } else {
                                        android.util.Slog.w(TAG, "Unknown element under <manifest>: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                        sb2 = null;
                                    }
                                }
                            }
                            if (parseUsesPermission(r27, resources, xmlResourceParser)) {
                                sb3 = null;
                            } else {
                                return null;
                            }
                        }
                    }
                    i20 = i11;
                    i21 = i7;
                    i16 = i9;
                    i17 = i10;
                    depth = i8;
                    i18 = 4;
                    set2 = set;
                    sb4 = sb3;
                    i19 = i6;
                }
            }
            i17 = i10;
            i20 = i11;
            i21 = i7;
            depth = i8;
            i18 = 4;
            i16 = i9;
            sb4 = sb2;
            i19 = i6;
            set2 = set;
        }
        int size = emptyList.size();
        java.util.ArrayList arrayList2 = new java.util.ArrayList(size);
        for (int i30 = i5; i30 < size; i30++) {
            android.content.pm.permission.SplitPermissionInfoParcelable splitPermissionInfoParcelable = emptyList.get(i30);
            arrayList2.add(new android.permission.PermissionManager.SplitPermissionInfo(splitPermissionInfoParcelable.getSplitPermission(), splitPermissionInfoParcelable.getNewPermissions(), splitPermissionInfoParcelable.getTargetSdk()));
        }
        int size2 = arrayList2.size();
        for (int i31 = i5; i31 < size2; i31++) {
            android.permission.PermissionManager.SplitPermissionInfo splitPermissionInfo = (android.permission.PermissionManager.SplitPermissionInfo) arrayList2.get(i31);
            if (r27.applicationInfo.targetSdkVersion < splitPermissionInfo.getTargetSdk() && r27.requestedPermissions.contains(splitPermissionInfo.getSplitPermission())) {
                java.util.List<java.lang.String> newPermissions = splitPermissionInfo.getNewPermissions();
                for (int i32 = i5; i32 < newPermissions.size(); i32++) {
                    java.lang.String str3 = newPermissions.get(i32);
                    if (!r27.requestedPermissions.contains(str3)) {
                        r27.requestedPermissions.add(str3);
                        r27.implicitPermissions.add(str3);
                    }
                }
            }
        }
        if (i4 < 0 || (i4 > 0 && r27.applicationInfo.targetSdkVersion >= 4)) {
            r27.applicationInfo.flags |= 512;
        }
        if (i3 != 0) {
            r27.applicationInfo.flags |= 1024;
        }
        if (i2 < 0 || (i2 > 0 && r27.applicationInfo.targetSdkVersion >= 4)) {
            r27.applicationInfo.flags |= 2048;
        }
        if (i22 < 0 || (i22 > 0 && r27.applicationInfo.targetSdkVersion >= 9)) {
            r27.applicationInfo.flags |= 524288;
        }
        if (i23 < 0 || (i23 > 0 && r27.applicationInfo.targetSdkVersion >= 4)) {
            r27.applicationInfo.flags |= 4096;
        }
        if (i24 < 0 || (i24 > 0 && r27.applicationInfo.targetSdkVersion >= 4)) {
            r27.applicationInfo.flags |= 8192;
        }
        if (r27.applicationInfo.usesCompatibilityMode()) {
            adjustPackageToBeUnresizeableAndUnpipable(r27);
        }
        return r27;
    }

    private void adjustPackageToBeUnresizeableAndUnpipable(android.content.pm.PackageParser.Package r4) {
        java.util.Iterator<android.content.pm.PackageParser.Activity> it = r4.activities.iterator();
        while (it.hasNext()) {
            android.content.pm.PackageParser.Activity next = it.next();
            next.info.resizeMode = 0;
            next.info.flags &= -4194305;
        }
    }

    private static boolean matchTargetCode(java.lang.String[] strArr, java.lang.String str) {
        int indexOf = str.indexOf(46);
        if (indexOf != -1) {
            str = str.substring(0, indexOf);
        }
        return com.android.internal.util.ArrayUtils.contains(strArr, str);
    }

    public static int computeTargetSdkVersion(int i, java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2) {
        if (str == null) {
            return i;
        }
        if (matchTargetCode(strArr, str)) {
            return 10000;
        }
        if (strArr.length > 0) {
            strArr2[0] = "Requires development platform " + str + " (current platform is any of " + java.util.Arrays.toString(strArr) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            return -1;
        }
        strArr2[0] = "Requires development platform " + str + " but this is a release platform.";
        return -1;
    }

    public static int computeMinSdkVersion(int i, java.lang.String str, int i2, java.lang.String[] strArr, java.lang.String[] strArr2) {
        if (str == null) {
            if (i <= i2) {
                return i;
            }
            strArr2[0] = "Requires newer sdk version #" + i + " (current version is #" + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            return -1;
        }
        if (matchTargetCode(strArr, str)) {
            return 10000;
        }
        if (strArr.length > 0) {
            strArr2[0] = "Requires development platform " + str + " (current platform is any of " + java.util.Arrays.toString(strArr) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        } else {
            strArr2[0] = "Requires development platform " + str + " but this is a release platform.";
        }
        return -1;
    }

    private android.content.pm.FeatureInfo parseUsesFeature(android.content.res.Resources resources, android.util.AttributeSet attributeSet) {
        android.content.pm.FeatureInfo featureInfo = new android.content.pm.FeatureInfo();
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.AndroidManifestUsesFeature);
        featureInfo.name = obtainAttributes.getNonResourceString(0);
        featureInfo.version = obtainAttributes.getInt(3, 0);
        if (featureInfo.name == null) {
            featureInfo.reqGlEsVersion = obtainAttributes.getInt(1, 0);
        }
        if (obtainAttributes.getBoolean(2, true)) {
            featureInfo.flags |= 1;
        }
        obtainAttributes.recycle();
        return featureInfo;
    }

    private boolean parseUsesStaticLibrary(android.content.pm.PackageParser.Package r9, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUsesStaticLibrary);
        java.lang.String nonResourceString = obtainAttributes.getNonResourceString(0);
        int i = obtainAttributes.getInt(1, -1);
        java.lang.String nonResourceString2 = obtainAttributes.getNonResourceString(2);
        obtainAttributes.recycle();
        if (nonResourceString == null || i < 0 || nonResourceString2 == null) {
            strArr[0] = "Bad uses-static-library declaration name: " + nonResourceString + " version: " + i + " certDigest" + nonResourceString2;
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
            return false;
        }
        if (r9.usesStaticLibraries != null && r9.usesStaticLibraries.contains(nonResourceString)) {
            strArr[0] = "Depending on multiple versions of static library " + nonResourceString;
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
            return false;
        }
        java.lang.String intern = nonResourceString.intern();
        java.lang.String lowerCase = nonResourceString2.replace(":", "").toLowerCase();
        java.lang.String[] strArr2 = libcore.util.EmptyArray.STRING;
        if (r9.applicationInfo.targetSdkVersion >= 27) {
            strArr2 = parseAdditionalCertificates(resources, xmlResourceParser, strArr);
            if (strArr2 == null) {
                return false;
            }
        } else {
            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
        }
        java.lang.String[] strArr3 = new java.lang.String[strArr2.length + 1];
        strArr3[0] = lowerCase;
        java.lang.System.arraycopy(strArr2, 0, strArr3, 1, strArr2.length);
        r9.usesStaticLibraries = com.android.internal.util.ArrayUtils.add(r9.usesStaticLibraries, intern);
        r9.usesStaticLibrariesVersions = com.android.internal.util.ArrayUtils.appendLong(r9.usesStaticLibrariesVersions, i, true);
        r9.usesStaticLibrariesCertDigests = (java.lang.String[][]) com.android.internal.util.ArrayUtils.appendElement(java.lang.String[].class, r9.usesStaticLibrariesCertDigests, strArr3, true);
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0076, code lost:
    
        return r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private java.lang.String[] parseAdditionalCertificates(android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String[] strArr2 = libcore.util.EmptyArray.STRING;
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                if (xmlResourceParser.getName().equals("additional-certificate")) {
                    android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestAdditionalCertificate);
                    java.lang.String nonResourceString = obtainAttributes.getNonResourceString(0);
                    obtainAttributes.recycle();
                    if (android.text.TextUtils.isEmpty(nonResourceString)) {
                        strArr[0] = "Bad additional-certificate declaration with empty certDigest:" + nonResourceString;
                        this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        obtainAttributes.recycle();
                        return null;
                    }
                    strArr2 = (java.lang.String[]) com.android.internal.util.ArrayUtils.appendElement(java.lang.String.class, strArr2, nonResourceString.replace(":", "").toLowerCase());
                } else {
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                }
            }
        }
    }

    private boolean parseUsesPermission(android.content.pm.PackageParser.Package r6, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUsesPermission);
        java.lang.String nonResourceString = obtainAttributes.getNonResourceString(0);
        android.util.TypedValue peekValue = obtainAttributes.peekValue(2);
        if (peekValue != null && peekValue.type >= 16 && peekValue.type <= 31) {
            i = peekValue.data;
        } else {
            i = 0;
        }
        java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(3, 0);
        java.lang.String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(4, 0);
        obtainAttributes.recycle();
        com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
        if (nonResourceString == null) {
            return true;
        }
        if (i != 0 && i < android.os.Build.VERSION.RESOURCES_SDK_INT) {
            return true;
        }
        if (nonConfigurationString != null && this.mCallback != null && !this.mCallback.hasFeature(nonConfigurationString)) {
            return true;
        }
        if (nonConfigurationString2 != null && this.mCallback != null && this.mCallback.hasFeature(nonConfigurationString2)) {
            return true;
        }
        if (r6.requestedPermissions.indexOf(nonResourceString) == -1) {
            r6.requestedPermissions.add(nonResourceString.intern());
        } else {
            android.util.Slog.w(TAG, "Ignoring duplicate uses-permissions/uses-permissions-sdk-m: " + nonResourceString + " in package: " + r6.packageName + " at: " + xmlResourceParser.getPositionDescription());
        }
        return true;
    }

    public static java.lang.String buildClassName(java.lang.String str, java.lang.CharSequence charSequence, java.lang.String[] strArr) {
        if (charSequence == null || charSequence.length() <= 0) {
            strArr[0] = "Empty class name in package " + str;
            return null;
        }
        java.lang.String charSequence2 = charSequence.toString();
        if (charSequence2.charAt(0) == '.') {
            return str + charSequence2;
        }
        if (charSequence2.indexOf(46) < 0) {
            return str + '.' + charSequence2;
        }
        return charSequence2;
    }

    private static java.lang.String buildCompoundName(java.lang.String str, java.lang.CharSequence charSequence, java.lang.String str2, java.lang.String[] strArr) {
        java.lang.String charSequence2 = charSequence.toString();
        char charAt = charSequence2.charAt(0);
        if (str != null && charAt == ':') {
            if (charSequence2.length() < 2) {
                strArr[0] = "Bad " + str2 + " name " + charSequence2 + " in package " + str + ": must be at least two characters";
                return null;
            }
            java.lang.String validateName = validateName(charSequence2.substring(1), false, false);
            if (validateName != null) {
                strArr[0] = "Invalid " + str2 + " name " + charSequence2 + " in package " + str + ": " + validateName;
                return null;
            }
            return str + charSequence2;
        }
        java.lang.String validateName2 = validateName(charSequence2, true, false);
        if (validateName2 != null && !"system".equals(charSequence2)) {
            strArr[0] = "Invalid " + str2 + " name " + charSequence2 + " in package " + str + ": " + validateName2;
            return null;
        }
        return charSequence2;
    }

    public static java.lang.String buildProcessName(java.lang.String str, java.lang.String str2, java.lang.CharSequence charSequence, int i, java.lang.String[] strArr, java.lang.String[] strArr2) {
        if ((i & 2) != 0 && !"system".equals(charSequence)) {
            return str2 != null ? str2 : str;
        }
        if (strArr != null) {
            for (int length = strArr.length - 1; length >= 0; length--) {
                java.lang.String str3 = strArr[length];
                if (str3.equals(str) || str3.equals(str2) || str3.equals(charSequence)) {
                    return str;
                }
            }
        }
        if (charSequence == null || charSequence.length() <= 0) {
            return str2;
        }
        return android.text.TextUtils.safeIntern(buildCompoundName(str, charSequence, "process", strArr2));
    }

    public static java.lang.String buildTaskAffinityName(java.lang.String str, java.lang.String str2, java.lang.CharSequence charSequence, java.lang.String[] strArr) {
        if (charSequence == null) {
            return str2;
        }
        if (charSequence.length() <= 0) {
            return null;
        }
        return buildCompoundName(str, charSequence, "taskAffinity", strArr);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x01e9, code lost:
    
        if (r5.keySet().removeAll(r7.keySet()) == false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x01eb, code lost:
    
        r21[0] = "Package" + r18.packageName + " AndroidManifext.xml 'key-set' and 'public-key' names must be distinct.";
        r17.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x020a, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x020b, code lost:
    
        r18.mKeySetMapping = new android.util.ArrayMap<>();
        r2 = r7.entrySet().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x021e, code lost:
    
        if (r2.hasNext() == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0220, code lost:
    
        r4 = (java.util.Map.Entry) r2.next();
        r7 = (java.lang.String) r4.getKey();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0238, code lost:
    
        if (((android.util.ArraySet) r4.getValue()).size() != 0) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0263, code lost:
    
        if (r8.contains(r7) == false) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x028a, code lost:
    
        r18.mKeySetMapping.put(r7, new android.util.ArraySet<>());
        r4 = ((android.util.ArraySet) r4.getValue()).iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x02a2, code lost:
    
        if (r4.hasNext() == false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x02a4, code lost:
    
        r18.mKeySetMapping.get(r7).add((java.security.PublicKey) r5.get((java.lang.String) r4.next()));
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0265, code lost:
    
        android.util.Slog.w(android.content.pm.PackageParser.TAG, "Package" + r18.packageName + " AndroidManifext.xml 'key-set' " + r7 + " contained improper 'public-key' tags. Not including in package's defined key-sets.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x023a, code lost:
    
        android.util.Slog.w(android.content.pm.PackageParser.TAG, "Package" + r18.packageName + " AndroidManifext.xml 'key-set' " + r7 + " has no valid associated 'public-key'. Not including in package's defined key-sets.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x02c8, code lost:
    
        if (r18.mKeySetMapping.keySet().containsAll(r6) == false) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x02ca, code lost:
    
        r18.mUpgradeKeySets = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x02cd, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x02ce, code lost:
    
        r21[0] = "Package" + r18.packageName + " AndroidManifext.xml does not define all 'upgrade-key-set's .";
        r17.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x02ed, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean parseKeySets(android.content.pm.PackageParser.Package r18, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = xmlResourceParser.getDepth();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.util.ArraySet<java.lang.String> arraySet = new android.util.ArraySet<>();
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        int i = -1;
        java.lang.String str = null;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == 3) {
                if (xmlResourceParser.getDepth() == i) {
                    i = -1;
                    str = null;
                }
            } else {
                java.lang.String name = xmlResourceParser.getName();
                if (name.equals("key-set")) {
                    if (str != null) {
                        strArr[0] = "Improperly nested 'key-set' tag at " + xmlResourceParser.getPositionDescription();
                        this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                        return false;
                    }
                    android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestKeySet);
                    java.lang.String nonResourceString = obtainAttributes.getNonResourceString(0);
                    arrayMap2.put(nonResourceString, new android.util.ArraySet());
                    i = xmlResourceParser.getDepth();
                    obtainAttributes.recycle();
                    str = nonResourceString;
                } else if (name.equals("public-key")) {
                    if (str == null) {
                        strArr[0] = "Improperly nested 'key-set' tag at " + xmlResourceParser.getPositionDescription();
                        this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                        return false;
                    }
                    android.content.res.TypedArray obtainAttributes2 = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestPublicKey);
                    java.lang.String nonResourceString2 = obtainAttributes2.getNonResourceString(0);
                    java.lang.String nonResourceString3 = obtainAttributes2.getNonResourceString(1);
                    if (nonResourceString3 == null && arrayMap.get(nonResourceString2) == null) {
                        strArr[0] = "'public-key' " + nonResourceString2 + " must define a public-key value on first use at " + xmlResourceParser.getPositionDescription();
                        this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                        obtainAttributes2.recycle();
                        return false;
                    }
                    if (nonResourceString3 != null) {
                        java.security.PublicKey parsePublicKey = parsePublicKey(nonResourceString3);
                        if (parsePublicKey == null) {
                            android.util.Slog.w(TAG, "No recognized valid key in 'public-key' tag at " + xmlResourceParser.getPositionDescription() + " key-set " + str + " will not be added to the package's defined key-sets.");
                            obtainAttributes2.recycle();
                            arraySet2.add(str);
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        } else if (arrayMap.get(nonResourceString2) == null || ((java.security.PublicKey) arrayMap.get(nonResourceString2)).equals(parsePublicKey)) {
                            arrayMap.put(nonResourceString2, parsePublicKey);
                        } else {
                            strArr[0] = "Value of 'public-key' " + nonResourceString2 + " conflicts with previously defined value at " + xmlResourceParser.getPositionDescription();
                            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                            obtainAttributes2.recycle();
                            return false;
                        }
                    }
                    ((android.util.ArraySet) arrayMap2.get(str)).add(nonResourceString2);
                    obtainAttributes2.recycle();
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                } else if (name.equals("upgrade-key-set")) {
                    android.content.res.TypedArray obtainAttributes3 = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUpgradeKeySet);
                    arraySet.add(obtainAttributes3.getNonResourceString(0));
                    obtainAttributes3.recycle();
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                } else {
                    android.util.Slog.w(TAG, "Unknown element under <key-sets>: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                }
            }
        }
    }

    private boolean parsePermissionGroup(android.content.pm.PackageParser.Package r21, int i, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestPermissionGroup);
        android.content.pm.PackageParser.PermissionGroup permissionGroup = new android.content.pm.PackageParser.PermissionGroup(r21, obtainAttributes.getResourceId(12, 0), obtainAttributes.getResourceId(9, 0), obtainAttributes.getResourceId(10, 0));
        if (!parsePackageItemInfo(r21, permissionGroup.info, strArr, "<permission-group>", obtainAttributes, true, 2, 0, 1, 8, 5, 7)) {
            obtainAttributes.recycle();
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        permissionGroup.info.descriptionRes = obtainAttributes.getResourceId(4, 0);
        permissionGroup.info.requestRes = obtainAttributes.getResourceId(11, 0);
        permissionGroup.info.flags = obtainAttributes.getInt(6, 0);
        permissionGroup.info.priority = obtainAttributes.getInt(3, 0);
        obtainAttributes.recycle();
        if (parseAllMetaData(resources, xmlResourceParser, "<permission-group>", permissionGroup, strArr)) {
            r21.permissionGroups.add(permissionGroup);
            return true;
        }
        this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0065  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean parsePermission(android.content.pm.PackageParser.Package r21, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String str;
        android.content.pm.PackageParser.Permission permission;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestPermission);
        if (obtainAttributes.hasValue(12)) {
            if ("android".equals(r21.packageName)) {
                str = obtainAttributes.getNonResourceString(12);
                permission = new android.content.pm.PackageParser.Permission(r21, str);
                if (parsePackageItemInfo(r21, permission.info, strArr, "<permission>", obtainAttributes, true, 2, 0, 1, 10, 7, 9)) {
                    obtainAttributes.recycle();
                    this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                    return false;
                }
                permission.info.group = obtainAttributes.getNonResourceString(4);
                if (permission.info.group != null) {
                    permission.info.group = permission.info.group.intern();
                }
                permission.info.descriptionRes = obtainAttributes.getResourceId(5, 0);
                permission.info.requestRes = obtainAttributes.getResourceId(13, 0);
                permission.info.protectionLevel = obtainAttributes.getInt(3, 0);
                permission.info.flags = obtainAttributes.getInt(8, 0);
                if (!permission.info.isRuntime() || !"android".equals(permission.info.packageName)) {
                    permission.info.flags &= -5;
                    permission.info.flags &= -9;
                } else if ((permission.info.flags & 4) != 0 && (permission.info.flags & 8) != 0) {
                    throw new java.lang.IllegalStateException("Permission cannot be both soft and hard restricted: " + permission.info.name);
                }
                obtainAttributes.recycle();
                if (permission.info.protectionLevel == -1) {
                    strArr[0] = "<permission> does not specify protectionLevel";
                    this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                    return false;
                }
                permission.info.protectionLevel = android.content.pm.PermissionInfo.fixProtectionLevel(permission.info.protectionLevel);
                if (permission.info.getProtectionFlags() != 0 && (permission.info.protectionLevel & 4096) == 0 && (permission.info.protectionLevel & 8192) == 0 && (permission.info.protectionLevel & 15) != 2) {
                    strArr[0] = "<permission>  protectionLevel specifies a non-instant flag but is not based on signature type";
                    this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                    return false;
                }
                if (!parseAllMetaData(resources, xmlResourceParser, "<permission>", permission, strArr)) {
                    this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                    return false;
                }
                r21.permissions.add(permission);
                return true;
            }
            android.util.Slog.w(TAG, r21.packageName + " defines a background permission. Only the 'android' package can do that.");
        }
        str = null;
        permission = new android.content.pm.PackageParser.Permission(r21, str);
        if (parsePackageItemInfo(r21, permission.info, strArr, "<permission>", obtainAttributes, true, 2, 0, 1, 10, 7, 9)) {
        }
    }

    private boolean parsePermissionTree(android.content.pm.PackageParser.Package r20, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.pm.PackageParser.Permission permission = new android.content.pm.PackageParser.Permission(r20, (java.lang.String) null);
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestPermissionTree);
        if (!parsePackageItemInfo(r20, permission.info, strArr, "<permission-tree>", obtainAttributes, true, 2, 0, 1, 5, 3, 4)) {
            obtainAttributes.recycle();
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        obtainAttributes.recycle();
        int indexOf = permission.info.name.indexOf(46);
        if (indexOf > 0) {
            indexOf = permission.info.name.indexOf(46, indexOf + 1);
        }
        if (indexOf < 0) {
            strArr[0] = "<permission-tree> name has less than three segments: " + permission.info.name;
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        permission.info.descriptionRes = 0;
        permission.info.requestRes = 0;
        permission.info.protectionLevel = 0;
        permission.tree = true;
        if (!parseAllMetaData(resources, xmlResourceParser, "<permission-tree>", permission, strArr)) {
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        r20.permissions.add(permission);
        return true;
    }

    private android.content.pm.PackageParser.Instrumentation parseInstrumentation(android.content.pm.PackageParser.Package r12, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestInstrumentation);
        if (this.mParseInstrumentationArgs == null) {
            this.mParseInstrumentationArgs = new android.content.pm.PackageParser.ParsePackageItemArgs(r12, strArr, 2, 0, 1, 8, 6, 7);
            this.mParseInstrumentationArgs.tag = "<instrumentation>";
        }
        this.mParseInstrumentationArgs.sa = obtainAttributes;
        android.content.pm.PackageParser.Instrumentation instrumentation = new android.content.pm.PackageParser.Instrumentation(this.mParseInstrumentationArgs, new android.content.pm.InstrumentationInfo());
        if (strArr[0] != null) {
            obtainAttributes.recycle();
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return null;
        }
        java.lang.String nonResourceString = obtainAttributes.getNonResourceString(3);
        instrumentation.info.targetPackage = nonResourceString != null ? nonResourceString.intern() : null;
        java.lang.String nonResourceString2 = obtainAttributes.getNonResourceString(9);
        instrumentation.info.targetProcesses = nonResourceString2 != null ? nonResourceString2.intern() : null;
        instrumentation.info.handleProfiling = obtainAttributes.getBoolean(4, false);
        instrumentation.info.functionalTest = obtainAttributes.getBoolean(5, false);
        obtainAttributes.recycle();
        if (instrumentation.info.targetPackage == null) {
            strArr[0] = "<instrumentation> does not specify targetPackage";
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return null;
        }
        if (!parseAllMetaData(resources, xmlResourceParser, "<instrumentation>", instrumentation, strArr)) {
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return null;
        }
        r12.instrumentation.add(instrumentation);
        return instrumentation;
    }

    /* JADX WARN: Code restructure failed: missing block: B:279:0x05f6, code lost:
    
        r12[r13] = "Bad static-library declaration name: " + r1 + " version: " + r4;
        r28.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
        com.android.internal.util.XmlUtils.skipCurrentTag(r31);
     */
    /* JADX WARN: Code restructure failed: missing block: B:280:0x061c, code lost:
    
        return r13;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v11, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v40 */
    /* JADX WARN: Type inference failed for: r13v41 */
    /* JADX WARN: Type inference failed for: r13v42 */
    /* JADX WARN: Type inference failed for: r13v43 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean parseBaseApplication(android.content.pm.PackageParser.Package r29, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String nonResourceString;
        ?? r13;
        android.content.res.TypedArray typedArray;
        java.lang.String str;
        android.content.pm.ApplicationInfo applicationInfo;
        java.lang.String[] strArr2;
        char c;
        android.content.pm.PackageParser.Package r14;
        android.content.pm.PackageParser.CachedComponentArgs cachedComponentArgs;
        android.content.res.XmlResourceParser xmlResourceParser2;
        android.content.pm.ApplicationInfo applicationInfo2;
        android.content.pm.PackageParser.Package r142;
        char c2;
        java.lang.String str2;
        java.lang.String nonResourceString2;
        java.lang.String nonResourceString3;
        android.content.pm.PackageParser.Package r8 = r29;
        android.content.res.XmlResourceParser xmlResourceParser3 = xmlResourceParser;
        android.content.pm.ApplicationInfo applicationInfo3 = r8.applicationInfo;
        java.lang.String str3 = r8.applicationInfo.packageName;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser3, com.android.internal.R.styleable.AndroidManifestApplication);
        applicationInfo3.iconRes = obtainAttributes.getResourceId(2, 0);
        applicationInfo3.roundIconRes = obtainAttributes.getResourceId(42, 0);
        if (!parsePackageItemInfo(r29, applicationInfo3, strArr, "<application>", obtainAttributes, false, 3, 1, 2, 42, 22, 30)) {
            obtainAttributes.recycle();
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
        if (applicationInfo3.name != null) {
            applicationInfo3.className = applicationInfo3.name;
        }
        int i2 = 4;
        java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(4, 1024);
        if (nonConfigurationString != null) {
            applicationInfo3.manageSpaceActivityName = buildClassName(str3, nonConfigurationString, strArr);
        }
        int i3 = 1;
        if (obtainAttributes.getBoolean(17, true)) {
            applicationInfo3.flags |= 32768;
            java.lang.String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(16, 1024);
            if (nonConfigurationString2 != null) {
                applicationInfo3.backupAgentName = buildClassName(str3, nonConfigurationString2, strArr);
                if (obtainAttributes.getBoolean(18, true)) {
                    applicationInfo3.flags |= 65536;
                }
                if (obtainAttributes.getBoolean(21, false)) {
                    applicationInfo3.flags |= 131072;
                }
                if (obtainAttributes.getBoolean(32, false)) {
                    applicationInfo3.flags |= 67108864;
                }
                if (obtainAttributes.getBoolean(40, false)) {
                    applicationInfo3.privateFlags |= 8192;
                }
            }
            android.util.TypedValue peekValue = obtainAttributes.peekValue(35);
            if (peekValue != null) {
                int i4 = peekValue.resourceId;
                applicationInfo3.fullBackupContent = i4;
                if (i4 == 0) {
                    applicationInfo3.fullBackupContent = peekValue.data == 0 ? -1 : 0;
                }
            }
        }
        applicationInfo3.theme = obtainAttributes.getResourceId(0, 0);
        applicationInfo3.descriptionRes = obtainAttributes.getResourceId(13, 0);
        if (obtainAttributes.getBoolean(8, false) && ((nonResourceString3 = obtainAttributes.getNonResourceString(45)) == null || this.mCallback.hasFeature(nonResourceString3))) {
            applicationInfo3.flags |= 8;
        }
        if (obtainAttributes.getBoolean(27, false)) {
            r8.mRequiredForAllUsers = true;
        }
        java.lang.String string = obtainAttributes.getString(28);
        if (string != null && string.length() > 0) {
            r8.mRestrictedAccountType = string;
        }
        java.lang.String string2 = obtainAttributes.getString(29);
        if (string2 != null && string2.length() > 0) {
            r8.mRequiredAccountType = string2;
        }
        if (obtainAttributes.getBoolean(10, false)) {
            applicationInfo3.flags |= 2;
        }
        if (obtainAttributes.getBoolean(20, false)) {
            applicationInfo3.flags |= 16384;
        }
        r8.baseHardwareAccelerated = obtainAttributes.getBoolean(23, r8.applicationInfo.targetSdkVersion >= 14);
        if (r8.baseHardwareAccelerated) {
            applicationInfo3.flags |= 536870912;
        }
        if (obtainAttributes.getBoolean(7, true)) {
            applicationInfo3.flags |= 4;
        }
        if (obtainAttributes.getBoolean(14, false)) {
            applicationInfo3.flags |= 32;
        }
        if (obtainAttributes.getBoolean(5, true)) {
            applicationInfo3.flags |= 64;
        }
        if (r8.parentPackage == null && obtainAttributes.getBoolean(15, false)) {
            applicationInfo3.flags |= 256;
        }
        if (obtainAttributes.getBoolean(24, false)) {
            applicationInfo3.flags |= 1048576;
        }
        if (obtainAttributes.getBoolean(36, r8.applicationInfo.targetSdkVersion < 28)) {
            applicationInfo3.flags |= 134217728;
        }
        if (obtainAttributes.getBoolean(26, false)) {
            applicationInfo3.flags |= 4194304;
        }
        if (obtainAttributes.getBoolean(33, false)) {
            applicationInfo3.flags |= Integer.MIN_VALUE;
        }
        if (obtainAttributes.getBoolean(34, true)) {
            applicationInfo3.flags |= 268435456;
        }
        if (obtainAttributes.getBoolean(53, false)) {
            applicationInfo3.privateFlags |= 33554432;
        }
        if (obtainAttributes.getBoolean(38, false)) {
            applicationInfo3.privateFlags |= 32;
        }
        if (obtainAttributes.getBoolean(39, false)) {
            applicationInfo3.privateFlags |= 64;
        }
        if (!obtainAttributes.hasValueOrEmpty(37)) {
            if (r8.applicationInfo.targetSdkVersion >= 24) {
                applicationInfo3.privateFlags |= 4096;
            }
        } else if (obtainAttributes.getBoolean(37, true)) {
            applicationInfo3.privateFlags |= 1024;
        } else {
            applicationInfo3.privateFlags |= 2048;
        }
        if (obtainAttributes.getBoolean(54, true)) {
            applicationInfo3.privateFlags |= 67108864;
        }
        if (obtainAttributes.getBoolean(55, r8.applicationInfo.targetSdkVersion >= 29)) {
            applicationInfo3.privateFlags |= 134217728;
        }
        if (obtainAttributes.getBoolean(56, r8.applicationInfo.targetSdkVersion < 29)) {
            applicationInfo3.privateFlags |= 536870912;
        }
        if (obtainAttributes.getBoolean(59, true)) {
            applicationInfo3.privateFlags |= Integer.MIN_VALUE;
        }
        applicationInfo3.maxAspectRatio = obtainAttributes.getFloat(44, 0.0f);
        applicationInfo3.minAspectRatio = obtainAttributes.getFloat(51, 0.0f);
        applicationInfo3.networkSecurityConfigRes = obtainAttributes.getResourceId(41, 0);
        applicationInfo3.category = obtainAttributes.getInt(43, -1);
        java.lang.String nonConfigurationString3 = obtainAttributes.getNonConfigurationString(6, 0);
        applicationInfo3.permission = (nonConfigurationString3 == null || nonConfigurationString3.length() <= 0) ? null : nonConfigurationString3.intern();
        if (r8.applicationInfo.targetSdkVersion >= 8) {
            nonResourceString = obtainAttributes.getNonConfigurationString(12, 1024);
        } else {
            nonResourceString = obtainAttributes.getNonResourceString(12);
        }
        applicationInfo3.taskAffinity = buildTaskAffinityName(applicationInfo3.packageName, applicationInfo3.packageName, nonResourceString, strArr);
        java.lang.String nonResourceString4 = obtainAttributes.getNonResourceString(48);
        if (nonResourceString4 != null) {
            applicationInfo3.appComponentFactory = buildClassName(applicationInfo3.packageName, nonResourceString4, strArr);
        }
        if (obtainAttributes.getBoolean(49, false)) {
            applicationInfo3.privateFlags |= 4194304;
        }
        if (obtainAttributes.getBoolean(50, false)) {
            applicationInfo3.privateFlags |= 16777216;
        }
        if (strArr[0] != null) {
            r13 = 0;
            typedArray = obtainAttributes;
            str = str3;
            applicationInfo = applicationInfo3;
            strArr2 = strArr;
            c = 2;
        } else {
            if (r8.applicationInfo.targetSdkVersion >= 8) {
                nonResourceString2 = obtainAttributes.getNonConfigurationString(11, 1024);
            } else {
                nonResourceString2 = obtainAttributes.getNonResourceString(11);
            }
            boolean z = false;
            c = 2;
            android.content.res.TypedArray typedArray2 = obtainAttributes;
            java.lang.String str4 = nonResourceString2;
            str = str3;
            applicationInfo = applicationInfo3;
            strArr2 = strArr;
            applicationInfo.processName = buildProcessName(applicationInfo3.packageName, null, str4, i, this.mSeparateProcesses, strArr);
            applicationInfo.enabled = typedArray2.getBoolean(9, true);
            if (typedArray2.getBoolean(31, false)) {
                applicationInfo.flags |= 33554432;
            }
            boolean z2 = typedArray2.getBoolean(47, false);
            typedArray = typedArray2;
            r13 = z;
            if (z2) {
                applicationInfo.privateFlags |= 2;
                typedArray = typedArray2;
                r13 = z;
                if (applicationInfo.processName != null) {
                    typedArray = typedArray2;
                    r13 = z;
                    if (!applicationInfo.processName.equals(applicationInfo.packageName)) {
                        strArr2[0] = "cantSaveState applications can not use custom processes";
                        typedArray = typedArray2;
                        r13 = z;
                    }
                }
            }
        }
        applicationInfo.uiOptions = typedArray.getInt(25, r13);
        applicationInfo.classLoaderName = typedArray.getString(46);
        if (applicationInfo.classLoaderName != null && !com.android.internal.os.ClassLoaderFactory.isValidClassLoaderName(applicationInfo.classLoaderName)) {
            strArr2[r13] = "Invalid class loader name: " + applicationInfo.classLoaderName;
        }
        applicationInfo.zygotePreloadName = typedArray.getString(52);
        typedArray.recycle();
        if (strArr2[r13] != null) {
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return r13;
        }
        int depth = xmlResourceParser.getDepth();
        android.content.pm.PackageParser.CachedComponentArgs cachedComponentArgs2 = new android.content.pm.PackageParser.CachedComponentArgs();
        boolean z3 = r13;
        boolean z4 = z3;
        boolean z5 = z4;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == i3) {
                r14 = r8;
                break;
            }
            if (next == 3 && xmlResourceParser.getDepth() <= depth) {
                r14 = r8;
                break;
            }
            if (next == 3) {
                cachedComponentArgs = cachedComponentArgs2;
                xmlResourceParser2 = xmlResourceParser3;
                applicationInfo2 = applicationInfo;
                r142 = r8;
                c2 = c;
                str2 = str;
            } else if (next == i2) {
                cachedComponentArgs = cachedComponentArgs2;
                xmlResourceParser2 = xmlResourceParser3;
                applicationInfo2 = applicationInfo;
                r142 = r8;
                c2 = c;
                str2 = str;
            } else {
                java.lang.String name = xmlResourceParser.getName();
                if (name.equals("activity")) {
                    cachedComponentArgs = cachedComponentArgs2;
                    xmlResourceParser2 = xmlResourceParser3;
                    android.content.pm.ApplicationInfo applicationInfo4 = applicationInfo;
                    java.lang.String str5 = str;
                    r142 = r8;
                    android.content.pm.PackageParser.Activity parseActivity = parseActivity(r29, resources, xmlResourceParser, i, strArr, cachedComponentArgs, false, r8.baseHardwareAccelerated);
                    if (parseActivity == null) {
                        this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                        return r13;
                    }
                    boolean z6 = parseActivity.order != 0 ? true : r13;
                    r142.activities.add(parseActivity);
                    z3 |= z6;
                    applicationInfo2 = applicationInfo4;
                    str2 = str5;
                    c2 = 2;
                } else {
                    cachedComponentArgs = cachedComponentArgs2;
                    xmlResourceParser2 = xmlResourceParser3;
                    android.content.pm.ApplicationInfo applicationInfo5 = applicationInfo;
                    r142 = r8;
                    java.lang.String str6 = str;
                    if (name.equals("receiver")) {
                        android.content.pm.PackageParser.Activity parseActivity2 = parseActivity(r29, resources, xmlResourceParser, i, strArr, cachedComponentArgs, true, false);
                        if (parseActivity2 == null) {
                            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                            return r13;
                        }
                        boolean z7 = parseActivity2.order != 0 ? true : r13;
                        r142.receivers.add(parseActivity2);
                        z4 |= z7;
                        applicationInfo2 = applicationInfo5;
                        str2 = str6;
                        c2 = 2;
                    } else if (name.equals("service")) {
                        android.content.pm.PackageParser.Service parseService = parseService(r29, resources, xmlResourceParser, i, strArr, cachedComponentArgs);
                        if (parseService == null) {
                            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                            return r13;
                        }
                        boolean z8 = parseService.order != 0 ? true : r13;
                        r142.services.add(parseService);
                        z5 |= z8;
                        applicationInfo2 = applicationInfo5;
                        str2 = str6;
                        c2 = 2;
                    } else if (name.equals("provider")) {
                        android.content.pm.PackageParser.Provider parseProvider = parseProvider(r29, resources, xmlResourceParser, i, strArr, cachedComponentArgs);
                        if (parseProvider == null) {
                            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                            return r13;
                        }
                        r142.providers.add(parseProvider);
                        applicationInfo2 = applicationInfo5;
                        str2 = str6;
                        c2 = 2;
                    } else if (name.equals("activity-alias")) {
                        android.content.pm.PackageParser.Activity parseActivityAlias = parseActivityAlias(r29, resources, xmlResourceParser, i, strArr, cachedComponentArgs);
                        if (parseActivityAlias == null) {
                            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                            return r13;
                        }
                        boolean z9 = parseActivityAlias.order != 0 ? true : r13;
                        r142.activities.add(parseActivityAlias);
                        z3 |= z9;
                        applicationInfo2 = applicationInfo5;
                        str2 = str6;
                        c2 = 2;
                    } else if (xmlResourceParser.getName().equals("meta-data")) {
                        android.os.Bundle parseMetaData = parseMetaData(resources, xmlResourceParser2, r142.mAppMetaData, strArr2);
                        r142.mAppMetaData = parseMetaData;
                        if (parseMetaData == null) {
                            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                            return r13;
                        }
                        applicationInfo2 = applicationInfo5;
                        str2 = str6;
                        c2 = 2;
                    } else if (name.equals("static-library")) {
                        android.content.res.TypedArray obtainAttributes2 = resources.obtainAttributes(xmlResourceParser2, com.android.internal.R.styleable.AndroidManifestStaticLibrary);
                        java.lang.String nonResourceString5 = obtainAttributes2.getNonResourceString(r13);
                        int i5 = obtainAttributes2.getInt(1, -1);
                        c2 = 2;
                        int i6 = obtainAttributes2.getInt(2, r13);
                        obtainAttributes2.recycle();
                        if (nonResourceString5 == null || i5 < 0) {
                            break;
                        }
                        if (r142.mSharedUserId != null) {
                            strArr2[r13] = "sharedUserId not allowed in static shared library";
                            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID;
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                            return r13;
                        }
                        if (r142.staticSharedLibName != null) {
                            strArr2[r13] = "Multiple static-shared libs for package " + str6;
                            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                            return r13;
                        }
                        str2 = str6;
                        r142.staticSharedLibName = nonResourceString5.intern();
                        if (i5 >= 0) {
                            r142.staticSharedLibVersion = android.content.pm.PackageInfo.composeLongVersionCode(i6, i5);
                        } else {
                            r142.staticSharedLibVersion = i5;
                        }
                        applicationInfo2 = applicationInfo5;
                        applicationInfo2.privateFlags |= 16384;
                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                    } else {
                        applicationInfo2 = applicationInfo5;
                        str2 = str6;
                        c2 = 2;
                        if (name.equals("library")) {
                            android.content.res.TypedArray obtainAttributes3 = resources.obtainAttributes(xmlResourceParser2, com.android.internal.R.styleable.AndroidManifestLibrary);
                            java.lang.String nonResourceString6 = obtainAttributes3.getNonResourceString(r13);
                            obtainAttributes3.recycle();
                            if (nonResourceString6 != null) {
                                java.lang.String intern = nonResourceString6.intern();
                                if (!com.android.internal.util.ArrayUtils.contains(r142.libraryNames, intern)) {
                                    r142.libraryNames = com.android.internal.util.ArrayUtils.add(r142.libraryNames, intern);
                                }
                            }
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        } else if (name.equals("uses-static-library")) {
                            if (!parseUsesStaticLibrary(r142, resources, xmlResourceParser2, strArr2)) {
                                return r13;
                            }
                        } else if (name.equals("uses-library")) {
                            android.content.res.TypedArray obtainAttributes4 = resources.obtainAttributes(xmlResourceParser2, com.android.internal.R.styleable.AndroidManifestUsesLibrary);
                            java.lang.String nonResourceString7 = obtainAttributes4.getNonResourceString(r13);
                            boolean z10 = obtainAttributes4.getBoolean(1, true);
                            obtainAttributes4.recycle();
                            if (nonResourceString7 != null) {
                                java.lang.String intern2 = nonResourceString7.intern();
                                if (z10) {
                                    r142.usesLibraries = com.android.internal.util.ArrayUtils.add(r142.usesLibraries, intern2);
                                } else {
                                    r142.usesOptionalLibraries = com.android.internal.util.ArrayUtils.add(r142.usesOptionalLibraries, intern2);
                                }
                            }
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        } else if (name.equals("uses-package")) {
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        } else if (name.equals("profileable")) {
                            if (resources.obtainAttributes(xmlResourceParser2, com.android.internal.R.styleable.AndroidManifestProfileable).getBoolean(1, r13)) {
                                applicationInfo2.privateFlags |= 8388608;
                            }
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        } else {
                            android.util.Slog.w(TAG, "Unknown element under <application>: " + name + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        }
                    }
                }
                c = c2;
                applicationInfo = applicationInfo2;
                str = str2;
                xmlResourceParser3 = xmlResourceParser2;
                r8 = r142;
                cachedComponentArgs2 = cachedComponentArgs;
                i2 = 4;
                i3 = 1;
            }
            c = c2;
            applicationInfo = applicationInfo2;
            str = str2;
            xmlResourceParser3 = xmlResourceParser2;
            r8 = r142;
            cachedComponentArgs2 = cachedComponentArgs;
            i2 = 4;
            i3 = 1;
        }
        if (android.text.TextUtils.isEmpty(r14.staticSharedLibName)) {
            r14.activities.add(generateAppDetailsHiddenActivity(r14, i, strArr2, r14.baseHardwareAccelerated));
        }
        if (z3) {
            java.util.Collections.sort(r14.activities, new java.util.Comparator() { // from class: android.content.pm.PackageParser$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int compare;
                    compare = java.lang.Integer.compare(((android.content.pm.PackageParser.Activity) obj2).order, ((android.content.pm.PackageParser.Activity) obj).order);
                    return compare;
                }
            });
        }
        if (z4) {
            java.util.Collections.sort(r14.receivers, new java.util.Comparator() { // from class: android.content.pm.PackageParser$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int compare;
                    compare = java.lang.Integer.compare(((android.content.pm.PackageParser.Activity) obj2).order, ((android.content.pm.PackageParser.Activity) obj).order);
                    return compare;
                }
            });
        }
        if (z5) {
            java.util.Collections.sort(r14.services, new java.util.Comparator() { // from class: android.content.pm.PackageParser$$ExternalSyntheticLambda2
                @Override // java.util.Comparator
                public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                    int compare;
                    compare = java.lang.Integer.compare(((android.content.pm.PackageParser.Service) obj2).order, ((android.content.pm.PackageParser.Service) obj).order);
                    return compare;
                }
            });
        }
        setMaxAspectRatio(r29);
        setMinAspectRatio(r29);
        setSupportsSizeChanges(r29);
        if (hasDomainURLs(r29)) {
            r14.applicationInfo.privateFlags |= 16;
            return true;
        }
        r14.applicationInfo.privateFlags &= -17;
        return true;
    }

    private static boolean hasDomainURLs(android.content.pm.PackageParser.Package r9) {
        if (r9 == null || r9.activities == null) {
            return false;
        }
        java.util.ArrayList<android.content.pm.PackageParser.Activity> arrayList = r9.activities;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            java.util.ArrayList<II> arrayList2 = arrayList.get(i).intents;
            if (arrayList2 != 0) {
                int size2 = arrayList2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    android.content.pm.PackageParser.ActivityIntentInfo activityIntentInfo = (android.content.pm.PackageParser.ActivityIntentInfo) arrayList2.get(i2);
                    if (activityIntentInfo.hasAction("android.intent.action.VIEW") && activityIntentInfo.hasAction("android.intent.action.VIEW") && (activityIntentInfo.hasDataScheme(android.content.IntentFilter.SCHEME_HTTP) || activityIntentInfo.hasDataScheme(android.content.IntentFilter.SCHEME_HTTPS))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v33 */
    /* JADX WARN: Type inference failed for: r0v34 */
    /* JADX WARN: Type inference failed for: r0v35 */
    /* JADX WARN: Type inference failed for: r0v36 */
    /* JADX WARN: Type inference failed for: r0v37 */
    /* JADX WARN: Type inference failed for: r0v38 */
    /* JADX WARN: Type inference failed for: r0v39 */
    /* JADX WARN: Type inference failed for: r0v40 */
    /* JADX WARN: Type inference failed for: r0v5, types: [android.content.pm.PackageParser$CachedComponentArgs-IA] */
    /* JADX WARN: Type inference failed for: r0v9, types: [android.content.pm.ComponentInfo] */
    private boolean parseSplitApplication(android.content.pm.PackageParser.Package r21, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, int i2, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i3;
        boolean z;
        int i4;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestApplication);
        int i5 = 1;
        int i6 = 4;
        if (obtainAttributes.getBoolean(7, true)) {
            int[] iArr = r21.splitFlags;
            iArr[i2] = iArr[i2] | 4;
        }
        java.lang.String string = obtainAttributes.getString(46);
        int i7 = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
        boolean z2 = false;
        if (string == null || com.android.internal.os.ClassLoaderFactory.isValidClassLoaderName(string)) {
            r21.applicationInfo.splitClassLoaderNames[i2] = string;
            int depth = xmlResourceParser.getDepth();
            while (true) {
                int next = xmlResourceParser.next();
                if (next != i5) {
                    if (next != 3 || xmlResourceParser.getDepth() > depth) {
                        if (next == 3) {
                            i5 = 1;
                            i6 = 4;
                        } else if (next == i6) {
                            continue;
                        } else {
                            ?? r0 = 0;
                            r0 = 0;
                            r0 = 0;
                            r0 = 0;
                            android.content.pm.PackageParser.CachedComponentArgs cachedComponentArgs = new android.content.pm.PackageParser.CachedComponentArgs();
                            java.lang.String name = xmlResourceParser.getName();
                            if (name.equals("activity")) {
                                i3 = depth;
                                z = z2;
                                i4 = i7;
                                android.content.pm.PackageParser.Activity parseActivity = parseActivity(r21, resources, xmlResourceParser, i, strArr, cachedComponentArgs, false, r21.baseHardwareAccelerated);
                                if (parseActivity == null) {
                                    this.mParseError = i4;
                                    return z;
                                }
                                r21.activities.add(parseActivity);
                                r0 = parseActivity.info;
                            } else {
                                i3 = depth;
                                z = z2;
                                i4 = i7;
                                if (name.equals("receiver")) {
                                    android.content.pm.PackageParser.Activity parseActivity2 = parseActivity(r21, resources, xmlResourceParser, i, strArr, cachedComponentArgs, true, false);
                                    if (parseActivity2 == null) {
                                        this.mParseError = i4;
                                        return z;
                                    }
                                    r21.receivers.add(parseActivity2);
                                    r0 = parseActivity2.info;
                                } else if (name.equals("service")) {
                                    android.content.pm.PackageParser.Service parseService = parseService(r21, resources, xmlResourceParser, i, strArr, cachedComponentArgs);
                                    if (parseService == null) {
                                        this.mParseError = i4;
                                        return z;
                                    }
                                    r21.services.add(parseService);
                                    r0 = parseService.info;
                                } else if (name.equals("provider")) {
                                    android.content.pm.PackageParser.Provider parseProvider = parseProvider(r21, resources, xmlResourceParser, i, strArr, cachedComponentArgs);
                                    if (parseProvider == null) {
                                        this.mParseError = i4;
                                        return z;
                                    }
                                    r21.providers.add(parseProvider);
                                    r0 = parseProvider.info;
                                } else if (name.equals("activity-alias")) {
                                    android.content.pm.PackageParser.Activity parseActivityAlias = parseActivityAlias(r21, resources, xmlResourceParser, i, strArr, cachedComponentArgs);
                                    if (parseActivityAlias == null) {
                                        this.mParseError = i4;
                                        return z;
                                    }
                                    r21.activities.add(parseActivityAlias);
                                    r0 = parseActivityAlias.info;
                                } else if (xmlResourceParser.getName().equals("meta-data")) {
                                    android.os.Bundle parseMetaData = parseMetaData(resources, xmlResourceParser, r21.mAppMetaData, strArr);
                                    r21.mAppMetaData = parseMetaData;
                                    if (parseMetaData == null) {
                                        this.mParseError = i4;
                                        return z;
                                    }
                                } else if (name.equals("uses-static-library")) {
                                    if (!parseUsesStaticLibrary(r21, resources, xmlResourceParser, strArr)) {
                                        return z;
                                    }
                                } else if (name.equals("uses-library")) {
                                    android.content.res.TypedArray obtainAttributes2 = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUsesLibrary);
                                    java.lang.String nonResourceString = obtainAttributes2.getNonResourceString(z ? 1 : 0);
                                    boolean z3 = obtainAttributes2.getBoolean(1, true);
                                    obtainAttributes2.recycle();
                                    if (nonResourceString != null) {
                                        java.lang.String intern = nonResourceString.intern();
                                        if (z3) {
                                            r21.usesLibraries = com.android.internal.util.ArrayUtils.add(r21.usesLibraries, intern);
                                            r21.usesOptionalLibraries = com.android.internal.util.ArrayUtils.remove(r21.usesOptionalLibraries, intern);
                                        } else if (!com.android.internal.util.ArrayUtils.contains(r21.usesLibraries, intern)) {
                                            r21.usesOptionalLibraries = com.android.internal.util.ArrayUtils.add(r21.usesOptionalLibraries, intern);
                                        }
                                    }
                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                } else if (name.equals("uses-package")) {
                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                } else {
                                    android.util.Slog.w(TAG, "Unknown element under <application>: " + name + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                    i7 = i4;
                                    z2 = z ? 1 : 0;
                                    depth = i3;
                                    i5 = 1;
                                    i6 = 4;
                                }
                            }
                            if (r0 != 0 && r0.splitName == null) {
                                r0.splitName = r21.splitNames[i2];
                            }
                            i7 = i4;
                            z2 = z;
                            depth = i3;
                            i5 = 1;
                            i6 = 4;
                        }
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        } else {
            strArr[0] = "Invalid class loader name: " + string;
            this.mParseError = android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED;
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean parsePackageItemInfo(android.content.pm.PackageParser.Package r1, android.content.pm.PackageItemInfo packageItemInfo, java.lang.String[] strArr, java.lang.String str, android.content.res.TypedArray typedArray, boolean z, int i, int i2, int i3, int i4, int i5, int i6) {
        if (typedArray == null) {
            strArr[0] = str + " does not contain any attributes";
            return false;
        }
        java.lang.String nonConfigurationString = typedArray.getNonConfigurationString(i, 0);
        if (nonConfigurationString == null) {
            if (z) {
                strArr[0] = str + " does not specify android:name";
                return false;
            }
        } else {
            java.lang.String buildClassName = buildClassName(r1.applicationInfo.packageName, nonConfigurationString, strArr);
            if (android.content.pm.PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(buildClassName)) {
                strArr[0] = str + " invalid android:name";
                return false;
            }
            packageItemInfo.name = buildClassName;
            if (buildClassName == null) {
                return false;
            }
        }
        int resourceId = sUseRoundIcon ? typedArray.getResourceId(i4, 0) : 0;
        if (resourceId != 0) {
            packageItemInfo.icon = resourceId;
            packageItemInfo.nonLocalizedLabel = null;
        } else {
            int resourceId2 = typedArray.getResourceId(i3, 0);
            if (resourceId2 != 0) {
                packageItemInfo.icon = resourceId2;
                packageItemInfo.nonLocalizedLabel = null;
            }
        }
        int resourceId3 = typedArray.getResourceId(i5, 0);
        if (resourceId3 != 0) {
            packageItemInfo.logo = resourceId3;
        }
        int resourceId4 = typedArray.getResourceId(i6, 0);
        if (resourceId4 != 0) {
            packageItemInfo.banner = resourceId4;
        }
        android.util.TypedValue peekValue = typedArray.peekValue(i2);
        if (peekValue != null) {
            int i7 = peekValue.resourceId;
            packageItemInfo.labelRes = i7;
            if (i7 == 0) {
                packageItemInfo.nonLocalizedLabel = peekValue.coerceToString();
            }
        }
        packageItemInfo.packageName = r1.packageName;
        return true;
    }

    private android.content.pm.PackageParser.Activity generateAppDetailsHiddenActivity(android.content.pm.PackageParser.Package r5, int i, java.lang.String[] strArr, boolean z) {
        android.content.pm.PackageParser.Activity activity = new android.content.pm.PackageParser.Activity(r5, android.content.pm.PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME, new android.content.pm.ActivityInfo());
        activity.owner = r5;
        activity.setPackageName(r5.packageName);
        activity.info.theme = 16973909;
        activity.info.exported = true;
        activity.info.name = android.content.pm.PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME;
        activity.info.processName = r5.applicationInfo.processName;
        activity.info.uiOptions = activity.info.applicationInfo.uiOptions;
        activity.info.taskAffinity = buildTaskAffinityName(r5.packageName, r5.packageName, ":app_details", strArr);
        activity.info.enabled = true;
        activity.info.launchMode = 0;
        activity.info.documentLaunchMode = 0;
        activity.info.maxRecents = android.app.ActivityTaskManager.getDefaultAppRecentsLimitStatic();
        activity.info.configChanges = getActivityConfigChanges(0, 0);
        activity.info.softInputMode = 0;
        activity.info.persistableMode = 1;
        activity.info.screenOrientation = -1;
        activity.info.resizeMode = 4;
        activity.info.lockTaskLaunchMode = 0;
        activity.info.directBootAware = false;
        activity.info.rotationAnimation = -1;
        activity.info.colorMode = 0;
        if (z) {
            activity.info.flags |= 512;
        }
        return activity;
    }

    /* JADX WARN: Code restructure failed: missing block: B:133:0x061f, code lost:
    
        resolveWindowLayout(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0622, code lost:
    
        if (r11 != false) goto L230;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0624, code lost:
    
        r0 = r8.info;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x062c, code lost:
    
        if (r8.intents.size() <= 0) goto L228;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x062e, code lost:
    
        r9 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0632, code lost:
    
        r0.exported = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0631, code lost:
    
        r9 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x0634, code lost:
    
        return r8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.content.pm.PackageParser.Activity parseActivity(android.content.pm.PackageParser.Package r23, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, java.lang.String[] strArr, android.content.pm.PackageParser.CachedComponentArgs cachedComponentArgs, boolean z, boolean z2) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        boolean z3;
        android.content.pm.PackageParser.ActivityIntentInfo activityIntentInfo;
        int i2;
        android.content.pm.PackageParser.Package r0;
        android.content.pm.PackageParser.ActivityIntentInfo activityIntentInfo2;
        int i3;
        android.content.pm.PackageParser.Package r6 = r23;
        android.content.res.Resources resources2 = resources;
        android.content.res.XmlResourceParser xmlResourceParser2 = xmlResourceParser;
        java.lang.String[] strArr2 = strArr;
        android.content.res.TypedArray obtainAttributes = resources2.obtainAttributes(xmlResourceParser2, com.android.internal.R.styleable.AndroidManifestActivity);
        if (cachedComponentArgs.mActivityArgs == null) {
            cachedComponentArgs.mActivityArgs = new android.content.pm.PackageParser.ParseComponentArgs(r23, strArr, 3, 1, 2, 44, 23, 30, this.mSeparateProcesses, 7, 17, 5);
        }
        cachedComponentArgs.mActivityArgs.tag = z ? "<receiver>" : "<activity>";
        cachedComponentArgs.mActivityArgs.sa = obtainAttributes;
        cachedComponentArgs.mActivityArgs.flags = i;
        android.content.pm.PackageParser.Activity activity = new android.content.pm.PackageParser.Activity(cachedComponentArgs.mActivityArgs, new android.content.pm.ActivityInfo());
        if (strArr2[0] != null) {
            obtainAttributes.recycle();
            return null;
        }
        boolean hasValue = obtainAttributes.hasValue(6);
        if (hasValue) {
            activity.info.exported = obtainAttributes.getBoolean(6, false);
        }
        activity.info.theme = obtainAttributes.getResourceId(0, 0);
        activity.info.uiOptions = obtainAttributes.getInt(26, activity.info.applicationInfo.uiOptions);
        java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(27, 1024);
        if (nonConfigurationString != null) {
            java.lang.String buildClassName = buildClassName(activity.info.packageName, nonConfigurationString, strArr2);
            if (strArr2[0] != null) {
                android.util.Log.e(TAG, "Activity " + activity.info.name + " specified invalid parentActivityName " + nonConfigurationString);
                strArr2[0] = null;
            } else {
                activity.info.parentActivityName = buildClassName;
            }
        }
        java.lang.String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(4, 0);
        if (nonConfigurationString2 == null) {
            activity.info.permission = r6.applicationInfo.permission;
        } else {
            activity.info.permission = nonConfigurationString2.length() > 0 ? nonConfigurationString2.toString().intern() : null;
        }
        activity.info.taskAffinity = buildTaskAffinityName(r6.applicationInfo.packageName, r6.applicationInfo.taskAffinity, obtainAttributes.getNonConfigurationString(8, 1024), strArr2);
        activity.info.splitName = obtainAttributes.getNonConfigurationString(48, 0);
        activity.info.flags = 0;
        if (obtainAttributes.getBoolean(9, false)) {
            activity.info.flags |= 1;
        }
        if (obtainAttributes.getBoolean(10, false)) {
            activity.info.flags |= 2;
        }
        if (obtainAttributes.getBoolean(11, false)) {
            activity.info.flags |= 4;
        }
        if (obtainAttributes.getBoolean(21, false)) {
            activity.info.flags |= 128;
        }
        if (obtainAttributes.getBoolean(18, false)) {
            android.content.pm.ActivityInfo activityInfo = activity.info;
            activityInfo.flags = 8 | activityInfo.flags;
        }
        if (obtainAttributes.getBoolean(12, false)) {
            activity.info.flags |= 16;
        }
        if (obtainAttributes.getBoolean(13, false)) {
            activity.info.flags |= 32;
        }
        if (obtainAttributes.getBoolean(19, (r6.applicationInfo.flags & 32) != 0)) {
            activity.info.flags |= 64;
        }
        if (obtainAttributes.getBoolean(22, false)) {
            activity.info.flags |= 256;
        }
        if (obtainAttributes.getBoolean(29, false) || obtainAttributes.getBoolean(39, false)) {
            activity.info.flags |= 1024;
        }
        if (obtainAttributes.getBoolean(24, false)) {
            activity.info.flags |= 2048;
        }
        if (obtainAttributes.getBoolean(65, false)) {
            activity.info.flags |= 536870912;
        }
        if (z) {
            activity.info.launchMode = 0;
            activity.info.configChanges = 0;
            if (obtainAttributes.getBoolean(28, false)) {
                activity.info.flags |= 1073741824;
            }
            activity.info.directBootAware = obtainAttributes.getBoolean(42, false);
        } else {
            if (obtainAttributes.getBoolean(25, z2)) {
                activity.info.flags |= 512;
            }
            activity.info.launchMode = obtainAttributes.getInt(14, 0);
            activity.info.documentLaunchMode = obtainAttributes.getInt(33, 0);
            activity.info.maxRecents = obtainAttributes.getInt(34, android.app.ActivityTaskManager.getDefaultAppRecentsLimitStatic());
            activity.info.configChanges = getActivityConfigChanges(obtainAttributes.getInt(16, 0), obtainAttributes.getInt(47, 0));
            activity.info.softInputMode = obtainAttributes.getInt(20, 0);
            activity.info.persistableMode = obtainAttributes.getInteger(32, 0);
            if (obtainAttributes.getBoolean(31, false)) {
                activity.info.flags |= Integer.MIN_VALUE;
            }
            if (obtainAttributes.getBoolean(35, false)) {
                activity.info.flags |= 8192;
            }
            if (obtainAttributes.getBoolean(36, false)) {
                activity.info.flags |= 4096;
            }
            if (obtainAttributes.getBoolean(37, false)) {
                activity.info.flags |= 16384;
            }
            activity.info.screenOrientation = obtainAttributes.getInt(15, -1);
            setActivityResizeMode(activity.info, obtainAttributes, r6);
            if (obtainAttributes.getBoolean(41, false)) {
                activity.info.flags |= 4194304;
            }
            if (obtainAttributes.getBoolean(64, false)) {
                activity.info.flags |= 262144;
            }
            if (obtainAttributes.hasValue(50) && obtainAttributes.getType(50) == 4) {
                activity.setMaxAspectRatio(obtainAttributes.getFloat(50, 0.0f));
            }
            if (obtainAttributes.hasValue(53) && obtainAttributes.getType(53) == 4) {
                activity.setMinAspectRatio(obtainAttributes.getFloat(53, 0.0f));
            }
            activity.info.lockTaskLaunchMode = obtainAttributes.getInt(38, 0);
            activity.info.directBootAware = obtainAttributes.getBoolean(42, false);
            activity.info.requestedVrComponent = obtainAttributes.getString(43);
            activity.info.rotationAnimation = obtainAttributes.getInt(46, -1);
            activity.info.colorMode = obtainAttributes.getInt(49, 0);
            if (obtainAttributes.getBoolean(56, false)) {
                activity.info.flags |= 33554432;
            }
            if (obtainAttributes.getBoolean(51, false)) {
                activity.info.flags |= 8388608;
            }
            if (obtainAttributes.getBoolean(52, false)) {
                activity.info.flags |= 16777216;
            }
            if (obtainAttributes.getBoolean(54, false)) {
                activity.info.privateFlags |= 1;
            }
        }
        if (activity.info.directBootAware) {
            r6.applicationInfo.privateFlags |= 256;
        }
        boolean z4 = obtainAttributes.getBoolean(45, false);
        if (z4) {
            activity.info.flags |= 1048576;
            r6.visibleToInstantApps = true;
        }
        obtainAttributes.recycle();
        if (z && (r6.applicationInfo.privateFlags & 2) != 0 && activity.info.processName == r6.packageName) {
            strArr2[0] = "Heavy-weight applications can not have receivers in main process";
        }
        if (strArr2[0] != null) {
            return null;
        }
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1) {
                z3 = true;
                break;
            }
            if (next == 3 && xmlResourceParser.getDepth() <= depth) {
                z3 = true;
                break;
            }
            if (next == 3) {
                strArr2 = strArr2;
            } else if (next == 4) {
                continue;
            } else if (xmlResourceParser.getName().equals("intent-filter")) {
                android.content.pm.PackageParser.ActivityIntentInfo activityIntentInfo3 = new android.content.pm.PackageParser.ActivityIntentInfo(activity);
                java.lang.String[] strArr3 = strArr2;
                if (!parseIntent(resources, xmlResourceParser, true, true, activityIntentInfo3, strArr)) {
                    return null;
                }
                if (activityIntentInfo3.countActions() == 0) {
                    android.util.Slog.w(TAG, "No actions in intent filter at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                    activityIntentInfo = activityIntentInfo3;
                } else {
                    activity.order = java.lang.Math.max(activityIntentInfo3.getOrder(), activity.order);
                    activityIntentInfo = activityIntentInfo3;
                    activity.intents.add(activityIntentInfo);
                }
                if (z4) {
                    i2 = 1;
                } else if (!z && isImplicitlyExposedIntent(activityIntentInfo)) {
                    i2 = 2;
                } else {
                    i2 = 0;
                }
                activityIntentInfo.setVisibilityToInstantApp(i2);
                if (activityIntentInfo.isVisibleToInstantApp()) {
                    activity.info.flags |= 1048576;
                }
                if (activityIntentInfo.isImplicitlyVisibleToInstantApp()) {
                    activity.info.flags |= 2097152;
                }
                r6 = r23;
                resources2 = resources;
                xmlResourceParser2 = xmlResourceParser;
                strArr2 = strArr3;
            } else {
                java.lang.String[] strArr4 = strArr2;
                if (!z && xmlResourceParser.getName().equals("preferred")) {
                    android.content.pm.PackageParser.ActivityIntentInfo activityIntentInfo4 = new android.content.pm.PackageParser.ActivityIntentInfo(activity);
                    if (!parseIntent(resources, xmlResourceParser, false, false, activityIntentInfo4, strArr)) {
                        return null;
                    }
                    if (activityIntentInfo4.countActions() == 0) {
                        android.util.Slog.w(TAG, "No actions in preferred at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                        r0 = r23;
                        activityIntentInfo2 = activityIntentInfo4;
                    } else {
                        r0 = r23;
                        if (r0.preferredActivityFilters == null) {
                            r0.preferredActivityFilters = new java.util.ArrayList<>();
                        }
                        activityIntentInfo2 = activityIntentInfo4;
                        r0.preferredActivityFilters.add(activityIntentInfo2);
                    }
                    if (z4) {
                        i3 = 1;
                    } else if (!z && isImplicitlyExposedIntent(activityIntentInfo2)) {
                        i3 = 2;
                    } else {
                        i3 = 0;
                    }
                    activityIntentInfo2.setVisibilityToInstantApp(i3);
                    if (activityIntentInfo2.isVisibleToInstantApp()) {
                        activity.info.flags |= 1048576;
                    }
                    if (activityIntentInfo2.isImplicitlyVisibleToInstantApp()) {
                        activity.info.flags |= 2097152;
                    }
                    resources2 = resources;
                    xmlResourceParser2 = xmlResourceParser;
                    r6 = r0;
                    strArr2 = strArr4;
                } else if (xmlResourceParser.getName().equals("meta-data")) {
                    android.os.Bundle parseMetaData = parseMetaData(resources, xmlResourceParser, activity.metaData, strArr4);
                    activity.metaData = parseMetaData;
                    if (parseMetaData != null) {
                        r6 = r23;
                        resources2 = resources;
                        xmlResourceParser2 = xmlResourceParser;
                        strArr2 = strArr4;
                    } else {
                        return null;
                    }
                } else if (z || !xmlResourceParser.getName().equals(android.media.TtmlUtils.TAG_LAYOUT)) {
                    android.util.Slog.w(TAG, "Problem in package " + this.mArchiveSourcePath + ":");
                    if (z) {
                        android.util.Slog.w(TAG, "Unknown element under <receiver>: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                    } else {
                        android.util.Slog.w(TAG, "Unknown element under <activity>: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                    }
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                    r6 = r23;
                    resources2 = resources;
                    xmlResourceParser2 = xmlResourceParser;
                    strArr2 = strArr4;
                } else {
                    parseLayout(resources, xmlResourceParser, activity);
                    r6 = r23;
                    resources2 = resources;
                    xmlResourceParser2 = xmlResourceParser;
                    strArr2 = strArr4;
                }
            }
        }
    }

    private void setActivityResizeMode(android.content.pm.ActivityInfo activityInfo, android.content.res.TypedArray typedArray, android.content.pm.PackageParser.Package r8) {
        boolean z = (r8.applicationInfo.privateFlags & 3072) != 0;
        if (typedArray.hasValue(40) || z) {
            if (typedArray.getBoolean(40, (r8.applicationInfo.privateFlags & 1024) != 0)) {
                activityInfo.resizeMode = 2;
                return;
            } else {
                activityInfo.resizeMode = 0;
                return;
            }
        }
        if ((r8.applicationInfo.privateFlags & 4096) != 0) {
            activityInfo.resizeMode = 1;
            return;
        }
        if (activityInfo.isFixedOrientationPortrait()) {
            activityInfo.resizeMode = 6;
            return;
        }
        if (activityInfo.isFixedOrientationLandscape()) {
            activityInfo.resizeMode = 5;
        } else if (activityInfo.isFixedOrientation()) {
            activityInfo.resizeMode = 7;
        } else {
            activityInfo.resizeMode = 4;
        }
    }

    private void setMaxAspectRatio(android.content.pm.PackageParser.Package r5) {
        float f;
        float f2 = r5.applicationInfo.targetSdkVersion < 26 ? 1.86f : 0.0f;
        if (r5.applicationInfo.maxAspectRatio != 0.0f) {
            f2 = r5.applicationInfo.maxAspectRatio;
        } else if (r5.mAppMetaData != null && r5.mAppMetaData.containsKey("android.max_aspect")) {
            f2 = r5.mAppMetaData.getFloat("android.max_aspect", f2);
        }
        java.util.Iterator<android.content.pm.PackageParser.Activity> it = r5.activities.iterator();
        while (it.hasNext()) {
            android.content.pm.PackageParser.Activity next = it.next();
            if (!next.hasMaxAspectRatio()) {
                if (next.metaData != null) {
                    f = next.metaData.getFloat("android.max_aspect", f2);
                } else {
                    f = f2;
                }
                next.setMaxAspectRatio(f);
            }
        }
    }

    private void setMinAspectRatio(android.content.pm.PackageParser.Package r4) {
        float f = r4.applicationInfo.minAspectRatio;
        java.util.Iterator<android.content.pm.PackageParser.Activity> it = r4.activities.iterator();
        while (it.hasNext()) {
            android.content.pm.PackageParser.Activity next = it.next();
            if (!next.hasMinAspectRatio()) {
                next.setMinAspectRatio(f);
            }
        }
    }

    private void setSupportsSizeChanges(android.content.pm.PackageParser.Package r7) {
        boolean z = r7.mAppMetaData != null && r7.mAppMetaData.getBoolean("android.supports_size_changes", false);
        java.util.Iterator<android.content.pm.PackageParser.Activity> it = r7.activities.iterator();
        while (it.hasNext()) {
            android.content.pm.PackageParser.Activity next = it.next();
            if (z || (next.metaData != null && next.metaData.getBoolean("android.supports_size_changes", false))) {
                next.info.supportsSizeChanges = true;
            }
        }
    }

    public static int getActivityConfigChanges(int i, int i2) {
        return i | ((~i2) & 3);
    }

    private void parseLayout(android.content.res.Resources resources, android.util.AttributeSet attributeSet, android.content.pm.PackageParser.Activity activity) {
        int i;
        float f;
        int i2;
        float f2;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.AndroidManifestLayout);
        int type = obtainAttributes.getType(3);
        if (type == 6) {
            f = obtainAttributes.getFraction(3, 1, 1, -1.0f);
            i = -1;
        } else if (type != 5) {
            i = -1;
            f = -1.0f;
        } else {
            i = obtainAttributes.getDimensionPixelSize(3, -1);
            f = -1.0f;
        }
        int type2 = obtainAttributes.getType(4);
        if (type2 == 6) {
            i2 = -1;
            f2 = obtainAttributes.getFraction(4, 1, 1, -1.0f);
        } else if (type2 != 5) {
            i2 = -1;
            f2 = -1.0f;
        } else {
            i2 = obtainAttributes.getDimensionPixelSize(4, -1);
            f2 = -1.0f;
        }
        int i3 = obtainAttributes.getInt(0, 17);
        int dimensionPixelSize = obtainAttributes.getDimensionPixelSize(1, -1);
        int dimensionPixelSize2 = obtainAttributes.getDimensionPixelSize(2, -1);
        obtainAttributes.recycle();
        activity.info.windowLayout = new android.content.pm.ActivityInfo.WindowLayout(i, f, i2, f2, i3, dimensionPixelSize, dimensionPixelSize2);
    }

    private void resolveWindowLayout(android.content.pm.PackageParser.Activity activity) {
        if (activity.metaData == null || !activity.metaData.containsKey("android.activity_window_layout_affinity")) {
            return;
        }
        android.content.pm.ActivityInfo activityInfo = activity.info;
        if (activityInfo.windowLayout == null || activityInfo.windowLayout.windowLayoutAffinity == null) {
            java.lang.String string = activity.metaData.getString("android.activity_window_layout_affinity");
            if (activityInfo.windowLayout == null) {
                activityInfo.windowLayout = new android.content.pm.ActivityInfo.WindowLayout(-1, -1.0f, -1, -1.0f, 0, -1, -1);
            }
            activityInfo.windowLayout.windowLayoutAffinity = string;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x0336, code lost:
    
        if (r10 != false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0338, code lost:
    
        r0 = r9.info;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0340, code lost:
    
        if (r9.intents.size() <= 0) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0342, code lost:
    
        r14 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0345, code lost:
    
        r0.exported = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0344, code lost:
    
        r14 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0347, code lost:
    
        return r9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.content.pm.PackageParser.Activity parseActivityAlias(android.content.pm.PackageParser.Package r28, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, java.lang.String[] strArr, android.content.pm.PackageParser.CachedComponentArgs cachedComponentArgs) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.pm.PackageParser.Activity activity;
        android.content.pm.PackageParser.ActivityIntentInfo activityIntentInfo;
        int i2;
        android.content.res.Resources resources2 = resources;
        android.content.res.XmlResourceParser xmlResourceParser2 = xmlResourceParser;
        java.lang.String[] strArr2 = strArr;
        android.content.res.TypedArray obtainAttributes = resources2.obtainAttributes(xmlResourceParser2, com.android.internal.R.styleable.AndroidManifestActivityAlias);
        java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(7, 1024);
        if (nonConfigurationString == null) {
            strArr2[0] = "<activity-alias> does not specify android:targetActivity";
            obtainAttributes.recycle();
            return null;
        }
        java.lang.String buildClassName = buildClassName(r28.applicationInfo.packageName, nonConfigurationString, strArr2);
        if (buildClassName == null) {
            obtainAttributes.recycle();
            return null;
        }
        if (cachedComponentArgs.mActivityAliasArgs == null) {
            cachedComponentArgs.mActivityAliasArgs = new android.content.pm.PackageParser.ParseComponentArgs(r28, strArr, 2, 0, 1, 11, 8, 10, this.mSeparateProcesses, 0, 6, 4);
            cachedComponentArgs.mActivityAliasArgs.tag = "<activity-alias>";
        }
        cachedComponentArgs.mActivityAliasArgs.sa = obtainAttributes;
        cachedComponentArgs.mActivityAliasArgs.flags = i;
        int size = r28.activities.size();
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                activity = null;
                break;
            }
            activity = r28.activities.get(i3);
            if (buildClassName.equals(activity.info.name)) {
                break;
            }
            i3++;
        }
        if (activity == null) {
            strArr2[0] = "<activity-alias> target activity " + buildClassName + " not found in manifest";
            obtainAttributes.recycle();
            return null;
        }
        android.content.pm.ActivityInfo activityInfo = new android.content.pm.ActivityInfo();
        activityInfo.targetActivity = buildClassName;
        activityInfo.configChanges = activity.info.configChanges;
        activityInfo.flags = activity.info.flags;
        activityInfo.privateFlags = activity.info.privateFlags;
        activityInfo.icon = activity.info.icon;
        activityInfo.logo = activity.info.logo;
        activityInfo.banner = activity.info.banner;
        activityInfo.labelRes = activity.info.labelRes;
        activityInfo.nonLocalizedLabel = activity.info.nonLocalizedLabel;
        activityInfo.launchMode = activity.info.launchMode;
        activityInfo.lockTaskLaunchMode = activity.info.lockTaskLaunchMode;
        activityInfo.processName = activity.info.processName;
        if (activityInfo.descriptionRes == 0) {
            activityInfo.descriptionRes = activity.info.descriptionRes;
        }
        activityInfo.screenOrientation = activity.info.screenOrientation;
        activityInfo.taskAffinity = activity.info.taskAffinity;
        activityInfo.theme = activity.info.theme;
        activityInfo.softInputMode = activity.info.softInputMode;
        activityInfo.uiOptions = activity.info.uiOptions;
        activityInfo.parentActivityName = activity.info.parentActivityName;
        activityInfo.maxRecents = activity.info.maxRecents;
        activityInfo.windowLayout = activity.info.windowLayout;
        activityInfo.resizeMode = activity.info.resizeMode;
        activityInfo.setMaxAspectRatio(activity.info.getMaxAspectRatio());
        activityInfo.setMinAspectRatio(activity.info.getManifestMinAspectRatio());
        activityInfo.supportsSizeChanges = activity.info.supportsSizeChanges;
        activityInfo.requestedVrComponent = activity.info.requestedVrComponent;
        activityInfo.directBootAware = activity.info.directBootAware;
        android.content.pm.PackageParser.Activity activity2 = new android.content.pm.PackageParser.Activity(cachedComponentArgs.mActivityAliasArgs, activityInfo);
        if (strArr2[0] != null) {
            obtainAttributes.recycle();
            return null;
        }
        boolean hasValue = obtainAttributes.hasValue(5);
        if (hasValue) {
            activity2.info.exported = obtainAttributes.getBoolean(5, false);
        }
        int i4 = 3;
        java.lang.String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(3, 0);
        if (nonConfigurationString2 != null) {
            activity2.info.permission = nonConfigurationString2.length() > 0 ? nonConfigurationString2.toString().intern() : null;
        }
        java.lang.String nonConfigurationString3 = obtainAttributes.getNonConfigurationString(9, 1024);
        if (nonConfigurationString3 != null) {
            java.lang.String buildClassName2 = buildClassName(activity2.info.packageName, nonConfigurationString3, strArr2);
            if (strArr2[0] != null) {
                android.util.Log.e(TAG, "Activity alias " + activity2.info.name + " specified invalid parentActivityName " + nonConfigurationString3);
                strArr2[0] = null;
            } else {
                activity2.info.parentActivityName = buildClassName2;
            }
        }
        int i5 = 1;
        boolean z = (activity2.info.flags & 1048576) != 0;
        obtainAttributes.recycle();
        if (strArr2[0] != null) {
            return null;
        }
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == i5 || (next == i4 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == i4) {
                i4 = 3;
                i5 = 1;
            } else if (next == 4) {
                continue;
            } else if (xmlResourceParser.getName().equals("intent-filter")) {
                android.content.pm.PackageParser.ActivityIntentInfo activityIntentInfo2 = new android.content.pm.PackageParser.ActivityIntentInfo(activity2);
                int i6 = depth;
                java.lang.String[] strArr3 = strArr2;
                android.content.res.XmlResourceParser xmlResourceParser3 = xmlResourceParser2;
                if (!parseIntent(resources, xmlResourceParser, true, true, activityIntentInfo2, strArr)) {
                    return null;
                }
                if (activityIntentInfo2.countActions() == 0) {
                    android.util.Slog.w(TAG, "No actions in intent filter at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                    activityIntentInfo = activityIntentInfo2;
                } else {
                    activity2.order = java.lang.Math.max(activityIntentInfo2.getOrder(), activity2.order);
                    activityIntentInfo = activityIntentInfo2;
                    activity2.intents.add(activityIntentInfo);
                }
                if (z) {
                    i2 = 1;
                } else if (isImplicitlyExposedIntent(activityIntentInfo)) {
                    i2 = 2;
                } else {
                    i2 = 0;
                }
                activityIntentInfo.setVisibilityToInstantApp(i2);
                if (activityIntentInfo.isVisibleToInstantApp()) {
                    activity2.info.flags |= 1048576;
                }
                if (activityIntentInfo.isImplicitlyVisibleToInstantApp()) {
                    activity2.info.flags |= 2097152;
                }
                resources2 = resources;
                strArr2 = strArr3;
                xmlResourceParser2 = xmlResourceParser3;
                depth = i6;
                i4 = 3;
                i5 = 1;
            } else {
                int i7 = depth;
                java.lang.String[] strArr4 = strArr2;
                android.content.res.XmlResourceParser xmlResourceParser4 = xmlResourceParser2;
                if (xmlResourceParser.getName().equals("meta-data")) {
                    android.os.Bundle parseMetaData = parseMetaData(resources, xmlResourceParser4, activity2.metaData, strArr4);
                    activity2.metaData = parseMetaData;
                    if (parseMetaData == null) {
                        return null;
                    }
                    resources2 = resources;
                    strArr2 = strArr4;
                    xmlResourceParser2 = xmlResourceParser4;
                    depth = i7;
                    i4 = 3;
                    i5 = 1;
                } else {
                    android.util.Slog.w(TAG, "Unknown element under <activity-alias>: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                    resources2 = resources;
                    strArr2 = strArr4;
                    xmlResourceParser2 = xmlResourceParser4;
                    depth = i7;
                    i4 = 3;
                    i5 = 1;
                }
            }
        }
    }

    private android.content.pm.PackageParser.Provider parseProvider(android.content.pm.PackageParser.Package r24, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, java.lang.String[] strArr, android.content.pm.PackageParser.CachedComponentArgs cachedComponentArgs) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray typedArray;
        boolean z;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestProvider);
        if (cachedComponentArgs.mProviderArgs != null) {
            typedArray = obtainAttributes;
        } else {
            typedArray = obtainAttributes;
            cachedComponentArgs.mProviderArgs = new android.content.pm.PackageParser.ParseComponentArgs(r24, strArr, 2, 0, 1, 19, 15, 17, this.mSeparateProcesses, 8, 14, 6);
            cachedComponentArgs.mProviderArgs.tag = "<provider>";
        }
        android.content.res.TypedArray typedArray2 = typedArray;
        cachedComponentArgs.mProviderArgs.sa = typedArray2;
        cachedComponentArgs.mProviderArgs.flags = i;
        android.content.pm.PackageParser.Provider provider = new android.content.pm.PackageParser.Provider(cachedComponentArgs.mProviderArgs, new android.content.pm.ProviderInfo());
        if (strArr[0] != null) {
            typedArray2.recycle();
            return null;
        }
        if (r24.applicationInfo.targetSdkVersion >= 17) {
            z = false;
        } else {
            z = true;
        }
        provider.info.exported = typedArray2.getBoolean(7, z);
        java.lang.String nonConfigurationString = typedArray2.getNonConfigurationString(10, 0);
        provider.info.isSyncable = typedArray2.getBoolean(11, false);
        java.lang.String nonConfigurationString2 = typedArray2.getNonConfigurationString(3, 0);
        java.lang.String nonConfigurationString3 = typedArray2.getNonConfigurationString(4, 0);
        if (nonConfigurationString3 == null) {
            nonConfigurationString3 = nonConfigurationString2;
        }
        if (nonConfigurationString3 == null) {
            provider.info.readPermission = r24.applicationInfo.permission;
        } else {
            provider.info.readPermission = nonConfigurationString3.length() > 0 ? nonConfigurationString3.toString().intern() : null;
        }
        java.lang.String nonConfigurationString4 = typedArray2.getNonConfigurationString(5, 0);
        if (nonConfigurationString4 != null) {
            nonConfigurationString2 = nonConfigurationString4;
        }
        if (nonConfigurationString2 == null) {
            provider.info.writePermission = r24.applicationInfo.permission;
        } else {
            provider.info.writePermission = nonConfigurationString2.length() > 0 ? nonConfigurationString2.toString().intern() : null;
        }
        provider.info.grantUriPermissions = typedArray2.getBoolean(13, false);
        provider.info.forceUriPermissions = typedArray2.getBoolean(22, false);
        provider.info.multiprocess = typedArray2.getBoolean(9, false);
        provider.info.initOrder = typedArray2.getInt(12, 0);
        provider.info.splitName = typedArray2.getNonConfigurationString(21, 0);
        provider.info.flags = 0;
        if (typedArray2.getBoolean(16, false)) {
            provider.info.flags |= 1073741824;
        }
        provider.info.directBootAware = typedArray2.getBoolean(18, false);
        if (provider.info.directBootAware) {
            r24.applicationInfo.privateFlags |= 256;
        }
        boolean z2 = typedArray2.getBoolean(20, false);
        if (z2) {
            provider.info.flags |= 1048576;
            r24.visibleToInstantApps = true;
        }
        typedArray2.recycle();
        if ((r24.applicationInfo.privateFlags & 2) != 0 && provider.info.processName == r24.packageName) {
            strArr[0] = "Heavy-weight applications can not have providers in main process";
            return null;
        }
        if (nonConfigurationString == null) {
            strArr[0] = "<provider> does not include authorities attribute";
            return null;
        }
        if (nonConfigurationString.length() <= 0) {
            strArr[0] = "<provider> has empty authorities attribute";
            return null;
        }
        provider.info.authority = nonConfigurationString.intern();
        if (parseProviderTags(resources, xmlResourceParser, z2, provider, strArr)) {
            return provider;
        }
        return null;
    }

    private boolean parseProviderTags(android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, boolean z, android.content.pm.PackageParser.Provider provider, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.os.PatternMatcher patternMatcher;
        java.lang.String str;
        boolean z2;
        android.content.pm.PathPermission pathPermission;
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next != 1) {
                if (next != 3 || xmlResourceParser.getDepth() > depth) {
                    if (next != 3 && next != 4) {
                        if (xmlResourceParser.getName().equals("intent-filter")) {
                            android.content.pm.PackageParser.ProviderIntentInfo providerIntentInfo = new android.content.pm.PackageParser.ProviderIntentInfo(provider);
                            if (!parseIntent(resources, xmlResourceParser, true, false, providerIntentInfo, strArr)) {
                                return false;
                            }
                            if (z) {
                                providerIntentInfo.setVisibilityToInstantApp(1);
                                provider.info.flags |= 1048576;
                            }
                            provider.order = java.lang.Math.max(providerIntentInfo.getOrder(), provider.order);
                            provider.intents.add(providerIntentInfo);
                        } else if (xmlResourceParser.getName().equals("meta-data")) {
                            android.os.Bundle parseMetaData = parseMetaData(resources, xmlResourceParser, provider.metaData, strArr);
                            provider.metaData = parseMetaData;
                            if (parseMetaData == null) {
                                return false;
                            }
                        } else if (xmlResourceParser.getName().equals("grant-uri-permission")) {
                            android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestGrantUriPermission);
                            java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
                            if (nonConfigurationString == null) {
                                patternMatcher = null;
                            } else {
                                patternMatcher = new android.os.PatternMatcher(nonConfigurationString, 0);
                            }
                            java.lang.String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(1, 0);
                            if (nonConfigurationString2 != null) {
                                patternMatcher = new android.os.PatternMatcher(nonConfigurationString2, 1);
                            }
                            java.lang.String nonConfigurationString3 = obtainAttributes.getNonConfigurationString(2, 0);
                            if (nonConfigurationString3 != null) {
                                patternMatcher = new android.os.PatternMatcher(nonConfigurationString3, 2);
                            }
                            obtainAttributes.recycle();
                            if (patternMatcher != null) {
                                if (provider.info.uriPermissionPatterns == null) {
                                    provider.info.uriPermissionPatterns = new android.os.PatternMatcher[1];
                                    provider.info.uriPermissionPatterns[0] = patternMatcher;
                                } else {
                                    int length = provider.info.uriPermissionPatterns.length;
                                    android.os.PatternMatcher[] patternMatcherArr = new android.os.PatternMatcher[length + 1];
                                    java.lang.System.arraycopy(provider.info.uriPermissionPatterns, 0, patternMatcherArr, 0, length);
                                    patternMatcherArr[length] = patternMatcher;
                                    provider.info.uriPermissionPatterns = patternMatcherArr;
                                }
                                provider.info.grantUriPermissions = true;
                                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                            } else {
                                android.util.Slog.w(TAG, "Unknown element under <path-permission>: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                            }
                        } else if (xmlResourceParser.getName().equals("path-permission")) {
                            android.content.res.TypedArray obtainAttributes2 = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestPathPermission);
                            java.lang.String nonConfigurationString4 = obtainAttributes2.getNonConfigurationString(0, 0);
                            java.lang.String nonConfigurationString5 = obtainAttributes2.getNonConfigurationString(1, 0);
                            if (nonConfigurationString5 == null) {
                                nonConfigurationString5 = nonConfigurationString4;
                            }
                            java.lang.String nonConfigurationString6 = obtainAttributes2.getNonConfigurationString(2, 0);
                            if (nonConfigurationString6 != null) {
                                nonConfigurationString4 = nonConfigurationString6;
                            }
                            if (nonConfigurationString5 == null) {
                                str = nonConfigurationString5;
                                z2 = false;
                            } else {
                                str = nonConfigurationString5.intern();
                                z2 = true;
                            }
                            if (nonConfigurationString4 != null) {
                                nonConfigurationString4 = nonConfigurationString4.intern();
                                z2 = true;
                            }
                            if (!z2) {
                                android.util.Slog.w(TAG, "No readPermission or writePermssion for <path-permission>: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                            } else {
                                java.lang.String nonConfigurationString7 = obtainAttributes2.getNonConfigurationString(3, 0);
                                if (nonConfigurationString7 == null) {
                                    pathPermission = null;
                                } else {
                                    pathPermission = new android.content.pm.PathPermission(nonConfigurationString7, 0, str, nonConfigurationString4);
                                }
                                java.lang.String nonConfigurationString8 = obtainAttributes2.getNonConfigurationString(4, 0);
                                if (nonConfigurationString8 != null) {
                                    pathPermission = new android.content.pm.PathPermission(nonConfigurationString8, 1, str, nonConfigurationString4);
                                }
                                java.lang.String nonConfigurationString9 = obtainAttributes2.getNonConfigurationString(5, 0);
                                if (nonConfigurationString9 != null) {
                                    pathPermission = new android.content.pm.PathPermission(nonConfigurationString9, 2, str, nonConfigurationString4);
                                }
                                java.lang.String nonConfigurationString10 = obtainAttributes2.getNonConfigurationString(7, 0);
                                if (nonConfigurationString10 != null) {
                                    pathPermission = new android.content.pm.PathPermission(nonConfigurationString10, 3, str, nonConfigurationString4);
                                }
                                obtainAttributes2.recycle();
                                if (pathPermission != null) {
                                    if (provider.info.pathPermissions == null) {
                                        provider.info.pathPermissions = new android.content.pm.PathPermission[1];
                                        provider.info.pathPermissions[0] = pathPermission;
                                    } else {
                                        int length2 = provider.info.pathPermissions.length;
                                        android.content.pm.PathPermission[] pathPermissionArr = new android.content.pm.PathPermission[length2 + 1];
                                        java.lang.System.arraycopy(provider.info.pathPermissions, 0, pathPermissionArr, 0, length2);
                                        pathPermissionArr[length2] = pathPermission;
                                        provider.info.pathPermissions = pathPermissionArr;
                                    }
                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                } else {
                                    android.util.Slog.w(TAG, "No path, pathPrefix, or pathPattern for <path-permission>: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                }
                            }
                        } else {
                            android.util.Slog.w(TAG, "Unknown element under <provider>: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    private android.content.pm.PackageParser.Service parseService(android.content.pm.PackageParser.Package r23, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, java.lang.String[] strArr, android.content.pm.PackageParser.CachedComponentArgs cachedComponentArgs) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.pm.PackageParser.ServiceIntentInfo serviceIntentInfo;
        android.content.res.Resources resources2 = resources;
        android.content.res.XmlResourceParser xmlResourceParser2 = xmlResourceParser;
        java.lang.String[] strArr2 = strArr;
        android.content.res.TypedArray obtainAttributes = resources2.obtainAttributes(xmlResourceParser2, com.android.internal.R.styleable.AndroidManifestService);
        if (cachedComponentArgs.mServiceArgs == null) {
            cachedComponentArgs.mServiceArgs = new android.content.pm.PackageParser.ParseComponentArgs(r23, strArr, 2, 0, 1, 15, 8, 12, this.mSeparateProcesses, 6, 7, 4);
            cachedComponentArgs.mServiceArgs.tag = "<service>";
        }
        cachedComponentArgs.mServiceArgs.sa = obtainAttributes;
        cachedComponentArgs.mServiceArgs.flags = i;
        android.content.pm.PackageParser.Service service = new android.content.pm.PackageParser.Service(cachedComponentArgs.mServiceArgs, new android.content.pm.ServiceInfo());
        if (strArr2[0] != null) {
            obtainAttributes.recycle();
            return null;
        }
        boolean hasValue = obtainAttributes.hasValue(5);
        if (hasValue) {
            service.info.exported = obtainAttributes.getBoolean(5, false);
        }
        int i2 = 3;
        java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(3, 0);
        if (nonConfigurationString == null) {
            service.info.permission = r23.applicationInfo.permission;
        } else {
            service.info.permission = nonConfigurationString.length() > 0 ? nonConfigurationString.toString().intern() : null;
        }
        service.info.splitName = obtainAttributes.getNonConfigurationString(17, 0);
        service.info.mForegroundServiceType = obtainAttributes.getInt(19, 0);
        service.info.flags = 0;
        if (obtainAttributes.getBoolean(9, false)) {
            service.info.flags |= 1;
        }
        if (obtainAttributes.getBoolean(10, false)) {
            service.info.flags |= 2;
        }
        int i3 = 4;
        if (obtainAttributes.getBoolean(14, false)) {
            service.info.flags |= 4;
        }
        if (obtainAttributes.getBoolean(18, false)) {
            service.info.flags |= 8;
        }
        if (obtainAttributes.getBoolean(11, false)) {
            service.info.flags |= 1073741824;
        }
        service.info.directBootAware = obtainAttributes.getBoolean(13, false);
        if (service.info.directBootAware) {
            r23.applicationInfo.privateFlags |= 256;
        }
        boolean z = obtainAttributes.getBoolean(16, false);
        if (z) {
            service.info.flags |= 1048576;
            r23.visibleToInstantApps = true;
        }
        obtainAttributes.recycle();
        if ((r23.applicationInfo.privateFlags & 2) != 0 && service.info.processName == r23.packageName) {
            strArr2[0] = "Heavy-weight applications can not have services in main process";
            return null;
        }
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == i2 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == i2) {
                i2 = 3;
                i3 = 4;
            } else if (next == i3) {
                continue;
            } else if (xmlResourceParser.getName().equals("intent-filter")) {
                android.content.pm.PackageParser.ServiceIntentInfo serviceIntentInfo2 = new android.content.pm.PackageParser.ServiceIntentInfo(service);
                int i4 = depth;
                java.lang.String[] strArr3 = strArr2;
                android.content.res.XmlResourceParser xmlResourceParser3 = xmlResourceParser2;
                android.content.res.Resources resources3 = resources2;
                if (!parseIntent(resources, xmlResourceParser, true, false, serviceIntentInfo2, strArr)) {
                    return null;
                }
                if (!z) {
                    serviceIntentInfo = serviceIntentInfo2;
                } else {
                    serviceIntentInfo = serviceIntentInfo2;
                    serviceIntentInfo.setVisibilityToInstantApp(1);
                    service.info.flags |= 1048576;
                }
                service.order = java.lang.Math.max(serviceIntentInfo.getOrder(), service.order);
                service.intents.add(serviceIntentInfo);
                strArr2 = strArr3;
                xmlResourceParser2 = xmlResourceParser3;
                resources2 = resources3;
                depth = i4;
                i2 = 3;
                i3 = 4;
            } else {
                int i5 = depth;
                java.lang.String[] strArr4 = strArr2;
                android.content.res.XmlResourceParser xmlResourceParser4 = xmlResourceParser2;
                android.content.res.Resources resources4 = resources2;
                if (xmlResourceParser.getName().equals("meta-data")) {
                    android.os.Bundle parseMetaData = parseMetaData(resources4, xmlResourceParser4, service.metaData, strArr4);
                    service.metaData = parseMetaData;
                    if (parseMetaData == null) {
                        return null;
                    }
                    strArr2 = strArr4;
                    xmlResourceParser2 = xmlResourceParser4;
                    resources2 = resources4;
                    depth = i5;
                    i2 = 3;
                    i3 = 4;
                } else {
                    android.util.Slog.w(TAG, "Unknown element under <service>: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                    strArr2 = strArr4;
                    xmlResourceParser2 = xmlResourceParser4;
                    resources2 = resources4;
                    depth = i5;
                    i2 = 3;
                    i3 = 4;
                }
            }
        }
        if (!hasValue) {
            service.info.exported = service.intents.size() > 0;
        }
        return service;
    }

    private boolean isImplicitlyExposedIntent(android.content.pm.PackageParser.IntentInfo intentInfo) {
        return intentInfo.hasCategory(android.content.Intent.CATEGORY_BROWSABLE) || intentInfo.hasAction(android.content.Intent.ACTION_SEND) || intentInfo.hasAction(android.content.Intent.ACTION_SENDTO) || intentInfo.hasAction(android.content.Intent.ACTION_SEND_MULTIPLE);
    }

    private boolean parseAllMetaData(android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, java.lang.String str, android.content.pm.PackageParser.Component<?> component, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                if (xmlResourceParser.getName().equals("meta-data")) {
                    android.os.Bundle parseMetaData = parseMetaData(resources, xmlResourceParser, component.metaData, strArr);
                    component.metaData = parseMetaData;
                    if (parseMetaData == null) {
                        return false;
                    }
                } else {
                    android.util.Slog.w(TAG, "Unknown element under " + str + ": " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                }
            }
        }
        return true;
    }

    private android.os.Bundle parseMetaData(android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.os.Bundle bundle, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestMetaData);
        if (bundle == null) {
            bundle = new android.os.Bundle();
        }
        boolean z = false;
        java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
        if (nonConfigurationString == null) {
            strArr[0] = "<meta-data> requires an android:name attribute";
            obtainAttributes.recycle();
            return null;
        }
        java.lang.String intern = nonConfigurationString.intern();
        android.util.TypedValue peekValue = obtainAttributes.peekValue(2);
        if (peekValue != null && peekValue.resourceId != 0) {
            bundle.putInt(intern, peekValue.resourceId);
        } else {
            android.util.TypedValue peekValue2 = obtainAttributes.peekValue(1);
            if (peekValue2 == null) {
                strArr[0] = "<meta-data> requires an android:value or android:resource attribute";
                bundle = null;
            } else if (peekValue2.type == 3) {
                java.lang.CharSequence coerceToString = peekValue2.coerceToString();
                bundle.putString(intern, coerceToString != null ? coerceToString.toString() : null);
            } else if (peekValue2.type == 18) {
                if (peekValue2.data != 0) {
                    z = true;
                }
                bundle.putBoolean(intern, z);
            } else if (peekValue2.type >= 16 && peekValue2.type <= 31) {
                bundle.putInt(intern, peekValue2.data);
            } else if (peekValue2.type == 4) {
                bundle.putFloat(intern, peekValue2.getFloat());
            } else {
                android.util.Slog.w(TAG, "<meta-data> only supports string, integer, float, color, boolean, and resource reference types: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
            }
        }
        obtainAttributes.recycle();
        com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
        return bundle;
    }

    private static android.content.pm.VerifierInfo parseVerifier(android.util.AttributeSet attributeSet) {
        int attributeCount = attributeSet.getAttributeCount();
        java.lang.String str = null;
        java.lang.String str2 = null;
        for (int i = 0; i < attributeCount; i++) {
            switch (attributeSet.getAttributeNameResource(i)) {
                case 16842755:
                    str = attributeSet.getAttributeValue(i);
                    break;
                case 16843686:
                    str2 = attributeSet.getAttributeValue(i);
                    break;
            }
        }
        if (str == null || str.length() == 0) {
            android.util.Slog.i(TAG, "verifier package name was null; skipping");
            return null;
        }
        java.security.PublicKey parsePublicKey = parsePublicKey(str2);
        if (parsePublicKey == null) {
            android.util.Slog.i(TAG, "Unable to parse verifier public key for " + str);
            return null;
        }
        return new android.content.pm.VerifierInfo(str, parsePublicKey);
    }

    public static final java.security.PublicKey parsePublicKey(java.lang.String str) {
        if (str == null) {
            android.util.Slog.w(TAG, "Could not parse null public key");
            return null;
        }
        try {
            return parsePublicKey(android.util.Base64.decode(str, 0));
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.w(TAG, "Could not parse verifier public key; invalid Base64");
            return null;
        }
    }

    public static final java.security.PublicKey parsePublicKey(byte[] bArr) {
        if (bArr == null) {
            android.util.Slog.w(TAG, "Could not parse null public key");
            return null;
        }
        try {
            java.security.spec.X509EncodedKeySpec x509EncodedKeySpec = new java.security.spec.X509EncodedKeySpec(bArr);
            try {
                return java.security.KeyFactory.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_RSA).generatePublic(x509EncodedKeySpec);
            } catch (java.security.NoSuchAlgorithmException e) {
                android.util.Slog.wtf(TAG, "Could not parse public key: RSA KeyFactory not included in build");
                try {
                    return java.security.KeyFactory.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_EC).generatePublic(x509EncodedKeySpec);
                } catch (java.security.NoSuchAlgorithmException e2) {
                    android.util.Slog.wtf(TAG, "Could not parse public key: EC KeyFactory not included in build");
                    try {
                        return java.security.KeyFactory.getInstance("DSA").generatePublic(x509EncodedKeySpec);
                    } catch (java.security.NoSuchAlgorithmException e3) {
                        android.util.Slog.wtf(TAG, "Could not parse public key: DSA KeyFactory not included in build");
                        return null;
                    } catch (java.security.spec.InvalidKeySpecException e4) {
                        return null;
                    }
                } catch (java.security.spec.InvalidKeySpecException e5) {
                    return java.security.KeyFactory.getInstance("DSA").generatePublic(x509EncodedKeySpec);
                }
            } catch (java.security.spec.InvalidKeySpecException e6) {
                return java.security.KeyFactory.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_EC).generatePublic(x509EncodedKeySpec);
            }
        } catch (java.lang.IllegalArgumentException e7) {
            android.util.Slog.w(TAG, "Could not parse verifier public key; invalid Base64");
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00a1, code lost:
    
        r22[0] = "No value supplied for <android:name>";
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00a3, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00c0, code lost:
    
        r22[0] = "No value supplied for <android:name>";
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00c2, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean parseIntent(android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, boolean z, boolean z2, android.content.pm.PackageParser.IntentInfo intentInfo, java.lang.String[] strArr) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        char c;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestIntentFilter);
        intentInfo.setPriority(obtainAttributes.getInt(2, 0));
        intentInfo.setOrder(obtainAttributes.getInt(3, 0));
        android.util.TypedValue peekValue = obtainAttributes.peekValue(0);
        if (peekValue != null) {
            int i = peekValue.resourceId;
            intentInfo.labelRes = i;
            if (i == 0) {
                intentInfo.nonLocalizedLabel = peekValue.coerceToString();
            }
        }
        int resourceId = sUseRoundIcon ? obtainAttributes.getResourceId(7, 0) : 0;
        if (resourceId != 0) {
            intentInfo.icon = resourceId;
        } else {
            intentInfo.icon = obtainAttributes.getResourceId(1, 0);
        }
        intentInfo.logo = obtainAttributes.getResourceId(4, 0);
        intentInfo.banner = obtainAttributes.getResourceId(5, 0);
        char c2 = 6;
        if (z2) {
            intentInfo.setAutoVerify(obtainAttributes.getBoolean(6, false));
        }
        obtainAttributes.recycle();
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                java.lang.String name = xmlResourceParser.getName();
                if (name.equals("action")) {
                    java.lang.String attributeValue = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                    if (attributeValue == null || attributeValue == "") {
                        break;
                    }
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                    intentInfo.addAction(attributeValue);
                    c = 6;
                } else if (name.equals("category")) {
                    java.lang.String attributeValue2 = xmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "name");
                    if (attributeValue2 == null || attributeValue2 == "") {
                        break;
                    }
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                    intentInfo.addCategory(attributeValue2);
                    c = 6;
                } else if (name.equals("data")) {
                    android.content.res.TypedArray obtainAttributes2 = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestData);
                    java.lang.String nonConfigurationString = obtainAttributes2.getNonConfigurationString(0, 0);
                    if (nonConfigurationString != null) {
                        try {
                            intentInfo.addDataType(nonConfigurationString);
                        } catch (android.content.IntentFilter.MalformedMimeTypeException e) {
                            strArr[0] = e.toString();
                            obtainAttributes2.recycle();
                            return false;
                        }
                    }
                    java.lang.String nonConfigurationString2 = obtainAttributes2.getNonConfigurationString(1, 0);
                    if (nonConfigurationString2 != null) {
                        intentInfo.addDataScheme(nonConfigurationString2);
                    }
                    java.lang.String nonConfigurationString3 = obtainAttributes2.getNonConfigurationString(8, 0);
                    if (nonConfigurationString3 != null) {
                        intentInfo.addDataSchemeSpecificPart(nonConfigurationString3, 0);
                    }
                    java.lang.String nonConfigurationString4 = obtainAttributes2.getNonConfigurationString(9, 0);
                    if (nonConfigurationString4 != null) {
                        intentInfo.addDataSchemeSpecificPart(nonConfigurationString4, 1);
                    }
                    java.lang.String nonConfigurationString5 = obtainAttributes2.getNonConfigurationString(10, 0);
                    if (nonConfigurationString5 != null) {
                        if (!z) {
                            strArr[0] = "sspPattern not allowed here; ssp must be literal";
                            return false;
                        }
                        intentInfo.addDataSchemeSpecificPart(nonConfigurationString5, 2);
                    }
                    java.lang.String nonConfigurationString6 = obtainAttributes2.getNonConfigurationString(2, 0);
                    java.lang.String nonConfigurationString7 = obtainAttributes2.getNonConfigurationString(3, 0);
                    if (nonConfigurationString6 != null) {
                        intentInfo.addDataAuthority(nonConfigurationString6, nonConfigurationString7);
                    }
                    java.lang.String nonConfigurationString8 = obtainAttributes2.getNonConfigurationString(4, 0);
                    if (nonConfigurationString8 != null) {
                        intentInfo.addDataPath(nonConfigurationString8, 0);
                    }
                    java.lang.String nonConfigurationString9 = obtainAttributes2.getNonConfigurationString(5, 0);
                    if (nonConfigurationString9 != null) {
                        intentInfo.addDataPath(nonConfigurationString9, 1);
                    }
                    c = 6;
                    java.lang.String nonConfigurationString10 = obtainAttributes2.getNonConfigurationString(6, 0);
                    if (nonConfigurationString10 != null) {
                        if (!z) {
                            strArr[0] = "pathPattern not allowed here; path must be literal";
                            return false;
                        }
                        intentInfo.addDataPath(nonConfigurationString10, 2);
                    }
                    java.lang.String nonConfigurationString11 = obtainAttributes2.getNonConfigurationString(14, 0);
                    if (nonConfigurationString11 != null) {
                        if (!z) {
                            strArr[0] = "pathAdvancedPattern not allowed here; path must be literal";
                            return false;
                        }
                        intentInfo.addDataPath(nonConfigurationString11, 3);
                    }
                    obtainAttributes2.recycle();
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                } else {
                    c = 6;
                    android.util.Slog.w(TAG, "Unknown element under <intent-filter>: " + xmlResourceParser.getName() + " at " + this.mArchiveSourcePath + " " + xmlResourceParser.getPositionDescription());
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                }
                c2 = c;
            }
        }
        intentInfo.hasDefault = intentInfo.hasCategory(android.content.Intent.CATEGORY_DEFAULT);
        return true;
    }

    public static final class SigningDetails implements android.os.Parcelable {
        private static final int PAST_CERT_EXISTS = 0;
        public final android.content.pm.Signature[] pastSigningCertificates;
        public final android.util.ArraySet<java.security.PublicKey> publicKeys;
        public final int signatureSchemeVersion;
        public final android.content.pm.Signature[] signatures;
        public static final android.content.pm.PackageParser.SigningDetails UNKNOWN = new android.content.pm.PackageParser.SigningDetails(null, 0, null, null);
        public static final android.os.Parcelable.Creator<android.content.pm.PackageParser.SigningDetails> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageParser.SigningDetails>() { // from class: android.content.pm.PackageParser.SigningDetails.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.SigningDetails createFromParcel(android.os.Parcel parcel) {
                if (parcel.readBoolean()) {
                    return android.content.pm.PackageParser.SigningDetails.UNKNOWN;
                }
                return new android.content.pm.PackageParser.SigningDetails(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.SigningDetails[] newArray(int i) {
                return new android.content.pm.PackageParser.SigningDetails[i];
            }
        };

        public @interface CertCapabilities {
            public static final int AUTH = 16;
            public static final int INSTALLED_DATA = 1;
            public static final int PERMISSION = 4;
            public static final int ROLLBACK = 8;
            public static final int SHARED_USER_ID = 2;
        }

        public @interface SignatureSchemeVersion {
            public static final int JAR = 1;
            public static final int SIGNING_BLOCK_V2 = 2;
            public static final int SIGNING_BLOCK_V3 = 3;
            public static final int SIGNING_BLOCK_V4 = 4;
            public static final int UNKNOWN = 0;
        }

        public SigningDetails(android.content.pm.Signature[] signatureArr, int i, android.util.ArraySet<java.security.PublicKey> arraySet, android.content.pm.Signature[] signatureArr2) {
            this.signatures = signatureArr;
            this.signatureSchemeVersion = i;
            this.publicKeys = arraySet;
            this.pastSigningCertificates = signatureArr2;
        }

        public SigningDetails(android.content.pm.Signature[] signatureArr, int i, android.content.pm.Signature[] signatureArr2) throws java.security.cert.CertificateException {
            this(signatureArr, i, android.content.pm.PackageParser.toSigningKeys(signatureArr), signatureArr2);
        }

        public SigningDetails(android.content.pm.Signature[] signatureArr, int i) throws java.security.cert.CertificateException {
            this(signatureArr, i, null);
        }

        public SigningDetails(android.content.pm.PackageParser.SigningDetails signingDetails) {
            if (signingDetails != null) {
                if (signingDetails.signatures != null) {
                    this.signatures = (android.content.pm.Signature[]) signingDetails.signatures.clone();
                } else {
                    this.signatures = null;
                }
                this.signatureSchemeVersion = signingDetails.signatureSchemeVersion;
                this.publicKeys = new android.util.ArraySet<>((android.util.ArraySet) signingDetails.publicKeys);
                if (signingDetails.pastSigningCertificates != null) {
                    this.pastSigningCertificates = (android.content.pm.Signature[]) signingDetails.pastSigningCertificates.clone();
                    return;
                } else {
                    this.pastSigningCertificates = null;
                    return;
                }
            }
            this.signatures = null;
            this.signatureSchemeVersion = 0;
            this.publicKeys = null;
            this.pastSigningCertificates = null;
        }

        public android.content.pm.PackageParser.SigningDetails mergeLineageWith(android.content.pm.PackageParser.SigningDetails signingDetails) {
            if (!hasPastSigningCertificates()) {
                return (signingDetails.hasPastSigningCertificates() && signingDetails.hasAncestorOrSelf(this)) ? signingDetails : this;
            }
            if (!signingDetails.hasPastSigningCertificates()) {
                return this;
            }
            android.content.pm.PackageParser.SigningDetails descendantOrSelf = getDescendantOrSelf(signingDetails);
            if (descendantOrSelf == null) {
                return this;
            }
            return descendantOrSelf == this ? mergeLineageWithAncestorOrSelf(signingDetails) : signingDetails.mergeLineageWithAncestorOrSelf(this);
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x007a, code lost:
        
            if (r8 < 0) goto L50;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x007c, code lost:
        
            return r10;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private android.content.pm.PackageParser.SigningDetails mergeLineageWithAncestorOrSelf(android.content.pm.PackageParser.SigningDetails signingDetails) {
            int i;
            int i2;
            int length = this.pastSigningCertificates.length - 1;
            int length2 = signingDetails.pastSigningCertificates.length - 1;
            if (length < 0 || length2 < 0) {
                return this;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            while (length >= 0 && !this.pastSigningCertificates[length].equals(signingDetails.pastSigningCertificates[length2])) {
                arrayList.add(new android.content.pm.Signature(this.pastSigningCertificates[length]));
                length--;
            }
            if (length >= 0) {
                boolean z = false;
                while (true) {
                    i = length - 1;
                    android.content.pm.Signature signature = this.pastSigningCertificates[length];
                    i2 = length2 - 1;
                    android.content.pm.Signature signature2 = signingDetails.pastSigningCertificates[length2];
                    android.content.pm.Signature signature3 = new android.content.pm.Signature(signature);
                    int flags = signature2.getFlags() & signature.getFlags();
                    if (signature.getFlags() != flags) {
                        signature3.setFlags(flags);
                        z = true;
                    }
                    arrayList.add(signature3);
                    if (i < 0 || i2 < 0 || !this.pastSigningCertificates[i].equals(signingDetails.pastSigningCertificates[i2])) {
                        break;
                    }
                    length = i;
                    length2 = i2;
                }
                while (i2 >= 0) {
                    arrayList.add(new android.content.pm.Signature(signingDetails.pastSigningCertificates[i2]));
                    i2--;
                }
                while (i >= 0) {
                    arrayList.add(new android.content.pm.Signature(this.pastSigningCertificates[i]));
                    i--;
                }
                if (arrayList.size() == this.pastSigningCertificates.length && !z) {
                    return this;
                }
                java.util.Collections.reverse(arrayList);
                try {
                    return new android.content.pm.PackageParser.SigningDetails(new android.content.pm.Signature[]{new android.content.pm.Signature(this.signatures[0])}, this.signatureSchemeVersion, (android.content.pm.Signature[]) arrayList.toArray(new android.content.pm.Signature[0]));
                } catch (java.security.cert.CertificateException e) {
                    android.util.Slog.e(android.content.pm.PackageParser.TAG, "Caught an exception creating the merged lineage: ", e);
                    return this;
                }
            }
            return this;
        }

        public boolean hasCommonAncestor(android.content.pm.PackageParser.SigningDetails signingDetails) {
            if (!hasPastSigningCertificates()) {
                return signingDetails.hasAncestorOrSelf(this);
            }
            if (signingDetails.hasPastSigningCertificates()) {
                return getDescendantOrSelf(signingDetails) != null;
            }
            return hasAncestorOrSelf(signingDetails);
        }

        public boolean hasAncestorOrSelfWithDigest(java.util.Set<java.lang.String> set) {
            if (this == UNKNOWN || set == null || set.size() == 0) {
                return false;
            }
            if (this.signatures.length > 1) {
                if (set.size() < this.signatures.length) {
                    return false;
                }
                for (android.content.pm.Signature signature : this.signatures) {
                    if (!set.contains(android.util.PackageUtils.computeSha256Digest(signature.toByteArray()))) {
                        return false;
                    }
                }
                return true;
            }
            if (set.contains(android.util.PackageUtils.computeSha256Digest(this.signatures[0].toByteArray()))) {
                return true;
            }
            if (hasPastSigningCertificates()) {
                for (int i = 0; i < this.pastSigningCertificates.length - 1; i++) {
                    if (set.contains(android.util.PackageUtils.computeSha256Digest(this.pastSigningCertificates[i].toByteArray()))) {
                        return true;
                    }
                }
            }
            return false;
        }

        private android.content.pm.PackageParser.SigningDetails getDescendantOrSelf(android.content.pm.PackageParser.SigningDetails signingDetails) {
            android.content.pm.PackageParser.SigningDetails signingDetails2;
            if (hasAncestorOrSelf(signingDetails)) {
                signingDetails2 = signingDetails;
                signingDetails = this;
            } else {
                if (!signingDetails.hasAncestor(this)) {
                    return null;
                }
                signingDetails2 = this;
            }
            int length = signingDetails.pastSigningCertificates.length - 1;
            int length2 = signingDetails2.pastSigningCertificates.length - 1;
            while (length >= 0 && !signingDetails.pastSigningCertificates[length].equals(signingDetails2.pastSigningCertificates[length2])) {
                length--;
            }
            if (length < 0) {
                return null;
            }
            do {
                length--;
                length2--;
                if (length < 0 || length2 < 0) {
                    break;
                }
            } while (signingDetails.pastSigningCertificates[length].equals(signingDetails2.pastSigningCertificates[length2]));
            if (length < 0 || length2 < 0) {
                return signingDetails;
            }
            return null;
        }

        public boolean hasSignatures() {
            return this.signatures != null && this.signatures.length > 0;
        }

        public boolean hasPastSigningCertificates() {
            return this.pastSigningCertificates != null && this.pastSigningCertificates.length > 0;
        }

        public boolean hasAncestorOrSelf(android.content.pm.PackageParser.SigningDetails signingDetails) {
            if (this == UNKNOWN || signingDetails == UNKNOWN) {
                return false;
            }
            if (signingDetails.signatures.length > 1) {
                return signaturesMatchExactly(signingDetails);
            }
            return hasCertificate(signingDetails.signatures[0]);
        }

        public boolean hasAncestor(android.content.pm.PackageParser.SigningDetails signingDetails) {
            if (this != UNKNOWN && signingDetails != UNKNOWN && hasPastSigningCertificates() && signingDetails.signatures.length == 1) {
                for (int i = 0; i < this.pastSigningCertificates.length - 1; i++) {
                    if (this.pastSigningCertificates[i].equals(signingDetails.signatures[0])) {
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean hasCommonSignerWithCapability(android.content.pm.PackageParser.SigningDetails signingDetails, int i) {
            if (this == UNKNOWN || signingDetails == UNKNOWN) {
                return false;
            }
            if (this.signatures.length > 1 || signingDetails.signatures.length > 1) {
                return signaturesMatchExactly(signingDetails);
            }
            android.util.ArraySet arraySet = new android.util.ArraySet();
            if (signingDetails.hasPastSigningCertificates()) {
                arraySet.addAll(java.util.Arrays.asList(signingDetails.pastSigningCertificates));
            } else {
                arraySet.addAll(java.util.Arrays.asList(signingDetails.signatures));
            }
            if (arraySet.contains(this.signatures[0])) {
                return true;
            }
            if (hasPastSigningCertificates()) {
                for (int i2 = 0; i2 < this.pastSigningCertificates.length - 1; i2++) {
                    if (arraySet.contains(this.pastSigningCertificates[i2]) && (this.pastSigningCertificates[i2].getFlags() & i) == i) {
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean checkCapability(android.content.pm.PackageParser.SigningDetails signingDetails, int i) {
            if (this == UNKNOWN || signingDetails == UNKNOWN) {
                return false;
            }
            if (signingDetails.signatures.length > 1) {
                return signaturesMatchExactly(signingDetails);
            }
            return hasCertificate(signingDetails.signatures[0], i);
        }

        public boolean checkCapabilityRecover(android.content.pm.PackageParser.SigningDetails signingDetails, int i) throws java.security.cert.CertificateException {
            if (signingDetails == UNKNOWN || this == UNKNOWN) {
                return false;
            }
            if (hasPastSigningCertificates() && signingDetails.signatures.length == 1) {
                for (int i2 = 0; i2 < this.pastSigningCertificates.length; i2++) {
                    if (android.content.pm.Signature.areEffectiveMatch(signingDetails.signatures[0], this.pastSigningCertificates[i2]) && this.pastSigningCertificates[i2].getFlags() == i) {
                        return true;
                    }
                }
                return false;
            }
            return android.content.pm.Signature.areEffectiveArraysMatch(signingDetails.signatures, this.signatures);
        }

        public boolean hasCertificate(android.content.pm.Signature signature) {
            return hasCertificateInternal(signature, 0);
        }

        public boolean hasCertificate(android.content.pm.Signature signature, int i) {
            return hasCertificateInternal(signature, i);
        }

        public boolean hasCertificate(byte[] bArr) {
            return hasCertificate(new android.content.pm.Signature(bArr));
        }

        private boolean hasCertificateInternal(android.content.pm.Signature signature, int i) {
            if (this == UNKNOWN) {
                return false;
            }
            if (hasPastSigningCertificates()) {
                for (int i2 = 0; i2 < this.pastSigningCertificates.length - 1; i2++) {
                    if (this.pastSigningCertificates[i2].equals(signature) && (i == 0 || (this.pastSigningCertificates[i2].getFlags() & i) == i)) {
                        return true;
                    }
                }
            }
            return this.signatures.length == 1 && this.signatures[0].equals(signature);
        }

        public boolean checkCapability(java.lang.String str, int i) {
            if (this == UNKNOWN) {
                return false;
            }
            if (hasSha256Certificate(str == null ? null : libcore.util.HexEncoding.decode(str, false), i)) {
                return true;
            }
            return android.util.PackageUtils.computeSignaturesSha256Digest(android.util.PackageUtils.computeSignaturesSha256Digests(this.signatures)).equals(str);
        }

        public boolean hasSha256Certificate(byte[] bArr) {
            return hasSha256CertificateInternal(bArr, 0);
        }

        public boolean hasSha256Certificate(byte[] bArr, int i) {
            return hasSha256CertificateInternal(bArr, i);
        }

        private boolean hasSha256CertificateInternal(byte[] bArr, int i) {
            if (this == UNKNOWN) {
                return false;
            }
            if (hasPastSigningCertificates()) {
                for (int i2 = 0; i2 < this.pastSigningCertificates.length - 1; i2++) {
                    if (java.util.Arrays.equals(bArr, android.util.PackageUtils.computeSha256DigestBytes(this.pastSigningCertificates[i2].toByteArray())) && (i == 0 || (this.pastSigningCertificates[i2].getFlags() & i) == i)) {
                        return true;
                    }
                }
            }
            if (this.signatures.length == 1) {
                return java.util.Arrays.equals(bArr, android.util.PackageUtils.computeSha256DigestBytes(this.signatures[0].toByteArray()));
            }
            return false;
        }

        public boolean signaturesMatchExactly(android.content.pm.PackageParser.SigningDetails signingDetails) {
            return android.content.pm.Signature.areExactArraysMatch(this.signatures, signingDetails.signatures);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            boolean z = UNKNOWN == this;
            parcel.writeBoolean(z);
            if (z) {
                return;
            }
            parcel.writeTypedArray(this.signatures, i);
            parcel.writeInt(this.signatureSchemeVersion);
            parcel.writeArraySet(this.publicKeys);
            parcel.writeTypedArray(this.pastSigningCertificates, i);
        }

        protected SigningDetails(android.os.Parcel parcel) {
            java.lang.ClassLoader classLoader = java.lang.Object.class.getClassLoader();
            this.signatures = (android.content.pm.Signature[]) parcel.createTypedArray(android.content.pm.Signature.CREATOR);
            this.signatureSchemeVersion = parcel.readInt();
            this.publicKeys = parcel.readArraySet(classLoader);
            this.pastSigningCertificates = (android.content.pm.Signature[]) parcel.createTypedArray(android.content.pm.Signature.CREATOR);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.content.pm.PackageParser.SigningDetails)) {
                return false;
            }
            android.content.pm.PackageParser.SigningDetails signingDetails = (android.content.pm.PackageParser.SigningDetails) obj;
            if (this.signatureSchemeVersion != signingDetails.signatureSchemeVersion || !android.content.pm.Signature.areExactArraysMatch(this.signatures, signingDetails.signatures)) {
                return false;
            }
            if (this.publicKeys != null) {
                if (!this.publicKeys.equals(signingDetails.publicKeys)) {
                    return false;
                }
            } else if (signingDetails.publicKeys != null) {
                return false;
            }
            if (!java.util.Arrays.equals(this.pastSigningCertificates, signingDetails.pastSigningCertificates)) {
                return false;
            }
            for (int i = 0; i < this.pastSigningCertificates.length; i++) {
                if (this.pastSigningCertificates[i].getFlags() != signingDetails.pastSigningCertificates[i].getFlags()) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            return (((((java.util.Arrays.hashCode(this.signatures) * 31) + this.signatureSchemeVersion) * 31) + (this.publicKeys != null ? this.publicKeys.hashCode() : 0)) * 31) + java.util.Arrays.hashCode(this.pastSigningCertificates);
        }

        public static class Builder {
            private android.content.pm.Signature[] mPastSigningCertificates;
            private int mSignatureSchemeVersion = 0;
            private android.content.pm.Signature[] mSignatures;

            public android.content.pm.PackageParser.SigningDetails.Builder setSignatures(android.content.pm.Signature[] signatureArr) {
                this.mSignatures = signatureArr;
                return this;
            }

            public android.content.pm.PackageParser.SigningDetails.Builder setSignatureSchemeVersion(int i) {
                this.mSignatureSchemeVersion = i;
                return this;
            }

            public android.content.pm.PackageParser.SigningDetails.Builder setPastSigningCertificates(android.content.pm.Signature[] signatureArr) {
                this.mPastSigningCertificates = signatureArr;
                return this;
            }

            private void checkInvariants() {
                if (this.mSignatures == null) {
                    throw new java.lang.IllegalStateException("SigningDetails requires the current signing certificates.");
                }
            }

            public android.content.pm.PackageParser.SigningDetails build() throws java.security.cert.CertificateException {
                checkInvariants();
                return new android.content.pm.PackageParser.SigningDetails(this.mSignatures, this.mSignatureSchemeVersion, this.mPastSigningCertificates);
            }
        }
    }

    public static final class Package implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageParser.Package>() { // from class: android.content.pm.PackageParser.Package.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.Package createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.PackageParser.Package(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.Package[] newArray(int i) {
                return new android.content.pm.PackageParser.Package[i];
            }
        };
        public final java.util.ArrayList<android.content.pm.PackageParser.Activity> activities;
        public android.content.pm.ApplicationInfo applicationInfo;
        public java.lang.String baseCodePath;
        public boolean baseHardwareAccelerated;
        public int baseRevisionCode;
        public java.util.ArrayList<android.content.pm.PackageParser.Package> childPackages;
        public java.lang.String codePath;
        public java.util.ArrayList<android.content.pm.ConfigurationInfo> configPreferences;
        public boolean coreApp;
        public java.lang.String cpuAbiOverride;
        public java.util.ArrayList<android.content.pm.FeatureGroupInfo> featureGroups;
        public final java.util.ArrayList<java.lang.String> implicitPermissions;
        public int installLocation;
        public final java.util.ArrayList<android.content.pm.PackageParser.Instrumentation> instrumentation;
        public boolean isStub;
        public java.util.ArrayList<java.lang.String> libraryNames;
        public java.util.ArrayList<java.lang.String> mAdoptPermissions;
        public android.os.Bundle mAppMetaData;
        public int mCompileSdkVersion;
        public java.lang.String mCompileSdkVersionCodename;
        public java.lang.Object mExtras;
        public android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.security.PublicKey>> mKeySetMapping;
        public long[] mLastPackageUsageTimeInMills;
        public java.util.ArrayList<java.lang.String> mOriginalPackages;
        public java.lang.String mOverlayCategory;
        public boolean mOverlayIsStatic;
        public int mOverlayPriority;
        public java.lang.String mOverlayTarget;
        public java.lang.String mOverlayTargetName;
        public int mPreferredOrder;
        public java.lang.String mRealPackage;
        public java.lang.String mRequiredAccountType;
        public boolean mRequiredForAllUsers;
        public java.lang.String mRestrictedAccountType;
        public java.lang.String mSharedUserId;
        public int mSharedUserLabel;
        public android.content.pm.PackageParser.SigningDetails mSigningDetails;
        public android.util.ArraySet<java.lang.String> mUpgradeKeySets;
        public int mVersionCode;
        public int mVersionCodeMajor;
        public java.lang.String mVersionName;
        public java.lang.String manifestPackageName;
        public java.lang.String packageName;
        public android.content.pm.PackageParser.Package parentPackage;
        public final java.util.ArrayList<android.content.pm.PackageParser.PermissionGroup> permissionGroups;
        public final java.util.ArrayList<android.content.pm.PackageParser.Permission> permissions;
        public java.util.ArrayList<android.content.pm.PackageParser.ActivityIntentInfo> preferredActivityFilters;
        public java.util.ArrayList<java.lang.String> protectedBroadcasts;
        public final java.util.ArrayList<android.content.pm.PackageParser.Provider> providers;
        public final java.util.ArrayList<android.content.pm.PackageParser.Activity> receivers;
        public java.util.ArrayList<android.content.pm.FeatureInfo> reqFeatures;
        public final java.util.ArrayList<java.lang.String> requestedPermissions;
        public byte[] restrictUpdateHash;
        public final java.util.ArrayList<android.content.pm.PackageParser.Service> services;
        public java.lang.String[] splitCodePaths;
        public int[] splitFlags;
        public java.lang.String[] splitNames;
        public int[] splitPrivateFlags;
        public int[] splitRevisionCodes;
        public java.lang.String staticSharedLibName;
        public long staticSharedLibVersion;
        public boolean use32bitAbi;
        public java.util.ArrayList<java.lang.String> usesLibraries;
        public java.lang.String[] usesLibraryFiles;
        public java.util.ArrayList<android.content.pm.SharedLibraryInfo> usesLibraryInfos;
        public java.util.ArrayList<java.lang.String> usesOptionalLibraries;
        public java.util.ArrayList<java.lang.String> usesStaticLibraries;
        public java.lang.String[][] usesStaticLibrariesCertDigests;
        public long[] usesStaticLibrariesVersions;
        public boolean visibleToInstantApps;
        public java.lang.String volumeUuid;

        public long getLongVersionCode() {
            return android.content.pm.PackageInfo.composeLongVersionCode(this.mVersionCodeMajor, this.mVersionCode);
        }

        public Package(java.lang.String str) {
            this.applicationInfo = new android.content.pm.ApplicationInfo();
            this.permissions = new java.util.ArrayList<>(0);
            this.permissionGroups = new java.util.ArrayList<>(0);
            this.activities = new java.util.ArrayList<>(0);
            this.receivers = new java.util.ArrayList<>(0);
            this.providers = new java.util.ArrayList<>(0);
            this.services = new java.util.ArrayList<>(0);
            this.instrumentation = new java.util.ArrayList<>(0);
            this.requestedPermissions = new java.util.ArrayList<>();
            this.implicitPermissions = new java.util.ArrayList<>();
            this.staticSharedLibName = null;
            this.staticSharedLibVersion = 0L;
            this.libraryNames = null;
            this.usesLibraries = null;
            this.usesStaticLibraries = null;
            this.usesStaticLibrariesVersions = null;
            this.usesStaticLibrariesCertDigests = null;
            this.usesOptionalLibraries = null;
            this.usesLibraryFiles = null;
            this.usesLibraryInfos = null;
            this.preferredActivityFilters = null;
            this.mOriginalPackages = null;
            this.mRealPackage = null;
            this.mAdoptPermissions = null;
            this.mAppMetaData = null;
            this.mSigningDetails = android.content.pm.PackageParser.SigningDetails.UNKNOWN;
            this.mPreferredOrder = 0;
            this.mLastPackageUsageTimeInMills = new long[8];
            this.configPreferences = null;
            this.reqFeatures = null;
            this.featureGroups = null;
            this.packageName = str;
            this.manifestPackageName = str;
            this.applicationInfo.packageName = str;
            this.applicationInfo.uid = -1;
        }

        public void setApplicationVolumeUuid(java.lang.String str) {
            java.util.UUID convert = android.os.storage.StorageManager.convert(str);
            this.applicationInfo.volumeUuid = str;
            this.applicationInfo.storageUuid = convert;
            if (this.childPackages != null) {
                int size = this.childPackages.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).applicationInfo.volumeUuid = str;
                    this.childPackages.get(i).applicationInfo.storageUuid = convert;
                }
            }
        }

        public void setApplicationInfoCodePath(java.lang.String str) {
            this.applicationInfo.setCodePath(str);
            if (this.childPackages != null) {
                int size = this.childPackages.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).applicationInfo.setCodePath(str);
                }
            }
        }

        @java.lang.Deprecated
        public void setApplicationInfoResourcePath(java.lang.String str) {
            this.applicationInfo.setResourcePath(str);
            if (this.childPackages != null) {
                int size = this.childPackages.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).applicationInfo.setResourcePath(str);
                }
            }
        }

        @java.lang.Deprecated
        public void setApplicationInfoBaseResourcePath(java.lang.String str) {
            this.applicationInfo.setBaseResourcePath(str);
            if (this.childPackages != null) {
                int size = this.childPackages.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).applicationInfo.setBaseResourcePath(str);
                }
            }
        }

        public void setApplicationInfoBaseCodePath(java.lang.String str) {
            this.applicationInfo.setBaseCodePath(str);
            if (this.childPackages != null) {
                int size = this.childPackages.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).applicationInfo.setBaseCodePath(str);
                }
            }
        }

        public java.util.List<java.lang.String> getChildPackageNames() {
            if (this.childPackages == null) {
                return null;
            }
            int size = this.childPackages.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            for (int i = 0; i < size; i++) {
                arrayList.add(this.childPackages.get(i).packageName);
            }
            return arrayList;
        }

        public boolean hasChildPackage(java.lang.String str) {
            int size = this.childPackages != null ? this.childPackages.size() : 0;
            for (int i = 0; i < size; i++) {
                if (this.childPackages.get(i).packageName.equals(str)) {
                    return true;
                }
            }
            return false;
        }

        public void setApplicationInfoSplitCodePaths(java.lang.String[] strArr) {
            this.applicationInfo.setSplitCodePaths(strArr);
        }

        @java.lang.Deprecated
        public void setApplicationInfoSplitResourcePaths(java.lang.String[] strArr) {
            this.applicationInfo.setSplitResourcePaths(strArr);
        }

        public void setSplitCodePaths(java.lang.String[] strArr) {
            this.splitCodePaths = strArr;
        }

        public void setCodePath(java.lang.String str) {
            this.codePath = str;
            if (this.childPackages != null) {
                int size = this.childPackages.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).codePath = str;
                }
            }
        }

        public void setBaseCodePath(java.lang.String str) {
            this.baseCodePath = str;
            if (this.childPackages != null) {
                int size = this.childPackages.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).baseCodePath = str;
                }
            }
        }

        public void setSigningDetails(android.content.pm.PackageParser.SigningDetails signingDetails) {
            this.mSigningDetails = signingDetails;
            if (this.childPackages != null) {
                int size = this.childPackages.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).mSigningDetails = signingDetails;
                }
            }
        }

        public void setVolumeUuid(java.lang.String str) {
            this.volumeUuid = str;
            if (this.childPackages != null) {
                int size = this.childPackages.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).volumeUuid = str;
                }
            }
        }

        public void setApplicationInfoFlags(int i, int i2) {
            android.content.pm.ApplicationInfo applicationInfo = this.applicationInfo;
            int i3 = this.applicationInfo.flags;
            int i4 = ~i;
            int i5 = i & i2;
            applicationInfo.flags = (i3 & i4) | i5;
            if (this.childPackages != null) {
                int size = this.childPackages.size();
                for (int i6 = 0; i6 < size; i6++) {
                    this.childPackages.get(i6).applicationInfo.flags = (this.applicationInfo.flags & i4) | i5;
                }
            }
        }

        public void setUse32bitAbi(boolean z) {
            this.use32bitAbi = z;
            if (this.childPackages != null) {
                int size = this.childPackages.size();
                for (int i = 0; i < size; i++) {
                    this.childPackages.get(i).use32bitAbi = z;
                }
            }
        }

        public boolean isLibrary() {
            return (this.staticSharedLibName == null && com.android.internal.util.ArrayUtils.isEmpty(this.libraryNames)) ? false : true;
        }

        public java.util.List<java.lang.String> getAllCodePaths() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.add(this.baseCodePath);
            if (!com.android.internal.util.ArrayUtils.isEmpty(this.splitCodePaths)) {
                java.util.Collections.addAll(arrayList, this.splitCodePaths);
            }
            return arrayList;
        }

        public java.util.List<java.lang.String> getAllCodePathsExcludingResourceOnly() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if ((this.applicationInfo.flags & 4) != 0) {
                arrayList.add(this.baseCodePath);
            }
            if (!com.android.internal.util.ArrayUtils.isEmpty(this.splitCodePaths)) {
                for (int i = 0; i < this.splitCodePaths.length; i++) {
                    if ((this.splitFlags[i] & 4) != 0) {
                        arrayList.add(this.splitCodePaths[i]);
                    }
                }
            }
            return arrayList;
        }

        public void setPackageName(java.lang.String str) {
            this.packageName = str;
            this.applicationInfo.packageName = str;
            for (int size = this.permissions.size() - 1; size >= 0; size--) {
                this.permissions.get(size).setPackageName(str);
            }
            for (int size2 = this.permissionGroups.size() - 1; size2 >= 0; size2--) {
                this.permissionGroups.get(size2).setPackageName(str);
            }
            for (int size3 = this.activities.size() - 1; size3 >= 0; size3--) {
                this.activities.get(size3).setPackageName(str);
            }
            for (int size4 = this.receivers.size() - 1; size4 >= 0; size4--) {
                this.receivers.get(size4).setPackageName(str);
            }
            for (int size5 = this.providers.size() - 1; size5 >= 0; size5--) {
                this.providers.get(size5).setPackageName(str);
            }
            for (int size6 = this.services.size() - 1; size6 >= 0; size6--) {
                this.services.get(size6).setPackageName(str);
            }
            for (int size7 = this.instrumentation.size() - 1; size7 >= 0; size7--) {
                this.instrumentation.get(size7).setPackageName(str);
            }
        }

        public boolean hasComponentClassName(java.lang.String str) {
            for (int size = this.activities.size() - 1; size >= 0; size--) {
                if (str.equals(this.activities.get(size).className)) {
                    return true;
                }
            }
            for (int size2 = this.receivers.size() - 1; size2 >= 0; size2--) {
                if (str.equals(this.receivers.get(size2).className)) {
                    return true;
                }
            }
            for (int size3 = this.providers.size() - 1; size3 >= 0; size3--) {
                if (str.equals(this.providers.get(size3).className)) {
                    return true;
                }
            }
            for (int size4 = this.services.size() - 1; size4 >= 0; size4--) {
                if (str.equals(this.services.get(size4).className)) {
                    return true;
                }
            }
            for (int size5 = this.instrumentation.size() - 1; size5 >= 0; size5--) {
                if (str.equals(this.instrumentation.get(size5).className)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isExternal() {
            return this.applicationInfo.isExternal();
        }

        public boolean isForwardLocked() {
            return false;
        }

        public boolean isOem() {
            return this.applicationInfo.isOem();
        }

        public boolean isVendor() {
            return this.applicationInfo.isVendor();
        }

        public boolean isProduct() {
            return this.applicationInfo.isProduct();
        }

        public boolean isSystemExt() {
            return this.applicationInfo.isSystemExt();
        }

        public boolean isOdm() {
            return this.applicationInfo.isOdm();
        }

        public boolean isPrivileged() {
            return this.applicationInfo.isPrivilegedApp();
        }

        public boolean isSystem() {
            return this.applicationInfo.isSystemApp();
        }

        public boolean isUpdatedSystemApp() {
            return this.applicationInfo.isUpdatedSystemApp();
        }

        public boolean canHaveOatDir() {
            return true;
        }

        public boolean isMatch(int i) {
            if ((i & 1048576) != 0) {
                return isSystem();
            }
            return true;
        }

        public long getLatestPackageUseTimeInMills() {
            long j = 0;
            for (long j2 : this.mLastPackageUsageTimeInMills) {
                j = java.lang.Math.max(j, j2);
            }
            return j;
        }

        public long getLatestForegroundPackageUseTimeInMills() {
            int[] iArr = {0, 2};
            long j = 0;
            for (int i = 0; i < 2; i++) {
                j = java.lang.Math.max(j, this.mLastPackageUsageTimeInMills[iArr[i]]);
            }
            return j;
        }

        public java.lang.String toString() {
            return "Package{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.packageName + "}";
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Package(android.os.Parcel parcel) {
            this.applicationInfo = new android.content.pm.ApplicationInfo();
            this.permissions = new java.util.ArrayList<>(0);
            this.permissionGroups = new java.util.ArrayList<>(0);
            this.activities = new java.util.ArrayList<>(0);
            this.receivers = new java.util.ArrayList<>(0);
            this.providers = new java.util.ArrayList<>(0);
            this.services = new java.util.ArrayList<>(0);
            this.instrumentation = new java.util.ArrayList<>(0);
            this.requestedPermissions = new java.util.ArrayList<>();
            this.implicitPermissions = new java.util.ArrayList<>();
            this.staticSharedLibName = null;
            this.staticSharedLibVersion = 0L;
            this.libraryNames = null;
            this.usesLibraries = null;
            this.usesStaticLibraries = null;
            this.usesStaticLibrariesVersions = null;
            this.usesStaticLibrariesCertDigests = null;
            this.usesOptionalLibraries = null;
            this.usesLibraryFiles = null;
            this.usesLibraryInfos = null;
            this.preferredActivityFilters = null;
            this.mOriginalPackages = null;
            this.mRealPackage = null;
            this.mAdoptPermissions = null;
            this.mAppMetaData = null;
            this.mSigningDetails = android.content.pm.PackageParser.SigningDetails.UNKNOWN;
            this.mPreferredOrder = 0;
            this.mLastPackageUsageTimeInMills = new long[8];
            this.configPreferences = null;
            this.reqFeatures = null;
            this.featureGroups = null;
            java.lang.ClassLoader classLoader = java.lang.Object.class.getClassLoader();
            this.packageName = parcel.readString().intern();
            this.manifestPackageName = parcel.readString();
            this.splitNames = parcel.readStringArray();
            this.volumeUuid = parcel.readString();
            this.codePath = parcel.readString();
            this.baseCodePath = parcel.readString();
            this.splitCodePaths = parcel.readStringArray();
            this.baseRevisionCode = parcel.readInt();
            this.splitRevisionCodes = parcel.createIntArray();
            this.splitFlags = parcel.createIntArray();
            this.splitPrivateFlags = parcel.createIntArray();
            this.baseHardwareAccelerated = parcel.readInt() == 1;
            this.applicationInfo = (android.content.pm.ApplicationInfo) parcel.readParcelable(classLoader, android.content.pm.ApplicationInfo.class);
            if (this.applicationInfo.permission != null) {
                this.applicationInfo.permission = this.applicationInfo.permission.intern();
            }
            parcel.readParcelableList(this.permissions, classLoader, android.content.pm.PackageParser.Permission.class);
            fixupOwner(this.permissions);
            parcel.readParcelableList(this.permissionGroups, classLoader, android.content.pm.PackageParser.PermissionGroup.class);
            fixupOwner(this.permissionGroups);
            parcel.readParcelableList(this.activities, classLoader, android.content.pm.PackageParser.Activity.class);
            fixupOwner(this.activities);
            parcel.readParcelableList(this.receivers, classLoader, android.content.pm.PackageParser.Activity.class);
            fixupOwner(this.receivers);
            parcel.readParcelableList(this.providers, classLoader, android.content.pm.PackageParser.Provider.class);
            fixupOwner(this.providers);
            parcel.readParcelableList(this.services, classLoader, android.content.pm.PackageParser.Service.class);
            fixupOwner(this.services);
            parcel.readParcelableList(this.instrumentation, classLoader, android.content.pm.PackageParser.Instrumentation.class);
            fixupOwner(this.instrumentation);
            parcel.readStringList(this.requestedPermissions);
            internStringArrayList(this.requestedPermissions);
            parcel.readStringList(this.implicitPermissions);
            internStringArrayList(this.implicitPermissions);
            this.protectedBroadcasts = parcel.createStringArrayList();
            internStringArrayList(this.protectedBroadcasts);
            this.parentPackage = (android.content.pm.PackageParser.Package) parcel.readParcelable(classLoader, android.content.pm.PackageParser.Package.class);
            this.childPackages = new java.util.ArrayList<>();
            parcel.readParcelableList(this.childPackages, classLoader, android.content.pm.PackageParser.Package.class);
            if (this.childPackages.size() == 0) {
                this.childPackages = null;
            }
            this.staticSharedLibName = parcel.readString();
            if (this.staticSharedLibName != null) {
                this.staticSharedLibName = this.staticSharedLibName.intern();
            }
            this.staticSharedLibVersion = parcel.readLong();
            this.libraryNames = parcel.createStringArrayList();
            internStringArrayList(this.libraryNames);
            this.usesLibraries = parcel.createStringArrayList();
            internStringArrayList(this.usesLibraries);
            this.usesOptionalLibraries = parcel.createStringArrayList();
            internStringArrayList(this.usesOptionalLibraries);
            this.usesLibraryFiles = parcel.readStringArray();
            this.usesLibraryInfos = parcel.createTypedArrayList(android.content.pm.SharedLibraryInfo.CREATOR);
            int readInt = parcel.readInt();
            if (readInt > 0) {
                this.usesStaticLibraries = new java.util.ArrayList<>(readInt);
                parcel.readStringList(this.usesStaticLibraries);
                internStringArrayList(this.usesStaticLibraries);
                this.usesStaticLibrariesVersions = new long[readInt];
                parcel.readLongArray(this.usesStaticLibrariesVersions);
                this.usesStaticLibrariesCertDigests = new java.lang.String[readInt][];
                for (int i = 0; i < readInt; i++) {
                    this.usesStaticLibrariesCertDigests[i] = parcel.createStringArray();
                }
            }
            this.preferredActivityFilters = new java.util.ArrayList<>();
            parcel.readParcelableList(this.preferredActivityFilters, classLoader, android.content.pm.PackageParser.ActivityIntentInfo.class);
            if (this.preferredActivityFilters.size() == 0) {
                this.preferredActivityFilters = null;
            }
            this.mOriginalPackages = parcel.createStringArrayList();
            this.mRealPackage = parcel.readString();
            this.mAdoptPermissions = parcel.createStringArrayList();
            this.mAppMetaData = parcel.readBundle();
            this.mVersionCode = parcel.readInt();
            this.mVersionCodeMajor = parcel.readInt();
            this.mVersionName = parcel.readString();
            if (this.mVersionName != null) {
                this.mVersionName = this.mVersionName.intern();
            }
            this.mSharedUserId = parcel.readString();
            if (this.mSharedUserId != null) {
                this.mSharedUserId = this.mSharedUserId.intern();
            }
            this.mSharedUserLabel = parcel.readInt();
            this.mSigningDetails = (android.content.pm.PackageParser.SigningDetails) parcel.readParcelable(classLoader, android.content.pm.PackageParser.SigningDetails.class);
            this.mPreferredOrder = parcel.readInt();
            this.configPreferences = new java.util.ArrayList<>();
            parcel.readParcelableList(this.configPreferences, classLoader, android.content.pm.ConfigurationInfo.class);
            if (this.configPreferences.size() == 0) {
                this.configPreferences = null;
            }
            this.reqFeatures = new java.util.ArrayList<>();
            parcel.readParcelableList(this.reqFeatures, classLoader, android.content.pm.FeatureInfo.class);
            if (this.reqFeatures.size() == 0) {
                this.reqFeatures = null;
            }
            this.featureGroups = new java.util.ArrayList<>();
            parcel.readParcelableList(this.featureGroups, classLoader, android.content.pm.FeatureGroupInfo.class);
            if (this.featureGroups.size() == 0) {
                this.featureGroups = null;
            }
            this.installLocation = parcel.readInt();
            this.coreApp = parcel.readInt() == 1;
            this.mRequiredForAllUsers = parcel.readInt() == 1;
            this.mRestrictedAccountType = parcel.readString();
            this.mRequiredAccountType = parcel.readString();
            this.mOverlayTarget = parcel.readString();
            this.mOverlayTargetName = parcel.readString();
            this.mOverlayCategory = parcel.readString();
            this.mOverlayPriority = parcel.readInt();
            this.mOverlayIsStatic = parcel.readInt() == 1;
            this.mCompileSdkVersion = parcel.readInt();
            this.mCompileSdkVersionCodename = parcel.readString();
            this.mUpgradeKeySets = parcel.readArraySet(classLoader);
            this.mKeySetMapping = android.content.pm.PackageParser.readKeySetMapping(parcel);
            this.cpuAbiOverride = parcel.readString();
            this.use32bitAbi = parcel.readInt() == 1;
            this.restrictUpdateHash = parcel.createByteArray();
            this.visibleToInstantApps = parcel.readInt() == 1;
        }

        private static void internStringArrayList(java.util.List<java.lang.String> list) {
            if (list != null) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    list.set(i, list.get(i).intern());
                }
            }
        }

        public void fixupOwner(java.util.List<? extends android.content.pm.PackageParser.Component<?>> list) {
            if (list != null) {
                for (android.content.pm.PackageParser.Component<?> component : list) {
                    component.owner = this;
                    if (component instanceof android.content.pm.PackageParser.Activity) {
                        ((android.content.pm.PackageParser.Activity) component).info.applicationInfo = this.applicationInfo;
                    } else if (component instanceof android.content.pm.PackageParser.Service) {
                        ((android.content.pm.PackageParser.Service) component).info.applicationInfo = this.applicationInfo;
                    } else if (component instanceof android.content.pm.PackageParser.Provider) {
                        ((android.content.pm.PackageParser.Provider) component).info.applicationInfo = this.applicationInfo;
                    }
                }
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.packageName);
            parcel.writeString(this.manifestPackageName);
            parcel.writeStringArray(this.splitNames);
            parcel.writeString(this.volumeUuid);
            parcel.writeString(this.codePath);
            parcel.writeString(this.baseCodePath);
            parcel.writeStringArray(this.splitCodePaths);
            parcel.writeInt(this.baseRevisionCode);
            parcel.writeIntArray(this.splitRevisionCodes);
            parcel.writeIntArray(this.splitFlags);
            parcel.writeIntArray(this.splitPrivateFlags);
            parcel.writeInt(this.baseHardwareAccelerated ? 1 : 0);
            parcel.writeParcelable(this.applicationInfo, i);
            parcel.writeParcelableList(this.permissions, i);
            parcel.writeParcelableList(this.permissionGroups, i);
            parcel.writeParcelableList(this.activities, i);
            parcel.writeParcelableList(this.receivers, i);
            parcel.writeParcelableList(this.providers, i);
            parcel.writeParcelableList(this.services, i);
            parcel.writeParcelableList(this.instrumentation, i);
            parcel.writeStringList(this.requestedPermissions);
            parcel.writeStringList(this.implicitPermissions);
            parcel.writeStringList(this.protectedBroadcasts);
            parcel.writeParcelable(this.parentPackage, i);
            parcel.writeParcelableList(this.childPackages, i);
            parcel.writeString(this.staticSharedLibName);
            parcel.writeLong(this.staticSharedLibVersion);
            parcel.writeStringList(this.libraryNames);
            parcel.writeStringList(this.usesLibraries);
            parcel.writeStringList(this.usesOptionalLibraries);
            parcel.writeStringArray(this.usesLibraryFiles);
            parcel.writeTypedList(this.usesLibraryInfos);
            if (com.android.internal.util.ArrayUtils.isEmpty(this.usesStaticLibraries)) {
                parcel.writeInt(-1);
            } else {
                parcel.writeInt(this.usesStaticLibraries.size());
                parcel.writeStringList(this.usesStaticLibraries);
                parcel.writeLongArray(this.usesStaticLibrariesVersions);
                for (java.lang.String[] strArr : this.usesStaticLibrariesCertDigests) {
                    parcel.writeStringArray(strArr);
                }
            }
            parcel.writeParcelableList(this.preferredActivityFilters, i);
            parcel.writeStringList(this.mOriginalPackages);
            parcel.writeString(this.mRealPackage);
            parcel.writeStringList(this.mAdoptPermissions);
            parcel.writeBundle(this.mAppMetaData);
            parcel.writeInt(this.mVersionCode);
            parcel.writeInt(this.mVersionCodeMajor);
            parcel.writeString(this.mVersionName);
            parcel.writeString(this.mSharedUserId);
            parcel.writeInt(this.mSharedUserLabel);
            parcel.writeParcelable(this.mSigningDetails, i);
            parcel.writeInt(this.mPreferredOrder);
            parcel.writeParcelableList(this.configPreferences, i);
            parcel.writeParcelableList(this.reqFeatures, i);
            parcel.writeParcelableList(this.featureGroups, i);
            parcel.writeInt(this.installLocation);
            parcel.writeInt(this.coreApp ? 1 : 0);
            parcel.writeInt(this.mRequiredForAllUsers ? 1 : 0);
            parcel.writeString(this.mRestrictedAccountType);
            parcel.writeString(this.mRequiredAccountType);
            parcel.writeString(this.mOverlayTarget);
            parcel.writeString(this.mOverlayTargetName);
            parcel.writeString(this.mOverlayCategory);
            parcel.writeInt(this.mOverlayPriority);
            parcel.writeInt(this.mOverlayIsStatic ? 1 : 0);
            parcel.writeInt(this.mCompileSdkVersion);
            parcel.writeString(this.mCompileSdkVersionCodename);
            parcel.writeArraySet(this.mUpgradeKeySets);
            android.content.pm.PackageParser.writeKeySetMapping(parcel, this.mKeySetMapping);
            parcel.writeString(this.cpuAbiOverride);
            parcel.writeInt(this.use32bitAbi ? 1 : 0);
            parcel.writeByteArray(this.restrictUpdateHash);
            parcel.writeInt(this.visibleToInstantApps ? 1 : 0);
        }
    }

    public static abstract class Component<II extends android.content.pm.PackageParser.IntentInfo> {
        public final java.lang.String className;
        android.content.ComponentName componentName;
        java.lang.String componentShortName;
        public final java.util.ArrayList<II> intents;
        public android.os.Bundle metaData;
        public int order;
        public android.content.pm.PackageParser.Package owner;

        public Component(android.content.pm.PackageParser.Package r1, java.util.ArrayList<II> arrayList, java.lang.String str) {
            this.owner = r1;
            this.intents = arrayList;
            this.className = str;
        }

        public Component(android.content.pm.PackageParser.Package r1) {
            this.owner = r1;
            this.intents = null;
            this.className = null;
        }

        public Component(android.content.pm.PackageParser.ParsePackageItemArgs parsePackageItemArgs, android.content.pm.PackageItemInfo packageItemInfo) {
            this.owner = parsePackageItemArgs.owner;
            this.intents = new java.util.ArrayList<>(0);
            if (android.content.pm.PackageParser.parsePackageItemInfo(parsePackageItemArgs.owner, packageItemInfo, parsePackageItemArgs.outError, parsePackageItemArgs.tag, parsePackageItemArgs.sa, true, parsePackageItemArgs.nameRes, parsePackageItemArgs.labelRes, parsePackageItemArgs.iconRes, parsePackageItemArgs.roundIconRes, parsePackageItemArgs.logoRes, parsePackageItemArgs.bannerRes)) {
                this.className = packageItemInfo.name;
            } else {
                this.className = null;
            }
        }

        public Component(android.content.pm.PackageParser.ParseComponentArgs parseComponentArgs, android.content.pm.ComponentInfo componentInfo) {
            this((android.content.pm.PackageParser.ParsePackageItemArgs) parseComponentArgs, (android.content.pm.PackageItemInfo) componentInfo);
            java.lang.String nonResourceString;
            if (parseComponentArgs.outError[0] != null) {
                return;
            }
            if (parseComponentArgs.processRes != 0) {
                if (this.owner.applicationInfo.targetSdkVersion >= 8) {
                    nonResourceString = parseComponentArgs.sa.getNonConfigurationString(parseComponentArgs.processRes, 1024);
                } else {
                    nonResourceString = parseComponentArgs.sa.getNonResourceString(parseComponentArgs.processRes);
                }
                componentInfo.processName = android.content.pm.PackageParser.buildProcessName(this.owner.applicationInfo.packageName, this.owner.applicationInfo.processName, nonResourceString, parseComponentArgs.flags, parseComponentArgs.sepProcesses, parseComponentArgs.outError);
            }
            if (parseComponentArgs.descriptionRes != 0) {
                componentInfo.descriptionRes = parseComponentArgs.sa.getResourceId(parseComponentArgs.descriptionRes, 0);
            }
            componentInfo.enabled = parseComponentArgs.sa.getBoolean(parseComponentArgs.enabledRes, true);
        }

        public Component(android.content.pm.PackageParser.Component<II> component) {
            this.owner = component.owner;
            this.intents = component.intents;
            this.className = component.className;
            this.componentName = component.componentName;
            this.componentShortName = component.componentShortName;
        }

        public android.content.ComponentName getComponentName() {
            if (this.componentName != null) {
                return this.componentName;
            }
            if (this.className != null) {
                this.componentName = new android.content.ComponentName(this.owner.applicationInfo.packageName, this.className);
            }
            return this.componentName;
        }

        protected Component(android.os.Parcel parcel) {
            this.className = parcel.readString();
            this.metaData = parcel.readBundle();
            this.intents = createIntentsList(parcel);
            this.owner = null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.className);
            parcel.writeBundle(this.metaData);
            writeIntentsList(this.intents, parcel, i);
        }

        private static void writeIntentsList(java.util.ArrayList<? extends android.content.pm.PackageParser.IntentInfo> arrayList, android.os.Parcel parcel, int i) {
            if (arrayList == null) {
                parcel.writeInt(-1);
                return;
            }
            int size = arrayList.size();
            parcel.writeInt(size);
            if (size > 0) {
                parcel.writeString(arrayList.get(0).getClass().getName());
                for (int i2 = 0; i2 < size; i2++) {
                    arrayList.get(i2).writeIntentInfoToParcel(parcel, i);
                }
            }
        }

        private static <T extends android.content.pm.PackageParser.IntentInfo> java.util.ArrayList<T> createIntentsList(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == -1) {
                return null;
            }
            if (readInt == 0) {
                return new java.util.ArrayList<>(0);
            }
            java.lang.String readString = parcel.readString();
            try {
                java.lang.reflect.Constructor<?> constructor = java.lang.Class.forName(readString).getConstructor(android.os.Parcel.class);
                android.view.ViewGroup.ChildListForAutoFillOrContentCapture childListForAutoFillOrContentCapture = (java.util.ArrayList<T>) new java.util.ArrayList(readInt);
                for (int i = 0; i < readInt; i++) {
                    childListForAutoFillOrContentCapture.add((android.content.pm.PackageParser.IntentInfo) constructor.newInstance(parcel));
                }
                return childListForAutoFillOrContentCapture;
            } catch (java.lang.ReflectiveOperationException e) {
                throw new java.lang.AssertionError("Unable to construct intent list for: " + readString);
            }
        }

        public void appendComponentShortName(java.lang.StringBuilder sb) {
            android.content.ComponentName.appendShortString(sb, this.owner.applicationInfo.packageName, this.className);
        }

        public void printComponentShortName(java.io.PrintWriter printWriter) {
            android.content.ComponentName.printShortString(printWriter, this.owner.applicationInfo.packageName, this.className);
        }

        public void setPackageName(java.lang.String str) {
            this.componentName = null;
            this.componentShortName = null;
        }
    }

    public static final class Permission extends android.content.pm.PackageParser.Component<android.content.pm.PackageParser.IntentInfo> implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageParser.Permission>() { // from class: android.content.pm.PackageParser.Permission.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.Permission createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.PackageParser.Permission(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.Permission[] newArray(int i) {
                return new android.content.pm.PackageParser.Permission[i];
            }
        };
        public android.content.pm.PackageParser.PermissionGroup group;
        public final android.content.pm.PermissionInfo info;
        public boolean tree;

        public Permission(android.content.pm.PackageParser.Package r1, java.lang.String str) {
            super(r1);
            this.info = new android.content.pm.PermissionInfo(str);
        }

        public Permission(android.content.pm.PackageParser.Package r1, android.content.pm.PermissionInfo permissionInfo) {
            super(r1);
            this.info = permissionInfo;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(java.lang.String str) {
            super.setPackageName(str);
            this.info.packageName = str;
        }

        public java.lang.String toString() {
            return "Permission{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.info.name + "}";
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.info, i);
            parcel.writeInt(this.tree ? 1 : 0);
            parcel.writeParcelable(this.group, i);
        }

        public boolean isAppOp() {
            return this.info.isAppOp();
        }

        private Permission(android.os.Parcel parcel) {
            super(parcel);
            java.lang.ClassLoader classLoader = java.lang.Object.class.getClassLoader();
            this.info = (android.content.pm.PermissionInfo) parcel.readParcelable(classLoader, android.content.pm.PermissionInfo.class);
            if (this.info.group != null) {
                this.info.group = this.info.group.intern();
            }
            this.tree = parcel.readInt() == 1;
            this.group = (android.content.pm.PackageParser.PermissionGroup) parcel.readParcelable(classLoader, android.content.pm.PackageParser.PermissionGroup.class);
        }
    }

    public static final class PermissionGroup extends android.content.pm.PackageParser.Component<android.content.pm.PackageParser.IntentInfo> implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageParser.PermissionGroup>() { // from class: android.content.pm.PackageParser.PermissionGroup.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.PermissionGroup createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.PackageParser.PermissionGroup(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.PermissionGroup[] newArray(int i) {
                return new android.content.pm.PackageParser.PermissionGroup[i];
            }
        };
        public final android.content.pm.PermissionGroupInfo info;

        public PermissionGroup(android.content.pm.PackageParser.Package r1, int i, int i2, int i3) {
            super(r1);
            this.info = new android.content.pm.PermissionGroupInfo(i, i2, i3);
        }

        public PermissionGroup(android.content.pm.PackageParser.Package r1, android.content.pm.PermissionGroupInfo permissionGroupInfo) {
            super(r1);
            this.info = permissionGroupInfo;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(java.lang.String str) {
            super.setPackageName(str);
            this.info.packageName = str;
        }

        public java.lang.String toString() {
            return "PermissionGroup{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.info.name + "}";
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.info, i);
        }

        private PermissionGroup(android.os.Parcel parcel) {
            super(parcel);
            this.info = (android.content.pm.PermissionGroupInfo) parcel.readParcelable(java.lang.Object.class.getClassLoader(), android.content.pm.PermissionGroupInfo.class);
        }
    }

    private static boolean copyNeeded(int i, android.content.pm.PackageParser.Package r4, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, android.os.Bundle bundle, int i2) {
        if (i2 != 0) {
            return true;
        }
        if (frameworkPackageUserState.getEnabledState() != 0) {
            if (r4.applicationInfo.enabled != (frameworkPackageUserState.getEnabledState() == 1)) {
                return true;
            }
        }
        if (frameworkPackageUserState.isSuspended() != ((r4.applicationInfo.flags & 1073741824) != 0) || !frameworkPackageUserState.isInstalled() || frameworkPackageUserState.isHidden() || frameworkPackageUserState.isStopped() || frameworkPackageUserState.isInstantApp() != r4.applicationInfo.isInstantApp()) {
            return true;
        }
        if ((i & 128) != 0 && (bundle != null || r4.mAppMetaData != null)) {
            return true;
        }
        int i3 = i & 1024;
        if (i3 == 0 || r4.usesLibraryFiles == null) {
            return ((i3 == 0 || r4.usesLibraryInfos == null) && r4.staticSharedLibName == null) ? false : true;
        }
        return true;
    }

    public static android.content.pm.ApplicationInfo generateApplicationInfo(android.content.pm.PackageParser.Package r1, int i, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState) {
        return generateApplicationInfo(r1, i, frameworkPackageUserState, android.os.UserHandle.getCallingUserId());
    }

    private static void updateApplicationInfo(android.content.pm.ApplicationInfo applicationInfo, int i, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState) {
        if (!sCompatibilityModeEnabled) {
            applicationInfo.disableCompatibilityMode();
        }
        if (frameworkPackageUserState.isInstalled()) {
            applicationInfo.flags |= 8388608;
        } else {
            applicationInfo.flags &= -8388609;
        }
        if (frameworkPackageUserState.isSuspended()) {
            applicationInfo.flags |= 1073741824;
        } else {
            applicationInfo.flags &= -1073741825;
        }
        if (frameworkPackageUserState.isInstantApp()) {
            applicationInfo.privateFlags |= 128;
        } else {
            applicationInfo.privateFlags &= android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        }
        if (frameworkPackageUserState.isVirtualPreload()) {
            applicationInfo.privateFlags |= 65536;
        } else {
            applicationInfo.privateFlags &= -65537;
        }
        if (frameworkPackageUserState.isHidden()) {
            applicationInfo.privateFlags |= 1;
        } else {
            applicationInfo.privateFlags &= -2;
        }
        if (frameworkPackageUserState.getEnabledState() == 1) {
            applicationInfo.enabled = true;
        } else if (frameworkPackageUserState.getEnabledState() == 4) {
            applicationInfo.enabled = (i & 32768) != 0;
        } else if (frameworkPackageUserState.getEnabledState() == 2 || frameworkPackageUserState.getEnabledState() == 3) {
            applicationInfo.enabled = false;
        }
        applicationInfo.enabledSetting = frameworkPackageUserState.getEnabledState();
        if (applicationInfo.category == -1) {
            applicationInfo.category = android.content.pm.FallbackCategoryProvider.getFallbackCategory(applicationInfo.packageName);
        }
        applicationInfo.seInfoUser = getSeinfoUser(frameworkPackageUserState);
        android.content.pm.overlay.OverlayPaths allOverlayPaths = frameworkPackageUserState.getAllOverlayPaths();
        if (allOverlayPaths != null) {
            applicationInfo.resourceDirs = (java.lang.String[]) allOverlayPaths.getResourceDirs().toArray(new java.lang.String[0]);
            applicationInfo.overlayPaths = (java.lang.String[]) allOverlayPaths.getOverlayPaths().toArray(new java.lang.String[0]);
        }
        applicationInfo.icon = (!sUseRoundIcon || applicationInfo.roundIconRes == 0) ? applicationInfo.iconRes : applicationInfo.roundIconRes;
    }

    public static android.content.pm.ApplicationInfo generateApplicationInfo(android.content.pm.PackageParser.Package r2, int i, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, int i2) {
        if (r2 == null || !checkUseInstalledOrHidden(i, frameworkPackageUserState, r2.applicationInfo) || !r2.isMatch(i)) {
            return null;
        }
        if (!copyNeeded(i, r2, frameworkPackageUserState, null, i2) && ((32768 & i) == 0 || frameworkPackageUserState.getEnabledState() != 4)) {
            updateApplicationInfo(r2.applicationInfo, i, frameworkPackageUserState);
            return r2.applicationInfo;
        }
        android.content.pm.ApplicationInfo applicationInfo = new android.content.pm.ApplicationInfo(r2.applicationInfo);
        applicationInfo.initForUser(i2);
        if ((i & 128) != 0) {
            applicationInfo.metaData = r2.mAppMetaData;
        }
        if ((i & 1024) != 0) {
            applicationInfo.sharedLibraryFiles = r2.usesLibraryFiles;
            applicationInfo.sharedLibraryInfos = r2.usesLibraryInfos;
        }
        if (frameworkPackageUserState.isStopped()) {
            applicationInfo.flags |= 2097152;
        } else {
            applicationInfo.flags &= -2097153;
        }
        updateApplicationInfo(applicationInfo, i, frameworkPackageUserState);
        return applicationInfo;
    }

    public static android.content.pm.ApplicationInfo generateApplicationInfo(android.content.pm.ApplicationInfo applicationInfo, int i, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, int i2) {
        if (applicationInfo == null || !checkUseInstalledOrHidden(i, frameworkPackageUserState, applicationInfo)) {
            return null;
        }
        android.content.pm.ApplicationInfo applicationInfo2 = new android.content.pm.ApplicationInfo(applicationInfo);
        applicationInfo2.initForUser(i2);
        if (frameworkPackageUserState.isStopped()) {
            applicationInfo2.flags |= 2097152;
        } else {
            applicationInfo2.flags &= -2097153;
        }
        updateApplicationInfo(applicationInfo2, i, frameworkPackageUserState);
        return applicationInfo2;
    }

    public static final android.content.pm.PermissionInfo generatePermissionInfo(android.content.pm.PackageParser.Permission permission, int i) {
        if (permission == null) {
            return null;
        }
        if ((i & 128) == 0) {
            return permission.info;
        }
        android.content.pm.PermissionInfo permissionInfo = new android.content.pm.PermissionInfo(permission.info);
        permissionInfo.metaData = permission.metaData;
        return permissionInfo;
    }

    public static final android.content.pm.PermissionGroupInfo generatePermissionGroupInfo(android.content.pm.PackageParser.PermissionGroup permissionGroup, int i) {
        if (permissionGroup == null) {
            return null;
        }
        if ((i & 128) == 0) {
            return permissionGroup.info;
        }
        android.content.pm.PermissionGroupInfo permissionGroupInfo = new android.content.pm.PermissionGroupInfo(permissionGroup.info);
        permissionGroupInfo.metaData = permissionGroup.metaData;
        return permissionGroupInfo;
    }

    public static final class Activity extends android.content.pm.PackageParser.Component<android.content.pm.PackageParser.ActivityIntentInfo> implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageParser.Activity>() { // from class: android.content.pm.PackageParser.Activity.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.Activity createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.PackageParser.Activity(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.Activity[] newArray(int i) {
                return new android.content.pm.PackageParser.Activity[i];
            }
        };
        public final android.content.pm.ActivityInfo info;
        private boolean mHasMaxAspectRatio;
        private boolean mHasMinAspectRatio;

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasMaxAspectRatio() {
            return this.mHasMaxAspectRatio;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasMinAspectRatio() {
            return this.mHasMinAspectRatio;
        }

        Activity(android.content.pm.PackageParser.Package r3, java.lang.String str, android.content.pm.ActivityInfo activityInfo) {
            super(r3, new java.util.ArrayList(0), str);
            this.info = activityInfo;
            this.info.applicationInfo = r3.applicationInfo;
        }

        public Activity(android.content.pm.PackageParser.ParseComponentArgs parseComponentArgs, android.content.pm.ActivityInfo activityInfo) {
            super(parseComponentArgs, (android.content.pm.ComponentInfo) activityInfo);
            this.info = activityInfo;
            this.info.applicationInfo = parseComponentArgs.owner.applicationInfo;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(java.lang.String str) {
            super.setPackageName(str);
            this.info.packageName = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMaxAspectRatio(float f) {
            if (this.info.resizeMode == 2 || this.info.resizeMode == 1) {
                return;
            }
            if (f < 1.0f && f != 0.0f) {
                return;
            }
            this.info.setMaxAspectRatio(f);
            this.mHasMaxAspectRatio = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setMinAspectRatio(float f) {
            if (this.info.resizeMode == 2 || this.info.resizeMode == 1) {
                return;
            }
            if (f < 1.0f && f != 0.0f) {
                return;
            }
            this.info.setMinAspectRatio(f);
            this.mHasMinAspectRatio = true;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("Activity{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.info, i | 2);
            parcel.writeBoolean(this.mHasMaxAspectRatio);
            parcel.writeBoolean(this.mHasMinAspectRatio);
        }

        private Activity(android.os.Parcel parcel) {
            super(parcel);
            this.info = (android.content.pm.ActivityInfo) parcel.readParcelable(java.lang.Object.class.getClassLoader(), android.content.pm.ActivityInfo.class);
            this.mHasMaxAspectRatio = parcel.readBoolean();
            this.mHasMinAspectRatio = parcel.readBoolean();
            java.util.Iterator it = this.intents.iterator();
            while (it.hasNext()) {
                android.content.pm.PackageParser.ActivityIntentInfo activityIntentInfo = (android.content.pm.PackageParser.ActivityIntentInfo) it.next();
                activityIntentInfo.activity = this;
                this.order = java.lang.Math.max(activityIntentInfo.getOrder(), this.order);
            }
            if (this.info.permission != null) {
                this.info.permission = this.info.permission.intern();
            }
        }
    }

    public static final android.content.pm.ActivityInfo generateActivityInfo(android.content.pm.PackageParser.Activity activity, int i, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, int i2) {
        return generateActivityInfo(activity, i, frameworkPackageUserState, i2, null);
    }

    private static android.content.pm.ActivityInfo generateActivityInfo(android.content.pm.PackageParser.Activity activity, int i, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, int i2, android.content.pm.ApplicationInfo applicationInfo) {
        if (activity == null || !checkUseInstalledOrHidden(i, frameworkPackageUserState, activity.owner.applicationInfo)) {
            return null;
        }
        if (!copyNeeded(i, activity.owner, frameworkPackageUserState, activity.metaData, i2)) {
            updateApplicationInfo(activity.info.applicationInfo, i, frameworkPackageUserState);
            return activity.info;
        }
        android.content.pm.ActivityInfo activityInfo = new android.content.pm.ActivityInfo(activity.info);
        activityInfo.metaData = activity.metaData;
        if (applicationInfo == null) {
            applicationInfo = generateApplicationInfo(activity.owner, i, frameworkPackageUserState, i2);
        }
        activityInfo.applicationInfo = applicationInfo;
        return activityInfo;
    }

    public static final android.content.pm.ActivityInfo generateActivityInfo(android.content.pm.ActivityInfo activityInfo, int i, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, int i2) {
        if (activityInfo == null || !checkUseInstalledOrHidden(i, frameworkPackageUserState, activityInfo.applicationInfo)) {
            return null;
        }
        android.content.pm.ActivityInfo activityInfo2 = new android.content.pm.ActivityInfo(activityInfo);
        activityInfo2.applicationInfo = generateApplicationInfo(activityInfo2.applicationInfo, i, frameworkPackageUserState, i2);
        return activityInfo2;
    }

    public static final class Service extends android.content.pm.PackageParser.Component<android.content.pm.PackageParser.ServiceIntentInfo> implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageParser.Service>() { // from class: android.content.pm.PackageParser.Service.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.Service createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.PackageParser.Service(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.Service[] newArray(int i) {
                return new android.content.pm.PackageParser.Service[i];
            }
        };
        public final android.content.pm.ServiceInfo info;

        public Service(android.content.pm.PackageParser.ParseComponentArgs parseComponentArgs, android.content.pm.ServiceInfo serviceInfo) {
            super(parseComponentArgs, (android.content.pm.ComponentInfo) serviceInfo);
            this.info = serviceInfo;
            this.info.applicationInfo = parseComponentArgs.owner.applicationInfo;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(java.lang.String str) {
            super.setPackageName(str);
            this.info.packageName = str;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("Service{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.info, i | 2);
        }

        private Service(android.os.Parcel parcel) {
            super(parcel);
            this.info = (android.content.pm.ServiceInfo) parcel.readParcelable(java.lang.Object.class.getClassLoader(), android.content.pm.ServiceInfo.class);
            java.util.Iterator it = this.intents.iterator();
            while (it.hasNext()) {
                android.content.pm.PackageParser.ServiceIntentInfo serviceIntentInfo = (android.content.pm.PackageParser.ServiceIntentInfo) it.next();
                serviceIntentInfo.service = this;
                this.order = java.lang.Math.max(serviceIntentInfo.getOrder(), this.order);
            }
            if (this.info.permission != null) {
                this.info.permission = this.info.permission.intern();
            }
        }
    }

    public static final android.content.pm.ServiceInfo generateServiceInfo(android.content.pm.PackageParser.Service service, int i, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, int i2) {
        return generateServiceInfo(service, i, frameworkPackageUserState, i2, null);
    }

    private static android.content.pm.ServiceInfo generateServiceInfo(android.content.pm.PackageParser.Service service, int i, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, int i2, android.content.pm.ApplicationInfo applicationInfo) {
        if (service == null || !checkUseInstalledOrHidden(i, frameworkPackageUserState, service.owner.applicationInfo)) {
            return null;
        }
        if (!copyNeeded(i, service.owner, frameworkPackageUserState, service.metaData, i2)) {
            updateApplicationInfo(service.info.applicationInfo, i, frameworkPackageUserState);
            return service.info;
        }
        android.content.pm.ServiceInfo serviceInfo = new android.content.pm.ServiceInfo(service.info);
        serviceInfo.metaData = service.metaData;
        if (applicationInfo == null) {
            applicationInfo = generateApplicationInfo(service.owner, i, frameworkPackageUserState, i2);
        }
        serviceInfo.applicationInfo = applicationInfo;
        return serviceInfo;
    }

    public static final class Provider extends android.content.pm.PackageParser.Component<android.content.pm.PackageParser.ProviderIntentInfo> implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageParser.Provider>() { // from class: android.content.pm.PackageParser.Provider.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.Provider createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.PackageParser.Provider(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.Provider[] newArray(int i) {
                return new android.content.pm.PackageParser.Provider[i];
            }
        };
        public final android.content.pm.ProviderInfo info;
        public boolean syncable;

        public Provider(android.content.pm.PackageParser.ParseComponentArgs parseComponentArgs, android.content.pm.ProviderInfo providerInfo) {
            super(parseComponentArgs, (android.content.pm.ComponentInfo) providerInfo);
            this.info = providerInfo;
            this.info.applicationInfo = parseComponentArgs.owner.applicationInfo;
            this.syncable = false;
        }

        public Provider(android.content.pm.PackageParser.Provider provider) {
            super(provider);
            this.info = provider.info;
            this.syncable = provider.syncable;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(java.lang.String str) {
            super.setPackageName(str);
            this.info.packageName = str;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("Provider{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.info, i | 2);
            parcel.writeInt(this.syncable ? 1 : 0);
        }

        private Provider(android.os.Parcel parcel) {
            super(parcel);
            this.info = (android.content.pm.ProviderInfo) parcel.readParcelable(java.lang.Object.class.getClassLoader(), android.content.pm.ProviderInfo.class);
            this.syncable = parcel.readInt() == 1;
            java.util.Iterator it = this.intents.iterator();
            while (it.hasNext()) {
                ((android.content.pm.PackageParser.ProviderIntentInfo) it.next()).provider = this;
            }
            if (this.info.readPermission != null) {
                this.info.readPermission = this.info.readPermission.intern();
            }
            if (this.info.writePermission != null) {
                this.info.writePermission = this.info.writePermission.intern();
            }
            if (this.info.authority != null) {
                this.info.authority = this.info.authority.intern();
            }
        }
    }

    public static final android.content.pm.ProviderInfo generateProviderInfo(android.content.pm.PackageParser.Provider provider, int i, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, int i2) {
        return generateProviderInfo(provider, i, frameworkPackageUserState, i2, null);
    }

    private static android.content.pm.ProviderInfo generateProviderInfo(android.content.pm.PackageParser.Provider provider, int i, android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, int i2, android.content.pm.ApplicationInfo applicationInfo) {
        if (provider == null || !checkUseInstalledOrHidden(i, frameworkPackageUserState, provider.owner.applicationInfo)) {
            return null;
        }
        if (!copyNeeded(i, provider.owner, frameworkPackageUserState, provider.metaData, i2) && ((i & 2048) != 0 || provider.info.uriPermissionPatterns == null)) {
            updateApplicationInfo(provider.info.applicationInfo, i, frameworkPackageUserState);
            return provider.info;
        }
        android.content.pm.ProviderInfo providerInfo = new android.content.pm.ProviderInfo(provider.info);
        providerInfo.metaData = provider.metaData;
        if ((i & 2048) == 0) {
            providerInfo.uriPermissionPatterns = null;
        }
        if (applicationInfo == null) {
            applicationInfo = generateApplicationInfo(provider.owner, i, frameworkPackageUserState, i2);
        }
        providerInfo.applicationInfo = applicationInfo;
        return providerInfo;
    }

    public static final class Instrumentation extends android.content.pm.PackageParser.Component<android.content.pm.PackageParser.IntentInfo> implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator<android.content.pm.PackageParser.Instrumentation>() { // from class: android.content.pm.PackageParser.Instrumentation.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.Instrumentation createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.PackageParser.Instrumentation(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.PackageParser.Instrumentation[] newArray(int i) {
                return new android.content.pm.PackageParser.Instrumentation[i];
            }
        };
        public final android.content.pm.InstrumentationInfo info;

        public Instrumentation(android.content.pm.PackageParser.ParsePackageItemArgs parsePackageItemArgs, android.content.pm.InstrumentationInfo instrumentationInfo) {
            super(parsePackageItemArgs, instrumentationInfo);
            this.info = instrumentationInfo;
        }

        @Override // android.content.pm.PackageParser.Component
        public void setPackageName(java.lang.String str) {
            super.setPackageName(str);
            this.info.packageName = str;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("Instrumentation{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(' ');
            appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.content.pm.PackageParser.Component, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.info, i);
        }

        private Instrumentation(android.os.Parcel parcel) {
            super(parcel);
            this.info = (android.content.pm.InstrumentationInfo) parcel.readParcelable(java.lang.Object.class.getClassLoader(), android.content.pm.InstrumentationInfo.class);
            if (this.info.targetPackage != null) {
                this.info.targetPackage = this.info.targetPackage.intern();
            }
            if (this.info.targetProcesses != null) {
                this.info.targetProcesses = this.info.targetProcesses.intern();
            }
        }
    }

    public static final android.content.pm.InstrumentationInfo generateInstrumentationInfo(android.content.pm.PackageParser.Instrumentation instrumentation, int i) {
        if (instrumentation == null) {
            return null;
        }
        if ((i & 128) == 0) {
            return instrumentation.info;
        }
        android.content.pm.InstrumentationInfo instrumentationInfo = new android.content.pm.InstrumentationInfo(instrumentation.info);
        instrumentationInfo.metaData = instrumentation.metaData;
        return instrumentationInfo;
    }

    public static abstract class IntentInfo extends android.content.IntentFilter {
        public int banner;
        public boolean hasDefault;
        public int icon;
        public int labelRes;
        public int logo;
        public java.lang.CharSequence nonLocalizedLabel;
        public int preferred;

        protected IntentInfo() {
        }

        protected IntentInfo(android.os.Parcel parcel) {
            super(parcel);
            this.hasDefault = parcel.readInt() == 1;
            this.labelRes = parcel.readInt();
            this.nonLocalizedLabel = parcel.readCharSequence();
            this.icon = parcel.readInt();
            this.logo = parcel.readInt();
            this.banner = parcel.readInt();
            this.preferred = parcel.readInt();
        }

        public void writeIntentInfoToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.hasDefault ? 1 : 0);
            parcel.writeInt(this.labelRes);
            parcel.writeCharSequence(this.nonLocalizedLabel);
            parcel.writeInt(this.icon);
            parcel.writeInt(this.logo);
            parcel.writeInt(this.banner);
            parcel.writeInt(this.preferred);
        }
    }

    public static final class ActivityIntentInfo extends android.content.pm.PackageParser.IntentInfo {
        public android.content.pm.PackageParser.Activity activity;

        public ActivityIntentInfo(android.content.pm.PackageParser.Activity activity) {
            this.activity = activity;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("ActivityIntentInfo{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(' ');
            this.activity.appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        public ActivityIntentInfo(android.os.Parcel parcel) {
            super(parcel);
        }
    }

    public static final class ServiceIntentInfo extends android.content.pm.PackageParser.IntentInfo {
        public android.content.pm.PackageParser.Service service;

        public ServiceIntentInfo(android.content.pm.PackageParser.Service service) {
            this.service = service;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("ServiceIntentInfo{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(' ');
            this.service.appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        public ServiceIntentInfo(android.os.Parcel parcel) {
            super(parcel);
        }
    }

    public static final class ProviderIntentInfo extends android.content.pm.PackageParser.IntentInfo {
        public android.content.pm.PackageParser.Provider provider;

        public ProviderIntentInfo(android.content.pm.PackageParser.Provider provider) {
            this.provider = provider;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            sb.append("ProviderIntentInfo{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(' ');
            this.provider.appendComponentShortName(sb);
            sb.append('}');
            return sb.toString();
        }

        public ProviderIntentInfo(android.os.Parcel parcel) {
            super(parcel);
        }
    }

    public static void setCompatibilityModeEnabled(boolean z) {
        sCompatibilityModeEnabled = z;
    }

    public static void readConfigUseRoundIcon(android.content.res.Resources resources) {
        if (resources != null) {
            sUseRoundIcon = resources.getBoolean(com.android.internal.R.bool.config_useRoundIcon);
            return;
        }
        try {
            android.content.pm.ApplicationInfo applicationInfo = android.app.ActivityThread.getPackageManager().getApplicationInfo("android", 0L, android.os.UserHandle.myUserId());
            android.content.res.Resources system = android.content.res.Resources.getSystem();
            sUseRoundIcon = android.app.ResourcesManager.getInstance().getResources(null, null, null, applicationInfo.resourceDirs, applicationInfo.overlayPaths, applicationInfo.sharedLibraryFiles, null, null, system.getCompatibilityInfo(), system.getClassLoader(), null).getBoolean(com.android.internal.R.bool.config_useRoundIcon);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static class PackageParserException extends java.lang.Exception {
        public final int error;

        public PackageParserException(int i, java.lang.String str) {
            super(str);
            this.error = i;
        }

        public PackageParserException(int i, java.lang.String str, java.lang.Throwable th) {
            super(str, th);
            this.error = i;
        }
    }

    @java.lang.Deprecated
    private static abstract class SplitDependencyLoader<E extends java.lang.Exception> {
        private final android.util.SparseArray<int[]> mDependencies;

        protected abstract void constructSplit(int i, int[] iArr, int i2) throws java.lang.Exception;

        protected abstract boolean isSplitCached(int i);

        protected SplitDependencyLoader(android.util.SparseArray<int[]> sparseArray) {
            this.mDependencies = sparseArray;
        }

        protected void loadDependenciesForSplit(int i) throws java.lang.Exception {
            if (isSplitCached(i)) {
                return;
            }
            if (i == 0) {
                constructSplit(0, collectConfigSplitIndices(0), -1);
                return;
            }
            android.util.IntArray intArray = new android.util.IntArray();
            intArray.add(i);
            while (true) {
                int[] iArr = this.mDependencies.get(i);
                if (iArr != null && iArr.length > 0) {
                    i = iArr[0];
                } else {
                    i = -1;
                }
                if (i < 0 || isSplitCached(i)) {
                    break;
                } else {
                    intArray.add(i);
                }
            }
            int size = intArray.size() - 1;
            while (size >= 0) {
                int i2 = intArray.get(size);
                constructSplit(i2, collectConfigSplitIndices(i2), i);
                size--;
                i = i2;
            }
        }

        private int[] collectConfigSplitIndices(int i) {
            int[] iArr = this.mDependencies.get(i);
            if (iArr == null || iArr.length <= 1) {
                return libcore.util.EmptyArray.INT;
            }
            return java.util.Arrays.copyOfRange(iArr, 1, iArr.length);
        }

        public static class IllegalDependencyException extends java.lang.Exception {
            private IllegalDependencyException(java.lang.String str) {
                super(str);
            }
        }

        private static int[] append(int[] iArr, int i) {
            if (iArr == null) {
                return new int[]{i};
            }
            int[] copyOf = java.util.Arrays.copyOf(iArr, iArr.length + 1);
            copyOf[iArr.length] = i;
            return copyOf;
        }

        public static android.util.SparseArray<int[]> createDependenciesFromPackage(android.content.pm.PackageParser.PackageLite packageLite) throws android.content.pm.PackageParser.SplitDependencyLoader.IllegalDependencyException {
            int i;
            int i2;
            android.util.SparseArray<int[]> sparseArray = new android.util.SparseArray<>();
            sparseArray.put(0, new int[]{-1});
            int i3 = 0;
            while (true) {
                if (i3 < packageLite.splitNames.length) {
                    if (packageLite.isFeatureSplits[i3]) {
                        java.lang.String str = packageLite.usesSplitNames[i3];
                        if (str != null) {
                            int binarySearch = java.util.Arrays.binarySearch(packageLite.splitNames, str);
                            if (binarySearch < 0) {
                                throw new android.content.pm.PackageParser.SplitDependencyLoader.IllegalDependencyException("Split '" + packageLite.splitNames[i3] + "' requires split '" + str + "', which is missing.");
                            }
                            i2 = binarySearch + 1;
                        } else {
                            i2 = 0;
                        }
                        sparseArray.put(i3 + 1, new int[]{i2});
                    }
                    i3++;
                } else {
                    int length = packageLite.splitNames.length;
                    for (int i4 = 0; i4 < length; i4++) {
                        if (!packageLite.isFeatureSplits[i4]) {
                            java.lang.String str2 = packageLite.configForSplit[i4];
                            if (str2 != null) {
                                int binarySearch2 = java.util.Arrays.binarySearch(packageLite.splitNames, str2);
                                if (binarySearch2 < 0) {
                                    throw new android.content.pm.PackageParser.SplitDependencyLoader.IllegalDependencyException("Split '" + packageLite.splitNames[i4] + "' targets split '" + str2 + "', which is missing.");
                                }
                                if (!packageLite.isFeatureSplits[binarySearch2]) {
                                    throw new android.content.pm.PackageParser.SplitDependencyLoader.IllegalDependencyException("Split '" + packageLite.splitNames[i4] + "' declares itself as configuration split for a non-feature split '" + packageLite.splitNames[binarySearch2] + "'");
                                }
                                i = binarySearch2 + 1;
                            } else {
                                i = 0;
                            }
                            sparseArray.put(i, append(sparseArray.get(i), i4 + 1));
                        }
                    }
                    java.util.BitSet bitSet = new java.util.BitSet();
                    int size = sparseArray.size();
                    for (int i5 = 0; i5 < size; i5++) {
                        int keyAt = sparseArray.keyAt(i5);
                        bitSet.clear();
                        while (keyAt != -1) {
                            if (bitSet.get(keyAt)) {
                                throw new android.content.pm.PackageParser.SplitDependencyLoader.IllegalDependencyException("Cycle detected in split dependencies.");
                            }
                            bitSet.set(keyAt);
                            int[] iArr = sparseArray.get(keyAt);
                            keyAt = iArr != null ? iArr[0] : -1;
                        }
                    }
                    return sparseArray;
                }
            }
        }
    }

    @java.lang.Deprecated
    private static class DefaultSplitAssetLoader implements android.content.pm.PackageParser.SplitAssetLoader {
        private android.content.res.ApkAssets mBaseApkAssets;
        private final java.lang.String mBaseCodePath;
        private android.content.res.AssetManager mCachedAssetManager;
        private final int mFlags;
        private final java.lang.String[] mSplitCodePaths;

        DefaultSplitAssetLoader(android.content.pm.PackageParser.PackageLite packageLite, int i) {
            this.mBaseCodePath = packageLite.baseCodePath;
            this.mSplitCodePaths = packageLite.splitCodePaths;
            this.mFlags = i;
        }

        private static android.content.res.ApkAssets loadApkAssets(java.lang.String str, int i) throws android.content.pm.PackageParser.PackageParserException {
            if ((i & 1) != 0 && !android.content.pm.PackageParser.isApkPath(str)) {
                throw new android.content.pm.PackageParser.PackageParserException(-100, "Invalid package file: " + str);
            }
            try {
                return android.content.res.ApkAssets.loadFromPath(str);
            } catch (java.io.IOException e) {
                throw new android.content.pm.PackageParser.PackageParserException(-2, "Failed to load APK at path " + str, e);
            }
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public android.content.res.AssetManager getBaseAssetManager() throws android.content.pm.PackageParser.PackageParserException {
            if (this.mCachedAssetManager == null) {
                int i = 1;
                android.content.res.ApkAssets[] apkAssetsArr = new android.content.res.ApkAssets[(this.mSplitCodePaths != null ? this.mSplitCodePaths.length : 0) + 1];
                this.mBaseApkAssets = loadApkAssets(this.mBaseCodePath, this.mFlags);
                apkAssetsArr[0] = this.mBaseApkAssets;
                if (!com.android.internal.util.ArrayUtils.isEmpty(this.mSplitCodePaths)) {
                    java.lang.String[] strArr = this.mSplitCodePaths;
                    int length = strArr.length;
                    int i2 = 0;
                    while (i2 < length) {
                        apkAssetsArr[i] = loadApkAssets(strArr[i2], this.mFlags);
                        i2++;
                        i++;
                    }
                }
                android.content.res.AssetManager assetManager = new android.content.res.AssetManager();
                assetManager.setConfiguration(0, 0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, android.os.Build.VERSION.RESOURCES_SDK_INT);
                assetManager.setApkAssets(apkAssetsArr, false);
                this.mCachedAssetManager = assetManager;
                return this.mCachedAssetManager;
            }
            return this.mCachedAssetManager;
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public android.content.res.AssetManager getSplitAssetManager(int i) throws android.content.pm.PackageParser.PackageParserException {
            return getBaseAssetManager();
        }

        @Override // java.lang.AutoCloseable
        public void close() throws java.lang.Exception {
            libcore.io.IoUtils.closeQuietly(this.mCachedAssetManager);
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public android.content.res.ApkAssets getBaseApkAssets() {
            return this.mBaseApkAssets;
        }
    }

    @java.lang.Deprecated
    private static class SplitAssetDependencyLoader extends android.content.pm.PackageParser.SplitDependencyLoader<android.content.pm.PackageParser.PackageParserException> implements android.content.pm.PackageParser.SplitAssetLoader {
        private final android.content.res.AssetManager[] mCachedAssetManagers;
        private final android.content.res.ApkAssets[][] mCachedSplitApks;
        private final int mFlags;
        private final java.lang.String[] mSplitPaths;

        SplitAssetDependencyLoader(android.content.pm.PackageParser.PackageLite packageLite, android.util.SparseArray<int[]> sparseArray, int i) {
            super(sparseArray);
            this.mSplitPaths = new java.lang.String[packageLite.splitCodePaths.length + 1];
            this.mSplitPaths[0] = packageLite.baseCodePath;
            java.lang.System.arraycopy(packageLite.splitCodePaths, 0, this.mSplitPaths, 1, packageLite.splitCodePaths.length);
            this.mFlags = i;
            this.mCachedSplitApks = new android.content.res.ApkAssets[this.mSplitPaths.length][];
            this.mCachedAssetManagers = new android.content.res.AssetManager[this.mSplitPaths.length];
        }

        @Override // android.content.pm.PackageParser.SplitDependencyLoader
        protected boolean isSplitCached(int i) {
            return this.mCachedAssetManagers[i] != null;
        }

        private static android.content.res.ApkAssets loadApkAssets(java.lang.String str, int i) throws android.content.pm.PackageParser.PackageParserException {
            if ((i & 1) != 0 && !android.content.pm.PackageParser.isApkPath(str)) {
                throw new android.content.pm.PackageParser.PackageParserException(-100, "Invalid package file: " + str);
            }
            try {
                return android.content.res.ApkAssets.loadFromPath(str);
            } catch (java.io.IOException e) {
                throw new android.content.pm.PackageParser.PackageParserException(-2, "Failed to load APK at path " + str, e);
            }
        }

        private static android.content.res.AssetManager createAssetManagerWithAssets(android.content.res.ApkAssets[] apkAssetsArr) {
            android.content.res.AssetManager assetManager = new android.content.res.AssetManager();
            assetManager.setConfiguration(0, 0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, android.os.Build.VERSION.RESOURCES_SDK_INT);
            assetManager.setApkAssets(apkAssetsArr, false);
            return assetManager;
        }

        @Override // android.content.pm.PackageParser.SplitDependencyLoader
        protected void constructSplit(int i, int[] iArr, int i2) throws android.content.pm.PackageParser.PackageParserException {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            if (i2 >= 0) {
                java.util.Collections.addAll(arrayList, this.mCachedSplitApks[i2]);
            }
            arrayList.add(loadApkAssets(this.mSplitPaths[i], this.mFlags));
            for (int i3 : iArr) {
                arrayList.add(loadApkAssets(this.mSplitPaths[i3], this.mFlags));
            }
            this.mCachedSplitApks[i] = (android.content.res.ApkAssets[]) arrayList.toArray(new android.content.res.ApkAssets[arrayList.size()]);
            this.mCachedAssetManagers[i] = createAssetManagerWithAssets(this.mCachedSplitApks[i]);
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public android.content.res.AssetManager getBaseAssetManager() throws android.content.pm.PackageParser.PackageParserException {
            loadDependenciesForSplit(0);
            return this.mCachedAssetManagers[0];
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public android.content.res.AssetManager getSplitAssetManager(int i) throws android.content.pm.PackageParser.PackageParserException {
            int i2 = i + 1;
            loadDependenciesForSplit(i2);
            return this.mCachedAssetManagers[i2];
        }

        @Override // java.lang.AutoCloseable
        public void close() throws java.lang.Exception {
            for (android.content.res.AssetManager assetManager : this.mCachedAssetManagers) {
                libcore.io.IoUtils.closeQuietly(assetManager);
            }
        }

        @Override // android.content.pm.PackageParser.SplitAssetLoader
        public android.content.res.ApkAssets getBaseApkAssets() {
            return this.mCachedSplitApks[0][0];
        }
    }

    public static boolean isMatch(android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, android.content.pm.ComponentInfo componentInfo, long j) {
        return isMatch(frameworkPackageUserState, componentInfo.applicationInfo.isSystemApp(), componentInfo.applicationInfo.enabled, componentInfo.enabled, componentInfo.directBootAware, componentInfo.name, j);
    }

    public static boolean isMatch(android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, boolean z, boolean z2, android.content.pm.ComponentInfo componentInfo, long j) {
        return isMatch(frameworkPackageUserState, z, z2, componentInfo.isEnabled(), componentInfo.directBootAware, componentInfo.name, j);
    }

    public static boolean isMatch(android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, boolean z, boolean z2, boolean z3, boolean z4, java.lang.String str, long j) {
        boolean z5 = true;
        boolean z6 = (4202496 & j) != 0;
        if (!isAvailable(frameworkPackageUserState, j) && (!z || !z6)) {
            return reportIfDebug(false, j);
        }
        if (!isEnabled(frameworkPackageUserState, z2, z3, str, j)) {
            return reportIfDebug(false, j);
        }
        if ((1048576 & j) != 0 && !z) {
            return reportIfDebug(false, j);
        }
        boolean z7 = ((262144 & j) == 0 || z4) ? false : true;
        boolean z8 = (524288 & j) != 0 && z4;
        if (!z7 && !z8) {
            z5 = false;
        }
        return reportIfDebug(z5, j);
    }

    public static boolean isAvailable(android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, long j) {
        boolean z = (4194304 & j) != 0;
        boolean z2 = (j & 8192) != 0;
        if (z) {
            return true;
        }
        return frameworkPackageUserState.isInstalled() && (!frameworkPackageUserState.isHidden() || z2);
    }

    public static boolean reportIfDebug(boolean z, long j) {
        return z;
    }

    public static boolean isEnabled(android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, android.content.pm.ComponentInfo componentInfo, long j) {
        return isEnabled(frameworkPackageUserState, componentInfo.applicationInfo.enabled, componentInfo.enabled, componentInfo.name, j);
    }

    public static boolean isEnabled(android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, boolean z, android.content.pm.ComponentInfo componentInfo, long j) {
        return isEnabled(frameworkPackageUserState, z, componentInfo.isEnabled(), componentInfo.name, j);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0020 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0027 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean isEnabled(android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState, boolean z, boolean z2, java.lang.String str, long j) {
        if ((512 & j) == 0) {
            switch (frameworkPackageUserState.getEnabledState()) {
                case 0:
                    if (!z) {
                        return false;
                    }
                    if (frameworkPackageUserState.isComponentEnabled(str)) {
                        return true;
                    }
                    if (frameworkPackageUserState.isComponentDisabled(str)) {
                        return false;
                    }
                    return z2;
                case 1:
                default:
                    if (frameworkPackageUserState.isComponentEnabled(str)) {
                    }
                    break;
                case 2:
                case 3:
                    return false;
                case 4:
                    if ((j & 32768) == 0) {
                        return false;
                    }
                    if (!z) {
                    }
                    if (frameworkPackageUserState.isComponentEnabled(str)) {
                    }
                    break;
            }
        } else {
            return true;
        }
    }

    public static void writeKeySetMapping(android.os.Parcel parcel, java.util.Map<java.lang.String, android.util.ArraySet<java.security.PublicKey>> map) {
        if (map == null) {
            parcel.writeInt(-1);
            return;
        }
        parcel.writeInt(map.size());
        for (java.lang.String str : map.keySet()) {
            parcel.writeString(str);
            android.util.ArraySet<java.security.PublicKey> arraySet = map.get(str);
            if (arraySet == null) {
                parcel.writeInt(-1);
            } else {
                int size = arraySet.size();
                parcel.writeInt(size);
                for (int i = 0; i < size; i++) {
                    parcel.writeSerializable(arraySet.valueAt(i));
                }
            }
        }
    }

    public static android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.security.PublicKey>> readKeySetMapping(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        if (readInt == -1) {
            return null;
        }
        android.util.ArrayMap<java.lang.String, android.util.ArraySet<java.security.PublicKey>> arrayMap = new android.util.ArrayMap<>();
        for (int i = 0; i < readInt; i++) {
            java.lang.String readString = parcel.readString();
            int readInt2 = parcel.readInt();
            if (readInt2 == -1) {
                arrayMap.put(readString, null);
            } else {
                android.util.ArraySet<java.security.PublicKey> arraySet = new android.util.ArraySet<>(readInt2);
                for (int i2 = 0; i2 < readInt2; i2++) {
                    arraySet.add((java.security.PublicKey) parcel.readSerializable(java.security.PublicKey.class.getClassLoader(), java.security.PublicKey.class));
                }
                arrayMap.put(readString, arraySet);
            }
        }
        return arrayMap;
    }

    public static java.lang.String getSeinfoUser(android.content.pm.pkg.FrameworkPackageUserState frameworkPackageUserState) {
        if (frameworkPackageUserState.isInstantApp()) {
            return ":ephemeralapp:complete";
        }
        return com.android.internal.pm.pkg.SEInfoUtil.COMPLETE_STR;
    }
}
