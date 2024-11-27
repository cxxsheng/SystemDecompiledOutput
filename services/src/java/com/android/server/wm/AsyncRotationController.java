package com.android.server.wm;

/* loaded from: classes3.dex */
class AsyncRotationController extends com.android.server.wm.FadeAnimationController implements java.util.function.Consumer<com.android.server.wm.WindowState> {
    private static final boolean DEBUG = false;
    private static final int OP_APP_SWITCH = 1;
    private static final int OP_CHANGE = 2;
    private static final int OP_CHANGE_MAY_SEAMLESS = 3;
    private static final int OP_LEGACY = 0;
    private static final java.lang.String TAG = "AsyncRotation";
    private final boolean mHasScreenRotationAnimation;
    private boolean mHideImmediately;
    private boolean mIsStartTransactionCommitted;
    private boolean mIsStartTransactionPrepared;
    private boolean mIsSyncDrawRequested;
    private com.android.server.wm.WindowToken mNavBarToken;
    private java.lang.Runnable mOnShowRunnable;
    private int mOriginalRotation;
    private com.android.server.wm.SeamlessRotator mRotator;
    private final com.android.server.wm.WindowManagerService mService;
    private final android.util.ArrayMap<com.android.server.wm.WindowToken, com.android.server.wm.AsyncRotationController.Operation> mTargetWindowTokens;
    private java.lang.Runnable mTimeoutRunnable;
    private final int mTransitionOp;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface TransitionOp {
    }

    AsyncRotationController(com.android.server.wm.DisplayContent displayContent) {
        super(displayContent);
        this.mTargetWindowTokens = new android.util.ArrayMap<>();
        this.mService = displayContent.mWmService;
        this.mOriginalRotation = displayContent.getWindowConfiguration().getRotation();
        if (displayContent.mTransitionController.getCollectingTransitionType() == 6) {
            com.android.server.wm.DisplayRotation displayRotation = displayContent.getDisplayRotation();
            com.android.server.wm.WindowState topFullscreenOpaqueWindow = displayContent.getDisplayPolicy().getTopFullscreenOpaqueWindow();
            if (topFullscreenOpaqueWindow != null && topFullscreenOpaqueWindow.mAttrs.rotationAnimation == 3 && topFullscreenOpaqueWindow.getTask() != null && displayRotation.canRotateSeamlessly(this.mOriginalRotation, displayRotation.getRotation())) {
                this.mTransitionOp = 3;
            } else {
                this.mTransitionOp = 2;
            }
        } else if (displayContent.mTransitionController.isShellTransitionsEnabled()) {
            this.mTransitionOp = 1;
        } else {
            this.mTransitionOp = 0;
        }
        this.mHasScreenRotationAnimation = displayContent.getRotationAnimation() != null || this.mTransitionOp == 2;
        if (this.mHasScreenRotationAnimation) {
            this.mHideImmediately = true;
        }
        displayContent.forAllWindows((java.util.function.Consumer<com.android.server.wm.WindowState>) this, true);
        if (this.mTransitionOp == 0) {
            this.mIsStartTransactionCommitted = true;
        } else if (displayContent.mTransitionController.isCollecting(displayContent)) {
            keepAppearanceInPreviousRotation();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0054, code lost:
    
        if (r5.mDisplayContent.mTransitionController.mNavigationBarAttachedToApp == false) goto L31;
     */
    @Override // java.util.function.Consumer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void accept(com.android.server.wm.WindowState windowState) {
        if (!windowState.mHasSurface || !canBeAsync(windowState.mToken)) {
            return;
        }
        if (this.mTransitionOp == 0 && windowState.mForceSeamlesslyRotate) {
            return;
        }
        int i = 1;
        if (windowState.mAttrs.type == 2019) {
            boolean navigationBarCanMove = this.mDisplayContent.getDisplayPolicy().navigationBarCanMove();
            if (this.mTransitionOp == 0) {
                this.mNavBarToken = windowState.mToken;
                if (navigationBarCanMove) {
                    return;
                }
                com.android.server.wm.RecentsAnimationController recentsAnimationController = this.mService.getRecentsAnimationController();
                if (recentsAnimationController != null && recentsAnimationController.isNavigationBarAttachedToApp()) {
                    return;
                }
            } else {
                if (!navigationBarCanMove) {
                    if (this.mTransitionOp != 3) {
                    }
                }
                this.mTargetWindowTokens.put(windowState.mToken, new com.android.server.wm.AsyncRotationController.Operation(i));
                return;
            }
            i = 2;
            this.mTargetWindowTokens.put(windowState.mToken, new com.android.server.wm.AsyncRotationController.Operation(i));
            return;
        }
        if (this.mTransitionOp != 3 && !windowState.mForceSeamlesslyRotate) {
            i = 2;
        }
        this.mTargetWindowTokens.put(windowState.mToken, new com.android.server.wm.AsyncRotationController.Operation(i));
    }

    static boolean canBeAsync(com.android.server.wm.WindowToken windowToken) {
        int i = windowToken.windowType;
        return (i <= 99 || i == 2011 || i == 2013 || i == 2040) ? false : true;
    }

    void keepAppearanceInPreviousRotation() {
        if (this.mIsSyncDrawRequested) {
            return;
        }
        for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
            if (!canDrawBeforeStartTransaction(this.mTargetWindowTokens.valueAt(size))) {
                com.android.server.wm.WindowToken keyAt = this.mTargetWindowTokens.keyAt(size);
                for (int childCount = keyAt.getChildCount() - 1; childCount >= 0; childCount--) {
                    keyAt.getChildAt(childCount).applyWithNextDraw(new java.util.function.Consumer() { // from class: com.android.server.wm.AsyncRotationController$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            com.android.server.wm.AsyncRotationController.lambda$keepAppearanceInPreviousRotation$0((android.view.SurfaceControl.Transaction) obj);
                        }
                    });
                }
            }
        }
        this.mIsSyncDrawRequested = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$keepAppearanceInPreviousRotation$0(android.view.SurfaceControl.Transaction transaction) {
    }

    void updateTargetWindows() {
        if (this.mTransitionOp == 0) {
            return;
        }
        if (!this.mIsStartTransactionCommitted) {
            if ((this.mTimeoutRunnable == null || !this.mIsStartTransactionPrepared) && !this.mDisplayContent.hasTopFixedRotationLaunchingApp() && !this.mDisplayContent.isRotationChanging() && !this.mDisplayContent.inTransition()) {
                android.util.Slog.d(TAG, "Cancel for no change");
                this.mDisplayContent.finishAsyncRotationIfPossible();
                return;
            }
            return;
        }
        for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
            com.android.server.wm.AsyncRotationController.Operation valueAt = this.mTargetWindowTokens.valueAt(size);
            if (!valueAt.mIsCompletionPending && valueAt.mAction != 1) {
                com.android.server.wm.WindowToken keyAt = this.mTargetWindowTokens.keyAt(size);
                int childCount = keyAt.getChildCount();
                int i = 0;
                for (int i2 = childCount - 1; i2 >= 0; i2--) {
                    com.android.server.wm.WindowState childAt = keyAt.getChildAt(i2);
                    if (childAt.isDrawn() || !childAt.mWinAnimator.getShown()) {
                        i++;
                    }
                }
                if (i == childCount) {
                    this.mDisplayContent.finishAsyncRotation(keyAt);
                }
            }
        }
    }

    private void finishOp(com.android.server.wm.WindowToken windowToken) {
        com.android.server.wm.AsyncRotationController.Operation remove = this.mTargetWindowTokens.remove(windowToken);
        if (remove == null) {
            return;
        }
        if (remove.mDrawTransaction != null) {
            windowToken.getSyncTransaction().merge(remove.mDrawTransaction);
            remove.mDrawTransaction = null;
        }
        if (remove.mAction == 3) {
            fadeWindowToken(true, windowToken, 64, new com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.AsyncRotationController$$ExternalSyntheticLambda0
                @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
                public final void onAnimationFinished(int i, com.android.server.wm.AnimationAdapter animationAdapter) {
                    com.android.server.wm.AsyncRotationController.this.lambda$finishOp$1(i, animationAdapter);
                }
            });
        } else if (remove.mAction == 2) {
            fadeWindowToken(true, windowToken, 64);
        } else if (remove.isValidSeamless()) {
            clearTransform(windowToken.getSyncTransaction(), remove.mLeash);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$finishOp$1(int i, com.android.server.wm.AnimationAdapter animationAdapter) {
        this.mDisplayContent.getInsetsStateController().getImeSourceProvider().reportImeDrawnForOrganizer();
    }

    private static void clearTransform(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        transaction.setMatrix(surfaceControl, 1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 1.0f);
        transaction.setPosition(surfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
    }

    void completeAll() {
        for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
            finishOp(this.mTargetWindowTokens.keyAt(size));
        }
        this.mTargetWindowTokens.clear();
        onAllCompleted();
    }

    private void onAllCompleted() {
        if (this.mTimeoutRunnable != null) {
            this.mService.mH.removeCallbacks(this.mTimeoutRunnable);
        }
        if (this.mOnShowRunnable != null) {
            this.mOnShowRunnable.run();
            this.mOnShowRunnable = null;
        }
    }

    boolean completeRotation(com.android.server.wm.WindowToken windowToken) {
        com.android.server.wm.AsyncRotationController.Operation operation;
        if (!this.mIsStartTransactionCommitted) {
            com.android.server.wm.AsyncRotationController.Operation operation2 = this.mTargetWindowTokens.get(windowToken);
            if (operation2 != null) {
                operation2.mIsCompletionPending = true;
            }
            return false;
        }
        if ((this.mTransitionOp == 1 && windowToken.mTransitionController.inTransition() && (operation = this.mTargetWindowTokens.get(windowToken)) != null && operation.mAction == 2) || !isTargetToken(windowToken)) {
            return false;
        }
        if (this.mHasScreenRotationAnimation || this.mTransitionOp != 0) {
            finishOp(windowToken);
            if (this.mTargetWindowTokens.isEmpty()) {
                onAllCompleted();
                return true;
            }
        }
        return false;
    }

    void start() {
        for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowToken keyAt = this.mTargetWindowTokens.keyAt(size);
            com.android.server.wm.AsyncRotationController.Operation valueAt = this.mTargetWindowTokens.valueAt(size);
            if (valueAt.mAction == 2 || valueAt.mAction == 3) {
                fadeWindowToken(false, keyAt, 64);
                valueAt.mLeash = keyAt.getAnimationLeash();
            } else if (valueAt.mAction == 1) {
                valueAt.mLeash = keyAt.mSurfaceControl;
            }
        }
        if (this.mHasScreenRotationAnimation) {
            scheduleTimeout();
        }
    }

    void updateRotation() {
        int rotation;
        if (this.mRotator == null || this.mOriginalRotation == (rotation = this.mDisplayContent.getWindowConfiguration().getRotation())) {
            return;
        }
        android.util.Slog.d(TAG, "Update original rotation " + rotation);
        this.mOriginalRotation = rotation;
        this.mDisplayContent.forAllWindows(new java.util.function.Consumer() { // from class: com.android.server.wm.AsyncRotationController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.wm.AsyncRotationController.this.lambda$updateRotation$2((com.android.server.wm.WindowState) obj);
            }
        }, true);
        this.mRotator = null;
        this.mIsStartTransactionCommitted = false;
        this.mIsSyncDrawRequested = false;
        keepAppearanceInPreviousRotation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRotation$2(com.android.server.wm.WindowState windowState) {
        if (windowState.mForceSeamlesslyRotate && windowState.mHasSurface && !this.mTargetWindowTokens.containsKey(windowState.mToken)) {
            com.android.server.wm.AsyncRotationController.Operation operation = new com.android.server.wm.AsyncRotationController.Operation(1);
            operation.mLeash = windowState.mToken.mSurfaceControl;
            this.mTargetWindowTokens.put(windowState.mToken, operation);
        }
    }

    private void scheduleTimeout() {
        if (this.mTimeoutRunnable == null) {
            this.mTimeoutRunnable = new java.lang.Runnable() { // from class: com.android.server.wm.AsyncRotationController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.wm.AsyncRotationController.this.lambda$scheduleTimeout$3();
                }
            };
        }
        this.mService.mH.postDelayed(this.mTimeoutRunnable, 2000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleTimeout$3() {
        java.lang.String str;
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                if (!this.mIsStartTransactionCommitted) {
                    if (!this.mIsStartTransactionPrepared) {
                        str = "setupStartTransaction is not called";
                    } else {
                        str = "start transaction is not committed";
                    }
                } else {
                    str = "unfinished windows " + this.mTargetWindowTokens;
                }
                android.util.Slog.i(TAG, "Async rotation timeout: " + str);
                if (!this.mIsStartTransactionCommitted && this.mIsStartTransactionPrepared) {
                    com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                    return;
                }
                this.mDisplayContent.finishAsyncRotationIfPossible();
                this.mService.mWindowPlacerLocked.performSurfacePlacement();
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
    }

    void hideImeImmediately() {
        if (this.mDisplayContent.mInputMethodWindow == null) {
            return;
        }
        com.android.server.wm.WindowToken windowToken = this.mDisplayContent.mInputMethodWindow.mToken;
        if (isTargetToken(windowToken)) {
            return;
        }
        hideImmediately(windowToken, 3);
    }

    private void hideImmediately(com.android.server.wm.WindowToken windowToken, int i) {
        boolean z = this.mHideImmediately;
        this.mHideImmediately = true;
        com.android.server.wm.AsyncRotationController.Operation operation = new com.android.server.wm.AsyncRotationController.Operation(i);
        this.mTargetWindowTokens.put(windowToken, operation);
        fadeWindowToken(false, windowToken, 64);
        operation.mLeash = windowToken.getAnimationLeash();
        this.mHideImmediately = z;
    }

    boolean isAsync(com.android.server.wm.WindowState windowState) {
        return windowState.mToken == this.mNavBarToken || (windowState.mForceSeamlesslyRotate && this.mTransitionOp == 0) || isTargetToken(windowState.mToken);
    }

    boolean isTargetToken(com.android.server.wm.WindowToken windowToken) {
        return this.mTargetWindowTokens.containsKey(windowToken);
    }

    boolean hasFadeOperation(com.android.server.wm.WindowToken windowToken) {
        com.android.server.wm.AsyncRotationController.Operation operation = this.mTargetWindowTokens.get(windowToken);
        return operation != null && operation.mAction == 2;
    }

    boolean hasSeamlessOperation(com.android.server.wm.WindowToken windowToken) {
        com.android.server.wm.AsyncRotationController.Operation operation = this.mTargetWindowTokens.get(windowToken);
        return operation != null && operation.mAction == 1;
    }

    boolean shouldFreezeInsetsPosition(com.android.server.wm.WindowState windowState) {
        return this.mTransitionOp != 0 && (this.mTransitionOp == 1 || com.android.server.wm.TransitionController.SYNC_METHOD == 1) && !this.mIsStartTransactionCommitted && canBeAsync(windowState.mToken) && isTargetToken(windowState.mToken);
    }

    android.view.SurfaceControl.Transaction getDrawTransaction(com.android.server.wm.WindowToken windowToken) {
        com.android.server.wm.AsyncRotationController.Operation operation;
        if (this.mTransitionOp == 0 || (operation = this.mTargetWindowTokens.get(windowToken)) == null) {
            return null;
        }
        if (operation.mDrawTransaction == null) {
            operation.mDrawTransaction = new android.view.SurfaceControl.Transaction();
        }
        return operation.mDrawTransaction;
    }

    void setOnShowRunnable(java.lang.Runnable runnable) {
        this.mOnShowRunnable = runnable;
    }

    void setupStartTransaction(android.view.SurfaceControl.Transaction transaction) {
        if (this.mIsStartTransactionCommitted) {
            return;
        }
        for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
            com.android.server.wm.AsyncRotationController.Operation valueAt = this.mTargetWindowTokens.valueAt(size);
            android.view.SurfaceControl surfaceControl = valueAt.mLeash;
            if (surfaceControl != null && surfaceControl.isValid()) {
                if (this.mHasScreenRotationAnimation && valueAt.mAction == 2) {
                    transaction.setAlpha(surfaceControl, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                } else {
                    if (this.mRotator == null) {
                        this.mRotator = new com.android.server.wm.SeamlessRotator(this.mOriginalRotation, this.mDisplayContent.getWindowConfiguration().getRotation(), this.mDisplayContent.getDisplayInfo(), false);
                    }
                    this.mRotator.applyTransform(transaction, surfaceControl);
                }
            }
        }
        transaction.addTransactionCommittedListener(new android.os.HandlerExecutor(this.mService.mH), new android.view.SurfaceControl.TransactionCommittedListener() { // from class: com.android.server.wm.AsyncRotationController$$ExternalSyntheticLambda4
            @Override // android.view.SurfaceControl.TransactionCommittedListener
            public final void onTransactionCommitted() {
                com.android.server.wm.AsyncRotationController.this.lambda$setupStartTransaction$4();
            }
        });
        this.mIsStartTransactionPrepared = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setupStartTransaction$4() {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                this.mIsStartTransactionCommitted = true;
                for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
                    if (this.mTargetWindowTokens.valueAt(size).mIsCompletionPending) {
                        this.mDisplayContent.finishAsyncRotation(this.mTargetWindowTokens.keyAt(size));
                    }
                }
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    void onTransactionCommitTimeout(android.view.SurfaceControl.Transaction transaction) {
        if (this.mIsStartTransactionCommitted) {
            return;
        }
        for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
            com.android.server.wm.AsyncRotationController.Operation valueAt = this.mTargetWindowTokens.valueAt(size);
            valueAt.mIsCompletionPending = true;
            if (valueAt.isValidSeamless()) {
                android.util.Slog.d(TAG, "Transaction timeout. Clear transform for " + this.mTargetWindowTokens.keyAt(size).getTopChild());
                clearTransform(transaction, valueAt.mLeash);
            }
        }
    }

    void onTransitionFinished() {
        if (this.mTransitionOp == 2) {
            if (this.mTargetWindowTokens.isEmpty()) {
                this.mDisplayContent.finishAsyncRotationIfPossible();
                return;
            }
            return;
        }
        for (int size = this.mTargetWindowTokens.size() - 1; size >= 0; size--) {
            com.android.server.wm.WindowToken keyAt = this.mTargetWindowTokens.keyAt(size);
            if (keyAt.isVisible()) {
                int childCount = keyAt.getChildCount() - 1;
                while (true) {
                    if (childCount < 0) {
                        break;
                    }
                    if (keyAt.getChildAt(childCount).isDrawFinishedLw()) {
                        this.mDisplayContent.finishAsyncRotation(keyAt);
                        break;
                    }
                    childCount--;
                }
            } else {
                this.mDisplayContent.finishAsyncRotation(keyAt);
            }
        }
        if (!this.mTargetWindowTokens.isEmpty()) {
            scheduleTimeout();
        }
    }

    boolean handleFinishDrawing(com.android.server.wm.WindowState windowState, android.view.SurfaceControl.Transaction transaction) {
        if (this.mTransitionOp == 0) {
            return false;
        }
        com.android.server.wm.AsyncRotationController.Operation operation = this.mTargetWindowTokens.get(windowState.mToken);
        if (operation == null) {
            if (this.mTransitionOp == 1 && !this.mIsStartTransactionCommitted && canBeAsync(windowState.mToken)) {
                hideImmediately(windowState.mToken, 2);
            }
            return false;
        }
        if (transaction == null || !this.mIsSyncDrawRequested || canDrawBeforeStartTransaction(operation)) {
            this.mDisplayContent.finishAsyncRotation(windowState.mToken);
            return false;
        }
        if (operation.mDrawTransaction == null) {
            if (windowState.isClientLocal()) {
                operation.mDrawTransaction = this.mService.mTransactionFactory.get();
                operation.mDrawTransaction.merge(transaction);
            } else {
                operation.mDrawTransaction = transaction;
            }
        } else {
            operation.mDrawTransaction.merge(transaction);
        }
        this.mDisplayContent.finishAsyncRotation(windowState.mToken);
        return true;
    }

    @Override // com.android.server.wm.FadeAnimationController
    public android.view.animation.Animation getFadeInAnimation() {
        if (this.mHasScreenRotationAnimation) {
            return android.view.animation.AnimationUtils.loadAnimation(this.mContext, android.R.anim.screen_rotate_0_enter);
        }
        return super.getFadeInAnimation();
    }

    @Override // com.android.server.wm.FadeAnimationController
    public android.view.animation.Animation getFadeOutAnimation() {
        if (this.mHideImmediately) {
            float f = this.mTransitionOp == 2 ? 1.0f : com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
            return new android.view.animation.AlphaAnimation(f, f);
        }
        return super.getFadeOutAnimation();
    }

    private boolean canDrawBeforeStartTransaction(com.android.server.wm.AsyncRotationController.Operation operation) {
        return operation.mAction != 1;
    }

    void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        printWriter.println(str + "AsyncRotationController");
        java.lang.String str2 = str + "  ";
        printWriter.println(str2 + "mTransitionOp=" + this.mTransitionOp);
        printWriter.println(str2 + "mIsStartTransactionCommitted=" + this.mIsStartTransactionCommitted);
        printWriter.println(str2 + "mIsSyncDrawRequested=" + this.mIsSyncDrawRequested);
        printWriter.println(str2 + "mOriginalRotation=" + this.mOriginalRotation);
        printWriter.println(str2 + "mTargetWindowTokens=" + this.mTargetWindowTokens);
    }

    private static class Operation {
        static final int ACTION_FADE = 2;
        static final int ACTION_SEAMLESS = 1;
        static final int ACTION_TOGGLE_IME = 3;
        final int mAction;
        android.view.SurfaceControl.Transaction mDrawTransaction;
        boolean mIsCompletionPending;
        android.view.SurfaceControl mLeash;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface Action {
        }

        Operation(int i) {
            this.mAction = i;
        }

        boolean isValidSeamless() {
            return this.mAction == 1 && this.mLeash != null && this.mLeash.isValid();
        }

        public java.lang.String toString() {
            return "Operation{a=" + this.mAction + " pending=" + this.mIsCompletionPending + '}';
        }
    }
}
