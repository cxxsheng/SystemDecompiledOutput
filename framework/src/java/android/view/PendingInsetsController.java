package android.view;

/* loaded from: classes4.dex */
public class PendingInsetsController implements android.view.WindowInsetsController {
    private static final int KEEP_BEHAVIOR = -1;
    private boolean mAnimationsDisabled;
    private int mAppearance;
    private int mAppearanceMask;
    private android.view.WindowInsetsAnimationControlListener mLoggingListener;
    private android.view.InsetsController mReplayedInsetsController;
    private final java.util.ArrayList<android.view.PendingInsetsController.PendingRequest> mRequests = new java.util.ArrayList<>();
    private int mBehavior = -1;
    private final android.view.InsetsState mDummyState = new android.view.InsetsState();
    private java.util.ArrayList<android.view.WindowInsetsController.OnControllableInsetsChangedListener> mControllableInsetsChangedListeners = new java.util.ArrayList<>();
    private int mCaptionInsetsHeight = 0;
    private int mImeCaptionBarInsetsHeight = 0;
    private int mRequestedVisibleTypes = android.view.WindowInsets.Type.defaultVisible();

    private interface PendingRequest {
        void replay(android.view.InsetsController insetsController);
    }

    @Override // android.view.WindowInsetsController
    public void show(int i) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.show(i);
        } else {
            this.mRequests.add(new android.view.PendingInsetsController.ShowRequest(i));
            this.mRequestedVisibleTypes = i | this.mRequestedVisibleTypes;
        }
    }

    @Override // android.view.WindowInsetsController
    public void hide(int i) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.hide(i);
            return;
        }
        this.mRequests.add(new android.view.PendingInsetsController.HideRequest(i));
        this.mRequestedVisibleTypes = (~i) & this.mRequestedVisibleTypes;
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsAppearance(int i, int i2) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.setSystemBarsAppearance(i, i2);
            return;
        }
        this.mAppearance = (i & i2) | (this.mAppearance & (~i2));
        this.mAppearanceMask |= i2;
    }

    @Override // android.view.WindowInsetsController
    public int getSystemBarsAppearance() {
        if (this.mReplayedInsetsController != null) {
            return this.mReplayedInsetsController.getSystemBarsAppearance();
        }
        return this.mAppearance;
    }

    @Override // android.view.WindowInsetsController
    public void setCaptionInsetsHeight(int i) {
        this.mCaptionInsetsHeight = i;
    }

    @Override // android.view.WindowInsetsController
    public void setImeCaptionBarInsetsHeight(int i) {
        this.mImeCaptionBarInsetsHeight = i;
    }

    @Override // android.view.WindowInsetsController
    public void setSystemBarsBehavior(int i) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.setSystemBarsBehavior(i);
        } else {
            this.mBehavior = i;
        }
    }

    @Override // android.view.WindowInsetsController
    public int getSystemBarsBehavior() {
        if (this.mReplayedInsetsController != null) {
            return this.mReplayedInsetsController.getSystemBarsBehavior();
        }
        if (this.mBehavior == -1) {
            return 1;
        }
        return this.mBehavior;
    }

    @Override // android.view.WindowInsetsController
    public void setAnimationsDisabled(boolean z) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.setAnimationsDisabled(z);
        } else {
            this.mAnimationsDisabled = z;
        }
    }

    @Override // android.view.WindowInsetsController
    public android.view.InsetsState getState() {
        return this.mDummyState;
    }

    @Override // android.view.WindowInsetsController
    public int getRequestedVisibleTypes() {
        if (this.mReplayedInsetsController != null) {
            return this.mReplayedInsetsController.getRequestedVisibleTypes();
        }
        return this.mRequestedVisibleTypes;
    }

    @Override // android.view.WindowInsetsController
    public void addOnControllableInsetsChangedListener(android.view.WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.addOnControllableInsetsChangedListener(onControllableInsetsChangedListener);
        } else {
            this.mControllableInsetsChangedListeners.add(onControllableInsetsChangedListener);
            onControllableInsetsChangedListener.onControllableInsetsChanged(this, 0);
        }
    }

    @Override // android.view.WindowInsetsController
    public void removeOnControllableInsetsChangedListener(android.view.WindowInsetsController.OnControllableInsetsChangedListener onControllableInsetsChangedListener) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.removeOnControllableInsetsChangedListener(onControllableInsetsChangedListener);
        } else {
            this.mControllableInsetsChangedListeners.remove(onControllableInsetsChangedListener);
        }
    }

    public void replayAndAttach(android.view.InsetsController insetsController) {
        if (this.mBehavior != -1) {
            insetsController.setSystemBarsBehavior(this.mBehavior);
        }
        if (this.mAppearanceMask != 0) {
            insetsController.setSystemBarsAppearance(this.mAppearance, this.mAppearanceMask);
        }
        if (this.mCaptionInsetsHeight != 0) {
            insetsController.setCaptionInsetsHeight(this.mCaptionInsetsHeight);
        }
        if (this.mImeCaptionBarInsetsHeight != 0) {
            insetsController.setImeCaptionBarInsetsHeight(this.mImeCaptionBarInsetsHeight);
        }
        if (this.mAnimationsDisabled) {
            insetsController.setAnimationsDisabled(true);
        }
        int size = this.mRequests.size();
        for (int i = 0; i < size; i++) {
            this.mRequests.get(i).replay(insetsController);
        }
        int size2 = this.mControllableInsetsChangedListeners.size();
        for (int i2 = 0; i2 < size2; i2++) {
            insetsController.addOnControllableInsetsChangedListener(this.mControllableInsetsChangedListeners.get(i2));
        }
        if (this.mLoggingListener != null) {
            insetsController.setSystemDrivenInsetsAnimationLoggingListener(this.mLoggingListener);
        }
        this.mRequests.clear();
        this.mControllableInsetsChangedListeners.clear();
        this.mBehavior = -1;
        this.mAppearance = 0;
        this.mAppearanceMask = 0;
        this.mAnimationsDisabled = false;
        this.mLoggingListener = null;
        this.mRequestedVisibleTypes = android.view.WindowInsets.Type.defaultVisible();
        this.mReplayedInsetsController = insetsController;
    }

    public void detach() {
        this.mReplayedInsetsController = null;
    }

    @Override // android.view.WindowInsetsController
    public void setSystemDrivenInsetsAnimationLoggingListener(android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.setSystemDrivenInsetsAnimationLoggingListener(windowInsetsAnimationControlListener);
        } else {
            this.mLoggingListener = windowInsetsAnimationControlListener;
        }
    }

    @Override // android.view.WindowInsetsController
    public void controlWindowInsetsAnimation(int i, long j, android.view.animation.Interpolator interpolator, android.os.CancellationSignal cancellationSignal, android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener) {
        if (this.mReplayedInsetsController != null) {
            this.mReplayedInsetsController.controlWindowInsetsAnimation(i, j, interpolator, cancellationSignal, windowInsetsAnimationControlListener);
        } else {
            windowInsetsAnimationControlListener.onCancelled(null);
        }
    }

    private static class ShowRequest implements android.view.PendingInsetsController.PendingRequest {
        private final int mTypes;

        public ShowRequest(int i) {
            this.mTypes = i;
        }

        @Override // android.view.PendingInsetsController.PendingRequest
        public void replay(android.view.InsetsController insetsController) {
            insetsController.show(this.mTypes);
        }
    }

    private static class HideRequest implements android.view.PendingInsetsController.PendingRequest {
        private final int mTypes;

        public HideRequest(int i) {
            this.mTypes = i;
        }

        @Override // android.view.PendingInsetsController.PendingRequest
        public void replay(android.view.InsetsController insetsController) {
            insetsController.hide(this.mTypes);
        }
    }
}
