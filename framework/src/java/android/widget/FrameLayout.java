package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class FrameLayout extends android.view.ViewGroup {
    private static final int DEFAULT_CHILD_GRAVITY = 8388659;

    @android.view.ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingBottom;

    @android.view.ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingLeft;

    @android.view.ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingRight;

    @android.view.ViewDebug.ExportedProperty(category = "padding")
    private int mForegroundPaddingTop;
    private final java.util.ArrayList<android.view.View> mMatchParentChildren;

    @android.view.ViewDebug.ExportedProperty(category = "measurement")
    boolean mMeasureAllChildren;

    public static class LayoutParams extends android.view.ViewGroup.MarginLayoutParams {
        public static final int UNSPECIFIED_GRAVITY = -1;
        public int gravity;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.FrameLayout.LayoutParams> {
            private int mLayout_gravityId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
                this.mLayout_gravityId = propertyMapper.mapGravity("layout_gravity", 16842931);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(android.widget.FrameLayout.LayoutParams layoutParams, android.view.inspector.PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readGravity(this.mLayout_gravityId, layoutParams.gravity);
            }
        }

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = -1;
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.FrameLayout_Layout);
            this.gravity = obtainStyledAttributes.getInt(0, -1);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = -1;
        }

        public LayoutParams(int i, int i2, int i3) {
            super(i, i2);
            this.gravity = -1;
            this.gravity = i3;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = -1;
        }

        public LayoutParams(android.view.ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.gravity = -1;
        }

        public LayoutParams(android.widget.FrameLayout.LayoutParams layoutParams) {
            super((android.view.ViewGroup.MarginLayoutParams) layoutParams);
            this.gravity = -1;
            this.gravity = layoutParams.gravity;
        }
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.FrameLayout> {
        private int mMeasureAllChildrenId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mMeasureAllChildrenId = propertyMapper.mapBoolean("measureAllChildren", 16843018);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.FrameLayout frameLayout, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mMeasureAllChildrenId, frameLayout.getMeasureAllChildren());
        }
    }

    public FrameLayout(android.content.Context context) {
        super(context);
        this.mMeasureAllChildren = false;
        this.mForegroundPaddingLeft = 0;
        this.mForegroundPaddingTop = 0;
        this.mForegroundPaddingRight = 0;
        this.mForegroundPaddingBottom = 0;
        this.mMatchParentChildren = new java.util.ArrayList<>(1);
    }

    public FrameLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FrameLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public FrameLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMeasureAllChildren = false;
        this.mForegroundPaddingLeft = 0;
        this.mForegroundPaddingTop = 0;
        this.mForegroundPaddingRight = 0;
        this.mForegroundPaddingBottom = 0;
        this.mMatchParentChildren = new java.util.ArrayList<>(1);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.FrameLayout, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.FrameLayout, attributeSet, obtainStyledAttributes, i, i2);
        if (obtainStyledAttributes.getBoolean(0, false)) {
            setMeasureAllChildren(true);
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    @android.view.RemotableViewMethod
    public void setForegroundGravity(int i) {
        if (getForegroundGravity() != i) {
            super.setForegroundGravity(i);
            android.graphics.drawable.Drawable foreground = getForeground();
            if (getForegroundGravity() == 119 && foreground != null) {
                android.graphics.Rect rect = new android.graphics.Rect();
                if (foreground.getPadding(rect)) {
                    this.mForegroundPaddingLeft = rect.left;
                    this.mForegroundPaddingTop = rect.top;
                    this.mForegroundPaddingRight = rect.right;
                    this.mForegroundPaddingBottom = rect.bottom;
                }
            } else {
                this.mForegroundPaddingLeft = 0;
                this.mForegroundPaddingTop = 0;
                this.mForegroundPaddingRight = 0;
                this.mForegroundPaddingBottom = 0;
            }
            requestLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public android.widget.FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new android.widget.FrameLayout.LayoutParams(-1, -1);
    }

    int getPaddingLeftWithForeground() {
        return isForegroundInsidePadding() ? java.lang.Math.max(this.mPaddingLeft, this.mForegroundPaddingLeft) : this.mPaddingLeft + this.mForegroundPaddingLeft;
    }

    int getPaddingRightWithForeground() {
        return isForegroundInsidePadding() ? java.lang.Math.max(this.mPaddingRight, this.mForegroundPaddingRight) : this.mPaddingRight + this.mForegroundPaddingRight;
    }

    private int getPaddingTopWithForeground() {
        return isForegroundInsidePadding() ? java.lang.Math.max(this.mPaddingTop, this.mForegroundPaddingTop) : this.mPaddingTop + this.mForegroundPaddingTop;
    }

    private int getPaddingBottomWithForeground() {
        return isForegroundInsidePadding() ? java.lang.Math.max(this.mPaddingBottom, this.mForegroundPaddingBottom) : this.mPaddingBottom + this.mForegroundPaddingBottom;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int childMeasureSpec;
        int childMeasureSpec2;
        int childCount = getChildCount();
        boolean z = (android.view.View.MeasureSpec.getMode(i) == 1073741824 && android.view.View.MeasureSpec.getMode(i2) == 1073741824) ? false : true;
        this.mMatchParentChildren.clear();
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            android.view.View childAt = getChildAt(i6);
            if (this.mMeasureAllChildren || childAt.getVisibility() != 8) {
                measureChildWithMargins(childAt, i, 0, i2, 0);
                android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) childAt.getLayoutParams();
                i5 = java.lang.Math.max(i5, childAt.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
                i4 = java.lang.Math.max(i4, childAt.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
                i3 = combineMeasuredStates(i3, childAt.getMeasuredState());
                if (z && (layoutParams.width == -1 || layoutParams.height == -1)) {
                    this.mMatchParentChildren.add(childAt);
                }
            }
        }
        int i7 = i3;
        int paddingLeftWithForeground = i5 + getPaddingLeftWithForeground() + getPaddingRightWithForeground();
        int max = java.lang.Math.max(i4 + getPaddingTopWithForeground() + getPaddingBottomWithForeground(), getSuggestedMinimumHeight());
        int max2 = java.lang.Math.max(paddingLeftWithForeground, getSuggestedMinimumWidth());
        android.graphics.drawable.Drawable foreground = getForeground();
        if (foreground != null) {
            max = java.lang.Math.max(max, foreground.getMinimumHeight());
            max2 = java.lang.Math.max(max2, foreground.getMinimumWidth());
        }
        setMeasuredDimension(resolveSizeAndState(max2, i, i7), resolveSizeAndState(max, i2, i7 << 16));
        int size = this.mMatchParentChildren.size();
        if (size > 1) {
            for (int i8 = 0; i8 < size; i8++) {
                android.view.View view = this.mMatchParentChildren.get(i8);
                android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) view.getLayoutParams();
                if (marginLayoutParams.width == -1) {
                    childMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.max(0, (((getMeasuredWidth() - getPaddingLeftWithForeground()) - getPaddingRightWithForeground()) - marginLayoutParams.leftMargin) - marginLayoutParams.rightMargin), 1073741824);
                } else {
                    childMeasureSpec = getChildMeasureSpec(i, getPaddingLeftWithForeground() + getPaddingRightWithForeground() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, marginLayoutParams.width);
                }
                if (marginLayoutParams.height == -1) {
                    childMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.max(0, (((getMeasuredHeight() - getPaddingTopWithForeground()) - getPaddingBottomWithForeground()) - marginLayoutParams.topMargin) - marginLayoutParams.bottomMargin), 1073741824);
                } else {
                    childMeasureSpec2 = getChildMeasureSpec(i2, getPaddingTopWithForeground() + getPaddingBottomWithForeground() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height);
                }
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        layoutChildren(i, i2, i3, i4, false);
    }

    void layoutChildren(int i, int i2, int i3, int i4, boolean z) {
        int i5;
        int i6;
        int childCount = getChildCount();
        int paddingLeftWithForeground = getPaddingLeftWithForeground();
        int paddingRightWithForeground = (i3 - i) - getPaddingRightWithForeground();
        int paddingTopWithForeground = getPaddingTopWithForeground();
        int paddingBottomWithForeground = (i4 - i2) - getPaddingBottomWithForeground();
        for (int i7 = 0; i7 < childCount; i7++) {
            android.view.View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                android.widget.FrameLayout.LayoutParams layoutParams = (android.widget.FrameLayout.LayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i8 = layoutParams.gravity;
                if (i8 == -1) {
                    i8 = DEFAULT_CHILD_GRAVITY;
                }
                int absoluteGravity = android.view.Gravity.getAbsoluteGravity(i8, getLayoutDirection());
                int i9 = i8 & 112;
                switch (absoluteGravity & 7) {
                    case 1:
                        i5 = (((((paddingRightWithForeground - paddingLeftWithForeground) - measuredWidth) / 2) + paddingLeftWithForeground) + layoutParams.leftMargin) - layoutParams.rightMargin;
                        break;
                    case 5:
                        if (!z) {
                            i5 = (paddingRightWithForeground - measuredWidth) - layoutParams.rightMargin;
                            break;
                        }
                    default:
                        i5 = layoutParams.leftMargin + paddingLeftWithForeground;
                        break;
                }
                switch (i9) {
                    case 16:
                        i6 = (((((paddingBottomWithForeground - paddingTopWithForeground) - measuredHeight) / 2) + paddingTopWithForeground) + layoutParams.topMargin) - layoutParams.bottomMargin;
                        break;
                    case 48:
                        i6 = paddingTopWithForeground + layoutParams.topMargin;
                        break;
                    case 80:
                        i6 = (paddingBottomWithForeground - measuredHeight) - layoutParams.bottomMargin;
                        break;
                    default:
                        i6 = paddingTopWithForeground + layoutParams.topMargin;
                        break;
                }
                childAt.layout(i5, i6, measuredWidth + i5, measuredHeight + i6);
            }
        }
    }

    @android.view.RemotableViewMethod
    public void setMeasureAllChildren(boolean z) {
        this.mMeasureAllChildren = z;
    }

    @java.lang.Deprecated
    public boolean getConsiderGoneChildrenWhenMeasuring() {
        return getMeasureAllChildren();
    }

    public boolean getMeasureAllChildren() {
        return this.mMeasureAllChildren;
    }

    @Override // android.view.ViewGroup
    public android.widget.FrameLayout.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.widget.FrameLayout.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof android.widget.FrameLayout.LayoutParams;
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (sPreserveMarginParamsInLayoutParamConversion) {
            if (layoutParams instanceof android.widget.FrameLayout.LayoutParams) {
                return new android.widget.FrameLayout.LayoutParams((android.widget.FrameLayout.LayoutParams) layoutParams);
            }
            if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
                return new android.widget.FrameLayout.LayoutParams((android.view.ViewGroup.MarginLayoutParams) layoutParams);
            }
        }
        return new android.widget.FrameLayout.LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.FrameLayout.class.getName();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("measurement:measureAllChildren", this.mMeasureAllChildren);
        viewHierarchyEncoder.addProperty("padding:foregroundPaddingLeft", this.mForegroundPaddingLeft);
        viewHierarchyEncoder.addProperty("padding:foregroundPaddingTop", this.mForegroundPaddingTop);
        viewHierarchyEncoder.addProperty("padding:foregroundPaddingRight", this.mForegroundPaddingRight);
        viewHierarchyEncoder.addProperty("padding:foregroundPaddingBottom", this.mForegroundPaddingBottom);
    }
}
