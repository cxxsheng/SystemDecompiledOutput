package com.android.server.pm;

/* loaded from: classes2.dex */
public class NoFilteringResolver extends com.android.server.pm.CrossProfileResolver {
    private static final java.lang.String FLAG_ALLOW_INTENT_REDIRECTION_FOR_CLONE_PROFILE = "allow_intent_redirection_for_clone_profile";

    public static boolean isIntentRedirectionAllowed(android.content.Context context, com.android.internal.config.appcloning.AppCloningDeviceConfigHelper appCloningDeviceConfigHelper, boolean z, long j) {
        return isAppCloningBuildingBlocksEnabled(context, appCloningDeviceConfigHelper) && (z || ((536870912 & j) != 0 && hasPermission(context, "android.permission.QUERY_CLONED_APPS")));
    }

    public NoFilteringResolver(com.android.server.pm.resolution.ComponentResolverApi componentResolverApi, com.android.server.pm.UserManagerService userManagerService) {
        super(componentResolverApi, userManagerService);
    }

    @Override // com.android.server.pm.CrossProfileResolver
    public java.util.List<com.android.server.pm.CrossProfileDomainInfo> resolveIntent(com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, int i, int i2, long j, java.lang.String str2, java.util.List<com.android.server.pm.CrossProfileIntentFilter> list, boolean z, java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function) {
        java.util.List<android.content.pm.ResolveInfo> queryActivities = this.mComponentResolver.queryActivities(computer, intent, str, j, i2);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (queryActivities != null) {
            for (int i3 = 0; i3 < queryActivities.size(); i3++) {
                arrayList.add(new com.android.server.pm.CrossProfileDomainInfo(queryActivities.get(i3), 0, i2));
            }
        }
        return filterIfNotSystemUser(arrayList, i);
    }

    @Override // com.android.server.pm.CrossProfileResolver
    public java.util.List<com.android.server.pm.CrossProfileDomainInfo> filterResolveInfoWithDomainPreferredActivity(android.content.Intent intent, java.util.List<com.android.server.pm.CrossProfileDomainInfo> list, long j, int i, int i2, int i3) {
        return list;
    }

    private static boolean hasPermission(android.content.Context context, java.lang.String str) {
        return context.checkCallingOrSelfPermission(str) == 0;
    }

    private static boolean isAppCloningBuildingBlocksEnabled(android.content.Context context, com.android.internal.config.appcloning.AppCloningDeviceConfigHelper appCloningDeviceConfigHelper) {
        boolean z;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (context.getResources().getBoolean(android.R.bool.config_enableActivityRecognitionHardwareOverlay)) {
                if (appCloningDeviceConfigHelper.getEnableAppCloningBuildingBlocks()) {
                    z = true;
                    return z;
                }
            }
            z = false;
            return z;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
