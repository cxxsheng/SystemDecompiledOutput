package com.android.server.om;

/* loaded from: classes2.dex */
interface PackageManagerHelper {
    boolean doesTargetDefineOverlayable(java.lang.String str, int i) throws java.io.IOException;

    void enforcePermission(java.lang.String str, java.lang.String str2) throws java.lang.SecurityException;

    @android.annotation.Nullable
    java.lang.String getConfigSignaturePackage();

    @android.annotation.NonNull
    java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.lang.String>> getNamedActors();

    @android.annotation.Nullable
    android.content.om.OverlayableInfo getOverlayableForTarget(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i) throws java.io.IOException;

    @android.annotation.Nullable
    com.android.server.pm.pkg.PackageState getPackageStateForUser(@android.annotation.NonNull java.lang.String str, int i);

    @android.annotation.Nullable
    java.lang.String[] getPackagesForUid(int i);

    @android.annotation.NonNull
    android.util.ArrayMap<java.lang.String, com.android.server.pm.pkg.PackageState> initializeForUser(int i);

    boolean isInstantApp(@android.annotation.NonNull java.lang.String str, int i);

    boolean signaturesMatching(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i);
}
