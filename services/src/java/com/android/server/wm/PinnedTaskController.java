package com.android.server.wm;

/* loaded from: classes3.dex */
class PinnedTaskController {
    private static final int DEFER_ORIENTATION_CHANGE_TIMEOUT_MS = 1000;
    private static final java.lang.String TAG = "WindowManager";
    private boolean mDeferOrientationChanging;
    private android.graphics.Rect mDestRotatedBounds;
    private final com.android.server.wm.DisplayContent mDisplayContent;
    private boolean mFreezingTaskConfig;
    private int mImeHeight;
    private boolean mIsImeShowing;
    private float mMaxAspectRatio;
    private float mMinAspectRatio;
    private android.view.IPinnedTaskListener mPinnedTaskListener;
    private android.window.PictureInPictureSurfaceTransaction mPipTransaction;
    private final com.android.server.wm.WindowManagerService mService;
    private final com.android.server.wm.PinnedTaskController.PinnedTaskListenerDeathHandler mPinnedTaskListenerDeathHandler = new com.android.server.wm.PinnedTaskController.PinnedTaskListenerDeathHandler();
    private final java.lang.Runnable mDeferOrientationTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.PinnedTaskController$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            com.android.server.wm.PinnedTaskController.this.lambda$new$0();
        }
    };

    private class PinnedTaskListenerDeathHandler implements android.os.IBinder.DeathRecipient {
        private PinnedTaskListenerDeathHandler() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = com.android.server.wm.PinnedTaskController.this.mService.mGlobalLock;
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    com.android.server.wm.PinnedTaskController.this.mPinnedTaskListener = null;
                    com.android.server.wm.PinnedTaskController.this.mFreezingTaskConfig = false;
                    com.android.server.wm.PinnedTaskController.this.mDeferOrientationTimeoutRunnable.run();
                } catch (java.lang.Throwable th) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
        }
    }

    PinnedTaskController(com.android.server.wm.WindowManagerService windowManagerService, com.android.server.wm.DisplayContent displayContent) {
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        reloadResources();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mDeferOrientationChanging) {
                    continueOrientationChange();
                    this.mService.mWindowPlacerLocked.requestTraversal();
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void onPostDisplayConfigurationChanged() {
        reloadResources();
        this.mFreezingTaskConfig = false;
    }

    private void reloadResources() {
        android.content.res.Resources resources = this.mService.mContext.getResources();
        this.mMinAspectRatio = resources.getFloat(android.R.dimen.config_letterboxVerticalPositionMultiplier);
        this.mMaxAspectRatio = resources.getFloat(android.R.dimen.config_letterboxThinLetterboxWidthDp);
    }

    void registerPinnedTaskListener(android.view.IPinnedTaskListener iPinnedTaskListener) {
        try {
            iPinnedTaskListener.asBinder().linkToDeath(this.mPinnedTaskListenerDeathHandler, 0);
            this.mPinnedTaskListener = iPinnedTaskListener;
            notifyImeVisibilityChanged(this.mIsImeShowing, this.mImeHeight);
            notifyMovementBoundsChanged(false);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Failed to register pinned task listener", e);
        }
    }

    public boolean isValidPictureInPictureAspectRatio(float f) {
        return java.lang.Float.compare(this.mMinAspectRatio, f) <= 0 && java.lang.Float.compare(f, this.mMaxAspectRatio) <= 0;
    }

    public boolean isValidExpandedPictureInPictureAspectRatio(float f) {
        return java.lang.Float.compare(this.mMinAspectRatio, f) > 0 || java.lang.Float.compare(f, this.mMaxAspectRatio) > 0;
    }

    void deferOrientationChangeForEnteringPipFromFullScreenIfNeeded() {
        int rotationForActivityInDifferentOrientation;
        com.android.server.wm.ActivityRecord activity = this.mDisplayContent.getActivity(new java.util.function.Predicate() { // from class: com.android.server.wm.PinnedTaskController$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$deferOrientationChangeForEnteringPipFromFullScreenIfNeeded$1;
                lambda$deferOrientationChangeForEnteringPipFromFullScreenIfNeeded$1 = com.android.server.wm.PinnedTaskController.lambda$deferOrientationChangeForEnteringPipFromFullScreenIfNeeded$1((com.android.server.wm.ActivityRecord) obj);
                return lambda$deferOrientationChangeForEnteringPipFromFullScreenIfNeeded$1;
            }
        });
        if (activity == null || activity.hasFixedRotationTransform() || (rotationForActivityInDifferentOrientation = this.mDisplayContent.rotationForActivityInDifferentOrientation(activity)) == -1) {
            return;
        }
        this.mDisplayContent.setFixedRotationLaunchingApp(activity, rotationForActivityInDifferentOrientation);
        this.mDeferOrientationChanging = true;
        this.mService.mH.removeCallbacks(this.mDeferOrientationTimeoutRunnable);
        this.mService.mH.postDelayed(this.mDeferOrientationTimeoutRunnable, (int) (java.lang.Math.max(1.0f, this.mService.getCurrentAnimatorScale()) * 1000.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$deferOrientationChangeForEnteringPipFromFullScreenIfNeeded$1(com.android.server.wm.ActivityRecord activityRecord) {
        return activityRecord.providesOrientation() && !activityRecord.getTask().inMultiWindowMode();
    }

    boolean shouldDeferOrientationChange() {
        return this.mDeferOrientationChanging;
    }

    void setEnterPipBounds(android.graphics.Rect rect) {
        if (!this.mDeferOrientationChanging) {
            return;
        }
        this.mFreezingTaskConfig = true;
        this.mDestRotatedBounds = new android.graphics.Rect(rect);
        if (!this.mDisplayContent.mTransitionController.isShellTransitionsEnabled()) {
            continueOrientationChange();
        }
    }

    void setEnterPipTransaction(android.window.PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction) {
        this.mFreezingTaskConfig = true;
        this.mPipTransaction = pictureInPictureSurfaceTransaction;
    }

    private void continueOrientationChange() {
        this.mDeferOrientationChanging = false;
        this.mService.mH.removeCallbacks(this.mDeferOrientationTimeoutRunnable);
        com.android.server.wm.WindowContainer lastOrientationSource = this.mDisplayContent.getLastOrientationSource();
        if (lastOrientationSource != null && !lastOrientationSource.isAppTransitioning()) {
            this.mDisplayContent.continueUpdateOrientationForDiffOrienLaunchingApp();
        }
    }

    void startSeamlessRotationIfNeeded(android.view.SurfaceControl.Transaction transaction, int i, int i2) {
        com.android.server.wm.TaskDisplayArea defaultTaskDisplayArea;
        com.android.server.wm.Task rootPinnedTask;
        android.graphics.Rect rect = this.mDestRotatedBounds;
        android.window.PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction = this.mPipTransaction;
        boolean z = pictureInPictureSurfaceTransaction == null || pictureInPictureSurfaceTransaction.mPosition == null;
        if ((rect == null && z) || (rootPinnedTask = (defaultTaskDisplayArea = this.mDisplayContent.getDefaultTaskDisplayArea()).getRootPinnedTask()) == null) {
            return;
        }
        android.graphics.Rect rect2 = null;
        this.mDestRotatedBounds = null;
        this.mPipTransaction = null;
        android.graphics.Rect bounds = defaultTaskDisplayArea.getBounds();
        if (!z) {
            float f = pictureInPictureSurfaceTransaction.mPosition.x;
            float f2 = pictureInPictureSurfaceTransaction.mPosition.y;
            android.graphics.Matrix matrix = pictureInPictureSurfaceTransaction.getMatrix();
            if (pictureInPictureSurfaceTransaction.mRotation == 90.0f) {
                f = pictureInPictureSurfaceTransaction.mPosition.y;
                f2 = bounds.right - pictureInPictureSurfaceTransaction.mPosition.x;
                matrix.postRotate(-90.0f);
            } else if (pictureInPictureSurfaceTransaction.mRotation == -90.0f) {
                f = bounds.bottom - pictureInPictureSurfaceTransaction.mPosition.y;
                f2 = pictureInPictureSurfaceTransaction.mPosition.x;
                matrix.postRotate(90.0f);
            }
            matrix.postTranslate(f, f2);
            android.view.SurfaceControl surfaceControl = rootPinnedTask.getSurfaceControl();
            transaction.setMatrix(surfaceControl, matrix, new float[9]);
            if (pictureInPictureSurfaceTransaction.hasCornerRadiusSet()) {
                transaction.setCornerRadius(surfaceControl, pictureInPictureSurfaceTransaction.mCornerRadius);
            }
            android.util.Slog.i(TAG, "Seamless rotation PiP tx=" + pictureInPictureSurfaceTransaction + " pos=" + f + "," + f2);
            return;
        }
        android.app.PictureInPictureParams pictureInPictureParams = rootPinnedTask.getPictureInPictureParams();
        if (pictureInPictureParams != null && pictureInPictureParams.hasSourceBoundsHint()) {
            rect2 = pictureInPictureParams.getSourceRectHint();
        }
        android.util.Slog.i(TAG, "Seamless rotation PiP bounds=" + rect + " hintRect=" + rect2);
        int deltaRotation = android.util.RotationUtils.deltaRotation(i, i2);
        if (rect2 != null && deltaRotation == 3 && rootPinnedTask.getDisplayCutoutInsets() != null) {
            android.graphics.Rect rect3 = android.util.RotationUtils.rotateInsets(android.graphics.Insets.of(rootPinnedTask.getDisplayCutoutInsets()), android.util.RotationUtils.deltaRotation(i2, i)).toRect();
            rect2.offset(rect3.left, rect3.top);
        }
        if (rect2 == null || !bounds.contains(rect2)) {
            rect2 = bounds;
        }
        int width = rect2.width();
        int height = rect2.height();
        float width2 = width <= height ? rect.width() / width : rect.height() / height;
        int i3 = (int) (((rect2.left - bounds.left) * width2) + 0.5f);
        int i4 = (int) (((rect2.top - bounds.top) * width2) + 0.5f);
        android.graphics.Matrix matrix2 = new android.graphics.Matrix();
        matrix2.setScale(width2, width2);
        matrix2.postTranslate(rect.left - i3, rect.top - i4);
        transaction.setMatrix(rootPinnedTask.getSurfaceControl(), matrix2, new float[9]);
    }

    boolean isFreezingTaskConfig(com.android.server.wm.Task task) {
        return this.mFreezingTaskConfig && task == this.mDisplayContent.getDefaultTaskDisplayArea().getRootPinnedTask();
    }

    void onCancelFixedRotationTransform() {
        this.mFreezingTaskConfig = false;
        this.mDeferOrientationChanging = false;
        this.mDestRotatedBounds = null;
        this.mPipTransaction = null;
    }

    void onActivityHidden(android.content.ComponentName componentName) {
        if (this.mPinnedTaskListener == null) {
            return;
        }
        try {
            this.mPinnedTaskListener.onActivityHidden(componentName);
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Error delivering reset reentry fraction event.", e);
        }
    }

    void setAdjustedForIme(boolean z, int i) {
        boolean z2 = z && i > 0;
        if (!z2) {
            i = 0;
        }
        if (z2 == this.mIsImeShowing && i == this.mImeHeight) {
            return;
        }
        this.mIsImeShowing = z2;
        this.mImeHeight = i;
        notifyImeVisibilityChanged(z2, i);
        notifyMovementBoundsChanged(true);
    }

    private void notifyImeVisibilityChanged(boolean z, int i) {
        if (this.mPinnedTaskListener != null) {
            try {
                this.mPinnedTaskListener.onImeVisibilityChanged(z, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Error delivering bounds changed event.", e);
            }
        }
    }

    private void notifyMovementBoundsChanged(boolean z) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (this.mPinnedTaskListener == null) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                try {
                    this.mPinnedTaskListener.onMovementBoundsChanged(z);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.e(TAG, "Error delivering actions changed event.", e);
                }
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.println(str + "PinnedTaskController");
        if (this.mDeferOrientationChanging) {
            printWriter.println(str + "  mDeferOrientationChanging=true");
        }
        if (this.mFreezingTaskConfig) {
            printWriter.println(str + "  mFreezingTaskConfig=true");
        }
        if (this.mDestRotatedBounds != null) {
            printWriter.println(str + "  mPendingBounds=" + this.mDestRotatedBounds);
        }
        if (this.mPipTransaction != null) {
            printWriter.println(str + "  mPipTransaction=" + this.mPipTransaction);
        }
        printWriter.println(str + "  mIsImeShowing=" + this.mIsImeShowing);
        printWriter.println(str + "  mImeHeight=" + this.mImeHeight);
        printWriter.println(str + "  mMinAspectRatio=" + this.mMinAspectRatio);
        printWriter.println(str + "  mMaxAspectRatio=" + this.mMaxAspectRatio);
    }
}
