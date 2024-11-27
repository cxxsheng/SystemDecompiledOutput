package com.android.server.pm.resolution;

/* loaded from: classes2.dex */
public class ComponentResolverSnapshot extends com.android.server.pm.resolution.ComponentResolverBase {
    public ComponentResolverSnapshot(@android.annotation.NonNull com.android.server.pm.resolution.ComponentResolver componentResolver, @android.annotation.NonNull com.android.server.pm.UserNeedsBadgingCache userNeedsBadgingCache) {
        super(com.android.server.pm.UserManagerService.getInstance());
        this.mActivities = new com.android.server.pm.resolution.ComponentResolver.ActivityIntentResolver(componentResolver.mActivities, this.mUserManager, userNeedsBadgingCache);
        this.mProviders = new com.android.server.pm.resolution.ComponentResolver.ProviderIntentResolver(componentResolver.mProviders, this.mUserManager);
        this.mReceivers = new com.android.server.pm.resolution.ComponentResolver.ReceiverIntentResolver(componentResolver.mReceivers, this.mUserManager, userNeedsBadgingCache);
        this.mServices = new com.android.server.pm.resolution.ComponentResolver.ServiceIntentResolver(componentResolver.mServices, this.mUserManager);
        this.mProvidersByAuthority = new android.util.ArrayMap<>(componentResolver.mProvidersByAuthority);
    }
}
