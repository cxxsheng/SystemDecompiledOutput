package com.android.internal.app;

/* loaded from: classes4.dex */
public class WorkProfilePausedEmptyStateProvider implements com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider {
    private final android.content.Context mContext;
    private final java.lang.String mMetricsCategory;
    private final com.android.internal.app.AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener mOnSwitchOnWorkSelectedListener;
    private final com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager mQuietModeManager;
    private final android.os.UserHandle mWorkProfileUserHandle;

    public WorkProfilePausedEmptyStateProvider(android.content.Context context, android.os.UserHandle userHandle, com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager quietModeManager, com.android.internal.app.AbstractMultiProfilePagerAdapter.OnSwitchOnWorkSelectedListener onSwitchOnWorkSelectedListener, java.lang.String str) {
        this.mContext = context;
        this.mWorkProfileUserHandle = userHandle;
        this.mQuietModeManager = quietModeManager;
        this.mMetricsCategory = str;
        this.mOnSwitchOnWorkSelectedListener = onSwitchOnWorkSelectedListener;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider
    public com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState getEmptyState(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        if (!resolverListAdapter.getUserHandle().equals(this.mWorkProfileUserHandle) || !this.mQuietModeManager.isQuietModeEnabled(this.mWorkProfileUserHandle) || resolverListAdapter.getCount() == 0) {
            return null;
        }
        return new com.android.internal.app.WorkProfilePausedEmptyStateProvider.WorkProfileOffEmptyState(((android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getString(android.app.admin.DevicePolicyResources.Strings.Core.RESOLVER_WORK_PAUSED_TITLE, new java.util.function.Supplier() { // from class: com.android.internal.app.WorkProfilePausedEmptyStateProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                java.lang.String lambda$getEmptyState$0;
                lambda$getEmptyState$0 = com.android.internal.app.WorkProfilePausedEmptyStateProvider.this.lambda$getEmptyState$0();
                return lambda$getEmptyState$0;
            }
        }), new com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState.ClickListener() { // from class: com.android.internal.app.WorkProfilePausedEmptyStateProvider$$ExternalSyntheticLambda1
            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState.ClickListener
            public final void onClick(com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState.TabControl tabControl) {
                com.android.internal.app.WorkProfilePausedEmptyStateProvider.this.lambda$getEmptyState$1(tabControl);
            }
        }, this.mMetricsCategory);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String lambda$getEmptyState$0() {
        return this.mContext.getString(com.android.internal.R.string.resolver_turn_on_work_apps);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getEmptyState$1(com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState.TabControl tabControl) {
        tabControl.showSpinner();
        if (this.mOnSwitchOnWorkSelectedListener != null) {
            this.mOnSwitchOnWorkSelectedListener.onSwitchOnWorkSelected();
        }
        this.mQuietModeManager.requestQuietModeEnabled(false, this.mWorkProfileUserHandle);
    }

    public static class WorkProfileOffEmptyState implements com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState {
        private final java.lang.String mMetricsCategory;
        private final com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState.ClickListener mOnClick;
        private final java.lang.String mTitle;

        public WorkProfileOffEmptyState(java.lang.String str, com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState.ClickListener clickListener, java.lang.String str2) {
            this.mTitle = str;
            this.mOnClick = clickListener;
            this.mMetricsCategory = str2;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public java.lang.String getTitle() {
            return this.mTitle;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState.ClickListener getButtonClickListener() {
            return this.mOnClick;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState
        public void onEmptyStateShown() {
            android.app.admin.DevicePolicyEventLogger.createEvent(157).setStrings(this.mMetricsCategory).write();
        }
    }
}
