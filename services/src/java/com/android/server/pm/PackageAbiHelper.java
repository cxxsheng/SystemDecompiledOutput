package com.android.server.pm;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public interface PackageAbiHelper {
    @android.annotation.NonNull
    com.android.server.pm.PackageAbiHelper.NativeLibraryPaths deriveNativeLibraryPaths(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, boolean z2, java.io.File file);

    android.util.Pair<com.android.server.pm.PackageAbiHelper.Abis, com.android.server.pm.PackageAbiHelper.NativeLibraryPaths> derivePackageAbi(com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z, boolean z2, java.lang.String str, java.io.File file) throws com.android.server.pm.PackageManagerException;

    @android.annotation.Nullable
    java.lang.String getAdjustedAbiForSharedUser(android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> arraySet, com.android.server.pm.pkg.AndroidPackage androidPackage);

    com.android.server.pm.PackageAbiHelper.Abis getBundledAppAbis(com.android.server.pm.pkg.AndroidPackage androidPackage);

    public static final class NativeLibraryPaths {
        public final java.lang.String nativeLibraryDir;
        public final java.lang.String nativeLibraryRootDir;
        public final boolean nativeLibraryRootRequiresIsa;
        public final java.lang.String secondaryNativeLibraryDir;

        @com.android.internal.annotations.VisibleForTesting
        NativeLibraryPaths(java.lang.String str, boolean z, java.lang.String str2, java.lang.String str3) {
            this.nativeLibraryRootDir = str;
            this.nativeLibraryRootRequiresIsa = z;
            this.nativeLibraryDir = str2;
            this.secondaryNativeLibraryDir = str3;
        }

        public void applyTo(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage) {
            parsedPackage.setNativeLibraryRootDir(this.nativeLibraryRootDir).setNativeLibraryRootRequiresIsa(this.nativeLibraryRootRequiresIsa).setNativeLibraryDir(this.nativeLibraryDir).setSecondaryNativeLibraryDir(this.secondaryNativeLibraryDir);
        }
    }

    public static final class Abis {
        public final java.lang.String primary;
        public final java.lang.String secondary;

        @com.android.internal.annotations.VisibleForTesting
        Abis(java.lang.String str, java.lang.String str2) {
            this.primary = str;
            this.secondary = str2;
        }

        public void applyTo(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage) {
            parsedPackage.setPrimaryCpuAbi(this.primary).setSecondaryCpuAbi(this.secondary);
        }

        public void applyTo(com.android.server.pm.PackageSetting packageSetting) {
            if (packageSetting != null) {
                packageSetting.setPrimaryCpuAbi(this.primary);
                packageSetting.setSecondaryCpuAbi(this.secondary);
            }
        }
    }
}
