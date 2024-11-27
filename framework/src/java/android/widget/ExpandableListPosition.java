package android.widget;

/* loaded from: classes4.dex */
class ExpandableListPosition {
    public static final int CHILD = 1;
    public static final int GROUP = 2;
    private static final int MAX_POOL_SIZE = 5;
    private static java.util.ArrayList<android.widget.ExpandableListPosition> sPool = new java.util.ArrayList<>(5);
    public int childPos;
    int flatListPos;
    public int groupPos;
    public int type;

    private void resetState() {
        this.groupPos = 0;
        this.childPos = 0;
        this.flatListPos = 0;
        this.type = 0;
    }

    private ExpandableListPosition() {
    }

    long getPackedPosition() {
        return this.type == 1 ? android.widget.ExpandableListView.getPackedPositionForChild(this.groupPos, this.childPos) : android.widget.ExpandableListView.getPackedPositionForGroup(this.groupPos);
    }

    static android.widget.ExpandableListPosition obtainGroupPosition(int i) {
        return obtain(2, i, 0, 0);
    }

    static android.widget.ExpandableListPosition obtainChildPosition(int i, int i2) {
        return obtain(1, i, i2, 0);
    }

    static android.widget.ExpandableListPosition obtainPosition(long j) {
        if (j == 4294967295L) {
            return null;
        }
        android.widget.ExpandableListPosition recycledOrCreate = getRecycledOrCreate();
        recycledOrCreate.groupPos = android.widget.ExpandableListView.getPackedPositionGroup(j);
        if (android.widget.ExpandableListView.getPackedPositionType(j) == 1) {
            recycledOrCreate.type = 1;
            recycledOrCreate.childPos = android.widget.ExpandableListView.getPackedPositionChild(j);
        } else {
            recycledOrCreate.type = 2;
        }
        return recycledOrCreate;
    }

    static android.widget.ExpandableListPosition obtain(int i, int i2, int i3, int i4) {
        android.widget.ExpandableListPosition recycledOrCreate = getRecycledOrCreate();
        recycledOrCreate.type = i;
        recycledOrCreate.groupPos = i2;
        recycledOrCreate.childPos = i3;
        recycledOrCreate.flatListPos = i4;
        return recycledOrCreate;
    }

    private static android.widget.ExpandableListPosition getRecycledOrCreate() {
        synchronized (sPool) {
            if (sPool.size() > 0) {
                android.widget.ExpandableListPosition remove = sPool.remove(0);
                remove.resetState();
                return remove;
            }
            return new android.widget.ExpandableListPosition();
        }
    }

    public void recycle() {
        synchronized (sPool) {
            if (sPool.size() < 5) {
                sPool.add(this);
            }
        }
    }
}
