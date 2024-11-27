package com.android.server.wm;

/* loaded from: classes3.dex */
class SmoothDimmer extends com.android.server.wm.Dimmer {
    private static final java.lang.String TAG = "WindowManager";
    final com.android.server.wm.DimmerAnimationHelper.AnimationAdapterFactory mAnimationAdapterFactory;
    com.android.server.wm.SmoothDimmer.DimState mDimState;

    @com.android.internal.annotations.VisibleForTesting
    class DimState {
        private final com.android.server.wm.DimmerAnimationHelper mAnimationHelper;
        android.view.SurfaceControl mDimSurface;
        final com.android.server.wm.WindowContainer mHostContainer;
        private com.android.server.wm.WindowContainer mLastRequestedDimContainer;
        boolean mSkipAnimation = false;
        boolean mAnimateExit = true;
        private boolean mIsVisible = false;
        final android.graphics.Rect mDimBounds = new android.graphics.Rect();

        DimState() {
            this.mHostContainer = com.android.server.wm.SmoothDimmer.this.mHost;
            this.mAnimationHelper = new com.android.server.wm.DimmerAnimationHelper(com.android.server.wm.SmoothDimmer.this.mAnimationAdapterFactory);
            try {
                this.mDimSurface = makeDimLayer();
            } catch (android.view.Surface.OutOfResourcesException e) {
                android.util.Log.w(com.android.server.wm.SmoothDimmer.TAG, "OutOfResourcesException creating dim surface");
            }
        }

        void ensureVisible(android.view.SurfaceControl.Transaction transaction) {
            if (!this.mIsVisible) {
                transaction.show(this.mDimSurface);
                transaction.setAlpha(this.mDimSurface, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
                this.mIsVisible = true;
            }
        }

        void adjustSurfaceLayout(android.view.SurfaceControl.Transaction transaction) {
            transaction.setPosition(this.mDimSurface, this.mDimBounds.left, this.mDimBounds.top);
            transaction.setWindowCrop(this.mDimSurface, this.mDimBounds.width(), this.mDimBounds.height());
        }

        void prepareLookChange(float f, int i) {
            this.mAnimationHelper.setRequestedAppearance(f, i);
        }

        void exit(android.view.SurfaceControl.Transaction transaction) {
            if (!this.mAnimateExit) {
                remove(transaction);
            } else {
                this.mAnimationHelper.setExitParameters();
                setReady(transaction);
            }
        }

        void remove(android.view.SurfaceControl.Transaction transaction) {
            this.mAnimationHelper.stopCurrentAnimation(this.mDimSurface);
            if (this.mDimSurface.isValid()) {
                transaction.remove(this.mDimSurface);
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_DIMMER, 5380455212389185829L, 0, null, java.lang.String.valueOf(this), java.lang.String.valueOf(transaction));
                return;
            }
            android.util.Log.w(com.android.server.wm.SmoothDimmer.TAG, "Tried to remove " + this.mDimSurface + " multiple times\n");
        }

        public java.lang.String toString() {
            return "SmoothDimmer#DimState with host=" + this.mHostContainer + ", surface=" + this.mDimSurface;
        }

        void prepareReparent(com.android.server.wm.WindowContainer windowContainer, int i) {
            this.mAnimationHelper.setRequestedRelativeParent(windowContainer, i);
        }

        void setReady(android.view.SurfaceControl.Transaction transaction) {
            this.mAnimationHelper.applyChanges(transaction, this);
        }

        boolean isDimming() {
            return this.mLastRequestedDimContainer != null;
        }

        private android.view.SurfaceControl makeDimLayer() {
            return com.android.server.wm.SmoothDimmer.this.mHost.makeChildSurface(null).setParent(com.android.server.wm.SmoothDimmer.this.mHost.getSurfaceControl()).setColorLayer().setName("Dim Layer for - " + com.android.server.wm.SmoothDimmer.this.mHost.getName()).setCallsite("DimLayer.makeDimLayer").build();
        }
    }

    protected SmoothDimmer(com.android.server.wm.WindowContainer windowContainer) {
        this(windowContainer, new com.android.server.wm.DimmerAnimationHelper.AnimationAdapterFactory());
    }

    @com.android.internal.annotations.VisibleForTesting
    SmoothDimmer(com.android.server.wm.WindowContainer windowContainer, com.android.server.wm.DimmerAnimationHelper.AnimationAdapterFactory animationAdapterFactory) {
        super(windowContainer);
        this.mAnimationAdapterFactory = animationAdapterFactory;
    }

    @Override // com.android.server.wm.Dimmer
    void resetDimStates() {
        if (this.mDimState != null) {
            this.mDimState.mLastRequestedDimContainer = null;
        }
    }

    @Override // com.android.server.wm.Dimmer
    protected void adjustAppearance(com.android.server.wm.WindowContainer windowContainer, float f, int i) {
        obtainDimState(windowContainer).prepareLookChange(f, i);
    }

    @Override // com.android.server.wm.Dimmer
    protected void adjustRelativeLayer(com.android.server.wm.WindowContainer windowContainer, int i) {
        if (this.mDimState != null) {
            this.mDimState.prepareReparent(windowContainer, i);
        }
    }

    @Override // com.android.server.wm.Dimmer
    boolean updateDims(android.view.SurfaceControl.Transaction transaction) {
        if (this.mDimState == null) {
            return false;
        }
        if (!this.mDimState.isDimming()) {
            this.mDimState.exit(transaction);
            this.mDimState = null;
            return false;
        }
        this.mDimState.adjustSurfaceLayout(transaction);
        com.android.server.wm.WindowState asWindowState = this.mDimState.mLastRequestedDimContainer.asWindowState();
        if (!this.mDimState.mIsVisible && asWindowState != null && asWindowState.mActivityRecord != null && asWindowState.mActivityRecord.mStartingData != null) {
            this.mDimState.mSkipAnimation = true;
        }
        this.mDimState.setReady(transaction);
        return true;
    }

    private com.android.server.wm.SmoothDimmer.DimState obtainDimState(com.android.server.wm.WindowContainer windowContainer) {
        if (this.mDimState == null) {
            this.mDimState = new com.android.server.wm.SmoothDimmer.DimState();
        }
        this.mDimState.mLastRequestedDimContainer = windowContainer;
        return this.mDimState;
    }

    @Override // com.android.server.wm.Dimmer
    @com.android.internal.annotations.VisibleForTesting
    android.view.SurfaceControl getDimLayer() {
        if (this.mDimState != null) {
            return this.mDimState.mDimSurface;
        }
        return null;
    }

    @Override // com.android.server.wm.Dimmer
    android.graphics.Rect getDimBounds() {
        if (this.mDimState != null) {
            return this.mDimState.mDimBounds;
        }
        return null;
    }

    @Override // com.android.server.wm.Dimmer
    void dontAnimateExit() {
        if (this.mDimState != null) {
            this.mDimState.mAnimateExit = false;
        }
    }
}
