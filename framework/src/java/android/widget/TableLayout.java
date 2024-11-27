package android.widget;

/* loaded from: classes4.dex */
public class TableLayout extends android.widget.LinearLayout {
    private android.util.SparseBooleanArray mCollapsedColumns;
    private boolean mInitialized;
    private int[] mMaxWidths;
    private android.widget.TableLayout.PassThroughHierarchyChangeListener mPassThroughListener;
    private boolean mShrinkAllColumns;
    private android.util.SparseBooleanArray mShrinkableColumns;
    private boolean mStretchAllColumns;
    private android.util.SparseBooleanArray mStretchableColumns;

    public TableLayout(android.content.Context context) {
        super(context);
        initTableLayout();
    }

    public TableLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TableLayout);
        java.lang.String string = obtainStyledAttributes.getString(0);
        if (string != null) {
            if (string.charAt(0) == '*') {
                this.mStretchAllColumns = true;
            } else {
                this.mStretchableColumns = parseColumns(string);
            }
        }
        java.lang.String string2 = obtainStyledAttributes.getString(1);
        if (string2 != null) {
            if (string2.charAt(0) == '*') {
                this.mShrinkAllColumns = true;
            } else {
                this.mShrinkableColumns = parseColumns(string2);
            }
        }
        java.lang.String string3 = obtainStyledAttributes.getString(2);
        if (string3 != null) {
            this.mCollapsedColumns = parseColumns(string3);
        }
        obtainStyledAttributes.recycle();
        initTableLayout();
    }

    private static android.util.SparseBooleanArray parseColumns(java.lang.String str) {
        android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        for (java.lang.String str2 : java.util.regex.Pattern.compile("\\s*,\\s*").split(str)) {
            try {
                int parseInt = java.lang.Integer.parseInt(str2);
                if (parseInt >= 0) {
                    sparseBooleanArray.put(parseInt, true);
                }
            } catch (java.lang.NumberFormatException e) {
            }
        }
        return sparseBooleanArray;
    }

    private void initTableLayout() {
        if (this.mCollapsedColumns == null) {
            this.mCollapsedColumns = new android.util.SparseBooleanArray();
        }
        if (this.mStretchableColumns == null) {
            this.mStretchableColumns = new android.util.SparseBooleanArray();
        }
        if (this.mShrinkableColumns == null) {
            this.mShrinkableColumns = new android.util.SparseBooleanArray();
        }
        setOrientation(1);
        this.mPassThroughListener = new android.widget.TableLayout.PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(this.mPassThroughListener);
        this.mInitialized = true;
    }

    @Override // android.view.ViewGroup
    public void setOnHierarchyChangeListener(android.view.ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mPassThroughListener.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    private void requestRowsLayout() {
        if (this.mInitialized) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).requestLayout();
            }
        }
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.mInitialized) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).forceLayout();
            }
        }
        super.requestLayout();
    }

    public boolean isShrinkAllColumns() {
        return this.mShrinkAllColumns;
    }

    public void setShrinkAllColumns(boolean z) {
        this.mShrinkAllColumns = z;
    }

    public boolean isStretchAllColumns() {
        return this.mStretchAllColumns;
    }

    public void setStretchAllColumns(boolean z) {
        this.mStretchAllColumns = z;
    }

    public void setColumnCollapsed(int i, boolean z) {
        this.mCollapsedColumns.put(i, z);
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            android.view.View childAt = getChildAt(i2);
            if (childAt instanceof android.widget.TableRow) {
                ((android.widget.TableRow) childAt).setColumnCollapsed(i, z);
            }
        }
        requestRowsLayout();
    }

    public boolean isColumnCollapsed(int i) {
        return this.mCollapsedColumns.get(i);
    }

    public void setColumnStretchable(int i, boolean z) {
        this.mStretchableColumns.put(i, z);
        requestRowsLayout();
    }

    public boolean isColumnStretchable(int i) {
        return this.mStretchAllColumns || this.mStretchableColumns.get(i);
    }

    public void setColumnShrinkable(int i, boolean z) {
        this.mShrinkableColumns.put(i, z);
        requestRowsLayout();
    }

    public boolean isColumnShrinkable(int i) {
        return this.mShrinkAllColumns || this.mShrinkableColumns.get(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void trackCollapsedColumns(android.view.View view) {
        if (view instanceof android.widget.TableRow) {
            android.widget.TableRow tableRow = (android.widget.TableRow) view;
            android.util.SparseBooleanArray sparseBooleanArray = this.mCollapsedColumns;
            int size = sparseBooleanArray.size();
            for (int i = 0; i < size; i++) {
                int keyAt = sparseBooleanArray.keyAt(i);
                boolean valueAt = sparseBooleanArray.valueAt(i);
                if (valueAt) {
                    tableRow.setColumnCollapsed(keyAt, valueAt);
                }
            }
        }
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view) {
        super.addView(view);
        requestRowsLayout();
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view, int i) {
        super.addView(view, i);
        requestRowsLayout();
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void addView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        super.addView(view, layoutParams);
        requestRowsLayout();
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        requestRowsLayout();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        measureVertical(i, i2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        layoutVertical(i, i2, i3, i4);
    }

    @Override // android.widget.LinearLayout
    void measureChildBeforeLayout(android.view.View view, int i, int i2, int i3, int i4, int i5) {
        if (view instanceof android.widget.TableRow) {
            ((android.widget.TableRow) view).setColumnsWidthConstraints(this.mMaxWidths);
        }
        super.measureChildBeforeLayout(view, i, i2, i3, i4, i5);
    }

    @Override // android.widget.LinearLayout
    void measureVertical(int i, int i2) {
        findLargestCells(i, i2);
        shrinkAndStretchColumns(i);
        super.measureVertical(i, i2);
    }

    private void findLargestCells(int i, int i2) {
        int childCount = getChildCount();
        boolean z = true;
        for (int i3 = 0; i3 < childCount; i3++) {
            android.view.View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8 && (childAt instanceof android.widget.TableRow)) {
                android.widget.TableRow tableRow = (android.widget.TableRow) childAt;
                tableRow.getLayoutParams().height = -2;
                int[] columnsWidths = tableRow.getColumnsWidths(i, i2);
                int length = columnsWidths.length;
                if (z) {
                    if (this.mMaxWidths == null || this.mMaxWidths.length != length) {
                        this.mMaxWidths = new int[length];
                    }
                    java.lang.System.arraycopy(columnsWidths, 0, this.mMaxWidths, 0, length);
                    z = false;
                } else {
                    int length2 = this.mMaxWidths.length;
                    int i4 = length - length2;
                    if (i4 > 0) {
                        int[] iArr = this.mMaxWidths;
                        this.mMaxWidths = new int[length];
                        java.lang.System.arraycopy(iArr, 0, this.mMaxWidths, 0, iArr.length);
                        java.lang.System.arraycopy(columnsWidths, iArr.length, this.mMaxWidths, iArr.length, i4);
                    }
                    int[] iArr2 = this.mMaxWidths;
                    int min = java.lang.Math.min(length2, length);
                    for (int i5 = 0; i5 < min; i5++) {
                        iArr2[i5] = java.lang.Math.max(iArr2[i5], columnsWidths[i5]);
                    }
                }
            }
        }
    }

    private void shrinkAndStretchColumns(int i) {
        if (this.mMaxWidths == null) {
            return;
        }
        int i2 = 0;
        for (int i3 : this.mMaxWidths) {
            i2 += i3;
        }
        int size = (android.view.View.MeasureSpec.getSize(i) - this.mPaddingLeft) - this.mPaddingRight;
        if (i2 > size && (this.mShrinkAllColumns || this.mShrinkableColumns.size() > 0)) {
            mutateColumnsWidth(this.mShrinkableColumns, this.mShrinkAllColumns, size, i2);
        } else if (i2 < size) {
            if (this.mStretchAllColumns || this.mStretchableColumns.size() > 0) {
                mutateColumnsWidth(this.mStretchableColumns, this.mStretchAllColumns, size, i2);
            }
        }
    }

    private void mutateColumnsWidth(android.util.SparseBooleanArray sparseBooleanArray, boolean z, int i, int i2) {
        int[] iArr = this.mMaxWidths;
        int length = iArr.length;
        int size = z ? length : sparseBooleanArray.size();
        int i3 = (i - i2) / size;
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            android.view.View childAt = getChildAt(i4);
            if (childAt instanceof android.widget.TableRow) {
                childAt.forceLayout();
            }
        }
        if (!z) {
            int i5 = 0;
            for (int i6 = 0; i6 < size; i6++) {
                int keyAt = sparseBooleanArray.keyAt(i6);
                if (sparseBooleanArray.valueAt(i6)) {
                    if (keyAt < length) {
                        iArr[keyAt] = iArr[keyAt] + i3;
                    } else {
                        i5++;
                    }
                }
            }
            if (i5 > 0 && i5 < size) {
                int i7 = (i3 * i5) / (size - i5);
                for (int i8 = 0; i8 < size; i8++) {
                    int keyAt2 = sparseBooleanArray.keyAt(i8);
                    if (sparseBooleanArray.valueAt(i8) && keyAt2 < length) {
                        if (i7 > iArr[keyAt2]) {
                            iArr[keyAt2] = 0;
                        } else {
                            iArr[keyAt2] = iArr[keyAt2] + i7;
                        }
                    }
                }
                return;
            }
            return;
        }
        for (int i9 = 0; i9 < size; i9++) {
            iArr[i9] = iArr[i9] + i3;
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public android.widget.TableLayout.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.widget.TableLayout.LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public android.widget.LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new android.widget.TableLayout.LayoutParams();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof android.widget.TableLayout.LayoutParams;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public android.widget.LinearLayout.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new android.widget.TableLayout.LayoutParams(layoutParams);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.TableLayout.class.getName();
    }

    public static class LayoutParams extends android.widget.LinearLayout.LayoutParams {
        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(-1, i2);
        }

        public LayoutParams(int i, int i2, float f) {
            super(-1, i2, f);
        }

        public LayoutParams() {
            super(-1, -2);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.width = -1;
        }

        public LayoutParams(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.width = -1;
            if (marginLayoutParams instanceof android.widget.TableLayout.LayoutParams) {
                this.weight = ((android.widget.TableLayout.LayoutParams) marginLayoutParams).weight;
            }
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void setBaseAttributes(android.content.res.TypedArray typedArray, int i, int i2) {
            this.width = -1;
            if (typedArray.hasValue(i2)) {
                this.height = typedArray.getLayoutDimension(i2, "layout_height");
            } else {
                this.height = -2;
            }
        }
    }

    private class PassThroughHierarchyChangeListener implements android.view.ViewGroup.OnHierarchyChangeListener {
        private android.view.ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        private PassThroughHierarchyChangeListener() {
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(android.view.View view, android.view.View view2) {
            android.widget.TableLayout.this.trackCollapsedColumns(view2);
            if (this.mOnHierarchyChangeListener != null) {
                this.mOnHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(android.view.View view, android.view.View view2) {
            if (this.mOnHierarchyChangeListener != null) {
                this.mOnHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }
}
