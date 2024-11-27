package com.android.server.wm;

/* loaded from: classes3.dex */
class WindowStateAnimator {
    static final int COMMIT_DRAW_PENDING = 2;
    static final int DRAW_PENDING = 1;
    static final int HAS_DRAWN = 4;
    static final int NO_SURFACE = 0;
    static final int PRESERVED_SURFACE_LAYER = 1;
    static final int READY_TO_SHOW = 3;
    static final int ROOT_TASK_CLIP_AFTER_ANIM = 0;
    static final int ROOT_TASK_CLIP_NONE = 1;
    static final java.lang.String TAG = "WindowManager";
    boolean mAnimationIsEntrance;
    final com.android.server.wm.WindowAnimator mAnimator;
    int mAttrType;
    final android.content.Context mContext;
    int mDrawState;
    boolean mEnterAnimationPending;
    boolean mEnteringAnimation;
    final boolean mIsWallpaper;
    boolean mLastHidden;
    final com.android.server.policy.WindowManagerPolicy mPolicy;
    final com.android.server.wm.WindowManagerService mService;
    final com.android.server.wm.Session mSession;
    com.android.server.wm.WindowSurfaceController mSurfaceController;
    private final com.android.server.wm.WallpaperController mWallpaperControllerLocked;
    final com.android.server.wm.WindowState mWin;
    float mShownAlpha = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    float mAlpha = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    float mLastAlpha = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private final android.graphics.Rect mSystemDecorRect = new android.graphics.Rect();

    java.lang.String drawStateToString() {
        switch (this.mDrawState) {
            case 0:
                return "NO_SURFACE";
            case 1:
                return "DRAW_PENDING";
            case 2:
                return "COMMIT_DRAW_PENDING";
            case 3:
                return "READY_TO_SHOW";
            case 4:
                return "HAS_DRAWN";
            default:
                return java.lang.Integer.toString(this.mDrawState);
        }
    }

    WindowStateAnimator(com.android.server.wm.WindowState windowState) {
        com.android.server.wm.WindowManagerService windowManagerService = windowState.mWmService;
        this.mService = windowManagerService;
        this.mAnimator = windowManagerService.mAnimator;
        this.mPolicy = windowManagerService.mPolicy;
        this.mContext = windowManagerService.mContext;
        this.mWin = windowState;
        this.mSession = windowState.mSession;
        this.mAttrType = windowState.mAttrs.type;
        this.mIsWallpaper = windowState.mIsWallpaper;
        this.mWallpaperControllerLocked = windowState.getDisplayContent().mWallpaperController;
    }

    void onAnimationFinished() {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -1495677286613044867L, 60, null, java.lang.String.valueOf(this), java.lang.Boolean.valueOf(this.mWin.mAnimatingExit), java.lang.Boolean.valueOf(this.mWin.mActivityRecord != null && this.mWin.mActivityRecord.reportedVisible));
        this.mWin.checkPolicyVisibilityChange();
        com.android.server.wm.DisplayContent displayContent = this.mWin.getDisplayContent();
        if ((this.mAttrType == 2000 || this.mAttrType == 2040) && this.mWin.isVisibleByPolicy()) {
            displayContent.setLayoutNeeded();
        }
        this.mWin.onExitAnimationDone();
        displayContent.pendingLayoutChanges |= 8;
        if (displayContent.mWallpaperController.isWallpaperTarget(this.mWin)) {
            displayContent.pendingLayoutChanges |= 4;
        }
        if (this.mWin.mActivityRecord != null) {
            this.mWin.mActivityRecord.updateReportedVisibilityLocked();
        }
    }

    void hide(android.view.SurfaceControl.Transaction transaction, java.lang.String str) {
        if (!this.mLastHidden) {
            this.mLastHidden = true;
            if (this.mSurfaceController != null) {
                this.mSurfaceController.hide(transaction, str);
            }
        }
    }

    boolean finishDrawingLocked(android.view.SurfaceControl.Transaction transaction) {
        boolean z = false;
        boolean z2 = this.mWin.mAttrs.type == 3;
        if (z2) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, 3436877176443058520L, 0, null, java.lang.String.valueOf(this.mWin), java.lang.String.valueOf(drawStateToString()));
        }
        if (this.mDrawState == 1) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_DRAW, 345647873457403698L, 0, null, java.lang.String.valueOf(this.mWin), java.lang.String.valueOf(this.mSurfaceController));
            if (z2) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_STARTING_WINDOW, -2385558637577093121L, 0, null, java.lang.String.valueOf(this.mWin));
            }
            this.mDrawState = 2;
            z = true;
        }
        if (transaction == null) {
            return z;
        }
        this.mWin.getSyncTransaction().merge(transaction);
        return true;
    }

    boolean commitFinishDrawingLocked() {
        if (this.mDrawState != 2 && this.mDrawState != 3) {
            return false;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -3490933626936411542L, 0, null, java.lang.String.valueOf(this.mSurfaceController));
        this.mDrawState = 3;
        com.android.server.wm.ActivityRecord activityRecord = this.mWin.mActivityRecord;
        if (activityRecord == null || activityRecord.canShowWindows() || this.mWin.mAttrs.type == 3) {
            return this.mWin.performShowLocked();
        }
        return false;
    }

    void resetDrawState() {
        this.mDrawState = 1;
        if (this.mWin.mActivityRecord != null && !this.mWin.mActivityRecord.isAnimating(1)) {
            this.mWin.mActivityRecord.clearAllDrawn();
        }
    }

    com.android.server.wm.WindowSurfaceController createSurfaceLocked() {
        int i;
        int i2;
        com.android.server.wm.WindowState windowState = this.mWin;
        if (this.mSurfaceController != null) {
            return this.mSurfaceController;
        }
        windowState.setHasSurface(false);
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -6088246515441976339L, 0, null, java.lang.String.valueOf(this));
        resetDrawState();
        this.mService.makeWindowFreezingScreenIfNeededLocked(windowState);
        android.view.WindowManager.LayoutParams layoutParams = windowState.mAttrs;
        if (!com.android.window.flags.Flags.secureWindowState() && windowState.isSecureLocked()) {
            i = 132;
        } else {
            i = 4;
        }
        if ((this.mWin.mAttrs.privateFlags & 1048576) == 0) {
            i2 = i;
        } else {
            i2 = i | 64;
        }
        try {
            this.mSurfaceController = new com.android.server.wm.WindowSurfaceController(layoutParams.getTitle().toString(), (layoutParams.flags & 16777216) != 0 ? -3 : layoutParams.format, i2, this, layoutParams.type);
            this.mSurfaceController.setColorSpaceAgnostic(windowState.getPendingTransaction(), (layoutParams.privateFlags & 16777216) != 0);
            windowState.setHasSurface(true);
            windowState.mInputWindowHandle.forceChange();
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_SURFACE_ALLOC, 2353125758087345363L, 336, null, java.lang.String.valueOf(this.mSurfaceController), java.lang.String.valueOf(this.mSession.mSurfaceSession), java.lang.Long.valueOf(this.mSession.mPid), java.lang.Long.valueOf(layoutParams.format), java.lang.Long.valueOf(i2), java.lang.String.valueOf(this));
            this.mLastHidden = true;
            return this.mSurfaceController;
        } catch (android.view.Surface.OutOfResourcesException e) {
            android.util.Slog.w(TAG, "OutOfResourcesException creating surface");
            this.mService.mRoot.reclaimSomeSurfaceMemory(this, "create", true);
            this.mDrawState = 0;
            return null;
        } catch (java.lang.Exception e2) {
            android.util.Slog.e(TAG, "Exception creating surface (parent dead?)", e2);
            this.mDrawState = 0;
            return null;
        }
    }

    boolean hasSurface() {
        return this.mSurfaceController != null && this.mSurfaceController.hasSurface();
    }

    void destroySurfaceLocked(android.view.SurfaceControl.Transaction transaction) {
        if (this.mSurfaceController == null) {
            return;
        }
        this.mWin.mHidden = true;
        try {
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_SURFACE_ALLOC, -4491856282178275074L, 0, null, java.lang.String.valueOf(this.mWin), java.lang.String.valueOf(new java.lang.RuntimeException().fillInStackTrace()));
            destroySurface(transaction);
            this.mWallpaperControllerLocked.hideWallpapers(this.mWin);
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.w(TAG, "Exception thrown when destroying Window " + this + " surface " + this.mSurfaceController + " session " + this.mSession + ": " + e.toString());
        }
        this.mWin.setHasSurface(false);
        if (this.mSurfaceController != null) {
            this.mSurfaceController.setShown(false);
        }
        this.mSurfaceController = null;
        this.mDrawState = 0;
    }

    void computeShownFrameLocked() {
        if ((this.mIsWallpaper && this.mService.mRoot.mWallpaperActionPending) || this.mWin.isDragResizeChanged()) {
            return;
        }
        this.mShownAlpha = this.mAlpha;
    }

    void prepareSurfaceLocked(android.view.SurfaceControl.Transaction transaction) {
        com.android.server.wm.WindowState windowState = this.mWin;
        if (!hasSurface()) {
            if (windowState.getOrientationChanging() && windowState.isGoneForLayout()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 8602950884833508970L, 0, null, java.lang.String.valueOf(windowState));
                windowState.setOrientationChanging(false);
                return;
            }
            return;
        }
        computeShownFrameLocked();
        if (!windowState.isOnScreen()) {
            hide(transaction, "prepareSurfaceLocked");
            this.mWallpaperControllerLocked.hideWallpapers(windowState);
            if (windowState.getOrientationChanging() && windowState.isGoneForLayout()) {
                windowState.setOrientationChanging(false);
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 8602950884833508970L, 0, null, java.lang.String.valueOf(windowState));
            }
        } else if (this.mLastAlpha != this.mShownAlpha || this.mLastHidden) {
            this.mLastAlpha = this.mShownAlpha;
            com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, -5079712802591263622L, 168, null, java.lang.String.valueOf(this.mSurfaceController), java.lang.Double.valueOf(this.mShownAlpha), java.lang.Double.valueOf(windowState.mHScale), java.lang.Double.valueOf(windowState.mVScale), java.lang.String.valueOf(windowState));
            if (this.mSurfaceController.prepareToShowInTransaction(transaction, this.mShownAlpha) && this.mDrawState == 4 && this.mLastHidden) {
                this.mSurfaceController.showRobustly(transaction);
                this.mLastHidden = false;
                com.android.server.wm.DisplayContent displayContent = windowState.getDisplayContent();
                if (!displayContent.getLastHasContent()) {
                    displayContent.pendingLayoutChanges |= 8;
                }
            }
        }
        if (windowState.getOrientationChanging()) {
            if (!windowState.isDrawn()) {
                if (windowState.mDisplayContent.shouldSyncRotationChange(windowState)) {
                    windowState.mWmService.mRoot.mOrientationChangeComplete = false;
                    this.mAnimator.mLastWindowFreezeSource = windowState;
                }
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, -2824875917893878016L, 0, null, java.lang.String.valueOf(windowState));
                return;
            }
            windowState.setOrientationChanging(false);
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ORIENTATION, 7457181879495900576L, 0, null, java.lang.String.valueOf(windowState));
        }
    }

    void setOpaqueLocked(boolean z) {
        if (this.mSurfaceController == null) {
            return;
        }
        this.mSurfaceController.setOpaque(z);
    }

    void setColorSpaceAgnosticLocked(boolean z) {
        if (this.mSurfaceController == null) {
            return;
        }
        this.mSurfaceController.setColorSpaceAgnostic(this.mWin.getPendingTransaction(), z);
    }

    void applyEnterAnimationLocked() {
        int i;
        if (this.mEnterAnimationPending) {
            this.mEnterAnimationPending = false;
            i = 1;
        } else {
            i = 3;
        }
        if (this.mAttrType != 1 && !this.mIsWallpaper && (this.mWin.mActivityRecord == null || !this.mWin.mActivityRecord.hasStartingWindow())) {
            applyAnimationLocked(i, true);
        }
        if (this.mService.mAccessibilityController.hasCallbacks()) {
            this.mService.mAccessibilityController.onWindowTransition(this.mWin, i);
        }
    }

    boolean applyAnimationLocked(int i, boolean z) {
        int i2 = 1;
        if (this.mWin.isAnimating() && this.mAnimationIsEntrance == z) {
            return true;
        }
        if (this.mWin.mAttrs.type == 2011) {
            this.mWin.getDisplayContent().adjustForImeIfNeeded();
            if (z) {
                this.mWin.setDisplayLayoutNeeded();
                this.mService.mWindowPlacerLocked.requestTraversal();
            }
        }
        if (this.mWin.mControllableInsetProvider != null) {
            return false;
        }
        if (this.mWin.mToken.okToAnimate()) {
            int selectAnimation = this.mWin.getDisplayContent().getDisplayPolicy().selectAnimation(this.mWin, i);
            int i3 = -1;
            android.view.animation.Animation animation = null;
            if (selectAnimation != 0) {
                if (selectAnimation != -1) {
                    android.os.Trace.traceBegin(32L, "WSA#loadAnimation");
                    animation = android.view.animation.AnimationUtils.loadAnimation(this.mContext, selectAnimation);
                    android.os.Trace.traceEnd(32L);
                }
            } else {
                switch (i) {
                    case 1:
                        i2 = 0;
                        break;
                    case 2:
                        break;
                    case 3:
                        i2 = 2;
                        break;
                    case 4:
                        i2 = 3;
                        break;
                    default:
                        i2 = -1;
                        break;
                }
                if (i2 >= 0) {
                    animation = this.mWin.getDisplayContent().mAppTransition.loadAnimationAttr(this.mWin.mAttrs, i2, 0);
                    i3 = i2;
                } else {
                    i3 = i2;
                }
            }
            if (com.android.internal.protolog.ProtoLogImpl_1545807451.isEnabled(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM)) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_ANIM, -5668794009329913533L, 13588, null, java.lang.String.valueOf(this), java.lang.Long.valueOf(selectAnimation), java.lang.Long.valueOf(i3), java.lang.String.valueOf(animation), java.lang.Long.valueOf(i), java.lang.Long.valueOf(this.mAttrType), java.lang.Boolean.valueOf(z), java.lang.String.valueOf(android.os.Debug.getCallers(20)));
            }
            if (animation != null) {
                android.os.Trace.traceBegin(32L, "WSA#startAnimation");
                this.mWin.startAnimation(animation);
                android.os.Trace.traceEnd(32L);
                this.mAnimationIsEntrance = z;
            }
        } else {
            this.mWin.cancelAnimation();
        }
        return this.mWin.isAnimating(0, 16);
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        if (this.mSurfaceController != null) {
            this.mSurfaceController.dumpDebug(protoOutputStream, 1146756268034L);
        }
        protoOutputStream.write(1159641169923L, this.mDrawState);
        this.mSystemDecorRect.dumpDebug(protoOutputStream, 1146756268036L);
        protoOutputStream.end(start);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        if (this.mAnimationIsEntrance) {
            printWriter.print(str);
            printWriter.print(" mAnimationIsEntrance=");
            printWriter.print(this.mAnimationIsEntrance);
        }
        if (this.mSurfaceController != null) {
            this.mSurfaceController.dump(printWriter, str, z);
        }
        if (z) {
            printWriter.print(str);
            printWriter.print("mDrawState=");
            printWriter.print(drawStateToString());
            printWriter.print(str);
            printWriter.print(" mLastHidden=");
            printWriter.println(this.mLastHidden);
            printWriter.print(str);
            printWriter.print("mEnterAnimationPending=" + this.mEnterAnimationPending);
            printWriter.print(str);
            printWriter.print("mSystemDecorRect=");
            this.mSystemDecorRect.printShortString(printWriter);
            printWriter.println();
        }
        if (this.mShownAlpha != 1.0f || this.mAlpha != 1.0f || this.mLastAlpha != 1.0f) {
            printWriter.print(str);
            printWriter.print("mShownAlpha=");
            printWriter.print(this.mShownAlpha);
            printWriter.print(" mAlpha=");
            printWriter.print(this.mAlpha);
            printWriter.print(" mLastAlpha=");
            printWriter.println(this.mLastAlpha);
        }
        if (this.mWin.mGlobalScale != 1.0f) {
            printWriter.print(str);
            printWriter.print("mGlobalScale=");
            printWriter.print(this.mWin.mGlobalScale);
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer("WindowStateAnimator{");
        stringBuffer.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        stringBuffer.append(' ');
        stringBuffer.append(this.mWin.mAttrs.getTitle());
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    boolean getShown() {
        if (this.mSurfaceController != null) {
            return this.mSurfaceController.getShown();
        }
        return false;
    }

    void destroySurface(android.view.SurfaceControl.Transaction transaction) {
        try {
            try {
                if (this.mSurfaceController != null) {
                    this.mSurfaceController.destroy(transaction);
                }
            } catch (java.lang.RuntimeException e) {
                android.util.Slog.w(TAG, "Exception thrown when destroying surface " + this + " surface " + this.mSurfaceController + " session " + this.mSession + ": " + e);
            }
        } finally {
            this.mWin.setHasSurface(false);
            this.mSurfaceController = null;
            this.mDrawState = 0;
        }
    }

    android.view.SurfaceControl getSurfaceControl() {
        if (!hasSurface()) {
            return null;
        }
        return this.mSurfaceController.mSurfaceControl;
    }
}
