package com.android.internal.app;

/* loaded from: classes4.dex */
public class ChooserMultiProfilePagerAdapter extends com.android.internal.app.AbstractMultiProfilePagerAdapter {
    private static final int SINGLE_CELL_SPAN_SIZE = 1;
    private int mBottomOffset;
    private final com.android.internal.app.ChooserMultiProfilePagerAdapter.ChooserProfileDescriptor[] mItems;
    private int mMaxTargetsPerRow;

    ChooserMultiProfilePagerAdapter(android.content.Context context, com.android.internal.app.ChooserActivity.ChooserGridAdapter chooserGridAdapter, com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider emptyStateProvider, com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager quietModeManager, android.os.UserHandle userHandle, android.os.UserHandle userHandle2, int i) {
        super(context, 0, emptyStateProvider, quietModeManager, userHandle, userHandle2);
        this.mItems = new com.android.internal.app.ChooserMultiProfilePagerAdapter.ChooserProfileDescriptor[]{createProfileDescriptor(chooserGridAdapter)};
        this.mMaxTargetsPerRow = i;
    }

    ChooserMultiProfilePagerAdapter(android.content.Context context, com.android.internal.app.ChooserActivity.ChooserGridAdapter chooserGridAdapter, com.android.internal.app.ChooserActivity.ChooserGridAdapter chooserGridAdapter2, com.android.internal.app.AbstractMultiProfilePagerAdapter.EmptyStateProvider emptyStateProvider, com.android.internal.app.AbstractMultiProfilePagerAdapter.QuietModeManager quietModeManager, int i, android.os.UserHandle userHandle, android.os.UserHandle userHandle2, int i2) {
        super(context, i, emptyStateProvider, quietModeManager, userHandle, userHandle2);
        this.mItems = new com.android.internal.app.ChooserMultiProfilePagerAdapter.ChooserProfileDescriptor[]{createProfileDescriptor(chooserGridAdapter), createProfileDescriptor(chooserGridAdapter2)};
        this.mMaxTargetsPerRow = i2;
    }

    private com.android.internal.app.ChooserMultiProfilePagerAdapter.ChooserProfileDescriptor createProfileDescriptor(com.android.internal.app.ChooserActivity.ChooserGridAdapter chooserGridAdapter) {
        com.android.internal.app.ChooserMultiProfilePagerAdapter.ChooserProfileDescriptor chooserProfileDescriptor = new com.android.internal.app.ChooserMultiProfilePagerAdapter.ChooserProfileDescriptor((android.view.ViewGroup) android.view.LayoutInflater.from(getContext()).inflate(com.android.internal.R.layout.chooser_list_per_profile, (android.view.ViewGroup) null, false), chooserGridAdapter);
        chooserProfileDescriptor.recyclerView.setAccessibilityDelegateCompat(new com.android.internal.app.ChooserRecyclerViewAccessibilityDelegate(chooserProfileDescriptor.recyclerView));
        return chooserProfileDescriptor;
    }

    public void setMaxTargetsPerRow(int i) {
        this.mMaxTargetsPerRow = i;
    }

    com.android.internal.widget.RecyclerView getListViewForIndex(int i) {
        return getItem(i).recyclerView;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ChooserMultiProfilePagerAdapter.ChooserProfileDescriptor getItem(int i) {
        return this.mItems[i];
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    int getItemCount() {
        return this.mItems.length;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ChooserActivity.ChooserGridAdapter getAdapterForIndex(int i) {
        return this.mItems[i].chooserGridAdapter;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ChooserListAdapter getListAdapterForUserHandle(android.os.UserHandle userHandle) {
        if (getPersonalListAdapter().getUserHandle().equals(userHandle) || userHandle.equals(getCloneUserHandle())) {
            return getPersonalListAdapter();
        }
        if (getWorkListAdapter() != null && getWorkListAdapter().getUserHandle().equals(userHandle)) {
            return getWorkListAdapter();
        }
        return null;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    void setupListAdapter(int i) {
        com.android.internal.widget.RecyclerView recyclerView = getItem(i).recyclerView;
        final com.android.internal.app.ChooserActivity.ChooserGridAdapter chooserGridAdapter = getItem(i).chooserGridAdapter;
        final com.android.internal.widget.GridLayoutManager gridLayoutManager = (com.android.internal.widget.GridLayoutManager) recyclerView.getLayoutManager();
        gridLayoutManager.setSpanCount(this.mMaxTargetsPerRow);
        gridLayoutManager.setSpanSizeLookup(new com.android.internal.widget.GridLayoutManager.SpanSizeLookup() { // from class: com.android.internal.app.ChooserMultiProfilePagerAdapter.1
            @Override // com.android.internal.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i2) {
                if (chooserGridAdapter.shouldCellSpan(i2)) {
                    return 1;
                }
                return gridLayoutManager.getSpanCount();
            }
        });
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ChooserListAdapter getActiveListAdapter() {
        return getAdapterForIndex(getCurrentPage()).getListAdapter();
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ChooserListAdapter getInactiveListAdapter() {
        if (getCount() == 1) {
            return null;
        }
        return getAdapterForIndex(1 - getCurrentPage()).getListAdapter();
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ChooserListAdapter getPersonalListAdapter() {
        return getAdapterForIndex(0).getListAdapter();
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ChooserListAdapter getWorkListAdapter() {
        if (getCount() == 1) {
            return null;
        }
        return getAdapterForIndex(1).getListAdapter();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.app.ChooserActivity.ChooserGridAdapter getCurrentRootAdapter() {
        return getAdapterForIndex(getCurrentPage());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.widget.RecyclerView getActiveAdapterView() {
        return getListViewForIndex(getCurrentPage());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    public com.android.internal.widget.RecyclerView getInactiveAdapterView() {
        if (getCount() == 1) {
            return null;
        }
        return getListViewForIndex(1 - getCurrentPage());
    }

    void setEmptyStateBottomOffset(int i) {
        this.mBottomOffset = i;
    }

    @Override // com.android.internal.app.AbstractMultiProfilePagerAdapter
    protected void setupContainerPadding(android.view.View view) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), getContext().getResources().getDimensionPixelSize(com.android.internal.R.dimen.resolver_empty_state_container_padding_bottom) + this.mBottomOffset);
    }

    class ChooserProfileDescriptor extends com.android.internal.app.AbstractMultiProfilePagerAdapter.ProfileDescriptor {
        private com.android.internal.app.ChooserActivity.ChooserGridAdapter chooserGridAdapter;
        private com.android.internal.widget.RecyclerView recyclerView;

        ChooserProfileDescriptor(android.view.ViewGroup viewGroup, com.android.internal.app.ChooserActivity.ChooserGridAdapter chooserGridAdapter) {
            super(viewGroup);
            this.chooserGridAdapter = chooserGridAdapter;
            this.recyclerView = (com.android.internal.widget.RecyclerView) viewGroup.findViewById(com.android.internal.R.id.resolver_list);
        }
    }
}
