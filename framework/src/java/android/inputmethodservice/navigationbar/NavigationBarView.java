package android.inputmethodservice.navigationbar;

/* loaded from: classes2.dex */
public final class NavigationBarView extends android.widget.FrameLayout {
    private static final boolean DEBUG = false;
    private static final android.view.animation.Interpolator FAST_OUT_SLOW_IN = new android.view.animation.PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    private static final java.lang.String TAG = "NavBarView";
    private android.inputmethodservice.navigationbar.KeyButtonDrawable mBackIcon;
    private final android.util.SparseArray<android.inputmethodservice.navigationbar.ButtonDispatcher> mButtonDispatchers;
    private android.content.res.Configuration mConfiguration;
    private int mCurrentRotation;
    android.view.View mCurrentView;
    private final int mDarkIconColor;
    private final android.inputmethodservice.navigationbar.DeadZone mDeadZone;
    private boolean mDeadZoneConsuming;
    int mDisabledFlags;
    private android.view.View mHorizontal;
    private android.inputmethodservice.navigationbar.KeyButtonDrawable mImeSwitcherIcon;
    private android.content.Context mLightContext;
    private final int mLightIconColor;
    private final int mNavBarMode;
    int mNavigationIconHints;
    private android.inputmethodservice.navigationbar.NavigationBarInflaterView mNavigationInflaterView;
    private android.content.res.Configuration mTmpLastConfiguration;

    public NavigationBarView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentView = null;
        this.mCurrentRotation = -1;
        this.mDisabledFlags = 0;
        this.mNavigationIconHints = 1;
        this.mNavBarMode = 2;
        this.mDeadZoneConsuming = false;
        this.mButtonDispatchers = new android.util.SparseArray<>();
        this.mLightContext = context;
        this.mLightIconColor = -1;
        this.mDarkIconColor = -1728053248;
        this.mConfiguration = new android.content.res.Configuration();
        this.mTmpLastConfiguration = new android.content.res.Configuration();
        this.mConfiguration.updateFrom(context.getResources().getConfiguration());
        this.mButtonDispatchers.put(com.android.internal.R.id.input_method_nav_back, new android.inputmethodservice.navigationbar.ButtonDispatcher(com.android.internal.R.id.input_method_nav_back));
        this.mButtonDispatchers.put(com.android.internal.R.id.input_method_nav_ime_switcher, new android.inputmethodservice.navigationbar.ButtonDispatcher(com.android.internal.R.id.input_method_nav_ime_switcher));
        this.mButtonDispatchers.put(com.android.internal.R.id.input_method_nav_home_handle, new android.inputmethodservice.navigationbar.ButtonDispatcher(com.android.internal.R.id.input_method_nav_home_handle));
        this.mDeadZone = new android.inputmethodservice.navigationbar.DeadZone(this);
        getBackButton().setLongClickable(false);
        android.inputmethodservice.navigationbar.ButtonDispatcher imeSwitchButton = getImeSwitchButton();
        imeSwitchButton.setLongClickable(false);
        imeSwitchButton.setOnClickListener(new android.view.View.OnClickListener() { // from class: android.inputmethodservice.navigationbar.NavigationBarView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(android.view.View view) {
                ((android.view.inputmethod.InputMethodManager) view.getContext().getSystemService(android.view.inputmethod.InputMethodManager.class)).showInputMethodPicker();
            }
        });
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        return shouldDeadZoneConsumeTouchEvents(motionEvent) || super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        shouldDeadZoneConsumeTouchEvents(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0022, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean shouldDeadZoneConsumeTouchEvents(android.view.MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mDeadZoneConsuming = false;
        }
        if (!this.mDeadZone.onTouchEvent(motionEvent) && !this.mDeadZoneConsuming) {
            return false;
        }
        switch (actionMasked) {
            case 0:
                this.mDeadZoneConsuming = true;
                break;
            case 1:
            case 3:
                this.mDeadZoneConsuming = false;
                break;
        }
    }

    public android.view.View getCurrentView() {
        return this.mCurrentView;
    }

    public void forEachView(java.util.function.Consumer<android.view.View> consumer) {
        if (this.mHorizontal != null) {
            consumer.accept(this.mHorizontal);
        }
    }

    public android.inputmethodservice.navigationbar.ButtonDispatcher getBackButton() {
        return this.mButtonDispatchers.get(com.android.internal.R.id.input_method_nav_back);
    }

    public android.inputmethodservice.navigationbar.ButtonDispatcher getImeSwitchButton() {
        return this.mButtonDispatchers.get(com.android.internal.R.id.input_method_nav_ime_switcher);
    }

    public android.inputmethodservice.navigationbar.ButtonDispatcher getHomeHandle() {
        return this.mButtonDispatchers.get(com.android.internal.R.id.input_method_nav_home_handle);
    }

    public android.util.SparseArray<android.inputmethodservice.navigationbar.ButtonDispatcher> getButtonDispatchers() {
        return this.mButtonDispatchers;
    }

    private void reloadNavIcons() {
        updateIcons(android.content.res.Configuration.EMPTY);
    }

    private void updateIcons(android.content.res.Configuration configuration) {
        boolean z = configuration.orientation != this.mConfiguration.orientation;
        boolean z2 = configuration.densityDpi != this.mConfiguration.densityDpi;
        boolean z3 = configuration.getLayoutDirection() != this.mConfiguration.getLayoutDirection();
        if (z2 || z3) {
            this.mImeSwitcherIcon = getDrawable(com.android.internal.R.drawable.ic_ime_switcher);
        }
        if (z || z2 || z3) {
            this.mBackIcon = getBackDrawable();
        }
    }

    private android.inputmethodservice.navigationbar.KeyButtonDrawable getBackDrawable() {
        android.inputmethodservice.navigationbar.KeyButtonDrawable drawable = getDrawable(com.android.internal.R.drawable.ic_ime_nav_back);
        orientBackButton(drawable);
        return drawable;
    }

    public static boolean isGesturalMode(int i) {
        return i == 2;
    }

    private void orientBackButton(android.inputmethodservice.navigationbar.KeyButtonDrawable keyButtonDrawable) {
        float f;
        boolean z = (this.mNavigationIconHints & 1) != 0;
        boolean z2 = this.mConfiguration.getLayoutDirection() == 1;
        float f2 = 0.0f;
        if (z) {
            f = z2 ? 90 : -90;
        } else {
            f = 0.0f;
        }
        if (keyButtonDrawable.getRotation() == f) {
            return;
        }
        if (isGesturalMode(2)) {
            keyButtonDrawable.setRotation(f);
            return;
        }
        if (z) {
            f2 = -android.inputmethodservice.navigationbar.NavigationBarUtils.dpToPx(2.0f, getResources());
        }
        android.animation.ObjectAnimator ofPropertyValuesHolder = android.animation.ObjectAnimator.ofPropertyValuesHolder(keyButtonDrawable, android.animation.PropertyValuesHolder.ofFloat(android.inputmethodservice.navigationbar.KeyButtonDrawable.KEY_DRAWABLE_ROTATE, f), android.animation.PropertyValuesHolder.ofFloat(android.inputmethodservice.navigationbar.KeyButtonDrawable.KEY_DRAWABLE_TRANSLATE_Y, f2));
        ofPropertyValuesHolder.setInterpolator(FAST_OUT_SLOW_IN);
        ofPropertyValuesHolder.setDuration(200L);
        ofPropertyValuesHolder.start();
    }

    private android.inputmethodservice.navigationbar.KeyButtonDrawable getDrawable(int i) {
        return android.inputmethodservice.navigationbar.KeyButtonDrawable.create(this.mLightContext, this.mLightIconColor, this.mDarkIconColor, i, true, null);
    }

    @Override // android.view.View
    public void setLayoutDirection(int i) {
        reloadNavIcons();
        super.setLayoutDirection(i);
    }

    public void setNavigationIconHints(int i) {
        if (i == this.mNavigationIconHints) {
            return;
        }
        this.mNavigationIconHints = i;
        updateNavButtonIcons();
    }

    private void updateNavButtonIcons() {
        android.inputmethodservice.navigationbar.KeyButtonDrawable keyButtonDrawable = this.mBackIcon;
        orientBackButton(keyButtonDrawable);
        getBackButton().setImageDrawable(keyButtonDrawable);
        getImeSwitchButton().setImageDrawable(this.mImeSwitcherIcon);
        getImeSwitchButton().setVisibility((this.mNavigationIconHints & 4) != 0 ? 0 : 4);
        getBackButton().setVisibility(0);
        getHomeHandle().setVisibility(4);
    }

    private android.view.Display getContextDisplay() {
        return getContext().getDisplay();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mNavigationInflaterView = (android.inputmethodservice.navigationbar.NavigationBarInflaterView) findViewById(com.android.internal.R.id.input_method_nav_inflater);
        this.mNavigationInflaterView.setButtonDispatchers(this.mButtonDispatchers);
        updateOrientationViews();
        reloadNavIcons();
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        this.mDeadZone.onDraw(canvas);
        super.onDraw(canvas);
    }

    private void updateOrientationViews() {
        this.mHorizontal = findViewById(com.android.internal.R.id.input_method_nav_horizontal);
        updateCurrentView();
    }

    private void updateCurrentView() {
        resetViews();
        this.mCurrentView = this.mHorizontal;
        this.mCurrentView.setVisibility(0);
        this.mCurrentRotation = getContextDisplay().getRotation();
        this.mNavigationInflaterView.setAlternativeOrder(this.mCurrentRotation == 1);
        this.mNavigationInflaterView.updateButtonDispatchersCurrentView();
    }

    private void resetViews() {
        this.mHorizontal.setVisibility(8);
    }

    private void reorient() {
        updateCurrentView();
        ((android.inputmethodservice.navigationbar.NavigationBarFrame) getRootView().findViewByPredicate(new java.util.function.Predicate() { // from class: android.inputmethodservice.navigationbar.NavigationBarView$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return android.inputmethodservice.navigationbar.NavigationBarView.lambda$reorient$1((android.view.View) obj);
            }
        })).setDeadZone(this.mDeadZone);
        this.mDeadZone.onConfigurationChanged(this.mCurrentRotation);
        if (!isLayoutDirectionResolved()) {
            resolveLayoutDirection();
        }
        updateNavButtonIcons();
    }

    static /* synthetic */ boolean lambda$reorient$1(android.view.View view) {
        return view instanceof android.inputmethodservice.navigationbar.NavigationBarFrame;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mTmpLastConfiguration.updateFrom(this.mConfiguration);
        this.mConfiguration.updateFrom(configuration);
        updateIcons(this.mTmpLastConfiguration);
        if (this.mTmpLastConfiguration.densityDpi != this.mConfiguration.densityDpi || this.mTmpLastConfiguration.getLayoutDirection() != this.mConfiguration.getLayoutDirection()) {
            updateNavButtonIcons();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        requestApplyInsets();
        reorient();
        updateNavButtonIcons();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
            this.mButtonDispatchers.valueAt(i).onDestroy();
        }
    }

    public void setDarkIntensity(float f) {
        for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
            this.mButtonDispatchers.valueAt(i).setDarkIntensity(f);
        }
    }
}
