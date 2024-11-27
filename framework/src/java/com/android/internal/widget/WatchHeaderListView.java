package com.android.internal.widget;

/* loaded from: classes5.dex */
public class WatchHeaderListView extends android.widget.ListView {
    private android.view.View mTopPanel;

    public WatchHeaderListView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public WatchHeaderListView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public WatchHeaderListView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.ListView
    protected android.widget.HeaderViewListAdapter wrapHeaderListAdapterInternal(java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList, java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList2, android.widget.ListAdapter listAdapter) {
        return new com.android.internal.widget.WatchHeaderListView.WatchHeaderListAdapter(arrayList, arrayList2, listAdapter);
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.ViewManager
    public void addView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        if (this.mTopPanel == null) {
            setTopPanel(view);
            return;
        }
        throw new java.lang.IllegalStateException("WatchHeaderListView can host only one header");
    }

    public void setTopPanel(android.view.View view) {
        this.mTopPanel = view;
        wrapAdapterIfNecessary();
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView
    public void setAdapter(android.widget.ListAdapter listAdapter) {
        super.setAdapter(listAdapter);
        wrapAdapterIfNecessary();
    }

    @Override // android.widget.ListView, android.view.ViewGroup, android.view.View
    protected android.view.View findViewTraversal(int i) {
        android.view.View findViewTraversal = super.findViewTraversal(i);
        if (findViewTraversal == null && this.mTopPanel != null && !this.mTopPanel.isRootNamespace()) {
            return this.mTopPanel.findViewById(i);
        }
        return findViewTraversal;
    }

    @Override // android.widget.ListView, android.view.ViewGroup, android.view.View
    protected android.view.View findViewWithTagTraversal(java.lang.Object obj) {
        android.view.View findViewWithTagTraversal = super.findViewWithTagTraversal(obj);
        if (findViewWithTagTraversal == null && this.mTopPanel != null && !this.mTopPanel.isRootNamespace()) {
            return this.mTopPanel.findViewWithTag(obj);
        }
        return findViewWithTagTraversal;
    }

    @Override // android.widget.ListView, android.view.ViewGroup, android.view.View
    protected <T extends android.view.View> T findViewByPredicateTraversal(java.util.function.Predicate<android.view.View> predicate, android.view.View view) {
        T t = (T) super.findViewByPredicateTraversal(predicate, view);
        if (t == null && this.mTopPanel != null && this.mTopPanel != view && !this.mTopPanel.isRootNamespace()) {
            return (T) this.mTopPanel.findViewByPredicate(predicate);
        }
        return t;
    }

    @Override // android.widget.ListView, android.widget.AbsListView
    public int getHeaderViewsCount() {
        if (this.mTopPanel == null) {
            return super.getHeaderViewsCount();
        }
        return super.getHeaderViewsCount() + (this.mTopPanel.getVisibility() == 8 ? 0 : 1);
    }

    private void wrapAdapterIfNecessary() {
        android.widget.ListAdapter adapter = getAdapter();
        if (adapter != null && this.mTopPanel != null) {
            if (!(adapter instanceof com.android.internal.widget.WatchHeaderListView.WatchHeaderListAdapter)) {
                wrapHeaderListAdapterInternal();
            }
            ((com.android.internal.widget.WatchHeaderListView.WatchHeaderListAdapter) getAdapter()).setTopPanel(this.mTopPanel);
            dispatchDataSetObserverOnChangedInternal();
        }
    }

    private static class WatchHeaderListAdapter extends android.widget.HeaderViewListAdapter {
        private android.view.View mTopPanel;

        public WatchHeaderListAdapter(java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList, java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList2, android.widget.ListAdapter listAdapter) {
            super(arrayList, arrayList2, listAdapter);
        }

        public void setTopPanel(android.view.View view) {
            this.mTopPanel = view;
        }

        private int getTopPanelCount() {
            return (this.mTopPanel == null || this.mTopPanel.getVisibility() == 8) ? 0 : 1;
        }

        @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
        public int getCount() {
            return super.getCount() + getTopPanelCount();
        }

        @Override // android.widget.HeaderViewListAdapter, android.widget.ListAdapter
        public boolean areAllItemsEnabled() {
            return getTopPanelCount() == 0 && super.areAllItemsEnabled();
        }

        @Override // android.widget.HeaderViewListAdapter, android.widget.ListAdapter
        public boolean isEnabled(int i) {
            int topPanelCount = getTopPanelCount();
            if (i < topPanelCount) {
                return false;
            }
            return super.isEnabled(i - topPanelCount);
        }

        @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
        public java.lang.Object getItem(int i) {
            int topPanelCount = getTopPanelCount();
            if (i < topPanelCount) {
                return null;
            }
            return super.getItem(i - topPanelCount);
        }

        @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
        public long getItemId(int i) {
            int i2;
            int headersCount = getHeadersCount() + getTopPanelCount();
            if (getWrappedAdapter() != null && i >= headersCount && (i2 = i - headersCount) < getWrappedAdapter().getCount()) {
                return getWrappedAdapter().getItemId(i2);
            }
            return -1L;
        }

        @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            int topPanelCount = getTopPanelCount();
            return i < topPanelCount ? this.mTopPanel : super.getView(i - topPanelCount, view, viewGroup);
        }

        @Override // android.widget.HeaderViewListAdapter, android.widget.Adapter
        public int getItemViewType(int i) {
            int i2;
            int headersCount = getHeadersCount() + getTopPanelCount();
            if (getWrappedAdapter() != null && i >= headersCount && (i2 = i - headersCount) < getWrappedAdapter().getCount()) {
                return getWrappedAdapter().getItemViewType(i2);
            }
            return -2;
        }
    }
}
