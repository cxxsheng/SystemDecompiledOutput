package com.android.server.pm.parsing.library;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public class ApexSharedLibraryUpdater extends com.android.server.pm.parsing.library.PackageSharedLibraryUpdater {
    private final android.util.ArrayMap<java.lang.String, com.android.server.SystemConfig.SharedLibraryEntry> mSharedLibraries;

    public ApexSharedLibraryUpdater(android.util.ArrayMap<java.lang.String, com.android.server.SystemConfig.SharedLibraryEntry> arrayMap) {
        this.mSharedLibraries = arrayMap;
    }

    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public void updatePackage(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, boolean z, boolean z2) {
        int size = this.mSharedLibraries.size();
        for (int i = 0; i < size; i++) {
            updateSharedLibraryForPackage(this.mSharedLibraries.valueAt(i), parsedPackage);
        }
    }

    private void updateSharedLibraryForPackage(com.android.server.SystemConfig.SharedLibraryEntry sharedLibraryEntry, com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage) {
        if (sharedLibraryEntry.onBootclasspathBefore != null && isTargetSdkAtMost(parsedPackage.getTargetSdkVersion(), sharedLibraryEntry.onBootclasspathBefore) && com.android.modules.utils.build.UnboundedSdkLevel.isAtLeast(sharedLibraryEntry.onBootclasspathBefore)) {
            prefixRequiredLibrary(parsedPackage, sharedLibraryEntry.name);
        }
        if (sharedLibraryEntry.canBeSafelyIgnored) {
            com.android.server.pm.parsing.library.PackageSharedLibraryUpdater.removeLibrary(parsedPackage, sharedLibraryEntry.name);
        }
    }

    private static boolean isTargetSdkAtMost(int i, java.lang.String str) {
        return isCodename(str) ? i < 10000 : i < java.lang.Integer.parseInt(str);
    }

    private static boolean isCodename(java.lang.String str) {
        if (str.length() == 0) {
            throw new java.lang.IllegalArgumentException();
        }
        return java.lang.Character.isUpperCase(str.charAt(0));
    }
}
