package android.widget;

/* loaded from: classes4.dex */
public class TableRow extends android.widget.LinearLayout {
    private android.widget.TableRow.ChildrenTracker mChildrenTracker;
    private android.util.SparseIntArray mColumnToChildIndex;
    private int[] mColumnWidths;
    private int[] mConstrainedColumnWidths;
    private int mNumColumns;

    public static class LayoutParams extends android.widget.LinearLayout.LayoutParams {
        private static final int LOCATION = 0;
        private static final int LOCATION_NEXT = 1;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        public int column;
        private int[] mOffset;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        public int span;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.TableRow.LayoutParams> {
            private int mLayout_columnId;
            private int mLayout_spanId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
                this.mLayout_columnId = propertyMapper.mapInt("layout_column", 16843084);
                this.mLayout_spanId = propertyMapper.mapInt("layout_span", 16843085);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(android.widget.TableRow.LayoutParams layoutParams, android.view.inspector.PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readInt(this.mLayout_columnId, layoutParams.column);
                propertyReader.readInt(this.mLayout_spanId, layoutParams.span);
            }
        }

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mOffset = new int[2];
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TableRow_Cell);
            this.column = obtainStyledAttributes.getInt(0, -1);
            this.span = obtainStyledAttributes.getInt(1, 1);
            if (this.span <= 1) {
                this.span = 1;
            }
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.mOffset = new int[2];
            this.column = -1;
            this.span = 1;
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2, f);
            this.mOffset = new int[2];
            this.column = -1;
            this.span = 1;
        }

        public LayoutParams() {
            super(-1, -2);
            this.mOffset = new int[2];
            this.column = -1;
            this.span = 1;
        }

        public LayoutParams(int i) {
            this();
            this.column = i;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mOffset = new int[2];
        }

        public LayoutParams(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mOffset = new int[2];
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void setBaseAttributes(android.content.res.TypedArray typedArray, int i, int i2) {
            if (typedArray.hasValue(i)) {
                this.width = typedArray.getLayoutDimension(i, "layout_width");
            } else {
                this.width = -1;
            }
            if (typedArray.hasValue(i2)) {
                this.height = typedArray.getLayoutDimension(i2, "layout_height");
            } else {
                this.height = -2;
            }
        }

        @Override // android.widget.LinearLayout.LayoutParams, android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:column", this.column);
            viewHierarchyEncoder.addProperty("layout:span", this.span);
        }
    }

    public TableRow(android.content.Context context) {
        super(context);
        this.mNumColumns = 0;
        initTableRow();
    }

    public TableRow(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNumColumns = 0;
        initTableRow();
    }

    private void initTableRow() {
        android.view.ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
        this.mChildrenTracker = new android.widget.TableRow.ChildrenTracker();
        if (onHierarchyChangeListener != null) {
            this.mChildrenTracker.setOnHierarchyChangeListener(onHierarchyChangeListener);
        }
        super.setOnHierarchyChangeListener(this.mChildrenTracker);
    }

    @Override // android.view.ViewGroup
    public void setOnHierarchyChangeListener(android.view.ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mChildrenTracker.setOnHierarchyChangeListener(onHierarchyChangeListener);
    }

    void setColumnCollapsed(int i, boolean z) {
        android.view.View virtualChildAt = getVirtualChildAt(i);
        if (virtualChildAt != null) {
            virtualChildAt.setVisibility(z ? 8 : 0);
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        measureHorizontal(i, i2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        layoutHorizontal(i, i2, i3, i4);
    }

    @Override // android.widget.LinearLayout
    public android.view.View getVirtualChildAt(int i) {
        if (this.mColumnToChildIndex == null) {
            mapIndexAndColumns();
        }
        int i2 = this.mColumnToChildIndex.get(i, -1);
        if (i2 != -1) {
            return getChildAt(i2);
        }
        return null;
    }

    @Override // android.widget.LinearLayout
    public int getVirtualChildCount() {
        if (this.mColumnToChildIndex == null) {
            mapIndexAndColumns();
        }
        return this.mNumColumns;
    }

    private void mapIndexAndColumns() {
        if (this.mColumnToChildIndex == null) {
            int childCount = getChildCount();
            this.mColumnToChildIndex = new android.util.SparseIntArray();
            android.util.SparseIntArray sparseIntArray = this.mColumnToChildIndex;
            int i = 0;
            for (int i2 = 0; i2 < childCount; i2++) {
                android.widget.TableRow.LayoutParams layoutParams = (android.widget.TableRow.LayoutParams) getChildAt(i2).getLayoutParams();
                if (layoutParams.column >= i) {
                    i = layoutParams.column;
                }
                int i3 = 0;
                while (i3 < layoutParams.span) {
                    sparseIntArray.put(i, i2);
                    i3++;
                    i++;
                }
            }
            this.mNumColumns = i;
        }
    }

    @Override // android.widget.LinearLayout
    int measureNullChild(int i) {
        return this.mConstrainedColumnWidths[i];
    }

    @Override // android.widget.LinearLayout
    void measureChildBeforeLayout(android.view.View view, int i, int i2, int i3, int i4, int i5) {
        int i6;
        if (this.mConstrainedColumnWidths != null) {
            android.widget.TableRow.LayoutParams layoutParams = (android.widget.TableRow.LayoutParams) view.getLayoutParams();
            int i7 = layoutParams.span;
            int[] iArr = this.mConstrainedColumnWidths;
            int i8 = 0;
            for (int i9 = 0; i9 < i7; i9++) {
                i8 += iArr[i + i9];
            }
            int i10 = layoutParams.gravity;
            boolean isHorizontal = android.view.Gravity.isHorizontal(i10);
            if (!isHorizontal) {
                i6 = 1073741824;
            } else {
                i6 = Integer.MIN_VALUE;
            }
            view.measure(android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.max(0, (i8 - layoutParams.leftMargin) - layoutParams.rightMargin), i6), getChildMeasureSpec(i4, this.mPaddingTop + this.mPaddingBottom + layoutParams.topMargin + layoutParams.bottomMargin + i5, layoutParams.height));
            if (isHorizontal) {
                layoutParams.mOffset[1] = i8 - view.getMeasuredWidth();
                switch (android.view.Gravity.getAbsoluteGravity(i10, getLayoutDirection()) & 7) {
                    case 1:
                        layoutParams.mOffset[0] = layoutParams.mOffset[1] / 2;
                        break;
                    case 5:
                        layoutParams.mOffset[0] = layoutParams.mOffset[1];
                        break;
                }
            }
            int[] iArr2 = layoutParams.mOffset;
            layoutParams.mOffset[1] = 0;
            iArr2[0] = 0;
            return;
        }
        super.measureChildBeforeLayout(view, i, i2, i3, i4, i5);
    }

    @Override // android.widget.LinearLayout
    int getChildrenSkipCount(android.view.View view, int i) {
        return ((android.widget.TableRow.LayoutParams) view.getLayoutParams()).span - 1;
    }

    @Override // android.widget.LinearLayout
    int getLocationOffset(android.view.View view) {
        return ((android.widget.TableRow.LayoutParams) view.getLayoutParams()).mOffset[0];
    }

    @Override // android.widget.LinearLayout
    int getNextLocationOffset(android.view.View view) {
        return ((android.widget.TableRow.LayoutParams) view.getLayoutParams()).mOffset[1];
    }

    int[] getColumnsWidths(int i, int i2) {
        int childMeasureSpec;
        int virtualChildCount = getVirtualChildCount();
        if (this.mColumnWidths == null || virtualChildCount != this.mColumnWidths.length) {
            this.mColumnWidths = new int[virtualChildCount];
        }
        int[] iArr = this.mColumnWidths;
        for (int i3 = 0; i3 < virtualChildCount; i3++) {
            android.view.View virtualChildAt = getVirtualChildAt(i3);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8) {
                android.widget.TableRow.LayoutParams layoutParams = (android.widget.TableRow.LayoutParams) virtualChildAt.getLayoutParams();
                if (layoutParams.span == 1) {
                    switch (layoutParams.width) {
                        case -2:
                            childMeasureSpec = getChildMeasureSpec(i, 0, -2);
                            break;
                        case -1:
                            childMeasureSpec = android.view.View.MeasureSpec.makeSafeMeasureSpec(android.view.View.MeasureSpec.getSize(i2), 0);
                            break;
                        default:
                            childMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824);
                            break;
                    }
                    virtualChildAt.measure(childMeasureSpec, childMeasureSpec);
                    iArr[i3] = virtualChildAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                } else {
                    iArr[i3] = 0;
                }
            } else {
                iArr[i3] = 0;
            }
        }
        return iArr;
    }

    void setColumnsWidthConstraints(int[] iArr) {
        if (iArr == null || iArr.length < getVirtualChildCount()) {
            throw new java.lang.IllegalArgumentException("columnWidths should be >= getVirtualChildCount()");
        }
        this.mConstrainedColumnWidths = iArr;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public android.widget.TableRow.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.widget.TableRow.LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public android.widget.LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new android.widget.TableRow.LayoutParams();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof android.widget.TableRow.LayoutParams;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public android.widget.LinearLayout.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new android.widget.TableRow.LayoutParams(layoutParams);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.TableRow.class.getName();
    }

    private class ChildrenTracker implements android.view.ViewGroup.OnHierarchyChangeListener {
        private android.view.ViewGroup.OnHierarchyChangeListener listener;

        private ChildrenTracker() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOnHierarchyChangeListener(android.view.ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
            this.listener = onHierarchyChangeListener;
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(android.view.View view, android.view.View view2) {
            android.widget.TableRow.this.mColumnToChildIndex = null;
            if (this.listener != null) {
                this.listener.onChildViewAdded(view, view2);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(android.view.View view, android.view.View view2) {
            android.widget.TableRow.this.mColumnToChildIndex = null;
            if (this.listener != null) {
                this.listener.onChildViewRemoved(view, view2);
            }
        }
    }
}
