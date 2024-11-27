package com.android.server.wm;

/* loaded from: classes3.dex */
class WallpaperWindowToken extends com.android.server.wm.WindowToken {
    private static final java.lang.String TAG = "WindowManager";
    private android.util.SparseArray<android.graphics.Rect> mCropHints;
    private boolean mShowWhenLocked;
    int mWallpaperDisplayOffsetX;
    int mWallpaperDisplayOffsetY;
    float mWallpaperX;
    float mWallpaperXStep;
    float mWallpaperY;
    float mWallpaperYStep;

    WallpaperWindowToken(com.android.server.wm.WindowManagerService windowManagerService, android.os.IBinder iBinder, boolean z, com.android.server.wm.DisplayContent displayContent, boolean z2) {
        this(windowManagerService, iBinder, z, displayContent, z2, null);
    }

    WallpaperWindowToken(com.android.server.wm.WindowManagerService windowManagerService, android.os.IBinder iBinder, boolean z, com.android.server.wm.DisplayContent displayContent, boolean z2, @android.annotation.Nullable android.os.Bundle bundle) {
        super(windowManagerService, iBinder, 2013, z, displayContent, z2, false, false, bundle);
        this.mShowWhenLocked = false;
        this.mWallpaperX = -1.0f;
        this.mWallpaperY = -1.0f;
        this.mWallpaperXStep = -1.0f;
        this.mWallpaperYStep = -1.0f;
        this.mWallpaperDisplayOffsetX = Integer.MIN_VALUE;
        this.mWallpaperDisplayOffsetY = Integer.MIN_VALUE;
        this.mCropHints = new android.util.SparseArray<>();
        displayContent.mWallpaperController.addWallpaperToken(this);
        setWindowingMode(1);
    }

    @Override // com.android.server.wm.WindowContainer
    com.android.server.wm.WallpaperWindowToken asWallpaperToken() {
        return this;
    }

    @Override // com.android.server.wm.WindowToken
    void setExiting(boolean z) {
        super.setExiting(z);
        this.mDisplayContent.mWallpaperController.removeWallpaperToken(this);
    }

    void setShowWhenLocked(boolean z) {
        if (z == this.mShowWhenLocked) {
            return;
        }
        this.mShowWhenLocked = z;
        getParent().positionChildAt(z ? Integer.MIN_VALUE : Integer.MAX_VALUE, this, false);
        this.mDisplayContent.mWallpaperController.onWallpaperTokenReordered();
    }

    boolean canShowWhenLocked() {
        return this.mShowWhenLocked;
    }

    void setCropHints(android.util.SparseArray<android.graphics.Rect> sparseArray) {
        this.mCropHints = sparseArray.clone();
    }

    android.util.SparseArray<android.graphics.Rect> getCropHints() {
        return this.mCropHints;
    }

    void sendWindowWallpaperCommand(java.lang.String str, int i, int i2, int i3, android.os.Bundle bundle, boolean z) {
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            try {
                ((com.android.server.wm.WindowState) this.mChildren.get(size)).mClient.dispatchWallpaperCommand(str, i, i2, i3, bundle, z);
                z = false;
            } catch (android.os.RemoteException e) {
            }
        }
    }

    void updateWallpaperOffset(boolean z) {
        com.android.server.wm.WallpaperController wallpaperController = this.mDisplayContent.mWallpaperController;
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            if (wallpaperController.updateWallpaperOffset((com.android.server.wm.WindowState) this.mChildren.get(size), z && !this.mWmService.mFlags.mWallpaperOffsetAsync)) {
                z = false;
            }
        }
    }

    void updateWallpaperWindows(boolean z) {
        if (this.mVisibleRequested != z) {
            com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_WALLPAPER, -7936547457136708587L, 12, null, java.lang.String.valueOf(this.token), java.lang.Boolean.valueOf(z));
            setVisibility(z);
        }
        com.android.server.wm.WindowState wallpaperTarget = this.mDisplayContent.mWallpaperController.getWallpaperTarget();
        if (z && wallpaperTarget != null) {
            com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mWmService.getRecentsAnimationController();
            if (recentsAnimationController != null && recentsAnimationController.isAnimatingTask(wallpaperTarget.getTask())) {
                recentsAnimationController.linkFixedRotationTransformIfNeeded(this);
            } else if ((wallpaperTarget.mActivityRecord == null || wallpaperTarget.mActivityRecord.isVisibleRequested()) && wallpaperTarget.mToken.hasFixedRotationTransform()) {
                linkFixedRotationTransform(wallpaperTarget.mToken);
            }
        }
        if (this.mTransitionController.inTransition(this)) {
            return;
        }
        setVisible(z);
    }

    private void setVisible(boolean z) {
        boolean isClientVisible = isClientVisible();
        setClientVisible(z);
        if (z && !isClientVisible) {
            for (int size = this.mChildren.size() - 1; size >= 0; size--) {
                ((com.android.server.wm.WindowState) this.mChildren.get(size)).requestUpdateWallpaperIfNeeded();
            }
        }
    }

    void setVisibility(boolean z) {
        if (this.mVisibleRequested != z) {
            this.mTransitionController.collect(this);
            setVisibleRequested(z);
        }
        if (!z && (this.mTransitionController.inTransition() || getDisplayContent().mAppTransition.isRunning())) {
            return;
        }
        commitVisibility(z);
    }

    void commitVisibility(boolean z) {
        if (z == isVisible()) {
            return;
        }
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_APP_TRANSITIONS, 7214407534407465113L, 60, null, java.lang.String.valueOf(this), java.lang.Boolean.valueOf(isVisible()), java.lang.Boolean.valueOf(this.mVisibleRequested));
        setVisibleRequested(z);
        setVisible(z);
    }

    boolean hasVisibleNotDrawnWallpaper() {
        if (!isVisible()) {
            return false;
        }
        for (int size = this.mChildren.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowState windowState = (com.android.server.wm.WindowState) this.mChildren.get(size);
            if (!windowState.isDrawn() && windowState.isVisible()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    void forAllWallpaperWindows(java.util.function.Consumer<com.android.server.wm.WallpaperWindowToken> consumer) {
        consumer.accept(this);
    }

    @Override // com.android.server.wm.WindowContainer
    boolean fillsParent() {
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean showWallpaper() {
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    protected boolean setVisibleRequested(boolean z) {
        if (!super.setVisibleRequested(z)) {
            return false;
        }
        setInsetsFrozen(!z);
        return true;
    }

    @Override // com.android.server.wm.WindowContainer
    protected boolean onChildVisibleRequestedChanged(@android.annotation.Nullable com.android.server.wm.WindowContainer windowContainer) {
        return false;
    }

    @Override // com.android.server.wm.WindowContainer
    boolean isVisible() {
        return isClientVisible();
    }

    @Override // com.android.server.wm.WindowToken
    public java.lang.String toString() {
        if (this.stringName == null) {
            this.stringName = "WallpaperWindowToken{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " token=" + this.token + '}';
        }
        return this.stringName;
    }
}
