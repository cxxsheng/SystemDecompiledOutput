package com.android.server.pm.parsing;

/* loaded from: classes2.dex */
public class PackageInfoUtils {
    private static final java.lang.String SYSTEM_DATA_PATH = android.os.Environment.getDataDirectoryPath() + java.io.File.separator + "system";
    private static final java.lang.String TAG = "PackageParsing";

    @android.annotation.Nullable
    public static android.content.pm.PackageInfo generate(com.android.server.pm.pkg.AndroidPackage androidPackage, int[] iArr, long j, long j2, long j3, java.util.Set<java.lang.String> set, java.util.Set<java.lang.String> set2, com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return generateWithComponents(androidPackage, iArr, j, j2, j3, set, set2, packageUserStateInternal, i, packageStateInternal);
    }

    private static android.content.pm.PackageInfo generateWithComponents(com.android.server.pm.pkg.AndroidPackage androidPackage, int[] iArr, long j, long j2, long j3, java.util.Set<java.lang.String> set, java.util.Set<java.lang.String> set2, com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        int size;
        int size2;
        int size3;
        int size4;
        int size5;
        int i2;
        int i3;
        android.content.pm.ActivityInfo[] activityInfoArr;
        int i4;
        android.content.pm.ApplicationInfo generateApplicationInfo = generateApplicationInfo(androidPackage, j, packageUserStateInternal, i, packageStateInternal);
        if (generateApplicationInfo == null) {
            return null;
        }
        android.content.pm.PackageInfo packageInfo = new android.content.pm.PackageInfo();
        packageInfo.packageName = androidPackage.getPackageName();
        packageInfo.splitNames = androidPackage.getSplitNames();
        com.android.server.pm.parsing.pkg.AndroidPackageUtils.fillVersionCodes(androidPackage, packageInfo);
        packageInfo.baseRevisionCode = androidPackage.getBaseRevisionCode();
        packageInfo.splitRevisionCodes = androidPackage.getSplitRevisionCodes();
        packageInfo.versionName = androidPackage.getVersionName();
        packageInfo.sharedUserId = androidPackage.getSharedUserId();
        packageInfo.sharedUserLabel = androidPackage.getSharedUserLabelResourceId();
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
        packageInfo.firstInstallTime = j2;
        packageInfo.lastUpdateTime = j3;
        if (packageUserStateInternal.getArchiveState() != null) {
            packageInfo.setArchiveTimeMillis(packageUserStateInternal.getArchiveState().getArchiveTimeMillis());
        }
        if ((256 & j) != 0) {
            packageInfo.gids = iArr;
        }
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
                for (int i5 = 0; i5 < size9; i5++) {
                    com.android.internal.pm.pkg.component.ParsedPermission parsedPermission = (com.android.internal.pm.pkg.component.ParsedPermission) androidPackage.getPermissions().get(i5);
                    android.content.pm.PermissionInfo generatePermissionInfo = generatePermissionInfo(parsedPermission, j);
                    if (set.contains(parsedPermission.getName())) {
                        generatePermissionInfo.flags |= 1073741824;
                    }
                    packageInfo.permissions[i5] = generatePermissionInfo;
                }
            }
            java.util.List usesPermissions = androidPackage.getUsesPermissions();
            int size10 = usesPermissions.size();
            if (size10 > 0) {
                packageInfo.requestedPermissions = new java.lang.String[size10];
                packageInfo.requestedPermissionsFlags = new int[size10];
                for (int i6 = 0; i6 < size10; i6++) {
                    com.android.internal.pm.pkg.component.ParsedUsesPermission parsedUsesPermission = (com.android.internal.pm.pkg.component.ParsedUsesPermission) usesPermissions.get(i6);
                    packageInfo.requestedPermissions[i6] = parsedUsesPermission.getName();
                    int[] iArr2 = packageInfo.requestedPermissionsFlags;
                    iArr2[i6] = iArr2[i6] | 1;
                    if (set2 != null && set2.contains(parsedUsesPermission.getName())) {
                        int[] iArr3 = packageInfo.requestedPermissionsFlags;
                        iArr3[i6] = iArr3[i6] | 2;
                    }
                    if ((parsedUsesPermission.getUsesPermissionFlags() & 65536) != 0) {
                        int[] iArr4 = packageInfo.requestedPermissionsFlags;
                        iArr4[i6] = 65536 | iArr4[i6];
                    }
                    if (androidPackage.getImplicitPermissions().contains(packageInfo.requestedPermissions[i6])) {
                        int[] iArr5 = packageInfo.requestedPermissionsFlags;
                        iArr5[i6] = iArr5[i6] | 4;
                    }
                }
            }
        }
        if ((2147483648L & j) != 0) {
            int size11 = com.android.internal.util.ArrayUtils.size(androidPackage.getAttributions());
            if (size11 > 0) {
                packageInfo.attributions = new android.content.pm.Attribution[size11];
                for (int i7 = 0; i7 < size11; i7++) {
                    com.android.internal.pm.pkg.component.ParsedAttribution parsedAttribution = (com.android.internal.pm.pkg.component.ParsedAttribution) androidPackage.getAttributions().get(i7);
                    if (parsedAttribution != null) {
                        packageInfo.attributions[i7] = new android.content.pm.Attribution(parsedAttribution.getTag(), parsedAttribution.getLabel());
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
        packageInfo.signatures = getDeprecatedSignatures(signingDetails, j);
        if ((134217728 & j) != 0) {
            if (signingDetails != android.content.pm.SigningDetails.UNKNOWN) {
                packageInfo.signingInfo = new android.content.pm.SigningInfo(signingDetails);
            } else {
                packageInfo.signingInfo = null;
            }
        }
        packageInfo.isStub = androidPackage.isStub();
        packageInfo.coreApp = androidPackage.isCoreApp();
        packageInfo.isApex = androidPackage.isApex();
        if (!packageStateInternal.hasSharedUser()) {
            packageInfo.sharedUserId = null;
            packageInfo.sharedUserLabel = 0;
        }
        if ((1 & j) != 0 && (size5 = androidPackage.getActivities().size()) > 0) {
            long j4 = j | 8589934592L;
            android.content.pm.ActivityInfo[] activityInfoArr2 = new android.content.pm.ActivityInfo[size5];
            int i8 = 0;
            int i9 = 0;
            while (i9 < size5) {
                com.android.internal.pm.pkg.component.ParsedActivity parsedActivity = (com.android.internal.pm.pkg.component.ParsedActivity) androidPackage.getActivities().get(i9);
                if (!com.android.server.pm.pkg.PackageUserStateUtils.isMatch(packageUserStateInternal, packageStateInternal.isSystem(), androidPackage.isEnabled(), parsedActivity.isEnabled(), parsedActivity.isDirectBootAware(), parsedActivity.getName(), j4)) {
                    i2 = i8;
                    i3 = i9;
                    activityInfoArr = activityInfoArr2;
                    i4 = size5;
                } else if (android.content.pm.PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME.equals(parsedActivity.getName())) {
                    i2 = i8;
                    i3 = i9;
                    activityInfoArr = activityInfoArr2;
                    i4 = size5;
                } else {
                    i3 = i9;
                    activityInfoArr = activityInfoArr2;
                    i4 = size5;
                    activityInfoArr[i8] = generateActivityInfo(androidPackage, parsedActivity, j4, packageUserStateInternal, generateApplicationInfo, i, packageStateInternal);
                    i8++;
                    i9 = i3 + 1;
                    activityInfoArr2 = activityInfoArr;
                    size5 = i4;
                }
                i8 = i2;
                i9 = i3 + 1;
                activityInfoArr2 = activityInfoArr;
                size5 = i4;
            }
            packageInfo.activities = (android.content.pm.ActivityInfo[]) com.android.internal.util.ArrayUtils.trimToSize(activityInfoArr2, i8);
        }
        if ((2 & j) != 0 && (size4 = androidPackage.getReceivers().size()) > 0) {
            android.content.pm.ActivityInfo[] activityInfoArr3 = new android.content.pm.ActivityInfo[size4];
            int i10 = 0;
            for (int i11 = 0; i11 < size4; i11++) {
                com.android.internal.pm.pkg.component.ParsedActivity parsedActivity2 = (com.android.internal.pm.pkg.component.ParsedActivity) androidPackage.getReceivers().get(i11);
                int i12 = i10;
                if (!com.android.server.pm.pkg.PackageUserStateUtils.isMatch(packageUserStateInternal, packageStateInternal.isSystem(), androidPackage.isEnabled(), parsedActivity2.isEnabled(), parsedActivity2.isDirectBootAware(), parsedActivity2.getName(), j)) {
                    i10 = i12;
                } else {
                    activityInfoArr3[i12] = generateActivityInfo(androidPackage, parsedActivity2, j, packageUserStateInternal, generateApplicationInfo, i, packageStateInternal);
                    i10 = i12 + 1;
                }
            }
            packageInfo.receivers = (android.content.pm.ActivityInfo[]) com.android.internal.util.ArrayUtils.trimToSize(activityInfoArr3, i10);
        }
        if ((4 & j) != 0 && (size3 = androidPackage.getServices().size()) > 0) {
            android.content.pm.ServiceInfo[] serviceInfoArr = new android.content.pm.ServiceInfo[size3];
            int i13 = 0;
            for (int i14 = 0; i14 < size3; i14++) {
                com.android.internal.pm.pkg.component.ParsedService parsedService = (com.android.internal.pm.pkg.component.ParsedService) androidPackage.getServices().get(i14);
                if (com.android.server.pm.pkg.PackageUserStateUtils.isMatch(packageUserStateInternal, packageStateInternal.isSystem(), androidPackage.isEnabled(), parsedService.isEnabled(), parsedService.isDirectBootAware(), parsedService.getName(), j)) {
                    serviceInfoArr[i13] = generateServiceInfo(androidPackage, parsedService, j, packageUserStateInternal, generateApplicationInfo, i, packageStateInternal);
                    i13++;
                }
            }
            packageInfo.services = (android.content.pm.ServiceInfo[]) com.android.internal.util.ArrayUtils.trimToSize(serviceInfoArr, i13);
        }
        if ((8 & j) != 0 && (size2 = androidPackage.getProviders().size()) > 0) {
            android.content.pm.ProviderInfo[] providerInfoArr = new android.content.pm.ProviderInfo[size2];
            int i15 = 0;
            for (int i16 = 0; i16 < size2; i16++) {
                com.android.internal.pm.pkg.component.ParsedProvider parsedProvider = (com.android.internal.pm.pkg.component.ParsedProvider) androidPackage.getProviders().get(i16);
                if (com.android.server.pm.pkg.PackageUserStateUtils.isMatch(packageUserStateInternal, packageStateInternal.isSystem(), androidPackage.isEnabled(), parsedProvider.isEnabled(), parsedProvider.isDirectBootAware(), parsedProvider.getName(), j)) {
                    providerInfoArr[i15] = generateProviderInfo(androidPackage, parsedProvider, j, packageUserStateInternal, generateApplicationInfo, i, packageStateInternal);
                    i15++;
                }
            }
            packageInfo.providers = (android.content.pm.ProviderInfo[]) com.android.internal.util.ArrayUtils.trimToSize(providerInfoArr, i15);
        }
        if ((16 & j) != 0 && (size = androidPackage.getInstrumentations().size()) > 0) {
            packageInfo.instrumentation = new android.content.pm.InstrumentationInfo[size];
            for (int i17 = 0; i17 < size; i17++) {
                packageInfo.instrumentation[i17] = generateInstrumentationInfo((com.android.internal.pm.pkg.component.ParsedInstrumentation) androidPackage.getInstrumentations().get(i17), androidPackage, j, packageUserStateInternal, i, packageStateInternal);
            }
        }
        return packageInfo;
    }

    public static android.content.pm.Signature[] getDeprecatedSignatures(android.content.pm.SigningDetails signingDetails, long j) {
        if ((j & 64) == 0) {
            return null;
        }
        if (signingDetails.hasPastSigningCertificates()) {
            return new android.content.pm.Signature[]{signingDetails.getPastSigningCertificates()[0]};
        }
        if (!signingDetails.hasSignatures()) {
            return null;
        }
        int length = signingDetails.getSignatures().length;
        android.content.pm.Signature[] signatureArr = new android.content.pm.Signature[length];
        java.lang.System.arraycopy(signingDetails.getSignatures(), 0, signatureArr, 0, length);
        return signatureArr;
    }

    private static void updateApplicationInfo(android.content.pm.ApplicationInfo applicationInfo, long j, com.android.server.pm.pkg.PackageUserState packageUserState) {
        if ((128 & j) == 0) {
            applicationInfo.metaData = null;
        }
        if ((1024 & j) == 0) {
            applicationInfo.sharedLibraryFiles = null;
            applicationInfo.sharedLibraryInfos = null;
        }
        if (!com.android.internal.pm.pkg.parsing.ParsingPackageUtils.sCompatibilityModeEnabled) {
            applicationInfo.disableCompatibilityMode();
        }
        applicationInfo.flags |= flag(packageUserState.isStopped(), 2097152) | flag(packageUserState.isInstalled(), 8388608) | flag(packageUserState.isSuspended(), 1073741824);
        applicationInfo.privateFlags |= flag(packageUserState.isInstantApp(), 128) | flag(packageUserState.isVirtualPreload(), 65536) | flag(packageUserState.isHidden(), 1);
        if (packageUserState.getEnabledState() == 1) {
            applicationInfo.enabled = true;
        } else if (packageUserState.getEnabledState() == 4) {
            applicationInfo.enabled = (j & 32768) != 0;
        } else if (packageUserState.getEnabledState() == 2 || packageUserState.getEnabledState() == 3) {
            applicationInfo.enabled = false;
        }
        applicationInfo.enabledSetting = packageUserState.getEnabledState();
        if (applicationInfo.category == -1) {
            applicationInfo.category = android.content.pm.FallbackCategoryProvider.getFallbackCategory(applicationInfo.packageName);
        }
        applicationInfo.seInfoUser = com.android.server.pm.pkg.SELinuxUtil.getSeinfoUser(packageUserState);
        android.content.pm.overlay.OverlayPaths allOverlayPaths = packageUserState.getAllOverlayPaths();
        if (allOverlayPaths != null) {
            applicationInfo.resourceDirs = (java.lang.String[]) allOverlayPaths.getResourceDirs().toArray(new java.lang.String[0]);
            applicationInfo.overlayPaths = (java.lang.String[]) allOverlayPaths.getOverlayPaths().toArray(new java.lang.String[0]);
        }
        applicationInfo.isArchived = com.android.server.pm.PackageArchiver.isArchived(packageUserState);
        if (applicationInfo.isArchived) {
            applicationInfo.nonLocalizedLabel = packageUserState.getArchiveState().getActivityInfos().get(0).getTitle();
        }
        if (!packageUserState.isInstalled() && !packageUserState.dataExists() && android.content.pm.Flags.nullableDataDir()) {
            applicationInfo.dataDir = null;
        }
    }

    @android.annotation.Nullable
    public static android.content.pm.ApplicationInfo generateDelegateApplicationInfo(@android.annotation.Nullable android.content.pm.ApplicationInfo applicationInfo, long j, @android.annotation.NonNull com.android.server.pm.pkg.PackageUserState packageUserState, int i) {
        if (applicationInfo == null || !checkUseInstalledOrHidden(j, packageUserState, applicationInfo)) {
            return null;
        }
        android.content.pm.ApplicationInfo applicationInfo2 = new android.content.pm.ApplicationInfo(applicationInfo);
        applicationInfo2.initForUser(i);
        applicationInfo2.icon = (!com.android.internal.pm.pkg.parsing.ParsingPackageUtils.sUseRoundIcon || applicationInfo2.roundIconRes == 0) ? applicationInfo2.iconRes : applicationInfo2.roundIconRes;
        updateApplicationInfo(applicationInfo2, j, packageUserState);
        return applicationInfo2;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public static android.content.pm.ApplicationInfo generateApplicationInfo(com.android.server.pm.pkg.AndroidPackage androidPackage, long j, @android.annotation.NonNull com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        java.lang.String[] strArr;
        int indexOf;
        java.util.ArrayList arrayList = null;
        if (androidPackage == null || !checkUseInstalledOrHidden(androidPackage, packageStateInternal, packageUserStateInternal, j) || !com.android.server.pm.parsing.pkg.AndroidPackageUtils.isMatchForSystemOnly(packageStateInternal, j)) {
            return null;
        }
        android.content.pm.ApplicationInfo generateAppInfoWithoutState = com.android.server.pm.parsing.pkg.AndroidPackageUtils.generateAppInfoWithoutState(androidPackage);
        updateApplicationInfo(generateAppInfoWithoutState, j, packageUserStateInternal);
        initForUser(generateAppInfoWithoutState, androidPackage, i, packageUserStateInternal);
        com.android.server.pm.pkg.PackageStateUnserialized transientState = packageStateInternal.getTransientState();
        generateAppInfoWithoutState.hiddenUntilInstalled = transientState.isHiddenUntilInstalled();
        java.util.List<java.lang.String> usesLibraryFiles = transientState.getUsesLibraryFiles();
        java.util.List<com.android.server.pm.pkg.SharedLibraryWrapper> usesLibraryInfos = transientState.getUsesLibraryInfos();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (int i2 = 0; i2 < usesLibraryInfos.size(); i2++) {
            arrayList2.add(usesLibraryInfos.get(i2).getInfo());
        }
        if (!usesLibraryFiles.isEmpty()) {
            strArr = (java.lang.String[]) usesLibraryFiles.toArray(new java.lang.String[0]);
        } else {
            strArr = null;
        }
        generateAppInfoWithoutState.sharedLibraryFiles = strArr;
        if (!android.content.pm.Flags.sdkLibIndependence()) {
            if (arrayList2.isEmpty()) {
                arrayList2 = null;
            }
            generateAppInfoWithoutState.sharedLibraryInfos = arrayList2;
            generateAppInfoWithoutState.optionalSharedLibraryInfos = null;
        } else {
            generateAppInfoWithoutState.sharedLibraryInfos = arrayList2.isEmpty() ? null : arrayList2;
            java.lang.String[] usesSdkLibraries = packageStateInternal.getUsesSdkLibraries();
            boolean[] usesSdkLibrariesOptional = packageStateInternal.getUsesSdkLibrariesOptional();
            if (!com.android.internal.util.ArrayUtils.isEmpty(usesSdkLibrariesOptional) && !com.android.internal.util.ArrayUtils.isEmpty(usesSdkLibraries) && usesSdkLibraries.length == usesSdkLibrariesOptional.length) {
                java.util.Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    android.content.pm.SharedLibraryInfo sharedLibraryInfo = (android.content.pm.SharedLibraryInfo) it.next();
                    if (sharedLibraryInfo.getType() == 3 && (indexOf = com.android.internal.util.ArrayUtils.indexOf(usesSdkLibraries, sharedLibraryInfo.getName())) >= 0 && usesSdkLibrariesOptional[indexOf]) {
                        if (arrayList == null) {
                            arrayList = new java.util.ArrayList();
                        }
                        arrayList.add(sharedLibraryInfo);
                    }
                }
            }
            generateAppInfoWithoutState.optionalSharedLibraryInfos = arrayList;
        }
        if (generateAppInfoWithoutState.category == -1) {
            generateAppInfoWithoutState.category = packageStateInternal.getCategoryOverride();
        }
        generateAppInfoWithoutState.seInfo = packageStateInternal.getSeInfo();
        generateAppInfoWithoutState.primaryCpuAbi = packageStateInternal.getPrimaryCpuAbi();
        generateAppInfoWithoutState.secondaryCpuAbi = packageStateInternal.getSecondaryCpuAbi();
        generateAppInfoWithoutState.flags |= appInfoFlags(generateAppInfoWithoutState.flags, packageStateInternal);
        generateAppInfoWithoutState.privateFlags |= appInfoPrivateFlags(generateAppInfoWithoutState.privateFlags, packageStateInternal);
        generateAppInfoWithoutState.privateFlagsExt |= appInfoPrivateFlagsExt(generateAppInfoWithoutState.privateFlagsExt, packageStateInternal);
        return generateAppInfoWithoutState;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public static android.content.pm.ActivityInfo generateActivityInfo(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.internal.pm.pkg.component.ParsedActivity parsedActivity, long j, @android.annotation.NonNull com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return generateActivityInfo(androidPackage, parsedActivity, j, packageUserStateInternal, null, i, packageStateInternal);
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public static android.content.pm.ActivityInfo generateActivityInfo(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.internal.pm.pkg.component.ParsedActivity parsedActivity, long j, @android.annotation.NonNull com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal, @android.annotation.Nullable android.content.pm.ApplicationInfo applicationInfo, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        if (parsedActivity == null || !checkUseInstalledOrHidden(androidPackage, packageStateInternal, packageUserStateInternal, j)) {
            return null;
        }
        if (applicationInfo == null) {
            applicationInfo = generateApplicationInfo(androidPackage, j, packageUserStateInternal, i, packageStateInternal);
        }
        if (applicationInfo == null) {
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
        activityInfo.requireContentUriPermissionFromCaller = parsedActivity.getRequireContentUriPermissionFromCaller();
        activityInfo.setKnownActivityEmbeddingCerts(parsedActivity.getKnownActivityEmbeddingCerts());
        assignFieldsComponentInfoParsedMainComponent(activityInfo, parsedActivity, packageStateInternal, i);
        return activityInfo;
    }

    @android.annotation.Nullable
    public static android.content.pm.ActivityInfo generateDelegateActivityInfo(@android.annotation.Nullable android.content.pm.ActivityInfo activityInfo, long j, @android.annotation.NonNull com.android.server.pm.pkg.PackageUserState packageUserState, int i) {
        if (activityInfo == null || !checkUseInstalledOrHidden(j, packageUserState, activityInfo.applicationInfo)) {
            return null;
        }
        android.content.pm.ActivityInfo activityInfo2 = new android.content.pm.ActivityInfo(activityInfo);
        activityInfo2.applicationInfo = generateDelegateApplicationInfo(activityInfo2.applicationInfo, j, packageUserState, i);
        return activityInfo2;
    }

    @android.annotation.Nullable
    public static android.content.pm.ServiceInfo generateServiceInfo(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.internal.pm.pkg.component.ParsedService parsedService, long j, com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return generateServiceInfo(androidPackage, parsedService, j, packageUserStateInternal, null, i, packageStateInternal);
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public static android.content.pm.ServiceInfo generateServiceInfo(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.internal.pm.pkg.component.ParsedService parsedService, long j, com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal, @android.annotation.Nullable android.content.pm.ApplicationInfo applicationInfo, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        if (parsedService == null || !checkUseInstalledOrHidden(androidPackage, packageStateInternal, packageUserStateInternal, j)) {
            return null;
        }
        if (applicationInfo == null) {
            applicationInfo = generateApplicationInfo(androidPackage, j, packageUserStateInternal, i, packageStateInternal);
        }
        if (applicationInfo == null) {
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
        assignFieldsComponentInfoParsedMainComponent(serviceInfo, parsedService, packageStateInternal, i);
        return serviceInfo;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    public static android.content.pm.ProviderInfo generateProviderInfo(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.internal.pm.pkg.component.ParsedProvider parsedProvider, long j, com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal, @android.annotation.NonNull android.content.pm.ApplicationInfo applicationInfo, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        if (parsedProvider == null || !checkUseInstalledOrHidden(androidPackage, packageStateInternal, packageUserStateInternal, j)) {
            return null;
        }
        if (applicationInfo == null || !androidPackage.getPackageName().equals(applicationInfo.packageName)) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("AppInfo's package name is different. Expected=");
            sb.append(androidPackage.getPackageName());
            sb.append(" actual=");
            sb.append(applicationInfo == null ? "(null AppInfo)" : applicationInfo.packageName);
            android.util.Slog.wtf(TAG, sb.toString());
            applicationInfo = generateApplicationInfo(androidPackage, j, packageUserStateInternal, i, packageStateInternal);
        }
        if (applicationInfo == null) {
            return null;
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
        assignFieldsComponentInfoParsedMainComponent(providerInfo, parsedProvider, packageStateInternal, i);
        return providerInfo;
    }

    @android.annotation.Nullable
    public static android.content.pm.InstrumentationInfo generateInstrumentationInfo(com.android.internal.pm.pkg.component.ParsedInstrumentation parsedInstrumentation, com.android.server.pm.pkg.AndroidPackage androidPackage, long j, com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        if (parsedInstrumentation == null || !checkUseInstalledOrHidden(androidPackage, packageStateInternal, packageUserStateInternal, j)) {
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
        initForUser(instrumentationInfo, androidPackage, i, packageUserStateInternal);
        instrumentationInfo.primaryCpuAbi = packageStateInternal.getPrimaryCpuAbi();
        instrumentationInfo.secondaryCpuAbi = packageStateInternal.getSecondaryCpuAbi();
        instrumentationInfo.nativeLibraryDir = androidPackage.getNativeLibraryDir();
        instrumentationInfo.secondaryNativeLibraryDir = androidPackage.getSecondaryNativeLibraryDir();
        assignFieldsPackageItemInfoParsedComponent(instrumentationInfo, parsedInstrumentation, packageStateInternal, i);
        if ((j & 128) == 0) {
            instrumentationInfo.metaData = null;
        } else {
            android.os.Bundle metaData = parsedInstrumentation.getMetaData();
            instrumentationInfo.metaData = metaData.isEmpty() ? null : metaData;
        }
        return instrumentationInfo;
    }

    @android.annotation.Nullable
    public static android.content.pm.PermissionInfo generatePermissionInfo(com.android.internal.pm.pkg.component.ParsedPermission parsedPermission, long j) {
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

    @android.annotation.Nullable
    public static android.content.pm.PermissionGroupInfo generatePermissionGroupInfo(com.android.internal.pm.pkg.component.ParsedPermissionGroup parsedPermissionGroup, long j) {
        if (parsedPermissionGroup == null) {
            return null;
        }
        android.content.pm.PermissionGroupInfo permissionGroupInfo = new android.content.pm.PermissionGroupInfo(parsedPermissionGroup.getRequestDetailRes(), parsedPermissionGroup.getBackgroundRequestRes(), parsedPermissionGroup.getBackgroundRequestDetailRes());
        assignFieldsPackageItemInfoParsedComponent(permissionGroupInfo, parsedPermissionGroup);
        permissionGroupInfo.descriptionRes = parsedPermissionGroup.getDescriptionRes();
        permissionGroupInfo.priority = parsedPermissionGroup.getPriority();
        permissionGroupInfo.requestRes = parsedPermissionGroup.getRequestRes();
        permissionGroupInfo.flags = parsedPermissionGroup.getFlags();
        if ((j & 128) == 0) {
            permissionGroupInfo.metaData = null;
        } else {
            android.os.Bundle metaData = parsedPermissionGroup.getMetaData();
            permissionGroupInfo.metaData = metaData.isEmpty() ? null : metaData;
        }
        return permissionGroupInfo;
    }

    @android.annotation.Nullable
    public static android.util.ArrayMap<java.lang.String, android.content.pm.ProcessInfo> generateProcessInfo(java.util.Map<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess> map, long j) {
        if (map == null) {
            return null;
        }
        android.util.ArrayMap<java.lang.String, android.content.pm.ProcessInfo> arrayMap = new android.util.ArrayMap<>(map.size());
        java.util.Iterator<java.lang.String> it = map.keySet().iterator();
        while (it.hasNext()) {
            com.android.internal.pm.pkg.component.ParsedProcess parsedProcess = map.get(it.next());
            arrayMap.put(parsedProcess.getName(), new android.content.pm.ProcessInfo(parsedProcess.getName(), new android.util.ArraySet(parsedProcess.getDeniedPermissions()), parsedProcess.getGwpAsanMode(), parsedProcess.getMemtagMode(), parsedProcess.getNativeHeapZeroInitialized(), parsedProcess.isUseEmbeddedDex()));
        }
        return arrayMap;
    }

    public static boolean checkUseInstalledOrHidden(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal, long j) {
        if ((536870912 & j) == 0 && !packageUserStateInternal.isInstalled() && packageStateInternal.getTransientState().isHiddenUntilInstalled()) {
            return false;
        }
        return com.android.server.pm.pkg.PackageUserStateUtils.isAvailable(packageUserStateInternal, j) || (packageStateInternal.isSystem() && matchUninstalledOrHidden(j));
    }

    private static boolean checkUseInstalledOrHidden(long j, @android.annotation.NonNull com.android.server.pm.pkg.PackageUserState packageUserState, @android.annotation.Nullable android.content.pm.ApplicationInfo applicationInfo) {
        if ((536870912 & j) != 0 || packageUserState.isInstalled() || applicationInfo == null || !applicationInfo.hiddenUntilInstalled) {
            return com.android.server.pm.pkg.PackageUserStateUtils.isAvailable(packageUserState, j) || (applicationInfo != null && applicationInfo.isSystemApp() && matchUninstalledOrHidden(j));
        }
        return false;
    }

    private static boolean matchUninstalledOrHidden(long j) {
        return (j & 4836040704L) != 0;
    }

    private static void assignFieldsComponentInfoParsedMainComponent(@android.annotation.NonNull android.content.pm.ComponentInfo componentInfo, @android.annotation.NonNull com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent) {
        assignFieldsPackageItemInfoParsedComponent(componentInfo, parsedMainComponent);
        componentInfo.descriptionRes = parsedMainComponent.getDescriptionRes();
        componentInfo.directBootAware = parsedMainComponent.isDirectBootAware();
        componentInfo.enabled = parsedMainComponent.isEnabled();
        componentInfo.splitName = parsedMainComponent.getSplitName();
        componentInfo.attributionTags = parsedMainComponent.getAttributionTags();
    }

    private static void assignFieldsPackageItemInfoParsedComponent(@android.annotation.NonNull android.content.pm.PackageItemInfo packageItemInfo, @android.annotation.NonNull com.android.internal.pm.pkg.component.ParsedComponent parsedComponent) {
        packageItemInfo.nonLocalizedLabel = com.android.internal.pm.pkg.component.ComponentParseUtils.getNonLocalizedLabel(parsedComponent);
        packageItemInfo.icon = com.android.internal.pm.pkg.component.ComponentParseUtils.getIcon(parsedComponent);
        packageItemInfo.banner = parsedComponent.getBanner();
        packageItemInfo.labelRes = parsedComponent.getLabelRes();
        packageItemInfo.logo = parsedComponent.getLogo();
        packageItemInfo.name = parsedComponent.getName();
        packageItemInfo.packageName = parsedComponent.getPackageName();
    }

    private static void assignFieldsComponentInfoParsedMainComponent(@android.annotation.NonNull android.content.pm.ComponentInfo componentInfo, @android.annotation.NonNull com.android.internal.pm.pkg.component.ParsedMainComponent parsedMainComponent, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i) {
        assignFieldsComponentInfoParsedMainComponent(componentInfo, parsedMainComponent);
        android.util.Pair<java.lang.CharSequence, java.lang.Integer> nonLocalizedLabelAndIcon = com.android.server.pm.parsing.ParsedComponentStateUtils.getNonLocalizedLabelAndIcon(parsedMainComponent, packageStateInternal, i);
        componentInfo.nonLocalizedLabel = (java.lang.CharSequence) nonLocalizedLabelAndIcon.first;
        componentInfo.icon = ((java.lang.Integer) nonLocalizedLabelAndIcon.second).intValue();
    }

    private static void assignFieldsPackageItemInfoParsedComponent(@android.annotation.NonNull android.content.pm.PackageItemInfo packageItemInfo, @android.annotation.NonNull com.android.internal.pm.pkg.component.ParsedComponent parsedComponent, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i) {
        assignFieldsPackageItemInfoParsedComponent(packageItemInfo, parsedComponent);
        android.util.Pair<java.lang.CharSequence, java.lang.Integer> nonLocalizedLabelAndIcon = com.android.server.pm.parsing.ParsedComponentStateUtils.getNonLocalizedLabelAndIcon(parsedComponent, packageStateInternal, i);
        packageItemInfo.nonLocalizedLabel = (java.lang.CharSequence) nonLocalizedLabelAndIcon.first;
        packageItemInfo.icon = ((java.lang.Integer) nonLocalizedLabelAndIcon.second).intValue();
    }

    private static int flag(boolean z, int i) {
        if (z) {
            return i;
        }
        return 0;
    }

    public static int appInfoFlags(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return appInfoFlags(flag(androidPackage.isFactoryTest(), 16) | flag(androidPackage.isExternalStorage(), 262144) | flag(androidPackage.isHardwareAccelerated(), 536870912) | flag(androidPackage.isBackupAllowed(), 32768) | flag(androidPackage.isKillAfterRestoreAllowed(), 65536) | flag(androidPackage.isRestoreAnyVersion(), 131072) | flag(androidPackage.isFullBackupOnly(), 67108864) | flag(androidPackage.isPersistent(), 8) | flag(androidPackage.isDebuggable(), 2) | flag(androidPackage.isVmSafeMode(), 16384) | flag(androidPackage.isDeclaredHavingCode(), 4) | flag(androidPackage.isTaskReparentingAllowed(), 32) | flag(androidPackage.isClearUserDataAllowed(), 64) | flag(androidPackage.isLargeHeap(), 1048576) | flag(androidPackage.isCleartextTrafficAllowed(), 134217728) | flag(androidPackage.isRtlSupported(), 4194304) | flag(androidPackage.isTestOnly(), 256) | flag(androidPackage.isMultiArch(), Integer.MIN_VALUE) | flag(androidPackage.isExtractNativeLibrariesRequested(), 268435456) | flag(androidPackage.isGame(), 33554432) | flag(androidPackage.isSmallScreensSupported(), 512) | flag(androidPackage.isNormalScreensSupported(), 1024) | flag(androidPackage.isLargeScreensSupported(), 2048) | flag(androidPackage.isExtraLargeScreensSupported(), 524288) | flag(androidPackage.isResizeable(), 4096) | flag(androidPackage.isAnyDensity(), 8192) | flag(com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.isSystem(androidPackage), 1), packageStateInternal);
    }

    public static int appInfoFlags(int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        if (packageStateInternal != null) {
            return i | flag(packageStateInternal.isUpdatedSystemApp(), 128);
        }
        return i;
    }

    public static int appInfoPrivateFlags(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        int flag = flag(androidPackage.isStaticSharedLibrary(), 16384) | flag(androidPackage.isResourceOverlay(), 268435456) | flag(androidPackage.isIsolatedSplitLoading(), 32768) | flag(androidPackage.isHasDomainUrls(), 16) | flag(androidPackage.isProfileableByShell(), 8388608) | flag(androidPackage.isBackupInForeground(), 8192) | flag(androidPackage.isUseEmbeddedDex(), 33554432) | flag(androidPackage.isDefaultToDeviceProtectedStorage(), 32) | flag(androidPackage.isDirectBootAware(), 64) | flag(androidPackage.isPartiallyDirectBootAware(), 256) | flag(androidPackage.isClearUserDataOnFailedRestoreAllowed(), 67108864) | flag(androidPackage.isAllowAudioPlaybackCapture(), 134217728) | flag(androidPackage.isRequestLegacyExternalStorage(), 536870912) | flag(androidPackage.isNonSdkApiRequested(), 4194304) | flag(androidPackage.isUserDataFragile(), 16777216) | flag(androidPackage.isSaveStateDisallowed(), 2) | flag(androidPackage.isResizeableActivityViaSdkVersion(), 4096) | flag(androidPackage.isAllowNativeHeapPointerTagging(), Integer.MIN_VALUE) | flag(com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.isSystemExt(androidPackage), 2097152) | flag(com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.isPrivileged(androidPackage), 8) | flag(com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.isOem(androidPackage), 131072) | flag(com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.isVendor(androidPackage), 262144) | flag(com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.isProduct(androidPackage), 524288) | flag(com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils.isOdm(androidPackage), 1073741824) | flag(androidPackage.isSignedWithPlatformKey(), 1048576);
        java.lang.Boolean resizeableActivity = androidPackage.getResizeableActivity();
        if (resizeableActivity != null) {
            if (resizeableActivity.booleanValue()) {
                flag |= 1024;
            } else {
                flag |= 2048;
            }
        }
        return appInfoPrivateFlags(flag, packageStateInternal);
    }

    public static int appInfoPrivateFlags(int i, @android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        return i;
    }

    public static int appInfoPrivateFlagsExt(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        boolean contains = com.android.server.SystemConfig.getInstance().getHiddenApiWhitelistedApps().contains(androidPackage.getPackageName());
        return appInfoPrivateFlagsExt(flag(androidPackage.isOnBackInvokedCallbackEnabled(), 8) | flag(androidPackage.isProfileable(), 1) | flag(androidPackage.hasRequestForegroundServiceExemption(), 2) | flag(androidPackage.isAttributionsUserVisible(), 4) | flag(contains, 16), packageStateInternal);
    }

    private static int appInfoPrivateFlagsExt(int i, @android.annotation.Nullable com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        if (packageStateInternal != null) {
            return i | flag(packageStateInternal.getCpuAbiOverride() != null, 32);
        }
        return i;
    }

    private static void initForUser(android.content.pm.ApplicationInfo applicationInfo, com.android.server.pm.pkg.AndroidPackage androidPackage, int i, com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal) {
        com.android.internal.pm.parsing.pkg.PackageImpl packageImpl = (com.android.internal.pm.parsing.pkg.PackageImpl) androidPackage;
        java.lang.String packageName = androidPackage.getPackageName();
        applicationInfo.uid = android.os.UserHandle.getUid(i, android.os.UserHandle.getAppId(androidPackage.getUid()));
        if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(packageName)) {
            applicationInfo.dataDir = SYSTEM_DATA_PATH;
            return;
        }
        if (!packageUserStateInternal.isInstalled() && !packageUserStateInternal.dataExists() && android.content.pm.Flags.nullableDataDir()) {
            applicationInfo.dataDir = null;
            return;
        }
        if (i == 0) {
            applicationInfo.credentialProtectedDataDir = packageImpl.getBaseAppDataCredentialProtectedDirForSystemUser() + packageName;
            applicationInfo.deviceProtectedDataDir = packageImpl.getBaseAppDataDeviceProtectedDirForSystemUser() + packageName;
        } else {
            java.lang.String valueOf = java.lang.String.valueOf(i);
            int length = packageImpl.getBaseAppDataCredentialProtectedDirForSystemUser().length();
            java.lang.StringBuilder replace = new java.lang.StringBuilder(packageImpl.getBaseAppDataCredentialProtectedDirForSystemUser()).replace(length - 2, length - 1, valueOf);
            replace.append(packageName);
            applicationInfo.credentialProtectedDataDir = replace.toString();
            int length2 = packageImpl.getBaseAppDataDeviceProtectedDirForSystemUser().length();
            java.lang.StringBuilder replace2 = new java.lang.StringBuilder(packageImpl.getBaseAppDataDeviceProtectedDirForSystemUser()).replace(length2 - 2, length2 - 1, valueOf);
            replace2.append(packageName);
            applicationInfo.deviceProtectedDataDir = replace2.toString();
        }
        if (androidPackage.isDefaultToDeviceProtectedStorage()) {
            applicationInfo.dataDir = applicationInfo.deviceProtectedDataDir;
        } else {
            applicationInfo.dataDir = applicationInfo.credentialProtectedDataDir;
        }
    }

    private static void initForUser(android.content.pm.InstrumentationInfo instrumentationInfo, com.android.server.pm.pkg.AndroidPackage androidPackage, int i, com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal) {
        com.android.internal.pm.parsing.pkg.PackageImpl packageImpl = (com.android.internal.pm.parsing.pkg.PackageImpl) androidPackage;
        java.lang.String packageName = androidPackage.getPackageName();
        if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(packageName)) {
            instrumentationInfo.dataDir = SYSTEM_DATA_PATH;
            return;
        }
        if (!packageUserStateInternal.isInstalled() && !packageUserStateInternal.dataExists() && android.content.pm.Flags.nullableDataDir()) {
            instrumentationInfo.dataDir = null;
            return;
        }
        if (i == 0) {
            instrumentationInfo.credentialProtectedDataDir = packageImpl.getBaseAppDataCredentialProtectedDirForSystemUser() + packageName;
            instrumentationInfo.deviceProtectedDataDir = packageImpl.getBaseAppDataDeviceProtectedDirForSystemUser() + packageName;
        } else {
            java.lang.String valueOf = java.lang.String.valueOf(i);
            int length = packageImpl.getBaseAppDataCredentialProtectedDirForSystemUser().length();
            java.lang.StringBuilder replace = new java.lang.StringBuilder(packageImpl.getBaseAppDataCredentialProtectedDirForSystemUser()).replace(length - 2, length - 1, valueOf);
            replace.append(packageName);
            instrumentationInfo.credentialProtectedDataDir = replace.toString();
            int length2 = packageImpl.getBaseAppDataDeviceProtectedDirForSystemUser().length();
            java.lang.StringBuilder replace2 = new java.lang.StringBuilder(packageImpl.getBaseAppDataDeviceProtectedDirForSystemUser()).replace(length2 - 2, length2 - 1, valueOf);
            replace2.append(packageName);
            instrumentationInfo.deviceProtectedDataDir = replace2.toString();
        }
        if (androidPackage.isDefaultToDeviceProtectedStorage()) {
            instrumentationInfo.dataDir = instrumentationInfo.deviceProtectedDataDir;
        } else {
            instrumentationInfo.dataDir = instrumentationInfo.credentialProtectedDataDir;
        }
    }

    @android.annotation.Nullable
    public static java.io.File getDataDir(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, int i) {
        if (com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME.equals(packageStateInternal.getPackageName())) {
            return android.os.Environment.getDataSystemDirectory();
        }
        if (!packageStateInternal.getUserStateOrDefault(i).isInstalled() && !packageStateInternal.getUserStateOrDefault(i).dataExists() && android.content.pm.Flags.nullableDataDir()) {
            return null;
        }
        if (packageStateInternal.isDefaultToDeviceProtectedStorage()) {
            return android.os.Environment.getDataUserDePackageDirectory(packageStateInternal.getVolumeUuid(), i, packageStateInternal.getPackageName());
        }
        return android.os.Environment.getDataUserCePackageDirectory(packageStateInternal.getVolumeUuid(), i, packageStateInternal.getPackageName());
    }

    public static class CachedApplicationInfoGenerator {
        private final android.util.ArrayMap<java.lang.String, android.content.pm.ApplicationInfo> mCache = new android.util.ArrayMap<>();

        @android.annotation.Nullable
        public android.content.pm.ApplicationInfo generate(com.android.server.pm.pkg.AndroidPackage androidPackage, long j, com.android.server.pm.pkg.PackageUserStateInternal packageUserStateInternal, int i, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
            android.content.pm.ApplicationInfo applicationInfo = this.mCache.get(androidPackage.getPackageName());
            if (applicationInfo != null) {
                return applicationInfo;
            }
            android.content.pm.ApplicationInfo generateApplicationInfo = com.android.server.pm.parsing.PackageInfoUtils.generateApplicationInfo(androidPackage, j, packageUserStateInternal, i, packageStateInternal);
            this.mCache.put(androidPackage.getPackageName(), generateApplicationInfo);
            return generateApplicationInfo;
        }
    }
}
