package com.android.server.pm.parsing.library;

@com.android.internal.annotations.VisibleForTesting
/* loaded from: classes2.dex */
public class AndroidTestBaseUpdater extends com.android.server.pm.parsing.library.PackageSharedLibraryUpdater {
    private static final long REMOVE_ANDROID_TEST_BASE = 133396946;
    private static final java.lang.String TAG = "AndroidTestBaseUpdater";

    private static boolean isChangeEnabled(@android.annotation.NonNull com.android.server.pm.pkg.AndroidPackage androidPackage, boolean z) {
        if (!z) {
            try {
                return com.android.internal.compat.IPlatformCompat.Stub.asInterface(android.os.ServiceManager.getService("platform_compat")).isChangeEnabled(REMOVE_ANDROID_TEST_BASE, com.android.server.pm.parsing.pkg.AndroidPackageUtils.generateAppInfoWithoutState(androidPackage));
            } catch (android.os.RemoteException | java.lang.NullPointerException e) {
                android.util.Log.e(TAG, "Failed to get a response from PLATFORM_COMPAT_SERVICE", e);
            }
        }
        return androidPackage.getTargetSdkVersion() > 29;
    }

    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public void updatePackage(com.android.internal.pm.parsing.pkg.ParsedPackage parsedPackage, boolean z, boolean z2) {
        if (!isChangeEnabled(parsedPackage, z)) {
            prefixRequiredLibrary(parsedPackage, "android.test.base");
        } else {
            prefixImplicitDependency(parsedPackage, "android.test.runner", "android.test.base");
        }
    }
}
