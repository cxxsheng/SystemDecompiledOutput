package android.view;

/* loaded from: classes4.dex */
public class ViewRootInsetsControllerHost implements android.view.InsetsController.Host {
    private final java.lang.String TAG = "VRInsetsControllerHost";
    private android.view.SyncRtSurfaceTransactionApplier mApplier;
    private final android.view.ViewRootImpl mViewRoot;

    public ViewRootInsetsControllerHost(android.view.ViewRootImpl viewRootImpl) {
        this.mViewRoot = viewRootImpl;
    }

    @Override // android.view.InsetsController.Host
    public android.os.Handler getHandler() {
        return this.mViewRoot.mHandler;
    }

    @Override // android.view.InsetsController.Host
    public void notifyInsetsChanged() {
        this.mViewRoot.notifyInsetsChanged();
    }

    @Override // android.view.InsetsController.Host
    public void addOnPreDrawRunnable(final java.lang.Runnable runnable) {
        if (this.mViewRoot.mView == null) {
            return;
        }
        this.mViewRoot.mView.getViewTreeObserver().addOnPreDrawListener(new android.view.ViewTreeObserver.OnPreDrawListener() { // from class: android.view.ViewRootInsetsControllerHost.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                android.view.ViewRootInsetsControllerHost.this.mViewRoot.mView.getViewTreeObserver().removeOnPreDrawListener(this);
                runnable.run();
                return true;
            }
        });
        this.mViewRoot.mView.invalidate();
    }

    @Override // android.view.InsetsController.Host
    public void dispatchWindowInsetsAnimationPrepare(android.view.WindowInsetsAnimation windowInsetsAnimation) {
        if (this.mViewRoot.mView == null) {
            return;
        }
        this.mViewRoot.mView.dispatchWindowInsetsAnimationPrepare(windowInsetsAnimation);
    }

    @Override // android.view.InsetsController.Host
    public android.view.WindowInsetsAnimation.Bounds dispatchWindowInsetsAnimationStart(android.view.WindowInsetsAnimation windowInsetsAnimation, android.view.WindowInsetsAnimation.Bounds bounds) {
        if (this.mViewRoot.mView == null) {
            return null;
        }
        return this.mViewRoot.mView.dispatchWindowInsetsAnimationStart(windowInsetsAnimation, bounds);
    }

    @Override // android.view.InsetsController.Host
    public android.view.WindowInsets dispatchWindowInsetsAnimationProgress(android.view.WindowInsets windowInsets, java.util.List<android.view.WindowInsetsAnimation> list) {
        if (this.mViewRoot.mView == null) {
            return null;
        }
        return this.mViewRoot.mView.dispatchWindowInsetsAnimationProgress(windowInsets, list);
    }

    @Override // android.view.InsetsController.Host
    public void dispatchWindowInsetsAnimationEnd(android.view.WindowInsetsAnimation windowInsetsAnimation) {
        if (this.mViewRoot.mView == null) {
            return;
        }
        this.mViewRoot.mView.dispatchWindowInsetsAnimationEnd(windowInsetsAnimation);
    }

    @Override // android.view.InsetsController.Host
    public void applySurfaceParams(android.view.SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr) {
        if (this.mViewRoot.mView == null) {
            throw new java.lang.IllegalStateException("View of the ViewRootImpl is not initiated.");
        }
        if (this.mApplier == null) {
            this.mApplier = new android.view.SyncRtSurfaceTransactionApplier(this.mViewRoot.mView);
        }
        if (this.mViewRoot.mView.isHardwareAccelerated() && isVisibleToUser()) {
            this.mApplier.scheduleApply(surfaceParamsArr);
            return;
        }
        android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        this.mApplier.applyParams(transaction, surfaceParamsArr);
        transaction.apply();
    }

    @Override // android.view.InsetsController.Host
    public void postInsetsAnimationCallback(java.lang.Runnable runnable) {
        this.mViewRoot.mChoreographer.postCallback(2, runnable, null);
    }

    @Override // android.view.InsetsController.Host
    public void updateCompatSysUiVisibility(int i, int i2, int i3) {
        this.mViewRoot.updateCompatSysUiVisibility(i, i2, i3);
    }

    @Override // android.view.InsetsController.Host
    public void updateRequestedVisibleTypes(int i) {
        try {
            if (this.mViewRoot.mAdded) {
                this.mViewRoot.mWindowSession.updateRequestedVisibleTypes(this.mViewRoot.mWindow, i);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e("VRInsetsControllerHost", "Failed to call insetsModified", e);
        }
    }

    @Override // android.view.InsetsController.Host
    public boolean hasAnimationCallbacks() {
        if (this.mViewRoot.mView == null) {
            return false;
        }
        return this.mViewRoot.mView.hasWindowInsetsAnimationCallback();
    }

    @Override // android.view.InsetsController.Host
    public void setSystemBarsAppearance(int i, int i2) {
        this.mViewRoot.mWindowAttributes.privateFlags |= 67108864;
        android.view.InsetsFlags insetsFlags = this.mViewRoot.mWindowAttributes.insetsFlags;
        int i3 = (i & i2) | (insetsFlags.appearance & (~i2));
        if (insetsFlags.appearance != i3) {
            insetsFlags.appearance = i3;
            this.mViewRoot.mWindowAttributesChanged = true;
            this.mViewRoot.scheduleTraversals();
        }
    }

    @Override // android.view.InsetsController.Host
    public int getSystemBarsAppearance() {
        return this.mViewRoot.mWindowAttributes.insetsFlags.appearance;
    }

    @Override // android.view.InsetsController.Host
    public boolean isSystemBarsAppearanceControlled() {
        return (this.mViewRoot.mWindowAttributes.privateFlags & 67108864) != 0;
    }

    @Override // android.view.InsetsController.Host
    public void setSystemBarsBehavior(int i) {
        this.mViewRoot.mWindowAttributes.privateFlags |= 134217728;
        if (this.mViewRoot.mWindowAttributes.insetsFlags.behavior != i) {
            this.mViewRoot.mWindowAttributes.insetsFlags.behavior = i;
            this.mViewRoot.mWindowAttributesChanged = true;
            this.mViewRoot.scheduleTraversals();
        }
    }

    @Override // android.view.InsetsController.Host
    public int getSystemBarsBehavior() {
        return this.mViewRoot.mWindowAttributes.insetsFlags.behavior;
    }

    @Override // android.view.InsetsController.Host
    public boolean isSystemBarsBehaviorControlled() {
        return (this.mViewRoot.mWindowAttributes.privateFlags & 134217728) != 0;
    }

    @Override // android.view.InsetsController.Host
    public void releaseSurfaceControlFromRt(final android.view.SurfaceControl surfaceControl) {
        if (this.mViewRoot.mView != null && this.mViewRoot.mView.isHardwareAccelerated()) {
            this.mViewRoot.registerRtFrameCallback(new android.graphics.HardwareRenderer.FrameDrawingCallback() { // from class: android.view.ViewRootInsetsControllerHost$$ExternalSyntheticLambda0
                @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
                public final void onFrameDraw(long j) {
                    android.view.SurfaceControl.this.release();
                }
            });
            this.mViewRoot.mView.invalidate();
        } else {
            surfaceControl.release();
        }
    }

    @Override // android.view.InsetsController.Host
    public android.view.inputmethod.InputMethodManager getInputMethodManager() {
        return (android.view.inputmethod.InputMethodManager) this.mViewRoot.mContext.getSystemService(android.view.inputmethod.InputMethodManager.class);
    }

    @Override // android.view.InsetsController.Host
    public java.lang.String getRootViewTitle() {
        if (this.mViewRoot == null) {
            return null;
        }
        return this.mViewRoot.getTitle().toString();
    }

    @Override // android.view.InsetsController.Host
    public android.content.Context getRootViewContext() {
        if (this.mViewRoot != null) {
            return this.mViewRoot.mContext;
        }
        return null;
    }

    @Override // android.view.InsetsController.Host
    public int dipToPx(int i) {
        if (this.mViewRoot != null) {
            return this.mViewRoot.dipToPx(i);
        }
        return 0;
    }

    @Override // android.view.InsetsController.Host
    public android.os.IBinder getWindowToken() {
        android.view.View view;
        if (this.mViewRoot == null || (view = this.mViewRoot.getView()) == null) {
            return null;
        }
        return view.getWindowToken();
    }

    @Override // android.view.InsetsController.Host
    public android.content.res.CompatibilityInfo.Translator getTranslator() {
        if (this.mViewRoot != null) {
            return this.mViewRoot.mTranslator;
        }
        return null;
    }

    @Override // android.view.InsetsController.Host
    public void notifyAnimationRunningStateChanged(boolean z) {
        if (this.mViewRoot != null) {
            this.mViewRoot.notifyInsetsAnimationRunningStateChanged(z);
        }
    }

    @Override // android.view.InsetsController.Host
    public boolean isHandlingPointerEvent() {
        return this.mViewRoot != null && this.mViewRoot.isHandlingPointerEvent();
    }

    private boolean isVisibleToUser() {
        return this.mViewRoot.getHostVisibility() == 0;
    }
}
