package com.android.server.pm;

/* loaded from: classes2.dex */
public abstract class CrossProfileResolver {
    protected final com.android.server.pm.resolution.ComponentResolverApi mComponentResolver;
    protected final com.android.server.pm.UserManagerService mUserManager;

    public abstract java.util.List<com.android.server.pm.CrossProfileDomainInfo> filterResolveInfoWithDomainPreferredActivity(android.content.Intent intent, java.util.List<com.android.server.pm.CrossProfileDomainInfo> list, long j, int i, int i2, int i3);

    public abstract java.util.List<com.android.server.pm.CrossProfileDomainInfo> resolveIntent(com.android.server.pm.Computer computer, android.content.Intent intent, java.lang.String str, int i, int i2, long j, java.lang.String str2, java.util.List<com.android.server.pm.CrossProfileIntentFilter> list, boolean z, java.util.function.Function<java.lang.String, com.android.server.pm.pkg.PackageStateInternal> function);

    public CrossProfileResolver(com.android.server.pm.resolution.ComponentResolverApi componentResolverApi, com.android.server.pm.UserManagerService userManagerService) {
        this.mComponentResolver = componentResolverApi;
        this.mUserManager = userManagerService;
    }

    protected final boolean isUserEnabled(int i) {
        boolean z;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.pm.UserInfo userInfo = this.mUserManager.getUserInfo(i);
            if (userInfo != null) {
                if (userInfo.isEnabled()) {
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

    protected final java.util.List<com.android.server.pm.CrossProfileDomainInfo> filterIfNotSystemUser(java.util.List<com.android.server.pm.CrossProfileDomainInfo> list, int i) {
        if (i == 0) {
            return list;
        }
        for (int size = com.android.internal.util.CollectionUtils.size(list) - 1; size >= 0; size--) {
            if ((list.get(size).mResolveInfo.activityInfo.flags & 536870912) != 0) {
                list.remove(size);
            }
        }
        return list;
    }

    protected final android.content.pm.UserInfo getProfileParent(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return this.mUserManager.getProfileParent(i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
