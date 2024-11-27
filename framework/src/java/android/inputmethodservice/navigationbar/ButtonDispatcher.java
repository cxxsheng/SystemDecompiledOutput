package android.inputmethodservice.navigationbar;

/* loaded from: classes2.dex */
final class ButtonDispatcher {
    private static final int FADE_DURATION_IN = 150;
    private static final int FADE_DURATION_OUT = 250;
    public static final android.view.animation.Interpolator LINEAR = new android.view.animation.LinearInterpolator();
    private android.view.View.AccessibilityDelegate mAccessibilityDelegate;
    private android.view.View.OnClickListener mClickListener;
    private android.view.View mCurrentView;
    private java.lang.Float mDarkIntensity;
    private java.lang.Boolean mDelayTouchFeedback;
    private android.animation.ValueAnimator mFadeAnimator;
    private final int mId;
    private android.inputmethodservice.navigationbar.KeyButtonDrawable mImageDrawable;
    private android.view.View.OnLongClickListener mLongClickListener;
    private java.lang.Boolean mLongClickable;
    private android.view.View.OnHoverListener mOnHoverListener;
    private android.view.View.OnTouchListener mTouchListener;
    private final java.util.ArrayList<android.view.View> mViews = new java.util.ArrayList<>();
    private float mAlpha = 1.0f;
    private int mVisibility = 0;
    private final android.animation.ValueAnimator.AnimatorUpdateListener mAlphaListener = new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.inputmethodservice.navigationbar.ButtonDispatcher$$ExternalSyntheticLambda0
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
            android.inputmethodservice.navigationbar.ButtonDispatcher.this.lambda$new$0(valueAnimator);
        }
    };
    private final android.animation.AnimatorListenerAdapter mFadeListener = new android.animation.AnimatorListenerAdapter() { // from class: android.inputmethodservice.navigationbar.ButtonDispatcher.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(android.animation.Animator animator) {
            android.inputmethodservice.navigationbar.ButtonDispatcher.this.mFadeAnimator = null;
            android.inputmethodservice.navigationbar.ButtonDispatcher.this.setVisibility(android.inputmethodservice.navigationbar.ButtonDispatcher.this.getAlpha() == 1.0f ? 0 : 4);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.animation.ValueAnimator valueAnimator) {
        setAlpha(((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue(), false, false);
    }

    ButtonDispatcher(int i) {
        this.mId = i;
    }

    public void clear() {
        this.mViews.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void addView(android.view.View view) {
        this.mViews.add(view);
        view.setOnClickListener(this.mClickListener);
        view.setOnTouchListener(this.mTouchListener);
        view.setOnLongClickListener(this.mLongClickListener);
        view.setOnHoverListener(this.mOnHoverListener);
        if (this.mLongClickable != null) {
            view.setLongClickable(this.mLongClickable.booleanValue());
        }
        view.setAlpha(this.mAlpha);
        view.setVisibility(this.mVisibility);
        if (this.mAccessibilityDelegate != null) {
            view.setAccessibilityDelegate(this.mAccessibilityDelegate);
        }
        if (view instanceof android.inputmethodservice.navigationbar.ButtonInterface) {
            android.inputmethodservice.navigationbar.ButtonInterface buttonInterface = (android.inputmethodservice.navigationbar.ButtonInterface) view;
            if (this.mDarkIntensity != null) {
                buttonInterface.setDarkIntensity(this.mDarkIntensity.floatValue());
            }
            if (this.mImageDrawable != null) {
                buttonInterface.setImageDrawable(this.mImageDrawable);
            }
            if (this.mDelayTouchFeedback != null) {
                buttonInterface.setDelayTouchFeedback(this.mDelayTouchFeedback.booleanValue());
            }
        }
    }

    public int getId() {
        return this.mId;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public boolean isVisible() {
        return getVisibility() == 0;
    }

    public float getAlpha() {
        return this.mAlpha;
    }

    public android.inputmethodservice.navigationbar.KeyButtonDrawable getImageDrawable() {
        return this.mImageDrawable;
    }

    public void setImageDrawable(android.inputmethodservice.navigationbar.KeyButtonDrawable keyButtonDrawable) {
        this.mImageDrawable = keyButtonDrawable;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            if (this.mViews.get(i) instanceof android.inputmethodservice.navigationbar.ButtonInterface) {
                ((android.inputmethodservice.navigationbar.ButtonInterface) this.mViews.get(i)).setImageDrawable(this.mImageDrawable);
            }
        }
        if (this.mImageDrawable != null) {
            this.mImageDrawable.setCallback(this.mCurrentView);
        }
    }

    public void setVisibility(int i) {
        if (this.mVisibility == i) {
            return;
        }
        if (this.mFadeAnimator != null) {
            this.mFadeAnimator.cancel();
        }
        this.mVisibility = i;
        int size = this.mViews.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mViews.get(i2).setVisibility(this.mVisibility);
        }
    }

    public void setAlpha(float f) {
        setAlpha(f, false);
    }

    public void setAlpha(float f, boolean z) {
        setAlpha(f, z, true);
    }

    public void setAlpha(float f, boolean z, long j) {
        setAlpha(f, z, j, true);
    }

    public void setAlpha(float f, boolean z, boolean z2) {
        setAlpha(f, z, getAlpha() < f ? 150L : 250L, z2);
    }

    public void setAlpha(float f, boolean z, long j, boolean z2) {
        if (this.mFadeAnimator != null && (z2 || z)) {
            this.mFadeAnimator.cancel();
        }
        if (z) {
            setVisibility(0);
            this.mFadeAnimator = android.animation.ValueAnimator.ofFloat(getAlpha(), f);
            this.mFadeAnimator.setDuration(j);
            this.mFadeAnimator.setInterpolator(LINEAR);
            this.mFadeAnimator.addListener(this.mFadeListener);
            this.mFadeAnimator.addUpdateListener(this.mAlphaListener);
            this.mFadeAnimator.start();
            return;
        }
        int i = (int) (f * 255.0f);
        if (((int) (getAlpha() * 255.0f)) != i) {
            this.mAlpha = i / 255.0f;
            int size = this.mViews.size();
            for (int i2 = 0; i2 < size; i2++) {
                this.mViews.get(i2).setAlpha(this.mAlpha);
            }
        }
    }

    public void setDarkIntensity(float f) {
        this.mDarkIntensity = java.lang.Float.valueOf(f);
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            if (this.mViews.get(i) instanceof android.inputmethodservice.navigationbar.ButtonInterface) {
                ((android.inputmethodservice.navigationbar.ButtonInterface) this.mViews.get(i)).setDarkIntensity(f);
            }
        }
    }

    public void setDelayTouchFeedback(boolean z) {
        this.mDelayTouchFeedback = java.lang.Boolean.valueOf(z);
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            if (this.mViews.get(i) instanceof android.inputmethodservice.navigationbar.ButtonInterface) {
                ((android.inputmethodservice.navigationbar.ButtonInterface) this.mViews.get(i)).setDelayTouchFeedback(z);
            }
        }
    }

    public void setOnClickListener(android.view.View.OnClickListener onClickListener) {
        this.mClickListener = onClickListener;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            this.mViews.get(i).setOnClickListener(this.mClickListener);
        }
    }

    public void setOnTouchListener(android.view.View.OnTouchListener onTouchListener) {
        this.mTouchListener = onTouchListener;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            this.mViews.get(i).setOnTouchListener(this.mTouchListener);
        }
    }

    public void setLongClickable(boolean z) {
        this.mLongClickable = java.lang.Boolean.valueOf(z);
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            this.mViews.get(i).setLongClickable(this.mLongClickable.booleanValue());
        }
    }

    public void setOnLongClickListener(android.view.View.OnLongClickListener onLongClickListener) {
        this.mLongClickListener = onLongClickListener;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            this.mViews.get(i).setOnLongClickListener(this.mLongClickListener);
        }
    }

    public void setOnHoverListener(android.view.View.OnHoverListener onHoverListener) {
        this.mOnHoverListener = onHoverListener;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            this.mViews.get(i).setOnHoverListener(this.mOnHoverListener);
        }
    }

    public void setAccessibilityDelegate(android.view.View.AccessibilityDelegate accessibilityDelegate) {
        this.mAccessibilityDelegate = accessibilityDelegate;
        int size = this.mViews.size();
        for (int i = 0; i < size; i++) {
            this.mViews.get(i).setAccessibilityDelegate(accessibilityDelegate);
        }
    }

    public void setTranslation(int i, int i2, int i3) {
        int size = this.mViews.size();
        for (int i4 = 0; i4 < size; i4++) {
            android.view.View view = this.mViews.get(i4);
            view.setTranslationX(i);
            view.setTranslationY(i2);
            view.setTranslationZ(i3);
        }
    }

    public java.util.ArrayList<android.view.View> getViews() {
        return this.mViews;
    }

    public android.view.View getCurrentView() {
        return this.mCurrentView;
    }

    public void setCurrentView(android.view.View view) {
        this.mCurrentView = view.findViewById(this.mId);
        if (this.mImageDrawable != null) {
            this.mImageDrawable.setCallback(this.mCurrentView);
        }
        if (this.mCurrentView != null) {
            this.mCurrentView.setTranslationX(0.0f);
            this.mCurrentView.setTranslationY(0.0f);
            this.mCurrentView.setTranslationZ(0.0f);
        }
    }

    public void onDestroy() {
    }
}
