package com.android.internal.app;

/* loaded from: classes4.dex */
public class ResolverMultiProfilePagerAdapter extends com.android.internal.app.AbstractMultiProfilePagerAdapter {
    private final com.android.internal.app.ResolverMultiProfilePagerAdapter.ResolverProfileDescriptor[] mItems;
    private boolean mUseLayoutWithDefault;

    ResolverMultiProfilePagerAdapter(android.content.Context context, com.android.internal.app.ResolverListAdapter resolverListAdapter, com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider emptyStateProvider, com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager quietModeManager, android.os.UserHandle userHandle, android.os.UserHandle userHandle2) {
        super(context, 0, emptyStateProvider, quietModeManager, userHandle, userHandle2);
        this.mItems = new com.android.internal.app.ResolverMultiProfilePagerAdapter.ResolverProfileDescriptor[]{createProfileDescriptor(resolverListAdapter)};
    }

    ResolverMultiProfilePagerAdapter(android.content.Context context, com.android.internal.app.ResolverListAdapter resolverListAdapter, com.android.internal.app.ResolverListAdapter resolverListAdapter2, com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider emptyStateProvider, com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager quietModeManager, int i, android.os.UserHandle userHandle, android.os.UserHandle userHandle2) {
        super(context, i, emptyStateProvider, quietModeManager, userHandle, userHandle2);
        this.mItems = new com.android.internal.app.ResolverMultiProfilePagerAdapter.ResolverProfileDescriptor[]{createProfileDescriptor(resolverListAdapter), createProfileDescriptor(resolverListAdapter2)};
    }

    private com.android.internal.app.ResolverMultiProfilePagerAdapter.ResolverProfileDescriptor createProfileDescriptor(com.android.internal.app.ResolverListAdapter resolverListAdapter) {
        return new com.android.internal.app.ResolverMultiProfilePagerAdapter.ResolverProfileDescriptor((android.view.ViewGroup) android.view.LayoutInflater.from(getContext()).inflate(com.android.internal.R.layout.resolver_list_per_profile, (android.view.ViewGroup) null, false), resolverListAdapter);
    }

    android.widget.ListView getListViewForIndex(int i) {
        return getItem(i).listView;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ResolverMultiProfilePagerAdapter.ResolverProfileDescriptor getItem(int i) {
        return this.mItems[i];
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    int getItemCount() {
        return this.mItems.length;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    void setupListAdapter(int i) {
        getItem(i).listView.setAdapter((android.widget.ListAdapter) getItem(i).resolverListAdapter);
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ResolverListAdapter getAdapterForIndex(int i) {
        return this.mItems[i].resolverListAdapter;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter, com.android.internal.widget.PagerAdapter
    public android.view.ViewGroup instantiateItem(android.view.ViewGroup viewGroup, int i) {
        setupListAdapter(i);
        return super.instantiateItem(viewGroup, i);
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    com.android.internal.app.ResolverListAdapter getListAdapterForUserHandle(android.os.UserHandle userHandle) {
        if (getPersonalListAdapter().getUserHandle().equals(userHandle) || userHandle.equals(getCloneUserHandle())) {
            return getPersonalListAdapter();
        }
        if (getWorkListAdapter() != null && getWorkListAdapter().getUserHandle().equals(userHandle)) {
            return getWorkListAdapter();
        }
        return null;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ResolverListAdapter getActiveListAdapter() {
        return getAdapterForIndex(getCurrentPage());
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ResolverListAdapter getInactiveListAdapter() {
        if (getCount() == 1) {
            return null;
        }
        return getAdapterForIndex(1 - getCurrentPage());
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ResolverListAdapter getPersonalListAdapter() {
        return getAdapterForIndex(0);
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ResolverListAdapter getWorkListAdapter() {
        if (getCount() == 1) {
            return null;
        }
        return getAdapterForIndex(1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ResolverListAdapter getCurrentRootAdapter() {
        return getActiveListAdapter();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public android.widget.ListView getActiveAdapterView() {
        return getListViewForIndex(getCurrentPage());
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    android.view.ViewGroup getInactiveAdapterView() {
        if (getCount() == 1) {
            return null;
        }
        return getListViewForIndex(1 - getCurrentPage());
    }

    void setUseLayoutWithDefault(boolean z) {
        this.mUseLayoutWithDefault = z;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    protected void setupContainerPadding(android.view.View view) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), this.mUseLayoutWithDefault ? view.getPaddingBottom() : 0);
    }

    class ResolverProfileDescriptor extends com.android.internal.app.AbstractMultiProfilePagerAdapter.ProfileDescriptor {
        final android.widget.ListView listView;
        private com.android.internal.app.ResolverListAdapter resolverListAdapter;

        ResolverProfileDescriptor(android.view.ViewGroup viewGroup, com.android.internal.app.ResolverListAdapter resolverListAdapter) {
            super(viewGroup);
            this.resolverListAdapter = resolverListAdapter;
            this.listView = (android.widget.ListView) viewGroup.findViewById(com.android.internal.R.id.resolver_list);
        }
    }
}
