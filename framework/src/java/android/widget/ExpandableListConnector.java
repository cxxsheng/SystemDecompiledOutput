package android.widget;

/* loaded from: classes4.dex */
class ExpandableListConnector extends android.widget.BaseAdapter implements android.widget.Filterable {
    private android.widget.ExpandableListAdapter mExpandableListAdapter;
    private int mTotalExpChildrenCount;
    private int mMaxExpGroupCount = Integer.MAX_VALUE;
    private final android.database.DataSetObserver mDataSetObserver = new android.widget.ExpandableListConnector.MyDataSetObserver();
    private java.util.ArrayList<android.widget.ExpandableListConnector.GroupMetadata> mExpGroupMetadataList = new java.util.ArrayList<>();

    public ExpandableListConnector(android.widget.ExpandableListAdapter expandableListAdapter) {
        setExpandableListAdapter(expandableListAdapter);
    }

    public void setExpandableListAdapter(android.widget.ExpandableListAdapter expandableListAdapter) {
        if (this.mExpandableListAdapter != null) {
            this.mExpandableListAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        this.mExpandableListAdapter = expandableListAdapter;
        expandableListAdapter.registerDataSetObserver(this.mDataSetObserver);
    }

    android.widget.ExpandableListConnector.PositionMetadata getUnflattenedPos(int i) {
        int i2;
        int i3;
        java.util.ArrayList<android.widget.ExpandableListConnector.GroupMetadata> arrayList = this.mExpGroupMetadataList;
        int size = arrayList.size();
        int i4 = size - 1;
        if (size != 0) {
            int i5 = 0;
            int i6 = i4;
            int i7 = 0;
            while (i5 <= i6) {
                int i8 = ((i6 - i5) / 2) + i5;
                android.widget.ExpandableListConnector.GroupMetadata groupMetadata = arrayList.get(i8);
                if (i > groupMetadata.lastChildFlPos) {
                    i5 = i8 + 1;
                    i7 = i8;
                } else if (i < groupMetadata.flPos) {
                    i6 = i8 - 1;
                    i7 = i8;
                } else {
                    if (i == groupMetadata.flPos) {
                        return android.widget.ExpandableListConnector.PositionMetadata.obtain(i, 2, groupMetadata.gPos, -1, groupMetadata, i8);
                    }
                    if (i > groupMetadata.lastChildFlPos) {
                        i7 = i8;
                    } else {
                        return android.widget.ExpandableListConnector.PositionMetadata.obtain(i, 1, groupMetadata.gPos, i - (groupMetadata.flPos + 1), groupMetadata, i8);
                    }
                }
            }
            if (i5 > i7) {
                android.widget.ExpandableListConnector.GroupMetadata groupMetadata2 = arrayList.get(i5 - 1);
                i2 = i5;
                i3 = (i - groupMetadata2.lastChildFlPos) + groupMetadata2.gPos;
            } else if (i6 < i7) {
                int i9 = i6 + 1;
                android.widget.ExpandableListConnector.GroupMetadata groupMetadata3 = arrayList.get(i9);
                i2 = i9;
                i3 = groupMetadata3.gPos - (groupMetadata3.flPos - i);
            } else {
                throw new java.lang.RuntimeException("Unknown state");
            }
            return android.widget.ExpandableListConnector.PositionMetadata.obtain(i, 2, i3, -1, null, i2);
        }
        return android.widget.ExpandableListConnector.PositionMetadata.obtain(i, 2, i, -1, null, 0);
    }

    android.widget.ExpandableListConnector.PositionMetadata getFlattenedPos(android.widget.ExpandableListPosition expandableListPosition) {
        java.util.ArrayList<android.widget.ExpandableListConnector.GroupMetadata> arrayList = this.mExpGroupMetadataList;
        int size = arrayList.size();
        int i = size - 1;
        if (size == 0) {
            return android.widget.ExpandableListConnector.PositionMetadata.obtain(expandableListPosition.groupPos, expandableListPosition.type, expandableListPosition.groupPos, expandableListPosition.childPos, null, 0);
        }
        int i2 = 0;
        int i3 = 0;
        while (i3 <= i) {
            int i4 = ((i - i3) / 2) + i3;
            android.widget.ExpandableListConnector.GroupMetadata groupMetadata = arrayList.get(i4);
            if (expandableListPosition.groupPos > groupMetadata.gPos) {
                i3 = i4 + 1;
                i2 = i4;
            } else if (expandableListPosition.groupPos < groupMetadata.gPos) {
                i = i4 - 1;
                i2 = i4;
            } else if (expandableListPosition.groupPos != groupMetadata.gPos) {
                i2 = i4;
            } else {
                if (expandableListPosition.type == 2) {
                    return android.widget.ExpandableListConnector.PositionMetadata.obtain(groupMetadata.flPos, expandableListPosition.type, expandableListPosition.groupPos, expandableListPosition.childPos, groupMetadata, i4);
                }
                if (expandableListPosition.type == 1) {
                    return android.widget.ExpandableListConnector.PositionMetadata.obtain(groupMetadata.flPos + expandableListPosition.childPos + 1, expandableListPosition.type, expandableListPosition.groupPos, expandableListPosition.childPos, groupMetadata, i4);
                }
                return null;
            }
        }
        if (expandableListPosition.type != 2) {
            return null;
        }
        if (i3 > i2) {
            android.widget.ExpandableListConnector.GroupMetadata groupMetadata2 = arrayList.get(i3 - 1);
            return android.widget.ExpandableListConnector.PositionMetadata.obtain(groupMetadata2.lastChildFlPos + (expandableListPosition.groupPos - groupMetadata2.gPos), expandableListPosition.type, expandableListPosition.groupPos, expandableListPosition.childPos, null, i3);
        }
        if (i >= i2) {
            return null;
        }
        int i5 = 1 + i;
        android.widget.ExpandableListConnector.GroupMetadata groupMetadata3 = arrayList.get(i5);
        return android.widget.ExpandableListConnector.PositionMetadata.obtain(groupMetadata3.flPos - (groupMetadata3.gPos - expandableListPosition.groupPos), expandableListPosition.type, expandableListPosition.groupPos, expandableListPosition.childPos, null, i5);
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return this.mExpandableListAdapter.areAllItemsEnabled();
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        android.widget.ExpandableListConnector.PositionMetadata unflattenedPos = getUnflattenedPos(i);
        android.widget.ExpandableListPosition expandableListPosition = unflattenedPos.position;
        boolean isChildSelectable = expandableListPosition.type == 1 ? this.mExpandableListAdapter.isChildSelectable(expandableListPosition.groupPos, expandableListPosition.childPos) : true;
        unflattenedPos.recycle();
        return isChildSelectable;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mExpandableListAdapter.getGroupCount() + this.mTotalExpChildrenCount;
    }

    @Override // android.widget.Adapter
    public java.lang.Object getItem(int i) {
        java.lang.Object child;
        android.widget.ExpandableListConnector.PositionMetadata unflattenedPos = getUnflattenedPos(i);
        if (unflattenedPos.position.type == 2) {
            child = this.mExpandableListAdapter.getGroup(unflattenedPos.position.groupPos);
        } else if (unflattenedPos.position.type == 1) {
            child = this.mExpandableListAdapter.getChild(unflattenedPos.position.groupPos, unflattenedPos.position.childPos);
        } else {
            throw new java.lang.RuntimeException("Flat list position is of unknown type");
        }
        unflattenedPos.recycle();
        return child;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        long combinedChildId;
        android.widget.ExpandableListConnector.PositionMetadata unflattenedPos = getUnflattenedPos(i);
        long groupId = this.mExpandableListAdapter.getGroupId(unflattenedPos.position.groupPos);
        if (unflattenedPos.position.type == 2) {
            combinedChildId = this.mExpandableListAdapter.getCombinedGroupId(groupId);
        } else if (unflattenedPos.position.type == 1) {
            combinedChildId = this.mExpandableListAdapter.getCombinedChildId(groupId, this.mExpandableListAdapter.getChildId(unflattenedPos.position.groupPos, unflattenedPos.position.childPos));
        } else {
            throw new java.lang.RuntimeException("Flat list position is of unknown type");
        }
        unflattenedPos.recycle();
        return combinedChildId;
    }

    @Override // android.widget.Adapter
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        android.view.View childView;
        android.widget.ExpandableListConnector.PositionMetadata unflattenedPos = getUnflattenedPos(i);
        if (unflattenedPos.position.type == 2) {
            childView = this.mExpandableListAdapter.getGroupView(unflattenedPos.position.groupPos, unflattenedPos.isExpanded(), view, viewGroup);
        } else {
            if (unflattenedPos.position.type == 1) {
                childView = this.mExpandableListAdapter.getChildView(unflattenedPos.position.groupPos, unflattenedPos.position.childPos, unflattenedPos.groupMetadata.lastChildFlPos == i, view, viewGroup);
            } else {
                throw new java.lang.RuntimeException("Flat list position is of unknown type");
            }
        }
        unflattenedPos.recycle();
        return childView;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        int i2;
        android.widget.ExpandableListConnector.PositionMetadata unflattenedPos = getUnflattenedPos(i);
        android.widget.ExpandableListPosition expandableListPosition = unflattenedPos.position;
        if (this.mExpandableListAdapter instanceof android.widget.HeterogeneousExpandableList) {
            android.widget.HeterogeneousExpandableList heterogeneousExpandableList = (android.widget.HeterogeneousExpandableList) this.mExpandableListAdapter;
            if (expandableListPosition.type == 2) {
                i2 = heterogeneousExpandableList.getGroupType(expandableListPosition.groupPos);
            } else {
                i2 = heterogeneousExpandableList.getChildType(expandableListPosition.groupPos, expandableListPosition.childPos) + heterogeneousExpandableList.getGroupTypeCount();
            }
        } else if (expandableListPosition.type == 2) {
            i2 = 0;
        } else {
            i2 = 1;
        }
        unflattenedPos.recycle();
        return i2;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        if (this.mExpandableListAdapter instanceof android.widget.HeterogeneousExpandableList) {
            android.widget.HeterogeneousExpandableList heterogeneousExpandableList = (android.widget.HeterogeneousExpandableList) this.mExpandableListAdapter;
            return heterogeneousExpandableList.getGroupTypeCount() + heterogeneousExpandableList.getChildTypeCount();
        }
        return 2;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return this.mExpandableListAdapter.hasStableIds();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshExpGroupMetadataList(boolean z, boolean z2) {
        int childrenCount;
        java.util.ArrayList<android.widget.ExpandableListConnector.GroupMetadata> arrayList = this.mExpGroupMetadataList;
        int size = arrayList.size();
        this.mTotalExpChildrenCount = 0;
        if (z2) {
            boolean z3 = false;
            for (int i = size - 1; i >= 0; i--) {
                android.widget.ExpandableListConnector.GroupMetadata groupMetadata = arrayList.get(i);
                int findGroupPosition = findGroupPosition(groupMetadata.gId, groupMetadata.gPos);
                if (findGroupPosition != groupMetadata.gPos) {
                    if (findGroupPosition == -1) {
                        arrayList.remove(i);
                        size--;
                    }
                    groupMetadata.gPos = findGroupPosition;
                    if (!z3) {
                        z3 = true;
                    }
                }
            }
            if (z3) {
                java.util.Collections.sort(arrayList);
            }
        }
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            android.widget.ExpandableListConnector.GroupMetadata groupMetadata2 = arrayList.get(i4);
            if (groupMetadata2.lastChildFlPos == -1 || z) {
                childrenCount = this.mExpandableListAdapter.getChildrenCount(groupMetadata2.gPos);
            } else {
                childrenCount = groupMetadata2.lastChildFlPos - groupMetadata2.flPos;
            }
            this.mTotalExpChildrenCount += childrenCount;
            int i5 = i2 + (groupMetadata2.gPos - i3);
            i3 = groupMetadata2.gPos;
            groupMetadata2.flPos = i5;
            i2 = i5 + childrenCount;
            groupMetadata2.lastChildFlPos = i2;
        }
    }

    boolean collapseGroup(int i) {
        android.widget.ExpandableListPosition obtain = android.widget.ExpandableListPosition.obtain(2, i, -1, -1);
        android.widget.ExpandableListConnector.PositionMetadata flattenedPos = getFlattenedPos(obtain);
        obtain.recycle();
        if (flattenedPos == null) {
            return false;
        }
        boolean collapseGroup = collapseGroup(flattenedPos);
        flattenedPos.recycle();
        return collapseGroup;
    }

    boolean collapseGroup(android.widget.ExpandableListConnector.PositionMetadata positionMetadata) {
        if (positionMetadata.groupMetadata == null) {
            return false;
        }
        this.mExpGroupMetadataList.remove(positionMetadata.groupMetadata);
        refreshExpGroupMetadataList(false, false);
        notifyDataSetChanged();
        this.mExpandableListAdapter.onGroupCollapsed(positionMetadata.groupMetadata.gPos);
        return true;
    }

    boolean expandGroup(int i) {
        android.widget.ExpandableListPosition obtain = android.widget.ExpandableListPosition.obtain(2, i, -1, -1);
        android.widget.ExpandableListConnector.PositionMetadata flattenedPos = getFlattenedPos(obtain);
        obtain.recycle();
        boolean expandGroup = expandGroup(flattenedPos);
        flattenedPos.recycle();
        return expandGroup;
    }

    boolean expandGroup(android.widget.ExpandableListConnector.PositionMetadata positionMetadata) {
        if (positionMetadata.position.groupPos < 0) {
            throw new java.lang.RuntimeException("Need group");
        }
        if (this.mMaxExpGroupCount == 0 || positionMetadata.groupMetadata != null) {
            return false;
        }
        if (this.mExpGroupMetadataList.size() >= this.mMaxExpGroupCount) {
            android.widget.ExpandableListConnector.GroupMetadata groupMetadata = this.mExpGroupMetadataList.get(0);
            int indexOf = this.mExpGroupMetadataList.indexOf(groupMetadata);
            collapseGroup(groupMetadata.gPos);
            if (positionMetadata.groupInsertIndex > indexOf) {
                positionMetadata.groupInsertIndex--;
            }
        }
        android.widget.ExpandableListConnector.GroupMetadata obtain = android.widget.ExpandableListConnector.GroupMetadata.obtain(-1, -1, positionMetadata.position.groupPos, this.mExpandableListAdapter.getGroupId(positionMetadata.position.groupPos));
        this.mExpGroupMetadataList.add(positionMetadata.groupInsertIndex, obtain);
        refreshExpGroupMetadataList(false, false);
        notifyDataSetChanged();
        this.mExpandableListAdapter.onGroupExpanded(obtain.gPos);
        return true;
    }

    public boolean isGroupExpanded(int i) {
        for (int size = this.mExpGroupMetadataList.size() - 1; size >= 0; size--) {
            if (this.mExpGroupMetadataList.get(size).gPos == i) {
                return true;
            }
        }
        return false;
    }

    public void setMaxExpGroupCount(int i) {
        this.mMaxExpGroupCount = i;
    }

    android.widget.ExpandableListAdapter getAdapter() {
        return this.mExpandableListAdapter;
    }

    @Override // android.widget.Filterable
    public android.widget.Filter getFilter() {
        android.widget.ExpandableListAdapter adapter = getAdapter();
        if (adapter instanceof android.widget.Filterable) {
            return ((android.widget.Filterable) adapter).getFilter();
        }
        return null;
    }

    java.util.ArrayList<android.widget.ExpandableListConnector.GroupMetadata> getExpandedGroupMetadataList() {
        return this.mExpGroupMetadataList;
    }

    void setExpandedGroupMetadataList(java.util.ArrayList<android.widget.ExpandableListConnector.GroupMetadata> arrayList) {
        if (arrayList == null || this.mExpandableListAdapter == null) {
            return;
        }
        int groupCount = this.mExpandableListAdapter.getGroupCount();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size).gPos >= groupCount) {
                return;
            }
        }
        this.mExpGroupMetadataList = arrayList;
        refreshExpGroupMetadataList(true, false);
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean isEmpty() {
        android.widget.ExpandableListAdapter adapter = getAdapter();
        if (adapter != null) {
            return adapter.isEmpty();
        }
        return true;
    }

    int findGroupPosition(long j, int i) {
        boolean z;
        boolean z2;
        int groupCount = this.mExpandableListAdapter.getGroupCount();
        if (groupCount == 0 || j == Long.MIN_VALUE) {
            return -1;
        }
        int i2 = groupCount - 1;
        int min = java.lang.Math.min(i2, java.lang.Math.max(0, i));
        long uptimeMillis = android.os.SystemClock.uptimeMillis() + 100;
        android.widget.ExpandableListAdapter adapter = getAdapter();
        if (adapter == null) {
            return -1;
        }
        int i3 = min;
        int i4 = i3;
        boolean z3 = false;
        while (android.os.SystemClock.uptimeMillis() <= uptimeMillis) {
            if (adapter.getGroupId(min) == j) {
                return min;
            }
            if (i3 != i2) {
                z = false;
            } else {
                z = true;
            }
            if (i4 != 0) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z && z2) {
                break;
            }
            if (z2 || (z3 && !z)) {
                i3++;
                z3 = false;
                min = i3;
            } else if (z || (!z3 && !z2)) {
                i4--;
                z3 = true;
                min = i4;
            }
        }
        return -1;
    }

    protected class MyDataSetObserver extends android.database.DataSetObserver {
        protected MyDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            android.widget.ExpandableListConnector.this.refreshExpGroupMetadataList(true, true);
            android.widget.ExpandableListConnector.this.notifyDataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            android.widget.ExpandableListConnector.this.refreshExpGroupMetadataList(true, true);
            android.widget.ExpandableListConnector.this.notifyDataSetInvalidated();
        }
    }

    static class GroupMetadata implements android.os.Parcelable, java.lang.Comparable<android.widget.ExpandableListConnector.GroupMetadata> {
        public static final android.os.Parcelable.Creator<android.widget.ExpandableListConnector.GroupMetadata> CREATOR = new android.os.Parcelable.Creator<android.widget.ExpandableListConnector.GroupMetadata>() { // from class: android.widget.ExpandableListConnector.GroupMetadata.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.ExpandableListConnector.GroupMetadata createFromParcel(android.os.Parcel parcel) {
                return android.widget.ExpandableListConnector.GroupMetadata.obtain(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readLong());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.ExpandableListConnector.GroupMetadata[] newArray(int i) {
                return new android.widget.ExpandableListConnector.GroupMetadata[i];
            }
        };
        static final int REFRESH = -1;
        int flPos;
        long gId;
        int gPos;
        int lastChildFlPos;

        private GroupMetadata() {
        }

        static android.widget.ExpandableListConnector.GroupMetadata obtain(int i, int i2, int i3, long j) {
            android.widget.ExpandableListConnector.GroupMetadata groupMetadata = new android.widget.ExpandableListConnector.GroupMetadata();
            groupMetadata.flPos = i;
            groupMetadata.lastChildFlPos = i2;
            groupMetadata.gPos = i3;
            groupMetadata.gId = j;
            return groupMetadata;
        }

        @Override // java.lang.Comparable
        public int compareTo(android.widget.ExpandableListConnector.GroupMetadata groupMetadata) {
            if (groupMetadata == null) {
                throw new java.lang.IllegalArgumentException();
            }
            return this.gPos - groupMetadata.gPos;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.flPos);
            parcel.writeInt(this.lastChildFlPos);
            parcel.writeInt(this.gPos);
            parcel.writeLong(this.gId);
        }
    }

    public static class PositionMetadata {
        private static final int MAX_POOL_SIZE = 5;
        private static java.util.ArrayList<android.widget.ExpandableListConnector.PositionMetadata> sPool = new java.util.ArrayList<>(5);
        public int groupInsertIndex;
        public android.widget.ExpandableListConnector.GroupMetadata groupMetadata;
        public android.widget.ExpandableListPosition position;

        private void resetState() {
            if (this.position != null) {
                this.position.recycle();
                this.position = null;
            }
            this.groupMetadata = null;
            this.groupInsertIndex = 0;
        }

        private PositionMetadata() {
        }

        static android.widget.ExpandableListConnector.PositionMetadata obtain(int i, int i2, int i3, int i4, android.widget.ExpandableListConnector.GroupMetadata groupMetadata, int i5) {
            android.widget.ExpandableListConnector.PositionMetadata recycledOrCreate = getRecycledOrCreate();
            recycledOrCreate.position = android.widget.ExpandableListPosition.obtain(i2, i3, i4, i);
            recycledOrCreate.groupMetadata = groupMetadata;
            recycledOrCreate.groupInsertIndex = i5;
            return recycledOrCreate;
        }

        private static android.widget.ExpandableListConnector.PositionMetadata getRecycledOrCreate() {
            synchronized (sPool) {
                if (sPool.size() > 0) {
                    android.widget.ExpandableListConnector.PositionMetadata remove = sPool.remove(0);
                    remove.resetState();
                    return remove;
                }
                return new android.widget.ExpandableListConnector.PositionMetadata();
            }
        }

        public void recycle() {
            resetState();
            synchronized (sPool) {
                if (sPool.size() < 5) {
                    sPool.add(this);
                }
            }
        }

        public boolean isExpanded() {
            return this.groupMetadata != null;
        }
    }
}
