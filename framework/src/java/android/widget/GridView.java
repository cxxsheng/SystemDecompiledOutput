package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class GridView extends android.widget.AbsListView {
    public static final int AUTO_FIT = -1;
    public static final int NO_STRETCH = 0;
    public static final int STRETCH_COLUMN_WIDTH = 2;
    public static final int STRETCH_SPACING = 1;
    public static final int STRETCH_SPACING_UNIFORM = 3;
    private int mColumnWidth;
    private int mGravity;
    private int mHorizontalSpacing;
    private int mNumColumns;
    private android.view.View mReferenceView;
    private android.view.View mReferenceViewInSelectedRow;
    private int mRequestedColumnWidth;
    private int mRequestedHorizontalSpacing;
    private int mRequestedNumColumns;
    private int mStretchMode;
    private final android.graphics.Rect mTempRect;
    private int mVerticalSpacing;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StretchMode {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.GridView> {
        private int mColumnWidthId;
        private int mGravityId;
        private int mHorizontalSpacingId;
        private int mNumColumnsId;
        private boolean mPropertiesMapped = false;
        private int mStretchModeId;
        private int mVerticalSpacingId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mColumnWidthId = propertyMapper.mapInt("columnWidth", 16843031);
            this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
            this.mHorizontalSpacingId = propertyMapper.mapInt("horizontalSpacing", 16843028);
            this.mNumColumnsId = propertyMapper.mapInt("numColumns", 16843032);
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            sparseArray.put(0, "none");
            sparseArray.put(1, "spacingWidth");
            sparseArray.put(2, "columnWidth");
            sparseArray.put(3, "spacingWidthUniform");
            java.util.Objects.requireNonNull(sparseArray);
            this.mStretchModeId = propertyMapper.mapIntEnum("stretchMode", 16843030, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mVerticalSpacingId = propertyMapper.mapInt("verticalSpacing", 16843029);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.GridView gridView, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readInt(this.mColumnWidthId, gridView.getColumnWidth());
            propertyReader.readGravity(this.mGravityId, gridView.getGravity());
            propertyReader.readInt(this.mHorizontalSpacingId, gridView.getHorizontalSpacing());
            propertyReader.readInt(this.mNumColumnsId, gridView.getNumColumns());
            propertyReader.readIntEnum(this.mStretchModeId, gridView.getStretchMode());
            propertyReader.readInt(this.mVerticalSpacingId, gridView.getVerticalSpacing());
        }
    }

    public GridView(android.content.Context context) {
        this(context, null);
    }

    public GridView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842865);
    }

    public GridView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public GridView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mNumColumns = -1;
        this.mHorizontalSpacing = 0;
        this.mVerticalSpacing = 0;
        this.mStretchMode = 2;
        this.mReferenceView = null;
        this.mReferenceViewInSelectedRow = null;
        this.mGravity = android.view.Gravity.START;
        this.mTempRect = new android.graphics.Rect();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.GridView, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.GridView, attributeSet, obtainStyledAttributes, i, i2);
        setHorizontalSpacing(obtainStyledAttributes.getDimensionPixelOffset(1, 0));
        setVerticalSpacing(obtainStyledAttributes.getDimensionPixelOffset(2, 0));
        int i3 = obtainStyledAttributes.getInt(3, 2);
        if (i3 >= 0) {
            setStretchMode(i3);
        }
        int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(4, -1);
        if (dimensionPixelOffset > 0) {
            setColumnWidth(dimensionPixelOffset);
        }
        setNumColumns(obtainStyledAttributes.getInt(5, 1));
        int i4 = obtainStyledAttributes.getInt(0, -1);
        if (i4 >= 0) {
            setGravity(i4);
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.widget.AdapterView
    public android.widget.ListAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override // android.widget.AbsListView
    @android.view.RemotableViewMethod(asyncImpl = "setRemoteViewsAdapterAsync")
    public void setRemoteViewsAdapter(android.content.Intent intent) {
        super.setRemoteViewsAdapter(intent);
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView
    public void setAdapter(android.widget.ListAdapter listAdapter) {
        int lookForSelectablePosition;
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        resetList();
        this.mRecycler.clear();
        this.mAdapter = listAdapter;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        super.setAdapter(listAdapter);
        if (this.mAdapter != null) {
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            this.mDataChanged = true;
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
            checkSelectionChanged();
        } else {
            checkFocus();
            checkSelectionChanged();
        }
        requestLayout();
    }

    @Override // android.widget.AdapterView
    int lookForSelectablePosition(int i, boolean z) {
        if (this.mAdapter == null || isInTouchMode() || i < 0 || i >= this.mItemCount) {
            return -1;
        }
        return i;
    }

    @Override // android.widget.AbsListView
    void fillGap(boolean z) {
        int i;
        int i2;
        int i3 = this.mNumColumns;
        int i4 = this.mVerticalSpacing;
        int childCount = getChildCount();
        int i5 = 0;
        if (z) {
            if ((this.mGroupFlags & 34) == 34) {
                i5 = getListPaddingTop();
            }
            if (childCount > 0) {
                i5 = getChildAt(childCount - 1).getBottom() + i4;
            }
            int i6 = this.mFirstPosition + childCount;
            if (this.mStackFromBottom) {
                i6 += i3 - 1;
            }
            fillDown(i6, i5);
            correctTooHigh(i3, i4, getChildCount());
            return;
        }
        if ((this.mGroupFlags & 34) != 34) {
            i = 0;
        } else {
            i = getListPaddingBottom();
        }
        int top = childCount > 0 ? getChildAt(0).getTop() - i4 : getHeight() - i;
        int i7 = this.mFirstPosition;
        if (!this.mStackFromBottom) {
            i2 = i7 - i3;
        } else {
            i2 = i7 - 1;
        }
        fillUp(i2, top);
        correctTooLow(i3, i4, getChildCount());
    }

    private android.view.View fillDown(int i, int i2) {
        int i3 = this.mBottom - this.mTop;
        android.view.View view = null;
        if ((this.mGroupFlags & 34) == 34) {
            i3 -= this.mListPadding.bottom;
        }
        while (i2 < i3 && i < this.mItemCount) {
            android.view.View makeRow = makeRow(i, i2, true);
            if (makeRow != null) {
                view = makeRow;
            }
            i2 = this.mReferenceView.getBottom() + this.mVerticalSpacing;
            i += this.mNumColumns;
        }
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return view;
    }

    private android.view.View makeRow(int i, int i2, boolean z) {
        int i3;
        int i4;
        int i5;
        int i6 = this.mColumnWidth;
        int i7 = this.mHorizontalSpacing;
        boolean isLayoutRtl = isLayoutRtl();
        boolean z2 = false;
        if (isLayoutRtl) {
            i3 = ((getWidth() - this.mListPadding.right) - i6) - (this.mStretchMode == 3 ? i7 : 0);
        } else {
            i3 = this.mListPadding.left + (this.mStretchMode == 3 ? i7 : 0);
        }
        if (!this.mStackFromBottom) {
            i5 = i;
            i4 = java.lang.Math.min(i + this.mNumColumns, this.mItemCount);
        } else {
            int i8 = i + 1;
            int max = java.lang.Math.max(0, (i - this.mNumColumns) + 1);
            int i9 = i8 - max;
            if (i9 >= this.mNumColumns) {
                i4 = i8;
                i5 = max;
            } else {
                i3 += (isLayoutRtl ? -1 : 1) * (this.mNumColumns - i9) * (i6 + i7);
                i4 = i8;
                i5 = max;
            }
        }
        boolean shouldShowSelector = shouldShowSelector();
        boolean z3 = touchModeDrawsInPressedState();
        int i10 = this.mSelectedPosition;
        int i11 = isLayoutRtl ? -1 : 1;
        android.view.View view = null;
        android.view.View view2 = null;
        int i12 = i3;
        int i13 = i5;
        while (i13 < i4) {
            boolean z4 = i13 == i10 ? true : z2;
            int i14 = i13;
            int i15 = i10;
            view = makeAndAddView(i13, i2, z, i12, z4, z ? -1 : i13 - i5);
            i12 += i11 * i6;
            if (i14 < i4 - 1) {
                i12 += i11 * i7;
            }
            if (z4 && (shouldShowSelector || z3)) {
                view2 = view;
            }
            i13 = i14 + 1;
            i10 = i15;
            z2 = false;
        }
        this.mReferenceView = view;
        if (view2 != null) {
            this.mReferenceViewInSelectedRow = this.mReferenceView;
        }
        return view2;
    }

    private android.view.View fillUp(int i, int i2) {
        int i3;
        android.view.View view = null;
        if ((this.mGroupFlags & 34) != 34) {
            i3 = 0;
        } else {
            i3 = this.mListPadding.top;
        }
        while (i2 > i3 && i >= 0) {
            android.view.View makeRow = makeRow(i, i2, false);
            if (makeRow != null) {
                view = makeRow;
            }
            i2 = this.mReferenceView.getTop() - this.mVerticalSpacing;
            this.mFirstPosition = i;
            i -= this.mNumColumns;
        }
        if (this.mStackFromBottom) {
            this.mFirstPosition = java.lang.Math.max(0, i + 1);
        }
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return view;
    }

    private android.view.View fillFromTop(int i) {
        this.mFirstPosition = java.lang.Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = java.lang.Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        this.mFirstPosition -= this.mFirstPosition % this.mNumColumns;
        return fillDown(this.mFirstPosition, i);
    }

    private android.view.View fillFromBottom(int i, int i2) {
        int min = (this.mItemCount - 1) - java.lang.Math.min(java.lang.Math.max(i, this.mSelectedPosition), this.mItemCount - 1);
        return fillUp((this.mItemCount - 1) - (min - (min % this.mNumColumns)), i2);
    }

    private android.view.View fillSelection(int i, int i2) {
        int i3;
        int max;
        int reconcileSelectedPosition = reconcileSelectedPosition();
        int i4 = this.mNumColumns;
        int i5 = this.mVerticalSpacing;
        if (!this.mStackFromBottom) {
            max = reconcileSelectedPosition - (reconcileSelectedPosition % i4);
            i3 = -1;
        } else {
            int i6 = (this.mItemCount - 1) - reconcileSelectedPosition;
            i3 = (this.mItemCount - 1) - (i6 - (i6 % i4));
            max = java.lang.Math.max(0, (i3 - i4) + 1);
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        android.view.View makeRow = makeRow(this.mStackFromBottom ? i3 : max, getTopSelectionPixel(i, verticalFadingEdgeLength, max), true);
        this.mFirstPosition = max;
        android.view.View view = this.mReferenceView;
        if (!this.mStackFromBottom) {
            fillDown(max + i4, view.getBottom() + i5);
            pinToBottom(i2);
            fillUp(max - i4, view.getTop() - i5);
            adjustViewsUpOrDown();
        } else {
            offsetChildrenTopAndBottom(getBottomSelectionPixel(i2, verticalFadingEdgeLength, i4, max) - view.getBottom());
            fillUp(max - 1, view.getTop() - i5);
            pinToTop(i);
            fillDown(i3 + i4, view.getBottom() + i5);
            adjustViewsUpOrDown();
        }
        return makeRow;
    }

    private void pinToTop(int i) {
        int top;
        if (this.mFirstPosition == 0 && (top = i - getChildAt(0).getTop()) < 0) {
            offsetChildrenTopAndBottom(top);
        }
    }

    private void pinToBottom(int i) {
        int bottom;
        int childCount = getChildCount();
        if (this.mFirstPosition + childCount == this.mItemCount && (bottom = i - getChildAt(childCount - 1).getBottom()) > 0) {
            offsetChildrenTopAndBottom(bottom);
        }
    }

    @Override // android.widget.AbsListView
    int findMotionRow(int i) {
        int childCount = getChildCount();
        if (childCount > 0) {
            int i2 = this.mNumColumns;
            if (!this.mStackFromBottom) {
                for (int i3 = 0; i3 < childCount; i3 += i2) {
                    if (i <= getChildAt(i3).getBottom()) {
                        return this.mFirstPosition + i3;
                    }
                }
                return -1;
            }
            for (int i4 = childCount - 1; i4 >= 0; i4 -= i2) {
                if (i >= getChildAt(i4).getTop()) {
                    return this.mFirstPosition + i4;
                }
            }
            return -1;
        }
        return -1;
    }

    private android.view.View fillSpecific(int i, int i2) {
        int i3;
        int max;
        android.view.View view;
        android.view.View view2;
        int i4 = this.mNumColumns;
        if (!this.mStackFromBottom) {
            max = i - (i % i4);
            i3 = -1;
        } else {
            int i5 = (this.mItemCount - 1) - i;
            i3 = (this.mItemCount - 1) - (i5 - (i5 % i4));
            max = java.lang.Math.max(0, (i3 - i4) + 1);
        }
        android.view.View makeRow = makeRow(this.mStackFromBottom ? i3 : max, i2, true);
        this.mFirstPosition = max;
        android.view.View view3 = this.mReferenceView;
        if (view3 == null) {
            return null;
        }
        int i6 = this.mVerticalSpacing;
        if (!this.mStackFromBottom) {
            view = fillUp(max - i4, view3.getTop() - i6);
            adjustViewsUpOrDown();
            view2 = fillDown(max + i4, view3.getBottom() + i6);
            int childCount = getChildCount();
            if (childCount > 0) {
                correctTooHigh(i4, i6, childCount);
            }
        } else {
            android.view.View fillDown = fillDown(i3 + i4, view3.getBottom() + i6);
            adjustViewsUpOrDown();
            android.view.View fillUp = fillUp(max - 1, view3.getTop() - i6);
            int childCount2 = getChildCount();
            if (childCount2 > 0) {
                correctTooLow(i4, i6, childCount2);
            }
            view = fillUp;
            view2 = fillDown;
        }
        if (makeRow != null) {
            return makeRow;
        }
        if (view != null) {
            return view;
        }
        return view2;
    }

    private void correctTooHigh(int i, int i2, int i3) {
        if ((this.mFirstPosition + i3) - 1 == this.mItemCount - 1 && i3 > 0) {
            int bottom = ((this.mBottom - this.mTop) - this.mListPadding.bottom) - getChildAt(i3 - 1).getBottom();
            android.view.View childAt = getChildAt(0);
            int top = childAt.getTop();
            if (bottom > 0) {
                if (this.mFirstPosition > 0 || top < this.mListPadding.top) {
                    if (this.mFirstPosition == 0) {
                        bottom = java.lang.Math.min(bottom, this.mListPadding.top - top);
                    }
                    offsetChildrenTopAndBottom(bottom);
                    if (this.mFirstPosition > 0) {
                        int i4 = this.mFirstPosition;
                        if (this.mStackFromBottom) {
                            i = 1;
                        }
                        fillUp(i4 - i, childAt.getTop() - i2);
                        adjustViewsUpOrDown();
                    }
                }
            }
        }
    }

    private void correctTooLow(int i, int i2, int i3) {
        if (this.mFirstPosition == 0 && i3 > 0) {
            int top = getChildAt(0).getTop();
            int i4 = this.mListPadding.top;
            int i5 = (this.mBottom - this.mTop) - this.mListPadding.bottom;
            int i6 = top - i4;
            android.view.View childAt = getChildAt(i3 - 1);
            int bottom = childAt.getBottom();
            int i7 = (this.mFirstPosition + i3) - 1;
            if (i6 > 0) {
                if (i7 < this.mItemCount - 1 || bottom > i5) {
                    if (i7 == this.mItemCount - 1) {
                        i6 = java.lang.Math.min(i6, bottom - i5);
                    }
                    offsetChildrenTopAndBottom(-i6);
                    if (i7 < this.mItemCount - 1) {
                        if (!this.mStackFromBottom) {
                            i = 1;
                        }
                        fillDown(i7 + i, childAt.getBottom() + i2);
                        adjustViewsUpOrDown();
                    }
                }
            }
        }
    }

    private android.view.View fillFromSelection(int i, int i2, int i3) {
        int i4;
        int max;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int i5 = this.mSelectedPosition;
        int i6 = this.mNumColumns;
        int i7 = this.mVerticalSpacing;
        if (!this.mStackFromBottom) {
            max = i5 - (i5 % i6);
            i4 = -1;
        } else {
            int i8 = (this.mItemCount - 1) - i5;
            i4 = (this.mItemCount - 1) - (i8 - (i8 % i6));
            max = java.lang.Math.max(0, (i4 - i6) + 1);
        }
        int topSelectionPixel = getTopSelectionPixel(i2, verticalFadingEdgeLength, max);
        int bottomSelectionPixel = getBottomSelectionPixel(i3, verticalFadingEdgeLength, i6, max);
        android.view.View makeRow = makeRow(this.mStackFromBottom ? i4 : max, i, true);
        this.mFirstPosition = max;
        android.view.View view = this.mReferenceView;
        adjustForTopFadingEdge(view, topSelectionPixel, bottomSelectionPixel);
        adjustForBottomFadingEdge(view, topSelectionPixel, bottomSelectionPixel);
        if (!this.mStackFromBottom) {
            fillUp(max - i6, view.getTop() - i7);
            adjustViewsUpOrDown();
            fillDown(max + i6, view.getBottom() + i7);
        } else {
            fillDown(i4 + i6, view.getBottom() + i7);
            adjustViewsUpOrDown();
            fillUp(max - 1, view.getTop() - i7);
        }
        return makeRow;
    }

    private int getBottomSelectionPixel(int i, int i2, int i3, int i4) {
        if ((i4 + i3) - 1 < this.mItemCount - 1) {
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

    private void adjustForBottomFadingEdge(android.view.View view, int i, int i2) {
        if (view.getBottom() > i2) {
            offsetChildrenTopAndBottom(-java.lang.Math.min(view.getTop() - i, view.getBottom() - i2));
        }
    }

    private void adjustForTopFadingEdge(android.view.View view, int i, int i2) {
        if (view.getTop() < i) {
            offsetChildrenTopAndBottom(java.lang.Math.min(i - view.getTop(), i2 - view.getBottom()));
        }
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

    private android.view.View moveSelection(int i, int i2, int i3) {
        int i4;
        int max;
        int i5;
        android.view.View makeRow;
        android.view.View view;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int i6 = this.mSelectedPosition;
        int i7 = this.mNumColumns;
        int i8 = this.mVerticalSpacing;
        if (!this.mStackFromBottom) {
            int i9 = i6 - i;
            max = i9 - (i9 % i7);
            i5 = i6 - (i6 % i7);
            i4 = -1;
        } else {
            int i10 = (this.mItemCount - 1) - i6;
            i4 = (this.mItemCount - 1) - (i10 - (i10 % i7));
            int max2 = java.lang.Math.max(0, (i4 - i7) + 1);
            int i11 = (this.mItemCount - 1) - (i6 - i);
            max = java.lang.Math.max(0, (((this.mItemCount - 1) - (i11 - (i11 % i7))) - i7) + 1);
            i5 = max2;
        }
        int i12 = i5 - max;
        int topSelectionPixel = getTopSelectionPixel(i2, verticalFadingEdgeLength, i5);
        int bottomSelectionPixel = getBottomSelectionPixel(i3, verticalFadingEdgeLength, i7, i5);
        this.mFirstPosition = i5;
        if (i12 > 0) {
            makeRow = makeRow(this.mStackFromBottom ? i4 : i5, (this.mReferenceViewInSelectedRow != null ? this.mReferenceViewInSelectedRow.getBottom() : 0) + i8, true);
            view = this.mReferenceView;
            adjustForBottomFadingEdge(view, topSelectionPixel, bottomSelectionPixel);
        } else if (i12 < 0) {
            makeRow = makeRow(this.mStackFromBottom ? i4 : i5, (this.mReferenceViewInSelectedRow == null ? 0 : this.mReferenceViewInSelectedRow.getTop()) - i8, false);
            view = this.mReferenceView;
            adjustForTopFadingEdge(view, topSelectionPixel, bottomSelectionPixel);
        } else {
            makeRow = makeRow(this.mStackFromBottom ? i4 : i5, this.mReferenceViewInSelectedRow != null ? this.mReferenceViewInSelectedRow.getTop() : 0, true);
            view = this.mReferenceView;
        }
        if (!this.mStackFromBottom) {
            fillUp(i5 - i7, view.getTop() - i8);
            adjustViewsUpOrDown();
            fillDown(i5 + i7, view.getBottom() + i8);
        } else {
            fillDown(i4 + i7, view.getBottom() + i8);
            adjustViewsUpOrDown();
            fillUp(i5 - 1, view.getTop() - i8);
        }
        return makeRow;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean determineColumns(int i) {
        int i2 = this.mRequestedHorizontalSpacing;
        int i3 = this.mStretchMode;
        int i4 = this.mRequestedColumnWidth;
        if (this.mRequestedNumColumns == -1) {
            if (i4 > 0) {
                this.mNumColumns = (i + i2) / (i4 + i2);
            } else {
                this.mNumColumns = 2;
            }
        } else {
            this.mNumColumns = this.mRequestedNumColumns;
        }
        if (this.mNumColumns <= 0) {
            this.mNumColumns = 1;
        }
        boolean z = false;
        switch (i3) {
            case 0:
                this.mColumnWidth = i4;
                this.mHorizontalSpacing = i2;
                return z;
            default:
                int i5 = (i - (this.mNumColumns * i4)) - ((this.mNumColumns - 1) * i2);
                if (i5 < 0) {
                    z = true;
                }
                switch (i3) {
                    case 1:
                        this.mColumnWidth = i4;
                        if (this.mNumColumns > 1) {
                            this.mHorizontalSpacing = i2 + (i5 / (this.mNumColumns - 1));
                            break;
                        } else {
                            this.mHorizontalSpacing = i2 + i5;
                            break;
                        }
                    case 2:
                        this.mColumnWidth = i4 + (i5 / this.mNumColumns);
                        this.mHorizontalSpacing = i2;
                        break;
                    case 3:
                        this.mColumnWidth = i4;
                        if (this.mNumColumns > 1) {
                            this.mHorizontalSpacing = i2 + (i5 / (this.mNumColumns + 1));
                            break;
                        } else {
                            this.mHorizontalSpacing = i2 + i5;
                            break;
                        }
                }
        }
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        super.onMeasure(i, i2);
        int mode = android.view.View.MeasureSpec.getMode(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        int size = android.view.View.MeasureSpec.getSize(i);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        if (mode == 0) {
            if (this.mColumnWidth > 0) {
                i4 = this.mColumnWidth + this.mListPadding.left + this.mListPadding.right;
            } else {
                i4 = this.mListPadding.left + this.mListPadding.right;
            }
            size = i4 + getVerticalScrollbarWidth();
        }
        boolean determineColumns = determineColumns((size - this.mListPadding.left) - this.mListPadding.right);
        int i5 = 0;
        this.mItemCount = this.mAdapter == null ? 0 : this.mAdapter.getCount();
        int i6 = this.mItemCount;
        if (i6 <= 0) {
            i3 = 0;
        } else {
            android.view.View obtainView = obtainView(0, this.mIsScrap);
            android.widget.AbsListView.LayoutParams layoutParams = (android.widget.AbsListView.LayoutParams) obtainView.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = (android.widget.AbsListView.LayoutParams) generateDefaultLayoutParams();
                obtainView.setLayoutParams(layoutParams);
            }
            layoutParams.viewType = this.mAdapter.getItemViewType(0);
            layoutParams.isEnabled = this.mAdapter.isEnabled(0);
            layoutParams.forceAdd = true;
            obtainView.measure(getChildMeasureSpec(android.view.View.MeasureSpec.makeMeasureSpec(this.mColumnWidth, 1073741824), 0, layoutParams.width), getChildMeasureSpec(android.view.View.MeasureSpec.makeSafeMeasureSpec(android.view.View.MeasureSpec.getSize(i2), 0), 0, layoutParams.height));
            i3 = obtainView.getMeasuredHeight();
            combineMeasuredStates(0, obtainView.getMeasuredState());
            if (this.mRecycler.shouldRecycleViewType(layoutParams.viewType)) {
                this.mRecycler.addScrapView(obtainView, -1);
            }
        }
        if (mode2 == 0) {
            size2 = this.mListPadding.top + this.mListPadding.bottom + i3 + (getVerticalFadingEdgeLength() * 2);
        }
        if (mode2 == Integer.MIN_VALUE) {
            int i7 = this.mListPadding.top + this.mListPadding.bottom;
            int i8 = this.mNumColumns;
            while (true) {
                if (i5 >= i6) {
                    size2 = i7;
                    break;
                }
                i7 += i3;
                i5 += i8;
                if (i5 < i6) {
                    i7 += this.mVerticalSpacing;
                }
                if (i7 >= size2) {
                    break;
                }
            }
        }
        if (mode == Integer.MIN_VALUE && this.mRequestedNumColumns != -1 && ((this.mRequestedNumColumns * this.mColumnWidth) + ((this.mRequestedNumColumns - 1) * this.mHorizontalSpacing) + this.mListPadding.left + this.mListPadding.right > size || determineColumns)) {
            size |= 16777216;
        }
        setMeasuredDimension(size, size2);
        this.mWidthMeasureSpec = i;
    }

    @Override // android.view.ViewGroup
    protected void attachLayoutAnimationParameters(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams, int i, int i2) {
        android.view.animation.GridLayoutAnimationController.AnimationParameters animationParameters = (android.view.animation.GridLayoutAnimationController.AnimationParameters) layoutParams.layoutAnimationParameters;
        if (animationParameters == null) {
            animationParameters = new android.view.animation.GridLayoutAnimationController.AnimationParameters();
            layoutParams.layoutAnimationParameters = animationParameters;
        }
        animationParameters.count = i2;
        animationParameters.index = i;
        animationParameters.columnsCount = this.mNumColumns;
        animationParameters.rowsCount = i2 / this.mNumColumns;
        if (!this.mStackFromBottom) {
            animationParameters.column = i % this.mNumColumns;
            animationParameters.row = i / this.mNumColumns;
        } else {
            int i3 = (i2 - 1) - i;
            animationParameters.column = (this.mNumColumns - 1) - (i3 % this.mNumColumns);
            animationParameters.row = (animationParameters.rowsCount - 1) - (i3 / this.mNumColumns);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.widget.AbsListView
    protected void layoutChildren() {
        boolean z;
        int i;
        android.view.View view;
        android.view.View view2;
        android.view.View view3;
        int i2;
        android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo;
        android.view.View view4;
        int i3;
        android.view.View fillFromTop;
        android.view.View childAt;
        int i4;
        int i5;
        android.view.View accessibilityFocusedChild;
        boolean z2 = this.mBlockLayoutRequests;
        if (!z2) {
            this.mBlockLayoutRequests = true;
        }
        try {
            super.layoutChildren();
            invalidate();
            if (this.mAdapter == null) {
                resetList();
                invokeOnItemScrollListener();
                if (z2) {
                    return;
                }
                this.mBlockLayoutRequests = false;
                return;
            }
            int i6 = this.mListPadding.top;
            int i7 = (this.mBottom - this.mTop) - this.mListPadding.bottom;
            int childCount = getChildCount();
            switch (this.mLayoutMode) {
                case 1:
                case 3:
                case 4:
                case 5:
                    i = 0;
                    view = null;
                    view2 = null;
                    view3 = null;
                    break;
                case 2:
                    int i8 = this.mNextSelectedPosition - this.mFirstPosition;
                    if (i8 >= 0 && i8 < childCount) {
                        view = getChildAt(i8);
                        view2 = null;
                        view3 = null;
                        i = 0;
                        break;
                    }
                    i = 0;
                    view = null;
                    view2 = null;
                    view3 = null;
                    break;
                case 6:
                    if (this.mNextSelectedPosition >= 0) {
                        i = this.mNextSelectedPosition - this.mSelectedPosition;
                        view = null;
                        view2 = null;
                        view3 = null;
                        break;
                    }
                    i = 0;
                    view = null;
                    view2 = null;
                    view3 = null;
                    break;
                default:
                    int i9 = this.mSelectedPosition - this.mFirstPosition;
                    view2 = (i9 < 0 || i9 >= childCount) ? null : getChildAt(i9);
                    view3 = getChildAt(0);
                    view = null;
                    i = 0;
                    break;
            }
            boolean z3 = this.mDataChanged;
            if (z3) {
                handleDataChanged();
            }
            if (this.mItemCount == 0) {
                resetList();
                invokeOnItemScrollListener();
                if (z2) {
                    return;
                }
                this.mBlockLayoutRequests = false;
                return;
            }
            setSelectedPositionInt(this.mNextSelectedPosition);
            android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
            if (viewRootImpl == null || (view4 = viewRootImpl.getAccessibilityFocusedHost()) == null || (accessibilityFocusedChild = getAccessibilityFocusedChild(view4)) == null) {
                i2 = -1;
                accessibilityNodeInfo = null;
                view4 = null;
            } else {
                if (z3 && !accessibilityFocusedChild.hasTransientState() && !this.mAdapterHasStableIds) {
                    accessibilityNodeInfo = null;
                    view4 = null;
                    i2 = getPositionForView(accessibilityFocusedChild);
                }
                accessibilityNodeInfo = viewRootImpl.getAccessibilityFocusedVirtualView();
                i2 = getPositionForView(accessibilityFocusedChild);
            }
            int i10 = this.mFirstPosition;
            android.widget.AbsListView.RecycleBin recycleBin = this.mRecycler;
            try {
                if (z3) {
                    int i11 = 0;
                    while (i11 < childCount) {
                        boolean z4 = z2;
                        recycleBin.addScrapView(getChildAt(i11), i10 + i11);
                        i11++;
                        z2 = z4;
                        i2 = i2;
                    }
                    z = z2;
                    i3 = i2;
                } else {
                    z = z2;
                    i3 = i2;
                    recycleBin.fillActiveViews(childCount, i10);
                }
                detachAllViewsFromParent();
                recycleBin.removeSkippedScrap();
                switch (this.mLayoutMode) {
                    case 1:
                        this.mFirstPosition = 0;
                        fillFromTop = fillFromTop(i6);
                        adjustViewsUpOrDown();
                        break;
                    case 2:
                        if (view == null) {
                            fillFromTop = fillSelection(i6, i7);
                            break;
                        } else {
                            fillFromTop = fillFromSelection(view.getTop(), i6, i7);
                            break;
                        }
                    case 3:
                        fillFromTop = fillUp(this.mItemCount - 1, i7);
                        adjustViewsUpOrDown();
                        break;
                    case 4:
                        fillFromTop = fillSpecific(this.mSelectedPosition, this.mSpecificTop);
                        break;
                    case 5:
                        fillFromTop = fillSpecific(this.mSyncPosition, this.mSpecificTop);
                        break;
                    case 6:
                        fillFromTop = moveSelection(i, i6, i7);
                        break;
                    default:
                        if (childCount != 0) {
                            if (this.mSelectedPosition >= 0 && this.mSelectedPosition < this.mItemCount) {
                                int i12 = this.mSelectedPosition;
                                if (view2 != null) {
                                    i6 = view2.getTop();
                                }
                                fillFromTop = fillSpecific(i12, i6);
                                break;
                            } else if (this.mFirstPosition >= this.mItemCount) {
                                fillFromTop = fillSpecific(0, i6);
                                break;
                            } else {
                                int i13 = this.mFirstPosition;
                                if (view3 != null) {
                                    i6 = view3.getTop();
                                }
                                fillFromTop = fillSpecific(i13, i6);
                                break;
                            }
                        } else if (this.mStackFromBottom) {
                            int i14 = this.mItemCount - 1;
                            if (this.mAdapter != null && !isInTouchMode()) {
                                i4 = i14;
                                setSelectedPositionInt(i4);
                                fillFromTop = fillFromBottom(i14, i7);
                                break;
                            }
                            i4 = -1;
                            setSelectedPositionInt(i4);
                            fillFromTop = fillFromBottom(i14, i7);
                        } else {
                            if (this.mAdapter != null && !isInTouchMode()) {
                                i5 = 0;
                                setSelectedPositionInt(i5);
                                fillFromTop = fillFromTop(i6);
                                break;
                            }
                            i5 = -1;
                            setSelectedPositionInt(i5);
                            fillFromTop = fillFromTop(i6);
                        }
                        break;
                }
                recycleBin.scrapActiveViews();
                if (fillFromTop != null) {
                    positionSelector(-1, fillFromTop);
                    this.mSelectedTop = fillFromTop.getTop();
                } else {
                    if (this.mTouchMode > 0 && this.mTouchMode < 3) {
                        android.view.View childAt2 = getChildAt(this.mMotionPosition - this.mFirstPosition);
                        if (childAt2 != null) {
                            positionSelector(this.mMotionPosition, childAt2);
                        }
                    } else if (this.mSelectedPosition != -1) {
                        android.view.View childAt3 = getChildAt(this.mSelectorPosition - this.mFirstPosition);
                        if (childAt3 != null) {
                            positionSelector(this.mSelectorPosition, childAt3);
                        }
                    } else {
                        this.mSelectedTop = 0;
                        this.mSelectorRect.setEmpty();
                    }
                }
                if (viewRootImpl != null && viewRootImpl.getAccessibilityFocusedHost() == null) {
                    if (view4 == null || !view4.isAttachedToWindow()) {
                        int i15 = i3;
                        if (i15 != -1 && (childAt = getChildAt(android.util.MathUtils.constrain(i15 - this.mFirstPosition, 0, getChildCount() - 1))) != null) {
                            childAt.requestAccessibilityFocus();
                        }
                    } else {
                        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = view4.getAccessibilityNodeProvider();
                        if (accessibilityNodeInfo == null || accessibilityNodeProvider == null) {
                            view4.requestAccessibilityFocus();
                        } else {
                            accessibilityNodeProvider.performAction(android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getSourceNodeId()), 64, null);
                        }
                    }
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
                if (z) {
                    return;
                }
                this.mBlockLayoutRequests = false;
            } catch (java.lang.Throwable th) {
                th = th;
                if (!z) {
                    this.mBlockLayoutRequests = false;
                }
                throw th;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            z = z2;
        }
    }

    private android.view.View makeAndAddView(int i, int i2, boolean z, int i3, boolean z2, int i4) {
        android.view.View activeView;
        if (!this.mDataChanged && (activeView = this.mRecycler.getActiveView(i)) != null) {
            setupChild(activeView, i, i2, z, i3, z2, true, i4);
            return activeView;
        }
        android.view.View obtainView = obtainView(i, this.mIsScrap);
        setupChild(obtainView, i, i2, z, i3, z2, this.mIsScrap[0], i4);
        return obtainView;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void setupChild(android.view.View view, int i, int i2, boolean z, int i3, boolean z2, boolean z3, int i4) {
        int i5;
        android.os.Trace.traceBegin(8L, "setupGridItem");
        boolean z4 = z2 && shouldShowSelector();
        boolean z5 = z4 != view.isSelected();
        int i6 = this.mTouchMode;
        boolean z6 = i6 > 0 && i6 < 3 && this.mMotionPosition == i;
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
            if (z4) {
                requestFocus();
            }
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
        if (z3 && !layoutParams.forceAdd) {
            attachViewToParent(view, i4, layoutParams);
            if (!z3 || ((android.widget.AbsListView.LayoutParams) view.getLayoutParams()).scrappedFromPosition != i) {
                view.jumpDrawablesToCurrentState();
            }
        } else {
            layoutParams.forceAdd = false;
            addViewInLayout(view, i4, layoutParams, true);
        }
        if (z8) {
            view.measure(android.view.ViewGroup.getChildMeasureSpec(android.view.View.MeasureSpec.makeMeasureSpec(this.mColumnWidth, 1073741824), 0, layoutParams.width), android.view.ViewGroup.getChildMeasureSpec(android.view.View.MeasureSpec.makeMeasureSpec(0, 0), 0, layoutParams.height));
        } else {
            cleanupLayoutState(view);
        }
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i7 = z ? i2 : i2 - measuredHeight;
        switch (android.view.Gravity.getAbsoluteGravity(this.mGravity, getLayoutDirection()) & 7) {
            case 1:
                i5 = i3 + ((this.mColumnWidth - measuredWidth) / 2);
                break;
            case 2:
            case 3:
            case 4:
            default:
                i5 = i3;
                break;
            case 5:
                i5 = (i3 + this.mColumnWidth) - measuredWidth;
                break;
        }
        if (z8) {
            view.layout(i5, i7, measuredWidth + i5, measuredHeight + i7);
        } else {
            view.offsetLeftAndRight(i5 - view.getLeft());
            view.offsetTopAndBottom(i7 - view.getTop());
        }
        if (this.mCachingStarted && !view.isDrawingCacheEnabled()) {
            view.setDrawingCacheEnabled(true);
        }
        android.os.Trace.traceEnd(8L);
    }

    @Override // android.widget.AdapterView
    public void setSelection(int i) {
        if (!isInTouchMode()) {
            setNextSelectedPositionInt(i);
        } else {
            this.mResurrectToPosition = i;
        }
        this.mLayoutMode = 2;
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        requestLayout();
    }

    @Override // android.widget.AbsListView
    void setSelectionInt(int i) {
        int i2 = this.mNextSelectedPosition;
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        setNextSelectedPositionInt(i);
        layoutChildren();
        int i3 = this.mStackFromBottom ? (this.mItemCount - 1) - this.mNextSelectedPosition : this.mNextSelectedPosition;
        if (this.mStackFromBottom) {
            i2 = (this.mItemCount - 1) - i2;
        }
        if (i3 / this.mNumColumns != i2 / this.mNumColumns) {
            awakenScrollBars();
        }
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
        if (this.mAdapter == null) {
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
                        if (!resurrectSelectionIfNeeded() && !arrowScroll(33)) {
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
                case 20:
                    if (keyEvent.hasNoModifiers()) {
                        if (!resurrectSelectionIfNeeded() && !arrowScroll(130)) {
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
                case 21:
                    if (keyEvent.hasNoModifiers()) {
                        if (!resurrectSelectionIfNeeded() && !arrowScroll(17)) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    break;
                case 22:
                    if (keyEvent.hasNoModifiers()) {
                        if (!resurrectSelectionIfNeeded() && !arrowScroll(66)) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    break;
                case 61:
                    if (keyEvent.hasNoModifiers()) {
                        if (!resurrectSelectionIfNeeded() && !sequenceScroll(2)) {
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    } else if (keyEvent.hasModifiers(1)) {
                        if (!resurrectSelectionIfNeeded() && !sequenceScroll(1)) {
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
            case 0:
                return super.onKeyDown(i, keyEvent);
            case 1:
                return super.onKeyUp(i, keyEvent);
            case 2:
                return super.onKeyMultiple(i, i2, keyEvent);
            default:
                return false;
        }
    }

    boolean pageScroll(int i) {
        int i2;
        if (i == 33) {
            i2 = java.lang.Math.max(0, this.mSelectedPosition - getChildCount());
        } else if (i != 130) {
            i2 = -1;
        } else {
            i2 = java.lang.Math.min(this.mItemCount - 1, this.mSelectedPosition + getChildCount());
        }
        if (i2 < 0) {
            return false;
        }
        setSelectionInt(i2);
        invokeOnItemScrollListener();
        awakenScrollBars();
        return true;
    }

    boolean fullScroll(int i) {
        boolean z = true;
        if (i == 33) {
            this.mLayoutMode = 2;
            setSelectionInt(0);
            invokeOnItemScrollListener();
        } else if (i != 130) {
            z = false;
        } else {
            this.mLayoutMode = 2;
            setSelectionInt(this.mItemCount - 1);
            invokeOnItemScrollListener();
        }
        if (z) {
            awakenScrollBars();
        }
        return z;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    boolean arrowScroll(int i) {
        int i2;
        int max;
        boolean z;
        int i3 = this.mSelectedPosition;
        int i4 = this.mNumColumns;
        boolean z2 = true;
        if (!this.mStackFromBottom) {
            max = (i3 / i4) * i4;
            i2 = java.lang.Math.min((max + i4) - 1, this.mItemCount - 1);
        } else {
            i2 = (this.mItemCount - 1) - ((((this.mItemCount - 1) - i3) / i4) * i4);
            max = java.lang.Math.max(0, (i2 - i4) + 1);
        }
        switch (i) {
            case 33:
                if (max > 0) {
                    this.mLayoutMode = 6;
                    setSelectionInt(java.lang.Math.max(0, i3 - i4));
                    z = true;
                    break;
                }
                z = false;
                break;
            case 130:
                if (i2 < this.mItemCount - 1) {
                    this.mLayoutMode = 6;
                    setSelectionInt(java.lang.Math.min(i4 + i3, this.mItemCount - 1));
                    z = true;
                    break;
                }
                z = false;
                break;
            default:
                z = false;
                break;
        }
        boolean isLayoutRtl = isLayoutRtl();
        if (i3 > max && ((i == 17 && !isLayoutRtl) || (i == 66 && isLayoutRtl))) {
            this.mLayoutMode = 6;
            setSelectionInt(java.lang.Math.max(0, i3 - 1));
        } else if (i3 < i2 && ((i == 17 && isLayoutRtl) || (i == 66 && !isLayoutRtl))) {
            this.mLayoutMode = 6;
            setSelectionInt(java.lang.Math.min(i3 + 1, this.mItemCount - 1));
        } else {
            z2 = z;
        }
        if (z2) {
            playSoundEffect(android.view.SoundEffectConstants.getContantForFocusDirection(i));
            invokeOnItemScrollListener();
        }
        if (z2) {
            awakenScrollBars();
        }
        return z2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    boolean sequenceScroll(int i) {
        int max;
        int i2;
        boolean z;
        int i3 = this.mSelectedPosition;
        int i4 = this.mNumColumns;
        int i5 = this.mItemCount;
        if (!this.mStackFromBottom) {
            max = (i3 / i4) * i4;
            i2 = java.lang.Math.min((i4 + max) - 1, i5 - 1);
        } else {
            int i6 = i5 - 1;
            int i7 = i6 - (((i6 - i3) / i4) * i4);
            max = java.lang.Math.max(0, (i7 - i4) + 1);
            i2 = i7;
        }
        switch (i) {
            case 1:
                if (i3 > 0) {
                    this.mLayoutMode = 6;
                    setSelectionInt(i3 - 1);
                    z = i3 == max;
                    r4 = true;
                    break;
                }
                z = false;
                break;
            case 2:
                if (i3 < i5 - 1) {
                    this.mLayoutMode = 6;
                    setSelectionInt(i3 + 1);
                    z = i3 == i2;
                    r4 = true;
                    break;
                }
                z = false;
                break;
            default:
                z = false;
                break;
        }
        if (r4) {
            playSoundEffect(android.view.SoundEffectConstants.getContantForFocusDirection(i));
            invokeOnItemScrollListener();
        }
        if (z) {
            awakenScrollBars();
        }
        return r4;
    }

    @Override // android.widget.AbsListView, android.view.View
    protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
        super.onFocusChanged(z, i, rect);
        int i2 = -1;
        if (z && rect != null) {
            rect.offset(this.mScrollX, this.mScrollY);
            android.graphics.Rect rect2 = this.mTempRect;
            int childCount = getChildCount();
            int i3 = Integer.MAX_VALUE;
            for (int i4 = 0; i4 < childCount; i4++) {
                if (isCandidateSelection(i4, i)) {
                    android.view.View childAt = getChildAt(i4);
                    childAt.getDrawingRect(rect2);
                    offsetDescendantRectToMyCoords(childAt, rect2);
                    int distance = getDistance(rect, rect2, i);
                    if (distance < i3) {
                        i2 = i4;
                        i3 = distance;
                    }
                }
            }
        }
        if (i2 >= 0) {
            setSelection(i2 + this.mFirstPosition);
        } else {
            requestLayout();
        }
    }

    private boolean isCandidateSelection(int i, int i2) {
        int i3;
        int max;
        int childCount = getChildCount();
        int i4 = childCount - 1;
        int i5 = i4 - i;
        if (!this.mStackFromBottom) {
            max = i - (i % this.mNumColumns);
            i3 = java.lang.Math.min((this.mNumColumns + max) - 1, childCount);
        } else {
            i3 = i4 - (i5 - (i5 % this.mNumColumns));
            max = java.lang.Math.max(0, (i3 - this.mNumColumns) + 1);
        }
        switch (i2) {
            case 1:
                return i == i3 && i3 == i4;
            case 2:
                return i == max && max == 0;
            case 17:
                return i == i3;
            case 33:
                return i3 == i4;
            case 66:
                return i == max;
            case 130:
                return max == 0;
            default:
                throw new java.lang.IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
        }
    }

    @android.view.RemotableViewMethod
    public void setGravity(int i) {
        if (this.mGravity != i) {
            this.mGravity = i;
            requestLayoutIfNecessary();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    @android.view.RemotableViewMethod
    public void setHorizontalSpacing(int i) {
        if (i != this.mRequestedHorizontalSpacing) {
            this.mRequestedHorizontalSpacing = i;
            requestLayoutIfNecessary();
        }
    }

    public int getHorizontalSpacing() {
        return this.mHorizontalSpacing;
    }

    public int getRequestedHorizontalSpacing() {
        return this.mRequestedHorizontalSpacing;
    }

    @android.view.RemotableViewMethod
    public void setVerticalSpacing(int i) {
        if (i != this.mVerticalSpacing) {
            this.mVerticalSpacing = i;
            requestLayoutIfNecessary();
        }
    }

    public int getVerticalSpacing() {
        return this.mVerticalSpacing;
    }

    @android.view.RemotableViewMethod
    public void setStretchMode(int i) {
        if (i != this.mStretchMode) {
            this.mStretchMode = i;
            requestLayoutIfNecessary();
        }
    }

    public int getStretchMode() {
        return this.mStretchMode;
    }

    @android.view.RemotableViewMethod
    public void setColumnWidth(int i) {
        if (i != this.mRequestedColumnWidth) {
            this.mRequestedColumnWidth = i;
            requestLayoutIfNecessary();
        }
    }

    public int getColumnWidth() {
        return this.mColumnWidth;
    }

    public int getRequestedColumnWidth() {
        return this.mRequestedColumnWidth;
    }

    @android.view.RemotableViewMethod
    public void setNumColumns(int i) {
        if (i != this.mRequestedNumColumns) {
            this.mRequestedNumColumns = i;
            requestLayoutIfNecessary();
        }
    }

    @android.view.ViewDebug.ExportedProperty
    public int getNumColumns() {
        return this.mNumColumns;
    }

    private void adjustViewsUpOrDown() {
        int childCount = getChildCount();
        if (childCount > 0) {
            int i = 0;
            if (!this.mStackFromBottom) {
                int top = getChildAt(0).getTop() - this.mListPadding.top;
                if (this.mFirstPosition != 0) {
                    top -= this.mVerticalSpacing;
                }
                if (top >= 0) {
                    i = top;
                }
            } else {
                int bottom = getChildAt(childCount - 1).getBottom() - (getHeight() - this.mListPadding.bottom);
                if (this.mFirstPosition + childCount < this.mItemCount) {
                    bottom += this.mVerticalSpacing;
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

    @Override // android.widget.AbsListView, android.view.View
    protected int computeVerticalScrollExtent() {
        int childCount = getChildCount();
        if (childCount <= 0) {
            return 0;
        }
        int i = (((childCount + r2) - 1) / this.mNumColumns) * 100;
        android.view.View childAt = getChildAt(0);
        int top = childAt.getTop();
        int height = childAt.getHeight();
        if (height > 0) {
            i += (top * 100) / height;
        }
        android.view.View childAt2 = getChildAt(childCount - 1);
        int bottom = childAt2.getBottom();
        int height2 = childAt2.getHeight();
        if (height2 > 0) {
            return i - (((bottom - getHeight()) * 100) / height2);
        }
        return i;
    }

    @Override // android.widget.AbsListView, android.view.View
    protected int computeVerticalScrollOffset() {
        if (this.mFirstPosition >= 0 && getChildCount() > 0) {
            android.view.View childAt = getChildAt(0);
            int top = childAt.getTop();
            int height = childAt.getHeight();
            if (height > 0) {
                int i = this.mNumColumns;
                int i2 = ((this.mItemCount + i) - 1) / i;
                return java.lang.Math.max(((((this.mFirstPosition + (isStackFromBottom() ? (i2 * i) - this.mItemCount : 0)) / i) * 100) - ((top * 100) / height)) + ((int) ((this.mScrollY / getHeight()) * i2 * 100.0f)), 0);
            }
        }
        return 0;
    }

    @Override // android.widget.AbsListView, android.view.View
    protected int computeVerticalScrollRange() {
        int i = ((this.mItemCount + r0) - 1) / this.mNumColumns;
        int max = java.lang.Math.max(i * 100, 0);
        if (this.mScrollY != 0) {
            return max + java.lang.Math.abs((int) ((this.mScrollY / getHeight()) * i * 100.0f));
        }
        return max;
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.GridView.class.getName();
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        int numColumns = getNumColumns();
        int count = getCount() / numColumns;
        accessibilityNodeInfo.setCollectionInfo(android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.obtain(count, numColumns, false, getSelectionModeForAccessibility()));
        if (numColumns > 0 || count > 0) {
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
                int numColumns = getNumColumns();
                int i2 = bundle.getInt(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_ROW_INT, -1);
                int min = java.lang.Math.min(numColumns * i2, getCount() - 1);
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
        int i2;
        int i3;
        super.onInitializeAccessibilityNodeInfoForItem(view, i, accessibilityNodeInfo);
        int count = getCount();
        int numColumns = getNumColumns();
        int i4 = count / numColumns;
        if (!this.mStackFromBottom) {
            i3 = i % numColumns;
            i2 = i / numColumns;
        } else {
            int i5 = (count - 1) - i;
            i2 = (i4 - 1) - (i5 / numColumns);
            i3 = (numColumns - 1) - (i5 % numColumns);
        }
        android.widget.AbsListView.LayoutParams layoutParams = (android.widget.AbsListView.LayoutParams) view.getLayoutParams();
        accessibilityNodeInfo.setCollectionItemInfo(android.view.accessibility.AccessibilityNodeInfo.CollectionItemInfo.obtain(i2, 1, i3, 1, layoutParams != null && layoutParams.viewType == -2, isItemChecked(i)));
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("numColumns", getNumColumns());
    }
}
