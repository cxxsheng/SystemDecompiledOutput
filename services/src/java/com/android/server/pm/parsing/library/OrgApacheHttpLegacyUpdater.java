package com.android.server.pm.parsing.library;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public class OrgApacheHttpLegacyUpdater extends com.android.server.pm.parsing.library.PackageSharedLibraryUpdater {
    private static boolean apkTargetsApiLevelLessThanOrEqualToOMR1(com.android.server.pm.pkg.AndroidPackage androidPackage) {
        return androidPackage.getTargetSdkVersion() < 28;
    }

    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public void updatePackage(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, boolean z, boolean z2) {
        if (apkTargetsApiLevelLessThanOrEqualToOMR1(parsedPackage)) {
            prefixRequiredLibrary(parsedPackage, com.android.server.pm.parsing.library.SharedLibraryNames.ORG_APACHE_HTTP_LEGACY);
        }
    }
}
