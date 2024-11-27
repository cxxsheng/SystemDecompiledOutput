package com.android.internal.policy;

/* loaded from: classes5.dex */
public class PhoneWindow extends android.view.Window implements com.android.internal.view.menu.MenuBuilder.Callback {
    private static final java.lang.String ACTION_BAR_TAG = "android:ActionBar";
    private static final int CUSTOM_TITLE_COMPATIBLE_FEATURES = 13505;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_BACKGROUND_FADE_DURATION_MS = 300;
    private static final long ENFORCE_EDGE_TO_EDGE = 309578419;
    static final int FLAG_RESOURCE_SET_ICON = 1;
    static final int FLAG_RESOURCE_SET_ICON_FALLBACK = 4;
    static final int FLAG_RESOURCE_SET_LOGO = 2;
    private static final java.lang.String FOCUSED_ID_TAG = "android:focusedViewId";
    private static final java.lang.String PANELS_TAG = "android:Panels";
    private static final java.lang.String TAG = "PhoneWindow";
    private static final java.lang.String VIEWS_TAG = "android:views";
    private com.android.internal.policy.PhoneWindow.ActionMenuPresenterCallback mActionMenuPresenterCallback;
    private android.view.ViewRootImpl.ActivityConfigCallback mActivityConfigCallback;
    private java.lang.Boolean mAllowEnterTransitionOverlap;
    private java.lang.Boolean mAllowFloatingWindowsFillScreen;
    private java.lang.Boolean mAllowReturnTransitionOverlap;
    private boolean mAlwaysReadCloseOnTouchAttr;
    private android.media.AudioManager mAudioManager;
    private int mAudioMode;
    private int mBackgroundBlurRadius;
    android.graphics.drawable.Drawable mBackgroundDrawable;
    private long mBackgroundFadeDurationMillis;
    android.graphics.drawable.Drawable mBackgroundFallbackDrawable;
    private android.widget.ProgressBar mCircularProgressBar;
    private boolean mClipToOutline;
    private boolean mClosingActionMenu;
    android.view.ViewGroup mContentParent;
    private boolean mContentParentExplicitlySet;
    private android.transition.Scene mContentScene;
    com.android.internal.view.menu.ContextMenuBuilder mContextMenu;
    final com.android.internal.policy.PhoneWindow.PhoneWindowMenuCallback mContextMenuCallback;
    com.android.internal.view.menu.MenuHelper mContextMenuHelper;
    private com.android.internal.policy.DecorView mDecor;
    private int mDecorCaptionShade;
    com.android.internal.widget.DecorContentParent mDecorContentParent;
    boolean mDecorFitsSystemWindows;
    private com.android.internal.policy.PhoneWindow.DrawableFeatureState[] mDrawables;
    private boolean mEdgeToEdgeEnforced;
    private float mElevation;
    boolean mEnsureNavigationBarContrastWhenTransparent;
    boolean mEnsureStatusBarContrastWhenTransparent;
    private android.transition.Transition mEnterTransition;
    private android.transition.Transition mExitTransition;
    android.util.TypedValue mFixedHeightMajor;
    android.util.TypedValue mFixedHeightMinor;
    android.util.TypedValue mFixedWidthMajor;
    android.util.TypedValue mFixedWidthMinor;
    private boolean mForceDecorInstall;
    private boolean mForcedNavigationBarColor;
    private boolean mForcedStatusBarColor;
    private int mFrameResource;
    private android.widget.ProgressBar mHorizontalProgressBar;
    int mIconRes;
    private int mInvalidatePanelMenuFeatures;
    private boolean mInvalidatePanelMenuPosted;
    private final java.lang.Runnable mInvalidatePanelMenuRunnable;
    boolean mIsFloating;
    private boolean mIsFrameRatePowerSavingsBalanced;
    private boolean mIsStartingWindow;
    private boolean mIsTranslucent;
    private android.app.KeyguardManager mKeyguardManager;
    private android.view.LayoutInflater mLayoutInflater;
    private android.widget.ImageView mLeftIconView;
    private boolean mLoadElevation;
    int mLogoRes;
    private android.media.session.MediaController mMediaController;
    private android.media.session.MediaSessionManager mMediaSessionManager;
    final android.util.TypedValue mMinWidthMajor;
    final android.util.TypedValue mMinWidthMinor;
    int mNavigationBarColor;
    int mNavigationBarDividerColor;
    private android.media.AudioManager.OnModeChangedListener mOnModeChangedListener;
    int mPanelChordingKey;
    private com.android.internal.policy.PhoneWindow.PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private com.android.internal.policy.PhoneWindow.PanelFeatureState[] mPanels;
    com.android.internal.policy.PhoneWindow.PanelFeatureState mPreparedPanel;
    private final android.window.ProxyOnBackInvokedDispatcher mProxyOnBackInvokedDispatcher;
    private android.transition.Transition mReenterTransition;
    public final boolean mRenderShadowsInCompositor;
    int mResourcesSetFlags;
    private android.transition.Transition mReturnTransition;
    private android.widget.ImageView mRightIconView;
    private android.transition.Transition mSharedElementEnterTransition;
    private android.transition.Transition mSharedElementExitTransition;
    private android.transition.Transition mSharedElementReenterTransition;
    private android.transition.Transition mSharedElementReturnTransition;
    private java.lang.Boolean mSharedElementsUseOverlay;
    int mStatusBarColor;
    private boolean mSupportsPictureInPicture;
    android.view.InputQueue.Callback mTakeInputQueueCallback;
    android.view.SurfaceHolder.Callback2 mTakeSurfaceCallback;
    private int mTextColor;
    private int mTheme;
    private java.lang.CharSequence mTitle;
    private int mTitleColor;
    private android.widget.TextView mTitleView;
    private android.transition.TransitionManager mTransitionManager;
    private int mUiOptions;
    private boolean mUseDecorContext;
    private int mVolumeControlStreamType;
    private static final android.view.Window.OnContentApplyWindowInsetsListener sDefaultContentInsetsApplier = new android.view.Window.OnContentApplyWindowInsetsListener() { // from class: com.android.internal.policy.PhoneWindow$$ExternalSyntheticLambda1
        @Override // android.view.Window.OnContentApplyWindowInsetsListener
        public final android.util.Pair onContentApplyWindowInsets(android.view.View view, android.view.WindowInsets windowInsets) {
            return com.android.internal.policy.PhoneWindow.lambda$static$0(view, windowInsets);
        }
    };
    private static final android.transition.Transition USE_DEFAULT_TRANSITION = new android.transition.TransitionSet();
    private static final int ENFORCE_EDGE_TO_EDGE_SDK_VERSION = android.os.SystemProperties.getInt("persist.wm.debug.default_e2e_since_sdk", Integer.MAX_VALUE);
    static final com.android.internal.policy.PhoneWindow.RotationWatcher sRotationWatcher = new com.android.internal.policy.PhoneWindow.RotationWatcher();

    static /* synthetic */ android.util.Pair lambda$static$0(android.view.View view, android.view.WindowInsets windowInsets) {
        if ((view.getWindowSystemUiVisibility() & 1536) != 0) {
            return new android.util.Pair(android.graphics.Insets.NONE, windowInsets);
        }
        android.graphics.Insets systemWindowInsets = windowInsets.getSystemWindowInsets();
        return new android.util.Pair(systemWindowInsets, windowInsets.inset(systemWindowInsets).consumeSystemWindowInsets());
    }

    static class WindowManagerHolder {
        static final android.view.IWindowManager sWindowManager = android.view.IWindowManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.WINDOW_SERVICE));

        WindowManagerHolder() {
        }
    }

    public PhoneWindow(android.content.Context context) {
        super(context);
        this.mContextMenuCallback = new com.android.internal.policy.PhoneWindow.PhoneWindowMenuCallback(this);
        this.mMinWidthMajor = new android.util.TypedValue();
        this.mMinWidthMinor = new android.util.TypedValue();
        this.mForceDecorInstall = false;
        this.mContentParentExplicitlySet = false;
        this.mBackgroundDrawable = null;
        this.mBackgroundFallbackDrawable = null;
        this.mBackgroundBlurRadius = 0;
        this.mLoadElevation = true;
        this.mFrameResource = 0;
        this.mTextColor = 0;
        this.mStatusBarColor = 0;
        this.mNavigationBarColor = 0;
        this.mNavigationBarDividerColor = 0;
        this.mForcedStatusBarColor = false;
        this.mForcedNavigationBarColor = false;
        this.mTitle = null;
        this.mTitleColor = 0;
        this.mAlwaysReadCloseOnTouchAttr = false;
        this.mVolumeControlStreamType = Integer.MIN_VALUE;
        this.mAudioMode = 0;
        this.mUiOptions = 0;
        this.mInvalidatePanelMenuRunnable = new java.lang.Runnable() { // from class: com.android.internal.policy.PhoneWindow.1
            @Override // java.lang.Runnable
            public void run() {
                for (int i = 0; i <= 13; i++) {
                    if ((com.android.internal.policy.PhoneWindow.this.mInvalidatePanelMenuFeatures & (1 << i)) != 0) {
                        com.android.internal.policy.PhoneWindow.this.doInvalidatePanelMenu(i);
                    }
                }
                com.android.internal.policy.PhoneWindow.this.mInvalidatePanelMenuPosted = false;
                com.android.internal.policy.PhoneWindow.this.mInvalidatePanelMenuFeatures = 0;
            }
        };
        this.mEnterTransition = null;
        this.mReturnTransition = USE_DEFAULT_TRANSITION;
        this.mExitTransition = null;
        this.mReenterTransition = USE_DEFAULT_TRANSITION;
        this.mSharedElementEnterTransition = null;
        this.mSharedElementReturnTransition = USE_DEFAULT_TRANSITION;
        this.mSharedElementExitTransition = null;
        this.mSharedElementReenterTransition = USE_DEFAULT_TRANSITION;
        this.mBackgroundFadeDurationMillis = -1L;
        this.mTheme = -1;
        this.mDecorCaptionShade = 0;
        this.mUseDecorContext = false;
        this.mIsFrameRatePowerSavingsBalanced = true;
        this.mDecorFitsSystemWindows = true;
        this.mLayoutInflater = android.view.LayoutInflater.from(context);
        this.mRenderShadowsInCompositor = android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.DEVELOPMENT_RENDER_SHADOWS_IN_COMPOSITOR, 1) != 0;
        this.mProxyOnBackInvokedDispatcher = new android.window.ProxyOnBackInvokedDispatcher(context);
        this.mAllowFloatingWindowsFillScreen = java.lang.Boolean.valueOf(context.getResources().getBoolean(com.android.internal.R.bool.config_allowFloatingWindowsFillScreen));
    }

    public PhoneWindow(android.content.Context context, android.view.Window window, android.view.ViewRootImpl.ActivityConfigCallback activityConfigCallback) {
        this(context);
        boolean z;
        boolean z2 = true;
        this.mUseDecorContext = true;
        if (window != null) {
            this.mDecor = (com.android.internal.policy.DecorView) window.getDecorView();
            this.mElevation = window.getElevation();
            this.mLoadElevation = false;
            this.mForceDecorInstall = true;
            getAttributes().token = window.getAttributes().token;
            android.view.ViewRootImpl viewRootImpl = this.mDecor.getViewRootImpl();
            if (viewRootImpl != null) {
                viewRootImpl.getOnBackInvokedDispatcher().clear();
                onViewRootImplSet(viewRootImpl);
            }
        }
        if (android.provider.Settings.Global.getInt(context.getContentResolver(), android.provider.Settings.Global.DEVELOPMENT_FORCE_RESIZABLE_ACTIVITIES, 0) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z && !context.getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_PICTURE_IN_PICTURE)) {
            z2 = false;
        }
        this.mSupportsPictureInPicture = z2;
        this.mActivityConfigCallback = activityConfigCallback;
    }

    public static boolean isEdgeToEdgeEnforced(android.content.pm.ApplicationInfo applicationInfo, boolean z, android.content.res.TypedArray typedArray) {
        if (typedArray.getBoolean(69, false)) {
            return false;
        }
        if (applicationInfo.targetSdkVersion < ENFORCE_EDGE_TO_EDGE_SDK_VERSION) {
            if (!com.android.window.flags.Flags.enforceEdgeToEdge()) {
                return false;
            }
            if (z) {
                if (!android.app.compat.CompatChanges.isChangeEnabled(ENFORCE_EDGE_TO_EDGE)) {
                    return false;
                }
            } else if (!applicationInfo.isChangeEnabled(ENFORCE_EDGE_TO_EDGE)) {
                return false;
            }
        }
        return true;
    }

    @Override // android.view.Window
    public final void setContainer(android.view.Window window) {
        super.setContainer(window);
    }

    @Override // android.view.Window
    public boolean requestFeature(int i) {
        if (this.mContentParentExplicitlySet) {
            throw new android.util.AndroidRuntimeException("requestFeature() must be called before adding content");
        }
        int features = getFeatures();
        int i2 = (1 << i) | features;
        if ((i2 & 128) != 0 && (i2 & (-13506)) != 0) {
            throw new android.util.AndroidRuntimeException("You cannot combine custom titles with other title features");
        }
        if ((features & 2) != 0 && i == 8) {
            return false;
        }
        if ((features & 256) != 0 && i == 1) {
            removeFeature(8);
        }
        if (i == 5 && getContext().getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_WATCH)) {
            throw new android.util.AndroidRuntimeException("You cannot use indeterminate progress on a watch.");
        }
        return super.requestFeature(i);
    }

    @Override // android.view.Window
    public void setUiOptions(int i) {
        this.mUiOptions = i;
    }

    @Override // android.view.Window
    public void setUiOptions(int i, int i2) {
        this.mUiOptions = (i & i2) | (this.mUiOptions & (~i2));
    }

    @Override // android.view.Window
    public android.transition.TransitionManager getTransitionManager() {
        return this.mTransitionManager;
    }

    @Override // android.view.Window
    public void setTransitionManager(android.transition.TransitionManager transitionManager) {
        this.mTransitionManager = transitionManager;
    }

    @Override // android.view.Window
    public android.transition.Scene getContentScene() {
        return this.mContentScene;
    }

    @Override // android.view.Window
    public void setContentView(int i) {
        if (this.mContentParent == null) {
            installDecor();
        } else if (!hasFeature(12)) {
            this.mContentParent.removeAllViews();
        }
        if (hasFeature(12)) {
            transitionTo(android.transition.Scene.getSceneForLayout(this.mContentParent, i, getContext()));
        } else {
            this.mLayoutInflater.inflate(i, this.mContentParent);
        }
        this.mContentParent.requestApplyInsets();
        android.view.Window.Callback callback = getCallback();
        if (callback != null && !isDestroyed()) {
            callback.onContentChanged();
        }
        this.mContentParentExplicitlySet = true;
    }

    @Override // android.view.Window
    public void setContentView(android.view.View view) {
        setContentView(view, new android.view.ViewGroup.LayoutParams(-1, -1));
    }

    @Override // android.view.Window
    public void setContentView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        if (this.mContentParent == null) {
            installDecor();
        } else if (!hasFeature(12)) {
            this.mContentParent.removeAllViews();
        }
        if (hasFeature(12)) {
            view.setLayoutParams(layoutParams);
            transitionTo(new android.transition.Scene(this.mContentParent, view));
        } else {
            this.mContentParent.addView(view, layoutParams);
        }
        this.mContentParent.requestApplyInsets();
        android.view.Window.Callback callback = getCallback();
        if (callback != null && !isDestroyed()) {
            callback.onContentChanged();
        }
        this.mContentParentExplicitlySet = true;
    }

    @Override // android.view.Window
    public void addContentView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams) {
        if (this.mContentParent == null) {
            installDecor();
        }
        if (hasFeature(12)) {
            android.util.Log.v(TAG, "addContentView does not support content transitions");
        }
        this.mContentParent.addView(view, layoutParams);
        this.mContentParent.requestApplyInsets();
        android.view.Window.Callback callback = getCallback();
        if (callback != null && !isDestroyed()) {
            callback.onContentChanged();
        }
    }

    @Override // android.view.Window
    public void clearContentView() {
        if (this.mDecor != null) {
            this.mDecor.clearContentView();
        }
    }

    private void transitionTo(android.transition.Scene scene) {
        if (this.mContentScene == null) {
            scene.enter();
        } else {
            this.mTransitionManager.transitionTo(scene);
        }
        this.mContentScene = scene;
    }

    @Override // android.view.Window
    public android.view.View getCurrentFocus() {
        if (this.mDecor != null) {
            return this.mDecor.findFocus();
        }
        return null;
    }

    @Override // android.view.Window
    public void takeSurface(android.view.SurfaceHolder.Callback2 callback2) {
        this.mTakeSurfaceCallback = callback2;
    }

    @Override // android.view.Window
    public void takeInputQueue(android.view.InputQueue.Callback callback) {
        this.mTakeInputQueueCallback = callback;
    }

    @Override // android.view.Window
    public boolean isFloating() {
        return this.mIsFloating;
    }

    public boolean isTranslucent() {
        return this.mIsTranslucent;
    }

    boolean isShowingWallpaper() {
        return (getAttributes().flags & 1048576) != 0;
    }

    @Override // android.view.Window
    public android.view.LayoutInflater getLayoutInflater() {
        return this.mLayoutInflater;
    }

    @Override // android.view.Window
    public void setTitle(java.lang.CharSequence charSequence) {
        setTitle(charSequence, true);
    }

    public void setTitle(java.lang.CharSequence charSequence, boolean z) {
        android.view.ViewRootImpl viewRootImpl;
        if (this.mTitleView != null) {
            this.mTitleView.lambda$setTextAsync$0(charSequence);
        } else if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setWindowTitle(charSequence);
        }
        this.mTitle = charSequence;
        if (z) {
            android.view.WindowManager.LayoutParams attributes = getAttributes();
            if (!android.text.TextUtils.equals(charSequence, attributes.accessibilityTitle)) {
                attributes.accessibilityTitle = android.text.TextUtils.stringOrSpannedString(charSequence);
                if (this.mDecor != null && (viewRootImpl = this.mDecor.getViewRootImpl()) != null) {
                    viewRootImpl.onWindowTitleChanged();
                }
                dispatchWindowAttributesChanged(getAttributes());
            }
        }
    }

    @Override // android.view.Window
    @java.lang.Deprecated
    public void setTitleColor(int i) {
        if (this.mTitleView != null) {
            this.mTitleView.setTextColor(i);
        }
        this.mTitleColor = i;
    }

    public final boolean preparePanel(com.android.internal.policy.PhoneWindow.PanelFeatureState panelFeatureState, android.view.KeyEvent keyEvent) {
        if (isDestroyed()) {
            return false;
        }
        if (panelFeatureState.isPrepared) {
            return true;
        }
        if (this.mPreparedPanel != null && this.mPreparedPanel != panelFeatureState) {
            closePanel(this.mPreparedPanel, false);
        }
        android.view.Window.Callback callback = getCallback();
        if (callback != null) {
            panelFeatureState.createdPanelView = callback.onCreatePanelView(panelFeatureState.featureId);
        }
        boolean z = panelFeatureState.featureId == 0 || panelFeatureState.featureId == 8;
        if (z && this.mDecorContentParent != null) {
            this.mDecorContentParent.setMenuPrepared();
        }
        if (panelFeatureState.createdPanelView == null) {
            if (panelFeatureState.menu == null || panelFeatureState.refreshMenuContent) {
                if (panelFeatureState.menu == null && (!initializePanelMenu(panelFeatureState) || panelFeatureState.menu == null)) {
                    return false;
                }
                if (z && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new com.android.internal.policy.PhoneWindow.ActionMenuPresenterCallback();
                    }
                    this.mDecorContentParent.setMenu(panelFeatureState.menu, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.menu.stopDispatchingItemsChanged();
                if (callback == null || !callback.onCreatePanelMenu(panelFeatureState.featureId, panelFeatureState.menu)) {
                    panelFeatureState.setMenu(null);
                    if (z && this.mDecorContentParent != null) {
                        this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                    }
                    return false;
                }
                panelFeatureState.refreshMenuContent = false;
            }
            panelFeatureState.menu.stopDispatchingItemsChanged();
            if (panelFeatureState.frozenActionViewState != null) {
                panelFeatureState.menu.restoreActionViewStates(panelFeatureState.frozenActionViewState);
                panelFeatureState.frozenActionViewState = null;
            }
            if (!callback.onPreparePanel(panelFeatureState.featureId, panelFeatureState.createdPanelView, panelFeatureState.menu)) {
                if (z && this.mDecorContentParent != null) {
                    this.mDecorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.menu.startDispatchingItemsChanged();
                return false;
            }
            panelFeatureState.qwertyMode = android.view.KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1;
            panelFeatureState.menu.setQwertyMode(panelFeatureState.qwertyMode);
            panelFeatureState.menu.startDispatchingItemsChanged();
        }
        panelFeatureState.isPrepared = true;
        panelFeatureState.isHandled = false;
        this.mPreparedPanel = panelFeatureState;
        return true;
    }

    @Override // android.view.Window
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        com.android.internal.policy.PhoneWindow.PanelFeatureState panelState;
        if (this.mDecorContentParent == null && (panelState = getPanelState(0, false)) != null && panelState.menu != null) {
            if (panelState.isOpen) {
                android.os.Bundle bundle = new android.os.Bundle();
                if (panelState.iconMenuPresenter != null) {
                    panelState.iconMenuPresenter.saveHierarchyState(bundle);
                }
                if (panelState.listMenuPresenter != null) {
                    panelState.listMenuPresenter.saveHierarchyState(bundle);
                }
                clearMenuViews(panelState);
                reopenMenu(false);
                if (panelState.iconMenuPresenter != null) {
                    panelState.iconMenuPresenter.restoreHierarchyState(bundle);
                }
                if (panelState.listMenuPresenter != null) {
                    panelState.listMenuPresenter.restoreHierarchyState(bundle);
                    return;
                }
                return;
            }
            clearMenuViews(panelState);
        }
    }

    @Override // android.view.Window
    public void onMultiWindowModeChanged() {
        if (this.mDecor != null) {
            this.mDecor.onConfigurationChanged(getContext().getResources().getConfiguration());
        }
    }

    @Override // android.view.Window
    public void onPictureInPictureModeChanged(boolean z) {
        if (this.mDecor != null) {
            this.mDecor.updatePictureInPictureOutlineProvider(z);
        }
    }

    private static void clearMenuViews(com.android.internal.policy.PhoneWindow.PanelFeatureState panelFeatureState) {
        panelFeatureState.createdPanelView = null;
        panelFeatureState.refreshDecorView = true;
        panelFeatureState.clearMenuPresenters();
    }

    @Override // android.view.Window
    public final void openPanel(int i, android.view.KeyEvent keyEvent) {
        if (i == 0 && this.mDecorContentParent != null && this.mDecorContentParent.canShowOverflowMenu() && !android.view.ViewConfiguration.get(getContext()).hasPermanentMenuKey()) {
            this.mDecorContentParent.showOverflowMenu();
        } else {
            openPanel(getPanelState(i, true), keyEvent);
        }
    }

    private void openPanel(com.android.internal.policy.PhoneWindow.PanelFeatureState panelFeatureState, android.view.KeyEvent keyEvent) {
        int i;
        int i2;
        android.view.ViewGroup.LayoutParams layoutParams;
        if (panelFeatureState.isOpen || isDestroyed()) {
            return;
        }
        if (panelFeatureState.featureId == 0) {
            android.content.Context context = getContext();
            boolean z = (context.getResources().getConfiguration().screenLayout & 15) == 4;
            boolean z2 = context.getApplicationInfo().targetSdkVersion >= 11;
            if (z && z2) {
                return;
            }
        }
        android.view.Window.Callback callback = getCallback();
        if (callback != null && !callback.onMenuOpened(panelFeatureState.featureId, panelFeatureState.menu)) {
            closePanel(panelFeatureState, true);
            return;
        }
        android.view.WindowManager windowManager = getWindowManager();
        if (windowManager == null || !preparePanel(panelFeatureState, keyEvent)) {
            return;
        }
        int i3 = -1;
        if (panelFeatureState.decorView == null || panelFeatureState.refreshDecorView) {
            if (panelFeatureState.decorView == null) {
                if (!initializePanelDecor(panelFeatureState) || panelFeatureState.decorView == null) {
                    return;
                }
            } else if (panelFeatureState.refreshDecorView && panelFeatureState.decorView.getChildCount() > 0) {
                panelFeatureState.decorView.removeAllViews();
            }
            if (!initializePanelContent(panelFeatureState) || !panelFeatureState.hasPanelItems()) {
                return;
            }
            android.view.ViewGroup.LayoutParams layoutParams2 = panelFeatureState.shownPanelView.getLayoutParams();
            if (layoutParams2 == null) {
                layoutParams2 = new android.view.ViewGroup.LayoutParams(-2, -2);
            }
            if (layoutParams2.width == -1) {
                i = panelFeatureState.fullBackground;
            } else {
                i3 = -2;
                i = panelFeatureState.background;
            }
            panelFeatureState.decorView.setWindowBackground(getContext().getDrawable(i));
            android.view.ViewParent parent = panelFeatureState.shownPanelView.getParent();
            if (parent != null && (parent instanceof android.view.ViewGroup)) {
                ((android.view.ViewGroup) parent).removeView(panelFeatureState.shownPanelView);
            }
            panelFeatureState.decorView.addView(panelFeatureState.shownPanelView, layoutParams2);
            if (!panelFeatureState.shownPanelView.hasFocus()) {
                panelFeatureState.shownPanelView.requestFocus();
            }
            i2 = i3;
        } else if (!panelFeatureState.isInListMode()) {
            i2 = -1;
        } else if (panelFeatureState.createdPanelView != null && (layoutParams = panelFeatureState.createdPanelView.getLayoutParams()) != null && layoutParams.width == -1) {
            i2 = -1;
        } else {
            i2 = -2;
        }
        if (!panelFeatureState.hasPanelItems()) {
            return;
        }
        panelFeatureState.isHandled = false;
        android.view.WindowManager.LayoutParams layoutParams3 = new android.view.WindowManager.LayoutParams(i2, -2, panelFeatureState.x, panelFeatureState.y, 1003, 8519680, panelFeatureState.decorView.mDefaultOpacity);
        if (panelFeatureState.isCompact) {
            layoutParams3.gravity = getOptionsPanelGravity();
            sRotationWatcher.addWindow(this);
        } else {
            layoutParams3.gravity = panelFeatureState.gravity;
        }
        layoutParams3.windowAnimations = panelFeatureState.windowAnimations;
        windowManager.addView(panelFeatureState.decorView, layoutParams3);
        panelFeatureState.isOpen = true;
    }

    @Override // android.view.Window
    public final void closePanel(int i) {
        if (i == 0 && this.mDecorContentParent != null && this.mDecorContentParent.canShowOverflowMenu() && !android.view.ViewConfiguration.get(getContext()).hasPermanentMenuKey()) {
            this.mDecorContentParent.hideOverflowMenu();
        } else if (i == 6) {
            closeContextMenu();
        } else {
            closePanel(getPanelState(i, true), true);
        }
    }

    public final void closePanel(com.android.internal.policy.PhoneWindow.PanelFeatureState panelFeatureState, boolean z) {
        if (z && panelFeatureState.featureId == 0 && this.mDecorContentParent != null && this.mDecorContentParent.isOverflowMenuShowing()) {
            checkCloseActionMenu(panelFeatureState.menu);
            return;
        }
        android.view.WindowManager windowManager = getWindowManager();
        if (windowManager != null && panelFeatureState.isOpen) {
            if (panelFeatureState.decorView != null) {
                windowManager.removeView(panelFeatureState.decorView);
                if (panelFeatureState.isCompact) {
                    sRotationWatcher.removeWindow(this);
                }
            }
            if (z) {
                callOnPanelClosed(panelFeatureState.featureId, panelFeatureState, null);
            }
        }
        panelFeatureState.isPrepared = false;
        panelFeatureState.isHandled = false;
        panelFeatureState.isOpen = false;
        panelFeatureState.shownPanelView = null;
        if (panelFeatureState.isInExpandedMode) {
            panelFeatureState.refreshDecorView = true;
            panelFeatureState.isInExpandedMode = false;
        }
        if (this.mPreparedPanel == panelFeatureState) {
            this.mPreparedPanel = null;
            this.mPanelChordingKey = 0;
        }
    }

    void checkCloseActionMenu(android.view.Menu menu) {
        if (this.mClosingActionMenu) {
            return;
        }
        this.mClosingActionMenu = true;
        this.mDecorContentParent.dismissPopups();
        android.view.Window.Callback callback = getCallback();
        if (callback != null && !isDestroyed()) {
            callback.onPanelClosed(8, menu);
        }
        this.mClosingActionMenu = false;
    }

    @Override // android.view.Window
    public final void togglePanel(int i, android.view.KeyEvent keyEvent) {
        com.android.internal.policy.PhoneWindow.PanelFeatureState panelState = getPanelState(i, true);
        if (panelState.isOpen) {
            closePanel(panelState, true);
        } else {
            openPanel(panelState, keyEvent);
        }
    }

    @Override // android.view.Window
    public void invalidatePanelMenu(int i) {
        this.mInvalidatePanelMenuFeatures = (1 << i) | this.mInvalidatePanelMenuFeatures;
        if (!this.mInvalidatePanelMenuPosted && this.mDecor != null) {
            this.mDecor.postOnAnimation(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuPosted = true;
        }
    }

    void doPendingInvalidatePanelMenu() {
        if (this.mInvalidatePanelMenuPosted) {
            this.mDecor.removeCallbacks(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuRunnable.run();
        }
    }

    void doInvalidatePanelMenu(int i) {
        com.android.internal.policy.PhoneWindow.PanelFeatureState panelState;
        com.android.internal.policy.PhoneWindow.PanelFeatureState panelState2 = getPanelState(i, false);
        if (panelState2 == null) {
            return;
        }
        if (panelState2.menu != null) {
            android.os.Bundle bundle = new android.os.Bundle();
            panelState2.menu.saveActionViewStates(bundle);
            if (bundle.size() > 0) {
                panelState2.frozenActionViewState = bundle;
            }
            panelState2.menu.stopDispatchingItemsChanged();
            panelState2.menu.clear();
        }
        panelState2.refreshMenuContent = true;
        panelState2.refreshDecorView = true;
        if ((i == 8 || i == 0) && this.mDecorContentParent != null && (panelState = getPanelState(0, false)) != null) {
            panelState.isPrepared = false;
            preparePanel(panelState, null);
        }
    }

    public final boolean onKeyDownPanel(int i, android.view.KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyEvent.getRepeatCount() == 0) {
            this.mPanelChordingKey = keyCode;
            com.android.internal.policy.PhoneWindow.PanelFeatureState panelState = getPanelState(i, false);
            if (panelState != null && !panelState.isOpen) {
                return preparePanel(panelState, keyEvent);
            }
        }
        return false;
    }

    public final void onKeyUpPanel(int i, android.view.KeyEvent keyEvent) {
        boolean z;
        boolean z2;
        if (this.mPanelChordingKey != 0) {
            this.mPanelChordingKey = 0;
            com.android.internal.policy.PhoneWindow.PanelFeatureState panelState = getPanelState(i, false);
            if (keyEvent.isCanceled()) {
                return;
            }
            if ((this.mDecor != null && this.mDecor.mPrimaryActionMode != null) || panelState == null) {
                return;
            }
            if (i == 0 && this.mDecorContentParent != null && this.mDecorContentParent.canShowOverflowMenu() && !android.view.ViewConfiguration.get(getContext()).hasPermanentMenuKey()) {
                if (!this.mDecorContentParent.isOverflowMenuShowing()) {
                    if (!isDestroyed() && preparePanel(panelState, keyEvent)) {
                        z = this.mDecorContentParent.showOverflowMenu();
                    }
                    z = false;
                } else {
                    z = this.mDecorContentParent.hideOverflowMenu();
                }
            } else if (panelState.isOpen || panelState.isHandled) {
                z = panelState.isOpen;
                closePanel(panelState, true);
            } else {
                if (panelState.isPrepared) {
                    if (!panelState.refreshMenuContent) {
                        z2 = true;
                    } else {
                        panelState.isPrepared = false;
                        z2 = preparePanel(panelState, keyEvent);
                    }
                    if (z2) {
                        android.util.EventLog.writeEvent(android.os.health.ServiceHealthStats.MEASUREMENT_START_SERVICE_COUNT, 0);
                        openPanel(panelState, keyEvent);
                        z = true;
                    }
                }
                z = false;
            }
            if (z) {
                android.media.AudioManager audioManager = (android.media.AudioManager) getContext().getSystemService("audio");
                if (audioManager != null) {
                    audioManager.playSoundEffect(0);
                } else {
                    android.util.Log.w(TAG, "Couldn't get audio manager");
                }
            }
        }
    }

    @Override // android.view.Window
    public final void closeAllPanels() {
        if (getWindowManager() == null) {
            return;
        }
        com.android.internal.policy.PhoneWindow.PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
        for (int i = 0; i < length; i++) {
            com.android.internal.policy.PhoneWindow.PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
            if (panelFeatureState != null) {
                closePanel(panelFeatureState, true);
            }
        }
        closeContextMenu();
    }

    private synchronized void closeContextMenu() {
        if (this.mContextMenu != null) {
            this.mContextMenu.close();
            dismissContextMenu();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void dismissContextMenu() {
        this.mContextMenu = null;
        if (this.mContextMenuHelper != null) {
            this.mContextMenuHelper.dismiss();
            this.mContextMenuHelper = null;
        }
    }

    @Override // android.view.Window
    public boolean performPanelShortcut(int i, int i2, android.view.KeyEvent keyEvent, int i3) {
        return performPanelShortcut(getPanelState(i, false), i2, keyEvent, i3);
    }

    boolean performPanelShortcut(com.android.internal.policy.PhoneWindow.PanelFeatureState panelFeatureState, int i, android.view.KeyEvent keyEvent, int i2) {
        boolean z = false;
        if (keyEvent.isSystem() || panelFeatureState == null) {
            return false;
        }
        if ((panelFeatureState.isPrepared || preparePanel(panelFeatureState, keyEvent)) && panelFeatureState.menu != null) {
            z = panelFeatureState.menu.performShortcut(i, keyEvent, i2);
        }
        if (z) {
            panelFeatureState.isHandled = true;
            if ((i2 & 1) == 0 && this.mDecorContentParent == null) {
                closePanel(panelFeatureState, true);
            }
        }
        return z;
    }

    @Override // android.view.Window
    public boolean performPanelIdentifierAction(int i, int i2, int i3) {
        com.android.internal.policy.PhoneWindow.PanelFeatureState panelState = getPanelState(i, true);
        if (!preparePanel(panelState, new android.view.KeyEvent(0, 82)) || panelState.menu == null) {
            return false;
        }
        boolean performIdentifierAction = panelState.menu.performIdentifierAction(i2, i3);
        if (this.mDecorContentParent == null) {
            closePanel(panelState, true);
        }
        return performIdentifierAction;
    }

    public com.android.internal.policy.PhoneWindow.PanelFeatureState findMenuPanel(android.view.Menu menu) {
        com.android.internal.policy.PhoneWindow.PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
        for (int i = 0; i < length; i++) {
            com.android.internal.policy.PhoneWindow.PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
            if (panelFeatureState != null && panelFeatureState.menu == menu) {
                return panelFeatureState;
            }
        }
        return null;
    }

    @Override // com.android.internal.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.MenuItem menuItem) {
        com.android.internal.policy.PhoneWindow.PanelFeatureState findMenuPanel;
        android.view.Window.Callback callback = getCallback();
        if (callback != null && !isDestroyed() && (findMenuPanel = findMenuPanel(menuBuilder.getRootMenu())) != null) {
            return callback.onMenuItemSelected(findMenuPanel.featureId, menuItem);
        }
        return false;
    }

    @Override // com.android.internal.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(com.android.internal.view.menu.MenuBuilder menuBuilder) {
        reopenMenu(true);
    }

    private void reopenMenu(boolean z) {
        if (this.mDecorContentParent != null && this.mDecorContentParent.canShowOverflowMenu() && (!android.view.ViewConfiguration.get(getContext()).hasPermanentMenuKey() || this.mDecorContentParent.isOverflowMenuShowPending())) {
            android.view.Window.Callback callback = getCallback();
            if (!this.mDecorContentParent.isOverflowMenuShowing() || !z) {
                if (callback != null && !isDestroyed()) {
                    if (this.mInvalidatePanelMenuPosted && (this.mInvalidatePanelMenuFeatures & 1) != 0) {
                        this.mDecor.removeCallbacks(this.mInvalidatePanelMenuRunnable);
                        this.mInvalidatePanelMenuRunnable.run();
                    }
                    com.android.internal.policy.PhoneWindow.PanelFeatureState panelState = getPanelState(0, false);
                    if (panelState != null && panelState.menu != null && !panelState.refreshMenuContent && callback.onPreparePanel(0, panelState.createdPanelView, panelState.menu)) {
                        callback.onMenuOpened(8, panelState.menu);
                        this.mDecorContentParent.showOverflowMenu();
                        return;
                    }
                    return;
                }
                return;
            }
            this.mDecorContentParent.hideOverflowMenu();
            com.android.internal.policy.PhoneWindow.PanelFeatureState panelState2 = getPanelState(0, false);
            if (panelState2 != null && callback != null && !isDestroyed()) {
                callback.onPanelClosed(8, panelState2.menu);
                return;
            }
            return;
        }
        com.android.internal.policy.PhoneWindow.PanelFeatureState panelState3 = getPanelState(0, false);
        if (panelState3 == null) {
            return;
        }
        boolean z2 = z ? !panelState3.isInExpandedMode : panelState3.isInExpandedMode;
        panelState3.refreshDecorView = true;
        closePanel(panelState3, false);
        panelState3.isInExpandedMode = z2;
        openPanel(panelState3, (android.view.KeyEvent) null);
    }

    protected boolean initializePanelMenu(com.android.internal.policy.PhoneWindow.PanelFeatureState panelFeatureState) {
        android.content.res.Resources.Theme theme;
        android.content.Context context = getContext();
        if ((panelFeatureState.featureId == 0 || panelFeatureState.featureId == 8) && this.mDecorContentParent != null) {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            android.content.res.Resources.Theme theme2 = context.getTheme();
            theme2.resolveAttribute(16843825, typedValue, true);
            if (typedValue.resourceId != 0) {
                theme = context.getResources().newTheme();
                theme.setTo(theme2);
                theme.applyStyle(typedValue.resourceId, true);
                theme.resolveAttribute(16843671, typedValue, true);
            } else {
                theme2.resolveAttribute(16843671, typedValue, true);
                theme = null;
            }
            if (typedValue.resourceId != 0) {
                if (theme == null) {
                    theme = context.getResources().newTheme();
                    theme.setTo(theme2);
                }
                theme.applyStyle(typedValue.resourceId, true);
            }
            if (theme != null) {
                android.view.ContextThemeWrapper contextThemeWrapper = new android.view.ContextThemeWrapper(context, 0);
                contextThemeWrapper.getTheme().setTo(theme);
                context = contextThemeWrapper;
            }
        }
        com.android.internal.view.menu.MenuBuilder menuBuilder = new com.android.internal.view.menu.MenuBuilder(context);
        menuBuilder.setCallback(this);
        panelFeatureState.setMenu(menuBuilder);
        return true;
    }

    protected boolean initializePanelDecor(com.android.internal.policy.PhoneWindow.PanelFeatureState panelFeatureState) {
        panelFeatureState.decorView = generateDecor(panelFeatureState.featureId);
        panelFeatureState.gravity = 81;
        panelFeatureState.setStyle(getContext());
        android.content.res.TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(null, com.android.internal.R.styleable.Window, 0, panelFeatureState.listPresenterTheme);
        float dimension = obtainStyledAttributes.getDimension(37, 0.0f);
        if (dimension != 0.0f) {
            panelFeatureState.decorView.setElevation(dimension);
        }
        obtainStyledAttributes.recycle();
        return true;
    }

    private int getOptionsPanelGravity() {
        try {
            return com.android.internal.policy.PhoneWindow.WindowManagerHolder.sWindowManager.getPreferredOptionsPanelGravity(getContext().getDisplayId());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Couldn't getOptionsPanelGravity; using default", e);
            return 81;
        }
    }

    void onOptionsPanelRotationChanged() {
        com.android.internal.policy.PhoneWindow.PanelFeatureState panelState = getPanelState(0, false);
        if (panelState == null) {
            return;
        }
        android.view.WindowManager.LayoutParams layoutParams = panelState.decorView != null ? (android.view.WindowManager.LayoutParams) panelState.decorView.getLayoutParams() : null;
        if (layoutParams != null) {
            layoutParams.gravity = getOptionsPanelGravity();
            android.view.WindowManager windowManager = getWindowManager();
            if (windowManager != null) {
                windowManager.updateViewLayout(panelState.decorView, layoutParams);
            }
        }
    }

    protected boolean initializePanelContent(com.android.internal.policy.PhoneWindow.PanelFeatureState panelFeatureState) {
        com.android.internal.view.menu.MenuView iconMenuView;
        if (panelFeatureState.createdPanelView != null) {
            panelFeatureState.shownPanelView = panelFeatureState.createdPanelView;
            return true;
        }
        if (panelFeatureState.menu == null) {
            return false;
        }
        if (this.mPanelMenuPresenterCallback == null) {
            this.mPanelMenuPresenterCallback = new com.android.internal.policy.PhoneWindow.PanelMenuPresenterCallback();
        }
        if (panelFeatureState.isInListMode()) {
            iconMenuView = panelFeatureState.getListMenuView(getContext(), this.mPanelMenuPresenterCallback);
        } else {
            iconMenuView = panelFeatureState.getIconMenuView(getContext(), this.mPanelMenuPresenterCallback);
        }
        panelFeatureState.shownPanelView = (android.view.View) iconMenuView;
        if (panelFeatureState.shownPanelView == null) {
            return false;
        }
        int windowAnimations = iconMenuView.getWindowAnimations();
        if (windowAnimations != 0) {
            panelFeatureState.windowAnimations = windowAnimations;
        }
        return true;
    }

    @Override // android.view.Window
    public boolean performContextMenuIdentifierAction(int i, int i2) {
        if (this.mContextMenu != null) {
            return this.mContextMenu.performIdentifierAction(i, i2);
        }
        return false;
    }

    @Override // android.view.Window
    public final void setElevation(float f) {
        this.mElevation = f;
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        if (this.mDecor != null) {
            this.mDecor.setElevation(f);
            attributes.setSurfaceInsets(this.mDecor, true, false);
        }
        dispatchWindowAttributesChanged(attributes);
    }

    @Override // android.view.Window
    public float getElevation() {
        return this.mElevation;
    }

    @Override // android.view.Window
    public final void setClipToOutline(boolean z) {
        this.mClipToOutline = z;
        if (this.mDecor != null) {
            this.mDecor.setClipToOutline(z);
        }
    }

    @Override // android.view.Window
    public final void setBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        if (drawable != this.mBackgroundDrawable) {
            this.mBackgroundDrawable = drawable;
            if (this.mDecor != null) {
                this.mDecor.startChanging();
                this.mDecor.setWindowBackground(drawable);
                if (this.mBackgroundFallbackDrawable != null) {
                    this.mDecor.setBackgroundFallback(drawable != null ? null : this.mBackgroundFallbackDrawable);
                }
                this.mDecor.finishChanging();
            }
        }
    }

    @Override // android.view.Window
    public final void setBackgroundBlurRadius(int i) {
        super.setBackgroundBlurRadius(i);
        if (android.view.CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED && this.mBackgroundBlurRadius != java.lang.Math.max(i, 0)) {
            this.mBackgroundBlurRadius = java.lang.Math.max(i, 0);
            this.mDecor.setBackgroundBlurRadius(this.mBackgroundBlurRadius);
        }
    }

    @Override // android.view.Window
    public final void setFeatureDrawableResource(int i, int i2) {
        if (i2 != 0) {
            com.android.internal.policy.PhoneWindow.DrawableFeatureState drawableState = getDrawableState(i, true);
            if (drawableState.resid != i2) {
                drawableState.resid = i2;
                drawableState.uri = null;
                drawableState.local = getContext().getDrawable(i2);
                updateDrawable(i, drawableState, false);
                return;
            }
            return;
        }
        setFeatureDrawable(i, null);
    }

    @Override // android.view.Window
    public final void setFeatureDrawableUri(int i, android.net.Uri uri) {
        if (uri != null) {
            com.android.internal.policy.PhoneWindow.DrawableFeatureState drawableState = getDrawableState(i, true);
            if (drawableState.uri == null || !drawableState.uri.equals(uri)) {
                drawableState.resid = 0;
                drawableState.uri = uri;
                drawableState.local = loadImageURI(uri);
                updateDrawable(i, drawableState, false);
                return;
            }
            return;
        }
        setFeatureDrawable(i, null);
    }

    @Override // android.view.Window
    public final void setFeatureDrawable(int i, android.graphics.drawable.Drawable drawable) {
        com.android.internal.policy.PhoneWindow.DrawableFeatureState drawableState = getDrawableState(i, true);
        drawableState.resid = 0;
        drawableState.uri = null;
        if (drawableState.local != drawable) {
            drawableState.local = drawable;
            updateDrawable(i, drawableState, false);
        }
    }

    @Override // android.view.Window
    public void setFeatureDrawableAlpha(int i, int i2) {
        com.android.internal.policy.PhoneWindow.DrawableFeatureState drawableState = getDrawableState(i, true);
        if (drawableState.alpha != i2) {
            drawableState.alpha = i2;
            updateDrawable(i, drawableState, false);
        }
    }

    protected final void setFeatureDefaultDrawable(int i, android.graphics.drawable.Drawable drawable) {
        com.android.internal.policy.PhoneWindow.DrawableFeatureState drawableState = getDrawableState(i, true);
        if (drawableState.def != drawable) {
            drawableState.def = drawable;
            updateDrawable(i, drawableState, false);
        }
    }

    @Override // android.view.Window
    public final void setFeatureInt(int i, int i2) {
        updateInt(i, i2, false);
    }

    protected final void updateDrawable(int i, boolean z) {
        com.android.internal.policy.PhoneWindow.DrawableFeatureState drawableState = getDrawableState(i, false);
        if (drawableState != null) {
            updateDrawable(i, drawableState, z);
        }
    }

    protected void onDrawableChanged(int i, android.graphics.drawable.Drawable drawable, int i2) {
        android.widget.ImageView rightIconView;
        if (i == 3) {
            rightIconView = getLeftIconView();
        } else if (i == 4) {
            rightIconView = getRightIconView();
        } else {
            return;
        }
        if (drawable != null) {
            drawable.setAlpha(i2);
            rightIconView.setImageDrawable(drawable);
            rightIconView.setVisibility(0);
            return;
        }
        rightIconView.setVisibility(8);
    }

    protected void onIntChanged(int i, int i2) {
        android.widget.FrameLayout frameLayout;
        if (i == 2 || i == 5) {
            updateProgressBars(i2);
        } else if (i == 7 && (frameLayout = (android.widget.FrameLayout) findViewById(com.android.internal.R.id.title_container)) != null) {
            this.mLayoutInflater.inflate(i2, frameLayout);
        }
    }

    private void updateProgressBars(int i) {
        android.widget.ProgressBar circularProgressBar = getCircularProgressBar(true);
        android.widget.ProgressBar horizontalProgressBar = getHorizontalProgressBar(true);
        int localFeatures = getLocalFeatures();
        if (i == -1) {
            if ((localFeatures & 4) != 0) {
                if (horizontalProgressBar != null) {
                    horizontalProgressBar.setVisibility((horizontalProgressBar.isIndeterminate() || horizontalProgressBar.getProgress() < 10000) ? 0 : 4);
                } else {
                    android.util.Log.e(TAG, "Horizontal progress bar not located in current window decor");
                }
            }
            if ((localFeatures & 32) != 0) {
                if (circularProgressBar != null) {
                    circularProgressBar.setVisibility(0);
                    return;
                } else {
                    android.util.Log.e(TAG, "Circular progress bar not located in current window decor");
                    return;
                }
            }
            return;
        }
        if (i == -2) {
            if ((localFeatures & 4) != 0) {
                if (horizontalProgressBar != null) {
                    horizontalProgressBar.setVisibility(8);
                } else {
                    android.util.Log.e(TAG, "Horizontal progress bar not located in current window decor");
                }
            }
            if ((localFeatures & 32) != 0) {
                if (circularProgressBar != null) {
                    circularProgressBar.setVisibility(8);
                    return;
                } else {
                    android.util.Log.e(TAG, "Circular progress bar not located in current window decor");
                    return;
                }
            }
            return;
        }
        if (i == -3) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setIndeterminate(true);
                return;
            } else {
                android.util.Log.e(TAG, "Horizontal progress bar not located in current window decor");
                return;
            }
        }
        if (i == -4) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setIndeterminate(false);
                return;
            } else {
                android.util.Log.e(TAG, "Horizontal progress bar not located in current window decor");
                return;
            }
        }
        if (i >= 0 && i <= 10000) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setProgress(i + 0);
            } else {
                android.util.Log.e(TAG, "Horizontal progress bar not located in current window decor");
            }
            if (i < 10000) {
                showProgressBars(horizontalProgressBar, circularProgressBar);
                return;
            } else {
                hideProgressBars(horizontalProgressBar, circularProgressBar);
                return;
            }
        }
        if (20000 <= i && i <= 30000) {
            if (horizontalProgressBar != null) {
                horizontalProgressBar.setSecondaryProgress(i - 20000);
            } else {
                android.util.Log.e(TAG, "Horizontal progress bar not located in current window decor");
            }
            showProgressBars(horizontalProgressBar, circularProgressBar);
        }
    }

    private void showProgressBars(android.widget.ProgressBar progressBar, android.widget.ProgressBar progressBar2) {
        int localFeatures = getLocalFeatures();
        if ((localFeatures & 32) != 0 && progressBar2 != null && progressBar2.getVisibility() == 4) {
            progressBar2.setVisibility(0);
        }
        if ((localFeatures & 4) != 0 && progressBar != null && progressBar.getProgress() < 10000) {
            progressBar.setVisibility(0);
        }
    }

    private void hideProgressBars(android.widget.ProgressBar progressBar, android.widget.ProgressBar progressBar2) {
        int localFeatures = getLocalFeatures();
        android.view.animation.Animation loadAnimation = android.view.animation.AnimationUtils.loadAnimation(getContext(), 17432577);
        loadAnimation.setDuration(1000L);
        if ((localFeatures & 32) != 0 && progressBar2 != null && progressBar2.getVisibility() == 0) {
            progressBar2.startAnimation(loadAnimation);
            progressBar2.setVisibility(4);
        }
        if ((localFeatures & 4) != 0 && progressBar != null && progressBar.getVisibility() == 0) {
            progressBar.startAnimation(loadAnimation);
            progressBar.setVisibility(4);
        }
    }

    @Override // android.view.Window
    public void setIcon(int i) {
        this.mIconRes = i;
        this.mResourcesSetFlags |= 1;
        this.mResourcesSetFlags &= -5;
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setIcon(i);
        }
    }

    @Override // android.view.Window
    public void setDefaultIcon(int i) {
        if ((this.mResourcesSetFlags & 1) != 0) {
            return;
        }
        this.mIconRes = i;
        if (this.mDecorContentParent != null) {
            if (!this.mDecorContentParent.hasIcon() || (this.mResourcesSetFlags & 4) != 0) {
                if (i != 0) {
                    this.mDecorContentParent.setIcon(i);
                    this.mResourcesSetFlags &= -5;
                } else {
                    this.mDecorContentParent.setIcon(getContext().getPackageManager().getDefaultActivityIcon());
                    this.mResourcesSetFlags |= 4;
                }
            }
        }
    }

    @Override // android.view.Window
    public void setLogo(int i) {
        this.mLogoRes = i;
        this.mResourcesSetFlags |= 2;
        if (this.mDecorContentParent != null) {
            this.mDecorContentParent.setLogo(i);
        }
    }

    @Override // android.view.Window
    public void setDefaultLogo(int i) {
        if ((this.mResourcesSetFlags & 2) != 0) {
            return;
        }
        this.mLogoRes = i;
        if (this.mDecorContentParent != null && !this.mDecorContentParent.hasLogo()) {
            this.mDecorContentParent.setLogo(i);
        }
    }

    @Override // android.view.Window
    public void setLocalFocus(boolean z, boolean z2) {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        viewRootImpl.windowFocusChanged(z);
        viewRootImpl.touchModeChanged(z2);
    }

    @Override // android.view.Window
    public void injectInputEvent(android.view.InputEvent inputEvent) {
        getViewRootImpl().dispatchInputEvent(inputEvent);
    }

    private android.view.ViewRootImpl getViewRootImpl() {
        android.view.ViewRootImpl viewRootImplOrNull = getViewRootImplOrNull();
        if (viewRootImplOrNull != null) {
            return viewRootImplOrNull;
        }
        throw new java.lang.IllegalStateException("view not added");
    }

    private android.view.ViewRootImpl getViewRootImplOrNull() {
        if (this.mDecor == null) {
            return null;
        }
        return this.mDecor.getViewRootImpl();
    }

    @Override // android.view.Window
    public void takeKeyEvents(boolean z) {
        this.mDecor.setFocusable(z);
    }

    @Override // android.view.Window
    public boolean superDispatchKeyEvent(android.view.KeyEvent keyEvent) {
        return this.mDecor.superDispatchKeyEvent(keyEvent);
    }

    @Override // android.view.Window
    public boolean superDispatchKeyShortcutEvent(android.view.KeyEvent keyEvent) {
        return this.mDecor.superDispatchKeyShortcutEvent(keyEvent);
    }

    @Override // android.view.Window
    public boolean superDispatchTouchEvent(android.view.MotionEvent motionEvent) {
        return this.mDecor.superDispatchTouchEvent(motionEvent);
    }

    @Override // android.view.Window
    public boolean superDispatchTrackballEvent(android.view.MotionEvent motionEvent) {
        return this.mDecor.superDispatchTrackballEvent(motionEvent);
    }

    @Override // android.view.Window
    public boolean superDispatchGenericMotionEvent(android.view.MotionEvent motionEvent) {
        return this.mDecor.superDispatchGenericMotionEvent(motionEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected boolean onKeyDown(int i, int i2, android.view.KeyEvent keyEvent) {
        android.view.KeyEvent.DispatcherState keyDispatcherState = this.mDecor != null ? this.mDecor.getKeyDispatcherState() : null;
        switch (i2) {
            case 4:
                if (keyEvent.getRepeatCount() <= 0 && i >= 0) {
                    if (keyDispatcherState != null) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    }
                    return true;
                }
                return false;
            case 24:
            case 25:
            case 164:
                if (this.mMediaController != null && !isActivePhoneCallOngoing()) {
                    getMediaSessionManager().dispatchVolumeKeyEventToSessionAsSystemService(keyEvent, this.mMediaController.getSessionToken());
                } else {
                    getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(keyEvent, this.mVolumeControlStreamType);
                }
                return true;
            case 79:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 126:
            case 127:
            case 130:
                return this.mMediaController != null && getMediaSessionManager().dispatchMediaKeyEventToSessionAsSystemService(keyEvent, this.mMediaController.getSessionToken());
            case 82:
                if (i < 0) {
                    i = 0;
                }
                onKeyDownPanel(i, keyEvent);
                return true;
            default:
                return false;
        }
    }

    private boolean isActivePhoneCallOngoing() {
        return this.mAudioMode == 2 || this.mAudioMode == 3;
    }

    private android.app.KeyguardManager getKeyguardManager() {
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager = (android.app.KeyguardManager) getContext().getSystemService(android.content.Context.KEYGUARD_SERVICE);
        }
        return this.mKeyguardManager;
    }

    android.media.AudioManager getAudioManager() {
        if (this.mAudioManager == null) {
            this.mAudioManager = (android.media.AudioManager) getContext().getSystemService("audio");
        }
        return this.mAudioManager;
    }

    private android.media.session.MediaSessionManager getMediaSessionManager() {
        if (this.mMediaSessionManager == null) {
            this.mMediaSessionManager = (android.media.session.MediaSessionManager) getContext().getSystemService(android.content.Context.MEDIA_SESSION_SERVICE);
        }
        return this.mMediaSessionManager;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    protected boolean onKeyUp(int i, int i2, android.view.KeyEvent keyEvent) {
        com.android.internal.policy.PhoneWindow.PanelFeatureState panelState;
        android.view.KeyEvent.DispatcherState keyDispatcherState = this.mDecor != null ? this.mDecor.getKeyDispatcherState() : null;
        if (keyDispatcherState != null) {
            keyDispatcherState.handleUpEvent(keyEvent);
        }
        switch (i2) {
            case 4:
                if (i >= 0 && keyEvent.isTracking() && !keyEvent.isCanceled()) {
                    if (i == 0 && (panelState = getPanelState(i, false)) != null && panelState.isInExpandedMode) {
                        reopenMenu(true);
                        return true;
                    }
                    closePanel(i);
                    return true;
                }
                return false;
            case 24:
            case 25:
                if (this.mMediaController != null) {
                    getMediaSessionManager().dispatchVolumeKeyEventToSessionAsSystemService(keyEvent, this.mMediaController.getSessionToken());
                } else {
                    getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(keyEvent, this.mVolumeControlStreamType);
                }
                return true;
            case 79:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 126:
            case 127:
            case 130:
                return this.mMediaController != null && getMediaSessionManager().dispatchMediaKeyEventToSessionAsSystemService(keyEvent, this.mMediaController.getSessionToken());
            case 82:
                if (i < 0) {
                    i = 0;
                }
                onKeyUpPanel(i, keyEvent);
                return true;
            case 84:
                if (!isNotInstantAppAndKeyguardRestricted() && (getContext().getResources().getConfiguration().uiMode & 15) != 6) {
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        launchDefaultSearch(keyEvent);
                    }
                    return true;
                }
                return false;
            case 164:
                getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(keyEvent, Integer.MIN_VALUE);
                return true;
            case 171:
                if (this.mSupportsPictureInPicture && !keyEvent.isCanceled()) {
                    getWindowControllerCallback().enterPictureInPictureModeIfPossible();
                }
                return true;
            default:
                return false;
        }
    }

    private boolean isNotInstantAppAndKeyguardRestricted() {
        return !getContext().getPackageManager().isInstantApp() && getKeyguardManager().inKeyguardRestrictedInputMode();
    }

    @Override // android.view.Window
    protected void onActive() {
    }

    @Override // android.view.Window
    public final android.view.View getDecorView() {
        if (this.mDecor == null || this.mForceDecorInstall) {
            installDecor();
        }
        return this.mDecor;
    }

    @Override // android.view.Window
    public final android.view.View peekDecorView() {
        return this.mDecor;
    }

    void onViewRootImplSet(android.view.ViewRootImpl viewRootImpl) {
        viewRootImpl.setActivityConfigCallback(this.mActivityConfigCallback);
        viewRootImpl.getOnBackInvokedDispatcher().updateContext(getContext());
        viewRootImpl.setFrameRatePowerSavingsBalanced(this.mIsFrameRatePowerSavingsBalanced);
        this.mProxyOnBackInvokedDispatcher.setActualDispatcher(viewRootImpl.getOnBackInvokedDispatcher());
        applyDecorFitsSystemWindows();
    }

    @Override // android.view.Window
    public android.os.Bundle saveHierarchyState() {
        android.os.Bundle bundle = new android.os.Bundle();
        if (this.mContentParent == null) {
            return bundle;
        }
        android.util.SparseArray<android.os.Parcelable> sparseArray = new android.util.SparseArray<>();
        this.mContentParent.saveHierarchyState(sparseArray);
        bundle.putSparseParcelableArray(VIEWS_TAG, sparseArray);
        android.view.View findFocus = this.mContentParent.findFocus();
        if (findFocus != null && findFocus.getId() != -1) {
            bundle.putInt(FOCUSED_ID_TAG, findFocus.getId());
        }
        android.util.SparseArray<android.os.Parcelable> sparseArray2 = new android.util.SparseArray<>();
        savePanelState(sparseArray2);
        if (sparseArray2.size() > 0) {
            bundle.putSparseParcelableArray(PANELS_TAG, sparseArray2);
        }
        if (this.mDecorContentParent != null) {
            android.util.SparseArray<android.os.Parcelable> sparseArray3 = new android.util.SparseArray<>();
            this.mDecorContentParent.saveToolbarHierarchyState(sparseArray3);
            bundle.putSparseParcelableArray(ACTION_BAR_TAG, sparseArray3);
        }
        return bundle;
    }

    @Override // android.view.Window
    public void restoreHierarchyState(android.os.Bundle bundle) {
        if (this.mContentParent == null) {
            return;
        }
        android.util.SparseArray<android.os.Parcelable> sparseParcelableArray = bundle.getSparseParcelableArray(VIEWS_TAG);
        if (sparseParcelableArray != null) {
            this.mContentParent.restoreHierarchyState(sparseParcelableArray);
        }
        int i = bundle.getInt(FOCUSED_ID_TAG, -1);
        if (i != -1) {
            android.view.View findViewById = this.mContentParent.findViewById(i);
            if (findViewById == null) {
                android.util.Log.w(TAG, "Previously focused view reported id " + i + " during save, but can't be found during restore.");
            } else {
                findViewById.requestFocus();
            }
        }
        android.util.SparseArray<android.os.Parcelable> sparseParcelableArray2 = bundle.getSparseParcelableArray(PANELS_TAG);
        if (sparseParcelableArray2 != null) {
            restorePanelState(sparseParcelableArray2);
        }
        if (this.mDecorContentParent != null) {
            android.util.SparseArray<android.os.Parcelable> sparseParcelableArray3 = bundle.getSparseParcelableArray(ACTION_BAR_TAG);
            if (sparseParcelableArray3 == null) {
                android.util.Log.w(TAG, "Missing saved instance states for action bar views! State will not be restored.");
            } else {
                doPendingInvalidatePanelMenu();
                this.mDecorContentParent.restoreToolbarHierarchyState(sparseParcelableArray3);
            }
        }
    }

    private void savePanelState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        com.android.internal.policy.PhoneWindow.PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        if (panelFeatureStateArr == null) {
            return;
        }
        for (int length = panelFeatureStateArr.length - 1; length >= 0; length--) {
            if (panelFeatureStateArr[length] != null) {
                sparseArray.put(length, panelFeatureStateArr[length].onSaveInstanceState());
            }
        }
    }

    private void restorePanelState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            int keyAt = sparseArray.keyAt(size);
            com.android.internal.policy.PhoneWindow.PanelFeatureState panelState = getPanelState(keyAt, false);
            if (panelState != null) {
                panelState.onRestoreInstanceState(sparseArray.get(keyAt));
                invalidatePanelMenu(keyAt);
            }
        }
    }

    void openPanelsAfterRestore() {
        com.android.internal.policy.PhoneWindow.PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        if (panelFeatureStateArr == null) {
            return;
        }
        for (int length = panelFeatureStateArr.length - 1; length >= 0; length--) {
            com.android.internal.policy.PhoneWindow.PanelFeatureState panelFeatureState = panelFeatureStateArr[length];
            if (panelFeatureState != null) {
                panelFeatureState.applyFrozenState();
                if (!panelFeatureState.isOpen && panelFeatureState.wasLastOpen) {
                    panelFeatureState.isInExpandedMode = panelFeatureState.wasLastExpanded;
                    openPanel(panelFeatureState, (android.view.KeyEvent) null);
                }
            }
        }
    }

    @Override // android.view.Window
    protected void onDestroy() {
        if (this.mOnModeChangedListener != null) {
            getAudioManager().removeOnModeChangedListener(this.mOnModeChangedListener);
            this.mOnModeChangedListener = null;
        }
    }

    private class PanelMenuPresenterCallback implements com.android.internal.view.menu.MenuPresenter.Callback {
        private PanelMenuPresenterCallback() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
            com.android.internal.view.menu.MenuBuilder rootMenu = menuBuilder.getRootMenu();
            boolean z2 = rootMenu != menuBuilder;
            com.android.internal.policy.PhoneWindow phoneWindow = com.android.internal.policy.PhoneWindow.this;
            if (z2) {
                menuBuilder = rootMenu;
            }
            com.android.internal.policy.PhoneWindow.PanelFeatureState findMenuPanel = phoneWindow.findMenuPanel(menuBuilder);
            if (findMenuPanel != null) {
                if (z2) {
                    com.android.internal.policy.PhoneWindow.this.callOnPanelClosed(findMenuPanel.featureId, findMenuPanel, rootMenu);
                    com.android.internal.policy.PhoneWindow.this.closePanel(findMenuPanel, true);
                } else {
                    com.android.internal.policy.PhoneWindow.this.closePanel(findMenuPanel, z);
                }
            }
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(com.android.internal.view.menu.MenuBuilder menuBuilder) {
            android.view.Window.Callback callback;
            if (menuBuilder == null && com.android.internal.policy.PhoneWindow.this.hasFeature(8) && (callback = com.android.internal.policy.PhoneWindow.this.getCallback()) != null && !com.android.internal.policy.PhoneWindow.this.isDestroyed()) {
                callback.onMenuOpened(8, menuBuilder);
                return true;
            }
            return true;
        }
    }

    private final class ActionMenuPresenterCallback implements com.android.internal.view.menu.MenuPresenter.Callback {
        private ActionMenuPresenterCallback() {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(com.android.internal.view.menu.MenuBuilder menuBuilder) {
            android.view.Window.Callback callback = com.android.internal.policy.PhoneWindow.this.getCallback();
            if (callback != null) {
                callback.onMenuOpened(8, menuBuilder);
                return true;
            }
            return false;
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
            com.android.internal.policy.PhoneWindow.this.checkCloseActionMenu(menuBuilder);
        }
    }

    protected com.android.internal.policy.DecorView generateDecor(int i) {
        android.content.Context context;
        if (this.mUseDecorContext) {
            android.content.Context applicationContext = getContext().getApplicationContext();
            if (applicationContext == null) {
                context = getContext();
            } else {
                com.android.internal.policy.DecorContext decorContext = new com.android.internal.policy.DecorContext(applicationContext, this);
                if (this.mTheme != -1) {
                    decorContext.setTheme(this.mTheme);
                }
                context = decorContext;
            }
        } else {
            context = getContext();
        }
        return new com.android.internal.policy.DecorView(context, i, this, getAttributes());
    }

    protected android.view.ViewGroup generateLayout(com.android.internal.policy.DecorView decorView) {
        int i;
        android.graphics.drawable.Drawable drawable;
        android.widget.ProgressBar circularProgressBar;
        android.content.res.TypedArray windowStyle = getWindowStyle();
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        this.mEdgeToEdgeEnforced = isEdgeToEdgeEnforced(getContext().getApplicationInfo(), true, windowStyle);
        if (this.mEdgeToEdgeEnforced) {
            getAttributes().privateFlags |= 2048;
            this.mDecorFitsSystemWindows = false;
        }
        this.mIsFloating = windowStyle.getBoolean(4, false);
        int i2 = (~getForcedWindowFlags()) & 65792;
        if (this.mIsFloating && !this.mAllowFloatingWindowsFillScreen.booleanValue()) {
            setLayout(-2, -2);
            setFlags(0, i2);
        } else {
            setFlags(65792, i2);
            attributes.setFitInsetsSides(0);
            attributes.setFitInsetsTypes(0);
            if (this.mEdgeToEdgeEnforced) {
                attributes.layoutInDisplayCutoutMode = 3;
            }
        }
        if (windowStyle.getBoolean(3, false)) {
            requestFeature(1);
        } else if (windowStyle.getBoolean(15, false)) {
            requestFeature(8);
        }
        if (windowStyle.getBoolean(17, false)) {
            requestFeature(9);
        }
        if (windowStyle.getBoolean(16, false)) {
            requestFeature(10);
        }
        if (windowStyle.getBoolean(9, false)) {
            setFlags(1024, (~getForcedWindowFlags()) & 1024);
        }
        if (windowStyle.getBoolean(23, false)) {
            setFlags(67108864, (~getForcedWindowFlags()) & 67108864);
        }
        if (windowStyle.getBoolean(24, false)) {
            setFlags(134217728, (~getForcedWindowFlags()) & 134217728);
        }
        if (windowStyle.getBoolean(14, false)) {
            setFlags(1048576, (~getForcedWindowFlags()) & 1048576);
        }
        if (windowStyle.getBoolean(18, getContext().getApplicationInfo().targetSdkVersion >= 11)) {
            setFlags(8388608, (~getForcedWindowFlags()) & 8388608);
        }
        windowStyle.getValue(19, this.mMinWidthMajor);
        windowStyle.getValue(20, this.mMinWidthMinor);
        if (windowStyle.hasValue(66)) {
            if (this.mFixedWidthMajor == null) {
                this.mFixedWidthMajor = new android.util.TypedValue();
            }
            windowStyle.getValue(66, this.mFixedWidthMajor);
        }
        if (windowStyle.hasValue(67)) {
            if (this.mFixedWidthMinor == null) {
                this.mFixedWidthMinor = new android.util.TypedValue();
            }
            windowStyle.getValue(67, this.mFixedWidthMinor);
        }
        if (windowStyle.hasValue(64)) {
            if (this.mFixedHeightMajor == null) {
                this.mFixedHeightMajor = new android.util.TypedValue();
            }
            windowStyle.getValue(64, this.mFixedHeightMajor);
        }
        if (windowStyle.hasValue(65)) {
            if (this.mFixedHeightMinor == null) {
                this.mFixedHeightMinor = new android.util.TypedValue();
            }
            windowStyle.getValue(65, this.mFixedHeightMinor);
        }
        if (windowStyle.getBoolean(25, false)) {
            requestFeature(12);
        }
        if (windowStyle.getBoolean(44, false)) {
            requestFeature(13);
        }
        if (windowStyle.hasValue(70)) {
            this.mIsFrameRatePowerSavingsBalanced = windowStyle.getBoolean(70, true);
        }
        this.mIsTranslucent = windowStyle.getBoolean(5, false);
        android.content.Context context = getContext();
        int i3 = context.getApplicationInfo().targetSdkVersion;
        boolean z = i3 < 21;
        boolean z2 = i3 < 29;
        if (!this.mForcedStatusBarColor && !this.mEdgeToEdgeEnforced) {
            this.mStatusBarColor = windowStyle.getColor(34, -16777216);
        }
        if (!this.mForcedNavigationBarColor && !this.mEdgeToEdgeEnforced) {
            int color = context.getColor(com.android.internal.R.color.navigation_bar_compatible);
            int color2 = context.getColor(com.android.internal.R.color.navigation_bar_default);
            int color3 = windowStyle.getColor(35, color2);
            if (color3 != color2 || context.getResources().getBoolean(com.android.internal.R.bool.config_navBarDefaultTransparent)) {
                color = color3;
            }
            this.mNavigationBarColor = color;
            this.mNavigationBarDividerColor = windowStyle.getColor(49, 0);
        }
        if (!z2) {
            this.mEnsureStatusBarContrastWhenTransparent = windowStyle.getBoolean(51, false);
            this.mEnsureNavigationBarContrastWhenTransparent = windowStyle.getBoolean(52, true);
        }
        if (!this.mIsFloating) {
            if (!z && windowStyle.getBoolean(33, false)) {
                setFlags(Integer.MIN_VALUE, (~getForcedWindowFlags()) & Integer.MIN_VALUE);
            }
            if (this.mDecor.mForceWindowDrawsBarBackgrounds) {
                attributes.privateFlags |= 32768;
            }
            attributes.privateFlags |= 64;
        }
        if (windowStyle.getBoolean(62, false)) {
            attributes.privateFlags |= 64;
        }
        decorView.setSystemUiVisibility((decorView.getSystemUiVisibility() & (-8209)) | (windowStyle.getBoolean(45, false) ? 8192 : 0) | (windowStyle.getBoolean(48, false) ? 16 : 0));
        if (windowStyle.hasValue(50)) {
            int i4 = windowStyle.getInt(50, -1);
            if (i4 < 0 || i4 > 3) {
                throw new java.lang.UnsupportedOperationException("Unknown windowLayoutInDisplayCutoutMode: " + windowStyle.getString(50));
            }
            attributes.layoutInDisplayCutoutMode = i4;
        }
        if ((this.mAlwaysReadCloseOnTouchAttr || getContext().getApplicationInfo().targetSdkVersion >= 11) && windowStyle.getBoolean(21, false)) {
            setCloseOnTouchOutsideIfNotSet(true);
        }
        if (!hasSoftInputMode()) {
            attributes.softInputMode = windowStyle.getInt(13, attributes.softInputMode);
        }
        if (windowStyle.getBoolean(11, this.mIsFloating)) {
            if ((getForcedWindowFlags() & 2) == 0) {
                attributes.flags |= 2;
            }
            if (!haveDimAmount()) {
                attributes.dimAmount = windowStyle.getFloat(0, 0.5f);
            }
        }
        if (windowStyle.getBoolean(54, false)) {
            if ((getForcedWindowFlags() & 4) == 0) {
                attributes.flags = 4 | attributes.flags;
            }
            attributes.setBlurBehindRadius(windowStyle.getDimensionPixelSize(53, 0));
        }
        setBackgroundBlurRadius(windowStyle.getDimensionPixelSize(55, 0));
        if (attributes.windowAnimations == 0) {
            attributes.windowAnimations = windowStyle.getResourceId(8, 0);
        }
        if (getContainer() == null) {
            if (this.mBackgroundDrawable == null) {
                if (this.mFrameResource == 0) {
                    this.mFrameResource = windowStyle.getResourceId(2, 0);
                }
                if (windowStyle.hasValue(1)) {
                    this.mBackgroundDrawable = windowStyle.getDrawable(1);
                }
            }
            if (windowStyle.hasValue(46)) {
                this.mBackgroundFallbackDrawable = windowStyle.getDrawable(46);
            }
            if (this.mLoadElevation) {
                this.mElevation = windowStyle.getDimension(37, 0.0f);
            }
            this.mClipToOutline = windowStyle.getBoolean(38, false);
            this.mTextColor = windowStyle.getColor(7, 0);
        }
        int localFeatures = getLocalFeatures();
        if ((localFeatures & 24) != 0) {
            if (this.mIsFloating) {
                android.util.TypedValue typedValue = new android.util.TypedValue();
                getContext().getTheme().resolveAttribute(com.android.internal.R.attr.dialogTitleIconsDecorLayout, typedValue, true);
                i = typedValue.resourceId;
            } else {
                i = com.android.internal.R.layout.screen_title_icons;
            }
            removeFeature(8);
        } else if ((localFeatures & 36) != 0 && (localFeatures & 256) == 0) {
            i = com.android.internal.R.layout.screen_progress;
        } else if ((localFeatures & 128) != 0) {
            if (this.mIsFloating) {
                android.util.TypedValue typedValue2 = new android.util.TypedValue();
                getContext().getTheme().resolveAttribute(com.android.internal.R.attr.dialogCustomTitleDecorLayout, typedValue2, true);
                i = typedValue2.resourceId;
            } else {
                i = com.android.internal.R.layout.screen_custom_title;
            }
            removeFeature(8);
        } else if ((localFeatures & 2) == 0) {
            if (this.mIsFloating) {
                android.util.TypedValue typedValue3 = new android.util.TypedValue();
                getContext().getTheme().resolveAttribute(com.android.internal.R.attr.dialogTitleDecorLayout, typedValue3, true);
                i = typedValue3.resourceId;
            } else if ((localFeatures & 256) != 0) {
                i = windowStyle.getResourceId(63, com.android.internal.R.layout.screen_action_bar);
            } else {
                i = com.android.internal.R.layout.screen_title;
            }
        } else if ((localFeatures & 1024) != 0) {
            i = com.android.internal.R.layout.screen_simple_overlay_action_mode;
        } else {
            i = com.android.internal.R.layout.screen_simple;
        }
        this.mDecor.startChanging();
        this.mDecor.onResourcesLoaded(this.mLayoutInflater, i);
        android.view.ViewGroup viewGroup = (android.view.ViewGroup) findViewById(16908290);
        if (viewGroup == null) {
            throw new java.lang.RuntimeException("Window couldn't find content container view");
        }
        if ((localFeatures & 32) != 0 && (circularProgressBar = getCircularProgressBar(false)) != null) {
            circularProgressBar.setIndeterminate(true);
        }
        if (getContainer() == null) {
            this.mDecor.setWindowBackground(this.mBackgroundDrawable);
            if (this.mFrameResource != 0) {
                drawable = getContext().getDrawable(this.mFrameResource);
            } else {
                drawable = null;
            }
            this.mDecor.setWindowFrame(drawable);
            this.mDecor.setElevation(this.mElevation);
            this.mDecor.setClipToOutline(this.mClipToOutline);
            if (this.mTitle != null) {
                setTitle(this.mTitle);
            }
            if (this.mTitleColor == 0) {
                this.mTitleColor = this.mTextColor;
            }
            setTitleColor(this.mTitleColor);
        }
        this.mDecor.finishChanging();
        return viewGroup;
    }

    @Override // android.view.Window
    public void alwaysReadCloseOnTouchAttr() {
        this.mAlwaysReadCloseOnTouchAttr = true;
    }

    private void installDecor() {
        this.mForceDecorInstall = false;
        if (this.mDecor == null) {
            this.mDecor = generateDecor(-1);
            this.mDecor.setDescendantFocusability(262144);
            this.mDecor.setIsRootNamespace(true);
            if (!this.mInvalidatePanelMenuPosted && this.mInvalidatePanelMenuFeatures != 0) {
                this.mDecor.postOnAnimation(this.mInvalidatePanelMenuRunnable);
            }
        } else {
            this.mDecor.setWindow(this);
        }
        if (this.mContentParent == null) {
            this.mContentParent = generateLayout(this.mDecor);
            this.mDecor.makeFrameworkOptionalFitsSystemWindows();
            com.android.internal.widget.DecorContentParent decorContentParent = (com.android.internal.widget.DecorContentParent) this.mDecor.findViewById(com.android.internal.R.id.decor_content_parent);
            if (decorContentParent != null) {
                this.mDecorContentParent = decorContentParent;
                this.mDecorContentParent.setWindowCallback(getCallback());
                if (this.mDecorContentParent.getTitle() == null) {
                    this.mDecorContentParent.setWindowTitle(this.mTitle);
                }
                int localFeatures = getLocalFeatures();
                for (int i = 0; i < 13; i++) {
                    if (((1 << i) & localFeatures) != 0) {
                        this.mDecorContentParent.initFeature(i);
                    }
                }
                this.mDecorContentParent.setUiOptions(this.mUiOptions);
                if ((this.mResourcesSetFlags & 1) != 0 || (this.mIconRes != 0 && !this.mDecorContentParent.hasIcon())) {
                    this.mDecorContentParent.setIcon(this.mIconRes);
                } else if ((this.mResourcesSetFlags & 1) == 0 && this.mIconRes == 0 && !this.mDecorContentParent.hasIcon()) {
                    this.mDecorContentParent.setIcon(getContext().getPackageManager().getDefaultActivityIcon());
                    this.mResourcesSetFlags |= 4;
                }
                if ((this.mResourcesSetFlags & 2) != 0 || (this.mLogoRes != 0 && !this.mDecorContentParent.hasLogo())) {
                    this.mDecorContentParent.setLogo(this.mLogoRes);
                }
                com.android.internal.policy.PhoneWindow.PanelFeatureState panelState = getPanelState(0, false);
                if (!isDestroyed() && ((panelState == null || panelState.menu == null) && !this.mIsStartingWindow)) {
                    invalidatePanelMenu(8);
                }
            } else {
                this.mTitleView = (android.widget.TextView) findViewById(16908310);
                if (this.mTitleView != null) {
                    if ((getLocalFeatures() & 2) != 0) {
                        android.view.View findViewById = findViewById(com.android.internal.R.id.title_container);
                        if (findViewById != null) {
                            findViewById.setVisibility(8);
                        } else {
                            this.mTitleView.setVisibility(8);
                        }
                        this.mContentParent.setForeground(null);
                    } else {
                        this.mTitleView.lambda$setTextAsync$0(this.mTitle);
                    }
                }
            }
            if (this.mDecor.getBackground() == null && this.mBackgroundFallbackDrawable != null) {
                this.mDecor.setBackgroundFallback(this.mBackgroundFallbackDrawable);
            }
            if (hasFeature(13)) {
                if (this.mTransitionManager == null) {
                    int resourceId = getWindowStyle().getResourceId(26, 0);
                    if (resourceId != 0) {
                        this.mTransitionManager = android.transition.TransitionInflater.from(getContext()).inflateTransitionManager(resourceId, this.mContentParent);
                    } else {
                        this.mTransitionManager = new android.transition.TransitionManager();
                    }
                }
                this.mEnterTransition = getTransition(this.mEnterTransition, null, 27);
                this.mReturnTransition = getTransition(this.mReturnTransition, USE_DEFAULT_TRANSITION, 39);
                this.mExitTransition = getTransition(this.mExitTransition, null, 28);
                this.mReenterTransition = getTransition(this.mReenterTransition, USE_DEFAULT_TRANSITION, 40);
                this.mSharedElementEnterTransition = getTransition(this.mSharedElementEnterTransition, null, 29);
                this.mSharedElementReturnTransition = getTransition(this.mSharedElementReturnTransition, USE_DEFAULT_TRANSITION, 41);
                this.mSharedElementExitTransition = getTransition(this.mSharedElementExitTransition, null, 30);
                this.mSharedElementReenterTransition = getTransition(this.mSharedElementReenterTransition, USE_DEFAULT_TRANSITION, 42);
                if (this.mAllowEnterTransitionOverlap == null) {
                    this.mAllowEnterTransitionOverlap = java.lang.Boolean.valueOf(getWindowStyle().getBoolean(32, true));
                }
                if (this.mAllowReturnTransitionOverlap == null) {
                    this.mAllowReturnTransitionOverlap = java.lang.Boolean.valueOf(getWindowStyle().getBoolean(31, true));
                }
                if (this.mBackgroundFadeDurationMillis < 0) {
                    this.mBackgroundFadeDurationMillis = getWindowStyle().getInteger(36, 300);
                }
                if (this.mSharedElementsUseOverlay == null) {
                    this.mSharedElementsUseOverlay = java.lang.Boolean.valueOf(getWindowStyle().getBoolean(43, true));
                }
            }
        }
    }

    private android.transition.Transition getTransition(android.transition.Transition transition, android.transition.Transition transition2, int i) {
        if (transition != transition2) {
            return transition;
        }
        int resourceId = getWindowStyle().getResourceId(i, -1);
        if (resourceId != -1 && resourceId != 17760256) {
            android.transition.Transition inflateTransition = android.transition.TransitionInflater.from(getContext()).inflateTransition(resourceId);
            if ((inflateTransition instanceof android.transition.TransitionSet) && ((android.transition.TransitionSet) inflateTransition).getTransitionCount() == 0) {
                return null;
            }
            return inflateTransition;
        }
        return transition2;
    }

    private android.graphics.drawable.Drawable loadImageURI(android.net.Uri uri) {
        try {
            return android.graphics.drawable.Drawable.createFromStream(getContext().getContentResolver().openInputStream(uri), null);
        } catch (java.lang.Exception e) {
            android.util.Log.w(TAG, "Unable to open content: " + uri);
            return null;
        }
    }

    private com.android.internal.policy.PhoneWindow.DrawableFeatureState getDrawableState(int i, boolean z) {
        if ((getFeatures() & (1 << i)) == 0) {
            if (!z) {
                return null;
            }
            throw new java.lang.RuntimeException("The feature has not been requested");
        }
        com.android.internal.policy.PhoneWindow.DrawableFeatureState[] drawableFeatureStateArr = this.mDrawables;
        if (drawableFeatureStateArr == null || drawableFeatureStateArr.length <= i) {
            com.android.internal.policy.PhoneWindow.DrawableFeatureState[] drawableFeatureStateArr2 = new com.android.internal.policy.PhoneWindow.DrawableFeatureState[i + 1];
            if (drawableFeatureStateArr != null) {
                java.lang.System.arraycopy(drawableFeatureStateArr, 0, drawableFeatureStateArr2, 0, drawableFeatureStateArr.length);
            }
            this.mDrawables = drawableFeatureStateArr2;
            drawableFeatureStateArr = drawableFeatureStateArr2;
        }
        com.android.internal.policy.PhoneWindow.DrawableFeatureState drawableFeatureState = drawableFeatureStateArr[i];
        if (drawableFeatureState == null) {
            com.android.internal.policy.PhoneWindow.DrawableFeatureState drawableFeatureState2 = new com.android.internal.policy.PhoneWindow.DrawableFeatureState(i);
            drawableFeatureStateArr[i] = drawableFeatureState2;
            return drawableFeatureState2;
        }
        return drawableFeatureState;
    }

    com.android.internal.policy.PhoneWindow.PanelFeatureState getPanelState(int i, boolean z) {
        return getPanelState(i, z, null);
    }

    private com.android.internal.policy.PhoneWindow.PanelFeatureState getPanelState(int i, boolean z, com.android.internal.policy.PhoneWindow.PanelFeatureState panelFeatureState) {
        if ((getFeatures() & (1 << i)) == 0) {
            if (!z) {
                return null;
            }
            throw new java.lang.RuntimeException("The feature has not been requested");
        }
        com.android.internal.policy.PhoneWindow.PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= i) {
            com.android.internal.policy.PhoneWindow.PanelFeatureState[] panelFeatureStateArr2 = new com.android.internal.policy.PhoneWindow.PanelFeatureState[i + 1];
            if (panelFeatureStateArr != null) {
                java.lang.System.arraycopy(panelFeatureStateArr, 0, panelFeatureStateArr2, 0, panelFeatureStateArr.length);
            }
            this.mPanels = panelFeatureStateArr2;
            panelFeatureStateArr = panelFeatureStateArr2;
        }
        com.android.internal.policy.PhoneWindow.PanelFeatureState panelFeatureState2 = panelFeatureStateArr[i];
        if (panelFeatureState2 == null) {
            if (panelFeatureState == null) {
                panelFeatureState = new com.android.internal.policy.PhoneWindow.PanelFeatureState(i);
            }
            panelFeatureStateArr[i] = panelFeatureState;
            return panelFeatureState;
        }
        return panelFeatureState2;
    }

    @Override // android.view.Window
    public final void setChildDrawable(int i, android.graphics.drawable.Drawable drawable) {
        com.android.internal.policy.PhoneWindow.DrawableFeatureState drawableState = getDrawableState(i, true);
        drawableState.child = drawable;
        updateDrawable(i, drawableState, false);
    }

    @Override // android.view.Window
    public final void setChildInt(int i, int i2) {
        updateInt(i, i2, false);
    }

    @Override // android.view.Window
    public boolean isShortcutKey(int i, android.view.KeyEvent keyEvent) {
        com.android.internal.policy.PhoneWindow.PanelFeatureState panelState = getPanelState(0, false);
        return (panelState == null || panelState.menu == null || !panelState.menu.isShortcutKey(i, keyEvent)) ? false : true;
    }

    private void updateDrawable(int i, com.android.internal.policy.PhoneWindow.DrawableFeatureState drawableFeatureState, boolean z) {
        android.graphics.drawable.Drawable drawable;
        if (this.mContentParent == null) {
            return;
        }
        int i2 = 1 << i;
        if ((getFeatures() & i2) == 0 && !z) {
            return;
        }
        if (drawableFeatureState == null) {
            drawable = null;
        } else {
            drawable = drawableFeatureState.child;
            if (drawable == null) {
                drawable = drawableFeatureState.local;
            }
            if (drawable == null) {
                drawable = drawableFeatureState.def;
            }
        }
        if ((i2 & getLocalFeatures()) == 0) {
            if (getContainer() != null) {
                if (isActive() || z) {
                    getContainer().setChildDrawable(i, drawable);
                    return;
                }
                return;
            }
            return;
        }
        if (drawableFeatureState != null) {
            if (drawableFeatureState.cur != drawable || drawableFeatureState.curAlpha != drawableFeatureState.alpha) {
                drawableFeatureState.cur = drawable;
                drawableFeatureState.curAlpha = drawableFeatureState.alpha;
                onDrawableChanged(i, drawable, drawableFeatureState.alpha);
            }
        }
    }

    private void updateInt(int i, int i2, boolean z) {
        if (this.mContentParent == null) {
            return;
        }
        int i3 = 1 << i;
        if ((getFeatures() & i3) == 0 && !z) {
            return;
        }
        if ((getLocalFeatures() & i3) == 0) {
            if (getContainer() != null) {
                getContainer().setChildInt(i, i2);
                return;
            }
            return;
        }
        onIntChanged(i, i2);
    }

    private android.widget.ImageView getLeftIconView() {
        if (this.mLeftIconView != null) {
            return this.mLeftIconView;
        }
        if (this.mContentParent == null) {
            installDecor();
        }
        android.widget.ImageView imageView = (android.widget.ImageView) findViewById(com.android.internal.R.id.left_icon);
        this.mLeftIconView = imageView;
        return imageView;
    }

    @Override // android.view.Window
    protected void dispatchWindowAttributesChanged(android.view.WindowManager.LayoutParams layoutParams) {
        super.dispatchWindowAttributesChanged(layoutParams);
        if (this.mDecor != null) {
            this.mDecor.updateColorViews(null, true);
        }
    }

    private android.widget.ProgressBar getCircularProgressBar(boolean z) {
        if (this.mCircularProgressBar != null) {
            return this.mCircularProgressBar;
        }
        if (this.mContentParent == null && z) {
            installDecor();
        }
        this.mCircularProgressBar = (android.widget.ProgressBar) findViewById(com.android.internal.R.id.progress_circular);
        if (this.mCircularProgressBar != null) {
            this.mCircularProgressBar.setVisibility(4);
        }
        return this.mCircularProgressBar;
    }

    private android.widget.ProgressBar getHorizontalProgressBar(boolean z) {
        if (this.mHorizontalProgressBar != null) {
            return this.mHorizontalProgressBar;
        }
        if (this.mContentParent == null && z) {
            installDecor();
        }
        this.mHorizontalProgressBar = (android.widget.ProgressBar) findViewById(com.android.internal.R.id.progress_horizontal);
        if (this.mHorizontalProgressBar != null) {
            this.mHorizontalProgressBar.setVisibility(4);
        }
        return this.mHorizontalProgressBar;
    }

    private android.widget.ImageView getRightIconView() {
        if (this.mRightIconView != null) {
            return this.mRightIconView;
        }
        if (this.mContentParent == null) {
            installDecor();
        }
        android.widget.ImageView imageView = (android.widget.ImageView) findViewById(com.android.internal.R.id.right_icon);
        this.mRightIconView = imageView;
        return imageView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callOnPanelClosed(int i, com.android.internal.policy.PhoneWindow.PanelFeatureState panelFeatureState, android.view.Menu menu) {
        android.view.Window.Callback callback = getCallback();
        if (callback == null) {
            return;
        }
        if (menu == null) {
            if (panelFeatureState == null && i >= 0 && i < this.mPanels.length) {
                panelFeatureState = this.mPanels[i];
            }
            if (panelFeatureState != null) {
                menu = panelFeatureState.menu;
            }
        }
        if ((panelFeatureState == null || panelFeatureState.isOpen) && !isDestroyed()) {
            callback.onPanelClosed(i, menu);
        }
    }

    private boolean isTvUserSetupComplete() {
        return (android.provider.Settings.Secure.getInt(getContext().getContentResolver(), android.provider.Settings.Secure.USER_SETUP_COMPLETE, 0) != 0) & (android.provider.Settings.Secure.getInt(getContext().getContentResolver(), android.provider.Settings.Secure.TV_USER_SETUP_COMPLETE, 0) != 0);
    }

    private boolean launchDefaultSearch(android.view.KeyEvent keyEvent) {
        android.view.SearchEvent searchEvent;
        boolean z = false;
        if (getContext().getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_LEANBACK) && !isTvUserSetupComplete()) {
            return false;
        }
        android.view.Window.Callback callback = getCallback();
        if (callback != null && !isDestroyed()) {
            int deviceId = keyEvent.getDeviceId();
            if (deviceId == 0) {
                searchEvent = null;
            } else {
                searchEvent = new android.view.SearchEvent(android.view.InputDevice.getDevice(deviceId));
            }
            try {
                z = callback.onSearchRequested(searchEvent);
            } catch (java.lang.AbstractMethodError e) {
                android.util.Log.e(TAG, "WindowCallback " + callback.getClass().getName() + " does not implement method onSearchRequested(SearchEvent); fa", e);
                z = callback.onSearchRequested();
            }
        }
        if (!z && (getContext().getResources().getConfiguration().uiMode & 15) == 4) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putInt(android.content.Intent.EXTRA_ASSIST_INPUT_DEVICE_ID, keyEvent.getDeviceId());
            bundle.putLong(android.content.Intent.EXTRA_TIME, keyEvent.getEventTime());
            ((android.app.SearchManager) getContext().getSystemService("search")).launchAssist(bundle);
            return true;
        }
        return z;
    }

    @Override // android.view.Window
    public void setVolumeControlStream(int i) {
        this.mVolumeControlStreamType = i;
    }

    @Override // android.view.Window
    public int getVolumeControlStream() {
        return this.mVolumeControlStreamType;
    }

    @Override // android.view.Window
    public void setMediaController(android.media.session.MediaController mediaController) {
        this.mMediaController = mediaController;
        if (mediaController != null && this.mOnModeChangedListener == null) {
            this.mAudioMode = getAudioManager().getMode();
            this.mOnModeChangedListener = new android.media.AudioManager.OnModeChangedListener() { // from class: com.android.internal.policy.PhoneWindow$$ExternalSyntheticLambda0
                @Override // android.media.AudioManager.OnModeChangedListener
                public final void onModeChanged(int i) {
                    com.android.internal.policy.PhoneWindow.this.lambda$setMediaController$1(i);
                }
            };
            getAudioManager().addOnModeChangedListener(getContext().getMainExecutor(), this.mOnModeChangedListener);
        } else if (this.mOnModeChangedListener != null) {
            getAudioManager().removeOnModeChangedListener(this.mOnModeChangedListener);
            this.mOnModeChangedListener = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMediaController$1(int i) {
        this.mAudioMode = i;
    }

    @Override // android.view.Window
    public android.media.session.MediaController getMediaController() {
        return this.mMediaController;
    }

    @Override // android.view.Window
    public void setEnterTransition(android.transition.Transition transition) {
        this.mEnterTransition = transition;
    }

    @Override // android.view.Window
    public void setReturnTransition(android.transition.Transition transition) {
        this.mReturnTransition = transition;
    }

    @Override // android.view.Window
    public void setExitTransition(android.transition.Transition transition) {
        this.mExitTransition = transition;
    }

    @Override // android.view.Window
    public void setReenterTransition(android.transition.Transition transition) {
        this.mReenterTransition = transition;
    }

    @Override // android.view.Window
    public void setSharedElementEnterTransition(android.transition.Transition transition) {
        this.mSharedElementEnterTransition = transition;
    }

    @Override // android.view.Window
    public void setSharedElementReturnTransition(android.transition.Transition transition) {
        this.mSharedElementReturnTransition = transition;
    }

    @Override // android.view.Window
    public void setSharedElementExitTransition(android.transition.Transition transition) {
        this.mSharedElementExitTransition = transition;
    }

    @Override // android.view.Window
    public void setSharedElementReenterTransition(android.transition.Transition transition) {
        this.mSharedElementReenterTransition = transition;
    }

    @Override // android.view.Window
    public android.transition.Transition getEnterTransition() {
        return this.mEnterTransition;
    }

    @Override // android.view.Window
    public android.transition.Transition getReturnTransition() {
        return this.mReturnTransition == USE_DEFAULT_TRANSITION ? getEnterTransition() : this.mReturnTransition;
    }

    @Override // android.view.Window
    public android.transition.Transition getExitTransition() {
        return this.mExitTransition;
    }

    @Override // android.view.Window
    public android.transition.Transition getReenterTransition() {
        return this.mReenterTransition == USE_DEFAULT_TRANSITION ? getExitTransition() : this.mReenterTransition;
    }

    @Override // android.view.Window
    public android.transition.Transition getSharedElementEnterTransition() {
        return this.mSharedElementEnterTransition;
    }

    @Override // android.view.Window
    public android.transition.Transition getSharedElementReturnTransition() {
        return this.mSharedElementReturnTransition == USE_DEFAULT_TRANSITION ? getSharedElementEnterTransition() : this.mSharedElementReturnTransition;
    }

    @Override // android.view.Window
    public android.transition.Transition getSharedElementExitTransition() {
        return this.mSharedElementExitTransition;
    }

    @Override // android.view.Window
    public android.transition.Transition getSharedElementReenterTransition() {
        return this.mSharedElementReenterTransition == USE_DEFAULT_TRANSITION ? getSharedElementExitTransition() : this.mSharedElementReenterTransition;
    }

    @Override // android.view.Window
    public void setAllowEnterTransitionOverlap(boolean z) {
        this.mAllowEnterTransitionOverlap = java.lang.Boolean.valueOf(z);
    }

    @Override // android.view.Window
    public boolean getAllowEnterTransitionOverlap() {
        if (this.mAllowEnterTransitionOverlap == null) {
            return true;
        }
        return this.mAllowEnterTransitionOverlap.booleanValue();
    }

    @Override // android.view.Window
    public void setAllowReturnTransitionOverlap(boolean z) {
        this.mAllowReturnTransitionOverlap = java.lang.Boolean.valueOf(z);
    }

    @Override // android.view.Window
    public boolean getAllowReturnTransitionOverlap() {
        if (this.mAllowReturnTransitionOverlap == null) {
            return true;
        }
        return this.mAllowReturnTransitionOverlap.booleanValue();
    }

    @Override // android.view.Window
    public long getTransitionBackgroundFadeDuration() {
        if (this.mBackgroundFadeDurationMillis < 0) {
            return 300L;
        }
        return this.mBackgroundFadeDurationMillis;
    }

    @Override // android.view.Window
    public void setTransitionBackgroundFadeDuration(long j) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("negative durations are not allowed");
        }
        this.mBackgroundFadeDurationMillis = j;
    }

    @Override // android.view.Window
    public void setSharedElementsUseOverlay(boolean z) {
        this.mSharedElementsUseOverlay = java.lang.Boolean.valueOf(z);
    }

    @Override // android.view.Window
    public boolean getSharedElementsUseOverlay() {
        if (this.mSharedElementsUseOverlay == null) {
            return true;
        }
        return this.mSharedElementsUseOverlay.booleanValue();
    }

    private static final class DrawableFeatureState {
        android.graphics.drawable.Drawable child;
        android.graphics.drawable.Drawable cur;
        android.graphics.drawable.Drawable def;
        final int featureId;
        android.graphics.drawable.Drawable local;
        int resid;
        android.net.Uri uri;
        int alpha = 255;
        int curAlpha = 255;

        DrawableFeatureState(int i) {
            this.featureId = i;
        }
    }

    static final class PanelFeatureState {
        int background;
        android.view.View createdPanelView;
        com.android.internal.policy.DecorView decorView;
        int featureId;
        android.os.Bundle frozenActionViewState;
        android.os.Bundle frozenMenuState;
        int fullBackground;
        int gravity;
        com.android.internal.view.menu.IconMenuPresenter iconMenuPresenter;
        boolean isCompact;
        boolean isHandled;
        boolean isInExpandedMode;
        boolean isOpen;
        boolean isPrepared;
        com.android.internal.view.menu.ListMenuPresenter listMenuPresenter;
        int listPresenterTheme;
        com.android.internal.view.menu.MenuBuilder menu;
        public boolean qwertyMode;
        boolean refreshDecorView = false;
        boolean refreshMenuContent;
        android.view.View shownPanelView;
        boolean wasLastExpanded;
        boolean wasLastOpen;
        int windowAnimations;
        int x;
        int y;

        PanelFeatureState(int i) {
            this.featureId = i;
        }

        public boolean isInListMode() {
            return this.isInExpandedMode || this.isCompact;
        }

        public boolean hasPanelItems() {
            if (this.shownPanelView == null) {
                return false;
            }
            if (this.createdPanelView != null) {
                return true;
            }
            return (this.isCompact || this.isInExpandedMode) ? this.listMenuPresenter.getAdapter().getCount() > 0 : ((android.view.ViewGroup) this.shownPanelView).getChildCount() > 0;
        }

        public void clearMenuPresenters() {
            if (this.menu != null) {
                this.menu.removeMenuPresenter(this.iconMenuPresenter);
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.iconMenuPresenter = null;
            this.listMenuPresenter = null;
        }

        void setStyle(android.content.Context context) {
            android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(com.android.internal.R.styleable.Theme);
            this.background = obtainStyledAttributes.getResourceId(46, 0);
            this.fullBackground = obtainStyledAttributes.getResourceId(47, 0);
            this.windowAnimations = obtainStyledAttributes.getResourceId(93, 0);
            this.isCompact = obtainStyledAttributes.getBoolean(378, false);
            this.listPresenterTheme = obtainStyledAttributes.getResourceId(379, com.android.internal.R.style.Theme_ExpandedMenu);
            obtainStyledAttributes.recycle();
        }

        void setMenu(com.android.internal.view.menu.MenuBuilder menuBuilder) {
            if (menuBuilder == this.menu) {
                return;
            }
            if (this.menu != null) {
                this.menu.removeMenuPresenter(this.iconMenuPresenter);
                this.menu.removeMenuPresenter(this.listMenuPresenter);
            }
            this.menu = menuBuilder;
            if (menuBuilder != null) {
                if (this.iconMenuPresenter != null) {
                    menuBuilder.addMenuPresenter(this.iconMenuPresenter);
                }
                if (this.listMenuPresenter != null) {
                    menuBuilder.addMenuPresenter(this.listMenuPresenter);
                }
            }
        }

        com.android.internal.view.menu.MenuView getListMenuView(android.content.Context context, com.android.internal.view.menu.MenuPresenter.Callback callback) {
            if (this.menu == null) {
                return null;
            }
            if (!this.isCompact) {
                getIconMenuView(context, callback);
            }
            if (this.listMenuPresenter == null) {
                this.listMenuPresenter = new com.android.internal.view.menu.ListMenuPresenter(com.android.internal.R.layout.list_menu_item_layout, this.listPresenterTheme);
                this.listMenuPresenter.setCallback(callback);
                this.listMenuPresenter.setId(com.android.internal.R.id.list_menu_presenter);
                this.menu.addMenuPresenter(this.listMenuPresenter);
            }
            if (this.iconMenuPresenter != null) {
                this.listMenuPresenter.setItemIndexOffset(this.iconMenuPresenter.getNumActualItemsShown());
            }
            return this.listMenuPresenter.getMenuView(this.decorView);
        }

        com.android.internal.view.menu.MenuView getIconMenuView(android.content.Context context, com.android.internal.view.menu.MenuPresenter.Callback callback) {
            if (this.menu == null) {
                return null;
            }
            if (this.iconMenuPresenter == null) {
                this.iconMenuPresenter = new com.android.internal.view.menu.IconMenuPresenter(context);
                this.iconMenuPresenter.setCallback(callback);
                this.iconMenuPresenter.setId(com.android.internal.R.id.icon_menu_presenter);
                this.menu.addMenuPresenter(this.iconMenuPresenter);
            }
            return this.iconMenuPresenter.getMenuView(this.decorView);
        }

        android.os.Parcelable onSaveInstanceState() {
            com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState savedState = new com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState();
            savedState.featureId = this.featureId;
            savedState.isOpen = this.isOpen;
            savedState.isInExpandedMode = this.isInExpandedMode;
            if (this.menu != null) {
                savedState.menuState = new android.os.Bundle();
                this.menu.savePresenterStates(savedState.menuState);
            }
            return savedState;
        }

        void onRestoreInstanceState(android.os.Parcelable parcelable) {
            com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState savedState = (com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState) parcelable;
            this.featureId = savedState.featureId;
            this.wasLastOpen = savedState.isOpen;
            this.wasLastExpanded = savedState.isInExpandedMode;
            this.frozenMenuState = savedState.menuState;
            this.createdPanelView = null;
            this.shownPanelView = null;
            this.decorView = null;
        }

        void applyFrozenState() {
            if (this.menu != null && this.frozenMenuState != null) {
                this.menu.restorePresenterStates(this.frozenMenuState);
                this.frozenMenuState = null;
            }
        }

        private static class SavedState implements android.os.Parcelable {
            public static final android.os.Parcelable.Creator<com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState> CREATOR = new android.os.Parcelable.Creator<com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState>() { // from class: com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState createFromParcel(android.os.Parcel parcel) {
                    return com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState.readFromParcel(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState[] newArray(int i) {
                    return new com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState[i];
                }
            };
            int featureId;
            boolean isInExpandedMode;
            boolean isOpen;
            android.os.Bundle menuState;

            private SavedState() {
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            @Override // android.os.Parcelable
            public void writeToParcel(android.os.Parcel parcel, int i) {
                parcel.writeInt(this.featureId);
                parcel.writeInt(this.isOpen ? 1 : 0);
                parcel.writeInt(this.isInExpandedMode ? 1 : 0);
                if (this.isOpen) {
                    parcel.writeBundle(this.menuState);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState readFromParcel(android.os.Parcel parcel) {
                com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState savedState = new com.android.internal.policy.PhoneWindow.PanelFeatureState.SavedState();
                savedState.featureId = parcel.readInt();
                savedState.isOpen = parcel.readInt() == 1;
                savedState.isInExpandedMode = parcel.readInt() == 1;
                if (savedState.isOpen) {
                    savedState.menuState = parcel.readBundle();
                }
                return savedState;
            }
        }
    }

    static class RotationWatcher extends android.view.IRotationWatcher.Stub {
        private android.os.Handler mHandler;
        private boolean mIsWatching;
        private final java.lang.Runnable mRotationChanged = new java.lang.Runnable() { // from class: com.android.internal.policy.PhoneWindow.RotationWatcher.1
            @Override // java.lang.Runnable
            public void run() {
                com.android.internal.policy.PhoneWindow.RotationWatcher.this.dispatchRotationChanged();
            }
        };
        private final java.util.ArrayList<java.lang.ref.WeakReference<com.android.internal.policy.PhoneWindow>> mWindows = new java.util.ArrayList<>();

        RotationWatcher() {
        }

        @Override // android.view.IRotationWatcher
        public void onRotationChanged(int i) throws android.os.RemoteException {
            this.mHandler.post(this.mRotationChanged);
        }

        public void addWindow(com.android.internal.policy.PhoneWindow phoneWindow) {
            synchronized (this.mWindows) {
                if (!this.mIsWatching) {
                    try {
                        com.android.internal.policy.PhoneWindow.WindowManagerHolder.sWindowManager.watchRotation(this, phoneWindow.getContext().getDisplayId());
                        this.mHandler = new android.os.Handler();
                        this.mIsWatching = true;
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(com.android.internal.policy.PhoneWindow.TAG, "Couldn't start watching for device rotation", e);
                    }
                }
                this.mWindows.add(new java.lang.ref.WeakReference<>(phoneWindow));
            }
        }

        public void removeWindow(com.android.internal.policy.PhoneWindow phoneWindow) {
            synchronized (this.mWindows) {
                int i = 0;
                while (i < this.mWindows.size()) {
                    com.android.internal.policy.PhoneWindow phoneWindow2 = this.mWindows.get(i).get();
                    if (phoneWindow2 != null && phoneWindow2 != phoneWindow) {
                        i++;
                    }
                    this.mWindows.remove(i);
                }
            }
        }

        void dispatchRotationChanged() {
            synchronized (this.mWindows) {
                int i = 0;
                while (i < this.mWindows.size()) {
                    com.android.internal.policy.PhoneWindow phoneWindow = this.mWindows.get(i).get();
                    if (phoneWindow != null) {
                        phoneWindow.onOptionsPanelRotationChanged();
                        i++;
                    } else {
                        this.mWindows.remove(i);
                    }
                }
            }
        }
    }

    public static final class PhoneWindowMenuCallback implements com.android.internal.view.menu.MenuBuilder.Callback, com.android.internal.view.menu.MenuPresenter.Callback {
        private static final int FEATURE_ID = 6;
        private boolean mShowDialogForSubmenu;
        private com.android.internal.view.menu.MenuDialogHelper mSubMenuHelper;
        private final com.android.internal.policy.PhoneWindow mWindow;

        public PhoneWindowMenuCallback(com.android.internal.policy.PhoneWindow phoneWindow) {
            this.mWindow = phoneWindow;
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(com.android.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
            if (menuBuilder.getRootMenu() != menuBuilder) {
                onCloseSubMenu(menuBuilder);
            }
            if (z) {
                android.view.Window.Callback callback = this.mWindow.getCallback();
                if (callback != null && !this.mWindow.isDestroyed()) {
                    callback.onPanelClosed(6, menuBuilder);
                }
                if (menuBuilder == this.mWindow.mContextMenu) {
                    this.mWindow.dismissContextMenu();
                }
                if (this.mSubMenuHelper != null) {
                    this.mSubMenuHelper.dismiss();
                    this.mSubMenuHelper = null;
                }
            }
        }

        private void onCloseSubMenu(com.android.internal.view.menu.MenuBuilder menuBuilder) {
            android.view.Window.Callback callback = this.mWindow.getCallback();
            if (callback != null && !this.mWindow.isDestroyed()) {
                callback.onPanelClosed(6, menuBuilder.getRootMenu());
            }
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(com.android.internal.view.menu.MenuBuilder menuBuilder, android.view.MenuItem menuItem) {
            android.view.Window.Callback callback = this.mWindow.getCallback();
            return (callback == null || this.mWindow.isDestroyed() || !callback.onMenuItemSelected(6, menuItem)) ? false : true;
        }

        @Override // com.android.internal.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(com.android.internal.view.menu.MenuBuilder menuBuilder) {
        }

        @Override // com.android.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(com.android.internal.view.menu.MenuBuilder menuBuilder) {
            if (menuBuilder == null) {
                return false;
            }
            menuBuilder.setCallback(this);
            if (!this.mShowDialogForSubmenu) {
                return false;
            }
            this.mSubMenuHelper = new com.android.internal.view.menu.MenuDialogHelper(menuBuilder);
            this.mSubMenuHelper.show(null);
            return true;
        }

        public void setShowDialogForSubmenu(boolean z) {
            this.mShowDialogForSubmenu = z;
        }
    }

    int getLocalFeaturesPrivate() {
        return super.getLocalFeatures();
    }

    @Override // android.view.Window
    protected void setDefaultWindowFormat(int i) {
        super.setDefaultWindowFormat(i);
    }

    void sendCloseSystemWindows() {
        sendCloseSystemWindows(getContext(), null);
    }

    void sendCloseSystemWindows(java.lang.String str) {
        sendCloseSystemWindows(getContext(), str);
    }

    public static void sendCloseSystemWindows(android.content.Context context, java.lang.String str) {
        if (android.app.ActivityManager.isSystemReady()) {
            try {
                android.app.ActivityManager.getService().closeSystemDialogs(str);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @Override // android.view.Window
    public int getStatusBarColor() {
        return this.mStatusBarColor;
    }

    @Override // android.view.Window
    public void setStatusBarColor(int i) {
        if (this.mEdgeToEdgeEnforced) {
            return;
        }
        if (this.mStatusBarColor == i && this.mForcedStatusBarColor) {
            return;
        }
        this.mStatusBarColor = i;
        this.mForcedStatusBarColor = true;
        if (this.mDecor != null) {
            this.mDecor.updateColorViews(null, false);
        }
        if (getWindowControllerCallback() != null) {
            getWindowControllerCallback().updateStatusBarColor(i);
        }
    }

    @Override // android.view.Window
    public int getNavigationBarColor() {
        return this.mNavigationBarColor;
    }

    @Override // android.view.Window
    public void setNavigationBarColor(int i) {
        if (this.mEdgeToEdgeEnforced) {
            return;
        }
        if (this.mNavigationBarColor == i && this.mForcedNavigationBarColor) {
            return;
        }
        this.mNavigationBarColor = i;
        this.mForcedNavigationBarColor = true;
        if (this.mDecor != null) {
            this.mDecor.updateColorViews(null, false);
        }
        if (getWindowControllerCallback() != null) {
            getWindowControllerCallback().updateNavigationBarColor(i);
        }
    }

    @Override // android.view.Window
    public void setNavigationBarDividerColor(int i) {
        if (this.mEdgeToEdgeEnforced) {
            return;
        }
        this.mNavigationBarDividerColor = i;
        if (this.mDecor != null) {
            this.mDecor.updateColorViews(null, false);
        }
    }

    @Override // android.view.Window
    public int getNavigationBarDividerColor() {
        return this.mNavigationBarDividerColor;
    }

    @Override // android.view.Window
    public void setStatusBarContrastEnforced(boolean z) {
        this.mEnsureStatusBarContrastWhenTransparent = z;
        if (this.mDecor != null) {
            this.mDecor.updateColorViews(null, false);
        }
    }

    @Override // android.view.Window
    public boolean isStatusBarContrastEnforced() {
        return this.mEnsureStatusBarContrastWhenTransparent;
    }

    @Override // android.view.Window
    public void setNavigationBarContrastEnforced(boolean z) {
        this.mEnsureNavigationBarContrastWhenTransparent = z;
        if (this.mDecor != null) {
            this.mDecor.updateColorViews(null, false);
        }
    }

    @Override // android.view.Window
    public boolean isNavigationBarContrastEnforced() {
        return this.mEnsureNavigationBarContrastWhenTransparent;
    }

    public void setIsStartingWindow(boolean z) {
        this.mIsStartingWindow = z;
    }

    @Override // android.view.Window
    public void setTheme(int i) {
        this.mTheme = i;
        if (this.mDecor != null) {
            android.content.Context context = this.mDecor.getContext();
            if (context instanceof com.android.internal.policy.DecorContext) {
                context.setTheme(i);
            }
        }
    }

    @Override // android.view.Window
    public void setResizingCaptionDrawable(android.graphics.drawable.Drawable drawable) {
        this.mDecor.setUserCaptionBackgroundDrawable(drawable);
    }

    @Override // android.view.Window
    public void setDecorCaptionShade(int i) {
        this.mDecorCaptionShade = i;
        if (this.mDecor != null) {
            this.mDecor.updateDecorCaptionShade();
        }
    }

    int getDecorCaptionShade() {
        return this.mDecorCaptionShade;
    }

    @Override // android.view.Window
    public void setAttributes(android.view.WindowManager.LayoutParams layoutParams) {
        super.setAttributes(layoutParams);
        if (this.mDecor != null) {
            this.mDecor.updateLogTag(layoutParams);
        }
    }

    @Override // android.view.Window
    public android.view.WindowInsetsController getInsetsController() {
        return this.mDecor.getWindowInsetsController();
    }

    @Override // android.view.Window
    public void setSystemGestureExclusionRects(java.util.List<android.graphics.Rect> list) {
        getViewRootImpl().setRootSystemGestureExclusionRects(list);
    }

    @Override // android.view.Window
    public java.util.List<android.graphics.Rect> getSystemGestureExclusionRects() {
        return getViewRootImpl().getRootSystemGestureExclusionRects();
    }

    @Override // android.view.Window
    public void setDecorFitsSystemWindows(boolean z) {
        if (this.mEdgeToEdgeEnforced) {
            return;
        }
        this.mDecorFitsSystemWindows = z;
        applyDecorFitsSystemWindows();
    }

    @Override // android.view.Window
    public boolean decorFitsSystemWindows() {
        return this.mDecorFitsSystemWindows;
    }

    private void applyDecorFitsSystemWindows() {
        android.view.Window.OnContentApplyWindowInsetsListener onContentApplyWindowInsetsListener;
        android.view.ViewRootImpl viewRootImplOrNull = getViewRootImplOrNull();
        if (viewRootImplOrNull != null) {
            if (this.mDecorFitsSystemWindows) {
                onContentApplyWindowInsetsListener = sDefaultContentInsetsApplier;
            } else {
                onContentApplyWindowInsetsListener = null;
            }
            viewRootImplOrNull.setOnContentApplyWindowInsetsListener(onContentApplyWindowInsetsListener);
        }
    }

    @Override // android.view.Window
    public void requestScrollCapture(android.view.IScrollCaptureResponseListener iScrollCaptureResponseListener) {
        getViewRootImpl().dispatchScrollCaptureRequest(iScrollCaptureResponseListener);
    }

    @Override // android.view.Window
    public void registerScrollCaptureCallback(android.view.ScrollCaptureCallback scrollCaptureCallback) {
        getViewRootImpl().addScrollCaptureCallback(scrollCaptureCallback);
    }

    @Override // android.view.Window
    public void unregisterScrollCaptureCallback(android.view.ScrollCaptureCallback scrollCaptureCallback) {
        getViewRootImpl().removeScrollCaptureCallback(scrollCaptureCallback);
    }

    @Override // android.view.Window
    public android.view.View getStatusBarBackgroundView() {
        if (this.mDecor != null) {
            return this.mDecor.getStatusBarBackgroundView();
        }
        return null;
    }

    @Override // android.view.Window
    public android.view.View getNavigationBarBackgroundView() {
        if (this.mDecor != null) {
            return this.mDecor.getNavigationBarBackgroundView();
        }
        return null;
    }

    @Override // android.view.Window
    public android.view.AttachedSurfaceControl getRootSurfaceControl() {
        return getViewRootImplOrNull();
    }

    @Override // android.view.Window
    public android.window.OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return this.mProxyOnBackInvokedDispatcher;
    }
}
