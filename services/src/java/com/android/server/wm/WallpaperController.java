package com.android.server.wm;

/* loaded from: classes3.dex */
class WallpaperController {
    private static final java.lang.String TAG = "WindowManager";
    private static final int WALLPAPER_DRAW_NORMAL = 0;
    private static final int WALLPAPER_DRAW_PENDING = 1;
    private static final long WALLPAPER_DRAW_PENDING_TIMEOUT_DURATION = 500;
    private static final int WALLPAPER_DRAW_TIMEOUT = 2;
    private static final long WALLPAPER_TIMEOUT = 150;
    private static final long WALLPAPER_TIMEOUT_RECOVERY = 10000;
    private com.android.server.wm.DisplayContent mDisplayContent;
    private volatile boolean mIsWallpaperNotifiedOnDisplaySwitch;
    private long mLastWallpaperTimeoutTime;
    private float mMaxWallpaperScale;
    private float mMinWallpaperScale;
    private com.android.server.wm.WindowManagerService mService;
    private boolean mShouldOffsetWallpaperCenter;
    private com.android.server.wm.WindowState mWaitingOnWallpaper;
    private com.android.server.wallpaper.WallpaperCropper.WallpaperCropUtils mWallpaperCropUtils = null;
    private final java.util.ArrayList<com.android.server.wm.WallpaperWindowToken> mWallpaperTokens = new java.util.ArrayList<>();
    private com.android.server.wm.WindowState mWallpaperTarget = null;
    private com.android.server.wm.WindowState mPrevWallpaperTarget = null;
    private float mLastWallpaperZoomOut = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
    private boolean mLastFrozen = false;
    private int mWallpaperDrawState = 0;

    @android.annotation.Nullable
    private android.graphics.Point mLargestDisplaySize = null;
    private final com.android.server.wm.WallpaperController.FindWallpaperTargetResult mFindResults = new com.android.server.wm.WallpaperController.FindWallpaperTargetResult();
    private final com.android.internal.util.ToBooleanFunction<com.android.server.wm.WindowState> mFindWallpaperTargetFunction = new com.android.internal.util.ToBooleanFunction() { // from class: com.android.server.wm.WallpaperController$$ExternalSyntheticLambda0
        public final boolean apply(java.lang.Object obj) {
            boolean lambda$new$0;
            lambda$new$0 = com.android.server.wm.WallpaperController.this.lambda$new$0((com.android.server.wm.WindowState) obj);
            return lambda$new$0;
        }
    };
    private java.util.function.Consumer<com.android.server.wm.WindowState> mComputeMaxZoomOutFunction = new java.util.function.Consumer() { // from class: com.android.server.wm.WallpaperController$$ExternalSyntheticLambda1
        @Override // java.util.function.Consumer
        public final void accept(java.lang.Object obj) {
            com.android.server.wm.WallpaperController.this.lambda$new$1((com.android.server.wm.WindowState) obj);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(com.android.server.wm.WindowState windowState) {
        boolean isShellTransitionsEnabled = windowState.mTransitionController.isShellTransitionsEnabled();
        if (!isShellTransitionsEnabled) {
            if (windowState.mActivityRecord != null && !windowState.mActivityRecord.isVisible() && !windowState.mActivityRecord.isAnimating(3)) {
                return false;
            }
        } else {
            com.android.server.wm.ActivityRecord activityRecord = windowState.mActivityRecord;
            if (activityRecord != null && !activityRecord.isVisibleRequested() && !activityRecord.isVisible()) {
                return false;
            }
        }
        com.android.server.wm.WindowContainer animatingContainer = windowState.mActivityRecord != null ? windowState.mActivityRecord.getAnimatingContainer() : null;
        if (!isShellTransitionsEnabled && animatingContainer != null && animatingContainer.isAnimating(3) && com.android.server.wm.AppTransition.isKeyguardGoingAwayTransitOld(animatingContainer.mTransit) && (animatingContainer.mTransitFlags & 4) != 0) {
            this.mFindResults.setUseTopWallpaperAsTarget(true);
        }
        if (this.mService.mPolicy.isKeyguardLocked()) {
            if (windowState.canShowWhenLocked()) {
                if (this.mService.mPolicy.isKeyguardOccluded() || (!isShellTransitionsEnabled ? this.mService.mPolicy.isKeyguardUnoccluding() : windowState.inTransition())) {
                    this.mFindResults.mNeedsShowWhenLockedWallpaper = (isFullscreen(windowState.mAttrs) && (windowState.mActivityRecord == null || windowState.mActivityRecord.fillsParent())) ? false : true;
                }
            } else if (windowState.hasWallpaper() && this.mService.mPolicy.isKeyguardHostWindow(windowState.mAttrs) && windowState.mTransitionController.isTransitionOnDisplay(this.mDisplayContent)) {
                this.mFindResults.setWallpaperTarget(windowState);
                return false;
            }
        }
        boolean z = windowState.hasWallpaper() || (animatingContainer != null && animatingContainer.getAnimation() != null && animatingContainer.getAnimation().getShowWallpaper());
        if (isRecentsTransitionTarget(windowState) || isBackNavigationTarget(windowState)) {
            this.mFindResults.setWallpaperTarget(windowState);
            return true;
        }
        if (!z || !windowState.isOnScreen() || (this.mWallpaperTarget != windowState && !windowState.isDrawFinishedLw())) {
            return false;
        }
        this.mFindResults.setWallpaperTarget(windowState);
        this.mFindResults.setIsWallpaperTargetForLetterbox(windowState.hasWallpaperForLetterboxBackground());
        if (windowState == this.mWallpaperTarget) {
            windowState.isAnimating(3);
        }
        return (windowState.mActivityRecord == null && this.mDisplayContent.isKeyguardGoingAway()) ? false : true;
    }

    private boolean isRecentsTransitionTarget(com.android.server.wm.WindowState windowState) {
        com.android.server.wm.RecentsAnimationController recentsAnimationController;
        return (windowState.mTransitionController.isShellTransitionsEnabled() || (recentsAnimationController = this.mService.getRecentsAnimationController()) == null || !recentsAnimationController.isWallpaperVisible(windowState)) ? false : true;
    }

    private boolean isBackNavigationTarget(com.android.server.wm.WindowState windowState) {
        return this.mService.mAtmService.mBackNavigationController.isWallpaperVisible(windowState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(com.android.server.wm.WindowState windowState) {
        if (!windowState.mIsWallpaper && java.lang.Float.compare(windowState.mWallpaperZoomOut, this.mLastWallpaperZoomOut) > 0) {
            this.mLastWallpaperZoomOut = windowState.mWallpaperZoomOut;
        }
    }

    WallpaperController(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent) {
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        android.content.res.Resources resources = windowManagerService.mContext.getResources();
        this.mMinWallpaperScale = resources.getFloat(android.R.dimen.config_signalCutoutWidthFraction);
        this.mMaxWallpaperScale = resources.getFloat(android.R.dimen.config_signalCutoutHeightFraction);
        this.mShouldOffsetWallpaperCenter = resources.getBoolean(android.R.bool.config_noHomeScreen);
    }

    void resetLargestDisplay(android.view.Display display) {
        if (display != null && display.getType() == 1) {
            this.mLargestDisplaySize = null;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setMinWallpaperScale(float f) {
        this.mMinWallpaperScale = f;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setMaxWallpaperScale(float f) {
        this.mMaxWallpaperScale = f;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setShouldOffsetWallpaperCenter(boolean z) {
        this.mShouldOffsetWallpaperCenter = z;
    }

    @android.annotation.Nullable
    private android.graphics.Point findLargestDisplaySize() {
        if (!this.mShouldOffsetWallpaperCenter || com.android.window.flags.Flags.multiCrop()) {
            return null;
        }
        android.graphics.Point point = new android.graphics.Point();
        java.util.List<android.view.DisplayInfo> possibleDisplayInfoLocked = this.mService.getPossibleDisplayInfoLocked(0);
        float f = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        for (int i = 0; i < possibleDisplayInfoLocked.size(); i++) {
            android.view.DisplayInfo displayInfo = possibleDisplayInfoLocked.get(i);
            float f2 = displayInfo.logicalWidth / displayInfo.physicalXDpi;
            if (displayInfo.type == 1 && f2 > f) {
                point.set(displayInfo.logicalWidth, displayInfo.logicalHeight);
                f = f2;
            }
        }
        return point;
    }

    void setWallpaperCropUtils(com.android.server.wallpaper.WallpaperCropper.WallpaperCropUtils wallpaperCropUtils) {
        this.mWallpaperCropUtils = wallpaperCropUtils;
    }

    com.android.server.wm.WindowState getWallpaperTarget() {
        return this.mWallpaperTarget;
    }

    com.android.server.wm.WindowState getPrevWallpaperTarget() {
        return this.mPrevWallpaperTarget;
    }

    boolean isWallpaperTarget(com.android.server.wm.WindowState windowState) {
        return windowState == this.mWallpaperTarget;
    }

    boolean isBelowWallpaperTarget(com.android.server.wm.WindowState windowState) {
        return this.mWallpaperTarget != null && this.mWallpaperTarget.mLayer >= windowState.mBaseLayer;
    }

    boolean isWallpaperVisible() {
        for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
            if (this.mWallpaperTokens.get(size).isVisible()) {
                return true;
            }
        }
        return false;
    }

    boolean isWallpaperTargetAnimating() {
        return this.mWallpaperTarget != null && this.mWallpaperTarget.isAnimating(3) && (this.mWallpaperTarget.mActivityRecord == null || !this.mWallpaperTarget.mActivityRecord.isWaitingForTransitionStart());
    }

    void hideDeferredWallpapersIfNeededLegacy() {
        for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
            com.android.server.wm.WallpaperWindowToken wallpaperWindowToken = this.mWallpaperTokens.get(size);
            if (!wallpaperWindowToken.isVisibleRequested()) {
                wallpaperWindowToken.commitVisibility(false);
            }
        }
    }

    void hideWallpapers(com.android.server.wm.WindowState windowState) {
        if ((this.mWallpaperTarget != null && (this.mWallpaperTarget != windowState || this.mPrevWallpaperTarget != null)) || this.mFindResults.useTopWallpaperAsTarget) {
            return;
        }
        for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
            com.android.server.wm.WallpaperWindowToken wallpaperWindowToken = this.mWallpaperTokens.get(size);
            wallpaperWindowToken.setVisibility(false);
            if (com.android.internal.protolog.ProtoLogImpl_1545807451.isEnabled(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WALLPAPER) && wallpaperWindowToken.isVisible()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WALLPAPER, -5254364639040552989L, 0, null, java.lang.String.valueOf(wallpaperWindowToken), java.lang.String.valueOf(windowState), java.lang.String.valueOf(this.mWallpaperTarget), java.lang.String.valueOf(this.mPrevWallpaperTarget), java.lang.String.valueOf(android.os.Debug.getCallers(5)));
            }
        }
    }

    boolean updateWallpaperOffset(com.android.server.wm.WindowState windowState, boolean z) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        float f;
        boolean z2;
        boolean z3;
        android.graphics.Rect parentFrame = windowState.getParentFrame();
        int width = parentFrame.width();
        int height = parentFrame.height();
        float f2 = height;
        float f3 = width / f2;
        android.graphics.Point point = new android.graphics.Point(width, height);
        com.android.server.wm.WallpaperWindowToken asWallpaperToken = windowState.mToken.asWallpaperToken();
        if (com.android.window.flags.Flags.multiCrop()) {
            if (this.mWallpaperCropUtils == null) {
                android.util.Slog.e(TAG, "Update wallpaper offsets before the system is ready. Aborting");
                return false;
            }
            android.graphics.Rect crop = this.mWallpaperCropUtils.getCrop(point, new android.graphics.Point(windowState.mRequestedWidth, windowState.mRequestedHeight), asWallpaperToken.getCropHints(), windowState.isRtl());
            f = crop.isEmpty() ? 1.0f : (f2 / crop.height()) / windowState.mVScale;
            float f4 = f - 1.0f;
            int height2 = (-crop.left) + ((int) (((crop.height() * f4) * f3) / 2.0f));
            i5 = (-crop.top) + ((int) ((f4 * crop.height()) / 2.0f));
            i2 = ((int) (crop.width() * windowState.mHScale)) - width;
            i3 = ((int) (crop.height() * windowState.mVScale)) - height;
            i4 = height2;
        } else {
            android.graphics.Rect frame = windowState.getFrame();
            int width2 = frame.width() - width;
            int height3 = frame.height() - height;
            if ((windowState.mAttrs.flags & 16384) == 0) {
                i = 0;
            } else if (java.lang.Math.abs(width2) <= 1 || java.lang.Math.abs(height3) <= 1) {
                i = 0;
            } else {
                android.util.Slog.d(TAG, "Skip wallpaper offset with inconsistent orientation, bounds=" + parentFrame + " frame=" + frame);
                return false;
            }
            i2 = width2;
            i3 = height3;
            i4 = i;
            i5 = i4;
            f = 1.0f;
        }
        float f5 = windowState.isRtl() ? 1.0f : 0.0f;
        if (asWallpaperToken.mWallpaperX >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            f5 = asWallpaperToken.mWallpaperX;
        }
        float f6 = asWallpaperToken.mWallpaperXStep >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? asWallpaperToken.mWallpaperXStep : -1.0f;
        int displayWidthOffset = getDisplayWidthOffset(i2, parentFrame, windowState.isRtl());
        int i6 = i2 - displayWidthOffset;
        int i7 = i6 > 0 ? -((int) ((i6 * f5) + 0.5f)) : 0;
        if (asWallpaperToken.mWallpaperDisplayOffsetX != Integer.MIN_VALUE) {
            i7 += asWallpaperToken.mWallpaperDisplayOffsetX;
        } else if (!windowState.isRtl()) {
            i7 -= displayWidthOffset;
        }
        int i8 = (int) (i7 + (i4 * windowState.mHScale));
        if (windowState.mWallpaperX == f5 && windowState.mWallpaperXStep == f6) {
            z2 = false;
        } else {
            windowState.mWallpaperX = f5;
            windowState.mWallpaperXStep = f6;
            z2 = true;
        }
        float f7 = asWallpaperToken.mWallpaperY >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? asWallpaperToken.mWallpaperY : 0.5f;
        float f8 = asWallpaperToken.mWallpaperYStep >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? asWallpaperToken.mWallpaperYStep : -1.0f;
        int i9 = i3 > 0 ? -((int) ((i3 * f7) + 0.5f)) : 0;
        if (asWallpaperToken.mWallpaperDisplayOffsetY != Integer.MIN_VALUE) {
            i9 += asWallpaperToken.mWallpaperDisplayOffsetY;
        }
        int i10 = (int) (i9 + (i5 * windowState.mVScale));
        if (windowState.mWallpaperY != f7 || windowState.mWallpaperYStep != f8) {
            windowState.mWallpaperY = f7;
            windowState.mWallpaperYStep = f8;
            z2 = true;
        }
        if (java.lang.Float.compare(windowState.mWallpaperZoomOut, this.mLastWallpaperZoomOut) == 0) {
            z3 = z2;
        } else {
            windowState.mWallpaperZoomOut = this.mLastWallpaperZoomOut;
            z3 = true;
        }
        boolean wallpaperOffset = windowState.setWallpaperOffset(i8, i10, (windowState.mShouldScaleWallpaper ? zoomOutToScale(windowState.mWallpaperZoomOut) : 1.0f) * f);
        if (z3 && (windowState.mAttrs.privateFlags & 4) != 0) {
            if (z) {
                try {
                    this.mWaitingOnWallpaper = windowState;
                } catch (android.os.RemoteException e) {
                }
            }
            windowState.mClient.dispatchWallpaperOffsets(windowState.mWallpaperX, windowState.mWallpaperY, windowState.mWallpaperXStep, windowState.mWallpaperYStep, windowState.mWallpaperZoomOut, z);
            if (z && this.mWaitingOnWallpaper != null) {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                if (this.mLastWallpaperTimeoutTime + 10000 < uptimeMillis) {
                    try {
                        this.mService.mGlobalLock.wait(150L);
                    } catch (java.lang.InterruptedException e2) {
                    }
                    if (150 + uptimeMillis < android.os.SystemClock.uptimeMillis()) {
                        android.util.Slog.i(TAG, "Timeout waiting for wallpaper to offset: " + windowState);
                        this.mLastWallpaperTimeoutTime = uptimeMillis;
                    }
                }
                this.mWaitingOnWallpaper = null;
            }
        }
        return wallpaperOffset;
    }

    private int getDisplayWidthOffset(int i, android.graphics.Rect rect, boolean z) {
        int width;
        if (!this.mShouldOffsetWallpaperCenter || com.android.window.flags.Flags.multiCrop()) {
            return 0;
        }
        if (this.mLargestDisplaySize == null) {
            this.mLargestDisplaySize = findLargestDisplaySize();
        }
        if (this.mLargestDisplaySize == null || this.mLargestDisplaySize.x == (width = rect.width()) || rect.width() >= rect.height()) {
            return 0;
        }
        int round = java.lang.Math.round(this.mLargestDisplaySize.x * (rect.height() / this.mLargestDisplaySize.y));
        if (z) {
            return round - ((width + round) / 2);
        }
        return java.lang.Math.min(round - width, i) / 2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setWindowWallpaperPosition(com.android.server.wm.WindowState windowState, float f, float f2, float f3, float f4) {
        if (windowState.mWallpaperX != f || windowState.mWallpaperY != f2) {
            windowState.mWallpaperX = f;
            windowState.mWallpaperY = f2;
            windowState.mWallpaperXStep = f3;
            windowState.mWallpaperYStep = f4;
            updateWallpaperOffsetLocked(windowState, !this.mService.mFlags.mWallpaperOffsetAsync);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setWallpaperZoomOut(com.android.server.wm.WindowState windowState, float f) {
        if (java.lang.Float.compare(windowState.mWallpaperZoomOut, f) != 0) {
            windowState.mWallpaperZoomOut = f;
            computeLastWallpaperZoomOut();
            for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
                this.mWallpaperTokens.get(size).updateWallpaperOffset(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setShouldZoomOutWallpaper(com.android.server.wm.WindowState windowState, boolean z) {
        if (z != windowState.mShouldScaleWallpaper) {
            windowState.mShouldScaleWallpaper = z;
            updateWallpaperOffsetLocked(windowState, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setWindowWallpaperDisplayOffset(com.android.server.wm.WindowState windowState, int i, int i2) {
        if (windowState.mWallpaperDisplayOffsetX != i || windowState.mWallpaperDisplayOffsetY != i2) {
            windowState.mWallpaperDisplayOffsetX = i;
            windowState.mWallpaperDisplayOffsetY = i2;
            updateWallpaperOffsetLocked(windowState, !this.mService.mFlags.mWallpaperOffsetAsync);
        }
    }

    void sendWindowWallpaperCommandUnchecked(com.android.server.wm.WindowState windowState, java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) {
        sendWindowWallpaperCommand(str, i, i2, i3, bundle, z);
    }

    private void sendWindowWallpaperCommand(java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) {
        for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
            this.mWallpaperTokens.get(size).sendWindowWallpaperCommand(str, i, i2, i3, bundle, z);
        }
    }

    private void updateWallpaperOffsetLocked(com.android.server.wm.WindowState windowState, boolean z) {
        com.android.server.wm.WindowState windowState2 = this.mWallpaperTarget;
        if (windowState2 == null && windowState.mToken.isVisible() && windowState.mTransitionController.inTransition()) {
            windowState2 = windowState;
        }
        com.android.server.wm.WallpaperWindowToken tokenForTarget = getTokenForTarget(windowState2);
        if (tokenForTarget == null) {
            return;
        }
        if (windowState2.mWallpaperX >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            tokenForTarget.mWallpaperX = windowState2.mWallpaperX;
        } else if (windowState.mWallpaperX >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            tokenForTarget.mWallpaperX = windowState.mWallpaperX;
        }
        if (windowState2.mWallpaperY >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            tokenForTarget.mWallpaperY = windowState2.mWallpaperY;
        } else if (windowState.mWallpaperY >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            tokenForTarget.mWallpaperY = windowState.mWallpaperY;
        }
        if (windowState2.mWallpaperDisplayOffsetX != Integer.MIN_VALUE) {
            tokenForTarget.mWallpaperDisplayOffsetX = windowState2.mWallpaperDisplayOffsetX;
        } else if (windowState.mWallpaperDisplayOffsetX != Integer.MIN_VALUE) {
            tokenForTarget.mWallpaperDisplayOffsetX = windowState.mWallpaperDisplayOffsetX;
        }
        if (windowState2.mWallpaperDisplayOffsetY != Integer.MIN_VALUE) {
            tokenForTarget.mWallpaperDisplayOffsetY = windowState2.mWallpaperDisplayOffsetY;
        } else if (windowState.mWallpaperDisplayOffsetY != Integer.MIN_VALUE) {
            tokenForTarget.mWallpaperDisplayOffsetY = windowState.mWallpaperDisplayOffsetY;
        }
        if (windowState2.mWallpaperXStep >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            tokenForTarget.mWallpaperXStep = windowState2.mWallpaperXStep;
        } else if (windowState.mWallpaperXStep >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            tokenForTarget.mWallpaperXStep = windowState.mWallpaperXStep;
        }
        if (windowState2.mWallpaperYStep >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            tokenForTarget.mWallpaperYStep = windowState2.mWallpaperYStep;
        } else if (windowState.mWallpaperYStep >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            tokenForTarget.mWallpaperYStep = windowState.mWallpaperYStep;
        }
        tokenForTarget.updateWallpaperOffset(z);
    }

    private com.android.server.wm.WallpaperWindowToken getTokenForTarget(com.android.server.wm.WindowState windowState) {
        if (windowState == null) {
            return null;
        }
        com.android.server.wm.WindowState topWallpaper = this.mFindResults.getTopWallpaper(windowState.canShowWhenLocked() && this.mService.isKeyguardLocked());
        if (topWallpaper == null) {
            return null;
        }
        return topWallpaper.mToken.asWallpaperToken();
    }

    void clearLastWallpaperTimeoutTime() {
        this.mLastWallpaperTimeoutTime = 0L;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void wallpaperCommandComplete(android.os.IBinder iBinder) {
        if (this.mWaitingOnWallpaper != null && this.mWaitingOnWallpaper.mClient.asBinder() == iBinder) {
            this.mWaitingOnWallpaper = null;
            this.mService.mGlobalLock.notifyAll();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void wallpaperOffsetsComplete(android.os.IBinder iBinder) {
        if (this.mWaitingOnWallpaper != null && this.mWaitingOnWallpaper.mClient.asBinder() == iBinder) {
            this.mWaitingOnWallpaper = null;
            this.mService.mGlobalLock.notifyAll();
        }
    }

    private void findWallpaperTarget() {
        this.mFindResults.reset();
        if (this.mService.mAtmService.mSupportsFreeformWindowManagement && this.mDisplayContent.getDefaultTaskDisplayArea().isRootTaskVisible(5)) {
            this.mFindResults.setUseTopWallpaperAsTarget(true);
        }
        findWallpapers();
        this.mDisplayContent.forAllWindows(this.mFindWallpaperTargetFunction, true);
        if (this.mFindResults.mNeedsShowWhenLockedWallpaper) {
            this.mFindResults.setUseTopWallpaperAsTarget(true);
        }
        if (this.mFindResults.wallpaperTarget == null && this.mFindResults.useTopWallpaperAsTarget) {
            this.mFindResults.setWallpaperTarget(this.mFindResults.getTopWallpaper(this.mDisplayContent.isKeyguardLocked()));
        }
    }

    private void findWallpapers() {
        for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
            com.android.server.wm.WallpaperWindowToken wallpaperWindowToken = this.mWallpaperTokens.get(size);
            boolean canShowWhenLocked = wallpaperWindowToken.canShowWhenLocked();
            for (int childCount = wallpaperWindowToken.getChildCount() - 1; childCount >= 0; childCount--) {
                com.android.server.wm.WindowState childAt = wallpaperWindowToken.getChildAt(childCount);
                if (childAt.mIsWallpaper) {
                    if (canShowWhenLocked && !this.mFindResults.hasTopShowWhenLockedWallpaper()) {
                        this.mFindResults.setTopShowWhenLockedWallpaper(childAt);
                    } else if (!canShowWhenLocked && !this.mFindResults.hasTopHideWhenLockedWallpaper()) {
                        this.mFindResults.setTopHideWhenLockedWallpaper(childAt);
                    }
                }
            }
        }
    }

    void collectTopWallpapers(com.android.server.wm.Transition transition) {
        if (this.mFindResults.hasTopShowWhenLockedWallpaper()) {
            transition.collect(this.mFindResults.mTopWallpaper.mTopShowWhenLockedWallpaper);
        }
        if (this.mFindResults.hasTopHideWhenLockedWallpaper()) {
            transition.collect(this.mFindResults.mTopWallpaper.mTopHideWhenLockedWallpaper);
        }
    }

    private boolean isFullscreen(android.view.WindowManager.LayoutParams layoutParams) {
        return layoutParams.x == 0 && layoutParams.y == 0 && layoutParams.width == -1 && layoutParams.height == -1;
    }

    private void updateWallpaperWindowsTarget(com.android.server.wm.WallpaperController.FindWallpaperTargetResult findWallpaperTargetResult) {
        com.android.server.wm.WindowState windowState = findWallpaperTargetResult.wallpaperTarget;
        if (this.mWallpaperTarget == windowState || (this.mPrevWallpaperTarget != null && this.mPrevWallpaperTarget == windowState)) {
            if (this.mPrevWallpaperTarget != null && !this.mPrevWallpaperTarget.isAnimatingLw()) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WALLPAPER, -3477087868568520027L, 0, null, null);
                this.mPrevWallpaperTarget = null;
                this.mWallpaperTarget = windowState;
                return;
            }
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WALLPAPER, -3751289048117070874L, 0, null, java.lang.String.valueOf(windowState), java.lang.String.valueOf(this.mWallpaperTarget), java.lang.String.valueOf(android.os.Debug.getCallers(5)));
        this.mPrevWallpaperTarget = null;
        final com.android.server.wm.WindowState windowState2 = this.mWallpaperTarget;
        this.mWallpaperTarget = windowState;
        boolean z = false;
        if (windowState2 == null && windowState != null) {
            updateWallpaperOffsetLocked(this.mWallpaperTarget, false);
        }
        if (windowState == null || windowState2 == null) {
            return;
        }
        boolean isAnimatingLw = windowState2.isAnimatingLw();
        boolean isAnimatingLw2 = windowState.isAnimatingLw();
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WALLPAPER, 5625223922466895079L, 0, null, java.lang.String.valueOf(isAnimatingLw2), java.lang.String.valueOf(isAnimatingLw));
        if (!isAnimatingLw2 || !isAnimatingLw || this.mDisplayContent.getWindow(new java.util.function.Predicate() { // from class: com.android.server.wm.WallpaperController$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$updateWallpaperWindowsTarget$2;
                lambda$updateWallpaperWindowsTarget$2 = com.android.server.wm.WallpaperController.lambda$updateWallpaperWindowsTarget$2(com.android.server.wm.WindowState.this, (com.android.server.wm.WindowState) obj);
                return lambda$updateWallpaperWindowsTarget$2;
            }
        }) == null) {
            return;
        }
        boolean z2 = (windowState.mActivityRecord == null || windowState.mActivityRecord.isVisibleRequested()) ? false : true;
        if (windowState2.mActivityRecord != null && !windowState2.mActivityRecord.isVisibleRequested()) {
            z = true;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WALLPAPER, 7634524672408826188L, 204, null, java.lang.String.valueOf(windowState2), java.lang.Boolean.valueOf(z), java.lang.String.valueOf(windowState), java.lang.Boolean.valueOf(z2));
        this.mPrevWallpaperTarget = windowState2;
        if (z2 && !z) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WALLPAPER, -4345077332231178044L, 0, null, null);
            this.mWallpaperTarget = windowState2;
        } else if (z2 == z && !this.mDisplayContent.mOpeningApps.contains(windowState.mActivityRecord) && (this.mDisplayContent.mOpeningApps.contains(windowState2.mActivityRecord) || this.mDisplayContent.mClosingApps.contains(windowState2.mActivityRecord))) {
            this.mWallpaperTarget = windowState2;
        }
        findWallpaperTargetResult.setWallpaperTarget(windowState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$updateWallpaperWindowsTarget$2(com.android.server.wm.WindowState windowState, com.android.server.wm.WindowState windowState2) {
        return windowState2 == windowState;
    }

    public void updateWallpaperTokens(boolean z) {
        updateWallpaperTokens((this.mWallpaperTarget == null && this.mPrevWallpaperTarget == null) ? false : true, z);
    }

    private void updateWallpaperTokens(boolean z, boolean z2) {
        com.android.server.wm.WindowState topWallpaper = this.mFindResults.getTopWallpaper(z2);
        com.android.server.wm.WallpaperWindowToken asWallpaperToken = topWallpaper == null ? null : topWallpaper.mToken.asWallpaperToken();
        for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
            com.android.server.wm.WallpaperWindowToken wallpaperWindowToken = this.mWallpaperTokens.get(size);
            wallpaperWindowToken.updateWallpaperWindows(z && wallpaperWindowToken == asWallpaperToken);
        }
    }

    void adjustWallpaperWindows() {
        this.mDisplayContent.mWallpaperMayChange = false;
        findWallpaperTarget();
        updateWallpaperWindowsTarget(this.mFindResults);
        com.android.server.wm.WallpaperWindowToken tokenForTarget = getTokenForTarget(this.mWallpaperTarget);
        boolean z = tokenForTarget != null;
        if (z) {
            if (this.mWallpaperTarget.mWallpaperX >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                tokenForTarget.mWallpaperX = this.mWallpaperTarget.mWallpaperX;
                tokenForTarget.mWallpaperXStep = this.mWallpaperTarget.mWallpaperXStep;
            }
            if (this.mWallpaperTarget.mWallpaperY >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                tokenForTarget.mWallpaperY = this.mWallpaperTarget.mWallpaperY;
                tokenForTarget.mWallpaperYStep = this.mWallpaperTarget.mWallpaperYStep;
            }
            if (this.mWallpaperTarget.mWallpaperDisplayOffsetX != Integer.MIN_VALUE) {
                tokenForTarget.mWallpaperDisplayOffsetX = this.mWallpaperTarget.mWallpaperDisplayOffsetX;
            }
            if (this.mWallpaperTarget.mWallpaperDisplayOffsetY != Integer.MIN_VALUE) {
                tokenForTarget.mWallpaperDisplayOffsetY = this.mWallpaperTarget.mWallpaperDisplayOffsetY;
            }
        }
        updateWallpaperTokens(z, this.mDisplayContent.isKeyguardLocked());
        if (z && this.mLastFrozen != this.mFindResults.isWallpaperTargetForLetterbox) {
            this.mLastFrozen = this.mFindResults.isWallpaperTargetForLetterbox;
            sendWindowWallpaperCommand(this.mFindResults.isWallpaperTargetForLetterbox ? "android.wallpaper.freeze" : "android.wallpaper.unfreeze", 0, 0, 0, null, false);
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WALLPAPER, -2504764636812266719L, 0, null, java.lang.String.valueOf(this.mWallpaperTarget), java.lang.String.valueOf(this.mPrevWallpaperTarget));
    }

    boolean processWallpaperDrawPendingTimeout() {
        if (this.mWallpaperDrawState == 1) {
            this.mWallpaperDrawState = 2;
            if (this.mService.getRecentsAnimationController() != null) {
                this.mService.getRecentsAnimationController().startAnimation();
            }
            this.mService.mAtmService.mBackNavigationController.startAnimation();
            return true;
        }
        return false;
    }

    boolean wallpaperTransitionReady() {
        boolean z;
        boolean z2 = true;
        int size = this.mWallpaperTokens.size() - 1;
        while (true) {
            if (size >= 0) {
                if (!this.mWallpaperTokens.get(size).hasVisibleNotDrawnWallpaper()) {
                    size--;
                } else {
                    if (this.mWallpaperDrawState == 2) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (this.mWallpaperDrawState == 0) {
                        this.mWallpaperDrawState = 1;
                        this.mService.mH.removeMessages(39, this);
                        this.mService.mH.sendMessageDelayed(this.mService.mH.obtainMessage(39, this), 500L);
                    }
                    z2 = false;
                }
            } else {
                z = true;
                break;
            }
        }
        if (z2) {
            this.mWallpaperDrawState = 0;
            this.mService.mH.removeMessages(39, this);
        }
        return z;
    }

    void adjustWallpaperWindowsForAppTransitionIfNeeded(android.util.ArraySet<com.android.server.wm.ActivityRecord> arraySet) {
        boolean z = true;
        if ((this.mDisplayContent.pendingLayoutChanges & 4) == 0) {
            int size = arraySet.size() - 1;
            while (true) {
                if (size < 0) {
                    z = false;
                    break;
                } else if (arraySet.valueAt(size).windowsCanBeWallpaperTarget()) {
                    break;
                } else {
                    size--;
                }
            }
        }
        if (z) {
            adjustWallpaperWindows();
        }
    }

    void addWallpaperToken(com.android.server.wm.WallpaperWindowToken wallpaperWindowToken) {
        this.mWallpaperTokens.add(wallpaperWindowToken);
    }

    void removeWallpaperToken(com.android.server.wm.WallpaperWindowToken wallpaperWindowToken) {
        this.mWallpaperTokens.remove(wallpaperWindowToken);
    }

    void onWallpaperTokenReordered() {
        if (this.mWallpaperTokens.size() > 1) {
            this.mWallpaperTokens.sort(null);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean canScreenshotWallpaper() {
        return canScreenshotWallpaper(getTopVisibleWallpaper());
    }

    private boolean canScreenshotWallpaper(com.android.server.wm.WindowState windowState) {
        return this.mService.mPolicy.isScreenOn() && windowState != null;
    }

    android.graphics.Bitmap screenshotWallpaperLocked() {
        com.android.server.wm.WindowState topVisibleWallpaper = getTopVisibleWallpaper();
        if (!canScreenshotWallpaper(topVisibleWallpaper)) {
            return null;
        }
        android.graphics.Rect bounds = topVisibleWallpaper.getBounds();
        bounds.offsetTo(0, 0);
        android.window.ScreenCapture.ScreenshotHardwareBuffer captureLayers = android.window.ScreenCapture.captureLayers(topVisibleWallpaper.getSurfaceControl(), bounds, 1.0f);
        if (captureLayers == null) {
            android.util.Slog.w(TAG, "Failed to screenshot wallpaper");
            return null;
        }
        return android.graphics.Bitmap.wrapHardwareBuffer(captureLayers.getHardwareBuffer(), captureLayers.getColorSpace());
    }

    android.view.SurfaceControl mirrorWallpaperSurface() {
        com.android.server.wm.WindowState topVisibleWallpaper = getTopVisibleWallpaper();
        if (topVisibleWallpaper != null) {
            return android.view.SurfaceControl.mirrorSurface(topVisibleWallpaper.getSurfaceControl());
        }
        return null;
    }

    com.android.server.wm.WindowState getTopVisibleWallpaper() {
        for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
            com.android.server.wm.WallpaperWindowToken wallpaperWindowToken = this.mWallpaperTokens.get(size);
            for (int childCount = wallpaperWindowToken.getChildCount() - 1; childCount >= 0; childCount--) {
                com.android.server.wm.WindowState childAt = wallpaperWindowToken.getChildAt(childCount);
                if (childAt.mWinAnimator.getShown() && childAt.mWinAnimator.mLastAlpha > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
                    return childAt;
                }
            }
        }
        return null;
    }

    void onDisplaySwitchStarted() {
        this.mIsWallpaperNotifiedOnDisplaySwitch = notifyDisplaySwitch(true);
    }

    void onDisplaySwitchFinished() {
        if (this.mIsWallpaperNotifiedOnDisplaySwitch) {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mIsWallpaperNotifiedOnDisplaySwitch = false;
                    notifyDisplaySwitch(false);
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    private boolean notifyDisplaySwitch(boolean z) {
        boolean z2 = false;
        for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
            com.android.server.wm.WallpaperWindowToken wallpaperWindowToken = this.mWallpaperTokens.get(size);
            for (int childCount = wallpaperWindowToken.getChildCount() - 1; childCount >= 0; childCount--) {
                com.android.server.wm.WindowState childAt = wallpaperWindowToken.getChildAt(childCount);
                if (!z || childAt.mWinAnimator.getShown()) {
                    try {
                        childAt.mClient.dispatchWallpaperCommand("android.wallpaper.displayswitch", 0, 0, z ? 1 : 0, (android.os.Bundle) null, false);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.w(TAG, "Failed to dispatch COMMAND_DISPLAY_SWITCH " + e);
                    }
                    z2 = true;
                }
            }
        }
        return z2;
    }

    private void computeLastWallpaperZoomOut() {
        this.mLastWallpaperZoomOut = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mDisplayContent.forAllWindows(this.mComputeMaxZoomOutFunction, true);
    }

    private float zoomOutToScale(float f) {
        return android.util.MathUtils.lerp(this.mMinWallpaperScale, this.mMaxWallpaperScale, 1.0f - f);
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.print(str);
        printWriter.print("displayId=");
        printWriter.println(this.mDisplayContent.getDisplayId());
        printWriter.print(str);
        printWriter.print("mWallpaperTarget=");
        printWriter.println(this.mWallpaperTarget);
        printWriter.print(str);
        printWriter.print("mLastWallpaperZoomOut=");
        printWriter.println(this.mLastWallpaperZoomOut);
        if (this.mPrevWallpaperTarget != null) {
            printWriter.print(str);
            printWriter.print("mPrevWallpaperTarget=");
            printWriter.println(this.mPrevWallpaperTarget);
        }
        for (int size = this.mWallpaperTokens.size() - 1; size >= 0; size--) {
            com.android.server.wm.WallpaperWindowToken wallpaperWindowToken = this.mWallpaperTokens.get(size);
            printWriter.print(str);
            printWriter.println("token " + wallpaperWindowToken + ":");
            printWriter.print(str);
            printWriter.print("  canShowWhenLocked=");
            printWriter.println(wallpaperWindowToken.canShowWhenLocked());
            dumpValue(printWriter, str, "mWallpaperX", wallpaperWindowToken.mWallpaperX);
            dumpValue(printWriter, str, "mWallpaperY", wallpaperWindowToken.mWallpaperY);
            dumpValue(printWriter, str, "mWallpaperXStep", wallpaperWindowToken.mWallpaperXStep);
            dumpValue(printWriter, str, "mWallpaperYStep", wallpaperWindowToken.mWallpaperYStep);
            dumpValue(printWriter, str, "mWallpaperDisplayOffsetX", wallpaperWindowToken.mWallpaperDisplayOffsetX);
            dumpValue(printWriter, str, "mWallpaperDisplayOffsetY", wallpaperWindowToken.mWallpaperDisplayOffsetY);
        }
    }

    private void dumpValue(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, float f) {
        printWriter.print(str);
        printWriter.print("  " + str2 + "=");
        printWriter.println(f >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? java.lang.Float.valueOf(f) : "NA");
    }

    private static final class FindWallpaperTargetResult {
        boolean isWallpaperTargetForLetterbox;
        boolean mNeedsShowWhenLockedWallpaper;
        com.android.server.wm.WallpaperController.FindWallpaperTargetResult.TopWallpaper mTopWallpaper;
        boolean useTopWallpaperAsTarget;
        com.android.server.wm.WindowState wallpaperTarget;

        private FindWallpaperTargetResult() {
            this.mTopWallpaper = new com.android.server.wm.WallpaperController.FindWallpaperTargetResult.TopWallpaper();
            this.useTopWallpaperAsTarget = false;
            this.wallpaperTarget = null;
            this.isWallpaperTargetForLetterbox = false;
        }

        static final class TopWallpaper {
            com.android.server.wm.WindowState mTopHideWhenLockedWallpaper = null;
            com.android.server.wm.WindowState mTopShowWhenLockedWallpaper = null;

            TopWallpaper() {
            }

            void reset() {
                this.mTopHideWhenLockedWallpaper = null;
                this.mTopShowWhenLockedWallpaper = null;
            }
        }

        void setTopHideWhenLockedWallpaper(com.android.server.wm.WindowState windowState) {
            this.mTopWallpaper.mTopHideWhenLockedWallpaper = windowState;
        }

        void setTopShowWhenLockedWallpaper(com.android.server.wm.WindowState windowState) {
            this.mTopWallpaper.mTopShowWhenLockedWallpaper = windowState;
        }

        boolean hasTopHideWhenLockedWallpaper() {
            return this.mTopWallpaper.mTopHideWhenLockedWallpaper != null;
        }

        boolean hasTopShowWhenLockedWallpaper() {
            return this.mTopWallpaper.mTopShowWhenLockedWallpaper != null;
        }

        com.android.server.wm.WindowState getTopWallpaper(boolean z) {
            if (!z && hasTopHideWhenLockedWallpaper()) {
                return this.mTopWallpaper.mTopHideWhenLockedWallpaper;
            }
            return this.mTopWallpaper.mTopShowWhenLockedWallpaper;
        }

        void setWallpaperTarget(com.android.server.wm.WindowState windowState) {
            this.wallpaperTarget = windowState;
        }

        void setUseTopWallpaperAsTarget(boolean z) {
            this.useTopWallpaperAsTarget = z;
        }

        void setIsWallpaperTargetForLetterbox(boolean z) {
            this.isWallpaperTargetForLetterbox = z;
        }

        void reset() {
            this.mTopWallpaper.reset();
            this.mNeedsShowWhenLockedWallpaper = false;
            this.wallpaperTarget = null;
            this.useTopWallpaperAsTarget = false;
            this.isWallpaperTargetForLetterbox = false;
        }
    }
}
