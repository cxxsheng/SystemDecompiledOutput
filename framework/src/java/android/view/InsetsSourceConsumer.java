package android.view;

/* loaded from: classes4.dex */
public class InsetsSourceConsumer {
    protected static final int ANIMATION_STATE_HIDE = 2;
    protected static final int ANIMATION_STATE_NONE = 0;
    protected static final int ANIMATION_STATE_SHOW = 1;
    private static final java.lang.String TAG = "InsetsSourceConsumer";
    protected int mAnimationState = 0;
    protected final android.view.InsetsController mController;
    private boolean mHasViewFocusWhenWindowFocusGain;
    private boolean mHasWindowFocus;
    private int mId;
    private android.graphics.Rect mPendingFrame;
    private android.graphics.Rect mPendingVisibleFrame;
    private android.view.InsetsSourceControl mSourceControl;
    protected final android.view.InsetsState mState;
    private final java.util.function.Supplier<android.view.SurfaceControl.Transaction> mTransactionSupplier;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ShowResult {
        public static final int IME_SHOW_DELAYED = 1;
        public static final int IME_SHOW_FAILED = 2;
        public static final int SHOW_IMMEDIATELY = 0;
    }

    public InsetsSourceConsumer(int i, int i2, android.view.InsetsState insetsState, java.util.function.Supplier<android.view.SurfaceControl.Transaction> supplier, android.view.InsetsController insetsController) {
        this.mId = i;
        this.mType = i2;
        this.mState = insetsState;
        this.mTransactionSupplier = supplier;
        this.mController = insetsController;
    }

    public boolean setControl(android.view.InsetsSourceControl insetsSourceControl, int[] iArr, int[] iArr2) {
        boolean z = false;
        if (java.util.Objects.equals(this.mSourceControl, insetsSourceControl)) {
            if (this.mSourceControl != null && this.mSourceControl != insetsSourceControl) {
                this.mSourceControl.release(new android.view.InsetsController$$ExternalSyntheticLambda7());
                this.mSourceControl = insetsSourceControl;
            }
            return false;
        }
        android.view.InsetsSourceControl insetsSourceControl2 = this.mSourceControl;
        this.mSourceControl = insetsSourceControl;
        if (this.mSourceControl == null) {
            this.mController.notifyControlRevoked(this);
            android.view.InsetsSource peekSource = this.mState.peekSource(this.mId);
            android.view.InsetsSource peekSource2 = this.mController.getLastDispatchedState().peekSource(this.mId);
            boolean z2 = peekSource != null && peekSource.isVisible();
            if (peekSource2 != null && peekSource2.isVisible()) {
                z = true;
            }
            if (peekSource != null) {
                peekSource.setVisible(z);
            }
            if (z2 != z) {
                this.mController.notifyVisibilityChanged();
            }
        } else {
            boolean isRequestedVisibleAwaitingControl = isRequestedVisibleAwaitingControl();
            android.view.SurfaceControl leash = insetsSourceControl2 != null ? insetsSourceControl2.getLeash() : null;
            android.view.SurfaceControl leash2 = insetsSourceControl.getLeash();
            if (leash2 != null && ((leash == null || !leash2.isSameSurface(leash)) && isRequestedVisibleAwaitingControl != insetsSourceControl.isInitiallyVisible())) {
                if (isRequestedVisibleAwaitingControl) {
                    iArr[0] = iArr[0] | this.mType;
                } else {
                    iArr2[0] = iArr2[0] | this.mType;
                }
            } else {
                if (applyLocalVisibilityOverride()) {
                    this.mController.notifyVisibilityChanged();
                }
                if (this.mController.getAnimationType(this.mType) == -1) {
                    applyRequestedVisibilityToControl();
                }
                if (!isRequestedVisibleAwaitingControl && insetsSourceControl2 == null) {
                    removeSurface();
                }
            }
        }
        if (insetsSourceControl2 != null) {
            insetsSourceControl2.release(new android.view.InsetsController$$ExternalSyntheticLambda7());
        }
        return true;
    }

    public android.view.InsetsSourceControl getControl() {
        return this.mSourceControl;
    }

    protected boolean isRequestedVisibleAwaitingControl() {
        return (this.mController.getRequestedVisibleTypes() & this.mType) != 0;
    }

    int getId() {
        return this.mId;
    }

    void setId(int i) {
        this.mId = i;
    }

    int getType() {
        return this.mType;
    }

    public boolean onAnimationStateChanged(boolean z) {
        boolean z2;
        boolean z3;
        int i = 1;
        if (!z && this.mPendingFrame != null) {
            android.view.InsetsSource peekSource = this.mState.peekSource(this.mId);
            if (peekSource == null) {
                z2 = false;
            } else {
                peekSource.setFrame(this.mPendingFrame);
                peekSource.setVisibleFrame(this.mPendingVisibleFrame);
                z2 = true;
            }
            this.mPendingFrame = null;
            this.mPendingVisibleFrame = null;
        } else {
            z2 = false;
        }
        boolean isShowRequested = isShowRequested();
        if (z || !isShowRequested) {
            z3 = this.mAnimationState == 1;
        } else {
            z3 = this.mAnimationState == 2;
        }
        if (z) {
            if (!isShowRequested) {
                i = 2;
            }
        } else {
            i = 0;
        }
        this.mAnimationState = i;
        if (!z3) {
            return z2 | applyLocalVisibilityOverride();
        }
        return z2;
    }

    protected boolean isShowRequested() {
        return (this.mController.getRequestedVisibleTypes() & getType()) != 0;
    }

    public void onWindowFocusGained(boolean z) {
        this.mHasWindowFocus = true;
        this.mHasViewFocusWhenWindowFocusGain = z;
    }

    public void onWindowFocusLost() {
        this.mHasWindowFocus = false;
    }

    boolean hasViewFocusWhenWindowFocusGain() {
        return this.mHasViewFocusWhenWindowFocusGain;
    }

    public boolean applyLocalVisibilityOverride() {
        android.view.InsetsSource peekSource = this.mState.peekSource(this.mId);
        if (peekSource == null) {
            return false;
        }
        boolean z = (this.mController.getRequestedVisibleTypes() & this.mType) != 0;
        if (this.mSourceControl == null || peekSource.isVisible() == z) {
            return false;
        }
        peekSource.setVisible(z);
        return true;
    }

    public int requestShow(boolean z, android.view.inputmethod.ImeTracker.Token token) {
        return 0;
    }

    void requestHide(boolean z, android.view.inputmethod.ImeTracker.Token token) {
    }

    public void onPerceptible(boolean z) {
    }

    public void removeSurface() {
    }

    public void updateSource(android.view.InsetsSource insetsSource, int i) {
        android.view.InsetsSource peekSource = this.mState.peekSource(this.mId);
        android.graphics.Rect rect = null;
        if (peekSource == null || i == -1 || peekSource.getFrame().equals(insetsSource.getFrame())) {
            this.mPendingFrame = null;
            this.mPendingVisibleFrame = null;
            this.mState.addSource(insetsSource);
            return;
        }
        android.view.InsetsSource insetsSource2 = new android.view.InsetsSource(insetsSource);
        this.mPendingFrame = new android.graphics.Rect(insetsSource2.getFrame());
        if (insetsSource2.getVisibleFrame() != null) {
            rect = new android.graphics.Rect(insetsSource2.getVisibleFrame());
        }
        this.mPendingVisibleFrame = rect;
        insetsSource2.setFrame(peekSource.getFrame());
        insetsSource2.setVisibleFrame(peekSource.getVisibleFrame());
        this.mState.addSource(insetsSource2);
    }

    private void applyRequestedVisibilityToControl() {
        if (this.mSourceControl == null || this.mSourceControl.getLeash() == null) {
            return;
        }
        boolean z = (this.mController.getRequestedVisibleTypes() & this.mType) != 0;
        android.view.SurfaceControl.Transaction transaction = this.mTransactionSupplier.get();
        try {
            if (z) {
                transaction.show(this.mSourceControl.getLeash());
            } else {
                transaction.hide(this.mSourceControl.getLeash());
            }
            transaction.setAlpha(this.mSourceControl.getLeash(), z ? 1.0f : 0.0f);
            transaction.apply();
            if (transaction != null) {
                transaction.close();
            }
            onPerceptible(z);
        } catch (java.lang.Throwable th) {
            if (transaction != null) {
                try {
                    transaction.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1133871366146L, this.mHasWindowFocus);
        protoOutputStream.write(1133871366147L, isShowRequested());
        if (this.mSourceControl != null) {
            this.mSourceControl.dumpDebug(protoOutputStream, 1146756268036L);
        }
        if (this.mPendingFrame != null) {
            this.mPendingFrame.dumpDebug(protoOutputStream, 1146756268037L);
        }
        if (this.mPendingVisibleFrame != null) {
            this.mPendingVisibleFrame.dumpDebug(protoOutputStream, 1146756268038L);
        }
        protoOutputStream.write(1120986464263L, this.mAnimationState);
        protoOutputStream.write(1120986464264L, this.mType);
        protoOutputStream.end(start);
    }
}
