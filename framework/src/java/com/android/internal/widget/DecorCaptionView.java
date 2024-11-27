package com.android.internal.widget;

/* loaded from: classes5.dex */
public class DecorCaptionView extends android.view.ViewGroup implements android.view.View.OnTouchListener, android.view.GestureDetector.OnGestureListener {
    private android.view.View mCaption;
    private boolean mCheckForDragging;
    private android.view.View mClickTarget;
    private android.view.View mClose;
    private final android.graphics.Rect mCloseRect;
    private android.view.View mContent;
    private int mDragSlop;
    private boolean mDragging;
    private android.view.GestureDetector mGestureDetector;
    private android.view.View mMaximize;
    private final android.graphics.Rect mMaximizeRect;
    private boolean mOverlayWithAppContent;
    private com.android.internal.policy.PhoneWindow mOwner;
    private int mRootScrollY;
    private boolean mShow;
    private java.util.ArrayList<android.view.View> mTouchDispatchList;
    private int mTouchDownX;
    private int mTouchDownY;

    public DecorCaptionView(android.content.Context context) {
        super(context);
        this.mOwner = null;
        this.mShow = false;
        this.mDragging = false;
        this.mOverlayWithAppContent = false;
        this.mTouchDispatchList = new java.util.ArrayList<>(2);
        this.mCloseRect = new android.graphics.Rect();
        this.mMaximizeRect = new android.graphics.Rect();
        init(context);
    }

    public DecorCaptionView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOwner = null;
        this.mShow = false;
        this.mDragging = false;
        this.mOverlayWithAppContent = false;
        this.mTouchDispatchList = new java.util.ArrayList<>(2);
        this.mCloseRect = new android.graphics.Rect();
        this.mMaximizeRect = new android.graphics.Rect();
        init(context);
    }

    public DecorCaptionView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOwner = null;
        this.mShow = false;
        this.mDragging = false;
        this.mOverlayWithAppContent = false;
        this.mTouchDispatchList = new java.util.ArrayList<>(2);
        this.mCloseRect = new android.graphics.Rect();
        this.mMaximizeRect = new android.graphics.Rect();
        init(context);
    }

    private void init(android.content.Context context) {
        this.mDragSlop = android.view.ViewConfiguration.get(context).getScaledTouchSlop();
        this.mGestureDetector = new android.view.GestureDetector(context, this);
        setContentDescription(context.getString(com.android.internal.R.string.accessibility_freeform_caption, context.getPackageManager().getApplicationLabel(context.getApplicationInfo())));
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mCaption = getChildAt(0);
    }

    public void setPhoneWindow(com.android.internal.policy.PhoneWindow phoneWindow, boolean z) {
        this.mOwner = phoneWindow;
        this.mShow = z;
        this.mOverlayWithAppContent = phoneWindow.isOverlayWithDecorCaptionEnabled();
        updateCaptionVisibility();
        this.mOwner.getDecorView().setOutlineProvider(android.view.ViewOutlineProvider.BOUNDS);
        this.mMaximize = findViewById(com.android.internal.R.id.maximize_window);
        this.mClose = findViewById(com.android.internal.R.id.close_window);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (this.mMaximizeRect.contains(x, y - this.mRootScrollY)) {
                this.mClickTarget = this.mMaximize;
            }
            if (this.mCloseRect.contains(x, y - this.mRootScrollY)) {
                this.mClickTarget = this.mClose;
            }
        }
        return this.mClickTarget != null;
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        if (this.mClickTarget != null) {
            this.mGestureDetector.onTouchEvent(motionEvent);
            int action = motionEvent.getAction();
            if (action == 1 || action == 3) {
                this.mClickTarget = null;
            }
            return true;
        }
        return false;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        boolean z = motionEvent.getToolType(motionEvent.getActionIndex()) == 3;
        boolean z2 = (motionEvent.getButtonState() & 1) != 0;
        int actionMasked = motionEvent.getActionMasked();
        switch (actionMasked) {
            case 0:
                if (!this.mShow) {
                    return false;
                }
                if (!z || z2) {
                    this.mCheckForDragging = true;
                    this.mTouchDownX = x;
                    this.mTouchDownY = y;
                    break;
                }
            case 1:
            case 3:
                if (this.mDragging) {
                    if (actionMasked == 1) {
                        finishMovingTask();
                    }
                    this.mDragging = false;
                    return !this.mCheckForDragging;
                }
                break;
            case 2:
                if (!this.mDragging && this.mCheckForDragging && (z || passedSlop(x, y))) {
                    this.mCheckForDragging = false;
                    this.mDragging = true;
                    startMovingTask(motionEvent.getRawX(), motionEvent.getRawY());
                    break;
                }
                break;
        }
        return this.mDragging || this.mCheckForDragging;
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    private boolean passedSlop(int i, int i2) {
        return java.lang.Math.abs(i - this.mTouchDownX) > this.mDragSlop || java.lang.Math.abs(i2 - this.mTouchDownY) > this.mDragSlop;
    }

    public void onConfigurationChanged(boolean z) {
        this.mShow = z;
        updateCaptionVisibility();
    }

    @Override // android.view.ViewGroup
    public void addView(android.view.View view, int i, android.view.ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof android.view.ViewGroup.MarginLayoutParams)) {
            throw new java.lang.IllegalArgumentException("params " + layoutParams + " must subclass MarginLayoutParams");
        }
        if (i >= 2 || getChildCount() >= 2) {
            throw new java.lang.IllegalStateException("DecorCaptionView can only handle 1 client view");
        }
        super.addView(view, 0, layoutParams);
        this.mContent = view;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        if (this.mCaption.getVisibility() != 8) {
            measureChildWithMargins(this.mCaption, i, 0, i2, 0);
            i3 = this.mCaption.getMeasuredHeight();
        } else {
            i3 = 0;
        }
        if (this.mContent != null) {
            if (this.mOverlayWithAppContent) {
                measureChildWithMargins(this.mContent, i, 0, i2, 0);
            } else {
                measureChildWithMargins(this.mContent, i, 0, i2, i3);
            }
        }
        setMeasuredDimension(android.view.View.MeasureSpec.getSize(i), android.view.View.MeasureSpec.getSize(i2));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        if (this.mCaption.getVisibility() != 8) {
            this.mCaption.layout(0, 0, this.mCaption.getMeasuredWidth(), this.mCaption.getMeasuredHeight());
            i5 = this.mCaption.getBottom() - this.mCaption.getTop();
            this.mMaximize.getHitRect(this.mMaximizeRect);
            this.mClose.getHitRect(this.mCloseRect);
        } else {
            this.mMaximizeRect.setEmpty();
            this.mCloseRect.setEmpty();
            i5 = 0;
        }
        if (this.mContent != null) {
            if (this.mOverlayWithAppContent) {
                this.mContent.layout(0, 0, this.mContent.getMeasuredWidth(), this.mContent.getMeasuredHeight());
            } else {
                this.mContent.layout(0, i5, this.mContent.getMeasuredWidth(), this.mContent.getMeasuredHeight() + i5);
            }
        }
        ((com.android.internal.policy.DecorView) this.mOwner.getDecorView()).notifyCaptionHeightChanged();
        this.mOwner.notifyRestrictedCaptionAreaCallback(this.mMaximize.getLeft(), this.mMaximize.getTop(), this.mClose.getRight(), this.mClose.getBottom());
    }

    private void updateCaptionVisibility() {
        this.mCaption.setVisibility(this.mShow ? 0 : 8);
        this.mCaption.setOnTouchListener(this);
    }

    private void toggleFreeformWindowingMode() {
        android.view.Window.WindowControllerCallback windowControllerCallback = this.mOwner.getWindowControllerCallback();
        if (windowControllerCallback != null) {
            windowControllerCallback.toggleFreeformWindowingMode();
        }
    }

    public boolean isCaptionShowing() {
        return this.mShow;
    }

    public int getCaptionHeight() {
        if (this.mCaption != null) {
            return this.mCaption.getHeight();
        }
        return 0;
    }

    public void removeContentView() {
        if (this.mContent != null) {
            removeView(this.mContent);
            this.mContent = null;
        }
    }

    public android.view.View getCaption() {
        return this.mCaption;
    }

    @Override // android.view.ViewGroup
    public android.view.ViewGroup.LayoutParams generateLayoutParams(android.util.AttributeSet attributeSet) {
        return new android.view.ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new android.view.ViewGroup.MarginLayoutParams(-1, -1);
    }

    @Override // android.view.ViewGroup
    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new android.view.ViewGroup.MarginLayoutParams(layoutParams);
    }

    @Override // android.view.ViewGroup
    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof android.view.ViewGroup.MarginLayoutParams;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(android.view.MotionEvent motionEvent) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(android.view.MotionEvent motionEvent) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(android.view.MotionEvent motionEvent) {
        if (this.mClickTarget == this.mMaximize) {
            toggleFreeformWindowingMode();
        } else if (this.mClickTarget == this.mClose) {
            this.mOwner.dispatchOnWindowDismissed(true, false);
        }
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(android.view.MotionEvent motionEvent) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(android.view.MotionEvent motionEvent, android.view.MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public void onRootViewScrollYChanged(int i) {
        if (this.mCaption != null) {
            this.mRootScrollY = i;
            this.mCaption.setTranslationY(i);
        }
    }
}
