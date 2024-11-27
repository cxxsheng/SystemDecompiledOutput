package com.android.server.pm;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
/* loaded from: classes2.dex */
interface FeatureConfig {
    void enableLogging(int i, boolean z);

    boolean isGloballyEnabled();

    boolean isLoggingEnabled(int i);

    void onSystemReady();

    boolean packageIsEnabled(com.android.server.pm.pkg.AndroidPackage androidPackage);

    com.android.server.pm.FeatureConfig snapshot();

    void updatePackageState(com.android.server.pm.pkg.PackageStateInternal packageStateInternal, boolean z);
}
