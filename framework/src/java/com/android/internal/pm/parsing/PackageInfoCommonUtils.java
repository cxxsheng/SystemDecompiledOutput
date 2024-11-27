package com.android.internal.pm.parsing;

/* loaded from: classes5.dex */
public class PackageInfoCommonUtils {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "PackageParsing";

    public static android.content.pm.PackageInfo generate(com.android.server.pm.pkg.AndroidPackage androidPackage, long j, int i) {
        int size;
        int size2;
        int i2;
        int size3;
        int size4;
        int size5;
        if (androidPackage == null) {
            return null;
        }
        android.content.pm.ApplicationInfo generateApplicationInfo = generateApplicationInfo(androidPackage, j, i);
        android.content.pm.PackageInfo packageInfo = new android.content.pm.PackageInfo();
        packageInfo.packageName = androidPackage.getPackageName();
        packageInfo.splitNames = androidPackage.getSplitNames();
        com.android.internal.pm.pkg.parsing.ParsingPackageHidden parsingPackageHidden = (com.android.internal.pm.pkg.parsing.ParsingPackageHidden) androidPackage;
        packageInfo.versionCode = parsingPackageHidden.getVersionCode();
        packageInfo.versionCodeMajor = parsingPackageHidden.getVersionCodeMajor();
        packageInfo.baseRevisionCode = androidPackage.getBaseRevisionCode();
        packageInfo.splitRevisionCodes = androidPackage.getSplitRevisionCodes();
        packageInfo.versionName = androidPackage.getVersionName();
        if (!androidPackage.isLeavingSharedUser()) {
            packageInfo.sharedUserId = androidPackage.getSharedUserId();
            packageInfo.sharedUserLabel = androidPackage.getSharedUserLabelResourceId();
        }
        packageInfo.applicationInfo = generateApplicationInfo;
        packageInfo.installLocation = androidPackage.getInstallLocation();
        if ((packageInfo.applicationInfo.flags & 1) != 0 || (packageInfo.applicationInfo.flags & 128) != 0) {
            packageInfo.requiredForAllUsers = androidPackage.isRequiredForAllUsers();
        }
        packageInfo.restrictedAccountType = androidPackage.getRestrictedAccountType();
        packageInfo.requiredAccountType = androidPackage.getRequiredAccountType();
        packageInfo.overlayTarget = androidPackage.getOverlayTarget();
        packageInfo.targetOverlayableName = androidPackage.getOverlayTargetOverlayableName();
        packageInfo.overlayCategory = androidPackage.getOverlayCategory();
        packageInfo.overlayPriority = androidPackage.getOverlayPriority();
        packageInfo.mOverlayIsStatic = androidPackage.isOverlayIsStatic();
        packageInfo.compileSdkVersion = androidPackage.getCompileSdkVersion();
        packageInfo.compileSdkVersionCodename = androidPackage.getCompileSdkVersionCodeName();
        packageInfo.isStub = androidPackage.isStub();
        packageInfo.coreApp = androidPackage.isCoreApp();
        packageInfo.isApex = androidPackage.isApex();
        if ((16384 & j) != 0) {
            int size6 = androidPackage.getConfigPreferences().size();
            if (size6 > 0) {
                packageInfo.configPreferences = new android.content.pm.ConfigurationInfo[size6];
                androidPackage.getConfigPreferences().toArray(packageInfo.configPreferences);
            }
            int size7 = androidPackage.getRequestedFeatures().size();
            if (size7 > 0) {
                packageInfo.reqFeatures = new android.content.pm.FeatureInfo[size7];
                androidPackage.getRequestedFeatures().toArray(packageInfo.reqFeatures);
            }
            int size8 = androidPackage.getFeatureGroups().size();
            if (size8 > 0) {
                packageInfo.featureGroups = new android.content.pm.FeatureGroupInfo[size8];
                androidPackage.getFeatureGroups().toArray(packageInfo.featureGroups);
            }
        }
        if ((4096 & j) != 0) {
            int size9 = com.android.internal.util.ArrayUtils.size(androidPackage.getPermissions());
            if (size9 > 0) {
                packageInfo.permissions = new android.content.pm.PermissionInfo[size9];
                for (int i3 = 0; i3 < size9; i3++) {
                    packageInfo.permissions[i3] = generatePermissionInfo(androidPackage.getPermissions().get(i3), j);
                }
            }
            java.util.List<com.android.internal.pm.pkg.component.ParsedUsesPermission> usesPermissions = androidPackage.getUsesPermissions();
            int size10 = usesPermissions.size();
            if (size10 > 0) {
                packageInfo.requestedPermissions = new java.lang.String[size10];
                packageInfo.requestedPermissionsFlags = new int[size10];
                for (int i4 = 0; i4 < size10; i4++) {
                    com.android.internal.pm.pkg.component.ParsedUsesPermission parsedUsesPermission = usesPermissions.get(i4);
                    packageInfo.requestedPermissions[i4] = parsedUsesPermission.getName();
                    int[] iArr = packageInfo.requestedPermissionsFlags;
                    iArr[i4] = iArr[i4] | 1;
                    if ((parsedUsesPermission.getUsesPermissionFlags() & 65536) != 0) {
                        int[] iArr2 = packageInfo.requestedPermissionsFlags;
                        iArr2[i4] = 65536 | iArr2[i4];
                    }
                    if (androidPackage.getImplicitPermissions().contains(packageInfo.requestedPermissions[i4])) {
                        int[] iArr3 = packageInfo.requestedPermissionsFlags;
                        iArr3[i4] = iArr3[i4] | 4;
                    }
                }
            }
        }
        if ((2147483648L & j) != 0) {
            int size11 = com.android.internal.util.ArrayUtils.size(androidPackage.getAttributions());
            if (size11 > 0) {
                packageInfo.attributions = new android.content.pm.Attribution[size11];
                for (int i5 = 0; i5 < size11; i5++) {
                    com.android.internal.pm.pkg.component.ParsedAttribution parsedAttribution = androidPackage.getAttributions().get(i5);
                    if (parsedAttribution != null) {
                        packageInfo.attributions[i5] = new android.content.pm.Attribution(parsedAttribution.getTag(), parsedAttribution.getLabel());
                    }
                }
            }
            if (androidPackage.isAttributionsUserVisible()) {
                packageInfo.applicationInfo.privateFlagsExt |= 4;
            } else {
                packageInfo.applicationInfo.privateFlagsExt &= -5;
            }
        } else {
            packageInfo.applicationInfo.privateFlagsExt &= -5;
        }
        android.content.pm.SigningDetails signingDetails = androidPackage.getSigningDetails();
        if ((64 & j) != 0) {
            if (signingDetails.hasPastSigningCertificates()) {
                packageInfo.signatures = new android.content.pm.Signature[1];
                packageInfo.signatures[0] = signingDetails.getPastSigningCertificates()[0];
            } else if (signingDetails.hasSignatures()) {
                int length = signingDetails.getSignatures().length;
                packageInfo.signatures = new android.content.pm.Signature[length];
                java.lang.System.arraycopy(signingDetails.getSignatures(), 0, packageInfo.signatures, 0, length);
            }
        }
        if ((134217728 & j) != 0) {
            if (signingDetails != android.content.pm.SigningDetails.UNKNOWN) {
                packageInfo.signingInfo = new android.content.pm.SigningInfo(signingDetails);
            } else {
                packageInfo.signingInfo = null;
            }
        }
        if ((1 & j) != 0 && (size5 = androidPackage.getActivities().size()) > 0) {
            android.content.pm.ActivityInfo[] activityInfoArr = new android.content.pm.ActivityInfo[size5];
            int i6 = 0;
            for (int i7 = 0; i7 < size5; i7++) {
                com.android.internal.pm.pkg.component.ParsedActivity parsedActivity = androidPackage.getActivities().get(i7);
                if (isMatch(androidPackage, parsedActivity.isDirectBootAware(), j) && !android.content.pm.PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(parsedActivity.getName())) {
                    activityInfoArr[i6] = generateActivityInfo(parsedActivity, j, generateApplicationInfo);
                    i6++;
                }
            }
            packageInfo.activities = (android.content.pm.ActivityInfo[]) com.android.internal.util.ArrayUtils.trimToSize(activityInfoArr, i6);
        }
        if ((2 & j) != 0 && (size4 = androidPackage.getReceivers().size()) > 0) {
            android.content.pm.ActivityInfo[] activityInfoArr2 = new android.content.pm.ActivityInfo[size4];
            int i8 = 0;
            for (int i9 = 0; i9 < size4; i9++) {
                com.android.internal.pm.pkg.component.ParsedActivity parsedActivity2 = androidPackage.getReceivers().get(i9);
                if (isMatch(androidPackage, parsedActivity2.isDirectBootAware(), j)) {
                    activityInfoArr2[i8] = generateActivityInfo(parsedActivity2, j, generateApplicationInfo);
                    i8++;
                }
            }
            packageInfo.receivers = (android.content.pm.ActivityInfo[]) com.android.internal.util.ArrayUtils.trimToSize(activityInfoArr2, i8);
        }
        if ((4 & j) != 0 && (size3 = androidPackage.getServices().size()) > 0) {
            android.content.pm.ServiceInfo[] serviceInfoArr = new android.content.pm.ServiceInfo[size3];
            int i10 = 0;
            for (int i11 = 0; i11 < size3; i11++) {
                com.android.internal.pm.pkg.component.ParsedService parsedService = androidPackage.getServices().get(i11);
                if (isMatch(androidPackage, parsedService.isDirectBootAware(), j)) {
                    serviceInfoArr[i10] = generateServiceInfo(parsedService, j, generateApplicationInfo);
                    i10++;
                }
            }
            packageInfo.services = (android.content.pm.ServiceInfo[]) com.android.internal.util.ArrayUtils.trimToSize(serviceInfoArr, i10);
        }
        if ((8 & j) != 0 && (size2 = androidPackage.getProviders().size()) > 0) {
            android.content.pm.ProviderInfo[] providerInfoArr = new android.content.pm.ProviderInfo[size2];
            int i12 = 0;
            int i13 = 0;
            while (i13 < size2) {
                com.android.internal.pm.pkg.component.ParsedProvider parsedProvider = androidPackage.getProviders().get(i13);
                if (isMatch(androidPackage, parsedProvider.isDirectBootAware(), j)) {
                    i2 = i13;
                    providerInfoArr[i12] = generateProviderInfo(androidPackage, parsedProvider, j, generateApplicationInfo, i);
                    i12++;
                } else {
                    i2 = i13;
                }
                i13 = i2 + 1;
            }
            packageInfo.providers = (android.content.pm.ProviderInfo[]) com.android.internal.util.ArrayUtils.trimToSize(providerInfoArr, i12);
        }
        if ((16 & j) != 0 && (size = androidPackage.getInstrumentations().size()) > 0) {
            packageInfo.instrumentation = new android.content.pm.InstrumentationInfo[size];
            for (int i14 = 0; i14 < size; i14++) {
                packageInfo.instrumentation[i14] = generateInstrumentationInfo(androidPackage.getInstrumentations().get(i14), androidPackage, j, i);
            }
        }
        return packageInfo;
    }

    private static void updateApplicationInfo(android.content.pm.ApplicationInfo applicationInfo, long j) {
        if ((128 & j) == 0) {
            applicationInfo.metaData = null;
        }
        if ((j & 1024) == 0) {
            applicationInfo.sharedLibraryFiles = null;
            applicationInfo.sharedLibraryInfos = null;
        }
        if (!com.android.internal.pm.pkg.parsing.ParsingPackageUtils.sCompatibilityModeEnabled) {
            applicationInfo.disableCompatibilityMode();
        }
        if (applicationInfo.category == -1) {
            applicationInfo.category = android.content.pm.FallbackCategoryProvider.getFallbackCategory(applicationInfo.packageName);
        }
        applicationInfo.seInfoUser = com.android.internal.pm.pkg.SEInfoUtil.COMPLETE_STR;
    }

    private static android.content.pm.ApplicationInfo generateApplicationInfo(com.android.server.pm.pkg.AndroidPackage androidPackage, long j, int i) {
        android.content.pm.ApplicationInfo appInfoWithoutState = ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).toAppInfoWithoutState();
        updateApplicationInfo(appInfoWithoutState, j);
        initForUser(appInfoWithoutState, androidPackage, i);
        appInfoWithoutState.primaryCpuAbi = com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.getRawPrimaryCpuAbi(androidPackage);
        appInfoWithoutState.secondaryCpuAbi = com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.getRawSecondaryCpuAbi(androidPackage);
        if ((128 & j) != 0) {
            appInfoWithoutState.metaData = androidPackage.getMetaData();
        }
        if ((j & 1024) != 0) {
            java.util.List<java.lang.String> usesLibraries = androidPackage.getUsesLibraries();
            appInfoWithoutState.sharedLibraryFiles = usesLibraries.isEmpty() ? null : (java.lang.String[]) usesLibraries.toArray(new java.lang.String[0]);
        }
        return appInfoWithoutState;
    }

    private static android.content.pm.ActivityInfo generateActivityInfo(com.android.internal.pm.pkg.component.ParsedActivity parsedActivity, long j, android.content.pm.ApplicationInfo applicationInfo) {
        if (parsedActivity == null) {
            return null;
        }
        android.content.pm.ActivityInfo activityInfo = new android.content.pm.ActivityInfo();
        activityInfo.targetActivity = parsedActivity.getTargetActivity();
        activityInfo.processName = parsedActivity.getProcessName();
        activityInfo.exported = parsedActivity.isExported();
        activityInfo.theme = parsedActivity.getTheme();
        activityInfo.uiOptions = parsedActivity.getUiOptions();
        activityInfo.parentActivityName = parsedActivity.getParentActivityName();
        activityInfo.permission = parsedActivity.getPermission();
        activityInfo.taskAffinity = parsedActivity.getTaskAffinity();
        activityInfo.flags = parsedActivity.getFlags();
        activityInfo.privateFlags = parsedActivity.getPrivateFlags();
        activityInfo.launchMode = parsedActivity.getLaunchMode();
        activityInfo.documentLaunchMode = parsedActivity.getDocumentLaunchMode();
        activityInfo.maxRecents = parsedActivity.getMaxRecents();
        activityInfo.configChanges = parsedActivity.getConfigChanges();
        activityInfo.softInputMode = parsedActivity.getSoftInputMode();
        activityInfo.persistableMode = parsedActivity.getPersistableMode();
        activityInfo.lockTaskLaunchMode = parsedActivity.getLockTaskLaunchMode();
        activityInfo.screenOrientation = parsedActivity.getScreenOrientation();
        activityInfo.resizeMode = parsedActivity.getResizeMode();
        activityInfo.setMaxAspectRatio(parsedActivity.getMaxAspectRatio());
        activityInfo.setMinAspectRatio(parsedActivity.getMinAspectRatio());
        activityInfo.supportsSizeChanges = parsedActivity.isSupportsSizeChanges();
        activityInfo.requestedVrComponent = parsedActivity.getRequestedVrComponent();
        activityInfo.rotationAnimation = parsedActivity.getRotationAnimation();
        activityInfo.colorMode = parsedActivity.getColorMode();
        activityInfo.windowLayout = parsedActivity.getWindowLayout();
        activityInfo.attributionTags = parsedActivity.getAttributionTags();
        if ((j & 128) != 0) {
            android.os.Bundle metaData = parsedActivity.getMetaData();
            activityInfo.metaData = metaData.isEmpty() ? null : metaData;
        } else {
            activityInfo.metaData = null;
        }
        activityInfo.applicationInfo = applicationInfo;
        activityInfo.requiredDisplayCategory = parsedActivity.getRequiredDisplayCategory();
        activityInfo.setKnownActivityEmbeddingCerts(parsedActivity.getKnownActivityEmbeddingCerts());
        assignFieldsComponentInfoParsedMainComponent(activityInfo, parsedActivity);
        return activityInfo;
    }

    private static android.content.pm.ServiceInfo generateServiceInfo(com.android.internal.pm.pkg.component.ParsedService parsedService, long j, android.content.pm.ApplicationInfo applicationInfo) {
        if (parsedService == null) {
            return null;
        }
        android.content.pm.ServiceInfo serviceInfo = new android.content.pm.ServiceInfo();
        serviceInfo.exported = parsedService.isExported();
        serviceInfo.flags = parsedService.getFlags();
        serviceInfo.permission = parsedService.getPermission();
        serviceInfo.processName = parsedService.getProcessName();
        serviceInfo.mForegroundServiceType = parsedService.getForegroundServiceType();
        serviceInfo.applicationInfo = applicationInfo;
        if ((j & 128) != 0) {
            android.os.Bundle metaData = parsedService.getMetaData();
            serviceInfo.metaData = metaData.isEmpty() ? null : metaData;
        }
        assignFieldsComponentInfoParsedMainComponent(serviceInfo, parsedService);
        return serviceInfo;
    }

    private static android.content.pm.ProviderInfo generateProviderInfo(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.internal.pm.pkg.component.ParsedProvider parsedProvider, long j, android.content.pm.ApplicationInfo applicationInfo, int i) {
        if (parsedProvider == null) {
            return null;
        }
        if (!androidPackage.getPackageName().equals(applicationInfo.packageName)) {
            android.util.Slog.wtf("PackageParsing", "AppInfo's package name is different. Expected=" + androidPackage.getPackageName() + " actual=" + applicationInfo.packageName);
            applicationInfo = generateApplicationInfo(androidPackage, j, i);
        }
        android.content.pm.ProviderInfo providerInfo = new android.content.pm.ProviderInfo();
        providerInfo.exported = parsedProvider.isExported();
        providerInfo.flags = parsedProvider.getFlags();
        providerInfo.processName = parsedProvider.getProcessName();
        providerInfo.authority = parsedProvider.getAuthority();
        providerInfo.isSyncable = parsedProvider.isSyncable();
        providerInfo.readPermission = parsedProvider.getReadPermission();
        providerInfo.writePermission = parsedProvider.getWritePermission();
        providerInfo.grantUriPermissions = parsedProvider.isGrantUriPermissions();
        providerInfo.forceUriPermissions = parsedProvider.isForceUriPermissions();
        providerInfo.multiprocess = parsedProvider.isMultiProcess();
        providerInfo.initOrder = parsedProvider.getInitOrder();
        providerInfo.uriPermissionPatterns = (android.os.PatternMatcher[]) parsedProvider.getUriPermissionPatterns().toArray(new android.os.PatternMatcher[0]);
        providerInfo.pathPermissions = (android.content.pm.PathPermission[]) parsedProvider.getPathPermissions().toArray(new android.content.pm.PathPermission[0]);
        if ((2048 & j) == 0) {
            providerInfo.uriPermissionPatterns = null;
        }
        if ((j & 128) != 0) {
            android.os.Bundle metaData = parsedProvider.getMetaData();
            providerInfo.metaData = metaData.isEmpty() ? null : metaData;
        }
        providerInfo.applicationInfo = applicationInfo;
        assignFieldsComponentInfoParsedMainComponent(providerInfo, parsedProvider);
        return providerInfo;
    }

    private static android.content.pm.InstrumentationInfo generateInstrumentationInfo(com.android.internal.pm.pkg.component.ParsedInstrumentation parsedInstrumentation, com.android.server.pm.pkg.AndroidPackage androidPackage, long j, int i) {
        if (parsedInstrumentation == null) {
            return null;
        }
        android.content.pm.InstrumentationInfo instrumentationInfo = new android.content.pm.InstrumentationInfo();
        instrumentationInfo.targetPackage = parsedInstrumentation.getTargetPackage();
        instrumentationInfo.targetProcesses = parsedInstrumentation.getTargetProcesses();
        instrumentationInfo.handleProfiling = parsedInstrumentation.isHandleProfiling();
        instrumentationInfo.functionalTest = parsedInstrumentation.isFunctionalTest();
        instrumentationInfo.sourceDir = androidPackage.getBaseApkPath();
        instrumentationInfo.publicSourceDir = androidPackage.getBaseApkPath();
        instrumentationInfo.splitNames = androidPackage.getSplitNames();
        instrumentationInfo.splitSourceDirs = androidPackage.getSplitCodePaths().length == 0 ? null : androidPackage.getSplitCodePaths();
        instrumentationInfo.splitPublicSourceDirs = androidPackage.getSplitCodePaths().length == 0 ? null : androidPackage.getSplitCodePaths();
        instrumentationInfo.splitDependencies = androidPackage.getSplitDependencies().size() == 0 ? null : androidPackage.getSplitDependencies();
        initForUser(instrumentationInfo, androidPackage, i);
        instrumentationInfo.primaryCpuAbi = com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.getRawPrimaryCpuAbi(androidPackage);
        instrumentationInfo.secondaryCpuAbi = com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.getRawSecondaryCpuAbi(androidPackage);
        instrumentationInfo.nativeLibraryDir = androidPackage.getNativeLibraryDir();
        instrumentationInfo.secondaryNativeLibraryDir = androidPackage.getSecondaryNativeLibraryDir();
        assignFieldsPackageItemInfoParsedComponent(instrumentationInfo, parsedInstrumentation);
        if ((j & 128) == 0) {
            instrumentationInfo.metaData = null;
        } else {
            android.os.Bundle metaData = parsedInstrumentation.getMetaData();
            instrumentationInfo.metaData = metaData.isEmpty() ? null : metaData;
        }
        return instrumentationInfo;
    }

    private static android.content.pm.PermissionInfo generatePermissionInfo(com.android.internal.pm.pkg.component.ParsedPermission parsedPermission, long j) {
        if (parsedPermission == null) {
            return null;
        }
        android.content.pm.PermissionInfo permissionInfo = new android.content.pm.PermissionInfo(parsedPermission.getBackgroundPermission());
        assignFieldsPackageItemInfoParsedComponent(permissionInfo, parsedPermission);
        permissionInfo.group = parsedPermission.getGroup();
        permissionInfo.requestRes = parsedPermission.getRequestRes();
        permissionInfo.protectionLevel = parsedPermission.getProtectionLevel();
        permissionInfo.descriptionRes = parsedPermission.getDescriptionRes();
        permissionInfo.flags = parsedPermission.getFlags();
        permissionInfo.knownCerts = parsedPermission.getKnownCerts();
        if ((j & 128) == 0) {
            permissionInfo.metaData = null;
        } else {
            android.os.Bundle metaData = parsedPermission.getMetaData();
            permissionInfo.metaData = metaData.isEmpty() ? null : metaData;
        }
        return permissionInfo;
    }

    private static void assignFieldsComponentInfoParsedMainComponent(android.content.pm.ComponentInfo componentInfo, com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent) {
        assignFieldsPackageItemInfoParsedComponent(componentInfo, parsedMainComponent);
        componentInfo.descriptionRes = parsedMainComponent.getDescriptionRes();
        componentInfo.directBootAware = parsedMainComponent.isDirectBootAware();
        componentInfo.enabled = parsedMainComponent.isEnabled();
        componentInfo.splitName = parsedMainComponent.getSplitName();
        componentInfo.attributionTags = parsedMainComponent.getAttributionTags();
        componentInfo.nonLocalizedLabel = parsedMainComponent.getNonLocalizedLabel();
        componentInfo.icon = parsedMainComponent.getIcon();
    }

    private static void assignFieldsPackageItemInfoParsedComponent(android.content.pm.PackageItemInfo packageItemInfo, com.android.internal.pm.pkg.component.ParsedComponent parsedComponent) {
        packageItemInfo.nonLocalizedLabel = com.android.internal.pm.pkg.component.ComponentParseUtils.getNonLocalizedLabel(parsedComponent);
        packageItemInfo.icon = com.android.internal.pm.pkg.component.ComponentParseUtils.getIcon(parsedComponent);
        packageItemInfo.banner = parsedComponent.getBanner();
        packageItemInfo.labelRes = parsedComponent.getLabelRes();
        packageItemInfo.logo = parsedComponent.getLogo();
        packageItemInfo.name = parsedComponent.getName();
        packageItemInfo.packageName = parsedComponent.getPackageName();
    }

    private static void initForUser(android.content.pm.ApplicationInfo applicationInfo, com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        com.android.internal.pm.parsing.pkg.PackageImpl packageImpl = (com.android.internal.pm.parsing.pkg.PackageImpl) androidPackage;
        java.lang.String packageName = androidPackage.getPackageName();
        applicationInfo.uid = android.os.UserHandle.getUid(i, android.os.UserHandle.getAppId(androidPackage.getUid()));
        java.lang.String baseAppDataCredentialProtectedDirForSystemUser = packageImpl.getBaseAppDataCredentialProtectedDirForSystemUser();
        java.lang.String baseAppDataDeviceProtectedDirForSystemUser = packageImpl.getBaseAppDataDeviceProtectedDirForSystemUser();
        if (baseAppDataCredentialProtectedDirForSystemUser != null && baseAppDataDeviceProtectedDirForSystemUser != null) {
            if (i == 0) {
                applicationInfo.credentialProtectedDataDir = baseAppDataCredentialProtectedDirForSystemUser + packageName;
                applicationInfo.deviceProtectedDataDir = baseAppDataDeviceProtectedDirForSystemUser + packageName;
            } else {
                java.lang.String valueOf = java.lang.String.valueOf(i);
                int length = baseAppDataCredentialProtectedDirForSystemUser.length();
                applicationInfo.credentialProtectedDataDir = new java.lang.StringBuilder(baseAppDataCredentialProtectedDirForSystemUser).replace(length - 2, length - 1, valueOf).append(packageName).toString();
                int length2 = baseAppDataDeviceProtectedDirForSystemUser.length();
                applicationInfo.deviceProtectedDataDir = new java.lang.StringBuilder(baseAppDataDeviceProtectedDirForSystemUser).replace(length2 - 2, length2 - 1, valueOf).append(packageName).toString();
            }
        }
        if (androidPackage.isDefaultToDeviceProtectedStorage()) {
            applicationInfo.dataDir = applicationInfo.deviceProtectedDataDir;
        } else {
            applicationInfo.dataDir = applicationInfo.credentialProtectedDataDir;
        }
    }

    private static void initForUser(android.content.pm.InstrumentationInfo instrumentationInfo, com.android.server.pm.pkg.AndroidPackage androidPackage, int i) {
        com.android.internal.pm.parsing.pkg.PackageImpl packageImpl = (com.android.internal.pm.parsing.pkg.PackageImpl) androidPackage;
        java.lang.String packageName = androidPackage.getPackageName();
        java.lang.String baseAppDataCredentialProtectedDirForSystemUser = packageImpl.getBaseAppDataCredentialProtectedDirForSystemUser();
        java.lang.String baseAppDataDeviceProtectedDirForSystemUser = packageImpl.getBaseAppDataDeviceProtectedDirForSystemUser();
        if (baseAppDataCredentialProtectedDirForSystemUser != null && baseAppDataDeviceProtectedDirForSystemUser != null) {
            if (i == 0) {
                instrumentationInfo.credentialProtectedDataDir = baseAppDataCredentialProtectedDirForSystemUser + packageName;
                instrumentationInfo.deviceProtectedDataDir = baseAppDataDeviceProtectedDirForSystemUser + packageName;
            } else {
                java.lang.String valueOf = java.lang.String.valueOf(i);
                int length = baseAppDataCredentialProtectedDirForSystemUser.length();
                instrumentationInfo.credentialProtectedDataDir = new java.lang.StringBuilder(baseAppDataCredentialProtectedDirForSystemUser).replace(length - 2, length - 1, valueOf).append(packageName).toString();
                int length2 = baseAppDataDeviceProtectedDirForSystemUser.length();
                instrumentationInfo.deviceProtectedDataDir = new java.lang.StringBuilder(baseAppDataDeviceProtectedDirForSystemUser).replace(length2 - 2, length2 - 1, valueOf).append(packageName).toString();
            }
        }
        if (androidPackage.isDefaultToDeviceProtectedStorage()) {
            instrumentationInfo.dataDir = instrumentationInfo.deviceProtectedDataDir;
        } else {
            instrumentationInfo.dataDir = instrumentationInfo.credentialProtectedDataDir;
        }
    }

    private static boolean isMatch(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, long j) {
        boolean isSystem = ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).isSystem();
        if ((1048576 & j) == 0 || isSystem) {
            return reportIfDebug((((262144 & j) > 0L ? 1 : ((262144 & j) == 0L ? 0 : -1)) != 0 && !z) || (((524288 & j) > 0L ? 1 : ((524288 & j) == 0L ? 0 : -1)) != 0 && z), j);
        }
        return reportIfDebug(false, j);
    }

    private static boolean reportIfDebug(boolean z, long j) {
        return z;
    }
}
