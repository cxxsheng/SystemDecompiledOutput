package android.view;

/* loaded from: classes4.dex */
public abstract class Window {
    public static final int DECOR_CAPTION_SHADE_AUTO = 0;
    public static final int DECOR_CAPTION_SHADE_DARK = 2;
    public static final int DECOR_CAPTION_SHADE_LIGHT = 1;

    @java.lang.Deprecated
    protected static final int DEFAULT_FEATURES = 65;
    public static final int FEATURE_ACTION_BAR = 8;
    public static final int FEATURE_ACTION_BAR_OVERLAY = 9;
    public static final int FEATURE_ACTION_MODE_OVERLAY = 10;
    public static final int FEATURE_ACTIVITY_TRANSITIONS = 13;
    public static final int FEATURE_CONTENT_TRANSITIONS = 12;
    public static final int FEATURE_CONTEXT_MENU = 6;
    public static final int FEATURE_CUSTOM_TITLE = 7;

    @java.lang.Deprecated
    public static final int FEATURE_INDETERMINATE_PROGRESS = 5;
    public static final int FEATURE_LEFT_ICON = 3;
    public static final int FEATURE_MAX = 13;
    public static final int FEATURE_NO_TITLE = 1;
    public static final int FEATURE_OPTIONS_PANEL = 0;

    @java.lang.Deprecated
    public static final int FEATURE_PROGRESS = 2;
    public static final int FEATURE_RIGHT_ICON = 4;

    @java.lang.Deprecated
    public static final int FEATURE_SWIPE_TO_DISMISS = 11;
    public static final int ID_ANDROID_CONTENT = 16908290;
    public static final java.lang.String NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME = "android:navigation:background";

    @java.lang.Deprecated
    public static final int PROGRESS_END = 10000;

    @java.lang.Deprecated
    public static final int PROGRESS_INDETERMINATE_OFF = -4;

    @java.lang.Deprecated
    public static final int PROGRESS_INDETERMINATE_ON = -3;

    @java.lang.Deprecated
    public static final int PROGRESS_SECONDARY_END = 30000;

    @java.lang.Deprecated
    public static final int PROGRESS_SECONDARY_START = 20000;

    @java.lang.Deprecated
    public static final int PROGRESS_START = 0;

    @java.lang.Deprecated
    public static final int PROGRESS_VISIBILITY_OFF = -2;

    @java.lang.Deprecated
    public static final int PROGRESS_VISIBILITY_ON = -1;
    public static final java.lang.String STATUS_BAR_BACKGROUND_TRANSITION_NAME = "android:status:background";
    private static boolean sToolkitSetFrameRateReadOnlyFlagValue = android.view.flags.Flags.toolkitSetFrameRateReadOnly();
    private android.view.Window mActiveChild;
    private java.lang.String mAppName;
    private android.os.IBinder mAppToken;
    private android.view.Window.Callback mCallback;
    private android.view.Window mContainer;
    private final android.content.Context mContext;
    private android.view.Window.DecorCallback mDecorCallback;
    private boolean mDestroyed;
    private int mFeatures;
    private boolean mHardwareAccelerated;
    private int mLocalFeatures;
    private android.view.Window.OnRestrictedCaptionAreaChangedListener mOnRestrictedCaptionAreaChangedListener;
    private android.view.Window.OnWindowDismissedCallback mOnWindowDismissedCallback;
    private android.view.Window.OnWindowSwipeDismissedCallback mOnWindowSwipeDismissedCallback;
    private android.graphics.Rect mRestrictedCaptionAreaRect;
    private int mSystemBarAppearance;
    private android.view.Window.WindowControllerCallback mWindowControllerCallback;
    private android.view.WindowManager mWindowManager;
    private android.content.res.TypedArray mWindowStyle;
    private boolean mIsActive = false;
    private boolean mHasChildren = false;
    private boolean mCloseOnTouchOutside = false;
    private boolean mSetCloseOnTouchOutside = false;
    private int mForcedWindowFlags = 0;
    private boolean mHaveWindowFormat = false;
    private boolean mHaveDimAmount = false;
    private int mDefaultWindowFormat = -1;
    private boolean mHasSoftInputMode = false;
    private boolean mOverlayWithDecorCaptionEnabled = true;
    private boolean mCloseOnSwipeEnabled = false;
    private final android.view.WindowManager.LayoutParams mWindowAttributes = new android.view.WindowManager.LayoutParams();

    public interface DecorCallback {
        boolean onDrawLegacyNavigationBarBackgroundChanged(boolean z);

        void onSystemBarAppearanceChanged(int i);
    }

    public interface OnContentApplyWindowInsetsListener {
        android.util.Pair<android.graphics.Insets, android.view.WindowInsets> onContentApplyWindowInsets(android.view.View view, android.view.WindowInsets windowInsets);
    }

    public interface OnFrameMetricsAvailableListener {
        void onFrameMetricsAvailable(android.view.Window window, android.view.FrameMetrics frameMetrics, int i);
    }

    public interface OnRestrictedCaptionAreaChangedListener {
        void onRestrictedCaptionAreaChanged(android.graphics.Rect rect);
    }

    public interface OnWindowDismissedCallback {
        void onWindowDismissed(boolean z, boolean z2);
    }

    public interface OnWindowSwipeDismissedCallback {
        void onWindowSwipeDismissed();
    }

    public interface WindowControllerCallback {
        void enterPictureInPictureModeIfPossible();

        boolean isTaskRoot();

        void toggleFreeformWindowingMode();

        void updateNavigationBarColor(int i);

        void updateStatusBarAppearance(int i);

        void updateStatusBarColor(int i);
    }

    public abstract void addContentView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams);

    public abstract void alwaysReadCloseOnTouchAttr();

    public abstract void clearContentView();

    public abstract void closeAllPanels();

    public abstract void closePanel(int i);

    public abstract android.view.View getCurrentFocus();

    public abstract android.view.View getDecorView();

    public abstract android.view.LayoutInflater getLayoutInflater();

    @java.lang.Deprecated
    public abstract int getNavigationBarColor();

    @java.lang.Deprecated
    public abstract int getStatusBarColor();

    public abstract int getVolumeControlStream();

    public abstract void invalidatePanelMenu(int i);

    public abstract boolean isFloating();

    public abstract boolean isShortcutKey(int i, android.view.KeyEvent keyEvent);

    protected abstract void onActive();

    public abstract void onConfigurationChanged(android.content.res.Configuration configuration);

    public abstract void onMultiWindowModeChanged();

    public abstract void onPictureInPictureModeChanged(boolean z);

    public abstract void openPanel(int i, android.view.KeyEvent keyEvent);

    public abstract android.view.View peekDecorView();

    public abstract boolean performContextMenuIdentifierAction(int i, int i2);

    public abstract boolean performPanelIdentifierAction(int i, int i2, int i3);

    public abstract boolean performPanelShortcut(int i, int i2, android.view.KeyEvent keyEvent, int i3);

    public abstract void restoreHierarchyState(android.os.Bundle bundle);

    public abstract android.os.Bundle saveHierarchyState();

    public abstract void setBackgroundDrawable(android.graphics.drawable.Drawable drawable);

    public abstract void setChildDrawable(int i, android.graphics.drawable.Drawable drawable);

    public abstract void setChildInt(int i, int i2);

    public abstract void setContentView(int i);

    public abstract void setContentView(android.view.View view);

    public abstract void setContentView(android.view.View view, android.view.ViewGroup.LayoutParams layoutParams);

    public abstract void setDecorCaptionShade(int i);

    public abstract void setFeatureDrawable(int i, android.graphics.drawable.Drawable drawable);

    public abstract void setFeatureDrawableAlpha(int i, int i2);

    public abstract void setFeatureDrawableResource(int i, int i2);

    public abstract void setFeatureDrawableUri(int i, android.net.Uri uri);

    public abstract void setFeatureInt(int i, int i2);

    @java.lang.Deprecated
    public abstract void setNavigationBarColor(int i);

    public abstract void setResizingCaptionDrawable(android.graphics.drawable.Drawable drawable);

    @java.lang.Deprecated
    public abstract void setStatusBarColor(int i);

    public abstract void setTitle(java.lang.CharSequence charSequence);

    @java.lang.Deprecated
    public abstract void setTitleColor(int i);

    public abstract void setVolumeControlStream(int i);

    public abstract boolean superDispatchGenericMotionEvent(android.view.MotionEvent motionEvent);

    public abstract boolean superDispatchKeyEvent(android.view.KeyEvent keyEvent);

    public abstract boolean superDispatchKeyShortcutEvent(android.view.KeyEvent keyEvent);

    public abstract boolean superDispatchTouchEvent(android.view.MotionEvent motionEvent);

    public abstract boolean superDispatchTrackballEvent(android.view.MotionEvent motionEvent);

    public abstract void takeInputQueue(android.view.InputQueue.Callback callback);

    public abstract void takeKeyEvents(boolean z);

    public abstract void takeSurface(android.view.SurfaceHolder.Callback2 callback2);

    public abstract void togglePanel(int i, android.view.KeyEvent keyEvent);

    public interface Callback {
        boolean dispatchGenericMotionEvent(android.view.MotionEvent motionEvent);

        boolean dispatchKeyEvent(android.view.KeyEvent keyEvent);

        boolean dispatchKeyShortcutEvent(android.view.KeyEvent keyEvent);

        boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent);

        boolean dispatchTouchEvent(android.view.MotionEvent motionEvent);

        boolean dispatchTrackballEvent(android.view.MotionEvent motionEvent);

        void onActionModeFinished(android.view.ActionMode actionMode);

        void onActionModeStarted(android.view.ActionMode actionMode);

        void onAttachedToWindow();

        void onContentChanged();

        boolean onCreatePanelMenu(int i, android.view.Menu menu);

        android.view.View onCreatePanelView(int i);

        void onDetachedFromWindow();

        boolean onMenuItemSelected(int i, android.view.MenuItem menuItem);

        boolean onMenuOpened(int i, android.view.Menu menu);

        void onPanelClosed(int i, android.view.Menu menu);

        boolean onPreparePanel(int i, android.view.View view, android.view.Menu menu);

        boolean onSearchRequested();

        boolean onSearchRequested(android.view.SearchEvent searchEvent);

        void onWindowAttributesChanged(android.view.WindowManager.LayoutParams layoutParams);

        void onWindowFocusChanged(boolean z);

        android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback);

        android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback callback, int i);

        default void onProvideKeyboardShortcuts(java.util.List<android.view.KeyboardShortcutGroup> list, android.view.Menu menu, int i) {
        }

        default void onPointerCaptureChanged(boolean z) {
        }
    }

    public Window(android.content.Context context) {
        this.mContext = context;
        int defaultFeatures = getDefaultFeatures(context);
        this.mLocalFeatures = defaultFeatures;
        this.mFeatures = defaultFeatures;
    }

    public final android.content.Context getContext() {
        return this.mContext;
    }

    public final android.content.res.TypedArray getWindowStyle() {
        android.content.res.TypedArray typedArray;
        synchronized (this) {
            if (this.mWindowStyle == null) {
                this.mWindowStyle = this.mContext.obtainStyledAttributes(com.android.internal.R.styleable.Window);
            }
            typedArray = this.mWindowStyle;
        }
        return typedArray;
    }

    public void setContainer(android.view.Window window) {
        this.mContainer = window;
        if (window != null) {
            this.mFeatures |= 2;
            this.mLocalFeatures |= 2;
            window.mHasChildren = true;
        }
    }

    public final android.view.Window getContainer() {
        return this.mContainer;
    }

    public final boolean hasChildren() {
        return this.mHasChildren;
    }

    public final void destroy() {
        this.mDestroyed = true;
        onDestroy();
    }

    protected void onDestroy() {
    }

    public final boolean isDestroyed() {
        return this.mDestroyed;
    }

    public void setWindowManager(android.view.WindowManager windowManager, android.os.IBinder iBinder, java.lang.String str) {
        setWindowManager(windowManager, iBinder, str, false);
    }

    public void setWindowManager(android.view.WindowManager windowManager, android.os.IBinder iBinder, java.lang.String str, boolean z) {
        this.mAppToken = iBinder;
        this.mAppName = str;
        this.mHardwareAccelerated = z;
        if (windowManager == null) {
            windowManager = (android.view.WindowManager) this.mContext.getSystemService(android.content.Context.WINDOW_SERVICE);
        }
        this.mWindowManager = ((android.view.WindowManagerImpl) windowManager).createLocalWindowManager(this);
    }

    void adjustLayoutParamsForSubWindow(android.view.WindowManager.LayoutParams layoutParams) {
        android.view.View peekDecorView;
        java.lang.CharSequence title = layoutParams.getTitle();
        if (layoutParams.type >= 1000 && layoutParams.type <= 1999) {
            if (layoutParams.token == null && (peekDecorView = peekDecorView()) != null) {
                layoutParams.token = peekDecorView.getWindowToken();
            }
            if (title == null || title.length() == 0) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder(32);
                if (layoutParams.type == 1001) {
                    sb.append("Media");
                } else if (layoutParams.type == 1004) {
                    sb.append("MediaOvr");
                } else if (layoutParams.type == 1000) {
                    sb.append("Panel");
                } else if (layoutParams.type == 1002) {
                    sb.append("SubPanel");
                } else if (layoutParams.type == 1005) {
                    sb.append("AboveSubPanel");
                } else if (layoutParams.type == 1003) {
                    sb.append("AtchDlg");
                } else {
                    sb.append(layoutParams.type);
                }
                if (this.mAppName != null) {
                    sb.append(":").append(this.mAppName);
                }
                layoutParams.setTitle(sb);
            }
        } else if (layoutParams.type >= 2000 && layoutParams.type <= 2999) {
            if (title == null || title.length() == 0) {
                java.lang.StringBuilder sb2 = new java.lang.StringBuilder(32);
                sb2.append("Sys").append(layoutParams.type);
                if (this.mAppName != null) {
                    sb2.append(":").append(this.mAppName);
                }
                layoutParams.setTitle(sb2);
            }
        } else {
            if (layoutParams.token == null) {
                layoutParams.token = this.mContainer == null ? this.mAppToken : this.mContainer.mAppToken;
            }
            if ((title == null || title.length() == 0) && this.mAppName != null) {
                layoutParams.setTitle(this.mAppName);
            }
        }
        if (layoutParams.packageName == null) {
            layoutParams.packageName = this.mContext.getPackageName();
        }
        if (this.mHardwareAccelerated || (this.mWindowAttributes.flags & 16777216) != 0) {
            layoutParams.flags |= 16777216;
        }
    }

    public android.view.WindowManager getWindowManager() {
        return this.mWindowManager;
    }

    public void setCallback(android.view.Window.Callback callback) {
        this.mCallback = callback;
    }

    public final android.view.Window.Callback getCallback() {
        return this.mCallback;
    }

    public final void addOnFrameMetricsAvailableListener(android.view.Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener, android.os.Handler handler) {
        android.view.View decorView = getDecorView();
        if (decorView == null) {
            throw new java.lang.IllegalStateException("can't observe a Window without an attached view");
        }
        if (onFrameMetricsAvailableListener == null) {
            throw new java.lang.NullPointerException("listener cannot be null");
        }
        decorView.addFrameMetricsListener(this, onFrameMetricsAvailableListener, handler);
    }

    public final void removeOnFrameMetricsAvailableListener(android.view.Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener) {
        if (getDecorView() != null) {
            getDecorView().removeFrameMetricsListener(onFrameMetricsAvailableListener);
        }
    }

    public final void setOnWindowDismissedCallback(android.view.Window.OnWindowDismissedCallback onWindowDismissedCallback) {
        this.mOnWindowDismissedCallback = onWindowDismissedCallback;
    }

    public final void dispatchOnWindowDismissed(boolean z, boolean z2) {
        if (this.mOnWindowDismissedCallback != null) {
            this.mOnWindowDismissedCallback.onWindowDismissed(z, z2);
        }
    }

    public final void setOnWindowSwipeDismissedCallback(android.view.Window.OnWindowSwipeDismissedCallback onWindowSwipeDismissedCallback) {
        this.mOnWindowSwipeDismissedCallback = onWindowSwipeDismissedCallback;
    }

    public final void dispatchOnWindowSwipeDismissed() {
        if (this.mOnWindowSwipeDismissedCallback != null) {
            this.mOnWindowSwipeDismissedCallback.onWindowSwipeDismissed();
        }
    }

    public final void setWindowControllerCallback(android.view.Window.WindowControllerCallback windowControllerCallback) {
        this.mWindowControllerCallback = windowControllerCallback;
    }

    public final android.view.Window.WindowControllerCallback getWindowControllerCallback() {
        return this.mWindowControllerCallback;
    }

    public final void setDecorCallback(android.view.Window.DecorCallback decorCallback) {
        this.mDecorCallback = decorCallback;
    }

    public final int getSystemBarAppearance() {
        return this.mSystemBarAppearance;
    }

    public final void dispatchOnSystemBarAppearanceChanged(int i) {
        this.mSystemBarAppearance = i;
        if (this.mDecorCallback != null) {
            this.mDecorCallback.onSystemBarAppearanceChanged(i);
        }
        if (this.mWindowControllerCallback != null) {
            this.mWindowControllerCallback.updateStatusBarAppearance(i);
        }
    }

    public final boolean onDrawLegacyNavigationBarBackgroundChanged(boolean z) {
        if (this.mDecorCallback == null) {
            return false;
        }
        return this.mDecorCallback.onDrawLegacyNavigationBarBackgroundChanged(z);
    }

    public final void setRestrictedCaptionAreaListener(android.view.Window.OnRestrictedCaptionAreaChangedListener onRestrictedCaptionAreaChangedListener) {
        this.mOnRestrictedCaptionAreaChangedListener = onRestrictedCaptionAreaChangedListener;
        this.mRestrictedCaptionAreaRect = onRestrictedCaptionAreaChangedListener != null ? new android.graphics.Rect() : null;
    }

    public final void setHideOverlayWindows(boolean z) {
        if (this.mContext.checkSelfPermission(android.Manifest.permission.HIDE_NON_SYSTEM_OVERLAY_WINDOWS) != 0 && this.mContext.checkSelfPermission(android.Manifest.permission.HIDE_OVERLAY_WINDOWS) != 0) {
            throw new java.lang.SecurityException("Permission denial: setHideOverlayWindows: HIDE_OVERLAY_WINDOWS");
        }
        setPrivateFlags(z ? 524288 : 0, 524288);
    }

    public void setLayout(int i, int i2) {
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        attributes.width = i;
        attributes.height = i2;
        dispatchWindowAttributesChanged(attributes);
    }

    public void setGravity(int i) {
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        attributes.gravity = i;
        dispatchWindowAttributesChanged(attributes);
    }

    public void setType(int i) {
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        attributes.type = i;
        dispatchWindowAttributesChanged(attributes);
    }

    public void setFormat(int i) {
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        if (i != 0) {
            attributes.format = i;
            this.mHaveWindowFormat = true;
        } else {
            attributes.format = this.mDefaultWindowFormat;
            this.mHaveWindowFormat = false;
        }
        dispatchWindowAttributesChanged(attributes);
    }

    public void setWindowAnimations(int i) {
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        attributes.windowAnimations = i;
        dispatchWindowAttributesChanged(attributes);
    }

    public void setSoftInputMode(int i) {
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        if (i != 0) {
            attributes.softInputMode = i;
            this.mHasSoftInputMode = true;
        } else {
            this.mHasSoftInputMode = false;
        }
        dispatchWindowAttributesChanged(attributes);
    }

    public void addFlags(int i) {
        setFlags(i, i);
    }

    public void addPrivateFlags(int i) {
        setPrivateFlags(i, i);
    }

    @android.annotation.SystemApi
    public void addSystemFlags(int i) {
        addPrivateFlags(i);
    }

    public void clearFlags(int i) {
        setFlags(0, i);
    }

    public void setFlags(int i, int i2) {
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        attributes.flags = (i & i2) | (attributes.flags & (~i2));
        this.mForcedWindowFlags |= i2;
        dispatchWindowAttributesChanged(attributes);
    }

    private void setPrivateFlags(int i, int i2) {
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        attributes.privateFlags = (i & i2) | (attributes.privateFlags & (~i2));
        dispatchWindowAttributesChanged(attributes);
    }

    protected void dispatchWindowAttributesChanged(android.view.WindowManager.LayoutParams layoutParams) {
        if (this.mCallback != null) {
            this.mCallback.onWindowAttributesChanged(layoutParams);
        }
    }

    public void setColorMode(int i) {
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        attributes.setColorMode(i);
        dispatchWindowAttributesChanged(attributes);
    }

    public void setDesiredHdrHeadroom(float f) {
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        attributes.setDesiredHdrHeadroom(f);
        dispatchWindowAttributesChanged(attributes);
    }

    public float getDesiredHdrHeadroom() {
        return getAttributes().getDesiredHdrHeadroom();
    }

    public void setFrameRateBoostOnTouchEnabled(boolean z) {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            android.view.WindowManager.LayoutParams attributes = getAttributes();
            attributes.setFrameRateBoostOnTouchEnabled(z);
            dispatchWindowAttributesChanged(attributes);
        }
    }

    public boolean getFrameRateBoostOnTouchEnabled() {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            return getAttributes().getFrameRateBoostOnTouchEnabled();
        }
        return true;
    }

    public void setFrameRatePowerSavingsBalanced(boolean z) {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            android.view.WindowManager.LayoutParams attributes = getAttributes();
            attributes.setFrameRatePowerSavingsBalanced(z);
            dispatchWindowAttributesChanged(attributes);
        }
    }

    public boolean isFrameRatePowerSavingsBalanced() {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            return getAttributes().isFrameRatePowerSavingsBalanced();
        }
        return false;
    }

    public void setPreferMinimalPostProcessing(boolean z) {
        this.mWindowAttributes.preferMinimalPostProcessing = z;
        dispatchWindowAttributesChanged(this.mWindowAttributes);
    }

    public int getColorMode() {
        return getAttributes().getColorMode();
    }

    public boolean isWideColorGamut() {
        return getColorMode() == 1 && getContext().getResources().getConfiguration().isScreenWideColorGamut();
    }

    public void setDimAmount(float f) {
        android.view.WindowManager.LayoutParams attributes = getAttributes();
        attributes.dimAmount = f;
        this.mHaveDimAmount = true;
        dispatchWindowAttributesChanged(attributes);
    }

    public void setDecorFitsSystemWindows(boolean z) {
    }

    public boolean decorFitsSystemWindows() {
        return false;
    }

    public void setAttributes(android.view.WindowManager.LayoutParams layoutParams) {
        this.mWindowAttributes.copyFrom(layoutParams);
        dispatchWindowAttributesChanged(this.mWindowAttributes);
    }

    public final android.view.WindowManager.LayoutParams getAttributes() {
        return this.mWindowAttributes;
    }

    protected final int getForcedWindowFlags() {
        return this.mForcedWindowFlags;
    }

    protected final boolean hasSoftInputMode() {
        return this.mHasSoftInputMode;
    }

    public void setCloseOnTouchOutside(boolean z) {
        this.mCloseOnTouchOutside = z;
        this.mSetCloseOnTouchOutside = true;
    }

    public void setCloseOnTouchOutsideIfNotSet(boolean z) {
        if (!this.mSetCloseOnTouchOutside) {
            this.mCloseOnTouchOutside = z;
            this.mSetCloseOnTouchOutside = true;
        }
    }

    public boolean shouldCloseOnTouchOutside() {
        return this.mCloseOnTouchOutside;
    }

    public boolean shouldCloseOnTouch(android.content.Context context, android.view.MotionEvent motionEvent) {
        return this.mCloseOnTouchOutside && peekDecorView() != null && ((motionEvent.getAction() == 1 && isOutOfBounds(context, motionEvent)) || motionEvent.getAction() == 4);
    }

    public void setSustainedPerformanceMode(boolean z) {
        setPrivateFlags(z ? 65536 : 0, 65536);
    }

    private boolean isOutOfBounds(android.content.Context context, android.view.MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int scaledWindowTouchSlop = android.view.ViewConfiguration.get(context).getScaledWindowTouchSlop();
        android.view.View decorView = getDecorView();
        int i = -scaledWindowTouchSlop;
        return x < i || y < i || x > decorView.getWidth() + scaledWindowTouchSlop || y > decorView.getHeight() + scaledWindowTouchSlop;
    }

    public boolean requestFeature(int i) {
        int i2 = 1 << i;
        this.mFeatures |= i2;
        this.mLocalFeatures |= this.mContainer != null ? (~this.mContainer.mFeatures) & i2 : i2;
        return (i2 & this.mFeatures) != 0;
    }

    protected void removeFeature(int i) {
        int i2 = 1 << i;
        this.mFeatures &= ~i2;
        int i3 = this.mLocalFeatures;
        if (this.mContainer != null) {
            i2 &= ~this.mContainer.mFeatures;
        }
        this.mLocalFeatures = (~i2) & i3;
    }

    public final void makeActive() {
        if (this.mContainer != null) {
            if (this.mContainer.mActiveChild != null) {
                this.mContainer.mActiveChild.mIsActive = false;
            }
            this.mContainer.mActiveChild = this;
        }
        this.mIsActive = true;
        onActive();
    }

    public final boolean isActive() {
        return this.mIsActive;
    }

    public <T extends android.view.View> T findViewById(int i) {
        return (T) getDecorView().findViewById(i);
    }

    public final <T extends android.view.View> T requireViewById(int i) {
        T t = (T) findViewById(i);
        if (t == null) {
            throw new java.lang.IllegalArgumentException("ID does not reference a View inside this Window");
        }
        return t;
    }

    public void setElevation(float f) {
    }

    public float getElevation() {
        return 0.0f;
    }

    public void setClipToOutline(boolean z) {
    }

    public void setBackgroundDrawableResource(int i) {
        setBackgroundDrawable(this.mContext.getDrawable(i));
    }

    public void setBackgroundBlurRadius(int i) {
    }

    public android.view.View getStatusBarBackgroundView() {
        return null;
    }

    public android.view.View getNavigationBarBackgroundView() {
        return null;
    }

    protected final int getFeatures() {
        return this.mFeatures;
    }

    public static int getDefaultFeatures(android.content.Context context) {
        int i;
        android.content.res.Resources resources = context.getResources();
        if (!resources.getBoolean(com.android.internal.R.bool.config_defaultWindowFeatureOptionsPanel)) {
            i = 0;
        } else {
            i = 1;
        }
        if (resources.getBoolean(com.android.internal.R.bool.config_defaultWindowFeatureContextMenu)) {
            return i | 64;
        }
        return i;
    }

    public boolean hasFeature(int i) {
        return ((1 << i) & getFeatures()) != 0;
    }

    protected final int getLocalFeatures() {
        return this.mLocalFeatures;
    }

    protected void setDefaultWindowFormat(int i) {
        this.mDefaultWindowFormat = i;
        if (!this.mHaveWindowFormat) {
            android.view.WindowManager.LayoutParams attributes = getAttributes();
            attributes.format = i;
            dispatchWindowAttributesChanged(attributes);
        }
    }

    protected boolean haveDimAmount() {
        return this.mHaveDimAmount;
    }

    public void setMediaController(android.media.session.MediaController mediaController) {
    }

    public android.media.session.MediaController getMediaController() {
        return null;
    }

    public void setUiOptions(int i) {
    }

    public void setUiOptions(int i, int i2) {
    }

    public void setIcon(int i) {
    }

    public void setDefaultIcon(int i) {
    }

    public void setLogo(int i) {
    }

    public void setDefaultLogo(int i) {
    }

    public void setLocalFocus(boolean z, boolean z2) {
    }

    public void injectInputEvent(android.view.InputEvent inputEvent) {
    }

    public android.transition.TransitionManager getTransitionManager() {
        return null;
    }

    public void setTransitionManager(android.transition.TransitionManager transitionManager) {
        throw new java.lang.UnsupportedOperationException();
    }

    public android.transition.Scene getContentScene() {
        return null;
    }

    public void setEnterTransition(android.transition.Transition transition) {
    }

    public void setReturnTransition(android.transition.Transition transition) {
    }

    public void setExitTransition(android.transition.Transition transition) {
    }

    public void setReenterTransition(android.transition.Transition transition) {
    }

    public android.transition.Transition getEnterTransition() {
        return null;
    }

    public android.transition.Transition getReturnTransition() {
        return null;
    }

    public android.transition.Transition getExitTransition() {
        return null;
    }

    public android.transition.Transition getReenterTransition() {
        return null;
    }

    public void setSharedElementEnterTransition(android.transition.Transition transition) {
    }

    public void setSharedElementReturnTransition(android.transition.Transition transition) {
    }

    public android.transition.Transition getSharedElementEnterTransition() {
        return null;
    }

    public android.transition.Transition getSharedElementReturnTransition() {
        return null;
    }

    public void setSharedElementExitTransition(android.transition.Transition transition) {
    }

    public void setSharedElementReenterTransition(android.transition.Transition transition) {
    }

    public android.transition.Transition getSharedElementExitTransition() {
        return null;
    }

    public android.transition.Transition getSharedElementReenterTransition() {
        return null;
    }

    public void setAllowEnterTransitionOverlap(boolean z) {
    }

    public boolean getAllowEnterTransitionOverlap() {
        return true;
    }

    public void setAllowReturnTransitionOverlap(boolean z) {
    }

    public boolean getAllowReturnTransitionOverlap() {
        return true;
    }

    public long getTransitionBackgroundFadeDuration() {
        return 0L;
    }

    public void setTransitionBackgroundFadeDuration(long j) {
    }

    public boolean getSharedElementsUseOverlay() {
        return true;
    }

    public void setSharedElementsUseOverlay(boolean z) {
    }

    @java.lang.Deprecated
    public void setNavigationBarDividerColor(int i) {
    }

    @java.lang.Deprecated
    public int getNavigationBarDividerColor() {
        return 0;
    }

    @java.lang.Deprecated
    public void setStatusBarContrastEnforced(boolean z) {
    }

    @java.lang.Deprecated
    public boolean isStatusBarContrastEnforced() {
        return false;
    }

    public void setNavigationBarContrastEnforced(boolean z) {
    }

    public boolean isNavigationBarContrastEnforced() {
        return false;
    }

    public void setSystemGestureExclusionRects(java.util.List<android.graphics.Rect> list) {
        throw new java.lang.UnsupportedOperationException("window does not support gesture exclusion rects");
    }

    public java.util.List<android.graphics.Rect> getSystemGestureExclusionRects() {
        return java.util.Collections.emptyList();
    }

    public void requestScrollCapture(android.view.IScrollCaptureResponseListener iScrollCaptureResponseListener) {
    }

    public void registerScrollCaptureCallback(android.view.ScrollCaptureCallback scrollCaptureCallback) {
    }

    public void unregisterScrollCaptureCallback(android.view.ScrollCaptureCallback scrollCaptureCallback) {
    }

    public void setTheme(int i) {
    }

    public void setOverlayWithDecorCaptionEnabled(boolean z) {
        this.mOverlayWithDecorCaptionEnabled = z;
    }

    public boolean isOverlayWithDecorCaptionEnabled() {
        return this.mOverlayWithDecorCaptionEnabled;
    }

    public void notifyRestrictedCaptionAreaCallback(int i, int i2, int i3, int i4) {
        if (this.mOnRestrictedCaptionAreaChangedListener != null) {
            this.mRestrictedCaptionAreaRect.set(i, i2, i3, i4);
            this.mOnRestrictedCaptionAreaChangedListener.onRestrictedCaptionAreaChanged(this.mRestrictedCaptionAreaRect);
        }
    }

    public android.view.WindowInsetsController getInsetsController() {
        return null;
    }

    public android.view.AttachedSurfaceControl getRootSurfaceControl() {
        return null;
    }

    public android.window.OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        throw new java.lang.RuntimeException("Not implemented. Must override in a subclass.");
    }
}
