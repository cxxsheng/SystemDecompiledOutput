package com.android.internal.app;

/* loaded from: classes4.dex */
public class NoCrossProfileEmptyStateProvider implements com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider {
    private final com.android.internal.app.AbstractMultiProfilePagerAdapter.CrossProfileIntentsChecker mCrossProfileIntentsChecker;
    private final com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState mNoPersonalToWorkEmptyState;
    private final com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState mNoWorkToPersonalEmptyState;
    private final android.os.UserHandle mPersonalProfileUserHandle;
    private final android.os.UserHandle mTabOwnerUserHandleForLaunch;

    public NoCrossProfileEmptyStateProvider(android.os.UserHandle userHandle, com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState emptyState, com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState emptyState2, com.android.internal.app.AbstractMultiProfilePagerAdapter.CrossProfileIntentsChecker crossProfileIntentsChecker, android.os.UserHandle userHandle2) {
        this.mPersonalProfileUserHandle = userHandle;
        this.mNoWorkToPersonalEmptyState = emptyState;
        this.mNoPersonalToWorkEmptyState = emptyState2;
        this.mCrossProfileIntentsChecker = crossProfileIntentsChecker;
        this.mTabOwnerUserHandleForLaunch = userHandle2;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider
    public com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState getEmptyState(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        if (!((this.mTabOwnerUserHandleForLaunch.equals(resolverListAdapter.getUserHandle()) || this.mCrossProfileIntentsChecker.hasCrossProfileIntents(resolverListAdapter.getIntents(), this.mTabOwnerUserHandleForLaunch.getIdentifier(), resolverListAdapter.getUserHandle().getIdentifier())) ? false : true)) {
            return null;
        }
        if (resolverListAdapter.getUserHandle().equals(this.mPersonalProfileUserHandle)) {
            return this.mNoWorkToPersonalEmptyState;
        }
        return this.mNoPersonalToWorkEmptyState;
    }

    public static class DevicePolicyBlockerEmptyState implements com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState {
        private final android.content.Context mContext;
        private final int mDefaultSubtitleResource;
        private final int mDefaultTitleResource;
        private final java.lang.String mDevicePolicyStringSubtitleId;
        private final java.lang.String mDevicePolicyStringTitleId;
        private final java.lang.String mEventCategory;
        private final int mEventId;

        public DevicePolicyBlockerEmptyState(android.content.Context context, java.lang.String str, int i, java.lang.String str2, int i2, int i3, java.lang.String str3) {
            this.mContext = context;
            this.mDevicePolicyStringTitleId = str;
            this.mDefaultTitleResource = i;
            this.mDevicePolicyStringSubtitleId = str2;
            this.mDefaultSubtitleResource = i2;
            this.mEventId = i3;
            this.mEventCategory = str3;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public java.lang.String getTitle() {
            return ((android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(this.mDevicePolicyStringTitleId, new java.util.function.Supplier() { // from class: com.android.internal.app.NoCrossProfileEmptyStateProvider$DevicePolicyBlockerEmptyState$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$getTitle$0;
                    lambda$getTitle$0 = com.android.internal.app.NoCrossProfileEmptyStateProvider.DevicePolicyBlockerEmptyState.this.lambda$getTitle$0();
                    return lambda$getTitle$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.String lambda$getTitle$0() {
            return this.mContext.getString(this.mDefaultTitleResource);
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public java.lang.String getSubtitle() {
            return ((android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(this.mDevicePolicyStringSubtitleId, new java.util.function.Supplier() { // from class: com.android.internal.app.NoCrossProfileEmptyStateProvider$DevicePolicyBlockerEmptyState$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$getSubtitle$1;
                    lambda$getSubtitle$1 = com.android.internal.app.NoCrossProfileEmptyStateProvider.DevicePolicyBlockerEmptyState.this.lambda$getSubtitle$1();
                    return lambda$getSubtitle$1;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.String lambda$getSubtitle$1() {
            return this.mContext.getString(this.mDefaultSubtitleResource);
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public void onEmptyStateShown() {
            android.app.admin.DevicePolicyEventLogger.createEvent(this.mEventId).setStrings(this.mEventCategory).write();
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public boolean shouldSkipDataRebuild() {
            return true;
        }
    }
}
