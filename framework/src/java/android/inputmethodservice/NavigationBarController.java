package android.inputmethodservice;

/* loaded from: classes2.dex */
final class NavigationBarController {
    private final android.inputmethodservice.NavigationBarController.Callback mImpl;

    private interface Callback {
        public static final android.inputmethodservice.NavigationBarController.Callback NOOP = new android.inputmethodservice.NavigationBarController.Callback() { // from class: android.inputmethodservice.NavigationBarController.Callback.1
        };

        default void updateTouchableInsets(android.inputmethodservice.InputMethodService.Insets insets, android.view.ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        }

        default void onSoftInputWindowCreated(android.inputmethodservice.SoftInputWindow softInputWindow) {
        }

        default void onViewInitialized() {
        }

        default void onWindowShown() {
        }

        default void onDestroy() {
        }

        default void onNavButtonFlagsChanged(int i) {
        }

        default boolean isShown() {
            return false;
        }

        default java.lang.String toDebugString() {
            return "No-op implementation";
        }
    }

    NavigationBarController(android.inputmethodservice.InputMethodService inputMethodService) {
        this.mImpl = android.inputmethodservice.InputMethodService.canImeRenderGesturalNavButtons() ? new android.inputmethodservice.NavigationBarController.Impl(inputMethodService) : android.inputmethodservice.NavigationBarController.Callback.NOOP;
    }

    void updateTouchableInsets(android.inputmethodservice.InputMethodService.Insets insets, android.view.ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        this.mImpl.updateTouchableInsets(insets, internalInsetsInfo);
    }

    void onSoftInputWindowCreated(android.inputmethodservice.SoftInputWindow softInputWindow) {
        this.mImpl.onSoftInputWindowCreated(softInputWindow);
    }

    void onViewInitialized() {
        this.mImpl.onViewInitialized();
    }

    void onWindowShown() {
        this.mImpl.onWindowShown();
    }

    void onDestroy() {
        this.mImpl.onDestroy();
    }

    void onNavButtonFlagsChanged(int i) {
        this.mImpl.onNavButtonFlagsChanged(i);
    }

    boolean isShown() {
        return this.mImpl.isShown();
    }

    java.lang.String toDebugString() {
        return this.mImpl.toDebugString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class Impl implements android.inputmethodservice.NavigationBarController.Callback, android.view.Window.DecorCallback {
        private static final int DEFAULT_COLOR_ADAPT_TRANSITION_TIME = 1700;
        private static final android.view.animation.Interpolator LEGACY_DECELERATE = new android.view.animation.PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
        private int mAppearance;
        private float mDarkIntensity;
        private boolean mDrawLegacyNavigationBarBackground;
        private boolean mImeDrawsImeNavBar;
        android.graphics.Insets mLastInsets;
        private android.inputmethodservice.navigationbar.NavigationBarFrame mNavigationBarFrame;
        private final android.inputmethodservice.InputMethodService mService;
        private boolean mShouldShowImeSwitcherWhenImeIsShown;
        private android.animation.ValueAnimator mTintAnimator;
        private boolean mDestroyed = false;
        private final android.graphics.Rect mTempRect = new android.graphics.Rect();
        private final int[] mTempPos = new int[2];

        Impl(android.inputmethodservice.InputMethodService inputMethodService) {
            this.mService = inputMethodService;
        }

        private android.graphics.Insets getSystemInsets() {
            android.view.View decorView;
            android.view.WindowInsets rootWindowInsets;
            if (this.mService.mWindow == null || (decorView = this.mService.mWindow.getWindow().getDecorView()) == null || (rootWindowInsets = decorView.getRootWindowInsets()) == null) {
                return null;
            }
            return android.graphics.Insets.min(rootWindowInsets.getInsets(android.view.WindowInsets.Type.systemBars() | android.view.WindowInsets.Type.displayCutout()), rootWindowInsets.getInsetsIgnoringVisibility(android.view.WindowInsets.Type.systemBars()));
        }

        private void installNavigationBarFrameIfNecessary() {
            int i;
            if (!this.mImeDrawsImeNavBar || this.mNavigationBarFrame != null) {
                return;
            }
            android.view.View decorView = this.mService.mWindow.getWindow().getDecorView();
            if (!(decorView instanceof android.view.ViewGroup)) {
                return;
            }
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) decorView;
            java.util.Objects.requireNonNull(android.inputmethodservice.navigationbar.NavigationBarFrame.class);
            this.mNavigationBarFrame = (android.inputmethodservice.navigationbar.NavigationBarFrame) viewGroup.findViewByPredicate(new android.inputmethodservice.NavigationBarController$Impl$$ExternalSyntheticLambda1(android.inputmethodservice.navigationbar.NavigationBarFrame.class));
            android.graphics.Insets systemInsets = getSystemInsets();
            if (this.mNavigationBarFrame == null) {
                this.mNavigationBarFrame = new android.inputmethodservice.navigationbar.NavigationBarFrame(this.mService);
                android.view.LayoutInflater.from(this.mService).inflate(com.android.internal.R.layout.input_method_navigation_bar, this.mNavigationBarFrame);
                if (systemInsets != null) {
                    viewGroup.addView(this.mNavigationBarFrame, new android.widget.FrameLayout.LayoutParams(-1, systemInsets.bottom, 80));
                    this.mLastInsets = systemInsets;
                } else {
                    viewGroup.addView(this.mNavigationBarFrame);
                }
                android.inputmethodservice.navigationbar.NavigationBarFrame navigationBarFrame = this.mNavigationBarFrame;
                java.util.Objects.requireNonNull(android.inputmethodservice.navigationbar.NavigationBarView.class);
                android.inputmethodservice.navigationbar.NavigationBarView navigationBarView = (android.inputmethodservice.navigationbar.NavigationBarView) navigationBarFrame.findViewByPredicate(new android.inputmethodservice.NavigationBarController$Impl$$ExternalSyntheticLambda1(android.inputmethodservice.navigationbar.NavigationBarView.class));
                if (navigationBarView != null) {
                    if (this.mShouldShowImeSwitcherWhenImeIsShown) {
                        i = 4;
                    } else {
                        i = 0;
                    }
                    navigationBarView.setNavigationIconHints(i | 1);
                }
            } else {
                this.mNavigationBarFrame.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1, systemInsets.bottom, 80));
                this.mLastInsets = systemInsets;
            }
            if (this.mDrawLegacyNavigationBarBackground) {
                this.mNavigationBarFrame.setBackgroundColor(-16777216);
            } else {
                this.mNavigationBarFrame.setBackground(null);
            }
            setIconTintInternal(calculateTargetDarkIntensity(this.mAppearance, this.mDrawLegacyNavigationBarBackground));
            this.mNavigationBarFrame.setOnApplyWindowInsetsListener(new android.view.View.OnApplyWindowInsetsListener() { // from class: android.inputmethodservice.NavigationBarController$Impl$$ExternalSyntheticLambda2
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final android.view.WindowInsets onApplyWindowInsets(android.view.View view, android.view.WindowInsets windowInsets) {
                    android.view.WindowInsets lambda$installNavigationBarFrameIfNecessary$0;
                    lambda$installNavigationBarFrameIfNecessary$0 = android.inputmethodservice.NavigationBarController.Impl.this.lambda$installNavigationBarFrameIfNecessary$0(view, windowInsets);
                    return lambda$installNavigationBarFrameIfNecessary$0;
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ android.view.WindowInsets lambda$installNavigationBarFrameIfNecessary$0(android.view.View view, android.view.WindowInsets windowInsets) {
            if (this.mNavigationBarFrame != null) {
                this.mNavigationBarFrame.setVisibility(windowInsets.isVisible(android.view.WindowInsets.Type.captionBar()) ? 0 : 8);
            }
            return view.onApplyWindowInsets(windowInsets);
        }

        private void uninstallNavigationBarFrameIfNecessary() {
            if (this.mNavigationBarFrame == null) {
                return;
            }
            android.view.ViewParent parent = this.mNavigationBarFrame.getParent();
            if (parent instanceof android.view.ViewGroup) {
                ((android.view.ViewGroup) parent).removeView(this.mNavigationBarFrame);
            }
            this.mNavigationBarFrame.setOnApplyWindowInsetsListener(null);
            this.mNavigationBarFrame = null;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void updateTouchableInsets(android.inputmethodservice.InputMethodService.Insets insets, android.view.ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
            android.graphics.Insets systemInsets;
            android.graphics.Region region;
            if (this.mImeDrawsImeNavBar && this.mNavigationBarFrame != null && (systemInsets = getSystemInsets()) != null) {
                android.view.Window window = this.mService.mWindow.getWindow();
                android.view.View decorView = window.getDecorView();
                boolean z = false;
                if (!this.mService.isExtractViewShown()) {
                    android.widget.FrameLayout frameLayout = this.mService.mInputFrame;
                    switch (insets.touchableInsets) {
                        case 0:
                            if (frameLayout.getVisibility() == 0) {
                                frameLayout.getLocationInWindow(this.mTempPos);
                                this.mTempRect.set(this.mTempPos[0], this.mTempPos[1], this.mTempPos[0] + frameLayout.getWidth(), this.mTempPos[1] + frameLayout.getHeight());
                                region = new android.graphics.Region(this.mTempRect);
                                break;
                            }
                            region = null;
                            break;
                        case 1:
                            if (frameLayout.getVisibility() == 0) {
                                frameLayout.getLocationInWindow(this.mTempPos);
                                this.mTempRect.set(this.mTempPos[0], insets.contentTopInsets, this.mTempPos[0] + frameLayout.getWidth(), this.mTempPos[1] + frameLayout.getHeight());
                                region = new android.graphics.Region(this.mTempRect);
                                break;
                            }
                            region = null;
                            break;
                        case 2:
                            if (frameLayout.getVisibility() == 0) {
                                frameLayout.getLocationInWindow(this.mTempPos);
                                this.mTempRect.set(this.mTempPos[0], insets.visibleTopInsets, this.mTempPos[0] + frameLayout.getWidth(), this.mTempPos[1] + frameLayout.getHeight());
                                region = new android.graphics.Region(this.mTempRect);
                                break;
                            }
                            region = null;
                            break;
                        case 3:
                            region = new android.graphics.Region();
                            region.set(insets.touchableRegion);
                            break;
                        default:
                            region = null;
                            break;
                    }
                    this.mTempRect.set(decorView.getLeft(), decorView.getBottom() - systemInsets.bottom, decorView.getRight(), decorView.getBottom());
                    if (region == null) {
                        region = new android.graphics.Region(this.mTempRect);
                    } else {
                        region.union(this.mTempRect);
                    }
                    internalInsetsInfo.touchableRegion.set(region);
                    internalInsetsInfo.setTouchableInsets(3);
                }
                if (decorView instanceof android.view.ViewGroup) {
                    android.view.ViewGroup viewGroup = (android.view.ViewGroup) decorView;
                    android.view.View navigationBarBackgroundView = window.getNavigationBarBackgroundView();
                    if (navigationBarBackgroundView != null && viewGroup.indexOfChild(navigationBarBackgroundView) > viewGroup.indexOfChild(this.mNavigationBarFrame)) {
                        z = true;
                    }
                }
                boolean z2 = !java.util.Objects.equals(systemInsets, this.mLastInsets);
                if (z || z2) {
                    scheduleRelayout();
                }
            }
        }

        private void scheduleRelayout() {
            final android.inputmethodservice.navigationbar.NavigationBarFrame navigationBarFrame = this.mNavigationBarFrame;
            navigationBarFrame.post(new java.lang.Runnable() { // from class: android.inputmethodservice.NavigationBarController$Impl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.inputmethodservice.NavigationBarController.Impl.this.lambda$scheduleRelayout$1(navigationBarFrame);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$scheduleRelayout$1(android.inputmethodservice.navigationbar.NavigationBarFrame navigationBarFrame) {
            android.view.Window window;
            android.view.View peekDecorView;
            if (this.mDestroyed || !navigationBarFrame.isAttachedToWindow() || (window = this.mService.mWindow.getWindow()) == null || (peekDecorView = window.peekDecorView()) == null || !(peekDecorView instanceof android.view.ViewGroup)) {
                return;
            }
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) peekDecorView;
            android.graphics.Insets systemInsets = getSystemInsets();
            if (!java.util.Objects.equals(systemInsets, this.mLastInsets)) {
                navigationBarFrame.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1, systemInsets.bottom, 80));
                this.mLastInsets = systemInsets;
            }
            android.view.View navigationBarBackgroundView = window.getNavigationBarBackgroundView();
            if (navigationBarBackgroundView != null && viewGroup.indexOfChild(navigationBarBackgroundView) > viewGroup.indexOfChild(navigationBarFrame)) {
                viewGroup.bringChildToFront(navigationBarFrame);
            }
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void onSoftInputWindowCreated(android.inputmethodservice.SoftInputWindow softInputWindow) {
            android.view.Window window = softInputWindow.getWindow();
            this.mAppearance = window.getSystemBarAppearance();
            window.setDecorCallback(this);
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void onViewInitialized() {
            if (this.mDestroyed) {
                return;
            }
            installNavigationBarFrameIfNecessary();
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void onDestroy() {
            if (this.mDestroyed) {
                return;
            }
            if (this.mTintAnimator != null) {
                this.mTintAnimator.cancel();
                this.mTintAnimator = null;
            }
            this.mDestroyed = true;
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void onWindowShown() {
            android.graphics.Insets systemInsets;
            if (!this.mDestroyed && this.mImeDrawsImeNavBar && this.mNavigationBarFrame != null && (systemInsets = getSystemInsets()) != null) {
                if (!java.util.Objects.equals(systemInsets, this.mLastInsets)) {
                    this.mNavigationBarFrame.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-1, systemInsets.bottom, 80));
                    this.mLastInsets = systemInsets;
                }
                android.view.Window window = this.mService.mWindow.getWindow();
                android.view.View decorView = window.getDecorView();
                if (decorView instanceof android.view.ViewGroup) {
                    android.view.ViewGroup viewGroup = (android.view.ViewGroup) decorView;
                    android.view.View navigationBarBackgroundView = window.getNavigationBarBackgroundView();
                    if (navigationBarBackgroundView != null && viewGroup.indexOfChild(navigationBarBackgroundView) > viewGroup.indexOfChild(this.mNavigationBarFrame)) {
                        viewGroup.bringChildToFront(this.mNavigationBarFrame);
                    }
                }
            }
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public void onNavButtonFlagsChanged(int i) {
            if (this.mDestroyed) {
                return;
            }
            boolean z = (i & 1) != 0;
            boolean z2 = (i & 2) != 0;
            this.mImeDrawsImeNavBar = z;
            boolean z3 = this.mShouldShowImeSwitcherWhenImeIsShown;
            this.mShouldShowImeSwitcherWhenImeIsShown = z2;
            this.mService.mWindow.getWindow().getDecorView().getWindowInsetsController().setImeCaptionBarInsetsHeight(getImeCaptionBarHeight());
            if (z) {
                installNavigationBarFrameIfNecessary();
                if (this.mNavigationBarFrame == null || this.mShouldShowImeSwitcherWhenImeIsShown == z3) {
                    return;
                }
                android.inputmethodservice.navigationbar.NavigationBarFrame navigationBarFrame = this.mNavigationBarFrame;
                java.util.Objects.requireNonNull(android.inputmethodservice.navigationbar.NavigationBarView.class);
                android.inputmethodservice.navigationbar.NavigationBarView navigationBarView = (android.inputmethodservice.navigationbar.NavigationBarView) navigationBarFrame.findViewByPredicate(new android.inputmethodservice.NavigationBarController$Impl$$ExternalSyntheticLambda1(android.inputmethodservice.navigationbar.NavigationBarView.class));
                if (navigationBarView == null) {
                    return;
                }
                navigationBarView.setNavigationIconHints((z2 ? 4 : 0) | 1);
                return;
            }
            uninstallNavigationBarFrameIfNecessary();
        }

        @Override // android.view.Window.DecorCallback
        public void onSystemBarAppearanceChanged(int i) {
            if (this.mDestroyed) {
                return;
            }
            this.mAppearance = i;
            if (this.mNavigationBarFrame == null) {
                return;
            }
            float calculateTargetDarkIntensity = calculateTargetDarkIntensity(this.mAppearance, this.mDrawLegacyNavigationBarBackground);
            if (this.mTintAnimator != null) {
                this.mTintAnimator.cancel();
            }
            this.mTintAnimator = android.animation.ValueAnimator.ofFloat(this.mDarkIntensity, calculateTargetDarkIntensity);
            this.mTintAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.inputmethodservice.NavigationBarController$Impl$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                    android.inputmethodservice.NavigationBarController.Impl.this.lambda$onSystemBarAppearanceChanged$2(valueAnimator);
                }
            });
            this.mTintAnimator.setDuration(1700L);
            this.mTintAnimator.setStartDelay(0L);
            this.mTintAnimator.setInterpolator(LEGACY_DECELERATE);
            this.mTintAnimator.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSystemBarAppearanceChanged$2(android.animation.ValueAnimator valueAnimator) {
            setIconTintInternal(((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue());
        }

        private void setIconTintInternal(float f) {
            this.mDarkIntensity = f;
            if (this.mNavigationBarFrame == null) {
                return;
            }
            android.inputmethodservice.navigationbar.NavigationBarFrame navigationBarFrame = this.mNavigationBarFrame;
            java.util.Objects.requireNonNull(android.inputmethodservice.navigationbar.NavigationBarView.class);
            android.inputmethodservice.navigationbar.NavigationBarView navigationBarView = (android.inputmethodservice.navigationbar.NavigationBarView) navigationBarFrame.findViewByPredicate(new android.inputmethodservice.NavigationBarController$Impl$$ExternalSyntheticLambda1(android.inputmethodservice.navigationbar.NavigationBarView.class));
            if (navigationBarView == null) {
                return;
            }
            navigationBarView.setDarkIntensity(f);
        }

        private static float calculateTargetDarkIntensity(int i, boolean z) {
            return !z && (i & 16) != 0 ? 1.0f : 0.0f;
        }

        @Override // android.view.Window.DecorCallback
        public boolean onDrawLegacyNavigationBarBackgroundChanged(boolean z) {
            if (this.mDestroyed) {
                return false;
            }
            if (z != this.mDrawLegacyNavigationBarBackground) {
                this.mDrawLegacyNavigationBarBackground = z;
                if (this.mNavigationBarFrame != null) {
                    if (this.mDrawLegacyNavigationBarBackground) {
                        this.mNavigationBarFrame.setBackgroundColor(-16777216);
                    } else {
                        this.mNavigationBarFrame.setBackground(null);
                    }
                    scheduleRelayout();
                }
                onSystemBarAppearanceChanged(this.mAppearance);
            }
            return z;
        }

        private int getImeCaptionBarHeight() {
            if (this.mImeDrawsImeNavBar) {
                return this.mService.getResources().getDimensionPixelSize(com.android.internal.R.dimen.navigation_bar_frame_height);
            }
            return 0;
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public boolean isShown() {
            return this.mNavigationBarFrame != null && this.mNavigationBarFrame.getVisibility() == 0;
        }

        @Override // android.inputmethodservice.NavigationBarController.Callback
        public java.lang.String toDebugString() {
            return "{mImeDrawsImeNavBar=" + this.mImeDrawsImeNavBar + " mNavigationBarFrame=" + this.mNavigationBarFrame + " mShouldShowImeSwitcherWhenImeIsShown=" + this.mShouldShowImeSwitcherWhenImeIsShown + " mAppearance=0x" + java.lang.Integer.toHexString(this.mAppearance) + " mDarkIntensity=" + this.mDarkIntensity + " mDrawLegacyNavigationBarBackground=" + this.mDrawLegacyNavigationBarBackground + "}";
        }
    }
}
