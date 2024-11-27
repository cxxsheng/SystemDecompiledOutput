package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class GridLayout extends android.view.ViewGroup {
    private static final int ALIGNMENT_MODE = 6;
    public static final int ALIGN_BOUNDS = 0;
    public static final int ALIGN_MARGINS = 1;
    private static final int CAN_STRETCH = 2;
    private static final int COLUMN_COUNT = 3;
    private static final int COLUMN_ORDER_PRESERVED = 4;
    private static final int DEFAULT_ALIGNMENT_MODE = 1;
    static final int DEFAULT_CONTAINER_MARGIN = 0;
    private static final int DEFAULT_COUNT = Integer.MIN_VALUE;
    private static final boolean DEFAULT_ORDER_PRESERVED = true;
    private static final int DEFAULT_ORIENTATION = 0;
    private static final boolean DEFAULT_USE_DEFAULT_MARGINS = false;
    public static final int HORIZONTAL = 0;
    private static final int INFLEXIBLE = 0;
    static final int MAX_SIZE = 100000;
    private static final int ORIENTATION = 0;
    private static final int ROW_COUNT = 1;
    private static final int ROW_ORDER_PRESERVED = 2;
    public static final int UNDEFINED = Integer.MIN_VALUE;
    static final int UNINITIALIZED_HASH = 0;
    private static final int USE_DEFAULT_MARGINS = 5;
    public static final int VERTICAL = 1;
    int mAlignmentMode;
    int mDefaultGap;
    final android.widget.GridLayout.Axis mHorizontalAxis;
    int mLastLayoutParamsHashCode;
    int mOrientation;
    android.util.Printer mPrinter;
    boolean mUseDefaultMargins;
    final android.widget.GridLayout.Axis mVerticalAxis;
    static final android.util.Printer LOG_PRINTER = new android.util.LogPrinter(3, android.widget.GridLayout.class.getName());
    static final android.util.Printer NO_PRINTER = new android.util.Printer() { // from class: android.widget.GridLayout.1
        @Override // android.util.Printer
        public void println(java.lang.String str) {
        }
    };
    static final android.widget.GridLayout.Alignment UNDEFINED_ALIGNMENT = new android.widget.GridLayout.Alignment() { // from class: android.widget.GridLayout.2
        @Override // android.widget.GridLayout.Alignment
        int getGravityOffset(android.view.View view, int i) {
            return Integer.MIN_VALUE;
        }

        @Override // android.widget.GridLayout.Alignment
        public int getAlignmentValue(android.view.View view, int i, int i2) {
            return Integer.MIN_VALUE;
        }
    };
    private static final android.widget.GridLayout.Alignment LEADING = new android.widget.GridLayout.Alignment() { // from class: android.widget.GridLayout.3
        @Override // android.widget.GridLayout.Alignment
        int getGravityOffset(android.view.View view, int i) {
            return 0;
        }

        @Override // android.widget.GridLayout.Alignment
        public int getAlignmentValue(android.view.View view, int i, int i2) {
            return 0;
        }
    };
    private static final android.widget.GridLayout.Alignment TRAILING = new android.widget.GridLayout.Alignment() { // from class: android.widget.GridLayout.4
        @Override // android.widget.GridLayout.Alignment
        int getGravityOffset(android.view.View view, int i) {
            return i;
        }

        @Override // android.widget.GridLayout.Alignment
        public int getAlignmentValue(android.view.View view, int i, int i2) {
            return i;
        }
    };
    public static final android.widget.GridLayout.Alignment TOP = LEADING;
    public static final android.widget.GridLayout.Alignment BOTTOM = TRAILING;
    public static final android.widget.GridLayout.Alignment START = LEADING;
    public static final android.widget.GridLayout.Alignment END = TRAILING;
    public static final android.widget.GridLayout.Alignment LEFT = createSwitchingAlignment(START, END);
    public static final android.widget.GridLayout.Alignment RIGHT = createSwitchingAlignment(END, START);
    public static final android.widget.GridLayout.Alignment CENTER = new android.widget.GridLayout.Alignment() { // from class: android.widget.GridLayout.6
        @Override // android.widget.GridLayout.Alignment
        int getGravityOffset(android.view.View view, int i) {
            return i >> 1;
        }

        @Override // android.widget.GridLayout.Alignment
        public int getAlignmentValue(android.view.View view, int i, int i2) {
            return i >> 1;
        }
    };
    public static final android.widget.GridLayout.Alignment BASELINE = new android.widget.GridLayout.Alignment() { // from class: android.widget.GridLayout.7
        @Override // android.widget.GridLayout.Alignment
        int getGravityOffset(android.view.View view, int i) {
            return 0;
        }

        @Override // android.widget.GridLayout.Alignment
        public int getAlignmentValue(android.view.View view, int i, int i2) {
            if (view.getVisibility() == 8) {
                return 0;
            }
            int baseline = view.getBaseline();
            if (baseline == -1) {
                return Integer.MIN_VALUE;
            }
            return baseline;
        }

        @Override // android.widget.GridLayout.Alignment
        public android.widget.GridLayout.Bounds getBounds() {
            return new android.widget.GridLayout.Bounds() { // from class: android.widget.GridLayout.7.1
                private int size;

                @Override // android.widget.GridLayout.Bounds
                protected void reset() {
                    super.reset();
                    this.size = Integer.MIN_VALUE;
                }

                @Override // android.widget.GridLayout.Bounds
                protected void include(int i, int i2) {
                    super.include(i, i2);
                    this.size = java.lang.Math.max(this.size, i + i2);
                }

                @Override // android.widget.GridLayout.Bounds
                protected int size(boolean z) {
                    return java.lang.Math.max(super.size(z), this.size);
                }

                @Override // android.widget.GridLayout.Bounds
                protected int getOffset(android.widget.GridLayout gridLayout, android.view.View view, android.widget.GridLayout.Alignment alignment, int i, boolean z) {
                    return java.lang.Math.max(0, super.getOffset(gridLayout, view, alignment, i, z));
                }
            };
        }
    };
    public static final android.widget.GridLayout.Alignment FILL = new android.widget.GridLayout.Alignment() { // from class: android.widget.GridLayout.8
        @Override // android.widget.GridLayout.Alignment
        int getGravityOffset(android.view.View view, int i) {
            return 0;
        }

        @Override // android.widget.GridLayout.Alignment
        public int getAlignmentValue(android.view.View view, int i, int i2) {
            return Integer.MIN_VALUE;
        }

        @Override // android.widget.GridLayout.Alignment
        public int getSizeInCell(android.view.View view, int i, int i2) {
            return i2;
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AlignmentMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Orientation {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.GridLayout> {
        private int mAlignmentModeId;
        private int mColumnCountId;
        private int mColumnOrderPreservedId;
        private int mOrientationId;
        private boolean mPropertiesMapped = false;
        private int mRowCountId;
        private int mRowOrderPreservedId;
        private int mUseDefaultMarginsId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            sparseArray.put(0, "alignBounds");
            sparseArray.put(1, "alignMargins");
            java.util.Objects.requireNonNull(sparseArray);
            this.mAlignmentModeId = propertyMapper.mapIntEnum("alignmentMode", 16843642, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mColumnCountId = propertyMapper.mapInt("columnCount", 16843639);
            this.mColumnOrderPreservedId = propertyMapper.mapBoolean("columnOrderPreserved", 16843640);
            android.util.SparseArray sparseArray2 = new android.util.SparseArray();
            sparseArray2.put(0, android.app.slice.Slice.HINT_HORIZONTAL);
            sparseArray2.put(1, "vertical");
            java.util.Objects.requireNonNull(sparseArray2);
            this.mOrientationId = propertyMapper.mapIntEnum("orientation", 16842948, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray2));
            this.mRowCountId = propertyMapper.mapInt("rowCount", 16843637);
            this.mRowOrderPreservedId = propertyMapper.mapBoolean("rowOrderPreserved", 16843638);
            this.mUseDefaultMarginsId = propertyMapper.mapBoolean("useDefaultMargins", 16843641);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.GridLayout gridLayout, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readIntEnum(this.mAlignmentModeId, gridLayout.getAlignmentMode());
            propertyReader.readInt(this.mColumnCountId, gridLayout.getColumnCount());
            propertyReader.readBoolean(this.mColumnOrderPreservedId, gridLayout.isColumnOrderPreserved());
            propertyReader.readIntEnum(this.mOrientationId, gridLayout.getOrientation());
            propertyReader.readInt(this.mRowCountId, gridLayout.getRowCount());
            propertyReader.readBoolean(this.mRowOrderPreservedId, gridLayout.isRowOrderPreserved());
            propertyReader.readBoolean(this.mUseDefaultMarginsId, gridLayout.getUseDefaultMargins());
        }
    }

    public GridLayout(android.content.Context context) {
        this(context, null);
    }

    public GridLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GridLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public GridLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mHorizontalAxis = new android.widget.GridLayout.Axis(true);
        this.mVerticalAxis = new android.widget.GridLayout.Axis(false);
        this.mOrientation = 0;
        this.mUseDefaultMargins = false;
        this.mAlignmentMode = 1;
        this.mLastLayoutParamsHashCode = 0;
        this.mPrinter = LOG_PRINTER;
        this.mDefaultGap = context.getResources().getDimensionPixelOffset(com.android.internal.R.dimen.default_gap);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.GridLayout, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.GridLayout, attributeSet, obtainStyledAttributes, i, i2);
        try {
            setRowCount(obtainStyledAttributes.getInt(1, Integer.MIN_VALUE));
            setColumnCount(obtainStyledAttributes.getInt(3, Integer.MIN_VALUE));
            setOrientation(obtainStyledAttributes.getInt(0, 0));
            setUseDefaultMargins(obtainStyledAttributes.getBoolean(5, false));
            setAlignmentMode(obtainStyledAttributes.getInt(6, 1));
            setRowOrderPreserved(obtainStyledAttributes.getBoolean(2, true));
            setColumnOrderPreserved(obtainStyledAttributes.getBoolean(4, true));
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(int i) {
        if (this.mOrientation != i) {
            this.mOrientation = i;
            invalidateStructure();
            requestLayout();
        }
    }

    public int getRowCount() {
        return this.mVerticalAxis.getCount();
    }

    @android.view.RemotableViewMethod
    public void setRowCount(int i) {
        this.mVerticalAxis.setCount(i);
        invalidateStructure();
        requestLayout();
    }

    public int getColumnCount() {
        return this.mHorizontalAxis.getCount();
    }

    @android.view.RemotableViewMethod
    public void setColumnCount(int i) {
        this.mHorizontalAxis.setCount(i);
        invalidateStructure();
        requestLayout();
    }

    public boolean getUseDefaultMargins() {
        return this.mUseDefaultMargins;
    }

    public void setUseDefaultMargins(boolean z) {
        this.mUseDefaultMargins = z;
        requestLayout();
    }

    public int getAlignmentMode() {
        return this.mAlignmentMode;
    }

    @android.view.RemotableViewMethod
    public void setAlignmentMode(int i) {
        this.mAlignmentMode = i;
        requestLayout();
    }

    public boolean isRowOrderPreserved() {
        return this.mVerticalAxis.isOrderPreserved();
    }

    public void setRowOrderPreserved(boolean z) {
        this.mVerticalAxis.setOrderPreserved(z);
        invalidateStructure();
        requestLayout();
    }

    public boolean isColumnOrderPreserved() {
        return this.mHorizontalAxis.isOrderPreserved();
    }

    public void setColumnOrderPreserved(boolean z) {
        this.mHorizontalAxis.setOrderPreserved(z);
        invalidateStructure();
        requestLayout();
    }

    public android.util.Printer getPrinter() {
        return this.mPrinter;
    }

    public void setPrinter(android.util.Printer printer) {
        if (printer == null) {
            printer = NO_PRINTER;
        }
        this.mPrinter = printer;
    }

    static int max2(int[] iArr, int i) {
        for (int i2 : iArr) {
            i = java.lang.Math.max(i, i2);
        }
        return i;
    }

    static <T> T[] append(T[] tArr, T[] tArr2) {
        T[] tArr3 = (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance(tArr.getClass().getComponentType(), tArr.length + tArr2.length));
        java.lang.System.arraycopy(tArr, 0, tArr3, 0, tArr.length);
        java.lang.System.arraycopy(tArr2, 0, tArr3, tArr.length, tArr2.length);
        return tArr3;
    }

    static android.widget.GridLayout.Alignment getAlignment(int i, boolean z) {
        switch ((i & (z ? 7 : 112)) >> (z ? 0 : 4)) {
            case 1:
                return CENTER;
            case 3:
                return z ? LEFT : TOP;
            case 5:
                return z ? RIGHT : BOTTOM;
            case 7:
                return FILL;
            case android.view.Gravity.START /* 8388611 */:
                return START;
            case android.view.Gravity.END /* 8388613 */:
                return END;
            default:
                return UNDEFINED_ALIGNMENT;
        }
    }

    private int getDefaultMargin(android.view.View view, boolean z, boolean z2) {
        if (view.getClass() == android.widget.Space.class) {
            return 0;
        }
        return this.mDefaultGap / 2;
    }

    private int getDefaultMargin(android.view.View view, boolean z, boolean z2, boolean z3) {
        return getDefaultMargin(view, z2, z3);
    }

    private int getDefaultMargin(android.view.View view, android.widget.GridLayout.LayoutParams layoutParams, boolean z, boolean z2) {
        boolean z3;
        boolean z4 = false;
        if (!this.mUseDefaultMargins) {
            return 0;
        }
        android.widget.GridLayout.Spec spec = z ? layoutParams.columnSpec : layoutParams.rowSpec;
        android.widget.GridLayout.Axis axis = z ? this.mHorizontalAxis : this.mVerticalAxis;
        android.widget.GridLayout.Interval interval = spec.span;
        if (z && isLayoutRtl()) {
            z3 = !z2;
        } else {
            z3 = z2;
        }
        if (!z3 ? interval.max == axis.getCount() : interval.min == 0) {
            z4 = true;
        }
        return getDefaultMargin(view, z4, z, z2);
    }

    int getMargin1(android.view.View view, boolean z, boolean z2) {
        int i;
        android.widget.GridLayout.LayoutParams layoutParams = getLayoutParams(view);
        if (z) {
            i = z2 ? layoutParams.leftMargin : layoutParams.rightMargin;
        } else {
            i = z2 ? layoutParams.topMargin : layoutParams.bottomMargin;
        }
        return i == Integer.MIN_VALUE ? getDefaultMargin(view, layoutParams, z, z2) : i;
    }

    private int getMargin(android.view.View view, boolean z, boolean z2) {
        if (this.mAlignmentMode == 1) {
            return getMargin1(view, z, z2);
        }
        android.widget.GridLayout.Axis axis = z ? this.mHorizontalAxis : this.mVerticalAxis;
        int[] leadingMargins = z2 ? axis.getLeadingMargins() : axis.getTrailingMargins();
        android.widget.GridLayout.LayoutParams layoutParams = getLayoutParams(view);
        android.widget.GridLayout.Interval interval = (z ? layoutParams.columnSpec : layoutParams.rowSpec).span;
        return leadingMargins[z2 ? interval.min : interval.max];
    }

    private int getTotalMargin(android.view.View view, boolean z) {
        return getMargin(view, z, true) + getMargin(view, z, false);
    }

    private static boolean fits(int[] iArr, int i, int i2, int i3) {
        if (i3 > iArr.length) {
            return false;
        }
        while (i2 < i3) {
            if (iArr[i2] > i) {
                return false;
            }
            i2++;
        }
        return true;
    }

    private static void procrusteanFill(int[] iArr, int i, int i2, int i3) {
        int length = iArr.length;
        java.util.Arrays.fill(iArr, java.lang.Math.min(i, length), java.lang.Math.min(i2, length), i3);
    }

    private static void setCellGroup(android.widget.GridLayout.LayoutParams layoutParams, int i, int i2, int i3, int i4) {
        layoutParams.setRowSpecSpan(new android.widget.GridLayout.Interval(i, i2 + i));
        layoutParams.setColumnSpecSpan(new android.widget.GridLayout.Interval(i3, i4 + i3));
    }

    private static int clip(android.widget.GridLayout.Interval interval, boolean z, int i) {
        int size = interval.size();
        if (i == 0) {
            return size;
        }
        return java.lang.Math.min(size, i - (z ? java.lang.Math.min(interval.min, i) : 0));
    }

    private void validateLayoutParams() {
        boolean z = this.mOrientation == 0;
        android.widget.GridLayout.Axis axis = z ? this.mHorizontalAxis : this.mVerticalAxis;
        int i = axis.definedCount != Integer.MIN_VALUE ? axis.definedCount : 0;
        int[] iArr = new int[i];
        int childCount = getChildCount();
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            android.widget.GridLayout.LayoutParams layoutParams = (android.widget.GridLayout.LayoutParams) getChildAt(i4).getLayoutParams();
            android.widget.GridLayout.Spec spec = z ? layoutParams.rowSpec : layoutParams.columnSpec;
            android.widget.GridLayout.Interval interval = spec.span;
            boolean z2 = spec.startDefined;
            int size = interval.size();
            if (z2) {
                i2 = interval.min;
            }
            android.widget.GridLayout.Spec spec2 = z ? layoutParams.columnSpec : layoutParams.rowSpec;
            android.widget.GridLayout.Interval interval2 = spec2.span;
            boolean z3 = spec2.startDefined;
            int clip = clip(interval2, z3, i);
            if (z3) {
                i3 = interval2.min;
            }
            if (i != 0) {
                if (!z2 || !z3) {
                    while (true) {
                        int i5 = i3 + clip;
                        if (fits(iArr, i2, i3, i5)) {
                            break;
                        }
                        if (z3) {
                            i2++;
                        } else if (i5 <= i) {
                            i3++;
                        } else {
                            i2++;
                            i3 = 0;
                        }
                    }
                }
                procrusteanFill(iArr, i3, i3 + clip, i2 + size);
            }
            if (z) {
                setCellGroup(layoutParams, i2, size, i3, clip);
            } else {
                setCellGroup(layoutParams, i3, clip, i2, size);
            }
            i3 += clip;
        }
    }

    private void invalidateStructure() {
        this.mLastLayoutParamsHashCode = 0;
        this.mHorizontalAxis.invalidateStructure();
        this.mVerticalAxis.invalidateStructure();
        invalidateValues();
    }

    private void invalidateValues() {
        if (this.mHorizontalAxis != null && this.mVerticalAxis != null) {
            this.mHorizontalAxis.invalidateValues();
            this.mVerticalAxis.invalidateValues();
        }
    }

    @Override // android.view.ViewGroup
    protected void onSetLayoutParams(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        super.onSetLayoutParams(view, layoutParams);
        if (!checkLayoutParams(layoutParams)) {
            handleInvalidParams("supplied LayoutParams are of the wrong type");
        }
        invalidateStructure();
    }

    final android.widget.GridLayout.LayoutParams getLayoutParams(android.view.View view) {
        return (android.widget.GridLayout.LayoutParams) view.getLayoutParams();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void handleInvalidParams(java.lang.String str) {
        throw new java.lang.IllegalArgumentException(str + ". ");
    }

    private void checkLayoutParams(android.widget.GridLayout.LayoutParams layoutParams, boolean z) {
        java.lang.String str = z ? "column" : "row";
        android.widget.GridLayout.Interval interval = (z ? layoutParams.columnSpec : layoutParams.rowSpec).span;
        if (interval.min != Integer.MIN_VALUE && interval.min < 0) {
            handleInvalidParams(str + " indices must be positive");
        }
        int i = (z ? this.mHorizontalAxis : this.mVerticalAxis).definedCount;
        if (i != Integer.MIN_VALUE) {
            if (interval.max > i) {
                handleInvalidParams(str + " indices (start + span) mustn't exceed the " + str + " count");
            }
            if (interval.size() > i) {
                handleInvalidParams(str + " span mustn't exceed the " + str + " count");
            }
        }
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof android.widget.GridLayout.LayoutParams)) {
            return false;
        }
        android.widget.GridLayout.LayoutParams layoutParams2 = (android.widget.GridLayout.LayoutParams) layoutParams;
        checkLayoutParams(layoutParams2, true);
        checkLayoutParams(layoutParams2, false);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public android.widget.GridLayout.LayoutParams generateDefaultLayoutParams() {
        return new android.widget.GridLayout.LayoutParams();
    }

    @Override // android.view.ViewGroup
    public android.widget.GridLayout.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.widget.GridLayout.LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public android.widget.GridLayout.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (sPreserveMarginParamsInLayoutParamConversion) {
            if (layoutParams instanceof android.widget.GridLayout.LayoutParams) {
                return new android.widget.GridLayout.LayoutParams((android.widget.GridLayout.LayoutParams) layoutParams);
            }
            if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
                return new android.widget.GridLayout.LayoutParams((android.view.ViewGroup.MarginLayoutParams) layoutParams);
            }
        }
        return new android.widget.GridLayout.LayoutParams(layoutParams);
    }

    private void drawLine(android.graphics.Canvas canvas, int i, int i2, int i3, int i4, android.graphics.Paint paint) {
        if (isLayoutRtl()) {
            int width = getWidth();
            canvas.drawLine(width - i, i2, width - i3, i4, paint);
        } else {
            canvas.drawLine(i, i2, i3, i4, paint);
        }
    }

    @Override // android.view.ViewGroup
    protected void onDebugDrawMargins(android.graphics.Canvas canvas, android.graphics.Paint paint) {
        android.widget.GridLayout.LayoutParams layoutParams = new android.widget.GridLayout.LayoutParams();
        for (int i = 0; i < getChildCount(); i++) {
            android.view.View childAt = getChildAt(i);
            layoutParams.setMargins(getMargin1(childAt, true, true), getMargin1(childAt, false, true), getMargin1(childAt, true, false), getMargin1(childAt, false, false));
            layoutParams.onDebugDraw(childAt, canvas, paint);
        }
    }

    @Override // android.view.ViewGroup
    protected void onDebugDraw(android.graphics.Canvas canvas) {
        android.graphics.Paint paint = new android.graphics.Paint();
        paint.setStyle(android.graphics.Paint.Style.STROKE);
        paint.setColor(android.graphics.Color.argb(50, 255, 255, 255));
        android.graphics.Insets opticalInsets = getOpticalInsets();
        int paddingTop = getPaddingTop() + opticalInsets.top;
        int paddingLeft = getPaddingLeft() + opticalInsets.left;
        int width = (getWidth() - getPaddingRight()) - opticalInsets.right;
        int height = (getHeight() - getPaddingBottom()) - opticalInsets.bottom;
        int[] iArr = this.mHorizontalAxis.locations;
        if (iArr != null) {
            for (int i : iArr) {
                int i2 = paddingLeft + i;
                drawLine(canvas, i2, paddingTop, i2, height, paint);
            }
        }
        int[] iArr2 = this.mVerticalAxis.locations;
        if (iArr2 != null) {
            for (int i3 : iArr2) {
                int i4 = paddingTop + i3;
                drawLine(canvas, paddingLeft, i4, width, i4, paint);
            }
        }
        super.onDebugDraw(canvas);
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(android.view.View view) {
        super.onViewAdded(view);
        invalidateStructure();
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(android.view.View view) {
        super.onViewRemoved(view);
        invalidateStructure();
    }

    @Override // android.view.ViewGroup
    protected void onChildVisibilityChanged(android.view.View view, int i, int i2) {
        super.onChildVisibilityChanged(view, i, i2);
        if (i == 8 || i2 == 8) {
            invalidateStructure();
        }
    }

    private int computeLayoutParamsHashCode() {
        int childCount = getChildCount();
        int i = 1;
        for (int i2 = 0; i2 < childCount; i2++) {
            android.view.View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                i = (i * 31) + ((android.widget.GridLayout.LayoutParams) childAt.getLayoutParams()).hashCode();
            }
        }
        return i;
    }

    private void consistencyCheck() {
        if (this.mLastLayoutParamsHashCode == 0) {
            validateLayoutParams();
            this.mLastLayoutParamsHashCode = computeLayoutParamsHashCode();
        } else if (this.mLastLayoutParamsHashCode != computeLayoutParamsHashCode()) {
            this.mPrinter.println("The fields of some layout parameters were modified in between layout operations. Check the javadoc for GridLayout.LayoutParams#rowSpec.");
            invalidateStructure();
            consistencyCheck();
        }
    }

    private void measureChildWithMargins2(android.view.View view, int i, int i2, int i3, int i4) {
        view.measure(getChildMeasureSpec(i, getTotalMargin(view, true), i3), getChildMeasureSpec(i2, getTotalMargin(view, false), i4));
    }

    private void measureChildrenWithMargins(int i, int i2, boolean z) {
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            android.view.View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                android.widget.GridLayout.LayoutParams layoutParams = getLayoutParams(childAt);
                if (z) {
                    measureChildWithMargins2(childAt, i, i2, layoutParams.width, layoutParams.height);
                } else {
                    boolean z2 = this.mOrientation == 0;
                    android.widget.GridLayout.Spec spec = z2 ? layoutParams.columnSpec : layoutParams.rowSpec;
                    if (spec.getAbsoluteAlignment(z2) == FILL) {
                        android.widget.GridLayout.Interval interval = spec.span;
                        int[] locations = (z2 ? this.mHorizontalAxis : this.mVerticalAxis).getLocations();
                        int totalMargin = (locations[interval.max] - locations[interval.min]) - getTotalMargin(childAt, z2);
                        if (z2) {
                            measureChildWithMargins2(childAt, i, i2, totalMargin, layoutParams.height);
                        } else {
                            measureChildWithMargins2(childAt, i, i2, layoutParams.width, totalMargin);
                        }
                    }
                }
            }
        }
    }

    static int adjust(int i, int i2) {
        return android.view.View.MeasureSpec.makeMeasureSpec(android.view.View.MeasureSpec.getSize(i2 + i), android.view.View.MeasureSpec.getMode(i));
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int measure;
        int i3;
        consistencyCheck();
        invalidateValues();
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int adjust = adjust(i, -paddingLeft);
        int adjust2 = adjust(i2, -paddingTop);
        measureChildrenWithMargins(adjust, adjust2, true);
        if (this.mOrientation == 0) {
            measure = this.mHorizontalAxis.getMeasure(adjust);
            measureChildrenWithMargins(adjust, adjust2, false);
            i3 = this.mVerticalAxis.getMeasure(adjust2);
        } else {
            int measure2 = this.mVerticalAxis.getMeasure(adjust2);
            measureChildrenWithMargins(adjust, adjust2, false);
            measure = this.mHorizontalAxis.getMeasure(adjust);
            i3 = measure2;
        }
        setMeasuredDimension(resolveSizeAndState(java.lang.Math.max(measure + paddingLeft, getSuggestedMinimumWidth()), i, 0), resolveSizeAndState(java.lang.Math.max(i3 + paddingTop, getSuggestedMinimumHeight()), i2, 0));
    }

    private int getMeasurement(android.view.View view, boolean z) {
        return z ? view.getMeasuredWidth() : view.getMeasuredHeight();
    }

    final int getMeasurementIncludingMargin(android.view.View view, boolean z) {
        if (view.getVisibility() == 8) {
            return 0;
        }
        return getMeasurement(view, z) + getTotalMargin(view, z);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        super.requestLayout();
        invalidateValues();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int[] iArr;
        android.widget.GridLayout gridLayout = this;
        consistencyCheck();
        int i5 = i3 - i;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        gridLayout.mHorizontalAxis.layout((i5 - paddingLeft) - paddingRight);
        gridLayout.mVerticalAxis.layout(((i4 - i2) - paddingTop) - paddingBottom);
        int[] locations = gridLayout.mHorizontalAxis.getLocations();
        int[] locations2 = gridLayout.mVerticalAxis.getLocations();
        int childCount = getChildCount();
        boolean z2 = false;
        int i6 = 0;
        while (i6 < childCount) {
            android.view.View childAt = gridLayout.getChildAt(i6);
            if (childAt.getVisibility() == 8) {
                iArr = locations;
            } else {
                android.widget.GridLayout.LayoutParams layoutParams = gridLayout.getLayoutParams(childAt);
                android.widget.GridLayout.Spec spec = layoutParams.columnSpec;
                android.widget.GridLayout.Spec spec2 = layoutParams.rowSpec;
                android.widget.GridLayout.Interval interval = spec.span;
                android.widget.GridLayout.Interval interval2 = spec2.span;
                int i7 = locations[interval.min];
                int i8 = locations2[interval2.min];
                int i9 = locations[interval.max] - i7;
                int i10 = locations2[interval2.max] - i8;
                int measurement = gridLayout.getMeasurement(childAt, true);
                int measurement2 = gridLayout.getMeasurement(childAt, z2);
                android.widget.GridLayout.Alignment absoluteAlignment = spec.getAbsoluteAlignment(true);
                android.widget.GridLayout.Alignment absoluteAlignment2 = spec2.getAbsoluteAlignment(z2);
                android.widget.GridLayout.Bounds value = gridLayout.mHorizontalAxis.getGroupBounds().getValue(i6);
                android.widget.GridLayout.Bounds value2 = gridLayout.mVerticalAxis.getGroupBounds().getValue(i6);
                int gravityOffset = absoluteAlignment.getGravityOffset(childAt, i9 - value.size(true));
                int gravityOffset2 = absoluteAlignment2.getGravityOffset(childAt, i10 - value2.size(true));
                int margin = gridLayout.getMargin(childAt, true, true);
                iArr = locations;
                int margin2 = gridLayout.getMargin(childAt, false, true);
                int margin3 = gridLayout.getMargin(childAt, true, false);
                int i11 = margin + margin3;
                int margin4 = margin2 + gridLayout.getMargin(childAt, false, false);
                int offset = value.getOffset(this, childAt, absoluteAlignment, measurement + i11, true);
                int offset2 = value2.getOffset(this, childAt, absoluteAlignment2, measurement2 + margin4, false);
                int sizeInCell = absoluteAlignment.getSizeInCell(childAt, measurement, i9 - i11);
                int sizeInCell2 = absoluteAlignment2.getSizeInCell(childAt, measurement2, i10 - margin4);
                int i12 = i7 + gravityOffset + offset;
                int i13 = !isLayoutRtl() ? paddingLeft + margin + i12 : (((i5 - sizeInCell) - paddingRight) - margin3) - i12;
                int i14 = paddingTop + i8 + gravityOffset2 + offset2 + margin2;
                if (sizeInCell != childAt.getMeasuredWidth() || sizeInCell2 != childAt.getMeasuredHeight()) {
                    childAt.measure(android.view.View.MeasureSpec.makeMeasureSpec(sizeInCell, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(sizeInCell2, 1073741824));
                }
                childAt.layout(i13, i14, sizeInCell + i13, sizeInCell2 + i14);
            }
            i6++;
            z2 = false;
            gridLayout = this;
            locations = iArr;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.GridLayout.class.getName();
    }

    final class Axis {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int COMPLETE = 2;
        private static final int NEW = 0;
        private static final int PENDING = 1;
        public android.widget.GridLayout.Arc[] arcs;
        public boolean arcsValid;
        android.widget.GridLayout.PackedMap<android.widget.GridLayout.Interval, android.widget.GridLayout.MutableInt> backwardLinks;
        public boolean backwardLinksValid;
        public int definedCount;
        public int[] deltas;
        android.widget.GridLayout.PackedMap<android.widget.GridLayout.Interval, android.widget.GridLayout.MutableInt> forwardLinks;
        public boolean forwardLinksValid;
        android.widget.GridLayout.PackedMap<android.widget.GridLayout.Spec, android.widget.GridLayout.Bounds> groupBounds;
        public boolean groupBoundsValid;
        public boolean hasWeights;
        public boolean hasWeightsValid;
        public final boolean horizontal;
        public int[] leadingMargins;
        public boolean leadingMarginsValid;
        public int[] locations;
        public boolean locationsValid;
        private int maxIndex;
        boolean orderPreserved;
        private android.widget.GridLayout.MutableInt parentMax;
        private android.widget.GridLayout.MutableInt parentMin;
        public int[] trailingMargins;
        public boolean trailingMarginsValid;

        private Axis(boolean z) {
            this.definedCount = Integer.MIN_VALUE;
            this.maxIndex = Integer.MIN_VALUE;
            this.groupBoundsValid = false;
            this.forwardLinksValid = false;
            this.backwardLinksValid = false;
            this.leadingMarginsValid = false;
            this.trailingMarginsValid = false;
            this.arcsValid = false;
            this.locationsValid = false;
            this.hasWeightsValid = false;
            this.orderPreserved = true;
            this.parentMin = new android.widget.GridLayout.MutableInt(0);
            this.parentMax = new android.widget.GridLayout.MutableInt(-100000);
            this.horizontal = z;
        }

        private int calculateMaxIndex() {
            int childCount = android.widget.GridLayout.this.getChildCount();
            int i = -1;
            for (int i2 = 0; i2 < childCount; i2++) {
                android.widget.GridLayout.LayoutParams layoutParams = android.widget.GridLayout.this.getLayoutParams(android.widget.GridLayout.this.getChildAt(i2));
                android.widget.GridLayout.Interval interval = (this.horizontal ? layoutParams.columnSpec : layoutParams.rowSpec).span;
                i = java.lang.Math.max(java.lang.Math.max(java.lang.Math.max(i, interval.min), interval.max), interval.size());
            }
            if (i == -1) {
                return Integer.MIN_VALUE;
            }
            return i;
        }

        private int getMaxIndex() {
            if (this.maxIndex == Integer.MIN_VALUE) {
                this.maxIndex = java.lang.Math.max(0, calculateMaxIndex());
            }
            return this.maxIndex;
        }

        public int getCount() {
            return java.lang.Math.max(this.definedCount, getMaxIndex());
        }

        public void setCount(int i) {
            if (i != Integer.MIN_VALUE && i < getMaxIndex()) {
                android.widget.GridLayout.handleInvalidParams((this.horizontal ? "column" : "row") + "Count must be greater than or equal to the maximum of all grid indices (and spans) defined in the LayoutParams of each child");
            }
            this.definedCount = i;
        }

        public boolean isOrderPreserved() {
            return this.orderPreserved;
        }

        public void setOrderPreserved(boolean z) {
            this.orderPreserved = z;
            invalidateStructure();
        }

        private android.widget.GridLayout.PackedMap<android.widget.GridLayout.Spec, android.widget.GridLayout.Bounds> createGroupBounds() {
            android.widget.GridLayout.Assoc of = android.widget.GridLayout.Assoc.of(android.widget.GridLayout.Spec.class, android.widget.GridLayout.Bounds.class);
            int childCount = android.widget.GridLayout.this.getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.widget.GridLayout.LayoutParams layoutParams = android.widget.GridLayout.this.getLayoutParams(android.widget.GridLayout.this.getChildAt(i));
                android.widget.GridLayout.Spec spec = this.horizontal ? layoutParams.columnSpec : layoutParams.rowSpec;
                of.put(spec, spec.getAbsoluteAlignment(this.horizontal).getBounds());
            }
            return of.pack();
        }

        private void computeGroupBounds() {
            for (android.widget.GridLayout.Bounds bounds : this.groupBounds.values) {
                bounds.reset();
            }
            int childCount = android.widget.GridLayout.this.getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = android.widget.GridLayout.this.getChildAt(i);
                android.widget.GridLayout.LayoutParams layoutParams = android.widget.GridLayout.this.getLayoutParams(childAt);
                android.widget.GridLayout.Spec spec = this.horizontal ? layoutParams.columnSpec : layoutParams.rowSpec;
                this.groupBounds.getValue(i).include(android.widget.GridLayout.this, childAt, spec, this, android.widget.GridLayout.this.getMeasurementIncludingMargin(childAt, this.horizontal) + (spec.weight == 0.0f ? 0 : getDeltas()[i]));
            }
        }

        public android.widget.GridLayout.PackedMap<android.widget.GridLayout.Spec, android.widget.GridLayout.Bounds> getGroupBounds() {
            if (this.groupBounds == null) {
                this.groupBounds = createGroupBounds();
            }
            if (!this.groupBoundsValid) {
                computeGroupBounds();
                this.groupBoundsValid = true;
            }
            return this.groupBounds;
        }

        private android.widget.GridLayout.PackedMap<android.widget.GridLayout.Interval, android.widget.GridLayout.MutableInt> createLinks(boolean z) {
            android.widget.GridLayout.Assoc of = android.widget.GridLayout.Assoc.of(android.widget.GridLayout.Interval.class, android.widget.GridLayout.MutableInt.class);
            android.widget.GridLayout.Spec[] specArr = getGroupBounds().keys;
            int length = specArr.length;
            for (int i = 0; i < length; i++) {
                of.put(z ? specArr[i].span : specArr[i].span.inverse(), new android.widget.GridLayout.MutableInt());
            }
            return of.pack();
        }

        private void computeLinks(android.widget.GridLayout.PackedMap<android.widget.GridLayout.Interval, android.widget.GridLayout.MutableInt> packedMap, boolean z) {
            for (android.widget.GridLayout.MutableInt mutableInt : packedMap.values) {
                mutableInt.reset();
            }
            android.widget.GridLayout.Bounds[] boundsArr = getGroupBounds().values;
            for (int i = 0; i < boundsArr.length; i++) {
                int size = boundsArr[i].size(z);
                android.widget.GridLayout.MutableInt value = packedMap.getValue(i);
                int i2 = value.value;
                if (!z) {
                    size = -size;
                }
                value.value = java.lang.Math.max(i2, size);
            }
        }

        private android.widget.GridLayout.PackedMap<android.widget.GridLayout.Interval, android.widget.GridLayout.MutableInt> getForwardLinks() {
            if (this.forwardLinks == null) {
                this.forwardLinks = createLinks(true);
            }
            if (!this.forwardLinksValid) {
                computeLinks(this.forwardLinks, true);
                this.forwardLinksValid = true;
            }
            return this.forwardLinks;
        }

        private android.widget.GridLayout.PackedMap<android.widget.GridLayout.Interval, android.widget.GridLayout.MutableInt> getBackwardLinks() {
            if (this.backwardLinks == null) {
                this.backwardLinks = createLinks(false);
            }
            if (!this.backwardLinksValid) {
                computeLinks(this.backwardLinks, false);
                this.backwardLinksValid = true;
            }
            return this.backwardLinks;
        }

        private void include(java.util.List<android.widget.GridLayout.Arc> list, android.widget.GridLayout.Interval interval, android.widget.GridLayout.MutableInt mutableInt, boolean z) {
            if (interval.size() == 0) {
                return;
            }
            if (z) {
                java.util.Iterator<android.widget.GridLayout.Arc> it = list.iterator();
                while (it.hasNext()) {
                    if (it.next().span.equals(interval)) {
                        return;
                    }
                }
            }
            list.add(new android.widget.GridLayout.Arc(interval, mutableInt));
        }

        private void include(java.util.List<android.widget.GridLayout.Arc> list, android.widget.GridLayout.Interval interval, android.widget.GridLayout.MutableInt mutableInt) {
            include(list, interval, mutableInt, true);
        }

        android.widget.GridLayout.Arc[][] groupArcsByFirstVertex(android.widget.GridLayout.Arc[] arcArr) {
            int count = getCount() + 1;
            android.widget.GridLayout.Arc[][] arcArr2 = new android.widget.GridLayout.Arc[count][];
            int[] iArr = new int[count];
            for (android.widget.GridLayout.Arc arc : arcArr) {
                int i = arc.span.min;
                iArr[i] = iArr[i] + 1;
            }
            for (int i2 = 0; i2 < count; i2++) {
                arcArr2[i2] = new android.widget.GridLayout.Arc[iArr[i2]];
            }
            java.util.Arrays.fill(iArr, 0);
            for (android.widget.GridLayout.Arc arc2 : arcArr) {
                int i3 = arc2.span.min;
                android.widget.GridLayout.Arc[] arcArr3 = arcArr2[i3];
                int i4 = iArr[i3];
                iArr[i3] = i4 + 1;
                arcArr3[i4] = arc2;
            }
            return arcArr2;
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [android.widget.GridLayout$Axis$1] */
        private android.widget.GridLayout.Arc[] topologicalSort(final android.widget.GridLayout.Arc[] arcArr) {
            return new java.lang.Object() { // from class: android.widget.GridLayout.Axis.1
                static final /* synthetic */ boolean $assertionsDisabled = false;
                android.widget.GridLayout.Arc[][] arcsByVertex;
                int cursor;
                android.widget.GridLayout.Arc[] result;
                int[] visited;

                {
                    this.result = new android.widget.GridLayout.Arc[arcArr.length];
                    this.cursor = this.result.length - 1;
                    this.arcsByVertex = android.widget.GridLayout.Axis.this.groupArcsByFirstVertex(arcArr);
                    this.visited = new int[android.widget.GridLayout.Axis.this.getCount() + 1];
                }

                void walk(int i) {
                    switch (this.visited[i]) {
                        case 0:
                            this.visited[i] = 1;
                            for (android.widget.GridLayout.Arc arc : this.arcsByVertex[i]) {
                                walk(arc.span.max);
                                android.widget.GridLayout.Arc[] arcArr2 = this.result;
                                int i2 = this.cursor;
                                this.cursor = i2 - 1;
                                arcArr2[i2] = arc;
                            }
                            this.visited[i] = 2;
                            break;
                    }
                }

                android.widget.GridLayout.Arc[] sort() {
                    int length = this.arcsByVertex.length;
                    for (int i = 0; i < length; i++) {
                        walk(i);
                    }
                    return this.result;
                }
            }.sort();
        }

        private android.widget.GridLayout.Arc[] topologicalSort(java.util.List<android.widget.GridLayout.Arc> list) {
            return topologicalSort((android.widget.GridLayout.Arc[]) list.toArray(new android.widget.GridLayout.Arc[list.size()]));
        }

        private void addComponentSizes(java.util.List<android.widget.GridLayout.Arc> list, android.widget.GridLayout.PackedMap<android.widget.GridLayout.Interval, android.widget.GridLayout.MutableInt> packedMap) {
            for (int i = 0; i < packedMap.keys.length; i++) {
                include(list, packedMap.keys[i], packedMap.values[i], false);
            }
        }

        private android.widget.GridLayout.Arc[] createArcs() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            addComponentSizes(arrayList, getForwardLinks());
            addComponentSizes(arrayList2, getBackwardLinks());
            if (this.orderPreserved) {
                int i = 0;
                while (i < getCount()) {
                    int i2 = i + 1;
                    include(arrayList, new android.widget.GridLayout.Interval(i, i2), new android.widget.GridLayout.MutableInt(0));
                    i = i2;
                }
            }
            int count = getCount();
            include(arrayList, new android.widget.GridLayout.Interval(0, count), this.parentMin, false);
            include(arrayList2, new android.widget.GridLayout.Interval(count, 0), this.parentMax, false);
            return (android.widget.GridLayout.Arc[]) android.widget.GridLayout.append(topologicalSort(arrayList), topologicalSort(arrayList2));
        }

        private void computeArcs() {
            getForwardLinks();
            getBackwardLinks();
        }

        public android.widget.GridLayout.Arc[] getArcs() {
            if (this.arcs == null) {
                this.arcs = createArcs();
            }
            if (!this.arcsValid) {
                computeArcs();
                this.arcsValid = true;
            }
            return this.arcs;
        }

        private boolean relax(int[] iArr, android.widget.GridLayout.Arc arc) {
            if (!arc.valid) {
                return false;
            }
            android.widget.GridLayout.Interval interval = arc.span;
            int i = interval.min;
            int i2 = interval.max;
            int i3 = iArr[i] + arc.value.value;
            if (i3 <= iArr[i2]) {
                return false;
            }
            iArr[i2] = i3;
            return true;
        }

        private void init(int[] iArr) {
            java.util.Arrays.fill(iArr, 0);
        }

        private java.lang.String arcsToString(java.util.List<android.widget.GridLayout.Arc> list) {
            java.lang.String str = this.horizontal ? "x" : "y";
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            boolean z = true;
            for (android.widget.GridLayout.Arc arc : list) {
                if (z) {
                    z = false;
                } else {
                    sb = sb.append(", ");
                }
                int i = arc.span.min;
                int i2 = arc.span.max;
                int i3 = arc.value.value;
                sb.append(i < i2 ? str + i2 + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + str + i + ">=" + i3 : str + i + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + str + i2 + "<=" + (-i3));
            }
            return sb.toString();
        }

        private void logError(java.lang.String str, android.widget.GridLayout.Arc[] arcArr, boolean[] zArr) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            for (int i = 0; i < arcArr.length; i++) {
                android.widget.GridLayout.Arc arc = arcArr[i];
                if (zArr[i]) {
                    arrayList.add(arc);
                }
                if (!arc.valid) {
                    arrayList2.add(arc);
                }
            }
            android.widget.GridLayout.this.mPrinter.println(str + " constraints: " + arcsToString(arrayList) + " are inconsistent; permanently removing: " + arcsToString(arrayList2) + ". ");
        }

        private boolean solve(android.widget.GridLayout.Arc[] arcArr, int[] iArr) {
            return solve(arcArr, iArr, true);
        }

        private boolean solve(android.widget.GridLayout.Arc[] arcArr, int[] iArr, boolean z) {
            java.lang.String str = this.horizontal ? android.app.slice.Slice.HINT_HORIZONTAL : "vertical";
            int count = getCount() + 1;
            boolean[] zArr = null;
            for (int i = 0; i < arcArr.length; i++) {
                init(iArr);
                for (int i2 = 0; i2 < count; i2++) {
                    boolean z2 = false;
                    for (android.widget.GridLayout.Arc arc : arcArr) {
                        z2 |= relax(iArr, arc);
                    }
                    if (!z2) {
                        if (zArr != null) {
                            logError(str, arcArr, zArr);
                        }
                        return true;
                    }
                }
                if (!z) {
                    return false;
                }
                boolean[] zArr2 = new boolean[arcArr.length];
                for (int i3 = 0; i3 < count; i3++) {
                    int length = arcArr.length;
                    for (int i4 = 0; i4 < length; i4++) {
                        zArr2[i4] = zArr2[i4] | relax(iArr, arcArr[i4]);
                    }
                }
                if (i == 0) {
                    zArr = zArr2;
                }
                int i5 = 0;
                while (true) {
                    if (i5 >= arcArr.length) {
                        break;
                    }
                    if (zArr2[i5]) {
                        android.widget.GridLayout.Arc arc2 = arcArr[i5];
                        if (arc2.span.min >= arc2.span.max) {
                            arc2.valid = false;
                            break;
                        }
                    }
                    i5++;
                }
            }
            return true;
        }

        private void computeMargins(boolean z) {
            int[] iArr = z ? this.leadingMargins : this.trailingMargins;
            int childCount = android.widget.GridLayout.this.getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = android.widget.GridLayout.this.getChildAt(i);
                if (childAt.getVisibility() != 8) {
                    android.widget.GridLayout.LayoutParams layoutParams = android.widget.GridLayout.this.getLayoutParams(childAt);
                    android.widget.GridLayout.Interval interval = (this.horizontal ? layoutParams.columnSpec : layoutParams.rowSpec).span;
                    int i2 = z ? interval.min : interval.max;
                    iArr[i2] = java.lang.Math.max(iArr[i2], android.widget.GridLayout.this.getMargin1(childAt, this.horizontal, z));
                }
            }
        }

        public int[] getLeadingMargins() {
            if (this.leadingMargins == null) {
                this.leadingMargins = new int[getCount() + 1];
            }
            if (!this.leadingMarginsValid) {
                computeMargins(true);
                this.leadingMarginsValid = true;
            }
            return this.leadingMargins;
        }

        public int[] getTrailingMargins() {
            if (this.trailingMargins == null) {
                this.trailingMargins = new int[getCount() + 1];
            }
            if (!this.trailingMarginsValid) {
                computeMargins(false);
                this.trailingMarginsValid = true;
            }
            return this.trailingMargins;
        }

        private boolean solve(int[] iArr) {
            return solve(getArcs(), iArr);
        }

        private boolean computeHasWeights() {
            int childCount = android.widget.GridLayout.this.getChildCount();
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = android.widget.GridLayout.this.getChildAt(i);
                if (childAt.getVisibility() != 8) {
                    android.widget.GridLayout.LayoutParams layoutParams = android.widget.GridLayout.this.getLayoutParams(childAt);
                    if ((this.horizontal ? layoutParams.columnSpec : layoutParams.rowSpec).weight != 0.0f) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean hasWeights() {
            if (!this.hasWeightsValid) {
                this.hasWeights = computeHasWeights();
                this.hasWeightsValid = true;
            }
            return this.hasWeights;
        }

        public int[] getDeltas() {
            if (this.deltas == null) {
                this.deltas = new int[android.widget.GridLayout.this.getChildCount()];
            }
            return this.deltas;
        }

        private void shareOutDelta(int i, float f) {
            java.util.Arrays.fill(this.deltas, 0);
            int childCount = android.widget.GridLayout.this.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                android.view.View childAt = android.widget.GridLayout.this.getChildAt(i2);
                if (childAt.getVisibility() != 8) {
                    android.widget.GridLayout.LayoutParams layoutParams = android.widget.GridLayout.this.getLayoutParams(childAt);
                    float f2 = (this.horizontal ? layoutParams.columnSpec : layoutParams.rowSpec).weight;
                    if (f2 != 0.0f) {
                        int round = java.lang.Math.round((i * f2) / f);
                        this.deltas[i2] = round;
                        i -= round;
                        f -= f2;
                    }
                }
            }
        }

        private void solveAndDistributeSpace(int[] iArr) {
            java.util.Arrays.fill(getDeltas(), 0);
            solve(iArr);
            boolean z = true;
            int childCount = (this.parentMin.value * android.widget.GridLayout.this.getChildCount()) + 1;
            if (childCount < 2) {
                return;
            }
            float calculateTotalWeight = calculateTotalWeight();
            int i = -1;
            int i2 = 0;
            while (i2 < childCount) {
                int i3 = (int) ((i2 + childCount) / 2);
                invalidateValues();
                shareOutDelta(i3, calculateTotalWeight);
                boolean solve = solve(getArcs(), iArr, false);
                if (solve) {
                    i2 = i3 + 1;
                    i = i3;
                } else {
                    childCount = i3;
                }
                z = solve;
            }
            if (i > 0 && !z) {
                invalidateValues();
                shareOutDelta(i, calculateTotalWeight);
                solve(iArr);
            }
        }

        private float calculateTotalWeight() {
            int childCount = android.widget.GridLayout.this.getChildCount();
            float f = 0.0f;
            for (int i = 0; i < childCount; i++) {
                android.view.View childAt = android.widget.GridLayout.this.getChildAt(i);
                if (childAt.getVisibility() != 8) {
                    android.widget.GridLayout.LayoutParams layoutParams = android.widget.GridLayout.this.getLayoutParams(childAt);
                    f += (this.horizontal ? layoutParams.columnSpec : layoutParams.rowSpec).weight;
                }
            }
            return f;
        }

        private void computeLocations(int[] iArr) {
            if (!hasWeights()) {
                solve(iArr);
            } else {
                solveAndDistributeSpace(iArr);
            }
            if (!this.orderPreserved) {
                int i = iArr[0];
                int length = iArr.length;
                for (int i2 = 0; i2 < length; i2++) {
                    iArr[i2] = iArr[i2] - i;
                }
            }
        }

        public int[] getLocations() {
            if (this.locations == null) {
                this.locations = new int[getCount() + 1];
            }
            if (!this.locationsValid) {
                computeLocations(this.locations);
                this.locationsValid = true;
            }
            return this.locations;
        }

        private int size(int[] iArr) {
            return iArr[getCount()];
        }

        private void setParentConstraints(int i, int i2) {
            this.parentMin.value = i;
            this.parentMax.value = -i2;
            this.locationsValid = false;
        }

        private int getMeasure(int i, int i2) {
            setParentConstraints(i, i2);
            return size(getLocations());
        }

        public int getMeasure(int i) {
            int mode = android.view.View.MeasureSpec.getMode(i);
            int size = android.view.View.MeasureSpec.getSize(i);
            switch (mode) {
                case Integer.MIN_VALUE:
                    return getMeasure(0, size);
                case 0:
                    return getMeasure(0, 100000);
                case 1073741824:
                    return getMeasure(size, size);
                default:
                    return 0;
            }
        }

        public void layout(int i) {
            setParentConstraints(i, i);
            getLocations();
        }

        public void invalidateStructure() {
            this.maxIndex = Integer.MIN_VALUE;
            this.groupBounds = null;
            this.forwardLinks = null;
            this.backwardLinks = null;
            this.leadingMargins = null;
            this.trailingMargins = null;
            this.arcs = null;
            this.locations = null;
            this.deltas = null;
            this.hasWeightsValid = false;
            invalidateValues();
        }

        public void invalidateValues() {
            this.groupBoundsValid = false;
            this.forwardLinksValid = false;
            this.backwardLinksValid = false;
            this.leadingMarginsValid = false;
            this.trailingMarginsValid = false;
            this.arcsValid = false;
            this.locationsValid = false;
        }
    }

    public static class LayoutParams extends android.view.ViewGroup.MarginLayoutParams {
        private static final int BOTTOM_MARGIN = 6;
        private static final int COLUMN = 1;
        private static final int COLUMN_SPAN = 4;
        private static final int COLUMN_WEIGHT = 6;
        private static final int DEFAULT_COLUMN = Integer.MIN_VALUE;
        private static final int DEFAULT_HEIGHT = -2;
        private static final int DEFAULT_MARGIN = Integer.MIN_VALUE;
        private static final int DEFAULT_ROW = Integer.MIN_VALUE;
        private static final android.widget.GridLayout.Interval DEFAULT_SPAN = new android.widget.GridLayout.Interval(Integer.MIN_VALUE, android.media.AudioSystem.DEVICE_IN_COMMUNICATION);
        private static final int DEFAULT_SPAN_SIZE = DEFAULT_SPAN.size();
        private static final int DEFAULT_WIDTH = -2;
        private static final int GRAVITY = 0;
        private static final int LEFT_MARGIN = 3;
        private static final int MARGIN = 2;
        private static final int RIGHT_MARGIN = 5;
        private static final int ROW = 2;
        private static final int ROW_SPAN = 3;
        private static final int ROW_WEIGHT = 5;
        private static final int TOP_MARGIN = 4;
        public android.widget.GridLayout.Spec columnSpec;
        public android.widget.GridLayout.Spec rowSpec;

        private LayoutParams(int i, int i2, int i3, int i4, int i5, int i6, android.widget.GridLayout.Spec spec, android.widget.GridLayout.Spec spec2) {
            super(i, i2);
            this.rowSpec = android.widget.GridLayout.Spec.UNDEFINED;
            this.columnSpec = android.widget.GridLayout.Spec.UNDEFINED;
            setMargins(i3, i4, i5, i6);
            this.rowSpec = spec;
            this.columnSpec = spec2;
        }

        public LayoutParams(android.widget.GridLayout.Spec spec, android.widget.GridLayout.Spec spec2) {
            this(-2, -2, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, spec, spec2);
        }

        public LayoutParams() {
            this(android.widget.GridLayout.Spec.UNDEFINED, android.widget.GridLayout.Spec.UNDEFINED);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.rowSpec = android.widget.GridLayout.Spec.UNDEFINED;
            this.columnSpec = android.widget.GridLayout.Spec.UNDEFINED;
        }

        public LayoutParams(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.rowSpec = android.widget.GridLayout.Spec.UNDEFINED;
            this.columnSpec = android.widget.GridLayout.Spec.UNDEFINED;
        }

        public LayoutParams(android.widget.GridLayout.LayoutParams layoutParams) {
            super((android.view.ViewGroup.MarginLayoutParams) layoutParams);
            this.rowSpec = android.widget.GridLayout.Spec.UNDEFINED;
            this.columnSpec = android.widget.GridLayout.Spec.UNDEFINED;
            this.rowSpec = layoutParams.rowSpec;
            this.columnSpec = layoutParams.columnSpec;
        }

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.rowSpec = android.widget.GridLayout.Spec.UNDEFINED;
            this.columnSpec = android.widget.GridLayout.Spec.UNDEFINED;
            reInitSuper(context, attributeSet);
            init(context, attributeSet);
        }

        private void reInitSuper(android.content.Context context, android.util.AttributeSet attributeSet) {
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ViewGroup_MarginLayout);
            try {
                int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(2, Integer.MIN_VALUE);
                this.leftMargin = obtainStyledAttributes.getDimensionPixelSize(3, dimensionPixelSize);
                this.topMargin = obtainStyledAttributes.getDimensionPixelSize(4, dimensionPixelSize);
                this.rightMargin = obtainStyledAttributes.getDimensionPixelSize(5, dimensionPixelSize);
                this.bottomMargin = obtainStyledAttributes.getDimensionPixelSize(6, dimensionPixelSize);
            } finally {
                obtainStyledAttributes.recycle();
            }
        }

        private void init(android.content.Context context, android.util.AttributeSet attributeSet) {
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.GridLayout_Layout);
            try {
                int i = obtainStyledAttributes.getInt(0, 0);
                this.columnSpec = android.widget.GridLayout.spec(obtainStyledAttributes.getInt(1, Integer.MIN_VALUE), obtainStyledAttributes.getInt(4, DEFAULT_SPAN_SIZE), android.widget.GridLayout.getAlignment(i, true), obtainStyledAttributes.getFloat(6, 0.0f));
                this.rowSpec = android.widget.GridLayout.spec(obtainStyledAttributes.getInt(2, Integer.MIN_VALUE), obtainStyledAttributes.getInt(3, DEFAULT_SPAN_SIZE), android.widget.GridLayout.getAlignment(i, false), obtainStyledAttributes.getFloat(5, 0.0f));
            } finally {
                obtainStyledAttributes.recycle();
            }
        }

        public void setGravity(int i) {
            this.rowSpec = this.rowSpec.copyWriteAlignment(android.widget.GridLayout.getAlignment(i, false));
            this.columnSpec = this.columnSpec.copyWriteAlignment(android.widget.GridLayout.getAlignment(i, true));
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void setBaseAttributes(android.content.res.TypedArray typedArray, int i, int i2) {
            this.width = typedArray.getLayoutDimension(i, -2);
            this.height = typedArray.getLayoutDimension(i2, -2);
        }

        final void setRowSpecSpan(android.widget.GridLayout.Interval interval) {
            this.rowSpec = this.rowSpec.copyWriteSpan(interval);
        }

        final void setColumnSpecSpan(android.widget.GridLayout.Interval interval) {
            this.columnSpec = this.columnSpec.copyWriteSpan(interval);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.widget.GridLayout.LayoutParams layoutParams = (android.widget.GridLayout.LayoutParams) obj;
            if (this.columnSpec.equals(layoutParams.columnSpec) && this.rowSpec.equals(layoutParams.rowSpec)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.rowSpec.hashCode() * 31) + this.columnSpec.hashCode();
        }
    }

    static final class Arc {
        public final android.widget.GridLayout.Interval span;
        public boolean valid = true;
        public final android.widget.GridLayout.MutableInt value;

        public Arc(android.widget.GridLayout.Interval interval, android.widget.GridLayout.MutableInt mutableInt) {
            this.span = interval;
            this.value = mutableInt;
        }

        public java.lang.String toString() {
            return this.span + " " + (!this.valid ? "+>" : android.telecom.Logging.Session.SUBSESSION_SEPARATION_CHAR) + " " + this.value;
        }
    }

    static final class MutableInt {
        public int value;

        public MutableInt() {
            reset();
        }

        public MutableInt(int i) {
            this.value = i;
        }

        public void reset() {
            this.value = Integer.MIN_VALUE;
        }

        public java.lang.String toString() {
            return java.lang.Integer.toString(this.value);
        }
    }

    static final class Assoc<K, V> extends java.util.ArrayList<android.util.Pair<K, V>> {
        private final java.lang.Class<K> keyType;
        private final java.lang.Class<V> valueType;

        private Assoc(java.lang.Class<K> cls, java.lang.Class<V> cls2) {
            this.keyType = cls;
            this.valueType = cls2;
        }

        public static <K, V> android.widget.GridLayout.Assoc<K, V> of(java.lang.Class<K> cls, java.lang.Class<V> cls2) {
            return new android.widget.GridLayout.Assoc<>(cls, cls2);
        }

        public void put(K k, V v) {
            add(android.util.Pair.create(k, v));
        }

        public android.widget.GridLayout.PackedMap<K, V> pack() {
            int size = size();
            java.lang.Object[] objArr = (java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) this.keyType, size);
            java.lang.Object[] objArr2 = (java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) this.valueType, size);
            for (int i = 0; i < size; i++) {
                objArr[i] = get(i).first;
                objArr2[i] = get(i).second;
            }
            return new android.widget.GridLayout.PackedMap<>(objArr, objArr2);
        }
    }

    static final class PackedMap<K, V> {
        public final int[] index;
        public final K[] keys;
        public final V[] values;

        private PackedMap(K[] kArr, V[] vArr) {
            this.index = createIndex(kArr);
            this.keys = (K[]) compact(kArr, this.index);
            this.values = (V[]) compact(vArr, this.index);
        }

        public V getValue(int i) {
            return this.values[this.index[i]];
        }

        private static <K> int[] createIndex(K[] kArr) {
            int length = kArr.length;
            int[] iArr = new int[length];
            java.util.HashMap hashMap = new java.util.HashMap();
            for (int i = 0; i < length; i++) {
                K k = kArr[i];
                java.lang.Integer num = (java.lang.Integer) hashMap.get(k);
                if (num == null) {
                    num = java.lang.Integer.valueOf(hashMap.size());
                    hashMap.put(k, num);
                }
                iArr[i] = num.intValue();
            }
            return iArr;
        }

        private static <K> K[] compact(K[] kArr, int[] iArr) {
            int length = kArr.length;
            K[] kArr2 = (K[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance(kArr.getClass().getComponentType(), android.widget.GridLayout.max2(iArr, -1) + 1));
            for (int i = 0; i < length; i++) {
                kArr2[iArr[i]] = kArr[i];
            }
            return kArr2;
        }
    }

    static class Bounds {
        public int after;
        public int before;
        public int flexibility;

        private Bounds() {
            reset();
        }

        protected void reset() {
            this.before = Integer.MIN_VALUE;
            this.after = Integer.MIN_VALUE;
            this.flexibility = 2;
        }

        protected void include(int i, int i2) {
            this.before = java.lang.Math.max(this.before, i);
            this.after = java.lang.Math.max(this.after, i2);
        }

        protected int size(boolean z) {
            if (!z && android.widget.GridLayout.canStretch(this.flexibility)) {
                return 100000;
            }
            return this.before + this.after;
        }

        protected int getOffset(android.widget.GridLayout gridLayout, android.view.View view, android.widget.GridLayout.Alignment alignment, int i, boolean z) {
            return this.before - alignment.getAlignmentValue(view, i, gridLayout.getLayoutMode());
        }

        protected final void include(android.widget.GridLayout gridLayout, android.view.View view, android.widget.GridLayout.Spec spec, android.widget.GridLayout.Axis axis, int i) {
            this.flexibility &= spec.getFlexibility();
            boolean z = axis.horizontal;
            int alignmentValue = spec.getAbsoluteAlignment(axis.horizontal).getAlignmentValue(view, i, gridLayout.getLayoutMode());
            include(alignmentValue, i - alignmentValue);
        }

        public java.lang.String toString() {
            return "Bounds{before=" + this.before + ", after=" + this.after + '}';
        }
    }

    static final class Interval {
        public final int max;
        public final int min;

        public Interval(int i, int i2) {
            this.min = i;
            this.max = i2;
        }

        int size() {
            return this.max - this.min;
        }

        android.widget.GridLayout.Interval inverse() {
            return new android.widget.GridLayout.Interval(this.max, this.min);
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.widget.GridLayout.Interval interval = (android.widget.GridLayout.Interval) obj;
            if (this.max == interval.max && this.min == interval.min) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.min * 31) + this.max;
        }

        public java.lang.String toString() {
            return android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + this.min + ", " + this.max + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static class Spec {
        static final float DEFAULT_WEIGHT = 0.0f;
        static final android.widget.GridLayout.Spec UNDEFINED = android.widget.GridLayout.spec(Integer.MIN_VALUE);
        final android.widget.GridLayout.Alignment alignment;
        final android.widget.GridLayout.Interval span;
        final boolean startDefined;
        final float weight;

        private Spec(boolean z, android.widget.GridLayout.Interval interval, android.widget.GridLayout.Alignment alignment, float f) {
            this.startDefined = z;
            this.span = interval;
            this.alignment = alignment;
            this.weight = f;
        }

        private Spec(boolean z, int i, int i2, android.widget.GridLayout.Alignment alignment, float f) {
            this(z, new android.widget.GridLayout.Interval(i, i2 + i), alignment, f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.widget.GridLayout.Alignment getAbsoluteAlignment(boolean z) {
            if (this.alignment != android.widget.GridLayout.UNDEFINED_ALIGNMENT) {
                return this.alignment;
            }
            if (this.weight == 0.0f) {
                return z ? android.widget.GridLayout.START : android.widget.GridLayout.BASELINE;
            }
            return android.widget.GridLayout.FILL;
        }

        final android.widget.GridLayout.Spec copyWriteSpan(android.widget.GridLayout.Interval interval) {
            return new android.widget.GridLayout.Spec(this.startDefined, interval, this.alignment, this.weight);
        }

        final android.widget.GridLayout.Spec copyWriteAlignment(android.widget.GridLayout.Alignment alignment) {
            return new android.widget.GridLayout.Spec(this.startDefined, this.span, alignment, this.weight);
        }

        final int getFlexibility() {
            return (this.alignment == android.widget.GridLayout.UNDEFINED_ALIGNMENT && this.weight == 0.0f) ? 0 : 2;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.widget.GridLayout.Spec spec = (android.widget.GridLayout.Spec) obj;
            if (this.alignment.equals(spec.alignment) && this.span.equals(spec.span)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.span.hashCode() * 31) + this.alignment.hashCode();
        }
    }

    public static android.widget.GridLayout.Spec spec(int i, int i2, android.widget.GridLayout.Alignment alignment, float f) {
        return new android.widget.GridLayout.Spec(i != Integer.MIN_VALUE, i, i2, alignment, f);
    }

    public static android.widget.GridLayout.Spec spec(int i, android.widget.GridLayout.Alignment alignment, float f) {
        return spec(i, 1, alignment, f);
    }

    public static android.widget.GridLayout.Spec spec(int i, int i2, float f) {
        return spec(i, i2, UNDEFINED_ALIGNMENT, f);
    }

    public static android.widget.GridLayout.Spec spec(int i, float f) {
        return spec(i, 1, f);
    }

    public static android.widget.GridLayout.Spec spec(int i, int i2, android.widget.GridLayout.Alignment alignment) {
        return spec(i, i2, alignment, 0.0f);
    }

    public static android.widget.GridLayout.Spec spec(int i, android.widget.GridLayout.Alignment alignment) {
        return spec(i, 1, alignment);
    }

    public static android.widget.GridLayout.Spec spec(int i, int i2) {
        return spec(i, i2, UNDEFINED_ALIGNMENT);
    }

    public static android.widget.GridLayout.Spec spec(int i) {
        return spec(i, 1);
    }

    public static abstract class Alignment {
        abstract int getAlignmentValue(android.view.View view, int i, int i2);

        abstract int getGravityOffset(android.view.View view, int i);

        Alignment() {
        }

        int getSizeInCell(android.view.View view, int i, int i2) {
            return i;
        }

        android.widget.GridLayout.Bounds getBounds() {
            return new android.widget.GridLayout.Bounds();
        }
    }

    private static android.widget.GridLayout.Alignment createSwitchingAlignment(final android.widget.GridLayout.Alignment alignment, final android.widget.GridLayout.Alignment alignment2) {
        return new android.widget.GridLayout.Alignment() { // from class: android.widget.GridLayout.5
            @Override // android.widget.GridLayout.Alignment
            int getGravityOffset(android.view.View view, int i) {
                return (!view.isLayoutRtl() ? android.widget.GridLayout.Alignment.this : alignment2).getGravityOffset(view, i);
            }

            @Override // android.widget.GridLayout.Alignment
            public int getAlignmentValue(android.view.View view, int i, int i2) {
                return (!view.isLayoutRtl() ? android.widget.GridLayout.Alignment.this : alignment2).getAlignmentValue(view, i, i2);
            }
        };
    }

    static boolean canStretch(int i) {
        return (i & 2) != 0;
    }
}
