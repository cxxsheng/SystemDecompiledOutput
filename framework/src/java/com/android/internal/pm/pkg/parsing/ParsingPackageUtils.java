package com.android.internal.pm.pkg.parsing;

/* loaded from: classes5.dex */
public class ParsingPackageUtils {
    public static final java.lang.String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";
    public static final java.lang.String APP_METADATA_FILE_NAME = "app.metadata";
    public static final float ASPECT_RATIO_NOT_SET = -1.0f;
    public static final boolean DEBUG_BACKUP = false;
    public static final boolean DEBUG_JAR = false;
    public static final float DEFAULT_PRE_O_MAX_ASPECT_RATIO = 1.86f;
    public static final java.lang.String METADATA_ACTIVITY_LAUNCH_MODE = "android.activity.launch_mode";
    public static final java.lang.String METADATA_ACTIVITY_WINDOW_LAYOUT_AFFINITY = "android.activity_window_layout_affinity";
    public static final java.lang.String METADATA_CAN_DISPLAY_ON_REMOTE_DEVICES = "android.can_display_on_remote_devices";
    public static final java.lang.String METADATA_MAX_ASPECT_RATIO = "android.max_aspect";
    public static final java.lang.String METADATA_SUPPORTS_SIZE_CHANGES = "android.supports_size_changes";
    public static final java.lang.String MNT_EXPAND = "/mnt/expand/";
    public static final int PARSE_APK_IN_APEX = 512;
    public static final int PARSE_CHATTY = Integer.MIN_VALUE;
    public static final int PARSE_COLLECT_CERTIFICATES = 32;
    public static final int PARSE_DEFAULT_INSTALL_LOCATION = -1;
    public static final int PARSE_DEFAULT_TARGET_SANDBOX = 1;
    public static final int PARSE_ENFORCE_CODE = 64;
    public static final int PARSE_EXTERNAL_STORAGE = 8;
    public static final int PARSE_IGNORE_OVERLAY_REQUIRED_SYSTEM_PROPERTY = 128;
    public static final int PARSE_IGNORE_PROCESSES = 2;
    public static final int PARSE_IS_SYSTEM_DIR = 16;
    public static final int PARSE_MUST_BE_APK = 1;
    public static final boolean RIGID_PARSER = false;
    private static final java.lang.String TAG = "PackageParsing";
    public static final java.lang.String TAG_ADOPT_PERMISSIONS = "adopt-permissions";
    public static final java.lang.String TAG_APPLICATION = "application";
    public static final java.lang.String TAG_ATTRIBUTION = "attribution";
    public static final java.lang.String TAG_COMPATIBLE_SCREENS = "compatible-screens";
    public static final java.lang.String TAG_EAT_COMMENT = "eat-comment";
    public static final java.lang.String TAG_FEATURE_GROUP = "feature-group";
    public static final java.lang.String TAG_INSTALL_CONSTRAINTS = "install-constraints";
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
    public static final java.lang.String TAG_RECEIVER = "receiver";
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
    private final com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback mCallback;
    private final android.util.DisplayMetrics mDisplayMetrics;
    private final java.lang.String[] mSeparateProcesses;
    private final java.util.List<android.permission.PermissionManager.SplitPermissionInfo> mSplitPermissionInfos;
    public static final int SDK_VERSION = android.os.Build.VERSION.SDK_INT;
    public static final java.lang.String[] SDK_CODENAMES = android.os.Build.VERSION.ACTIVE_CODENAMES;
    public static boolean sCompatibilityModeEnabled = true;
    public static boolean sUseRoundIcon = false;

    public interface Callback {
        java.util.Set<java.lang.String> getHiddenApiWhitelistedApps();

        java.util.Set<java.lang.String> getInstallConstraintsAllowlist();

        boolean hasFeature(java.lang.String str);

        com.android.internal.pm.pkg.parsing.ParsingPackage startParsingPackage(java.lang.String str, java.lang.String str2, java.lang.String str3, android.content.res.TypedArray typedArray, boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ParseFlags {
    }

    public static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.parsing.pkg.ParsedPackage> parseDefault(android.content.pm.parsing.result.ParseInput parseInput, java.io.File file, int i, java.util.List<android.permission.PermissionManager.SplitPermissionInfo> list, boolean z, com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback callback) {
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parsePackage = new com.android.internal.pm.pkg.parsing.ParsingPackageUtils(null, null, list, callback).parsePackage(parseInput, file, i);
        if (parsePackage.isError()) {
            return parseInput.error(parsePackage);
        }
        com.android.internal.pm.parsing.pkg.ParsedPackage hideAsParsed = parsePackage.getResult().hideAsParsed();
        if (z) {
            android.content.pm.parsing.result.ParseResult<android.content.pm.SigningDetails> signingDetails = getSigningDetails(parseInput, hideAsParsed, false);
            if (signingDetails.isError()) {
                return parseInput.error(signingDetails);
            }
            hideAsParsed.setSigningDetails(signingDetails.getResult());
        }
        return parseInput.success(hideAsParsed);
    }

    public ParsingPackageUtils(java.lang.String[] strArr, android.util.DisplayMetrics displayMetrics, java.util.List<android.permission.PermissionManager.SplitPermissionInfo> list, com.android.internal.pm.pkg.parsing.ParsingPackageUtils.Callback callback) {
        this.mSeparateProcesses = strArr;
        this.mDisplayMetrics = displayMetrics;
        this.mSplitPermissionInfos = list;
        this.mCallback = callback;
    }

    public android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parsePackage(android.content.pm.parsing.result.ParseInput parseInput, java.io.File file, int i) {
        if (file.isDirectory()) {
            return parseClusterPackage(parseInput, file, i);
        }
        return parseMonolithicPackage(parseInput, file, i);
    }

    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseClusterPackage(android.content.pm.parsing.result.ParseInput parseInput, java.io.File file, int i) {
        android.util.SparseArray<int[]> sparseArray;
        com.android.internal.pm.split.SplitAssetLoader defaultSplitAssetLoader;
        android.content.pm.parsing.result.ParseResult<?> parseClusterPackageLite = android.content.pm.parsing.ApkLiteParseUtils.parseClusterPackageLite(parseInput, file, (i & 512) != 0 ? 512 : 0);
        if (parseClusterPackageLite.isError()) {
            return parseInput.error(parseClusterPackageLite);
        }
        android.content.pm.parsing.PackageLite result = parseClusterPackageLite.getResult();
        if (!result.isIsolatedSplits() || com.android.internal.util.ArrayUtils.isEmpty(result.getSplitNames())) {
            sparseArray = null;
            defaultSplitAssetLoader = new com.android.internal.pm.split.DefaultSplitAssetLoader(result, i);
        } else {
            try {
                sparseArray = com.android.internal.pm.split.SplitAssetDependencyLoader.createDependenciesFromPackage(result);
                defaultSplitAssetLoader = new com.android.internal.pm.split.SplitAssetDependencyLoader(result, sparseArray, i);
            } catch (android.content.pm.split.SplitDependencyLoader.IllegalDependencyException e) {
                return parseInput.error(-101, e.getMessage());
            }
        }
        try {
            android.content.pm.parsing.result.ParseResult<?> parseBaseApk = parseBaseApk(parseInput, new java.io.File(result.getBaseApkPath()), result.getPath(), defaultSplitAssetLoader, i, result.isIsSdkLibrary() && android.content.pm.Flags.disallowSdkLibsToBeApps());
            if (parseBaseApk.isError()) {
                return parseInput.error(parseBaseApk);
            }
            com.android.internal.pm.pkg.parsing.ParsingPackage result2 = parseBaseApk.getResult();
            if (!com.android.internal.util.ArrayUtils.isEmpty(result.getSplitNames())) {
                result2.asSplit(result.getSplitNames(), result.getSplitApkPaths(), result.getSplitRevisionCodes(), sparseArray);
                int length = result.getSplitNames().length;
                for (int i2 = 0; i2 < length; i2++) {
                    android.content.pm.parsing.result.ParseResult<?> parseSplitApk = parseSplitApk(parseInput, result2, i2, defaultSplitAssetLoader.getSplitAssetManager(i2), i);
                    if (parseSplitApk.isError()) {
                        return parseInput.error(parseSplitApk);
                    }
                }
            }
            result2.set32BitAbiPreferred(result.isUse32bitAbi());
            return parseInput.success(result2);
        } catch (java.lang.IllegalArgumentException e2) {
            return parseInput.error(e2.getCause() instanceof java.io.IOException ? -2 : -100, e2.getMessage(), e2);
        } finally {
            libcore.io.IoUtils.closeQuietly(defaultSplitAssetLoader);
        }
    }

    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseMonolithicPackage(android.content.pm.parsing.result.ParseInput parseInput, java.io.File file, int i) {
        android.content.pm.parsing.result.ParseResult<android.content.pm.parsing.PackageLite> parseMonolithicPackageLite = android.content.pm.parsing.ApkLiteParseUtils.parseMonolithicPackageLite(parseInput, file, i);
        if (parseMonolithicPackageLite.isError()) {
            return parseInput.error(parseMonolithicPackageLite);
        }
        android.content.pm.parsing.PackageLite result = parseMonolithicPackageLite.getResult();
        com.android.internal.pm.split.DefaultSplitAssetLoader defaultSplitAssetLoader = new com.android.internal.pm.split.DefaultSplitAssetLoader(result, i);
        try {
            android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseBaseApk = parseBaseApk(parseInput, file, file.getCanonicalPath(), defaultSplitAssetLoader, i, result.isIsSdkLibrary() && android.content.pm.Flags.disallowSdkLibsToBeApps());
            return parseBaseApk.isError() ? parseInput.error(parseBaseApk) : parseInput.success(parseBaseApk.getResult().set32BitAbiPreferred(result.isUse32bitAbi()));
        } catch (java.io.IOException e) {
            return parseInput.error(-102, "Failed to get path: " + file, e);
        } finally {
            libcore.io.IoUtils.closeQuietly(defaultSplitAssetLoader);
        }
    }

    public android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parsePackageFromPackageLite(android.content.pm.parsing.result.ParseInput parseInput, android.content.pm.parsing.PackageLite packageLite, int i) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        java.lang.String volumeUuid = getVolumeUuid(packageLite.getPath());
        java.lang.String packageName = packageLite.getPackageName();
        com.android.internal.pm.pkg.parsing.ParsingPackage startParsingPackage = this.mCallback.startParsingPackage(packageName, packageLite.getBaseApkPath(), packageLite.getPath(), null, packageLite.isCoreApp());
        int targetSdk = packageLite.getTargetSdk();
        startParsingPackage.setVersionCode(packageLite.getVersionCode());
        startParsingPackage.setVersionCodeMajor(packageLite.getVersionCodeMajor());
        startParsingPackage.setBaseRevisionCode(packageLite.getBaseRevisionCode());
        startParsingPackage.setVersionName(null);
        startParsingPackage.setCompileSdkVersion(0);
        startParsingPackage.setCompileSdkVersionCodeName(null);
        startParsingPackage.setIsolatedSplitLoading(false);
        startParsingPackage.setTargetSdkVersion(targetSdk);
        boolean z5 = true;
        com.android.internal.pm.pkg.parsing.ParsingPackage targetSandboxVersion = startParsingPackage.setInstallLocation(packageLite.getInstallLocation()).setTargetSandboxVersion(1);
        if ((i & 8) == 0) {
            z = false;
        } else {
            z = true;
        }
        targetSandboxVersion.setExternalStorage(z);
        android.content.pm.ArchivedPackageParcel archivedPackage = packageLite.getArchivedPackage();
        if (archivedPackage == null) {
            return parseInput.error(-102, "archivePackage is missing");
        }
        com.android.internal.pm.pkg.parsing.ParsingPackage extractNativeLibrariesRequested = startParsingPackage.setBackupAllowed(true).setClearUserDataAllowed(true).setClearUserDataOnFailedRestoreAllowed(true).setAllowNativeHeapPointerTagging(true).setEnabled(true).setExtractNativeLibrariesRequested(true);
        if (targetSdk < 29) {
            z2 = false;
        } else {
            z2 = true;
        }
        com.android.internal.pm.pkg.parsing.ParsingPackage allowAudioPlaybackCapture = extractNativeLibrariesRequested.setAllowAudioPlaybackCapture(z2);
        if (targetSdk < 14) {
            z3 = false;
        } else {
            z3 = true;
        }
        com.android.internal.pm.pkg.parsing.ParsingPackage hardwareAccelerated = allowAudioPlaybackCapture.setHardwareAccelerated(z3);
        java.lang.String str = archivedPackage.requestLegacyExternalStorage;
        if (targetSdk >= 29) {
            z4 = false;
        } else {
            z4 = true;
        }
        com.android.internal.pm.pkg.parsing.ParsingPackage requestLegacyExternalStorage = hardwareAccelerated.setRequestLegacyExternalStorage(com.android.internal.util.XmlUtils.convertValueToBoolean(str, z4));
        if (targetSdk >= 28) {
            z5 = false;
        }
        requestLegacyExternalStorage.setCleartextTrafficAllowed(z5).setDefaultToDeviceProtectedStorage(com.android.internal.util.XmlUtils.convertValueToBoolean(archivedPackage.defaultToDeviceProtectedStorage, false)).setUserDataFragile(com.android.internal.util.XmlUtils.convertValueToBoolean(archivedPackage.userDataFragile, false)).setCategory(-1).setMaxAspectRatio(0.0f).setMinAspectRatio(0.0f);
        startParsingPackage.setDeclaredHavingCode(false);
        android.content.pm.parsing.result.ParseResult<java.lang.String> buildTaskAffinityName = com.android.internal.pm.pkg.component.ComponentParseUtils.buildTaskAffinityName(packageName, packageName, null, parseInput);
        if (buildTaskAffinityName.isError()) {
            return parseInput.error(buildTaskAffinityName);
        }
        startParsingPackage.setTaskAffinity(buildTaskAffinityName.getResult());
        android.content.pm.parsing.result.ParseResult<java.lang.String> buildProcessName = com.android.internal.pm.pkg.component.ComponentParseUtils.buildProcessName(packageName, null, null, i, this.mSeparateProcesses, parseInput);
        if (buildProcessName.isError()) {
            return parseInput.error(buildProcessName);
        }
        startParsingPackage.setProcessName(buildProcessName.getResult());
        startParsingPackage.setGwpAsanMode(-1);
        startParsingPackage.setMemtagMode(-1);
        afterParseBaseApplication(startParsingPackage);
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> validateBaseApkTags = validateBaseApkTags(parseInput, startParsingPackage);
        if (validateBaseApkTags.isError()) {
            return validateBaseApkTags;
        }
        startParsingPackage.setVolumeUuid(volumeUuid);
        if ((i & 32) != 0) {
            startParsingPackage.setSigningDetails(packageLite.getSigningDetails());
        } else {
            startParsingPackage.setSigningDetails(android.content.pm.SigningDetails.UNKNOWN);
        }
        return parseInput.success(startParsingPackage.set32BitAbiPreferred(packageLite.isUse32bitAbi()));
    }

    private static java.lang.String getVolumeUuid(java.lang.String str) {
        if (!str.startsWith("/mnt/expand/")) {
            return null;
        }
        return str.substring("/mnt/expand/".length(), str.indexOf(47, "/mnt/expand/".length()));
    }

    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseBaseApk(android.content.pm.parsing.result.ParseInput parseInput, java.io.File file, java.lang.String str, com.android.internal.pm.split.SplitAssetLoader splitAssetLoader, int i, boolean z) {
        boolean z2;
        java.lang.String absolutePath = file.getAbsolutePath();
        java.lang.String volumeUuid = getVolumeUuid(absolutePath);
        try {
            android.content.res.AssetManager baseAssetManager = splitAssetLoader.getBaseAssetManager();
            int findCookieForPath = baseAssetManager.findCookieForPath(absolutePath);
            if (findCookieForPath == 0) {
                return parseInput.error(-101, "Failed adding asset path: " + absolutePath);
            }
            try {
                android.content.res.XmlResourceParser openXmlResourceParser = baseAssetManager.openXmlResourceParser(findCookieForPath, "AndroidManifest.xml");
                try {
                    android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseBaseApk = parseBaseApk(parseInput, absolutePath, str, new android.content.res.Resources(baseAssetManager, this.mDisplayMetrics, null), openXmlResourceParser, i, z);
                    if (parseBaseApk.isError()) {
                        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> error = parseInput.error(parseBaseApk.getErrorCode(), absolutePath + " (at " + openXmlResourceParser.getPositionDescription() + "): " + parseBaseApk.getErrorMessage());
                        if (openXmlResourceParser != null) {
                            openXmlResourceParser.close();
                        }
                        return error;
                    }
                    com.android.internal.pm.pkg.parsing.ParsingPackage result = parseBaseApk.getResult();
                    if (baseAssetManager.containsAllocatedTable()) {
                        android.content.pm.parsing.result.ParseResult<?> deferError = parseInput.deferError("Targeting R+ (version 30 and above) requires the resources.arsc of installed APKs to be stored uncompressed and aligned on a 4-byte boundary", android.content.pm.parsing.result.ParseInput.DeferredError.RESOURCES_ARSC_COMPRESSED);
                        if (deferError.isError()) {
                            android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> error2 = parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_RESOURCES_ARSC_COMPRESSED, deferError.getErrorMessage());
                            if (openXmlResourceParser != null) {
                                openXmlResourceParser.close();
                            }
                            return error2;
                        }
                    }
                    try {
                        z2 = splitAssetLoader.getBaseApkAssets().definesOverlayable();
                    } catch (java.io.IOException e) {
                        z2 = false;
                    }
                    if (z2) {
                        android.util.SparseArray<java.lang.String> assignedPackageIdentifiers = baseAssetManager.getAssignedPackageIdentifiers();
                        int size = assignedPackageIdentifiers.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            java.util.Map<java.lang.String, java.lang.String> overlayableMap = baseAssetManager.getOverlayableMap(assignedPackageIdentifiers.valueAt(i2));
                            if (overlayableMap != null && !overlayableMap.isEmpty()) {
                                for (java.lang.String str2 : overlayableMap.keySet()) {
                                    result.addOverlayable(str2, overlayableMap.get(str2));
                                }
                            }
                        }
                    }
                    result.setVolumeUuid(volumeUuid);
                    if ((i & 32) != 0) {
                        android.content.pm.parsing.result.ParseResult<?> signingDetails = getSigningDetails(parseInput, result, false);
                        if (signingDetails.isError()) {
                            android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> error3 = parseInput.error(signingDetails);
                            if (openXmlResourceParser != null) {
                                openXmlResourceParser.close();
                            }
                            return error3;
                        }
                        result.setSigningDetails(signingDetails.getResult());
                    } else {
                        result.setSigningDetails(android.content.pm.SigningDetails.UNKNOWN);
                    }
                    if (android.content.pm.Flags.aslInApkAppMetadataSource()) {
                        try {
                            java.io.InputStream open = baseAssetManager.open(APP_METADATA_FILE_NAME);
                            try {
                                result.setAppMetadataFileInApk(true);
                                if (open != null) {
                                    open.close();
                                }
                            } finally {
                            }
                        } catch (java.lang.Exception e2) {
                        }
                    }
                    android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> success = parseInput.success(result);
                    if (openXmlResourceParser != null) {
                        openXmlResourceParser.close();
                    }
                    return success;
                } finally {
                }
            } catch (java.lang.Exception e3) {
                return parseInput.error(-102, "Failed to read manifest from " + absolutePath, e3);
            }
        } catch (java.lang.IllegalArgumentException e4) {
            return parseInput.error(e4.getCause() instanceof java.io.IOException ? -2 : -100, e4.getMessage(), e4);
        }
    }

    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseSplitApk(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, int i, android.content.res.AssetManager assetManager, int i2) {
        java.lang.String str = parsingPackage.getSplitCodePaths()[i];
        int findCookieForPath = assetManager.findCookieForPath(str);
        if (findCookieForPath == 0) {
            return parseInput.error(-101, "Failed adding asset path: " + str);
        }
        try {
            android.content.res.XmlResourceParser openXmlResourceParser = assetManager.openXmlResourceParser(findCookieForPath, "AndroidManifest.xml");
            try {
                android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseSplitApk = parseSplitApk(parseInput, parsingPackage, new android.content.res.Resources(assetManager, this.mDisplayMetrics, null), openXmlResourceParser, i2, i);
                if (parseSplitApk.isError()) {
                    android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> error = parseInput.error(parseSplitApk.getErrorCode(), str + " (at " + openXmlResourceParser.getPositionDescription() + "): " + parseSplitApk.getErrorMessage());
                    if (openXmlResourceParser != null) {
                        openXmlResourceParser.close();
                    }
                    return error;
                }
                if (openXmlResourceParser != null) {
                    openXmlResourceParser.close();
                }
                return parseSplitApk;
            } finally {
            }
        } catch (java.lang.Exception e) {
            return parseInput.error(-102, "Failed to read manifest from " + str, e);
        }
    }

    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseBaseApk(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, java.lang.String str2, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.pm.parsing.result.ParseResult<android.util.Pair<java.lang.String, java.lang.String>> parsePackageSplitNames = android.content.pm.parsing.ApkLiteParseUtils.parsePackageSplitNames(parseInput, xmlResourceParser);
        if (parsePackageSplitNames.isError()) {
            return parseInput.error(parsePackageSplitNames);
        }
        android.util.Pair<java.lang.String, java.lang.String> result = parsePackageSplitNames.getResult();
        java.lang.String str3 = result.first;
        java.lang.String str4 = result.second;
        if (!android.text.TextUtils.isEmpty(str4)) {
            return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME, "Expected base APK, but found split " + str4);
        }
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifest);
        try {
            com.android.internal.pm.pkg.parsing.ParsingPackage startParsingPackage = this.mCallback.startParsingPackage(str3, str, str2, obtainAttributes, xmlResourceParser.getAttributeBooleanValue(null, "coreApp", false));
            android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseBaseApkTags = parseBaseApkTags(parseInput, startParsingPackage, obtainAttributes, resources, xmlResourceParser, i, z);
            if (parseBaseApkTags.isError()) {
                return parseBaseApkTags;
            }
            return parseInput.success(startParsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseSplitApk(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, int i2) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> unknownTag;
        android.content.pm.parsing.result.ParseResult<android.util.Pair<java.lang.String, java.lang.String>> parsePackageSplitNames = android.content.pm.parsing.ApkLiteParseUtils.parsePackageSplitNames(parseInput, xmlResourceParser);
        if (parsePackageSplitNames.isError()) {
            return parseInput.error(parsePackageSplitNames);
        }
        int depth = xmlResourceParser.getDepth();
        boolean z = false;
        while (true) {
            int next = xmlResourceParser.next();
            if (next != 1) {
                if (depth + 1 >= xmlResourceParser.getDepth() && next == 2) {
                    if ("application".equals(xmlResourceParser.getName())) {
                        if (z) {
                            android.util.Slog.w("PackageParsing", "<manifest> has more than one <application>");
                            unknownTag = parseInput.success(null);
                        } else {
                            unknownTag = parseSplitApplication(parseInput, parsingPackage, resources, xmlResourceParser, i, i2);
                            z = true;
                        }
                    } else {
                        unknownTag = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag("<manifest>", parsingPackage, xmlResourceParser, parseInput);
                    }
                    if (unknownTag.isError()) {
                        return parseInput.error(unknownTag);
                    }
                }
            } else {
                if (!z) {
                    android.content.pm.parsing.result.ParseResult<?> deferError = parseInput.deferError("<manifest> does not contain an <application>", android.content.pm.parsing.result.ParseInput.DeferredError.MISSING_APP_TAG);
                    if (deferError.isError()) {
                        return parseInput.error(deferError);
                    }
                }
                return parseInput.success(parsingPackage);
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x009c, code lost:
    
        if (r4.equals("provider") != false) goto L41;
     */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0148  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseSplitApplication(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, int i2) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        boolean z;
        android.content.pm.parsing.result.ParseResult<?> parseActivityOrReceiver;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestApplication);
        try {
            parsingPackage.setSplitHasCode(i2, obtainAttributes.getBoolean(7, true));
            java.lang.String string = obtainAttributes.getString(46);
            if (string != null && !com.android.internal.os.ClassLoaderFactory.isValidClassLoaderName(string)) {
                return parseInput.error("Invalid class loader name: " + string);
            }
            parsingPackage.setSplitClassLoaderName(i2, string);
            obtainAttributes.recycle();
            java.lang.String str = parsingPackage.getSplitNames()[i2];
            int depth = xmlResourceParser.getDepth();
            while (true) {
                int next = xmlResourceParser.next();
                if (next != 1) {
                    char c = 3;
                    if (next != 3 || xmlResourceParser.getDepth() > depth) {
                        if (next == 2) {
                            java.lang.String name = xmlResourceParser.getName();
                            switch (name.hashCode()) {
                                case -1655966961:
                                    if (name.equals("activity")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -987494927:
                                    break;
                                case -808719889:
                                    if (name.equals("receiver")) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 790287890:
                                    if (name.equals("activity-alias")) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1984153269:
                                    if (name.equals("service")) {
                                        c = 2;
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
                                    z = true;
                                    parseActivityOrReceiver = com.android.internal.pm.pkg.component.ParsedActivityUtils.parseActivityOrReceiver(this.mSeparateProcesses, parsingPackage, resources, xmlResourceParser, i, sUseRoundIcon, str, parseInput);
                                    if (parseActivityOrReceiver.isSuccess()) {
                                        com.android.internal.pm.pkg.component.ParsedActivity parsedActivity = (com.android.internal.pm.pkg.component.ParsedActivity) parseActivityOrReceiver.getResult();
                                        if (z) {
                                            parsingPackage.addActivity(parsedActivity);
                                            break;
                                        } else {
                                            parsingPackage.addReceiver(parsedActivity);
                                            break;
                                        }
                                    }
                                    break;
                                case 1:
                                    z = false;
                                    parseActivityOrReceiver = com.android.internal.pm.pkg.component.ParsedActivityUtils.parseActivityOrReceiver(this.mSeparateProcesses, parsingPackage, resources, xmlResourceParser, i, sUseRoundIcon, str, parseInput);
                                    if (parseActivityOrReceiver.isSuccess()) {
                                    }
                                    break;
                                case 2:
                                    parseActivityOrReceiver = com.android.internal.pm.pkg.component.ParsedServiceUtils.parseService(this.mSeparateProcesses, parsingPackage, resources, xmlResourceParser, i, sUseRoundIcon, str, parseInput);
                                    if (parseActivityOrReceiver.isSuccess()) {
                                        parsingPackage.addService((com.android.internal.pm.pkg.component.ParsedService) parseActivityOrReceiver.getResult());
                                        break;
                                    }
                                    break;
                                case 3:
                                    parseActivityOrReceiver = com.android.internal.pm.pkg.component.ParsedProviderUtils.parseProvider(this.mSeparateProcesses, parsingPackage, resources, xmlResourceParser, i, sUseRoundIcon, str, parseInput);
                                    if (parseActivityOrReceiver.isSuccess()) {
                                        parsingPackage.addProvider((com.android.internal.pm.pkg.component.ParsedProvider) parseActivityOrReceiver.getResult());
                                        break;
                                    }
                                    break;
                                case 4:
                                    parseActivityOrReceiver = com.android.internal.pm.pkg.component.ParsedActivityUtils.parseActivityAlias(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, str, parseInput);
                                    if (parseActivityOrReceiver.isSuccess()) {
                                        parsingPackage.addActivity((com.android.internal.pm.pkg.component.ParsedActivity) parseActivityOrReceiver.getResult());
                                        break;
                                    }
                                    break;
                                default:
                                    parseActivityOrReceiver = parseSplitBaseAppChildTags(parseInput, name, parsingPackage, resources, xmlResourceParser);
                                    break;
                            }
                            if (parseActivityOrReceiver.isError()) {
                                return parseInput.error(parseActivityOrReceiver);
                            }
                        }
                    }
                }
            }
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private android.content.pm.parsing.result.ParseResult parseSplitBaseAppChildTags(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        char c;
        switch (str.hashCode()) {
            case -1608941274:
                if (str.equals("uses-native-library")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1521117785:
                if (str.equals("uses-sdk-library")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1356765254:
                if (str.equals("uses-library")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1115949454:
                if (str.equals("meta-data")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -993141291:
                if (str.equals("property")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 8960125:
                if (str.equals("uses-static-library")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1964930885:
                if (str.equals("uses-package")) {
                    c = 6;
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
                android.content.pm.parsing.result.ParseResult<android.content.pm.PackageManager.Property> parseMetaData = parseMetaData(parsingPackage, null, resources, xmlResourceParser, "<meta-data>", parseInput);
                if (parseMetaData.isSuccess() && parseMetaData.getResult() != null) {
                    parsingPackage.setMetaData(parseMetaData.getResult().toBundle(parsingPackage.getMetaData()));
                }
                return parseMetaData;
            case 1:
                android.content.pm.parsing.result.ParseResult<android.content.pm.PackageManager.Property> parseMetaData2 = parseMetaData(parsingPackage, null, resources, xmlResourceParser, "<property>", parseInput);
                if (parseMetaData2.isSuccess()) {
                    parsingPackage.addProperty(parseMetaData2.getResult());
                }
                return parseMetaData2;
            case 2:
                return parseUsesSdkLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case 3:
                return parseUsesStaticLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case 4:
                return parseUsesLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case 5:
                return parseUsesNativeLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case 6:
                return parseInput.success(null);
            default:
                return com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag("<application>", parsingPackage, xmlResourceParser, parseInput);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x00a6, code lost:
    
        if (r13 != false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00b0, code lost:
    
        if (com.android.internal.util.ArrayUtils.size(r16.getInstrumentations()) != 0) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00b2, code lost:
    
        r0 = r15.deferError("<manifest> does not contain an <application> or <instrumentation>", android.content.pm.parsing.result.ParseInput.DeferredError.MISSING_APP_TAG);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00bf, code lost:
    
        if (r0.isError() == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00c5, code lost:
    
        return r15.error(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00ca, code lost:
    
        return validateBaseApkTags(r15, r16);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseBaseApkTags(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.TypedArray typedArray, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseBaseApkTag;
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseSharedUser = parseSharedUser(parseInput, parsingPackage, typedArray);
        if (parseSharedUser.isError()) {
            return parseSharedUser;
        }
        parsingPackage.setInstallLocation(anInteger(-1, 4, typedArray)).setTargetSandboxVersion(anInteger(1, 7, typedArray)).setExternalStorage((i & 8) != 0).setUpdatableSystem(xmlResourceParser.getAttributeBooleanValue(null, "updatableSystem", true)).setEmergencyInstaller(xmlResourceParser.getAttributeValue(null, "emergencyInstaller"));
        int depth = xmlResourceParser.getDepth();
        boolean z2 = false;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2) {
                java.lang.String name = xmlResourceParser.getName();
                if ("application".equals(name)) {
                    if (z2) {
                        android.util.Slog.w("PackageParsing", "<manifest> has more than one <application>");
                        parseBaseApkTag = parseInput.success(null);
                    } else {
                        parseBaseApkTag = parseBaseApplication(parseInput, parsingPackage, resources, xmlResourceParser, i, z);
                        z2 = true;
                    }
                } else {
                    parseBaseApkTag = parseBaseApkTag(name, parseInput, parsingPackage, resources, xmlResourceParser, i);
                }
                if (parseBaseApkTag.isError()) {
                    return parseInput.error(parseBaseApkTag);
                }
            }
        }
    }

    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> validateBaseApkTags(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage) {
        if (!com.android.internal.pm.pkg.component.ParsedAttributionUtils.isCombinationValid(parsingPackage.getAttributions())) {
            return parseInput.error(-101, "Combination <attribution> tags are not valid");
        }
        if (com.android.internal.pm.pkg.component.ParsedPermissionUtils.declareDuplicatePermission(parsingPackage)) {
            return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "Found duplicate permission with a different attribute value.");
        }
        convertCompatPermissions(parsingPackage);
        convertSplitPermissions(parsingPackage);
        if (parsingPackage.getTargetSdkVersion() < 4 || (!parsingPackage.isSmallScreensSupported() && !parsingPackage.isNormalScreensSupported() && !parsingPackage.isLargeScreensSupported() && !parsingPackage.isExtraLargeScreensSupported() && !parsingPackage.isResizeable() && !parsingPackage.isAnyDensity())) {
            adjustPackageToBeUnresizeableAndUnpipable(parsingPackage);
        }
        return parseInput.success(parsingPackage);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private android.content.pm.parsing.result.ParseResult parseBaseApkTag(java.lang.String str, android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        char c;
        switch (str.hashCode()) {
            case -1773650763:
                if (str.equals("uses-configuration")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1667688228:
                if (str.equals("permission-tree")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1108197302:
                if (str.equals("original-package")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -1091287984:
                if (str.equals("overlay")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -998269702:
                if (str.equals("restrict-update")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case -979207434:
                if (str.equals("feature")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -517618225:
                if (str.equals("permission")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -309882753:
                if (str.equals("attribution")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -266709319:
                if (str.equals("uses-sdk")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -170723071:
                if (str.equals("permission-group")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -129269526:
                if (str.equals("eat-comment")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 119109844:
                if (str.equals("uses-gl-texture")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 349565761:
                if (str.equals("supports-input")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 454915839:
                if (str.equals("key-sets")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 544550766:
                if (str.equals("instrumentation")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 599862896:
                if (str.equals("uses-permission")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 632228327:
                if (str.equals("adopt-permissions")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 655087462:
                if (str.equals("queries")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 862539012:
                if (str.equals(TAG_INSTALL_CONSTRAINTS)) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 896788286:
                if (str.equals("supports-screens")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1343942321:
                if (str.equals("uses-permission-sdk-23")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 1439495522:
                if (str.equals("protected-broadcast")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 1682371816:
                if (str.equals("feature-group")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 1705921021:
                if (str.equals("uses-permission-sdk-m")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1792785909:
                if (str.equals("uses-feature")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 1818228622:
                if (str.equals("compatible-screens")) {
                    c = 20;
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
                return parseOverlay(parseInput, parsingPackage, resources, xmlResourceParser);
            case 1:
                return parseKeySets(parseInput, parsingPackage, resources, xmlResourceParser);
            case 2:
            case 3:
                return parseAttribution(parseInput, parsingPackage, resources, xmlResourceParser);
            case 4:
                return parsePermissionGroup(parseInput, parsingPackage, resources, xmlResourceParser);
            case 5:
                return parsePermission(parseInput, parsingPackage, resources, xmlResourceParser);
            case 6:
                return parsePermissionTree(parseInput, parsingPackage, resources, xmlResourceParser);
            case 7:
            case '\b':
            case '\t':
                return parseUsesPermission(parseInput, parsingPackage, resources, xmlResourceParser);
            case '\n':
                return parseUsesConfiguration(parseInput, parsingPackage, resources, xmlResourceParser);
            case 11:
                return parseUsesFeature(parseInput, parsingPackage, resources, xmlResourceParser);
            case '\f':
                return parseFeatureGroup(parseInput, parsingPackage, resources, xmlResourceParser);
            case '\r':
                return parseUsesSdk(parseInput, parsingPackage, resources, xmlResourceParser, i);
            case 14:
                return parseSupportScreens(parseInput, parsingPackage, resources, xmlResourceParser);
            case 15:
                return parseProtectedBroadcast(parseInput, parsingPackage, resources, xmlResourceParser);
            case 16:
                return parseInstrumentation(parseInput, parsingPackage, resources, xmlResourceParser);
            case 17:
                return parseOriginalPackage(parseInput, parsingPackage, resources, xmlResourceParser);
            case 18:
                return parseAdoptPermissions(parseInput, parsingPackage, resources, xmlResourceParser);
            case 19:
            case 20:
            case 21:
            case 22:
                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                return parseInput.success(parsingPackage);
            case 23:
                return parseRestrictUpdateHash(i, parseInput, parsingPackage, resources, xmlResourceParser);
            case 24:
                return parseInstallConstraints(parseInput, parsingPackage, resources, xmlResourceParser, this.mCallback.getInstallConstraintsAllowlist());
            case 25:
                return parseQueries(parseInput, parsingPackage, resources, xmlResourceParser);
            default:
                return com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag("<manifest>", parsingPackage, xmlResourceParser, parseInput);
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseSharedUser(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.TypedArray typedArray) {
        boolean z = false;
        java.lang.String nonConfigString = nonConfigString(0, 0, typedArray);
        if (android.text.TextUtils.isEmpty(nonConfigString)) {
            return parseInput.success(parsingPackage);
        }
        if (!"android".equals(parsingPackage.getPackageName())) {
            android.content.pm.parsing.result.ParseResult validateName = android.content.pm.parsing.FrameworkParsingPackageUtils.validateName(parseInput, nonConfigString, true, true);
            if (validateName.isError()) {
                return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID, "<manifest> specifies bad sharedUserId name \"" + nonConfigString + "\": " + validateName.getErrorMessage());
            }
        }
        int anInteger = anInteger(0, 13, typedArray);
        if (anInteger != 0 && anInteger < android.os.Build.VERSION.RESOURCES_SDK_INT) {
            z = true;
        }
        return parseInput.success(parsingPackage.setLeavingSharedUser(z).setSharedUserId(nonConfigString.intern()).setSharedUserLabelResourceId(resId(3, typedArray)));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x01de, code lost:
    
        if (r5.keySet().removeAll(r7.keySet()) == false) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x01fb, code lost:
    
        return r16.error("Package" + r2 + " AndroidManifest.xml 'key-set' and 'public-key' names must be distinct.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x01fc, code lost:
    
        r3 = r7.entrySet().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0208, code lost:
    
        if (r3.hasNext() == false) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x020a, code lost:
    
        r7 = (java.util.Map.Entry) r3.next();
        r9 = (java.lang.String) r7.getKey();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0222, code lost:
    
        if (((android.util.ArraySet) r7.getValue()).size() != 0) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x024b, code lost:
    
        if (r8.contains(r9) == false) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0270, code lost:
    
        r7 = ((android.util.ArraySet) r7.getValue()).iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x027e, code lost:
    
        if (r7.hasNext() == false) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0280, code lost:
    
        r17.addKeySet(r9, (java.security.PublicKey) r5.get((java.lang.String) r7.next()));
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x024d, code lost:
    
        android.util.Slog.w("PackageParsing", "Package" + r2 + " AndroidManifest.xml 'key-set' " + r9 + " contained improper 'public-key' tags. Not including in package's defined key-sets.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0224, code lost:
    
        android.util.Slog.w("PackageParsing", "Package" + r2 + " AndroidManifest.xml 'key-set' " + r9 + " has no valid associated 'public-key'. Not including in package's defined key-sets.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x029e, code lost:
    
        if (r17.getKeySetMapping().keySet().containsAll(r6) == false) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x02a0, code lost:
    
        r17.setUpgradeKeySets(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x02a7, code lost:
    
        return r16.success(r17);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x02c3, code lost:
    
        return r16.error("Package" + r2 + " AndroidManifest.xml does not define all 'upgrade-key-set's .");
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x01cc, code lost:
    
        r2 = r17.getPackageName();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseKeySets(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        char c;
        int depth = xmlResourceParser.getDepth();
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        android.util.ArraySet arraySet = new android.util.ArraySet();
        android.util.ArrayMap arrayMap2 = new android.util.ArrayMap();
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        int i = -1;
        java.lang.String str = null;
        while (true) {
            int next = xmlResourceParser.next();
            if (next != 1 && (next != 3 || xmlResourceParser.getDepth() > depth)) {
                if (next != 3) {
                    java.lang.String name = xmlResourceParser.getName();
                    switch (name.hashCode()) {
                        case -1369233085:
                            if (name.equals("upgrade-key-set")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case -816609292:
                            if (name.equals("key-set")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1903323387:
                            if (name.equals("public-key")) {
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
                            if (str != null) {
                                return parseInput.error("Improperly nested 'key-set' tag at " + xmlResourceParser.getPositionDescription());
                            }
                            try {
                                str = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestKeySet).getNonResourceString(0);
                                arrayMap2.put(str, new android.util.ArraySet());
                                i = xmlResourceParser.getDepth();
                                break;
                            } finally {
                            }
                        case 1:
                            if (str == null) {
                                return parseInput.error("Improperly nested 'key-set' tag at " + xmlResourceParser.getPositionDescription());
                            }
                            android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestPublicKey);
                            try {
                                java.lang.String nonResString = nonResString(0, obtainAttributes);
                                java.lang.String nonResString2 = nonResString(1, obtainAttributes);
                                if (nonResString2 != null || arrayMap.get(nonResString) != null) {
                                    if (nonResString2 != null) {
                                        java.security.PublicKey parsePublicKey = android.content.pm.parsing.FrameworkParsingPackageUtils.parsePublicKey(nonResString2);
                                        if (parsePublicKey == null) {
                                            android.util.Slog.w("PackageParsing", "No recognized valid key in 'public-key' tag at " + xmlResourceParser.getPositionDescription() + " key-set " + str + " will not be added to the package's defined key-sets.");
                                            arraySet2.add(str);
                                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                            break;
                                        } else {
                                            if (arrayMap.get(nonResString) != null && !((java.security.PublicKey) arrayMap.get(nonResString)).equals(parsePublicKey)) {
                                                return parseInput.error("Value of 'public-key' " + nonResString + " conflicts with previously defined value at " + xmlResourceParser.getPositionDescription());
                                            }
                                            arrayMap.put(nonResString, parsePublicKey);
                                        }
                                    }
                                    ((android.util.ArraySet) arrayMap2.get(str)).add(nonResString);
                                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                    break;
                                } else {
                                    return parseInput.error("'public-key' " + nonResString + " must define a public-key value on first use at " + xmlResourceParser.getPositionDescription());
                                }
                            } finally {
                            }
                            break;
                        case 2:
                            try {
                                arraySet.add(resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUpgradeKeySet).getNonResourceString(0));
                                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                                break;
                            } finally {
                            }
                        default:
                            android.content.pm.parsing.result.ParseResult<?> unknownTag = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag("<key-sets>", parsingPackage, xmlResourceParser, parseInput);
                            if (!unknownTag.isError()) {
                                break;
                            } else {
                                return parseInput.error(unknownTag);
                            }
                    }
                } else if (xmlResourceParser.getDepth() == i) {
                    i = -1;
                    str = null;
                }
            }
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseAttribution(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedAttribution> parseAttribution = com.android.internal.pm.pkg.component.ParsedAttributionUtils.parseAttribution(resources, xmlResourceParser, parseInput);
        if (parseAttribution.isError()) {
            return parseInput.error(parseAttribution);
        }
        return parseInput.success(parsingPackage.addAttribution(parseAttribution.getResult()));
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parsePermissionGroup(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedPermissionGroup> parsePermissionGroup = com.android.internal.pm.pkg.component.ParsedPermissionUtils.parsePermissionGroup(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput);
        if (parsePermissionGroup.isError()) {
            return parseInput.error(parsePermissionGroup);
        }
        return parseInput.success(parsingPackage.addPermissionGroup(parsePermissionGroup.getResult()));
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parsePermission(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedPermission> parsePermission = com.android.internal.pm.pkg.component.ParsedPermissionUtils.parsePermission(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput);
        if (parsePermission.isError()) {
            return parseInput.error(parsePermission);
        }
        com.android.internal.pm.pkg.component.ParsedPermission result = parsePermission.getResult();
        if (result != null) {
            parsingPackage.addPermission(result);
        }
        return parseInput.success(parsingPackage);
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parsePermissionTree(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedPermission> parsePermissionTree = com.android.internal.pm.pkg.component.ParsedPermissionUtils.parsePermissionTree(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput);
        if (parsePermissionTree.isError()) {
            return parseInput.error(parsePermissionTree);
        }
        return parseInput.success(parsingPackage.addPermission(parsePermissionTree.getResult()));
    }

    private int parseMinOrMaxSdkVersion(android.content.res.TypedArray typedArray, int i, int i2) {
        android.util.TypedValue peekValue = typedArray.peekValue(i);
        if (peekValue != null && peekValue.type >= 16 && peekValue.type <= 31) {
            return peekValue.data;
        }
        return i2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x00c8, code lost:
    
        r3 = r18.success(r19);
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00cc, code lost:
    
        if (r7 != null) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00d2, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00d5, code lost:
    
        if (android.os.Build.VERSION.RESOURCES_SDK_INT < r8) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00d9, code lost:
    
        if (android.os.Build.VERSION.RESOURCES_SDK_INT <= r10) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00df, code lost:
    
        if (r17.mCallback == null) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00e1, code lost:
    
        r6 = r11.size() - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00e7, code lost:
    
        if (r6 < 0) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00f5, code lost:
    
        if (r17.mCallback.hasFeature((java.lang.String) r11.valueAt(r6)) != false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00fc, code lost:
    
        r6 = r6 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00fb, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ff, code lost:
    
        r8 = true;
        r6 = r13.size() - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0105, code lost:
    
        if (r6 < 0) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0113, code lost:
    
        if (r17.mCallback.hasFeature((java.lang.String) r13.valueAt(r6)) == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x011a, code lost:
    
        r6 = r6 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0119, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x011f, code lost:
    
        r0 = r19.getUsesPermissions();
        r6 = r0.size();
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0128, code lost:
    
        if (r9 >= r6) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x012a, code lost:
    
        r10 = r0.get(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0138, code lost:
    
        if (java.util.Objects.equals(r10.getName(), r7) == false) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x01aa, code lost:
    
        r9 = r9 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0142, code lost:
    
        if (r10.getUsesPermissionFlags() == r15) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0176, code lost:
    
        return r18.error("Conflicting uses-permissions flags: " + r7 + " in package: " + r19.getPackageName() + " at: " + r21.getPositionDescription());
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0177, code lost:
    
        android.util.Slog.w("PackageParsing", "Ignoring duplicate uses-permissions/uses-permissions-sdk-m: " + r7 + " in package: " + r19.getPackageName() + " at: " + r21.getPositionDescription());
        r6 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x01af, code lost:
    
        if (r6 != false) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x01b1, code lost:
    
        r19.addUsesPermission(new com.android.internal.pm.pkg.component.ParsedUsesPermissionImpl(r7, r15));
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x01bd, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x01ae, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x011d, code lost:
    
        r8 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x01c2, code lost:
    
        return r3;
     */
    /* JADX WARN: Failed to find 'out' block for switch in B:78:0x0069. Please report as an issue. */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseUsesPermission(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        char c;
        android.content.pm.parsing.result.ParseResult<java.lang.String> parseRequiredFeature;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUsesPermission);
        try {
            java.lang.String nonResourceString = obtainAttributes.getNonResourceString(0);
            int i = 1;
            int parseMinOrMaxSdkVersion = parseMinOrMaxSdkVersion(obtainAttributes, 1, Integer.MIN_VALUE);
            int parseMinOrMaxSdkVersion2 = parseMinOrMaxSdkVersion(obtainAttributes, 2, Integer.MAX_VALUE);
            android.util.ArraySet arraySet = new android.util.ArraySet();
            java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(3, 0);
            if (nonConfigurationString != null) {
                arraySet.add(nonConfigurationString);
            }
            android.util.ArraySet arraySet2 = new android.util.ArraySet();
            java.lang.String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(4, 0);
            if (nonConfigurationString2 != null) {
                arraySet2.add(nonConfigurationString2);
            }
            int i2 = obtainAttributes.getInt(5, 0);
            int depth = xmlResourceParser.getDepth();
            while (true) {
                int next = xmlResourceParser.next();
                if (next != i && (next != 3 || xmlResourceParser.getDepth() > depth)) {
                    if (next == 3) {
                        i = 1;
                    } else if (next == 4) {
                        i = 1;
                    } else {
                        java.lang.String name = xmlResourceParser.getName();
                        switch (name.hashCode()) {
                            case 874138830:
                                if (name.equals("required-not-feature")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1693350600:
                                if (name.equals("required-feature")) {
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
                                parseRequiredFeature = parseRequiredFeature(parseInput, resources, xmlResourceParser);
                                if (parseRequiredFeature.isSuccess()) {
                                    arraySet.add(parseRequiredFeature.getResult());
                                    break;
                                }
                                break;
                            case 1:
                                parseRequiredFeature = parseRequiredNotFeature(parseInput, resources, xmlResourceParser);
                                if (parseRequiredFeature.isSuccess()) {
                                    arraySet2.add(parseRequiredFeature.getResult());
                                    break;
                                }
                                break;
                            default:
                                parseRequiredFeature = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag("<uses-permission>", parsingPackage, xmlResourceParser, parseInput);
                                break;
                        }
                        if (parseRequiredFeature.isError()) {
                            return parseInput.error(parseRequiredFeature);
                        }
                        i = 1;
                    }
                }
            }
        } finally {
            obtainAttributes.recycle();
        }
    }

    private android.content.pm.parsing.result.ParseResult<java.lang.String> parseRequiredFeature(android.content.pm.parsing.result.ParseInput parseInput, android.content.res.Resources resources, android.util.AttributeSet attributeSet) {
        android.content.pm.parsing.result.ParseResult<java.lang.String> success;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.AndroidManifestRequiredFeature);
        try {
            java.lang.String string = obtainAttributes.getString(0);
            if (android.text.TextUtils.isEmpty(string)) {
                success = parseInput.error("Feature name is missing from <required-feature> tag.");
            } else {
                success = parseInput.success(string);
            }
            return success;
        } finally {
            obtainAttributes.recycle();
        }
    }

    private android.content.pm.parsing.result.ParseResult<java.lang.String> parseRequiredNotFeature(android.content.pm.parsing.result.ParseInput parseInput, android.content.res.Resources resources, android.util.AttributeSet attributeSet) {
        android.content.pm.parsing.result.ParseResult<java.lang.String> success;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.AndroidManifestRequiredNotFeature);
        try {
            java.lang.String string = obtainAttributes.getString(0);
            if (android.text.TextUtils.isEmpty(string)) {
                success = parseInput.error("Feature name is missing from <required-not-feature> tag.");
            } else {
                success = parseInput.success(string);
            }
            return success;
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseUsesConfiguration(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) {
        android.content.pm.ConfigurationInfo configurationInfo = new android.content.pm.ConfigurationInfo();
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUsesConfiguration);
        try {
            configurationInfo.reqTouchScreen = obtainAttributes.getInt(0, 0);
            configurationInfo.reqKeyboardType = obtainAttributes.getInt(1, 0);
            if (obtainAttributes.getBoolean(2, false)) {
                configurationInfo.reqInputFeatures = 1 | configurationInfo.reqInputFeatures;
            }
            configurationInfo.reqNavigation = obtainAttributes.getInt(3, 0);
            if (obtainAttributes.getBoolean(4, false)) {
                configurationInfo.reqInputFeatures |= 2;
            }
            parsingPackage.addConfigPreference(configurationInfo);
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseUsesFeature(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) {
        android.content.pm.FeatureInfo parseFeatureInfo = parseFeatureInfo(resources, xmlResourceParser);
        parsingPackage.addReqFeature(parseFeatureInfo);
        if (parseFeatureInfo.name == null) {
            android.content.pm.ConfigurationInfo configurationInfo = new android.content.pm.ConfigurationInfo();
            configurationInfo.reqGlEsVersion = parseFeatureInfo.reqGlEsVersion;
            parsingPackage.addConfigPreference(configurationInfo);
        }
        return parseInput.success(parsingPackage);
    }

    private static android.content.pm.FeatureInfo parseFeatureInfo(android.content.res.Resources resources, android.util.AttributeSet attributeSet) {
        android.content.pm.FeatureInfo featureInfo = new android.content.pm.FeatureInfo();
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.AndroidManifestUsesFeature);
        try {
            featureInfo.name = obtainAttributes.getNonResourceString(0);
            featureInfo.version = obtainAttributes.getInt(3, 0);
            if (featureInfo.name == null) {
                featureInfo.reqGlEsVersion = obtainAttributes.getInt(1, 0);
            }
            if (obtainAttributes.getBoolean(2, true)) {
                featureInfo.flags |= 1;
            }
            return featureInfo;
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseFeatureGroup(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.pm.FeatureGroupInfo featureGroupInfo = new android.content.pm.FeatureGroupInfo();
        int depth = xmlResourceParser.getDepth();
        java.util.ArrayList arrayList = null;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2) {
                java.lang.String name = xmlResourceParser.getName();
                if (name.equals("uses-feature")) {
                    android.content.pm.FeatureInfo parseFeatureInfo = parseFeatureInfo(resources, xmlResourceParser);
                    parseFeatureInfo.flags = 1 | parseFeatureInfo.flags;
                    arrayList = com.android.internal.util.ArrayUtils.add((java.util.ArrayList<android.content.pm.FeatureInfo>) arrayList, parseFeatureInfo);
                } else {
                    android.util.Slog.w("PackageParsing", "Unknown element under <feature-group>: " + name + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
                }
            }
        }
        if (arrayList != null) {
            featureGroupInfo.features = new android.content.pm.FeatureInfo[arrayList.size()];
            featureGroupInfo.features = (android.content.pm.FeatureInfo[]) arrayList.toArray(featureGroupInfo.features);
        }
        parsingPackage.addFeatureGroup(featureGroupInfo);
        return parseInput.success(parsingPackage);
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseUsesSdk(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        boolean z;
        int i2;
        java.lang.String str;
        java.lang.String str2;
        android.content.pm.parsing.result.ParseResult<android.util.SparseIntArray> unknownTag;
        android.util.TypedValue peekValue;
        if (SDK_VERSION > 0) {
            int i3 = 0;
            boolean z2 = (i & 512) != 0;
            android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUsesSdk);
            try {
                android.util.TypedValue peekValue2 = obtainAttributes.peekValue(0);
                android.util.SparseIntArray sparseIntArray = null;
                if (peekValue2 == null) {
                    z = false;
                    i2 = 1;
                    str = null;
                } else if (peekValue2.type != 3 || peekValue2.string == null) {
                    i2 = peekValue2.data;
                    z = true;
                    str = null;
                } else {
                    java.lang.String charSequence = peekValue2.string.toString();
                    z = !android.text.TextUtils.isEmpty(charSequence);
                    str = charSequence;
                    i2 = 1;
                }
                android.util.TypedValue peekValue3 = obtainAttributes.peekValue(1);
                if (peekValue3 == null) {
                    i3 = i2;
                    str2 = str;
                } else if (peekValue3.type != 3 || peekValue3.string == null) {
                    i3 = peekValue3.data;
                    str2 = str;
                    str = null;
                } else {
                    str2 = peekValue3.string.toString();
                    if (z) {
                        str2 = str;
                        str = str2;
                    } else {
                        str = str2;
                    }
                }
                int i4 = (!z2 || (peekValue = obtainAttributes.peekValue(2)) == null) ? Integer.MAX_VALUE : peekValue.data;
                android.content.pm.parsing.result.ParseResult<java.lang.Integer> computeTargetSdkVersion = android.content.pm.parsing.FrameworkParsingPackageUtils.computeTargetSdkVersion(i3, str, SDK_CODENAMES, parseInput, z2);
                if (computeTargetSdkVersion.isError()) {
                    return parseInput.error(computeTargetSdkVersion);
                }
                int intValue = computeTargetSdkVersion.getResult().intValue();
                android.content.pm.parsing.result.ParseResult<?> enableDeferredError = parseInput.enableDeferredError(parsingPackage.getPackageName(), intValue);
                if (enableDeferredError.isError()) {
                    return parseInput.error(enableDeferredError);
                }
                android.content.pm.parsing.result.ParseResult<java.lang.Integer> computeMinSdkVersion = android.content.pm.parsing.FrameworkParsingPackageUtils.computeMinSdkVersion(i2, str2, SDK_VERSION, SDK_CODENAMES, parseInput);
                if (computeMinSdkVersion.isError()) {
                    return parseInput.error(computeMinSdkVersion);
                }
                parsingPackage.setMinSdkVersion(computeMinSdkVersion.getResult().intValue()).setTargetSdkVersion(intValue);
                if (z2) {
                    android.content.pm.parsing.result.ParseResult<java.lang.Integer> computeMaxSdkVersion = android.content.pm.parsing.FrameworkParsingPackageUtils.computeMaxSdkVersion(i4, SDK_VERSION, parseInput);
                    if (computeMaxSdkVersion.isError()) {
                        return parseInput.error(computeMaxSdkVersion);
                    }
                    parsingPackage.setMaxSdkVersion(computeMaxSdkVersion.getResult().intValue());
                }
                int depth = xmlResourceParser.getDepth();
                while (true) {
                    int next = xmlResourceParser.next();
                    if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                        break;
                    }
                    if (next != 3 && next != 4) {
                        if (xmlResourceParser.getName().equals("extension-sdk")) {
                            if (sparseIntArray == null) {
                                sparseIntArray = new android.util.SparseIntArray();
                            }
                            unknownTag = parseExtensionSdk(parseInput, resources, xmlResourceParser, sparseIntArray);
                            com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
                        } else {
                            unknownTag = com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag("<uses-sdk>", parsingPackage, xmlResourceParser, parseInput);
                        }
                        if (unknownTag.isError()) {
                            return parseInput.error(unknownTag);
                        }
                    }
                }
                parsingPackage.setMinExtensionVersions(exactSizedCopyOfSparseArray(sparseIntArray));
            } finally {
                obtainAttributes.recycle();
            }
        }
        return parseInput.success(parsingPackage);
    }

    private static android.util.SparseIntArray exactSizedCopyOfSparseArray(android.util.SparseIntArray sparseIntArray) {
        if (sparseIntArray == null) {
            return null;
        }
        android.util.SparseIntArray sparseIntArray2 = new android.util.SparseIntArray(sparseIntArray.size());
        for (int i = 0; i < sparseIntArray.size(); i++) {
            sparseIntArray2.put(sparseIntArray.keyAt(i), sparseIntArray.valueAt(i));
        }
        return sparseIntArray2;
    }

    private static android.content.pm.parsing.result.ParseResult<android.util.SparseIntArray> parseExtensionSdk(android.content.pm.parsing.result.ParseInput parseInput, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.util.SparseIntArray sparseIntArray) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestExtensionSdk);
        try {
            int i = obtainAttributes.getInt(0, -1);
            int i2 = obtainAttributes.getInt(1, -1);
            obtainAttributes.recycle();
            if (i < 0) {
                return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "<extension-sdk> must specify an sdkVersion >= 0");
            }
            if (i2 < 0) {
                return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "<extension-sdk> must specify minExtensionVersion >= 0");
            }
            try {
                int extensionVersion = android.os.ext.SdkExtensions.getExtensionVersion(i);
                if (extensionVersion < i2) {
                    return parseInput.error(-12, "Package requires " + i + " extension version " + i2 + " which exceeds device version " + extensionVersion);
                }
                sparseIntArray.put(i, i2);
                return parseInput.success(sparseIntArray);
            } catch (java.lang.RuntimeException e) {
                return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "Specified sdkVersion " + i + " is not valid");
            }
        } catch (java.lang.Throwable th) {
            obtainAttributes.recycle();
            throw th;
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseRestrictUpdateHash(int i, android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) {
        if ((i & 16) != 0) {
            android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestRestrictUpdate);
            try {
                java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
                if (nonConfigurationString != null) {
                    int length = nonConfigurationString.length();
                    byte[] bArr = new byte[length / 2];
                    for (int i2 = 0; i2 < length; i2 += 2) {
                        bArr[i2 / 2] = (byte) ((java.lang.Character.digit(nonConfigurationString.charAt(i2), 16) << 4) + java.lang.Character.digit(nonConfigurationString.charAt(i2 + 1), 16));
                    }
                    parsingPackage.setRestrictUpdateHash(bArr);
                } else {
                    parsingPackage.setRestrictUpdateHash(null);
                }
            } finally {
                obtainAttributes.recycle();
            }
        }
        return parseInput.success(parsingPackage);
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseInstallConstraints(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, java.util.Set<java.lang.String> set) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        return com.android.internal.pm.pkg.component.InstallConstraintsTagParser.parseInstallConstraints(parseInput, parsingPackage, resources, xmlResourceParser, set);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0191, code lost:
    
        return r12.success(r13);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseQueries(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String str;
        android.net.Uri uri;
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2) {
                if (xmlResourceParser.getName().equals("intent")) {
                    android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedIntentInfoImpl> parseIntentInfo = com.android.internal.pm.pkg.component.ParsedIntentInfoUtils.parseIntentInfo(null, parsingPackage, resources, xmlResourceParser, true, true, parseInput);
                    if (parseIntentInfo.isError()) {
                        return parseInput.error(parseIntentInfo);
                    }
                    android.content.IntentFilter intentFilter = parseIntentInfo.getResult().getIntentFilter();
                    int countActions = intentFilter.countActions();
                    int countDataSchemes = intentFilter.countDataSchemes();
                    int countDataTypes = intentFilter.countDataTypes();
                    int length = intentFilter.getHosts().length;
                    if (countDataSchemes == 0 && countDataTypes == 0 && countActions == 0) {
                        return parseInput.error("intent tags must contain either an action or data.");
                    }
                    if (countActions > 1) {
                        return parseInput.error("intent tag may have at most one action.");
                    }
                    if (countDataTypes > 1) {
                        return parseInput.error("intent tag may have at most one data type.");
                    }
                    if (countDataSchemes > 1) {
                        return parseInput.error("intent tag may have at most one data scheme.");
                    }
                    if (length > 1) {
                        return parseInput.error("intent tag may have at most one data host.");
                    }
                    android.content.Intent intent = new android.content.Intent();
                    int countCategories = intentFilter.countCategories();
                    for (int i = 0; i < countCategories; i++) {
                        intent.addCategory(intentFilter.getCategory(i));
                    }
                    java.lang.String str2 = null;
                    if (length != 1) {
                        str = null;
                    } else {
                        str = intentFilter.getHosts()[0];
                    }
                    if (countDataSchemes != 1) {
                        uri = null;
                    } else {
                        uri = new android.net.Uri.Builder().scheme(intentFilter.getDataScheme(0)).authority(str).path(android.content.IntentFilter.WILDCARD_PATH).build();
                    }
                    if (countDataTypes == 1) {
                        java.lang.String dataType = intentFilter.getDataType(0);
                        if (!dataType.contains("/")) {
                            str2 = dataType + android.content.IntentFilter.WILDCARD_PATH;
                        } else {
                            str2 = dataType;
                        }
                        if (uri == null) {
                            uri = new android.net.Uri.Builder().scheme("content").authority("*").path(android.content.IntentFilter.WILDCARD_PATH).build();
                        }
                    }
                    intent.setDataAndType(uri, str2);
                    if (countActions == 1) {
                        intent.setAction(intentFilter.getAction(0));
                    }
                    parsingPackage.addQueriesIntent(intent);
                } else if (xmlResourceParser.getName().equals("package")) {
                    java.lang.String nonConfigurationString = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestQueriesPackage).getNonConfigurationString(0, 0);
                    if (android.text.TextUtils.isEmpty(nonConfigurationString)) {
                        return parseInput.error("Package name is missing from package tag.");
                    }
                    parsingPackage.addQueriesPackage(nonConfigurationString.intern());
                } else if (xmlResourceParser.getName().equals("provider")) {
                    android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestQueriesProvider);
                    try {
                        java.lang.String nonConfigurationString2 = obtainAttributes.getNonConfigurationString(0, 0);
                        if (android.text.TextUtils.isEmpty(nonConfigurationString2)) {
                            return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_MANIFEST_MALFORMED, "Authority missing from provider tag.");
                        }
                        java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(nonConfigurationString2, android.inputmethodservice.navigationbar.NavigationBarInflaterView.GRAVITY_SEPARATOR);
                        while (stringTokenizer.hasMoreElements()) {
                            parsingPackage.addQueriesProvider(stringTokenizer.nextToken());
                        }
                    } finally {
                        obtainAttributes.recycle();
                    }
                } else {
                    continue;
                }
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0447, code lost:
    
        if (android.text.TextUtils.isEmpty(r25.getStaticSharedLibraryName()) == false) goto L233;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0451, code lost:
    
        if (android.text.TextUtils.isEmpty(r25.getSdkLibraryName()) == false) goto L233;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x0453, code lost:
    
        r1 = generateAppDetailsHiddenActivity(r24, r25);
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x045b, code lost:
    
        if (r1.isError() == false) goto L232;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0461, code lost:
    
        return r24.error(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0462, code lost:
    
        r25.addActivity(r1.getResult());
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x046b, code lost:
    
        if (r18 == false) goto L235;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x046d, code lost:
    
        r25.sortActivities();
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0470, code lost:
    
        if (r19 == false) goto L237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0472, code lost:
    
        r25.sortReceivers();
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0475, code lost:
    
        if (r20 == false) goto L239;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0477, code lost:
    
        r25.sortServices();
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x047a, code lost:
    
        afterParseBaseApplication(r25);
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x0481, code lost:
    
        return r24.success(r25);
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x02c6, code lost:
    
        if (r3.equals("service") != false) goto L167;
     */
    /* JADX WARN: Removed duplicated region for block: B:172:0x043b  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0436 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x03ec A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:217:0x03e8 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseBaseApplication(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i, boolean z) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i2;
        android.content.pm.parsing.result.ParseResult<?> parseApexSystemService;
        boolean z2;
        boolean z3;
        java.lang.String packageName = parsingPackage.getPackageName();
        int targetSdkVersion = parsingPackage.getTargetSdkVersion();
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestApplication);
        try {
            if (obtainAttributes == null) {
                return parseInput.error("<application> does not contain any attributes");
            }
            java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(3, 0);
            if (nonConfigurationString != null) {
                java.lang.String packageName2 = parsingPackage.getPackageName();
                java.lang.String buildClassName = com.android.internal.pm.pkg.parsing.ParsingUtils.buildClassName(packageName2, nonConfigurationString);
                if (android.content.pm.PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(buildClassName)) {
                    return parseInput.error("<application> invalid android:name");
                }
                if (buildClassName == null) {
                    return parseInput.error("Empty class name in package " + packageName2);
                }
                parsingPackage.setApplicationClassName(buildClassName);
            }
            android.util.TypedValue peekValue = obtainAttributes.peekValue(1);
            if (peekValue != null) {
                parsingPackage.setLabelResourceId(peekValue.resourceId);
                if (peekValue.resourceId == 0) {
                    parsingPackage.setNonLocalizedLabel(peekValue.coerceToString());
                }
            }
            parseBaseAppBasicFlags(parsingPackage, obtainAttributes);
            java.lang.String nonConfigString = nonConfigString(1024, 4, obtainAttributes);
            if (nonConfigString != null) {
                java.lang.String buildClassName2 = com.android.internal.pm.pkg.parsing.ParsingUtils.buildClassName(packageName, nonConfigString);
                if (buildClassName2 == null) {
                    return parseInput.error("Empty class name in package " + packageName);
                }
                parsingPackage.setManageSpaceActivityName(buildClassName2);
            }
            if (parsingPackage.isBackupAllowed()) {
                java.lang.String nonConfigString2 = nonConfigString(1024, 16, obtainAttributes);
                if (nonConfigString2 != null) {
                    java.lang.String buildClassName3 = com.android.internal.pm.pkg.parsing.ParsingUtils.buildClassName(packageName, nonConfigString2);
                    if (buildClassName3 == null) {
                        return parseInput.error("Empty class name in package " + packageName);
                    }
                    parsingPackage.setBackupAgentName(buildClassName3).setKillAfterRestoreAllowed(bool(true, 18, obtainAttributes)).setRestoreAnyVersion(bool(false, 21, obtainAttributes)).setFullBackupOnly(bool(false, 32, obtainAttributes)).setBackupInForeground(bool(false, 40, obtainAttributes));
                }
                android.util.TypedValue peekValue2 = obtainAttributes.peekValue(35);
                if (peekValue2 != null) {
                    int i3 = peekValue2.resourceId;
                    if (peekValue2.resourceId == 0) {
                        i3 = peekValue2.data == 0 ? -1 : 0;
                    }
                    parsingPackage.setFullBackupContentResourceId(i3);
                }
            }
            if (obtainAttributes.getBoolean(8, false)) {
                java.lang.String nonResourceString = obtainAttributes.getNonResourceString(45);
                if (nonResourceString != null && !this.mCallback.hasFeature(nonResourceString)) {
                    z3 = false;
                    parsingPackage.setPersistent(z3);
                }
                z3 = true;
                parsingPackage.setPersistent(z3);
            }
            if (obtainAttributes.hasValueOrEmpty(37)) {
                parsingPackage.setResizeableActivity(java.lang.Boolean.valueOf(obtainAttributes.getBoolean(37, true)));
            } else {
                parsingPackage.setResizeableActivityViaSdkVersion(targetSdkVersion >= 24);
            }
            android.content.pm.parsing.result.ParseResult<java.lang.String> buildTaskAffinityName = com.android.internal.pm.pkg.component.ComponentParseUtils.buildTaskAffinityName(packageName, packageName, targetSdkVersion >= 8 ? obtainAttributes.getNonConfigurationString(12, 1024) : obtainAttributes.getNonResourceString(12), parseInput);
            if (buildTaskAffinityName.isError()) {
                return parseInput.error(buildTaskAffinityName);
            }
            parsingPackage.setTaskAffinity(buildTaskAffinityName.getResult());
            java.lang.String nonResourceString2 = obtainAttributes.getNonResourceString(48);
            if (nonResourceString2 != null) {
                java.lang.String buildClassName4 = com.android.internal.pm.pkg.parsing.ParsingUtils.buildClassName(packageName, nonResourceString2);
                if (buildClassName4 == null) {
                    return parseInput.error("Empty class name in package " + packageName);
                }
                parsingPackage.setAppComponentFactory(buildClassName4);
            }
            android.content.pm.parsing.result.ParseResult<java.lang.String> buildProcessName = com.android.internal.pm.pkg.component.ComponentParseUtils.buildProcessName(packageName, null, targetSdkVersion >= 8 ? obtainAttributes.getNonConfigurationString(11, 1024) : obtainAttributes.getNonResourceString(11), i, this.mSeparateProcesses, parseInput);
            if (buildProcessName.isError()) {
                return parseInput.error(buildProcessName);
            }
            java.lang.String result = buildProcessName.getResult();
            parsingPackage.setProcessName(result);
            if (parsingPackage.isSaveStateDisallowed() && result != null && !result.equals(packageName)) {
                return parseInput.error("cantSaveState applications can not use custom processes");
            }
            java.lang.String classLoaderName = parsingPackage.getClassLoaderName();
            if (classLoaderName != null && !com.android.internal.os.ClassLoaderFactory.isValidClassLoaderName(classLoaderName)) {
                return parseInput.error("Invalid class loader name: " + classLoaderName);
            }
            parsingPackage.setGwpAsanMode(obtainAttributes.getInt(62, -1));
            parsingPackage.setMemtagMode(obtainAttributes.getInt(64, -1));
            if (obtainAttributes.hasValue(65)) {
                parsingPackage.setNativeHeapZeroInitialized(obtainAttributes.getBoolean(65, false) ? 1 : 0);
            }
            if (obtainAttributes.hasValue(67)) {
                parsingPackage.setRequestRawExternalStorageAccess(java.lang.Boolean.valueOf(obtainAttributes.getBoolean(67, false)));
            }
            if (obtainAttributes.hasValue(68)) {
                parsingPackage.setRequestForegroundServiceExemption(obtainAttributes.getBoolean(68, false));
            }
            android.content.pm.parsing.result.ParseResult<java.util.Set<java.lang.String>> parseKnownActivityEmbeddingCerts = com.android.internal.pm.pkg.parsing.ParsingUtils.parseKnownActivityEmbeddingCerts(obtainAttributes, resources, 72, parseInput);
            if (parseKnownActivityEmbeddingCerts.isError()) {
                return parseInput.error(parseKnownActivityEmbeddingCerts);
            }
            java.util.Set<java.lang.String> result2 = parseKnownActivityEmbeddingCerts.getResult();
            if (result2 != null) {
                parsingPackage.setKnownActivityEmbeddingCerts(result2);
            }
            obtainAttributes.recycle();
            int depth = xmlResourceParser.getDepth();
            boolean z4 = false;
            boolean z5 = false;
            boolean z6 = false;
            while (true) {
                int next = xmlResourceParser.next();
                if (next != 1 && (next != 3 || xmlResourceParser.getDepth() > depth)) {
                    char c = 2;
                    if (next == 2) {
                        java.lang.String name = xmlResourceParser.getName();
                        switch (name.hashCode()) {
                            case -1655966961:
                                if (name.equals("activity")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -1572095710:
                                if (name.equals("apex-system-service")) {
                                    c = 5;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -987494927:
                                if (name.equals("provider")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -808719889:
                                if (name.equals("receiver")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 790287890:
                                if (name.equals("activity-alias")) {
                                    c = 4;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1984153269:
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        switch (c) {
                            case 0:
                                i2 = depth;
                                z2 = true;
                                if (!z) {
                                    break;
                                } else {
                                    parseApexSystemService = com.android.internal.pm.pkg.component.ParsedActivityUtils.parseActivityOrReceiver(this.mSeparateProcesses, parsingPackage, resources, xmlResourceParser, i, sUseRoundIcon, null, parseInput);
                                    if (parseApexSystemService.isSuccess()) {
                                        com.android.internal.pm.pkg.component.ParsedActivity parsedActivity = (com.android.internal.pm.pkg.component.ParsedActivity) parseApexSystemService.getResult();
                                        if (z2) {
                                            boolean z7 = parsedActivity.getOrder() != 0;
                                            parsingPackage.addActivity(parsedActivity);
                                            z4 |= z7;
                                        } else {
                                            boolean z8 = parsedActivity.getOrder() != 0;
                                            parsingPackage.addReceiver(parsedActivity);
                                            z5 |= z8;
                                        }
                                    }
                                    if (!parseApexSystemService.isError()) {
                                        return parseInput.error(parseApexSystemService);
                                    }
                                    depth = i2;
                                }
                            case 1:
                                i2 = depth;
                                z2 = false;
                                if (!z) {
                                }
                                break;
                            case 2:
                                i2 = depth;
                                if (z) {
                                    break;
                                } else {
                                    parseApexSystemService = com.android.internal.pm.pkg.component.ParsedServiceUtils.parseService(this.mSeparateProcesses, parsingPackage, resources, xmlResourceParser, i, sUseRoundIcon, null, parseInput);
                                    if (parseApexSystemService.isSuccess()) {
                                        com.android.internal.pm.pkg.component.ParsedService parsedService = (com.android.internal.pm.pkg.component.ParsedService) parseApexSystemService.getResult();
                                        boolean z9 = parsedService.getOrder() != 0;
                                        parsingPackage.addService(parsedService);
                                        z6 |= z9;
                                    }
                                    if (!parseApexSystemService.isError()) {
                                    }
                                }
                                break;
                            case 3:
                                if (z) {
                                    i2 = depth;
                                    break;
                                } else {
                                    i2 = depth;
                                    parseApexSystemService = com.android.internal.pm.pkg.component.ParsedProviderUtils.parseProvider(this.mSeparateProcesses, parsingPackage, resources, xmlResourceParser, i, sUseRoundIcon, null, parseInput);
                                    if (parseApexSystemService.isSuccess()) {
                                        parsingPackage.addProvider((com.android.internal.pm.pkg.component.ParsedProvider) parseApexSystemService.getResult());
                                    }
                                    if (!parseApexSystemService.isError()) {
                                    }
                                }
                                break;
                            case 4:
                                if (z) {
                                    i2 = depth;
                                    break;
                                } else {
                                    parseApexSystemService = com.android.internal.pm.pkg.component.ParsedActivityUtils.parseActivityAlias(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, null, parseInput);
                                    if (parseApexSystemService.isSuccess()) {
                                        com.android.internal.pm.pkg.component.ParsedActivity parsedActivity2 = (com.android.internal.pm.pkg.component.ParsedActivity) parseApexSystemService.getResult();
                                        boolean z10 = parsedActivity2.getOrder() != 0;
                                        parsingPackage.addActivity(parsedActivity2);
                                        z4 |= z10;
                                    }
                                    i2 = depth;
                                    if (!parseApexSystemService.isError()) {
                                    }
                                }
                                break;
                            case 5:
                                parseApexSystemService = com.android.internal.pm.pkg.component.ParsedApexSystemServiceUtils.parseApexSystemService(resources, xmlResourceParser, parseInput);
                                if (parseApexSystemService.isSuccess()) {
                                    parsingPackage.addApexSystemService((com.android.internal.pm.pkg.component.ParsedApexSystemService) parseApexSystemService.getResult());
                                }
                                i2 = depth;
                                if (!parseApexSystemService.isError()) {
                                }
                                break;
                            default:
                                i2 = depth;
                                parseApexSystemService = parseBaseAppChildTag(parseInput, name, parsingPackage, resources, xmlResourceParser, i);
                                if (!parseApexSystemService.isError()) {
                                }
                                break;
                        }
                    } else {
                        i2 = depth;
                    }
                    depth = i2;
                }
            }
        } finally {
            obtainAttributes.recycle();
        }
    }

    private void afterParseBaseApplication(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage) {
        setMaxAspectRatio(parsingPackage);
        setMinAspectRatio(parsingPackage);
        setSupportsSizeChanges(parsingPackage);
        parsingPackage.setHasDomainUrls(hasDomainURLs(parsingPackage));
    }

    private void parseBaseAppBasicFlags(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.TypedArray typedArray) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        int targetSdkVersion = parsingPackage.getTargetSdkVersion();
        com.android.internal.pm.pkg.parsing.ParsingPackage onBackInvokedCallbackEnabled = parsingPackage.setBackupAllowed(bool(true, 17, typedArray)).setClearUserDataAllowed(bool(true, 5, typedArray)).setClearUserDataOnFailedRestoreAllowed(bool(true, 54, typedArray)).setAllowNativeHeapPointerTagging(bool(true, 59, typedArray)).setEnabled(bool(true, 9, typedArray)).setExtractNativeLibrariesRequested(bool(true, 34, typedArray)).setDeclaredHavingCode(bool(true, 7, typedArray)).setTaskReparentingAllowed(bool(false, 14, typedArray)).setSaveStateDisallowed(bool(false, 47, typedArray)).setCrossProfile(bool(false, 58, typedArray)).setDebuggable(bool(false, 10, typedArray)).setDefaultToDeviceProtectedStorage(bool(false, 38, typedArray)).setDirectBootAware(bool(false, 39, typedArray)).setForceQueryable(bool(false, 57, typedArray)).setGame(bool(false, 31, typedArray)).setUserDataFragile(bool(false, 50, typedArray)).setLargeHeap(bool(false, 24, typedArray)).setMultiArch(bool(false, 33, typedArray)).setPreserveLegacyExternalStorage(bool(false, 61, typedArray)).setRequiredForAllUsers(bool(false, 27, typedArray)).setRtlSupported(bool(false, 26, typedArray)).setTestOnly(bool(false, 15, typedArray)).setUseEmbeddedDex(bool(false, 53, typedArray)).setNonSdkApiRequested(bool(false, 49, typedArray)).setVmSafeMode(bool(false, 20, typedArray)).setAutoRevokePermissions(anInt(60, typedArray)).setAttributionsAreUserVisible(bool(false, 69, typedArray)).setResetEnabledSettingsOnAppDataCleared(bool(false, 70, typedArray)).setOnBackInvokedCallbackEnabled(bool(false, 73, typedArray));
        if (targetSdkVersion >= 29) {
            z = true;
        } else {
            z = false;
        }
        com.android.internal.pm.pkg.parsing.ParsingPackage allowAudioPlaybackCapture = onBackInvokedCallbackEnabled.setAllowAudioPlaybackCapture(bool(z, 55, typedArray));
        if (targetSdkVersion >= 14) {
            z2 = true;
        } else {
            z2 = false;
        }
        com.android.internal.pm.pkg.parsing.ParsingPackage hardwareAccelerated = allowAudioPlaybackCapture.setHardwareAccelerated(bool(z2, 23, typedArray));
        if (targetSdkVersion < 29) {
            z3 = true;
        } else {
            z3 = false;
        }
        com.android.internal.pm.pkg.parsing.ParsingPackage requestLegacyExternalStorage = hardwareAccelerated.setRequestLegacyExternalStorage(bool(z3, 56, typedArray));
        if (targetSdkVersion < 28) {
            z4 = true;
        } else {
            z4 = false;
        }
        requestLegacyExternalStorage.setCleartextTrafficAllowed(bool(z4, 36, typedArray)).setUiOptions(anInt(25, typedArray)).setCategory(anInt(-1, 43, typedArray)).setMaxAspectRatio(aFloat(44, typedArray)).setMinAspectRatio(aFloat(51, typedArray)).setBannerResourceId(resId(30, typedArray)).setDescriptionResourceId(resId(13, typedArray)).setIconResourceId(resId(2, typedArray)).setLogoResourceId(resId(22, typedArray)).setNetworkSecurityConfigResourceId(resId(41, typedArray)).setRoundIconResourceId(resId(42, typedArray)).setThemeResourceId(resId(0, typedArray)).setDataExtractionRulesResourceId(resId(66, typedArray)).setLocaleConfigResourceId(resId(71, typedArray)).setClassLoaderName(string(46, typedArray)).setRequiredAccountType(string(29, typedArray)).setRestrictedAccountType(string(28, typedArray)).setZygotePreloadName(string(52, typedArray)).setPermission(nonConfigString(0, 6, typedArray)).setAllowCrossUidActivitySwitchFromBelow(bool(true, 75, typedArray));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private android.content.pm.parsing.result.ParseResult parseBaseAppChildTag(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        char c;
        switch (str.hashCode()) {
            case -1803294168:
                if (str.equals("sdk-library")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1608941274:
                if (str.equals("uses-native-library")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1521117785:
                if (str.equals("uses-sdk-library")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1356765254:
                if (str.equals("uses-library")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1115949454:
                if (str.equals("meta-data")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1094759587:
                if (str.equals("processes")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1056667556:
                if (str.equals("static-library")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -993141291:
                if (str.equals("property")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 8960125:
                if (str.equals("uses-static-library")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 166208699:
                if (str.equals("library")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 178070147:
                if (str.equals("profileable")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 1964930885:
                if (str.equals("uses-package")) {
                    c = '\n';
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
                android.content.pm.parsing.result.ParseResult<android.content.pm.PackageManager.Property> parseMetaData = parseMetaData(parsingPackage, null, resources, xmlResourceParser, "<meta-data>", parseInput);
                if (parseMetaData.isSuccess() && parseMetaData.getResult() != null) {
                    parsingPackage.setMetaData(parseMetaData.getResult().toBundle(parsingPackage.getMetaData()));
                }
                return parseMetaData;
            case 1:
                android.content.pm.parsing.result.ParseResult<android.content.pm.PackageManager.Property> parseMetaData2 = parseMetaData(parsingPackage, null, resources, xmlResourceParser, "<property>", parseInput);
                if (parseMetaData2.isSuccess()) {
                    parsingPackage.addProperty(parseMetaData2.getResult());
                }
                return parseMetaData2;
            case 2:
                return parseSdkLibrary(parsingPackage, resources, xmlResourceParser, parseInput);
            case 3:
                return parseStaticLibrary(parsingPackage, resources, xmlResourceParser, parseInput);
            case 4:
                return parseLibrary(parsingPackage, resources, xmlResourceParser, parseInput);
            case 5:
                return parseUsesSdkLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case 6:
                return parseUsesStaticLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case 7:
                return parseUsesLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case '\b':
                return parseUsesNativeLibrary(parseInput, parsingPackage, resources, xmlResourceParser);
            case '\t':
                return parseProcesses(parseInput, parsingPackage, resources, xmlResourceParser, this.mSeparateProcesses, i);
            case '\n':
                return parseInput.success(null);
            case 11:
                return parseProfileable(parseInput, parsingPackage, resources, xmlResourceParser);
            default:
                return com.android.internal.pm.pkg.parsing.ParsingUtils.unknownTag("<application>", parsingPackage, xmlResourceParser, parseInput);
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseSdkLibrary(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.content.pm.parsing.result.ParseInput parseInput) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestSdkLibrary);
        try {
            java.lang.String nonResourceString = obtainAttributes.getNonResourceString(0);
            int i = obtainAttributes.getInt(1, -1);
            if (nonResourceString != null && i >= 0) {
                return parsingPackage.getSharedUserId() != null ? parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID, "sharedUserId not allowed in SDK library") : parsingPackage.getSdkLibraryName() != null ? parseInput.error("Multiple SDKs for package " + parsingPackage.getPackageName()) : parseInput.success(parsingPackage.setSdkLibraryName(nonResourceString.intern()).setSdkLibVersionMajor(i).setSdkLibrary(true));
            }
            return parseInput.error("Bad sdk-library declaration name: " + nonResourceString + " version: " + i);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseStaticLibrary(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.content.pm.parsing.result.ParseInput parseInput) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestStaticLibrary);
        try {
            java.lang.String nonResourceString = obtainAttributes.getNonResourceString(0);
            int i = obtainAttributes.getInt(1, -1);
            int i2 = obtainAttributes.getInt(2, 0);
            if (nonResourceString != null && i >= 0) {
                return parsingPackage.getSharedUserId() != null ? parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID, "sharedUserId not allowed in static shared library") : parsingPackage.getStaticSharedLibraryName() != null ? parseInput.error("Multiple static-shared libs for package " + parsingPackage.getPackageName()) : parseInput.success(parsingPackage.setStaticSharedLibraryName(nonResourceString.intern()).setStaticSharedLibraryVersion(android.content.pm.PackageInfo.composeLongVersionCode(i2, i)).setStaticSharedLibrary(true));
            }
            return parseInput.error("Bad static-library declaration name: " + nonResourceString + " version: " + i);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseLibrary(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, android.content.pm.parsing.result.ParseInput parseInput) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestLibrary);
        try {
            java.lang.String nonResourceString = obtainAttributes.getNonResourceString(0);
            if (nonResourceString != null) {
                java.lang.String intern = nonResourceString.intern();
                if (!com.android.internal.util.ArrayUtils.contains(parsingPackage.getLibraryNames(), intern)) {
                    parsingPackage.addLibraryName(intern);
                }
            }
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseUsesSdkLibrary(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String str = "";
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUsesSdkLibrary);
        try {
            java.lang.String nonResourceString = obtainAttributes.getNonResourceString(0);
            int i = obtainAttributes.getInt(2, -1);
            java.lang.String nonResourceString2 = obtainAttributes.getNonResourceString(1);
            boolean z = obtainAttributes.getBoolean(3, false);
            if (nonResourceString != null && i >= 0 && nonResourceString2 != null) {
                if (parsingPackage.getUsesSdkLibraries().contains(nonResourceString)) {
                    return parseInput.error("Depending on multiple versions of SDK library " + nonResourceString);
                }
                java.lang.String intern = nonResourceString.intern();
                java.lang.String lowerCase = nonResourceString2.replace(":", "").toLowerCase();
                if ("".equals(lowerCase)) {
                    java.lang.String str2 = android.os.SystemProperties.get("debug.pm.uses_sdk_library_default_cert_digest", "");
                    try {
                        libcore.util.HexEncoding.decode(str2, false);
                        str = str2;
                    } catch (java.lang.IllegalArgumentException e) {
                    }
                } else {
                    str = lowerCase;
                }
                android.content.pm.parsing.result.ParseResult<java.lang.String[]> parseAdditionalCertificates = parseAdditionalCertificates(parseInput, resources, xmlResourceParser);
                if (parseAdditionalCertificates.isError()) {
                    return parseInput.error((android.content.pm.parsing.result.ParseResult<?>) parseAdditionalCertificates);
                }
                java.lang.String[] result = parseAdditionalCertificates.getResult();
                java.lang.String[] strArr = new java.lang.String[result.length + 1];
                strArr[0] = str;
                java.lang.System.arraycopy(result, 0, strArr, 1, result.length);
                return parseInput.success(parsingPackage.addUsesSdkLibrary(intern, i, strArr, z));
            }
            return parseInput.error("Bad uses-sdk-library declaration name: " + nonResourceString + " version: " + i + " certDigest" + nonResourceString2);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseUsesStaticLibrary(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUsesStaticLibrary);
        try {
            java.lang.String nonResourceString = obtainAttributes.getNonResourceString(0);
            int i = obtainAttributes.getInt(1, -1);
            java.lang.String nonResourceString2 = obtainAttributes.getNonResourceString(2);
            if (nonResourceString != null && i >= 0 && nonResourceString2 != null) {
                if (parsingPackage.getUsesStaticLibraries().contains(nonResourceString)) {
                    return parseInput.error("Depending on multiple versions of static library " + nonResourceString);
                }
                java.lang.String intern = nonResourceString.intern();
                java.lang.String lowerCase = nonResourceString2.replace(":", "").toLowerCase();
                java.lang.String[] strArr = libcore.util.EmptyArray.STRING;
                if (parsingPackage.getTargetSdkVersion() >= 27) {
                    android.content.pm.parsing.result.ParseResult<java.lang.String[]> parseAdditionalCertificates = parseAdditionalCertificates(parseInput, resources, xmlResourceParser);
                    if (parseAdditionalCertificates.isError()) {
                        return parseInput.error((android.content.pm.parsing.result.ParseResult<?>) parseAdditionalCertificates);
                    }
                    strArr = parseAdditionalCertificates.getResult();
                }
                java.lang.String[] strArr2 = new java.lang.String[strArr.length + 1];
                strArr2[0] = lowerCase;
                java.lang.System.arraycopy(strArr, 0, strArr2, 1, strArr.length);
                return parseInput.success(parsingPackage.addUsesStaticLibrary(intern, i, strArr2));
            }
            return parseInput.error("Bad uses-static-library declaration name: " + nonResourceString + " version: " + i + " certDigest" + nonResourceString2);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseUsesLibrary(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUsesLibrary);
        try {
            java.lang.String nonResourceString = obtainAttributes.getNonResourceString(0);
            boolean z = obtainAttributes.getBoolean(1, true);
            if (nonResourceString != null) {
                java.lang.String intern = nonResourceString.intern();
                if (z) {
                    parsingPackage.addUsesLibrary(intern).removeUsesOptionalLibrary(intern);
                } else if (!com.android.internal.util.ArrayUtils.contains(parsingPackage.getUsesLibraries(), intern)) {
                    parsingPackage.addUsesOptionalLibrary(intern);
                }
            }
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseUsesNativeLibrary(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestUsesNativeLibrary);
        try {
            java.lang.String nonResourceString = obtainAttributes.getNonResourceString(0);
            boolean z = obtainAttributes.getBoolean(1, true);
            if (nonResourceString != null) {
                if (z) {
                    parsingPackage.addUsesNativeLibrary(nonResourceString).removeUsesOptionalNativeLibrary(nonResourceString);
                } else if (!com.android.internal.util.ArrayUtils.contains(parsingPackage.getUsesNativeLibraries(), nonResourceString)) {
                    parsingPackage.addUsesOptionalNativeLibrary(nonResourceString);
                }
            }
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseProcesses(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, java.lang.String[] strArr, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.content.pm.parsing.result.ParseResult<android.util.ArrayMap<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess>> parseProcesses = com.android.internal.pm.pkg.component.ParsedProcessUtils.parseProcesses(strArr, parsingPackage, resources, xmlResourceParser, i, parseInput);
        if (parseProcesses.isError()) {
            return parseInput.error(parseProcesses);
        }
        return parseInput.success(parsingPackage.setProcesses(parseProcesses.getResult()));
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseProfileable(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) {
        boolean z;
        com.android.internal.pm.pkg.parsing.ParsingPackage profileableByShell;
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestProfileable);
        try {
            boolean z2 = false;
            if (!parsingPackage.isProfileableByShell() && !bool(false, 1, obtainAttributes)) {
                z = false;
                profileableByShell = parsingPackage.setProfileableByShell(z);
                if (profileableByShell.isProfileable() && bool(true, 0, obtainAttributes)) {
                    z2 = true;
                }
                return parseInput.success(profileableByShell.setProfileable(z2));
            }
            z = true;
            profileableByShell = parsingPackage.setProfileableByShell(z);
            if (profileableByShell.isProfileable()) {
                z2 = true;
            }
            return parseInput.success(profileableByShell.setProfileable(z2));
        } finally {
            obtainAttributes.recycle();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0074, code lost:
    
        return r6.success(r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static android.content.pm.parsing.result.ParseResult<java.lang.String[]> parseAdditionalCertificates(android.content.pm.parsing.result.ParseInput parseInput, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String[] strArr = libcore.util.EmptyArray.STRING;
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2 && xmlResourceParser.getName().equals("additional-certificate")) {
                android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestAdditionalCertificate);
                try {
                    java.lang.String nonResourceString = obtainAttributes.getNonResourceString(0);
                    if (android.text.TextUtils.isEmpty(nonResourceString)) {
                        return parseInput.error("Bad additional-certificate declaration with empty certDigest:" + nonResourceString);
                    }
                    strArr = (java.lang.String[]) com.android.internal.util.ArrayUtils.appendElement(java.lang.String.class, strArr, nonResourceString.replace(":", "").toLowerCase());
                } finally {
                    obtainAttributes.recycle();
                }
            }
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedActivity> generateAppDetailsHiddenActivity(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage) {
        java.lang.String packageName = parsingPackage.getPackageName();
        android.content.pm.parsing.result.ParseResult<java.lang.String> buildTaskAffinityName = com.android.internal.pm.pkg.component.ComponentParseUtils.buildTaskAffinityName(packageName, packageName, ":app_details", parseInput);
        if (buildTaskAffinityName.isError()) {
            return parseInput.error(buildTaskAffinityName);
        }
        return parseInput.success(com.android.internal.pm.pkg.component.ParsedActivityImpl.makeAppDetailsActivity(packageName, parsingPackage.getProcessName(), parsingPackage.getUiOptions(), buildTaskAffinityName.getResult(), parsingPackage.isHardwareAccelerated()));
    }

    private static boolean hasDomainURLs(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage) {
        java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> activities = parsingPackage.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            java.util.List<com.android.internal.pm.pkg.component.ParsedIntentInfo> intents = activities.get(i).getIntents();
            int size2 = intents.size();
            for (int i2 = 0; i2 < size2; i2++) {
                android.content.IntentFilter intentFilter = intents.get(i2).getIntentFilter();
                if (intentFilter.hasAction("android.intent.action.VIEW") && intentFilter.hasAction("android.intent.action.VIEW") && (intentFilter.hasDataScheme(android.content.IntentFilter.SCHEME_HTTP) || intentFilter.hasDataScheme(android.content.IntentFilter.SCHEME_HTTPS))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void setMaxAspectRatio(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage) {
        float f = parsingPackage.getTargetSdkVersion() < 26 ? 1.86f : 0.0f;
        float maxAspectRatio = parsingPackage.getMaxAspectRatio();
        if (maxAspectRatio != 0.0f) {
            f = maxAspectRatio;
        } else {
            android.os.Bundle metaData = parsingPackage.getMetaData();
            if (metaData != null && metaData.containsKey("android.max_aspect")) {
                f = metaData.getFloat("android.max_aspect", f);
            }
        }
        java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> activities = parsingPackage.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.pm.pkg.component.ParsedActivity parsedActivity = activities.get(i);
            if (parsedActivity.getMaxAspectRatio() == -1.0f) {
                com.android.internal.pm.pkg.component.ComponentMutateUtils.setMaxAspectRatio(parsedActivity, parsedActivity.getResizeMode(), parsedActivity.getMetaData().getFloat("android.max_aspect", f));
            }
        }
    }

    private void setMinAspectRatio(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage) {
        float minAspectRatio = parsingPackage.getMinAspectRatio();
        java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> activities = parsingPackage.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.pm.pkg.component.ParsedActivity parsedActivity = activities.get(i);
            if (parsedActivity.getMinAspectRatio() == -1.0f) {
                com.android.internal.pm.pkg.component.ComponentMutateUtils.setMinAspectRatio(parsedActivity, parsedActivity.getResizeMode(), minAspectRatio);
            }
        }
    }

    private void setSupportsSizeChanges(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage) {
        android.os.Bundle metaData = parsingPackage.getMetaData();
        boolean z = metaData != null && metaData.getBoolean("android.supports_size_changes", false);
        java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> activities = parsingPackage.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.pm.pkg.component.ParsedActivity parsedActivity = activities.get(i);
            if (z || parsedActivity.getMetaData().getBoolean("android.supports_size_changes", false)) {
                com.android.internal.pm.pkg.component.ComponentMutateUtils.setSupportsSizeChanges(parsedActivity, true);
            }
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseOverlay(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestResourceOverlay);
        try {
            java.lang.String string = obtainAttributes.getString(1);
            int anInt = anInt(0, 0, obtainAttributes);
            if (string == null) {
                return parseInput.error("<overlay> does not specify a target package");
            }
            if (anInt < 0 || anInt > 9999) {
                return parseInput.error("<overlay> priority must be between 0 and 9999");
            }
            java.lang.String string2 = obtainAttributes.getString(5);
            java.lang.String string3 = obtainAttributes.getString(6);
            if (android.content.pm.parsing.FrameworkParsingPackageUtils.checkRequiredSystemProperties(string2, string3)) {
                return parseInput.success(parsingPackage.setResourceOverlay(true).setOverlayTarget(string).setOverlayPriority(anInt).setOverlayTargetOverlayableName(obtainAttributes.getString(3)).setOverlayCategory(obtainAttributes.getString(2)).setOverlayIsStatic(bool(false, 4, obtainAttributes)));
            }
            java.lang.String str = "Skipping target and overlay pair " + string + " and " + parsingPackage.getBaseApkPath() + ": overlay ignored due to required system property: " + string2 + " with value: " + string3;
            android.util.Slog.i("PackageParsing", str);
            return parseInput.skip(str);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseProtectedBroadcast(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestProtectedBroadcast);
        try {
            java.lang.String nonResString = nonResString(0, obtainAttributes);
            if (nonResString != null) {
                parsingPackage.addProtectedBroadcast(nonResString);
            }
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseSupportScreens(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestSupportsScreens);
        try {
            int anInt = anInt(0, 6, obtainAttributes);
            int anInt2 = anInt(0, 7, obtainAttributes);
            return parseInput.success(parsingPackage.setSmallScreensSupported(anInt(1, 1, obtainAttributes)).setNormalScreensSupported(anInt(1, 2, obtainAttributes)).setLargeScreensSupported(anInt(1, 3, obtainAttributes)).setExtraLargeScreensSupported(anInt(1, 5, obtainAttributes)).setResizeable(anInt(1, 4, obtainAttributes)).setAnyDensity(anInt(1, 0, obtainAttributes)).setRequiresSmallestWidthDp(anInt).setCompatibleWidthLimitDp(anInt2).setLargestWidthLimitDp(anInt(0, 8, obtainAttributes)));
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseInstrumentation(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.component.ParsedInstrumentation> parseInstrumentation = com.android.internal.pm.pkg.component.ParsedInstrumentationUtils.parseInstrumentation(parsingPackage, resources, xmlResourceParser, sUseRoundIcon, parseInput);
        if (parseInstrumentation.isError()) {
            return parseInput.error(parseInstrumentation);
        }
        return parseInput.success(parsingPackage.addInstrumentation(parseInstrumentation.getResult()));
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseOriginalPackage(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestOriginalPackage);
        try {
            java.lang.String nonConfigurationString = obtainAttributes.getNonConfigurationString(0, 0);
            if (!parsingPackage.getPackageName().equals(nonConfigurationString)) {
                parsingPackage.addOriginalPackage(nonConfigurationString);
            }
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static android.content.pm.parsing.result.ParseResult<com.android.internal.pm.pkg.parsing.ParsingPackage> parseAdoptPermissions(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestOriginalPackage);
        try {
            java.lang.String nonConfigString = nonConfigString(0, 0, obtainAttributes);
            if (nonConfigString != null) {
                parsingPackage.addAdoptPermission(nonConfigString);
            }
            return parseInput.success(parsingPackage);
        } finally {
            obtainAttributes.recycle();
        }
    }

    private static void convertCompatPermissions(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage) {
        int length = com.android.internal.pm.permission.CompatibilityPermissionInfo.COMPAT_PERMS.length;
        for (int i = 0; i < length; i++) {
            com.android.internal.pm.permission.CompatibilityPermissionInfo compatibilityPermissionInfo = com.android.internal.pm.permission.CompatibilityPermissionInfo.COMPAT_PERMS[i];
            if (parsingPackage.getTargetSdkVersion() < compatibilityPermissionInfo.getSdkVersion()) {
                if (!parsingPackage.getRequestedPermissions().contains(compatibilityPermissionInfo.getName())) {
                    parsingPackage.addImplicitPermission(compatibilityPermissionInfo.getName());
                }
            } else {
                return;
            }
        }
    }

    private void convertSplitPermissions(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage) {
        int size = this.mSplitPermissionInfos.size();
        for (int i = 0; i < size; i++) {
            android.permission.PermissionManager.SplitPermissionInfo splitPermissionInfo = this.mSplitPermissionInfos.get(i);
            java.util.Set<java.lang.String> requestedPermissions = parsingPackage.getRequestedPermissions();
            if (parsingPackage.getTargetSdkVersion() < splitPermissionInfo.getTargetSdk() && requestedPermissions.contains(splitPermissionInfo.getSplitPermission())) {
                java.util.List<java.lang.String> newPermissions = splitPermissionInfo.getNewPermissions();
                for (int i2 = 0; i2 < newPermissions.size(); i2++) {
                    java.lang.String str = newPermissions.get(i2);
                    if (!requestedPermissions.contains(str)) {
                        parsingPackage.addImplicitPermission(str);
                    }
                }
            }
        }
    }

    private static void adjustPackageToBeUnresizeableAndUnpipable(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage) {
        java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> activities = parsingPackage.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.pm.pkg.component.ParsedActivity parsedActivity = activities.get(i);
            com.android.internal.pm.pkg.component.ComponentMutateUtils.setResizeMode(parsedActivity, 0);
            com.android.internal.pm.pkg.component.ComponentMutateUtils.setExactFlags(parsedActivity, parsedActivity.getFlags() & (-4194305));
        }
    }

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.PackageManager.Property> parseMetaData(com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, com.android.internal.pm.pkg.component.ParsedComponent parsedComponent, android.content.res.Resources resources, android.content.res.XmlResourceParser xmlResourceParser, java.lang.String str, android.content.pm.parsing.result.ParseInput parseInput) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, com.android.internal.R.styleable.AndroidManifestMetaData);
        try {
            java.lang.String safeIntern = android.text.TextUtils.safeIntern(nonConfigString(0, 0, obtainAttributes));
            if (safeIntern == null) {
                return parseInput.error(str + " requires an android:name attribute");
            }
            java.lang.String packageName = parsingPackage.getPackageName();
            android.content.pm.PackageManager.Property property = null;
            java.lang.String name = parsedComponent != null ? parsedComponent.getName() : null;
            android.util.TypedValue peekValue = obtainAttributes.peekValue(2);
            if (peekValue == null || peekValue.resourceId == 0) {
                android.util.TypedValue peekValue2 = obtainAttributes.peekValue(1);
                if (peekValue2 == null) {
                    return parseInput.error(str + " requires an android:value or android:resource attribute");
                }
                if (peekValue2.type == 3) {
                    java.lang.CharSequence coerceToString = peekValue2.coerceToString();
                    property = new android.content.pm.PackageManager.Property(safeIntern, coerceToString != null ? coerceToString.toString() : null, packageName, name);
                } else if (peekValue2.type == 18) {
                    property = new android.content.pm.PackageManager.Property(safeIntern, peekValue2.data != 0, packageName, name);
                } else if (peekValue2.type >= 16 && peekValue2.type <= 31) {
                    property = new android.content.pm.PackageManager.Property(safeIntern, peekValue2.data, false, packageName, name);
                } else if (peekValue2.type == 4) {
                    property = new android.content.pm.PackageManager.Property(safeIntern, peekValue2.getFloat(), packageName, name);
                } else {
                    android.util.Slog.w("PackageParsing", str + " only supports string, integer, float, color, boolean, and resource reference types: " + xmlResourceParser.getName() + " at " + parsingPackage.getBaseApkPath() + " " + xmlResourceParser.getPositionDescription());
                }
            } else {
                property = new android.content.pm.PackageManager.Property(safeIntern, peekValue.resourceId, true, packageName, name);
            }
            return parseInput.success(property);
        } finally {
            obtainAttributes.recycle();
        }
    }

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.SigningDetails> getSigningDetails(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, boolean z) {
        return getSigningDetails(parseInput, parsedPackage.getBaseApkPath(), parsedPackage.isStaticSharedLibrary(), parsedPackage.getTargetSdkVersion(), parsedPackage.getSplitCodePaths(), z);
    }

    private static android.content.pm.parsing.result.ParseResult<android.content.pm.SigningDetails> getSigningDetails(android.content.pm.parsing.result.ParseInput parseInput, com.android.internal.pm.pkg.parsing.ParsingPackage parsingPackage, boolean z) {
        return getSigningDetails(parseInput, parsingPackage.getBaseApkPath(), parsingPackage.isStaticSharedLibrary(), parsingPackage.getTargetSdkVersion(), parsingPackage.getSplitCodePaths(), z);
    }

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.SigningDetails> getSigningDetails(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, boolean z, int i, java.lang.String[] strArr, boolean z2) {
        android.content.pm.SigningDetails signingDetails = android.content.pm.SigningDetails.UNKNOWN;
        android.os.Trace.traceBegin(262144L, "collectCertificates");
        try {
            android.content.pm.parsing.result.ParseResult<android.content.pm.SigningDetails> signingDetails2 = getSigningDetails(parseInput, str, z2, z, signingDetails, i);
            if (signingDetails2.isError()) {
                return parseInput.error(signingDetails2);
            }
            android.content.pm.SigningDetails result = signingDetails2.getResult();
            boolean equals = new java.io.File(android.os.Environment.getRootDirectory(), "framework/framework-res.apk").getAbsolutePath().equals(str);
            if (!com.android.internal.util.ArrayUtils.isEmpty(strArr) && !equals) {
                for (java.lang.String str2 : strArr) {
                    signingDetails2 = getSigningDetails(parseInput, str2, z2, z, result, i);
                    if (signingDetails2.isError()) {
                        return parseInput.error(signingDetails2);
                    }
                }
            }
            return signingDetails2;
        } finally {
            android.os.Trace.traceEnd(262144L);
        }
    }

    public static android.content.pm.parsing.result.ParseResult<android.content.pm.SigningDetails> getSigningDetails(android.content.pm.parsing.result.ParseInput parseInput, java.lang.String str, boolean z, boolean z2, android.content.pm.SigningDetails signingDetails, int i) {
        android.content.pm.parsing.result.ParseResult<android.content.pm.SigningDetails> verify;
        int minimumSignatureSchemeVersionForTargetSdk = android.util.apk.ApkSignatureVerifier.getMinimumSignatureSchemeVersionForTargetSdk(i);
        if (z2) {
            minimumSignatureSchemeVersionForTargetSdk = 2;
        }
        if (z) {
            verify = android.util.apk.ApkSignatureVerifier.unsafeGetCertsWithoutVerification(parseInput, str, minimumSignatureSchemeVersionForTargetSdk);
        } else {
            verify = android.util.apk.ApkSignatureVerifier.verify(parseInput, str, minimumSignatureSchemeVersionForTargetSdk);
        }
        if (verify.isError()) {
            return parseInput.error(verify);
        }
        if (signingDetails == android.content.pm.SigningDetails.UNKNOWN) {
            return verify;
        }
        if (!android.content.pm.Signature.areExactMatch(signingDetails, verify.getResult())) {
            return parseInput.error(android.content.pm.PackageManager.INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES, str + " has mismatched certificates");
        }
        return parseInput.success(signingDetails);
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

    private static boolean bool(boolean z, int i, android.content.res.TypedArray typedArray) {
        return typedArray.getBoolean(i, z);
    }

    private static float aFloat(float f, int i, android.content.res.TypedArray typedArray) {
        return typedArray.getFloat(i, f);
    }

    private static float aFloat(int i, android.content.res.TypedArray typedArray) {
        return typedArray.getFloat(i, 0.0f);
    }

    private static int anInt(int i, int i2, android.content.res.TypedArray typedArray) {
        return typedArray.getInt(i2, i);
    }

    private static int anInteger(int i, int i2, android.content.res.TypedArray typedArray) {
        return typedArray.getInteger(i2, i);
    }

    private static int anInt(int i, android.content.res.TypedArray typedArray) {
        return typedArray.getInt(i, 0);
    }

    private static int resId(int i, android.content.res.TypedArray typedArray) {
        return typedArray.getResourceId(i, 0);
    }

    private static java.lang.String string(int i, android.content.res.TypedArray typedArray) {
        return typedArray.getString(i);
    }

    private static java.lang.String nonConfigString(int i, int i2, android.content.res.TypedArray typedArray) {
        return typedArray.getNonConfigurationString(i2, i);
    }

    private static java.lang.String nonResString(int i, android.content.res.TypedArray typedArray) {
        return typedArray.getNonResourceString(i);
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
}
