package com.android.server.pm.parsing.pkg;

/* loaded from: classes2.dex */
public class AndroidPackageUtils {
    private AndroidPackageUtils() {
    }

    public static java.util.List<java.lang.String> getAllCodePathsExcludingResourceOnly(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        com.android.internal.pm.parsing.pkg.PackageImpl packageImpl = (com.android.internal.pm.parsing.pkg.PackageImpl) androidPackage;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (packageImpl.isDeclaredHavingCode()) {
            arrayList.add(packageImpl.getBaseApkPath());
        }
        java.lang.String[] splitCodePaths = packageImpl.getSplitCodePaths();
        if (!com.android.internal.util.ArrayUtils.isEmpty(splitCodePaths)) {
            for (int i = 0; i < splitCodePaths.length; i++) {
                if ((packageImpl.getSplitFlags()[i] & 4) != 0) {
                    arrayList.add(splitCodePaths[i]);
                }
            }
        }
        return arrayList;
    }

    public static java.util.List<java.lang.String> getAllCodePaths(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        com.android.internal.pm.parsing.pkg.PackageImpl packageImpl = (com.android.internal.pm.parsing.pkg.PackageImpl) androidPackage;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(packageImpl.getBaseApkPath());
        java.lang.String[] splitCodePaths = packageImpl.getSplitCodePaths();
        if (!com.android.internal.util.ArrayUtils.isEmpty(splitCodePaths)) {
            java.util.Collections.addAll(arrayList, splitCodePaths);
        }
        return arrayList;
    }

    public static android.content.pm.SharedLibraryInfo createSharedLibraryForSdk(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return new android.content.pm.SharedLibraryInfo(null, androidPackage.getPackageName(), getAllCodePaths(androidPackage), androidPackage.getSdkLibraryName(), androidPackage.getSdkLibVersionMajor(), 3, new android.content.pm.VersionedPackage(androidPackage.getManifestPackageName(), androidPackage.getLongVersionCode()), null, null, false);
    }

    public static android.content.pm.SharedLibraryInfo createSharedLibraryForStatic(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return new android.content.pm.SharedLibraryInfo(null, androidPackage.getPackageName(), getAllCodePaths(androidPackage), androidPackage.getStaticSharedLibraryName(), androidPackage.getStaticSharedLibraryVersion(), 2, new android.content.pm.VersionedPackage(androidPackage.getManifestPackageName(), androidPackage.getLongVersionCode()), null, null, false);
    }

    public static android.content.pm.SharedLibraryInfo createSharedLibraryForDynamic(com.android.server.pm.pkg.AndroidPackage androidPackage, java.lang.String str) {
        return new android.content.pm.SharedLibraryInfo(null, androidPackage.getPackageName(), getAllCodePaths(androidPackage), str, -1L, 1, new android.content.pm.VersionedPackage(androidPackage.getPackageName(), androidPackage.getLongVersionCode()), null, null, false);
    }

    public static java.util.Map<java.lang.String, java.lang.String> getPackageDexMetadata(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return android.content.pm.dex.DexMetadataHelper.buildPackageApkToDexMetadataMap(getAllCodePaths(androidPackage));
    }

    public static void validatePackageDexMetadata(com.android.server.pm.pkg.AndroidPackage androidPackage) throws com.android.internal.pm.parsing.PackageParserException {
        java.util.Collection<java.lang.String> values = getPackageDexMetadata(androidPackage).values();
        java.lang.String packageName = androidPackage.getPackageName();
        long longVersionCode = androidPackage.getLongVersionCode();
        android.content.pm.parsing.result.ParseTypeImpl forDefaultParsing = android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing();
        java.util.Iterator<java.lang.String> it = values.iterator();
        while (it.hasNext()) {
            android.content.pm.parsing.result.ParseResult validateDexMetadataFile = android.content.pm.dex.DexMetadataHelper.validateDexMetadataFile(forDefaultParsing.reset(), it.next(), packageName, longVersionCode);
            if (validateDexMetadataFile.isError()) {
                throw new com.android.internal.pm.parsing.PackageParserException(validateDexMetadataFile.getErrorCode(), validateDexMetadataFile.getErrorMessage(), validateDexMetadataFile.getException());
            }
        }
    }

    public static com.android.internal.content.NativeLibraryHelper.Handle createNativeLibraryHandle(com.android.server.pm.pkg.AndroidPackage androidPackage) throws java.io.IOException {
        return com.android.internal.content.NativeLibraryHelper.Handle.create(getAllCodePaths(androidPackage), androidPackage.isMultiArch(), androidPackage.isExtractNativeLibrariesRequested(), androidPackage.isDebuggable());
    }

    public static boolean canHaveOatDir(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, @android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return (!packageState.isSystem() || packageState.isUpdatedSystemApp()) && !android.os.incremental.IncrementalManager.isIncrementalPath(androidPackage.getPath());
    }

    public static boolean hasComponentClassName(com.android.server.pm.pkg.AndroidPackage androidPackage, java.lang.String str) {
        java.util.List activities = androidPackage.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            if (java.util.Objects.equals(str, ((com.android.internal.pm.pkg.component.ParsedActivity) activities.get(i)).getName())) {
                return true;
            }
        }
        java.util.List receivers = androidPackage.getReceivers();
        int size2 = receivers.size();
        for (int i2 = 0; i2 < size2; i2++) {
            if (java.util.Objects.equals(str, ((com.android.internal.pm.pkg.component.ParsedActivity) receivers.get(i2)).getName())) {
                return true;
            }
        }
        java.util.List providers = androidPackage.getProviders();
        int size3 = providers.size();
        for (int i3 = 0; i3 < size3; i3++) {
            if (java.util.Objects.equals(str, ((com.android.internal.pm.pkg.component.ParsedProvider) providers.get(i3)).getName())) {
                return true;
            }
        }
        java.util.List services = androidPackage.getServices();
        int size4 = services.size();
        for (int i4 = 0; i4 < size4; i4++) {
            if (java.util.Objects.equals(str, ((com.android.internal.pm.pkg.component.ParsedService) services.get(i4)).getName())) {
                return true;
            }
        }
        java.util.List instrumentations = androidPackage.getInstrumentations();
        int size5 = instrumentations.size();
        for (int i5 = 0; i5 < size5; i5++) {
            if (java.util.Objects.equals(str, ((com.android.internal.pm.pkg.component.ParsedInstrumentation) instrumentations.get(i5)).getName())) {
                return true;
            }
        }
        return androidPackage.getBackupAgentName() != null && java.util.Objects.equals(str, androidPackage.getBackupAgentName());
    }

    public static boolean isEncryptionAware(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return androidPackage.isDirectBootAware() || androidPackage.isPartiallyDirectBootAware();
    }

    public static boolean isLibrary(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return (androidPackage.getSdkLibraryName() == null && androidPackage.getStaticSharedLibraryName() == null && androidPackage.getLibraryNames().isEmpty()) ? false : true;
    }

    public static int getHiddenApiEnforcementPolicy(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        boolean z;
        if (androidPackage == null) {
            z = false;
        } else {
            z = true;
            if (!androidPackage.isSignedWithPlatformKey()) {
                if (packageStateInternal.isSystem()) {
                    if (!androidPackage.isNonSdkApiRequested() && !com.android.server.SystemConfig.getInstance().getHiddenApiWhitelistedApps().contains(androidPackage.getPackageName())) {
                        z = false;
                    }
                } else {
                    z = false;
                }
            }
        }
        if (z) {
            return 0;
        }
        return 2;
    }

    public static boolean isMatchForSystemOnly(@android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState, long j) {
        if ((j & 1048576) != 0) {
            return packageState.isSystem();
        }
        return true;
    }

    public static java.lang.String getRawPrimaryCpuAbi(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).getPrimaryCpuAbi();
    }

    public static java.lang.String getRawSecondaryCpuAbi(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).getSecondaryCpuAbi();
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public static android.content.pm.ApplicationInfo generateAppInfoWithoutState(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).toAppInfoWithoutState();
    }

    @android.annotation.Nullable
    public static java.lang.String getRealPackageOrNull(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z) {
        if (androidPackage.getOriginalPackages().isEmpty() || !z) {
            return null;
        }
        return androidPackage.getManifestPackageName();
    }

    public static void fillVersionCodes(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull android.content.pm.PackageInfo packageInfo) {
        com.android.internal.pm.pkg.parsing.ParsingPackageHidden parsingPackageHidden = (com.android.internal.pm.pkg.parsing.ParsingPackageHidden) androidPackage;
        packageInfo.versionCode = parsingPackageHidden.getVersionCode();
        packageInfo.versionCodeMajor = parsingPackageHidden.getVersionCodeMajor();
    }
}
