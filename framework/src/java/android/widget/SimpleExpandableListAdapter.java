package android.widget;

/* loaded from: classes4.dex */
public class SimpleExpandableListAdapter extends android.widget.BaseExpandableListAdapter {
    private java.util.List<? extends java.util.List<? extends java.util.Map<java.lang.String, ?>>> mChildData;
    private java.lang.String[] mChildFrom;
    private int mChildLayout;
    private int[] mChildTo;
    private int mCollapsedGroupLayout;
    private int mExpandedGroupLayout;
    private java.util.List<? extends java.util.Map<java.lang.String, ?>> mGroupData;
    private java.lang.String[] mGroupFrom;
    private int[] mGroupTo;
    private android.view.LayoutInflater mInflater;
    private int mLastChildLayout;

    public SimpleExpandableListAdapter(android.content.Context context, java.util.List<? extends java.util.Map<java.lang.String, ?>> list, int i, java.lang.String[] strArr, int[] iArr, java.util.List<? extends java.util.List<? extends java.util.Map<java.lang.String, ?>>> list2, int i2, java.lang.String[] strArr2, int[] iArr2) {
        this(context, list, i, i, strArr, iArr, list2, i2, i2, strArr2, iArr2);
    }

    public SimpleExpandableListAdapter(android.content.Context context, java.util.List<? extends java.util.Map<java.lang.String, ?>> list, int i, int i2, java.lang.String[] strArr, int[] iArr, java.util.List<? extends java.util.List<? extends java.util.Map<java.lang.String, ?>>> list2, int i3, java.lang.String[] strArr2, int[] iArr2) {
        this(context, list, i, i2, strArr, iArr, list2, i3, i3, strArr2, iArr2);
    }

    public SimpleExpandableListAdapter(android.content.Context context, java.util.List<? extends java.util.Map<java.lang.String, ?>> list, int i, int i2, java.lang.String[] strArr, int[] iArr, java.util.List<? extends java.util.List<? extends java.util.Map<java.lang.String, ?>>> list2, int i3, int i4, java.lang.String[] strArr2, int[] iArr2) {
        this.mGroupData = list;
        this.mExpandedGroupLayout = i;
        this.mCollapsedGroupLayout = i2;
        this.mGroupFrom = strArr;
        this.mGroupTo = iArr;
        this.mChildData = list2;
        this.mChildLayout = i3;
        this.mLastChildLayout = i4;
        this.mChildFrom = strArr2;
        this.mChildTo = iArr2;
        this.mInflater = (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override // android.widget.ExpandableListAdapter
    public java.lang.Object getChild(int i, int i2) {
        return this.mChildData.get(i).get(i2);
    }

    @Override // android.widget.ExpandableListAdapter
    public long getChildId(int i, int i2) {
        return i2;
    }

    @Override // android.widget.ExpandableListAdapter
    public android.view.View getChildView(int i, int i2, boolean z, android.view.View view, android.view.ViewGroup viewGroup) {
        if (view == null) {
            view = newChildView(z, viewGroup);
        }
        bindView(view, this.mChildData.get(i).get(i2), this.mChildFrom, this.mChildTo);
        return view;
    }

    public android.view.View newChildView(boolean z, android.view.ViewGroup viewGroup) {
        return this.mInflater.inflate(z ? this.mLastChildLayout : this.mChildLayout, viewGroup, false);
    }

    private void bindView(android.view.View view, java.util.Map<java.lang.String, ?> map, java.lang.String[] strArr, int[] iArr) {
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            android.widget.TextView textView = (android.widget.TextView) view.findViewById(iArr[i]);
            if (textView != null) {
                textView.lambda$setTextAsync$0((java.lang.String) map.get(strArr[i]));
            }
        }
    }

    @Override // android.widget.ExpandableListAdapter
    public int getChildrenCount(int i) {
        return this.mChildData.get(i).size();
    }

    @Override // android.widget.ExpandableListAdapter
    public java.lang.Object getGroup(int i) {
        return this.mGroupData.get(i);
    }

    @Override // android.widget.ExpandableListAdapter
    public int getGroupCount() {
        return this.mGroupData.size();
    }

    @Override // android.widget.ExpandableListAdapter
    public long getGroupId(int i) {
        return i;
    }

    @Override // android.widget.ExpandableListAdapter
    public android.view.View getGroupView(int i, boolean z, android.view.View view, android.view.ViewGroup viewGroup) {
        if (view == null) {
            view = newGroupView(z, viewGroup);
        }
        bindView(view, this.mGroupData.get(i), this.mGroupFrom, this.mGroupTo);
        return view;
    }

    public android.view.View newGroupView(boolean z, android.view.ViewGroup viewGroup) {
        return this.mInflater.inflate(z ? this.mExpandedGroupLayout : this.mCollapsedGroupLayout, viewGroup, false);
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    @Override // android.widget.ExpandableListAdapter
    public boolean hasStableIds() {
        return true;
    }
}
