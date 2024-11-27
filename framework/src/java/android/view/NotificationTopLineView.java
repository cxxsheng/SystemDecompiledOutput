package android.view;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class NotificationTopLineView extends android.view.ViewGroup {
    private android.view.View mAppName;
    private final int mChildHideWidth;
    private final int mChildMinWidth;
    private android.view.View mFeedbackIcon;
    private android.view.View.OnClickListener mFeedbackListener;
    private final int mGravityY;
    private android.view.View mHeaderText;
    private android.view.View mHeaderTextDivider;
    private int mHeaderTextMarginEnd;
    private int mMaxAscent;
    private int mMaxDescent;
    private final android.view.NotificationTopLineView.OverflowAdjuster mOverflowAdjuster;
    private android.view.View mSecondaryHeaderText;
    private android.view.View mSecondaryHeaderTextDivider;
    private android.view.View mTitle;
    private android.view.NotificationTopLineView.HeaderTouchListener mTouchListener;
    private java.util.Set<android.view.View> mViewsToDisappear;

    public NotificationTopLineView(android.content.Context context) {
        this(context, null);
    }

    public NotificationTopLineView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotificationTopLineView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationTopLineView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mOverflowAdjuster = new android.view.NotificationTopLineView.OverflowAdjuster();
        this.mTouchListener = new android.view.NotificationTopLineView.HeaderTouchListener();
        this.mViewsToDisappear = new java.util.HashSet();
        android.content.res.Resources resources = getResources();
        this.mChildMinWidth = resources.getDimensionPixelSize(com.android.internal.R.dimen.notification_header_shrink_min_width);
        this.mChildHideWidth = resources.getDimensionPixelSize(com.android.internal.R.dimen.notification_header_shrink_hide_width);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16842927}, i, i2);
        int i3 = obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
        if ((i3 & 80) == 80) {
            this.mGravityY = 80;
        } else if ((i3 & 48) == 48) {
            this.mGravityY = 48;
        } else {
            this.mGravityY = 16;
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mAppName = findViewById(com.android.internal.R.id.app_name_text);
        this.mTitle = findViewById(16908310);
        this.mHeaderText = findViewById(com.android.internal.R.id.header_text);
        this.mHeaderTextDivider = findViewById(com.android.internal.R.id.header_text_divider);
        this.mSecondaryHeaderText = findViewById(com.android.internal.R.id.header_text_secondary);
        this.mSecondaryHeaderTextDivider = findViewById(com.android.internal.R.id.header_text_secondary_divider);
        this.mFeedbackIcon = findViewById(com.android.internal.R.id.feedback);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        android.os.Trace.beginSection("NotificationTopLineView#onMeasure");
        int size = android.view.View.MeasureSpec.getSize(i);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        boolean z = android.view.View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE;
        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
        int makeMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE);
        int paddingStart = getPaddingStart();
        this.mMaxAscent = -1;
        this.mMaxDescent = -1;
        int i3 = -1;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            android.view.View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                childAt.measure(getChildMeasureSpec(makeMeasureSpec, marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, marginLayoutParams.width), getChildMeasureSpec(makeMeasureSpec2, marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height));
                paddingStart += marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + childAt.getMeasuredWidth();
                int baseline = childAt.getBaseline();
                int measuredHeight = childAt.getMeasuredHeight();
                if (baseline != -1) {
                    this.mMaxAscent = java.lang.Math.max(this.mMaxAscent, baseline);
                    this.mMaxDescent = java.lang.Math.max(this.mMaxDescent, measuredHeight - baseline);
                }
                i3 = java.lang.Math.max(i3, measuredHeight);
            }
        }
        this.mViewsToDisappear.clear();
        int max = java.lang.Math.max(this.mHeaderTextMarginEnd, getPaddingEnd());
        if (paddingStart > size - max) {
            this.mOverflowAdjuster.resetForOverflow((paddingStart - size) + max, makeMeasureSpec2).adjust(this.mAppName, null, this.mChildMinWidth).adjust(this.mHeaderText, this.mHeaderTextDivider, this.mChildMinWidth).adjust(this.mSecondaryHeaderText, this.mSecondaryHeaderTextDivider, 0).adjust(this.mTitle, null, this.mChildMinWidth).adjust(this.mHeaderText, this.mHeaderTextDivider, 0).adjust(this.mTitle, null, 0).finish();
        }
        if (z) {
            size2 = i3;
        }
        setMeasuredDimension(size, size2);
        android.os.Trace.endSection();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        boolean z2 = getLayoutDirection() == 1;
        int width = getWidth();
        int paddingStart = getPaddingStart();
        int childCount = getChildCount();
        int i6 = i4 - i2;
        int i7 = (i6 - this.mPaddingTop) - this.mPaddingBottom;
        int i8 = this.mPaddingTop + ((i7 - (this.mMaxAscent + this.mMaxDescent)) / 2) + this.mMaxAscent;
        for (int i9 = 0; i9 < childCount; i9++) {
            android.view.View childAt = getChildAt(i9);
            if (childAt.getVisibility() != 8) {
                int measuredHeight = childAt.getMeasuredHeight();
                android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                int baseline = childAt.getBaseline();
                switch (this.mGravityY) {
                    case 16:
                        if (baseline != -1) {
                            int i10 = i7 - measuredHeight;
                            if (i10 > 0) {
                                i5 = i8 - baseline;
                                break;
                            } else {
                                i5 = (i10 / 2) + this.mPaddingTop;
                                break;
                            }
                        } else {
                            i5 = ((this.mPaddingTop + ((i7 - measuredHeight) / 2)) + marginLayoutParams.topMargin) - marginLayoutParams.bottomMargin;
                            break;
                        }
                    case 48:
                        i5 = this.mPaddingTop + marginLayoutParams.topMargin;
                        if (baseline != -1) {
                            i5 += this.mMaxAscent - baseline;
                            break;
                        }
                        break;
                    case 80:
                        i5 = ((i6 - this.mPaddingBottom) - measuredHeight) - marginLayoutParams.bottomMargin;
                        if (baseline != -1) {
                            i5 -= this.mMaxDescent - (measuredHeight - baseline);
                            break;
                        }
                        break;
                    default:
                        i5 = this.mPaddingTop;
                        break;
                }
                if (this.mViewsToDisappear.contains(childAt)) {
                    childAt.layout(paddingStart, i5, paddingStart, measuredHeight + i5);
                } else {
                    int marginStart = paddingStart + marginLayoutParams.getMarginStart();
                    int measuredWidth = childAt.getMeasuredWidth() + marginStart;
                    int i11 = z2 ? width - measuredWidth : marginStart;
                    int i12 = z2 ? width - marginStart : measuredWidth;
                    int marginEnd = measuredWidth + marginLayoutParams.getMarginEnd();
                    childAt.layout(i11, i5, i12, measuredHeight + i5);
                    paddingStart = marginEnd;
                }
            }
        }
        updateTouchListener();
    }

    @Override // android.view.ViewGroup
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.view.ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    private void updateTouchListener() {
        if (this.mFeedbackListener == null) {
            setOnTouchListener(null);
        } else {
            setOnTouchListener(this.mTouchListener);
            this.mTouchListener.bindTouchRects();
        }
    }

    public void setFeedbackOnClickListener(android.view.View.OnClickListener onClickListener) {
        this.mFeedbackListener = onClickListener;
        this.mFeedbackIcon.setOnClickListener(this.mFeedbackListener);
        updateTouchListener();
    }

    public void setHeaderTextMarginEnd(int i) {
        if (this.mHeaderTextMarginEnd != i) {
            this.mHeaderTextMarginEnd = i;
            requestLayout();
        }
    }

    public int getHeaderTextMarginEnd() {
        return this.mHeaderTextMarginEnd;
    }

    public void setPaddingStart(int i) {
        setPaddingRelative(i, getPaddingTop(), getPaddingEnd(), getPaddingBottom());
    }

    private class HeaderTouchListener implements android.view.View.OnTouchListener {
        private float mDownX;
        private float mDownY;
        private android.graphics.Rect mFeedbackRect;
        private int mTouchSlop;
        private boolean mTrackGesture;

        HeaderTouchListener() {
        }

        public void bindTouchRects() {
            this.mFeedbackRect = getRectAroundView(android.view.NotificationTopLineView.this.mFeedbackIcon);
            this.mTouchSlop = android.view.ViewConfiguration.get(android.view.NotificationTopLineView.this.getContext()).getScaledTouchSlop();
        }

        private android.graphics.Rect getRectAroundView(android.view.View view) {
            float f = android.view.NotificationTopLineView.this.getResources().getDisplayMetrics().density * 48.0f;
            float max = java.lang.Math.max(f, view.getWidth());
            float max2 = java.lang.Math.max(f, view.getHeight());
            android.graphics.Rect rect = new android.graphics.Rect();
            if (view.getVisibility() == 8) {
                view = android.view.NotificationTopLineView.this.getFirstChildNotGone();
                rect.left = (int) (view.getLeft() - (max / 2.0f));
            } else {
                rect.left = (int) (((view.getLeft() + view.getRight()) / 2.0f) - (max / 2.0f));
            }
            rect.top = (int) (((view.getTop() + view.getBottom()) / 2.0f) - (max2 / 2.0f));
            rect.bottom = (int) (rect.top + max2);
            rect.right = (int) (rect.left + max);
            return rect;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            switch (motionEvent.getActionMasked() & 255) {
                case 0:
                    this.mTrackGesture = false;
                    if (isInside(x, y)) {
                        this.mDownX = x;
                        this.mDownY = y;
                        this.mTrackGesture = true;
                        return true;
                    }
                    break;
                case 1:
                    if (this.mTrackGesture && onTouchUp(x, y, this.mDownX, this.mDownY)) {
                        return true;
                    }
                    break;
                case 2:
                    if (this.mTrackGesture && (java.lang.Math.abs(this.mDownX - x) > this.mTouchSlop || java.lang.Math.abs(this.mDownY - y) > this.mTouchSlop)) {
                        this.mTrackGesture = false;
                        break;
                    }
                    break;
            }
            return this.mTrackGesture;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean onTouchUp(float f, float f2, float f3, float f4) {
            if (android.view.NotificationTopLineView.this.mFeedbackIcon.isVisibleToUser()) {
                if (this.mFeedbackRect.contains((int) f, (int) f2) || this.mFeedbackRect.contains((int) f3, (int) f4)) {
                    android.view.NotificationTopLineView.this.mFeedbackIcon.performClick();
                    return true;
                }
                return false;
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isInside(float f, float f2) {
            return this.mFeedbackRect.contains((int) f, (int) f2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.View getFirstChildNotGone() {
        for (int i = 0; i < getChildCount(); i++) {
            android.view.View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                return childAt;
            }
        }
        return this;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isInTouchRect(float f, float f2) {
        if (this.mFeedbackListener == null) {
            return false;
        }
        return this.mTouchListener.isInside(f, f2);
    }

    public boolean onTouchUp(float f, float f2, float f3, float f4) {
        if (this.mFeedbackListener == null) {
            return false;
        }
        return this.mTouchListener.onTouchUp(f, f2, f3, f4);
    }

    private final class OverflowAdjuster {
        private int mHeightSpec;
        private int mOverflow;
        private android.view.View mRegrowView;

        private OverflowAdjuster() {
        }

        android.view.NotificationTopLineView.OverflowAdjuster resetForOverflow(int i, int i2) {
            this.mOverflow = i;
            this.mHeightSpec = i2;
            this.mRegrowView = null;
            return this;
        }

        android.view.NotificationTopLineView.OverflowAdjuster adjust(android.view.View view, android.view.View view2, int i) {
            if (this.mOverflow <= 0 || view == null || view.getVisibility() == 8) {
                return this;
            }
            int measuredWidth = view.getMeasuredWidth();
            if (measuredWidth <= i) {
                return this;
            }
            int max = java.lang.Math.max(i, measuredWidth - this.mOverflow);
            if (i == 0 && max < android.view.NotificationTopLineView.this.mChildHideWidth && this.mRegrowView != null && this.mRegrowView != view) {
                max = 0;
            }
            view.measure(android.view.View.MeasureSpec.makeMeasureSpec(max, Integer.MIN_VALUE), this.mHeightSpec);
            this.mOverflow -= measuredWidth - max;
            if (max == 0) {
                android.view.NotificationTopLineView.this.mViewsToDisappear.add(view);
                this.mOverflow -= getHorizontalMargins(view);
                if (view2 != null && view2.getVisibility() != 8) {
                    android.view.NotificationTopLineView.this.mViewsToDisappear.add(view2);
                    int measuredWidth2 = view2.getMeasuredWidth();
                    view2.measure(android.view.View.MeasureSpec.makeMeasureSpec(0, Integer.MIN_VALUE), this.mHeightSpec);
                    this.mOverflow -= measuredWidth2 + getHorizontalMargins(view2);
                }
            }
            if (this.mOverflow < 0 && this.mRegrowView != null) {
                this.mRegrowView.measure(android.view.View.MeasureSpec.makeMeasureSpec(this.mRegrowView.getMeasuredWidth() - this.mOverflow, Integer.MIN_VALUE), this.mHeightSpec);
                finish();
                return this;
            }
            if (max != 0) {
                this.mRegrowView = view;
            }
            return this;
        }

        void finish() {
            resetForOverflow(0, 0);
        }

        private int getHorizontalMargins(android.view.View view) {
            android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) view.getLayoutParams();
            return marginLayoutParams.getMarginStart() + marginLayoutParams.getMarginEnd();
        }
    }
}
