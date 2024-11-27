package com.android.internal.pm.parsing.pkg;

/* loaded from: classes5.dex */
public class AndroidPackageLegacyUtils {
    private AndroidPackageLegacyUtils() {
    }

    public static java.lang.String getRawPrimaryCpuAbi(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).getPrimaryCpuAbi();
    }

    public static java.lang.String getRawSecondaryCpuAbi(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).getSecondaryCpuAbi();
    }

    @java.lang.Deprecated
    public static android.content.pm.ApplicationInfo generateAppInfoWithoutState(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).toAppInfoWithoutState();
    }

    public static java.lang.String getRealPackageOrNull(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z) {
        if (androidPackage.getOriginalPackages().isEmpty() || !z) {
            return null;
        }
        return androidPackage.getManifestPackageName();
    }

    public static void fillVersionCodes(com.android.server.pm.pkg.AndroidPackage androidPackage, android.content.pm.PackageInfo packageInfo) {
        com.android.internal.pm.pkg.parsing.ParsingPackageHidden parsingPackageHidden = (com.android.internal.pm.pkg.parsing.ParsingPackageHidden) androidPackage;
        packageInfo.versionCode = parsingPackageHidden.getVersionCode();
        packageInfo.versionCodeMajor = parsingPackageHidden.getVersionCodeMajor();
    }

    @java.lang.Deprecated
    public static boolean isSystem(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).isSystem();
    }

    @java.lang.Deprecated
    public static boolean isSystemExt(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).isSystemExt();
    }

    @java.lang.Deprecated
    public static boolean isPrivileged(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).isPrivileged();
    }

    @java.lang.Deprecated
    public static boolean isOem(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).isOem();
    }

    @java.lang.Deprecated
    public static boolean isVendor(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).isVendor();
    }

    @java.lang.Deprecated
    public static boolean isProduct(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).isProduct();
    }

    @java.lang.Deprecated
    public static boolean isOdm(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return ((com.android.internal.pm.parsing.pkg.AndroidPackageHidden) androidPackage).isOdm();
    }
}
