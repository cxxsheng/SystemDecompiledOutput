package com.android.internal.widget;

/* loaded from: classes5.dex */
public class SlidingTab extends android.view.ViewGroup {
    private static final int ANIM_DURATION = 250;
    private static final int ANIM_TARGET_TIME = 500;
    private static final boolean DBG = false;
    private static final int HORIZONTAL = 0;
    private static final java.lang.String LOG_TAG = "SlidingTab";
    private static final float THRESHOLD = 0.6666667f;
    private static final android.os.VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES = android.os.VibrationAttributes.createForUsage(18);
    private static final int TRACKING_MARGIN = 50;
    private static final int VERTICAL = 1;
    private static final long VIBRATE_LONG = 40;
    private static final long VIBRATE_SHORT = 30;
    private boolean mAnimating;
    private final android.view.animation.Animation.AnimationListener mAnimationDoneListener;
    private com.android.internal.widget.SlidingTab.Slider mCurrentSlider;
    private final float mDensity;
    private int mGrabbedState;
    private boolean mHoldLeftOnTransition;
    private boolean mHoldRightOnTransition;
    private final com.android.internal.widget.SlidingTab.Slider mLeftSlider;
    private com.android.internal.widget.SlidingTab.OnTriggerListener mOnTriggerListener;
    private final int mOrientation;
    private com.android.internal.widget.SlidingTab.Slider mOtherSlider;
    private final com.android.internal.widget.SlidingTab.Slider mRightSlider;
    private float mThreshold;
    private final android.graphics.Rect mTmpRect;
    private boolean mTracking;
    private boolean mTriggered;
    private android.os.Vibrator mVibrator;

    public interface OnTriggerListener {
        public static final int LEFT_HANDLE = 1;
        public static final int NO_HANDLE = 0;
        public static final int RIGHT_HANDLE = 2;

        void onGrabbedStateChange(android.view.View view, int i);

        void onTrigger(android.view.View view, int i);
    }

    private static class Slider {
        public static final int ALIGN_BOTTOM = 3;
        public static final int ALIGN_LEFT = 0;
        public static final int ALIGN_RIGHT = 1;
        public static final int ALIGN_TOP = 2;
        public static final int ALIGN_UNKNOWN = 4;
        private static final int STATE_ACTIVE = 2;
        private static final int STATE_NORMAL = 0;
        private static final int STATE_PRESSED = 1;
        private int alignment_value;
        private final android.widget.ImageView tab;
        private final android.widget.ImageView target;
        private final android.widget.TextView text;
        private int currentState = 0;
        private int alignment = 4;

        Slider(android.view.ViewGroup viewGroup, int i, int i2, int i3) {
            this.tab = new android.widget.ImageView(viewGroup.getContext());
            this.tab.setBackgroundResource(i);
            this.tab.setScaleType(android.widget.ImageView.ScaleType.CENTER);
            this.tab.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
            this.text = new android.widget.TextView(viewGroup.getContext());
            this.text.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -1));
            this.text.setBackgroundResource(i2);
            this.text.setTextAppearance(viewGroup.getContext(), com.android.internal.R.style.TextAppearance_SlidingTabNormal);
            this.target = new android.widget.ImageView(viewGroup.getContext());
            this.target.setImageResource(i3);
            this.target.setScaleType(android.widget.ImageView.ScaleType.CENTER);
            this.target.setLayoutParams(new android.view.ViewGroup.LayoutParams(-2, -2));
            this.target.setVisibility(4);
            viewGroup.addView(this.target);
            viewGroup.addView(this.tab);
            viewGroup.addView(this.text);
        }

        void setIcon(int i) {
            this.tab.setImageResource(i);
        }

        void setTabBackgroundResource(int i) {
            this.tab.setBackgroundResource(i);
        }

        void setBarBackgroundResource(int i) {
            this.text.setBackgroundResource(i);
        }

        void setHintText(int i) {
            this.text.setText(i);
        }

        void hide() {
            int i;
            int i2 = 0;
            boolean z = this.alignment == 0 || this.alignment == 1;
            if (z) {
                i = this.alignment == 0 ? this.alignment_value - this.tab.getRight() : this.alignment_value - this.tab.getLeft();
            } else {
                i = 0;
            }
            if (!z) {
                i2 = this.alignment == 2 ? this.alignment_value - this.tab.getBottom() : this.alignment_value - this.tab.getTop();
            }
            android.view.animation.TranslateAnimation translateAnimation = new android.view.animation.TranslateAnimation(0.0f, i, 0.0f, i2);
            translateAnimation.setDuration(250L);
            translateAnimation.setFillAfter(true);
            this.tab.startAnimation(translateAnimation);
            this.text.startAnimation(translateAnimation);
            this.target.setVisibility(4);
        }

        void show(boolean z) {
            this.text.setVisibility(0);
            this.tab.setVisibility(0);
            if (z) {
                boolean z2 = true;
                if (this.alignment != 0 && this.alignment != 1) {
                    z2 = false;
                }
                android.view.animation.TranslateAnimation translateAnimation = new android.view.animation.TranslateAnimation(-(z2 ? this.alignment == 0 ? this.tab.getWidth() : -this.tab.getWidth() : 0), 0.0f, -(z2 ? 0 : this.alignment == 2 ? this.tab.getHeight() : -this.tab.getHeight()), 0.0f);
                translateAnimation.setDuration(250L);
                this.tab.startAnimation(translateAnimation);
                this.text.startAnimation(translateAnimation);
            }
        }

        void setState(int i) {
            this.text.setPressed(i == 1);
            this.tab.setPressed(i == 1);
            if (i == 2) {
                int[] iArr = {16842914};
                if (this.text.getBackground().isStateful()) {
                    this.text.getBackground().setState(iArr);
                }
                if (this.tab.getBackground().isStateful()) {
                    this.tab.getBackground().setState(iArr);
                }
                this.text.setTextAppearance(this.text.getContext(), com.android.internal.R.style.TextAppearance_SlidingTabActive);
            } else {
                this.text.setTextAppearance(this.text.getContext(), com.android.internal.R.style.TextAppearance_SlidingTabNormal);
            }
            this.currentState = i;
        }

        void showTarget() {
            android.view.animation.AlphaAnimation alphaAnimation = new android.view.animation.AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(500L);
            this.target.startAnimation(alphaAnimation);
            this.target.setVisibility(0);
        }

        void reset(boolean z) {
            int i;
            int top;
            setState(0);
            this.text.setVisibility(0);
            this.text.setTextAppearance(this.text.getContext(), com.android.internal.R.style.TextAppearance_SlidingTabNormal);
            this.tab.setVisibility(0);
            this.target.setVisibility(4);
            boolean z2 = true;
            if (this.alignment != 0 && this.alignment != 1) {
                z2 = false;
            }
            if (!z2) {
                i = 0;
            } else {
                i = this.alignment == 0 ? this.alignment_value - this.tab.getLeft() : this.alignment_value - this.tab.getRight();
            }
            if (z2) {
                top = 0;
            } else {
                top = this.alignment == 2 ? this.alignment_value - this.tab.getTop() : this.alignment_value - this.tab.getBottom();
            }
            if (z) {
                android.view.animation.TranslateAnimation translateAnimation = new android.view.animation.TranslateAnimation(0.0f, i, 0.0f, top);
                translateAnimation.setDuration(250L);
                translateAnimation.setFillAfter(false);
                this.text.startAnimation(translateAnimation);
                this.tab.startAnimation(translateAnimation);
                return;
            }
            if (z2) {
                this.text.offsetLeftAndRight(i);
                this.tab.offsetLeftAndRight(i);
            } else {
                this.text.offsetTopAndBottom(top);
                this.tab.offsetTopAndBottom(top);
            }
            this.text.clearAnimation();
            this.tab.clearAnimation();
            this.target.clearAnimation();
        }

        void setTarget(int i) {
            this.target.setImageResource(i);
        }

        void layout(int i, int i2, int i3, int i4, int i5) {
            this.alignment = i5;
            android.graphics.drawable.Drawable background = this.tab.getBackground();
            int intrinsicWidth = background.getIntrinsicWidth();
            int intrinsicHeight = background.getIntrinsicHeight();
            android.graphics.drawable.Drawable drawable = this.target.getDrawable();
            int intrinsicWidth2 = drawable.getIntrinsicWidth();
            int intrinsicHeight2 = drawable.getIntrinsicHeight();
            int i6 = i3 - i;
            int i7 = i4 - i2;
            float f = i6;
            int i8 = intrinsicWidth / 2;
            int i9 = (((int) (f * com.android.internal.widget.SlidingTab.THRESHOLD)) - intrinsicWidth2) + i8;
            int i10 = ((int) (f * 0.3333333f)) - i8;
            int i11 = i6 - intrinsicWidth;
            int i12 = i11 / 2;
            int i13 = i12 + intrinsicWidth;
            if (i5 == 0 || i5 == 1) {
                int i14 = (i7 - intrinsicHeight2) / 2;
                int i15 = intrinsicHeight2 + i14;
                int i16 = (i7 - intrinsicHeight) / 2;
                int i17 = (i7 + intrinsicHeight) / 2;
                if (i5 == 0) {
                    this.tab.layout(0, i16, intrinsicWidth, i17);
                    this.text.layout(0 - i6, i16, 0, i17);
                    this.text.setGravity(5);
                    this.target.layout(i9, i14, intrinsicWidth2 + i9, i15);
                    this.alignment_value = i;
                    return;
                }
                this.tab.layout(i11, i16, i6, i17);
                this.text.layout(i6, i16, i6 + i6, i17);
                this.target.layout(i10, i14, i10 + intrinsicWidth2, i15);
                this.text.setGravity(48);
                this.alignment_value = i3;
                return;
            }
            int i18 = (i6 - intrinsicWidth2) / 2;
            int i19 = (i6 + intrinsicWidth2) / 2;
            float f2 = i7;
            int i20 = intrinsicHeight / 2;
            int i21 = (((int) (com.android.internal.widget.SlidingTab.THRESHOLD * f2)) + i20) - intrinsicHeight2;
            int i22 = ((int) (f2 * 0.3333333f)) - i20;
            if (i5 == 2) {
                this.tab.layout(i12, 0, i13, intrinsicHeight);
                this.text.layout(i12, 0 - i7, i13, 0);
                this.target.layout(i18, i21, i19, intrinsicHeight2 + i21);
                this.alignment_value = i2;
                return;
            }
            this.tab.layout(i12, i7 - intrinsicHeight, i13, i7);
            this.text.layout(i12, i7, i13, i7 + i7);
            this.target.layout(i18, i22, i19, intrinsicHeight2 + i22);
            this.alignment_value = i4;
        }

        public void updateDrawableStates() {
            setState(this.currentState);
        }

        public void measure(int i, int i2) {
            int size = android.view.View.MeasureSpec.getSize(i);
            int size2 = android.view.View.MeasureSpec.getSize(i2);
            this.tab.measure(android.view.View.MeasureSpec.makeSafeMeasureSpec(size, 0), android.view.View.MeasureSpec.makeSafeMeasureSpec(size2, 0));
            this.text.measure(android.view.View.MeasureSpec.makeSafeMeasureSpec(size, 0), android.view.View.MeasureSpec.makeSafeMeasureSpec(size2, 0));
        }

        public int getTabWidth() {
            return this.tab.getMeasuredWidth();
        }

        public int getTabHeight() {
            return this.tab.getMeasuredHeight();
        }

        public void startAnimation(android.view.animation.Animation animation, android.view.animation.Animation animation2) {
            this.tab.startAnimation(animation);
            this.text.startAnimation(animation2);
        }

        public void hideTarget() {
            this.target.clearAnimation();
            this.target.setVisibility(4);
        }
    }

    public SlidingTab(android.content.Context context) {
        this(context, null);
    }

    public SlidingTab(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHoldLeftOnTransition = true;
        this.mHoldRightOnTransition = true;
        this.mGrabbedState = 0;
        this.mTriggered = false;
        this.mAnimationDoneListener = new android.view.animation.Animation.AnimationListener() { // from class: com.android.internal.widget.SlidingTab.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(android.view.animation.Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(android.view.animation.Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(android.view.animation.Animation animation) {
                com.android.internal.widget.SlidingTab.this.onAnimationDone();
            }
        };
        this.mTmpRect = new android.graphics.Rect();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.SlidingTab);
        this.mOrientation = obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
        this.mDensity = getResources().getDisplayMetrics().density;
        this.mLeftSlider = new com.android.internal.widget.SlidingTab.Slider(this, com.android.internal.R.drawable.jog_tab_left_generic, com.android.internal.R.drawable.jog_tab_bar_left_generic, com.android.internal.R.drawable.jog_tab_target_gray);
        this.mRightSlider = new com.android.internal.widget.SlidingTab.Slider(this, com.android.internal.R.drawable.jog_tab_right_generic, com.android.internal.R.drawable.jog_tab_bar_right_generic, com.android.internal.R.drawable.jog_tab_target_gray);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int max;
        int max2;
        android.view.View.MeasureSpec.getMode(i);
        int size = android.view.View.MeasureSpec.getSize(i);
        android.view.View.MeasureSpec.getMode(i2);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        this.mLeftSlider.measure(i, i2);
        this.mRightSlider.measure(i, i2);
        int tabWidth = this.mLeftSlider.getTabWidth();
        int tabWidth2 = this.mRightSlider.getTabWidth();
        int tabHeight = this.mLeftSlider.getTabHeight();
        int tabHeight2 = this.mRightSlider.getTabHeight();
        if (isHorizontal()) {
            max = java.lang.Math.max(size, tabWidth + tabWidth2);
            max2 = java.lang.Math.max(tabHeight, tabHeight2);
        } else {
            max = java.lang.Math.max(tabWidth, tabHeight2);
            max2 = java.lang.Math.max(size2, tabHeight + tabHeight2);
        }
        setMeasuredDimension(max, max2);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (this.mAnimating) {
            return false;
        }
        this.mLeftSlider.tab.getHitRect(this.mTmpRect);
        int i = (int) x;
        int i2 = (int) y;
        boolean contains = this.mTmpRect.contains(i, i2);
        this.mRightSlider.tab.getHitRect(this.mTmpRect);
        boolean contains2 = this.mTmpRect.contains(i, i2);
        if (!this.mTracking && !contains && !contains2) {
            return false;
        }
        switch (action) {
            case 0:
                this.mTracking = true;
                this.mTriggered = false;
                vibrate(VIBRATE_SHORT);
                if (contains) {
                    this.mCurrentSlider = this.mLeftSlider;
                    this.mOtherSlider = this.mRightSlider;
                    this.mThreshold = isHorizontal() ? 0.6666667f : 0.3333333f;
                    setGrabbedState(1);
                } else {
                    this.mCurrentSlider = this.mRightSlider;
                    this.mOtherSlider = this.mLeftSlider;
                    this.mThreshold = isHorizontal() ? 0.3333333f : 0.6666667f;
                    setGrabbedState(2);
                }
                this.mCurrentSlider.setState(1);
                this.mCurrentSlider.showTarget();
                this.mOtherSlider.hide();
            default:
                return true;
        }
    }

    public void reset(boolean z) {
        this.mLeftSlider.reset(z);
        this.mRightSlider.reset(z);
        if (!z) {
            this.mAnimating = false;
        }
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        if (i != getVisibility() && i == 4) {
            reset(false);
        }
        super.setVisibility(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0050, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0063, code lost:
    
        r0 = true;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        boolean z;
        if (this.mTracking) {
            int action = motionEvent.getAction();
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            switch (action) {
                case 2:
                    if (withinView(x, y, this)) {
                        moveHandle(x, y);
                        if (!isHorizontal()) {
                            x = y;
                        }
                        float width = this.mThreshold * (isHorizontal() ? getWidth() : getHeight());
                        if (isHorizontal()) {
                            if (this.mCurrentSlider != this.mLeftSlider) {
                                z = false;
                                break;
                            } else {
                                z = false;
                                break;
                            }
                        } else if (this.mCurrentSlider != this.mLeftSlider) {
                            z = false;
                            break;
                        } else {
                            z = false;
                            break;
                        }
                        if (!this.mTriggered && z) {
                            this.mTriggered = true;
                            this.mTracking = false;
                            this.mCurrentSlider.setState(2);
                            boolean z2 = this.mCurrentSlider == this.mLeftSlider;
                            dispatchTriggerEvent(z2 ? 1 : 2);
                            startAnimating(z2 ? this.mHoldLeftOnTransition : this.mHoldRightOnTransition);
                            setGrabbedState(0);
                            break;
                        }
                    }
                    break;
                case 1:
                case 3:
                    cancelGrab();
                    break;
            }
        }
        return this.mTracking || super.onTouchEvent(motionEvent);
    }

    private void cancelGrab() {
        this.mTracking = false;
        this.mTriggered = false;
        this.mOtherSlider.show(true);
        this.mCurrentSlider.reset(false);
        this.mCurrentSlider.hideTarget();
        this.mCurrentSlider = null;
        this.mOtherSlider = null;
        setGrabbedState(0);
    }

    void startAnimating(final boolean z) {
        final int i;
        this.mAnimating = true;
        com.android.internal.widget.SlidingTab.Slider slider = this.mCurrentSlider;
        final int i2 = 0;
        if (isHorizontal()) {
            int right = slider.tab.getRight();
            int width = slider.tab.getWidth();
            int left = slider.tab.getLeft();
            int width2 = getWidth();
            if (z) {
                width = 0;
            }
            i2 = slider == this.mRightSlider ? -((right + width2) - width) : ((width2 - left) + width2) - width;
            i = 0;
        } else {
            int top = slider.tab.getTop();
            int bottom = slider.tab.getBottom();
            int height = slider.tab.getHeight();
            int height2 = getHeight();
            if (z) {
                height = 0;
            }
            i = slider == this.mRightSlider ? (top + height2) - height : -(((height2 - bottom) + height2) - height);
        }
        float f = i2;
        float f2 = i;
        android.view.animation.TranslateAnimation translateAnimation = new android.view.animation.TranslateAnimation(0.0f, f, 0.0f, f2);
        translateAnimation.setDuration(250L);
        translateAnimation.setInterpolator(new android.view.animation.LinearInterpolator());
        translateAnimation.setFillAfter(true);
        android.view.animation.TranslateAnimation translateAnimation2 = new android.view.animation.TranslateAnimation(0.0f, f, 0.0f, f2);
        translateAnimation2.setDuration(250L);
        translateAnimation2.setInterpolator(new android.view.animation.LinearInterpolator());
        translateAnimation2.setFillAfter(true);
        translateAnimation.setAnimationListener(new android.view.animation.Animation.AnimationListener() { // from class: com.android.internal.widget.SlidingTab.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(android.view.animation.Animation animation) {
                android.view.animation.Animation alphaAnimation;
                if (z) {
                    alphaAnimation = new android.view.animation.TranslateAnimation(i2, i2, i, i);
                    alphaAnimation.setDuration(1000L);
                    com.android.internal.widget.SlidingTab.this.mAnimating = false;
                } else {
                    alphaAnimation = new android.view.animation.AlphaAnimation(0.5f, 1.0f);
                    alphaAnimation.setDuration(250L);
                    com.android.internal.widget.SlidingTab.this.resetView();
                }
                alphaAnimation.setAnimationListener(com.android.internal.widget.SlidingTab.this.mAnimationDoneListener);
                com.android.internal.widget.SlidingTab.this.mLeftSlider.startAnimation(alphaAnimation, alphaAnimation);
                com.android.internal.widget.SlidingTab.this.mRightSlider.startAnimation(alphaAnimation, alphaAnimation);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(android.view.animation.Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(android.view.animation.Animation animation) {
            }
        });
        slider.hideTarget();
        slider.startAnimation(translateAnimation, translateAnimation2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAnimationDone() {
        resetView();
        this.mAnimating = false;
    }

    private boolean withinView(float f, float f2, android.view.View view) {
        return (isHorizontal() && f2 > -50.0f && f2 < ((float) (view.getHeight() + 50))) || (!isHorizontal() && f > -50.0f && f < ((float) (view.getWidth() + 50)));
    }

    private boolean isHorizontal() {
        return this.mOrientation == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetView() {
        this.mLeftSlider.reset(false);
        this.mRightSlider.reset(false);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (z) {
            this.mLeftSlider.layout(i, i2, i3, i4, isHorizontal() ? 0 : 3);
            this.mRightSlider.layout(i, i2, i3, i4, isHorizontal() ? 1 : 2);
        }
    }

    private void moveHandle(float f, float f2) {
        android.widget.ImageView imageView = this.mCurrentSlider.tab;
        android.widget.TextView textView = this.mCurrentSlider.text;
        if (isHorizontal()) {
            int left = (((int) f) - imageView.getLeft()) - (imageView.getWidth() / 2);
            imageView.offsetLeftAndRight(left);
            textView.offsetLeftAndRight(left);
        } else {
            int top = (((int) f2) - imageView.getTop()) - (imageView.getHeight() / 2);
            imageView.offsetTopAndBottom(top);
            textView.offsetTopAndBottom(top);
        }
        invalidate();
    }

    public void setLeftTabResources(int i, int i2, int i3, int i4) {
        this.mLeftSlider.setIcon(i);
        this.mLeftSlider.setTarget(i2);
        this.mLeftSlider.setBarBackgroundResource(i3);
        this.mLeftSlider.setTabBackgroundResource(i4);
        this.mLeftSlider.updateDrawableStates();
    }

    public void setLeftHintText(int i) {
        if (isHorizontal()) {
            this.mLeftSlider.setHintText(i);
        }
    }

    public void setRightTabResources(int i, int i2, int i3, int i4) {
        this.mRightSlider.setIcon(i);
        this.mRightSlider.setTarget(i2);
        this.mRightSlider.setBarBackgroundResource(i3);
        this.mRightSlider.setTabBackgroundResource(i4);
        this.mRightSlider.updateDrawableStates();
    }

    public void setRightHintText(int i) {
        if (isHorizontal()) {
            this.mRightSlider.setHintText(i);
        }
    }

    public void setHoldAfterTrigger(boolean z, boolean z2) {
        this.mHoldLeftOnTransition = z;
        this.mHoldRightOnTransition = z2;
    }

    private synchronized void vibrate(long j) {
        if (this.mVibrator == null) {
            this.mVibrator = (android.os.Vibrator) getContext().getSystemService(android.os.Vibrator.class);
        }
        this.mVibrator.vibrate(android.os.VibrationEffect.createOneShot(j, -1), TOUCH_VIBRATION_ATTRIBUTES);
    }

    public void setOnTriggerListener(com.android.internal.widget.SlidingTab.OnTriggerListener onTriggerListener) {
        this.mOnTriggerListener = onTriggerListener;
    }

    private void dispatchTriggerEvent(int i) {
        vibrate(VIBRATE_LONG);
        if (this.mOnTriggerListener != null) {
            this.mOnTriggerListener.onTrigger(this, i);
        }
    }

    @Override // android.view.View
    protected void onVisibilityChanged(android.view.View view, int i) {
        super.onVisibilityChanged(view, i);
        if (view == this && i != 0 && this.mGrabbedState != 0) {
            cancelGrab();
        }
    }

    private void setGrabbedState(int i) {
        if (i != this.mGrabbedState) {
            this.mGrabbedState = i;
            if (this.mOnTriggerListener != null) {
                this.mOnTriggerListener.onGrabbedStateChange(this, this.mGrabbedState);
            }
        }
    }

    private void log(java.lang.String str) {
        android.util.Log.d(LOG_TAG, str);
    }
}
