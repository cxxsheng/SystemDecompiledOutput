package android.view;

/* loaded from: classes4.dex */
class HdrRenderState implements java.util.function.Consumer<android.view.Display> {
    private static final boolean FLAG_ANIMATE_ENABLED = com.android.graphics.hwui.flags.Flags.animateHdrTransitions();
    private static final float TRANSITION_PER_MS = 0.01f;
    private final android.view.ViewRootImpl mViewRoot;
    private boolean mIsHdrEnabled = false;
    private boolean mIsListenerRegistered = false;
    private boolean mUpdateHdrSdrRatioInfo = false;
    private float mDesiredHdrSdrRatio = 1.0f;
    private float mTargetDesiredHdrSdrRatio = 1.0f;
    private float mTargetHdrSdrRatio = 1.0f;
    private float mRenderHdrSdrRatio = 1.0f;
    private float mPreviousRenderRatio = 1.0f;
    private long mLastUpdateMillis = -1;

    HdrRenderState(android.view.ViewRootImpl viewRootImpl) {
        this.mViewRoot = viewRootImpl;
    }

    @Override // java.util.function.Consumer
    public void accept(android.view.Display display) {
        forceUpdateHdrSdrRatio();
        this.mViewRoot.invalidate();
    }

    boolean isHdrEnabled() {
        return this.mIsHdrEnabled;
    }

    void stopListening() {
        if (this.mIsListenerRegistered) {
            this.mViewRoot.mDisplay.unregisterHdrSdrRatioChangedListener(this);
            this.mIsListenerRegistered = false;
        }
    }

    void startListening() {
        if (isHdrEnabled() && !this.mIsListenerRegistered && this.mViewRoot.mDisplay != null) {
            this.mViewRoot.mDisplay.registerHdrSdrRatioChangedListener(this.mViewRoot.mExecutor, this);
        }
    }

    boolean updateForFrame(long j) {
        boolean z = this.mUpdateHdrSdrRatioInfo;
        this.mUpdateHdrSdrRatioInfo = false;
        this.mRenderHdrSdrRatio = this.mTargetHdrSdrRatio;
        float max = java.lang.Math.max(java.lang.Math.min(32L, j - this.mLastUpdateMillis), 8L) * 0.01f;
        this.mLastUpdateMillis = j;
        if (z && FLAG_ANIMATE_ENABLED) {
            if (isHdrEnabled()) {
                if (this.mTargetHdrSdrRatio - this.mPreviousRenderRatio > max) {
                    this.mRenderHdrSdrRatio = this.mPreviousRenderRatio + max;
                    this.mUpdateHdrSdrRatioInfo = true;
                    this.mViewRoot.invalidate();
                }
                this.mPreviousRenderRatio = this.mRenderHdrSdrRatio;
                if (this.mTargetDesiredHdrSdrRatio < this.mDesiredHdrSdrRatio) {
                    this.mDesiredHdrSdrRatio = java.lang.Math.max(this.mTargetDesiredHdrSdrRatio, this.mDesiredHdrSdrRatio - max);
                    if (this.mDesiredHdrSdrRatio != this.mTargetDesiredHdrSdrRatio) {
                        this.mUpdateHdrSdrRatioInfo = true;
                        this.mViewRoot.invalidate();
                    }
                }
            } else {
                this.mPreviousRenderRatio = this.mTargetHdrSdrRatio;
                this.mDesiredHdrSdrRatio = this.mTargetDesiredHdrSdrRatio;
            }
        }
        return z;
    }

    float getDesiredHdrSdrRatio() {
        return this.mDesiredHdrSdrRatio;
    }

    float getRenderHdrSdrRatio() {
        return this.mRenderHdrSdrRatio;
    }

    void forceUpdateHdrSdrRatio() {
        if (isHdrEnabled()) {
            this.mTargetHdrSdrRatio = java.lang.Math.min(this.mDesiredHdrSdrRatio, this.mViewRoot.mDisplay.getHdrSdrRatio());
        } else {
            this.mTargetHdrSdrRatio = 1.0f;
        }
        this.mUpdateHdrSdrRatioInfo = true;
    }

    void setDesiredHdrSdrRatio(boolean z, float f) {
        this.mIsHdrEnabled = z;
        this.mLastUpdateMillis = android.os.SystemClock.uptimeMillis();
        if (f != this.mTargetDesiredHdrSdrRatio) {
            this.mTargetDesiredHdrSdrRatio = f;
            if (this.mTargetDesiredHdrSdrRatio > this.mDesiredHdrSdrRatio || !FLAG_ANIMATE_ENABLED) {
                this.mDesiredHdrSdrRatio = this.mTargetDesiredHdrSdrRatio;
            }
            forceUpdateHdrSdrRatio();
            this.mViewRoot.invalidate();
            if (isHdrEnabled()) {
                startListening();
            } else {
                stopListening();
            }
        }
    }
}
