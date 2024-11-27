package android.view;

/* loaded from: classes4.dex */
public final class ImeInsetsSourceConsumer extends android.view.InsetsSourceConsumer {
    private boolean mHasPendingRequest;
    private boolean mIsRequestedVisibleAwaitingControl;

    public ImeInsetsSourceConsumer(int i, android.view.InsetsState insetsState, java.util.function.Supplier<android.view.SurfaceControl.Transaction> supplier, android.view.InsetsController insetsController) {
        super(i, android.view.WindowInsets.Type.ime(), insetsState, supplier, insetsController);
    }

    @Override // android.view.InsetsSourceConsumer
    public boolean onAnimationStateChanged(boolean z) {
        if (!z) {
            com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("ImeInsetsSourceConsumer#onAnimationFinished", this.mController.getHost().getInputMethodManager(), null);
        }
        boolean onAnimationStateChanged = super.onAnimationStateChanged(z);
        if (!isShowRequested()) {
            this.mIsRequestedVisibleAwaitingControl = false;
            if (!z && !this.mHasPendingRequest) {
                notifyHidden(android.view.inputmethod.ImeTracker.forLogging().onStart(2, 5, 51, this.mController.getHost().isHandlingPointerEvent()));
                removeSurface();
            }
        }
        this.mHasPendingRequest = false;
        return onAnimationStateChanged;
    }

    @Override // android.view.InsetsSourceConsumer
    public void onWindowFocusGained(boolean z) {
        super.onWindowFocusGained(z);
        getImm().registerImeConsumer(this);
        if ((this.mController.getRequestedVisibleTypes() & getType()) != 0 && getControl() == null) {
            this.mIsRequestedVisibleAwaitingControl = true;
        }
    }

    @Override // android.view.InsetsSourceConsumer
    public void onWindowFocusLost() {
        super.onWindowFocusLost();
        getImm().unregisterImeConsumer(this);
        this.mIsRequestedVisibleAwaitingControl = false;
    }

    @Override // android.view.InsetsSourceConsumer
    public boolean applyLocalVisibilityOverride() {
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("ImeInsetsSourceConsumer#applyLocalVisibilityOverride", this.mController.getHost().getInputMethodManager(), null);
        return super.applyLocalVisibilityOverride();
    }

    @Override // android.view.InsetsSourceConsumer
    public int requestShow(boolean z, android.view.inputmethod.ImeTracker.Token token) {
        if (z) {
            com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("ImeInsetsSourceConsumer#requestShow", this.mController.getHost().getInputMethodManager(), null);
        }
        onShowRequested();
        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 36);
        if (getControl() == null) {
            this.mIsRequestedVisibleAwaitingControl = true;
        }
        if (!z) {
            if (!this.mState.isSourceOrDefaultVisible(getId(), getType()) || getControl() == null) {
                return getImm().requestImeShow(this.mController.getHost().getWindowToken(), token) ? 1 : 2;
            }
            return 0;
        }
        return 0;
    }

    @Override // android.view.InsetsSourceConsumer
    void requestHide(boolean z, android.view.inputmethod.ImeTracker.Token token) {
        if (!z) {
            if (getControl() != null) {
                token = android.view.inputmethod.ImeTracker.forLogging().onStart(2, 5, 52, this.mController.getHost().isHandlingPointerEvent());
            }
            notifyHidden(token);
        }
        if (this.mAnimationState == 1) {
            this.mHasPendingRequest = true;
        }
    }

    private void notifyHidden(android.view.inputmethod.ImeTracker.Token token) {
        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 38);
        getImm().notifyImeHidden(this.mController.getHost().getWindowToken(), token);
        this.mIsRequestedVisibleAwaitingControl = false;
        android.os.Trace.asyncTraceEnd(8L, "IC.hideRequestFromApi", 0);
    }

    @Override // android.view.InsetsSourceConsumer
    public void removeSurface() {
        android.os.IBinder windowToken = this.mController.getHost().getWindowToken();
        if (windowToken != null) {
            getImm().removeImeSurface(windowToken);
        }
    }

    @Override // android.view.InsetsSourceConsumer
    public boolean setControl(android.view.InsetsSourceControl insetsSourceControl, int[] iArr, int[] iArr2) {
        com.android.internal.inputmethod.ImeTracing.getInstance().triggerClientDump("ImeInsetsSourceConsumer#setControl", this.mController.getHost().getInputMethodManager(), null);
        if (!super.setControl(insetsSourceControl, iArr, iArr2)) {
            return false;
        }
        if (insetsSourceControl == null && !this.mIsRequestedVisibleAwaitingControl) {
            this.mController.setRequestedVisibleTypes(0, getType());
            removeSurface();
        }
        if (insetsSourceControl != null) {
            this.mIsRequestedVisibleAwaitingControl = false;
            return true;
        }
        return true;
    }

    @Override // android.view.InsetsSourceConsumer
    protected boolean isRequestedVisibleAwaitingControl() {
        return super.isRequestedVisibleAwaitingControl() || this.mIsRequestedVisibleAwaitingControl;
    }

    @Override // android.view.InsetsSourceConsumer
    public void onPerceptible(boolean z) {
        super.onPerceptible(z);
        android.os.IBinder windowToken = this.mController.getHost().getWindowToken();
        if (windowToken != null) {
            getImm().reportPerceptible(windowToken, z);
        }
    }

    @Override // android.view.InsetsSourceConsumer
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        super.dumpDebug(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1133871366147L, this.mIsRequestedVisibleAwaitingControl);
        protoOutputStream.write(1133871366150L, this.mHasPendingRequest);
        protoOutputStream.end(start);
    }

    public void onShowRequested() {
        if (this.mAnimationState == 2) {
            this.mHasPendingRequest = true;
        }
    }

    private android.view.inputmethod.InputMethodManager getImm() {
        return this.mController.getHost().getInputMethodManager();
    }
}
