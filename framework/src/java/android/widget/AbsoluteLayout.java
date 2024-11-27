package android.widget;

@android.widget.RemoteViews.RemoteView
@java.lang.Deprecated
/* loaded from: classes4.dex */
public class AbsoluteLayout extends android.view.ViewGroup {

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        public int x;
        public int y;

        public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.AbsoluteLayout.LayoutParams> {
            private int mLayout_xId;
            private int mLayout_yId;
            private boolean mPropertiesMapped = false;

            @Override // android.view.inspector.InspectionCompanion
            public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
                this.mLayout_xId = propertyMapper.mapInt("layout_x", 16843135);
                this.mLayout_yId = propertyMapper.mapInt("layout_y", 16843136);
                this.mPropertiesMapped = true;
            }

            @Override // android.view.inspector.InspectionCompanion
            public void readProperties(android.widget.AbsoluteLayout.LayoutParams layoutParams, android.view.inspector.PropertyReader propertyReader) {
                if (!this.mPropertiesMapped) {
                    throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
                }
                propertyReader.readInt(this.mLayout_xId, layoutParams.x);
                propertyReader.readInt(this.mLayout_yId, layoutParams.y);
            }
        }

        public LayoutParams(int i, int i2, int i3, int i4) {
            super(i, i2);
            this.x = i3;
            this.y = i4;
        }

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.AbsoluteLayout_Layout);
            this.x = obtainStyledAttributes.getDimensionPixelOffset(0, 0);
            this.y = obtainStyledAttributes.getDimensionPixelOffset(1, 0);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        @Override // android.view.ViewGroup.LayoutParams
        public java.lang.String debug(java.lang.String str) {
            return str + "Absolute.LayoutParams={width=" + sizeToString(this.width) + ", height=" + sizeToString(this.height) + " x=" + this.x + " y=" + this.y + "}";
        }
    }

    public AbsoluteLayout(android.content.Context context) {
        this(context, null);
    }

    public AbsoluteLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AbsoluteLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AbsoluteLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        measureChildren(i, i2);
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < childCount; i5++) {
            android.view.View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                android.widget.AbsoluteLayout.LayoutParams layoutParams = (android.widget.AbsoluteLayout.LayoutParams) childAt.getLayoutParams();
                int measuredWidth = layoutParams.x + childAt.getMeasuredWidth();
                int measuredHeight = layoutParams.y + childAt.getMeasuredHeight();
                i3 = java.lang.Math.max(i3, measuredWidth);
                i4 = java.lang.Math.max(i4, measuredHeight);
            }
        }
        setMeasuredDimension(resolveSizeAndState(java.lang.Math.max(i3 + this.mPaddingLeft + this.mPaddingRight, getSuggestedMinimumWidth()), i, 0), resolveSizeAndState(java.lang.Math.max(i4 + this.mPaddingTop + this.mPaddingBottom, getSuggestedMinimumHeight()), i2, 0));
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new android.widget.AbsoluteLayout.LayoutParams(-2, -2, 0, 0);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            android.view.View childAt = getChildAt(i5);
            if (childAt.getVisibility() != 8) {
                android.widget.AbsoluteLayout.LayoutParams layoutParams = (android.widget.AbsoluteLayout.LayoutParams) childAt.getLayoutParams();
                int i6 = this.mPaddingLeft + layoutParams.x;
                int i7 = this.mPaddingTop + layoutParams.y;
                childAt.layout(i6, i7, childAt.getMeasuredWidth() + i6, childAt.getMeasuredHeight() + i7);
            }
        }
    }

    @Override // android.view.ViewGroup
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.widget.AbsoluteLayout.LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof android.widget.AbsoluteLayout.LayoutParams;
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new android.widget.AbsoluteLayout.LayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
