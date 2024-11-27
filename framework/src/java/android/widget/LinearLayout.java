package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class LinearLayout extends android.view.ViewGroup {
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private static boolean sCompatibilityDone = false;
    private static boolean sRemeasureWeightedChildren = true;
    private final boolean mAllowInconsistentMeasurement;

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    private boolean mBaselineAligned;

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    private int mBaselineAlignedChildIndex;

    @android.view.ViewDebug.ExportedProperty(category = "measurement")
    private int mBaselineChildTop;
    private android.graphics.drawable.Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;

    @android.view.ViewDebug.ExportedProperty(category = "measurement", flagMapping = {@android.view.ViewDebug.FlagToString(equals = -1, mask = -1, name = android.security.keystore.KeyProperties.DIGEST_NONE), @android.view.ViewDebug.FlagToString(equals = 0, mask = 0, name = android.security.keystore.KeyProperties.DIGEST_NONE), @android.view.ViewDebug.FlagToString(equals = 48, mask = 48, name = "TOP"), @android.view.ViewDebug.FlagToString(equals = 80, mask = 80, name = "BOTTOM"), @android.view.ViewDebug.FlagToString(equals = 3, mask = 3, name = "LEFT"), @android.view.ViewDebug.FlagToString(equals = 5, mask = 5, name = "RIGHT"), @android.view.ViewDebug.FlagToString(equals = android.view.Gravity.START, mask = android.view.Gravity.START, name = "START"), @android.view.ViewDebug.FlagToString(equals = android.view.Gravity.END, mask = android.view.Gravity.END, name = "END"), @android.view.ViewDebug.FlagToString(equals = 16, mask = 16, name = "CENTER_VERTICAL"), @android.view.ViewDebug.FlagToString(equals = 112, mask = 112, name = "FILL_VERTICAL"), @android.view.ViewDebug.FlagToString(equals = 1, mask = 1, name = "CENTER_HORIZONTAL"), @android.view.ViewDebug.FlagToString(equals = 7, mask = 7, name = "FILL_HORIZONTAL"), @android.view.ViewDebug.FlagToString(equals = 17, mask = 17, name = "CENTER"), @android.view.ViewDebug.FlagToString(equals = 119, mask = 119, name = "FILL"), @android.view.ViewDebug.FlagToString(equals = 8388608, mask = 8388608, name = "RELATIVE")}, formatToHexString = true)
    private int mGravity;
    private int mLayoutDirection;
    private int[] mMaxAscent;
    private int[] mMaxDescent;

    @android.view.ViewDebug.ExportedProperty(category = "measurement")
    private int mOrientation;
    private int mShowDividers;

    @android.view.ViewDebug.ExportedProperty(category = "measurement")
    private int mTotalLength;

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    private boolean mUseLargestChild;

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    private float mWeightSum;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DividerMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    public static class LayoutParams extends android.view.ViewGroup.MarginLayoutParams {

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT, mapping = {@android.view.ViewDebug.IntToString(from = -1, to = android.security.keystore.KeyProperties.DIGEST_NONE), @android.view.ViewDebug.IntToString(from = 0, to = android.security.keystore.KeyProperties.DIGEST_NONE), @android.view.ViewDebug.IntToString(from = 48, to = "TOP"), @android.view.ViewDebug.IntToString(from = 80, to = "BOTTOM"), @android.view.ViewDebug.IntToString(from = 3, to = "LEFT"), @android.view.ViewDebug.IntToString(from = 5, to = "RIGHT"), @android.view.ViewDebug.IntToString(from = android.view.Gravity.START, to = "START"), @android.view.ViewDebug.IntToString(from = android.view.Gravity.END, to = "END"), @android.view.ViewDebug.IntToString(from = 16, to = "CENTER_VERTICAL"), @android.view.ViewDebug.IntToString(from = 112, to = "FILL_VERTICAL"), @android.view.ViewDebug.IntToString(from = 1, to = "CENTER_HORIZONTAL"), @android.view.ViewDebug.IntToString(from = 7, to = "FILL_HORIZONTAL"), @android.view.ViewDebug.IntToString(from = 17, to = "CENTER"), @android.view.ViewDebug.IntToString(from = 119, to = "FILL")})
        public int gravity;

        @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
        public float weight;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.LinearLayout.LayoutParams> {
            private int mLayout_gravityId;
            private int mLayout_weightId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
                this.mLayout_gravityId = propertyMapper.mapGravity("layout_gravity", 16842931);
                this.mLayout_weightId = propertyMapper.mapFloat("layout_weight", 16843137);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(android.widget.LinearLayout.LayoutParams layoutParams, android.view.inspector.PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readGravity(this.mLayout_gravityId, layoutParams.gravity);
                propertyReader.readFloat(this.mLayout_weightId, layoutParams.weight);
            }
        }

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = -1;
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.LinearLayout_Layout);
            this.weight = obtainStyledAttributes.getFloat(3, 0.0f);
            this.gravity = obtainStyledAttributes.getInt(0, -1);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = -1;
            this.weight = 0.0f;
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2);
            this.gravity = -1;
            this.weight = f;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = -1;
        }

        public LayoutParams(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.gravity = -1;
        }

        public LayoutParams(android.widget.LinearLayout.LayoutParams layoutParams) {
            super((android.view.ViewGroup.MarginLayoutParams) layoutParams);
            this.gravity = -1;
            this.weight = layoutParams.weight;
            this.gravity = layoutParams.gravity;
        }

        @Override // android.view.ViewGroup.LayoutParams
        public java.lang.String debug(java.lang.String str) {
            return str + "LinearLayout.LayoutParams={width=" + sizeToString(this.width) + ", height=" + sizeToString(this.height) + " weight=" + this.weight + "}";
        }

        @Override // android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
        protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:weight", this.weight);
            viewHierarchyEncoder.addProperty("layout:gravity", this.gravity);
        }
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.LinearLayout> {
        private int mBaselineAlignedChildIndexId;
        private int mBaselineAlignedId;
        private int mDividerId;
        private int mGravityId;
        private int mMeasureWithLargestChildId;
        private int mOrientationId;
        private boolean mPropertiesMapped = false;
        private int mWeightSumId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mBaselineAlignedId = propertyMapper.mapBoolean("baselineAligned", 16843046);
            this.mBaselineAlignedChildIndexId = propertyMapper.mapInt("baselineAlignedChildIndex", 16843047);
            this.mDividerId = propertyMapper.mapObject("divider", 16843049);
            this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
            this.mMeasureWithLargestChildId = propertyMapper.mapBoolean("measureWithLargestChild", 16843476);
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            sparseArray.put(0, android.app.slice.Slice.HINT_HORIZONTAL);
            sparseArray.put(1, "vertical");
            java.util.Objects.requireNonNull(sparseArray);
            this.mOrientationId = propertyMapper.mapIntEnum("orientation", 16842948, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mWeightSumId = propertyMapper.mapFloat("weightSum", 16843048);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.LinearLayout linearLayout, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mBaselineAlignedId, linearLayout.isBaselineAligned());
            propertyReader.readInt(this.mBaselineAlignedChildIndexId, linearLayout.getBaselineAlignedChildIndex());
            propertyReader.readObject(this.mDividerId, linearLayout.getDividerDrawable());
            propertyReader.readGravity(this.mGravityId, linearLayout.getGravity());
            propertyReader.readBoolean(this.mMeasureWithLargestChildId, linearLayout.isMeasureWithLargestChildEnabled());
            propertyReader.readIntEnum(this.mOrientationId, linearLayout.getOrientation());
            propertyReader.readFloat(this.mWeightSumId, linearLayout.getWeightSum());
        }
    }

    public LinearLayout(android.content.Context context) {
        this(context, null);
    }

    public LinearLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinearLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public LinearLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        boolean z;
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        this.mLayoutDirection = -1;
        if (!sCompatibilityDone && context != null) {
            if (context.getApplicationInfo().targetSdkVersion >= 28) {
                z = true;
            } else {
                z = false;
            }
            sRemeasureWeightedChildren = z;
            sCompatibilityDone = true;
        }
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.LinearLayout, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.LinearLayout, attributeSet, obtainStyledAttributes, i, i2);
        int i3 = obtainStyledAttributes.getInt(1, -1);
        if (i3 >= 0) {
            setOrientation(i3);
        }
        int i4 = obtainStyledAttributes.getInt(0, -1);
        if (i4 >= 0) {
            setGravity(i4);
        }
        boolean z2 = obtainStyledAttributes.getBoolean(2, true);
        if (!z2) {
            setBaselineAligned(z2);
        }
        this.mWeightSum = obtainStyledAttributes.getFloat(4, -1.0f);
        this.mBaselineAlignedChildIndex = obtainStyledAttributes.getInt(3, -1);
        this.mUseLargestChild = obtainStyledAttributes.getBoolean(6, false);
        this.mShowDividers = obtainStyledAttributes.getInt(7, 0);
        this.mDividerPadding = obtainStyledAttributes.getDimensionPixelSize(8, 0);
        setDividerDrawable(obtainStyledAttributes.getDrawable(5));
        this.mAllowInconsistentMeasurement = context.getApplicationInfo().targetSdkVersion <= 23;
        obtainStyledAttributes.recycle();
    }

    private boolean isShowingDividers() {
        return (this.mShowDividers == 0 || this.mDivider == null) ? false : true;
    }

    public void setShowDividers(int i) {
        if (i == this.mShowDividers) {
            return;
        }
        this.mShowDividers = i;
        setWillNotDraw(!isShowingDividers());
        requestLayout();
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    public android.graphics.drawable.Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public void setDividerDrawable(android.graphics.drawable.Drawable drawable) {
        if (drawable == this.mDivider) {
            return;
        }
        this.mDivider = drawable;
        if (drawable != null) {
            this.mDividerWidth = drawable.getIntrinsicWidth();
            this.mDividerHeight = drawable.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        setWillNotDraw(!isShowingDividers());
        requestLayout();
    }

    public void setDividerPadding(int i) {
        if (i == this.mDividerPadding) {
            return;
        }
        this.mDividerPadding = i;
        if (isShowingDividers()) {
            requestLayout();
            invalidate();
        }
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        if (this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            drawDividersVertical(canvas);
        } else {
            drawDividersHorizontal(canvas);
        }
    }

    void drawDividersVertical(android.graphics.Canvas canvas) {
        int bottom;
        int virtualChildCount = getVirtualChildCount();
        for (int i = 0; i < virtualChildCount; i++) {
            android.view.View virtualChildAt = getVirtualChildAt(i);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                drawHorizontalDivider(canvas, (virtualChildAt.getTop() - ((android.widget.LinearLayout.LayoutParams) virtualChildAt.getLayoutParams()).topMargin) - this.mDividerHeight);
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            android.view.View lastNonGoneChild = getLastNonGoneChild();
            if (lastNonGoneChild == null) {
                bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            } else {
                bottom = lastNonGoneChild.getBottom() + ((android.widget.LinearLayout.LayoutParams) lastNonGoneChild.getLayoutParams()).bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    private android.view.View getLastNonGoneChild() {
        for (int virtualChildCount = getVirtualChildCount() - 1; virtualChildCount >= 0; virtualChildCount--) {
            android.view.View virtualChildAt = getVirtualChildAt(virtualChildCount);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8) {
                return virtualChildAt;
            }
        }
        return null;
    }

    void drawDividersHorizontal(android.graphics.Canvas canvas) {
        int right;
        int left;
        int virtualChildCount = getVirtualChildCount();
        boolean isLayoutRtl = isLayoutRtl();
        for (int i = 0; i < virtualChildCount; i++) {
            android.view.View virtualChildAt = getVirtualChildAt(i);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8 && hasDividerBeforeChildAt(i)) {
                android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) virtualChildAt.getLayoutParams();
                if (isLayoutRtl) {
                    left = virtualChildAt.getRight() + layoutParams.rightMargin;
                } else {
                    left = (virtualChildAt.getLeft() - layoutParams.leftMargin) - this.mDividerWidth;
                }
                drawVerticalDivider(canvas, left);
            }
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            android.view.View lastNonGoneChild = getLastNonGoneChild();
            if (lastNonGoneChild == null) {
                if (isLayoutRtl) {
                    right = getPaddingLeft();
                } else {
                    right = (getWidth() - getPaddingRight()) - this.mDividerWidth;
                }
            } else {
                android.widget.LinearLayout.LayoutParams layoutParams2 = (android.widget.LinearLayout.LayoutParams) lastNonGoneChild.getLayoutParams();
                if (isLayoutRtl) {
                    right = (lastNonGoneChild.getLeft() - layoutParams2.leftMargin) - this.mDividerWidth;
                } else {
                    right = lastNonGoneChild.getRight() + layoutParams2.rightMargin;
                }
            }
            drawVerticalDivider(canvas, right);
        }
    }

    void drawHorizontalDivider(android.graphics.Canvas canvas, int i) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, i, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + i);
        this.mDivider.draw(canvas);
    }

    void drawVerticalDivider(android.graphics.Canvas canvas, int i) {
        this.mDivider.setBounds(i, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + i, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    @android.view.RemotableViewMethod
    public void setBaselineAligned(boolean z) {
        this.mBaselineAligned = z;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    @android.view.RemotableViewMethod
    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.mUseLargestChild = z;
    }

    @Override // android.view.View
    public int getBaseline() {
        int i;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        if (getChildCount() <= this.mBaselineAlignedChildIndex) {
            throw new java.lang.RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        android.view.View childAt = getChildAt(this.mBaselineAlignedChildIndex);
        int baseline = childAt.getBaseline();
        if (baseline == -1) {
            if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            }
            throw new java.lang.RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
        int i2 = this.mBaselineChildTop;
        if (this.mOrientation == 1 && (i = this.mGravity & 112) != 48) {
            switch (i) {
                case 16:
                    i2 += ((((this.mBottom - this.mTop) - this.mPaddingTop) - this.mPaddingBottom) - this.mTotalLength) / 2;
                    break;
                case 80:
                    i2 = ((this.mBottom - this.mTop) - this.mPaddingBottom) - this.mTotalLength;
                    break;
            }
        }
        return i2 + ((android.widget.LinearLayout.LayoutParams) childAt.getLayoutParams()).topMargin + baseline;
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    @android.view.RemotableViewMethod
    public void setBaselineAlignedChildIndex(int i) {
        if (i < 0 || i >= getChildCount()) {
            throw new java.lang.IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mBaselineAlignedChildIndex = i;
    }

    android.view.View getVirtualChildAt(int i) {
        return getChildAt(i);
    }

    int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    @android.view.RemotableViewMethod
    public void setWeightSum(float f) {
        this.mWeightSum = java.lang.Math.max(0.0f, f);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mOrientation == 1) {
            measureVertical(i, i2);
        } else {
            measureHorizontal(i, i2);
        }
    }

    protected boolean hasDividerBeforeChildAt(int i) {
        if (this.mShowDividers == 0) {
            return false;
        }
        return i == getVirtualChildCount() ? (this.mShowDividers & 4) != 0 : allViewsAreGoneBefore(i) ? (this.mShowDividers & 1) != 0 : (this.mShowDividers & 2) != 0;
    }

    private boolean hasDividerAfterChildAt(int i) {
        if (this.mShowDividers == 0) {
            return false;
        }
        return allViewsAreGoneAfter(i) ? (this.mShowDividers & 4) != 0 : (this.mShowDividers & 2) != 0;
    }

    private boolean allViewsAreGoneBefore(int i) {
        for (int i2 = i - 1; i2 >= 0; i2--) {
            android.view.View virtualChildAt = getVirtualChildAt(i2);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8) {
                return false;
            }
        }
        return true;
    }

    private boolean allViewsAreGoneAfter(int i) {
        int virtualChildCount = getVirtualChildCount();
        for (int i2 = i + 1; i2 < virtualChildCount; i2++) {
            android.view.View virtualChildAt = getVirtualChildAt(i2);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:165:0x032a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void measureVertical(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        float f;
        boolean z;
        boolean z2;
        int i8;
        int i9;
        int i10;
        int i11;
        android.widget.LinearLayout.LayoutParams layoutParams;
        android.view.View view;
        int i12;
        boolean z3;
        int i13;
        this.mTotalLength = 0;
        int virtualChildCount = getVirtualChildCount();
        int mode = android.view.View.MeasureSpec.getMode(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        int i14 = this.mBaselineAlignedChildIndex;
        boolean z4 = this.mUseLargestChild;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        boolean z5 = false;
        int i20 = 0;
        int i21 = 0;
        boolean z6 = false;
        boolean z7 = true;
        float f2 = 0.0f;
        int i22 = Integer.MIN_VALUE;
        while (true) {
            int i23 = 8;
            if (i18 < virtualChildCount) {
                int i24 = i17;
                android.view.View virtualChildAt = getVirtualChildAt(i18);
                if (virtualChildAt == null) {
                    this.mTotalLength += measureNullChild(i18);
                    i17 = i24;
                } else if (virtualChildAt.getVisibility() == 8) {
                    i18 += getChildrenSkipCount(virtualChildAt, i18);
                    i17 = i24;
                } else {
                    int i25 = i15 + 1;
                    if (hasDividerBeforeChildAt(i18)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                    android.widget.LinearLayout.LayoutParams layoutParams2 = (android.widget.LinearLayout.LayoutParams) virtualChildAt.getLayoutParams();
                    float f3 = f2 + layoutParams2.weight;
                    boolean z8 = layoutParams2.height == 0 && layoutParams2.weight > 0.0f;
                    if (mode2 != 1073741824 || !z8) {
                        int i26 = i22;
                        if (z8) {
                            layoutParams2.height = -2;
                        }
                        i8 = i25;
                        i9 = i16;
                        i10 = i24;
                        i11 = i18;
                        layoutParams = layoutParams2;
                        measureChildBeforeLayout(virtualChildAt, i18, i, 0, i2, f3 == 0.0f ? this.mTotalLength : 0);
                        int measuredHeight = virtualChildAt.getMeasuredHeight();
                        if (z8) {
                            layoutParams.height = 0;
                            i19 += measuredHeight;
                        }
                        int i27 = this.mTotalLength;
                        view = virtualChildAt;
                        this.mTotalLength = java.lang.Math.max(i27, i27 + measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin + getNextLocationOffset(view));
                        if (!z4) {
                            i22 = i26;
                        } else {
                            i22 = java.lang.Math.max(measuredHeight, i26);
                        }
                    } else {
                        int i28 = this.mTotalLength;
                        this.mTotalLength = java.lang.Math.max(i28, layoutParams2.topMargin + i28 + layoutParams2.bottomMargin);
                        i11 = i18;
                        layoutParams = layoutParams2;
                        z5 = true;
                        i10 = i24;
                        i22 = i22;
                        i8 = i25;
                        i9 = i16;
                        view = virtualChildAt;
                    }
                    if (i14 >= 0) {
                        i12 = i11;
                        if (i14 == i12 + 1) {
                            this.mBaselineChildTop = this.mTotalLength;
                        }
                    } else {
                        i12 = i11;
                    }
                    if (i12 < i14 && layoutParams.weight > 0.0f) {
                        throw new java.lang.RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                    }
                    if (mode != 1073741824 && layoutParams.width == -1) {
                        z3 = true;
                        z6 = true;
                    } else {
                        z3 = false;
                    }
                    int i29 = layoutParams.leftMargin + layoutParams.rightMargin;
                    int measuredWidth = view.getMeasuredWidth() + i29;
                    i20 = java.lang.Math.max(i20, measuredWidth);
                    i21 = combineMeasuredStates(i21, view.getMeasuredState());
                    z7 = z7 && layoutParams.width == -1;
                    if (layoutParams.weight > 0.0f) {
                        i13 = java.lang.Math.max(i9, z3 ? i29 : measuredWidth);
                        i17 = i10;
                    } else {
                        i17 = java.lang.Math.max(i10, z3 ? i29 : measuredWidth);
                        i13 = i9;
                    }
                    i18 = i12 + getChildrenSkipCount(view, i12);
                    i16 = i13;
                    f2 = f3;
                    i15 = i8;
                }
                i18++;
            } else {
                int i30 = i16;
                int i31 = i17;
                int i32 = i21;
                if (i15 > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
                    this.mTotalLength += this.mDividerHeight;
                }
                if (z4 && (mode2 == Integer.MIN_VALUE || mode2 == 0)) {
                    this.mTotalLength = 0;
                    int i33 = 0;
                    while (i33 < virtualChildCount) {
                        android.view.View virtualChildAt2 = getVirtualChildAt(i33);
                        if (virtualChildAt2 == null) {
                            this.mTotalLength += measureNullChild(i33);
                        } else if (virtualChildAt2.getVisibility() == i23) {
                            i33 += getChildrenSkipCount(virtualChildAt2, i33);
                        } else {
                            android.widget.LinearLayout.LayoutParams layoutParams3 = (android.widget.LinearLayout.LayoutParams) virtualChildAt2.getLayoutParams();
                            int i34 = this.mTotalLength;
                            this.mTotalLength = java.lang.Math.max(i34, i34 + i22 + layoutParams3.topMargin + layoutParams3.bottomMargin + getNextLocationOffset(virtualChildAt2));
                        }
                        i33++;
                        i23 = 8;
                    }
                }
                this.mTotalLength += this.mPaddingTop + this.mPaddingBottom;
                int resolveSizeAndState = resolveSizeAndState(java.lang.Math.max(this.mTotalLength, getSuggestedMinimumHeight()), i2, 0);
                int i35 = (16777215 & resolveSizeAndState) - this.mTotalLength;
                if (this.mAllowInconsistentMeasurement) {
                    i19 = 0;
                }
                int i36 = i35 + i19;
                if (z5 || ((sRemeasureWeightedChildren || i36 != 0) && f2 > 0.0f)) {
                    if (this.mWeightSum > 0.0f) {
                        f2 = this.mWeightSum;
                    }
                    this.mTotalLength = 0;
                    int i37 = i32;
                    int i38 = i20;
                    float f4 = f2;
                    int i39 = 0;
                    while (i39 < virtualChildCount) {
                        android.view.View virtualChildAt3 = getVirtualChildAt(i39);
                        if (virtualChildAt3 == null) {
                            i5 = i22;
                            i6 = i36;
                        } else if (virtualChildAt3.getVisibility() == 8) {
                            i5 = i22;
                            i6 = i36;
                        } else {
                            android.widget.LinearLayout.LayoutParams layoutParams4 = (android.widget.LinearLayout.LayoutParams) virtualChildAt3.getLayoutParams();
                            float f5 = layoutParams4.weight;
                            if (f5 <= 0.0f) {
                                i5 = i22;
                                i7 = i36;
                            } else {
                                i5 = i22;
                                int i40 = (int) ((i36 * f5) / f4);
                                int i41 = i36 - i40;
                                float f6 = f4 - f5;
                                if (this.mUseLargestChild && mode2 != 1073741824) {
                                    i40 = i5;
                                } else if (layoutParams4.height != 0 || (this.mAllowInconsistentMeasurement && mode2 != 1073741824)) {
                                    i40 += virtualChildAt3.getMeasuredHeight();
                                }
                                virtualChildAt3.measure(getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight + layoutParams4.leftMargin + layoutParams4.rightMargin, layoutParams4.width), android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.max(0, i40), 1073741824));
                                i37 = combineMeasuredStates(i37, virtualChildAt3.getMeasuredState() & (-256));
                                f4 = f6;
                                i7 = i41;
                            }
                            int i42 = i7;
                            int i43 = layoutParams4.leftMargin + layoutParams4.rightMargin;
                            int measuredWidth2 = virtualChildAt3.getMeasuredWidth() + i43;
                            i38 = java.lang.Math.max(i38, measuredWidth2);
                            if (mode != 1073741824) {
                                f = f4;
                                if (layoutParams4.width == -1) {
                                    z = true;
                                    if (!z) {
                                        i43 = measuredWidth2;
                                    }
                                    int max = java.lang.Math.max(i31, i43);
                                    if (z7 && layoutParams4.width == -1) {
                                        z2 = true;
                                        int i44 = this.mTotalLength;
                                        this.mTotalLength = java.lang.Math.max(i44, i44 + virtualChildAt3.getMeasuredHeight() + layoutParams4.topMargin + layoutParams4.bottomMargin + getNextLocationOffset(virtualChildAt3));
                                        i31 = max;
                                        z7 = z2;
                                        i6 = i42;
                                        f4 = f;
                                    }
                                    z2 = false;
                                    int i442 = this.mTotalLength;
                                    this.mTotalLength = java.lang.Math.max(i442, i442 + virtualChildAt3.getMeasuredHeight() + layoutParams4.topMargin + layoutParams4.bottomMargin + getNextLocationOffset(virtualChildAt3));
                                    i31 = max;
                                    z7 = z2;
                                    i6 = i42;
                                    f4 = f;
                                }
                            } else {
                                f = f4;
                            }
                            z = false;
                            if (!z) {
                            }
                            int max2 = java.lang.Math.max(i31, i43);
                            if (z7) {
                                z2 = true;
                                int i4422 = this.mTotalLength;
                                this.mTotalLength = java.lang.Math.max(i4422, i4422 + virtualChildAt3.getMeasuredHeight() + layoutParams4.topMargin + layoutParams4.bottomMargin + getNextLocationOffset(virtualChildAt3));
                                i31 = max2;
                                z7 = z2;
                                i6 = i42;
                                f4 = f;
                            }
                            z2 = false;
                            int i44222 = this.mTotalLength;
                            this.mTotalLength = java.lang.Math.max(i44222, i44222 + virtualChildAt3.getMeasuredHeight() + layoutParams4.topMargin + layoutParams4.bottomMargin + getNextLocationOffset(virtualChildAt3));
                            i31 = max2;
                            z7 = z2;
                            i6 = i42;
                            f4 = f;
                        }
                        i39++;
                        i36 = i6;
                        i22 = i5;
                    }
                    i3 = i;
                    this.mTotalLength += this.mPaddingTop + this.mPaddingBottom;
                    i20 = i38;
                    i4 = i31;
                    i32 = i37;
                } else {
                    i4 = java.lang.Math.max(i31, i30);
                    if (z4 && mode2 != 1073741824) {
                        for (int i45 = 0; i45 < virtualChildCount; i45++) {
                            android.view.View virtualChildAt4 = getVirtualChildAt(i45);
                            if (virtualChildAt4 != null && virtualChildAt4.getVisibility() != 8 && ((android.widget.LinearLayout.LayoutParams) virtualChildAt4.getLayoutParams()).weight > 0.0f) {
                                virtualChildAt4.measure(android.view.View.MeasureSpec.makeMeasureSpec(virtualChildAt4.getMeasuredWidth(), 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(i22, 1073741824));
                            }
                        }
                    }
                    i3 = i;
                }
                if (z7 || mode == 1073741824) {
                    i4 = i20;
                }
                setMeasuredDimension(resolveSizeAndState(java.lang.Math.max(i4 + this.mPaddingLeft + this.mPaddingRight, getSuggestedMinimumWidth()), i3, i32), resolveSizeAndState);
                if (z6) {
                    forceUniformWidth(virtualChildCount, i2);
                    return;
                }
                return;
            }
        }
    }

    private void forceUniformWidth(int i, int i2) {
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            android.view.View virtualChildAt = getVirtualChildAt(i3);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8) {
                android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) virtualChildAt.getLayoutParams();
                if (layoutParams.width == -1) {
                    int i4 = layoutParams.height;
                    layoutParams.height = virtualChildAt.getMeasuredHeight();
                    measureChildWithMargins(virtualChildAt, makeMeasureSpec, 0, i2, 0);
                    layoutParams.height = i4;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0200  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void measureHorizontal(int i, int i2) {
        int[] iArr;
        int i3;
        int max;
        int i4;
        int max2;
        int i5;
        int i6;
        int i7;
        int i8;
        int baseline;
        int i9;
        int i10;
        int i11;
        boolean z;
        boolean z2;
        android.widget.LinearLayout.LayoutParams layoutParams;
        android.view.View view;
        int i12;
        boolean z3;
        int measuredHeight;
        int baseline2;
        boolean z4 = false;
        this.mTotalLength = 0;
        int virtualChildCount = getVirtualChildCount();
        int mode = android.view.View.MeasureSpec.getMode(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] iArr2 = this.mMaxAscent;
        int[] iArr3 = this.mMaxDescent;
        iArr2[3] = -1;
        iArr2[2] = -1;
        iArr2[1] = -1;
        iArr2[0] = -1;
        iArr3[3] = -1;
        iArr3[2] = -1;
        iArr3[1] = -1;
        iArr3[0] = -1;
        boolean z5 = this.mBaselineAligned;
        boolean z6 = this.mUseLargestChild;
        int i13 = 1073741824;
        boolean z7 = mode == 1073741824;
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        int i18 = 0;
        int i19 = 0;
        boolean z8 = false;
        int i20 = 0;
        boolean z9 = false;
        boolean z10 = true;
        float f = 0.0f;
        int i21 = Integer.MIN_VALUE;
        while (true) {
            iArr = iArr3;
            if (i14 >= virtualChildCount) {
                break;
            }
            android.view.View virtualChildAt = getVirtualChildAt(i14);
            if (virtualChildAt == null) {
                this.mTotalLength += measureNullChild(i14);
                z = z6;
                z2 = z5;
            } else if (virtualChildAt.getVisibility() == 8) {
                i14 += getChildrenSkipCount(virtualChildAt, i14);
                z = z6;
                z2 = z5;
            } else {
                i15++;
                if (hasDividerBeforeChildAt(i14)) {
                    this.mTotalLength += this.mDividerWidth;
                }
                android.widget.LinearLayout.LayoutParams layoutParams2 = (android.widget.LinearLayout.LayoutParams) virtualChildAt.getLayoutParams();
                f += layoutParams2.weight;
                boolean z11 = (layoutParams2.width != 0 || layoutParams2.weight <= 0.0f) ? z4 : true;
                if (mode == i13 && z11) {
                    if (z7) {
                        this.mTotalLength += layoutParams2.leftMargin + layoutParams2.rightMargin;
                    } else {
                        int i22 = this.mTotalLength;
                        this.mTotalLength = java.lang.Math.max(i22, layoutParams2.leftMargin + i22 + layoutParams2.rightMargin);
                    }
                    if (z5) {
                        virtualChildAt.measure(android.view.View.MeasureSpec.makeSafeMeasureSpec(android.view.View.MeasureSpec.getSize(i), 0), android.view.View.MeasureSpec.makeSafeMeasureSpec(android.view.View.MeasureSpec.getSize(i2), 0));
                        i11 = i14;
                        z = z6;
                        z2 = z5;
                        layoutParams = layoutParams2;
                        view = virtualChildAt;
                    } else {
                        i11 = i14;
                        z = z6;
                        z2 = z5;
                        layoutParams = layoutParams2;
                        view = virtualChildAt;
                        z8 = true;
                    }
                } else {
                    if (z11) {
                        layoutParams2.width = -2;
                    }
                    i11 = i14;
                    z = z6;
                    z2 = z5;
                    layoutParams = layoutParams2;
                    measureChildBeforeLayout(virtualChildAt, i11, i, f == 0.0f ? this.mTotalLength : 0, i2, 0);
                    int measuredWidth = virtualChildAt.getMeasuredWidth();
                    if (z11) {
                        layoutParams.width = 0;
                        i17 += measuredWidth;
                    }
                    if (z7) {
                        view = virtualChildAt;
                        this.mTotalLength += layoutParams.leftMargin + measuredWidth + layoutParams.rightMargin + getNextLocationOffset(view);
                    } else {
                        view = virtualChildAt;
                        int i23 = this.mTotalLength;
                        this.mTotalLength = java.lang.Math.max(i23, i23 + measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin + getNextLocationOffset(view));
                    }
                    if (z) {
                        i21 = java.lang.Math.max(measuredWidth, i21);
                    }
                }
                if (mode2 != 1073741824) {
                    i12 = -1;
                    if (layoutParams.height == -1) {
                        z3 = true;
                        z9 = true;
                        int i24 = layoutParams.topMargin + layoutParams.bottomMargin;
                        measuredHeight = view.getMeasuredHeight() + i24;
                        i20 = combineMeasuredStates(i20, view.getMeasuredState());
                        if (z2 && (baseline2 = view.getBaseline()) != i12) {
                            int i25 = ((((layoutParams.gravity >= 0 ? this.mGravity : layoutParams.gravity) & 112) >> 4) & (-2)) >> 1;
                            iArr2[i25] = java.lang.Math.max(iArr2[i25], baseline2);
                            iArr[i25] = java.lang.Math.max(iArr[i25], measuredHeight - baseline2);
                        }
                        i16 = java.lang.Math.max(i16, measuredHeight);
                        z10 = !z10 && layoutParams.height == -1;
                        if (layoutParams.weight <= 0.0f) {
                            if (!z3) {
                                i24 = measuredHeight;
                            }
                            i19 = java.lang.Math.max(i19, i24);
                        } else {
                            int i26 = i19;
                            if (!z3) {
                                i24 = measuredHeight;
                            }
                            i18 = java.lang.Math.max(i18, i24);
                            i19 = i26;
                        }
                        int i27 = i11;
                        i14 = getChildrenSkipCount(view, i27) + i27;
                    }
                } else {
                    i12 = -1;
                }
                z3 = false;
                int i242 = layoutParams.topMargin + layoutParams.bottomMargin;
                measuredHeight = view.getMeasuredHeight() + i242;
                i20 = combineMeasuredStates(i20, view.getMeasuredState());
                if (z2) {
                    int i252 = ((((layoutParams.gravity >= 0 ? this.mGravity : layoutParams.gravity) & 112) >> 4) & (-2)) >> 1;
                    iArr2[i252] = java.lang.Math.max(iArr2[i252], baseline2);
                    iArr[i252] = java.lang.Math.max(iArr[i252], measuredHeight - baseline2);
                }
                i16 = java.lang.Math.max(i16, measuredHeight);
                if (z10) {
                }
                if (layoutParams.weight <= 0.0f) {
                }
                int i272 = i11;
                i14 = getChildrenSkipCount(view, i272) + i272;
            }
            i14++;
            iArr3 = iArr;
            z6 = z;
            z5 = z2;
            i13 = 1073741824;
            z4 = false;
        }
        boolean z12 = z6;
        boolean z13 = z5;
        int i28 = i16;
        int i29 = i18;
        int i30 = i19;
        int i31 = i20;
        if (i15 > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
            this.mTotalLength += this.mDividerWidth;
        }
        if (iArr2[1] == -1 && iArr2[0] == -1 && iArr2[2] == -1 && iArr2[3] == -1) {
            max = i28;
            i3 = i31;
        } else {
            i3 = i31;
            max = java.lang.Math.max(i28, java.lang.Math.max(iArr2[3], java.lang.Math.max(iArr2[0], java.lang.Math.max(iArr2[1], iArr2[2]))) + java.lang.Math.max(iArr[3], java.lang.Math.max(iArr[0], java.lang.Math.max(iArr[1], iArr[2]))));
        }
        if (!z12) {
            i4 = max;
        } else if (mode == Integer.MIN_VALUE || mode == 0) {
            this.mTotalLength = 0;
            int i32 = 0;
            int i33 = 0;
            while (i32 < virtualChildCount) {
                android.view.View virtualChildAt2 = getVirtualChildAt(i32);
                if (virtualChildAt2 == null) {
                    this.mTotalLength += measureNullChild(i32);
                    i9 = max;
                } else if (virtualChildAt2.getVisibility() == 8) {
                    i32 += getChildrenSkipCount(virtualChildAt2, i32);
                    i9 = max;
                } else {
                    i33++;
                    if (hasDividerBeforeChildAt(i32)) {
                        this.mTotalLength += this.mDividerWidth;
                    }
                    android.widget.LinearLayout.LayoutParams layoutParams3 = (android.widget.LinearLayout.LayoutParams) virtualChildAt2.getLayoutParams();
                    if (z7) {
                        i9 = max;
                        this.mTotalLength += layoutParams3.leftMargin + i21 + layoutParams3.rightMargin + getNextLocationOffset(virtualChildAt2);
                        i10 = i32;
                    } else {
                        i9 = max;
                        int i34 = this.mTotalLength;
                        i10 = i32;
                        this.mTotalLength = java.lang.Math.max(i34, i34 + i21 + layoutParams3.leftMargin + layoutParams3.rightMargin + getNextLocationOffset(virtualChildAt2));
                    }
                    i32 = i10;
                }
                i32++;
                max = i9;
            }
            i4 = max;
            if (i33 > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
                this.mTotalLength += this.mDividerWidth;
            }
        } else {
            i4 = max;
        }
        this.mTotalLength += this.mPaddingLeft + this.mPaddingRight;
        int resolveSizeAndState = resolveSizeAndState(java.lang.Math.max(this.mTotalLength, getSuggestedMinimumWidth()), i, 0);
        int i35 = (16777215 & resolveSizeAndState) - this.mTotalLength;
        if (this.mAllowInconsistentMeasurement) {
            i17 = 0;
        }
        int i36 = i35 + i17;
        if (z8 || ((sRemeasureWeightedChildren || i36 != 0) && f > 0.0f)) {
            if (this.mWeightSum > 0.0f) {
                f = this.mWeightSum;
            }
            iArr2[3] = -1;
            iArr2[2] = -1;
            iArr2[1] = -1;
            iArr2[0] = -1;
            iArr[3] = -1;
            iArr[2] = -1;
            iArr[1] = -1;
            iArr[0] = -1;
            this.mTotalLength = 0;
            int i37 = i3;
            int i38 = -1;
            int i39 = 0;
            int i40 = 0;
            while (i39 < virtualChildCount) {
                android.view.View virtualChildAt3 = getVirtualChildAt(i39);
                if (virtualChildAt3 != null) {
                    i6 = i21;
                    if (virtualChildAt3.getVisibility() != 8) {
                        int i41 = i40 + 1;
                        if (hasDividerBeforeChildAt(i39)) {
                            this.mTotalLength += this.mDividerWidth;
                        }
                        android.widget.LinearLayout.LayoutParams layoutParams4 = (android.widget.LinearLayout.LayoutParams) virtualChildAt3.getLayoutParams();
                        float f2 = layoutParams4.weight;
                        if (f2 <= 0.0f) {
                            i7 = i41;
                        } else {
                            i7 = i41;
                            int i42 = (int) ((i36 * f2) / f);
                            int i43 = i36 - i42;
                            f -= f2;
                            if (this.mUseLargestChild && mode != 1073741824) {
                                i42 = i6;
                            } else if (layoutParams4.width != 0 || (this.mAllowInconsistentMeasurement && mode != 1073741824)) {
                                i42 += virtualChildAt3.getMeasuredWidth();
                            }
                            virtualChildAt3.measure(android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.max(0, i42), 1073741824), getChildMeasureSpec(i2, this.mPaddingTop + this.mPaddingBottom + layoutParams4.topMargin + layoutParams4.bottomMargin, layoutParams4.height));
                            i37 = combineMeasuredStates(i37, virtualChildAt3.getMeasuredState() & (-16777216));
                            i36 = i43;
                        }
                        if (z7) {
                            i8 = i36;
                            this.mTotalLength += virtualChildAt3.getMeasuredWidth() + layoutParams4.leftMargin + layoutParams4.rightMargin + getNextLocationOffset(virtualChildAt3);
                        } else {
                            i8 = i36;
                            int i44 = this.mTotalLength;
                            this.mTotalLength = java.lang.Math.max(i44, virtualChildAt3.getMeasuredWidth() + i44 + layoutParams4.leftMargin + layoutParams4.rightMargin + getNextLocationOffset(virtualChildAt3));
                        }
                        boolean z14 = mode2 != 1073741824 && layoutParams4.height == -1;
                        int i45 = layoutParams4.topMargin + layoutParams4.bottomMargin;
                        int measuredHeight2 = virtualChildAt3.getMeasuredHeight() + i45;
                        i38 = java.lang.Math.max(i38, measuredHeight2);
                        if (!z14) {
                            i45 = measuredHeight2;
                        }
                        i29 = java.lang.Math.max(i29, i45);
                        boolean z15 = z10 && layoutParams4.height == -1;
                        if (z13 && (baseline = virtualChildAt3.getBaseline()) != -1) {
                            int i46 = ((((layoutParams4.gravity < 0 ? this.mGravity : layoutParams4.gravity) & 112) >> 4) & (-2)) >> 1;
                            iArr2[i46] = java.lang.Math.max(iArr2[i46], baseline);
                            iArr[i46] = java.lang.Math.max(iArr[i46], measuredHeight2 - baseline);
                        }
                        z10 = z15;
                        i40 = i7;
                        i36 = i8;
                    }
                } else {
                    i6 = i21;
                }
                i39++;
                i21 = i6;
            }
            if (i40 > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
                this.mTotalLength += this.mDividerWidth;
            }
            this.mTotalLength += this.mPaddingLeft + this.mPaddingRight;
            max2 = (iArr2[1] == -1 && iArr2[0] == -1 && iArr2[2] == -1 && iArr2[3] == -1) ? i38 : java.lang.Math.max(i38, java.lang.Math.max(iArr2[3], java.lang.Math.max(iArr2[0], java.lang.Math.max(iArr2[1], iArr2[2]))) + java.lang.Math.max(iArr[3], java.lang.Math.max(iArr[0], java.lang.Math.max(iArr[1], iArr[2]))));
            i5 = i37;
        } else {
            i29 = java.lang.Math.max(i29, i30);
            if (z12 && mode != 1073741824) {
                for (int i47 = 0; i47 < virtualChildCount; i47++) {
                    android.view.View virtualChildAt4 = getVirtualChildAt(i47);
                    if (virtualChildAt4 != null && virtualChildAt4.getVisibility() != 8 && ((android.widget.LinearLayout.LayoutParams) virtualChildAt4.getLayoutParams()).weight > 0.0f) {
                        virtualChildAt4.measure(android.view.View.MeasureSpec.makeMeasureSpec(i21, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(virtualChildAt4.getMeasuredHeight(), 1073741824));
                    }
                }
            }
            i5 = i3;
            max2 = i4;
        }
        if (z10 || mode2 == 1073741824) {
            i29 = max2;
        }
        setMeasuredDimension(resolveSizeAndState | (i5 & (-16777216)), resolveSizeAndState(java.lang.Math.max(i29 + this.mPaddingTop + this.mPaddingBottom, getSuggestedMinimumHeight()), i2, i5 << 16));
        if (z9) {
            forceUniformHeight(virtualChildCount, i);
        }
    }

    private void forceUniformHeight(int i, int i2) {
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            android.view.View virtualChildAt = getVirtualChildAt(i3);
            if (virtualChildAt != null && virtualChildAt.getVisibility() != 8) {
                android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) virtualChildAt.getLayoutParams();
                if (layoutParams.height == -1) {
                    int i4 = layoutParams.width;
                    layoutParams.width = virtualChildAt.getMeasuredWidth();
                    measureChildWithMargins(virtualChildAt, i2, 0, makeMeasureSpec, 0);
                    layoutParams.width = i4;
                }
            }
        }
    }

    int getChildrenSkipCount(android.view.View view, int i) {
        return 0;
    }

    int measureNullChild(int i) {
        return 0;
    }

    void measureChildBeforeLayout(android.view.View view, int i, int i2, int i3, int i4, int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5);
    }

    int getLocationOffset(android.view.View view) {
        return 0;
    }

    int getNextLocationOffset(android.view.View view) {
        return 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mOrientation == 1) {
            layoutVertical(i, i2, i3, i4);
        } else {
            layoutHorizontal(i, i2, i3, i4);
        }
    }

    void layoutVertical(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = this.mPaddingLeft;
        int i8 = i3 - i;
        int i9 = i8 - this.mPaddingRight;
        int i10 = (i8 - i7) - this.mPaddingRight;
        int virtualChildCount = getVirtualChildCount();
        int i11 = this.mGravity & 112;
        int i12 = this.mGravity & android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        switch (i11) {
            case 16:
                i5 = this.mPaddingTop + (((i4 - i2) - this.mTotalLength) / 2);
                break;
            case 80:
                i5 = ((this.mPaddingTop + i4) - i2) - this.mTotalLength;
                break;
            default:
                i5 = this.mPaddingTop;
                break;
        }
        int i13 = 0;
        while (i13 < virtualChildCount) {
            android.view.View virtualChildAt = getVirtualChildAt(i13);
            if (virtualChildAt == null) {
                i5 += measureNullChild(i13);
            } else if (virtualChildAt.getVisibility() != 8) {
                int measuredWidth = virtualChildAt.getMeasuredWidth();
                int measuredHeight = virtualChildAt.getMeasuredHeight();
                android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) virtualChildAt.getLayoutParams();
                int i14 = layoutParams.gravity;
                if (i14 < 0) {
                    i14 = i12;
                }
                switch (android.view.Gravity.getAbsoluteGravity(i14, getLayoutDirection()) & 7) {
                    case 1:
                        i6 = ((((i10 - measuredWidth) / 2) + i7) + layoutParams.leftMargin) - layoutParams.rightMargin;
                        break;
                    case 5:
                        i6 = (i9 - measuredWidth) - layoutParams.rightMargin;
                        break;
                    default:
                        i6 = layoutParams.leftMargin + i7;
                        break;
                }
                if (hasDividerBeforeChildAt(i13)) {
                    i5 += this.mDividerHeight;
                }
                int i15 = i5 + layoutParams.topMargin;
                setChildFrame(virtualChildAt, i6, i15 + getLocationOffset(virtualChildAt), measuredWidth, measuredHeight);
                int nextLocationOffset = i15 + measuredHeight + layoutParams.bottomMargin + getNextLocationOffset(virtualChildAt);
                i13 += getChildrenSkipCount(virtualChildAt, i13);
                i5 = nextLocationOffset;
            }
            i13++;
        }
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (i != this.mLayoutDirection) {
            this.mLayoutDirection = i;
            if (this.mOrientation == 0) {
                requestLayout();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void layoutHorizontal(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        boolean z;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        boolean isLayoutRtl = isLayoutRtl();
        int i15 = this.mPaddingTop;
        int i16 = i4 - i2;
        int i17 = i16 - this.mPaddingBottom;
        int i18 = (i16 - i15) - this.mPaddingBottom;
        int virtualChildCount = getVirtualChildCount();
        int i19 = this.mGravity & android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int i20 = this.mGravity & 112;
        boolean z2 = this.mBaselineAligned;
        int[] iArr = this.mMaxAscent;
        int[] iArr2 = this.mMaxDescent;
        switch (android.view.Gravity.getAbsoluteGravity(i19, getLayoutDirection())) {
            case 1:
                i5 = this.mPaddingLeft + (((i3 - i) - this.mTotalLength) / 2);
                break;
            case 5:
                i5 = ((this.mPaddingLeft + i3) - i) - this.mTotalLength;
                break;
            default:
                i5 = this.mPaddingLeft;
                break;
        }
        if (!isLayoutRtl) {
            i6 = 0;
            i7 = 1;
        } else {
            i6 = virtualChildCount - 1;
            i7 = -1;
        }
        int i21 = 0;
        while (i21 < virtualChildCount) {
            int i22 = i6 + (i7 * i21);
            android.view.View virtualChildAt = getVirtualChildAt(i22);
            if (virtualChildAt == null) {
                i5 += measureNullChild(i22);
                z = isLayoutRtl;
                i8 = i15;
                i9 = i17;
                i10 = virtualChildCount;
            } else if (virtualChildAt.getVisibility() != 8) {
                int measuredWidth = virtualChildAt.getMeasuredWidth();
                int measuredHeight = virtualChildAt.getMeasuredHeight();
                android.widget.LinearLayout.LayoutParams layoutParams = (android.widget.LinearLayout.LayoutParams) virtualChildAt.getLayoutParams();
                if (z2) {
                    i11 = i21;
                    i10 = virtualChildCount;
                    if (layoutParams.height != -1) {
                        i12 = virtualChildAt.getBaseline();
                        i13 = layoutParams.gravity;
                        if (i13 < 0) {
                            i13 = i20;
                        }
                        switch (i13 & 112) {
                            case 16:
                                i9 = i17;
                                i14 = ((((i18 - measuredHeight) / 2) + i15) + layoutParams.topMargin) - layoutParams.bottomMargin;
                                break;
                            case 48:
                                i9 = i17;
                                i14 = layoutParams.topMargin + i15;
                                if (i12 != -1) {
                                    i14 += iArr[1] - i12;
                                    break;
                                }
                                break;
                            case 80:
                                i9 = i17;
                                i14 = (i17 - measuredHeight) - layoutParams.bottomMargin;
                                if (i12 == -1) {
                                    break;
                                } else {
                                    i14 -= iArr2[2] - (virtualChildAt.getMeasuredHeight() - i12);
                                    break;
                                }
                            default:
                                i9 = i17;
                                i14 = i15;
                                break;
                        }
                        if (!isLayoutRtl) {
                            if (hasDividerAfterChildAt(i22)) {
                                i5 += this.mDividerWidth;
                            }
                        } else if (hasDividerBeforeChildAt(i22)) {
                            i5 += this.mDividerWidth;
                        }
                        int i23 = layoutParams.leftMargin + i5;
                        z = isLayoutRtl;
                        i8 = i15;
                        setChildFrame(virtualChildAt, i23 + getLocationOffset(virtualChildAt), i14, measuredWidth, measuredHeight);
                        int nextLocationOffset = i23 + measuredWidth + layoutParams.rightMargin + getNextLocationOffset(virtualChildAt);
                        i21 = i11 + getChildrenSkipCount(virtualChildAt, i22);
                        i5 = nextLocationOffset;
                    }
                } else {
                    i11 = i21;
                    i10 = virtualChildCount;
                }
                i12 = -1;
                i13 = layoutParams.gravity;
                if (i13 < 0) {
                }
                switch (i13 & 112) {
                    case 16:
                        break;
                    case 48:
                        break;
                    case 80:
                        break;
                }
                if (!isLayoutRtl) {
                }
                int i232 = layoutParams.leftMargin + i5;
                z = isLayoutRtl;
                i8 = i15;
                setChildFrame(virtualChildAt, i232 + getLocationOffset(virtualChildAt), i14, measuredWidth, measuredHeight);
                int nextLocationOffset2 = i232 + measuredWidth + layoutParams.rightMargin + getNextLocationOffset(virtualChildAt);
                i21 = i11 + getChildrenSkipCount(virtualChildAt, i22);
                i5 = nextLocationOffset2;
            } else {
                z = isLayoutRtl;
                i8 = i15;
                i9 = i17;
                i10 = virtualChildCount;
            }
            i21++;
            virtualChildCount = i10;
            i17 = i9;
            i15 = i8;
            isLayoutRtl = z;
        }
    }

    private void setChildFrame(android.view.View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i3 + i, i4 + i2);
    }

    public void setOrientation(int i) {
        if (this.mOrientation != i) {
            this.mOrientation = i;
            requestLayout();
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    @android.view.RemotableViewMethod
    public void setGravity(int i) {
        if (this.mGravity != i) {
            if ((8388615 & i) == 0) {
                i |= android.view.Gravity.START;
            }
            if ((i & 112) == 0) {
                i |= 48;
            }
            this.mGravity = i;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    @android.view.RemotableViewMethod
    public void setHorizontalGravity(int i) {
        int i2 = i & android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if ((8388615 & this.mGravity) != i2) {
            this.mGravity = i2 | (this.mGravity & (-8388616));
            requestLayout();
        }
    }

    @android.view.RemotableViewMethod
    public void setVerticalGravity(int i) {
        int i2 = i & 112;
        if ((this.mGravity & 112) != i2) {
            this.mGravity = i2 | (this.mGravity & (-113));
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup
    public android.widget.LinearLayout.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.widget.LinearLayout.LayoutParams(getContext(), attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public android.widget.LinearLayout.LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new android.widget.LinearLayout.LayoutParams(-2, -2);
        }
        if (this.mOrientation == 1) {
            return new android.widget.LinearLayout.LayoutParams(-1, -2);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public android.widget.LinearLayout.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (sPreserveMarginParamsInLayoutParamConversion) {
            if (layoutParams instanceof android.widget.LinearLayout.LayoutParams) {
                return new android.widget.LinearLayout.LayoutParams((android.widget.LinearLayout.LayoutParams) layoutParams);
            }
            if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
                return new android.widget.LinearLayout.LayoutParams((android.view.ViewGroup.MarginLayoutParams) layoutParams);
            }
        }
        return new android.widget.LinearLayout.LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof android.widget.LinearLayout.LayoutParams;
    }

    @Override // android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.LinearLayout.class.getName();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("layout:baselineAligned", this.mBaselineAligned);
        viewHierarchyEncoder.addProperty("layout:baselineAlignedChildIndex", this.mBaselineAlignedChildIndex);
        viewHierarchyEncoder.addProperty("measurement:baselineChildTop", this.mBaselineChildTop);
        viewHierarchyEncoder.addProperty("measurement:orientation", this.mOrientation);
        viewHierarchyEncoder.addProperty("measurement:gravity", this.mGravity);
        viewHierarchyEncoder.addProperty("measurement:totalLength", this.mTotalLength);
        viewHierarchyEncoder.addProperty("layout:totalLength", this.mTotalLength);
        viewHierarchyEncoder.addProperty("layout:useLargestChild", this.mUseLargestChild);
    }
}
