package com.android.server.wm;

/* loaded from: classes3.dex */
class WindowSurfaceController {
    static final java.lang.String TAG = "WindowManager";
    final com.android.server.wm.WindowStateAnimator mAnimator;
    private final com.android.server.wm.WindowManagerService mService;
    android.view.SurfaceControl mSurfaceControl;
    private boolean mSurfaceShown = false;
    private final com.android.server.wm.Session mWindowSession;
    private final int mWindowType;
    private final java.lang.String title;

    WindowSurfaceController(java.lang.String str, int i, int i2, com.android.server.wm.WindowStateAnimator windowStateAnimator, int i3) {
        this.mAnimator = windowStateAnimator;
        this.title = str;
        this.mService = windowStateAnimator.mService;
        com.android.server.wm.WindowState windowState = windowStateAnimator.mWin;
        this.mWindowType = i3;
        this.mWindowSession = windowState.mSession;
        android.os.Trace.traceBegin(32L, "new SurfaceControl");
        this.mSurfaceControl = windowState.makeSurface().setParent(windowState.getSurfaceControl()).setName(str).setFormat(i).setFlags(i2).setMetadata(2, i3).setMetadata(1, this.mWindowSession.mUid).setMetadata(6, this.mWindowSession.mPid).setCallsite("WindowSurfaceController").setBLASTLayer().build();
        android.os.Trace.traceEnd(32L);
    }

    void hide(android.view.SurfaceControl.Transaction transaction, java.lang.String str) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, -2055407587764455051L, 0, null, java.lang.String.valueOf(str), java.lang.String.valueOf(this.title));
        if (this.mSurfaceShown) {
            hideSurface(transaction);
        }
    }

    private void hideSurface(android.view.SurfaceControl.Transaction transaction) {
        if (this.mSurfaceControl == null) {
            return;
        }
        setShown(false);
        try {
            transaction.hide(this.mSurfaceControl);
            if (this.mAnimator.mIsWallpaper) {
                com.android.server.wm.DisplayContent displayContent = this.mAnimator.mWin.getDisplayContent();
                android.util.EventLog.writeEvent(com.android.server.wm.EventLogTags.WM_WALLPAPER_SURFACE, java.lang.Integer.valueOf(displayContent.mDisplayId), 0, java.lang.String.valueOf(displayContent.mWallpaperController.getWallpaperTarget()));
            }
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.w(TAG, "Exception hiding surface in " + this);
        }
    }

    void destroy(android.view.SurfaceControl.Transaction transaction) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_SURFACE_ALLOC, -5854683348829455340L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(android.os.Debug.getCallers(8)));
        try {
            try {
                if (this.mSurfaceControl != null) {
                    if (this.mAnimator.mIsWallpaper && !this.mAnimator.mWin.mWindowRemovalAllowed && !this.mAnimator.mWin.mRemoveOnExit) {
                        android.util.Slog.e(TAG, "Unexpected removing wallpaper surface of " + this.mAnimator.mWin + " by " + android.os.Debug.getCallers(8));
                    }
                    transaction.remove(this.mSurfaceControl);
                }
            } catch (java.lang.RuntimeException e) {
                android.util.Slog.w(TAG, "Error destroying surface in: " + this, e);
            }
            setShown(false);
            this.mSurfaceControl = null;
        } catch (java.lang.Throwable th) {
            setShown(false);
            this.mSurfaceControl = null;
            throw th;
        }
    }

    boolean prepareToShowInTransaction(android.view.SurfaceControl.Transaction transaction, float f) {
        if (this.mSurfaceControl == null) {
            return false;
        }
        transaction.setAlpha(this.mSurfaceControl, f);
        return true;
    }

    void setOpaque(boolean z) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, 7813672046338784579L, 3, null, java.lang.Boolean.valueOf(z), java.lang.String.valueOf(this.title));
        if (this.mSurfaceControl == null) {
            return;
        }
        this.mAnimator.mWin.getPendingTransaction().setOpaque(this.mSurfaceControl, z);
        this.mService.scheduleAnimationLocked();
    }

    void setColorSpaceAgnostic(android.view.SurfaceControl.Transaction transaction, boolean z) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, -8864150640874799238L, 3, null, java.lang.Boolean.valueOf(z), java.lang.String.valueOf(this.title));
        if (this.mSurfaceControl == null) {
            return;
        }
        transaction.setColorSpaceAgnostic(this.mSurfaceControl, z);
    }

    void showRobustly(android.view.SurfaceControl.Transaction transaction) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.i(com.android.internal.protolog.ProtoLogGroup.WM_SHOW_TRANSACTIONS, -8398940245851553814L, 0, null, java.lang.String.valueOf(this.title));
        if (this.mSurfaceShown) {
            return;
        }
        setShown(true);
        transaction.show(this.mSurfaceControl);
        if (this.mAnimator.mIsWallpaper) {
            com.android.server.wm.DisplayContent displayContent = this.mAnimator.mWin.getDisplayContent();
            android.util.EventLog.writeEvent(com.android.server.wm.EventLogTags.WM_WALLPAPER_SURFACE, java.lang.Integer.valueOf(displayContent.mDisplayId), 1, java.lang.String.valueOf(displayContent.mWallpaperController.getWallpaperTarget()));
        }
    }

    boolean clearWindowContentFrameStats() {
        if (this.mSurfaceControl == null) {
            return false;
        }
        return this.mSurfaceControl.clearContentFrameStats();
    }

    boolean getWindowContentFrameStats(android.view.WindowContentFrameStats windowContentFrameStats) {
        if (this.mSurfaceControl == null) {
            return false;
        }
        return this.mSurfaceControl.getContentFrameStats(windowContentFrameStats);
    }

    boolean hasSurface() {
        return this.mSurfaceControl != null;
    }

    void getSurfaceControl(android.view.SurfaceControl surfaceControl) {
        surfaceControl.copyFrom(this.mSurfaceControl, "WindowSurfaceController.getSurfaceControl");
    }

    boolean getShown() {
        return this.mSurfaceShown;
    }

    void setShown(boolean z) {
        this.mSurfaceShown = z;
        this.mService.updateNonSystemOverlayWindowsVisibilityIfNeeded(this.mAnimator.mWin, z);
        this.mAnimator.mWin.onSurfaceShownChanged(z);
        if (this.mWindowSession != null) {
            this.mWindowSession.onWindowSurfaceVisibilityChanged(this, this.mSurfaceShown, this.mWindowType);
        }
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1133871366145L, this.mSurfaceShown);
        protoOutputStream.end(start);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        if (z) {
            printWriter.print(str);
            printWriter.print("mSurface=");
            printWriter.println(this.mSurfaceControl);
        }
        printWriter.print(str);
        printWriter.print("Surface: shown=");
        printWriter.print(this.mSurfaceShown);
    }

    public java.lang.String toString() {
        return this.mSurfaceControl.toString();
    }
}
