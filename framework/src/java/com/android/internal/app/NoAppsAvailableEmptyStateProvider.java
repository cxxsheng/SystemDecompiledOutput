package com.android.internal.app;

/* loaded from: classes4.dex */
public class NoAppsAvailableEmptyStateProvider implements com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider {
    private final android.content.Context mContext;
    private final java.lang.String mMetricsCategory;
    private final android.os.UserHandle mPersonalProfileUserHandle;
    private final android.os.UserHandle mTabOwnerUserHandleForLaunch;
    private final android.os.UserHandle mWorkProfileUserHandle;

    public NoAppsAvailableEmptyStateProvider(android.content.Context context, android.os.UserHandle userHandle, android.os.UserHandle userHandle2, java.lang.String str, android.os.UserHandle userHandle3) {
        this.mContext = context;
        this.mWorkProfileUserHandle = userHandle;
        this.mPersonalProfileUserHandle = userHandle2;
        this.mMetricsCategory = str;
        this.mTabOwnerUserHandleForLaunch = userHandle3;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider
    public com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState getEmptyState(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        java.lang.String string;
        android.os.UserHandle userHandle = resolverListAdapter.getUserHandle();
        if (this.mWorkProfileUserHandle != null && (this.mTabOwnerUserHandleForLaunch.equals(userHandle) || !hasAppsInOtherProfile(resolverListAdapter))) {
            if (userHandle == this.mPersonalProfileUserHandle) {
                string = ((android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_NO_PERSONAL_APPS, new java.util.function.Supplier() { // from class: com.android.internal.app.NoAppsAvailableEmptyStateProvider$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        java.lang.String lambda$getEmptyState$0;
                        lambda$getEmptyState$0 = com.android.internal.app.NoAppsAvailableEmptyStateProvider.this.lambda$getEmptyState$0();
                        return lambda$getEmptyState$0;
                    }
                });
            } else {
                string = ((android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_NO_WORK_APPS, new java.util.function.Supplier() { // from class: com.android.internal.app.NoAppsAvailableEmptyStateProvider$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        java.lang.String lambda$getEmptyState$1;
                        lambda$getEmptyState$1 = com.android.internal.app.NoAppsAvailableEmptyStateProvider.this.lambda$getEmptyState$1();
                        return lambda$getEmptyState$1;
                    }
                });
            }
            return new com.android.internal.app.NoAppsAvailableEmptyStateProvider.NoAppsAvailableEmptyState(string, this.mMetricsCategory, userHandle == this.mPersonalProfileUserHandle);
        }
        if (this.mWorkProfileUserHandle == null) {
            return new com.android.internal.app.NoAppsAvailableEmptyStateProvider.DefaultEmptyState();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getEmptyState$0() {
        return this.mContext.getString(com.android.internal.R.string.resolver_no_personal_apps_available);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getEmptyState$1() {
        return this.mContext.getString(com.android.internal.R.string.resolver_no_work_apps_available);
    }

    private boolean hasAppsInOtherProfile(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        if (this.mWorkProfileUserHandle == null) {
            return false;
        }
        java.util.Iterator<com.android.internal.app.ResolverActivity.ResolvedComponentInfo> it = resolverListAdapter.getResolversForUser(this.mTabOwnerUserHandleForLaunch).iterator();
        while (it.hasNext()) {
            if (it.next().getResolveInfoAt(0).targetUserId != -2) {
                return true;
            }
        }
        return false;
    }

    public static class DefaultEmptyState implements com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState {
        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public boolean useDefaultEmptyView() {
            return true;
        }
    }

    public static class NoAppsAvailableEmptyState implements com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState {
        private boolean mIsPersonalProfile;
        private java.lang.String mMetricsCategory;
        private java.lang.String mTitle;

        public NoAppsAvailableEmptyState(java.lang.String str, java.lang.String str2, boolean z) {
            this.mTitle = str;
            this.mMetricsCategory = str2;
            this.mIsPersonalProfile = z;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public java.lang.String getTitle() {
            return this.mTitle;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public void onEmptyStateShown() {
            android.app.admin.DevicePolicyEventLogger.createEvent(160).setStrings(this.mMetricsCategory).setBoolean(this.mIsPersonalProfile).write();
        }
    }
}
