package com.android.server.wm;

/* loaded from: classes3.dex */
public class DimmerAnimationHelper {
    private static final int DEFAULT_DIM_ANIM_DURATION_MS = 200;
    private static final java.lang.String TAG = "WindowManager";
    private com.android.server.wm.DimmerAnimationHelper.AnimationSpec mAlphaAnimationSpec;
    private final com.android.server.wm.DimmerAnimationHelper.AnimationAdapterFactory mAnimationAdapterFactory;
    private com.android.server.wm.AnimationAdapter mLocalAnimationAdapter;
    private com.android.server.wm.DimmerAnimationHelper.Change mCurrentProperties = new com.android.server.wm.DimmerAnimationHelper.Change();
    private com.android.server.wm.DimmerAnimationHelper.Change mRequestedProperties = new com.android.server.wm.DimmerAnimationHelper.Change();

    static class Change {
        private static final float EPSILON = 1.0E-4f;
        private float mAlpha;
        private int mBlurRadius;
        private com.android.server.wm.WindowContainer mDimmingContainer;
        private int mRelativeLayer;

        Change() {
            this.mAlpha = -1.0f;
            this.mBlurRadius = -1;
            this.mDimmingContainer = null;
            this.mRelativeLayer = -1;
        }

        Change(com.android.server.wm.DimmerAnimationHelper.Change change) {
            this.mAlpha = -1.0f;
            this.mBlurRadius = -1;
            this.mDimmingContainer = null;
            this.mRelativeLayer = -1;
            this.mAlpha = change.mAlpha;
            this.mBlurRadius = change.mBlurRadius;
            this.mDimmingContainer = change.mDimmingContainer;
            this.mRelativeLayer = change.mRelativeLayer;
        }

        boolean hasSameVisualProperties(com.android.server.wm.DimmerAnimationHelper.Change change) {
            return java.lang.Math.abs(this.mAlpha - change.mAlpha) < EPSILON && this.mBlurRadius == change.mBlurRadius;
        }

        boolean hasSameDimmingContainer(com.android.server.wm.DimmerAnimationHelper.Change change) {
            return this.mDimmingContainer != null && this.mDimmingContainer == change.mDimmingContainer;
        }

        void inheritPropertiesFromAnimation(com.android.server.wm.DimmerAnimationHelper.AnimationSpec animationSpec) {
            this.mAlpha = animationSpec.mCurrentAlpha;
            this.mBlurRadius = animationSpec.mCurrentBlur;
        }

        public java.lang.String toString() {
            return "Dim state: alpha=" + this.mAlpha + ", blur=" + this.mBlurRadius + ", container=" + this.mDimmingContainer + ", relativePosition=" + this.mRelativeLayer;
        }
    }

    DimmerAnimationHelper(com.android.server.wm.DimmerAnimationHelper.AnimationAdapterFactory animationAdapterFactory) {
        this.mAnimationAdapterFactory = animationAdapterFactory;
    }

    void setExitParameters() {
        setRequestedRelativeParent(this.mRequestedProperties.mDimmingContainer, -1);
        setRequestedAppearance(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, 0);
    }

    void setRequestedRelativeParent(com.android.server.wm.WindowContainer windowContainer, int i) {
        this.mRequestedProperties.mDimmingContainer = windowContainer;
        this.mRequestedProperties.mRelativeLayer = i;
    }

    void setRequestedAppearance(float f, int i) {
        this.mRequestedProperties.mAlpha = f;
        this.mRequestedProperties.mBlurRadius = i;
    }

    void applyChanges(android.view.SurfaceControl.Transaction transaction, com.android.server.wm.SmoothDimmer.DimState dimState) {
        if (this.mRequestedProperties.mDimmingContainer == null) {
            android.util.Log.e(TAG, this + " does not have a dimming container. Have you forgotten to call adjustRelativeLayer?");
            return;
        }
        if (this.mRequestedProperties.mDimmingContainer.mSurfaceControl == null) {
            android.util.Log.w(TAG, "container " + this.mRequestedProperties.mDimmingContainer + "does not have a surface");
            dimState.remove(transaction);
            return;
        }
        dimState.ensureVisible(transaction);
        relativeReparent(dimState.mDimSurface, this.mRequestedProperties.mDimmingContainer.getSurfaceControl(), this.mRequestedProperties.mRelativeLayer, transaction);
        if (!this.mCurrentProperties.hasSameVisualProperties(this.mRequestedProperties)) {
            stopCurrentAnimation(dimState.mDimSurface);
            if (dimState.mSkipAnimation || (this.mRequestedProperties.hasSameDimmingContainer(this.mCurrentProperties) && dimState.isDimming())) {
                com.android.internal.protolog.ProtoLogImpl_1545807451.d(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_DIMMER, 3778139410556664218L, 24, null, java.lang.String.valueOf(dimState), java.lang.Double.valueOf(this.mRequestedProperties.mAlpha), java.lang.Long.valueOf(this.mRequestedProperties.mBlurRadius));
                setAlphaBlur(dimState.mDimSurface, this.mRequestedProperties.mAlpha, this.mRequestedProperties.mBlurRadius, transaction);
                dimState.mSkipAnimation = false;
            } else {
                startAnimation(transaction, dimState);
            }
        } else if (!dimState.isDimming()) {
            dimState.remove(transaction);
        }
        this.mCurrentProperties = new com.android.server.wm.DimmerAnimationHelper.Change(this.mRequestedProperties);
    }

    private void startAnimation(final android.view.SurfaceControl.Transaction transaction, final com.android.server.wm.SmoothDimmer.DimState dimState) {
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_DIMMER, -6357087772993832060L, 0, null, java.lang.String.valueOf(dimState));
        this.mAlphaAnimationSpec = getRequestedAnimationSpec();
        this.mLocalAnimationAdapter = this.mAnimationAdapterFactory.get(this.mAlphaAnimationSpec, dimState.mHostContainer.mWmService.mSurfaceAnimationRunner);
        final float f = this.mRequestedProperties.mAlpha;
        final int i = this.mRequestedProperties.mBlurRadius;
        this.mLocalAnimationAdapter.startAnimation(dimState.mDimSurface, transaction, 4, new com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback() { // from class: com.android.server.wm.DimmerAnimationHelper$$ExternalSyntheticLambda0
            @Override // com.android.server.wm.SurfaceAnimator.OnAnimationFinishedCallback
            public final void onAnimationFinished(int i2, com.android.server.wm.AnimationAdapter animationAdapter) {
                com.android.server.wm.DimmerAnimationHelper.this.lambda$startAnimation$0(dimState, f, i, transaction, i2, animationAdapter);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAnimation$0(com.android.server.wm.SmoothDimmer.DimState dimState, float f, int i, android.view.SurfaceControl.Transaction transaction, int i2, com.android.server.wm.AnimationAdapter animationAdapter) {
        com.android.server.wm.WindowManagerGlobalLock windowManagerGlobalLock = dimState.mHostContainer.mWmService.mGlobalLock;
        com.android.server.wm.WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                setAlphaBlur(dimState.mDimSurface, f, i, transaction);
                if (f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE && !dimState.isDimming()) {
                    dimState.remove(transaction);
                }
                this.mLocalAnimationAdapter = null;
                this.mAlphaAnimationSpec = null;
            } catch (java.lang.Throwable th) {
                com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection();
    }

    private boolean isAnimating() {
        return this.mAlphaAnimationSpec != null;
    }

    void stopCurrentAnimation(android.view.SurfaceControl surfaceControl) {
        if (this.mLocalAnimationAdapter != null && isAnimating()) {
            this.mCurrentProperties.inheritPropertiesFromAnimation(this.mAlphaAnimationSpec);
            this.mLocalAnimationAdapter.onAnimationCancelled(surfaceControl);
            this.mLocalAnimationAdapter = null;
            this.mAlphaAnimationSpec = null;
        }
    }

    private com.android.server.wm.DimmerAnimationHelper.AnimationSpec getRequestedAnimationSpec() {
        float max = java.lang.Math.max(this.mCurrentProperties.mAlpha, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        com.android.server.wm.DimmerAnimationHelper.AnimationSpec animationSpec = new com.android.server.wm.DimmerAnimationHelper.AnimationSpec(new com.android.server.wm.DimmerAnimationHelper.AnimationSpec.AnimationExtremes(java.lang.Float.valueOf(max), java.lang.Float.valueOf(this.mRequestedProperties.mAlpha)), new com.android.server.wm.DimmerAnimationHelper.AnimationSpec.AnimationExtremes(java.lang.Integer.valueOf(java.lang.Math.max(this.mCurrentProperties.mBlurRadius, 0)), java.lang.Integer.valueOf(this.mRequestedProperties.mBlurRadius)), (long) (getDimDuration(this.mRequestedProperties.mDimmingContainer) * java.lang.Math.abs(this.mRequestedProperties.mAlpha - max)));
        com.android.internal.protolog.ProtoLogImpl_1545807451.v(com.android.internal.protolog.ProtoLogGroup.WM_DEBUG_DIMMER, -1187783168730646350L, 0, null, java.lang.String.valueOf(animationSpec));
        return animationSpec;
    }

    void relativeReparent(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl surfaceControl2, int i, android.view.SurfaceControl.Transaction transaction) {
        try {
            transaction.setRelativeLayer(surfaceControl, surfaceControl2, i);
        } catch (java.lang.NullPointerException e) {
            android.util.Log.w(TAG, "Tried to change parent of dim " + surfaceControl + " after remove", e);
        }
    }

    void setAlphaBlur(android.view.SurfaceControl surfaceControl, float f, int i, android.view.SurfaceControl.Transaction transaction) {
        try {
            transaction.setAlpha(surfaceControl, f);
            transaction.setBackgroundBlurRadius(surfaceControl, i);
        } catch (java.lang.NullPointerException e) {
            android.util.Log.w(TAG, "Tried to change look of dim " + surfaceControl + " after remove", e);
        }
    }

    private long getDimDuration(com.android.server.wm.WindowContainer windowContainer) {
        com.android.server.wm.AnimationAdapter animation = windowContainer.mSurfaceAnimator.getAnimation();
        return animation == null ? (long) (windowContainer.mWmService.getTransitionAnimationScaleLocked() * 200.0f) : animation.getDurationHint();
    }

    static class AnimationSpec implements com.android.server.wm.LocalAnimationAdapter.AnimationSpec {
        private static final java.lang.String TAG = "WindowManager";
        private final com.android.server.wm.DimmerAnimationHelper.AnimationSpec.AnimationExtremes<java.lang.Float> mAlpha;
        private final com.android.server.wm.DimmerAnimationHelper.AnimationSpec.AnimationExtremes<java.lang.Integer> mBlur;
        private final long mDuration;
        float mCurrentAlpha = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        int mCurrentBlur = 0;
        boolean mStarted = false;

        static class AnimationExtremes<T> {
            final T mFinishValue;
            final T mStartValue;

            AnimationExtremes(T t, T t2) {
                this.mStartValue = t;
                this.mFinishValue = t2;
            }

            public java.lang.String toString() {
                return "[" + this.mStartValue + "->" + this.mFinishValue + "]";
            }
        }

        AnimationSpec(com.android.server.wm.DimmerAnimationHelper.AnimationSpec.AnimationExtremes<java.lang.Float> animationExtremes, com.android.server.wm.DimmerAnimationHelper.AnimationSpec.AnimationExtremes<java.lang.Integer> animationExtremes2, long j) {
            this.mAlpha = animationExtremes;
            this.mBlur = animationExtremes2;
            this.mDuration = j;
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public long getDuration() {
            return this.mDuration;
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public void apply(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl, long j) {
            if (!this.mStarted) {
                this.mStarted = true;
                return;
            }
            float fraction = getFraction(j);
            this.mCurrentAlpha = ((this.mAlpha.mFinishValue.floatValue() - this.mAlpha.mStartValue.floatValue()) * fraction) + this.mAlpha.mStartValue.floatValue();
            this.mCurrentBlur = (((int) fraction) * (this.mBlur.mFinishValue.intValue() - this.mBlur.mStartValue.intValue())) + this.mBlur.mStartValue.intValue();
            if (surfaceControl.isValid()) {
                transaction.setAlpha(surfaceControl, this.mCurrentAlpha);
                transaction.setBackgroundBlurRadius(surfaceControl, this.mCurrentBlur);
                return;
            }
            android.util.Log.w(TAG, "Dimmer#AnimationSpec tried to access " + surfaceControl + " after release");
        }

        public java.lang.String toString() {
            return "Animation spec: alpha=" + this.mAlpha + ", blur=" + this.mBlur;
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            printWriter.print(str);
            printWriter.print("from_alpha=");
            printWriter.print(this.mAlpha.mStartValue);
            printWriter.print(" to_alpha=");
            printWriter.print(this.mAlpha.mFinishValue);
            printWriter.print(str);
            printWriter.print("from_blur=");
            printWriter.print(this.mBlur.mStartValue);
            printWriter.print(" to_blur=");
            printWriter.print(this.mBlur.mFinishValue);
            printWriter.print(" duration=");
            printWriter.println(this.mDuration);
        }

        @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
        public void dumpDebugInner(android.util.proto.ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(1146756268035L);
            protoOutputStream.write(1108101562369L, this.mAlpha.mStartValue.floatValue());
            protoOutputStream.write(1108101562370L, this.mAlpha.mFinishValue.floatValue());
            protoOutputStream.write(1112396529667L, this.mDuration);
            protoOutputStream.end(start);
        }
    }

    static class AnimationAdapterFactory {
        AnimationAdapterFactory() {
        }

        public com.android.server.wm.AnimationAdapter get(com.android.server.wm.LocalAnimationAdapter.AnimationSpec animationSpec, com.android.server.wm.SurfaceAnimationRunner surfaceAnimationRunner) {
            return new com.android.server.wm.LocalAnimationAdapter(animationSpec, surfaceAnimationRunner);
        }
    }
}
