package com.android.server.pm.dex;

/* loaded from: classes2.dex */
public final class ArtUtils {
    private ArtUtils() {
    }

    public static com.android.server.pm.dex.ArtPackageInfo createArtPackageInfo(com.android.server.pm.pkg.AndroidPackage androidPackage, com.android.server.pm.pkg.PackageState packageState) {
        return new com.android.server.pm.dex.ArtPackageInfo(androidPackage.getPackageName(), java.util.Arrays.asList(com.android.server.pm.InstructionSets.getAppDexInstructionSets(packageState.getPrimaryCpuAbi(), packageState.getSecondaryCpuAbi())), com.android.server.pm.parsing.pkg.AndroidPackageUtils.getAllCodePaths(androidPackage), getOatDir(androidPackage, packageState));
    }

    private static java.lang.String getOatDir(com.android.server.pm.pkg.AndroidPackage androidPackage, @android.annotation.NonNull com.android.server.pm.pkg.PackageState packageState) {
        if (!com.android.server.pm.parsing.pkg.AndroidPackageUtils.canHaveOatDir(packageState, androidPackage)) {
            return null;
        }
        java.io.File file = new java.io.File(androidPackage.getPath());
        if (file.isDirectory()) {
            return com.android.server.pm.PackageDexOptimizer.getOatDir(file).getAbsolutePath();
        }
        return null;
    }
}
