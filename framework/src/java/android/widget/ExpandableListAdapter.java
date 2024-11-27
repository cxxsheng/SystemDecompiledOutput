package android.widget;

/* loaded from: classes4.dex */
public interface ExpandableListAdapter {
    boolean areAllItemsEnabled();

    java.lang.Object getChild(int i, int i2);

    long getChildId(int i, int i2);

    android.view.View getChildView(int i, int i2, boolean z, android.view.View view, android.view.ViewGroup viewGroup);

    int getChildrenCount(int i);

    long getCombinedChildId(long j, long j2);

    long getCombinedGroupId(long j);

    java.lang.Object getGroup(int i);

    int getGroupCount();

    long getGroupId(int i);

    android.view.View getGroupView(int i, boolean z, android.view.View view, android.view.ViewGroup viewGroup);

    boolean hasStableIds();

    boolean isChildSelectable(int i, int i2);

    boolean isEmpty();

    void onGroupCollapsed(int i);

    void onGroupExpanded(int i);

    void registerDataSetObserver(android.database.DataSetObserver dataSetObserver);

    void unregisterDataSetObserver(android.database.DataSetObserver dataSetObserver);
}
