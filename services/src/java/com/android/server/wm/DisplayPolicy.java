package com.android.server.wm;

/* loaded from: classes3.dex */
public class DisplayPolicy {
    static final int ANIMATION_NONE = -1;
    static final int ANIMATION_STYLEABLE = 0;
    private static final int INSETS_OVERRIDE_INDEX_INVALID = -1;
    private static final int MSG_DISABLE_POINTER_LOCATION = 5;
    private static final int MSG_ENABLE_POINTER_LOCATION = 4;
    private static final int NAV_BAR_FORCE_TRANSPARENT = 2;
    private static final int NAV_BAR_OPAQUE_WHEN_FREEFORM_OR_DOCKED = 0;
    private static final int NAV_BAR_TRANSLUCENT_WHEN_FREEFORM_OPAQUE_OTHERWISE = 1;
    private static final long PANIC_GESTURE_EXPIRATION = 30000;
    private static final java.lang.String TAG = "WindowManager";
    private final android.view.accessibility.AccessibilityManager mAccessibilityManager;
    private boolean mAllowLockscreenWhenOn;
    private final com.android.server.wm.WindowManagerInternal.AppTransitionListener mAppTransitionListener;
    private volatile boolean mAwake;
    private com.android.server.wm.WindowState mBottomGestureHost;
    private com.android.server.wm.DisplayPolicy.DecorInsets.Cache mCachedDecorInsets;
    private boolean mCanSystemBarsBeShownByUser;
    private final boolean mCarDockEnablesAccelerometer;
    private final android.content.Context mContext;
    private android.content.res.Resources mCurrentUserResources;
    final com.android.server.wm.DisplayPolicy.DecorInsets mDecorInsets;
    private final boolean mDeskDockEnablesAccelerometer;
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private boolean mDreamingLockscreen;
    private java.lang.String mFocusedApp;
    private com.android.server.wm.WindowState mFocusedWindow;
    private final com.android.internal.policy.ForceShowNavBarSettingsObserver mForceShowNavBarSettingsObserver;
    private boolean mForceShowNavigationBarEnabled;
    private int mForciblyShownTypes;
    private final com.android.internal.policy.GestureNavigationSettingsObserver mGestureNavigationSettingsObserver;
    private final android.os.Handler mHandler;
    private volatile boolean mHasNavigationBar;
    private volatile boolean mHasStatusBar;
    private volatile boolean mHdmiPlugged;
    private boolean mImeInsetsConsumed;
    private boolean mImmersiveConfirmationWindowExists;
    private final com.android.server.wm.ImmersiveModeConfirmation mImmersiveModeConfirmation;
    private boolean mIsFreeformWindowOverlappingWithNavBar;
    private boolean mIsImmersiveMode;
    private volatile boolean mKeyguardDrawComplete;
    private int mLastAppearance;
    private int mLastBehavior;
    private int mLastDisableFlags;
    private com.android.server.wm.WindowState mLastFocusedWindow;
    private com.android.internal.statusbar.LetterboxDetails[] mLastLetterboxDetails;
    private boolean mLastShowingDream;
    private com.android.internal.view.AppearanceRegion[] mLastStatusBarAppearanceRegions;
    private com.android.server.wm.WindowState mLeftGestureHost;
    private int mLeftGestureInset;
    private final java.lang.Object mLock;
    private com.android.server.wm.WindowState mNavBarBackgroundWindowCandidate;
    private com.android.server.wm.WindowState mNavBarColorWindowCandidate;
    private volatile boolean mNavigationBarAlwaysShowOnSideGesture;
    private volatile boolean mNavigationBarCanMove;
    private volatile com.android.server.wm.WindowState mNotificationShade;
    private final long mPanicThresholdMs;
    private long mPanicTime;
    private long mPendingPanicGestureUptime;
    private volatile boolean mPersistentVrModeEnabled;
    private com.android.internal.widget.PointerLocationView mPointerLocationView;
    private com.android.server.wm.RefreshRatePolicy mRefreshRatePolicy;
    private boolean mRemoteInsetsControllerControlsSystemBars;
    private com.android.server.wm.WindowState mRightGestureHost;
    private int mRightGestureInset;
    private volatile boolean mScreenOnEarly;
    private volatile boolean mScreenOnFully;
    private volatile com.android.server.policy.WindowManagerPolicy.ScreenOnListener mScreenOnListener;
    private final com.android.internal.util.ScreenshotHelper mScreenshotHelper;
    private final com.android.server.wm.WindowManagerService mService;
    private com.android.server.wm.DisplayPolicy.SettingsObserver mSettingsObserver;
    private boolean mShouldAttachNavBarToAppDuringTransition;
    private boolean mShowingDream;
    private com.android.server.statusbar.StatusBarManagerInternal mStatusBarManagerInternal;
    private com.android.server.wm.SystemGesturesPointerEventListener mSystemGestures;
    private com.android.server.wm.WindowState mSystemUiControllingWindow;
    private com.android.server.wm.WindowState mTopFullscreenOpaqueWindowState;
    private com.android.server.wm.WindowState mTopGestureHost;
    private boolean mTopIsFullscreen;
    private final android.content.Context mUiContext;
    private volatile boolean mWindowManagerDrawComplete;
    private static final int SHOW_TYPES_FOR_SWIPE = android.view.WindowInsets.Type.statusBars() | android.view.WindowInsets.Type.navigationBars();
    private static final int SHOW_TYPES_FOR_PANIC = android.view.WindowInsets.Type.navigationBars();
    private static final boolean USE_CACHED_INSETS_FOR_DISPLAY_SWITCH = android.os.SystemProperties.getBoolean("persist.wm.debug.cached_insets_switch", true);
    private static final android.graphics.Rect sTmpRect = new android.graphics.Rect();
    private static final android.graphics.Rect sTmpRect2 = new android.graphics.Rect();
    private static final android.graphics.Rect sTmpDisplayCutoutSafe = new android.graphics.Rect();
    private static final android.window.ClientWindowFrames sTmpClientFrames = new android.window.ClientWindowFrames();
    private final java.lang.Object mServiceAcquireLock = new java.lang.Object();
    private volatile int mLidState = -1;
    private volatile int mDockMode = 0;
    private volatile int mForceNavbar = -1;
    private com.android.server.wm.WindowState mStatusBar = null;
    private com.android.server.wm.WindowState mNavigationBar = null;
    private int mNavigationBarPosition = 4;
    private final android.util.ArraySet<com.android.server.wm.WindowState> mInsetsSourceWindowsExceptIme = new android.util.ArraySet<>();
    private final android.util.ArraySet<com.android.server.wm.ActivityRecord> mSystemBarColorApps = new android.util.ArraySet<>();
    private final android.util.ArraySet<com.android.server.wm.ActivityRecord> mRelaunchingSystemBarColorApps = new android.util.ArraySet<>();
    private final java.util.ArrayList<com.android.internal.view.AppearanceRegion> mStatusBarAppearanceRegionList = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.server.wm.WindowState> mStatusBarBackgroundWindows = new java.util.ArrayList<>();
    private final java.util.ArrayList<com.android.internal.statusbar.LetterboxDetails> mLetterboxDetails = new java.util.ArrayList<>();
    private int mLastRequestedVisibleTypes = android.view.WindowInsets.Type.defaultVisible();
    private final android.graphics.Rect mStatusBarColorCheckedBounds = new android.graphics.Rect();
    private final android.graphics.Rect mStatusBarBackgroundCheckedBounds = new android.graphics.Rect();
    private boolean mLastFocusIsFullscreen = false;
    private final android.view.WindowLayout mWindowLayout = new android.view.WindowLayout();
    private int mNavBarOpacityMode = 0;
    private final java.lang.Runnable mHiddenNavPanic = new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy.3
        @Override // java.lang.Runnable
        public void run() {
            synchronized (com.android.server.wm.DisplayPolicy.this.mLock) {
                try {
                    if (com.android.server.wm.DisplayPolicy.this.mService.mPolicy.isUserSetupComplete()) {
                        com.android.server.wm.DisplayPolicy.this.mPendingPanicGestureUptime = android.os.SystemClock.uptimeMillis();
                        com.android.server.wm.DisplayPolicy.this.updateSystemBarAttributes();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    };

    com.android.server.statusbar.StatusBarManagerInternal getStatusBarManagerInternal() {
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal;
        synchronized (this.mServiceAcquireLock) {
            try {
                if (this.mStatusBarManagerInternal == null) {
                    this.mStatusBarManagerInternal = (com.android.server.statusbar.StatusBarManagerInternal) com.android.server.LocalServices.getService(com.android.server.statusbar.StatusBarManagerInternal.class);
                }
                statusBarManagerInternal = this.mStatusBarManagerInternal;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return statusBarManagerInternal;
    }

    private class PolicyHandler extends android.os.Handler {
        PolicyHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 4:
                    com.android.server.wm.DisplayPolicy.this.enablePointerLocation();
                    break;
                case 5:
                    com.android.server.wm.DisplayPolicy.this.disablePointerLocation();
                    break;
            }
        }
    }

    private class SettingsObserver extends android.database.ContentObserver {
        public SettingsObserver(android.os.Handler handler) {
            super(handler);
            com.android.server.wm.DisplayPolicy.this.mContext.getContentResolver().registerContentObserver(lineageos.providers.LineageSettings.System.getUriFor("force_show_navbar"), false, this, -1);
            com.android.server.wm.DisplayPolicy.this.updateSettings();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            com.android.server.wm.DisplayPolicy.this.updateSettings();
        }
    }

    DisplayPolicy(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent) {
        this.mService = windowManagerService;
        this.mContext = displayContent.isDefaultDisplay ? windowManagerService.mContext : windowManagerService.mContext.createDisplayContext(displayContent.getDisplay());
        this.mUiContext = displayContent.isDefaultDisplay ? windowManagerService.mAtmService.getUiContext() : windowManagerService.mAtmService.mSystemThread.getSystemUiContext(displayContent.getDisplayId());
        this.mDisplayContent = displayContent;
        this.mDecorInsets = new com.android.server.wm.DisplayPolicy.DecorInsets(displayContent);
        this.mLock = windowManagerService.getWindowManagerLock();
        int displayId = displayContent.getDisplayId();
        android.content.res.Resources resources = this.mContext.getResources();
        this.mCarDockEnablesAccelerometer = resources.getBoolean(android.R.bool.config_carDockEnablesAccelerometer);
        this.mDeskDockEnablesAccelerometer = resources.getBoolean(android.R.bool.config_deskDockEnablesAccelerometer);
        this.mCanSystemBarsBeShownByUser = !resources.getBoolean(android.R.bool.config_remoteInsetsControllerControlsSystemBars) || resources.getBoolean(android.R.bool.config_quickSettingsShowMediaPlayer);
        this.mPanicThresholdMs = resources.getInteger(android.R.integer.config_fixedRefreshRateInHighZone);
        this.mAccessibilityManager = (android.view.accessibility.AccessibilityManager) this.mContext.getSystemService("accessibility");
        if (!displayContent.isDefaultDisplay) {
            this.mAwake = true;
            this.mScreenOnEarly = true;
            this.mScreenOnFully = true;
        }
        android.os.Looper looper = com.android.server.UiThread.getHandler().getLooper();
        this.mHandler = new com.android.server.wm.DisplayPolicy.PolicyHandler(looper);
        if (!android.view.ViewRootImpl.CLIENT_TRANSIENT) {
            this.mSystemGestures = new com.android.server.wm.SystemGesturesPointerEventListener(this.mUiContext, this.mHandler, new com.android.server.wm.DisplayPolicy.AnonymousClass1());
            displayContent.registerPointerEventListener(this.mSystemGestures);
        }
        this.mAppTransitionListener = new com.android.server.wm.DisplayPolicy.AnonymousClass2(displayId);
        displayContent.mAppTransition.registerListenerLocked(this.mAppTransitionListener);
        displayContent.mTransitionController.registerLegacyListener(this.mAppTransitionListener);
        if (android.view.ViewRootImpl.CLIENT_TRANSIENT || android.view.ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION) {
            this.mImmersiveModeConfirmation = null;
        } else {
            this.mImmersiveModeConfirmation = new com.android.server.wm.ImmersiveModeConfirmation(this.mContext, looper, this.mService.mVrModeEnabled, this.mCanSystemBarsBeShownByUser);
        }
        this.mScreenshotHelper = displayContent.isDefaultDisplay ? new com.android.internal.util.ScreenshotHelper(this.mContext) : null;
        if (this.mDisplayContent.isDefaultDisplay) {
            this.mHasStatusBar = true;
            this.mHasNavigationBar = this.mContext.getResources().getBoolean(android.R.bool.config_showBuiltinWirelessChargingAnim);
            java.lang.String str = android.os.SystemProperties.get("qemu.hw.mainkeys");
            if ("1".equals(str)) {
                this.mHasNavigationBar = false;
            } else if ("0".equals(str)) {
                this.mHasNavigationBar = true;
            }
            this.mSettingsObserver = new com.android.server.wm.DisplayPolicy.SettingsObserver(this.mHandler);
        } else {
            this.mHasStatusBar = false;
            this.mHasNavigationBar = this.mDisplayContent.supportsSystemDecorations();
        }
        this.mRefreshRatePolicy = new com.android.server.wm.RefreshRatePolicy(this.mService, this.mDisplayContent.getDisplayInfo(), this.mService.mHighRefreshRateDenylist);
        this.mGestureNavigationSettingsObserver = new com.android.internal.policy.GestureNavigationSettingsObserver(this.mHandler, this.mContext, new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayPolicy.this.lambda$new$0();
            }
        });
        android.os.Handler handler = this.mHandler;
        final com.android.internal.policy.GestureNavigationSettingsObserver gestureNavigationSettingsObserver = this.mGestureNavigationSettingsObserver;
        java.util.Objects.requireNonNull(gestureNavigationSettingsObserver);
        handler.post(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                gestureNavigationSettingsObserver.register();
            }
        });
        this.mForceShowNavBarSettingsObserver = new com.android.internal.policy.ForceShowNavBarSettingsObserver(this.mHandler, this.mContext);
        this.mForceShowNavBarSettingsObserver.setOnChangeRunnable(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayPolicy.this.updateForceShowNavBarSettings();
            }
        });
        this.mForceShowNavigationBarEnabled = this.mForceShowNavBarSettingsObserver.isEnabled();
        android.os.Handler handler2 = this.mHandler;
        final com.android.internal.policy.ForceShowNavBarSettingsObserver forceShowNavBarSettingsObserver = this.mForceShowNavBarSettingsObserver;
        java.util.Objects.requireNonNull(forceShowNavBarSettingsObserver);
        handler2.post(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                forceShowNavBarSettingsObserver.register();
            }
        });
    }

    /* renamed from: com.android.server.wm.DisplayPolicy$1, reason: invalid class name */
    class AnonymousClass1 implements com.android.server.wm.SystemGesturesPointerEventListener.Callbacks {
        private static final long MOUSE_GESTURE_DELAY_MS = 500;
        private java.lang.Runnable mOnSwipeFromLeft = new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayPolicy.AnonymousClass1.this.onSwipeFromLeft();
            }
        };
        private java.lang.Runnable mOnSwipeFromTop = new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayPolicy.AnonymousClass1.this.onSwipeFromTop();
            }
        };
        private java.lang.Runnable mOnSwipeFromRight = new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$1$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayPolicy.AnonymousClass1.this.onSwipeFromRight();
            }
        };
        private java.lang.Runnable mOnSwipeFromBottom = new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$1$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayPolicy.AnonymousClass1.this.onSwipeFromBottom();
            }
        };

        AnonymousClass1() {
        }

        private android.graphics.Insets getControllableInsets(com.android.server.wm.WindowState windowState) {
            if (windowState == null) {
                return android.graphics.Insets.NONE;
            }
            com.android.server.wm.InsetsSourceProvider controllableInsetProvider = windowState.getControllableInsetProvider();
            if (controllableInsetProvider == null) {
                return android.graphics.Insets.NONE;
            }
            return controllableInsetProvider.getSource().calculateInsets(windowState.getBounds(), true);
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onSwipeFromTop() {
            synchronized (com.android.server.wm.DisplayPolicy.this.mLock) {
                com.android.server.wm.DisplayPolicy.this.requestTransientBars(com.android.server.wm.DisplayPolicy.this.mTopGestureHost, getControllableInsets(com.android.server.wm.DisplayPolicy.this.mTopGestureHost).top > 0);
            }
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onSwipeFromBottom() {
            synchronized (com.android.server.wm.DisplayPolicy.this.mLock) {
                com.android.server.wm.DisplayPolicy.this.requestTransientBars(com.android.server.wm.DisplayPolicy.this.mBottomGestureHost, getControllableInsets(com.android.server.wm.DisplayPolicy.this.mBottomGestureHost).bottom > 0);
            }
        }

        private boolean allowsSideSwipe(android.graphics.Region region) {
            return com.android.server.wm.DisplayPolicy.this.mNavigationBarAlwaysShowOnSideGesture && !com.android.server.wm.DisplayPolicy.this.mSystemGestures.currentGestureStartedInRegion(region);
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onSwipeFromRight() {
            android.graphics.Region obtain = android.graphics.Region.obtain();
            synchronized (com.android.server.wm.DisplayPolicy.this.mLock) {
                try {
                    com.android.server.wm.DisplayPolicy.this.mDisplayContent.calculateSystemGestureExclusion(obtain, null);
                    boolean z = getControllableInsets(com.android.server.wm.DisplayPolicy.this.mRightGestureHost).right > 0;
                    if (z || allowsSideSwipe(obtain)) {
                        com.android.server.wm.DisplayPolicy.this.requestTransientBars(com.android.server.wm.DisplayPolicy.this.mRightGestureHost, z);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            obtain.recycle();
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onSwipeFromLeft() {
            android.graphics.Region obtain = android.graphics.Region.obtain();
            synchronized (com.android.server.wm.DisplayPolicy.this.mLock) {
                try {
                    com.android.server.wm.DisplayPolicy.this.mDisplayContent.calculateSystemGestureExclusion(obtain, null);
                    boolean z = getControllableInsets(com.android.server.wm.DisplayPolicy.this.mLeftGestureHost).left > 0;
                    if (z || allowsSideSwipe(obtain)) {
                        com.android.server.wm.DisplayPolicy.this.requestTransientBars(com.android.server.wm.DisplayPolicy.this.mLeftGestureHost, z);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            obtain.recycle();
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onFling(int i) {
            if (com.android.server.wm.DisplayPolicy.this.mService.mPowerManagerInternal != null) {
                com.android.server.wm.DisplayPolicy.this.mService.mPowerManagerInternal.setPowerBoost(0, i);
            }
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onDebug() {
        }

        private com.android.server.wm.WindowOrientationListener getOrientationListener() {
            com.android.server.wm.DisplayRotation displayRotation = com.android.server.wm.DisplayPolicy.this.mDisplayContent.getDisplayRotation();
            if (displayRotation != null) {
                return displayRotation.getOrientationListener();
            }
            return null;
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onDown() {
            com.android.server.wm.WindowOrientationListener orientationListener = getOrientationListener();
            if (orientationListener != null) {
                orientationListener.onTouchStart();
            }
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onUpOrCancel() {
            com.android.server.wm.WindowOrientationListener orientationListener = getOrientationListener();
            if (orientationListener != null) {
                orientationListener.onTouchEnd();
            }
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onMouseHoverAtLeft() {
            com.android.server.wm.DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromLeft);
            com.android.server.wm.DisplayPolicy.this.mHandler.postDelayed(this.mOnSwipeFromLeft, 500L);
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onMouseHoverAtTop() {
            com.android.server.wm.DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromTop);
            com.android.server.wm.DisplayPolicy.this.mHandler.postDelayed(this.mOnSwipeFromTop, 500L);
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onMouseHoverAtRight() {
            com.android.server.wm.DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromRight);
            com.android.server.wm.DisplayPolicy.this.mHandler.postDelayed(this.mOnSwipeFromRight, 500L);
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onMouseHoverAtBottom() {
            com.android.server.wm.DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromBottom);
            com.android.server.wm.DisplayPolicy.this.mHandler.postDelayed(this.mOnSwipeFromBottom, 500L);
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onMouseLeaveFromLeft() {
            com.android.server.wm.DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromLeft);
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onMouseLeaveFromTop() {
            com.android.server.wm.DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromTop);
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onMouseLeaveFromRight() {
            com.android.server.wm.DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromRight);
        }

        @Override // com.android.server.wm.SystemGesturesPointerEventListener.Callbacks
        public void onMouseLeaveFromBottom() {
            com.android.server.wm.DisplayPolicy.this.mHandler.removeCallbacks(this.mOnSwipeFromBottom);
        }
    }

    /* renamed from: com.android.server.wm.DisplayPolicy$2, reason: invalid class name */
    class AnonymousClass2 extends com.android.server.wm.WindowManagerInternal.AppTransitionListener {
        private java.lang.Runnable mAppTransitionCancelled;
        private java.lang.Runnable mAppTransitionFinished;
        private java.lang.Runnable mAppTransitionPending;
        final /* synthetic */ int val$displayId;

        AnonymousClass2(int i) {
            this.val$displayId = i;
            final int i2 = this.val$displayId;
            this.mAppTransitionPending = new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.DisplayPolicy.AnonymousClass2.this.lambda$$0(i2);
                }
            };
            final int i3 = this.val$displayId;
            this.mAppTransitionCancelled = new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$2$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.DisplayPolicy.AnonymousClass2.this.lambda$$1(i3);
                }
            };
            final int i4 = this.val$displayId;
            this.mAppTransitionFinished = new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$2$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.DisplayPolicy.AnonymousClass2.this.lambda$$2(i4);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$$0(int i) {
            com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = com.android.server.wm.DisplayPolicy.this.getStatusBarManagerInternal();
            if (statusBarManagerInternal != null) {
                statusBarManagerInternal.appTransitionPending(i);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$$1(int i) {
            com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = com.android.server.wm.DisplayPolicy.this.getStatusBarManagerInternal();
            if (statusBarManagerInternal != null) {
                statusBarManagerInternal.appTransitionCancelled(i);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$$2(int i) {
            com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = com.android.server.wm.DisplayPolicy.this.getStatusBarManagerInternal();
            if (statusBarManagerInternal != null) {
                statusBarManagerInternal.appTransitionFinished(i);
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public void onAppTransitionPendingLocked() {
            com.android.server.wm.DisplayPolicy.this.mHandler.post(this.mAppTransitionPending);
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public int onAppTransitionStartingLocked(final long j, final long j2) {
            com.android.server.wm.DisplayPolicy.this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.DisplayPolicy.AnonymousClass2.this.lambda$onAppTransitionStartingLocked$3(j, j2);
                }
            });
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAppTransitionStartingLocked$3(long j, long j2) {
            com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = com.android.server.wm.DisplayPolicy.this.getStatusBarManagerInternal();
            if (statusBarManagerInternal != null) {
                statusBarManagerInternal.appTransitionStarting(com.android.server.wm.DisplayPolicy.this.mContext.getDisplayId(), j, j2);
            }
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public void onAppTransitionCancelledLocked(boolean z) {
            com.android.server.wm.DisplayPolicy.this.mHandler.post(this.mAppTransitionCancelled);
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public void onAppTransitionFinishedLocked(android.os.IBinder iBinder) {
            com.android.server.wm.DisplayPolicy.this.mHandler.post(this.mAppTransitionFinished);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        synchronized (this.mLock) {
            try {
                onConfigurationChanged();
                if (!android.view.ViewRootImpl.CLIENT_TRANSIENT) {
                    this.mSystemGestures.onConfigurationChanged();
                }
                this.mDisplayContent.updateSystemGestureExclusion();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateForceShowNavBarSettings() {
        synchronized (this.mLock) {
            this.mForceShowNavigationBarEnabled = this.mForceShowNavBarSettingsObserver.isEnabled();
            updateSystemBarAttributes();
        }
    }

    void systemReady() {
        if (!android.view.ViewRootImpl.CLIENT_TRANSIENT) {
            this.mSystemGestures.systemReady();
        }
        if (this.mService.mPointerLocationEnabled) {
            setPointerLocationEnabled(true);
        }
    }

    public void updateSettings() {
        this.mForceNavbar = lineageos.providers.LineageSettings.System.getIntForUser(this.mContext.getContentResolver(), "force_show_navbar", 0, -2);
    }

    private int getDisplayId() {
        return this.mDisplayContent.getDisplayId();
    }

    public void setHdmiPlugged(boolean z) {
        setHdmiPlugged(z, false);
    }

    public void setHdmiPlugged(boolean z, boolean z2) {
        if (z2 || this.mHdmiPlugged != z) {
            this.mHdmiPlugged = z;
            this.mService.updateRotation(true, true);
            android.content.Intent intent = new android.content.Intent("android.intent.action.HDMI_PLUGGED");
            intent.addFlags(67108864);
            intent.putExtra("state", z);
            this.mContext.sendStickyBroadcastAsUser(intent, android.os.UserHandle.ALL);
        }
    }

    boolean isHdmiPlugged() {
        return this.mHdmiPlugged;
    }

    boolean isCarDockEnablesAccelerometer() {
        return this.mCarDockEnablesAccelerometer;
    }

    boolean isDeskDockEnablesAccelerometer() {
        return this.mDeskDockEnablesAccelerometer;
    }

    public void setPersistentVrModeEnabled(boolean z) {
        this.mPersistentVrModeEnabled = z;
    }

    public boolean isPersistentVrModeEnabled() {
        return this.mPersistentVrModeEnabled;
    }

    public void setDockMode(int i) {
        this.mDockMode = i;
    }

    public int getDockMode() {
        return this.mDockMode;
    }

    public boolean hasNavigationBar() {
        return this.mHasNavigationBar || this.mForceNavbar == 1;
    }

    public boolean hasStatusBar() {
        return this.mHasStatusBar;
    }

    boolean hasSideGestures() {
        return this.mHasNavigationBar && (this.mLeftGestureInset > 0 || this.mRightGestureInset > 0);
    }

    public boolean navigationBarCanMove() {
        return this.mNavigationBarCanMove;
    }

    public void setLidState(int i) {
        this.mLidState = i;
    }

    public int getLidState() {
        return this.mLidState;
    }

    public void setAwake(boolean z) {
        synchronized (this.mLock) {
            try {
                if (z == this.mAwake) {
                    return;
                }
                this.mAwake = z;
                if (this.mDisplayContent.isDefaultDisplay) {
                    if (z) {
                        this.mService.mAtmService.mVisibleDozeUiProcess = null;
                    } else if (this.mScreenOnFully && this.mNotificationShade != null) {
                        this.mService.mAtmService.mVisibleDozeUiProcess = this.mNotificationShade.getProcess();
                    }
                    this.mService.mAtmService.mKeyguardController.updateDeferTransitionForAod(this.mAwake);
                    if (!z) {
                        this.mDisplayContent.mWallpaperController.onDisplaySwitchFinished();
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public boolean isAwake() {
        return this.mAwake;
    }

    public boolean isScreenOnEarly() {
        return this.mScreenOnEarly;
    }

    public boolean isScreenOnFully() {
        return this.mScreenOnFully;
    }

    public boolean isKeyguardDrawComplete() {
        return this.mKeyguardDrawComplete;
    }

    public boolean isWindowManagerDrawComplete() {
        return this.mWindowManagerDrawComplete;
    }

    public boolean isForceShowNavigationBarEnabled() {
        return this.mForceShowNavigationBarEnabled;
    }

    public com.android.server.policy.WindowManagerPolicy.ScreenOnListener getScreenOnListener() {
        return this.mScreenOnListener;
    }

    boolean isRemoteInsetsControllerControllingSystemBars() {
        return this.mRemoteInsetsControllerControlsSystemBars;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setRemoteInsetsControllerControlsSystemBars(boolean z) {
        this.mRemoteInsetsControllerControlsSystemBars = z;
    }

    public void screenTurningOn(com.android.server.policy.WindowManagerPolicy.ScreenOnListener screenOnListener) {
        com.android.server.wm.WindowProcessController windowProcessController;
        synchronized (this.mLock) {
            try {
                this.mScreenOnEarly = true;
                this.mScreenOnFully = false;
                this.mKeyguardDrawComplete = false;
                this.mWindowManagerDrawComplete = false;
                this.mScreenOnListener = screenOnListener;
                if (this.mAwake || this.mNotificationShade == null) {
                    windowProcessController = null;
                } else {
                    windowProcessController = this.mNotificationShade.getProcess();
                    this.mService.mAtmService.mVisibleDozeUiProcess = windowProcessController;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (windowProcessController != null) {
            android.os.Trace.instant(32L, "screenTurnedOnWhileDozing");
            this.mService.mAtmService.setProcessAnimatingWhileDozing(windowProcessController);
        }
    }

    public void screenTurnedOn() {
        this.mDisplayContent.mWallpaperController.onDisplaySwitchFinished();
    }

    public void screenTurnedOff() {
        synchronized (this.mLock) {
            this.mScreenOnEarly = false;
            this.mScreenOnFully = false;
            this.mKeyguardDrawComplete = false;
            this.mWindowManagerDrawComplete = false;
            this.mScreenOnListener = null;
            this.mService.mAtmService.mVisibleDozeUiProcess = null;
        }
    }

    public boolean finishKeyguardDrawn() {
        synchronized (this.mLock) {
            try {
                if (!this.mScreenOnEarly || this.mKeyguardDrawComplete) {
                    return false;
                }
                this.mKeyguardDrawComplete = true;
                this.mWindowManagerDrawComplete = false;
                return true;
            } finally {
            }
        }
    }

    public boolean finishWindowsDrawn() {
        synchronized (this.mLock) {
            try {
                if (!this.mScreenOnEarly || this.mWindowManagerDrawComplete) {
                    return false;
                }
                this.mWindowManagerDrawComplete = true;
                return true;
            } finally {
            }
        }
    }

    public boolean finishScreenTurningOn() {
        synchronized (this.mLock) {
            try {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SCREEN_ON, -6228339285356824882L, 1023, null, java.lang.Boolean.valueOf(this.mAwake), java.lang.Boolean.valueOf(this.mScreenOnEarly), java.lang.Boolean.valueOf(this.mScreenOnFully), java.lang.Boolean.valueOf(this.mKeyguardDrawComplete), java.lang.Boolean.valueOf(this.mWindowManagerDrawComplete));
                if (this.mScreenOnFully || !this.mScreenOnEarly || !this.mWindowManagerDrawComplete || (this.mAwake && !this.mKeyguardDrawComplete)) {
                    return false;
                }
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SCREEN_ON, -6028033043540330282L, 0, null, null);
                this.mScreenOnListener = null;
                this.mScreenOnFully = true;
                return true;
            } finally {
            }
        }
    }

    public void adjustWindowParamsLw(com.android.server.wm.WindowState windowState, android.view.WindowManager.LayoutParams layoutParams) {
        switch (layoutParams.type) {
            case 1:
                if (layoutParams.isFullscreen() && windowState.mActivityRecord != null && windowState.mActivityRecord.fillsParent() && (layoutParams.privateFlags & 32768) != 0 && (layoutParams.getFitInsetsTypes() != 0 || ((layoutParams.privateFlags & 2048) != 0 && layoutParams.layoutInDisplayCutoutMode != 3))) {
                    throw new java.lang.IllegalArgumentException("Illegal attributes: Main activity window that isn't translucent trying to fit insets or display cutouts. attrs=" + layoutParams);
                }
                break;
            case 2005:
                if (layoutParams.hideTimeoutMilliseconds < 0 || layoutParams.hideTimeoutMilliseconds > 4100) {
                    layoutParams.hideTimeoutMilliseconds = 4100L;
                }
                layoutParams.hideTimeoutMilliseconds = this.mAccessibilityManager.getRecommendedTimeoutMillis((int) layoutParams.hideTimeoutMilliseconds, 2);
                layoutParams.flags |= 16;
                break;
            case 2006:
            case 2015:
                layoutParams.flags |= 24;
                layoutParams.flags &= -262145;
                break;
            case 2013:
                layoutParams.layoutInDisplayCutoutMode = 3;
                break;
        }
        if (android.view.WindowManager.LayoutParams.isSystemAlertWindowType(layoutParams.type)) {
            float f = this.mService.mMaximumObscuringOpacityForTouch;
            if (layoutParams.alpha > f && (layoutParams.flags & 16) != 0 && !windowState.isTrustedOverlay()) {
                android.util.Slog.w(TAG, java.lang.String.format("App %s has a system alert window (type = %d) with FLAG_NOT_TOUCHABLE and LayoutParams.alpha = %.2f > %.2f, setting alpha to %.2f to let touches pass through (if this is isn't desirable, remove flag FLAG_NOT_TOUCHABLE).", layoutParams.packageName, java.lang.Integer.valueOf(layoutParams.type), java.lang.Float.valueOf(layoutParams.alpha), java.lang.Float.valueOf(f), java.lang.Float.valueOf(f)));
                layoutParams.alpha = f;
                windowState.mWinAnimator.mAlpha = f;
            }
        }
        if (!windowState.mSession.mCanSetUnrestrictedGestureExclusion) {
            layoutParams.privateFlags &= -33;
        }
    }

    public void setDropInputModePolicy(com.android.server.wm.WindowState windowState, android.view.WindowManager.LayoutParams layoutParams) {
        if (layoutParams.type == 2005 && (layoutParams.privateFlags & 536870912) == 0) {
            this.mService.mTransactionFactory.get().setDropInputMode(windowState.getSurfaceControl(), 1).apply();
        }
    }

    int validateAddingWindowLw(android.view.WindowManager.LayoutParams layoutParams, int i, int i2) {
        if ((layoutParams.privateFlags & 536870912) != 0) {
            this.mContext.enforcePermission("android.permission.INTERNAL_SYSTEM_WINDOW", i, i2, "DisplayPolicy");
        }
        if ((layoutParams.privateFlags & Integer.MIN_VALUE) != 0) {
            com.android.server.wm.ActivityTaskManagerService.enforceTaskPermission("DisplayPolicy");
        }
        switch (layoutParams.type) {
            case 2000:
                this.mContext.enforcePermission("android.permission.STATUS_BAR_SERVICE", i, i2, "DisplayPolicy");
                if (this.mStatusBar != null && this.mStatusBar.isAlive()) {
                    return -7;
                }
                break;
            case 2014:
                return -10;
            case 2017:
            case 2033:
            case 2041:
                this.mContext.enforcePermission("android.permission.STATUS_BAR_SERVICE", i, i2, "DisplayPolicy");
                break;
            case 2019:
                this.mContext.enforcePermission("android.permission.STATUS_BAR_SERVICE", i, i2, "DisplayPolicy");
                if (this.mNavigationBar != null && this.mNavigationBar.isAlive()) {
                    return -7;
                }
                break;
            case 2024:
                this.mContext.enforcePermission("android.permission.STATUS_BAR_SERVICE", i, i2, "DisplayPolicy");
                break;
            case 2040:
                this.mContext.enforcePermission("android.permission.STATUS_BAR_SERVICE", i, i2, "DisplayPolicy");
                if (this.mNotificationShade != null && this.mNotificationShade.isAlive()) {
                    return -7;
                }
                break;
        }
        if (layoutParams.providedInsets != null && !this.mService.mAtmService.isCallerRecents(i2)) {
            this.mContext.enforcePermission("android.permission.STATUS_BAR_SERVICE", i, i2, "DisplayPolicy");
            return 0;
        }
        return 0;
    }

    void addWindowLw(com.android.server.wm.WindowState windowState, android.view.WindowManager.LayoutParams layoutParams) {
        android.util.SparseArray<com.android.internal.util.function.TriFunction<com.android.server.wm.DisplayFrames, com.android.server.wm.WindowContainer, android.graphics.Rect, java.lang.Integer>> sparseArray;
        switch (layoutParams.type) {
            case 2000:
                this.mStatusBar = windowState;
                break;
            case 2019:
                this.mNavigationBar = windowState;
                break;
            case 2040:
                this.mNotificationShade = windowState;
                break;
        }
        if ((layoutParams.privateFlags & 131072) != 0) {
            this.mImmersiveConfirmationWindowExists = true;
        }
        if (layoutParams.providedInsets != null) {
            for (int length = layoutParams.providedInsets.length - 1; length >= 0; length--) {
                android.view.InsetsFrameProvider insetsFrameProvider = layoutParams.providedInsets[length];
                com.android.internal.util.function.TriFunction<com.android.server.wm.DisplayFrames, com.android.server.wm.WindowContainer, android.graphics.Rect, java.lang.Integer> frameProvider = getFrameProvider(windowState, length, -1);
                android.view.InsetsFrameProvider.InsetsSizeOverride[] insetsSizeOverrides = insetsFrameProvider.getInsetsSizeOverrides();
                if (insetsSizeOverrides != null) {
                    sparseArray = new android.util.SparseArray<>();
                    for (int length2 = insetsSizeOverrides.length - 1; length2 >= 0; length2--) {
                        sparseArray.put(insetsSizeOverrides[length2].getWindowType(), getFrameProvider(windowState, length, length2));
                    }
                } else {
                    sparseArray = null;
                }
                com.android.server.wm.InsetsSourceProvider orCreateSourceProvider = this.mDisplayContent.getInsetsStateController().getOrCreateSourceProvider(insetsFrameProvider.getId(), insetsFrameProvider.getType());
                orCreateSourceProvider.getSource().setFlags(insetsFrameProvider.getFlags());
                orCreateSourceProvider.setWindowContainer(windowState, frameProvider, sparseArray);
                this.mInsetsSourceWindowsExceptIme.add(windowState);
            }
        }
    }

    private static com.android.internal.util.function.TriFunction<com.android.server.wm.DisplayFrames, com.android.server.wm.WindowContainer, android.graphics.Rect, java.lang.Integer> getFrameProvider(final com.android.server.wm.WindowState windowState, final int i, final int i2) {
        return new com.android.internal.util.function.TriFunction() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda0
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                java.lang.Integer lambda$getFrameProvider$1;
                lambda$getFrameProvider$1 = com.android.server.wm.DisplayPolicy.lambda$getFrameProvider$1(com.android.server.wm.WindowState.this, i, i2, (com.android.server.wm.DisplayFrames) obj, (com.android.server.wm.WindowContainer) obj2, (android.graphics.Rect) obj3);
                return lambda$getFrameProvider$1;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$getFrameProvider$1(com.android.server.wm.WindowState windowState, int i, int i2, com.android.server.wm.DisplayFrames displayFrames, com.android.server.wm.WindowContainer windowContainer, android.graphics.Rect rect) {
        android.graphics.Insets insetsSize;
        android.view.WindowManager.LayoutParams forRotation = windowState.mAttrs.forRotation(displayFrames.mRotation);
        android.view.InsetsFrameProvider insetsFrameProvider = forRotation.providedInsets[i];
        android.graphics.Rect rect2 = displayFrames.mUnrestricted;
        android.graphics.Rect rect3 = displayFrames.mDisplayCutoutSafe;
        boolean z = false;
        switch (insetsFrameProvider.getSource()) {
            case 0:
                rect.set(rect2);
                break;
            case 1:
                rect.set(windowContainer.getBounds());
                break;
            case 2:
                if ((forRotation.privateFlags & 4096) != 0) {
                    z = true;
                    break;
                }
                break;
            case 3:
                rect.set(insetsFrameProvider.getArbitraryRectangle());
                break;
        }
        if (i2 == -1) {
            insetsSize = insetsFrameProvider.getInsetsSize();
        } else {
            insetsSize = insetsFrameProvider.getInsetsSizeOverrides()[i2].getInsetsSize();
        }
        if (insetsFrameProvider.getMinimalInsetsSizeInDisplayCutoutSafe() != null) {
            sTmpRect2.set(rect);
        }
        calculateInsetsFrame(rect, insetsSize);
        if (z && insetsSize != null) {
            android.view.WindowLayout.extendFrameByCutout(rect3, rect2, rect, sTmpRect);
        }
        if (insetsFrameProvider.getMinimalInsetsSizeInDisplayCutoutSafe() != null) {
            calculateInsetsFrame(sTmpRect2, insetsFrameProvider.getMinimalInsetsSizeInDisplayCutoutSafe());
            android.view.WindowLayout.extendFrameByCutout(rect3, rect2, sTmpRect2, sTmpRect);
            if (sTmpRect2.contains(rect)) {
                rect.set(sTmpRect2);
            }
        }
        return java.lang.Integer.valueOf(insetsFrameProvider.getFlags());
    }

    private static void calculateInsetsFrame(android.graphics.Rect rect, android.graphics.Insets insets) {
        if (insets == null) {
            return;
        }
        if (insets.left != 0) {
            rect.right = rect.left + insets.left;
            return;
        }
        if (insets.top != 0) {
            rect.bottom = rect.top + insets.top;
            return;
        }
        if (insets.right != 0) {
            rect.left = rect.right - insets.right;
        } else if (insets.bottom != 0) {
            rect.top = rect.bottom - insets.bottom;
        } else {
            rect.setEmpty();
        }
    }

    com.android.internal.util.function.TriFunction<com.android.server.wm.DisplayFrames, com.android.server.wm.WindowContainer, android.graphics.Rect, java.lang.Integer> getImeSourceFrameProvider() {
        return new com.android.internal.util.function.TriFunction() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda14
            public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                java.lang.Integer lambda$getImeSourceFrameProvider$2;
                lambda$getImeSourceFrameProvider$2 = com.android.server.wm.DisplayPolicy.this.lambda$getImeSourceFrameProvider$2((com.android.server.wm.DisplayFrames) obj, (com.android.server.wm.WindowContainer) obj2, (android.graphics.Rect) obj3);
                return lambda$getImeSourceFrameProvider$2;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.Integer lambda$getImeSourceFrameProvider$2(com.android.server.wm.DisplayFrames displayFrames, com.android.server.wm.WindowContainer windowContainer, android.graphics.Rect rect) {
        com.android.server.wm.WindowState asWindowState = windowContainer.asWindowState();
        if (asWindowState == null) {
            throw new java.lang.IllegalArgumentException("IME insets must be provided by a window.");
        }
        rect.inset(asWindowState.mGivenContentInsets);
        return 0;
    }

    void removeWindowLw(com.android.server.wm.WindowState windowState) {
        if (this.mStatusBar == windowState) {
            this.mStatusBar = null;
        } else if (this.mNavigationBar == windowState) {
            this.mNavigationBar = null;
        } else if (this.mNotificationShade == windowState) {
            this.mNotificationShade = null;
        }
        if (this.mLastFocusedWindow == windowState) {
            this.mLastFocusedWindow = null;
        }
        if (windowState.hasInsetsSourceProvider()) {
            android.util.SparseArray<com.android.server.wm.InsetsSourceProvider> insetsSourceProviders = windowState.getInsetsSourceProviders();
            com.android.server.wm.InsetsStateController insetsStateController = this.mDisplayContent.getInsetsStateController();
            for (int size = insetsSourceProviders.size() - 1; size >= 0; size--) {
                com.android.server.wm.InsetsSourceProvider valueAt = insetsSourceProviders.valueAt(size);
                valueAt.setWindowContainer(null, null, null);
                insetsStateController.removeSourceProvider(valueAt.getSource().getId());
            }
        }
        this.mInsetsSourceWindowsExceptIme.remove(windowState);
        if ((windowState.mAttrs.privateFlags & 131072) != 0) {
            this.mImmersiveConfirmationWindowExists = false;
        }
    }

    com.android.server.wm.WindowState getStatusBar() {
        return this.mStatusBar;
    }

    com.android.server.wm.WindowState getNotificationShade() {
        return this.mNotificationShade;
    }

    com.android.server.wm.WindowState getNavigationBar() {
        return this.mNavigationBar;
    }

    boolean isImmersiveMode() {
        return this.mIsImmersiveMode;
    }

    int selectAnimation(com.android.server.wm.WindowState windowState, int i) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -7427596081878257508L, 4, null, java.lang.String.valueOf(windowState), java.lang.Long.valueOf(i));
        if (i == 5 && windowState.hasAppShownWindows()) {
            if (windowState.isActivityTypeHome()) {
                return -1;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -6269658847003264525L, 0, null, null);
            return android.R.anim.app_starting_exit;
        }
        return 0;
    }

    public boolean areSystemBarsForcedConsumedLw() {
        return false;
    }

    void simulateLayoutDisplay(com.android.server.wm.DisplayFrames displayFrames) {
        sTmpClientFrames.attachedFrame = null;
        for (int size = this.mInsetsSourceWindowsExceptIme.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowState valueAt = this.mInsetsSourceWindowsExceptIme.valueAt(size);
            this.mWindowLayout.computeFrames(valueAt.mAttrs.forRotation(displayFrames.mRotation), displayFrames.mInsetsState, displayFrames.mDisplayCutoutSafe, displayFrames.mUnrestricted, valueAt.getWindowingMode(), -1, -1, valueAt.getRequestedVisibleTypes(), valueAt.mGlobalScale, sTmpClientFrames);
            android.util.SparseArray<com.android.server.wm.InsetsSourceProvider> insetsSourceProviders = valueAt.getInsetsSourceProviders();
            android.view.InsetsState insetsState = displayFrames.mInsetsState;
            for (int size2 = insetsSourceProviders.size() - 1; size2 >= 0; size2--) {
                insetsState.addSource(insetsSourceProviders.valueAt(size2).createSimulatedSource(displayFrames, sTmpClientFrames.frame));
            }
        }
    }

    void onDisplayInfoChanged(android.view.DisplayInfo displayInfo) {
        if (!android.view.ViewRootImpl.CLIENT_TRANSIENT) {
            this.mSystemGestures.onDisplayInfoChanged(displayInfo);
        }
    }

    public void layoutWindowLw(com.android.server.wm.WindowState windowState, com.android.server.wm.WindowState windowState2, com.android.server.wm.DisplayFrames displayFrames) {
        int i;
        int i2;
        if (!windowState.skipLayout()) {
            com.android.server.wm.DisplayFrames displayFrames2 = windowState.getDisplayFrames(displayFrames);
            android.view.WindowManager.LayoutParams forRotation = windowState.mAttrs.forRotation(displayFrames2.mRotation);
            sTmpClientFrames.attachedFrame = windowState2 != null ? windowState2.getFrame() : null;
            boolean z = forRotation == windowState.mAttrs;
            if (z) {
                i = windowState.mRequestedWidth;
            } else {
                i = -1;
            }
            if (z) {
                i2 = windowState.mRequestedHeight;
            } else {
                i2 = -1;
            }
            this.mWindowLayout.computeFrames(forRotation, windowState.getInsetsState(), displayFrames2.mDisplayCutoutSafe, windowState.getBounds(), windowState.getWindowingMode(), i, i2, windowState.getRequestedVisibleTypes(), windowState.mGlobalScale, sTmpClientFrames);
            windowState.setFrames(sTmpClientFrames, windowState.mRequestedWidth, windowState.mRequestedHeight);
        }
    }

    com.android.server.wm.WindowState getTopFullscreenOpaqueWindow() {
        return this.mTopFullscreenOpaqueWindowState;
    }

    boolean isTopLayoutFullscreen() {
        return this.mTopIsFullscreen;
    }

    public void beginPostLayoutPolicyLw() {
        this.mLeftGestureHost = null;
        this.mTopGestureHost = null;
        this.mRightGestureHost = null;
        this.mBottomGestureHost = null;
        this.mTopFullscreenOpaqueWindowState = null;
        this.mNavBarColorWindowCandidate = null;
        this.mNavBarBackgroundWindowCandidate = null;
        this.mStatusBarAppearanceRegionList.clear();
        this.mLetterboxDetails.clear();
        this.mStatusBarBackgroundWindows.clear();
        this.mStatusBarColorCheckedBounds.setEmpty();
        this.mStatusBarBackgroundCheckedBounds.setEmpty();
        this.mSystemBarColorApps.clear();
        this.mAllowLockscreenWhenOn = false;
        this.mShowingDream = false;
        this.mIsFreeformWindowOverlappingWithNavBar = false;
        this.mForciblyShownTypes = 0;
        this.mImeInsetsConsumed = false;
    }

    public void applyPostLayoutPolicyLw(com.android.server.wm.WindowState windowState, android.view.WindowManager.LayoutParams layoutParams, com.android.server.wm.WindowState windowState2, com.android.server.wm.WindowState windowState3) {
        com.android.internal.statusbar.LetterboxDetails letterboxDetails;
        if (layoutParams.type == 2019) {
            this.mNavigationBarPosition = navigationBarPosition(this.mDisplayContent.mDisplayFrames.mRotation);
        }
        boolean canAffectSystemUiFlags = windowState.canAffectSystemUiFlags();
        applyKeyguardPolicy(windowState, windowState3);
        if (!this.mIsFreeformWindowOverlappingWithNavBar && windowState.inFreeformWindowingMode() && windowState.mActivityRecord != null && isOverlappingWithNavBar(windowState)) {
            this.mIsFreeformWindowOverlappingWithNavBar = true;
        }
        if (windowState.hasInsetsSourceProvider()) {
            android.util.SparseArray<com.android.server.wm.InsetsSourceProvider> insetsSourceProviders = windowState.getInsetsSourceProviders();
            android.graphics.Rect bounds = windowState.getBounds();
            for (int size = insetsSourceProviders.size() - 1; size >= 0; size--) {
                android.view.InsetsSource source = insetsSourceProviders.valueAt(size).getSource();
                if ((source.getType() & (android.view.WindowInsets.Type.systemGestures() | android.view.WindowInsets.Type.mandatorySystemGestures())) != 0 && (this.mLeftGestureHost == null || this.mTopGestureHost == null || this.mRightGestureHost == null || this.mBottomGestureHost == null)) {
                    android.graphics.Insets calculateInsets = source.calculateInsets(bounds, false);
                    if (this.mLeftGestureHost == null && calculateInsets.left > 0) {
                        this.mLeftGestureHost = windowState;
                    }
                    if (this.mTopGestureHost == null && calculateInsets.top > 0) {
                        this.mTopGestureHost = windowState;
                    }
                    if (this.mRightGestureHost == null && calculateInsets.right > 0) {
                        this.mRightGestureHost = windowState;
                    }
                    if (this.mBottomGestureHost == null && calculateInsets.bottom > 0) {
                        this.mBottomGestureHost = windowState;
                    }
                }
            }
        }
        if (windowState.mSession.mCanForceShowingInsets) {
            this.mForciblyShownTypes |= windowState.mAttrs.forciblyShownTypes;
        }
        if (windowState.mImeInsetsConsumed != this.mImeInsetsConsumed) {
            windowState.mImeInsetsConsumed = this.mImeInsetsConsumed;
            com.android.server.wm.WindowState windowState4 = this.mDisplayContent.mInputMethodWindow;
            if (windowState.isReadyToDispatchInsetsState() && windowState4 != null && windowState4.isVisible()) {
                windowState.notifyInsetsChanged();
            }
        }
        if ((layoutParams.privateFlags & 33554432) != 0 && windowState.isVisible()) {
            this.mImeInsetsConsumed = true;
        }
        if (!canAffectSystemUiFlags) {
            return;
        }
        boolean z = layoutParams.type >= 1 && layoutParams.type < 2000;
        if (this.mTopFullscreenOpaqueWindowState == null) {
            int i = layoutParams.flags;
            if (windowState.isDreamWindow() && (!this.mDreamingLockscreen || (windowState.isVisible() && windowState.hasDrawn()))) {
                this.mShowingDream = true;
                z = true;
            }
            if (z && windowState2 == null && layoutParams.isFullscreen() && (i & 1) != 0) {
                this.mAllowLockscreenWhenOn = true;
            }
        }
        if ((z && windowState2 == null && layoutParams.isFullscreen()) || layoutParams.type == 2031) {
            boolean z2 = layoutParams.type == 3 && windowState.mAnimatingExit;
            if (this.mTopFullscreenOpaqueWindowState == null && !z2) {
                this.mTopFullscreenOpaqueWindowState = windowState;
            }
            if (this.mStatusBar != null && sTmpRect.setIntersect(windowState.getFrame(), this.mStatusBar.getFrame()) && !this.mStatusBarBackgroundCheckedBounds.contains(sTmpRect)) {
                this.mStatusBarBackgroundWindows.add(windowState);
                this.mStatusBarBackgroundCheckedBounds.union(sTmpRect);
                if (!this.mStatusBarColorCheckedBounds.contains(sTmpRect)) {
                    this.mStatusBarAppearanceRegionList.add(new com.android.internal.view.AppearanceRegion(windowState.mAttrs.insetsFlags.appearance & 8, new android.graphics.Rect(windowState.getFrame())));
                    this.mStatusBarColorCheckedBounds.union(sTmpRect);
                    addSystemBarColorApp(windowState);
                }
            }
            if (isOverlappingWithNavBar(windowState)) {
                if (this.mNavBarColorWindowCandidate == null) {
                    this.mNavBarColorWindowCandidate = windowState;
                    addSystemBarColorApp(windowState);
                }
                if (this.mNavBarBackgroundWindowCandidate == null) {
                    this.mNavBarBackgroundWindowCandidate = windowState;
                }
            }
            com.android.server.wm.ActivityRecord activityRecord = windowState.getActivityRecord();
            if (activityRecord != null && (letterboxDetails = activityRecord.mLetterboxUiController.getLetterboxDetails()) != null) {
                this.mLetterboxDetails.add(letterboxDetails);
                return;
            }
            return;
        }
        if (windowState.isDimming()) {
            if (this.mStatusBar != null) {
                if (windowState.mToken.getWindowLayerFromType() < this.mStatusBar.mToken.getWindowLayerFromType() && addStatusBarAppearanceRegionsForDimmingWindow(windowState.mAttrs.insetsFlags.appearance & 8, this.mStatusBar.getFrame(), windowState.getBounds(), windowState.getFrame())) {
                    addSystemBarColorApp(windowState);
                }
            }
            if (isOverlappingWithNavBar(windowState) && this.mNavBarColorWindowCandidate == null) {
                this.mNavBarColorWindowCandidate = windowState;
                addSystemBarColorApp(windowState);
                return;
            }
            return;
        }
        if (z && windowState2 == null) {
            if ((this.mNavBarColorWindowCandidate == null || this.mNavBarBackgroundWindowCandidate == null) && windowState.getFrame().contains(getBarContentFrameForWindow(windowState, android.view.WindowInsets.Type.navigationBars()))) {
                if (this.mNavBarColorWindowCandidate == null) {
                    this.mNavBarColorWindowCandidate = windowState;
                    addSystemBarColorApp(windowState);
                }
                if (this.mNavBarBackgroundWindowCandidate == null) {
                    this.mNavBarBackgroundWindowCandidate = windowState;
                }
            }
        }
    }

    private boolean addStatusBarAppearanceRegionsForDimmingWindow(int i, android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3) {
        if (!sTmpRect.setIntersect(rect2, rect) || this.mStatusBarColorCheckedBounds.contains(sTmpRect)) {
            return false;
        }
        if (i == 0 || !sTmpRect2.setIntersect(rect3, rect)) {
            this.mStatusBarAppearanceRegionList.add(new com.android.internal.view.AppearanceRegion(0, new android.graphics.Rect(rect2)));
            this.mStatusBarColorCheckedBounds.union(sTmpRect);
            return true;
        }
        this.mStatusBarAppearanceRegionList.add(new com.android.internal.view.AppearanceRegion(i, new android.graphics.Rect(rect3)));
        if (!sTmpRect.equals(sTmpRect2) && sTmpRect.height() == sTmpRect2.height()) {
            if (sTmpRect.left != sTmpRect2.left) {
                this.mStatusBarAppearanceRegionList.add(new com.android.internal.view.AppearanceRegion(0, new android.graphics.Rect(rect2.left, rect2.top, sTmpRect2.left, rect2.bottom)));
            }
            if (sTmpRect.right != sTmpRect2.right) {
                this.mStatusBarAppearanceRegionList.add(new com.android.internal.view.AppearanceRegion(0, new android.graphics.Rect(sTmpRect2.right, rect2.top, rect2.right, rect2.bottom)));
            }
        }
        this.mStatusBarColorCheckedBounds.union(sTmpRect);
        return true;
    }

    private void addSystemBarColorApp(com.android.server.wm.WindowState windowState) {
        com.android.server.wm.ActivityRecord activityRecord = windowState.mActivityRecord;
        if (activityRecord != null) {
            this.mSystemBarColorApps.add(activityRecord);
        }
    }

    public void finishPostLayoutPolicyLw() {
        if (!this.mShowingDream) {
            this.mDreamingLockscreen = this.mService.mPolicy.isKeyguardShowingAndNotOccluded();
        }
        updateSystemBarAttributes();
        if (this.mShowingDream != this.mLastShowingDream) {
            this.mLastShowingDream = this.mShowingDream;
            this.mDisplayContent.notifyKeyguardFlagsChanged();
        }
        this.mService.mPolicy.setAllowLockscreenWhenOn(getDisplayId(), this.mAllowLockscreenWhenOn);
    }

    boolean areTypesForciblyShownTransiently(int i) {
        return (this.mForciblyShownTypes & i) == i;
    }

    private void applyKeyguardPolicy(com.android.server.wm.WindowState windowState, com.android.server.wm.WindowState windowState2) {
        if (windowState.canBeHiddenByKeyguard()) {
            boolean shouldBeHiddenByKeyguard = shouldBeHiddenByKeyguard(windowState, windowState2);
            if (windowState.mIsImWindow) {
                this.mDisplayContent.getInsetsStateController().getImeSourceProvider().setFrozen(shouldBeHiddenByKeyguard);
            }
            if (shouldBeHiddenByKeyguard) {
                windowState.hide(false, true);
            } else {
                windowState.show(false, true);
            }
        }
    }

    private boolean shouldBeHiddenByKeyguard(com.android.server.wm.WindowState windowState, com.android.server.wm.WindowState windowState2) {
        boolean z = false;
        if (!this.mDisplayContent.isDefaultDisplay || !isKeyguardShowing()) {
            return false;
        }
        if (windowState2 != null && windowState2.isVisible() && windowState.mIsImWindow && (windowState2.canShowWhenLocked() || !windowState2.canBeHiddenByKeyguard())) {
            return false;
        }
        if (isKeyguardOccluded() && (windowState.canShowWhenLocked() || (windowState.mAttrs.privateFlags & 256) != 0)) {
            z = true;
        }
        return !z;
    }

    boolean topAppHidesSystemBar(int i) {
        if (this.mTopFullscreenOpaqueWindowState == null || getInsetsPolicy().areTypesForciblyShowing(i)) {
            return false;
        }
        return !this.mTopFullscreenOpaqueWindowState.isRequestedVisible(i);
    }

    public void switchUser() {
        updateCurrentUserResources();
        updateForceShowNavBarSettings();
    }

    void onOverlayChanged() {
        updateCurrentUserResources();
        this.mDisplayContent.requestDisplayUpdate(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayPolicy.this.lambda$onOverlayChanged$3();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onOverlayChanged$3() {
        onConfigurationChanged();
        if (!android.view.ViewRootImpl.CLIENT_TRANSIENT) {
            this.mSystemGestures.onConfigurationChanged();
        }
    }

    public void onConfigurationChanged() {
        android.content.res.Resources currentUserResources = getCurrentUserResources();
        this.mNavBarOpacityMode = currentUserResources.getInteger(android.R.integer.config_multiuserMaxRunningUsers);
        this.mLeftGestureInset = this.mGestureNavigationSettingsObserver.getLeftSensitivity(currentUserResources);
        this.mRightGestureInset = this.mGestureNavigationSettingsObserver.getRightSensitivity(currentUserResources);
        this.mNavigationBarAlwaysShowOnSideGesture = currentUserResources.getBoolean(android.R.bool.config_mms_content_disposition_support);
        this.mRemoteInsetsControllerControlsSystemBars = currentUserResources.getBoolean(android.R.bool.config_remoteInsetsControllerControlsSystemBars);
        updateConfigurationAndScreenSizeDependentBehaviors();
        boolean z = currentUserResources.getBoolean(android.R.bool.config_attachNavBarToAppDuringTransition);
        if (this.mShouldAttachNavBarToAppDuringTransition != z) {
            this.mShouldAttachNavBarToAppDuringTransition = z;
        }
    }

    void updateConfigurationAndScreenSizeDependentBehaviors() {
        android.content.res.Resources currentUserResources = getCurrentUserResources();
        this.mNavigationBarCanMove = this.mDisplayContent.mBaseDisplayWidth != this.mDisplayContent.mBaseDisplayHeight && currentUserResources.getBoolean(android.R.bool.config_mobile_data_capable);
        this.mDisplayContent.getDisplayRotation().updateUserDependentConfiguration(currentUserResources);
    }

    private void updateCurrentUserResources() {
        int currentUserId = this.mService.mAmInternal.getCurrentUserId();
        android.content.Context systemUiContext = getSystemUiContext();
        if (currentUserId == 0) {
            this.mCurrentUserResources = systemUiContext.getResources();
        } else {
            android.app.LoadedApk packageInfo = android.app.ActivityThread.currentActivityThread().getPackageInfo(systemUiContext.getPackageName(), (android.content.res.CompatibilityInfo) null, 0, currentUserId);
            this.mCurrentUserResources = android.app.ResourcesManager.getInstance().getResources(systemUiContext.getWindowContextToken(), packageInfo.getResDir(), (java.lang.String[]) null, packageInfo.getOverlayDirs(), packageInfo.getOverlayPaths(), packageInfo.getApplicationInfo().sharedLibraryFiles, java.lang.Integer.valueOf(this.mDisplayContent.getDisplayId()), (android.content.res.Configuration) null, systemUiContext.getResources().getCompatibilityInfo(), (java.lang.ClassLoader) null, (java.util.List) null);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    android.content.res.Resources getCurrentUserResources() {
        if (this.mCurrentUserResources == null) {
            updateCurrentUserResources();
        }
        return this.mCurrentUserResources;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.content.Context getContext() {
        return this.mContext;
    }

    android.content.Context getSystemUiContext() {
        return this.mUiContext;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setCanSystemBarsBeShownByUser(boolean z) {
        this.mCanSystemBarsBeShownByUser = z;
    }

    void notifyDisplayReady() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayPolicy.this.lambda$notifyDisplayReady$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyDisplayReady$4() {
        int displayId = getDisplayId();
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            statusBarManagerInternal.onDisplayReady(displayId);
        }
        com.android.server.wallpaper.WallpaperManagerInternal wallpaperManagerInternal = (com.android.server.wallpaper.WallpaperManagerInternal) com.android.server.LocalServices.getService(com.android.server.wallpaper.WallpaperManagerInternal.class);
        if (wallpaperManagerInternal != null) {
            wallpaperManagerInternal.onDisplayReady(displayId);
        }
    }

    float getWindowCornerRadius() {
        return this.mDisplayContent.getDisplay().getType() == 1 ? com.android.internal.policy.ScreenDecorationsUtils.getWindowCornerRadius(this.mContext) : com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    }

    boolean isShowingDreamLw() {
        return this.mShowingDream;
    }

    static class DecorInsets {
        private final com.android.server.wm.DisplayContent mDisplayContent;
        private final com.android.server.wm.DisplayPolicy.DecorInsets.Info[] mInfoForRotation = new com.android.server.wm.DisplayPolicy.DecorInsets.Info[4];
        final com.android.server.wm.DisplayPolicy.DecorInsets.Info mTmpInfo = new com.android.server.wm.DisplayPolicy.DecorInsets.Info();

        static class Info {
            final android.graphics.Rect mNonDecorInsets = new android.graphics.Rect();
            final android.graphics.Rect mConfigInsets = new android.graphics.Rect();
            final android.graphics.Rect mNonDecorFrame = new android.graphics.Rect();
            final android.graphics.Rect mConfigFrame = new android.graphics.Rect();
            private boolean mNeedUpdate = true;

            Info() {
            }

            android.view.InsetsState update(com.android.server.wm.DisplayContent displayContent, int i, int i2, int i3) {
                com.android.server.wm.DisplayFrames displayFrames = new com.android.server.wm.DisplayFrames();
                displayContent.updateDisplayFrames(displayFrames, i, i2, i3);
                displayContent.getDisplayPolicy().simulateLayoutDisplay(displayFrames);
                android.view.InsetsState insetsState = displayFrames.mInsetsState;
                android.graphics.Rect displayFrame = insetsState.getDisplayFrame();
                android.graphics.Insets calculateInsets = insetsState.calculateInsets(displayFrame, displayContent.mWmService.mDecorTypes, true);
                android.graphics.Insets calculateInsets2 = insetsState.calculateInsets(displayFrame, displayContent.mWmService.mConfigTypes, true);
                this.mNonDecorInsets.set(calculateInsets.left, calculateInsets.top, calculateInsets.right, calculateInsets.bottom);
                this.mConfigInsets.set(calculateInsets2.left, calculateInsets2.top, calculateInsets2.right, calculateInsets2.bottom);
                this.mNonDecorFrame.set(displayFrame);
                this.mNonDecorFrame.inset(this.mNonDecorInsets);
                this.mConfigFrame.set(displayFrame);
                this.mConfigFrame.inset(this.mConfigInsets);
                this.mNeedUpdate = false;
                return insetsState;
            }

            void set(com.android.server.wm.DisplayPolicy.DecorInsets.Info info) {
                this.mNonDecorInsets.set(info.mNonDecorInsets);
                this.mConfigInsets.set(info.mConfigInsets);
                this.mNonDecorFrame.set(info.mNonDecorFrame);
                this.mConfigFrame.set(info.mConfigFrame);
                this.mNeedUpdate = false;
            }

            public java.lang.String toString() {
                java.lang.StringBuilder sb = new java.lang.StringBuilder(32);
                return "{nonDecorInsets=" + this.mNonDecorInsets.toShortString(sb) + ", configInsets=" + this.mConfigInsets.toShortString(sb) + ", nonDecorFrame=" + this.mNonDecorFrame.toShortString(sb) + ", configFrame=" + this.mConfigFrame.toShortString(sb) + '}';
            }
        }

        DecorInsets(com.android.server.wm.DisplayContent displayContent) {
            this.mDisplayContent = displayContent;
            for (int length = this.mInfoForRotation.length - 1; length >= 0; length--) {
                this.mInfoForRotation[length] = new com.android.server.wm.DisplayPolicy.DecorInsets.Info();
            }
        }

        com.android.server.wm.DisplayPolicy.DecorInsets.Info get(int i, int i2, int i3) {
            com.android.server.wm.DisplayPolicy.DecorInsets.Info info = this.mInfoForRotation[i];
            if (info.mNeedUpdate) {
                info.update(this.mDisplayContent, i, i2, i3);
            }
            return info;
        }

        void invalidate() {
            for (com.android.server.wm.DisplayPolicy.DecorInsets.Info info : this.mInfoForRotation) {
                info.mNeedUpdate = true;
            }
        }

        void setTo(com.android.server.wm.DisplayPolicy.DecorInsets decorInsets) {
            for (int length = this.mInfoForRotation.length - 1; length >= 0; length--) {
                this.mInfoForRotation[length].set(decorInsets.mInfoForRotation[length]);
            }
        }

        void dump(java.lang.String str, java.io.PrintWriter printWriter) {
            for (int i = 0; i < this.mInfoForRotation.length; i++) {
                printWriter.println(str + android.view.Surface.rotationToString(i) + "=" + this.mInfoForRotation[i]);
            }
        }

        static boolean hasInsetsFrameDiff(android.view.InsetsState insetsState, android.view.InsetsState insetsState2, int i) {
            int i2 = 0;
            for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
                android.view.InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
                if ((sourceAt.getType() & i) != 0) {
                    i2++;
                    android.view.InsetsSource peekSource = insetsState2.peekSource(sourceAt.getId());
                    if (peekSource == null || !peekSource.getFrame().equals(sourceAt.getFrame())) {
                        return true;
                    }
                }
            }
            int i3 = 0;
            for (int sourceSize2 = insetsState2.sourceSize() - 1; sourceSize2 >= 0; sourceSize2--) {
                if ((insetsState2.sourceAt(sourceSize2).getType() & i) != 0) {
                    i3++;
                }
            }
            return i2 != i3;
        }

        private static class Cache {
            static final int ID_UPDATING_CONFIG = -1;
            boolean mActive;
            final com.android.server.wm.DisplayPolicy.DecorInsets mDecorInsets;
            int mPreserveId;

            Cache(com.android.server.wm.DisplayContent displayContent) {
                this.mDecorInsets = new com.android.server.wm.DisplayPolicy.DecorInsets(displayContent);
            }

            boolean canPreserve() {
                return this.mPreserveId == -1 || this.mDecorInsets.mDisplayContent.mTransitionController.inTransition(this.mPreserveId);
            }
        }
    }

    boolean updateDecorInsetsInfo() {
        if (shouldKeepCurrentDecorInsets()) {
            return false;
        }
        com.android.server.wm.DisplayFrames displayFrames = this.mDisplayContent.mDisplayFrames;
        int i = displayFrames.mRotation;
        int i2 = displayFrames.mWidth;
        int i3 = displayFrames.mHeight;
        com.android.server.wm.DisplayPolicy.DecorInsets.Info info = this.mDecorInsets.mTmpInfo;
        android.view.InsetsState update = info.update(this.mDisplayContent, i, i2, i3);
        if (info.mConfigFrame.equals(getDecorInsetsInfo(i, i2, i3).mConfigFrame)) {
            if (com.android.server.wm.DisplayPolicy.DecorInsets.hasInsetsFrameDiff(update, this.mDisplayContent.mDisplayFrames.mInsetsState, this.mService.mConfigTypes)) {
                for (int length = this.mDecorInsets.mInfoForRotation.length - 1; length >= 0; length--) {
                    if (length != i) {
                        boolean z = (length + i) % 2 == 1;
                        this.mDecorInsets.mInfoForRotation[length].update(this.mDisplayContent, length, z ? i3 : i2, z ? i2 : i3);
                    }
                }
                this.mDecorInsets.mInfoForRotation[i].set(info);
            }
            return false;
        }
        if (this.mCachedDecorInsets != null && !this.mCachedDecorInsets.canPreserve() && this.mScreenOnFully) {
            this.mCachedDecorInsets = null;
        }
        this.mDecorInsets.invalidate();
        this.mDecorInsets.mInfoForRotation[i].set(info);
        return true;
    }

    com.android.server.wm.DisplayPolicy.DecorInsets.Info getDecorInsetsInfo(int i, int i2, int i3) {
        return this.mDecorInsets.get(i, i2, i3);
    }

    boolean shouldKeepCurrentDecorInsets() {
        return this.mCachedDecorInsets != null && this.mCachedDecorInsets.mActive && this.mCachedDecorInsets.canPreserve();
    }

    void physicalDisplayChanged() {
        if (USE_CACHED_INSETS_FOR_DISPLAY_SWITCH) {
            updateCachedDecorInsets();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateCachedDecorInsets() {
        com.android.server.wm.DisplayPolicy.DecorInsets decorInsets;
        if (this.mCachedDecorInsets == null) {
            this.mCachedDecorInsets = new com.android.server.wm.DisplayPolicy.DecorInsets.Cache(this.mDisplayContent);
            decorInsets = null;
        } else {
            decorInsets = new com.android.server.wm.DisplayPolicy.DecorInsets(this.mDisplayContent);
            decorInsets.setTo(this.mCachedDecorInsets.mDecorInsets);
        }
        this.mCachedDecorInsets.mPreserveId = -1;
        this.mCachedDecorInsets.mDecorInsets.setTo(this.mDecorInsets);
        if (decorInsets != null) {
            this.mDecorInsets.setTo(decorInsets);
            this.mCachedDecorInsets.mActive = true;
        }
    }

    void physicalDisplayUpdated() {
        if (this.mCachedDecorInsets == null) {
            return;
        }
        if (!this.mDisplayContent.mTransitionController.isCollecting()) {
            this.mCachedDecorInsets = null;
        } else {
            this.mCachedDecorInsets.mPreserveId = this.mDisplayContent.mTransitionController.getCollectingTransitionId();
        }
    }

    int navigationBarPosition(int i) {
        if (this.mNavigationBar != null) {
            switch (this.mNavigationBar.mAttrs.forRotation(i).gravity) {
                case 3:
                    return 1;
                case 4:
                default:
                    return 4;
                case 5:
                    return 2;
            }
        }
        return -1;
    }

    public void focusChangedLw(com.android.server.wm.WindowState windowState, com.android.server.wm.WindowState windowState2) {
        this.mFocusedWindow = windowState2;
        this.mLastFocusedWindow = windowState;
        if (this.mDisplayContent.isDefaultDisplay) {
            this.mService.mPolicy.onDefaultDisplayFocusChangedLw(windowState2);
        }
        updateSystemBarAttributes();
    }

    @com.android.internal.annotations.VisibleForTesting
    void requestTransientBars(com.android.server.wm.WindowState windowState, boolean z) {
        com.android.server.wm.InsetsControlTarget insetsControlTarget;
        if (android.view.ViewRootImpl.CLIENT_TRANSIENT || windowState == null || !this.mService.mPolicy.isUserSetupComplete()) {
            return;
        }
        if (!this.mCanSystemBarsBeShownByUser) {
            android.util.Slog.d(TAG, "Remote insets controller disallows showing system bars - ignoring request");
            return;
        }
        com.android.server.wm.InsetsSourceProvider controllableInsetProvider = windowState.getControllableInsetProvider();
        if (controllableInsetProvider == null) {
            insetsControlTarget = null;
        } else {
            insetsControlTarget = controllableInsetProvider.getControlTarget();
        }
        if (insetsControlTarget == null || insetsControlTarget == getNotificationShade()) {
            return;
        }
        com.android.server.wm.WindowState window = insetsControlTarget.getWindow();
        if (window != null && window.isActivityTypeDream()) {
            return;
        }
        int statusBars = (android.view.WindowInsets.Type.statusBars() | android.view.WindowInsets.Type.navigationBars()) & insetsControlTarget.getRequestedVisibleTypes();
        com.android.server.wm.InsetsSourceProvider controllableInsetProvider2 = windowState.getControllableInsetProvider();
        if (controllableInsetProvider2 != null && controllableInsetProvider2.getSource().getType() == android.view.WindowInsets.Type.navigationBars() && (android.view.WindowInsets.Type.navigationBars() & statusBars) != 0) {
            insetsControlTarget.showInsets(android.view.WindowInsets.Type.navigationBars(), false, null);
            return;
        }
        if (!insetsControlTarget.canShowTransient()) {
            insetsControlTarget.showInsets(android.view.WindowInsets.Type.statusBars() | android.view.WindowInsets.Type.navigationBars(), false, null);
            if (windowState == this.mStatusBar && !this.mStatusBar.transferTouch()) {
                android.util.Slog.i(TAG, "Could not transfer touch to the status bar");
            }
        } else {
            this.mDisplayContent.getInsetsPolicy().showTransient(SHOW_TYPES_FOR_SWIPE, z);
            insetsControlTarget.showInsets(statusBars, false, null);
        }
        if (android.view.ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION || android.view.ViewRootImpl.CLIENT_TRANSIENT) {
            this.mStatusBarManagerInternal.confirmImmersivePrompt();
        } else {
            this.mImmersiveModeConfirmation.confirmCurrentPrompt();
        }
    }

    boolean isKeyguardShowing() {
        return this.mService.mPolicy.isKeyguardShowing();
    }

    private boolean isKeyguardOccluded() {
        return this.mService.mPolicy.isKeyguardOccluded();
    }

    com.android.server.wm.InsetsPolicy getInsetsPolicy() {
        return this.mDisplayContent.getInsetsPolicy();
    }

    void addRelaunchingApp(com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mSystemBarColorApps.contains(activityRecord) && !activityRecord.hasStartingWindow()) {
            this.mRelaunchingSystemBarColorApps.add(activityRecord);
        }
    }

    void removeRelaunchingApp(com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mRelaunchingSystemBarColorApps.remove(activityRecord) & this.mRelaunchingSystemBarColorApps.isEmpty()) {
            updateSystemBarAttributes();
        }
    }

    void resetSystemBarAttributes() {
        this.mLastDisableFlags = 0;
        updateSystemBarAttributes();
    }

    void updateSystemBarAttributes() {
        com.android.server.wm.WindowState windowState;
        com.android.server.wm.WindowState windowState2 = this.mFocusedWindow != null ? this.mFocusedWindow : this.mTopFullscreenOpaqueWindowState;
        if (windowState2 == null) {
            return;
        }
        if ((windowState2.getAttrs().privateFlags & 131072) != 0) {
            if (this.mNotificationShade != null && this.mNotificationShade.canReceiveKeys()) {
                windowState2 = this.mNotificationShade;
            } else if (this.mLastFocusedWindow != null && this.mLastFocusedWindow.canReceiveKeys()) {
                windowState2 = this.mLastFocusedWindow;
            } else {
                windowState2 = this.mTopFullscreenOpaqueWindowState;
            }
            if (windowState2 == null) {
                return;
            }
        }
        this.mSystemUiControllingWindow = windowState2;
        final int displayId = getDisplayId();
        final int disableFlags = windowState2.getDisableFlags();
        int updateSystemBarsLw = updateSystemBarsLw(windowState2, disableFlags);
        if (!this.mRelaunchingSystemBarColorApps.isEmpty()) {
            return;
        }
        com.android.server.wm.WindowState chooseNavigationColorWindowLw = chooseNavigationColorWindowLw(this.mNavBarColorWindowCandidate, this.mDisplayContent.mInputMethodWindow, this.mNavigationBarPosition);
        boolean z = chooseNavigationColorWindowLw != null && chooseNavigationColorWindowLw == this.mDisplayContent.mInputMethodWindow;
        final int updateLightNavigationBarLw = updateSystemBarsLw | updateLightNavigationBarLw(windowState2.mAttrs.insetsFlags.appearance, chooseNavigationColorWindowLw);
        if (topAppHidesSystemBar(android.view.WindowInsets.Type.navigationBars())) {
            windowState = this.mTopFullscreenOpaqueWindowState;
        } else {
            windowState = windowState2;
        }
        final int i = windowState.mAttrs.insetsFlags.behavior;
        final java.lang.String str = windowState2.mAttrs.packageName;
        boolean z2 = (windowState2.isRequestedVisible(android.view.WindowInsets.Type.statusBars()) && windowState2.isRequestedVisible(android.view.WindowInsets.Type.navigationBars())) ? false : true;
        final com.android.internal.view.AppearanceRegion[] appearanceRegionArr = new com.android.internal.view.AppearanceRegion[this.mStatusBarAppearanceRegionList.size()];
        this.mStatusBarAppearanceRegionList.toArray(appearanceRegionArr);
        if (this.mLastDisableFlags != disableFlags) {
            this.mLastDisableFlags = disableFlags;
            final java.lang.String windowState3 = windowState2.toString();
            callStatusBarSafely(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.statusbar.StatusBarManagerInternal) obj).setDisableFlags(displayId, disableFlags, windowState3);
                }
            });
        }
        final int requestedVisibleTypes = windowState2.getRequestedVisibleTypes();
        final com.android.internal.statusbar.LetterboxDetails[] letterboxDetailsArr = new com.android.internal.statusbar.LetterboxDetails[this.mLetterboxDetails.size()];
        this.mLetterboxDetails.toArray(letterboxDetailsArr);
        if (this.mLastAppearance == updateLightNavigationBarLw && this.mLastBehavior == i && this.mLastRequestedVisibleTypes == requestedVisibleTypes && java.util.Objects.equals(this.mFocusedApp, str) && this.mLastFocusIsFullscreen == z2 && java.util.Arrays.equals(this.mLastStatusBarAppearanceRegions, appearanceRegionArr) && java.util.Arrays.equals(this.mLastLetterboxDetails, letterboxDetailsArr)) {
            return;
        }
        if (this.mDisplayContent.isDefaultDisplay && (this.mLastFocusIsFullscreen != z2 || ((this.mLastAppearance ^ updateLightNavigationBarLw) & 4) != 0)) {
            this.mService.mInputManager.setSystemUiLightsOut(z2 || (updateLightNavigationBarLw & 4) != 0);
        }
        this.mLastAppearance = updateLightNavigationBarLw;
        this.mLastBehavior = i;
        this.mLastRequestedVisibleTypes = requestedVisibleTypes;
        this.mFocusedApp = str;
        this.mLastFocusIsFullscreen = z2;
        this.mLastStatusBarAppearanceRegions = appearanceRegionArr;
        this.mLastLetterboxDetails = letterboxDetailsArr;
        final boolean z3 = z;
        callStatusBarSafely(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                ((com.android.server.statusbar.StatusBarManagerInternal) obj).onSystemBarAttributesChanged(displayId, updateLightNavigationBarLw, appearanceRegionArr, z3, i, requestedVisibleTypes, str, letterboxDetailsArr);
            }
        });
    }

    private void callStatusBarSafely(final java.util.function.Consumer<com.android.server.statusbar.StatusBarManagerInternal> consumer) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayPolicy.this.lambda$callStatusBarSafely$7(consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$callStatusBarSafely$7(java.util.function.Consumer consumer) {
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal = getStatusBarManagerInternal();
        if (statusBarManagerInternal != null) {
            consumer.accept(statusBarManagerInternal);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    static com.android.server.wm.WindowState chooseNavigationColorWindowLw(com.android.server.wm.WindowState windowState, com.android.server.wm.WindowState windowState2, int i) {
        if (!(windowState2 != null && windowState2.isVisible() && i == 4 && (windowState2.mAttrs.flags & Integer.MIN_VALUE) != 0)) {
            return windowState;
        }
        if (windowState != null && windowState.isDimming()) {
            if (android.view.WindowManager.LayoutParams.mayUseInputMethod(windowState.mAttrs.flags)) {
                return windowState2;
            }
            return windowState;
        }
        return windowState2;
    }

    @com.android.internal.annotations.VisibleForTesting
    int updateLightNavigationBarLw(int i, com.android.server.wm.WindowState windowState) {
        if (windowState == null || !isLightBarAllowed(windowState, android.view.WindowInsets.Type.navigationBars())) {
            return i & (-17);
        }
        return (i & (-17)) | (windowState.mAttrs.insetsFlags.appearance & 16);
    }

    private int updateSystemBarsLw(com.android.server.wm.WindowState windowState, int i) {
        com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal;
        com.android.server.wm.TaskDisplayArea defaultTaskDisplayArea = this.mDisplayContent.getDefaultTaskDisplayArea();
        boolean z = false;
        boolean z2 = defaultTaskDisplayArea.getRootTask(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$updateSystemBarsLw$8;
                lambda$updateSystemBarsLw$8 = com.android.server.wm.DisplayPolicy.lambda$updateSystemBarsLw$8((com.android.server.wm.Task) obj);
                return lambda$updateSystemBarsLw$8;
            }
        }) != null;
        boolean isRootTaskVisible = defaultTaskDisplayArea.isRootTaskVisible(5);
        getInsetsPolicy().updateSystemBars(windowState, z2, isRootTaskVisible);
        boolean z3 = topAppHidesSystemBar(android.view.WindowInsets.Type.statusBars());
        if (getStatusBar() != null && (statusBarManagerInternal = getStatusBarManagerInternal()) != null) {
            statusBarManagerInternal.setTopAppHidesStatusBar(z3);
        }
        this.mTopIsFullscreen = z3 && (this.mNotificationShade == null || !this.mNotificationShade.isVisible());
        int configureNavBarOpacity = configureNavBarOpacity(configureStatusBarOpacity(3), z2, isRootTaskVisible);
        boolean z4 = this.mIsImmersiveMode;
        final boolean isImmersiveMode = isImmersiveMode(windowState);
        if (z4 != isImmersiveMode) {
            this.mIsImmersiveMode = isImmersiveMode;
            com.android.server.wm.RootDisplayArea rootDisplayArea = windowState.getRootDisplayArea();
            final int i2 = rootDisplayArea == null ? -1 : rootDisplayArea.mFeatureId;
            if (!android.view.ViewRootImpl.CLIENT_TRANSIENT && !android.view.ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION) {
                this.mImmersiveModeConfirmation.immersiveModeChangedLw(i2, isImmersiveMode, this.mService.mPolicy.isUserSetupComplete(), isNavBarEmpty(i));
            } else {
                callStatusBarSafely(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda13
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.statusbar.StatusBarManagerInternal) obj).immersiveModeChanged(i2, isImmersiveMode);
                    }
                });
            }
        }
        boolean z5 = !windowState.isRequestedVisible(android.view.WindowInsets.Type.navigationBars());
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        if (this.mPendingPanicGestureUptime != 0 && uptimeMillis - this.mPendingPanicGestureUptime <= 30000) {
            z = true;
        }
        com.android.server.wm.DisplayPolicy displayPolicy = this.mService.getDefaultDisplayContentLocked().getDisplayPolicy();
        if (z && z5 && isImmersiveMode && displayPolicy.isKeyguardDrawComplete()) {
            this.mPendingPanicGestureUptime = 0L;
            if (!isNavBarEmpty(i)) {
                this.mDisplayContent.getInsetsPolicy().showTransient(SHOW_TYPES_FOR_PANIC, true);
            }
        }
        return configureNavBarOpacity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateSystemBarsLw$8(com.android.server.wm.Task task) {
        return task.isVisible() && task.getTopLeafTask().getAdjacentTask() != null;
    }

    private static boolean isLightBarAllowed(com.android.server.wm.WindowState windowState, int i) {
        if (windowState == null) {
            return false;
        }
        return intersectsAnyInsets(windowState.getFrame(), windowState.getInsetsState(), i);
    }

    private android.graphics.Rect getBarContentFrameForWindow(com.android.server.wm.WindowState windowState, int i) {
        com.android.server.wm.DisplayFrames displayFrames = windowState.getDisplayFrames(this.mDisplayContent.mDisplayFrames);
        android.view.InsetsState insetsState = displayFrames.mInsetsState;
        android.graphics.Rect rect = displayFrames.mUnrestricted;
        android.graphics.Rect rect2 = sTmpDisplayCutoutSafe;
        android.graphics.Insets waterfallInsets = insetsState.getDisplayCutout().getWaterfallInsets();
        android.graphics.Rect rect3 = new android.graphics.Rect();
        android.graphics.Rect rect4 = sTmpRect;
        rect2.set(displayFrames.mDisplayCutoutSafe);
        for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            android.view.InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
            if (sourceAt.getType() == i) {
                if (i == android.view.WindowInsets.Type.statusBars()) {
                    rect2.set(displayFrames.mDisplayCutoutSafe);
                    android.graphics.Insets calculateInsets = sourceAt.calculateInsets(rect, true);
                    if (calculateInsets.left > 0) {
                        rect2.left = java.lang.Math.max(rect.left + waterfallInsets.left, rect.left);
                    } else if (calculateInsets.top > 0) {
                        rect2.top = java.lang.Math.max(rect.top + waterfallInsets.top, rect.top);
                    } else if (calculateInsets.right > 0) {
                        rect2.right = java.lang.Math.max(rect.right - waterfallInsets.right, rect.right);
                    } else if (calculateInsets.bottom > 0) {
                        rect2.bottom = java.lang.Math.max(rect.bottom - waterfallInsets.bottom, rect.bottom);
                    }
                }
                rect4.set(sourceAt.getFrame());
                rect4.intersect(rect2);
                rect3.union(rect4);
            }
        }
        return rect3;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isFullyTransparentAllowed(com.android.server.wm.WindowState windowState, int i) {
        if (windowState == null) {
            return true;
        }
        return windowState.isFullyTransparentBarAllowed(getBarContentFrameForWindow(windowState, i));
    }

    private static boolean drawsBarBackground(com.android.server.wm.WindowState windowState) {
        if (windowState == null) {
            return true;
        }
        return ((windowState.getAttrs().privateFlags & 32768) != 0) || ((windowState.getAttrs().flags & Integer.MIN_VALUE) != 0);
    }

    private int configureStatusBarOpacity(int i) {
        boolean z = true;
        boolean z2 = true;
        for (int size = this.mStatusBarBackgroundWindows.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowState windowState = this.mStatusBarBackgroundWindows.get(size);
            z &= drawsBarBackground(windowState);
            z2 &= isFullyTransparentAllowed(windowState, android.view.WindowInsets.Type.statusBars());
        }
        if (z) {
            i &= -2;
        }
        if (!z2) {
            return i | 32;
        }
        return i;
    }

    private int configureNavBarOpacity(int i, boolean z, boolean z2) {
        com.android.server.wm.WindowState chooseNavigationBackgroundWindow = chooseNavigationBackgroundWindow(this.mNavBarBackgroundWindowCandidate, this.mDisplayContent.mInputMethodWindow, this.mNavigationBarPosition);
        boolean z3 = chooseNavigationBackgroundWindow != null || this.mNavBarBackgroundWindowCandidate == null;
        if (this.mNavBarOpacityMode == 2) {
            if (z3) {
                i = clearNavBarOpaqueFlag(i);
            }
        } else if (this.mNavBarOpacityMode != 0) {
            if (this.mNavBarOpacityMode == 1 && z2) {
                i = clearNavBarOpaqueFlag(i);
            }
        } else if (z || z2) {
            if (this.mIsFreeformWindowOverlappingWithNavBar) {
                i = clearNavBarOpaqueFlag(i);
            }
        } else if (z3) {
            i = clearNavBarOpaqueFlag(i);
        }
        if (!isFullyTransparentAllowed(chooseNavigationBackgroundWindow, android.view.WindowInsets.Type.navigationBars())) {
            return i | 64;
        }
        return i;
    }

    private int clearNavBarOpaqueFlag(int i) {
        return i & (-3);
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    static com.android.server.wm.WindowState chooseNavigationBackgroundWindow(com.android.server.wm.WindowState windowState, com.android.server.wm.WindowState windowState2, int i) {
        if (windowState2 != null && windowState2.isVisible() && i == 4 && drawsBarBackground(windowState2)) {
            return windowState2;
        }
        if (drawsBarBackground(windowState)) {
            return windowState;
        }
        return null;
    }

    private boolean isImmersiveMode(com.android.server.wm.WindowState windowState) {
        if (windowState == null || windowState.mPolicy.getWindowLayerLw(windowState) > windowState.mPolicy.getWindowLayerFromTypeLw(2000) || windowState.isActivityTypeDream()) {
            return false;
        }
        return getInsetsPolicy().hasHiddenSources(android.view.WindowInsets.Type.navigationBars());
    }

    private static boolean isNavBarEmpty(int i) {
        return (i & 23068672) == 23068672;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onPowerKeyDown(boolean z) {
        boolean isPowerKeyDownPanic;
        if (!android.view.ViewRootImpl.CLIENT_TRANSIENT && !android.view.ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION) {
            isPowerKeyDownPanic = this.mImmersiveModeConfirmation.onPowerKeyDown(z, android.os.SystemClock.elapsedRealtime(), isImmersiveMode(this.mSystemUiControllingWindow), isNavBarEmpty(this.mLastDisableFlags));
        } else {
            isPowerKeyDownPanic = isPowerKeyDownPanic(z, android.os.SystemClock.elapsedRealtime(), isImmersiveMode(this.mSystemUiControllingWindow), isNavBarEmpty(this.mLastDisableFlags));
        }
        if (isPowerKeyDownPanic) {
            this.mHandler.post(this.mHiddenNavPanic);
        }
    }

    private boolean isPowerKeyDownPanic(boolean z, long j, boolean z2, boolean z3) {
        if (!z && j - this.mPanicTime < this.mPanicThresholdMs) {
            return !this.mImmersiveConfirmationWindowExists;
        }
        if (z && z2 && !z3) {
            this.mPanicTime = j;
            return false;
        }
        this.mPanicTime = 0L;
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onVrStateChangedLw(boolean z) {
        if (!android.view.ViewRootImpl.CLIENT_TRANSIENT && !android.view.ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION) {
            this.mImmersiveModeConfirmation.onVrStateChangedLw(z);
        }
    }

    public void onLockTaskStateChangedLw(int i) {
        if (!android.view.ViewRootImpl.CLIENT_TRANSIENT && !android.view.ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION) {
            this.mImmersiveModeConfirmation.onLockTaskModeChangedLw(i);
        }
    }

    public void onUserActivityEventTouch() {
        if (this.mAwake) {
            return;
        }
        com.android.server.wm.WindowState windowState = this.mNotificationShade;
        this.mService.mAtmService.setProcessAnimatingWhileDozing(windowState != null ? windowState.getProcess() : null);
    }

    boolean onSystemUiSettingsChanged() {
        if (android.view.ViewRootImpl.CLIENT_TRANSIENT || android.view.ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION) {
            return false;
        }
        return this.mImmersiveModeConfirmation.onSettingChanged(this.mService.mCurrentUserId);
    }

    public void takeScreenshot(int i, int i2) {
        if (this.mScreenshotHelper != null) {
            this.mScreenshotHelper.takeScreenshot(new com.android.internal.util.ScreenshotRequest.Builder(i, i2).build(), this.mHandler, (java.util.function.Consumer) null);
        }
    }

    com.android.server.wm.RefreshRatePolicy getRefreshRatePolicy() {
        return this.mRefreshRatePolicy;
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.println("DisplayPolicy");
        java.lang.String str2 = str + "  ";
        java.lang.String str3 = str2 + "  ";
        printWriter.print(str2);
        printWriter.print("mCarDockEnablesAccelerometer=");
        printWriter.print(this.mCarDockEnablesAccelerometer);
        printWriter.print(" mDeskDockEnablesAccelerometer=");
        printWriter.println(this.mDeskDockEnablesAccelerometer);
        printWriter.print(str2);
        printWriter.print("mDockMode=");
        printWriter.print(android.content.Intent.dockStateToString(this.mDockMode));
        printWriter.print(" mLidState=");
        printWriter.println(com.android.server.policy.WindowManagerPolicy.WindowManagerFuncs.lidStateToString(this.mLidState));
        printWriter.print(str2);
        printWriter.print("mAwake=");
        printWriter.print(this.mAwake);
        printWriter.print(" mScreenOnEarly=");
        printWriter.print(this.mScreenOnEarly);
        printWriter.print(" mScreenOnFully=");
        printWriter.println(this.mScreenOnFully);
        printWriter.print(str2);
        printWriter.print("mKeyguardDrawComplete=");
        printWriter.print(this.mKeyguardDrawComplete);
        printWriter.print(" mWindowManagerDrawComplete=");
        printWriter.println(this.mWindowManagerDrawComplete);
        printWriter.print(str2);
        printWriter.print("mHdmiPlugged=");
        printWriter.println(this.mHdmiPlugged);
        if (this.mLastDisableFlags != 0) {
            printWriter.print(str2);
            printWriter.print("mLastDisableFlags=0x");
            printWriter.println(java.lang.Integer.toHexString(this.mLastDisableFlags));
        }
        if (this.mLastAppearance != 0) {
            printWriter.print(str2);
            printWriter.print("mLastAppearance=");
            printWriter.println(android.view.ViewDebug.flagsToString(android.view.InsetsFlags.class, "appearance", this.mLastAppearance));
        }
        if (this.mLastBehavior != 0) {
            printWriter.print(str2);
            printWriter.print("mLastBehavior=");
            printWriter.println(android.view.ViewDebug.flagsToString(android.view.InsetsFlags.class, "behavior", this.mLastBehavior));
        }
        printWriter.print(str2);
        printWriter.print("mShowingDream=");
        printWriter.print(this.mShowingDream);
        printWriter.print(" mDreamingLockscreen=");
        printWriter.println(this.mDreamingLockscreen);
        if (this.mStatusBar != null) {
            printWriter.print(str2);
            printWriter.print("mStatusBar=");
            printWriter.println(this.mStatusBar);
        }
        if (this.mNotificationShade != null) {
            printWriter.print(str2);
            printWriter.print("mExpandedPanel=");
            printWriter.println(this.mNotificationShade);
        }
        printWriter.print(str2);
        printWriter.print("isKeyguardShowing=");
        printWriter.println(isKeyguardShowing());
        if (this.mNavigationBar != null) {
            printWriter.print(str2);
            printWriter.print("mNavigationBar=");
            printWriter.println(this.mNavigationBar);
            printWriter.print(str2);
            printWriter.print("mNavBarOpacityMode=");
            printWriter.println(this.mNavBarOpacityMode);
            printWriter.print(str2);
            printWriter.print("mNavigationBarCanMove=");
            printWriter.println(this.mNavigationBarCanMove);
            printWriter.print(str2);
            printWriter.print("mNavigationBarPosition=");
            printWriter.println(this.mNavigationBarPosition);
        }
        if (this.mLeftGestureHost != null) {
            printWriter.print(str2);
            printWriter.print("mLeftGestureHost=");
            printWriter.println(this.mLeftGestureHost);
        }
        if (this.mTopGestureHost != null) {
            printWriter.print(str2);
            printWriter.print("mTopGestureHost=");
            printWriter.println(this.mTopGestureHost);
        }
        if (this.mRightGestureHost != null) {
            printWriter.print(str2);
            printWriter.print("mRightGestureHost=");
            printWriter.println(this.mRightGestureHost);
        }
        if (this.mBottomGestureHost != null) {
            printWriter.print(str2);
            printWriter.print("mBottomGestureHost=");
            printWriter.println(this.mBottomGestureHost);
        }
        if (this.mFocusedWindow != null) {
            printWriter.print(str2);
            printWriter.print("mFocusedWindow=");
            printWriter.println(this.mFocusedWindow);
        }
        if (this.mTopFullscreenOpaqueWindowState != null) {
            printWriter.print(str2);
            printWriter.print("mTopFullscreenOpaqueWindowState=");
            printWriter.println(this.mTopFullscreenOpaqueWindowState);
        }
        if (!this.mSystemBarColorApps.isEmpty()) {
            printWriter.print(str2);
            printWriter.print("mSystemBarColorApps=");
            printWriter.println(this.mSystemBarColorApps);
        }
        if (!this.mRelaunchingSystemBarColorApps.isEmpty()) {
            printWriter.print(str2);
            printWriter.print("mRelaunchingSystemBarColorApps=");
            printWriter.println(this.mRelaunchingSystemBarColorApps);
        }
        if (this.mNavBarColorWindowCandidate != null) {
            printWriter.print(str2);
            printWriter.print("mNavBarColorWindowCandidate=");
            printWriter.println(this.mNavBarColorWindowCandidate);
        }
        if (this.mNavBarBackgroundWindowCandidate != null) {
            printWriter.print(str2);
            printWriter.print("mNavBarBackgroundWindowCandidate=");
            printWriter.println(this.mNavBarBackgroundWindowCandidate);
        }
        if (this.mLastStatusBarAppearanceRegions != null) {
            printWriter.print(str2);
            printWriter.println("mLastStatusBarAppearanceRegions=");
            for (int length = this.mLastStatusBarAppearanceRegions.length - 1; length >= 0; length--) {
                printWriter.print(str3);
                printWriter.println(this.mLastStatusBarAppearanceRegions[length]);
            }
        }
        if (this.mLastLetterboxDetails != null) {
            printWriter.print(str2);
            printWriter.println("mLastLetterboxDetails=");
            for (int length2 = this.mLastLetterboxDetails.length - 1; length2 >= 0; length2--) {
                printWriter.print(str3);
                printWriter.println(this.mLastLetterboxDetails[length2]);
            }
        }
        if (!this.mStatusBarBackgroundWindows.isEmpty()) {
            printWriter.print(str2);
            printWriter.println("mStatusBarBackgroundWindows=");
            for (int size = this.mStatusBarBackgroundWindows.size() - 1; size >= 0; size--) {
                com.android.server.wm.WindowState windowState = this.mStatusBarBackgroundWindows.get(size);
                printWriter.print(str3);
                printWriter.println(windowState);
            }
        }
        printWriter.print(str2);
        printWriter.print("mTopIsFullscreen=");
        printWriter.println(this.mTopIsFullscreen);
        printWriter.print(str2);
        printWriter.print("mImeInsetsConsumed=");
        printWriter.println(this.mImeInsetsConsumed);
        printWriter.print(str2);
        printWriter.print("mForceShowNavigationBarEnabled=");
        printWriter.print(this.mForceShowNavigationBarEnabled);
        printWriter.print(" mAllowLockscreenWhenOn=");
        printWriter.println(this.mAllowLockscreenWhenOn);
        printWriter.print(str2);
        printWriter.print("mRemoteInsetsControllerControlsSystemBars=");
        printWriter.println(this.mRemoteInsetsControllerControlsSystemBars);
        printWriter.print(str2);
        printWriter.println("mDecorInsetsInfo:");
        this.mDecorInsets.dump(str3, printWriter);
        if (this.mCachedDecorInsets != null) {
            printWriter.print(str2);
            printWriter.println("mCachedDecorInsets:");
            this.mCachedDecorInsets.mDecorInsets.dump(str3, printWriter);
        }
        if (!android.view.ViewRootImpl.CLIENT_TRANSIENT) {
            this.mSystemGestures.dump(printWriter, str2);
        }
    }

    private boolean supportsPointerLocation() {
        return this.mDisplayContent.isDefaultDisplay || !this.mDisplayContent.isPrivate();
    }

    void setPointerLocationEnabled(boolean z) {
        if (!supportsPointerLocation()) {
            return;
        }
        this.mHandler.sendEmptyMessage(z ? 4 : 5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void enablePointerLocation() {
        if (this.mPointerLocationView != null) {
            return;
        }
        this.mPointerLocationView = new com.android.internal.widget.PointerLocationView(this.mContext);
        this.mPointerLocationView.setPrintCoords(false);
        android.view.WindowManager.LayoutParams layoutParams = new android.view.WindowManager.LayoutParams();
        layoutParams.type = 2015;
        layoutParams.flags = com.android.internal.util.FrameworkStatsLog.TV_CAS_SESSION_OPEN_STATUS;
        layoutParams.privateFlags |= 16;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.layoutInDisplayCutoutMode = 3;
        if (android.app.ActivityManager.isHighEndGfx()) {
            layoutParams.flags |= 16777216;
            layoutParams.privateFlags |= 2;
        }
        layoutParams.format = -3;
        layoutParams.setTitle("PointerLocation - display " + getDisplayId());
        layoutParams.inputFeatures = layoutParams.inputFeatures | 1;
        ((android.view.WindowManager) this.mContext.getSystemService(android.view.WindowManager.class)).addView(this.mPointerLocationView, layoutParams);
        this.mDisplayContent.registerPointerEventListener(this.mPointerLocationView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disablePointerLocation() {
        if (this.mPointerLocationView == null) {
            return;
        }
        if (!this.mDisplayContent.isRemoved()) {
            this.mDisplayContent.unregisterPointerEventListener(this.mPointerLocationView);
        }
        ((android.view.WindowManager) this.mContext.getSystemService(android.view.WindowManager.class)).removeView(this.mPointerLocationView);
        this.mPointerLocationView = null;
    }

    boolean isWindowExcludedFromContent(com.android.server.wm.WindowState windowState) {
        return (windowState == null || this.mPointerLocationView == null || windowState.mClient != this.mPointerLocationView.getWindowToken()) ? false : true;
    }

    void release() {
        this.mDisplayContent.mTransitionController.unregisterLegacyListener(this.mAppTransitionListener);
        android.os.Handler handler = this.mHandler;
        final com.android.internal.policy.GestureNavigationSettingsObserver gestureNavigationSettingsObserver = this.mGestureNavigationSettingsObserver;
        java.util.Objects.requireNonNull(gestureNavigationSettingsObserver);
        handler.post(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                gestureNavigationSettingsObserver.unregister();
            }
        });
        android.os.Handler handler2 = this.mHandler;
        final com.android.internal.policy.ForceShowNavBarSettingsObserver forceShowNavBarSettingsObserver = this.mForceShowNavBarSettingsObserver;
        java.util.Objects.requireNonNull(forceShowNavBarSettingsObserver);
        handler2.post(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayPolicy$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                forceShowNavBarSettingsObserver.unregister();
            }
        });
        if (!android.view.ViewRootImpl.CLIENT_TRANSIENT && !android.view.ViewRootImpl.CLIENT_IMMERSIVE_CONFIRMATION) {
            this.mImmersiveModeConfirmation.release();
        }
        if (this.mService.mPointerLocationEnabled) {
            setPointerLocationEnabled(false);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean isOverlappingWithNavBar(@android.annotation.NonNull com.android.server.wm.WindowState windowState) {
        if (!windowState.isVisible()) {
            return false;
        }
        return intersectsAnyInsets(windowState.isDimming() ? windowState.getBounds() : windowState.getFrame(), windowState.getInsetsState(), android.view.WindowInsets.Type.navigationBars());
    }

    private static boolean intersectsAnyInsets(android.graphics.Rect rect, android.view.InsetsState insetsState, int i) {
        for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            android.view.InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
            if ((sourceAt.getType() & i) != 0 && sourceAt.isVisible() && android.graphics.Rect.intersects(rect, sourceAt.getFrame())) {
                return true;
            }
        }
        return false;
    }

    boolean shouldAttachNavBarToAppDuringTransition() {
        return this.mShouldAttachNavBarToAppDuringTransition && this.mNavigationBar != null;
    }
}
