package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class ListView extends android.widget.AbsListView {
    private static final float MAX_SCROLL_FACTOR = 0.33f;
    private static final int MIN_SCROLL_PREVIEW_PIXELS = 2;
    static final int NO_POSITION = -1;
    static final java.lang.String TAG = "ListView";
    private boolean mAreAllItemsSelectable;
    private final android.widget.ListView.ArrowScrollFocusResult mArrowScrollFocusResult;
    android.graphics.drawable.Drawable mDivider;
    int mDividerHeight;
    private boolean mDividerIsOpaque;
    private android.graphics.Paint mDividerPaint;
    private android.widget.ListView.FocusSelector mFocusSelector;
    private boolean mFooterDividersEnabled;
    java.util.ArrayList<android.widget.ListView.FixedViewInfo> mFooterViewInfos;
    private boolean mHeaderDividersEnabled;
    java.util.ArrayList<android.widget.ListView.FixedViewInfo> mHeaderViewInfos;
    private boolean mIsCacheColorOpaque;
    private boolean mItemsCanFocus;
    android.graphics.drawable.Drawable mOverScrollFooter;
    android.graphics.drawable.Drawable mOverScrollHeader;
    private final android.graphics.Rect mTempRect;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.ListView> {
        private int mDividerHeightId;
        private int mDividerId;
        private int mFooterDividersEnabledId;
        private int mHeaderDividersEnabledId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mDividerId = propertyMapper.mapObject("divider", 16843049);
            this.mDividerHeightId = propertyMapper.mapInt("dividerHeight", 16843050);
            this.mFooterDividersEnabledId = propertyMapper.mapBoolean("footerDividersEnabled", 16843311);
            this.mHeaderDividersEnabledId = propertyMapper.mapBoolean("headerDividersEnabled", 16843310);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.ListView listView, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mDividerId, listView.getDivider());
            propertyReader.readInt(this.mDividerHeightId, listView.getDividerHeight());
            propertyReader.readBoolean(this.mFooterDividersEnabledId, listView.areFooterDividersEnabled());
            propertyReader.readBoolean(this.mHeaderDividersEnabledId, listView.areHeaderDividersEnabled());
        }
    }

    public class FixedViewInfo {
        public java.lang.Object data;
        public boolean isSelectable;
        public android.view.View view;

        public FixedViewInfo() {
        }
    }

    public ListView(android.content.Context context) {
        this(context, null);
    }

    public ListView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842868);
    }

    public ListView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ListView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        int dimensionPixelSize;
        this.mHeaderViewInfos = com.google.android.collect.Lists.newArrayList();
        this.mFooterViewInfos = com.google.android.collect.Lists.newArrayList();
        this.mAreAllItemsSelectable = true;
        this.mItemsCanFocus = false;
        this.mTempRect = new android.graphics.Rect();
        this.mArrowScrollFocusResult = new android.widget.ListView.ArrowScrollFocusResult();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ListView, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.ListView, attributeSet, obtainStyledAttributes, i, i2);
        java.lang.CharSequence[] textArray = obtainStyledAttributes.getTextArray(0);
        if (textArray != null) {
            setAdapter((android.widget.ListAdapter) new android.widget.ArrayAdapter(context, 17367043, textArray));
        }
        android.graphics.drawable.Drawable drawable = obtainStyledAttributes.getDrawable(1);
        if (drawable != null) {
            setDivider(drawable);
        }
        android.graphics.drawable.Drawable drawable2 = obtainStyledAttributes.getDrawable(5);
        if (drawable2 != null) {
            setOverscrollHeader(drawable2);
        }
        android.graphics.drawable.Drawable drawable3 = obtainStyledAttributes.getDrawable(6);
        if (drawable3 != null) {
            setOverscrollFooter(drawable3);
        }
        if (obtainStyledAttributes.hasValueOrEmpty(2) && (dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(2, 0)) != 0) {
            setDividerHeight(dimensionPixelSize);
        }
        this.mHeaderDividersEnabled = obtainStyledAttributes.getBoolean(3, true);
        this.mFooterDividersEnabled = obtainStyledAttributes.getBoolean(4, true);
        obtainStyledAttributes.recycle();
    }

    public int getMaxScrollAmount() {
        return (int) ((this.mBottom - this.mTop) * MAX_SCROLL_FACTOR);
    }

    private void adjustViewsUpOrDown() {
        int childCount = getChildCount();
        if (childCount > 0) {
            int i = 0;
            if (!this.mStackFromBottom) {
                int top = getChildAt(0).getTop() - this.mListPadding.top;
                if (this.mFirstPosition != 0) {
                    top -= this.mDividerHeight;
                }
                if (top >= 0) {
                    i = top;
                }
            } else {
                int bottom = getChildAt(childCount - 1).getBottom() - (getHeight() - this.mListPadding.bottom);
                if (this.mFirstPosition + childCount < this.mItemCount) {
                    bottom += this.mDividerHeight;
                }
                if (bottom <= 0) {
                    i = bottom;
                }
            }
            if (i != 0) {
                offsetChildrenTopAndBottom(-i);
            }
        }
    }

    public void addHeaderView(android.view.View view, java.lang.Object obj, boolean z) {
        if (view.getParent() != null && view.getParent() != this && android.util.Log.isLoggable(TAG, 5)) {
            android.util.Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first.");
        }
        android.widget.ListView.FixedViewInfo fixedViewInfo = new android.widget.ListView.FixedViewInfo();
        fixedViewInfo.view = view;
        fixedViewInfo.data = obj;
        fixedViewInfo.isSelectable = z;
        this.mHeaderViewInfos.add(fixedViewInfo);
        this.mAreAllItemsSelectable &= z;
        if (this.mAdapter != null) {
            if (!(this.mAdapter instanceof android.widget.HeaderViewListAdapter)) {
                wrapHeaderListAdapterInternal();
            }
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
        }
    }

    public void addHeaderView(android.view.View view) {
        addHeaderView(view, null, true);
    }

    @Override // android.widget.AbsListView
    public int getHeaderViewsCount() {
        return this.mHeaderViewInfos.size();
    }

    public boolean removeHeaderView(android.view.View view) {
        boolean z = false;
        if (this.mHeaderViewInfos.size() <= 0) {
            return false;
        }
        if (this.mAdapter != null && ((android.widget.HeaderViewListAdapter) this.mAdapter).removeHeader(view)) {
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
            z = true;
        }
        removeFixedViewInfo(view, this.mHeaderViewInfos);
        return z;
    }

    private void removeFixedViewInfo(android.view.View view, java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (arrayList.get(i).view == view) {
                arrayList.remove(i);
                return;
            }
        }
    }

    public void addFooterView(android.view.View view, java.lang.Object obj, boolean z) {
        if (view.getParent() != null && view.getParent() != this && android.util.Log.isLoggable(TAG, 5)) {
            android.util.Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first.");
        }
        android.widget.ListView.FixedViewInfo fixedViewInfo = new android.widget.ListView.FixedViewInfo();
        fixedViewInfo.view = view;
        fixedViewInfo.data = obj;
        fixedViewInfo.isSelectable = z;
        this.mFooterViewInfos.add(fixedViewInfo);
        this.mAreAllItemsSelectable &= z;
        if (this.mAdapter != null) {
            if (!(this.mAdapter instanceof android.widget.HeaderViewListAdapter)) {
                wrapHeaderListAdapterInternal();
            }
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
        }
    }

    public void addFooterView(android.view.View view) {
        addFooterView(view, null, true);
    }

    @Override // android.widget.AbsListView
    public int getFooterViewsCount() {
        return this.mFooterViewInfos.size();
    }

    public boolean removeFooterView(android.view.View view) {
        boolean z = false;
        if (this.mFooterViewInfos.size() <= 0) {
            return false;
        }
        if (this.mAdapter != null && ((android.widget.HeaderViewListAdapter) this.mAdapter).removeFooter(view)) {
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
            z = true;
        }
        removeFixedViewInfo(view, this.mFooterViewInfos);
        return z;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.widget.AdapterView
    public android.widget.ListAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override // android.widget.AbsListView
    @android.view.RemotableViewMethod(asyncImpl = "setRemoteViewsAdapterAsync")
    public void setRemoteViewsAdapter(android.content.Intent intent) {
        super.setRemoteViewsAdapter(intent);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.widget.AbsListView, android.widget.AdapterView
    public void setAdapter(android.widget.ListAdapter listAdapter) {
        int lookForSelectablePosition;
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        resetList();
        this.mRecycler.clear();
        if (this.mHeaderViewInfos.size() > 0 || this.mFooterViewInfos.size() > 0) {
            this.mAdapter = wrapHeaderListAdapterInternal(this.mHeaderViewInfos, this.mFooterViewInfos, listAdapter);
        } else {
            this.mAdapter = listAdapter;
        }
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        super.setAdapter(listAdapter);
        if (this.mAdapter != null) {
            this.mAreAllItemsSelectable = this.mAdapter.areAllItemsEnabled();
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            checkFocus();
            this.mDataSetObserver = new android.widget.AbsListView.AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            this.mRecycler.setViewTypeCount(this.mAdapter.getViewTypeCount());
            if (this.mStackFromBottom) {
                lookForSelectablePosition = lookForSelectablePosition(this.mItemCount - 1, false);
            } else {
                lookForSelectablePosition = lookForSelectablePosition(0, true);
            }
            setSelectedPositionInt(lookForSelectablePosition);
            setNextSelectedPositionInt(lookForSelectablePosition);
            if (this.mItemCount == 0) {
                checkSelectionChanged();
            }
        } else {
            this.mAreAllItemsSelectable = true;
            checkFocus();
            checkSelectionChanged();
        }
        requestLayout();
    }

    @Override // android.widget.AbsListView
    void resetList() {
        clearRecycledState(this.mHeaderViewInfos);
        clearRecycledState(this.mFooterViewInfos);
        super.resetList();
        this.mLayoutMode = 0;
    }

    private void clearRecycledState(java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList) {
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                android.view.ViewGroup.LayoutParams layoutParams = arrayList.get(i).view.getLayoutParams();
                if (checkLayoutParams(layoutParams)) {
                    ((android.widget.AbsListView.LayoutParams) layoutParams).recycledHeaderFooter = false;
                }
            }
        }
    }

    private boolean showingTopFadingEdge() {
        return this.mFirstPosition > 0 || getChildAt(0).getTop() > this.mScrollY + this.mListPadding.top;
    }

    private boolean showingBottomFadingEdge() {
        int childCount = getChildCount();
        return (this.mFirstPosition + childCount) - 1 < this.mItemCount - 1 || getChildAt(childCount + (-1)).getBottom() < (this.mScrollY + getHeight()) - this.mListPadding.bottom;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(android.view.View view, android.graphics.Rect rect, boolean z) {
        int i;
        int i2;
        int i3;
        int i4 = rect.top;
        rect.offset(view.getLeft(), view.getTop());
        rect.offset(-view.getScrollX(), -view.getScrollY());
        int height = getHeight();
        int scrollY = getScrollY();
        int i5 = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (showingTopFadingEdge() && (this.mSelectedPosition > 0 || i4 > verticalFadingEdgeLength)) {
            scrollY += verticalFadingEdgeLength;
        }
        int bottom = getChildAt(getChildCount() - 1).getBottom();
        if (showingBottomFadingEdge() && (this.mSelectedPosition < this.mItemCount - 1 || rect.bottom < bottom - verticalFadingEdgeLength)) {
            i5 -= verticalFadingEdgeLength;
        }
        if (rect.bottom > i5 && rect.top > scrollY) {
            if (rect.height() > height) {
                i3 = (rect.top - scrollY) + 0;
            } else {
                i3 = (rect.bottom - i5) + 0;
            }
            i = java.lang.Math.min(i3, bottom - i5);
        } else if (rect.top < scrollY && rect.bottom < i5) {
            if (rect.height() > height) {
                i2 = 0 - (i5 - rect.bottom);
            } else {
                i2 = 0 - (scrollY - rect.top);
            }
            i = java.lang.Math.max(i2, getChildAt(0).getTop() - scrollY);
        } else {
            i = 0;
        }
        boolean z2 = i != 0;
        if (z2) {
            scrollListItemsBy(-i);
            positionSelector(-1, view);
            this.mSelectedTop = view.getTop();
            invalidate();
        }
        return z2;
    }

    @Override // android.widget.AbsListView
    void fillGap(boolean z) {
        int i;
        int height;
        int childCount = getChildCount();
        int i2 = 0;
        if (z) {
            if ((this.mGroupFlags & 34) == 34) {
                i2 = getListPaddingTop();
            }
            if (childCount > 0) {
                i2 = this.mDividerHeight + getChildAt(childCount - 1).getBottom();
            }
            fillDown(this.mFirstPosition + childCount, i2);
            correctTooHigh(getChildCount());
            return;
        }
        if ((this.mGroupFlags & 34) != 34) {
            i = 0;
        } else {
            i = getListPaddingBottom();
        }
        if (childCount > 0) {
            height = getChildAt(0).getTop() - this.mDividerHeight;
        } else {
            height = getHeight() - i;
        }
        fillUp(this.mFirstPosition - 1, height);
        correctTooLow(getChildCount());
    }

    private android.view.View fillDown(int i, int i2) {
        int i3;
        int i4 = this.mBottom - this.mTop;
        android.view.View view = null;
        if ((this.mGroupFlags & 34) != 34) {
            i3 = i2;
        } else {
            i4 -= this.mListPadding.bottom;
            i3 = i2;
        }
        while (true) {
            if (i3 >= i4 || i >= this.mItemCount) {
                break;
            }
            boolean z = i == this.mSelectedPosition;
            android.view.View makeAndAddView = makeAndAddView(i, i3, true, this.mListPadding.left, z);
            i3 = makeAndAddView.getBottom() + this.mDividerHeight;
            if (z) {
                view = makeAndAddView;
            }
            i++;
        }
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return view;
    }

    private android.view.View fillUp(int i, int i2) {
        int i3;
        int i4;
        android.view.View view = null;
        if ((this.mGroupFlags & 34) != 34) {
            i3 = i2;
            i4 = 0;
        } else {
            i4 = this.mListPadding.top;
            i3 = i2;
        }
        while (true) {
            if (i3 <= i4 || i < 0) {
                break;
            }
            boolean z = i == this.mSelectedPosition;
            android.view.View makeAndAddView = makeAndAddView(i, i3, false, this.mListPadding.left, z);
            i3 = makeAndAddView.getTop() - this.mDividerHeight;
            if (z) {
                view = makeAndAddView;
            }
            i--;
        }
        this.mFirstPosition = i + 1;
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return view;
    }

    private android.view.View fillFromTop(int i) {
        this.mFirstPosition = java.lang.Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = java.lang.Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        return fillDown(this.mFirstPosition, i);
    }

    private android.view.View fillFromMiddle(int i, int i2) {
        int i3 = i2 - i;
        int reconcileSelectedPosition = reconcileSelectedPosition();
        android.view.View makeAndAddView = makeAndAddView(reconcileSelectedPosition, i, true, this.mListPadding.left, true);
        this.mFirstPosition = reconcileSelectedPosition;
        int measuredHeight = makeAndAddView.getMeasuredHeight();
        if (measuredHeight <= i3) {
            makeAndAddView.offsetTopAndBottom((i3 - measuredHeight) / 2);
        }
        fillAboveAndBelow(makeAndAddView, reconcileSelectedPosition);
        if (!this.mStackFromBottom) {
            correctTooHigh(getChildCount());
        } else {
            correctTooLow(getChildCount());
        }
        return makeAndAddView;
    }

    private void fillAboveAndBelow(android.view.View view, int i) {
        int i2 = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            fillUp(i - 1, view.getTop() - i2);
            adjustViewsUpOrDown();
            fillDown(i + 1, view.getBottom() + i2);
        } else {
            fillDown(i + 1, view.getBottom() + i2);
            adjustViewsUpOrDown();
            fillUp(i - 1, view.getTop() - i2);
        }
    }

    private android.view.View fillFromSelection(int i, int i2, int i3) {
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int i4 = this.mSelectedPosition;
        int topSelectionPixel = getTopSelectionPixel(i2, verticalFadingEdgeLength, i4);
        int bottomSelectionPixel = getBottomSelectionPixel(i3, verticalFadingEdgeLength, i4);
        android.view.View makeAndAddView = makeAndAddView(i4, i, true, this.mListPadding.left, true);
        if (makeAndAddView.getBottom() > bottomSelectionPixel) {
            makeAndAddView.offsetTopAndBottom(-java.lang.Math.min(makeAndAddView.getTop() - topSelectionPixel, makeAndAddView.getBottom() - bottomSelectionPixel));
        } else if (makeAndAddView.getTop() < topSelectionPixel) {
            makeAndAddView.offsetTopAndBottom(java.lang.Math.min(topSelectionPixel - makeAndAddView.getTop(), bottomSelectionPixel - makeAndAddView.getBottom()));
        }
        fillAboveAndBelow(makeAndAddView, i4);
        if (!this.mStackFromBottom) {
            correctTooHigh(getChildCount());
        } else {
            correctTooLow(getChildCount());
        }
        return makeAndAddView;
    }

    private int getBottomSelectionPixel(int i, int i2, int i3) {
        if (i3 != this.mItemCount - 1) {
            return i - i2;
        }
        return i;
    }

    private int getTopSelectionPixel(int i, int i2, int i3) {
        if (i3 > 0) {
            return i + i2;
        }
        return i;
    }

    @Override // android.widget.AbsListView
    @android.view.RemotableViewMethod
    public void smoothScrollToPosition(int i) {
        super.smoothScrollToPosition(i);
    }

    @Override // android.widget.AbsListView
    @android.view.RemotableViewMethod
    public void smoothScrollByOffset(int i) {
        super.smoothScrollByOffset(i);
    }

    private android.view.View moveSelection(android.view.View view, android.view.View view2, int i, int i2, int i3) {
        android.view.View makeAndAddView;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int i4 = this.mSelectedPosition;
        int topSelectionPixel = getTopSelectionPixel(i2, verticalFadingEdgeLength, i4);
        int bottomSelectionPixel = getBottomSelectionPixel(i2, verticalFadingEdgeLength, i4);
        if (i > 0) {
            android.view.View makeAndAddView2 = makeAndAddView(i4 - 1, view.getTop(), true, this.mListPadding.left, false);
            int i5 = this.mDividerHeight;
            makeAndAddView = makeAndAddView(i4, makeAndAddView2.getBottom() + i5, true, this.mListPadding.left, true);
            if (makeAndAddView.getBottom() > bottomSelectionPixel) {
                int i6 = -java.lang.Math.min(java.lang.Math.min(makeAndAddView.getTop() - topSelectionPixel, makeAndAddView.getBottom() - bottomSelectionPixel), (i3 - i2) / 2);
                makeAndAddView2.offsetTopAndBottom(i6);
                makeAndAddView.offsetTopAndBottom(i6);
            }
            if (!this.mStackFromBottom) {
                fillUp(this.mSelectedPosition - 2, makeAndAddView.getTop() - i5);
                adjustViewsUpOrDown();
                fillDown(this.mSelectedPosition + 1, makeAndAddView.getBottom() + i5);
            } else {
                fillDown(this.mSelectedPosition + 1, makeAndAddView.getBottom() + i5);
                adjustViewsUpOrDown();
                fillUp(this.mSelectedPosition - 2, makeAndAddView.getTop() - i5);
            }
        } else if (i < 0) {
            if (view2 != null) {
                makeAndAddView = makeAndAddView(i4, view2.getTop(), true, this.mListPadding.left, true);
            } else {
                makeAndAddView = makeAndAddView(i4, view.getTop(), false, this.mListPadding.left, true);
            }
            if (makeAndAddView.getTop() < topSelectionPixel) {
                makeAndAddView.offsetTopAndBottom(java.lang.Math.min(java.lang.Math.min(topSelectionPixel - makeAndAddView.getTop(), bottomSelectionPixel - makeAndAddView.getBottom()), (i3 - i2) / 2));
            }
            fillAboveAndBelow(makeAndAddView, i4);
        } else {
            int top = view.getTop();
            makeAndAddView = makeAndAddView(i4, top, true, this.mListPadding.left, true);
            if (top < i2 && makeAndAddView.getBottom() < i2 + 20) {
                makeAndAddView.offsetTopAndBottom(i2 - makeAndAddView.getTop());
            }
            fillAboveAndBelow(makeAndAddView, i4);
        }
        return makeAndAddView;
    }

    private class FocusSelector implements java.lang.Runnable {
        private static final int STATE_REQUEST_FOCUS = 3;
        private static final int STATE_SET_SELECTION = 1;
        private static final int STATE_WAIT_FOR_LAYOUT = 2;
        private int mAction;
        private int mPosition;
        private int mPositionTop;

        private FocusSelector() {
        }

        android.widget.ListView.FocusSelector setupForSetSelection(int i, int i2) {
            this.mPosition = i;
            this.mPositionTop = i2;
            this.mAction = 1;
            return this;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mAction == 1) {
                android.widget.ListView.this.setSelectionFromTop(this.mPosition, this.mPositionTop);
                this.mAction = 2;
            } else if (this.mAction == 3) {
                android.view.View childAt = android.widget.ListView.this.getChildAt(this.mPosition - android.widget.ListView.this.mFirstPosition);
                if (childAt != null) {
                    childAt.requestFocus();
                }
                this.mAction = -1;
            }
        }

        java.lang.Runnable setupFocusIfValid(int i) {
            if (this.mAction != 2 || i != this.mPosition) {
                return null;
            }
            this.mAction = 3;
            return this;
        }

        void onLayoutComplete() {
            if (this.mAction == 2) {
                this.mAction = -1;
            }
        }
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        if (this.mFocusSelector != null) {
            removeCallbacks(this.mFocusSelector);
            this.mFocusSelector = null;
        }
        super.onDetachedFromWindow();
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        android.view.View focusedChild;
        if (getChildCount() > 0 && (focusedChild = getFocusedChild()) != null) {
            int indexOfChild = this.mFirstPosition + indexOfChild(focusedChild);
            int top = focusedChild.getTop() - java.lang.Math.max(0, focusedChild.getBottom() - (i2 - this.mPaddingTop));
            if (this.mFocusSelector == null) {
                this.mFocusSelector = new android.widget.ListView.FocusSelector();
            }
            post(this.mFocusSelector.setupForSetSelection(indexOfChild, top));
        }
        super.onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        super.onMeasure(i, i2);
        int mode = android.view.View.MeasureSpec.getMode(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        int size = android.view.View.MeasureSpec.getSize(i);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        int i7 = 0;
        this.mItemCount = this.mAdapter == null ? 0 : this.mAdapter.getCount();
        if (this.mItemCount <= 0 || !(mode == 0 || mode2 == 0)) {
            i3 = 0;
            i4 = 0;
        } else {
            android.view.View obtainView = obtainView(0, this.mIsScrap);
            measureScrapChild(obtainView, 0, i, size2);
            i3 = obtainView.getMeasuredWidth();
            i4 = obtainView.getMeasuredHeight();
            int combineMeasuredStates = combineMeasuredStates(0, obtainView.getMeasuredState());
            if (recycleOnMeasure() && this.mRecycler.shouldRecycleViewType(((android.widget.AbsListView.LayoutParams) obtainView.getLayoutParams()).viewType)) {
                this.mRecycler.addScrapView(obtainView, 0);
            }
            i7 = combineMeasuredStates;
        }
        if (mode == 0) {
            i5 = this.mListPadding.left + this.mListPadding.right + i3 + getVerticalScrollbarWidth();
        } else {
            i5 = ((-16777216) & i7) | size;
        }
        if (mode2 != 0) {
            i6 = size2;
        } else {
            i6 = this.mListPadding.top + this.mListPadding.bottom + i4 + (getVerticalFadingEdgeLength() * 2);
        }
        if (mode2 == Integer.MIN_VALUE) {
            i6 = measureHeightOfChildren(i, 0, -1, i6, -1);
        }
        setMeasuredDimension(i5, i6);
        this.mWidthMeasureSpec = i;
    }

    private void measureScrapChild(android.view.View view, int i, int i2, int i3) {
        int makeSafeMeasureSpec;
        android.widget.AbsListView.LayoutParams layoutParams = (android.widget.AbsListView.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = (android.widget.AbsListView.LayoutParams) generateDefaultLayoutParams();
            view.setLayoutParams(layoutParams);
        }
        layoutParams.viewType = this.mAdapter.getItemViewType(i);
        layoutParams.isEnabled = this.mAdapter.isEnabled(i);
        layoutParams.forceAdd = true;
        int childMeasureSpec = android.view.ViewGroup.getChildMeasureSpec(i2, this.mListPadding.left + this.mListPadding.right, layoutParams.width);
        int i4 = layoutParams.height;
        if (i4 > 0) {
            makeSafeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(i4, 1073741824);
        } else {
            makeSafeMeasureSpec = android.view.View.MeasureSpec.makeSafeMeasureSpec(i3, 0);
        }
        view.measure(childMeasureSpec, makeSafeMeasureSpec);
        view.forceLayout();
    }

    @android.view.ViewDebug.ExportedProperty(category = android.app.slice.Slice.HINT_LIST)
    protected boolean recycleOnMeasure() {
        return true;
    }

    final int measureHeightOfChildren(int i, int i2, int i3, int i4, int i5) {
        android.widget.ListAdapter listAdapter = this.mAdapter;
        if (listAdapter == null) {
            return this.mListPadding.top + this.mListPadding.bottom;
        }
        int i6 = this.mListPadding.top + this.mListPadding.bottom;
        int i7 = this.mDividerHeight;
        if (i3 == -1) {
            i3 = listAdapter.getCount() - 1;
        }
        android.widget.AbsListView.RecycleBin recycleBin = this.mRecycler;
        boolean recycleOnMeasure = recycleOnMeasure();
        boolean[] zArr = this.mIsScrap;
        int i8 = 0;
        while (i2 <= i3) {
            android.view.View obtainView = obtainView(i2, zArr);
            measureScrapChild(obtainView, i2, i, i4);
            if (i2 > 0) {
                i6 += i7;
            }
            if (recycleOnMeasure && recycleBin.shouldRecycleViewType(((android.widget.AbsListView.LayoutParams) obtainView.getLayoutParams()).viewType)) {
                recycleBin.addScrapView(obtainView, -1);
            }
            i6 += obtainView.getMeasuredHeight();
            if (i6 >= i4) {
                if (i5 < 0 || i2 <= i5 || i8 <= 0 || i6 == i4) {
                    return i4;
                }
                return i8;
            }
            if (i5 >= 0 && i2 >= i5) {
                i8 = i6;
            }
            i2++;
        }
        return i6;
    }

    @Override // android.widget.AbsListView
    int findMotionRow(int i) {
        int childCount = getChildCount();
        if (childCount > 0) {
            if (!this.mStackFromBottom) {
                for (int i2 = 0; i2 < childCount; i2++) {
                    if (i <= getChildAt(i2).getBottom()) {
                        return this.mFirstPosition + i2;
                    }
                }
                return -1;
            }
            for (int i3 = childCount - 1; i3 >= 0; i3--) {
                if (i >= getChildAt(i3).getTop()) {
                    return this.mFirstPosition + i3;
                }
            }
            return -1;
        }
        return -1;
    }

    private android.view.View fillSpecific(int i, int i2) {
        android.view.View view;
        android.view.View view2;
        boolean z = i == this.mSelectedPosition;
        android.view.View makeAndAddView = makeAndAddView(i, i2, true, this.mListPadding.left, z);
        this.mFirstPosition = i;
        int i3 = this.mDividerHeight;
        if (!this.mStackFromBottom) {
            view = fillUp(i - 1, makeAndAddView.getTop() - i3);
            adjustViewsUpOrDown();
            view2 = fillDown(i + 1, makeAndAddView.getBottom() + i3);
            int childCount = getChildCount();
            if (childCount > 0) {
                correctTooHigh(childCount);
            }
        } else {
            android.view.View fillDown = fillDown(i + 1, makeAndAddView.getBottom() + i3);
            adjustViewsUpOrDown();
            android.view.View fillUp = fillUp(i - 1, makeAndAddView.getTop() - i3);
            int childCount2 = getChildCount();
            if (childCount2 > 0) {
                correctTooLow(childCount2);
            }
            view = fillUp;
            view2 = fillDown;
        }
        if (z) {
            return makeAndAddView;
        }
        if (view != null) {
            return view;
        }
        return view2;
    }

    private void correctTooHigh(int i) {
        if ((this.mFirstPosition + i) - 1 == this.mItemCount - 1 && i > 0) {
            int bottom = ((this.mBottom - this.mTop) - this.mListPadding.bottom) - getChildAt(i - 1).getBottom();
            android.view.View childAt = getChildAt(0);
            int top = childAt.getTop();
            if (bottom > 0) {
                if (this.mFirstPosition > 0 || top < this.mListPadding.top) {
                    if (this.mFirstPosition == 0) {
                        bottom = java.lang.Math.min(bottom, this.mListPadding.top - top);
                    }
                    offsetChildrenTopAndBottom(bottom);
                    if (this.mFirstPosition > 0) {
                        fillUp(this.mFirstPosition - 1, childAt.getTop() - this.mDividerHeight);
                        adjustViewsUpOrDown();
                    }
                }
            }
        }
    }

    private void correctTooLow(int i) {
        if (this.mFirstPosition == 0 && i > 0) {
            int top = getChildAt(0).getTop();
            int i2 = this.mListPadding.top;
            int i3 = (this.mBottom - this.mTop) - this.mListPadding.bottom;
            int i4 = top - i2;
            android.view.View childAt = getChildAt(i - 1);
            int bottom = childAt.getBottom();
            int i5 = (this.mFirstPosition + i) - 1;
            if (i4 > 0) {
                if (i5 < this.mItemCount - 1 || bottom > i3) {
                    if (i5 == this.mItemCount - 1) {
                        i4 = java.lang.Math.min(i4, bottom - i3);
                    }
                    offsetChildrenTopAndBottom(-i4);
                    if (i5 < this.mItemCount - 1) {
                        fillDown(i5 + 1, childAt.getBottom() + this.mDividerHeight);
                        adjustViewsUpOrDown();
                        return;
                    }
                    return;
                }
                if (i5 == this.mItemCount - 1) {
                    adjustViewsUpOrDown();
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:152:0x0275 A[Catch: all -> 0x037d, TryCatch #0 {all -> 0x037d, blocks: (B:73:0x0123, B:77:0x012e, B:80:0x014e, B:81:0x0156, B:83:0x015b, B:85:0x01ba, B:86:0x020e, B:88:0x021d, B:90:0x0221, B:92:0x0227, B:96:0x0231, B:100:0x0242, B:102:0x0248, B:103:0x024b, B:105:0x025e, B:107:0x02b1, B:110:0x02b9, B:112:0x02c0, B:115:0x02c9, B:116:0x02d8, B:119:0x02e1, B:121:0x02f5, B:123:0x02fc, B:125:0x0302, B:126:0x0305, B:128:0x030e, B:129:0x0316, B:131:0x0325, B:132:0x0328, B:140:0x0250, B:141:0x0237, B:144:0x025a, B:145:0x0265, B:147:0x026a, B:152:0x0275, B:154:0x0280, B:156:0x02a4, B:159:0x02ac, B:160:0x0286, B:162:0x028b, B:164:0x0296, B:166:0x029c, B:168:0x01c8, B:169:0x01dc, B:171:0x01e0, B:173:0x01e6, B:176:0x01ef, B:177:0x01eb, B:178:0x01f4, B:180:0x01fa, B:183:0x0203, B:184:0x01ff, B:185:0x0208, B:186:0x015e, B:187:0x0166, B:188:0x0170, B:190:0x017c, B:192:0x0180, B:194:0x0188, B:196:0x018f, B:198:0x019e, B:199:0x01a8, B:200:0x01ad, B:201:0x0147, B:209:0x033c, B:210:0x037c), top: B:42:0x00b3 }] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0286 A[Catch: all -> 0x037d, TryCatch #0 {all -> 0x037d, blocks: (B:73:0x0123, B:77:0x012e, B:80:0x014e, B:81:0x0156, B:83:0x015b, B:85:0x01ba, B:86:0x020e, B:88:0x021d, B:90:0x0221, B:92:0x0227, B:96:0x0231, B:100:0x0242, B:102:0x0248, B:103:0x024b, B:105:0x025e, B:107:0x02b1, B:110:0x02b9, B:112:0x02c0, B:115:0x02c9, B:116:0x02d8, B:119:0x02e1, B:121:0x02f5, B:123:0x02fc, B:125:0x0302, B:126:0x0305, B:128:0x030e, B:129:0x0316, B:131:0x0325, B:132:0x0328, B:140:0x0250, B:141:0x0237, B:144:0x025a, B:145:0x0265, B:147:0x026a, B:152:0x0275, B:154:0x0280, B:156:0x02a4, B:159:0x02ac, B:160:0x0286, B:162:0x028b, B:164:0x0296, B:166:0x029c, B:168:0x01c8, B:169:0x01dc, B:171:0x01e0, B:173:0x01e6, B:176:0x01ef, B:177:0x01eb, B:178:0x01f4, B:180:0x01fa, B:183:0x0203, B:184:0x01ff, B:185:0x0208, B:186:0x015e, B:187:0x0166, B:188:0x0170, B:190:0x017c, B:192:0x0180, B:194:0x0188, B:196:0x018f, B:198:0x019e, B:199:0x01a8, B:200:0x01ad, B:201:0x0147, B:209:0x033c, B:210:0x037c), top: B:42:0x00b3 }] */
    @Override // android.widget.AbsListView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void layoutChildren() {
        int i;
        android.view.View view;
        android.view.View view2;
        android.view.View view3;
        int i2;
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo;
        android.view.View view4;
        android.view.View view5;
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo2;
        android.view.View view6;
        android.view.View fillFromTop;
        java.lang.Runnable runnable;
        boolean z;
        android.view.View childAt;
        android.view.View accessibilityFocusedChild;
        boolean z2 = this.mBlockLayoutRequests;
        if (z2) {
            return;
        }
        this.mBlockLayoutRequests = true;
        try {
            super.layoutChildren();
            invalidate();
            if (this.mAdapter == null) {
                resetList();
                invokeOnItemScrollListener();
                if (this.mFocusSelector != null) {
                    this.mFocusSelector.onLayoutComplete();
                }
                if (z2) {
                    return;
                }
                this.mBlockLayoutRequests = false;
                return;
            }
            int i3 = this.mListPadding.top;
            int i4 = (this.mBottom - this.mTop) - this.mListPadding.bottom;
            int childCount = getChildCount();
            switch (this.mLayoutMode) {
                case 2:
                    int i5 = this.mNextSelectedPosition - this.mFirstPosition;
                    if (i5 >= 0 && i5 < childCount) {
                        view2 = getChildAt(i5);
                        i = 0;
                        view = null;
                        view3 = null;
                        break;
                    }
                case 1:
                case 3:
                case 4:
                case 5:
                    i = 0;
                    view = null;
                    view2 = null;
                    view3 = null;
                    break;
                default:
                    int i6 = this.mSelectedPosition - this.mFirstPosition;
                    android.view.View childAt2 = (i6 < 0 || i6 >= childCount) ? null : getChildAt(i6);
                    android.view.View childAt3 = getChildAt(0);
                    int i7 = this.mNextSelectedPosition >= 0 ? this.mNextSelectedPosition - this.mSelectedPosition : 0;
                    android.view.View view7 = childAt2;
                    view2 = getChildAt(i6 + i7);
                    view = view7;
                    int i8 = i7;
                    view3 = childAt3;
                    i = i8;
                    break;
            }
            boolean z3 = this.mDataChanged;
            if (z3) {
                handleDataChanged();
            }
            if (this.mItemCount == 0) {
                resetList();
                invokeOnItemScrollListener();
                if (this.mFocusSelector != null) {
                    this.mFocusSelector.onLayoutComplete();
                }
                if (z2) {
                    return;
                }
                this.mBlockLayoutRequests = false;
                return;
            }
            try {
                if (this.mItemCount != this.mAdapter.getCount()) {
                    throw new java.lang.IllegalStateException("The content of the adapter has changed but ListView did not receive a notification. Make sure the content of your adapter is not modified from a background thread, but only from the UI thread. Make sure your adapter calls notifyDataSetChanged() when its content changes. [in ListView(" + getId() + ", " + getClass() + ") with Adapter(" + this.mAdapter.getClass() + ")]");
                }
                setSelectedPositionInt(this.mNextSelectedPosition);
                android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
                if (viewRootImpl == null || (view4 = viewRootImpl.getAccessibilityFocusedHost()) == null || (accessibilityFocusedChild = getAccessibilityFocusedChild(view4)) == null) {
                    i2 = -1;
                    accessibilityNodeInfo = null;
                    view4 = null;
                } else {
                    if (z3 && !isDirectChildHeaderOrFooter(accessibilityFocusedChild) && (!accessibilityFocusedChild.hasTransientState() || !this.mAdapterHasStableIds)) {
                        accessibilityNodeInfo = null;
                        view4 = null;
                        i2 = getPositionForView(accessibilityFocusedChild);
                    }
                    accessibilityNodeInfo = viewRootImpl.getAccessibilityFocusedVirtualView();
                    i2 = getPositionForView(accessibilityFocusedChild);
                }
                android.view.View focusedChild = getFocusedChild();
                if (focusedChild != null) {
                    if (z3 && !isDirectChildHeaderOrFooter(focusedChild) && !focusedChild.hasTransientState() && !this.mAdapterHasStableIds) {
                        view5 = null;
                        focusedChild = null;
                        requestFocus();
                    }
                    view5 = findFocus();
                    if (view5 != null) {
                        view5.dispatchStartTemporaryDetach();
                    }
                    requestFocus();
                } else {
                    view5 = null;
                    focusedChild = null;
                }
                int i9 = this.mFirstPosition;
                int i10 = i2;
                android.widget.AbsListView.RecycleBin recycleBin = this.mRecycler;
                if (z3) {
                    int i11 = 0;
                    while (i11 < childCount) {
                        recycleBin.addScrapView(getChildAt(i11), i9 + i11);
                        i11++;
                        accessibilityNodeInfo = accessibilityNodeInfo;
                        view4 = view4;
                    }
                    accessibilityNodeInfo2 = accessibilityNodeInfo;
                    view6 = view4;
                } else {
                    accessibilityNodeInfo2 = accessibilityNodeInfo;
                    view6 = view4;
                    recycleBin.fillActiveViews(childCount, i9);
                }
                detachAllViewsFromParent();
                recycleBin.removeSkippedScrap();
                switch (this.mLayoutMode) {
                    case 1:
                        this.mFirstPosition = 0;
                        fillFromTop = fillFromTop(i3);
                        adjustViewsUpOrDown();
                        break;
                    case 2:
                        if (view2 == null) {
                            fillFromTop = fillFromMiddle(i3, i4);
                            break;
                        } else {
                            fillFromTop = fillFromSelection(view2.getTop(), i3, i4);
                            break;
                        }
                    case 3:
                        fillFromTop = fillUp(this.mItemCount - 1, i4);
                        adjustViewsUpOrDown();
                        break;
                    case 4:
                        int reconcileSelectedPosition = reconcileSelectedPosition();
                        android.view.View fillSpecific = fillSpecific(reconcileSelectedPosition, this.mSpecificTop);
                        if (fillSpecific == null && this.mFocusSelector != null && (runnable = this.mFocusSelector.setupFocusIfValid(reconcileSelectedPosition)) != null) {
                            post(runnable);
                        }
                        fillFromTop = fillSpecific;
                        break;
                    case 5:
                        fillFromTop = fillSpecific(this.mSyncPosition, this.mSpecificTop);
                        break;
                    case 6:
                        fillFromTop = moveSelection(view, view2, i, i3, i4);
                        break;
                    default:
                        if (childCount != 0) {
                            if (this.mSelectedPosition >= 0 && this.mSelectedPosition < this.mItemCount) {
                                int i12 = this.mSelectedPosition;
                                if (view != null) {
                                    i3 = view.getTop();
                                }
                                fillFromTop = fillSpecific(i12, i3);
                                break;
                            } else if (this.mFirstPosition >= this.mItemCount) {
                                fillFromTop = fillSpecific(0, i3);
                                break;
                            } else {
                                int i13 = this.mFirstPosition;
                                if (view3 != null) {
                                    i3 = view3.getTop();
                                }
                                fillFromTop = fillSpecific(i13, i3);
                                break;
                            }
                        } else if (!this.mStackFromBottom) {
                            setSelectedPositionInt(lookForSelectablePosition(0, true));
                            fillFromTop = fillFromTop(i3);
                            break;
                        } else {
                            setSelectedPositionInt(lookForSelectablePosition(this.mItemCount - 1, false));
                            fillFromTop = fillUp(this.mItemCount - 1, i4);
                            break;
                        }
                }
                recycleBin.scrapActiveViews();
                removeUnusedFixedViews(this.mHeaderViewInfos);
                removeUnusedFixedViews(this.mFooterViewInfos);
                if (fillFromTop != null) {
                    if (this.mItemsCanFocus && hasFocus() && !fillFromTop.hasFocus()) {
                        if ((fillFromTop == focusedChild && view5 != null && view5.requestFocus()) || fillFromTop.requestFocus()) {
                            fillFromTop.setSelected(false);
                            this.mSelectorRect.setEmpty();
                        } else {
                            android.view.View focusedChild2 = getFocusedChild();
                            if (focusedChild2 != null) {
                                focusedChild2.clearFocus();
                            }
                            positionSelector(-1, fillFromTop);
                        }
                    } else {
                        positionSelector(-1, fillFromTop);
                    }
                    this.mSelectedTop = fillFromTop.getTop();
                } else {
                    if (this.mTouchMode != 1 && this.mTouchMode != 2) {
                        z = false;
                        if (!z) {
                            android.view.View childAt4 = getChildAt(this.mMotionPosition - this.mFirstPosition);
                            if (childAt4 != null) {
                                positionSelector(this.mMotionPosition, childAt4);
                            }
                        } else if (this.mSelectorPosition != -1) {
                            android.view.View childAt5 = getChildAt(this.mSelectorPosition - this.mFirstPosition);
                            if (childAt5 != null) {
                                positionSelector(this.mSelectorPosition, childAt5);
                            }
                        } else {
                            this.mSelectedTop = 0;
                            this.mSelectorRect.setEmpty();
                        }
                        if (hasFocus() && view5 != null) {
                            view5.requestFocus();
                        }
                    }
                    z = true;
                    if (!z) {
                    }
                    if (hasFocus()) {
                        view5.requestFocus();
                    }
                }
                if (viewRootImpl != null && viewRootImpl.getAccessibilityFocusedHost() == null) {
                    if (view6 != null && view6.isAttachedToWindow()) {
                        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = view6.getAccessibilityNodeProvider();
                        if (accessibilityNodeInfo2 == null || accessibilityNodeProvider == null) {
                            view6.requestAccessibilityFocus();
                        } else {
                            accessibilityNodeProvider.performAction(android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo2.getSourceNodeId()), 64, null);
                        }
                    } else if (i10 != -1 && (childAt = getChildAt(android.util.MathUtils.constrain(i10 - this.mFirstPosition, 0, getChildCount() - 1))) != null) {
                        childAt.requestAccessibilityFocus();
                    }
                }
                if (view5 != null && view5.getWindowToken() != null) {
                    view5.dispatchFinishTemporaryDetach();
                }
                this.mLayoutMode = 0;
                this.mDataChanged = false;
                if (this.mPositionScrollAfterLayout != null) {
                    post(this.mPositionScrollAfterLayout);
                    this.mPositionScrollAfterLayout = null;
                }
                this.mNeedSync = false;
                setNextSelectedPositionInt(this.mSelectedPosition);
                updateScrollIndicators();
                if (this.mItemCount > 0) {
                    checkSelectionChanged();
                }
                invokeOnItemScrollListener();
                if (this.mFocusSelector != null) {
                    this.mFocusSelector.onLayoutComplete();
                }
                if (z2) {
                    return;
                }
                this.mBlockLayoutRequests = false;
            } catch (java.lang.Throwable th) {
                th = th;
                if (this.mFocusSelector != null) {
                    this.mFocusSelector.onLayoutComplete();
                }
                if (!z2) {
                    this.mBlockLayoutRequests = false;
                }
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    @Override // android.widget.AbsListView
    boolean trackMotionScroll(int i, int i2) {
        boolean trackMotionScroll = super.trackMotionScroll(i, i2);
        removeUnusedFixedViews(this.mHeaderViewInfos);
        removeUnusedFixedViews(this.mFooterViewInfos);
        return trackMotionScroll;
    }

    private void removeUnusedFixedViews(java.util.List<android.widget.ListView.FixedViewInfo> list) {
        if (list == null) {
            return;
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            android.view.View view = list.get(size).view;
            android.widget.AbsListView.LayoutParams layoutParams = (android.widget.AbsListView.LayoutParams) view.getLayoutParams();
            if (view.getParent() == null && layoutParams != null && layoutParams.recycledHeaderFooter) {
                removeDetachedView(view, false);
                layoutParams.recycledHeaderFooter = false;
            }
        }
    }

    private boolean isDirectChildHeaderOrFooter(android.view.View view) {
        java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList = this.mHeaderViewInfos;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (view == arrayList.get(i).view) {
                return true;
            }
        }
        java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList2 = this.mFooterViewInfos;
        int size2 = arrayList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            if (view == arrayList2.get(i2).view) {
                return true;
            }
        }
        return false;
    }

    private android.view.View makeAndAddView(int i, int i2, boolean z, int i3, boolean z2) {
        android.view.View activeView;
        if (!this.mDataChanged && (activeView = this.mRecycler.getActiveView(i)) != null) {
            setupChild(activeView, i, i2, z, i3, z2, true);
            return activeView;
        }
        android.view.View obtainView = obtainView(i, this.mIsScrap);
        setupChild(obtainView, i, i2, z, i3, z2, this.mIsScrap[0]);
        return obtainView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setupChild(android.view.View view, int i, int i2, boolean z, int i3, boolean z2, boolean z3) {
        int makeSafeMeasureSpec;
        android.os.Trace.traceBegin(8L, "setupListItem");
        boolean z4 = z2 && shouldShowSelector();
        boolean z5 = z4 != view.isSelected();
        int i4 = this.mTouchMode;
        boolean z6 = i4 > 0 && i4 < 3 && this.mMotionPosition == i;
        boolean z7 = z6 != view.isPressed();
        boolean z8 = !z3 || z5 || view.isLayoutRequested();
        android.widget.AbsListView.LayoutParams layoutParams = (android.widget.AbsListView.LayoutParams) view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = (android.widget.AbsListView.LayoutParams) generateDefaultLayoutParams();
        }
        layoutParams.viewType = this.mAdapter.getItemViewType(i);
        layoutParams.isEnabled = this.mAdapter.isEnabled(i);
        if (z5) {
            view.setSelected(z4);
        }
        if (z7) {
            view.setPressed(z6);
        }
        if (this.mChoiceMode != 0 && this.mCheckStates != null) {
            if (view instanceof android.widget.Checkable) {
                ((android.widget.Checkable) view).setChecked(this.mCheckStates.get(i));
            } else if (getContext().getApplicationInfo().targetSdkVersion >= 11) {
                view.setActivated(this.mCheckStates.get(i));
            }
        }
        if ((z3 && !layoutParams.forceAdd) || (layoutParams.recycledHeaderFooter && layoutParams.viewType == -2)) {
            attachViewToParent(view, z ? -1 : 0, layoutParams);
            if (z3 && ((android.widget.AbsListView.LayoutParams) view.getLayoutParams()).scrappedFromPosition != i) {
                view.jumpDrawablesToCurrentState();
            }
        } else {
            layoutParams.forceAdd = false;
            if (layoutParams.viewType == -2) {
                layoutParams.recycledHeaderFooter = true;
            }
            addViewInLayout(view, z ? -1 : 0, layoutParams, true);
            view.resolveRtlPropertiesIfNeeded();
        }
        if (z8) {
            int childMeasureSpec = android.view.ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mListPadding.left + this.mListPadding.right, layoutParams.width);
            int i5 = layoutParams.height;
            if (i5 > 0) {
                makeSafeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
            } else {
                makeSafeMeasureSpec = android.view.View.MeasureSpec.makeSafeMeasureSpec(getMeasuredHeight(), 0);
            }
            view.measure(childMeasureSpec, makeSafeMeasureSpec);
        } else {
            cleanupLayoutState(view);
        }
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i6 = z ? i2 : i2 - measuredHeight;
        if (z8) {
            view.layout(i3, i6, measuredWidth + i3, measuredHeight + i6);
        } else {
            view.offsetLeftAndRight(i3 - view.getLeft());
            view.offsetTopAndBottom(i6 - view.getTop());
        }
        if (this.mCachingStarted && !view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }
        android.os.Trace.traceEnd(8L);
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup
    protected boolean canAnimate() {
        return super.canAnimate() && this.mItemCount > 0;
    }

    @Override // android.widget.AdapterView
    public void setSelection(int i) {
        setSelectionFromTop(i, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x000f, code lost:
    
        if (r4 == (r0 + 1)) goto L11;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0017  */
    @Override // android.widget.AbsListView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void setSelectionInt(int i) {
        boolean z;
        setNextSelectedPositionInt(i);
        int i2 = this.mSelectedPosition;
        if (i2 >= 0) {
            z = true;
            if (i != i2 - 1) {
            }
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            layoutChildren();
            if (!z) {
                awakenScrollBars();
                return;
            }
            return;
        }
        z = false;
        if (this.mPositionScroller != null) {
        }
        layoutChildren();
        if (!z) {
        }
    }

    @Override // android.widget.AdapterView
    int lookForSelectablePosition(int i, boolean z) {
        android.widget.ListAdapter listAdapter = this.mAdapter;
        if (listAdapter == null || isInTouchMode()) {
            return -1;
        }
        int count = listAdapter.getCount();
        if (!this.mAreAllItemsSelectable) {
            if (z) {
                i = java.lang.Math.max(0, i);
                while (i < count && !listAdapter.isEnabled(i)) {
                    i++;
                }
            } else {
                i = java.lang.Math.min(i, count - 1);
                while (i >= 0 && !listAdapter.isEnabled(i)) {
                    i--;
                }
            }
        }
        if (i < 0 || i >= count) {
            return -1;
        }
        return i;
    }

    int lookForSelectablePositionAfter(int i, int i2, boolean z) {
        int max;
        android.widget.ListAdapter listAdapter = this.mAdapter;
        if (listAdapter == null || isInTouchMode()) {
            return -1;
        }
        int lookForSelectablePosition = lookForSelectablePosition(i2, z);
        if (lookForSelectablePosition != -1) {
            return lookForSelectablePosition;
        }
        int count = listAdapter.getCount() - 1;
        int constrain = android.util.MathUtils.constrain(i, -1, count);
        if (z) {
            max = java.lang.Math.min(i2 - 1, count);
            while (max > constrain && !listAdapter.isEnabled(max)) {
                max--;
            }
            if (max <= constrain) {
                return -1;
            }
        } else {
            max = java.lang.Math.max(0, i2 + 1);
            while (max < constrain && !listAdapter.isEnabled(max)) {
                max++;
            }
            if (max >= constrain) {
                return -1;
            }
        }
        return max;
    }

    public void setSelectionAfterHeaderView() {
        int headerViewsCount = getHeaderViewsCount();
        if (headerViewsCount > 0) {
            this.mNextSelectedPosition = 0;
        } else if (this.mAdapter != null) {
            setSelection(headerViewsCount);
        } else {
            this.mNextSelectedPosition = headerViewsCount;
            this.mLayoutMode = 2;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        boolean dispatchKeyEvent = super.dispatchKeyEvent(keyEvent);
        if (!dispatchKeyEvent && getFocusedChild() != null && keyEvent.getAction() == 0) {
            return onKeyDown(keyEvent.getKeyCode(), keyEvent);
        }
        return dispatchKeyEvent;
    }

    @Override // android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        return commonKey(i, 1, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, android.view.KeyEvent keyEvent) {
        return commonKey(i, i2, keyEvent);
    }

    @Override // android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        return commonKey(i, 1, keyEvent);
    }

    private boolean commonKey(int i, int i2, android.view.KeyEvent keyEvent) {
        boolean z;
        int i3;
        if (this.mAdapter == null || !isAttachedToWindow()) {
            return false;
        }
        if (this.mDataChanged) {
            layoutChildren();
        }
        int action = keyEvent.getAction();
        if (android.view.KeyEvent.isConfirmKey(i) && keyEvent.hasNoModifiers() && action != 1) {
            z = resurrectSelectionIfNeeded();
            if (!z && keyEvent.getRepeatCount() == 0 && getChildCount() > 0) {
                keyPressed();
                z = true;
            }
        } else {
            z = false;
        }
        if (!z && action != 1) {
            switch (i) {
                case 19:
                    if (keyEvent.hasNoModifiers()) {
                        z = resurrectSelectionIfNeeded();
                        if (!z) {
                            while (true) {
                                i3 = i2 - 1;
                                if (i2 > 0 && arrowScroll(33)) {
                                    z = true;
                                    i2 = i3;
                                }
                            }
                            i2 = i3;
                            break;
                        }
                    } else if (keyEvent.hasModifiers(2)) {
                        if (!resurrectSelectionIfNeeded() && !fullScroll(33)) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    break;
                case 20:
                    if (keyEvent.hasNoModifiers()) {
                        z = resurrectSelectionIfNeeded();
                        if (!z) {
                            while (true) {
                                i3 = i2 - 1;
                                if (i2 > 0 && arrowScroll(130)) {
                                    z = true;
                                    i2 = i3;
                                }
                            }
                            i2 = i3;
                            break;
                        }
                    } else if (keyEvent.hasModifiers(2)) {
                        if (!resurrectSelectionIfNeeded() && !fullScroll(130)) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    break;
                case 21:
                    if (keyEvent.hasNoModifiers()) {
                        z = handleHorizontalFocusWithinListItem(17);
                        break;
                    }
                    break;
                case 22:
                    if (keyEvent.hasNoModifiers()) {
                        z = handleHorizontalFocusWithinListItem(66);
                        break;
                    }
                    break;
                case 61:
                    if (keyEvent.hasNoModifiers()) {
                        if (!resurrectSelectionIfNeeded() && !arrowScroll(130)) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    } else if (keyEvent.hasModifiers(1)) {
                        if (!resurrectSelectionIfNeeded() && !arrowScroll(33)) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    break;
                case 92:
                    if (keyEvent.hasNoModifiers()) {
                        if (!resurrectSelectionIfNeeded() && !pageScroll(33)) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    } else if (keyEvent.hasModifiers(2)) {
                        if (!resurrectSelectionIfNeeded() && !fullScroll(33)) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    break;
                case 93:
                    if (keyEvent.hasNoModifiers()) {
                        if (!resurrectSelectionIfNeeded() && !pageScroll(130)) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    } else if (keyEvent.hasModifiers(2)) {
                        if (!resurrectSelectionIfNeeded() && !fullScroll(130)) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    break;
                case 122:
                    if (keyEvent.hasNoModifiers()) {
                        if (!resurrectSelectionIfNeeded() && !fullScroll(33)) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    break;
                case 123:
                    if (keyEvent.hasNoModifiers()) {
                        if (!resurrectSelectionIfNeeded() && !fullScroll(130)) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    break;
            }
        }
        if (z || sendToTextFilter(i, i2, keyEvent)) {
            return true;
        }
        switch (action) {
        }
        return true;
    }

    boolean pageScroll(int i) {
        int min;
        boolean z;
        int lookForSelectablePositionAfter;
        if (i == 33) {
            min = java.lang.Math.max(0, (this.mSelectedPosition - getChildCount()) - 1);
            z = false;
        } else {
            if (i != 130) {
                return false;
            }
            min = java.lang.Math.min(this.mItemCount - 1, (this.mSelectedPosition + getChildCount()) - 1);
            z = true;
        }
        if (min < 0 || (lookForSelectablePositionAfter = lookForSelectablePositionAfter(this.mSelectedPosition, min, z)) < 0) {
            return false;
        }
        this.mLayoutMode = 4;
        this.mSpecificTop = this.mPaddingTop + getVerticalFadingEdgeLength();
        if (z && lookForSelectablePositionAfter > this.mItemCount - getChildCount()) {
            this.mLayoutMode = 3;
        }
        if (!z && lookForSelectablePositionAfter < getChildCount()) {
            this.mLayoutMode = 1;
        }
        setSelectionInt(lookForSelectablePositionAfter);
        invokeOnItemScrollListener();
        if (!awakenScrollBars()) {
            invalidate();
        }
        return true;
    }

    boolean fullScroll(int i) {
        int i2;
        boolean z = true;
        if (i == 33) {
            if (this.mSelectedPosition != 0) {
                int lookForSelectablePositionAfter = lookForSelectablePositionAfter(this.mSelectedPosition, 0, true);
                if (lookForSelectablePositionAfter >= 0) {
                    this.mLayoutMode = 1;
                    setSelectionInt(lookForSelectablePositionAfter);
                    invokeOnItemScrollListener();
                }
            }
            z = false;
        } else {
            if (i == 130 && this.mSelectedPosition < (i2 = this.mItemCount - 1)) {
                int lookForSelectablePositionAfter2 = lookForSelectablePositionAfter(this.mSelectedPosition, i2, false);
                if (lookForSelectablePositionAfter2 >= 0) {
                    this.mLayoutMode = 3;
                    setSelectionInt(lookForSelectablePositionAfter2);
                    invokeOnItemScrollListener();
                }
            }
            z = false;
        }
        if (z && !awakenScrollBars()) {
            awakenScrollBars();
            invalidate();
        }
        return z;
    }

    private boolean handleHorizontalFocusWithinListItem(int i) {
        android.view.View selectedView;
        if (i != 17 && i != 66) {
            throw new java.lang.IllegalArgumentException("direction must be one of {View.FOCUS_LEFT, View.FOCUS_RIGHT}");
        }
        int childCount = getChildCount();
        if (this.mItemsCanFocus && childCount > 0 && this.mSelectedPosition != -1 && (selectedView = getSelectedView()) != null && selectedView.hasFocus() && (selectedView instanceof android.view.ViewGroup)) {
            android.view.View findFocus = selectedView.findFocus();
            android.view.View findNextFocus = android.view.FocusFinder.getInstance().findNextFocus((android.view.ViewGroup) selectedView, findFocus, i);
            if (findNextFocus != null) {
                android.graphics.Rect rect = this.mTempRect;
                if (findFocus != null) {
                    findFocus.getFocusedRect(rect);
                    offsetDescendantRectToMyCoords(findFocus, rect);
                    offsetRectIntoDescendantCoords(findNextFocus, rect);
                } else {
                    rect = null;
                }
                if (findNextFocus.requestFocus(i, rect)) {
                    return true;
                }
            }
            android.view.View findNextFocus2 = android.view.FocusFinder.getInstance().findNextFocus((android.view.ViewGroup) getRootView(), findFocus, i);
            if (findNextFocus2 != null) {
                return isViewAncestorOf(findNextFocus2, this);
            }
            return false;
        }
        return false;
    }

    boolean arrowScroll(int i) {
        try {
            this.mInLayout = true;
            boolean arrowScrollImpl = arrowScrollImpl(i);
            if (arrowScrollImpl) {
                playSoundEffect(android.view.SoundEffectConstants.getContantForFocusDirection(i));
            }
            return arrowScrollImpl;
        } finally {
            this.mInLayout = false;
        }
    }

    private final int nextSelectedPositionForDirection(android.view.View view, int i, int i2) {
        int i3;
        if (i2 == 130) {
            int height = getHeight() - this.mListPadding.bottom;
            if (view == null || view.getBottom() > height) {
                return -1;
            }
            if (i != -1 && i >= this.mFirstPosition) {
                i3 = i + 1;
            } else {
                i3 = this.mFirstPosition;
            }
        } else {
            int i4 = this.mListPadding.top;
            if (view == null || view.getTop() < i4) {
                return -1;
            }
            int childCount = (this.mFirstPosition + getChildCount()) - 1;
            if (i != -1 && i <= childCount) {
                i3 = i - 1;
            } else {
                i3 = childCount;
            }
        }
        if (i3 < 0 || i3 >= this.mAdapter.getCount()) {
            return -1;
        }
        return lookForSelectablePosition(i3, i2 == 130);
    }

    private boolean arrowScrollImpl(int i) {
        android.view.View findFocus;
        android.view.View focusedChild;
        if (getChildCount() <= 0) {
            return false;
        }
        android.view.View selectedView = getSelectedView();
        int i2 = this.mSelectedPosition;
        int nextSelectedPositionForDirection = nextSelectedPositionForDirection(selectedView, i2, i);
        int amountToScroll = amountToScroll(i, nextSelectedPositionForDirection);
        android.view.View view = null;
        android.widget.ListView.ArrowScrollFocusResult arrowScrollFocused = this.mItemsCanFocus ? arrowScrollFocused(i) : null;
        if (arrowScrollFocused != null) {
            nextSelectedPositionForDirection = arrowScrollFocused.getSelectedPosition();
            amountToScroll = arrowScrollFocused.getAmountToScroll();
        }
        boolean z = arrowScrollFocused != null;
        if (nextSelectedPositionForDirection != -1) {
            handleNewSelectionChange(selectedView, i, nextSelectedPositionForDirection, arrowScrollFocused != null);
            setSelectedPositionInt(nextSelectedPositionForDirection);
            setNextSelectedPositionInt(nextSelectedPositionForDirection);
            selectedView = getSelectedView();
            if (this.mItemsCanFocus && arrowScrollFocused == null && (focusedChild = getFocusedChild()) != null) {
                focusedChild.clearFocus();
            }
            checkSelectionChanged();
            i2 = nextSelectedPositionForDirection;
            z = true;
        }
        if (amountToScroll > 0) {
            if (i != 33) {
                amountToScroll = -amountToScroll;
            }
            scrollListItemsBy(amountToScroll);
            z = true;
        }
        if (this.mItemsCanFocus && arrowScrollFocused == null && selectedView != null && selectedView.hasFocus() && (findFocus = selectedView.findFocus()) != null && (!isViewAncestorOf(findFocus, this) || distanceToView(findFocus) > 0)) {
            findFocus.clearFocus();
        }
        if (nextSelectedPositionForDirection == -1 && selectedView != null && !isViewAncestorOf(selectedView, this)) {
            hideSelector();
            this.mResurrectToPosition = -1;
        } else {
            view = selectedView;
        }
        if (!z) {
            return false;
        }
        if (view != null) {
            positionSelectorLikeFocus(i2, view);
            this.mSelectedTop = view.getTop();
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        invokeOnItemScrollListener();
        return true;
    }

    private void handleNewSelectionChange(android.view.View view, int i, int i2, boolean z) {
        android.view.View childAt;
        boolean z2;
        if (i2 == -1) {
            throw new java.lang.IllegalArgumentException("newSelectedPosition needs to be valid");
        }
        int i3 = this.mSelectedPosition - this.mFirstPosition;
        int i4 = i2 - this.mFirstPosition;
        if (i == 33) {
            z2 = true;
            childAt = view;
            view = getChildAt(i4);
            i3 = i4;
            i4 = i3;
        } else {
            childAt = getChildAt(i4);
            z2 = false;
        }
        int childCount = getChildCount();
        if (view != null) {
            view.setSelected(!z && z2);
            measureAndAdjustDown(view, i3, childCount);
        }
        if (childAt != null) {
            childAt.setSelected((z || z2) ? false : true);
            measureAndAdjustDown(childAt, i4, childCount);
        }
    }

    private void measureAndAdjustDown(android.view.View view, int i, int i2) {
        int height = view.getHeight();
        measureItem(view);
        if (view.getMeasuredHeight() != height) {
            relayoutMeasuredItem(view);
            int measuredHeight = view.getMeasuredHeight() - height;
            while (true) {
                i++;
                if (i < i2) {
                    getChildAt(i).offsetTopAndBottom(measuredHeight);
                } else {
                    return;
                }
            }
        }
    }

    private void measureItem(android.view.View view) {
        int makeSafeMeasureSpec;
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new android.view.ViewGroup.LayoutParams(-1, -2);
        }
        int childMeasureSpec = android.view.ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mListPadding.left + this.mListPadding.right, layoutParams.width);
        int i = layoutParams.height;
        if (i > 0) {
            makeSafeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(i, 1073741824);
        } else {
            makeSafeMeasureSpec = android.view.View.MeasureSpec.makeSafeMeasureSpec(getMeasuredHeight(), 0);
        }
        view.measure(childMeasureSpec, makeSafeMeasureSpec);
    }

    private void relayoutMeasuredItem(android.view.View view) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i = this.mListPadding.left;
        int top = view.getTop();
        view.layout(i, top, measuredWidth + i, measuredHeight + top);
    }

    private int getArrowScrollPreviewLength() {
        return java.lang.Math.max(2, getVerticalFadingEdgeLength());
    }

    private int amountToScroll(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int height = getHeight() - this.mListPadding.bottom;
        int i6 = this.mListPadding.top;
        int childCount = getChildCount();
        if (i == 130) {
            int i7 = childCount - 1;
            if (i2 != -1) {
                i7 = i2 - this.mFirstPosition;
            }
            while (childCount <= i7) {
                addViewBelow(getChildAt(childCount - 1), (this.mFirstPosition + childCount) - 1);
                childCount++;
            }
            int i8 = this.mFirstPosition + i7;
            android.view.View childAt = getChildAt(i7);
            if (i8 >= this.mItemCount - 1) {
                i5 = height;
            } else {
                i5 = height - getArrowScrollPreviewLength();
            }
            if (childAt.getBottom() <= i5) {
                return 0;
            }
            if (i2 != -1 && i5 - childAt.getTop() >= getMaxScrollAmount()) {
                return 0;
            }
            int bottom = childAt.getBottom() - i5;
            if (this.mFirstPosition + childCount == this.mItemCount) {
                bottom = java.lang.Math.min(bottom, getChildAt(childCount - 1).getBottom() - height);
            }
            return java.lang.Math.min(bottom, getMaxScrollAmount());
        }
        if (i2 == -1) {
            i3 = 0;
        } else {
            i3 = i2 - this.mFirstPosition;
        }
        while (i3 < 0) {
            addViewAbove(getChildAt(0), this.mFirstPosition);
            this.mFirstPosition--;
            i3 = i2 - this.mFirstPosition;
        }
        int i9 = this.mFirstPosition + i3;
        android.view.View childAt2 = getChildAt(i3);
        if (i9 <= 0) {
            i4 = i6;
        } else {
            i4 = getArrowScrollPreviewLength() + i6;
        }
        if (childAt2.getTop() >= i4) {
            return 0;
        }
        if (i2 != -1 && childAt2.getBottom() - i4 >= getMaxScrollAmount()) {
            return 0;
        }
        int top = i4 - childAt2.getTop();
        if (this.mFirstPosition == 0) {
            top = java.lang.Math.min(top, i6 - getChildAt(0).getTop());
        }
        return java.lang.Math.min(top, getMaxScrollAmount());
    }

    private static class ArrowScrollFocusResult {
        private int mAmountToScroll;
        private int mSelectedPosition;

        private ArrowScrollFocusResult() {
        }

        void populate(int i, int i2) {
            this.mSelectedPosition = i;
            this.mAmountToScroll = i2;
        }

        public int getSelectedPosition() {
            return this.mSelectedPosition;
        }

        public int getAmountToScroll() {
            return this.mAmountToScroll;
        }
    }

    private int lookForSelectablePositionOnScreen(int i) {
        int childCount;
        int i2;
        int i3 = this.mFirstPosition;
        if (i == 130) {
            if (this.mSelectedPosition != -1) {
                i2 = this.mSelectedPosition + 1;
            } else {
                i2 = i3;
            }
            if (i2 >= this.mAdapter.getCount()) {
                return -1;
            }
            if (i2 < i3) {
                i2 = i3;
            }
            int lastVisiblePosition = getLastVisiblePosition();
            android.widget.ListAdapter adapter = getAdapter();
            while (i2 <= lastVisiblePosition) {
                if (!adapter.isEnabled(i2) || getChildAt(i2 - i3).getVisibility() != 0) {
                    i2++;
                } else {
                    return i2;
                }
            }
        } else {
            int childCount2 = (getChildCount() + i3) - 1;
            if (this.mSelectedPosition != -1) {
                childCount = this.mSelectedPosition - 1;
            } else {
                childCount = (getChildCount() + i3) - 1;
            }
            if (childCount < 0 || childCount >= this.mAdapter.getCount()) {
                return -1;
            }
            if (childCount <= childCount2) {
                childCount2 = childCount;
            }
            android.widget.ListAdapter adapter2 = getAdapter();
            while (childCount2 >= i3) {
                if (!adapter2.isEnabled(childCount2) || getChildAt(childCount2 - i3).getVisibility() != 0) {
                    childCount2--;
                } else {
                    return childCount2;
                }
            }
        }
        return -1;
    }

    private android.widget.ListView.ArrowScrollFocusResult arrowScrollFocused(int i) {
        android.view.View findNextFocusFromRect;
        int lookForSelectablePositionOnScreen;
        android.view.View selectedView = getSelectedView();
        if (selectedView == null || !selectedView.hasFocus()) {
            if (i != 130) {
                int height = (getHeight() - this.mListPadding.bottom) - ((this.mFirstPosition + getChildCount()) - 1 < this.mItemCount ? getArrowScrollPreviewLength() : 0);
                if (selectedView != null && selectedView.getBottom() < height) {
                    height = selectedView.getBottom();
                }
                this.mTempRect.set(0, height, 0, height);
            } else {
                int arrowScrollPreviewLength = this.mListPadding.top + (this.mFirstPosition > 0 ? getArrowScrollPreviewLength() : 0);
                if (selectedView != null && selectedView.getTop() > arrowScrollPreviewLength) {
                    arrowScrollPreviewLength = selectedView.getTop();
                }
                this.mTempRect.set(0, arrowScrollPreviewLength, 0, arrowScrollPreviewLength);
            }
            findNextFocusFromRect = android.view.FocusFinder.getInstance().findNextFocusFromRect(this, this.mTempRect, i);
        } else {
            findNextFocusFromRect = android.view.FocusFinder.getInstance().findNextFocus(this, selectedView.findFocus(), i);
        }
        if (findNextFocusFromRect != null) {
            int positionOfNewFocus = positionOfNewFocus(findNextFocusFromRect);
            if (this.mSelectedPosition != -1 && positionOfNewFocus != this.mSelectedPosition && (lookForSelectablePositionOnScreen = lookForSelectablePositionOnScreen(i)) != -1 && ((i == 130 && lookForSelectablePositionOnScreen < positionOfNewFocus) || (i == 33 && lookForSelectablePositionOnScreen > positionOfNewFocus))) {
                return null;
            }
            int amountToScrollToNewFocus = amountToScrollToNewFocus(i, findNextFocusFromRect, positionOfNewFocus);
            int maxScrollAmount = getMaxScrollAmount();
            if (amountToScrollToNewFocus < maxScrollAmount) {
                findNextFocusFromRect.requestFocus(i);
                this.mArrowScrollFocusResult.populate(positionOfNewFocus, amountToScrollToNewFocus);
                return this.mArrowScrollFocusResult;
            }
            if (distanceToView(findNextFocusFromRect) < maxScrollAmount) {
                findNextFocusFromRect.requestFocus(i);
                this.mArrowScrollFocusResult.populate(positionOfNewFocus, maxScrollAmount);
                return this.mArrowScrollFocusResult;
            }
        }
        return null;
    }

    private int positionOfNewFocus(android.view.View view) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (isViewAncestorOf(view, getChildAt(i))) {
                return this.mFirstPosition + i;
            }
        }
        throw new java.lang.IllegalArgumentException("newFocus is not a child of any of the children of the list!");
    }

    private boolean isViewAncestorOf(android.view.View view, android.view.View view2) {
        if (view == view2) {
            return true;
        }
        java.lang.Object parent = view.getParent();
        return (parent instanceof android.view.ViewGroup) && isViewAncestorOf((android.view.View) parent, view2);
    }

    private int amountToScrollToNewFocus(int i, android.view.View view, int i2) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        if (i == 33) {
            if (this.mTempRect.top < this.mListPadding.top) {
                int i3 = this.mListPadding.top - this.mTempRect.top;
                return i2 > 0 ? i3 + getArrowScrollPreviewLength() : i3;
            }
        } else {
            int height = getHeight() - this.mListPadding.bottom;
            if (this.mTempRect.bottom > height) {
                int i4 = this.mTempRect.bottom - height;
                return i2 < this.mItemCount + (-1) ? i4 + getArrowScrollPreviewLength() : i4;
            }
        }
        return 0;
    }

    private int distanceToView(android.view.View view) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        int i = (this.mBottom - this.mTop) - this.mListPadding.bottom;
        if (this.mTempRect.bottom < this.mListPadding.top) {
            return this.mListPadding.top - this.mTempRect.bottom;
        }
        if (this.mTempRect.top <= i) {
            return 0;
        }
        return this.mTempRect.top - i;
    }

    private void scrollListItemsBy(int i) {
        int i2;
        int i3 = this.mScrollX;
        int i4 = this.mScrollY;
        offsetChildrenTopAndBottom(i);
        int height = getHeight() - this.mListPadding.bottom;
        int i5 = this.mListPadding.top;
        android.widget.AbsListView.RecycleBin recycleBin = this.mRecycler;
        if (i < 0) {
            int childCount = getChildCount();
            android.view.View childAt = getChildAt(childCount - 1);
            while (childAt.getBottom() < height && (this.mFirstPosition + childCount) - 1 < this.mItemCount - 1) {
                childAt = addViewBelow(childAt, i2);
                childCount++;
            }
            if (childAt.getBottom() < height) {
                offsetChildrenTopAndBottom(height - childAt.getBottom());
            }
            android.view.View childAt2 = getChildAt(0);
            while (childAt2.getBottom() < i5) {
                if (recycleBin.shouldRecycleViewType(((android.widget.AbsListView.LayoutParams) childAt2.getLayoutParams()).viewType)) {
                    recycleBin.addScrapView(childAt2, this.mFirstPosition);
                }
                detachViewFromParent(childAt2);
                childAt2 = getChildAt(0);
                this.mFirstPosition++;
            }
        } else {
            android.view.View childAt3 = getChildAt(0);
            while (childAt3.getTop() > i5 && this.mFirstPosition > 0) {
                childAt3 = addViewAbove(childAt3, this.mFirstPosition);
                this.mFirstPosition--;
            }
            if (childAt3.getTop() > i5) {
                offsetChildrenTopAndBottom(i5 - childAt3.getTop());
            }
            int childCount2 = getChildCount() - 1;
            android.view.View childAt4 = getChildAt(childCount2);
            while (childAt4.getTop() > height) {
                if (recycleBin.shouldRecycleViewType(((android.widget.AbsListView.LayoutParams) childAt4.getLayoutParams()).viewType)) {
                    recycleBin.addScrapView(childAt4, this.mFirstPosition + childCount2);
                }
                detachViewFromParent(childAt4);
                childCount2--;
                childAt4 = getChildAt(childCount2);
            }
        }
        recycleBin.fullyDetachScrapViews();
        removeUnusedFixedViews(this.mHeaderViewInfos);
        removeUnusedFixedViews(this.mFooterViewInfos);
        onScrollChanged(this.mScrollX, this.mScrollY, i3, i4);
    }

    private android.view.View addViewAbove(android.view.View view, int i) {
        int i2 = i - 1;
        android.view.View obtainView = obtainView(i2, this.mIsScrap);
        setupChild(obtainView, i2, view.getTop() - this.mDividerHeight, false, this.mListPadding.left, false, this.mIsScrap[0]);
        return obtainView;
    }

    private android.view.View addViewBelow(android.view.View view, int i) {
        int i2 = i + 1;
        android.view.View obtainView = obtainView(i2, this.mIsScrap);
        setupChild(obtainView, i2, view.getBottom() + this.mDividerHeight, true, this.mListPadding.left, false, this.mIsScrap[0]);
        return obtainView;
    }

    public void setItemsCanFocus(boolean z) {
        this.mItemsCanFocus = z;
        if (!z) {
            setDescendantFocusability(393216);
        }
    }

    public boolean getItemsCanFocus() {
        return this.mItemsCanFocus;
    }

    @Override // android.view.View
    public boolean isOpaque() {
        boolean z = (this.mCachingActive && this.mIsCacheColorOpaque && this.mDividerIsOpaque && hasOpaqueScrollbars()) || super.isOpaque();
        if (z) {
            int i = this.mListPadding != null ? this.mListPadding.top : this.mPaddingTop;
            android.view.View childAt = getChildAt(0);
            if (childAt == null || childAt.getTop() > i) {
                return false;
            }
            int height = getHeight() - (this.mListPadding != null ? this.mListPadding.bottom : this.mPaddingBottom);
            android.view.View childAt2 = getChildAt(getChildCount() - 1);
            if (childAt2 == null || childAt2.getBottom() < height) {
                return false;
            }
        }
        return z;
    }

    @Override // android.widget.AbsListView
    public void setCacheColorHint(int i) {
        boolean z = (i >>> 24) == 255;
        this.mIsCacheColorOpaque = z;
        if (z) {
            if (this.mDividerPaint == null) {
                this.mDividerPaint = new android.graphics.Paint();
            }
            this.mDividerPaint.setColor(i);
        }
        super.setCacheColorHint(i);
    }

    void drawOverscrollHeader(android.graphics.Canvas canvas, android.graphics.drawable.Drawable drawable, android.graphics.Rect rect) {
        int minimumHeight = drawable.getMinimumHeight();
        canvas.save();
        canvas.clipRect(rect);
        if (rect.bottom - rect.top < minimumHeight) {
            rect.top = rect.bottom - minimumHeight;
        }
        drawable.setBounds(rect);
        drawable.draw(canvas);
        canvas.restore();
    }

    void drawOverscrollFooter(android.graphics.Canvas canvas, android.graphics.drawable.Drawable drawable, android.graphics.Rect rect) {
        int minimumHeight = drawable.getMinimumHeight();
        canvas.save();
        canvas.clipRect(rect);
        if (rect.bottom - rect.top < minimumHeight) {
            rect.bottom = rect.top + minimumHeight;
        }
        drawable.setBounds(rect);
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void dispatchDraw(android.graphics.Canvas canvas) {
        android.widget.ListAdapter listAdapter;
        int i;
        int i2;
        int i3;
        android.graphics.drawable.Drawable drawable;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z;
        android.widget.ListAdapter listAdapter2;
        android.graphics.Paint paint;
        if (this.mCachingStarted) {
            this.mCachingActive = true;
        }
        int i9 = this.mDividerHeight;
        android.graphics.drawable.Drawable drawable2 = this.mOverScrollHeader;
        android.graphics.drawable.Drawable drawable3 = this.mOverScrollFooter;
        int i10 = drawable2 != null ? 1 : 0;
        boolean z2 = drawable3 != null;
        boolean z3 = i9 > 0 && this.mDivider != null;
        if (z3 || i10 != 0 || z2) {
            android.graphics.Rect rect = this.mTempRect;
            rect.left = this.mPaddingLeft;
            rect.right = (this.mRight - this.mLeft) - this.mPaddingRight;
            int childCount = getChildCount();
            int headerViewsCount = getHeaderViewsCount();
            int i11 = this.mItemCount;
            int size = i11 - this.mFooterViewInfos.size();
            boolean z4 = this.mHeaderDividersEnabled;
            boolean z5 = this.mFooterDividersEnabled;
            int i12 = this.mFirstPosition;
            android.widget.ListAdapter listAdapter3 = this.mAdapter;
            boolean z6 = isOpaque() && !super.isOpaque();
            if (z6) {
                i = i11;
                if (this.mDividerPaint == null && this.mIsCacheColorOpaque) {
                    this.mDividerPaint = new android.graphics.Paint();
                    listAdapter = listAdapter3;
                    this.mDividerPaint.setColor(getCacheColorHint());
                } else {
                    listAdapter = listAdapter3;
                }
            } else {
                listAdapter = listAdapter3;
                i = i11;
            }
            android.graphics.Paint paint2 = this.mDividerPaint;
            if ((this.mGroupFlags & 34) != 34) {
                i2 = 0;
                i3 = 0;
            } else {
                i2 = this.mListPadding.top;
                i3 = this.mListPadding.bottom;
            }
            int i13 = i2;
            boolean z7 = z2;
            int i14 = ((this.mBottom - this.mTop) - i3) + this.mScrollY;
            if (this.mStackFromBottom) {
                boolean z8 = z3;
                android.graphics.drawable.Drawable drawable4 = drawable3;
                android.widget.ListAdapter listAdapter4 = listAdapter;
                int i15 = this.mScrollY;
                if (childCount > 0 && i10 != 0) {
                    rect.top = i15;
                    rect.bottom = getChildAt(0).getTop();
                    drawOverscrollHeader(canvas, drawable2, rect);
                }
                int i16 = i10;
                while (i16 < childCount) {
                    int i17 = i12 + i16;
                    boolean z9 = i17 < headerViewsCount;
                    boolean z10 = i17 >= size;
                    if ((z4 || !z9) && (z5 || !z10)) {
                        drawable = drawable4;
                        int top = getChildAt(i16).getTop();
                        if (z8) {
                            i5 = i15;
                            i6 = i13;
                            if (top <= i6) {
                                i4 = i10;
                            } else {
                                boolean z11 = i16 == i10;
                                i4 = i10;
                                int i18 = i17 - 1;
                                if (listAdapter4.isEnabled(i17) && ((z4 || (!z9 && i18 >= headerViewsCount)) && (z11 || (listAdapter4.isEnabled(i18) && (z5 || (!z10 && i18 < size)))))) {
                                    rect.top = top - i9;
                                    rect.bottom = top;
                                    drawDivider(canvas, rect, i16 - 1);
                                } else if (z6) {
                                    rect.top = top - i9;
                                    rect.bottom = top;
                                    canvas.drawRect(rect, paint2);
                                }
                            }
                        } else {
                            i4 = i10;
                            i5 = i15;
                            i6 = i13;
                        }
                    } else {
                        drawable = drawable4;
                        i4 = i10;
                        i5 = i15;
                        i6 = i13;
                    }
                    i16++;
                    i13 = i6;
                    i15 = i5;
                    drawable4 = drawable;
                    i10 = i4;
                }
                android.graphics.drawable.Drawable drawable5 = drawable4;
                int i19 = i15;
                if (childCount > 0 && i19 > 0) {
                    if (z7) {
                        int i20 = this.mBottom;
                        rect.top = i20;
                        rect.bottom = i20 + i19;
                        drawOverscrollFooter(canvas, drawable5, rect);
                    } else if (z8) {
                        rect.top = i14;
                        rect.bottom = i14 + i9;
                        drawDivider(canvas, rect, -1);
                    }
                }
            } else {
                int i21 = this.mScrollY;
                if (childCount > 0 && i21 < 0) {
                    if (i10 != 0) {
                        rect.bottom = 0;
                        rect.top = i21;
                        drawOverscrollHeader(canvas, drawable2, rect);
                    } else if (z3) {
                        rect.bottom = 0;
                        rect.top = -i9;
                        drawDivider(canvas, rect, -1);
                    }
                }
                int i22 = 0;
                int i23 = 0;
                while (i23 < childCount) {
                    int i24 = i12 + i23;
                    boolean z12 = i24 < headerViewsCount;
                    boolean z13 = i24 >= size;
                    if ((!z4 && z12) || (!z5 && z13)) {
                        i8 = i14;
                        i7 = i12;
                        z = z3;
                        listAdapter2 = listAdapter;
                        paint = paint2;
                    } else {
                        i22 = getChildAt(i23).getBottom();
                        i7 = i12;
                        boolean z14 = i23 == childCount + (-1);
                        if (!z3 || i22 >= i14) {
                            i8 = i14;
                            z = z3;
                            listAdapter2 = listAdapter;
                            paint = paint2;
                        } else if (z7 && z14) {
                            i8 = i14;
                            z = z3;
                            listAdapter2 = listAdapter;
                            paint = paint2;
                        } else {
                            i8 = i14;
                            int i25 = i24 + 1;
                            z = z3;
                            listAdapter2 = listAdapter;
                            if (listAdapter2.isEnabled(i24) && ((z4 || (!z12 && i25 >= headerViewsCount)) && (z14 || (listAdapter2.isEnabled(i25) && (z5 || (!z13 && i25 < size)))))) {
                                rect.top = i22;
                                rect.bottom = i22 + i9;
                                drawDivider(canvas, rect, i23);
                                paint = paint2;
                            } else if (!z6) {
                                paint = paint2;
                            } else {
                                rect.top = i22;
                                rect.bottom = i22 + i9;
                                paint = paint2;
                                canvas.drawRect(rect, paint);
                            }
                        }
                    }
                    i23++;
                    paint2 = paint;
                    listAdapter = listAdapter2;
                    i12 = i7;
                    i14 = i8;
                    z3 = z;
                }
                int i26 = i12;
                int i27 = this.mBottom + this.mScrollY;
                if (z7 && i26 + childCount == i && i27 > i22) {
                    rect.top = i22;
                    rect.bottom = i27;
                    drawOverscrollFooter(canvas, drawable3, rect);
                }
            }
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(android.graphics.Canvas canvas, android.view.View view, long j) {
        boolean drawChild = super.drawChild(canvas, view, j);
        if (this.mCachingActive && view.mCachingFailed) {
            this.mCachingActive = false;
        }
        return drawChild;
    }

    void drawDivider(android.graphics.Canvas canvas, android.graphics.Rect rect, int i) {
        android.graphics.drawable.Drawable drawable = this.mDivider;
        drawable.setBounds(rect);
        drawable.draw(canvas);
    }

    public android.graphics.drawable.Drawable getDivider() {
        return this.mDivider;
    }

    public void setDivider(android.graphics.drawable.Drawable drawable) {
        if (drawable != null) {
            this.mDividerHeight = drawable.getIntrinsicHeight();
        } else {
            this.mDividerHeight = 0;
        }
        this.mDivider = drawable;
        this.mDividerIsOpaque = drawable == null || drawable.getOpacity() == -1;
        requestLayout();
        invalidate();
    }

    public int getDividerHeight() {
        return this.mDividerHeight;
    }

    public void setDividerHeight(int i) {
        this.mDividerHeight = i;
        requestLayout();
        invalidate();
    }

    public void setHeaderDividersEnabled(boolean z) {
        this.mHeaderDividersEnabled = z;
        invalidate();
    }

    public boolean areHeaderDividersEnabled() {
        return this.mHeaderDividersEnabled;
    }

    public void setFooterDividersEnabled(boolean z) {
        this.mFooterDividersEnabled = z;
        invalidate();
    }

    public boolean areFooterDividersEnabled() {
        return this.mFooterDividersEnabled;
    }

    public void setOverscrollHeader(android.graphics.drawable.Drawable drawable) {
        this.mOverScrollHeader = drawable;
        if (this.mScrollY < 0) {
            invalidate();
        }
    }

    public android.graphics.drawable.Drawable getOverscrollHeader() {
        return this.mOverScrollHeader;
    }

    public void setOverscrollFooter(android.graphics.drawable.Drawable drawable) {
        this.mOverScrollFooter = drawable;
        invalidate();
    }

    public android.graphics.drawable.Drawable getOverscrollFooter() {
        return this.mOverScrollFooter;
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
        super.onFocusChanged(z, i, rect);
        android.widget.ListAdapter listAdapter = this.mAdapter;
        int i2 = -1;
        int i3 = 0;
        if (listAdapter != null && z && rect != null) {
            rect.offset(this.mScrollX, this.mScrollY);
            if (listAdapter.getCount() < getChildCount() + this.mFirstPosition) {
                this.mLayoutMode = 0;
                layoutChildren();
            }
            android.graphics.Rect rect2 = this.mTempRect;
            int childCount = getChildCount();
            int i4 = this.mFirstPosition;
            int i5 = Integer.MAX_VALUE;
            int i6 = 0;
            while (i3 < childCount) {
                if (listAdapter.isEnabled(i4 + i3)) {
                    android.view.View childAt = getChildAt(i3);
                    childAt.getDrawingRect(rect2);
                    offsetDescendantRectToMyCoords(childAt, rect2);
                    int distance = getDistance(rect, rect2, i);
                    if (distance < i5) {
                        i6 = childAt.getTop();
                        i2 = i3;
                        i5 = distance;
                    }
                }
                i3++;
            }
            i3 = i6;
        }
        if (i2 >= 0) {
            setSelectionFromTop(i2 + this.mFirstPosition, i3);
        } else {
            requestLayout();
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                addHeaderView(getChildAt(i));
            }
            removeAllViews();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected <T extends android.view.View> T findViewTraversal(int i) {
        T t = (T) super.findViewTraversal(i);
        if (t == null) {
            T t2 = (T) findViewInHeadersOrFooters(this.mHeaderViewInfos, i);
            if (t2 != null) {
                return t2;
            }
            t = (T) findViewInHeadersOrFooters(this.mFooterViewInfos, i);
            if (t != null) {
                return t;
            }
        }
        return t;
    }

    android.view.View findViewInHeadersOrFooters(java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList, int i) {
        android.view.View findViewById;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                android.view.View view = arrayList.get(i2).view;
                if (!view.isRootNamespace() && (findViewById = view.findViewById(i)) != null) {
                    return findViewById;
                }
            }
            return null;
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected <T extends android.view.View> T findViewWithTagTraversal(java.lang.Object obj) {
        T t = (T) super.findViewWithTagTraversal(obj);
        if (t == null) {
            T t2 = (T) findViewWithTagInHeadersOrFooters(this.mHeaderViewInfos, obj);
            if (t2 != null) {
                return t2;
            }
            t = (T) findViewWithTagInHeadersOrFooters(this.mFooterViewInfos, obj);
            if (t != null) {
                return t;
            }
        }
        return t;
    }

    android.view.View findViewWithTagInHeadersOrFooters(java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList, java.lang.Object obj) {
        android.view.View findViewWithTag;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                android.view.View view = arrayList.get(i).view;
                if (!view.isRootNamespace() && (findViewWithTag = view.findViewWithTag(obj)) != null) {
                    return findViewWithTag;
                }
            }
            return null;
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected <T extends android.view.View> T findViewByPredicateTraversal(java.util.function.Predicate<android.view.View> predicate, android.view.View view) {
        T t = (T) super.findViewByPredicateTraversal(predicate, view);
        if (t == null) {
            T t2 = (T) findViewByPredicateInHeadersOrFooters(this.mHeaderViewInfos, predicate, view);
            if (t2 != null) {
                return t2;
            }
            t = (T) findViewByPredicateInHeadersOrFooters(this.mFooterViewInfos, predicate, view);
            if (t != null) {
                return t;
            }
        }
        return t;
    }

    android.view.View findViewByPredicateInHeadersOrFooters(java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList, java.util.function.Predicate<android.view.View> predicate, android.view.View view) {
        android.view.View findViewByPredicate;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                android.view.View view2 = arrayList.get(i).view;
                if (view2 != view && !view2.isRootNamespace() && (findViewByPredicate = view2.findViewByPredicate(predicate)) != null) {
                    return findViewByPredicate;
                }
            }
            return null;
        }
        return null;
    }

    @java.lang.Deprecated
    public long[] getCheckItemIds() {
        if (this.mAdapter != null && this.mAdapter.hasStableIds()) {
            return getCheckedItemIds();
        }
        if (this.mChoiceMode != 0 && this.mCheckStates != null && this.mAdapter != null) {
            android.util.SparseBooleanArray sparseBooleanArray = this.mCheckStates;
            int size = sparseBooleanArray.size();
            long[] jArr = new long[size];
            android.widget.ListAdapter listAdapter = this.mAdapter;
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                if (sparseBooleanArray.valueAt(i2)) {
                    jArr[i] = listAdapter.getItemId(sparseBooleanArray.keyAt(i2));
                    i++;
                }
            }
            if (i == size) {
                return jArr;
            }
            long[] jArr2 = new long[i];
            java.lang.System.arraycopy(jArr, 0, jArr2, 0, i);
            return jArr2;
        }
        return new long[0];
    }

    @Override // android.widget.AbsListView
    int getHeightForPosition(int i) {
        int heightForPosition = super.getHeightForPosition(i);
        if (shouldAdjustHeightForDivider(i)) {
            return heightForPosition + this.mDividerHeight;
        }
        return heightForPosition;
    }

    private boolean shouldAdjustHeightForDivider(int i) {
        int i2 = this.mDividerHeight;
        android.graphics.drawable.Drawable drawable = this.mOverScrollHeader;
        android.graphics.drawable.Drawable drawable2 = this.mOverScrollFooter;
        int i3 = drawable != null ? 1 : 0;
        boolean z = drawable2 != null;
        if (i2 > 0 && this.mDivider != null) {
            boolean z2 = isOpaque() && !super.isOpaque();
            int i4 = this.mItemCount;
            int headerViewsCount = getHeaderViewsCount();
            int size = i4 - this.mFooterViewInfos.size();
            boolean z3 = i < headerViewsCount;
            boolean z4 = i >= size;
            boolean z5 = this.mHeaderDividersEnabled;
            boolean z6 = this.mFooterDividersEnabled;
            if ((z5 || !z3) && (z6 || !z4)) {
                android.widget.ListAdapter listAdapter = this.mAdapter;
                if (!this.mStackFromBottom) {
                    boolean z7 = i == i4 - 1;
                    if (!z || !z7) {
                        int i5 = i + 1;
                        if ((listAdapter.isEnabled(i) && ((z5 || (!z3 && i5 >= headerViewsCount)) && (z7 || (listAdapter.isEnabled(i5) && (z6 || (!z4 && i5 < size)))))) || z2) {
                            return true;
                        }
                    }
                } else {
                    boolean z8 = i == i3;
                    if (!z8) {
                        int i6 = i - 1;
                        if ((listAdapter.isEnabled(i) && ((z5 || (!z3 && i6 >= headerViewsCount)) && (z8 || (listAdapter.isEnabled(i6) && (z6 || (!z4 && i6 < size)))))) || z2) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.ListView.class.getName();
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        int count = getCount();
        accessibilityNodeInfo.setCollectionInfo(android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain(-1, -1, false, getSelectionModeForAccessibility()));
        if (count > 0) {
            accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION);
        }
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean performAccessibilityActionInternal(int i, android.os.Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        switch (i) {
            case 16908343:
                int i2 = bundle.getInt(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_ROW_INT, -1);
                int min = java.lang.Math.min(i2, getCount() - 1);
                if (i2 >= 0) {
                    smoothScrollToPosition(min);
                    break;
                }
                break;
        }
        return true;
    }

    @Override // android.widget.AbsListView
    public void onInitializeAccessibilityNodeInfoForItem(android.view.View view, int i, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoForItem(view, i, accessibilityNodeInfo);
        android.widget.AbsListView.LayoutParams layoutParams = (android.widget.AbsListView.LayoutParams) view.getLayoutParams();
        accessibilityNodeInfo.setCollectionItemInfo(android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain(i, 1, 0, 1, layoutParams != null && layoutParams.viewType == -2, isItemChecked(i)));
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("recycleOnMeasure", recycleOnMeasure());
    }

    protected android.widget.HeaderViewListAdapter wrapHeaderListAdapterInternal(java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList, java.util.ArrayList<android.widget.ListView.FixedViewInfo> arrayList2, android.widget.ListAdapter listAdapter) {
        return new android.widget.HeaderViewListAdapter(arrayList, arrayList2, listAdapter);
    }

    protected void wrapHeaderListAdapterInternal() {
        this.mAdapter = wrapHeaderListAdapterInternal(this.mHeaderViewInfos, this.mFooterViewInfos, this.mAdapter);
    }

    protected void dispatchDataSetObserverOnChangedInternal() {
        if (this.mDataSetObserver != null) {
            this.mDataSetObserver.onChanged();
        }
    }
}
