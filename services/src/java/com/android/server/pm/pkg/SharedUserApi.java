package com.android.server.pm.pkg;

/* loaded from: classes2.dex */
public interface SharedUserApi {
    int getAppId();

    @android.annotation.NonNull
    android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> getDisabledPackageStates();

    @android.annotation.NonNull
    java.lang.String getName();

    @android.annotation.NonNull
    android.util.ArraySet<? extends com.android.server.pm.pkg.PackageStateInternal> getPackageStates();

    @android.annotation.NonNull
    java.util.List<com.android.server.pm.pkg.AndroidPackage> getPackages();

    int getPrivateUidFlags();

    @android.annotation.NonNull
    android.util.ArrayMap<java.lang.String, com.android.internal.pm.pkg.component.ParsedProcess> getProcesses();

    int getSeInfoTargetSdkVersion();

    @android.annotation.NonNull
    com.android.server.pm.permission.LegacyPermissionState getSharedUserLegacyPermissionState();

    @android.annotation.NonNull
    android.content.pm.SigningDetails getSigningDetails();

    int getUidFlags();

    boolean isPrivileged();
}
