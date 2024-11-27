package com.android.server.pm.pkg;

@android.processor.immutability.Immutable
@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes2.dex */
public interface SharedLibrary {
    @android.annotation.NonNull
    java.util.List<java.lang.String> getAllCodePaths();

    @android.annotation.NonNull
    @android.processor.immutability.Immutable.Policy(exceptions = {android.processor.immutability.Immutable.Policy.Exception.FINAL_CLASSES_WITH_FINAL_FIELDS})
    android.content.pm.VersionedPackage getDeclaringPackage();

    @android.annotation.NonNull
    java.util.List<com.android.server.pm.pkg.SharedLibrary> getDependencies();

    @android.annotation.NonNull
    @android.processor.immutability.Immutable.Policy(exceptions = {android.processor.immutability.Immutable.Policy.Exception.FINAL_CLASSES_WITH_FINAL_FIELDS})
    java.util.List<android.content.pm.VersionedPackage> getDependentPackages();

    @android.annotation.Nullable
    java.lang.String getName();

    @android.annotation.Nullable
    java.lang.String getPackageName();

    @android.annotation.Nullable
    java.lang.String getPath();

    int getType();

    long getVersion();

    boolean isNative();
}
