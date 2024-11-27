package android.widget;

/* loaded from: classes4.dex */
public abstract class BaseExpandableListAdapter implements android.widget.ExpandableListAdapter, android.widget.HeterogeneousExpandableList {
    private final android.database.DataSetObservable mDataSetObservable = new android.database.DataSetObservable();

    @Override // android.widget.ExpandableListAdapter
    public void registerDataSetObserver(android.database.DataSetObserver dataSetObserver) {
        this.mDataSetObservable.registerObserver(dataSetObserver);
    }

    @Override // android.widget.ExpandableListAdapter
    public void unregisterDataSetObserver(android.database.DataSetObserver dataSetObserver) {
        this.mDataSetObservable.unregisterObserver(dataSetObserver);
    }

    public void notifyDataSetInvalidated() {
        this.mDataSetObservable.notifyInvalidated();
    }

    public void notifyDataSetChanged() {
        this.mDataSetObservable.notifyChanged();
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override // android.widget.ExpandableListAdapter
    public void onGroupCollapsed(int i) {
    }

    @Override // android.widget.ExpandableListAdapter
    public void onGroupExpanded(int i) {
    }

    @Override // android.widget.ExpandableListAdapter
    public long getCombinedChildId(long j, long j2) {
        return ((j & 2147483647L) << 32) | Long.MIN_VALUE | (j2 & (-1));
    }

    @Override // android.widget.ExpandableListAdapter
    public long getCombinedGroupId(long j) {
        return (j & 2147483647L) << 32;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isEmpty() {
        return getGroupCount() == 0;
    }

    @Override // android.widget.HeterogeneousExpandableList
    public int getChildType(int i, int i2) {
        return 0;
    }

    @Override // android.widget.HeterogeneousExpandableList
    public int getChildTypeCount() {
        return 1;
    }

    @Override // android.widget.HeterogeneousExpandableList
    public int getGroupType(int i) {
        return 0;
    }

    @Override // android.widget.HeterogeneousExpandableList
    public int getGroupTypeCount() {
        return 1;
    }
}
