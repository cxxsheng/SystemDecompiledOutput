package com.android.server.wm;

/* loaded from: classes3.dex */
class WindowState extends com.android.server.wm.WindowContainer<com.android.server.wm.WindowState> implements com.android.server.policy.WindowManagerPolicy.WindowState, com.android.server.wm.InsetsControlTarget, com.android.server.wm.InputTarget {
    static final int BLAST_TIMEOUT_DURATION = 5000;
    static final int EXCLUSION_LEFT = 0;
    static final int EXCLUSION_RIGHT = 1;
    static final int EXIT_ANIMATING_TYPES = 25;
    static final int LEGACY_POLICY_VISIBILITY = 1;
    static final int MINIMUM_VISIBLE_HEIGHT_IN_DP = 32;
    static final int MINIMUM_VISIBLE_WIDTH_IN_DP = 48;
    private static final int POLICY_VISIBILITY_ALL = 3;
    static final int RESIZE_HANDLE_WIDTH_IN_DP = 30;
    static final java.lang.String TAG = "WindowManager";
    private static final int VISIBLE_FOR_USER = 2;
    private static final java.lang.StringBuilder sTmpSB = new java.lang.StringBuilder();
    private static final java.util.Comparator<com.android.server.wm.WindowState> sWindowSubLayerComparator = new java.util.Comparator<com.android.server.wm.WindowState>() { // from class: com.android.server.wm.WindowState.1
        @Override // java.util.Comparator
        public int compare(com.android.server.wm.WindowState windowState, com.android.server.wm.WindowState windowState2) {
            int i = windowState.mSubLayer;
            int i2 = windowState2.mSubLayer;
            if (i < i2) {
                return -1;
            }
            if (i == i2 && i2 < 0) {
                return -1;
            }
            return 1;
        }
    };
    final android.view.InsetsState mAboveInsetsState;
    com.android.server.wm.ActivityRecord mActivityRecord;
    boolean mAnimatingExit;
    boolean mAppFreezing;
    final int mAppOp;
    private boolean mAppOpVisibility;
    final android.view.WindowManager.LayoutParams mAttrs;
    final int mBaseLayer;
    final android.view.IWindow mClient;
    private final android.window.ClientWindowFrames mClientWindowFrames;
    float mCompatScale;
    final android.content.Context mContext;
    boolean mDestroying;
    int mDisableFlags;
    private boolean mDragResizing;
    private boolean mDragResizingChangeReported;
    private final java.util.List<com.android.server.wm.WindowState.DrawHandler> mDrawHandlers;
    private android.os.PowerManager.WakeLock mDrawLock;
    private boolean mDrawnStateEvaluated;
    private final java.util.List<android.graphics.Rect> mExclusionRects;
    private android.os.RemoteCallbackList<android.view.IWindowFocusObserver> mFocusCallbacks;
    private boolean mForceHideNonSystemOverlayWindow;
    final boolean mForceSeamlesslyRotate;
    int mFrameRateSelectionPriority;
    com.android.server.wm.RefreshRatePolicy.FrameRateVote mFrameRateVote;
    private android.view.InsetsState mFrozenInsetsState;
    final android.graphics.Rect mGivenContentInsets;
    boolean mGivenInsetsPending;
    final android.graphics.Region mGivenTouchableRegion;
    final android.graphics.Rect mGivenVisibleInsets;
    float mGlobalScale;
    float mHScale;
    boolean mHasSurface;
    boolean mHaveFrame;
    boolean mHidden;
    private boolean mHiddenWhileSuspended;
    boolean mImeInsetsConsumed;
    boolean mInRelayout;
    android.view.InputChannel mInputChannel;
    android.os.IBinder mInputChannelToken;
    final com.android.server.wm.InputWindowHandleWrapper mInputWindowHandle;
    float mInvGlobalScale;
    private boolean mIsChildWindow;
    private boolean mIsDimming;
    private final boolean mIsFloatingLayer;
    final boolean mIsImWindow;
    final boolean mIsWallpaper;
    private final java.util.List<android.graphics.Rect> mKeepClearAreas;
    private com.android.internal.policy.KeyInterceptionInfo mKeyInterceptionInfo;
    private boolean mLastConfigReportedToClient;
    private final long[] mLastExclusionLogUptimeMillis;
    int mLastFreezeDuration;
    private final int[] mLastGrantedExclusionHeight;
    float mLastHScale;
    private final android.util.MergedConfiguration mLastReportedConfiguration;
    private final int[] mLastRequestedExclusionHeight;
    private int mLastRequestedHeight;
    private int mLastRequestedWidth;
    private boolean mLastShownChangedReported;
    final android.graphics.Rect mLastSurfaceInsets;
    private java.lang.CharSequence mLastTitle;
    float mLastVScale;
    int mLastVisibleLayoutRotation;
    int mLayer;
    final boolean mLayoutAttached;
    boolean mLayoutNeeded;
    int mLayoutSeq;
    boolean mLegacyPolicyVisibilityAfterAnim;
    android.util.SparseArray<android.view.InsetsSource> mMergedLocalInsetsSources;
    private boolean mMovedByResize;
    boolean mObscured;
    private android.window.OnBackInvokedCallbackInfo mOnBackInvokedCallbackInfo;
    private long mOrientationChangeRedrawRequestTime;
    private boolean mOrientationChangeTimedOut;
    private boolean mOrientationChanging;
    final float mOverrideScale;
    final boolean mOwnerCanAddInternalSystemWindow;
    final int mOwnerUid;
    com.android.server.wm.SeamlessRotator mPendingSeamlessRotate;
    boolean mPermanentlyHidden;
    final com.android.server.policy.WindowManagerPolicy mPolicy;
    private int mPolicyVisibility;
    int mPrepareSyncSeqId;
    private boolean mRedrawForSyncReported;
    boolean mRelayoutCalled;
    int mRelayoutSeq;
    boolean mRemoveOnExit;
    boolean mRemoved;
    int mRequestedHeight;
    private int mRequestedVisibleTypes;
    int mRequestedWidth;
    boolean mResizedWhileGone;
    private final java.util.function.Consumer<android.view.SurfaceControl.Transaction> mSeamlessRotationFinishedConsumer;
    boolean mSeamlesslyRotated;
    final com.android.server.wm.Session mSession;
    private final java.util.function.Consumer<android.view.SurfaceControl.Transaction> mSetSurfacePositionConsumer;
    boolean mShouldScaleWallpaper;
    final int mShowUserId;
    com.android.server.wm.StartingData mStartingData;
    private java.lang.String mStringNameCache;
    final int mSubLayer;
    boolean mSurfacePlacementNeeded;
    private final android.graphics.Point mSurfacePosition;
    private int mSurfaceTranslationY;
    int mSyncSeqId;
    private final android.graphics.Region mTapExcludeRegion;
    private final android.content.res.Configuration mTempConfiguration;
    final android.graphics.Matrix mTmpMatrix;
    final float[] mTmpMatrixArray;
    private final android.graphics.Point mTmpPoint;
    private final android.graphics.Rect mTmpRect;
    private final android.graphics.Region mTmpRegion;
    private final android.view.SurfaceControl.Transaction mTmpTransaction;

    @android.annotation.NonNull
    com.android.server.wm.WindowToken mToken;
    int mTouchableInsets;
    private final java.util.List<android.graphics.Rect> mUnrestrictedKeepClearAreas;
    float mVScale;
    int mViewVisibility;
    int mWallpaperDisplayOffsetX;
    int mWallpaperDisplayOffsetY;
    float mWallpaperScale;
    float mWallpaperX;
    float mWallpaperXStep;
    float mWallpaperY;
    float mWallpaperYStep;
    float mWallpaperZoomOut;
    private boolean mWasExiting;
    final com.android.server.wm.WindowStateAnimator mWinAnimator;
    private final com.android.server.wm.WindowFrames mWindowFrames;
    final com.android.server.wm.WindowState.WindowId mWindowId;
    boolean mWindowRemovalAllowed;
    int mXOffset;
    int mYOffset;

    class DrawHandler {
        java.util.function.Consumer<android.view.SurfaceControl.Transaction> mConsumer;
        int mSeqId;

        DrawHandler(int i, java.util.function.Consumer<android.view.SurfaceControl.Transaction> consumer) {
            this.mSeqId = i;
            this.mConsumer = consumer;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.view.SurfaceControl.Transaction transaction) {
        finishSeamlessRotation(transaction);
        updateSurfacePosition(transaction);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(android.view.SurfaceControl.Transaction transaction) {
        if (this.mSurfaceControl != null && this.mSurfaceControl.isValid() && !this.mSurfaceAnimator.hasLeash()) {
            transaction.setPosition(this.mSurfaceControl, this.mSurfacePosition.x, this.mSurfacePosition.y);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.WindowState asWindowState() {
        return this;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public boolean isRequestedVisible(int i) {
        return (i & this.mRequestedVisibleTypes) != 0;
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

    @com.android.internal.annotations.VisibleForTesting
    void setRequestedVisibleTypes(int i, int i2) {
        setRequestedVisibleTypes((i & i2) | (this.mRequestedVisibleTypes & (~i2)));
    }

    void freezeInsetsState() {
        if (this.mFrozenInsetsState == null) {
            this.mFrozenInsetsState = new android.view.InsetsState(getInsetsState(), true);
        }
    }

    void clearFrozenInsetsState() {
        this.mFrozenInsetsState = null;
    }

    android.view.InsetsState getFrozenInsetsState() {
        return this.mFrozenInsetsState;
    }

    boolean isReadyToDispatchInsetsState() {
        return (shouldCheckTokenVisibleRequested() ? isVisibleRequested() : isVisible()) && this.mFrozenInsetsState == null;
    }

    void seamlesslyRotateIfAllowed(android.view.SurfaceControl.Transaction transaction, int i, int i2, boolean z) {
        if (!isVisibleNow() || this.mIsWallpaper || this.mToken.hasFixedRotationTransform()) {
            return;
        }
        com.android.server.wm.Task task = getTask();
        if (task != null && task.inPinnedWindowingMode()) {
            return;
        }
        if (this.mPendingSeamlessRotate != null) {
            i = this.mPendingSeamlessRotate.getOldRotation();
        }
        if (this.mControllableInsetProvider != null && this.mControllableInsetProvider.getSource().getType() == android.view.WindowInsets.Type.ime()) {
            return;
        }
        if (this.mForceSeamlesslyRotate || z) {
            if (this.mControllableInsetProvider != null) {
                this.mControllableInsetProvider.startSeamlessRotation();
            }
            this.mPendingSeamlessRotate = new com.android.server.wm.SeamlessRotator(i, i2, getDisplayInfo(), false);
            this.mLastSurfacePosition.set(this.mSurfacePosition.x, this.mSurfacePosition.y);
            this.mPendingSeamlessRotate.unrotate(transaction, this);
            getDisplayContent().getDisplayRotation().markForSeamlessRotation(this, true);
            applyWithNextDraw(this.mSeamlessRotationFinishedConsumer);
        }
    }

    void cancelSeamlessRotation() {
        finishSeamlessRotation(getPendingTransaction());
    }

    void finishSeamlessRotation(android.view.SurfaceControl.Transaction transaction) {
        if (this.mPendingSeamlessRotate == null) {
            return;
        }
        this.mPendingSeamlessRotate.finish(transaction, this);
        this.mPendingSeamlessRotate = null;
        getDisplayContent().getDisplayRotation().markForSeamlessRotation(this, false);
        if (this.mControllableInsetProvider != null) {
            this.mControllableInsetProvider.finishSeamlessRotation();
        }
    }

    java.util.List<android.graphics.Rect> getSystemGestureExclusion() {
        return this.mExclusionRects;
    }

    boolean setSystemGestureExclusion(java.util.List<android.graphics.Rect> list) {
        if (this.mExclusionRects.equals(list)) {
            return false;
        }
        this.mExclusionRects.clear();
        this.mExclusionRects.addAll(list);
        return true;
    }

    boolean isImplicitlyExcludingAllSystemGestures() {
        return (this.mAttrs.insetsFlags.behavior == 2 && !isRequestedVisible(android.view.WindowInsets.Type.navigationBars())) && this.mWmService.mConstants.mSystemGestureExcludedByPreQStickyImmersive && this.mActivityRecord != null && this.mActivityRecord.mTargetSdk < 29;
    }

    void setLastExclusionHeights(int i, int i2, int i3) {
        if ((this.mLastGrantedExclusionHeight[i] == i3 && this.mLastRequestedExclusionHeight[i] == i2) ? false : true) {
            if (this.mLastShownChangedReported) {
                logExclusionRestrictions(i);
            }
            this.mLastGrantedExclusionHeight[i] = i3;
            this.mLastRequestedExclusionHeight[i] = i2;
        }
    }

    void getKeepClearAreas(java.util.Collection<android.graphics.Rect> collection, java.util.Collection<android.graphics.Rect> collection2) {
        getKeepClearAreas(collection, collection2, new android.graphics.Matrix(), new float[9]);
    }

    void getKeepClearAreas(java.util.Collection<android.graphics.Rect> collection, java.util.Collection<android.graphics.Rect> collection2, android.graphics.Matrix matrix, float[] fArr) {
        collection.addAll(getRectsInScreenSpace(this.mKeepClearAreas, matrix, fArr));
        collection2.addAll(getRectsInScreenSpace(this.mUnrestrictedKeepClearAreas, matrix, fArr));
    }

    java.util.List<android.graphics.Rect> getRectsInScreenSpace(java.util.List<android.graphics.Rect> list, android.graphics.Matrix matrix, float[] fArr) {
        getTransformationMatrix(fArr, matrix);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.graphics.RectF rectF = new android.graphics.RectF();
        java.util.Iterator<android.graphics.Rect> it = list.iterator();
        while (it.hasNext()) {
            rectF.set(it.next());
            matrix.mapRect(rectF);
            android.graphics.Rect rect = new android.graphics.Rect();
            rectF.roundOut(rect);
            arrayList.add(rect);
        }
        return arrayList;
    }

    boolean setKeepClearAreas(java.util.List<android.graphics.Rect> list, java.util.List<android.graphics.Rect> list2) {
        boolean z = !this.mKeepClearAreas.equals(list);
        boolean z2 = !this.mUnrestrictedKeepClearAreas.equals(list2);
        if (!z && !z2) {
            return false;
        }
        if (z) {
            this.mKeepClearAreas.clear();
            this.mKeepClearAreas.addAll(list);
        }
        if (z2) {
            this.mUnrestrictedKeepClearAreas.clear();
            this.mUnrestrictedKeepClearAreas.addAll(list2);
        }
        return true;
    }

    void setOnBackInvokedCallbackInfo(@android.annotation.Nullable android.window.OnBackInvokedCallbackInfo onBackInvokedCallbackInfo) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_BACK_PREVIEW, -7237767461056267619L, 0, "%s: Setting back callback %s", java.lang.String.valueOf(this), java.lang.String.valueOf(onBackInvokedCallbackInfo));
        this.mOnBackInvokedCallbackInfo = onBackInvokedCallbackInfo;
    }

    @android.annotation.Nullable
    android.window.OnBackInvokedCallbackInfo getOnBackInvokedCallbackInfo() {
        return this.mOnBackInvokedCallbackInfo;
    }

    WindowState(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.Session session, android.view.IWindow iWindow, com.android.server.wm.WindowToken windowToken, com.android.server.wm.WindowState windowState, int i, android.view.WindowManager.LayoutParams layoutParams, int i2, int i3, int i4, boolean z) {
        super(windowManagerService);
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        this.mAttrs = new android.view.WindowManager.LayoutParams();
        this.mPolicyVisibility = 3;
        boolean z7 = true;
        this.mLegacyPolicyVisibilityAfterAnim = true;
        this.mAppOpVisibility = true;
        this.mHidden = true;
        this.mDragResizingChangeReported = true;
        this.mRedrawForSyncReported = true;
        this.mSyncSeqId = 0;
        this.mPrepareSyncSeqId = 0;
        this.mRelayoutSeq = -1;
        this.mLayoutSeq = -1;
        this.mLastReportedConfiguration = new android.util.MergedConfiguration();
        this.mTempConfiguration = new android.content.res.Configuration();
        this.mGivenContentInsets = new android.graphics.Rect();
        this.mGivenVisibleInsets = new android.graphics.Rect();
        this.mGivenTouchableRegion = new android.graphics.Region();
        this.mTouchableInsets = 0;
        this.mGlobalScale = 1.0f;
        this.mInvGlobalScale = 1.0f;
        this.mCompatScale = 1.0f;
        this.mHScale = 1.0f;
        this.mVScale = 1.0f;
        this.mLastHScale = 1.0f;
        this.mLastVScale = 1.0f;
        this.mXOffset = 0;
        this.mYOffset = 0;
        this.mWallpaperScale = 1.0f;
        this.mTmpMatrix = new android.graphics.Matrix();
        this.mTmpMatrixArray = new float[9];
        this.mWindowFrames = new com.android.server.wm.WindowFrames();
        this.mClientWindowFrames = new android.window.ClientWindowFrames();
        this.mExclusionRects = new java.util.ArrayList();
        this.mKeepClearAreas = new java.util.ArrayList();
        this.mUnrestrictedKeepClearAreas = new java.util.ArrayList();
        this.mLastRequestedExclusionHeight = new int[]{0, 0};
        this.mLastGrantedExclusionHeight = new int[]{0, 0};
        this.mLastExclusionLogUptimeMillis = new long[]{0, 0};
        this.mWallpaperX = -1.0f;
        this.mWallpaperY = -1.0f;
        this.mWallpaperZoomOut = -1.0f;
        this.mWallpaperXStep = -1.0f;
        this.mWallpaperYStep = -1.0f;
        this.mWallpaperDisplayOffsetX = Integer.MIN_VALUE;
        this.mWallpaperDisplayOffsetY = Integer.MIN_VALUE;
        this.mLastVisibleLayoutRotation = -1;
        this.mHasSurface = false;
        this.mTmpRect = new android.graphics.Rect();
        this.mTmpPoint = new android.graphics.Point();
        this.mTmpRegion = new android.graphics.Region();
        this.mResizedWhileGone = false;
        this.mSeamlesslyRotated = false;
        this.mImeInsetsConsumed = false;
        this.mAboveInsetsState = new android.view.InsetsState();
        this.mMergedLocalInsetsSources = null;
        this.mLastSurfaceInsets = new android.graphics.Rect();
        this.mSurfacePosition = new android.graphics.Point();
        this.mTapExcludeRegion = new android.graphics.Region();
        this.mIsDimming = false;
        this.mRequestedVisibleTypes = android.view.WindowInsets.Type.defaultVisible();
        this.mFrameRateSelectionPriority = -1;
        this.mFrameRateVote = new com.android.server.wm.RefreshRatePolicy.FrameRateVote();
        this.mDrawHandlers = new java.util.ArrayList();
        this.mSeamlessRotationFinishedConsumer = new java.util.function.Consumer() { // from class: com.android.server.wm.WindowState$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.WindowState.this.lambda$new$0((android.view.SurfaceControl.Transaction) obj);
            }
        };
        this.mSetSurfacePositionConsumer = new java.util.function.Consumer() { // from class: com.android.server.wm.WindowState$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.WindowState.this.lambda$new$1((android.view.SurfaceControl.Transaction) obj);
            }
        };
        this.mTmpTransaction = windowManagerService.mTransactionFactory.get();
        this.mSession = session;
        this.mClient = iWindow;
        this.mAppOp = i;
        this.mToken = windowToken;
        this.mDisplayContent = windowToken.mDisplayContent;
        this.mActivityRecord = this.mToken.asActivityRecord();
        this.mOwnerUid = i3;
        this.mShowUserId = i4;
        this.mOwnerCanAddInternalSystemWindow = z;
        this.mWindowId = new com.android.server.wm.WindowState.WindowId();
        this.mAttrs.copyFrom(layoutParams);
        this.mLastSurfaceInsets.set(this.mAttrs.surfaceInsets);
        this.mViewVisibility = i2;
        this.mPolicy = this.mWmService.mPolicy;
        this.mContext = this.mWmService.mContext;
        this.mForceSeamlesslyRotate = windowToken.mRoundedCornerOverlay;
        this.mInputWindowHandle = new com.android.server.wm.InputWindowHandleWrapper(new android.view.InputWindowHandle(this.mActivityRecord != null ? this.mActivityRecord.getInputApplicationHandle(false) : null, getDisplayId()));
        this.mInputWindowHandle.setFocusable(false);
        this.mInputWindowHandle.setOwnerPid(session.mPid);
        this.mInputWindowHandle.setOwnerUid(session.mUid);
        this.mInputWindowHandle.setName(getName());
        this.mInputWindowHandle.setPackageName(this.mAttrs.packageName);
        this.mInputWindowHandle.setLayoutParamsType(this.mAttrs.type);
        if (!com.android.window.flags.Flags.surfaceTrustedOverlay()) {
            this.mInputWindowHandle.setTrustedOverlay(isWindowTrustedOverlay());
        }
        if (this.mAttrs.type < 1000 || this.mAttrs.type > 1999) {
            this.mBaseLayer = (this.mPolicy.getWindowLayerLw(this) * 10000) + 1000;
            this.mSubLayer = 0;
            this.mIsChildWindow = false;
            this.mLayoutAttached = false;
            if (this.mAttrs.type == 2011 || this.mAttrs.type == 2012) {
                z2 = true;
            } else {
                z2 = false;
            }
            this.mIsImWindow = z2;
            if (this.mAttrs.type == 2013) {
                z3 = true;
            } else {
                z3 = false;
            }
            this.mIsWallpaper = z3;
        } else {
            this.mBaseLayer = (this.mPolicy.getWindowLayerLw(windowState) * 10000) + 1000;
            this.mSubLayer = this.mPolicy.getSubWindowLayerFromTypeLw(layoutParams.type);
            this.mIsChildWindow = true;
            if (this.mAttrs.type != 1003) {
                z4 = true;
            } else {
                z4 = false;
            }
            this.mLayoutAttached = z4;
            if (windowState.mAttrs.type == 2011 || windowState.mAttrs.type == 2012) {
                z5 = true;
            } else {
                z5 = false;
            }
            this.mIsImWindow = z5;
            if (windowState.mAttrs.type == 2013) {
                z6 = true;
            } else {
                z6 = false;
            }
            this.mIsWallpaper = z6;
        }
        if (!this.mIsImWindow && !this.mIsWallpaper) {
            z7 = false;
        }
        this.mIsFloatingLayer = z7;
        if (this.mActivityRecord != null && this.mActivityRecord.mShowForAllUsers) {
            this.mAttrs.flags |= 524288;
        }
        this.mWinAnimator = new com.android.server.wm.WindowStateAnimator(this);
        this.mWinAnimator.mAlpha = layoutParams.alpha;
        this.mRequestedWidth = -1;
        this.mRequestedHeight = -1;
        this.mLastRequestedWidth = -1;
        this.mLastRequestedHeight = -1;
        this.mLayer = 0;
        this.mOverrideScale = this.mWmService.mAtmService.mCompatModePackages.getCompatScale(this.mAttrs.packageName, session.mUid);
        updateGlobalScale();
        if (this.mIsChildWindow) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 8135615413833185273L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(windowState));
            windowState.addChild(this, sWindowSubLayerComparator);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void setInitialSurfaceControlProperties(android.view.SurfaceControl.Builder builder) {
        super.setInitialSurfaceControlProperties(builder);
        if (com.android.window.flags.Flags.surfaceTrustedOverlay() && isWindowTrustedOverlay()) {
            getPendingTransaction().setTrustedOverlay(this.mSurfaceControl, true);
        }
        if (com.android.window.flags.Flags.secureWindowState()) {
            getPendingTransaction().setSecure(this.mSurfaceControl, isSecureLocked());
        }
        getPendingTransaction().setCanOccludePresentation(this.mSurfaceControl, !this.mSession.mCanAddInternalSystemWindow);
    }

    void updateTrustedOverlay() {
        this.mInputWindowHandle.setTrustedOverlay(getPendingTransaction(), this.mSurfaceControl, isWindowTrustedOverlay());
        this.mInputWindowHandle.forceChange();
    }

    boolean isWindowTrustedOverlay() {
        return com.android.server.wm.InputMonitor.isTrustedOverlay(this.mAttrs.type) || ((this.mAttrs.privateFlags & 536870912) != 0 && this.mSession.mCanAddInternalSystemWindow) || ((this.mAttrs.privateFlags & 8) != 0 && this.mSession.mCanCreateSystemApplicationOverlay);
    }

    int getTouchOcclusionMode() {
        return (android.view.WindowManager.LayoutParams.isSystemAlertWindowType(this.mAttrs.type) || isAnimating(3, -1) || inTransition()) ? 1 : 0;
    }

    void updateGlobalScale() {
        float compatScale;
        if (hasCompatScale()) {
            if (this.mOverrideScale == 1.0f || this.mToken.hasSizeCompatBounds()) {
                compatScale = this.mToken.getCompatScale();
            } else {
                compatScale = 1.0f;
            }
            this.mCompatScale = compatScale;
            this.mGlobalScale = this.mCompatScale * this.mOverrideScale;
            this.mInvGlobalScale = 1.0f / this.mGlobalScale;
            return;
        }
        this.mCompatScale = 1.0f;
        this.mInvGlobalScale = 1.0f;
        this.mGlobalScale = 1.0f;
    }

    float getCompatScaleForClient() {
        if (this.mToken.hasSizeCompatBounds()) {
            return 1.0f;
        }
        return this.mCompatScale;
    }

    boolean hasCompatScale() {
        if (this.mAttrs.type == 3) {
            return false;
        }
        if (this.mWmService.mAtmService.mCompatModePackages.useLegacyScreenCompatMode(this.mSession.mProcess.mInfo.packageName)) {
            return true;
        }
        return (this.mActivityRecord != null && this.mActivityRecord.hasSizeCompatBounds()) || this.mOverrideScale != 1.0f;
    }

    boolean getDrawnStateEvaluated() {
        return this.mDrawnStateEvaluated;
    }

    void setDrawnStateEvaluated(boolean z) {
        this.mDrawnStateEvaluated = z;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    void onParentChanged(com.android.server.wm.ConfigurationContainer configurationContainer, com.android.server.wm.ConfigurationContainer configurationContainer2) {
        super.onParentChanged(configurationContainer, configurationContainer2);
        setDrawnStateEvaluated(false);
        getDisplayContent().reapplyMagnificationSpec();
    }

    int getOwningUid() {
        return this.mOwnerUid;
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowState
    public java.lang.String getOwningPackage() {
        return this.mAttrs.packageName;
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowState
    public boolean canAddInternalSystemWindow() {
        return this.mOwnerCanAddInternalSystemWindow;
    }

    boolean skipLayout() {
        return this.mActivityRecord != null && this.mActivityRecord.mWaitForEnteringPinnedMode;
    }

    void setFrames(android.window.ClientWindowFrames clientWindowFrames, int i, int i2) {
        int i3;
        int i4;
        com.android.server.wm.WindowFrames windowFrames = this.mWindowFrames;
        this.mTmpRect.set(windowFrames.mParentFrame);
        windowFrames.mDisplayFrame.set(clientWindowFrames.displayFrame);
        windowFrames.mParentFrame.set(clientWindowFrames.parentFrame);
        windowFrames.mFrame.set(clientWindowFrames.frame);
        windowFrames.mCompatFrame.set(windowFrames.mFrame);
        if (this.mInvGlobalScale != 1.0f) {
            windowFrames.mCompatFrame.scale(this.mInvGlobalScale);
        }
        windowFrames.setParentFrameWasClippedByDisplayCutout(clientWindowFrames.isParentFrameClippedByDisplayCutout);
        windowFrames.mRelFrame.set(windowFrames.mFrame);
        com.android.server.wm.WindowContainer parent = getParent();
        if (this.mIsChildWindow) {
            com.android.server.wm.WindowState windowState = (com.android.server.wm.WindowState) parent;
            i4 = windowState.mWindowFrames.mFrame.left;
            i3 = windowState.mWindowFrames.mFrame.top;
        } else if (parent == null) {
            i3 = 0;
            i4 = 0;
        } else {
            android.graphics.Rect bounds = parent.getBounds();
            i4 = bounds.left;
            i3 = bounds.top;
        }
        windowFrames.mRelFrame.offsetTo(windowFrames.mFrame.left - i4, windowFrames.mFrame.top - i3);
        if (i != this.mLastRequestedWidth || i2 != this.mLastRequestedHeight || !this.mTmpRect.equals(windowFrames.mParentFrame)) {
            this.mLastRequestedWidth = i;
            this.mLastRequestedHeight = i2;
            windowFrames.setContentChanged(true);
        }
        if (!windowFrames.mFrame.equals(windowFrames.mLastFrame) || !windowFrames.mRelFrame.equals(windowFrames.mLastRelFrame)) {
            this.mWmService.mFrameChangingWindows.add(this);
        }
        if (this.mAttrs.type == 2034 && !windowFrames.mFrame.equals(windowFrames.mLastFrame)) {
            this.mMovedByResize = true;
        }
        if (this.mIsWallpaper) {
            android.graphics.Rect rect = windowFrames.mLastFrame;
            android.graphics.Rect rect2 = windowFrames.mFrame;
            if (rect.width() != rect2.width() || rect.height() != rect2.height()) {
                this.mDisplayContent.mWallpaperController.updateWallpaperOffset(this, false);
            }
        }
        updateSourceFrame(windowFrames.mFrame);
        if (this.mActivityRecord != null && !this.mIsChildWindow) {
            this.mActivityRecord.layoutLetterboxIfNeeded(this);
        }
        this.mSurfacePlacementNeeded = true;
        this.mHaveFrame = true;
    }

    void updateSourceFrame(android.graphics.Rect rect) {
        if (!hasInsetsSourceProvider()) {
            return;
        }
        if (this.mGivenInsetsPending && this.mAttrs.providedInsets == null) {
            return;
        }
        android.util.SparseArray<com.android.server.wm.InsetsSourceProvider> insetsSourceProviders = getInsetsSourceProviders();
        for (int size = insetsSourceProviders.size() - 1; size >= 0; size--) {
            insetsSourceProviders.valueAt(size).updateSourceFrame(rect);
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public android.graphics.Rect getBounds() {
        return this.mToken.hasSizeCompatBounds() ? this.mToken.getBounds() : super.getBounds();
    }

    android.graphics.Rect getFrame() {
        return this.mWindowFrames.mFrame;
    }

    android.graphics.Rect getRelativeFrame() {
        return this.mWindowFrames.mRelFrame;
    }

    android.graphics.Rect getDisplayFrame() {
        return this.mWindowFrames.mDisplayFrame;
    }

    android.graphics.Rect getParentFrame() {
        return this.mWindowFrames.mParentFrame;
    }

    android.view.WindowManager.LayoutParams getAttrs() {
        return this.mAttrs;
    }

    int getDisableFlags() {
        return this.mDisableFlags;
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowState
    public int getBaseType() {
        return getTopParentWindow().mAttrs.type;
    }

    boolean setReportResizeHints() {
        return this.mWindowFrames.setReportResizeHints();
    }

    void updateResizingWindowIfNeeded() {
        boolean hasInsetsChanged = this.mWindowFrames.hasInsetsChanged();
        if ((!this.mHasSurface || getDisplayContent().mLayoutSeq != this.mLayoutSeq || isGoneForLayout()) && !hasInsetsChanged) {
            return;
        }
        com.android.server.wm.WindowStateAnimator windowStateAnimator = this.mWinAnimator;
        boolean reportResizeHints = setReportResizeHints();
        boolean z = (this.mInRelayout || isLastConfigReportedToClient()) ? false : true;
        boolean z2 = !this.mDragResizingChangeReported && isDragResizeChanged();
        boolean z3 = this.mLayoutAttached && getParentWindow().frameChanged();
        if (reportResizeHints || z || hasInsetsChanged || z2 || shouldSendRedrawForSync() || z3) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RESIZE, 8842744325264128950L, com.android.internal.util.FrameworkStatsLog.BOOT_TIME_EVENT_ELAPSED_TIME_REPORTED, null, java.lang.String.valueOf(this), java.lang.String.valueOf(this.mWindowFrames.getInsetsChangedInfo()), java.lang.Boolean.valueOf(z), java.lang.Boolean.valueOf(reportResizeHints));
            consumeInsetsChange();
            onResizeHandled();
            this.mWmService.makeWindowFreezingScreenIfNeededLocked(this);
            if ((z || getOrientationChanging() || z2) && isVisibleRequested()) {
                windowStateAnimator.mDrawState = 1;
                if (this.mActivityRecord != null) {
                    this.mActivityRecord.clearAllDrawn();
                    if (this.mAttrs.type == 3 && this.mActivityRecord.mStartingData != null) {
                        this.mActivityRecord.mStartingData.mIsDisplayed = false;
                    }
                }
            }
            if (!this.mWmService.mResizingWindows.contains(this)) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RESIZE, -8636590597069784069L, 0, null, java.lang.String.valueOf(this));
                this.mWmService.mResizingWindows.add(this);
                return;
            }
            return;
        }
        if (getOrientationChanging() && isDrawn()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -2710188685736986208L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(windowStateAnimator.mSurfaceController));
            setOrientationChanging(false);
            this.mLastFreezeDuration = (int) (android.os.SystemClock.elapsedRealtime() - this.mWmService.mDisplayFreezeTime);
        }
    }

    private boolean frameChanged() {
        return !this.mWindowFrames.mFrame.equals(this.mWindowFrames.mLastFrame);
    }

    boolean getOrientationChanging() {
        if (this.mTransitionController.isShellTransitionsEnabled()) {
            return false;
        }
        return ((!this.mOrientationChanging && (!isVisible() || getConfiguration().orientation == getLastReportedConfiguration().orientation)) || this.mSeamlesslyRotated || this.mOrientationChangeTimedOut) ? false : true;
    }

    void setOrientationChanging(boolean z) {
        this.mOrientationChangeTimedOut = false;
        if (this.mOrientationChanging == z) {
            return;
        }
        this.mOrientationChanging = z;
        if (z) {
            this.mLastFreezeDuration = 0;
            if (this.mWmService.mRoot.mOrientationChangeComplete && this.mDisplayContent.shouldSyncRotationChange(this)) {
                this.mWmService.mRoot.mOrientationChangeComplete = false;
                return;
            }
            return;
        }
        this.mDisplayContent.finishAsyncRotation(this.mToken);
    }

    void orientationChangeTimedOut() {
        this.mOrientationChangeTimedOut = true;
    }

    @Override // com.android.server.wm.WindowContainer
    void onDisplayChanged(com.android.server.wm.DisplayContent displayContent) {
        if (displayContent != null && this.mDisplayContent != null && displayContent != this.mDisplayContent && this.mDisplayContent.getImeInputTarget() == this) {
            displayContent.updateImeInputAndControlTarget(getImeInputTarget());
            this.mDisplayContent.setImeInputTarget(null);
        }
        super.onDisplayChanged(displayContent);
        if (displayContent != null && this.mInputWindowHandle.getDisplayId() != displayContent.getDisplayId()) {
            this.mLayoutSeq = displayContent.mLayoutSeq - 1;
            this.mInputWindowHandle.setDisplayId(displayContent.getDisplayId());
        }
    }

    com.android.server.wm.DisplayFrames getDisplayFrames(com.android.server.wm.DisplayFrames displayFrames) {
        com.android.server.wm.DisplayFrames fixedRotationTransformDisplayFrames = this.mToken.getFixedRotationTransformDisplayFrames();
        if (fixedRotationTransformDisplayFrames != null) {
            return fixedRotationTransformDisplayFrames;
        }
        return displayFrames;
    }

    android.view.DisplayInfo getDisplayInfo() {
        android.view.DisplayInfo fixedRotationTransformDisplayInfo = this.mToken.getFixedRotationTransformDisplayInfo();
        if (fixedRotationTransformDisplayInfo != null) {
            return fixedRotationTransformDisplayInfo;
        }
        return getDisplayContent().getDisplayInfo();
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public android.graphics.Rect getMaxBounds() {
        android.graphics.Rect fixedRotationTransformMaxBounds = this.mToken.getFixedRotationTransformMaxBounds();
        if (fixedRotationTransformMaxBounds != null) {
            return fixedRotationTransformMaxBounds;
        }
        return super.getMaxBounds();
    }

    android.view.InsetsState getInsetsState() {
        return getInsetsState(false);
    }

    android.view.InsetsState getInsetsState(boolean z) {
        android.view.InsetsState fixedRotationTransformInsetsState = this.mToken.getFixedRotationTransformInsetsState();
        com.android.server.wm.InsetsPolicy insetsPolicy = getDisplayContent().getInsetsPolicy();
        if (fixedRotationTransformInsetsState != null) {
            return insetsPolicy.adjustInsetsForWindow(this, fixedRotationTransformInsetsState);
        }
        return insetsPolicy.adjustInsetsForWindow(this, insetsPolicy.enforceInsetsPolicyForTarget(this.mAttrs, getWindowingMode(), isAlwaysOnTop(), this.mFrozenInsetsState != null ? this.mFrozenInsetsState : getMergedInsetsState()), z);
    }

    private android.view.InsetsState getMergedInsetsState() {
        android.view.InsetsState insetsState;
        if (this.mAttrs.receiveInsetsIgnoringZOrder) {
            insetsState = getDisplayContent().getInsetsStateController().getRawInsetsState();
        } else {
            insetsState = this.mAboveInsetsState;
        }
        if (this.mMergedLocalInsetsSources == null) {
            return insetsState;
        }
        android.view.InsetsState insetsState2 = new android.view.InsetsState(insetsState);
        for (int i = 0; i < this.mMergedLocalInsetsSources.size(); i++) {
            insetsState2.addSource(this.mMergedLocalInsetsSources.valueAt(i));
        }
        return insetsState2;
    }

    android.view.InsetsState getCompatInsetsState() {
        android.view.InsetsState insetsState = getInsetsState();
        if (this.mInvGlobalScale != 1.0f) {
            android.view.InsetsState insetsState2 = new android.view.InsetsState(insetsState, true);
            insetsState2.scale(this.mInvGlobalScale);
            return insetsState2;
        }
        return insetsState;
    }

    android.view.InsetsState getInsetsStateWithVisibilityOverride() {
        android.view.InsetsState insetsState = new android.view.InsetsState(getInsetsState(), true);
        for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
            android.view.InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
            boolean isRequestedVisible = isRequestedVisible(sourceAt.getType());
            if (sourceAt.isVisible() != isRequestedVisible) {
                sourceAt.setVisible(isRequestedVisible);
            }
        }
        return insetsState;
    }

    @Override // com.android.server.wm.InputTarget
    public int getDisplayId() {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        if (displayContent == null) {
            return -1;
        }
        return displayContent.getDisplayId();
    }

    @Override // com.android.server.wm.InputTarget
    public com.android.server.wm.WindowState getWindowState() {
        return this;
    }

    @Override // com.android.server.wm.InputTarget
    public android.os.IBinder getWindowToken() {
        return this.mClient.asBinder();
    }

    @Override // com.android.server.wm.InputTarget
    public int getPid() {
        return this.mSession.mPid;
    }

    @Override // com.android.server.wm.InputTarget
    public int getUid() {
        return this.mSession.mUid;
    }

    com.android.server.wm.Task getTask() {
        if (this.mActivityRecord != null) {
            return this.mActivityRecord.getTask();
        }
        return null;
    }

    @android.annotation.Nullable
    com.android.server.wm.TaskFragment getTaskFragment() {
        if (this.mActivityRecord != null) {
            return this.mActivityRecord.getTaskFragment();
        }
        return null;
    }

    @android.annotation.Nullable
    com.android.server.wm.Task getRootTask() {
        com.android.server.wm.Task task = getTask();
        if (task != null) {
            return task.getRootTask();
        }
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        if (this.mAttrs.type < 2000 || displayContent == null) {
            return null;
        }
        return displayContent.getDefaultTaskDisplayArea().getRootHomeTask();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void getVisibleBounds(android.graphics.Rect rect) {
        com.android.server.wm.Task task = getTask();
        boolean z = false;
        boolean z2 = task != null && task.cropWindowsToRootTaskBounds();
        rect.setEmpty();
        this.mTmpRect.setEmpty();
        if (z2) {
            com.android.server.wm.Task rootTask = task.getRootTask();
            if (rootTask != null) {
                rootTask.getDimBounds(this.mTmpRect);
            }
            rect.set(this.mWindowFrames.mFrame);
            rect.inset(getInsetsStateWithVisibilityOverride().calculateVisibleInsets(rect, this.mAttrs.type, getActivityType(), this.mAttrs.softInputMode, this.mAttrs.flags));
            if (!z) {
                rect.intersect(this.mTmpRect);
                return;
            }
            return;
        }
        z = z2;
        rect.set(this.mWindowFrames.mFrame);
        rect.inset(getInsetsStateWithVisibilityOverride().calculateVisibleInsets(rect, this.mAttrs.type, getActivityType(), this.mAttrs.softInputMode, this.mAttrs.flags));
        if (!z) {
        }
    }

    public long getInputDispatchingTimeoutMillis() {
        if (this.mActivityRecord != null) {
            return this.mActivityRecord.mInputDispatchingTimeoutMillis;
        }
        return android.os.InputConstants.DEFAULT_DISPATCHING_TIMEOUT_MILLIS;
    }

    boolean hasAppShownWindows() {
        return this.mActivityRecord != null && (this.mActivityRecord.firstWindowDrawn || this.mActivityRecord.isStartingWindowDisplayed());
    }

    @Override // com.android.server.wm.WindowContainer
    boolean hasContentToDisplay() {
        if (!this.mAppFreezing && isDrawn()) {
            if (this.mViewVisibility != 0) {
                if (isAnimating(3) && !getDisplayContent().mAppTransition.isTransitionSet()) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return super.hasContentToDisplay();
    }

    private boolean isVisibleByPolicyOrInsets() {
        return isVisibleByPolicy() && (this.mControllableInsetProvider == null || this.mControllableInsetProvider.isClientVisible());
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isVisible() {
        return wouldBeVisibleIfPolicyIgnored() && isVisibleByPolicyOrInsets();
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isVisibleRequested() {
        boolean z = wouldBeVisibleRequestedIfPolicyIgnored() && isVisibleByPolicyOrInsets();
        if (z && shouldCheckTokenVisibleRequested()) {
            return this.mToken.isVisibleRequested();
        }
        return z;
    }

    boolean shouldCheckTokenVisibleRequested() {
        return (this.mActivityRecord == null && this.mToken.asWallpaperToken() == null) ? false : true;
    }

    boolean isVisibleByPolicy() {
        return (this.mPolicyVisibility & 3) == 3;
    }

    boolean providesDisplayDecorInsets() {
        if (this.mInsetsSourceProviders == null) {
            return false;
        }
        for (int size = this.mInsetsSourceProviders.size() - 1; size >= 0; size--) {
            if ((this.mInsetsSourceProviders.valueAt(size).getSource().getType() & this.mWmService.mConfigTypes) != 0) {
                return true;
            }
        }
        return false;
    }

    void clearPolicyVisibilityFlag(int i) {
        this.mPolicyVisibility = (~i) & this.mPolicyVisibility;
        this.mWmService.scheduleAnimationLocked();
    }

    void setPolicyVisibilityFlag(int i) {
        this.mPolicyVisibility = i | this.mPolicyVisibility;
        this.mWmService.scheduleAnimationLocked();
    }

    private boolean isLegacyPolicyVisibility() {
        return (this.mPolicyVisibility & 1) != 0;
    }

    boolean wouldBeVisibleIfPolicyIgnored() {
        if (!this.mHasSurface || isParentWindowHidden() || this.mAnimatingExit || this.mDestroying) {
            return false;
        }
        return !(this.mToken.asWallpaperToken() != null) || this.mToken.isVisible();
    }

    private boolean wouldBeVisibleRequestedIfPolicyIgnored() {
        com.android.server.wm.WindowState parentWindow = getParentWindow();
        if (((parentWindow == null || parentWindow.isVisibleRequested()) ? false : true) || this.mAnimatingExit || this.mDestroying) {
            return false;
        }
        return !(this.mToken.asWallpaperToken() != null) || this.mToken.isVisibleRequested();
    }

    boolean isVisibleNow() {
        return (this.mToken.isVisible() || this.mAttrs.type == 3) && isVisible();
    }

    boolean isPotentialDragTarget(boolean z) {
        return ((!z && !isVisibleNow()) || this.mRemoved || this.mInputChannel == null || this.mInputWindowHandle == null) ? false : true;
    }

    boolean isVisibleRequestedOrAdding() {
        com.android.server.wm.ActivityRecord activityRecord = this.mActivityRecord;
        return (this.mHasSurface || (!this.mRelayoutCalled && this.mViewVisibility == 0)) && isVisibleByPolicy() && !isParentWindowHidden() && !((activityRecord != null && !activityRecord.isVisibleRequested()) || this.mAnimatingExit || this.mDestroying);
    }

    boolean isOnScreen() {
        if (!this.mHasSurface || this.mDestroying || !isVisibleByPolicy()) {
            return false;
        }
        com.android.server.wm.ActivityRecord activityRecord = this.mActivityRecord;
        if (activityRecord != null) {
            return (!isParentWindowHidden() && (isStartingWindowAssociatedToTask() ? this.mStartingData.mAssociatedTask.isVisible() : activityRecord.isVisible())) || isAnimationRunningSelfOrParent();
        }
        com.android.server.wm.WallpaperWindowToken asWallpaperToken = this.mToken.asWallpaperToken();
        return asWallpaperToken != null ? !isParentWindowHidden() && asWallpaperToken.isVisible() : !isParentWindowHidden() || isAnimating(3);
    }

    boolean isDreamWindow() {
        return this.mActivityRecord != null && this.mActivityRecord.getActivityType() == 5;
    }

    boolean isSecureLocked() {
        if ((this.mAttrs.flags & 8192) == 0 && !this.mWmService.mSensitiveContentPackages.shouldBlockScreenCaptureForApp(getOwningPackage(), getOwningUid(), getWindowToken())) {
            return !android.app.admin.DevicePolicyCache.getInstance().isScreenCaptureAllowed(this.mShowUserId);
        }
        return true;
    }

    boolean mightAffectAllDrawn() {
        return ((!isOnScreen() && !(this.mWinAnimator.mAttrType == 1 || this.mWinAnimator.mAttrType == 4)) || this.mAnimatingExit || this.mDestroying) ? false : true;
    }

    boolean isInteresting() {
        com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mWmService.getRecentsAnimationController();
        return this.mActivityRecord != null && !(this.mActivityRecord.isFreezingScreen() && this.mAppFreezing) && this.mViewVisibility == 0 && (recentsAnimationController == null || recentsAnimationController.isInterestingForAllDrawn(this));
    }

    boolean isReadyForDisplay() {
        boolean z = !isParentWindowHidden() && this.mViewVisibility == 0 && this.mToken.isVisible();
        if (this.mHasSurface && isVisibleByPolicy() && !this.mDestroying) {
            return z || isAnimating(3);
        }
        return false;
    }

    boolean isFullyTransparent() {
        return this.mAttrs.alpha == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    }

    boolean canAffectSystemUiFlags() {
        if (isFullyTransparent()) {
            return false;
        }
        if (this.mActivityRecord == null) {
            return this.mWinAnimator.getShown() && !(this.mAnimatingExit || this.mDestroying);
        }
        if (this.mActivityRecord.canAffectSystemUiFlags()) {
            return (this.mAttrs.type == 3 && (this.mStartingData instanceof com.android.server.wm.SnapshotStartingData)) ? false : true;
        }
        return false;
    }

    boolean isDisplayed() {
        com.android.server.wm.ActivityRecord activityRecord = this.mActivityRecord;
        return isDrawn() && isVisibleByPolicy() && ((!isParentWindowHidden() && (activityRecord == null || activityRecord.isVisibleRequested())) || isAnimationRunningSelfOrParent());
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowState
    public boolean isAnimatingLw() {
        return isAnimating(3);
    }

    boolean isGoneForLayout() {
        com.android.server.wm.ActivityRecord activityRecord = this.mActivityRecord;
        return this.mViewVisibility == 8 || !this.mRelayoutCalled || (activityRecord == null && !(wouldBeVisibleIfPolicyIgnored() && isVisibleByPolicy())) || (!(activityRecord == null || activityRecord.isVisibleRequested()) || isParentWindowGoneForLayout() || ((this.mAnimatingExit && !isAnimatingLw()) || this.mDestroying));
    }

    public boolean isDrawFinishedLw() {
        return this.mHasSurface && !this.mDestroying && (this.mWinAnimator.mDrawState == 2 || this.mWinAnimator.mDrawState == 3 || this.mWinAnimator.mDrawState == 4);
    }

    boolean isDrawn() {
        return this.mHasSurface && !this.mDestroying && (this.mWinAnimator.mDrawState == 3 || this.mWinAnimator.mDrawState == 4);
    }

    private boolean isOpaqueDrawn() {
        boolean z = this.mToken.asWallpaperToken() != null;
        return ((!z && this.mAttrs.format == -1) || (z && this.mToken.isVisible())) && isDrawn() && !isAnimating(3);
    }

    void requestDrawIfNeeded(java.util.List<com.android.server.wm.WindowState> list) {
        if (!isVisible()) {
            return;
        }
        com.android.server.wm.WallpaperWindowToken asWallpaperToken = this.mToken.asWallpaperToken();
        if (asWallpaperToken != null) {
            if (asWallpaperToken.hasVisibleNotDrawnWallpaper()) {
                list.add(this);
                return;
            }
            return;
        }
        if (this.mActivityRecord != null) {
            if (!this.mActivityRecord.isVisibleRequested() || this.mActivityRecord.allDrawn) {
                return;
            }
            if (this.mAttrs.type == 3) {
                if (isDrawn()) {
                    return;
                }
            } else if (this.mActivityRecord.mStartingWindow != null) {
                return;
            }
        } else if (!this.mPolicy.isKeyguardHostWindow(this.mAttrs)) {
            return;
        }
        this.mWinAnimator.mDrawState = 1;
        forceReportingResized();
        list.add(this);
    }

    @Override // com.android.server.wm.WindowContainer
    void onMovedByResize() {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RESIZE, 5236278969232209904L, 0, null, java.lang.String.valueOf(this));
        this.mMovedByResize = true;
        super.onMovedByResize();
    }

    void onAppVisibilityChanged(boolean z, boolean z2) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((com.android.server.wm.WindowState) this.mChildren.get(size)).onAppVisibilityChanged(z, z2);
        }
        boolean isVisibleNow = isVisibleNow();
        if (this.mAttrs.type == 3) {
            if (!z && isVisibleNow && this.mActivityRecord.isAnimating(3)) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, 7646042751617940718L, 0, null, java.lang.String.valueOf(this));
                this.mAnimatingExit = true;
                this.mRemoveOnExit = true;
                this.mWindowRemovalAllowed = true;
                return;
            }
            return;
        }
        if (z != isVisibleNow) {
            if (!z2 && isVisibleNow) {
                com.android.server.wm.AccessibilityController accessibilityController = this.mWmService.mAccessibilityController;
                this.mWinAnimator.applyAnimationLocked(2, false);
                if (accessibilityController.hasCallbacks()) {
                    accessibilityController.onWindowTransition(this, 2);
                }
            }
            setDisplayLayoutNeeded();
        }
    }

    boolean onSetAppExiting(boolean z) {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        boolean z2 = false;
        if (!z) {
            this.mPermanentlyHidden = true;
            hide(false, false);
        }
        if (isVisibleNow() && z) {
            this.mWinAnimator.applyAnimationLocked(2, false);
            if (this.mWmService.mAccessibilityController.hasCallbacks()) {
                this.mWmService.mAccessibilityController.onWindowTransition(this, 2);
            }
            if (displayContent != null) {
                displayContent.setLayoutNeeded();
            }
            z2 = true;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            z2 |= ((com.android.server.wm.WindowState) this.mChildren.get(size)).onSetAppExiting(z);
        }
        return z2;
    }

    @Override // com.android.server.wm.WindowContainer
    void onResize() {
        java.util.ArrayList<com.android.server.wm.WindowState> arrayList = this.mWmService.mResizingWindows;
        if (this.mHasSurface && !isGoneForLayout() && !arrayList.contains(this)) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RESIZE, 1783521309242112490L, 0, null, java.lang.String.valueOf(this));
            arrayList.add(this);
        }
        if (isGoneForLayout()) {
            this.mResizedWhileGone = true;
        }
        super.onResize();
    }

    void handleWindowMovedIfNeeded() {
        if (!hasMoved()) {
            return;
        }
        int i = this.mWindowFrames.mFrame.left;
        int i2 = this.mWindowFrames.mFrame.top;
        if (canPlayMoveAnimation()) {
            startMoveAnimation(i, i2);
        }
        if (this.mWmService.mAccessibilityController.hasCallbacks()) {
            this.mWmService.mAccessibilityController.onSomeWindowResizedOrMoved(getDisplayId());
        }
        try {
            this.mClient.moved(i, i2);
        } catch (android.os.RemoteException e) {
        }
        this.mMovedByResize = false;
    }

    private boolean canPlayMoveAnimation() {
        boolean hasMovementAnimations;
        if (getTask() == null) {
            hasMovementAnimations = getWindowConfiguration().hasMovementAnimations();
        } else {
            hasMovementAnimations = getTask().getWindowConfiguration().hasMovementAnimations();
        }
        return this.mToken.okToAnimate() && (this.mAttrs.privateFlags & 64) == 0 && !isDragResizing() && hasMovementAnimations && !this.mWinAnimator.mLastHidden && !this.mSeamlesslyRotated;
    }

    private boolean hasMoved() {
        return this.mHasSurface && !((!this.mWindowFrames.hasContentChanged() && !this.mMovedByResize) || this.mAnimatingExit || ((this.mWindowFrames.mRelFrame.top == this.mWindowFrames.mLastRelFrame.top && this.mWindowFrames.mRelFrame.left == this.mWindowFrames.mLastRelFrame.left) || ((this.mIsChildWindow && getParentWindow().hasMoved()) || this.mTransitionController.isCollecting())));
    }

    boolean isObscuringDisplay() {
        com.android.server.wm.Task task = getTask();
        return (task == null || task.fillsParent()) && isOpaqueDrawn() && fillsDisplay();
    }

    boolean fillsDisplay() {
        android.view.DisplayInfo displayInfo = getDisplayInfo();
        return this.mWindowFrames.mFrame.left <= 0 && this.mWindowFrames.mFrame.top <= 0 && this.mWindowFrames.mFrame.right >= displayInfo.appWidth && this.mWindowFrames.mFrame.bottom >= displayInfo.appHeight;
    }

    boolean matchesDisplayAreaBounds() {
        android.graphics.Rect fixedRotationTransformDisplayBounds = this.mToken.getFixedRotationTransformDisplayBounds();
        if (fixedRotationTransformDisplayBounds != null) {
            return fixedRotationTransformDisplayBounds.equals(getBounds());
        }
        com.android.server.wm.DisplayArea displayArea = getDisplayArea();
        if (displayArea == null) {
            return getDisplayContent().getBounds().equals(getBounds());
        }
        return displayArea.getBounds().equals(getBounds());
    }

    boolean isLastConfigReportedToClient() {
        return this.mLastConfigReportedToClient;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void onConfigurationChanged(android.content.res.Configuration configuration) {
        android.content.res.Configuration configuration2 = super.getConfiguration();
        this.mTempConfiguration.setTo(configuration2);
        super.onConfigurationChanged(configuration);
        int diff = configuration2.diff(this.mTempConfiguration);
        if (diff != 0) {
            this.mLastConfigReportedToClient = false;
        }
        if ((getDisplayContent().getImeInputTarget() == this || isImeLayeringTarget()) && (diff & 536870912) != 0) {
            this.mDisplayContent.updateImeControlTarget(isImeLayeringTarget());
            if (this.mStartingData != null && this.mStartingData.mAssociatedTask == null && this.mTempConfiguration.windowConfiguration.getRotation() == configuration2.windowConfiguration.getRotation() && !this.mTempConfiguration.windowConfiguration.getBounds().equals(getBounds())) {
                this.mStartingData.mResizedFromTransfer = true;
                this.mActivityRecord.associateStartingWindowWithTaskIfNeeded();
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void removeImmediately() {
        if (this.mRemoved) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 1351053513466395411L, 0, null, java.lang.String.valueOf(this));
            return;
        }
        this.mRemoved = true;
        this.mWinAnimator.destroySurfaceLocked(getSyncTransaction());
        if (!this.mDrawHandlers.isEmpty()) {
            this.mWmService.mH.removeMessages(64, this);
        }
        super.removeImmediately();
        if (isImeOverlayLayeringTarget()) {
            this.mWmService.dispatchImeTargetOverlayVisibilityChanged(this.mClient.asBinder(), this.mAttrs.type, false, true);
        }
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        if (isImeLayeringTarget()) {
            displayContent.removeImeSurfaceByTarget(this);
            displayContent.setImeLayeringTarget(null);
            displayContent.computeImeTarget(true);
        }
        if (displayContent.getImeInputTarget() == this && !inRelaunchingActivity()) {
            this.mWmService.dispatchImeInputTargetVisibilityChanged(this.mClient.asBinder(), false, true);
            displayContent.updateImeInputAndControlTarget(null);
        }
        int i = this.mAttrs.type;
        if (com.android.server.wm.WindowManagerService.excludeWindowTypeFromTapOutTask(i)) {
            displayContent.mTapExcludedWindows.remove(this);
        }
        if (i == 2037 || i == 2030) {
            this.mWmService.mDisplayManagerInternal.onPresentation(displayContent.getDisplay().getDisplayId(), false);
        }
        displayContent.mTapExcludeProvidingWindows.remove(this);
        displayContent.getDisplayPolicy().removeWindowLw(this);
        disposeInputChannel();
        this.mOnBackInvokedCallbackInfo = null;
        this.mSession.onWindowRemoved(this);
        this.mWmService.postWindowRemoveCleanupLocked(this);
        consumeInsetsChange();
    }

    @Override // com.android.server.wm.WindowContainer
    void removeIfPossible() {
        boolean z;
        this.mWindowRemovalAllowed = true;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 3927343382258792268L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(android.os.Debug.getCallers(5)));
        boolean z2 = this.mStartingData != null;
        if (z2) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -4831815184899821371L, 0, null, java.lang.String.valueOf(this));
            if (this.mActivityRecord != null) {
                this.mActivityRecord.forAllWindows(new com.android.internal.util.ToBooleanFunction() { // from class: com.android.server.wm.WindowState$$ExternalSyntheticLambda3
                    public final boolean apply(java.lang.Object obj) {
                        boolean lambda$removeIfPossible$2;
                        lambda$removeIfPossible$2 = com.android.server.wm.WindowState.lambda$removeIfPossible$2((com.android.server.wm.WindowState) obj);
                        return lambda$removeIfPossible$2;
                    }
                }, true);
            }
            this.mTransitionController.mTransitionTracer.logRemovingStartingWindow(this.mStartingData);
        } else if (this.mAttrs.type == 1 && isSelfAnimating(0, 128)) {
            cancelAnimation();
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS, -5803097884846965819L, 1, null, java.lang.Long.valueOf(java.lang.System.identityHashCode(this.mClient.asBinder())), java.lang.String.valueOf(this.mWinAnimator.mSurfaceController), java.lang.String.valueOf(android.os.Debug.getCallers(5)));
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            disposeInputChannel();
            this.mOnBackInvokedCallbackInfo = null;
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -2547748024041128829L, 262128, null, java.lang.String.valueOf(this), java.lang.String.valueOf(this.mWinAnimator.mSurfaceController), java.lang.Boolean.valueOf(this.mAnimatingExit), java.lang.Boolean.valueOf(this.mRemoveOnExit), java.lang.Boolean.valueOf(this.mHasSurface), java.lang.Boolean.valueOf(this.mWinAnimator.getShown()), java.lang.Boolean.valueOf(isAnimating(3)), java.lang.Boolean.valueOf(this.mActivityRecord != null && this.mActivityRecord.isAnimating(3)), java.lang.Boolean.valueOf(this.mWmService.mDisplayFrozen), java.lang.String.valueOf(android.os.Debug.getCallers(6)));
            if (this.mHasSurface && this.mToken.okToAnimate()) {
                z = isVisible();
                boolean z3 = (displayContent.inTransition() || inRelaunchingActivity()) ? false : true;
                if (z && isDisplayed()) {
                    int i = !z2 ? 2 : 5;
                    if (z3 && this.mWinAnimator.applyAnimationLocked(i, false)) {
                        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, 7789778354950913237L, 0, null, java.lang.String.valueOf(this));
                        if (z2 && this.mSurfaceAnimator.hasLeash()) {
                            getPendingTransaction().setLayer(this.mSurfaceAnimator.mLeash, Integer.MAX_VALUE);
                        }
                        this.mAnimatingExit = true;
                        setDisplayLayoutNeeded();
                        this.mWmService.requestTraversal();
                    }
                    if (this.mWmService.mAccessibilityController.hasCallbacks()) {
                        this.mWmService.mAccessibilityController.onWindowTransition(this, i);
                    }
                }
                boolean z4 = z3 && (this.mAnimatingExit || isAnimationRunningSelfOrParent());
                boolean z5 = z2 && this.mActivityRecord != null && this.mActivityRecord.isLastWindow(this);
                if (this.mWinAnimator.getShown() && !z5 && z4) {
                    this.mAnimatingExit = true;
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -4143841388126586338L, 0, null, java.lang.String.valueOf(this));
                    com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, 4419190702135590390L, 0, null, java.lang.String.valueOf(this));
                    setupWindowForRemoveOnExit();
                    if (this.mActivityRecord != null) {
                        this.mActivityRecord.updateReportedVisibilityLocked();
                    }
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return;
                }
            } else {
                z = false;
            }
            boolean providesDisplayDecorInsets = providesDisplayDecorInsets();
            removeImmediately();
            boolean z6 = z && displayContent.updateOrientation();
            if (providesDisplayDecorInsets) {
                z6 |= displayContent.getDisplayPolicy().updateDecorInsetsInfo();
            }
            if (z6) {
                displayContent.sendNewConfiguration();
            }
            this.mWmService.updateFocusedWindowLocked(isFocused() ? 4 : 0, true);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeIfPossible$2(com.android.server.wm.WindowState windowState) {
        if (!windowState.isSelfAnimating(0, 128)) {
            return false;
        }
        windowState.cancelAnimation();
        return true;
    }

    private void setupWindowForRemoveOnExit() {
        this.mRemoveOnExit = true;
        setDisplayLayoutNeeded();
        getDisplayContent().getDisplayPolicy().removeWindowLw(this);
        boolean updateFocusedWindowLocked = this.mWmService.updateFocusedWindowLocked(3, false);
        this.mWmService.mWindowPlacerLocked.performSurfacePlacement();
        if (updateFocusedWindowLocked) {
            getDisplayContent().getInputMonitor().updateInputWindowsLw(false);
        }
    }

    void setHasSurface(boolean z) {
        this.mHasSurface = z;
    }

    boolean canBeImeTarget() {
        int i;
        if (this.mIsImWindow || inPinnedWindowingMode() || this.mAttrs.type == 2036) {
            return false;
        }
        if (!(this.mActivityRecord == null || this.mActivityRecord.windowsAreFocusable())) {
            return false;
        }
        com.android.server.wm.Task rootTask = getRootTask();
        if (rootTask != null && !rootTask.isFocusable()) {
            return false;
        }
        if (this.mAttrs.type != 3 && (i = this.mAttrs.flags & 131080) != 0 && i != 131080) {
            return false;
        }
        if (rootTask == null || this.mActivityRecord == null || !this.mTransitionController.isTransientLaunch(this.mActivityRecord)) {
            return isVisibleRequestedOrAdding() || (isVisible() && this.mActivityRecord != null && this.mActivityRecord.isVisible());
        }
        return false;
    }

    void openInputChannel(@android.annotation.NonNull android.view.InputChannel inputChannel) {
        if (this.mInputChannel != null) {
            throw new java.lang.IllegalStateException("Window already has an input channel.");
        }
        this.mInputChannel = this.mWmService.mInputManager.createInputChannel(getName());
        this.mInputChannelToken = this.mInputChannel.getToken();
        this.mInputWindowHandle.setToken(this.mInputChannelToken);
        this.mWmService.mInputToWindowMap.put(this.mInputChannelToken, this);
        this.mInputChannel.copyTo(inputChannel);
    }

    @java.lang.Deprecated
    public boolean transferTouch() {
        return this.mWmService.mInputManager.transferTouch(this.mInputChannelToken, getDisplayId());
    }

    void disposeInputChannel() {
        if (this.mInputChannelToken != null) {
            this.mWmService.mInputManager.removeInputChannel(this.mInputChannelToken);
            this.mWmService.mKeyInterceptionInfoForToken.remove(this.mInputChannelToken);
            this.mWmService.mInputToWindowMap.remove(this.mInputChannelToken);
            this.mInputChannelToken = null;
        }
        if (this.mInputChannel != null) {
            this.mInputChannel.dispose();
            this.mInputChannel = null;
        }
        this.mInputWindowHandle.setToken(null);
    }

    void setDisplayLayoutNeeded() {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        if (displayContent != null) {
            displayContent.setLayoutNeeded();
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void switchUser(int i) {
        super.switchUser(i);
        if (showToCurrentUser()) {
            setPolicyVisibilityFlag(2);
        } else {
            clearPolicyVisibilityFlag(2);
        }
    }

    void getSurfaceTouchableRegion(android.graphics.Region region, android.view.WindowManager.LayoutParams layoutParams) {
        boolean isModal = layoutParams.isModal();
        if (isModal) {
            if (this.mActivityRecord != null) {
                updateRegionForModalActivityWindow(region);
            } else {
                getDisplayContent().getBounds(this.mTmpRect);
                int width = this.mTmpRect.width();
                int height = this.mTmpRect.height();
                region.set(-width, -height, width + width, height + height);
            }
            subtractTouchExcludeRegionIfNeeded(region);
        } else {
            getTouchableRegion(region);
        }
        android.graphics.Rect rect = this.mWindowFrames.mFrame;
        if (rect.left != 0 || rect.top != 0) {
            region.translate(-rect.left, -rect.top);
        }
        if (isModal && this.mTouchableInsets == 3) {
            this.mTmpRegion.set(0, 0, rect.right, rect.bottom);
            this.mTmpRegion.op(this.mGivenTouchableRegion, android.graphics.Region.Op.DIFFERENCE);
            region.op(this.mTmpRegion, android.graphics.Region.Op.DIFFERENCE);
        }
        if (this.mInvGlobalScale != 1.0f) {
            region.scale(this.mInvGlobalScale);
        }
    }

    private void adjustRegionInFreefromWindowMode(android.graphics.Rect rect) {
        if (!inFreeformWindowingMode()) {
            return;
        }
        int i = -com.android.server.wm.WindowManagerService.dipToPixel(30, getDisplayContent().getDisplayMetrics());
        rect.inset(i, i);
    }

    private void updateRegionForModalActivityWindow(android.graphics.Region region) {
        this.mActivityRecord.getLetterboxInnerBounds(this.mTmpRect);
        if (this.mTmpRect.isEmpty()) {
            android.graphics.Rect fixedRotationTransformDisplayBounds = this.mActivityRecord.getFixedRotationTransformDisplayBounds();
            if (fixedRotationTransformDisplayBounds != null) {
                this.mTmpRect.set(fixedRotationTransformDisplayBounds);
            } else {
                com.android.server.wm.TaskFragment taskFragment = getTaskFragment();
                if (taskFragment != null) {
                    taskFragment.getDimBounds(this.mTmpRect);
                } else if (getRootTask() != null) {
                    getRootTask().getDimBounds(this.mTmpRect);
                }
            }
        }
        adjustRegionInFreefromWindowMode(this.mTmpRect);
        region.set(this.mTmpRect);
        cropRegionToRootTaskBoundsIfNeeded(region);
    }

    void checkPolicyVisibilityChange() {
        if (isLegacyPolicyVisibility() != this.mLegacyPolicyVisibilityAfterAnim) {
            if (this.mLegacyPolicyVisibilityAfterAnim) {
                setPolicyVisibilityFlag(1);
            } else {
                clearPolicyVisibilityFlag(1);
            }
            if (!isVisibleByPolicy()) {
                this.mWinAnimator.hide(getPendingTransaction(), "checkPolicyVisibilityChange");
                if (isFocused()) {
                    com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -6167820560758523840L, 0, null, null);
                    this.mWmService.mFocusMayChange = true;
                }
                setDisplayLayoutNeeded();
                this.mWmService.enableScreenIfNeededLocked();
            }
        }
    }

    void setRequestedSize(int i, int i2) {
        if (this.mRequestedWidth != i || this.mRequestedHeight != i2) {
            this.mLayoutNeeded = true;
            this.mRequestedWidth = i;
            this.mRequestedHeight = i2;
        }
    }

    void prepareWindowToDisplayDuringRelayout(boolean z) {
        if ((this.mAttrs.flags & 2097152) != 0 || (this.mActivityRecord != null && this.mActivityRecord.canTurnScreenOn())) {
            boolean z2 = this.mWmService.mAllowTheaterModeWakeFromLayout || android.provider.Settings.Global.getInt(this.mWmService.mContext.getContentResolver(), "theater_mode_on", 0) == 0;
            boolean z3 = this.mActivityRecord == null || this.mActivityRecord.currentLaunchCanTurnScreenOn();
            if (z2 && z3 && (this.mWmService.mAtmService.isDreaming() || !this.mWmService.mPowerManager.isInteractive())) {
                this.mWmService.mPowerManager.wakeUp(android.os.SystemClock.uptimeMillis(), 2, "android.server.wm:SCREEN_ON_FLAG");
            }
            if (this.mActivityRecord != null) {
                this.mActivityRecord.setCurrentLaunchCanTurnScreenOn(false);
            }
        }
        if (z) {
            return;
        }
        if ((this.mAttrs.softInputMode & com.android.internal.util.FrameworkStatsLog.BOOT_TIME_EVENT_ELAPSED_TIME_REPORTED) == 16) {
            this.mLayoutNeeded = true;
        }
        if (isDrawn() && this.mToken.okToAnimate()) {
            this.mWinAnimator.applyEnterAnimationLocked();
        }
    }

    private android.content.res.Configuration getProcessGlobalConfiguration() {
        com.android.server.wm.WindowState parentWindow = getParentWindow();
        com.android.server.wm.Session session = parentWindow != null ? parentWindow.mSession : this.mSession;
        return session.mPid == com.android.server.wm.WindowManagerService.MY_PID ? this.mWmService.mRoot.getConfiguration() : session.mProcess.getConfiguration();
    }

    private android.content.res.Configuration getLastReportedConfiguration() {
        return this.mLastReportedConfiguration.getMergedConfiguration();
    }

    void adjustStartingWindowFlags() {
        if (this.mAttrs.type == 1 && this.mActivityRecord != null && this.mActivityRecord.mStartingWindow != null) {
            android.view.WindowManager.LayoutParams layoutParams = this.mActivityRecord.mStartingWindow.mAttrs;
            layoutParams.flags = (layoutParams.flags & (-4718594)) | (this.mAttrs.flags & 4718593);
        }
    }

    void setWindowScale(int i, int i2) {
        float f;
        float f2 = 1.0f;
        if ((this.mAttrs.flags & 16384) != 0) {
            if (this.mAttrs.width == i) {
                f = 1.0f;
            } else {
                f = this.mAttrs.width / i;
            }
            this.mHScale = f;
            if (this.mAttrs.height != i2) {
                f2 = this.mAttrs.height / i2;
            }
            this.mVScale = f2;
            return;
        }
        this.mVScale = 1.0f;
        this.mHScale = 1.0f;
    }

    boolean canReceiveKeys() {
        return canReceiveKeys(false);
    }

    public java.lang.String canReceiveKeysReason(boolean z) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("fromTouch= ");
        sb.append(z);
        sb.append(" isVisibleRequestedOrAdding=");
        sb.append(isVisibleRequestedOrAdding());
        sb.append(" mViewVisibility=");
        sb.append(this.mViewVisibility);
        sb.append(" mRemoveOnExit=");
        sb.append(this.mRemoveOnExit);
        sb.append(" flags=");
        sb.append(this.mAttrs.flags);
        sb.append(" appWindowsAreFocusable=");
        sb.append(this.mActivityRecord == null || this.mActivityRecord.windowsAreFocusable(z));
        sb.append(" canReceiveTouchInput=");
        sb.append(canReceiveTouchInput());
        sb.append(" displayIsOnTop=");
        sb.append(getDisplayContent().isOnTop());
        sb.append(" displayIsTrusted=");
        sb.append(getDisplayContent().isTrusted());
        sb.append(" transitShouldKeepFocus=");
        sb.append(this.mActivityRecord != null && this.mTransitionController.shouldKeepFocus(this.mActivityRecord));
        return sb.toString();
    }

    public boolean canReceiveKeys(boolean z) {
        if (this.mActivityRecord != null && this.mTransitionController.shouldKeepFocus(this.mActivityRecord)) {
            return true;
        }
        if (isVisibleRequestedOrAdding() && this.mViewVisibility == 0 && !this.mRemoveOnExit && (this.mAttrs.flags & 8) == 0 && (this.mActivityRecord == null || this.mActivityRecord.windowsAreFocusable(z)) && (this.mActivityRecord == null || this.mActivityRecord.getTask() == null || !this.mActivityRecord.getTask().getRootTask().shouldIgnoreInput())) {
            return z || getDisplayContent().isOnTop() || getDisplayContent().isTrusted();
        }
        return false;
    }

    @Override // com.android.server.policy.WindowManagerPolicy.WindowState
    public boolean canShowWhenLocked() {
        if (this.mActivityRecord != null) {
            return this.mActivityRecord.canShowWhenLocked();
        }
        return (this.mAttrs.flags & 524288) != 0;
    }

    boolean canReceiveTouchInput() {
        if (this.mActivityRecord == null || this.mActivityRecord.getTask() == null || this.mTransitionController.shouldKeepFocus(this.mActivityRecord)) {
            return true;
        }
        return !this.mActivityRecord.getTask().getRootTask().shouldIgnoreInput() && this.mActivityRecord.isVisibleRequested();
    }

    @java.lang.Deprecated
    boolean hasDrawn() {
        return this.mWinAnimator.mDrawState == 4;
    }

    boolean show(boolean z, boolean z2) {
        if ((isLegacyPolicyVisibility() && this.mLegacyPolicyVisibilityAfterAnim) || !showToCurrentUser() || !this.mAppOpVisibility || this.mPermanentlyHidden || this.mHiddenWhileSuspended || this.mForceHideNonSystemOverlayWindow) {
            return false;
        }
        if (z) {
            if (!this.mToken.okToAnimate()) {
                z = false;
            } else if (isLegacyPolicyVisibility() && !isAnimating(3)) {
                z = false;
            }
        }
        setPolicyVisibilityFlag(1);
        this.mLegacyPolicyVisibilityAfterAnim = true;
        if (z) {
            this.mWinAnimator.applyAnimationLocked(1, true);
        }
        if (z2) {
            this.mWmService.scheduleAnimationLocked();
        }
        if ((this.mAttrs.flags & 8) == 0) {
            this.mWmService.updateFocusedWindowLocked(0, false);
        }
        return true;
    }

    boolean hide(boolean z, boolean z2) {
        if (z && !this.mToken.okToAnimate()) {
            z = false;
        }
        if (!(z ? this.mLegacyPolicyVisibilityAfterAnim : isLegacyPolicyVisibility())) {
            return false;
        }
        if (z && !this.mWinAnimator.applyAnimationLocked(2, false)) {
            z = false;
        }
        this.mLegacyPolicyVisibilityAfterAnim = false;
        boolean isFocused = isFocused();
        if (!z) {
            clearPolicyVisibilityFlag(1);
            this.mWmService.enableScreenIfNeededLocked();
            if (isFocused) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_FOCUS_LIGHT, -208079497999140637L, 0, null, null);
                this.mWmService.mFocusMayChange = true;
            }
        }
        if (z2) {
            this.mWmService.scheduleAnimationLocked();
        }
        if (isFocused) {
            this.mWmService.updateFocusedWindowLocked(0, false);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setForceHideNonSystemOverlayWindowIfNeeded(boolean z) {
        int baseType = getBaseType();
        if (!this.mSession.mCanAddInternalSystemWindow) {
            if (!android.view.WindowManager.LayoutParams.isSystemAlertWindowType(baseType) && baseType != 2005) {
                return;
            }
            if ((baseType == 2038 && this.mAttrs.isSystemApplicationOverlay() && this.mSession.mCanCreateSystemApplicationOverlay) || this.mForceHideNonSystemOverlayWindow == z) {
                return;
            }
            this.mForceHideNonSystemOverlayWindow = z;
            if (z) {
                hide(true, true);
            } else {
                show(true, true);
            }
        }
    }

    void setHiddenWhileSuspended(boolean z) {
        if (!this.mOwnerCanAddInternalSystemWindow) {
            if ((!android.view.WindowManager.LayoutParams.isSystemAlertWindowType(this.mAttrs.type) && this.mAttrs.type != 2005) || this.mHiddenWhileSuspended == z) {
                return;
            }
            this.mHiddenWhileSuspended = z;
            if (z) {
                hide(true, true);
            } else {
                show(true, true);
            }
        }
    }

    private void setAppOpVisibilityLw(boolean z) {
        if (this.mAppOpVisibility != z) {
            this.mAppOpVisibility = z;
            if (z) {
                show(true, true);
            } else {
                hide(true, true);
            }
        }
    }

    void initAppOpsState() {
        int startOpNoThrow;
        if (this.mAppOp != -1 && this.mAppOpVisibility && (startOpNoThrow = this.mWmService.mAppOps.startOpNoThrow(this.mAppOp, getOwningUid(), getOwningPackage(), true, null, "init-default-visibility")) != 0 && startOpNoThrow != 3) {
            setAppOpVisibilityLw(false);
        }
    }

    void resetAppOpsState() {
        if (this.mAppOp != -1 && this.mAppOpVisibility) {
            this.mWmService.mAppOps.finishOp(this.mAppOp, getOwningUid(), getOwningPackage(), (java.lang.String) null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void updateAppOpsState() {
        if (this.mAppOp == -1) {
            return;
        }
        int owningUid = getOwningUid();
        java.lang.String owningPackage = getOwningPackage();
        if (this.mAppOpVisibility) {
            int checkOpNoThrow = this.mWmService.mAppOps.checkOpNoThrow(this.mAppOp, owningUid, owningPackage);
            if (checkOpNoThrow != 0 && checkOpNoThrow != 3) {
                this.mWmService.mAppOps.finishOp(this.mAppOp, owningUid, owningPackage, (java.lang.String) null);
                setAppOpVisibilityLw(false);
                return;
            }
            return;
        }
        int startOpNoThrow = this.mWmService.mAppOps.startOpNoThrow(this.mAppOp, owningUid, owningPackage, true, null, "attempt-to-be-visible");
        if (startOpNoThrow == 0 || startOpNoThrow == 3) {
            setAppOpVisibilityLw(true);
        }
    }

    public void hidePermanentlyLw() {
        if (!this.mPermanentlyHidden) {
            this.mPermanentlyHidden = true;
            hide(true, true);
        }
    }

    public void pokeDrawLockLw(long j) {
        if (isVisibleRequestedOrAdding()) {
            if (this.mDrawLock == null) {
                java.lang.CharSequence windowTag = getWindowTag();
                this.mDrawLock = this.mWmService.mPowerManager.newWakeLock(128, "Window:" + ((java.lang.Object) windowTag));
                this.mDrawLock.setReferenceCounted(false);
                this.mDrawLock.setWorkSource(new android.os.WorkSource(this.mOwnerUid, this.mAttrs.packageName));
            }
            this.mDrawLock.acquire(j);
        }
    }

    boolean isAlive() {
        return this.mClient.asBinder().isBinderAlive();
    }

    @Override // com.android.server.wm.WindowContainer
    void sendAppVisibilityToClients() {
        super.sendAppVisibilityToClients();
        boolean isClientVisible = this.mToken.isClientVisible();
        if (this.mAttrs.type == 3 && !isClientVisible) {
            return;
        }
        try {
            this.mClient.dispatchAppVisibility(isClientVisible);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Exception thrown during dispatchAppVisibility " + this, e);
            if (android.os.Process.getUidForPid(this.mSession.mPid) == this.mSession.mUid) {
                android.os.Process.killProcess(this.mSession.mPid);
            }
        }
    }

    void onStartFreezingScreen() {
        this.mAppFreezing = true;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((com.android.server.wm.WindowState) this.mChildren.get(size)).onStartFreezingScreen();
        }
    }

    boolean onStopFreezingScreen() {
        boolean z = false;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            z |= ((com.android.server.wm.WindowState) this.mChildren.get(size)).onStopFreezingScreen();
        }
        if (!this.mAppFreezing) {
            return z;
        }
        this.mAppFreezing = false;
        if (this.mHasSurface && !getOrientationChanging() && this.mWmService.mWindowsFreezingScreen != 2) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 8812513438749898553L, 0, null, java.lang.String.valueOf(this));
            setOrientationChanging(true);
        }
        this.mLastFreezeDuration = 0;
        setDisplayLayoutNeeded();
        return true;
    }

    boolean destroySurface(boolean z, boolean z2) {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mChildren);
        boolean z3 = false;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            z3 |= ((com.android.server.wm.WindowState) arrayList.get(size)).destroySurface(z, z2);
        }
        if (!z2 && !this.mWindowRemovalAllowed && !z) {
            return z3;
        }
        if (!this.mDestroying) {
            return z3;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.e(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, -2964267636425934067L, android.hardware.audio.common.V2_0.AudioChannelMask.IN_6, null, java.lang.String.valueOf(this), java.lang.Boolean.valueOf(z2), java.lang.Boolean.valueOf(this.mWindowRemovalAllowed), java.lang.Boolean.valueOf(this.mRemoveOnExit));
        if (!z || this.mRemoveOnExit) {
            destroySurfaceUnchecked();
        }
        if (this.mRemoveOnExit) {
            removeImmediately();
        }
        if (z) {
            requestUpdateWallpaperIfNeeded();
        }
        this.mDestroying = false;
        if (!getDisplayContent().mAppTransition.isTransitionSet() || !getDisplayContent().mOpeningApps.contains(this.mActivityRecord)) {
            return true;
        }
        this.mWmService.mWindowPlacerLocked.requestTraversal();
        return true;
    }

    void destroySurfaceUnchecked() {
        this.mWinAnimator.destroySurfaceLocked(this.mTmpTransaction);
        this.mTmpTransaction.apply();
        this.mAnimatingExit = false;
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, 7336961102428192483L, 0, null, java.lang.String.valueOf(this));
        if (syncNextBuffer()) {
            immediatelyNotifyBlastSync();
        }
    }

    void onSurfaceShownChanged(boolean z) {
        if (this.mLastShownChangedReported == z) {
            return;
        }
        this.mLastShownChangedReported = z;
        if (z) {
            initExclusionRestrictions();
        } else {
            logExclusionRestrictions(0);
            logExclusionRestrictions(1);
            getDisplayContent().removeImeSurfaceByTarget(this);
        }
        if (this.mAttrs.type >= 2000 && this.mAttrs.type != 2005 && this.mAttrs.type != 2030) {
            if (this.mAttrs.type != 2037 || !isOnVirtualDisplay()) {
                this.mWmService.mAtmService.mActiveUids.onNonAppSurfaceVisibilityChanged(this.mOwnerUid, z);
            }
        }
    }

    private boolean isOnVirtualDisplay() {
        return getDisplayContent().mDisplay.getType() == 5;
    }

    private void logExclusionRestrictions(int i) {
        if (!com.android.server.wm.DisplayContent.logsGestureExclusionRestrictions(this) || android.os.SystemClock.uptimeMillis() < this.mLastExclusionLogUptimeMillis[i] + this.mWmService.mConstants.mSystemGestureExclusionLogDebounceTimeoutMillis) {
            return;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        long j = uptimeMillis - this.mLastExclusionLogUptimeMillis[i];
        this.mLastExclusionLogUptimeMillis[i] = uptimeMillis;
        int i2 = this.mLastRequestedExclusionHeight[i];
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.EXCLUSION_RECT_STATE_CHANGED, this.mAttrs.packageName, i2, i2 - this.mLastGrantedExclusionHeight[i], i + 1, getConfiguration().orientation == 2, false, (int) j);
    }

    private void initExclusionRestrictions() {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        this.mLastExclusionLogUptimeMillis[0] = uptimeMillis;
        this.mLastExclusionLogUptimeMillis[1] = uptimeMillis;
    }

    boolean showForAllUsers() {
        switch (this.mAttrs.type) {
            case 3:
            case 2000:
            case 2001:
            case 2002:
            case 2007:
            case 2008:
            case 2009:
            case 2017:
            case 2018:
            case 2019:
            case com.android.server.notification.NotificationShellCmd.NOTIFICATION_ID /* 2020 */:
            case 2021:
            case 2022:
            case 2024:
            case 2026:
            case 2027:
            case 2030:
            case 2034:
            case 2037:
            case 2039:
            case 2040:
            case 2041:
                break;
            default:
                if ((this.mAttrs.privateFlags & 16) == 0) {
                    return false;
                }
                break;
        }
        return this.mOwnerCanAddInternalSystemWindow;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean showToCurrentUser() {
        com.android.server.wm.WindowState topParentWindow = getTopParentWindow();
        return (topParentWindow.mAttrs.type < 2000 && topParentWindow.mActivityRecord != null && topParentWindow.mActivityRecord.mShowForAllUsers && topParentWindow.getFrame().left <= topParentWindow.getDisplayFrame().left && topParentWindow.getFrame().top <= topParentWindow.getDisplayFrame().top && topParentWindow.getFrame().right >= topParentWindow.getDisplayFrame().right && topParentWindow.getFrame().bottom >= topParentWindow.getDisplayFrame().bottom) || topParentWindow.showForAllUsers() || this.mWmService.isUserVisible(topParentWindow.mShowUserId);
    }

    private static void applyInsets(android.graphics.Region region, android.graphics.Rect rect, android.graphics.Rect rect2) {
        region.set(rect.left + rect2.left, rect.top + rect2.top, rect.right - rect2.right, rect.bottom - rect2.bottom);
    }

    void getTouchableRegion(android.graphics.Region region) {
        android.graphics.Rect rect = this.mWindowFrames.mFrame;
        switch (this.mTouchableInsets) {
            case 1:
                applyInsets(region, rect, this.mGivenContentInsets);
                break;
            case 2:
                applyInsets(region, rect, this.mGivenVisibleInsets);
                break;
            case 3:
                region.set(this.mGivenTouchableRegion);
                if (rect.left != 0 || rect.top != 0) {
                    region.translate(rect.left, rect.top);
                    break;
                }
                break;
            default:
                region.set(rect);
                break;
        }
        cropRegionToRootTaskBoundsIfNeeded(region);
        subtractTouchExcludeRegionIfNeeded(region);
    }

    void getEffectiveTouchableRegion(android.graphics.Region region) {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        if (this.mAttrs.isModal() && displayContent != null) {
            region.set(displayContent.getBounds());
            cropRegionToRootTaskBoundsIfNeeded(region);
            subtractTouchExcludeRegionIfNeeded(region);
            return;
        }
        getTouchableRegion(region);
    }

    private void cropRegionToRootTaskBoundsIfNeeded(android.graphics.Region region) {
        com.android.server.wm.Task rootTask;
        com.android.server.wm.Task task = getTask();
        if (task == null || !task.cropWindowsToRootTaskBounds() || (rootTask = task.getRootTask()) == null || rootTask.mCreatedByOrganizer) {
            return;
        }
        rootTask.getDimBounds(this.mTmpRect);
        adjustRegionInFreefromWindowMode(this.mTmpRect);
        region.op(this.mTmpRect, android.graphics.Region.Op.INTERSECT);
    }

    private void subtractTouchExcludeRegionIfNeeded(android.graphics.Region region) {
        if (this.mTapExcludeRegion.isEmpty()) {
            return;
        }
        android.graphics.Region obtain = android.graphics.Region.obtain();
        getTapExcludeRegion(obtain);
        if (!obtain.isEmpty()) {
            region.op(obtain, android.graphics.Region.Op.DIFFERENCE);
        }
        obtain.recycle();
    }

    void reportFocusChangedSerialized(boolean z) {
        if (this.mFocusCallbacks != null) {
            int beginBroadcast = this.mFocusCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                android.view.IWindowFocusObserver broadcastItem = this.mFocusCallbacks.getBroadcastItem(i);
                if (z) {
                    try {
                        broadcastItem.focusGained(this.mWindowId.asBinder());
                    } catch (android.os.RemoteException e) {
                    }
                } else {
                    broadcastItem.focusLost(this.mWindowId.asBinder());
                }
            }
            this.mFocusCallbacks.finishBroadcast();
        }
    }

    @Override // com.android.server.wm.ConfigurationContainer
    public android.content.res.Configuration getConfiguration() {
        if (!registeredForDisplayAreaConfigChanges()) {
            return super.getConfiguration();
        }
        this.mTempConfiguration.setTo(getProcessGlobalConfiguration());
        this.mTempConfiguration.updateFrom(getMergedOverrideConfiguration());
        return this.mTempConfiguration;
    }

    private boolean registeredForDisplayAreaConfigChanges() {
        com.android.server.wm.WindowState parentWindow = getParentWindow();
        com.android.server.wm.Session session = parentWindow != null ? parentWindow.mSession : this.mSession;
        if (session.mPid == com.android.server.wm.WindowManagerService.MY_PID) {
            return false;
        }
        return session.mProcess.registeredForDisplayAreaConfigChanges();
    }

    @android.annotation.NonNull
    com.android.server.wm.WindowProcessController getProcess() {
        return this.mSession.mProcess;
    }

    void fillClientWindowFramesAndConfiguration(android.window.ClientWindowFrames clientWindowFrames, android.util.MergedConfiguration mergedConfiguration, boolean z, boolean z2) {
        clientWindowFrames.frame.set(this.mWindowFrames.mCompatFrame);
        clientWindowFrames.displayFrame.set(this.mWindowFrames.mDisplayFrame);
        if (this.mInvGlobalScale != 1.0f) {
            clientWindowFrames.displayFrame.scale(this.mInvGlobalScale);
        }
        if (this.mLayoutAttached) {
            if (clientWindowFrames.attachedFrame == null) {
                clientWindowFrames.attachedFrame = new android.graphics.Rect();
            }
            clientWindowFrames.attachedFrame.set(getParentWindow().getFrame());
            if (this.mInvGlobalScale != 1.0f) {
                clientWindowFrames.attachedFrame.scale(this.mInvGlobalScale);
            }
        }
        clientWindowFrames.compatScale = getCompatScaleForClient();
        if (z || (z2 && (this.mActivityRecord == null || this.mActivityRecord.isVisibleRequested()))) {
            mergedConfiguration.setConfiguration(getProcessGlobalConfiguration(), getMergedOverrideConfiguration());
            if (mergedConfiguration != this.mLastReportedConfiguration) {
                this.mLastReportedConfiguration.setTo(mergedConfiguration);
            }
        } else {
            mergedConfiguration.setTo(this.mLastReportedConfiguration);
        }
        this.mLastConfigReportedToClient = true;
    }

    void reportResized() {
        int i;
        if (inRelaunchingActivity()) {
            return;
        }
        if (shouldCheckTokenVisibleRequested() && !this.mToken.isVisibleRequested()) {
            return;
        }
        if (android.os.Trace.isTagEnabled(32L)) {
            android.os.Trace.traceBegin(32L, "wm.reportResized_" + ((java.lang.Object) getWindowTag()));
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_RESIZE, -6920306331987525705L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(this.mWindowFrames.mCompatFrame));
        boolean z = this.mWinAnimator.mDrawState == 1;
        if (z) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 2714651498627020992L, 0, null, java.lang.String.valueOf(this));
        }
        this.mDragResizingChangeReported = true;
        this.mWindowFrames.clearReportResizeHints();
        int rotation = this.mLastReportedConfiguration.getMergedConfiguration().windowConfiguration.getRotation();
        fillClientWindowFramesAndConfiguration(this.mClientWindowFrames, this.mLastReportedConfiguration, true, false);
        boolean shouldSendRedrawForSync = shouldSendRedrawForSync();
        boolean z2 = shouldSendRedrawForSync && shouldSyncWithBuffers();
        boolean z3 = shouldSendRedrawForSync || z;
        boolean isDragResizeChanged = isDragResizeChanged();
        boolean z4 = z2 || isDragResizeChanged;
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        boolean areSystemBarsForcedConsumedLw = displayContent.getDisplayPolicy().areSystemBarsForcedConsumedLw();
        int displayId = displayContent.getDisplayId();
        if (isDragResizeChanged) {
            setDragResizing();
        }
        boolean isDragResizing = isDragResizing();
        markRedrawForSyncReported();
        if (com.android.window.flags.Flags.bundleClientTransactionFlag()) {
            getProcess().scheduleClientTransactionItem(android.app.servertransaction.WindowStateResizeItem.obtain(this.mClient, this.mClientWindowFrames, z3, this.mLastReportedConfiguration, getCompatInsetsState(), z4, areSystemBarsForcedConsumedLw, displayId, z2 ? this.mSyncSeqId : -1, isDragResizing));
            onResizePostDispatched(z, rotation, displayId);
        } else {
            try {
                android.view.IWindow iWindow = this.mClient;
                android.window.ClientWindowFrames clientWindowFrames = this.mClientWindowFrames;
                android.util.MergedConfiguration mergedConfiguration = this.mLastReportedConfiguration;
                android.view.InsetsState compatInsetsState = getCompatInsetsState();
                if (z2) {
                    i = this.mSyncSeqId;
                } else {
                    i = -1;
                }
                iWindow.resized(clientWindowFrames, z3, mergedConfiguration, compatInsetsState, z4, areSystemBarsForcedConsumedLw, displayId, i, isDragResizing);
                onResizePostDispatched(z, rotation, displayId);
            } catch (android.os.RemoteException e) {
                setOrientationChanging(false);
                this.mLastFreezeDuration = (int) (android.os.SystemClock.elapsedRealtime() - this.mWmService.mDisplayFreezeTime);
                android.util.Slog.w(TAG, "Failed to report 'resized' to " + this + " due to " + e);
            }
        }
        android.os.Trace.traceEnd(32L);
    }

    private void onResizePostDispatched(boolean z, int i, int i2) {
        if (z && i >= 0 && i != this.mLastReportedConfiguration.getMergedConfiguration().windowConfiguration.getRotation()) {
            this.mOrientationChangeRedrawRequestTime = android.os.SystemClock.elapsedRealtime();
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -5755338358883139945L, 0, null, java.lang.String.valueOf(this));
        }
        if (this.mWmService.mAccessibilityController.hasCallbacks()) {
            this.mWmService.mAccessibilityController.onSomeWindowResizedOrMoved(i2);
        }
    }

    boolean inRelaunchingActivity() {
        return this.mActivityRecord != null && this.mActivityRecord.isRelaunching();
    }

    boolean isClientLocal() {
        return this.mClient instanceof android.view.IWindow.Stub;
    }

    private void consumeInsetsChange() {
        if (this.mWindowFrames.hasInsetsChanged()) {
            this.mWindowFrames.setInsetsChanged(false);
            com.android.server.wm.WindowManagerService windowManagerService = this.mWmService;
            windowManagerService.mWindowsInsetsChanged--;
            if (this.mWmService.mWindowsInsetsChanged == 0) {
                this.mWmService.mH.removeMessages(66);
            }
        }
    }

    void notifyInsetsChanged() {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_INSETS, -5211036212243647844L, 0, null, java.lang.String.valueOf(this));
        if (!this.mWindowFrames.hasInsetsChanged()) {
            this.mWindowFrames.setInsetsChanged(true);
            this.mWmService.mWindowsInsetsChanged++;
            this.mWmService.mH.removeMessages(66);
            this.mWmService.mH.sendEmptyMessage(66);
        }
        com.android.server.wm.WindowContainer parent = getParent();
        if (parent != null) {
            parent.updateOverlayInsetsState(this);
        }
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public void notifyInsetsControlChanged(int i) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WINDOW_INSETS, -3186229270467822891L, 0, null, java.lang.String.valueOf(this));
        if (this.mRemoved) {
            return;
        }
        try {
            this.mClient.insetsControlChanged(getCompatInsetsState(), getDisplayContent().getInsetsStateController().getControlsForDispatch(this));
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to deliver inset control state change to w=" + this, e);
        }
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public com.android.server.wm.WindowState getWindow() {
        return this;
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public void showInsets(int i, boolean z, @android.annotation.Nullable android.view.inputmethod.ImeTracker.Token token) {
        try {
            android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 21);
            this.mClient.showInsets(i, z, token);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to deliver showInsets", e);
            android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 21);
        }
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public void hideInsets(int i, boolean z, @android.annotation.Nullable android.view.inputmethod.ImeTracker.Token token) {
        try {
            android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 22);
            this.mClient.hideInsets(i, z, token);
        } catch (android.os.RemoteException e) {
            android.util.Slog.w(TAG, "Failed to deliver hideInsets", e);
            android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 22);
        }
    }

    @Override // com.android.server.wm.InsetsControlTarget
    public boolean canShowTransient() {
        return (this.mAttrs.insetsFlags.behavior & 2) != 0;
    }

    boolean canBeHiddenByKeyguard() {
        if (this.mActivityRecord != null) {
            return false;
        }
        switch (this.mAttrs.type) {
            case 2000:
            case 2013:
            case 2019:
            case 2040:
                break;
            default:
                if (this.mPolicy.getWindowLayerLw(this) < this.mPolicy.getWindowLayerFromTypeLw(2040)) {
                }
                break;
        }
        return false;
    }

    private int getRootTaskId() {
        com.android.server.wm.Task rootTask = getRootTask();
        if (rootTask == null) {
            return -1;
        }
        return rootTask.mTaskId;
    }

    public void registerFocusObserver(android.view.IWindowFocusObserver iWindowFocusObserver) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mFocusCallbacks == null) {
                    this.mFocusCallbacks = new android.os.RemoteCallbackList<>();
                }
                this.mFocusCallbacks.register(iWindowFocusObserver);
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    public void unregisterFocusObserver(android.view.IWindowFocusObserver iWindowFocusObserver) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mWmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mFocusCallbacks != null) {
                    this.mFocusCallbacks.unregister(iWindowFocusObserver);
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    boolean isFocused() {
        return getDisplayContent().mCurrentFocus == this;
    }

    boolean areAppWindowBoundsLetterboxed() {
        return (this.mActivityRecord == null || isStartingWindowAssociatedToTask() || (!this.mActivityRecord.areBoundsLetterboxed() && !isLetterboxedForDisplayCutout())) ? false : true;
    }

    boolean isLetterboxedForDisplayCutout() {
        if (this.mActivityRecord != null && this.mWindowFrames.parentFrameWasClippedByDisplayCutout() && this.mAttrs.layoutInDisplayCutoutMode != 3 && this.mAttrs.isFullscreen()) {
            return !frameCoversEntireAppTokenBounds();
        }
        return false;
    }

    private boolean frameCoversEntireAppTokenBounds() {
        this.mTmpRect.set(this.mActivityRecord.getBounds());
        this.mTmpRect.intersectUnchecked(this.mWindowFrames.mFrame);
        return this.mActivityRecord.getBounds().equals(this.mTmpRect);
    }

    boolean isFullyTransparentBarAllowed(android.graphics.Rect rect) {
        return this.mActivityRecord == null || this.mActivityRecord.isFullyTransparentBarAllowed(rect);
    }

    boolean isDragResizeChanged() {
        return this.mDragResizing != computeDragResizing();
    }

    @Override // com.android.server.wm.WindowContainer
    void resetDragResizingChangeReported() {
        this.mDragResizingChangeReported = false;
        super.resetDragResizingChangeReported();
    }

    private boolean computeDragResizing() {
        com.android.server.wm.Task task = getTask();
        if (task == null) {
            return false;
        }
        if ((!inFreeformWindowingMode() && !task.getRootTask().mCreatedByOrganizer) || task.getActivityType() == 2 || this.mAttrs.width != -1 || this.mAttrs.height != -1 || !task.isDragResizing()) {
            return false;
        }
        return true;
    }

    void setDragResizing() {
        boolean computeDragResizing = computeDragResizing();
        if (computeDragResizing == this.mDragResizing) {
            return;
        }
        this.mDragResizing = computeDragResizing;
    }

    boolean isDragResizing() {
        return this.mDragResizing;
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.ConfigurationContainer
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        boolean isVisible = isVisible();
        if (i == 2 && !isVisible) {
            return;
        }
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268033L, i);
        protoOutputStream.write(1120986464259L, getDisplayId());
        protoOutputStream.write(1120986464260L, getRootTaskId());
        this.mAttrs.dumpDebug(protoOutputStream, 1146756268037L);
        this.mGivenContentInsets.dumpDebug(protoOutputStream, 1146756268038L);
        this.mWindowFrames.dumpDebug(protoOutputStream, 1146756268073L);
        this.mAttrs.surfaceInsets.dumpDebug(protoOutputStream, 1146756268044L);
        android.graphics.GraphicsProtos.dumpPointProto(this.mSurfacePosition, protoOutputStream, 1146756268048L);
        this.mWinAnimator.dumpDebug(protoOutputStream, 1146756268045L);
        protoOutputStream.write(1133871366158L, this.mAnimatingExit);
        protoOutputStream.write(1120986464274L, this.mRequestedWidth);
        protoOutputStream.write(1120986464275L, this.mRequestedHeight);
        protoOutputStream.write(1120986464276L, this.mViewVisibility);
        protoOutputStream.write(1133871366166L, this.mHasSurface);
        protoOutputStream.write(1133871366167L, isReadyForDisplay());
        protoOutputStream.write(1133871366178L, this.mRemoveOnExit);
        protoOutputStream.write(1133871366179L, this.mDestroying);
        protoOutputStream.write(1133871366180L, this.mRemoved);
        protoOutputStream.write(1133871366181L, isOnScreen());
        protoOutputStream.write(1133871366182L, isVisible);
        protoOutputStream.write(1133871366183L, this.mPendingSeamlessRotate != null);
        protoOutputStream.write(1133871366186L, this.mForceSeamlesslyRotate);
        protoOutputStream.write(1133871366187L, hasCompatScale());
        protoOutputStream.write(1108101562412L, this.mGlobalScale);
        protoOutputStream.write(1120986464304L, this.mRequestedVisibleTypes);
        java.util.Iterator<android.graphics.Rect> it = this.mKeepClearAreas.iterator();
        while (it.hasNext()) {
            it.next().dumpDebug(protoOutputStream, 2246267895853L);
        }
        java.util.Iterator<android.graphics.Rect> it2 = this.mUnrestrictedKeepClearAreas.iterator();
        while (it2.hasNext()) {
            it2.next().dumpDebug(protoOutputStream, 2246267895854L);
        }
        if (this.mMergedLocalInsetsSources != null) {
            for (int i2 = 0; i2 < this.mMergedLocalInsetsSources.size(); i2++) {
                this.mMergedLocalInsetsSources.valueAt(i2).dumpDebug(protoOutputStream, 2246267895855L);
            }
        }
        protoOutputStream.end(start);
    }

    @Override // com.android.server.wm.WindowContainer
    long getProtoFieldId() {
        return 1146756268040L;
    }

    @Override // com.android.server.wm.WindowContainer
    public void writeIdentifierToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, java.lang.System.identityHashCode(this));
        protoOutputStream.write(1120986464258L, this.mShowUserId);
        java.lang.CharSequence windowTag = getWindowTag();
        if (windowTag != null) {
            protoOutputStream.write(1138166333443L, windowTag.toString());
        }
        protoOutputStream.end(start);
    }

    @Override // com.android.server.wm.WindowContainer
    @dalvik.annotation.optimization.NeverCompile
    void dump(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        printWriter.print(str + "mDisplayId=" + getDisplayId());
        if (getRootTask() != null) {
            printWriter.print(" rootTaskId=" + getRootTaskId());
        }
        printWriter.println(" mSession=" + this.mSession + " mClient=" + this.mClient.asBinder());
        printWriter.println(str + "mOwnerUid=" + this.mOwnerUid + " showForAllUsers=" + showForAllUsers() + " package=" + this.mAttrs.packageName + " appop=" + android.app.AppOpsManager.opToName(this.mAppOp));
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append("mAttrs=");
        sb.append(this.mAttrs.toString(str));
        printWriter.println(sb.toString());
        printWriter.println(str + "Requested w=" + this.mRequestedWidth + " h=" + this.mRequestedHeight + " mLayoutSeq=" + this.mLayoutSeq);
        if (this.mRequestedWidth != this.mLastRequestedWidth || this.mRequestedHeight != this.mLastRequestedHeight) {
            printWriter.println(str + "LastRequested w=" + this.mLastRequestedWidth + " h=" + this.mLastRequestedHeight);
        }
        if (this.mIsChildWindow || this.mLayoutAttached) {
            printWriter.println(str + "mParentWindow=" + getParentWindow() + " mLayoutAttached=" + this.mLayoutAttached);
        }
        if (this.mIsImWindow || this.mIsWallpaper || this.mIsFloatingLayer) {
            printWriter.println(str + "mIsImWindow=" + this.mIsImWindow + " mIsWallpaper=" + this.mIsWallpaper + " mIsFloatingLayer=" + this.mIsFloatingLayer);
        }
        if (z) {
            printWriter.print(str);
            printWriter.print("mBaseLayer=");
            printWriter.print(this.mBaseLayer);
            printWriter.print(" mSubLayer=");
            printWriter.print(this.mSubLayer);
        }
        if (z) {
            printWriter.println(str + "mToken=" + this.mToken);
            if (this.mActivityRecord != null) {
                printWriter.println(str + "mActivityRecord=" + this.mActivityRecord);
                printWriter.print(str + "drawnStateEvaluated=" + getDrawnStateEvaluated());
                printWriter.println(str + "mightAffectAllDrawn=" + mightAffectAllDrawn());
            }
            printWriter.println(str + "mViewVisibility=0x" + java.lang.Integer.toHexString(this.mViewVisibility) + " mHaveFrame=" + this.mHaveFrame + " mObscured=" + this.mObscured);
            if (this.mDisableFlags != 0) {
                printWriter.println(str + "mDisableFlags=" + android.view.ViewDebug.flagsToString(android.view.View.class, "mSystemUiVisibility", this.mDisableFlags));
            }
        }
        if (!isVisibleByPolicy() || !this.mLegacyPolicyVisibilityAfterAnim || !this.mAppOpVisibility || isParentWindowHidden() || this.mPermanentlyHidden || this.mForceHideNonSystemOverlayWindow || this.mHiddenWhileSuspended) {
            printWriter.println(str + "mPolicyVisibility=" + isVisibleByPolicy() + " mLegacyPolicyVisibilityAfterAnim=" + this.mLegacyPolicyVisibilityAfterAnim + " mAppOpVisibility=" + this.mAppOpVisibility + " parentHidden=" + isParentWindowHidden() + " mPermanentlyHidden=" + this.mPermanentlyHidden + " mHiddenWhileSuspended=" + this.mHiddenWhileSuspended + " mForceHideNonSystemOverlayWindow=" + this.mForceHideNonSystemOverlayWindow);
        }
        if (!this.mRelayoutCalled || this.mLayoutNeeded) {
            printWriter.println(str + "mRelayoutCalled=" + this.mRelayoutCalled + " mLayoutNeeded=" + this.mLayoutNeeded);
        }
        if (z) {
            printWriter.println(str + "mGivenContentInsets=" + this.mGivenContentInsets.toShortString(sTmpSB) + " mGivenVisibleInsets=" + this.mGivenVisibleInsets.toShortString(sTmpSB));
            if (this.mTouchableInsets != 0 || this.mGivenInsetsPending) {
                printWriter.println(str + "mTouchableInsets=" + this.mTouchableInsets + " mGivenInsetsPending=" + this.mGivenInsetsPending);
                android.graphics.Region region = new android.graphics.Region();
                getTouchableRegion(region);
                printWriter.println(str + "touchable region=" + region);
            }
            printWriter.println(str + "mFullConfiguration=" + getConfiguration());
            printWriter.println(str + "mLastReportedConfiguration=" + getLastReportedConfiguration());
        }
        printWriter.println(str + "mHasSurface=" + this.mHasSurface + " isReadyForDisplay()=" + isReadyForDisplay() + " mWindowRemovalAllowed=" + this.mWindowRemovalAllowed);
        if (this.mInvGlobalScale != 1.0f) {
            printWriter.println(str + "mCompatFrame=" + this.mWindowFrames.mCompatFrame.toShortString(sTmpSB));
        }
        if (z) {
            this.mWindowFrames.dump(printWriter, str);
            printWriter.println(str + " surface=" + this.mAttrs.surfaceInsets.toShortString(sTmpSB));
        }
        super.dump(printWriter, str, z);
        printWriter.println(str + this.mWinAnimator + ":");
        this.mWinAnimator.dump(printWriter, str + "  ", z);
        if (this.mAnimatingExit || this.mRemoveOnExit || this.mDestroying || this.mRemoved) {
            printWriter.println(str + "mAnimatingExit=" + this.mAnimatingExit + " mRemoveOnExit=" + this.mRemoveOnExit + " mDestroying=" + this.mDestroying + " mRemoved=" + this.mRemoved);
        }
        if (getOrientationChanging() || this.mAppFreezing) {
            java.lang.StringBuilder sb2 = new java.lang.StringBuilder();
            sb2.append(str);
            sb2.append("mOrientationChanging=");
            sb2.append(this.mOrientationChanging);
            sb2.append(" configOrientationChanging=");
            sb2.append(getLastReportedConfiguration().orientation != getConfiguration().orientation);
            sb2.append(" mAppFreezing=");
            sb2.append(this.mAppFreezing);
            printWriter.println(sb2.toString());
        }
        if (this.mLastFreezeDuration != 0) {
            printWriter.print(str + "mLastFreezeDuration=");
            android.util.TimeUtils.formatDuration((long) this.mLastFreezeDuration, printWriter);
            printWriter.println();
        }
        printWriter.print(str + "mForceSeamlesslyRotate=" + this.mForceSeamlesslyRotate + " seamlesslyRotate: pending=");
        if (this.mPendingSeamlessRotate != null) {
            this.mPendingSeamlessRotate.dump(printWriter);
        } else {
            printWriter.print("null");
        }
        printWriter.println();
        if (this.mXOffset != 0 || this.mYOffset != 0) {
            printWriter.println(str + "mXOffset=" + this.mXOffset + " mYOffset=" + this.mYOffset);
        }
        if (this.mHScale != 1.0f || this.mVScale != 1.0f) {
            printWriter.println(str + "mHScale=" + this.mHScale + " mVScale=" + this.mVScale);
        }
        if (this.mWallpaperX != -1.0f || this.mWallpaperY != -1.0f) {
            printWriter.println(str + "mWallpaperX=" + this.mWallpaperX + " mWallpaperY=" + this.mWallpaperY);
        }
        if (this.mWallpaperXStep != -1.0f || this.mWallpaperYStep != -1.0f) {
            printWriter.println(str + "mWallpaperXStep=" + this.mWallpaperXStep + " mWallpaperYStep=" + this.mWallpaperYStep);
        }
        if (this.mWallpaperZoomOut != -1.0f) {
            printWriter.println(str + "mWallpaperZoomOut=" + this.mWallpaperZoomOut);
        }
        if (this.mWallpaperDisplayOffsetX != Integer.MIN_VALUE || this.mWallpaperDisplayOffsetY != Integer.MIN_VALUE) {
            printWriter.println(str + "mWallpaperDisplayOffsetX=" + this.mWallpaperDisplayOffsetX + " mWallpaperDisplayOffsetY=" + this.mWallpaperDisplayOffsetY);
        }
        if (this.mDrawLock != null) {
            printWriter.println(str + "mDrawLock=" + this.mDrawLock);
        }
        if (isDragResizing()) {
            printWriter.println(str + "isDragResizing=" + isDragResizing());
        }
        if (computeDragResizing()) {
            printWriter.println(str + "computeDragResizing=" + computeDragResizing());
        }
        if (this.mImeInsetsConsumed) {
            printWriter.println(str + "mImeInsetsConsumed=true");
        }
        printWriter.println(str + "isOnScreen=" + isOnScreen());
        printWriter.println(str + "isVisible=" + isVisible());
        printWriter.println(str + "keepClearAreas: restricted=" + this.mKeepClearAreas + ", unrestricted=" + this.mUnrestrictedKeepClearAreas);
        if (z && this.mRequestedVisibleTypes != android.view.WindowInsets.Type.defaultVisible()) {
            printWriter.println(str + "Requested non-default-visibility types: " + android.view.WindowInsets.Type.toString(this.mRequestedVisibleTypes ^ android.view.WindowInsets.Type.defaultVisible()));
        }
        printWriter.println(str + "mPrepareSyncSeqId=" + this.mPrepareSyncSeqId);
    }

    @Override // com.android.server.wm.ConfigurationContainer
    java.lang.String getName() {
        return java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + ((java.lang.Object) getWindowTag());
    }

    java.lang.CharSequence getWindowTag() {
        java.lang.CharSequence title = this.mAttrs.getTitle();
        if (title == null || title.length() <= 0) {
            return this.mAttrs.packageName;
        }
        return title;
    }

    public java.lang.String toString() {
        java.lang.CharSequence windowTag = getWindowTag();
        if (this.mStringNameCache == null || this.mLastTitle != windowTag || this.mWasExiting != this.mAnimatingExit) {
            this.mLastTitle = windowTag;
            this.mWasExiting = this.mAnimatingExit;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Window{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(" u");
            sb.append(this.mShowUserId);
            sb.append(" ");
            sb.append((java.lang.Object) this.mLastTitle);
            sb.append(this.mAnimatingExit ? " EXITING}" : "}");
            this.mStringNameCache = sb.toString();
        }
        return this.mStringNameCache;
    }

    boolean isChildWindow() {
        return this.mIsChildWindow;
    }

    boolean hideNonSystemOverlayWindowsWhenVisible() {
        return (this.mAttrs.privateFlags & 524288) != 0 && this.mSession.mCanHideNonSystemOverlayWindows;
    }

    com.android.server.wm.WindowState getParentWindow() {
        if (this.mIsChildWindow) {
            return (com.android.server.wm.WindowState) super.getParent();
        }
        return null;
    }

    com.android.server.wm.WindowState getTopParentWindow() {
        com.android.server.wm.WindowState windowState = this;
        com.android.server.wm.WindowState windowState2 = windowState;
        while (windowState != null && windowState.mIsChildWindow) {
            windowState = windowState.getParentWindow();
            if (windowState != null) {
                windowState2 = windowState;
            }
        }
        return windowState2;
    }

    boolean isParentWindowHidden() {
        com.android.server.wm.WindowState parentWindow = getParentWindow();
        return parentWindow != null && parentWindow.mHidden;
    }

    private boolean isParentWindowGoneForLayout() {
        com.android.server.wm.WindowState parentWindow = getParentWindow();
        return parentWindow != null && parentWindow.isGoneForLayout();
    }

    void requestUpdateWallpaperIfNeeded() {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        if (displayContent != null && ((this.mIsWallpaper && !this.mLastConfigReportedToClient) || hasWallpaper())) {
            displayContent.pendingLayoutChanges |= 4;
            displayContent.setLayoutNeeded();
            this.mWmService.mWindowPlacerLocked.requestTraversal();
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((com.android.server.wm.WindowState) this.mChildren.get(size)).requestUpdateWallpaperIfNeeded();
        }
    }

    float translateToWindowX(float f) {
        float f2 = f - this.mWindowFrames.mFrame.left;
        if (this.mGlobalScale != 1.0f) {
            return f2 * this.mInvGlobalScale;
        }
        return f2;
    }

    float translateToWindowY(float f) {
        float f2 = f - this.mWindowFrames.mFrame.top;
        if (this.mGlobalScale != 1.0f) {
            return f2 * this.mInvGlobalScale;
        }
        return f2;
    }

    int getRotationAnimationHint() {
        if (this.mActivityRecord != null) {
            return this.mActivityRecord.mRotationAnimationHint;
        }
        return -1;
    }

    boolean commitFinishDrawing(android.view.SurfaceControl.Transaction transaction) {
        boolean commitFinishDrawingLocked = this.mWinAnimator.commitFinishDrawingLocked();
        if (commitFinishDrawingLocked) {
            this.mWinAnimator.prepareSurfaceLocked(transaction);
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            commitFinishDrawingLocked |= ((com.android.server.wm.WindowState) this.mChildren.get(size)).commitFinishDrawing(transaction);
        }
        return commitFinishDrawingLocked;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean performShowLocked() {
        if (!showToCurrentUser()) {
            clearPolicyVisibilityFlag(2);
            return false;
        }
        logPerformShow("performShow on ");
        int i = this.mWinAnimator.mDrawState;
        if ((i == 4 || i == 3) && this.mActivityRecord != null) {
            if (this.mAttrs.type != 3) {
                this.mActivityRecord.onFirstWindowDrawn(this);
            } else {
                this.mActivityRecord.onStartingWindowDrawn();
            }
        }
        if (this.mWinAnimator.mDrawState != 3 || !isReadyForDisplay()) {
            return false;
        }
        logPerformShow("Showing ");
        this.mWmService.enableScreenIfNeededLocked();
        this.mWinAnimator.applyEnterAnimationLocked();
        this.mWinAnimator.mLastAlpha = -1.0f;
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -7413136364930452718L, 0, null, java.lang.String.valueOf(this));
        this.mWinAnimator.mDrawState = 4;
        this.mWmService.scheduleAnimationLocked();
        if (this.mHidden) {
            this.mHidden = false;
            com.android.server.wm.DisplayContent displayContent = getDisplayContent();
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                com.android.server.wm.WindowState windowState = (com.android.server.wm.WindowState) this.mChildren.get(size);
                if (windowState.mWinAnimator.mSurfaceController != null) {
                    windowState.performShowLocked();
                    if (displayContent != null) {
                        displayContent.setLayoutNeeded();
                    }
                }
            }
        }
        return true;
    }

    private void logPerformShow(java.lang.String str) {
    }

    android.view.WindowInfo getWindowInfo() {
        android.view.WindowInfo obtain = android.view.WindowInfo.obtain();
        obtain.displayId = getDisplayId();
        obtain.type = this.mAttrs.type;
        obtain.layer = this.mLayer;
        obtain.token = this.mClient.asBinder();
        if (this.mActivityRecord != null) {
            obtain.activityToken = this.mActivityRecord.token;
        }
        obtain.accessibilityIdOfAnchor = this.mAttrs.accessibilityIdOfAnchor;
        obtain.focused = isFocused();
        com.android.server.wm.Task task = getTask();
        obtain.inPictureInPicture = task != null && task.inPinnedWindowingMode();
        obtain.taskId = task == null ? -1 : task.mTaskId;
        obtain.hasFlagWatchOutsideTouch = (this.mAttrs.flags & 262144) != 0;
        if (this.mIsChildWindow) {
            obtain.parentToken = getParentWindow().mClient.asBinder();
        }
        int size = this.mChildren.size();
        if (size > 0) {
            if (obtain.childTokens == null) {
                obtain.childTokens = new java.util.ArrayList(size);
            }
            for (int i = 0; i < size; i++) {
                obtain.childTokens.add(((com.android.server.wm.WindowState) this.mChildren.get(i)).mClient.asBinder());
            }
        }
        return obtain;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean forAllWindows(com.android.internal.util.ToBooleanFunction<com.android.server.wm.WindowState> toBooleanFunction, boolean z) {
        if (this.mChildren.isEmpty()) {
            return applyInOrderWithImeWindows(toBooleanFunction, z);
        }
        if (z) {
            return forAllWindowTopToBottom(toBooleanFunction);
        }
        return forAllWindowBottomToTop(toBooleanFunction);
    }

    private boolean forAllWindowBottomToTop(com.android.internal.util.ToBooleanFunction<com.android.server.wm.WindowState> toBooleanFunction) {
        int size = this.mChildren.size();
        com.android.server.wm.WindowState windowState = (com.android.server.wm.WindowState) this.mChildren.get(0);
        int i = 0;
        while (i < size && windowState.mSubLayer < 0) {
            if (windowState.applyInOrderWithImeWindows(toBooleanFunction, false)) {
                return true;
            }
            i++;
            if (i >= size) {
                break;
            }
            windowState = (com.android.server.wm.WindowState) this.mChildren.get(i);
        }
        if (applyInOrderWithImeWindows(toBooleanFunction, false)) {
            return true;
        }
        while (i < size) {
            if (windowState.applyInOrderWithImeWindows(toBooleanFunction, false)) {
                return true;
            }
            i++;
            if (i >= size) {
                break;
            }
            windowState = (com.android.server.wm.WindowState) this.mChildren.get(i);
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    void updateAboveInsetsState(final android.view.InsetsState insetsState, android.util.SparseArray<android.view.InsetsSource> sparseArray, final android.util.ArraySet<com.android.server.wm.WindowState> arraySet) {
        final android.util.SparseArray createMergedSparseArray = com.android.server.wm.WindowContainer.createMergedSparseArray(sparseArray, this.mLocalInsetsSources);
        forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.WindowState$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.WindowState.lambda$updateAboveInsetsState$3(insetsState, arraySet, createMergedSparseArray, (com.android.server.wm.WindowState) obj);
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateAboveInsetsState$3(android.view.InsetsState insetsState, android.util.ArraySet arraySet, android.util.SparseArray sparseArray, com.android.server.wm.WindowState windowState) {
        if (!windowState.mAboveInsetsState.equals(insetsState)) {
            windowState.mAboveInsetsState.set(insetsState);
            arraySet.add(windowState);
        }
        if (!sparseArray.contentEquals(windowState.mMergedLocalInsetsSources)) {
            windowState.mMergedLocalInsetsSources = sparseArray;
            arraySet.add(windowState);
        }
        android.util.SparseArray<com.android.server.wm.InsetsSourceProvider> sparseArray2 = windowState.mInsetsSourceProviders;
        if (sparseArray2 != null) {
            for (int size = sparseArray2.size() - 1; size >= 0; size--) {
                insetsState.addSource(sparseArray2.valueAt(size).getSource());
            }
        }
    }

    private boolean forAllWindowTopToBottom(com.android.internal.util.ToBooleanFunction<com.android.server.wm.WindowState> toBooleanFunction) {
        int size = this.mChildren.size() - 1;
        com.android.server.wm.WindowState windowState = (com.android.server.wm.WindowState) this.mChildren.get(size);
        while (size >= 0 && windowState.mSubLayer >= 0) {
            if (windowState.applyInOrderWithImeWindows(toBooleanFunction, true)) {
                return true;
            }
            size--;
            if (size < 0) {
                break;
            }
            windowState = (com.android.server.wm.WindowState) this.mChildren.get(size);
        }
        if (applyInOrderWithImeWindows(toBooleanFunction, true)) {
            return true;
        }
        while (size >= 0) {
            if (windowState.applyInOrderWithImeWindows(toBooleanFunction, true)) {
                return true;
            }
            size--;
            if (size >= 0) {
                windowState = (com.android.server.wm.WindowState) this.mChildren.get(size);
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean applyImeWindowsIfNeeded(com.android.internal.util.ToBooleanFunction<com.android.server.wm.WindowState> toBooleanFunction, boolean z) {
        if (!isImeLayeringTarget()) {
            return false;
        }
        com.android.server.wm.WindowState imeInputTarget = getImeInputTarget();
        if (imeInputTarget == null || imeInputTarget.isDrawn() || imeInputTarget.isVisibleRequested()) {
            return this.mDisplayContent.forAllImeWindows(toBooleanFunction, z);
        }
        return false;
    }

    private boolean applyInOrderWithImeWindows(com.android.internal.util.ToBooleanFunction<com.android.server.wm.WindowState> toBooleanFunction, boolean z) {
        if (z) {
            if (applyImeWindowsIfNeeded(toBooleanFunction, z) || toBooleanFunction.apply(this)) {
                return true;
            }
            return false;
        }
        if (toBooleanFunction.apply(this) || applyImeWindowsIfNeeded(toBooleanFunction, z)) {
            return true;
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.WindowState getWindow(java.util.function.Predicate<com.android.server.wm.WindowState> predicate) {
        if (this.mChildren.isEmpty()) {
            if (predicate.test(this)) {
                return this;
            }
            return null;
        }
        int size = this.mChildren.size() - 1;
        com.android.server.wm.WindowState windowState = (com.android.server.wm.WindowState) this.mChildren.get(size);
        while (size >= 0 && windowState.mSubLayer >= 0) {
            if (predicate.test(windowState)) {
                return windowState;
            }
            size--;
            if (size < 0) {
                break;
            }
            windowState = (com.android.server.wm.WindowState) this.mChildren.get(size);
        }
        if (predicate.test(this)) {
            return this;
        }
        while (size >= 0) {
            if (predicate.test(windowState)) {
                return windowState;
            }
            size--;
            if (size < 0) {
                break;
            }
            windowState = (com.android.server.wm.WindowState) this.mChildren.get(size);
        }
        return null;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean isSelfOrAncestorWindowAnimatingExit() {
        com.android.server.wm.WindowState windowState = this;
        while (!windowState.mAnimatingExit) {
            windowState = windowState.getParentWindow();
            if (windowState == null) {
                return false;
            }
        }
        return true;
    }

    boolean isAnimationRunningSelfOrParent() {
        return inTransitionSelfOrParent() || isAnimating(0, 16);
    }

    private boolean shouldFinishAnimatingExit() {
        if (inTransition()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 7624470121297688739L, 0, null, java.lang.String.valueOf(this));
            return false;
        }
        if (!this.mDisplayContent.okToAnimate()) {
            return true;
        }
        if (isAnimationRunningSelfOrParent()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 810267895099109466L, 0, null, java.lang.String.valueOf(this));
            return false;
        }
        if (!this.mDisplayContent.mWallpaperController.isWallpaperTarget(this)) {
            return true;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, -1760879391350377377L, 0, null, java.lang.String.valueOf(this));
        return false;
    }

    void cleanupAnimatingExitWindow() {
        if (this.mAnimatingExit && shouldFinishAnimatingExit()) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.w(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 272960397873328729L, 0, null, java.lang.String.valueOf(this));
            onExitAnimationDone();
        }
    }

    void onExitAnimationDone() {
        if (com.android.internal.protolog.ProtoLogImpl_1545807451.isEnabled(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM)) {
            com.android.server.wm.AnimationAdapter animation = this.mSurfaceAnimator.getAnimation();
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            if (animation != null) {
                animation.dump(new java.io.PrintWriter(stringWriter), "");
            }
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -1007526574020149845L, android.hardware.audio.common.V2_0.AudioChannelMask.IN_6, null, java.lang.String.valueOf(this), java.lang.Boolean.valueOf(this.mAnimatingExit), java.lang.Boolean.valueOf(this.mRemoveOnExit), java.lang.Boolean.valueOf(isAnimating()), java.lang.String.valueOf(stringWriter));
        }
        if (!this.mChildren.isEmpty()) {
            java.util.ArrayList arrayList = new java.util.ArrayList(this.mChildren);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ((com.android.server.wm.WindowState) arrayList.get(size)).onExitAnimationDone();
            }
        }
        if (this.mWinAnimator.mEnteringAnimation) {
            this.mWinAnimator.mEnteringAnimation = false;
            this.mWmService.requestTraversal();
            if (this.mActivityRecord == null) {
                try {
                    this.mClient.dispatchWindowShown();
                } catch (android.os.RemoteException e) {
                }
            }
        }
        if (isAnimating() || !isSelfOrAncestorWindowAnimatingExit()) {
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ADD_REMOVE, 1738645946553610841L, 12, null, java.lang.String.valueOf(this), java.lang.Boolean.valueOf(this.mRemoveOnExit));
        this.mDestroying = true;
        boolean hasSurface = this.mWinAnimator.hasSurface();
        this.mWinAnimator.hide(getPendingTransaction(), "onExitAnimationDone");
        if (this.mActivityRecord != null) {
            if (this.mAttrs.type == 1) {
                this.mActivityRecord.destroySurfaces();
            } else {
                destroySurface(false, this.mActivityRecord.mAppStopped);
            }
        } else if (hasSurface) {
            this.mWmService.mDestroySurface.add(this);
        }
        this.mAnimatingExit = false;
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -7737516306844862315L, 0, null, java.lang.String.valueOf(this));
        getDisplayContent().mWallpaperController.hideWallpapers(this);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean handleCompleteDeferredRemoval() {
        if (this.mRemoveOnExit && !isSelfAnimating(0, 16)) {
            this.mRemoveOnExit = false;
            removeImmediately();
        }
        return super.handleCompleteDeferredRemoval();
    }

    boolean clearAnimatingFlags() {
        boolean z;
        boolean z2 = false;
        if (!this.mRemoveOnExit) {
            if (!this.mAnimatingExit) {
                z = false;
            } else {
                this.mAnimatingExit = false;
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -3153130647145726082L, 0, null, java.lang.String.valueOf(this));
                z = true;
            }
            if (!this.mDestroying) {
                z2 = z;
            } else {
                this.mDestroying = false;
                this.mWmService.mDestroySurface.remove(this);
                z2 = true;
            }
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            z2 |= ((com.android.server.wm.WindowState) this.mChildren.get(size)).clearAnimatingFlags();
        }
        return z2;
    }

    public boolean isRtl() {
        return getConfiguration().getLayoutDirection() == 1;
    }

    void updateReportedVisibility(com.android.server.wm.WindowState.UpdateReportedVisibilityResults updateReportedVisibilityResults) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            ((com.android.server.wm.WindowState) this.mChildren.get(size)).updateReportedVisibility(updateReportedVisibilityResults);
        }
        if (this.mAppFreezing || this.mViewVisibility != 0 || this.mAttrs.type == 3 || this.mDestroying) {
            return;
        }
        updateReportedVisibilityResults.numInteresting++;
        if (isDrawn()) {
            updateReportedVisibilityResults.numDrawn++;
            if (!isAnimating(3)) {
                updateReportedVisibilityResults.numVisible++;
            }
            updateReportedVisibilityResults.nowGone = false;
            return;
        }
        if (isAnimating(3)) {
            updateReportedVisibilityResults.nowGone = false;
        }
    }

    boolean surfaceInsetsChanging() {
        return !this.mLastSurfaceInsets.equals(this.mAttrs.surfaceInsets);
    }

    int relayoutVisibleWindow(int i) {
        boolean isVisible = isVisible();
        int i2 = i | ((isVisible && isDrawn()) ? 0 : 1);
        if (this.mAnimatingExit) {
            android.util.Slog.d(TAG, "relayoutVisibleWindow: " + this + " mAnimatingExit=true, mRemoveOnExit=" + this.mRemoveOnExit + ", mDestroying=" + this.mDestroying);
            if (isAnimating()) {
                cancelAnimation();
            }
            this.mAnimatingExit = false;
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -5202247309108694583L, 0, null, java.lang.String.valueOf(this));
        }
        if (this.mDestroying) {
            this.mDestroying = false;
            this.mWmService.mDestroySurface.remove(this);
        }
        if (!isVisible) {
            this.mWinAnimator.mEnterAnimationPending = true;
        }
        this.mLastVisibleLayoutRotation = getDisplayContent().getRotation();
        this.mWinAnimator.mEnteringAnimation = true;
        android.os.Trace.traceBegin(32L, "prepareToDisplay");
        try {
            prepareWindowToDisplayDuringRelayout(isVisible);
            return i2;
        } finally {
            android.os.Trace.traceEnd(32L);
        }
    }

    boolean isLaidOut() {
        return this.mLayoutSeq != -1;
    }

    void updateLastFrames() {
        this.mWindowFrames.mLastFrame.set(this.mWindowFrames.mFrame);
        this.mWindowFrames.mLastRelFrame.set(this.mWindowFrames.mRelFrame);
    }

    void onResizeHandled() {
        this.mWindowFrames.onResizeHandled();
    }

    @Override // com.android.server.wm.WindowContainer
    protected boolean isSelfAnimating(int i, int i2) {
        if (this.mControllableInsetProvider != null) {
            return false;
        }
        return super.isSelfAnimating(i, i2);
    }

    void startAnimation(android.view.animation.Animation animation) {
        if (this.mControllableInsetProvider != null) {
            return;
        }
        android.view.DisplayInfo displayInfo = getDisplayInfo();
        animation.initialize(this.mWindowFrames.mFrame.width(), this.mWindowFrames.mFrame.height(), displayInfo.appWidth, displayInfo.appHeight);
        animation.restrictDuration(com.android.server.job.controllers.JobStatus.DEFAULT_TRIGGER_UPDATE_DELAY);
        animation.scaleCurrentDuration(this.mWmService.getWindowAnimationScaleLocked());
        startAnimation(getPendingTransaction(), new com.android.server.wm.LocalAnimationAdapter(new com.android.server.wm.WindowAnimationSpec(animation, this.mSurfacePosition, false, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE), this.mWmService.mSurfaceAnimationRunner));
        commitPendingTransaction();
    }

    private void startMoveAnimation(int i, int i2) {
        if (this.mControllableInsetProvider != null) {
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, 6291563604478341956L, 0, null, java.lang.String.valueOf(this));
        android.graphics.Point point = new android.graphics.Point();
        android.graphics.Point point2 = new android.graphics.Point();
        transformFrameToSurfacePosition(this.mWindowFrames.mLastFrame.left, this.mWindowFrames.mLastFrame.top, point);
        transformFrameToSurfacePosition(i, i2, point2);
        startAnimation(getPendingTransaction(), new com.android.server.wm.LocalAnimationAdapter(new com.android.server.wm.WindowState.MoveAnimationSpec(point.x, point.y, point2.x, point2.y), this.mWmService.mSurfaceAnimationRunner));
    }

    private void startAnimation(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.AnimationAdapter animationAdapter) {
        startAnimation(transaction, animationAdapter, this.mWinAnimator.mLastHidden, 16);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.server.wm.WindowContainer
    public void onAnimationFinished(int i, com.android.server.wm.AnimationAdapter animationAdapter) {
        super.onAnimationFinished(i, animationAdapter);
        this.mWinAnimator.onAnimationFinished();
    }

    void getTransformationMatrix(float[] fArr, android.graphics.Matrix matrix) {
        fArr[0] = this.mGlobalScale;
        fArr[3] = 0.0f;
        fArr[1] = 0.0f;
        fArr[4] = this.mGlobalScale;
        transformSurfaceInsetsPosition(this.mTmpPoint, this.mAttrs.surfaceInsets);
        int i = this.mSurfacePosition.x + this.mTmpPoint.x;
        int i2 = this.mSurfacePosition.y + this.mTmpPoint.y;
        com.android.server.wm.WindowContainer parent = getParent();
        if (isChildWindow()) {
            com.android.server.wm.WindowState parentWindow = getParentWindow();
            i += parentWindow.mWindowFrames.mFrame.left - parentWindow.mAttrs.surfaceInsets.left;
            i2 += parentWindow.mWindowFrames.mFrame.top - parentWindow.mAttrs.surfaceInsets.top;
        } else if (parent != null) {
            android.graphics.Rect bounds = parent.getBounds();
            i += bounds.left;
            i2 += bounds.top;
        }
        fArr[2] = i;
        fArr[5] = i2;
        fArr[6] = 0.0f;
        fArr[7] = 0.0f;
        fArr[8] = 1.0f;
        matrix.setValues(fArr);
    }

    static final class UpdateReportedVisibilityResults {
        boolean nowGone = true;
        int numDrawn;
        int numInteresting;
        int numVisible;

        UpdateReportedVisibilityResults() {
        }

        void reset() {
            this.numInteresting = 0;
            this.numVisible = 0;
            this.numDrawn = 0;
            this.nowGone = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class WindowId extends android.view.IWindowId.Stub {
        private final java.lang.ref.WeakReference<com.android.server.wm.WindowState> mOuter;

        private WindowId(com.android.server.wm.WindowState windowState) {
            this.mOuter = new java.lang.ref.WeakReference<>(windowState);
        }

        public void registerFocusObserver(android.view.IWindowFocusObserver iWindowFocusObserver) {
            com.android.server.wm.WindowState windowState = this.mOuter.get();
            if (windowState != null) {
                windowState.registerFocusObserver(iWindowFocusObserver);
            }
        }

        public void unregisterFocusObserver(android.view.IWindowFocusObserver iWindowFocusObserver) {
            com.android.server.wm.WindowState windowState = this.mOuter.get();
            if (windowState != null) {
                windowState.unregisterFocusObserver(iWindowFocusObserver);
            }
        }

        public boolean isFocused() {
            boolean isFocused;
            com.android.server.wm.WindowState windowState = this.mOuter.get();
            if (windowState != null) {
                com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = windowState.mWmService.mGlobalLock;
                com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        isFocused = windowState.isFocused();
                    } catch (java.lang.Throwable th) {
                        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                return isFocused;
            }
            return false;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    boolean shouldMagnify() {
        return (this.mAttrs.type == 2039 || this.mAttrs.type == 2011 || this.mAttrs.type == 2012 || this.mAttrs.type == 2027 || this.mAttrs.type == 2019 || this.mAttrs.type == 2024 || (this.mAttrs.privateFlags & 4194304) != 0) ? false : true;
    }

    @Override // com.android.server.wm.WindowContainer
    android.view.SurfaceSession getSession() {
        if (this.mSession.mSurfaceSession != null) {
            return this.mSession.mSurfaceSession;
        }
        return getParent().getSession();
    }

    @Override // com.android.server.wm.WindowContainer
    boolean needsZBoost() {
        com.android.server.wm.ActivityRecord activityRecord;
        com.android.server.wm.InsetsControlTarget imeTarget = getDisplayContent().getImeTarget(0);
        if (!this.mIsImWindow || imeTarget == null || (activityRecord = imeTarget.getWindow().mActivityRecord) == null) {
            return false;
        }
        return activityRecord.needsZBoost();
    }

    private boolean isStartingWindowAssociatedToTask() {
        return (this.mStartingData == null || this.mStartingData.mAssociatedTask == null) ? false : true;
    }

    private void applyDims() {
        if ((this.mAttrs.flags & 2) != 0 || shouldDrawBlurBehind()) {
            if (com.android.server.wm.Dimmer.DIMMER_REFACTOR) {
                if (!this.mWinAnimator.getShown()) {
                    return;
                }
            } else if (!isVisibleNow()) {
                return;
            }
            if (!this.mHidden && this.mTransitionController.canApplyDim(getTask())) {
                this.mIsDimming = true;
                float f = (this.mAttrs.flags & 2) != 0 ? this.mAttrs.dimAmount : com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
                int blurBehindRadius = shouldDrawBlurBehind() ? this.mAttrs.getBlurBehindRadius() : 0;
                if (isVisibleNow()) {
                    getDimmer().adjustAppearance(this, f, blurBehindRadius);
                }
                getDimmer().adjustRelativeLayer(this, -1);
            }
        }
    }

    private boolean shouldDrawBlurBehind() {
        return (this.mAttrs.flags & 4) != 0 && this.mWmService.mBlurController.getBlurEnabled();
    }

    @com.android.internal.annotations.VisibleForTesting
    void updateFrameRateSelectionPriorityIfNeeded() {
        com.android.server.wm.RefreshRatePolicy refreshRatePolicy = getDisplayContent().getDisplayPolicy().getRefreshRatePolicy();
        int calculatePriority = refreshRatePolicy.calculatePriority(this);
        if (this.mFrameRateSelectionPriority != calculatePriority) {
            this.mFrameRateSelectionPriority = calculatePriority;
            getPendingTransaction().setFrameRateSelectionPriority(this.mSurfaceControl, this.mFrameRateSelectionPriority);
        }
        if (refreshRatePolicy.updateFrameRateVote(this)) {
            getPendingTransaction().setFrameRate(this.mSurfaceControl, this.mFrameRateVote.mRefreshRate, this.mFrameRateVote.mCompatibility, 1);
            if (com.android.window.flags.Flags.explicitRefreshRateHints()) {
                getPendingTransaction().setFrameRateSelectionStrategy(this.mSurfaceControl, this.mFrameRateVote.mSelectionStrategy);
            }
        }
    }

    private void updateScaleIfNeeded() {
        if (!isVisibleRequested() && (!this.mIsWallpaper || !this.mToken.isVisible())) {
            return;
        }
        float f = this.mGlobalScale;
        com.android.server.wm.WindowState parentWindow = getParentWindow();
        if (parentWindow != null) {
            f *= parentWindow.mInvGlobalScale;
        }
        float f2 = this.mHScale * f * this.mWallpaperScale;
        float f3 = this.mVScale * f * this.mWallpaperScale;
        if (this.mLastHScale != f2 || this.mLastVScale != f3) {
            getSyncTransaction().setMatrix(this.mSurfaceControl, f2, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, f3);
            this.mLastHScale = f2;
            this.mLastVScale = f3;
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void prepareSurfaces() {
        this.mIsDimming = false;
        if (this.mHasSurface) {
            if (!com.android.server.wm.Dimmer.DIMMER_REFACTOR) {
                applyDims();
            }
            updateSurfacePositionNonOrganized();
            updateFrameRateSelectionPriorityIfNeeded();
            updateScaleIfNeeded();
            this.mWinAnimator.prepareSurfaceLocked(getSyncTransaction());
            if (com.android.server.wm.Dimmer.DIMMER_REFACTOR) {
                applyDims();
            }
        }
        super.prepareSurfaces();
    }

    @Override // com.android.server.wm.WindowContainer
    @com.android.internal.annotations.VisibleForTesting
    void updateSurfacePosition(android.view.SurfaceControl.Transaction transaction) {
        boolean z;
        boolean z2;
        if (this.mSurfaceControl == null) {
            return;
        }
        if (this.mActivityRecord != null && this.mActivityRecord.isConfigurationDispatchPaused()) {
            return;
        }
        if ((this.mWmService.mWindowPlacerLocked.isLayoutDeferred() || isGoneForLayout()) && !this.mSurfacePlacementNeeded) {
            return;
        }
        boolean z3 = false;
        this.mSurfacePlacementNeeded = false;
        transformFrameToSurfacePosition(this.mWindowFrames.mFrame.left, this.mWindowFrames.mFrame.top, this.mSurfacePosition);
        if (this.mWallpaperScale != 1.0f) {
            android.graphics.Rect parentFrame = getParentFrame();
            android.graphics.Matrix matrix = this.mTmpMatrix;
            matrix.setTranslate(this.mXOffset, this.mYOffset);
            matrix.postScale(this.mWallpaperScale, this.mWallpaperScale, parentFrame.exactCenterX(), parentFrame.exactCenterY());
            matrix.getValues(this.mTmpMatrixArray);
            this.mSurfacePosition.offset(java.lang.Math.round(this.mTmpMatrixArray[2]), java.lang.Math.round(this.mTmpMatrixArray[5]));
        } else {
            this.mSurfacePosition.offset(this.mXOffset, this.mYOffset);
        }
        com.android.server.wm.AsyncRotationController asyncRotationController = this.mDisplayContent.getAsyncRotationController();
        if ((asyncRotationController == null || !asyncRotationController.hasSeamlessOperation(this.mToken)) && this.mPendingSeamlessRotate == null && !this.mSurfaceAnimator.hasLeash() && !this.mLastSurfacePosition.equals(this.mSurfacePosition)) {
            boolean isFrameSizeChangeReported = this.mWindowFrames.isFrameSizeChangeReported();
            boolean surfaceInsetsChanging = surfaceInsetsChanging();
            if (!isFrameSizeChangeReported && !surfaceInsetsChanging) {
                z = false;
            } else {
                z = true;
            }
            this.mLastSurfacePosition.set(this.mSurfacePosition.x, this.mSurfacePosition.y);
            if (surfaceInsetsChanging) {
                this.mLastSurfaceInsets.set(this.mAttrs.surfaceInsets);
            }
            if (!z || !this.mWinAnimator.getShown() || canPlayMoveAnimation() || !okToDisplay() || this.mSyncState != 0) {
                z2 = false;
            } else {
                z2 = true;
            }
            com.android.server.wm.ActivityRecord activityRecord = getActivityRecord();
            if (activityRecord != null && activityRecord.areBoundsLetterboxed() && activityRecord.mLetterboxUiController.getIsRelaunchingAfterRequestedOrientationChanged()) {
                z3 = true;
            }
            if (z2 || z3) {
                applyWithNextDraw(this.mSetSurfacePositionConsumer);
            } else {
                this.mSetSurfacePositionConsumer.accept(transaction);
            }
        }
    }

    @Override // com.android.server.wm.WindowContainer
    void transformFrameToSurfacePosition(int i, int i2, android.graphics.Point point) {
        android.graphics.Rect bounds;
        point.set(i, i2);
        com.android.server.wm.WindowContainer parent = getParent();
        if (isChildWindow()) {
            com.android.server.wm.WindowState parentWindow = getParentWindow();
            point.offset(-parentWindow.mWindowFrames.mFrame.left, -parentWindow.mWindowFrames.mFrame.top);
            if (this.mInvGlobalScale != 1.0f) {
                point.x = (int) ((point.x * this.mInvGlobalScale) + 0.5f);
                point.y = (int) ((point.y * this.mInvGlobalScale) + 0.5f);
            }
            transformSurfaceInsetsPosition(this.mTmpPoint, parentWindow.mAttrs.surfaceInsets);
            point.offset(this.mTmpPoint.x, this.mTmpPoint.y);
        } else if (parent != null) {
            if (isStartingWindowAssociatedToTask()) {
                bounds = this.mStartingData.mAssociatedTask.getBounds();
            } else {
                bounds = parent.getBounds();
            }
            point.offset(-bounds.left, -bounds.top);
        }
        transformSurfaceInsetsPosition(this.mTmpPoint, this.mAttrs.surfaceInsets);
        point.offset(-this.mTmpPoint.x, -this.mTmpPoint.y);
        point.y += this.mSurfaceTranslationY;
    }

    private void transformSurfaceInsetsPosition(android.graphics.Point point, android.graphics.Rect rect) {
        if (this.mGlobalScale == 1.0f || this.mIsChildWindow) {
            point.x = rect.left;
            point.y = rect.top;
        } else {
            point.x = (int) ((rect.left * this.mGlobalScale) + 0.5f);
            point.y = (int) ((rect.top * this.mGlobalScale) + 0.5f);
        }
    }

    boolean needsRelativeLayeringToIme() {
        com.android.server.wm.WindowState imeLayeringTarget;
        if (this.mDisplayContent.shouldImeAttachedToApp() || !getDisplayContent().getImeContainer().isVisible()) {
            return false;
        }
        if (isChildWindow()) {
            if (getParentWindow().isImeLayeringTarget()) {
                return true;
            }
        } else if (this.mActivityRecord != null) {
            com.android.server.wm.WindowState imeLayeringTarget2 = getImeLayeringTarget();
            return (imeLayeringTarget2 == null || imeLayeringTarget2 == this || imeLayeringTarget2.mToken != this.mToken || this.mAttrs.type == 3 || getParent() == null || imeLayeringTarget2.compareTo((com.android.server.wm.WindowContainer) this) > 0) ? false : true;
        }
        return (this.mAttrs.flags & 131080) == 131072 && isTrustedOverlay() && canAddInternalSystemWindow() && (imeLayeringTarget = getImeLayeringTarget()) != null && imeLayeringTarget != this && imeLayeringTarget.compareTo((com.android.server.wm.WindowContainer) this) <= 0;
    }

    @Override // com.android.server.wm.InputTarget
    public com.android.server.wm.InsetsControlTarget getImeControlTarget() {
        return getDisplayContent().getImeHostOrFallback(this);
    }

    @Override // com.android.server.wm.WindowContainer
    void assignLayer(android.view.SurfaceControl.Transaction transaction, int i) {
        if (this.mStartingData != null) {
            transaction.setLayer(this.mSurfaceControl, Integer.MAX_VALUE);
        } else if (needsRelativeLayeringToIme()) {
            getDisplayContent().assignRelativeLayerForImeTargetChild(transaction, this);
        } else {
            super.assignLayer(transaction, i);
        }
    }

    boolean isDimming() {
        return this.mIsDimming;
    }

    @Override // com.android.server.wm.WindowContainer
    protected void reparentSurfaceControl(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        if (isStartingWindowAssociatedToTask()) {
            return;
        }
        super.reparentSurfaceControl(transaction, surfaceControl);
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public android.view.SurfaceControl getAnimationLeashParent() {
        if (isStartingWindowAssociatedToTask()) {
            return this.mStartingData.mAssociatedTask.mSurfaceControl;
        }
        return super.getAnimationLeashParent();
    }

    @Override // com.android.server.wm.WindowContainer, com.android.server.wm.SurfaceAnimator.Animatable
    public void onAnimationLeashCreated(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        super.onAnimationLeashCreated(transaction, surfaceControl);
        if (isStartingWindowAssociatedToTask()) {
            transaction.setLayer(surfaceControl, Integer.MAX_VALUE);
        }
    }

    @Override // com.android.server.wm.WindowContainer
    public void assignChildLayers(android.view.SurfaceControl.Transaction transaction) {
        int i = 2;
        for (int i2 = 0; i2 < this.mChildren.size(); i2++) {
            com.android.server.wm.WindowState windowState = (com.android.server.wm.WindowState) this.mChildren.get(i2);
            if (windowState.mAttrs.type == 1001) {
                if (this.mWinAnimator.hasSurface()) {
                    windowState.assignRelativeLayer(transaction, this.mWinAnimator.mSurfaceController.mSurfaceControl, -2);
                } else {
                    windowState.assignLayer(transaction, -2);
                }
            } else if (windowState.mAttrs.type == 1004) {
                if (this.mWinAnimator.hasSurface()) {
                    windowState.assignRelativeLayer(transaction, this.mWinAnimator.mSurfaceController.mSurfaceControl, -1);
                } else {
                    windowState.assignLayer(transaction, -1);
                }
            } else {
                windowState.assignLayer(transaction, i);
            }
            windowState.assignChildLayers(transaction);
            i++;
        }
    }

    void updateTapExcludeRegion(android.graphics.Region region) {
        com.android.server.wm.DisplayContent displayContent = getDisplayContent();
        if (displayContent == null) {
            throw new java.lang.IllegalStateException("Trying to update window not attached to any display.");
        }
        if (region == null || region.isEmpty()) {
            this.mTapExcludeRegion.setEmpty();
            displayContent.mTapExcludeProvidingWindows.remove(this);
        } else {
            this.mTapExcludeRegion.set(region);
            displayContent.mTapExcludeProvidingWindows.add(this);
        }
        displayContent.updateTouchExcludeRegion();
        displayContent.getInputMonitor().updateInputWindowsLw(true);
    }

    void getTapExcludeRegion(android.graphics.Region region) {
        this.mTmpRect.set(this.mWindowFrames.mFrame);
        this.mTmpRect.offsetTo(0, 0);
        region.set(this.mTapExcludeRegion);
        region.op(this.mTmpRect, android.graphics.Region.Op.INTERSECT);
        region.translate(this.mWindowFrames.mFrame.left, this.mWindowFrames.mFrame.top);
    }

    boolean isImeLayeringTarget() {
        return getDisplayContent().getImeTarget(0) == this;
    }

    boolean isImeOverlayLayeringTarget() {
        return isImeLayeringTarget() && (this.mAttrs.flags & 131080) != 0;
    }

    com.android.server.wm.WindowState getImeLayeringTarget() {
        com.android.server.wm.InsetsControlTarget imeTarget = getDisplayContent().getImeTarget(0);
        if (imeTarget != null) {
            return imeTarget.getWindow();
        }
        return null;
    }

    com.android.server.wm.WindowState getImeInputTarget() {
        com.android.server.wm.InputTarget imeInputTarget = this.mDisplayContent.getImeInputTarget();
        if (imeInputTarget != null) {
            return imeInputTarget.getWindowState();
        }
        return null;
    }

    void forceReportingResized() {
        this.mWindowFrames.forceReportingResized();
    }

    com.android.server.wm.WindowFrames getWindowFrames() {
        return this.mWindowFrames;
    }

    void resetContentChanged() {
        this.mWindowFrames.setContentChanged(false);
    }

    private final class MoveAnimationSpec implements com.android.server.wm.LocalAnimationAdapter.AnimationSpec {
        private final long mDuration;
        private android.graphics.Point mFrom;
        private android.view.animation.Interpolator mInterpolator;
        private android.graphics.Point mTo;

        private MoveAnimationSpec(int i, int i2, int i3, int i4) {
            this.mFrom = new android.graphics.Point();
            this.mTo = new android.graphics.Point();
            android.view.animation.Animation loadAnimation = android.view.animation.AnimationUtils.loadAnimation(com.android.server.wm.WindowState.this.mContext, android.R.anim.window_move_from_decor);
            this.mDuration = (long) (loadAnimation.computeDurationHint() * com.android.server.wm.WindowState.this.mWmService.getWindowAnimationScaleLocked());
            this.mInterpolator = loadAnimation.getInterpolator();
            this.mFrom.set(i, i2);
            this.mTo.set(i3, i4);
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public long getDuration() {
            return this.mDuration;
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public void apply(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, long j) {
            float interpolation = this.mInterpolator.getInterpolation(getFraction(j));
            transaction.setPosition(surfaceControl, this.mFrom.x + ((this.mTo.x - this.mFrom.x) * interpolation), this.mFrom.y + ((this.mTo.y - this.mFrom.y) * interpolation));
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.println(str + "from=" + this.mFrom + " to=" + this.mTo + " duration=" + this.mDuration);
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public void dumpDebugInner(android.util.proto.ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(1146756268034L);
            android.graphics.GraphicsProtos.dumpPointProto(this.mFrom, protoOutputStream, 1146756268033L);
            android.graphics.GraphicsProtos.dumpPointProto(this.mTo, protoOutputStream, 1146756268034L);
            protoOutputStream.write(1112396529667L, this.mDuration);
            protoOutputStream.end(start);
        }
    }

    com.android.internal.policy.KeyInterceptionInfo getKeyInterceptionInfo() {
        if (this.mKeyInterceptionInfo == null || this.mKeyInterceptionInfo.layoutParamsPrivateFlags != getAttrs().privateFlags || this.mKeyInterceptionInfo.layoutParamsType != getAttrs().type || this.mKeyInterceptionInfo.windowTitle != getWindowTag() || this.mKeyInterceptionInfo.windowOwnerUid != getOwningUid()) {
            this.mKeyInterceptionInfo = new com.android.internal.policy.KeyInterceptionInfo(getAttrs().type, getAttrs().privateFlags, getWindowTag().toString(), getOwningUid());
        }
        return this.mKeyInterceptionInfo;
    }

    @Override // com.android.server.wm.WindowContainer
    void getAnimationFrames(android.graphics.Rect rect, android.graphics.Rect rect2, android.graphics.Rect rect3, android.graphics.Rect rect4) {
        if (inFreeformWindowingMode()) {
            rect.set(getFrame());
        } else if (areAppWindowBoundsLetterboxed() || this.mToken.isFixedRotationTransforming()) {
            rect.set(getTask().getBounds());
        } else {
            rect.set(getParentFrame());
        }
        rect4.set(getAttrs().surfaceInsets);
        android.view.InsetsState insetsStateWithVisibilityOverride = getInsetsStateWithVisibilityOverride();
        rect2.set(insetsStateWithVisibilityOverride.calculateInsets(rect, android.view.WindowInsets.Type.systemBars(), false).toRect());
        rect3.set(insetsStateWithVisibilityOverride.calculateInsets(rect, android.view.WindowInsets.Type.systemBars(), true).toRect());
    }

    void setViewVisibility(int i) {
        this.mViewVisibility = i;
    }

    android.view.SurfaceControl getClientViewRootSurface() {
        return this.mWinAnimator.getSurfaceControl();
    }

    private void dropBufferFrom(android.view.SurfaceControl.Transaction transaction) {
        android.view.SurfaceControl clientViewRootSurface = getClientViewRootSurface();
        if (clientViewRootSurface == null) {
            return;
        }
        transaction.unsetBuffer(clientViewRootSurface);
    }

    @Override // com.android.server.wm.WindowContainer
    protected boolean shouldUpdateSyncOnReparent() {
        return (this.mSyncState == 0 || this.mLastConfigReportedToClient) ? false : true;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean prepareSync() {
        if (!this.mDrawHandlers.isEmpty()) {
            android.util.Slog.w(TAG, "prepareSync with mDrawHandlers, " + this + ", " + android.os.Debug.getCallers(8));
        }
        if (!super.prepareSync()) {
            return false;
        }
        if (this.mIsWallpaper) {
            return true;
        }
        if (this.mActivityRecord != null && this.mViewVisibility != 0 && this.mWinAnimator.mAttrType != 1 && this.mWinAnimator.mAttrType != 3) {
            return false;
        }
        this.mSyncState = 1;
        if (this.mPrepareSyncSeqId > 0) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, -5774445199273871848L, 0, null, java.lang.String.valueOf(this));
            dropBufferFrom(this.mSyncTransaction);
        }
        this.mSyncSeqId++;
        if (getSyncMethod() == 1) {
            this.mPrepareSyncSeqId = this.mSyncSeqId;
            requestRedrawForSync();
        } else if (this.mHasSurface && this.mWinAnimator.mDrawState != 1) {
            requestRedrawForSync();
        }
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isSyncFinished(com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup) {
        if (!isVisibleRequested() || isFullyTransparent()) {
            return true;
        }
        if (this.mSyncState == 1 && this.mLastConfigReportedToClient && isDrawn()) {
            onSyncFinishedDrawing();
        }
        return super.isSyncFinished(syncGroup);
    }

    @Override // com.android.server.wm.WindowContainer
    void finishSync(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup, boolean z) {
        com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup2 = getSyncGroup();
        if (syncGroup2 == null || syncGroup == syncGroup2) {
            this.mPrepareSyncSeqId = 0;
            if (z) {
                dropBufferFrom(this.mSyncTransaction);
            }
            super.finishSync(transaction, syncGroup, z);
        }
    }

    boolean finishDrawing(android.view.SurfaceControl.Transaction transaction, int i) {
        boolean z;
        boolean z2;
        android.view.SurfaceControl.Transaction transaction2 = transaction;
        if (this.mOrientationChangeRedrawRequestTime > 0) {
            android.util.Slog.i(TAG, "finishDrawing of orientation change: " + this + " " + (android.os.SystemClock.elapsedRealtime() - this.mOrientationChangeRedrawRequestTime) + "ms");
            this.mOrientationChangeRedrawRequestTime = 0L;
        } else if (this.mActivityRecord != null && this.mActivityRecord.mRelaunchStartTime != 0 && this.mActivityRecord.findMainWindow(false) == this) {
            android.util.Slog.i(TAG, "finishDrawing of relaunch: " + this + " " + (android.os.SystemClock.elapsedRealtime() - this.mActivityRecord.mRelaunchStartTime) + "ms");
            this.mActivityRecord.finishOrAbortReplacingWindow();
        }
        if (this.mActivityRecord != null && this.mAttrs.type == 3) {
            this.mWmService.mAtmService.mTaskSupervisor.getActivityMetricsLogger().notifyStartingWindowDrawn(this.mActivityRecord);
        }
        boolean z3 = this.mPrepareSyncSeqId > 0;
        boolean z4 = z3 && this.mPrepareSyncSeqId > i;
        if (z4 && transaction2 != null) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_SYNC_ENGINE, 8097934579596343476L, 5, null, java.lang.Long.valueOf(i), java.lang.Long.valueOf(this.mPrepareSyncSeqId), java.lang.String.valueOf(this));
            dropBufferFrom(transaction);
        }
        boolean executeDrawHandlers = executeDrawHandlers(transaction, i);
        com.android.server.wm.AsyncRotationController asyncRotationController = this.mDisplayContent.getAsyncRotationController();
        if (asyncRotationController != null && asyncRotationController.handleFinishDrawing(this, transaction2)) {
            z2 = true;
            transaction2 = null;
            z = false;
        } else if (z3) {
            if (z4) {
                z = false;
            } else {
                z = onSyncFinishedDrawing();
            }
            if (transaction2 == null) {
                z2 = false;
            } else {
                this.mSyncTransaction.merge(transaction2);
                transaction2 = null;
                z2 = false;
            }
        } else if (!syncNextBuffer()) {
            z = false;
            z2 = false;
        } else {
            z = onSyncFinishedDrawing();
            z2 = false;
        }
        boolean finishDrawingLocked = this.mWinAnimator.finishDrawingLocked(transaction2) | z;
        if (z2) {
            return false;
        }
        return executeDrawHandlers || finishDrawingLocked;
    }

    void immediatelyNotifyBlastSync() {
        finishDrawing(null, Integer.MAX_VALUE);
        this.mWmService.mH.removeMessages(64, this);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean fillsParent() {
        return this.mAttrs.type == 3;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean showWallpaper() {
        if (!isVisibleRequested() || inMultiWindowMode()) {
            return false;
        }
        return hasWallpaper();
    }

    boolean hasWallpaper() {
        return (this.mAttrs.flags & 1048576) != 0 || hasWallpaperForLetterboxBackground();
    }

    boolean hasWallpaperForLetterboxBackground() {
        return this.mActivityRecord != null && this.mActivityRecord.hasWallpaperBackgroundForLetterbox();
    }

    private boolean shouldSendRedrawForSync() {
        if (this.mRedrawForSyncReported) {
            return false;
        }
        if (!this.mInRelayout || (this.mPrepareSyncSeqId <= 0 && !(this.mViewVisibility == 0 && this.mWinAnimator.mDrawState == 1))) {
            return syncNextBuffer();
        }
        return false;
    }

    int getSyncMethod() {
        com.android.server.wm.BLASTSyncEngine.SyncGroup syncGroup = getSyncGroup();
        if (syncGroup == null) {
            return 0;
        }
        return this.mSyncMethodOverride != -1 ? this.mSyncMethodOverride : syncGroup.mSyncMethod;
    }

    boolean shouldSyncWithBuffers() {
        return !this.mDrawHandlers.isEmpty() || getSyncMethod() == 1;
    }

    void requestRedrawForSync() {
        this.mRedrawForSyncReported = false;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean syncNextBuffer() {
        return super.syncNextBuffer() || this.mDrawHandlers.size() != 0;
    }

    void applyWithNextDraw(java.util.function.Consumer<android.view.SurfaceControl.Transaction> consumer) {
        if (this.mSyncState != 0) {
            android.util.Slog.w(TAG, "applyWithNextDraw with mSyncState=" + this.mSyncState + ", " + this + ", " + android.os.Debug.getCallers(8));
        }
        this.mSyncSeqId++;
        this.mDrawHandlers.add(new com.android.server.wm.WindowState.DrawHandler(this.mSyncSeqId, consumer));
        requestRedrawForSync();
        this.mWmService.mH.sendNewMessageDelayed(64, this, 5000L);
    }

    boolean executeDrawHandlers(android.view.SurfaceControl.Transaction transaction, int i) {
        boolean z;
        if (transaction != null) {
            z = false;
        } else {
            transaction = this.mTmpTransaction;
            z = true;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        boolean z2 = false;
        for (int i2 = 0; i2 < this.mDrawHandlers.size(); i2++) {
            com.android.server.wm.WindowState.DrawHandler drawHandler = this.mDrawHandlers.get(i2);
            if (drawHandler.mSeqId <= i) {
                drawHandler.mConsumer.accept(transaction);
                arrayList.add(drawHandler);
                z2 = true;
            }
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            this.mDrawHandlers.remove((com.android.server.wm.WindowState.DrawHandler) arrayList.get(i3));
        }
        if (z2) {
            this.mWmService.mH.removeMessages(64, this);
        }
        if (z) {
            transaction.apply();
        }
        return z2;
    }

    void setSurfaceTranslationY(int i) {
        this.mSurfaceTranslationY = i;
    }

    @Override // com.android.server.wm.WindowContainer
    int getWindowType() {
        return this.mAttrs.type;
    }

    void markRedrawForSyncReported() {
        this.mRedrawForSyncReported = true;
    }

    boolean setWallpaperOffset(int i, int i2, float f) {
        if (this.mXOffset == i && this.mYOffset == i2 && java.lang.Float.compare(this.mWallpaperScale, f) == 0) {
            return false;
        }
        this.mXOffset = i;
        this.mYOffset = i2;
        this.mWallpaperScale = f;
        scheduleAnimation();
        return true;
    }

    boolean isTrustedOverlay() {
        if (com.android.window.flags.Flags.surfaceTrustedOverlay()) {
            com.android.server.wm.WindowState parentWindow = getParentWindow();
            return isWindowTrustedOverlay() || (parentWindow != null && parentWindow.isWindowTrustedOverlay());
        }
        return this.mInputWindowHandle.isTrustedOverlay();
    }

    @Override // com.android.server.wm.InputTarget
    public boolean receiveFocusFromTapOutside() {
        return canReceiveKeys(true);
    }

    @Override // com.android.server.wm.InputTarget
    public void handleTapOutsideFocusOutsideSelf() {
    }

    @Override // com.android.server.wm.InputTarget
    public void handleTapOutsideFocusInsideSelf() {
        this.mWmService.moveDisplayToTopInternal(getDisplayId());
        this.mWmService.handleTaskFocusChange(getTask(), this.mActivityRecord);
    }

    void clearClientTouchableRegion() {
        this.mTouchableInsets = 0;
        this.mGivenTouchableRegion.setEmpty();
    }

    @Override // com.android.server.wm.InputTarget
    public boolean shouldControlIme() {
        return !inMultiWindowMode();
    }

    @Override // com.android.server.wm.InputTarget
    public boolean canScreenshotIme() {
        return !isSecureLocked();
    }

    @Override // com.android.server.wm.InputTarget
    public com.android.server.wm.ActivityRecord getActivityRecord() {
        return this.mActivityRecord;
    }

    @Override // com.android.server.wm.InputTarget
    public boolean isInputMethodClientFocus(int i, int i2) {
        return getDisplayContent().isInputMethodClientFocus(i, i2);
    }

    @Override // com.android.server.wm.InputTarget
    public void dumpProto(android.util.proto.ProtoOutputStream protoOutputStream, long j, int i) {
        dumpDebug(protoOutputStream, j, i);
    }

    public boolean cancelAndRedraw() {
        return this.mPrepareSyncSeqId > 0;
    }

    void setSecureLocked(boolean z) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, 8269653477215188641L, 3, null, java.lang.Boolean.valueOf(z), java.lang.String.valueOf(getName()));
        if (com.android.window.flags.Flags.secureWindowState()) {
            if (this.mSurfaceControl == null) {
                return;
            } else {
                getPendingTransaction().setSecure(this.mSurfaceControl, z);
            }
        } else if (this.mWinAnimator.mSurfaceController == null || this.mWinAnimator.mSurfaceController.mSurfaceControl == null) {
            return;
        } else {
            getPendingTransaction().setSecure(this.mWinAnimator.mSurfaceController.mSurfaceControl, z);
        }
        if (this.mDisplayContent != null) {
            this.mDisplayContent.refreshImeSecureFlag(getSyncTransaction());
        }
        this.mWmService.scheduleAnimationLocked();
    }
}
