package com.android.server.pm.parsing.library;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public class AndroidHidlUpdater extends com.android.server.pm.parsing.library.PackageSharedLibraryUpdater {
    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public void updatePackage(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, boolean z, boolean z2) {
        if ((parsedPackage.getTargetSdkVersion() <= 28) && (z || z2)) {
            prefixRequiredLibrary(parsedPackage, "android.hidl.base-V1.0-java");
            prefixRequiredLibrary(parsedPackage, "android.hidl.manager-V1.0-java");
        } else {
            com.android.server.pm.parsing.library.PackageSharedLibraryUpdater.removeLibrary(parsedPackage, "android.hidl.base-V1.0-java");
            com.android.server.pm.parsing.library.PackageSharedLibraryUpdater.removeLibrary(parsedPackage, "android.hidl.manager-V1.0-java");
        }
    }
}
