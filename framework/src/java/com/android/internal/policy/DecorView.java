package com.android.internal.policy;

/* loaded from: classes5.dex */
public class DecorView extends android.widget.FrameLayout implements com.android.internal.view.RootViewSurfaceTaker, android.view.WindowCallbacks {
    private static final boolean DEBUG_MEASURE = false;
    public static final int DECOR_SHADOW_FOCUSED_HEIGHT_IN_DIP = 20;
    public static final int DECOR_SHADOW_UNFOCUSED_HEIGHT_IN_DIP = 5;
    private static final int SCRIM_LIGHT = -419430401;
    private static final boolean SWEEP_OPEN_MENU = false;
    private static final java.lang.String TAG = "DecorView";
    private boolean mAllowUpdateElevation;
    private boolean mApplyFloatingHorizontalInsets;
    private boolean mApplyFloatingVerticalInsets;
    private com.android.internal.policy.BackdropFrameRenderer mBackdropFrameRenderer;
    private com.android.internal.graphics.drawable.BackgroundBlurDrawable mBackgroundBlurDrawable;
    private final android.view.ViewTreeObserver.OnPreDrawListener mBackgroundBlurOnPreDrawListener;
    private int mBackgroundBlurRadius;
    private final com.android.internal.widget.BackgroundFallback mBackgroundFallback;
    private android.graphics.Insets mBackgroundInsets;
    private final android.graphics.Rect mBackgroundPadding;
    private final int mBarEnterExitDuration;
    private android.graphics.drawable.Drawable mCaptionBackgroundDrawable;
    private boolean mChanging;
    android.view.ViewGroup mContentRoot;
    private boolean mCrossWindowBlurEnabled;
    private java.util.function.Consumer<java.lang.Boolean> mCrossWindowBlurEnabledListener;
    private com.android.internal.widget.DecorCaptionView mDecorCaptionView;
    int mDefaultOpacity;
    private int mDownY;
    private boolean mDrawLegacyNavigationBarBackground;
    private boolean mDrawLegacyNavigationBarBackgroundHandled;
    private final android.graphics.Rect mDrawingBounds;
    private boolean mElevationAdjustedForStack;
    private android.animation.ObjectAnimator mFadeAnim;
    private final int mFeatureId;
    private android.view.ActionMode mFloatingActionMode;
    private android.view.View mFloatingActionModeOriginatingView;
    private final android.graphics.Rect mFloatingInsets;
    private com.android.internal.widget.floatingtoolbar.FloatingToolbar mFloatingToolbar;
    private android.view.ViewTreeObserver.OnPreDrawListener mFloatingToolbarPreDrawListener;
    final boolean mForceWindowDrawsBarBackgrounds;
    private final android.graphics.Rect mFrameOffsets;
    private final android.graphics.Rect mFramePadding;
    private boolean mHasCaption;
    private final android.view.animation.Interpolator mHideInterpolator;
    private boolean mIsInPictureInPictureMode;
    private com.android.internal.graphics.drawable.BackgroundBlurDrawable mLastBackgroundBlurDrawable;
    private android.graphics.drawable.Drawable.Callback mLastBackgroundDrawableCb;
    private android.graphics.Insets mLastBackgroundInsets;
    private int mLastBottomInset;
    private int mLastForceConsumingTypes;
    private boolean mLastHasBottomStableInset;
    private boolean mLastHasLeftStableInset;
    private boolean mLastHasRightStableInset;
    private boolean mLastHasTopStableInset;
    private int mLastLeftInset;
    private android.graphics.drawable.Drawable mLastOriginalBackgroundDrawable;
    private android.view.ViewOutlineProvider mLastOutlineProvider;
    private int mLastRightInset;
    private int mLastSuppressScrimTypes;
    private int mLastTopInset;
    private int mLastWindowFlags;
    private final android.graphics.Paint mLegacyNavigationBarBackgroundPaint;
    java.lang.String mLogTag;
    private android.graphics.drawable.Drawable mMenuBackground;
    private final com.android.internal.policy.DecorView.ColorViewState mNavigationColorViewState;
    private int mOriginalBackgroundBlurRadius;
    private android.graphics.drawable.Drawable mOriginalBackgroundDrawable;
    private android.view.PendingInsetsController mPendingInsetsController;
    private android.graphics.drawable.Drawable mPendingWindowBackground;
    android.view.ActionMode mPrimaryActionMode;
    private android.widget.PopupWindow mPrimaryActionModePopup;
    private com.android.internal.widget.ActionBarContextView mPrimaryActionModeView;
    private android.graphics.drawable.Drawable mResizingBackgroundDrawable;
    private int mRootScrollY;
    private final int mSemiTransparentBarColor;
    private final android.view.animation.Interpolator mShowInterpolator;
    private java.lang.Runnable mShowPrimaryActionModePopup;
    private final com.android.internal.policy.DecorView.ColorViewState mStatusColorViewState;
    private android.view.View mStatusGuard;
    private android.graphics.Rect mTempRect;
    private android.graphics.drawable.Drawable mUserCaptionBackgroundDrawable;
    private boolean mWatchingForMenu;
    private final com.android.internal.policy.WearGestureInterceptionDetector mWearGestureInterceptionDetector;
    private com.android.internal.policy.PhoneWindow mWindow;
    private boolean mWindowResizeCallbacksAdded;
    public static final com.android.internal.policy.DecorView.ColorViewAttributes STATUS_BAR_COLOR_VIEW_ATTRIBUTES = new com.android.internal.policy.DecorView.ColorViewAttributes(67108864, 48, 3, 5, android.view.Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME, 16908335, android.view.WindowInsets.Type.statusBars());
    public static final com.android.internal.policy.DecorView.ColorViewAttributes NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES = new com.android.internal.policy.DecorView.ColorViewAttributes(134217728, 80, 5, 3, android.view.Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME, 16908336, android.view.WindowInsets.Type.navigationBars());
    private static final android.view.ViewOutlineProvider PIP_OUTLINE_PROVIDER = new android.view.ViewOutlineProvider() { // from class: com.android.internal.policy.DecorView.1
        @Override // android.view.ViewOutlineProvider
        public void getOutline(android.view.View view, android.graphics.Outline outline) {
            outline.setRect(0, 0, view.getWidth(), view.getHeight());
            outline.setAlpha(1.0f);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0() {
        updateBackgroundBlurCorners();
        return true;
    }

    DecorView(android.content.Context context, int i, com.android.internal.policy.PhoneWindow phoneWindow, android.view.WindowManager.LayoutParams layoutParams) {
        super(context);
        boolean z = false;
        this.mAllowUpdateElevation = false;
        this.mElevationAdjustedForStack = false;
        this.mDefaultOpacity = -1;
        this.mDrawingBounds = new android.graphics.Rect();
        this.mBackgroundPadding = new android.graphics.Rect();
        this.mFramePadding = new android.graphics.Rect();
        this.mFrameOffsets = new android.graphics.Rect();
        this.mHasCaption = false;
        this.mStatusColorViewState = new com.android.internal.policy.DecorView.ColorViewState(STATUS_BAR_COLOR_VIEW_ATTRIBUTES);
        this.mNavigationColorViewState = new com.android.internal.policy.DecorView.ColorViewState(NAVIGATION_BAR_COLOR_VIEW_ATTRIBUTES);
        this.mBackgroundFallback = new com.android.internal.widget.BackgroundFallback();
        this.mLastTopInset = 0;
        this.mLastBottomInset = 0;
        this.mLastRightInset = 0;
        this.mLastLeftInset = 0;
        this.mLastHasTopStableInset = false;
        this.mLastHasBottomStableInset = false;
        this.mLastHasRightStableInset = false;
        this.mLastHasLeftStableInset = false;
        this.mLastWindowFlags = 0;
        this.mLastForceConsumingTypes = 0;
        this.mLastSuppressScrimTypes = 0;
        this.mRootScrollY = 0;
        this.mWindowResizeCallbacksAdded = false;
        this.mLastBackgroundDrawableCb = null;
        this.mBackdropFrameRenderer = null;
        this.mLogTag = TAG;
        this.mFloatingInsets = new android.graphics.Rect();
        this.mApplyFloatingVerticalInsets = false;
        this.mApplyFloatingHorizontalInsets = false;
        this.mLegacyNavigationBarBackgroundPaint = new android.graphics.Paint();
        this.mBackgroundInsets = android.graphics.Insets.NONE;
        this.mLastBackgroundInsets = android.graphics.Insets.NONE;
        this.mPendingInsetsController = new android.view.PendingInsetsController();
        this.mOriginalBackgroundBlurRadius = 0;
        this.mBackgroundBlurRadius = 0;
        this.mBackgroundBlurOnPreDrawListener = new android.view.ViewTreeObserver.OnPreDrawListener() { // from class: com.android.internal.policy.DecorView$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                boolean lambda$new$0;
                lambda$new$0 = com.android.internal.policy.DecorView.this.lambda$new$0();
                return lambda$new$0;
            }
        };
        this.mFeatureId = i;
        this.mShowInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context, 17563662);
        this.mHideInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context, 17563663);
        this.mBarEnterExitDuration = context.getResources().getInteger(com.android.internal.R.integer.dock_enter_exit_duration);
        if (context.getResources().getBoolean(com.android.internal.R.bool.config_forceWindowDrawsStatusBarBackground) && layoutParams.type != 2011 && context.getApplicationInfo().targetSdkVersion >= 24) {
            z = true;
        }
        this.mForceWindowDrawsBarBackgrounds = z;
        this.mSemiTransparentBarColor = context.getResources().getColor(com.android.internal.R.color.system_bar_background_semi_transparent, null);
        setWindow(phoneWindow);
        updateLogTag(layoutParams);
        this.mLegacyNavigationBarBackgroundPaint.setColor(-16777216);
        this.mWearGestureInterceptionDetector = com.android.internal.policy.WearGestureInterceptionDetector.isEnabled(context) ? new com.android.internal.policy.WearGestureInterceptionDetector(context, this) : null;
    }

    void setBackgroundFallback(android.graphics.drawable.Drawable drawable) {
        this.mBackgroundFallback.setDrawable(drawable);
        setWillNotDraw(getBackground() == null && !this.mBackgroundFallback.hasFallback());
    }

    public android.graphics.drawable.Drawable getBackgroundFallback() {
        return this.mBackgroundFallback.getDrawable();
    }

    android.view.View getStatusBarBackgroundView() {
        return this.mStatusColorViewState.view;
    }

    android.view.View getNavigationBarBackgroundView() {
        return this.mNavigationColorViewState.view;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean gatherTransparentRegion(android.graphics.Region region) {
        return gatherTransparentRegion(this.mStatusColorViewState, region) || gatherTransparentRegion(this.mNavigationColorViewState, region) || super.gatherTransparentRegion(region);
    }

    boolean gatherTransparentRegion(com.android.internal.policy.DecorView.ColorViewState colorViewState, android.graphics.Region region) {
        if (colorViewState.view != null && colorViewState.visible && isResizing()) {
            return colorViewState.view.gatherTransparentRegion(region);
        }
        return false;
    }

    @Override // android.view.View
    public void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        this.mBackgroundFallback.draw(this, this.mContentRoot, canvas, this.mWindow.mContentParent, this.mStatusColorViewState.view, this.mNavigationColorViewState.view);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        boolean z = keyEvent.getAction() == 0;
        if (z && keyEvent.getRepeatCount() == 0) {
            if (this.mWindow.mPanelChordingKey > 0 && this.mWindow.mPanelChordingKey != keyCode && dispatchKeyShortcutEvent(keyEvent)) {
                return true;
            }
            if (this.mWindow.mPreparedPanel != null && this.mWindow.mPreparedPanel.isOpen && this.mWindow.performPanelShortcut(this.mWindow.mPreparedPanel, keyCode, keyEvent, 0)) {
                return true;
            }
        }
        if (!this.mWindow.isDestroyed()) {
            android.view.Window.Callback callback = this.mWindow.getCallback();
            if ((callback == null || this.mFeatureId >= 0) ? super.dispatchKeyEvent(keyEvent) : callback.dispatchKeyEvent(keyEvent)) {
                return true;
            }
        }
        return z ? this.mWindow.onKeyDown(this.mFeatureId, keyEvent.getKeyCode(), keyEvent) : this.mWindow.onKeyUp(this.mFeatureId, keyEvent.getKeyCode(), keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyShortcutEvent(android.view.KeyEvent keyEvent) {
        if (this.mWindow.mPreparedPanel != null && this.mWindow.performPanelShortcut(this.mWindow.mPreparedPanel, keyEvent.getKeyCode(), keyEvent, 1)) {
            if (this.mWindow.mPreparedPanel != null) {
                this.mWindow.mPreparedPanel.isHandled = true;
            }
            return true;
        }
        android.view.Window.Callback callback = this.mWindow.getCallback();
        if ((callback == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchKeyShortcutEvent(keyEvent) : callback.dispatchKeyShortcutEvent(keyEvent)) {
            return true;
        }
        com.android.internal.policy.PhoneWindow.PanelFeatureState panelState = this.mWindow.getPanelState(0, false);
        if (panelState != null && this.mWindow.mPreparedPanel == null) {
            this.mWindow.preparePanel(panelState, keyEvent);
            boolean performPanelShortcut = this.mWindow.performPanelShortcut(panelState, keyEvent.getKeyCode(), keyEvent, 1);
            panelState.isPrepared = false;
            if (performPanelShortcut) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
        android.view.Window.Callback callback = this.mWindow.getCallback();
        return (callback == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchTouchEvent(motionEvent) : callback.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTrackballEvent(android.view.MotionEvent motionEvent) {
        android.view.Window.Callback callback = this.mWindow.getCallback();
        return (callback == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchTrackballEvent(motionEvent) : callback.dispatchTrackballEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean dispatchGenericMotionEvent(android.view.MotionEvent motionEvent) {
        android.view.Window.Callback callback = this.mWindow.getCallback();
        return (callback == null || this.mWindow.isDestroyed() || this.mFeatureId >= 0) ? super.dispatchGenericMotionEvent(motionEvent) : callback.dispatchGenericMotionEvent(motionEvent);
    }

    public boolean superDispatchKeyEvent(android.view.KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4) {
            int action = keyEvent.getAction();
            if (this.mPrimaryActionMode != null) {
                if (action == 1) {
                    this.mPrimaryActionMode.finish();
                }
                return true;
            }
        }
        if (super.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        return getViewRootImpl() != null && getViewRootImpl().dispatchUnhandledKeyEvent(keyEvent);
    }

    public boolean superDispatchKeyShortcutEvent(android.view.KeyEvent keyEvent) {
        return super.dispatchKeyShortcutEvent(keyEvent);
    }

    public boolean superDispatchTouchEvent(android.view.MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean superDispatchTrackballEvent(android.view.MotionEvent motionEvent) {
        return super.dispatchTrackballEvent(motionEvent);
    }

    public boolean superDispatchGenericMotionEvent(android.view.MotionEvent motionEvent) {
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        return onInterceptTouchEvent(motionEvent);
    }

    private boolean isOutOfInnerBounds(int i, int i2) {
        return i < 0 || i2 < 0 || i > getWidth() || i2 > getHeight();
    }

    private boolean isOutOfBounds(int i, int i2) {
        return i < -5 || i2 < -5 || i > getWidth() + 5 || i2 > getHeight() + 5;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (this.mHasCaption && isShowingCaption() && action == 0 && isOutOfInnerBounds((int) motionEvent.getX(), (int) motionEvent.getY())) {
            return true;
        }
        if (this.mFeatureId >= 0 && action == 0 && isOutOfBounds((int) motionEvent.getX(), (int) motionEvent.getY())) {
            this.mWindow.closePanel(this.mFeatureId);
            return true;
        }
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null && this.mWearGestureInterceptionDetector != null) {
            boolean isIntercepting = this.mWearGestureInterceptionDetector.isIntercepting();
            boolean onInterceptTouchEvent = this.mWearGestureInterceptionDetector.onInterceptTouchEvent(motionEvent);
            if (isIntercepting != onInterceptTouchEvent) {
                viewRootImpl.updateDecorViewGestureInterception(onInterceptTouchEvent);
            }
            return onInterceptTouchEvent;
        }
        return false;
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEvent(int i) {
        if (!android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            return;
        }
        if ((this.mFeatureId == 0 || this.mFeatureId == 6 || this.mFeatureId == 2 || this.mFeatureId == 5) && getChildCount() == 1) {
            getChildAt(0).sendAccessibilityEvent(i);
        } else {
            super.sendAccessibilityEvent(i);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchPopulateAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        android.view.Window.Callback callback = this.mWindow.getCallback();
        if (callback != null && !this.mWindow.isDestroyed() && callback.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
            return true;
        }
        return super.dispatchPopulateAccessibilityEventInternal(accessibilityEvent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        if (frame) {
            android.graphics.Rect rect = this.mDrawingBounds;
            getDrawingRect(rect);
            android.graphics.drawable.Drawable foreground = getForeground();
            if (foreground != null) {
                android.graphics.Rect rect2 = this.mFrameOffsets;
                rect.left += rect2.left;
                rect.top += rect2.top;
                rect.right -= rect2.right;
                rect.bottom -= rect2.bottom;
                foreground.setBounds(rect);
                android.graphics.Rect rect3 = this.mFramePadding;
                rect.left += rect3.left - rect2.left;
                rect.top += rect3.top - rect2.top;
                rect.right -= rect3.right - rect2.right;
                rect.bottom -= rect3.bottom - rect2.bottom;
            }
            android.graphics.drawable.Drawable background = super.getBackground();
            if (background != null) {
                background.setBounds(rect);
            }
        }
        return frame;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00f0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    @Override // android.widget.FrameLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onMeasure(int i, int i2) {
        int i3;
        boolean z;
        int i4;
        android.util.TypedValue typedValue;
        int i5;
        android.util.TypedValue typedValue2;
        int i6;
        int i7;
        android.util.DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        boolean z2 = false;
        boolean z3 = getResources().getConfiguration().orientation == 1;
        int mode = android.view.View.MeasureSpec.getMode(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        this.mApplyFloatingHorizontalInsets = false;
        if (mode == Integer.MIN_VALUE) {
            com.android.internal.policy.PhoneWindow phoneWindow = this.mWindow;
            android.util.TypedValue typedValue3 = z3 ? phoneWindow.mFixedWidthMinor : phoneWindow.mFixedWidthMajor;
            if (typedValue3 != null && typedValue3.type != 0) {
                if (typedValue3.type != 5) {
                    if (typedValue3.type == 6) {
                        i7 = (int) typedValue3.getFraction(displayMetrics.widthPixels, displayMetrics.widthPixels);
                    } else {
                        i7 = 0;
                    }
                } else {
                    i7 = (int) typedValue3.getDimension(displayMetrics);
                }
                int size = android.view.View.MeasureSpec.getSize(i);
                if (i7 <= 0) {
                    i3 = android.view.View.MeasureSpec.makeMeasureSpec((size - this.mFloatingInsets.left) - this.mFloatingInsets.right, Integer.MIN_VALUE);
                    this.mApplyFloatingHorizontalInsets = true;
                    z = false;
                } else {
                    i3 = android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.min(i7, size), 1073741824);
                    z = true;
                }
                this.mApplyFloatingVerticalInsets = false;
                if (mode2 == Integer.MIN_VALUE) {
                    if (z3) {
                        typedValue2 = this.mWindow.mFixedHeightMajor;
                    } else {
                        typedValue2 = this.mWindow.mFixedHeightMinor;
                    }
                    if (typedValue2 != null && typedValue2.type != 0) {
                        if (typedValue2.type != 5) {
                            if (typedValue2.type == 6) {
                                i6 = (int) typedValue2.getFraction(displayMetrics.heightPixels, displayMetrics.heightPixels);
                            } else {
                                i6 = 0;
                            }
                        } else {
                            i6 = (int) typedValue2.getDimension(displayMetrics);
                        }
                        int size2 = android.view.View.MeasureSpec.getSize(i2);
                        if (i6 > 0) {
                            i4 = android.view.View.MeasureSpec.makeMeasureSpec(java.lang.Math.min(i6, size2), 1073741824);
                        } else if ((this.mWindow.getAttributes().flags & 256) == 0) {
                            i4 = android.view.View.MeasureSpec.makeMeasureSpec((size2 - this.mFloatingInsets.top) - this.mFloatingInsets.bottom, Integer.MIN_VALUE);
                            this.mApplyFloatingVerticalInsets = true;
                        }
                        super.onMeasure(i3, i4);
                        int measuredWidth = getMeasuredWidth();
                        int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824);
                        if (!z && mode == Integer.MIN_VALUE) {
                            typedValue = !z3 ? this.mWindow.mMinWidthMinor : this.mWindow.mMinWidthMajor;
                            float applyDimension = android.util.TypedValue.applyDimension(1, r1.getConfiguration().screenWidthDp, displayMetrics);
                            if (typedValue.type != 0) {
                                if (typedValue.type != 5) {
                                    if (typedValue.type == 6) {
                                        i5 = (int) typedValue.getFraction(applyDimension, applyDimension);
                                    } else {
                                        i5 = 0;
                                    }
                                } else {
                                    i5 = (int) typedValue.getDimension(displayMetrics);
                                }
                                if (measuredWidth < i5) {
                                    makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
                                    z2 = true;
                                }
                            }
                        }
                        if (z2) {
                            super.onMeasure(makeMeasureSpec, i4);
                            return;
                        }
                        return;
                    }
                }
                i4 = i2;
                super.onMeasure(i3, i4);
                int measuredWidth2 = getMeasuredWidth();
                int makeMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec(measuredWidth2, 1073741824);
                if (!z) {
                    if (!z3) {
                    }
                    float applyDimension2 = android.util.TypedValue.applyDimension(1, r1.getConfiguration().screenWidthDp, displayMetrics);
                    if (typedValue.type != 0) {
                    }
                }
                if (z2) {
                }
            }
        }
        i3 = i;
        z = false;
        this.mApplyFloatingVerticalInsets = false;
        if (mode2 == Integer.MIN_VALUE) {
        }
        i4 = i2;
        super.onMeasure(i3, i4);
        int measuredWidth22 = getMeasuredWidth();
        int makeMeasureSpec22 = android.view.View.MeasureSpec.makeMeasureSpec(measuredWidth22, 1073741824);
        if (!z) {
        }
        if (z2) {
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mApplyFloatingVerticalInsets) {
            offsetTopAndBottom(this.mFloatingInsets.top);
        }
        if (this.mApplyFloatingHorizontalInsets) {
            offsetLeftAndRight(this.mFloatingInsets.left);
        }
        updateElevation();
        this.mAllowUpdateElevation = true;
        if (z && this.mDrawLegacyNavigationBarBackground) {
            getViewRootImpl().requestInvalidateRootRenderNode();
        }
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
        super.draw(canvas);
        if (this.mMenuBackground != null) {
            this.mMenuBackground.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean showContextMenuForChild(android.view.View view) {
        return showContextMenuForChildInternal(view, Float.NaN, Float.NaN);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean showContextMenuForChild(android.view.View view, float f, float f2) {
        return showContextMenuForChildInternal(view, f, f2);
    }

    private boolean showContextMenuForChildInternal(android.view.View view, float f, float f2) {
        com.android.internal.view.menu.MenuHelper showDialog;
        if (this.mWindow.mContextMenuHelper != null) {
            this.mWindow.mContextMenuHelper.dismiss();
            this.mWindow.mContextMenuHelper = null;
        }
        com.android.internal.policy.PhoneWindow.PhoneWindowMenuCallback phoneWindowMenuCallback = this.mWindow.mContextMenuCallback;
        if (this.mWindow.mContextMenu == null) {
            this.mWindow.mContextMenu = new com.android.internal.view.menu.ContextMenuBuilder(getContext());
            this.mWindow.mContextMenu.setCallback(phoneWindowMenuCallback);
        } else {
            this.mWindow.mContextMenu.clearAll();
        }
        boolean z = (java.lang.Float.isNaN(f) || java.lang.Float.isNaN(f2)) ? false : true;
        if (z) {
            showDialog = this.mWindow.mContextMenu.showPopup(getContext(), view, f, f2);
        } else {
            showDialog = this.mWindow.mContextMenu.showDialog(view, view.getWindowToken());
        }
        if (showDialog != null) {
            phoneWindowMenuCallback.setShowDialogForSubmenu(!z);
            showDialog.setPresenterCallback(phoneWindowMenuCallback);
        }
        this.mWindow.mContextMenuHelper = showDialog;
        return showDialog != null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public android.view.ActionMode startActionModeForChild(android.view.View view, android.view.ActionMode.Callback callback) {
        return startActionModeForChild(view, callback, 0);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public android.view.ActionMode startActionModeForChild(android.view.View view, android.view.ActionMode.Callback callback, int i) {
        return startActionMode(view, callback, i);
    }

    @Override // android.view.View
    public android.view.ActionMode startActionMode(android.view.ActionMode.Callback callback) {
        return startActionMode(callback, 0);
    }

    @Override // android.view.View
    public android.view.ActionMode startActionMode(android.view.ActionMode.Callback callback, int i) {
        return startActionMode(this, callback, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private android.view.ActionMode startActionMode(android.view.View view, android.view.ActionMode.Callback callback, int i) {
        android.view.ActionMode actionMode;
        com.android.internal.policy.DecorView.ActionModeCallback2Wrapper actionModeCallback2Wrapper = new com.android.internal.policy.DecorView.ActionModeCallback2Wrapper(callback);
        android.view.ActionMode actionMode2 = null;
        if (this.mWindow.getCallback() != null && !this.mWindow.isDestroyed()) {
            try {
                actionMode = this.mWindow.getCallback().onWindowStartingActionMode(actionModeCallback2Wrapper, i);
            } catch (java.lang.AbstractMethodError e) {
                if (i == 0) {
                    try {
                        actionMode = this.mWindow.getCallback().onWindowStartingActionMode(actionModeCallback2Wrapper);
                    } catch (java.lang.AbstractMethodError e2) {
                    }
                }
            }
            if (actionMode == null) {
                if (actionMode.getType() == 0) {
                    cleanupPrimaryActionMode();
                    this.mPrimaryActionMode = actionMode;
                } else if (actionMode.getType() == 1) {
                    if (this.mFloatingActionMode != null) {
                        this.mFloatingActionMode.finish();
                    }
                    this.mFloatingActionMode = actionMode;
                }
                actionMode2 = actionMode;
            } else {
                android.view.ActionMode createActionMode = createActionMode(i, actionModeCallback2Wrapper, view);
                if (createActionMode != null && actionModeCallback2Wrapper.onCreateActionMode(createActionMode, createActionMode.getMenu())) {
                    setHandledActionMode(createActionMode);
                    actionMode2 = createActionMode;
                }
            }
            if (actionMode2 != null && this.mWindow.getCallback() != null && !this.mWindow.isDestroyed()) {
                try {
                    this.mWindow.getCallback().onActionModeStarted(actionMode2);
                } catch (java.lang.AbstractMethodError e3) {
                }
            }
            return actionMode2;
        }
        actionMode = null;
        if (actionMode == null) {
        }
        if (actionMode2 != null) {
            this.mWindow.getCallback().onActionModeStarted(actionMode2);
        }
        return actionMode2;
    }

    private void cleanupPrimaryActionMode() {
        if (this.mPrimaryActionMode != null) {
            this.mPrimaryActionMode.finish();
            this.mPrimaryActionMode = null;
        }
        if (this.mPrimaryActionModeView != null) {
            this.mPrimaryActionModeView.killMode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanupFloatingActionModeViews() {
        if (this.mFloatingToolbar != null) {
            this.mFloatingToolbar.dismiss();
            this.mFloatingToolbar = null;
        }
        if (this.mFloatingActionModeOriginatingView != null) {
            if (this.mFloatingToolbarPreDrawListener != null) {
                this.mFloatingActionModeOriginatingView.getViewTreeObserver().removeOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
                this.mFloatingToolbarPreDrawListener = null;
            }
            this.mFloatingActionModeOriginatingView = null;
        }
    }

    void startChanging() {
        this.mChanging = true;
    }

    void finishChanging() {
        this.mChanging = false;
        drawableChanged();
    }

    public void setWindowBackground(android.graphics.drawable.Drawable drawable) {
        if (this.mWindow == null) {
            this.mPendingWindowBackground = drawable;
            return;
        }
        if (this.mOriginalBackgroundDrawable != drawable) {
            this.mOriginalBackgroundDrawable = drawable;
            updateBackgroundDrawable();
            if (drawable != null) {
                this.mResizingBackgroundDrawable = enforceNonTranslucentBackground(drawable, this.mWindow.isTranslucent() || this.mWindow.isShowingWallpaper());
            } else {
                this.mResizingBackgroundDrawable = getResizingBackgroundDrawable(this.mWindow.mBackgroundDrawable, this.mWindow.mBackgroundFallbackDrawable, this.mWindow.isTranslucent() || this.mWindow.isShowingWallpaper());
            }
            if (this.mResizingBackgroundDrawable != null) {
                this.mResizingBackgroundDrawable.getPadding(this.mBackgroundPadding);
            } else {
                this.mBackgroundPadding.setEmpty();
            }
            if (!android.view.View.sBrokenWindowBackground) {
                drawableChanged();
            }
        }
    }

    @Override // android.view.View
    public void setBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        setWindowBackground(drawable);
    }

    public void setWindowFrame(android.graphics.drawable.Drawable drawable) {
        if (getForeground() != drawable) {
            setForeground(drawable);
            if (drawable != null) {
                drawable.getPadding(this.mFramePadding);
            } else {
                this.mFramePadding.setEmpty();
            }
            drawableChanged();
        }
    }

    @Override // android.view.View
    public void onWindowSystemUiVisibilityChanged(int i) {
        updateColorViews(null, true);
        updateDecorCaptionStatus(getResources().getConfiguration());
        if (this.mStatusGuard != null && this.mStatusGuard.getVisibility() == 0) {
            updateStatusGuardColor();
        }
    }

    @Override // android.view.View
    public void onSystemBarAppearanceChanged(int i) {
        updateColorViews(null, true);
        if (this.mWindow != null) {
            this.mWindow.dispatchOnSystemBarAppearanceChanged(i);
        }
    }

    @Override // android.view.View
    public android.view.WindowInsets onApplyWindowInsets(android.view.WindowInsets windowInsets) {
        android.view.WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        this.mFloatingInsets.setEmpty();
        if ((attributes.flags & 256) == 0) {
            if (attributes.height == -2) {
                this.mFloatingInsets.top = windowInsets.getSystemWindowInsetTop();
                this.mFloatingInsets.bottom = windowInsets.getSystemWindowInsetBottom();
                windowInsets = windowInsets.inset(0, windowInsets.getSystemWindowInsetTop(), 0, windowInsets.getSystemWindowInsetBottom());
            }
            if (this.mWindow.getAttributes().width == -2) {
                this.mFloatingInsets.left = windowInsets.getSystemWindowInsetTop();
                this.mFloatingInsets.right = windowInsets.getSystemWindowInsetBottom();
                windowInsets = windowInsets.inset(windowInsets.getSystemWindowInsetLeft(), 0, windowInsets.getSystemWindowInsetRight(), 0);
            }
        }
        this.mFrameOffsets.set(windowInsets.getSystemWindowInsetsAsRect());
        android.view.WindowInsets updateStatusGuard = updateStatusGuard(updateColorViews(windowInsets, true));
        if (getForeground() != null) {
            drawableChanged();
        }
        return updateStatusGuard;
    }

    @Override // android.view.ViewGroup
    public boolean isTransitionGroup() {
        return false;
    }

    public static boolean isNavBarToRightEdge(int i, int i2) {
        return i == 0 && i2 > 0;
    }

    public static boolean isNavBarToLeftEdge(int i, int i2) {
        return i == 0 && i2 > 0;
    }

    public static int getNavBarSize(int i, int i2, int i3) {
        return isNavBarToRightEdge(i, i2) ? i2 : isNavBarToLeftEdge(i, i3) ? i3 : i;
    }

    public static void getNavigationBarRect(int i, int i2, android.graphics.Rect rect, android.graphics.Rect rect2, float f) {
        int i3 = (int) (rect.bottom * f);
        int i4 = (int) (rect.left * f);
        int i5 = (int) (rect.right * f);
        int navBarSize = getNavBarSize(i3, i5, i4);
        if (isNavBarToRightEdge(i3, i5)) {
            rect2.set(i - navBarSize, 0, i, i2);
        } else if (isNavBarToLeftEdge(i3, i4)) {
            rect2.set(0, 0, navBarSize, i2);
        } else {
            rect2.set(0, i2 - navBarSize, i, i2);
        }
    }

    android.view.WindowInsets updateColorViews(android.view.WindowInsets windowInsets, boolean z) {
        int systemBarsAppearance;
        boolean z2;
        int i;
        android.graphics.Insets min;
        android.view.WindowInsets windowInsets2;
        android.view.WindowInsets windowInsets3;
        android.view.WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        int windowSystemUiVisibility = attributes.systemUiVisibility | getWindowSystemUiVisibility();
        android.view.WindowInsetsController windowInsetsController = getWindowInsetsController();
        int requestedVisibleTypes = windowInsetsController.getRequestedVisibleTypes();
        boolean z3 = true;
        boolean z4 = this.mWindow.getAttributes().type == 2011;
        if (!this.mWindow.mIsFloating || z4) {
            boolean z5 = (!isLaidOut()) | (((this.mLastWindowFlags ^ attributes.flags) & Integer.MIN_VALUE) != 0);
            this.mLastWindowFlags = attributes.flags;
            android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
            if (viewRootImpl != null) {
                systemBarsAppearance = viewRootImpl.mWindowAttributes.insetsFlags.appearance;
            } else {
                systemBarsAppearance = windowInsetsController.getSystemBarsAppearance();
            }
            if (windowInsets != null) {
                this.mLastForceConsumingTypes = windowInsets.getForceConsumingTypes();
                boolean clearsCompatInsets = android.view.InsetsState.clearsCompatInsets(attributes.type, attributes.flags, getResources().getConfiguration().windowConfiguration.getActivityType(), this.mLastForceConsumingTypes);
                int systemBars = android.view.WindowInsets.Type.systemBars() | android.view.WindowInsets.Type.displayCutout();
                android.graphics.Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(android.view.WindowInsets.Type.systemBars());
                if (clearsCompatInsets) {
                    min = android.graphics.Insets.NONE;
                } else {
                    min = android.graphics.Insets.min(windowInsets.getInsets(systemBars), insetsIgnoringVisibility);
                }
                this.mLastTopInset = min.top;
                this.mLastBottomInset = min.bottom;
                this.mLastRightInset = min.right;
                this.mLastLeftInset = min.left;
                boolean z6 = insetsIgnoringVisibility.top != 0;
                boolean z7 = z5 | (z6 != this.mLastHasTopStableInset);
                this.mLastHasTopStableInset = z6;
                boolean z8 = insetsIgnoringVisibility.bottom != 0;
                boolean z9 = z7 | (z8 != this.mLastHasBottomStableInset);
                this.mLastHasBottomStableInset = z8;
                boolean z10 = insetsIgnoringVisibility.right != 0;
                boolean z11 = z9 | (z10 != this.mLastHasRightStableInset);
                this.mLastHasRightStableInset = z10;
                boolean z12 = insetsIgnoringVisibility.left != 0;
                boolean z13 = z12 != this.mLastHasLeftStableInset;
                this.mLastHasLeftStableInset = z12;
                this.mLastSuppressScrimTypes = windowInsets.getSuppressScrimTypes();
                z2 = z11 | z13;
            } else {
                z2 = z5;
            }
            boolean isNavBarToRightEdge = isNavBarToRightEdge(this.mLastBottomInset, this.mLastRightInset);
            boolean isNavBarToLeftEdge = isNavBarToLeftEdge(this.mLastBottomInset, this.mLastLeftInset);
            int i2 = systemBarsAppearance;
            updateColorViewInt(this.mNavigationColorViewState, calculateNavigationBarColor(systemBarsAppearance), this.mWindow.mNavigationBarDividerColor, getNavBarSize(this.mLastBottomInset, this.mLastRightInset, this.mLastLeftInset), isNavBarToRightEdge || isNavBarToLeftEdge, isNavBarToLeftEdge, 0, z && !z2, this.mForceWindowDrawsBarBackgrounds, requestedVisibleTypes);
            boolean z14 = this.mDrawLegacyNavigationBarBackground;
            this.mDrawLegacyNavigationBarBackground = ((this.mLastForceConsumingTypes | requestedVisibleTypes) & android.view.WindowInsets.Type.navigationBars()) != 0 && (this.mWindow.getAttributes().flags & Integer.MIN_VALUE) == 0;
            if (z14 != this.mDrawLegacyNavigationBarBackground) {
                this.mDrawLegacyNavigationBarBackgroundHandled = this.mWindow.onDrawLegacyNavigationBarBackgroundChanged(this.mDrawLegacyNavigationBarBackground);
                if (viewRootImpl != null) {
                    viewRootImpl.requestInvalidateRootRenderNode();
                }
            }
            boolean z15 = isNavBarToRightEdge && this.mNavigationColorViewState.present;
            boolean z16 = isNavBarToLeftEdge && this.mNavigationColorViewState.present;
            if (z15) {
                i = this.mLastRightInset;
            } else {
                i = z16 ? this.mLastLeftInset : 0;
            }
            int calculateStatusBarColor = calculateStatusBarColor(i2);
            updateColorViewInt(this.mStatusColorViewState, calculateStatusBarColor, 0, this.mLastTopInset, false, z16, i, z && !z2, this.mForceWindowDrawsBarBackgrounds, requestedVisibleTypes);
            if (this.mHasCaption) {
                this.mDecorCaptionView.getCaption().setBackgroundColor(calculateStatusBarColor);
                updateDecorCaptionShade();
            }
        }
        boolean z17 = (windowSystemUiVisibility & 2) != 0 || (android.view.WindowInsets.Type.navigationBars() & requestedVisibleTypes) == 0;
        boolean z18 = this.mWindow.mDecorFitsSystemWindows;
        boolean z19 = ((this.mForceWindowDrawsBarBackgrounds || this.mDrawLegacyNavigationBarBackgroundHandled) && (attributes.flags & Integer.MIN_VALUE) == 0 && (windowSystemUiVisibility & 512) == 0 && z18 && !z17) || ((this.mLastForceConsumingTypes & android.view.WindowInsets.Type.navigationBars()) != 0 && z17);
        boolean z20 = ((attributes.flags & Integer.MIN_VALUE) != 0 && (windowSystemUiVisibility & 512) == 0 && z18 && !z17) || z19;
        boolean z21 = ((windowSystemUiVisibility & 4) == 0 && (attributes.flags & 1024) == 0 && (android.view.WindowInsets.Type.statusBars() & requestedVisibleTypes) != 0) ? false : true;
        if (((windowSystemUiVisibility & 1024) != 0 || !z18 || (attributes.flags & 256) != 0 || (attributes.flags & 65536) != 0 || !this.mForceWindowDrawsBarBackgrounds || this.mLastTopInset == 0) && ((this.mLastForceConsumingTypes & android.view.WindowInsets.Type.statusBars()) == 0 || !z21)) {
            z3 = false;
        }
        int i3 = z3 ? this.mLastTopInset : 0;
        int i4 = z20 ? this.mLastRightInset : 0;
        int i5 = z20 ? this.mLastBottomInset : 0;
        int i6 = z20 ? this.mLastLeftInset : 0;
        if (this.mContentRoot == null) {
            windowInsets2 = windowInsets;
        } else if (!(this.mContentRoot.getLayoutParams() instanceof android.view.ViewGroup.MarginLayoutParams)) {
            windowInsets2 = windowInsets;
        } else {
            android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) this.mContentRoot.getLayoutParams();
            if (marginLayoutParams.topMargin == i3 && marginLayoutParams.rightMargin == i4 && marginLayoutParams.bottomMargin == i5 && marginLayoutParams.leftMargin == i6) {
                windowInsets2 = windowInsets;
            } else {
                marginLayoutParams.topMargin = i3;
                marginLayoutParams.rightMargin = i4;
                marginLayoutParams.bottomMargin = i5;
                marginLayoutParams.leftMargin = i6;
                this.mContentRoot.setLayoutParams(marginLayoutParams);
                windowInsets2 = windowInsets;
                if (windowInsets2 == null) {
                    requestApplyInsets();
                }
            }
            if (windowInsets2 != null) {
                windowInsets3 = windowInsets2.inset(i6, i3, i4, i5);
                if (z19 || z17 || this.mDrawLegacyNavigationBarBackgroundHandled) {
                    this.mBackgroundInsets = android.graphics.Insets.NONE;
                } else {
                    this.mBackgroundInsets = android.graphics.Insets.of(this.mLastLeftInset, 0, this.mLastRightInset, this.mLastBottomInset);
                }
                updateBackgroundDrawable();
                return windowInsets3;
            }
        }
        windowInsets3 = windowInsets2;
        if (z19) {
        }
        this.mBackgroundInsets = android.graphics.Insets.NONE;
        updateBackgroundDrawable();
        return windowInsets3;
    }

    private void updateBackgroundDrawable() {
        android.graphics.drawable.Drawable drawable;
        if (this.mBackgroundInsets == null) {
            this.mBackgroundInsets = android.graphics.Insets.NONE;
        }
        if (this.mBackgroundInsets.equals(this.mLastBackgroundInsets) && this.mBackgroundBlurDrawable == this.mLastBackgroundBlurDrawable && this.mLastOriginalBackgroundDrawable == this.mOriginalBackgroundDrawable) {
            return;
        }
        android.graphics.drawable.Drawable drawable2 = this.mOriginalBackgroundDrawable;
        if (this.mBackgroundBlurDrawable == null) {
            drawable = drawable2;
        } else {
            drawable = new android.graphics.drawable.LayerDrawable(new android.graphics.drawable.Drawable[]{this.mBackgroundBlurDrawable, this.mOriginalBackgroundDrawable});
        }
        if (drawable != null && !this.mBackgroundInsets.equals(android.graphics.Insets.NONE)) {
            drawable = new android.graphics.drawable.InsetDrawable(drawable, this.mBackgroundInsets.left, this.mBackgroundInsets.top, this.mBackgroundInsets.right, this.mBackgroundInsets.bottom) { // from class: com.android.internal.policy.DecorView.2
                @Override // android.graphics.drawable.InsetDrawable, android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
                public boolean getPadding(android.graphics.Rect rect) {
                    return getDrawable().getPadding(rect);
                }
            };
        }
        super.setBackgroundDrawable(drawable);
        this.mLastBackgroundInsets = this.mBackgroundInsets;
        this.mLastBackgroundBlurDrawable = this.mBackgroundBlurDrawable;
        this.mLastOriginalBackgroundDrawable = this.mOriginalBackgroundDrawable;
    }

    private void updateBackgroundBlurCorners() {
        if (this.mBackgroundBlurDrawable == null) {
            return;
        }
        float f = 0.0f;
        if (this.mBackgroundBlurRadius != 0 && this.mOriginalBackgroundDrawable != null) {
            android.graphics.Outline outline = new android.graphics.Outline();
            this.mOriginalBackgroundDrawable.getOutline(outline);
            if (outline.mMode == 1) {
                f = outline.getRadius();
            }
        }
        this.mBackgroundBlurDrawable.setCornerRadius(f);
    }

    private void updateBackgroundBlurRadius() {
        if (getViewRootImpl() == null) {
            return;
        }
        this.mBackgroundBlurRadius = (this.mCrossWindowBlurEnabled && this.mWindow.isTranslucent()) ? this.mOriginalBackgroundBlurRadius : 0;
        if (this.mBackgroundBlurDrawable == null && this.mBackgroundBlurRadius > 0) {
            this.mBackgroundBlurDrawable = getViewRootImpl().createBackgroundBlurDrawable();
            updateBackgroundDrawable();
        }
        if (this.mBackgroundBlurDrawable != null) {
            this.mBackgroundBlurDrawable.setBlurRadius(this.mBackgroundBlurRadius);
        }
    }

    void setBackgroundBlurRadius(int i) {
        this.mOriginalBackgroundBlurRadius = i;
        if (i > 0) {
            if (this.mCrossWindowBlurEnabledListener == null) {
                this.mCrossWindowBlurEnabledListener = new java.util.function.Consumer() { // from class: com.android.internal.policy.DecorView$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.internal.policy.DecorView.this.lambda$setBackgroundBlurRadius$1((java.lang.Boolean) obj);
                    }
                };
                ((android.view.WindowManager) getContext().getSystemService(android.view.WindowManager.class)).addCrossWindowBlurEnabledListener(this.mCrossWindowBlurEnabledListener);
                getViewTreeObserver().addOnPreDrawListener(this.mBackgroundBlurOnPreDrawListener);
                return;
            }
            updateBackgroundBlurRadius();
            return;
        }
        if (this.mCrossWindowBlurEnabledListener != null) {
            updateBackgroundBlurRadius();
            removeBackgroundBlurDrawable();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setBackgroundBlurRadius$1(java.lang.Boolean bool) {
        this.mCrossWindowBlurEnabled = bool.booleanValue();
        updateBackgroundBlurRadius();
    }

    void removeBackgroundBlurDrawable() {
        if (this.mCrossWindowBlurEnabledListener != null) {
            ((android.view.WindowManager) getContext().getSystemService(android.view.WindowManager.class)).removeCrossWindowBlurEnabledListener(this.mCrossWindowBlurEnabledListener);
            this.mCrossWindowBlurEnabledListener = null;
        }
        getViewTreeObserver().removeOnPreDrawListener(this.mBackgroundBlurOnPreDrawListener);
        this.mBackgroundBlurDrawable = null;
        updateBackgroundDrawable();
    }

    @Override // android.view.View
    public android.graphics.drawable.Drawable getBackground() {
        return this.mOriginalBackgroundDrawable;
    }

    private int calculateStatusBarColor(int i) {
        return calculateBarColor(this.mWindow.getAttributes().flags, 67108864, this.mSemiTransparentBarColor, this.mWindow.mStatusBarColor, i, 8, this.mWindow.mEnsureStatusBarContrastWhenTransparent && (this.mLastSuppressScrimTypes & android.view.WindowInsets.Type.statusBars()) == 0);
    }

    private int calculateNavigationBarColor(int i) {
        return calculateBarColor(this.mWindow.getAttributes().flags, 134217728, this.mSemiTransparentBarColor, this.mWindow.mNavigationBarColor, i, 16, this.mWindow.mEnsureNavigationBarContrastWhenTransparent && (this.mLastSuppressScrimTypes & android.view.WindowInsets.Type.navigationBars()) == 0);
    }

    public static int calculateBarColor(int i, int i2, int i3, int i4, int i5, int i6, boolean z) {
        if ((i2 & i) != 0) {
            return i3;
        }
        if ((i & Integer.MIN_VALUE) == 0) {
            return -16777216;
        }
        if (z && android.graphics.Color.alpha(i4) == 0) {
            return (i5 & i6) != 0 ? SCRIM_LIGHT : i3;
        }
        return i4;
    }

    private int getCurrentColor(com.android.internal.policy.DecorView.ColorViewState colorViewState) {
        if (colorViewState.visible) {
            return colorViewState.color;
        }
        return 0;
    }

    private void updateColorViewInt(final com.android.internal.policy.DecorView.ColorViewState colorViewState, int i, int i2, int i3, boolean z, boolean z2, int i4, boolean z3, boolean z4, int i5) {
        int i6;
        boolean z5;
        int i7 = i4;
        int i8 = colorViewState.attributes.insetsType;
        colorViewState.present = colorViewState.attributes.isPresent(((i5 & i8) == 0 && (i8 & this.mLastForceConsumingTypes) == 0) ? false : true, this.mWindow.getAttributes().flags, z4);
        boolean isVisible = colorViewState.attributes.isVisible(colorViewState.present, i, this.mWindow.getAttributes().flags, z4);
        boolean z6 = isVisible && !isResizing() && !this.mHasCaption && i3 > 0;
        android.view.View view = colorViewState.view;
        int i9 = z ? -1 : i3;
        int i10 = z ? i3 : -1;
        if (z) {
            com.android.internal.policy.DecorView.ColorViewAttributes colorViewAttributes = colorViewState.attributes;
            i6 = z2 ? colorViewAttributes.seascapeGravity : colorViewAttributes.horizontalGravity;
        } else {
            i6 = colorViewState.attributes.verticalGravity;
        }
        if (view == null) {
            if (!z6) {
                z5 = false;
            } else {
                view = new android.view.View(this.mContext);
                colorViewState.view = view;
                setColor(view, i, i2, z, z2);
                view.setTransitionName(colorViewState.attributes.transitionName);
                view.setId(colorViewState.attributes.id);
                view.setVisibility(4);
                colorViewState.targetVisibility = 0;
                android.widget.FrameLayout.LayoutParams layoutParams = new android.widget.FrameLayout.LayoutParams(i10, i9, i6);
                if (z2) {
                    layoutParams.leftMargin = i7;
                } else {
                    layoutParams.rightMargin = i7;
                }
                addView(view, layoutParams);
                updateColorViewTranslations();
                z5 = true;
            }
        } else {
            int i11 = z6 ? 0 : 4;
            boolean z7 = colorViewState.targetVisibility != i11;
            colorViewState.targetVisibility = i11;
            android.widget.FrameLayout.LayoutParams layoutParams2 = (android.widget.FrameLayout.LayoutParams) view.getLayoutParams();
            int i12 = z2 ? 0 : i7;
            if (!z2) {
                i7 = 0;
            }
            boolean z8 = z7;
            if (layoutParams2.height != i9 || layoutParams2.width != i10 || layoutParams2.gravity != i6 || layoutParams2.rightMargin != i12 || layoutParams2.leftMargin != i7) {
                layoutParams2.height = i9;
                layoutParams2.width = i10;
                layoutParams2.gravity = i6;
                layoutParams2.rightMargin = i12;
                layoutParams2.leftMargin = i7;
                view.setLayoutParams(layoutParams2);
            }
            if (z6) {
                setColor(view, i, i2, z, z2);
            }
            z5 = z8;
        }
        if (z5) {
            view.animate().cancel();
            if (!z3 || isResizing()) {
                view.setAlpha(1.0f);
                view.setVisibility(z6 ? 0 : 4);
            } else if (z6) {
                if (view.getVisibility() != 0) {
                    view.setVisibility(0);
                    view.setAlpha(0.0f);
                }
                view.animate().alpha(1.0f).setInterpolator(this.mShowInterpolator).setDuration(this.mBarEnterExitDuration);
            } else {
                view.animate().alpha(0.0f).setInterpolator(this.mHideInterpolator).setDuration(this.mBarEnterExitDuration).withEndAction(new java.lang.Runnable() { // from class: com.android.internal.policy.DecorView.3
                    @Override // java.lang.Runnable
                    public void run() {
                        colorViewState.view.setAlpha(1.0f);
                        colorViewState.view.setVisibility(4);
                    }
                });
            }
        }
        colorViewState.visible = isVisible;
        colorViewState.color = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void setColor(android.view.View view, int i, int i2, boolean z, boolean z2) {
        if (i2 != 0) {
            android.util.Pair pair = (android.util.Pair) view.getTag();
            if (pair == null || ((java.lang.Boolean) pair.first).booleanValue() != z || ((java.lang.Boolean) pair.second).booleanValue() != z2) {
                int round = java.lang.Math.round(android.util.TypedValue.applyDimension(1, 1.0f, view.getContext().getResources().getDisplayMetrics()));
                view.setBackground(new android.graphics.drawable.LayerDrawable(new android.graphics.drawable.Drawable[]{new android.graphics.drawable.ColorDrawable(i2), new android.graphics.drawable.InsetDrawable((android.graphics.drawable.Drawable) new android.graphics.drawable.ColorDrawable(i), (!z || z2) ? 0 : round, !z ? round : 0, (z && z2) ? round : 0, 0)}));
                view.setTag(new android.util.Pair(java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z2)));
                return;
            } else {
                android.graphics.drawable.LayerDrawable layerDrawable = (android.graphics.drawable.LayerDrawable) view.getBackground();
                ((android.graphics.drawable.ColorDrawable) ((android.graphics.drawable.InsetDrawable) layerDrawable.getDrawable(1)).getDrawable()).setColor(i);
                ((android.graphics.drawable.ColorDrawable) layerDrawable.getDrawable(0)).setColor(i2);
                return;
            }
        }
        view.setTag(null);
        view.setBackgroundColor(i);
    }

    private void updateColorViewTranslations() {
        int i = this.mRootScrollY;
        if (this.mStatusColorViewState.view != null) {
            this.mStatusColorViewState.view.setTranslationY(i > 0 ? i : 0.0f);
        }
        if (this.mNavigationColorViewState.view != null) {
            this.mNavigationColorViewState.view.setTranslationY(i < 0 ? i : 0.0f);
        }
    }

    private android.view.WindowInsets updateStatusGuard(android.view.WindowInsets windowInsets) {
        boolean z;
        boolean z2;
        if (this.mPrimaryActionModeView != null && (this.mPrimaryActionModeView.getLayoutParams() instanceof android.view.ViewGroup.MarginLayoutParams)) {
            android.view.ViewGroup.MarginLayoutParams marginLayoutParams = (android.view.ViewGroup.MarginLayoutParams) this.mPrimaryActionModeView.getLayoutParams();
            if (this.mPrimaryActionModeView.isShown()) {
                if (this.mTempRect == null) {
                    this.mTempRect = new android.graphics.Rect();
                }
                android.view.WindowInsets computeSystemWindowInsets = this.mWindow.mContentParent.computeSystemWindowInsets(windowInsets, this.mTempRect);
                int systemWindowInsetTop = computeSystemWindowInsets.getSystemWindowInsetTop();
                int systemWindowInsetLeft = computeSystemWindowInsets.getSystemWindowInsetLeft();
                int systemWindowInsetRight = computeSystemWindowInsets.getSystemWindowInsetRight();
                android.view.WindowInsets rootWindowInsets = getRootWindowInsets();
                int systemWindowInsetLeft2 = rootWindowInsets.getSystemWindowInsetLeft();
                int systemWindowInsetRight2 = rootWindowInsets.getSystemWindowInsetRight();
                if (marginLayoutParams.topMargin == systemWindowInsetTop && marginLayoutParams.leftMargin == systemWindowInsetLeft && marginLayoutParams.rightMargin == systemWindowInsetRight) {
                    z2 = false;
                } else {
                    marginLayoutParams.topMargin = systemWindowInsetTop;
                    marginLayoutParams.leftMargin = systemWindowInsetLeft;
                    marginLayoutParams.rightMargin = systemWindowInsetRight;
                    z2 = true;
                }
                if (systemWindowInsetTop > 0 && this.mStatusGuard == null) {
                    this.mStatusGuard = new android.view.View(this.mContext);
                    this.mStatusGuard.setVisibility(8);
                    android.widget.FrameLayout.LayoutParams layoutParams = new android.widget.FrameLayout.LayoutParams(-1, marginLayoutParams.topMargin, 51);
                    layoutParams.leftMargin = systemWindowInsetLeft2;
                    layoutParams.rightMargin = systemWindowInsetRight2;
                    addView(this.mStatusGuard, indexOfChild(this.mStatusColorViewState.view), layoutParams);
                } else if (this.mStatusGuard != null) {
                    android.widget.FrameLayout.LayoutParams layoutParams2 = (android.widget.FrameLayout.LayoutParams) this.mStatusGuard.getLayoutParams();
                    if (layoutParams2.height != marginLayoutParams.topMargin || layoutParams2.leftMargin != systemWindowInsetLeft2 || layoutParams2.rightMargin != systemWindowInsetRight2) {
                        layoutParams2.height = marginLayoutParams.topMargin;
                        layoutParams2.leftMargin = systemWindowInsetLeft2;
                        layoutParams2.rightMargin = systemWindowInsetRight2;
                        this.mStatusGuard.setLayoutParams(layoutParams2);
                    }
                }
                z = this.mStatusGuard != null;
                if (z && this.mStatusGuard.getVisibility() != 0) {
                    updateStatusGuardColor();
                }
                if (((this.mWindow.getLocalFeaturesPrivate() & 1024) == 0) && z) {
                    windowInsets = windowInsets.inset(0, windowInsets.getSystemWindowInsetTop(), 0, 0);
                }
                r4 = z2;
            } else if (marginLayoutParams.topMargin == 0 && marginLayoutParams.leftMargin == 0 && marginLayoutParams.rightMargin == 0) {
                r4 = false;
                z = false;
            } else {
                marginLayoutParams.topMargin = 0;
                z = false;
            }
            if (r4) {
                this.mPrimaryActionModeView.setLayoutParams(marginLayoutParams);
            }
        } else {
            z = false;
        }
        if (this.mStatusGuard != null) {
            this.mStatusGuard.setVisibility(z ? 0 : 8);
        }
        return windowInsets;
    }

    private void updateStatusGuardColor() {
        int color;
        boolean z = (getWindowSystemUiVisibility() & 8192) != 0;
        android.view.View view = this.mStatusGuard;
        if (z) {
            color = this.mContext.getColor(com.android.internal.R.color.decor_view_status_guard_light);
        } else {
            color = this.mContext.getColor(com.android.internal.R.color.decor_view_status_guard);
        }
        view.setBackgroundColor(color);
    }

    public void updatePictureInPictureOutlineProvider(boolean z) {
        if (this.mIsInPictureInPictureMode == z) {
            return;
        }
        if (z) {
            android.view.Window.WindowControllerCallback windowControllerCallback = this.mWindow.getWindowControllerCallback();
            if (windowControllerCallback != null && windowControllerCallback.isTaskRoot()) {
                super.setOutlineProvider(PIP_OUTLINE_PROVIDER);
            }
        } else if (getOutlineProvider() != this.mLastOutlineProvider) {
            setOutlineProvider(this.mLastOutlineProvider);
        }
        this.mIsInPictureInPictureMode = z;
    }

    @Override // android.view.View
    public void setOutlineProvider(android.view.ViewOutlineProvider viewOutlineProvider) {
        super.setOutlineProvider(viewOutlineProvider);
        this.mLastOutlineProvider = viewOutlineProvider;
    }

    private void drawableChanged() {
        if (this.mChanging) {
            return;
        }
        android.graphics.Rect rect = this.mFramePadding != null ? this.mFramePadding : new android.graphics.Rect();
        android.graphics.Rect rect2 = this.mBackgroundPadding != null ? this.mBackgroundPadding : new android.graphics.Rect();
        setPadding(rect.left + rect2.left, rect.top + rect2.top, rect.right + rect2.right, rect.bottom + rect2.bottom);
        requestLayout();
        invalidate();
        android.app.WindowConfiguration windowConfiguration = getResources().getConfiguration().windowConfiguration;
        boolean z = this.mWindow.mRenderShadowsInCompositor;
        int i = -3;
        if (!windowConfiguration.hasWindowShadow() || z) {
            android.graphics.drawable.Drawable background = getBackground();
            android.graphics.drawable.Drawable foreground = getForeground();
            if (background == null) {
                i = -1;
            } else if (foreground == null) {
                i = background.getOpacity();
            } else if (rect.left <= 0 && rect.top <= 0 && rect.right <= 0 && rect.bottom <= 0) {
                int opacity = foreground.getOpacity();
                int opacity2 = background.getOpacity();
                if (opacity == -1 || opacity2 == -1) {
                    i = -1;
                } else if (opacity == 0) {
                    i = opacity2;
                } else if (opacity2 == 0) {
                    i = opacity;
                } else {
                    i = android.graphics.drawable.Drawable.resolveOpacity(opacity, opacity2);
                }
            }
        }
        this.mDefaultOpacity = i;
        if (this.mFeatureId < 0) {
            this.mWindow.setDefaultWindowFormat(i);
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (this.mWindow.hasFeature(0) && !z && this.mWindow.mPanelChordingKey != 0) {
            this.mWindow.closePanel(0);
        }
        android.view.Window.Callback callback = this.mWindow.getCallback();
        if (callback != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0) {
            callback.onWindowFocusChanged(z);
        }
        if (this.mPrimaryActionMode != null) {
            this.mPrimaryActionMode.onWindowFocusChanged(z);
        }
        if (this.mFloatingActionMode != null) {
            this.mFloatingActionMode.onWindowFocusChanged(z);
        }
        updateElevation();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        android.view.Window.Callback callback = this.mWindow.getCallback();
        if (callback != null && !this.mWindow.isDestroyed() && this.mFeatureId < 0) {
            callback.onAttachedToWindow();
        }
        if (this.mFeatureId == -1) {
            this.mWindow.openPanelsAfterRestore();
        }
        if (!this.mWindowResizeCallbacksAdded) {
            getViewRootImpl().addWindowCallbacks(this);
            this.mWindowResizeCallbacksAdded = true;
        } else if (this.mBackdropFrameRenderer != null) {
            this.mBackdropFrameRenderer.onConfigurationChange();
        }
        updateBackgroundBlurRadius();
        this.mWindow.onViewRootImplSet(getViewRootImpl());
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        android.view.Window.Callback callback = this.mWindow.getCallback();
        if (callback != null && this.mFeatureId < 0) {
            callback.onDetachedFromWindow();
        }
        if (this.mWindow.mDecorContentParent != null) {
            this.mWindow.mDecorContentParent.dismissPopups();
        }
        if (this.mPrimaryActionModePopup != null) {
            removeCallbacks(this.mShowPrimaryActionModePopup);
            if (this.mPrimaryActionModePopup.isShowing()) {
                this.mPrimaryActionModePopup.dismiss();
            }
            this.mPrimaryActionModePopup = null;
        }
        if (this.mFloatingToolbar != null) {
            this.mFloatingToolbar.dismiss();
            this.mFloatingToolbar = null;
        }
        removeBackgroundBlurDrawable();
        com.android.internal.policy.PhoneWindow.PanelFeatureState panelState = this.mWindow.getPanelState(0, false);
        if (panelState != null && panelState.menu != null && this.mFeatureId < 0) {
            panelState.menu.close();
        }
        releaseThreadedRenderer();
        if (this.mWindowResizeCallbacksAdded) {
            getViewRootImpl().removeWindowCallbacks(this);
            this.mWindowResizeCallbacksAdded = false;
        }
        this.mPendingInsetsController.detach();
    }

    @Override // android.view.View
    public void onCloseSystemDialogs(java.lang.String str) {
        if (this.mFeatureId >= 0) {
            this.mWindow.closeAllPanels();
        }
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public android.view.SurfaceHolder.Callback2 willYouTakeTheSurface() {
        if (this.mFeatureId < 0) {
            return this.mWindow.mTakeSurfaceCallback;
        }
        return null;
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public android.view.InputQueue.Callback willYouTakeTheInputQueue() {
        if (this.mFeatureId < 0) {
            return this.mWindow.mTakeInputQueueCallback;
        }
        return null;
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public void setSurfaceType(int i) {
        this.mWindow.setType(i);
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public void setSurfaceFormat(int i) {
        this.mWindow.setFormat(i);
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public void setSurfaceKeepScreenOn(boolean z) {
        if (z) {
            this.mWindow.addFlags(128);
        } else {
            this.mWindow.clearFlags(128);
        }
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public void onRootViewScrollYChanged(int i) {
        this.mRootScrollY = i;
        if (this.mDecorCaptionView != null) {
            this.mDecorCaptionView.onRootViewScrollYChanged(i);
        }
        updateColorViewTranslations();
    }

    @Override // com.android.internal.view.RootViewSurfaceTaker
    public android.view.PendingInsetsController providePendingInsetsController() {
        return this.mPendingInsetsController;
    }

    private android.view.ActionMode createActionMode(int i, android.view.ActionMode.Callback2 callback2, android.view.View view) {
        switch (i) {
            case 1:
                return createFloatingActionMode(view, callback2);
            default:
                return createStandaloneActionMode(callback2);
        }
    }

    private void setHandledActionMode(android.view.ActionMode actionMode) {
        if (actionMode.getType() == 0) {
            setHandledPrimaryActionMode(actionMode);
        } else if (actionMode.getType() == 1) {
            setHandledFloatingActionMode(actionMode);
        }
    }

    private android.view.ActionMode createStandaloneActionMode(android.view.ActionMode.Callback callback) {
        android.content.Context context;
        endOnGoingFadeAnimation();
        cleanupPrimaryActionMode();
        if (this.mPrimaryActionModeView == null || !this.mPrimaryActionModeView.isAttachedToWindow()) {
            if (this.mWindow.isFloating()) {
                android.util.TypedValue typedValue = new android.util.TypedValue();
                android.content.res.Resources.Theme theme = this.mContext.getTheme();
                theme.resolveAttribute(16843825, typedValue, true);
                if (typedValue.resourceId != 0) {
                    android.content.res.Resources.Theme newTheme = this.mContext.getResources().newTheme();
                    newTheme.setTo(theme);
                    newTheme.applyStyle(typedValue.resourceId, true);
                    context = new android.view.ContextThemeWrapper(this.mContext, 0);
                    context.getTheme().setTo(newTheme);
                } else {
                    context = this.mContext;
                }
                this.mPrimaryActionModeView = new com.android.internal.widget.ActionBarContextView(context);
                this.mPrimaryActionModePopup = new android.widget.PopupWindow(context, (android.util.AttributeSet) null, com.android.internal.R.attr.actionModePopupWindowStyle);
                this.mPrimaryActionModePopup.setWindowLayoutType(2);
                this.mPrimaryActionModePopup.setContentView(this.mPrimaryActionModeView);
                this.mPrimaryActionModePopup.setWidth(-1);
                context.getTheme().resolveAttribute(16843499, typedValue, true);
                this.mPrimaryActionModeView.setContentHeight(android.util.TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics()));
                this.mPrimaryActionModePopup.setHeight(-2);
                this.mShowPrimaryActionModePopup = new java.lang.Runnable() { // from class: com.android.internal.policy.DecorView.4
                    @Override // java.lang.Runnable
                    public void run() {
                        com.android.internal.policy.DecorView.this.mPrimaryActionModePopup.showAtLocation(com.android.internal.policy.DecorView.this.mPrimaryActionModeView.getApplicationWindowToken(), 55, 0, 0);
                        com.android.internal.policy.DecorView.this.endOnGoingFadeAnimation();
                        if (com.android.internal.policy.DecorView.this.shouldAnimatePrimaryActionModeView()) {
                            com.android.internal.policy.DecorView.this.mFadeAnim = android.animation.ObjectAnimator.ofFloat(com.android.internal.policy.DecorView.this.mPrimaryActionModeView, (android.util.Property<com.android.internal.widget.ActionBarContextView, java.lang.Float>) android.view.View.ALPHA, 0.0f, 1.0f);
                            com.android.internal.policy.DecorView.this.mFadeAnim.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.policy.DecorView.4.1
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationStart(android.animation.Animator animator) {
                                    com.android.internal.policy.DecorView.this.mPrimaryActionModeView.setVisibility(0);
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(android.animation.Animator animator) {
                                    com.android.internal.policy.DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                                    com.android.internal.policy.DecorView.this.mFadeAnim = null;
                                }
                            });
                            com.android.internal.policy.DecorView.this.mFadeAnim.start();
                            return;
                        }
                        com.android.internal.policy.DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                        com.android.internal.policy.DecorView.this.mPrimaryActionModeView.setVisibility(0);
                    }
                };
            } else {
                android.view.ViewStub viewStub = (android.view.ViewStub) findViewById(com.android.internal.R.id.action_mode_bar_stub);
                if (viewStub != null) {
                    this.mPrimaryActionModeView = (com.android.internal.widget.ActionBarContextView) viewStub.inflate();
                    this.mPrimaryActionModePopup = null;
                }
            }
        }
        if (this.mPrimaryActionModeView == null) {
            return null;
        }
        this.mPrimaryActionModeView.killMode();
        return new com.android.internal.view.StandaloneActionMode(this.mPrimaryActionModeView.getContext(), this.mPrimaryActionModeView, callback, this.mPrimaryActionModePopup == null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endOnGoingFadeAnimation() {
        if (this.mFadeAnim != null) {
            this.mFadeAnim.end();
        }
    }

    private void setHandledPrimaryActionMode(android.view.ActionMode actionMode) {
        endOnGoingFadeAnimation();
        this.mPrimaryActionMode = actionMode;
        this.mPrimaryActionMode.invalidate();
        this.mPrimaryActionModeView.initForMode(this.mPrimaryActionMode);
        if (this.mPrimaryActionModePopup != null) {
            post(this.mShowPrimaryActionModePopup);
        } else if (shouldAnimatePrimaryActionModeView()) {
            this.mFadeAnim = android.animation.ObjectAnimator.ofFloat(this.mPrimaryActionModeView, (android.util.Property<com.android.internal.widget.ActionBarContextView, java.lang.Float>) android.view.View.ALPHA, 0.0f, 1.0f);
            this.mFadeAnim.addListener(new android.animation.AnimatorListenerAdapter() { // from class: com.android.internal.policy.DecorView.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(android.animation.Animator animator) {
                    com.android.internal.policy.DecorView.this.mPrimaryActionModeView.setVisibility(0);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(android.animation.Animator animator) {
                    com.android.internal.policy.DecorView.this.mPrimaryActionModeView.setAlpha(1.0f);
                    com.android.internal.policy.DecorView.this.mFadeAnim = null;
                }
            });
            this.mFadeAnim.start();
        } else {
            this.mPrimaryActionModeView.setAlpha(1.0f);
            this.mPrimaryActionModeView.setVisibility(0);
        }
        this.mPrimaryActionModeView.sendAccessibilityEvent(32);
    }

    boolean shouldAnimatePrimaryActionModeView() {
        return isLaidOut();
    }

    private android.view.ActionMode createFloatingActionMode(android.view.View view, android.view.ActionMode.Callback2 callback2) {
        if (this.mFloatingActionMode != null) {
            this.mFloatingActionMode.finish();
        }
        cleanupFloatingActionModeViews();
        this.mFloatingToolbar = new com.android.internal.widget.floatingtoolbar.FloatingToolbar(this.mWindow);
        final com.android.internal.view.FloatingActionMode floatingActionMode = new com.android.internal.view.FloatingActionMode(this.mContext, callback2, view, this.mFloatingToolbar);
        this.mFloatingActionModeOriginatingView = view;
        this.mFloatingToolbarPreDrawListener = new android.view.ViewTreeObserver.OnPreDrawListener() { // from class: com.android.internal.policy.DecorView.6
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                floatingActionMode.updateViewLocationInWindow();
                return true;
            }
        };
        return floatingActionMode;
    }

    private void setHandledFloatingActionMode(android.view.ActionMode actionMode) {
        this.mFloatingActionMode = actionMode;
        this.mFloatingActionMode.invalidate();
        this.mFloatingActionModeOriginatingView.getViewTreeObserver().addOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
    }

    void enableCaption(boolean z) {
        if (this.mHasCaption != z) {
            this.mHasCaption = z;
            if (getForeground() != null) {
                drawableChanged();
            }
            notifyCaptionHeightChanged();
        }
    }

    public void notifyCaptionHeightChanged() {
        if (!android.view.ViewRootImpl.CAPTION_ON_SHELL) {
            getWindowInsetsController().setCaptionInsetsHeight(getCaptionInsetsHeight());
        }
    }

    void setWindow(com.android.internal.policy.PhoneWindow phoneWindow) {
        this.mWindow = phoneWindow;
        android.content.Context context = getContext();
        if (context instanceof com.android.internal.policy.DecorContext) {
            ((com.android.internal.policy.DecorContext) context).setPhoneWindow(this.mWindow);
        }
        if (this.mPendingWindowBackground != null) {
            android.graphics.drawable.Drawable drawable = this.mPendingWindowBackground;
            this.mPendingWindowBackground = null;
            setWindowBackground(drawable);
        }
    }

    @Override // android.view.View
    public android.content.res.Resources getResources() {
        return getContext().getResources();
    }

    @Override // android.view.View
    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateDecorCaptionStatus(configuration);
        initializeElevation();
    }

    @Override // android.view.View
    public void onMovedToDisplay(int i, android.content.res.Configuration configuration) {
        super.onMovedToDisplay(i, configuration);
        getContext().updateDisplay(i);
    }

    private boolean isFillingScreen(android.content.res.Configuration configuration) {
        return (configuration.windowConfiguration.getWindowingMode() == 1) && ((getWindowSystemUiVisibility() | getSystemUiVisibility()) & 4) != 0;
    }

    private void updateDecorCaptionStatus(android.content.res.Configuration configuration) {
        boolean z = configuration.windowConfiguration.hasWindowDecorCaption() && !isFillingScreen(configuration);
        if (this.mDecorCaptionView == null && z) {
            this.mDecorCaptionView = createDecorCaptionView(this.mWindow.getLayoutInflater());
            if (this.mDecorCaptionView != null) {
                if (this.mDecorCaptionView.getParent() == null) {
                    addView(this.mDecorCaptionView, 0, new android.view.ViewGroup.LayoutParams(-1, -1));
                }
                removeView(this.mContentRoot);
                this.mDecorCaptionView.addView(this.mContentRoot, new android.view.ViewGroup.MarginLayoutParams(-1, -1));
                return;
            }
            return;
        }
        if (this.mDecorCaptionView != null) {
            this.mDecorCaptionView.onConfigurationChanged(z);
            enableCaption(z);
        }
    }

    void onResourcesLoaded(android.view.LayoutInflater layoutInflater, int i) {
        if (this.mBackdropFrameRenderer != null) {
            loadBackgroundDrawablesIfNeeded();
            this.mBackdropFrameRenderer.onResourcesLoaded(this, this.mResizingBackgroundDrawable, this.mCaptionBackgroundDrawable, this.mUserCaptionBackgroundDrawable, getCurrentColor(this.mStatusColorViewState), getCurrentColor(this.mNavigationColorViewState));
        }
        this.mDecorCaptionView = createDecorCaptionView(layoutInflater);
        android.view.View inflate = layoutInflater.inflate(i, (android.view.ViewGroup) null);
        if (this.mDecorCaptionView != null) {
            if (this.mDecorCaptionView.getParent() == null) {
                addView(this.mDecorCaptionView, new android.view.ViewGroup.LayoutParams(-1, -1));
            }
            this.mDecorCaptionView.addView(inflate, new android.view.ViewGroup.MarginLayoutParams(-1, -1));
        } else {
            addView(inflate, 0, new android.view.ViewGroup.LayoutParams(-1, -1));
        }
        this.mContentRoot = (android.view.ViewGroup) inflate;
        initializeElevation();
    }

    private void loadBackgroundDrawablesIfNeeded() {
        if (this.mResizingBackgroundDrawable == null) {
            this.mResizingBackgroundDrawable = getResizingBackgroundDrawable(this.mWindow.mBackgroundDrawable, this.mWindow.mBackgroundFallbackDrawable, this.mWindow.isTranslucent() || this.mWindow.isShowingWallpaper());
            if (this.mResizingBackgroundDrawable == null) {
                android.util.Log.w(this.mLogTag, "Failed to find background drawable for PhoneWindow=" + this.mWindow);
            }
        }
        if (this.mCaptionBackgroundDrawable == null) {
            this.mCaptionBackgroundDrawable = getContext().getDrawable(com.android.internal.R.drawable.decor_caption_title_focused);
        }
        if (this.mResizingBackgroundDrawable != null) {
            this.mLastBackgroundDrawableCb = this.mResizingBackgroundDrawable.getCallback();
            this.mResizingBackgroundDrawable.setCallback(null);
        }
    }

    private com.android.internal.widget.DecorCaptionView createDecorCaptionView(android.view.LayoutInflater layoutInflater) {
        com.android.internal.widget.DecorCaptionView decorCaptionView = null;
        com.android.internal.widget.DecorCaptionView decorCaptionView2 = null;
        for (int childCount = getChildCount() - 1; childCount >= 0 && decorCaptionView2 == null; childCount--) {
            android.view.View childAt = getChildAt(childCount);
            if (childAt instanceof com.android.internal.widget.DecorCaptionView) {
                decorCaptionView2 = (com.android.internal.widget.DecorCaptionView) childAt;
                removeViewAt(childCount);
            }
        }
        android.view.WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        boolean z = attributes.type == 1 || attributes.type == 2 || attributes.type == 4;
        android.app.WindowConfiguration windowConfiguration = getResources().getConfiguration().windowConfiguration;
        if (!this.mWindow.isFloating() && z && windowConfiguration.hasWindowDecorCaption() && !android.view.ViewRootImpl.CAPTION_ON_SHELL) {
            if (decorCaptionView2 != null) {
                decorCaptionView = decorCaptionView2;
            } else {
                decorCaptionView = inflateDecorCaptionView(layoutInflater);
            }
            decorCaptionView.setPhoneWindow(this.mWindow, true);
        }
        enableCaption(decorCaptionView != null);
        return decorCaptionView;
    }

    private com.android.internal.widget.DecorCaptionView inflateDecorCaptionView(android.view.LayoutInflater layoutInflater) {
        com.android.internal.widget.DecorCaptionView decorCaptionView = (com.android.internal.widget.DecorCaptionView) android.view.LayoutInflater.from(getContext()).inflate(com.android.internal.R.layout.decor_caption, (android.view.ViewGroup) null);
        setDecorCaptionShade(decorCaptionView);
        return decorCaptionView;
    }

    private void setDecorCaptionShade(com.android.internal.widget.DecorCaptionView decorCaptionView) {
        switch (this.mWindow.getDecorCaptionShade()) {
            case 1:
                setLightDecorCaptionShade(decorCaptionView);
                break;
            case 2:
                setDarkDecorCaptionShade(decorCaptionView);
                break;
            default:
                if ((getWindowSystemUiVisibility() & 8192) != 0) {
                    setDarkDecorCaptionShade(decorCaptionView);
                    break;
                } else {
                    setLightDecorCaptionShade(decorCaptionView);
                    break;
                }
        }
    }

    void updateDecorCaptionShade() {
        if (this.mDecorCaptionView != null) {
            setDecorCaptionShade(this.mDecorCaptionView);
        }
    }

    private void setLightDecorCaptionShade(com.android.internal.widget.DecorCaptionView decorCaptionView) {
        decorCaptionView.findViewById(com.android.internal.R.id.maximize_window).setBackgroundResource(com.android.internal.R.drawable.decor_maximize_button_light);
        decorCaptionView.findViewById(com.android.internal.R.id.close_window).setBackgroundResource(com.android.internal.R.drawable.decor_close_button_light);
    }

    private void setDarkDecorCaptionShade(com.android.internal.widget.DecorCaptionView decorCaptionView) {
        decorCaptionView.findViewById(com.android.internal.R.id.maximize_window).setBackgroundResource(com.android.internal.R.drawable.decor_maximize_button_dark);
        decorCaptionView.findViewById(com.android.internal.R.id.close_window).setBackgroundResource(com.android.internal.R.drawable.decor_close_button_dark);
    }

    public static android.graphics.drawable.Drawable getResizingBackgroundDrawable(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2, boolean z) {
        if (drawable != null) {
            return enforceNonTranslucentBackground(drawable, z);
        }
        if (drawable2 != null) {
            return enforceNonTranslucentBackground(drawable2, z);
        }
        return new android.graphics.drawable.ColorDrawable(-16777216);
    }

    private static android.graphics.drawable.Drawable enforceNonTranslucentBackground(android.graphics.drawable.Drawable drawable, boolean z) {
        if (!z && (drawable instanceof android.graphics.drawable.ColorDrawable)) {
            android.graphics.drawable.ColorDrawable colorDrawable = (android.graphics.drawable.ColorDrawable) drawable;
            int color = colorDrawable.getColor();
            if (android.graphics.Color.alpha(color) != 255) {
                android.graphics.drawable.ColorDrawable colorDrawable2 = (android.graphics.drawable.ColorDrawable) colorDrawable.getConstantState().newDrawable().mutate();
                colorDrawable2.setColor(android.graphics.Color.argb(255, android.graphics.Color.red(color), android.graphics.Color.green(color), android.graphics.Color.blue(color)));
                return colorDrawable2;
            }
        }
        return drawable;
    }

    void clearContentView() {
        if (this.mDecorCaptionView != null) {
            this.mDecorCaptionView.removeContentView();
            return;
        }
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            android.view.View childAt = getChildAt(childCount);
            if (childAt != this.mStatusColorViewState.view && childAt != this.mNavigationColorViewState.view && childAt != this.mStatusGuard) {
                removeViewAt(childCount);
            }
        }
    }

    @Override // android.view.WindowCallbacks
    public void onWindowSizeIsChanging(android.graphics.Rect rect, boolean z, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        if (this.mBackdropFrameRenderer != null) {
            this.mBackdropFrameRenderer.setTargetRect(rect, z, rect2);
        }
    }

    @Override // android.view.WindowCallbacks
    public void onWindowDragResizeStart(android.graphics.Rect rect, boolean z, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        if (this.mWindow.isDestroyed()) {
            releaseThreadedRenderer();
            return;
        }
        if (this.mBackdropFrameRenderer != null) {
            return;
        }
        android.view.ThreadedRenderer threadedRenderer = getThreadedRenderer();
        if (threadedRenderer != null && !android.view.ViewRootImpl.CAPTION_ON_SHELL) {
            loadBackgroundDrawablesIfNeeded();
            this.mBackdropFrameRenderer = new com.android.internal.policy.BackdropFrameRenderer(this, threadedRenderer, rect, this.mResizingBackgroundDrawable, this.mCaptionBackgroundDrawable, this.mUserCaptionBackgroundDrawable, getCurrentColor(this.mStatusColorViewState), getCurrentColor(this.mNavigationColorViewState), z, getRootWindowInsets().getInsets(android.view.WindowInsets.Type.systemBars()));
            updateElevation();
            updateColorViews(null, false);
        }
        getViewRootImpl().requestInvalidateRootRenderNode();
    }

    @Override // android.view.WindowCallbacks
    public void onWindowDragResizeEnd() {
        releaseThreadedRenderer();
        updateColorViews(null, false);
        getViewRootImpl().requestInvalidateRootRenderNode();
    }

    @Override // android.view.WindowCallbacks
    public boolean onContentDrawn(int i, int i2, int i3, int i4) {
        if (this.mBackdropFrameRenderer == null) {
            return false;
        }
        return this.mBackdropFrameRenderer.onContentDrawn(i, i2, i3, i4);
    }

    @Override // android.view.WindowCallbacks
    public void onRequestDraw(boolean z) {
        if (this.mBackdropFrameRenderer != null) {
            this.mBackdropFrameRenderer.onRequestDraw(z);
        } else if (z && isAttachedToWindow()) {
            getViewRootImpl().reportDrawFinish();
        }
    }

    @Override // android.view.WindowCallbacks
    public void onPostDraw(android.graphics.RecordingCanvas recordingCanvas) {
        drawLegacyNavigationBarBackground(recordingCanvas);
    }

    private void drawLegacyNavigationBarBackground(android.graphics.RecordingCanvas recordingCanvas) {
        android.view.View view;
        if (!this.mDrawLegacyNavigationBarBackground || this.mDrawLegacyNavigationBarBackgroundHandled || (view = this.mNavigationColorViewState.view) == null) {
            return;
        }
        recordingCanvas.drawRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), this.mLegacyNavigationBarBackgroundPaint);
    }

    private void releaseThreadedRenderer() {
        if (this.mResizingBackgroundDrawable != null && this.mLastBackgroundDrawableCb != null) {
            this.mResizingBackgroundDrawable.setCallback(this.mLastBackgroundDrawableCb);
            this.mLastBackgroundDrawableCb = null;
        }
        if (this.mBackdropFrameRenderer != null) {
            this.mBackdropFrameRenderer.releaseRenderer();
            this.mBackdropFrameRenderer = null;
            updateElevation();
        }
    }

    private boolean isResizing() {
        return this.mBackdropFrameRenderer != null;
    }

    private void initializeElevation() {
        this.mAllowUpdateElevation = false;
        updateElevation();
    }

    private void updateElevation() {
        float f;
        int windowingMode = getResources().getConfiguration().windowConfiguration.getWindowingMode();
        if (this.mWindow.mRenderShadowsInCompositor) {
            return;
        }
        boolean z = this.mElevationAdjustedForStack;
        if (windowingMode == 5 && !isResizing()) {
            f = dipToPx(this.mAllowUpdateElevation ? hasWindowFocus() ? 20.0f : 5.0f : 20.0f);
            this.mElevationAdjustedForStack = true;
        } else {
            this.mElevationAdjustedForStack = false;
            f = 0.0f;
        }
        if ((z || this.mElevationAdjustedForStack) && getElevation() != f) {
            if (!isResizing()) {
                this.mWindow.setElevation(f);
            } else {
                setElevation(f);
            }
        }
    }

    boolean isShowingCaption() {
        return this.mDecorCaptionView != null && this.mDecorCaptionView.isCaptionShowing();
    }

    int getCaptionHeight() {
        if (isShowingCaption()) {
            return this.mDecorCaptionView.getCaptionHeight();
        }
        return 0;
    }

    public int getCaptionInsetsHeight() {
        if (this.mWindow.isOverlayWithDecorCaptionEnabled()) {
            return getCaptionHeight();
        }
        return 0;
    }

    private float dipToPx(float f) {
        return android.util.TypedValue.applyDimension(1, f, getResources().getDisplayMetrics());
    }

    void setUserCaptionBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        this.mUserCaptionBackgroundDrawable = drawable;
        if (this.mBackdropFrameRenderer != null) {
            this.mBackdropFrameRenderer.setUserCaptionBackgroundDrawable(drawable);
        }
    }

    private static java.lang.String getTitleSuffix(android.view.WindowManager.LayoutParams layoutParams) {
        if (layoutParams == null) {
            return "";
        }
        java.lang.String[] split = layoutParams.getTitle().toString().split("\\.");
        if (split.length <= 0) {
            return "";
        }
        return split[split.length - 1];
    }

    void updateLogTag(android.view.WindowManager.LayoutParams layoutParams) {
        this.mLogTag = "DecorView[" + getTitleSuffix(layoutParams) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.view.View
    public void requestKeyboardShortcuts(java.util.List<android.view.KeyboardShortcutGroup> list, int i) {
        com.android.internal.policy.PhoneWindow.PanelFeatureState panelState = this.mWindow.getPanelState(0, false);
        com.android.internal.view.menu.MenuBuilder menuBuilder = panelState != null ? panelState.menu : null;
        if (!this.mWindow.isDestroyed() && this.mWindow.getCallback() != null) {
            this.mWindow.getCallback().onProvideKeyboardShortcuts(list, menuBuilder, i);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchPointerCaptureChanged(boolean z) {
        super.dispatchPointerCaptureChanged(z);
        if (!this.mWindow.isDestroyed() && this.mWindow.getCallback() != null) {
            this.mWindow.getCallback().onPointerCaptureChanged(z);
        }
    }

    @Override // android.view.View
    public int getAccessibilityViewId() {
        return 2147483646;
    }

    @Override // android.view.View
    public android.view.WindowInsetsController getWindowInsetsController() {
        if (isAttachedToWindow()) {
            return super.getWindowInsetsController();
        }
        return this.mPendingInsetsController;
    }

    @Override // android.view.View
    public java.lang.String toString() {
        return super.toString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + getTitleSuffix(this.mWindow.getAttributes()) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    private static class ColorViewState {
        final com.android.internal.policy.DecorView.ColorViewAttributes attributes;
        int color;
        boolean visible;
        android.view.View view = null;
        int targetVisibility = 4;
        boolean present = false;

        ColorViewState(com.android.internal.policy.DecorView.ColorViewAttributes colorViewAttributes) {
            this.attributes = colorViewAttributes;
        }
    }

    public static class ColorViewAttributes {
        final int horizontalGravity;
        final int id;
        final int insetsType;
        final int seascapeGravity;
        final java.lang.String transitionName;
        final int translucentFlag;
        final int verticalGravity;

        private ColorViewAttributes(int i, int i2, int i3, int i4, java.lang.String str, int i5, int i6) {
            this.id = i5;
            this.translucentFlag = i;
            this.verticalGravity = i2;
            this.horizontalGravity = i3;
            this.seascapeGravity = i4;
            this.transitionName = str;
            this.insetsType = i6;
        }

        public boolean isPresent(boolean z, int i, boolean z2) {
            return z && ((Integer.MIN_VALUE & i) != 0 || z2);
        }

        public boolean isVisible(boolean z, int i, int i2, boolean z2) {
            return z && android.graphics.Color.alpha(i) != 0 && ((this.translucentFlag & i2) == 0 || z2);
        }

        public boolean isVisible(int i, int i2, int i3, boolean z) {
            return isVisible(isPresent((i & this.insetsType) != 0, i3, z), i2, i3, z);
        }
    }

    private class ActionModeCallback2Wrapper extends android.view.ActionMode.Callback2 {
        private final android.view.ActionMode.Callback mWrapped;

        public ActionModeCallback2Wrapper(android.view.ActionMode.Callback callback) {
            this.mWrapped = callback;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(android.view.ActionMode actionMode, android.view.Menu menu) {
            return this.mWrapped.onCreateActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(android.view.ActionMode actionMode, android.view.Menu menu) {
            com.android.internal.policy.DecorView.this.requestFitSystemWindows();
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(android.view.ActionMode actionMode, android.view.MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(android.view.ActionMode actionMode) {
            boolean z;
            this.mWrapped.onDestroyActionMode(actionMode);
            if (com.android.internal.policy.DecorView.this.mContext.getApplicationInfo().targetSdkVersion >= 23) {
                z = actionMode == com.android.internal.policy.DecorView.this.mPrimaryActionMode;
                r2 = actionMode == com.android.internal.policy.DecorView.this.mFloatingActionMode;
                if (!z && actionMode.getType() == 0) {
                    android.util.Log.e(com.android.internal.policy.DecorView.this.mLogTag, "Destroying unexpected ActionMode instance of TYPE_PRIMARY; " + actionMode + " was not the current primary action mode! Expected " + com.android.internal.policy.DecorView.this.mPrimaryActionMode);
                }
                if (!r2 && actionMode.getType() == 1) {
                    android.util.Log.e(com.android.internal.policy.DecorView.this.mLogTag, "Destroying unexpected ActionMode instance of TYPE_FLOATING; " + actionMode + " was not the current floating action mode! Expected " + com.android.internal.policy.DecorView.this.mFloatingActionMode);
                }
            } else {
                z = actionMode.getType() == 0;
                if (actionMode.getType() == 1) {
                    r2 = true;
                }
            }
            if (z) {
                if (com.android.internal.policy.DecorView.this.mPrimaryActionModePopup != null) {
                    com.android.internal.policy.DecorView.this.removeCallbacks(com.android.internal.policy.DecorView.this.mShowPrimaryActionModePopup);
                }
                if (com.android.internal.policy.DecorView.this.mPrimaryActionModeView != null) {
                    com.android.internal.policy.DecorView.this.endOnGoingFadeAnimation();
                    final com.android.internal.widget.ActionBarContextView actionBarContextView = com.android.internal.policy.DecorView.this.mPrimaryActionModeView;
                    com.android.internal.policy.DecorView.this.mFadeAnim = android.animation.ObjectAnimator.ofFloat(com.android.internal.policy.DecorView.this.mPrimaryActionModeView, (android.util.Property<com.android.internal.widget.ActionBarContextView, java.lang.Float>) android.view.View.ALPHA, 1.0f, 0.0f);
                    com.android.internal.policy.DecorView.this.mFadeAnim.addListener(new android.animation.Animator.AnimatorListener() { // from class: com.android.internal.policy.DecorView.ActionModeCallback2Wrapper.1
                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(android.animation.Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(android.animation.Animator animator) {
                            if (actionBarContextView == com.android.internal.policy.DecorView.this.mPrimaryActionModeView) {
                                actionBarContextView.setVisibility(8);
                                if (com.android.internal.policy.DecorView.this.mPrimaryActionModePopup != null) {
                                    com.android.internal.policy.DecorView.this.mPrimaryActionModePopup.dismiss();
                                }
                                actionBarContextView.killMode();
                                com.android.internal.policy.DecorView.this.mFadeAnim = null;
                                com.android.internal.policy.DecorView.this.requestApplyInsets();
                            }
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(android.animation.Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationRepeat(android.animation.Animator animator) {
                        }
                    });
                    com.android.internal.policy.DecorView.this.mFadeAnim.start();
                }
                com.android.internal.policy.DecorView.this.mPrimaryActionMode = null;
            } else if (r2) {
                com.android.internal.policy.DecorView.this.cleanupFloatingActionModeViews();
                com.android.internal.policy.DecorView.this.mFloatingActionMode = null;
            }
            if (com.android.internal.policy.DecorView.this.mWindow.getCallback() != null && !com.android.internal.policy.DecorView.this.mWindow.isDestroyed()) {
                try {
                    com.android.internal.policy.DecorView.this.mWindow.getCallback().onActionModeFinished(actionMode);
                } catch (java.lang.AbstractMethodError e) {
                }
            }
            com.android.internal.policy.DecorView.this.requestFitSystemWindows();
        }

        @Override // android.view.ActionMode.Callback2
        public void onGetContentRect(android.view.ActionMode actionMode, android.view.View view, android.graphics.Rect rect) {
            if (this.mWrapped instanceof android.view.ActionMode.Callback2) {
                ((android.view.ActionMode.Callback2) this.mWrapped).onGetContentRect(actionMode, view, rect);
            } else {
                super.onGetContentRect(actionMode, view, rect);
            }
        }
    }
}
