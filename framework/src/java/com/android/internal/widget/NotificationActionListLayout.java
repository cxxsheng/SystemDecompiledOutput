package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class NotificationActionListLayout extends android.widget.LinearLayout {
    public static final java.util.Comparator<com.android.internal.widget.NotificationActionListLayout.TextViewInfo> MEASURE_ORDER_COMPARATOR = new java.util.Comparator() { // from class: com.android.internal.widget.NotificationActionListLayout$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
            return com.android.internal.widget.NotificationActionListLayout.lambda$static$0((com.android.internal.widget.NotificationActionListLayout.TextViewInfo) obj, (com.android.internal.widget.NotificationActionListLayout.TextViewInfo) obj2);
        }
    };
    private static final java.lang.String TAG = "NotificationActionListLayout";
    private int mCollapsibleIndentDimen;
    private int mDefaultPaddingBottom;
    private int mDefaultPaddingTop;
    private int mEmphasizedHeight;
    private boolean mEmphasizedMode;
    private int mEmphasizedPaddingBottom;
    private int mEmphasizedPaddingTop;
    private boolean mEvenlyDividedMode;
    private int mExtraStartPadding;
    private final int mGravity;
    private java.util.ArrayList<android.view.View> mMeasureOrderOther;
    private java.util.ArrayList<com.android.internal.widget.NotificationActionListLayout.TextViewInfo> mMeasureOrderTextViews;
    int mNumNotGoneChildren;
    int mNumPriorityChildren;
    private int mRegularHeight;
    private int mTotalWidth;

    public NotificationActionListLayout(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotificationActionListLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationActionListLayout(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTotalWidth = 0;
        this.mExtraStartPadding = 0;
        this.mMeasureOrderTextViews = new java.util.ArrayList<>();
        this.mMeasureOrderOther = new java.util.ArrayList<>();
        this.mCollapsibleIndentDimen = com.android.internal.R.dimen.notification_actions_padding_start;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16842927}, i, i2);
        this.mGravity = obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPriority(android.view.View view) {
        return (view instanceof com.android.internal.widget.EmphasizedNotificationButton) && ((com.android.internal.widget.EmphasizedNotificationButton) view).isPriority();
    }

    private void countAndRebuildMeasureOrder() {
        boolean z;
        boolean z2;
        int childCount = getChildCount();
        this.mNumNotGoneChildren = 0;
        this.mNumPriorityChildren = 0;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            z = true;
            if (i >= childCount) {
                break;
            }
            android.view.View childAt = getChildAt(i);
            if (childAt instanceof android.widget.TextView) {
                i2++;
            } else {
                i3++;
            }
            if (childAt.getVisibility() != 8) {
                this.mNumNotGoneChildren++;
                if (isPriority(childAt)) {
                    this.mNumPriorityChildren++;
                }
            }
            i++;
        }
        if (i2 == this.mMeasureOrderTextViews.size() && i3 == this.mMeasureOrderOther.size()) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (!z2) {
            int size = this.mMeasureOrderTextViews.size();
            for (int i4 = 0; i4 < size; i4++) {
                if (this.mMeasureOrderTextViews.get(i4).needsRebuild()) {
                    break;
                }
            }
        }
        z = z2;
        if (z) {
            rebuildMeasureOrder(i2, i3);
        }
    }

    private int measureAndReturnEvenlyDividedWidth(int i, int i2) {
        int childCount = getChildCount();
        int i3 = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            android.view.View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                i3 += marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            }
        }
        int i5 = i2 - i3;
        int i6 = i5 / this.mNumNotGoneChildren;
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(i6, 1073741824);
        android.util.Log.v(TAG, "measuring evenly divided width: numChildren = " + childCount + ", innerWidth = " + i2 + "px, childMarginSum = " + i3 + "px, innerWidthMinusChildMargins = " + i5 + "px, childWidth = " + i6 + "px, childWidthMeasureSpec = " + android.view.View.MeasureSpec.toString(makeMeasureSpec));
        for (int i7 = 0; i7 < childCount; i7++) {
            android.view.View childAt2 = getChildAt(i7);
            if (childAt2.getVisibility() != 8) {
                childAt2.measure(makeMeasureSpec, i);
            }
        }
        return i2;
    }

    private int measureAndGetUsedWidth(int i, int i2, int i3, boolean z) {
        boolean z2;
        android.view.View view;
        int i4;
        int i5;
        int childCount = getChildCount();
        boolean z3 = android.view.View.MeasureSpec.getMode(i) != 0;
        int size = this.mMeasureOrderOther.size();
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        for (int i10 = 0; i10 < childCount; i10++) {
            if (i10 < size) {
                view = this.mMeasureOrderOther.get(i10);
                z2 = false;
            } else {
                com.android.internal.widget.NotificationActionListLayout.TextViewInfo textViewInfo = this.mMeasureOrderTextViews.get(i10 - size);
                android.widget.TextView textView = textViewInfo.mTextView;
                z2 = textViewInfo.mIsPriority;
                view = textView;
            }
            if (view.getVisibility() != 8) {
                android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) view.getLayoutParams();
                if (!z3) {
                    i4 = i6;
                    i5 = i7;
                } else {
                    int i11 = i3 - i7;
                    int i12 = this.mNumNotGoneChildren - i8;
                    int i13 = i11 / i12;
                    if (z2 && z) {
                        if (i6 == 0) {
                            i6 = getResources().getDimensionPixelSize(com.android.internal.R.dimen.notification_actions_collapsed_priority_width);
                        }
                        i13 = marginLayoutParams.leftMargin + i6 + marginLayoutParams.rightMargin;
                    } else if (z2) {
                        int i14 = this.mNumPriorityChildren - i9;
                        i13 = (i11 - (((i12 - i14) * i3) / 4)) / i14;
                    }
                    i4 = i6;
                    i5 = i3 - i13;
                }
                measureChildWithMargins(view, i, i5, i2, 0);
                i7 += view.getMeasuredWidth() + marginLayoutParams.rightMargin + marginLayoutParams.leftMargin;
                i8++;
                if (!z2) {
                    i6 = i4;
                } else {
                    i9++;
                    i6 = i4;
                }
            }
        }
        int dimensionPixelOffset = this.mCollapsibleIndentDimen == 0 ? 0 : getResources().getDimensionPixelOffset(this.mCollapsibleIndentDimen);
        if (i3 - i7 <= dimensionPixelOffset) {
            this.mExtraStartPadding = 0;
        } else {
            this.mExtraStartPadding = dimensionPixelOffset;
        }
        return i7;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        countAndRebuildMeasureOrder();
        int size = (android.view.View.MeasureSpec.getSize(i) - this.mPaddingLeft) - this.mPaddingRight;
        if (this.mEvenlyDividedMode) {
            i3 = measureAndReturnEvenlyDividedWidth(i2, size);
        } else {
            int measureAndGetUsedWidth = measureAndGetUsedWidth(i, i2, size, false);
            if (this.mNumPriorityChildren != 0 && measureAndGetUsedWidth >= size) {
                i3 = measureAndGetUsedWidth(i, i2, size, true);
            } else {
                i3 = measureAndGetUsedWidth;
            }
        }
        this.mTotalWidth = i3 + this.mPaddingRight + this.mPaddingLeft + this.mExtraStartPadding;
        setMeasuredDimension(resolveSize(getSuggestedMinimumWidth(), i), resolveSize(getSuggestedMinimumHeight(), i2));
    }

    private void rebuildMeasureOrder(int i, int i2) {
        clearMeasureOrder();
        this.mMeasureOrderTextViews.ensureCapacity(i);
        this.mMeasureOrderOther.ensureCapacity(i2);
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            android.view.View childAt = getChildAt(i3);
            if (childAt instanceof android.widget.TextView) {
                android.widget.TextView textView = (android.widget.TextView) childAt;
                if (textView.getText().length() > 0) {
                    this.mMeasureOrderTextViews.add(new com.android.internal.widget.NotificationActionListLayout.TextViewInfo(textView));
                }
            }
            this.mMeasureOrderOther.add(childAt);
        }
        this.mMeasureOrderTextViews.sort(MEASURE_ORDER_COMPARATOR);
    }

    private void clearMeasureOrder() {
        this.mMeasureOrderOther.clear();
        this.mMeasureOrderTextViews.clear();
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(android.view.View view) {
        super.onViewAdded(view);
        clearMeasureOrder();
        if (view.getBackground() instanceof android.graphics.drawable.RippleDrawable) {
            ((android.graphics.drawable.RippleDrawable) view.getBackground()).setForceSoftware(true);
        }
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(android.view.View view) {
        super.onViewRemoved(view);
        clearMeasureOrder();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        boolean isLayoutRtl = isLayoutRtl();
        int i7 = this.mPaddingTop;
        int i8 = 1;
        if ((this.mGravity & 1) != 0) {
            i5 = ((this.mPaddingLeft + i) + ((i3 - i) / 2)) - (this.mTotalWidth / 2);
        } else {
            int i9 = this.mPaddingLeft;
            if (android.view.Gravity.getAbsoluteGravity(android.view.Gravity.START, getLayoutDirection()) == 5) {
                i5 = i9 + ((i3 - i) - this.mTotalWidth);
            } else {
                i5 = i9 + this.mExtraStartPadding;
            }
        }
        int i10 = ((i4 - i2) - i7) - this.mPaddingBottom;
        int childCount = getChildCount();
        if (!isLayoutRtl) {
            i6 = 0;
        } else {
            i6 = childCount - 1;
            i8 = -1;
        }
        for (int i11 = 0; i11 < childCount; i11++) {
            android.view.View childAt = getChildAt((i8 * i11) + i6);
            if (childAt.getVisibility() != 8) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                int i12 = ((((i10 - measuredHeight) / 2) + i7) + marginLayoutParams.topMargin) - marginLayoutParams.bottomMargin;
                int i13 = i5 + marginLayoutParams.leftMargin;
                childAt.layout(i13, i12, i13 + measuredWidth, measuredHeight + i12);
                i5 = i13 + measuredWidth + marginLayoutParams.rightMargin;
            }
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mDefaultPaddingBottom = getPaddingBottom();
        this.mDefaultPaddingTop = getPaddingTop();
        updateHeights();
    }

    private void updateHeights() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(com.android.internal.R.dimen.button_inset_vertical_material);
        this.mEmphasizedPaddingTop = getResources().getDimensionPixelSize(com.android.internal.R.dimen.notification_content_margin) - dimensionPixelSize;
        this.mEmphasizedPaddingBottom = getResources().getDimensionPixelSize(com.android.internal.R.dimen.notification_content_margin_end) - dimensionPixelSize;
        this.mEmphasizedHeight = this.mEmphasizedPaddingTop + this.mEmphasizedPaddingBottom + getResources().getDimensionPixelSize(com.android.internal.R.dimen.notification_action_emphasized_height);
        this.mRegularHeight = getResources().getDimensionPixelSize(com.android.internal.R.dimen.notification_action_list_height);
    }

    @android.view.RemotableViewMethod
    public void setCollapsibleIndentDimen(int i) {
        if (this.mCollapsibleIndentDimen != i) {
            this.mCollapsibleIndentDimen = i;
            requestLayout();
        }
    }

    @android.view.RemotableViewMethod
    public void setEvenlyDividedMode(boolean z) {
        if (z && !android.app.Flags.evenlyDividedCallStyleActionLayout()) {
            android.util.Log.e(TAG, "setEvenlyDividedMode(true) called with new action layout disabled; leaving evenly divided mode disabled");
        } else if (z != this.mEvenlyDividedMode) {
            android.util.Log.v(TAG, "evenlyDividedMode changed to " + z + "; requesting layout");
            this.mEvenlyDividedMode = z;
            requestLayout();
        }
    }

    @android.view.RemotableViewMethod
    public void setEmphasizedMode(boolean z) {
        int i;
        this.mEmphasizedMode = z;
        if (z) {
            setPaddingRelative(getPaddingStart(), this.mEmphasizedPaddingTop, getPaddingEnd(), this.mEmphasizedPaddingBottom);
            setMinimumHeight(this.mEmphasizedHeight);
            i = -2;
        } else {
            setPaddingRelative(getPaddingStart(), this.mDefaultPaddingTop, getPaddingEnd(), this.mDefaultPaddingBottom);
            i = this.mRegularHeight;
        }
        android.view.ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = i;
        setLayoutParams(layoutParams);
    }

    public int getExtraMeasureHeight() {
        if (this.mEmphasizedMode) {
            return this.mEmphasizedHeight - this.mRegularHeight;
        }
        return 0;
    }

    static /* synthetic */ int lambda$static$0(com.android.internal.widget.NotificationActionListLayout.TextViewInfo textViewInfo, com.android.internal.widget.NotificationActionListLayout.TextViewInfo textViewInfo2) {
        int i = -java.lang.Boolean.compare(textViewInfo.mIsPriority, textViewInfo2.mIsPriority);
        if (i != 0) {
            return i;
        }
        return java.lang.Integer.compare(textViewInfo.mTextLength, textViewInfo2.mTextLength);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TextViewInfo {
        final boolean mIsPriority;
        final int mTextLength;
        final android.widget.TextView mTextView;

        TextViewInfo(android.widget.TextView textView) {
            this.mIsPriority = com.android.internal.widget.NotificationActionListLayout.isPriority(textView);
            this.mTextLength = textView.getText().length();
            this.mTextView = textView;
        }

        boolean needsRebuild() {
            return (this.mTextView.getText().length() == this.mTextLength && com.android.internal.widget.NotificationActionListLayout.isPriority(this.mTextView) == this.mIsPriority) ? false : true;
        }
    }
}
