package android.widget;

/* loaded from: classes4.dex */
public class ExpandableListView extends android.widget.ListView {
    public static final int CHILD_INDICATOR_INHERIT = -1;
    private static final int INDICATOR_UNDEFINED = -2;
    private static final long PACKED_POSITION_INT_MASK_CHILD = -1;
    private static final long PACKED_POSITION_INT_MASK_GROUP = 2147483647L;
    private static final long PACKED_POSITION_MASK_CHILD = 4294967295L;
    private static final long PACKED_POSITION_MASK_GROUP = 9223372032559808512L;
    private static final long PACKED_POSITION_MASK_TYPE = Long.MIN_VALUE;
    private static final long PACKED_POSITION_SHIFT_GROUP = 32;
    private static final long PACKED_POSITION_SHIFT_TYPE = 63;
    public static final int PACKED_POSITION_TYPE_CHILD = 1;
    public static final int PACKED_POSITION_TYPE_GROUP = 0;
    public static final int PACKED_POSITION_TYPE_NULL = 2;
    public static final long PACKED_POSITION_VALUE_NULL = 4294967295L;
    private android.widget.ExpandableListAdapter mAdapter;
    private android.graphics.drawable.Drawable mChildDivider;
    private android.graphics.drawable.Drawable mChildIndicator;
    private int mChildIndicatorEnd;
    private int mChildIndicatorLeft;
    private int mChildIndicatorRight;
    private int mChildIndicatorStart;
    private android.widget.ExpandableListConnector mConnector;
    private android.graphics.drawable.Drawable mGroupIndicator;
    private int mIndicatorEnd;
    private int mIndicatorLeft;
    private final android.graphics.Rect mIndicatorRect;
    private int mIndicatorRight;
    private int mIndicatorStart;
    private android.widget.ExpandableListView.OnChildClickListener mOnChildClickListener;
    private android.widget.ExpandableListView.OnGroupClickListener mOnGroupClickListener;
    private android.widget.ExpandableListView.OnGroupCollapseListener mOnGroupCollapseListener;
    private android.widget.ExpandableListView.OnGroupExpandListener mOnGroupExpandListener;
    private static final int[] EMPTY_STATE_SET = new int[0];
    private static final int[] GROUP_EXPANDED_STATE_SET = {16842920};
    private static final int[] GROUP_EMPTY_STATE_SET = {16842921};
    private static final int[] GROUP_EXPANDED_EMPTY_STATE_SET = {16842920, 16842921};
    private static final int[][] GROUP_STATE_SETS = {EMPTY_STATE_SET, GROUP_EXPANDED_STATE_SET, GROUP_EMPTY_STATE_SET, GROUP_EXPANDED_EMPTY_STATE_SET};
    private static final int[] CHILD_LAST_STATE_SET = {16842918};

    public interface OnChildClickListener {
        boolean onChildClick(android.widget.ExpandableListView expandableListView, android.view.View view, int i, int i2, long j);
    }

    public interface OnGroupClickListener {
        boolean onGroupClick(android.widget.ExpandableListView expandableListView, android.view.View view, int i, long j);
    }

    public interface OnGroupCollapseListener {
        void onGroupCollapse(int i);
    }

    public interface OnGroupExpandListener {
        void onGroupExpand(int i);
    }

    public ExpandableListView(android.content.Context context) {
        this(context, null);
    }

    public ExpandableListView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842863);
    }

    public ExpandableListView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ExpandableListView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIndicatorRect = new android.graphics.Rect();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ExpandableListView, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.ExpandableListView, attributeSet, obtainStyledAttributes, i, i2);
        this.mGroupIndicator = obtainStyledAttributes.getDrawable(0);
        this.mChildIndicator = obtainStyledAttributes.getDrawable(1);
        this.mIndicatorLeft = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        this.mIndicatorRight = obtainStyledAttributes.getDimensionPixelSize(3, 0);
        if (this.mIndicatorRight == 0 && this.mGroupIndicator != null) {
            this.mIndicatorRight = this.mIndicatorLeft + this.mGroupIndicator.getIntrinsicWidth();
        }
        this.mChildIndicatorLeft = obtainStyledAttributes.getDimensionPixelSize(4, -1);
        this.mChildIndicatorRight = obtainStyledAttributes.getDimensionPixelSize(5, -1);
        this.mChildDivider = obtainStyledAttributes.getDrawable(6);
        if (!isRtlCompatibilityMode()) {
            this.mIndicatorStart = obtainStyledAttributes.getDimensionPixelSize(7, -2);
            this.mIndicatorEnd = obtainStyledAttributes.getDimensionPixelSize(8, -2);
            this.mChildIndicatorStart = obtainStyledAttributes.getDimensionPixelSize(9, -1);
            this.mChildIndicatorEnd = obtainStyledAttributes.getDimensionPixelSize(10, -1);
        }
        obtainStyledAttributes.recycle();
    }

    private boolean isRtlCompatibilityMode() {
        return this.mContext.getApplicationInfo().targetSdkVersion < 17 || !hasRtlSupport();
    }

    private boolean hasRtlSupport() {
        return this.mContext.getApplicationInfo().hasRtlSupport();
    }

    @Override // android.widget.AbsListView, android.view.View
    public void onRtlPropertiesChanged(int i) {
        resolveIndicator();
        resolveChildIndicator();
    }

    private void resolveIndicator() {
        if (isLayoutRtl()) {
            if (this.mIndicatorStart >= 0) {
                this.mIndicatorRight = this.mIndicatorStart;
            }
            if (this.mIndicatorEnd >= 0) {
                this.mIndicatorLeft = this.mIndicatorEnd;
            }
        } else {
            if (this.mIndicatorStart >= 0) {
                this.mIndicatorLeft = this.mIndicatorStart;
            }
            if (this.mIndicatorEnd >= 0) {
                this.mIndicatorRight = this.mIndicatorEnd;
            }
        }
        if (this.mIndicatorRight == 0 && this.mGroupIndicator != null) {
            this.mIndicatorRight = this.mIndicatorLeft + this.mGroupIndicator.getIntrinsicWidth();
        }
    }

    private void resolveChildIndicator() {
        if (isLayoutRtl()) {
            if (this.mChildIndicatorStart >= -1) {
                this.mChildIndicatorRight = this.mChildIndicatorStart;
            }
            if (this.mChildIndicatorEnd >= -1) {
                this.mChildIndicatorLeft = this.mChildIndicatorEnd;
                return;
            }
            return;
        }
        if (this.mChildIndicatorStart >= -1) {
            this.mChildIndicatorLeft = this.mChildIndicatorStart;
        }
        if (this.mChildIndicatorEnd >= -1) {
            this.mChildIndicatorRight = this.mChildIndicatorEnd;
        }
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void dispatchDraw(android.graphics.Canvas canvas) {
        int i;
        int i2;
        int i3;
        super.dispatchDraw(canvas);
        if (this.mChildIndicator == null && this.mGroupIndicator == null) {
            return;
        }
        int i4 = 0;
        boolean z = (this.mGroupFlags & 34) == 34;
        if (!z) {
            i = 0;
        } else {
            i = canvas.save();
            int i5 = this.mScrollX;
            int i6 = this.mScrollY;
            canvas.clipRect(this.mPaddingLeft + i5, this.mPaddingTop + i6, ((i5 + this.mRight) - this.mLeft) - this.mPaddingRight, ((i6 + this.mBottom) - this.mTop) - this.mPaddingBottom);
        }
        int headerViewsCount = getHeaderViewsCount();
        int footerViewsCount = ((this.mItemCount - getFooterViewsCount()) - headerViewsCount) - 1;
        int i7 = this.mBottom;
        android.graphics.Rect rect = this.mIndicatorRect;
        int childCount = getChildCount();
        int i8 = this.mFirstPosition - headerViewsCount;
        int i9 = -4;
        while (i4 < childCount) {
            if (i8 >= 0) {
                if (i8 > footerViewsCount) {
                    break;
                }
                android.view.View childAt = getChildAt(i4);
                int top = childAt.getTop();
                int bottom = childAt.getBottom();
                if (bottom >= 0 && top <= i7) {
                    android.widget.ExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(i8);
                    boolean isLayoutRtl = isLayoutRtl();
                    int width = getWidth();
                    if (unflattenedPos.position.type != i9) {
                        if (unflattenedPos.position.type == 1) {
                            if (this.mChildIndicatorLeft != -1) {
                                i2 = this.mChildIndicatorLeft;
                            } else {
                                i2 = this.mIndicatorLeft;
                            }
                            rect.left = i2;
                            if (this.mChildIndicatorRight != -1) {
                                i3 = this.mChildIndicatorRight;
                            } else {
                                i3 = this.mIndicatorRight;
                            }
                            rect.right = i3;
                        } else {
                            rect.left = this.mIndicatorLeft;
                            rect.right = this.mIndicatorRight;
                        }
                        if (!isLayoutRtl) {
                            rect.left += this.mPaddingLeft;
                            rect.right += this.mPaddingLeft;
                        } else {
                            int i10 = rect.left;
                            rect.left = width - rect.right;
                            rect.right = width - i10;
                            rect.left -= this.mPaddingRight;
                            rect.right -= this.mPaddingRight;
                        }
                        i9 = unflattenedPos.position.type;
                    }
                    if (rect.left != rect.right) {
                        if (this.mStackFromBottom) {
                            rect.top = top;
                            rect.bottom = bottom;
                        } else {
                            rect.top = top;
                            rect.bottom = bottom;
                        }
                        android.graphics.drawable.Drawable indicator = getIndicator(unflattenedPos);
                        if (indicator != null) {
                            indicator.setBounds(rect);
                            indicator.draw(canvas);
                        }
                    }
                    unflattenedPos.recycle();
                }
            }
            i4++;
            i8++;
        }
        if (z) {
            canvas.restoreToCount(i);
        }
    }

    private android.graphics.drawable.Drawable getIndicator(android.widget.ExpandableListConnector.PositionMetadata positionMetadata) {
        android.graphics.drawable.Drawable drawable;
        int[] iArr;
        if (positionMetadata.position.type == 2) {
            drawable = this.mGroupIndicator;
            if (drawable != null && drawable.isStateful()) {
                boolean z = positionMetadata.groupMetadata == null || positionMetadata.groupMetadata.lastChildFlPos == positionMetadata.groupMetadata.flPos;
                drawable.setState(GROUP_STATE_SETS[(positionMetadata.isExpanded() ? 1 : 0) | (z ? 2 : 0)]);
            }
        } else {
            drawable = this.mChildIndicator;
            if (drawable != null && drawable.isStateful()) {
                if (positionMetadata.position.flatListPos == positionMetadata.groupMetadata.lastChildFlPos) {
                    iArr = CHILD_LAST_STATE_SET;
                } else {
                    iArr = EMPTY_STATE_SET;
                }
                drawable.setState(iArr);
            }
        }
        return drawable;
    }

    public void setChildDivider(android.graphics.drawable.Drawable drawable) {
        this.mChildDivider = drawable;
    }

    @Override // android.widget.ListView
    void drawDivider(android.graphics.Canvas canvas, android.graphics.Rect rect, int i) {
        int i2 = i + this.mFirstPosition;
        if (i2 >= 0) {
            android.widget.ExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(getFlatPositionForConnector(i2));
            if (unflattenedPos.position.type == 1 || (unflattenedPos.isExpanded() && unflattenedPos.groupMetadata.lastChildFlPos != unflattenedPos.groupMetadata.flPos)) {
                android.graphics.drawable.Drawable drawable = this.mChildDivider;
                drawable.setBounds(rect);
                drawable.draw(canvas);
                unflattenedPos.recycle();
                return;
            }
            unflattenedPos.recycle();
        }
        super.drawDivider(canvas, rect, i2);
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView
    public void setAdapter(android.widget.ListAdapter listAdapter) {
        throw new java.lang.RuntimeException("For ExpandableListView, use setAdapter(ExpandableListAdapter) instead of setAdapter(ListAdapter)");
    }

    @Override // android.widget.ListView, android.widget.AdapterView
    public android.widget.ListAdapter getAdapter() {
        return super.getAdapter();
    }

    @Override // android.widget.AdapterView
    public void setOnItemClickListener(android.widget.AdapterView.OnItemClickListener onItemClickListener) {
        super.setOnItemClickListener(onItemClickListener);
    }

    public void setAdapter(android.widget.ExpandableListAdapter expandableListAdapter) {
        this.mAdapter = expandableListAdapter;
        if (expandableListAdapter != null) {
            this.mConnector = new android.widget.ExpandableListConnector(expandableListAdapter);
        } else {
            this.mConnector = null;
        }
        super.setAdapter((android.widget.ListAdapter) this.mConnector);
    }

    public android.widget.ExpandableListAdapter getExpandableListAdapter() {
        return this.mAdapter;
    }

    private boolean isHeaderOrFooterPosition(int i) {
        return i < getHeaderViewsCount() || i >= this.mItemCount - getFooterViewsCount();
    }

    private int getFlatPositionForConnector(int i) {
        return i - getHeaderViewsCount();
    }

    private int getAbsoluteFlatPosition(int i) {
        return i + getHeaderViewsCount();
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView
    public boolean performItemClick(android.view.View view, int i, long j) {
        if (isHeaderOrFooterPosition(i)) {
            return super.performItemClick(view, i, j);
        }
        boolean handleItemClick = handleItemClick(view, getFlatPositionForConnector(i), j);
        if (view != null) {
            view.sendAccessibilityEvent(1);
        }
        return handleItemClick;
    }

    boolean handleItemClick(android.view.View view, int i, long j) {
        android.widget.ExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(i);
        long childOrGroupId = getChildOrGroupId(unflattenedPos.position);
        boolean z = false;
        if (unflattenedPos.position.type == 2) {
            if (this.mOnGroupClickListener != null && this.mOnGroupClickListener.onGroupClick(this, view, unflattenedPos.position.groupPos, childOrGroupId)) {
                unflattenedPos.recycle();
                return true;
            }
            if (unflattenedPos.isExpanded()) {
                this.mConnector.collapseGroup(unflattenedPos);
                playSoundEffect(0);
                if (this.mOnGroupCollapseListener != null) {
                    this.mOnGroupCollapseListener.onGroupCollapse(unflattenedPos.position.groupPos);
                }
            } else {
                this.mConnector.expandGroup(unflattenedPos);
                playSoundEffect(0);
                if (this.mOnGroupExpandListener != null) {
                    this.mOnGroupExpandListener.onGroupExpand(unflattenedPos.position.groupPos);
                }
                int i2 = unflattenedPos.position.groupPos;
                int headerViewsCount = unflattenedPos.position.flatListPos + getHeaderViewsCount();
                smoothScrollToPosition(this.mAdapter.getChildrenCount(i2) + headerViewsCount, headerViewsCount);
            }
            z = true;
        } else if (this.mOnChildClickListener != null) {
            playSoundEffect(0);
            return this.mOnChildClickListener.onChildClick(this, view, unflattenedPos.position.groupPos, unflattenedPos.position.childPos, childOrGroupId);
        }
        unflattenedPos.recycle();
        return z;
    }

    public boolean expandGroup(int i) {
        return expandGroup(i, false);
    }

    public boolean expandGroup(int i, boolean z) {
        android.widget.ExpandableListPosition obtain = android.widget.ExpandableListPosition.obtain(2, i, -1, -1);
        android.widget.ExpandableListConnector.PositionMetadata flattenedPos = this.mConnector.getFlattenedPos(obtain);
        obtain.recycle();
        boolean expandGroup = this.mConnector.expandGroup(flattenedPos);
        if (this.mOnGroupExpandListener != null) {
            this.mOnGroupExpandListener.onGroupExpand(i);
        }
        if (z) {
            int headerViewsCount = flattenedPos.position.flatListPos + getHeaderViewsCount();
            smoothScrollToPosition(this.mAdapter.getChildrenCount(i) + headerViewsCount, headerViewsCount);
        }
        flattenedPos.recycle();
        return expandGroup;
    }

    public boolean collapseGroup(int i) {
        boolean collapseGroup = this.mConnector.collapseGroup(i);
        if (this.mOnGroupCollapseListener != null) {
            this.mOnGroupCollapseListener.onGroupCollapse(i);
        }
        return collapseGroup;
    }

    public void setOnGroupCollapseListener(android.widget.ExpandableListView.OnGroupCollapseListener onGroupCollapseListener) {
        this.mOnGroupCollapseListener = onGroupCollapseListener;
    }

    public void setOnGroupExpandListener(android.widget.ExpandableListView.OnGroupExpandListener onGroupExpandListener) {
        this.mOnGroupExpandListener = onGroupExpandListener;
    }

    public void setOnGroupClickListener(android.widget.ExpandableListView.OnGroupClickListener onGroupClickListener) {
        this.mOnGroupClickListener = onGroupClickListener;
    }

    public void setOnChildClickListener(android.widget.ExpandableListView.OnChildClickListener onChildClickListener) {
        this.mOnChildClickListener = onChildClickListener;
    }

    public long getExpandableListPosition(int i) {
        if (isHeaderOrFooterPosition(i)) {
            return 4294967295L;
        }
        android.widget.ExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(getFlatPositionForConnector(i));
        long packedPosition = unflattenedPos.position.getPackedPosition();
        unflattenedPos.recycle();
        return packedPosition;
    }

    public int getFlatListPosition(long j) {
        android.widget.ExpandableListPosition obtainPosition = android.widget.ExpandableListPosition.obtainPosition(j);
        android.widget.ExpandableListConnector.PositionMetadata flattenedPos = this.mConnector.getFlattenedPos(obtainPosition);
        obtainPosition.recycle();
        int i = flattenedPos.position.flatListPos;
        flattenedPos.recycle();
        return getAbsoluteFlatPosition(i);
    }

    public long getSelectedPosition() {
        return getExpandableListPosition(getSelectedItemPosition());
    }

    public long getSelectedId() {
        long selectedPosition = getSelectedPosition();
        if (selectedPosition == 4294967295L) {
            return -1L;
        }
        int packedPositionGroup = getPackedPositionGroup(selectedPosition);
        if (getPackedPositionType(selectedPosition) == 0) {
            return this.mAdapter.getGroupId(packedPositionGroup);
        }
        return this.mAdapter.getChildId(packedPositionGroup, getPackedPositionChild(selectedPosition));
    }

    public void setSelectedGroup(int i) {
        android.widget.ExpandableListPosition obtainGroupPosition = android.widget.ExpandableListPosition.obtainGroupPosition(i);
        android.widget.ExpandableListConnector.PositionMetadata flattenedPos = this.mConnector.getFlattenedPos(obtainGroupPosition);
        obtainGroupPosition.recycle();
        super.setSelection(getAbsoluteFlatPosition(flattenedPos.position.flatListPos));
        flattenedPos.recycle();
    }

    public boolean setSelectedChild(int i, int i2, boolean z) {
        android.widget.ExpandableListPosition obtainChildPosition = android.widget.ExpandableListPosition.obtainChildPosition(i, i2);
        android.widget.ExpandableListConnector.PositionMetadata flattenedPos = this.mConnector.getFlattenedPos(obtainChildPosition);
        if (flattenedPos == null) {
            if (!z) {
                return false;
            }
            expandGroup(i);
            flattenedPos = this.mConnector.getFlattenedPos(obtainChildPosition);
            if (flattenedPos == null) {
                throw new java.lang.IllegalStateException("Could not find child");
            }
        }
        super.setSelection(getAbsoluteFlatPosition(flattenedPos.position.flatListPos));
        obtainChildPosition.recycle();
        flattenedPos.recycle();
        return true;
    }

    public boolean isGroupExpanded(int i) {
        return this.mConnector.isGroupExpanded(i);
    }

    public static int getPackedPositionType(long j) {
        if (j == 4294967295L) {
            return 2;
        }
        if ((j & Long.MIN_VALUE) == Long.MIN_VALUE) {
            return 1;
        }
        return 0;
    }

    public static int getPackedPositionGroup(long j) {
        if (j == 4294967295L) {
            return -1;
        }
        return (int) ((j & PACKED_POSITION_MASK_GROUP) >> 32);
    }

    public static int getPackedPositionChild(long j) {
        if (j != 4294967295L && (j & Long.MIN_VALUE) == Long.MIN_VALUE) {
            return (int) (j & 4294967295L);
        }
        return -1;
    }

    public static long getPackedPositionForChild(int i, int i2) {
        return (i2 & (-1)) | ((i & PACKED_POSITION_INT_MASK_GROUP) << 32) | Long.MIN_VALUE;
    }

    public static long getPackedPositionForGroup(int i) {
        return (i & PACKED_POSITION_INT_MASK_GROUP) << 32;
    }

    @Override // android.widget.AbsListView
    android.view.ContextMenu.ContextMenuInfo createContextMenuInfo(android.view.View view, int i, long j) {
        if (isHeaderOrFooterPosition(i)) {
            return new android.widget.AdapterView.AdapterContextMenuInfo(view, i, j);
        }
        android.widget.ExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(getFlatPositionForConnector(i));
        android.widget.ExpandableListPosition expandableListPosition = unflattenedPos.position;
        long childOrGroupId = getChildOrGroupId(expandableListPosition);
        long packedPosition = expandableListPosition.getPackedPosition();
        unflattenedPos.recycle();
        return new android.widget.ExpandableListView.ExpandableListContextMenuInfo(view, packedPosition, childOrGroupId);
    }

    @Override // android.widget.ListView, android.widget.AbsListView
    public void onInitializeAccessibilityNodeInfoForItem(android.view.View view, int i, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoForItem(view, i, accessibilityNodeInfo);
        android.widget.ExpandableListConnector.PositionMetadata unflattenedPos = this.mConnector.getUnflattenedPos(i);
        if (unflattenedPos.position.type == 2 && view != null && view.isEnabled()) {
            accessibilityNodeInfo.setClickable(true);
            accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
            if (isGroupExpanded(unflattenedPos.position.groupPos)) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
            } else {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
            }
        }
        unflattenedPos.recycle();
    }

    private long getChildOrGroupId(android.widget.ExpandableListPosition expandableListPosition) {
        if (expandableListPosition.type == 1) {
            return this.mAdapter.getChildId(expandableListPosition.groupPos, expandableListPosition.childPos);
        }
        return this.mAdapter.getGroupId(expandableListPosition.groupPos);
    }

    public void setChildIndicator(android.graphics.drawable.Drawable drawable) {
        this.mChildIndicator = drawable;
    }

    public void setChildIndicatorBounds(int i, int i2) {
        this.mChildIndicatorLeft = i;
        this.mChildIndicatorRight = i2;
        resolveChildIndicator();
    }

    public void setChildIndicatorBoundsRelative(int i, int i2) {
        this.mChildIndicatorStart = i;
        this.mChildIndicatorEnd = i2;
        resolveChildIndicator();
    }

    public void setGroupIndicator(android.graphics.drawable.Drawable drawable) {
        this.mGroupIndicator = drawable;
        if (this.mIndicatorRight == 0 && this.mGroupIndicator != null) {
            this.mIndicatorRight = this.mIndicatorLeft + this.mGroupIndicator.getIntrinsicWidth();
        }
    }

    public void setIndicatorBounds(int i, int i2) {
        this.mIndicatorLeft = i;
        this.mIndicatorRight = i2;
        resolveIndicator();
    }

    public void setIndicatorBoundsRelative(int i, int i2) {
        this.mIndicatorStart = i;
        this.mIndicatorEnd = i2;
        resolveIndicator();
    }

    public static class ExpandableListContextMenuInfo implements android.view.ContextMenu.ContextMenuInfo {
        public long id;
        public long packedPosition;
        public android.view.View targetView;

        public ExpandableListContextMenuInfo(android.view.View view, long j, long j2) {
            this.targetView = view;
            this.packedPosition = j;
            this.id = j2;
        }
    }

    static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.widget.ExpandableListView.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.ExpandableListView.SavedState>() { // from class: android.widget.ExpandableListView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.ExpandableListView.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.widget.ExpandableListView.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.ExpandableListView.SavedState[] newArray(int i) {
                return new android.widget.ExpandableListView.SavedState[i];
            }
        };
        java.util.ArrayList<android.widget.ExpandableListConnector.GroupMetadata> expandedGroupMetadataList;

        SavedState(android.os.Parcelable parcelable, java.util.ArrayList<android.widget.ExpandableListConnector.GroupMetadata> arrayList) {
            super(parcelable);
            this.expandedGroupMetadataList = arrayList;
        }

        private SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.expandedGroupMetadataList = new java.util.ArrayList<>();
            parcel.readList(this.expandedGroupMetadataList, android.widget.ExpandableListConnector.class.getClassLoader(), android.widget.ExpandableListConnector.GroupMetadata.class);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeList(this.expandedGroupMetadataList);
        }
    }

    @Override // android.widget.AbsListView, android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        return new android.widget.ExpandableListView.SavedState(super.onSaveInstanceState(), this.mConnector != null ? this.mConnector.getExpandedGroupMetadataList() : null);
    }

    @Override // android.widget.AbsListView, android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (!(parcelable instanceof android.widget.ExpandableListView.SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        android.widget.ExpandableListView.SavedState savedState = (android.widget.ExpandableListView.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (this.mConnector != null && savedState.expandedGroupMetadataList != null) {
            this.mConnector.setExpandedGroupMetadataList(savedState.expandedGroupMetadataList);
        }
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.ExpandableListView.class.getName();
    }
}
