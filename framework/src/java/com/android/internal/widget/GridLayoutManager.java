package com.android.internal.widget;

/* loaded from: classes5.dex */
public class GridLayoutManager extends com.android.internal.widget.LinearLayoutManager {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_SPAN_COUNT = -1;
    private static final java.lang.String TAG = "GridLayoutManager";
    int[] mCachedBorders;
    final android.graphics.Rect mDecorInsets;
    boolean mPendingSpanCountChange;
    final android.util.SparseIntArray mPreLayoutSpanIndexCache;
    final android.util.SparseIntArray mPreLayoutSpanSizeCache;
    android.view.View[] mSet;
    int mSpanCount;
    com.android.internal.widget.GridLayoutManager.SpanSizeLookup mSpanSizeLookup;

    public GridLayoutManager(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new android.util.SparseIntArray();
        this.mPreLayoutSpanIndexCache = new android.util.SparseIntArray();
        this.mSpanSizeLookup = new com.android.internal.widget.GridLayoutManager.DefaultSpanSizeLookup();
        this.mDecorInsets = new android.graphics.Rect();
        setSpanCount(getProperties(context, attributeSet, i, i2).spanCount);
    }

    public GridLayoutManager(android.content.Context context, int i) {
        super(context);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new android.util.SparseIntArray();
        this.mPreLayoutSpanIndexCache = new android.util.SparseIntArray();
        this.mSpanSizeLookup = new com.android.internal.widget.GridLayoutManager.DefaultSpanSizeLookup();
        this.mDecorInsets = new android.graphics.Rect();
        setSpanCount(i);
    }

    public GridLayoutManager(android.content.Context context, int i, int i2, boolean z) {
        super(context, i2, z);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new android.util.SparseIntArray();
        this.mPreLayoutSpanIndexCache = new android.util.SparseIntArray();
        this.mSpanSizeLookup = new com.android.internal.widget.GridLayoutManager.DefaultSpanSizeLookup();
        this.mDecorInsets = new android.graphics.Rect();
        setSpanCount(i);
    }

    @Override // com.android.internal.widget.LinearLayoutManager
    public void setStackFromEnd(boolean z) {
        if (z) {
            throw new java.lang.UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.setStackFromEnd(false);
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public int getRowCountForAccessibility(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public int getColumnCountForAccessibility(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state) {
        if (this.mOrientation == 1) {
            return this.mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfoForItem(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof com.android.internal.widget.GridLayoutManager.LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfo);
            return;
        }
        com.android.internal.widget.GridLayoutManager.LayoutParams layoutParams2 = (com.android.internal.widget.GridLayoutManager.LayoutParams) layoutParams;
        int spanGroupIndex = getSpanGroupIndex(recycler, state, layoutParams2.getViewLayoutPosition());
        if (this.mOrientation == 0) {
            accessibilityNodeInfo.setCollectionItemInfo(android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain(layoutParams2.getSpanIndex(), layoutParams2.getSpanSize(), spanGroupIndex, 1, false, false));
        } else {
            accessibilityNodeInfo.setCollectionItemInfo(android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain(spanGroupIndex, 1, layoutParams2.getSpanIndex(), layoutParams2.getSpanSize(), false, false));
        }
    }

    @Override // com.android.internal.widget.LinearLayoutManager, com.android.internal.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state) {
        if (state.isPreLayout()) {
            cachePreLayoutSpanMapping();
        }
        super.onLayoutChildren(recycler, state);
        clearPreLayoutSpanMappingCache();
    }

    @Override // com.android.internal.widget.LinearLayoutManager, com.android.internal.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(com.android.internal.widget.RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingSpanCountChange = false;
    }

    private void clearPreLayoutSpanMappingCache() {
        this.mPreLayoutSpanSizeCache.clear();
        this.mPreLayoutSpanIndexCache.clear();
    }

    private void cachePreLayoutSpanMapping() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            com.android.internal.widget.GridLayoutManager.LayoutParams layoutParams = (com.android.internal.widget.GridLayoutManager.LayoutParams) getChildAt(i).getLayoutParams();
            int viewLayoutPosition = layoutParams.getViewLayoutPosition();
            this.mPreLayoutSpanSizeCache.put(viewLayoutPosition, layoutParams.getSpanSize());
            this.mPreLayoutSpanIndexCache.put(viewLayoutPosition, layoutParams.getSpanIndex());
        }
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public void onItemsAdded(com.android.internal.widget.RecyclerView recyclerView, int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public void onItemsChanged(com.android.internal.widget.RecyclerView recyclerView) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public void onItemsRemoved(com.android.internal.widget.RecyclerView recyclerView, int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public void onItemsUpdated(com.android.internal.widget.RecyclerView recyclerView, int i, int i2, java.lang.Object obj) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public void onItemsMoved(com.android.internal.widget.RecyclerView recyclerView, int i, int i2, int i3) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    @Override // com.android.internal.widget.LinearLayoutManager, com.android.internal.widget.RecyclerView.LayoutManager
    public com.android.internal.widget.RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new com.android.internal.widget.GridLayoutManager.LayoutParams(-2, -1);
        }
        return new com.android.internal.widget.GridLayoutManager.LayoutParams(-1, -2);
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public com.android.internal.widget.RecyclerView.LayoutParams generateLayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
        return new com.android.internal.widget.GridLayoutManager.LayoutParams(context, attributeSet);
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public com.android.internal.widget.RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
            return new com.android.internal.widget.GridLayoutManager.LayoutParams((android.view.ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new com.android.internal.widget.GridLayoutManager.LayoutParams(layoutParams);
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public boolean checkLayoutParams(com.android.internal.widget.RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof com.android.internal.widget.GridLayoutManager.LayoutParams;
    }

    public void setSpanSizeLookup(com.android.internal.widget.GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public com.android.internal.widget.GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return this.mSpanSizeLookup;
    }

    private void updateMeasurements() {
        int height;
        if (getOrientation() == 1) {
            height = (getWidth() - getPaddingRight()) - getPaddingLeft();
        } else {
            height = (getHeight() - getPaddingBottom()) - getPaddingTop();
        }
        calculateItemBorders(height);
    }

    @Override // com.android.internal.widget.RecyclerView.LayoutManager
    public void setMeasuredDimension(android.graphics.Rect rect, int i, int i2) {
        int chooseSize;
        int chooseSize2;
        if (this.mCachedBorders == null) {
            super.setMeasuredDimension(rect, i, i2);
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (this.mOrientation == 1) {
            chooseSize2 = chooseSize(i2, rect.height() + paddingTop, getMinimumHeight());
            chooseSize = chooseSize(i, this.mCachedBorders[this.mCachedBorders.length - 1] + paddingLeft, getMinimumWidth());
        } else {
            chooseSize = chooseSize(i, rect.width() + paddingLeft, getMinimumWidth());
            chooseSize2 = chooseSize(i2, this.mCachedBorders[this.mCachedBorders.length - 1] + paddingTop, getMinimumHeight());
        }
        setMeasuredDimension(chooseSize, chooseSize2);
    }

    private void calculateItemBorders(int i) {
        this.mCachedBorders = calculateItemBorders(this.mCachedBorders, this.mSpanCount, i);
    }

    static int[] calculateItemBorders(int[] iArr, int i, int i2) {
        int i3;
        if (iArr == null || iArr.length != i + 1 || iArr[iArr.length - 1] != i2) {
            iArr = new int[i + 1];
        }
        int i4 = 0;
        iArr[0] = 0;
        int i5 = i2 / i;
        int i6 = i2 % i;
        int i7 = 0;
        for (int i8 = 1; i8 <= i; i8++) {
            i4 += i6;
            if (i4 > 0 && i - i4 < i6) {
                i3 = i5 + 1;
                i4 -= i;
            } else {
                i3 = i5;
            }
            i7 += i3;
            iArr[i8] = i7;
        }
        return iArr;
    }

    int getSpaceForSpanRange(int i, int i2) {
        if (this.mOrientation == 1 && isLayoutRTL()) {
            return this.mCachedBorders[this.mSpanCount - i] - this.mCachedBorders[(this.mSpanCount - i) - i2];
        }
        return this.mCachedBorders[i2 + i] - this.mCachedBorders[i];
    }

    @Override // com.android.internal.widget.LinearLayoutManager
    void onAnchorReady(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, com.android.internal.widget.LinearLayoutManager.AnchorInfo anchorInfo, int i) {
        super.onAnchorReady(recycler, state, anchorInfo, i);
        updateMeasurements();
        if (state.getItemCount() > 0 && !state.isPreLayout()) {
            ensureAnchorIsInCorrectSpan(recycler, state, anchorInfo, i);
        }
        ensureViewSet();
    }

    private void ensureViewSet() {
        if (this.mSet == null || this.mSet.length != this.mSpanCount) {
            this.mSet = new android.view.View[this.mSpanCount];
        }
    }

    @Override // com.android.internal.widget.LinearLayoutManager, com.android.internal.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i, com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state) {
        updateMeasurements();
        ensureViewSet();
        return super.scrollHorizontallyBy(i, recycler, state);
    }

    @Override // com.android.internal.widget.LinearLayoutManager, com.android.internal.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i, com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state) {
        updateMeasurements();
        ensureViewSet();
        return super.scrollVerticallyBy(i, recycler, state);
    }

    private void ensureAnchorIsInCorrectSpan(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, com.android.internal.widget.LinearLayoutManager.AnchorInfo anchorInfo, int i) {
        boolean z = i == 1;
        int spanIndex = getSpanIndex(recycler, state, anchorInfo.mPosition);
        if (!z) {
            int itemCount = state.getItemCount() - 1;
            int i2 = anchorInfo.mPosition;
            while (i2 < itemCount) {
                int i3 = i2 + 1;
                int spanIndex2 = getSpanIndex(recycler, state, i3);
                if (spanIndex2 <= spanIndex) {
                    break;
                }
                i2 = i3;
                spanIndex = spanIndex2;
            }
            anchorInfo.mPosition = i2;
            return;
        }
        while (spanIndex > 0 && anchorInfo.mPosition > 0) {
            anchorInfo.mPosition--;
            spanIndex = getSpanIndex(recycler, state, anchorInfo.mPosition);
        }
    }

    @Override // com.android.internal.widget.LinearLayoutManager
    android.view.View findReferenceChild(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, int i, int i2, int i3) {
        ensureLayoutState();
        int startAfterPadding = this.mOrientationHelper.getStartAfterPadding();
        int endAfterPadding = this.mOrientationHelper.getEndAfterPadding();
        int i4 = i2 > i ? 1 : -1;
        android.view.View view = null;
        android.view.View view2 = null;
        while (i != i2) {
            android.view.View childAt = getChildAt(i);
            int position = getPosition(childAt);
            if (position >= 0 && position < i3 && getSpanIndex(recycler, state, position) == 0) {
                if (((com.android.internal.widget.RecyclerView.LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
                    if (view2 == null) {
                        view2 = childAt;
                    }
                } else if (this.mOrientationHelper.getDecoratedStart(childAt) >= endAfterPadding || this.mOrientationHelper.getDecoratedEnd(childAt) < startAfterPadding) {
                    if (view == null) {
                        view = childAt;
                    }
                } else {
                    return childAt;
                }
            }
            i += i4;
        }
        return view != null ? view : view2;
    }

    private int getSpanGroupIndex(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, int i) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanGroupIndex(i, this.mSpanCount);
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout == -1) {
            android.util.Log.w(TAG, "Cannot find span size for pre layout position. " + i);
            return 0;
        }
        return this.mSpanSizeLookup.getSpanGroupIndex(convertPreLayoutPositionToPostLayout, this.mSpanCount);
    }

    private int getSpanIndex(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, int i) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getCachedSpanIndex(i, this.mSpanCount);
        }
        int i2 = this.mPreLayoutSpanIndexCache.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout == -1) {
            android.util.Log.w(TAG, "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
            return 0;
        }
        return this.mSpanSizeLookup.getCachedSpanIndex(convertPreLayoutPositionToPostLayout, this.mSpanCount);
    }

    private int getSpanSize(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, int i) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanSize(i);
        }
        int i2 = this.mPreLayoutSpanSizeCache.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout == -1) {
            android.util.Log.w(TAG, "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
            return 1;
        }
        return this.mSpanSizeLookup.getSpanSize(convertPreLayoutPositionToPostLayout);
    }

    @Override // com.android.internal.widget.LinearLayoutManager
    void collectPrefetchPositionsForLayoutState(com.android.internal.widget.RecyclerView.State state, com.android.internal.widget.LinearLayoutManager.LayoutState layoutState, com.android.internal.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i = this.mSpanCount;
        for (int i2 = 0; i2 < this.mSpanCount && layoutState.hasMore(state) && i > 0; i2++) {
            int i3 = layoutState.mCurrentPosition;
            layoutPrefetchRegistry.addPosition(i3, java.lang.Math.max(0, layoutState.mScrollingOffset));
            i -= this.mSpanSizeLookup.getSpanSize(i3);
            layoutState.mCurrentPosition += layoutState.mItemDirection;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x00ad, code lost:
    
        r22.mFinished = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00af, code lost:
    
        return;
     */
    @Override // com.android.internal.widget.LinearLayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void layoutChunk(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, com.android.internal.widget.LinearLayoutManager.LayoutState layoutState, com.android.internal.widget.LinearLayoutManager.LayoutChunkResult layoutChunkResult) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int decoratedMeasurementInOther;
        int childMeasureSpec;
        int i10;
        boolean z;
        android.view.View next;
        int modeInOther = this.mOrientationHelper.getModeInOther();
        boolean z2 = modeInOther != 1073741824;
        int i11 = getChildCount() > 0 ? this.mCachedBorders[this.mSpanCount] : 0;
        if (z2) {
            updateMeasurements();
        }
        boolean z3 = layoutState.mItemDirection == 1;
        int i12 = this.mSpanCount;
        if (z3) {
            i = 0;
            i2 = 0;
        } else {
            i12 = getSpanIndex(recycler, state, layoutState.mCurrentPosition) + getSpanSize(recycler, state, layoutState.mCurrentPosition);
            i = 0;
            i2 = 0;
        }
        while (i2 < this.mSpanCount && layoutState.hasMore(state) && i12 > 0) {
            int i13 = layoutState.mCurrentPosition;
            int spanSize = getSpanSize(recycler, state, i13);
            if (spanSize > this.mSpanCount) {
                throw new java.lang.IllegalArgumentException("Item at position " + i13 + " requires " + spanSize + " spans but GridLayoutManager has only " + this.mSpanCount + " spans.");
            }
            i12 -= spanSize;
            if (i12 < 0 || (next = layoutState.next(recycler)) == null) {
                break;
            }
            i += spanSize;
            this.mSet[i2] = next;
            i2++;
        }
        int i14 = i2;
        assignSpans(recycler, state, i2, i, z3);
        float f = 0.0f;
        int i15 = 0;
        for (int i16 = 0; i16 < i14; i16++) {
            android.view.View view = this.mSet[i16];
            if (layoutState.mScrapList == null) {
                if (z3) {
                    addView(view);
                    z = false;
                } else {
                    z = false;
                    addView(view, 0);
                }
            } else {
                z = false;
                if (z3) {
                    addDisappearingView(view);
                } else {
                    addDisappearingView(view, 0);
                }
            }
            calculateItemDecorationsForChild(view, this.mDecorInsets);
            measureChild(view, modeInOther, z);
            int decoratedMeasurement = this.mOrientationHelper.getDecoratedMeasurement(view);
            if (decoratedMeasurement > i15) {
                i15 = decoratedMeasurement;
            }
            float decoratedMeasurementInOther2 = (this.mOrientationHelper.getDecoratedMeasurementInOther(view) * 1.0f) / ((com.android.internal.widget.GridLayoutManager.LayoutParams) view.getLayoutParams()).mSpanSize;
            if (decoratedMeasurementInOther2 > f) {
                f = decoratedMeasurementInOther2;
            }
        }
        if (z2) {
            guessMeasurement(f, i11);
            i15 = 0;
            for (int i17 = 0; i17 < i14; i17++) {
                android.view.View view2 = this.mSet[i17];
                measureChild(view2, 1073741824, true);
                int decoratedMeasurement2 = this.mOrientationHelper.getDecoratedMeasurement(view2);
                if (decoratedMeasurement2 > i15) {
                    i15 = decoratedMeasurement2;
                }
            }
        }
        for (int i18 = 0; i18 < i14; i18++) {
            android.view.View view3 = this.mSet[i18];
            if (this.mOrientationHelper.getDecoratedMeasurement(view3) != i15) {
                com.android.internal.widget.GridLayoutManager.LayoutParams layoutParams = (com.android.internal.widget.GridLayoutManager.LayoutParams) view3.getLayoutParams();
                android.graphics.Rect rect = layoutParams.mDecorInsets;
                int i19 = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
                int i20 = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
                int spaceForSpanRange = getSpaceForSpanRange(layoutParams.mSpanIndex, layoutParams.mSpanSize);
                if (this.mOrientation == 1) {
                    i10 = getChildMeasureSpec(spaceForSpanRange, 1073741824, i20, layoutParams.width, false);
                    childMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(i15 - i19, 1073741824);
                } else {
                    int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(i15 - i20, 1073741824);
                    childMeasureSpec = getChildMeasureSpec(spaceForSpanRange, 1073741824, i19, layoutParams.height, false);
                    i10 = makeMeasureSpec;
                }
                measureChildWithDecorationsAndMargin(view3, i10, childMeasureSpec, true);
            }
        }
        int i21 = 0;
        layoutChunkResult.mConsumed = i15;
        if (this.mOrientation == 1) {
            if (layoutState.mLayoutDirection == -1) {
                int i22 = layoutState.mOffset;
                int i23 = i22 - i15;
                i4 = 0;
                i3 = 0;
                i6 = i23;
                i5 = i22;
            } else {
                i6 = layoutState.mOffset;
                i5 = i6 + i15;
                i4 = 0;
                i3 = 0;
            }
        } else if (layoutState.mLayoutDirection == -1) {
            int i24 = layoutState.mOffset;
            i4 = i24 - i15;
            i3 = i24;
            i5 = 0;
            i6 = 0;
        } else {
            int i25 = layoutState.mOffset;
            i3 = i25 + i15;
            i4 = i25;
            i5 = 0;
            i6 = 0;
        }
        while (i21 < i14) {
            android.view.View view4 = this.mSet[i21];
            com.android.internal.widget.GridLayoutManager.LayoutParams layoutParams2 = (com.android.internal.widget.GridLayoutManager.LayoutParams) view4.getLayoutParams();
            if (this.mOrientation != 1) {
                int paddingTop = getPaddingTop() + this.mCachedBorders[layoutParams2.mSpanIndex];
                i7 = paddingTop;
                i8 = i4;
                i9 = i3;
                decoratedMeasurementInOther = this.mOrientationHelper.getDecoratedMeasurementInOther(view4) + paddingTop;
            } else if (isLayoutRTL()) {
                int paddingLeft = getPaddingLeft() + this.mCachedBorders[this.mSpanCount - layoutParams2.mSpanIndex];
                decoratedMeasurementInOther = i5;
                i9 = paddingLeft;
                i8 = paddingLeft - this.mOrientationHelper.getDecoratedMeasurementInOther(view4);
                i7 = i6;
            } else {
                int paddingLeft2 = getPaddingLeft() + this.mCachedBorders[layoutParams2.mSpanIndex];
                decoratedMeasurementInOther = i5;
                i8 = paddingLeft2;
                i9 = this.mOrientationHelper.getDecoratedMeasurementInOther(view4) + paddingLeft2;
                i7 = i6;
            }
            layoutDecoratedWithMargins(view4, i8, i7, i9, decoratedMeasurementInOther);
            if (layoutParams2.isItemRemoved() || layoutParams2.isItemChanged()) {
                layoutChunkResult.mIgnoreConsumed = true;
            }
            layoutChunkResult.mFocusable |= view4.hasFocusable();
            i21++;
            i5 = decoratedMeasurementInOther;
            i4 = i8;
            i3 = i9;
            i6 = i7;
        }
        java.util.Arrays.fill(this.mSet, (java.lang.Object) null);
    }

    private void measureChild(android.view.View view, int i, boolean z) {
        int i2;
        int i3;
        com.android.internal.widget.GridLayoutManager.LayoutParams layoutParams = (com.android.internal.widget.GridLayoutManager.LayoutParams) view.getLayoutParams();
        android.graphics.Rect rect = layoutParams.mDecorInsets;
        int i4 = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
        int i5 = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
        int spaceForSpanRange = getSpaceForSpanRange(layoutParams.mSpanIndex, layoutParams.mSpanSize);
        if (this.mOrientation == 1) {
            i3 = getChildMeasureSpec(spaceForSpanRange, i, i5, layoutParams.width, false);
            i2 = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getHeightMode(), i4, layoutParams.height, true);
        } else {
            int childMeasureSpec = getChildMeasureSpec(spaceForSpanRange, i, i4, layoutParams.height, false);
            int childMeasureSpec2 = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getWidthMode(), i5, layoutParams.width, true);
            i2 = childMeasureSpec;
            i3 = childMeasureSpec2;
        }
        measureChildWithDecorationsAndMargin(view, i3, i2, z);
    }

    private void guessMeasurement(float f, int i) {
        calculateItemBorders(java.lang.Math.max(java.lang.Math.round(f * this.mSpanCount), i));
    }

    private void measureChildWithDecorationsAndMargin(android.view.View view, int i, int i2, boolean z) {
        boolean shouldMeasureChild;
        com.android.internal.widget.RecyclerView.LayoutParams layoutParams = (com.android.internal.widget.RecyclerView.LayoutParams) view.getLayoutParams();
        if (z) {
            shouldMeasureChild = shouldReMeasureChild(view, i, i2, layoutParams);
        } else {
            shouldMeasureChild = shouldMeasureChild(view, i, i2, layoutParams);
        }
        if (shouldMeasureChild) {
            view.measure(i, i2);
        }
    }

    private void assignSpans(com.android.internal.widget.RecyclerView.Recycler recycler, com.android.internal.widget.RecyclerView.State state, int i, int i2, boolean z) {
        int i3;
        int i4;
        int i5;
        int i6 = 0;
        if (z) {
            i5 = 1;
            i4 = i;
            i3 = 0;
        } else {
            i3 = i - 1;
            i4 = -1;
            i5 = -1;
        }
        while (i3 != i4) {
            android.view.View view = this.mSet[i3];
            com.android.internal.widget.GridLayoutManager.LayoutParams layoutParams = (com.android.internal.widget.GridLayoutManager.LayoutParams) view.getLayoutParams();
            layoutParams.mSpanSize = getSpanSize(recycler, state, getPosition(view));
            layoutParams.mSpanIndex = i6;
            i6 += layoutParams.mSpanSize;
            i3 += i5;
        }
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    public void setSpanCount(int i) {
        if (i == this.mSpanCount) {
            return;
        }
        this.mPendingSpanCountChange = true;
        if (i < 1) {
            throw new java.lang.IllegalArgumentException("Span count should be at least 1. Provided " + i);
        }
        this.mSpanCount = i;
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        requestLayout();
    }

    public static abstract class SpanSizeLookup {
        final android.util.SparseIntArray mSpanIndexCache = new android.util.SparseIntArray();
        private boolean mCacheSpanIndices = false;

        public abstract int getSpanSize(int i);

        public void setSpanIndexCacheEnabled(boolean z) {
            this.mCacheSpanIndices = z;
        }

        public void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }

        public boolean isSpanIndexCacheEnabled() {
            return this.mCacheSpanIndices;
        }

        int getCachedSpanIndex(int i, int i2) {
            if (!this.mCacheSpanIndices) {
                return getSpanIndex(i, i2);
            }
            int i3 = this.mSpanIndexCache.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            int spanIndex = getSpanIndex(i, i2);
            this.mSpanIndexCache.put(i, spanIndex);
            return spanIndex;
        }

        public int getSpanIndex(int i, int i2) {
            int i3;
            int i4;
            int findReferenceIndexFromCache;
            int spanSize = getSpanSize(i);
            if (spanSize == i2) {
                return 0;
            }
            if (this.mCacheSpanIndices && this.mSpanIndexCache.size() > 0 && (findReferenceIndexFromCache = findReferenceIndexFromCache(i)) >= 0) {
                i4 = this.mSpanIndexCache.get(findReferenceIndexFromCache) + getSpanSize(findReferenceIndexFromCache);
                i3 = findReferenceIndexFromCache + 1;
            } else {
                i3 = 0;
                i4 = 0;
            }
            while (i3 < i) {
                int spanSize2 = getSpanSize(i3);
                i4 += spanSize2;
                if (i4 == i2) {
                    i4 = 0;
                } else if (i4 > i2) {
                    i4 = spanSize2;
                }
                i3++;
            }
            if (spanSize + i4 > i2) {
                return 0;
            }
            return i4;
        }

        int findReferenceIndexFromCache(int i) {
            int size = this.mSpanIndexCache.size() - 1;
            int i2 = 0;
            while (i2 <= size) {
                int i3 = (i2 + size) >>> 1;
                if (this.mSpanIndexCache.keyAt(i3) < i) {
                    i2 = i3 + 1;
                } else {
                    size = i3 - 1;
                }
            }
            int i4 = i2 - 1;
            if (i4 >= 0 && i4 < this.mSpanIndexCache.size()) {
                return this.mSpanIndexCache.keyAt(i4);
            }
            return -1;
        }

        public int getSpanGroupIndex(int i, int i2) {
            int spanSize = getSpanSize(i);
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                int spanSize2 = getSpanSize(i5);
                i3 += spanSize2;
                if (i3 == i2) {
                    i4++;
                    i3 = 0;
                } else if (i3 > i2) {
                    i4++;
                    i3 = spanSize2;
                }
            }
            if (i3 + spanSize > i2) {
                return i4 + 1;
            }
            return i4;
        }
    }

    @Override // com.android.internal.widget.LinearLayoutManager, com.android.internal.widget.RecyclerView.LayoutManager
    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && !this.mPendingSpanCountChange;
    }

    public static final class DefaultSpanSizeLookup extends com.android.internal.widget.GridLayoutManager.SpanSizeLookup {
        @Override // com.android.internal.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int i) {
            return 1;
        }

        @Override // com.android.internal.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanIndex(int i, int i2) {
            return i % i2;
        }
    }

    public static class LayoutParams extends com.android.internal.widget.RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        int mSpanIndex;
        int mSpanSize;

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(com.android.internal.widget.RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public int getSpanIndex() {
            return this.mSpanIndex;
        }

        public int getSpanSize() {
            return this.mSpanSize;
        }
    }
}
