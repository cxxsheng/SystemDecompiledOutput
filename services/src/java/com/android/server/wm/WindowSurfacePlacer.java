package com.android.server.wm;

/* loaded from: classes3.dex */
class WindowSurfacePlacer {
    static final int SET_UPDATE_ROTATION = 1;
    static final int SET_WALLPAPER_ACTION_PENDING = 2;
    private static final java.lang.String TAG = "WindowManager";
    private int mDeferredRequests;
    private int mLayoutRepeatCount;
    private final com.android.server.wm.WindowManagerService mService;
    private boolean mTraversalScheduled;
    private boolean mInLayout = false;
    private int mDeferDepth = 0;
    private final com.android.server.wm.WindowSurfacePlacer.Traverser mPerformSurfacePlacement = new com.android.server.wm.WindowSurfacePlacer.Traverser();

    private class Traverser implements java.lang.Runnable {
        private Traverser() {
        }

        @Override // java.lang.Runnable
        public void run() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.WindowSurfacePlacer.this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.WindowSurfacePlacer.this.performSurfacePlacement();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    WindowSurfacePlacer(com.android.server.wm.WindowManagerService windowManagerService) {
        this.mService = windowManagerService;
    }

    void deferLayout() {
        this.mDeferDepth++;
    }

    void continueLayout(boolean z) {
        this.mDeferDepth--;
        if (this.mDeferDepth > 0) {
            return;
        }
        if (z || this.mDeferredRequests > 0) {
            performSurfacePlacement();
            this.mDeferredRequests = 0;
        }
    }

    boolean isLayoutDeferred() {
        return this.mDeferDepth > 0;
    }

    void performSurfacePlacementIfScheduled() {
        if (this.mTraversalScheduled) {
            performSurfacePlacement();
        }
    }

    final void performSurfacePlacement() {
        performSurfacePlacement(false);
    }

    final void performSurfacePlacement(boolean z) {
        if (this.mDeferDepth > 0 && !z) {
            this.mDeferredRequests++;
            return;
        }
        int i = 6;
        do {
            this.mTraversalScheduled = false;
            performSurfacePlacementLoop();
            this.mService.mAnimationHandler.removeCallbacks(this.mPerformSurfacePlacement);
            i--;
            if (!this.mTraversalScheduled) {
                break;
            }
        } while (i > 0);
        this.mService.mRoot.mWallpaperActionPending = false;
    }

    private void performSurfacePlacementLoop() {
        if (this.mInLayout) {
            android.util.Slog.w(TAG, "performLayoutAndPlaceSurfacesLocked called while in layout. Callers=" + android.os.Debug.getCallers(3));
            return;
        }
        if (this.mService.getDefaultDisplayContentLocked().mWaitingForConfig || !this.mService.mDisplayReady) {
            return;
        }
        this.mInLayout = true;
        if (!this.mService.mForceRemoves.isEmpty()) {
            while (!this.mService.mForceRemoves.isEmpty()) {
                com.android.server.wm.WindowState remove = this.mService.mForceRemoves.remove(0);
                android.util.Slog.i(TAG, "Force removing: " + remove);
                remove.removeImmediately();
            }
            android.util.Slog.w(TAG, "Due to memory failure, waiting a bit for next layout");
            java.lang.Object obj = new java.lang.Object();
            synchronized (obj) {
                try {
                    obj.wait(250L);
                } catch (java.lang.InterruptedException e) {
                }
            }
        }
        try {
            this.mService.mRoot.performSurfacePlacement();
            this.mInLayout = false;
            if (this.mService.mRoot.isLayoutNeeded()) {
                int i = this.mLayoutRepeatCount + 1;
                this.mLayoutRepeatCount = i;
                if (i < 6) {
                    requestTraversal();
                } else {
                    android.util.Slog.e(TAG, "Performed 6 layouts in a row. Skipping");
                    this.mLayoutRepeatCount = 0;
                }
            } else {
                this.mLayoutRepeatCount = 0;
            }
            if (this.mService.mWindowsChanged && !this.mService.mWindowChangeListeners.isEmpty()) {
                this.mService.mH.removeMessages(19);
                this.mService.mH.sendEmptyMessage(19);
            }
        } catch (java.lang.RuntimeException e2) {
            this.mInLayout = false;
            android.util.Slog.wtf(TAG, "Unhandled exception while laying out windows", e2);
        }
    }

    void debugLayoutRepeats(java.lang.String str, int i) {
        if (this.mLayoutRepeatCount >= 4) {
            android.util.Slog.v(TAG, "Layouts looping: " + str + ", mPendingLayoutChanges = 0x" + java.lang.Integer.toHexString(i));
        }
    }

    boolean isInLayout() {
        return this.mInLayout;
    }

    boolean isTraversalScheduled() {
        return this.mTraversalScheduled;
    }

    void requestTraversal() {
        if (this.mTraversalScheduled) {
            return;
        }
        this.mTraversalScheduled = true;
        if (this.mDeferDepth > 0) {
            this.mDeferredRequests++;
        } else {
            this.mService.mAnimationHandler.post(this.mPerformSurfacePlacement);
        }
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "mTraversalScheduled=" + this.mTraversalScheduled);
    }
}
