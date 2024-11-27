package com.android.server.pm.resolution;

/* loaded from: classes2.dex */
public interface ComponentResolverApi {
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    boolean componentExists(@android.annotation.NonNull android.content.ComponentName componentName);

    void dumpActivityResolvers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str);

    void dumpContentProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str);

    void dumpProviderResolvers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str);

    void dumpReceiverResolvers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str);

    void dumpServicePermissions(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState);

    void dumpServiceResolvers(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull com.android.server.pm.DumpState dumpState, @android.annotation.NonNull java.lang.String str);

    @android.annotation.Nullable
    com.android.internal.pm.pkg.component.ParsedActivity getActivity(@android.annotation.NonNull android.content.ComponentName componentName);

    @android.annotation.Nullable
    com.android.internal.pm.pkg.component.ParsedProvider getProvider(@android.annotation.NonNull android.content.ComponentName componentName);

    @android.annotation.Nullable
    com.android.internal.pm.pkg.component.ParsedActivity getReceiver(@android.annotation.NonNull android.content.ComponentName componentName);

    @android.annotation.Nullable
    com.android.internal.pm.pkg.component.ParsedService getService(@android.annotation.NonNull android.content.ComponentName componentName);

    boolean isActivityDefined(@android.annotation.NonNull android.content.ComponentName componentName);

    @android.annotation.Nullable
    java.util.List<android.content.pm.ResolveInfo> queryActivities(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, int i);

    @android.annotation.Nullable
    java.util.List<android.content.pm.ResolveInfo> queryActivities(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> list, int i);

    @android.annotation.Nullable
    android.content.pm.ProviderInfo queryProvider(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.lang.String str, long j, int i);

    @android.annotation.Nullable
    java.util.List<android.content.pm.ResolveInfo> queryProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, int i);

    @android.annotation.Nullable
    java.util.List<android.content.pm.ResolveInfo> queryProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull java.util.List<com.android.internal.pm.pkg.component.ParsedProvider> list, int i);

    @android.annotation.Nullable
    java.util.List<android.content.pm.ProviderInfo> queryProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2, int i, long j, int i2);

    @android.annotation.Nullable
    java.util.List<android.content.pm.ResolveInfo> queryReceivers(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, int i);

    @android.annotation.Nullable
    java.util.List<android.content.pm.ResolveInfo> queryReceivers(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull java.util.List<com.android.internal.pm.pkg.component.ParsedActivity> list, int i);

    @android.annotation.Nullable
    java.util.List<android.content.pm.ResolveInfo> queryServices(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, int i);

    @android.annotation.Nullable
    java.util.List<android.content.pm.ResolveInfo> queryServices(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull android.content.Intent intent, @android.annotation.Nullable java.lang.String str, long j, @android.annotation.NonNull java.util.List<com.android.internal.pm.pkg.component.ParsedService> list, int i);

    void querySyncProviders(@android.annotation.NonNull com.android.server.pm.Computer computer, @android.annotation.NonNull java.util.List<java.lang.String> list, @android.annotation.NonNull java.util.List<android.content.pm.ProviderInfo> list2, boolean z, int i);
}
