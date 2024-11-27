package com.android.server.pm.pkg;

/* loaded from: classes2.dex */
public interface PackageUserStateInternal extends com.android.server.pm.pkg.PackageUserState, android.content.pm.pkg.FrameworkPackageUserState {
    public static final com.android.server.pm.pkg.PackageUserStateInternal DEFAULT = new com.android.server.pm.pkg.PackageUserStateDefault();

    @android.annotation.Nullable
    com.android.server.utils.WatchedArraySet<java.lang.String> getDisabledComponentsNoCopy();

    @android.annotation.Nullable
    com.android.server.utils.WatchedArraySet<java.lang.String> getEnabledComponentsNoCopy();

    @android.annotation.Nullable
    android.util.Pair<java.lang.String, java.lang.Integer> getOverrideLabelIconForComponent(@android.annotation.NonNull android.content.ComponentName componentName);

    @android.annotation.Nullable
    com.android.server.utils.WatchedArrayMap<android.content.pm.UserPackage, com.android.server.pm.pkg.SuspendParams> getSuspendParams();
}
