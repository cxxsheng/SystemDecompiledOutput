package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class StackView extends android.widget.AdapterViewAnimator {
    private static final int DEFAULT_ANIMATION_DURATION = 400;
    private static final int FRAME_PADDING = 4;
    private static final int GESTURE_NONE = 0;
    private static final int GESTURE_SLIDE_DOWN = 2;
    private static final int GESTURE_SLIDE_UP = 1;
    private static final int INVALID_POINTER = -1;
    private static final int ITEMS_SLIDE_DOWN = 1;
    private static final int ITEMS_SLIDE_UP = 0;
    private static final int MINIMUM_ANIMATION_DURATION = 50;
    private static final int MIN_TIME_BETWEEN_INTERACTION_AND_AUTOADVANCE = 5000;
    private static final long MIN_TIME_BETWEEN_SCROLLS = 100;
    private static final int NUM_ACTIVE_VIEWS = 5;
    private static final float PERSPECTIVE_SCALE_FACTOR = 0.0f;
    private static final float PERSPECTIVE_SHIFT_FACTOR_X = 0.1f;
    private static final float PERSPECTIVE_SHIFT_FACTOR_Y = 0.1f;
    private static final float SLIDE_UP_RATIO = 0.7f;
    private static final int STACK_RELAYOUT_DURATION = 100;
    private static final float SWIPE_THRESHOLD_RATIO = 0.2f;
    private static android.widget.StackView.HolographicHelper sHolographicHelper;
    private final java.lang.String TAG;
    private int mActivePointerId;
    private int mClickColor;
    private android.widget.ImageView mClickFeedback;
    private boolean mClickFeedbackIsValid;
    private boolean mFirstLayoutHappened;
    private int mFramePadding;
    private android.widget.ImageView mHighlight;
    private float mInitialX;
    private float mInitialY;
    private long mLastInteractionTime;
    private long mLastScrollTime;
    private int mMaximumVelocity;
    private float mNewPerspectiveShiftX;
    private float mNewPerspectiveShiftY;
    private float mPerspectiveShiftX;
    private float mPerspectiveShiftY;
    private int mResOutColor;
    private int mSlideAmount;
    private int mStackMode;
    private android.widget.StackView.StackSlider mStackSlider;
    private int mSwipeGestureType;
    private int mSwipeThreshold;
    private final android.graphics.Rect mTouchRect;
    private int mTouchSlop;
    private boolean mTransitionIsSetup;
    private android.view.VelocityTracker mVelocityTracker;
    private int mYVelocity;
    private final android.graphics.Rect stackInvalidateRect;

    public StackView(android.content.Context context) {
        this(context, null);
    }

    public StackView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16843838);
    }

    public StackView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public StackView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.TAG = "StackView";
        this.mTouchRect = new android.graphics.Rect();
        this.mYVelocity = 0;
        this.mSwipeGestureType = 0;
        this.mTransitionIsSetup = false;
        this.mClickFeedbackIsValid = false;
        this.mFirstLayoutHappened = false;
        this.mLastInteractionTime = 0L;
        this.stackInvalidateRect = new android.graphics.Rect();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.StackView, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.StackView, attributeSet, obtainStyledAttributes, i, i2);
        this.mResOutColor = obtainStyledAttributes.getColor(1, 0);
        this.mClickColor = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        initStackView();
    }

    private void initStackView() {
        configureViewAnimator(5, 1);
        setStaticTransformationsEnabled(true);
        android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mActivePointerId = -1;
        this.mHighlight = new android.widget.ImageView(getContext());
        this.mHighlight.setLayoutParams(new android.widget.StackView.LayoutParams(this.mHighlight));
        addViewInLayout(this.mHighlight, -1, new android.widget.StackView.LayoutParams(this.mHighlight));
        this.mClickFeedback = new android.widget.ImageView(getContext());
        this.mClickFeedback.setLayoutParams(new android.widget.StackView.LayoutParams(this.mClickFeedback));
        addViewInLayout(this.mClickFeedback, -1, new android.widget.StackView.LayoutParams(this.mClickFeedback));
        this.mClickFeedback.setVisibility(4);
        this.mStackSlider = new android.widget.StackView.StackSlider();
        if (sHolographicHelper == null) {
            sHolographicHelper = new android.widget.StackView.HolographicHelper(this.mContext);
        }
        setClipChildren(false);
        setClipToPadding(false);
        this.mStackMode = 1;
        this.mWhichChild = -1;
        this.mFramePadding = (int) java.lang.Math.ceil(this.mContext.getResources().getDisplayMetrics().density * 4.0f);
    }

    @Override // android.widget.AdapterViewAnimator
    void transformViewForTransition(int i, int i2, final android.view.View view, boolean z) {
        if (!z) {
            ((android.widget.StackView.StackFrame) view).cancelSliderAnimator();
            view.setRotationX(0.0f);
            android.widget.StackView.LayoutParams layoutParams = (android.widget.StackView.LayoutParams) view.getLayoutParams();
            layoutParams.setVerticalOffset(0);
            layoutParams.setHorizontalOffset(0);
        }
        if (i == -1 && i2 == getNumActiveViews() - 1) {
            transformViewAtIndex(i2, view, false);
            view.setVisibility(0);
            view.setAlpha(1.0f);
        } else if (i == 0 && i2 == 1) {
            android.widget.StackView.StackFrame stackFrame = (android.widget.StackView.StackFrame) view;
            stackFrame.cancelSliderAnimator();
            view.setVisibility(0);
            int round = java.lang.Math.round(this.mStackSlider.getDurationForNeutralPosition(this.mYVelocity));
            android.widget.StackView.StackSlider stackSlider = new android.widget.StackView.StackSlider(this.mStackSlider);
            stackSlider.setView(view);
            if (z) {
                android.animation.ObjectAnimator ofPropertyValuesHolder = android.animation.ObjectAnimator.ofPropertyValuesHolder(stackSlider, android.animation.PropertyValuesHolder.ofFloat("XProgress", 0.0f), android.animation.PropertyValuesHolder.ofFloat("YProgress", 0.0f));
                ofPropertyValuesHolder.setDuration(round);
                ofPropertyValuesHolder.setInterpolator(new android.view.animation.LinearInterpolator());
                stackFrame.setSliderAnimator(ofPropertyValuesHolder);
                ofPropertyValuesHolder.start();
            } else {
                stackSlider.setYProgress(0.0f);
                stackSlider.setXProgress(0.0f);
            }
        } else if (i == 1 && i2 == 0) {
            android.widget.StackView.StackFrame stackFrame2 = (android.widget.StackView.StackFrame) view;
            stackFrame2.cancelSliderAnimator();
            int round2 = java.lang.Math.round(this.mStackSlider.getDurationForOffscreenPosition(this.mYVelocity));
            android.widget.StackView.StackSlider stackSlider2 = new android.widget.StackView.StackSlider(this.mStackSlider);
            stackSlider2.setView(view);
            if (z) {
                android.animation.ObjectAnimator ofPropertyValuesHolder2 = android.animation.ObjectAnimator.ofPropertyValuesHolder(stackSlider2, android.animation.PropertyValuesHolder.ofFloat("XProgress", 0.0f), android.animation.PropertyValuesHolder.ofFloat("YProgress", 1.0f));
                ofPropertyValuesHolder2.setDuration(round2);
                ofPropertyValuesHolder2.setInterpolator(new android.view.animation.LinearInterpolator());
                stackFrame2.setSliderAnimator(ofPropertyValuesHolder2);
                ofPropertyValuesHolder2.start();
            } else {
                stackSlider2.setYProgress(1.0f);
                stackSlider2.setXProgress(0.0f);
            }
        } else if (i2 == 0) {
            view.setAlpha(0.0f);
            view.setVisibility(4);
        } else if ((i != 0 && i != 1) || i2 <= 1) {
            if (i == -1) {
                view.setAlpha(1.0f);
                view.setVisibility(0);
            } else if (i2 == -1) {
                if (z) {
                    postDelayed(new java.lang.Runnable() { // from class: android.widget.StackView.1
                        @Override // java.lang.Runnable
                        public void run() {
                            view.setAlpha(0.0f);
                        }
                    }, MIN_TIME_BETWEEN_SCROLLS);
                } else {
                    view.setAlpha(0.0f);
                }
            }
        } else {
            view.setVisibility(0);
            view.setAlpha(1.0f);
            view.setRotationX(0.0f);
            android.widget.StackView.LayoutParams layoutParams2 = (android.widget.StackView.LayoutParams) view.getLayoutParams();
            layoutParams2.setVerticalOffset(0);
            layoutParams2.setHorizontalOffset(0);
        }
        if (i2 != -1) {
            transformViewAtIndex(i2, view, z);
        }
    }

    private void transformViewAtIndex(int i, android.view.View view, boolean z) {
        int i2;
        float f = this.mPerspectiveShiftY;
        float f2 = this.mPerspectiveShiftX;
        if (this.mStackMode == 1) {
            i2 = (this.mMaxNumActiveViews - i) - 1;
            if (i2 == this.mMaxNumActiveViews - 1) {
                i2--;
            }
        } else {
            i2 = i - 1;
            if (i2 < 0) {
                i2++;
            }
        }
        float f3 = (i2 * 1.0f) / (this.mMaxNumActiveViews - 2);
        float f4 = 1.0f - f3;
        float f5 = 1.0f - (0.0f * f4);
        float measuredHeight = (f3 * f) + ((f5 - 1.0f) * ((getMeasuredHeight() * 0.9f) / 2.0f));
        float measuredWidth = (f4 * f2) + ((1.0f - f5) * ((getMeasuredWidth() * 0.9f) / 2.0f));
        boolean z2 = view instanceof android.widget.StackView.StackFrame;
        if (z2) {
            ((android.widget.StackView.StackFrame) view).cancelTransformAnimator();
        }
        if (z) {
            android.animation.ObjectAnimator ofPropertyValuesHolder = android.animation.ObjectAnimator.ofPropertyValuesHolder(view, android.animation.PropertyValuesHolder.ofFloat("scaleX", f5), android.animation.PropertyValuesHolder.ofFloat("scaleY", f5), android.animation.PropertyValuesHolder.ofFloat("translationY", measuredHeight), android.animation.PropertyValuesHolder.ofFloat("translationX", measuredWidth));
            ofPropertyValuesHolder.setDuration(MIN_TIME_BETWEEN_SCROLLS);
            if (z2) {
                ((android.widget.StackView.StackFrame) view).setTransformAnimator(ofPropertyValuesHolder);
            }
            ofPropertyValuesHolder.start();
            return;
        }
        view.setTranslationX(measuredWidth);
        view.setTranslationY(measuredHeight);
        view.setScaleX(f5);
        view.setScaleY(f5);
    }

    private void setupStackSlider(android.view.View view, int i) {
        this.mStackSlider.setMode(i);
        if (view != null) {
            this.mHighlight.setImageBitmap(sHolographicHelper.createResOutline(view, this.mResOutColor));
            this.mHighlight.setRotation(view.getRotation());
            this.mHighlight.setTranslationY(view.getTranslationY());
            this.mHighlight.setTranslationX(view.getTranslationX());
            this.mHighlight.bringToFront();
            view.bringToFront();
            this.mStackSlider.setView(view);
            view.setVisibility(0);
        }
    }

    @Override // android.widget.AdapterViewAnimator
    @android.view.RemotableViewMethod
    public void showNext() {
        android.view.View viewAtRelativeIndex;
        if (this.mSwipeGestureType != 0) {
            return;
        }
        if (!this.mTransitionIsSetup && (viewAtRelativeIndex = getViewAtRelativeIndex(1)) != null) {
            setupStackSlider(viewAtRelativeIndex, 0);
            this.mStackSlider.setYProgress(0.0f);
            this.mStackSlider.setXProgress(0.0f);
        }
        super.showNext();
    }

    @Override // android.widget.AdapterViewAnimator
    @android.view.RemotableViewMethod
    public void showPrevious() {
        android.view.View viewAtRelativeIndex;
        if (this.mSwipeGestureType != 0) {
            return;
        }
        if (!this.mTransitionIsSetup && (viewAtRelativeIndex = getViewAtRelativeIndex(0)) != null) {
            setupStackSlider(viewAtRelativeIndex, 0);
            this.mStackSlider.setYProgress(1.0f);
            this.mStackSlider.setXProgress(0.0f);
        }
        super.showPrevious();
    }

    @Override // android.widget.AdapterViewAnimator
    void showOnly(int i, boolean z) {
        android.view.View view;
        super.showOnly(i, z);
        for (int i2 = this.mCurrentWindowEnd; i2 >= this.mCurrentWindowStart; i2--) {
            int modulo = modulo(i2, getWindowSize());
            if (this.mViewsMap.get(java.lang.Integer.valueOf(modulo)) != null && (view = this.mViewsMap.get(java.lang.Integer.valueOf(modulo)).view) != null) {
                view.bringToFront();
            }
        }
        if (this.mHighlight != null) {
            this.mHighlight.bringToFront();
        }
        this.mTransitionIsSetup = false;
        this.mClickFeedbackIsValid = false;
    }

    void updateClickFeedback() {
        if (!this.mClickFeedbackIsValid) {
            android.view.View viewAtRelativeIndex = getViewAtRelativeIndex(1);
            if (viewAtRelativeIndex != null) {
                this.mClickFeedback.setImageBitmap(sHolographicHelper.createClickOutline(viewAtRelativeIndex, this.mClickColor));
                this.mClickFeedback.setTranslationX(viewAtRelativeIndex.getTranslationX());
                this.mClickFeedback.setTranslationY(viewAtRelativeIndex.getTranslationY());
            }
            this.mClickFeedbackIsValid = true;
        }
    }

    @Override // android.widget.AdapterViewAnimator
    void showTapFeedback(android.view.View view) {
        updateClickFeedback();
        this.mClickFeedback.setVisibility(0);
        this.mClickFeedback.bringToFront();
        invalidate();
    }

    @Override // android.widget.AdapterViewAnimator
    void hideTapFeedback(android.view.View view) {
        this.mClickFeedback.setVisibility(4);
        invalidate();
    }

    private void updateChildTransforms() {
        for (int i = 0; i < getNumActiveViews(); i++) {
            android.view.View viewAtRelativeIndex = getViewAtRelativeIndex(i);
            if (viewAtRelativeIndex != null) {
                transformViewAtIndex(i, viewAtRelativeIndex, false);
            }
        }
    }

    private static class StackFrame extends android.widget.FrameLayout {
        java.lang.ref.WeakReference<android.animation.ObjectAnimator> sliderAnimator;
        java.lang.ref.WeakReference<android.animation.ObjectAnimator> transformAnimator;

        public StackFrame(android.content.Context context) {
            super(context);
        }

        void setTransformAnimator(android.animation.ObjectAnimator objectAnimator) {
            this.transformAnimator = new java.lang.ref.WeakReference<>(objectAnimator);
        }

        void setSliderAnimator(android.animation.ObjectAnimator objectAnimator) {
            this.sliderAnimator = new java.lang.ref.WeakReference<>(objectAnimator);
        }

        boolean cancelTransformAnimator() {
            android.animation.ObjectAnimator objectAnimator;
            if (this.transformAnimator != null && (objectAnimator = this.transformAnimator.get()) != null) {
                objectAnimator.cancel();
                return true;
            }
            return false;
        }

        boolean cancelSliderAnimator() {
            android.animation.ObjectAnimator objectAnimator;
            if (this.sliderAnimator != null && (objectAnimator = this.sliderAnimator.get()) != null) {
                objectAnimator.cancel();
                return true;
            }
            return false;
        }
    }

    @Override // android.widget.AdapterViewAnimator
    android.widget.FrameLayout getFrameForChild() {
        android.widget.StackView.StackFrame stackFrame = new android.widget.StackView.StackFrame(this.mContext);
        stackFrame.setPadding(this.mFramePadding, this.mFramePadding, this.mFramePadding, this.mFramePadding);
        return stackFrame;
    }

    @Override // android.widget.AdapterViewAnimator
    void applyTransformForChildAtIndex(android.view.View view, int i) {
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(android.graphics.Canvas canvas) {
        canvas.getClipBounds(this.stackInvalidateRect);
        int childCount = getChildCount();
        boolean z = false;
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = getChildAt(i);
            android.widget.StackView.LayoutParams layoutParams = (android.widget.StackView.LayoutParams) childAt.getLayoutParams();
            if ((layoutParams.horizontalOffset == 0 && layoutParams.verticalOffset == 0) || childAt.getAlpha() == 0.0f || childAt.getVisibility() != 0) {
                layoutParams.resetInvalidateRect();
            }
            android.graphics.Rect invalidateRect = layoutParams.getInvalidateRect();
            if (!invalidateRect.isEmpty()) {
                this.stackInvalidateRect.union(invalidateRect);
                z = true;
            }
        }
        if (z) {
            canvas.save();
            canvas.clipRectUnion(this.stackInvalidateRect);
            super.dispatchDraw(canvas);
            canvas.restore();
            return;
        }
        super.dispatchDraw(canvas);
    }

    private void onLayout() {
        if (!this.mFirstLayoutHappened) {
            this.mFirstLayoutHappened = true;
            updateChildTransforms();
        }
        int round = java.lang.Math.round(getMeasuredHeight() * SLIDE_UP_RATIO);
        if (this.mSlideAmount != round) {
            this.mSlideAmount = round;
            this.mSwipeThreshold = java.lang.Math.round(round * 0.2f);
        }
        if (java.lang.Float.compare(this.mPerspectiveShiftY, this.mNewPerspectiveShiftY) != 0 || java.lang.Float.compare(this.mPerspectiveShiftX, this.mNewPerspectiveShiftX) != 0) {
            this.mPerspectiveShiftY = this.mNewPerspectiveShiftY;
            this.mPerspectiveShiftX = this.mNewPerspectiveShiftX;
            updateChildTransforms();
        }
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) != 0) {
            switch (motionEvent.getAction()) {
                case 8:
                    float axisValue = motionEvent.getAxisValue(9);
                    if (axisValue < 0.0f) {
                        pacedScroll(false);
                        return true;
                    }
                    if (axisValue > 0.0f) {
                        pacedScroll(true);
                        return true;
                    }
                    break;
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    private void pacedScroll(boolean z) {
        if (java.lang.System.currentTimeMillis() - this.mLastScrollTime > MIN_TIME_BETWEEN_SCROLLS) {
            if (z) {
                showPrevious();
            } else {
                showNext();
            }
            this.mLastScrollTime = java.lang.System.currentTimeMillis();
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        switch (motionEvent.getAction() & 255) {
            case 0:
                if (this.mActivePointerId == -1) {
                    this.mInitialX = motionEvent.getX();
                    this.mInitialY = motionEvent.getY();
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    break;
                }
                break;
            case 1:
            case 3:
                this.mActivePointerId = -1;
                this.mSwipeGestureType = 0;
                break;
            case 2:
                int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex == -1) {
                    android.util.Log.d("StackView", "Error: No data for our primary pointer.");
                    return false;
                }
                beginGestureIfNeeded(motionEvent.getY(findPointerIndex) - this.mInitialY);
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                break;
        }
        return this.mSwipeGestureType != 0;
    }

    private void beginGestureIfNeeded(float f) {
        int i;
        if (((int) java.lang.Math.abs(f)) > this.mTouchSlop && this.mSwipeGestureType == 0) {
            int i2 = 2;
            int i3 = f < 0.0f ? 1 : 2;
            cancelLongPress();
            requestDisallowInterceptTouchEvent(true);
            if (this.mAdapter == null) {
                return;
            }
            int count = getCount();
            if (this.mStackMode == 0) {
                i = i3 == 2 ? 0 : 1;
            } else {
                i = i3 == 2 ? 1 : 0;
            }
            boolean z = this.mLoopViews && count == 1 && ((this.mStackMode == 0 && i3 == 1) || (this.mStackMode == 1 && i3 == 2));
            boolean z2 = this.mLoopViews && count == 1 && ((this.mStackMode == 1 && i3 == 1) || (this.mStackMode == 0 && i3 == 2));
            if (this.mLoopViews && !z2 && !z) {
                i2 = 0;
            } else if (this.mCurrentWindowStartUnbounded + i == -1 || z2) {
                i++;
                i2 = 1;
            } else if (this.mCurrentWindowStartUnbounded + i != count - 1 && !z) {
                i2 = 0;
            }
            this.mTransitionIsSetup = i2 == 0;
            android.view.View viewAtRelativeIndex = getViewAtRelativeIndex(i);
            if (viewAtRelativeIndex == null) {
                return;
            }
            setupStackSlider(viewAtRelativeIndex, i2);
            this.mSwipeGestureType = i3;
            cancelHandleClick();
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.widget.AdapterViewAnimator, android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        int action = motionEvent.getAction();
        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        if (findPointerIndex == -1) {
            android.util.Log.d("StackView", "Error: No data for our primary pointer.");
            return false;
        }
        float y = motionEvent.getY(findPointerIndex);
        float x = motionEvent.getX(findPointerIndex);
        float f = y - this.mInitialY;
        float f2 = x - this.mInitialX;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = android.view.VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        switch (action & 255) {
            case 1:
                handlePointerUp(motionEvent);
                return true;
            case 2:
                beginGestureIfNeeded(f);
                float f3 = f2 / (this.mSlideAmount * 1.0f);
                if (this.mSwipeGestureType == 2) {
                    float f4 = ((f - (this.mTouchSlop * 1.0f)) / this.mSlideAmount) * 1.0f;
                    if (this.mStackMode == 1) {
                        f4 = 1.0f - f4;
                    }
                    this.mStackSlider.setYProgress(1.0f - f4);
                    this.mStackSlider.setXProgress(f3);
                    return true;
                }
                if (this.mSwipeGestureType == 1) {
                    float f5 = ((-(f + (this.mTouchSlop * 1.0f))) / this.mSlideAmount) * 1.0f;
                    if (this.mStackMode == 1) {
                        f5 = 1.0f - f5;
                    }
                    this.mStackSlider.setYProgress(f5);
                    this.mStackSlider.setXProgress(f3);
                    return true;
                }
                return true;
            case 3:
                this.mActivePointerId = -1;
                this.mSwipeGestureType = 0;
                return true;
            case 4:
            case 5:
            default:
                return true;
            case 6:
                onSecondaryPointerUp(motionEvent);
                return true;
        }
    }

    private void onSecondaryPointerUp(android.view.MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            android.view.View viewAtRelativeIndex = getViewAtRelativeIndex(this.mSwipeGestureType == 2 ? 0 : 1);
            if (viewAtRelativeIndex == null) {
                return;
            }
            for (int i = 0; i < motionEvent.getPointerCount(); i++) {
                if (i != actionIndex) {
                    float x = motionEvent.getX(i);
                    float y = motionEvent.getY(i);
                    this.mTouchRect.set(viewAtRelativeIndex.getLeft(), viewAtRelativeIndex.getTop(), viewAtRelativeIndex.getRight(), viewAtRelativeIndex.getBottom());
                    if (this.mTouchRect.contains(java.lang.Math.round(x), java.lang.Math.round(y))) {
                        float x2 = motionEvent.getX(actionIndex);
                        this.mInitialY += y - motionEvent.getY(actionIndex);
                        this.mInitialX += x - x2;
                        this.mActivePointerId = motionEvent.getPointerId(i);
                        if (this.mVelocityTracker != null) {
                            this.mVelocityTracker.clear();
                            return;
                        }
                        return;
                    }
                }
            }
            handlePointerUp(motionEvent);
        }
    }

    private void handlePointerUp(android.view.MotionEvent motionEvent) {
        float f;
        int round;
        int round2;
        int y = (int) (motionEvent.getY(motionEvent.findPointerIndex(this.mActivePointerId)) - this.mInitialY);
        this.mLastInteractionTime = java.lang.System.currentTimeMillis();
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
            this.mYVelocity = (int) this.mVelocityTracker.getYVelocity(this.mActivePointerId);
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        if (y > this.mSwipeThreshold && this.mSwipeGestureType == 2 && this.mStackSlider.mMode == 0) {
            this.mSwipeGestureType = 0;
            if (this.mStackMode == 0) {
                showPrevious();
            } else {
                showNext();
            }
            this.mHighlight.bringToFront();
        } else if (y < (-this.mSwipeThreshold) && this.mSwipeGestureType == 1 && this.mStackSlider.mMode == 0) {
            this.mSwipeGestureType = 0;
            if (this.mStackMode == 0) {
                showNext();
            } else {
                showPrevious();
            }
            this.mHighlight.bringToFront();
        } else {
            if (this.mSwipeGestureType == 1) {
                f = this.mStackMode != 1 ? 0.0f : 1.0f;
                if (this.mStackMode == 0 || this.mStackSlider.mMode != 0) {
                    round2 = java.lang.Math.round(this.mStackSlider.getDurationForNeutralPosition());
                } else {
                    round2 = java.lang.Math.round(this.mStackSlider.getDurationForOffscreenPosition());
                }
                android.animation.ObjectAnimator ofPropertyValuesHolder = android.animation.ObjectAnimator.ofPropertyValuesHolder(new android.widget.StackView.StackSlider(this.mStackSlider), android.animation.PropertyValuesHolder.ofFloat("XProgress", 0.0f), android.animation.PropertyValuesHolder.ofFloat("YProgress", f));
                ofPropertyValuesHolder.setDuration(round2);
                ofPropertyValuesHolder.setInterpolator(new android.view.animation.LinearInterpolator());
                ofPropertyValuesHolder.start();
            } else if (this.mSwipeGestureType == 2) {
                f = this.mStackMode == 1 ? 0.0f : 1.0f;
                if (this.mStackMode == 1 || this.mStackSlider.mMode != 0) {
                    round = java.lang.Math.round(this.mStackSlider.getDurationForNeutralPosition());
                } else {
                    round = java.lang.Math.round(this.mStackSlider.getDurationForOffscreenPosition());
                }
                android.animation.ObjectAnimator ofPropertyValuesHolder2 = android.animation.ObjectAnimator.ofPropertyValuesHolder(new android.widget.StackView.StackSlider(this.mStackSlider), android.animation.PropertyValuesHolder.ofFloat("XProgress", 0.0f), android.animation.PropertyValuesHolder.ofFloat("YProgress", f));
                ofPropertyValuesHolder2.setDuration(round);
                ofPropertyValuesHolder2.start();
            }
        }
        this.mActivePointerId = -1;
        this.mSwipeGestureType = 0;
    }

    private class StackSlider {
        static final int BEGINNING_OF_STACK_MODE = 1;
        static final int END_OF_STACK_MODE = 2;
        static final int NORMAL_MODE = 0;
        int mMode;
        android.view.View mView;
        float mXProgress;
        float mYProgress;

        public StackSlider() {
            this.mMode = 0;
        }

        public StackSlider(android.widget.StackView.StackSlider stackSlider) {
            this.mMode = 0;
            this.mView = stackSlider.mView;
            this.mYProgress = stackSlider.mYProgress;
            this.mXProgress = stackSlider.mXProgress;
            this.mMode = stackSlider.mMode;
        }

        private float cubic(float f) {
            return ((float) (java.lang.Math.pow((f * 2.0f) - 1.0f, 3.0d) + 1.0d)) / 2.0f;
        }

        private float highlightAlphaInterpolator(float f) {
            if (f < 0.4f) {
                return cubic(f / 0.4f) * 0.85f;
            }
            return cubic(1.0f - ((f - 0.4f) / 0.6f)) * 0.85f;
        }

        private float viewAlphaInterpolator(float f) {
            if (f > 0.3f) {
                return (f - 0.3f) / android.widget.StackView.SLIDE_UP_RATIO;
            }
            return 0.0f;
        }

        private float rotationInterpolator(float f) {
            if (f < 0.2f) {
                return 0.0f;
            }
            return (f - 0.2f) / 0.8f;
        }

        void setView(android.view.View view) {
            this.mView = view;
        }

        public void setYProgress(float f) {
            float max = java.lang.Math.max(0.0f, java.lang.Math.min(1.0f, f));
            this.mYProgress = max;
            if (this.mView == null) {
            }
            android.widget.StackView.LayoutParams layoutParams = (android.widget.StackView.LayoutParams) this.mView.getLayoutParams();
            android.widget.StackView.LayoutParams layoutParams2 = (android.widget.StackView.LayoutParams) android.widget.StackView.this.mHighlight.getLayoutParams();
            int i = android.widget.StackView.this.mStackMode == 0 ? 1 : -1;
            if (java.lang.Float.compare(0.0f, this.mYProgress) != 0 && java.lang.Float.compare(1.0f, this.mYProgress) != 0) {
                if (this.mView.getLayerType() == 0) {
                    this.mView.setLayerType(2, null);
                }
            } else if (this.mView.getLayerType() != 0) {
                this.mView.setLayerType(0, null);
            }
            switch (this.mMode) {
                case 0:
                    float f2 = i;
                    float f3 = (-max) * f2;
                    layoutParams.setVerticalOffset(java.lang.Math.round(android.widget.StackView.this.mSlideAmount * f3));
                    layoutParams2.setVerticalOffset(java.lang.Math.round(f3 * android.widget.StackView.this.mSlideAmount));
                    android.widget.StackView.this.mHighlight.setAlpha(highlightAlphaInterpolator(max));
                    float viewAlphaInterpolator = viewAlphaInterpolator(1.0f - max);
                    if (this.mView.getAlpha() == 0.0f && viewAlphaInterpolator != 0.0f && this.mView.getVisibility() != 0) {
                        this.mView.setVisibility(0);
                    } else if (viewAlphaInterpolator == 0.0f && this.mView.getAlpha() != 0.0f && this.mView.getVisibility() == 0) {
                        this.mView.setVisibility(4);
                    }
                    this.mView.setAlpha(viewAlphaInterpolator);
                    float f4 = f2 * 90.0f;
                    this.mView.setRotationX(rotationInterpolator(max) * f4);
                    android.widget.StackView.this.mHighlight.setRotationX(f4 * rotationInterpolator(max));
                    break;
                case 1:
                    float f5 = (1.0f - max) * 0.2f;
                    float f6 = i * f5;
                    layoutParams.setVerticalOffset(java.lang.Math.round(android.widget.StackView.this.mSlideAmount * f6));
                    layoutParams2.setVerticalOffset(java.lang.Math.round(f6 * android.widget.StackView.this.mSlideAmount));
                    android.widget.StackView.this.mHighlight.setAlpha(highlightAlphaInterpolator(f5));
                    break;
                case 2:
                    float f7 = max * 0.2f;
                    float f8 = (-i) * f7;
                    layoutParams.setVerticalOffset(java.lang.Math.round(android.widget.StackView.this.mSlideAmount * f8));
                    layoutParams2.setVerticalOffset(java.lang.Math.round(f8 * android.widget.StackView.this.mSlideAmount));
                    android.widget.StackView.this.mHighlight.setAlpha(highlightAlphaInterpolator(f7));
                    break;
            }
        }

        public void setXProgress(float f) {
            float max = java.lang.Math.max(-2.0f, java.lang.Math.min(2.0f, f));
            this.mXProgress = max;
            if (this.mView == null) {
                return;
            }
            android.widget.StackView.LayoutParams layoutParams = (android.widget.StackView.LayoutParams) this.mView.getLayoutParams();
            android.widget.StackView.LayoutParams layoutParams2 = (android.widget.StackView.LayoutParams) android.widget.StackView.this.mHighlight.getLayoutParams();
            float f2 = max * 0.2f;
            layoutParams.setHorizontalOffset(java.lang.Math.round(android.widget.StackView.this.mSlideAmount * f2));
            layoutParams2.setHorizontalOffset(java.lang.Math.round(f2 * android.widget.StackView.this.mSlideAmount));
        }

        void setMode(int i) {
            this.mMode = i;
        }

        float getDurationForNeutralPosition() {
            return getDuration(false, 0.0f);
        }

        float getDurationForOffscreenPosition() {
            return getDuration(true, 0.0f);
        }

        float getDurationForNeutralPosition(float f) {
            return getDuration(false, f);
        }

        float getDurationForOffscreenPosition(float f) {
            return getDuration(true, f);
        }

        private float getDuration(boolean z, float f) {
            if (this.mView == null) {
                return 0.0f;
            }
            android.widget.StackView.LayoutParams layoutParams = (android.widget.StackView.LayoutParams) this.mView.getLayoutParams();
            float hypot = (float) java.lang.Math.hypot(layoutParams.horizontalOffset, layoutParams.verticalOffset);
            float hypot2 = (float) java.lang.Math.hypot(android.widget.StackView.this.mSlideAmount, android.widget.StackView.this.mSlideAmount * 0.4f);
            if (hypot > hypot2) {
                hypot = hypot2;
            }
            if (f == 0.0f) {
                return (z ? 1.0f - (hypot / hypot2) : hypot / hypot2) * 400.0f;
            }
            float abs = z ? hypot / java.lang.Math.abs(f) : (hypot2 - hypot) / java.lang.Math.abs(f);
            if (abs < 50.0f || abs > 400.0f) {
                return getDuration(z, 0.0f);
            }
            return abs;
        }

        public float getYProgress() {
            return this.mYProgress;
        }

        public float getXProgress() {
            return this.mXProgress;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.widget.AdapterViewAnimator
    public android.widget.StackView.LayoutParams createOrReuseLayoutParams(android.view.View view) {
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof android.widget.StackView.LayoutParams) {
            android.widget.StackView.LayoutParams layoutParams2 = (android.widget.StackView.LayoutParams) layoutParams;
            layoutParams2.setHorizontalOffset(0);
            layoutParams2.setVerticalOffset(0);
            layoutParams2.width = 0;
            layoutParams2.width = 0;
            return layoutParams2;
        }
        return new android.widget.StackView.LayoutParams(view);
    }

    @Override // android.widget.AdapterViewAnimator, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        checkForAndHandleDataChanged();
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            android.view.View childAt = getChildAt(i5);
            int measuredWidth = this.mPaddingLeft + childAt.getMeasuredWidth();
            int measuredHeight = this.mPaddingTop + childAt.getMeasuredHeight();
            android.widget.StackView.LayoutParams layoutParams = (android.widget.StackView.LayoutParams) childAt.getLayoutParams();
            childAt.layout(this.mPaddingLeft + layoutParams.horizontalOffset, this.mPaddingTop + layoutParams.verticalOffset, measuredWidth + layoutParams.horizontalOffset, measuredHeight + layoutParams.verticalOffset);
        }
        onLayout();
    }

    @Override // android.widget.AdapterViewAnimator, android.widget.Advanceable
    public void advance() {
        long currentTimeMillis = java.lang.System.currentTimeMillis() - this.mLastInteractionTime;
        if (this.mAdapter == null) {
            return;
        }
        if ((getCount() != 1 || !this.mLoopViews) && this.mSwipeGestureType == 0 && currentTimeMillis > 5000) {
            showNext();
        }
    }

    private void measureChildren() {
        int childCount = getChildCount();
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        float f = measuredWidth;
        int round = (java.lang.Math.round(f * 0.9f) - this.mPaddingLeft) - this.mPaddingRight;
        float f2 = measuredHeight;
        int round2 = (java.lang.Math.round(0.9f * f2) - this.mPaddingTop) - this.mPaddingBottom;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < childCount; i3++) {
            android.view.View childAt = getChildAt(i3);
            childAt.measure(android.view.View.MeasureSpec.makeMeasureSpec(round, Integer.MIN_VALUE), android.view.View.MeasureSpec.makeMeasureSpec(round2, Integer.MIN_VALUE));
            if (childAt != this.mHighlight && childAt != this.mClickFeedback) {
                int measuredWidth2 = childAt.getMeasuredWidth();
                int measuredHeight2 = childAt.getMeasuredHeight();
                if (measuredWidth2 > i) {
                    i = measuredWidth2;
                }
                if (measuredHeight2 > i2) {
                    i2 = measuredHeight2;
                }
            }
        }
        this.mNewPerspectiveShiftX = f * 0.1f;
        this.mNewPerspectiveShiftY = f2 * 0.1f;
        if (i > 0 && childCount > 0 && i < round) {
            this.mNewPerspectiveShiftX = measuredWidth - i;
        }
        if (i2 > 0 && childCount > 0 && i2 < round2) {
            this.mNewPerspectiveShiftY = measuredHeight - i2;
        }
    }

    @Override // android.widget.AdapterViewAnimator, android.view.View
    protected void onMeasure(int i, int i2) {
        int size = android.view.View.MeasureSpec.getSize(i);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        int mode = android.view.View.MeasureSpec.getMode(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        boolean z = (this.mReferenceChildWidth == -1 || this.mReferenceChildHeight == -1) ? false : true;
        if (mode2 == 0) {
            if (z) {
                size2 = java.lang.Math.round(this.mReferenceChildHeight * 2.1111112f) + this.mPaddingTop + this.mPaddingBottom;
            } else {
                size2 = 0;
            }
        } else if (mode2 == Integer.MIN_VALUE) {
            if (z) {
                int round = java.lang.Math.round(this.mReferenceChildHeight * 2.1111112f) + this.mPaddingTop + this.mPaddingBottom;
                if (round <= size2) {
                    size2 = round;
                } else {
                    size2 |= 16777216;
                }
            } else {
                size2 = 0;
            }
        }
        if (mode == 0) {
            if (z) {
                size = java.lang.Math.round(this.mReferenceChildWidth * 2.1111112f) + this.mPaddingLeft + this.mPaddingRight;
            } else {
                size = 0;
            }
        } else if (mode2 == Integer.MIN_VALUE) {
            if (z) {
                int i3 = this.mReferenceChildWidth + this.mPaddingLeft + this.mPaddingRight;
                if (i3 <= size) {
                    size = i3;
                } else {
                    size |= 16777216;
                }
            } else {
                size = 0;
            }
        }
        setMeasuredDimension(size, size2);
        measureChildren();
    }

    @Override // android.widget.AdapterViewAnimator, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.StackView.class.getName();
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setScrollable(getChildCount() > 1);
        if (isEnabled()) {
            if (getDisplayedChild() < getChildCount() - 1) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                if (this.mStackMode == 0) {
                    accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_DOWN);
                } else {
                    accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_UP);
                }
            }
            if (getDisplayedChild() > 0) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                if (this.mStackMode == 0) {
                    accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_UP);
                } else {
                    accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_DOWN);
                }
            }
        }
    }

    private boolean goForward() {
        if (getDisplayedChild() < getChildCount() - 1) {
            showNext();
            return true;
        }
        return false;
    }

    private boolean goBackward() {
        if (getDisplayedChild() > 0) {
            showPrevious();
            return true;
        }
        return false;
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, android.os.Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (!isEnabled()) {
            return false;
        }
        switch (i) {
            case 4096:
                break;
            case 8192:
                break;
            case 16908358:
                if (this.mStackMode != 0) {
                    break;
                } else {
                    break;
                }
            case 16908359:
                if (this.mStackMode != 0) {
                    break;
                } else {
                    break;
                }
        }
        return false;
    }

    class LayoutParams extends android.view.ViewGroup.LayoutParams {
        private final android.graphics.Rect globalInvalidateRect;
        int horizontalOffset;
        private final android.graphics.Rect invalidateRect;
        private final android.graphics.RectF invalidateRectf;
        android.view.View mView;
        private final android.graphics.Rect parentRect;
        int verticalOffset;

        LayoutParams(android.view.View view) {
            super(0, 0);
            this.parentRect = new android.graphics.Rect();
            this.invalidateRect = new android.graphics.Rect();
            this.invalidateRectf = new android.graphics.RectF();
            this.globalInvalidateRect = new android.graphics.Rect();
            this.width = 0;
            this.height = 0;
            this.horizontalOffset = 0;
            this.verticalOffset = 0;
            this.mView = view;
        }

        LayoutParams(android.content.Context context, android.util.AttributeSet attributeSet) {
            super(context, attributeSet);
            this.parentRect = new android.graphics.Rect();
            this.invalidateRect = new android.graphics.Rect();
            this.invalidateRectf = new android.graphics.RectF();
            this.globalInvalidateRect = new android.graphics.Rect();
            this.horizontalOffset = 0;
            this.verticalOffset = 0;
            this.width = 0;
            this.height = 0;
        }

        void invalidateGlobalRegion(android.view.View view, android.graphics.Rect rect) {
            this.globalInvalidateRect.set(rect);
            this.globalInvalidateRect.union(0, 0, android.widget.StackView.this.getWidth(), android.widget.StackView.this.getHeight());
            if (view.getParent() == null || !(view.getParent() instanceof android.view.View)) {
                return;
            }
            this.parentRect.set(0, 0, 0, 0);
            boolean z = true;
            while (view.getParent() != null && (view.getParent() instanceof android.view.View) && !this.parentRect.contains(this.globalInvalidateRect)) {
                if (!z) {
                    this.globalInvalidateRect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
                }
                view = (android.view.View) view.getParent();
                this.parentRect.set(view.getScrollX(), view.getScrollY(), view.getWidth() + view.getScrollX(), view.getHeight() + view.getScrollY());
                view.invalidate(this.globalInvalidateRect.left, this.globalInvalidateRect.top, this.globalInvalidateRect.right, this.globalInvalidateRect.bottom);
                z = false;
            }
            view.invalidate(this.globalInvalidateRect.left, this.globalInvalidateRect.top, this.globalInvalidateRect.right, this.globalInvalidateRect.bottom);
        }

        android.graphics.Rect getInvalidateRect() {
            return this.invalidateRect;
        }

        void resetInvalidateRect() {
            this.invalidateRect.set(0, 0, 0, 0);
        }

        public void setVerticalOffset(int i) {
            setOffsets(this.horizontalOffset, i);
        }

        public void setHorizontalOffset(int i) {
            setOffsets(i, this.verticalOffset);
        }

        public void setOffsets(int i, int i2) {
            int i3 = i - this.horizontalOffset;
            this.horizontalOffset = i;
            int i4 = i2 - this.verticalOffset;
            this.verticalOffset = i2;
            if (this.mView != null) {
                this.mView.requestLayout();
                this.invalidateRectf.set(java.lang.Math.min(this.mView.getLeft() + i3, this.mView.getLeft()), java.lang.Math.min(this.mView.getTop() + i4, this.mView.getTop()), java.lang.Math.max(this.mView.getRight() + i3, this.mView.getRight()), java.lang.Math.max(this.mView.getBottom() + i4, this.mView.getBottom()));
                float f = -this.invalidateRectf.left;
                float f2 = -this.invalidateRectf.top;
                this.invalidateRectf.offset(f, f2);
                this.mView.getMatrix().mapRect(this.invalidateRectf);
                this.invalidateRectf.offset(-f, -f2);
                this.invalidateRect.set((int) java.lang.Math.floor(this.invalidateRectf.left), (int) java.lang.Math.floor(this.invalidateRectf.top), (int) java.lang.Math.ceil(this.invalidateRectf.right), (int) java.lang.Math.ceil(this.invalidateRectf.bottom));
                invalidateGlobalRegion(this.mView, this.invalidateRect);
            }
        }
    }

    private static class HolographicHelper {
        private static final int CLICK_FEEDBACK = 1;
        private static final int RES_OUT = 0;
        private float mDensity;
        private android.graphics.BlurMaskFilter mLargeBlurMaskFilter;
        private android.graphics.BlurMaskFilter mSmallBlurMaskFilter;
        private final android.graphics.Paint mHolographicPaint = new android.graphics.Paint();
        private final android.graphics.Paint mErasePaint = new android.graphics.Paint();
        private final android.graphics.Paint mBlurPaint = new android.graphics.Paint();
        private final android.graphics.Canvas mCanvas = new android.graphics.Canvas();
        private final android.graphics.Canvas mMaskCanvas = new android.graphics.Canvas();
        private final int[] mTmpXY = new int[2];
        private final android.graphics.Matrix mIdentityMatrix = new android.graphics.Matrix();

        HolographicHelper(android.content.Context context) {
            this.mDensity = context.getResources().getDisplayMetrics().density;
            this.mHolographicPaint.setFilterBitmap(true);
            this.mHolographicPaint.setMaskFilter(android.graphics.TableMaskFilter.CreateClipTable(0, 30));
            this.mErasePaint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_OUT));
            this.mErasePaint.setFilterBitmap(true);
            this.mSmallBlurMaskFilter = new android.graphics.BlurMaskFilter(this.mDensity * 2.0f, android.graphics.BlurMaskFilter.Blur.NORMAL);
            this.mLargeBlurMaskFilter = new android.graphics.BlurMaskFilter(this.mDensity * 4.0f, android.graphics.BlurMaskFilter.Blur.NORMAL);
        }

        android.graphics.Bitmap createClickOutline(android.view.View view, int i) {
            return createOutline(view, 1, i);
        }

        android.graphics.Bitmap createResOutline(android.view.View view, int i) {
            return createOutline(view, 0, i);
        }

        android.graphics.Bitmap createOutline(android.view.View view, int i, int i2) {
            this.mHolographicPaint.setColor(i2);
            if (i == 0) {
                this.mBlurPaint.setMaskFilter(this.mSmallBlurMaskFilter);
            } else if (i == 1) {
                this.mBlurPaint.setMaskFilter(this.mLargeBlurMaskFilter);
            }
            if (view.getMeasuredWidth() == 0 || view.getMeasuredHeight() == 0) {
                return null;
            }
            android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(view.getResources().getDisplayMetrics(), view.getMeasuredWidth(), view.getMeasuredHeight(), android.graphics.Bitmap.Config.ARGB_8888);
            this.mCanvas.setBitmap(createBitmap);
            float rotationX = view.getRotationX();
            float rotation = view.getRotation();
            float translationY = view.getTranslationY();
            float translationX = view.getTranslationX();
            view.setRotationX(0.0f);
            view.setRotation(0.0f);
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            view.draw(this.mCanvas);
            view.setRotationX(rotationX);
            view.setRotation(rotation);
            view.setTranslationY(translationY);
            view.setTranslationX(translationX);
            drawOutline(this.mCanvas, createBitmap);
            this.mCanvas.setBitmap(null);
            return createBitmap;
        }

        void drawOutline(android.graphics.Canvas canvas, android.graphics.Bitmap bitmap) {
            android.graphics.Bitmap extractAlpha = bitmap.extractAlpha(this.mBlurPaint, this.mTmpXY);
            this.mMaskCanvas.setBitmap(extractAlpha);
            this.mMaskCanvas.drawBitmap(bitmap, -r0[0], -r0[1], this.mErasePaint);
            canvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
            canvas.setMatrix(this.mIdentityMatrix);
            canvas.drawBitmap(extractAlpha, r0[0], r0[1], this.mHolographicPaint);
            this.mMaskCanvas.setBitmap(null);
            extractAlpha.recycle();
        }
    }
}
