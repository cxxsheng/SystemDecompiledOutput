package com.android.internal.app;

/* loaded from: classes4.dex */
public abstract class AbstractMultiProfilePagerAdapter extends com.android.internal.widget.PagerAdapter {
    static final int PROFILE_PERSONAL = 0;
    static final int PROFILE_WORK = 1;
    private static final java.lang.String TAG = "AbstractMultiProfilePagerAdapter";
    private final android.os.UserHandle mCloneUserHandle;
    private final android.content.Context mContext;
    private int mCurrentPage;
    private final com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider mEmptyStateProvider;
    private java.util.Set<java.lang.Integer> mLoadedPages = new java.util.HashSet();
    private com.android.internal.app.AbstractMultiProfilePagerAdapter.OnProfileSelectedListener mOnProfileSelectedListener;
    private final com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager mQuietModeManager;
    private final android.os.UserHandle mWorkProfileUserHandle;

    public interface OnProfileSelectedListener {
        void onProfilePageStateChanged(int i);

        void onProfileSelected(int i);
    }

    interface OnSwitchOnWorkSelectedListener {
        void onSwitchOnWorkSelected();
    }

    @interface Profile {
    }

    public interface QuietModeManager {
        boolean isQuietModeEnabled(android.os.UserHandle userHandle);

        boolean isWaitingToEnableWorkProfile();

        void markWorkProfileEnabledBroadcastReceived();

        void requestQuietModeEnabled(boolean z, android.os.UserHandle userHandle);
    }

    abstract android.view.ViewGroup getActiveAdapterView();

    public abstract com.android.internal.app.ResolverListAdapter getActiveListAdapter();

    public abstract java.lang.Object getAdapterForIndex(int i);

    abstract java.lang.Object getCurrentRootAdapter();

    abstract android.view.ViewGroup getInactiveAdapterView();

    public abstract com.android.internal.app.ResolverListAdapter getInactiveListAdapter();

    public abstract com.android.internal.app.AbstractMultiProfilePagerAdapter.ProfileDescriptor getItem(int i);

    abstract int getItemCount();

    abstract com.android.internal.app.ResolverListAdapter getListAdapterForUserHandle(android.os.UserHandle userHandle);

    public abstract com.android.internal.app.ResolverListAdapter getPersonalListAdapter();

    public abstract com.android.internal.app.ResolverListAdapter getWorkListAdapter();

    abstract void setupListAdapter(int i);

    AbstractMultiProfilePagerAdapter(android.content.Context context, int i, com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider emptyStateProvider, com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager quietModeManager, android.os.UserHandle userHandle, android.os.UserHandle userHandle2) {
        this.mContext = (android.content.Context) java.util.Objects.requireNonNull(context);
        this.mCurrentPage = i;
        this.mWorkProfileUserHandle = userHandle;
        this.mCloneUserHandle = userHandle2;
        this.mEmptyStateProvider = emptyStateProvider;
        this.mQuietModeManager = quietModeManager;
    }

    private boolean isQuietModeEnabled(android.os.UserHandle userHandle) {
        return this.mQuietModeManager.isQuietModeEnabled(userHandle);
    }

    void setOnProfileSelectedListener(com.android.internal.app.AbstractMultiProfilePagerAdapter.OnProfileSelectedListener onProfileSelectedListener) {
        this.mOnProfileSelectedListener = onProfileSelectedListener;
    }

    android.content.Context getContext() {
        return this.mContext;
    }

    void setupViewPager(com.android.internal.widget.ViewPager viewPager) {
        viewPager.setOnPageChangeListener(new com.android.internal.widget.ViewPager.SimpleOnPageChangeListener() { // from class: com.android.internal.app.AbstractMultiProfilePagerAdapter.1
            @Override // com.android.internal.widget.ViewPager.SimpleOnPageChangeListener, com.android.internal.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                com.android.internal.app.AbstractMultiProfilePagerAdapter.this.mCurrentPage = i;
                if (!com.android.internal.app.AbstractMultiProfilePagerAdapter.this.mLoadedPages.contains(java.lang.Integer.valueOf(i))) {
                    com.android.internal.app.AbstractMultiProfilePagerAdapter.this.rebuildActiveTab(true);
                    com.android.internal.app.AbstractMultiProfilePagerAdapter.this.mLoadedPages.add(java.lang.Integer.valueOf(i));
                }
                if (com.android.internal.app.AbstractMultiProfilePagerAdapter.this.mOnProfileSelectedListener != null) {
                    com.android.internal.app.AbstractMultiProfilePagerAdapter.this.mOnProfileSelectedListener.onProfileSelected(i);
                }
            }

            @Override // com.android.internal.widget.ViewPager.SimpleOnPageChangeListener, com.android.internal.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
                if (com.android.internal.app.AbstractMultiProfilePagerAdapter.this.mOnProfileSelectedListener != null) {
                    com.android.internal.app.AbstractMultiProfilePagerAdapter.this.mOnProfileSelectedListener.onProfilePageStateChanged(i);
                }
            }
        });
        viewPager.setAdapter(this);
        viewPager.setCurrentItem(this.mCurrentPage);
        this.mLoadedPages.add(java.lang.Integer.valueOf(this.mCurrentPage));
    }

    void clearInactiveProfileCache() {
        if (this.mLoadedPages.size() == 1) {
            return;
        }
        this.mLoadedPages.remove(java.lang.Integer.valueOf(1 - this.mCurrentPage));
    }

    @Override // com.android.internal.widget.PagerAdapter
    public android.view.ViewGroup instantiateItem(android.view.ViewGroup viewGroup, int i) {
        com.android.internal.app.AbstractMultiProfilePagerAdapter.ProfileDescriptor item = getItem(i);
        viewGroup.addView(item.rootView);
        return item.rootView;
    }

    @Override // com.android.internal.widget.PagerAdapter
    public void destroyItem(android.view.ViewGroup viewGroup, int i, java.lang.Object obj) {
        viewGroup.removeView((android.view.View) obj);
    }

    @Override // com.android.internal.widget.PagerAdapter
    public int getCount() {
        return getItemCount();
    }

    protected int getCurrentPage() {
        return this.mCurrentPage;
    }

    public android.os.UserHandle getCurrentUserHandle() {
        return getActiveListAdapter().mResolverListController.getUserHandle();
    }

    @Override // com.android.internal.widget.PagerAdapter
    public boolean isViewFromObject(android.view.View view, java.lang.Object obj) {
        return view == obj;
    }

    @Override // com.android.internal.widget.PagerAdapter
    public java.lang.CharSequence getPageTitle(int i) {
        return null;
    }

    public android.os.UserHandle getCloneUserHandle() {
        return this.mCloneUserHandle;
    }

    boolean rebuildActiveTab(boolean z) {
        android.os.Trace.beginSection("MultiProfilePagerAdapter#rebuildActiveTab");
        boolean rebuildTab = rebuildTab(getActiveListAdapter(), z);
        android.os.Trace.endSection();
        return rebuildTab;
    }

    boolean rebuildInactiveTab(boolean z) {
        android.os.Trace.beginSection("MultiProfilePagerAdapter#rebuildInactiveTab");
        if (getItemCount() == 1) {
            android.os.Trace.endSection();
            return false;
        }
        boolean rebuildTab = rebuildTab(getInactiveListAdapter(), z);
        android.os.Trace.endSection();
        return rebuildTab;
    }

    private int userHandleToPageIndex(android.os.UserHandle userHandle) {
        if (userHandle.equals(getPersonalListAdapter().mResolverListController.getUserHandle())) {
            return 0;
        }
        return 1;
    }

    private boolean rebuildTab(com.android.internal.app.ResolverListAdapter resolverListAdapter, boolean z) {
        if (shouldSkipRebuild(resolverListAdapter)) {
            resolverListAdapter.postListReadyRunnable(z, true);
            return false;
        }
        return resolverListAdapter.rebuildList(z);
    }

    private boolean shouldSkipRebuild(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState emptyState = this.mEmptyStateProvider.getEmptyState(resolverListAdapter);
        return emptyState != null && emptyState.shouldSkipDataRebuild();
    }

    void showEmptyResolverListEmptyState(final com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        android.view.View.OnClickListener onClickListener;
        final com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState emptyState = this.mEmptyStateProvider.getEmptyState(resolverListAdapter);
        if (emptyState == null) {
            return;
        }
        emptyState.onEmptyStateShown();
        if (emptyState.getButtonClickListener() == null) {
            onClickListener = null;
        } else {
            onClickListener = new android.view.View.OnClickListener() { // from class: com.android.internal.app.AbstractMultiProfilePagerAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(android.view.View view) {
                    com.android.internal.app.AbstractMultiProfilePagerAdapter.this.lambda$showEmptyResolverListEmptyState$1(emptyState, resolverListAdapter, view);
                }
            };
        }
        showEmptyState(resolverListAdapter, emptyState, onClickListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEmptyResolverListEmptyState$1(com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState emptyState, final com.android.internal.app.ResolverListAdapter resolverListAdapter, android.view.View view) {
        emptyState.getButtonClickListener().onClick(new com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState.TabControl() { // from class: com.android.internal.app.AbstractMultiProfilePagerAdapter$$ExternalSyntheticLambda1
            @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState.TabControl
            public final void showSpinner() {
                com.android.internal.app.AbstractMultiProfilePagerAdapter.this.lambda$showEmptyResolverListEmptyState$0(resolverListAdapter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEmptyResolverListEmptyState$0(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        showSpinner(getItem(userHandleToPageIndex(resolverListAdapter.getUserHandle())).getEmptyStateView());
    }

    public static class MyUserIdProvider {
        public int getMyUserId() {
            return android.os.UserHandle.myUserId();
        }
    }

    public static class CrossProfileIntentsChecker {
        private final android.content.ContentResolver mContentResolver;

        public CrossProfileIntentsChecker(android.content.ContentResolver contentResolver) {
            this.mContentResolver = contentResolver;
        }

        public boolean hasCrossProfileIntents(java.util.List<android.content.Intent> list, final int i, final int i2) {
            final android.content.pm.IPackageManager packageManager = android.app.AppGlobals.getPackageManager();
            return list.stream().anyMatch(new java.util.function.Predicate() { // from class: com.android.internal.app.AbstractMultiProfilePagerAdapter$CrossProfileIntentsChecker$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$hasCrossProfileIntents$0;
                    lambda$hasCrossProfileIntents$0 = com.android.internal.app.AbstractMultiProfilePagerAdapter.CrossProfileIntentsChecker.this.lambda$hasCrossProfileIntents$0(i, i2, packageManager, (android.content.Intent) obj);
                    return lambda$hasCrossProfileIntents$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$hasCrossProfileIntents$0(int i, int i2, android.content.pm.IPackageManager iPackageManager, android.content.Intent intent) {
            return com.android.internal.app.IntentForwarderActivity.canForward(intent, i, i2, iPackageManager, this.mContentResolver) != null;
        }
    }

    protected void showEmptyState(com.android.internal.app.ResolverListAdapter resolverListAdapter, com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState emptyState, android.view.View.OnClickListener onClickListener) {
        com.android.internal.app.AbstractMultiProfilePagerAdapter.ProfileDescriptor item = getItem(userHandleToPageIndex(resolverListAdapter.getUserHandle()));
        item.rootView.findViewById(com.android.internal.R.id.resolver_list).setVisibility(8);
        android.view.ViewGroup emptyStateView = item.getEmptyStateView();
        resetViewVisibilitiesForEmptyState(emptyStateView);
        emptyStateView.setVisibility(0);
        setupContainerPadding(emptyStateView.findViewById(com.android.internal.R.id.resolver_empty_state_container));
        android.widget.TextView textView = (android.widget.TextView) emptyStateView.findViewById(com.android.internal.R.id.resolver_empty_state_title);
        java.lang.String title = emptyState.getTitle();
        if (title != null) {
            textView.setVisibility(0);
            textView.lambda$setTextAsync$0(title);
        } else {
            textView.setVisibility(8);
        }
        android.widget.TextView textView2 = (android.widget.TextView) emptyStateView.findViewById(com.android.internal.R.id.resolver_empty_state_subtitle);
        java.lang.String subtitle = emptyState.getSubtitle();
        if (subtitle != null) {
            textView2.setVisibility(0);
            textView2.lambda$setTextAsync$0(subtitle);
        } else {
            textView2.setVisibility(8);
        }
        emptyStateView.findViewById(16908292).setVisibility(emptyState.useDefaultEmptyView() ? 0 : 8);
        android.widget.Button button = (android.widget.Button) emptyStateView.findViewById(com.android.internal.R.id.resolver_empty_state_button);
        button.setVisibility(onClickListener != null ? 0 : 8);
        button.setOnClickListener(onClickListener);
        resolverListAdapter.markTabLoaded();
    }

    protected void setupContainerPadding(android.view.View view) {
    }

    private void showSpinner(android.view.View view) {
        view.findViewById(com.android.internal.R.id.resolver_empty_state_title).setVisibility(4);
        view.findViewById(com.android.internal.R.id.resolver_empty_state_button).setVisibility(4);
        view.findViewById(com.android.internal.R.id.resolver_empty_state_progress).setVisibility(0);
        view.findViewById(16908292).setVisibility(8);
    }

    private void resetViewVisibilitiesForEmptyState(android.view.View view) {
        view.findViewById(com.android.internal.R.id.resolver_empty_state_title).setVisibility(0);
        view.findViewById(com.android.internal.R.id.resolver_empty_state_subtitle).setVisibility(0);
        view.findViewById(com.android.internal.R.id.resolver_empty_state_button).setVisibility(4);
        view.findViewById(com.android.internal.R.id.resolver_empty_state_progress).setVisibility(8);
        view.findViewById(16908292).setVisibility(8);
    }

    protected void showListView(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        com.android.internal.app.AbstractMultiProfilePagerAdapter.ProfileDescriptor item = getItem(userHandleToPageIndex(resolverListAdapter.getUserHandle()));
        item.rootView.findViewById(com.android.internal.R.id.resolver_list).setVisibility(0);
        item.rootView.findViewById(com.android.internal.R.id.resolver_empty_state).setVisibility(8);
    }

    boolean shouldShowEmptyStateScreen(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        return (resolverListAdapter.getUnfilteredCount() == 0 && resolverListAdapter.getPlaceholderCount() == 0) || (resolverListAdapter.getUserHandle().equals(this.mWorkProfileUserHandle) && isQuietModeEnabled(this.mWorkProfileUserHandle));
    }

    public static class ProfileDescriptor {
        private final android.view.ViewGroup mEmptyStateView;
        public final android.view.ViewGroup rootView;

        ProfileDescriptor(android.view.ViewGroup viewGroup) {
            this.rootView = viewGroup;
            this.mEmptyStateView = (android.view.ViewGroup) viewGroup.findViewById(com.android.internal.R.id.resolver_empty_state);
        }

        protected android.view.ViewGroup getEmptyStateView() {
            return this.mEmptyStateView;
        }
    }

    public interface EmptyStateProvider {
        default com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState getEmptyState(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
            return null;
        }
    }

    public static class CompositeEmptyStateProvider implements com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider {
        private final com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider[] mProviders;

        public CompositeEmptyStateProvider(com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider... emptyStateProviderArr) {
            this.mProviders = emptyStateProviderArr;
        }

        @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider
        public com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState getEmptyState(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
            for (com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider emptyStateProvider : this.mProviders) {
                com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState emptyState = emptyStateProvider.getEmptyState(resolverListAdapter);
                if (emptyState != null) {
                    return emptyState;
                }
            }
            return null;
        }
    }

    public interface EmptyState {

        public interface ClickListener {
            void onClick(com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState.TabControl tabControl);
        }

        public interface TabControl {
            void showSpinner();
        }

        default java.lang.String getTitle() {
            return null;
        }

        default java.lang.String getSubtitle() {
            return null;
        }

        default com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyState.ClickListener getButtonClickListener() {
            return null;
        }

        default boolean useDefaultEmptyView() {
            return false;
        }

        default boolean shouldSkipDataRebuild() {
            return false;
        }

        default void onEmptyStateShown() {
        }
    }
}
