package android.widget;

/* loaded from: classes4.dex */
public class HeaderViewListAdapter implements android.widget.WrapperListAdapter, android.widget.Filterable {
    static final java.util.ArrayList<android.widget.ListView.FixedViewInfo> EMPTY_INFO_LIST = new java.util.ArrayList<>();
    private final android.widget.ListAdapter mAdapter;
    boolean mAreAllFixedViewsSelectable;
    java.util.ArrayList<android.widget.ListView.FixedViewInfo> mFooterViewInfos;
    java.util.ArrayList<android.widget.ListView.FixedViewInfo> mHeaderViewInfos;
    private final boolean mIsFilterable;

    public HeaderViewListAdapter(java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList, java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList2, android.widget.ListAdapter listAdapter) {
        this.mAdapter = listAdapter;
        this.mIsFilterable = listAdapter instanceof android.widget.Filterable;
        if (arrayList == null) {
            this.mHeaderViewInfos = EMPTY_INFO_LIST;
        } else {
            this.mHeaderViewInfos = arrayList;
        }
        if (arrayList2 == null) {
            this.mFooterViewInfos = EMPTY_INFO_LIST;
        } else {
            this.mFooterViewInfos = arrayList2;
        }
        this.mAreAllFixedViewsSelectable = areAllListInfosSelectable(this.mHeaderViewInfos) && areAllListInfosSelectable(this.mFooterViewInfos);
    }

    public int getHeadersCount() {
        return this.mHeaderViewInfos.size();
    }

    public int getFootersCount() {
        return this.mFooterViewInfos.size();
    }

    @Override // android.widget.Adapter
    public boolean isEmpty() {
        return this.mAdapter == null || this.mAdapter.isEmpty();
    }

    private boolean areAllListInfosSelectable(java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList) {
        if (arrayList != null) {
            java.util.Iterator<android.widget.ListView.FixedViewInfo> it = arrayList.iterator();
            while (it.hasNext()) {
                if (!it.next().isSelectable) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    public boolean removeHeader(android.view.View view) {
        boolean z = false;
        for (int i = 0; i < this.mHeaderViewInfos.size(); i++) {
            if (this.mHeaderViewInfos.get(i).view == view) {
                this.mHeaderViewInfos.remove(i);
                if (areAllListInfosSelectable(this.mHeaderViewInfos) && areAllListInfosSelectable(this.mFooterViewInfos)) {
                    z = true;
                }
                this.mAreAllFixedViewsSelectable = z;
                return true;
            }
        }
        return false;
    }

    public boolean removeFooter(android.view.View view) {
        boolean z = false;
        for (int i = 0; i < this.mFooterViewInfos.size(); i++) {
            if (this.mFooterViewInfos.get(i).view == view) {
                this.mFooterViewInfos.remove(i);
                if (areAllListInfosSelectable(this.mHeaderViewInfos) && areAllListInfosSelectable(this.mFooterViewInfos)) {
                    z = true;
                }
                this.mAreAllFixedViewsSelectable = z;
                return true;
            }
        }
        return false;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        if (this.mAdapter != null) {
            return getFootersCount() + getHeadersCount() + this.mAdapter.getCount();
        }
        return getFootersCount() + getHeadersCount();
    }

    @Override // android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        if (this.mAdapter != null) {
            return this.mAreAllFixedViewsSelectable && this.mAdapter.areAllItemsEnabled();
        }
        return true;
    }

    @Override // android.widget.ListAdapter
    public boolean isEnabled(int i) {
        int i2;
        int headersCount = getHeadersCount();
        if (i < headersCount) {
            return this.mHeaderViewInfos.get(i).isSelectable;
        }
        int i3 = i - headersCount;
        if (this.mAdapter == null) {
            i2 = 0;
        } else {
            i2 = this.mAdapter.getCount();
            if (i3 < i2) {
                return this.mAdapter.isEnabled(i3);
            }
        }
        return this.mFooterViewInfos.get(i3 - i2).isSelectable;
    }

    @Override // android.widget.Adapter
    public java.lang.Object getItem(int i) {
        int i2;
        int headersCount = getHeadersCount();
        if (i < headersCount) {
            return this.mHeaderViewInfos.get(i).data;
        }
        int i3 = i - headersCount;
        if (this.mAdapter == null) {
            i2 = 0;
        } else {
            i2 = this.mAdapter.getCount();
            if (i3 < i2) {
                return this.mAdapter.getItem(i3);
            }
        }
        return this.mFooterViewInfos.get(i3 - i2).data;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        int i2;
        int headersCount = getHeadersCount();
        if (this.mAdapter != null && i >= headersCount && (i2 = i - headersCount) < this.mAdapter.getCount()) {
            return this.mAdapter.getItemId(i2);
        }
        return -1L;
    }

    @Override // android.widget.Adapter
    public boolean hasStableIds() {
        if (this.mAdapter != null) {
            return this.mAdapter.hasStableIds();
        }
        return false;
    }

    @Override // android.widget.Adapter
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        int i2;
        int headersCount = getHeadersCount();
        if (i < headersCount) {
            return this.mHeaderViewInfos.get(i).view;
        }
        int i3 = i - headersCount;
        if (this.mAdapter == null) {
            i2 = 0;
        } else {
            i2 = this.mAdapter.getCount();
            if (i3 < i2) {
                return this.mAdapter.getView(i3, view, viewGroup);
            }
        }
        return this.mFooterViewInfos.get(i3 - i2).view;
    }

    @Override // android.widget.Adapter
    public int getItemViewType(int i) {
        int i2;
        int headersCount = getHeadersCount();
        if (this.mAdapter != null && i >= headersCount && (i2 = i - headersCount) < this.mAdapter.getCount()) {
            return this.mAdapter.getItemViewType(i2);
        }
        return -2;
    }

    @Override // android.widget.Adapter
    public int getViewTypeCount() {
        if (this.mAdapter != null) {
            return this.mAdapter.getViewTypeCount();
        }
        return 1;
    }

    @Override // android.widget.Adapter
    public void registerDataSetObserver(android.database.DataSetObserver dataSetObserver) {
        if (this.mAdapter != null) {
            this.mAdapter.registerDataSetObserver(dataSetObserver);
        }
    }

    @Override // android.widget.Adapter
    public void unregisterDataSetObserver(android.database.DataSetObserver dataSetObserver) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(dataSetObserver);
        }
    }

    @Override // android.widget.Filterable
    public android.widget.Filter getFilter() {
        if (this.mIsFilterable) {
            return ((android.widget.Filterable) this.mAdapter).getFilter();
        }
        return null;
    }

    @Override // android.widget.WrapperListAdapter
    public android.widget.ListAdapter getWrappedAdapter() {
        return this.mAdapter;
    }
}
