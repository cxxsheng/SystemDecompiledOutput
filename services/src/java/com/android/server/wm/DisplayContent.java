package com.android.server.wm;

/* loaded from: classes3.dex */
class DisplayContent extends com.android.server.wm.RootDisplayArea implements com.android.server.policy.WindowManagerPolicy.DisplayContentInfo {
    private static final android.view.InsetsState.OnTraverseCallbacks COPY_SOURCE_VISIBILITY = new android.view.InsetsState.OnTraverseCallbacks() { // from class: com.android.server.wm.DisplayContent.1
        public void onIdMatch(android.view.InsetsSource insetsSource, android.view.InsetsSource insetsSource2) {
            insetsSource.setVisible(insetsSource2.isVisible());
        }
    };
    private static final long FIXED_ROTATION_HIDE_ANIMATION_DEBOUNCE_DELAY_MS = 250;
    static final int FORCE_SCALING_MODE_AUTO = 0;
    static final int FORCE_SCALING_MODE_DISABLED = 1;
    static final int IME_TARGET_CONTROL = 2;
    static final int IME_TARGET_LAYERING = 0;
    static final float INVALID_DPI = 0.0f;
    private static final java.lang.String TAG = "WindowManager";
    boolean isDefaultDisplay;
    private android.view.SurfaceControl mA11yOverlayLayer;
    private java.util.Set<com.android.server.wm.ActivityRecord> mActiveSizeCompatActivities;
    final java.util.ArrayList<com.android.server.wm.RootWindowContainer.SleepToken> mAllSleepTokens;
    final com.android.server.wm.AppTransition mAppTransition;
    final com.android.server.wm.AppTransitionController mAppTransitionController;
    private final java.util.function.Consumer<com.android.server.wm.WindowState> mApplyPostLayoutPolicy;
    private final java.util.function.Consumer<com.android.server.wm.WindowState> mApplySurfaceChangesTransaction;
    private com.android.server.wm.AsyncRotationController mAsyncRotationController;
    final com.android.server.wm.ActivityTaskManagerService mAtmService;
    android.view.DisplayCutout mBaseDisplayCutout;
    int mBaseDisplayDensity;
    int mBaseDisplayHeight;
    float mBaseDisplayPhysicalXDpi;
    float mBaseDisplayPhysicalYDpi;
    int mBaseDisplayWidth;
    android.view.RoundedCorners mBaseRoundedCorners;
    final android.util.ArraySet<com.android.server.wm.WindowContainer> mChangingContainers;

    @com.android.internal.annotations.VisibleForTesting
    final float mCloseToSquareMaxAspectRatio;
    final android.util.ArraySet<com.android.server.wm.ActivityRecord> mClosingApps;
    final android.util.ArrayMap<com.android.server.wm.WindowContainer, android.graphics.Rect> mClosingChangingContainers;
    private final android.util.DisplayMetrics mCompatDisplayMetrics;
    float mCompatibleScreenScale;
    private final java.util.function.Predicate<com.android.server.wm.WindowState> mComputeImeTargetPredicate;

    @android.annotation.Nullable
    private com.android.server.wm.ContentRecorder mContentRecorder;
    com.android.server.wm.WindowState mCurrentFocus;
    private int mCurrentOverrideConfigurationChanges;
    android.view.PrivacyIndicatorBounds mCurrentPrivacyIndicatorBounds;

    @android.annotation.Nullable
    java.lang.String mCurrentUniqueDisplayId;
    private final android.os.RemoteCallbackList<android.view.IDecorViewGestureListener> mDecorViewGestureListener;
    private int mDeferUpdateImeTargetCount;
    private boolean mDeferredRemoval;
    final java.util.function.Consumer<com.android.server.wm.DeviceStateController.DeviceState> mDeviceStateConsumer;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.wm.DeviceStateController mDeviceStateController;
    final android.view.Display mDisplay;
    private android.util.IntArray mDisplayAccessUIDs;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.DisplayAreaPolicy mDisplayAreaPolicy;
    private final com.android.server.wm.utils.RotationCache<android.view.DisplayCutout, com.android.server.wm.utils.WmDisplayCutout> mDisplayCutoutCache;
    com.android.server.wm.DisplayFrames mDisplayFrames;
    final int mDisplayId;
    private final android.view.DisplayInfo mDisplayInfo;
    private final android.util.DisplayMetrics mDisplayMetrics;
    private final com.android.server.wm.DisplayPolicy mDisplayPolicy;
    private boolean mDisplayReady;
    private final com.android.server.wm.DisplayRotation mDisplayRotation;

    @android.annotation.Nullable
    final com.android.server.wm.DisplayRotationCompatPolicy mDisplayRotationCompatPolicy;
    boolean mDisplayScalingDisabled;
    private final com.android.server.wm.utils.RotationCache<android.view.DisplayShape, android.view.DisplayShape> mDisplayShapeCache;
    final com.android.server.wm.PhysicalDisplaySwitchTransitionLauncher mDisplaySwitchTransitionLauncher;
    private final com.android.server.wm.DisplayUpdater mDisplayUpdater;
    boolean mDontMoveToTop;
    com.android.server.wm.DisplayWindowPolicyControllerHelper mDwpcHelper;
    private final com.android.internal.util.ToBooleanFunction<com.android.server.wm.WindowState> mFindFocusedWindow;
    private com.android.server.wm.ActivityRecord mFixedRotationLaunchingApp;
    final com.android.server.wm.DisplayContent.FixedRotationTransitionListener mFixedRotationTransitionListener;
    com.android.server.wm.ActivityRecord mFocusedApp;
    private android.window.SystemPerformanceHinter.HighPerfSession mHighFrameRateSession;
    private android.os.PowerManager.WakeLock mHoldScreenWakeLock;
    private com.android.server.wm.WindowState mHoldScreenWindow;
    boolean mIgnoreDisplayCutout;
    private com.android.server.wm.InsetsControlTarget mImeControlTarget;
    private com.android.server.wm.InputTarget mImeInputTarget;
    private com.android.server.wm.WindowState mImeLayeringTarget;
    com.android.server.wm.DisplayContent.ImeScreenshot mImeScreenshot;

    @android.annotation.Nullable
    private android.util.Pair<android.os.IBinder, com.android.server.wm.WindowContainerListener> mImeTargetTokenListenerPair;
    private final com.android.server.wm.DisplayContent.ImeContainer mImeWindowsContainer;
    private boolean mInEnsureActivitiesVisible;
    private boolean mInTouchMode;
    android.view.DisplayCutout mInitialDisplayCutout;
    int mInitialDisplayDensity;
    int mInitialDisplayHeight;
    android.view.DisplayShape mInitialDisplayShape;
    int mInitialDisplayWidth;
    float mInitialPhysicalXDpi;
    float mInitialPhysicalYDpi;
    android.view.RoundedCorners mInitialRoundedCorners;

    @com.android.internal.annotations.VisibleForTesting
    android.view.SurfaceControl mInputMethodSurfaceParent;
    com.android.server.wm.WindowState mInputMethodWindow;
    private com.android.server.wm.InputMonitor mInputMonitor;
    private android.view.SurfaceControl mInputOverlayLayer;
    private final com.android.server.wm.InsetsPolicy mInsetsPolicy;
    private final com.android.server.wm.InsetsStateController mInsetsStateController;
    boolean mIsDensityForced;
    boolean mIsSizeForced;
    boolean mLastContainsRunningSurfaceAnimator;

    @android.annotation.Nullable
    private android.view.DisplayInfo mLastDisplayInfoOverride;
    private boolean mLastHasContent;
    private com.android.server.wm.InputTarget mLastImeInputTarget;
    private com.android.server.wm.WindowState mLastWakeLockHoldingWindow;
    private com.android.server.wm.WindowState mLastWakeLockObscuringWindow;
    private boolean mLastWallpaperVisible;
    private boolean mLayoutNeeded;
    int mLayoutSeq;
    private android.view.MagnificationSpec mMagnificationSpec;
    private int mMaxUiWidth;
    private com.android.internal.logging.MetricsLogger mMetricsLogger;
    int mMinSizeOfResizeableTaskDp;
    final java.util.List<android.os.IBinder> mNoAnimationNotifyOnTransitionFinished;
    private com.android.server.wm.WindowState mObscuringWindow;
    private final com.android.server.wm.ActivityTaskManagerInternal.SleepTokenAcquirer mOffTokenAcquirer;
    final android.util.ArraySet<com.android.server.wm.ActivityRecord> mOpeningApps;

    @android.annotation.Nullable
    private com.android.server.wm.TaskDisplayArea mOrientationRequestingTaskDisplayArea;
    private android.view.SurfaceControl mOverlayLayer;
    private final java.util.function.Consumer<com.android.server.wm.WindowState> mPerformLayout;
    private final java.util.function.Consumer<com.android.server.wm.WindowState> mPerformLayoutAttached;
    private android.graphics.Point mPhysicalDisplaySize;
    final com.android.server.wm.PinnedTaskController mPinnedTaskController;
    private final com.android.server.wm.PointerEventDispatcher mPointerEventDispatcher;
    private final com.android.server.wm.utils.RotationCache<android.view.PrivacyIndicatorBounds, android.view.PrivacyIndicatorBounds> mPrivacyIndicatorBoundsCache;
    final android.util.DisplayMetrics mRealDisplayMetrics;
    final com.android.server.wm.RemoteDisplayChangeController mRemoteDisplayChangeController;
    com.android.server.wm.DisplayContent.RemoteInsetsControlTarget mRemoteInsetsControlTarget;
    private final android.os.IBinder.DeathRecipient mRemoteInsetsDeath;
    private boolean mRemoved;
    private boolean mRemoving;
    private java.util.Set<android.graphics.Rect> mRestrictedKeepClearAreas;
    private com.android.server.wm.RootWindowContainer mRootWindowContainer;
    private final com.android.server.wm.DisplayRotationReversionController mRotationReversionController;
    private final com.android.server.wm.utils.RotationCache<android.view.RoundedCorners, android.view.RoundedCorners> mRoundedCornerCache;
    private boolean mSandboxDisplayApis;
    private final java.util.function.Consumer<com.android.server.wm.WindowState> mScheduleToastTimeout;
    private com.android.server.wm.ScreenRotationAnimation mScreenRotationAnimation;
    private final android.view.SurfaceSession mSession;
    final android.util.SparseArray<com.android.server.wm.ShellRoot> mShellRoots;
    boolean mSkipAppTransitionAnimation;
    private boolean mSleeping;
    private final android.graphics.Region mSystemGestureExclusion;
    private int mSystemGestureExclusionLimit;
    private final android.os.RemoteCallbackList<android.view.ISystemGestureExclusionListener> mSystemGestureExclusionListeners;
    private final android.graphics.Region mSystemGestureExclusionUnrestricted;
    private boolean mSystemGestureExclusionWasRestricted;
    private final android.graphics.Rect mSystemGestureFrameLeft;
    private final android.graphics.Rect mSystemGestureFrameRight;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.wm.TaskTapPointerEventListener mTapDetector;
    final android.util.ArraySet<com.android.server.wm.WindowState> mTapExcludeProvidingWindows;
    final java.util.ArrayList<com.android.server.wm.WindowState> mTapExcludedWindows;
    private final android.content.res.Configuration mTempConfig;
    private final com.android.server.wm.DisplayContent.ApplySurfaceChangesTransactionState mTmpApplySurfaceChangesTransactionState;
    private final android.content.res.Configuration mTmpConfiguration;
    private final android.util.DisplayMetrics mTmpDisplayMetrics;
    private com.android.server.wm.WindowState mTmpHoldScreenWindow;
    private boolean mTmpInitial;
    private final android.graphics.Rect mTmpRect;
    private final android.graphics.Rect mTmpRect2;
    private final android.graphics.Region mTmpRegion;
    private final com.android.server.wm.DisplayContent.TaskForResizePointSearchResult mTmpTaskForResizePointSearchResult;
    private final java.util.LinkedList<com.android.server.wm.ActivityRecord> mTmpUpdateAllDrawn;
    private com.android.server.wm.WindowState mTmpWindow;
    private final java.util.HashMap<android.os.IBinder, com.android.server.wm.WindowToken> mTokenMap;
    private android.graphics.Region mTouchExcludeRegion;
    private android.window.SystemPerformanceHinter.HighPerfSession mTransitionPrefSession;
    final com.android.server.wm.UnknownAppVisibilityController mUnknownAppVisibilityController;
    private java.util.Set<android.graphics.Rect> mUnrestrictedKeepClearAreas;
    private boolean mUpdateImeRequestedWhileDeferred;
    private boolean mUpdateImeTarget;
    private final java.util.function.Consumer<com.android.server.wm.WindowState> mUpdateWindowsForAnimator;
    boolean mWaitingForConfig;
    com.android.server.wm.WallpaperController mWallpaperController;
    boolean mWallpaperMayChange;
    final java.util.ArrayList<com.android.server.wm.WindowState> mWinAddedSinceNullFocus;
    final java.util.ArrayList<com.android.server.wm.WindowState> mWinRemovedSinceNullFocus;
    private final float mWindowCornerRadius;
    private android.view.SurfaceControl mWindowingLayer;
    int pendingLayoutChanges;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ForceScalingMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface InputMethodTarget {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mRemoteInsetsControlTarget = null;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(com.android.server.wm.WindowState windowState) {
        com.android.server.wm.WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
        com.android.server.wm.ActivityRecord activityRecord = windowState.mActivityRecord;
        if (windowStateAnimator.mDrawState == 3) {
            if ((activityRecord == null || activityRecord.canShowWindows()) && windowState.performShowLocked()) {
                this.pendingLayoutChanges |= 8;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(com.android.server.wm.WindowState windowState) {
        int i = this.mTmpWindow.mOwnerUid;
        com.android.server.wm.WindowManagerService.H h = this.mWmService.mH;
        if (windowState.mAttrs.type == 2005 && windowState.mOwnerUid == i && !h.hasMessages(52, windowState)) {
            h.sendMessageDelayed(h.obtainMessage(52, windowState), windowState.mAttrs.hideTimeoutMilliseconds);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$3(com.android.server.wm.WindowState windowState) {
        com.android.server.wm.ActivityRecord activityRecord = this.mFocusedApp;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS, 1432179297701477868L, 52, null, java.lang.String.valueOf(windowState), java.lang.Long.valueOf(windowState.mAttrs.flags), java.lang.Boolean.valueOf(windowState.canReceiveKeys()), java.lang.String.valueOf(windowState.canReceiveKeysReason(false)));
        if (!windowState.canReceiveKeys()) {
            return false;
        }
        if (windowState.mIsImWindow && windowState.isChildWindow() && (this.mImeLayeringTarget == null || !this.mImeLayeringTarget.isRequestedVisible(android.view.WindowInsets.Type.ime()))) {
            return false;
        }
        if (windowState.mAttrs.type == 2012 && this.mImeLayeringTarget != null && !this.mImeLayeringTarget.isRequestedVisible(android.view.WindowInsets.Type.ime()) && !this.mImeLayeringTarget.isVisibleRequested()) {
            return false;
        }
        com.android.server.wm.ActivityRecord activityRecord2 = windowState.mActivityRecord;
        if (activityRecord == null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -1998969924927409574L, 0, null, java.lang.String.valueOf(windowState));
            this.mTmpWindow = windowState;
            return true;
        }
        if (!activityRecord.windowsAreFocusable()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -1513212297283619351L, 0, null, java.lang.String.valueOf(windowState));
            this.mTmpWindow = windowState;
            return true;
        }
        if (activityRecord2 != null && windowState.mAttrs.type != 3) {
            if (activityRecord.compareTo((com.android.server.wm.WindowContainer) activityRecord2) > 0) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, 271075236829935631L, 0, null, java.lang.String.valueOf(activityRecord));
                this.mTmpWindow = null;
                return true;
            }
            com.android.server.wm.TaskFragment taskFragment = activityRecord2.getTaskFragment();
            if (taskFragment != null && taskFragment.isEmbedded() && activityRecord2.getTask() == activityRecord.getTask() && activityRecord2.getTaskFragment() != activityRecord.getTaskFragment()) {
                return false;
            }
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, 3066566560703920191L, 0, null, java.lang.String.valueOf(windowState));
        this.mTmpWindow = windowState;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(com.android.server.wm.WindowState windowState) {
        if (windowState.mLayoutAttached) {
            return;
        }
        if (!windowState.isGoneForLayout() || !windowState.mHaveFrame || windowState.mLayoutNeeded) {
            if (this.mTmpInitial) {
                windowState.resetContentChanged();
            }
            windowState.mSurfacePlacementNeeded = true;
            windowState.mLayoutNeeded = false;
            boolean isLaidOut = true ^ windowState.isLaidOut();
            getDisplayPolicy().layoutWindowLw(windowState, null, this.mDisplayFrames);
            windowState.mLayoutSeq = this.mLayoutSeq;
            if (isLaidOut) {
                if (!windowState.getFrame().isEmpty()) {
                    windowState.updateLastFrames();
                    this.mWmService.mFrameChangingWindows.remove(windowState);
                }
                windowState.onResizeHandled();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(com.android.server.wm.WindowState windowState) {
        if (!windowState.mLayoutAttached) {
            return;
        }
        if ((windowState.mViewVisibility != 8 && windowState.mRelayoutCalled) || !windowState.mHaveFrame || windowState.mLayoutNeeded) {
            if (this.mTmpInitial) {
                windowState.resetContentChanged();
            }
            windowState.mSurfacePlacementNeeded = true;
            windowState.mLayoutNeeded = false;
            getDisplayPolicy().layoutWindowLw(windowState, windowState.getParentWindow(), this.mDisplayFrames);
            windowState.mLayoutSeq = this.mLayoutSeq;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$6(com.android.server.wm.WindowState windowState) {
        return windowState.canBeImeTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$7(com.android.server.wm.WindowState windowState) {
        getDisplayPolicy().applyPostLayoutPolicyLw(windowState, windowState.mAttrs, windowState.getParentWindow(), this.mImeLayeringTarget);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$8(com.android.server.wm.WindowState windowState) {
        com.android.server.wm.WindowSurfacePlacer windowSurfacePlacer = this.mWmService.mWindowPlacerLocked;
        boolean z = windowState.mObscured != this.mTmpApplySurfaceChangesTransactionState.obscured;
        com.android.server.wm.RootWindowContainer rootWindowContainer = this.mWmService.mRoot;
        if (windowState.mHasSurface) {
            boolean commitFinishDrawingLocked = windowState.mWinAnimator.commitFinishDrawingLocked();
            if (this.isDefaultDisplay && commitFinishDrawingLocked && windowState.hasWallpaper()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WALLPAPER, -8667452489821572603L, 0, null, java.lang.String.valueOf(windowState));
                this.mWallpaperMayChange = true;
                this.pendingLayoutChanges |= 4;
            }
        }
        windowState.mObscured = this.mTmpApplySurfaceChangesTransactionState.obscured;
        if (!this.mTmpApplySurfaceChangesTransactionState.obscured) {
            boolean isDisplayed = windowState.isDisplayed();
            if (isDisplayed && windowState.isObscuringDisplay()) {
                this.mObscuringWindow = windowState;
                this.mTmpApplySurfaceChangesTransactionState.obscured = true;
            }
            boolean handleNotObscuredLocked = rootWindowContainer.handleNotObscuredLocked(windowState, this.mTmpApplySurfaceChangesTransactionState.obscured, this.mTmpApplySurfaceChangesTransactionState.syswin);
            if (!this.mTmpApplySurfaceChangesTransactionState.displayHasContent && !getDisplayPolicy().isWindowExcludedFromContent(windowState)) {
                com.android.server.wm.DisplayContent.ApplySurfaceChangesTransactionState applySurfaceChangesTransactionState = this.mTmpApplySurfaceChangesTransactionState;
                applySurfaceChangesTransactionState.displayHasContent = handleNotObscuredLocked | applySurfaceChangesTransactionState.displayHasContent;
            }
            if (windowState.mHasSurface && isDisplayed) {
                if ((windowState.mAttrs.flags & 128) != 0) {
                    this.mTmpHoldScreenWindow = windowState;
                } else if (windowState == this.mLastWakeLockHoldingWindow) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_KEEP_SCREEN_ON, 6283995720623600346L, 0, null, java.lang.String.valueOf(windowState), java.lang.String.valueOf(android.os.Debug.getCallers(10)));
                }
                int i = windowState.mAttrs.type;
                if (i == 2008 || i == 2010 || (i == 2040 && this.mWmService.mPolicy.isKeyguardShowing())) {
                    this.mTmpApplySurfaceChangesTransactionState.syswin = true;
                }
                if (this.mTmpApplySurfaceChangesTransactionState.preferredRefreshRate == 0.0f && windowState.mAttrs.preferredRefreshRate != 0.0f) {
                    this.mTmpApplySurfaceChangesTransactionState.preferredRefreshRate = windowState.mAttrs.preferredRefreshRate;
                }
                this.mTmpApplySurfaceChangesTransactionState.preferMinimalPostProcessing |= windowState.mAttrs.preferMinimalPostProcessing;
                com.android.server.wm.DisplayContent.ApplySurfaceChangesTransactionState applySurfaceChangesTransactionState2 = this.mTmpApplySurfaceChangesTransactionState;
                applySurfaceChangesTransactionState2.disableHdrConversion = (true ^ windowState.mAttrs.isHdrConversionEnabled()) | applySurfaceChangesTransactionState2.disableHdrConversion;
                int preferredModeId = getDisplayPolicy().getRefreshRatePolicy().getPreferredModeId(windowState);
                if (windowState.getWindowingMode() != 2 && this.mTmpApplySurfaceChangesTransactionState.preferredModeId == 0 && preferredModeId != 0) {
                    this.mTmpApplySurfaceChangesTransactionState.preferredModeId = preferredModeId;
                }
                float preferredMinRefreshRate = getDisplayPolicy().getRefreshRatePolicy().getPreferredMinRefreshRate(windowState);
                if (this.mTmpApplySurfaceChangesTransactionState.preferredMinRefreshRate == 0.0f && preferredMinRefreshRate != 0.0f) {
                    this.mTmpApplySurfaceChangesTransactionState.preferredMinRefreshRate = preferredMinRefreshRate;
                }
                float preferredMaxRefreshRate = getDisplayPolicy().getRefreshRatePolicy().getPreferredMaxRefreshRate(windowState);
                if (this.mTmpApplySurfaceChangesTransactionState.preferredMaxRefreshRate == 0.0f && preferredMaxRefreshRate != 0.0f) {
                    this.mTmpApplySurfaceChangesTransactionState.preferredMaxRefreshRate = preferredMaxRefreshRate;
                }
            }
        }
        if (z && windowState.isVisible() && this.mWallpaperController.isWallpaperTarget(windowState)) {
            this.mWallpaperController.updateWallpaperTokens(this.mDisplayContent.isKeyguardLocked());
        }
        windowState.handleWindowMovedIfNeeded();
        windowState.resetContentChanged();
        com.android.server.wm.ActivityRecord activityRecord = windowState.mActivityRecord;
        if (activityRecord != null && activityRecord.isVisibleRequested()) {
            activityRecord.updateLetterboxSurfaceIfNeeded(windowState);
            if (activityRecord.updateDrawnWindowStates(windowState) && !this.mTmpUpdateAllDrawn.contains(activityRecord)) {
                this.mTmpUpdateAllDrawn.add(activityRecord);
            }
        }
        windowState.updateResizingWindowIfNeeded();
    }

    DisplayContent(android.view.Display display, com.android.server.wm.RootWindowContainer rootWindowContainer, @android.annotation.NonNull com.android.server.wm.DeviceStateController deviceStateController) {
        super(rootWindowContainer.mWindowManager, "DisplayContent", 0);
        this.mMinSizeOfResizeableTaskDp = -1;
        this.mImeWindowsContainer = new com.android.server.wm.DisplayContent.ImeContainer(this.mWmService);
        this.mMaxUiWidth = 0;
        this.mSkipAppTransitionAnimation = false;
        this.mOpeningApps = new android.util.ArraySet<>();
        this.mClosingApps = new android.util.ArraySet<>();
        this.mChangingContainers = new android.util.ArraySet<>();
        this.mClosingChangingContainers = new android.util.ArrayMap<>();
        this.mNoAnimationNotifyOnTransitionFinished = new java.util.ArrayList();
        this.mTokenMap = new java.util.HashMap<>();
        this.mInitialDisplayWidth = 0;
        this.mInitialDisplayHeight = 0;
        this.mInitialPhysicalXDpi = 0.0f;
        this.mInitialPhysicalYDpi = 0.0f;
        this.mInitialDisplayDensity = 0;
        this.mDisplayCutoutCache = new com.android.server.wm.utils.RotationCache<>(new com.android.server.wm.utils.RotationCache.RotationDependentComputation() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda40
            @Override // com.android.server.wm.utils.RotationCache.RotationDependentComputation
            public final java.lang.Object compute(java.lang.Object obj, int i) {
                com.android.server.wm.utils.WmDisplayCutout calculateDisplayCutoutForRotationUncached;
                calculateDisplayCutoutForRotationUncached = com.android.server.wm.DisplayContent.this.calculateDisplayCutoutForRotationUncached((android.view.DisplayCutout) obj, i);
                return calculateDisplayCutoutForRotationUncached;
            }
        });
        this.mRoundedCornerCache = new com.android.server.wm.utils.RotationCache<>(new com.android.server.wm.utils.RotationCache.RotationDependentComputation() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda45
            @Override // com.android.server.wm.utils.RotationCache.RotationDependentComputation
            public final java.lang.Object compute(java.lang.Object obj, int i) {
                android.view.RoundedCorners calculateRoundedCornersForRotationUncached;
                calculateRoundedCornersForRotationUncached = com.android.server.wm.DisplayContent.this.calculateRoundedCornersForRotationUncached((android.view.RoundedCorners) obj, i);
                return calculateRoundedCornersForRotationUncached;
            }
        });
        this.mCurrentPrivacyIndicatorBounds = new android.view.PrivacyIndicatorBounds();
        this.mPrivacyIndicatorBoundsCache = new com.android.server.wm.utils.RotationCache<>(new com.android.server.wm.utils.RotationCache.RotationDependentComputation() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda46
            @Override // com.android.server.wm.utils.RotationCache.RotationDependentComputation
            public final java.lang.Object compute(java.lang.Object obj, int i) {
                android.view.PrivacyIndicatorBounds calculatePrivacyIndicatorBoundsForRotationUncached;
                calculatePrivacyIndicatorBoundsForRotationUncached = com.android.server.wm.DisplayContent.this.calculatePrivacyIndicatorBoundsForRotationUncached((android.view.PrivacyIndicatorBounds) obj, i);
                return calculatePrivacyIndicatorBoundsForRotationUncached;
            }
        });
        this.mDisplayShapeCache = new com.android.server.wm.utils.RotationCache<>(new com.android.server.wm.utils.RotationCache.RotationDependentComputation() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda47
            @Override // com.android.server.wm.utils.RotationCache.RotationDependentComputation
            public final java.lang.Object compute(java.lang.Object obj, int i) {
                android.view.DisplayShape calculateDisplayShapeForRotationUncached;
                calculateDisplayShapeForRotationUncached = com.android.server.wm.DisplayContent.this.calculateDisplayShapeForRotationUncached((android.view.DisplayShape) obj, i);
                return calculateDisplayShapeForRotationUncached;
            }
        });
        this.mBaseDisplayWidth = 0;
        this.mBaseDisplayHeight = 0;
        this.mIsSizeForced = false;
        this.mSandboxDisplayApis = true;
        this.mBaseDisplayDensity = 0;
        this.mIsDensityForced = false;
        this.mBaseDisplayPhysicalXDpi = 0.0f;
        this.mBaseDisplayPhysicalYDpi = 0.0f;
        this.mDisplayInfo = new android.view.DisplayInfo();
        this.mDisplayMetrics = new android.util.DisplayMetrics();
        this.mSystemGestureExclusionListeners = new android.os.RemoteCallbackList<>();
        this.mDecorViewGestureListener = new android.os.RemoteCallbackList<>();
        this.mSystemGestureExclusion = new android.graphics.Region();
        this.mSystemGestureExclusionWasRestricted = false;
        this.mSystemGestureExclusionUnrestricted = new android.graphics.Region();
        this.mSystemGestureFrameLeft = new android.graphics.Rect();
        this.mSystemGestureFrameRight = new android.graphics.Rect();
        this.mRestrictedKeepClearAreas = new android.util.ArraySet();
        this.mUnrestrictedKeepClearAreas = new android.util.ArraySet();
        this.mRealDisplayMetrics = new android.util.DisplayMetrics();
        this.mTmpDisplayMetrics = new android.util.DisplayMetrics();
        this.mCompatDisplayMetrics = new android.util.DisplayMetrics();
        this.mLastWallpaperVisible = false;
        this.mTouchExcludeRegion = new android.graphics.Region();
        this.mTmpRect = new android.graphics.Rect();
        this.mTmpRect2 = new android.graphics.Rect();
        this.mTmpRegion = new android.graphics.Region();
        this.mTmpConfiguration = new android.content.res.Configuration();
        this.mTapExcludedWindows = new java.util.ArrayList<>();
        this.mTapExcludeProvidingWindows = new android.util.ArraySet<>();
        this.mTmpUpdateAllDrawn = new java.util.LinkedList<>();
        this.mTmpTaskForResizePointSearchResult = new com.android.server.wm.DisplayContent.TaskForResizePointSearchResult();
        this.mTmpApplySurfaceChangesTransactionState = new com.android.server.wm.DisplayContent.ApplySurfaceChangesTransactionState();
        this.mDisplayReady = false;
        this.mWallpaperMayChange = false;
        this.mSession = new android.view.SurfaceSession();
        this.mCurrentFocus = null;
        this.mFocusedApp = null;
        this.mOrientationRequestingTaskDisplayArea = null;
        this.mFixedRotationTransitionListener = new com.android.server.wm.DisplayContent.FixedRotationTransitionListener();
        this.mWinAddedSinceNullFocus = new java.util.ArrayList<>();
        this.mWinRemovedSinceNullFocus = new java.util.ArrayList<>();
        this.mLayoutSeq = 0;
        this.mShellRoots = new android.util.SparseArray<>();
        this.mRemoteInsetsControlTarget = null;
        this.mRemoteInsetsDeath = new android.os.IBinder.DeathRecipient() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda48
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                com.android.server.wm.DisplayContent.this.lambda$new$0();
            }
        };
        this.mDisplayAccessUIDs = new android.util.IntArray();
        this.mAllSleepTokens = new java.util.ArrayList<>();
        this.mActiveSizeCompatActivities = new android.util.ArraySet();
        this.mTempConfig = new android.content.res.Configuration();
        this.mInEnsureActivitiesVisible = false;
        this.mUpdateWindowsForAnimator = new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda49
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.this.lambda$new$1((com.android.server.wm.WindowState) obj);
            }
        };
        this.mScheduleToastTimeout = new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda50
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.this.lambda$new$2((com.android.server.wm.WindowState) obj);
            }
        };
        this.mFindFocusedWindow = new com.android.internal.util.ToBooleanFunction() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda51
            public final boolean apply(java.lang.Object obj) {
                boolean lambda$new$3;
                lambda$new$3 = com.android.server.wm.DisplayContent.this.lambda$new$3((com.android.server.wm.WindowState) obj);
                return lambda$new$3;
            }
        };
        this.mPerformLayout = new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda52
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.this.lambda$new$4((com.android.server.wm.WindowState) obj);
            }
        };
        this.mPerformLayoutAttached = new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda53
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.this.lambda$new$5((com.android.server.wm.WindowState) obj);
            }
        };
        this.mComputeImeTargetPredicate = new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda41
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$new$6;
                lambda$new$6 = com.android.server.wm.DisplayContent.this.lambda$new$6((com.android.server.wm.WindowState) obj);
                return lambda$new$6;
            }
        };
        this.mApplyPostLayoutPolicy = new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda42
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.this.lambda$new$7((com.android.server.wm.WindowState) obj);
            }
        };
        this.mApplySurfaceChangesTransaction = new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda43
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.this.lambda$new$8((com.android.server.wm.WindowState) obj);
            }
        };
        if (this.mWmService.mRoot.getDisplayContent(display.getDisplayId()) != null) {
            throw new java.lang.IllegalArgumentException("Display with ID=" + display.getDisplayId() + " already exists=" + this.mWmService.mRoot.getDisplayContent(display.getDisplayId()) + " new=" + display);
        }
        this.mRootWindowContainer = rootWindowContainer;
        this.mAtmService = this.mWmService.mAtmService;
        this.mDisplay = display;
        this.mDisplayId = display.getDisplayId();
        this.mCurrentUniqueDisplayId = display.getUniqueId();
        this.mOffTokenAcquirer = this.mRootWindowContainer.mDisplayOffTokenAcquirer;
        this.mWallpaperController = new com.android.server.wm.WallpaperController(this.mWmService, this);
        this.mWallpaperController.resetLargestDisplay(display);
        display.getDisplayInfo(this.mDisplayInfo);
        display.getMetrics(this.mDisplayMetrics);
        if (com.android.window.flags.Flags.deferDisplayUpdates()) {
            this.mDisplayUpdater = new com.android.server.wm.DeferredDisplayUpdater(this);
        } else {
            this.mDisplayUpdater = new com.android.server.wm.ImmediateDisplayUpdater(this);
        }
        this.mSystemGestureExclusionLimit = (this.mWmService.mConstants.mSystemGestureExclusionLimitDp * this.mDisplayMetrics.densityDpi) / 160;
        this.isDefaultDisplay = this.mDisplayId == 0;
        this.mInsetsStateController = new com.android.server.wm.InsetsStateController(this);
        initializeDisplayBaseInfo();
        this.mDisplayFrames = new com.android.server.wm.DisplayFrames(this.mInsetsStateController.getRawInsetsState(), this.mDisplayInfo, calculateDisplayCutoutForRotation(this.mDisplayInfo.rotation), calculateRoundedCornersForRotation(this.mDisplayInfo.rotation), calculatePrivacyIndicatorBoundsForRotation(this.mDisplayInfo.rotation), calculateDisplayShapeForRotation(this.mDisplayInfo.rotation));
        this.mHoldScreenWakeLock = this.mWmService.mPowerManager.newWakeLock(536870922, "WindowManager/displayId:" + this.mDisplayId, this.mDisplayId);
        this.mHoldScreenWakeLock.setReferenceCounted(false);
        this.mAppTransition = new com.android.server.wm.AppTransition(this.mWmService.mContext, this.mWmService, this);
        this.mAppTransition.registerListenerLocked(this.mWmService.mActivityManagerAppTransitionNotifier);
        this.mAppTransition.registerListenerLocked(this.mFixedRotationTransitionListener);
        this.mAppTransitionController = new com.android.server.wm.AppTransitionController(this.mWmService, this);
        this.mTransitionController.registerLegacyListener(this.mFixedRotationTransitionListener);
        this.mUnknownAppVisibilityController = new com.android.server.wm.UnknownAppVisibilityController(this.mWmService, this);
        this.mDisplaySwitchTransitionLauncher = new com.android.server.wm.PhysicalDisplaySwitchTransitionLauncher(this, this.mTransitionController);
        this.mRemoteDisplayChangeController = new com.android.server.wm.RemoteDisplayChangeController(this);
        this.mPointerEventDispatcher = new com.android.server.wm.PointerEventDispatcher(this.mWmService.mInputManager.monitorInput("PointerEventDispatcher" + this.mDisplayId, this.mDisplayId));
        if (com.android.input.flags.Flags.removePointerEventTrackingInWm()) {
            this.mTapDetector = null;
        } else {
            this.mTapDetector = new com.android.server.wm.TaskTapPointerEventListener(this.mWmService, this);
            registerPointerEventListener(this.mTapDetector);
        }
        if (this.mWmService.mMousePositionTracker != null) {
            registerPointerEventListener(this.mWmService.mMousePositionTracker);
        }
        if (this.mWmService.mAtmService.getRecentTasks() != null) {
            registerPointerEventListener(this.mWmService.mAtmService.getRecentTasks().getInputListener());
        }
        this.mDeviceStateController = deviceStateController;
        this.mDisplayPolicy = new com.android.server.wm.DisplayPolicy(this.mWmService, this);
        this.mDisplayRotation = new com.android.server.wm.DisplayRotation(this.mWmService, this, this.mDisplayInfo.address, this.mDeviceStateController, rootWindowContainer.getDisplayRotationCoordinator());
        this.mDeviceStateConsumer = new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda44
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.this.lambda$new$9((com.android.server.wm.DeviceStateController.DeviceState) obj);
            }
        };
        this.mDeviceStateController.registerDeviceStateCallback(this.mDeviceStateConsumer, new android.os.HandlerExecutor(this.mWmService.mH));
        this.mCloseToSquareMaxAspectRatio = this.mWmService.mContext.getResources().getFloat(android.R.dimen.config_alertDialogSelectionScrollOffset);
        if (this.isDefaultDisplay) {
            this.mWmService.mPolicy.setDefaultDisplay(this);
        }
        if (this.mWmService.mDisplayReady) {
            this.mDisplayPolicy.onConfigurationChanged();
        }
        if (this.mWmService.mSystemReady) {
            this.mDisplayPolicy.systemReady();
        }
        this.mWindowCornerRadius = this.mDisplayPolicy.getWindowCornerRadius();
        this.mPinnedTaskController = new com.android.server.wm.PinnedTaskController(this.mWmService, this);
        android.view.SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
        configureSurfaces(pendingTransaction);
        pendingTransaction.apply();
        onDisplayChanged(this);
        updateDisplayAreaOrganizers();
        this.mDisplayRotationCompatPolicy = this.mWmService.mLetterboxConfiguration.isCameraCompatTreatmentEnabledAtBuildTime() ? new com.android.server.wm.DisplayRotationCompatPolicy(this) : null;
        this.mRotationReversionController = new com.android.server.wm.DisplayRotationReversionController(this);
        this.mInputMonitor = new com.android.server.wm.InputMonitor(this.mWmService, this);
        this.mInsetsPolicy = new com.android.server.wm.InsetsPolicy(this.mInsetsStateController, this);
        this.mMinSizeOfResizeableTaskDp = getMinimalTaskSizeDp();
        setWindowingMode(1);
        this.mWmService.mDisplayWindowSettings.applySettingsToDisplayLocked(this);
        this.mInTouchMode = this.mWmService.mContext.getResources().getBoolean(android.R.bool.config_defaultInTouchMode);
        this.mWmService.mInputManager.setInTouchMode(this.mInTouchMode, com.android.server.wm.WindowManagerService.MY_PID, com.android.server.wm.WindowManagerService.MY_UID, true, this.mDisplayId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$9(com.android.server.wm.DeviceStateController.DeviceState deviceState) {
        this.mDisplaySwitchTransitionLauncher.foldStateChanged(deviceState);
        this.mDisplayRotation.foldStateChanged(deviceState);
    }

    private void beginHoldScreenUpdate() {
        this.mTmpHoldScreenWindow = null;
        this.mObscuringWindow = null;
    }

    private void finishHoldScreenUpdate() {
        boolean z = this.mTmpHoldScreenWindow != null;
        if (z && this.mTmpHoldScreenWindow != this.mHoldScreenWindow) {
            this.mHoldScreenWakeLock.setWorkSource(new android.os.WorkSource(this.mTmpHoldScreenWindow.mSession.mUid, this.mTmpHoldScreenWindow.mSession.mPackageName));
        }
        this.mHoldScreenWindow = this.mTmpHoldScreenWindow;
        this.mTmpHoldScreenWindow = null;
        if (z != this.mHoldScreenWakeLock.isHeld()) {
            if (z) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_KEEP_SCREEN_ON, 1959209522588955826L, 0, null, java.lang.String.valueOf(this.mHoldScreenWindow));
                this.mLastWakeLockHoldingWindow = this.mHoldScreenWindow;
                this.mLastWakeLockObscuringWindow = null;
                this.mHoldScreenWakeLock.acquire();
                return;
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_KEEP_SCREEN_ON, 352937214222086717L, 0, null, java.lang.String.valueOf(this.mObscuringWindow));
            this.mLastWakeLockHoldingWindow = null;
            this.mLastWakeLockObscuringWindow = this.mObscuringWindow;
            this.mHoldScreenWakeLock.release();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void migrateToNewSurfaceControl(android.view.SurfaceControl.Transaction transaction) {
        transaction.remove(this.mSurfaceControl);
        this.mLastSurfacePosition.set(0, 0);
        this.mLastDeltaRotation = 0;
        configureSurfaces(transaction);
        for (int i = 0; i < this.mChildren.size(); i++) {
            android.view.SurfaceControl surfaceControl = ((com.android.server.wm.DisplayArea) this.mChildren.get(i)).getSurfaceControl();
            if (surfaceControl != null) {
                transaction.reparent(surfaceControl, this.mSurfaceControl);
            }
        }
        scheduleAnimation();
    }

    private void configureSurfaces(android.view.SurfaceControl.Transaction transaction) {
        android.view.SurfaceControl.Builder callsite = this.mWmService.makeSurfaceBuilder(this.mSession).setOpaque(true).setContainerLayer().setCallsite("DisplayContent");
        this.mSurfaceControl = callsite.setName(getName()).setContainerLayer().build();
        if (this.mDisplayAreaPolicy == null) {
            this.mDisplayAreaPolicy = this.mWmService.getDisplayAreaPolicyProvider().instantiate(this.mWmService, this, this, this.mImeWindowsContainer);
        }
        java.util.List<com.android.server.wm.DisplayArea<? extends com.android.server.wm.WindowContainer>> displayAreas = this.mDisplayAreaPolicy.getDisplayAreas(4);
        com.android.server.wm.DisplayArea<? extends com.android.server.wm.WindowContainer> displayArea = displayAreas.size() == 1 ? displayAreas.get(0) : null;
        if (displayArea != null && displayArea.getParent() == this) {
            this.mWindowingLayer = displayArea.mSurfaceControl;
            transaction.reparent(this.mWindowingLayer, this.mSurfaceControl);
        } else {
            this.mWindowingLayer = this.mSurfaceControl;
            this.mSurfaceControl = callsite.setName("RootWrapper").build();
            transaction.reparent(this.mWindowingLayer, this.mSurfaceControl).show(this.mWindowingLayer);
        }
        if (this.mOverlayLayer == null) {
            this.mOverlayLayer = callsite.setName("Display Overlays").setParent(this.mSurfaceControl).build();
        } else {
            transaction.reparent(this.mOverlayLayer, this.mSurfaceControl);
        }
        if (this.mInputOverlayLayer == null) {
            this.mInputOverlayLayer = callsite.setName("Input Overlays").setParent(this.mSurfaceControl).build();
        } else {
            transaction.reparent(this.mInputOverlayLayer, this.mSurfaceControl);
        }
        if (this.mA11yOverlayLayer == null) {
            this.mA11yOverlayLayer = callsite.setName("Accessibility Overlays").setParent(this.mSurfaceControl).build();
        } else {
            transaction.reparent(this.mA11yOverlayLayer, this.mSurfaceControl);
        }
        transaction.setLayer(this.mSurfaceControl, 0).setLayerStack(this.mSurfaceControl, this.mDisplayId).show(this.mSurfaceControl).setLayer(this.mOverlayLayer, Integer.MAX_VALUE).show(this.mOverlayLayer).setLayer(this.mInputOverlayLayer, 2147483646).show(this.mInputOverlayLayer).setLayer(this.mA11yOverlayLayer, 2147483645).show(this.mA11yOverlayLayer);
    }

    com.android.server.wm.DisplayRotationReversionController getRotationReversionController() {
        return this.mRotationReversionController;
    }

    boolean isReady() {
        return this.mWmService.mDisplayReady && this.mDisplayReady;
    }

    boolean setInTouchMode(boolean z) {
        if (this.mInTouchMode == z) {
            return false;
        }
        this.mInTouchMode = z;
        return true;
    }

    boolean isInTouchMode() {
        return this.mInTouchMode;
    }

    int getDisplayId() {
        return this.mDisplayId;
    }

    float getWindowCornerRadius() {
        return this.mWindowCornerRadius;
    }

    com.android.server.wm.WindowToken getWindowToken(android.os.IBinder iBinder) {
        return this.mTokenMap.get(iBinder);
    }

    void addWindowToken(android.os.IBinder iBinder, com.android.server.wm.WindowToken windowToken) {
        com.android.server.wm.DisplayContent windowTokenDisplay = this.mWmService.mRoot.getWindowTokenDisplay(windowToken);
        if (windowTokenDisplay != null) {
            throw new java.lang.IllegalArgumentException("Can't map token=" + windowToken + " to display=" + getName() + " already mapped to display=" + windowTokenDisplay + " tokens=" + windowTokenDisplay.mTokenMap);
        }
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("Can't map token=" + windowToken + " to display=" + getName() + " binder is null");
        }
        if (windowToken == null) {
            throw new java.lang.IllegalArgumentException("Can't map null token to display=" + getName() + " binder=" + iBinder);
        }
        this.mTokenMap.put(iBinder, windowToken);
        if (windowToken.asActivityRecord() == null) {
            windowToken.mDisplayContent = this;
            findAreaForToken(windowToken).asTokens().addChild(windowToken);
        }
    }

    com.android.server.wm.WindowToken removeWindowToken(android.os.IBinder iBinder, boolean z) {
        com.android.server.wm.WindowToken remove = this.mTokenMap.remove(iBinder);
        if (remove != null && remove.asActivityRecord() == null) {
            remove.setExiting(z);
        }
        return remove;
    }

    android.view.SurfaceControl addShellRoot(@android.annotation.NonNull android.view.IWindow iWindow, int i) {
        com.android.server.wm.ShellRoot shellRoot = this.mShellRoots.get(i);
        if (shellRoot != null) {
            if (shellRoot.getClient() == iWindow) {
                return shellRoot.getSurfaceControl();
            }
            shellRoot.clear();
            this.mShellRoots.remove(i);
        }
        com.android.server.wm.ShellRoot shellRoot2 = new com.android.server.wm.ShellRoot(iWindow, this, i);
        android.view.SurfaceControl surfaceControl = shellRoot2.getSurfaceControl();
        if (surfaceControl == null) {
            shellRoot2.clear();
            return null;
        }
        this.mShellRoots.put(i, shellRoot2);
        return new android.view.SurfaceControl(surfaceControl, "DisplayContent.addShellRoot");
    }

    void removeShellRoot(int i) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                com.android.server.wm.ShellRoot shellRoot = this.mShellRoots.get(i);
                if (shellRoot == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                shellRoot.clear();
                this.mShellRoots.remove(i);
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    void setRemoteInsetsController(android.view.IDisplayWindowInsetsController iDisplayWindowInsetsController) {
        if (this.mRemoteInsetsControlTarget != null) {
            this.mRemoteInsetsControlTarget.mRemoteInsetsController.asBinder().unlinkToDeath(this.mRemoteInsetsDeath, 0);
            this.mRemoteInsetsControlTarget = null;
        }
        if (iDisplayWindowInsetsController != null) {
            try {
                iDisplayWindowInsetsController.asBinder().linkToDeath(this.mRemoteInsetsDeath, 0);
                this.mRemoteInsetsControlTarget = new com.android.server.wm.DisplayContent.RemoteInsetsControlTarget(iDisplayWindowInsetsController);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void reParentWindowToken(com.android.server.wm.WindowToken windowToken) {
        com.android.server.wm.DisplayContent displayContent = windowToken.getDisplayContent();
        if (displayContent == this) {
            return;
        }
        if (displayContent != null && displayContent.mTokenMap.remove(windowToken.token) != null && windowToken.asActivityRecord() == null) {
            windowToken.getParent().removeChild(windowToken);
        }
        addWindowToken(windowToken.token, windowToken);
        if (this.mWmService.mAccessibilityController.hasCallbacks()) {
            this.mWmService.mAccessibilityController.onSomeWindowResizedOrMoved(displayContent != null ? displayContent.getDisplayId() : -1, getDisplayId());
        }
    }

    void removeAppToken(android.os.IBinder iBinder) {
        com.android.server.wm.WindowToken removeWindowToken = removeWindowToken(iBinder, true);
        if (removeWindowToken == null) {
            android.util.Slog.w(TAG, "removeAppToken: Attempted to remove non-existing token: " + iBinder);
            return;
        }
        com.android.server.wm.ActivityRecord asActivityRecord = removeWindowToken.asActivityRecord();
        if (asActivityRecord == null) {
            android.util.Slog.w(TAG, "Attempted to remove non-App token: " + iBinder + " token=" + removeWindowToken);
            return;
        }
        asActivityRecord.onRemovedFromDisplay();
        if (asActivityRecord == this.mFixedRotationLaunchingApp) {
            asActivityRecord.finishFixedRotationTransform();
            setFixedRotationLaunchingAppUnchecked(null);
        }
    }

    @Override // com.android.server.policy.WindowManagerPolicy.DisplayContentInfo
    public android.view.Display getDisplay() {
        return this.mDisplay;
    }

    android.view.DisplayInfo getDisplayInfo() {
        return this.mDisplayInfo;
    }

    android.util.DisplayMetrics getDisplayMetrics() {
        return this.mDisplayMetrics;
    }

    com.android.server.wm.DisplayPolicy getDisplayPolicy() {
        return this.mDisplayPolicy;
    }

    @Override // com.android.server.policy.WindowManagerPolicy.DisplayContentInfo
    public com.android.server.wm.DisplayRotation getDisplayRotation() {
        return this.mDisplayRotation;
    }

    com.android.server.wm.InsetsStateController getInsetsStateController() {
        return this.mInsetsStateController;
    }

    com.android.server.wm.InsetsPolicy getInsetsPolicy() {
        return this.mInsetsPolicy;
    }

    int getRotation() {
        return this.mDisplayRotation.getRotation();
    }

    int getLastOrientation() {
        return this.mDisplayRotation.getLastOrientation();
    }

    void registerRemoteAnimations(android.view.RemoteAnimationDefinition remoteAnimationDefinition) {
        this.mAppTransitionController.registerRemoteAnimations(remoteAnimationDefinition);
    }

    void reconfigureDisplayLocked() {
        if (!isReady()) {
            return;
        }
        configureDisplayPolicy();
        setLayoutNeeded();
        boolean updateOrientation = updateOrientation();
        android.content.res.Configuration configuration = getConfiguration();
        this.mTmpConfiguration.setTo(configuration);
        computeScreenConfiguration(this.mTmpConfiguration);
        int diff = configuration.diff(this.mTmpConfiguration);
        if (updateOrientation | (diff != 0)) {
            this.mWaitingForConfig = true;
            if (this.mTransitionController.isShellTransitionsEnabled()) {
                android.graphics.Rect bounds = configuration.windowConfiguration.getBounds();
                android.graphics.Rect bounds2 = this.mTmpConfiguration.windowConfiguration.getBounds();
                com.android.server.wm.Transition collectingTransition = this.mTransitionController.getCollectingTransition();
                android.window.TransitionRequestInfo.DisplayChange displayChange = collectingTransition != null ? null : new android.window.TransitionRequestInfo.DisplayChange(this.mDisplayId);
                if (displayChange != null) {
                    displayChange.setStartAbsBounds(bounds);
                    displayChange.setEndAbsBounds(bounds2);
                } else {
                    collectingTransition.setKnownConfigChanges(this, diff);
                    this.mTransitionController.setDisplaySyncMethod(bounds, bounds2, this);
                }
                requestChangeTransitionIfNeeded(diff, displayChange);
            } else if (this.mLastHasContent) {
                this.mWmService.startFreezingDisplay(0, 0, this);
            }
            sendNewConfiguration();
        }
        this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
    }

    boolean sendNewConfiguration() {
        if (!isReady() || this.mRemoteDisplayChangeController.isWaitingForRemoteDisplayChange()) {
            return false;
        }
        com.android.server.wm.Transition.ReadyCondition readyCondition = this.mTransitionController.isCollecting() ? new com.android.server.wm.Transition.ReadyCondition("displayConfig", this) : null;
        if (readyCondition != null) {
            this.mTransitionController.waitFor(readyCondition);
        } else if (this.mTransitionController.isShellTransitionsEnabled() && this.mLastHasContent) {
            android.util.Slog.e(TAG, "Display reconfigured outside of a transition: " + this);
        }
        boolean updateDisplayOverrideConfigurationLocked = updateDisplayOverrideConfigurationLocked();
        if (readyCondition != null) {
            readyCondition.meet();
        }
        if (updateDisplayOverrideConfigurationLocked) {
            return true;
        }
        clearFixedRotationLaunchingApp();
        if (this.mWaitingForConfig) {
            this.mWaitingForConfig = false;
            this.mWmService.mLastFinishedFreezeSource = "config-unchanged";
            setLayoutNeeded();
            this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
        }
        return false;
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    boolean onDescendantOrientationChanged(@android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer) {
        int i;
        android.content.res.Configuration updateOrientation = updateOrientation(windowContainer, false);
        if (windowContainer != null) {
            i = windowContainer.getOverrideOrientation();
        } else {
            i = -2;
        }
        boolean handlesOrientationChangeFromDescendant = handlesOrientationChangeFromDescendant(i);
        if (updateOrientation == null) {
            return handlesOrientationChangeFromDescendant;
        }
        if (!handlesOrientationChangeFromDescendant || !(windowContainer instanceof com.android.server.wm.ActivityRecord)) {
            updateDisplayOverrideConfigurationLocked(updateOrientation, null, false);
        } else if (!updateDisplayOverrideConfigurationLocked(updateOrientation, (com.android.server.wm.ActivityRecord) windowContainer, false)) {
            this.mRootWindowContainer.resumeFocusedTasksTopActivities();
        }
        return handlesOrientationChangeFromDescendant;
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    boolean handlesOrientationChangeFromDescendant(int i) {
        return (shouldIgnoreOrientationRequest(i) || getDisplayRotation().isFixedToUserRotation()) ? false : true;
    }

    boolean updateOrientation() {
        return updateOrientation(false);
    }

    android.content.res.Configuration updateOrientation(com.android.server.wm.WindowContainer<?> windowContainer, boolean z) {
        com.android.server.wm.ActivityRecord asActivityRecord;
        if (!this.mDisplayReady || !updateOrientation(z)) {
            return null;
        }
        if (windowContainer != null && !this.mWmService.mRoot.mOrientationChangeComplete && (asActivityRecord = windowContainer.asActivityRecord()) != null && asActivityRecord.mayFreezeScreenLocked()) {
            asActivityRecord.startFreezingScreen();
        }
        android.content.res.Configuration configuration = new android.content.res.Configuration();
        computeScreenConfiguration(configuration);
        return configuration;
    }

    private int getMinimalTaskSizeDp() {
        android.content.res.Resources resources = getDisplayUiContext().getResources();
        android.util.TypedValue typedValue = new android.util.TypedValue();
        resources.getValue(android.R.dimen.default_app_widget_padding_right, typedValue, true);
        int i = (typedValue.data >> 0) & 15;
        if (typedValue.type != 5 || i != 1) {
            throw new java.lang.IllegalArgumentException("Resource ID #0x" + java.lang.Integer.toHexString(android.R.dimen.default_app_widget_padding_right) + " is not in valid type or unit");
        }
        return (int) android.util.TypedValue.complexToFloat(typedValue.data);
    }

    private boolean updateOrientation(boolean z) {
        com.android.server.wm.WindowContainer windowContainer = this.mLastOrientationSource;
        int orientation = getOrientation();
        com.android.server.wm.WindowContainer lastOrientationSource = getLastOrientationSource();
        if (lastOrientationSource != windowContainer && this.mRotationReversionController.isRotationReversionEnabled()) {
            this.mRotationReversionController.updateForNoSensorOverride();
        }
        com.android.server.wm.ActivityRecord asActivityRecord = lastOrientationSource != null ? lastOrientationSource.asActivityRecord() : null;
        if (asActivityRecord != null) {
            com.android.server.wm.Task task = asActivityRecord.getTask();
            if (task != null && orientation != task.mLastReportedRequestedOrientation) {
                task.mLastReportedRequestedOrientation = orientation;
                this.mAtmService.getTaskChangeNotificationController().notifyTaskRequestedOrientationChanged(task.mTaskId, orientation);
            }
            com.android.server.wm.ActivityRecord activityRecord = !asActivityRecord.isVisibleRequested() ? topRunningActivity() : asActivityRecord;
            if (activityRecord != null && handleTopActivityLaunchingInDifferentOrientation(activityRecord, asActivityRecord, true)) {
                return false;
            }
        }
        return this.mDisplayRotation.updateOrientation(orientation, z);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isSyncFinished(com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup) {
        return !this.mRemoteDisplayChangeController.isWaitingForRemoteDisplayChange();
    }

    int rotationForActivityInDifferentOrientation(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
        int rotation;
        int rotationForOrientation;
        com.android.server.wm.ActivityRecord activity;
        if (this.mTransitionController.useShellTransitionsRotation()) {
            return -1;
        }
        int overrideOrientation = activityRecord.getOverrideOrientation();
        if (!com.android.server.wm.WindowManagerService.ENABLE_FIXED_ROTATION_TRANSFORM || shouldIgnoreOrientationRequest(overrideOrientation)) {
            return -1;
        }
        if (overrideOrientation == 3 && (activity = getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean canDefineOrientationForActivitiesAbove;
                canDefineOrientationForActivitiesAbove = ((com.android.server.wm.ActivityRecord) obj).canDefineOrientationForActivitiesAbove();
                return canDefineOrientationForActivitiesAbove;
            }
        }, activityRecord, false, true)) != null) {
            activityRecord = activity;
        }
        if (activityRecord.inMultiWindowMode() || activityRecord.getRequestedConfigurationOrientation(true) == getConfiguration().orientation || (rotationForOrientation = this.mDisplayRotation.rotationForOrientation(activityRecord.getRequestedOrientation(), (rotation = getRotation()))) == rotation) {
            return -1;
        }
        return rotationForOrientation;
    }

    boolean handleTopActivityLaunchingInDifferentOrientation(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        return handleTopActivityLaunchingInDifferentOrientation(activityRecord, activityRecord, z);
    }

    private boolean handleTopActivityLaunchingInDifferentOrientation(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, @android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord2, boolean z) {
        int rotationForActivityInDifferentOrientation;
        if (!com.android.server.wm.WindowManagerService.ENABLE_FIXED_ROTATION_TRANSFORM || activityRecord.isFinishingFixedRotationTransform()) {
            return false;
        }
        if (activityRecord.hasFixedRotationTransform()) {
            return true;
        }
        if (!activityRecord.occludesParent() || activityRecord.isReportedDrawn()) {
            return false;
        }
        if (z) {
            if (this.mTransitionController.isShellTransitionsEnabled()) {
                if (!this.mTransitionController.isCollecting(activityRecord)) {
                    return false;
                }
            } else if (!this.mAppTransition.isTransitionSet() || !this.mOpeningApps.contains(activityRecord)) {
                return false;
            }
            if (activityRecord.isState(com.android.server.wm.ActivityRecord.State.RESUMED) && !activityRecord.getTask().mInResumeTopActivity) {
                return false;
            }
        } else if (activityRecord != topRunningActivity()) {
            return false;
        }
        if ((this.mLastWallpaperVisible && activityRecord.windowsCanBeWallpaperTarget() && this.mFixedRotationTransitionListener.mAnimatingRecents == null && !this.mTransitionController.isTransientLaunch(activityRecord)) || (rotationForActivityInDifferentOrientation = rotationForActivityInDifferentOrientation(activityRecord2)) == -1 || !activityRecord.getDisplayArea().matchParentBounds()) {
            return false;
        }
        setFixedRotationLaunchingApp(activityRecord, rotationForActivityInDifferentOrientation);
        return true;
    }

    boolean hasTopFixedRotationLaunchingApp() {
        return (this.mFixedRotationLaunchingApp == null || this.mFixedRotationLaunchingApp == this.mFixedRotationTransitionListener.mAnimatingRecents) ? false : true;
    }

    boolean isFixedRotationLaunchingApp(com.android.server.wm.ActivityRecord activityRecord) {
        return this.mFixedRotationLaunchingApp == activityRecord;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    com.android.server.wm.AsyncRotationController getAsyncRotationController() {
        return this.mAsyncRotationController;
    }

    void setFixedRotationLaunchingAppUnchecked(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord) {
        setFixedRotationLaunchingAppUnchecked(activityRecord, -1);
    }

    void setFixedRotationLaunchingAppUnchecked(@android.annotation.Nullable com.android.server.wm.ActivityRecord activityRecord, int i) {
        if (this.mFixedRotationLaunchingApp == null && activityRecord != null) {
            this.mWmService.mDisplayNotificationController.dispatchFixedRotationStarted(this, i);
            startAsyncRotation(activityRecord == this.mFixedRotationTransitionListener.mAnimatingRecents || this.mTransitionController.isTransientLaunch(activityRecord));
        } else if (this.mFixedRotationLaunchingApp != null && activityRecord == null) {
            this.mWmService.mDisplayNotificationController.dispatchFixedRotationFinished(this);
            if (!this.mTransitionController.hasCollectingRotationChange(this, getRotation())) {
                finishAsyncRotationIfPossible();
            }
        }
        this.mFixedRotationLaunchingApp = activityRecord;
    }

    void setFixedRotationLaunchingApp(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord, int i) {
        com.android.server.wm.ActivityRecord activityRecord2 = this.mFixedRotationLaunchingApp;
        if (activityRecord2 == activityRecord && activityRecord.getWindowConfiguration().getRotation() == i) {
            return;
        }
        if (activityRecord2 != null && activityRecord2.getWindowConfiguration().getRotation() == i && activityRecord2.isInTransition()) {
            activityRecord.linkFixedRotationTransform(activityRecord2);
            if (activityRecord != this.mFixedRotationTransitionListener.mAnimatingRecents) {
                setFixedRotationLaunchingAppUnchecked(activityRecord, i);
                return;
            }
            return;
        }
        if (!activityRecord.hasFixedRotationTransform()) {
            startFixedRotationTransform(activityRecord, i);
        }
        setFixedRotationLaunchingAppUnchecked(activityRecord, i);
        if (activityRecord2 != null) {
            activityRecord2.finishFixedRotationTransform();
        }
    }

    void continueUpdateOrientationForDiffOrienLaunchingApp() {
        if (this.mFixedRotationLaunchingApp == null || this.mPinnedTaskController.shouldDeferOrientationChange()) {
            return;
        }
        if (this.mDisplayRotation.updateOrientation(getOrientation(), false)) {
            sendNewConfiguration();
        } else {
            if (this.mRemoteDisplayChangeController.isWaitingForRemoteDisplayChange()) {
                return;
            }
            clearFixedRotationLaunchingApp();
        }
    }

    private void clearFixedRotationLaunchingApp() {
        if (this.mFixedRotationLaunchingApp == null) {
            return;
        }
        this.mFixedRotationLaunchingApp.finishFixedRotationTransform();
        setFixedRotationLaunchingAppUnchecked(null);
    }

    private void startFixedRotationTransform(com.android.server.wm.WindowToken windowToken, int i) {
        this.mTmpConfiguration.unset();
        android.view.DisplayInfo computeScreenConfiguration = computeScreenConfiguration(this.mTmpConfiguration, i);
        windowToken.applyFixedRotationTransform(computeScreenConfiguration, new com.android.server.wm.DisplayFrames(new android.view.InsetsState(), computeScreenConfiguration, calculateDisplayCutoutForRotation(i), calculateRoundedCornersForRotation(i), calculatePrivacyIndicatorBoundsForRotation(i), calculateDisplayShapeForRotation(i)), this.mTmpConfiguration);
    }

    void rotateInDifferentOrientationIfNeeded(com.android.server.wm.ActivityRecord activityRecord) {
        int rotationForActivityInDifferentOrientation = rotationForActivityInDifferentOrientation(activityRecord);
        if (rotationForActivityInDifferentOrientation != -1) {
            startFixedRotationTransform(activityRecord, rotationForActivityInDifferentOrientation);
        }
    }

    boolean isRotationChanging() {
        return this.mDisplayRotation.getRotation() != getWindowConfiguration().getRotation();
    }

    private void startAsyncRotationIfNeeded() {
        if (isRotationChanging()) {
            startAsyncRotation(false);
        }
    }

    private boolean startAsyncRotation(boolean z) {
        if (z) {
            this.mWmService.mH.postDelayed(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.DisplayContent.this.lambda$startAsyncRotation$11();
                }
            }, FIXED_ROTATION_HIDE_ANIMATION_DEBOUNCE_DELAY_MS);
            return false;
        }
        if (this.mAsyncRotationController != null) {
            return false;
        }
        this.mAsyncRotationController = new com.android.server.wm.AsyncRotationController(this);
        this.mAsyncRotationController.start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAsyncRotation$11() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mFixedRotationLaunchingApp != null && startAsyncRotation(false)) {
                    getPendingTransaction().apply();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void finishAsyncRotationIfPossible() {
        com.android.server.wm.AsyncRotationController asyncRotationController = this.mAsyncRotationController;
        if (asyncRotationController != null && !this.mDisplayRotation.hasSeamlessRotatingWindow()) {
            asyncRotationController.completeAll();
            this.mAsyncRotationController = null;
        }
    }

    void finishAsyncRotation(com.android.server.wm.WindowToken windowToken) {
        com.android.server.wm.AsyncRotationController asyncRotationController = this.mAsyncRotationController;
        if (asyncRotationController != null && asyncRotationController.completeRotation(windowToken)) {
            this.mAsyncRotationController = null;
        }
    }

    boolean shouldSyncRotationChange(com.android.server.wm.WindowState windowState) {
        com.android.server.wm.AsyncRotationController asyncRotationController = this.mAsyncRotationController;
        return asyncRotationController == null || !asyncRotationController.isAsync(windowState);
    }

    void notifyInsetsChanged(java.util.function.Consumer<com.android.server.wm.WindowState> consumer) {
        android.view.InsetsState fixedRotationTransformInsetsState;
        if (this.mFixedRotationLaunchingApp != null && (fixedRotationTransformInsetsState = this.mFixedRotationLaunchingApp.getFixedRotationTransformInsetsState()) != null) {
            android.view.InsetsState.traverse(fixedRotationTransformInsetsState, this.mInsetsStateController.getRawInsetsState(), COPY_SOURCE_VISIBILITY);
        }
        forAllWindows(consumer, true);
        if (this.mRemoteInsetsControlTarget != null) {
            this.mRemoteInsetsControlTarget.notifyInsetsChanged();
        }
        if (this.mWmService.mAccessibilityController.hasCallbacks()) {
            this.mWmService.mAccessibilityController.updateImeVisibilityIfNeeded(this.mDisplayId, this.mImeControlTarget != null && this.mImeControlTarget.isRequestedVisible(android.view.WindowInsets.Type.ime()));
        }
    }

    boolean updateRotationUnchecked() {
        return this.mDisplayRotation.updateRotationUnchecked(false);
    }

    boolean canShowTasksInHostDeviceRecents() {
        if (this.mDwpcHelper == null) {
            return true;
        }
        return this.mDwpcHelper.canShowTasksInHostDeviceRecents();
    }

    @android.annotation.Nullable
    android.content.ComponentName getCustomHomeComponent() {
        if (!isHomeSupported() || this.mDwpcHelper == null) {
            return null;
        }
        return this.mDwpcHelper.getCustomHomeComponent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: applyRotation, reason: merged with bridge method [inline-methods] */
    public void lambda$applyRotationAndFinishFixedRotation$41(final int i, final int i2) {
        this.mDisplayRotation.applyCurrentRotation(i2);
        final boolean z = false;
        boolean z2 = this.mTransitionController.getTransitionPlayer() != null;
        if (this.mDisplayRotation.isRotatingSeamlessly() && !z2) {
            z = true;
        }
        final android.view.SurfaceControl.Transaction syncTransaction = z2 ? getSyncTransaction() : getPendingTransaction();
        com.android.server.wm.ScreenRotationAnimation rotationAnimation = z ? null : getRotationAnimation();
        updateDisplayAndOrientation(null);
        if (rotationAnimation != null && rotationAnimation.hasScreenshot()) {
            rotationAnimation.setRotation(syncTransaction, i2);
        }
        if (!z2) {
            forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.DisplayContent.lambda$applyRotation$12(syncTransaction, i, i2, z, (com.android.server.wm.WindowState) obj);
                }
            }, true);
            this.mPinnedTaskController.startSeamlessRotationIfNeeded(syncTransaction, i, i2);
            if (!this.mDisplayRotation.hasSeamlessRotatingWindow()) {
                this.mDisplayRotation.cancelSeamlessRotation();
            }
        }
        if (z2) {
            getPendingTransaction().setFixedTransformHint(this.mSurfaceControl, i2);
            syncTransaction.unsetFixedTransformHint(this.mSurfaceControl);
        }
        scheduleAnimation();
        this.mWmService.mRotationWatcherController.dispatchDisplayRotationChange(this.mDisplayId, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$applyRotation$12(android.view.SurfaceControl.Transaction transaction, int i, int i2, boolean z, com.android.server.wm.WindowState windowState) {
        windowState.seamlesslyRotateIfAllowed(transaction, i, i2, z);
        if (!z && windowState.mHasSurface) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 2632363530212357762L, 0, null, java.lang.String.valueOf(windowState));
            windowState.setOrientationChanging(true);
        }
    }

    void configureDisplayPolicy() {
        this.mRootWindowContainer.updateDisplayImePolicyCache();
        this.mDisplayPolicy.updateConfigurationAndScreenSizeDependentBehaviors();
        this.mDisplayRotation.configure(this.mBaseDisplayWidth, this.mBaseDisplayHeight);
    }

    private android.view.DisplayInfo updateDisplayAndOrientation(android.content.res.Configuration configuration) {
        int rotation = getRotation();
        boolean z = true;
        if (rotation != 1 && rotation != 3) {
            z = false;
        }
        boolean z2 = z;
        int i = z2 ? this.mBaseDisplayHeight : this.mBaseDisplayWidth;
        int i2 = z2 ? this.mBaseDisplayWidth : this.mBaseDisplayHeight;
        android.view.DisplayCutout calculateDisplayCutoutForRotation = calculateDisplayCutoutForRotation(rotation);
        android.view.RoundedCorners calculateRoundedCornersForRotation = calculateRoundedCornersForRotation(rotation);
        android.view.DisplayShape calculateDisplayShapeForRotation = calculateDisplayShapeForRotation(rotation);
        android.graphics.Rect rect = this.mDisplayPolicy.getDecorInsetsInfo(rotation, i, i2).mNonDecorFrame;
        this.mDisplayInfo.rotation = rotation;
        this.mDisplayInfo.logicalWidth = i;
        this.mDisplayInfo.logicalHeight = i2;
        this.mDisplayInfo.logicalDensityDpi = this.mBaseDisplayDensity;
        this.mDisplayInfo.physicalXDpi = this.mBaseDisplayPhysicalXDpi;
        this.mDisplayInfo.physicalYDpi = this.mBaseDisplayPhysicalYDpi;
        this.mDisplayInfo.appWidth = rect.width();
        this.mDisplayInfo.appHeight = rect.height();
        if (this.isDefaultDisplay) {
            this.mDisplayInfo.getLogicalMetrics(this.mRealDisplayMetrics, android.content.res.CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO, (android.content.res.Configuration) null);
        }
        android.view.DisplayInfo displayInfo = this.mDisplayInfo;
        if (calculateDisplayCutoutForRotation.isEmpty()) {
            calculateDisplayCutoutForRotation = null;
        }
        displayInfo.displayCutout = calculateDisplayCutoutForRotation;
        this.mDisplayInfo.roundedCorners = calculateRoundedCornersForRotation;
        this.mDisplayInfo.displayShape = calculateDisplayShapeForRotation;
        this.mDisplayInfo.getAppMetrics(this.mDisplayMetrics);
        if (this.mDisplayScalingDisabled) {
            this.mDisplayInfo.flags |= 1073741824;
        } else {
            this.mDisplayInfo.flags &= -1073741825;
        }
        computeSizeRanges(this.mDisplayInfo, z2, i, i2, this.mDisplayMetrics.density, configuration);
        setDisplayInfoOverride();
        if (this.isDefaultDisplay) {
            this.mCompatibleScreenScale = android.content.res.CompatibilityInfo.computeCompatibleScaling(this.mDisplayMetrics, this.mCompatDisplayMetrics);
        }
        onDisplayInfoChanged();
        return this.mDisplayInfo;
    }

    private void setDisplayInfoOverride() {
        this.mWmService.mDisplayManagerInternal.setDisplayInfoOverrideFromWindowManager(this.mDisplayId, this.mDisplayInfo);
        if (this.mLastDisplayInfoOverride == null) {
            this.mLastDisplayInfoOverride = new android.view.DisplayInfo();
        }
        this.mLastDisplayInfoOverride.copyFrom(this.mDisplayInfo);
    }

    android.view.DisplayCutout calculateDisplayCutoutForRotation(int i) {
        return this.mDisplayCutoutCache.getOrCompute(this.mIsSizeForced ? this.mBaseDisplayCutout : this.mInitialDisplayCutout, i).getDisplayCutout();
    }

    static com.android.server.wm.utils.WmDisplayCutout calculateDisplayCutoutForRotationAndDisplaySizeUncached(android.view.DisplayCutout displayCutout, int i, int i2, int i3) {
        if (displayCutout == null || displayCutout == android.view.DisplayCutout.NO_CUTOUT) {
            return com.android.server.wm.utils.WmDisplayCutout.NO_CUTOUT;
        }
        if (i2 == i3) {
            android.util.Slog.w(TAG, "Ignore cutout because display size is square: " + i2);
            return com.android.server.wm.utils.WmDisplayCutout.NO_CUTOUT;
        }
        if (i == 0) {
            return com.android.server.wm.utils.WmDisplayCutout.computeSafeInsets(displayCutout, i2, i3);
        }
        boolean z = false;
        android.view.DisplayCutout rotated = displayCutout.getRotated(i2, i3, 0, i);
        if (i == 1 || i == 3) {
            z = true;
        }
        int i4 = z ? i3 : i2;
        if (!z) {
            i2 = i3;
        }
        return new com.android.server.wm.utils.WmDisplayCutout(rotated, new android.util.Size(i4, i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.server.wm.utils.WmDisplayCutout calculateDisplayCutoutForRotationUncached(android.view.DisplayCutout displayCutout, int i) {
        return calculateDisplayCutoutForRotationAndDisplaySizeUncached(displayCutout, i, this.mIsSizeForced ? this.mBaseDisplayWidth : this.mInitialDisplayWidth, this.mIsSizeForced ? this.mBaseDisplayHeight : this.mInitialDisplayHeight);
    }

    android.view.RoundedCorners calculateRoundedCornersForRotation(int i) {
        return this.mRoundedCornerCache.getOrCompute(this.mIsSizeForced ? this.mBaseRoundedCorners : this.mInitialRoundedCorners, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.RoundedCorners calculateRoundedCornersForRotationUncached(android.view.RoundedCorners roundedCorners, int i) {
        if (roundedCorners == null || roundedCorners == android.view.RoundedCorners.NO_ROUNDED_CORNERS) {
            return android.view.RoundedCorners.NO_ROUNDED_CORNERS;
        }
        if (i == 0) {
            return roundedCorners;
        }
        return roundedCorners.rotate(i, this.mIsSizeForced ? this.mBaseDisplayWidth : this.mInitialDisplayWidth, this.mIsSizeForced ? this.mBaseDisplayHeight : this.mInitialDisplayHeight);
    }

    android.view.PrivacyIndicatorBounds calculatePrivacyIndicatorBoundsForRotation(int i) {
        return this.mPrivacyIndicatorBoundsCache.getOrCompute(this.mCurrentPrivacyIndicatorBounds, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.PrivacyIndicatorBounds calculatePrivacyIndicatorBoundsForRotationUncached(android.view.PrivacyIndicatorBounds privacyIndicatorBounds, int i) {
        if (privacyIndicatorBounds == null) {
            return new android.view.PrivacyIndicatorBounds(new android.graphics.Rect[4], i);
        }
        return privacyIndicatorBounds.rotate(i);
    }

    android.view.DisplayShape calculateDisplayShapeForRotation(int i) {
        return this.mDisplayShapeCache.getOrCompute(this.mInitialDisplayShape, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.view.DisplayShape calculateDisplayShapeForRotationUncached(android.view.DisplayShape displayShape, int i) {
        if (displayShape == null) {
            return android.view.DisplayShape.NONE;
        }
        if (i == 0) {
            return displayShape;
        }
        return displayShape.setRotation(i);
    }

    android.view.DisplayInfo computeScreenConfiguration(android.content.res.Configuration configuration, int i) {
        boolean z = i == 1 || i == 3;
        int i2 = z ? this.mBaseDisplayHeight : this.mBaseDisplayWidth;
        int i3 = z ? this.mBaseDisplayWidth : this.mBaseDisplayHeight;
        configuration.windowConfiguration.setMaxBounds(0, 0, i2, i3);
        configuration.windowConfiguration.setBounds(configuration.windowConfiguration.getMaxBounds());
        computeScreenAppConfiguration(configuration, i2, i3, i);
        android.view.DisplayInfo displayInfo = new android.view.DisplayInfo(this.mDisplayInfo);
        displayInfo.rotation = i;
        displayInfo.logicalWidth = i2;
        displayInfo.logicalHeight = i3;
        android.graphics.Rect appBounds = configuration.windowConfiguration.getAppBounds();
        displayInfo.appWidth = appBounds.width();
        displayInfo.appHeight = appBounds.height();
        android.view.DisplayCutout calculateDisplayCutoutForRotation = calculateDisplayCutoutForRotation(i);
        if (calculateDisplayCutoutForRotation.isEmpty()) {
            calculateDisplayCutoutForRotation = null;
        }
        displayInfo.displayCutout = calculateDisplayCutoutForRotation;
        computeSizeRanges(displayInfo, z, i2, i3, this.mDisplayMetrics.density, configuration);
        return displayInfo;
    }

    private void computeScreenAppConfiguration(android.content.res.Configuration configuration, int i, int i2, int i3) {
        configuration.windowConfiguration.setAppBounds(this.mDisplayPolicy.getDecorInsetsInfo(i3, i, i2).mNonDecorFrame);
        configuration.windowConfiguration.setRotation(i3);
        float f = this.mDisplayMetrics.density;
        configuration.screenWidthDp = (int) ((r0.mConfigFrame.width() / f) + 0.5f);
        configuration.screenHeightDp = (int) ((r0.mConfigFrame.height() / f) + 0.5f);
        configuration.compatScreenWidthDp = (int) (configuration.screenWidthDp / this.mCompatibleScreenScale);
        configuration.compatScreenHeightDp = (int) (configuration.screenHeightDp / this.mCompatibleScreenScale);
        boolean z = true;
        configuration.orientation = configuration.screenWidthDp <= configuration.screenHeightDp ? 1 : 2;
        configuration.screenLayout = com.android.server.wm.WindowContainer.computeScreenLayout(android.content.res.Configuration.resetScreenLayout(configuration.screenLayout), configuration.screenWidthDp, configuration.screenHeightDp);
        if (i3 != 1 && i3 != 3) {
            z = false;
        }
        configuration.compatSmallestScreenWidthDp = computeCompatSmallestWidth(z, i, i2);
        configuration.windowConfiguration.setDisplayRotation(i3);
    }

    void computeScreenConfiguration(android.content.res.Configuration configuration) {
        int i;
        int i2;
        int i3;
        android.view.DisplayInfo updateDisplayAndOrientation = updateDisplayAndOrientation(configuration);
        int i4 = updateDisplayAndOrientation.logicalWidth;
        int i5 = updateDisplayAndOrientation.logicalHeight;
        this.mTmpRect.set(0, 0, i4, i5);
        configuration.windowConfiguration.setBounds(this.mTmpRect);
        configuration.windowConfiguration.setMaxBounds(this.mTmpRect);
        configuration.windowConfiguration.setWindowingMode(getWindowingMode());
        configuration.windowConfiguration.setDisplayWindowingMode(getWindowingMode());
        computeScreenAppConfiguration(configuration, i4, i5, updateDisplayAndOrientation.rotation);
        int i6 = configuration.screenLayout & (-769);
        if ((updateDisplayAndOrientation.flags & 16) != 0) {
            i = 512;
        } else {
            i = 256;
        }
        configuration.screenLayout = i6 | i;
        configuration.densityDpi = updateDisplayAndOrientation.logicalDensityDpi;
        if (updateDisplayAndOrientation.isHdr() && this.mWmService.hasHdrSupport()) {
            i2 = 8;
        } else {
            i2 = 4;
        }
        if (updateDisplayAndOrientation.isWideColorGamut() && this.mWmService.hasWideColorGamutSupport()) {
            i3 = 2;
        } else {
            i3 = 1;
        }
        configuration.colorMode = i3 | i2;
        configuration.touchscreen = 1;
        configuration.keyboard = 1;
        configuration.navigation = 1;
        android.view.InputDevice[] inputDevices = this.mWmService.mInputManager.getInputDevices();
        int length = inputDevices != null ? inputDevices.length : 0;
        int i7 = 0;
        int i8 = 0;
        for (int i9 = 0; i9 < length; i9++) {
            android.view.InputDevice inputDevice = inputDevices[i9];
            if (!inputDevice.isVirtual() && this.mWmService.mInputManager.canDispatchToDisplay(inputDevice.getId(), this.mDisplayId)) {
                int sources = inputDevice.getSources();
                int i10 = inputDevice.isExternal() ? 2 : 1;
                if (this.mWmService.mIsTouchDevice) {
                    if ((sources & com.android.server.usb.descriptors.UsbACInterface.FORMAT_II_AC3) == 4098) {
                        configuration.touchscreen = 3;
                    }
                } else {
                    configuration.touchscreen = 1;
                }
                if ((sources & 65540) == 65540) {
                    configuration.navigation = 3;
                    i7 |= i10;
                } else if ((sources & 513) == 513 && configuration.navigation == 1) {
                    configuration.navigation = 2;
                    i7 |= i10;
                }
                if (inputDevice.getKeyboardType() == 2) {
                    configuration.keyboard = 2;
                    i8 |= i10;
                }
            }
        }
        if (configuration.navigation == 1 && this.mWmService.mHasPermanentDpad) {
            configuration.navigation = 2;
            i7 |= 1;
        }
        boolean z = configuration.keyboard != 1;
        if (z != this.mWmService.mHardKeyboardAvailable) {
            this.mWmService.mHardKeyboardAvailable = z;
            this.mWmService.mH.removeMessages(22);
            this.mWmService.mH.sendEmptyMessage(22);
        }
        this.mDisplayPolicy.updateConfigurationAndScreenSizeDependentBehaviors();
        configuration.keyboardHidden = 1;
        configuration.hardKeyboardHidden = 1;
        configuration.navigationHidden = 1;
        this.mWmService.mPolicy.adjustConfigurationLw(configuration, i8, i7);
    }

    private int computeCompatSmallestWidth(boolean z, int i, int i2) {
        this.mTmpDisplayMetrics.setTo(this.mDisplayMetrics);
        android.util.DisplayMetrics displayMetrics = this.mTmpDisplayMetrics;
        if (!z) {
            i2 = i;
            i = i2;
        }
        return reduceCompatConfigWidthSize(reduceCompatConfigWidthSize(reduceCompatConfigWidthSize(reduceCompatConfigWidthSize(0, 0, displayMetrics, i2, i), 1, displayMetrics, i, i2), 2, displayMetrics, i2, i), 3, displayMetrics, i, i2);
    }

    private int reduceCompatConfigWidthSize(int i, int i2, android.util.DisplayMetrics displayMetrics, int i3, int i4) {
        android.graphics.Rect rect = this.mDisplayPolicy.getDecorInsetsInfo(i2, i3, i4).mNonDecorFrame;
        displayMetrics.noncompatWidthPixels = rect.width();
        displayMetrics.noncompatHeightPixels = rect.height();
        int computeCompatibleScaling = (int) (((displayMetrics.noncompatWidthPixels / android.content.res.CompatibilityInfo.computeCompatibleScaling(displayMetrics, (android.util.DisplayMetrics) null)) / displayMetrics.density) + 0.5f);
        if (i == 0 || computeCompatibleScaling < i) {
            return computeCompatibleScaling;
        }
        return i;
    }

    private void computeSizeRanges(android.view.DisplayInfo displayInfo, boolean z, int i, int i2, float f, android.content.res.Configuration configuration) {
        if (z) {
            i2 = i;
            i = i2;
        }
        displayInfo.smallestNominalAppWidth = 1073741824;
        displayInfo.smallestNominalAppHeight = 1073741824;
        displayInfo.largestNominalAppWidth = 0;
        displayInfo.largestNominalAppHeight = 0;
        adjustDisplaySizeRanges(displayInfo, 0, i, i2);
        adjustDisplaySizeRanges(displayInfo, 1, i2, i);
        adjustDisplaySizeRanges(displayInfo, 2, i, i2);
        adjustDisplaySizeRanges(displayInfo, 3, i2, i);
        if (configuration == null) {
            return;
        }
        configuration.smallestScreenWidthDp = (int) ((displayInfo.smallestNominalAppWidth / f) + 0.5f);
    }

    private void adjustDisplaySizeRanges(android.view.DisplayInfo displayInfo, int i, int i2, int i3) {
        com.android.server.wm.DisplayPolicy.DecorInsets.Info decorInsetsInfo = this.mDisplayPolicy.getDecorInsetsInfo(i, i2, i3);
        int width = decorInsetsInfo.mConfigFrame.width();
        int height = decorInsetsInfo.mConfigFrame.height();
        if (width < displayInfo.smallestNominalAppWidth) {
            displayInfo.smallestNominalAppWidth = width;
        }
        if (width > displayInfo.largestNominalAppWidth) {
            displayInfo.largestNominalAppWidth = width;
        }
        if (height < displayInfo.smallestNominalAppHeight) {
            displayInfo.smallestNominalAppHeight = height;
        }
        if (height > displayInfo.largestNominalAppHeight) {
            displayInfo.largestNominalAppHeight = height;
        }
    }

    int getPreferredOptionsPanelGravity() {
        int rotation = getRotation();
        if (this.mInitialDisplayWidth < this.mInitialDisplayHeight) {
            switch (rotation) {
                case 1:
                    return 85;
                case 2:
                    return 81;
                case 3:
                    return 8388691;
                default:
                    return 81;
            }
        }
        switch (rotation) {
            case 1:
                return 81;
            case 2:
                return 8388691;
            case 3:
                return 81;
            default:
                return 85;
        }
    }

    com.android.server.wm.PinnedTaskController getPinnedTaskController() {
        return this.mPinnedTaskController;
    }

    boolean hasAccess(int i) {
        return this.mDisplay.hasAccess(i);
    }

    boolean isPrivate() {
        return (this.mDisplay.getFlags() & 4) != 0;
    }

    boolean isTrusted() {
        return this.mDisplay.isTrusted();
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getRootTask(final int i, final int i2) {
        return (com.android.server.wm.Task) getItemFromTaskDisplayAreas(new java.util.function.Function() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda35
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.wm.Task lambda$getRootTask$13;
                lambda$getRootTask$13 = com.android.server.wm.DisplayContent.lambda$getRootTask$13(i, i2, (com.android.server.wm.TaskDisplayArea) obj);
                return lambda$getRootTask$13;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.wm.Task lambda$getRootTask$13(int i, int i2, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        return taskDisplayArea.getRootTask(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getRootTask$14(int i, com.android.server.wm.Task task) {
        return task.getRootTaskId() == i;
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getRootTask(final int i) {
        return getRootTask(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda18
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getRootTask$14;
                lambda$getRootTask$14 = com.android.server.wm.DisplayContent.lambda$getRootTask$14(i, (com.android.server.wm.Task) obj);
                return lambda$getRootTask$14;
            }
        });
    }

    int getRootTaskCount() {
        final int[] iArr = new int[1];
        forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda24
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.lambda$getRootTaskCount$15(iArr, (com.android.server.wm.Task) obj);
            }
        });
        return iArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getRootTaskCount$15(int[] iArr, com.android.server.wm.Task task) {
        iArr[0] = iArr[0] + 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getTopRootTask$16(com.android.server.wm.Task task) {
        return true;
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getTopRootTask() {
        return getRootTask(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTopRootTask$16;
                lambda$getTopRootTask$16 = com.android.server.wm.DisplayContent.lambda$getTopRootTask$16((com.android.server.wm.Task) obj);
                return lambda$getTopRootTask$16;
            }
        });
    }

    int getCurrentOverrideConfigurationChanges() {
        return this.mCurrentOverrideConfigurationChanges;
    }

    int getInitialDisplayDensity() {
        int i = this.mInitialDisplayDensity;
        if (this.mMaxUiWidth > 0 && this.mInitialDisplayWidth > this.mMaxUiWidth) {
            return (int) ((i * this.mMaxUiWidth) / this.mInitialDisplayWidth);
        }
        return i;
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        int i = getConfiguration().orientation;
        int windowingMode = getWindowingMode();
        super.onConfigurationChanged(configuration);
        if (this.mDisplayPolicy != null) {
            this.mDisplayPolicy.onConfigurationChanged();
            this.mPinnedTaskController.onPostDisplayConfigurationChanged();
            this.mMinSizeOfResizeableTaskDp = getMinimalTaskSizeDp();
        }
        updateImeParent();
        if (this.mContentRecorder != null) {
            this.mContentRecorder.onConfigurationChanged(i, windowingMode);
        }
        if (i != getConfiguration().orientation) {
            getMetricsLogger().write(new android.metrics.LogMaker(1659).setSubtype(getConfiguration().orientation).addTaggedData(1660, java.lang.Integer.valueOf(getDisplayId())));
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    boolean fillsParent() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isVisible() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isVisibleRequested() {
        return (!isVisible() || this.mRemoved || this.mRemoving) ? false : true;
    }

    @Override // com.android.server.wm.WindowContainer
    void onAppTransitionDone() {
        super.onAppTransitionDone();
        this.mWmService.mWindowsChanged = true;
        onTransitionFinished();
    }

    void onTransitionFinished() {
        if (this.mFixedRotationLaunchingApp != null && !this.mFixedRotationLaunchingApp.isVisibleRequested() && !this.mFixedRotationLaunchingApp.isVisible() && !this.mDisplayRotation.isRotatingSeamlessly()) {
            clearFixedRotationLaunchingApp();
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    void setDisplayWindowingMode(int i) {
        setWindowingMode(i);
    }

    boolean forAllImeWindows(com.android.internal.util.ToBooleanFunction<com.android.server.wm.WindowState> toBooleanFunction, boolean z) {
        return this.mImeWindowsContainer.forAllWindowForce(toBooleanFunction, z);
    }

    @Override // com.android.server.wm.WindowContainer
    int getOrientation() {
        int orientation;
        if (this.mWmService.mDisplayFrozen && this.mWmService.mPolicy.isKeyguardLocked()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -9191821315942566105L, 5, null, java.lang.Long.valueOf(this.mDisplayId), java.lang.Long.valueOf(getLastOrientation()));
            return getLastOrientation();
        }
        if (this.mDisplayRotationCompatPolicy != null && (orientation = this.mDisplayRotationCompatPolicy.getOrientation()) != -1) {
            this.mLastOrientationSource = null;
            return orientation;
        }
        int orientation2 = super.getOrientation();
        if (!handlesOrientationChangeFromDescendant(orientation2)) {
            com.android.server.wm.ActivityRecord activityRecord = topRunningActivity(true);
            if (activityRecord != null && activityRecord.mLetterboxUiController.shouldUseDisplayLandscapeNaturalOrientation()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -74384795669614579L, 21, null, java.lang.Long.valueOf(this.mDisplayId), java.lang.Long.valueOf(orientation2), 0L, java.lang.String.valueOf(activityRecord));
                return 0;
            }
            this.mLastOrientationSource = null;
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -3395592185328682328L, 21, null, java.lang.Long.valueOf(this.mDisplayId), java.lang.Long.valueOf(orientation2), -1L);
            return -1;
        }
        if (orientation2 != -2) {
            return orientation2;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 3438870491084701232L, 5, null, -1L, java.lang.Long.valueOf(this.mDisplayId));
        return -1;
    }

    void updateDisplayInfo(@android.annotation.NonNull android.view.DisplayInfo displayInfo) {
        updateBaseDisplayMetricsIfNeeded(displayInfo);
        com.android.server.wm.utils.DisplayInfoOverrides.copyDisplayInfoFields(this.mDisplayInfo, displayInfo, this.mLastDisplayInfoOverride, com.android.server.wm.utils.DisplayInfoOverrides.WM_OVERRIDE_FIELDS);
        this.mDisplayInfo.getAppMetrics(this.mDisplayMetrics, this.mDisplay.getDisplayAdjustments());
        onDisplayInfoChanged();
        onDisplayChanged(this);
    }

    void updatePrivacyIndicatorBounds(android.graphics.Rect[] rectArr) {
        android.view.PrivacyIndicatorBounds privacyIndicatorBounds = this.mCurrentPrivacyIndicatorBounds;
        this.mCurrentPrivacyIndicatorBounds = this.mCurrentPrivacyIndicatorBounds.updateStaticBounds(rectArr);
        if (!java.util.Objects.equals(privacyIndicatorBounds, this.mCurrentPrivacyIndicatorBounds)) {
            updateDisplayFrames(true);
        }
    }

    void onDisplayInfoChanged() {
        updateDisplayFrames(false);
        this.mInputMonitor.layoutInputConsumers(this.mDisplayInfo.logicalWidth, this.mDisplayInfo.logicalHeight);
        this.mDisplayPolicy.onDisplayInfoChanged(this.mDisplayInfo);
    }

    private void updateDisplayFrames(boolean z) {
        if (updateDisplayFrames(this.mDisplayFrames, this.mDisplayInfo.rotation, this.mDisplayInfo.logicalWidth, this.mDisplayInfo.logicalHeight)) {
            this.mInsetsStateController.onDisplayFramesUpdated(z);
        }
    }

    boolean updateDisplayFrames(com.android.server.wm.DisplayFrames displayFrames, int i, int i2, int i3) {
        return displayFrames.update(i, i2, i3, calculateDisplayCutoutForRotation(i), calculateRoundedCornersForRotation(i), calculatePrivacyIndicatorBoundsForRotation(i), calculateDisplayShapeForRotation(i));
    }

    @Override // com.android.server.wm.WindowContainer
    void onDisplayChanged(com.android.server.wm.DisplayContent displayContent) {
        super.onDisplayChanged(displayContent);
        updateSystemGestureExclusionLimit();
        updateKeepClearAreas();
    }

    void updateSystemGestureExclusionLimit() {
        this.mSystemGestureExclusionLimit = (this.mWmService.mConstants.mSystemGestureExclusionLimitDp * this.mDisplayMetrics.densityDpi) / 160;
        updateSystemGestureExclusion();
    }

    void initializeDisplayBaseInfo() {
        android.hardware.display.DisplayManagerInternal displayManagerInternal = this.mWmService.mDisplayManagerInternal;
        if (displayManagerInternal != null) {
            android.view.DisplayInfo displayInfo = displayManagerInternal.getDisplayInfo(this.mDisplayId);
            if (displayInfo != null) {
                this.mDisplayInfo.copyFrom(displayInfo);
            }
            this.mDwpcHelper = new com.android.server.wm.DisplayWindowPolicyControllerHelper(this);
        }
        updateBaseDisplayMetrics(this.mDisplayInfo.logicalWidth, this.mDisplayInfo.logicalHeight, this.mDisplayInfo.logicalDensityDpi, this.mDisplayInfo.physicalXDpi, this.mDisplayInfo.physicalYDpi);
        this.mInitialDisplayWidth = this.mDisplayInfo.logicalWidth;
        this.mInitialDisplayHeight = this.mDisplayInfo.logicalHeight;
        this.mInitialDisplayDensity = this.mDisplayInfo.logicalDensityDpi;
        this.mInitialPhysicalXDpi = this.mDisplayInfo.physicalXDpi;
        this.mInitialPhysicalYDpi = this.mDisplayInfo.physicalYDpi;
        this.mInitialDisplayCutout = this.mDisplayInfo.displayCutout;
        this.mInitialRoundedCorners = this.mDisplayInfo.roundedCorners;
        this.mCurrentPrivacyIndicatorBounds = new android.view.PrivacyIndicatorBounds(new android.graphics.Rect[4], this.mDisplayInfo.rotation);
        this.mInitialDisplayShape = this.mDisplayInfo.displayShape;
        android.view.Display.Mode maximumResolutionDisplayMode = android.util.DisplayUtils.getMaximumResolutionDisplayMode(this.mDisplayInfo.supportedModes);
        this.mPhysicalDisplaySize = new android.graphics.Point(maximumResolutionDisplayMode == null ? this.mInitialDisplayWidth : maximumResolutionDisplayMode.getPhysicalWidth(), maximumResolutionDisplayMode == null ? this.mInitialDisplayHeight : maximumResolutionDisplayMode.getPhysicalHeight());
    }

    private void updateBaseDisplayMetricsIfNeeded(android.view.DisplayInfo displayInfo) {
        android.view.DisplayCutout displayCutout;
        int i;
        android.view.DisplayShape displayShape;
        android.view.RoundedCorners roundedCorners;
        java.lang.String str;
        this.mDisplayInfo.copyFrom(displayInfo);
        int rotation = getRotation();
        int i2 = this.mDisplayInfo.rotation;
        boolean z = i2 == 1 || i2 == 3;
        android.view.DisplayInfo displayInfo2 = this.mDisplayInfo;
        int i3 = z ? displayInfo2.logicalHeight : displayInfo2.logicalWidth;
        int i4 = z ? this.mDisplayInfo.logicalWidth : this.mDisplayInfo.logicalHeight;
        int i5 = this.mDisplayInfo.logicalDensityDpi;
        float f = this.mDisplayInfo.physicalXDpi;
        float f2 = this.mDisplayInfo.physicalYDpi;
        if (!this.mIgnoreDisplayCutout) {
            displayCutout = this.mDisplayInfo.displayCutout;
        } else {
            displayCutout = android.view.DisplayCutout.NO_CUTOUT;
        }
        java.lang.String str2 = this.mDisplayInfo.uniqueId;
        android.view.RoundedCorners roundedCorners2 = this.mDisplayInfo.roundedCorners;
        android.view.DisplayShape displayShape2 = this.mDisplayInfo.displayShape;
        boolean z2 = (this.mInitialDisplayWidth == i3 && this.mInitialDisplayHeight == i4 && this.mInitialDisplayDensity == i5 && this.mInitialPhysicalXDpi == f && this.mInitialPhysicalYDpi == f2 && java.util.Objects.equals(this.mInitialDisplayCutout, displayCutout) && java.util.Objects.equals(this.mInitialRoundedCorners, roundedCorners2) && java.util.Objects.equals(this.mInitialDisplayShape, displayShape2)) ? false : true;
        boolean z3 = !str2.equals(this.mCurrentUniqueDisplayId);
        if (z2 || z3) {
            if (z3) {
                this.mWmService.mDisplayWindowSettings.applySettingsToDisplayLocked(this, false);
                displayShape = displayShape2;
                roundedCorners = roundedCorners2;
                i = rotation;
                str = str2;
                this.mDisplayUpdater.onDisplayContentDisplayPropertiesPreChanged(this.mDisplayId, this.mInitialDisplayWidth, this.mInitialDisplayHeight, i3, i4);
                this.mDisplayRotation.physicalDisplayChanged();
                this.mDisplayPolicy.physicalDisplayChanged();
            } else {
                i = rotation;
                displayShape = displayShape2;
                roundedCorners = roundedCorners2;
                str = str2;
            }
            android.view.DisplayCutout displayCutout2 = displayCutout;
            java.lang.String str3 = str;
            android.view.RoundedCorners roundedCorners3 = roundedCorners;
            android.view.DisplayShape displayShape3 = displayShape;
            updateBaseDisplayMetrics(this.mIsSizeForced ? this.mBaseDisplayWidth : i3, this.mIsSizeForced ? this.mBaseDisplayHeight : i4, this.mIsDensityForced ? this.mBaseDisplayDensity : i5, this.mIsSizeForced ? this.mBaseDisplayPhysicalXDpi : f, this.mIsSizeForced ? this.mBaseDisplayPhysicalYDpi : f2);
            configureDisplayPolicy();
            if (z3) {
                this.mWmService.mDisplayWindowSettings.applyRotationSettingsToDisplayLocked(this);
            }
            this.mInitialDisplayWidth = i3;
            this.mInitialDisplayHeight = i4;
            this.mInitialDisplayDensity = i5;
            this.mInitialPhysicalXDpi = f;
            this.mInitialPhysicalYDpi = f2;
            this.mInitialDisplayCutout = displayCutout2;
            this.mInitialRoundedCorners = roundedCorners3;
            this.mInitialDisplayShape = displayShape3;
            this.mCurrentUniqueDisplayId = str3;
            reconfigureDisplayLocked();
            if (z3) {
                this.mDisplayPolicy.physicalDisplayUpdated();
                this.mDisplayUpdater.onDisplayContentDisplayPropertiesPostChanged(i, getRotation(), getDisplayAreaInfo());
            }
        }
    }

    void setMaxUiWidth(int i) {
        this.mMaxUiWidth = i;
        updateBaseDisplayMetrics(this.mBaseDisplayWidth, this.mBaseDisplayHeight, this.mBaseDisplayDensity, this.mBaseDisplayPhysicalXDpi, this.mBaseDisplayPhysicalYDpi);
    }

    void updateBaseDisplayMetrics(int i, int i2, int i3, float f, float f2) {
        this.mBaseDisplayWidth = i;
        this.mBaseDisplayHeight = i2;
        this.mBaseDisplayDensity = i3;
        this.mBaseDisplayPhysicalXDpi = f;
        this.mBaseDisplayPhysicalYDpi = f2;
        if (this.mIsSizeForced) {
            this.mBaseDisplayCutout = loadDisplayCutout(i, i2);
            this.mBaseRoundedCorners = loadRoundedCorners(i, i2);
        }
        if (this.mMaxUiWidth > 0 && this.mBaseDisplayWidth > this.mMaxUiWidth) {
            float f3 = this.mMaxUiWidth / this.mBaseDisplayWidth;
            this.mBaseDisplayHeight = (int) (this.mBaseDisplayHeight * f3);
            this.mBaseDisplayWidth = this.mMaxUiWidth;
            this.mBaseDisplayPhysicalXDpi *= f3;
            this.mBaseDisplayPhysicalYDpi *= f3;
            if (!this.mIsDensityForced) {
                this.mBaseDisplayDensity = (int) (this.mBaseDisplayDensity * f3);
            }
        }
        if (this.mDisplayReady && !this.mDisplayPolicy.shouldKeepCurrentDecorInsets()) {
            this.mDisplayPolicy.mDecorInsets.invalidate();
        }
    }

    void setForcedDensity(int i, int i2) {
        this.mIsDensityForced = i != getInitialDisplayDensity();
        boolean z = i2 == -2;
        if (this.mWmService.mCurrentUserId == i2 || z) {
            this.mBaseDisplayDensity = i;
            reconfigureDisplayLocked();
        }
        if (z) {
            return;
        }
        if (i == getInitialDisplayDensity()) {
            i = 0;
        }
        this.mWmService.mDisplayWindowSettings.setForcedDensity(getDisplayInfo(), i, i2);
    }

    void setForcedScalingMode(int i) {
        if (i != 1) {
            i = 0;
        }
        this.mDisplayScalingDisabled = i != 0;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Using display scaling mode: ");
        sb.append(this.mDisplayScalingDisabled ? "off" : com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_AUTO);
        android.util.Slog.i(TAG, sb.toString());
        reconfigureDisplayLocked();
        this.mWmService.mDisplayWindowSettings.setForcedScalingMode(this, i);
    }

    void setForcedSize(int i, int i2) {
        setForcedSize(i, i2, 0.0f, 0.0f);
    }

    void setForcedSize(int i, int i2, float f, float f2) {
        if (this.mMaxUiWidth > 0 && i > this.mMaxUiWidth) {
            i2 = (int) (i2 * (this.mMaxUiWidth / i));
            i = this.mMaxUiWidth;
        }
        int i3 = 0;
        this.mIsSizeForced = (this.mInitialDisplayWidth == i && this.mInitialDisplayHeight == i2) ? false : true;
        if (this.mIsSizeForced) {
            android.graphics.Point validForcedSize = getValidForcedSize(i, i2);
            int i4 = validForcedSize.x;
            i2 = validForcedSize.y;
            i = i4;
        }
        android.util.Slog.i(TAG, "Using new display size: " + i + "x" + i2);
        int i5 = this.mBaseDisplayDensity;
        if (f == 0.0f) {
            f = this.mBaseDisplayPhysicalXDpi;
        }
        float f3 = f;
        if (f2 == 0.0f) {
            f2 = this.mBaseDisplayPhysicalYDpi;
        }
        updateBaseDisplayMetrics(i, i2, i5, f3, f2);
        reconfigureDisplayLocked();
        if (this.mIsSizeForced) {
            i3 = i;
        } else {
            i2 = 0;
        }
        this.mWmService.mDisplayWindowSettings.setForcedSize(this, i3, i2);
    }

    android.graphics.Point getValidForcedSize(int i, int i2) {
        int max = java.lang.Math.max(this.mInitialDisplayWidth, this.mInitialDisplayHeight) * 3;
        return new android.graphics.Point(java.lang.Math.min(java.lang.Math.max(i, 200), max), java.lang.Math.min(java.lang.Math.max(i2, 200), max));
    }

    android.view.DisplayCutout loadDisplayCutout(int i, int i2) {
        if (this.mDisplayPolicy == null || this.mInitialDisplayCutout == null) {
            return null;
        }
        return android.view.DisplayCutout.fromResourcesRectApproximation(this.mDisplayPolicy.getSystemUiContext().getResources(), this.mDisplayInfo.uniqueId, this.mPhysicalDisplaySize.x, this.mPhysicalDisplaySize.y, i, i2);
    }

    android.view.RoundedCorners loadRoundedCorners(int i, int i2) {
        if (this.mDisplayPolicy == null || this.mInitialRoundedCorners == null) {
            return null;
        }
        return android.view.RoundedCorners.fromResources(this.mDisplayPolicy.getSystemUiContext().getResources(), this.mDisplayInfo.uniqueId, this.mPhysicalDisplaySize.x, this.mPhysicalDisplaySize.y, i, i2);
    }

    @Override // com.android.server.wm.DisplayArea
    void getStableRect(android.graphics.Rect rect) {
        android.view.InsetsState rawInsetsState = this.mDisplayContent.getInsetsStateController().getRawInsetsState();
        rect.set(rawInsetsState.getDisplayFrame());
        rect.inset(rawInsetsState.calculateInsets(rect, android.view.WindowInsets.Type.systemBars(), true));
    }

    com.android.server.wm.TaskDisplayArea getDefaultTaskDisplayArea() {
        return this.mDisplayAreaPolicy.getDefaultTaskDisplayArea();
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateDisplayAreaOrganizers() {
        if (!isTrusted()) {
            return;
        }
        forAllDisplayAreas(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda55
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.this.lambda$updateDisplayAreaOrganizers$17((com.android.server.wm.DisplayArea) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDisplayAreaOrganizers$17(com.android.server.wm.DisplayArea displayArea) {
        android.window.IDisplayAreaOrganizer organizerByFeature;
        if (!displayArea.isOrganized() && (organizerByFeature = this.mAtmService.mWindowOrganizerController.mDisplayAreaOrganizerController.getOrganizerByFeature(displayArea.mFeatureId)) != null) {
            displayArea.setOrganizer(organizerByFeature);
        }
    }

    @android.annotation.Nullable
    com.android.server.wm.Task findTaskForResizePoint(final int i, final int i2) {
        final int dipToPixel = com.android.server.wm.WindowManagerService.dipToPixel(30, this.mDisplayMetrics);
        return (com.android.server.wm.Task) getItemFromTaskDisplayAreas(new java.util.function.Function() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda54
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.wm.Task lambda$findTaskForResizePoint$18;
                lambda$findTaskForResizePoint$18 = com.android.server.wm.DisplayContent.this.lambda$findTaskForResizePoint$18(i, i2, dipToPixel, (com.android.server.wm.TaskDisplayArea) obj);
                return lambda$findTaskForResizePoint$18;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.server.wm.Task lambda$findTaskForResizePoint$18(int i, int i2, int i3, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        return this.mTmpTaskForResizePointSearchResult.process(taskDisplayArea, i, i2, i3);
    }

    void updateTouchExcludeRegion() {
        if (this.mTapDetector == null) {
            return;
        }
        final com.android.server.wm.Task task = this.mFocusedApp != null ? this.mFocusedApp.getTask() : null;
        if (task == null) {
            this.mTouchExcludeRegion.setEmpty();
        } else {
            this.mTouchExcludeRegion.set(0, 0, this.mDisplayInfo.logicalWidth, this.mDisplayInfo.logicalHeight);
            final int dipToPixel = com.android.server.wm.WindowManagerService.dipToPixel(30, this.mDisplayMetrics);
            this.mTmpRect.setEmpty();
            this.mTmpRect2.setEmpty();
            forAllTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.DisplayContent.this.lambda$updateTouchExcludeRegion$19(task, dipToPixel, (com.android.server.wm.Task) obj);
                }
            });
            if (!this.mTmpRect2.isEmpty()) {
                this.mTouchExcludeRegion.op(this.mTmpRect2, android.graphics.Region.Op.UNION);
            }
        }
        if (this.mInputMethodWindow != null && this.mInputMethodWindow.isVisible()) {
            this.mInputMethodWindow.getTouchableRegion(this.mTmpRegion);
            this.mTouchExcludeRegion.op(this.mTmpRegion, android.graphics.Region.Op.UNION);
        }
        for (int size = this.mTapExcludedWindows.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowState windowState = this.mTapExcludedWindows.get(size);
            if (windowState.isVisible()) {
                windowState.getTouchableRegion(this.mTmpRegion);
                this.mTouchExcludeRegion.op(this.mTmpRegion, android.graphics.Region.Op.UNION);
            }
        }
        amendWindowTapExcludeRegion(this.mTouchExcludeRegion);
        this.mTapDetector.setTouchExcludeRegion(this.mTouchExcludeRegion);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: processTaskForTouchExcludeRegion, reason: merged with bridge method [inline-methods] */
    public void lambda$updateTouchExcludeRegion$19(com.android.server.wm.Task task, com.android.server.wm.Task task2, int i) {
        com.android.server.wm.ActivityRecord topVisibleActivity = task.getTopVisibleActivity();
        if (topVisibleActivity == null || !topVisibleActivity.hasContentToDisplay()) {
            return;
        }
        if (task.isActivityTypeHome() && task.isVisible() && task.isResizeable()) {
            task.getDisplayArea().getBounds(this.mTmpRect);
        } else {
            task.getDimBounds(this.mTmpRect);
        }
        if (task == task2) {
            this.mTmpRect2.set(this.mTmpRect);
        }
        boolean inFreeformWindowingMode = task.inFreeformWindowingMode();
        if (task != task2 || inFreeformWindowingMode) {
            if (inFreeformWindowingMode) {
                int i2 = -i;
                this.mTmpRect.inset(i2, i2);
                this.mTmpRect.inset(getInsetsStateController().getRawInsetsState().calculateInsets(this.mTmpRect, android.view.WindowInsets.Type.systemBars() | android.view.WindowInsets.Type.ime(), false));
            }
            this.mTouchExcludeRegion.op(this.mTmpRect, android.graphics.Region.Op.DIFFERENCE);
        }
    }

    private void amendWindowTapExcludeRegion(android.graphics.Region region) {
        android.graphics.Region obtain = android.graphics.Region.obtain();
        for (int size = this.mTapExcludeProvidingWindows.size() - 1; size >= 0; size--) {
            this.mTapExcludeProvidingWindows.valueAt(size).getTapExcludeRegion(obtain);
            region.op(obtain, android.graphics.Region.Op.UNION);
        }
        obtain.recycle();
    }

    @Override // com.android.server.wm.WindowContainer
    void switchUser(int i) {
        super.switchUser(i);
        this.mWmService.mWindowsChanged = true;
        this.mDisplayPolicy.switchUser();
    }

    private boolean shouldDeferRemoval() {
        return isAnimating(3) || this.mTransitionController.isTransitionOnDisplay(this);
    }

    @Override // com.android.server.wm.WindowContainer
    void removeIfPossible() {
        if (shouldDeferRemoval()) {
            this.mDeferredRemoval = true;
        } else {
            removeImmediately();
        }
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    void removeImmediately() {
        this.mDeferredRemoval = false;
        try {
            this.mOpeningApps.clear();
            this.mClosingApps.clear();
            this.mChangingContainers.clear();
            this.mUnknownAppVisibilityController.clear();
            this.mAppTransition.removeAppTransitionTimeoutCallbacks();
            this.mTransitionController.unregisterLegacyListener(this.mFixedRotationTransitionListener);
            handleAnimatingStoppedAndTransition();
            this.mWmService.stopFreezingDisplayLocked();
            this.mDeviceStateController.unregisterDeviceStateCallback(this.mDeviceStateConsumer);
            super.removeImmediately();
            this.mPointerEventDispatcher.dispose();
            setRotationAnimation(null);
            setRemoteInsetsController(null);
            this.mOverlayLayer.release();
            this.mInputOverlayLayer.release();
            this.mA11yOverlayLayer.release();
            this.mWindowingLayer.release();
            this.mInputMonitor.onDisplayRemoved();
            this.mWmService.mDisplayNotificationController.dispatchDisplayRemoved(this);
            this.mDisplayRotation.onDisplayRemoved();
            this.mWmService.mAccessibilityController.onDisplayRemoved(this.mDisplayId);
            this.mRootWindowContainer.mTaskSupervisor.getKeyguardController().onDisplayRemoved(this.mDisplayId);
            this.mWallpaperController.resetLargestDisplay(this.mDisplay);
            this.mWmService.mDisplayWindowSettings.onDisplayRemoved(this);
            this.mDisplayReady = false;
            getPendingTransaction().apply();
            this.mWmService.mWindowPlacerLocked.requestTraversal();
            if (this.mDisplayRotationCompatPolicy != null) {
                this.mDisplayRotationCompatPolicy.dispose();
            }
        } catch (java.lang.Throwable th) {
            this.mDisplayReady = false;
            throw th;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    boolean handleCompleteDeferredRemoval() {
        boolean z = super.handleCompleteDeferredRemoval() || shouldDeferRemoval();
        if (!z && this.mDeferredRemoval) {
            removeImmediately();
            return false;
        }
        return z;
    }

    void adjustForImeIfNeeded() {
        com.android.server.wm.WindowState windowState = this.mInputMethodWindow;
        this.mPinnedTaskController.setAdjustedForIme(windowState != null && windowState.isVisible() && windowState.isDisplayed(), getInputMethodWindowVisibleHeight());
    }

    int getInputMethodWindowVisibleHeight() {
        android.view.InsetsState rawInsetsState = getInsetsStateController().getRawInsetsState();
        android.view.InsetsSource peekSource = rawInsetsState.peekSource(android.view.InsetsSource.ID_IME);
        if (peekSource == null || !peekSource.isVisible()) {
            return 0;
        }
        android.graphics.Rect visibleFrame = peekSource.getVisibleFrame() != null ? peekSource.getVisibleFrame() : peekSource.getFrame();
        android.graphics.Rect rect = this.mTmpRect;
        rect.set(rawInsetsState.getDisplayFrame());
        rect.inset(rawInsetsState.calculateInsets(rect, android.view.WindowInsets.Type.systemBars() | android.view.WindowInsets.Type.displayCutout(), false));
        return rect.bottom - visibleFrame.top;
    }

    void enableHighPerfTransition(boolean z) {
        if (!com.android.window.flags.Flags.explicitRefreshRateHints()) {
            if (z) {
                getPendingTransaction().setEarlyWakeupStart();
                return;
            } else {
                getPendingTransaction().setEarlyWakeupEnd();
                return;
            }
        }
        if (z) {
            if (this.mTransitionPrefSession == null) {
                this.mTransitionPrefSession = this.mWmService.mSystemPerformanceHinter.createSession(3, this.mDisplayId, "Transition");
            }
            this.mTransitionPrefSession.start();
        } else if (this.mTransitionPrefSession != null) {
            this.mTransitionPrefSession.close();
        }
    }

    void enableHighFrameRate(boolean z) {
        if (!com.android.window.flags.Flags.explicitRefreshRateHints()) {
            return;
        }
        if (z) {
            if (this.mHighFrameRateSession == null) {
                this.mHighFrameRateSession = this.mWmService.mSystemPerformanceHinter.createSession(2, this.mDisplayId, "WindowAnimation");
            }
            this.mHighFrameRateSession.start();
        } else if (this.mHighFrameRateSession != null) {
            this.mHighFrameRateSession.close();
        }
    }

    void rotateBounds(int i, int i2, android.graphics.Rect rect) {
        getBounds(this.mTmpRect, i);
        android.util.RotationUtils.rotateBounds(rect, this.mTmpRect, i, i2);
    }

    public void setRotationAnimation(com.android.server.wm.ScreenRotationAnimation screenRotationAnimation) {
        com.android.server.wm.ScreenRotationAnimation screenRotationAnimation2 = this.mScreenRotationAnimation;
        this.mScreenRotationAnimation = screenRotationAnimation;
        if (screenRotationAnimation2 != null) {
            screenRotationAnimation2.kill();
        }
        if (screenRotationAnimation != null && screenRotationAnimation.hasScreenshot()) {
            startAsyncRotationIfNeeded();
        }
    }

    public com.android.server.wm.ScreenRotationAnimation getRotationAnimation() {
        return this.mScreenRotationAnimation;
    }

    void requestChangeTransitionIfNeeded(int i, @android.annotation.Nullable android.window.TransitionRequestInfo.DisplayChange displayChange) {
        if (this.mLastHasContent) {
            com.android.server.wm.TransitionController transitionController = this.mTransitionController;
            if (transitionController.isCollecting()) {
                if (displayChange != null) {
                    throw new java.lang.IllegalArgumentException("Provided displayChange for non-new transition");
                }
                if (!transitionController.isCollecting(this)) {
                    transitionController.collect(this);
                    startAsyncRotationIfNeeded();
                    if (this.mFixedRotationLaunchingApp != null) {
                        setSeamlessTransitionForFixedRotation(transitionController.getCollectingTransition());
                        return;
                    }
                    return;
                }
                if (this.mAsyncRotationController != null && !isRotationChanging()) {
                    android.util.Slog.i(TAG, "Finish AsyncRotation for previous intermediate change");
                    finishAsyncRotationIfPossible();
                    return;
                }
                return;
            }
            com.android.server.wm.Transition requestTransitionIfNeeded = transitionController.requestTransitionIfNeeded(6, 0, this, this, null, displayChange);
            if (requestTransitionIfNeeded != null) {
                this.mAtmService.startPowerMode(2);
                if (this.mAsyncRotationController != null) {
                    this.mAsyncRotationController.updateRotation();
                }
                if (this.mFixedRotationLaunchingApp != null) {
                    setSeamlessTransitionForFixedRotation(requestTransitionIfNeeded);
                } else if (isRotationChanging()) {
                    if (displayChange != null && this.mDisplayRotation.shouldRotateSeamlessly(displayChange.getStartRotation(), displayChange.getEndRotation(), false)) {
                        requestTransitionIfNeeded.onSeamlessRotating(this);
                    }
                    this.mWmService.mLatencyTracker.onActionStart(6);
                    transitionController.mTransitionMetricsReporter.associate(requestTransitionIfNeeded.getToken(), new java.util.function.LongConsumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda1
                        @Override // java.util.function.LongConsumer
                        public final void accept(long j) {
                            com.android.server.wm.DisplayContent.this.lambda$requestChangeTransitionIfNeeded$20(j);
                        }
                    });
                    startAsyncRotation(false);
                }
                requestTransitionIfNeeded.setKnownConfigChanges(this, i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestChangeTransitionIfNeeded$20(long j) {
        this.mWmService.mLatencyTracker.onActionEnd(6);
    }

    private void setSeamlessTransitionForFixedRotation(com.android.server.wm.Transition transition) {
        transition.setSeamlessRotation(this);
        if (this.mAsyncRotationController != null) {
            this.mAsyncRotationController.keepAppearanceInPreviousRotation();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    boolean inTransition() {
        return this.mScreenRotationAnimation != null || super.inTransition();
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        if (i == 2 && !isVisible()) {
            return;
        }
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268053L, i);
        protoOutputStream.write(1120986464258L, this.mDisplayId);
        protoOutputStream.write(1120986464265L, this.mBaseDisplayDensity);
        this.mDisplayInfo.dumpDebug(protoOutputStream, 1146756268042L);
        this.mDisplayRotation.dumpDebug(protoOutputStream, 1146756268065L);
        com.android.server.wm.ScreenRotationAnimation rotationAnimation = getRotationAnimation();
        if (rotationAnimation != null) {
            rotationAnimation.dumpDebug(protoOutputStream, 1146756268044L);
        }
        this.mDisplayFrames.dumpDebug(protoOutputStream, 1146756268045L);
        protoOutputStream.write(1120986464295L, this.mMinSizeOfResizeableTaskDp);
        if (this.mTransitionController.isShellTransitionsEnabled()) {
            this.mTransitionController.dumpDebugLegacy(protoOutputStream, 1146756268048L);
        } else {
            this.mAppTransition.dumpDebug(protoOutputStream, 1146756268048L);
        }
        if (this.mFocusedApp != null) {
            this.mFocusedApp.writeNameToProto(protoOutputStream, 1138166333455L);
        }
        for (int size = this.mOpeningApps.size() - 1; size >= 0; size--) {
            this.mOpeningApps.valueAt(size).writeIdentifierToProto(protoOutputStream, 2246267895825L);
        }
        for (int size2 = this.mClosingApps.size() - 1; size2 >= 0; size2--) {
            this.mClosingApps.valueAt(size2).writeIdentifierToProto(protoOutputStream, 2246267895826L);
        }
        com.android.server.wm.Task focusedRootTask = getFocusedRootTask();
        if (focusedRootTask != null) {
            protoOutputStream.write(1120986464279L, focusedRootTask.getRootTaskId());
            com.android.server.wm.ActivityRecord focusedActivity = focusedRootTask.getDisplayArea().getFocusedActivity();
            if (focusedActivity != null) {
                focusedActivity.writeIdentifierToProto(protoOutputStream, 1146756268056L);
            }
        } else {
            protoOutputStream.write(1120986464279L, -1);
        }
        protoOutputStream.write(1133871366170L, isReady());
        protoOutputStream.write(1133871366180L, isSleeping());
        for (int i2 = 0; i2 < this.mAllSleepTokens.size(); i2++) {
            this.mAllSleepTokens.get(i2).writeTagToProto(protoOutputStream, 2237677961253L);
        }
        if (this.mImeLayeringTarget != null) {
            this.mImeLayeringTarget.dumpDebug(protoOutputStream, 1146756268059L, i);
        }
        if (this.mImeInputTarget != null) {
            this.mImeInputTarget.dumpProto(protoOutputStream, 1146756268060L, i);
        }
        if (this.mImeControlTarget != null && this.mImeControlTarget.getWindow() != null) {
            this.mImeControlTarget.getWindow().dumpDebug(protoOutputStream, 1146756268061L, i);
        }
        if (this.mCurrentFocus != null) {
            this.mCurrentFocus.dumpDebug(protoOutputStream, 1146756268062L, i);
        }
        if (this.mInsetsStateController != null) {
            this.mInsetsStateController.dumpDebug(protoOutputStream, i);
        }
        protoOutputStream.write(1120986464290L, getImePolicy());
        java.util.Iterator<android.graphics.Rect> it = getKeepClearAreas().iterator();
        while (it.hasNext()) {
            it.next().dumpDebug(protoOutputStream, 2246267895846L);
        }
        protoOutputStream.end(start);
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    long getProtoFieldId() {
        return 1146756268035L;
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
    public void dump(final java.io.PrintWriter printWriter, final java.lang.String str, final boolean z) {
        printWriter.print(str);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Display: mDisplayId=");
        sb.append(this.mDisplayId);
        sb.append(isOrganized() ? " (organized)" : "");
        printWriter.println(sb.toString());
        java.lang.String str2 = "  " + str;
        printWriter.print(str2);
        printWriter.print("init=");
        printWriter.print(this.mInitialDisplayWidth);
        printWriter.print("x");
        printWriter.print(this.mInitialDisplayHeight);
        printWriter.print(" ");
        printWriter.print(this.mInitialDisplayDensity);
        printWriter.print("dpi");
        printWriter.print(" mMinSizeOfResizeableTaskDp=");
        printWriter.print(this.mMinSizeOfResizeableTaskDp);
        if (this.mInitialDisplayWidth != this.mBaseDisplayWidth || this.mInitialDisplayHeight != this.mBaseDisplayHeight || this.mInitialDisplayDensity != this.mBaseDisplayDensity) {
            printWriter.print(" base=");
            printWriter.print(this.mBaseDisplayWidth);
            printWriter.print("x");
            printWriter.print(this.mBaseDisplayHeight);
            printWriter.print(" ");
            printWriter.print(this.mBaseDisplayDensity);
            printWriter.print("dpi");
        }
        if (this.mDisplayScalingDisabled) {
            printWriter.println(" noscale");
        }
        printWriter.print(" cur=");
        printWriter.print(this.mDisplayInfo.logicalWidth);
        printWriter.print("x");
        printWriter.print(this.mDisplayInfo.logicalHeight);
        printWriter.print(" app=");
        printWriter.print(this.mDisplayInfo.appWidth);
        printWriter.print("x");
        printWriter.print(this.mDisplayInfo.appHeight);
        printWriter.print(" rng=");
        printWriter.print(this.mDisplayInfo.smallestNominalAppWidth);
        printWriter.print("x");
        printWriter.print(this.mDisplayInfo.smallestNominalAppHeight);
        printWriter.print("-");
        printWriter.print(this.mDisplayInfo.largestNominalAppWidth);
        printWriter.print("x");
        printWriter.println(this.mDisplayInfo.largestNominalAppHeight);
        printWriter.print(str2 + "deferred=" + this.mDeferredRemoval + " mLayoutNeeded=" + this.mLayoutNeeded);
        java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
        sb2.append(" mTouchExcludeRegion=");
        sb2.append(this.mTouchExcludeRegion);
        printWriter.println(sb2.toString());
        printWriter.println();
        super.dump(printWriter, str, z);
        printWriter.print(str);
        printWriter.print("mLayoutSeq=");
        printWriter.println(this.mLayoutSeq);
        printWriter.print("  mCurrentFocus=");
        printWriter.println(this.mCurrentFocus);
        printWriter.print("  mFocusedApp=");
        printWriter.println(this.mFocusedApp);
        if (this.mFixedRotationLaunchingApp != null) {
            printWriter.println("  mFixedRotationLaunchingApp=" + this.mFixedRotationLaunchingApp);
        }
        if (this.mAsyncRotationController != null) {
            this.mAsyncRotationController.dump(printWriter, str);
        }
        printWriter.println();
        printWriter.print(str + "mHoldScreenWindow=");
        printWriter.print(this.mHoldScreenWindow);
        printWriter.println();
        printWriter.print(str + "mObscuringWindow=");
        printWriter.print(this.mObscuringWindow);
        printWriter.println();
        printWriter.print(str + "mLastWakeLockHoldingWindow=");
        printWriter.print(this.mLastWakeLockHoldingWindow);
        printWriter.println();
        printWriter.print(str + "mLastWakeLockObscuringWindow=");
        printWriter.println(this.mLastWakeLockObscuringWindow);
        printWriter.println();
        this.mWallpaperController.dump(printWriter, "  ");
        if (this.mSystemGestureExclusionListeners.getRegisteredCallbackCount() > 0) {
            printWriter.println();
            printWriter.print("  mSystemGestureExclusion=");
            printWriter.println(this.mSystemGestureExclusion);
        }
        java.util.Set<android.graphics.Rect> keepClearAreas = getKeepClearAreas();
        if (!keepClearAreas.isEmpty()) {
            printWriter.println();
            printWriter.print("  keepClearAreas=");
            printWriter.println(keepClearAreas);
        }
        printWriter.println();
        printWriter.println(str + "Display areas in top down Z order:");
        dumpChildDisplayArea(printWriter, str2, z);
        printWriter.println();
        printWriter.println(str + "Task display areas in top down Z order:");
        forAllTaskDisplayAreas(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.lambda$dump$21(printWriter, str, z, (com.android.server.wm.TaskDisplayArea) obj);
            }
        });
        printWriter.println();
        com.android.server.wm.ScreenRotationAnimation rotationAnimation = getRotationAnimation();
        if (rotationAnimation != null) {
            printWriter.println("  mScreenRotationAnimation:");
            rotationAnimation.printTo(str2, printWriter);
        } else if (z) {
            printWriter.println("  no ScreenRotationAnimation ");
        }
        printWriter.println();
        com.android.server.wm.Task rootHomeTask = getDefaultTaskDisplayArea().getRootHomeTask();
        if (rootHomeTask != null) {
            printWriter.println(str + "rootHomeTask=" + rootHomeTask.getName());
        }
        com.android.server.wm.Task rootPinnedTask = getDefaultTaskDisplayArea().getRootPinnedTask();
        if (rootPinnedTask != null) {
            printWriter.println(str + "rootPinnedTask=" + rootPinnedTask.getName());
        }
        com.android.server.wm.Task rootTask = getDefaultTaskDisplayArea().getRootTask(0, 3);
        if (rootTask != null) {
            printWriter.println(str + "rootRecentsTask=" + rootTask.getName());
        }
        com.android.server.wm.Task rootTask2 = getRootTask(0, 5);
        if (rootTask2 != null) {
            printWriter.println(str + "rootDreamTask=" + rootTask2.getName());
        }
        printWriter.println();
        this.mPinnedTaskController.dump(str, printWriter);
        printWriter.println();
        this.mDisplayFrames.dump(str, printWriter);
        printWriter.println();
        this.mDisplayPolicy.dump(str, printWriter);
        printWriter.println();
        this.mDisplayRotation.dump(str, printWriter);
        printWriter.println();
        this.mInputMonitor.dump(printWriter, "  ");
        printWriter.println();
        this.mInsetsStateController.dump(str, printWriter);
        this.mInsetsPolicy.dump(str, printWriter);
        this.mDwpcHelper.dump(str, printWriter);
        printWriter.println();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dump$21(java.io.PrintWriter printWriter, java.lang.String str, boolean z, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        taskDisplayArea.dump(printWriter, str + "  ", z);
    }

    @Override // com.android.server.wm.DisplayArea
    public java.lang.String toString() {
        return "Display{#" + this.mDisplayId + " state=" + android.view.Display.stateToString(this.mDisplayInfo.state) + " size=" + this.mDisplayInfo.logicalWidth + "x" + this.mDisplayInfo.logicalHeight + " " + android.view.Surface.rotationToString(this.mDisplayInfo.rotation) + "}";
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.ConfigurationContainer
    java.lang.String getName() {
        return "Display " + this.mDisplayId + " name=\"" + this.mDisplayInfo.name + "\"";
    }

    com.android.server.wm.WindowState getTouchableWinAtPointLocked(float f, float f2) {
        final int i = (int) f;
        final int i2 = (int) f2;
        return getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda38
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$getTouchableWinAtPointLocked$22;
                lambda$getTouchableWinAtPointLocked$22 = com.android.server.wm.DisplayContent.this.lambda$getTouchableWinAtPointLocked$22(i, i2, (com.android.server.wm.WindowState) obj);
                return lambda$getTouchableWinAtPointLocked$22;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getTouchableWinAtPointLocked$22(int i, int i2, com.android.server.wm.WindowState windowState) {
        int i3 = windowState.mAttrs.flags;
        if (!windowState.isVisible() || (i3 & 16) != 0) {
            return false;
        }
        windowState.getVisibleBounds(this.mTmpRect);
        if (!this.mTmpRect.contains(i, i2)) {
            return false;
        }
        windowState.getTouchableRegion(this.mTmpRegion);
        return this.mTmpRegion.contains(i, i2) || (i3 & 40) == 0;
    }

    boolean canAddToastWindowForUid(final int i) {
        return getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda33
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$canAddToastWindowForUid$23;
                lambda$canAddToastWindowForUid$23 = com.android.server.wm.DisplayContent.lambda$canAddToastWindowForUid$23(i, (com.android.server.wm.WindowState) obj);
                return lambda$canAddToastWindowForUid$23;
            }
        }) != null || getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda34
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$canAddToastWindowForUid$24;
                lambda$canAddToastWindowForUid$24 = com.android.server.wm.DisplayContent.lambda$canAddToastWindowForUid$24(i, (com.android.server.wm.WindowState) obj);
                return lambda$canAddToastWindowForUid$24;
            }
        }) == null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$canAddToastWindowForUid$23(int i, com.android.server.wm.WindowState windowState) {
        return windowState.mOwnerUid == i && windowState.isFocused();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$canAddToastWindowForUid$24(int i, com.android.server.wm.WindowState windowState) {
        return windowState.mAttrs.type == 2005 && windowState.mOwnerUid == i && !windowState.mPermanentlyHidden && !windowState.mWindowRemovalAllowed;
    }

    void scheduleToastWindowsTimeoutIfNeededLocked(com.android.server.wm.WindowState windowState, com.android.server.wm.WindowState windowState2) {
        if (windowState != null) {
            if (windowState2 != null && windowState2.mOwnerUid == windowState.mOwnerUid) {
                return;
            }
            this.mTmpWindow = windowState;
            forAllWindows(this.mScheduleToastTimeout, false);
        }
    }

    boolean canStealTopFocus() {
        return (this.mDisplayInfo.flags & 4096) == 0;
    }

    com.android.server.wm.WindowState findFocusedWindowIfNeeded(int i) {
        if (hasOwnFocus() || i == -1) {
            return findFocusedWindow();
        }
        return null;
    }

    com.android.server.wm.WindowState findFocusedWindow() {
        this.mTmpWindow = null;
        forAllWindows(this.mFindFocusedWindow, true);
        if (this.mTmpWindow == null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -1123818872155982592L, 1, null, java.lang.Long.valueOf(getDisplayId()));
            return null;
        }
        return this.mTmpWindow;
    }

    boolean updateFocusedWindowLocked(int i, boolean z, int i2) {
        boolean z2;
        if (this.mCurrentFocus != null && this.mTransitionController.shouldKeepFocus(this.mCurrentFocus) && this.mFocusedApp != null && this.mCurrentFocus.isDescendantOf(this.mFocusedApp) && this.mCurrentFocus.isVisible() && this.mCurrentFocus.isFocusable()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS, -2192125645150932161L, 0, null, null);
            return false;
        }
        com.android.server.wm.WindowState findFocusedWindowIfNeeded = findFocusedWindowIfNeeded(i2);
        if (this.mCurrentFocus == findFocusedWindowIfNeeded) {
            return false;
        }
        if (this.mInputMethodWindow == null) {
            z2 = false;
        } else {
            z2 = this.mImeLayeringTarget != computeImeTarget(true);
            if (i != 1 && i != 3) {
                assignWindowLayers(false);
            }
            if (z2) {
                this.mWmService.mWindowsChanged = true;
                setLayoutNeeded();
                findFocusedWindowIfNeeded = findFocusedWindowIfNeeded(i2);
            }
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, 3101160328044493048L, 16, null, java.lang.String.valueOf(this.mCurrentFocus), java.lang.String.valueOf(findFocusedWindowIfNeeded), java.lang.Long.valueOf(getDisplayId()), java.lang.String.valueOf(android.os.Debug.getCallers(4)));
        com.android.server.wm.WindowState windowState = this.mCurrentFocus;
        this.mCurrentFocus = findFocusedWindowIfNeeded;
        if (findFocusedWindowIfNeeded != null) {
            this.mWinAddedSinceNullFocus.clear();
            this.mWinRemovedSinceNullFocus.clear();
            if (findFocusedWindowIfNeeded.canReceiveKeys()) {
                findFocusedWindowIfNeeded.mToken.paused = false;
            }
        }
        getDisplayPolicy().focusChangedLw(windowState, findFocusedWindowIfNeeded);
        this.mAtmService.mBackNavigationController.onFocusChanged(findFocusedWindowIfNeeded);
        if (z2 && windowState != this.mInputMethodWindow) {
            if (i == 2) {
                performLayout(true, z);
            } else if (i == 3) {
                assignWindowLayers(false);
            }
        }
        if (i != 1) {
            getInputMonitor().setInputFocusLw(findFocusedWindowIfNeeded, z);
        }
        adjustForImeIfNeeded();
        updateKeepClearAreas();
        scheduleToastWindowsTimeoutIfNeededLocked(windowState, findFocusedWindowIfNeeded);
        if (i == 2) {
            this.pendingLayoutChanges |= 8;
        }
        if (this.mWmService.mAccessibilityController.hasCallbacks()) {
            this.mWmService.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.DisplayContent.this.updateAccessibilityOnWindowFocusChanged((com.android.server.wm.AccessibilityController) obj);
                }
            }, this.mWmService.mAccessibilityController));
        }
        return true;
    }

    void updateAccessibilityOnWindowFocusChanged(com.android.server.wm.AccessibilityController accessibilityController) {
        accessibilityController.onWindowFocusChangedNot(getDisplayId());
    }

    boolean setFocusedApp(com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord != null) {
            com.android.server.wm.DisplayContent displayContent = activityRecord.getDisplayContent();
            if (displayContent != this) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append(activityRecord);
                sb.append(" is not on ");
                sb.append(getName());
                sb.append(" but ");
                sb.append(displayContent != null ? displayContent.getName() : "none");
                throw new java.lang.IllegalStateException(sb.toString());
            }
            onLastFocusedTaskDisplayAreaChanged(activityRecord.getDisplayArea());
        }
        if (this.mFocusedApp == activityRecord) {
            return false;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, 7634130879993688940L, 4, null, java.lang.String.valueOf(activityRecord), java.lang.Long.valueOf(getDisplayId()), java.lang.String.valueOf(android.os.Debug.getCallers(4)));
        com.android.server.wm.Task task = this.mFocusedApp != null ? this.mFocusedApp.getTask() : null;
        com.android.server.wm.Task task2 = activityRecord != null ? activityRecord.getTask() : null;
        this.mFocusedApp = activityRecord;
        if (task != task2) {
            if (task != null) {
                task.onAppFocusChanged(false);
            }
            if (task2 != null) {
                task2.onAppFocusChanged(true);
            }
        }
        getInputMonitor().setFocusedAppLw(activityRecord);
        updateTouchExcludeRegion();
        return true;
    }

    void onRunningActivityChanged() {
        this.mDwpcHelper.onRunningActivityChanged();
    }

    void onLastFocusedTaskDisplayAreaChanged(@android.annotation.Nullable com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        this.mOrientationRequestingTaskDisplayArea = taskDisplayArea;
    }

    @android.annotation.Nullable
    com.android.server.wm.TaskDisplayArea getOrientationRequestingTaskDisplayArea() {
        return this.mOrientationRequestingTaskDisplayArea;
    }

    void assignWindowLayers(boolean z) {
        android.os.Trace.traceBegin(32L, "assignWindowLayers");
        assignChildLayers(getSyncTransaction());
        if (z) {
            setLayoutNeeded();
        }
        scheduleAnimation();
        android.os.Trace.traceEnd(32L);
    }

    boolean destroyLeakedSurfaces() {
        this.mTmpWindow = null;
        final android.view.SurfaceControl.Transaction transaction = this.mWmService.mTransactionFactory.get();
        forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda36
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.this.lambda$destroyLeakedSurfaces$25(transaction, (com.android.server.wm.WindowState) obj);
            }
        }, false);
        transaction.apply();
        return this.mTmpWindow != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$destroyLeakedSurfaces$25(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.WindowState windowState) {
        com.android.server.wm.WindowStateAnimator windowStateAnimator = windowState.mWinAnimator;
        if (windowStateAnimator.mSurfaceController == null) {
            return;
        }
        if (!this.mWmService.mSessions.contains(windowStateAnimator.mSession)) {
            android.util.Slog.w(TAG, "LEAKED SURFACE (session doesn't exist): " + windowState + " surface=" + windowStateAnimator.mSurfaceController + " token=" + windowState.mToken + " pid=" + windowState.mSession.mPid + " uid=" + windowState.mSession.mUid);
            windowStateAnimator.destroySurface(transaction);
            this.mWmService.mForceRemoves.add(windowState);
            this.mTmpWindow = windowState;
            return;
        }
        if (windowState.mActivityRecord != null && !windowState.mActivityRecord.isClientVisible()) {
            android.util.Slog.w(TAG, "LEAKED SURFACE (app token hidden): " + windowState + " surface=" + windowStateAnimator.mSurfaceController + " token=" + windowState.mActivityRecord);
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, -4130402450005935184L, 0, null, java.lang.String.valueOf(windowState));
            windowStateAnimator.destroySurface(transaction);
            this.mTmpWindow = windowState;
        }
    }

    boolean hasAlertWindowSurfaces() {
        for (int size = this.mWmService.mSessions.size() - 1; size >= 0; size--) {
            if (this.mWmService.mSessions.valueAt(size).hasAlertWindowSurfaces(this)) {
                return true;
            }
        }
        return false;
    }

    void setInputMethodWindowLocked(com.android.server.wm.WindowState windowState) {
        this.mInputMethodWindow = windowState;
        if (this.mInputMethodWindow != null) {
            this.mAtmService.onImeWindowSetOnDisplayArea(this.mInputMethodWindow.mSession.mPid, this.mImeWindowsContainer);
        }
        this.mInsetsStateController.getImeSourceProvider().setWindowContainer(windowState, this.mDisplayPolicy.getImeSourceFrameProvider(), null);
        computeImeTarget(true);
        updateImeControlTarget();
    }

    com.android.server.wm.WindowState computeImeTarget(boolean z) {
        if (this.mInputMethodWindow == null) {
            if (z) {
                setImeLayeringTargetInner(null);
            }
            return null;
        }
        com.android.server.wm.WindowState windowState = this.mImeLayeringTarget;
        if (!canUpdateImeTarget()) {
            this.mUpdateImeRequestedWhileDeferred = true;
            return windowState;
        }
        this.mUpdateImeTarget = z;
        com.android.server.wm.WindowState window = getWindow(this.mComputeImeTargetPredicate);
        if (window == null) {
            if (z) {
                setImeLayeringTargetInner(null);
            }
            return null;
        }
        if (z) {
            setImeLayeringTargetInner(window);
        }
        return window;
    }

    void computeImeTargetIfNeeded(com.android.server.wm.ActivityRecord activityRecord) {
        if (this.mImeLayeringTarget != null && this.mImeLayeringTarget.mActivityRecord == activityRecord) {
            computeImeTarget(true);
        }
    }

    private boolean isImeControlledByApp() {
        return this.mImeInputTarget != null && this.mImeInputTarget.shouldControlIme();
    }

    boolean shouldImeAttachedToApp() {
        if (this.mImeWindowsContainer.isOrganized()) {
            return false;
        }
        return (this.mMagnificationSpec == null) && isImeControlledByApp() && this.mImeLayeringTarget != null && this.mImeLayeringTarget.mActivityRecord != null && this.mImeLayeringTarget.getWindowingMode() == 1 && this.mImeLayeringTarget.matchesDisplayAreaBounds();
    }

    boolean isImeAttachedToApp() {
        return shouldImeAttachedToApp() && this.mInputMethodSurfaceParent != null && this.mInputMethodSurfaceParent.isSameSurface(this.mImeLayeringTarget.mActivityRecord.getSurfaceControl());
    }

    com.android.server.wm.InsetsControlTarget getImeHostOrFallback(com.android.server.wm.WindowState windowState) {
        if (windowState != null && windowState.getDisplayContent().getImePolicy() == 0) {
            return windowState;
        }
        return getImeFallback();
    }

    com.android.server.wm.InsetsControlTarget getImeFallback() {
        com.android.server.wm.DisplayContent defaultDisplayContentLocked = this.mWmService.getDefaultDisplayContentLocked();
        com.android.server.wm.WindowState statusBar = defaultDisplayContentLocked.getDisplayPolicy().getStatusBar();
        return statusBar != null ? statusBar : defaultDisplayContentLocked.mRemoteInsetsControlTarget;
    }

    com.android.server.wm.InsetsControlTarget getImeTarget(int i) {
        switch (i) {
            case 0:
                return this.mImeLayeringTarget;
            case 1:
            default:
                return null;
            case 2:
                return this.mImeControlTarget;
        }
    }

    com.android.server.wm.InputTarget getImeInputTarget() {
        return this.mImeInputTarget;
    }

    int getImePolicy() {
        if (!isTrusted()) {
            return 1;
        }
        int imePolicyLocked = this.mWmService.mDisplayWindowSettings.getImePolicyLocked(this);
        if (imePolicyLocked == 1 && forceDesktopMode()) {
            return 0;
        }
        return imePolicyLocked;
    }

    boolean forceDesktopMode() {
        return (!this.mWmService.mForceDesktopModeOnExternalDisplays || this.isDefaultDisplay || isPrivate()) ? false : true;
    }

    void onShowImeRequested() {
        if (this.mInputMethodWindow != null && this.mFixedRotationLaunchingApp != null) {
            this.mInputMethodWindow.mToken.linkFixedRotationTransform(this.mFixedRotationLaunchingApp);
            if (this.mAsyncRotationController != null) {
                this.mAsyncRotationController.hideImeImmediately();
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setImeLayeringTarget(com.android.server.wm.WindowState windowState) {
        this.mImeLayeringTarget = windowState;
    }

    private void setImeLayeringTargetInner(@android.annotation.Nullable com.android.server.wm.WindowState windowState) {
        com.android.server.wm.RootDisplayArea rootDisplayArea;
        if (windowState == this.mImeLayeringTarget && this.mLastImeInputTarget == this.mImeInputTarget) {
            return;
        }
        this.mLastImeInputTarget = this.mImeInputTarget;
        if (this.mImeLayeringTarget != null && this.mImeLayeringTarget == this.mImeInputTarget) {
            boolean z = this.mImeLayeringTarget.mAnimatingExit && this.mImeLayeringTarget.mAttrs.type != 1 && this.mImeLayeringTarget.isSelfAnimating(0, 16);
            if (this.mImeLayeringTarget.inTransitionSelfOrParent() || z) {
                showImeScreenshot();
            }
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, 4464269036743635127L, 0, null, java.lang.String.valueOf(windowState));
        boolean z2 = windowState != this.mImeLayeringTarget;
        this.mImeLayeringTarget = windowState;
        if (windowState != null && !this.mImeWindowsContainer.isOrganized() && (rootDisplayArea = windowState.getRootDisplayArea()) != null && rootDisplayArea != this.mImeWindowsContainer.getRootDisplayArea() && rootDisplayArea.placeImeContainer(this.mImeWindowsContainer)) {
            if (this.mInputMethodWindow != null) {
                this.mInputMethodWindow.hide(false, false);
            }
            z2 = true;
        }
        assignWindowLayers(true);
        this.mInsetsStateController.updateAboveInsetsState(this.mInsetsStateController.getRawInsetsState().isSourceOrDefaultVisible(android.view.InsetsSource.ID_IME, android.view.WindowInsets.Type.ime()));
        updateImeControlTarget(z2);
    }

    @com.android.internal.annotations.VisibleForTesting
    void setImeInputTarget(com.android.server.wm.InputTarget inputTarget) {
        final com.android.server.wm.WindowState windowState;
        if (this.mImeTargetTokenListenerPair != null) {
            com.android.server.wm.WindowToken windowToken = this.mTokenMap.get(this.mImeTargetTokenListenerPair.first);
            if (windowToken != null) {
                windowToken.unregisterWindowContainerListener((com.android.server.wm.WindowContainerListener) this.mImeTargetTokenListenerPair.second);
            }
            this.mImeTargetTokenListenerPair = null;
        }
        this.mImeInputTarget = inputTarget;
        if (inputTarget != null && (windowState = inputTarget.getWindowState()) != null) {
            this.mImeTargetTokenListenerPair = new android.util.Pair<>(windowState.mToken.token, new com.android.server.wm.WindowContainerListener() { // from class: com.android.server.wm.DisplayContent.2
                @Override // com.android.server.wm.WindowContainerListener
                public void onVisibleRequestedChanged(boolean z) {
                    com.android.server.wm.DisplayContent.this.mWmService.dispatchImeInputTargetVisibilityChanged(windowState.mClient.asBinder(), z, windowState.mActivityRecord != null && windowState.mActivityRecord.finishing);
                }
            });
            windowState.mToken.registerWindowContainerListener((com.android.server.wm.WindowContainerListener) this.mImeTargetTokenListenerPair.second);
            this.mWmService.dispatchImeInputTargetVisibilityChanged(windowState.mClient.asBinder(), windowState.isVisible(), false);
        }
        if (refreshImeSecureFlag(getPendingTransaction())) {
            this.mWmService.requestTraversal();
        }
    }

    boolean refreshImeSecureFlag(android.view.SurfaceControl.Transaction transaction) {
        return this.mImeWindowsContainer.setCanScreenshot(transaction, this.mImeInputTarget == null || this.mImeInputTarget.canScreenshotIme());
    }

    @com.android.internal.annotations.VisibleForTesting
    void setImeControlTarget(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        this.mImeControlTarget = insetsControlTarget;
    }

    static final class ImeScreenshot {
        private android.view.SurfaceControl mImeSurface;
        private android.graphics.Point mImeSurfacePosition;
        private com.android.server.wm.WindowState mImeTarget;
        private android.view.SurfaceControl.Builder mSurfaceBuilder;

        ImeScreenshot(android.view.SurfaceControl.Builder builder, @android.annotation.NonNull com.android.server.wm.WindowState windowState) {
            this.mSurfaceBuilder = builder;
            this.mImeTarget = windowState;
        }

        com.android.server.wm.WindowState getImeTarget() {
            return this.mImeTarget;
        }

        @com.android.internal.annotations.VisibleForTesting
        android.view.SurfaceControl getImeScreenshotSurface() {
            return this.mImeSurface;
        }

        private android.view.SurfaceControl createImeSurface(android.window.ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer, android.view.SurfaceControl.Transaction transaction) {
            android.view.SurfaceControl surfaceControl;
            android.hardware.HardwareBuffer hardwareBuffer = screenshotHardwareBuffer.getHardwareBuffer();
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, 4835192778854186097L, 0, null, java.lang.String.valueOf(this.mImeTarget), java.lang.String.valueOf(hardwareBuffer.getWidth()), java.lang.String.valueOf(hardwareBuffer.getHeight()));
            com.android.server.wm.WindowState windowState = this.mImeTarget.getDisplayContent().mInputMethodWindow;
            com.android.server.wm.ActivityRecord activityRecord = this.mImeTarget.mActivityRecord;
            if (this.mImeTarget.mAttrs.type == 1) {
                surfaceControl = activityRecord.getSurfaceControl();
            } else {
                surfaceControl = this.mImeTarget.getSurfaceControl();
            }
            android.view.SurfaceControl build = this.mSurfaceBuilder.setName("IME-snapshot-surface").setBLASTLayer().setFormat(hardwareBuffer.getFormat()).setParent(surfaceControl).setCallsite("DisplayContent.attachAndShowImeScreenshotOnTarget").build();
            com.android.server.wm.InputMonitor.setTrustedOverlayInputInfo(build, transaction, windowState.getDisplayId(), "IME-snapshot-surface");
            transaction.setBuffer(build, hardwareBuffer);
            transaction.setColorSpace(activityRecord.mSurfaceControl, android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB));
            transaction.setLayer(build, 1);
            android.graphics.Point point = new android.graphics.Point(windowState.getFrame().left, windowState.getFrame().top);
            if (surfaceControl == activityRecord.getSurfaceControl()) {
                transaction.setPosition(build, point.x, point.y);
            } else {
                point.offset(-this.mImeTarget.getFrame().left, -this.mImeTarget.getFrame().top);
                point.offset(this.mImeTarget.mAttrs.surfaceInsets.left, this.mImeTarget.mAttrs.surfaceInsets.top);
                transaction.setPosition(build, point.x, point.y);
            }
            this.mImeSurfacePosition = point;
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, 2408509162360028352L, 5, null, java.lang.Long.valueOf(point.x), java.lang.Long.valueOf(point.y));
            return build;
        }

        private void removeImeSurface(android.view.SurfaceControl.Transaction transaction) {
            if (this.mImeSurface != null) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, 2005731931732324688L, 0, null, java.lang.String.valueOf(android.os.Debug.getCallers(6)));
                transaction.remove(this.mImeSurface);
                this.mImeSurface = null;
            }
            if (android.view.inputmethod.ImeTracker.DEBUG_IME_VISIBILITY) {
                android.util.EventLog.writeEvent(com.android.server.wm.EventLogTags.IMF_REMOVE_IME_SCREENSHOT, this.mImeTarget.toString());
            }
        }

        void attachAndShow(android.view.SurfaceControl.Transaction transaction) {
            android.window.ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer;
            com.android.server.wm.DisplayContent displayContent = this.mImeTarget.getDisplayContent();
            com.android.server.wm.Task task = this.mImeTarget.getTask();
            boolean z = (this.mImeSurface != null && this.mImeSurface.getWidth() == displayContent.mInputMethodWindow.getFrame().width() && this.mImeSurface.getHeight() == displayContent.mInputMethodWindow.getFrame().height()) ? false : true;
            if (task != null && !task.isActivityTypeHomeOrRecents()) {
                if (z) {
                    screenshotHardwareBuffer = displayContent.mWmService.mTaskSnapshotController.snapshotImeFromAttachedTask(task);
                } else {
                    screenshotHardwareBuffer = null;
                }
                if (screenshotHardwareBuffer != null) {
                    removeImeSurface(transaction);
                    this.mImeSurface = createImeSurface(screenshotHardwareBuffer, transaction);
                }
            }
            boolean z2 = this.mImeSurface != null && this.mImeSurface.isValid();
            if (z2 && displayContent.getInsetsStateController().getImeSourceProvider().isImeShowing()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, -6495118720675662641L, 0, null, java.lang.String.valueOf(this.mImeTarget), java.lang.String.valueOf(android.os.Debug.getCallers(6)));
                transaction.show(this.mImeSurface);
                if (android.view.inputmethod.ImeTracker.DEBUG_IME_VISIBILITY) {
                    android.util.EventLog.writeEvent(com.android.server.wm.EventLogTags.IMF_SHOW_IME_SCREENSHOT, this.mImeTarget.toString(), java.lang.Integer.valueOf(displayContent.mInputMethodWindow.mTransitFlags), this.mImeSurfacePosition.toString());
                    return;
                }
                return;
            }
            if (!z2) {
                removeImeSurface(transaction);
            }
        }

        void detach(android.view.SurfaceControl.Transaction transaction) {
            removeImeSurface(transaction);
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
            sb.append("ImeScreenshot{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(" imeTarget=" + this.mImeTarget);
            sb.append(" surface=" + this.mImeSurface);
            sb.append('}');
            return sb.toString();
        }
    }

    private void attachImeScreenshotOnTargetIfNeeded() {
        if (shouldImeAttachedToApp() && this.mWmService.mPolicy.isScreenOn() && this.mInputMethodWindow != null && this.mInputMethodWindow.isVisible()) {
            attachImeScreenshotOnTarget(this.mImeLayeringTarget);
        }
    }

    private void attachImeScreenshotOnTarget(com.android.server.wm.WindowState windowState) {
        attachImeScreenshotOnTarget(windowState, false);
    }

    private void attachImeScreenshotOnTarget(com.android.server.wm.WindowState windowState, boolean z) {
        android.view.SurfaceControl.Transaction pendingTransaction = getPendingTransaction();
        removeImeSurfaceImmediately();
        this.mImeScreenshot = new com.android.server.wm.DisplayContent.ImeScreenshot(this.mWmService.mSurfaceControlFactory.apply(null), windowState);
        this.mImeScreenshot.attachAndShow(pendingTransaction);
        if (this.mInputMethodWindow != null && z) {
            this.mInputMethodWindow.hide(false, false);
        }
    }

    void showImeScreenshot() {
        attachImeScreenshotOnTargetIfNeeded();
    }

    @com.android.internal.annotations.VisibleForTesting
    void showImeScreenshot(com.android.server.wm.WindowState windowState) {
        attachImeScreenshotOnTarget(windowState, true);
    }

    void removeImeSurfaceByTarget(com.android.server.wm.WindowContainer windowContainer) {
        if (this.mImeScreenshot == null || windowContainer == null) {
            return;
        }
        if (windowContainer.asWindowState() != null && windowContainer.asWindowState().mAttrs.type == 3) {
            return;
        }
        final com.android.server.wm.WindowState imeTarget = this.mImeScreenshot.getImeTarget();
        if (windowContainer == imeTarget || windowContainer.getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeImeSurfaceByTarget$26;
                lambda$removeImeSurfaceByTarget$26 = com.android.server.wm.DisplayContent.lambda$removeImeSurfaceByTarget$26(com.android.server.wm.WindowState.this, obj);
                return lambda$removeImeSurfaceByTarget$26;
            }
        }) != null) {
            removeImeSurfaceImmediately();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeImeSurfaceByTarget$26(com.android.server.wm.WindowState windowState, java.lang.Object obj) {
        return obj == windowState;
    }

    void removeImeSurfaceImmediately() {
        if (this.mImeScreenshot != null) {
            this.mImeScreenshot.detach(getSyncTransaction());
            this.mImeScreenshot = null;
        }
    }

    void updateImeInputAndControlTarget(com.android.server.wm.InputTarget inputTarget) {
        if (this.mImeInputTarget != inputTarget) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, -4354595179162289537L, 0, null, java.lang.String.valueOf(inputTarget));
            setImeInputTarget(inputTarget);
            this.mInsetsStateController.updateAboveInsetsState(this.mInsetsStateController.getRawInsetsState().isSourceOrDefaultVisible(android.view.InsetsSource.ID_IME, android.view.WindowInsets.Type.ime()));
            updateImeControlTarget((this.mImeControlTarget != this.mRemoteInsetsControlTarget || this.mInputMethodSurfaceParent == null || this.mInputMethodSurfaceParent.isSameSurface(this.mImeWindowsContainer.getParent().mSurfaceControl)) ? false : true);
        }
    }

    boolean onImeInsetsClientVisibilityUpdate() {
        final boolean[] zArr = new boolean[1];
        com.android.server.wm.ActivityRecord activityRecord = this.mImeInputTarget != null ? this.mImeInputTarget.getActivityRecord() : null;
        if ((this.mImeInputTarget != this.mLastImeInputTarget) || (activityRecord != null && activityRecord.isVisibleRequested() && activityRecord.mImeInsetsFrozenUntilStartInput)) {
            forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda39
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.wm.DisplayContent.lambda$onImeInsetsClientVisibilityUpdate$27(zArr, (com.android.server.wm.ActivityRecord) obj);
                }
            });
        }
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$onImeInsetsClientVisibilityUpdate$27(boolean[] zArr, com.android.server.wm.ActivityRecord activityRecord) {
        if (activityRecord.mImeInsetsFrozenUntilStartInput && activityRecord.isVisibleRequested()) {
            activityRecord.mImeInsetsFrozenUntilStartInput = false;
            zArr[0] = true;
        }
    }

    void updateImeControlTarget() {
        updateImeControlTarget(false);
    }

    void updateImeControlTarget(boolean z) {
        com.android.server.wm.InsetsControlTarget insetsControlTarget = this.mImeControlTarget;
        this.mImeControlTarget = computeImeControlTarget();
        this.mInsetsStateController.onImeControlTargetChanged(this.mImeControlTarget);
        if ((insetsControlTarget != this.mImeControlTarget) || z) {
            updateImeParent();
        }
        com.android.server.wm.WindowState asWindowOrNull = com.android.server.wm.InsetsControlTarget.asWindowOrNull(this.mImeControlTarget);
        final android.os.IBinder asBinder = asWindowOrNull != null ? asWindowOrNull.mClient.asBinder() : null;
        this.mWmService.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda56
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.wm.DisplayContent.lambda$updateImeControlTarget$28(asBinder);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateImeControlTarget$28(android.os.IBinder iBinder) {
        com.android.server.inputmethod.InputMethodManagerInternal.get().reportImeControl(iBinder);
    }

    void updateImeParent() {
        android.view.SurfaceControl lastRelativeLayer;
        if (this.mImeWindowsContainer.isOrganized()) {
            this.mInputMethodSurfaceParent = null;
            return;
        }
        android.view.SurfaceControl computeImeParent = computeImeParent();
        if (computeImeParent != null && computeImeParent != this.mInputMethodSurfaceParent) {
            this.mInputMethodSurfaceParent = computeImeParent;
            getSyncTransaction().reparent(this.mImeWindowsContainer.mSurfaceControl, computeImeParent);
            if (android.view.inputmethod.ImeTracker.DEBUG_IME_VISIBILITY) {
                android.util.EventLog.writeEvent(com.android.server.wm.EventLogTags.IMF_UPDATE_IME_PARENT, computeImeParent.toString());
            }
            assignRelativeLayerForIme(getSyncTransaction(), true);
            scheduleAnimation();
            this.mWmService.mH.post(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.DisplayContent.this.lambda$updateImeParent$29();
                }
            });
            return;
        }
        if (this.mImeControlTarget != null && this.mImeControlTarget == this.mImeLayeringTarget && (lastRelativeLayer = this.mImeWindowsContainer.getLastRelativeLayer()) != this.mImeLayeringTarget.mSurfaceControl) {
            assignRelativeLayerForIme(getSyncTransaction(), false);
            if (lastRelativeLayer != this.mImeWindowsContainer.getLastRelativeLayer()) {
                scheduleAnimation();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateImeParent$29() {
        com.android.server.inputmethod.InputMethodManagerInternal.get().onImeParentChanged(getDisplayId());
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.wm.InsetsControlTarget computeImeControlTarget() {
        if (this.mImeInputTarget == null) {
            return null;
        }
        com.android.server.wm.WindowState windowState = this.mImeInputTarget.getWindowState();
        if ((!isImeControlledByApp() && this.mRemoteInsetsControlTarget != null) || getImeHostOrFallback(windowState) == this.mRemoteInsetsControlTarget) {
            return this.mRemoteInsetsControlTarget;
        }
        return windowState;
    }

    @com.android.internal.annotations.VisibleForTesting
    android.view.SurfaceControl computeImeParent() {
        if (!com.android.server.wm.ImeTargetVisibilityPolicy.canComputeImeParent(this.mImeLayeringTarget, this.mImeInputTarget)) {
            return null;
        }
        if (shouldImeAttachedToApp()) {
            return this.mImeLayeringTarget.mActivityRecord.getSurfaceControl();
        }
        if (this.mImeWindowsContainer.getParent() != null) {
            return this.mImeWindowsContainer.getParent().getSurfaceControl();
        }
        return null;
    }

    void setLayoutNeeded() {
        this.mLayoutNeeded = true;
    }

    private void clearLayoutNeeded() {
        this.mLayoutNeeded = false;
    }

    boolean isLayoutNeeded() {
        return this.mLayoutNeeded;
    }

    void dumpTokens(java.io.PrintWriter printWriter, boolean z) {
        if (this.mTokenMap.isEmpty()) {
            return;
        }
        printWriter.println("  Display #" + this.mDisplayId);
        printWriter.println("    mInTouchMode=" + this.mInTouchMode);
        for (com.android.server.wm.WindowToken windowToken : this.mTokenMap.values()) {
            printWriter.print("  ");
            printWriter.print(windowToken);
            if (z) {
                printWriter.println(':');
                windowToken.dump(printWriter, "    ", z);
            } else {
                printWriter.println();
            }
        }
        if (!this.mOpeningApps.isEmpty() || !this.mClosingApps.isEmpty() || !this.mChangingContainers.isEmpty()) {
            printWriter.println();
            if (this.mOpeningApps.size() > 0) {
                printWriter.print("  mOpeningApps=");
                printWriter.println(this.mOpeningApps);
            }
            if (this.mClosingApps.size() > 0) {
                printWriter.print("  mClosingApps=");
                printWriter.println(this.mClosingApps);
            }
            if (this.mChangingContainers.size() > 0) {
                printWriter.print("  mChangingApps=");
                printWriter.println(this.mChangingContainers);
            }
        }
        this.mUnknownAppVisibilityController.dump(printWriter, "  ");
    }

    void dumpWindowAnimators(final java.io.PrintWriter printWriter, final java.lang.String str) {
        final int[] iArr = new int[1];
        forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.lambda$dumpWindowAnimators$30(printWriter, str, iArr, (com.android.server.wm.WindowState) obj);
            }
        }, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$dumpWindowAnimators$30(java.io.PrintWriter printWriter, java.lang.String str, int[] iArr, com.android.server.wm.WindowState windowState) {
        printWriter.println(str + "Window #" + iArr[0] + ": " + windowState.mWinAnimator);
        iArr[0] = iArr[0] + 1;
    }

    boolean shouldWaitForSystemDecorWindowsOnBoot() {
        if (!this.isDefaultDisplay && !supportsSystemDecorations()) {
            return false;
        }
        final android.util.SparseBooleanArray sparseBooleanArray = new android.util.SparseBooleanArray();
        sparseBooleanArray.put(2040, true);
        if (getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda37
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$shouldWaitForSystemDecorWindowsOnBoot$31;
                lambda$shouldWaitForSystemDecorWindowsOnBoot$31 = com.android.server.wm.DisplayContent.this.lambda$shouldWaitForSystemDecorWindowsOnBoot$31(sparseBooleanArray, (com.android.server.wm.WindowState) obj);
                return lambda$shouldWaitForSystemDecorWindowsOnBoot$31;
            }
        }) != null) {
            return true;
        }
        boolean z = this.mWmService.mContext.getResources().getBoolean(android.R.bool.config_enableTelephonyTimeZoneDetection) && this.mWmService.mContext.getResources().getBoolean(android.R.bool.config_checkWallpaperAtBoot);
        boolean z2 = sparseBooleanArray.get(2021);
        boolean z3 = sparseBooleanArray.get(1);
        boolean z4 = sparseBooleanArray.get(2013);
        boolean z5 = sparseBooleanArray.get(2040);
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SCREEN_ON, 5683557566110711213L, 16383, null, java.lang.Boolean.valueOf(this.mWmService.mSystemBooted), java.lang.Boolean.valueOf(this.mWmService.mShowingBootMessages), java.lang.Boolean.valueOf(z2), java.lang.Boolean.valueOf(z3), java.lang.Boolean.valueOf(z4), java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(z5));
        if (this.mWmService.mSystemBooted || z2) {
            return this.mWmService.mSystemBooted && (!(z3 || z5) || (z && !z4));
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public /* synthetic */ boolean lambda$shouldWaitForSystemDecorWindowsOnBoot$31(android.util.SparseBooleanArray sparseBooleanArray, com.android.server.wm.WindowState windowState) {
        boolean z = windowState.isVisible() && !windowState.mObscured;
        boolean isDrawn = windowState.isDrawn();
        if (z && !isDrawn) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BOOT, 2432701541536053712L, 1, null, java.lang.Long.valueOf(windowState.mAttrs.type));
            return true;
        }
        if (isDrawn) {
            switch (windowState.mAttrs.type) {
                case 1:
                case 2013:
                case 2021:
                    sparseBooleanArray.put(windowState.mAttrs.type, true);
                    break;
                case 2040:
                    sparseBooleanArray.put(2040, this.mWmService.mPolicy.isKeyguardDrawnLw());
                    break;
            }
        }
        return false;
    }

    void updateWindowsForAnimator() {
        forAllWindows(this.mUpdateWindowsForAnimator, true);
        if (this.mAsyncRotationController != null) {
            this.mAsyncRotationController.updateTargetWindows();
        }
    }

    boolean isInputMethodClientFocus(int i, int i2) {
        com.android.server.wm.WindowState computeImeTarget = computeImeTarget(false);
        return computeImeTarget != null && computeImeTarget.mSession.mUid == i && computeImeTarget.mSession.mPid == i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$hasSecureWindowOnScreen$32(com.android.server.wm.WindowState windowState) {
        return windowState.isOnScreen() && windowState.isSecureLocked();
    }

    boolean hasSecureWindowOnScreen() {
        return getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda29
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$hasSecureWindowOnScreen$32;
                lambda$hasSecureWindowOnScreen$32 = com.android.server.wm.DisplayContent.lambda$hasSecureWindowOnScreen$32((com.android.server.wm.WindowState) obj);
                return lambda$hasSecureWindowOnScreen$32;
            }
        }) != null;
    }

    void onWindowFreezeTimeout() {
        android.util.Slog.w(TAG, "Window freeze timeout expired.");
        this.mWmService.mWindowsFreezingScreen = 2;
        forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.this.lambda$onWindowFreezeTimeout$33((com.android.server.wm.WindowState) obj);
            }
        }, true);
        this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onWindowFreezeTimeout$33(com.android.server.wm.WindowState windowState) {
        if (!windowState.getOrientationChanging()) {
            return;
        }
        windowState.orientationChangeTimedOut();
        windowState.mLastFreezeDuration = (int) (android.os.SystemClock.elapsedRealtime() - this.mWmService.mDisplayFreezeTime);
        android.util.Slog.w(TAG, "Force clearing orientation change: " + windowState);
    }

    void onWindowAnimationFinished(@android.annotation.NonNull com.android.server.wm.WindowContainer windowContainer, int i) {
        if (this.mImeScreenshot != null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, -124113386733162358L, 0, null, java.lang.String.valueOf(windowContainer), java.lang.String.valueOf(com.android.server.wm.SurfaceAnimator.animationTypeToString(i)), java.lang.String.valueOf(this.mImeScreenshot), java.lang.String.valueOf(this.mImeScreenshot.getImeTarget()));
        }
        if ((i & 25) != 0) {
            removeImeSurfaceByTarget(windowContainer);
        }
    }

    void applySurfaceChangesTransaction() {
        com.android.server.wm.WindowSurfacePlacer windowSurfacePlacer = this.mWmService.mWindowPlacerLocked;
        beginHoldScreenUpdate();
        this.mTmpUpdateAllDrawn.clear();
        if ((this.pendingLayoutChanges & 4) != 0) {
            this.mWallpaperController.adjustWallpaperWindows();
        }
        if ((this.pendingLayoutChanges & 2) != 0 && updateOrientation()) {
            setLayoutNeeded();
            sendNewConfiguration();
        }
        if ((this.pendingLayoutChanges & 1) != 0) {
            setLayoutNeeded();
        }
        performLayout(true, false);
        this.pendingLayoutChanges = 0;
        android.os.Trace.traceBegin(32L, "applyPostLayoutPolicy");
        try {
            this.mDisplayPolicy.beginPostLayoutPolicyLw();
            forAllWindows(this.mApplyPostLayoutPolicy, true);
            this.mDisplayPolicy.finishPostLayoutPolicyLw();
            android.os.Trace.traceEnd(32L);
            this.mInsetsStateController.onPostLayout();
            this.mTmpApplySurfaceChangesTransactionState.reset();
            android.os.Trace.traceBegin(32L, "applyWindowSurfaceChanges");
            try {
                forAllWindows(this.mApplySurfaceChangesTransaction, true);
                android.os.Trace.traceEnd(32L);
                prepareSurfaces();
                this.mInsetsStateController.getImeSourceProvider().checkShowImePostLayout();
                this.mLastHasContent = this.mTmpApplySurfaceChangesTransactionState.displayHasContent;
                if (!inTransition() && !this.mDisplayRotation.isRotatingSeamlessly()) {
                    this.mWmService.mDisplayManagerInternal.setDisplayProperties(this.mDisplayId, this.mLastHasContent, this.mTmpApplySurfaceChangesTransactionState.preferredRefreshRate, this.mTmpApplySurfaceChangesTransactionState.preferredModeId, this.mTmpApplySurfaceChangesTransactionState.preferredMinRefreshRate, this.mTmpApplySurfaceChangesTransactionState.preferredMaxRefreshRate, this.mTmpApplySurfaceChangesTransactionState.preferMinimalPostProcessing, this.mTmpApplySurfaceChangesTransactionState.disableHdrConversion, true);
                }
                updateRecording();
                boolean isWallpaperVisible = this.mWallpaperController.isWallpaperVisible();
                if (isWallpaperVisible != this.mLastWallpaperVisible) {
                    this.mLastWallpaperVisible = isWallpaperVisible;
                    this.mWmService.mWallpaperVisibilityListeners.notifyWallpaperVisibilityChanged(this);
                }
                while (!this.mTmpUpdateAllDrawn.isEmpty()) {
                    this.mTmpUpdateAllDrawn.removeLast().updateAllDrawn();
                }
                finishHoldScreenUpdate();
            } finally {
            }
        } finally {
        }
    }

    private void getBounds(android.graphics.Rect rect, int i) {
        getBounds(rect);
        int deltaRotation = android.util.RotationUtils.deltaRotation(this.mDisplayInfo.rotation, i);
        if (deltaRotation == 1 || deltaRotation == 3) {
            rect.set(0, 0, rect.height(), rect.width());
        }
    }

    int getNaturalOrientation() {
        return this.mBaseDisplayWidth <= this.mBaseDisplayHeight ? 1 : 2;
    }

    int getNaturalConfigurationOrientation() {
        android.content.res.Configuration configuration = getConfiguration();
        if (configuration.windowConfiguration.getDisplayRotation() == 0) {
            return configuration.orientation;
        }
        android.graphics.Rect rect = this.mDisplayPolicy.getDecorInsetsInfo(0, this.mBaseDisplayWidth, this.mBaseDisplayHeight).mConfigFrame;
        return rect.width() <= rect.height() ? 1 : 2;
    }

    void performLayout(boolean z, boolean z2) {
        android.os.Trace.traceBegin(32L, "performLayout");
        try {
            performLayoutNoTrace(z, z2);
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    private void performLayoutNoTrace(boolean z, boolean z2) {
        if (!isLayoutNeeded()) {
            return;
        }
        clearLayoutNeeded();
        int i = this.mLayoutSeq + 1;
        if (i < 0) {
            i = 0;
        }
        this.mLayoutSeq = i;
        this.mTmpInitial = z;
        forAllWindows(this.mPerformLayout, true);
        forAllWindows(this.mPerformLayoutAttached, true);
        this.mInputMonitor.setUpdateInputWindowsNeededLw();
        if (z2) {
            this.mInputMonitor.updateInputWindowsLw(false);
        }
    }

    android.window.ScreenCapture.LayerCaptureArgs getLayerCaptureArgs() {
        if (!this.mWmService.mPolicy.isScreenOn()) {
            return null;
        }
        getBounds(this.mTmpRect);
        this.mTmpRect.offsetTo(0, 0);
        return new android.window.ScreenCapture.LayerCaptureArgs.Builder(getSurfaceControl()).setSourceCrop(this.mTmpRect).build();
    }

    @Override // com.android.server.wm.WindowContainer
    void onDescendantOverrideConfigurationChanged() {
        setLayoutNeeded();
        this.mWmService.requestTraversal();
    }

    @Override // com.android.server.wm.WindowContainer
    boolean okToDisplay() {
        return okToDisplay(false, false);
    }

    boolean okToDisplay(boolean z, boolean z2) {
        if (this.mDisplayId != 0) {
            return this.mDisplayInfo.state == 2;
        }
        if ((!this.mWmService.mDisplayFrozen || z) && this.mWmService.mDisplayEnabled) {
            return z2 || this.mWmService.mPolicy.isScreenOn();
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean okToAnimate(boolean z, boolean z2) {
        return okToDisplay(z, z2) && (this.mDisplayId != 0 || this.mWmService.mPolicy.okToAnimate(z2)) && (z || this.mDisplayPolicy.isScreenOnFully());
    }

    static final class TaskForResizePointSearchResult implements java.util.function.Predicate<com.android.server.wm.Task> {
        private int delta;
        private android.graphics.Rect mTmpRect = new android.graphics.Rect();
        private com.android.server.wm.Task taskForResize;
        private int x;
        private int y;

        TaskForResizePointSearchResult() {
        }

        com.android.server.wm.Task process(com.android.server.wm.WindowContainer windowContainer, int i, int i2, int i3) {
            this.taskForResize = null;
            this.x = i;
            this.y = i2;
            this.delta = i3;
            this.mTmpRect.setEmpty();
            windowContainer.forAllTasks(this);
            return this.taskForResize;
        }

        @Override // java.util.function.Predicate
        public boolean test(com.android.server.wm.Task task) {
            if (!task.getRootTask().getWindowConfiguration().canResizeTask() || task.getWindowingMode() == 1 || task.isOrganized()) {
                return true;
            }
            task.getDimBounds(this.mTmpRect);
            this.mTmpRect.inset(-this.delta, -this.delta);
            if (this.mTmpRect.contains(this.x, this.y)) {
                this.mTmpRect.inset(this.delta, this.delta);
                if (this.mTmpRect.contains(this.x, this.y)) {
                    return true;
                }
                this.taskForResize = task;
                return true;
            }
            return false;
        }
    }

    private static final class ApplySurfaceChangesTransactionState {
        public boolean disableHdrConversion;
        public boolean displayHasContent;
        public boolean obscured;
        public boolean preferMinimalPostProcessing;
        public float preferredMaxRefreshRate;
        public float preferredMinRefreshRate;
        public int preferredModeId;
        public float preferredRefreshRate;
        public boolean syswin;

        private ApplySurfaceChangesTransactionState() {
        }

        void reset() {
            this.displayHasContent = false;
            this.obscured = false;
            this.syswin = false;
            this.preferMinimalPostProcessing = false;
            this.preferredRefreshRate = 0.0f;
            this.preferredModeId = 0;
            this.preferredMinRefreshRate = 0.0f;
            this.preferredMaxRefreshRate = 0.0f;
            this.disableHdrConversion = false;
        }
    }

    private static class ImeContainer extends com.android.server.wm.DisplayArea.Tokens {
        boolean mNeedsLayer;

        ImeContainer(com.android.server.wm.WindowManagerService windowManagerService) {
            super(windowManagerService, com.android.server.wm.DisplayArea.Type.ABOVE_TASKS, "ImeContainer", 8);
            this.mNeedsLayer = false;
        }

        public void setNeedsLayer() {
            this.mNeedsLayer = true;
        }

        @Override // com.android.server.wm.DisplayArea.Tokens, com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer
        int getOrientation(int i) {
            if (shouldIgnoreOrientationRequest(i)) {
                return -2;
            }
            return i;
        }

        @Override // com.android.server.wm.WindowContainer
        void updateAboveInsetsState(android.view.InsetsState insetsState, android.util.SparseArray<android.view.InsetsSource> sparseArray, android.util.ArraySet<com.android.server.wm.WindowState> arraySet) {
            if (skipImeWindowsDuringTraversal(this.mDisplayContent)) {
                return;
            }
            super.updateAboveInsetsState(insetsState, sparseArray, arraySet);
        }

        @Override // com.android.server.wm.WindowContainer
        boolean forAllWindows(com.android.internal.util.ToBooleanFunction<com.android.server.wm.WindowState> toBooleanFunction, boolean z) {
            if (skipImeWindowsDuringTraversal(this.mDisplayContent)) {
                return false;
            }
            return super.forAllWindows(toBooleanFunction, z);
        }

        private static boolean skipImeWindowsDuringTraversal(com.android.server.wm.DisplayContent displayContent) {
            return (displayContent.mImeLayeringTarget == null || displayContent.mWmService.mDisplayFrozen) ? false : true;
        }

        boolean forAllWindowForce(com.android.internal.util.ToBooleanFunction<com.android.server.wm.WindowState> toBooleanFunction, boolean z) {
            return super.forAllWindows(toBooleanFunction, z);
        }

        @Override // com.android.server.wm.WindowContainer
        void assignLayer(android.view.SurfaceControl.Transaction transaction, int i) {
            if (!this.mNeedsLayer) {
                return;
            }
            super.assignLayer(transaction, i);
            this.mNeedsLayer = false;
        }

        @Override // com.android.server.wm.WindowContainer
        void assignRelativeLayer(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, int i, boolean z) {
            if (!this.mNeedsLayer) {
                return;
            }
            super.assignRelativeLayer(transaction, surfaceControl, i, z);
            this.mNeedsLayer = false;
        }

        @Override // com.android.server.wm.DisplayArea
        void setOrganizer(android.window.IDisplayAreaOrganizer iDisplayAreaOrganizer, boolean z) {
            super.setOrganizer(iDisplayAreaOrganizer, z);
            this.mDisplayContent.updateImeParent();
            if (iDisplayAreaOrganizer != null) {
                android.view.SurfaceControl parentSurfaceControl = getParentSurfaceControl();
                if (this.mSurfaceControl != null && parentSurfaceControl != null) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, -1556099709547629010L, 0, null, java.lang.String.valueOf(parentSurfaceControl));
                    getPendingTransaction().reparent(this.mSurfaceControl, parentSurfaceControl);
                } else {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_IME, 1119786654111970652L, 0, null, java.lang.String.valueOf(this.mSurfaceControl), java.lang.String.valueOf(parentSurfaceControl));
                }
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    android.view.SurfaceSession getSession() {
        return this.mSession;
    }

    @Override // com.android.server.wm.WindowContainer
    android.view.SurfaceControl.Builder makeChildSurface(com.android.server.wm.WindowContainer windowContainer) {
        android.view.SurfaceControl.Builder containerLayer = this.mWmService.makeSurfaceBuilder(windowContainer != null ? windowContainer.getSession() : getSession()).setContainerLayer();
        if (windowContainer == null) {
            return containerLayer;
        }
        return containerLayer.setName(windowContainer.getName()).setParent(this.mSurfaceControl);
    }

    android.view.SurfaceControl.Builder makeOverlay() {
        return this.mWmService.makeSurfaceBuilder(this.mSession).setParent(getOverlayLayer());
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public android.view.SurfaceControl.Builder makeAnimationLeash() {
        return this.mWmService.makeSurfaceBuilder(this.mSession).setParent(this.mSurfaceControl).setContainerLayer();
    }

    void reparentToOverlay(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        transaction.reparent(surfaceControl, getOverlayLayer());
    }

    void applyMagnificationSpec(android.view.MagnificationSpec magnificationSpec) {
        if (magnificationSpec.scale != 1.0d) {
            this.mMagnificationSpec = magnificationSpec;
        } else {
            this.mMagnificationSpec = null;
        }
        updateImeParent();
        if (magnificationSpec.scale != 1.0d) {
            applyMagnificationSpec(getPendingTransaction(), magnificationSpec);
        } else {
            clearMagnificationSpec(getPendingTransaction());
        }
        getPendingTransaction().apply();
    }

    void reapplyMagnificationSpec() {
        if (this.mMagnificationSpec != null) {
            applyMagnificationSpec(getPendingTransaction(), this.mMagnificationSpec);
        }
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    void onParentChanged(com.android.server.wm.ConfigurationContainer configurationContainer, com.android.server.wm.ConfigurationContainer configurationContainer2) {
        if (!isReady()) {
            this.mDisplayReady = true;
            if (this.mWmService.mDisplayManagerInternal != null) {
                setDisplayInfoOverride();
                configureDisplayPolicy();
            }
            if (!this.isDefaultDisplay) {
                this.mDisplayRotation.updateRotationUnchecked(true);
            }
            reconfigureDisplayLocked();
            onRequestedOverrideConfigurationChanged(getRequestedOverrideConfiguration());
            this.mWmService.mDisplayNotificationController.dispatchDisplayAdded(this);
            this.mWmService.mWindowContextListenerController.registerWindowContainerListener(this.mAtmService.getProcessController(getDisplayUiContext().getIApplicationThread()), getDisplayUiContext().getWindowContextToken(), this, -1, null);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void assignChildLayers(android.view.SurfaceControl.Transaction transaction) {
        assignRelativeLayerForIme(transaction, false);
        super.assignChildLayers(transaction);
    }

    private void assignRelativeLayerForIme(android.view.SurfaceControl.Transaction transaction, boolean z) {
        if (this.mImeWindowsContainer.isOrganized()) {
            return;
        }
        this.mImeWindowsContainer.setNeedsLayer();
        com.android.server.wm.WindowState windowState = this.mImeLayeringTarget;
        if (windowState != null && (windowState.mActivityRecord == null || !windowState.mActivityRecord.hasStartingWindow())) {
            if ((windowState.getSurfaceControl() == null || windowState.mToken != ((this.mImeControlTarget == null || this.mImeControlTarget.getWindow() == null) ? null : this.mImeControlTarget.getWindow().mToken) || windowState.inMultiWindowMode()) ? false : true) {
                this.mImeWindowsContainer.assignRelativeLayer(transaction, windowState.getSurfaceControl(), 1, z);
                return;
            }
        }
        if (this.mInputMethodSurfaceParent != null) {
            this.mImeWindowsContainer.assignRelativeLayer(transaction, this.mInputMethodSurfaceParent, 1, z);
        }
    }

    void assignRelativeLayerForImeTargetChild(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.WindowContainer windowContainer) {
        windowContainer.assignRelativeLayer(transaction, this.mImeWindowsContainer.getSurfaceControl(), 1);
    }

    @Override // com.android.server.wm.DisplayArea.Dimmable, com.android.server.wm.WindowContainer
    void prepareSurfaces() {
        android.os.Trace.traceBegin(32L, "prepareSurfaces");
        try {
            super.prepareSurfaces();
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    void deferUpdateImeTarget() {
        if (this.mDeferUpdateImeTargetCount == 0) {
            this.mUpdateImeRequestedWhileDeferred = false;
        }
        this.mDeferUpdateImeTargetCount++;
    }

    void continueUpdateImeTarget() {
        if (this.mDeferUpdateImeTargetCount == 0) {
            return;
        }
        this.mDeferUpdateImeTargetCount--;
        if (this.mDeferUpdateImeTargetCount == 0 && this.mUpdateImeRequestedWhileDeferred) {
            computeImeTarget(true);
        }
    }

    private boolean canUpdateImeTarget() {
        return this.mDeferUpdateImeTargetCount == 0;
    }

    com.android.server.wm.InputMonitor getInputMonitor() {
        return this.mInputMonitor;
    }

    boolean getLastHasContent() {
        return this.mLastHasContent;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setLastHasContent() {
        this.mLastHasContent = true;
    }

    void registerPointerEventListener(@android.annotation.NonNull android.view.WindowManagerPolicyConstants.PointerEventListener pointerEventListener) {
        this.mPointerEventDispatcher.registerInputEventListener(pointerEventListener);
    }

    void unregisterPointerEventListener(@android.annotation.NonNull android.view.WindowManagerPolicyConstants.PointerEventListener pointerEventListener) {
        this.mPointerEventDispatcher.unregisterInputEventListener(pointerEventListener);
    }

    void transferAppTransitionFrom(com.android.server.wm.DisplayContent displayContent) {
        if (this.mAppTransition.transferFrom(displayContent.mAppTransition) && okToAnimate()) {
            this.mSkipAppTransitionAnimation = false;
        }
    }

    @java.lang.Deprecated
    void prepareAppTransition(int i) {
        prepareAppTransition(i, 0);
    }

    @java.lang.Deprecated
    void prepareAppTransition(int i, int i2) {
        if (this.mAppTransition.prepareAppTransition(i, i2) && okToAnimate() && i != 0) {
            this.mSkipAppTransitionAnimation = false;
        }
    }

    void requestTransitionAndLegacyPrepare(int i, int i2) {
        requestTransitionAndLegacyPrepare(i, i2, null);
    }

    void requestTransitionAndLegacyPrepare(int i, int i2, @android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer) {
        prepareAppTransition(i, i2);
        this.mTransitionController.requestTransitionIfNeeded(i, i2, windowContainer, this);
    }

    void executeAppTransition() {
        this.mTransitionController.setReady(this);
        if (this.mAppTransition.isTransitionSet()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 7019634211809476510L, 4, null, java.lang.String.valueOf(this.mAppTransition), java.lang.Long.valueOf(this.mDisplayId), java.lang.String.valueOf(android.os.Debug.getCallers(5)));
            this.mAppTransition.setReady();
            this.mWmService.mWindowPlacerLocked.requestTraversal();
        }
    }

    void handleAnimatingStoppedAndTransition() {
        this.mAppTransition.setIdle();
        for (int size = this.mNoAnimationNotifyOnTransitionFinished.size() - 1; size >= 0; size--) {
            this.mAppTransition.notifyAppTransitionFinishedLocked(this.mNoAnimationNotifyOnTransitionFinished.get(size));
        }
        this.mNoAnimationNotifyOnTransitionFinished.clear();
        this.mWallpaperController.hideDeferredWallpapersIfNeededLegacy();
        onAppTransitionDone();
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WALLPAPER, -3219913508985161450L, 0, null, null);
        computeImeTarget(true);
        this.mWallpaperMayChange = true;
        this.mWmService.mFocusMayChange = true;
        this.pendingLayoutChanges |= 1;
    }

    boolean isNextTransitionForward() {
        if (!this.mTransitionController.isShellTransitionsEnabled()) {
            return this.mAppTransition.containsTransitRequest(1) || this.mAppTransition.containsTransitRequest(3);
        }
        int collectingTransitionType = this.mTransitionController.getCollectingTransitionType();
        return collectingTransitionType == 1 || collectingTransitionType == 3;
    }

    boolean supportsSystemDecorations() {
        return (this.mWmService.mDisplayWindowSettings.shouldShowSystemDecorsLocked(this) || (this.mDisplay.getFlags() & 64) != 0 || forceDesktopMode()) && this.mDisplayId != this.mWmService.mVr2dDisplayId && isTrusted();
    }

    boolean isHomeSupported() {
        return (this.mWmService.mDisplayWindowSettings.isHomeSupportedLocked(this) && isTrusted()) || supportsSystemDecorations();
    }

    android.view.SurfaceControl getWindowingLayer() {
        return this.mWindowingLayer;
    }

    com.android.server.wm.DisplayArea.Tokens getImeContainer() {
        return this.mImeWindowsContainer;
    }

    android.view.SurfaceControl getOverlayLayer() {
        return this.mOverlayLayer;
    }

    android.view.SurfaceControl getInputOverlayLayer() {
        return this.mInputOverlayLayer;
    }

    android.view.SurfaceControl getA11yOverlayLayer() {
        return this.mA11yOverlayLayer;
    }

    android.view.SurfaceControl[] findRoundedCornerOverlays() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (com.android.server.wm.WindowToken windowToken : this.mTokenMap.values()) {
            if (windowToken.mRoundedCornerOverlay && windowToken.isVisible()) {
                arrayList.add(windowToken.mSurfaceControl);
            }
        }
        return (android.view.SurfaceControl[]) arrayList.toArray(new android.view.SurfaceControl[0]);
    }

    boolean updateSystemGestureExclusion() {
        if (this.mSystemGestureExclusionListeners.getRegisteredCallbackCount() == 0) {
            return false;
        }
        android.graphics.Region obtain = android.graphics.Region.obtain();
        this.mSystemGestureExclusionWasRestricted = calculateSystemGestureExclusion(obtain, this.mSystemGestureExclusionUnrestricted);
        try {
            if (this.mSystemGestureExclusion.equals(obtain)) {
                obtain.recycle();
                return false;
            }
            this.mSystemGestureExclusion.set(obtain);
            android.graphics.Region region = this.mSystemGestureExclusionWasRestricted ? this.mSystemGestureExclusionUnrestricted : null;
            for (int beginBroadcast = this.mSystemGestureExclusionListeners.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                try {
                    this.mSystemGestureExclusionListeners.getBroadcastItem(beginBroadcast).onSystemGestureExclusionChanged(this.mDisplayId, obtain, region);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "Failed to notify SystemGestureExclusionListener", e);
                }
            }
            this.mSystemGestureExclusionListeners.finishBroadcast();
            obtain.recycle();
            return true;
        } catch (java.lang.Throwable th) {
            obtain.recycle();
            throw th;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean calculateSystemGestureExclusion(final android.graphics.Region region, @android.annotation.Nullable final android.graphics.Region region2) {
        region.setEmpty();
        if (region2 != null) {
            region2.setEmpty();
        }
        final android.graphics.Region obtain = android.graphics.Region.obtain();
        obtain.set(0, 0, this.mDisplayFrames.mWidth, this.mDisplayFrames.mHeight);
        android.view.InsetsState rawInsetsState = this.mInsetsStateController.getRawInsetsState();
        android.graphics.Rect displayFrame = rawInsetsState.getDisplayFrame();
        android.graphics.Insets calculateInsets = rawInsetsState.calculateInsets(displayFrame, android.view.WindowInsets.Type.systemGestures(), false);
        this.mSystemGestureFrameLeft.set(displayFrame.left, displayFrame.top, displayFrame.left + calculateInsets.left, displayFrame.bottom);
        this.mSystemGestureFrameRight.set(displayFrame.right - calculateInsets.right, displayFrame.top, displayFrame.right, displayFrame.bottom);
        final android.graphics.Region obtain2 = android.graphics.Region.obtain();
        final android.graphics.Region obtain3 = android.graphics.Region.obtain();
        final int[] iArr = {this.mSystemGestureExclusionLimit, this.mSystemGestureExclusionLimit};
        final com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mWmService.getRecentsAnimationController();
        forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.this.lambda$calculateSystemGestureExclusion$34(recentsAnimationController, obtain, obtain2, obtain3, iArr, region, region2, (com.android.server.wm.WindowState) obj);
            }
        }, true);
        obtain3.recycle();
        obtain2.recycle();
        obtain.recycle();
        return iArr[0] < this.mSystemGestureExclusionLimit || iArr[1] < this.mSystemGestureExclusionLimit;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$calculateSystemGestureExclusion$34(com.android.server.wm.RecentsAnimationController recentsAnimationController, android.graphics.Region region, android.graphics.Region region2, android.graphics.Region region3, int[] iArr, android.graphics.Region region4, android.graphics.Region region5, com.android.server.wm.WindowState windowState) {
        boolean z = recentsAnimationController != null && recentsAnimationController.shouldApplyInputConsumer(windowState.getActivityRecord());
        if (!windowState.canReceiveTouchInput() || !windowState.isVisible() || (windowState.getAttrs().flags & 16) != 0 || region.isEmpty() || z) {
            return;
        }
        windowState.getEffectiveTouchableRegion(region2);
        region2.op(region, android.graphics.Region.Op.INTERSECT);
        if (windowState.isImplicitlyExcludingAllSystemGestures()) {
            region3.set(region2);
        } else {
            com.android.server.wm.utils.RegionUtils.rectListToRegion(windowState.getSystemGestureExclusion(), region3);
            region3.scale(windowState.mGlobalScale);
            android.graphics.Rect rect = windowState.getWindowFrames().mFrame;
            region3.translate(rect.left, rect.top);
            region3.op(region2, android.graphics.Region.Op.INTERSECT);
        }
        if (needsGestureExclusionRestrictions(windowState, false)) {
            iArr[0] = addToGlobalAndConsumeLimit(region3, region4, this.mSystemGestureFrameLeft, iArr[0], windowState, 0);
            iArr[1] = addToGlobalAndConsumeLimit(region3, region4, this.mSystemGestureFrameRight, iArr[1], windowState, 1);
            android.graphics.Region obtain = android.graphics.Region.obtain(region3);
            obtain.op(this.mSystemGestureFrameLeft, android.graphics.Region.Op.DIFFERENCE);
            obtain.op(this.mSystemGestureFrameRight, android.graphics.Region.Op.DIFFERENCE);
            region4.op(obtain, android.graphics.Region.Op.UNION);
            obtain.recycle();
        } else {
            if (needsGestureExclusionRestrictions(windowState, true)) {
                addToGlobalAndConsumeLimit(region3, region4, this.mSystemGestureFrameLeft, Integer.MAX_VALUE, windowState, 0);
                addToGlobalAndConsumeLimit(region3, region4, this.mSystemGestureFrameRight, Integer.MAX_VALUE, windowState, 1);
            }
            region4.op(region3, android.graphics.Region.Op.UNION);
        }
        if (region5 != null) {
            region5.op(region3, android.graphics.Region.Op.UNION);
        }
        region.op(region2, android.graphics.Region.Op.DIFFERENCE);
    }

    private static boolean needsGestureExclusionRestrictions(com.android.server.wm.WindowState windowState, boolean z) {
        int i = windowState.mAttrs.type;
        return (((!windowState.isRequestedVisible(android.view.WindowInsets.Type.navigationBars()) && windowState.mAttrs.insetsFlags.behavior == 2) && !z) || i == 2011 || i == 2040 || windowState.getActivityType() == 2 || (windowState.mAttrs.privateFlags & 32) != 0) ? false : true;
    }

    static boolean logsGestureExclusionRestrictions(com.android.server.wm.WindowState windowState) {
        android.view.WindowManager.LayoutParams attrs;
        int i;
        return windowState.mWmService.mConstants.mSystemGestureExclusionLogDebounceTimeoutMillis > 0 && (i = (attrs = windowState.getAttrs()).type) != 2013 && i != 3 && i != 2019 && (attrs.flags & 16) == 0 && needsGestureExclusionRestrictions(windowState, true) && windowState.getDisplayContent().mDisplayPolicy.hasSideGestures();
    }

    private static int addToGlobalAndConsumeLimit(android.graphics.Region region, final android.graphics.Region region2, android.graphics.Rect rect, int i, com.android.server.wm.WindowState windowState, int i2) {
        android.graphics.Region obtain = android.graphics.Region.obtain(region);
        obtain.op(rect, android.graphics.Region.Op.INTERSECT);
        final int[] iArr = {i};
        final int[] iArr2 = {0};
        com.android.server.wm.utils.RegionUtils.forEachRectReverse(obtain, new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.lambda$addToGlobalAndConsumeLimit$35(iArr, iArr2, region2, (android.graphics.Rect) obj);
            }
        });
        windowState.setLastExclusionHeights(i2, iArr2[0], i - iArr[0]);
        obtain.recycle();
        return iArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$addToGlobalAndConsumeLimit$35(int[] iArr, int[] iArr2, android.graphics.Region region, android.graphics.Rect rect) {
        if (iArr[0] <= 0) {
            return;
        }
        int height = rect.height();
        iArr2[0] = iArr2[0] + height;
        if (height > iArr[0]) {
            rect.top = rect.bottom - iArr[0];
        }
        iArr[0] = iArr[0] - height;
        region.op(rect, android.graphics.Region.Op.UNION);
    }

    void registerSystemGestureExclusionListener(android.view.ISystemGestureExclusionListener iSystemGestureExclusionListener) {
        boolean z;
        this.mSystemGestureExclusionListeners.register(iSystemGestureExclusionListener);
        if (this.mSystemGestureExclusionListeners.getRegisteredCallbackCount() == 1) {
            z = updateSystemGestureExclusion();
        } else {
            z = false;
        }
        if (!z) {
            try {
                iSystemGestureExclusionListener.onSystemGestureExclusionChanged(this.mDisplayId, this.mSystemGestureExclusion, this.mSystemGestureExclusionWasRestricted ? this.mSystemGestureExclusionUnrestricted : null);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Failed to notify SystemGestureExclusionListener during register", e);
            }
        }
    }

    void unregisterSystemGestureExclusionListener(android.view.ISystemGestureExclusionListener iSystemGestureExclusionListener) {
        this.mSystemGestureExclusionListeners.unregister(iSystemGestureExclusionListener);
    }

    void registerDecorViewGestureListener(android.view.IDecorViewGestureListener iDecorViewGestureListener) {
        this.mDecorViewGestureListener.register(iDecorViewGestureListener);
    }

    void unregisterDecorViewGestureListener(android.view.IDecorViewGestureListener iDecorViewGestureListener) {
        this.mDecorViewGestureListener.unregister(iDecorViewGestureListener);
    }

    void updateDecorViewGestureIntercepted(android.os.IBinder iBinder, boolean z) {
        for (int beginBroadcast = this.mDecorViewGestureListener.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
            try {
                this.mDecorViewGestureListener.getBroadcastItem(beginBroadcast).onInterceptionChanged(iBinder, z);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Failed to notify DecorViewGestureListener", e);
            }
        }
        this.mDecorViewGestureListener.finishBroadcast();
    }

    void updateKeepClearAreas() {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        getKeepClearAreas(arraySet, arraySet2);
        if (!this.mRestrictedKeepClearAreas.equals(arraySet) || !this.mUnrestrictedKeepClearAreas.equals(arraySet2)) {
            this.mRestrictedKeepClearAreas = arraySet;
            this.mUnrestrictedKeepClearAreas = arraySet2;
            this.mWmService.mDisplayNotificationController.dispatchKeepClearAreasChanged(this, arraySet, arraySet2);
        }
    }

    void getKeepClearAreas(final java.util.Set<android.graphics.Rect> set, final java.util.Set<android.graphics.Rect> set2) {
        final android.graphics.Matrix matrix = new android.graphics.Matrix();
        final float[] fArr = new float[9];
        final com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mWmService.getRecentsAnimationController();
        forAllWindows(new com.android.internal.util.ToBooleanFunction() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda7
            public final boolean apply(java.lang.Object obj) {
                boolean lambda$getKeepClearAreas$37;
                lambda$getKeepClearAreas$37 = com.android.server.wm.DisplayContent.lambda$getKeepClearAreas$37(com.android.server.wm.RecentsAnimationController.this, set, set2, matrix, fArr, (com.android.server.wm.WindowState) obj);
                return lambda$getKeepClearAreas$37;
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getKeepClearAreas$37(com.android.server.wm.RecentsAnimationController recentsAnimationController, java.util.Set set, final java.util.Set set2, android.graphics.Matrix matrix, float[] fArr, com.android.server.wm.WindowState windowState) {
        if (recentsAnimationController != null && recentsAnimationController.shouldApplyInputConsumer(windowState.getActivityRecord())) {
            return false;
        }
        if (windowState.isVisible() && !windowState.inPinnedWindowingMode()) {
            windowState.getKeepClearAreas(set, set2, matrix, fArr);
            if (windowState.mIsImWindow) {
                android.graphics.Region obtain = android.graphics.Region.obtain();
                windowState.getEffectiveTouchableRegion(obtain);
                com.android.server.wm.utils.RegionUtils.forEachRect(obtain, new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda14
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        set2.add((android.graphics.Rect) obj);
                    }
                });
            }
        }
        return windowState.getWindowType() == 1 && windowState.getWindowingMode() == 1;
    }

    java.util.Set<android.graphics.Rect> getKeepClearAreas() {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        getKeepClearAreas(arraySet, arraySet);
        return arraySet;
    }

    protected com.android.internal.logging.MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new com.android.internal.logging.MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    void requestDisplayUpdate(@android.annotation.NonNull java.lang.Runnable runnable) {
        this.mDisplayUpdater.updateDisplayInfo(runnable);
    }

    void onDisplayInfoUpdated(@android.annotation.NonNull android.view.DisplayInfo displayInfo) {
        int i = this.mDisplayInfo.state;
        updateDisplayInfo(displayInfo);
        int displayId = this.mDisplay.getDisplayId();
        int i2 = this.mDisplayInfo.state;
        if (displayId != 0) {
            if (i2 == 1) {
                this.mOffTokenAcquirer.acquire(this.mDisplayId);
            } else if (i2 == 2) {
                this.mOffTokenAcquirer.release(this.mDisplayId);
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -8165317816061445169L, 21, "Content Recording: Display %d state was (%d), is now (%d), so update recording?", java.lang.Long.valueOf(this.mDisplayId), java.lang.Long.valueOf(i), java.lang.Long.valueOf(i2));
            if (i != i2) {
                updateRecording();
            }
        }
        this.mWallpaperController.resetLargestDisplay(this.mDisplay);
        if (android.view.Display.isSuspendedState(i) && !android.view.Display.isSuspendedState(i2) && i2 != 0) {
            this.mWmService.mWindowContextListenerController.dispatchPendingConfigurationIfNeeded(this.mDisplayId);
        }
        this.mWmService.requestTraversal();
    }

    static boolean alwaysCreateRootTask(int i, int i2) {
        return (i2 == 1 || i2 == 3) && (i == 1 || i == 5 || i == 2 || i == 6);
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getFocusedRootTask() {
        return (com.android.server.wm.Task) getItemFromTaskDisplayAreas(new java.util.function.Function() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda26
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((com.android.server.wm.TaskDisplayArea) obj).getFocusedRootTask();
            }
        });
    }

    void removeRootTasksInWindowingModes(final int... iArr) {
        if (iArr == null || iArr.length == 0) {
            return;
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.lambda$removeRootTasksInWindowingModes$38(iArr, arrayList, (com.android.server.wm.Task) obj);
            }
        });
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            this.mRootWindowContainer.mTaskSupervisor.removeRootTask((com.android.server.wm.Task) arrayList.get(size));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$removeRootTasksInWindowingModes$38(int[] iArr, java.util.ArrayList arrayList, com.android.server.wm.Task task) {
        for (int i : iArr) {
            if (!task.mCreatedByOrganizer && task.getWindowingMode() == i && task.isActivityTypeStandardOrUndefined()) {
                arrayList.add(task);
            }
        }
    }

    void removeRootTasksWithActivityTypes(final int... iArr) {
        if (iArr == null || iArr.length == 0) {
            return;
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda27
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.lambda$removeRootTasksWithActivityTypes$39(iArr, arrayList, (com.android.server.wm.Task) obj);
            }
        });
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            this.mRootWindowContainer.mTaskSupervisor.removeRootTask((com.android.server.wm.Task) arrayList.get(size));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$removeRootTasksWithActivityTypes$39(int[] iArr, java.util.ArrayList arrayList, com.android.server.wm.Task task) {
        for (int i : iArr) {
            if (task.mCreatedByOrganizer) {
                for (int childCount = task.getChildCount() - 1; childCount >= 0; childCount--) {
                    com.android.server.wm.Task task2 = (com.android.server.wm.Task) task.getChildAt(childCount);
                    if (task2.getActivityType() == i) {
                        arrayList.add(task2);
                    }
                }
            } else if (task.getActivityType() == i) {
                arrayList.add(task);
            }
        }
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord topRunningActivity() {
        return topRunningActivity(false);
    }

    @android.annotation.Nullable
    com.android.server.wm.ActivityRecord topRunningActivity(final boolean z) {
        return (com.android.server.wm.ActivityRecord) getItemFromTaskDisplayAreas(new java.util.function.Function() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda17
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.wm.ActivityRecord lambda$topRunningActivity$40;
                lambda$topRunningActivity$40 = com.android.server.wm.DisplayContent.lambda$topRunningActivity$40(z, (com.android.server.wm.TaskDisplayArea) obj);
                return lambda$topRunningActivity$40;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.wm.ActivityRecord lambda$topRunningActivity$40(boolean z, com.android.server.wm.TaskDisplayArea taskDisplayArea) {
        return taskDisplayArea.topRunningActivity(z);
    }

    boolean updateDisplayOverrideConfigurationLocked() {
        com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mWmService.getRecentsAnimationController();
        if (recentsAnimationController != null) {
            recentsAnimationController.cancelAnimationForDisplayChange();
        }
        android.content.res.Configuration configuration = new android.content.res.Configuration();
        computeScreenConfiguration(configuration);
        this.mAtmService.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.server.wm.ActivityTaskManagerService$$ExternalSyntheticLambda23(), this.mAtmService.mAmInternal, java.lang.Integer.valueOf(this.mDisplayId)));
        android.provider.Settings.System.clearConfiguration(configuration);
        updateDisplayOverrideConfigurationLocked(configuration, null, false);
        return this.mAtmService.mTmpUpdateConfigurationResult.changes != 0;
    }

    boolean updateDisplayOverrideConfigurationLocked(android.content.res.Configuration configuration, com.android.server.wm.ActivityRecord activityRecord, boolean z) {
        int performDisplayOverrideConfigUpdate;
        this.mAtmService.deferWindowLayout();
        if (configuration == null) {
            performDisplayOverrideConfigUpdate = 0;
        } else {
            try {
                if (this.mDisplayId == 0) {
                    performDisplayOverrideConfigUpdate = this.mAtmService.updateGlobalConfigurationLocked(configuration, false, false, com.android.server.am.ProcessList.INVALID_ADJ);
                } else {
                    performDisplayOverrideConfigUpdate = performDisplayOverrideConfigUpdate(configuration);
                }
                this.mAtmService.mTmpUpdateConfigurationResult.changes = performDisplayOverrideConfigUpdate;
                this.mAtmService.mTmpUpdateConfigurationResult.mIsUpdating = true;
            } catch (java.lang.Throwable th) {
                this.mAtmService.mTmpUpdateConfigurationResult.mIsUpdating = false;
                this.mAtmService.continueWindowLayout();
                throw th;
            }
        }
        boolean ensureConfigAndVisibilityAfterUpdate = z ? true : this.mAtmService.ensureConfigAndVisibilityAfterUpdate(activityRecord, performDisplayOverrideConfigUpdate);
        this.mAtmService.mTmpUpdateConfigurationResult.mIsUpdating = false;
        this.mAtmService.continueWindowLayout();
        this.mAtmService.mTmpUpdateConfigurationResult.activityRelaunched = !ensureConfigAndVisibilityAfterUpdate;
        return ensureConfigAndVisibilityAfterUpdate;
    }

    int performDisplayOverrideConfigUpdate(android.content.res.Configuration configuration) {
        this.mTempConfig.setTo(getRequestedOverrideConfiguration());
        int updateFrom = this.mTempConfig.updateFrom(configuration);
        if (updateFrom != 0) {
            android.util.Slog.i(TAG, "Override config changes=" + java.lang.Integer.toHexString(updateFrom) + " " + this.mTempConfig + " for displayId=" + this.mDisplayId);
            if (isReady() && this.mTransitionController.isShellTransitionsEnabled()) {
                requestChangeTransitionIfNeeded(updateFrom, null);
            }
            onRequestedOverrideConfigurationChanged(this.mTempConfig);
            if (((updateFrom & 4096) != 0) && this.mDisplayId == 0) {
                this.mAtmService.mAppWarnings.onDensityChanged();
                this.mAtmService.mH.sendMessage(com.android.internal.util.function.pooled.PooledLambda.obtainMessage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda13
                    public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                        ((android.app.ActivityManagerInternal) obj).killAllBackgroundProcessesExcept(((java.lang.Integer) obj2).intValue(), ((java.lang.Integer) obj3).intValue());
                    }
                }, this.mAtmService.mAmInternal, 24, 6));
            }
            this.mWmService.mDisplayNotificationController.dispatchDisplayChanged(this, getConfiguration());
        }
        return updateFrom;
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void onRequestedOverrideConfigurationChanged(android.content.res.Configuration configuration) {
        android.content.res.Configuration requestedOverrideConfiguration = getRequestedOverrideConfiguration();
        int rotation = requestedOverrideConfiguration.windowConfiguration.getRotation();
        int rotation2 = configuration.windowConfiguration.getRotation();
        if (rotation != -1 && rotation2 != -1 && rotation != rotation2) {
            applyRotationAndFinishFixedRotation(rotation, rotation2);
        }
        this.mCurrentOverrideConfigurationChanges = requestedOverrideConfiguration.diff(configuration);
        super.onRequestedOverrideConfigurationChanged(configuration);
        this.mCurrentOverrideConfigurationChanges = 0;
        if (this.mWaitingForConfig) {
            this.mWaitingForConfig = false;
            this.mWmService.mLastFinishedFreezeSource = "new-config";
        }
        this.mAtmService.addWindowLayoutReasons(1);
    }

    @Override // com.android.server.wm.WindowContainer
    void onResize() {
        super.onResize();
        if (this.mWmService.mAccessibilityController.hasCallbacks()) {
            this.mWmService.mAccessibilityController.onDisplaySizeChanged(this);
        }
    }

    private void applyRotationAndFinishFixedRotation(final int i, final int i2) {
        com.android.server.wm.ActivityRecord activityRecord = this.mFixedRotationLaunchingApp;
        if (activityRecord == null) {
            lambda$applyRotationAndFinishFixedRotation$41(i, i2);
        } else {
            activityRecord.finishFixedRotationTransform(new java.lang.Runnable() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.DisplayContent.this.lambda$applyRotationAndFinishFixedRotation$41(i, i2);
                }
            });
            setFixedRotationLaunchingAppUnchecked(null);
        }
    }

    void handleActivitySizeCompatModeIfNeeded(com.android.server.wm.ActivityRecord activityRecord) {
        com.android.server.wm.Task organizedTask = activityRecord.getOrganizedTask();
        if (organizedTask == null) {
            this.mActiveSizeCompatActivities.remove(activityRecord);
            return;
        }
        if (activityRecord.isState(com.android.server.wm.ActivityRecord.State.RESUMED) && activityRecord.inSizeCompatMode()) {
            if (this.mActiveSizeCompatActivities.add(activityRecord)) {
                organizedTask.onSizeCompatActivityChanged();
            }
        } else if (this.mActiveSizeCompatActivities.remove(activityRecord)) {
            organizedTask.onSizeCompatActivityChanged();
        }
    }

    boolean isUidPresent(int i) {
        java.util.function.Predicate<com.android.server.wm.ActivityRecord> obtainPredicate = com.android.internal.util.function.pooled.PooledLambda.obtainPredicate(new com.android.server.wm.DisplayContent$$ExternalSyntheticLambda12(), com.android.internal.util.function.pooled.PooledLambda.__(com.android.server.wm.ActivityRecord.class), java.lang.Integer.valueOf(i));
        boolean z = this.mDisplayContent.getActivity(obtainPredicate) != null;
        obtainPredicate.recycle();
        return z;
    }

    boolean isRemoved() {
        return this.mRemoved;
    }

    boolean isRemoving() {
        return this.mRemoving;
    }

    void remove() {
        this.mRemoving = true;
        this.mRootWindowContainer.mTaskSupervisor.beginDeferResume();
        try {
            com.android.server.wm.Task task = (com.android.server.wm.Task) reduceOnAllTaskDisplayAreas(new java.util.function.BiFunction() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda8
                @Override // java.util.function.BiFunction
                public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                    com.android.server.wm.Task lambda$remove$42;
                    lambda$remove$42 = com.android.server.wm.DisplayContent.lambda$remove$42((com.android.server.wm.TaskDisplayArea) obj, (com.android.server.wm.Task) obj2);
                    return lambda$remove$42;
                }
            }, null, false);
            this.mRootWindowContainer.mTaskSupervisor.endDeferResume();
            this.mRemoved = true;
            if (this.mContentRecorder != null) {
                this.mContentRecorder.stopRecording();
            }
            if (task != null) {
                task.resumeNextFocusAfterReparent();
            }
            releaseSelfIfNeeded();
            this.mDisplayPolicy.release();
            if (!this.mAllSleepTokens.isEmpty()) {
                this.mAllSleepTokens.forEach(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.wm.DisplayContent.this.lambda$remove$43((com.android.server.wm.RootWindowContainer.SleepToken) obj);
                    }
                });
                this.mAllSleepTokens.clear();
                this.mAtmService.updateSleepIfNeededLocked();
            }
        } catch (java.lang.Throwable th) {
            this.mRootWindowContainer.mTaskSupervisor.endDeferResume();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.wm.Task lambda$remove$42(com.android.server.wm.TaskDisplayArea taskDisplayArea, com.android.server.wm.Task task) {
        com.android.server.wm.Task remove = taskDisplayArea.remove();
        if (remove != null) {
            return remove;
        }
        return task;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$remove$43(com.android.server.wm.RootWindowContainer.SleepToken sleepToken) {
        this.mRootWindowContainer.mSleepTokens.remove(sleepToken.mHashKey);
    }

    void releaseSelfIfNeeded() {
        if (!this.mRemoved) {
            return;
        }
        if (!forAllRootTasks(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda21
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$releaseSelfIfNeeded$44;
                lambda$releaseSelfIfNeeded$44 = com.android.server.wm.DisplayContent.lambda$releaseSelfIfNeeded$44((com.android.server.wm.Task) obj);
                return lambda$releaseSelfIfNeeded$44;
            }
        }) && getRootTaskCount() > 0) {
            forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda22
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.wm.Task) obj).removeIfPossible("releaseSelfIfNeeded");
                }
            });
        } else if (getTopRootTask() == null) {
            removeIfPossible();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$releaseSelfIfNeeded$44(com.android.server.wm.Task task) {
        return !task.isActivityTypeHome() || task.hasChild();
    }

    android.util.IntArray getPresentUIDs() {
        this.mDisplayAccessUIDs.clear();
        this.mDisplayContent.forAllActivities(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda32
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.this.lambda$getPresentUIDs$46((com.android.server.wm.ActivityRecord) obj);
            }
        });
        return this.mDisplayAccessUIDs;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getPresentUIDs$46(com.android.server.wm.ActivityRecord activityRecord) {
        this.mDisplayAccessUIDs.add(activityRecord.getUid());
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldDestroyContentOnRemove() {
        return this.mDisplay.getRemoveMode() == 1;
    }

    boolean shouldSleep() {
        return (getRootTaskCount() == 0 || !this.mAllSleepTokens.isEmpty()) && this.mAtmService.mRunningVoice == null;
    }

    void ensureActivitiesVisible(final com.android.server.wm.ActivityRecord activityRecord, final boolean z) {
        if (this.mInEnsureActivitiesVisible) {
            return;
        }
        this.mAtmService.mTaskSupervisor.beginActivityVisibilityUpdate();
        try {
            this.mInEnsureActivitiesVisible = true;
            forAllRootTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda30
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    ((com.android.server.wm.Task) obj).ensureActivitiesVisible(com.android.server.wm.ActivityRecord.this, z);
                }
            });
            if (this.mTransitionController.useShellTransitionsRotation() && this.mTransitionController.isCollecting() && this.mWallpaperController.getWallpaperTarget() != null) {
                this.mWallpaperController.adjustWallpaperWindows();
            }
        } finally {
            this.mAtmService.mTaskSupervisor.endActivityVisibilityUpdate();
            this.mInEnsureActivitiesVisible = false;
        }
    }

    boolean isSleeping() {
        return this.mSleeping;
    }

    void setIsSleeping(boolean z) {
        this.mSleeping = z;
    }

    void notifyKeyguardFlagsChanged() {
        if (!isKeyguardLocked()) {
            return;
        }
        boolean isTransitionSet = this.mAppTransition.isTransitionSet();
        if (!isTransitionSet) {
            prepareAppTransition(0);
        }
        this.mRootWindowContainer.ensureActivitiesVisible();
        if (!isTransitionSet) {
            executeAppTransition();
        }
    }

    boolean canShowWithInsecureKeyguard() {
        return (this.mDisplay.getFlags() & 32) != 0;
    }

    boolean isKeyguardLocked() {
        return this.mRootWindowContainer.mTaskSupervisor.getKeyguardController().isKeyguardLocked(this.mDisplayId);
    }

    boolean isKeyguardGoingAway() {
        return this.mRootWindowContainer.mTaskSupervisor.getKeyguardController().isKeyguardGoingAway(this.mDisplayId);
    }

    boolean isKeyguardAlwaysUnlocked() {
        return (this.mDisplayInfo.flags & 512) != 0;
    }

    boolean shouldRotateWithContent() {
        return (this.mDisplayInfo.flags & 16384) != 0;
    }

    boolean hasOwnFocus() {
        return this.mWmService.mPerDisplayFocusEnabled || (this.mDisplayInfo.flags & 2048) != 0;
    }

    boolean isKeyguardOccluded() {
        return this.mRootWindowContainer.mTaskSupervisor.getKeyguardController().isKeyguardOccluded(this.mDisplayId);
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getTaskOccludingKeyguard() {
        com.android.server.wm.KeyguardController keyguardController = this.mRootWindowContainer.mTaskSupervisor.getKeyguardController();
        if (keyguardController.getTopOccludingActivity(this.mDisplayId) != null) {
            return keyguardController.getTopOccludingActivity(this.mDisplayId).getRootTask();
        }
        if (keyguardController.getDismissKeyguardActivity(this.mDisplayId) != null) {
            return keyguardController.getDismissKeyguardActivity(this.mDisplayId).getRootTask();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$removeAllTasks$48(com.android.server.wm.Task task) {
        task.getRootTask().removeChild(task, "removeAllTasks");
    }

    @com.android.internal.annotations.VisibleForTesting
    void removeAllTasks() {
        forAllTasks(new java.util.function.Consumer() { // from class: com.android.server.wm.DisplayContent$$ExternalSyntheticLambda31
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.DisplayContent.lambda$removeAllTasks$48((com.android.server.wm.Task) obj);
            }
        });
    }

    android.content.Context getDisplayUiContext() {
        return this.mDisplayPolicy.getSystemUiContext();
    }

    @Override // com.android.server.wm.DisplayArea
    boolean setIgnoreOrientationRequest(boolean z) {
        if (this.mSetIgnoreOrientationRequest == z) {
            return false;
        }
        boolean ignoreOrientationRequest = super.setIgnoreOrientationRequest(z);
        this.mWmService.mDisplayWindowSettings.setIgnoreOrientationRequest(this, this.mSetIgnoreOrientationRequest);
        return ignoreOrientationRequest;
    }

    void onIsIgnoreOrientationRequestDisabledChanged() {
        if (this.mFocusedApp != null) {
            onLastFocusedTaskDisplayAreaChanged(this.mFocusedApp.getDisplayArea());
        }
        if (this.mSetIgnoreOrientationRequest) {
            updateOrientation();
        }
    }

    @android.annotation.Nullable
    com.android.server.wm.WindowState findScrollCaptureTargetWindow(@android.annotation.Nullable final com.android.server.wm.WindowState windowState, final int i) {
        return getWindow(new java.util.function.Predicate<com.android.server.wm.WindowState>() { // from class: com.android.server.wm.DisplayContent.3
            boolean behindTopWindow;

            {
                this.behindTopWindow = windowState == null;
            }

            @Override // java.util.function.Predicate
            public boolean test(com.android.server.wm.WindowState windowState2) {
                if (!this.behindTopWindow) {
                    if (windowState2 == windowState) {
                        this.behindTopWindow = true;
                    }
                    return false;
                }
                if (i == -1) {
                    if (!windowState2.canReceiveKeys()) {
                        return false;
                    }
                } else {
                    com.android.server.wm.Task task = windowState2.getTask();
                    if (task == null || !task.isTaskId(i)) {
                        return false;
                    }
                }
                return !windowState2.isSecureLocked();
            }
        });
    }

    @Override // com.android.server.wm.DisplayArea, com.android.server.wm.ConfigurationContainer
    public boolean providesMaxBounds() {
        return true;
    }

    void setSandboxDisplayApis(boolean z) {
        this.mSandboxDisplayApis = z;
    }

    boolean sandboxDisplayApis() {
        return this.mSandboxDisplayApis;
    }

    private com.android.server.wm.ContentRecorder getContentRecorder() {
        if (this.mContentRecorder == null) {
            this.mContentRecorder = new com.android.server.wm.ContentRecorder(this);
        }
        return this.mContentRecorder;
    }

    @com.android.internal.annotations.VisibleForTesting
    void pauseRecording() {
        if (this.mContentRecorder != null) {
            this.mContentRecorder.pauseRecording();
        }
    }

    void setContentRecordingSession(@android.annotation.Nullable android.view.ContentRecordingSession contentRecordingSession) {
        getContentRecorder().setContentRecordingSession(contentRecordingSession);
    }

    boolean setDisplayMirroring() {
        int displayIdToMirror = this.mWmService.mDisplayManagerInternal.getDisplayIdToMirror(this.mDisplayId);
        if (displayIdToMirror == -1) {
            return false;
        }
        if (displayIdToMirror == this.mDisplayId) {
            if (this.mDisplayId != 0) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 4162342172327950908L, 1, "Content Recording: Attempting to mirror self on %d", java.lang.Long.valueOf(displayIdToMirror));
            }
            return false;
        }
        com.android.server.wm.DisplayContent displayContentOrCreate = this.mRootWindowContainer.getDisplayContentOrCreate(displayIdToMirror);
        if (displayContentOrCreate == null && this.mDisplayId == 0) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 5489691866309868814L, 1, "Content Recording: Found no matching mirror display for id=%d for DEFAULT_DISPLAY. Nothing to mirror.", java.lang.Long.valueOf(displayIdToMirror));
            return false;
        }
        if (displayContentOrCreate == null) {
            displayContentOrCreate = this.mRootWindowContainer.getDefaultDisplay();
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, -39794010824230928L, 5, "Content Recording: Attempting to mirror %d from %d but no DisplayContent associated. Changing to mirror default display.", java.lang.Long.valueOf(displayIdToMirror), java.lang.Long.valueOf(this.mDisplayId));
        }
        setContentRecordingSession(android.view.ContentRecordingSession.createDisplaySession(displayContentOrCreate.getDisplayId()).setVirtualDisplayId(this.mDisplayId));
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_CONTENT_RECORDING, 6545352723229848841L, 5, "Content Recording: Successfully created a ContentRecordingSession for displayId=%d to mirror content from displayId=%d", java.lang.Long.valueOf(this.mDisplayId), java.lang.Long.valueOf(displayIdToMirror));
        return true;
    }

    void updateRecording() {
        if ((this.mContentRecorder == null || !this.mContentRecorder.isContentRecordingSessionSet()) && !setDisplayMirroring()) {
            return;
        }
        this.mContentRecorder.updateRecording();
    }

    boolean isCurrentlyRecording() {
        return this.mContentRecorder != null && this.mContentRecorder.isCurrentlyRecording();
    }

    class FixedRotationTransitionListener extends com.android.server.wm.WindowManagerInternal.AppTransitionListener {
        private com.android.server.wm.ActivityRecord mAnimatingRecents;
        private boolean mRecentsWillBeTop;

        FixedRotationTransitionListener() {
        }

        void onStartRecentsAnimation(@android.annotation.NonNull com.android.server.wm.ActivityRecord activityRecord) {
            this.mAnimatingRecents = activityRecord;
            if (activityRecord.isVisible() && com.android.server.wm.DisplayContent.this.mFocusedApp != null && !com.android.server.wm.DisplayContent.this.mFocusedApp.occludesParent()) {
                return;
            }
            com.android.server.wm.DisplayContent.this.rotateInDifferentOrientationIfNeeded(activityRecord);
            if (activityRecord.hasFixedRotationTransform()) {
                com.android.server.wm.DisplayContent.this.setFixedRotationLaunchingApp(activityRecord, activityRecord.getWindowConfiguration().getRotation());
            }
        }

        void onFinishRecentsAnimation() {
            com.android.server.wm.ActivityRecord activityRecord = this.mAnimatingRecents;
            boolean z = this.mRecentsWillBeTop;
            this.mAnimatingRecents = null;
            this.mRecentsWillBeTop = false;
            if (z) {
                return;
            }
            if (activityRecord != null && activityRecord == com.android.server.wm.DisplayContent.this.mFixedRotationLaunchingApp && activityRecord.isVisible() && activityRecord != com.android.server.wm.DisplayContent.this.topRunningActivity()) {
                com.android.server.wm.DisplayContent.this.setFixedRotationLaunchingAppUnchecked(null);
            } else {
                com.android.server.wm.DisplayContent.this.continueUpdateOrientationForDiffOrienLaunchingApp();
            }
        }

        void notifyRecentsWillBeTop() {
            this.mRecentsWillBeTop = true;
        }

        boolean shouldDeferRotation() {
            com.android.server.wm.ActivityRecord activityRecord = null;
            if (com.android.server.wm.DisplayContent.this.mTransitionController.isShellTransitionsEnabled()) {
                com.android.server.wm.ActivityRecord activityRecord2 = com.android.server.wm.DisplayContent.this.mFixedRotationLaunchingApp;
                if (activityRecord2 != null && com.android.server.wm.DisplayContent.this.mTransitionController.isTransientLaunch(activityRecord2)) {
                    activityRecord = activityRecord2;
                }
            } else if (this.mAnimatingRecents != null && !com.android.server.wm.DisplayContent.this.hasTopFixedRotationLaunchingApp()) {
                activityRecord = this.mAnimatingRecents;
            }
            if (activityRecord == null || activityRecord.getRequestedConfigurationOrientation(true) == 0) {
                return false;
            }
            return com.android.server.wm.DisplayContent.this.mWmService.mPolicy.okToAnimate(false);
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public void onAppTransitionFinishedLocked(android.os.IBinder iBinder) {
            com.android.server.wm.ActivityRecord forTokenLocked = com.android.server.wm.ActivityRecord.forTokenLocked(iBinder);
            if (forTokenLocked == null || forTokenLocked == this.mAnimatingRecents) {
                return;
            }
            if (this.mAnimatingRecents != null && this.mRecentsWillBeTop) {
                return;
            }
            if (com.android.server.wm.DisplayContent.this.mFixedRotationLaunchingApp == null) {
                forTokenLocked.finishFixedRotationTransform();
                return;
            }
            if (com.android.server.wm.DisplayContent.this.mFixedRotationLaunchingApp.hasFixedRotationTransform(forTokenLocked)) {
                if (com.android.server.wm.DisplayContent.this.mFixedRotationLaunchingApp.hasAnimatingFixedRotationTransition()) {
                    return;
                }
            } else {
                com.android.server.wm.Task task = forTokenLocked.getTask();
                if (task == null || task != com.android.server.wm.DisplayContent.this.mFixedRotationLaunchingApp.getTask() || task.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.DisplayContent$FixedRotationTransitionListener$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        return ((com.android.server.wm.ActivityRecord) obj).isInTransition();
                    }
                }) != null) {
                    return;
                }
            }
            com.android.server.wm.DisplayContent.this.continueUpdateOrientationForDiffOrienLaunchingApp();
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public void onAppTransitionCancelledLocked(boolean z) {
            if (com.android.server.wm.DisplayContent.this.mTransitionController.isShellTransitionsEnabled()) {
                return;
            }
            com.android.server.wm.DisplayContent.this.continueUpdateOrientationForDiffOrienLaunchingApp();
        }

        @Override // com.android.server.wm.WindowManagerInternal.AppTransitionListener
        public void onAppTransitionTimeoutLocked() {
            com.android.server.wm.DisplayContent.this.continueUpdateOrientationForDiffOrienLaunchingApp();
        }
    }

    class RemoteInsetsControlTarget implements com.android.server.wm.InsetsControlTarget {
        private final boolean mCanShowTransient;
        private final android.view.IDisplayWindowInsetsController mRemoteInsetsController;
        private int mRequestedVisibleTypes = android.view.WindowInsets.Type.defaultVisible();

        RemoteInsetsControlTarget(android.view.IDisplayWindowInsetsController iDisplayWindowInsetsController) {
            this.mRemoteInsetsController = iDisplayWindowInsetsController;
            this.mCanShowTransient = com.android.server.wm.DisplayContent.this.mWmService.mContext.getResources().getBoolean(android.R.bool.config_quickSettingsShowMediaPlayer);
        }

        void topFocusedWindowChanged(android.content.ComponentName componentName, int i) {
            try {
                this.mRemoteInsetsController.topFocusedWindowChanged(componentName, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.wm.DisplayContent.TAG, "Failed to deliver package in top focused window change", e);
            }
        }

        void notifyInsetsChanged() {
            try {
                this.mRemoteInsetsController.insetsChanged(com.android.server.wm.DisplayContent.this.getInsetsStateController().getRawInsetsState());
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.wm.DisplayContent.TAG, "Failed to deliver inset state change", e);
            }
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public void notifyInsetsControlChanged(int i) {
            com.android.server.wm.InsetsStateController insetsStateController = com.android.server.wm.DisplayContent.this.getInsetsStateController();
            try {
                this.mRemoteInsetsController.insetsControlChanged(insetsStateController.getRawInsetsState(), insetsStateController.getControlsForDispatch(this));
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.wm.DisplayContent.TAG, "Failed to deliver inset control state change", e);
            }
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public void showInsets(int i, boolean z, @android.annotation.Nullable android.view.inputmethod.ImeTracker.Token token) {
            try {
                android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 23);
                this.mRemoteInsetsController.showInsets(i, z, token);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.wm.DisplayContent.TAG, "Failed to deliver showInsets", e);
                android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 23);
            }
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public void hideInsets(int i, boolean z, @android.annotation.Nullable android.view.inputmethod.ImeTracker.Token token) {
            try {
                android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 24);
                this.mRemoteInsetsController.hideInsets(i, z, token);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.wm.DisplayContent.TAG, "Failed to deliver hideInsets", e);
                android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 24);
            }
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public boolean canShowTransient() {
            return this.mCanShowTransient;
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public boolean isRequestedVisible(int i) {
            return ((android.view.WindowInsets.Type.ime() & i) != 0 && com.android.server.wm.DisplayContent.this.getInsetsStateController().getImeSourceProvider().isImeShowing()) || (i & this.mRequestedVisibleTypes) != 0;
        }

        @Override // com.android.server.wm.InsetsControlTarget
        public int getRequestedVisibleTypes() {
            return this.mRequestedVisibleTypes;
        }

        void setRequestedVisibleTypes(int i) {
            if (this.mRequestedVisibleTypes != i) {
                this.mRequestedVisibleTypes = i;
            }
        }
    }

    android.view.MagnificationSpec getMagnificationSpec() {
        return this.mMagnificationSpec;
    }

    com.android.server.wm.DisplayArea findAreaForWindowType(int i, android.os.Bundle bundle, boolean z, boolean z2) {
        if (i >= 1 && i <= 99) {
            return this.mDisplayAreaPolicy.getTaskDisplayArea(bundle);
        }
        if (i == 2011 || i == 2012) {
            return getImeContainer();
        }
        return this.mDisplayAreaPolicy.findAreaForWindowType(i, bundle, z, z2);
    }

    com.android.server.wm.DisplayArea findAreaForToken(com.android.server.wm.WindowToken windowToken) {
        return findAreaForWindowType(windowToken.getWindowType(), windowToken.mOptions, windowToken.mOwnerCanManageAppTokens, windowToken.mRoundedCornerOverlay);
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.DisplayContent asDisplayContent() {
        return this;
    }

    @Override // com.android.server.wm.WindowContainer
    int getRelativeDisplayRotation() {
        return 0;
    }

    public void replaceContent(android.view.SurfaceControl surfaceControl) {
        new android.view.SurfaceControl.Transaction().reparent(surfaceControl, getSurfaceControl()).reparent(this.mWindowingLayer, null).reparent(this.mOverlayLayer, null).reparent(this.mInputOverlayLayer, null).reparent(this.mA11yOverlayLayer, null).apply();
    }
}
