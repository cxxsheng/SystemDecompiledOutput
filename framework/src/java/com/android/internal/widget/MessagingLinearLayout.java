package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class MessagingLinearLayout extends android.view.ViewGroup {
    private static final boolean TRACE_ONMEASURE = android.os.Build.isDebuggable();
    private int mMaxDisplayedLines;
    private int mSpacing;

    public MessagingLinearLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxDisplayedLines = Integer.MAX_VALUE;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.MessagingLinearLayout, 0, 0);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            switch (obtainStyledAttributes.getIndex(i)) {
                case 0:
                    this.mSpacing = obtainStyledAttributes.getDimensionPixelSize(i, 0);
                    break;
            }
        }
        obtainStyledAttributes.recycle();
    }

    /* JADX WARN: Code restructure failed: missing block: B:55:0x0177, code lost:
    
        r14 = r1;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onMeasure(int i, int i2) {
        int i3;
        com.android.internal.widget.MessagingLinearLayout.LayoutParams layoutParams;
        android.view.View view;
        com.android.internal.widget.MessagingLinearLayout.MessagingChild messagingChild;
        com.android.internal.widget.MessagingLinearLayout.MessagingChild messagingChild2;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        if (TRACE_ONMEASURE) {
            android.os.Trace.beginSection("MessagingLinearLayout#onMeasure");
            trackMeasureSpecs(i, i2);
        }
        int size = android.view.View.MeasureSpec.getSize(i2);
        switch (android.view.View.MeasureSpec.getMode(i2)) {
            case 0:
                i3 = Integer.MAX_VALUE;
                break;
            default:
                i3 = size;
                break;
        }
        int i9 = this.mPaddingLeft + this.mPaddingRight;
        int childCount = getChildCount();
        for (int i10 = 0; i10 < childCount; i10++) {
            android.view.View childAt = getChildAt(i10);
            ((com.android.internal.widget.MessagingLinearLayout.LayoutParams) childAt.getLayoutParams()).hide = true;
            childAt.requestLayout();
            if (childAt instanceof com.android.internal.widget.MessagingLinearLayout.MessagingChild) {
                ((com.android.internal.widget.MessagingLinearLayout.MessagingChild) childAt).setIsFirstInLayout(true);
            }
        }
        int i11 = i9;
        int i12 = childCount - 1;
        int i13 = this.mPaddingTop + this.mPaddingBottom;
        int i14 = this.mMaxDisplayedLines;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        boolean z = true;
        com.android.internal.widget.MessagingLinearLayout.MessagingChild messagingChild3 = null;
        android.view.View view2 = null;
        while (true) {
            if (i12 >= 0 && i13 < i3) {
                if (getChildAt(i12).getVisibility() != 8) {
                    android.view.View childAt2 = getChildAt(i12);
                    com.android.internal.widget.MessagingLinearLayout.LayoutParams layoutParams2 = (com.android.internal.widget.MessagingLinearLayout.LayoutParams) getChildAt(i12).getLayoutParams();
                    int i18 = this.mSpacing;
                    if (!(childAt2 instanceof com.android.internal.widget.MessagingLinearLayout.MessagingChild)) {
                        layoutParams = layoutParams2;
                        view = childAt2;
                        messagingChild = messagingChild3;
                        messagingChild2 = null;
                        i4 = i14;
                        i5 = 0;
                    } else {
                        if (messagingChild3 == null || !messagingChild3.hasDifferentHeightWhenFirst()) {
                            i7 = i18;
                            layoutParams = layoutParams2;
                            view = childAt2;
                            messagingChild = messagingChild3;
                            i8 = 0;
                        } else {
                            messagingChild3.setIsFirstInLayout(false);
                            i7 = i18;
                            layoutParams = layoutParams2;
                            view = childAt2;
                            messagingChild = messagingChild3;
                            measureChildWithMargins(view2, i, 0, i2, i15 - i16);
                            i8 = view2.getMeasuredHeight() - i16;
                            i14 -= messagingChild.getConsumedLines() - i17;
                        }
                        com.android.internal.widget.MessagingLinearLayout.MessagingChild messagingChild4 = (com.android.internal.widget.MessagingLinearLayout.MessagingChild) view;
                        messagingChild4.setMaxDisplayedLines(i14);
                        i18 = i7 + messagingChild4.getExtraSpacing();
                        messagingChild2 = messagingChild4;
                        i4 = i14;
                        i5 = i8;
                    }
                    int i19 = z ? 0 : i18;
                    measureChildWithMargins(view, i, 0, i2, ((i13 - this.mPaddingTop) - this.mPaddingBottom) + i19);
                    int measuredHeight = view.getMeasuredHeight();
                    int max = java.lang.Math.max(i13, i13 + measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin + i19 + i5);
                    if (messagingChild2 == null) {
                        i6 = 0;
                    } else {
                        i6 = messagingChild2.getMeasuredType();
                    }
                    boolean z2 = i6 == 2 && !z;
                    boolean z3 = i6 == 1 || (i6 == 2 && z);
                    if (max <= i3 && !z2) {
                        if (messagingChild2 == null) {
                            i13 = i15;
                            messagingChild3 = messagingChild;
                        } else {
                            i17 = messagingChild2.getConsumedLines();
                            i4 -= i17;
                            i16 = measuredHeight;
                            view2 = view;
                            messagingChild3 = messagingChild2;
                        }
                        i11 = java.lang.Math.max(i11, view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin + this.mPaddingLeft + this.mPaddingRight);
                        layoutParams.hide = false;
                        if (!z3 && i4 > 0) {
                            z = false;
                            i15 = i13;
                            i14 = i4;
                            i13 = max;
                        }
                    } else {
                        com.android.internal.widget.MessagingLinearLayout.MessagingChild messagingChild5 = messagingChild;
                        if (messagingChild5 != null && messagingChild5.hasDifferentHeightWhenFirst()) {
                            messagingChild5.setIsFirstInLayout(true);
                            measureChildWithMargins(view2, i, 0, i2, i15 - i16);
                        }
                    }
                }
                i12--;
            }
        }
        setMeasuredDimension(resolveSize(java.lang.Math.max(getSuggestedMinimumWidth(), i11), i), java.lang.Math.max(getSuggestedMinimumHeight(), i13));
        if (TRACE_ONMEASURE) {
            android.os.Trace.endSection();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = this.mPaddingLeft;
        int i8 = (i3 - i) - this.mPaddingRight;
        int layoutDirection = getLayoutDirection();
        int childCount = getChildCount();
        int i9 = this.mPaddingTop;
        boolean isShown = isShown();
        int i10 = 1;
        boolean z2 = true;
        int i11 = 0;
        while (i11 < childCount) {
            android.view.View childAt = getChildAt(i11);
            if (childAt.getVisibility() != 8) {
                com.android.internal.widget.MessagingLinearLayout.LayoutParams layoutParams = (com.android.internal.widget.MessagingLinearLayout.LayoutParams) childAt.getLayoutParams();
                com.android.internal.widget.MessagingLinearLayout.MessagingChild messagingChild = (com.android.internal.widget.MessagingLinearLayout.MessagingChild) childAt;
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                if (layoutDirection == i10) {
                    i5 = (i8 - measuredWidth) - layoutParams.rightMargin;
                } else {
                    i5 = i7 + layoutParams.leftMargin;
                }
                if (layoutParams.hide) {
                    if (isShown && layoutParams.visibleBefore) {
                        childAt.layout(i5, i9, measuredWidth + i5, layoutParams.lastVisibleHeight + i9);
                        messagingChild.hideAnimated();
                    }
                    layoutParams.visibleBefore = false;
                } else {
                    i6 = 1;
                    layoutParams.visibleBefore = true;
                    layoutParams.lastVisibleHeight = measuredHeight;
                    if (!z2) {
                        i9 += this.mSpacing;
                    }
                    int i12 = i9 + layoutParams.topMargin;
                    childAt.layout(i5, i12, measuredWidth + i5, i12 + measuredHeight);
                    i9 = i12 + measuredHeight + layoutParams.bottomMargin;
                    z2 = false;
                    i11++;
                    i10 = i6;
                }
            }
            i6 = 1;
            i11++;
            i10 = i6;
        }
    }

    private void trackMeasureSpecs(int i, int i2) {
        if (!TRACE_ONMEASURE) {
            return;
        }
        int size = android.view.View.MeasureSpec.getSize(i);
        int mode = android.view.View.MeasureSpec.getMode(i);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        android.os.Trace.setCounter("MessagingLinearLayout#onMeasure_widthMeasureSpecSize", size);
        android.os.Trace.setCounter("MessagingLinearLayout#onMeasure_widthMeasureSpecMode", mode);
        android.os.Trace.setCounter("MessagingLinearLayout#onMeasure_heightMeasureSpecSize", size2);
        android.os.Trace.setCounter("MessagingLinearLayout#onMeasure_heightMeasureSpecMode", mode2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ViewGroup
    protected boolean drawChild(android.graphics.Canvas canvas, android.view.View view, long j) {
        if (((com.android.internal.widget.MessagingLinearLayout.LayoutParams) view.getLayoutParams()).hide && !((com.android.internal.widget.MessagingLinearLayout.MessagingChild) view).isHidingAnimated()) {
            return true;
        }
        return super.drawChild(canvas, view, j);
    }

    public void setSpacing(int i) {
        if (this.mSpacing != i) {
            this.mSpacing = i;
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup
    public com.android.internal.widget.MessagingLinearLayout.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new com.android.internal.widget.MessagingLinearLayout.LayoutParams(this.mContext, attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public com.android.internal.widget.MessagingLinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new com.android.internal.widget.MessagingLinearLayout.LayoutParams(-1, -2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public com.android.internal.widget.MessagingLinearLayout.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        com.android.internal.widget.MessagingLinearLayout.LayoutParams layoutParams2 = new com.android.internal.widget.MessagingLinearLayout.LayoutParams(layoutParams.width, layoutParams.height);
        if (layoutParams instanceof android.view.ViewGroup.MarginLayoutParams) {
            layoutParams2.copyMarginsFrom((android.view.ViewGroup.MarginLayoutParams) layoutParams);
        }
        return layoutParams2;
    }

    public static boolean isGone(android.view.View view) {
        if (view.getVisibility() == 8) {
            return true;
        }
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        return (layoutParams instanceof com.android.internal.widget.MessagingLinearLayout.LayoutParams) && ((com.android.internal.widget.MessagingLinearLayout.LayoutParams) layoutParams).hide;
    }

    @android.view.RemotableViewMethod
    public void setMaxDisplayedLines(int i) {
        this.mMaxDisplayedLines = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public com.android.internal.widget.IMessagingLayout getMessagingLayout() {
        android.view.View view = this;
        do {
            java.lang.Object parent = view.getParent();
            if (parent instanceof android.view.View) {
                view = (android.view.View) parent;
            } else {
                return null;
            }
        } while (!(view instanceof com.android.internal.widget.IMessagingLayout));
        return (com.android.internal.widget.IMessagingLayout) view;
    }

    @Override // android.view.View
    public int getBaseline() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            android.view.View childAt = getChildAt(childCount);
            if (!isGone(childAt)) {
                int baseline = childAt.getBaseline();
                if (baseline == -1) {
                    return -1;
                }
                return ((android.view.ViewGroup.MarginLayoutParams) childAt.getLayoutParams()).topMargin + baseline;
            }
        }
        return super.getBaseline();
    }

    public interface MessagingChild {
        public static final int MEASURED_NORMAL = 0;
        public static final int MEASURED_SHORTENED = 1;
        public static final int MEASURED_TOO_SMALL = 2;

        int getConsumedLines();

        int getMeasuredType();

        void hideAnimated();

        boolean isHidingAnimated();

        void recycle();

        void setMaxDisplayedLines(int i);

        default void setIsFirstInLayout(boolean z) {
        }

        default boolean hasDifferentHeightWhenFirst() {
            return false;
        }

        default int getExtraSpacing() {
            return 0;
        }
    }

    public static class LayoutParams extends android.view.ViewGroup.MarginLayoutParams {
        public boolean hide;
        public int lastVisibleHeight;
        public boolean visibleBefore;

        public LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.hide = false;
            this.visibleBefore = false;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.hide = false;
            this.visibleBefore = false;
        }
    }
}
